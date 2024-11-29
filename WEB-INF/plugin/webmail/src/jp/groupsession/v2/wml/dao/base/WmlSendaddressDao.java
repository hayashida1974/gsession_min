package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlSendaddressModel;

/**
 * <p>WML_SENDADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlSendaddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlSendaddressDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlSendaddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlSendaddressDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table WML_SENDADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table WML_SENDADDRESS (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WSA_NUM NUMBER(10,0) not null,");
            sql.addSql("   WSA_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WSA_ADDRESS varchar(768) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WSA_ADDRESS_SRH varchar(768),");
            sql.addSql("   primary key (WMD_MAILNUM,WSA_NUM)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert WML_SENDADDRESS Data Bindding JavaBean
     * @param bean WML_SENDADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlSendaddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_SENDADDRESS(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWsaNum());
            sql.addIntValue(bean.getWsaType());
            sql.addStrValue(bean.getWsaAddress());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWsaAddressSrh());
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
     * <p>Insert WML_SENDADDRESS Data Bindding JavaBean
     * @param beanList WML_SENDADDRESS DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlSendaddressModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_SENDADDRESS(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (WmlSendaddressModel bean : beanList) {
                sql.addLongValue(bean.getWmdMailnum());
                sql.addIntValue(bean.getWsaNum());
                sql.addIntValue(bean.getWsaType());
                sql.addStrValue(bean.getWsaAddress());
                sql.addIntValue(bean.getWacSid());
                sql.addStrValue(bean.getWsaAddressSrh());

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
     * <br>[機  能] 送信先アドレスの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param wacSid アカウントSID
     * @param toAddressList TOに指定されたメールアドレス一覧
     * @param ccAddressList CCに指定されたメールアドレス一覧
     * @param bccAddressList BCCに指定されたメールアドレス一覧
     * @throws SQLException SQL実行時例外
     */
    public void insertSendAddress(long mailNum,
                                    int wacSid,
                                    List<String> toAddressList,
                                    List<String> ccAddressList,
                                    List<String> bccAddressList,
                                    int wlsNum)
    throws SQLException {

        int number = wlsNum;
        int maxNumber = (wlsNum - 1);
        if (toAddressList != null) {
            maxNumber += toAddressList.size();
        }
        if (ccAddressList != null) {
            maxNumber += ccAddressList.size();
        }
        if (bccAddressList != null) {
            maxNumber += bccAddressList.size();
        }

        //メールアドレスが1件も指定されていない場合は登録処理を行わない
        if (maxNumber == 0) {
            return;
        }

        PreparedStatement pstmt = null;

        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_SENDADDRESS(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" )");
            sql.addSql(" values");

            //To
            if (toAddressList != null) {
                for (String address : toAddressList) {
                    sql.addSql(" (");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?");
                    if (number < maxNumber) {
                        sql.addSql(" ),");
                    } else {
                        sql.addSql(" )");
                    }

                    sql.addLongValue(mailNum);
                    sql.addIntValue(number);
                    sql.addIntValue(GSConstWebmail.WSA_TYPE_TO);
                    sql.addStrValue(new String(address));
                    sql.addIntValue(wacSid);
                    sql.addStrValue(address.toLowerCase());

                    number++;
                }
            }

            //Cc
            if (ccAddressList != null) {
                for (String address : ccAddressList) {
                    sql.addSql(" (");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?");
                    if (number < maxNumber) {
                        sql.addSql(" ),");
                    } else {
                        sql.addSql(" )");
                    }

                    sql.addLongValue(mailNum);
                    sql.addIntValue(number);
                    sql.addIntValue(GSConstWebmail.WSA_TYPE_CC);
                    sql.addStrValue(new String(address));
                    sql.addIntValue(wacSid);
                    sql.addStrValue(address.toLowerCase());

                    number++;
                }
            }

            //Bcc
            if (bccAddressList != null) {
                for (String address : bccAddressList) {
                    sql.addSql(" (");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?");
                    if (number < maxNumber) {
                        sql.addSql(" ),");
                    } else {
                        sql.addSql(" )");
                    }

                    sql.addLongValue(mailNum);
                    sql.addIntValue(number);
                    sql.addIntValue(GSConstWebmail.WSA_TYPE_BCC);
                    sql.addStrValue(new String(address));
                    sql.addIntValue(wacSid);
                    sql.addStrValue(address.toLowerCase());

                    number++;
                }
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
     * <p>Update WML_SENDADDRESS Data Bindding JavaBean
     * @param bean WML_SENDADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlSendaddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" set ");
            sql.addSql("   WSA_TYPE=?,");
            sql.addSql("   WSA_ADDRESS=?,");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   WSA_ADDRESS_SRH=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WSA_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWsaType());
            sql.addStrValue(bean.getWsaAddress());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWsaAddressSrh());

            //where
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWsaNum());

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
     * <p>Select WML_SENDADDRESS All Data
     * @return List in WML_SENDADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlSendaddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlSendaddressModel> ret = new ArrayList<WmlSendaddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" from ");
            sql.addSql("   WML_SENDADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlSendaddressFromRs(rs));
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
     * <p>Select WML_SENDADDRESS All Data
     * @param mailNum メッセージ番号
     * @return List in WML_SENDADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlSendaddressModel> select(long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlSendaddressModel> ret = new ArrayList<WmlSendaddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" from ");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlSendaddressFromRs(rs));
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
     * <p>Select WML_SENDADDRESS
     * @param wmdMailnum WMD_MAILNUM
     * @param wsaNum WSA_NUM
     * @return WML_SENDADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public WmlSendaddressModel select(long wmdMailnum, int wsaNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlSendaddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" from");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WSA_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wsaNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlSendaddressFromRs(rs);
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
     * <p>Delete WML_SENDADDRESS
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

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
     * <p>Delete WML_SENDADDRESS
     * @param wmdMailnum WMD_MAILNUM
     * @param wsaNum WSA_NUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum, int wsaNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WSA_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wsaNum);

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
     * <p>Select WML_SENDADDRESS All Data
     * @return List in WML_SENDADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public long count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_SENDADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <p>Select WML_SENDADDRESS All Data
     * @return List in WML_SENDADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public long maxMailNum() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WMD_MAILNUM) as MAX");
            sql.addSql(" from ");
            sql.addSql("   WML_SENDADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("MAX");
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
     * <p>指定した範囲の送信先情報を取得する
     * @param from 開始
     * @param to 終了
     * @return List in WML_SENDADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlSendaddressModel> selectPart(
            long from, long to) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlSendaddressModel> ret = new ArrayList<WmlSendaddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_ADDRESS,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSA_ADDRESS_SRH");
            sql.addSql(" from ");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM >= ?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM <= ?");

            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc,");
            sql.addSql("   WSA_NUM asc");

            sql.addLongValue(from);
            sql.addLongValue(to);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlSendaddressFromRs(rs));
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
     * <br>[機  能] 指定したメールの送信先メールアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNum メッセージ番号
     * @return 送信先メールアドレス
     * @throws SQLException SQL実行例外
     */
    public String getSendAdress(
            int wacSid,
            long mailNum)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WSA_ADDRESS ");
            sql.addSql(" from ");
            sql.addSql("   WML_SENDADDRESS ");
            sql.addSql(" where ");
            sql.addSql("    WAC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addSql(" order by ");
            sql.addSql("   WSA_TYPE asc,");
            sql.addSql("   WSA_NUM asc ");
            sql.addSql(" limit 1; ");

            sql.addIntValue(wacSid);
            sql.addLongValue(mailNum);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = rs.getString("WSA_ADDRESS");

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
     * <p>Create WML_SENDADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlSendaddressModel
     * @throws SQLException SQL実行例外
     */
    private WmlSendaddressModel __getWmlSendaddressFromRs(ResultSet rs) throws SQLException {
        WmlSendaddressModel bean = new WmlSendaddressModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWsaNum(rs.getInt("WSA_NUM"));
        bean.setWsaType(rs.getInt("WSA_TYPE"));
        bean.setWsaAddress(rs.getString("WSA_ADDRESS"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWsaAddressSrh(rs.getString("WSA_ADDRESS_SRH"));
        return bean;
    }
}
