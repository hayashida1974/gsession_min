package jp.groupsession.v2.wml.batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.model.PushRequestModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;

/**
 * <br>[機  能] WEBメール プッシュ通知用クラス
 * <br>[解  説] 別スレッドで実行する
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class WmlSendPushThread extends Thread {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlSendPushThread.class);


    /** アカウントSID */
    private int wacSid__;
    /** リクエスト情報 */
    private RequestModel reqMdl__;
    /** プッシュ通知情報 */
    private List<PushRequestModel> pushMdlList__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    @SuppressWarnings("all")
    private WmlSendPushThread() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param pushMdlList プッシュ情報
     */
    public WmlSendPushThread(int wacSid, RequestModel reqMdl, List<PushRequestModel> pushMdlList) {
        wacSid__ = wacSid;
        reqMdl__ = reqMdl;
        pushMdlList__ = pushMdlList;
    }

    /**
     * <br>[機  能] プッシュ通知送信スレッドを開始する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param reqMdl リクエスト情報
     * @param pushMdlList プッシュ情報
     */
    public static void startThread(int wacSid, RequestModel reqMdl, List<PushRequestModel> pushMdlList) {
        try {
            WmlSendPushThread thread = new WmlSendPushThread(wacSid, reqMdl, pushMdlList);
            thread.start();
        } catch (Throwable e) {
            log__.error("WEBメール プッシュ通知送信スレッド開始時に例外発生", e);
        } finally {

        }

    }

    /**
     * <br>[機  能] Thread処理実行
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {

        //スレッド名を設定
        Thread.currentThread().setName("WMLSendPush"
                + "-" + System.currentTimeMillis()
                + "-" + reqMdl__.getDomain()
                + "-" + Thread.currentThread().getId());

        log__.info("********** WMLSendPushThread start sid = " + wacSid__);

        Connection con = null;
        try {
            con = GroupSession.getConnection(reqMdl__.getDomain(), 1000);
            IPushServiceOperator psOpe = PushServiceOperator.getInstance(
                    con, reqMdl__.getDomain());

            psOpe.sendMessage(con, reqMdl__, pushMdlList__, GSConst.PLUGINID_WML);
        } catch (SQLException e) {
            log__.error("WEBメール プッシュ通知送信時に例外発生", e);
        } catch (InterruptedException e) {
            log__.error("WEBメール プッシュ通知送信時にThread処理で例外発生", e);
        } catch (Throwable e) {
            log__.error("Exception", e);
        } finally {
            JDBCUtil.closeConnection(con);

            try {
                //スレッド名に"END-"を設定する
                Thread.currentThread().setName("END-" + Thread.currentThread().getName());
            } catch (Throwable e) {
            }
        }
        
        log__.info("********** WMLSendPushThread end sid = " + wacSid__);
        
    }

}
