package jp.groupsession.v2.adr.adr161;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>アドレス帳 コンタクト履歴画面で使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.1
 */
public class Adr161BinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr161BinDao.class);

    /**
     * <p>Default Constructor
     */
    public Adr161BinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr161BinDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] コンタクト履歴SIDからバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrTmpSid コンタクト履歴SID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List < CmnBinfModel > getAddressTmpFileList(int adrTmpSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   ADR_CONTACT_BIN.ADC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ADR_CONTACT_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0");

            sql.addIntValue(adrTmpSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                //添付ファイルサイズ設定(表示用)
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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