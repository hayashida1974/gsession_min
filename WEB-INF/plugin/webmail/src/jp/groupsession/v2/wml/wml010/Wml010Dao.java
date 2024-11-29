package jp.groupsession.v2.wml.wml010;

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
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.wml010.model.Wml010AccountModel;
import jp.groupsession.v2.wml.wml010.model.Wml010AddressModel;
import jp.groupsession.v2.wml.wml010.model.Wml010SendAddrModel;

/**
 * <br>[機  能] WEBメール メール一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml010Dao.class);

    /**
     * <p>Default Constructor
     */
    public Wml010Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml010Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したアカウント内メールの最小/最大送信日を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 日付
     * @throws SQLException SQL実行時例外
     */
    public UDate[] getMailDate(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UDate[] mailDate = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_MAILDATA.WAC_SID,");
            sql.addSql("   min(WML_MAILDATA.WMD_SDATE) as FRDATE,");
            sql.addSql("   max(WML_MAILDATA.WMD_SDATE) as TODATE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WML_MAILDATA.WAC_SID = ?");
            sql.addSql(" group by");
            sql.addSql("   WML_MAILDATA.WAC_SID");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                mailDate = new UDate[] {
                            UDate.getInstanceTimestamp(rs.getTimestamp("FRDATE")),
                            UDate.getInstanceTimestamp(rs.getTimestamp("TODATE"))};
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailDate;
    }

    /**
     * <br>[機  能] 指定したアカウントを除くアカウント情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid 除外するアカウントのアカウントSID
     * @param userSid ユーザSID
     * @return アカウント情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Wml010AccountModel> getNotSelectAccountList(int wacSid, int userSid)
    throws SQLException {
        //アカウント代理人が許可されているかを判定する
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(getCon());

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Wml010AccountModel> accountList = new ArrayList<Wml010AccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select WAC_SID, WAS_SORT");
            sql.addSql("       from WML_ACCOUNT_SORT");
            sql.addSql("       where USR_SID = ?");
            sql.addSql("     ) ACCOUNT_SORT");
            sql.addSql("   on");
            sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_SORT.WAC_SID");
            sql.addIntValue(userSid);

            WmlDao wmlDao = new WmlDao();
            wmlDao.setAccountSearchSql(sql, userSid, proxyUserFlg);

            if (wacSid > 0) {
                sql.addSql(" and");
                sql.addSql("   WML_ACCOUNT.WAC_SID <> ?");
                sql.addIntValue(wacSid);
            }
            sql.addSql(" group by");
            sql.addSql("   WML_ACCOUNT.WAC_SID, WML_ACCOUNT.WAC_NAME, ACCOUNT_SORT.WAS_SORT");
            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT, WML_ACCOUNT.WAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            WmlMaildataDao mailDao = new WmlMaildataDao(con);
            long notReadCount = 0;
            while (rs.next()) {
                Wml010AccountModel accountData = new Wml010AccountModel();

                accountData.setWacSid(rs.getInt("WAC_SID"));
                accountData.setWacName(rs.getString("WAC_NAME"));
                notReadCount = mailDao.getNotReadCount(accountData.getWacSid());
                accountData.setNotReadCount(notReadCount);
                accountList.add(accountData);
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
     * <br>[機  能] 指定したメールの送信先メールアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNum メッセージ番号
     * @return 送信先メールアドレス
     * @throws SQLException SQL実行時例外
     */
    public Wml010SendAddrModel getSendAddress(int wacSid, long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Wml010SendAddrModel addrModel = new Wml010SendAddrModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where");
            if (wacSid > 0) {
                sql.addSql("   WAC_SID  = ?");
                sql.addSql(" and");
                sql.addIntValue(wacSid);
            }
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addSql(" order by");
            sql.addSql("   WSA_NUM");
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int type = rs.getInt("WSA_TYPE");
                String address = rs.getString("WSA_ADDRESS");
                addrModel.addRenban(rs.getInt("WSA_NUM"));
                if (type == GSConstWebmail.WSA_TYPE_TO) {
                    addrModel.addToAddress(address);
                } else if (type == GSConstWebmail.WSA_TYPE_CC) {
                    addrModel.addCcAddress(address);
                } else if (type == GSConstWebmail.WSA_TYPE_BCC) {
                    addrModel.addBccAddress(address);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addrModel;
    }

    /**
     * <br>[機  能] 指定したディレクトリ内の全ての「未読/既読」メールのメッセージ番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSid ディレクトリSID
     * @param readType メール未読/既読
     * @return メッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getMailNum(long wdrSid, int readType) throws SQLException {
        List<Long> mailNumList = new ArrayList<Long>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WMD_MAILNUM from WML_MAILDATA");
            sql.addSql(" where WDR_SID = ?");
            sql.addLongValue(wdrSid);

            sql.addSql(" and WMD_READED = ?");
            if (readType == Wml010Const.MAIL_READTYPE_NOREAD) {
                sql.addIntValue(GSConstWebmail.WMD_READED_YES);
            } else {
                sql.addIntValue(GSConstWebmail.WMD_READED_NO);
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                mailNumList.add(rs.getLong("WMD_MAILNUM"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailNumList;
    }

    /**
     * <br>[機  能] ユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @return ユーザ情報
     * @throws SQLException SQL実行時例外
     */
    public List<Wml010AddressModel> getShainList(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Wml010AddressModel> addressList = new ArrayList<Wml010AddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (");
            sql.addSql("        select POS_SORT from CMN_POSITION");
            sql.addSql("        where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("      )");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   exists (");
            sql.addSql("     select 1 from CMN_BELONGM");
            sql.addSql("     where CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
            sql.addSql("     and CMN_BELONGM.GRP_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("        length(coalesce(CMN_USRM_INF.USI_MAIL1, '')) > 0");
            sql.addSql("      and");
            sql.addSql("        CMN_USRM_INF.USI_MAIL1_KF = ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("        length(coalesce(CMN_USRM_INF.USI_MAIL2, '')) > 0");
            sql.addSql("      and");
            sql.addSql("        CMN_USRM_INF.USI_MAIL2_KF = ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("        length(coalesce(CMN_USRM_INF.USI_MAIL3, '')) > 0");
            sql.addSql("      and");
            sql.addSql("        CMN_USRM_INF.USI_MAIL3_KF = ?");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(grpSid);
            sql.addIntValue(GSConstUser.INDIVIDUAL_INFO_OPEN);
            sql.addIntValue(GSConstUser.INDIVIDUAL_INFO_OPEN);
            sql.addIntValue(GSConstUser.INDIVIDUAL_INFO_OPEN);

            //並び順を設定する
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            sql.addSql(" order by");
            if (sortMdl != null) {
                __setShainOrder(sql, sortMdl.getCscUserSkey1(), sortMdl.getCscUserOrder1());

                if (sortMdl.getCscUserSkey2() != GSConst.USERCMB_SKEY_NOSET) {
                    sql.addSql("   ,");
                    __setShainOrder(sql, sortMdl.getCscUserSkey2(), sortMdl.getCscUserOrder2());
                }
            } else {
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml010AddressModel addressData = new Wml010AddressModel();
                addressData.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                addressData.setUserSid(rs.getInt("USR_SID"));
                addressData.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                if (rs.getInt("USI_MAIL1_KF") == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                    addressData.setMail1(rs.getString("USI_MAIL1"));
                }
                if (rs.getInt("USI_MAIL2_KF") == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                    addressData.setMail2(rs.getString("USI_MAIL2"));
                }
                if (rs.getInt("USI_MAIL3_KF") == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                    addressData.setMail3(rs.getString("USI_MAIL3"));
                }

                addressList.add(addressData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addressList;
    }

    /**
     * <br>[機  能] アドレス帳情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param tantoFlg trueの場合は担当者に指定されているアドレス帳情報のみを表示
     * @return アドレス帳情報
     * @throws SQLException SQL実行時例外
     */
    public List<Wml010AddressModel> getAddressList(int userSid, boolean tantoFlg)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Wml010AddressModel> addressList = new ArrayList<Wml010AddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SEI,");
            sql.addSql("   ADR_MEI,");
            sql.addSql("   ADR_MAIL1,");
            sql.addSql("   ADR_MAIL2,");
            sql.addSql("   ADR_MAIL3");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where");

            AddressDao.addViewableWhereSQL(sql, userSid);

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      length(coalesce(ADR_ADDRESS.ADR_MAIL1, '')) > 0");
            sql.addSql("    or");
            sql.addSql("      length(coalesce(ADR_ADDRESS.ADR_MAIL2, '')) > 0");
            sql.addSql("    or");
            sql.addSql("      length(coalesce(ADR_ADDRESS.ADR_MAIL3, '')) > 0");
            sql.addSql("   )");

            if (tantoFlg) {
                sql.addSql(" and");
                sql.addSql("   exists (");
                sql.addSql("     select ADR_SID from ADR_PERSONCHARGE");
                sql.addSql("     where ADR_PERSONCHARGE.USR_SID = ?");
                sql.addSql("     and ADR_ADDRESS.ADR_SID = ADR_PERSONCHARGE.ADR_SID");
                sql.addSql("   )");
                sql.addIntValue(userSid);
            }

            sql.addSql(" order by");
            sql.addSql("   ADR_SEI_KN asc,");
            sql.addSql("   ADR_MEI_KN asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml010AddressModel addressData = new Wml010AddressModel();
                addressData.setUserName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
                addressData.setMail1(rs.getString("ADR_MAIL1"));
                addressData.setMail2(rs.getString("ADR_MAIL2"));
                addressData.setMail3(rs.getString("ADR_MAIL3"));

                addressList.add(addressData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addressList;
    }

    /**
     * <br>[機  能] ヘッダー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param headerType ヘッダー種別
     * @return ディレクトリ種別
     * @throws SQLException SQL実行時例外
     */
    public List<String> getHeaderValue(long mailNum, String headerType) throws SQLException {
        return getHeaderValue(mailNum, headerType, false);
    }

    /**
     * <br>[機  能] ヘッダー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param headerType ヘッダー種別
     * @param type 完全一致 false:大文字、小文字を区別しない
     * @return ディレクトリ種別
     * @throws SQLException SQL実行時例外
     */
    public List<String> getHeaderValue(long mailNum, String headerType, boolean type)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> headerList = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMH_CONTENT");
            sql.addSql(" from ");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailNum);

            sql.addSql(" and");
            if (type) {
                sql.addSql("   WMH_TYPE = ?");
                sql.addStrValue(headerType);
            } else {
                sql.addSql("   upper(WMH_TYPE) = ?");
                sql.addStrValue(headerType.toUpperCase());
            }
            sql.addSql(" order by");
            sql.addSql("   WMH_NUM");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                headerList.add(rs.getString("WMH_CONTENT"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return headerList;
    }

    /**
     * <br>[機  能] ユーザ情報のソート順を設定する
     * <br>[解  説]
     * <br>[備  考] sortKey、orderにはコンボボックスソート設定(CMN_CMB_SORT_CONF)の
     * <br>         設定を指定する
     * @param sql SqlBuffer
     * @param sortKey ソートキー
     * @param order 並び順
     * @return SqlBuffer
     */
    private SqlBuffer __setShainOrder(SqlBuffer sql, int sortKey, int order) {

        //並び順
        String orderStr = " asc";
        if (order == GSConst.ORDER_KEY_DESC) {
            orderStr = " desc";
        }

        //ソートカラム
        switch (sortKey) {
            //氏名
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN" + orderStr + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN" + orderStr);
                break;
            //社員/職員番号
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end" + orderStr);
                break;
            //役職
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("  YAKUSYOKU_EXIST" + orderStr + ",");
                sql.addSql("  YAKUSYOKU_SORT" + orderStr);
                break;
             //生年月日
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE" + orderStr);
                break;
             //ソートキー1
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1" + orderStr);
                break;
             //ソートキー2
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2" + orderStr);
                break;
            default:
                break;
        }
        return sql;
    }

    

    /**
     * <br>[機  能] 指定したSIDのアカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accSid カウントSID
     * @return アカウント名
     * @throws SQLException SQL実行時例外
     */
    public String getAccName(long accSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String accName = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_NAME");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");

            sql.addLongValue(accSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                accName = rs.getString("WAC_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return accName;
    }

    /**
     * <br>[機  能] 指定したメールのラベル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSid カウントSID
     * @return ラベル名
     * @throws SQLException SQL実行時例外
     */
    public String getLabelName(long mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String labelName = null;
        con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_LABEL.WLB_NAME as WLB_NAME");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION,");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WML_LABEL_RELATION.WMD_MAILNUM = ?");
            sql.addSql("   and");
            sql.addSql("   WML_LABEL_RELATION.WLB_SID = WML_LABEL.WLB_SID");

            sql.addLongValue(mailSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                labelName = rs.getString("WLB_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return labelName;
    }

    /**
     * <br>[機  能] 指定したメールが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existsMailData(long mailNum) throws SQLException {

        boolean result = false;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailNum);

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
}
