package jp.groupsession.v2.cmn.cmn170.dao;

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
import jp.groupsession.v2.cmn.cmn170.Cmn170Model;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] テーマ情報を取得するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn170Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn170Dao.class);

    /**
     * <p>Default Constructor
     */
    public Cmn170Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Cmn170Dao(Connection con) {
        super(con);
    }


    /**
     * <p>Select CMN_THEME
     * @return List in CMN_170Model
     * @throws SQLException SQL実行例外
     */
    public List<Cmn170Model> selectClassicTheme() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Cmn170Model> ret = new ArrayList<Cmn170Model>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH_IMG");
            sql.addSql(" from ");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID <= 6");
            sql.addSql(" order by ");
            sql.addSql("   CTM_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnThemeFromRs(rs));
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
     * <p>Select CMN_THEME
     * @return List in CMN_170Model
     * @throws SQLException SQL実行例外
     */
    public List<Cmn170Model> selectOriginalTheme() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Cmn170Model> ret = new ArrayList<Cmn170Model>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH_IMG");
            sql.addSql(" from ");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID > 6");
            sql.addSql(" order by ");
            sql.addSql("   CTM_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnThemeFromRs(rs));
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
     * <p>Select CTM_PATH
     * @param ctmSid CTM_SID
     * @return List in CMN_170Model
     * @throws SQLException SQL実行例外
     */
    public String select(int ctmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CTM_PATH");
            sql.addSql(" from");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where");
            sql.addSql("   CTM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ctmSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getString("CTM_PATH");
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
     * <p>Select CMN_USR_THEME
     * @param usrSid USR_SID
     * @return ret
     * @throws SQLException SQL実行例外
     */
    public int select2(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CTM_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_THEME");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CTM_SID");
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
     * <br>[機  能] URLSIDを個人ブックマークに登録している人数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ctmSid CTMSID
     * @return 人数
     * @throws SQLException SQL実行例外
     */
    private int __selectPerCount(int ctmSid)
    throws SQLException {
        log__.debug("CTMSIDを登録している人数を取得する");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SID_TABLE.SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       CMN_USRM.USR_SID,");
            sql.addSql("       COALESCE(CMN_USR_THEME.CTM_SID,7) as SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM");
            sql.addSql("       left join");
            sql.addSql("         CMN_USR_THEME");
            sql.addSql("       on");
            sql.addSql("         CMN_USRM.USR_SID = CMN_USR_THEME.USR_SID");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM.USR_JKBN = ?");
            sql.addSql("     and");
            sql.addSql("       CMN_USRM.USR_SID > ?");
            sql.addSql("   ) as SID_TABLE");
            sql.addSql(" where");
            sql.addSql("   SID_TABLE.SID=?");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(ctmSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Create CMN_THEME Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnThemeModel
     * @throws SQLException SQL実行例外
     */
    private Cmn170Model __getCmnThemeFromRs(ResultSet rs) throws SQLException {
        Cmn170Model bean = new Cmn170Model();
        bean.setCtmSid(rs.getInt("CTM_SID"));
        bean.setCtmName(rs.getString("CTM_NAME"));
        bean.setCtmPathImg(rs.getString("CTM_PATH_IMG"));
        bean.setCtmPerCount(__selectPerCount(rs.getInt("CTM_SID")));

        return bean;
    }
}
