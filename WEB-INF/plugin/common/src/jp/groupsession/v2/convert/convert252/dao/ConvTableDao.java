package jp.groupsession.v2.convert.convert252.dao;

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
 * <br>[解  説] v2.5.2へコンバートする際に使用する
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
        //インデックスを設定する
        createIndex();

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
     * <br>[機  能] インデックスを作成します
     * <br>[解  説] Exceptionはthrowしません。
     * <br>[備  考]
     */
    public void createIndex() {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //スケジュールチューニング
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create index SCH_DATA_INDEX1"
                    + " on SCH_DATA(SCD_USR_SID,SCD_FR_DATE,SCD_TO_DATE)");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log__.info("スケジュールのIndexは作成済みです");
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        try {

            //施設予約チューニング
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create index RSV_SIS_YRK_INDEX1"
                    + " on RSV_SIS_YRK(RSD_SID, RSY_FR_DATE, RSY_TO_DATE)");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log__.info("施設予約のIndexは作成済みです");
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

        //施設予約
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_ID varchar(15) not null default '1'");
        sqlList.add(sql);

        //施設IDのデフォルト値を設定
        __createSQLdefaultRsdId(sqlList);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_ENTER_INF");
        sql.addSql(" (");
        sql.addSql("   ENI_NAME       varchar(50),");
        sql.addSql("   ENI_NAME_KN    varchar(100),");
        sql.addSql("   ENI_KISYU      integer         not null,");
        sql.addSql("   ENI_URL        varchar(100),");
        sql.addSql("   ENI_BIKO       varchar(1000),");
        sql.addSql("   ENI_AUID       integer         not null,");
        sql.addSql("   ENI_ADATE      timestamp       not null,");
        sql.addSql("   ENI_EUID       integer         not null,");
        sql.addSql("   ENI_EDATE      timestamp       not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert ");
        sql.addSql(" into ");
        sql.addSql(" CMN_ENTER_INF(");
        sql.addSql("   ENI_NAME,");
        sql.addSql("   ENI_NAME_KN,");
        sql.addSql("   ENI_KISYU,");
        sql.addSql("   ENI_URL,");
        sql.addSql("   ENI_BIKO,");
        sql.addSql("   ENI_AUID,");
        sql.addSql("   ENI_ADATE,");
        sql.addSql("   ENI_EUID,");
        sql.addSql("   ENI_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   '',");
        sql.addSql("   '',");
        sql.addSql("   4,");
        sql.addSql("   '',");
        sql.addSql("   '',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] 施設予約の施設IDの初期値を設定するSQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList in SqlBuffer
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQLdefaultRsdId(
            ArrayList<SqlBuffer> sqlList)
            throws SQLException {

        SqlBuffer sql = new SqlBuffer();

        List<String> rsdSidList = __getRsdSidList();
        if (rsdSidList != null && rsdSidList.size() > 0) {
            for (String rsdSid : rsdSidList) {
                sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   RSV_SIS_DATA");
                sql.addSql(" set ");
                sql.addSql("   RSD_ID=" + rsdSid);
                sql.addSql(" where ");
                sql.addSql("   RSD_SID=" + rsdSid);
                sqlList.add(sql);
            }
        }

        return sqlList;
    }

    /**
     * <p>Select RSV_SIS_DATA All Data
     * @return List in RSV_SIS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<String> __getRsdSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSD_SID");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("RSD_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

}