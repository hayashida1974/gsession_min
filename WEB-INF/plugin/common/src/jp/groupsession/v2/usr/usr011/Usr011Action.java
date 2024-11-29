package jp.groupsession.v2.usr.usr011;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupDao;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 グループ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr011Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr011Action.class);

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
        Usr011Form usr011Form = (Usr011Form) form;

        //グループSID取得
        int grpSid = NullDefault.getInt(String.valueOf(usr011Form.getUsr010grpSid()), -1);
        //処理モード取得
        String mode = NullDefault.getString(usr011Form.getUsr010grpmode(), "");
        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("kakunin")) {
            //OKボタンクリック
            if (mode.equals("add")) {
                //追加
                forward = __doAddKakunin(map, usr011Form, req, res, con);
            } else if (mode.equals("edit")) {
                //修正
                forward = __doEditKakunin(map, usr011Form, req, res, con, grpSid);
            }
        } else if (cmd.equals("back")) {
            //戻るボタンクリック
            forward = map.findForward("back");
        } else if (cmd.equals("usr011_back")) {
            log__.debug("確認画面から戻る");
            forward = __doRedraw(map, usr011Form, req, res, con);
        } else if (cmd.equals("del")) {
            //削除
            forward = __doDelete(map, usr011Form, req, res, con, cmd);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, usr011Form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説] 利用者管理メニューを表示する
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Usr011Form ufo,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        int grpSid = NullDefault.getInt(String.valueOf(ufo.getUsr010grpSid()), -1);
//        int dbParentsGroup = -1;
//        String disabled = "";

        //処理モード=編集
        if (ufo.getUsr010grpmode().equals("edit")) {
            con.setAutoCommit(true);

            //管理者グループは削除不可、それ以外のグループは削除可
            if (GSConstUser.SID_ADMIN == grpSid) {
                ufo.setUsr011DelButton(GSConstCommon.DISABLE);
            } else {
                ufo.setUsr011DelButton(GSConstCommon.ENABLE);
            }

            //データ取得
            CmnGroupmDao gdao = new CmnGroupmDao(con);
            CmnGroupmModel model = gdao.select(grpSid);

            //グループID
            ufo.setUsr011gpId(
                    NullDefault.getString(ufo.getUsr011gpId(), model.getGrpId()));
            //グループ名
            ufo.setUsr011gpname(
                    NullDefault.getString(ufo.getUsr011gpname(), model.getGrpName()));
            //グループ名カナ
            ufo.setUsr011gpnameKana(
                    NullDefault.getString(ufo.getUsr011gpnameKana(), model.getGrpNameKn()));
            //コメント
            ufo.setUsr011com(
                    NullDefault.getString(ufo.getUsr011com(), model.getGrpComment()));

            //所属ユーザリスト作成
            if (ufo.getUsers_l() == null) {
                __setInitSltBelongUserInfo(map, ufo, req, res, con, grpSid);
            }

            //所属ユーザリスト(管理者)作成
            if (ufo.getUsersKr_l() == null) {
                __setInitSltBelongUserKrInfo(map, ufo, req, res, con, grpSid);
            }

            con.setAutoCommit(false);
        }
        return __doRedraw(map, ufo, req, res, con);
    }

    /**
     * <br>[機  能] 再描画処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doRedraw(ActionMapping map,
                                      Usr011Form ufo,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        con.setAutoCommit(true);

        int grpSid = NullDefault.getInt(String.valueOf(ufo.getUsr010grpSid()), -1);
        int dbParentsGroup = -1;
        String disabled = "";

        //処理モード=編集
        if (ufo.getUsr010grpmode().equals("edit")) {
            dbParentsGroup = __getParentsGroup(grpSid, con);
            disabled = __getDisabledGroups(grpSid, req, con);
        } else {
            dbParentsGroup = -1;
            disabled = __getDisabledGroups();
        }

        int selectGroup = NullDefault.getInt(req.getParameter("selectgroup"), dbParentsGroup);
        ufo.setSelectgroup(selectGroup);

        //選択不可グループ
        log__.debug("選択不可グループ=" + disabled);
        ufo.setDisabledGroups(disabled);
        ufo.setUsr011DelKbn("0");
        ufo.setUsr011grpsid(grpSid);

        //デフォルトグループに設定しているユーザを所属ユーザに設定
        __setDefaultBelongUser(map, ufo, req, res, con);

        //システム管理グループの場合、指定ユーザを除外
        String adminSid = String.valueOf(GSConst.SYSTEM_USER_ADMIN);
        String systemMailSid = String.valueOf(GSConst.SYSTEM_USER_MAIL);
        if (grpSid == GSConstUser.SID_ADMIN 
            && ufo.getUsers_l() != null && ufo.getUsers_l().length > 0) {
            String[] users = Arrays.asList(ufo.getUsers_l())
                                    .stream()
                                    .filter(u -> !u.equals(adminSid) && !u.equals(systemMailSid))
                                    .toArray(String[]::new);
            ufo.setUsers_l(users);
        }

        //前画面選択グループのセット
        int sltgrpSid = ufo.getSlt_group();
        __setSltUserPerGroupInfo(map, ufo, req, res, con, sltgrpSid);

        //所属ユーザ(管理者)のセット
        __setSltUserKrInfo(map, ufo, req, res, con, grpSid);

        //選択可能なグループ階層レベルを取得する
        ufo.setSelectLevel(__getSelectLevel(ufo, req, res, con));

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] デフォルトグループに設定しているユーザを所属ユーザに設定
     * <br>[解  説] 
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param grpSid グループSID
     * @throws SQLException 
     * @throws Exception 実行例外
     */
    private void __setDefaultBelongUser(
        ActionMapping map,
        Usr011Form ufo,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {
        
        Usr011Biz biz = new Usr011Biz(getRequestModel(req));
        Usr011ParamModel paramMdl = new Usr011ParamModel();
        paramMdl.setParam(ufo);
        biz.setDefaultBelongUser(paramMdl, con);
        paramMdl.setFormData(ufo);
    }

    /**
     * <br>[機  能] グループ、ユーザ設定
     * <br>[解  説] ・グループセレクトボックスと、所属ユーザを設定します。
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param grpSid グループSID
     * @throws Exception 実行例外
     */
    private void __setSltUserPerGroupInfo(ActionMapping map,
                                            Usr011Form ufo,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            int grpSid)
        throws Exception {

        //グループセレクトボックス
        Usr011Biz biz = new Usr011Biz(getRequestModel(req));

        Usr011ParamModel paramMdl = new Usr011ParamModel();
        paramMdl.setParam(ufo);
        biz.getGroupComb(paramMdl, con);
        paramMdl.setFormData(ufo);

        //未所属ユーザーリストを作成します。
        UserBiz userBiz = new UserBiz();
        ufo.setUsr011tarUnbelongingUser(
                (ArrayList<SltUserPerGroupModel>) userBiz.getUserPerGroupList(
                        con, ufo.getSlt_group(), null, true));

        String[] svList = ufo.getUsers_l();
        if (svList == null) {
            svList = new String[0];
        }

        //選択済みユーザ一覧
        List<CmnUsrmInfModel> luList = userBiz.getUserList(con, svList);
        ArrayList<SltUserPerGroupModel> uList = new ArrayList<SltUserPerGroupModel>();
        ArrayList<SltUserPerGroupModel> rightList = ufo.getUsr011tarUnbelongingUser();
        for (String svUser : svList) {
            int index = 0;
            boolean exists = false;
            int svUserSid = Integer.parseInt(svUser);
            for (; index < rightList.size(); index++) {
                SltUserPerGroupModel belongModel = (SltUserPerGroupModel) rightList.get(index);
                if (belongModel.getUsrsid() == svUserSid) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                rightList.remove(index);
            }
            for (CmnUsrmInfModel ufmdl : luList) {
                if (ufmdl.getUsrSid() == svUserSid) {
                    //所属へユーザーの移動
                    SltUserPerGroupModel bean = new SltUserPerGroupModel();
                    bean.setUsrsid(ufmdl.getUsrSid());
                    bean.setFullName(ufmdl.getUsiSei()
                            + "  " + ufmdl.getUsiMei());
                    bean.setUsrUkoFlg(ufmdl.getUsrUkoFlg());
                    uList.add(bean);

                }
            }
        }
        ufo.setUsr011tarUnbelongingUser(rightList);
        log__.debug("未所属ユーザ件数=" + rightList.size());

        ufo.setUsr011tarBelongingUser(uList);
    }

    /**
     * <br>[機  能] ユーザ設定
     * <br>[解  説] 所属ユーザ(管理者)を設定します。
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param grpSid グループSID
     * @throws Exception 実行例外
     */
    private void __setSltUserKrInfo(ActionMapping map,
                                      Usr011Form ufo,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      int grpSid)
        throws Exception {

        //所属利用者に設定されているSID取得
        String[] sv_users = ufo.getUsers_l();

        //所属利用者情報取得
        UserBiz usrBiz = new UserBiz();
        List<CmnUsrmInfModel> userList = null;
        CmnUsrmInfModel[] retList = null;

        if (sv_users != null && sv_users.length > 0) {
            userList = usrBiz.getUserList(con, sv_users);
            if (!userList.isEmpty()) {
                CmnUsrmInfModel[] mdlList =
                    (CmnUsrmInfModel[]) userList.toArray(
                        new CmnUsrmInfModel[userList.size()]);

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
        }

        //取得したユーザを管理者・一般利用者に振分け
        ArrayList<SltUserPerGroupModel> kanriUser =
            new ArrayList<SltUserPerGroupModel>();
        ArrayList<SltUserPerGroupModel> ippanUser =
            new ArrayList<SltUserPerGroupModel>();
        ArrayList<String> kanriSid = new ArrayList<String>();

        //管理者に設定されているSID取得
        String[] svList = ufo.getUsersKr_l();
        if (svList == null) {
            svList = new String[0];
        }

        if (retList != null) {
            for (CmnUsrmInfModel uMdl : retList) {

                SltUserPerGroupModel bean = new SltUserPerGroupModel();
                bean.setUsrsid(uMdl.getUsrSid());
                bean.setFullName(uMdl.getUsiSei() + "  " + uMdl.getUsiMei());
                bean.setUsrUkoFlg(uMdl.getUsrUkoFlg());
                boolean exists = false;
                for (String krUser : svList) {
//                    int idx = 0;
                    int krUserSid = Integer.parseInt(krUser);

                    //管理者に設定されているユーザか
                    if (uMdl.getUsrSid() == krUserSid) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    kanriUser.add(bean);
                    kanriSid.add(String.valueOf(bean.getUsrsid()));
                } else {
                    ippanUser.add(bean);
                }
            }
        }
        ufo.setUsr011BelongingUserKr(kanriUser);
        ufo.setUsr011UnBelongingUserKr(ippanUser);

        if (kanriSid.isEmpty()) {
            ufo.setUsersKr_l(new String[0]);
        } else {
            ufo.setUsersKr_l(
                    (String[]) kanriSid.toArray(new String[kanriSid.size()]));
        }
    }

    /**
     * <br>[機  能] 所属ユーザ設定
     * <br>[解  説] 所属ユーザを設定します。
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param grpSid グループSID
     * @throws Exception 実行例外
     */
    private void __setInitSltBelongUserInfo(ActionMapping map, Usr011Form ufo,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con,
        int grpSid) throws Exception {

        //所属ユーザリストから除外するユーザリスト
        List<String> userSids = new ArrayList<String>();
        userSids.add(String.valueOf(GSConst.SYSTEM_USER_ADMIN));
        userSids.add(String.valueOf(GSConst.SYSTEM_USER_MAIL));
        
        //所属ユーザーリストを作成します。
        UserBiz userBiz = new UserBiz();
        List<SltUserPerGroupModel> alist
            = userBiz.getUserPerGroupList(con, grpSid, userSids.toArray(new String[0]), false);

        ufo.setUsr011tarBelongingUser(alist);

        String[] sids = new String[alist.size()];
        for (int i = 0; i < alist.size(); i++) {
            SltUserPerGroupModel sltModel = (SltUserPerGroupModel) alist.get(i);
            sids[i] = Integer.toString(sltModel.getUsrsid());
        }
        ufo.setUsers_l(sids);
    }

    /**
     * <br>[機  能] 所属ユーザ(管理者)設定
     * <br>[解  説] 所属ユーザ(管理者)を設定します。
     * <br>[備  考]
     * @param map マップ
     * @param ufo フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param grpSid グループSID
     * @throws Exception 実行例外
     */
    private void __setInitSltBelongUserKrInfo(ActionMapping map, Usr011Form ufo,
                                                HttpServletRequest req,
                                                HttpServletResponse res,
                                                Connection con,
                                                int grpSid)
        throws Exception {

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        //所属ユーザーリストを作成します。
        SltUserPerGroupDao tdao = new SltUserPerGroupDao(con);
        ArrayList<SltUserPerGroupModel> alist
            = (ArrayList<SltUserPerGroupModel>) tdao.selectAdminGroupUserList(grpSid, sortMdl);
        ufo.setUsr011BelongingUserKr(alist);

        String[] sids = new String[alist.size()];
        for (int i = 0; i < alist.size(); i++) {
            SltUserPerGroupModel sltModel = (SltUserPerGroupModel) alist.get(i);
            sids[i] = Integer.toString(sltModel.getUsrsid());
        }
        ufo.setUsersKr_l(sids);
    }

    /**
     * <br>[機  能] 確認ボタンクリック時処理(追加モード)
     * <br>[解  説] 確認ボタンクリック時の処理を行う
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doAddKakunin(ActionMapping map, Usr011Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateAdd(map, getRequestModel(req), con);
        con.setAutoCommit(false);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doRedraw(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //確認画面へ
        forward = map.findForward("kakunin");
        return forward;
    }

    /**
     * <br>[機  能] 確認ボタンクリック時処理(修正モード)
     * <br>[解  説] 確認ボタンクリック時の処理を行う
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param grpSid グループSID
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doEditKakunin(ActionMapping map, Usr011Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, int grpSid)
            throws Exception {

        ActionForward forward = null;

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateEdit(map, getRequestModel(req), con, true);
        con.setAutoCommit(false);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doRedraw(map, form, req, res, con);
        }
        
        //トランザクショントークン設定
        saveToken(req);
        //確認画面へ
        forward = map.findForward("kakunin");
        return forward;
    }

    /**
     * <br>[機  能] 「削除ボタン押下時」処理
     * <br>[解  説] ・次画面を確認画面に設定する
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmd コマンド
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(ActionMapping map,
                                      Usr011Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      String cmd)
        throws Exception {

        ActionForward forward = null;

        con.setAutoCommit(true);
        ActionErrors errors = form.validateDell(map, getRequestModel(req), con);
        con.setAutoCommit(false);
        if (errors.size() > 0) {
            addErrors(req, errors);
            forward = __doRedraw(map, form, req, res, con);
            return forward;
        }

        //トランザクショントークン設定
        saveToken(req);
        // 確認画面表示
        forward = map.findForward("kakunin");

        return forward;
    }

    /**
     * <br>選択可能階層レベルを取得する
     * <br>末端階層レベルから移動可能な階層レベルを算出します。
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return String 階層
     * @throws SQLException SQL実行時例外
     */
    private String __getSelectLevel(
            Usr011Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        con.setAutoCommit(true);
        String mode = NullDefault.getString(form.getUsr010grpmode(), "");
        String ret = "9";
        if (mode.equals("edit")) {
            int grSid = form.getUsr010grpSid();
            //編集対象のグループの末端階層レベルから移動可能な階層レベルを算出
            Usr011Biz biz = new Usr011Biz(getRequestModel(req));
            ret = biz.getSelectLevel(grSid, con);
        }
        con.setAutoCommit(false);

        return ret;
    }

    /**
     * グループSIDから親グループSIDを取得する
     * @param gsid 基準グループSID
     * @param con コネクション
     * @return int 親グループSID
     * @throws SQLException SQL実行時例外
     */
    private int __getParentsGroup(int gsid, Connection con) throws SQLException {
        int ret = -1;
        GroupDao grDao = new GroupDao(con);
        CmnGroupClassModel classMdl = grDao.getGroupClassModel(gsid);
        if (classMdl != null) {
            ret = classMdl.getParentsSid(gsid);
        }
        log__.debug("DB親グループSID=" + ret);
        return  ret;
    }


    /**
     * 選択不可のグループSIDのリストを取得する(編集用)
     * @param grSid グループSID
     * @param req リクエスト
     * @param con コネクション
     * @return String 選択不可のグループSIDのカンマ区切りの配列
     */
    private String __getDisabledGroups(
            int grSid,
            HttpServletRequest req,
            Connection con) {

        ArrayList<Integer> list = null;
        Usr011Biz biz = new Usr011Biz(getRequestModel(req));
        list = biz.getDisabledGroups(grSid, con);
        String ret = __getCvsGroupSid(list);

        return ret;
    }

    /**
     * 選択不可のグループSIDのリストを取得する(追加用)
     * @return String 選択不可のグループSIDのカンマ区切りの配列
     */
    private String __getDisabledGroups() {

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(Integer.valueOf(0));
        String ret = __getCvsGroupSid(list);

        return ret;
    }
    /**
     * グループSIDのリストからカンマ区切りの文字列に変換します。
     * @param list グループSIDのリスト
     * @return カンマ区切りの文字列
     */
    private String __getCvsGroupSid(ArrayList<Integer> list) {

        String csvGroups = new String();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i != 0) {
                    csvGroups = csvGroups.concat(",");
                }
                csvGroups = csvGroups.concat((list.get(i)).toString());
            }
        }
        return csvGroups;
    }

}