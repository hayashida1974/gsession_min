package jp.groupsession.v2.cmn.cmn180;

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
 * <br>[機  能] 天気予報(メイン)の情報を取得するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn180Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn180Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Cmn180Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 天気予報地域情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 天気予報地域情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Cmn180AreaModel> getAreaList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Cmn180AreaModel> ret = new ArrayList<Cmn180AreaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_MDISP_WEATHER.MDW_AREA as AREA_SID,");
            sql.addSql("   CMN_WEATHER_AREA.CWA_NAME as AREA_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_MDISP_WEATHER,");
            sql.addSql("   CMN_WEATHER_AREA");
            sql.addSql(" where ");
            sql.addSql("   CMN_MDISP_WEATHER.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_MDISP_WEATHER.MDW_AREA = CMN_WEATHER_AREA.CWA_SID");
            sql.addSql(" order by");
            sql.addSql("   CMN_MDISP_WEATHER.MDW_SORT");
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Cmn180AreaModel areaData = new Cmn180AreaModel();
                areaData.setAreaId(rs.getInt("AREA_SID"));
                areaData.setAreaName(rs.getString("AREA_NAME"));
                ret.add(areaData);
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
