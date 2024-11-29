package jp.groupsession.v2.bbs.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.model.BbsBinModel;
import jp.groupsession.v2.bbs.model.BbsBodyBinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BBS_BODY_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsBodyBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsBinDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsBodyBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsBodyBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert BBS_BODY_BIN Data Bindding JavaBean
     * @param bean BBS_BODY_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsBodyBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_BODY_BIN(");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BBB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBwiSid());
            sql.addIntValue(bean.getBbbFileSid());
            sql.addLongValue(bean.getBinSid());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>投稿本文添付情報の一括登録を行う
     * @param bwiSid 投稿SID
     * @param bodyFileList 本文内ファイルのID
     * @param bodyBinSidList バイナリSIDの一覧
     * @throws SQLException SQL実行例外
     */
    public void insertBbsBodyBinData(
            int bwiSid, List<String> bodyFileList, List <String> bodyBinSidList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql(" BBS_BODY_BIN (");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BBB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   " + bwiSid + ",");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            String logString = sql.toLogString();
            pstmt = con.prepareStatement(sql.toSqlString());

            for (int i = 0; i < bodyFileList.size(); ++i) {
                pstmt.setInt(1, Integer.parseInt(bodyFileList.get(i)));
                pstmt.setLong(2, Long.parseLong(bodyBinSidList.get(i)));
                logString = StringUtil.substitute(logString, "?,", bodyFileList.get(i) + ",");
                logString = StringUtil.substitute(logString, "?", bodyBinSidList.get(i));
                log__.info(logString);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update BBS_BODY_BIN Data Bindding JavaBean
     * @param bean BBS_BODY_BIN Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_BODY_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getBwiSid());
            sql.addLongValue(bean.getBinSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select BBS_BODY_BIN All Data
     * @return List in BBS_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsBodyBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsBodyBinModel> ret = new ArrayList<BbsBodyBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BBB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_BODY_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsBodyBinFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     * <p>Select BBS_BODY_BIN
     * @param bean BBS_BODY_BIN Model
     * @return BBS_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public BbsBodyBinModel select(BbsBodyBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsBodyBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BBB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addSql(" and");
            sql.addSql("   BBB_FILE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBwiSid());
            sql.addIntValue(bean.getBbbFileSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsBodyBinFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した投稿SIDから投稿本文添付情報を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return BBS_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsBodyBinModel> select(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BbsBodyBinModel> ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BBB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            ret = new ArrayList<BbsBodyBinModel>();
            while (rs.next()) {
                BbsBodyBinModel retMdl = new BbsBodyBinModel();
                retMdl.setBwiSid(rs.getInt("BWI_SID"));
                retMdl.setBbbFileSid(rs.getInt("BBB_FILE_SID"));
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                ret.add(retMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete BBS_BODY_BIN
     * @param bwiSid 投稿SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create BBS_BODY_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsBodyBinModel
     * @throws SQLException SQL実行例外
     */
    private BbsBodyBinModel __getBbsBodyBinFromRs(ResultSet rs) throws SQLException {
        BbsBodyBinModel bean = new BbsBodyBinModel();
        bean.setBwiSid(rs.getInt("BWI_SID"));
        bean.setBbbFileSid(rs.getInt("BBB_FILE_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }

}
