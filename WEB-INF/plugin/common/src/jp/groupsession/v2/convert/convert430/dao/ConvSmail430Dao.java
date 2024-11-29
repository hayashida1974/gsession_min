package jp.groupsession.v2.convert.convert430.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.convert.convert430.model.CvtSmailModel;
import jp.groupsession.v2.convert.convert430.model.CvtSmlAccountModel;
import jp.groupsession.v2.convert.convert430.model.CvtSmlAccountUserModel;
import jp.groupsession.v2.convert.convert430.model.CvtSmlUserModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウント設定用DAO
 * <br>[解  説] v4.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvSmail430Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvSmail430Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvSmail430Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 登録されているユーザのショートメールアカウントを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void smailConv(MlCountMtController mtCon) throws SQLException {

        try {

            //既存のユーザを取得する
            List<CmnUsrmInfModel> usrList = new ArrayList<CmnUsrmInfModel>();
            CmnUsrmDao udao = new CmnUsrmDao(getCon());
            usrList = udao.getUsrAll();

            if (usrList != null && !usrList.isEmpty()) {
                //ユーザのアカウントを登録
                __doInsertSmlAccount(usrList, mtCon);
            }

        } catch (SQLException e) {
            throw e;
        }
    }


    /**
     * <br>[機  能] ユーザのアカウントを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @param usrList List<BaseUserModel>
     * @throws SQLException SQL実行例外
     */
    private void __doInsertSmlAccount(List<CmnUsrmInfModel> usrList,
                                      MlCountMtController mtCon)
    throws SQLException {

        log__.info("ショートメール   コンバート開始");
        int cnt = 0;

        //ユーザのアカウントを登録する
        for (CmnUsrmInfModel bean : usrList) {

            String userName = "";

            if (!StringUtil.isNullZeroStringSpace(bean.getUsiSei())
                    && !StringUtil.isNullZeroStringSpace(bean.getUsiMei())) {
                userName = bean.getUsiSei() + " " + bean.getUsiMei() + " ";
            }

            cnt++;
            log__.info("ショートメールコンバート " + cnt + "/" + usrList.size() + "人 (" + userName + ")");

            int sacSaiSid = 0;

            if (bean.getUsrSid() > 0) {
                sacSaiSid = (int) mtCon.getSaibanNumber("smail",
                    "account", -1);
            }

            //削除区分
            int jkbn = 0;
            if (bean.getUsrJkbn() == 9) {
                jkbn = 1;
            }

            //アカウント登録
            CvtSmlAccountModel accountMdl = new CvtSmlAccountModel();
            accountMdl.setSacSid(sacSaiSid);
            accountMdl.setUsrSid(bean.getUsrSid());
            accountMdl.setSacName(bean.getUsiSei() + " " + bean.getUsiMei());
            accountMdl.setSacTheme(0);
            accountMdl.setSacType(2);
            accountMdl.setSacSendMailtype(0);
            accountMdl.setSacBiko("");
            accountMdl.setSacJkbn(jkbn);
            accountMdl.setSacQuotes(0);
            __insertSmlAccount(accountMdl);

            //アカウントユーザ登録
            if (bean.getUsrSid() >= GSConstUser.USER_RESERV_SID) {
                CvtSmlAccountUserModel accountUserMdl = new CvtSmlAccountUserModel();
                accountUserMdl.setSacSid(sacSaiSid);
                accountUserMdl.setUsrSid(bean.getUsrSid());
                __insertSmlAccountUser(accountUserMdl);
            }


            //送信件数取得
            int count = 0;
            count = selectCnt(bean.getUsrSid());

            //全容量
            Long all_total_size = new Long(0);

            //送信メール10000件ずつ取得

            Connection con = getCon();
            for (int num = 0; num < count; num += 10000) {
                log__.debug("num = " + num);

                ArrayList<CvtSmailModel> smlList = new ArrayList<CvtSmailModel>();
                smlList = selectSmeisList(bean.getUsrSid(), num, 10000);
                //メールの容量を計算
                if (smlList != null && !smlList.isEmpty()) {
                    for (CvtSmailModel mdl : smlList) {

                        Long titile_byte = new Long(0);
                        Long body_byte = new Long(0);
                        Long file_byte = new Long(0);

                        if (!StringUtil.isNullZeroStringSpace(mdl.getSmsTitle())) {
                            try {
                                if (mdl.getSmsTitle().getBytes("UTF-8").length != 0) {
                                    titile_byte = Long.valueOf(
                                            mdl.getSmsTitle().getBytes("UTF-8").length);
                                }
                            } catch (UnsupportedEncodingException e) {
                                log__.error("文字のバイト数取得に失敗");
                                titile_byte = Long.valueOf(mdl.getSmsTitle().getBytes().length);
                            }
                        }

                        if (!StringUtil.isNullZeroStringSpace(mdl.getSmsBody())) {
                            try {
                                if (mdl.getSmsBody().getBytes("UTF-8").length != 0) {
                                    body_byte = Long.valueOf(
                                            mdl.getSmsBody().getBytes("UTF-8").length);
                                }
                            } catch (UnsupportedEncodingException e) {
                                log__.error("文字のバイト数取得に失敗");
                                body_byte = Long.valueOf(mdl.getSmsBody().getBytes().length);
                            }
                        }

                        if (mdl.getSmsSize() != 0) {
                            file_byte = mdl.getSmsSize();
                        }

                        Long totalSize = titile_byte + body_byte + file_byte;
                        mdl.setSmsSize(totalSize);
                        mdl.setSacSid(sacSaiSid);
                        updateSms(mdl, con);

                        all_total_size += totalSize;

                    }

                    log__.info(userName + "送信メール---------" + num + "/" + count);
                }
            }

            //草稿件数取得
            int countWmeis = 0;
            countWmeis = selectWmeisCnt(bean.getUsrSid());

            //草稿メール100件ずつ取得
            for (int wnum = 0; wnum < countWmeis; wnum += 100) {

                ArrayList<CvtSmailModel> smlList = new ArrayList<CvtSmailModel>();
                smlList = selectWmeisList(bean.getUsrSid(), wnum, 100);
                if (smlList != null && !smlList.isEmpty()) {
                    //メールの容量を計算
                    for (CvtSmailModel mdl : smlList) {

                        Long titile_byte = new Long(0);
                        Long body_byte = new Long(0);
                        Long file_byte = new Long(0);

                        if (!StringUtil.isNullZeroStringSpace(mdl.getSmsTitle())) {
                            try {
                                if (mdl.getSmsTitle().getBytes("UTF-8").length != 0) {
                                    titile_byte = Long.valueOf(
                                            mdl.getSmsTitle().getBytes("UTF-8").length);
                                }
                            } catch (UnsupportedEncodingException e) {
                                log__.error("文字のバイト数取得に失敗");
                                titile_byte = Long.valueOf(mdl.getSmsTitle().getBytes().length);
                            }
                        }

                        if (!StringUtil.isNullZeroStringSpace(mdl.getSmsBody())) {
                            try {
                                if (mdl.getSmsBody().getBytes("UTF-8").length != 0) {
                                    body_byte = Long.valueOf(
                                            mdl.getSmsBody().getBytes("UTF-8").length);
                                }
                            } catch (UnsupportedEncodingException e) {
                                log__.error("文字のバイト数取得に失敗");
                                body_byte = Long.valueOf(mdl.getSmsBody().getBytes().length);
                            }
                        }

                        if (mdl.getSmsSize() != 0) {
                            file_byte = mdl.getSmsSize();
                        }

                        Long totalSize = titile_byte + body_byte + file_byte;
                        mdl.setSmsSize(totalSize);
                        mdl.setSacSid(sacSaiSid);
                        updateWms(mdl);

                        all_total_size += totalSize;

                    }

                    log__.info(userName + "草稿メール---------" + wnum + "/" + countWmeis);
                }
            }


            //ディスク使用量登録
            log__.info(userName + "ディスク使用量登録");
            insertAccountDisk(sacSaiSid, all_total_size);

            //受信メール更新
            log__.info(userName + "受信メール更新");
            updateJmeis(bean.getUsrSid(), sacSaiSid);
            //宛先更新
            log__.info(userName + "宛先更新");
            updateAsak(bean.getUsrSid(), sacSaiSid);
            //自動削除更新
            log__.info(userName + "自動削除更新");
            updateAdel(bean.getUsrSid(), sacSaiSid);
            //転送設定登録
            log__.info(userName + "転送設定更新");
            CvtSmlUserModel smlUsrMdl = null;
            smlUsrMdl = selectSmlUsr(bean.getUsrSid());
            if (smlUsrMdl != null) {
                insertTensoSet(smlUsrMdl, sacSaiSid);
            }
            //ひな形更新
            log__.info(userName + "ひな形更新");
            updateHina(bean.getUsrSid(), sacSaiSid);
        }
    }


    /**
     * <p>Insert SML_ACCOUNT Data Bindding JavaBean
     * @param bean SML_ACCOUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void __insertSmlAccount(CvtSmlAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_SEND_MAILTYPE,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
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
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSacName());
            sql.addStrValue(bean.getSacBiko());
            sql.addIntValue(bean.getSacSendMailtype());
            sql.addIntValue(bean.getSacJkbn());
            sql.addIntValue(bean.getSacTheme());
            sql.addIntValue(bean.getSacQuotes());
            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert SML_ACCOUNT_USER Data Bindding JavaBean
     * @param bean SML_ACCOUNT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void __insertSmlAccountUser(CvtSmlAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT_USER(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   null,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getUsrSid());
            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select SML_SMEIS
     * @param usrSid ユーザSID
     * @return SML_SMEISModel
     * @throws SQLException SQL実行例外
     */
    public int selectCnt(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMS_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Select SML_WMEIS
     * @param usrSid ユーザSID
     * @return SML_WMEISModel
     * @throws SQLException SQL実行例外
     */
    public int selectWmeisCnt(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMW_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <br>[機  能] 指定されたユーザの送信メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList < CvtSmailModel > selectSmeisList(int userSid,
                                                     int offset,
                                                     int limit)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<CvtSmailModel> ret = new ArrayList<CvtSmailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_SMEIS.USR_SID as USR_SID, ");
            sql.addSql("   SML_SMEIS.SMS_SID as SMS_SID, ");
            sql.addSql("   SML_SMEIS.SMS_TITLE as SMS_TITLE, ");
            sql.addSql("   SML_SMEIS.SMS_BODY as SMS_BODY, ");
            sql.addSql("   BIN_TABLE.FILE_SIZE as FILE_SIZE");
            sql.addSql(" from ");
            sql.addSql("   SML_SMEIS ");
            sql.addSql(" left join ");
            sql.addSql("   ( ");
            sql.addSql("     select ");
            sql.addSql("       SML_BIN.SML_SID as SML_SID,  ");
            sql.addSql("       SUM(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE ");
            sql.addSql("     from ");
            sql.addSql("       SML_BIN ");
            sql.addSql("     left join CMN_BINF ");
            sql.addSql("     on SML_BIN.BIN_SID = CMN_BINF.BIN_SID ");
            sql.addSql("     group by ");
            sql.addSql("       SML_BIN.SML_SID ");
            sql.addSql("    ) BIN_TABLE ");
            sql.addSql("  on SML_SMEIS.SMS_SID = BIN_TABLE.SML_SID ");
            sql.addSql(" where ");
            sql.addSql("   SML_SMEIS.USR_SID = ?");
            sql.addSql(" order by SML_SMEIS.SMS_SID");

            sql.addIntValue(userSid);
            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CvtSmailModel mdl = new CvtSmailModel();
                mdl.setSmlSid(rs.getInt("SMS_SID"));
                mdl.setSmsTitle(rs.getString("SMS_TITLE"));
                mdl.setSmsBody(rs.getString("SMS_BODY"));
                mdl.setSmsSize(rs.getLong("FILE_SIZE"));

                ret.add(mdl);
            }

//            pstmt =
//                con.prepareStatement(
//                    sql.toSqlString(),
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);
//
//            sql.addIntValue(userSid);
//
//            //ログを出力
//            log__.debug(sql.toLogString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//
//            if (offset > 1) {
//                rs.absolute(offset - 1);
//            }
//
//            for (int i = 0; rs.next() && i < limit; i++) {
//                CvtSmailModel mdl = new CvtSmailModel();
//                mdl.setSmlSid(rs.getInt("SMS_SID"));
//                mdl.setSmsTitle(rs.getString("SMS_TITLE"));
//                mdl.setSmsBody(rs.getString("SMS_BODY"));
//                mdl.setSmsSize(rs.getLong("FILE_SIZE"));
//
//                ret.add(mdl);
//            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }


    /**
     * <br>[機  能] 指定されたユーザの草稿メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList < CvtSmailModel > selectWmeisList(int userSid,
                                                     int offset,
                                                     int limit)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<CvtSmailModel> ret = new ArrayList<CvtSmailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_WMEIS.USR_SID as USR_SID, ");
            sql.addSql("   SML_WMEIS.SMW_SID as SMW_SID, ");
            sql.addSql("   SML_WMEIS.SMW_TITLE as SMW_TITLE, ");
            sql.addSql("   SML_WMEIS.SMW_BODY as SMW_BODY, ");
            sql.addSql("   BIN_TABLE.FILE_SIZE as FILE_SIZE");
            sql.addSql(" from ");
            sql.addSql("   SML_WMEIS ");
            sql.addSql(" left join ");
            sql.addSql("   ( ");
            sql.addSql("     select ");
            sql.addSql("       SML_BIN.SML_SID as SML_SID,  ");
            sql.addSql("       SUM(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE ");
            sql.addSql("     from ");
            sql.addSql("       SML_BIN ");
            sql.addSql("     left join CMN_BINF ");
            sql.addSql("     on SML_BIN.BIN_SID = CMN_BINF.BIN_SID ");
            sql.addSql("     group by ");
            sql.addSql("       SML_BIN.SML_SID ");
            sql.addSql("    ) BIN_TABLE ");
            sql.addSql("  on SML_WMEIS.SMW_SID = BIN_TABLE.SML_SID ");
            sql.addSql(" where ");
            sql.addSql("   SML_WMEIS.USR_SID = ?");
            sql.addSql(" order by SML_WMEIS.SMW_SID");

            sql.addIntValue(userSid);

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CvtSmailModel mdl = new CvtSmailModel();
                mdl.setSmlSid(rs.getInt("SMW_SID"));
                mdl.setSmsTitle(rs.getString("SMW_TITLE"));
                mdl.setSmsBody(rs.getString("SMW_BODY"));
                mdl.setSmsSize(rs.getLong("FILE_SIZE"));

                ret.add(mdl);
            }

//            pstmt =
//                con.prepareStatement(
//                    sql.toSqlString(),
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);
//
//            sql.addIntValue(userSid);
//
//            //ログを出力
//            log__.debug(sql.toLogString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//
//            if (offset > 1) {
//                rs.absolute(offset - 1);
//            }
//
//            for (int i = 0; rs.next() && i < limit; i++) {
//                CvtSmailModel mdl = new CvtSmailModel();
//                mdl.setSmlSid(rs.getInt("SMW_SID"));
//                mdl.setSmsTitle(rs.getString("SMW_TITLE"));
//                mdl.setSmsBody(rs.getString("SMW_BODY"));
//                mdl.setSmsSize(rs.getLong("FILE_SIZE"));
//
//                ret.add(mdl);
//            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }


    /**
     * <p>Update SML_SMEIS Data Bindding JavaBean
     * @param bean SML_SMEIS Data Bindding JavaBean
     * @param con コネクション
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSms(CvtSmailModel bean, Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SMS_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(bean.getSacSid());
            sql.addLongValue(bean.getSmsSize());
            //where
            sql.addIntValue(bean.getSmlSid());

            log__.debug(sql.toLogString());
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
     * <p>Update SML_SMEIS Data Bindding JavaBean
     * @param bean SML_SMEIS Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateWms(CvtSmailModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SMW_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   SMW_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(bean.getSacSid());
            sql.addLongValue(bean.getSmsSize());
            //where
            sql.addIntValue(bean.getSmlSid());

            log__.debug(sql.toLogString());
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
     * <p>Insert SML_ACCOUNT_DISK Data Bindding JavaBean
     * @param sacSid SAC_SID
     * @param sdsSize SDS_SIZE
     * @throws SQLException SQL実行例外
     */
    public void insertAccountDisk(int sacSid, long sdsSize) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT_DISK(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SDS_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addLongValue(sdsSize);
            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update SML_JMEIS Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param sacSid SAC_SID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJmeis(int usrSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update SML_ASAK Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param sacSid SAC_SID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAsak(int usrSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ASAK");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update SML_HINA Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param sacSid SAC_SID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateHina(int usrSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_HINA");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update SML_HINA Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param sacSid SAC_SID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateAdel(int usrSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ADEL");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Select SML_SMEIS
     * @param usrSid ユーザSID
     * @return SML_SMEISModel
     * @throws SQLException SQL実行例外
     */
    public CvtSmlUserModel selectSmlUsr(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CvtSmlUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   USR_SID as USR_SID,");
            sql.addSql("   SML_MAILFW as SML_MAILFW,");
            sql.addSql("   SML_MAIL_DF as SML_MAIL_DF,");
            sql.addSql("   SML_SMAIL_OP as SML_SMAIL_OP,");
            sql.addSql("   SML_HURIWAKE as SML_HURIWAKE,");
            sql.addSql("   SML_ZMAIL1 as SML_ZMAIL1,");
            sql.addSql("   SML_ZMAIL2 as SML_ZMAIL2,");
            sql.addSql("   SML_ZMAIL3 as SML_ZMAIL3,");
            sql.addSql("   0 as SML_AUID,");
            sql.addSql("   current_time as SML_ADATE,");
            sql.addSql("   0 as SML_EUID,");
            sql.addSql("   current_time as SML_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_USER");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CvtSmlUserModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setSmlMailfw(rs.getInt("SML_MAILFW"));
                ret.setSmlMailDf(rs.getString("SML_MAIL_DF"));
                ret.setSmlSmailOp(rs.getInt("SML_SMAIL_OP"));
                ret.setSmlHuriwake(rs.getInt("SML_HURIWAKE"));
                ret.setSmlZmail1(rs.getString("SML_ZMAIL1"));
                ret.setSmlZmail2(rs.getString("SML_ZMAIL2"));
                ret.setSmlZmail3(rs.getString("SML_ZMAIL3"));
                ret.setSmlAuid(rs.getInt("SML_AUID"));
                ret.setSmlAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SML_ADATE")));
                ret.setSmlEuid(rs.getInt("SML_EUID"));
                ret.setSmlEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SML_EDATE")));
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
     * <p>Insert SML_ACCOUNT Data Bindding JavaBean
     * @param bean SML_ACCOUNT Data Bindding JavaBean
     * @param sacSid sacSid
     * @throws SQLException SQL実行例外
     */
    public void insertTensoSet(
            CvtSmlUserModel bean, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT_FORWARD(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAF_MAILFW,");
            sql.addSql("   SAF_MAIL_DF,");
            sql.addSql("   SAF_SMAIL_OP,");
            sql.addSql("   SAF_HURIWAKE,");
            sql.addSql("   SAF_ZMAIL1,");
            sql.addSql("   SAF_ZMAIL2,");
            sql.addSql("   SAF_ZMAIL3,");
            sql.addSql("   SAF_AUID,");
            sql.addSql("   SAF_ADATE,");
            sql.addSql("   SAF_EUID,");
            sql.addSql("   SAF_EDATE");
            sql.addSql(" )");
            sql.addSql("  values");
            sql.addSql("  (");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?");
            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSmlMailfw());
            sql.addStrValue(bean.getSmlMailDf());
            sql.addIntValue(bean.getSmlSmailOp());
            sql.addIntValue(bean.getSmlHuriwake());
            sql.addStrValue(bean.getSmlZmail1());
            sql.addStrValue(bean.getSmlZmail2());
            sql.addStrValue(bean.getSmlZmail3());
            sql.addIntValue(bean.getSmlAuid());
            sql.addDateValue(bean.getSmlAdate());
            sql.addIntValue(bean.getSmlEuid());
            sql.addDateValue(bean.getSmlEdate());

            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

}