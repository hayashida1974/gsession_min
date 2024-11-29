package jp.groupsession.v2.wml.wml270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAccessConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAddressDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistDao;

/**
 * <br>[機  能] WEBメール 送信先リスト管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml270Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml270Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Wml270ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {

        if (paramMdl.getWml270searchFlg() != 1) {
            //検索条件Model生成
            Wml270SearchModel searchMdl = __createSearchModel(paramMdl, reqMdl);

            //検索結果件数を取得する
            int maxCount = getRecordCount(con, paramMdl, reqMdl);

            //件数カウント
            int limit = searchMdl.getLimit();
            int maxPage = 1;
            if (maxCount > 0) {
                maxPage = PageUtil.getPageCount(maxCount, limit);
            }
            log__.debug("表示件数 = " + maxCount);

            //現在ページ（ページコンボのvalueを設定）
            int nowPage = paramMdl.getWml270pageTop();
            if (nowPage == 0) {
                nowPage = 1;
            } else if (nowPage > maxPage) {
                nowPage = maxPage;
            }
            //結果取得開始カーソル位置
            int start = PageUtil.getRowNumber(nowPage, limit);
            searchMdl.setStart(start);

            //ページング
            paramMdl.setWml270pageTop(nowPage);
            paramMdl.setWml270pageBottom(nowPage);
            if (maxPage > 1) {
                paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));
            }

            Wml270Dao dao = new Wml270Dao(con);
            List<Wml270DestListModel> destListList = dao.getDestListList(searchMdl, reqMdl);
            paramMdl.setDestListList(destListList);
        }
    }

    /**
     * <br>[機  能] 送信先リスト名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 送信先リストSID
     * @return 送信先リスト名
     * @throws SQLException SQL実行時例外
     */
    public String getSendListName(Connection con, String[] sidList)
    throws SQLException {

        Wml270Dao wml270Dao = new Wml270Dao(con);
        List<String> titleList = wml270Dao.getSendListNameList(sidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx), ""));
        }

        return msgTitle;
    }

    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getRecordCount(Connection con, Wml270ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        Wml270Dao dao = new Wml270Dao(con);
        return dao.recordCount(__createSearchModel(paramMdl, reqMdl));
    }


    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return 検索条件Model
     */
    private Wml270SearchModel __createSearchModel(Wml270ParamModel paramMdl, RequestModel reqMdl) {
        Wml270SearchModel searchMdl = new Wml270SearchModel();
        searchMdl.setKeyword(paramMdl.getWml270svKeyword());
        if (paramMdl.getWmlAccountMode() == 1) {
            searchMdl.setUserSid(reqMdl.getSmodel().getUsrsid());
        }

        searchMdl.setSortKey(paramMdl.getWml270sortKey());
        searchMdl.setOrder(paramMdl.getWml270order());
        searchMdl.setLimit(GSConstWebmail.LIMIT_DSP_SENDLIST);

        return searchMdl;
    }

    /**
     * <br>[機  能] 送信先リストの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteDestList(Connection con, Wml270ParamModel paramMdl, int userSid)
    throws SQLException {

        WmlDestlistDao destListDao = new WmlDestlistDao(con);
        WmlDestlistAddressDao destAddressDao = new WmlDestlistAddressDao(con);
        WmlDestlistAccessConfDao destAccessDao = new WmlDestlistAccessConfDao(con);
        for (String wdlSid : paramMdl.getWml270selectDestList()) {
            destListDao.delete(Integer.parseInt(wdlSid));
            destAddressDao.delete(Integer.parseInt(wdlSid));
            destAccessDao.delete(Integer.parseInt(wdlSid));
        }
    }

    /**
     * <br>[機  能] ログ用 連絡先リスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlSidList SIDリスト
     * @return ログ
     * @throws SQLException SQL実行時例外
     */
    public String getDestList(Connection con, String[] wdlSidList)
    throws SQLException {

        WmlDestlistDao destListDao = new WmlDestlistDao(con);
        String ret = destListDao.select(wdlSidList);
        return ret;
    }
}
