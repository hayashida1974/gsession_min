package jp.groupsession.v2.usr.usr032kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr032.Usr032Biz;

/**
 * <br>[機  能] メイン 管理者設定 ユーザインポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr032knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr032knAction.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        Usr032knForm uform = (Usr032knForm) form;

        con.setAutoCommit(true);
        UsrCommonBiz ucBiz = new UsrCommonBiz(con, getRequestModel(req));
        boolean existGroup = ucBiz.isNotAdminGroupExists(con);
        con.setAutoCommit(false);
        if (!existGroup) {
            //システム管理グループ以外のグループが存在しない場合、共通メッセージ画面へ遷移
            return getNotAdminGroupErrorPage(map, req);
        }

        //パスワード変更の有効・無効を設定
        if (canChangePassword(con, 0)) {
            uform.setChangePassword(GSConst.CHANGEPASSWORD_PARMIT);
        } else {
            uform.setChangePassword(GSConst.CHANGEPASSWORD_NOTPARMIT);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        String processMode = uform.getProcessMode();

        if (cmd.equals("Usr032kn_Back")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            forward = map.findForward("back");
        } else if (cmd.equals("doImp")) {
            //実行ボタン押下
            log__.debug("実行ボタン押下");
            forward = __doImport(map, uform, req, res, con, processMode);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }


    /**
     * <br>[機  能] 確認画面の表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Usr032knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);

        try {
            //テンポラリディレクトリパスを取得
            RequestModel reqMdl = getRequestModel(req);
            Usr032Biz biz032 = new Usr032Biz();
            String tempDir = biz032.getTempDir(reqMdl);

            //ファイル名設定
            String fileName = __getFileName(tempDir);
            form.setUsr032knFileName(fileName);

            int mode = form.getUsr032cmdMode();

            //通常モードの場合、グループ情報取得
            if (mode == GSConstUser.MODE_NORMAL) {

                //所属グループ
                int[] gsids = UserUtil.toGroupSidFromCsv(form.getSelectgroup());
                GroupBiz grpBiz = new GroupBiz();
                form.setUsr032knSltgps(grpBiz.getGroupTreeList(con, gsids));

                //デフォルトグループ
                int dgsid = form.getUsr031defgroup();
                GroupDao dao = new GroupDao(con);
                form.setUsr032knDefgp(dao.getGroup(dgsid));
            }

            //再入力チェック
            boolean updatePass = (form.getUsr032updatePassFlg() == GSConstUser.PASS_CHANGE_OK);
            ActionErrors errors = form.validateCheck(map,  reqMdl, tempDir, con,
                                                                         updatePass);
            if (errors.size() > 0) {
                addErrors(req, errors);
                return map.getInputForward();
            }

            //通常orグループ一括
            int torikomiMode = form.getUsr032cmdMode();

            //取込み予定情報を取得
            UserCsvImport csvImport
                = new UserCsvImport(getRequestModel(req), 1, torikomiMode, con);
            form.setUsr032knImpList(csvImport.importCsv(tempDir, _getLoginInstance()));

            //未登録の役職リストを作成
            HashMap<String, String> posMap = csvImport.getPosMap();
            Iterator<String> posIt = posMap.values().iterator();
            ArrayList<String> posList = new ArrayList<String>();
            while (posIt.hasNext()) {
                String posName = (String) posIt.next();
                posList.add(posName);
            }
            form.setPosList(posList);

            //通常モード以外の場合、グループ情報取得
            if (mode != GSConstUser.MODE_NORMAL) {
                //グループ名称変更フラグ
                List<CmnUsrmInfModel> cuim = new ArrayList<CmnUsrmInfModel>();
                cuim = form.getUsr032knImpList();
                for (int i = 0; i < cuim.size(); i++) {
                    if (cuim.get(i).getUsiHenkou().equals(GSConstUser.GROUP_NAME_CHANGE)) {
                        form.setUsr032knGrpflg(Integer.valueOf(GSConstUser.GROUP_NAME_CHANGE));
                        break;
                    }
                }

            }
        } finally {
            con.setAutoCommit(false);
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private String __getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザ追加処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param processMode 処理モード
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
                                      Usr032knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      String processMode)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Usr032Biz biz032 = new Usr032Biz();
        String tempDir = biz032.getTempDir(reqMdl);

        con.setAutoCommit(true);
        List<CmnGroupmModel> glist = null;
        CmnGroupmModel dfGroup = null;
        try {
            //再入力チェック
            boolean updatePass = (form.getUsr032updatePassFlg() == GSConstUser.PASS_CHANGE_OK);
            ActionErrors errors = form.validateCheck(map, getRequestModel(req), tempDir, con,
                                                                         updatePass);
            if (errors.size() > 0) {
                log__.debug("再チェックNG");
                return __doInit(map, form, req, res, con);
            }

            //グループ情報取得
            int mode = form.getUsr032cmdMode();
            int[] gsids = null;
            int dgsid = 0;

            //通常モードのグループ情報取得
            if (mode == GSConstUser.MODE_NORMAL) {
                gsids = UserUtil.toGroupSidFromCsv(form.getSelectgroup());
                GroupDao dao = new GroupDao(con);

                //所属グループ
                glist = dao.getGroups(gsids);

                //デフォルトグループ
                dgsid = form.getUsr031defgroup();
                dfGroup = dao.getGroup(dgsid);
            }
        } finally {
            con.setAutoCommit(false);
        }

        //取込み処理
        log__.debug("ユーザ取り込み開始");
        CommonBiz cmnBiz = new CommonBiz();
        boolean commit = false;
        try {
            MlCountMtController cntCon = null;
            //SID採番
            cntCon = getCountMtController(req);

            //コンストラクタ設定用モデルを作成する。
            UserCsvImportModel csvImportModel = new UserCsvImportModel();
            csvImportModel.setGlist(glist);
            csvImportModel.setDfGroup(dfGroup);
            csvImportModel.setUserSid(userSid);
            csvImportModel.setMode(0);
            csvImportModel.setUpdateFlg(form.getUsr032updateFlg());
            csvImportModel.setUpdatePassFlg(form.getUsr032updatePassFlg());
            csvImportModel.setPswdKbn(form.getUsr032PswdKbn());
            csvImportModel.setInsertFlg(form.getUsr032createFlg());

            //通常orグループ一括
            int torikomiMode = form.getUsr032cmdMode();

            UserCsvImport csvImport = new UserCsvImport(getRequestModel(req), cntCon,
                                   con, csvImportModel, torikomiMode);

            //ユーザリスナー取得
            IUserGroupListener[] lis = UserUtil.getUserListeners(getPluginConfig(req));
            csvImport.setLis(lis);
            ArrayList<CmnUsrmInfModel> userlist = csvImport.importCsv(tempDir, _getLoginInstance());
            long num = 0;
            if (userlist != null) {
                num = userlist.size();
            }

            //役職情報の編集を行う
            Usr032knBiz biz = new Usr032knBiz();
            biz.posEdit(con, cntCon, userSid);

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ インポート **/
            String strImport = gsMsg.getMessage("cmn.import");

            //ログ出力
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    strImport, GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("user.usr013kn.4") + "]" + num);

            commit = true;
        } catch (Exception e) {
            log__.error("ユーザCSVの取り込みに失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }

            //テンポラリディレクトリのファイル削除を行う
            biz032.deleteTempDir(reqMdl);
        }

        //完了画面遷移
        __setKanryou(map, req, form, "touroku.kanryo.object");
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @param msgState 完了画面に表示するメッセージのキー
     */
    private void __setKanryou(ActionMapping map,
                               HttpServletRequest req,
                               Usr032knForm form,
                               String msgState) {
        GsMessage gsMsg = new GsMessage();
        //ユーザ
        String textUser = gsMsg.getMessage(req, "cmn.user");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("back");
        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=Usr032kn_ImpComp");

        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textUser));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("processMode", form.getProcessMode());
        cmn999Form.addHiddenParam("usr030SearchKana", form.getUsr030SearchKana());
        cmn999Form.addHiddenParam("usr030selectusers", form.getUsr030selectusers());
        cmn999Form.addHiddenParam("selectgroup", form.getSelectgroup());

        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr030shainno", form.getUsr030shainno());
        cmn999Form.addHiddenParam("usr030userId", form.getUsr030userId());
        cmn999Form.addHiddenParam("usr030usrUkoFlg", form.getUsr030usrUkoFlg());
        cmn999Form.addHiddenParam("usr030sei", form.getUsr030sei());
        cmn999Form.addHiddenParam("usr030mei", form.getUsr030mei());
        cmn999Form.addHiddenParam("usr030seikn", form.getUsr030seikn());
        cmn999Form.addHiddenParam("usr030meikn", form.getUsr030meikn());
        cmn999Form.addHiddenParam("usr030agefrom", form.getUsr030agefrom());
        cmn999Form.addHiddenParam("usr030ageto", form.getUsr030ageto());
        cmn999Form.addHiddenParam("usr030yakushoku", form.getUsr030yakushoku());
        cmn999Form.addHiddenParam("usr030mail", form.getUsr030mail());
        cmn999Form.addHiddenParam("usr030tdfkCd", form.getUsr030tdfkCd());
        cmn999Form.addHiddenParam("usr030seibetu", form.getUsr030seibetu());
        cmn999Form.addHiddenParam("usr030entranceYearFr", form.getUsr030entranceYearFr());
        cmn999Form.addHiddenParam("usr030entranceMonthFr", form.getUsr030entranceMonthFr());
        cmn999Form.addHiddenParam("usr030entranceDayFr", form.getUsr030entranceDayFr());
        cmn999Form.addHiddenParam("usr030entranceYearTo", form.getUsr030entranceYearTo());
        cmn999Form.addHiddenParam("usr030entranceMonthTo", form.getUsr030entranceMonthTo());
        cmn999Form.addHiddenParam("usr030entranceDayTo", form.getUsr030entranceDayTo());

        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
        cmn999Form.addHiddenParam("usr030userIdSave", form.getUsr030userIdSave());
        cmn999Form.addHiddenParam("usr030usrUkoFlgSave", form.getUsr030usrUkoFlgSave());
        cmn999Form.addHiddenParam("usr030shainnoSave", form.getUsr030shainnoSave());
        cmn999Form.addHiddenParam("usr030seiSave", form.getUsr030seiSave());
        cmn999Form.addHiddenParam("usr030meiSave", form.getUsr030meiSave());
        cmn999Form.addHiddenParam("usr030seiknSave", form.getUsr030seiknSave());
        cmn999Form.addHiddenParam("usr030meiknSave", form.getUsr030meiknSave());
        cmn999Form.addHiddenParam("usr030agefromSave", form.getUsr030agefromSave());
        cmn999Form.addHiddenParam("usr030agetoSave", form.getUsr030agetoSave());
        cmn999Form.addHiddenParam("usr030yakushokuSave", form.getUsr030yakushokuSave());
        cmn999Form.addHiddenParam("usr030mailSave", form.getUsr030mailSave());
        cmn999Form.addHiddenParam("usr030tdfkCdSave", form.getUsr030tdfkCdSave());
        cmn999Form.addHiddenParam("usr030seibetuSave", form.getUsr030seibetu());
        cmn999Form.addHiddenParam("usr030entranceYearFrSave", form.getUsr030entranceYearFrSave());
        cmn999Form.addHiddenParam("usr030entranceMonthFrSave", form.getUsr030entranceMonthFrSave());
        cmn999Form.addHiddenParam("usr030entranceDayFrSave", form.getUsr030entranceDayFrSave());
        cmn999Form.addHiddenParam("usr030entranceYearToSave", form.getUsr030entranceYearToSave());
        cmn999Form.addHiddenParam("usr030entranceMonthToSave", form.getUsr030entranceMonthToSave());
        cmn999Form.addHiddenParam("usr030entranceDayToSave", form.getUsr030entranceDayToSave());

        cmn999Form.addHiddenParam("usr030cmdMode", form.getUsr030cmdMode());
        cmn999Form.addHiddenParam("usr030SearchFlg", form.getUsr030SearchFlg());

        req.setAttribute("cmn999Form", cmn999Form);
    }
}