package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlLabelModel;

/**
 * <p>WML_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlLabelDao(Connection con) {
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
            sql.addSql("drop table WML_LABEL");

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
            sql.addSql(" create table SML_LABEL (");
            sql.addSql("   SLB_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   SLB_NAME varchar(300) not null,");
            sql.addSql("   SLB_TYPE NUMBER(10,0) not null,");
            sql.addSql("   SLB_ORDER NUMBER(10,0) not null,");
            sql.addSql("   SAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WLB_SID)");
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
     * <p>Insert SML_LABEL Data Bindding JavaBean
     * @param bean SML_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_LABEL(");
            sql.addSql("   SLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_NAME,");
            sql.addSql("   SLB_TYPE,");
            sql.addSql("   SLB_ORDER,");
            sql.addSql("   SAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSlbSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSlbName());
            sql.addIntValue(bean.getSlbType());
            sql.addIntValue(bean.getSlbOrder());
            sql.addIntValue(bean.getSacSid());
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
     * <p>Update SML_LABEL Data Bindding JavaBean
     * @param bean SML_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_LABEL");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   SLB_NAME=?,");
            sql.addSql("   SLB_TYPE=?,");
            sql.addSql("   SLB_ORDER=?,");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSlbName());
            sql.addIntValue(bean.getSlbType());
            sql.addIntValue(bean.getSlbOrder());
            sql.addIntValue(bean.getSacSid());
            //where
            sql.addIntValue(bean.getSlbSid());

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
     * <p>並び順を更新する
     * @param slbSid ラベルSID
     * @param slbOrder 並び順
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateOrder(int slbSid, int slbOrder) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_LABEL");
            sql.addSql(" set ");
            sql.addSql("   SLB_ORDER=?");
            sql.addSql(" where ");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(slbOrder);
            sql.addIntValue(slbSid);

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
     * <p>Select SML_LABEL All Data
     * @return List in SML_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlLabelModel> ret = new ArrayList<SmlLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_NAME,");
            sql.addSql("   SLB_TYPE,");
            sql.addSql("   SLB_ORDER,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLabelFromRs(rs));
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
     * <p>Select SML_LABEL
     * @param slbSid SLB_SID
     * @return SML_LABELModel
     * @throws SQLException SQL実行例外
     */
    public SmlLabelModel select(int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_NAME,");
            sql.addSql("   SLB_TYPE,");
            sql.addSql("   SLB_ORDER,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(slbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlLabelFromRs(rs);
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
     * <p>Select SML_LABEL
     * @param sacSid SAC_SID
     * @return SML_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLabelModel> selectList(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlLabelModel> ret = new ArrayList<SmlLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_NAME,");
            sql.addSql("   SLB_TYPE,");
            sql.addSql("   SLB_ORDER,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   SLB_ORDER asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLabelFromRs(rs));
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
     * <p>Select SML_LABEL
     * @param sacSid SAC_SID
     * @param slbSid SLB_SID
     * @return SML_LABELModel
     * @throws SQLException SQL実行例外
     */
    public SmlLabelModel select(int sacSid, int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_NAME,");
            sql.addSql("   SLB_TYPE,");
            sql.addSql("   SLB_ORDER,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql("   and");
            sql.addSql("   SLB_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   SLB_ORDER asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(slbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlLabelFromRs(rs);
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
     * <p>Delete SML_LABEL
     * @param slbSid SLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(slbSid);

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
     * <br>[機  能] ラベル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @return ラベル情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<SmlLabelModel> getLabelList(int sacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlLabelModel> ret = new ArrayList<SmlLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_LABEL.SLB_SID        as SLB_SID,");
            sql.addSql("   SML_LABEL.USR_SID        as USR_SID,");
            sql.addSql("   SML_LABEL.SLB_NAME       as SLB_NAME,");
            sql.addSql("   SML_LABEL.SLB_TYPE       as SLB_TYPE,");
            sql.addSql("   SML_LABEL.SLB_ORDER      as SLB_ORDER,");
            sql.addSql("   SML_LABEL.SAC_SID        as SAC_SID,");
            sql.addSql("   SML_TOTAL_LABEL.LB_COUNT as LB_COUNT");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL");
            sql.addSql(" left join (");

            sql.addSql(" select");
            sql.addSql("   sum(SML_LABEL_TABLE.LABEL_COUNT) as LB_COUNT,");
            sql.addSql("   SML_LABEL_TABLE.LABEL_SID as LB_SID");
            sql.addSql(" from");
            sql.addSql(" (");
            sql.addSql("   select ");
            sql.addSql("     distinct");
            sql.addSql("     count(SML_JMEIS_LABEL.SMJ_SID) as LABEL_COUNT,");
            sql.addSql("     SML_JMEIS_LABEL.SLB_SID as LABEL_SID");
            sql.addSql("   from");
            sql.addSql("     SML_JMEIS_LABEL");
            sql.addSql("   left join");
            sql.addSql("     SML_JMEIS");
            sql.addSql("   on ");
            sql.addSql("     SML_JMEIS_LABEL.SMJ_SID = SML_JMEIS.SMJ_SID");
            sql.addSql("   where");
            sql.addSql("     SML_JMEIS_LABEL.SAC_SID = ?");
            sql.addSql("   and");
            sql.addSql("     SML_JMEIS.SAC_SID = ?");
            sql.addSql("   and");
            sql.addSql("     SML_JMEIS.SMJ_JKBN = ?");
            sql.addSql("   and");
            sql.addSql("     SML_JMEIS.SMJ_OPKBN = ?");
            sql.addSql("   group by");
            sql.addSql("     SML_JMEIS_LABEL.SLB_SID");
            sql.addSql("  union all");
            sql.addSql("   select ");
            sql.addSql("     distinct");
            sql.addSql("     0 as LABEL_COUNT,");
            sql.addSql("     SML_SMEIS_LABEL.SLB_SID as LABEL_SID");
            sql.addSql("   from");
            sql.addSql("     SML_SMEIS_LABEL");
            sql.addSql("   left join");
            sql.addSql("     SML_SMEIS");
            sql.addSql("   on ");
            sql.addSql("     SML_SMEIS_LABEL.SMS_SID = SML_SMEIS.SMS_SID");
            sql.addSql("   where");
            sql.addSql("     SML_SMEIS_LABEL.SAC_SID = ?");
            sql.addSql("   and");
            sql.addSql("     SML_SMEIS.SAC_SID = ?");
            sql.addSql("   and");
            sql.addSql("     SML_SMEIS.SMS_JKBN = ?");
            sql.addSql("   group by");
            sql.addSql("     SML_SMEIS_LABEL.SLB_SID");
            sql.addSql("  union all");
            sql.addSql("   select ");
            sql.addSql("     distinct");
            sql.addSql("     0 as LABEL_COUNT,");
            sql.addSql("     SML_WMEIS_LABEL.SLB_SID as LABEL_SID");
            sql.addSql("   from");
            sql.addSql("     SML_WMEIS_LABEL");
            sql.addSql("   left join");
            sql.addSql("     SML_WMEIS");
            sql.addSql("   on ");
            sql.addSql("     SML_WMEIS_LABEL.SMW_SID = SML_WMEIS.SMW_SID");
            sql.addSql("   where");
            sql.addSql("     SML_WMEIS_LABEL.SAC_SID = ?");
            sql.addSql("   and");
            sql.addSql("     SML_WMEIS.SAC_SID = ?");
            sql.addSql("   and");
            sql.addSql("     SML_WMEIS.SMW_JKBN = ?");
            sql.addSql("   group by");
            sql.addSql("     SML_WMEIS_LABEL.SLB_SID");
            sql.addSql("  ) SML_LABEL_TABLE");
            sql.addSql(" group by");
            sql.addSql("  SML_LABEL_TABLE.LABEL_SID");
            sql.addSql(" ) SML_TOTAL_LABEL ");

            sql.addSql(" on SML_TOTAL_LABEL.LB_SID = SML_LABEL.SLB_SID");

            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("     SML_LABEL.SLB_TYPE = ?");
            sql.addSql("   and");
            sql.addSql("     SML_LABEL.SAC_SID = ?");
            sql.addSql("    )");
            sql.addSql(" or");
            sql.addSql("   (");
            sql.addSql("     SML_LABEL.SLB_TYPE = ?");
            sql.addSql("   and ");

            //「ラベルを登録したユーザが[使用者]として設定されているアカウント」のみを対象とする。
            sql.addSql("     (");
            sql.addSql("       SML_LABEL.USR_SID in (");
            sql.addSql("         select SML_ACCOUNT_USER.USR_SID from SML_ACCOUNT_USER");
            sql.addSql("         where SML_ACCOUNT_USER.SAC_SID = ?");
            sql.addSql("         and coalesce(SML_ACCOUNT_USER.USR_SID, 0) > 0");
            sql.addSql("       )");
            sql.addSql("      or");
            sql.addSql("       SML_LABEL.USR_SID in (");
            sql.addSql("         select CMN_BELONGM.USR_SID");
            sql.addSql("         from");
            sql.addSql("           SML_ACCOUNT_USER,");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where SML_ACCOUNT_USER.SAC_SID = ?");
            sql.addSql("         and coalesce(SML_ACCOUNT_USER.GRP_SID, 0) > 0");
            sql.addSql("         and SML_ACCOUNT_USER.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addSql(" order by");
            sql.addSql("   SML_LABEL.SLB_ORDER asc");

            sql.addIntValue(sacSid);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            sql.addIntValue(sacSid);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addIntValue(sacSid);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addIntValue(GSConstSmail.LABELTYPE_ONES);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.LABELTYPE_ALL);
            sql.addIntValue(sacSid);
            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SmlLabelModel bean = new SmlLabelModel();
                bean.setSlbSid(rs.getInt("SLB_SID"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setSlbName(rs.getString("SLB_NAME"));
                bean.setSlbType(rs.getInt("SLB_TYPE"));
                bean.setSlbOrder(rs.getInt("SLB_ORDER"));
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSlbCount(rs.getInt("LB_COUNT"));
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
     * <br>[機  能] 指定したユーザが作成したラベルの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param slbType ラベル種別
     * @return ラベルの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getLabelList(int usrSid, int slbType) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SLB_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SLB_TYPE = ?");

            sql.addIntValue(usrSid);
            sql.addIntValue(slbType);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SLB_SID"));
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
     * <br>[機  能] ラベルの並び順の現在最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @return ラベル最大値
     * @throws SQLException SQL実行時例外
     */
    public int maxSortNumber(int sacSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxNumber = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(SLB_ORDER) as MAX_ORDER");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL");
            sql.addSql(" where");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAX_ORDER");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxNumber;
    }

    /**
     * <p>Create SML_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlLabelModel
     * @throws SQLException SQL実行例外
     */
    private SmlLabelModel __getSmlLabelFromRs(ResultSet rs) throws SQLException {
        SmlLabelModel bean = new SmlLabelModel();
        bean.setSlbSid(rs.getInt("SLB_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSlbName(rs.getString("SLB_NAME"));
        bean.setSlbType(rs.getInt("SLB_TYPE"));
        bean.setSlbOrder(rs.getInt("SLB_ORDER"));
        bean.setSacSid(rs.getInt("SAC_SID"));
        return bean;
    }
}
