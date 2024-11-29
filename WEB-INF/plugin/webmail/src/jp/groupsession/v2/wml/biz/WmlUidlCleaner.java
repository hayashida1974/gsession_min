package jp.groupsession.v2.wml.biz;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlManageDeluidDao;
import jp.groupsession.v2.wml.dao.base.WmlUidlDao;
import jp.groupsession.v2.wml.dao.base.WmlUidlTempDao;
import jp.groupsession.v2.wml.model.WmlReceiveServerModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlManageDeluidModel;
import jp.groupsession.v2.wml.pop3.Pop3Server;

/**
 *
 * <br>[機  能] 受信済みメールUIDLからメールサーバにないものを削除する
 * <br>[解  説] 日次バッチからcleanメソッドが実行される
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlUidlCleaner {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlUidlCleaner.class);

    /** UIDL削除処理を行うアカウントの上限数 */
    private static final int MAX_DELUID_ACCOUNT_COUNT__ = 200;

    /**
     *
     * <br>[機  能] 受信済みメールUIDLからメールサーバにないものを削除する
     * <br>[解  説] アカウント毎に実行し、削除が5000件以上になる場合は次回に実行する
     * <br>[備  考] 日次バッチからの利用を想定
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    public void clean(Connection con) throws Exception {

        WmlAccountDao accountDao = new WmlAccountDao(con);
        int accountCount = accountDao.getAccountCount();
        log__.debug("受信済みメールUIDL削除 | 全アカウント件数 = " + accountCount);
        if (accountCount == 0) {
            return;
        }

        //UIDL削除の対象となるアカウント情報を取得する
        WmlManageDeluidDao wduDao = new WmlManageDeluidDao(con);
        List<WmlAccountModel> accountList = null;
        if (accountCount <= MAX_DELUID_ACCOUNT_COUNT__) {
            //アカウント件数 <= 削除対象アカウントの上限数の場合、全てのアカウント情報を取得する
            accountList = accountDao.getAccountDataForDelUidl(0, MAX_DELUID_ACCOUNT_COUNT__);
        } else {
            //前回UIDL削除を行ったアカウントを取得する
            int beforeDelWacSid = 0;
            List<WmlManageDeluidModel> wduList = wduDao.select();
            if (wduList != null && !wduList.isEmpty()) {
                beforeDelWacSid = wduList.get(0).getWacSid();
            }
            accountList = accountDao.getAccountDataForDelUidl(
                                    beforeDelWacSid, MAX_DELUID_ACCOUNT_COUNT__);
            log__.debug("受信済みメールUIDL削除 | アカウント一覧取得 1 = " + accountList.size());
            if (accountList.size() < MAX_DELUID_ACCOUNT_COUNT__) {
                accountList.addAll(
                        accountDao.getAccountDataForDelUidl(
                                    0, MAX_DELUID_ACCOUNT_COUNT__ - accountList.size())
                        );
            }
            log__.debug("受信済みメールUIDL削除 | アカウント一覧取得 2 = " + accountList.size());
        }

        //受信済みメールUIDLの削除
        int doDelWacSid = 0;
        for (WmlAccountModel accMdl : accountList) {
            long time = System.currentTimeMillis();
            __cleanAccSingle(con, accMdl);
            doDelWacSid = accMdl.getWacSid();
            log__.info("受信済みメールUIDL削除 | 削除対象アカウント | " + accMdl.getWacName()
                        + " | SID = " + accMdl.getWacSid()
                        + " | Time = " + (System.currentTimeMillis() - time));
        }

        //次回のUIDL削除時、「今回削除したアカウントの次から」削除を開始するために
        //今回最後に削除を行ったアカウントの情報を登録する
        if (accountCount > MAX_DELUID_ACCOUNT_COUNT__) {
            WmlManageDeluidModel wduMdl = new WmlManageDeluidModel();
            wduMdl.setWacSid(doDelWacSid);
            wduMdl.setWduDeldate(new UDate());

            wduDao.deleteAll();
            wduDao.insert(wduMdl);
        }

    }

    /**
     *
     * <br>[機  能] 受信済みメールUIDLからメールサーバにないものを削除する
     * <br>[解  説] アカウントに対して実行し、削除が5000件以上になる場合は次回に実行する
     * <br>[備  考]
     * @param con コネクション
     * @param accMdl アカウント情報
     * @throws Exception 実行時例外
    */
    private void __cleanAccSingle(Connection con, WmlAccountModel accMdl) throws Exception {
        try {
            GSContext gsContext = GroupSession.getContext();
            String appRootPath = (String) gsContext.get(GSContext.APP_ROOT_PATH);

            WmlBiz wmlBiz = new WmlBiz();
            WmlReceiveServerModel receiveModel
            = wmlBiz.createReceiveServerData(con,
                    appRootPath,
                    accMdl);

            Pop3Server popServer = null;

//          1. WEBメールアカウント メール受信サーバへ接続する
//          2. メール受信サーバへから「全UIDの一覧」を取得する
            Set<String> uidlSet = null;
            try {
                popServer = new Pop3Server();
                uidlSet = popServer.getLiveUID(receiveModel);
            } catch (GSException e) {
                log__.warn("受信済みUIDL削除時のUID一覧取得時、受信サーバの接続に失敗 : "
                        + receiveModel.getWacSid()
                        + " | " + receiveModel.getAccountName());
                return;
            } catch (Exception e) {
                log__.warn("受信済みUIDL削除時のUID一覧取得に失敗 : "
                            + receiveModel.getWacSid()
                            + " | " + receiveModel.getAccountName());
                return;
            }

//          3. 2. で取得したUIDを「WML_UIDL_TMP」テーブルに全件登録する
            WmlUidlTempDao uidTempDao = new WmlUidlTempDao(con);
            uidTempDao.insertIkkatu(accMdl.getWacSid(), uidlSet);


//          4. WML_UIDLテーブルから「WML_UIDL_TMPテーブルに存在しないUID」を削除する
            WmlUidlDao uidDao = new WmlUidlDao(con);
            uidDao.deleteIkkatu(accMdl.getWacSid(), 5000);

//          5. 4. の削除完了後、WML_UIDL_TMPテーブルのレコード(2. で追加したデータ)を削除する
            uidTempDao.delete(accMdl.getWacSid());
        } catch (Exception e) {
            log__.error("UIDL削除時に例外発生 SID = " + accMdl.getWacSid(), e);
        }
    }

}
