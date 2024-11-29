package jp.groupsession.v2.convert.convert321.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v3.2.1へコンバートする際に使用する
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
     * @param mtCon 採番コントローラ
     * @throws SQLException 例外
     */
    public void convert(MlCountMtController mtCon) throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //create Table or alter table
        createTable();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL();

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

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
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL() throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;

        //スマートフォン　テーマ
        sql = new SqlBuffer();
        sql.addSql(" create table MBL_THEME");
        sql.addSql(" (");
        sql.addSql(" MBT_SID integer not null,");
        sql.addSql(" MBT_ID  varchar(20) not null,");
        sql.addSql(" MBT_NAME varchar(50) not null,");
        sql.addSql(" MBT_AUID integer not null,");
        sql.addSql(" MBT_ADATE timestamp not null,");
        sql.addSql(" MBT_EUID integer not null,");
        sql.addSql(" MBT_EDATE timestamp not null,");
        sql.addSql(" primary key (MBT_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MBL_USR_THEME");
        sql.addSql(" (");
        sql.addSql(" USR_SID integer not null,");
        sql.addSql(" MBT_SID integer not null,");
        sql.addSql(" MUT_AUID integer not null,");
        sql.addSql(" MUT_ADATE timestamp not null,");
        sql.addSql(" MUT_EUID integer not null,");
        sql.addSql(" MUT_EDATE timestamp not null,");
        sql.addSql(" primary key (USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        //スマートフォンテーマ
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" MBL_THEME(");
        sql.addSql("   MBT_SID,");
        sql.addSql("   MBT_ID,");
        sql.addSql("   MBT_NAME,");
        sql.addSql("   MBT_AUID,");
        sql.addSql("   MBT_ADATE,");
        sql.addSql("   MBT_EUID,");
        sql.addSql("   MBT_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  1,");
        sql.addSql("  'b',");
        sql.addSql("  'デフォルト',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" MBL_THEME(");
        sql.addSql("   MBT_SID,");
        sql.addSql("   MBT_ID,");
        sql.addSql("   MBT_NAME,");
        sql.addSql("   MBT_AUID,");
        sql.addSql("   MBT_ADATE,");
        sql.addSql("   MBT_EUID,");
        sql.addSql("   MBT_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  2,");
        sql.addSql("  'a',");
        sql.addSql("  '黒',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" MBL_THEME(");
        sql.addSql("   MBT_SID,");
        sql.addSql("   MBT_ID,");
        sql.addSql("   MBT_NAME,");
        sql.addSql("   MBT_AUID,");
        sql.addSql("   MBT_ADATE,");
        sql.addSql("   MBT_EUID,");
        sql.addSql("   MBT_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  3,");
        sql.addSql("  'c',");
        sql.addSql("  'グレー',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" MBL_THEME(");
        sql.addSql("   MBT_SID,");
        sql.addSql("   MBT_ID,");
        sql.addSql("   MBT_NAME,");
        sql.addSql("   MBT_AUID,");
        sql.addSql("   MBT_ADATE,");
        sql.addSql("   MBT_EUID,");
        sql.addSql("   MBT_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  4,");
        sql.addSql("  'e',");
        sql.addSql("  'オレンジ',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //ショートメールCC・BCC
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ASAK DROP PRIMARY KEY;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ASAK add SMJ_SENDKBN integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ASAK add primary key (USR_SID, SMS_SID, SMJ_SENDKBN);");
        sqlList.add(sql);

//PostgreSQL
//        alter table sml_asak drop constraint sml_asak_pkey;
//        alter table SML_ASAK add SMJ_SENDKBN integer not null default 0;
//        alter table SML_ASAK add constraint table_key primary key (USR_SID, SMS_SID, SMJ_SENDKBN);

        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS add SMJ_SENDKBN integer not null default 0;");
        sqlList.add(sql);

        //プロジェクトメンバーソート
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_MEMBERS add PRM_SORT integer not null default 1;");
        sqlList.add(sql);

        //稟議テンプレートカテゴリー
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATE_CATEGORY");
        sql.addSql(" (");
        sql.addSql(" RTC_SID integer not null,");
        sql.addSql(" RTC_TYPE integer not null,");
        sql.addSql(" USR_SID integer,");
        sql.addSql(" RTC_SORT integer not null,");
        sql.addSql(" RTC_NAME varchar(20) not null,");
        sql.addSql(" RTC_AUID integer not null,");
        sql.addSql(" RTC_ADATE timestamp not null,");
        sql.addSql(" RTC_EUID integer not null,");
        sql.addSql(" RTC_EDATE timestamp not null,");
        sql.addSql(" primary key (RTC_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE add RTC_SID integer not null default 0;");
        sqlList.add(sql);

        //WEBメールインデックス
        sql = new SqlBuffer();
        sql.addSql(" create index WML_MAIL_LOG_INDEX1 on WML_MAIL_LOG(WLG_DATE);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_MAILDATA_INDEX2 on WML_MAILDATA(WMD_SDATE);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_DIRECTORY_INDEX1 on WML_DIRECTORY(WAC_SID, WDR_TYPE);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_LABEL_RELATION_INDEX1 on WML_LABEL_RELATION(WLB_SID);");
        sqlList.add(sql);

        return sqlList;
    }
}