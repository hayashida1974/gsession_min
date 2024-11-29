package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>ADR_ADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrAddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrAddressDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrAddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrAddressDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table ADR_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table ADR_ADDRESS (");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_SEI varchar(10) not null,");
            sql.addSql("   ADR_MEI varchar(10) not null,");
            sql.addSql("   ADR_SEI_KN varchar(20) not null,");
            sql.addSql("   ADR_MEI_KN varchar(20) not null,");
            sql.addSql("   ADR_SINI varchar(3) not null,");
            sql.addSql("   ACO_SID NUMBER(10,0),");
            sql.addSql("   ABA_SID NUMBER(10,0),");
            sql.addSql("   ADR_SYOZOKU varchar(20),");
            sql.addSql("   APS_SID NUMBER(10,0),");
            sql.addSql("   ADR_MAIL1 varchar(50),");
            sql.addSql("   ADR_MAIL_CMT1 varchar(10),");
            sql.addSql("   ADR_MAIL2 varchar(50),");
            sql.addSql("   ADR_MAIL_CMT2 varchar(10),");
            sql.addSql("   ADR_MAIL3 varchar(50),");
            sql.addSql("   ADR_MAIL_CMT3 varchar(10),");
            sql.addSql("   ADR_POSTNO1 varchar(3),");
            sql.addSql("   ADR_POSTNO2 varchar(4),");
            sql.addSql("   TDF_SID NUMBER(10,0),");
            sql.addSql("   ADR_ADDR1 varchar(100),");
            sql.addSql("   ADR_ADDR2 varchar(100),");
            sql.addSql("   ADR_TEL1 varchar(20),");
            sql.addSql("   ADR_TEL_NAI1 varchar(10),");
            sql.addSql("   ADR_TEL_CMT1 varchar(10),");
            sql.addSql("   ADR_TEL2 varchar(20),");
            sql.addSql("   ADR_TEL_NAI2 varchar(10),");
            sql.addSql("   ADR_TEL_CMT2 varchar(10),");
            sql.addSql("   ADR_TEL3 varchar(20),");
            sql.addSql("   ADR_TEL_NAI3 varchar(10),");
            sql.addSql("   ADR_TEL_CMT3 varchar(10),");
            sql.addSql("   ADR_FAX1 varchar(20),");
            sql.addSql("   ADR_FAX_CMT1 varchar(10),");
            sql.addSql("   ADR_FAX2 varchar(20),");
            sql.addSql("   ADR_FAX_CMT2 varchar(10),");
            sql.addSql("   ADR_FAX3 varchar(20),");
            sql.addSql("   ADR_FAX_CMT3 varchar(10),");
            sql.addSql("   ADR_BIKO varchar(1000),");
            sql.addSql("   ADR_PERMIT_VIEW NUMBER(10,0),");
            sql.addSql("   ADR_PERMIT_EDIT NUMBER(10,0),");
            sql.addSql("   ADR_AUID NUMBER(10,0) not null,");
            sql.addSql("   ADR_ADATE varchar(23) not null,");
            sql.addSql("   ADR_EUID NUMBER(10,0) not null,");
            sql.addSql("   ADR_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADR_SID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert ADR_ADDRESS Data Bindding JavaBean
     * @param bean ADR_ADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_ADDRESS(");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdrSid());
            sql.addStrValue(bean.getAdrSei());
            sql.addStrValue(bean.getAdrMei());
            sql.addStrValue(bean.getAdrSeiKn());
            sql.addStrValue(bean.getAdrMeiKn());
            sql.addStrValue(bean.getAdrSini());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addStrValue(bean.getAdrSyozoku());
            sql.addIntValue(bean.getApsSid());
            sql.addStrValue(bean.getAdrMail1());
            sql.addStrValue(bean.getAdrMailCmt1());
            sql.addStrValue(bean.getAdrMail2());
            sql.addStrValue(bean.getAdrMailCmt2());
            sql.addStrValue(bean.getAdrMail3());
            sql.addStrValue(bean.getAdrMailCmt3());
            sql.addStrValue(bean.getAdrPostno1());
            sql.addStrValue(bean.getAdrPostno2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getAdrAddr1());
            sql.addStrValue(bean.getAdrAddr2());
            sql.addStrValue(bean.getAdrTel1());
            sql.addStrValue(bean.getAdrTelNai1());
            sql.addStrValue(bean.getAdrTelCmt1());
            sql.addStrValue(bean.getAdrTel2());
            sql.addStrValue(bean.getAdrTelNai2());
            sql.addStrValue(bean.getAdrTelCmt2());
            sql.addStrValue(bean.getAdrTel3());
            sql.addStrValue(bean.getAdrTelNai3());
            sql.addStrValue(bean.getAdrTelCmt3());
            sql.addStrValue(bean.getAdrFax1());
            sql.addStrValue(bean.getAdrFaxCmt1());
            sql.addStrValue(bean.getAdrFax2());
            sql.addStrValue(bean.getAdrFaxCmt2());
            sql.addStrValue(bean.getAdrFax3());
            sql.addStrValue(bean.getAdrFaxCmt3());
            sql.addStrValue(bean.getAdrBiko());
            sql.addIntValue(bean.getAdrPermitView());
            sql.addIntValue(bean.getAdrPermitEdit());
            sql.addIntValue(bean.getAdrAuid());
            sql.addDateValue(bean.getAdrAdate());
            sql.addIntValue(bean.getAdrEuid());
            sql.addDateValue(bean.getAdrEdate());
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
     * <p>Update ADR_ADDRESS Data Bindding JavaBean
     * @param bean ADR_ADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" set ");
            sql.addSql("   ADR_SEI=?,");
            sql.addSql("   ADR_MEI=?,");
            sql.addSql("   ADR_SEI_KN=?,");
            sql.addSql("   ADR_MEI_KN=?,");
            sql.addSql("   ADR_SINI=?,");
            sql.addSql("   ACO_SID=?,");
            sql.addSql("   ABA_SID=?,");
            sql.addSql("   ADR_SYOZOKU=?,");
            sql.addSql("   APS_SID=?,");
            sql.addSql("   ADR_MAIL1=?,");
            sql.addSql("   ADR_MAIL_CMT1=?,");
            sql.addSql("   ADR_MAIL2=?,");
            sql.addSql("   ADR_MAIL_CMT2=?,");
            sql.addSql("   ADR_MAIL3=?,");
            sql.addSql("   ADR_MAIL_CMT3=?,");
            sql.addSql("   ADR_POSTNO1=?,");
            sql.addSql("   ADR_POSTNO2=?,");
            sql.addSql("   TDF_SID=?,");
            sql.addSql("   ADR_ADDR1=?,");
            sql.addSql("   ADR_ADDR2=?,");
            sql.addSql("   ADR_TEL1=?,");
            sql.addSql("   ADR_TEL_NAI1=?,");
            sql.addSql("   ADR_TEL_CMT1=?,");
            sql.addSql("   ADR_TEL2=?,");
            sql.addSql("   ADR_TEL_NAI2=?,");
            sql.addSql("   ADR_TEL_CMT2=?,");
            sql.addSql("   ADR_TEL3=?,");
            sql.addSql("   ADR_TEL_NAI3=?,");
            sql.addSql("   ADR_TEL_CMT3=?,");
            sql.addSql("   ADR_FAX1=?,");
            sql.addSql("   ADR_FAX_CMT1=?,");
            sql.addSql("   ADR_FAX2=?,");
            sql.addSql("   ADR_FAX_CMT2=?,");
            sql.addSql("   ADR_FAX3=?,");
            sql.addSql("   ADR_FAX_CMT3=?,");
            sql.addSql("   ADR_BIKO=?,");
            sql.addSql("   ADR_PERMIT_VIEW=?,");
            sql.addSql("   ADR_PERMIT_EDIT=?,");
            sql.addSql("   ADR_EUID=?,");
            sql.addSql("   ADR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAdrSei());
            sql.addStrValue(bean.getAdrMei());
            sql.addStrValue(bean.getAdrSeiKn());
            sql.addStrValue(bean.getAdrMeiKn());
            sql.addStrValue(bean.getAdrSini());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addStrValue(bean.getAdrSyozoku());
            sql.addIntValue(bean.getApsSid());
            sql.addStrValue(bean.getAdrMail1());
            sql.addStrValue(bean.getAdrMailCmt1());
            sql.addStrValue(bean.getAdrMail2());
            sql.addStrValue(bean.getAdrMailCmt2());
            sql.addStrValue(bean.getAdrMail3());
            sql.addStrValue(bean.getAdrMailCmt3());
            sql.addStrValue(bean.getAdrPostno1());
            sql.addStrValue(bean.getAdrPostno2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getAdrAddr1());
            sql.addStrValue(bean.getAdrAddr2());
            sql.addStrValue(bean.getAdrTel1());
            sql.addStrValue(bean.getAdrTelNai1());
            sql.addStrValue(bean.getAdrTelCmt1());
            sql.addStrValue(bean.getAdrTel2());
            sql.addStrValue(bean.getAdrTelNai2());
            sql.addStrValue(bean.getAdrTelCmt2());
            sql.addStrValue(bean.getAdrTel3());
            sql.addStrValue(bean.getAdrTelNai3());
            sql.addStrValue(bean.getAdrTelCmt3());
            sql.addStrValue(bean.getAdrFax1());
            sql.addStrValue(bean.getAdrFaxCmt1());
            sql.addStrValue(bean.getAdrFax2());
            sql.addStrValue(bean.getAdrFaxCmt2());
            sql.addStrValue(bean.getAdrFax3());
            sql.addStrValue(bean.getAdrFaxCmt3());
            sql.addStrValue(bean.getAdrBiko());
            sql.addIntValue(bean.getAdrPermitView());
            sql.addIntValue(bean.getAdrPermitEdit());
            sql.addIntValue(bean.getAdrEuid());
            sql.addDateValue(bean.getAdrEdate());
            //where
            sql.addIntValue(bean.getAdrSid());

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
     * <p>Select ADR_ADDRESS All Data
     * @return List in ADR_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrAddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrAddressModel> ret = new ArrayList<AdrAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrAddressFromRs(rs));
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
     * <p>Select ADR_ADDRESS
     * @param adrSid ADR_SID
     * @return ADR_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public AdrAddressModel select(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrAddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrAddressFromRs(rs);
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
     * <p>Select ADR_ADDRESS
     * @param sids ADR_SID
     * @return ADR_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrAddressModel> select(Collection<Integer> sids) throws SQLException {
        Connection con = null;
        List<AdrAddressModel> ret = new ArrayList<>();
        if (sids == null || sids.isEmpty()) {
            return ret;
        }

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = sids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   ADR_SID,");
        sb.append("   ADR_SEI,");
        sb.append("   ADR_MEI,");
        sb.append("   ADR_SEI_KN,");
        sb.append("   ADR_MEI_KN,");
        sb.append("   ADR_SINI,");
        sb.append("   ACO_SID,");
        sb.append("   ABA_SID,");
        sb.append("   ADR_SYOZOKU,");
        sb.append("   APS_SID,");
        sb.append("   ADR_MAIL1,");
        sb.append("   ADR_MAIL_CMT1,");
        sb.append("   ADR_MAIL2,");
        sb.append("   ADR_MAIL_CMT2,");
        sb.append("   ADR_MAIL3,");
        sb.append("   ADR_MAIL_CMT3,");
        sb.append("   ADR_POSTNO1,");
        sb.append("   ADR_POSTNO2,");
        sb.append("   TDF_SID,");
        sb.append("   ADR_ADDR1,");
        sb.append("   ADR_ADDR2,");
        sb.append("   ADR_TEL1,");
        sb.append("   ADR_TEL_NAI1,");
        sb.append("   ADR_TEL_CMT1,");
        sb.append("   ADR_TEL2,");
        sb.append("   ADR_TEL_NAI2,");
        sb.append("   ADR_TEL_CMT2,");
        sb.append("   ADR_TEL3,");
        sb.append("   ADR_TEL_NAI3,");
        sb.append("   ADR_TEL_CMT3,");
        sb.append("   ADR_FAX1,");
        sb.append("   ADR_FAX_CMT1,");
        sb.append("   ADR_FAX2,");
        sb.append("   ADR_FAX_CMT2,");
        sb.append("   ADR_FAX3,");
        sb.append("   ADR_FAX_CMT3,");
        sb.append("   ADR_BIKO,");
        sb.append("   ADR_PERMIT_VIEW,");
        sb.append("   ADR_PERMIT_EDIT,");
        sb.append("   ADR_AUID,");
        sb.append("   ADR_ADATE,");
        sb.append("   ADR_EUID,");
        sb.append("   ADR_EDATE");
        sb.append(" from");
        sb.append("   ADR_ADDRESS");
        sb.append(" where ");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" ADR_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        ret.add(__getAdrAddressFromRs(rs));
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }

    /**
     * <br>[機  能] アドレス帳情報が存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid ADR_SID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] 特定役職のカウントを取る
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param posSid 役職SID
     * @return cnt 該当件数
     * @throws SQLException SQL実行例外
     */
    public int selectPosCount(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   APS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(posSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt;
    }

    /**
     * <p>Delete ADR_ADDRESS
     * @param adrSid ADR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

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
     * <br>[機  能] 役職を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param posSid 役職SID
     * @throws SQLException SQL実行例外
     * @return con 更新件数
     */
    public int updatePos(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" set");
            sql.addSql("   APS_SID=?");
            sql.addSql(" where");
            sql.addSql("   APS_SID=?");

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
     * <br>[機  能] 指定されたアドレス帳IDの一覧を取得する(閲覧権限チェック)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adrIdList アドレス帳ID一覧
     * @param usrSid セッションユーザSID
     * @param adminFlg 管理者フラグ
     * @return ret アドレス帳リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrAddressModel> selectViewAdrList(String[] adrIdList,
                                                    int usrSid, boolean adminFlg)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<AdrAddressModel> ret = new ArrayList<AdrAddressModel>();
        if (adrIdList == null || adrIdList.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");

            sql.addSql("   ADR_SID in (");

            int idx = 0;
            for (String strAdrId : adrIdList) {
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(strAdrId));

                if (idx < adrIdList.length - 1) {
                    sql.addSql(",");
                }

                idx += 1;
            }

            sql.addSql("   )");
            if (!adminFlg) {
                sql.addSql(" and");

                //閲覧権限
                sql.addSql("   (");
                sql.addSql("     ADR_ADDRESS.ADR_PERMIT_VIEW = ?");

                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql("       ADR_ADDRESS.ADR_PERMIT_VIEW = ?");
                sql.addSql("       and");
                sql.addSql("       ADR_ADDRESS.ADR_SID in (");
                sql.addSql("         select ADR_SID from ADR_PERSONCHARGE");
                sql.addSql("         where USR_SID = ?");
                sql.addSql("       )");
                sql.addSql("     )");

                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql("       ADR_ADDRESS.ADR_PERMIT_VIEW = ?");
                sql.addSql("       and");
                sql.addSql("       ADR_ADDRESS.ADR_SID in (");
                sql.addSql("         select ADR_SID from ADR_PERMIT_VIEW");
                sql.addSql("         where");
                sql.addSql("           GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where USR_SID = ?");
                sql.addSql("           )");
                sql.addSql("       )");
                sql.addSql("     )");

                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql("       ADR_ADDRESS.ADR_PERMIT_VIEW = ?");
                sql.addSql("       and");
                sql.addSql("       ADR_ADDRESS.ADR_SID in (");
                sql.addSql("         select ADR_SID from ADR_PERMIT_VIEW");
                sql.addSql("         where USR_SID = ?");
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addSql("   )");

                sql.addIntValue(GSConstAddress.EDITPERMIT_NORESTRICTION);
                sql.addIntValue(GSConstAddress.EDITPERMIT_OWN);
                sql.addIntValue(usrSid);
                sql.addIntValue(GSConstAddress.EDITPERMIT_GROUP);
                sql.addIntValue(usrSid);
                sql.addIntValue(GSConstAddress.EDITPERMIT_USER);
                sql.addIntValue(usrSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrAddressFromRs(rs));
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
     * <br>[機  能] 指定されたアドレス帳IDの一覧を取得する（編集権限チェック）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adrIdList アドレス帳ID一覧
     * @param usrSid セッションユーザSID
     * @param adminFlg 管理者フラグ
     * @return ret アドレス帳リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrAddressModel> selectAdrList(String[] adrIdList,
                                                    int usrSid, boolean adminFlg)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<AdrAddressModel> ret = new ArrayList<AdrAddressModel>();
        if (adrIdList == null || adrIdList.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");

            sql.addSql("   ADR_SID in (");

            int idx = 0;
            for (String strAdrId : adrIdList) {
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(strAdrId));

                if (idx < adrIdList.length - 1) {
                    sql.addSql(",");
                }

                idx += 1;
            }

            sql.addSql("   )");
            if (!adminFlg) {
                sql.addSql(" and");

                //閲覧権限
                sql.addSql("   (");
                sql.addSql("     ADR_ADDRESS.ADR_PERMIT_EDIT = ?");

                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql("       ADR_ADDRESS.ADR_PERMIT_EDIT = ?");
                sql.addSql("       and");
                sql.addSql("       ADR_ADDRESS.ADR_SID in (");
                sql.addSql("         select ADR_SID from ADR_PERSONCHARGE");
                sql.addSql("         where USR_SID = ?");
                sql.addSql("       )");
                sql.addSql("     )");

                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql("       ADR_ADDRESS.ADR_PERMIT_EDIT = ?");
                sql.addSql("       and");
                sql.addSql("       ADR_ADDRESS.ADR_SID in (");
                sql.addSql("         select ADR_SID from ADR_PERMIT_EDIT");
                sql.addSql("         where");
                sql.addSql("           GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where USR_SID = ?");
                sql.addSql("           )");
                sql.addSql("       )");
                sql.addSql("     )");

                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql("       ADR_ADDRESS.ADR_PERMIT_EDIT = ?");
                sql.addSql("       and");
                sql.addSql("       ADR_ADDRESS.ADR_SID in (");
                sql.addSql("         select ADR_SID from ADR_PERMIT_EDIT");
                sql.addSql("         where USR_SID = ?");
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addSql("   )");

                sql.addIntValue(GSConstAddress.EDITPERMIT_NORESTRICTION);
                sql.addIntValue(GSConstAddress.EDITPERMIT_OWN);
                sql.addIntValue(usrSid);
                sql.addIntValue(GSConstAddress.EDITPERMIT_GROUP);
                sql.addIntValue(usrSid);
                sql.addIntValue(GSConstAddress.EDITPERMIT_USER);
                sql.addIntValue(usrSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrAddressFromRs(rs));
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
     * <br>[機  能] 指定されたアドレス帳IDに該当するアドレス帳情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adrIdList アドレス帳ID一覧
     * @return ret アドレス帳情報リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrAddressModel> selAdrList(String[] adrIdList)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<AdrAddressModel> ret = new ArrayList<AdrAddressModel>();
        if (adrIdList == null || adrIdList.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");

            sql.addSql("   ADR_SID in (");

            int idx = 0;
            for (String strAdrId : adrIdList) {
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(strAdrId));

                if (idx < adrIdList.length - 1) {
                    sql.addSql(",");
                }

                idx += 1;
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrAddressFromRs(rs));
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
     * <br>[機  能] 指定された会社ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param selectUsr 選択済ユーザ
     * @param myAdrSid 自分自身のアドレスSID
     * @param acoSid 会社SID
     * @param usrSid セッションユーザSID
     * @return ret アドレス帳リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrAddressModel> selectAcoUsrList(String[] selectUsr,
                                                       int myAdrSid,
                                                       int acoSid,
                                                       int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<AdrAddressModel> ret = new ArrayList<AdrAddressModel>();
        if (acoSid < 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_SEI_KN,");
            sql.addSql("   ADR_MEI_KN,");
            sql.addSql("   ADR_SINI,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ADR_SYOZOKU,");
            sql.addSql("   APS_SID,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL_CMT1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL_CMT2,");
            sql.addSql("   ADR_MAIL3,");
            sql.addSql("   ADR_MAIL_CMT3,");
            sql.addSql("   ADR_POSTNO1,");
            sql.addSql("   ADR_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ADR_ADDR1,");
            sql.addSql("   ADR_ADDR2,");
            sql.addSql("   ADR_TEL1,");
            sql.addSql("   ADR_TEL_NAI1,");
            sql.addSql("   ADR_TEL_CMT1,");
            sql.addSql("   ADR_TEL2,");
            sql.addSql("   ADR_TEL_NAI2,");
            sql.addSql("   ADR_TEL_CMT2,");
            sql.addSql("   ADR_TEL3,");
            sql.addSql("   ADR_TEL_NAI3,");
            sql.addSql("   ADR_TEL_CMT3,");
            sql.addSql("   ADR_FAX1,");
            sql.addSql("   ADR_FAX_CMT1,");
            sql.addSql("   ADR_FAX2,");
            sql.addSql("   ADR_FAX_CMT2,");
            sql.addSql("   ADR_FAX3,");
            sql.addSql("   ADR_FAX_CMT3,");
            sql.addSql("   ADR_BIKO,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   ADR_AUID,");
            sql.addSql("   ADR_ADATE,");
            sql.addSql("   ADR_EUID,");
            sql.addSql("   ADR_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID = ?");
            sql.addSql(" and");
            sql.addIntValue(acoSid);

            sql.addSql("   ADR_SID not in (");
            sql.addSql("?");
            sql.addIntValue(myAdrSid);

            if (selectUsr != null && selectUsr.length > 0) {
                sql.addSql(",");
                int idx = 0;
                for (String strAdrSid : selectUsr) {
                    sql.addSql("?");
                    sql.addIntValue(Integer.parseInt(strAdrSid));
                    if (idx < selectUsr.length - 1) {
                        sql.addSql(",");
                    }
                    idx += 1;
                }
            }

            sql.addSql("   )");
            sql.addSql(" and");

            //閲覧権限
            AddressDao.addViewableWhereSQL(sql, usrSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrAddressFromRs(rs));
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
     * <br>[機  能] アドレス情報に指定した会社が設定されている場合、会社を未設定に変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param acoSid 会社SID
     * @param userSid ユーザSID
     * @param date 日時
     * @throws SQLException SQL実行例外
     */
    public void resetCompany(int acoSid, int userSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" set");
            sql.addSql("   ACO_SID=?,");
            sql.addSql("   ABA_SID=?,");
            sql.addSql("   ADR_EUID=?,");
            sql.addSql("   ADR_EDATE=?");
            sql.addSql(" where");
            sql.addSql("   ACO_SID=?");
            sql.addIntValue(0);
            sql.addIntValue(0);
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            sql.addIntValue(acoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create ADR_ADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrAddressModel
     * @throws SQLException SQL実行例外
     */
    private AdrAddressModel __getAdrAddressFromRs(ResultSet rs) throws SQLException {
        AdrAddressModel bean = new AdrAddressModel();
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdrSei(rs.getString("ADR_SEI"));
        bean.setAdrMei(rs.getString("ADR_MEI"));
        bean.setAdrSeiKn(rs.getString("ADR_SEI_KN"));
        bean.setAdrMeiKn(rs.getString("ADR_MEI_KN"));
        bean.setAdrSini(rs.getString("ADR_SINI"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setAdrSyozoku(rs.getString("ADR_SYOZOKU"));
        bean.setApsSid(rs.getInt("APS_SID"));
        bean.setAdrMail1(rs.getString("ADR_MAIL1"));
        bean.setAdrMailCmt1(rs.getString("ADR_MAIL_CMT1"));
        bean.setAdrMail2(rs.getString("ADR_MAIL2"));
        bean.setAdrMailCmt2(rs.getString("ADR_MAIL_CMT2"));
        bean.setAdrMail3(rs.getString("ADR_MAIL3"));
        bean.setAdrMailCmt3(rs.getString("ADR_MAIL_CMT3"));
        bean.setAdrPostno1(rs.getString("ADR_POSTNO1"));
        bean.setAdrPostno2(rs.getString("ADR_POSTNO2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setAdrAddr1(rs.getString("ADR_ADDR1"));
        bean.setAdrAddr2(rs.getString("ADR_ADDR2"));
        bean.setAdrTel1(rs.getString("ADR_TEL1"));
        bean.setAdrTelNai1(rs.getString("ADR_TEL_NAI1"));
        bean.setAdrTelCmt1(rs.getString("ADR_TEL_CMT1"));
        bean.setAdrTel2(rs.getString("ADR_TEL2"));
        bean.setAdrTelNai2(rs.getString("ADR_TEL_NAI2"));
        bean.setAdrTelCmt2(rs.getString("ADR_TEL_CMT2"));
        bean.setAdrTel3(rs.getString("ADR_TEL3"));
        bean.setAdrTelNai3(rs.getString("ADR_TEL_NAI3"));
        bean.setAdrTelCmt3(rs.getString("ADR_TEL_CMT3"));
        bean.setAdrFax1(rs.getString("ADR_FAX1"));
        bean.setAdrFaxCmt1(rs.getString("ADR_FAX_CMT1"));
        bean.setAdrFax2(rs.getString("ADR_FAX2"));
        bean.setAdrFaxCmt2(rs.getString("ADR_FAX_CMT2"));
        bean.setAdrFax3(rs.getString("ADR_FAX3"));
        bean.setAdrFaxCmt3(rs.getString("ADR_FAX_CMT3"));
        bean.setAdrBiko(rs.getString("ADR_BIKO"));
        bean.setAdrPermitView(rs.getInt("ADR_PERMIT_VIEW"));
        bean.setAdrPermitEdit(rs.getInt("ADR_PERMIT_EDIT"));
        bean.setAdrAuid(rs.getInt("ADR_AUID"));
        bean.setAdrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADR_ADATE")));
        bean.setAdrEuid(rs.getInt("ADR_EUID"));
        bean.setAdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADR_EDATE")));
        return bean;
    }
}
