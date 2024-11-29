package jp.groupsession.v2.convert.convert253.dao;

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
 * <br>[解  説] v2.5.3へコンバートする際に使用する
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

        //新規テーブルのcreate、insertを行う
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

        //掲示板フォーラムメンバテーブル変更
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_MEM rename to BBS_FOR_MEM_CONV253");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table BBS_FOR_MEM");
        sql.addSql(" (");
        sql.addSql("         BFI_SID        integer not null,");
        sql.addSql("         USR_SID        integer not null,");
        sql.addSql("         GRP_SID        integer not null,");
        sql.addSql("         primary key (BFI_SID, USR_SID, GRP_SID)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into BBS_FOR_MEM");
        sql.addSql(" (");
        sql.addSql("   BFI_SID,");
        sql.addSql("   USR_SID,");
        sql.addSql("   GRP_SID");
        sql.addSql(" )");
        sql.addSql(" select");
        sql.addSql("   BFI_SID,");
        sql.addSql("   USR_SID,");
        sql.addSql("   -1");
        sql.addSql(" from BBS_FOR_MEM_CONV253");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop table BBS_FOR_MEM_CONV253");
        sqlList.add(sql);


        return sqlList;
    }
}