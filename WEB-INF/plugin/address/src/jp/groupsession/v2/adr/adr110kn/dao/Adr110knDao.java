package jp.groupsession.v2.adr.adr110kn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.adr110kn.model.Adr110knModel;
import jp.groupsession.v2.adr.dao.AddressDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>アドレス帳 会社登録確認画面で使用するDAOクラス
 *
 * @author JTS
 */
public class Adr110knDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr110knDao.class);

    /**
     * <p>Default Constructor
     */
    public Adr110knDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr110knDao(Connection con) {
        super(con);
    }


    /**
     * <br>[機  能] 指定した会社に属するアドレス情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @param usrSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行例外
     */
    public List<Adr110knModel> getUserListBelongCompany(
            int acoSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Adr110knModel> ret = new ArrayList<Adr110knModel>();
        Adr110knModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID, ");
            sql.addSql("   ADR_SEI, ");
            sql.addSql("   ADR_MEI, ");
            sql.addSql("   ADR_SEI_KN, ");
            sql.addSql("   ADR_MEI_KN, ");
            sql.addSql("   ABA_NAME, ");
            sql.addSql("   APS_NAME ");
            sql.addSql(" from ");
            sql.addSql("   ((ADR_ADDRESS ");
            sql.addSql("   left join ADR_POSITION");
            sql.addSql("     on ADR_ADDRESS.APS_SID = ADR_POSITION.APS_SID)");
            sql.addSql("   left join ADR_COMPANY_BASE");
            sql.addSql("     on ADR_ADDRESS.ABA_SID = ADR_COMPANY_BASE.ABA_SID)");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("      ADR_ADDRESS.ACO_SID = ? ");
            sql.addSql("    or ");
            sql.addSql("      ADR_COMPANY_BASE.ACO_SID = ? ");
            sql.addSql("   )");
            sql.addSql(" and ");

            sql.addIntValue(acoSid);
            sql.addIntValue(acoSid);

            AddressDao.addViewableWhereSQL(sql, usrSid);

            sql.addSql(" order by ");
            sql.addSql("   ADR_POSITION.APS_SORT, ");
            sql.addSql("   ADR_ADDRESS.ADR_SEI_KN, ");
            sql.addSql("   ADR_ADDRESS.ADR_MEI_KN ");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                model = new Adr110knModel();
                model.setUserName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
                model.setAdrSid(rs.getInt("ADR_SID"));
                model.setCompanyBaseName(rs.getString("ABA_NAME"));
                model.setPositionName(rs.getString("APS_NAME"));
                ret.add(model);
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
