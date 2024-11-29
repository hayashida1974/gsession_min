package jp.groupsession.v2.rng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.biz.RngDoNextBiz;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.model.RngSingiModel;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] 稟議についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngIUserGroupListenerImpl implements IUserGroupListener {

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param con DBコネクション
     * @param cntCon MlCountMtController
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon,
                        Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

        try {
            //承認者の処理
            RingiDao dao = new RingiDao(con);
            List<RingiDataModel> proRngSidList = dao.getProgressRingiSidList(usid);
            int rngSid = 0;
            UDate now = new UDate();
            RngKeiroStepDao kDao = new RngKeiroStepDao(con);
            RngSingiDao sDao = new RngSingiDao(con);
            MlCountMtController cntCon =
                    GroupSession.getResourceManager().getCountController(reqMdl);
            PluginConfig pconfig = GroupSession.getResourceManager().getPluginConfig(reqMdl);
            CommonBiz cmnBiz = new CommonBiz();
            boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
            String rootPath = "";
            GSContext gscontext = GroupSession.getContext();
            Object pathObj = gscontext.get(GSContext.APP_ROOT_PATH);
            if (pathObj != null) {
                rootPath = (String) pathObj;
            }

            for (int i = 0; i < proRngSidList.size(); i++) {
                rngSid = proRngSidList.get(i).getRngSid();
                RngDoNextBiz nextBiz = new RngDoNextBiz(con, reqMdl, sDao, kDao, rngSid);
                int rksSid = sDao.getRksSid(rngSid, usid, RngConst.RNG_RNCSTATUS_CONFIRM);

                int sinkouFlg = kDao.getNoUser(rksSid);
                if (sinkouFlg == 0 && proRngSidList.get(i).getRngCompflg() == 0) {
                    //稟議審議情報の更新
                    RngSingiModel singiMdl = new RngSingiModel();
                    singiMdl.setRngSid(rngSid);
                    singiMdl.setUsrSid(usid);
                    singiMdl.setRksSid(rksSid);
                    singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
                    singiMdl.setRssEuid(usid);
                    singiMdl.setRssEdate(now);
                    sDao.updateSingi(singiMdl);
                    nextBiz.doNext(cntCon, rksSid, rootPath, pconfig,
                            smailPluginUseFlg, usid, 1, true);
                }
            }

            //最終確認者
            List<RingiDataModel> rngSidList = dao.getConfirmRingiSidList(usid);
            for (int i = 0; i < rngSidList.size(); i++) {
                rngSid = rngSidList.get(i).getRngSid();
                //稟議審議情報の更新
                RngSingiDao singiDao = new RngSingiDao(con);
                RngSingiModel singiMdl = new RngSingiModel();
                singiMdl.setRssEdate(now);
                singiMdl.setUsrSid(usid);
                singiMdl.setRngSid(rngSid);
                singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRMATION);
                singiDao.updateConfirm(singiMdl);

                RngKeiroStepDao keiroDao = new RngKeiroStepDao(con);
                RngKeiroStepModel keiroMdl = new RngKeiroStepModel();
                keiroMdl.setRksEdate(now);
                List<Integer> rksList = singiDao.checkConfirm(rngSid, usid);
                if (rksList.size() > 0) {
                    keiroMdl.setRksChkdate(now);
                    keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRMATION);
                    keiroDao.updateConfirm(keiroMdl, rksList);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <br>[機  能] グループ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid, int eusid) throws SQLException {
    }

    /**
     * <br>[機  能] グループ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(
            Connection con, int gsid, int eusid, RequestModel reqMdl) throws SQLException {

    }

    /**
     * <br>[機  能] ユーザの所属グループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids, int eusid)
    throws SQLException {
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid, int eusid)
    throws SQLException {
    }
}
