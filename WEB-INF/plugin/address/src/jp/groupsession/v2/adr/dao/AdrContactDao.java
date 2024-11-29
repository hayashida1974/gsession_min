package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrContactModel;

/**
 * <p>ADR_CONTACT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrContactDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrContactDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrContactDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrContactDao(Connection con) {
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
            sql.addSql("drop table ADR_CONTACT");

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
            sql.addSql(" create table ADR_CONTACT (");
            sql.addSql("   ADC_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   ADC_TITLE varchar(100) not null,");
            sql.addSql("   ADC_TYPE NUMBER(10,0) not null,");
            sql.addSql("   ADC_CTTIME varchar(23),");
            sql.addSql("   ADC_CTTIME_TO varchar(23),");
            sql.addSql("   PRJ_SID NUMBER(10,0),");
            sql.addSql("   ADC_BIKO varchar(1000),");
            sql.addSql("   ADC_AUID NUMBER(10,0) not null,");
            sql.addSql("   ADC_ADATE varchar(23) not null,");
            sql.addSql("   ADC_EUID NUMBER(10,0) not null,");
            sql.addSql("   ADC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADC_SID)");
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
     * <p>Insert ADR_CONTACT Data Bindding JavaBean
     * @param bean ADR_CONTACT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrContactModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_CONTACT(");
            sql.addSql("   ADC_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_TITLE,");
            sql.addSql("   ADC_TYPE,");
            sql.addSql("   ADC_CTTIME,");
            sql.addSql("   ADC_CTTIME_TO,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_BIKO,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE,");
            sql.addSql("   ADC_GRP_SID");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdcSid());
            sql.addIntValue(bean.getAdrSid());
            sql.addStrValue(bean.getAdcTitle());
            sql.addIntValue(bean.getAdcType());
            sql.addDateValue(bean.getAdcCttime());
            sql.addDateValue(bean.getAdcCttimeTo());
            sql.addIntValue(bean.getPrjSid());
            sql.addStrValue(bean.getAdcBiko());
            sql.addIntValue(bean.getAdcAuid());
            sql.addDateValue(bean.getAdcAdate());
            sql.addIntValue(bean.getAdcEuid());
            sql.addDateValue(bean.getAdcEdate());
            sql.addIntValue(bean.getAdcGrpSid());
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
     * <p>Insert ADR_CONTACT Data Bindding JavaBean
     * @param beanList ADR_CONTACT DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<AdrContactModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<AdrContactModel> exeList = new ArrayList<>();
        Iterator<AdrContactModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" ADR_CONTACT(");
        sb.append("   ADC_SID,");
        sb.append("   ADR_SID,");
        sb.append("   ADC_TITLE,");
        sb.append("   ADC_TYPE,");
        sb.append("   ADC_CTTIME,");
        sb.append("   ADC_CTTIME_TO,");
        sb.append("   PRJ_SID,");
        sb.append("   ADC_BIKO,");
        sb.append("   ADC_AUID,");
        sb.append("   ADC_ADATE,");
        sb.append("   ADC_EUID,");
        sb.append("   ADC_EDATE,");
        sb.append("   ADC_GRP_SID");
        sb.append(" )");
        sb.append(" values");


        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());

            Iterator<AdrContactModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                AdrContactModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
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

                sql.addIntValue(bean.getAdcSid());
                sql.addIntValue(bean.getAdrSid());
                sql.addStrValue(bean.getAdcTitle());
                sql.addIntValue(bean.getAdcType());
                sql.addDateValue(bean.getAdcCttime());
                sql.addDateValue(bean.getAdcCttimeTo());
                sql.addIntValue(bean.getPrjSid());
                sql.addStrValue(bean.getAdcBiko());
                sql.addIntValue(bean.getAdcAuid());
                sql.addDateValue(bean.getAdcAdate());
                sql.addIntValue(bean.getAdcEuid());
                sql.addDateValue(bean.getAdcEdate());
                sql.addIntValue(bean.getAdcGrpSid());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                pstmt.executeUpdate();

            }
            exeList.clear();
        }
    }

    /**
     * <p>Update ADR_CONTACT Data Bindding JavaBean
     * @param bean ADR_CONTACT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrContactModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" set ");
            sql.addSql("   ADR_SID=?,");
            sql.addSql("   ADC_TITLE=?,");
            sql.addSql("   ADC_TYPE=?,");
            sql.addSql("   ADC_CTTIME=?,");
            sql.addSql("   ADC_CTTIME_TO=?,");
            sql.addSql("   PRJ_SID=?,");
            sql.addSql("   ADC_BIKO=?,");
            sql.addSql("   ADC_AUID=?,");
            sql.addSql("   ADC_ADATE=?,");
            sql.addSql("   ADC_EUID=?,");
            sql.addSql("   ADC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdrSid());
            sql.addStrValue(bean.getAdcTitle());
            sql.addIntValue(bean.getAdcType());
            sql.addDateValue(bean.getAdcCttime());
            sql.addDateValue(bean.getAdcCttimeTo());
            sql.addIntValue(bean.getPrjSid());
            sql.addStrValue(bean.getAdcBiko());
            sql.addIntValue(bean.getAdcAuid());
            sql.addDateValue(bean.getAdcAdate());
            sql.addIntValue(bean.getAdcEuid());
            sql.addDateValue(bean.getAdcEdate());
            //where
            sql.addIntValue(bean.getAdcSid());

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
     * <p>Select ADR_CONTACT All Data
     * @return List in ADR_CONTACTModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrContactModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrContactModel> ret = new ArrayList<AdrContactModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADC_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_TITLE,");
            sql.addSql("   ADC_TYPE,");
            sql.addSql("   ADC_CTTIME,");
            sql.addSql("   ADC_CTTIME_TO,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_BIKO,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE,");
            sql.addSql("   ADC_GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_CONTACT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrContactFromRs(rs));
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
     * <br>[機  能] 指定されたコンタクト履歴グループSIDのアドレス帳を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adcsid コンタクト履歴SID
     * @param adcGrpSid コンタクト履歴グループSID
     * @return ret コンタクト履歴グループのアドレス帳リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<AdrContactModel> selectGrpList(int adcsid, int adcGrpSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrContactModel> ret = new ArrayList<AdrContactModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADC_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_TITLE,");
            sql.addSql("   ADC_TYPE,");
            sql.addSql("   ADC_CTTIME,");
            sql.addSql("   ADC_CTTIME_TO,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_BIKO,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE,");
            sql.addSql("   ADC_GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADC_GRP_SID = ?");
            sql.addIntValue(adcGrpSid);

            if (adcsid > 0) {
                sql.addSql(" and");
                sql.addSql("   ADC_SID <> ?");
                sql.addIntValue(adcsid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrContactFromRs(rs));
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
     * <p>Select ADR_CONTACT
     * @param adcSid ADC_SID
     * @return ADR_CONTACTModel
     * @throws SQLException SQL実行例外
     */
    public AdrContactModel select(int adcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrContactModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADC_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_TITLE,");
            sql.addSql("   ADC_TYPE,");
            sql.addSql("   ADC_CTTIME,");
            sql.addSql("   ADC_CTTIME_TO,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_BIKO,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE,");
            sql.addSql("   ADC_GRP_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrContactFromRs(rs);
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
     * <p>Delete ADR_CONTACT
     * @param adcSid ADC_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int adcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);

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
     * <p>Delete ADR_CONTACT
     * @param scdSid SCD_SID list
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(Collection<Integer> adcSidList) throws SQLException {
        int count = 0;

        if (adcSidList == null || adcSidList.size() <= 0) {
            return count;
        }
        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = adcSidList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" delete");
        sb.append(" from");
        sb.append("   ADR_CONTACT");
        sb.append(" where ");

        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" ADC_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                count += pstmt.executeUpdate();

            }
            exeList.clear();
        }
        return count;

    }

    /**
     * <br>[機  能] 指定したアドレス帳のコンタクト履歴情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteToAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

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
     * <br>[機  能] 指定したコンタクト履歴グループSIDデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adcGrpSid コンタクト履歴グループSID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteToGrpData(int adcGrpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADC_GRP_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcGrpSid);

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
     * <br>[機  能] 指定されたコンタクト履歴グループSIDのアドレス帳SIDを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adcGrpSid コンタクト履歴グループSID
     * @return ret コンタクト履歴グループのアドレス帳リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Integer> selectGrpAdrSid(int adcGrpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        Connection con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADC_GRP_SID = ?");
            sql.addSql(" group by ");
            sql.addSql("   ADR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcGrpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("ADR_SID"));
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
     * <br>[機  能] 指定されたアドレス帳のコンタクト履歴SIDを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adrSid アドレス帳SID
     * @return コンタクト履歴SID
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Integer> selectAdcSid(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        Connection con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADC_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("ADC_SID"));
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
     * <br>[機  能] アドレス帳情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 対象ユーザ
     * @param dateFr 日付範囲(開始)
     * @param dateTo 日付範囲(終了)
     * @param sessionUserSid セッションユーザ
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<AdrContactModel> getArdContactList(int userSid,
                                                   UDate dateFr,
                                                   UDate dateTo,
                                                   int sessionUserSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AdrContactModel> ret = new ArrayList<AdrContactModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_ADDRESS.ADR_SID as ADR_SID,");

            sql.addSql("   CONTACT.ADC_SID as ADC_SID,");
            sql.addSql("   CONTACT.ADC_TITLE as ADC_TITLE,");
            sql.addSql("   CONTACT.ADC_TYPE as ADC_TYPE,");
            sql.addSql("   CONTACT.ADC_CTTIME as ADC_CTTIME,");
            sql.addSql("   CONTACT.ADC_CTTIME_TO as ADC_CTTIME_TO,");
            sql.addSql("   CONTACT.PRJ_SID as PRJ_SID,");
            sql.addSql("   CONTACT.ADC_BIKO as ADC_BIKO,");
            sql.addSql("   CONTACT.ADC_AUID as ADC_AUID,");
            sql.addSql("   CONTACT.ADC_ADATE as ADC_ADATE,");
            sql.addSql("   CONTACT.ADC_EUID as ADC_EUID,");
            sql.addSql("   CONTACT.ADC_EDATE as ADC_EDATE,");
            sql.addSql("   CONTACT.ADC_GRP_SID as ADC_GRP_SID");

            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      ADC_SID,");
            sql.addSql("      ADR_SID,");
            sql.addSql("      ADC_TITLE,");
            sql.addSql("      ADC_TYPE,");
            sql.addSql("      ADC_CTTIME,");
            sql.addSql("      ADC_CTTIME_TO,");
            sql.addSql("      PRJ_SID,");
            sql.addSql("      ADC_BIKO,");
            sql.addSql("      ADC_AUID,");
            sql.addSql("      ADC_ADATE,");
            sql.addSql("      ADC_EUID,");
            sql.addSql("      ADC_EDATE,");
            sql.addSql("      ADC_GRP_SID");
            sql.addSql("    from");
            sql.addSql("      ADR_CONTACT");

            // コンタクト履歴取得条件
            List<String> whereList = new ArrayList<String>();
            if (userSid > 0) {
                whereList.add("    where");
                whereList.add("      ADC_EUID = ?");
                sql.addIntValue(userSid);
            }

            // 日時条件(指定日時範囲を含むもの全て取得)
            if (dateTo != null) {
                whereList.add((whereList.size() > 0 ? "    and" : "    where"));
                whereList.add("      ADC_CTTIME<=?");
                sql.addDateValue(dateTo);
            }
            if (dateFr != null) {
                whereList.add((whereList.size() > 0 ? "    and" : "    where"));
                whereList.add("      ADC_CTTIME_TO>=?");
                sql.addDateValue(dateFr);
            }
            if (whereList.size() > 0) {
                for (String whereStr : whereList) {
                    sql.addSql(whereStr);
                }
            }
            sql.addSql("   ) CONTACT,");

            sql.addSql("   ADR_ADDRESS"); // 権限チェックに必要

            sql.addSql(" where");
            sql.addSql("   CONTACT.ADR_SID = ADR_ADDRESS.ADR_SID");
            sql.addSql(" and");

            //閲覧権限
            AddressDao.addViewableWhereSQL(sql, sessionUserSid);

            //並び順を設定
            sql.addSql(" order by");
            sql.addSql("   CONTACT.ADC_CTTIME asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrContactFromRs(rs));
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
     * <br>[機  能] 指定したコンタクト履歴のデータサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adcSidList ADC_SID
     * @return データサイズ
     * @throws SQLException SQL実行例外
     */
    public long getDataSize(List<Integer> adcSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();
        if (adcSidList == null || adcSidList.isEmpty()) {
            return dataSize;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   octet_length(ADC_TITLE) as TITLE_SIZE,");
            sql.addSql("   octet_length(ADC_BIKO) as BIKO_SIZE");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID in (");

            for (int idx = 0; idx < adcSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(adcSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("BIKO_SIZE");
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
     * <p>Create ADR_CONTACT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrContactModel
     * @throws SQLException SQL実行例外
     */
    private AdrContactModel __getAdrContactFromRs(ResultSet rs) throws SQLException {
        AdrContactModel bean = new AdrContactModel();
        bean.setAdcSid(rs.getInt("ADC_SID"));
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdcTitle(rs.getString("ADC_TITLE"));
        bean.setAdcType(rs.getInt("ADC_TYPE"));
        bean.setAdcCttime(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME")));
        bean.setAdcCttimeTo(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME_TO")));
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setAdcBiko(rs.getString("ADC_BIKO"));
        bean.setAdcAuid(rs.getInt("ADC_AUID"));
        bean.setAdcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_ADATE")));
        bean.setAdcEuid(rs.getInt("ADC_EUID"));
        bean.setAdcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_EDATE")));
        bean.setAdcGrpSid(rs.getInt("ADC_GRP_SID"));
        return bean;
    }
}