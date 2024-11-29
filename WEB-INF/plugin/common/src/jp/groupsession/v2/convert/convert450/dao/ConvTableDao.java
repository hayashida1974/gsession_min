package jp.groupsession.v2.convert.convert450.dao;

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
 * <br>[解  説] v4.5.0へコンバートする際に使用する
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

        //内線番号
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_TEL_NAI1 type varchar(15);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_TEL_NAI2 type varchar(15);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_TEL_NAI3 type varchar(15);");
        sqlList.add(sql);

        //マイグループ共有設定
        sql = new SqlBuffer();

        sql.addSql(" create table CMN_MY_GROUP_SHARE");
        sql.addSql(" (");
        sql.addSql("  USR_SID          integer      not null,");
        sql.addSql("  MGP_SID          integer      not null,");
        sql.addSql("  MGS_USR_SID          integer      not null,");
        sql.addSql("  MGS_GRP_SID          integer      not null,");
        sql.addSql("  primary key (USR_SID, MGP_SID, MGS_USR_SID ,MGS_GRP_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //オペレーションログ情報 操作コード(内部)
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_LOG add LOG_CODE varchar(100);");
        sqlList.add(sql);

        //ユーザ数
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USRM_COUNT");
        sql.addSql(" (");
        sql.addSql("   CUC_DATE   date     not null,");
        sql.addSql("   CUC_MONTH  integer  not null,");
        sql.addSql("   CUC_CNT    bigint   not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //日報 ショートメール通知設定 マイグループに対する通知を削除
        sql = new SqlBuffer();
        sql.addSql(" delete from NTP_SML_MEMBER where NSM_GRP = 1;");
        sqlList.add(sql);

        //掲示板 管理者設定掲示ファーラム設定 掲示期間有効・無効設定
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_LIMIT_ON integer not null default 1;");
        sqlList.add(sql);

        //ショートメール 管理者設定 集計テーブル
        sql = new SqlBuffer();
        sql.addSql("    create table SML_LOG_COUNT");
        sql.addSql("    (");
        sql.addSql("        SAC_SID        integer         not null,");
        sql.addSql("        SLC_KBN        integer         not null,");
        sql.addSql("        SLC_SYS_KBN        integer         not null,");
        sql.addSql("        SLC_CNT_TO        integer         not null,");
        sql.addSql("        SLC_CNT_CC        integer         not null,");
        sql.addSql("        SLC_CNT_BCC        integer         not null,");
        sql.addSql("        SLC_DATE      timestamp       not null");
        sql.addSql("    );");
        sqlList.add(sql);

        //ショートメール 管理者設定 集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index SML_LOG_COUNT_INDEX1 on SML_LOG_COUNT(SLC_DATE);");
        sqlList.add(sql);

        //ショートメール ショートメール集計データ_自動削除テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_LOG_COUNT_ADEL");
        sql.addSql(" (");
        sql.addSql("  SLD_DEL_KBN            integer           not null,");
        sql.addSql("  SLD_DEL_YEAR           integer,");
        sql.addSql("  SLD_DEL_MONTH          integer,");
        sql.addSql("  SLD_AUID               integer           not null,");
        sql.addSql("  SLD_ADATE              timestamp         not null,");
        sql.addSql("  SLD_EUID               integer           not null,");
        sql.addSql("  SLD_EDATE              timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール ショートメール集計データ_集計結果テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_LOG_COUNT_SUM");
        sql.addSql(" (");
        sql.addSql("  SLS_KBN      integer   not null,");
        sql.addSql("  SLS_SYS_KBN  integer   not null,");
        sql.addSql("  SLS_CNT      bigint    not null,");
        sql.addSql("  SLS_CNT_TO   bigint    not null,");
        sql.addSql("  SLS_CNT_CC   bigint    not null,");
        sql.addSql("  SLS_CNT_BCC  bigint    not null,");
        sql.addSql("  SLS_DATE     date      not null,");
        sql.addSql("  SLS_MONTH    integer   not null,");
        sql.addSql("  SLS_EDATE    timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 送信制限テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_BAN_DEST ");
        sql.addSql(" (");
        sql.addSql("   SBC_SID integer not null,");
        sql.addSql("   SBD_TARGET_SID integer not null,");
        sql.addSql("   SBD_TARGET_KBN integer not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 送信制限設定テーブル
        sql = new SqlBuffer();
        sql.addSql("create table SML_BAN_DEST_CONF");
        sql.addSql("(");
        sql.addSql("    SBC_SID        integer         not null,");
        sql.addSql("    SBC_NAME       varchar(50)     not null,");
        sql.addSql("    SBC_BIKO       varchar(1000),");
        sql.addSql("    SBC_AUID       integer         not null,");
        sql.addSql("    SBC_ADATE      timestamp       not null,");
        sql.addSql("    SBC_EUID       integer         not null,");
        sql.addSql("    SBC_EDATE      timestamp       not null,");
        sql.addSql("    primary key(SBC_SID)");
        sql.addSql(");");
        sqlList.add(sql);
        //ショートメール 送信制限特例許可テーブル
       sql = new SqlBuffer();
        sql.addSql("create table SML_BAN_DEST_PERMIT");
        sql.addSql("(");
        sql.addSql("    SBC_SID        integer         not null,");
        sql.addSql("    SBP_TARGET_SID integer         not null,");
        sql.addSql("    SBP_TARGET_KBN integer         not null");
        sql.addSql(");        ");
        sqlList.add(sql);

        //回覧板 管理者設定 集計テーブル
        sql = new SqlBuffer();
        sql.addSql("    create table CIR_LOG_COUNT");
        sql.addSql("    (");
        sql.addSql("        CAC_SID        integer         not null,");
        sql.addSql("        CLC_KBN        integer         not null,");
        sql.addSql("        CLC_CNT        integer         not null,");
        sql.addSql("        CLC_DATE        timestamp       not null");
        sql.addSql("    );");
        sqlList.add(sql);

        //回覧板 管理者設定 集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index CIR_LOG_COUNT_INDEX1 on CIR_LOG_COUNT(CLC_DATE);");
        sqlList.add(sql);

        //回覧板 回覧板ログ集計_自動削除テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_LOG_COUNT_ADEL");
        sql.addSql(" (");
        sql.addSql("  CLD_DEL_KBN            integer           not null,");
        sql.addSql("  CLD_DEL_YEAR           integer,");
        sql.addSql("  CLD_DEL_MONTH          integer,");
        sql.addSql("  CLD_AUID               integer           not null,");
        sql.addSql("  CLD_ADATE              timestamp         not null,");
        sql.addSql("  CLD_EUID               integer           not null,");
        sql.addSql("  CLD_EDATE              timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //回覧板 回覧板ログ集計_集計結果テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_LOG_COUNT_SUM");
        sql.addSql(" (");
        sql.addSql("  CLS_KBN      integer   not null,");
        sql.addSql("  CLS_CNT      bigint    not null,");
        sql.addSql("  CLS_CNT_SUM  bigint    not null,");
        sql.addSql("  CLS_DATE     date      not null,");
        sql.addSql("  CLS_MONTH    integer   not null,");
        sql.addSql("  CLS_EDATE    timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ファイルアップロード集計テーブル
        sql = new SqlBuffer();
        sql.addSql("    create table FILE_UPLOAD_LOG");
        sql.addSql("    (");
        sql.addSql("        USR_SID        integer         not null,");
        sql.addSql("        GRP_SID        integer         not null,");
        sql.addSql("        FUL_REG_KBN        integer         not null,");
        sql.addSql("        FCB_SID        integer         not null,");
        sql.addSql("        BIN_SID        bigint         not null,");
        sql.addSql("        FUL_DATE      timestamp       not null");
        sql.addSql("    ) ;");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ファイルアップロード集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index FILE_UPLOAD_LOG_INDEX1 on FILE_UPLOAD_LOG(FUL_DATE);");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ファイルダウンロード集計テーブル
        sql = new SqlBuffer();
        sql.addSql("    create table FILE_DOWNLOAD_LOG");
        sql.addSql("    (");
        sql.addSql("        USR_SID        integer         not null,");
        sql.addSql("        FCB_SID        integer         not null,");
        sql.addSql("        BIN_SID        bigint         not null,");
        sql.addSql("        FDL_DATE      timestamp       not null");
        sql.addSql("    ) ;");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ファイルダウンロード集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index FILE_DOWNLOAD_LOG_INDEX1 on FILE_DOWNLOAD_LOG(FDL_DATE);");
        sqlList.add(sql);

        //ファイル管理 ファイル管理集計データ_自動削除テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_LOG_ADEL");
        sql.addSql(" (");
        sql.addSql("  FLD_DEL_KBN            integer           not null,");
        sql.addSql("  FLD_DEL_YEAR           integer,");
        sql.addSql("  FLD_DEL_MONTH          integer,");
        sql.addSql("  FLD_AUID               integer           not null,");
        sql.addSql("  FLD_ADATE              timestamp         not null,");
        sql.addSql("  FLD_EUID               integer           not null,");
        sql.addSql("  FLD_EDATE              timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ファイル管理 ファイル管理集計データ_集計結果テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_LOG_COUNT_SUM");
        sql.addSql(" (");
        sql.addSql("  FLS_KBN      integer   not null,");
        sql.addSql("  FLS_DATE     date      not null,");
        sql.addSql("  FLS_MONTH    integer   not null,");
        sql.addSql("  FLS_CNT      bigint    not null,");
        sql.addSql("  FLS_EDATE    timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール 管理者設定 集計テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table WML_LOG_COUNT");
        sql.addSql(" (");
        sql.addSql("     WAC_SID        integer         not null,");
        sql.addSql("     WLC_KBN        integer         not null,");
        sql.addSql("     WLC_CNT_TO        integer         not null,");
        sql.addSql("     WLC_CNT_CC        integer         not null,");
        sql.addSql("     WLC_CNT_BCC        integer         not null,");
        sql.addSql("     WLC_DATE      timestamp       not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール 管理者設定 集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index WML_LOG_COUNT_INDEX1 on WML_LOG_COUNT(WLC_DATE);");
        sqlList.add(sql);

        //WEBメール メールログ集計_自動削除テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table WML_LOG_COUNT_ADEL");
        sql.addSql(" (");
        sql.addSql("  WLD_DEL_KBN            integer           not null,");
        sql.addSql("  WLD_DEL_YEAR           integer,");
        sql.addSql("  WLD_DEL_MONTH          integer,");
        sql.addSql("  WLD_AUID               integer           not null,");
        sql.addSql("  WLD_ADATE              timestamp         not null,");
        sql.addSql("  WLD_EUID               integer           not null,");
        sql.addSql("  WLD_EDATE              timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール メールログ集計_集計結果テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table WML_LOG_COUNT_SUM");
        sql.addSql(" (");
        sql.addSql("  WLS_KBN      integer   not null,");
        sql.addSql("  WLS_CNT      bigint    not null,");
        sql.addSql("  WLS_CNT_TO   bigint    not null,");
        sql.addSql("  WLS_CNT_CC   bigint   not null,");
        sql.addSql("  WLS_CNT_BCC  bigint   not null,");
        sql.addSql("  WLS_DATE     date      not null,");
        sql.addSql("  WLS_MONTH    integer   not null,");
        sql.addSql("  WLS_EDATE    timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板 管理者設定 閲覧集計テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_VIEW_LOG_COUNT");
        sql.addSql(" (");
        sql.addSql("     USR_SID        integer         not null,");
        sql.addSql("     BFI_SID        integer         not null,");
        sql.addSql("     BTI_SID        integer         not null,");
        sql.addSql("     BVL_VIEW_DATE      timestamp       not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板 管理者設定 閲覧集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index BBS_VIEW_LOG_COUNT_INDEX1 on BBS_VIEW_LOG_COUNT(BVL_VIEW_DATE);");
        sqlList.add(sql);

        //掲示板 管理者設定 投稿集計テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_WRITE_LOG_COUNT");
        sql.addSql(" (");
        sql.addSql("     USR_SID        integer         not null,");
        sql.addSql("     GRP_SID        integer         not null,");
        sql.addSql("     BFI_SID        integer         not null,");
        sql.addSql("     BTI_SID        integer         not null,");
        sql.addSql("     BWL_WRT_DATE      timestamp       not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板 管理者設定 投稿集計テーブル インデックス追加
        sql = new SqlBuffer();
        sql.addSql(
                " create index BBS_WRITE_LOG_COUNT_INDEX1 on BBS_WRITE_LOG_COUNT(BWL_WRT_DATE);");
        sqlList.add(sql);

        //掲示板 掲示板集計データ_自動削除テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_LOG_COUNT_ADEL");
        sql.addSql(" (");
        sql.addSql("  BLD_DEL_KBN            integer           not null,");
        sql.addSql("  BLD_DEL_YEAR           integer,");
        sql.addSql("  BLD_DEL_MONTH          integer,");
        sql.addSql("  BLD_AUID               integer           not null,");
        sql.addSql("  BLD_ADATE              timestamp         not null,");
        sql.addSql("  BLD_EUID               integer           not null,");
        sql.addSql("  BLD_EDATE              timestamp         not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板 掲示板集計データ_集計結果テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_LOG_COUNT_SUM");
        sql.addSql(" (");
        sql.addSql("  BLS_KBN   integer not null,");
        sql.addSql("  BLS_CNT   bigint not null,");
        sql.addSql("  BLS_DATE  date not null,");
        sql.addSql("  BLS_MONTH integer not null,");
        sql.addSql("  BLS_EDATE timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //アンケート 管理者設定
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_ADM_CONF");
        sql.addSql("(");
        sql.addSql("        EAC_KBN_TAISYO      integer                 ,");
        sql.addSql("        EAC_MAIN_DSP_USE    integer                 ,");
        sql.addSql("        EAC_MAIN_DSP        integer                 ,");
        sql.addSql("        EAC_LIST_CNT_USE    integer                 ,");
        sql.addSql("        EAC_LIST_CNT        integer                 ,");
        sql.addSql("        EAC_AUID            integer         not null,");
        sql.addSql("        EAC_ADATE           timestamp       not null,");
        sql.addSql("        EAC_EUID            integer         not null,");
        sql.addSql("        EAC_EDATE           timestamp       not null");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 回答 基本情報
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_ANS_MAIN");
        sql.addSql("(");
        sql.addSql("        EMN_SID         bigint          not null,");
        sql.addSql("        USR_SID         integer         not null,");
        sql.addSql("        EAM_STS_KBN     integer                 ,");
        sql.addSql("        EQM_ANS_DATE    timestamp               ,");
        sql.addSql("        EAM_AUID        integer         not null,");
        sql.addSql("        EAM_ADATE       timestamp       not null,");
        sql.addSql("        EAM_EUID        integer         not null,");
        sql.addSql("        EAM_EDATE       timestamp       not null,");
        sql.addSql("        primary key(EMN_SID, USR_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 回答 サブ情報
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_ANS_SUB");
        sql.addSql("(");
        sql.addSql("        EMN_SID         bigint          not null,");
        sql.addSql("        USR_SID         integer         not null,");
        sql.addSql("        EQM_SEQ         integer         not null,");
        sql.addSql("        EQS_SEQ         integer         not null,");
        sql.addSql("        EAS_ANS_TXT     varchar(1000)           ,");
        sql.addSql("        EAS_ANS_NUM     bigint                  ,");
        sql.addSql("        EAS_ANS_DAT     date                    ,");
        sql.addSql("        EAS_ANS         varchar(1000)           ,");
        sql.addSql("        EQM_AUID        integer         not null,");
        sql.addSql("        EQM_ADATE       timestamp       not null,");
        sql.addSql("        EQM_EUID        integer         not null,");
        sql.addSql("        EQM_EDATE       timestamp       not null,");
        sql.addSql("        primary key(EMN_SID, USR_SID, EQM_SEQ, EQS_SEQ)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 作成可能者
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_CRT_USER");
        sql.addSql("(");
        sql.addSql("        ECU_KBN         integer         not null,");
        sql.addSql("        ECU_SID         bigint          not null,");
        sql.addSql("        ECU_AUID        integer         not null,");
        sql.addSql("        ECU_ADATE       timestamp       not null,");
        sql.addSql("        ECU_EUID        integer         not null,");
        sql.addSql("        ECU_EDATE       timestamp       not null,");
        sql.addSql("        primary key(ECU_KBN, ECU_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 基本情報
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_MAIN");
        sql.addSql("(");
        sql.addSql("        EMN_SID         bigint          not null,");
        sql.addSql("        EMN_DATA_KBN    integer                 ,");
        sql.addSql("        ETP_SID         integer                 ,");
        sql.addSql("        EMN_TITLE       varchar(100)            ,");
        sql.addSql("        EMN_PRI_KBN     integer                 ,");
        sql.addSql("        EMN_DESC        text                    ,");
        sql.addSql("        EMN_DESC_PLAIN  text                    ,");
        sql.addSql("        EMN_ATTACH_KBN  integer                 ,");
        sql.addSql("        EMN_ATTACH_ID   varchar(100)            ,");
        sql.addSql("        EMN_ATTACH_NAME varchar(100)            ,");
        sql.addSql("        EMN_ATTACH_POS  integer                 ,");
        sql.addSql("        EMN_OPEN_STR    date                    ,");
        sql.addSql("        EMN_OPEN_END    date                    ,");
        sql.addSql("        EMN_RES_END     date                    ,");
        sql.addSql("        EMN_ANONY       integer                 ,");
        sql.addSql("        EMN_ANS_OPEN    integer                 ,");
        sql.addSql("        EMN_SEND_GRP    bigint                  ,");
        sql.addSql("        EMN_SEND_USR    bigint                  ,");
        sql.addSql("        EMN_SEND_NAME   varchar(100)            ,");
        sql.addSql("        EMN_TARGET      integer                 ,");
        sql.addSql("        EMN_QUESEC_TYPE integer         not null,");
        sql.addSql("        EMN_AUID        integer         not null,");
        sql.addSql("        EMN_ADATE       timestamp       not null,");
        sql.addSql("        EMN_EUID        integer         not null,");
        sql.addSql("        EMN_EDATE       timestamp       not null,");
        sql.addSql("        primary key (EMN_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 個人設定
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_PRI_CONF");
        sql.addSql("(");
        sql.addSql("        USR_SID         integer         not null,");
        sql.addSql("        EPC_MAIN_DSP    integer                 ,");
        sql.addSql("        EPC_LIST_CNT    integer                 ,");
        sql.addSql("        EPC_AUID        integer         not null,");
        sql.addSql("        EPC_ADATE       timestamp       not null,");
        sql.addSql("        EPC_EUID        integer         not null,");
        sql.addSql("        EPC_EDATE       timestamp       not null,");
        sql.addSql("        primary key (USR_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 設問 基本情報
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_QUE_MAIN");
        sql.addSql("(");
        sql.addSql("        EMN_SID         bigint          not null,");
        sql.addSql("        EQM_SEQ         integer         not null,");
        sql.addSql("        EQM_DSP_SEC     integer                 ,");
        sql.addSql("        EQM_QUE_SEC     varchar(10)             ,");
        sql.addSql("        EQM_QUESTION    varchar(100)            ,");
        sql.addSql("        EQM_QUE_KBN     integer                 ,");
        sql.addSql("        EQM_REQUIRE     integer                 ,");
        sql.addSql("        EQM_OTHER       integer                 ,");
        sql.addSql("        EQM_DESC        text                    ,");
        sql.addSql("        EQM_DESC_PLAIN  text                    ,");
        sql.addSql("        EQM_ATTACH_KBN  integer                 ,");
        sql.addSql("        EQM_ATTACH_ID   varchar(100)            ,");
        sql.addSql("        EQM_ATTACH_NAME varchar(100)            ,");
        sql.addSql("        EQM_ATTACH_POS  integer                 ,");
        sql.addSql("        EQM_LINE_KBN    integer                 ,");
        sql.addSql("        EQM_GRF_KBN     integer                 ,");
        sql.addSql("        EQM_AUID        integer         not null,");
        sql.addSql("        EQM_ADATE       timestamp       not null,");
        sql.addSql("        EQM_EUID        integer         not null,");
        sql.addSql("        EQM_EDATE       timestamp       not null,");
        sql.addSql("        primary key (EMN_SID, EQM_SEQ)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 設問 サブ情報
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_QUE_SUB");
        sql.addSql("(");
        sql.addSql("        EMN_SID         bigint          not null,");
        sql.addSql("        EQM_SEQ         integer         not null,");
        sql.addSql("        EQS_SEQ         integer         not null,");
        sql.addSql("        EQS_DSP_SEC     integer                 ,");
        sql.addSql("        EQS_DSP_NAME    varchar(30)             ,");
        sql.addSql("        EQS_DEF_TXT     varchar(1000)           ,");
        sql.addSql("        EQS_DEF_NUM     bigint                  ,");
        sql.addSql("        EQS_DEF_DAT     date                    ,");
        sql.addSql("        EQS_DEF         varchar(1000)           ,");
        sql.addSql("        EQS_RNG_STR_NUM bigint                  ,");
        sql.addSql("        EQS_RNG_END_NUM bigint                  ,");
        sql.addSql("        EQS_RNG_STR_DAT date                    ,");
        sql.addSql("        EQS_RNG_END_DAT date                    ,");
        sql.addSql("        EQS_UNIT_NUM    varchar(10)             ,");
        sql.addSql("        EQS_AUID        integer         not null,");
        sql.addSql("        EQS_ADATE       timestamp       not null,");
        sql.addSql("        EQS_EUID        integer         not null,");
        sql.addSql("        EQS_EDATE       timestamp       not null,");
        sql.addSql("        primary key (EMN_SID, EQM_SEQ, EQS_SEQ)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 対象者
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_SUBJECT");
        sql.addSql("(");
        sql.addSql("        EMN_SID         bigint          not null,");
        sql.addSql("        USR_SID         integer,");
        sql.addSql("        GRP_SID         integer");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 種類
        sql = new SqlBuffer();
        sql.addSql("create table ENQ_TYPE");
        sql.addSql("(");
        sql.addSql("        ETP_SID         bigint          not null,");
        sql.addSql("        ETP_DSP_SEQ     integer                 ,");
        sql.addSql("        ETP_NAME        varchar(100)            ,");
        sql.addSql("        ETP_AUID        integer         not null,");
        sql.addSql("        ETP_ADATE       timestamp       not null,");
        sql.addSql("        ETP_EUID        integer         not null,");
        sql.addSql("        ETP_EDATE       timestamp       not null,");
        sql.addSql("        primary key(ETP_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //アンケート 自動削除
        sql = new SqlBuffer();
        sql.addSql(" create table ENQ_AUTODELETE");
        sql.addSql(" (");
        sql.addSql("         EAD_SEND_KBN        integer         not null,");
        sql.addSql("         EAD_SEND_YEAR       integer         not null,");
        sql.addSql("         EAD_SEND_MONTH      integer         not null,");
        sql.addSql("         EAD_DRAFT_KBN       integer         not null,");
        sql.addSql("         EAD_DRAFT_YEAR       integer        not null,");
        sql.addSql("         EAD_DRAFT_MONTH     integer         not null,");
        sql.addSql("         EAD_AUID            integer         not null,");
        sql.addSql("         EAD_ADATE           timestamp       not null,");
        sql.addSql("         EAD_EUID            integer         not null,");
        sql.addSql("         EAD_EDATE           timestamp       not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //ユーザプラグイン パラメータ設定区分
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USR_PLUGIN add CUP_PARAM_KBN integer not null default 0;");
        sqlList.add(sql);

        //ユーザプラグイン 送信方法区分
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USR_PLUGIN add CUP_SEND_KBN integer not null default 0;");
        sqlList.add(sql);

        //ユーザプラグインパラメータ
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PLUGIN_PARAM");
        sql.addSql(" (");
        sql.addSql("         CUP_PID         varchar(10)      not null,");
        sql.addSql("         CPP_NUM         integer      not null,");
        sql.addSql("         CPP_NAME        varchar(100)     not null,");
        sql.addSql("         CPP_VALUE       varchar(1000),");
        sql.addSql("         primary key (CUP_PID, CPP_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ファイルテキスト情報
        sql = new SqlBuffer();
        sql.addSql("   create table FILE_FILE_TEXT");
        sql.addSql("   (");
        sql.addSql("     FDR_SID    integer  not null,");
        sql.addSql("     FDR_VERSION    integer  not null,");
        sql.addSql("     FCB_SID    integer  not null,");
        sql.addSql("     FFT_SEC_NO    integer  not null,");
        sql.addSql("     FFT_TEXT    text,");
        sql.addSql("     FFT_BIKO    varchar(1000),");
        sql.addSql("     FFT_READ_KBN    integer  not null");
        sql.addSql("   );");
        sqlList.add(sql);

        //グループ名最大文字数
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUPM alter GRP_NAME type varchar(50);");
        sqlList.add(sql);

        //グループ名カナ最大文字数
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUPM alter GRP_NAME_KN type varchar(75);");
        sqlList.add(sql);

        //スケジュール特例アクセス
        sql = new SqlBuffer();
        sql.addSql("create table SCH_SPACCESS");
        sql.addSql("(");
        sql.addSql("  SSA_SID    integer        not null,");
        sql.addSql("  SSA_NAME   varchar(50)    not null,");
        sql.addSql("  SSA_BIKO   varchar(1000),");
        sql.addSql("  SSA_AUID   integer        not null,");
        sql.addSql("  SSA_ADATE  timestamp      not null,");
        sql.addSql("  SSA_EUID   integer        not null,");
        sql.addSql("  SSA_EDATE  timestamp      not null,");
        sql.addSql("  primary key(SSA_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //スケジュール特例アクセス_制限対象
        sql = new SqlBuffer();
        sql.addSql("create table SCH_SPACCESS_TARGET");
        sql.addSql("(");
        sql.addSql("  SSA_SID    integer       not null,");
        sql.addSql("  SST_TYPE   integer       not null,");
        sql.addSql("  SST_TSID   integer       not null");
        sql.addSql(");");
        sqlList.add(sql);

        //スケジュール特例アクセス_許可対象
        sql = new SqlBuffer();
        sql.addSql("create table SCH_SPACCESS_PERMIT");
        sql.addSql("(");
        sql.addSql("  SSA_SID    integer       not null,");
        sql.addSql("  SSP_TYPE   integer       not null,");
        sql.addSql("  SSP_PSID   integer       not null");
        sql.addSql(");");
        sqlList.add(sql);

        //ファイル管理、v4.3.0バージョンアップ前まで存在したキャビネット、フォルダ自身に添付機能で、
        //添付されたファイルがファイル区分 ファイル管理(1)でなく、共通(0)として保存されていた不具合の修正SQL。
        //データコンバート実施した場合、ZIONでエラーが発生する。
        //バイナリー情報 ファイル管理区分
        sql = new SqlBuffer();
        sql.addSql("update CMN_BINF set BIN_FILEKBN = 1");
        sql.addSql("  where BIN_FILEKBN = 0 and BIN_SID in (select BIN_SID from FILE_FILE_BIN);");
        sqlList.add(sql);

        return sqlList;
    }
}