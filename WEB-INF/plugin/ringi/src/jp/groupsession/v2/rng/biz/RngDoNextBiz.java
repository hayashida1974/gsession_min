package jp.groupsession.v2.rng.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.IRingiListener;
import jp.groupsession.v2.rng.RingiListenerModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.model.RngSingiModel;

/**
 *
 * <br>[機  能] 稟議の審議状態を判定し次の経路ステップへ進めるビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngDoNextBiz {
    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(RngDoNextBiz.class);

    /** コネクション */
    protected Connection con__ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;
    /** 審議DAO*/
    private RngSingiDao singiDao__;
    /** 経路DAO*/
    private RngKeiroStepDao keiroDao__;
    /** 稟議SID*/
    int rngSid__;


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param sDao 審議DAO
     * @param kDao 経路DAO
     * @param rngSid 稟議SID
     */
    public RngDoNextBiz(Connection con, RequestModel reqMdl,
            RngSingiDao sDao, RngKeiroStepDao kDao, int rngSid) {
        con__ = con;
        reqMdl__ = reqMdl;
        singiDao__ = sDao;
        keiroDao__ = kDao;
        rngSid__ = rngSid;
    }

    /**
    *
    * <br>[機  能]後閲指示時の処理
    * <br>[解  説]
    * <br>[備  考]
    * @param cntCon MlCountMtController
    * @param rksSid 経路SID
    * @param appRootPath パス
    * @param pluginConfig プラグインコンフィグ
    * @param smailPluginUseFlg ショートメール使用フラグ
    * @param userSid ユーザSID
    * @throws Exception Exception
    */
   public void doNextKoetu(MlCountMtController cntCon, int rksSid, String appRootPath,
           PluginConfig pluginConfig, boolean smailPluginUseFlg, int userSid) throws Exception {

       log__.info("次ユーザ確認");

       UDate now = new UDate();
       int nextRksSid = rksSid;
       RngKeiroStepModel keirostepMdl = __createKeiroStepModel(nextRksSid, userSid, now);

       do {
           nextRksSid = keiroDao__.lastAuthorizer(keirostepMdl, 1);
           keirostepMdl = __createKeiroStepModel(nextRksSid, userSid, now);
           // 審議なし進行許可フラグをチェック
           int sinkoFlg = keiroDao__.getNoUser(nextRksSid);
           if (sinkoFlg == 1) {
               break;
           }
           //削除ユーザチェック
           boolean delCheack = __deleteUserCheack(nextRksSid, keirostepMdl, userSid);
           //削除時以外で経路状態が未確認以外だった場合ループを抜ける
           if (!delCheack) {
               break;
           }
       } while (__chkGotoNext(keirostepMdl));
       __nextUserConfirm(keirostepMdl, cntCon, nextRksSid,
               appRootPath, pluginConfig, smailPluginUseFlg,
               userSid, 0, false);
   }

    /**
     *
     * <br>[機  能]承認時の登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param rksSid 経路SID
     * @param appRootPath パス
     * @param pluginConfig プラグインコンフィグ
     * @param smailPluginUseFlg ショートメール使用フラグ
     * @param userSid ユーザSID
     * @param delFlg ユーザ削除時
     * @param smlSendFlg ショートメール送信フラグ
     * @throws Exception Exception
     */
    public void doNext(MlCountMtController cntCon, int rksSid, String appRootPath,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg,
            int userSid,
            int delFlg,
            boolean smlSendFlg) throws Exception {

        log__.info("次ユーザ確認");
        //通知リスナーの情報を取得
        RngBiz rngBiz = new RngBiz(con__, cntCon);

        UDate now = new UDate();

        RingiListenerModel listenerMdl = rngBiz.createListenerModel(
                con__, cntCon, rngSid__, appRootPath, pluginConfig, smailPluginUseFlg);

        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);
        int nextRksSid = rksSid;
        RngKeiroStepModel keirostepMdl = __createKeiroStepModel(nextRksSid, userSid, now);
        boolean bNext = false;

        while (__chkGotoNext(keirostepMdl)) {
            //現在審議中
            keirostepMdl.setRksChkdate(now);
            keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
            keiroDao__.updateKeiroStep(keirostepMdl);
            bNext = true;
            // 最終承認者かどうか
            nextRksSid = keiroDao__.lastAuthorizer(keirostepMdl, 1);
            if (nextRksSid == 0) {
                // 最終承認者であれば
                RngRndataDao rngDao = new RngRndataDao(con__);
                RngRndataModel rngData = __createRndataModel(userSid, now);
                rngData.setRngStatus(RngConst.RNG_STATUS_SETTLED);
                rngData.setRngAdmcomment(null);
                rngDao.completeRingi(rngData, true);
                String url = rngBiz.createThreadUrl(reqMdl__, rngSid__);
                //URLを設定
                listenerMdl.setRngUrl(url);

                //最終確認者がいるかチェック
                List<Integer> rksList = keiroDao__.confirmerCheck(rngSid__);
                if (rksList.size() > 0) {
                    //最終確認者の受信日を更新する
                    keiroDao__.updateRcvdateForConfirmUser(rngSid__, userSid, now);
                    singiDao__.updateRcvdateForConfirmUser(rngSid__, userSid, now);

                    //削除ユーザ対応
                    singiDao__.setDeleteUserConf(rksList);
                    List<Integer> kList = singiDao__.checkConfirm(rksList.get(0));
                    if (kList.size() > 0) {
                        RngKeiroStepModel keiroMdl = new RngKeiroStepModel();
                        keiroMdl.setRksEdate(now);
                        keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRMATION);
                        keiroDao__.updateConfirm(keiroMdl, kList);
                    }
                }

                //ショートメール通知
                listenerMdl.setUserSid(rngData.getRngApplicate());
                //稟議承認(完了)通知を行う
                for (IRingiListener listener : listenerList) {
                    listener.sendSmlMain(
                            listenerMdl, reqMdl__, RngConst.STATUS_SOURCE_APPROVAL_COMP_SML);
                }
                return;
            }
            // 審議なし進行許可フラグをチェック
            int sinkoFlg = keiroDao__.getNoUser(nextRksSid);
            if (sinkoFlg == 1) {
                break;
            }
            //削除ユーザチェック
            __deleteUserCheack(nextRksSid, keirostepMdl, userSid);
            keirostepMdl = __createKeiroStepModel(nextRksSid, userSid, now);
        }
        // ログインユーザが最終承認者ではない場合、次の承認者の稟議経路情報を更新する
        if (bNext) {
            __nextUserConfirm(keirostepMdl, cntCon, nextRksSid,
                    appRootPath, pluginConfig, smailPluginUseFlg,
                    userSid, delFlg, smlSendFlg);
        }
    }

    /**
     * <br>[機  能] 稟議情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param keirostepMdl 経路Model
     * @param userSid ユーザSID
     * @return bRtn 経路上ユーザ全削除かどうか
     * @throws SQLException SQLException
     */
    private boolean __deleteUserCheack(int rksSid, RngKeiroStepModel keirostepMdl,
            int userSid) throws SQLException {

        boolean bRtn = false;
        UDate now = new UDate();
        // 次の承認者が削除されていないか確認する
        RngRndataDao rngDao = new RngRndataDao(con__);
        ArrayList<Integer> nextUser = singiDao__.nextUser(rksSid, rngSid__);
        boolean bNextUserDelete = false;
        RngSingiModel singiMdl = new RngSingiModel();

        if (nextUser.size() == 1) {
            bNextUserDelete = rngDao.nextDelete(nextUser.get(0));
            if (bNextUserDelete) {
                keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
                keirostepMdl.setRksRcvdate(now);
                keirostepMdl.setRksChkdate(null);
                keirostepMdl.setRksSid(rksSid);
                keiroDao__.updateApprovalKeiroStep(keirostepMdl);

                singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
                singiMdl.setRssChkdate(null);
                singiMdl.setRssEdate(now);
                singiMdl.setRssEuid(userSid);
                singiMdl.setRksSid(rksSid);
                singiMdl.setRngSid(rngSid__);
                singiDao__.updateApprovalSingi(singiMdl);
                bRtn = true;
            }

        } else {
            //経路の承認者全員が削除されていないかの判定
            boolean groupDelete = true;
            for (int nIdx = 0; nIdx < nextUser.size(); nIdx++) {
                bNextUserDelete = rngDao.nextDelete(nextUser.get(nIdx));
                if (bNextUserDelete) {
                    singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
                    singiMdl.setRssChkdate(null);
                    singiMdl.setRssEdate(now);
                    singiMdl.setRssEuid(userSid);
                    singiMdl.setRksSid(rksSid);
                    singiMdl.setUsrSid(nextUser.get(nIdx));
                    singiDao__.updateSingi(singiMdl);
                } else {
                    groupDelete = false;
                }
            }
            if (groupDelete) {
                keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
                keirostepMdl.setRksRcvdate(now);
                keirostepMdl.setRksChkdate(null);
                keirostepMdl.setRksSid(rksSid);
                keiroDao__.updateApprovalKeiroStep(keirostepMdl);
                bRtn = true;
            }
        }
        return bRtn;
    }

    /**
     * <br>[機  能]  ログインユーザが最終承認者ではない場合、次の承認者の稟議経路情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param keirostepMdl 経路ステップモデル
     * @param cntCon MlCountMtController
     * @param rksSid 経路SID
     * @param appRootPath パス
     * @param pluginConfig プラグインコンフィグ
     * @param smailPluginUseFlg ショートメール使用フラグ
     * @param userSid ユーザSID
     * @param delFlg 削除フラグ
     * @param smlSendFlg ショートメール送信フラグ
     * @throws Exception Exception
     */
    private void __nextUserConfirm(RngKeiroStepModel keirostepMdl, MlCountMtController cntCon,
            int rksSid, String appRootPath,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg,
            int userSid,
            int delFlg,
            boolean smlSendFlg) throws Exception {

        //通知リスナーの情報を取得
        RngBiz rngBiz = new RngBiz(con__, cntCon);
        RingiListenerModel listenerMdl = rngBiz.createListenerModel(
                con__, cntCon, rngSid__, appRootPath, pluginConfig, smailPluginUseFlg);

        UDate now = new UDate();
        //稟議を申請中状態にする

        keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
        keirostepMdl.setRksRcvdate(now);
        keirostepMdl.setRksChkdate(null);
        keirostepMdl.setRksSid(rksSid);
        keiroDao__.updateApprovalKeiroStep(keirostepMdl);

        RngSingiModel sMdl = new RngSingiModel();
        sMdl = __createSingiModel(rksSid, userSid, now);
        sMdl.setRssChkdate(null);
        sMdl.setRksSid(rksSid);

        /*
        その中で複数ユーザ経路かつ一部ユーザが削除されている場合、
        進行許可フラグをみてそのユーザは申請中ではなく承認状態に変更する
        */
        boolean bDelUser = false;
        int sinkoFlg = keiroDao__.getNoUser(rksSid);
        if (sinkoFlg == 0) {
            List<Integer> delList = keiroDao__.getDelUser(rksSid);
            if (delList.size() > 0) {
                sMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
                singiDao__.updateSingiDelUser(sMdl, delList);
                bDelUser = true;
            }
        }
        sMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
        if (delFlg == 1 || bDelUser) {
            singiDao__.updateSingiStautsNoset(sMdl);
        } else {
            singiDao__.updateApprovalSingi(sMdl);
        }

        if (smlSendFlg) {
            String url = rngBiz.createThreadUrl(reqMdl__, rngSid__);
            //URLを設定
            listenerMdl.setRngUrl(url);
            RingiRequestModel model = new RingiRequestModel();
            model.setAppRootPath(appRootPath);
            //稟議リスナーの処理を実行する。
            rngBiz.manageRingiLisner(model, 0, rngSid__, pluginConfig, smailPluginUseFlg, reqMdl__);
        }
    }

    /**
     * <br>[機  能] 稟議情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param now 現在日時
     * @return 稟議情報Model
     */
    private RngRndataModel __createRndataModel(int userSid, UDate now) {
        RngRndataModel rngMdl = new RngRndataModel();
        rngMdl.setRngSid(rngSid__);

        rngMdl.setRngAuid(userSid);
        rngMdl.setRngAdate(now);
        rngMdl.setRngEuid(userSid);
        rngMdl.setRngEdate(now);

        return rngMdl;
    }
    /**
     * <br>[機  能] 稟議経路審議情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param userSid ユーザSID
     * @param now 現在日時
     * @return 稟議経路情報Model
     */
    private RngSingiModel __createSingiModel(int rksSid,
                                                int userSid, UDate now) {
        RngSingiModel singiMdl = new RngSingiModel();
        singiMdl.setRksSid(rksSid);
        singiMdl.setRngSid(rngSid__);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
        singiMdl.setUsrSid(userSid);
        singiMdl.setRssAuid(userSid);
        singiMdl.setRssAdate(now);
        singiMdl.setRssEuid(userSid);
        singiMdl.setRssEdate(now);

        return singiMdl;
    }
    /**
     * <br>[機  能] 稟議経路ステップ情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param userSid ユーザSID
     * @param now 現在日時
     * @return 稟議経路情報Model
     */
    private RngKeiroStepModel __createKeiroStepModel(int rksSid,
                                                int userSid, UDate now) {
        RngKeiroStepModel keiroMdl = new RngKeiroStepModel();
        keiroMdl.setRksSid(rksSid);
        keiroMdl.setRngSid(rngSid__);
        keiroMdl.setRksAuid(userSid);
        keiroMdl.setRksAdate(now);
        keiroMdl.setRksEuid(userSid);
        keiroMdl.setRksEdate(now);

        return keiroMdl;
    }

    /**
     *
     * <br>[機  能] 次の経路に進めるか判定する
     * <br>[解  説]
     * <br>[備  考] 審議データを承認後に呼ぶこと
     * @param keirostepMdl 経路ステップモデル
     * @return 0:承認未完 1:承認完了
     * @throws SQLException SQLException
     */
    private boolean __chkGotoNext(RngKeiroStepModel keirostepMdl)
            throws SQLException {
        boolean bNextKeiro = false;

        // 0:未達成 1:承認達成 2:否認達成
        int nTassei = keiroDao__.checkApproval(keirostepMdl, RngConst.RNG_RNCSTATUS_APPR);
        if (nTassei == 1) {
            bNextKeiro = true;

            //現在の次の経路が未到達経路なのか確認を行う
            int nextRksSid = keiroDao__.lastAuthorizer(keirostepMdl, 1);
            UDate now = new UDate();
            int rksStatus = RngConst.RNG_RNCSTATUS_NOSET;
            if (nextRksSid != 0) {
                rksStatus = keiroDao__.select(nextRksSid).getRksStatus();
            }
            //もし既に到達していた経路の場合
            //（差し戻しか再申請が行われていた場合）
            //その経路状態をを未設定に変更
            if (rksStatus != RngConst.RNG_RNCSTATUS_NOSET) {
                RngKeiroStepModel keiroMdl = __createKeiroStepModel(nextRksSid,
                        keirostepMdl.getRksEuid(), now);
                keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
                keiroDao__.updateApprovalKeiroStep(keiroMdl);
                RngSingiModel singiMdl = __createSingiModel(nextRksSid, 0, now);
                singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_NOSET);
                singiMdl.setUsrSidDairi(-1);
                singiMdl.setUsrSidKoetu(-1);
                singiDao__.updateApprovalSingi(singiMdl);
            }
        }
        return bNextKeiro;
    }
}
