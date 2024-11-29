package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmailUsrModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <p>SML_ACCOUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlAccountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountDao.class);
    /** 削除区分フィルタ*/
    public static final int JKBN_ALL = -1;
    /** 削除区分フィルタ*/
    public static final int JKBN_LIV = 0;
    /** 削除区分フィルタ*/
    public static final int JKBN_DEL = 1;
    /**
     * <p>Default Constructor
     */
    public SmlAccountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAccountDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert SML_ACCOUNT Data Bindding JavaBean
     * @param bean SML_ACCOUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAccountModel bean) throws SQLException {

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
     * <p>Update SML_ACCOUNT Data Bindding JavaBean
     * @param bean SML_ACCOUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   SAC_TYPE=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   SAC_NAME=?,");
            sql.addSql("   SAC_BIKO=?,");
            sql.addSql("   SAC_SEND_MAILTYPE=?,");
            sql.addSql("   SAC_THEME=?,");
            sql.addSql("   SAC_QUOTES=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSacName());
            sql.addStrValue(bean.getSacBiko());
            sql.addIntValue(bean.getSacSendMailtype());
            sql.addIntValue(bean.getSacTheme());
            sql.addIntValue(bean.getSacQuotes());

            //where
            sql.addIntValue(bean.getSacSid());

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
     * <p>Update SML_ACCOUNT Data Bindding JavaBean
     * @param bean SML_ACCOUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateConv(SmlAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=NULL");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID<>?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(0);
            sql.addIntValue(0);

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
     * <p>Select SML_ACCOUNT All Data
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountModel> ret = new ArrayList<SmlAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_SEND_MAILTYPE,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlAccountFromRs(rs));
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
     * <p>代表アカウント(作成アカウント)をすべて取得する
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmailUsrModel> selectSmlAccount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmailUsrModel smlUsrMdl = null;
        ArrayList<SmailUsrModel> ret = new ArrayList<SmailUsrModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID is null");
            sql.addSql(" and ");
            sql.addSql("   SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addSql(" order by SAC_NAME, SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                smlUsrMdl = new SmailUsrModel();
                smlUsrMdl.setSacName(rs.getString("SAC_NAME"));
                smlUsrMdl.setSacSid(String.valueOf(rs.getInt("SAC_SID")));
                ret.add(smlUsrMdl);
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
     * <p>SIDで指定した代表アカウント(作成アカウント)をすべて取得する
     * @param sacSids 指定SID
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmailUsrModel> selectSmlAccount(List<Integer> sacSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmailUsrModel smlUsrMdl = null;
        ArrayList<SmailUsrModel> ret = new ArrayList<SmailUsrModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID is null");
            sql.addSql(" and ");
            sql.addSql("   SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addSql(" and SAC_SID in (");
            for (int i = 0; i < sacSids.size(); i++) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql(sacSids.get(i).toString());
            }
            sql.addSql("   )");
            sql.addSql(" order by SAC_NAME, SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                smlUsrMdl = new SmailUsrModel();
                smlUsrMdl.setSacName(rs.getString("SAC_NAME"));
                smlUsrMdl.setSacSid(String.valueOf(rs.getInt("SAC_SID")));
                ret.add(smlUsrMdl);
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
     * <p>代表アカウント(作成アカウント)をすべて取得する
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<UsrLabelValueBean> selectSmlAccountLv() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UsrLabelValueBean smlUsrMdl = null;
        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID is null");
            sql.addSql(" and ");
            sql.addSql("   SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addSql(" order by SAC_NAME, SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                smlUsrMdl = new UsrLabelValueBean();
                smlUsrMdl.setLabel(rs.getString("SAC_NAME"));
                smlUsrMdl.setValue(
                        GSConstSmail.SML_ACCOUNT_STR + String.valueOf(rs.getInt("SAC_SID")));
                ret.add(smlUsrMdl);
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
     * <br>[機  能] 代表アカウント(作成アカウント)のSIDをすべて取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 自動受信対象アカウントのアカウントデータ一覧
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getSmlAccountSidList() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID is null");
            sql.addSql(" and ");
            sql.addSql("   SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SAC_SID"));
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
     * <br>[機  能] アカウントのアカウントデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 自動受信対象アカウントのアカウントデータ一覧
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountModel> getAccountDataList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SmlAccountModel> ret = new ArrayList<SmlAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_SEND_MAILTYPE,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlAccountFromRs(rs));
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
     * <br>[機  能] 指定したユーザが利用可能なアカウント情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public int getAccountCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql = __setAccountSearchSql(sql, userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
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
     * <br>[機  能] 指定したユーザが利用可能なアカウント情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountModel> getAccountList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountModel> ret = new ArrayList<SmlAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE,");
            sql.addSql("   SML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO,");
            sql.addSql("   SML_ACCOUNT.SAC_SEND_MAILTYPE as SAC_SEND_MAILTYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as SAC_JKBN,");
            sql.addSql("   SML_ACCOUNT.SAC_THEME as SAC_THEME,");
            sql.addSql("   SML_ACCOUNT.SAC_QUOTES as SAC_QUOTES,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI ");

            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.SAS_SORT, SML_ACCOUNT.SAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlAccountModel acc = __getSmlAccountFromRs(rs);
                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    acc.setSacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    acc.setSacName(rs.getString("SAC_NAME"));
                }

                ret.add(acc);
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
     * <br>[機  能] ユーザが指定したアカウントを利用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param userSid ユーザSID
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行例外
     */
    public boolean canUseAccount(int sacSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");

            sql = setAccountSearchSql(sql, userSid);

            sql.addSql(" and SML_ACCOUNT.SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.setPagingValue(0, 1);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したユーザが利用可能なアカウントのアカウントSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アカウントSID一覧
     * @throws SQLException SQL実行例外
     */
    public int[] getAccountSidList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int[] sacSidArray = new int[0];
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID");

            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.SAS_SORT");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<Integer> accountSidList = new ArrayList<Integer>();
            while (rs.next()) {
                accountSidList.add(rs.getInt("SAC_SID"));
            }

            sacSidArray = new int[accountSidList.size()];
            for (int i = 0; i < accountSidList.size(); i++) {
                sacSidArray[i] = accountSidList.get(i).intValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sacSidArray;
    }

    /**
     * <br>[機  能] 選択アカウントの使用権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param userSid ユーザSID
     * @return 使用権限
     * @throws SQLException SQLException
     */
    public boolean getAccountPossession(int accountSid, int userSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean bRtn = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COUNT(SML_ACCOUNT.SAC_SID) as CNT");
            sql = __setAccountSearchSql(sql, userSid);
            sql.addSql(" and ");
            sql.addSql("   SML_ACCOUNT.SAC_SID = ? ");

            sql.addIntValue(accountSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CNT") > 0) {
                    bRtn = true;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return bRtn;
    }

    /**
     * <br>[機  能] 指定したユーザのデフォルトアカウントSIDを取得する
     * <br>[解  説] 利用可能なアカウントのうち、並び順 = 1のものをデフォルトアカウントとする
     * <br>[備  考] 利用可能なアカウントが存在しない場合、0を返す
     * @param userSid ユーザSID
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public int getDefaultAccountSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int defAccountSid = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID");

            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT, SML_ACCOUNT.SAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                defAccountSid = rs.getInt("SAC_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return defAccountSid;
    }
    /**
    *
    * <br>[機  能] sidによる絞り込み条件文生成共通処理
    * <br>[解  説]
    * <br>[備  考]
    * @param target 対象フィールド名
    * @param list sid一覧
    * @return 絞り込み条件文
    */
   private String __whereInString(String target, List<? extends Object> list) {
       StringBuilder usrSidSql = new StringBuilder();

       if (list == null || list.size() == 0) {
           // リストなし
       } else if (list.size() == 1) {
           usrSidSql.append(" ");
           usrSidSql.append(target);
           usrSidSql.append(" = ");
           usrSidSql.append(String.valueOf(list.get(0)));
       } else if (list.size() > 500) {
           int st = 0;
           int max = list.size();
           int step = 500;
           int ed = step;
           while (st < max) {
               if (st == 0) {
                   usrSidSql.append(" ( ");
               } else {
                   usrSidSql.append(" or ");
               }
               if (ed > max) {
                   ed = max;
               }
               usrSidSql.append(target);
               usrSidSql.append(" in ( ");
               usrSidSql.append(String.valueOf(list.get(st)));
               for (int idx = st + 1; idx < ed; idx++) {
                   usrSidSql.append(", ");
                   usrSidSql.append(String.valueOf(list.get(idx)));
               }
               usrSidSql.append(" ) ");

               st = ed;
               ed = ed + step;
           }
           usrSidSql.append(" ) ");

       } else {
           usrSidSql.append(" ");
           usrSidSql.append(target);
           usrSidSql.append(" in ( ");
           usrSidSql.append(String.valueOf(list.get(0)));
           for (int idx = 1; idx < list.size(); idx++) {
               usrSidSql.append(", ");
               usrSidSql.append(String.valueOf(list.get(idx)));
           }
           usrSidSql.append(" ) ");
       }
       return usrSidSql.toString();
   }

    /**
     * <p>Select SML_ACCOUNT
     * @param sacSid SAC_SID
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountModel> select(List<Integer> sacSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmlAccountModel> ret = new ArrayList<SmlAccountModel>();
        if (sacSid == null || sacSid.size() == 0) {
            return ret; // 指定アカウントSIDがない場合、空リストを返す
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE,");
            sql.addSql("   SML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO,");
            sql.addSql("   SML_ACCOUNT.SAC_SEND_MAILTYPE as SAC_SEND_MAILTYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as SAC_JKBN,");
            sql.addSql("   SML_ACCOUNT.SAC_THEME as SAC_THEME,");
            sql.addSql("   SML_ACCOUNT.SAC_QUOTES as SAC_QUOTES,");

            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");

            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join CMN_USRM_INF");
            sql.addSql("   on SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql(__whereInString("SML_ACCOUNT.SAC_SID", sacSid));
            sql.addSql(" and  SML_ACCOUNT.SAC_JKBN=?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlAccountModel bean = new SmlAccountModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSacType(rs.getInt("SAC_TYPE"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setSacName(rs.getString("SAC_NAME"));
                bean.setSacBiko(rs.getString("SAC_BIKO"));
                bean.setSacSendMailtype(rs.getInt("SAC_SEND_MAILTYPE"));
                bean.setSacJkbn(rs.getInt("SAC_JKBN"));
                bean.setSacTheme(rs.getInt("SAC_THEME"));
                bean.setSacQuotes(rs.getInt("SAC_QUOTES"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    bean.setSacName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                }
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
     * <p>Select SML_ACCOUNT
     * @param sacSid SAC_SID
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public SmlAccountModel select(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlAccountModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE,");
            sql.addSql("   SML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO,");
            sql.addSql("   SML_ACCOUNT.SAC_SEND_MAILTYPE as SAC_SEND_MAILTYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as SAC_JKBN,");
            sql.addSql("   SML_ACCOUNT.SAC_THEME as SAC_THEME,");
            sql.addSql("   SML_ACCOUNT.SAC_QUOTES as SAC_QUOTES,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");

            sql.addSql("     left join");
            sql.addSql("       CMN_USRM_INF");
            sql.addSql("     on");
            sql.addSql("       SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("     left join");
            sql.addSql("       CMN_USRM");
            sql.addSql("     on");
            sql.addSql("       SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");

            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new SmlAccountModel();
                ret.setSacSid(rs.getInt("SAC_SID"));
                ret.setSacType(rs.getInt("SAC_TYPE"));
                ret.setUsrSid(rs.getInt("USR_SID"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    ret.setSacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));

                } else {
                    ret.setSacName(rs.getString("SAC_NAME"));
                }

                ret.setSacBiko(rs.getString("SAC_BIKO"));
                ret.setSacSendMailtype(rs.getInt("SAC_SEND_MAILTYPE"));
                ret.setSacJkbn(rs.getInt("SAC_JKBN"));
                ret.setSacTheme(rs.getInt("SAC_THEME"));
                ret.setSacQuotes(rs.getInt("SAC_QUOTES"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Select SML_ACCOUNT
     * @param usrSid USR_SID
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public SmlAccountModel selectFromUsrSid(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlAccountModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE,");
            sql.addSql("   SML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO,");
            sql.addSql("   SML_ACCOUNT.SAC_SEND_MAILTYPE as SAC_SEND_MAILTYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as SAC_JKBN,");
            sql.addSql("   SML_ACCOUNT.SAC_THEME as SAC_THEME,");
            sql.addSql("   SML_ACCOUNT.SAC_QUOTES as SAC_QUOTES,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");

            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" on SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getSmlAccountFromUserRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>ユーザSIDからアカウントSIDを取得
     * @param usrSids USR_SID
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<String> selectFromUsrSids(String[] usrSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_SEND_MAILTYPE,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in (");
            for (int i = 0; i < usrSids.length; i++) {
                if (i > 0) {
                    sql.addSql(", ?");
                } else {
                    sql.addSql("  ?");
                }
                sql.addIntValue(Integer.parseInt(usrSids[i]));
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(String.valueOf(rs.getInt("SAC_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] アカウントSIDからアカウント情報を取得するSQL生成
     * <br>[解  説]
     * <br>[備  考] ユーザアカウントの場合、名前が正しくない可能性がある
     * @param accountSids アカウントSID
     * @param filterJkbn 削除区分フィルタ （-1:全て 0:存在 1:削除済み）
     * @return Sql
     */
    private SqlBuffer __sqlSelectSacSids(String[] accountSids, int filterJkbn) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        sql.addSql("   SAC_SID,");
        sql.addSql("   SAC_TYPE,");
        sql.addSql("   USR_SID,");
        sql.addSql("   SAC_NAME,");
        sql.addSql("   SAC_BIKO,");
        sql.addSql("   SAC_SEND_MAILTYPE,");
        sql.addSql("   SAC_JKBN,");
        sql.addSql("   SAC_THEME,");
        sql.addSql("   SAC_QUOTES");
        sql.addSql(" from");
        sql.addSql("   SML_ACCOUNT");
        sql.addSql(" where ");
        sql.addSql("   SAC_SID in (");
        for (int i = 0; i < accountSids.length; i++) {
            if (i > 0) {
                sql.addSql(", ?");
            } else {
                sql.addSql("  ?");
            }
            sql.addIntValue(Integer.parseInt(accountSids[i]));
        }
        sql.addSql("   )");
        if (filterJkbn != JKBN_ALL) {
            sql.addSql(" and");
            sql.addSql("  SAC_JKBN=?");
            sql.addIntValue(filterJkbn);
        }
        sql.addSql(" order by SAC_NAME, SAC_SID");
        return sql;
    }
    /**
     * <p>アカウントSIDからアカウント情報を取得
     * <br>[備  考] ユーザアカウントの場合、名前が正しくない可能性がある
     * @param accountSids SAC_SID
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> selectSacSids(String[] accountSids) throws SQLException {
        return selectSacSids(accountSids, JKBN_ALL);
    }
    /**
     * <p>アカウントSIDからアカウント情報を取得
     * <br>[備  考] ユーザアカウントの場合、名前が正しくない可能性がある
     * @param accountSids SAC_SID
     * @param filterJkbn 削除区分フィルタ （-1:全て 0:存在 1:削除済み）
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> selectSacSids(String[] accountSids, int filterJkbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        LabelValueBean mdl = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        if (accountSids != null && accountSids.length > 0) {

            try {
                SqlBuffer sql = __sqlSelectSacSids(accountSids, filterJkbn);
                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    mdl = new LabelValueBean();
                    mdl.setValue(String.valueOf(rs.getInt("SAC_SID")));
                    mdl.setLabel(rs.getString("SAC_NAME"));
                    ret.add(mdl);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);
            }

        }
        return ret;
    }
    /**
     * <p>アカウントSIDからアカウント情報を取得
     * @param accountSids SAC_SID
     * @param filterJkbn 削除区分フィルタ （-1:全て 0:存在 1:削除済み）
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<UsrLabelValueBean> selectSacSids2(String[] accountSids, int filterJkbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UsrLabelValueBean mdl = null;
        List<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        con = getCon();

        if (accountSids != null && accountSids.length > 0) {

            try {
                SqlBuffer sql = __sqlSelectSacSids(accountSids, filterJkbn);
                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    mdl = new UsrLabelValueBean();
                    mdl.setValue("sac" + String.valueOf(rs.getInt("SAC_SID")));
                    mdl.setLabel(rs.getString("SAC_NAME"));
                    ret.add(mdl);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);
            }

        }
        return ret;
    }

    /**
     * <p>アカウントSIDからアカウント情報を取得
     * @param accountSids SAC_SID
     * @return SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<UsrLabelValueBean> selectSacSids2(String[] accountSids) throws SQLException {


        return selectSacSids2(accountSids, JKBN_ALL);
    }


    /**
     * <br>[機  能] 利用可能なアカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<SmlAccountModel> getExistAccountData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SmlAccountModel> ret = new ArrayList<SmlAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAC_NAME,");
            sql.addSql("   SAC_BIKO,");
            sql.addSql("   SAC_SEND_MAILTYPE,");
            sql.addSql("   SAC_JKBN,");
            sql.addSql("   SAC_THEME,");
            sql.addSql("   SAC_QUOTES");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SAC_JKBN = ?");

            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlAccountFromRs(rs));
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
     * <br>[機  能] 利用可能なアカウントのアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<Integer> getExistAccountSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SAC_JKBN = ?");

            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SAC_SID"));
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
     * <p>Delete SML_ACCOUNT
     * @param sacSid SAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

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
     * <br>[機  能] アカウント情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SmlAccountModel
     * @param accountMode アカウント種別
     * @throws SQLException SQL実行例外
     */
    public void insertAccount(SmlAccountModel bean, int accountMode) throws SQLException {

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
//            if (accountMode == GSConstSmail.SAC_TYPE_USER) {
//                sql.addSql("   ?,");
//            } else {
                sql.addSql("   null,");
//            }
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
//            if (accountMode != GSConstSmail.SAC_TYPE_USER) {
//                sql.addValue(bean.getUsrSid());
//            }
            sql.addStrValue(bean.getSacName());
            sql.addStrValue(bean.getSacBiko());
            sql.addIntValue(bean.getSacSendMailtype());
            sql.addIntValue(bean.getSacJkbn());
            sql.addIntValue(bean.getSacTheme());
            sql.addIntValue(bean.getSacQuotes());
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
     * <br>[機  能] アカウント情報の登録を行う(ユーザ登録時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SmlAccountModel
     * @throws SQLException SQL実行例外
     */
    public void insertAccountDef(SmlAccountModel bean) throws SQLException {

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
     * <br>[機  能] アカウント情報の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SmlAccountModel
     * @param accountMode アカウントモード
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateAccount(SmlAccountModel bean, int accountMode) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   SAC_NAME=?,");
            //アカウント処理モード 共通
            if (accountMode == GSConstSmail.ACCOUNTMODE_COMMON) {
                sql.addSql("   SAC_TYPE=?,");
            }
            sql.addSql("   SAC_BIKO=?,");
            sql.addSql("   SAC_SEND_MAILTYPE=?,");
            sql.addSql("   SAC_THEME=?,");
            sql.addSql("   SAC_QUOTES=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSacName());
            //アカウント処理モード 共通
            if (accountMode == GSConstSmail.ACCOUNTMODE_COMMON) {
                sql.addIntValue(bean.getSacType());
            }
            sql.addStrValue(bean.getSacBiko());
            sql.addIntValue(bean.getSacSendMailtype());
            sql.addIntValue(bean.getSacTheme());
            sql.addIntValue(bean.getSacQuotes());

            //where
            sql.addIntValue(bean.getSacSid());

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
     * <br>[機  能] アカウント情報の変更を行う(アカウント編集画面)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SmlAccountModel
     * @param accountMode アカウントモード
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateAccountEdit(SmlAccountModel bean, int accountMode) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   SAC_NAME=?,");
            //アカウント処理モード 共通
            if (accountMode == GSConstSmail.ACCOUNTMODE_COMMON) {
                sql.addSql("   SAC_TYPE=?,");
            }
            sql.addSql("   SAC_SIGN=?,");
            sql.addSql("   SAC_AUTOTO=?,");
            sql.addSql("   SAC_AUTOCC=?,");
            sql.addSql("   SAC_AUTOBCC=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSacName());
            //アカウント処理モード 共通
            if (accountMode == GSConstSmail.ACCOUNTMODE_COMMON) {
                sql.addIntValue(bean.getSacType());
            }

            //where
            sql.addIntValue(bean.getSacSid());

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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(int sacSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   SAC_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            sql.addIntValue(jkbn);
            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSidList アカウントSID
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(String[] sacSidList, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   SAC_JKBN=?");
            sql.addIntValue(jkbn);
            sql.addSql(" where ");
            sql.addSql("   SAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(sacSidList[0]));
            for (String accountSid : sacSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] アカウント情報取得SQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @return SqlBuffer
     */
    public SqlBuffer setAccountSearchSql(SqlBuffer sql, int userSid) {
        sql.addSql(" where");
        sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
        sql.addSql(" and");
        sql.addSql("   (");
        sql.addSql("      (");
        sql.addSql("         SML_ACCOUNT.SAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         SML_ACCOUNT.USR_SID = ?");
        sql.addSql("      )");
        sql.addSql("      or ");
        sql.addSql("      (");
        sql.addSql("         exists ( ");
        sql.addSql("           select SAC_SID from SML_ACCOUNT_USER ");
        sql.addSql("           where");
        sql.addSql("           GRP_SID in ( ");
        sql.addSql("             select GRP_SID from CMN_BELONGM ");
        sql.addSql("             where USR_SID = ? ");
        sql.addSql("           )");
        sql.addSql("         and ");
        sql.addSql("           SML_ACCOUNT.SAC_SID = SML_ACCOUNT_USER.SAC_SID ");
        sql.addSql("         and ");
        sql.addSql("           SML_ACCOUNT_USER.USR_SID < 0 ");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("      or ");
        sql.addSql("      (");
        sql.addSql("         exists ( ");
        sql.addSql("           select SAC_SID from SML_ACCOUNT_USER ");
        sql.addSql("           where");
        sql.addSql("             USR_SID = ? ");
        sql.addSql("           and ");
        sql.addSql("             SML_ACCOUNT.SAC_SID = SML_ACCOUNT_USER.SAC_SID ");
        sql.addSql("           and ");
        sql.addSql("             SML_ACCOUNT_USER.GRP_SID < 0 ");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("   )");

        sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
        sql.addIntValue(GSConstSmail.SAC_TYPE_NORMAL);
        sql.addIntValue(userSid);
        sql.addIntValue(userSid);
        sql.addIntValue(userSid);

        return sql;
    }

    /**
     * <br>[機  能] アカウント情報取得SQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @return SqlBuffer
     */
    private SqlBuffer __setAccountSearchSql(SqlBuffer sql, int userSid) {
        sql.addSql(" from ");
        sql.addSql("   SML_ACCOUNT");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("       select SAC_SID, SAS_SORT");
        sql.addSql("       from SML_ACCOUNT_SORT");
        sql.addSql("       where USR_SID = ?");
        sql.addSql("     ) ACCOUNT_SORT");
        sql.addSql("   on");
        sql.addSql("     SML_ACCOUNT.SAC_SID = ACCOUNT_SORT.SAC_SID");
        sql.addSql("      left join");
        sql.addSql("        CMN_USRM_INF");
        sql.addSql("      on");
        sql.addSql("        SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addIntValue(userSid);

        sql = setAccountSearchSql(sql, userSid);

        return sql;
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

    /**
     * <br>[機  能] 指定したアカウント名に該当するアカウントのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param accountName アカウント名
     * @return アカウントSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> searchByNameAccount(String accountName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Integer> accountList = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select CMN_USRM.USR_SID, CMN_USRM_INF.USI_SEI"
                            + " || ' ' || CMN_USRM_INF.USI_MEI as USR_ACCOUNT_NAME");
            sql.addSql("      from CMN_USRM, CMN_USRM_INF");
            sql.addSql("      where CMN_USRM.USR_JKBN = ?");
            sql.addSql("      and CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("     ) USER_ACCOUNT");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = USER_ACCOUNT.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            sql.addSql(" and ");
            sql.addSql("   (");
            //共通アカウント
            sql.addSql("     (");
            sql.addSql("       coalesce(SML_ACCOUNT.USR_SID, 0) <= 0");
            sql.addSql("     and ");
            sql.addSql("       SML_ACCOUNT.SAC_NAME = ?");
            sql.addSql("     )");
            sql.addSql("   or ");
            //ユーザアカウント
            sql.addSql("     (");
            sql.addSql("       coalesce(SML_ACCOUNT.USR_SID, 0) > 0");
            sql.addSql("     and ");
            sql.addSql("       USER_ACCOUNT.USR_ACCOUNT_NAME = ?");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addStrValue(accountName);
            sql.addStrValue(accountName);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                accountList.add(rs.getInt("SAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return accountList;
    }

    /**
     *
     * <br>[機  能] アカウント種別を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid SAC_SID
     * @return アカウント種別
     * @throws SQLException SQL実行例外
     */
    public int getSacType(int sacSid) throws SQLException {
         int sacType = GSConstSmail.SAC_TYPE_NORMAL;

         PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_TYPE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addIntValue(sacSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sacType = rs.getInt("SAC_TYPE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return sacType;
    }

    /**
     * <p>Create SML_ACCOUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAccountModel
     * @throws SQLException SQL実行例外
     */
    private SmlAccountModel __getSmlAccountFromRs(ResultSet rs) throws SQLException {
        SmlAccountModel bean = new SmlAccountModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSacType(rs.getInt("SAC_TYPE"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSacName(rs.getString("SAC_NAME"));
        bean.setSacBiko(rs.getString("SAC_BIKO"));
        bean.setSacSendMailtype(rs.getInt("SAC_SEND_MAILTYPE"));
        bean.setSacJkbn(rs.getInt("SAC_JKBN"));
        bean.setSacTheme(rs.getInt("SAC_THEME"));
        bean.setSacQuotes(rs.getInt("SAC_QUOTES"));
        return bean;
    }

    /**
     * <p>Create SML_ACCOUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAccountModel
     * @throws SQLException SQL実行例外
     */
    private SmlAccountModel __getSmlAccountFromUserRs(ResultSet rs) throws SQLException {
        SmlAccountModel bean = new SmlAccountModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSacType(rs.getInt("SAC_TYPE"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSacName(rs.getString("SAC_NAME"));
        bean.setSacBiko(rs.getString("SAC_BIKO"));
        bean.setSacSendMailtype(rs.getInt("SAC_SEND_MAILTYPE"));
        bean.setSacJkbn(rs.getInt("SAC_JKBN"));
        bean.setSacTheme(rs.getInt("SAC_THEME"));
        bean.setSacQuotes(rs.getInt("SAC_QUOTES"));

        if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
            bean.setSacName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
        }

        return bean;
    }
}
