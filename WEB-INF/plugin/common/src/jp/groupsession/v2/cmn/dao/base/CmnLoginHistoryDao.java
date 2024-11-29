package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLoginHistoryModel;
import jp.groupsession.v2.cmn.model.base.LoginHistorySearchModel;
import jp.groupsession.v2.man.man050.LoginHistoryCsvRecordListenerImpl;
import jp.groupsession.v2.man.man050.Man050ViewModel;
import jp.groupsession.v2.man.man170.Man170Model;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr090.Usr090Model;

/**
 * <br>[機  能] ログイン履歴DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnLoginHistoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLoginHistoryDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLoginHistoryDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ログイン履歴登録
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean パラメータ
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLoginHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOGIN_HISTORY");
            sql.addSql(" (");
            sql.addSql("   USR_SID,");
            sql.addSql("   CLH_TERMINAL,");
            sql.addSql("   CLH_IP,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   CLH_UID,");
            sql.addSql("   CLH_AUID,");
            sql.addSql("   CLH_ADATE,");
            sql.addSql("   CLH_EUID,");
            sql.addSql("   CLH_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getClhTerminal());
            sql.addStrValue(bean.getClhIp());
            sql.addIntValue(bean.getClhCar());
            sql.addStrValue(bean.getClhUid());
            sql.addIntValue(bean.getClhAuid());
            sql.addDateValue(bean.getClhAdate());
            sql.addIntValue(bean.getClhEuid());
            sql.addDateValue(bean.getClhEdate());

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
     * <br>[機  能] ログイン履歴登録
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param beanList パラメータ
     * @throws SQLException SQL実行例外
     */
    public void insert(List<CmnLoginHistoryModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOGIN_HISTORY");
            sql.addSql(" (");
            sql.addSql("   USR_SID,");
            sql.addSql("   CLH_TERMINAL,");
            sql.addSql("   CLH_IP,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   CLH_UID,");
            sql.addSql("   CLH_AUID,");
            sql.addSql("   CLH_ADATE,");
            sql.addSql("   CLH_EUID,");
            sql.addSql("   CLH_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (CmnLoginHistoryModel bean : beanList) {
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getClhTerminal());
                sql.addStrValue(bean.getClhIp());
                sql.addIntValue(bean.getClhCar());
                sql.addStrValue(bean.getClhUid());
                sql.addIntValue(bean.getClhAuid());
                sql.addDateValue(bean.getClhAdate());
                sql.addIntValue(bean.getClhEuid());
                sql.addDateValue(bean.getClhEdate());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ログイン履歴 件数を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 検索条件
     * @return cnt 該当件数
     * @throws SQLException SQL実行例外
     */
    public int selectHistoryCnt(CmnLoginHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(USR_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(bean.getUsrSid());

            //端末区分
            if (bean.getClhTerminal() > 0) {
                sql.addSql(" and");
                sql.addSql("   CLH_TERMINAL = ?");
                sql.addIntValue(bean.getClhTerminal());
            }

            //キャリア
            if (bean.getClhCar() > 0) {
                sql.addSql(" and");
                sql.addSql("   CLH_CAR = ?");
                sql.addIntValue(bean.getClhCar());
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            pstmt = sql.setParameter(pstmt);
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
     * <br>[機  能] ログイン履歴一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 検索条件
     * @param sortKey ソート対象
     * @param orderKey 並び順
     * @param page ページ
     * @param maxCnt ページ毎の最大表示件数
     * @param reqMdl リクエスト情報
     * @return ログイン履歴一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Man170Model> getLoginHistoryList(CmnLoginHistoryModel bean,
                                                       int sortKey,
                                                       int orderKey,
                                                       int page,
                                                       int maxCnt,
                                                       RequestModel reqMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Man170Model> ret = new ArrayList<Man170Model>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CLH_EDATE,");
            sql.addSql("   CLH_TERMINAL,");
            sql.addSql("   CLH_IP,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   CLH_UID");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(bean.getUsrSid());

            //端末区分
            if (bean.getClhTerminal() > 0) {
                sql.addSql(" and");
                sql.addSql("   CLH_TERMINAL = ?");
                sql.addIntValue(bean.getClhTerminal());
            }

            //キャリア
            if (bean.getClhCar() > 0) {
                sql.addSql(" and");
                sql.addSql("   CLH_CAR = ?");
                sql.addIntValue(bean.getClhCar());
            }

            //オーダー
            String orderStr = " asc";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                orderStr = " desc";
            }

            sql.addSql(" order by");

            //ソートカラム
            switch (sortKey) {
                //ログイン時間
                case GSConstUser.SORT_DATE:
                    sql.addSql("   CLH_EDATE" + orderStr);
                    break;
                //端末
                case GSConstUser.SORT_TERMINAL:
                    sql.addSql("   CLH_TERMINAL" + orderStr);
                    break;
                //IPアドレス
                case GSConstUser.SORT_IP:
                    sql.addSql("   CLH_IP" + orderStr);
                    break;
                //キャリア
                case GSConstUser.SORT_CAR:
                    sql.addSql("   CLH_CAR" + orderStr);
                    break;
                //固体識別番号
                case GSConstUser.SORT_UID:
                    sql.addSql("   CLH_UID" + orderStr);
                    break;
                default:
                    break;
            }

            log__.info(sql.toLogString());

            pstmt =
                getCon().prepareStatement(
                        sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                Man170Model mdl = new Man170Model();

                String loginTime = "";
                UDate loginUd = UDate.getInstanceTimestamp(rs.getTimestamp("CLH_EDATE"));

                if (loginUd != null) {
                    loginTime =
                        UDateUtil.getSlashYYMD(loginUd)
                        + "  "
                        + UDateUtil.getSeparateHMS(loginUd);
                }
                mdl.setLoginTime(loginTime);

                String terminalString = "";
                int terminalKbn = rs.getInt("CLH_TERMINAL");

                GsMessage gsMsg = new GsMessage(reqMdl);
                //メッセージ モバイル
                String mobile = gsMsg.getMessage("main.man120.4");

                switch (terminalKbn) {
                    case GSConstCommon.TERMINAL_KBN_PC:
                        terminalString = GSConstCommon.TERMINAL_KBN_PC_TEXT;
                        break;
                    case GSConstCommon.TERMINAL_KBN_MOBILE:
                        terminalString = mobile;
                        break;
                    default:
                        break;
                }
                mdl.setTerminalName(terminalString);

                mdl.setClhIp(NullDefault.getString(rs.getString("CLH_IP"), ""));

                String carString = "";
                int carKbn = rs.getInt("CLH_CAR");

                switch (carKbn) {
                    case GSConstCommon.CAR_KBN_PC:
                        carString = GSConstCommon.CAR_KBN_PC_TEXT;
                        break;
                    case GSConstCommon.CAR_KBN_DOCOMO:
                        carString = GSConstCommon.CAR_KBN_DOCOMO_TEXT;
                        break;
                    case GSConstCommon.CAR_KBN_KDDI:
                        carString = GSConstCommon.CAR_KBN_KDDI_TEXT;
                        break;
                    case GSConstCommon.CAR_KBN_SOFTBANK:
                        carString = GSConstCommon.CAR_KBN_SOFTBANK_TEXT;
                        break;
                    default:
                        break;
                }
                mdl.setCarName(carString);

                mdl.setClhUid(NullDefault.getString(rs.getString("CLH_UID"), ""));
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
     * <br>[機  能] 固体識別番号履歴の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 検索条件
     * @return cnt 固体識別番号履歴件数
     * @throws SQLException SQL実行例外
     */
    public int getUidHistoryCount(CmnLoginHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(distinct CLH_UID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CLH_TERMINAL = ?");
            sql.addSql(" and");
            sql.addSql("   (CLH_UID <> '' and CLH_UID is not null)");

            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getClhTerminal());

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 固体識別番号履歴を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 検索条件
     * @param sortKey ソート対象
     * @param orderKey 並び順
     * @param page ページ
     * @param maxCnt ページ毎の最大表示件数
     * @return 固体識別番号履歴
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Usr090Model> getUidHistoryList(CmnLoginHistoryModel bean,
                                                     int sortKey,
                                                     int orderKey,
                                                     int page,
                                                     int maxCnt) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Usr090Model> ret = new ArrayList<Usr090Model>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   distinct CLH_UID,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   max(CLH_EDATE) as CLH_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CLH_TERMINAL = ?");
            sql.addSql(" and");
            sql.addSql("   (CLH_UID <> '' and CLH_UID is not null)");
            sql.addSql(" group by");
            sql.addSql("   CLH_UID,");
            sql.addSql("   CLH_CAR");

            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getClhTerminal());

            //オーダー
            String orderStr = " asc";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                orderStr = " desc";
            }

            sql.addSql(" order by");

            //ソートカラム
            switch (sortKey) {
                //ログイン時間
                case GSConstUser.SORT_DATE:
                    sql.addSql("   CLH_EDATE" + orderStr);
                    break;
                //キャリア
                case GSConstUser.SORT_CAR:
                    sql.addSql("   CLH_CAR" + orderStr);
                    break;
                //固体識別番号
                case GSConstUser.SORT_UID:
                    sql.addSql("   CLH_UID" + orderStr);
                    break;
                default:
                    break;
            }

            log__.info(sql.toLogString());

            pstmt =
                getCon().prepareStatement(
                        sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {

                Usr090Model mdl = new Usr090Model();

                String loginTime = "";
                UDate loginUd = UDate.getInstanceTimestamp(rs.getTimestamp("CLH_EDATE"));

                if (loginUd != null) {
                    loginTime =
                        UDateUtil.getSlashYYMD(loginUd)
                        + "  "
                        + UDateUtil.getSeparateHMS(loginUd);
                }
                mdl.setLoginTime(loginTime);

                String carString = "";
                int carKbn = rs.getInt("CLH_CAR");

                switch (carKbn) {
                    case GSConstCommon.CAR_KBN_PC:
                        carString = GSConstCommon.CAR_KBN_PC_TEXT;
                        break;
                    case GSConstCommon.CAR_KBN_DOCOMO:
                        carString = GSConstCommon.CAR_KBN_DOCOMO_TEXT;
                        break;
                    case GSConstCommon.CAR_KBN_KDDI:
                        carString = GSConstCommon.CAR_KBN_KDDI_TEXT;
                        break;
                    case GSConstCommon.CAR_KBN_SOFTBANK:
                        carString = GSConstCommon.CAR_KBN_SOFTBANK_TEXT;
                        break;
                    default:
                        break;
                }
                mdl.setCarName(carString);

                mdl.setClhUid(NullDefault.getString(rs.getString("CLH_UID"), ""));
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
     * <br>[機  能] 検索条件に合わせてログイン履歴一覧を取得
     * <br>[解  説] ソートキーでソート条件(0-3)を切り替えます
     * <br>         並びにオーダーキーで降順(0)昇順(1)を切り替えます
     * <br>[備  考]
     * @param model LoginHistorySearchModel
     * @param reqMdl リクエスト情報
     * @return グループリスト
     * @throws SQLException SQL実行時例外
     */
    public List<Man050ViewModel> selectSearchLoginList(LoginHistorySearchModel model,
                                                    RequestModel reqMdl)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Man050ViewModel> ret = new ArrayList<Man050ViewModel>();
        String order = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.GRP_SID as GRP_SID,");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID,");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as GRP_KBN,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SNO,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_EDATE as USI_LTLGIN,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_TERMINAL as USI_TERM,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_CAR as USI_CAR,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_UID as USI_UID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YKSK,");
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
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_LOGIN_HISTORY ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql("   AND CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(model.getGsid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            if (model.getUsid() > -1) {
                sql.addSql("   AND CMN_USRM.USR_SID = ? ");
                sql.addIntValue(model.getUsid());
            }
            if (model.getTerminal() > 0) {
                sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_TERMINAL = ? ");
                sql.addIntValue(model.getTerminal());
            }
            if (model.getCar() > 0) {
                sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_CAR = ? ");
                sql.addIntValue(model.getCar());
            }

            sql.addSql("   AND CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_LOGIN_HISTORY.USR_SID");

            sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_EDATE ");
            //開始
            String dateFromStr = model.getFrDate().getDateStringForSql();
            StringBuilder dateFromStrBuf = new StringBuilder();
            dateFromStrBuf.append(" 00:");
            dateFromStrBuf.append("00:");
            dateFromStrBuf.append("00.");
            dateFromStrBuf.append("0");
            dateFromStr = dateFromStr + dateFromStrBuf.toString();

            //終了
            String dateToStr = model.getToDate().getDateStringForSql();
            StringBuilder dateToStrBuf = new StringBuilder();
            dateToStrBuf.append(" 23:");
            dateToStrBuf.append("59:");
            dateToStrBuf.append("59.");
            dateToStrBuf.append("999");
            dateToStr = dateToStr + dateToStrBuf.toString();

            sql.addSql("    between ");
            sql.addSql(" '" + dateFromStr + "' ");
            sql.addSql("    and ");
            sql.addSql(" '" + dateToStr + "' ");

            sql.addSql(" order by ");

            //キー値でascとdescを切り替えます
            if (model.getOrderKey() == GSConst.ORDER_KEY_ASC) {
                order = "asc";
            } else if (model.getOrderKey() == GSConst.ORDER_KEY_DESC) {
                order = "desc";
            }
            //ソートカラム
            switch (model.getSortKey()) {
                case GSConstUser.USER_SORT_ID:
                    sql.addSql("   USR_LGID " + order);
                    break;
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("   YAKUSYOKU_EXIST " + order + ", ");
                    sql.addSql("   YAKUSYOKU_SORT " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_LALG:
                    sql.addSql("   USI_LTLGIN " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_TERMINAL:
                    sql.addSql("   CLH_TERMINAL " + order);
                    break;
                case GSConstUser.USER_SORT_CAR:
                    sql.addSql("   CLH_CAR " + order);
                    break;
                case GSConstUser.USER_SORT_UID:
                    sql.addSql("   CLH_UID " + order);
                    break;
                default:
                    break;
            }

            pstmt =
                getCon().prepareStatement(
                        sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (model.getPage() > 1) {
                rs.absolute((model.getPage() - 1) * model.getMaxCnt());
            }

            for (int i = 0; rs.next() && i < model.getMaxCnt(); i++) {
                ret.add(__getSearchLoginHistoryFromRs(rs, reqMdl));
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
     * <p>ログイン履歴情報CSVを出力する。
     * @param rl LoginHistoryCsvRecordListenerImpl
     * @param model LoginHistorySearchModel
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void selectSearchLoginListForCsv(LoginHistoryCsvRecordListenerImpl rl,
                                            LoginHistorySearchModel model,
                                            RequestModel reqMdl)
        throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String order = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.GRP_SID as GRP_SID,");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID,");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as GRP_KBN,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SNO,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_EDATE as USI_LTLGIN,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_TERMINAL as USI_TERM,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_CAR as USI_CAR,");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_UID as USI_UID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YKSK,");
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
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_LOGIN_HISTORY ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql("   AND CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(model.getGsid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            if (model.getUsid() > -1) {
                sql.addSql("   AND CMN_USRM.USR_SID = ? ");
                sql.addIntValue(model.getUsid());
            }
            if (model.getTerminal() > 0) {
                sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_TERMINAL = ? ");
                sql.addIntValue(model.getTerminal());
            }
            if (model.getCar() > 0) {
                sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_CAR = ? ");
                sql.addIntValue(model.getCar());
            }

            sql.addSql("   AND CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_LOGIN_HISTORY.USR_SID");

            sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_EDATE ");
            //開始
            String dateFromStr = model.getFrDate().getDateStringForSql();
            StringBuilder dateFromStrBuf = new StringBuilder();
            dateFromStrBuf.append(" 00:");
            dateFromStrBuf.append("00:");
            dateFromStrBuf.append("00.");
            dateFromStrBuf.append("0");
            dateFromStr = dateFromStr + dateFromStrBuf.toString();

            //終了
            String dateToStr = model.getToDate().getDateStringForSql();
            StringBuilder dateToStrBuf = new StringBuilder();
            dateToStrBuf.append(" 23:");
            dateToStrBuf.append("59:");
            dateToStrBuf.append("59.");
            dateToStrBuf.append("999");
            dateToStr = dateToStr + dateToStrBuf.toString();

            sql.addSql("    between ");
            sql.addSql(" '" + dateFromStr + "' ");
            sql.addSql("    and ");
            sql.addSql(" '" + dateToStr + "' ");

            sql.addSql(" order by ");

            //キー値でascとdescを切り替えます
            if (model.getOrderKey() == GSConst.ORDER_KEY_ASC) {
                order = "asc";
            } else if (model.getOrderKey() == GSConst.ORDER_KEY_DESC) {
                order = "desc";
            }
            //ソートカラム
            switch (model.getSortKey()) {
                case GSConstUser.USER_SORT_ID:
                    sql.addSql("   USR_LGID " + order);
                    break;
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("   YAKUSYOKU_EXIST " + order + ", ");
                    sql.addSql("   YAKUSYOKU_SORT " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_LALG:
                    sql.addSql("   USI_LTLGIN " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_TERMINAL:
                    sql.addSql("   CLH_TERMINAL " + order);
                    break;
                case GSConstUser.USER_SORT_CAR:
                    sql.addSql("   CLH_CAR " + order);
                    break;
                case GSConstUser.USER_SORT_UID:
                    sql.addSql("   CLH_UID " + order);
                    break;
                default:
                    break;
            }

            pstmt =
                getCon().prepareStatement(
                        sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                setLoginHistoryCsvRecordFromRs(rs, rl, reqMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <br>[機  能] 検索条件に合わせてログイン履歴 件数を取得
     * <br>[解  説] ソートキーでソート条件(0-3)を切り替えます
     * <br>         並びにオーダーキーで降順(0)昇順(1)を切り替えます
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid グループSID
     * @param terminal 端末
     * @param car キャリア
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @return グループリスト
     * @throws SQLException SQL実行時例外
     */
    public int selectSearchLoginListCnt(int usid,
                                          int gsid,
                                          int terminal,
                                          int car,
                                          UDate frDate,
                                          UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CMN_USRM.USR_SID) as cnt");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_LOGIN_HISTORY ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql("   AND CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(gsid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            if (usid > -1) {
                sql.addSql("   AND CMN_USRM.USR_SID = ? ");
                sql.addIntValue(usid);
            }
            if (terminal > 0) {
                sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_TERMINAL = ? ");
                sql.addIntValue(terminal);
            }
            if (car > 0) {
                sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_CAR = ? ");
                sql.addIntValue(car);
            }

            sql.addSql("   AND CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_LOGIN_HISTORY.USR_SID");

            sql.addSql("   AND CMN_LOGIN_HISTORY.CLH_EDATE ");

            //開始
            String dateFromStr = frDate.getDateStringForSql();
            StringBuilder dateFromStrBuf = new StringBuilder();
            dateFromStrBuf.append(" 00:");
            dateFromStrBuf.append("00:");
            dateFromStrBuf.append("00.");
            dateFromStrBuf.append("0");
            dateFromStr = dateFromStr + dateFromStrBuf.toString();

            //終了
            String dateToStr = toDate.getDateStringForSql();
            StringBuilder dateToStrBuf = new StringBuilder();
            dateToStrBuf.append(" 23:");
            dateToStrBuf.append("59:");
            dateToStrBuf.append("59.");
            dateToStrBuf.append("999");
            dateToStr = dateToStr + dateToStrBuf.toString();

            sql.addSql("    between ");
            sql.addSql(" '" + dateFromStr + "' ");
            sql.addSql("    and ");
            sql.addSql(" '" + dateToStr + "' ");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 最終ログイン日時が最も古い個体識別番号を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param uid 個体識別番号
     * @return 個体識別番号
     * @throws SQLException SQL実行例外
     */
    public CmnLoginHistoryModel selectOldLoginUid(int usrSid, String uid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CmnLoginHistoryModel clhMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   CLH_TERMINAL,");
            sql.addSql("   CLH_IP,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   CLH_UID,");
            sql.addSql("   CLH_AUID,");
            sql.addSql("   CLH_ADATE,");
            sql.addSql("   CLH_EUID,");
            sql.addSql("   CLH_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(usrSid);
            sql.addSql("   and CLH_UID = ?");
            sql.addStrValue(uid);
            sql.addSql(" order by");
            sql.addSql("   CLH_ADATE asc");
            sql.addSql(" limit");
            sql.addSql("   1");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                clhMdl = __getCmnLoginHistoryFromRs(rs);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return clhMdl;
    }

    /**
     * <br>[機  能] 指定された日付以前のデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delDate 対象日
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(UDate delDate) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   CLH_ADATE <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(delDate);

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
     * <br>[機  能] 指定されたユーザのデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <p>CMN_BELONGM, CMN_GROUPM, CMN_USRM_INFの
     * 結果セットをBeanにセットします<br>
     * Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param reqMdl リクエスト情報
     * @return created CmnBelongmModel
     * @throws SQLException SQL実行例外
     */
    private Man050ViewModel __getSearchLoginHistoryFromRs(ResultSet rs,
                                                        RequestModel reqMdl) throws SQLException {
        Man050ViewModel bean = new Man050ViewModel();
        bean.setGroupsid(rs.getInt("GRP_SID"));
        bean.setUsrsid(rs.getInt("USR_SID"));
        bean.setGrpAdmin(rs.getInt("GRP_KBN"));
        bean.setUsrlgid(rs.getString("USR_LGID"));
        bean.setNamesei(rs.getString("USI_SEI"));
        bean.setNamemei(rs.getString("USI_MEI"));
        bean.setFullName(rs.getString("USI_SEI")
                + " " + rs.getString("USI_MEI"));
        bean.setSyainno(rs.getString("USI_SNO"));
        bean.setYakusyoku(rs.getString("USI_YKSK"));
        bean.setLgintime(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));

        String terminalString = "";
        int terminalKbn = rs.getInt("USI_TERM");

        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ モバイル
        String mobile = gsMsg.getMessage("main.man120.4");

        switch (terminalKbn) {
            case GSConstCommon.TERMINAL_KBN_PC:
                terminalString = GSConstCommon.TERMINAL_KBN_PC_TEXT;
                break;
            case GSConstCommon.TERMINAL_KBN_MOBILE:
                terminalString = mobile;
                break;
            default:
                break;
        }
        bean.setTerminalName(terminalString);

        String carString = "";
        int carKbn = rs.getInt("USI_CAR");

        switch (carKbn) {
//            case GSConstCommon.CAR_KBN_PC:
//                carString = GSConstCommon.CAR_KBN_PC_TEXT;
//                break;
            case GSConstCommon.CAR_KBN_DOCOMO:
                carString = GSConstCommon.CAR_KBN_DOCOMO_TEXT;
                break;
            case GSConstCommon.CAR_KBN_KDDI:
                carString = GSConstCommon.CAR_KBN_KDDI_TEXT;
                break;
            case GSConstCommon.CAR_KBN_SOFTBANK:
                carString = GSConstCommon.CAR_KBN_SOFTBANK_TEXT;
                break;
            default:
                break;
        }
        bean.setCarName(carString);
        bean.setClhUid(rs.getString("USI_UID"));
        return bean;
    }

    /**
     * <p>Select CMN_LOGIN_HISTORY All Data
     * @return List in CMN_LOGIN_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnLoginHistoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLoginHistoryModel> ret = new ArrayList<CmnLoginHistoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CLH_TERMINAL,");
            sql.addSql("   CLH_IP,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   CLH_UID,");
            sql.addSql("   CLH_AUID,");
            sql.addSql("   CLH_ADATE,");
            sql.addSql("   CLH_EUID,");
            sql.addSql("   CLH_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOGIN_HISTORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLoginHistoryFromRs(rs));
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
     * <p>Select CMN_LOGIN_HISTORY All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CMN_LOGIN_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnLoginHistoryModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLoginHistoryModel> ret = new ArrayList<CmnLoginHistoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CLH_TERMINAL,");
            sql.addSql("   CLH_IP,");
            sql.addSql("   CLH_CAR,");
            sql.addSql("   CLH_UID,");
            sql.addSql("   CLH_AUID,");
            sql.addSql("   CLH_ADATE,");
            sql.addSql("   CLH_EUID,");
            sql.addSql("   CLH_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" order by ");
            sql.addSql("   USR_SID asc,");
            sql.addSql("   CLH_ADATE asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLoginHistoryFromRs(rs));
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
     * <p>count CMN_LOGIN_HISTORY All Data
     * @return List in CMN_LOGIN_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOGIN_HISTORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if  (rs.next()) {
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
     * <br>[機  能] 指定したログイン履歴情報のデータサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @param usrSid ユーザSID
     * @param ip IPアドレス
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getLoginHistoryDataSize(UDate date, int usrSid, String ip) throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(CLH_IP)) as IP_SIZE,");
            sql.addSql("   sum(octet_length(CLH_UID)) as UID_SIZE");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CLH_IP = ?");
            sql.addSql(" and ");
            sql.addSql("   CLH_ADATE = ?");
            sql.addIntValue(usrSid);
            sql.addStrValue(ip);
            sql.addDateValue(date);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize += rs.getLong("IP_SIZE");
                dataSize += rs.getLong("UID_SIZE");
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
     * <br>[機  能] 指定日時より前のログイン履歴情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getLoginHistoryDataSize(UDate date) throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(CLH_IP)) as IP_SIZE,");
            sql.addSql("   sum(octet_length(CLH_UID)) as UID_SIZE");
            sql.addSql(" from");
            sql.addSql("   CMN_LOGIN_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   CLH_ADATE <= ?");
            sql.addDateValue(date);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize += rs.getLong("IP_SIZE");
                dataSize += rs.getLong("UID_SIZE");
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
     * <p>Create CMN_LOGIN_HISTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLoginHistoryModel
     * @throws SQLException SQL実行例外
     */
    private CmnLoginHistoryModel __getCmnLoginHistoryFromRs(ResultSet rs) throws SQLException {
        CmnLoginHistoryModel bean = new CmnLoginHistoryModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setClhTerminal(rs.getInt("CLH_TERMINAL"));
        bean.setClhIp(rs.getString("CLH_IP"));
        bean.setClhCar(rs.getInt("CLH_CAR"));
        bean.setClhUid(rs.getString("CLH_UID"));
        bean.setClhAuid(rs.getInt("CLH_AUID"));
        bean.setClhAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CLH_ADATE")));
        bean.setClhEuid(rs.getInt("CLH_EUID"));
        bean.setClhEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CLH_EDATE")));
        return bean;
    }

    /**
     * <p>結果セットからCmnLoginHistoryModelを取得する。
     * @param rs 結果セット
     * @param rl LoginHistoryCsvRecordListenerImpl
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setLoginHistoryCsvRecordFromRs(
            ResultSet rs, LoginHistoryCsvRecordListenerImpl rl,
            RequestModel reqMdl)
        throws SQLException, CSVException {

        Man050ViewModel bean = new Man050ViewModel();
        bean.setGroupsid(rs.getInt("GRP_SID"));
        bean.setUsrsid(rs.getInt("USR_SID"));
        bean.setGrpAdmin(rs.getInt("GRP_KBN"));
        bean.setUsrlgid(rs.getString("USR_LGID"));
        bean.setNamesei(rs.getString("USI_SEI"));
        bean.setNamemei(rs.getString("USI_MEI"));
        bean.setFullName(rs.getString("USI_SEI")
                + " " + rs.getString("USI_MEI"));
        bean.setSyainno(rs.getString("USI_SNO"));
        bean.setYakusyoku(rs.getString("USI_YKSK"));
        bean.setLgintimeStr(convertUDateGS2(
                UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")), reqMdl));

        String terminalString = "";
        int terminalKbn = rs.getInt("USI_TERM");

        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ モバイル
        String mobile = gsMsg.getMessage("main.man120.4");

        switch (terminalKbn) {
            case GSConstCommon.TERMINAL_KBN_PC:
                terminalString = GSConstCommon.TERMINAL_KBN_PC_TEXT;
                break;
            case GSConstCommon.TERMINAL_KBN_MOBILE:
                terminalString = mobile;
                break;
            default:
                break;
        }
        bean.setTerminalName(terminalString);

        String carString = "";
        int carKbn = rs.getInt("USI_CAR");

        switch (carKbn) {
            case GSConstCommon.CAR_KBN_PC:
                carString = GSConstCommon.CAR_KBN_PC_TEXT;
                break;
            case GSConstCommon.CAR_KBN_DOCOMO:
                carString = GSConstCommon.CAR_KBN_DOCOMO_TEXT;
                break;
            case GSConstCommon.CAR_KBN_KDDI:
                carString = GSConstCommon.CAR_KBN_KDDI_TEXT;
                break;
            case GSConstCommon.CAR_KBN_SOFTBANK:
                carString = GSConstCommon.CAR_KBN_SOFTBANK_TEXT;
                break;
            default:
                break;
        }
        bean.setCarName(carString);
        bean.setClhUid(NullDefault.getString(rs.getString("USI_UID"), ""));
        rl.setRecord(bean);
    }

    /**
     * <br>[機  能] UDate形式の時間を表示形式に変換します。
     * <br>[解  説] 例）20060101235959 → 2006/01/01 23:59:59
     * <br>[備  考]
     * @param udate UDate
     * @param reqMdl RequestModel
     * @return String
     */
    public static String convertUDateGS2(UDate udate, RequestModel reqMdl) {
        String strSdate = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (udate == null) {
            return gsMsg.getMessage("main.src.man050.4");
        }
        strSdate = UDateUtil.getSlashYYMD(udate)
                 + "  "
                 + UDateUtil.getSeparateHMS(udate);
        log__.debug("生成結果：" + strSdate);
        return strSdate;
    }
}