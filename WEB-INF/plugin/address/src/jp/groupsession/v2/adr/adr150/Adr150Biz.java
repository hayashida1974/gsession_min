package jp.groupsession.v2.adr.adr150;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr150.dao.Adr150Dao;
import jp.groupsession.v2.adr.adr150.model.Adr150SearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社選択のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr150Biz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr150Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr150ParamModel
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr150ParamModel paramMdl) throws SQLException {
        //業種コンボを設定
        AddressBiz adrBiz = new AddressBiz(reqMdl_);
        paramMdl.setAtiCmbList(adrBiz.getGyosyuLabelList(con));

        //都道府県コンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con, gsMsg));

        //会社情報の検索を行う
        if (paramMdl.getAdr150searchFlg() == 1) {
            Adr150SearchModel searchMdl = __createSearchModel(paramMdl);

            //最大件数
            int searchCnt = getSearchCount(con, searchMdl);
            int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;
            //ページ調整
            int maxPage = searchCnt / maxCnt;
            if ((searchCnt % maxCnt) > 0) {
                maxPage++;
            }
            int page = paramMdl.getAdr150page();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }
            paramMdl.setAdr150page(page);
            paramMdl.setAdr150pageTop(page);
            paramMdl.setAdr150pageBottom(page);

            //ページコンボ設定
            if (maxPage > 1) {
                paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
            }
            searchMdl.setPage(page);
            searchMdl.setMaxViewCount(GSConstAddress.COMPANYSEARCH_MAXCOUNT);
            Adr150Dao adr150Dao = new Adr150Dao(con);
            paramMdl.setCompanyList(adr150Dao.getSearchResultList(searchMdl));
        }
    }

    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param searchMdl 検索条件
     * @return 検索結果件数
     * @throws SQLException SQL実行例外
     */
    public int getSearchCount(Connection con, Adr150SearchModel searchMdl) throws SQLException {
        Adr150Dao adr150Dao = new Adr150Dao(con);
        return adr150Dao.getSearchCount(searchMdl);
    }

    /**
     * <br>[機  能] 検索条件Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr150ParamModel
     * @return 検索条件Model
     */
    private Adr150SearchModel __createSearchModel(Adr150ParamModel paramMdl) {
        Adr150SearchModel searchMdl = new Adr150SearchModel();

        //企業コード
        searchMdl.setCoCode(paramMdl.getAdr150svCode());
        //会社名
        searchMdl.setCoName(paramMdl.getAdr150svCoName());
        //会社名カナ
        searchMdl.setCoNameKn(paramMdl.getAdr150svCoNameKn());
        //支店・営業所名
        searchMdl.setCoBaseName(paramMdl.getAdr150svCoBaseName());
        //業種
        searchMdl.setAtiSid(paramMdl.getAdr150svAtiSid());
        //都道府県
        searchMdl.setTdfk(paramMdl.getAdr150svTdfk());
        //備考
        searchMdl.setBiko(paramMdl.getAdr150svBiko());
        //ソートキー
        searchMdl.setSortKey(paramMdl.getAdr150SortKey());
        //オーダーキー
        searchMdl.setOrderKey(paramMdl.getAdr150OrderKey());

        return searchMdl;
    }
}
