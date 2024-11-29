package jp.groupsession.v2.convert.convert520.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v5.2.0へコンバートする際に使用する
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

        __tcdTimezoneEdit(saiban);


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

        SqlBuffer sql = null;
        //メモプラグイン
        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_ADM_CONF (");
        sql.addSql("   MAC_ATDEL_KBN integer not null,");
        sql.addSql("   MAC_ATDEL_Y integer,");
        sql.addSql("   MAC_ATDEL_M integer,");
        sql.addSql("   MAC_AUID integer not null,");
        sql.addSql("   MAC_ADATE timestamp not null,");
        sql.addSql("   MAC_EUID integer not null,");
        sql.addSql("   MAC_EDATE timestamp not null");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_BELONG_LABEL (");
        sql.addSql("   MEM_SID bigint not null,");
        sql.addSql("   MML_SID integer not null,");
        sql.addSql("   primary key (MEM_SID,MML_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_BIN (");
        sql.addSql("   MEM_SID bigint not null,");
        sql.addSql("   BIN_SID bigint not null,");
        sql.addSql("   primary key (MEM_SID,BIN_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_DATA (");
        sql.addSql("   MEM_SID bigint not null,");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   MMD_CONTENT varchar(1000) not null,");
        sql.addSql("   MMD_DEL_CONF integer not null,");
        sql.addSql("   MMD_AUID integer not null,");
        sql.addSql("   MMD_ADATE timestamp not null,");
        sql.addSql("   MMD_EUID integer not null,");
        sql.addSql("   MMD_EDATE timestamp not null,");
        sql.addSql("   primary key (MEM_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_DATA_PERIOD (");
        sql.addSql("   MEM_SID bigint not null,");
        sql.addSql("   MDP_DEL_DATE date not null,");
        sql.addSql("   primary key (MEM_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_LABEL (");
        sql.addSql("   MML_SID integer not null,");
        sql.addSql("   MML_NAME varchar(20) not null,");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   MML_SORT integer not null,");
        sql.addSql("   MML_AUID integer not null,");
        sql.addSql("   MML_ADATE timestamp not null,");
        sql.addSql("   MML_EUID integer not null,");
        sql.addSql("   MML_EDATE timestamp not null,");
        sql.addSql("   primary key (MML_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_PRI_CONF (");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   MPC_DSP_MODE integer not null,");
        sql.addSql("   MPC_AUID integer not null,");
        sql.addSql("   MPC_ADATE timestamp not null,");
        sql.addSql("   MPC_EUID integer not null,");
        sql.addSql("   MPC_EDATE timestamp not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //共通
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_OAUTH");
        sql.addSql(" (");
        sql.addSql("   COU_SID                integer       not null,");
        sql.addSql("   COU_PROVIDER           integer       not null,");
        sql.addSql("   COU_AUTH_ID            varchar(1000) not null,");
        sql.addSql("   COU_AUTH_SECRET        varchar(1000) not null,");
        sql.addSql("   COU_BIKO               varchar(1000),");
        sql.addSql("   COU_AUID               integer       not null,");
        sql.addSql("   COU_ADATE              timestamp     not null,");
        sql.addSql("   COU_EUID               integer       not null,");
        sql.addSql("   COU_EDATE              timestamp     not null,");
        sql.addSql("   primary key(COU_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_OAUTH_TOKEN");
        sql.addSql(" (");
        sql.addSql("   COT_SID                integer       not null,");
        sql.addSql("   COU_SID                integer       not null,");
        sql.addSql("   COT_ACCOUNTID          varchar(300),");
        sql.addSql("   COT_AUTH_RTOKEN        varchar(3000),");
        sql.addSql("   COT_AUTH_ATOKEN        varchar(3000),");
        sql.addSql("   COT_AUTH_ATOKEN_DATE   timestamp,");
        sql.addSql("   primary key(COT_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_OTP_CONF add column COC_SMTP_AUTH_TYPE"
                    + " integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table CMN_OTP_CONF add column COT_SID integer;");
        sqlList.add(sql);

        //WEBメール
        sql = new SqlBuffer();
        sql.addSql(" create table WML_DELMAIL_LIST");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   WMD_MAILNUM     bigint       not null,");
        sql.addSql("   WDL_ADATE       timestamp    not null,");
        sql.addSql("   primary key (WAC_SID, WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_DELMAIL_TEMP");
        sql.addSql(" (");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   WMD_MAILNUM     bigint       not null,");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   WDT_ADATE       timestamp    not null,");
        sql.addSql("   primary key (USR_SID, WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_UIDL_TEMP ");
        sql.addSql(" ( ");
        sql.addSql("   WAC_SID       integer       not null, ");
        sql.addSql("   WUD_UID       varchar(1000) not null, ");
        sql.addSql("   primary key(WAC_SID, WUD_UID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);
        sql = new SqlBuffer();

        sql.addSql(" create table WML_MANAGE_DELUID (");
        sql.addSql("   WAC_SID      integer       not null,");
        sql.addSql("   WDU_DELDATE  timestamp     not null");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add column WAC_AUTH_TYPE integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT add COT_SID integer;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_SEND_HOST drop not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_SEND_PORT drop not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_RECEIVE_HOST drop not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_RECEIVE_PORT drop not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_RECEIVE_USER drop not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_RECEIVE_PASS drop not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add column WAC_AUTO_SAVE_MINUTE"
                    + " integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter column WAC_SEND_USER type varchar(256);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter column WAC_RECEIVE_USER type varchar(256);");
        sqlList.add(sql);

        //タイムカード
        sql = new SqlBuffer();
        sql.addSql(" create table TCD_TIMEZONE_INFO(");
        sql.addSql("   TTI_SID integer not null,");
        sql.addSql("   TTI_NAME varchar(100) not null,");
        sql.addSql("   TTI_RYAKU varchar(5) not null,");
        sql.addSql("   TTI_SORT integer not null,");
        sql.addSql("   TTI_USE integer not null,");
        sql.addSql("   TTI_HOLIDAY integer not null,");
        sql.addSql("   TTI_AUID integer not null,");
        sql.addSql("   TTI_ADATE timestamp not null,");
        sql.addSql("   TTI_EUID integer not null,");
        sql.addSql("   TTI_EDATE timestamp not null,");
        sql.addSql("   primary key (TTI_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_TIMEZONE_INFO (");
        sql.addSql("   TTI_SID,");
        sql.addSql("   TTI_NAME,");
        sql.addSql("   TTI_RYAKU,");
        sql.addSql("   TTI_SORT,");
        sql.addSql("   TTI_USE,");
        sql.addSql("   TTI_HOLIDAY,");
        sql.addSql("   TTI_AUID,");
        sql.addSql("   TTI_ADATE,");
        sql.addSql("   TTI_EUID,");
        sql.addSql("   TTI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  0,");
        sql.addSql("  '通常時間帯',");
        sql.addSql("  '通常',");
        sql.addSql("  1,");
        sql.addSql("  1,");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table TCD_TIMEZONE_PRI(");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   TTI_SID integer not null,");
        sql.addSql("   TTP_DEFAULT integer not null,");
        sql.addSql("   TTP_AUID integer not null,");
        sql.addSql("   TTP_ADATE timestamp not null,");
        sql.addSql("   primary key (USR_SID, TTI_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_TCDATA add column TTI_SID integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_TIMEZONE add column TTI_SID integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add column TAC_LOCK_TIMEZONE  integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add column TAC_DEF_TIMEZONE  integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE_KEIRO add column RTK_KEIRO_COMMENT  varchar(200);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add column TAC_WORKREPORT_KBN integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add column TAC_WORKREPORT_SID bigint default 0;");
        sqlList.add(sql);

        //ショートメール
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add column SMA_AUTH_TYPE integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add column COT_SID integer not null default 0;");
        sqlList.add(sql);

        //安否確認
        sql = new SqlBuffer();
        sql.addSql(" alter table ANP_CMN_CONF add column APC_AUTH_TYPE integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ANP_CMN_CONF add column COT_SID integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ANP_CMN_CONF alter column APC_SEND_HOST drop not null;");
        sqlList.add(sql);

        //No.1056
        sql = new SqlBuffer();
        sql.addSql(" create table TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID integer not null,");
        sql.addSql("   THI_NAME varchar(10) not null,");
        sql.addSql("   THI_HOLTOTAL_KBN integer not null,");
        sql.addSql("   THI_LIMIT integer not null,");
        sql.addSql("   THI_ORDER integer not null,");
        sql.addSql("   THI_CONTENT_KBN integer not null,");
        sql.addSql("   THI_AUID integer not null,");
        sql.addSql("   THI_ADATE timestamp not null,");
        sql.addSql("   THI_EUID integer not null,");
        sql.addSql("   THI_EDATE timestamp not null,");
        sql.addSql("   primary key (THI_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID,");
        sql.addSql("   THI_NAME,");
        sql.addSql("   THI_HOLTOTAL_KBN,");
        sql.addSql("   THI_LIMIT,");
        sql.addSql("   THI_ORDER,");
        sql.addSql("   THI_CONTENT_KBN,");
        sql.addSql("   THI_AUID,");
        sql.addSql("   THI_ADATE,");
        sql.addSql("   THI_EUID,");
        sql.addSql("   THI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  1,");
        sql.addSql("  '欠勤',");
        sql.addSql("  2,");
        sql.addSql("  0,");
        sql.addSql("  1,");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID,");
        sql.addSql("   THI_NAME,");
        sql.addSql("   THI_HOLTOTAL_KBN,");
        sql.addSql("   THI_LIMIT,");
        sql.addSql("   THI_ORDER,");
        sql.addSql("   THI_CONTENT_KBN,");
        sql.addSql("   THI_AUID,");
        sql.addSql("   THI_ADATE,");
        sql.addSql("   THI_EUID,");
        sql.addSql("   THI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  2,");
        sql.addSql("  '慶弔',");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  2,");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID,");
        sql.addSql("   THI_NAME,");
        sql.addSql("   THI_HOLTOTAL_KBN,");
        sql.addSql("   THI_LIMIT,");
        sql.addSql("   THI_ORDER,");
        sql.addSql("   THI_CONTENT_KBN,");
        sql.addSql("   THI_AUID,");
        sql.addSql("   THI_ADATE,");
        sql.addSql("   THI_EUID,");
        sql.addSql("   THI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  3,");
        sql.addSql("  '有休',");
        sql.addSql("  1,");
        sql.addSql("  0,");
        sql.addSql("  3,");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID,");
        sql.addSql("   THI_NAME,");
        sql.addSql("   THI_HOLTOTAL_KBN,");
        sql.addSql("   THI_LIMIT,");
        sql.addSql("   THI_ORDER,");
        sql.addSql("   THI_CONTENT_KBN,");
        sql.addSql("   THI_AUID,");
        sql.addSql("   THI_ADATE,");
        sql.addSql("   THI_EUID,");
        sql.addSql("   THI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  4,");
        sql.addSql("  '代休',");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  4,");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID,");
        sql.addSql("   THI_NAME,");
        sql.addSql("   THI_HOLTOTAL_KBN,");
        sql.addSql("   THI_LIMIT,");
        sql.addSql("   THI_ORDER,");
        sql.addSql("   THI_CONTENT_KBN,");
        sql.addSql("   THI_AUID,");
        sql.addSql("   THI_ADATE,");
        sql.addSql("   THI_EUID,");
        sql.addSql("   THI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  6,");
        sql.addSql("  '振休',");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  5,");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into TCD_HOLIDAY_INF (");
        sql.addSql("   THI_SID,");
        sql.addSql("   THI_NAME,");
        sql.addSql("   THI_HOLTOTAL_KBN,");
        sql.addSql("   THI_LIMIT,");
        sql.addSql("   THI_ORDER,");
        sql.addSql("   THI_CONTENT_KBN,");
        sql.addSql("   THI_AUID,");
        sql.addSql("   THI_ADATE,");
        sql.addSql("   THI_EUID,");
        sql.addSql("   THI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  5,");
        sql.addSql("  'その他',");
        sql.addSql("  0,");
        sql.addSql("  0,");
        sql.addSql("  6,");
        sql.addSql("  1,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_SAIBAN (");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  'timecard',");
        sql.addSql("  'tcdHolInf',");
        sql.addSql("  6,");
        sql.addSql("  'tcdHolInf',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {

            sql.addSql("alter table TCD_TCDATA rename TCD_HOLKBN to THI_SID;");

        } else {
            sql.addSql("alter table TCD_TCDATA alter column TCD_HOLKBN rename to THI_SID;");
        }
        sqlList.add(sql);

        //No.1057
        sql = new SqlBuffer();
        sql.addSql(" create table TCD_YUKYUDATA (");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   TCY_NENDO integer not null,");
        sql.addSql("   TCY_YUKYU decimal(6, 3) not null,");
        sql.addSql("   TCY_AUID integer not null,");
        sql.addSql("   TCY_ADATE timestamp not null,");
        sql.addSql("   TCY_EUID integer not null,");
        sql.addSql("   TCY_EDATE timestamp not null,");
        sql.addSql("   primary key (USR_SID, TCY_NENDO)");
        sql.addSql(" )");
        sqlList.add(sql);

        //No.1058
        sql = new SqlBuffer();
        sql.addSql(" create table TCD_YUKYU_ALERT (");
        sql.addSql("   TYA_NO integer not null,");
        sql.addSql("   TYA_MONTH integer not null,");
        sql.addSql("   TYA_BASEDAYS integer not null,");
        sql.addSql("   TYA_MESSAGE varchar(50) not null");
        sql.addSql(" )");
        sqlList.add(sql);

        //No.1059
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PUSH_ERROR_INFO (");
        sql.addSql("   PEI_ECODE varchar(75) not null,");
        sql.addSql("   PEI_MESSAGE varchar(150) not null,");
        sql.addSql("   PEI_AUID integer not null,");
        sql.addSql("   PEI_ADATE timestamp not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PUSH_SERVER_TOKEN (");
        sql.addSql("   PST_TOKEN varchar(75) not null,");
        sql.addSql("   PST_LIMIT timestamp not null,");
        sql.addSql("   primary key (PST_TOKEN)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PUSH_SERVER_OTP (");
        sql.addSql("   PSO_KEY varchar(100) not null,");
        sql.addSql("   PSO_USECNT integer not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PUSH_TOKEN (");
        sql.addSql("   CPT_PUSH_TOKEN varchar(256) not null,");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   CMU_UID varchar(50) not null,");
        sql.addSql("   CPT_PLUGIN varchar(256) not null,");
        sql.addSql("   primary key (CPT_PUSH_TOKEN)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SML_PUSH_UCONF (");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   SAC_SID integer not null,");
        sql.addSql("   SPU_PUSHUSE integer not null,");
        sql.addSql("   primary key (USR_SID, SAC_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add column SCD_TARGET_APP integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add column SCD_TARGET_PC integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add column SCD_TARGET_GRP integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add column SCD_REMINDER integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add column SCC_TARGET_APP integer not null default 1;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add column SCC_TARGET_PC integer not null default 1;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add column SCC_REMINDER integer not null default 4;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_PUSH_LIST (");
        sql.addSql("   SCD_SID integer not null,");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   SPL_TARGET_APP integer not null,");
        sql.addSql("   SPL_TARGET_PC integer not null,");
        sql.addSql("   SPL_REMINDER timestamp not null,");
        sql.addSql("   primary key (SCD_SID, USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("   create index SCH_DATA_INDEX3 on SCH_DATA(SCD_FR_DATE); ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("   create index SCH_PUSH_LIST_INDEX1 on SCH_PUSH_LIST(SPL_REMINDER); ");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能]タイムゾーン深夜の内容を残業にコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __tcdTimezoneEdit(MlCountMtController saiban)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TTZ_SID,");
            sql.addSql("   TTZ_KBN,");
            sql.addSql("   TTZ_FRTIME,");
            sql.addSql("   TTZ_TOTIME,");
            sql.addSql("   TTI_SID");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE");
            sql.addSql(" where");
            sql.addSql("   TTZ_KBN = ?");
            sql.addIntValue(GSConstTimecard.TIMEZONE_KBN_SINYA);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int ttzSid = (int) saiban.getSaibanNumber(
                        SaibanModel.SBNSID_TIMECARD,
                        SaibanModel.SBNSID_SUB_TCD,
                        0);

                sql = new SqlBuffer();
                sql.addSql(" insert into TCD_TIMEZONE (");
                sql.addSql("   TTZ_SID,");
                sql.addSql("   TTZ_KBN,");
                sql.addSql("   TTZ_FRTIME,");
                sql.addSql("   TTZ_TOTIME,");
                sql.addSql("   TTI_SID");
                sql.addSql(" ) values (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" );");
                sql.addIntValue(ttzSid);
                sql.addIntValue(GSConstTimecard.TIMEZONE_KBN_ZANGYO);
                sql.addDateValue(UDate.getInstance(rs.getTime("TTZ_FRTIME").getTime()));
                sql.addDateValue(UDate.getInstance(rs.getTime("TTZ_TOTIME").getTime()));
                sql.addIntValue(rs.getInt("TTI_SID"));
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
