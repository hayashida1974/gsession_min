package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメール メールを削除するAPI ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsDeleteBiz {

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
    public WmlAccountsMailsDeleteBiz(RestApiContext ctx) {
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
    public void execute(WmlAccountsMailsDeleteParamModel paramMdl) throws SQLException {

        boolean commit = false;
        try {
            //アカウントSIDを取得
            int wacSid = __getAccountSid(paramMdl);

            //メールの物理削除を行う
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.deleteMailData(con__, String.valueOf(wacSid), paramMdl.getSidArray(), ctx__.getRequestUserSid());

            con__.commit();
            commit = true;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * 削除対象メールの件名を取得する
     * @param paramMdl パラメータ情報
     * @return メールの件名
     * @throws SQLException SQL実行時例外
     */
    public String[] getDeleteMailTitle(WmlAccountsMailsDeleteParamModel paramMdl) throws SQLException {
        int wacSid = __getAccountSid(paramMdl);

        WmlMaildataDao mailDataDao = new WmlMaildataDao(con__);
        List<WmlMaildataModel> mailList = mailDataDao.getMailDataList(wacSid, paramMdl.getSidArray());

        return mailList.stream()
            .map(mailData -> mailData.getWmdTitle())
            .toArray(String[]::new);
    }

    private int __getAccountSid(WmlAccountsMailsDeleteParamModel paramMdl) throws SQLException {
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        return wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());
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
