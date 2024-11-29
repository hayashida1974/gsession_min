package jp.groupsession.v2.adr.adr170;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrContactBinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>アドレス帳 コンタクト履歴登録画面で使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.1
 */
public class Adr170BinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr170BinDao.class);

    /**
     * <p>Default Constructor
     */
    public Adr170BinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr170BinDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] コンタクト履歴SIDからバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adcSid コンタクト履歴SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectBinSidList(int adcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID = ?");
            sql.addIntValue(adcSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("BIN_SID"));
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
     * <br>[機  能] コンタクト履歴SIDから添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adcSid コンタクト履歴SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBin(int adcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID = ?");
            sql.addIntValue(adcSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] コンタクト履歴添付情報を登録する
     * <br>[解  説] Listを渡し複数登録する
     * <br>[備  考]
     * @param bean AdrContactBinModel
     * @param binList List in String
     * @throws SQLException SQL実行例外
     */
    public void insertAdrBin(AdrContactBinModel bean, List<String> binList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (binList.size() < 1) {
            return;
        }

        try {

            for (int i = 0; i < binList.size(); i++) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" ADR_CONTACT_BIN(");
                sql.addSql("   ADC_SID,");
                sql.addSql("   BIN_SID,");
                sql.addSql("   ACB_AUID,");
                sql.addSql("   ACB_ADATE,");
                sql.addSql("   ACB_EUID,");
                sql.addSql("   ACB_EDATE");
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

                sql.addIntValue(bean.getAdcSid());
                sql.addLongValue(NullDefault.getLong(binList.get(i), 0));
                sql.addIntValue(bean.getAcbAuid());
                sql.addDateValue(bean.getAcbAdate());
                sql.addIntValue(bean.getAcbEuid());
                sql.addDateValue(bean.getAcbEdate());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}