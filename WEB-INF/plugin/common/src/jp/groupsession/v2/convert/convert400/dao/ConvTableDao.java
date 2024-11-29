package jp.groupsession.v2.convert.convert400.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * <br>[解  説] v4.0.0へのコンバートで使用する
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

        //共通 ファイル名の最大長変更
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_BINF alter column BIN_FILE_NAME type varchar(255);");
        sqlList.add(sql);

        //ファイル管理 ファイル名の最大長変更
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_DIRECTORY alter column FDR_NAME type varchar(255);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_FILE_REKI alter column FFR_FNAME type varchar(255);");
        sqlList.add(sql);


        //バイナリSIDをintからbigintに変更
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_BINF alter column BIN_FILE_PATH type varchar(30);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_BINF alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_CONTACT_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_INFO_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_CABINET_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_DIRECTORY_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_FILE_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table IPK_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_DIRECTORY alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_FILE_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_PRJDATA alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_TODO_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table SML_BIN alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_INFO alter column BIN_SID type bigint;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_CABINET alter column FCB_MARK type bigint;");
        sqlList.add(sql);

        //メイン画面の在席管理備考の最大文字数を10文字から30文字に変更
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USR_INOUT alter column UIO_BIKO type varchar(30);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_FIX_UPDATE alter column ZFU_MSG type varchar(30);");
        sqlList.add(sql);

        //バックアップ設定にZIP出力区分のフィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_BACKUP_CONF add BUC_ZIPOUT integer not null default 1;");
        sqlList.add(sql);

        //ショートメール 返信･転送した場合は返信したショートメールの表示を変更
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS add SMJ_RTN_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS add SMJ_FW_KBN integer not null default 0;");
        sqlList.add(sql);

        //プロジェクトの外部メンバーのソート
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_ADDRESS add PRA_SORT integer not null default 0;");
        sqlList.add(sql);
        //プロジェクトの外部メンバーのソート
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_DEF_DSP integer not null default 0;");
        sqlList.add(sql);
        //プロジェクト個人設定 TODOスケジュール表示
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_USER_CONF add PUC_SCH_KBN integer default 0;");
        sqlList.add(sql);

        //企業情報画像バイナリSID
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_ENTER_INF add BIN_SID bigint;");
        sqlList.add(sql);
        //企業情報画像表示区分
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_ENTER_INF add ENI_IMG_KBN integer not null default 0;");
        sqlList.add(sql);

        IDbUtil dbUtil = DBUtilFactory.getInstance();

        //グループを10階層に変更
        sql = new SqlBuffer();
        if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {
            sql.addSql(" alter table CMN_GROUP_CLASS drop constraint cmn_group_class_pkey;");
        } else {
            sql.addSql(" alter table CMN_GROUP_CLASS DROP PRIMARY KEY;");
        }
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUP_CLASS add GCL_SID6 integer not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUP_CLASS add GCL_SID7 integer not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUP_CLASS add GCL_SID8 integer not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUP_CLASS add GCL_SID9 integer not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUP_CLASS add GCL_SID10 integer not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUP_CLASS add primary key"
                   + " (GCL_SID1, GCL_SID2, GCL_SID3, GCL_SID4,"
                   + " GCL_SID5, GCL_SID6, GCL_SID7, GCL_SID8, GCL_SID9, GCL_SID10);");
        sqlList.add(sql);

        //新着RSS表示日数設定
        sql = new SqlBuffer();
        sql.addSql(" create table RSS_UCONF");
        sql.addSql(" (");
        sql.addSql("   USR_SID       integer      not null,");
        sql.addSql("   RUC_NEW_CNT   integer      not null,");
        sql.addSql("   RUC_AUID      integer      not null,");
        sql.addSql("   RUC_ADATE     timestamp    not null,");
        sql.addSql("   RUC_EUID      integer      not null,");
        sql.addSql("   RUC_EDATE     timestamp    not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ユーザプラグイン
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USR_PLUGIN");
        sql.addSql(" (");
        sql.addSql("   CUP_PID  varchar(10)  not null,");
        sql.addSql("   CUP_NAME  varchar(10)  not null,");
        sql.addSql("   CUP_URL       varchar(1000) not null,");
        sql.addSql("   CUP_VIEW integer      not null,");
        sql.addSql("   CUP_TARGET integer      not null,");
        sql.addSql("   BIN_SID    bigint  not null,");
        sql.addSql("   primary key (CUP_PID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //施設予約 施設予約管理者設定
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_DTM_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_DTM_FR integer not null default 9;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_DTM_TO integer not null default 18;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_EDIT_KBN  integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_EDIT integer not null default 0;");
        sqlList.add(sql);

        //在席管理 管理者設定
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_ADM_CONF add ZAC_SORT_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_ADM_CONF add ZAC_SORT_KEY1 integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_ADM_CONF add ZAC_SORT_ORDER1 integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_ADM_CONF add ZAC_SORT_KEY2 integer not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_ADM_CONF add ZAC_SORT_ORDER2 integer not null default 0;");
        sqlList.add(sql);

        //スケジュール   グループスケジュール表示設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_GRP_SHOW_KBN integer not null default 0;");
        sqlList.add(sql);

        //スケジュール 管理者設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_EDIT_STYPE integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_EDIT integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_REPEAT_STYPE integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_REPEAT_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_REPEAT_MY_KBN integer not null default 0;");
        sqlList.add(sql);

        //====================== ポータルプラグイン =======================

        //ポータル管理者情報
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_ADM_CONF");
        sql.addSql(" (");
        sql.addSql("   PAC_PTL_EDITKBN         smallint          not null,");
        sql.addSql("   PAC_DEF_KBN             smallint          not null,");
        sql.addSql("   PAC_DEF_TYPE            smallint          not null,");
        sql.addSql("   primary key (PAC_PTL_EDITKBN)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータルメイン設定
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_MAIN_CONF");
        sql.addSql(" (");
        sql.addSql("   USR_SID         integer           not null,");
        sql.addSql("   PTL_SID         integer           not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータル権限設定
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTAL_CONF_READ");
        sql.addSql(" (");
        sql.addSql("   PTL_SID         integer           not null,");
        sql.addSql("   USR_SID         integer           not null,");
        sql.addSql("   GRP_SID         integer           not null,");
        sql.addSql("   primary key (PTL_SID, USR_SID, GRP_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータルレイアウト
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTAL_LAYOUT");
        sql.addSql(" (");
        sql.addSql("   PTL_SID          integer           not null,");
        sql.addSql("   PLY_POSITION     smallint          not null,");
        sql.addSql("   PTS_VIEW         integer           not null,");
        sql.addSql("   PLY_AUID         integer           not null,");
        sql.addSql("   PLY_ADATE        timestamp         not null,");
        sql.addSql("   PLY_EUID         integer           not null,");
        sql.addSql("   PLY_EDATE        timestamp         not null,");
        sql.addSql("   primary key (PTL_SID, PLY_POSITION)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータル_ポートレットパラメータ
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTAL_POSITION_PARAM");
        sql.addSql(" (");
        sql.addSql("   PTL_SID           integer             not null,");
        sql.addSql("   PTP_ITEMID        varchar(17)         not null,");
        sql.addSql("   PPM_PARAM_NO      integer             not null,");
        sql.addSql("   PPM_PARAM_NAME    varchar(50)         not null,");
        sql.addSql("   PPM_PARAM_VALUE   varchar(1000)       not null,");
        sql.addSql("   primary key (PTL_SID, PTP_ITEMID, PPM_PARAM_NO)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータル_位置情報
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTAL_POSITION");
        sql.addSql(" (");
        sql.addSql("   PTL_SID           integer           not null,");
        sql.addSql("   PTP_ITEMID        varchar(17)       not null,");
        sql.addSql("   PLY_POSITION      smallint          not null,");
        sql.addSql("   PTP_SORT          integer           not null,");
        sql.addSql("   PTP_VIEW          smallint          not null,");
        sql.addSql("   PTP_TYPE          smallint          not null,");
        sql.addSql("   PLT_SID           integer,");
        sql.addSql("   PCT_PID           varchar(10),");
        sql.addSql("   MSC_ID            varchar(150),");
        sql.addSql("   PTP_PARAMKBN      smallint          not null,");
        sql.addSql("   primary key (PTL_SID, PTP_ITEMID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータル表示順
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTAL_SORT");
        sql.addSql(" (");
        sql.addSql("   PTL_SID          integer           not null,");
        sql.addSql("   PTS_KBN          smallint          not null,");
        sql.addSql("   USR_SID          integer           not null,");
        sql.addSql("   PTS_SORT         integer           not null,");
        sql.addSql("   primary key (PTL_SID, PTS_KBN, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータル情報
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTAL");
        sql.addSql(" (");
        sql.addSql("   PTL_SID          integer           not null,");
        sql.addSql("   PTL_NAME         varchar(100)      not null,");
        sql.addSql("   PTL_DESCRIPTION  varchar(1000),");
        sql.addSql("   PTL_OPEN         smallint          not null,");
        sql.addSql("   PTL_ACCESS       smallint          not null,");
        sql.addSql("   PTL_AUID         integer           not null,");
        sql.addSql("   PTL_ADATE        timestamp        not null,");
        sql.addSql("   PTL_EUID         integer           not null,");
        sql.addSql("   PTL_EDATE        timestamp        not null,");
        sql.addSql("   primary key (PTL_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポートレット
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTLET");
        sql.addSql(" (");
        sql.addSql("   PLT_SID          integer           not null,");
        sql.addSql("   PLT_NAME         varchar(100)      not null,");

        if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {
            sql.addSql("   PLT_CONTENT      text              not null,");
        } else {
            sql.addSql("   PLT_CONTENT      CLOB              not null,");
        }
        sql.addSql("   PLT_CONTENT_TYPE smallint          not null,");
        sql.addSql("   PLT_CONTENT_URL  varchar(1000),");
        sql.addSql("   PLT_DESCRIPTION  varchar(1000),");
        sql.addSql("   PLC_SID          integer           not null,");
        sql.addSql("   PLT_BORDER       smallint          not null,");
        sql.addSql("   PLT_AUID         integer           not null,");
        sql.addSql("   PLT_ADATE        timestamp         not null,");
        sql.addSql("   PLT_EUID         integer           not null,");
        sql.addSql("   PLT_EDATE        timestamp         not null,");
        sql.addSql("   primary key (PLT_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポートレットカテゴリー情報
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTLET_CATEGORY");
        sql.addSql(" (");
        sql.addSql("   PLC_SID          integer           not null,");
        sql.addSql("   PLC_NAME         varchar(100)      not null,");
        sql.addSql("   PLC_BIKO         varchar(500),");
        sql.addSql("   primary key (PLC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポートレットカテゴリー_表示順
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTLET_CATEGORY_SORT");
        sql.addSql(" (");
        sql.addSql("   PLC_SID          integer           not null,");
        sql.addSql("   PCS_SORT         integer           not null,");
        sql.addSql("   primary key (PLC_SID, PCS_SORT)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポートレット_画像
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTLET_IMAGE");
        sql.addSql(" (");
        sql.addSql("   PLT_SID         integer           not null,");
        sql.addSql("   PLI_SID         bigint            not null,");
        sql.addSql("   BIN_SID         bigint            not null,");
        sql.addSql("   PLI_NAME        varchar(100)      not null,");
        sql.addSql("   primary key (PLT_SID, PLI_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポートレット_表示順
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_PORTLET_SORT");
        sql.addSql(" (");
        sql.addSql("   PLT_SID          integer           not null,");
        sql.addSql("   PLS_SORT         integer           not null,");
        sql.addSql("   PLS_VIEW         smallint          not null,");
        sql.addSql("   primary key (PLT_SID, PLS_SORT)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ポータル個人設定
        sql = new SqlBuffer();
        sql.addSql(" create table PTL_UCONF");
        sql.addSql(" (");
        sql.addSql("   USR_SID             integer           not null,");
        sql.addSql("   PUC_DEF_TYPE         smallint          not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);


        //ポータル 管理者設定登録
        sql = new SqlBuffer();
        sql.addSql(" insert into PTL_ADM_CONF values (");
        sql.addSql("   1, 1, 0");
        sql.addSql(" );");
        sqlList.add(sql);

        //メインのポータル情報登録
        sql = new SqlBuffer();
        sql.addSql(" insert into PTL_PORTAL");
        sql.addSql(" values (");
        sql.addSql("  0, 'メイン', null, 0, 0, 0, current_timestamp, 0, current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //メインのポータル表示順登録
        sql = new SqlBuffer();
        sql.addSql(" insert into PTL_PORTAL_SORT values (");
        sql.addSql("   0, 0, 0, 1");
        sql.addSql(" );");
        sqlList.add(sql);

        //====================== ポータルプラグイン END=======================

        //プロジェクトTODOのコメント文字数最大1000文字に変更
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_TODOCOMMENT alter column PCM_COMMENT type varchar(1000);");
        sqlList.add(sql);

        //施設予約 個人設定にショートメール通知設定を追加。
        sql = new SqlBuffer();
        sql.addSql("  alter table RSV_USER add RSU_SMAIL_KBN integer not null default 0;");
        sqlList.add(sql);

        //WEBメール 署名位置区分用フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add WAC_SIGN_POINT_KBN integer not null default 1;");
        sqlList.add(sql);

        //WEBメール 返信時署名表示区分用フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add WAC_SIGN_DSP_KBN integer not null default 1;");
        sqlList.add(sql);


        //メイン画面レイアウト
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_MAINSCREEN_LAYOUT");
        sql.addSql(" (");
        sql.addSql("   USR_SID          integer           not null,");
        sql.addSql("   MSC_POSITION     smallint          not null,");
        sql.addSql("   MSL_AUID         integer           not null,");
        sql.addSql("   MSL_ADATE        timestamp         not null,");
        sql.addSql("   MSL_EUID         integer           not null,");
        sql.addSql("   MSL_EDATE        timestamp         not null,");
        sql.addSql("   primary key (USR_SID, MSC_POSITION)");
        sql.addSql(" );");
        sqlList.add(sql);

        //メイン画面レイアウト管理者設定
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_MAINSCREEN_LAYOUT_ADMIN");
        sql.addSql(" (");
        sql.addSql("   MLC_ADM_LAYOUT_KBN smallint        not null,");
        sql.addSql("   MLC_DEFAULT_KBN    smallint        not null,");
        sql.addSql("   MLC_AUID           integer         not null,");
        sql.addSql("   MLC_ADATE          timestamp       not null,");
        sql.addSql("   MLC_EUID           integer         not null,");
        sql.addSql("   MLC_EDATE          timestamp       not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //メイン画面レイアウト個人設定
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_MAINSCREEN_LAYOUT_USER");
        sql.addSql(" (");
        sql.addSql("   USR_SID          smallint          not null,");
        sql.addSql("   MSU_DEFAULT_KBN  smallint          not null,");
        sql.addSql("   MSU_AUID         integer           not null,");
        sql.addSql("   MSU_ADATE        timestamp         not null,");
        sql.addSql("   MSU_EUID         integer           not null,");
        sql.addSql("   MSU_EDATE        timestamp         not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板 フォーラム情報 既読許可区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_MREAD integer not null default 1;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 スレッドテンプレート区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_TEMPLATE_KBN integer not null default 0;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 スレッドテンプレートフィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_TEMPLATE varchar(2000);");
        sqlList.add(sql);

        //掲示板 フォーラム情報 スレッドテンプレート投稿適応区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_TEMPLATE_WRITE integer not null default 0;");
        sqlList.add(sql);

        //WEBメール 管理者設定 管理者一括設定区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF "
                 + "add WAD_PERMIT_KBN integer not null default 1;");
        sqlList.add(sql);

        //WEBメール 管理者設定 自動受信時間フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF "
                 + "add WAD_AUTO_RECEIVE_TIME integer not null default 5;");
        sqlList.add(sql);

        //WEBメール 管理者設定 ディスク容量設定区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF "
                 + "add WAD_DISK integer not null default 0;");
        sqlList.add(sql);

        //WEBメール 管理者設定 ディスク容量フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF "
                 + "add WAD_DISK_SIZE integer;");
        sqlList.add(sql);

        //WEBメール 管理者設定 受信時削除区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF "
                 + "add WAD_DELRECEIVE integer not null default 0;");
        sqlList.add(sql);

        //WEBメール 管理者設定 自動受信設定区分フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF "
                 + "add WAD_AUTORECEIVE integer not null default 1;");
        sqlList.add(sql);

        //WEBメール アカウント設定 自動受信時間フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT "
                 + "add WAC_AUTO_RECEIVE_TIME integer not null default 0;");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] ポータル機能に関係するテーブルが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @return true:存在する false:存在しない
     * @throws SQLException WEBメール アカウント情報のコンバートに失敗
     */
    public boolean checkPortal(Connection con) throws SQLException {
        boolean result = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ?");
            sql.addStrValue("PTL_ADM_CONF");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            result = rs.next();
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return result;
    }
}