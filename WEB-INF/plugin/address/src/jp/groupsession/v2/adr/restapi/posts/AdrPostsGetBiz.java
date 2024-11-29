package jp.groupsession.v2.adr.restapi.posts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 役職一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrPostsGetBiz {

    /** 実行結果*/
    private final List<AdrPostsResultModel> result__ = new ArrayList<AdrPostsResultModel>();
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
    public AdrPostsGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 役職一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute() throws SQLException {

        //役職一覧取得
        AdrPositionDao apsDao = new AdrPositionDao(con__);
        List<AdrPositionModel> mdlList = apsDao.selectPositionList();
        for (AdrPositionModel apsMdl : mdlList) {
            AdrPostsResultModel resultModel = new AdrPostsResultModel();
            resultModel.setSid(apsMdl.getApsSid());
            resultModel.setName(apsMdl.getApsName());
            result__.add(resultModel);
        }
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<AdrPostsResultModel> getResult() {
        return result__;
    }
}
