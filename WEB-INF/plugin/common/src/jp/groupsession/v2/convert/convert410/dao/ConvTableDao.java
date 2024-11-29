package jp.groupsession.v2.convert.convert410.dao;

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
 * <br>[解  説] v4.1.0へコンバートする際に使用する
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

        //言語
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LANG");
        sql.addSql(" (");
        sql.addSql("   LNG_SID       integer      not null,");
        sql.addSql("   LNG_NAME      varchar (20) not null,");
        sql.addSql("   LNG_CODE      varchar (20) not null,");
        sql.addSql("   LNG_COUNTRY   varchar (20) not null,");
        sql.addSql("   primary key (LNG_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_LANG");
        sql.addSql(" (LNG_SID, LNG_NAME, LNG_CODE, LNG_COUNTRY)");
        sql.addSql("    values (1,'日本語','ja','JP'");
        sql.addSql("  );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_LANG");
        sql.addSql(" (LNG_SID, LNG_NAME, LNG_CODE, LNG_COUNTRY)");
        sql.addSql("    values (2,'English','en','US'");
        sql.addSql("  );");
        sqlList.add(sql);

        //ユーザ言語
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USR_LANG");
        sql.addSql(" (");
        sql.addSql("   USR_SID        integer         not null,");
        sql.addSql("   CUL_COUNTRY    varchar (20)    not null,");
        sql.addSql("   CUL_AUID       integer         not null,");
        sql.addSql("   CUL_ADATE      timestamp       not null,");
        sql.addSql("   CUL_EUID       integer         not null,");
        sql.addSql("   CUL_EDATE      timestamp       not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ユーザID
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM alter USR_LGID type VARCHAR(256);");
        sqlList.add(sql);
        //ユーザPASS
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM alter USR_PSWD type VARCHAR(352);");
        sqlList.add(sql);

        //WEBメール ディスク容量制限
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_DISK_COMP integer default 0;");
        sqlList.add(sql);

        //回覧板初期値管理者設定
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_INIT");
        sql.addSql(" (");
        sql.addSql("   CIN_INITSET_KEN  integer       not null,");
        sql.addSql("   CIN_MEMO_KBN     integer       not null,");
        sql.addSql("   CIN_MEMO_DAY     integer       not null,");
        sql.addSql("   CIN_KOU_KBN      integer       not null,");
        sql.addSql("   CIN_AUID         integer       not null,");
        sql.addSql("   CIN_ADATE        timestamp     not null,");
        sql.addSql("   CIN_EUID         integer       not null,");
        sql.addSql("   CIN_EDATE        timestamp     not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //回覧板 初期値個人設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER add CUR_MEMO_KBN integer not null default 0;");
        sqlList.add(sql);

        //回覧板 初期値個人設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER add CUR_MEMO_DAY integer not null default 0;");
        sqlList.add(sql);

        //回覧板 初期値個人設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER add CUR_KOU_KBN integer not null default 0;");
        sqlList.add(sql);

        //回覧板 初期値個人設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER add CUR_INIT_KBN integer not null default 0;");
        sqlList.add(sql);

        //日報
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_ADM_CONF");
        sql.addSql(" (");
        sql.addSql("  NAC_CRANGE            integer           not null,");
        sql.addSql("  NAC_ATDEL_FLG         integer           not null,");
        sql.addSql("  NAC_ATDEL_Y           integer                   ,");
        sql.addSql("  NAC_ATDEL_M           integer                   ,");
        sql.addSql("  NAC_HOUR_DIV          integer                   ,");
        sql.addSql("  NAC_SML_KBN           integer                   ,");
        sql.addSql("  NAC_SML_NOTICE_KBN    integer                   ,");
        sql.addSql("  NAC_SML_NOTICE_GRP    integer                   ,");
        sql.addSql("  NAC_CSML_KBN          integer                   ,");
        sql.addSql("  NAC_CSML_NOTICE_KBN   integer                   ,");
        sql.addSql("  NAC_GSML_KBN          integer                   ,");
        sql.addSql("  NAC_GSML_NOTICE_KBN   integer                   ,");
        sql.addSql("  NAC_AUID              integer           not null,");
        sql.addSql("  NAC_ADATE             timestamp         not null,");
        sql.addSql("  NAC_EUID              integer           not null,");
        sql.addSql("  NAC_EDATE             timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table NTP_AN_SHOHIN");
        sql.addSql(" (");
        sql.addSql("   NAN_SID                integer           not null,");
        sql.addSql("   NHN_SID                integer           not null,");
        sql.addSql("   NAS_AUID               integer                   ,");
        sql.addSql("   NAS_ADATE              timestamp         not null,");
        sql.addSql("   NAS_EUID               integer                   ,");
        sql.addSql("   NAS_EDATE              timestamp         not null,");
        sql.addSql("   primary key (NAN_SID, NHN_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_ANKEN");
        sql.addSql("(");
        sql.addSql("  NAN_SID               integer           not null,");
        sql.addSql("  NAN_CODE              varchar(8)        not null,");
        sql.addSql("  NAN_NAME              varchar(100)      not null,");
        sql.addSql("  NAN_DETIAL            varchar(1000)     not null,");
        sql.addSql("  NAN_DATE              timestamp         not null,");
        sql.addSql("  ACO_SID               integer           not null,");
        sql.addSql("  ABA_SID               integer           not null,");
        sql.addSql("  NGP_SID               integer                   ,");
        sql.addSql("  NAN_MIKOMI            integer                   ,");
        sql.addSql("  NAN_KIN_MITUMORI      integer                   ,");
        sql.addSql("  NAN_KIN_JUTYU         integer                   ,");
        sql.addSql("  NAN_SYODAN            integer                   ,");
        sql.addSql("  NAN_STATE             integer                   ,");
        sql.addSql("  NCN_SID               integer                   ,");
        sql.addSql("  NAN_AUID              integer                   ,");
        sql.addSql("  NAN_ADATE             timestamp         not null,");
        sql.addSql("  NAN_EUID              integer                   ,");
        sql.addSql("  NAN_EDATE             timestamp         not null,");
        sql.addSql("  primary key (NAN_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_BIN_TEMPU");
        sql.addSql("(");
        sql.addSql("  NBT_SID               integer           not null,");
        sql.addSql("  NIP_SID               integer           not null,");
        sql.addSql("  BIN_SID               integer           not null,");
        sql.addSql("  NBT_AUID              integer                   ,");
        sql.addSql("  NBT_ADATE             timestamp         not null,");
        sql.addSql("  NBT_EUID              integer                   ,");
        sql.addSql("  NBT_EDATE             timestamp         not null,");
        sql.addSql("  primary key (NBT_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_BIN");
        sql.addSql("(");
        sql.addSql("  NTP_SID        integer      not null,");
        sql.addSql("  BIN_SID        bigint       not null,");
        sql.addSql("  primary key (NTP_SID, BIN_SID)");
        sql.addSql(") ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_COL_MSG");
        sql.addSql("(");
        sql.addSql("      NCM_ID                integer           not null,");
        sql.addSql("      NCM_MSG               varchar(50)               ,");
        sql.addSql("      NCM_AUID              integer                   ,");
        sql.addSql("      NCM_ADATE             timestamp         not null,");
        sql.addSql("      NCM_EUID              integer                   ,");
        sql.addSql("      NCM_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NCM_ID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_MIKOMIDO_MSG");
        sql.addSql("(");
        sql.addSql("      NMM_ID                integer           not null,");
        sql.addSql("      NMM_MSG               varchar(1000)               ,");
        sql.addSql("      NMM_AUID              integer                   ,");
        sql.addSql("      NMM_ADATE             timestamp         not null,");
        sql.addSql("      NMM_EUID              integer                   ,");
        sql.addSql("      NMM_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NMM_ID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_COMMENT");
        sql.addSql("(");
        sql.addSql("      NPC_SID               integer           not null,");
        sql.addSql("      NIP_SID               integer           not null,");
        sql.addSql("      USR_SID               integer           not null,");
        sql.addSql("      NPC_COMMENT           varchar(1000)     not null,");
        sql.addSql("      NPC_VIEW_KBN          integer           not null,");
        sql.addSql("      NPC_AUID              integer                   ,");
        sql.addSql("      NPC_ADATE             timestamp         not null,");
        sql.addSql("      NPC_EUID              integer                   ,");
        sql.addSql("      NPC_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NPC_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_CONTACT");
        sql.addSql("(");
        sql.addSql("      NCN_SID               integer           not null,");
        sql.addSql("      NCN_CODE              varchar(5)        not null,");
        sql.addSql("      NCN_NAME              varchar(50)       not null,");
        sql.addSql("      NCN_AUID              integer                   ,");
        sql.addSql("      NCN_ADATE             timestamp                 ,");
        sql.addSql("      NCN_EUID              integer                   ,");
        sql.addSql("      NCN_EDATE             timestamp                 ,");
        sql.addSql("      primary key (NCN_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_DATA");
        sql.addSql("(");
        sql.addSql("      NIP_SID               integer           not null,");
        sql.addSql("      USR_SID               integer           not null,");
        sql.addSql("      NIP_DATE              timestamp         not null,");
        sql.addSql("      NIP_FR_TIME           timestamp         not null,");
        sql.addSql("      NIP_TO_TIME           timestamp         not null,");
        sql.addSql("      NIP_KADO_HH           integer           not null,");
        sql.addSql("      NIP_KADO_MM           integer           not null,");
        sql.addSql("      NIP_MGY_SID           integer           not null,");
        sql.addSql("      NAN_SID               integer           not null,");
        sql.addSql("      ACO_SID               integer                   ,");
        sql.addSql("      ABA_SID               integer                   ,");
        sql.addSql("      NIP_TITLE             varchar(100)       not null,");
        sql.addSql("      NIP_TITLE_CLO         integer                   ,");
        sql.addSql("      MPR_SID               integer           not null,");
        sql.addSql("      MKB_SID               integer           not null,");
        sql.addSql("      MKH_SID               integer           not null,");
        sql.addSql("      NIP_TIEUP_SID         integer                   ,");
        sql.addSql("      NIP_KEIZOKU           integer           not null,");
        sql.addSql("      NIP_ACTEND            timestamp                 ,");
        sql.addSql("      NIP_DETAIL            varchar(1000)     not null,");
        sql.addSql("      NIP_ACTION_DATE       timestamp                 ,");
        sql.addSql("      NIP_ACTION            varchar(1000)             ,");
        sql.addSql("      NIP_ACTDATE_KBN       integer                   ,");
        sql.addSql("      NIP_ASSIGN            varchar(1000)             ,");
        sql.addSql("      NIP_KINGAKU           integer                   ,");
        sql.addSql("      NIP_MIKOMI            integer                   ,");
        sql.addSql("      NIP_SYOKAN            varchar(1000)             ,");
        sql.addSql("      NIP_PUBLIC            integer                   ,");
        sql.addSql("      NIP_EDIT              integer                   ,");
        sql.addSql("      NEX_SID               integer                   ,");
        sql.addSql("      NIP_AUID              integer                   ,");
        sql.addSql("      NIP_ADATE             timestamp         not null,");
        sql.addSql("      NIP_EUID              integer                   ,");
        sql.addSql("      NIP_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NIP_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_EXTENED");
        sql.addSql("(");
        sql.addSql("      NEX_SID               integer           not null,");
        sql.addSql("      NEX_KBN               integer           not null,");
        sql.addSql("      NEX_DWEEK1            integer                   ,");
        sql.addSql("      NEX_DWEEK2            integer                   ,");
        sql.addSql("      NEX_DWEEK3            integer                   ,");
        sql.addSql("      NEX_DWEEK4            integer                   ,");
        sql.addSql("      NEX_DWEEK5            integer                   ,");
        sql.addSql("      NEX_DWEEK6            integer                   ,");
        sql.addSql("      NEX_DWEEK7            integer                   ,");
        sql.addSql("      NEX_DAY               integer                   ,");
        sql.addSql("      NEX_WEEK              integer                   ,");
        sql.addSql("      NEX_TIME_FR            timestamp        not null,");
        sql.addSql("      NEX_TIME_TO            timestamp        not null,");
        sql.addSql("      NEX_KADO_HH           integer           not null,");
        sql.addSql("      NEX_KADO_MM           integer           not null,");
        sql.addSql("      NEX_TRAN_KBN          integer           not null,");
        sql.addSql("      NEX_DATE_FR           timestamp         not null,");
        sql.addSql("      NEX_DATE_TO           timestamp         not null,");
        sql.addSql("      NEX_MGY_SID           integer           not null,");
        sql.addSql("      NEX_NAN_SID           integer           not null,");
        sql.addSql("      NEX_ACO_SID           integer                   ,");
        sql.addSql("      NEX_ABO_SID           integer                   ,");
        sql.addSql("      NEX_TITLE             varchar(50)       not null,");
        sql.addSql("      NEX_TITLE_CLO         integer                   ,");
        sql.addSql("      NEX_MPR_SID           integer           not null,");
        sql.addSql("      NEX_MKB_SID           integer           not null,");
        sql.addSql("      NEX_MKH_SID           integer           not null,");
        sql.addSql("      NEX_TIEUP_SID         integer                   ,");
        sql.addSql("      NEX_KEIZOKU           integer           not null,");
        sql.addSql("      NEX_ACTEND            timestamp                 ,");
        sql.addSql("      NEX_DETAIL            varchar(1000)     not null,");
        sql.addSql("      NEX_ASSIGN            varchar(1000)             ,");
        sql.addSql("      NEX_KINGAKU           integer                   ,");
        sql.addSql("      NEX_MIKOMI            integer                   ,");
        sql.addSql("      NEX_SYOKAN            varchar(1000)             ,");
        sql.addSql("      NEX_PUBLIC            integer                   ,");
        sql.addSql("      NEX_EDIT              integer                   ,");
        sql.addSql("      NEX_AUID              integer                   ,");
        sql.addSql("      NEX_ADATE             timestamp         not null,");
        sql.addSql("      NEX_EUID              integer                   ,");
        sql.addSql("      NEX_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NEX_SID)");
        sql.addSql(");");
        sqlList.add(sql);


        sql = new SqlBuffer();
        sql.addSql("create table NTP_GOOD");
        sql.addSql("(");
        sql.addSql("  NTP_SID         integer      not null,");
        sql.addSql("  USR_SID        integer       not null,");
        sql.addSql("  primary key (NTP_SID, USR_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_GYOMU");
        sql.addSql("(");
        sql.addSql("      NGY_SID               integer           not null,");
        sql.addSql("      NGY_CODE              varchar(5)        not null,");
        sql.addSql("      NGY_NAME              varchar(50)       not null,");
        sql.addSql("      NGY_AUID              integer                   ,");
        sql.addSql("      NGY_ADATE             timestamp                 ,");
        sql.addSql("      NGY_EUID              integer                   ,");
        sql.addSql("      NGY_EDATE             timestamp                 ,");
        sql.addSql("      primary key (NGY_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_KAKUNIN");
        sql.addSql("(");
        sql.addSql("      NKK_SID               integer           not null,");
        sql.addSql("      NIP_SID               integer           not null,");
        sql.addSql("      USR_SID               integer           not null,");
        sql.addSql("      NKK_CHECK             integer           not null,");
        sql.addSql("      NKK_DATE_CHECK        timestamp                 ,");
        sql.addSql("      NKK_AUID              integer                   ,");
        sql.addSql("      NKK_ADATE             timestamp         not null,");
        sql.addSql("      NKK_EUID              integer                   ,");
        sql.addSql("      NKK_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NKK_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_KTBUNRUI_SORT");
        sql.addSql("(");
        sql.addSql("  NKB_SID         integer      not null,");
        sql.addSql("  NKS_SORT        integer      not null,");
        sql.addSql("  primary key (NKB_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_KTBUNRUI");
        sql.addSql("(");
        sql.addSql("      NKB_SID               integer           not null,");
        sql.addSql("      NKB_CODE              varchar(5)        not null,");
        sql.addSql("      NKB_NAME              varchar(50)       not null,");
        sql.addSql("      NKB_TIEUP_KBN         integer           not null,");
        sql.addSql("      NKB_AUID              integer                   ,");
        sql.addSql("      NKB_ADATE             timestamp                 ,");
        sql.addSql("      NKB_EUID              integer                   ,");
        sql.addSql("      NKB_EDATE             timestamp                 ,");
        sql.addSql("      primary key (NKB_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_KTHOUHOU");
        sql.addSql("(");
        sql.addSql("      NKH_SID               integer           not null,");
        sql.addSql("      NKH_CODE              varchar(5)        not null,");
        sql.addSql("      NKH_NAME              varchar(50)       not null,");
        sql.addSql("      NKH_AUID              integer                   ,");
        sql.addSql("      NKH_ADATE             timestamp                 ,");
        sql.addSql("      NKH_EUID              integer                   ,");
        sql.addSql("      NKH_EDATE             timestamp                 ,");
        sql.addSql("      primary key (NKH_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_NI_KATUDO");
        sql.addSql("(");
        sql.addSql("      NKD_SID      integer           not null,");
        sql.addSql("      NIP_SID      integer           not null,");
        sql.addSql("      NAN_SID      integer           not null,");
        sql.addSql("      ACO_SID      integer           not null,");
        sql.addSql("      ABA_SID      integer           not null,");
        sql.addSql("      NKD_ACEDATE  timestamp         not null,");
        sql.addSql("      NKD_ACDATE   timestamp                 ,");
        sql.addSql("      NKD_AUID     integer           not null,");
        sql.addSql("      NKD_ADATE    timestamp         not null,");
        sql.addSql("      NKD_EUID     integer           not null,");
        sql.addSql("      NKD_EDATE    timestamp         not null,");
        sql.addSql("      primary key (NKD_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_NI_SHOHIN");
        sql.addSql("(");
        sql.addSql("      NSH_SID               integer           not null,");
        sql.addSql("      NIP_SID               integer           not null,");
        sql.addSql("      MHN_SID               integer           not null,");
        sql.addSql("      NSH_NAME              varchar(50)               ,");
        sql.addSql("      NSH_PRICE_SALE        integer                   ,");
        sql.addSql("      NSH_PRICE_COST        integer                   ,");
        sql.addSql("      NSH_AUID              integer                   ,");
        sql.addSql("      NSH_ADATE             timestamp         not null,");
        sql.addSql("      NSH_EUID              integer                   ,");
        sql.addSql("      NSH_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NSH_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_PRI_CONF");
        sql.addSql("(");
        sql.addSql("      USR_SID               integer           not null,");
        sql.addSql("      NPR_DSP_GROUP         integer           not null,");
        sql.addSql("      NPR_SORT_KEY1         integer           not null,");
        sql.addSql("      NPR_SORT_ORDER1       integer           not null,");
        sql.addSql("      NPR_SORT_KEY2         integer           not null,");
        sql.addSql("      NPR_SORT_ORDER2       integer           not null,");
        sql.addSql("      NPR_INI_FR_DATE       timestamp         not null,");
        sql.addSql("      NPR_INI_TO_DATE       timestamp         not null,");
        sql.addSql("      NPR_INI_FCOLOR        integer           not null,");
        sql.addSql("      NPR_DSP_LIST          integer           not null,");
        sql.addSql("      NPR_AUTO_RELOAD       integer           not null,");
        sql.addSql("      NPR_DSP_MYGROUP       integer                   ,");
        sql.addSql("      NPR_DSP_POSITION      integer           not null,");
        sql.addSql("      NPR_SMAIL             integer           not null,");
        sql.addSql("      NPR_CMT_SMAIL         integer           not null,");
        sql.addSql("      NPR_GOOD_SMAIL        integer           not null,");
        sql.addSql("      NPR_SCH_KBN           integer           not null,");
        sql.addSql("      NPR_AUID              integer           not null,");
        sql.addSql("      NPR_ADATE             timestamp         not null,");
        sql.addSql("      NPR_EUID              integer           not null,");
        sql.addSql("      NPR_EDATE             timestamp         not null,");
        sql.addSql("      primary key(USR_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_PRI_KAKUNIN");
        sql.addSql("(");
        sql.addSql("      USR_SID               integer           not null,");
        sql.addSql("      NPK_DFT_USR           integer           not null,");
        sql.addSql("      NPK_AUID              integer                   ,");
        sql.addSql("      NPK_ADATE             timestamp         not null,");
        sql.addSql("      NPK_EUID              integer                   ,");
        sql.addSql("      NPK_EDATE             timestamp         not null,");
        sql.addSql("      primary key (USR_SID, NPK_DFT_USR)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_PROCESS");
        sql.addSql("(");
        sql.addSql("      NGP_SID               integer           not null,");
        sql.addSql("      NGY_SID               integer           not null,");
        sql.addSql("      NGP_CODE              varchar(8)        not null,");
        sql.addSql("      NGP_NAME              varchar(50)       not null,");
        sql.addSql("      NGP_ACCOUNT           varchar(1000)             ,");
        sql.addSql("      NGP_SORT              integer                   ,");
        sql.addSql("      NGP_AUID              integer                   ,");
        sql.addSql("      NGP_ADATE             timestamp                 ,");
        sql.addSql("      NGP_EUID              integer                   ,");
        sql.addSql("      NGP_EDATE             timestamp                 ,");
        sql.addSql("      primary key (NGP_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_SANKA");
        sql.addSql("(");
        sql.addSql("      NSN_SID               integer           not null,");
        sql.addSql("      NIP_SID               integer           not null,");
        sql.addSql("      USR_SID               integer           not null,");
        sql.addSql("      NTA_AUID              integer                   ,");
        sql.addSql("      NTA_ADATE             timestamp         not null,");
        sql.addSql("      NTA_EUID              integer                   ,");
        sql.addSql("      NTA_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NSN_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_SHOHIN");
        sql.addSql("(");
        sql.addSql("      NHN_SID               integer           not null,");
        sql.addSql("      NHN_CODE              varchar(13)       not null,");
        sql.addSql("      NHN_NAME              varchar(50)       not null,");
        sql.addSql("      NHN_PRICE_SALE        integer                   ,");
        sql.addSql("      NHN_PRICE_COST        integer                   ,");
        sql.addSql("      NHN_HOSOKU            varchar(1000)             ,");
        sql.addSql("      NHN_AUID              integer                   ,");
        sql.addSql("      NHN_ADATE             timestamp                 ,");
        sql.addSql("      NHN_EUID              integer                   ,");
        sql.addSql("      NHN_EDATE             timestamp                 ,");
        sql.addSql("      primary key (NHN_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_SML_MEMBER (");
        sql.addSql("   NSM_USR_SID integer not null,");
        sql.addSql("   GRP_SID     integer not null,");
        sql.addSql("   USR_SID     integer not null,");
        sql.addSql("   NSM_GRP     integer not null default 0,");
        sql.addSql("   primary key (NSM_USR_SID,GRP_SID,USR_SID,NSM_GRP)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TANTO");
        sql.addSql("(");
        sql.addSql("      NTA_SID               integer           not null,");
        sql.addSql("      NIP_SID               integer           not null,");
        sql.addSql("      ADR_SID               integer           not null,");
        sql.addSql("      NTA_AUID              integer                   ,");
        sql.addSql("      NTA_ADATE             timestamp         not null,");
        sql.addSql("      NTA_EUID              integer                   ,");
        sql.addSql("      NTA_EDATE             timestamp         not null,");
        sql.addSql("      primary key (NTA_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_KTHOUHOU_SORT (");
        sql.addSql("NKH_SID      integer not null,");
        sql.addSql("NHS_SORT     integer not null,");
        sql.addSql("primary key (NKH_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_CONTACT_SORT (");
        sql.addSql("  NCN_SID      integer not null,");
        sql.addSql("  NCS_SORT     integer not null,");
        sql.addSql("  primary key (NCN_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_GYOMU_SORT (");
        sql.addSql("  NGY_SID      integer not null,");
        sql.addSql("  NGS_SORT     integer not null,");
        sql.addSql("  primary key (NGY_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_PROCESS_SORT (");
        sql.addSql("  NGP_SID      integer not null,");
        sql.addSql("  NGY_SID      integer not null,");
        sql.addSql("  NPS_SORT     integer not null,");
        sql.addSql("  primary key (NGP_SID, NGY_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TARGET (");
        sql.addSql("  NTG_SID       integer           not null,");
        sql.addSql("  NTG_NAME      varchar(50)       not null,");
        sql.addSql("  NTG_UNIT      varchar(50)       not null,");
        sql.addSql("  NTG_DEF       bigint            not null,");
        sql.addSql("  NTG_DETAIL    varchar(1000)             ,");
        sql.addSql("  NTG_AUID      integer                   ,");
        sql.addSql("  NTG_ADATE     timestamp                 ,");
        sql.addSql("  NTG_EUID      integer                   ,");
        sql.addSql("  NTG_EDATE     timestamp                 ,");
        sql.addSql("  primary key (NTG_SID)                    ");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TARGET_SORT (");
        sql.addSql("  NTG_SID       integer           not null,");
        sql.addSql("  NTS_SORT      integer           not null,");
        sql.addSql("  primary key (NTG_SID)                    ");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TEMPLATE (");
        sql.addSql("  NTT_SID       integer           not null,");
        sql.addSql("  NTT_NAME      varchar(50)       not null,");
        sql.addSql("  NTT_ANKEN     integer           not null,");
        sql.addSql("  NTT_COMP      integer           not null,");
        sql.addSql("  NTT_KATUDO    integer           not null,");
        sql.addSql("  NTT_MIKOMI    integer           not null,");
        sql.addSql("  NTT_TEMP      integer           not null,");
        sql.addSql("  NTT_ACTION    integer           not null,");
        sql.addSql("  NTT_DETAIL    varchar(1000)             ,");
        sql.addSql("  NTT_AUID      integer                   ,");
        sql.addSql("  NTT_ADATE     timestamp                 ,");
        sql.addSql("  NTT_EUID      integer                   ,");
        sql.addSql("  NTT_EDATE     timestamp                 ,");
        sql.addSql("  primary key (NTT_SID)                    ");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TEMPLATE_SORT (");
        sql.addSql("  NTT_SID       integer           not null,");
        sql.addSql("  NPS_SORT      integer           not null,");
        sql.addSql("  primary key (NTT_SID)                    ");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("insert into NTP_TEMPLATE(");
        sql.addSql("            NTT_SID,");
        sql.addSql("            NTT_NAME,");
        sql.addSql("            NTT_ANKEN,");
        sql.addSql("            NTT_COMP,");
        sql.addSql("            NTT_KATUDO,");
        sql.addSql("            NTT_MIKOMI,");
        sql.addSql("            NTT_TEMP,");
        sql.addSql("            NTT_DETAIL,");
        sql.addSql("            NTT_ACTION,");
        sql.addSql("            NTT_AUID,");
        sql.addSql("            NTT_ADATE,");
        sql.addSql("            NTT_EUID,");
        sql.addSql("            NTT_EDATE)");
        sql.addSql("        values");
        sql.addSql("           (1,");
        sql.addSql("           '日報テンプレート',");
        sql.addSql("            0,");
        sql.addSql("            0,");
        sql.addSql("            0,");
        sql.addSql("            0,");
        sql.addSql("            0,");
        sql.addSql("           '',");
        sql.addSql("            0,");
        sql.addSql("            0,");
        sql.addSql("            current_timestamp,");
        sql.addSql("            0,");
        sql.addSql("            current_timestamp); ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("insert into CMN_SAIBAN(");
        sql.addSql("            SBN_SID,");
        sql.addSql("            SBN_SID_SUB,");
        sql.addSql("            SBN_NUMBER,");
        sql.addSql("            SBN_STRING,");
        sql.addSql("            SBN_AID,");
        sql.addSql("            SBN_ADATE,");
        sql.addSql("            SBN_EID,");
        sql.addSql("            SBN_EDATE)");
        sql.addSql("       values");
        sql.addSql("          ('nippou',");
        sql.addSql("           'template',");
        sql.addSql("            1,");
        sql.addSql("           'template',");
        sql.addSql("            0,");
        sql.addSql("            current_timestamp,");
        sql.addSql("            0,");
        sql.addSql("            current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("insert into NTP_TEMPLATE_SORT(NTT_SID, NPS_SORT) values (1,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TEMPLATE_MEMBER (");
        sql.addSql("  NTM_TMP_SID   integer           not null,");
        sql.addSql("  GRP_SID       integer           not null,");
        sql.addSql("  USR_SID       integer           not null,");
        sql.addSql("  primary key (NTM_TMP_SID,GRP_SID,USR_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_TEMPLATE_TARGET (");
        sql.addSql("  NTT_SID       integer           not null,");
        sql.addSql("  NTG_SID       integer           not null,");
        sql.addSql("  primary key (NTT_SID, NTG_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_PRI_TARGET (");
        sql.addSql("  NTG_SID       integer           not null,");
        sql.addSql("  USR_SID       integer           not null,");
        sql.addSql("  NPG_YEAR      integer           not null,");
        sql.addSql("  NPG_MONTH     integer           not null,");
        sql.addSql("  NPG_DATE      timestamp         not null,");
        sql.addSql("  NPG_TARGET    bigint            not null,");
        sql.addSql("  NPG_RECORD    bigint            not null,");
        sql.addSql("  NPG_AUID      integer                   ,");
        sql.addSql("  NPG_ADATE     timestamp                 ,");
        sql.addSql("  NPG_EUID      integer                   ,");
        sql.addSql("  NPG_EDATE     timestamp                 ,");
        sql.addSql("  primary key (NTG_SID, USR_SID, NPG_YEAR, NPG_MONTH)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table NTP_CHECK (");
        sql.addSql("  NTP_SID        integer      not null,");
        sql.addSql("  USR_SID        integer      not null,");
        sql.addSql("  primary key (NTP_SID, USR_SID)");
        sql.addSql(");");
        sqlList.add(sql);


        //業務マスタ
        sql = new SqlBuffer();
        sql.addSql(" insert into  NTP_GYOMU(");
        sql.addSql("  NGY_SID, NGY_CODE, NGY_NAME, NGY_AUID, NGY_ADATE, NGY_EUID, NGY_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,'1','営業',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into  NTP_GYOMU(");
        sql.addSql("  NGY_SID, NGY_CODE, NGY_NAME, NGY_AUID, NGY_ADATE, NGY_EUID, NGY_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,'2','開発',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        //業務ソート
        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_GYOMU_SORT(");
        sql.addSql("  NGY_SID,NGS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_GYOMU_SORT(");
        sql.addSql("  NGY_SID,NGS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,1);");
        sqlList.add(sql);


        //プロセス一覧
        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + "NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1, 1, '1','アプローチ','',1,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2, 1, '2','ヒアリング','',2,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3, 1, '3','企画提案','',3,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4, 1, '4','プレゼン','',4,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5, 1, '5','クロージング','',5,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6, 1, '6','アフターフォロー','',6,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (7, 2, '7','調査','',1,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (8, 2, '8','資料作成','',2,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (9, 2, '9','製造・開発','',3,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS(");
        sql.addSql("  NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT,"
                + " NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (10,2,'10','テスト','',4,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        //プロセスソート
        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,1,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql(" (2,1,1);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("   (3,1,2);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,1,3);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,1,4);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,1,5);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (7,2,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (8,2,1);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (9,2,2);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_PROCESS_SORT(");
        sql.addSql("  NGP_SID, NGY_SID, NPS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (10,2,3);");
        sqlList.add(sql);

        //活動分類一覧
        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,'1','要望対応',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,'2','問合せ対応',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3,'3','クレーム対応',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,'4','会議',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,'5','資料作成',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,'6','セミナー',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_KTBUNRUI(");
        sql.addSql("  NKB_SID, NKB_CODE, NKB_NAME, NKB_TIEUP_KBN,"
                + " NKB_AUID, NKB_ADATE, NKB_EUID, NKB_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (7,'7','引継ぎ',0,0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);


        //活動分類ソート
        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,1);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3,2);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,3);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,4);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,5);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTBUNRUI_SORT(");
        sql.addSql("  NKB_SID, NKS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (7,6);");
        sqlList.add(sql);


        //活動方法一覧
        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,'1','訪問',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,'2','来社',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3,'3','電話',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,'4','FAX',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,'5','メール',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,'6','郵送',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU(");
        sql.addSql("  NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (7,'7','社内作業',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        //活動方法ソート
        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,1);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3,2);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,3);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,4);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,5);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_KTHOUHOU_SORT(");
        sql.addSql("  NKH_SID, NHS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (7,6);");
        sqlList.add(sql);


        //顧客源泉一覧
        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT(");
        sql.addSql("  NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,'1','既存',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT(");
        sql.addSql("  NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,'2','紹介',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT(");
        sql.addSql("  NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3,'3','セミナー',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT(");
        sql.addSql("  NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,'4','広告',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT(");
        sql.addSql("  NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,'5','ホームページ',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT(");
        sql.addSql("  NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,'6','SNS',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        //顧客源泉ソート
        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT_SORT(");
        sql.addSql("  NCN_SID, NCS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (1,0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT_SORT(");
        sql.addSql("  NCN_SID, NCS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (2,1);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT_SORT(");
        sql.addSql("  NCN_SID, NCS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (3,2);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT_SORT(");
        sql.addSql("  NCN_SID, NCS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (4,3);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT_SORT(");
        sql.addSql("  NCN_SID, NCS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (5,4);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into NTP_CONTACT_SORT(");
        sql.addSql("  NCN_SID, NCS_SORT");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  (6,5);");
        sqlList.add(sql);

       //採番マスタ
        sql = new SqlBuffer();
        sql.addSql("  insert into CMN_SAIBAN(");
        sql.addSql("  SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING,"
                + " SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  ('nippou','gyomu',2,'gyomu',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into CMN_SAIBAN(");
        sql.addSql("  SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING,"
                + " SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  ('nippou','process',10,'process',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into CMN_SAIBAN(");
        sql.addSql("  SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING,"
                + " SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  ('nippou','ktbunrui',7,'ktbunrui',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into CMN_SAIBAN(");
        sql.addSql("  SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING,"
                + " SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  ('nippou','kthouhou',7,'kthouhou',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  insert into CMN_SAIBAN(");
        sql.addSql("  SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING,"
                + " SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE");
        sql.addSql("  )");
        sql.addSql(" values");
        sql.addSql("  ('nippou','contact',6,'contact',0,current_timestamp,0,current_timestamp);");
        sqlList.add(sql);

        //掲示板個人設定 添付画像表示区分
        sql = new SqlBuffer();
        sql.addSql("  alter table BBS_USER ADD BUR_TEMP_IMAGE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール個人設定 添付画像表示区分
        sql = new SqlBuffer();
        sql.addSql("  alter table SML_USER ADD SML_TEMP_DSP integer not null default 0;");
        sqlList.add(sql);

        //ショートメール本文の最大 2000へ変更
        sql = new SqlBuffer();
        sql.addSql("  alter table SML_SMEIS alter SMS_BODY type VARCHAR(2000);");
        sqlList.add(sql);

        //ショートメール本文（草案）の最大 2000へ変更
        sql = new SqlBuffer();
        sql.addSql("  alter table SML_WMEIS alter SMW_BODY type VARCHAR(2000);");
        sqlList.add(sql);

        return sqlList;
    }
}