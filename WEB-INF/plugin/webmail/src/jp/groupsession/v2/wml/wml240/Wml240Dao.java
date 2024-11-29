package jp.groupsession.v2.wml.wml240;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.AccountDataModel;

/**
 * <br>[機  能] WEBメール メールテンプレート管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml240Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml240Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml240Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アカウントリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<AccountDataModel> getAccountList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AccountDataModel> ret = new ArrayList<AccountDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql(" order by WAC_NAME asc");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AccountDataModel model = new AccountDataModel();
                model.setAccountSid(rs.getInt("WAC_SID"));
                model.setAccountName(rs.getString("WAC_NAME"));
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
