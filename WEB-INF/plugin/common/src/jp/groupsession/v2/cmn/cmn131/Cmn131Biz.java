package jp.groupsession.v2.cmn.cmn131;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupShareDao;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupMsModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupShareModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
/**
 * <br>[機  能] メイン 個人設定 マイグループ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn131Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn131Biz.class);

    /** 登録モード */
    public static final String MODE_ADD = "0";
    /** 編集モード */
    public static final String MODE_EDIT = "1";
    /** GSメッセージ */
    public GsMessage gsMsg__ = new GsMessage();

    /**
     * <p>コンストラクタ
     * @param gsMsg GsMessage
     */
    public Cmn131Biz(GsMessage gsMsg) {
        gsMsg__ = gsMsg;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     * @param con コネクション
     * @param sessionUserSid ユーザーSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cmn131ParamModel cmn131Model, Connection con,
            int sessionUserSid)
    throws SQLException {

        /** 画面タイトルセット ****************************************************/
        cmn131Model.setCmn131dspTitle(
                getDspTitle(NullDefault.getString(cmn131Model.getCmn130cmdMode(), "")));

        /**マイグループリストをセットする*******/
        //グループが未選択の場合、デフォルトグループを設定
        int groupSid = NullDefault.getInt(cmn131Model.getCmn131groupSid(), -1);
        if (groupSid == -1) {
            GroupBiz grpBz = new GroupBiz();
            int defGrp = grpBz.getDefaultGroupSid(sessionUserSid, con);
            cmn131Model.setCmn131groupSid(String.valueOf(defGrp));
        }

        //グループが未選択の場合、デフォルトグループを設定
        int refGroupSid = NullDefault.getInt(cmn131Model.getCmn131refGroupSid(), -1);
        if (refGroupSid == -1) {
            GroupBiz grpBz = new GroupBiz();
            int defGrp = grpBz.getDefaultGroupSid(sessionUserSid, con);
            cmn131Model.setCmn131refGroupSid(String.valueOf(defGrp));
        }
    }

    /**
     * <br>[機  能] 画面タイトルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmdMode 処理モード
     * @return String 画面タイトル
     */
    public String getDspTitle(String cmdMode) {

        //マイグループ登録
        String textMyGroupAdd = gsMsg__.getMessage("cmn.mygroup.add");
        //マイグループ編集
        String textMyGroupEdit = gsMsg__.getMessage("cmn.mygroup.edit");

        String title = textMyGroupAdd;
        if (cmdMode.equals(MODE_ADD)) {
            title = textMyGroupAdd;
        } else if (cmdMode.equals(MODE_EDIT)) {
            title = textMyGroupEdit;
        }

        return title;
    }

    /**
     * <br>[機  能] グループ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setGroupInfo(Cmn131ParamModel cmn131Model, Connection con) throws SQLException {

        //処理モード
        String cmdMode = NullDefault.getString(cmn131Model.getCmn130cmdMode(), "");
        if (cmdMode.equals(MODE_ADD) || cmdMode.equals("")
        || cmn131Model.getCmn131initFlg() == 1) {
            //登録モード、または初期表示以外の場合は取得しない
            return;
        }

        /** マイグループSIDからマイグループ情報を取得する **************************/
        int groupSid = NullDefault.getInt(cmn131Model.getCmn130selectGroupSid(), -1);
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        CmnMyGroupModel cmgMdl = cmgDao.getMyGroupInfo(groupSid);
        if (cmgMdl == null) {
            return;
        }
        //マイグループ名
        cmn131Model.setCmn131name(cmgMdl.getMgpName());
        //メモ
        cmn131Model.setCmn131memo(cmgMdl.getMgpMemo());


        /** マイグループSIDからマイグループ情報明細を取得する************************/
        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        List<CmnMyGroupMsModel> cmgmList = cmgmDao.getMyGroupMsInfo(groupSid);

        String[] userSid = new String[cmgmList.size()];
        for (int i = 0; i < cmgmList.size(); i++) {
            CmnMyGroupMsModel cmgmMdl = cmgmList.get(i);
            userSid[i] = String.valueOf(cmgmMdl.getMgmSid());
        }
        //現在選択中のメンバー(コンボ表示に使用する値)へセット
        cmn131Model.setCmn131userSid(userSid);

        CmnMyGroupShareDao cmgsDao = new CmnMyGroupShareDao(con);
        List<CmnMyGroupShareModel> cmgsList = cmgsDao.select(groupSid);
        //共有設定をセット
        List<String> usergroupList = new ArrayList<String>();
        for (CmnMyGroupShareModel cmgsMdl : cmgsList) {
            if (cmgsMdl.getMgsUsrSid() > GSConstUser.USER_RESERV_SID) {
                usergroupList.add(String.valueOf(cmgsMdl.getMgsUsrSid()));
            } else if (cmgsMdl.getMgsGrpSid() >= 0) {
                usergroupList.add("G" + String.valueOf(cmgsMdl.getMgsGrpSid()));
            }
        }
        cmn131Model.setCmn131refUserSid(usergroupList.toArray(new String[usergroupList.size()]));

        cmn131Model.setCmn131initFlg(1);
    }

    /**
     * <br>[機  能] コンボで選択中のメンバーをメンバーリストから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     */
    public void deleteMnb(Cmn131ParamModel cmn131Model) {

        //コンボで選択中
        String[] selectUserSid = cmn131Model.getCmn131selectUserSid();
        //メンバーリスト(コンボ表示に使用する値)
        String[] userSid = cmn131Model.getCmn131userSid();

        CommonBiz biz = new CommonBiz();
        cmn131Model.setCmn131userSid(biz.getDeleteMember(selectUserSid, userSid));
    }

    /**
     * <br>[機  能] 追加用メンバーコンボで選択中のメンバーをメンバーリストに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     */
    public void addMnb(Cmn131ParamModel cmn131Model) {

        //追加用メンバー(選択中)
        String[] addUserSid = cmn131Model.getCmn131addUserSid();
        //メンバーリスト(コンボ表示に使用する値)
        String[] userSid = cmn131Model.getCmn131userSid();

        CommonBiz biz = new CommonBiz();
        cmn131Model.setCmn131userSid(biz.getAddMember(addUserSid, userSid));
    }

    /**
     * <br>[機  能] 追加参照権限のコンボで選択中のメンバーを追加参照権限リストから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     */
    public void deleteRefMnb(Cmn131ParamModel cmn131Model) {

        //コンボで選択中
        String[] selectUserSid = cmn131Model.getCmn131refSelectUserSid();
        //メンバーリスト(コンボ表示に使用する値)
        String[] userSid = cmn131Model.getCmn131refUserSid();

        CommonBiz biz = new CommonBiz();
        cmn131Model.setCmn131refUserSid(biz.getDeleteMember(selectUserSid, userSid));
    }

    /**
     * <br>[機  能] 追加参照権限の追加用メンバーコンボで選択中のメンバーを追加参照権限リストに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     */
    public void addRefMnb(Cmn131ParamModel cmn131Model) {

        //追加用メンバー(選択中)
        String[] addUserSid = cmn131Model.getCmn131refAddUserSid();
        //メンバーリスト(コンボ表示に使用する値)
        String[] userSid = cmn131Model.getCmn131refUserSid();

        cmn131Model.setCmn131refUserSid(__getAddGrpMember(addUserSid, userSid));
    }

    /**
     * <br>[機  能] 削除するマイグループ名を取得する
     * <br>[解  説] 複数存在する場合は改行を挿入する
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     * @param con コネクション
     * @return String 削除するマイグループ名
     * @throws SQLException SQL実行例外
     */
    public String getDeleteGroupName(Cmn131ParamModel cmn131Model,
                                                   Connection con) throws SQLException {

        //マイグループSIDを取得
        String delGroupSid = cmn131Model.getCmn130selectGroupSid();

        //マイグループSID(複数)からマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        CmnMyGroupModel cmgMdl = cmgDao.getMyGroupInfo(Integer.valueOf(delGroupSid));

        //マイグループ名取得
        StringBuilder deleteGroup = new StringBuilder();
        deleteGroup.append("・" + NullDefault.getString(cmgMdl.getMgpName(), ""));

        return deleteGroup.toString();
    }

    /**
     * <br>[機  能] 選択されたマイグループを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131Model パラメータ格納モデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(Cmn131ParamModel cmn131Model, Connection con) throws SQLException {

        //マイグループSIDを取得
        String[] delGroupSid = {cmn131Model.getCmn130selectGroupSid()};

        boolean commit = false;
        try {
            con.setAutoCommit(false);

            //マイグループSID(複数)を指定してマイグループを削除する
            MyGroupDao mgDao = new MyGroupDao(con);
            mgDao.deleteMyGroup(delGroupSid);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.warn("マイグループ削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する(グループ一覧を含む)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private ArrayList <LabelValueBean> __getGroupLabelList(Connection con) throws SQLException {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        LabelValueBean labelBean = new LabelValueBean();
        labelBean.setLabel(gsMsg__.getMessage("cmn.grouplist"));
        labelBean.setValue(String.valueOf(GSConstCommon.GRP_SID_GRPLIST));
        labelList.add(labelBean);

        //グループリスト取得
        GroupBiz gBiz = new GroupBiz();
        ArrayList <GroupModel> gpList = gBiz.getGroupCombList(con);

        GroupModel gpMdl = null;
        for (int i = 0; i < gpList.size(); i++) {
            gpMdl = gpList.get(i);
            labelList.add(
                    new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }
        log__.debug("labelList.size()=>" + labelList.size());
        return labelList;
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のグループまたはメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    private String[] __getAddGrpMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] ログ用 メッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param delGroupSidList SIDリスト
     * @param con コネクション
     * @return ログメッセージ
     * @throws SQLException SQL実行例外
     */
    public String getLogMessage(String[] delGroupSidList,
            Connection con) throws SQLException {

        String msg = "";
        //マイグループSID(複数)を指定してマイグループを削除する
        CmnMyGroupDao mgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> grpList =
                mgDao.getMyGroupListFromGroupSid(delGroupSidList);
        for (CmnMyGroupModel mdl : grpList) {
            if (msg.length() > 0) {
                msg += "\r\n";
            }
            msg += mdl.getMgpName();
        }
        return msg;
    }

    /**
     * <br>[機  能] マイグループの編集権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn131model Cmn131ParamModel
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @return 編集権限 true:あり false:なし
     * @throws SQLException SQL実行例外
     */
    public boolean checkEditKengen(
        Cmn131ParamModel cmn131Model,
        Connection con,
        int userSid) throws SQLException {

        String cmdMode = cmn131Model.getCmn130cmdMode();
        if (cmdMode.equals(MODE_ADD)) {
            //登録モードの場合、編集権限チェックを行わない
            return true;
        }

        CmnMyGroupDao dao = new CmnMyGroupDao(con);
        int groupSid = NullDefault.getInt(cmn131Model.getCmn130selectGroupSid(), -1);
        CmnMyGroupModel mdl = dao.select(userSid, groupSid);
        if (mdl == null) {
            return false;
        }
        return true;
    }
}
