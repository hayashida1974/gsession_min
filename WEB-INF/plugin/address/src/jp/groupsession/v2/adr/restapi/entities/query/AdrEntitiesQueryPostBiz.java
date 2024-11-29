package jp.groupsession.v2.adr.restapi.entities.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.dao.AdrAddressSearchDao;
import jp.groupsession.v2.adr.model.AdrAddressSearchModel;
import jp.groupsession.v2.adr.restapi.entities.AdrEntitiesResultModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] アドレス帳 アドレス情報一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrEntitiesQueryPostBiz {

    /** 実行結果*/
    private final List<AdrEntitiesResultModel> result__ = new ArrayList<>();
    /** 検索結果件数 */
    private Integer maxCount__ = 0;
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     *
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiContext
     */
    public AdrEntitiesQueryPostBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 送信先リストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void execute(AdrEntitiesQueryPostParamModel paramMdl) throws SQLException {

        AdrAddressSearchModel searchMdl = new AdrAddressSearchModel();
        //検索キーワード
        searchMdl.setKeyword(paramMdl.getKeywordText());
        //ラベルSID
        searchMdl.setLabel(paramMdl.getLabelSidArray());

        //氏名カナ 先頭1文字
        searchMdl.setUnameKnHead(paramMdl.getSeiKanaStartText());
        //役職SID
        searchMdl.setPosition(paramMdl.getYakusyokuSid());
        //都道府県SID
        searchMdl.setTdfk(paramMdl.getTodofukenSid());

        if (paramMdl.getTantoSearchType() == AdrEntitiesQueryPostParamModel.TANTOSEACHTYPE_USER) {
            //ユーザID
            searchMdl.setUserId(paramMdl.getTantoUserId());
        } else if (paramMdl.getTantoSearchType()
                    == AdrEntitiesQueryPostParamModel.TANTOSEACHTYPE_GROUP) {
            //グループID
            searchMdl.setGroupId(paramMdl.getTantoGroupId());
        }

        //会社検索タイプ
        searchMdl.setCompanySearchType(paramMdl.getCompanySearchType());
        if (paramMdl.getCompanySearchType()
                == AdrEntitiesQueryPostParamModel.COMPANYSEACHTYPE_NO_LMIT) {
            //企業コード
            searchMdl.setCoCode(paramMdl.getCompanyId());
            //会社拠点SID
            searchMdl.setCompanyBaseSid(paramMdl.getBaseSid());
            //会社名カナ 先頭1文字
            searchMdl.setCnameKnHead(paramMdl.getCompanyNameKanaStartText());
            //業種SID
            searchMdl.setAtiSid(paramMdl.getIndustrySid());
        }

        //ソート昇順降順
        searchMdl.setOrderKey(paramMdl.getSortOrderFlg());
        //カナ順取得開始位置
        searchMdl.setKanaStartOffset(paramMdl.getKanaStartOffsetText());

        //読み込み開始位置 > 検索結果件数の場合、空の検索結果を返す
        int offset = paramMdl.getOffset();
        AdrAddressSearchDao searchDao = new AdrAddressSearchDao(con__);
        maxCount__ = searchDao.getSearchCount(searchMdl, ctx__.getRequestModel());
        if (offset + 1 > maxCount__) {
            return;
        }

        //検索開始位置
        searchMdl.setOffset(offset);
        //取得最大件数
        searchMdl.setLimit(paramMdl.getLimit());

        //アドレス情報一覧取得
        result__.addAll(searchDao.getSearchAddressList(searchMdl, ctx__.getRequestModel()));
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<AdrEntitiesResultModel> getResult() {
        return result__;
    }

    /**
     *
     * <br>[機  能] 検索結果件数の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public Integer getMaxCount() {
        return maxCount__;
    }
}
