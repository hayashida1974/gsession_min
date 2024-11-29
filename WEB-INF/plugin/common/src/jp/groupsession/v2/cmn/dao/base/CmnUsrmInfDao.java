package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>CMN_USRM_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrmInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrmInfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrmInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrmInfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_USRM_INF Data Bindding JavaBean
     * @param bean CMN_USRM_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrmInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USRM_INF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   USI_SEI,");
            sql.addSql("   USI_MEI,");
            sql.addSql("   USI_SEI_KN,");
            sql.addSql("   USI_MEI_KN,");
            sql.addSql("   USI_SINI,");
            sql.addSql("   USI_BDATE,");
            sql.addSql("   USI_ZIP1,");
            sql.addSql("   USI_ZIP2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   USI_ADDR1,");
            sql.addSql("   USI_ADDR2,");
            sql.addSql("   USI_TEL1,");
            sql.addSql("   USI_TEL_NAI1,");
            sql.addSql("   USI_TEL_CMT1,");
            sql.addSql("   USI_TEL2,");
            sql.addSql("   USI_TEL_NAI2,");
            sql.addSql("   USI_TEL_CMT2,");
            sql.addSql("   USI_TEL3,");
            sql.addSql("   USI_TEL_NAI3,");
            sql.addSql("   USI_TEL_CMT3,");
            sql.addSql("   USI_FAX1,");
            sql.addSql("   USI_FAX_CMT1,");
            sql.addSql("   USI_FAX2,");
            sql.addSql("   USI_FAX_CMT2,");
            sql.addSql("   USI_FAX3,");
            sql.addSql("   USI_FAX_CMT3,");
            sql.addSql("   USI_MAIL1,");
            sql.addSql("   USI_MAIL_CMT1,");
            sql.addSql("   USI_MAIL2,");
            sql.addSql("   USI_MAIL_CMT2,");
            sql.addSql("   USI_MAIL3,");
            sql.addSql("   USI_MAIL_CMT3,");
            sql.addSql("   USI_SYAIN_NO,");
            sql.addSql("   USI_SYOZOKU,");
            sql.addSql("   USI_YAKUSYOKU,");
            sql.addSql("   USI_SEIBETU,");
            sql.addSql("   USI_ENTRANCE_DATE,");
            sql.addSql("   USI_SORTKEY1,");
            sql.addSql("   USI_SORTKEY2,");
            sql.addSql("   POS_SID,");
            sql.addSql("   USI_BIKO,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USI_PICT_KF,");
            sql.addSql("   USI_BDATE_KF,");
            sql.addSql("   USI_MAIL1_KF,");
            sql.addSql("   USI_MAIL2_KF,");
            sql.addSql("   USI_MAIL3_KF,");
            sql.addSql("   USI_ZIP_KF,");
            sql.addSql("   USI_TDF_KF,");
            sql.addSql("   USI_ADDR1_KF,");
            sql.addSql("   USI_ADDR2_KF,");
            sql.addSql("   USI_TEL1_KF,");
            sql.addSql("   USI_TEL2_KF,");
            sql.addSql("   USI_TEL3_KF,");
            sql.addSql("   USI_FAX1_KF,");
            sql.addSql("   USI_FAX2_KF,");
            sql.addSql("   USI_FAX3_KF,");
            sql.addSql("   USI_MBL_USE,");
            sql.addSql("   USI_AUID,");
            sql.addSql("   USI_ADATE,");
            sql.addSql("   USI_EUID,");
            sql.addSql("   USI_EDATE,");
            sql.addSql("   USI_NUM_CONT,");
            sql.addSql("   USI_NUM_AUTADD,");
            sql.addSql("   USI_LTLGIN,");
            sql.addSql("   USI_OTPSEND_ADDRESS");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getUsiSei());
            sql.addStrValue(bean.getUsiMei());
            sql.addStrValue(bean.getUsiSeiKn());
            sql.addStrValue(bean.getUsiMeiKn());
            sql.addStrValue(bean.getUsiSini());
            sql.addDateValue(bean.getUsiBdate());
            sql.addStrValue(bean.getUsiZip1());
            sql.addStrValue(bean.getUsiZip2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getUsiAddr1());
            sql.addStrValue(bean.getUsiAddr2());
            sql.addStrValue(bean.getUsiTel1());
            sql.addStrValue(bean.getUsiTelNai1());
            sql.addStrValue(bean.getUsiTelCmt1());
            sql.addStrValue(bean.getUsiTel2());
            sql.addStrValue(bean.getUsiTelNai2());
            sql.addStrValue(bean.getUsiTelCmt2());
            sql.addStrValue(bean.getUsiTel3());
            sql.addStrValue(bean.getUsiTelNai3());
            sql.addStrValue(bean.getUsiTelCmt3());
            sql.addStrValue(bean.getUsiFax1());
            sql.addStrValue(bean.getUsiFaxCmt1());
            sql.addStrValue(bean.getUsiFax2());
            sql.addStrValue(bean.getUsiFaxCmt2());
            sql.addStrValue(bean.getUsiFax3());
            sql.addStrValue(bean.getUsiFaxCmt3());
            sql.addStrValue(bean.getUsiMail1());
            sql.addStrValue(bean.getUsiMailCmt1());
            sql.addStrValue(bean.getUsiMail2());
            sql.addStrValue(bean.getUsiMailCmt2());
            sql.addStrValue(bean.getUsiMail3());
            sql.addStrValue(bean.getUsiMailCmt3());
            sql.addStrValue(bean.getUsiSyainNo());
            sql.addStrValue(bean.getUsiSyozoku());
            sql.addStrValue(bean.getUsiYakusyoku());
            sql.addIntValue(bean.getUsiSeibetu());
            sql.addDateValue(bean.getUsiEntranceDate());
            sql.addStrValue(bean.getUsiSortkey1());
            sql.addStrValue(bean.getUsiSortkey2());
            sql.addIntValue(bean.getPosSid());
            sql.addStrValue(bean.getUsiBiko());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getUsiPictKf());
            sql.addIntValue(bean.getUsiBdateKf());
            sql.addIntValue(bean.getUsiMail1Kf());
            sql.addIntValue(bean.getUsiMail2Kf());
            sql.addIntValue(bean.getUsiMail3Kf());
            sql.addIntValue(bean.getUsiZipKf());
            sql.addIntValue(bean.getUsiTdfKf());
            sql.addIntValue(bean.getUsiAddr1Kf());
            sql.addIntValue(bean.getUsiAddr2Kf());
            sql.addIntValue(bean.getUsiTel1Kf());
            sql.addIntValue(bean.getUsiTel2Kf());
            sql.addIntValue(bean.getUsiTel3Kf());
            sql.addIntValue(bean.getUsiFax1Kf());
            sql.addIntValue(bean.getUsiFax2Kf());
            sql.addIntValue(bean.getUsiFax3Kf());
            sql.addIntValue(bean.getUsiMblUse());
            sql.addIntValue(bean.getUsiAuid());
            sql.addDateValue(bean.getUsiAdate());
            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
            sql.addIntValue(bean.getUsiNumCont());
            sql.addIntValue(bean.getUsiNumAutadd());
            sql.addDateValue(bean.getUsiLtlgin());
            sql.addStrValue(bean.getUsiOtpsendAddress());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CMN_USRM_INF Data Bindding JavaBean
     * @param bean CMN_USRM_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCmnUserInf(CmnUsrmInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   USI_SEI=?,");
            sql.addSql("   USI_MEI=?,");
            sql.addSql("   USI_SEI_KN=?,");
            sql.addSql("   USI_MEI_KN=?,");
            sql.addSql("   USI_SINI=?,");
            sql.addSql("   USI_BDATE=?,");
            sql.addSql("   USI_ZIP1=?,");
            sql.addSql("   USI_ZIP2=?,");
            sql.addSql("   TDF_SID=?,");
            sql.addSql("   USI_ADDR1=?,");
            sql.addSql("   USI_ADDR2=?,");
            sql.addSql("   USI_TEL1=?,");
            sql.addSql("   USI_TEL_NAI1=?,");
            sql.addSql("   USI_TEL_CMT1=?,");
            sql.addSql("   USI_TEL2=?,");
            sql.addSql("   USI_TEL_NAI2=?,");
            sql.addSql("   USI_TEL_CMT2=?,");
            sql.addSql("   USI_TEL3=?,");
            sql.addSql("   USI_TEL_NAI3=?,");
            sql.addSql("   USI_TEL_CMT3=?,");
            sql.addSql("   USI_FAX1=?,");
            sql.addSql("   USI_FAX_CMT1=?,");
            sql.addSql("   USI_FAX2=?,");
            sql.addSql("   USI_FAX_CMT2=?,");
            sql.addSql("   USI_FAX3=?,");
            sql.addSql("   USI_FAX_CMT3=?,");
            sql.addSql("   USI_MAIL1=?,");
            sql.addSql("   USI_MAIL_CMT1=?,");
            sql.addSql("   USI_MAIL2=?,");
            sql.addSql("   USI_MAIL_CMT2=?,");
            sql.addSql("   USI_MAIL3=?,");
            sql.addSql("   USI_MAIL_CMT3=?,");
            sql.addSql("   USI_SYAIN_NO=?,");
            sql.addSql("   USI_SYOZOKU=?,");
            sql.addSql("   USI_YAKUSYOKU=?,");
            sql.addSql("   USI_SEIBETU=?,");
            sql.addSql("   USI_ENTRANCE_DATE=?,");
            sql.addSql("   USI_SORTKEY1=?,");
            sql.addSql("   USI_SORTKEY2=?,");
            sql.addSql("   POS_SID=?,");
            sql.addSql("   USI_BIKO=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   USI_PICT_KF=?,");
            sql.addSql("   USI_BDATE_KF=?,");
            sql.addSql("   USI_MAIL1_KF=?,");
            sql.addSql("   USI_MAIL2_KF=?,");
            sql.addSql("   USI_MAIL3_KF=?,");
            sql.addSql("   USI_ZIP_KF=?,");
            sql.addSql("   USI_TDF_KF=?,");
            sql.addSql("   USI_ADDR1_KF=?,");
            sql.addSql("   USI_ADDR2_KF=?,");
            sql.addSql("   USI_TEL1_KF=?,");
            sql.addSql("   USI_TEL2_KF=?,");
            sql.addSql("   USI_TEL3_KF=?,");
            sql.addSql("   USI_FAX1_KF=?,");
            sql.addSql("   USI_FAX2_KF=?,");
            sql.addSql("   USI_FAX3_KF=?,");
            sql.addSql("   USI_EUID=?,");
            sql.addSql("   USI_EDATE=?,");
            sql.addSql("   USI_OTPSEND_ADDRESS=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getUsiSei());
            sql.addStrValue(bean.getUsiMei());
            sql.addStrValue(bean.getUsiSeiKn());
            sql.addStrValue(bean.getUsiMeiKn());
            sql.addStrValue(bean.getUsiSini());
            sql.addDateValue(bean.getUsiBdate());
            sql.addStrValue(bean.getUsiZip1());
            sql.addStrValue(bean.getUsiZip2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getUsiAddr1());
            sql.addStrValue(bean.getUsiAddr2());
            sql.addStrValue(bean.getUsiTel1());
            sql.addStrValue(bean.getUsiTelNai1());
            sql.addStrValue(bean.getUsiTelCmt1());
            sql.addStrValue(bean.getUsiTel2());
            sql.addStrValue(bean.getUsiTelNai2());
            sql.addStrValue(bean.getUsiTelCmt2());
            sql.addStrValue(bean.getUsiTel3());
            sql.addStrValue(bean.getUsiTelNai3());
            sql.addStrValue(bean.getUsiTelCmt3());
            sql.addStrValue(bean.getUsiFax1());
            sql.addStrValue(bean.getUsiFaxCmt1());
            sql.addStrValue(bean.getUsiFax2());
            sql.addStrValue(bean.getUsiFaxCmt2());
            sql.addStrValue(bean.getUsiFax3());
            sql.addStrValue(bean.getUsiFaxCmt3());
            sql.addStrValue(bean.getUsiMail1());
            sql.addStrValue(bean.getUsiMailCmt1());
            sql.addStrValue(bean.getUsiMail2());
            sql.addStrValue(bean.getUsiMailCmt2());
            sql.addStrValue(bean.getUsiMail3());
            sql.addStrValue(bean.getUsiMailCmt3());
            sql.addStrValue(bean.getUsiSyainNo());
            sql.addStrValue(bean.getUsiSyozoku());
            sql.addStrValue(bean.getUsiYakusyoku());
            sql.addIntValue(bean.getUsiSeibetu());
            sql.addDateValue(bean.getUsiEntranceDate());
            sql.addStrValue(bean.getUsiSortkey1());
            sql.addStrValue(bean.getUsiSortkey2());
            sql.addIntValue(bean.getPosSid());
            sql.addStrValue(bean.getUsiBiko());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getUsiPictKf());
            sql.addIntValue(bean.getUsiBdateKf());
            sql.addIntValue(bean.getUsiMail1Kf());
            sql.addIntValue(bean.getUsiMail2Kf());
            sql.addIntValue(bean.getUsiMail3Kf());
            sql.addIntValue(bean.getUsiZipKf());
            sql.addIntValue(bean.getUsiTdfKf());
            sql.addIntValue(bean.getUsiAddr1Kf());
            sql.addIntValue(bean.getUsiAddr2Kf());
            sql.addIntValue(bean.getUsiTel1Kf());
            sql.addIntValue(bean.getUsiTel2Kf());
            sql.addIntValue(bean.getUsiTel3Kf());
            sql.addIntValue(bean.getUsiFax1Kf());
            sql.addIntValue(bean.getUsiFax2Kf());
            sql.addIntValue(bean.getUsiFax3Kf());
            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
            sql.addStrValue(bean.getUsiOtpsendAddress());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>バイナリSID、写真公開フラグ以外のユーザ情報を更新する
     *
     * @param bean CMN_USRM_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCmnUserInfNoBinSid(CmnUsrmInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            if (bean.getUsiSei().length() != 0) {
                sql.addSql("   USI_SEI=?,");
            }
            if (bean.getUsiMei().length() != 0) {
                sql.addSql("   USI_MEI=?,");
            }
            if (bean.getUsiSeiKn().length() != 0) {
                sql.addSql("   USI_SEI_KN=?,");
            }
            if (bean.getUsiMeiKn().length() != 0) {
                sql.addSql("   USI_MEI_KN=?,");
            }
            if (bean.getUsiSini().length() != 0) {
                sql.addSql("   USI_SINI=?,");
            }
            sql.addSql("   USI_BDATE=?,");
            sql.addSql("   USI_ZIP1=?,");
            sql.addSql("   USI_ZIP2=?,");
            sql.addSql("   TDF_SID=?,");
            sql.addSql("   USI_ADDR1=?,");
            sql.addSql("   USI_ADDR2=?,");
            sql.addSql("   USI_TEL1=?,");
            sql.addSql("   USI_TEL_NAI1=?,");
            sql.addSql("   USI_TEL_CMT1=?,");
            sql.addSql("   USI_TEL2=?,");
            sql.addSql("   USI_TEL_NAI2=?,");
            sql.addSql("   USI_TEL_CMT2=?,");
            sql.addSql("   USI_TEL3=?,");
            sql.addSql("   USI_TEL_NAI3=?,");
            sql.addSql("   USI_TEL_CMT3=?,");
            sql.addSql("   USI_FAX1=?,");
            sql.addSql("   USI_FAX_CMT1=?,");
            sql.addSql("   USI_FAX2=?,");
            sql.addSql("   USI_FAX_CMT2=?,");
            sql.addSql("   USI_FAX3=?,");
            sql.addSql("   USI_FAX_CMT3=?,");
            sql.addSql("   USI_MAIL1=?,");
            sql.addSql("   USI_MAIL_CMT1=?,");
            sql.addSql("   USI_MAIL2=?,");
            sql.addSql("   USI_MAIL_CMT2=?,");
            sql.addSql("   USI_MAIL3=?,");
            sql.addSql("   USI_MAIL_CMT3=?,");
            sql.addSql("   USI_SYAIN_NO=?,");
            sql.addSql("   USI_SYOZOKU=?,");
            sql.addSql("   USI_YAKUSYOKU=?,");
            sql.addSql("   USI_SEIBETU=?,");
            sql.addSql("   USI_ENTRANCE_DATE=?,");
            sql.addSql("   USI_SORTKEY1=?,");
            sql.addSql("   USI_SORTKEY2=?,");
            sql.addSql("   POS_SID=?,");
            sql.addSql("   USI_BIKO=?,");
            sql.addSql("   USI_BDATE_KF=?,");
            sql.addSql("   USI_MAIL1_KF=?,");
            sql.addSql("   USI_MAIL2_KF=?,");
            sql.addSql("   USI_MAIL3_KF=?,");
            sql.addSql("   USI_ZIP_KF=?,");
            sql.addSql("   USI_TDF_KF=?,");
            sql.addSql("   USI_ADDR1_KF=?,");
            sql.addSql("   USI_ADDR2_KF=?,");
            sql.addSql("   USI_TEL1_KF=?,");
            sql.addSql("   USI_TEL2_KF=?,");
            sql.addSql("   USI_TEL3_KF=?,");
            sql.addSql("   USI_FAX1_KF=?,");
            sql.addSql("   USI_FAX2_KF=?,");
            sql.addSql("   USI_FAX3_KF=?,");
            sql.addSql("   USI_EUID=?,");
            sql.addSql("   USI_EDATE=?,");
            sql.addSql("   USI_OTPSEND_ADDRESS=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            if (bean.getUsiSei().length() != 0) {
                sql.addStrValue(bean.getUsiSei());
            }
            if (bean.getUsiMei().length() != 0) {
                sql.addStrValue(bean.getUsiMei());
            }
            if (bean.getUsiSeiKn().length() != 0) {
                sql.addStrValue(bean.getUsiSeiKn());
            }
            if (bean.getUsiMeiKn().length() != 0) {
                sql.addStrValue(bean.getUsiMeiKn());
            }
            if (bean.getUsiSini().length() != 0) {
                sql.addStrValue(bean.getUsiSini());
            }
            sql.addDateValue(bean.getUsiBdate());
            sql.addStrValue(bean.getUsiZip1());
            sql.addStrValue(bean.getUsiZip2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getUsiAddr1());
            sql.addStrValue(bean.getUsiAddr2());
            sql.addStrValue(bean.getUsiTel1());
            sql.addStrValue(bean.getUsiTelNai1());
            sql.addStrValue(bean.getUsiTelCmt1());
            sql.addStrValue(bean.getUsiTel2());
            sql.addStrValue(bean.getUsiTelNai2());
            sql.addStrValue(bean.getUsiTelCmt2());
            sql.addStrValue(bean.getUsiTel3());
            sql.addStrValue(bean.getUsiTelNai3());
            sql.addStrValue(bean.getUsiTelCmt3());
            sql.addStrValue(bean.getUsiFax1());
            sql.addStrValue(bean.getUsiFaxCmt1());
            sql.addStrValue(bean.getUsiFax2());
            sql.addStrValue(bean.getUsiFaxCmt2());
            sql.addStrValue(bean.getUsiFax3());
            sql.addStrValue(bean.getUsiFaxCmt3());
            sql.addStrValue(bean.getUsiMail1());
            sql.addStrValue(bean.getUsiMailCmt1());
            sql.addStrValue(bean.getUsiMail2());
            sql.addStrValue(bean.getUsiMailCmt2());
            sql.addStrValue(bean.getUsiMail3());
            sql.addStrValue(bean.getUsiMailCmt3());
            sql.addStrValue(bean.getUsiSyainNo());
            sql.addStrValue(bean.getUsiSyozoku());
            sql.addStrValue(bean.getUsiYakusyoku());
            sql.addIntValue(bean.getUsiSeibetu());
            sql.addDateValue(bean.getUsiEntranceDate());
            sql.addStrValue(bean.getUsiSortkey1());
            sql.addStrValue(bean.getUsiSortkey2());
            sql.addIntValue(bean.getPosSid());
            sql.addStrValue(bean.getUsiBiko());
            sql.addIntValue(bean.getUsiBdateKf());
            sql.addIntValue(bean.getUsiMail1Kf());
            sql.addIntValue(bean.getUsiMail2Kf());
            sql.addIntValue(bean.getUsiMail3Kf());
            sql.addIntValue(bean.getUsiZipKf());
            sql.addIntValue(bean.getUsiTdfKf());
            sql.addIntValue(bean.getUsiAddr1Kf());
            sql.addIntValue(bean.getUsiAddr2Kf());
            sql.addIntValue(bean.getUsiTel1Kf());
            sql.addIntValue(bean.getUsiTel2Kf());
            sql.addIntValue(bean.getUsiTel3Kf());
            sql.addIntValue(bean.getUsiFax1Kf());
            sql.addIntValue(bean.getUsiFax2Kf());
            sql.addIntValue(bean.getUsiFax3Kf());
            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
            sql.addStrValue(bean.getUsiOtpsendAddress());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Update CMN_USRM_INF Data Bindding JavaBean
     * @param bean CMN_USRM_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCmnUserInfWithMblKbn(CmnUsrmInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   USI_SEI=?,");
            sql.addSql("   USI_MEI=?,");
            sql.addSql("   USI_SEI_KN=?,");
            sql.addSql("   USI_MEI_KN=?,");
            sql.addSql("   USI_SINI=?,");
            sql.addSql("   USI_BDATE=?,");
            sql.addSql("   USI_ZIP1=?,");
            sql.addSql("   USI_ZIP2=?,");
            sql.addSql("   TDF_SID=?,");
            sql.addSql("   USI_ADDR1=?,");
            sql.addSql("   USI_ADDR2=?,");
            sql.addSql("   USI_TEL1=?,");
            sql.addSql("   USI_TEL_NAI1=?,");
            sql.addSql("   USI_TEL_CMT1=?,");
            sql.addSql("   USI_TEL2=?,");
            sql.addSql("   USI_TEL_NAI2=?,");
            sql.addSql("   USI_TEL_CMT2=?,");
            sql.addSql("   USI_TEL3=?,");
            sql.addSql("   USI_TEL_NAI3=?,");
            sql.addSql("   USI_TEL_CMT3=?,");
            sql.addSql("   USI_FAX1=?,");
            sql.addSql("   USI_FAX_CMT1=?,");
            sql.addSql("   USI_FAX2=?,");
            sql.addSql("   USI_FAX_CMT2=?,");
            sql.addSql("   USI_FAX3=?,");
            sql.addSql("   USI_FAX_CMT3=?,");
            sql.addSql("   USI_MAIL1=?,");
            sql.addSql("   USI_MAIL_CMT1=?,");
            sql.addSql("   USI_MAIL2=?,");
            sql.addSql("   USI_MAIL_CMT2=?,");
            sql.addSql("   USI_MAIL3=?,");
            sql.addSql("   USI_MAIL_CMT3=?,");
            sql.addSql("   USI_SYAIN_NO=?,");
            sql.addSql("   USI_SYOZOKU=?,");
            sql.addSql("   USI_YAKUSYOKU=?,");
            sql.addSql("   USI_SEIBETU=?,");
            sql.addSql("   USI_ENTRANCE_DATE=?,");
            sql.addSql("   USI_SORTKEY1=?,");
            sql.addSql("   USI_SORTKEY2=?,");
            sql.addSql("   POS_SID=?,");
            sql.addSql("   USI_BIKO=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   USI_PICT_KF=?,");
            sql.addSql("   USI_BDATE_KF=?,");
            sql.addSql("   USI_MAIL1_KF=?,");
            sql.addSql("   USI_MAIL2_KF=?,");
            sql.addSql("   USI_MAIL3_KF=?,");
            sql.addSql("   USI_ZIP_KF=?,");
            sql.addSql("   USI_TDF_KF=?,");
            sql.addSql("   USI_ADDR1_KF=?,");
            sql.addSql("   USI_ADDR2_KF=?,");
            sql.addSql("   USI_TEL1_KF=?,");
            sql.addSql("   USI_TEL2_KF=?,");
            sql.addSql("   USI_TEL3_KF=?,");
            sql.addSql("   USI_FAX1_KF=?,");
            sql.addSql("   USI_FAX2_KF=?,");
            sql.addSql("   USI_FAX3_KF=?,");
            sql.addSql("   USI_MBL_USE=?,");
            sql.addSql("   USI_EUID=?,");
            sql.addSql("   USI_EDATE=?,");
            sql.addSql("   USI_NUM_CONT=?,");
            sql.addSql("   USI_NUM_AUTADD=?,");
            sql.addSql("   USI_OTPSEND_ADDRESS=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getUsiSei());
            sql.addStrValue(bean.getUsiMei());
            sql.addStrValue(bean.getUsiSeiKn());
            sql.addStrValue(bean.getUsiMeiKn());
            sql.addStrValue(bean.getUsiSini());
            sql.addDateValue(bean.getUsiBdate());
            sql.addStrValue(bean.getUsiZip1());
            sql.addStrValue(bean.getUsiZip2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getUsiAddr1());
            sql.addStrValue(bean.getUsiAddr2());
            sql.addStrValue(bean.getUsiTel1());
            sql.addStrValue(bean.getUsiTelNai1());
            sql.addStrValue(bean.getUsiTelCmt1());
            sql.addStrValue(bean.getUsiTel2());
            sql.addStrValue(bean.getUsiTelNai2());
            sql.addStrValue(bean.getUsiTelCmt2());
            sql.addStrValue(bean.getUsiTel3());
            sql.addStrValue(bean.getUsiTelNai3());
            sql.addStrValue(bean.getUsiTelCmt3());
            sql.addStrValue(bean.getUsiFax1());
            sql.addStrValue(bean.getUsiFaxCmt1());
            sql.addStrValue(bean.getUsiFax2());
            sql.addStrValue(bean.getUsiFaxCmt2());
            sql.addStrValue(bean.getUsiFax3());
            sql.addStrValue(bean.getUsiFaxCmt3());
            sql.addStrValue(bean.getUsiMail1());
            sql.addStrValue(bean.getUsiMailCmt1());
            sql.addStrValue(bean.getUsiMail2());
            sql.addStrValue(bean.getUsiMailCmt2());
            sql.addStrValue(bean.getUsiMail3());
            sql.addStrValue(bean.getUsiMailCmt3());
            sql.addStrValue(bean.getUsiSyainNo());
            sql.addStrValue(bean.getUsiSyozoku());
            sql.addStrValue(bean.getUsiYakusyoku());
            sql.addIntValue(bean.getUsiSeibetu());
            sql.addDateValue(bean.getUsiEntranceDate());
            sql.addStrValue(bean.getUsiSortkey1());
            sql.addStrValue(bean.getUsiSortkey2());
            sql.addIntValue(bean.getPosSid());
            sql.addStrValue(bean.getUsiBiko());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getUsiPictKf());
            sql.addIntValue(bean.getUsiBdateKf());
            sql.addIntValue(bean.getUsiMail1Kf());
            sql.addIntValue(bean.getUsiMail2Kf());
            sql.addIntValue(bean.getUsiMail3Kf());
            sql.addIntValue(bean.getUsiZipKf());
            sql.addIntValue(bean.getUsiTdfKf());
            sql.addIntValue(bean.getUsiAddr1Kf());
            sql.addIntValue(bean.getUsiAddr2Kf());
            sql.addIntValue(bean.getUsiTel1Kf());
            sql.addIntValue(bean.getUsiTel2Kf());
            sql.addIntValue(bean.getUsiTel3Kf());
            sql.addIntValue(bean.getUsiFax1Kf());
            sql.addIntValue(bean.getUsiFax2Kf());
            sql.addIntValue(bean.getUsiFax3Kf());
            sql.addIntValue(bean.getUsiMblUse());
            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
            sql.addIntValue(bean.getUsiNumCont());
            sql.addIntValue(bean.getUsiNumAutadd());
            sql.addStrValue(bean.getUsiOtpsendAddress());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <p>指定されたSID分のモバイル情報を更新します
     * @param usids ユーザーSID 配列
     * @param bean CMN_USRM_INF Model
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMblKbn(String[] usids, CmnUsrmInfModel bean)
        throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   USI_EUID=?,");
            sql.addSql("   USI_EDATE=?,");
            sql.addSql("   USI_MBL_USE=?,");
            sql.addSql("   USI_NUM_CONT=?,");
            sql.addSql("   USI_NUM_AUTADD=?");

            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
            sql.addIntValue(bean.getUsiMblUse());
            sql.addIntValue(bean.getUsiNumCont());
            sql.addIntValue(bean.getUsiNumAutadd());

            if (usids != null) {
                sql.addSql(" where ");
                sql.addSql("   USR_SID in (");

                for (int i = 0; i < usids.length; i++) {
                    if (i > 0) {
                        sql.addSql("     , ");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(usids[i]));
                }
                sql.addSql("   )");
            }
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <br>[機  能] 役職SIDを指定し、該当するユーザの役職SIDをクリアする
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int crearPosSid(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   POS_SID = ?");
            sql.addSql(" where ");
            sql.addSql("   POS_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.POS_DEFAULT);
            sql.addIntValue(posSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * 指定したグループSIDかつ役職SIDを持つユーザSIDを取得します。
     * @param grpSid グループSID
     * @param posSid 役職SID
     * @return ユーザSIDリスト
     * @throws SQLException SQL実行例外
     * */
    public ArrayList<Integer> getBelongUsrsFromPosition(int grpSid, int posSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.POS_SID=?");
            sql.addIntValue(posSid);
            if (grpSid > -1) {
                sql.addSql(" and ");
                sql.addSql("   CMN_USRM_INF.USR_SID");
                sql.addSql(" in(");
                sql.addSql("  select  ");
                sql.addSql("      CMN_BELONGM.USR_SID ");
                sql.addSql("  from");
                sql.addSql("      CMN_BELONGM");
                sql.addSql("  where");
                sql.addSql("      CMN_BELONGM.GRP_SID=?");
                sql.addSql(" )");
                sql.addIntValue(grpSid);
            }
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN=?");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID=CMN_USRM.USR_SID");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <p>Update CMN_USRM_INF Data Bindding JavaBean
     * @param bean CMN_USRM_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCmnUserPct(CmnUsrmInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   USI_EUID=?,");
            sql.addSql("   USI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

//    /**
//     * <p>Select CMN_USRM_INF All Data
//     * @return List in CMN_USRM_INFModel
//     * @throws SQLException SQL実行例外
//     */
//    public List<CmnUsrmInfModel> select() throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select ");
//            sql.addSql("   USR_SID,");
//            sql.addSql("   USI_SEI,");
//            sql.addSql("   USI_MEI,");
//            sql.addSql("   USI_SEI_KN,");
//            sql.addSql("   USI_MEI_KN,");
//            sql.addSql("   USI_SINI,");
//            sql.addSql("   USI_BDATE,");
//            sql.addSql("   USI_ZIP1,");
//            sql.addSql("   USI_ZIP2,");
//            sql.addSql("   TDF_SID,");
//            sql.addSql("   USI_ADDR1,");
//            sql.addSql("   USI_ADDR2,");
//            sql.addSql("   USI_TEL1,");
//            sql.addSql("   USI_TEL_NAI1,");
//            sql.addSql("   USI_TEL_CMT1,");
//            sql.addSql("   USI_TEL2,");
//            sql.addSql("   USI_TEL_NAI2,");
//            sql.addSql("   USI_TEL_CMT2,");
//            sql.addSql("   USI_TEL3,");
//            sql.addSql("   USI_TEL_NAI3,");
//            sql.addSql("   USI_TEL_CMT3,");
//            sql.addSql("   USI_FAX1,");
//            sql.addSql("   USI_FAX_CMT1,");
//            sql.addSql("   USI_FAX2,");
//            sql.addSql("   USI_FAX_CMT2,");
//            sql.addSql("   USI_FAX3,");
//            sql.addSql("   USI_FAX_CMT3,");
//            sql.addSql("   USI_MAIL1,");
//            sql.addSql("   USI_MAIL_CMT1,");
//            sql.addSql("   USI_MAIL2,");
//            sql.addSql("   USI_MAIL_CMT2,");
//            sql.addSql("   USI_MAIL3,");
//            sql.addSql("   USI_MAIL_CMT3,");
//            sql.addSql("   USI_SYAIN_NO,");
//            sql.addSql("   USI_SYOZOKU,");
//            sql.addSql("   USI_YAKUSYOKU,");
//            sql.addSql("   POS_SID,");
//            sql.addSql("   USI_BIKO,");
//            sql.addSql("   BIN_SID,");
//            sql.addSql("   USI_PICT_KF,");
//            sql.addSql("   USI_BDATE_KF,");
//            sql.addSql("   USI_MAIL1_KF,");
//            sql.addSql("   USI_MAIL2_KF,");
//            sql.addSql("   USI_MAIL3_KF,");
//            sql.addSql("   USI_ZIP_KF,");
//            sql.addSql("   USI_TDF_KF,");
//            sql.addSql("   USI_ADDR1_KF,");
//            sql.addSql("   USI_ADDR2_KF,");
//            sql.addSql("   USI_TEL1_KF,");
//            sql.addSql("   USI_TEL2_KF,");
//            sql.addSql("   USI_TEL3_KF,");
//            sql.addSql("   USI_FAX1_KF,");
//            sql.addSql("   USI_FAX2_KF,");
//            sql.addSql("   USI_FAX3_KF,");
//            sql.addSql("   USI_LTLGIN,");
//            sql.addSql("   USI_AUID,");
//            sql.addSql("   USI_ADATE,");
//            sql.addSql("   USI_EUID,");
//            sql.addSql("   USI_EDATE");
//            sql.addSql(" from ");
//            sql.addSql("   CMN_USRM_INF");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            log__.info(sql.toLogString());
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ret.add(__getCmnUsrmInfFromRs(rs));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }

    /**
     * <p>Select CMN_USRM_INF
     * @param bean CMN_USRM_INF Model
     * @return CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel select(CmnUsrmInfModel bean) throws SQLException {
        return select(bean.getUsrSid());
    }
    /**
     * <p>Select CMN_USRM_INF
     * @return CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   USI_SEI,");
            sql.addSql("   USI_MEI,");
            sql.addSql("   USI_SEI_KN,");
            sql.addSql("   USI_MEI_KN,");
            sql.addSql("   USI_SINI,");
            sql.addSql("   USI_BDATE,");
            sql.addSql("   USI_ZIP1,");
            sql.addSql("   USI_ZIP2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   USI_ADDR1,");
            sql.addSql("   USI_ADDR2,");
            sql.addSql("   USI_TEL1,");
            sql.addSql("   USI_TEL_NAI1,");
            sql.addSql("   USI_TEL_CMT1,");
            sql.addSql("   USI_TEL2,");
            sql.addSql("   USI_TEL_NAI2,");
            sql.addSql("   USI_TEL_CMT2,");
            sql.addSql("   USI_TEL3,");
            sql.addSql("   USI_TEL_NAI3,");
            sql.addSql("   USI_TEL_CMT3,");
            sql.addSql("   USI_FAX1,");
            sql.addSql("   USI_FAX_CMT1,");
            sql.addSql("   USI_FAX2,");
            sql.addSql("   USI_FAX_CMT2,");
            sql.addSql("   USI_FAX3,");
            sql.addSql("   USI_FAX_CMT3,");
            sql.addSql("   USI_MAIL1,");
            sql.addSql("   USI_MAIL_CMT1,");
            sql.addSql("   USI_MAIL2,");
            sql.addSql("   USI_MAIL_CMT2,");
            sql.addSql("   USI_MAIL3,");
            sql.addSql("   USI_MAIL_CMT3,");
            sql.addSql("   USI_SYAIN_NO,");
            sql.addSql("   USI_SYOZOKU,");
            sql.addSql("   USI_YAKUSYOKU,");
            sql.addSql("   USI_SEIBETU,");
            sql.addSql("   USI_ENTRANCE_DATE,");
            sql.addSql("   USI_SORTKEY1,");
            sql.addSql("   USI_SORTKEY2,");
            sql.addSql("   POS_SID,");
            sql.addSql("   USI_BIKO,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USI_PICT_KF,");
            sql.addSql("   USI_BDATE_KF,");
            sql.addSql("   USI_MAIL1_KF,");
            sql.addSql("   USI_MAIL2_KF,");
            sql.addSql("   USI_MAIL3_KF,");
            sql.addSql("   USI_ZIP_KF,");
            sql.addSql("   USI_TDF_KF,");
            sql.addSql("   USI_ADDR1_KF,");
            sql.addSql("   USI_ADDR2_KF,");
            sql.addSql("   USI_TEL1_KF,");
            sql.addSql("   USI_TEL2_KF,");
            sql.addSql("   USI_TEL3_KF,");
            sql.addSql("   USI_FAX1_KF,");
            sql.addSql("   USI_FAX2_KF,");
            sql.addSql("   USI_FAX3_KF,");
            sql.addSql("   USI_MBL_USE,");
            sql.addSql("   USI_LTLGIN,");
            sql.addSql("   USI_AUID,");
            sql.addSql("   USI_ADATE,");
            sql.addSql("   USI_EUID,");
            sql.addSql("   USI_EDATE,");
            sql.addSql("   USI_NUM_CONT,");
            sql.addSql("   USI_NUM_AUTADD,");
            sql.addSql("   USI_OTPSEND_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");


            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrmInfFromRs(rs));
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
     * <p>Select CMN_USRM_INF
     * @param usid ユーザSID
     * @return CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel select(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   USI_SEI,");
            sql.addSql("   USI_MEI,");
            sql.addSql("   USI_SEI_KN,");
            sql.addSql("   USI_MEI_KN,");
            sql.addSql("   USI_SINI,");
            sql.addSql("   USI_BDATE,");
            sql.addSql("   USI_ZIP1,");
            sql.addSql("   USI_ZIP2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   USI_ADDR1,");
            sql.addSql("   USI_ADDR2,");
            sql.addSql("   USI_TEL1,");
            sql.addSql("   USI_TEL_NAI1,");
            sql.addSql("   USI_TEL_CMT1,");
            sql.addSql("   USI_TEL2,");
            sql.addSql("   USI_TEL_NAI2,");
            sql.addSql("   USI_TEL_CMT2,");
            sql.addSql("   USI_TEL3,");
            sql.addSql("   USI_TEL_NAI3,");
            sql.addSql("   USI_TEL_CMT3,");
            sql.addSql("   USI_FAX1,");
            sql.addSql("   USI_FAX_CMT1,");
            sql.addSql("   USI_FAX2,");
            sql.addSql("   USI_FAX_CMT2,");
            sql.addSql("   USI_FAX3,");
            sql.addSql("   USI_FAX_CMT3,");
            sql.addSql("   USI_MAIL1,");
            sql.addSql("   USI_MAIL_CMT1,");
            sql.addSql("   USI_MAIL2,");
            sql.addSql("   USI_MAIL_CMT2,");
            sql.addSql("   USI_MAIL3,");
            sql.addSql("   USI_MAIL_CMT3,");
            sql.addSql("   USI_SYAIN_NO,");
            sql.addSql("   USI_SYOZOKU,");
            sql.addSql("   USI_YAKUSYOKU,");
            sql.addSql("   USI_SEIBETU,");
            sql.addSql("   USI_ENTRANCE_DATE,");
            sql.addSql("   USI_SORTKEY1,");
            sql.addSql("   USI_SORTKEY2,");
            sql.addSql("   POS_SID,");
            sql.addSql("   USI_BIKO,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USI_PICT_KF,");
            sql.addSql("   USI_BDATE_KF,");
            sql.addSql("   USI_MAIL1_KF,");
            sql.addSql("   USI_MAIL2_KF,");
            sql.addSql("   USI_MAIL3_KF,");
            sql.addSql("   USI_ZIP_KF,");
            sql.addSql("   USI_TDF_KF,");
            sql.addSql("   USI_ADDR1_KF,");
            sql.addSql("   USI_ADDR2_KF,");
            sql.addSql("   USI_TEL1_KF,");
            sql.addSql("   USI_TEL2_KF,");
            sql.addSql("   USI_TEL3_KF,");
            sql.addSql("   USI_FAX1_KF,");
            sql.addSql("   USI_FAX2_KF,");
            sql.addSql("   USI_FAX3_KF,");
            sql.addSql("   USI_MBL_USE,");
            sql.addSql("   USI_LTLGIN,");
            sql.addSql("   USI_AUID,");
            sql.addSql("   USI_ADATE,");
            sql.addSql("   USI_EUID,");
            sql.addSql("   USI_EDATE,");
            sql.addSql("   USI_NUM_CONT,");
            sql.addSql("   USI_NUM_AUTADD,");
            sql.addSql("   USI_OTPSEND_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrmInfFromRs(rs);
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
     * <p>Select CMN_USRM_INF
     * @param usid ユーザSID
     * @return CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel selectInit(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setBinSid(rs.getLong("BIN_SID"));
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
            sql.addSql("     select");
            sql.addSql("       POS_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_POSITION");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_INF.USI_YAKUSYOKU = CMN_POSITION.POS_NAME");
            sql.addSql("     and");
            sql.addSql("       CMN_POSITION.POS_SORT = (");
            sql.addSql("         select min(POS_SORT) from CMN_POSITION POSITION_SORT");
            sql.addSql("         where CMN_USRM_INF.USI_YAKUSYOKU = POSITION_SORT.POS_NAME");
            sql.addSql("       )");
            sql.addSql("   )");
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

    /**
     * <br>[機  能] ユーザ情報から全ての役職名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getAllPos() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
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

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnUsrmInfModel bean = new CmnUsrmInfModel();
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
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名を取得します。
     * <br>[備  考]
     * @param usids ユーザーSID 配列
     * @param sortMdl ソート情報
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUsersInfList(String[] usids,
            CmnCmbsortConfModel sortMdl) throws SQLException {
        return getUsersInfList(usids, sortMdl, GSConstUser.USER_JTKBN_ACTIVE);
    }
    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名を取得します。
     * <br>[備  考]
     * @param usids ユーザーSID 配列
     * @param sortMdl ソート情報
     * @param jKbn 削除区分
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUsersInfList(String[] usids,
            CmnCmbsortConfModel sortMdl, int jKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        if (usids == null) {
            return ret;
        }
        if (usids.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1 as USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1 as USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2 as USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2 as USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3 as USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3 as USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1 as USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2 as USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3 as USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1 as USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2 as USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3 as USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MBL_USE as USI_MBL_USE,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_CONT as USI_NUM_CONT,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_AUTADD as USI_NUM_AUTADD,");
            sql.addSql("   CMN_USRM_INF.USI_OTPSEND_ADDRESS as USI_OTPSEND_ADDRESS,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID in ( ");

            for (int i = 0; i < usids.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(usids[i]));
            }
            sql.addSql("        )");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            if (jKbn >= GSConstUser.USER_JTKBN_ACTIVE) {
                sql.addSql(" and");
                sql.addSql("   CMN_USRM.USR_JKBN = ?");
                sql.addIntValue(jKbn);
            }
            sql = __setOrderSQL(sql, sortMdl);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnUsrmInfModel umodel = __getCmnUsrmInfFromRs(rs);
                __setUkoFlgFromRs(umodel, rs);
                __setJKbnFromRs(umodel, rs);
                ret.add(umodel);
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
     * <p>指定されたSID分のユーザー情報を取得します
     * @param usids ユーザーSID 配列
     * @param userKeyMap ユーザキーマップ
     * @param sortMdl ソート情報
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMemberEditModel> selectPrjMemberModel(String[] usids,
                                                          HashMap<String, String> userKeyMap,
                                                          CmnCmbsortConfModel sortMdl)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjMemberEditModel> ret = new ArrayList<PrjMemberEditModel>();
        con = getCon();

        if (usids == null) {
            return ret;
        }
        if (usids.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID in ( ");

            for (int i = 0; i < usids.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(usids[i]));
            }
            sql.addSql("        )");
            sql.addSql("   and CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");

            sql = __setOrderSQL(sql, sortMdl);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrjMemberEditModel bean = new PrjMemberEditModel();
                int usrSid = rs.getInt("USR_SID");
                bean.setUsrSid(usrSid);
                bean.setUsiSei(rs.getString("USI_SEI"));
                bean.setUsiMei(rs.getString("USI_MEI"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setMemberKey(userKeyMap.get(String.valueOf(usrSid)));
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
     * <br>[機  能] 指定した役職SIDに所属しているユーザの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @return int ユーザの件数
     * @throws SQLException SQL実行例外
     */
    public int getPosCount(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CMN_USRM.USR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.POS_SID = ?");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(posSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] 指定した役職SIDに所属しているユーザのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @return int ユーザの件数
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getPosUser(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.POS_SID = ?");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(posSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] ユーザ情報から固体識別番号の制御に関する値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ret ユーザ情報モデル
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel selectUidStatus(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CmnUsrmInfModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USI_NUM_CONT,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_AUTADD");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");

            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsiNumCont(rs.getInt("USI_NUM_CONT"));
                ret.setUsiNumAutadd(rs.getInt("USI_NUM_AUTADD"));
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
     * <p>ユーザSIDのリストからユーザ情報一覧を取得する。
     * @param usrSids ユーザSIDのリスト 空またはnullの場合全件取得
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUserList(ArrayList < Integer > usrSids)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN, ");
            sql.addSql("   CMN_USRM_INF.USI_SINI, ");
            sql.addSql("   CMN_USRM_INF.USI_BDATE, ");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1, ");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2, ");
            sql.addSql("   CMN_USRM_INF.TDF_SID, ");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1, ");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2, ");
            sql.addSql("   CMN_USRM_INF.USI_TEL1, ");
            sql.addSql("   CMN_USRM_INF.USI_TEL2, ");
            sql.addSql("   CMN_USRM_INF.USI_TEL3, ");
            sql.addSql("   CMN_USRM_INF.USI_FAX1, ");
            sql.addSql("   CMN_USRM_INF.USI_FAX2, ");
            sql.addSql("   CMN_USRM_INF.USI_FAX3, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3, ");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO, ");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU, ");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1, ");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2, ");
            //sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU, ");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO, ");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN, ");
            sql.addSql("   CMN_USRM_INF.USI_AUID, ");
            sql.addSql("   CMN_USRM_INF.USI_ADATE, ");
            sql.addSql("   CMN_USRM_INF.USI_EUID, ");
            sql.addSql("   CMN_USRM_INF.USI_EDATE ");
            sql.addSql("from");
            sql.addSql("  CMN_USRM, ");
            sql.addSql("  CMN_USRM_INF ");
            sql.addSql("where ");
            sql.addSql("  CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("and ");
            sql.addSql("  CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql("and ");
                sql.addSql("  CMN_USRM.USR_SID in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }
            sql.addSql("order by ");
            sql.addSql("  CMN_USRM_INF.USI_YAKUSYOKU, ");
            sql.addSql("  CMN_USRM_INF.USI_SEI_KN, ");
            sql.addSql("  CMN_USRM_INF.USI_MEI_KN ");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel model = UserSearchDao.getCmnUsrmInfModelFromRs(rs);
                model = __setUkoFlgFromRs(model, rs);
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

    /**
     * <br>[機  能] 固体識別番号自動登録区分を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return cnt 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAutAdd(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set");
            sql.addSql("   USI_NUM_AUTADD = ?,");
            sql.addSql("   USI_EUID = ?,");
            sql.addSql("   USI_EDATE =?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstUser.UID_AUTO_REG_NO);
            sql.addIntValue(usrSid);
            sql.addDateValue(now);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定されたSIDのユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名を取得します。
     * <br>[備  考] 削除済みユーザも取得可能
     * @param usrSid ユーザーSID
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel getUsersInf(int usrSid) throws SQLException {
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(getCon());
        List<CmnUsrmInfModel> ulist = usiDao.getUsersInfList(new String[] {String.valueOf(usrSid)});
        if (ulist.size() <= 0) {
            return null;
        }
        return ulist.get(0);
    }
    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名を取得します。
     * <br>[備  考] 削除済みユーザも取得可能 並び順は名前順固定
     * @param usids ユーザーSID 配列
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getUsersInfList(String[] usids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        if (usids == null) {
            return ret;
        }
        if (usids.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2,");
//            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE,");
            sql.addSql("   CMN_USRM_INF.POS_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql("   left join");
            sql.addSql("     CMN_POSITION");
            sql.addSql("   on");
            sql.addSql("     CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID, ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID in ( ");

            for (int i = 0; i < usids.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(usids[i]));
            }
            sql.addSql("        )");
            sql.addSql("   and CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID  ");
            sql.addSql(" order by");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnUsrmInfModel mdl = getCmnUsrmInfFromRsKoukai(rs);
                __setUkoFlgFromRs(mdl, rs);
                ret.add(mdl);

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
     * <p>ユーザSIDのリストからユーザ情報一覧を取得する。
     * @param usrSids ユーザSIDのリスト 空またはnullの場合全件取得
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUserList(String[] usrSids)
        throws SQLException {
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        ArrayList<Integer> sidList = new ArrayList<Integer>();
        for (String sid : usrSids) {
            sidList.add(Integer.parseInt(sid));
        }
        ret.addAll(getUserList(sidList));
        return ret;
    }

    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名を取得します。
     * <br>[備  考]
     * @param usids ユーザーSID 配列
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getUsersDataList(String[] usids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        if (usids == null || usids.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN, ");
            sql.addSql("   CMN_USRM_INF.USI_SINI, ");
            sql.addSql("   CMN_USRM_INF.USI_BDATE, ");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1, ");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2, ");
            sql.addSql("   CMN_USRM_INF.TDF_SID, ");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1, ");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2, ");
            sql.addSql("   CMN_USRM_INF.USI_TEL1, ");
            sql.addSql("   CMN_USRM_INF.USI_TEL2, ");
            sql.addSql("   CMN_USRM_INF.USI_TEL3, ");
            sql.addSql("   CMN_USRM_INF.USI_FAX1, ");
            sql.addSql("   CMN_USRM_INF.USI_FAX2, ");
            sql.addSql("   CMN_USRM_INF.USI_FAX3, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3, ");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO, ");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU, ");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1, ");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2, ");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU, ");
            sql.addSql("   CMN_USRM_INF.USI_BIKO, ");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN, ");
            sql.addSql("   CMN_USRM_INF.USI_AUID, ");
            sql.addSql("   CMN_USRM_INF.USI_ADATE, ");
            sql.addSql("   CMN_USRM_INF.USI_EUID, ");
            sql.addSql("   CMN_USRM_INF.USI_EDATE ");
            sql.addSql("from");
            sql.addSql("  CMN_USRM, ");
            sql.addSql("  CMN_USRM_INF ");
            sql.addSql("where ");
            sql.addSql("  CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("and ");
            sql.addSql("  CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            if (usids != null && usids.length > 0) {
                sql.addSql("and ");
                sql.addSql("  CMN_USRM_INF.USR_SID in( ");
                for (int i = 0; i < usids.length; i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(Integer.parseInt(usids[i]));
                }
                sql.addSql("  ) ");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel model = UserSearchDao.getCmnUsrmInfModelFromRs(rs);
                model = __setUkoFlgFromRs(model, rs);
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

    /**
     * <br>[機  能] 指定されたSIDのユーザーアドレス情報を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param usids ユーザーSID
     * @return CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel getUserAddressData(int usids)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3, ");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addIntValue(usids);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                ret.setUsiSei(rs.getString("USI_SEI"));
                ret.setUsiMei(rs.getString("USI_MEI"));
                ret.setUsiMail1(rs.getString("USI_MAIL1"));
                ret.setUsiMail2(rs.getString("USI_MAIL2"));
                ret.setUsiMail3(rs.getString("USI_MAIL3"));
                ret.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
                ret.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
                ret.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
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
     * <br>[機  能] パラメータusidに該当するユーザの氏名と状態を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usid ユーザSID
     * @return ret ユーザモデル
     * @throws SQLException SQL実行時例外
     */
    public CmnUsrmInfModel selectUserNameAndJtkbn(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CmnUsrmInfModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_JKBN as jkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as ukoflg,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as sei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as mei");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsiSei(rs.getString("sei"));
                ret.setUsiMei(rs.getString("mei"));
                ret.setUsrJkbn(rs.getInt("jkbn"));
                ret.setUsrUkoFlg(rs.getInt("ukoflg"));
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
     * <br>[機  能] 最終ログイン時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @return 最終ログイン時間
     * @throws SQLException SQL実行例外
     */
    public UDate getLastLoginTime(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate lastLoginTime = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USI_LTLGIN");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                lastLoginTime = UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return lastLoginTime;
    }

    /**
     * <br>[機  能] 指定したバイナリSIDがユーザ情報のもので且つ、公開区分が「公開」であるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @return true:存在する  false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckUserImage(Long binSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addSql(" and ");
            sql.addSql(" ( ");
            sql.addSql("   USI_PICT_KF=?");
            sql.addSql("  or ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(binSid);
            sql.addIntValue(GSConstUser.INDIVIDUAL_INFO_OPEN);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return cnt > 0;
    }
    /**
     *
     * <br>[機  能] ユーザ有効無フラグをリザルトセットから設定
     * <br>[解  説]
     * <br>[備  考]
     * @param umodel ユーザモデル
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrmInfModel __setUkoFlgFromRs(CmnUsrmInfModel umodel,
            ResultSet rs) throws SQLException {
        umodel.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return umodel;
    }

    /**
    *
    * <br>[機  能] ユーザ削除フラグをリザルトセットから設定
    * <br>[解  説]
    * <br>[備  考]
    * @param umodel ユーザモデル
    * @param rs ResultSet
    * @return created CmnUsrmInfModel
    * @throws SQLException SQL実行例外
    */
   private CmnUsrmInfModel __setJKbnFromRs(CmnUsrmInfModel umodel,
           ResultSet rs) throws SQLException {
       umodel.setUsrJkbn(rs.getInt("USR_JKBN"));
       return umodel;
   }

    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrmInfModel __getCmnUsrmInfFromRs(ResultSet rs) throws SQLException {
        CmnUsrmInfModel bean = new CmnUsrmInfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTelNai1(rs.getString("USI_TEL_NAI1"));
        bean.setUsiTelCmt1(rs.getString("USI_TEL_CMT1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTelNai2(rs.getString("USI_TEL_NAI2"));
        bean.setUsiTelCmt2(rs.getString("USI_TEL_CMT2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiTelNai3(rs.getString("USI_TEL_NAI3"));
        bean.setUsiTelCmt3(rs.getString("USI_TEL_CMT3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFaxCmt1(rs.getString("USI_FAX_CMT1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFaxCmt2(rs.getString("USI_FAX_CMT2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiFaxCmt3(rs.getString("USI_FAX_CMT3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMailCmt1(rs.getString("USI_MAIL_CMT1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMailCmt2(rs.getString("USI_MAIL_CMT2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiMailCmt3(rs.getString("USI_MAIL_CMT3"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
            UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setPosSid(rs.getInt("POS_SID"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsiPictKf(rs.getInt("USI_PICT_KF"));
        bean.setUsiBdateKf(rs.getInt("USI_BDATE_KF"));
        bean.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
        bean.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
        bean.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
        bean.setUsiZipKf(rs.getInt("USI_ZIP_KF"));
        bean.setUsiTdfKf(rs.getInt("USI_TDF_KF"));
        bean.setUsiAddr1Kf(rs.getInt("USI_ADDR1_KF"));
        bean.setUsiAddr2Kf(rs.getInt("USI_ADDR2_KF"));
        bean.setUsiTel1Kf(rs.getInt("USI_TEL1_KF"));
        bean.setUsiTel2Kf(rs.getInt("USI_TEL2_KF"));
        bean.setUsiTel3Kf(rs.getInt("USI_TEL3_KF"));
        bean.setUsiFax1Kf(rs.getInt("USI_FAX1_KF"));
        bean.setUsiFax2Kf(rs.getInt("USI_FAX2_KF"));
        bean.setUsiFax3Kf(rs.getInt("USI_FAX3_KF"));
        bean.setUsiMblUse(rs.getInt("USI_MBL_USE"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));
        bean.setUsiNumCont(rs.getInt("USI_NUM_CONT"));
        bean.setUsiNumAutadd(rs.getInt("USI_NUM_AUTADD"));
        bean.setUsiOtpsendAddress(rs.getString("USI_OTPSEND_ADDRESS"));
        return bean;
    }

    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public static CmnUsrmInfModel getCmnUsrmInfFromRsKoukai(ResultSet rs) throws SQLException {
        CmnUsrmInfModel bean = new CmnUsrmInfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTelNai1(rs.getString("USI_TEL_NAI1"));
        bean.setUsiTelCmt1(rs.getString("USI_TEL_CMT1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTelNai2(rs.getString("USI_TEL_NAI2"));
        bean.setUsiTelCmt2(rs.getString("USI_TEL_CMT2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiTelNai3(rs.getString("USI_TEL_NAI3"));
        bean.setUsiTelCmt3(rs.getString("USI_TEL_CMT3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFaxCmt1(rs.getString("USI_FAX_CMT1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFaxCmt2(rs.getString("USI_FAX_CMT2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiFaxCmt3(rs.getString("USI_FAX_CMT3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMailCmt1(rs.getString("USI_MAIL_CMT1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMailCmt2(rs.getString("USI_MAIL_CMT2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiMailCmt3(rs.getString("USI_MAIL_CMT3"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
           UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsiPictKf(rs.getInt("USI_PICT_KF"));
        bean.setUsiBdateKf(rs.getInt("USI_BDATE_KF"));
        bean.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
        bean.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
        bean.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
        bean.setUsiZipKf(rs.getInt("USI_ZIP_KF"));
        bean.setUsiTdfKf(rs.getInt("USI_TDF_KF"));
        bean.setUsiAddr1Kf(rs.getInt("USI_ADDR1_KF"));
        bean.setUsiAddr2Kf(rs.getInt("USI_ADDR2_KF"));
        bean.setUsiTel1Kf(rs.getInt("USI_TEL1_KF"));
        bean.setUsiTel2Kf(rs.getInt("USI_TEL2_KF"));
        bean.setUsiTel3Kf(rs.getInt("USI_TEL3_KF"));
        bean.setUsiFax1Kf(rs.getInt("USI_FAX1_KF"));
        bean.setUsiFax2Kf(rs.getInt("USI_FAX2_KF"));
        bean.setUsiFax3Kf(rs.getInt("USI_FAX3_KF"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));

        /** HTML表示用にメールアドレスを成型 START ****************************/
        String mailAddress1 = bean.getUsiMail1();
        if (mailAddress1 != null) {
            mailAddress1 = StringUtilHtml.transToHTmlWithWbr(
                    StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress1)), 30);
            bean.setMailAddress1(mailAddress1);
        }

        String mailAddress2 = bean.getUsiMail2();
        if (mailAddress2 != null) {
            mailAddress2 = StringUtilHtml.transToHTmlWithWbr(
                    StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress2)), 30);
            bean.setMailAddress2(mailAddress2);
        }

        String mailAddress3 = bean.getUsiMail3();
        if (mailAddress3 != null) {
            mailAddress3 = StringUtilHtml.transToHTmlWithWbr(
                    StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress3)), 30);
            bean.setMailAddress3(mailAddress3);
        }

        /** HTML表示用にメールアドレスを成型 END ****************************/


        //生年月日
        if (bean.getUsiBdateKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiBdate(null);
        }
        //メールアドレス1
        if (bean.getUsiMail1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiMail1(null);
            bean.setUsiMailCmt1(null);
            bean.setMailAddress1(null);
        }
        //メールアドレス2
        if (bean.getUsiMail2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiMail2(null);
            bean.setUsiMailCmt2(null);
            bean.setMailAddress2(null);
        }
        //メールアドレス3
        if (bean.getUsiMail3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiMail3(null);
            bean.setUsiMailCmt3(null);
            bean.setMailAddress3(null);
        }
        //郵便番号
        if (bean.getUsiZipKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiZip1(null);
            bean.setUsiZip2(null);
        }
        //都道府県
        if (bean.getUsiTdfKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setTdfSid(-1);
        }
        //住所1
        if (bean.getUsiAddr1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiAddr1(null);
        }
        //住所2
        if (bean.getUsiAddr2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiAddr2(null);
        }
        //TEL1
        if (bean.getUsiTel1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiTel1(null);
            bean.setUsiTelNai1(null);
            bean.setUsiTelCmt1(null);
        }
        //TEL2
        if (bean.getUsiTel2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiTel2(null);
            bean.setUsiTelNai2(null);
            bean.setUsiTelCmt2(null);
        }
        //TEL3
        if (bean.getUsiTel3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiTel3(null);
            bean.setUsiTelNai3(null);
            bean.setUsiTelCmt3(null);
        }
        //FAX1
        if (bean.getUsiFax1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiFax1(null);
            bean.setUsiFaxCmt1(null);
        }
        //FAX2
        if (bean.getUsiFax2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiFax2(null);
            bean.setUsiFaxCmt2(null);
        }
        //FAX3
        if (bean.getUsiFax3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiFax3(null);
            bean.setUsiFaxCmt3(null);
        }
        return bean;
    }

    /**
     * <br>[機  能] SqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortMdl ソート情報
     * @return SqlBuffer
     */
    private SqlBuffer __setOrderSQL(SqlBuffer sql, CmnCmbsortConfModel sortMdl) {

        sql.addSql(" order by ");

        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   USI_SEI_KN asc,");
                sql.addSql("   USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }
        sql.addSql("  ,USR_SID asc");
        return sql;
    }

    /**
    *
    * <br>[機  能] 指定SIDのデータサイズを返す
    * <br>[解  説]
    * <br>[備  考]
    * @return データサイズ
    * @throws SQLException SQL実行時例外
    */
   public Long getDiskSize() throws SQLException {

       long ret = 0;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql("   sum(octet_length(USI_ADDR1)) as USI_ADDR1,");
           sql.addSql("   sum(octet_length(USI_ADDR2)) as USI_ADDR2,");
           sql.addSql("   sum(octet_length(USI_MAIL1)) as USI_MAIL1,");
           sql.addSql("   sum(octet_length(USI_MAIL2)) as USI_MAIL2,");
           sql.addSql("   sum(octet_length(USI_MAIL3)) as USI_MAIL3,");
           sql.addSql("   sum(octet_length(USI_BIKO)) as USI_BIKO,");
           sql.addSql("   sum(octet_length(USI_OTPSEND_ADDRESS)) as USI_OTPSEND_ADDRESS");
           sql.addSql(" from ");
           sql.addSql("   CMN_USRM_INF ");

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret = rs.getInt("USI_ADDR1");
               ret += rs.getInt("USI_ADDR2");
               ret += rs.getInt("USI_MAIL1");
               ret += rs.getInt("USI_MAIL2");
               ret += rs.getInt("USI_MAIL3");
               ret += rs.getInt("USI_BIKO");
               ret += rs.getInt("USI_OTPSEND_ADDRESS");
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
   *
   * <br>[機  能] 指定SIDのデータサイズを返す
   * <br>[解  説]
   * <br>[備  考]
   * @return データサイズ
   * @throws SQLException SQL実行時例外
   */
  public Long getBelongBinSize() throws SQLException {

      long ret = 0;

      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Connection con = null;
      con = getCon();

      try {
          //SQL文
          SqlBuffer sql = new SqlBuffer();
          sql.addSql(" select ");
          sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as SIZE ");
          sql.addSql(" from ");
          sql.addSql("   CMN_USRM_INF ");
          sql.addSql(" inner join ");
          sql.addSql("   CMN_BINF ");
          sql.addSql(" on ");
          sql.addSql("   CMN_USRM_INF.BIN_SID=CMN_BINF.BIN_SID ");

          pstmt = con.prepareStatement(sql.toSqlString());
          log__.info(sql.toLogString());
          rs = pstmt.executeQuery();
          if (rs.next()) {
              ret = rs.getInt("SIZE");
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
