package jp.groupsession.v2.sml.sml290;

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
import jp.groupsession.v2.sml.model.LabelDataModel;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml290Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml290Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml290Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sml110account アカウントSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelDataModel> getLabelList(int sml110account)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelDataModel> ret =
            new ArrayList<LabelDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SLB_SID, ");
            sql.addSql("   SLB_NAME, ");
            sql.addSql("   SLB_ORDER ");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("     SLB_TYPE = ?");
            sql.addSql("   and");
            sql.addSql("     SAC_SID = ?");
            sql.addSql("    )");
            sql.addSql(" or");
            sql.addSql("   (");
            sql.addSql("     SLB_TYPE = ?");
            sql.addSql("   and ");
            sql.addSql("     (");
            sql.addSql("       SML_LABEL.USR_SID in (");
            sql.addSql("         select USR_SID from SML_ACCOUNT_USER");
            sql.addSql("         where SAC_SID = ?");
            sql.addSql("         and coalesce(USR_SID, 0) > 0");
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
            sql.addSql(" order by ");
            sql.addSql("   SLB_ORDER asc ");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstSmail.LABELTYPE_ONES);
            sql.addIntValue(sml110account);
            sql.addIntValue(GSConstSmail.LABELTYPE_ALL);
            sql.addIntValue(sml110account);
            sql.addIntValue(sml110account);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LabelDataModel model = new LabelDataModel();
                model.setLabelSid(rs.getInt("SLB_SID"));
                model.setLabelName(rs.getString("SLB_NAME"));
                model.setLabelOrder(rs.getInt("SLB_ORDER"));
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
