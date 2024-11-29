package jp.groupsession.v2.wml.restapi.accounts.mails.open_condition;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールを既読または未読にするAPI ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsOpenConditionPutBiz {

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
    public WmlAccountsMailsOpenConditionPutBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] メールの未読/既読状態を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void execute(WmlAccountsMailsOpenConditionPutParamModel paramMdl) throws SQLException {

        boolean commit = false;
        try {
            //アカウントSIDを取得
            WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
            int wacSid = wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());

            //メールの未読・既読状態を更新する
            int readType = GSConstWebmail.READEDFLG_NOREAD;
            if (paramMdl.getOpenFlg() == WmlAccountsMailsOpenConditionPutParamModel.OPENFLG_YES) {
                readType = GSConstWebmail.READEDFLG_READED;
            }

            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.changeMailReaded(con__, paramMdl.getSidArray(), readType);

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
