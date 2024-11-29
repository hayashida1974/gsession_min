package jp.groupsession.v2.wml.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.model.MailData;
import jp.groupsession.v2.wml.model.MailFilterConditionModel;
import jp.groupsession.v2.wml.model.MailFilterModel;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.WmlMailDeleteModel;
import jp.groupsession.v2.wml.model.WmlMainInfoMessageModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;
import jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel;

/**
 * <br>[機  能] WEBメールプラグインで共通使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WebmailDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WebmailDao.class);

    /**
     * <p>Default Constructor
     */
    public WebmailDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WebmailDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したラベルが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param wlbSid ラベルSID
     * @return true:存在する、false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existLabel(int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(WLB_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where");
            sql.addSql("   WLB_SID = ?");
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getLong("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したディレクトリが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wdrSid ディレクトリSID
     * @return true:存在する、false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existDirectory(int wacSid, long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(WDR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WDR_SID = ?");
            sql.addIntValue(wacSid);
            sql.addLongValue(wdrSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getLong("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したアカウントのディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param delDirTypeList ディレクトリ種別一覧
     * @return ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getWdrSidList(int wacSid, List<Integer> delDirTypeList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Long> wdrSidList = new ArrayList<Long>();

        if (delDirTypeList == null || delDirTypeList.isEmpty()) {
            return wdrSidList;
        }

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);

            sql.addSql(" and");
            sql.addSql("   WDR_TYPE in (");
            sql.addSql("     ?");
            sql.addLongValue(delDirTypeList.get(0).longValue());

            for (int i = 1; i < delDirTypeList.size(); i++) {
                sql.addSql("     ,?");
                sql.addLongValue(delDirTypeList.get(i).longValue());
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                wdrSidList.add(rs.getLong("WDR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return wdrSidList;
    }

    /**
     * <br>[機  能] 指定したアカウントのディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wdrType ディレクトリ種別
     * @return ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public long getWdrSid(int wacSid, int wdrType) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long wdrSid = 0;

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WDR_TYPE = ?");
            sql.addIntValue(wacSid);
            sql.addIntValue(wdrType);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                wdrSid = rs.getLong("WDR_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return wdrSid;
    }

    /**
     * <br>[機  能] 指定したディレクトリ内のメール件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSidList ディレクトリSID
     * @return メール件数
     * @throws SQLException SQL実行時例外
     */
    public BigInteger getMailCount(List<Long> wdrSidList) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BigInteger count = new BigInteger("0");

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(WMD_MAILNUM) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WDR_SID in (");

            for (int i = 0; i < wdrSidList.size(); i++) {
                if (i < wdrSidList.size() - 1) {
                    sql.addSql("     ?,");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(wdrSidList.get(i));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = new BigInteger(rs.getString("CNT"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
        }

        return count;
    }

    /**
     * <br>[機  能] フィルター情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return フィルター情報
     * @throws SQLException SQL実行時例外
     */
    public List<MailFilterModel> getFilterData(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MailFilterModel> filterList = new ArrayList<MailFilterModel>();

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_FILTER.WFT_SID as WFT_SID,");
            sql.addSql("   WML_FILTER.WFT_TEMPFILE as WFT_TEMPFILE,");
            sql.addSql("   WML_FILTER.WFT_ACTION_LABEL as WFT_ACTION_LABEL,");
            sql.addSql("   WML_FILTER.WLB_SID as WLB_SID,");
            sql.addSql("   WML_FILTER.WFT_ACTION_READ as WFT_ACTION_READ,");
            sql.addSql("   WML_FILTER.WFT_ACTION_FOLDER as WFT_ACTION_FOLDER,");
            sql.addSql("   WML_FILTER.WFT_ACTION_FORWARD as WFT_ACTION_FORWARD,");
            sql.addSql("   WML_FILTER.WFT_ACTION_FWADDRESS as WFT_ACTION_FWADDRESS,");
            sql.addSql("   WML_FILTER.WFT_CONDITION as WFT_CONDITION");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER, ");
            sql.addSql("   WML_FILTER_SORT ");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("       WML_FILTER.WFT_TYPE = ? ");
            sql.addSql("     and ");
            sql.addSql("       WML_FILTER.WAC_SID = ?");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       WML_FILTER.WFT_TYPE = ? ");
            sql.addSql("     and ");
            sql.addSql("       (");
            sql.addSql("         WML_FILTER.USR_SID in (");
            sql.addSql("           select USR_SID from WML_ACCOUNT_USER");
            sql.addSql("           where WAC_SID = ?");
            sql.addSql("           and coalesce(USR_SID, 0) > 0");
            sql.addSql("         )");
            sql.addSql("        or");
            sql.addSql("         WML_FILTER.USR_SID in (");
            sql.addSql("           select CMN_BELONGM.USR_SID");
            sql.addSql("           from");
            sql.addSql("             WML_ACCOUNT_USER,");
            sql.addSql("             CMN_BELONGM");
            sql.addSql("           where WML_ACCOUNT_USER.WAC_SID = ?");
            sql.addSql("           and coalesce(WML_ACCOUNT_USER.GRP_SID, 0) > 0");
            sql.addSql("           and WML_ACCOUNT_USER.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   WML_FILTER_SORT.WAC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   WML_FILTER.WFT_SID = WML_FILTER_SORT.WFT_SID ");
            sql.addSql(" order by ");
            sql.addSql("   WML_FILTER_SORT.WFS_SORT");

            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.LABELTYPE_ALL);
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MailFilterModel filterData = new MailFilterModel();
                filterData.setWftSid(rs.getInt("WFT_SID"));
                filterData.setTempFile(
                        rs.getInt("WFT_TEMPFILE") == GSConstWebmail.FILTER_TEMPFILE_YES);
                filterData.setLabel(
                        rs.getInt("WFT_ACTION_LABEL") == GSConstWebmail.FILTER_LABEL_SETLABEL);
                filterData.setLabelSid(rs.getInt("WLB_SID"));
                filterData.setReaded(
                        rs.getInt("WFT_ACTION_READ") == GSConstWebmail.FILTER_READED_SETREADED);
                filterData.setMove(
                        rs.getInt("WFT_ACTION_FOLDER"));
                filterData.setForward(
                        rs.getInt("WFT_ACTION_FORWARD") == GSConstWebmail.FILTER_FORWARD_SEND);
                filterData.setForwardAddress(rs.getString("WFT_ACTION_FWADDRESS"));
                filterData.setCondition(rs.getInt("WFT_CONDITION"));

                filterList.add(filterData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return filterList;
    }

    /**
     * <br>[機  能] フィルター条件を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wftSid フィルターSID
     * @return フィルター条件
     * @throws SQLException SQL実行時例外
     */
    public List<MailFilterConditionModel> getFilterConditionData(int wftSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MailFilterConditionModel> conditionList
            = new ArrayList<MailFilterConditionModel>();

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFC_TYPE,");
            sql.addSql("   WFC_EXPRESSION,");
            sql.addSql("   WFC_TEXT");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" where");
            sql.addSql("   WFT_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   WFC_NUM");
            sql.addIntValue(wftSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            MailFilterConditionModel conditionData = null;
            while (rs.next()) {
                conditionData = new MailFilterConditionModel();
                conditionData.setType(rs.getInt("WFC_TYPE"));
                conditionData.setExpression(rs.getInt("WFC_EXPRESSION"));
                conditionData.setText(rs.getString("WFC_TEXT"));
                conditionList.add(conditionData);
            }
            conditionData = null;

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return conditionList;
    }

    /**
     * <br>[機  能] 指定されたメール情報のうち、ゴミ箱ディレクトリ内のメッセージ番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @param dustDirSid ゴミ箱ディレクトリのディレクトリSID
     * @return フィルター条件
     * @throws SQLException SQL実行時例外
     */
    public long[] getMessageNumInDust(long[] mailNumList, long dustDirSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long[] dustMailNumArray = new long[0];

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WDR_SID = ?");
            sql.addLongValue(dustDirSid);

            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM in (");
            for (int i = 0; i < mailNumList.length; i++) {
                if (i == mailNumList.length - 1) {
                    sql.addSql("     " + mailNumList[i]);
                } else {
                    sql.addSql("     " + mailNumList[i] + ",");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<Long> dustMailList = new ArrayList<Long>();
            while (rs.next()) {
                dustMailList.add(rs.getLong("WMD_MAILNUM"));
            }

            dustMailNumArray = new long[dustMailList.size()];
            for (int i = 0; i < dustMailList.size(); i++) {
                dustMailNumArray[i] = dustMailList.get(i).longValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return dustMailNumArray;
    }

    /**
     * <br>[機  能] メール情報を指定したディレクトリへ移動する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param wdrSid ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public void moveMail(long[] mailNum, long wdrSid) throws SQLException {

        if (mailNum == null || mailNum.length == 0) {
            return;
        }
        PreparedStatement pstmt = null;

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" set");
            sql.addSql("   WDR_SID = ?");
            sql.addLongValue(wdrSid);

            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM in (");
            boolean first = true;
            for (long messageNum : mailNum) {
                if (!first) {
                    sql.addSql("     ,");
                }
                sql.addSql("     ?");
                sql.addLongValue(messageNum);
                first = false;
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <br>[機  能] フィルタリング対象となるメールのメッセージ番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum 対象となるメールのメッセージ番号
     * @param filterData フィルター情報
     * @param conditionList フィルタリング条件
     * @param wacSid アカウントSID
     * @return フィルタリング対象となるメールのメッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getFilteringMailNum(List<Long> mailNum, MailFilterModel filterData,
                                    List<MailFilterConditionModel> conditionList, int wacSid)
    throws SQLException {

        if (mailNum == null || mailNum.isEmpty()) {
            return new ArrayList<Long>(mailNum.size());
        }

        List<Long> updateMailNum = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SqlBuffer sql = null;

        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);
            sql.addSql(" and");
            sql = __setMailNumSql(sql, mailNum);
            sql = __setFilteringCondition(sql, filterData, conditionList, mailNum);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            updateMailNum = new ArrayList<Long>(mailNum.size());
            while (rs.next()) {
                updateMailNum.add(rs.getLong("WMD_MAILNUM"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return updateMailNum;
    }

    /**
     * <br>[機  能] フィルタリングの条件と一致したメール情報を更新する
     * <br>[解  説] フィルター情報の更新内容に従ってメール情報の更新を行う
     * <br>[備  考]
     * @param rcvMdl 受信モデル
     * @param mailNum 更新対象メールのメッセージ番号
     * @param filterData フィルター情報
     * @throws SQLException SQL実行時例外
     */
    public void updateFilterlingMail(Pop3ReceiveModel rcvMdl,
            List<Long> mailNum, MailFilterModel filterData)
    throws SQLException {
        int wacSid = rcvMdl.getWacSid();

        PreparedStatement pstmt = null;

        SqlBuffer sql = null;
        try {

            //既読にする or フォルダへ移動
            if (filterData.isReaded() || filterData.getMove() != GSConstWebmail.FILTER_MOVE_NOSET) {
                //SQL文
                sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   WML_MAILDATA");
                sql.addSql(" set");

                if (filterData.isReaded()) {
                    sql.addSql("   WMD_READED = ?");
                    sql.addIntValue(GSConstWebmail.READEDFLG_READED);
                }
                if (filterData.getMove() != GSConstWebmail.FILTER_MOVE_NOSET) {
                    if (filterData.isReaded()) {
                        sql.addSql("   ,");
                    }
                    long wdrSid;
                    switch (filterData.getMove()) {
                    case GSConstWebmail.FILTER_MOVE_DUST:
                        wdrSid = rcvMdl.getDustWdrSid();
                        break;
                    case GSConstWebmail.FILTER_MOVE_STRAGE:
                        wdrSid = rcvMdl.getStrageWdrSid();
                        break;
                    default:
                        wdrSid = rcvMdl.getSaveWdrSid();
                        break;
                    }

                    sql.addSql("   WDR_SID = ?");
                    sql.addLongValue(wdrSid);
                }

                sql.addSql(" where");
                sql.addSql("   WAC_SID=?");
                sql.addIntValue(wacSid);
                sql.addSql(" and");
                sql = __setMailNumSql(sql, mailNum);

                log__.info(sql.toLogString());

                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                JDBCUtil.closePreparedStatement(pstmt);
                pstmt = null;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <br>[機  能] 指定したメール情報にラベルを付与する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNum 対象メールのメッセージ番号
     * @param wlbSid ラベルSID
     * @throws SQLException SQL実行時例外
     */
    public void insertLabelRelation(int wacSid, List<Long> mailNum, long wlbSid)
    throws SQLException {

        PreparedStatement pstmt = null;

        SqlBuffer sql = null;
        try {
            sql = new SqlBuffer();
            sql.addSql(" insert into");
            sql.addSql(" WML_LABEL_RELATION (");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");

            boolean first = true;
            for (Long wmdMailNum : mailNum) {
                if (!first) {
                    sql.addSql(" ,");
                }

                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");
                sql.addLongValue(wmdMailNum.longValue());
                sql.addLongValue(wlbSid);
                sql.addIntValue(wacSid);

                first = false;
            }

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <br>[機  能] フィルタリングの条件と一致したメール情報を更新する
     * <br>[解  説] フィルター情報の更新内容に従ってメール情報の更新を行う
     * <br>[備  考]
     * @param filterData フィルター情報
     * @param conditionList フィルター条件
     * @param wacSid 更新対象のアカウントSID
     * @param moveToWdrSid ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public void updateFilterlingMail(MailFilterModel filterData,
                                    List<MailFilterConditionModel> conditionList,
                                    int wacSid, long moveToWdrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlBuffer sql = null;
        try {

            //ラベルを付与
            if (filterData.isLabel()) {
                sql = new SqlBuffer();
                sql.addSql(" insert into");
                sql.addSql(" WML_LABEL_RELATION (");
                sql.addSql("   WMD_MAILNUM,");
                sql.addSql("   WLB_SID,");
                sql.addSql("   WAC_SID");
                sql.addSql(" )");
                sql.addSql(" select");
                sql.addSql("   WMD_MAILNUM,");
                sql.addSql("   ?,");
                sql.addSql("   WAC_SID");
                sql.addSql(" from");
                sql.addSql("   WML_MAILDATA");
                sql.addSql(" where");
                sql.addSql("   WDR_SID in (");
                sql.addSql("     select WDR_SID from WML_DIRECTORY");
                sql.addSql("     where WAC_SID = ?");
                sql.addSql("     and WDR_TYPE <> ?"); //「ゴミ箱」フォルダ内のメールは除外する
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   not exists (");
                sql.addSql("     select 1 from WML_LABEL_RELATION");
                sql.addSql("     where");
                sql.addSql("       WLB_SID = ?");
                sql.addSql("     and");
                sql.addSql("       WML_MAILDATA.WMD_MAILNUM = WML_LABEL_RELATION.WMD_MAILNUM");
                sql.addSql("   )");
                sql.addIntValue(filterData.getLabelSid());
                sql.addIntValue(wacSid);
                sql.addIntValue(GSConstWebmail.DIR_TYPE_DUST);
                sql.addIntValue(filterData.getLabelSid());

                sql = __setFilteringCondition(sql, filterData, conditionList, null);

                log__.info(sql.toLogString());
                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                JDBCUtil.closePreparedStatement(pstmt);
                pstmt = null;
            }

            //既読にする or フォルダへ移動
            if (filterData.isReaded() || filterData.getMove() != GSConstWebmail.FILTER_MOVE_NOSET) {
                WmlBiz wmlBiz = new WmlBiz();
                long dustWdrSid = wmlBiz.getDirectorySid(getCon(), wacSid,
                        GSConstWebmail.DIR_TYPE_DUST);

                //更新対象のメッセージ番号を取得する
                sql = new SqlBuffer();
                sql.addSql(" select WMD_MAILNUM from WML_MAILDATA");
                sql.addSql(" where WAC_SID = ?");
                sql.addIntValue(wacSid);

                //「ゴミ箱」フォルダ内のメールは除外する
                sql.addSql(" and WDR_SID <> ?");
                sql.addLongValue(dustWdrSid);

                sql = __setFilteringCondition(sql, filterData, conditionList, null);

                log__.info(sql.toLogString());
                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();

                List<Long> updateMailNumList = new ArrayList<Long>();
                while (rs.next()) {
                    updateMailNumList.add(rs.getLong("WMD_MAILNUM"));
                }

                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);
                //100件づつ更新する
                for (int idx = 0; idx < updateMailNumList.size(); idx += 100) {
                    sql = new SqlBuffer();
                    sql.addSql(" update");
                    sql.addSql("   WML_MAILDATA");
                    sql.addSql(" set");

                    if (filterData.isReaded()) {
                        sql.addSql("   WMD_READED = ?");
                        sql.addIntValue(GSConstWebmail.READEDFLG_READED);
                    }
                    if (filterData.getMove() != GSConstWebmail.FILTER_MOVE_NOSET) {
                        if (filterData.isReaded()) {
                            sql.addSql("   ,");
                        }
                        sql.addSql("   WDR_SID = ?");
                        sql.addLongValue(moveToWdrSid);
                    }

                    sql.addSql(" where");
                    sql.addSql("   WMD_MAILNUM in (");
                    for (int mailIdx = idx;
                            mailIdx < updateMailNumList.size() && mailIdx < idx + 100; mailIdx++) {
                        if (mailIdx > idx) {
                            sql.addSql("     ,?");
                        } else {
                            sql.addSql("     ?");
                        }
                        sql.addLongValue(updateMailNumList.get(mailIdx));
                    }
                    sql.addSql("   )");

                    log__.info(sql.toLogString());

                    pstmt = getCon().prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <br>[機  能] ログアウト時のメール情報削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param delDirTypeList 削除対象ディレクトリ種別一覧
     * @param sessionUserSid セッションユーザSID
     * @return メール情報削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMailDataForLogout(int wacSid, List<Integer> delDirTypeList,
                                        int sessionUserSid)
    throws SQLException {

        //削除対象のディレクトリSIDを取得する
        List<Long> wdrSidList = getWdrSidList(wacSid, delDirTypeList);
        if (wdrSidList == null || wdrSidList.isEmpty()) {
            return 0;
        }

        //削除対象のメール情報が存在するかを確認する
        if (getMailCount(wdrSidList).compareTo(BigInteger.ZERO) <= 0) {
            return 0;
        }

        __deleteTableData("WML_SENDADDRESS", wdrSidList);
        __deleteTableData("WML_LABEL_RELATION", wdrSidList);
        updateTempFileDelete(wdrSidList, sessionUserSid);
        __deleteTableData("WML_HEADER_DATA", wdrSidList);
        __deleteTableData("WML_MAIL_BODY", wdrSidList);
        return deleteMailData(wdrSidList);
    }

    /**
     * <br>[機  能] メール添付ファイルの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSidList 削除対象ディレクトリ一覧
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateTempFileDelete(List<Long> wdrSidList, int userSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   WML_TEMPFILE ");
            sql.addSql(" set ");
            sql.addSql("   WTF_EUID = ?, ");
            sql.addSql("   WTF_EDATE = ?, ");
            sql.addSql("   WTF_JKBN = ? ");
            sql.addIntValue(userSid);
            sql.addDateValue(new UDate());
            sql.addIntValue(GSConstWebmail.WMD_STATUS_DUST);

            sql = __setUpdateWhereSql(sql, "WML_TEMPFILE", wdrSidList);

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
     * <br>[機  能] メール情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSidList 削除対象ディレクトリ一覧
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMailData(List<Long> wdrSidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID in (");
            sql.addSql("     ?");
            sql.addLongValue(wdrSidList.get(0).longValue());
            for (int i = 1; i < wdrSidList.size(); i++) {
                sql.addSql("   ,?");
                sql.addLongValue(wdrSidList.get(i).longValue());
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] メール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return メール情報
     * @throws SQLException SQL実行時例外
     */
    public MailData getMailData(long mailNum) throws SQLException {
        List<Long> mailNumList = new ArrayList<Long>();
        mailNumList.add(mailNum);
        List<MailData> mailDataList = getMailData(mailNumList);
        if (mailDataList == null || mailDataList.isEmpty()) {
            return null;
        }

        return mailDataList.get(0);
    }

    /**
     * <br>[機  能] メール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return メール情報
     * @throws SQLException SQL実行時例外
     */
    public List<MailData> getMailData(List<Long> mailNumList) throws SQLException {

        List<MailData> mailDataList = new ArrayList<MailData>();
        if (mailNumList == null || mailNumList.isEmpty()) {
            return mailDataList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM as WMD_MAILNUM,");
            sql.addSql("   WML_MAILDATA.WMD_SDATE as WMD_SDATE,");
            sql.addSql("   WML_MAILDATA.WMD_TITLE as WMD_TITLE,");
            sql.addSql("   WML_MAILDATA.WMD_FROM as WMD_FROM,");
            sql.addSql("   WML_MAILDATA.WMD_TEMPFLG as WMD_TEMPFLG,");
            sql.addSql("   WML_MAIL_BODY.WMB_BODY as WMB_BODY");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_MAIL_BODY");
            sql.addSql(" where ");
            if (mailNumList.size() == 1) {
                sql.addSql("   WML_MAILDATA.WMD_MAILNUM = ?");
                sql.addLongValue(mailNumList.get(0));
            } else {
                sql.addSql("   WML_MAILDATA.WMD_MAILNUM in (");
                for (int idx = 0; idx < mailNumList.size() - 1; idx++) {
                    sql.addSql("     " + mailNumList.get(idx) + ",");
                }
                sql.addSql("     " + mailNumList.get(mailNumList.size() - 1));
                sql.addSql("   )");
            }
            sql.addSql(" and ");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MailData mailData = new MailData();

                mailData.setMailNum(rs.getLong("WMD_MAILNUM"));
                mailData.setSdate(UDate.getInstanceTimestamp(rs.getTimestamp("WMD_SDATE")));
                mailData.setTitle(rs.getString("WMD_TITLE"));
                mailData.setFrom(rs.getString("WMD_FROM"));
                mailData.setTempFlg(rs.getInt("WMD_TEMPFLG") == GSConstWebmail.TEMPFLG_EXIST);
                mailData.setBody(rs.getString("WMB_BODY"));

                if (mailData.isTempFlg()) {
                    mailData.setTempFileList(getTempFileList(mailData.getMailNum()));
                }

                mailDataList.add(mailData);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailDataList;
    }

    /**
     * <br>[機  能] 指定したメールの添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return 添付ファイル情報
     * @throws SQLException SQL実行時例外
     */
    public List<MailTempFileModel> getTempFileList(long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<MailTempFileModel> tempFileList = new ArrayList<MailTempFileModel>();
        CommonBiz cmnbiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addSql(" and");
            sql.addSql("   WTF_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   WTF_SID");
            sql.addLongValue(mailNum);
            sql.addIntValue(GSConstWebmail.TEMPFILE_STATUS_NORMAL);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MailTempFileModel tempFile = new MailTempFileModel();
                tempFile.setBinSid(rs.getLong("WTF_SID"));
                tempFile.setFileName(rs.getString("WTF_FILE_NAME"));
                tempFile.setFilePath(rs.getString("WTF_FILE_PATH"));
                tempFile.setFileSize(rs.getLong("WTF_FILE_SIZE"));
                long size = rs.getInt("WTF_FILE_SIZE");
                String strSize = cmnbiz.getByteSizeString(size);
                tempFile.setFileSizeDsp(strSize);
                tempFile.setHtmlMail(
                        rs.getInt("WTF_HTMLMAIL") == GSConstWebmail.TEMPFILE_HTMLMAIL_HTML);
                tempFile.setCharset(rs.getString("WTF_CHARSET"));

                tempFileList.add(tempFile);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return tempFileList;
    }

    /**
     * <br>[機  能] メイン画面に表示するメッセージの情報を取得する
     * <br>[解  説] アカウントの受信箱に未読メールがある場合、アカウント毎の未読件数を返す
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param wacSidList アカウントSID一覧
     * @return メイン画面に表示するメッセージの情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlMainInfoMessageModel> getMainInfoMessageList(int userSid, int[] wacSidList)
    throws SQLException {

        List<WmlMainInfoMessageModel> infoMsgList = new ArrayList<WmlMainInfoMessageModel>();
        if (wacSidList == null || wacSidList.length == 0) {
            return infoMsgList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME,");
            sql.addSql("   WML_ACCOUNT_DISK.WDS_SIZE as WDS_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_DISK,");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select WAC_SID, WAS_SORT from WML_ACCOUNT_SORT");
            sql.addSql("      where USR_SID = ?");
            sql.addSql("     ) ACCOUNT_SORT");
            sql.addSql("   on");
            sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_SORT.WAC_SID");
            sql.addIntValue(userSid);

            sql.addSql(" where");

            if (wacSidList.length == 1) {
                sql.addSql("   WML_ACCOUNT.WAC_SID = ?");
                sql.addIntValue(wacSidList[0]);
            } else {
                sql.addSql("   WML_ACCOUNT.WAC_SID in (");
                for (int wacIdx = 0; wacIdx < wacSidList.length; wacIdx++) {
                    if (wacIdx < wacSidList.length - 1) {
                        sql.addSql("     ?,");
                    } else {
                        sql.addSql("     ?");
                    }
                    sql.addIntValue(wacSidList[wacIdx]);
                }
                sql.addSql("   )");
            }

            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT.WAC_SID = WML_ACCOUNT_DISK.WAC_SID");

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            long time = System.currentTimeMillis();
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int wacSid = rs.getInt("WAC_SID");
                long noReadCount = __getNoReadMailCount(wacSid);

                if (noReadCount > 0) {
                    WmlMainInfoMessageModel infoMsgData = new WmlMainInfoMessageModel();
                    infoMsgData.setWacSid(wacSid);
                    infoMsgData.setWacName(rs.getString("WAC_NAME"));
                    infoMsgData.setNoReadCount(noReadCount);
                    infoMsgData.setWdsSize(rs.getLong("WDS_SIZE"));
                    infoMsgList.add(infoMsgData);
                }
            }

            log__.info("メイン未読件数取得時間 " + (System.currentTimeMillis() - time));
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return infoMsgList;
    }

    /**
     * <br>[機  能] 指定したアカウントの受信箱内の未読メール件数を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return メイン画面に表示するメッセージの情報
     * @throws SQLException SQL実行時例外
     */
    private long __getNoReadMailCount(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long noReadCount = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(1) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY,");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WML_DIRECTORY.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_DIRECTORY.WDR_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_DIRECTORY.WDR_SID = WML_MAILDATA.WDR_SID");
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WMD_READED = ?");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.DIR_TYPE_RECEIVE);
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.WMD_READED_NO);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                noReadCount = rs.getLong("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return noReadCount;
    }

    /**
     * <br>[機  能] 送信元の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuSendDel(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_SENDADDRESS", mailNumList);
    }

    /**
     * <br>[機  能] メール - ラベル の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuLabelRelationDel(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_LABEL_RELATION", mailNumList);
    }

    /**
     * <br>[機  能] メール情報_送信予約 の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuSendplanDel(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_MAIL_SENDPLAN", mailNumList);
    }

    /**
     * <br>[機  能] メール添付ファイルの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuUpdateTempFile(int usrSid, List<Long> mailNumList)
        throws SQLException {

        int count = 0;
        if (mailNumList == null || mailNumList.isEmpty()) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = getCon();
        

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   WML_TEMPFILE ");
            sql.addSql(" set ");
            sql.addSql("   WTF_EUID = ?, ");
            sql.addSql("   WTF_EDATE = ?, ");
            sql.addSql("   WTF_JKBN = ? ");
            sql.addIntValue(usrSid);
            sql.addDateValue(new UDate());
            sql.addIntValue(GSConstWebmail.WMD_STATUS_DUST);

            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");

            sql.addSql("     ?");
            sql.addLongValue(mailNumList.get(0));

            for (int numIdx = 1; numIdx < mailNumList.size(); numIdx++) {
                sql.addSql("     ,?");
                sql.addLongValue(mailNumList.get(numIdx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] メール-ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuDelLabelRelation(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_LABEL_RELATION", mailNumList);
    }

    /**
     * <br>[機  能] メールヘッダ情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuDelHeader(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_HEADER_DATA", mailNumList);
    }

    /**
     * <br>[機  能] メール本文の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuDelBody(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_MAIL_BODY", mailNumList);
    }

    /**
     * <br>[機  能] メール情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int manuDelMailData(List<Long> mailNumList)
        throws SQLException {

        return __deleteTableDataForMailNum("WML_MAILDATA", mailNumList);
    }

    /**
     * <br>[機  能] 送受信ログの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param day 日
     * @throws SQLException SQL実行例外
     */
    public void deleteMailLog(int year, int month, int day) throws SQLException {

        UDate delUd = new UDate();
        delUd.addYear(year * -1);
        delUd.addMonth(month * -1);
        delUd.addDay(day * -1);
        delUd.setHour(GSConstMain.DAY_END_HOUR);
        delUd.setMinute(GSConstMain.DAY_END_MINUTES);
        delUd.setSecond(GSConstMain.DAY_END_SECOND);
        delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

        deleteMailLog(null, delUd);

    }

    /**
     * <br>[機  能] 送受信ログの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @throws SQLException SQL実行例外
     */
    public void deleteMailLog(UDate frDate, UDate toDate) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");

            if (frDate != null || toDate != null) {
                sql.addSql(" where ");
                if (frDate != null) {
                    sql.addSql("   WLG_DATE >= ? ");
                    sql.addDateValue(frDate);
                }
                if (toDate != null) {
                    if (frDate != null) {
                        sql.addSql(" and");
                    }
                    sql.addSql("   WLG_DATE <= ? ");
                    sql.addDateValue(toDate);
                }
            }


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
     * <br>[機  能] 削除対象の送受信ログを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param day 日
     * @throws SQLException SQL実行例外
     */
    public List<Long> getMailLogToDelete(int year, int month, int day) throws SQLException {

        //削除基準日を取得
        UDate delUd = new UDate();
        delUd.addYear(year * -1);
        delUd.addMonth(month * -1);
        delUd.addDay(day * -1);
        delUd.setHour(GSConstMain.DAY_END_HOUR);
        delUd.setMinute(GSConstMain.DAY_END_MINUTES);
        delUd.setSecond(GSConstMain.DAY_END_SECOND);
        delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

        return getMailLogToDelete(null, delUd);
    }

    /**
     * <br>[機  能] 削除対象の送受信ログを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param day 日
     * @throws SQLException SQL実行例外
     */
    public List<Long> getMailLogToDelete(UDate frDate, UDate toDate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<Long> ret = new ArrayList<Long>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");

            if (frDate != null || toDate != null) {
                sql.addSql(" where ");
                if (frDate != null) {
                    sql.addSql("   WLG_DATE >= ? ");
                    sql.addDateValue(frDate);
                }
                if (toDate != null) {
                    if (frDate != null) {
                        sql.addSql(" and");
                    }
                    sql.addSql("   WLG_DATE <= ? ");
                    sql.addDateValue(toDate);
                }
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("WMD_MAILNUM"));
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
     * <br>[機  能] 送受信ログ_送信先の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param day 日
     * @throws SQLException SQL実行例外
     */
    public void deleteMailLogSend(int year, int month, int day)
        throws SQLException {

        UDate delUd = new UDate();
        delUd.addYear(year * -1);
        delUd.addMonth(month * -1);
        delUd.addDay(day * -1);
        delUd.setHour(GSConstMain.DAY_END_HOUR);
        delUd.setMinute(GSConstMain.DAY_END_MINUTES);
        delUd.setSecond(GSConstMain.DAY_END_SECOND);
        delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

        deleteMailLogSend(null, delUd);
    }

    /**
     * <br>[機  能] 送受信ログ_送信先の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 開始日時
     * @param toDate 終了日付
     * @throws SQLException SQL実行例外
     */
    public void deleteMailLogSend(UDate frDate, UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");

            if (frDate != null || toDate != null) {
                sql.addSql(" where");
                sql.addSql("   exists (");
                sql.addSql("     select 1 from WML_MAIL_LOG");
                sql.addSql("     where WML_MAIL_LOG.WMD_MAILNUM = WML_MAIL_LOG_SEND.WMD_MAILNUM");
                if (frDate != null) {
                    sql.addSql("     and WML_MAIL_LOG.WLG_DATE >= ?");
                    sql.addDateValue(frDate);
                }
                if (toDate != null) {
                    sql.addSql("     and WML_MAIL_LOG.WLG_DATE <= ?");
                    sql.addDateValue(toDate);
                }
                sql.addSql("   )");
            }

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
     * <br>[機  能] 指定したテーブルの削除SQLを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName テーブル名
     * @param mailNumList 削除対象メールのメッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    private int __deleteTableDataForMailNum(String tableName, List<Long> mailNumList)
    throws SQLException {
        int count = 0;
        if (mailNumList == null || mailNumList.isEmpty()) {
            return count;
        }

        PreparedStatement pstmt = null;
        Connection con = getCon();
        

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   " + tableName);
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");

            sql.addSql("     ?");
            sql.addLongValue(mailNumList.get(0));

            for (int numIdx = 1; numIdx < mailNumList.size(); numIdx++) {
                sql.addSql("     ,?");
                sql.addLongValue(mailNumList.get(numIdx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count += pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 更新 or 削除対象のメッセージ番号一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param delMdl 手動削除設定モデル
     * @return メッセージ番号一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getUpdateMailList(WmlMailDeleteModel delMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Long> mailNumList = new ArrayList<Long>();
        SqlBuffer sql = null;

        try {

            WmlDirectoryDao dirDao = new WmlDirectoryDao(getCon());
            List<Long> dirSidList = dirDao.getDirSidList(delMdl.getWacSid(),
                                                        delMdl.getManuDelDirList());
            dirDao = null;
            if (dirSidList == null || dirSidList.isEmpty()) {
                return mailNumList;
            }

            UDate delUd = null;
            if (delMdl.isUseDate()) {
                delUd = new UDate();
                delUd.addYear((delMdl.getManuDelYear() * -1));
                delUd.addMonth((delMdl.getManuDelMonth() * -1));
                delUd.addDay((delMdl.getManuDelDay() * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);
            }

            sql = new SqlBuffer();
            sql.addSql(" select WMD_MAILNUM from WML_MAILDATA");
            sql.addSql(" where WDR_SID = ?");
            sql.addSql(" and WAC_SID = ?");
            if (delMdl.isUseDate()) {
                sql.addSql(" and WMD_SDATE <= ? ");
            }

            pstmt = getCon().prepareStatement(sql.toSqlString());
            for (long wdrSid : dirSidList) {

                sql.clearValue();
                sql.addLongValue(wdrSid);
                sql.addIntValue(delMdl.getWacSid());
                if (delMdl.isUseDate()) {
                    sql.addDateValue(delUd);
                }
                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    mailNumList.add(Long.valueOf((rs.getLong("WMD_MAILNUM"))));
                }
            }
        } catch (SQLException e) {
            if (sql != null) {
                log__.error("Error SQL: " + sql.toLogString());
            }
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return mailNumList;
    }

    /**
     * <br>[機  能] SqlBufferに検索SQLのメッセージ番号部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param mailNum メッセージ番号
     * @return SqlBuffer
     */
    private SqlBuffer __setMailNumSql(SqlBuffer sql, List<Long> mailNum) {
        if (mailNum == null && mailNum.isEmpty()) {
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM <> -1");

        } else {
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM in (");
            boolean first = true;
            for (Long messageNum : mailNum) {
                if (!first) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                    first = false;
                }
                sql.addLongValue(messageNum);
            }
            sql.addSql("   )");
        }
        return sql;
    }

    /**
     * <br>[機  能] 検索SQL パターン文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param conditionMdl フィルター条件情報
     * @return パターン文字列
     */
    private String __getPatternString(MailFilterConditionModel conditionMdl) {
        StringBuilder sb = null;
        if (conditionMdl.getExpression() == GSConstWebmail.FILTER_TYPE_EXCLUDE) {
            sb = new StringBuilder(" not like");
        } else {
            sb = new StringBuilder(" like");
        }

        sb.append(" '%");
        sb.append(JDBCUtil.escapeForLikeSearch(conditionMdl.getText()));
        sb.append("%' ESCAPE '");
        sb.append(JDBCUtil.def_esc);
        sb.append("'");

        return sb.toString();
    }

    /**
     * <br>[機  能] 指定したテーブルの削除SQLを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName テーブル名
     * @param wdrSidList ディレクトリSID一覧
     * @throws SQLException SQL実行時例外
     */
    private void __deleteTableData(String tableName, List<Long> wdrSidList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   " + tableName);
            sql = __setUpdateWhereSql(sql, tableName, wdrSidList);

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
     * <br>[機  能] 更新 or 削除SQLのwhere SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param tableName テーブル名
     * @param wdrSidList ディレクトリSID一覧
     * @return SqlBuffer
     */
    private SqlBuffer __setUpdateWhereSql(SqlBuffer sql, String tableName, List<Long> wdrSidList) {
        sql.addSql(" where ");
        sql.addSql("   exists ( ");
        sql.addSql("     select 1 from");
        sql.addSql("       WML_MAILDATA ");
        sql.addSql("     where ");
        sql.addSql("       WML_MAILDATA.WDR_SID in (");
        sql.addSql("         ?");
        sql.addLongValue(wdrSidList.get(0).longValue());
        for (int i = 1; i < wdrSidList.size(); i++) {
            sql.addSql("         ,?");
            sql.addLongValue(wdrSidList.get(i).longValue());
        }
        sql.addSql("       )");

        sql.addSql("     and ");
        sql.addSql("       WML_MAILDATA.WMD_MAILNUM = " + tableName + ".WMD_MAILNUM");
        sql.addSql("   ) ");

        return sql;
    }

    /**
     * <br>[機  能] 指定したアカウントのディスク容量集計値を取得する
     * <br>[解  説] アカウントディスク使用量(WML_ACCOUNT_DISK)ではなく
     * <br>         メール情報(WML_MAILDATA)のディスク容量を集計した値を返す
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return ディスク容量
     * @throws SQLException SQL実行時例外
     */
    public long getSumAccountDiskSize(int wacSid) throws SQLException {

        //ディレクトリSIDを取得する
        WmlDirectoryDao dirDao = new WmlDirectoryDao(getCon());
        List<Long> wdrSidList = dirDao.getDirectorySidList(wacSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long diskSize = 0;

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select sum(WMD_SIZE) as DISK_SIZE from WML_MAILDATA");
            sql.addSql(" where WDR_SID = ?");
            sql.addSql(" and WAC_SID = ?");
            pstmt = getCon().prepareStatement(sql.toSqlString());

            long time = System.currentTimeMillis();
            for (long wdrSid : wdrSidList) {
                sql.addLongValue(wdrSid);
                sql.addIntValue(wacSid);
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    diskSize += rs.getLong("DISK_SIZE");
                }
                JDBCUtil.closeResultSet(rs);
                sql.clearValue();
            }

            log__.info("ディスク容量集計値取得:[wacSid " + wacSid + "]" + (System.currentTimeMillis() - time));
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return diskSize;
    }

    /**
     * <br>[機  能] SqlBufferにフィルタリング対象検索条件SQLを設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param filterData フィルター情報
     * @param conditionList フィルタリング条件
     * @param mailNum メッセージ番号
     * @return SqlBuffer
     */
    private SqlBuffer __setFilteringCondition(SqlBuffer sql, MailFilterModel filterData,
                                           List<MailFilterConditionModel> conditionList,
                                           List<Long> mailNum) {

        String conditionStr = " and";
        if (filterData.getCondition() == GSConstWebmail.FILTER_CONDITION_OR) {
            conditionStr = " or";
        }

        boolean existCondition = conditionList != null && !conditionList.isEmpty();

        //添付ファイル
        if (filterData.isTempFile()) {
            sql.addSql(" and");
            if (existCondition) {
                sql.addSql("   (");
            }
            sql.addSql("   WML_MAILDATA.WMD_TEMPFLG = ?");
            sql.addIntValue(GSConstWebmail.TEMPFLG_EXIST);
        }

        //フィルター条件
        if (existCondition) {
            if (filterData.isTempFile()) {
                sql.addSql(conditionStr);
            } else {
                sql.addSql(" and");
            }

            sql.addSql("   (");

            boolean first = true;
            List<MailFilterConditionModel> bodyConditionList
                = new ArrayList<MailFilterConditionModel>();
            for (MailFilterConditionModel conditionMdl : conditionList) {

                if (conditionMdl.getType() == GSConstWebmail.FILTER_TYPE_MAIN) {
                    bodyConditionList.add(conditionMdl);
                    continue;
                }

                if (!first) {
                    sql.addSql("  " + conditionStr);
                }

                switch (conditionMdl.getType()) {
                    case GSConstWebmail.FILTER_TYPE_TITLE :
                        sql.addSql("     WML_MAILDATA.WMD_TITLE"
                                        + __getPatternString(conditionMdl));
                        break;
                    case GSConstWebmail.FILTER_TYPE_ADDRESS :
                    case GSConstWebmail.FILTER_TYPE_CC :

                        if (conditionMdl.getExpression() == GSConstWebmail.FILTER_TYPE_EXCLUDE) {
                            sql.addSql("     not exists (");
                        } else {
                            sql.addSql("     exists (");
                        }
                        sql.addSql("       select 1 from WML_SENDADDRESS SADDR");
                        sql.addSql("       where");
                        sql.addSql("         SADDR.WSA_TYPE = ?");
                        if (conditionMdl.getType() == GSConstWebmail.FILTER_TYPE_CC) {
                            sql.addIntValue(GSConstWebmail.WSA_TYPE_CC);
                        } else {
                            sql.addIntValue(GSConstWebmail.WSA_TYPE_TO);
                        }

                        if (mailNum != null && !mailNum.isEmpty()) {
                            sql.addSql("       and");
                            sql.addSql("         SADDR.WMD_MAILNUM in (");
                            boolean exist = false;
                            for (Long messageNum : mailNum) {
                                if (exist) {
                                    sql.addSql("         ,?");
                                } else {
                                    sql.addSql("         ?");
                                    exist = true;
                                }
                                sql.addLongValue(messageNum);
                            }
                            sql.addSql("         )");
                        }
                        sql.addSql("       and");
                        sql.addSql("         WML_MAILDATA.WMD_MAILNUM = SADDR.WMD_MAILNUM");
                        sql.addSql("       and");
                        sql.addSql("         SADDR.WSA_ADDRESS like '%"
                                        + JDBCUtil.escapeForLikeSearch(conditionMdl.getText())
                                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                        sql.addSql("     )");
                        break;
                    case GSConstWebmail.FILTER_TYPE_SEND :
                        sql.addSql("     WML_MAILDATA.WMD_FROM"
                                + __getPatternString(conditionMdl));
                        break;
                    default:
                        break;
                }

                first = false;
            }

            //本文の条件を追加する
            if (!bodyConditionList.isEmpty()) {
                if (!first) {
                    sql.addSql("  " + conditionStr);
                }

                sql.addSql("     (");
                __setBodyFilteringCondition(sql, filterData, bodyConditionList, conditionStr);
                sql.addSql("     )");

                bodyConditionList = null;
            }

            sql.addSql("   )");

            if (filterData.isTempFile()) {
                sql.addSql("   )");
            }
        }
        conditionStr = null;
        return sql;
    }

    /**
     * <br>[機  能] SqlBufferにフィルタリング対象検索条件SQL(本文)を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param filterData フィルター情報
     * @param bodyConditionList 本文に対するフィルタリング条件
     * @param conditionStr 条件文字列
     * @return SqlBuffer
     */
    private SqlBuffer __setBodyFilteringCondition(SqlBuffer sql, MailFilterModel filterData,
                                           List<MailFilterConditionModel> bodyConditionList,
                                           String conditionStr) {

        //H2 全文検索の場合のみ
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() == GSConst.DBTYPE_H2DB) {
            sql.addSql("     exists (");

            boolean first = true;
            StringBuilder value = new StringBuilder("       select 1 from FTL_SEARCH_DATA(\'");
            for (MailFilterConditionModel conditionMdl : bodyConditionList) {

                if (!first) {
                    value.append(" ");
                    if (filterData.getCondition() == GSConstWebmail.FILTER_CONDITION_AND) {
                        value.append("AND ");
                    }
                }

                if (conditionMdl.getExpression() == GSConstWebmail.FILTER_TYPE_EXCLUDE) {
                    value.append("-");
                }
                value.append(JDBCUtil.encFullStringH2Lucene(conditionMdl.getText()));
                first = false;
            }

            value.append("\', 0, 0) FTL , WML_MAIL_BODY");
            sql.addSql(value.toString());
            value = null;

            sql.addSql("       where");
            sql.addSql("         FTL.TABLE = \'WML_MAIL_BODY\'");
            sql.addSql("       and");
            sql.addSql("         WML_MAIL_BODY.WMD_MAILNUM = FTL.KEYS[0]");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WDR_SID <> -1");
            sql.addSql("     )");

        } else {
            sql.addSql("     exists (");
            sql.addSql("       select 1 from WML_MAIL_BODY");
            sql.addSql("       where");
            sql.addSql("         WML_MAILDATA.WMD_MAILNUM = "
                        + "WML_MAIL_BODY.WMD_MAILNUM");

            sql.addSql("       and");
            sql.addSql("         (");

            boolean first = true;
            for (MailFilterConditionModel conditionMdl : bodyConditionList) {

                if (!first) {
                    sql.addSql("  " + conditionStr);
                }
                sql.addSql("         WML_MAIL_BODY.WMB_BODY"
                        + __getPatternString(conditionMdl));
                first = false;
            }

            sql.addSql("         )");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WDR_SID <> -1");
            sql.addSql("     )");
        }
        return sql;
    }

    /**
     * <br>[機  能] 指定したメール添付ファイルのファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtfSidList バイナリーSID
     * @return ファイルパス
     * @throws SQLException SQL実行時例外
     */
    public List<String> getTempFilePathList(List<Long> wtfSidList) throws SQLException {

        List<String> filePathList = new ArrayList<String>();
        if (wtfSidList == null || wtfSidList.isEmpty()) {
            return filePathList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTF_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where");
            sql.addSql("   WTF_SID in (");
            sql.addSql("     ?");
            sql.addLongValue(wtfSidList.get(0).longValue());

            for (int i = 1; i < wtfSidList.size(); i++) {
                sql.addSql("     ,?");
                sql.addLongValue(wtfSidList.get(i).longValue());
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                filePathList.add(rs.getString("WTF_FILE_PATH"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return filePathList;
    }

    /**
     * <br>[機  能] 指定したユーザが使用可能な送信先リストの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 送信先リストの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlDestlistModel> getDestList(int userSid) throws SQLException {

        List<WmlDestlistModel> destList = new ArrayList<WmlDestlistModel>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDL_NAME,");
            sql.addSql("   WDL_BIKO,");
            sql.addSql("   WDL_AUID,");
            sql.addSql("   WDL_ADATE,");
            sql.addSql("   WDL_EUID,");
            sql.addSql("   WDL_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID in (");
            sql.addSql("      select WDL_SID from WML_DESTLIST_ACCESS_CONF");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      or");
            sql.addSql("        (");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_USR_SID in (");
            sql.addSql("           select GRP_SID from CMN_BELONGM");
            sql.addSql("            where CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addSql(" order by");
            sql.addSql("   WDL_NAME");

            sql.addIntValue(GSConstWebmail.WLA_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WLA_KBN_GROUP);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WmlDestlistModel destlistData = new WmlDestlistModel();
                destlistData.setWdlSid(rs.getInt("WDL_SID"));
                destlistData.setWdlName(rs.getString("WDL_NAME"));
                destlistData.setWdlBiko(rs.getString("WDL_BIKO"));
                destList.add(destlistData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return destList;
    }
}
