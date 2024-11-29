package jp.groupsession.v2.wml.restapi.accounts.mails.label;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールにラベルを設定または解除するAPI ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsLabelPutBiz {

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
    public WmlAccountsMailsLabelPutBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] メールに対しラベルを設定する、または指定したラベルを解除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void execute(WmlAccountsMailsLabelPutParamModel paramMdl) throws SQLException {

        boolean commit = false;
        try {
            //アカウントSIDを取得
            WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
            int wacSid = wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());

            //ラベルSID
            int labelSid = paramMdl.getLabelSid();

            WmlBiz wmlBiz = new WmlBiz();
            //指定メールに対し、ラベルの設定 or 解除を行う
            if (paramMdl.getProcType().equals(WmlAccountsMailsLabelPutParamModel.PROCTYPE_ADD)) {
                //ラベルの設定
                wmlBiz.addLabelToMail(con__,
                                    wacSid,
                                    paramMdl.getSidArray(),
                                    labelSid);

            } else if (paramMdl.getProcType().equals(WmlAccountsMailsLabelPutParamModel.PROCTYPE_REMOVE)) {
                //ラベルの解除
                wmlBiz.deleteLabelToMail(con__, paramMdl.getSidArray(), labelSid);
            }

            //ラベル設定 or 解除後のメール情報を結果として返す
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
