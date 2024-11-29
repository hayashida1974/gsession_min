package jp.groupsession.v2.adr.adr241.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.adr010.dao.Adr010Dao;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr241.model.Adr241AddressModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 会社選択画面 担当者一覧で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr241Dao extends Adr010Dao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr241Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr241Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アドレス帳情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<Adr241AddressModel> getAddressList(Adr010SearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Adr241AddressModel> ret = new ArrayList<Adr241AddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_ADDRESS.ADR_SID as ADR_SID,");
            sql.addSql("   ADR_ADDRESS.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_ADDRESS.ABA_SID as ABA_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI as ADR_SEI,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI as ADR_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when ADR_ADDRESS.APS_SID= -1 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   ADR_POSITION.APS_SORT as APS_SORT");


            sql = _createSearchSql(sql, model);

            //並び順を設定
            int order = model.getOrderKey();
            sql.addSql(" order by");
            sql.addSql(_getSortSql("ADR_ADDRESS.ADR_SEI_KN", order) + ",");
            sql.addSql(_getSortSql("ADR_ADDRESS.ADR_MEI_KN", order) + ",");
            sql.addSql(_getSortSql("YAKUSYOKU_EXIST", order) + ",");
            sql.addSql(_getSortSql("ADR_POSITION.APS_SORT", order));

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Adr241AddressModel detailMdl = new Adr241AddressModel();

                detailMdl.setAddressSid(rs.getInt("ADR_SID"));
                detailMdl.setCompanySid(rs.getInt("ACO_SID"));
                detailMdl.setCompanyBaseSid(rs.getInt("ABA_SID"));
                detailMdl.setAddressName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));

                ret.add(detailMdl);
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
