package jp.groupsession.v2.wml.wml070;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 送受信ログ管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml070Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml070Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml070Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] レコード件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  prmModel 検索パラメータモデル
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int recordCount(Wml070SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");
            sql.addSql("   left join ");
            sql.addSql("     WML_MAIL_LOG_SEND ");
            sql.addSql("   on ");
            sql.addSql("     WML_MAIL_LOG.WMD_MAILNUM = WML_MAIL_LOG_SEND.WMD_MAILNUM ");

            sql = __setSearchWhereSql(prmModel, sql);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 送受信ログ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索条件Model
     * @return List in Wml070MailDataModel
     * @throws SQLException SQL実行例外
     */
    public List<Wml070MailDataModel> getSearchData(Wml070SearchParameterModel prmModel)
    throws SQLException {

        PreparedStatement pstmt = null;
        List<Wml070MailDataModel> ret = new ArrayList<Wml070MailDataModel>();
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql(" WML_MAIL_LOG.WMD_MAILNUM as WMD_MAILNUM, ");
            sql.addSql(" WML_MAIL_LOG.WLG_TITLE as WLG_TITLE, ");
            sql.addSql(" WML_MAIL_LOG.WLG_DATE as WLG_DATE, ");
            sql.addSql(" WML_MAIL_LOG.WLG_FROM as WLG_FROM, ");
            sql.addSql(" WML_MAIL_LOG.WLG_TEMPFLG as WLG_TEMPFLG, ");
            sql.addSql(" WML_MAIL_LOG.WLG_MAILTYPE as WLG_MAILTYPE, ");
            sql.addSql(" WML_MAIL_LOG_SEND.WLS_TYPE as WLS_TYPE, ");
            sql.addSql(" WML_MAIL_LOG_SEND.WLS_ADDRESS as WLS_ADDRESS ");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG ");
            sql.addSql("   left join ");
            sql.addSql("     WML_MAIL_LOG_SEND ");
            sql.addSql("   on ");
            sql.addSql("     WML_MAIL_LOG.WMD_MAILNUM = WML_MAIL_LOG_SEND.WMD_MAILNUM ");

            sql = __setSearchWhereSql(prmModel, sql);

            sql.addSql(" order by ");

            switch (prmModel.getSortKey()) {

            case 0:
                //ソート 件名
                sql.addSql(" WML_MAIL_LOG.WLG_TITLE ");
                break;
            case 1:
                //ソート 差出人
                sql.addSql(" WML_MAIL_LOG.WLG_FROM ");
                break;
            case 2:
                //ソート 宛先
                sql.addSql(" WML_MAIL_LOG_SEND.WLS_ADDRESS ");
                break;
            case 3:
                //ソート 日時
                sql.addSql(" WML_MAIL_LOG.WLG_DATE ");
                break;
            default:
                    break;

            }

            //昇順
            if (prmModel.getOrder() == GSConstWebmail.ORDER_ASC) {
                sql.addSql(" asc ");

            //降順
            } else {
                sql.addSql(" desc ");
            }

            sql.setPagingValue(prmModel.getStart() - 1, prmModel.getLimit());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlMailLogRs(rs));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

   /**
    *
    * [機 能] SQL(キーワード入力時の検索条件)を作成
    * [解 説]
    * [備 考]
    * @param bean Wml070SearchParameterModel
    * @param sql SqlBuffer
    * @param codCount 条件カウント
    * @return codCount 条件カウント
    */
   private int __createKeyWord(Wml070SearchParameterModel bean,
                                  SqlBuffer sql,
                                  int codCount) {

       // 件名
       List<String> titleList = bean.getWml070Title();
       // メールアドレス
       String mailAddress = bean.getWml070Address();
       String keywordJoin = "  and";

       //検索条件 件名
       if (titleList != null && titleList.size() > 0) {
           if (codCount == 0) {
               sql.addSql(" where ");
               codCount++;
           } else {
               sql.addSql(" and ");
           }

           sql.addSql("      (");

           for (int i = 0; i < titleList.size(); i++) {
               if (i > 0) {
                   sql.addSql(keywordJoin);
               }

               sql.addSql("       WML_MAIL_LOG.WLG_TITLE like '%"
                       + JDBCUtil.escapeForLikeSearch(titleList.get(i))
                       + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
           }
           sql.addSql("      )");
       }

       if (mailAddress != null
               && (bean.getWml070AddressFrom() == GSConstWebmail.ADDRESS_KBN_OK
                       || bean.getWml070AddressTo() == GSConstWebmail.ADDRESS_KBN_OK)) {
           if (codCount == 0) {
               sql.addSql(" where ");
               codCount++;
           } else {
               sql.addSql(" and ");
           }

           //検索条件 差出人
           if (bean.getWml070AddressFrom() == GSConstWebmail.ADDRESS_KBN_OK) {
               sql.addSql("       WML_MAIL_LOG.WLG_FROM like '%"
                       + mailAddress
                       + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
               if (bean.getWml070AddressTo() == GSConstWebmail.ADDRESS_KBN_OK) {
                   sql.addSql(keywordJoin);
               }
           }

           //検索条件 宛先
           if (bean.getWml070AddressTo() == GSConstWebmail.ADDRESS_KBN_OK) {
               sql.addSql("       WML_MAIL_LOG_SEND.WLS_ADDRESS like '%"
                       + mailAddress
                       + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
           }
       }
       return codCount;
   }

   /**
    * <br>[機  能] 検索SQLの検索条件を設定する
    * <br>[解  説]
    * <br>[備  考]
    * @param bean 検索条件
    * @param sql SqlBuffer
    * @return SqlBuffer
    */
   private SqlBuffer __setSearchWhereSql(Wml070SearchParameterModel bean, SqlBuffer sql) {

       int codCount = 0;

       //種別 受信
       if (bean.getWml070Type() == GSConstWebmail.TYPE_RESV) {
           sql.addSql(" where ");
           sql.addSql("   WML_MAIL_LOG.WLG_MAILTYPE = 0 ");
           codCount++;

       //種別 送信
       } else if (bean.getWml070Type() == GSConstWebmail.TYPE_SEND) {
           sql.addSql(" where ");
           sql.addSql("   WML_MAIL_LOG.WLG_MAILTYPE = 1 ");
           codCount++;
       }

       // キーワード
       codCount = __createKeyWord(bean, sql, codCount);

       String dateFromStr = null;
       String dateToStr = null;

       if (bean.getWml070SendDateYear() != GSConstWebmail.SELECT_DATECOMBO
               && bean.getWml070SendDateMonth() != GSConstWebmail.SELECT_DATECOMBO
               && bean.getWml070SendDateDay() != GSConstWebmail.SELECT_DATECOMBO) {

           if (codCount == 0) {
               sql.addSql(" where ");

           } else {
               sql.addSql(" and ");
           }

           //日時 一致する or 範囲指定
           if (bean.getWml070SendDateCondition() == GSConstWebmail.DATE_KBN_EQUAL
           || bean.getWml070SendDateCondition() == GSConstWebmail.DATE_KBN_DATEAREA) {
               //開始
               dateFromStr = bean.getWriteDate().getDateStringForSql();
               StringBuilder dateFromStrBuf = new StringBuilder();
               dateFromStrBuf.append(" 00:");
               dateFromStrBuf.append("00:");
               dateFromStrBuf.append("00.");
               dateFromStrBuf.append("0");
               dateFromStr = dateFromStr + dateFromStrBuf.toString();

               //終了
               if (bean.getWml070SendDateCondition() == GSConstWebmail.DATE_KBN_DATEAREA) {
                   dateToStr = bean.getWriteDateTo().getDateStringForSql();
               } else {
                   dateToStr = bean.getWriteDate().getDateStringForSql();
               }
               StringBuilder dateToStrBuf = new StringBuilder();
               dateToStrBuf.append(" 23:");
               dateToStrBuf.append("59:");
               dateToStrBuf.append("59.");
               dateToStrBuf.append("999");
               dateToStr = dateToStr + dateToStrBuf.toString();

               sql.addSql(" WML_MAIL_LOG.WLG_DATE ");
               sql.addSql(" between ");
               sql.addSql(" '" + dateFromStr + "' ");
               sql.addSql(" and ");
               sql.addSql(" '" + dateToStr + "' ");

           //日時 以前
           } else if (bean.getWml070SendDateCondition()
                   == GSConstWebmail.DATE_KBN_BEFORE) {

               sql.addSql(" WML_MAIL_LOG.WLG_DATE <= ? ");
               sql.addDateValue(bean.getWriteDate());
           //以降
           } else if (bean.getWml070SendDateCondition() == GSConstWebmail.DATE_KBN_AFTER) {

               sql.addSql(" WML_MAIL_LOG.WLG_DATE >= ? ");
               sql.addDateValue(bean.getWriteDate());
           }
       }

       return sql;
   }

   /**
    * <br>[機  能] 送受信ログ一覧情報を格納したモデルを取得する。
    * <br>[解  説]
    * <br>[備  考]
    * @param rs ResultSet
    * @return bean Wml070MailDataModel
    * @throws SQLException SQL実行例外
    */
   private Wml070MailDataModel __getWmlMailLogRs(ResultSet rs) throws SQLException {
       Wml070MailDataModel bean = new Wml070MailDataModel();

       bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
       bean.setWlgTitle(rs.getString("WLG_TITLE"));
       bean.setWlgDate(UDate.getInstanceTimestamp(rs.getTimestamp("WLG_DATE")));
       bean.setWlgFrom(rs.getString("WLG_FROM"));
       bean.setWlgTempFlg(rs.getInt("WLG_TEMPFLG"));
       bean.setWlgMailType(rs.getInt("WLG_MAILTYPE"));
       bean.setWlsType(rs.getInt("WLS_TYPE"));
       bean.setWlsAddress(rs.getString("WLS_ADDRESS"));

       return bean;
   }
}
