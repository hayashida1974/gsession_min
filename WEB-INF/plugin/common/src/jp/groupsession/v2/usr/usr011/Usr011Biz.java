package jp.groupsession.v2.usr.usr011;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 グループ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr011Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr011Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr011Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 選択可能レベルを取得します。
     * @param gSid グループSID
     * @param con コネクション
     * @return String
     * @throws SQLException SQL実行時例外
     */
    public String getSelectLevel(int gSid, Connection con) throws SQLException {
        String ret = null;
        if (gSid == 0) {
            ret = "0";
            return ret;
        }

        try {
            GroupDao dao = new GroupDao(con);
            CmnGroupClassModel model = null;
            int uLv = -1;
            int myLv = -1;
            ArrayList<CmnGroupClassModel> list = dao.getUnderGroupClassList(gSid);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    model = (CmnGroupClassModel) list.get(i);
                    if (i == 0) {
                        myLv = model.getClassLevel(gSid);
                    }
                    if (uLv < model.getUnderGroupLevel()) {
                        uLv = model.getUnderGroupLevel();
                    }
                }
            }
            ret = (Integer.valueOf(10 - (uLv - myLv + 1))).toString();
        } catch (SQLException e) {
            log__.error("SQLエラー", e);
        }
        return ret;
    }

    /**
     * 選択不可能グループSIDリストを取得します。
     * @param gSid グループSID
     * @param con コネクション
     * @return ArrayList 選択不可能グループSIDリスト
     */
    public ArrayList<Integer> getDisabledGroups(int gSid, Connection con) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (gSid == 0) {
            ret.add(Integer.valueOf(-1));
        }

        try {
            GroupDao dao = new GroupDao(con);
            ArrayList<CmnGroupClassModel> list = dao.getUnderGroupClassList(gSid);
            CmnGroupClassModel model = null;
            for (int i = 0; i < list.size(); i++) {
                model = (CmnGroupClassModel) list.get(i);
                ret.add(Integer.valueOf(model.getUnderGroup()));
                log__.debug("選択不可能グループSID = " + model.getUnderGroup());
            }
            //管理者グループへは階層追加不可
            ret.add(Integer.valueOf(0));
            log__.debug("選択不可能グループSID = " + Integer.valueOf(0));
        } catch (SQLException e) {
            log__.error("SQLエラー", e);
        }
        return ret;
    }

    /**
     * グループコンボを作成します。
     * @param paramMdl Usr011ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void getGroupComb(Usr011ParamModel paramMdl,
                              Connection con)
    throws SQLException {

        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }
        //フォームにセット
        paramMdl.setGroupCombo(groupCombo);

    }

    /**
     * デフォルトグループに設定しているユーザを所属ユーザに設定
     * @param paramMdl Usr011ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setDefaultBelongUser(
        Usr011ParamModel paramMdl,
        Connection con) throws SQLException {

        List<String> belongUsers = new ArrayList<String>();
        if (paramMdl.getUsers_l() != null && paramMdl.getUsers_l().length > 0) {
            belongUsers.addAll(Arrays.asList(paramMdl.getUsers_l()));
        }
        CmnBelongmDao dao = new CmnBelongmDao(con);
        List<Integer> defUserSids = dao.selectBelongDefaultUserSid(paramMdl.getUsr010grpSid());

        for (int defUid : defUserSids) {
            if (!belongUsers.contains(String.valueOf(defUid))) {
                belongUsers.add(String.valueOf(defUid));
            }
        }
        paramMdl.setDefGroupUserSidList(defUserSids);
        paramMdl.setUsers_l(belongUsers.toArray(new String[0]));
    }
}