package jp.groupsession.v2.convert.convert350.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.DataSourceUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] WEBメールのコンバートを行う
 * <br>[解  説] v3.5.0以前のバージョンからのコンバートで使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvWebmail350 extends AbstractDao {

    /** 1度に更新するメール情報の件数 */
    public static final int BUFFER = 100;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvWebmail350.class);

    /**
     * <p>Default Constructor
     */
    public ConvWebmail350() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvWebmail350(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] WEBメールのコンバートを実行
     * <br>[解  説]
     * <br>[備  考]
     * @param gscontext GSContext
     * @throws SQLException 例外
     */
    public void convert(GSContext gscontext) throws SQLException {

        log__.warn("-- v3.5.0へのコンバート - WEBメール 開始 --");

        DataSource ds = null;
        try {
            ds = getDataSource(gscontext);
            reConnect(ds);

            //コンバートが実施ずみかを判定する
            if (__isConver350()) {
                log__.warn("WEBメールのコンバートは実施ずみです。");
                log__.warn("処理を終了します。");
                log__.warn("-- v3.5.0へのコンバート - WEBメール 終了 --");
                return;
            }

            //アカウントSIDフィールドの追加
            __createConvertTable();
            getCon().commit();
            reConnect(ds);

            String[] changeTableName = {"WML_MAILDATA",
                                    "WML_MAIL_BODY",
                                    "WML_SENDADDRESS",
                                    "WML_LABEL_RELATION",
                                    "WML_MAIL_LOG",
                                    "WML_MAIL_LOG_SEND",
                                    "WML_HEADER_DATA"
            };

            //メール情報の総数を取得する
            long mailCount = __getMailCount();

            if (mailCount > 0) {
                //アカウントSIDの一覧を取得する
                List<Integer> wacSidList = __getWacSidList();

                long compCount = 0;
                for (int wacSid : wacSidList) {

                    //メール情報に関連するアカウントのSIDを設定する
                    List<Long> dirSidList = __getDirectoryList(wacSid);
                    List<Long> mailList = __getMailAccountList(wacSid, dirSidList);
                    while (!mailList.isEmpty()) {
                        boolean commit = false;
                        try {

                            for (String tableName : changeTableName) {
                                __updateWacSid(wacSid, mailList, tableName);
                            }
                            getCon().commit();
                            reConnect(ds);

                            commit = true;

                        } finally {
                            if (!commit) {
                                getCon().rollback();
                            }
                        }

                        compCount += mailList.size();
                        if (compCount > mailCount) {
                            compCount = mailCount;
                        }
                        log__.warn("WEBメール コンバート 進行状況 : " + compCount + " / " + mailCount);
                        mailList = null;
                        mailList = __getMailAccountList(wacSid, dirSidList);
                    }
                }
            }

            //コンバートテーブルを置き換える
            reConnect(ds);
            boolean commit = false;
            try {
                for (String tableName : changeTableName) {
                    __changeConvertTable(tableName);
                }
                getCon().commit();
                commit = true;
            } catch (Exception e) {
                throw new SQLException("コンバートテーブルの置換に失敗", e);
            } finally {
                if (!commit) {
                    getCon().rollback();
                }
            }

            //インデックスを作成する
            reConnect(ds);
            __indexReBuild();

            getCon().commit();
        } catch (SAXException e) {
            log__.error("WEBメール コンバートに使用するDataSourceの取得に失敗", e);
            throw new SQLException("WEBメール コンバートに使用するDataSourceの取得に失敗");
        } catch (IOException e) {
            log__.error("WEBメール コンバートに使用するDataSourceの取得に失敗", e);
            throw new SQLException("WEBメール コンバートに使用するDataSourceの取得に失敗");
        } catch (IOToolsException e) {
            log__.error("WEBメール コンバートに使用するDataSourceの取得に失敗", e);
            throw new SQLException("WEBメール コンバートに使用するDataSourceの取得に失敗");
        } finally {
            JDBCUtil.closeConnection(getCon());
            ds = null;
        }

        log__.warn("-- v3.5.0へのコンバート - WEBメール 終了 --");
    }

    /**
     * <br>[機  能] コンバート用テーブルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __createConvertTable() throws SQLException {

        List<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" create table WML_HEADER_DATA_CONVERT");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint         not null,");
        sql.addSql("   WMH_NUM      integer        not null,");
        sql.addSql("   WMH_TYPE     varchar(200)   not null,");
        sql.addSql("   WMH_CONTENT  varchar(1000)  not null,");
        sql.addSql("   WAC_SID      integer        not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WMH_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_LABEL_RELATION_CONVERT");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint       not null,");
        sql.addSql("   WLB_SID      integer      not null,");
        sql.addSql("   WAC_SID      integer      not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_BODY_CONVERT");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint   not null,");
        sql.addSql("   WMB_BODY     text,");
        sql.addSql("   WMB_CHARSET  varchar(50),");
        sql.addSql("   WAC_SID      integer  not null,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_LOG_SEND_CONVERT");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint        not null,");
        sql.addSql("   WLS_NUM      integer       not null,");
        sql.addSql("   WLS_TYPE     integer       not null,");
        sql.addSql("   WLS_ADDRESS  varchar(768)  not null,");
        sql.addSql("   WAC_SID      integer       not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WLS_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_LOG_CONVERT");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM   bigint          not null,");
        sql.addSql("   WLG_TITLE     varchar(1000)   not null,");
        sql.addSql("   WLG_DATE      timestamp,");
        sql.addSql("   WLG_FROM      varchar(256),");
        sql.addSql("   WLG_TEMPFLG   integer         not null,");
        sql.addSql("   WLG_MAILTYPE  integer         not null,");
        sql.addSql("   WAC_SID       integer         not null,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAILDATA_CONVERT");
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
        sql.addSql("   WAC_SID      integer         not null,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_SENDADDRESS_CONVERT");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM  bigint        not null,");
        sql.addSql("   WSA_NUM      integer       not null,");
        sql.addSql("   WSA_TYPE     integer       not null,");
        sql.addSql("   WSA_ADDRESS  varchar(768)  not null,");
        sql.addSql("   WAC_SID      integer       not null,");
        sql.addSql("   primary key(WMD_MAILNUM, WSA_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        Statement stmt = null;

        for (SqlBuffer alterSql : sqlList) {
            try {
                log__.info(alterSql.toLogString());
                stmt = getCon().createStatement();
                stmt.executeUpdate(alterSql.toSqlString());
            } catch (Exception e) {
                log__.warn("既に登録されています。", e);
                getCon().rollback();
            } finally {
                JDBCUtil.closeStatement(stmt);
                stmt = null;
            }
        }
    }

    /**
     * <br>[機  能] バージョン3.5.0以降へコンバート済みかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true: コンバート済み false:コンバート未設定
     * @throws SQLException SQL実行時例外
     */
    private boolean __isConver350() throws SQLException {

        Statement stmt = null;
        boolean result = false;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(WAC_SID) from WML_MAILDATA");
            sql.addSql(" where WAC_SID = 0");

            log__.info(sql.toLogString());

            try {
                stmt = getCon().createStatement();
                stmt.execute(sql.toSqlString());
                result = true;
            } catch (SQLException e) {
            }

        } finally {
            JDBCUtil.closeStatement(stmt);
            stmt = null;
            getCon().rollback();
        }

        return result;
    }

    /**
     * <br>[機  能] メール情報の総数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メール情報の総数
     * @throws SQLException SQL実行時例外
     */
    private Long __getMailCount() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        long count = 0;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT from WML_MAILDATA");

            log__.info(sql.toLogString());
            stmt = getCon().createStatement();
            rs = stmt.executeQuery(sql.toSqlString());

            if (rs.next()) {
                count = rs.getLong("CNT");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
            rs = null;
            stmt = null;
        }

        return count;
    }

    /**
     * <br>[機  能] 指定したアカウント内のディレクトリSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return メッセージ番号一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Long> __getDirectoryList(int wacSid) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        List<Long> list = new ArrayList<Long>();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WDR_SID from WML_DIRECTORY");
            sql.addSql(" where WAC_SID = " + wacSid);

            log__.info(sql.toLogString());
            stmt = getCon().createStatement();
            rs = stmt.executeQuery(sql.toSqlString());

            while (rs.next()) {
                list.add(rs.getLong("WDR_SID"));
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
            rs = null;
            stmt = null;
        }

        return list;
    }

    /**
     * <br>[機  能] 指定したアカウント内メールのメッセージ番号一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wdrSidList ディレクトリSID
     * @return メッセージ番号一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Long> __getMailAccountList(int wacSid, List<Long> wdrSidList) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        List<Long> list = new ArrayList<Long>();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WML_MAILDATA.WMD_MAILNUM as MAILNUM from WML_MAILDATA");

            StringBuilder where = new StringBuilder(" where WML_MAILDATA.WDR_SID in (");
            for (int idx = 0; idx < wdrSidList.size(); idx++) {
                if (idx > 0) {
                    where.append(",");
                }
                where.append(String.valueOf(wdrSidList.get(idx)));
            }
            where.append(")");

            sql.addSql(where.toString());

            sql.addSql(" and not exists (");
            sql.addSql("   select 1 from WML_MAILDATA_CONVERT");
            sql.addSql("   where WML_MAILDATA_CONVERT.WAC_SID = " + wacSid);
            sql.addSql("   and WML_MAILDATA_CONVERT.WMD_MAILNUM = WML_MAILDATA.WMD_MAILNUM");
            sql.addSql(" )");

//            sql.addSql(" limit " + BUFFER + " offset 0");
            sql.setPagingValue(0, BUFFER);

            log__.info(sql.toLogString());
            stmt = getCon().createStatement();
            rs = stmt.executeQuery(sql.toSqlString());

            while (rs.next()) {
                list.add(rs.getLong("MAILNUM"));
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
            rs = null;
            stmt = null;
        }

        return list;
    }

    /**
     * <br>[機  能] アカウントSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getWacSidList() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> list = new ArrayList<Integer>();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WAC_SID from WML_ACCOUNT");
            log__.info(sql.toLogString());

            stmt = getCon().createStatement();
            rs = stmt.executeQuery(sql.toSqlString());

            while (rs.next()) {
                list.add(rs.getInt("WAC_SID"));
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
            rs = null;
            stmt = null;
        }

        return list;
    }

    /**
     * <br>[機  能] 指定したテーブルのアカウントSIDを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNumList メッセージ番号一覧
     * @param tableName テーブル名
     * @throws SQLException SQL実行時例外
     */
    private void __updateWacSid(int wacSid, List<Long> mailNumList, String tableName)
    throws SQLException {
        if (mailNumList == null || mailNumList.isEmpty()) {
            return;
        }

        Statement stmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into " + tableName + "_CONVERT");
            sql.addSql(" select *, " + wacSid + " from " + tableName);

            StringBuilder where = new StringBuilder(" where WMD_MAILNUM in (");
            for (int idx = 0; idx < mailNumList.size(); idx++) {
                if (idx > 0) {
                    where.append(",");
                }
                where.append(String.valueOf(mailNumList.get(idx)));
            }
            where.append(")");

            sql.addSql(where.toString());
            log__.info(sql.toLogString());

            stmt = getCon().createStatement();
            stmt.executeUpdate(sql.toSqlString());

        } finally {
            JDBCUtil.closeStatement(stmt);
            stmt = null;
        }
    }

    /**
     * <br>[機  能] コンバートテーブルを実際に使用するテーブルとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName テーブル名
     * @throws Exception SQL実行時例外
     */
    private void __changeConvertTable(String tableName) throws Exception {
        List<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" drop table " + tableName);
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table " + tableName + "_CONVERT"
                    + " rename to " + tableName);
        sqlList.add(sql);

        Statement stmt = null;

        for (SqlBuffer alterSql : sqlList) {
            try {
                log__.info(alterSql.toLogString());
                stmt = getCon().createStatement();
                stmt.executeUpdate(alterSql.toSqlString());
            } catch (Exception e) {
                log__.error("alter tableに失敗", e);
                throw e;
            } finally {
                JDBCUtil.closeStatement(stmt);
                stmt = null;
            }
        }
    }

    /**
     * <br>[機  能] INDEXの再設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __indexReBuild() throws SQLException {

        SqlBuffer sql = new SqlBuffer();
        List<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() == GSConst.DBTYPE_H2DB) {
            sql.addSql(" drop alias FTL_INIT;");
            sqlList.add(sql);
            sql = new SqlBuffer();
            sql.addSql(" CALL FTL_DROP_ALL();");
            sqlList.add(sql);
            sql = new SqlBuffer();
            sql.addSql(" CREATE ALIAS IF NOT EXISTS FTL_INIT FOR"
                    + "\"org.h2.fulltext.FullTextLucene.init\";");
            sqlList.add(sql);
            sql = new SqlBuffer();
            sql.addSql(" CALL FTL_INIT();");
            sqlList.add(sql);
            sql = new SqlBuffer();
            sql.addSql(" CALL FTL_CREATE_INDEX('PUBLIC', 'WML_MAIL_BODY', 'WMB_BODY');");
            sqlList.add(sql);

            sql = new SqlBuffer();
            sql.addSql(" create index WML_LABEL_RELATION_INDEX1 on WML_LABEL_RELATION(WLB_SID);");
            sqlList.add(sql);

            sql = new SqlBuffer();
            sql.addSql(" create index WML_MAIL_LOG_INDEX1 on WML_MAIL_LOG( WLG_DATE);");
            sqlList.add(sql);

            sql = new SqlBuffer();
            sql.addSql(" create index WML_MAILDATA_INDEX on WML_MAILDATA( WDR_SID);");
            sqlList.add(sql);

            sql = new SqlBuffer();
            sql.addSql(" create index WML_MAILDATA_INDEX2 on WML_MAILDATA( WMD_SDATE);");
            sqlList.add(sql);
        }

        Statement stmt = null;

        for (SqlBuffer alterSql : sqlList) {
            try {
                log__.info(alterSql.toLogString());
                stmt = getCon().createStatement();
                stmt.executeUpdate(alterSql.toSqlString());
            } catch (Exception e) {
                log__.error("Indexの再登録に失敗", e);
            } finally {
                JDBCUtil.closeStatement(stmt);
                stmt = null;
            }
        }
    }

    /**
     * <br>[機  能] DBの再接続を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ds DataSource
     * @throws SQLException DBの再接続に失敗
     */
    public void reConnect(DataSource ds) throws SQLException {

        if (ds instanceof BasicDataSource) {
            BasicDataSource bds = (BasicDataSource) ds;
            if (bds.isClosed()) {
                return;
            }
        }

        //コネクション(リソース)開放
        JDBCUtil.closeConnection(getCon());
        setCon(null);
        System.gc();

        //コネクション再接続
        setCon(JDBCUtil.getConnection(ds));
        if (getCon().getAutoCommit()) {
            getCon().setAutoCommit(false);
        }
    }

    /**
     * <br>[機  能] コンバート用のDataSourceを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gscontext GSContext
     * @return DataSource
     * @throws SAXException DataSourceの取得に失敗
     * @throws IOException DataSourceの取得に失敗
     * @throws IOToolsException DB保存先パスのチェック時に失敗
     */
    public DataSource getDataSource(GSContext gscontext)
    throws SAXException, IOException, IOToolsException {
        String rootPath = (String) gscontext.get(GSContext.APP_ROOT_PATH);
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        String dbConfPath = GsDataSourceFactory.getDataSourcePath(rootPath);
        DataSourceModel model = GsDataSourceFactory.createDataSourceModel(
                                                            GSConst.DATASOURCE_KEY,
                                                            dbConfPath, dbUtil, rootPath);

        if (dbUtil.getDbType() == GSConst.DBTYPE_H2DB) {
            String dbDir = GsDataSourceFactory.getDbDir(rootPath);
            log__.warn("コンバート対象となるDB保存先パス: " + dbDir);
            if (!IOTools.isDirCheck(dbDir, false)) {
                throw new IOException("DB保存先パスが存在しません。");
            }

            String url = "jdbc:h2:" + dbDir + "gs2db" + File.separator + "gs2db";
            url += ";LOCK_MODE=3";
            url += ";LOCK_TIMEOUT=1000";
            url += ";DEFAULT_LOCK_TIMEOUT=5000";
            url += ";MULTI_THREADED=0";
            url += ";IFEXISTS=FALSE";
            url += ";AUTOCOMMIT=OFF";
            url += ";DB_CLOSE_ON_EXIT=FALSE";
            url += ";MAX_LENGTH_INPLACE_LOB=10240";
            url += ";CACHE_SIZE=235929";
            url += ";PAGE_SIZE=8192";
            url += ";CACHE_TYPE=SOFT_LRU";
            url += ";MVCC=TRUE";
            url += ";QUERY_TIMEOUT=600000";
            model.setUrl(url);
            log__.warn("url = " + url);
        }

        return DataSourceUtil.createDataSource(model);
    }

    /**
     * Ver3.5.0以前のWEBメールデータをVer3.5.0形式へコンバートする
     * @param args パラメータ
     */
    public static void main(String[] args) {

        ConvWebmail350 convWebmail = new ConvWebmail350();
        String appRoot = args[0];
        GSContext gscontext = new GSContext();
        try {
            if (StringUtil.isNullZeroString(appRoot)) {
                log__.error("アプリケーションルートパスが設定されていません。");
                return;
            }

            log__.info("アプリケーションルートパス = " + appRoot);

            gscontext.put(GSContext.APP_ROOT_PATH, appRoot);
            ConfigBundle.readConfig(appRoot);

            convWebmail.convert(gscontext);

        } catch (Throwable e) {
            log__.error("WEBメール コンバートに失敗", e);
        } finally {
            try {
                IDbUtil dbUtil = DBUtilFactory.getInstance();
                dbUtil.shutdownDbServer(appRoot, convWebmail.getCon());
            } catch (Exception e) {
            } finally {
                JDBCUtil.closeConnection(convWebmail.getCon());
            }
        }
    }
}