package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールを単体取得するAPI ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsGetBiz {

    /** 実行結果*/
    private WmlAccountsMailsResultModel result__ = new WmlAccountsMailsResultModel();
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     * 
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     */
    public WmlAccountsMailsGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     * <br>[機  能] メールを指定したディレクトリへ移動する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _execute(WmlAccountsMailsGetParamModel paramMdl) throws SQLException {

        WmlRestMailDataBiz biz = new WmlRestMailDataBiz();
        
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int accountSid = wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());
        long mailSid = paramMdl.getMailSid();
        long[] mailSidArray = new long[] {mailSid};
        String appRootPath = ctx__.getAppRootPath();

        List<WmlAccountsMailsResultModel> resultList =
            biz.getMailsResult(con__, accountSid, mailSidArray, appRootPath);
        for (WmlAccountsMailsResultModel mdl : resultList) {
            //送信したメール1件の情報を取得
            result__ = mdl;
            break;
        }
    }

    /**
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public WmlAccountsMailsResultModel getResult() {
        return result__;
    }
}
