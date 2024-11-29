package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlAdelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_ADATE Data Access Object
 *
 * @author JTS DaoGenerator
 */
public class SmlAdelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAdelDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAdelDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ショートメール自動削除設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @return ret ショートメール自動削除設定モデル
     * @throws SQLException SQL実行例外
     */
    public SmlAdelModel select(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        SmlAdelModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAD_USR_KBN,");
            sql.addSql("   SAD_DEL_KBN,");
            sql.addSql("   SAD_JDEL_KBN,");
            sql.addSql("   SAD_JDEL_YEAR,");
            sql.addSql("   SAD_JDEL_MONTH,");
            sql.addSql("   SAD_SDEL_KBN,");
            sql.addSql("   SAD_SDEL_YEAR,");
            sql.addSql("   SAD_SDEL_MONTH,");
            sql.addSql("   SAD_WDEL_KBN,");
            sql.addSql("   SAD_WDEL_YEAR,");
            sql.addSql("   SAD_WDEL_MONTH,");
            sql.addSql("   SAD_DDEL_KBN,");
            sql.addSql("   SAD_DDEL_YEAR,");
            sql.addSql("   SAD_DDEL_MONTH,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_ADEL");
            sql.addSql(" where");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getSmlAdelFromRs(rs);
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
     * <br>[機  能] ショートメール自動削除設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret ショートメール自動削除設定モデル
     * @throws SQLException SQL実行例外
     */
    public List<SmlAdelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<SmlAdelModel> ret = new ArrayList<SmlAdelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAD_USR_KBN,");
            sql.addSql("   SAD_DEL_KBN,");
            sql.addSql("   SAD_JDEL_KBN,");
            sql.addSql("   SAD_JDEL_YEAR,");
            sql.addSql("   SAD_JDEL_MONTH,");
            sql.addSql("   SAD_SDEL_KBN,");
            sql.addSql("   SAD_SDEL_YEAR,");
            sql.addSql("   SAD_SDEL_MONTH,");
            sql.addSql("   SAD_WDEL_KBN,");
            sql.addSql("   SAD_WDEL_YEAR,");
            sql.addSql("   SAD_WDEL_MONTH,");
            sql.addSql("   SAD_DDEL_KBN,");
            sql.addSql("   SAD_DDEL_YEAR,");
            sql.addSql("   SAD_DDEL_MONTH,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_ADEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSmlAdelFromRs(rs));
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
     * <br>[機  能] ショートメール自動削除設定(全ユーザ)を取得する
     * <br>[解  説]
     * <br>[備  考] [自動削除する] に設定されているユーザが対象
     *
     * @param kbn 1:受信 2:送信 3:草稿 4:ゴミ箱
     * @return ret ショートメール自動削除設定モデルリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmlAdelModel> selectAutoDelUserData(int kbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<SmlAdelModel> ret = new ArrayList<SmlAdelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAD_USR_KBN,");
            sql.addSql("   SAD_DEL_KBN,");
            sql.addSql("   SAD_JDEL_KBN,");
            sql.addSql("   SAD_JDEL_YEAR,");
            sql.addSql("   SAD_JDEL_MONTH,");
            sql.addSql("   SAD_SDEL_KBN,");
            sql.addSql("   SAD_SDEL_YEAR,");
            sql.addSql("   SAD_SDEL_MONTH,");
            sql.addSql("   SAD_WDEL_KBN,");
            sql.addSql("   SAD_WDEL_YEAR,");
            sql.addSql("   SAD_WDEL_MONTH,");
            sql.addSql("   SAD_DDEL_KBN,");
            sql.addSql("   SAD_DDEL_YEAR,");
            sql.addSql("   SAD_DDEL_MONTH,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_ADEL");
            sql.addSql(" where");

            if (kbn == 1) {
                sql.addSql("   SAD_JDEL_KBN = ?");
            } else if (kbn == 2) {
                sql.addSql("   SAD_SDEL_KBN = ?");
            } else if (kbn == 3) {
                sql.addSql("   SAD_WDEL_KBN = ?");
            } else if (kbn == 4) {
                sql.addSql("   SAD_DDEL_KBN = ?");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.SML_AUTO_DEL_LIMIT);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlAdelModel mdl = __getSmlAdelFromRs(rs);
                ret.add(mdl);
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
     * <br>[機  能] ショートメール自動削除設定(管理者)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ret ショートメール自動削除設定
     * @throws SQLException SQL実行例外
     */
    public SmlAdelModel getAdminAutoDelData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        SmlAdelModel model = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAD_USR_KBN,");
            sql.addSql("   SAD_DEL_KBN,");
            sql.addSql("   SAD_JDEL_KBN,");
            sql.addSql("   SAD_JDEL_YEAR,");
            sql.addSql("   SAD_JDEL_MONTH,");
            sql.addSql("   SAD_SDEL_KBN,");
            sql.addSql("   SAD_SDEL_YEAR,");
            sql.addSql("   SAD_SDEL_MONTH,");
            sql.addSql("   SAD_WDEL_KBN,");
            sql.addSql("   SAD_WDEL_YEAR,");
            sql.addSql("   SAD_WDEL_MONTH,");
            sql.addSql("   SAD_DDEL_KBN,");
            sql.addSql("   SAD_DDEL_YEAR,");
            sql.addSql("   SAD_DDEL_MONTH,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_ADEL");
            sql.addSql(" where");
            sql.addSql("   SAD_USR_KBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.SML_ADEL_USR_KBN_ADM);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getSmlAdelFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return model;
    }

    /**
     * <br>[機  能] ショートメール自動削除設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean SML_ADEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAdelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ADEL(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAD_USR_KBN,");
            sql.addSql("   SAD_DEL_KBN,");
            sql.addSql("   SAD_JDEL_KBN,");
            sql.addSql("   SAD_JDEL_YEAR,");
            sql.addSql("   SAD_JDEL_MONTH,");
            sql.addSql("   SAD_SDEL_KBN,");
            sql.addSql("   SAD_SDEL_YEAR,");
            sql.addSql("   SAD_SDEL_MONTH,");
            sql.addSql("   SAD_WDEL_KBN,");
            sql.addSql("   SAD_WDEL_YEAR,");
            sql.addSql("   SAD_WDEL_MONTH,");
            sql.addSql("   SAD_DDEL_KBN,");
            sql.addSql("   SAD_DDEL_YEAR,");
            sql.addSql("   SAD_DDEL_MONTH,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
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
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSadUsrKbn());
            sql.addIntValue(bean.getSadDelKbn());
            sql.addIntValue(bean.getSadJdelKbn());
            sql.addIntValue(bean.getSadJdelYear());
            sql.addIntValue(bean.getSadJdelMonth());
            sql.addIntValue(bean.getSadSdelKbn());
            sql.addIntValue(bean.getSadSdelYear());
            sql.addIntValue(bean.getSadSdelMonth());
            sql.addIntValue(bean.getSadWdelKbn());
            sql.addIntValue(bean.getSadWdelYear());
            sql.addIntValue(bean.getSadWdelMonth());
            sql.addIntValue(bean.getSadDdelKbn());
            sql.addIntValue(bean.getSadDdelYear());
            sql.addIntValue(bean.getSadDdelMonth());
            sql.addIntValue(bean.getSadAuid());
            sql.addDateValue(bean.getSadAdate());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());

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
     * <br>[機  能] ショートメール自動削除設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean SML_ADEL Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAdelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ADEL");
            sql.addSql(" set");
            sql.addSql("   SAD_DEL_KBN = ?,");
            sql.addSql("   SAD_JDEL_KBN = ?,");
            sql.addSql("   SAD_JDEL_YEAR = ?,");
            sql.addSql("   SAD_JDEL_MONTH = ?,");
            sql.addSql("   SAD_SDEL_KBN = ?,");
            sql.addSql("   SAD_SDEL_YEAR = ?,");
            sql.addSql("   SAD_SDEL_MONTH = ?,");
            sql.addSql("   SAD_WDEL_KBN = ?,");
            sql.addSql("   SAD_WDEL_YEAR = ?,");
            sql.addSql("   SAD_WDEL_MONTH = ?,");
            sql.addSql("   SAD_DDEL_KBN = ?,");
            sql.addSql("   SAD_DDEL_YEAR = ?,");
            sql.addSql("   SAD_DDEL_MONTH = ?,");
            sql.addSql("   SAD_EUID = ?,");
            sql.addSql("   SAD_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadDelKbn());
            sql.addIntValue(bean.getSadJdelKbn());
            sql.addIntValue(bean.getSadJdelYear());
            sql.addIntValue(bean.getSadJdelMonth());
            sql.addIntValue(bean.getSadSdelKbn());
            sql.addIntValue(bean.getSadSdelYear());
            sql.addIntValue(bean.getSadSdelMonth());
            sql.addIntValue(bean.getSadWdelKbn());
            sql.addIntValue(bean.getSadWdelYear());
            sql.addIntValue(bean.getSadWdelMonth());
            sql.addIntValue(bean.getSadDdelKbn());
            sql.addIntValue(bean.getSadDdelYear());
            sql.addIntValue(bean.getSadDdelMonth());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());
            //where
            sql.addIntValue(bean.getSacSid());

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
     * <p>Create SML_ADEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAdelModel
     * @throws SQLException SQL実行例外
     */
    private SmlAdelModel __getSmlAdelFromRs(ResultSet rs) throws SQLException {
        SmlAdelModel bean = new SmlAdelModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSadUsrKbn(rs.getInt("SAD_USR_KBN"));
        bean.setSadDelKbn(rs.getInt("SAD_DEL_KBN"));
        bean.setSadJdelKbn(rs.getInt("SAD_JDEL_KBN"));
        bean.setSadJdelYear(rs.getInt("SAD_JDEL_YEAR"));
        bean.setSadJdelMonth(rs.getInt("SAD_JDEL_MONTH"));
        bean.setSadSdelKbn(rs.getInt("SAD_SDEL_KBN"));
        bean.setSadSdelYear(rs.getInt("SAD_SDEL_YEAR"));
        bean.setSadSdelMonth(rs.getInt("SAD_SDEL_MONTH"));
        bean.setSadWdelKbn(rs.getInt("SAD_WDEL_KBN"));
        bean.setSadWdelYear(rs.getInt("SAD_WDEL_YEAR"));
        bean.setSadWdelMonth(rs.getInt("SAD_WDEL_MONTH"));
        bean.setSadDdelKbn(rs.getInt("SAD_DDEL_KBN"));
        bean.setSadDdelYear(rs.getInt("SAD_DDEL_YEAR"));
        bean.setSadDdelMonth(rs.getInt("SAD_DDEL_MONTH"));
        bean.setSadAuid(rs.getInt("SAD_AUID"));
        bean.setSadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAD_ADATE")));
        bean.setSadEuid(rs.getInt("SAD_EUID"));
        bean.setSadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAD_EDATE")));
        return bean;
    }
}