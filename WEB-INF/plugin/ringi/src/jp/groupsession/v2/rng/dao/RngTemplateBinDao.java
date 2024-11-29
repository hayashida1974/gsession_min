package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.model.RngTemplateBinModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <p>RNG_TEMPLATE_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplateBinDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplateBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplateBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table RNG_TEMPLATE_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table RNG_TEMPLATE_BIN (");
            sql.addSql("   RTP_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   RTP_VER NUMBER(10,0) not null");
            sql.addSql("   primary key (RTP_SID,BIN_SID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert RNG_TEMPLATE_BIN Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplateBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATE_BIN(");
            sql.addSql("   RTP_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   RTP_VER");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtpSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getRtpVer());
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
     * <p>Update RNG_TEMPLATE_BIN Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int update(RngTemplateBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" and");
            sql.addSql("   RTP_VER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getRtpSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getRtpVer());

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
     * <p>Select RNG_TEMPLATE_BIN All Data
     * @return List in RNG_TEMPLATE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateBinModel> ret = new ArrayList<RngTemplateBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTP_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   RTP_VER");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateBinFromRs(rs));
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
     * <p>Delete RNG_TEMPLATE_BIN
     * @param bean RNG_TEMPLATE_BIN Model
     * @throws SQLException SQL実行例外
     * @return count
     */
    public  int delete(RngTemplateBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_BIN");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" and");
            sql.addSql("   RTP_VER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtpSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getRtpVer());

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
     * <br>[機  能] 指定したテンプレートSIDのバイナリ情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid テンプレートSID
     * @return count
     * @throws SQLException SQL実行時例外
     */
    public int deleteTpl(int rtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_BIN");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtpSid);

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
     * <p>Create RNG_TEMPLATE_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplateBinModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplateBinModel __getRngTemplateBinFromRs(ResultSet rs) throws SQLException {
        RngTemplateBinModel bean = new RngTemplateBinModel();
        bean.setRtpSid(rs.getInt("RTP_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setRtpVer(rs.getInt("RTP_VER"));
        return bean;
    }

    /**
     * <br>[機  能] 指定されたバイナリSIDが稟議テンプレートの添付ファイルのものかチェックする。
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid 稟議テンプレートSID
     * @param binSid バイナリSID
     * @return RNG_TEMPLATE_BINModel
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckRngTemplateBin(int rtpSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_BIN");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtpSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <br>[機  能] 指定されたテンプレートSIDのバイナリ情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid 稟議テンプレートSID
     * @return RNG_TEMPLATE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<Long> getBinSid(int rtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> lRtn = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_BIN");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lRtn.add(rs.getLong("BIN_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return lRtn;
    }

    /**
     * <br>[機  能] 指定した稟議テンプレート情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSidList 稟議テンプレート情報
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<RngTemplateModel> rtpList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   RNG_TEMPLATE_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   RNG_TEMPLATE_BIN.RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   RNG_TEMPLATE_BIN.RTP_VER=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateModel rtpMdl : rtpList) {
                log__.info(sql.toLogString());
                sql.addIntValue(rtpMdl.getRtpSid());
                sql.addIntValue(rtpMdl.getRtpVer());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    fileSize += rs.getInt("FILE_SIZE");
                }
                JDBCUtil.closeResultSet(rs);
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }
}
