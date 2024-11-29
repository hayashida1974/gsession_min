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
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.ISearchParam;
import jp.groupsession.v2.rng.model.RngTemplateKeiroConditionModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;

/**
 * <p>RNG_TEMPLATE_KEIRO_CONDITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateKeiroConditionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplateKeiroConditionDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplateKeiroConditionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplateKeiroConditionDao(Connection con) {
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
            sql.addSql("drop table RNG_TEMPLATE_KEIRO_CONDITION");

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
            sql.addSql(" create table RNG_TEMPLATE_KEIRO_CONDITION (");
            sql.addSql("   RTK_SID NUMBER(10,0) not null,");
            sql.addSql("   RKC_IFTEMPLATE NUMBER(10,0) not null,");
            sql.addSql("   RKC_IFGROUP NUMBER(10,0) not null,");
            sql.addSql("   RKC_IFPOS NUMBER(10,0) not null,");
            sql.addSql("   RKC_IFFORM varchar(20),");
            sql.addSql("   RKC_IFFORM_OPR varchar(10),");
            sql.addSql("   RKC_IFFORM_VALUE varchar(20)");
            sql.addSql("   RKC_ORID NUMBER(10,0) not null,");
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
     * <p>Insert RNG_TEMPLATE_KEIRO_CONDITION Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_KEIRO_CONDITION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplateKeiroConditionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATE_KEIRO_CONDITION(");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKC_IFTEMPLATE,");
            sql.addSql("   RKC_IFGROUP,");
            sql.addSql("   RKC_IFPOS,");
            sql.addSql("   RKC_IFFORM,");
            sql.addSql("   RKC_IFFORM_OPR,");
            sql.addSql("   RKC_IFFORM_VALUE,");
            sql.addSql("   RKC_ORID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRkcIftemplate());
            sql.addIntValue(bean.getRkcIfgroup());
            sql.addIntValue(bean.getRkcIfpos());
            sql.addStrValue(bean.getRkcIfform());
            sql.addStrValue(bean.getRkcIfformOpr());
            sql.addStrValue(bean.getRkcIfformValue());
            sql.addIntValue(bean.getRkcOrid());

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
     * <p>Update RNG_TEMPLATE_KEIRO_CONDITION Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_KEIRO_CONDITION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplateKeiroConditionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_KEIRO_CONDITION");
            sql.addSql(" set ");
            sql.addSql("   RTK_SID=?,");
            sql.addSql("   RKC_IFTEMPLATE=?,");
            sql.addSql("   RKC_IFGROUP=?,");
            sql.addSql("   RKC_IFPOS=?,");
            sql.addSql("   RKC_IFFORM=?,");
            sql.addSql("   RKC_IFFORM_OPR=?,");
            sql.addSql("   RKC_IFFORM_VALUE=?,");
            sql.addSql("   RKC_ORID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRkcIftemplate());
            sql.addIntValue(bean.getRkcIfgroup());
            sql.addIntValue(bean.getRkcIfpos());
            sql.addStrValue(bean.getRkcIfform());
            sql.addStrValue(bean.getRkcIfformOpr());
            sql.addStrValue(bean.getRkcIfformValue());
            sql.addIntValue(bean.getRkcOrid());

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
     * <p>Select RNG_TEMPLATE_KEIRO_CONDITION All Data
     * @return List in RNG_TEMPLATE_KEIRO_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroConditionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroConditionModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKC_IFTEMPLATE,");
            sql.addSql("   RKC_IFGROUP,");
            sql.addSql("   RKC_IFPOS,");
            sql.addSql("   RKC_IFFORM,");
            sql.addSql("   RKC_IFFORM_OPR,");
            sql.addSql("   RKC_IFFORM_VALUE,");
            sql.addSql("   RKC_ORID");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO_CONDITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroConditionFromRs(rs));
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
     * <p>Select RNG_TEMPLATE_KEIRO_CONDITION All Data
     * @param param 検索オブジェクト
     * @param keiroDao 経路テンプレート経路ステップ情報DAO
     * @param jkbn 削除区分
     * @return List in RNG_TEMPLATE_KEIRO_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroConditionModel> select(RngTemplateKeiroDao keiroDao,
            ISearchParam param, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroConditionModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKC_IFTEMPLATE,");
            sql.addSql("   RKC_IFGROUP,");
            sql.addSql("   RKC_IFPOS,");
            sql.addSql("   RKC_IFFORM,");
            sql.addSql("   RKC_IFFORM_OPR,");
            sql.addSql("   RKC_IFFORM_VALUE,");
            sql.addSql("   RKC_ORID");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID in ( ");
            keiroDao.writeSelect(sql, param, jkbn);
            sql.addSql("   )  ");
            sql.addSql(" order by RKC_ORID, RTK_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroConditionFromRs(rs));
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
     * <p>Select RNG_TEMPLATE_KEIRO_CONDITION All Data
     * @param rtkSid テンプレート経路ステップSID
     * @return List in RNG_TEMPLATE_KEIRO_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroConditionModel> select(int rtkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroConditionModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKC_IFTEMPLATE,");
            sql.addSql("   RKC_IFGROUP,");
            sql.addSql("   RKC_IFPOS,");
            sql.addSql("   RKC_IFFORM,");
            sql.addSql("   RKC_IFFORM_OPR,");
            sql.addSql("   RKC_IFFORM_VALUE,");
            sql.addSql("   RKC_ORID");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID = ? ");
            sql.addIntValue(rtkSid);
            sql.addSql(" order by RKC_ORID, RTK_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroConditionFromRs(rs));
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
     * <p>Delete RNG_TEMPLATE_KEIRO
     * @param rtkList  List in RTK_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteNoUseStep(List<RngTemplateKeiroModel> rtkList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateKeiroModel rtkMdl : rtkList) {
                sql.addIntValue(rtkMdl.getRtkSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create RNG_TEMPLATE_KEIRO_CONDITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplateKeiroConditionModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplateKeiroConditionModel
    __getRngTemplateKeiroConditionFromRs(ResultSet rs) throws SQLException {
        RngTemplateKeiroConditionModel bean = new RngTemplateKeiroConditionModel();
        bean.setRtkSid(rs.getInt("RTK_SID"));
        bean.setRkcIftemplate(rs.getInt("RKC_IFTEMPLATE"));
        bean.setRkcIfgroup(rs.getInt("RKC_IFGROUP"));
        bean.setRkcIfpos(rs.getInt("RKC_IFPOS"));
        bean.setRkcIfform(rs.getString("RKC_IFFORM"));
        bean.setRkcIfformOpr(rs.getString("RKC_IFFORM_OPR"));
        bean.setRkcIfformValue(rs.getString("RKC_IFFORM_VALUE"));
        bean.setRkcOrid(rs.getInt("RKC_ORID"));

        return bean;
    }

}
