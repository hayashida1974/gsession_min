package jp.groupsession.v2.wml.listener;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.batch.IBatchBackupListener;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.wml.dao.base.WmlDelmailListDao;
import jp.groupsession.v2.wml.pop3.Pop3Server;
import jp.groupsession.v2.wml.wml320kn.WmlDeleteMailThread;

/**
 * <br>[機  能] バックアップ処理時に実行されるリスナーを実装
 * <br>[解  説] 自動バックアップについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlBatchBackupListenerImpl implements IBatchBackupListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlBatchListenerImpl.class);

    /**
     * <p>バックアップ処理の前に実行されるJob
     * @param con DBコネクション
     * @param param バックアップ前処理で使用する情報
     * @param domain ドメイン
     * @throws Exception バックアップ前処理実行時例外
     */
    public void doBeforeBackup(Connection con, Object param, String domain) throws Exception {
        Pop3Server.stopReceive();
        while (Pop3Server.existReceiveAccount(domain)) {
            Thread.sleep(3000);
        }
    }

    /**
     * <p>バックアップ処理
     * @param con DBコネクション
     * @param param バックアップ処理時に使用する情報
     * @throws Exception バックアップ処理実行時例外
     */
    public void doBackup(Connection con, Object param) throws Exception {
    }

    /**
     * <p>バックアップ処理終了後に実行されるJob
     * @param con DBコネクション
     * @param param バックアップ終了後処理で使用する情報
     * @param domain ドメイン
     * @throws Exception バックアップ終了後処理実行時例外
     */
    public void doAfterBackup(Connection con, Object param, String domain) throws Exception {
        Pop3Server.startReceive();

        //中断したメール情報一括削除処理を再開する
        try {
            WmlDelmailListDao delListDao = new WmlDelmailListDao(con);
            List<Integer> wacSidList = delListDao.getSuspendedAccount();
            if (!wacSidList.isEmpty()) {
                String appRootPath = (String) ((GSContext) param).get(GSContext.APP_ROOT_PATH);
                for (int wacSid : wacSidList) {
                    WmlDeleteMailThread.startThread(domain, wacSid, appRootPath,
                                                    WmlDeleteMailThread.STARTSTATUS_RESTART);
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            log__.error("WEBメール バックアップ完了後のメール情報一括削除再開処理に失敗", e);
        }
    }
}