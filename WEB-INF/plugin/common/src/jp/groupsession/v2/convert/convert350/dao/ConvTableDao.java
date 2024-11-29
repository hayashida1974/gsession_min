package jp.groupsession.v2.convert.convert350.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v3.5.0へコンバートする際に使用する
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

            //施設予約 INDEX追加
            __createReceiveIndex(con);

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

        //プロジェクトアイコンデータ
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_PRJDATA add BIN_SID integer not null default 0;");
        sqlList.add(sql);

        //施設予約グループID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_GRP rename to RSV_SIS_GRP_CONV350");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_SIS_GRP");
        sql.addSql(" (");
        sql.addSql("   RSG_SID       integer not null,");
        sql.addSql("   RSK_SID       integer not null,");
        sql.addSql("   RSG_ID        varchar(15) not null,");
        sql.addSql("   RSG_NAME      varchar(20) not null,");
        sql.addSql("   RSG_ADM_KBN   integer not null,");
        sql.addSql("   RSG_SORT      integer not null,");
        sql.addSql("   RSG_AUID      integer not null,");
        sql.addSql("   RSG_ADATE     timestamp not null,");
        sql.addSql("   RSG_EUID      integer not null,");
        sql.addSql("   RSG_EDATE     timestamp not null,");
        sql.addSql("   primary key (RSG_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into RSV_SIS_GRP");
        sql.addSql(" (");
        sql.addSql("   RSG_SID,");
        sql.addSql("   RSK_SID,");
        sql.addSql("   RSG_ID,");
        sql.addSql("   RSG_NAME,");
        sql.addSql("   RSG_ADM_KBN,");
        sql.addSql("   RSG_SORT,");
        sql.addSql("   RSG_AUID,");
        sql.addSql("   RSG_ADATE,");
        sql.addSql("   RSG_EUID,");
        sql.addSql("   RSG_EDATE");
        sql.addSql(" )");
        sql.addSql(" select");
        sql.addSql("   RSG_SID,");
        sql.addSql("   RSK_SID,");
        sql.addSql("   RSG_SID,");
        sql.addSql("   RSG_NAME,");
        sql.addSql("   RSG_ADM_KBN,");
        sql.addSql("   RSG_SORT,");
        sql.addSql("   RSG_AUID,");
        sql.addSql("   RSG_ADATE,");
        sql.addSql("   RSG_EUID,");
        sql.addSql("   RSG_EDATE");
        sql.addSql(" from RSV_SIS_GRP_CONV350");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop table RSV_SIS_GRP_CONV350");
        sqlList.add(sql);

        //施設予約 個人設定 画像表示切替設定追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER add RSU_IMG_DSP integer not null default 0;");
        sqlList.add(sql);

        //施設予約 施設画像データテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table RSV_BIN");
        sql.addSql(" (");
        sql.addSql("   RSD_SID       integer not null,");
        sql.addSql("   BIN_SID       integer not null,");
        sql.addSql("   RSD_IMG_KBN   integer not null,");
        sql.addSql("   RSD_DSP_KBN      integer not null,");
        sql.addSql("   primary key (RSD_SID, BIN_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //施設予約 施設情報「場所/地図画像コメント」フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT1 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT2 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT3 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT4 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT5 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT6 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT7 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT8 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT9 varchar(50);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT10 varchar(50);");
        sqlList.add(sql);

        //施設予約 施設情報「場所コメント」フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PLA_CMT varchar(50);");
        sqlList.add(sql);

        //施設予約 施設情報表示区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_ID_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_SNUM_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_1_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_2_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_3_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_4_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_5_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_6_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_7_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_8_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_9_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PROP_10_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT1_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT2_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT3_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT4_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT5_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT6_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT7_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT8_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT9_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_CMT10_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_BIKO_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_IMG_DF integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_DATA add RSD_PLA_CMT_DF integer not null default 1;");
        sqlList.add(sql);

        //施設予約 個人設定 初期表示画面追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER add RSU_INI_DSP integer not null default 0;");
        sqlList.add(sql);

        //施設予約 個人設定 メイン表示設定画面 表示区分追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_MAIN add RSM_DSP_KBN integer not null default 0;");
        sqlList.add(sql);

        //ショートメール転送設定のID/PASSのフィールドの定義変更
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN alter column SMA_SMTP_PASS type varchar(100);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN alter column SMA_SMTP_USER type varchar(100);");
        sqlList.add(sql);

        //プラグイン制限方法フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_PLUGIN_CONTROL add PCT_TYPE integer default 0;");
        sqlList.add(sql);

        //プラグイン管理者テーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PLUGIN_ADMIN");
        sql.addSql(" (");
        sql.addSql("   PCT_PID  varchar(10)  not null,");
        sql.addSql("   GRP_SID  integer      not null,");
        sql.addSql("   USR_SID  integer      not null,");
        sql.addSql("   primary key (PCT_PID, GRP_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール転送のSSL設定フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_SSL integer not null default 0;");
        sqlList.add(sql);

        //アドレス帳　閲覧・編集権限
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ACONF add AAC_VRM_EDIT integer not null default 0;");
        sqlList.add(sql);
        //アドレス帳　管理者設定閲覧区分
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ACONF add AAC_PVW_KBN integer not null default 0;");
        sqlList.add(sql);
        //アドレス帳　管理者設定編集区分
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ACONF add AAC_PET_KBN integer not null default 0;");
        sqlList.add(sql);
        //アドレス帳　個人設定閲覧区分
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_UCONF add AUC_PERMIT_VIEW integer not null default 0;");
        sqlList.add(sql);
        //アドレス帳　個人設定編集区分
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_UCONF add AUC_PERMIT_EDIT integer not null default 0;");
        sqlList.add(sql);

        //ファイル管理 更新コメント
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_FILE_REKI add FFR_UP_CMT varchar(3000);");
        sqlList.add(sql);

        //回覧板 メモ修正
        sql = new SqlBuffer();
        sql.addSql("  alter table CIR_INF add CIF_MEMO_FLG integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("  alter table CIR_INF add CIF_MEMO_DATE timestamp;");
        sqlList.add(sql);

        //プロジェクト 予算最大値変更
        sql = new SqlBuffer();
        sql.addSql("  alter table PRJ_PRJDATA alter column PRJ_YOSAN type bigint;");
        sqlList.add(sql);

        //社員情報 メールアドレス入力最大値変更
        sql = new SqlBuffer();
        sql.addSql("  alter table CMN_USRM_INF alter column USI_MAIL1 type varchar(256);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("  alter table CMN_USRM_INF alter column USI_MAIL2 type varchar(256);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("  alter table CMN_USRM_INF alter column USI_MAIL3 type varchar(256);");
        sqlList.add(sql);

        //スケジュール 個人設定 初期表示画面追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_DEF_DSP integer not null default 1;");
        sqlList.add(sql);

        //スケジュール 個人設定 重複登録設定追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_REPEAT_KBN integer default 0 not null;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_REPEAT_MY_KBN integer default 0 not null;");
        sqlList.add(sql);

        //掲示板フォーラムメンバー権限設定
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_MEM add BFM_AUTH integer not null default 1;");
        sqlList.add(sql);

        //掲示板フォーラム管理者
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_FOR_ADMIN");
        sql.addSql(" (");
        sql.addSql("   BFI_SID  integer      not null,");
        sql.addSql("   USR_SID  integer      not null,");
        sql.addSql("   primary key (BFI_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメールメイン表示設定項目
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_MAIN_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_MAIN_CNT integer not null default 10;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_MAIN_SORT integer not null default 0;");
        sqlList.add(sql);

        //掲示板サブコンテンツ表示設定項目
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_USER add BUR_SUB_NEW_THRE integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_USER add BUR_SUB_FORUM integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_USER add BUR_SUB_UNCHK_THRE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール一覧画面写真表示設定項目
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_PHOTO_DSP integer not null default 0;");
        sqlList.add(sql);

        //ファイル管理 ロック日付
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_FILE_BIN add FFL_LOCK_DATE timestamp;");
        sqlList.add(sql);

        //ショートメール 管理者設定 転送Fromアドレス入力最大値変更
        sql = new SqlBuffer();
        sql.addSql("  alter table SML_ADMIN alter column SMA_FROM_ADD type varchar(256);");
        sqlList.add(sql);

        //プロキシサーバ ユーザ認証と使用しないアドレスを追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_PXY_AUTH integer;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_PXY_AUTH_ID varchar(256);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_PXY_AUTH_PASS varchar(1200);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_PXY_ADRKBN integer;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PROXY_ADDRESS");
        sql.addSql(" (");
        sql.addSql("   CXA_ADDRESS  varchar(200) not null,");
        sql.addSql("   CXA_NO       integer      not null,");
        sql.addSql("   primary key (CXA_ADDRESS)");
        sql.addSql(" );");
        sqlList.add(sql);

        //施設予約 アクセス制限機能追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_GRP add RSG_ACS_LIMIT_KBN integer default -1;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table RSV_ACCESS_CONF");
        sql.addSql(" (");
        sql.addSql("   RSG_SID       integer not null,");
        sql.addSql("   RSD_SID       integer not null,");
        sql.addSql("   USR_SID       integer not null,");
        sql.addSql("   GRP_SID       integer not null,");
        sql.addSql("   RAC_AUTH      integer not null");
        sql.addSql(" );");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] 施設予約 INDEX追加
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @throws SQLException 施設予約 INDEXの検索、または追加に失敗
     */
    private void __createReceiveIndex(Connection con) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;

        try {
            SqlBuffer sql = new SqlBuffer();
            IDbUtil dbUtil = DBUtilFactory.getInstance();
            if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {
                sql.addSql(" select * from pg_indexes");
                sql.addSql(" where upper(indexname) = 'RSV_SIS_YRK_INDEX1'");
            } else {
                sql.addSql(" select 1 from INFORMATION_SCHEMA.INDEXES");
                sql.addSql(" where INDEX_NAME = 'RSV_SIS_YRK_INDEX1'");
            }

            log__.info(sql.toLogString());
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql.toSqlString());

            if (!rs.next()) {

                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(stmt);

                sql = new SqlBuffer();
                sql.addSql(" create index RSV_SIS_YRK_INDEX1");
                sql.addSql(" on RSV_SIS_YRK(RSD_SID,RSY_FR_DATE,RSY_TO_DATE);");

                log__.info(sql.toLogString());
                stmt = con.createStatement();
                stmt.executeUpdate(sql.toSqlString());
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }
    }
}