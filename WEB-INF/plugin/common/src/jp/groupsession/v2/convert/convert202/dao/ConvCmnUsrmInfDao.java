package jp.groupsession.v2.convert.convert202.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.convert.convert202.model.ConvUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ユーザ情報のDAOクラス(コンバート用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvCmnUsrmInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvCmnUsrmInfDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvCmnUsrmInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvCmnUsrmInfDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ユーザ情報から全ての役職名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<ConvUsrmInfModel> getAllPos() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ConvUsrmInfModel> ret = new ArrayList<ConvUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" group by");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU");

            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ConvUsrmInfModel bean = new ConvUsrmInfModel();
                bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
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
     * <br>[機  能] ユーザ情報の役職に対応する役職SIDを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePos() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set");
            sql.addSql("   POS_SID = (");
            sql.addSql("             select");
            sql.addSql("               POS_SID");
            sql.addSql("             from");
            sql.addSql("               CMN_POSITION");
            sql.addSql("             where");
            sql.addSql("               CMN_USRM_INF.USI_YAKUSYOKU = CMN_POSITION.POS_NAME");
            sql.addSql("             )");
            sql.addSql(" where");
            sql.addSql("   USI_YAKUSYOKU is not null");
            sql.addSql(" and");
            sql.addSql("   USI_YAKUSYOKU != ''");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] ユーザ情報の役職(文字列)を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateClearPos() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set");
            sql.addSql("   USI_YAKUSYOKU = null");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

}
