package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

/**
 * <p>CMN_POSITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPositionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPositionDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPositionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPositionDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 役職情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_POSITION Data Bindding JavaBean
     * @return int 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insertPos(CmnPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_POSITION(");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
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

            sql.addIntValue(bean.getPosSid());
            sql.addStrValue(bean.getPosCode());
            sql.addStrValue(bean.getPosName());
            sql.addStrValue(bean.getPosBiko());
            sql.addIntValue(bean.getPosSort());
            sql.addIntValue(bean.getPosAuid());
            sql.addDateValue(bean.getPosAdate());
            sql.addIntValue(bean.getPosEuid());
            sql.addDateValue(bean.getPosEdate());

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
     * <br>[機  能] 役職情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_POSITION Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePos(CmnPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" set ");
            sql.addSql("   POS_NAME = ?,");
            sql.addSql("   POS_CODE = ?,");
            sql.addSql("   POS_BIKO = ?,");
            sql.addSql("   POS_EUID = ?,");
            sql.addSql("   POS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   POS_SID = ?");

            sql.addStrValue(bean.getPosName());
            sql.addStrValue(bean.getPosCode());
            sql.addStrValue(bean.getPosBiko());
            sql.addIntValue(bean.getPosEuid());
            sql.addDateValue(bean.getPosEdate());
            //where
            sql.addIntValue(bean.getPosSid());

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
     * <br>[機  能] 役職情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_POSITION Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePosition(CmnPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" set ");
            sql.addSql("   POS_NAME = ?,");
            sql.addSql("   POS_CODE = ?,");
            sql.addSql("   POS_BIKO = ?,");
            sql.addSql("   POS_SORT = ?,");
            sql.addSql("   POS_EUID = ?,");
            sql.addSql("   POS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   POS_SID = ?");

            sql.addStrValue(bean.getPosName());
            sql.addStrValue(bean.getPosCode());
            sql.addStrValue(bean.getPosBiko());
            sql.addIntValue(bean.getPosSort());
            sql.addIntValue(bean.getPosEuid());
            sql.addDateValue(bean.getPosEdate());
            //where
            sql.addIntValue(bean.getPosSid());

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
     * <br>[機  能] 役職表示順を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid ポジションSID
     * @param posSort 表示順
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePosSort(int posSid, int posSort) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" set ");
            sql.addSql("   POS_SORT = ?");
            sql.addSql(" where ");
            sql.addSql("   POS_SID = ?");

            sql.addIntValue(posSort);
            //where
            sql.addIntValue(posSid);

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
     * <br>[機  能] 役職一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param allFlg 全取得フラグ true=全て取得、false=未設定レコードを除く
     * @return List in CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPositionModel> getPosList(boolean allFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPositionModel> ret = new ArrayList<CmnPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION");

            if (!allFlg) {
                //未設定用レコードを除く
                sql.addSql(" where ");
                sql.addSql("   POS_SID != ?");
                sql.addIntValue(GSConst.POS_DEFAULT);
            }

            sql.addSql(" order by ");
            sql.addSql("   POS_SORT asc");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnPositionFromRs(rs));
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
     * <br>[機  能] 役職一覧を取得する
     * <br>[解  説] ソート順（）
     * <br>[備  考]
     * @param allFlg 全取得フラグ true=全て取得、false=未設定レコードを除く
     * @return List in CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPositionModel> getPosListSort(boolean allFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPositionModel> ret = new ArrayList<CmnPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION");

            if (!allFlg) {
                //未設定用レコードを除く
                sql.addSql(" where ");
                sql.addSql("   POS_SID != ?");
                sql.addIntValue(GSConst.POS_DEFAULT);
            }

            sql.addSql(" order by ");
            sql.addSql("   POS_SORT asc,");
            sql.addSql("   POS_EDATE desc,");
            sql.addSql("   POS_SID asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnPositionFromRs(rs));
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
     * <br>[機  能] SIDで指定した役職を除いた役職一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList 除外対象SID
     * @return List in CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPositionModel> getPosList(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPositionModel> ret = new ArrayList<CmnPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION");
            if (sidList.size() > 0) {
                sql.addSql(" where ");
                sql.addSql("   POS_SID not in (");
                boolean firstFlg = true;
                for (int sid : sidList) {
                    if (!firstFlg) {
                        sql.addSql("     ,");
                    } else {
                        firstFlg = false;
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(sid);
                }
                sql.addSql("   )");
            }
            sql.addSql(" order by ");
            sql.addSql("   POS_SORT asc,");
            sql.addSql("   POS_EDATE desc,");
            sql.addSql("   POS_SID asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnPositionFromRs(rs));
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
     * <br>[機  能] SIDで指定した役職一覧を取得する
     * <br>[解  説] ソート順（）
     * <br>[備  考]
     * @param sidList SIDリスト
     * @return List in CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPositionModel> getPosListSort(String[] sidList) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPositionModel> ret = new ArrayList<CmnPositionModel>();
        if (sidList == null || sidList.length == 0) {
            return ret;
        }
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION");

            sql.addSql(" where ");
            sql.addSql("   POS_SID in (");
            for (int i = 0; i < sidList.length; i++) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql(sidList[i]);
            }
            sql.addSql("   )");

            sql.addSql(" order by ");
            sql.addSql("   POS_SORT asc,");
            sql.addSql("   POS_EDATE desc,");
            sql.addSql("   POS_SID asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnPositionFromRs(rs));
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
     * <br>[機  能] 役職名称から該当件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posName 役職名称
     * @return int 該当件数
     * @throws SQLException SQL実行例外
     */
    public int getPosCount(String posName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(POS_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where");
            sql.addSql("   POS_NAME = ?");

            sql.addStrValue(posName);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
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
     * <br>[機  能] 役職SIDから役職情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @return CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public CmnPositionModel getPosInfo(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_SID = ?");

            sql.addIntValue(posSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnPositionFromRs(rs);
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
     * <br>[機  能] 役職名から役職情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posName 役職名
     * @return CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public CmnPositionModel getPosInfo(String posName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_NAME = ?");

            sql.addStrValue(posName);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnPositionFromRs(rs);
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
     * <br>[機  能] 役職コードから役職情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posCode 役職コード
     * @return CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public CmnPositionModel getPosInfoFromCode(String posCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_CODE = ?");

            sql.addStrValue(posCode);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnPositionFromRs(rs);
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
     * <br>[機  能] 役職コードが存在するか確認する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param posCode 検索する役職コード
     * @param posSid 除外する役職SID(自分自身など)
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistPositionCode(String posCode, int posSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(POS_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_CODE = ?");

            sql.addStrValue(posCode);

            if (posSid > 0) {
                sql.addSql(" and");
                sql.addSql("   POS_SID <> ?");
                sql.addIntValue(posSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
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
     * <br>[機  能] 役職名を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param posCode 検索する役職コード
     * @return 役職名
     * @throws SQLException SQL実行例外
     */
    public String getPositionName(String posCode)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        String posName = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   POS_NAME");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_CODE = ?");
            sql.addStrValue(posCode);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                posName = rs.getString("POS_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return posName;
    }

    /**
     * <br>[機  能] 役職名が存在するか確認する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param posName 検索する役職名
     * @param posSid 除外する役職SID(自分自身など)
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistPositionName(String posName, int posSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(POS_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_NAME = ?");

            sql.addStrValue(posName);

            if (posSid > 0) {
                sql.addSql(" and");
                sql.addSql("   POS_SID <> ?");
                sql.addIntValue(posSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
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
     * <br>[機  能] 役職SIDが最大の役職を取得する
     * <br>[解  説] 最後に追加した役職を取得
     * <br>[備  考]
     * @return CMN_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public CmnPositionModel getLastPos() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_CODE,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" order by ");
            sql.addSql("   POS_SID desc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                //最初の1件のみ取得
                ret = __getCmnPositionFromRs(rs);
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
     * <br>[機  能] 役職SIDを指定して、役職情報を物理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deletePos(int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   POS_SID = ?");

            sql.addIntValue(posSid);

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
     * <p>Create CMN_POSITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPositionModel
     * @throws SQLException SQL実行例外
     */
    private CmnPositionModel __getCmnPositionFromRs(ResultSet rs) throws SQLException {
        CmnPositionModel bean = new CmnPositionModel();
        bean.setPosSid(rs.getInt("POS_SID"));
        bean.setPosCode(rs.getString("POS_CODE"));
        bean.setPosName(rs.getString("POS_NAME"));
        bean.setPosBiko(rs.getString("POS_BIKO"));
        bean.setPosSort(rs.getInt("POS_SORT"));
        bean.setPosAuid(rs.getInt("POS_AUID"));
        bean.setPosAdate(UDate.getInstanceTimestamp(rs.getTimestamp("POS_ADATE")));
        bean.setPosEuid(rs.getInt("POS_EUID"));
        bean.setPosEdate(UDate.getInstanceTimestamp(rs.getTimestamp("POS_EDATE")));
        return bean;
    }
}
