package jp.groupsession.v2.sml.sml380;

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
import jp.groupsession.v2.sml.dao.SmlBanDestConfDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBanDestPermitDao;
/**
 *
 * <br>[機  能] 送信先制限設定 一覧画面　ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml380Biz {
    /** Logging インスタンス */
  private static Log log__ = LogFactory.getLog(Sml380Biz.class);

  /**
   * <br>[機  能] 初期表示設定を行う
   * <br>[解  説]
   * <br>[備  考]
   * @param con コネクション
   * @param paramMdl パラメータ情報
   * @param reqMdl リクエスト情報
   * @throws SQLException SQL実行時例外
   */
  public void setInitData(Connection con, Sml380ParamModel paramMdl,
                          RequestModel reqMdl) throws SQLException {

      if (paramMdl.getSml380searchFlg() != 1) {
          //検索条件Model生成
          Sml380SearchModel searchMdl = __createSearchModel(paramMdl, reqMdl);

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
          int nowPage = paramMdl.getSml380pageTop();
          if (nowPage == 0) {
              nowPage = 1;
          } else if (nowPage > maxPage) {
              nowPage = maxPage;
          }
          //結果取得開始カーソル位置
          int start = PageUtil.getRowNumber(nowPage, limit);
          searchMdl.setStart(start);

          //ページング
          paramMdl.setSml380pageTop(nowPage);
          paramMdl.setSml380pageBottom(nowPage);
          if (maxPage > 1) {
              paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));
          }
          Sml380Dao sml380dao = new Sml380Dao(con);
          List<Sml380DataModel> list = sml380dao.searchSbc(searchMdl);
         paramMdl.setBanList(list);
      }
  }
  /**
   * <br>[機  能] 送信制限設定名を取得する
   * <br>[解  説]
   * <br>[備  考]
   * @param con コネクション
   * @param sidList 送信先リストSID
   * @return 送信先リスト名
   * @throws SQLException SQL実行時例外
   */
  public String getSendListName(Connection con, String[] sidList)
  throws SQLException {
      Sml380Dao sml380Dao = new Sml380Dao(con);
      List<String> titleList = sml380Dao.getSbcNameList(sidList);

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
  public int getRecordCount(Connection con, Sml380ParamModel paramMdl, RequestModel reqMdl)
  throws SQLException {
      Sml380Dao dao = new Sml380Dao(con);
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
  private Sml380SearchModel __createSearchModel(Sml380ParamModel paramMdl, RequestModel reqMdl) {
      Sml380SearchModel searchMdl = new Sml380SearchModel();
      searchMdl.setKeyword(paramMdl.getSml380svKeyword());
      searchMdl.setSortKey(paramMdl.getSml380sortKey());
      searchMdl.setOrder(paramMdl.getSml380order());
      searchMdl.setLimit(GSConstWebmail.LIMIT_DSP_SENDLIST);
      searchMdl.setStart(paramMdl.getSml380pageTop());
      return searchMdl;
  }

  /**
   * <br>[機  能] 送信先リストの削除を行う
   * <br>[解  説]
   * <br>[備  考]
   * @param con コネクション
   * @param paramMdl パラメータ情報
   * @throws SQLException SQL実行時例外
   */
  public void deleteDestList(Connection con, Sml380ParamModel paramMdl)
  throws SQLException {
      SmlBanDestConfDao sbcDao = new SmlBanDestConfDao(con);
      SmlBanDestDao sbdDao = new SmlBanDestDao(con);
      SmlBanDestPermitDao sbpDao = new SmlBanDestPermitDao(con);
      for (String sbcSidStr : paramMdl.getSml380selectBanList()) {
          int sbcSid = NullDefault.getInt(sbcSidStr, -1);
          if (sbcSid > 0) {
              sbcDao.delete(sbcSid);
              sbpDao.delete(sbcSid);
              sbdDao.delete(sbcSid);
          }
      }
  }

}
