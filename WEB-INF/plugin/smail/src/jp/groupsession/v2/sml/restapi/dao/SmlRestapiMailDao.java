package jp.groupsession.v2.sml.restapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailBodyModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メール情報
 * <br>[解  説]
 * <br>[備  考]
 */
public class SmlRestapiMailDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlRestapiMailDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlRestapiMailDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ショートメールの本文情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSidList メールSID一覧
     * @return メールSIDをキーにした本文情報一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, SmlRestapiMailBodyModel> getMailBodyMap(List<Integer> mailSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet  rs  = null;
        Connection con = getCon();

        HashMap<Integer, SmlRestapiMailBodyModel> ret
                = new HashMap<Integer, SmlRestapiMailBodyModel>();

        try {
            // -------------------------------------------------
            //  メール本文情報
            // -------------------------------------------------
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   unionView.mailSid  as mailSid,");
            sql.addSql("   unionView.mailType as mailType,");
            sql.addSql("   unionView.mailBody as body");
            sql.addSql(" from");
            sql.addSql("   (");
            // 受信 or 送信
            sql.addSql("    select ");
            sql.addSql("      SMS_SID  as mailSid,");
            sql.addSql("      SMS_TYPE as mailType,");
            sql.addSql("      SMS_BODY as mailBody");
            sql.addSql("    from");
            sql.addSql("      SML_SMEIS");
            sql.addSql("    where");
            sql.addSql("      SMS_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("      )");

            sql.addSql(" union all");
            //草稿
            sql.addSql("    select ");
            sql.addSql("      SMW_SID  as mailSid,");
            sql.addSql("      SMW_TYPE as mailType,");
            sql.addSql("      SMW_BODY as mailBody");
            sql.addSql("    from");
            sql.addSql("      SML_WMEIS");
            sql.addSql("    where");
            sql.addSql("      SMW_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("      )");
            sql.addSql("   ) unionView");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key   = Integer.valueOf(rs.getInt("mailSid"));
                Integer type  = Integer.valueOf(rs.getInt("mailType"));
                String  body  = rs.getString("body");
                SmlRestapiMailBodyModel mdl = new SmlRestapiMailBodyModel();

                if (body != null) {
                    mdl.setMailType(type);
                    mdl.setMailBody(body);
                    ret.put(key, mdl);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        try {
            // -------------------------------------------------
            //  添付ファイル一覧情報
            // -------------------------------------------------
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    SML_BIN.SML_SID  as SML_SID,");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    SML_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    SML_BIN.SML_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    SML_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CommonBiz cmnBiz = new CommonBiz();
            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("SML_SID"));
                SmlRestapiMailBodyModel mdl = ret.get(key);
                if (mdl == null) {
                    mdl = new SmlRestapiMailBodyModel();
                    ret.put(key, mdl);
                }

                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                retMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSize(size);
                retMdl.setBinFileSizeDsp(strSize);
                mdl.addBinModel(retMdl);
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
     * <br>[機  能] ショートメールの宛先情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSidList メールSID一覧
     * @param smlKbn      メールボックス区分
     * @return メールSIDをキーにした宛先情報一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, ArrayList<AtesakiModel>> getMailAtesakiMap(List<Integer> mailSidList,
                           String smlKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (mailSidList == null || mailSidList.size() == 0) {
            return null;
        }

        HashMap<Integer, ArrayList<AtesakiModel>> ret
                                  = new HashMap<Integer, ArrayList<AtesakiModel>>();
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID   as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE  as sacType,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME  as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN  as sacJkbn,");
            sql.addSql("   CMN_USRM_INF.USI_SEI  as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI  as usiMei,");
            sql.addSql("   CMN_USRM.USR_JKBN     as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg, ");
            sql.addSql("   unionView.smjSid      as smjSid,");
            sql.addSql("   unionView.smjOpdate   as smjOpdate,");
            sql.addSql("   unionView.smjFwkbn    as smjFwkbn,");
            sql.addSql("   unionView.smjSendkbn  as smjSendkbn");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" left join CMN_USRM_INF");
            sql.addSql(" on SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join CMN_USRM");
            sql.addSql(" on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID,");
            sql.addSql("   (");
            if (!smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                sql.addSql("     select");
                sql.addSql("       SAC_SID      as sacSid,");
                sql.addSql("       SMJ_SID      as smjSid,");
                sql.addSql("       SMJ_OPDATE   as smjOpdate,");
                sql.addSql("       SMJ_FWKBN    as smjFwkbn,");
                sql.addSql("       SMJ_SENDKBN  as smjSendkbn");
                sql.addSql("     from");
                sql.addSql("       SML_JMEIS");
                sql.addSql("     where");
                sql.addSql("       SMJ_SID in (");
                for (int i = 0; i < mailSidList.size(); i++) {
                    Integer mailSid = mailSidList.get(i);
                    sql.addSql((i > 0 ? "   ,?" : "   ?"));
                    sql.addIntValue(mailSid.intValue());
                }
                sql.addSql("       )");
            }
            if (!smlKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
             && !smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                if (!smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                    sql.addSql("   union all");
                }
                sql.addSql("     select");
                sql.addSql("       SAC_SID      as sacSid,");
                sql.addSql("       SMS_SID      as smjSid,");
                sql.addSql("       NULL         as smjOpdate,");
                sql.addSql("       NULL         as smjFwkbn,");
                sql.addSql("       SMJ_SENDKBN  as smjSendkbn");
                sql.addSql("     from");
                sql.addSql("       SML_ASAK");
                sql.addSql("     where");
                sql.addSql("       SMS_SID in (");
                for (int i = 0; i < mailSidList.size(); i++) {
                    Integer mailSid = mailSidList.get(i);
                    sql.addSql((i > 0 ? "   ,?" : "   ?"));
                    sql.addIntValue(mailSid.intValue());
                }
                sql.addSql("       )");
            }
            sql.addSql("   ) as unionView");
            sql.addSql(" where ");
            sql.addSql("   unionView.sacSid = SML_ACCOUNT.SAC_SID");

            // 送信済み、草稿以外の場合は、宛先が含まれないのでここで条件セット
            if (smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
             || smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                sql.addSql(" and");
                sql.addSql("   unionView.smjSendkbn <> ?");
                sql.addIntValue(GSConstSmail.SML_SEND_KBN_ATESAKI);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("smjSid"));
                ArrayList<AtesakiModel> list = ret.get(key);
                if (list == null) {
                    list = new ArrayList<AtesakiModel>();
                    ret.put(key, list);
                }
                String sacName = "";
                int    sacJkbn = GSConstSmail.SAC_JKBN_NORMAL;
                if (!StringUtil.isNullZeroStringSpace(rs.getString("usiSei"))
                 && !StringUtil.isNullZeroStringSpace(rs.getString("usiMei"))) {
                    // 通常アカウントの場合 ⇒ ユーザ名(姓＋名)
                    sacName = rs.getString("usiSei") + " " + rs.getString("usiMei");
                    if (rs.getInt("usrJkbn") == GSConstUser.USER_JTKBN_DELETE) {
                        sacJkbn = GSConstSmail.SAC_JKBN_DELETE;
                    }
                } else {
                    // 代表者アカウント or ユーザ情報が見つからなかった場合 ⇒ アカウント名
                    sacName = rs.getString("sacName");
                    sacJkbn = rs.getInt("sacJkbn");
                }

                AtesakiModel mdl = new AtesakiModel();
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(sacName);
                mdl.setAccountJkbn(sacJkbn);
                mdl.setSmjOpdate(UDate.getInstanceTimestamp(rs.getTimestamp("smjOpdate")));
                mdl.setSmjFwkbn(rs.getString("smjFwkbn"));
                mdl.setSmjSendkbn(rs.getInt("smjSendkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                list.add(mdl);
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
     * <br>[機  能] 受信メッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param jkbn ゴミ箱区分
     * @param nonReadFlg 未読取得フラグ
     * @return cnt メッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getInboxCount(int sacSid, int jkbn, boolean nonReadFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMJ_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addIntValue(sacSid);
            sql.addIntValue(jkbn);
            if (nonReadFlg) {
                sql.addSql(" and");
                sql.addSql("   SMJ_OPKBN = ?");
                sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            }

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
     * <br>[機  能] 送信メッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param jkbn ゴミ箱区分
     * @return cnt メッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getSentCount(int sacSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMS_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMS_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(jkbn);

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
     * <br>[機  能] 草稿メッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param jkbn ゴミ箱区分
     * @return cnt メッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getDraftCount(int sacSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMW_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(jkbn);

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
     * <br>[機  能] ラベル受信件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param labelSid ラベルSID
     * @param nonReadFlg 未読取得フラグ
     * @return cnt メッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getLabelInboxCount(int sacSid, int labelSid, boolean nonReadFlg)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SML_JMEIS.SMJ_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" left join");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" on");
            sql.addSql("   SML_JMEIS.SMJ_SID = SML_JMEIS_LABEL.SMJ_SID");
            sql.addSql("   and SML_JMEIS.SAC_SID = SML_JMEIS_LABEL.SAC_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_JMEIS.SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_JKBN = ?");
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            if (nonReadFlg) {
                sql.addSql(" and");
                sql.addSql("   SMJ_OPKBN = ?");
                sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            }
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS_LABEL.SLB_SID = ?");
            sql.addIntValue(labelSid);

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
     * <br>[機  能] ラベル送信件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param labelSid ラベルSID
     * @return cnt メッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getLabelSentCount(int sacSid, int labelSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SML_SMEIS.SMS_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" left join");
            sql.addSql("   SML_SMEIS_LABEL");
            sql.addSql(" on");
            sql.addSql("   SML_SMEIS.SMS_SID = SML_SMEIS_LABEL.SMS_SID");
            sql.addSql("   and SML_SMEIS.SAC_SID = SML_SMEIS_LABEL.SAC_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_SMEIS.SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS_LABEL.SLB_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(labelSid);

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
     * <br>[機  能] ラベル草稿件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param labelSid ラベルSID
     * @return cnt メッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getLabelDraftCount(int sacSid, int labelSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SML_WMEIS.SMW_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" left join");
            sql.addSql("   SML_WMEIS_LABEL");
            sql.addSql(" on");
            sql.addSql("   SML_WMEIS.SMW_SID = SML_WMEIS_LABEL.SMW_SID");
            sql.addSql("   and SML_WMEIS.SAC_SID = SML_WMEIS_LABEL.SAC_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_WMEIS.SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS_LABEL.SLB_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(labelSid);

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
     * <br>[機  能] 指定されたメールSIDの送信メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailDetailModel> selectSmeisDetailFromSid(int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailDetailModel> ret = new ArrayList<SmailDetailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("   SML_SMEIS.SMS_SID as smsSid,");
            sql.addSql("   SML_SMEIS.SMS_MARK as smsMark,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle,");
            sql.addSql("   SML_SMEIS.SMS_SDATE as smsSdate,");
            sql.addSql("   SML_SMEIS.SMS_BODY as smsBody,");
            sql.addSql("   SML_SMEIS.SMS_TYPE as smsType,");
            sql.addSql("   CMN_USRM.USR_SID as usrSid,");
            sql.addSql("   CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as usiMei");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT ");
            sql.addSql("     left join ");
            sql.addSql("       CMN_USRM ");
            sql.addSql("     on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       left join ");
            sql.addSql("         CMN_USRM_INF ");
            sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID, ");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SML_SMEIS.SMS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SAC_SID = SML_ACCOUNT.SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mailSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            SmailDao smlDao = new SmailDao(con);
            if (rs.next()) {
                SmailDetailModel mdl = new SmailDetailModel();
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));
                mdl.setSmlSid(rs.getInt("smsSid"));
                mdl.setSmsMark(rs.getInt("smsMark"));
                mdl.setSmsTitle(rs.getString("smsTitle"));
                mdl.setSmsSdate(UDate.getInstanceTimestamp(rs.getTimestamp("smsSdate")));
                mdl.setSmsBody(rs.getString("smsBody"));
                mdl.setSmsType(rs.getInt("smsType"));
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setAtesakiList(
                        smlDao.getAtesakiList(mdl.getSmlSid(),
                                GSConstSmail.SML_SEND_KBN_ATESAKI,
                                sortMdl));
                mdl.setCcList(
                        smlDao.getAtesakiList(mdl.getSmlSid(),
                                GSConstSmail.SML_SEND_KBN_CC,
                                sortMdl));
                mdl.setBccList(
                        smlDao.getAtesakiList(mdl.getSmlSid(),
                                GSConstSmail.SML_SEND_KBN_BCC,
                                sortMdl));
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
     * <br>[機  能] 指定されたメールSIDの下書きメッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @param jtkbn 状態区分
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailDetailModel> selectWmeisDetail(int sacSid,
                                                          int mailSid,
                                                          int jtkbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailDetailModel> ret = new ArrayList<SmailDetailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(GSConstSmail.TAB_DSP_MODE_SOKO + " as mailKbn,");
            sql.addSql("   SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("   SML_WMEIS.SMW_SID as smwSid,");
            sql.addSql("   SML_WMEIS.SMW_MARK as smwMark,");
            sql.addSql("   SML_WMEIS.SMW_BODY as smwBody,");
            sql.addSql("   SML_WMEIS.SMW_TYPE as smwType,");
            sql.addSql("   SML_WMEIS.SMW_TITLE as smwTitle,");
            sql.addSql("   SML_WMEIS.SMW_EDATE as smwEdate,");
            sql.addSql("   CMN_USRM.USR_SID as usrSid,");
            sql.addSql("   CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as usiPictKf");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT ");
            sql.addSql("     left join ");
            sql.addSql("       CMN_USRM ");
            sql.addSql("     on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       left join ");
            sql.addSql("         CMN_USRM_INF ");
            sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID, ");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SML_WMEIS.SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SAC_SID = SML_ACCOUNT.SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);
            sql.addIntValue(jtkbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                SmailDetailModel mdl = new SmailDetailModel();
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmlSid(rs.getInt("smwSid"));
                mdl.setSmsMark(rs.getInt("smwMark"));
                mdl.setSmsTitle(rs.getString("smwTitle"));
                mdl.setSmsBody(rs.getString("smwBody"));
                mdl.setSmsType(rs.getInt("smwType"));
                mdl.setSmsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("smwEdate")));
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setAtesakiList(getSitagakiAtesakiList(mdl.getSmlSid(),
                        GSConstSmail.SML_SEND_KBN_ATESAKI));
                mdl.setCcList(getSitagakiAtesakiList(mdl.getSmlSid(),
                        GSConstSmail.SML_SEND_KBN_CC));
                mdl.setBccList(getSitagakiAtesakiList(mdl.getSmlSid(),
                        GSConstSmail.SML_SEND_KBN_BCC));
                mdl.setBinFileSid(rs.getLong("binSid"));
                mdl.setPhotoFileDsp(rs.getInt("usiPictKf"));

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
     * <br>[機  能] 指定された下書きSIDの宛先を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sendkbn 送信区分
     * @return ret 宛先リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AtesakiModel> getSitagakiAtesakiList(int mailSid,
            int sendkbn) throws SQLException {
        Connection con = null;
        con = getCon();
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        return getSitagakiAtesakiList(mailSid, sendkbn, sortMdl);
    }
    /**
     * <br>[機  能] 指定された下書きSIDの宛先を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sendkbn 送信区分
     * @param sortMdl 共通ソート条件
     * @return ret 宛先リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AtesakiModel> getSitagakiAtesakiList(int mailSid,
            int sendkbn, CmnCmbsortConfModel sortMdl) throws SQLException {
        return getSitagakiAtesakiList(mailSid, -1, sendkbn, sortMdl);
    }
    /**
     * <br>[機  能] 指定された下書きSIDの宛先を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sacJkbn 削除区分 -1を指定した場合、全て取得する。
     * @param sendkbn 送信区分
     * @param sortMdl 共通ソート条件
     * @return ret 宛先リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AtesakiModel> getSitagakiAtesakiList(int mailSid, int sacJkbn,
            int sendkbn, CmnCmbsortConfModel sortMdl)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as sacJkbn,");
            sql.addSql("   CMN_USRM.USR_SID as usrSid,");
            sql.addSql("   CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("   SML_ASAK.SMJ_SENDKBN as smjSendkbn,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as usiPictKf,");
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
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT ");
            sql.addSql("     left join ");
            sql.addSql("       CMN_USRM ");
            sql.addSql("     on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       left join ");
            sql.addSql("         CMN_USRM_INF ");
            sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID, ");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SML_ASAK.SMJ_SENDKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_ASAK.SMS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_ASAK.SAC_SID = SML_ACCOUNT.SAC_SID");
            if (sacJkbn != -1) {
                sql.addSql(" and ");
                sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            }
            __setOrderSQL(sql, sortMdl);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sendkbn);
            sql.addIntValue(mailSid);
            if (sacJkbn != -1) {
                sql.addIntValue(sacJkbn);
            }
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AtesakiModel mdl = new AtesakiModel();
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setBinFileSid(rs.getLong("binSid"));
                mdl.setPhotoFileDsp(rs.getInt("usiPictKf"));
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
     * <br>[機  能] SqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortMdl ソート情報
     * @return SqlBuffer
     */
    private SqlBuffer __setOrderSQL(SqlBuffer sql, CmnCmbsortConfModel sortMdl) {

        sql.addSql(" order by ");
        sql.addSql(" (case when CMN_USRM_INF.USR_SID is null then 1 else 0 end),");
        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   CMN_USRM_INF.USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,CMN_USRM_INF.USI_SEI_KN " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,CMN_USRM_INF.USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,CMN_USRM_INF.USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,CMN_USRM_INF.USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }
        sql.addSql("   ,SML_ACCOUNT.SAC_NAME,SML_ACCOUNT.SAC_SID ");

        return sql;
    }

    /**
     * <p>SIDで指定したアカウントの内、存在するアカウントSIDを返す
     * @param sacSids 指定SID
     * @return 実在するアカウントSID
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, Integer> getExistSacSidList(HashSet<Integer> sacSids)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql(" SAC_SID in (");
            int cnt = 0;
            for (Integer sid : sacSids) {
                sql.addSql((cnt > 0 ? "     ,?" : "     ?"));
                sql.addIntValue(sid);
                cnt++;
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer key   = Integer.valueOf(rs.getString("SAC_SID"));
                String  value = rs.getString("USR_SID");
                if (value != null) {
                    // 通常アカウント
                    ret.put(key, Integer.valueOf(value));
                } else {
                    // 代表者アカウント
                    ret.put(key, -1);
                }
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
     * <br>[機  能] アカウントSID配列から削除済みのユーザ数をカウントする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID配列
     * @return ret カウント件数
     * @throws SQLException SQL実行例外
     */
    public int getCountDeleteAccount(List<String> userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SML_ACCOUNT.SAC_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_SID in (");

            for (int i = 0; i < userSid.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(userSid.get(i));
            }
            sql.addSql(")");

            sql.addSql(" and");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.SAC_JKBN_DELETE);

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
}
