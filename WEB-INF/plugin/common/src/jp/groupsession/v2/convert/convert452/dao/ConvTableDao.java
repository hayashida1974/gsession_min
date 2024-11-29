package jp.groupsession.v2.convert.convert452.dao;

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
 * <br>[解  説] v4.5.2へコンバートする際に使用する
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

        //スケジュール スケジュール特例アクセス_許可対象 権限区分
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_SPACCESS_PERMIT add SSP_AUTH integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update SCH_SPACCESS_PERMIT set SSP_AUTH = 1;");
        sqlList.add(sql);

        //ショートメールアカウント 自動送信先設定
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ACCOUNT_AUTODEST");
        sql.addSql(" (");
        sql.addSql(" SAC_SID        integer         not null,");
        sql.addSql(" SAA_TYPE integer         not null,");
        sql.addSql(" SAA_SID integer         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 管理者設定 最大表示件数 設定種別
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_MAX_DSP_STYPE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 最大表示件数
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_MAX_DSP integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 自動リロード時間 設定種別
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_RELOAD_STYPE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 自動リロード時間
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_RELOAD integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 写真表示 設定種別
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_PHOTO_DSP_STYPE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 写真表示
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_PHOTO_DSP integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 添付画像表示 設定種別
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_ATTACH_DSP_STYPE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 添付画像表示
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_ATTACH_DSP integer not null default 0;");
        sqlList.add(sql);

        return sqlList;
    }
}