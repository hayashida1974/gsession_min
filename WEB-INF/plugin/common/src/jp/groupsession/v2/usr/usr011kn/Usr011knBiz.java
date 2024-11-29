package jp.groupsession.v2.usr.usr011kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupClassDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.usr011.Usr011ParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 グループ登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr011knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr011knBiz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr011knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] グループの追加・更新・削除処理
     * <br>[解  説] DBトランザクションを行う
     * <br>[備  考]
     * @param paramMdl Usr011knParamModel
     * @param con コネクション
     * @param cntCon 採番コントローラ
     * @param lis ユーザリスナー
     * @param reqMdl RequestModel
     * @throws Exception 実行例外
     */
    public void executeTransaction(Usr011knParamModel paramMdl,
                                    Connection con,
                                    MlCountMtController cntCon,
                                    IUserGroupListener[] lis,
                                    RequestModel reqMdl)
        throws Exception {

        //処理モード取得
        String processMode = paramMdl.getUsr010grpmode();
        String delKbn = paramMdl.getUsr011DelKbn();
        int gsid = paramMdl.getUsr011grpsid();
        boolean commitFlg = false;

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        int sessionUser = usModel.getUsrsid();

        if (processMode.equals("add")) {
            //SID採番
            gsid =
                (int) cntCon.getSaibanNumber(
                        SaibanModel.SBNSID_USER,
                        SaibanModel.SBNSID_SUB_GROUP,
                        sessionUser);
        }

        try {

            //グループ編集(所属ユーザ変更)の場合、変更前の所属ユーザを取得する
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> beforeUserList = new ArrayList<Integer>();
            if (processMode.equals("edit") && !delKbn.equals("del")) {
                beforeUserList = belongDao.selectBelongUserSid(gsid);
            }

            __execGroupm(gsid, sessionUser, paramMdl, con, processMode, delKbn);
            __execBelongm(gsid, sessionUser, paramMdl, con, processMode, delKbn);
            __execGroupClassm(gsid, sessionUser, paramMdl, con, processMode, delKbn);

            if (processMode.equals("add")) {
                //グループ追加
                //各プラグインリスナーを呼び出し
                for (int i = 0; i < lis.length; i++) {
                    lis[i].addGroup(con, gsid, sessionUser);
                }

            } else if (processMode.equals("edit") && !delKbn.equals("del")) {
                //グループ編集(所属ユーザ変更)
                int[] gsidList = {gsid};
                List<Integer> nowUserList = belongDao.selectBelongUserSid(gsid);
                for (int belongUsrSid : nowUserList) {
                    if (!beforeUserList.contains(belongUsrSid)) {
                        //各プラグインリスナーを呼び出し
                        for (int i = 0; i < lis.length; i++) {
                            lis[i].changeBelong(con, belongUsrSid, new int[0], gsidList,
                                                sessionUser);
                        }
                    }
                }

            } else if (delKbn.equals("del")) {
                //グループ削除
                //各プラグインリスナーを呼び出し
                for (int i = 0; i < lis.length; i++) {
                    lis[i].deleteGroup(con, gsid, sessionUser, reqMdl);
                }
            }

            commitFlg = true;
        } catch (SQLException e) {
            if (processMode.equals("add")) {
                log__.error("グループ追加に失敗", e);
            } else if (paramMdl.getUsr010grpmode().equals("edit")) {
                log__.error("グループ更新に失敗", e);
            } else {
                log__.error("グループ削除に失敗", e);
            }
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] グループマスタ処理
     * <br>[解  説] グループマスタの追加・更新・削除処理を行います
     * <br>[備  考]
     * @param gSid グループSID
     * @param sessionUser セッションユーザーSID
     * @param paramMdl Usr011knParamModel
     * @param con コネクション
     * @param processMode 処理モード
     * @param delKbn 削除区分
     * @throws SQLException SQL実行時例外
     */
    private void __execGroupm(int gSid,
                               int sessionUser,
                               Usr011ParamModel paramMdl,
                               Connection con,
                               String processMode,
                               String delKbn)
        throws SQLException {

        CmnGroupmModel gmodel = new CmnGroupmModel();
        CmnGroupmDao gdao = new CmnGroupmDao(con);

        UDate now = new UDate();

        gmodel.setGrpSid(gSid);
        gmodel.setGrpId(paramMdl.getUsr011gpId());
        gmodel.setGrpName(paramMdl.getUsr011gpname());
        gmodel.setGrpNameKn(NullDefault.getString(
                paramMdl.getUsr011gpnameKana(), ""));
        gmodel.setGrpComment(paramMdl.getUsr011com());
        gmodel.setGrpEuid(sessionUser);
        gmodel.setGrpEdate(now);
        gmodel.setGrpJkbn(CmnGroupmDao.GRP_JKBN_LIVING);

        if (processMode.equals("add")) {
            gmodel.setGrpAuid(sessionUser);
            gmodel.setGrpAdate(now);
            gmodel.setGrpSort(gSid);
            gdao.insert(gmodel);
        } else if (processMode.equals("edit")
                && !delKbn.equals("del")) {
            gdao.updateCmnGroup(gmodel);
        } else if (delKbn.equals("del")) {
            gmodel.setGrpJkbn(CmnGroupmDao.GRP_JKBN_DELETED);
            gdao.updateCmnGroupDel(gmodel);
        }
    }

    /**
     * <br>[機  能] ユーザ所属マスタ処理
     * <br>[解  説] ユーザ所属マスタの追加・削除処理を行います
     * <br>[備  考]
     * @param gSid グループSID
     * @param sessionUser セッションユーザーSID
     * @param paramMdl Usr011knParamModel
     * @param con コネクション
     * @param processMode 処理モード
     * @param delKbn 削除区分
     * @throws SQLException SQL実行時例外
     */
    private void __execBelongm(int gSid,
                                 int sessionUser,
                                 Usr011ParamModel paramMdl,
                                 Connection con,
                                 String processMode,
                                 String delKbn)
        throws SQLException {

        UDate now = new UDate();

        //システム管理グループの場合、除外していたユーザを追加
        if (gSid == GSConstUser.SID_ADMIN) {
            List<String> sv_users = new ArrayList<String>();
            if (paramMdl.getUsers_l() != null && paramMdl.getUsers_l().length > 0 ) {
                sv_users.addAll(Arrays.asList(paramMdl.getUsers_l()));
            }
            sv_users.add(String.valueOf(GSConst.SYSTEM_USER_ADMIN));
            sv_users.add(String.valueOf(GSConst.SYSTEM_USER_MAIL));
            paramMdl.setUsers_l(sv_users.toArray(new String[0]));
        }

        String[] usids = paramMdl.getUsers_l();
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        CmnBelongmModel belong = new CmnBelongmModel();

        if (usids != null && usids.length > 0) {

            //グループSID
            belong.setGrpSid(gSid);
            //登録者ＩＤ
            belong.setBegAuid(sessionUser);
            //登録日時
            belong.setBegAdate(now);
            //更新者ＩＤ
            belong.setBegEuid(sessionUser);
            //更新日時
            belong.setBegEdate(now);
            //デフォルトグループフラグ
            belong.setBegDefgrp(0);
            //レコード追加
            bdao.insertBelongUser(belong, usids);

            //所属者全員を一般グループメンバに変更
            bdao.updataAllBelongUserGrpKbn(gSid, GSConst.USER_NOT_ADMIN);

            //システム管理グループの場合、除外していたユーザを追加
            if (gSid == GSConstUser.SID_ADMIN) {
                List<String> sv_usersKr = new ArrayList<String>();
                if (paramMdl.getUsersKr_l() != null && paramMdl.getUsersKr_l().length > 0 ) {
                    sv_usersKr.addAll(Arrays.asList(paramMdl.getUsersKr_l()));
                }
                sv_usersKr.add(String.valueOf(GSConst.SYSTEM_USER_ADMIN));
                sv_usersKr.add(String.valueOf(GSConst.SYSTEM_USER_MAIL));
                paramMdl.setUsersKr_l(sv_usersKr.toArray(new String[0]));
            }

            String[] krSids = paramMdl.getUsersKr_l();
            if (krSids != null && krSids.length > 0) {
                bdao.updataBelongUserGrpKbn(GSConst.USER_ADMIN, gSid, krSids);
            }

        } else if (processMode.equals("edit")) {
            //ユーザーが全てリムーブされた時
            //レコード削除
            bdao.delete(gSid);
        }
    }

    /**
     * <br>[機  能] グループ階層マスタ処理
     * <br>[解  説] グループ階層マスタの追加・更新処理を行います
     * <br>[備  考]
     * @param gSid グループSID
     * @param sessionUser セッションユーザーSID
     * @param paramMdl Usr011knParamModel
     * @param con コネクション
     * @param processMode 処理モード
     * @param delKbn 削除区分
     * @throws SQLException SQL実行時例外
     * @throws CloneNotSupportedException 複製生成時の例外
     */
    private void __execGroupClassm(int gSid,
                                     int sessionUser,
                                     Usr011knParamModel paramMdl,
                                     Connection con,
                                     String processMode,
                                     String delKbn)
        throws CloneNotSupportedException, SQLException {

        CmnGroupClassModel gclass = new CmnGroupClassModel();
        CmnGroupClassDao gclsDao = new CmnGroupClassDao(con);
        GroupDao grpDao = new GroupDao(con);
        UDate now = new UDate();

        //グループクラス
        int selectGrp = paramMdl.getSelectgroup();
        if (processMode.equals("add")) {
            //親階層モデルを取得
            CmnGroupClassModel classModel = grpDao.getGroupClassModel(selectGrp);
            if (classModel == null) {
                classModel = new CmnGroupClassModel();
            }
            classModel.addUnder(gSid);
            classModel.setGclAuid(sessionUser);
            classModel.setGclAdate(now);
            classModel.setGclEuid(sessionUser);
            classModel.setGclEdate(now);
            gclsDao.insert(classModel);
        } else if (processMode.equals("edit")
                && !delKbn.equals("del")) {

            //親階層モデルを取得
            CmnGroupClassModel parModel = grpDao.getGroupClassModel(selectGrp);
            if (parModel == null) {
                parModel = new CmnGroupClassModel();
            }
            //移動グループ階層を取得
            ArrayList<CmnGroupClassModel> gcList = grpDao.getUnderGroupClassList(gSid);
            CmnGroupClassModel gcModel = new CmnGroupClassModel();

            //グループ階層情報を登録
            for (int i = 0; i < gcList.size(); i++) {
                gcModel = (CmnGroupClassModel) gcList.get(i);
                try {
                    gcModel = __addParentsGroup(parModel, gcModel, gSid);
                } catch (CloneNotSupportedException e) {
                    log__.error("複製Objectの生成に失敗");
                    throw e;
                }

                gcModel.setGclAuid(sessionUser);
                gcModel.setGclAdate(now);
                gcModel.setGclEuid(sessionUser);
                gcModel.setGclEdate(now);
                //既存グループ階層を削除
                gclsDao.delete((CmnGroupClassModel) gcList.get(i));
                //グループ階層を登録
                gclsDao.insert(gcModel);
            }
            gclsDao.update(gclass);
        } else if (delKbn.equals("del")) {
            //削除時処理
            CmnGroupClassDao clDao = new CmnGroupClassDao(con);
            clDao.deleteDelGroup(gSid);
        }
    }

    /**
     * 移動するグループ階層モデルを親グループ階層モデルへ追加した状態の
     * グループ階層モデルを取得します。
     * @param parModel 親グループ階層モデル
     * @param mvModel 移動するグループ階層モデル
     * @param gSid 移動するグループSID
     * @return GroupClassModel
     * @throws CloneNotSupportedException CloneNotSupportedException
     */
    private CmnGroupClassModel __addParentsGroup(
            CmnGroupClassModel parModel,
            CmnGroupClassModel mvModel,
        int gSid
        ) throws CloneNotSupportedException {
        if (parModel == null) {
            parModel = new CmnGroupClassModel();
        }
        CmnGroupClassModel gcModel = new CmnGroupClassModel();
        gcModel = (CmnGroupClassModel) parModel.clone();


        ArrayList<Integer> list = mvModel.getGroupSids(gSid);
        for (int i = 0; i < list.size(); i++) {
            if (((Integer) list.get(i)).intValue() != -1) {
                log__.debug("移動するグループSID ==>" + (Integer) list.get(i));
                gcModel.addUnder(((Integer) list.get(i)).intValue());
            }
        }
        return gcModel;
    }
}