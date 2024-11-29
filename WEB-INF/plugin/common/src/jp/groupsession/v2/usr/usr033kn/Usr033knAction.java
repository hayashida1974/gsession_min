package jp.groupsession.v2.usr.usr033kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.usr033.Usr033Biz;

/**
 * <br>[機  能] メイン 管理者設定 ユーザ一括削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr033knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr033knAction.class);

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
    * @throws Exception 実行例外
    */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        ActionForward forward = null;
        Usr033knForm usr033knForm = (Usr033knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("Usr033kn_Back")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            forward = map.findForward("back");
        } else if (cmd.equals("doDel")) {
            //実行ボタン押下
            log__.debug("実行ボタン押下");
            forward = __doDel(map, usr033knForm, req, res, con);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr033knForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] ユーザ削除処理
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
    private ActionForward __doDel(
            ActionMapping map,
            Usr033knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Usr033Biz biz033 = new Usr033Biz();
        String tempDir = biz033.getTempDir(reqMdl);

        con.setAutoCommit(true);
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        try {
            //入力チェック
            ActionErrors errors = form.validateCheck(getRequestModel(req), tempDir, con);
            if (errors.size() > 0) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        } finally {
            con.setAutoCommit(false);
        }
        //CSV取込み、ユーザ一括削除処理
        log__.debug("ユーザ一括削除開始");
        boolean commitFlg = false;
        ArrayList<Integer> usrList = new ArrayList<Integer>();
        try {
            UserCsvDelete csvDelete =
                    new UserCsvDelete(getRequestModel(req),
                            GSConstUser.CSV_IMPORT_RUN, con);

            //ユーザリスナー取得
            IUserGroupListener[] lis = UserUtil.getUserListeners(getPluginConfig(req));
            csvDelete.setLis(lis);

            //CSVを読込み、削除を実行
            csvDelete.importCsv(tempDir, _getLoginInstance());
            
            usrList = csvDelete.getDelUserList();

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ ユーザ一括削除 **/
            String strUserDel = gsMsg.getMessage("user.usr033.1");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            long num = form.getUsr033knCount();
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    strUserDel, GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("user.usr013kn.4") + "]" + num);

            commitFlg = true;

        } catch (Exception e) {
            log__.error("ユーザ一括削除に失敗しました。" + e);
            throw e;

        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }

            //テンポラリディレクトリのファイル削除を行う
            biz033.deleteTempDir(reqMdl);
        }

        //完了画面遷移
        __setKanryou(map, req, form, "sakujo.kanryo.object", usrList);
        return  map.findForward("gf_msg");
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
     * @return ActionForward アクションフォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(
            ActionMapping map,
            Usr033knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Usr033Biz biz033 = new Usr033Biz();
        String tempDir = biz033.getTempDir(reqMdl);

        con.setAutoCommit(true);
        try {
            //入力チェック
            ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
            if (errors.size() > 0) {
                addErrors(req, errors);
                return map.getInputForward();
            }

            String fileName = __getFileName(tempDir);
            form.setUsr033knFileName(fileName);

            //CSVを読込み、削除する情報を取得
            UserCsvDelete csvDelete =
                    new UserCsvDelete(reqMdl,
                            GSConstUser.CSV_IMPORT_DISPLAY, con);
            
            
            ArrayList<CmnUsrmInfModel> impList = csvDelete.importCsv(tempDir, _getLoginInstance());
            form.setUsr033knImpList(impList);
            
            //ログインユーザを削除した場合
            List<Integer> selectUsrList = impList.stream()
                    .map(mdl -> mdl.getUsrSid())
                    .collect(Collectors.toList());
            if (selectUsrList.contains(getSessionUserSid(req))) {
                form.setUsr033knDelSelfFlg(true);
            }
            
            if (form.getUsr033knImpList() != null) {
                form.setUsr033knCount(form.getUsr033knImpList().size());
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
     * <br>[機  能] 登録完了画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @param msgState 完了画面に表示するメッセージのキー
     * @param usrList 削除済みユーザリスト
     */
    private void __setKanryou(
            ActionMapping map,
            HttpServletRequest req,
            Usr033knForm form,
            String msgState,
            ArrayList<Integer> usrList) {
        log__.debug("START");
        
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //ユーザ
        String textUser = gsMsg.getMessage(req, "cmn.user");
        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        
        //ログインユーザを削除した場合
        if (usrList.contains(getSessionUserSid(req))) {
            removeSession(req);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
            List<String> messageList = new ArrayList<String>();
            messageList.add(msgRes.getMessage(msgState, textUser));
            messageList.add(gsMsg.getMessage("user.usr031kn.15"));
            messageList.add(gsMsg.getMessage("user.usr031kn.16"));
            cmn999Form.setMessageList(messageList);
            urlForward = map.findForward("gf_domain_logout");
            cmn999Form.setUrlOK(urlForward.getPath());
            req.setAttribute("cmn999Form", cmn999Form);
            return;
        }
        
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        log__.debug("■完了画面msgState :" + msgState);
        cmn999Form.setMessage(msgRes.getMessage(msgState, textUser));
        log__.debug("表示メッセージ３３：" + msgRes.getMessage(msgState, textUser));

        urlForward = map.findForward("list");

        //画面パラメータをセット
        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr030userId", form.getUsr030userId());
        cmn999Form.addHiddenParam("usr030usrUkoFlg", form.getUsr030usrUkoFlg());
        cmn999Form.addHiddenParam("usr030shainno", form.getUsr030shainno());
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

        cmn999Form.addHiddenParam("usr030SearchKana", form.getUsr030SearchKana());
        cmn999Form.addHiddenParam("usr030selectuser", form.getUsr030selectuser());
        cmn999Form.addHiddenParam("usr030cmdMode", form.getUsr030cmdMode());
        cmn999Form.addHiddenParam("usr030SearchFlg", form.getUsr030SearchFlg());

        cmn999Form.setUrlOK(urlForward.getPath());
        req.setAttribute("cmn999Form", cmn999Form);

        log__.debug("END");
    }
}
