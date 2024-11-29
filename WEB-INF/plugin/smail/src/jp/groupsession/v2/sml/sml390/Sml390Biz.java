package jp.groupsession.v2.sml.sml390;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBanDestConfDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBanDestPermitDao;
import jp.groupsession.v2.sml.model.SmlBanDestConfModel;
import jp.groupsession.v2.sml.model.SmlBanDestModel;
import jp.groupsession.v2.sml.model.SmlBanDestPermitModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] 送信先制限設定 登録編集画面　ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml390Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml390Biz.class);
    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Sml390ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {

        //初期表示
        if (paramMdl.getSml390initFlg() == GSConstSmail.DSP_FIRST) {
            int sbcSid = paramMdl.getSml380EditBan();
            if (sbcSid > 0) {
                //編集
                SmlBanDestDao sbdDao = new SmlBanDestDao(con);
                List<SmlBanDestModel> sbdList =  sbdDao.getBanDestList(sbcSid);
                SmlBanDestPermitDao sbpDao = new SmlBanDestPermitDao(con);
                List<SmlBanDestPermitModel> sbpList =  sbpDao.getSmlBanDestPermitList(sbcSid);
                List<String> sbdUsrList = new ArrayList<String>();
                List<String> sbdAccList = new ArrayList<String>();
                List<String> sbpIdList = new ArrayList<String>();

                for (SmlBanDestModel sbd : sbdList) {
                    if (sbd.getSbdTargetKbn() == GSConstSmail.SBD_TARGET_KBN_USER) {
                        sbdUsrList.add(
                                String.valueOf(sbd.getSbdTargetSid()));
                    }
                    if (sbd.getSbdTargetKbn() == GSConstSmail.SBD_TARGET_KBN_GROUP) {
                        sbdUsrList.add(
                                "G" + String.valueOf(sbd.getSbdTargetSid()));

                    }
                    if (sbd.getSbdTargetKbn() == GSConstSmail.SBD_TARGET_KBN_ACCOUNT) {
                        sbdAccList.add(String.valueOf(sbd.getSbdTargetSid()));
                    }
                }
                for (SmlBanDestPermitModel sbp : sbpList) {
                    if (sbp.getSbpTargetKbn() == GSConstSmail.SBP_TARGET_KBN_USER) {
                        sbpIdList.add(String.valueOf(sbp.getSbpTargetSid()));
                    }
                    if (sbp.getSbpTargetKbn() == GSConstSmail.SBP_TARGET_KBN_GROUP) {
                        sbpIdList.add("G" + String.valueOf(sbp.getSbpTargetSid()));
                    }
                    if (sbp.getSbpTargetKbn() == GSConstSmail.SBP_TARGET_KBN_POST) {
                        paramMdl.setSml390post(sbp.getSbpTargetSid());
                    }
                }
                SmlBanDestConfDao sbcDao = new SmlBanDestConfDao(con);
                SmlBanDestConfModel sbcMdl = sbcDao.select(sbcSid);
                if (sbcMdl != null) {
                    paramMdl.setSml390sbdTarget(sbdUsrList.toArray(new String[sbdUsrList.size()]));
                    paramMdl.setSml390sbdTargetAcc(
                            sbdAccList.toArray(new String[sbdAccList.size()]));
                    paramMdl.setSml390sbpTarget(sbpIdList.toArray(new String[sbpIdList.size()]));
                    paramMdl.setSml390sbcName(sbcMdl.getSbcName());
                    paramMdl.setSml390biko(sbcMdl.getSbcBiko());
                }
            }
            paramMdl.setSml390initFlg(GSConstWebmail.DSP_ALREADY);
        }
        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.grouplist"),
                                                String.valueOf(Sml390Form.GROUP_COMBO_VALUE)));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }


        //グループが未選択の場合、デフォルトグループを設定
        int defGrp = paramMdl.getSml390banGroup();
        if (defGrp == -1) {
            GroupBiz grpBz = new GroupBiz();
            defGrp = grpBz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setSml390banGroup(defGrp);
        }
        defGrp = paramMdl.getSml390ableGroup();
        if (defGrp == -1) {
            GroupBiz grpBz = new GroupBiz();
            defGrp = grpBz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setSml390ableGroup(defGrp);
        }

        CmnPositionDao pdao = new CmnPositionDao(con);
        List<CmnPositionModel> plist = pdao.getPosListSort(false);
        List<LabelValueBean> pcomb = new ArrayList<LabelValueBean>();
        pcomb.add(new LabelValueBean(
                gsMsg.getMessage("cmn.notset"),
                String.valueOf(-1)
                ));
        for (CmnPositionModel pModel : plist) {
            pcomb.add(new LabelValueBean(
                    pModel.getPosName() + " " + gsMsg.getMessage("ntp.66"),
                    String.valueOf(pModel.getPosSid())
                    ));
        }
        paramMdl.setPostCombo(pcomb);
        //制限する送信先を設定
        _setSelectBanTarget(con, paramMdl);

        //使用許可を設定
        _setSelectAble(con, paramMdl);
    }
    /**
     *
     * <br>[機  能] 制限する送信先を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    protected void _setSelectBanTarget(Connection con,
            Sml390ParamModel paramMdl) throws SQLException {
        UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
        paramMdl.setSml390sbdTargetSelectCombo(
                selBiz.getSelectedLabel(paramMdl.getSml390sbdTarget(), con));
        //代表アカウント情報取得
        SmlAccountDao adao = new SmlAccountDao(con);
        List<LabelValueBean> alist = adao.selectSacSids(
                paramMdl.getSml390sbdTargetAcc(),
                SmlAccountDao.JKBN_LIV);
        paramMdl.setSml390sbdTargetAccSelectCombo(alist);


    }
    /**
     *
     * <br>[機  能] 送信許可を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    protected void _setSelectAble(Connection con,
            Sml390ParamModel paramMdl) throws SQLException {

        paramMdl.setSml390sbpTargetSelectCombo(
                __getAbleUserLabelList(con, paramMdl.getSml390sbpTarget()));

    }

    /**
     * <br>[機  能] ユーザ、グループ、役職情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userList 取得するユーザSID・グループSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getAbleUserLabelList(Connection con, String[] userList)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();
        ArrayList<String> pstSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (userList != null) {
            for (String userId : userList) {
                String str = NullDefault.getString(userId, "-1");
                log__.debug("str==>" + str);
                log__.debug("G.index==>" + str.indexOf("G"));
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                } else if (str.contains(new String("P").subSequence(0, 1))) {
                    //役職
                    pstSids.add(str.substring(1, str.length()));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }
        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        UsrLabelValueBean label = null;
        for (GroupModel gmodel : glist) {
            label = new UsrLabelValueBean();
            label.setLabel(gmodel.getGroupName());
            label.setValue("G" + String.valueOf(gmodel.getGroupSid()));
            ret.add(label);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            ret.add(new UsrLabelValueBean(umodel));
        }
        return ret;
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
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
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

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
     * <br>[機  能] 送信先リストの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteDestList(Connection con, Sml390ParamModel paramMdl)
    throws SQLException {
        SmlBanDestConfDao sbcDao = new SmlBanDestConfDao(con);
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        SmlBanDestPermitDao sbpDao = new SmlBanDestPermitDao(con);
        int sbcSid = paramMdl.getSml380EditBan();
        if (sbcSid > 0) {
            sbcDao.delete(sbcSid);
            sbpDao.delete(sbcSid);
            sbdDao.delete(sbcSid);
        }
    }
}
