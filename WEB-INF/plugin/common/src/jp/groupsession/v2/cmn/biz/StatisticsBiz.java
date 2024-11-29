package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 統計情報に関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class StatisticsBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(StatisticsBiz.class);

    /**
     * <br>[機  能] 「/」で区切られた日付文字列からUDateを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列
     * @return date 日付
     */
    public static UDate getDateFromString(String dateStr) {
        UDate date = new UDate();
        if (!StringUtil.isNullZeroStringSpace(dateStr)) {
            date.setDate(
                    Integer.parseInt(dateStr.substring(0, 4)),
                    Integer.parseInt(dateStr.substring(5, 7)),
                    Integer.parseInt(dateStr.substring(8, 10)));
        }
        return date;
    }

    /**
     * <br>[機  能] 表示件数リストを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return 表示件数リスト
     */
    public static ArrayList<LabelValueBean> getDspNumList() {

        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();

        ret.add(new LabelValueBean("10" , "10"));
        ret.add(new LabelValueBean("20" , "20"));
        ret.add(new LabelValueBean("30" , "30"));
        ret.add(new LabelValueBean("40" , "40"));
        ret.add(new LabelValueBean("50" , "50"));
        ret.add(new LabelValueBean("100" , "100"));

        return ret;
    }

    /**
     * <br>[機  能] 年コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param minYear 最古年
     * @param maxYear 最新年
     * @param reqMdl リクエストモデル
     * @return 年コンボ
     * @throws SQLException SQL実行時例外
     */
    public static ArrayList<LabelValueBean> getDspYearList(
            int minYear, int maxYear, RequestModel reqMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());

        //最新日時と最古日時からリストを作成する
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        for (int i = minYear; i <= maxYear; ++i) {
            String labelValue = String.valueOf(i);
            ret.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[]{labelValue}), labelValue));
        }
        return ret;
    }

    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ArrayList (in LabelValueBean) 月コンボ
     */
    public static List<LabelValueBean> getMonthList(RequestModel reqMdl) {
        int month = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + gsMsg.getMessage("cmn.month"),
                            String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>[機  能]数字をフォーマットします
     * <br>[解  説]コンマをつける
     * <br>[備  考]
     *
     * @param num 変換元の数字
     * @return 変換済みの文字列
     */
    public static String formatNum(int num) {
        return formatNum((long) num);
    }

    /**
     * <br>[機  能]数字をフォーマットします
     * <br>[解  説]コンマをつける
     * <br>[備  考]
     *
     * @param num 変換元の数字
     * @return 変換済みの文字列
     */
    public static String formatNum(long num) {
        //数字をフォーマットする
        NumberFormat exObject1 = NumberFormat.getNumberInstance();
        String f_num = String.valueOf(exObject1.format(num));
        return f_num;
    }

    /**
     * <br>[機  能] 削除を行う
     * <br>[解  説]
     * <br>[備  考] 現在日から指定した年、月前のデータを削除する
     * @param con コネクション
     * @param tableName 削除対象テーブル名
     * @param fieldName 削除対象テーブルの「日付」フィールド名
     * @param year 年
     * @param month 月
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteLogCount(
            Connection con, String tableName, String fieldName, int year, int month)
                    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //削除基準となる日時を生成
            UDate delDate = new UDate();
            delDate.addYear(year * -1);
            delDate.addMonth(month * -1);
            delDate.setMaxHhMmSs();

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   " + tableName + " ");
            sql.addSql(" where ");
            sql.addSql("   " + fieldName + " <= ? ");
            sql.addDateValue(delDate);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

}