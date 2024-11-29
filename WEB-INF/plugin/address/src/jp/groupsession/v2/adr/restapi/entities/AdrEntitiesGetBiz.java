package jp.groupsession.v2.adr.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.adr.restapi.biz.AdrRestAddressBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 アドレス情報取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrEntitiesGetBiz {

    /** 実行結果*/
    private AdrEntitiesResultModel result__ = new AdrEntitiesResultModel();
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
    public AdrEntitiesGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] アドレス情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute(AdrEntitiesGetParamModel param) throws SQLException {

        //アドレス情報を取得
        AdrRestAddressBiz biz = new AdrRestAddressBiz();
        result__ = biz.getAddressInfo(con__, param.getAddressSid());
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public AdrEntitiesResultModel getResult() {
        return result__;
    }
}
