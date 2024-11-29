package jp.groupsession.v2.convert.convert481.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.7.1へコンバートする際に使用する
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
     * @param saiban 採番用コントローラー
     * @throws SQLException 例外
     */
    public void convert(
            MlCountMtController saiban) throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //create Table or alter table
        createTable(saiban);

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @throws SQLException SQL実行例外
     */
    public void createTable(
            MlCountMtController saiban) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL(saiban);

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
     * @param saiban 採番コントローラー
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL(
            MlCountMtController saiban) throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;

        // WEBメール 通知管理テーブルを作成
        sql = new SqlBuffer();
        sql.addSql(" create table");
        sql.addSql("   WML_MANAGE_NOTICE");
        sql.addSql("     (");
        sql.addSql("       WAC_SID integer not null,");
        sql.addSql("       WMN_RCVFAILED_FLG integer not null,");
        sql.addSql("       primary key(WAC_SID)");
        sql.addSql("     )");
        sql.addSql("     ;");
        sqlList.add(sql);

        // WEBメール 利用可能な全アカウント分のレコードを通知管理テーブルへ追加
        sql = new SqlBuffer();
        sql.addSql(" insert into WML_MANAGE_NOTICE");
        sql.addSql("   (");
        sql.addSql("     WAC_SID,");
        sql.addSql("     WMN_RCVFAILED_FLG");
        sql.addSql("   )");
        sql.addSql("   select");
        sql.addSql("     WAC_SID,");
        sql.addSql("     0 ");
        sql.addSql("   from");
        sql.addSql("     WML_ACCOUNT");
        sql.addSql("   where");
        sql.addSql("     WAC_JKBN=0");
        sql.addSql("     ;");
        sqlList.add(sql);

        //稟議一覧検索用インデクスの追加
        sql = new SqlBuffer();
        sql.addSql(" create index RNG_SINGI2 on RNG_SINGI(USR_SID);");
        sqlList.add(sql);

        return sqlList;
    }
}
