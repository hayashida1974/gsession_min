package jp.groupsession.v2.adr.adr100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr100.dao.Adr100Dao;
import jp.groupsession.v2.adr.adr100.model.Adr100SearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr100Biz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr100Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr100ParamModel
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr100ParamModel paramMdl, int sessionUserSid)
        throws SQLException {

        int mode = paramMdl.getAdr100mode();

        if (mode == GSConstAddress.SEARCH_COMPANY_MODE_50) {
            //五十音検索
            __getInitData50(con, paramMdl, sessionUserSid);
        } else if (mode == GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) {

            //詳細検索
            __getInitDataDetail(con, paramMdl, sessionUserSid);
        }

        //会社情報の検索を行う
        if (paramMdl.getAdr100searchFlg() == 1) {
            Adr100SearchModel searchMdl = __createSearchModel(paramMdl);

            //最大件数
            int searchCnt = getSearchCount(con, searchMdl);

            int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;

            AdrUconfDao dao = new AdrUconfDao(con);
            AdrUconfModel model = dao.select(sessionUserSid);
            if (model != null && model.getAucComcount() > 0) {
                maxCnt = model.getAucComcount();
            }

            //ページ調整
            int maxPage = searchCnt / maxCnt;
            if ((searchCnt % maxCnt) > 0) {
                maxPage++;
            }
            int page = paramMdl.getAdr100page();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }
            paramMdl.setAdr100page(page);
            paramMdl.setAdr100pageTop(page);
            paramMdl.setAdr100pageBottom(page);

            //ページコンボ設定
            if (maxPage > 1) {
                paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
            }
            searchMdl.setPage(page);
            searchMdl.setMaxViewCount(maxCnt);
            Adr100Dao adr100Dao = new Adr100Dao(con);
            paramMdl.setCompanyList(adr100Dao.getSearchResultList(searchMdl));
        }
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr100ParamModel
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __getInitDataDetail(Connection con, Adr100ParamModel paramMdl, int sessionUserSid)
        throws SQLException {

        //業種コンボを設定
        AddressBiz adrBiz = new AddressBiz(reqMdl_);
        paramMdl.setAtiCmbList(adrBiz.getGyosyuLabelList(con));

        //都道府県コンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con, gsMsg));

    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr100ParamModel
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __getInitData50(Connection con, Adr100ParamModel paramMdl, int sessionUserSid)
        throws SQLException {

        AdrCompanyDao comDao = new AdrCompanyDao(con);


        //会社名イニシャルリストを設定
        List<String> kanaList = comDao.getcomInitialList();
        paramMdl.setAdr100comNameKanaList(kanaList);


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
    public int getSearchCount(Connection con, Adr100SearchModel searchMdl) throws SQLException {
        Adr100Dao adr100Dao = new Adr100Dao(con);
        return adr100Dao.getSearchCount(searchMdl);
    }

    /**
     * <br>[機  能] 検索条件Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr100ParamModel
     * @return 検索条件Model
     */
    private Adr100SearchModel __createSearchModel(Adr100ParamModel paramMdl) {
        Adr100SearchModel searchMdl = new Adr100SearchModel();

        //モード
        searchMdl.setMode(paramMdl.getAdr100mode());
        //ソートキー
        searchMdl.setSortKey(paramMdl.getAdr100SortKey());
        //オーダーキー
        searchMdl.setOrderKey(paramMdl.getAdr100OrderKey());

        if (paramMdl.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {

            //会社名先頭文字
            searchMdl.setCoSini(paramMdl.getAdr100SearchKana());

        } else if (paramMdl.getAdr100mode() == GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) {

            //企業コード
            searchMdl.setCoCode(paramMdl.getAdr100svCode());
            //会社名
            searchMdl.setCoName(paramMdl.getAdr100svCoName());
            //会社名カナ
            searchMdl.setCoNameKn(paramMdl.getAdr100svCoNameKn());
            //支店・営業所名
            searchMdl.setCoBaseName(paramMdl.getAdr100svCoBaseName());
            //業種
            searchMdl.setAtiSid(paramMdl.getAdr100svAtiSid());
            //都道府県
            searchMdl.setTdfk(paramMdl.getAdr100svTdfk());
            //備考
            searchMdl.setBiko(paramMdl.getAdr100svBiko());

        }


        return searchMdl;
    }
}
