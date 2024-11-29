package jp.groupsession.v2.usr.usr011kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.UserUtil;

/**
 * <br>[機  能] メイン 管理者設定 グループ登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr011knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr011knAction.class);

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
        Usr011knForm usr011Form = (Usr011knForm) form;

        //処理モード取得
        String mode = NullDefault.getString(usr011Form.getUsr010grpmode(), "");
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("commit")) {
            //データ登録
            if (mode.equals("add")) {
                //追加
                forward = __doAdd(map, usr011Form, req, res, con);
            } else if (mode.equals("edit")) {
                //修正 or 削除
                forward = __doEdit(map, usr011Form, req, res, con);
            }
        } else if (cmd.equals("usr011_back")) {
            //戻る
            forward = map.findForward("usr011_back");
        } else {
            //初期処理
            log__.debug("初期表示処理");
            __doInit(map, usr011Form, req, res, con);
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <br>[機  能] 確認画面初期処理
     * <br>[解  説] 確認画面の初期表示用データを取得・セットします
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doInit(ActionMapping map,
                                    Usr011knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);

        int grpSid = form.getUsr010grpSid();

        if (form.getUsr011DelKbn().equals("del")) {
            //データ取得
            CmnGroupmModel gmodel = new CmnGroupmModel();
            gmodel.setGrpSid(grpSid);
            gmodel.setGrpSort(grpSid);
            CmnGroupmDao gdao = new CmnGroupmDao(con);
            CmnGroupmModel model = gdao.select(grpSid);
            //画面項目へセット
            form.setUsr011gpIdDel(model.getGrpId());
            form.setUsr011gpnameDel(model.getGrpName());
            form.setUsr011gpnameKanaDel(model.getGrpNameKn());
            form.setUsr011comDel(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getGrpComment()));
            //ヘルプモードを設定する
            form.setUsr011hrpPrm(GSConstUser.HELP_MODE_DELETE);
        } else {
            form.setUsr011comHtml(StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(form.getUsr011com(), "")));
            //追加確認画面遷移時
            if (form.getUsr010grpmode().equals("add")) {
                //ヘルプモードを設定する
                form.setUsr011hrpPrm(GSConstUser.HELP_MODE_ADD);

            //編集確認画面遷移時
            } else if (form.getUsr010grpmode().equals("edit")) {
                //ヘルプモードを設定する
                form.setUsr011hrpPrm(GSConstUser.HELP_MODE_EDIT);
            }
        }

        //所属ユーザリスト作成
        String[] sv_users = form.getUsers_l();

        if (sv_users != null && sv_users.length > 0) {
            CmnUsrmInfModel[] retList = null;
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> list = userBiz.getUserList(con, sv_users);

//            for (CmnUsrmInfModel uinfMdl : list) {
            for (int n = 0; n < list.size(); n++) {
                CmnUsrmInfModel[] mdlList =
                    (CmnUsrmInfModel[]) list.toArray(
                        new CmnUsrmInfModel[list.size()]);

                retList = new CmnUsrmInfModel[mdlList.length];

                for (int i = 0; i < sv_users.length; i++) {
                    int svSid = Integer.parseInt(sv_users[i]);
                    CmnUsrmInfModel retMdl = null;
                    for (CmnUsrmInfModel ret : mdlList) {
                        if (ret.getUsrSid() == svSid) {
                            retMdl = ret;
                            break;
                        }
                    }
                    if (retMdl != null) {
                        retList[i] = retMdl;
                    }
                }
            }

            List<SltUserPerGroupModel> setlist = new ArrayList<SltUserPerGroupModel>();
            if (retList != null && retList.length > 0) {
                for (CmnUsrmInfModel afterSort : retList) {
                    SltUserPerGroupModel smodel = new SltUserPerGroupModel();
                    smodel.setFullName(StringUtilHtml.transToHTmlPlusAmparsant(
                            afterSort.getUsiSei() + "  " + afterSort.getUsiMei()));
                    smodel.setUsrUkoFlg(afterSort.getUsrUkoFlg());
                    setlist.add(smodel);
                }
            }

            form.setUsr011tarBelongingUser(setlist);
        }

        //管理者ユーザリスト生成
        String[] sv_usersKr = form.getUsersKr_l(); 
        
        if (sv_usersKr != null && sv_usersKr.length > 0) {

            CmnUsrmInfModel[] retList = null;
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> list = userBiz.getUserList(con, sv_usersKr);

            for (int n = 0; n < list.size(); n++) {

                CmnUsrmInfModel[] mdlList =
                    (CmnUsrmInfModel[]) list.toArray(
                        new CmnUsrmInfModel[list.size()]);

                retList = new CmnUsrmInfModel[mdlList.length];

                for (int i = 0; i < sv_usersKr.length; i++) {
                    int svSid = Integer.parseInt(sv_usersKr[i]);
                    CmnUsrmInfModel retMdl = null;
                    for (CmnUsrmInfModel ret : mdlList) {
                        if (ret.getUsrSid() == svSid) {
                            retMdl = ret;
                            break;
                        }
                    }
                    if (retMdl != null) {
                        retList[i] = retMdl;
                    }
                }
            }

            ArrayList<SltUserPerGroupModel> setlist = new ArrayList<SltUserPerGroupModel>();
            if (retList != null && retList.length > 0) {
                for (CmnUsrmInfModel afterSort : retList) {
                    SltUserPerGroupModel smodel = new SltUserPerGroupModel();
                    smodel.setFullName(StringUtilHtml.transToHTmlPlusAmparsant(
                            afterSort.getUsiSei() + "  " + afterSort.getUsiMei()));
                    smodel.setUsrUkoFlg(afterSort.getUsrUkoFlg());
                    setlist.add(smodel);
                }
            }

            form.setUsr011BelongingUserKr(setlist);
        }

        //グループ階層文字列を生成
        form.setUsr011knGroupClassName(__getGroupClassString(form, con));

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 追加時処理
     * <br>[解  説] データの追加処理を行います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doAdd(ActionMapping map, Usr011knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        ActionErrors errors = form.validateAdd(map, getRequestModel(req), con);
        con.setAutoCommit(false);
        //再入力チェック
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.findForward("usr011_back");
        }

        //採番コントローラー
        MlCountMtController cntCon = getCountMtController(req);

        //ユーザリスナー取得
        IUserGroupListener[] lis = UserUtil.getUserListeners(getPluginConfig(req));

        //データ追加
        Usr011knBiz biz = new Usr011knBiz(getRequestModel(req));

        Usr011knParamModel paramMdl = new Usr011knParamModel();
        paramMdl.setParam(form);
        biz.executeTransaction(paramMdl, con, cntCon, lis, getRequestModel(req));
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        /** メッセージ 登録 **/
        String entry = gsMsg.getMessage("cmn.entry");

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                entry, GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("cmn.group.name") + "]"
                + NullDefault.getString(form.getUsr011gpname(), ""));
        //遷移
        __setKanryou(map, req, form.getUsr010grpmode(), form.getUsr011DelKbn());
        forward = map.findForward("gf_msg");

        return forward;
    }

    /**
     * <br>[機  能] 修正時処理
     * <br>[解  説] データの更新処理を行います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doEdit(ActionMapping map,
                                    Usr011knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        ActionForward forward = null;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ 削除
        String delete = gsMsg.getMessage("cmn.delete");
        //メッセージ 変更
        String change = gsMsg.getMessage("cmn.change");

        if (!form.getUsr011DelKbn().equals("del")) {
            //修正処理
            if (!isTokenValid(req, true)) {
                return getSubmitErrorPage(map, req);
            }

            con.setAutoCommit(true);
            ActionErrors errors = form.validateEdit(map, getRequestModel(req), con, true);
            con.setAutoCommit(false);
            //再入力チェック
            if (errors.size() > 0) {
                addErrors(req, errors);
                return map.findForward("usr011_back");
            }

            //ユーザリスナー取得
            IUserGroupListener[] lis = UserUtil.getUserListeners(getPluginConfig(req));

            //データ修正
            Usr011knBiz biz = new Usr011knBiz(getRequestModel(req));

            Usr011knParamModel paramMdl = new Usr011knParamModel();
            paramMdl.setParam(form);
            biz.executeTransaction(paramMdl, con, null, lis, getRequestModel(req));
            paramMdl.setFormData(form);

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    change, GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("cmn.group.name") + "]"
                    + NullDefault.getString(form.getUsr011gpname(), ""));

            //遷移
            __setKanryou(map, req, form.getUsr010grpmode(), form.getUsr011DelKbn());
            forward = map.findForward("gf_msg");

        } else {
            //削除処理
            forward = __doDel(map, form, req, res, con);
            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    delete, GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("cmn.group.name") + "]"
                    + NullDefault.getString(form.getUsr011gpname(), ""));
        }

        return forward;
    }

    /**
     * <br>[機  能] 削除時処理
     * <br>[解  説] データの削除処理を行います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doDel(ActionMapping map,
                                   Usr011knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
        throws Exception {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        ActionErrors errors = form.validateDell(map, getRequestModel(req), con);
        con.setAutoCommit(false);
        //再入力チェック
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.findForward("usr011_back");
        }

        //ユーザリスナー取得
        IUserGroupListener[] lis = UserUtil.getUserListeners(getPluginConfig(req));

        //データ修正
        Usr011knBiz biz = new Usr011knBiz(getRequestModel(req));

        Usr011knParamModel paramMdl = new Usr011knParamModel();
        paramMdl.setParam(form);
        biz.executeTransaction(paramMdl, con, null, lis, getRequestModel(req));
        paramMdl.setFormData(form);

        //遷移
        __setKanryou(map, req, form.getUsr010grpmode(), form.getUsr011DelKbn());
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param processMode 処理モード
     * @param delKbn 削除区分
     */
    private void __setKanryou(
        ActionMapping map,
        HttpServletRequest req,
        String processMode,
        String delKbn) {

        log__.debug("START");
        GsMessage gsMsg = new GsMessage();
        //グループ
        String textGroup = gsMsg.getMessage(req, "cmn.group");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("list");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        if (delKbn.equals("del")) {
            msgState = "sakujo.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textGroup));
        req.setAttribute("cmn999Form", cmn999Form);

        log__.debug("END");
    }

    /**
     * グループ階層のテキスト表現文字列を生成します
     * @param form フォーム
     * @param con コネクション
     * @return String
     * @throws SQLException SQL実行時例外
     */
    private String __getGroupClassString(Usr011knForm form, Connection con) throws SQLException {

        String ret = "";
        StringBuilder buf = new StringBuilder();
//        int confGrSid = form.getSelectgroup();
        int confGrSid = 0;

        if (form.getUsr011DelKbn().equals("del")) {
            confGrSid = form.getUsr010grpSid();
        } else {
            confGrSid = form.getSelectgroup();
        }

        GroupDao grDao = new GroupDao(con);
        CmnGroupClassModel classMdl = grDao.getGroupClassModel(confGrSid);
        String dirString = "＞";

        if (classMdl != null) {

            if (classMdl.getGclName1() != null) {
                buf.append(classMdl.getGclName1());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName2() != null) {
                buf.append(classMdl.getGclName2());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName3() != null) {
                buf.append(classMdl.getGclName3());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName4() != null) {
                buf.append(classMdl.getGclName4());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName5() != null) {
                buf.append(classMdl.getGclName5());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName6() != null) {
                buf.append(classMdl.getGclName6());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName7() != null) {
                buf.append(classMdl.getGclName7());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName8() != null) {
                buf.append(classMdl.getGclName8());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName9() != null) {
                buf.append(classMdl.getGclName9());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
            if (classMdl.getGclName10() != null) {
                buf.append(classMdl.getGclName10());
                buf.append(" ");
                buf.append(dirString);
                buf.append(" ");
            }
        }

        if (form.getUsr011DelKbn().equals("del")) {
            int lastIdx = buf.lastIndexOf(dirString);
            if (lastIdx != -1) {
                buf.delete(lastIdx, buf.length());
            }
        } else {
            buf.append(form.getUsr011gpname());
        }
        ret = buf.toString();

        return ret;
    }

//    /**
//     * 指定文字長のスペース文字列を生成する。
//     * @param length  指定文字長
//     * @return スペース文字列
//     */
//    private String __getSpaceString(int length) {
//        StringBuffer ret = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            ret.append(" ");
//        }
//        return ret.toString();
//    }
}