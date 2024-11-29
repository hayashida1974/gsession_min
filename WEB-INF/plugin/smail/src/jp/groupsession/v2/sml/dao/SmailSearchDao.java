package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.sml030.HashControlModel;
import jp.groupsession.v2.sml.sml030.Sml030Model;
import jp.groupsession.v2.sml.sml090.Sml090SearchParameterModel;

/**
 * <br>[機  能] ショートメールの検索に関する処理を実装したDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmailSearchDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmailSearchDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public SmailSearchDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public SmailSearchDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 受信モードの検索全データ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータモデル
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchDataCountJushin(Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SML_JMEIS.SMJ_SID) as cnt");
            __createDetailSqlJushin(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;

    }

    /**
     * <br>[機  能] 受信モードの検索メッセージデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータ
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailModel> getSearchDataJushin(Sml090SearchParameterModel prmModel)
        throws SQLException {

        int offset = prmModel.getOffset();
        int limit = prmModel.getLimit();

        log__.debug("----------------------------");
        log__.debug("offset         :" + offset);
        log__.debug("limit          :" + limit);
        log__.debug("----------------------------");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();
        con = getCon();
        final Map<Integer, SmailModel> mailMap = new HashMap<>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(GSConstSmail.TAB_DSP_MODE_JUSIN + " as mailKbn,");
            sql.addSql("   SML_JMEIS.SMJ_SID as smjSid,");
            sql.addSql("   SML_JMEIS.SMJ_OPKBN as smjOpkbn,");
            sql.addSql("   SML_JMEIS.SMJ_RTN_KBN as smjRtnKbn,");
            sql.addSql("   SML_JMEIS.SMJ_FW_KBN as smjFwKbn,");
            sql.addSql("   SML_SMEIS.SMS_MARK as smsMark,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle,");
            sql.addSql("   SML_SMEIS.SMS_SDATE as smsSdate,");
            sql.addSql("   SML_SMEIS.SMS_SIZE as mailSize,");
            sql.addSql("   SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("   CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("   CMN_USRM_INF.USR_SID as usrSid,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as usiPictKf");
            __createDetailSqlJushin(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);

            __createSortOrderSql(sql, prmModel, GSConstSmail.TAB_DSP_MODE_JUSIN);

            if (offset <= 1) {
                offset = 0;
            }
            if (offset > 1) {
                offset = offset - 1;
            }
            sql.setPagingValue(offset, limit);

            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next())  {
                SmailModel mdl = new SmailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmlSid(rs.getInt("smjSid"));
                mdl.setSmjOpkbn(rs.getInt("smjOpkbn"));
                mdl.setReturnKbn(rs.getInt("smjRtnKbn"));
                mdl.setFwKbn(rs.getInt("smjFwKbn"));
                mdl.setSmsMark(rs.getInt("smsMark"));
                mdl.setSmsTitle(rs.getString("smsTitle"));
                mdl.setSmsSize(rs.getLong("mailSize"));
                UDate sDate = UDate.getInstanceTimestamp(rs.getTimestamp("smsSdate"));
                mdl.setSmsSdate(sDate);
                mdl.setStrSdate(
                        UDateUtil.getSlashYYMD(sDate) + " " + UDateUtil.getSeparateHMS(sDate));
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setBinFileSid(rs.getLong("binSid"));
                mdl.setPhotoFileDsp(rs.getInt("usiPictKf"));
                mdl.setMailKey(rs.getString("mailKbn")
                    + StringUtil.toDecFormat(rs.getInt("smjSid"), GSConstSmail.MAIL_KEY_FORMAT));

                ret.add(mdl);
                mailMap.put(mdl.getSmlSid(), mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //添付ファイル数取得
        SmlBinDao binDao = new SmlBinDao(con);
        binDao.getBinFileCnt(mailMap.keySet())
        .entrySet().stream()
        .filter(entry -> mailMap.containsKey(entry.getKey()))
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setBinCnt(entry.getValue());
        });

        //ラベル取得
        new SmailDao(con).getSmjLabelMap(mailMap.keySet(), prmModel.getMySid())
        .entrySet().stream()
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setLabelList(entry.getValue());
        });

        return ret;
    }

    /**
     * <br>[機  能] 全データハッシュ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSid メールSID
     * @param prmModel 検索パラメータモデル
     * @return ret 次、または前のメッセージSID
     * @throws SQLException SQL実行例外
     */
    public HashControlModel createJAllDataHash(int mailSid,
            Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashControlModel ret = new HashControlModel();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_JMEIS.SMJ_SID as smjSid,");
            sql.addSql("   SML_JMEIS.SMJ_OPKBN as smjOpkbn,");
            sql.addSql("   SML_SMEIS.SMS_MARK as smsMark,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle,");
            sql.addSql("   SML_SMEIS.SMS_SDATE as smsSdate,");
            sql.addSql("   SML_SMEIS.SMS_SIZE as smsSize,");
            sql.addSql("   CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as usiMei");
            __createDetailSqlJushin(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);
            __createSortOrderSql(sql, prmModel, GSConstSmail.TAB_DSP_MODE_JUSIN);

            pstmt = con.prepareStatement(sql.toSqlString());

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int allOffset = 0;
            int pageOffset = 0;
            int page = 1;
            int rowNum = 0;
            HashMap<Integer, Sml030Model> map =
                new HashMap<Integer, Sml030Model>();

            int limit = prmModel.getLimit();
            while (rs.next()) {
                allOffset += 1;
                pageOffset += 1;
                if (pageOffset > limit) {
                    pageOffset = 1;
                    page += 1;
                }

                Sml030Model mdl = new Sml030Model();
                mdl.setMailSid(rs.getInt("smjSid"));
                mdl.setPageNum(page);

                if (mdl.getMailSid() == mailSid) {
                    rowNum = allOffset;
                }
                map.put(allOffset, mdl);
            }

            ret.setMap(map);
            ret.setRowNum(rowNum);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 受信メッセージ用の検索部SQLを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param prmModel 検索パラメータ
     * @param jtKbn 状態区分
     * @throws SQLException SQL実行時例外
     */
    private void __createDetailSqlJushin(SqlBuffer sql,
            Sml090SearchParameterModel prmModel, int jtKbn) throws SQLException {

        //セッションユーザSID
        int mySid = prmModel.getMySid();
        //開封区分
        int openKbn = prmModel.getOpenKbn();


        sql.addSql(" from");
        sql.addSql("   SML_ACCOUNT ");
        sql.addSql("     left join ");
        sql.addSql("       CMN_USRM ");
        sql.addSql("     on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
        sql.addSql("       left join ");
        sql.addSql("         CMN_USRM_INF ");
        sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID, ");
        sql.addSql("   SML_SMEIS,");
        sql.addSql("   SML_JMEIS");

        sql.addSql(" where ");
        sql.addSql("   SML_JMEIS.SAC_SID = ?");
        sql.addIntValue(mySid);
        sql.addSql(" and");
        sql.addSql("   SML_JMEIS.SMJ_JKBN = ?");
        sql.addIntValue(jtKbn);
        __sqlSetUpForSoushinsya(sql, prmModel); //差出人
        __sqlSetUpForMark(sql, prmModel); //マーク
        __sqlSetUpForKeyword(sql, prmModel); //キーワード
        __sqlSetUpForSmlSid(sql, prmModel); // ショートメールSID
        sql.addSql(" and");
        sql.addSql("   SML_JMEIS.SMJ_SID = SML_SMEIS.SMS_SID");
        sql.addSql(" and");
        sql.addSql("   SML_SMEIS.SAC_SID = SML_ACCOUNT.SAC_SID");

        if (GSConstSmail.OPKBN_UNOPENED == openKbn) {
            //未読のみ
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_OPKBN = " + GSConstSmail.OPKBN_UNOPENED);
        } else if (GSConstSmail.OPKBN_OPENED == openKbn) {
            //既読のみ
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_OPKBN = " + GSConstSmail.OPKBN_OPENED);
        }

        // 日時指定
        UDate fromDate = prmModel.getFromDate(); // 日時範囲指定(開始)
        UDate toDate   = prmModel.getToDate();   // 日時範囲指定(終了)
        if (fromDate != null) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_SDATE >= ?");
            sql.addDateValue(fromDate);
        }
        if (toDate != null) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_SDATE <= ?");
            sql.addDateValue(toDate);
        }
    }

    /**
     * <br>[機  能] 送信モードの検索全データ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータモデル
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchDataCountSoushin(Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(DISTINCT SML_SMEIS.SMS_SID) as cnt");
            __createDetailSqlSoushin(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;

    }

    /**
     * <br>[機  能] 送信モードの検索メッセージデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータ
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailModel> getSearchDataSoushin(Sml090SearchParameterModel prmModel)
        throws SQLException {

        int offset = prmModel.getOffset();
        int limit = prmModel.getLimit();

        log__.debug("----------------------------");
        log__.debug("offset         :" + offset);
        log__.debug("limit          :" + limit);
        log__.debug("----------------------------");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();
        final Map<Integer, SmailModel> mailMap = new HashMap<>();
        con = getCon();
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" DISTINCT ");
            sql.addSql(GSConstSmail.TAB_DSP_MODE_SOSIN + " as mailKbn,");
            sql.addSql("   SML_SMEIS.SMS_SID as smsSid,");
            sql.addSql("   SML_SMEIS.SMS_MARK as smsMark,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle,");
            sql.addSql("   SML_SMEIS.SMS_SIZE as mailSize,");
            sql.addSql("   SML_SMEIS.SMS_SDATE as smsSdate");
            __createDetailSqlSoushin(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);
            __createSortOrderSql(sql, prmModel, GSConstSmail.TAB_DSP_MODE_SOSIN);
            if (offset <= 1) {
                offset = 0;
            }
            if (offset > 1) {
                offset = offset - 1;
            }
            sql.setPagingValue(offset, limit);

            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            SmailDao smlDao = new SmailDao(con);
            while (rs.next())  {
                SmailModel mdl = new SmailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmlSid(rs.getInt("smsSid"));
                mdl.setSmsMark(rs.getInt("smsMark"));
                mdl.setSmsTitle(rs.getString("smsTitle"));
                mdl.setSmsSize(rs.getLong("mailSize"));
                UDate sDate = UDate.getInstanceTimestamp(rs.getTimestamp("smsSdate"));
                mdl.setSmsSdate(sDate);
                mdl.setStrSdate(
                        UDateUtil.getSlashYYMD(sDate) + " " + UDateUtil.getSeparateHMS(sDate));
                mdl.setAtesakiList(
                        smlDao.getAtesakiList(mdl.getSmlSid(),
                                GSConstSmail.SML_SEND_KBN_ATESAKI,
                                sortMdl));

                mdl.setMailKey(rs.getString("mailKbn")
                        + StringUtil.toDecFormat(rs.getInt("smsSid"),
                                                GSConstSmail.MAIL_KEY_FORMAT));

                ret.add(mdl);
                mailMap.put(mdl.getSmlSid(), mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //添付ファイル数取得
        SmlBinDao binDao = new SmlBinDao(con);
        binDao.getBinFileCnt(mailMap.keySet())
        .entrySet().stream()
        .filter(entry -> mailMap.containsKey(entry.getKey()))
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setBinCnt(entry.getValue());
        });
        //ラベル取得
        new SmailDao(con).getSmsLabelMap(mailMap.keySet(), prmModel.getMySid())
        .entrySet().stream()
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setLabelList(entry.getValue());
        });

        return ret;
    }


    /**
     * <br>[機  能] 全データハッシュ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSid メールSID
     * @param prmModel 検索パラメータモデル
     * @return ret 次、または前のメッセージSID
     * @throws SQLException SQL実行例外
     */
    public HashControlModel createSAllDataHash(int mailSid,
            Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashControlModel ret = new HashControlModel();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql(" DISTINCT ");
            sql.addSql("   SML_SMEIS.SMS_SID as smsSid,");
            sql.addSql("   SML_SMEIS.SMS_MARK as smsMark,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle,");
            sql.addSql("   SML_SMEIS.SMS_SDATE as smsSdate,");
            sql.addSql("   SML_SMEIS.SMS_SIZE as smsSize");
            __createDetailSqlSoushin(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);
            __createSortOrderSql(sql, prmModel, GSConstSmail.TAB_DSP_MODE_SOSIN);

            pstmt = con.prepareStatement(sql.toSqlString());

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int allOffset = 0;
            int pageOffset = 0;
            int page = 1;
            int rowNum = 0;
            HashMap<Integer, Sml030Model> map =
                new HashMap<Integer, Sml030Model>();

            int limit = prmModel.getLimit();
            while (rs.next()) {
                allOffset += 1;
                pageOffset += 1;
                if (pageOffset > limit) {
                    pageOffset = 1;
                    page += 1;
                }

                Sml030Model mdl = new Sml030Model();
                mdl.setMailSid(rs.getInt("smsSid"));
                mdl.setPageNum(page);

                if (mdl.getMailSid() == mailSid) {
                    rowNum = allOffset;
                }
                map.put(allOffset, mdl);
            }

            ret.setMap(map);
            ret.setRowNum(rowNum);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 送信メッセージ用の検索部SQLを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param prmModel 検索パラメータ
     * @param jtKbn 状態区分
     * @throws SQLException 実行時例外
     */
    private void __createDetailSqlSoushin(SqlBuffer sql,
            Sml090SearchParameterModel prmModel, int jtKbn) throws SQLException {

        //セッションユーザSID
        int mySid = prmModel.getMySid();


        sql.addSql("    from");
        sql.addSql("     (");
        sql.addSql("      select * from SML_SMEIS");
        sql.addSql("      where");
        sql.addSql("          SML_SMEIS.SAC_SID = ?");
        sql.addIntValue(mySid);
        sql.addSql("       and");
        sql.addSql("         SML_SMEIS.SMS_JKBN = ?");
        sql.addIntValue(jtKbn);
        sql.addSql("      ) SML_SMEIS");
        sql.addSql("     left join");
        sql.addSql("       (");
        sql.addSql("         select");
        sql.addSql("           SML_ACCOUNT.SAC_SID as SAC_SID,");
        sql.addSql("           CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("           CMN_USRM_INF.USR_SID as USR_SID,");
        sql.addSql("           CMN_USRM_INF.USI_SEI as USI_SEI,");
        sql.addSql("           CMN_USRM_INF.USI_MEI as USI_MEI,");
        sql.addSql("           CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("           CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("           CMN_USRM_INF.BIN_SID as BIN_SID,");
        sql.addSql("           CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF");
        sql.addSql("         from");
        sql.addSql("           SML_ACCOUNT");
        sql.addSql("              left join");
        sql.addSql("                CMN_USRM");
        sql.addSql("              on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
        sql.addSql("                left join");
        sql.addSql("                  CMN_USRM_INF");
        sql.addSql("                on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("           where SML_ACCOUNT.SAC_SID = ?");
        sql.addIntValue(mySid);
        sql.addSql("          ) SOSHIN_USRM_INF");
        sql.addSql("        on SML_SMEIS.SAC_SID = SOSHIN_USRM_INF.SAC_SID,");
        sql.addSql("     SML_ACCOUNT");
        sql.addSql("       left join");
        sql.addSql("         CMN_USRM");
        sql.addSql("       on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
        sql.addSql("         left join");
        sql.addSql("           CMN_USRM_INF");
        sql.addSql("         on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("       where");
        sql.addSql("         SML_ACCOUNT.SAC_SID = ?");
        sql.addIntValue(mySid);
        sql.addSql("       and");
        sql.addSql("         SML_SMEIS.SAC_SID = SML_ACCOUNT.SAC_SID");
        sql.addSql("       and ");
        sql.addSql("         SOSHIN_USRM_INF.SAC_SID = SML_SMEIS.SAC_SID ");


        // 日時指定
        UDate fromDate = prmModel.getFromDate();
        UDate toDate   = prmModel.getToDate();
        if (fromDate != null) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_SDATE >= ?");
            sql.addDateValue(fromDate);
        }
        if (toDate != null) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_SDATE <= ?");
            sql.addDateValue(toDate);
        }

        __sqlSetUpForAtesaki(sql, prmModel); //宛先
        __sqlSetUpForMark(sql, prmModel); //マーク
        __sqlSetUpForKeyword(sql, prmModel); //キーワード
        __sqlSetUpForSmlSid(sql, prmModel); //ショートメールSID

    }


    /**
     * <br>[機  能] ソートオーダー部のSQL文を構築する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param prmModel Sml090SearchParameterModel
     * @param kbn メールボックス区分
     */
    private void __createSortOrderSql(SqlBuffer sql,
            Sml090SearchParameterModel prmModel, String kbn) {

        int sortKey1 = prmModel.getSearchSortKey1();
        int orderKey1 = prmModel.getSearchOrderKey1();
        int sortKey2 = prmModel.getSearchSortKey2();
        int orderKey2 = prmModel.getSearchOrderKey2();
        log__.debug("----------------------------");
        log__.debug("sort/order[1]  :" + sortKey1 + " / " + orderKey1);
        log__.debug("sort/order[2]  :" + sortKey2 + " / " + orderKey2);
        log__.debug("----------------------------");

        sql.addSql(" order by");

        //オーダー1
        String orderStr1 = "";
        if (orderKey1 == GSConstSmail.ORDER_KEY_ASC) {
            orderStr1 = "  asc";
        } else if (orderKey1 == GSConstSmail.ORDER_KEY_DESC) {
            orderStr1 = "  desc";
        }

        //ソートカラム1
        switch (sortKey1) {
            //マーク区分
            case GSConstSmail.MSG_SORT_KEY_MARK:
                sql.addSql("  SML_SMEIS.SMS_MARK");
                sql.addSql(orderStr1);
                break;
            //件名
            case GSConstSmail.MSG_SORT_KEY_TITLE:
//                if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(kbn)) {
//                    sql.addSql("  SML_JMEIS.SMJ_OPKBN asc,");
//                }
                sql.addSql("  SML_SMEIS.SMS_TITLE");
                sql.addSql(orderStr1);
                break;
            //差出人
            case GSConstSmail.MSG_SORT_KEY_NAME:
                sql.addSql("  (case when CMN_USRM_INF.USI_SEI_KN is null then 1");
                sql.addSql("      else 0 end) " + orderStr1 + ",");
                sql.addSql("  CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr1);
                sql.addSql(",");
                sql.addSql("  CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr1);
                sql.addSql(",");
                sql.addSql("  SML_ACCOUNT.SAC_NAME");
                sql.addSql(orderStr1);

                break;
            //宛先
            case GSConstSmail.MSG_SORT_KEY_ATESAKI:

                sql.addSql("  CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr1);
                sql.addSql(",");
                sql.addSql("  CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr1);
                break;

            //容量
            case GSConstSmail.MSG_SORT_KEY_SIZE:
                sql.addSql("  SML_SMEIS.SMS_SIZE");
                sql.addSql(orderStr1);
                break;

            //日時
            case GSConstSmail.MSG_SORT_KEY_DATE:
                sql.addSql("  SML_SMEIS.SMS_SDATE");
                sql.addSql(orderStr1);
                break;
            default:
                sql.addSql("  SML_SMEIS.SMS_SDATE");
                sql.addSql(orderStr1);
                break;
        }

        //オーダー2
        String orderStr2 = "";
        if (orderKey2 == GSConstSmail.ORDER_KEY_ASC) {
            orderStr2 = "  asc";
        } else if (orderKey2 == GSConstSmail.ORDER_KEY_DESC) {
            orderStr2 = "  desc";
        }

        //ソートカラム2
        switch (sortKey2) {
            //マーク区分
            case GSConstSmail.MSG_SORT_KEY_MARK:
                sql.addSql(",  SML_SMEIS.SMS_MARK");
                sql.addSql(orderStr2);
                break;
            //件名
            case GSConstSmail.MSG_SORT_KEY_TITLE:
                if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(kbn)) {
                    sql.addSql(",  SML_JMEIS.SMJ_OPKBN asc");
                }
                sql.addSql(",  SML_SMEIS.SMS_TITLE");
                sql.addSql(orderStr2);
                break;
            //差出人
            case GSConstSmail.MSG_SORT_KEY_NAME:
                sql.addSql(",  (case when CMN_USRM_INF.USI_SEI_KN is null then 1");
                sql.addSql("      else 0 end) " + orderStr2 + ",");
                sql.addSql("  CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr2);
                sql.addSql(",");
                sql.addSql("  CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr2);
                sql.addSql(",");
                sql.addSql("  SML_ACCOUNT.SAC_NAME");
                sql.addSql(orderStr2);
                break;
            //宛先
            case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                sql.addSql(",  CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr2);
                sql.addSql(",");
                sql.addSql("  CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr2);
                break;

            //容量
            case GSConstSmail.MSG_SORT_KEY_SIZE:
                sql.addSql(",  SML_SMEIS.SMS_SIZE");
                sql.addSql(orderStr2);
                break;

            //送信日時
            case GSConstSmail.MSG_SORT_KEY_DATE:
                sql.addSql(",  SML_SMEIS.SMS_SDATE");
                sql.addSql(orderStr2);
                break;
            default:
                sql.addSql(",  SML_SMEIS.SMS_SDATE");
                sql.addSql(orderStr2);
                break;
        }
    }


    /**
     * <br>[機  能] 草稿モードの検索全データ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータモデル
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchDataCountSoukou(Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(DISTINCT SML_WMEIS.SMW_SID) as cnt");
            __createDetailSqlSoukou(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;

    }

    /**
     * <br>[機  能] 草稿モードの検索メッセージデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータ
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailModel> getSearchDataSoukou(Sml090SearchParameterModel prmModel)
        throws SQLException {

        int offset = prmModel.getOffset();
        int limit = prmModel.getLimit();
        int orderKey1 = prmModel.getSearchOrderKey1();
        int orderKey2 = prmModel.getSearchOrderKey2();
        int sortKey1 = prmModel.getSearchSortKey1();
        int sortKey2 = prmModel.getSearchSortKey2();

        log__.debug("----------------------------");
        log__.debug("offset         :" + offset);
        log__.debug("limit          :" + limit);
        log__.debug("----------------------------");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();
        con = getCon();
        final Map<Integer, SmailModel> mailMap = new HashMap<>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" DISTINCT ");
            sql.addSql(GSConstSmail.TAB_DSP_MODE_SOKO + " as mailKbn,");
            sql.addSql("   SML_WMEIS.SMW_SID as smwSid,");
            sql.addSql("   SML_WMEIS.SMW_MARK as smwMark,");
            sql.addSql("   SML_WMEIS.SMW_TITLE as smwTitle,");
            sql.addSql("   SML_WMEIS.SMW_EDATE as smwEdate,");
            sql.addSql("   SML_WMEIS.SMW_SIZE as mailSize");
            __createDetailSqlSoukou(sql, prmModel, GSConstSmail.SML_JTKBN_TOROKU);
            sql.addSql(" order by");


            //オーダー1 -----------------------------------------------
            String orderStr1 = "";
            if (orderKey1 == GSConstSmail.ORDER_KEY_ASC) {
                orderStr1 = "  asc";
            } else if (orderKey1 == GSConstSmail.ORDER_KEY_DESC) {
                orderStr1 = "  desc";
            }

            //ソートカラム1
            switch (sortKey1) {
                //マーク区分
                case GSConstSmail.MSG_SORT_KEY_MARK:
                    sql.addSql("  SML_WMEIS.SMW_MARK");
                    sql.addSql(orderStr1);
                    break;
                //件名
                case GSConstSmail.MSG_SORT_KEY_TITLE:
                    sql.addSql("  SML_WMEIS.SMW_TITLE");
                    sql.addSql(orderStr1);
                    break;
                //差出人
                case GSConstSmail.MSG_SORT_KEY_NAME:
                    sql.addSql("  ASAKSEI_KN");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  ASAK.ASAKMEI_KN");
                    sql.addSql(orderStr1);
                    break;
                //宛先
                case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                    sql.addSql("  ASAK.ASAKSEI_KN");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  ASAK.ASAKMEI_KN");
                    sql.addSql(orderStr1);
                    break;
                //容量
                case GSConstSmail.MSG_SORT_KEY_SIZE:
                    sql.addSql("  SML_WMEIS.SMW_SIZE");
                    sql.addSql(orderStr1);
                    break;
                //更新日時
                case GSConstSmail.MSG_SORT_KEY_DATE:
                    sql.addSql("  SML_WMEIS.SMW_EDATE");
                    sql.addSql(orderStr1);
                    break;
                default:
                    break;
            }
            //オーダー2 -----------------------------------------------
            String orderStr2 = "";
            if (orderKey2 == GSConstSmail.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else if (orderKey2 == GSConstSmail.ORDER_KEY_DESC) {
                orderStr2 = "  desc";
            }

            //ソートカラム2
            switch (sortKey2) {
                //マーク区分
                case GSConstSmail.MSG_SORT_KEY_MARK:
                    sql.addSql(",  SML_WMEIS.SMW_MARK");
                    sql.addSql(orderStr2);
                    break;
                //件名
                case GSConstSmail.MSG_SORT_KEY_TITLE:
                    sql.addSql(",  SML_WMEIS.SMW_TITLE");
                    sql.addSql(orderStr2);
                    break;
                //差出人
                case GSConstSmail.MSG_SORT_KEY_NAME:
                    sql.addSql(",  ASAKSEI_KN");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  ASAK.ASAKMEI_KN");
                    sql.addSql(orderStr2);
                    break;
                //宛先
                case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                    sql.addSql(",  ASAK.ASAKSEI_KN");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  ASAK.ASAKMEI_KN");
                    sql.addSql(orderStr2);
                    break;
                //容量
                case GSConstSmail.MSG_SORT_KEY_SIZE:
                    sql.addSql(",  SML_WMEIS.SMW_SIZE");
                    sql.addSql(orderStr2);
                    break;
                //更新日時
                case GSConstSmail.MSG_SORT_KEY_DATE:
                    sql.addSql(",  SML_WMEIS.SMW_EDATE");
                    sql.addSql(orderStr2);
                    break;
                default:
                    break;
            }
            //-----------------------------------------------------------
            if (offset <= 1) {
                offset = 0;
            }
            if (offset > 1) {
                offset = offset - 1;
            }
            sql.setPagingValue(offset, limit);
            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            SmailDao smlDao = new SmailDao(con);
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            while (rs.next())  {
                SmailModel mdl = new SmailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmlSid(rs.getInt("smwSid"));
                mdl.setSmsMark(rs.getInt("smwMark"));
                mdl.setSmsTitle(rs.getString("smwTitle"));
                mdl.setSmsSize(rs.getLong("mailSize"));
                UDate sDate = UDate.getInstanceTimestamp(rs.getTimestamp("smwEdate"));
                mdl.setSmsSdate(sDate);
                mdl.setStrSdate(
                        UDateUtil.getSlashYYMD(sDate) + " " + UDateUtil.getSeparateHMS(sDate));

                mdl.setAtesakiList(
                        smlDao.getSitagakiAtesakiList(mdl.getSmlSid(),
                                                    GSConstSmail.SML_SEND_KBN_ATESAKI, sortMdl));
                mdl.setMailKey(rs.getString("mailKbn")
                        + StringUtil.toDecFormat(rs.getInt("smwSid"),
                                                GSConstSmail.MAIL_KEY_FORMAT));
                ret.add(mdl);
                mailMap.put(mdl.getSmlSid(), mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //添付ファイル数取得
        SmlBinDao binDao = new SmlBinDao(con);
        binDao.getBinFileCnt(mailMap.keySet())
        .entrySet().stream()
        .filter(entry -> mailMap.containsKey(entry.getKey()))
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setBinCnt(entry.getValue());
        });

        //ラベル取得
        new SmailDao(con).getSmwLabelMap(mailMap.keySet(), prmModel.getMySid())
        .entrySet().stream()
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setLabelList(entry.getValue());
        });

        return ret;
    }

    /**
     * <br>[機  能] 草稿メッセージ用の検索部SQLを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param prmModel 検索パラメータ
     * @param jtKbn 状態区分
     * @throws SQLException 実行時例外
     */
    private void __createDetailSqlSoukou(SqlBuffer sql,
            Sml090SearchParameterModel prmModel, int jtKbn) throws SQLException {

        //セッションユーザSID
        int mySid = prmModel.getMySid();


        sql.addSql(" from");
        sql.addSql("   SML_ACCOUNT ");
        sql.addSql("     left join ");
        sql.addSql("       CMN_USRM ");
        sql.addSql("     on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
        sql.addSql("       left join ");
        sql.addSql("         CMN_USRM_INF ");
        sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID, ");
        sql.addSql("   SML_WMEIS");
        sql.addSql("     left join");
        sql.addSql("       (");
        sql.addSql("         select");
        sql.addSql("           SML_ASAK.SMS_SID as SMS_SID,");
        sql.addSql("           SML_ACCOUNT.SAC_SID as ASAKSACSID,");
        sql.addSql("           SML_ACCOUNT.SAC_NAME as ASAKSACNAME,");
        sql.addSql("           SML_ACCOUNT.SAC_JKBN as ASAKSACJKBN,");
        sql.addSql("           CMN_USRM.USR_JKBN as ASAKUJKBN,");
        sql.addSql("           CMN_USRM_INF.USR_SID as INFUSRSID,");
        sql.addSql("           CMN_USRM_INF.USI_SEI as ASAKSEI,");
        sql.addSql("           CMN_USRM_INF.USI_MEI as ASAKMEI,");
        sql.addSql("           CMN_USRM_INF.USI_SEI_KN as ASAKSEI_KN,");
        sql.addSql("           CMN_USRM_INF.USI_MEI_KN as ASAKMEI_KN,");
        sql.addSql("           CMN_USRM_INF.BIN_SID as ASAKBINSID,");
        sql.addSql("           CMN_USRM_INF.USI_PICT_KF as ASAKUSIPICTKF,");
        sql.addSql("           SML_ASAK.SAC_SID as ASAKUSID,");
        sql.addSql("           SML_ASAK.SMJ_SENDKBN as ASAKSENDKBN");
        sql.addSql("         from");
        sql.addSql("           SML_ASAK,");
        sql.addSql("           SML_ACCOUNT ");
        sql.addSql("             left join ");
        sql.addSql("               CMN_USRM ");
        sql.addSql("              on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
        sql.addSql("              left join ");
        sql.addSql("                CMN_USRM_INF ");
        sql.addSql("               on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("          where");
        sql.addSql("            SML_ASAK.SAC_SID = SML_ACCOUNT.SAC_SID");
        sql.addSql("        ) ASAK");
        sql.addSql("      on SML_WMEIS.SMW_SID = ASAK.SMS_SID");

        sql.addSql("  where ");
        sql.addSql("    SML_WMEIS.SAC_SID = ?");
        sql.addIntValue(mySid);
        sql.addSql("   and");
        sql.addSql("     SML_WMEIS.SMW_JKBN = ?");
        sql.addIntValue(jtKbn);
        sql.addSql("   and");
        sql.addSql("    SML_WMEIS.SAC_SID = SML_ACCOUNT.SAC_SID");

        // 日時指定
        UDate fromDate = prmModel.getFromDate();
        UDate toDate   = prmModel.getToDate();
        if (fromDate != null) {
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_EDATE >= ?");
            sql.addDateValue(fromDate);
        }
        if (toDate != null) {
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_EDATE <= ?");
            sql.addDateValue(toDate);
        }



        //宛先---------------------------------------------------------
        String[] atesakis = prmModel.getAtesaki();

        if (atesakis != null && atesakis.length > 0) {

            if (log__.isDebugEnabled()) {
                log__.debug("----------------------------");
                for (String str : atesakis) {
                    log__.debug(str);
                }
                log__.debug("----------------------------");
            }

            List<String> gsAtesaki = new ArrayList<String>();
            List<String> smailAtesaki = new ArrayList<String>();

            for (String id : atesakis) {
                if (id.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                    smailAtesaki.add(id.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
                } else {
                    gsAtesaki.add(id);
                }
            }

            if (!gsAtesaki.isEmpty()) {
                SmlAccountDao sacDao = new SmlAccountDao(getCon());
                smailAtesaki.addAll(sacDao.selectFromUsrSids(
                        (String[]) gsAtesaki.toArray(new String[gsAtesaki.size()])));
            }

            sql.addSql(" and");
            sql.addSql(" ASAK.ASAKUSID in (");

            for (int i = 0; i < smailAtesaki.size(); i++) {
                if (i > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("      ?");
                }
                sql.addIntValue(Integer.parseInt(smailAtesaki.get(i)));
            }
            sql.addSql(" )");

            sql.addSql(" and");
            sql.addSql("   ASAK.ASAKSENDKBN=?");
            sql.addIntValue(GSConstSmail.SML_SEND_KBN_ATESAKI);

        }

        //マーク-------------------------------------------------------
        int mark = prmModel.getMailMark();

        if (GSConstSmail.MARK_KBN_ALL != mark) {
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_MARK = ?");
            sql.addIntValue(mark);
        }


        //キーワード---------------------------------------------------
        List<String>  keywordList = prmModel.getKeywordList();
        //検索対象
        boolean targetTitle = false;
        boolean targetBody = false;
        String[] targets = prmModel.getSearchTarget();
        if (targets != null && targets.length > 0) {
            for (String target : targets) {
                if (String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE).equals(target)) {
                    targetTitle = true;
                }
                if (String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN).equals(target)) {
                    targetBody = true;
                }
            }
        }

        if (keywordList != null && !keywordList.isEmpty()) {

            String keywordJoin = "  and";
            if (prmModel.getKeyWordkbn() == GSConstSmail.KEY_WORD_KBN_OR) {
                keywordJoin = "   or";
            }

            if (targetTitle) {
                sql.addSql("  and");
                if (targetBody) {
                    sql.addSql("    (");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       SML_WMEIS.SMW_TITLE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("      )");
            }

            if (targetBody) {
                if (targetTitle) {
                    sql.addSql("      or");
                } else {
                    sql.addSql("      and");
                }
                sql.addSql("      ((");
                sql.addSql("        SML_WMEIS.SMW_TYPE = 0");
                sql.addSql("        and");
                sql.addSql("        (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       SML_WMEIS.SMW_BODY like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("        )");
                sql.addSql("      )");

                sql.addSql("      or");

                sql.addSql("      (");
                sql.addSql("        SML_WMEIS.SMW_TYPE = 1");
                sql.addSql("        and");
                sql.addSql("        (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       SML_WMEIS.SMW_BODY_PLAIN like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("        )");
                sql.addSql("      ))");

                if (targetTitle) {
                    sql.addSql("    )");
                }

            }
        }
        //ショートメールSID---------------------------------------------------
        int smlSid = prmModel.getSmlSid();

        if (smlSid != GSConstCommon.NUM_INIT) {
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_SID = ?");
            sql.addIntValue(smlSid);
        }

    }

    /**
     * <br>[機  能] ゴミ箱モードの検索全データ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータモデル
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchDataCountGomiBako(Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(cntView.cnt) as cnt");
            sql.addSql(" from");
            sql.addSql("   (");
            //受信
            sql.addSql("    select");
            sql.addSql("      count(SML_JMEIS.SMJ_SID) as cnt");
            __createDetailSqlJushin(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql(" union all");
            //送信
            sql.addSql("    select");
            sql.addSql("      count(DISTINCT SML_SMEIS.SMS_SID) as cnt");
            __createDetailSqlSoushin(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql(" union all");
            //草稿
            sql.addSql("    select");
            sql.addSql("      count(DISTINCT SML_WMEIS.SMW_SID) as cnt");
            __createDetailSqlSoukou(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql("   ) cntView");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;

    }

    /**
     * <br>[機  能] ゴミ箱モードの検索メッセージデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索パラメータ
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailModel> getSearchDataGomiBako(Sml090SearchParameterModel prmModel)
        throws SQLException {

        int offset = prmModel.getOffset();
        int limit = prmModel.getLimit();
        int orderKey1 = prmModel.getSearchOrderKey1();
        int orderKey2 = prmModel.getSearchOrderKey2();
        int sortKey1 = prmModel.getSearchSortKey1();
        int sortKey2 = prmModel.getSearchSortKey2();

        log__.debug("----------------------------");
        log__.debug("offset         :" + offset);
        log__.debug("limit          :" + limit);
        log__.debug("----------------------------");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();
        con = getCon();
        final Map<Integer, SmailModel> mailMap = new HashMap<>();
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" DISTINCT ");
            sql.addSql("   unionView.mailKbn as mailKbn,");
            sql.addSql("   unionView.mailSid as mailSid,");
            sql.addSql("   unionView.smjOpkbn as smjOpkbn,");
            sql.addSql("   unionView.smjRtnkbn as smjRtnkbn,");
            sql.addSql("   unionView.smjFwkbn as smjFwkbn,");
            sql.addSql("   unionView.mailMark as mailMark,");
            sql.addSql("   unionView.mailTitle as mailTitle,");
            sql.addSql("   unionView.mailSize as mailSize,");
            sql.addSql("   unionView.mailDate as mailDate,");
            sql.addSql("   unionView.sacSid as sacSid,");
            sql.addSql("   (case when unionView.usiSeiKn is null then 1 else 0 end) as sacType,");
            sql.addSql("   unionView.sacName as sacName,");
            sql.addSql("   unionView.sacJkbn as sacJkbn,");
            sql.addSql("   unionView.usrJkbn as usrJkbn,");
            sql.addSql("   unionView.usrSid as usrSid,");
            sql.addSql("   unionView.usiSei as usiSei,");
            sql.addSql("   unionView.usiMei as usiMei,");
            sql.addSql("   unionView.usiSeiKn as usiSeiKn,");
            sql.addSql("   unionView.usiMeiKn as usiMeiKn,");
            sql.addSql("   unionView.usrUkoFlg as usrUkoFlg,");
            sql.addSql("   unionView.binSid as binSid,");
            sql.addSql("   unionView.usiPictKf as usiPictKf");
            sql.addSql(" from");
            sql.addSql("   (");
            //受信
            sql.addSql("    select ");
            sql.addSql("      " + GSConstSmail.TAB_DSP_MODE_JUSIN + " as mailKbn,");
            sql.addSql("      SML_JMEIS.SMJ_SID as mailSid,");
            sql.addSql("      SML_JMEIS.SMJ_OPKBN as smjOpkbn, ");
            sql.addSql("      SML_JMEIS.SMJ_RTN_KBN as smjRtnkbn, ");
            sql.addSql("      SML_JMEIS.SMJ_FW_KBN as smjFwkbn, ");
            sql.addSql("      SML_SMEIS.SMS_MARK as mailMark,");
            sql.addSql("      SML_SMEIS.SMS_TITLE as mailTitle,");
            sql.addSql("      SML_SMEIS.SMS_SDATE as mailDate,");
            sql.addSql("      SML_SMEIS.SMS_SIZE as mailSize,");
            sql.addSql("      CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("      CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("      SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("      SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("      SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("      CMN_USRM_INF.USR_SID as usrSid,");
            sql.addSql("      CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("      CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN as usiSeiKn,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN as usiMeiKn,");
            sql.addSql("      CMN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("      CMN_USRM_INF.USI_PICT_KF as usiPictKf");
            __createDetailSqlJushin(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql(" union all");
            //送信
            sql.addSql("    select ");
            sql.addSql(" DISTINCT ");
            sql.addSql("      " + GSConstSmail.TAB_DSP_MODE_SOSIN + " as mailKbn,");
            sql.addSql("      SML_SMEIS.SMS_SID as mailSid,");
            sql.addSql("      0 as smjOpkbn,");
            sql.addSql("      0 as smjRtnkbn,");
            sql.addSql("      0 as smjFwkbn,");
            sql.addSql("      SML_SMEIS.SMS_MARK as mailMark,");
            sql.addSql("      SML_SMEIS.SMS_TITLE as mailTitle,");
            sql.addSql("      SML_SMEIS.SMS_SDATE as mailDate,");
            sql.addSql("      SML_SMEIS.SMS_SIZE as mailSize,");
            sql.addSql("      0 as usrJkbn,");
            sql.addSql("      SOSHIN_USRM_INF.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("      SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("      SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("      SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("      SOSHIN_USRM_INF.USR_SID as usrSid,");
            sql.addSql("      SOSHIN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("      SOSHIN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("      SOSHIN_USRM_INF.USI_SEI_KN as usiSeiKn,");
            sql.addSql("      SOSHIN_USRM_INF.USI_MEI_KN as usiMeiKn,");
            sql.addSql("      SOSHIN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("      SOSHIN_USRM_INF.USI_PICT_KF as usiPictKf");
            __createDetailSqlSoushin(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql(" union all");
            //草稿
            sql.addSql("    select ");
            sql.addSql(" DISTINCT ");
            sql.addSql("      " + GSConstSmail.TAB_DSP_MODE_SOKO + " as mailKbn,");
            sql.addSql("      SML_WMEIS.SMW_SID as mailSid,");
            sql.addSql("      0 as smjOpkbn,");
            sql.addSql("      0 as smjRtnkbn,");
            sql.addSql("      0 as smjFwkbn,");
            sql.addSql("      SML_WMEIS.SMW_MARK as mailMark,");
            sql.addSql("      SML_WMEIS.SMW_TITLE as mailTitle,");
            sql.addSql("      SML_WMEIS.SMW_EDATE as mailDate,");
            sql.addSql("      SML_WMEIS.SMW_SIZE as mailSize,");
            sql.addSql("      0 as usrJkbn,");
            sql.addSql("      CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("      SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("      SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("      SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("      CMN_USRM_INF.USR_SID as usrSid,");
            sql.addSql("      CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("      CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN as usiSeiKn,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN as usiMeiKn,");
            sql.addSql("      CMN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("      CMN_USRM_INF.USI_PICT_KF as usiPictKf");
            __createDetailSqlSoukou(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql("   ) unionView");

            sql.addSql(" order by");

            //オーダー1--------------------------------------------------------
            String orderStr1 = "";
            if (orderKey1 == GSConstSmail.ORDER_KEY_ASC) {
                orderStr1 = "  asc";
            } else if (orderKey1 == GSConstSmail.ORDER_KEY_DESC) {
                orderStr1 = "  desc";
            }

            //ソートカラム1
            switch (sortKey1) {
                //マーク区分
                case GSConstSmail.MSG_SORT_KEY_MARK:
                    sql.addSql("  unionView.mailMark");
                    sql.addSql(orderStr1);
                    break;
                //件名
                case GSConstSmail.MSG_SORT_KEY_TITLE:
                    sql.addSql("  unionView.mailKbn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.mailTitle");
                    sql.addSql(orderStr1);
                    break;
                //差出人
                case GSConstSmail.MSG_SORT_KEY_NAME:
                    sql.addSql("  sacType " + orderStr1 + ",");
                    sql.addSql("  unionView.usiSeiKn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.sacName");
                    sql.addSql(orderStr1);
                    break;
                //宛先
                case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                    sql.addSql("  unionView.usiSeiKn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr1);
                    break;
                //容量
                case GSConstSmail.MSG_SORT_KEY_SIZE:
                    sql.addSql("  unionView.mailSize");
                    sql.addSql(orderStr1);
                    break;
                //日時
                case GSConstSmail.MSG_SORT_KEY_DATE:
                    sql.addSql("  unionView.mailDate");
                    sql.addSql(orderStr1);
                    break;
                default:
                    break;
            }
            //オーダー2--------------------------------------------------------
            String orderStr2 = "";
            if (orderKey2 == GSConstSmail.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else if (orderKey2 == GSConstSmail.ORDER_KEY_DESC) {
                orderStr2 = "  desc";
            }

            //ソートカラム2
            switch (sortKey2) {
                //マーク区分
                case GSConstSmail.MSG_SORT_KEY_MARK:
                    sql.addSql(",  unionView.mailMark");
                    sql.addSql(orderStr2);
                    break;
                //件名
                case GSConstSmail.MSG_SORT_KEY_TITLE:
                    sql.addSql(",  unionView.mailKbn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.mailTitle");
                    sql.addSql(orderStr2);
                    break;
                //差出人
                case GSConstSmail.MSG_SORT_KEY_NAME:
                    sql.addSql(",  sacType " + orderStr2 + ",");

                    sql.addSql("  unionView.usiSeiKn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.sacName");
                    sql.addSql(orderStr2);
                    break;
                //宛先
                case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                    sql.addSql(",  unionView.usiSeiKn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr2);
                    break;
                //容量
                case GSConstSmail.MSG_SORT_KEY_SIZE:
                    sql.addSql(",  unionView.mailSize");
                    sql.addSql(orderStr2);
                    break;
                //日時
                case GSConstSmail.MSG_SORT_KEY_DATE:
                    sql.addSql(",  unionView.mailDate");
                    sql.addSql(orderStr2);
                    break;
                default:
                    break;
            }
            //------------------------------------------------------------------
            if (offset <= 1) {
                offset = 0;
            }
            if (offset > 1) {
                offset = offset - 1;
            }
            sql.setPagingValue(offset, limit);

            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            SmailDao smlDao = new SmailDao(con);
            while (rs.next())  {
                SmailModel mdl = new SmailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmlSid(rs.getInt("mailSid"));
                mdl.setSmjOpkbn(rs.getInt("smjOpkbn"));
                mdl.setReturnKbn(rs.getInt("smjRtnkbn"));
                mdl.setFwKbn(rs.getInt("smjFwkbn"));
                mdl.setSmsMark(rs.getInt("mailMark"));
                mdl.setSmsTitle(rs.getString("mailTitle"));
                mdl.setSmsSize(rs.getLong("mailSize"));
                mdl.setBinFileSid(rs.getLong("binSid"));
                mdl.setPhotoFileDsp(rs.getInt("usiPictKf"));
                UDate sDate = UDate.getInstanceTimestamp(rs.getTimestamp("mailDate"));
                mdl.setSmsSdate(sDate);
                mdl.setStrSdate(
                        UDateUtil.getSlashYYMD(sDate) + " " + UDateUtil.getSeparateHMS(sDate));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));

                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));

                if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(mdl.getMailKbn())) {
                    //受信ボックスのメールの時
                    mdl.setUsrSid(rs.getInt("usrSid"));
                    mdl.setUsiSei(rs.getString("usiSei"));
                    mdl.setUsiMei(rs.getString("usiMei"));

                } else if (GSConstSmail.TAB_DSP_MODE_SOSIN.equals(mdl.getMailKbn())) {
                    //送信ボックスのメールの時
                    mdl.setAtesakiList(
                            smlDao.getAtesakiList(mdl.getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_ATESAKI,
                                    sortMdl)
                            );
                    mdl.setUsrSid(rs.getInt("usrSid"));
                    mdl.setUsiSei(rs.getString("usiSei"));
                    mdl.setUsiMei(rs.getString("usiMei"));
                } else if (GSConstSmail.TAB_DSP_MODE_SOKO.equals(mdl.getMailKbn())) {
                    //草稿ボックスのメールの時
                    mdl.setAtesakiList(smlDao.getSitagakiAtesakiList(
                            mdl.getSmlSid(),
                            GSConstSmail.SML_SEND_KBN_ATESAKI,
                            sortMdl));
                    mdl.setUsrSid(rs.getInt("usrSid"));
                    mdl.setUsiSei(rs.getString("usiSei"));
                    mdl.setUsiMei(rs.getString("usiMei"));
                }

                mdl.setMailKey(rs.getString("mailKbn")
                        + StringUtil.toDecFormat(mdl.getSmlSid(), GSConstSmail.MAIL_KEY_FORMAT));

                ret.add(mdl);
                mailMap.put(mdl.getSmlSid(), mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //添付ファイル数取得
        SmlBinDao binDao = new SmlBinDao(con);
        binDao.getBinFileCnt(mailMap.keySet())
        .entrySet().stream()
        .filter(entry -> mailMap.containsKey(entry.getKey()))
        .forEach(entry -> {
            mailMap.get(entry.getKey()).setBinCnt(entry.getValue());
        });
        //ラベル取得
        Map<String, Set<Integer>> kbnMap =
                ret.stream()
                .collect(Collectors.groupingBy(SmailModel::getMailKbn,
                                              Collectors.mapping(
                                                      SmailModel::getSmlSid,
                                                      Collectors.toSet())));
        SmailDao smlDao = new SmailDao(con);
        for (Entry<String, Set<Integer>> kbnE : kbnMap.entrySet()) {
            smlDao.getMailLabelMap(
                    kbnE.getValue(), prmModel.getMySid(), kbnE.getKey())
            .entrySet().stream()
            .forEach(entry -> {
                mailMap.get(entry.getKey()).setLabelList(entry.getValue());
            });
        }

        return ret;
    }


    /**
     * <br>[機  能] 全データハッシュ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param mailKbn メール区分
     * @param mailSid メールSID
     * @param prmModel 検索パラメータモデル
     * @return ret 次、または前のメッセージSID
     * @throws SQLException SQL実行例外
     */
    public HashControlModel createGAllDataHash(String mailKbn,
            int mailSid,
            Sml090SearchParameterModel prmModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashControlModel ret = new HashControlModel();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql(" DISTINCT ");
            sql.addSql("   unionView.mailKbn as mailKbn,");
            sql.addSql("   unionView.mailSid as mailSid,");
            sql.addSql("   unionView.mailMark as mailMark,");
            sql.addSql("   unionView.mailTitle as mailTitle,");
            sql.addSql("   unionView.mailDate as mailDate,");
            sql.addSql("   unionView.mailSize as mailSize,");
            sql.addSql("   unionView.usrJkbn as usrJkbn,");
            sql.addSql("   unionView.usiSei as usiSei,");
            sql.addSql("   unionView.usiMei as usiMei,");
            sql.addSql("   unionView.sacName as sacName,");
            sql.addSql("   (case when unionView.usiSeiKn is null then 1 else 0 end) as sacType,");
            sql.addSql("   unionView.usiSeiKn as usiSeiKn,");
            sql.addSql("   unionView.usiMeiKn as usiMeiKn");
            sql.addSql(" from");
            sql.addSql("   (");
            //受信
            sql.addSql("    select ");
            sql.addSql("      " + GSConstSmail.TAB_DSP_MODE_JUSIN + " as mailKbn,");
            sql.addSql("      SML_JMEIS.SMJ_SID as mailSid,");
            sql.addSql("      SML_SMEIS.SMS_MARK as mailMark,");
            sql.addSql("      SML_SMEIS.SMS_TITLE as mailTitle,");
            sql.addSql("      SML_SMEIS.SMS_SDATE as mailDate,");
            sql.addSql("      SML_SMEIS.SMS_SIZE as mailSize,");
            sql.addSql("      CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("      CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("      CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN as usiSeiKn,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN as usiMeiKn");
            __createDetailSqlJushin(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql(" union all");
            //送信
            sql.addSql("    select ");
            sql.addSql(" DISTINCT ");
            sql.addSql("      " + GSConstSmail.TAB_DSP_MODE_SOSIN + " as mailKbn,");
            sql.addSql("      SML_SMEIS.SMS_SID as mailSid,");
            sql.addSql("      SML_SMEIS.SMS_MARK as mailMark,");
            sql.addSql("      SML_SMEIS.SMS_TITLE as mailTitle,");
            sql.addSql("      SML_SMEIS.SMS_SDATE as mailDate,");
            sql.addSql("      SML_SMEIS.SMS_SIZE as mailSize,");

//            sql.addSql("      CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("(case when CMN_USRM.USR_JKBN = 1 then 0 end) as usrJkbn,");
            sql.addSql("      '' as usiSei,");
            sql.addSql("      '' as usiMei,");
            sql.addSql("      '' as usiSeiKn,");
            sql.addSql("      '' as usiMeiKn");
            __createDetailSqlSoushin(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql(" union all");
            //草稿
            sql.addSql("    select ");
            sql.addSql(" DISTINCT ");
            sql.addSql("      " + GSConstSmail.TAB_DSP_MODE_SOKO + " as mailKbn,");
            sql.addSql("      SML_WMEIS.SMW_SID as mailSid,");
            sql.addSql("      SML_WMEIS.SMW_MARK as mailMark,");
            sql.addSql("      SML_WMEIS.SMW_TITLE as mailTitle,");
            sql.addSql("      SML_WMEIS.SMW_EDATE as mailDate,");
            sql.addSql("      SML_WMEIS.SMW_SIZE as mailSize,");
//            sql.addSql("      ASAK.ASAKUJKBN as usrJkbn,");
            sql.addSql("(case when ASAK.ASAKUJKBN = 1 then 0 end) as usrJkbn,");
            sql.addSql("      '' as usiSei,");
            sql.addSql("      '' as usiMeii,");
            sql.addSql("      '' as usiSeiKn,");
            sql.addSql("      '' as usiMeiKn");
            __createDetailSqlSoukou(sql, prmModel, GSConstSmail.SML_JTKBN_GOMIBAKO);
            sql.addSql("   ) unionView");

            sql.addSql(" order by");

            int orderKey1 = prmModel.getSearchOrderKey1();
            int orderKey2 = prmModel.getSearchOrderKey2();
            int sortKey1 = prmModel.getSearchSortKey1();
            int sortKey2 = prmModel.getSearchSortKey2();

            //オーダー1--------------------------------------------------------
            String orderStr1 = "";
            if (orderKey1 == GSConstSmail.ORDER_KEY_ASC) {
                orderStr1 = "  asc";
            } else if (orderKey1 == GSConstSmail.ORDER_KEY_DESC) {
                orderStr1 = "  desc";
            }

            //ソートカラム1
            switch (sortKey1) {
                //マーク区分
                case GSConstSmail.MSG_SORT_KEY_MARK:
                    sql.addSql("  unionView.mailMark");
                    sql.addSql(orderStr1);
                    break;
                //件名
                case GSConstSmail.MSG_SORT_KEY_TITLE:
                    sql.addSql("  unionView.mailKbn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.mailTitle");
                    sql.addSql(orderStr1);
                    break;
                //差出人
                case GSConstSmail.MSG_SORT_KEY_NAME:
                    sql.addSql("  sacType " + orderStr1 + ",");
                    sql.addSql("  unionView.usiSeiKn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.sacName");
                    sql.addSql(orderStr1);
                    break;
                //宛先
                case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                    sql.addSql("  unionView.usiSeiKn");
                    sql.addSql(orderStr1);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr1);
                    break;
                //サイズ
                case GSConstSmail.MSG_SORT_KEY_SIZE:
                    sql.addSql("  unionView.mailSize");
                    sql.addSql(orderStr1);
                    break;
                //日時
                case GSConstSmail.MSG_SORT_KEY_DATE:
                    sql.addSql("  unionView.mailDate");
                    sql.addSql(orderStr1);
                    break;
                default:
                    break;
            }
            //オーダー2--------------------------------------------------------
            String orderStr2 = "";
            if (orderKey2 == GSConstSmail.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else if (orderKey2 == GSConstSmail.ORDER_KEY_DESC) {
                orderStr2 = "  desc";
            }

            //ソートカラム2
            switch (sortKey2) {
                //マーク区分
                case GSConstSmail.MSG_SORT_KEY_MARK:
                    sql.addSql(",  unionView.mailMark");
                    sql.addSql(orderStr2);
                    break;
                //件名
                case GSConstSmail.MSG_SORT_KEY_TITLE:
                    sql.addSql(",  unionView.mailKbn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.mailTitle");
                    sql.addSql(orderStr2);
                    break;
                //差出人
                case GSConstSmail.MSG_SORT_KEY_NAME:
                    sql.addSql(",  sacType " + orderStr2 + ",");
                    sql.addSql("  unionView.usiSeiKn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.sacName");
                    sql.addSql(orderStr2);
                    break;
                //宛先
                case GSConstSmail.MSG_SORT_KEY_ATESAKI:
                    sql.addSql(",  unionView.usiSeiKn");
                    sql.addSql(orderStr2);
                    sql.addSql(",");
                    sql.addSql("  unionView.usiMeiKn");
                    sql.addSql(orderStr2);
                    break;
                //サイズ
                case GSConstSmail.MSG_SORT_KEY_SIZE:
                    sql.addSql(",  unionView.mailSize");
                    sql.addSql(orderStr2);
                    break;
                //日時
                case GSConstSmail.MSG_SORT_KEY_DATE:
                    sql.addSql(",  unionView.mailDate");
                    sql.addSql(orderStr2);
                    break;
                default:
                    break;
            }
            //------------------------------------------------------------------

            pstmt = con.prepareStatement(sql.toSqlString());

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int allOffset = 0;
            int pageOffset = 0;
            int page = 1;
            int rowNum = 0;
            HashMap<Integer, Sml030Model> map =
                new HashMap<Integer, Sml030Model>();

            int limit = prmModel.getLimit();
            while (rs.next()) {
                allOffset += 1;
                pageOffset += 1;
                if (pageOffset > limit) {
                    pageOffset = 1;
                    page += 1;
                }

                Sml030Model mdl = new Sml030Model();
                mdl.setMailSid(rs.getInt("mailSid"));
                mdl.setPageNum(page);
                mdl.setMailKbn(rs.getString("mailKbn"));

                if (mdl.getMailSid() == mailSid
                    && mdl.getMailKbn().equals(mailKbn)) {
                    rowNum = allOffset;
                }
                map.put(allOffset, mdl);
            }

            ret.setMap(map);
            ret.setRowNum(rowNum);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 差出人部SQL条件構築
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param prmModel Sml090SearchParameterModel
     * @throws SQLException SQL実行時例外
     */
    private void __sqlSetUpForSoushinsya(
            SqlBuffer sql, Sml090SearchParameterModel prmModel) throws SQLException {
        //グループ
        String groupSid = prmModel.getSltGroup();
        //ユーザ
        String userSid = prmModel.getSltUser();

        if (!String.valueOf(GSConstCommon.NUM_INIT).equals(groupSid)
                                   && !String.valueOf(GSConstCommon.NUM_INIT).equals(userSid)) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SAC_SID in (select SAC_SID from SML_ACCOUNT where ");

            if (userSid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                sql.addSql("   SAC_SID = ?)");
                sql.addIntValue(
                        __parseInt(userSid.substring(GSConstSmail.SML_ACCOUNT_STR.length())));
            } else {
                sql.addSql("   USR_SID = ?)");
                sql.addIntValue(__parseInt(userSid));
            }

        } else if (!String.valueOf(GSConstCommon.NUM_INIT).equals(groupSid)
                                    && String.valueOf(GSConstCommon.NUM_INIT).equals(userSid)) {
            //グループに所属するユーザ全て
            sql.addSql(" and");


            if (groupSid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                sql.addSql("   SML_SMEIS.SAC_SID"
                        + " in (select SAC_SID from SML_ACCOUNT where USR_SID is null)");
            } else {
                sql.addSql("   SML_SMEIS.SAC_SID"
                        + " in (select SAC_SID from SML_ACCOUNT where USR_SID in (");
                sql.addSql("                           select");
                sql.addSql("                             USR_SID");
                sql.addSql("                           from");
                sql.addSql("                             CMN_BELONGM");
                sql.addSql("                           where GRP_SID = ?))");
                sql.addIntValue(__parseInt(groupSid));
            }
        }
    }

    /**
     * <br>[機  能] 宛先部SQL条件構築
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param prmModel Sml090SearchParameterModel
     * @throws SQLException 実行時例外
     */
    private void __sqlSetUpForAtesaki(
            SqlBuffer sql, Sml090SearchParameterModel prmModel) throws SQLException {
        //宛先
        String[] atesakis = prmModel.getAtesaki();

        if (atesakis != null && atesakis.length > 0) {

            if (log__.isDebugEnabled()) {
                log__.debug("----------------------------");

                for (String str : atesakis) {
                    log__.debug(str);
                }
                log__.debug("----------------------------");
            }

            List<String> gsAtesaki = new ArrayList<String>();
            List<String> smailAtesaki = new ArrayList<String>();

            for (String id : atesakis) {
                if (id.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                    smailAtesaki.add(id.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
                } else {
                    gsAtesaki.add(id);
                }
            }

            if (!gsAtesaki.isEmpty()) {
                SmlAccountDao sacDao = new SmlAccountDao(getCon());
                smailAtesaki.addAll(sacDao.selectFromUsrSids(
                        (String[]) gsAtesaki.toArray(new String[gsAtesaki.size()])));
            }

            sql.addSql("       and");
            sql.addSql("         (");

            for (int i = 0; i < smailAtesaki.size(); i++) {
                if (0 < i) {
                    sql.addSql("        or");
                }

                sql.addSql("          SML_SMEIS.SMS_SID in (");
                sql.addSql("            select SMJ_SID from SML_JMEIS");
                sql.addSql("            where SML_JMEIS.SMJ_SENDKBN = ?");
                sql.addSql("            and SML_JMEIS.SAC_SID = ?");
                sql.addSql("          )");
                sql.addIntValue(GSConstSmail.SML_SEND_KBN_ATESAKI);
                sql.addIntValue(Integer.parseInt(smailAtesaki.get(i)));

            }
            sql.addSql("        )");
        }

    }

    /**
     * <br>[機  能] マーク部SQL条件構築
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param prmModel Sml090SearchParameterModel
     */
    private void __sqlSetUpForMark(SqlBuffer sql, Sml090SearchParameterModel prmModel) {
        //マーク
        int mark = prmModel.getMailMark();

        if (GSConstSmail.MARK_KBN_ALL != mark) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_MARK = ?");
            sql.addIntValue(mark);
        }
    }

    /**
     * <br>[機  能] キーワード部SQL条件構築
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param prmModel Sml090SearchParameterModel
     */
    private void __sqlSetUpForKeyword(SqlBuffer sql, Sml090SearchParameterModel prmModel) {
        //キーワード
        List<String>  keywordList = prmModel.getKeywordList();
        //検索対象
        boolean targetTitle = false;
        boolean targetBody = false;
        String[] targets = prmModel.getSearchTarget();

        if (targets == null || targets.length <= 0) {
            return;
        }

        for (String target : targets) {
            if (String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE).equals(target)) {
                targetTitle = true;
            }
            if (String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN).equals(target)) {
                targetBody = true;
            }
        }

        if (keywordList != null && !keywordList.isEmpty()) {

            String keywordJoin = "  and";
            if (prmModel.getKeyWordkbn() == GSConstSmail.KEY_WORD_KBN_OR) {
                keywordJoin = "   or";
            }

            if (targetTitle) {
                sql.addSql("  and");
                if (targetBody) {
                    sql.addSql("    (");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       SML_SMEIS.SMS_TITLE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("      )");
            }

            if (targetBody) {
                if (targetTitle) {
                    sql.addSql("      or");
                } else {
                    sql.addSql("      and");
                }
                sql.addSql("      ((");
                sql.addSql("      SML_SMEIS.SMS_TYPE = 0 and (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       SML_SMEIS.SMS_BODY like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("      )");
                sql.addSql("      )");
                sql.addSql("      or");
                sql.addSql("      (");
                sql.addSql("      SML_SMEIS.SMS_TYPE = 1 and (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       SML_SMEIS.SMS_BODY_PLAIN like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("      )");
                sql.addSql("      ))");

                if (targetTitle) {
                    sql.addSql("    )");
                }

            }
        }

    }

    /**
     * <br>[機  能] ショートメールSQL条件構築
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param prmModel Sml090SearchParameterModel
     */
    private void __sqlSetUpForSmlSid(SqlBuffer sql, Sml090SearchParameterModel prmModel) {
        //ショートメールSID
        int smlSid = prmModel.getSmlSid();

        if (smlSid != GSConstCommon.NUM_INIT) {
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_SID = ?");
            sql.addIntValue(smlSid);
        }
    }

    /**
     * <br>[機  能] 文字列を数値型へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param value 文字列
     * @return valueを数値型変換した値
     */
    private int __parseInt(String value) {
        int numValue = -1;
        try {
            numValue = NullDefault.getInt(value, -1);
        } catch (Exception e) {
        }
       return numValue;
    }
}