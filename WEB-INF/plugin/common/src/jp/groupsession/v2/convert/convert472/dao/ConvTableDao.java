package jp.groupsession.v2.convert.convert472.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.fil.GSConstFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.7.2へコンバートする際に使用する
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

        // アンケート 初期ファイル選択フィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_PRI_CONF");
        sql.addSql(" add");
        sql.addSql("   EPC_FOLDER_SELECT integer;");
        sqlList.add(sql);

        // アンケート 回答可能追加フィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_PRI_CONF");
        sql.addSql(" add");
        sql.addSql("   EPC_CAN_ANSWER integer;");
        sqlList.add(sql);

        // アンケート 初期ファイル選択フィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_ADM_CONF");
        sql.addSql(" add");
        sql.addSql("   EAC_FOLDER_USE  integer;");
        sqlList.add(sql);

        // アンケート 初期ファイル選択フィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_ADM_CONF");
        sql.addSql(" add");
        sql.addSql("   EAC_FOLDER_SELECT integer;");
        sqlList.add(sql);

        // アンケート 回答可能フィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_ADM_CONF");
        sql.addSql(" add");
        sql.addSql("   EAC_CAN_ANSWER integer;");
        sqlList.add(sql);

        // ユーザ情報 都道府県コード-1→0へ
        sql = new SqlBuffer();
        sql.addSql(" UPDATE CMN_USRM_INF");
        sql.addSql(" SET");
        sql.addSql(" TDF_SID = 0 WHERE TDF_SID = -1;");
        sqlList.add(sql);

        //施設予約 公開区分の追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_YRK ");
        sql.addSql(" add ");
        sql.addSql(" RSY_PUBLIC integer default 0 not null ");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //施設予約拡張情報 公開区分の追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_RYRK ");
        sql.addSql(" add ");
        sql.addSql(" RSR_PUBLIC integer default 0 ");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //施設予約 管理者設定 公開区分初期値の追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF ");
        sql.addSql(" add ");
        sql.addSql(" RAC_INI_PUBLIC_KBN integer default 0 not null ");
        sql.addSql(";");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF ");
        sql.addSql(" add ");
        sql.addSql(" RAC_INI_PUBLIC integer default 0 not null ");
        sql.addSql(";");
        sqlList.add(sql);

        //施設予約 個人設定 公開区分初期値の追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER ");
        sql.addSql(" add ");
        sql.addSql(" RSU_INI_PUBLIC integer default 0 not null ");
        sql.addSql(";");
        sqlList.add(sql);

        //施設予約 担当者名部分の長さを変更
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_KYRK alter RKY_NAME type varchar(70);");
        sqlList.add(sql);

        //WEBメール アカウントに[TOPコマンドの有効/無効]を設定する機能を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT ");
        sql.addSql(" add ");
        sql.addSql(" WAC_TOP_CMD integer default 0 not null ");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //メイン 管理者設定 外部ページ表示制限設定テーブルの追加
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_EXT_PAGE ");
        sql.addSql(" ( ");
        sql.addSql("   CEP_LIMIT_DSP  integer not null,   ");
        sql.addSql("   CEP_AUID  integer not null,   ");
        sql.addSql("   CEP_ADATE  timestamp not null,   ");
        sql.addSql("   CEP_EUID  integer not null,   ");
        sql.addSql("   CEP_EDATE  timestamp not null   ");
        sql.addSql(" ) ");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //メイン 管理者設定 GS内にページの表示を許可する外部ドメイン
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PERMITTED_DOMAIN ");
        sql.addSql(" ( ");
        sql.addSql("   CPD_EXT_DOMAIN  varchar(300),   ");
        sql.addSql("   CPD_AUID  integer,   ");
        sql.addSql("   CPD_ADATE  timestamp,   ");
        sql.addSql("   CPD_EUID  integer,   ");
        sql.addSql("   CPD_EDATE  timestamp   ");
        sql.addSql(" ) ");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //ショートメール ショートメール詳細(受信) インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index SML_JMEIS_INDEX2 on SML_JMEIS(SAC_SID);");
        sqlList.add(sql);

        //掲示板 スレッド閲覧情報 インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index BBS_THRE_VIEW_INDEX2 on BBS_THRE_VIEW(BFI_SID);");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ロック無効の場合、ファイルロックフラグを全て解除する
        sql = new SqlBuffer();
        sql.addSql(" update FILE_FILE_BIN ");
        sql.addSql(" set ");
        sql.addSql("   FFL_LOCK_KBN = " + GSConstFile.LOCK_KBN_OFF);
        sql.addSql(" where");
        sql.addSql("   FFL_LOCK_KBN = " + GSConstFile.LOCK_KBN_ON);
        sql.addSql("   AND EXISTS (");
        sql.addSql("     SELECT ");
        sql.addSql("       1 ");
        sql.addSql("     FROM ");
        sql.addSql("       FILE_ACONF ");
        sql.addSql("     WHERE ");
        sql.addSql("       FAC_LOCK_KBN = " + GSConstFile.LOCK_KBN_OFF);
        sql.addSql("   )");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //WEBメール アカウントに[アカウントID]を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT ");
        sql.addSql(" add ");
        sql.addSql(" WAC_ACCOUNT_ID varchar(100)");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //WEBメール [アカウントID]にアカウントSIDをコピー(数値→文字列へ変換)
        sql = new SqlBuffer();
        sql.addSql(" update WML_ACCOUNT ");
        sql.addSql(" set ");
        sql.addSql(" WAC_ACCOUNT_ID = CAST(WAC_SID as varchar(100)) ");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //WEBメール アカウントの[アカウントID]を再定義(not null を追加)
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT ");
        sql.addSql(" ALTER ");
        sql.addSql(" WAC_ACCOUNT_ID set not null");
        sql.addSql(" ; ");
        sqlList.add(sql);

        //回覧板 回覧板閲覧情報 インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index CIR_VIEW_INDEX1 on CIR_VIEW(CAC_SID);");
        sqlList.add(sql);

        //施設予約 施設予約情報 インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index RSV_SIS_YRK_INDEX3 on RSV_SIS_YRK(RSY_APPR_STATUS);");
        sqlList.add(sql);

        //ファイル管理 キャビネット情報 [個人キャビネット]フラグを追加
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_CABINET add FCB_PERSONAL_FLG integer not null default 0;");
        sql.addSql(" alter table FILE_CABINET add FCB_OWNER_SID integer;");
        sqlList.add(sql);

        //ファイル管理 管理者設定情報に個人キャビネット設定を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_ACONF add FAC_PERSONAL_KBN integer default 0 not null;");
        sql.addSql(" alter table FILE_ACONF add FAC_USE_KBN integer default 0 not null;");
        sql.addSql(" alter table FILE_ACONF add FAC_PERSONAL_CAPA integer default 0 not null;");
        sql.addSql(" alter table FILE_ACONF add FAC_PERSONAL_SIZE integer;");
        sql.addSql(" alter table FILE_ACONF add FAC_PERSONAL_WARN integer;");
        sql.addSql(" alter table FILE_ACONF add FAC_PERSONAL_VER integer default 0 not null;");
        sql.addSql(" alter table FILE_ACONF add FAC_PERSONAL_EDATE timestamp;");
        sqlList.add(sql);

        //ファイル管理 個人キャビネット許可ユーザテーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_PCB_OWNER(");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   USR_KBN integer not null,");
        sql.addSql("   PRIMARY KEY(USR_SID, USR_KBN)");
        sql.addSql(" );");
        sqlList.add(sql);

        return sqlList;
    }
}