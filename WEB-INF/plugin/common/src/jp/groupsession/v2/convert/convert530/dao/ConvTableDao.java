package jp.groupsession.v2.convert.convert530.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.TempFileUtilFactory;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v5.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);
    /** オペレーションログ レコード毎サイズ */
    private static final int SIZE_CMN_LOG__ = 450;

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
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @param saiban 採番用コントローラー
     * @throws SQLException 例外
     */
    public void convert(
            MlCountMtController saiban) throws SQLException {

        log__.debug("-- DBコンバート開始 --");
        //create Table or alter table
        createTable(saiban);

        __deletePluginControl();
        __deletePluginControlMember();

        __updateSmailSokoBodyPlain();

        __renameTcdTimezone();

        __updateRngTemplateAddFormID();

        //使用データサイズ集計に関するテーブル、初期データを登録
        __createDataUsedData(getCon());

        log__.debug("-- DBコンバート終了 --");
    }
    /**
     *
     * <br>[機  能] 既存のRngTemplateにFormIDを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __updateRngTemplateAddFormID() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sel = new SqlBuffer();
            sel.addSql(" select");
            sel.addSql("   RTP_SID,");
            sel.addSql("   RTP_VER, ");
            sel.addSql("   RTP_MAXVER_KBN, ");
            sel.addSql("   RTP_FORM ");
            sel.addSql(" from");
            sel.addSql("   RNG_TEMPLATE");
            sel.addSql(" where");
            sel.addSql("   RTP_MAXVER_KBN = ?");
            sel.addIntValue(1);
            pstmt = con.prepareStatement(sel.toSqlString());

            log__.info(sel.toLogString());
            sel.setParameter(pstmt);

            SqlBuffer updRtf = new SqlBuffer();
            updRtf.addSql(" update");
            updRtf.addSql("   RNG_TEMPLATE_FORM");
            updRtf.addSql(" set ");
            updRtf.addSql("   RTF_ID=? ");
            updRtf.addSql(" where");
            updRtf.addSql("   RTP_SID = ?");
            updRtf.addSql("   and RTP_VER = ?");
            updRtf.addSql("   and RTF_SID = ?");


            SqlBuffer updRtp = new SqlBuffer();
            updRtp.addSql(" update");
            updRtp.addSql("   RNG_TEMPLATE");
            updRtp.addSql(" set ");
            updRtp.addSql("   RTP_FORM=? ");
            updRtp.addSql(" where");
            updRtp.addSql("   RTP_SID = ?");
            updRtp.addSql("   and RTP_VER = ?");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rtpSid = rs.getInt("RTP_SID");
                int rtpVer = rs.getInt("RTP_VER");
                RngFormIdPublisher idP = new RngFormIdPublisher(rs.getString("RTP_FORM"));

                try (
                    PreparedStatement pstmtUpd = con.prepareStatement(updRtf.toSqlString());
                     ) {
                    for (Entry<Integer, String> entry : idP.getPublishedIdMap().entrySet()) {
                        updRtf.clearValue();
                        updRtf.addStrValue(entry.getValue());
                        updRtf.addIntValue(rtpSid);
                        updRtf.addIntValue(rtpVer);
                        updRtf.addIntValue(entry.getKey());

                        log__.info(updRtf.toLogString());
                        updRtf.setParameter(pstmtUpd);
                        pstmtUpd.executeUpdate();
                    }
                }
                try (
                    PreparedStatement pstmtUpd = con.prepareStatement(updRtp.toSqlString());
                     ) {

                    updRtp.clearValue();
                    updRtp.addStrValue(idP.getPublishedJSON());
                    updRtp.addIntValue(rtpSid);
                    updRtp.addIntValue(rtpVer);

                    log__.info(updRtp.toLogString());
                    updRtp.setParameter(pstmtUpd);
                    pstmtUpd.executeUpdate();
                }

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }


    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @throws SQLException SQL実行例外
     */
    public void createTable(
            MlCountMtController saiban) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL(saiban);

            for (SqlBuffer sql : sqlList) {
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

    /**
     * <br>[機  能] SQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL(
            MlCountMtController saiban) throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   ENQ_QUE_MAIN");
        sql.addSql(" set");
        sql.addSql("   EQM_DSP_SEC = EQM_SEQ");
        sql.addSql(" where");
        sql.addSql("   EMN_SID in (");
        sql.addSql("     select");
        sql.addSql("       EMN_SID");
        sql.addSql("     from");
        sql.addSql("       ENQ_QUE_MAIN");
        sql.addSql("     where");
        sql.addSql("       EQM_SEQ != EQM_DSP_SEC");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("alter table PTL_PORTLET_SORT drop column PLS_VIEW;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_DATAFOLDER (");
        sql.addSql("   CDF_NAME varchar(20) not null,");
        sql.addSql("   CDF_SIZE bigint not null,");
        sql.addSql("   CDF_SORT integer not null,");
        sql.addSql("   primary key(CDF_NAME)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("drop table BBS_LOG_COUNT_ADEL;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("drop table CIR_LOG_COUNT_ADEL;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("drop table FILE_LOG_ADEL;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("drop table SML_LOG_COUNT_ADEL;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("drop table WML_LOG_COUNT_ADEL;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("alter table SCH_EXDATA add column SCE_DAYS_MONTH integer;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("alter table SCH_EXDATA add column SCE_PERIOD_KBN integer;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   SCH_EXDATA");
        sql.addSql(" set");
        sql.addSql("   SCE_DAYS_MONTH = 2");
        sql.addSql(" where");
        sql.addSql("   SCE_KBN = 3");
        sql.addSql(" and");
        sql.addSql("   SCE_DAY > 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_DISP_ACONF (");
        sql.addSql("   CDA_ROKUYOU_KBN integer not null,");
        sql.addSql("   CDA_ROKUYOU integer not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_DISP_PCONF (");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   CDP_ROKUYOU integer not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_DATA_PUB (");
        sql.addSql("   SCD_SID integer not null,");
        sql.addSql("   SDP_TYPE integer not null,");
        sql.addSql("   SDP_PSID integer not null,");
        sql.addSql("   primary key (SCD_SID,SDP_TYPE,SDP_PSID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_INIT_PUB");
        sql.addSql(" (");
        sql.addSql("   USR_SID        integer not null,");
        sql.addSql("   SIP_TYPE       integer not null,");
        sql.addSql("   SIP_PSID       integer not null,");
        sql.addSql("   primary key (USR_SID, SIP_TYPE, SIP_PSID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_DATA_PUB (");
        sql.addSql("   RSY_SID integer not null,");
        sql.addSql("   RDP_TYPE integer not null,");
        sql.addSql("   RDP_PSID integer not null,");
        sql.addSql("   primary key (RSY_SID,RDP_TYPE,RDP_PSID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_BIN (");
        sql.addSql("   SCD_SID integer not null,");
        sql.addSql("   BIN_SID bigint not null,");
        sql.addSql("   primary key (SCD_SID,BIN_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_EXDATA_BIN (");
        sql.addSql("   SCE_SID integer not null,");
        sql.addSql("   BIN_SID bigint not null,");
        sql.addSql("   primary key (SCE_SID,BIN_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_INIT_PUB (");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   RIP_TYPE integer not null,");
        sql.addSql("   RIP_PSID integer not null,");
        sql.addSql("   primary key (USR_SID,RIP_TYPE,RIP_PSID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_EXDATA_PUB (");
        sql.addSql("   SCE_SID integer not null,");
        sql.addSql("   SEP_TYPE integer not null,");
        sql.addSql("   SEP_PSID integer not null,");
        sql.addSql("   primary key (SCE_SID,SEP_TYPE,SEP_PSID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_EXDATA_PUB (");
        sql.addSql("   RSR_RSID integer not null,");
        sql.addSql("   REP_TYPE integer not null,");
        sql.addSql("   REP_PSID integer not null,");
        sql.addSql("   primary key (RSR_RSID,REP_TYPE,REP_PSID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_MYVIEWLIST (");
        sql.addSql("   SMY_SID integer not null,");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   SMY_NAME varchar(50) not null,");
        sql.addSql("   SMY_BIKO varchar(1000),");
        sql.addSql("   SMY_SORT integer not null,");
        sql.addSql("   MGP_AUID integer not null,");
        sql.addSql("   MGP_ADATE timestamp not null,");
        sql.addSql("   MGP_EUID integer not null,");
        sql.addSql("   MGP_EDATE timestamp not null,");
        sql.addSql("   primary key (SMY_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_MYVIEWLIST_BELONG (");
        sql.addSql("   SMY_SID integer not null,");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   GRP_SID integer not null,");
        sql.addSql("   SMV_ORDER integer not null,");
        sql.addSql("   primary key (SMY_SID,USR_SID,GRP_SID,SMV_ORDER)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("alter table SCH_PRI_CONF add column SCC_DSP_VIEWLIST"
                + " integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table API_CONF add column APC_AUTO_DEL integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  alter table RNG_TEMPLATE add column"
                + " RTP_USE_APICONNECT integer not null default 0; ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("  alter table RNG_TEMPLATE add column RTP_APICONNECT_COMMENT varchar(100); ");
        sqlList.add(sql);

        //スケジュール自動削除設定
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   SCH_ADM_CONF");
        sql.addSql(" set ");
        sql.addSql("   SAD_ATDEL_M = 1");
        sql.addSql(" where");
        sql.addSql("   SAD_ATDEL_FLG = 1");
        sql.addSql(" and");
        sql.addSql("   SAD_ATDEL_Y = 0");
        sql.addSql(" and");
        sql.addSql("   SAD_ATDEL_M = 0 ");
        sqlList.add(sql);
        //掲示板自動削除設定
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   BBS_ADM_CONF");
        sql.addSql(" set ");
        sql.addSql("   BAC_ATDEL_M = 1");
        sql.addSql(" where");
        sql.addSql("   BAC_ATDEL_FLG = 1");
        sql.addSql(" and");
        sql.addSql("   BAC_ATDEL_Y = 0");
        sql.addSql(" and");
        sql.addSql("   BAC_ATDEL_M = 0 ");
        sqlList.add(sql);
        //ショートメール自動削除設定 受信
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   SML_ADEL");
        sql.addSql(" set ");
        sql.addSql("   SAD_JDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   SAD_JDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   SAD_JDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   SAD_JDEL_MONTH = 0 ");
        sqlList.add(sql);
        //ショートメール自動削除設定 送信
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   SML_ADEL");
        sql.addSql(" set ");
        sql.addSql("   SAD_SDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   SAD_SDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   SAD_SDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   SAD_SDEL_MONTH = 0 ");
        sqlList.add(sql);
        //ショートメール自動削除設定 草稿
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   SML_ADEL");
        sql.addSql(" set ");
        sql.addSql("   SAD_WDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   SAD_WDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   SAD_WDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   SAD_WDEL_MONTH = 0 ");
        sqlList.add(sql);
        //ショートメール自動削除設定 ゴミ箱
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   SML_ADEL");
        sql.addSql(" set ");
        sql.addSql("   SAD_DDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   SAD_DDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   SAD_DDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   SAD_DDEL_MONTH = 0 ");
        sqlList.add(sql);
        //施設予約自動削除設定
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   RSV_ADM_CONF");
        sql.addSql(" set ");
        sql.addSql("   RAC_ADL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   RAC_ADL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   RAC_ADL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   RAC_ADL_MONTH = 0 ");
        sqlList.add(sql);
        //日報自動削除設定
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   NTP_ADM_CONF");
        sql.addSql(" set ");
        sql.addSql("   NAC_ATDEL_M = 1");
        sql.addSql(" where");
        sql.addSql("   NAC_ATDEL_FLG = 1");
        sql.addSql(" and");
        sql.addSql("   NAC_ATDEL_Y = 0");
        sql.addSql(" and");
        sql.addSql("   NAC_ATDEL_M = 0 ");
        sqlList.add(sql);
        //回覧板自動削除設定 受信
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CIR_ADEL");
        sql.addSql(" set ");
        sql.addSql("   CAD_JDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   CAD_JDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   CAD_JDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   CAD_JDEL_MONTH = 0 ");
        sqlList.add(sql);
        //回覧板自動削除設定 送信
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CIR_ADEL");
        sql.addSql(" set ");
        sql.addSql("   CAD_SDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   CAD_SDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   CAD_SDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   CAD_SDEL_MONTH = 0 ");
        sqlList.add(sql);
        //回覧板自動削除設定 ゴミ箱
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CIR_ADEL");
        sql.addSql(" set ");
        sql.addSql("   CAD_DDEL_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   CAD_DDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   CAD_DDEL_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   CAD_DDEL_MONTH = 0 ");
        sqlList.add(sql);
        //WEBメール自動削除設定 ゴミ箱
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   WML_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   WAD_DUST_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   WAD_DUST_KBN = 2");
        sql.addSql(" and");
        sql.addSql("   WAD_DUST_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_DUST_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_DUST_DAY = 0 ");
        sqlList.add(sql);
        //WEBメール自動削除設定 送信済み
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   WML_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   WAD_SEND_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   WAD_SEND_KBN = 2");
        sql.addSql(" and");
        sql.addSql("   WAD_SEND_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_SEND_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_SEND_DAY = 0 ");
        sqlList.add(sql);
        //WEBメール自動削除設定 草稿
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   WML_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   WAD_DRAFT_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   WAD_DRAFT_KBN = 2");
        sql.addSql(" and");
        sql.addSql("   WAD_DRAFT_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_DRAFT_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_DRAFT_DAY = 0 ");
        sqlList.add(sql);
        //WEBメール自動削除設定 受信箱
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   WML_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   WAD_RESV_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   WAD_RESV_KBN = 2");
        sql.addSql(" and");
        sql.addSql("   WAD_RESV_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_RESV_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_RESV_DAY = 0 ");
        sqlList.add(sql);
        //WEBメール自動削除設定 保管
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   WML_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   WAD_KEEP_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   WAD_KEEP_KBN = 2");
        sql.addSql(" and");
        sql.addSql("   WAD_KEEP_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_KEEP_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   WAD_KEEP_DAY = 0 ");
        sqlList.add(sql);
        //稟議自動削除設定 申請中
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   RNG_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   RAD_PENDING_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   RAD_PENDING_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   RAD_PENDING_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   RAD_PENDING_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   RAD_PENDING_DAY = 0 ");
        sqlList.add(sql);
        //稟議自動削除設定 完了
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   RNG_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   RAD_COMPLETE_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   RAD_COMPLETE_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   RAD_COMPLETE_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   RAD_COMPLETE_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   RAD_COMPLETE_DAY = 0 ");
        sqlList.add(sql);
        //稟議自動削除設定 草稿
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   RNG_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   RAD_DRAFT_DAY = 1");
        sql.addSql(" where");
        sql.addSql("   RAD_DRAFT_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   RAD_DRAFT_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   RAD_DRAFT_MONTH = 0");
        sql.addSql(" and");
        sql.addSql("   RAD_DRAFT_DAY = 0 ");
        sqlList.add(sql);
        //アンケート自動削除設定 草稿
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   ENQ_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   EAD_DRAFT_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   EAD_DRAFT_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   EAD_DRAFT_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   EAD_DRAFT_MONTH = 0");
        sqlList.add(sql);
        //アンケート自動削除設定 発信
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   ENQ_AUTODELETE");
        sql.addSql(" set ");
        sql.addSql("   EAD_SEND_MONTH = 1");
        sql.addSql(" where");
        sql.addSql("   EAD_SEND_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   EAD_SEND_YEAR = 0");
        sql.addSql(" and");
        sql.addSql("   EAD_SEND_MONTH = 0");
        sqlList.add(sql);
        //チャット自動削除設定
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CHT_ADM_CONF");
        sql.addSql(" set ");
        sql.addSql("   CAC_ATDEL_M = 1");
        sql.addSql(" where");
        sql.addSql("   CAC_ATDEL_FLG = 1");
        sql.addSql(" and");
        sql.addSql("   CAC_ATDEL_Y = 0");
        sql.addSql(" and");
        sql.addSql("   CAC_ATDEL_M = 0");
        sqlList.add(sql);
        //メモ自動削除設定
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   MEMO_ADM_CONF");
        sql.addSql(" set ");
        sql.addSql("   MAC_ATDEL_M = 1");
        sql.addSql(" where");
        sql.addSql("   MAC_ATDEL_KBN = 1");
        sql.addSql(" and");
        sql.addSql("   MAC_ATDEL_Y = 0");
        sql.addSql(" and");
        sql.addSql("   MAC_ATDEL_M = 0");
        sqlList.add(sql);

        return sqlList;


    }

    /**
     * <br>[機  能] プラグイン使用制限から「メイン」プラグインを削除
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __deletePluginControl() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");
            sql.addStrValue("main");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] プラグイン使用制限_メンバーから「メイン」プラグインを削除
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __deletePluginControlMember() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");
            sql.addStrValue("main");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能]ショートメール HTML形式の草稿メールを対象にデータの補完を行う。
     * <br>[解  説]SMW_BODYからStringUtilHtml.deleteHtmlTag()でHTMLタグを除去した文字列を
     *             SMW_BODY_PLAINにセットする。
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __updateSmailSokoBodyPlain()
            throws SQLException {

        PreparedStatement pstmt = null;
        PreparedStatement updPstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_BODY");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!StringUtil.isNullZeroStringSpace(rs.getString("SMW_BODY"))) {
                    try {
                        sql = new SqlBuffer();
                        sql.addSql(" update");
                        sql.addSql("   SML_WMEIS");
                        sql.addSql(" set");
                        sql.addSql("   SMW_BODY_PLAIN = ?");
                        sql.addSql(" where");
                        sql.addSql("   SAC_SID = ?");
                        sql.addSql("   and SMW_SID = ?");
                        sql.addStrValue(StringUtilHtml.deleteHtmlTag(rs.getString("SMW_BODY")));
                        sql.addIntValue(rs.getInt("SAC_SID"));
                        sql.addIntValue(rs.getInt("SMW_SID"));
                        log__.info(sql.toLogString());
                        updPstmt = con.prepareStatement(sql.toSqlString());
                        sql.setParameter(updPstmt);
                        updPstmt.executeUpdate();
                    } catch (SQLException e) {
                        throw e;
                    } finally {
                        JDBCUtil.closeStatement(updPstmt);
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能]タイムカード時間帯設定で重複している略称のナンバリングを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __renameTcdTimezone() throws SQLException {

        List<TcdTimezoneInfoModel> overlapList = new ArrayList<TcdTimezoneInfoModel>();
        List<TcdTimezoneInfoModel> notOverlapList = new ArrayList<TcdTimezoneInfoModel>();
        List<String> notOverlapNameList = new ArrayList<String>();
        int count = 1;
        String overlapRyaku = null;

        //重複データリストを取得
        overlapList = __getTimezoneList(true);

        //重複データが存在する時
        if (overlapList != null) {
            //重複以外のデータを取得
            notOverlapList = __getTimezoneList(false);
            for (TcdTimezoneInfoModel nameMdl : notOverlapList) {
                notOverlapNameList.add(nameMdl.getTtiRyaku());
            }

            for (TcdTimezoneInfoModel model : overlapList) {
                if (model.getTtiRyaku().equals(overlapRyaku)) {
                    String rename = overlapRyaku + String.valueOf(count);

                    while (notOverlapNameList.contains(rename)) {
                        count++;
                        rename = overlapRyaku + String.valueOf(count);
                    }
                    __setTimezoneRyaku(model.getTtiSid(), rename.toString());
                    count++;
                } else {
                    overlapRyaku = model.getTtiRyaku();
                    count = 2;
                }
            }
        }
    }

    /**
     * <br>[機  能] タイムカード時間帯設定を取得する。
     * <br>[解  説]
     * <br>[備  考] overlapFlg: true 略称一覧取得、false 略称以外の一覧取得
     * @param overlapFlg true: 重複リスト, false: 重複以外のリスト
     * @return タイムカード時間帯設定一覧
     * @throws SQLException SQL実行時例外
     */
    private List<TcdTimezoneInfoModel> __getTimezoneList(boolean overlapFlg)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezoneInfoModel> list = new ArrayList<TcdTimezoneInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where");
            sql.addSql("   TTI_RYAKU in");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      TTI_RYAKU");
            sql.addSql("    from");
            sql.addSql("      TCD_TIMEZONE_INFO");
            sql.addSql("    group by");
            sql.addSql("      TTI_RYAKU");
            sql.addSql("    having");
            if (overlapFlg) {
                sql.addSql("      COUNT(*) > 1");
            } else {
                sql.addSql("      COUNT(*) = 1");
            }
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(__getTcdTimezoneInfoFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return list;
    }

    /**
     * <br>[機  能] タイムカード時間帯設定略称を再設定(ナンバリング)する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ttiSid 時間帯SID
     * @param rename 略称
     * @throws SQLException SQL実行時例外
     * @return count 更新件数
     */
    private int __setTimezoneRyaku(int ttiSid, String rename)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" set ");
            sql.addSql("   TTI_RYAKU=?");;
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");
            sql.addStrValue(rename);
            sql.addIntValue(ttiSid);
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
     * <p>Create TCD_TIMEZONE_INFO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return bean TcdTimezoneInfoModel
     * @throws SQLException SQL実行例外
     */
    private TcdTimezoneInfoModel __getTcdTimezoneInfoFromRs(ResultSet rs) throws SQLException {
        TcdTimezoneInfoModel bean = new TcdTimezoneInfoModel();
        bean.setTtiSid(rs.getInt("TTI_SID"));
        bean.setTtiName(rs.getString("TTI_NAME"));
        bean.setTtiRyaku(rs.getString("TTI_RYAKU"));
        bean.setTtiSort(rs.getInt("TTI_SORT"));
        bean.setTtiUse(rs.getInt("TTI_USE"));
        bean.setTtiHoliday(rs.getInt("TTI_HOLIDAY"));
        bean.setTtiAuid(rs.getInt("TTI_AUID"));
        bean.setTtiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TTI_ADATE")));
        bean.setTtiEuid(rs.getInt("TTI_EUID"));
        bean.setTtiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TTI_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] データサイズ集計テーブル、及び初期データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __createDataUsedData(Connection con) throws SQLException {

        //データサイズ集計テーブルのcreate table文を実行
        List<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" create table CMN_DATAUSED");
        sql.addSql(" (");
        sql.addSql("   CDU_PLUGIN varchar(20) not null,");
        sql.addSql("   CDU_SIZE   bigint      not null,");
        sql.addSql("   primary key(CDU_PLUGIN)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table MAN_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE               integer,");
        sql.addSql("   CMN_LOG_SIZE           bigint not null,");
        sql.addSql("   CMN_LOGIN_HISTORY_SIZE bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table RSV_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   RSV_SYRK_SUM_SIZE  bigint not null,");
        sql.addSql("   RSV_DATA_SUM_SIZE  bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   BBS_FOR_SUM_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table SCH_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   SCH_DATA_SIZE      bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table SML_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   SML_MAIL_SIZE      bigint not null,");
        sql.addSql("   SAC_DISCSIZE_SUM   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   CIR_DATA_SIZE      bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   FILE_CABINET_SIZE  bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table WML_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   WAC_DISCSIZE_SUM   bigint not null,");
        sql.addSql("   WTP_DISCSIZE_SUM   bigint not null,");
        sql.addSql("   WLG_DISCSIZE_SUM   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE                 integer,");
        sql.addSql("   RNG_RNDATA_DISK_SIZE     bigint not null,");
        sql.addSql("   RNG_TEMPLATE_DISK_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table PRJ_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   PRJ_TODO_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table ADR_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE           integer,");
        sql.addSql("   ADR_CONTACT_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table IPK_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   IPK_DATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   NTP_DATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table CHT_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   CHT_DISK_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table ENQ_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   ENQ_DATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table ZAI_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   ZAI_DATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table RSS_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   RSS_DATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table MEMO_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   MEMO_DATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table TCD_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   TCD_TCDATA_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table BMK_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   BMK_BOOKMARK_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table ANP_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   ANP_HDATA_SIZE   bigint not null,");
        sql.addSql("   ANP_JDATA_SIZE   bigint not null,");
        sql.addSql("   ANP_MTEP_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table USR_DATAUSED_SUM");
        sql.addSql(" (");
        sql.addSql("   SUM_TYPE        integer,");
        sql.addSql("   CMN_USR_SIZE   bigint not null");
        sql.addSql(" );");
        sqlList.add(sql);

        PreparedStatement pstmt = null;
        try {
            for (SqlBuffer sqlBuff : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sqlBuff.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }

        //メイン 使用データサイズの登録
        long logSize = __getRecordCount(con, "CMN_LOG") * SIZE_CMN_LOG__;
        long loginHistorySize = __getTotalDataSize(con, "CMN_LOGIN_HISTORY",
                new String[] {
                        "CLH_IP",
                        "CLH_UID"
                }
                );

        __insertDataused(con, "MAN_DATAUSED_SUM",
                        new long[] {
                                logSize,
                                loginHistorySize
                        }
        );
        __entryPluginUsedDisk(con,
                "main",
                logSize + loginHistorySize);


        //施設予約 使用データサイズの登録
        long rsvYrkDataSize = 0;
        rsvYrkDataSize +=  __getTotalDataSize(con, "RSV_SIS_YRK",
                new String[] {
                        "RSY_MOK",
                        "RSY_BIKO"
                }
                );
        rsvYrkDataSize += __getTotalDataSize(con, "RSV_SIS_RYRK",
                new String[] {
                        "RSR_MOK",
                        "RSR_BIKO"
                }
                );
        rsvYrkDataSize += __getTotalDataSize(con, "RSV_SIS_KYRK",
                new String[] {
                        "RKY_BUSYO",
                        "RKY_NAME",
                        "RKY_NUM",
                        "RKY_CONTACT",
                        "RKY_GUIDE",
                        "RKY_PARK_NUM",
                        "RKY_DEST"
                }
                );

        long rsvDataSize = __getTotalDataSize(con, "RSV_SIS_DATA",
                new String[] {
                        "RSD_NAME",
                        "RSD_SNUM",
                        "RSD_PROP_1",
                        "RSD_PROP_2",
                        "RSD_PROP_3",
                        "RSD_PROP_4",
                        "RSD_PROP_5",
                        "RSD_PROP_6",
                        "RSD_PROP_7",
                        "RSD_PROP_8",
                        "RSD_PROP_9",
                        "RSD_PROP_10",
                        "RSD_IMG_CMT1",
                        "RSD_IMG_CMT2",
                        "RSD_IMG_CMT3",
                        "RSD_IMG_CMT4",
                        "RSD_IMG_CMT5",
                        "RSD_IMG_CMT6",
                        "RSD_IMG_CMT7",
                        "RSD_IMG_CMT8",
                        "RSD_IMG_CMT9",
                        "RSD_IMG_CMT10",
                        "RSD_BIKO",
                        "RSD_PLA_CMT"
                }
                );

        rsvDataSize += __getTotalFileSize(con, "RSV_BIN");

        __insertDataused(con, "RSV_DATAUSED_SUM",
                        new long[] {
                                rsvYrkDataSize,
                                rsvDataSize
                        }
        );
        __entryPluginUsedDisk(con,
                "reserve",
                rsvYrkDataSize + rsvDataSize);

        //掲示板 使用データサイズの登録
        long forumSize = __getSumValue(con, "BBS_FOR_SUM", "BFS_SIZE");
        __insertDataused(con, "BBS_DATAUSED_SUM",
                new long[] {
                        forumSize
                }
        );
        __entryPluginUsedDisk(con,
                "bulletin",
                forumSize);

        //スケジュール 使用データサイズの登録
        long schDataSize = 0;
        schDataSize += __getTotalDataSize(con, "SCH_DATA",
                new String[] {
                        "SCD_TITLE",
                        "SCD_VALUE",
                        "SCD_BIKO",
                        "SCD_ATTEND_COMMENT"
                }
                );
        schDataSize += __getTotalDataSize(con, "SCH_EXDATA",
                new String[] {
                        "SCE_TITLE",
                        "SCE_VALUE",
                        "SCE_BIKO"
                }
                );

        __insertDataused(con, "SCH_DATAUSED_SUM",
                        new long[] {
                                schDataSize
                        }
        );
        __entryPluginUsedDisk(con,
                "schedule",
                schDataSize);

        //ショートメール 使用データサイズの登録
        long smlAccountSize = __getSmlAccountSize(con);

        long smlMailSize = 0;
        smlMailSize += __getTotalDataSize(con, "SML_SMEIS",
                new String[] {
                        "SMS_TITLE",
                        "SMS_BODY",
                        "SMS_BODY_PLAIN"
                }
                );
        smlMailSize += __getTotalDataSize(con, "SML_WMEIS",
                new String[] {
                        "SMW_TITLE",
                        "SMW_BODY",
                        "SMW_BODY_PLAIN"
                }
                );
        smlMailSize += __getTotalFileSize(con, "SML_BIN");

        __insertDataused(con, "SML_DATAUSED_SUM",
                new long[] {
                        smlMailSize,
                        smlAccountSize
                }
        );
        __entryPluginUsedDisk(con,
                "smail",
                smlMailSize);

        //回覧板 使用データサイズの登録
        long cirDataSize = 0;
        cirDataSize += __getTotalDataSize(con, "CIR_INF",
                new String[] {
                        "CIF_TITLE",
                        "CIF_VALUE"
                }
                );
        cirDataSize += __getTotalFileSize(con, "CIR_BIN");
        cirDataSize += __getTotalDataSize(con, "CIR_VIEW",
                new String[] {
                        "CVW_MEMO"
                }
                );
        cirDataSize += __getTotalFileSize(con, "CIR_USER_BIN", "CUB_BIN_SID");

        __insertDataused(con, "CIR_DATAUSED_SUM",
                new long[] {
                        cirDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "circular",
                cirDataSize);

        //ファイル管理 使用データサイズの登録
        long cabinetSize = __getTotalFileCabinetSize(con);
        __insertDataused(con, "FILE_DATAUSED_SUM",
                new long[] {
                        cabinetSize
                }
        );
        __entryPluginUsedDisk(con,
                "file",
                cabinetSize);

        //WEBメール 使用データサイズの登録
        long wmlAccountSize = __getSumValue(con, "WML_ACCOUNT_DISK", "WDS_SIZE");

        long wmlTemplateSize = 0;
        wmlTemplateSize += __getTotalDataSize(con, "WML_MAIL_TEMPLATE",
                new String[] {
                        "WTP_TITLE",
                        "WTP_BODY"
                }
                );
        wmlTemplateSize += __getTotalFileSize(con, "WML_MAIL_TEMPLATE_FILE");

        long wmlMaillogSize = 0;
        wmlMaillogSize += __getTotalDataSize(con, "WML_MAIL_LOG",
                new String[] {
                        "WLG_TITLE",
                        "WLG_FROM"
                }
                );
        wmlMaillogSize += __getTotalDataSize(con, "WML_MAIL_LOG_SEND",
                new String[] {
                        "WLS_ADDRESS"
                }
                );

        __insertDataused(con, "WML_DATAUSED_SUM",
                new long[] {
                        wmlAccountSize,
                        wmlTemplateSize,
                        wmlMaillogSize,
                }
        );
        __entryPluginUsedDisk(con,
                "webmail",
                wmlAccountSize + wmlTemplateSize + wmlMaillogSize);

        //稟議 使用データサイズの登録
        long rngRnDataSize = 0;
        rngRnDataSize += __getTotalDataSize(con, "RNG_FORMDATA",
                new String[] {
                        "RFD_VALUE"
                }
                );
        rngRnDataSize += __getTotalDataSize(con, "RNG_SINGI",
                new String[] {
                        "RSS_COMMENT"
                }
                );
        rngRnDataSize += __getTotalFileSize(con, "RNG_BIN");

        long rngTemplateSize = 0;
        rngTemplateSize += __getTotalDataSize(con, "RNG_TEMPLATE",
                new String[] {
                        "RTP_FORM",
                        "RTP_TITLE",
                        "RTP_RNG_TITLE",
                        "RTP_BIKO"
                }
                );
        rngTemplateSize += __getTotalDataSize(con, "RNG_TEMPLATE_FORM",
                new String[] {
                        "RTF_ID",
                        "RTF_TITLE"
                }
                );
        rngTemplateSize += __getTotalDataSize(con, "RNG_TEMPLATE_KEIRO",
                new String[] {
                        "RTK_KEIRO_COMMENT"
                }
                );
        rngTemplateSize += __getTotalFileSize(con, "RNG_TEMPLATE_BIN");

        __insertDataused(con, "RNG_DATAUSED_SUM",
                new long[] {
                        rngRnDataSize,
                        rngTemplateSize
                }
        );
        __entryPluginUsedDisk(con,
                "ringi",
                rngRnDataSize + rngTemplateSize);

        //プロジェクト 使用データサイズの登録
        long todoDataSize = 0;
        todoDataSize += __getTotalDataSize(con, "PRJ_TODODATA",
                new String[] {
                        "PTD_TITLE",
                        "PTD_CONTENT"
                }
                );
        todoDataSize += __getTotalDataSize(con, "PRJ_TODOCOMMENT",
                new String[] {
                        "PCM_COMMENT"
                }
                );
        todoDataSize += __getTotalFileSize(con, "PRJ_TODO_BIN");

        __insertDataused(con, "PRJ_DATAUSED_SUM",
                new long[] {
                        todoDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "project",
                todoDataSize);

        //アドレス帳 使用データサイズの登録
        long adrContactSize = 0;
        adrContactSize += __getTotalDataSize(con, "ADR_CONTACT",
                new String[] {
                        "ADC_TITLE",
                        "ADC_BIKO"
                }
                );
        adrContactSize += __getTotalFileSize(con, "ADR_CONTACT_BIN");

        __insertDataused(con, "ADR_DATAUSED_SUM",
                new long[] {
                        adrContactSize
                }
        );
        __entryPluginUsedDisk(con,
                "address",
                adrContactSize);

        //在席管理 使用データサイズの登録
        long zaiDataSize = 0;
        zaiDataSize += __getTotalDataSize(con, "ZAI_INFO",
                new String[] {
                        "ZIF_NAME",
                        "ZIF_MSG"
                }
                );
        zaiDataSize += __getTotalDataSize(con, "ZAI_INDEX",
                new String[] {
                        "ZIN_NAME",
                        "ZIN_MSG",
                        "ZIN_OTHER_VALUE"
                }
                );
        zaiDataSize += __getTotalFileSize(con, "ZAI_INFO");

        __insertDataused(con, "ZAI_DATAUSED_SUM",
                new long[] {
                        zaiDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "zaiseki",
                zaiDataSize);

        //RSS 使用データサイズの登録
        long rssDataSize = 0;
        rssDataSize += __getTotalDataSize(con, "RSS_DATA",
                new String[] {
                        "RSD_TITLE",
                        "RSD_URL_FEED",
                        "RSD_URL",
                        "RSD_AUTH_ID",
                        "RSD_AUTH_PSWD"
                }
                );
        rssDataSize += __getTotalDataSize(con, "RSS_INFOM",
                new String[] {
                        "RSM_URL_FEED",
                        "RSM_AUTH_ID",
                        "RSM_AUTH_PSWD"
                }
                );

        //RSSフィード情報の合計サイズ取得
        boolean autoCommit = con.getAutoCommit();
        try {
            if (autoCommit) {
                con.setAutoCommit(false);
            }
            ITempFileUtil tempFileUtil = TempFileUtilFactory.getInstance("");
            rssDataSize += tempFileUtil.getTotalFeedDataSize(con);
        } finally {
            if (autoCommit) {
                con.setAutoCommit(true);
            }
        }

        __insertDataused(con, "RSS_DATAUSED_SUM",
                new long[] {
                        rssDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "rss",
                rssDataSize);

        //IP管理 使用データサイズの登録
        long ipkDataSize = 0;
        ipkDataSize += __getTotalDataSize(con, "IPK_NET",
                new String[] {
                        "INT_NAME",
                        "INT_MSG"
                }
                );
        ipkDataSize += __getTotalFileSize(con, "IPK_BIN");

        __insertDataused(con, "IPK_DATAUSED_SUM",
                new long[] {
                        ipkDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "ipkanri",
                ipkDataSize);

        //日報 使用データサイズの登録
        long ntpDataSize = 0;
        ntpDataSize += __getTotalDataSize(con, "NTP_DATA",
                new String[] {
                        "NIP_TITLE",
                        "NIP_DETAIL",
                        "NIP_ASSIGN",
                        "NIP_SYOKAN",
                        "NIP_ACTION"
                }
                );
        ntpDataSize += __getTotalDataSize(con, "NTP_COMMENT",
                new String[] {
                        "NPC_COMMENT"
                }
                );
        ntpDataSize += __getTotalFileSize(con, "NTP_BIN");

        __insertDataused(con, "NTP_DATAUSED_SUM",
                new long[] {
                        ntpDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "nippou",
                ntpDataSize);

        //チャット 使用データサイズの登録
        long chatDataSize = 0;
        chatDataSize += __getTotalDataSize(con, "CHT_USER_DATA",
                new String[] {
                        "CUD_TEXT"
                }
                );
        chatDataSize += __getTotalDataSize(con, "CHT_GROUP_DATA",
                new String[] {
                        "CGD_TEXT"
                }
                );
        chatDataSize += __getTotalFileSize(con, "CHT_GROUP_DATA");
        chatDataSize += __getTotalFileSize(con, "CHT_USER_DATA");

        __insertDataused(con, "CHT_DATAUSED_SUM",
                new long[] {
                        chatDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "chat",
                chatDataSize);

        //アンケート 使用データサイズの登録
        long enqDataSize = 0;
        enqDataSize += __getTotalDataSize(con, "ENQ_MAIN",
                new String[] {
                        "EMN_TITLE",
                        "EMN_DESC",
                        "EMN_DESC_PLAIN",
                        "EMN_SEND_NAME"
                }
                );
        enqDataSize += __getTotalDataSize(con, "ENQ_QUE_MAIN",
                new String[] {
                        "EQM_QUESTION",
                        "EQM_DESC",
                        "EQM_DESC_PLAIN"
                }
                );
        enqDataSize += __getTotalDataSize(con, "ENQ_ANS_SUB",
                new String[] {
                        "EAS_ANS_TXT",
                        "EAS_ANS"
                }
                );
        enqDataSize += __getEnqMainFileSize(con);
        enqDataSize += __getEnqQueMainFileSize(con);

        __insertDataused(con, "ENQ_DATAUSED_SUM",
                new long[] {
                        enqDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "enquete",
                enqDataSize);

        //メモ 使用データサイズ登録
        long    memoDataSize = 0;
        memoDataSize += __getTotalDataSize(con, "MEMO_DATA",
                new String[] {
                        "MMD_CONTENT"
                }
                );
        memoDataSize += __getTotalFileSize(con, "MEMO_BIN");
        __insertDataused(con, "MEMO_DATAUSED_SUM",
                new long[] {
                        memoDataSize
                }
        );
        __entryPluginUsedDisk(con,
                "memo",
                memoDataSize);

        //タイムカード 使用データサイズの登録
        long tcdDataSize = __getTcdDataSize(con);
        __insertDataused(con, "TCD_DATAUSED_SUM",
                        new long[] {
                                tcdDataSize
                        }
        );
        __entryPluginUsedDisk(con,
                "timecard",
                tcdDataSize);

        //ブックマーク 使用データサイズの登録
        long bmkDataSize = 0;
        bmkDataSize += __getTotalDataSize(con, "BMK_BOOKMARK",
                new String[] {
                        "BMK_TITLE",
                        "BMK_CMT"
                }
                );

        __insertDataused(con, "BMK_DATAUSED_SUM",
                        new long[] {
                                bmkDataSize
                        }
        );
        __entryPluginUsedDisk(con,
                "bookmark",
                bmkDataSize);

        //安否確認 使用データサイズの登録
        long anpHDataSize = 0;
        long anpJDataSize = 0;
        long anpMDataSize = 0;
        anpHDataSize += __getTotalDataSize(con, "ANP_HDATA",
                new String[] {
                        "APH_SUBJECT",
                        "APH_TEXT1",
                        "APH_TEXT2"
                }
                );
        anpJDataSize += __getTotalDataSize(con, "ANP_JDATA",
                new String[] {
                        "APD_MAILADR",
                        "APD_COMMENT"
                }
                );
        anpMDataSize += __getTotalDataSize(con, "ANP_MTEMP",
                new String[] {
                        "APM_TITLE",
                        "APM_SUBJECT",
                        "APM_TEXT1",
                        "APM_TEXT2",
                }
                );
        __insertDataused(con, "ANP_DATAUSED_SUM",
                        new long[] {
                                anpHDataSize,
                                anpJDataSize,
                                anpMDataSize
                        }
        );
        __entryPluginUsedDisk(con,
                "anpi",
                anpHDataSize + anpJDataSize + anpMDataSize);

        //ユーザ情報 使用データサイズの登録
        long usrDataSize = 0;
        usrDataSize += __getTotalDataSize(con, "CMN_USRM",
                new String[] {
                        "USR_LGID",
                        "USR_PSWD"
                }
                );
        usrDataSize += __getTotalDataSize(con, "CMN_USRM_INF",
                new String[] {
                        "USI_ADDR1",
                        "USI_ADDR2",
                        "USI_MAIL1",
                        "USI_MAIL2",
                        "USI_MAIL3",
                        "USI_BIKO",
                        "USI_OTPSEND_ADDRESS"
                }
                );
        usrDataSize += __getTotalFileSize(con, "CMN_USRM_INF");

        __insertDataused(con, "USR_DATAUSED_SUM",
                        new long[] {
                                usrDataSize
                        }
        );
        __entryPluginUsedDisk(con,
                "user",
                usrDataSize);

    }

    /**
     * <br>[機  能] 指定したテーブルのデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tableName テーブル名
     * @param fieldNameList フィールド名
     * @return データサイズ合計
     * @throws SQLException SQL実行事例外
     */
    private long __getTotalDataSize(Connection con, String tableName, String[] fieldNameList)
    throws SQLException {
        long dataSize = 0;

        Statement stmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("select");

            for (int idx = 0; idx < fieldNameList.length; idx++) {
                if (idx > 0) {
                    sql.addSql("  ,");
                }
                sql.addSql("   sum(octet_length("
                        + fieldNameList[idx]
                        + ")) as SIZE" + idx);
            }
            sql.addSql(" from");
            sql.addSql("   " + tableName);

            stmt = con.createStatement();
            log__.info(sql.toLogString());
            rs = stmt.executeQuery(sql.toSqlString());

            if (rs.next()) {
                for (int idx = 0; idx < fieldNameList.length; idx++) {
                    dataSize += rs.getLong("SIZE" + idx);
                }
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }

        return dataSize;
    }


    /**
     * <br>[機  能] 指定したテーブルに関連するバイナリ情報のファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tableName テーブル名
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行事例外
     */
    private long __getTotalFileSize(Connection con, String tableName) throws SQLException {
        return __getTotalFileSize(con, tableName, "BIN_SID");
    }
    /**
     * <br>[機  能] 指定したテーブルに関連するバイナリ情報のファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tableName テーブル名
     * @param fieldName フィールド名
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行事例外
     */
    private long __getTotalFileSize(Connection con, String tableName, String fieldName)
    throws SQLException {
        long fileSize = 0;

        Statement stmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF,");
            sql.addSql("   " + tableName);
            sql.addSql(" where");
            sql.addSql("   " + tableName + "." + fieldName + " = CMN_BINF.BIN_SID");

            stmt = con.createStatement();
            log__.info(sql.toLogString());
            rs = stmt.executeQuery(sql.toSqlString());

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }

        return fileSize;
    }

    /**
     * <br>[機  能] 指定したテーブル, フィールドの合計値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tableName テーブル名
     * @param fieldName フィールド名
     * @return 指定フィールドの合計値
     * @throws SQLException SQL実行事例外
     */
    private long __getSumValue(Connection con, String tableName, String fieldName)
    throws SQLException {
        long sumValue = 0;

        Statement stmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   sum(" + fieldName + ") as SUM_VALUE");
            sql.addSql(" from");
            sql.addSql("   " + tableName);

            stmt = con.createStatement();
            log__.info(sql.toLogString());
            rs = stmt.executeQuery(sql.toSqlString());

            if (rs.next()) {
                sumValue = rs.getLong("SUM_VALUE");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }

        return sumValue;
    }

    /**
     * <br>[機  能] 指定したテーブルのレコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tableName テーブル名
     * @return レコード件数
     * @throws SQLException SQL実行事例外
     */
    private long __getRecordCount(Connection con, String tableName) throws SQLException {
        long count = 0;

        Statement stmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select count(*) as CNT from " + tableName);
            stmt = con.createStatement();
            log__.info(sql.toLogString());
            rs = stmt.executeQuery(sql.toSqlString());

            if (rs.next()) {
                count = rs.getLong("CNT");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }

        return count;
    }

    /**
     * <br>[機  能] プラグイン別の使用データサイズ合計を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param pluginId プラグインID
     * @param usedSize 使用データサイズ合計
     * @throws SQLException SQL実行時例外
     */
    private void __entryPluginUsedDisk(Connection con, String pluginId, long usedSize)
    throws SQLException {

        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_DATAUSED(");
            sql.addSql("   CDU_PLUGIN,");
            sql.addSql("   CDU_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pluginId);
            sql.addLongValue(usedSize);
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
     * <br>[機  能] 使用データサイズ集計テーブルへのデータ登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tableName テーブル名
     * @param valueList 設定値
     * @throws SQLException SQL実行事例外
     */
    private void __insertDataused(Connection con, String tableName, long[] valueList)
    throws SQLException {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" insert into " + tableName);
            sql.addSql(" values (");

            //合計値区分を設定
            sql.addSql("   ?");
            sql.addIntValue(0);

            //各データサイズを設定
            for (int idx = 0; idx < valueList.length; idx++) {
                sql.addSql("   ,?");
                sql.addLongValue(valueList[idx]);
            }
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            pstmt.executeUpdate();

        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ショートメールアカウントのディスク使用量合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 指定フィールドの合計値
     * @throws SQLException SQL実行事例外
     */
    private long __getSmlAccountSize(Connection con)
    throws SQLException {
        long sumValue = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   sum(SML_ACCOUNT_DISK.SDS_SIZE) as SUM_VALUE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT,");
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" where");
            sql.addSql("   SML_ACCOUNT.SAC_SID = SML_ACCOUNT_DISK.SAC_SID");
            sql.addSql(" and");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_ACCOUNT.SAC_SID not in (");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");
            sql.addIntValue(0);
            sql.addIntValue(0);
            sql.addIntValue(1);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                sumValue = rs.getLong("SUM_VALUE");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return sumValue;
    }

    /**
     * <br>[機  能] ファイル管理 キャビネットの使用データサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return キャビネットの使用データサイズ合計
     * @throws SQLException SQL実行事例外
     */
    private long __getTotalFileCabinetSize(Connection con) throws SQLException {
        long totalSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   sum(FILE_FILE_BIN.FFL_FILE_SIZE) as SUM_SIZE");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN=?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(1);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalSize = rs.getLong("SUM_SIZE");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return totalSize;
    }

    /**
     * <br>[機  能] アンケート基本情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    private long __getEnqMainFileSize(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   ENQ_MAIN.EMN_ATTACH_ID is not null");
            sql.addSql(" and");
            sql.addSql("   cast(ENQ_MAIN.EMN_ATTACH_ID as bigint) = CMN_BINF.BIN_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /** タイムカード情報 データサイズ */
    private static final int SIZE_TCD_DATA__ = 16;

    /**
     * <br>[機  能] タイムカード データサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    private long __getTcdDataSize(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  ");
            sql.addSql("   sum(octet_length(TCD_BIKO)) as TCD_BIKO,");
            sql.addSql(String.format(" count(TCD_DATE) * %d as SIZE ", SIZE_TCD_DATA__));
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("TCD_BIKO");
                dataSize += rs.getLong("SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dataSize;
    }

    /**
     * <br>[機  能] アンケート 設問_基本情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    private long __getEnqQueMainFileSize(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   ENQ_QUE_MAIN.EQM_ATTACH_ID is not null");
            sql.addSql(" and");
            sql.addSql("   cast(ENQ_QUE_MAIN.EQM_ATTACH_ID as bigint) = CMN_BINF.BIN_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }
}
