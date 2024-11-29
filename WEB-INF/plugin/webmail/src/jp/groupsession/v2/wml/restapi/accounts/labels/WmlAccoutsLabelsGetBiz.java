package jp.groupsession.v2.wml.restapi.accounts.labels;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメールラベル一覧 取得用ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccoutsLabelsGetBiz {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccoutsLabelsGetBiz.class);

    /** RestApiコンテキスト */
    private final RestApiContext ctx__;
    /** パラメータ */
    private WmlAccoutsLabelsGetParamModel param__;
    /** 実行結果 */
    private List<WmlAccountsLabelsResultModel> result__ = new ArrayList<WmlAccountsLabelsResultModel>();

    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsGetBiz#result__
     */
    public List<WmlAccountsLabelsResultModel> getResult() {
        return result__;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param param WEBメールラベル一覧 取得用モデル
     * @param ctx RestApiコンテキスト
     */
    public WmlAccoutsLabelsGetBiz(WmlAccoutsLabelsGetParamModel param, RestApiContext ctx) {
        ctx__ = ctx;
        param__ = param;
    }

    /**
     * <br>[機  能] ラベル一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     */
    protected void _execute() throws SQLException {
        
        Connection con = ctx__.getCon();
        String accountId = param__.getAccountId();
        
        //WEBメールのアカウントSIDの取得
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int wacSid = wraBiz.getWmlAccountSid(con, accountId);

        log__.info("ラベル取得 アカウントID：" + accountId + " アカウントSID:" + wacSid);
        
        //ラベル情報の取得
        WmlLabelDao wlbDao = new WmlLabelDao(con);
        List<WmlLabelModel> labelList = wlbDao.getLabelList(wacSid);

        WmlAccountsLabelsResultModel resultMdl = null;
        for (WmlLabelModel labelMdl : labelList) {
            resultMdl = new WmlAccountsLabelsResultModel();
            resultMdl.setSid(labelMdl.getWlbSid());
            resultMdl.setName(labelMdl.getWlbName());
            result__.add(resultMdl);
        }
    }

}
