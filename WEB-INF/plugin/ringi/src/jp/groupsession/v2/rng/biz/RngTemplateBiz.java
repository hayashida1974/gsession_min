package jp.groupsession.v2.rng.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;

/**
 * <br>[機  能] 稟議のテンプレート機能で使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTemplateBiz {

    /**
     * <br>[機  能] 指定したテンプレートSIDのテンプレート情報を返します
     * <br>[解  説]
     * <br>[備  考]
     * @param tplSid テンプレートSID
     * @param con Connection
     * @return テンプレートモデル
     * @throws SQLException SQL実行時例外
     */
    public RngTemplateModel getRtpModel(int tplSid, Connection con) throws SQLException {
        RngTemplateDao dao = new RngTemplateDao(con);
        RngTemplateModel rtModel = new RngTemplateModel();

        rtModel = dao.select(tplSid);
        return rtModel;
    }

    /**
     * <br>[機  能]経路情報からその経路にユーザが存在するかの判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param keiro Rng110KeiroDialogParamModel
     * @param con Connection
     * @param reqMdl リクエストモデル
     * @return true:ユーザが存在する false:ユーザが存在しない
     * @throws SQLException SQLException
     */
    public boolean keiroUserCheck(Rng110KeiroDialogParamModel keiro,
            Connection con,
            RequestModel reqMdl)
            throws SQLException {

        boolean ret = false;
        ArrayList<Integer> userList = new ArrayList<Integer>();
        RngBiz rngBiz = new RngBiz(con);
        switch (keiro.getKeiroKbn()) {
        case EnumKeiroKbn.FREESET_VAL:
            ret = true;
            break;
        case EnumKeiroKbn.USERTARGET_VAL:
            UserGroupSelectBiz tUgsBiz = new UserGroupSelectBiz();
            ArrayList<String> tSids = new ArrayList<String>();
            for (String[] value : keiro.getUsrgrouptarget().getSelected().values()) {
                for (int idx = 0; idx < value.length; idx++) {
                    tSids.add(value[idx]);
                }
            }
            userList = tUgsBiz.getSelectedUserLabel(tSids.toArray(new String[tSids.size()]), con);
            if (userList != null && userList.size() > 0) {
                if (rngBiz.getUserList(userList).size() > 0) {
                    return true;
                }
            }
            break;
        case EnumKeiroKbn.USERSEL_VAL:
            ArrayList<String> sSids = new ArrayList<String>();
            for (String[] value : keiro.getUsrgroupselect().getSelected().values()) {
                for (int idx = 0; idx < value.length; idx++) {
                    sSids.add(value[idx]);
                }
            }
            if (sSids.size() == 0) {
                return true;
            }
            UserGroupSelectBiz sUgsBiz = new UserGroupSelectBiz();
            userList = sUgsBiz.getSelectedUserLabel(sSids.toArray(new String[sSids.size()]), con);
            if (userList != null && userList.size() > 0) {
                List<Integer> useSelList = rngBiz.getUserList(userList);
                if (useSelList.size() > 0) {
                    if (keiro.getKeiroKbn() == EnumKeiroKbn.USERSEL_VAL
                            && useSelList.size() == 1
                            && useSelList.get(0) == reqMdl.getSmodel().getUsrsid()
                            && keiro.getOwn() == RngConst.RNG_OWNSINGI_NO) {
                        return false;
                    }
                    return true;
                }
            }
            break;
        case EnumKeiroKbn.GROUPSEL_VAL:
            ArrayList<Integer> nGrpSids = new ArrayList<Integer>();
            for (String value : keiro.getGroupSel().getSelected()) {
                nGrpSids.add(Integer.parseInt(value));
            }
            if (nGrpSids.size() == 0) {
                return true;
            }
            userList = (ArrayList<Integer>) rngBiz.getUserList(nGrpSids, null);
            if (userList != null && userList.size() > 0) {
                List<Integer> useSelList = rngBiz.getUserList(userList);
                if (useSelList.size() > 0) {
                    if (keiro.getKeiroKbn() == EnumKeiroKbn.USERSEL_VAL
                            && useSelList.size() == 1
                            && useSelList.get(0) == reqMdl.getSmodel().getUsrsid()
                            && keiro.getOwn() == RngConst.RNG_OWNSINGI_NO) {
                        return false;
                    }
                    return true;
                }
            }
            break;
        case EnumKeiroKbn.POSTARGET_VAL:
            for (TargetPosSel pos : keiro.getTargetposMap().values()) {
                int grpSid = -1;
                if (pos.getGrpSel().getSelected() != null) {
                    grpSid = Integer.parseInt(pos.getGrpSel().getSelected());
                }
                if (pos.getPosSel().getSelected() != null) {
                    int posSid = NullDefault.getInt(pos.getPosSel().getSelected(), 0);
                    CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                    ArrayList<Integer> usrSids = usrDao.getBelongUsrsFromPosition(grpSid, posSid);
                    if (rngBiz.getUserList(usrSids).size() > 0) {
                        return true;
                    }
                }
            }
            break;
        case EnumKeiroKbn.BOSSTARGET_VAL:
            ret = true;
            break;
        default:
            break;

        }
        return ret;
    }

}
