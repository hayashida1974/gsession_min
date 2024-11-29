package jp.groupsession.v2.convert.convert202.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL();

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] alter tableなどのDBの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void alterTable() throws SQLException {

        //注意/////////////////////////////////////////////////////////
        //レコードが1件もない場合はdrop → create を行う
        //jdbc経由でalter tableを流す時、レコードがないとエラーになる為
        ///////////////////////////////////////////////////////////////

        try {

            //BBS_USERの編集を行う
            __alterBbsUser();

            //SCH_DATAの編集を行う
            __alterSchData();

            //CIR_USERの編集を行う
            __alterCirUser();

            //SCH_PRI_CONFの編集を行う
            __alterSchPriConf();

            //RSV_SIS_YRKの編集を行う
            __alterRsvSisYrk();

            //CMN_USRM_INFの編集を行う
            __alterCmnUsrmInf();

            //SRH_USERの編集を行う
            __alterSrhUser();

            //SRH_ACONFの編集を行う
            __alterSrhAconf();

            //CMN_CONTMの編集を行う
            __alterCmnCntm();

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] BBS_USERの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterBbsUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   BBS_USER");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table BBS_USER");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table BBS_USER");
                sql.addSql("(");
                sql.addSql("        USR_SID            integer not null,");
                sql.addSql("        BUR_FOR_CNT        integer not null,");
                sql.addSql("        BUR_THRE_CNT       integer not null,");
                sql.addSql("        BUR_WRT_CNT        integer not null,");
                sql.addSql("        BUR_NEW_CNT        integer not null,");
                sql.addSql("        BUR_SML_NTF        integer not null,");
                sql.addSql("        BUR_THRE_MAIN_CNT  integer not null,");
                sql.addSql("        BUR_WRTLIST_ORDER  integer not null,");
                sql.addSql("        BUR_AUID           integer not null,");
                sql.addSql("        BUR_ADATE          timestamp not null,");
                sql.addSql("        BUR_EUID           integer not null,");
                sql.addSql("        BUR_EDATE          timestamp not null,");
                sql.addSql("        primary key (USR_SID)");
                sql.addSql(") ");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table ");
                sql.addSql("   BBS_USER add BUR_WRTLIST_ORDER integer default 0 not null");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SCH_DATAの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterSchData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table SCH_DATA");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table SCH_DATA");
                sql.addSql("(");
                sql.addSql("        SCD_SID         integer         not null,");
                sql.addSql("        SCD_USR_SID     integer         not null,");
                sql.addSql("        SCD_GRP_SID     integer                 ,");
                sql.addSql("        SCD_USR_KBN     integer                 ,");
                sql.addSql("        SCD_FR_DATE     timestamp               ,");
                sql.addSql("        SCD_TO_DATE     timestamp               ,");
                sql.addSql("        SCD_DAILY       integer                 ,");
                sql.addSql("        SCD_BGCOLOR     integer                 ,");
                sql.addSql("        SCD_TITLE       varchar(50)             ,");
                sql.addSql("        SCD_VALUE       varchar(1000)           ,");
                sql.addSql("        SCD_BIKO        varchar(1000)           ,");
                sql.addSql("        SCD_PUBLIC      integer                 ,");
                sql.addSql("        SCD_AUID        integer         not null,");
                sql.addSql("        SCD_ADATE       timestamp       not null,");
                sql.addSql("        SCD_EUID        integer         not null,");
                sql.addSql("        SCD_EDATE       timestamp       not null,");
                sql.addSql("        SCE_SID         integer                 ,");
                sql.addSql("        SCD_RSSID       integer                 ,");
                sql.addSql("        SCD_EDIT        integer                 ,");
                sql.addSql("        primary key (SCD_SID)");
                sql.addSql(")");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table SCH_DATA add SCE_SID integer default -1");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table SCH_DATA add SCD_EDIT integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table SCH_DATA add SCD_RSSID integer default -1");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] CIR_USERの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterCirUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CIR_USER");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table CIR_USER");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table CIR_USER");
                sql.addSql("(");
                sql.addSql("        USR_SID       integer      not null,");
                sql.addSql("        CUR_MAX_DSP   integer      not null,");
                sql.addSql("        CUR_SML_NTF   integer      not null,");
                sql.addSql("        CUR_AUID      integer      not null,");
                sql.addSql("        CUR_ADATE     timestamp    not null,");
                sql.addSql("        CUR_EUID      integer      not null,");
                sql.addSql("        CUR_EDATE     timestamp    not null,");
                sql.addSql("        primary key (USR_SID)");
                sql.addSql(") ");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table CIR_USER add CUR_SML_NTF integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SCH_PRI_CONFの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterSchPriConf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   SCH_PRI_CONF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table SCH_PRI_CONF");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table SCH_PRI_CONF");
                sql.addSql("(");
                sql.addSql("        USR_SID         integer         not null,");
                sql.addSql("        SCC_FR_DATE     timestamp       not null,");
                sql.addSql("        SCC_TO_DATE     timestamp       not null,");
                sql.addSql("        SCC_DSP_GROUP   integer         not null,");
                sql.addSql("        SCC_SORT_KEY1   integer         not null,");
                sql.addSql("        SCC_SORT_ORDER1 integer         not null,");
                sql.addSql("        SCC_SORT_KEY2   integer         not null,");
                sql.addSql("        SCC_SORT_ORDER2 integer         not null,");
                sql.addSql("        SCC_INI_FR_DATE timestamp       not null,");
                sql.addSql("        SCC_INI_TO_DATE timestamp       not null,");
                sql.addSql("        SCC_INI_PUBLIC  integer         not null,");
                sql.addSql("        SCC_INI_FCOLOR  integer         not null,");
                sql.addSql("        SCC_INI_EDIT    integer         not null,");
                sql.addSql("        SCC_DSP_LIST    integer         not null,");
                sql.addSql("        SCC_DSP_MYGROUP integer         ,");
                sql.addSql("        SCC_AUID        integer         not null,");
                sql.addSql("        SCC_ADATE       timestamp       not null,");
                sql.addSql("        SCC_EUID        integer         not null,");
                sql.addSql("        SCC_EDATE       timestamp       not null,");
                sql.addSql("        primary key (USR_SID)");
                sql.addSql(")");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table SCH_PRI_CONF add SCC_INI_EDIT integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table SCH_PRI_CONF add SCC_DSP_MYGROUP integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] RSV_SIS_YRKの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterRsvSisYrk() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table RSV_SIS_YRK");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table RSV_SIS_YRK");
                sql.addSql("(");
                sql.addSql("  RSY_SID       integer not null,");
                sql.addSql("  RSD_SID       integer not null,");
                sql.addSql("  RSY_YGRP_SID  integer,");
                sql.addSql("  RSY_MOK       varchar(20) not null,");
                sql.addSql("  RSY_FR_DATE   timestamp not null,");
                sql.addSql("  RSY_TO_DATE   timestamp not null,");
                sql.addSql("  RSY_BIKO      varchar(1000),");
                sql.addSql("  RSY_AUID      integer not null,");
                sql.addSql("  RSY_ADATE     timestamp not null,");
                sql.addSql("  RSY_EUID      integer not null,");
                sql.addSql("  RSY_EDATE     timestamp not null,");
                sql.addSql("  SCD_RSSID     integer,");
                sql.addSql("  RSY_EDIT      integer not null,");
                sql.addSql("  RSR_RSID      integer,");
                sql.addSql("  primary key   (RSY_SID)");
                sql.addSql(")");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table RSV_SIS_YRK add SCD_RSSID integer default -1");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();


                sql = new SqlBuffer();
                sql.addSql("alter table RSV_SIS_YRK add RSY_EDIT integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();


                sql = new SqlBuffer();
                sql.addSql("alter table RSV_SIS_YRK add RSR_RSID integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] CMN_USRM_INFの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterCmnUsrmInf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table CMN_USRM_INF");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table CMN_USRM_INF");
                sql.addSql("(");
                sql.addSql("        USR_SID         integer      not null,");
                sql.addSql("        USI_SEI         varchar(10),");
                sql.addSql("        USI_MEI         varchar(10),");
                sql.addSql("        USI_SEI_KN      varchar(20),");
                sql.addSql("        USI_MEI_KN      varchar(20),");
                sql.addSql("        USI_SINI        varchar(3),");
                sql.addSql("        USI_BDATE       date,");
                sql.addSql("        USI_ZIP1        varchar(3),");
                sql.addSql("        USI_ZIP2        varchar(4),");
                sql.addSql("        TDF_SID         integer,");
                sql.addSql("        USI_ADDR1       varchar(100),");
                sql.addSql("        USI_ADDR2       varchar(100),");
                sql.addSql("        USI_TEL1        varchar(20),");
                sql.addSql("        USI_TEL_NAI1        varchar(10),");
                sql.addSql("        USI_TEL_CMT1        varchar(10),");
                sql.addSql("        USI_TEL2        varchar(20),");
                sql.addSql("        USI_TEL_NAI2        varchar(10),");
                sql.addSql("        USI_TEL_CMT2        varchar(10),");
                sql.addSql("        USI_TEL3        varchar(20),");
                sql.addSql("        USI_TEL_NAI3        varchar(10),");
                sql.addSql("        USI_TEL_CMT3        varchar(10),");
                sql.addSql("        USI_FAX1        varchar(20),");
                sql.addSql("        USI_FAX_CMT1        varchar(10),");
                sql.addSql("        USI_FAX2        varchar(20),");
                sql.addSql("        USI_FAX_CMT2        varchar(10),");
                sql.addSql("        USI_FAX3        varchar(20),");
                sql.addSql("        USI_FAX_CMT3        varchar(10),");
                sql.addSql("        USI_MAIL1       varchar(50),");
                sql.addSql("        USI_MAIL_CMT1        varchar(10),");
                sql.addSql("        USI_MAIL2       varchar(50),");
                sql.addSql("        USI_MAIL_CMT2        varchar(10),");
                sql.addSql("        USI_MAIL3       varchar(50),");
                sql.addSql("        USI_MAIL_CMT3        varchar(10),");
                sql.addSql("        USI_SYAIN_NO    varchar(20),");
                sql.addSql("        USI_SYOZOKU     varchar(20),");
                sql.addSql("        USI_YAKUSYOKU   varchar(20),");
                sql.addSql("        POS_SID        integer,");
                sql.addSql("        USI_BIKO        varchar(1000),");
                sql.addSql("        BIN_SID         integer,");
                sql.addSql("        USI_PICT_KF     integer,");
                sql.addSql("        USI_BDATE_KF    integer,");
                sql.addSql("        USI_MAIL1_KF    integer,");
                sql.addSql("        USI_MAIL2_KF    integer,");
                sql.addSql("        USI_MAIL3_KF    integer,");
                sql.addSql("        USI_ZIP_KF      integer,");
                sql.addSql("        USI_TDF_KF      integer,");
                sql.addSql("        USI_ADDR1_KF    integer,");
                sql.addSql("        USI_ADDR2_KF    integer,");
                sql.addSql("        USI_TEL1_KF     integer,");
                sql.addSql("        USI_TEL2_KF     integer,");
                sql.addSql("        USI_TEL3_KF     integer,");
                sql.addSql("        USI_FAX1_KF     integer,");
                sql.addSql("        USI_FAX2_KF     integer,");
                sql.addSql("        USI_FAX3_KF     integer,");
                sql.addSql("        USI_LTLGIN      timestamp,");
                sql.addSql("        USI_AUID        integer      not null,");
                sql.addSql("        USI_ADATE       timestamp       not null,");
                sql.addSql("        USI_EUID        integer      not null,");
                sql.addSql("        USI_EDATE       timestamp       not null,");
                sql.addSql("        primary key (USR_SID)");
                sql.addSql(")");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_TEL_NAI1 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_TEL_CMT1 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_TEL_NAI2 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_TEL_CMT2 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_TEL_NAI3 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_TEL_CMT3 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_FAX_CMT1 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_FAX_CMT2 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_FAX_CMT3 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_MAIL_CMT1 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_MAIL_CMT2 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add USI_MAIL_CMT3 varchar(10) default ''");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_USRM_INF add POS_SID integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SRH_USERの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterSrhUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   SRH_USER");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table SRH_USER");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table SRH_USER");
                sql.addSql("(");
                sql.addSql("        USR_SID        integer not null,");
                sql.addSql("        SHU_MAX_DSP    integer not null,");
                sql.addSql("        SHU_DEF        integer not null,");
                sql.addSql("        SHU_DEF_GRP    integer not null,");
                sql.addSql("        SHU_IMG_SWITCH integer not null,");
                sql.addSql("        SHU_HIS_SWITCH integer not null,");
                sql.addSql("        SHU_AUID       integer not null,");
                sql.addSql("        SHU_ADATE      timestamp not null,");
                sql.addSql("        SHU_EUID       integer not null,");
                sql.addSql("        SHU_EDATE      timestamp not null,");
                sql.addSql("        primary key (USR_SID)");
                sql.addSql(") ");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table SRH_USER add SHU_IMG_SWITCH integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table SRH_USER add SHU_HIS_SWITCH integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SRH_ACONFの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterSrhAconf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   SRH_ACONF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table SRH_ACONF");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table SRH_ACONF");
                sql.addSql("(");
                sql.addSql("        SAC_RANK          integer      not null,");
                sql.addSql("        SAC_NEW           integer      not null,");
                sql.addSql("        SAC_LOGIN_VIEW    integer      not null,");
                sql.addSql("        SAC_LOGIN_RANK    integer      not null,");
                sql.addSql("        SAC_LOGIN_NEW     integer      not null,");
                sql.addSql("        SAC_LOGIN_IMG     integer      not null,");
                sql.addSql("        SAC_LOGIN_HIS     integer      not null,");
                sql.addSql("        SAC_AUID          integer      not null,");
                sql.addSql("        SAC_ADATE         timestamp    not null,");
                sql.addSql("        SAC_EUID          integer      not null,");
                sql.addSql("        SAC_EDATE         timestamp    not null");
                sql.addSql(")");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table SRH_ACONF add SAC_LOGIN_IMG integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql(" alter table SRH_ACONF add SAC_LOGIN_HIS integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] CMN_CONTMの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __alterCmnCntm() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_CONTM");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

            if (count < 1) {
                //drop create
                sql = new SqlBuffer();
                sql.addSql(" drop table CMN_CONTM");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

                sql = new SqlBuffer();
                sql.addSql("create table CMN_CONTM");
                sql.addSql("(");
                sql.addSql("        CNT_PXY_USE    integer,");
                sql.addSql("        CNT_PXY_URL    varchar(200),");
                sql.addSql("        CNT_PXY_PORT   integer,");
                sql.addSql("        CNT_MENU_STATIC   integer");
                sql.addSql("        ");
                sql.addSql(") ");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();

            } else {
                //alter table
                sql = new SqlBuffer();
                sql.addSql(" alter table CMN_CONTM add CNT_MENU_STATIC integer default 0");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in SqlBuffer
     */
    private List<SqlBuffer> __createSQL() {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("create table SCH_EXDATA");
        sql.addSql("(");
        sql.addSql("    SCE_SID        integer         not null,");
        sql.addSql("    SCE_KBN        integer         not null,");
        sql.addSql("    SCE_DWEEK1     integer                 ,");
        sql.addSql("    SCE_DWEEK2     integer                 ,");
        sql.addSql("    SCE_DWEEK3     integer                 ,");
        sql.addSql("    SCE_DWEEK4     integer                 ,");
        sql.addSql("    SCE_DWEEK5     integer                 ,");
        sql.addSql("    SCE_DWEEK6     integer                 ,");
        sql.addSql("    SCE_DWEEK7     integer                 ,");
        sql.addSql("    SCE_DAY        integer                 ,");
        sql.addSql("    SCE_WEEK       integer                 ,");
        sql.addSql("    SCE_TIME_FR    timestamp       not null,");
        sql.addSql("    SCE_TIME_TO    timestamp       not null,");
        sql.addSql("    SCE_TRAN_KBN   integer         not null,");
        sql.addSql("    SCE_DATE_FR    timestamp       not null,");
        sql.addSql("    SCE_DATE_TO    timestamp       not null,");
        sql.addSql("    SCE_BGCOLOR    integer         not null,");
        sql.addSql("    SCE_TITLE      varchar(50)             ,");
        sql.addSql("    SCE_VALUE      varchar(1000)           ,");
        sql.addSql("    SCE_BIKO       varchar(1000)           ,");
        sql.addSql("    SCE_PUBLIC     integer                 ,");
        sql.addSql("    SCE_EDIT       integer                 ,");
        sql.addSql("    SCE_AUID       integer         not null,");
        sql.addSql("    SCE_ADATE      timestamp       not null,");
        sql.addSql("    SCE_EUID       integer         not null,");
        sql.addSql("    SCE_EDATE      timestamp       not null,");
        sql.addSql("    primary key (SCE_SID)");
        sql.addSql(")");
        sqlList.add(sql);


        sql = new SqlBuffer();
        sql.addSql("create table CMN_POSITION");
        sql.addSql("(");
        sql.addSql("        POS_SID    integer       not null,");
        sql.addSql("        POS_NAME   varchar(20)   not null,");
        sql.addSql("        POS_BIKO   varchar(300)  not null,");
        sql.addSql("        POS_SORT   integer       not null,");
        sql.addSql("        POS_AUID   integer       not null,");
        sql.addSql("        POS_ADATE  timestamp     not null,");
        sql.addSql("        POS_EUID   integer       not null,");
        sql.addSql("        POS_EDATE  timestamp     not null,");
        sql.addSql("        primary key (POS_SID)");
        sql.addSql(") ");
        sqlList.add(sql);


        sql = new SqlBuffer();
        sql.addSql("create table RSV_SIS_RYRK");
        sql.addSql("(");
        sql.addSql("  RSR_RSID      integer not null,");
        sql.addSql("  RSR_KBN       integer not null,");
        sql.addSql("  RSR_DWEEK1    integer,");
        sql.addSql("  RSR_DWEEK2    integer,");
        sql.addSql("  RSR_DWEEK3    integer,");
        sql.addSql("  RSR_DWEEK4    integer,");
        sql.addSql("  RSR_DWEEK5    integer,");
        sql.addSql("  RSR_DWEEK6    integer,");
        sql.addSql("  RSR_DWEEK7    integer,");
        sql.addSql("  RSR_DAY       integer,");
        sql.addSql("  RSR_WEEK      integer,");
        sql.addSql("  RSR_TIME_FR   timestamp not null,");
        sql.addSql("  RSR_TIME_TO   timestamp not null,");
        sql.addSql("  RSR_TRAN_KBN  integer not null,");
        sql.addSql("  RSR_DATE_FR   timestamp not null,");
        sql.addSql("  RSR_DATE_TO   timestamp not null,");
        sql.addSql("  RSR_MOK       varchar(20) not null,");
        sql.addSql("  RSR_BIKO      varchar(1000),");
        sql.addSql("  RSR_EDIT      integer,");
        sql.addSql("  RSR_AUID      integer not null,");
        sql.addSql("  RSR_ADATE     timestamp not null,");
        sql.addSql("  RSR_EUID      integer not null,");
        sql.addSql("  RSR_EDATE     timestamp not null,");
        sql.addSql("  primary key   (RSR_RSID)");
        sql.addSql(")");
        sqlList.add(sql);


        sql = new SqlBuffer();
        sql.addSql(" insert ");
        sql.addSql(" into ");
        sql.addSql(" CMN_POSITION(");
        sql.addSql("   POS_SID,");
        sql.addSql("   POS_NAME,");
        sql.addSql("   POS_BIKO,");
        sql.addSql("   POS_SORT,");
        sql.addSql("   POS_AUID,");
        sql.addSql("   POS_ADATE,");
        sql.addSql("   POS_EUID,");
        sql.addSql("   POS_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   0,");
        sql.addSql("   '未設定',");
        sql.addSql("   '',");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("create table CMN_MAINSCREEN_CONF");
        sql.addSql("(");
        sql.addSql("        USR_SID       integer         not null,");
        sql.addSql("        MSC_ID        varchar(150)    not null,");
        sql.addSql("        MSC_POSITION  integer         not null,");
        sql.addSql("        MSC_ORDER     integer         not null,");
        sql.addSql("        MSC_AUID      integer         not null,");
        sql.addSql("        MSC_ADATE     timestamp       not null,");
        sql.addSql("        MSC_EUID      integer         not null,");
        sql.addSql("        MSC_EDATE     timestamp       not null,");
        sql.addSql("        primary key(USR_SID, MSC_ID)");
        sql.addSql(") ");
        sqlList.add(sql);

        return sqlList;
    }
}
