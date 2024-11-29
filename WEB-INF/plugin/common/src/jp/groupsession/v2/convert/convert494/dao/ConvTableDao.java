package jp.groupsession.v2.convert.convert494.dao;

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
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.4へコンバートする際に使用する
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

        //
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {
        }

        //チャット 投稿集計情報の追加
        sql = new SqlBuffer();
        sql.addSql(" create table CHT_USER_DATA_SUM");
        sql.addSql(" (");
        sql.addSql("   CUP_SID  integer not null,");
        sql.addSql("   CUS_CNT  integer not null,");
        sql.addSql("   CUS_LASTSID bigint not null,");
        sql.addSql("   CUS_LASTDATE timestamp,");
        sql.addSql("   primary key(CUP_SID)");
        sql.addSql(" );");
        sql.addSql("     ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CHT_GROUP_DATA_SUM");
        sql.addSql(" (");
        sql.addSql("   CGI_SID  integer not null,");
        sql.addSql("   CGS_CNT  integer not null,");
        sql.addSql("   CGS_LASTSID bigint not null,");
        sql.addSql("   CGS_LASTDATE timestamp,");
        sql.addSql("   primary key(CGI_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index CHT_USER_DATA_SUM_INDEX on CHT_USER_DATA_SUM(CUS_LASTSID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index CHT_GROUP_DATA_SUM_INDEX on CHT_GROUP_DATA_SUM(CGS_LASTSID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CHT_USER_DATA_SUM(CUP_SID, CUS_CNT, CUS_LASTSID, CUS_LASTDATE)");
        sql.addSql(" select");
        sql.addSql("         CHT_USER_PAIR.CUP_SID,");
        sql.addSql("         count(CHT_USER_DATA.CUP_SID),");
        sql.addSql("         max(coalesce(CHT_USER_DATA.CUD_SID,0)),");
        sql.addSql("         max(CHT_USER_DATA.CUD_ADATE)");
        sql.addSql("     from");
        sql.addSql("         CHT_USER_DATA");
        sql.addSql("         right join CHT_USER_PAIR");
        sql.addSql("           on CHT_USER_DATA.CUP_SID = CHT_USER_PAIR.CUP_SID");
        sql.addSql("         ");
        sql.addSql(" GROUP BY CHT_USER_PAIR.CUP_SID;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CHT_GROUP_DATA_SUM(CGI_SID, CGS_CNT, CGS_LASTSID, CGS_LASTDATE)");
        sql.addSql(" select");
        sql.addSql("         CHT_GROUP_INF.CGI_SID,");
        sql.addSql("         count(CHT_GROUP_DATA.CGD_SID),");
        sql.addSql("         max(coalesce(CHT_GROUP_DATA.CGD_SID, 0)),");
        sql.addSql("         max(CHT_GROUP_DATA.CGD_ADATE)");
        sql.addSql("     from");
        sql.addSql("         CHT_GROUP_DATA");
        sql.addSql("         right join CHT_GROUP_INF");
        sql.addSql("           on CHT_GROUP_DATA.CGI_SID = CHT_GROUP_INF.CGI_SID");
        sql.addSql(" GROUP BY CHT_GROUP_INF.CGI_SID;");
        sqlList.add(sql);

        //チャット 閲覧情報 閲覧数情報の追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CHT_USER_VIEW add CUV_VIEWCNT integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CHT_GROUP_VIEW add CGV_VIEWCNT integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("    CHT_USER_VIEW");
        sql.addSql(" set ");
        sql.addSql("    CUV_VIEWCNT=(");
        sql.addSql("      select");
        sql.addSql("       count(1)");
        sql.addSql("      from CHT_USER_DATA");
        sql.addSql("      where CHT_USER_DATA.CUP_SID=CHT_USER_VIEW.CUP_SID");
        sql.addSql("         and CHT_USER_DATA.CUD_SID <= CHT_USER_VIEW.CUD_SID)");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("    CHT_GROUP_VIEW");
        sql.addSql(" set ");
        sql.addSql("    CGV_VIEWCNT=(");
        sql.addSql("      select");
        sql.addSql("       count(1)");
        sql.addSql("      from CHT_GROUP_DATA");
        sql.addSql("      where CHT_GROUP_DATA.CGI_SID=CHT_GROUP_VIEW.CGI_SID");
        sql.addSql("         and CHT_GROUP_DATA.CGD_SID <= CHT_GROUP_VIEW.CGD_SID)");
        sqlList.add(sql);

        return sqlList;
    }




}
