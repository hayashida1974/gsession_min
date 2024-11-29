package jp.groupsession.v2.convert.convert470.dao;

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
import jp.groupsession.v2.cmn.GSConstBBS;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.7.0へコンバートする際に使用する
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
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

        //WEBメール アカウント_受信情報
        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_RCVDATA");
        sql.addSql(" (");
        sql.addSql("   WAC_SID            integer      not null,");
        sql.addSql("   WRD_RECEIVE_DATE   timestamp,");
        sql.addSql("   primary key (WAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into WML_ACCOUNT_RCVDATA");
        sql.addSql(" select WAC_SID, WAC_RECEIVE_DATE from WML_ACCOUNT;");
        sqlList.add(sql);

        //掲示板のフォーラム情報に時間単位を追加
        sql = new SqlBuffer();
        sql.addSql("alter table BBS_FOR_INF add BFI_MIN_DIV integer default ");
        sql.addSql(" '" + GSConstBBS.MINUTE_DIVISION5 + "' ");
        sql.addSql(";");
        sqlList.add(sql);

        //予約投稿されたスレッドとそのスレッドが投稿されたフォーラムのSIDを保持するテーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_THRE_RSV( ");
        sql.addSql(" BFI_SID integer not null, ");
        sql.addSql(" BTI_SID integer not null ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //BBS_THRE_RSVに、予約投稿されたスレッドのURLを保持するフィールドを追加
        sql = new SqlBuffer();
        sql.addSql("alter table BBS_THRE_RSV add BTR_URL varchar(3000) not null;");
        sqlList.add(sql);

        //日報特例アクセス用のテーブル作成
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_SPACCESS(");
        sql.addSql(" NSA_SID INTEGER not null primary key,");
        sql.addSql(" NSA_NAME VARCHAR not null,");
        sql.addSql(" NSA_BIKO VARCHAR,");
        sql.addSql(" NSA_AUID INTEGER,");
        sql.addSql(" NSA_ADATE TIMESTAMP,");
        sql.addSql(" NSA_EUID INTEGER,");
        sql.addSql(" NSA_EDATE TIMESTAMP");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table NTP_SPACCESS_TARGET(");
        sql.addSql(" NSA_SID INTEGER not null,");
        sql.addSql(" NST_TYPE INTEGER not null,");
        sql.addSql(" NST_TSID INTEGER not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table NTP_SPACCESS_PERMIT(");
        sql.addSql(" NSA_SID INTEGER not null,");
        sql.addSql(" NSP_TYPE INTEGER not null,");
        sql.addSql(" NSP_PSID INTEGER not null,");
        sql.addSql(" NSP_AUTH INTEGER not null");
        sql.addSql(" )");
        sqlList.add(sql);

        //NTP_ANKENに、案件の権限区分を保持するフィールドを追加
        sql = new SqlBuffer();
        sql.addSql("alter table NTP_ANKEN add NAN_PERMIT_VIEW integer default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table NTP_ANKEN add NAN_PERMIT_EDIT integer default 0;");
        sqlList.add(sql);

        //NTP_ANKEN＿PERMITテーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_ANKEN_PERMIT(");
        sql.addSql(" NAN_SID INTEGER not null,");
        sql.addSql(" USR_SID INTEGER not null,");
        sql.addSql(" GRP_SID INTEGER not null,");
        sql.addSql(" NAP_KBN INTEGER not null,");
        sql.addSql(" primary key (NAN_SID,USR_SID,GRP_SID,NAP_KBN) ");
        sql.addSql(" )");
        sqlList.add(sql);

        //削除済み案件に紐づく履歴を削除
        sql = new SqlBuffer();
        sql.addSql(" delete from NTP_AN_HISTORY where not exists");
        sql.addSql(" (select NAN_SID from NTP_ANKEN where");
        sql.addSql(" NTP_ANKEN.NAN_SID = NTP_AN_HISTORY.NAN_SID);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" delete from NTP_AN_SHOHIN_HISTORY where not exists");
        sql.addSql(" (select NAN_SID from NTP_ANKEN where");
        sql.addSql(" NTP_ANKEN.NAN_SID = NTP_AN_SHOHIN_HISTORY.NAN_SID);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" delete from NTP_AN_MEMBER where not exists");
        sql.addSql(" (select NAN_SID from NTP_ANKEN where");
        sql.addSql(" NTP_ANKEN.NAN_SID = NTP_AN_MEMBER.NAN_SID);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" delete from NTP_AN_MEMBER_HISTORY where not exists");
        sql.addSql(" (select NAN_SID from NTP_ANKEN where");
        sql.addSql(" NTP_ANKEN.NAN_SID = NTP_AN_MEMBER_HISTORY.NAN_SID);");
        sqlList.add(sql);

        //掲示板において、スレッドの掲示終了時間を23時59分に設定
        //H2
        if (h2db) {
            sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   BBS_THRE_INF ");
            sql.addSql(" set");
            sql.addSql("   BTI_LIMIT_DATE = dateadd(second, 86340, BTI_LIMIT_DATE) ");
            sql.addSql(" where");
            sql.addSql("   BTI_LIMIT_DATE = BTI_LIMIT_DATE");
            sql.addSql(" ;");
            sqlList.add(sql);
            //Postgres
        } else {
            sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   BBS_THRE_INF ");
            sql.addSql(" set");
            sql.addSql("   BTI_LIMIT_DATE = BTI_LIMIT_DATE + CAST('86340 second' AS INTERVAL) ");
            sql.addSql(" where");
            sql.addSql("   BTI_LIMIT_DATE = BTI_LIMIT_DATE");
            sql.addSql(" ;");
            sqlList.add(sql);
        }

        return sqlList;
    }
}