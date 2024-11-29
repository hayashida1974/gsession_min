package jp.groupsession.v2.convert.convert451.dao;

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
 * <br>[解  説] v4.5.1へコンバートする際に使用する
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

        //メイン 管理者設定 個人情報編集権限設定
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PCONF_EDIT");
        sql.addSql(" (");
        sql.addSql("         CPE_PCONF_KBN    integer not null,");
        sql.addSql("         CPE_PASSWORD_KBN integer not null,");
        sql.addSql("         CPE_AUID         integer not null,");
        sql.addSql("         CPE_ADATE        timestamp not null,");
        sql.addSql("         CPE_EUID         integer not null,");
        sql.addSql("         CPE_EDATE        timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //スケジュール 管理者設定 同時修正設定 設定種別 初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_SAME_STYPE integer not null default 0;");
        sqlList.add(sql);

        //スケジュール 管理者設定 同時修正設定 初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_SAME integer not null default 1;");
        sqlList.add(sql);

        //スケジュール 個人設定 同時修正設定 初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_INI_SAME integer not null default 1;");
        sqlList.add(sql);

        return sqlList;
    }
}