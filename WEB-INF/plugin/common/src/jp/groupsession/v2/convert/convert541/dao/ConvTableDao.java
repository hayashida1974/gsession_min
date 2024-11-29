package jp.groupsession.v2.convert.convert541.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v5.4.1へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @param saiban 採番用コントローラー
     * @throws SQLException 例外
     */
    public void convert(
            MlCountMtController saiban) throws SQLException {

        log__.debug("-- DBコンバート開始 --");
        //create Table or alter table
        createTable(saiban);


        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @throws SQLException SQL実行例外
     */
    public void createTable(
            MlCountMtController saiban) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL(saiban);

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

            //登録済みのスケジュール通知予定リストに「リマインダー通知時間区分」を設定
            __updateSchPushReminder(con);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL(
            MlCountMtController saiban) throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        //==== Push通知対応 ====
        __addSql(sqlList, " drop table CMN_PUSH_TOKEN;");
        __addSql(sqlList, " drop table CMN_PUSH_SERVER_TOKEN;");
        __addSql(sqlList, " drop table CMN_PUSH_SERVER_OTP;"); //廃止
        __addSql(sqlList, " drop table CMN_PUSH_ERROR_INFO;");

        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" create table MBL_PUSH_TOKEN");
        sql.addSql(" (");
        sql.addSql("      MPT_PUSH_TOKEN    varchar(256)        not null,");
        sql.addSql("      USR_SID           integer             not null,");
        sql.addSql("      CMU_UID           varchar(50)         not null,");
        sql.addSql("      MPT_APP_ID        varchar(256)        not null,");
        sql.addSql("      primary key (MPT_PUSH_TOKEN)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MBL_PUSH_SERVER_TOKEN");
        sql.addSql(" (");
        sql.addSql("      PST_TOKEN         varchar(256)        not null,");
        sql.addSql("      PST_LIMIT         timestamp          not null,");
        sql.addSql("      primary key (PST_TOKEN)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MBL_PUSH_ERROR_INFO");
        sql.addSql(" (");
        sql.addSql("      PEI_ECODE        varchar(75)        not null,");
        sql.addSql("      PEI_MESSAGE      varchar(150)       not null,");
        sql.addSql("      PEI_AUID         integer            not null,");
        sql.addSql("      PEI_ADATE        timestamp          not null");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MBL_PUSH_TARGET_IGNORE");
        sql.addSql(" (");
        sql.addSql("     USR_SID             integer         not null,");
        sql.addSql("     APP_ID              varchar(100)    not null,");
        sql.addSql("     PLUGIN_ID           varchar(20)     not null,");
        sql.addSql("     PUC_SUBKBN          varchar(20)     not null,");
        sql.addSql("     PUC_SUBTARGET_SID   integer         not null,");
        sql.addSql("     primary key (USR_SID, APP_ID, PLUGIN_ID, PUC_SUBKBN, PUC_SUBTARGET_SID)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        //スケジュール通知設定
        sql.addSql(" alter table SCH_DATA drop column SCD_TARGET_APP;");
        sql.addSql(" alter table SCH_DATA drop column SCD_TARGET_PC;");
        sql.addSql(" alter table SCH_EXDATA drop column SCE_TARGET_APP;");
        sql.addSql(" alter table SCH_EXDATA drop column SCE_TARGET_PC;");
        sql.addSql(" alter table SCH_PUSH_LIST drop column SPL_TARGET_APP;");
        sql.addSql(" alter table SCH_PUSH_LIST drop column SPL_TARGET_PC;");
        sql.addSql(" alter table SCH_PUSH_LIST add column SPL_REMINDER_KBN integer not null default 0;");
        sql.addSql(" alter table SCH_PRI_CONF drop column SCC_TARGET_APP;");
        sql.addSql(" alter table SCH_PRI_CONF drop column SCC_TARGET_PC;");

        __addSql(sqlList,
            "create table MBL_PUSH_UID"
            +" ("
            +"     CMU_UID           varchar(50)         not null,"
            +"     USR_SID           integer             not null,"
            +"     MPI_LOGINDATE     timestamp          not null,"
            +"     primary key (CMU_UID)"
            +" ) ;"
            );
        __addSql(sqlList,
            "create index MBL_PUSH_TOKEN_INDEX1 on MBL_PUSH_TOKEN(USR_SID, CMU_UID);"
            );
        __addSql(sqlList,
            "create index MBL_PUSH_UID_INDEX1 on MBL_PUSH_UID(USR_SID, CMU_UID);"
            );

        //==== Push通知対応 ====

        return sqlList;
    }
    /**
     * <br>[機  能] 指定されたSQL文をSqlBufferに設定し、実行SQLリストへ追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList 実行SQLリスト
     * @param sql SQL文
     */
    private void __addSql(ArrayList<SqlBuffer> sqlList, String sql) {
        SqlBuffer sqlBuffer = new SqlBuffer();
        sqlBuffer.addSql(sql);
        sqlList.add(sqlBuffer);
    }

    /**
     * スケジュール通知予定リストに「リマインダー通知時間区分」を設定
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __updateSchPushReminder(Connection con) throws SQLException{
        
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            //更新対象となる通知予定リストの一覧を取得
            List<SchPushListModel> reminderList = new ArrayList<SchPushListModel>();
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_PUSH_LIST.SCD_SID as SCD_SID,");
            sql.addSql("   SCH_PUSH_LIST.USR_SID as USR_SID,");
            sql.addSql("   SCH_PUSH_LIST.SPL_REMINDER as SPL_REMINDER,");
            sql.addSql("   SCH_DATA.SCD_FR_DATE as SCD_FR_DATE");
            sql.addSql(" from");
            sql.addSql("   SCH_PUSH_LIST,");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where");
            sql.addSql("   SCH_PUSH_LIST.SCD_SID = SCH_DATA.SCD_SID");

            log__.info(sql.toLogString());
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql.toSqlString());
            while (rs.next()) {
                SchPushListModel model = new SchPushListModel();
                model.setScdSid(rs.getInt("SCD_SID"));
                model.setUsrSid(rs.getInt("USR_SID"));
                model.setSplReminder(UDate.getInstanceTimestamp(rs.getTimestamp("SPL_REMINDER")));
                model.setScdFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_FR_DATE")));
                reminderList.add(model);
            }

            //スケジュール開始日時、リマインダー通知時間から算出したリマインダー通知区分を
            //スケジュール_通知予定リストに設定する
            sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" set");
            sql.addSql("   SPL_REMINDER_KBN = ?");
            sql.addSql(" where");
            sql.addSql("   SCD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (SchPushListModel model : reminderList) {
                int reminderKbn = __getRiminderKbn(model.getScdFrDate(), model.getSplReminder());
                sql.addIntValue(reminderKbn);
                sql.addIntValue(model.getScdSid());
                sql.addIntValue(model.getUsrSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
                pstmt.clearParameters();
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * 実行時間とスケジュール開始時間の差からリマインダー区分を取得する
     * @param schTime スケジュール開始時間
     * @param splReminder リマインダー通知予定時刻
     * @return リマインダー区分
     */
    private int __getRiminderKbn(UDate schTime, UDate splReminder) {
        schTime.setSecond(0);
        schTime.setMilliSecond(0);
        splReminder.setSecond(0);
        splReminder.setMilliSecond(0);
        long diff = UDateUtil.diffMillis(schTime, splReminder);

        //0分前
        if (diff < 60000) {
            return GSConstSchedule.REMINDER_TIME_ZERO;
        }
        //5分前
        if (diff <= 300000) {
            return GSConstSchedule.REMINDER_TIME_FIVE_MINUTES;
        }
        //10分前
        if (diff <= 600000) {
            return GSConstSchedule.REMINDER_TIME_TEN_MINUTES;
        }
        //15分前
        if (diff <= 900000) {
            return GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES;
        }
        //30分前
        if (diff <= 1800000) {
            return GSConstSchedule.REMINDER_TIME_THIRTY_MINUTES;
        }
        //1時間前
        if (diff <= 3600000) {
            return GSConstSchedule.REMINDER_TIME_ONE_HOUR;
        }
        //2時間前
        if (diff <= 7200000) {
            return GSConstSchedule.REMINDER_TIME_TWO_HOUR;
        }
        //3時間前
        if (diff <= 10800000) {
            return GSConstSchedule.REMINDER_TIME_THREE_HOUR;
        }
        //6時間前
        if (diff <= 21600000) {
            return GSConstSchedule.REMINDER_TIME_SIX_HOUR;
        }
        //12時間前
        if (diff <= 43200000) {
            return GSConstSchedule.REMINDER_TIME_TWELVE_HOUR;
        }   
        //1日前
        if (diff <= 86400000) {
            return GSConstSchedule.REMINDER_TIME_ONE_DAY;
        }    
        //2日前
        if (diff <= 172800000) {
            return GSConstSchedule.REMINDER_TIME_TWO_DAY;
        }    
        //1週前
        if (diff <= 604800000) {
            return GSConstSchedule.REMINDER_TIME_ONE_WEEK;
        }    

        return GSConstSchedule.REMINDER_TIME_ZERO;
    }
}
