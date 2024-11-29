package jp.groupsession.v2.cmn.cmn132;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupMsModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.usr040.Usr040DspModel;

import org.apache.commons.beanutils.BeanUtils;

/**
 * <br>[機  能] メイン 個人設定 共有マイグループ確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn132Biz {
    /** GSメッセージ */
    public GsMessage gsMsg__ = new GsMessage();
    /**
     * <p>コンストラクタ
     * @param gsMsg GsMessage
     */
    public Cmn132Biz(GsMessage gsMsg) {
        gsMsg__ = gsMsg;
    }
    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn132Model パラメータ格納モデル
     * @param con コネクション
     * @param sessionUserSid ユーザーSID
     * @throws SQLException SQL実行例外
     * @throws Exception ユーザ情報データコピー時の例外
     */
    public void setInitData(Cmn132ParamModel cmn132Model, Connection con,
            int sessionUserSid) throws SQLException, Exception {

        /** マイグループSIDからマイグループ情報を取得する **************************/
        int groupSid = NullDefault.getInt(cmn132Model.getCmn130selectGroupSid(), -1);
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        CmnMyGroupModel cmgMdl = cmgDao.getMyGroupInfo(groupSid);
        if (cmgMdl == null) {
            return;
        }
        //マイグループ名
        cmn132Model.setCmn131name(cmgMdl.getMgpName());
        //メモ
        cmn132Model.setCmn131memo(StringUtilHtml.transToHTmlPlusAmparsant(cmgMdl.getMgpMemo()));


        /** マイグループSIDからマイグループ情報明細を取得する************************/
        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        List<CmnMyGroupMsModel> cmgmList = cmgmDao.getMyGroupMsInfo(groupSid);

        String[] userSid = new String[cmgmList.size()];
        for (int i = 0; i < cmgmList.size(); i++) {
            CmnMyGroupMsModel cmgmMdl = cmgmList.get(i);
            userSid[i] = String.valueOf(cmgmMdl.getMgmSid());
        }


        UserBiz usrBiz = new UserBiz();

        List <CmnUsrmInfModel> userDataList = usrBiz.getUserList(con, userSid);

        cmn132Model.setCmn131knMemberList(
                __getDspMdlList(con, userDataList)
                );


        int usrSid = cmgMdl.getUsrSid();
        List <CmnUsrmInfModel> ownerDataList =
                usrBiz.getUserList(con, new String[] {String.valueOf(usrSid)});
        if (ownerDataList.size() != 0) {
            CmnUsrmInfModel owner = ownerDataList.get(0);
            cmn132Model.setCmn132owner(owner);
        } else {
            CmnUsrmInfModel owner = new CmnUsrmInfModel();
            cmn132Model.setCmn132owner(owner);
        }
    }
    /**
     * <br>[機  能] ユーザリストに所属グループ情報を付加する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param ulist ユーザリスト
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     * @return 表示リスト
     */
    private ArrayList<Usr040DspModel> __getDspMdlList(Connection con, List<CmnUsrmInfModel> ulist)
            throws SQLException, Exception {

        ArrayList<Usr040DspModel> ret = new ArrayList<Usr040DspModel>();
        Usr040DspModel dspMdl = null;
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> gpList = null;
        if (sortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_SET) {
            gpList = dao.getGroupTree(sortMdl);
        } else {
            gpList = dao.getGroupList(sortMdl);
        }

        for (CmnUsrmInfModel mdl : ulist) {
            dspMdl = new Usr040DspModel();

            //CmnUsrmInfModel → Usr040DspModel パラメータのコピー
            BeanUtils.copyProperties(dspMdl, mdl);
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongList = belongDao.selectUserBelongGroupSid(mdl.getUsrSid());
            ArrayList<GroupModel> groupList = new ArrayList<GroupModel>();

            for (GroupModel gpMdl : gpList) {
                if (belongList.indexOf(new Integer(gpMdl.getGroupSid())) >= 0) {
                    groupList.add(gpMdl);
                }

            }
            dspMdl.setBelongGrpList(groupList);
            ret.add(dspMdl);
        }

        return ret;
    }
}
