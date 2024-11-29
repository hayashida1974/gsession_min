package jp.groupsession.v2.wml.restapi.accounts.count;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelRelationDao;
import jp.groupsession.v2.wml.model.WmlLabelCountModel;
import jp.groupsession.v2.wml.restapi.accounts.count.WmlAccountsCountResultModel.LabelArray;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestDirectoryBiz;
import jp.groupsession.v2.wml.restapi.model.DirectoryArray;

/**
 * <br>[機  能] WEBメール件数 取得用ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsCountBiz {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsCountBiz.class);

     /** RestApiコンテキスト */
    private final RestApiContext ctx__;
    /** リクエストパラメータ */
    private final WmlAccountsCountParamModel param__;
    /** 実行結果 */
    private WmlAccountsCountResultModel result__ = new WmlAccountsCountResultModel();

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param param WEBメール件数 取得用モデル
     * @param ctx RestApiコンテキスト
     */
    public WmlAccountsCountBiz(RestApiContext ctx, WmlAccountsCountParamModel param) {
        param__ = param;
        ctx__ = ctx;
    }

    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.wml.restapi.accounts.count.WmlAccountsCountBiz#result__
     */
    public WmlAccountsCountResultModel getResult() {
        return result__;
    }

    /**
     * <br>[機  能] メール件数の取得
     * <br>[解  説]
     * <br>[備  考]
     */
    protected void _execute() throws SQLException {

        Connection con = ctx__.getCon();
        String accountId = param__.getAccountId();
        
        //WEBメールのアカウントSIDの取得
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int wacSid = wraBiz.getWmlAccountSid(con, accountId);
        log__.info("メール件数取得 アカウントID：" + accountId + " アカウントSID:" + wacSid);

        //ディレクトリ情報の設定
        log__.info("ディレクトリ情報の取得");
        __setDirectoryData(wacSid);
        
        //ラベル情報の設定
        log__.info("ラベルのディレクトリ情報の取得");
        __setLabelData(wacSid);
    }

    /**
     * <br>[機  能] ディレクトリ情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     */
    private void __setDirectoryData(int wacSid) throws SQLException {

        Connection con = ctx__.getCon();
        RequestModel reqMdl = ctx__.getRequestModel();

        //メールボックス情報の取得
        WmlRestDirectoryBiz dirBiz = new WmlRestDirectoryBiz();
        List<DirectoryArray> directoryArrayList = dirBiz.getDirectoryData(con, reqMdl, wacSid);
        
        result__.setDirectoryArray(directoryArrayList);
    }

    /**
     * <br>[機  能] ラベル情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     */
    private void __setLabelData(int wacSid) throws SQLException {

        Connection con = ctx__.getCon();

        //ラベル情報の取得
        WmlLabelDao wlbDao = new WmlLabelDao(con);
        List<WmlLabelCountModel> labelList = wlbDao.getLabelListWithMidoku(wacSid);
        List<Integer> labelSidList = labelList.stream()
            .map(mdl -> mdl.getId())
            .collect(Collectors.toList());

        //ラベルごとの全件情報の取得
        WmlLabelRelationDao wlrDao = new WmlLabelRelationDao(con);
        Map<Integer, Long> countMap = wlrDao.getLabelMailCount(wacSid, labelSidList);

        List<LabelArray> labelArrayList = new ArrayList<LabelArray>();
        LabelArray labelArray = null;
        long countNum = 0;
        for (WmlLabelCountModel labelMdl : labelList) {
            countNum = countMap.get(labelMdl.getId());
            labelArray = new LabelArray(labelMdl, countNum);
            labelArrayList.add(labelArray);
        }
        result__.setLabelArray(labelArrayList);
    }

}
