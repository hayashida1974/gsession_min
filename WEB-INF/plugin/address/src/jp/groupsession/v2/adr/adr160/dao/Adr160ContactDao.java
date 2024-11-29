package jp.groupsession.v2.adr.adr160.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr160.csv.Adr160CsvModel;
import jp.groupsession.v2.adr.adr160.csv.Adr160CsvRecordListenerImpl;
import jp.groupsession.v2.adr.adr160.model.Adr160ContactModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160ContactDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr160ContactDao.class);

    /** 取得区分(カウント) */
    public static final int GET_COUNT = 0;
    /** 取得区分(一覧表示) */
    public static final int GET_LIST = 1;

    /**
     * <p>Default Constructor
     */
    public Adr160ContactDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr160ContactDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] コンタクト履歴の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return int コンタクト履歴の件数
     * @throws SQLException SQL実行例外
     */
    public int getContactCount(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateSql(GET_COUNT, adrSid, -1, -1);

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
     * <br>[機  能] コンタクト履歴を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrsid アドレス帳SID
     * @param start スタート位置
     * @param limit 表示件数
     * @param sortKey ソートする項目
     * @param orderKey 昇順・降順
     * @return List in Adr160ContactModel
     * @throws SQLException SQL実行例外
     */
    public List<Adr160ContactModel> getContactList(
            int adrsid, int start, int limit, String sortKey, String orderKey)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Adr160ContactModel> ret = new ArrayList<Adr160ContactModel>();
        con = getCon();

        try {
            //SQL文
            int sortKeyInt = NullDefault.getInt(sortKey, GSConstAddress.CONTACT_SORT_SYUBETU);
            int orderKeyInt = NullDefault.getInt(orderKey, GSConst.ORDER_KEY_ASC);
            SqlBuffer sql = __cleateSql(GET_LIST, adrsid, sortKeyInt, orderKeyInt);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE ,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (start > 1) {
                rs.absolute(start - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                Adr160ContactModel retMdl = new Adr160ContactModel();
                retMdl.setAdcSid(rs.getInt("ADC_SID"));
                retMdl.setAdcTitle(rs.getString("ADC_TITLE"));
                retMdl.setAdcType(rs.getInt("ADC_TYPE"));
                retMdl.setAdcCttime(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME")));
                retMdl.setAdcCttimeTo(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME_TO")));
                retMdl.setAdcCttimeDsp(__cleateDspDate(retMdl.getAdcCttime()));
                retMdl.setAdcCttimeToDsp(__cleateDspDate(retMdl.getAdcCttimeTo()));
                retMdl.setAdcAdduserDspSei(rs.getString("USI_SEI"));
                retMdl.setAdcAdduserDspMei(rs.getString("USI_MEI"));
                retMdl.setAdcAddUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                retMdl.setAdcAddusrJkbn(rs.getInt("USR_JKBN"));
                ret.add(retMdl);
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
     * <br>[機  能] SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param getKbn 0=カウント、1=一覧表示
     * @param adrSid アドレス帳SID
     * @param sortKey ソートする項目
     * @param orderKey 降順・昇順
     * @return SqlBuffer
     */
    private SqlBuffer __cleateSql(int getKbn, int adrSid, int sortKey, int orderKey) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(*) as CNT");

        } else {
            //一覧表示
            sql.addSql("   CON.ADC_SID,");
            sql.addSql("   CON.ADR_SID,");
            sql.addSql("   CON.ADC_TITLE,");
            sql.addSql("   CON.ADC_TYPE,");
            sql.addSql("   CON.ADC_CTTIME,");
            sql.addSql("   CON.ADC_CTTIME_TO,");
            sql.addSql("   CON.PRJ_SID,");
            sql.addSql("   CON.ADC_BIKO,");
            sql.addSql("   CON.ADC_AUID,");
            sql.addSql("   CON.ADC_ADATE,");
            sql.addSql("   CON.ADC_EUID,");
            sql.addSql("   CON.ADC_EDATE,");

            sql.addSql("   USR.USI_SEI,");
            sql.addSql("   USR.USI_MEI,");

            sql.addSql("   USRM.USR_JKBN,");
            sql.addSql("   USRM.USR_UKO_FLG");

        }

        sql.addSql(" from");
        sql.addSql("   ADR_CONTACT as CON ");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM_INF as USR");
        sql.addSql(" on");
        sql.addSql("   CON.ADC_AUID = USR.USR_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM as USRM");
        sql.addSql(" on");
        sql.addSql("   CON.ADC_AUID = USRM.USR_SID");

        sql.addSql(" where");
        sql.addSql("   CON.ADR_SID = ?");
        sql.addIntValue(adrSid);

        //ソート
        if (getKbn == GET_LIST) {
            sql.addSql("  order by");
            //ソートする項目
            if (sortKey == GSConstAddress.CONTACT_SORT_SYUBETU) {
                sql.addSql("    CON.ADC_TYPE");
            } else if (sortKey == GSConstAddress.CONTACT_SORT_TITLE) {
                sql.addSql("    CON.ADC_TITLE");
            } else if (sortKey == GSConstAddress.CONTACT_SORT_ADDUSER) {
                sql.addSql("    CON.ADC_AUID");
            } else if (sortKey == GSConstAddress.CONTACT_SORT_DATE) {
                sql.addSql("    CON.ADC_CTTIME");
            }
            //昇順・降順
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                sql.addSql("  asc");
            } else if (orderKey == GSConst.ORDER_KEY_DESC) {
                sql.addSql("  desc");
            }
        }
        return sql;
    }

    /**
     * <p>指定した条件のデータをCSVへ出力する
     * @param adrSid 検索条件のアドレス帳SID
     * @param sessionUserSid セッションユーザSID
     * @param rl リスナークラス
     * @param reqMdl RequestModel
     * @return List in Adr160CsvModel 結果を格納したListオブジェクト
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public int createContactForCsv(
            int adrSid,
            int sessionUserSid,
            Adr160CsvRecordListenerImpl rl,
            RequestModel reqMdl)
            throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CONT.ADC_SID,");
            sql.addSql("   CONT.ADR_SID,");
            sql.addSql("   CONT.ADC_TITLE,");
            sql.addSql("   CONT.ADC_TYPE,");
            sql.addSql("   CONT.ADC_CTTIME,");
            sql.addSql("   CONT.ADC_CTTIME_TO,");
            sql.addSql("   CONT.PRJ_SID,");
            sql.addSql("   CONT.ADC_BIKO,");
            sql.addSql("   CONT.ADC_AUID,");
            sql.addSql("   CONT.ADC_ADATE,");
            sql.addSql("   CONT.ADC_EUID,");
            sql.addSql("   CONT.ADC_EDATE,");
            sql.addSql("   ADDR.ADR_SEI,");
            sql.addSql("   ADDR.ADR_MEI,");
            sql.addSql("   ADDR.ADR_SEI_KN,");
            sql.addSql("   ADDR.ADR_MEI_KN,");
            sql.addSql("   COMP.ACO_NAME,");
            sql.addSql("   COMP.ACO_NAME_KN,");
            sql.addSql("   CBAS.ABA_TYPE,");
            sql.addSql("   CBAS.ABA_NAME,");
            sql.addSql("   PRJD.PRJ_NAME");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT as CONT ");

            sql.addSql(" left join");
            sql.addSql("   ADR_ADDRESS as ADDR");
            sql.addSql(" on");
            sql.addSql("   CONT.ADR_SID = ADDR.ADR_SID");

            sql.addSql(" left join");
            sql.addSql("   ADR_COMPANY as COMP");
            sql.addSql(" on");
            sql.addSql("   ADDR.ACO_SID = COMP.ACO_SID");

            sql.addSql(" left join");
            sql.addSql("   ADR_COMPANY_BASE as CBAS");
            sql.addSql(" on");
            sql.addSql("   ADDR.ABA_SID = CBAS.ABA_SID");

            sql.addSql(" left join");
            sql.addSql("   PRJ_PRJDATA as PRJD");
            sql.addSql(" on");
            sql.addSql("   CONT.PRJ_SID = PRJD.PRJ_SID");

            sql.addSql(" where ");
            sql.addSql("   CONT.ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            Adr160CsvModel model = null;
            GsMessage gsMsg = new GsMessage();

            while (rs.next()) {
                model = __getAdrCsvFromRs(rs, reqMdl, gsMsg);
                rl.setRecord(model);
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
     * <p>CSV出力用モデルを生成
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @param gsMsg メッセージ
     * @return created TcdCsvModel
     * @throws SQLException SQL実行例外
     */
    private Adr160CsvModel __getAdrCsvFromRs(ResultSet rs,
                                              RequestModel reqMdl,
                                              GsMessage gsMsg)
    throws SQLException {

        Adr160CsvModel bean = new Adr160CsvModel();
        bean.setAdcSid(rs.getInt("ADC_SID"));
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdcTitle(rs.getString("ADC_TITLE"));
        bean.setAdcType(rs.getInt("ADC_TYPE"));

        UDate frm = UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME"));
        bean.setAddressContactStartDate(frm.getStrYear()  + "/"
                     + frm.getStrMonth()  + "/"
                     + frm.getStrDay());
        bean.setAddressContactStartTime(frm.getStrHour()  + ":"
                          + frm.getStrMinute());
        UDate to = UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME_TO"));
        bean.setAddressContactEndDate(to.getStrYear()  + "/"
                   + to.getStrMonth()  + "/"
                   + to.getStrDay());
        bean.setAddressContactEndTime(to.getStrHour()  + ":"
                         + to.getStrMinute());

        bean.setAdcCttime(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME")));
        bean.setAdcCttimeTo(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME_TO")));
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setAdcBiko(rs.getString("ADC_BIKO"));
        bean.setAdcAuid(rs.getInt("ADC_AUID"));
        bean.setAdcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_ADATE")));
        bean.setAdcEuid(rs.getInt("ADC_EUID"));
        bean.setAdcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_EDATE")));
        bean.setAddressName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
        bean.setAddressNameKana(rs.getString("ADR_SEI_KN") + " " + rs.getString("ADR_MEI_KN"));
        bean.setAddressKaisyaName(rs.getString("ACO_NAME"));
        bean.setAddressKaisyaNameKana(rs.getString("ACO_NAME_KN"));
        bean.setAddressEigyosyoSyubetu(rs.getInt("ABA_TYPE"));
        bean.setAddressEigyosyoSyubetuName(rs.getString("ABA_NAME"));
        bean.setProjectName(rs.getString("PRJ_NAME"));

        if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_OTHER) {
            //その他
            bean.setTypeName(gsMsg.getMessage("cmn.other"));
        } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_TEL) {
            //電話
            bean.setTypeName(gsMsg.getMessage("cmn.phone"));
        } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_MAIL) {
            //メール
            bean.setTypeName(gsMsg.getMessage("cmn.mail"));
        } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_WEB) {
            //WEB
            bean.setTypeName(GSConst.TEXT_CONTYP_WEB);
        } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_MEETING) {
            //打ち合わせ
            bean.setTypeName(gsMsg.getMessage("address.28"));
        }

        return bean;
    }

    /**
     * <br>[機  能] yyyy/mm/dd hh:mm を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @return String
     */
    private String __cleateDspDate(UDate date) {

        if (date == null) {
            return "";
        }

        StringBuilder viewDate = new StringBuilder("");

        viewDate.append(date.getStrYear());
        viewDate.append("/");
        viewDate.append(date.getStrMonth());
        viewDate.append("/");
        viewDate.append(date.getStrDay());
        viewDate.append(" ");
        viewDate.append(date.getStrHour());
        viewDate.append(":");
        viewDate.append(date.getStrMinute());

        return viewDate.toString();
    }
}