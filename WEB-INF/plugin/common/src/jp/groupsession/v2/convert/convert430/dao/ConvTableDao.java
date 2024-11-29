package jp.groupsession.v2.convert.convert430.dao;

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
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.3.0へコンバートする際に使用する
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

            int cnt = 0;
            for (SqlBuffer sql : sqlList) {
                cnt++;
                log__.info("テーブル作成・変更処理 " + cnt + "/" + sqlList.size());
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

        //ユーザ情報 ソートキー
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_USRM_INF add USI_SORTKEY1 VARCHAR(10);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_USRM_INF add USI_SORTKEY2 VARCHAR(10);");
        sqlList.add(sql);

        //ユーザ情報 性別
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_USRM_INF add USI_SEIBETU integer not null default 0;");
        sqlList.add(sql);

        //ユーザ情報 入社年月日
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_USRM_INF add USI_ENTRANCE_DATE timestamp;");
        sqlList.add(sql);

        //ユーザ情報 氏名 姓、氏名 名、氏名 姓カナ、氏名 名カナ、役職
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_SEI type varchar(30);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_MEI type varchar(30);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_SEI_KN type varchar(60);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_MEI_KN type varchar(60);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter USI_YAKUSYOKU type varchar(30);");
        sqlList.add(sql);

        //役職マスタ 役職名称
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_POSITION alter POS_NAME type varchar(30);");
        sqlList.add(sql);

        //プロジェクト TODO登録画面区分
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE PRJ_USER_CONF add PUC_TODO_DSP integer default 0;");
        sqlList.add(sql);

        //上部メニュー ユーザロゴバイナリSID
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_ENTER_INF add MENU_BIN_SID bigint; ");
        sqlList.add(sql);

        //上部メニュー ロゴ区分
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_ENTER_INF add ENI_MENU_IMG_KBN integer not null default 0;");
        sqlList.add(sql);

        //WEBメール アカウント
        String[] wmlField = {"WAC_THEME", "WAC_CHECK_ADDRESS", "WAC_CHECK_FILE",
                                    "WAC_COMPRESS_FILE", "WAC_TIMESENT", "WAC_QUOTES",
                                    "WAC_DISK_SPS"};
        for (String fieldName : wmlField) {
            sql = new SqlBuffer();
            sql.addSql(" alter table WML_ACCOUNT add " + fieldName
                            + " integer not null default 0;");
            sqlList.add(sql);
        }

        //WEBメール アカウント アカウント名
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT alter WAC_NAME type varchar(200);");
        sqlList.add(sql);

        //WEBメール アカウント 自動受信 受信実行サーバ
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add WAC_AUTORECEIVE_AP integer;");
        sqlList.add(sql);

        //WEBメール アカウント 時間差送信 初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add WAC_TIMESENT_DEF integer;");
        sqlList.add(sql);

        //WEBメール アカウント 添付ファイル自動圧縮 初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ACCOUNT add WAC_COMPRESS_FILE_DEF integer;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定
        wmlField = new String[] {"WAD_CHECK_ADDRESS", "WAD_CHECK_FILE",
                                            "WAD_COMPRESS_FILE", "WAD_TIMESENT", "WAD_SEND_LIMIT"};
        for (String fieldName : wmlField) {
            sql = new SqlBuffer();
            sql.addSql(" alter table WML_ADM_CONF add " + fieldName
                            + " integer not null default 0;");
            sqlList.add(sql);
        }

        //WEBメール WEBメール管理者設定 送信メールサイズ制限
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_SEND_LIMIT_SIZE integer;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 メール転送制限
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_FWLIMIT integer not null default 0;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 BCC強制変換
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_BCC integer not null default 0;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 BCC強制変換 閾値
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_BCC_TH integer;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 ディスク容量警告
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_WARN_DISK integer not null default 0;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 ディスク容量警告 閾値
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_WARN_DISK_TH integer;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 サーバ情報の設定
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_SETTING_SERVER integer not null default 0;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 代理人
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_PROXY_USER integer not null default 0;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 添付ファイル自動圧縮 初期値
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_COMPRESS_FILE_DEF integer;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 時間差送信 初期値
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_TIMESENT_DEF integer;");
        sqlList.add(sql);

        //WEBメール WEBメール管理者設定 初期設定
        sql = new SqlBuffer();
        sql.addSql(" update WML_ADM_CONF set ");
        sql.addSql(" WAD_COMPRESS_FILE = 2,");
        sql.addSql(" WAD_COMPRESS_FILE_DEF = 1");
        sqlList.add(sql);

        //WEBメール メール情報_送信予約
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_SENDPLAN");
        sql.addSql(" (");
        sql.addSql("   WMD_MAILNUM   bigint      not null,");
        sql.addSql("   WAC_SID       integer     not null,");
        sql.addSql("   WSP_SENDKBN   integer     not null,");
        sql.addSql("   WSP_SENDDATE  timestamp   not null,");
        sql.addSql("   WSP_MAILTYPE  integer     not null,");
        sql.addSql("   WSP_COMPRESS_FILE integer,");
        sql.addSql("   primary key(WMD_MAILNUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール 「未送信」ディレクトリを有効にする
        sql = new SqlBuffer();
        sql.addSql(" update WML_DIRECTORY set WDR_VIEW = 0 where WDR_TYPE = 3;");
        sqlList.add(sql);

        //WEBメール 「未送信」ディレクトリを「予約送信」ディレクトリに変更する
        sql = new SqlBuffer();
        sql.addSql(" update WML_DIRECTORY set WDR_NAME = '予約送信' where WDR_TYPE = 3;");
        sqlList.add(sql);

        //WEBメール [フィルター_送信先メールアドレス] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_FILTER_FWADDRESS");
        sql.addSql(" (");
        sql.addSql("   WFT_SID               integer not null,");
        sql.addSql("   WFA_NO                integer not null,");
        sql.addSql("   WFA_ADDRESS      varchar(256) not null,");
        sql.addSql("   primary key(WFT_SID, WFA_NO)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール [フィルター_送信先メールアドレス] テーブルに転送先アドレスを登録
        sql = new SqlBuffer();
        sql.addSql(" insert into WML_FILTER_FWADDRESS");
        sql.addSql(" select");
        sql.addSql("   WFT_SID,");
        sql.addSql("   1,");
        sql.addSql("   WFT_ACTION_FWADDRESS");
        sql.addSql(" from");
        sql.addSql("   WML_FILTER");
        sql.addSql(" where");
        sql.addSql("   WFT_ACTION_FORWARD = 1;");
        sqlList.add(sql);

        //WEBメール [フィルター] テーブルから「転送先アドレス」を削除する
        sql = new SqlBuffer();
        sql.addSql(" update WML_FILTER");
        sql.addSql(" set WFT_ACTION_FWADDRESS = null");
        sql.addSql(" where WFT_ACTION_FORWARD = 1;");
        sqlList.add(sql);

        //WEBメール [WEBメール_転送制限] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_FWLIMIT");
        sql.addSql(" (");
        sql.addSql("   WFL_TEXT   varchar(50)  not null,");
        sql.addSql("   WFL_NO     integer      not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール [アカウント署名] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_SIGN");
        sql.addSql(" (");
        sql.addSql("   WAC_SID    integer not null,");
        sql.addSql("   WSI_NO     integer not null,");
        sql.addSql("   WSI_TITLE  varchar(100) not null,");
        sql.addSql("   WSI_SIGN   varchar(1000) not null,");
        sql.addSql("   WSI_DEF    integer not null,");
        sql.addSql("   primary key (WAC_SID, WSI_NO)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into WML_ACCOUNT_SIGN");
        sql.addSql(" select");
        sql.addSql("  WAC_SID,");
        sql.addSql("  1,");
        sql.addSql("  '署名',");
        sql.addSql("  WAC_SIGN,");
        sql.addSql("  1");
        sql.addSql(" from");
        sql.addSql("  WML_ACCOUNT");
        sql.addSql(" where length(coalesce(WAC_SIGN, '')) > 0;");
        sqlList.add(sql);

        //WEBメール [メールテンプレート] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_TEMPLATE");
        sql.addSql(" (");
        sql.addSql("   WTP_SID   integer not null,");
        sql.addSql("   WTP_TYPE  integer not null,");
        sql.addSql("   WAC_SID   integer,");
        sql.addSql("   WTP_NAME  varchar(100) not null,");
        sql.addSql("   WTP_TITLE varchar(1000),");
        sql.addSql("   WTP_BODY  text,");
        sql.addSql("   WTP_ORDER integer not null,");
        sql.addSql("   WTP_AUID  integer not null,");
        sql.addSql("   WTP_ADATE timestamp not null,");
        sql.addSql("   WTP_EUID  integer not null,");
        sql.addSql("   WTP_EDATE timestamp not null,");
        sql.addSql("   primary key(WTP_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール [メールテンプレート_ファイル] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_MAIL_TEMPLATE_FILE");
        sql.addSql(" (");
        sql.addSql("   WTP_SID   integer not null,");
        sql.addSql("   BIN_SID    bigint not null,");
        sql.addSql("   primary key(WTP_SID, BIN_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール アカウント_受信状況 更新日時
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT_RCVSVR add WRS_EDATE timestamp;");
        sqlList.add(sql);

        //WEBメール [アカウント_受信ロック] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_RCVLOCK");
        sql.addSql(" (");
        sql.addSql("   WAC_SID            integer      not null,");
        sql.addSql("   WRL_RCVEDATE       timestamp    not null,");
        sql.addSql("   primary key (WAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール WML_LABEL_RELATION_INDEX1 を変更
        sql = new SqlBuffer();
        sql.addSql(" drop index WML_SENDADDRESS_INDEX1;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_SENDADDRESS_INDEX1");
        sql.addSql(" on WML_SENDADDRESS(WAC_SID,WSA_TYPE);");
        sqlList.add(sql);

        //WEBメール [アカウント代理人] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_ACCOUNT_USER_PROXY");
        sql.addSql(" (");
        sql.addSql("   WAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール [送信先リスト] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_DESTLIST");
        sql.addSql(" (");
        sql.addSql("   WDL_SID    integer not null,");
        sql.addSql("   WDL_NAME   varchar(100) not null,");
        sql.addSql("   WDL_BIKO   varchar(1000),");
        sql.addSql("   WDL_AUID   integer not null,");
        sql.addSql("   WDL_ADATE  timestamp not null,");
        sql.addSql("   WDL_EUID   integer not null,");
        sql.addSql("   WDL_EDATE  timestamp not null,");
        sql.addSql("   primary key(WDL_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール [送信先リスト_送信先] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_DESTLIST_ADDRESS");
        sql.addSql(" (");
        sql.addSql("   WDL_SID    integer not null,");
        sql.addSql("   WDA_TYPE   integer not null,");
        sql.addSql("   WDA_SID    integer not null,");
        sql.addSql("   WDA_ADRNO  integer not null,");
        sql.addSql("   primary key(WDL_SID, WDA_TYPE, WDA_SID, WDA_ADRNO)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール [送信先リスト_アクセス設定] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table WML_DESTLIST_ACCESS_CONF");
        sql.addSql(" (");
        sql.addSql("   WDL_SID      integer not null,");
        sql.addSql("   WLA_KBN      integer not null,");
        sql.addSql("   WLA_USR_SID  integer not null,");
        sql.addSql("   WLA_AUTH     integer not null,");
        sql.addSql("   primary key(WDL_SID, WLA_KBN, WLA_USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //回覧板 [回覧先ユーザ添付情報] テーブルを追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_USER_BIN");
        sql.addSql(" (");
        sql.addSql("   CIF_SID        integer      not null,");
        sql.addSql("   USR_SID        integer      not null,");
        sql.addSql("   CUB_BIN_SID        bigint       not null,");
        sql.addSql("   primary key (CIF_SID, USR_SID, CUB_BIN_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 送信テーブル メールサイズ追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS add SMS_SIZE bigint not null default 0;");
        sqlList.add(sql);

        //ショートメール 草稿テーブル メールサイズ追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS add SMW_SIZE bigint not null default 0;");
        sqlList.add(sql);

        //ショートメール 送信テーブル アカウントSID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS add SAC_SID integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 受信テーブル アカウントSID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS add SAC_SID integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 草稿テーブル アカウントSID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS add SAC_SID integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 宛先テーブル アカウントSID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ASAK add SAC_SID integer not null default 0;");
        sqlList.add(sql);

        //ショートメール ひな形テーブル アカウントSID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_HINA add SAC_SID integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 自動削除 アカウントSID追加
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE SML_ADEL ADD SAC_SID integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 アカウント作成区分追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_ACNT_MAKE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 自動削除区分追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_AUTO_DEL_KBN integer not null default 0;");
        sqlList.add(sql);

        //ショートメール 管理者設定 アカウント使用者設定制限追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_ACNT_USER integer not null default 0;");
        sqlList.add(sql);

        //ショートメール アカウントディスク容量追加
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ACCOUNT_DISK");
        sql.addSql(" (");
        sql.addSql("   SAC_SID         integer      not null,");
        sql.addSql("   SDS_SIZE        bigint        not null,");
        sql.addSql("   primary key (SAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール アカウントソート追加
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ACCOUNT_SORT");
        sql.addSql(" (");
        sql.addSql("   SAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   SAS_SORT        BIGINT       not null,");
        sql.addSql("   primary key (SAC_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール アカウント使用ユーザ追加
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ACCOUNT_USER");
        sql.addSql(" (");
        sql.addSql("   SAC_SID         integer      not null,");
        sql.addSql("   GRP_SID         integer,");
        sql.addSql("   USR_SID         integer");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール アカウント追加
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ACCOUNT");
        sql.addSql(" (");
        sql.addSql("   SAC_SID                integer       not null,");
        sql.addSql("   SAC_TYPE               integer       not null,");
        sql.addSql("   USR_SID                integer,");
        sql.addSql("   SAC_NAME               varchar(100)  not null,");
        sql.addSql("   SAC_BIKO               varchar(1000),");
        sql.addSql("   SAC_SEND_MAILTYPE      integer       not null,");
        sql.addSql("   SAC_JKBN               integer       not null,");
        sql.addSql("   SAC_THEME              integer       not null,");
        sql.addSql("   SAC_QUOTES             integer       not null,");
        sql.addSql("   primary key(SAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール アカウント転送設定
        sql = new SqlBuffer();
        sql.addSql(" create table SML_ACCOUNT_FORWARD");
        sql.addSql(" (");
        sql.addSql("     SAC_SID       integer      not null,");
        sql.addSql("     USR_SID       integer      not null,");
        sql.addSql("     SAF_MAILFW    integer      not null,");
        sql.addSql("     SAF_SMAIL_OP  integer      not null,");
        sql.addSql("     SAF_MAIL_DF   varchar(50),");
        sql.addSql("     SAF_HURIWAKE  integer,");
        sql.addSql("     SAF_ZMAIL1    varchar(50),");
        sql.addSql("     SAF_ZMAIL2    varchar(50),");
        sql.addSql("     SAF_ZMAIL3    varchar(50),");
        sql.addSql("     SAF_AUID      integer      not null,");
        sql.addSql("     SAF_ADATE     timestamp    not null,");
        sql.addSql("     SAF_EUID      integer      not null,");
        sql.addSql("     SAF_EDATE     timestamp    not null,");
        sql.addSql("     primary key (SAC_SID, USR_SID)");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        //スケジュール スケジュール管理者設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_DSP_GROUP integer not null default 0;");
        sqlList.add(sql);

        //スケジュール 管理者設定 タイトル色区分
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_MSG_COLOR_KBN integer not null default 0;");
        sqlList.add(sql);

        //スケジュール 拡張情報 毎年 月
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_EXDATA add SCE_MONTH_YEARLY integer default 0;");
        sqlList.add(sql);

        //スケジュール 拡張情報 毎年 日
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_EXDATA add SCE_DAY_YEARLY integer default 0;");
        sqlList.add(sql);

        //回覧板 回覧先情報
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_VIEW add CAC_SID integer not null default 0;");
        sqlList.add(sql);

        //回覧板 自動削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ADEL add CAC_SID integer not null default 0;");
        sqlList.add(sql);

        //回覧板 ユーザ添付
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER_BIN add CAC_SID integer not null default 0;");
        sqlList.add(sql);

        //回覧板 アカウントソート追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_ACCOUNT_SORT");
        sql.addSql(" (");
        sql.addSql("   CAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   CAS_SORT        BIGINT       not null,");
        sql.addSql("   primary key (CAC_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //回覧板 アカウント使用ユーザ追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_ACCOUNT_USER");
        sql.addSql(" (");
        sql.addSql("   CAC_SID         integer      not null,");
        sql.addSql("   GRP_SID         integer,");
        sql.addSql("   USR_SID         integer");
        sql.addSql(" );");
        sqlList.add(sql);

        //回覧板  アカウント追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_ACCOUNT");
        sql.addSql(" (");
        sql.addSql("   CAC_SID                integer       not null,");
        sql.addSql("   CAC_TYPE               integer       not null,");
        sql.addSql("   USR_SID                integer,");
        sql.addSql("   CAC_NAME               varchar(100)  not null,");
        sql.addSql("   CAC_BIKO               varchar(1000),");
        sql.addSql("   CAC_JKBN               integer       not null,");
        sql.addSql("   CAC_THEME              integer       not null,");
        sql.addSql("   CAC_SML_NTF            integer       not null,");
        sql.addSql("   CAC_MEMO_KBN           integer       not null,");
        sql.addSql("   CAC_MEMO_DAY           integer       not null,");
        sql.addSql("   CAC_KOU_KBN            integer       not null,");
        sql.addSql("   CAC_INIT_KBN           integer       not null,");
        sql.addSql("   primary key(CAC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);


        //回覧板 アカウント作成区分
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INIT add CIN_ACNT_MAKE integer not null default 0;");
        sqlList.add(sql);

        //回覧板 自動削除区分
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INIT add CIN_AUTO_DEL_KBN integer not null default 0;");
        sqlList.add(sql);

        //回覧板 回覧板初期値管理者設定 アカウント使用者設定制限
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INIT add CIN_ACNT_USER integer not null default 0;");
        sqlList.add(sql);

        //掲示板 スレッド情報 登録者グループSID
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_INF add BTI_AGID integer;");
        sqlList.add(sql);

        //掲示板 スレッド情報 更新者グループSID
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_INF add BTI_EGID integer;");
        sqlList.add(sql);

        //掲示板 投稿情報 登録者グループSID
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_WRITE_INF add BWI_AGID integer;");
        sqlList.add(sql);

        //掲示板 投稿情報 更新者グループSID
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_WRITE_INF add BWI_EGID integer;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 ディスク容量制限
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_DISK integer not null default 0;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 ディスク容量上限
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_DISK_SIZE integer;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 ディスク容量警告
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_WARN_DISK integer not null default 0;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 ディスク容量警告 閾値
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_WARN_DISK_TH integer;");
        sqlList.add(sql);

        //掲示板 フォーラム集計情報 ディスク使用量
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_SUM add BFS_SIZE bigint not null default 0;");
        sqlList.add(sql);

        //掲示板 スレッド集計情報 ディスク使用量
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_SUM add BTS_SIZE bigint not null default 0;");
        sqlList.add(sql);

        //掲示板 管理者設定 ショートメール通知設定
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_ADM_CONF add BAC_SML_NTF integer not null default 0;");
        sqlList.add(sql);

        //掲示板 管理者設定 ショートメール通知設定区分
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_ADM_CONF add BAC_SML_NTF_KBN integer;");
        sqlList.add(sql);

        //施設予約 施設予約区分別情報
        sql = new SqlBuffer();
        sql.addSql("   create table RSV_SIS_KYRK");
        sql.addSql("   (");
        sql.addSql("     RSY_SID           integer not null,");
        sql.addSql("     RKY_BUSYO          varchar(50),");
        sql.addSql("     RKY_NAME          varchar(50),");
        sql.addSql("     RKY_NUM           varchar(5),");
        sql.addSql("     RKY_USE_KBN           integer,");
        sql.addSql("     RKY_CONTACT          varchar(50),");
        sql.addSql("     RKY_GUIDE          varchar(50),");
        sql.addSql("     RKY_PARK_NUM           varchar(5),");
        sql.addSql("     RKY_PRINT_KBN           integer,");
        sql.addSql("     RKY_DEST          varchar(50),");
        sql.addSql("     RKY_AUID          integer not null,");
        sql.addSql("     RKY_ADATE         timestamp not null,");
        sql.addSql("     RKY_EUID          integer not null,");
        sql.addSql("     RKY_EDATE         timestamp not null,");
        sql.addSql("     primary key  (RSY_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //施設予約 施設予約拡張区分別情報
        sql = new SqlBuffer();
        sql.addSql("   create table RSV_SIS_KRYRK");
        sql.addSql("   (");
        sql.addSql("     RSR_RSID           integer not null,");
        sql.addSql("     RKR_BUSYO          varchar(50),");
        sql.addSql("     RKR_NAME          varchar(50),");
        sql.addSql("     RKR_NUM           varchar(5),");
        sql.addSql("     RKR_USE_KBN           integer,");
        sql.addSql("     RKR_CONTACT          varchar(50),");
        sql.addSql("     RKR_GUIDE          varchar(50),");
        sql.addSql("     RKR_PARK_NUM           varchar(5),");
        sql.addSql("     RKR_PRINT_KBN           integer,");
        sql.addSql("     RKR_DEST          varchar(50),");
        sql.addSql("     RKR_AUID          integer not null,");
        sql.addSql("     RKR_ADATE         timestamp not null,");
        sql.addSql("     RKR_EUID          integer not null,");
        sql.addSql("     RKR_EDATE         timestamp not null,");
        sql.addSql("     primary key  (RSR_RSID)");
        sql.addSql("   );");
        sqlList.add(sql);


        //施設予約 施設グループ管理者 グループSID追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_ADM add GRP_SID integer not null default -1;");
        sqlList.add(sql);

        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);
        //H2の場合
        if (h2db) {
            sql = new SqlBuffer();
            sql.addSql(" alter table RSV_SIS_ADM DROP primary key;");
            sqlList.add(sql);
        } else {
            //Postgresqlの場合
            sql = new SqlBuffer();
            sql.addSql(" alter table RSV_SIS_ADM drop constraint rsv_sis_adm_pkey");
            sqlList.add(sql);
        }

        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_ADM add primary key (RSG_SID, USR_SID, GRP_SID);");
        sqlList.add(sql);

        //施設予約拡張情報 毎年 月
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_RYRK add RSR_MONTH_YEARLY integer default 0;");
        sqlList.add(sql);

        //施設予約拡張情報 毎年 日
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_SIS_RYRK add RSR_DAY_YEARLY integer default 0;");
        sqlList.add(sql);

        //稟議 管理者設定 ショートメール通知設定
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF add RAR_SML_NTF integer not null default 0;");
        sqlList.add(sql);

        //稟議 管理者設定 ショートメール通知設定区分
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF add RAR_SML_NTF_KBN integer;");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ショートメール通知設定区分
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_ACONF add FAC_SMAIL_SEND_KBN integer not null default 0;");
        sqlList.add(sql);

        //ファイル管理 管理者設定 ショートメール通知設定
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_ACONF add FAC_SMAIL_SEND integer not null default 0;");
        sqlList.add(sql);

        //回覧板 管理者設定
        sql = new SqlBuffer();
        sql.addSql("   create table CIR_ACONF(");
        sql.addSql("       CAF_SMAIL_SEND_KBN      integer   not null,");
        sql.addSql("       CAF_SMAIL_SEND      integer   not null,");
        sql.addSql("       CAF_AUID      integer   not null,");
        sql.addSql("       CAF_ADATE      timestamp not null,");
        sql.addSql("       CAF_EUID        integer   not null,");
        sql.addSql("       CAF_EDATE      timestamp not null");
        sql.addSql("   );");
        sqlList.add(sql);

        //施設予約 管理者設定 ショートメール通知設定区分
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_SMAIL_SEND_KBN integer not null default 0;");
        sqlList.add(sql);

        //施設予約 管理者設定 ショートメール通知設定
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_SMAIL_SEND integer not null default 0;");
        sqlList.add(sql);

        //スケジュール 管理者設定 ショートメール通知設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_SMAIL_SEND_KBN integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_SMAIL integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_SMAIL_GROUP integer not null default 0;");
        sqlList.add(sql);

        //スケジュール スケジュール情報 出欠確認
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add SCD_ATTEND_KBN integer default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add SCD_ATTEND_ANS integer default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add SCD_ATTEND_AU_KBN integer default 1;");
        sqlList.add(sql);

        //スケジュール 個人設定 出欠確認時ショートメール通知
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_SMAIL_ATTEND integer not null default 0;");
        sqlList.add(sql);

        //スケジュール 管理者設定 出欠確認時ショートメール通知
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_SMAIL_ATTEND integer not null default 0;");
        sqlList.add(sql);

        //掲示板 スレッド情報 掲示開始日時
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_INF add BTI_LIMIT_FR_DATE timestamp;");
        sqlList.add(sql);

        //掲示板 スレッド情報 既存情報の更新（掲示開始日時）
        //H2の場合
        if (h2db) {
            sql = new SqlBuffer();
            sql.addSql(" update BBS_THRE_INF set BTI_LIMIT_FR_DATE = ");
            sql.addSql(" formatdatetime(BTI_ADATE, 'yyyy-MM-dd') || ' 00:00:00.000' ");
            sql.addSql(" where BTI_LIMIT = 1;");
            sqlList.add(sql);

        //Postgresqlの場合
        } else {
            sql = new SqlBuffer();
            sql.addSql(" update BBS_THRE_INF set BTI_LIMIT_FR_DATE = ");
            sql.addSql(" cast (to_char(BTI_ADATE, 'yyyy-MM-dd') || ' 00:00:00.000' as timestamp)");
            sql.addSql(" where BTI_LIMIT = 1;");
            sqlList.add(sql);
        }

        //掲示板 フォーラム情報 掲示期間初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_LIMIT integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_LIMIT_DATE integer;");
        sqlList.add(sql);

        //掲示板 フォーラム情報 保存期間
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_KEEP integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_KEEP_DATE_Y integer;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_KEEP_DATE_M integer;");
        sqlList.add(sql);

        //アドレス帳 氏名 姓、氏名 名、氏名カナ 姓、氏名カナ 名
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter ADR_SEI type varchar(30);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter ADR_MEI type varchar(30);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter ADR_SEI_KN type varchar(60);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter ADR_MEI_KN type varchar(60);");
        sqlList.add(sql);

        //アドレス帳役職マスタ 役職名
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_POSITION alter APS_NAME type varchar(30);");
        sqlList.add(sql);

        //ユーザ情報 カテゴリ初期値
        sql = new SqlBuffer();
        sql.addSql(" insert");
        sql.addSql(" into");
        sql.addSql(" CMN_LABEL_USR_CATEGORY(");
        sql.addSql("   LUC_SID,");
        sql.addSql("   LUC_NAME,");
        sql.addSql("   LUC_BIKO,");
        sql.addSql("   LUC_SORT,");
        sql.addSql("   LUC_AUID,");
        sql.addSql("   LUC_ADATE,");
        sql.addSql("   LUC_EUID,");
        sql.addSql("   LUC_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   0,");
        sql.addSql("   '未設定',");
        sql.addSql("   null,");
        sql.addSql("   (select ");
        sql.addSql("      case when max(LUC_SORT) is null then 1");
        sql.addSql("          else max(LUC_SORT) + 1");
        sql.addSql("      end LUC_SORT");
        sql.addSql("    from CMN_LABEL_USR_CATEGORY");
        sql.addSql("   ),");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //スケジュール 管理者設定 初期値設定 公開区分
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_PUBLIC integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_PUBLIC_KBN integer not null default 0;");
        sqlList.add(sql);

        return sqlList;
    }
}