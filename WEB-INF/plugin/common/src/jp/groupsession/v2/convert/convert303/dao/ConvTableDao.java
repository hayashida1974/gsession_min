package jp.groupsession.v2.convert.convert303.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v3.0.3へコンバートする際に使用する
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
     * @param mtCon 採番コントローラ
     * @throws SQLException 例外
     */
    public void convert(MlCountMtController mtCon) throws SQLException {

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

        //共通 ログイン設定 新規追加
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_LOGIN_CONF");
        sql.addSql(" (");
        sql.addSql("   LLC_LOCKOUT_KBN integer not null,");
        sql.addSql("   LLC_FAIL_CNT integer,");
        sql.addSql("   LLC_FAIL_AGE integer,");
        sql.addSql("   LLC_LOCK_AGE integer,");
        sql.addSql("   LLC_AUID integer not null,");
        sql.addSql("   LLC_ADATE timestamp not null,");
        sql.addSql("   LLC_EUID integer not null,");
        sql.addSql("   LLC_EDATE timestamp not null");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        //掲示板 個人設定　既読投稿をメインに表示する設定を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_USER add BUR_MAIN_CHKED_DSP integer not null default 1");
        sqlList.add(sql);

        //ライセンス認証用にGSIDを追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_GS_UID varchar(50) ");
        sqlList.add(sql);

        return sqlList;
    }

}