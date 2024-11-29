package jp.groupsession.v2.wml.restapi.accounts.mails.move;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestDirectoryBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールを移動するAPI ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsMovePutBiz {

    /** 実行結果*/
    private final List<WmlAccountsMailsResultModel> result__ = new ArrayList<>();
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     * 
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlAccountsMailsMovePutBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     * 
     * <br>[機  能] メールを指定したディレクトリへ移動する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void execute(WmlAccountsMailsMovePutParamModel paramMdl) throws SQLException {

        boolean commit = false;
        try {
            //アカウントSIDを取得
            WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
            int wacSid = wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());

            //移動先ディレクトリSIDを取得する
            WmlRestDirectoryBiz wrdBiz = new WmlRestDirectoryBiz();
            long moveDirSid = wrdBiz.getDirectorySid(con__, wacSid, paramMdl.getDirectoryType());

            //メールの保存先ディレクトリを更新する
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.moveMail(con__, paramMdl.getSidArray(), moveDirSid);

            //更新後のメール情報を返す
            WmlRestMailDataBiz wrmBiz = new WmlRestMailDataBiz();
            result__.addAll(wrmBiz.getMailsResult(con__, wacSid, paramMdl.getSidArray(), ctx__.getAppRootPath()));

            con__.commit();
            commit = true;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<WmlAccountsMailsResultModel> getResult() {
        return result__;
    }
}
