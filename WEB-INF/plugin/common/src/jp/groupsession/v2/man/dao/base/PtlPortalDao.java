package jp.groupsession.v2.man.dao.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.model.base.PtlPortalModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTAL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortalDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortalDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortalDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTAL");

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
            sql.addSql(" create table PTL_PORTAL (");
            sql.addSql("   PTL_SID integer not null,");
            sql.addSql("   PTL_NAME varchar(100) not null,");
            sql.addSql("   PTL_DESCRIPTION varchar(1000),");
            sql.addSql("   PTL_OPEN integer not null,");
            sql.addSql("   PTL_ACCESS integer not null,");
            sql.addSql("   PTL_AUID integer not null,");
            sql.addSql("   PTL_ADATE timestamp not null,");
            sql.addSql("   PTL_EUID integer not null,");
            sql.addSql("   PTL_EDATE timestamp not null,");
            sql.addSql("   primary key (PTL_SID)");
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
     * <p>Insert PTL_PORTAL Data Bindding JavaBean
     * @param bean PTL_PORTAL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortalModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTAL(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTL_NAME,");
            sql.addSql("   PTL_DESCRIPTION,");
            sql.addSql("   PTL_OPEN,");
            sql.addSql("   PTL_ACCESS,");
            sql.addSql("   PTL_AUID,");
            sql.addSql("   PTL_ADATE,");
            sql.addSql("   PTL_EUID,");
            sql.addSql("   PTL_EDATE");
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
            sql.addIntValue(bean.getPtlSid());
            sql.addStrValue(bean.getPtlName());
            sql.addStrValue(bean.getPtlDescription());
            sql.addIntValue(bean.getPtlOpen());
            sql.addIntValue(bean.getPtlAccess());
            sql.addIntValue(bean.getPtlAuid());
            sql.addDateValue(bean.getPtlAdate());
            sql.addIntValue(bean.getPtlEuid());
            sql.addDateValue(bean.getPtlEdate());
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
     * <p>Update PTL_PORTAL Data Bindding JavaBean
     * @param bean PTL_PORTAL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortalModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL");
            sql.addSql(" set ");
            sql.addSql("   PTL_NAME=?,");
            sql.addSql("   PTL_DESCRIPTION=?,");
            sql.addSql("   PTL_OPEN=?,");
            sql.addSql("   PTL_ACCESS=?,");
            sql.addSql("   PTL_EUID=?,");
            sql.addSql("   PTL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPtlName());
            sql.addStrValue(bean.getPtlDescription());
            sql.addIntValue(bean.getPtlOpen());
            sql.addIntValue(bean.getPtlAccess());
            sql.addIntValue(bean.getPtlEuid());
            sql.addDateValue(bean.getPtlEdate());
            //where
            sql.addIntValue(bean.getPtlSid());

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
     * <p>Select PTL_PORTAL All Data
     * @return List in PTL_PORTALModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalModel> ret = new ArrayList<PtlPortalModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTL_NAME,");
            sql.addSql("   PTL_DESCRIPTION,");
            sql.addSql("   PTL_OPEN,");
            sql.addSql("   PTL_ACCESS,");
            sql.addSql("   PTL_AUID,");
            sql.addSql("   PTL_ADATE,");
            sql.addSql("   PTL_EUID,");
            sql.addSql("   PTL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalFromRs(rs));
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
     * <p>Select PTL_PORTAL
     * @param ptlSid PTL_SID
     * @return PTL_PORTALModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortalModel select(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortalModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTL_NAME,");
            sql.addSql("   PTL_DESCRIPTION,");
            sql.addSql("   PTL_OPEN,");
            sql.addSql("   PTL_ACCESS,");
            sql.addSql("   PTL_AUID,");
            sql.addSql("   PTL_ADATE,");
            sql.addSql("   PTL_EUID,");
            sql.addSql("   PTL_EDATE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortalFromRs(rs);
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
     * <p>ポータル一覧を取得します。
     * @param ptsKbn ポータル区分
     * @return PTL_PORTALModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalModel> getPortaList(int ptsKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalModel> ret = new ArrayList<PtlPortalModel>();
        PtlPortalModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_PORTAL.PTL_SID,");
            sql.addSql("   PTL_NAME,");
            sql.addSql("   PTL_DESCRIPTION,");
            sql.addSql("   PTL_OPEN,");
            sql.addSql("   PTL_ACCESS,");
            sql.addSql("   PTL_AUID,");
            sql.addSql("   PTL_ADATE,");
            sql.addSql("   PTL_EUID,");
            sql.addSql("   PTL_EDATE,");
            sql.addSql("   PTS_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL");
            sql.addSql("     left join");
            sql.addSql("       (select PTL_SID, PTS_SORT from PTL_PORTAL_SORT");
            sql.addSql("        where PTS_KBN=?");
            sql.addSql("       ) PORTAL_SORT");
            sql.addSql("     on PTL_PORTAL.PTL_SID = PORTAL_SORT.PTL_SID ");
            sql.addSql(" order by ");
            sql.addSql("   PTS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptsKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = __getPtlPortalFromRs(rs);
                bean.setPtsSort(rs.getInt("PTS_SORT"));
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
     * <p>閲覧可能なポータル一覧を取得します。
     * @param editKbn 管理者設定編集権限区分
     * @param usrSid ユーザSID
     * @return PTL_PORTALModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalModel> getReadPortalList(int editKbn, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalModel> ret = new ArrayList<PtlPortalModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_PORTAL.PTL_SID,");
            sql.addSql("   PTL_PORTAL.PTL_NAME,");
            sql.addSql("   PTL_PORTAL.PTL_DESCRIPTION,");
            sql.addSql("   PORTAL_SORT.PTS_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL");
            sql.addSql("     left join");
            sql.addSql("     (select * from PTL_PORTAL_SORT");
            sql.addSql("        where PTS_KBN=?");
            if (editKbn == GSConstPortal.EDIT_KBN_ADM) {
                sql.addIntValue(GSConstPortal.PTS_KBN_COMMON);
            } else {
                sql.addIntValue(GSConstPortal.PTS_KBN_USER);
                sql.addSql("    and USR_SID=?");
                sql.addIntValue(usrSid);

            }
            sql.addSql("     ) PORTAL_SORT  ");
            sql.addSql("     on PTL_PORTAL.PTL_SID = PORTAL_SORT.PTL_SID");

            sql.addSql(" where ");
            sql.addSql("   PTL_PORTAL.PTL_OPEN=?");
            sql.addSql(" and ");
            sql.addSql("   (  ");
            sql.addSql("     PTL_PORTAL.PTL_ACCESS=?");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("       PTL_PORTAL.PTL_ACCESS=?");
            sql.addSql("       and");
            sql.addSql("       PTL_PORTAL.PTL_SID in (");
            sql.addSql("         select PTL_SID from PTL_PORTAL_CONF_READ");
            sql.addSql("         where");
            sql.addSql("         PTL_PORTAL_CONF_READ.USR_SID=?");
            sql.addSql("         or");
            sql.addSql("         PTL_PORTAL_CONF_READ.GRP_SID in (");
            sql.addSql("                                   select GRP_SID from CMN_BELONGM");
            sql.addSql("                                   where USR_SID=?");
            sql.addSql("                                         )");
            sql.addSql("       ) ");
            sql.addSql("     ) ");
            sql.addSql("   ) ");

            sql.addSql(" order by ");
            sql.addSql("   PORTAL_SORT.PTS_SORT asc ");

            sql.addIntValue(GSConstPortal.PTL_OPENKBN_OK);
            sql.addIntValue(GSConstPortal.PTL_ACCESS_OFF);
            sql.addIntValue(GSConstPortal.PTL_ACCESS_ON);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                PtlPortalModel model = new PtlPortalModel();
                model.setPtlSid(rs.getInt("PTL_SID"));
                model.setPtlName(rs.getString("PTL_NAME"));
                ret.add(model);
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
     * <p>Delete PTL_PORTAL
     * @param ptlSid PTL_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

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
     * <br>[機  能] ユーザごとのポータルの並び順を取得します
     * <br>[解  説] ポータルの並び替え権限が「制限なし」に設定されている場合のみ
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return List in PTL_PORTALModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<PtlPortalModel> selectForUser(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalModel> ret = new ArrayList<PtlPortalModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_PORTAL.PTL_SID,");
            sql.addSql("   PTL_PORTAL.PTL_NAME,");
            sql.addSql("   PTL_PORTAL.PTL_DESCRIPTION,");
            sql.addSql("   PORTAL_SORT.PTS_SORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL");
            sql.addSql("       left join (");
            sql.addSql("           select * from PTL_PORTAL_SORT");
            sql.addSql("           where PTS_KBN = 1");
            sql.addSql("           and USR_SID = ?");
            sql.addSql("       ) PORTAL_SORT");
            sql.addSql("       on");
            sql.addSql("           PTL_PORTAL.PTL_SID = PORTAL_SORT.PTL_SID");
            sql.addSql(" where ");
            sql.addSql("   PTL_PORTAL.PTL_OPEN = 0");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("     PTL_ACCESS = 0");
            sql.addSql("    or ( ");
            sql.addSql("       PTL_ACCESS = 1 ");
            sql.addSql("       and PTL_PORTAL.PTL_SID in (");
            sql.addSql("           select ");
            sql.addSql("               PTL_SID ");
            sql.addSql("           from ");
            sql.addSql("               PTL_PORTAL_CONF_READ");
            sql.addSql("           where ( ");
            sql.addSql("            PTL_PORTAL_CONF_READ.USR_SID = ?");
            sql.addSql("            or PTL_PORTAL_CONF_READ.GRP_SID in ( ");
            sql.addSql("                    select");
            sql.addSql("                      GRP_SID");
            sql.addSql("                    from");
            sql.addSql("                      CMN_BELONGM");
            sql.addSql("                    where");
            sql.addSql("                      USR_SID = ?");
            sql.addSql("            )");
            sql.addSql("           )");
            sql.addSql("           )");
            sql.addSql("       )");
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   coalesce(PTS_SORT, 0) asc, ");
            sql.addSql("   PTL_SID asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PtlPortalModel model = new PtlPortalModel();
                model.setPtlSid(rs.getInt("PTL_SID"));
                model.setPtlName(rs.getString("PTL_NAME"));
                model.setPtlDescription(rs.getString("PTL_DESCRIPTION"));
                model.setPtsSort(rs.getInt("PTS_SORT"));
                ret.add(model);
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
     * <br>[機  能] 共通のポータルの並び順を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return List in PTL_PORTALModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<PtlPortalModel> selectForPublic(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalModel> ret = new ArrayList<PtlPortalModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_PORTAL.PTL_SID,");
            sql.addSql("   PTL_PORTAL.PTL_NAME,");
            sql.addSql("   PTL_PORTAL.PTL_DESCRIPTION,");
            sql.addSql("   PORTAL_SORT.PTS_SORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL");
            sql.addSql("       left join (");
            sql.addSql("           select * from PTL_PORTAL_SORT");
            sql.addSql("           where PTS_KBN = 0");
            sql.addSql("       ) PORTAL_SORT");
            sql.addSql("       on");
            sql.addSql("           PTL_PORTAL.PTL_SID = PORTAL_SORT.PTL_SID");
            sql.addSql(" where ");
            sql.addSql("   PTL_PORTAL.PTL_OPEN = 0");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("     PTL_ACCESS = 0");
            sql.addSql("    or ( ");
            sql.addSql("       PTL_ACCESS = 1 ");
            sql.addSql("       and PTL_PORTAL.PTL_SID in (");
            sql.addSql("           select ");
            sql.addSql("               PTL_SID ");
            sql.addSql("           from ");
            sql.addSql("               PTL_PORTAL_CONF_READ");
            sql.addSql("           where ( ");
            sql.addSql("            PTL_PORTAL_CONF_READ.USR_SID = ?");
            sql.addSql("            or PTL_PORTAL_CONF_READ.GRP_SID in ( ");
            sql.addSql("                    select");
            sql.addSql("                      GRP_SID");
            sql.addSql("                    from");
            sql.addSql("                      CMN_BELONGM");
            sql.addSql("                    where");
            sql.addSql("                      USR_SID = ?");
            sql.addSql("            )");
            sql.addSql("           )");
            sql.addSql("           )");
            sql.addSql("       )");
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   coalesce(PTS_SORT, 0) asc, ");
            sql.addSql("   PTL_SID asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PtlPortalModel model = new PtlPortalModel();
                model.setPtlSid(rs.getInt("PTL_SID"));
                model.setPtlName(rs.getString("PTL_NAME"));
                model.setPtlDescription(rs.getString("PTL_DESCRIPTION"));
                model.setPtsSort(rs.getInt("PTS_SORT"));
                ret.add(model);
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
     * <p>Create PTL_PORTAL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortalModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortalModel __getPtlPortalFromRs(ResultSet rs) throws SQLException {
        PtlPortalModel bean = new PtlPortalModel();
        bean.setPtlSid(rs.getInt("PTL_SID"));
        bean.setPtlName(rs.getString("PTL_NAME"));
        bean.setPtlDescription(rs.getString("PTL_DESCRIPTION"));
        bean.setPtlOpen(rs.getInt("PTL_OPEN"));
        bean.setPtlAccess(rs.getInt("PTL_ACCESS"));
        bean.setPtlAuid(rs.getInt("PTL_AUID"));
        bean.setPtlAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTL_ADATE")));
        bean.setPtlEuid(rs.getInt("PTL_EUID"));
        bean.setPtlEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTL_EDATE")));
        return bean;
    }
}
