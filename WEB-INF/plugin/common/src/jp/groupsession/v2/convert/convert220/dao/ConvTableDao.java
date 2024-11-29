package jp.groupsession.v2.convert.convert220.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * <br>[解  説] v2.2.0へコンバートする際に使用する
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

        //ALTER CMN_CONT_M
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_ID_SAVE integer default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_PASS_SAVE integer default 0");
        sqlList.add(sql);

        //ALTER CMN_SAIBAN
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_SAIBAN alter column SBN_SID_SUB varchar(20) not null");
        sqlList.add(sql);

        //ALTER CMN_USRM_INF
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF add USI_MBL_USE integer default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF add USI_NUM_CONT integer default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF add USI_NUM_AUTADD integer default 0");
        sqlList.add(sql);

        //ALTER PRJ_USER_CONF
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_TODO_DATE integer default 2");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_TODO_PROJECT integer default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_TODO_STATUS integer default -4");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_PRJ_PROJECT integer default 1");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_MAIN_DATE integer default 2");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_MAIN_STATUS integer default -4");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_MAIN_MENBER integer default 0");
        sqlList.add(sql);

        //ALTER RNG_TEMPLATE
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE add RTP_SORT integer not null default 0");
        sqlList.add(sql);

        //CREATE CMN_LOGIN_HISTORY
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LOGIN_HISTORY");
        sql.addSql(" (");
        sql.addSql("   USR_SID       integer,");
        sql.addSql("   CLH_TERMINAL  integer,");
        sql.addSql("   CLH_IP        varchar(50),");
        sql.addSql("   CLH_CAR       integer,");
        sql.addSql("   CLH_UID       varchar(50),");
        sql.addSql("   CLH_AUID      integer not null,");
        sql.addSql("   CLH_ADATE     timestamp not null,");
        sql.addSql("   CLH_EUID      integer not null,");
        sql.addSql("   CLH_EDATE     timestamp not null");
        sql.addSql(" )");
        sqlList.add(sql);

        //CREATE CMN_MBL_UID
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_MBL_UID");
        sql.addSql(" (");
        sql.addSql("   USR_SID     integer not null,");
        sql.addSql("   CMU_UID_1   varchar(50),");
        sql.addSql("   CMU_UID_2   varchar(50),");
        sql.addSql("   CMU_UID_3   varchar(50),");
        sql.addSql("   CMU_AUID    integer not null,");
        sql.addSql("   CMU_ADATE   timestamp not null,");
        sql.addSql("   CMU_EUID    integer not null,");
        sql.addSql("   CMU_EDATE   timestamp not null,");
        sql.addSql("   primary key(USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        //CREATE CMN_LHIS_BATCH_CONF
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LHIS_BATCH_CONF");
        sql.addSql(" (");
        sql.addSql("   CBC_ADL_KBN    integer not null,");
        sql.addSql("   CBC_ADL_YEAR   integer,");
        sql.addSql("   CBC_ADL_MONTH  integer,");
        sql.addSql("   CBC_AUID       integer not null,");
        sql.addSql("   CBC_ADATE      timestamp not null,");
        sql.addSql("   CBC_EUID       integer not null,");
        sql.addSql("   CBC_EDATE      timestamp not null");
        sql.addSql(" )");
        sqlList.add(sql);

        //CREATE SML_ADEL
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ADEL");
        sql.addSql(" (");
        sql.addSql("   USR_SID          integer    not null,");
        sql.addSql("   SAD_USR_KBN      integer    not null,");
        sql.addSql("   SAD_DEL_KBN      integer    not null,");
        sql.addSql("   SAD_JDEL_KBN     integer    not null,");
        sql.addSql("   SAD_JDEL_YEAR    integer    not null,");
        sql.addSql("   SAD_JDEL_MONTH   integer    not null,");
        sql.addSql("   SAD_SDEL_KBN     integer    not null,");
        sql.addSql("   SAD_SDEL_YEAR    integer    not null,");
        sql.addSql("   SAD_SDEL_MONTH   integer    not null,");
        sql.addSql("   SAD_WDEL_KBN     integer    not null,");
        sql.addSql("   SAD_WDEL_YEAR    integer    not null,");
        sql.addSql("   SAD_WDEL_MONTH   integer    not null,");
        sql.addSql("   SAD_DDEL_KBN     integer    not null,");
        sql.addSql("   SAD_DDEL_YEAR    integer    not null,");
        sql.addSql("   SAD_DDEL_MONTH   integer    not null,");
        sql.addSql("   SAD_AUID         integer    not null,");
        sql.addSql("   SAD_ADATE        timestamp  not null,");
        sql.addSql("   SAD_EUID         integer    not null,");
        sql.addSql("   SAD_EDATE        timestamp  not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        //CREATE ADR_UCONF
        sql = new SqlBuffer();
        sql.addSql("create table ADR_UCONF");
        sql.addSql("(");
        sql.addSql("  USR_SID         integer      not null,");
        sql.addSql("  AUC_ADRCOUNT    integer      not null,");
        sql.addSql("  AUC_COMCOUNT    integer      not null,");
        sql.addSql("  AUC_AUID        integer      not null,");
        sql.addSql("  AUC_ADATE       timestamp   not null,");
        sql.addSql("  AUC_EUID        integer      not null,");
        sql.addSql("  AUC_EDATE       timestamp   not null,");
        sql.addSql("  primary key (USR_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_TYPEINDUSTRY
        sql = new SqlBuffer();
        sql.addSql("create table ADR_TYPEINDUSTRY");
        sql.addSql("(");
        sql.addSql("  ATI_SID     integer         not null,");
        sql.addSql("  ATI_NAME    varchar(20)     not null,");
        sql.addSql("  ATI_BIKO    varchar(300),");
        sql.addSql("  ATI_SORT    integer         not null,");
        sql.addSql("  ATI_AUID    integer         not null,");
        sql.addSql("  ATI_ADATE   timestamp      not null,");
        sql.addSql("  ATI_EUID    integer         not null,");
        sql.addSql("  ATI_EDATE   timestamp      not null,");
        sql.addSql("  primary key (ATI_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_POSITION
        sql = new SqlBuffer();
        sql.addSql("create table ADR_POSITION");
        sql.addSql("(");
        sql.addSql("  APS_SID         integer  not null,");
        sql.addSql("  APS_NAME        varchar(20)  not null,");
        sql.addSql("  APS_AUID        integer  not null,");
        sql.addSql("  APS_ADATE       timestamp  not null,");
        sql.addSql("  APS_EUID        integer  not null,");
        sql.addSql("  APS_EDATE       timestamp  not null,");
        sql.addSql("  APS_BIKO        varchar(300),");
        sql.addSql("  APS_SORT        integer not null,");
        sql.addSql("  primary key(APS_SID)");
        sql.addSql(")");
        sqlList.add(sql);


        //CREATE ADR_PERSONCHARGE
        sql = new SqlBuffer();
        sql.addSql("create table ADR_PERSONCHARGE");
        sql.addSql("(");
        sql.addSql("  ADR_SID     integer         not null,");
        sql.addSql("  USR_SID     integer         not null,");
        sql.addSql("  APC_AUID    integer         not null,");
        sql.addSql("  APC_ADATE   timestamp      not null,");
        sql.addSql("  APC_EUID    integer         not null,");
        sql.addSql("  APC_EDATE   timestamp      not null,");
        sql.addSql("  primary key (ADR_SID, USR_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_PERMIT_VIEW
        sql = new SqlBuffer();
        sql.addSql("create table ADR_PERMIT_VIEW");
        sql.addSql("(");
        sql.addSql("  ADR_SID         integer      not null,");
        sql.addSql("  ADR_PERMIT_VIEW integer      not null,");
        sql.addSql("  GRP_SID         integer      not null,");
        sql.addSql("  USR_SID         integer      not null,");
        sql.addSql("  APV_AUID        integer      not null,");
        sql.addSql("  APV_ADATE       timestamp   not null,");
        sql.addSql("  APV_EUID        integer      not null,");
        sql.addSql("  APV_EDATE       timestamp   not null,");
        sql.addSql("  primary key (ADR_SID, GRP_SID, USR_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_PERMIT_EDIT
        sql = new SqlBuffer();
        sql.addSql("create table ADR_PERMIT_EDIT");
        sql.addSql("(");
        sql.addSql("  ADR_SID         integer      not null,");
        sql.addSql("  ADR_PERMIT_EDIT integer      not null,");
        sql.addSql("  GRP_SID         integer      not null,");
        sql.addSql("  USR_SID         integer      not null,");
        sql.addSql("  APE_AUID        integer      not null,");
        sql.addSql("  APE_ADATE       timestamp   not null,");
        sql.addSql("  APE_EUID        integer      not null,");
        sql.addSql("  APE_EDATE       timestamp   not null,");
        sql.addSql("  primary key (ADR_SID, GRP_SID, USR_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_LABEL
        sql = new SqlBuffer();
        sql.addSql("create table ADR_LABEL");
        sql.addSql("(");
        sql.addSql("  ALB_SID     integer         not null,");
        sql.addSql("  ALB_NAME    varchar(20)     not null,");
        sql.addSql("  ALB_BIKO    varchar(300),");
        sql.addSql("  ALB_SORT    integer         not null,");
        sql.addSql("  ALB_AUID    integer         not null,");
        sql.addSql("  ALB_ADATE   timestamp      not null,");
        sql.addSql("  ALB_EUID    integer         not null,");
        sql.addSql("  ALB_EDATE   timestamp      not null,");
        sql.addSql("  primary key (ALB_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_CONTACT
        sql = new SqlBuffer();
        sql.addSql("create table ADR_CONTACT");
        sql.addSql("(");
        sql.addSql("  ADC_SID         integer  not null,");
        sql.addSql("  ADC_GRP_SID     integer  not null default -1,");
        sql.addSql("  ADR_SID         integer  not null,");
        sql.addSql("  ADC_TITLE       varchar(100)  not null,");
        sql.addSql("  ADC_TYPE        integer  not null,");
        sql.addSql("  ADC_CTTIME      timestamp,");
        sql.addSql("  ADC_CTTIME_TO   timestamp,");
        sql.addSql("  PRJ_SID         integer,");
        sql.addSql("  ADC_BIKO        text,");
        sql.addSql("  ADC_AUID        integer  not null,");
        sql.addSql("  ADC_ADATE       timestamp  not null,");
        sql.addSql("  ADC_EUID        integer  not null,");
        sql.addSql("  ADC_EDATE       timestamp  not null,");
        sql.addSql("  primary key(ADC_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_CONTACT_BIN
        sql = new SqlBuffer();
        sql.addSql("create table ADR_CONTACT_BIN");
        sql.addSql("(");
        sql.addSql("  ADC_SID         integer  not null,");
        sql.addSql("  BIN_SID         integer  not null,");
        sql.addSql("  ACB_AUID        integer  not null,");
        sql.addSql("  ACB_ADATE       timestamp  not null,");
        sql.addSql("  ACB_EUID        integer  not null,");
        sql.addSql("  ACB_EDATE       timestamp  not null,");
        sql.addSql("  primary key(ADC_SID, BIN_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_COMPANY
        sql = new SqlBuffer();
        sql.addSql("create table ADR_COMPANY");
        sql.addSql("(");
        sql.addSql("  ACO_SID         integer  not null,");
        sql.addSql("  ACO_CODE        varchar(20)  not null,");
        sql.addSql("  ACO_NAME        varchar(50)  not null,");
        sql.addSql("  ACO_NAME_KN     varchar(100)  not null,");
        sql.addSql("  ACO_URL         varchar(100),");
        sql.addSql("  ACO_BIKO        varchar(1000),");
        sql.addSql("  ACO_AUID        integer  not null,");
        sql.addSql("  ACO_ADATE       timestamp  not null,");
        sql.addSql("  ACO_EUID        integer  not null,");
        sql.addSql("  ACO_EDATE       timestamp  not null,");
        sql.addSql("  primary key(ACO_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_COMPANY_BASE
        sql = new SqlBuffer();
        sql.addSql("create table ADR_COMPANY_BASE");
        sql.addSql("(");
        sql.addSql("  ABA_SID         integer  not null,");
        sql.addSql("  ACO_SID         integer  not null,");
        sql.addSql("  ABA_TYPE        integer  not null,");
        sql.addSql("  ABA_NAME        varchar(50)  not null,");
        sql.addSql("  ABA_POSTNO1     varchar(3),");
        sql.addSql("  ABA_POSTNO2     varchar(4),");
        sql.addSql("  TDF_SID         integer,");
        sql.addSql("  ABA_ADDR1       varchar(100),");
        sql.addSql("  ABA_ADDR2       varchar(100),");
        sql.addSql("  ABA_BIKO        varchar(1000),");
        sql.addSql("  ABA_AUID        integer  not null,");
        sql.addSql("  ABA_ADATE       timestamp  not null,");
        sql.addSql("  ABA_EUID        integer  not null,");
        sql.addSql("  ABA_EDATE       timestamp  not null,");
        sql.addSql("  primary key(ABA_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_BELONG_LABEL
        sql = new SqlBuffer();
        sql.addSql("create table ADR_BELONG_LABEL");
        sql.addSql("(");
        sql.addSql("  ADR_SID     integer         not null,");
        sql.addSql("  ALB_SID     integer         not null,");
        sql.addSql("  ABL_AUID    integer         not null,");
        sql.addSql("  ABL_ADATE   timestamp      not null,");
        sql.addSql("  ABL_EUID    integer         not null,");
        sql.addSql("  ABL_EDATE   timestamp      not null,");
        sql.addSql("  primary key (ADR_SID, ALB_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_BELONG_INDUSTRY
        sql = new SqlBuffer();
        sql.addSql("create table ADR_BELONG_INDUSTRY");
        sql.addSql("(");
        sql.addSql("  ACO_SID     integer         not null,");
        sql.addSql("  ATI_SID     integer         not null,");
        sql.addSql("  ABI_AUID    integer         not null,");
        sql.addSql("  ABI_ADATE   timestamp      not null,");
        sql.addSql("  ABI_EUID    integer         not null,");
        sql.addSql("  ABI_EDATE   timestamp      not null,");
        sql.addSql("  primary key (ACO_SID, ATI_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_ADDRESS
        sql = new SqlBuffer();
        sql.addSql("create table ADR_ADDRESS");
        sql.addSql("(");
        sql.addSql("  ADR_SID          integer           not null,");
        sql.addSql("  ADR_SEI          varchar(10)       not null,");
        sql.addSql("  ADR_MEI          varchar(10)       not null,");
        sql.addSql("  ADR_SEI_KN       varchar(20)       not null,");
        sql.addSql("  ADR_MEI_KN       varchar(20)       not null,");
        sql.addSql("  ADR_SINI         varchar(3)        not null,");
        sql.addSql("  ACO_SID          integer,");
        sql.addSql("  ABA_SID          integer,");
        sql.addSql("  ADR_SYOZOKU      varchar(20),");
        sql.addSql("  APS_SID          integer,");
        sql.addSql("  ADR_MAIL1        varchar(50),");
        sql.addSql("  ADR_MAIL_CMT1    varchar(10),");
        sql.addSql("  ADR_MAIL2        varchar(50),");
        sql.addSql("  ADR_MAIL_CMT2    varchar(10),");
        sql.addSql("  ADR_MAIL3        varchar(50),");
        sql.addSql("  ADR_MAIL_CMT3    varchar(10),");
        sql.addSql("  ADR_POSTNO1      varchar(3),");
        sql.addSql("  ADR_POSTNO2      varchar(4),");
        sql.addSql("  TDF_SID          integer,");
        sql.addSql("  ADR_ADDR1        varchar(100),");
        sql.addSql("  ADR_ADDR2        varchar(100),");
        sql.addSql("  ADR_TEL1         varchar(20),");
        sql.addSql("  ADR_TEL_NAI1     varchar(10),");
        sql.addSql("  ADR_TEL_CMT1     varchar(10),");
        sql.addSql("  ADR_TEL2         varchar(20),");
        sql.addSql("  ADR_TEL_NAI2     varchar(10),");
        sql.addSql("  ADR_TEL_CMT2     varchar(10),");
        sql.addSql("  ADR_TEL3         varchar(20),");
        sql.addSql("  ADR_TEL_NAI3     varchar(10),");
        sql.addSql("  ADR_TEL_CMT3     varchar(10),");
        sql.addSql("  ADR_FAX1         varchar(20),");
        sql.addSql("  ADR_FAX_CMT1     varchar(10),");
        sql.addSql("  ADR_FAX2         varchar(20),");
        sql.addSql("  ADR_FAX_CMT2     varchar(10),");
        sql.addSql("  ADR_FAX3         varchar(20),");
        sql.addSql("  ADR_FAX_CMT3     varchar(10),");
        sql.addSql("  ADR_BIKO         varchar(1000),");
        sql.addSql("  ADR_PERMIT_VIEW  integer,");
        sql.addSql("  ADR_PERMIT_EDIT  integer,");
        sql.addSql("  ADR_AUID         integer           not null,");
        sql.addSql("  ADR_ADATE        timestamp        not null,");
        sql.addSql("  ADR_EUID         integer           not null,");
        sql.addSql("  ADR_EDATE        timestamp        not null,");
        sql.addSql("  primary key (ADR_SID)");
        sql.addSql(")");
        sqlList.add(sql);

        //CREATE ADR_ACONF
        sql = new SqlBuffer();
        sql.addSql("create table ADR_ACONF");
        sql.addSql("(");
        sql.addSql("  AAC_ATI_EDIT    integer  not null,");
        sql.addSql("  AAC_ACO_EDIT    integer  not null,");
        sql.addSql("  AAC_ALB_EDIT    integer  not null,");
        sql.addSql("  AAC_EXPORT      integer  not null,");
        sql.addSql("  AAC_AUID        integer  not null,");
        sql.addSql("  AAC_ADATE       timestamp  not null,");
        sql.addSql("  AAC_EUID        integer  not null,");
        sql.addSql("  AAC_EDATE       timestamp  not null,");
        sql.addSql("  AAC_YKS_EDIT    integer  not null");
        sql.addSql(")");
        sqlList.add(sql);

        //INSERT CMN_SAIBAN
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" CMN_SAIBAN(");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  'address',");
        sql.addSql("  'adressbook',");
        sql.addSql("  100,");
        sql.addSql("  'adressbook',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //INSERT ADR_TYPEINDUSTRY
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   1,");
        sql.addSql("   '農業，林業',");
        sql.addSql("   '',");
        sql.addSql("   1,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   2,");
        sql.addSql("   '漁業',");
        sql.addSql("   '',");
        sql.addSql("   2,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   3,");
        sql.addSql("   '鉱業，採石業，砂利採取業',");
        sql.addSql("   '',");
        sql.addSql("   3,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   4,");
        sql.addSql("   '建設業',");
        sql.addSql("   '',");
        sql.addSql("   4,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   5,");
        sql.addSql("   '製造業',");
        sql.addSql("   '',");
        sql.addSql("   5,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   6,");
        sql.addSql("   '電気・ガス・熱供給・水道業',");
        sql.addSql("   '',");
        sql.addSql("   6,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   7,");
        sql.addSql("   '情報通信業',");
        sql.addSql("   '',");
        sql.addSql("   7,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   8,");
        sql.addSql("   '運輸業，郵便業',");
        sql.addSql("   '',");
        sql.addSql("   8,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   9,");
        sql.addSql("   '卸売業，小売業',");
        sql.addSql("   '',");
        sql.addSql("   9,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   10,");
        sql.addSql("   '金融業，保険業',");
        sql.addSql("   '',");
        sql.addSql("   10,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   11,");
        sql.addSql("   '不動産業，物品賃貸業',");
        sql.addSql("   '',");
        sql.addSql("   11,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   12,");
        sql.addSql("   '学術研究，専門・技術サービス業',");
        sql.addSql("   '',");
        sql.addSql("   12,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   13,");
        sql.addSql("   '宿泊業，飲食サービス業',");
        sql.addSql("   '',");
        sql.addSql("   13,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   14,");
        sql.addSql("   '生活関連サービス業，娯楽業',");
        sql.addSql("   '',");
        sql.addSql("   14,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   15,");
        sql.addSql("   '教育，学習支援業',");
        sql.addSql("   '',");
        sql.addSql("   15,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   16,");
        sql.addSql("   '医療，福祉',");
        sql.addSql("   '',");
        sql.addSql("   16,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   17,");
        sql.addSql("   '複合サービス事業',");
        sql.addSql("   '',");
        sql.addSql("   17,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   18,");
        sql.addSql("   'サービス業（他に分類されないもの）',");
        sql.addSql("   '',");
        sql.addSql("   18,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   19,");
        sql.addSql("   '公務（他に分類されるものを除く）',");
        sql.addSql("   '',");
        sql.addSql("   19,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into ADR_TYPEINDUSTRY(");
        sql.addSql("   ATI_SID,");
        sql.addSql("   ATI_NAME,");
        sql.addSql("   ATI_BIKO,");
        sql.addSql("   ATI_SORT,");
        sql.addSql("   ATI_AUID,");
        sql.addSql("   ATI_ADATE,");
        sql.addSql("   ATI_EUID,");
        sql.addSql("   ATI_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   20,");
        sql.addSql("   '分類不能の産業',");
        sql.addSql("   '',");
        sql.addSql("   20,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //INSERT CMN_SAIBAN
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" CMN_SAIBAN(");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  'address',");
        sql.addSql("  'company',");
        sql.addSql("  100,");
        sql.addSql("  'company',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //INSERT CMN_SAIBAN
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" CMN_SAIBAN(");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  'address',");
        sql.addSql("  'cobase',");
        sql.addSql("  100,");
        sql.addSql("  'cobase',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //INSERT CMN_SAIBAN
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" CMN_SAIBAN(");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("  'address',");
        sql.addSql("  'industry',");
        sql.addSql("  20,");
        sql.addSql("  'industry',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        return sqlList;
    }
}