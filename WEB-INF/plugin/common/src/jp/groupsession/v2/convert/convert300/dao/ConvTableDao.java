package jp.groupsession.v2.convert.convert300.dao;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v3.0.0へコンバートする際に使用する
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

        //回覧板情報確認日次を設定する。
        __updatecirConfDate();

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

        //WEBメールテーブル
        sql = new SqlBuffer();
//        sql.addSql("CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\"");
        sql.addSql("CREATE ALIAS IF NOT EXISTS FTL_INIT FOR"
            + " \"org.h2.fulltext.FullTextLucene.init\"");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("CALL FTL_INIT();");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_DISK");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   WDS_SIZE        bigint        not null,");
        sql.addSql("   primary key (WAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_SORT");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   WAS_SORT        BIGINT       not null,");
        sql.addSql("   primary key (WAC_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_USER");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   GRP_SID         integer,");
        sql.addSql("   USR_SID         integer");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT");
        sql.addSql(" (");
        sql.addSql("   WAC_SID            integer       not null,");
        sql.addSql("   WAC_TYPE           integer       not null,");
        sql.addSql("   USR_SID            integer,");
        sql.addSql("   WAC_NAME           varchar(100)  not null,");
        sql.addSql("   WAC_ADDRESS        varchar(256)  not null,");
        sql.addSql("   WAC_SEND_HOST      varchar(100)  not null,");
        sql.addSql("   WAC_SEND_PORT      integer       not null,");
        sql.addSql("   WAC_SEND_USER      varchar(100),");
        sql.addSql("   WAC_SEND_PASS      varchar(100),");
        sql.addSql("   WAC_SEND_SSL       integer       not null,");
        sql.addSql("   WAC_RECEIVE_TYPE   integer       not null,");
        sql.addSql("   WAC_RECEIVE_HOST   varchar(100)  not null,");
        sql.addSql("   WAC_RECEIVE_PORT   integer       not null,");
        sql.addSql("   WAC_RECEIVE_USER   varchar(100)  not null,");
        sql.addSql("   WAC_RECEIVE_PASS   varchar(100)  not null,");
        sql.addSql("   WAC_RECEIVE_SSL    integer       not null,");
        sql.addSql("   WAC_DISK           integer       not null,");
        sql.addSql("   WAC_DISK_SIZE      integer       not null,");
        sql.addSql("   WAC_BIKO           varchar(1000),");
        sql.addSql("   WAC_ORGANIZATION   varchar(100),");
        sql.addSql("   WAC_SIGN           varchar(1000),");
        sql.addSql("   WAC_AUTOTO         varchar(100),");
        sql.addSql("   WAC_AUTOCC         varchar(100),");
        sql.addSql("   WAC_AUTOBCC        varchar(100),");
        sql.addSql("   WAC_DELRECEIVE     integer       not null,");
        sql.addSql("   WAC_RERECEIVE      integer       not null,");
        sql.addSql("   WAC_APOP           integer       not null,");
        sql.addSql("   WAC_SMTP_AUTH      integer       not null,");
        sql.addSql("   WAC_POPBSMTP       integer       not null,");
        sql.addSql("   WAC_ENCODE_SEND    integer       not null,");
        sql.addSql("   WAC_AUTORECEIVE    integer       not null,");
        sql.addSql("   WAC_SEND_MAILTYPE  integer       not null,");
        sql.addSql("   WAC_RECEIVE_DATE   timestamp,");
        sql.addSql("   WAC_JKBN           integer       not null,");
        sql.addSql("   primary key(WAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_ADM_CONF");
        sql.addSql(" (");
        sql.addSql("   WAD_ACNT_MAKE         integer      not null,");
        sql.addSql("   WAD_ACCT_SENDFORMAT   integer      not null");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_AUTODELETE_LOG");
        sql.addSql(" (");
        sql.addSql("   WAL_DEL_KBN            integer           not null,");
        sql.addSql("   WAL_DEL_YEAR           integer,");
        sql.addSql("   WAL_DEL_MONTH          integer,");
        sql.addSql("   WAL_DEL_DAY            integer,");
        sql.addSql("   WAL_AUID               integer           not null,");
        sql.addSql("   WAL_ADATE              timestamp         not null,");
        sql.addSql("   WAL_EUID               integer           not null,");
        sql.addSql("   WAL_EDATE              timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_AUTODELETE");
        sql.addSql(" (");
        sql.addSql("   WAD_DUST_KBN            integer           not null,");
        sql.addSql("   WAD_DUST_YEAR           integer,");
        sql.addSql("   WAD_DUST_MONTH          integer,");
        sql.addSql("   WAD_DUST_DAY            integer,");
        sql.addSql("   WAD_SEND_KBN            integer           not null,");
        sql.addSql("   WAD_SEND_YEAR           integer,");
        sql.addSql("   WAD_SEND_MONTH          integer,");
        sql.addSql("   WAD_SEND_DAY            integer,");
        sql.addSql("   WAD_DRAFT_KBN           integer           not null,");
        sql.addSql("   WAD_DRAFT_YEAR          integer,");
        sql.addSql("   WAD_DRAFT_MONTH         integer,");
        sql.addSql("   WAD_DRAFT_DAY           integer,");
        sql.addSql("   WAD_RESV_KBN            integer           not null,");
        sql.addSql("   WAD_RESV_YEAR           integer,");
        sql.addSql("   WAD_RESV_MONTH          integer,");
        sql.addSql("   WAD_RESV_DAY            integer,");
        sql.addSql("   WAD_KEEP_KBN            integer           not null,");
        sql.addSql("   WAD_KEEP_YEAR           integer,");
        sql.addSql("   WAD_KEEP_MONTH          integer,");
        sql.addSql("   WAD_KEEP_DAY            integer,");
        sql.addSql("   WAD_AUID                integer           not null,");
        sql.addSql("   WAD_ADATE               timestamp         not null,");
        sql.addSql("   WAD_EUID                integer           not null,");
        sql.addSql("   WAD_EDATE               timestamp         not null");

        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_DIRECTORY");
        sql.addSql(" (");
        sql.addSql("   WDR_SID      bigint          not null,");
        sql.addSql("   WAC_SID      integer         not null,");
        sql.addSql("   WDR_NAME     varchar(100)    not null,");
        sql.addSql("   WDR_TYPE     integer         not null,");
        sql.addSql("   WDR_DEFAULT  integer         not null,");
        sql.addSql("   WDR_VIEW     integer         not null,");
        sql.addSql("   primary key(WDR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_FILTER_CONDITION");
        sql.addSql(" (");
        sql.addSql("   WFT_SID         integer      not null,");
        sql.addSql("   WFC_NUM         integer      not null,");
        sql.addSql("   WFC_TYPE        integer      not null,");
        sql.addSql("   WFC_EXPRESSION  integer      not null,");
        sql.addSql("   WFC_TEXT        varchar(256) not null,");
        sql.addSql("   primary key(WFT_SID, WFC_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_FILTER_SORT");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   WFT_SID         integer      not null,");
        sql.addSql("   WFS_SORT        bigint       not null,");
        sql.addSql("   primary key (WAC_SID, WFT_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_FILTER");
        sql.addSql(" (");
        sql.addSql("   WFT_SID               integer not null,");
        sql.addSql("   USR_SID               integer not null,");
        sql.addSql("   WFT_NAME              varchar(100) not null,");
        sql.addSql("   WFT_TYPE              integer not null,");
        sql.addSql("   WAC_SID               integer,");
        sql.addSql("   WFT_TEMPFILE          integer not null,");
        sql.addSql("   WFT_ACTION_LABEL      integer not null,");
        sql.addSql("   WLB_SID               integer,");
        sql.addSql("   WFT_ACTION_READ       integer not null,");
        sql.addSql("   WFT_ACTION_DUST       integer not null,");
        sql.addSql("   WFT_ACTION_FORWARD    integer not null,");
        sql.addSql("   WFT_ACTION_FWADDRESS  varchar(256),");
        sql.addSql("   WFT_CONDITION         integer not null,");
        sql.addSql("   primary key(WFT_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_HEADER_DATA");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint         not null,");
        sql.addSql("   WMH_NUM      integer        not null,");
        sql.addSql("   WMH_TYPE     varchar(200)   not null,");
        sql.addSql("   WMH_CONTENT  varchar(1000)  not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WMH_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_LABEL_RELATION");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint       not null,");
        sql.addSql("   WLB_SID      integer      not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_LABEL");
        sql.addSql(" (");
        sql.addSql("   WLB_SID   integer      not null,");
        sql.addSql("   USR_SID   integer      not null,");
        sql.addSql("   WLB_NAME  varchar(100) not null,");
        sql.addSql("   WLB_TYPE  integer      not null,");
        sql.addSql("   WAC_SID   integer      not null,");
        sql.addSql("   WLB_ORDER integer      not null,");
        sql.addSql("   primary key(WLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_BODY");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint          not null,");
        sql.addSql("   WMB_BODY     text,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_LOG_SEND");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint        not null,");
        sql.addSql("   WLS_NUM      integer       not null,");
        sql.addSql("   WLS_TYPE     integer       not null,");
        sql.addSql("   WLS_ADDRESS  varchar(768)  not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WLS_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_LOG");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM   bigint          not null,");
        sql.addSql("   WLG_TITLE     varchar(1000)   not null,");
        sql.addSql("   WLG_DATE      timestamp,");
        sql.addSql("   WLG_FROM      varchar(256),");
        sql.addSql("   WLG_TEMPFLG   integer         not null,");
        sql.addSql("   WLG_MAILTYPE  integer         not null,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAILDATA");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint          not null,");
        sql.addSql("   WMD_TITLE    varchar(1000),");
        sql.addSql("   WMD_SDATE    timestamp,");
        sql.addSql("   WMD_FROM     varchar(256),");
        sql.addSql("   WMD_TEMPFLG  integer         not null,");
        sql.addSql("   WMD_STATUS   integer         not null,");
        sql.addSql("   WMD_REPLY    integer         not null,");
        sql.addSql("   WMD_FORWARD  integer         not null,");
        sql.addSql("   WMD_READED   integer         not null,");
        sql.addSql("   WDR_SID      bigint          not null,");
        sql.addSql("   WMD_SIZE     bigint          not null,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_SENDADDRESS");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint        not null,");
        sql.addSql("   WSA_NUM      integer       not null,");
        sql.addSql("   WSA_TYPE     integer       not null,");
        sql.addSql("   WSA_ADDRESS  varchar(768)  not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WSA_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_TEMPFILE");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM         bigint            not null,");
        sql.addSql("   WTF_SID             decimal(100, 0)   not null,");
        sql.addSql("   WTF_FILE_NAME       varchar(260)      not null,");
        sql.addSql("   WTF_FILE_PATH       varchar(20)       not null,");
        sql.addSql("   WTF_FILE_EXTENSION  varchar(20),");
        sql.addSql("   WTF_FILE_SIZE       bigint            not null,");
        sql.addSql("   WTF_AUID            integer           not null,");
        sql.addSql("   WTF_ADATE           timestamp         not null,");
        sql.addSql("   WTF_EUID            integer           not null,");
        sql.addSql("   WTF_EDATE           timestamp         not null,");
        sql.addSql("   WTF_JKBN            integer           not null,");
        sql.addSql("   WTF_HTMLMAIL        integer           not null,");
        sql.addSql("   WTF_CHARSET         varchar(50),");
        sql.addSql("   primary key(WMD_MAILNUM, WTF_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_UIDL");
        sql.addSql(" (");
        sql.addSql("   WAC_SID       integer       not null,");
        sql.addSql("   WUD_ACCOUNT   varchar(500)  not null,");
        sql.addSql("   WUD_UID       varchar(1000) not null,");
        sql.addSql("   primary key(WAC_SID, WUD_ACCOUNT, WUD_UID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAILDATA_SORT");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   WDR_SID         bigint       not null,");
        sql.addSql("   WLB_SID         integer      not null,");
        sql.addSql("   WMS_SORTKEY     integer      not null,");
        sql.addSql("   WMS_ORDER       integer      not null,");
        sql.addSql("   primary key (WAC_SID, USR_SID, WDR_SID, WLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("CALL FTL_CREATE_INDEX('PUBLIC', 'WML_MAIL_BODY', 'WMB_BODY');");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into WML_ADM_CONF ( WAD_ACNT_MAKE, WAD_ACCT_SENDFORMAT )");
        sql.addSql(" values ( 1, 0 );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create index WML_MAILDATA_INDEX on WML_MAILDATA( WDR_SID );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table ADR_CONTACT_PRJ");
        sql.addSql(" (");
        sql.addSql("   ADC_SID       integer           not null,");
        sql.addSql("   PRJ_SID       integer           not null,");
        sql.addSql("   ADC_AUID      integer           not null,");
        sql.addSql("   ADC_ADATE     timestamp         not null,");
        sql.addSql("   ADC_EUID      integer           not null,");
        sql.addSql("   ADC_EDATE     timestamp         not null,");
        sql.addSql("   primary key(ADC_SID, PRJ_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //スケジュールスケジュール個人設定テーブル変更
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF rename to SCH_PRI_CONF_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_PRI_CONF");
        sql.addSql(" (");
        sql.addSql("   USR_SID         integer         not null,");
        sql.addSql("   SCC_FR_DATE     timestamp       not null,");
        sql.addSql("   SCC_TO_DATE     timestamp       not null,");
        sql.addSql("   SCC_DSP_GROUP   integer         not null,");
        sql.addSql("   SCC_SORT_KEY1   integer         not null,");
        sql.addSql("   SCC_SORT_ORDER1 integer         not null,");
        sql.addSql("   SCC_SORT_KEY2   integer         not null,");
        sql.addSql("   SCC_SORT_ORDER2 integer         not null,");
        sql.addSql("   SCC_INI_FR_DATE timestamp       not null,");
        sql.addSql("   SCC_INI_TO_DATE timestamp       not null,");
        sql.addSql("   SCC_INI_PUBLIC  integer         not null,");
        sql.addSql("   SCC_INI_FCOLOR  integer         not null,");
        sql.addSql("   SCC_INI_EDIT    integer         not null,");
        sql.addSql("   SCC_DSP_LIST    integer         not null,");
        sql.addSql("   SCC_DSP_MYGROUP integer         ,");
        sql.addSql("   SCC_SMAIL       integer         not null,");
        sql.addSql("   SCC_SMAIL_GROUP integer         not null,");
        sql.addSql("   SCC_RELOAD      integer         not null,");
        sql.addSql("   SCC_INI_WEEK    integer         not null,");
        sql.addSql("   SCC_SORT_EDIT   integer         not null,");
        sql.addSql("   SCC_AUID        integer         not null,");
        sql.addSql("   SCC_ADATE       timestamp       not null,");
        sql.addSql("   SCC_EUID        integer         not null,");
        sql.addSql("   SCC_EDATE       timestamp       not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into SCH_PRI_CONF");
        sql.addSql(" (");
        sql.addSql("   USR_SID,");
        sql.addSql("   SCC_FR_DATE,");
        sql.addSql("   SCC_TO_DATE,");
        sql.addSql("   SCC_DSP_GROUP,");
        sql.addSql("   SCC_SORT_KEY1,");
        sql.addSql("   SCC_SORT_ORDER1,");
        sql.addSql("   SCC_SORT_KEY2,");
        sql.addSql("   SCC_SORT_ORDER2,");
        sql.addSql("   SCC_INI_FR_DATE,");
        sql.addSql("   SCC_INI_TO_DATE,");
        sql.addSql("   SCC_INI_PUBLIC,");
        sql.addSql("   SCC_INI_FCOLOR,");
        sql.addSql("   SCC_INI_EDIT,");
        sql.addSql("   SCC_DSP_LIST,");
        sql.addSql("   SCC_DSP_MYGROUP,");
        sql.addSql("   SCC_SMAIL,");
        sql.addSql("   SCC_SMAIL_GROUP,");
        sql.addSql("   SCC_RELOAD,");
        sql.addSql("   SCC_INI_WEEK,");
        sql.addSql("   SCC_SORT_EDIT,");
        sql.addSql("   SCC_AUID,");
        sql.addSql("   SCC_ADATE,");
        sql.addSql("   SCC_EUID,");
        sql.addSql("   SCC_EDATE");
        sql.addSql(" )");
        sql.addSql(" select");
        sql.addSql("   USR_SID,");
        sql.addSql("   SCC_FR_DATE,");
        sql.addSql("   SCC_TO_DATE,");
        sql.addSql("   SCC_DSP_GROUP,");
        sql.addSql("   SCC_SORT_KEY1,");
        sql.addSql("   SCC_SORT_ORDER1,");
        sql.addSql("   SCC_SORT_KEY2,");
        sql.addSql("   SCC_SORT_ORDER2,");
        sql.addSql("   SCC_INI_FR_DATE,");
        sql.addSql("   SCC_INI_TO_DATE,");
        sql.addSql("   SCC_INI_PUBLIC,");
        sql.addSql("   SCC_INI_FCOLOR,");
        sql.addSql("   SCC_INI_EDIT,");
        sql.addSql("   SCC_DSP_LIST,");
        sql.addSql("   SCC_DSP_MYGROUP,");
        sql.addSql("   SCC_SMAIL,");
        sql.addSql("   SCC_SMAIL_GROUP,");
        sql.addSql("   SCC_RELOAD,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   SCC_AUID,");
        sql.addSql("   SCC_ADATE,");
        sql.addSql("   SCC_EUID,");
        sql.addSql("   SCC_EDATE");
        sql.addSql(" from SCH_PRI_CONF_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop table SCH_PRI_CONF_CONV300");
        sqlList.add(sql);

        //スケジュールスケジュール管理者設定テーブル変更
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF rename to SCH_ADM_CONF_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_ADM_CONF");
        sql.addSql(" (");
        sql.addSql("   SAD_CRANGE      integer    not null,");
        sql.addSql("   SAD_ATDEL_FLG   integer    not null,");
        sql.addSql("   SAD_ATDEL_Y     integer            ,");
        sql.addSql("   SAD_ATDEL_M     integer            ,");
        sql.addSql("   SAD_HOUR_DIV    integer            ,");
        sql.addSql("   SAD_SORT_KBN    integer    not null,");
        sql.addSql("   SAD_SORT_KEY1   integer    not null,");
        sql.addSql("   SAD_SORT_ORDER1 integer    not null,");
        sql.addSql("   SAD_SORT_KEY2   integer    not null,");
        sql.addSql("   SAD_SORT_ORDER2 integer    not null,");
        sql.addSql("   SAD_AUID        integer    not null,");
        sql.addSql("   SAD_ADATE       timestamp  not null,");
        sql.addSql("   SAD_EUID        integer    not null,");
        sql.addSql("   SAD_EDATE       timestamp  not null");
        sql.addSql("   ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert ");
        sql.addSql(" into ");
        sql.addSql(" SCH_ADM_CONF(");
        sql.addSql("   SAD_CRANGE,");
        sql.addSql("   SAD_ATDEL_FLG,");
        sql.addSql("   SAD_ATDEL_Y,");
        sql.addSql("   SAD_ATDEL_M,");
        sql.addSql("   SAD_HOUR_DIV,");
        sql.addSql("   SAD_SORT_KBN,");
        sql.addSql("   SAD_SORT_KEY1,");
        sql.addSql("   SAD_SORT_ORDER1,");
        sql.addSql("   SAD_SORT_KEY2,");
        sql.addSql("   SAD_SORT_ORDER2,");
        sql.addSql("   SAD_AUID,");
        sql.addSql("   SAD_ADATE,");
        sql.addSql("   SAD_EUID,");
        sql.addSql("   SAD_EDATE");
        sql.addSql(" )");
        sql.addSql(" select ");
        sql.addSql("   SAD_CRANGE,");
        sql.addSql("   SAD_ATDEL_FLG,");
        sql.addSql("   SAD_ATDEL_Y,");
        sql.addSql("   SAD_ATDEL_M,");
        sql.addSql("   SAD_HOUR_DIV,");
        sql.addSql("   1,");
        sql.addSql("   3,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   SAD_AUID,");
        sql.addSql("   SAD_ADATE,");
        sql.addSql("   SAD_EUID,");
        sql.addSql("   SAD_EDATE");
        sql.addSql(" from SCH_ADM_CONF_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop table SCH_ADM_CONF_CONV300");
        sqlList.add(sql);

        //プロジェクト-アドレス帳情報Mappingテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table PRJ_ADDRESS");
        sql.addSql(" (");
        sql.addSql("   PRJ_SID         integer         not null,");
        sql.addSql("   ADR_SID         integer         not null,");
        sql.addSql("   PRA_AUID        integer         not null,");
        sql.addSql("   PRA_ADATE       timestamp       not null,");
        sql.addSql("   PRA_EUID        integer         not null,");
        sql.addSql("   PRA_EDATE       timestamp       not null,");
        sql.addSql("   primary key(PRJ_SID, ADR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //プロジェクト-会社情報Mappingテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table PRJ_COMPANY");
        sql.addSql(" (");
        sql.addSql("   PRJ_SID         integer         not null,");
        sql.addSql("   ACO_SID         integer         not null,");
        sql.addSql("   ABA_SID         integer                 ,");
        sql.addSql("   PRC_AUID        integer         not null,");
        sql.addSql("   PRC_ADATE       timestamp       not null,");
        sql.addSql("   PRC_EUID        integer         not null,");
        sql.addSql("   PRC_EDATE       timestamp       not null,");
        sql.addSql("   primary key(PRJ_SID, ACO_SID, ABA_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID         integer         not null,");
        sql.addSql("   CTM_ID          varchar(20)     not null,");
        sql.addSql("   CTM_NAME        varchar(50)     not null,");
        sql.addSql("   CTM_PATH        varchar(100)    not null,");
        sql.addSql("   CTM_PATH_IMG    varchar(150)    not null,");
        sql.addSql("   CTM_AUID        integer         not null,");
        sql.addSql("   CTM_ADATE       timestamp       not null,");
        sql.addSql("   CTM_EUID        integer         not null,");
        sql.addSql("   CTM_EDATE       timestamp       not null,");
        sql.addSql("   primary key (CTM_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ユーザーテーマテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USR_THEME");
        sql.addSql(" (");
        sql.addSql("   USR_SID         integer         not null,");
        sql.addSql("   CTM_SID         integer         not null,");
        sql.addSql("   UTM_AUID        integer         not null,");
        sql.addSql("   UTM_ADATE       timestamp       not null,");
        sql.addSql("   UTM_EUID        integer         not null,");
        sql.addSql("   UTM_EDATE       timestamp       not null,");
        sql.addSql("   primary key (USR_SID,CTM_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル初期値（テーマ１）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID,");
        sql.addSql("   CTM_ID,");
        sql.addSql("   CTM_NAME,");
        sql.addSql("   CTM_PATH,");
        sql.addSql("   CTM_PATH_IMG,");
        sql.addSql("   CTM_AUID,");
        sql.addSql("   CTM_ADATE,");
        sql.addSql("   CTM_EUID,");
        sql.addSql("   CTM_EDATE,");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   1,");
        sql.addSql("   'theme1',");
        sql.addSql("   'デフォルト',");
        sql.addSql("   'common/css/theme1',");
        sql.addSql("   'common/images/image_theme1.gif',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル初期値（テーマ2）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID,");
        sql.addSql("   CTM_ID,");
        sql.addSql("   CTM_NAME,");
        sql.addSql("   CTM_PATH,");
        sql.addSql("   CTM_PATH_IMG,");
        sql.addSql("   CTM_AUID,");
        sql.addSql("   CTM_ADATE,");
        sql.addSql("   CTM_EUID,");
        sql.addSql("   CTM_EDATE,");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   2,");
        sql.addSql("   'theme2',");
        sql.addSql("   'グレー',");
        sql.addSql("   'common/css/theme2',");
        sql.addSql("   'common/images/image_theme2.gif',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル初期値（テーマ3）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID,");
        sql.addSql("   CTM_ID,");
        sql.addSql("   CTM_NAME,");
        sql.addSql("   CTM_PATH,");
        sql.addSql("   CTM_PATH_IMG,");
        sql.addSql("   CTM_AUID,");
        sql.addSql("   CTM_ADATE,");
        sql.addSql("   CTM_EUID,");
        sql.addSql("   CTM_EDATE,");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   3,");
        sql.addSql("   'theme3',");
        sql.addSql("   '緑',");
        sql.addSql("   'common/css/theme3',");
        sql.addSql("   'common/images/image_theme3.gif',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル初期値（テーマ4）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID,");
        sql.addSql("   CTM_ID,");
        sql.addSql("   CTM_NAME,");
        sql.addSql("   CTM_PATH,");
        sql.addSql("   CTM_PATH_IMG,");
        sql.addSql("   CTM_AUID,");
        sql.addSql("   CTM_ADATE,");
        sql.addSql("   CTM_EUID,");
        sql.addSql("   CTM_EDATE,");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   4,");
        sql.addSql("   'theme4',");
        sql.addSql("   '赤',");
        sql.addSql("   'common/css/theme4',");
        sql.addSql("   'common/images/image_theme4.gif',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル初期値（テーマ5）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID,");
        sql.addSql("   CTM_ID,");
        sql.addSql("   CTM_NAME,");
        sql.addSql("   CTM_PATH,");
        sql.addSql("   CTM_PATH_IMG,");
        sql.addSql("   CTM_AUID,");
        sql.addSql("   CTM_ADATE,");
        sql.addSql("   CTM_EUID,");
        sql.addSql("   CTM_EDATE,");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   5,");
        sql.addSql("   'theme5',");
        sql.addSql("   'ピンク',");
        sql.addSql("   'common/css/theme5',");
        sql.addSql("   'common/images/image_theme5.gif',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //テーマテーブル初期値（テーマ6）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME");
        sql.addSql(" (");
        sql.addSql("   CTM_SID,");
        sql.addSql("   CTM_ID,");
        sql.addSql("   CTM_NAME,");
        sql.addSql("   CTM_PATH,");
        sql.addSql("   CTM_PATH_IMG,");
        sql.addSql("   CTM_AUID,");
        sql.addSql("   CTM_ADATE,");
        sql.addSql("   CTM_EUID,");
        sql.addSql("   CTM_EDATE,");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   6,");
        sql.addSql("   'theme6',");
        sql.addSql("   '黄色',");
        sql.addSql("   'common/css/theme6',");
        sql.addSql("   'common/images/image_theme6.gif',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //施設予約情報テーブル変更
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_YRK rename to RSV_SIS_YRK_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_SIS_YRK");
        sql.addSql(" (");
        sql.addSql("   RSY_SID         integer not null,");
        sql.addSql("   RSD_SID         integer not null,");
        sql.addSql("   RSY_YGRP_SID    integer,");
        sql.addSql("   RSY_MOK         varchar(50) not null,");
        sql.addSql("   RSY_FR_DATE     timestamp not null,");
        sql.addSql("   RSY_TO_DATE     timestamp not null,");
        sql.addSql("   RSY_BIKO        varchar(1000),");
        sql.addSql("   RSY_AUID        integer not null,");
        sql.addSql("   RSY_ADATE       timestamp not null,");
        sql.addSql("   RSY_EUID        integer not null,");
        sql.addSql("   RSY_EDATE       timestamp not null,");
        sql.addSql("   SCD_RSSID       integer,");
        sql.addSql("   RSY_EDIT        integer not null,");
        sql.addSql("   RSR_RSID        integer,");
        sql.addSql("   primary key (RSY_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into RSV_SIS_YRK");
        sql.addSql(" (");
        sql.addSql("   RSY_SID,");
        sql.addSql("   RSD_SID,");
        sql.addSql("   RSY_YGRP_SID,");
        sql.addSql("   RSY_MOK,");
        sql.addSql("   RSY_FR_DATE,");
        sql.addSql("   RSY_TO_DATE,");
        sql.addSql("   RSY_BIKO,");
        sql.addSql("   RSY_AUID,");
        sql.addSql("   RSY_ADATE,");
        sql.addSql("   RSY_EUID,");
        sql.addSql("   RSY_EDATE,");
        sql.addSql("   SCD_RSSID,");
        sql.addSql("   RSY_EDIT,");
        sql.addSql("   RSR_RSID");
        sql.addSql(" )");
        sql.addSql(" select");
        sql.addSql("   RSY_SID,");
        sql.addSql("   RSD_SID,");
        sql.addSql("   RSY_YGRP_SID,");
        sql.addSql("   RSY_MOK,");
        sql.addSql("   RSY_FR_DATE,");
        sql.addSql("   RSY_TO_DATE,");
        sql.addSql("   RSY_BIKO,");
        sql.addSql("   RSY_AUID,");
        sql.addSql("   RSY_ADATE,");
        sql.addSql("   RSY_EUID,");
        sql.addSql("   RSY_EDATE,");
        sql.addSql("   SCD_RSSID,");
        sql.addSql("   RSY_EDIT,");
        sql.addSql("   RSR_RSID");
        sql.addSql(" from RSV_SIS_YRK_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop table RSV_SIS_YRK_CONV300");
        sqlList.add(sql);

        //施設予約拡張情報テーブル変更
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_RYRK rename to RSV_SIS_RYRK_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_SIS_RYRK");
        sql.addSql(" (");
        sql.addSql("   RSR_RSID        integer not null,");
        sql.addSql("   RSR_KBN         integer not null,");
        sql.addSql("   RSR_DWEEK1      integer,");
        sql.addSql("   RSR_DWEEK2      integer,");
        sql.addSql("   RSR_DWEEK3      integer,");
        sql.addSql("   RSR_DWEEK4      integer,");
        sql.addSql("   RSR_DWEEK5      integer,");
        sql.addSql("   RSR_DWEEK6      integer,");
        sql.addSql("   RSR_DWEEK7      integer,");
        sql.addSql("   RSR_DAY         integer,");
        sql.addSql("   RSR_WEEK        integer,");
        sql.addSql("   RSR_TIME_FR     timestamp not null,");
        sql.addSql("   RSR_TIME_TO     timestamp not null,");
        sql.addSql("   RSR_TRAN_KBN    integer not null,");
        sql.addSql("   RSR_DATE_FR     timestamp not null,");
        sql.addSql("   RSR_DATE_TO     timestamp not null,");
        sql.addSql("   RSR_MOK         varchar(50) not null,");
        sql.addSql("   RSR_BIKO        varchar(1000),");
        sql.addSql("   RSR_EDIT        integer,");
        sql.addSql("   RSR_AUID        integer not null,");
        sql.addSql("   RSR_ADATE       timestamp not null,");
        sql.addSql("   RSR_EUID        integer not null,");
        sql.addSql("   RSR_EDATE       timestamp not null,");
        sql.addSql("   primary key (RSR_RSID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into RSV_SIS_RYRK");
        sql.addSql(" (");
        sql.addSql("   RSR_RSID,");
        sql.addSql("   RSR_KBN,");
        sql.addSql("   RSR_DWEEK1,");
        sql.addSql("   RSR_DWEEK2,");
        sql.addSql("   RSR_DWEEK3,");
        sql.addSql("   RSR_DWEEK4,");
        sql.addSql("   RSR_DWEEK5,");
        sql.addSql("   RSR_DWEEK6,");
        sql.addSql("   RSR_DWEEK7,");
        sql.addSql("   RSR_DAY,");
        sql.addSql("   RSR_WEEK,");
        sql.addSql("   RSR_TIME_FR,");
        sql.addSql("   RSR_TIME_TO,");
        sql.addSql("   RSR_TRAN_KBN,");
        sql.addSql("   RSR_DATE_FR,");
        sql.addSql("   RSR_DATE_TO,");
        sql.addSql("   RSR_MOK,");
        sql.addSql("   RSR_BIKO,");
        sql.addSql("   RSR_EDIT,");
        sql.addSql("   RSR_AUID,");
        sql.addSql("   RSR_ADATE,");
        sql.addSql("   RSR_EUID,");
        sql.addSql("   RSR_EDATE");
        sql.addSql(" )");
        sql.addSql(" select");
        sql.addSql("   RSR_RSID,");
        sql.addSql("   RSR_KBN,");
        sql.addSql("   RSR_DWEEK1,");
        sql.addSql("   RSR_DWEEK2,");
        sql.addSql("   RSR_DWEEK3,");
        sql.addSql("   RSR_DWEEK4,");
        sql.addSql("   RSR_DWEEK5,");
        sql.addSql("   RSR_DWEEK6,");
        sql.addSql("   RSR_DWEEK7,");
        sql.addSql("   RSR_DAY,");
        sql.addSql("   RSR_WEEK,");
        sql.addSql("   RSR_TIME_FR,");
        sql.addSql("   RSR_TIME_TO,");
        sql.addSql("   RSR_TRAN_KBN,");
        sql.addSql("   RSR_DATE_FR,");
        sql.addSql("   RSR_DATE_TO,");
        sql.addSql("   RSR_MOK,");
        sql.addSql("   RSR_BIKO,");
        sql.addSql("   RSR_EDIT,");
        sql.addSql("   RSR_AUID,");
        sql.addSql("   RSR_ADATE,");
        sql.addSql("   RSR_EUID,");
        sql.addSql("   RSR_EDATE");
        sql.addSql(" from RSV_SIS_RYRK_CONV300");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop table RSV_SIS_RYRK_CONV300");
        sqlList.add(sql);

        //回覧板
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_VIEW add CVW_CONF_DATE timestamp;");
        sqlList.add(sql);

        //オペレーションログ
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LOG_CONF");
        sql.addSql(" (");
        sql.addSql("   LGC_PLUGIN      varchar(20)     not null,");
        sql.addSql("   LGC_LEVEL_ERROR integer         not null,");
        sql.addSql("   LGC_LEVEL_WARN  integer         not null,");
        sql.addSql("   LGC_LEVEL_INFO  integer         not null,");
        sql.addSql("   LGC_LEVEL_TRACE integer         not null,");
        sql.addSql("   LGC_AUID        integer        ,");
        sql.addSql("   LGC_ADATE       timestamp      ,");
        sql.addSql("   LGC_EUID        integer        ,");
        sql.addSql("   LGC_EDATE       timestamp      ,");
        sql.addSql("   primary key (LGC_PLUGIN)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LOG");
        sql.addSql(" (");
        sql.addSql("   LOG_DATE        timestamp       not null,");
        sql.addSql("   USR_SID         integer         not null,");
        sql.addSql("   LOG_LEVEL       varchar(5)      not null,");
        sql.addSql("   LOG_PLUGIN      varchar(20)     ,");
        sql.addSql("   LOG_PLUGIN_NAME varchar(50)     ,");
        sql.addSql("   LOG_PG_ID       varchar(100)    ,");
        sql.addSql("   LOG_PG_NAME     varchar(300)    ,");
        sql.addSql("   LOG_OP_CODE     varchar(10)     ,");
        sql.addSql("   LOG_OP_VALUE    varchar(3000)   ,");
        sql.addSql("   LOG_IP          varchar(40)     ,");
        sql.addSql("   VER_VERSION     varchar(60)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LOG_DEL_CONF");
        sql.addSql(" (");
        sql.addSql("   LDC_ADL_KBN      integer    not null,");
        sql.addSql("   LDC_ADL_YEAR     integer,");
        sql.addSql("   LDC_ADL_MONTH    integer,");
        sql.addSql("   LDC_AUID         integer    not null,");
        sql.addSql("   LDC_ADATE        timestamp  not null,");
        sql.addSql("   LDC_EUID         integer    not null,");
        sql.addSql("   LDC_EDATE        timestamp  not null");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        //ショートメールインデックス
        sql = new SqlBuffer();
        sql.addSql("create index SML_SMEIS_INDEX1 on SML_SMEIS(USR_SID,SMS_JKBN);");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <p>回覧板確認時間の更新する。
     * @throws SQLException SQL実行例外
     */
    private void __updatecirConfDate() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("CVW_CONF") == 1) {
                    __updateView(
                            rs.getInt("USR_SID"),
                            rs.getInt("CIF_SID"),
                            UDate.getInstanceTimestamp(rs.getTimestamp("CVW_EDATE")));
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 受信回覧板確認日時の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param cifSid 回覧板SID
     * @param cvwConfDate 確認日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    private int __updateView(int usrSid, int cifSid, UDate cvwConfDate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CVW_CONF_DATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIF_SID = ?");

            sql.addDateValue(cvwConfDate);
            //where
            sql.addIntValue(usrSid);
            sql.addIntValue(cifSid);

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