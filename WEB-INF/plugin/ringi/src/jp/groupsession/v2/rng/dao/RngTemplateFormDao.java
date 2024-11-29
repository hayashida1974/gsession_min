package jp.groupsession.v2.rng.dao;

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
import jp.groupsession.v2.rng.model.RngTemplateFormModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <p>RNG_TEMPLATE_FORM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateFormDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplateFormDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplateFormDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplateFormDao(Connection con) {
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
            sql.addSql("drop table RNG_TEMPLATE_FORM");

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
            sql.addSql(" create table RNG_TEMPLATE_FORM (");
            sql.addSql("   RTP_SID NUMBER(10,0) not null,");
            sql.addSql("   RTP_VER NUMBER(10,0) not null,");
            sql.addSql("   RTF_SID NUMBER(10,0) not null,");
            sql.addSql("   RTF_ID varchar(100),");
            sql.addSql("   RTF_TITLE varchar(100),");
            sql.addSql("   RTF_REQUIRE NUMBER(10,0) not null,");
            sql.addSql("   RTF_TYPE NUMBER(10,0) not null,");
            sql.addSql("   RTF_BODY Date,");
            sql.addSql("   primary key (RTP_SID,RTP_VER,RTF_SID)");
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
     * <p>Insert RNG_TEMPLATE_FORM Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_FORM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplateFormModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATE_FORM(");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTF_SID,");
            sql.addSql("   RTF_ID,");
            sql.addSql("   RTF_TITLE,");
            sql.addSql("   RTF_REQUIRE,");
            sql.addSql("   RTF_TYPE,");
            sql.addSql("   RTF_BODY");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtpSid());
            sql.addIntValue(bean.getRtpVer());
            sql.addIntValue(bean.getRtfSid());
            sql.addStrValue(bean.getRtfId());
            sql.addStrValue(bean.getRtfTitle());
            sql.addIntValue(bean.getRtfRequire());
            sql.addIntValue(bean.getRtfType());
            sql.addStrValue(bean.getRtfBody());
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
     * <p>Update RNG_TEMPLATE_FORM Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_FORM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplateFormModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" set ");
            sql.addSql("   RTF_ID=?,");
            sql.addSql("   RTF_TITLE=?,");
            sql.addSql("   RTF_REQUIRE=?,");
            sql.addSql("   RTF_TYPE=?,");
            sql.addSql("   RTF_BODY=?");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   RTP_VER=?");
            sql.addSql(" and");
            sql.addSql("   RTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRtfId());
            sql.addStrValue(bean.getRtfTitle());
            sql.addIntValue(bean.getRtfRequire());
            sql.addIntValue(bean.getRtfType());
            sql.addStrValue(bean.getRtfBody());
            //where
            sql.addIntValue(bean.getRtpSid());
            sql.addIntValue(bean.getRtpVer());
            sql.addIntValue(bean.getRtfSid());

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
     *
     * <br>[機  能] 最新テンプレートで使用されているRngTemplateFormModelを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid テンプレートSID
     * @param rtpVer テンプレートバージョン -1以下を指定した場合は最新
     * @return List in RNG_TEMPLATE_FORMModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateFormModel> select(int rtpSid, int rtpVer) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateFormModel> ret = new ArrayList<RngTemplateFormModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTF_SID,");
            sql.addSql("   RTF_ID,");
            sql.addSql("   RTF_TITLE,");
            sql.addSql("   RTF_REQUIRE,");
            sql.addSql("   RTF_TYPE,");
            sql.addSql("   RTF_BODY");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addIntValue(rtpSid);
            sql.addSql(" and");
            if (rtpVer >= 0) {
                sql.addSql("   RTP_VER =?");
                sql.addIntValue(rtpVer);
            } else {
                sql.addSql("   RTP_VER in (");
                sql.addSql("     select RTP_VER from RNG_TEMPLATE where RTP_SID=?");
                sql.addSql("         and RTP_MAXVER_KBN = ?");
                sql.addSql("   )");
                sql.addIntValue(rtpSid);
                sql.addIntValue(RngTemplateDao.MAXVER_KBN_ON);
            }
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateFormFromRs(rs));
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
     *
     * <br>[機  能] 旧バージョンの稟議から引き継ぎ対象のフォームSIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid テンプレートSID
     * @param oldVer 古いバージョン
     * @param newVer 新しいバージョン 0以下 は最新
     * @return List in RTF_SID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getConvertAbleSidList(int rtpSid, int oldVer, int newVer) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NEW_FORM.RTF_SID as RTF_SID");
            sql.addSql(" from ");
            sql.addSql("( select ");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTF_SID,");
            sql.addSql("   RTF_ID,");
            sql.addSql("   RTF_TITLE,");
            sql.addSql("   RTF_REQUIRE,");
            sql.addSql("   RTF_TYPE,");
            sql.addSql("   RTF_BODY");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addIntValue(rtpSid);
            sql.addSql(" and");
            sql.addSql("   RTP_VER =?");
            sql.addIntValue(oldVer);
            sql.addSql(" and RTF_ID <> ''");
            sql.addSql(" ) OLD_FORM, ");
            sql.addSql("( select ");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTF_SID,");
            sql.addSql("   RTF_ID,");
            sql.addSql("   RTF_TITLE,");
            sql.addSql("   RTF_REQUIRE,");
            sql.addSql("   RTF_TYPE,");
            sql.addSql("   RTF_BODY");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addIntValue(rtpSid);
            sql.addSql(" and");
            if (newVer > 0) {
                sql.addSql("   RTP_VER =?");
                sql.addIntValue(newVer);
            } else {
                sql.addSql("   RTP_VER in (");
                sql.addSql("     select RTP_VER from RNG_TEMPLATE where RTP_SID=?");
                sql.addSql("         and RTP_MAXVER_KBN = ?");
                sql.addSql("   )");
                sql.addIntValue(rtpSid);
                sql.addIntValue(RngTemplateDao.MAXVER_KBN_ON);
            }
            sql.addSql(" and RTF_ID <> ''");
            sql.addSql(" ) NEW_FORM ");
            sql.addSql(" where ");
            sql.addSql("   OLD_FORM.RTF_ID=NEW_FORM.RTF_ID");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("RTF_SID"));
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
     * <p>Select RNG_TEMPLATE_FORM All Data
     * @return List in RNG_TEMPLATE_FORMModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateFormModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateFormModel> ret = new ArrayList<RngTemplateFormModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTF_SID,");
            sql.addSql("   RTF_ID,");
            sql.addSql("   RTF_TITLE,");
            sql.addSql("   RTF_REQUIRE,");
            sql.addSql("   RTF_TYPE,");
            sql.addSql("   RTF_BODY");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_FORM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateFormFromRs(rs));
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
     * <p>Select RNG_TEMPLATE_FORM
     * @param rtpSid RTP_SID
     * @param rtpVer RTP_VER
     * @param rtfSid RTF_SID
     * @return RNG_TEMPLATE_FORMModel
     * @throws SQLException SQL実行例外
     */
    public RngTemplateFormModel select(int rtpSid, int rtpVer, int rtfSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngTemplateFormModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTF_SID,");
            sql.addSql("   RTF_ID,");
            sql.addSql("   RTF_TITLE,");
            sql.addSql("   RTF_REQUIRE,");
            sql.addSql("   RTF_TYPE,");
            sql.addSql("   RTF_BODY");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   RTP_VER=?");
            sql.addSql(" and");
            sql.addSql("   RTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtpSid);
            sql.addIntValue(rtpVer);
            sql.addIntValue(rtfSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngTemplateFormFromRs(rs);
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
     * <br>[機  能] 指定した稟議テンプレートフォーム情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpList 稟議テンプレート情報
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public long getTemplateFormDataSize(List<RngTemplateModel> rtpList) throws SQLException {

        if (rtpList == null || rtpList.isEmpty()) {
            return 0;
        }

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RTF_ID)) as ID_SIZE,");
            sql.addSql("   sum(octet_length(RTF_TITLE)) as TITLE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where");
            sql.addSql("    RTP_SID=?");
            sql.addSql("  and");
            sql.addSql("    RTP_VER=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateModel rtpMdl : rtpList) {
                log__.info(sql.toLogString());
                sql.addIntValue(rtpMdl.getRtpSid());
                sql.addIntValue(rtpMdl.getRtpVer());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    dataSize += rs.getLong("ID_SIZE");
                    dataSize += rs.getLong("TITLE_SIZE");
                }
                JDBCUtil.closeResultSet(rs);
                sql.clearValue();
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
     * どの稟議でも使用されていないテンプレートの物理削除を行う
     * @param rtpList 論理削除済みテンプレート
     * @return 削除件数
     * @throws SQLException SQL実行例外
     * */
    public int deleteComp(List<RngTemplateModel> rtpList)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where");
            sql.addSql("    RTP_SID=?");
            sql.addSql("  and");
            sql.addSql("    RTP_VER=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateModel rtpMdl : rtpList) {
                log__.info(sql.toLogString());
                sql.addIntValue(rtpMdl.getRtpSid());
                sql.addIntValue(rtpMdl.getRtpVer());
                sql.setParameter(pstmt);
                count = pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete RNG_TEMPLATE_FORM
     * @param rtpSid RTP_SID
     * @param rtpVer RTP_VER
     * @param rtfSid RTF_SID
     * @throws SQLException SQL実行例外
     * @return 実行件数
     */
    public int delete(int rtpSid, int rtpVer, int rtfSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_FORM");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   RTP_VER=?");
            sql.addSql(" and");
            sql.addSql("   RTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtpSid);
            sql.addIntValue(rtpVer);
            sql.addIntValue(rtfSid);

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
     * <p>Create RNG_TEMPLATE_FORM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplateFormModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplateFormModel __getRngTemplateFormFromRs(ResultSet rs) throws SQLException {
        RngTemplateFormModel bean = new RngTemplateFormModel();
        bean.setRtpSid(rs.getInt("RTP_SID"));
        bean.setRtpVer(rs.getInt("RTP_VER"));
        bean.setRtfSid(rs.getInt("RTF_SID"));
        bean.setRtfId(rs.getString("RTF_ID"));
        bean.setRtfTitle(rs.getString("RTF_TITLE"));
        bean.setRtfRequire(rs.getInt("RTF_REQUIRE"));
        bean.setRtfType(rs.getInt("RTF_TYPE"));
        bean.setRtfBody(rs.getString("RTF_BODY"));
        return bean;
    }
}
