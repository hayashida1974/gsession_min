package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnInfoTagModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_INFO_TAG Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnInfoTagDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnInfoTagDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnInfoTagDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnInfoTagDao(Connection con) {
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
            sql.addSql("drop table CMN_INFO_TAG");

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
            sql.addSql(" create table CMN_INFO_TAG (");
            sql.addSql("   IMS_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (IMS_SID,USR_SID,GRP_SID)");
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
     * <p>Insert CMN_INFO_TAG Data Bindding JavaBean
     * @param bean CMN_INFO_TAG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnInfoTagModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_INFO_TAG(");
            sql.addSql("   IMS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getImsSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>Update CMN_INFO_TAG Data Bindding JavaBean
     * @param bean CMN_INFO_TAG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnInfoTagModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getImsSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select CMN_INFO_TAG All Data
     * @return List in CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnInfoTagModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnInfoTagModel> ret = new ArrayList<CmnInfoTagModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IMS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_TAG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoTagFromRs(rs));
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
     * <p>Select CMN_INFO_TAG All Data
     * @param imsSid インフォメーションSID
     * @return List in CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnInfoTagModel> select(int imsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnInfoTagModel> ret = new ArrayList<CmnInfoTagModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IMS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");
            sql.addIntValue(imsSid);
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoTagFromRs(rs));
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
     * <p>Select CMN_INFO_TAG
     * @param imsSid IMS_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @return CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public CmnInfoTagModel select(int imsSid, int usrSid, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnInfoTagModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IMS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(imsSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnInfoTagFromRs(rs);
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
     * <p>ユーザSIDを指定して公開対象情報を取得する
     * @param usrSid USR_SID
     * @param ret HashMap in CmnInfoTagModel
     * @return CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, CmnInfoTagModel> getTargetInfoForUser(
            HashMap<Integer, CmnInfoTagModel> ret, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IMS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=-1");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            CmnInfoTagModel bean = null;
            while (rs.next()) {
                bean = __getCmnInfoTagFromRs(rs);
                ret.put(new Integer(bean.getImsSid()), bean);
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
     * <p>グループSIDを指定して公開対象情報を取得する
     * @param ret HashMap in CmnInfoTagModel
     * @param grpSidList GRP_SID
     * @return CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, CmnInfoTagModel> getTargetInfoForGroup(
            HashMap<Integer, CmnInfoTagModel> ret, ArrayList<Integer> grpSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IMS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=-1");
            sql.addSql(" and");
            sql.addSql("   GRP_SID in(-1");
            for (Integer gpSid : grpSidList) {
                sql.addSql(" ,?");
                sql.addIntValue(gpSid);
            }
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            CmnInfoTagModel bean = null;
            while (rs.next()) {
                bean = __getCmnInfoTagFromRs(rs);
                ret.put(new Integer(bean.getImsSid()), bean);
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
     * <p>インフォメーションSIDを指定して公開対象のグループ名一覧を取得する
     * @param imsSid インフォメーションSID
     * @return List in CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getTargetGroupNames(int imsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_GROUPM.GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_NAME_KN");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_TAG,");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   CMN_GROUPM.GRP_SID = CMN_INFO_TAG.GRP_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_INFO_TAG.IMS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CMN_INFO_TAG.USR_SID=-1");
            sql.addSql(" order by");
            sql.addSql("   CMN_GROUPM.GRP_NAME_KN");
            sql.addIntValue(imsSid);
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("GRP_NAME"));
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
     * <p>インフォメーションSIDを指定して公開対象のユーザ名一覧を取得する
     * @param imsSid インフォメーションSID
     * @return List in CMN_INFO_TAGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getTargetUserNames(int imsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_TAG,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_INFO_TAG.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_INFO_TAG.IMS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CMN_INFO_TAG.GRP_SID=-1");
            sql.addSql(" order by");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
            sql.addIntValue(imsSid);
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
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
     * <p>Delete CMN_INFO_TAG
     * @param imsSid IMS_SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int imsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(imsSid);

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
     * <p>Delete CMN_INFO_TAG
     * @param imsSid IMS_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int imsSid, int usrSid, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(imsSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

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
     * <p>Delete CMN_INFO_TAG
     * @param imsSids IMS_SID配列
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(String[] imsSids) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_TAG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID in(-1");
            
            for (String imsSid : imsSids) {
                sql.addSql("   ,?");
                sql.addIntValue(Integer.parseInt(imsSid));                
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <p>Create CMN_INFO_TAG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnInfoTagModel
     * @throws SQLException SQL実行例外
     */
    private CmnInfoTagModel __getCmnInfoTagFromRs(ResultSet rs) throws SQLException {
        CmnInfoTagModel bean = new CmnInfoTagModel();
        bean.setImsSid(rs.getInt("IMS_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        return bean;
    }
}
