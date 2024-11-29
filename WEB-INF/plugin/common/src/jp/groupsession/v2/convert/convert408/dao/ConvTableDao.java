package jp.groupsession.v2.convert.convert408.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.0.8へのコンバートで使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @throws SQLException 例外
     */
    public void convert() throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //create Table or alter table
        createTable();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL();

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL() throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;

        //施設グループ、施設情報に施設予約の承認に関するフィールドを追加
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_GRP add RSG_APPR_KBN     integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_DATA add RSD_APPR_KBN     integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_DATA add RSD_APPR_KBN_DF  integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();

        //施設予約情報に承認に関するフィールドを追加
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_YRK add RSY_APPR_STATUS integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_YRK add RSY_APPR_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_YRK add RSY_APPR_UID integer;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_YRK add RSY_APPR_DATE timestamp;");
        sqlList.add(sql);

        return sqlList;
    }
}