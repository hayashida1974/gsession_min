package jp.groupsession.v2.wml.restapi.accounts.labels;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlLabelBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメールラベル 登録用ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccoutsLabelsPostBiz {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccoutsLabelsPostBiz.class);

    /** RestApiコンテキスト */
    private final RestApiContext ctx__;
    /** パラメータ */
    private WmlAccoutsLabelsPostParamModel param__;
    /** 実行結果 */
    private WmlAccountsLabelsResultModel result__ = new WmlAccountsLabelsResultModel();
    /** 採番コントローラ*/
    private MlCountMtController mlCnt__;

    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.wml.restapi.accounts.labels.WmlAccoutsLabelsPostBiz#result__
     */
    public WmlAccountsLabelsResultModel getResult() {
        return result__;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param param WEBメールラベル一覧 取得用モデル
     * @param ctx RestApiコンテキスト
     */
    public WmlAccoutsLabelsPostBiz(WmlAccoutsLabelsPostParamModel param, RestApiContext ctx) {
        ctx__ = ctx;
        param__ = param;
        try {
            mlCnt__ = GroupSession.getResourceManager().getCountController(ctx.getRequestModel());
        } catch (Exception e) {
            throw new RuntimeException("採番コントローラ取得失敗", e);
        }
    }

    /**
     * <br>[機  能] ラベル一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     */
    protected void _execute() throws SQLException {

        boolean defAutoCommit = ctx__.getCon().getAutoCommit();
        ctx__.getCon().setAutoCommit(false);
        //登録処理
        log__.info("ラベル登録処理開始");
        __postLabel();
        log__.info("ラベル登録処理終了");
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);
    }

    /**
     * <br>[機  能] ラベルの登録
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __postLabel() throws SQLException {
        Connection con = ctx__.getCon();
        int userSid = ctx__.getRequestUserSid();

        //WEBメールのアカウントSIDの取得
        String accountId = param__.getAccountId();
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int wacSid = wraBiz.getWmlAccountSid(con, accountId);

        //ラベルの登録
        WmlLabelBiz labelBiz = new WmlLabelBiz();
        WmlLabelModel labelModel = labelBiz.doInsertLabel(
            mlCnt__, con, userSid, param__.getName(), wacSid);

        //オペレーションログの登録
        WmlBiz wmlBiz = new WmlBiz();
        RequestModel reqMdl = ctx__.getRequestModel();
        GsMessage gsMsg = new GsMessage(reqMdl);

        WmlAccountDao accountDao = new WmlAccountDao(con);
        String accountName = accountDao.getAccountName(wacSid);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage("wml.96"));
        sb.append("]");
        sb.append(accountName);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("wml.74"));
        sb.append("]");
        sb.append(NullDefault.getString(labelModel.getWlbName(), ""));

        wmlBiz.outPutApiLog(reqMdl, ctx__.getCon(), 
            ctx__.getRequestUserSid(), "RESTAPI_MAIL_LABEL_INSERT",
            gsMsg.getMessage("cmn.entry"), GSConstLog.LEVEL_TRACE, sb.toString());


        //結果の作成
        WmlAccountsLabelsResultModel resultMdl = new WmlAccountsLabelsResultModel();
        resultMdl.setSid(labelModel.getWlbSid());
        resultMdl.setName(labelModel.getWlbName());

        result__ = resultMdl;
    }
}
