package jp.groupsession.v2.wml.dao;

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
import jp.groupsession.v2.wml.model.WmlAddressBookModel;

/**
     * <br>[機  能] アドレス帳情報を取得するDaoクラス
     * <br>[解  説]
     * <br>[備  考]
     */
public class WmlGetAddressBookDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlGetAddressBookDao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public WmlGetAddressBookDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アドレス帳情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return アドレス帳情報
     * @throws SQLException SQL実行時例外
     */
    public WmlAddressBookModel getAddressBookData(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAddressBookModel addressData = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql = getAddresData(sql);
            sql.addSql(" where");
            sql.addSql("   ADR_ADDRESS.ADR_SID = ?");
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                addressData = __getAddressBookFromRs(rs);
                return addressData;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addressData;
    }

    /**
     * <br>[機  能] アドレス帳情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return アドレス帳情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlAddressBookModel> getAddressBookData(String[] adrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAddressBookModel> ret = new ArrayList<WmlAddressBookModel>();
        con = getCon();
        if (adrSid == null) {
            return ret;
        }
        if (adrSid.length < 1) {
            return ret;
        }
        try {
          //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql = getAddresData(sql);
            sql.addSql(" where ");
            sql.addSql("   ADR_ADDRESS.ADR_SID in ( ");

            for (int i = 0; i < adrSid.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(adrSid[i]));
            }
            sql.addSql("        )");
            sql.addSql(" order by");
            sql.addSql("   ADR_ADDRESS.ADR_SEI_KN asc,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI_KN asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAddressBookFromRs(rs));
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
     * <br>[機  能]アドレス帳情報を取得する共通ロジック
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQL
     * @return アドレス帳情報取得に必要なSQL
     * @throws SQLException SQL実行時例外
     */
    protected SqlBuffer getAddresData(SqlBuffer sql) throws SQLException {
            sql.addSql(" select ");
            sql.addSql("   ADR_ADDRESS.ADR_SID as ADR_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI as ADR_SEI,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI as ADR_MEI,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI_KN as ADR_SEI_KN,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI_KN as ADR_MEI_KN,");
            sql.addSql("   ADR_ADDRESS.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_ADDRESS.ABA_SID as ABA_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL1 as ADR_MAIL1,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL2 as ADR_MAIL2,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL3 as ADR_MAIL3,");
            sql.addSql("   ADR_ADDRESS.APS_SID as APS_SID,");
            sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("   ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("   ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
            sql.addSql("   (case");
            sql.addSql("      when ADR_ADDRESS.APS_SID= -1 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   ADR_POSITION.APS_NAME as APS_NAME,");
            sql.addSql("   ADR_POSITION.APS_SORT as APS_SORT");
            sql.addSql(" from ");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql("   left join");
            sql.addSql("     ADR_COMPANY");
            sql.addSql("   on");
            sql.addSql("     ADR_ADDRESS.ACO_SID = ADR_COMPANY.ACO_SID");
            sql.addSql("   left join");
            sql.addSql("     ADR_COMPANY_BASE");
            sql.addSql("   on");
            sql.addSql("     ADR_ADDRESS.ABA_SID = ADR_COMPANY_BASE.ABA_SID");
            sql.addSql("   left join");
            sql.addSql("     ADR_POSITION");
            sql.addSql("   on");
            sql.addSql("     ADR_ADDRESS.APS_SID = ADR_POSITION.APS_SID");
        return sql;
    }
    /**
     * <p>Create ADR_ADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created Wml280AddressBookModel
     * @throws SQLException SQL実行例外
     */
    private WmlAddressBookModel __getAddressBookFromRs(ResultSet rs)
            throws SQLException {
        WmlAddressBookModel bean = new WmlAddressBookModel();
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdrSei(rs.getString("ADR_SEI"));
        bean.setAdrMei(rs.getString("ADR_MEI"));
        bean.setAdrSeiKn(rs.getString("ADR_SEI_KN"));
        bean.setAdrMeiKn(rs.getString("ADR_MEI_KN"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setAdrMail1(rs.getString("ADR_MAIL1"));
        bean.setAdrMail2(rs.getString("ADR_MAIL2"));
        bean.setAdrMail3(rs.getString("ADR_MAIL3"));
        bean.setAcoName(rs.getString("ACO_NAME"));
        bean.setAcoNameKn(rs.getString("ACO_NAME_KN"));
        bean.setAcoId(rs.getString("ACO_CODE"));
        bean.setAbaName(rs.getString("ABA_NAME"));
        bean.setAbaType(rs.getString("ABA_TYPE"));
        bean.setYakusyokuSid(rs.getInt("APS_SID"));
        bean.setYakusyoku(rs.getString("APS_NAME"));
        bean.setYakusyokuSort(rs.getInt("APS_SORT"));
        bean.setYakusyokuFlg(rs.getInt("YAKUSYOKU_EXIST"));
        return bean;
    }
}
