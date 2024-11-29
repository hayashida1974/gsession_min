package jp.groupsession.v2.convert.convert310.dao;

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
 * <br>[解  説] v3.1.0へコンバートする際に使用する
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

        //共通 インフォメーション 新規追加

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_INFO_MSG");
        sql.addSql(" (");
        sql.addSql(" IMS_SID integer not null,");
        sql.addSql(" IMS_MSG varchar(450) ,");
        sql.addSql(" IMS_VALUE varchar(3000) ,");
        sql.addSql(" IMS_FR_DATE timestamp not null,");
        sql.addSql(" IMS_TO_DATE timestamp not null,");
        sql.addSql(" IMS_JTKB integer not null,");
        sql.addSql(" IMS_KBN integer not null,");
        sql.addSql(" IMS_DWEEK1 integer ,");
        sql.addSql(" IMS_DWEEK2 integer ,");
        sql.addSql(" IMS_DWEEK3 integer ,");
        sql.addSql(" IMS_DWEEK4 integer ,");
        sql.addSql(" IMS_DWEEK5 integer ,");
        sql.addSql(" IMS_DWEEK6 integer ,");
        sql.addSql(" IMS_DWEEK7 integer ,");
        sql.addSql(" IMS_DAY integer ,");
        sql.addSql(" IMS_WEEK integer ,");
        sql.addSql(" IMS_AUID integer not null,");
        sql.addSql(" IMS_ADATE timestamp not null,");
        sql.addSql(" IMS_EUID integer not null,");
        sql.addSql(" IMS_EDATE timestamp not null,");
        sql.addSql(" IMS_HOLKBN integer not null,");
        sql.addSql(" primary key (IMS_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_INFO_TAG");
        sql.addSql(" (");
        sql.addSql(" IMS_SID integer not null,");
        sql.addSql(" USR_SID integer not null,");
        sql.addSql(" GRP_SID integer not null,");
        sql.addSql(" primary key (IMS_SID,USR_SID,GRP_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_INFO_USER");
        sql.addSql(" (");
        sql.addSql(" USR_SID integer not null,");
        sql.addSql(" GRP_SID integer not null,");
        sql.addSql(" primary key (USR_SID,GRP_SID)");
        sql.addSql(" ); ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_INFO_BIN");
        sql.addSql(" (");
        sql.addSql("   IMS_SID  integer   not null,");
        sql.addSql("   BIN_SID  integer   not null,");
        sql.addSql("   primary key (IMS_SID,BIN_SID)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PLUGIN_CONTROL");
        sql.addSql(" (");
        sql.addSql(" PCT_PID varchar(10) not null,");
        sql.addSql(" PCT_KBN integer not null,");
        sql.addSql(" primary key (PCT_PID)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PLUGIN_CONTROL_MEMBER");
        sql.addSql(" (");
        sql.addSql(" PCT_PID varchar(10) not null,");
        sql.addSql(" GRP_SID integer not null,");
        sql.addSql(" USR_SID integer not null,");
        sql.addSql(" primary key (PCT_PID, GRP_SID, USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LABEL_USR_CATEGORY");
        sql.addSql("(");
        sql.addSql(" LUC_SID   integer not null ,");
        sql.addSql(" LUC_NAME  varchar(20) not null,");
        sql.addSql(" LUC_BIKO  varchar(300),");
        sql.addSql(" LUC_SORT  integer not null,");
        sql.addSql(" LUC_AUID  integer not null,");
        sql.addSql(" LUC_ADATE timestamp not null,");
        sql.addSql(" LUC_EUID  integer not null,");
        sql.addSql(" LUC_EDATE timestamp not null,");
        sql.addSql(" primary key(LUC_SID));");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table CMN_LABEL_USR_CONF");
        sql.addSql("(");
        sql.addSql(" LUF_EDIT   integer   not null,");
        sql.addSql(" LUF_SET    integer   not null,");
        sql.addSql(" LUF_AUID   integer   not null,");
        sql.addSql(" LUF_ADATE  timestamp not null,");
        sql.addSql(" LUF_EUID   integer   not null,");
        sql.addSql(" LUF_EDATE  timestamp not null");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LABEL_USR");
        sql.addSql(" (");
        sql.addSql(" LAB_SID   integer not null,");
        sql.addSql(" LUC_SID   integer not null,");
        sql.addSql(" LAB_NAME  varchar(30) not null,");
        sql.addSql(" LAB_BIKO  varchar(300),");
        sql.addSql(" LAB_AUID  integer not null,");
        sql.addSql(" LAB_ADATE timestamp not null,");
        sql.addSql(" LAB_EUID  integer not null,");
        sql.addSql(" LAB_EDATE timestamp not null,");
        sql.addSql(" LAB_SORT  integer not null,");
        sql.addSql("primary key(LAB_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USRM_LABEL");
        sql.addSql(" (");
        sql.addSql(" USR_SID    integer not null,");
        sql.addSql(" LAB_SID    integer not null,");
        sql.addSql(" USL_AUID   integer not null,");
        sql.addSql(" USL_ADATE  timestamp not null,");
        sql.addSql(" USL_EUID   integer not null,");
        sql.addSql(" USL_EDATE  timestamp not null,");
        sql.addSql(" primary key(USR_SID, LAB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_LABEL");
        sql.addSql(" alter column ");
        sql.addSql("   ALB_NAME varchar(30) not null;");
        sqlList.add(sql);

        //WEBメール
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF");
        sql.addSql(" add WAD_ACCT_LOG_REGIST integer not null default 1;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_RCVSVR");
        sql.addSql(" (");
        sql.addSql("   WAC_SID            integer      not null,");
        sql.addSql("   WRS_RECEIVE_COUNT  bigint       not null,");
        sql.addSql("   WRS_RECEIVE_SIZE   bigint       not null,");
        sql.addSql("   primary key (WAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into WML_ACCOUNT_RCVSVR");
        sql.addSql(" select WAC_SID, 0, 0 from WML_ACCOUNT;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAILDATA_SORT_SEARCH");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   WSS_SORTKEY     integer      not null,");
        sql.addSql("   WSS_ORDER       integer      not null,");
        sql.addSql("   primary key (WAC_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //タイムカード個人設定
        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_PRI_CONF add TPC_KINMU_OUT integer not null default 0;");
        sqlList.add(sql);

        //ファイル管理更新通知表示件数
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_UCONF add FUC_CALL integer    not null default 10;");
        sqlList.add(sql);

        //ファイル管理マーク区分
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_CABINET add FCB_MARK integer  not null default 0;");
        sqlList.add(sql);

        //ブックマーク個人設定
        sql = new SqlBuffer();
        sql.addSql(" alter table BMK_UCONF add BUC_NEW_CNT integer not null default 3;");
        sqlList.add(sql);

        //アドレス帳ラベルのカテゴリフィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_LABEL");
        sql.addSql("  add ALC_SID integer not null default 1");
        sqlList.add(sql);

        //アドレス帳ラベルカテゴリ
        sql = new SqlBuffer();
        sql.addSql(" create table ADR_LABEL_CATEGORY");
        sql.addSql("  (");
        sql.addSql("   ALC_SID     integer       not null,");
        sql.addSql("   ALC_NAME    varchar(20)   not null,");
        sql.addSql("   ALC_BIKO    varchar(300),");
        sql.addSql("   ALC_SORT    integer       not null,");
        sql.addSql("   ALC_AUID    integer       not null,");
        sql.addSql("   ALC_ADATE   timestamp     not null,");
        sql.addSql("   ALC_EUID    integer       not null,");
        sql.addSql("   ALC_EDATE   timestamp     not null,");
        sql.addSql("   primary key (ALC_SID))");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into ADR_LABEL_CATEGORY");
        sql.addSql("  (");
        sql.addSql("   ALC_SID,");
        sql.addSql("   ALC_NAME,");
        sql.addSql("   ALC_BIKO,");
        sql.addSql("   ALC_SORT,");
        sql.addSql("   ALC_AUID,");
        sql.addSql("   ALC_ADATE,");
        sql.addSql("   ALC_EUID,");
        sql.addSql("   ALC_EDATE");
        sql.addSql(")");
        sql.addSql(" values (1, '未設定', null, 1, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP)");
        sqlList.add(sql);

        //採番マスタアップデート
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
        sql.addSql("  'category',");
        sql.addSql("  1,");
        sql.addSql("  'category',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        //掲示板アイコン
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BIN_SID integer not null default 0;");
        sqlList.add(sql);

        return sqlList;
    }
}