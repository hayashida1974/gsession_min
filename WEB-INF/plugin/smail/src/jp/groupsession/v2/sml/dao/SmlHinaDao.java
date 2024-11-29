package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlHinaModel;

/**
 * <p>SMAIL_HINA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlHinaDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlHinaDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlHinaDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlHinaDao(Connection con) {
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
            sql.addSql("drop table SML_HINA");

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
            sql.addSql(" create table SML_HINA (");
            sql.addSql("   SAC_SID NUMBER(4,0) not null,");
            sql.addSql("   SHN_SID NUMBER(4,0) not null,");
            sql.addSql("   SHN_HNAME varchar(150),");
            sql.addSql("   SHN_TITLE varchar(150),");
            sql.addSql("   SHN_MARK NUMBER(4,0),");
            sql.addSql("   SHN_BODY varchar(3000),");
            sql.addSql("   SHN_JKBN NUMBER(4,0),");
            sql.addSql("   SHN_CKBN NUMBER(4,0),");
            sql.addSql("   SHN_AUID NUMBER(4,0) not null,");
            sql.addSql("   SHN_ADATE varchar(8) not null,");
            sql.addSql("   SHN_EUID NUMBER(4,0) not null,");
            sql.addSql("   SHN_EDATE varchar(8) not null,");
            sql.addSql("   primary key (SHN_SID)");
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
     * <p>Insert SML_HINA Data Bindding JavaBean
     * @param bean SML_HINA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlHinaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_HINA(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getShnSid());
            sql.addStrValue(bean.getShnHname());
            sql.addStrValue(bean.getShnTitle());
            sql.addIntValue(bean.getShnMark());
            sql.addStrValue(bean.getShnBody());
            sql.addIntValue(bean.getShnJkbn());
            sql.addIntValue(bean.getShnCkbn());
            sql.addIntValue(bean.getShnAuid());
            sql.addDateValue(bean.getShnAdate());
            sql.addIntValue(bean.getShnEuid());
            sql.addDateValue(bean.getShnEdate());
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
     * <p>Update SML_HINA Data Bindding JavaBean
     * @param bean SML_HINA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int update(SmlHinaModel bean) throws SQLException {

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
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SHN_HNAME=?,");
            sql.addSql("   SHN_TITLE=?,");
            sql.addSql("   SHN_MARK=?,");
            sql.addSql("   SHN_BODY=?,");
            sql.addSql("   SHN_JKBN=?,");
            sql.addSql("   SHN_AUID=?,");
            sql.addSql("   SHN_ADATE=?,");
            sql.addSql("   SHN_EUID=?,");
            sql.addSql("   SHN_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addStrValue(bean.getShnHname());
            sql.addStrValue(bean.getShnTitle());
            sql.addIntValue(bean.getShnMark());
            sql.addStrValue(bean.getShnBody());
            sql.addIntValue(bean.getShnJkbn());
            sql.addIntValue(bean.getShnAuid());
            sql.addDateValue(bean.getShnAdate());
            sql.addIntValue(bean.getShnEuid());
            sql.addDateValue(bean.getShnEdate());
            //where
            sql.addIntValue(bean.getShnSid());

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
     * <p>Select SML_HINA All Data
     * @return List in SML_HINAModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlHinaModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlHinaModel> ret = new ArrayList<SmlHinaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlHinaFromRs(rs));
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
     * <p>Select SML_HINA All Data
     * @param sacSid sacSid
     * @return List in SML_HINAModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlHinaModel> select(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlHinaModel> ret = new ArrayList<SmlHinaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SHN_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   SHN_HNAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlHinaFromRs(rs));
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
     * <p>共通と個人のひな形リストを取得する。
     * @param sacSid sacSid
     * @return List in SML_HINAModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlHinaModel> getHinaList(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlHinaModel> ret = new ArrayList<SmlHinaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     SHN_CKBN=?");
            sql.addSql("   or");
            sql.addSql("     SAC_SID=?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   SHN_JKBN = ?");

            sql.addSql(" order by");
            sql.addSql("   SHN_CKBN ASC,");
            sql.addSql("   SHN_HNAME ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.HINA_KBN_CMN);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlHinaFromRs(rs));
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
     * <p>Select SML_HINA
     * @param bean SML_HINA Model
     * @return SML_HINAModel
     * @throws SQLException SQL実行例外
     */
    public SmlHinaModel select(SmlHinaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlHinaModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getShnSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlHinaFromRs(rs);
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
     * <p>Delete SML_HINA
     * @param bean SML_HINA Model
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public  int delete(SmlHinaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getShnSid());

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
     * <br>[機  能] 指定されたSIDの雛形名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param hinaSid 雛形SID
     * @return hinaName 雛形名称
     * @throws SQLException SQL実行例外
     */
    public String getHinaName(int hinaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String hinaName = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SHN_HNAME");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SHN_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(hinaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                hinaName = rs.getString("SHN_HNAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return hinaName;
    }

    /**
     * <br>[機  能] 指定されたアカウントの雛形件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param hinaKbn ひな形区分
     * @return count データ数
     * @throws SQLException SQL実行例外
     */
    public int getHinaCount(int sacSid, int hinaKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SHN_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SHN_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SHN_CKBN = ?");

            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(hinaKbn);

            if (hinaKbn == GSConstSmail.HINA_KBN_PRI) {
                sql.addSql(" and");
                sql.addSql("   SAC_SID = ?");
                sql.addIntValue(sacSid);
            }

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
     * <br>[機  能] 指定されたアカウントの雛形一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param hinaKbn ひな形区分
     * @return ret 雛形一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList < SmlHinaModel > selectHinaList(int sacSid,
                                                        int offset,
                                                        int limit,
                                                        int sortKey,
                                                        int orderKey,
                                                        int hinaKbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmlHinaModel> ret = new ArrayList<SmlHinaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SHN_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SHN_CKBN = ?");
            if (hinaKbn == GSConstSmail.HINA_KBN_PRI) {
                sql.addSql(" and");
                sql.addSql("   SAC_SID = ?");
            }
            sql.addSql(" order by");

            //ソートカラム
            switch (sortKey) {
                case GSConstSmail.HINA_SORT_KEY_NAME:
                    sql.addSql("  SHN_HNAME");
                    break;
                case GSConstSmail.HINA_SORT_KEY_TITLE:
                    sql.addSql("  SHN_TITLE");
                    break;
                case GSConstSmail.HINA_SORT_KEY_MARK:
                    sql.addSql("  SHN_MARK");
                    break;
                default:
                    break;
            }

            //オーダー
            if (orderKey == GSConstSmail.ORDER_KEY_ASC) {
                sql.addSql("  asc");
            } else if (orderKey == GSConstSmail.ORDER_KEY_DESC) {
                sql.addSql("  desc");
            }

            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(hinaKbn);
            if (hinaKbn == GSConstSmail.HINA_KBN_PRI) {
                sql.addIntValue(sacSid);
            }

            //ログを出力
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                ret.add(__getSmlHinaFromRs(rs));
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
     * <br>[機  能] 指定した区分の雛形一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param hinaKbn ひな形区分
     * @return ret 雛形一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmlHinaModel> selectHinaList(int sacSid, int hinaKbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmlHinaModel> ret = new ArrayList<SmlHinaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SHN_SID,");
            sql.addSql("   SHN_HNAME,");
            sql.addSql("   SHN_TITLE,");
            sql.addSql("   SHN_MARK,");
            sql.addSql("   SHN_BODY,");
            sql.addSql("   SHN_JKBN,");
            sql.addSql("   SHN_CKBN,");
            sql.addSql("   SHN_AUID,");
            sql.addSql("   SHN_ADATE,");
            sql.addSql("   SHN_EUID,");
            sql.addSql("   SHN_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SHN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql(" and");
            sql.addSql("   SHN_CKBN = ?");
            sql.addIntValue(hinaKbn);
            if (hinaKbn == GSConstSmail.HINA_KBN_PRI) {
                sql.addSql(" and");
                sql.addSql("   SAC_SID = ?");
            }
            sql.addSql(" order by");
            sql.addSql("  SHN_HNAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (hinaKbn == GSConstSmail.HINA_KBN_PRI) {
                sql.addIntValue(sacSid);
            }
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSmlHinaFromRs(rs));
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
     * <br>[機  能] 指定された雛形データを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新Model
     * @throws SQLException SQL実行例外
     */
    public void updateHinaData(SmlHinaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_HINA");
            sql.addSql(" set ");
            sql.addSql("   SHN_HNAME = ?,");
            sql.addSql("   SHN_TITLE = ?,");
            sql.addSql("   SHN_MARK = ?,");
            sql.addSql("   SHN_BODY = ?,");
            sql.addSql("   SHN_EUID = ?,");
            sql.addSql("   SHN_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SHN_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getShnHname());
            sql.addStrValue(bean.getShnTitle());
            sql.addIntValue(bean.getShnMark());
            sql.addStrValue(bean.getShnBody());
            sql.addIntValue(bean.getShnEuid());
            sql.addDateValue(bean.getShnEdate());
            sql.addIntValue(bean.getShnSid());

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
     * <br>[機  能] 指定されたアカウントSIDのひな形を物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void deleteHinaButuri(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_HINA");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

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
     * <p>Create SML_HINA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SMLHinaModel
     * @throws SQLException SQL実行例外
     */
    private SmlHinaModel __getSmlHinaFromRs(ResultSet rs) throws SQLException {
        SmlHinaModel bean = new SmlHinaModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setShnSid(rs.getInt("SHN_SID"));
        bean.setShnHname(rs.getString("SHN_HNAME"));
        bean.setShnTitle(rs.getString("SHN_TITLE"));
        bean.setShnMark(rs.getInt("SHN_MARK"));
        bean.setShnBody(rs.getString("SHN_BODY"));
        bean.setShnJkbn(rs.getInt("SHN_JKBN"));
        bean.setShnCkbn(rs.getInt("SHN_CKBN"));
        bean.setShnAuid(rs.getInt("SHN_AUID"));
        bean.setShnAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SHN_ADATE")));
        bean.setShnEuid(rs.getInt("SHN_EUID"));
        bean.setShnEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SHN_EDATE")));
        return bean;
    }
}