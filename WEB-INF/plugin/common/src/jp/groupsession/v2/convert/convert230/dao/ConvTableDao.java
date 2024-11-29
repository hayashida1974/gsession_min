package jp.groupsession.v2.convert.convert230.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v2.3.0へコンバートする際に使用する
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
            List<SqlBuffer> sqlList = __createSQL(con);

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
     * @param con コネクション
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private List<SqlBuffer> __createSQL(Connection con) throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();
        SqlBuffer sql = new SqlBuffer();

        //テーブル作成SQL
        sqlList = __createSQLCreate(con, sqlList, sql);

        //フィールド追加SQL
        sqlList = __createSQLAdd(sqlList, sql);

        //データ追加SQL
        sqlList = __createSQLInsert(sqlList, sql);

        return sqlList;
    }

    /**
     * <br>[機  能] SQLを生成する(テーブル作成)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sqlList SQLリスト
     * @param sql SqlBuffer
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private ArrayList<SqlBuffer> __createSQLCreate(
            Connection con, ArrayList<SqlBuffer> sqlList, SqlBuffer sql)
    throws SQLException {

        //アドレス帳
        sqlList = __createAcoSini(con, sqlList);

        //ブックマーク
        sql = new SqlBuffer();
        sql.addSql("create table BMK_LABEL");
        sql.addSql("(");
        sql.addSql("  BLB_SID     integer     not null,");
        sql.addSql("  BLB_KBN     integer     not null,");
        sql.addSql("  USR_SID     integer     not null,");
        sql.addSql("  GRP_SID     integer     not null,");
        sql.addSql("  BLB_NAME    varchar(20) not null,");
        sql.addSql("  BLB_AUID    integer     not null,");
        sql.addSql("  BLB_ADATE   timestamp   not null,");
        sql.addSql("  BLB_EUID    integer     not null,");
        sql.addSql("  BLB_EDATE   timestamp   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  BLB_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_GROUP_EDIT");
        sql.addSql("(");
        sql.addSql("  GRP_SID     integer     not null,");
        sql.addSql("  BGE_GRP_SID integer     not null,");
        sql.addSql("  BGE_USR_SID integer     not null,");
        sql.addSql("  BGE_AUID    integer     not null,");
        sql.addSql("  BGE_ADATE   timestamp   not null,");
        sql.addSql("  BGE_EUID    integer     not null,");
        sql.addSql("  BGE_EDATE   timestamp   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  GRP_SID,");
        sql.addSql("  BGE_GRP_SID,");
        sql.addSql("  BGE_USR_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_GCONF");
        sql.addSql("(");
        sql.addSql("  GRP_SID     integer     not null,");
        sql.addSql("  BGC_EDIT    integer     not null,");
        sql.addSql("  BGC_AUID    integer     not null,");
        sql.addSql("  BGC_ADATE   timestamp   not null,");
        sql.addSql("  BGC_EUID    integer     not null,");
        sql.addSql("  BGC_EDATE   timestamp   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  GRP_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_BOOKMARK");
        sql.addSql("(");
        sql.addSql("  BMK_SID     integer         not null,");
        sql.addSql("  BMK_KBN     integer         not null,");
        sql.addSql("  USR_SID     integer         not null,");
        sql.addSql("  GRP_SID     integer,");
        sql.addSql("  BMU_SID     integer,");
        sql.addSql("  BMK_TITLE   varchar(150)    not null,");
        sql.addSql("  BMK_CMT     varchar(1000)   not null,");
        sql.addSql("  BMK_SCORE   integer         not null,");
        sql.addSql("  BMK_PUBLIC  integer         not null,");
        sql.addSql("  BMK_MAIN    integer         not null,");
        sql.addSql("  BMK_SORT    integer         not null,");
        sql.addSql("  BMK_AUID    integer         not null,");
        sql.addSql("  BMK_ADATE   timestamp       not null,");
        sql.addSql("  BMK_EUID    integer         not null,");
        sql.addSql("  BMK_EDATE   timestamp       not null,");
        sql.addSql("  primary key (");
        sql.addSql("  BMK_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_BELONG_LABEL");
        sql.addSql("(");
        sql.addSql("  BMK_SID     integer     not null,");
        sql.addSql("  BLB_SID     integer     not null,");
        sql.addSql("  BBL_AUID    integer     not null,");
        sql.addSql("  BBL_ADATE   timestamp   not null,");
        sql.addSql("  BBL_EUID    integer     not null,");
        sql.addSql("  BBL_EDATE   timestamp   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  BMK_SID,");
        sql.addSql("  BLB_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_ACONF");
        sql.addSql("(");
        sql.addSql("  BAC_PUB_EDIT    integer     not null,");
        sql.addSql("  BAC_GRP_EDIT    integer     not null,");
        sql.addSql("  BAC_AUID        integer     not null,");
        sql.addSql("  BAC_ADATE       timestamp   not null,");
        sql.addSql("  BAC_EUID        integer     not null,");
        sql.addSql("  BAC_EDATE       timestamp   not null");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_PUBLIC_EDIT");
        sql.addSql("(");
        sql.addSql("  GRP_SID     integer     not null,");
        sql.addSql("  USR_SID     integer     not null,");
        sql.addSql("  BPE_AUID    integer     not null,");
        sql.addSql("  BPE_ADATE   timestamp   not null,");
        sql.addSql("  BPE_EUID    integer     not null,");
        sql.addSql("  BPE_EDATE   timestamp   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  GRP_SID,");
        sql.addSql("  USR_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_SCHEME");
        sql.addSql("(");
        sql.addSql("  BSC_SCHEME    varchar(20)   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  BSC_SCHEME");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_UCONF");
        sql.addSql("(");
        sql.addSql("  USR_SID         integer     not null,");
        sql.addSql("  BUC_COUNT       integer     not null,");
        sql.addSql("  BUC_MAIN_MY     integer     not null,");
        sql.addSql("  BUC_MAIN_NEW    integer     not null,");
        sql.addSql("  BUC_AUID        integer     not null,");
        sql.addSql("  BUC_ADATE       timestamp   not null,");
        sql.addSql("  BUC_EUID        integer     not null,");
        sql.addSql("  BUC_EDATE       timestamp   not null,");
        sql.addSql("  primary key (");
        sql.addSql("  USR_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table BMK_URL");
        sql.addSql("(");
        sql.addSql("  BMU_SID         integer       not null,");
        sql.addSql("  BMU_URL         varchar(1000) not null,");
        sql.addSql("  BMU_TITLE       varchar(150)  not null,");
        sql.addSql("  BMU_PUB_DATE    timestamp,");
        sql.addSql("  BMU_AUID        integer       not null,");
        sql.addSql("  BMU_ADATE       timestamp     not null,");
        sql.addSql("  BMU_EUID        integer       not null,");
        sql.addSql("  BMU_EDATE       timestamp     not null,");
        sql.addSql("  primary key (");
        sql.addSql("  BMU_SID");
        sql.addSql("  )");
        sql.addSql(")");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] SQLを生成する(フィールド追加)
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList SQLリスト
     * @param sql SqlBuffer
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private ArrayList<SqlBuffer> __createSQLAdd(ArrayList<SqlBuffer> sqlList, SqlBuffer sql)
    throws SQLException {

        sql = new SqlBuffer();

        //共通
        sql.addSql(" alter table CMN_BINF alter column BIN_FILE_EXTENSION set null;");
        sqlList.add(sql);

        //タイムカード
        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_TCDATA add TCD_STRIKE_INTIME time default null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_TCDATA add TCD_STRIKE_OUTTIME time default null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add TAC_LOCK_STRIKE integer default 0 not null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add TAC_LOCK_BIKO integer default 0 not null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add TAC_LOCK_LATE integer default 0 not null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_ADM_CONF add TAC_LOCK_HOLIDAY integer default 0 not null");
        sqlList.add(sql);

        //ショートメール
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS add SMJ_FWKBN integer default 0");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] SQLを生成する(insert)
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList SQLリスト
     * @param sql SqlBuffer
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private ArrayList<SqlBuffer> __createSQLInsert(ArrayList<SqlBuffer> sqlList, SqlBuffer sql)
    throws SQLException {

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'aaa'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'aaas'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'acap'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'cap'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'cid'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'crid'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'data'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'dav'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'dict'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'dns'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'fax'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'file'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'ftp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'go'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'gopher'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'h323'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'http'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'https'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'iax'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'icap'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'im'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'imap'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'info'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'ipp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'iris'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'iris.beep'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'iris.xpc'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'iris.xpcs'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'iris.lwz'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'ldap'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'mailto'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'mid'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'modem'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'msrp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'msrps'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'mtqp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'mupdate'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'news'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'nfs'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'nntp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'opaquelocktoken'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'pop'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'pres'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'rtsp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'service'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'shttp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'sieve'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'sip'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'sips'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'snmp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'soap.beep'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'soap.beeps'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'tag'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'tel'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'telnet'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'tftp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'thismessage'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'tip'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'tv'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'urn'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'vemmi'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'xmlrpc.beep'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'xmlrpc.beeps'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'xmpp'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'z39.50r'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'z39.50s'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'afs'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'dtn'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'mailserver'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'pack'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'tn3270'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'prospero'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'snews'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'videotex'");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql("   BMK_SCHEME(");
        sql.addSql("   BSC_SCHEME");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   'wais'");
        sql.addSql(" )");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] アドレス帳会社名頭文字データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネション
     * @param sqlList SQLリスト
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private ArrayList<SqlBuffer> __createAcoSini(
            Connection con, ArrayList<SqlBuffer> sqlList)
            throws SQLException {

        SqlBuffer sql = new SqlBuffer();
        SqlBuffer retSql = new SqlBuffer();
        retSql.addSql(" alter table ADR_COMPANY add ACO_SINI varchar(3) default 'あ' not null");
        sqlList.add(retSql);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String initinal = null;

        try {
            //SQL文
            sql.addSql(" select ");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ACO_NAME_KN");
            sql.addSql(" from ");
            sql.addSql("   ADR_COMPANY");

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {

                retSql = new SqlBuffer();
                initinal = StringUtilKana.getInitKanaChar(rs.getString("ACO_NAME_KN"));
                retSql.addSql(" update");
                retSql.addSql("   ADR_COMPANY");
                retSql.addSql(" set ");
                retSql.addSql("   ACO_SINI='" + initinal + "'");
                retSql.addSql(" where ");
                retSql.addSql("   ACO_SID=" + rs.getInt("ACO_SID"));
                sqlList.add(retSql);

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sqlList;
    }


}