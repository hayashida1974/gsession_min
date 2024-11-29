package jp.groupsession.v2.adr.restapi.labels;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.restapi.labels.AdrLabelsResultModel.LabelInfo;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 ラベル一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrLabelsGetBiz {

    /** 実行結果*/
    private final List<AdrLabelsResultModel> result__ = new ArrayList<AdrLabelsResultModel>();
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
    public AdrLabelsGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] ラベル一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute() throws SQLException {

        AdrLabelCategoryDao alcDao = new AdrLabelCategoryDao(con__);
        AdrLabelDao albDao = new AdrLabelDao(con__);

        //カテゴリ一覧取得
        List<AdrLabelCategoryModel> alcMdlList = alcDao.select();
        for (AdrLabelCategoryModel alcMdl : alcMdlList) {
            AdrLabelsResultModel resultModel = new AdrLabelsResultModel();
            resultModel.setSid(alcMdl.getAlcSid());
            resultModel.setName(alcMdl.getAlcName());
            
            //ラベル一覧取得
            List<LabelInfo> labelArray = new ArrayList<LabelInfo>();
            List<AdrLabelModel> albMdlList = albDao.getLabelInCategory(alcMdl.getAlcSid());
            for (AdrLabelModel albMdl : albMdlList) {
                LabelInfo labelInfo = new LabelInfo();
                labelInfo.setSid(albMdl.getAlbSid());
                labelInfo.setName(albMdl.getAlbName());
                labelArray.add(labelInfo);
            }
            resultModel.setLabelArray(labelArray);
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
    public List<AdrLabelsResultModel> getResult() {
        return result__;
    }
}
