package jp.groupsession.v2.wml.wml260;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 送信予定メール管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml260Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml260Dao(Connection con) {
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
    public int recordCount(Wml260SearchParameterModel prmModel) throws SQLException {

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
            sql.addSql("   WML_DIRECTORY,");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_MAIL_SENDPLAN");

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
     * <br>[機  能] 予約送信メール一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索条件Model
     * @return List in Wml260MailDataModel
     * @throws SQLException SQL実行例外
     */
    public List<Wml260MailDataModel> getSearchData(Wml260SearchParameterModel prmModel)
    throws SQLException {

        PreparedStatement pstmt = null;
        List<Wml260MailDataModel> ret = new ArrayList<Wml260MailDataModel>();
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME, ");
            sql.addSql("   WML_ACCOUNT.WAC_ADDRESS as WAC_ADDRESS, ");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM as WMD_MAILNUM, ");
            sql.addSql("   WML_MAILDATA.WMD_TITLE as WMD_TITLE, ");
            sql.addSql("   WML_MAILDATA.WMD_TEMPFLG as WMD_TEMPFLG, ");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDDATE as WSP_SENDDATE, ");
            sql.addSql("   SENDADDRESS.WSA_ADDRESS as WSA_ADDRESS ");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT,");
            sql.addSql("   WML_DIRECTORY,");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_MAIL_SENDPLAN");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select WMD_MAILNUM, WSA_ADDRESS from WML_SENDADDRESS");
            sql.addSql("       where WSA_NUM = 1 ");
            sql.addSql("     ) SENDADDRESS");
            sql.addSql("   on");
            sql.addSql("     WML_MAIL_SENDPLAN.WMD_MAILNUM = SENDADDRESS.WMD_MAILNUM");

            sql = __setSearchWhereSql(prmModel, sql);
            sql.addSql("  and");
            sql.addSql("    WML_MAILDATA.WAC_SID = WML_ACCOUNT.WAC_SID");

            sql.addSql(" order by ");

            switch (prmModel.getSortKey()) {

            case 0:
                //ソート 件名
                sql.addSql(" WML_MAILDATA.WMD_TITLE ");
                break;
            case 1:
                //ソート 差出人
                sql.addSql(" WML_MAILDATA.WMD_FROM ");
                break;
            case 2:
                //ソート 宛先
                sql.addSql(" SENDADDRESS.WSA_ADDRESS");
                break;
            case 3:
                //ソート 日時
                sql.addSql(" WML_MAIL_SENDPLAN.WSP_SENDDATE ");
                break;
            case 4:
                //ソート アカウント名
                sql.addSql(" WML_ACCOUNT.WAC_NAME ");
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

            int start = 0;
            if (prmModel.getStart() > 1) {
                start = prmModel.getStart() - 1;
            }
            sql.setPagingValue(start, prmModel.getLimit());

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
     * <br>[機  能] 指定した予約送信メール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return 予約送信メール情報
     * @throws SQLException SQL実行例外
     */
    public WmlMaildataModel getReservationMailData(int mailNum)
    throws SQLException {
        WmlMaildataModel mailData = null;

        Wml260SearchParameterModel prmModel = new Wml260SearchParameterModel();
        prmModel.setStart(0);
        prmModel.setLimit(1);
        prmModel.setMailNumList(new long[] {mailNum});

        List<Wml260MailDataModel> mailDataList = getSearchData(prmModel);
        if (mailDataList != null && !mailDataList.isEmpty()) {
            Wml260MailDataModel searchResult = mailDataList.get(0);

            mailData = new WmlMaildataModel();
            mailData.setWmdMailnum(searchResult.getWmdMailnum());
            mailData.setWmdTitle(searchResult.getWlgTitle());
            mailData.setWmdFrom(searchResult.getWlgFrom());
            mailData.setWmdTempflg(searchResult.getWlgTempFlg());
            mailData.setWmdSdate(searchResult.getWlgDate());
        }

        return mailData;
    }

    /**
     *
     * [機 能] SQL(キーワード入力時の検索条件)を作成
     * [解 説]
     * [備 考]
     * @param bean Wml260SearchParameterModel
     * @param sql SqlBuffer
     */
    private void __createKeyWord(Wml260SearchParameterModel bean,
                                                SqlBuffer sql) {

        // 件名
        List<String> titleList = bean.getWml260Title();
//        // メールアドレス
//        String mailAddress = bean.getWml260Address();
        String keywordJoin = "  and";

        //検索条件 件名
        if (titleList != null && titleList.size() > 0) {
            sql.addSql(" and ");
            sql.addSql("      (");

            for (int i = 0; i < titleList.size(); i++) {
                if (i > 0) {
                    sql.addSql(keywordJoin);
                }

                sql.addSql("       WML_MAILDATA.WMD_TITLE like '%"
                        + JDBCUtil.escapeForLikeSearch(titleList.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }
            sql.addSql("      )");
        }

//        if (mailAddress != null
//        && (bean.getWml260AddressFrom() == GSConstWebmail.ADDRESS_KBN_OK
//            || bean.getWml260AddressTo() == GSConstWebmail.ADDRESS_KBN_OK)) {
//            sql.addSql(" and ");
//
//            //検索条件 差出人
//            if (bean.getWml260AddressFrom() == GSConstWebmail.ADDRESS_KBN_OK) {
//                sql.addSql("       WML_MAILDATA.WMD_FROM like '%"
//                        + mailAddress
//                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
//                if (bean.getWml260AddressTo() == GSConstWebmail.ADDRESS_KBN_OK) {
//                    sql.addSql(keywordJoin);
//                }
//            }
//
//            //検索条件 宛先
//            if (bean.getWml260AddressTo() == GSConstWebmail.ADDRESS_KBN_OK) {
//                sql.addSql("       WML_MAILDATA_SEND.WLS_ADDRESS like '%"
//                        + mailAddress
//                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
//            }
//        }
    }

    /**
     * <br>[機  能] 検索SQLの検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 検索条件
     * @param sql SqlBuffer
     * @return SqlBuffer
     */
    private SqlBuffer __setSearchWhereSql(Wml260SearchParameterModel bean, SqlBuffer sql) {

        //「予約送信」メールのみを対象とする
        sql.addSql(" where ");
        sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDKBN = ?");
        sql.addSql(" and ");
        sql.addSql("   WML_MAILDATA.WMD_MAILNUM = WML_MAIL_SENDPLAN.WMD_MAILNUM");
        sql.addSql(" and ");
        sql.addSql("   WML_DIRECTORY.WDR_TYPE = ?");
        sql.addSql(" and ");
        sql.addSql("   WML_MAILDATA.WDR_SID = WML_DIRECTORY.WDR_SID");
        sql.addIntValue(GSConstWebmail.WSP_SENDKBN_SENDTARGET);
        sql.addIntValue(GSConstWebmail.DIR_TYPE_NOSEND);

        //メッセージ番号
        long[] mailNumList = bean.getMailNumList();
        if (mailNumList != null && mailNumList.length > 0) {
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM in (");
            for (int mailIdx = 0; mailIdx < mailNumList.length; mailIdx++) {
                if (mailIdx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(mailNumList[mailIdx]);
            }
            sql.addSql("   )");
        }

        //アカウント名
        if (!StringUtil.isNullZeroString(bean.getWml260AccountName())) {
            sql.addSql(" and ");
            sql.addSql("   WML_MAILDATA.WAC_SID in (");
            sql.addSql("     select WAC_SID  from WML_ACCOUNT");
            sql.addSql("     where WAC_NAME like '%"
                            + JDBCUtil.escapeForLikeSearch(bean.getWml260AccountName())
                            + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   )");
        }

        // キーワード
        __createKeyWord(bean, sql);

        String dateFromStr = null;
        String dateToStr = null;

        if (bean.getWml260SendDateYear() != GSConstWebmail.SELECT_DATECOMBO
                && bean.getWml260SendDateMonth() != GSConstWebmail.SELECT_DATECOMBO
                && bean.getWml260SendDateDay() != GSConstWebmail.SELECT_DATECOMBO) {

            sql.addSql(" and ");

            //日時 一致する or 範囲指定
            if (bean.getWml260SendDateCondition() == GSConstWebmail.DATE_KBN_EQUAL
            || bean.getWml260SendDateCondition() == GSConstWebmail.DATE_KBN_DATEAREA) {
                //開始
                dateFromStr = bean.getWriteDate().getDateStringForSql();
                StringBuilder dateFromStrBuf = new StringBuilder();
                dateFromStrBuf.append(" 00:");
                dateFromStrBuf.append("00:");
                dateFromStrBuf.append("00.");
                dateFromStrBuf.append("0");
                dateFromStr = dateFromStr + dateFromStrBuf.toString();

                //終了
                if (bean.getWml260SendDateCondition() == GSConstWebmail.DATE_KBN_DATEAREA) {
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

                sql.addSql(" WML_MAILDATA.WMD_DATE ");
                sql.addSql(" between ");
                sql.addSql(" '" + dateFromStr + "' ");
                sql.addSql(" and ");
                sql.addSql(" '" + dateToStr + "' ");

            //日時 以前
            } else if (bean.getWml260SendDateCondition()
                    == GSConstWebmail.DATE_KBN_BEFORE) {

                sql.addSql(" WML_MAILDATA.WMD_DATE <= ? ");
                sql.addDateValue(bean.getWriteDate());
            //以降
            } else if (bean.getWml260SendDateCondition() == GSConstWebmail.DATE_KBN_AFTER) {

                sql.addSql(" WML_MAILDATA.WMD_DATE >= ? ");
                sql.addDateValue(bean.getWriteDate());
            }
        }

        return sql;
    }

    /**
     * <br>[機  能] 予約送信メール一覧情報を格納したモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean Wml260MailDataModel
     * @throws SQLException SQL実行例外
     */
    private Wml260MailDataModel __getWmlMailLogRs(ResultSet rs) throws SQLException {
        Wml260MailDataModel bean = new Wml260MailDataModel();

        bean.setAccountName(rs.getString("WAC_NAME"));
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWlgTitle(rs.getString("WMD_TITLE"));
        bean.setWlgFrom(rs.getString("WAC_ADDRESS"));
        bean.setWlgTempFlg(rs.getInt("WMD_TEMPFLG"));
        bean.setWlgDate(UDate.getInstanceTimestamp(rs.getTimestamp("WSP_SENDDATE")));
        bean.setWlsAddress(rs.getString("WSA_ADDRESS"));

        return bean;
    }
}
