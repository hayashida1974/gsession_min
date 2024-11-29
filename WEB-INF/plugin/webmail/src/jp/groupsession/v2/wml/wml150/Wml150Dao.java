package jp.groupsession.v2.wml.wml150;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール アカウント基本設定画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml150Dao.class);

    /**
     * <p>Default Constructor
     */
    public Wml150Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml150Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定した転送アドレス制限文字に該当しないフィルター情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fwTextArray 転送アドレス制限文字
     * @return フィルター情報
     * @throws SQLException SQL実行時例外
     */
    public List<Wml150FwCheckModel> getFwCheckList(String[] fwTextArray) throws SQLException {
        List<Wml150FwCheckModel> filterList = new ArrayList<Wml150FwCheckModel>();
        if (fwTextArray == null || fwTextArray.length == 0) {
            return filterList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_FILTER.WFT_SID as WFT_SID,");
            sql.addSql("   WML_FILTER.WFT_NAME as WFT_NAME,");
            sql.addSql("   WML_FILTER.WFT_TYPE as WFT_TYPE,");
            sql.addSql("   WML_FILTER.WAC_SID as WAC_SID,");
            sql.addSql("   WML_FILTER_FWADDRESS.WFA_ADDRESS as WFA_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME,");
            sql.addSql("   USRDATA.USR_SID as USR_SID,");
            sql.addSql("   USRDATA.USI_SEI as USI_SEI,");
            sql.addSql("   USRDATA.USI_MEI as USI_MEI,");
            sql.addSql("   USRDATA.USR_JKBN as USR_JKBN");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_FWADDRESS,");
            sql.addSql("   WML_FILTER");
            sql.addSql("   left join");
            sql.addSql("     WML_ACCOUNT");
            sql.addSql("   on");
            sql.addSql("     WML_FILTER.WAC_SID = WML_ACCOUNT.WAC_SID");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select");
            sql.addSql("        CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("        CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("        CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("        CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql("      from");
            sql.addSql("        CMN_USRM,");
            sql.addSql("        CMN_USRM_INF");
            sql.addSql("      where");
            sql.addSql("        CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("     ) USRDATA");
            sql.addSql("   on");
            sql.addSql("     WML_FILTER.USR_SID = USRDATA.USR_SID");
            sql.addSql(" where");
            sql.addSql("   WML_FILTER.WFT_ACTION_FORWARD = 1");
            sql.addSql(" and");
            sql.addSql("   WML_FILTER.WFT_SID = WML_FILTER_FWADDRESS.WFT_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            for (int idx = 0; idx < fwTextArray.length; idx++) {
                if (idx > 0) {
                    sql.addSql("   and");
                }
                sql.addSql("     WFA_ADDRESS not like '%"
                                + JDBCUtil.escapeForLikeSearch(fwTextArray[idx])
                                + "%'");
            }
            sql.addSql("   )");

            sql.addSql(" order by");
            sql.addSql("   WML_ACCOUNT.WAC_NAME asc,");
            sql.addSql("   WML_FILTER.WFT_NAME asc,");
            sql.addSql("   WML_FILTER.WFT_SID asc");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml150FwCheckModel filterData = new Wml150FwCheckModel();
                filterData.setWftSid(rs.getInt("WFT_SID"));
                filterData.setFilterName(rs.getString("WFT_NAME"));
                filterData.setWftType(rs.getInt("WFT_TYPE"));
                filterData.setFwAddress(rs.getString("WFA_ADDRESS"));
                filterData.setWacSid(rs.getInt("WAC_SID"));
                filterData.setWacName(rs.getString("WAC_NAME"));
                filterData.setUserSid(rs.getInt("USR_SID"));
                filterData.setUserNameSei(rs.getString("USI_SEI"));
                filterData.setUserNameMei(rs.getString("USI_MEI"));
                filterData.setUsrJkbn(rs.getInt("USR_JKBN"));

                filterList.add(filterData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return filterList;
    }

    /**
     * <br>[機  能] 全てのフィルター転送設定を未設定に変更
     * <br>[解  説] 動作_指定アドレスに転送する を 0:未設定へ更新
     * <br>[備  考]
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int clearFilterActonForward() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_FILTER");
            sql.addSql(" set WFT_ACTION_FORWARD = ?");
            sql.addIntValue(GSConstWebmail.FILTER_FORWARD_NOSET);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
}
