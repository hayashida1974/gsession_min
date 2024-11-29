package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrmLabelModel;

/**
 * <p>CMN_USRM_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrmLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrmLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrmLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrmLabelDao(Connection con) {
        super(con);
    }


    /**
     * <p>Insert CMN_USRM_LABEL Data Bindding JavaBean
     * @param bean CMN_USRM_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrmLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USRM_LABEL(");
            sql.addSql("   USR_SID,");
            sql.addSql("   LAB_SID,");
            sql.addSql("   USL_AUID,");
            sql.addSql("   USL_ADATE,");
            sql.addSql("   USL_EUID,");
            sql.addSql("   USL_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getLabSid());
            sql.addIntValue(bean.getUslAuid());
            sql.addDateValue(bean.getUslAdate());
            sql.addIntValue(bean.getUslEuid());
            sql.addDateValue(bean.getUslEdate());
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
     * <br>[機  能] 指定したユーザSIDに設定されているラベルSID一覧を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql("   from");
            sql.addSql("     CMN_USRM_LABEL");
            sql.addSql("   where");
            sql.addSql("     USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }



    /**
     * <br>[機  能] 指定したラベルを指定したユーザに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CmnUsrmLabelModel
     * @throws SQLException SQL実行例外
     */
    public void insertLabelMulti(CmnUsrmLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            String[] checkSid = getLabListBelongUsr(bean.getUsrSid());
            boolean checkFlg = false;
            for (String str : checkSid) {
                if (bean.getLabSid() == Integer.parseInt(str) && checkFlg == false) {
                    checkFlg = true;
                }
            }
            if (checkFlg == false) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CMN_USRM_LABEL(");
                sql.addSql("   USR_SID,");
                sql.addSql("   LAB_SID,");
                sql.addSql("   USL_AUID,");
                sql.addSql("   USL_ADATE,");
                sql.addSql("   USL_EUID,");
                sql.addSql("   USL_EDATE");
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
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getLabSid());
                sql.addIntValue(bean.getUslAuid());
                sql.addDateValue(bean.getUslAdate());
                sql.addIntValue(bean.getUslEuid());
                sql.addDateValue(bean.getUslEdate());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したラベルSIDに付与されているユーザ情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param labSid ラベルSID
     * @return int アドレス帳の件数
     * @throws SQLException SQL実行例外
     */
    public int getIndCount(int labSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(USR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_LABEL");
            sql.addSql(" where");
            sql.addSql("   LAB_SID = ?");

            sql.addIntValue(labSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したラベルSIDに付与されているユーザ情報のSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param labSid ラベルSID
     * @return int アドレス帳の件数
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getLabelUser(int labSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_LABEL");
            sql.addSql(" where");
            sql.addSql("   LAB_SID = ?");

            sql.addIntValue(labSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] 指定したユーザSIDに設定されているラベルSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return int[] ラベルSID一覧
     * @throws SQLException SQL実行例外
     */
    public String[] getLabListBelongUsr(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<String> labelSidList = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LAB_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_LABEL");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                labelSidList.add(rs.getString("LAB_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return labelSidList.toArray(new String[labelSidList.size()]);
    }
    /**
     * <br>[機  能] 指定したユーザSIDに設定されているラベルSIDMapを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sids ユーザSID
     * @return Map<Integer, Set<Integer> ユーザSID、ラベルSIDマップ
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Set<Integer>> getLabListBelongUsrMap(
            Set<Integer> sids) throws SQLException {
        Connection con = null;
        Map<Integer, Set<Integer>> ret = new HashMap<>();
        if (sids == null || sids.isEmpty()) {
            return ret;
        }

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = sids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   USR_SID,");
        sb.append("   LAB_SID");
        sb.append(" from");
        sb.append("   CMN_USRM_LABEL");
        sb.append(" where");

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
            sql.addSql(" USR_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addIntValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {
                    while (rs.next()) {
                        int usrSid = rs.getInt("USR_SID");
                        if (!ret.containsKey(usrSid)) {
                            ret.put(usrSid, new HashSet<>());
                        }
                        ret.get(usrSid).add(rs.getInt("LAB_SID"));
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }
     /**
     * <br>[機  能] 指定したカテゴリSIDに格納されているラベルのうち、
     *              ユーザ情報に付与されている件数をラベルSIDごとに取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param catSid ラベルSID
     * @return ユーザ情報ごとのラベル付与件数
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmLabelModel> getCountLabBelongCat(int catSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<CmnUsrmLabelModel> modelList = new ArrayList<CmnUsrmLabelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    LAB_SID,");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from CMN_USRM_LABEL");
            sql.addSql("  where LAB_SID in (");
            sql.addSql("    select LAB_SID from CMN_LABEL_USR");
            sql.addSql("    where LUC_SID = ? )");
            sql.addSql("  group by");
            sql.addSql("    LAB_SID");
            sql.addIntValue(catSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnUsrmLabelModel model = new CmnUsrmLabelModel();
                model.setUsrSid(rs.getInt("LAB_SID"));
                model.setCount(rs.getInt("CNT"));
                modelList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return modelList;
    }

    /**
     * <p>Select CmnUsrmLabel All Data
     * @return List in CmnUsrmLabelModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmLabelModel> ret = new ArrayList<CmnUsrmLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   LAB_SID,");
            sql.addSql("   USL_AUID,");
            sql.addSql("   USL_ADATE,");
            sql.addSql("   USL_EUID,");
            sql.addSql("   USL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrmLabelFromRs(rs));
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
     * <p>Create CmnUsrmLabel Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmLabelModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrmLabelModel __getCmnUsrmLabelFromRs(ResultSet rs) throws SQLException {
        CmnUsrmLabelModel bean = new CmnUsrmLabelModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setLabSid(rs.getInt("LAB_SID"));
        bean.setUslAuid(rs.getInt("USL_AUID"));
        bean.setUslAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USL_ADATE")));
        bean.setUslEuid(rs.getInt("USL_EUID"));
        bean.setUslEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USL_EDATE")));
        return bean;
    }
}
