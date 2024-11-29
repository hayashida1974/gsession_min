package jp.groupsession.v2.wml.restapi.accounts.mails.receive;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.batch.WmlReceiveBatch;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

public class WmlAccountsMailsReceivePostBiz {
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    public WmlAccountsMailsReceivePostBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    public void execute(
        WmlAccountsMailsReceivePostParamModel param,
        GSContext context
        ) throws SQLException {
        boolean commit = false;
        try {

            WmlRestAccountBiz wacBiz = new WmlRestAccountBiz();

            MessageResources msgResource =
                                    (MessageResources) context.get(GSContext.MSG_RESOURCE);
            WmlReceiveBatch receiveBatch =
                        new WmlReceiveBatch(
                            context,
                            wacBiz.getWmlAccountSid(con__, param.getAccountId()),
                            msgResource,
                            ctx__.getRequestModel().getDomain(),
                            ctx__.getRequestUserSid());
            Thread thread = new Thread(receiveBatch);
            receiveBatch.setStatus(WmlReceiveBatch.STATUS_RECEIVE);
            thread.start();

            // 受信メール進捗チェック(1秒間隔でチェック → 30秒経過後はタイムアウト)
            for (int i = 0; i < 30; i++) {
                if (receiveBatch.getStatus() != WmlReceiveBatch.STATUS_RECEIVE) {
                    // 終了判定
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

            if (con__ != null && !con__.isClosed()) {
                con__.commit();
                commit = true;
            }
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con__);
            }
        }
    }


}
