package jp.groupsession.v2.convert.convert210.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v2.1.0へコンバートする際に使用する
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
     * @throws SQLException 例外
     */
    public void convert() throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //新規テーブルのcreate、insertを行う
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

            //WEB検索のインターネット検索管理者設定のテーブルにレコードが無い場合はSQLを追加する
            sqlList = __getSrhAconfRecord(con, sqlList);

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
     */
    private List<SqlBuffer> __createSQL() {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("create table CMN_FILE_CONF");
        sql.addSql("(");
        sql.addSql("        FIC_MAX_SIZE integer   not null,");
        sql.addSql("        FIC_AUID     integer   not null,");
        sql.addSql("        FIC_ADATE    timestamp not null,");
        sql.addSql("        FIC_EUID     integer   not null,");
        sql.addSql("        FIC_EDATE    timestamp not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert ");
        sql.addSql(" into ");
        sql.addSql(" CMN_FILE_CONF(");
        sql.addSql("   FIC_MAX_SIZE,");
        sql.addSql("   FIC_AUID,");
        sql.addSql("   FIC_ADATE,");
        sql.addSql("   FIC_EUID,");
        sql.addSql("   FIC_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   10,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //メインDBコンバート
        sql = new SqlBuffer();
        sql.addSql("alter table CMN_MDISP add MDP_RELOAD Integer default 600000;");
        sqlList.add(sql);

        //スケジュールDBコンバート
        sql = new SqlBuffer();
        sql.addSql("alter table SCH_ADM_CONF add SAD_HOUR_DIV Integer default 10");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table SCH_PRI_CONF add SCC_SMAIL Integer not null default 0");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table SCH_PRI_CONF add SCC_SMAIL_GROUP Integer not null default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("alter table TCD_PRI_CONF add TPC_ZSK_STS Integer default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table SCH_COL_MSG");
        sql.addSql("(");
        sql.addSql("    SCM_ID      integer    not null,");
        sql.addSql("    SCM_MSG     varchar(30),");
        sql.addSql("    SCM_AUID    integer    not null,");
        sql.addSql("    SCM_ADATE   timestamp  not null,");
        sql.addSql("    SCM_EUID    integer    not null,");
        sql.addSql("    SCM_EDATE   timestamp  not null,");
        sql.addSql("    primary key (SCM_ID)");
        sql.addSql(") ");
        sqlList.add(sql);

        //ショートメールDBコンバート
        sql = new SqlBuffer();
        sql.addSql("create table SML_ADMIN");
        sql.addSql("(");
        sql.addSql("    SMA_MAILFW     integer      not null,");
        sql.addSql("    SMA_SMTPURL    varchar(200),");
        sql.addSql("    SMA_SMTP_PORT  varchar(5),");
        sql.addSql("    SMA_SMTP_USER  varchar(20),");
        sql.addSql("    SMA_SMTP_PASS  varchar(44),");
        sql.addSql("    SMA_FROM_ADD   varchar(50),");
        sql.addSql("    SMA_AUID       integer      not null,");
        sql.addSql("    SMA_ADATE      timestamp    not null,");
        sql.addSql("    SMA_EUID       integer      not null,");
        sql.addSql("    SMA_EDATE      timestamp    not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_MAILFW Integer default 0");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_MAIL_DF varchar(50)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_HURIWAKE Integer default 0");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_ZMAIL1 varchar(50)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_ZMAIL2 varchar(50)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_ZMAIL3 varchar(50)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_SMAIL_OP Integer default 0");
        sqlList.add(sql);

        //掲示板DBコンバート
        sql = new SqlBuffer();
        sql.addSql("alter table BBS_USER add BUR_THRE_IMAGE Integer not null default 0");
        sqlList.add(sql);

        //RSSリーダーDBコンバート
        sql = new SqlBuffer();
        sql.addSql("create table RSS_INFOM");
        sql.addSql("(");
        sql.addSql("    RSS_SID           integer not null,");
        sql.addSql("    RSM_URL_FEED      varchar(6000) not null,");
        sql.addSql("    RSM_FEEDDATA      bytea,");
        sql.addSql("    RSM_UPDATE_TIME   timestamp not null,");
        sql.addSql("    RSM_AUID          integer not null,");
        sql.addSql("    RSM_ADATE         timestamp not null,");
        sql.addSql("    RSM_EUID          integer not null,");
        sql.addSql("    RSM_EDATE         timestamp not null,");
        sql.addSql("    primary key (RSS_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table RSS_DATA");
        sql.addSql("(");
        sql.addSql("    RSS_SID         integer not null,");
        sql.addSql("    USR_SID         integer not null,");
        sql.addSql("    RSD_TITLE       varchar(150) not null,");
        sql.addSql("    RSD_URL_FEED    varchar(6000) not null,");
        sql.addSql("    RSD_URL         varchar(6000) not null,");
        sql.addSql("    RSD_VIEW        integer not null,");
        sql.addSql("    RSD_MAIN_VIEW   integer not null,");
        sql.addSql("    RSD_FEED_COUNT  integer not null,");
        sql.addSql("    RSD_AUID        integer not null,");
        sql.addSql("    RSD_ADATE       timestamp not null,");
        sql.addSql("    RSD_EUID        integer not null,");
        sql.addSql("    RSD_EDATE       timestamp not null,");
        sql.addSql("    primary key (RSS_SID, USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table RSS_ACONF");
        sql.addSql("(");
        sql.addSql("    RAC_READTIME      integer      not null,");
        sql.addSql("    RAC_AUID          integer      not null,");
        sql.addSql("    RAC_ADATE         timestamp    not null,");
        sql.addSql("    RAC_EUID          integer      not null,");
        sql.addSql("    RAC_EDATE         timestamp    not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("insert into RSS_ACONF");
        sql.addSql("(");
        sql.addSql("    RAC_READTIME,");
        sql.addSql("    RAC_AUID,");
        sql.addSql("    RAC_ADATE,");
        sql.addSql("    RAC_EUID,");
        sql.addSql("    RAC_EDATE");
        sql.addSql(")");
        sql.addSql("    values");
        sql.addSql("(");
        sql.addSql("    30,");
        sql.addSql("    0,");
        sql.addSql("    current_timestamp,");
        sql.addSql("    0,");
        sql.addSql("    current_timestamp");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table RSS_POSITION");
        sql.addSql("(");
        sql.addSql("    RSS_SID       integer not null,");
        sql.addSql("    USR_SID       integer not null,");
        sql.addSql("    RSP_POSITION  integer not null,");
        sql.addSql("    RSP_ORDER     integer not null,");
        sql.addSql("    RSP_AUID      integer not null,");
        sql.addSql("    RSP_ADATE     timestamp not null,");
        sql.addSql("    RSP_EUID      integer not null,");
        sql.addSql("    RSP_EDATE     timestamp not null,");
        sql.addSql("    primary key (RSS_SID, USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table RSS_POSITION_MAIN");
        sql.addSql("(");
        sql.addSql("    RSS_SID       integer not null,");
        sql.addSql("    USR_SID       integer not null,");
        sql.addSql("    RPM_POSITION  integer not null,");
        sql.addSql("    RPM_ORDER     integer not null,");
        sql.addSql("    RPM_AUID      integer not null,");
        sql.addSql("    RPM_ADATE     timestamp not null,");
        sql.addSql("    RPM_EUID      integer not null,");
        sql.addSql("    RPM_EDATE     timestamp not null,");
        sql.addSql("    primary key (RSS_SID, USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        //IP管理DBコンバート
        sql = new SqlBuffer();
        sql.addSql("create table IPK_NET");
        sql.addSql("(");
        sql.addSql("    INT_SID integer not null,");
        sql.addSql("    INT_NAME varchar (50) not null,");
        sql.addSql("    INT_NETAD varchar (15) not null,");
        sql.addSql("    INT_SABNET varchar (15) not null,");
        sql.addSql("    INT_DSP integer not null,");
        sql.addSql("    INT_MSG varchar (1000),");
        sql.addSql("    INT_SORT integer not null,");
        sql.addSql("    INT_AUID integer not null,");
        sql.addSql("    INT_ADATE timestamp not null,");
        sql.addSql("    INT_EUID integer not null,");
        sql.addSql("    INT_EDATE timestamp not null,");
        sql.addSql("    primary key (INT_SID) ");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table IPK_NET_ADM");
        sql.addSql("(");
        sql.addSql("    INT_SID        integer not null,");
        sql.addSql("    USR_SID        integer not null,");
        sql.addSql("    INC_AUID       integer not null,");
        sql.addSql("    INC_ADATE      timestamp not null,");
        sql.addSql("    INC_EUID       integer not null,");
        sql.addSql("    INC_EDATE      timestamp not null,");
        sql.addSql("    primary key (INT_SID, USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table IPK_ADD");
        sql.addSql("(");
        sql.addSql("    IAD_SID        integer not null,");
        sql.addSql("    INT_SID        integer not null,");
        sql.addSql("    IAD_NAME       varchar (50) not null,");
        sql.addSql("    IAD_IPAD       decimal not null,");
        sql.addSql("    IAD_USED_KBN   integer not null,");
        sql.addSql("    IAD_MSG        varchar (1000),");
        sql.addSql("    IAD_PRTMNG_NUM varchar (50),");
        sql.addSql("    IAD_CPU        integer not null,");
        sql.addSql("    IAD_MEMORY     integer not null,");
        sql.addSql("    IAD_HD         integer not null,");
        sql.addSql("    IAD_AUID       integer not null,");
        sql.addSql("    IAD_ADATE      timestamp not null,");
        sql.addSql("    IAD_EUID       integer not null,");
        sql.addSql("    IAD_EDATE      timestamp not null,");
        sql.addSql("    primary key (IAD_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table IPK_ADD_ADM");
        sql.addSql("(");
        sql.addSql("    IAD_SID        integer not null,");
        sql.addSql("    USR_SID        integer not null,");
        sql.addSql("    IAC_AUID       integer not null,");
        sql.addSql("    IAC_ADATE      timestamp not null,");
        sql.addSql("    IAC_EUID       integer not null,");
        sql.addSql("    IAC_EDATE      timestamp not null,");
        sql.addSql("    primary key (IAD_SID, USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table IPK_BIN");
        sql.addSql("(");
        sql.addSql("    BIN_SID integer not null,");
        sql.addSql("    INT_SID integer not null,");
        sql.addSql("    IAD_SID integer not null,");
        sql.addSql("    BIN_DSP integer");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table IPK_SPECM");
        sql.addSql("(");
        sql.addSql("    ISM_SID        integer not null,");
        sql.addSql("    ISM_KBN        integer not null,");
        sql.addSql("    ISM_NAME       varchar (50) not null,");
        sql.addSql("    ISM_LEVEL      integer not null,");
        sql.addSql("    ISM_BIKO       varchar(1000),");
        sql.addSql("    ISM_AUID       integer not null,");
        sql.addSql("    ISM_ADATE      timestamp not null,");
        sql.addSql("    ISM_EUID       integer not null,");
        sql.addSql("    ISM_EDATE      timestamp not null,");
        sql.addSql("    primary key (ISM_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        //プロジェクト管理DBコンバート
        sql = new SqlBuffer();
        sql.addSql("create table PRJ_MEMBERS_TMP");
        sql.addSql("(");
        sql.addSql("    PRT_SID           integer         not null,");
        sql.addSql("    USR_SID           integer         not null,");
        sql.addSql("    PMT_EMPLOYEE_KBN  integer         not null,");
        sql.addSql("    PMT_ADMIN_KBN     integer         not null,");
        sql.addSql("    PMT_AUID          integer         not null,");
        sql.addSql("    PMT_ADATE         timestamp       not null,");
        sql.addSql("    PMT_EUID          integer         not null,");
        sql.addSql("    PMT_EDATE         timestamp       not null,");
        sql.addSql("    PMT_MEM_KEY       varchar(20),");
        sql.addSql("    primary key (PRT_SID, USR_SID, PMT_EMPLOYEE_KBN)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_PRJDATA_TMP");
        sql.addSql("(");
        sql.addSql("    PRT_SID         integer        not null,");
        sql.addSql("    PRT_KBN         integer        not null,");
        sql.addSql("    PRT_TMP_NAME    varchar(50)    not null,");
        sql.addSql("    PRT_USR_SID     integer        not null,");
        sql.addSql("    PRT_ID          varchar(20),");
        sql.addSql("    PRT_NAME        varchar(50),");
        sql.addSql("    PRT_NAME_SHORT  varchar(20),");
        sql.addSql("    PRT_YOSAN       integer,");
        sql.addSql("    PRT_KOUKAI_KBN  integer        not null,");
        sql.addSql("    PRT_DATE_START  timestamp,");
        sql.addSql("    PRT_DATE_END    timestamp,");
        sql.addSql("    PRT_STATUS_SID  integer        not null,");
        sql.addSql("    PRT_TARGET      varchar(300),");
        sql.addSql("    PRT_CONTENT     varchar(1000),");
        sql.addSql("    PRT_MAIL_KBN    integer        not null,");
        sql.addSql("    PRT_EDIT        integer        not null,");
        sql.addSql("    PRT_AUID        integer        not null,");
        sql.addSql("    PRT_ADATE       timestamp      not null,");
        sql.addSql("    PRT_EUID        integer        not null,");
        sql.addSql("    PRT_EDATE       timestamp      not null,");
        sql.addSql("    primary key (PRT_SID) ");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_PRJSTATUS_TMP");
        sql.addSql("(");
        sql.addSql("    PRT_SID         integer         not null,");
        sql.addSql("    PTT_SID         integer         not null,");
        sql.addSql("    PTT_SORT        integer         not null,");
        sql.addSql("    PTT_NAME        varchar(20)     not null,");
        sql.addSql("    PTT_RATE        integer         not null,");
        sql.addSql("    PTT_AUID        integer         not null,");
        sql.addSql("    PTT_ADATE       timestamp       not null,");
        sql.addSql("    PTT_EUID        integer         not null,");
        sql.addSql("    PTT_EDATE       timestamp       not null,");
        sql.addSql("    primary key (PRT_SID, PTT_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table PRJ_TODOCATEGORY_TMP");
        sql.addSql("(");
        sql.addSql("    PRT_SID           integer         not null,");
        sql.addSql("    PCT_CATEGORY_SID  integer         not null,");
        sql.addSql("    PCT_SORT          integer         not null,");
        sql.addSql("    PCT_NAME          varchar(20)     not null,");
        sql.addSql("    PCT_AUID          integer         not null,");
        sql.addSql("    PCT_ADATE         timestamp       not null,");
        sql.addSql("    PCT_EUID          integer         not null,");
        sql.addSql("    PCT_EDATE         timestamp       not null,");
        sql.addSql("    primary key (PRT_SID, PCT_CATEGORY_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODOSTATUS_TMP");
        sql.addSql("(");
        sql.addSql("    PRT_SID         integer         not null,");
        sql.addSql("    PST_SID         integer         not null,");
        sql.addSql("    PST_NAME        varchar(20)     not null,");
        sql.addSql("    PST_RATE        integer         not null,");
        sql.addSql("    PST_SORT        integer         not null,");
        sql.addSql("    PST_AUID        integer         not null,");
        sql.addSql("    PST_ADATE       timestamp       not null,");
        sql.addSql("    PST_EUID        integer         not null,");
        sql.addSql("    PST_EDATE       timestamp       not null,");
        sql.addSql("    primary key (PRT_SID, PST_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODOCATEGORY");
        sql.addSql("(");
        sql.addSql("    PRJ_SID          integer         not null,");
        sql.addSql("    PTC_CATEGORY_SID integer         not null,");
        sql.addSql("    PTC_SORT         integer         not null,");
        sql.addSql("    PTC_NAME         varchar(20)     not null,");
        sql.addSql("    PTC_AUID         integer         not null,");
        sql.addSql("    PTC_ADATE        timestamp       not null,");
        sql.addSql("    PTC_EUID         integer         not null,");
        sql.addSql("    PTC_EDATE        timestamp       not null,");
        sql.addSql("    primary key (PRJ_SID, PTC_CATEGORY_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODOCOMMENT");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer         not null,");
        sql.addSql("    PTD_SID         integer         not null,");
        sql.addSql("    PCM_SID         integer         not null,");
        sql.addSql("    PCM_COMMENT     varchar(500)    not null,");
        sql.addSql("    PCM_AUID        integer         not null,");
        sql.addSql("    PCM_ADATE       timestamp       not null,");
        sql.addSql("    PCM_EUID        integer         not null,");
        sql.addSql("    PCM_EDATE       timestamp       not null,");
        sql.addSql("    primary key (PRJ_SID, PTD_SID, PCM_SID) ");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODO_BIN");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer         not null,");
        sql.addSql("    PTD_SID         integer         not null,");
        sql.addSql("    BIN_SID         integer         not null,");
        sql.addSql("    USR_SID         integer         not null,");
        sql.addSql("    primary key (PRJ_SID, PTD_SID, BIN_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_STATUS_HISTORY");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer         not null,");
        sql.addSql("    PTD_SID         integer         not null,");
        sql.addSql("    PSH_SID         integer         not null,");
        sql.addSql("    PTS_SID         integer         not null,");
        sql.addSql("    PSH_REASON      varchar(500)    not null,");
        sql.addSql("    PSH_AUID        integer         not null,");
        sql.addSql("    PSH_ADATE       timestamp       not null,");
        sql.addSql("    PSH_EUID        integer         not null,");
        sql.addSql("    PSH_EDATE       timestamp       not null,");
        sql.addSql("    primary key (PRJ_SID, PTD_SID, PSH_SID) ");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_PRJSTATUS");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer         not null,");
        sql.addSql("    PRS_SID         integer         not null,");
        sql.addSql("    PRS_SORT        integer         not null,");
        sql.addSql("    PRS_NAME        varchar(20)     not null,");
        sql.addSql("    PRS_RATE        integer         not null,");
        sql.addSql("    PRS_AUID        integer         not null,");
        sql.addSql("    PRS_ADATE       timestamp       not null,");
        sql.addSql("    PRS_EUID        integer         not null,");
        sql.addSql("    PRS_EDATE       timestamp       not null,");
        sql.addSql("    primary key (PRJ_SID, PRS_SID) ");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_PRJDATA");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer    not null,");
        sql.addSql("    PRJ_MY_KBN      integer    not null,");
        sql.addSql("    PRJ_ID          varchar(20)  not null,");
        sql.addSql("    PRJ_NAME        varchar(50)  not null,");
        sql.addSql("    PRJ_NAME_SHORT  varchar(20)  not null,");
        sql.addSql("    PRJ_YOSAN       integer    ,");
        sql.addSql("    PRJ_KOUKAI_KBN  integer    not null,");
        sql.addSql("    PRJ_DATE_START  timestamp  ,");
        sql.addSql("    PRJ_DATE_END    timestamp  ,");
        sql.addSql("    PRJ_STATUS_SID  integer    not null,");
        sql.addSql("    PRJ_TARGET      varchar(300)  ,");
        sql.addSql("    PRJ_CONTENT     varchar(1000)  ,");
        sql.addSql("    PRJ_EDIT        integer    not null,");
        sql.addSql("    PRJ_MAIL_KBN    integer    not null,");
        sql.addSql("    PRJ_AUID        integer    not null,");
        sql.addSql("    PRJ_ADATE       timestamp  not null,");
        sql.addSql("    PRJ_EUID        integer    not null,");
        sql.addSql("    PRJ_EDATE       timestamp  not null,");
        sql.addSql("    primary key (PRJ_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_MEMBERS");
        sql.addSql("(");
        sql.addSql("    PRJ_SID          integer         not null,");
        sql.addSql("    USR_SID          integer         not null,");
        sql.addSql("    PRM_EMPLOYEE_KBN integer         not null,");
        sql.addSql("    PRM_ADMIN_KBN    integer         not null,");
        sql.addSql("    PRM_AUID         integer         not null,");
        sql.addSql("    PRM_ADATE        timestamp       not null,");
        sql.addSql("    PRM_EUID         integer         not null,");
        sql.addSql("    PRM_EDATE        timestamp       not null,");
        sql.addSql("    PRM_MEM_KEY      varchar(20),");
        sql.addSql("    primary key (PRJ_SID, USR_SID, PRM_EMPLOYEE_KBN) ");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_ADM_CONF");
        sql.addSql("(");
        sql.addSql("    PAC_PRJ_EDIT   integer    not null,");
        sql.addSql("    PAC_AUID       integer    not null,");
        sql.addSql("    PAC_ADATE      timestamp  not null,");
        sql.addSql("    PAC_EUID       integer    not null,");
        sql.addSql("    PAC_EDATE      timestamp  not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("insert into PRJ_ADM_CONF");
        sql.addSql("(");
        sql.addSql("    PAC_PRJ_EDIT,");
        sql.addSql("    PAC_AUID,");
        sql.addSql("    PAC_ADATE,");
        sql.addSql("    PAC_EUID,");
        sql.addSql("    PAC_EDATE");
        sql.addSql(")");
        sql.addSql("    values");
        sql.addSql("(");
        sql.addSql("    0,");
        sql.addSql("    0,");
        sql.addSql("    current_timestamp,");
        sql.addSql("    0,");
        sql.addSql("    current_timestamp");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODODATA");
        sql.addSql("(");
        sql.addSql("    PRJ_SID          integer    not null,");
        sql.addSql("    PTD_SID          integer    not null,");
        sql.addSql("    PTD_NO           integer    not null,");
        sql.addSql("    PTD_CATEGORY     integer    not null,");
        sql.addSql("    PTD_TITLE        varchar(100)  not null,");
        sql.addSql("    PTD_DATE_PLAN    timestamp,");
        sql.addSql("    PRJ_DATE_LIMIT   timestamp,");
        sql.addSql("    PTD_DATE_START   timestamp,");
        sql.addSql("    PTD_DATE_END     timestamp,");
        sql.addSql("    PTD_PLAN_KOSU    decimal(4, 1),");
        sql.addSql("    PTD_RESULTS_KOSU decimal(4, 1),");
        sql.addSql("    PTD_ALARM_KBN    integer    not null,");
        sql.addSql("    PTD_IMPORTANCE   integer    not null,");
        sql.addSql("    PSH_SID          integer    not null,");
        sql.addSql("    PTS_SID          integer    not null,");
        sql.addSql("    PTD_CONTENT      varchar(1000)  ,");
        sql.addSql("    PTD_AUID         integer    not null,");
        sql.addSql("    PTD_ADATE        timestamp  not null,");
        sql.addSql("    PTD_EUID         integer    not null,");
        sql.addSql("    PTD_EDATE        timestamp  not null,");
        sql.addSql("    primary key (PRJ_SID, PTD_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODOMEMBER");
        sql.addSql("(");
        sql.addSql("    PRJ_SID          integer    not null,");
        sql.addSql("    PTD_SID          integer    not null,");
        sql.addSql("    USR_SID          integer    not null,");
        sql.addSql("    PTM_EMPLOYEE_KBN integer    not null,");
        sql.addSql("    PTM_AUID         integer    not null,");
        sql.addSql("    PTM_ADATE        timestamp  not null,");
        sql.addSql("    PTM_EUID         integer    not null,");
        sql.addSql("    PTM_EDATE        timestamp  not null,");
        sql.addSql("    primary key (PRJ_SID, PTD_SID, USR_SID, PTM_EMPLOYEE_KBN)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_TODOSTATUS");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer         not null,");
        sql.addSql("    PTS_SID         integer         not null,");
        sql.addSql("    PTS_NAME        varchar(20)     not null,");
        sql.addSql("    PTS_RATE        integer         not null,");
        sql.addSql("    PTS_SORT        integer         not null,");
        sql.addSql("    PTS_AUID        integer         not null,");
        sql.addSql("    PTS_ADATE       timestamp       not null,");
        sql.addSql("    PTS_EUID        integer         not null,");
        sql.addSql("    PTS_EDATE       timestamp       not null,");
        sql.addSql("    primary key (PRJ_SID, PTS_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_USER_CONF");
        sql.addSql("(");
        sql.addSql("    USR_SID       integer    not null,");
        sql.addSql("    PUC_PRJ_CNT   integer    not null,");
        sql.addSql("    PUC_TODO_CNT  integer    not null,");
        sql.addSql("    PUC_AUID      integer    not null,");
        sql.addSql("    PUC_ADATE     timestamp  not null,");
        sql.addSql("    PUC_EUID      integer    not null,");
        sql.addSql("    PUC_EDATE     timestamp  not null,");
        sql.addSql("    primary key (USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_FILE_BIN");
        sql.addSql("(");
        sql.addSql("    PDR_SID         integer      not null,");
        sql.addSql("    BIN_SID         integer      not null,");
        sql.addSql("    PFL_EXT         varchar(50),");
        sql.addSql("    PFL_FILE_SIZE   integer      not null,");
        sql.addSql("    primary key (PDR_SID, BIN_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table PRJ_DIRECTORY");
        sql.addSql("(");
        sql.addSql("    PRJ_SID         integer      not null,");
        sql.addSql("    PDR_SID         integer      not null,");
        sql.addSql("    PDR_PARENT_SID  integer      not null,");
        sql.addSql("    PDR_KBN         integer      not null,");
        sql.addSql("    PDR_LEVEL       integer      not null,");
        sql.addSql("    PDR_NAME        varchar(50)  not null,");
        sql.addSql("    PDR_NAIYO       varchar(1000),");
        sql.addSql("    PDR_BIKO        varchar(1000),");
        sql.addSql("    BIN_SID         integer      not null,");
        sql.addSql("    PDR_AUID        integer      not null,");
        sql.addSql("    PDR_ADATE       timestamp    not null,");
        sql.addSql("    PDR_EUID        integer      not null,");
        sql.addSql("    PDR_EDATE       timestamp    not null,");
        sql.addSql("    primary key (PRJ_SID, PDR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        //在席管理DBコンバート
        sql = new SqlBuffer();
        sql.addSql("create table ZAI_PRI_CONF");
        sql.addSql("(");
        sql.addSql("    USR_SID          integer         not null,");
        sql.addSql("    ZIF_SID          integer         not null,");
        sql.addSql("    ZPC_AID          integer         not null,");
        sql.addSql("    ZPC_ADATE        timestamp       not null,");
        sql.addSql("    ZPC_EID          integer         not null,");
        sql.addSql("    ZPC_EDATE        timestamp       not null,");
        sql.addSql("    primary key (USR_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table ZAI_INFO");
        sql.addSql("(");
        sql.addSql("    ZIF_SID          integer         not null,");
        sql.addSql("    ZIF_NAME         varchar(50)     not null,");
        sql.addSql("    BIN_SID          integer         not null,");
        sql.addSql("    ZIF_SORT         integer         not null,");
        sql.addSql("    ZIF_MSG          varchar(1000)           ,");
        sql.addSql("    ZIF_AUID         integer         not null,");
        sql.addSql("    ZIF_ADATE        timestamp       not null,");
        sql.addSql("    ZIF_EUID         integer         not null,");
        sql.addSql("    ZIF_EDATE        timestamp       not null,");
        sql.addSql("    primary key (ZIF_SID)");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table ZAI_INDEX");
        sql.addSql("(");
        sql.addSql("    ZIF_SID          integer         not null,");
        sql.addSql("    ZIN_LINKKBN      integer         not null,");
        sql.addSql("    ZIN_LINKSID      integer         not null,");
        sql.addSql("    ZIN_NAME         varchar(50)     not null,");
        sql.addSql("    ZIN_BGCOLOR      integer         not null,");
        sql.addSql("    ZIN_MSG          varchar(1000)           ,");
        sql.addSql("    ZIN_XINDEX       integer         not null,");
        sql.addSql("    ZIN_YINDEX       integer         not null,");
        sql.addSql("    ZIN_OTHER_VALUE  varchar(20)     not null,");
        sql.addSql("    ZIN_AUID         integer         not null,");
        sql.addSql("    ZIN_ADATE        timestamp       not null,");
        sql.addSql("    ZIN_EUID         integer         not null,");
        sql.addSql("    ZIN_EDATE        timestamp       not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table ZAI_ADM_CONF");
        sql.addSql("(");
        sql.addSql("    ZAC_NAISEN_KBN   integer         not null default 1,");
        sql.addSql("    ZAC_AID          integer         not null,");
        sql.addSql("    ZAC_ADATE        timestamp       not null,");
        sql.addSql("    ZAC_EID          integer         not null,");
        sql.addSql("    ZAC_EDATE        timestamp       not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table WK_ZAI_INDEX");
        sql.addSql("(");
        sql.addSql("    WZI_SESSION_SID  varchar(50)     not null,");
        sql.addSql("    WZI_KEY          varchar(50)     not null,");
        sql.addSql("    WZI_SID          integer         not null,");
        sql.addSql("    WZI_LINKKBN      integer         not null,");
        sql.addSql("    WZI_LINKSID      integer         not null,");
        sql.addSql("    WZI_NAME         varchar(50)     not null,");
        sql.addSql("    WZI_BGCOLOR      integer         not null,");
        sql.addSql("    WZI_MSG          varchar(1000)           ,");
        sql.addSql("    WZI_XINDEX       integer         not null,");
        sql.addSql("    WZI_YINDEX       integer         not null,");
        sql.addSql("    WZI_OTHER_VALUE  varchar(20)     not null");
        sql.addSql(") ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("insert into ZAI_ADM_CONF");
        sql.addSql("(");
        sql.addSql("    ZAC_NAISEN_KBN,");
        sql.addSql("    ZAC_AID,");
        sql.addSql("    ZAC_ADATE,");
        sql.addSql("    ZAC_EID,");
        sql.addSql("    ZAC_EDATE");
        sql.addSql(")");
        sql.addSql("    values");
        sql.addSql("(");
        sql.addSql("    1,");
        sql.addSql("    0,");
        sql.addSql("    current_timestamp,");
        sql.addSql("    0,");
        sql.addSql("    current_timestamp");
        sql.addSql(") ");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] WEB検索のSRH_ACONFにレコードが無い場合、SQL(insert)を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sqlList SQLリスト
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private List<SqlBuffer> __getSrhAconfRecord(Connection con, List<SqlBuffer> sqlList)
    throws SQLException {

        int count = getSrhAconfRecordCnt(con);
        if (count <= 0) {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("insert into SRH_ACONF");
            sql.addSql("(");
            sql.addSql("    SAC_RANK,");
            sql.addSql("    SAC_NEW,");
            sql.addSql("    SAC_LOGIN_VIEW,");
            sql.addSql("    SAC_LOGIN_RANK,");
            sql.addSql("    SAC_LOGIN_NEW,");
            sql.addSql("    SAC_LOGIN_IMG,");
            sql.addSql("    SAC_LOGIN_HIS,");
            sql.addSql("    SAC_AUID,");
            sql.addSql("    SAC_ADATE,");
            sql.addSql("    SAC_EUID,");
            sql.addSql("    SAC_EDATE");
            sql.addSql(")");
            sql.addSql("    values");
            sql.addSql("(");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    0,");
            sql.addSql("    current_timestamp,");
            sql.addSql("    0,");
            sql.addSql("    current_timestamp");
            sql.addSql(") ");
            sqlList.add(sql);
        }
        return sqlList;
    }

    /**
     * <br>[機  能] SRH_ACONFのレコード数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return SRH_ACONFModel
     * @throws SQLException SQL実行例外
     */
    private int getSrhAconfRecordCnt(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   SRH_ACONF");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
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
     * <br>[機  能] SML_ADMINにSMA_SMTP_PORTフィールドが存在するか確認する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @return ret true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistsSmtpPortField(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean ret = true;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMA_SMTP_PORT");
            sql.addSql(" from");
            sql.addSql("   SML_ADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            log__.debug("SML_ADMINにSMA_SMTP_PORTフィールドが存在する");

        } catch (SQLException e) {
            log__.error("SML_ADMINにSMA_SMTP_PORTフィールドが存在しない");
            ret = false;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] SML_ADMINにSMA_SMTP_PORTフィールドを追加する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void addColumnOfSmtpPort(Connection con) throws SQLException {

        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("alter table SML_ADMIN add SMA_SMTP_PORT varchar(5)");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log__.error("SML_ADMINにSMA_SMTP_PORTフィールド追加失敗");
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}