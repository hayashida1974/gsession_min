package jp.groupsession.v2.convert.convert561.dao;

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

public class ConvTableDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * コンストラクタ
     * @param con
     */
    public ConvTableDao(Connection con) {
        super(con);
    }
    /**
     * コンバートを行う
     * @param saiban
     * @throws SQLException
    */
    public void convert(MlCountMtController saiban) throws SQLException {
        log__.debug("-- DBコンバート開始 --");
        //create Table or alter table
        createTable(saiban);
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

        //パスワードルール設定に大文字小文字混在区分を追加
        __addSql(sqlList,
            "alter table CMN_PSWD_CONF add PWC_UPPERCASE integer not null default 0;");

        //IP指定部分の文字数制限を撤廃
        __addSql(sqlList, "alter table API_CONF alter column APC_TOAKEN_IP type text;");
        __addSql(sqlList, "alter table API_CONF alter column APC_BASIC_IP type text;");
        __addSql(sqlList, "alter table CMN_FIREWALL_CONF alter column CFC_ALLOW_IP type text;");
        __addSql(sqlList, "alter table CMN_OTP_CONF alter column COC_OTP_IP type text;");

        //GSモバイルアプリ経由の外部アクセスに「使用可能プラグインの制限」設定を追加
        __addSql(sqlList, "alter table CMN_FIREWALL_CONF add CFC_ALLOW_MBL_PLUGIN integer not null default 0;");
        SqlBuffer sqlBuffer = new SqlBuffer();
        sqlBuffer.addSql("create table CMN_PLUGIN_MOBILE_PERMISSION");
        sqlBuffer.addSql("(");
        sqlBuffer.addSql("     PMP_PLUGIN    varchar(10)  not null,");
        sqlBuffer.addSql("     primary key (PMP_PLUGIN)");
        sqlBuffer.addSql(");");
        sqlList.add(sqlBuffer);

        return sqlList;
    }

    /**
     * <br>[機  能] 指定されたSQL文をSqlBufferに設定し、実行SQLリストへ追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList 実行SQLリスト
     * @param sql SQL文
     */
    private void __addSql(ArrayList<SqlBuffer> sqlList, String sql) {
        SqlBuffer sqlBuffer = new SqlBuffer();
        sqlBuffer.addSql(sql);
        sqlList.add(sqlBuffer);
    }

}
