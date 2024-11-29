package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlAccountAutoDestModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_ACCOUNT_AUTODEST Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAccountAutoDestDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountAutoDestDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlAccountAutoDestDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAccountAutoDestDao(Connection con) {
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
            sql.addSql("drop table SML_ACCOUNT_AUTODEST");

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
            sql.addSql(" create table SML_ACCOUNT_AUTODEST (");
            sql.addSql("   SAC_SID NUMBER(10,0) not null,");
            sql.addSql("   SAA_TYPE NUMBER(10,0) not null,");
            sql.addSql("   SAA_SID NUMBER(10,0) not null");
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
     * <p>Insert SML_ACCOUNT_AUTODEST Data Bindding JavaBean
     * @param bean SML_ACCOUNT_AUTODEST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAccountAutoDestModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT_AUTODEST(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAA_TYPE,");
            sql.addSql("   SAA_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSaaType());
            sql.addIntValue(bean.getSaaSid());
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
     * <p>Update SML_ACCOUNT_AUTODEST Data Bindding JavaBean
     * @param bean SML_ACCOUNT_AUTODEST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAccountAutoDestModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT_AUTODEST");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SAA_TYPE=?,");
            sql.addSql("   SAA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSaaType());
            sql.addIntValue(bean.getSaaSid());

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
     * <p>Delete SML_ACCOUNT_AUTODEST Data Bindding sacSid
     * @param sacSid sacSid
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int sacSid) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_AUTODEST");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

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
     * <p>Select SML_ACCOUNT_AUTODEST All Data
     * @return List in SML_ACCOUNT_AUTODESTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountAutoDestModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountAutoDestModel> ret = new ArrayList<SmlAccountAutoDestModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAA_TYPE,");
            sql.addSql("   SAA_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_AUTODEST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlAutoDestFromRs(rs));
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
     * <p>Select SML_ACCOUNT_AUTODEST All Data
     * @param sacSid アカウントSID
     * @param type 0:To 1:Cc 2:Bcc
     * @return List in SML_ACCOUNT_AUTODESTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountModel> getAutoDestAccounts(int sacSid, int type) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountModel> ret = new ArrayList<SmlAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAA_SID as SAA_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE,");
            sql.addSql("   SML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO,");
            sql.addSql("   SML_ACCOUNT.SAC_SEND_MAILTYPE as SAC_SEND_MAILTYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as SAC_JKBN,");
            sql.addSql("   SML_ACCOUNT.SAC_THEME as SAC_THEME,");
            sql.addSql("   SML_ACCOUNT.SAC_QUOTES as SAC_QUOTES,");

            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");

            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_AUTODEST,");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join CMN_USRM_INF");
            sql.addSql("   on SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAC_SID=?");
            sql.addIntValue(sacSid);
            sql.addSql("   and SML_ACCOUNT_AUTODEST.SAA_TYPE=?");
            sql.addIntValue(type);
            sql.addSql("   and SML_ACCOUNT_AUTODEST.SAA_SID=SML_ACCOUNT.SAC_SID");
            sql.addSql("   and SML_ACCOUNT.SAC_JKBN=?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlAccountModel bean = new SmlAccountModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSacType(rs.getInt("SAC_TYPE"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setSacName(rs.getString("SAC_NAME"));
                bean.setSacBiko(rs.getString("SAC_BIKO"));
                bean.setSacSendMailtype(rs.getInt("SAC_SEND_MAILTYPE"));
                bean.setSacJkbn(rs.getInt("SAC_JKBN"));
                bean.setSacTheme(rs.getInt("SAC_THEME"));
                bean.setSacQuotes(rs.getInt("SAC_QUOTES"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    bean.setSacName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                }
                ret.add(bean);
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
     * <p>Create SML_ACCOUNT_AUTODEST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAutoDestModel
     * @throws SQLException SQL実行例外
     */
    private SmlAccountAutoDestModel __getSmlAutoDestFromRs(ResultSet rs) throws SQLException {
        SmlAccountAutoDestModel bean = new SmlAccountAutoDestModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSaaType(rs.getInt("SAA_TYPE"));
        bean.setSaaSid(rs.getInt("SAA_SID"));
        return bean;
    }
}
