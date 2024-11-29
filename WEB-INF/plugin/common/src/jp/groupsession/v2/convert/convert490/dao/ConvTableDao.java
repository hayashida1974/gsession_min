package jp.groupsession.v2.convert.convert490.dao;

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
 * <br>[解  説] v4.9.0へコンバートする際に使用する
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

        // WEBメール 管理者設定情報の初期データ登録(既存データが無い場合のみ)
        WmlAconfInitDataDao wadDao = new WmlAconfInitDataDao();
        wadDao.convert(getCon());

        //稟議申請時 存在しないテンプレートを参照して稟議の申請が行えていた不具合対応
        RngRtpErrorDataConvertDao rtpErrorDao = new RngRtpErrorDataConvertDao();
        rtpErrorDao.convert(getCon());
        //チャットプラグイン追加
        ConvChatDao chtConvDao = new ConvChatDao();
        chtConvDao.convert(getCon());
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

        /*------------チューニング------*/
        sql = new SqlBuffer();
        sql.addSql(" drop index if exists NTP_DATA_INDEX2;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" drop index if exists CIR_INF_INDEX1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" drop index if exists NTP_DATA_INDEX3;");
        sqlList.add(sql);


        //スケジュール 日報次のアクション取得 index追加
        sql = new SqlBuffer();
        sql.addSql(" create index NTP_DATA_INDEX2");
        sql.addSql(" ON NTP_DATA(USR_SID, NIP_ACTDATE_KBN, NIP_ACTION_DATE);");
        sqlList.add(sql);

        //回覧版送信フォルダ検索 indexを追加
        sql = new SqlBuffer();
        sql.addSql(" create index CIR_INF_INDEX1 on CIR_INF(CIF_AUID);");
        sqlList.add(sql);

        //日報 タイムライン検索用 index追加
        sql = new SqlBuffer();
        sql.addSql(" create index NTP_DATA_INDEX3");
        sql.addSql(" on NTP_DATA(NIP_DATE, NIP_EDATE, USR_SID);");
        sqlList.add(sql);
        /*-------------------------------*/

        /*------------セキュア認証対応------*/
        //個人情報編集権限設定
        sql = new SqlBuffer();
        sql.addSql("alter table CMN_PCONF_EDIT add CPE_OTPSEND_KBN integer not null default 1;");
        sqlList.add(sql);

        //ワンタイムパスワード設定情報
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_OTP_CONF");
        sql.addSql("   (");
        sql.addSql("    COC_USE_OTP         integer not null,");
        sql.addSql("    COC_OTP_USER        integer not null,");
        sql.addSql("    COC_OTP_USER_TYPE   integer not null,");
        sql.addSql("    COC_OTP_IPCOND      integer not null,");
        sql.addSql("    COC_OTP_IP          varchar(256),");
        sql.addSql("    COC_SMTP_URL        varchar(200),");
        sql.addSql("    COC_SMTP_PORT       varchar(5),");
        sql.addSql("    COC_SMTP_SSLUSE     integer not null,");
        sql.addSql("    COC_SMTP_FROM       varchar(200),");
        sql.addSql("    COC_SMTP_USER       varchar(100),");
        sql.addSql("    COC_SMTP_PASS       varchar(140)");
        sql.addSql("   );");

        sqlList.add(sql);
        //ワンタイムパスワード使用者選択情報
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_OTP_USER");
        sql.addSql("   (");
        sql.addSql("    USR_SID integer not null,");
        sql.addSql("    GRP_SID integer not null,");
        sql.addSql("    primary key (USR_SID, GRP_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //ワンタイムパスワード発行情報
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_OTP_TOKEN");
        sql.addSql("   (");
        sql.addSql("    COT_TOKEN   varchar(256) not null,");
        sql.addSql("    USR_SID     integer not null,");
        sql.addSql("    COT_PASS    varchar(4),");
        sql.addSql("    COT_LIMIT_DATE  timestamp,");
        sql.addSql("    COT_DATE   timestamp,");
        sql.addSql("    primary key (COT_TOKEN)");
        sql.addSql("   );");
        sqlList.add(sql);

        //ワンタイムパスワード通知アドレス登録画面トークン情報
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_OTP_ATOKEN");
        sql.addSql("   (");
        sql.addSql("    COA_TOKEN   varchar(256) not null,");
        sql.addSql("    USR_SID     integer not null,");
        sql.addSql("    COA_PASS    varchar(4),");
        sql.addSql("    COA_ADDRESS varchar(256),");
        sql.addSql("    COA_LIMIT_DATE  timestamp,");
        sql.addSql("    COA_DATE    timestamp,");
        sql.addSql("    primary key (COA_TOKEN)");
        sql.addSql("   );");
        sqlList.add(sql);

        //ユーザ情報
        sql = new SqlBuffer();
        sql.addSql("alter table CMN_USRM_INF add USI_OTPSEND_ADDRESS varchar(256);");
        sqlList.add(sql);

        //WEBAPI基本設定
        sql = new SqlBuffer();
        sql.addSql(" create table API_CONF");
        sql.addSql("   (");
        sql.addSql("    APC_TOAKEN_USE integer not null,");
        sql.addSql("    APC_TOAKEN_IP varchar(256),");
        sql.addSql("    APC_TOAKEN_LIFE integer not null,");
        sql.addSql("    APC_BASIC_USE integer not null,");
        sql.addSql("    APC_BASIC_IP varchar(256)");
        sql.addSql("   );");
        sqlList.add(sql);

        //既存環境用インサート
        sql = new SqlBuffer();
        sql.addSql(" insert into API_CONF");
        sql.addSql("   (");
        sql.addSql("    APC_TOAKEN_USE,");
        sql.addSql("    APC_TOAKEN_IP,");
        sql.addSql("    APC_TOAKEN_LIFE,");
        sql.addSql("    APC_BASIC_USE,");
        sql.addSql("    APC_BASIC_IP");
        sql.addSql("   ) values ( ");
        sql.addSql("    1,");
        sql.addSql("    '',");
        sql.addSql("    14,");
        sql.addSql("    1,");
        sql.addSql("    ''");
        sql.addSql("   );");
        sqlList.add(sql);

        //認証トークン情報
        sql = new SqlBuffer();
        sql.addSql(" create table API_TOKEN");
        sql.addSql("   (");
        sql.addSql("    APT_TOKEN varchar(256),");
        sql.addSql("    USR_SID integer not null,");
        sql.addSql("    APT_CLIENT integer not null,");
        sql.addSql("    APT_CLIENT_ID varchar(256),");
        sql.addSql("    APT_IP varchar(40),");
        sql.addSql("    APT_JKBN integer not null,");
        sql.addSql("    APT_LIMIT_DATE timestamp not null,");
        sql.addSql("    APT_AUID integer not null,");
        sql.addSql("    APT_ADATE timestamp not null,");
        sql.addSql("    APT_EUID integer not null,");
        sql.addSql("    APT_EDATE timestamp not null,");
        sql.addSql("    primary key (APT_TOKEN)");
        sql.addSql("   );");
        sqlList.add(sql);
        /*-------------------------------*/

        /*--------------No713 稟議日時バッチで複写用経路ステップから参照される経路テンプレートが削除されてしまう不具合----*/
        //参照先が消えてしまった複写用経路ステップを任意経路の選択状態として復元する
        sql = new SqlBuffer();
        sql.addSql(" delete ");
        sql.addSql("        from RNG_COPY_KEIROSTEP_SELECT ");
        sql.addSql("        where  ");
        sql.addSql("         RKS_SID in ( ");
        sql.addSql("          select ");
        sql.addSql("           RKS_SID  ");
        sql.addSql("          from RNG_COPY_KEIRO_STEP  ");
        sql.addSql("          where ");
        sql.addSql("           RTK_SID >= 0 ");
        sql.addSql("           and not exists ( ");
        sql.addSql("            select ");
        sql.addSql("             RTK_SID  ");
        sql.addSql("            from RNG_TEMPLATE_KEIRO  ");
        sql.addSql("            where ");
        sql.addSql("             RTK_SID=RNG_COPY_KEIRO_STEP.RTK_SID) ");
        sql.addSql("         ); ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into ");
        sql.addSql("        RNG_COPY_KEIROSTEP_SELECT( ");
        sql.addSql("         RKS_SID, ");
        sql.addSql("         RCK_SORT, ");
        sql.addSql("         USR_SID, ");
        sql.addSql("         GRP_SID, ");
        sql.addSql("         POS_SID ");
        sql.addSql("       ) ");
        sql.addSql("       select ");
        sql.addSql("        RNG_KEIROSTEP_SELECT.RKS_SID, ");
        sql.addSql("        0, ");
        sql.addSql("        RNG_KEIROSTEP_SELECT.USR_SID, ");
        sql.addSql("        RNG_KEIROSTEP_SELECT.GRP_SID, ");
        sql.addSql("        RNG_KEIROSTEP_SELECT.POS_SID ");
        sql.addSql("        from RNG_KEIROSTEP_SELECT ");
        sql.addSql("        where  ");
        sql.addSql("         RNG_KEIROSTEP_SELECT.RKS_SID in ( ");
        sql.addSql("          select ");
        sql.addSql("           RKS_SID  ");
        sql.addSql("          from RNG_COPY_KEIRO_STEP  ");
        sql.addSql("          where ");
        sql.addSql("           RTK_SID >= 0 ");
        sql.addSql("           and not exists ( ");
        sql.addSql("            select ");
        sql.addSql("             RTK_SID  ");
        sql.addSql("            from RNG_TEMPLATE_KEIRO  ");
        sql.addSql("            where ");
        sql.addSql("             RTK_SID=RNG_COPY_KEIRO_STEP.RTK_SID) ");
        sql.addSql("         );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" delete ");
        sql.addSql("          from RNG_COPY_KEIRO_STEP  ");
        sql.addSql("          where ");
        sql.addSql("           RCK_SORT > 0 ");
        sql.addSql("           and RKS_SID in ( ");
        sql.addSql("          select ");
        sql.addSql("           RKS_SID  ");
        sql.addSql("          from RNG_COPY_KEIRO_STEP  ");
        sql.addSql("          where ");
        sql.addSql("           RTK_SID >= 0 ");
        sql.addSql("           and not exists ( ");
        sql.addSql("            select ");
        sql.addSql("             RTK_SID  ");
        sql.addSql("            from RNG_TEMPLATE_KEIRO  ");
        sql.addSql("            where ");
        sql.addSql("             RTK_SID=RNG_COPY_KEIRO_STEP.RTK_SID) ");
        sql.addSql("           );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" update RNG_COPY_KEIRO_STEP ");
        sql.addSql("          set RTK_SID=-1 ");
        sql.addSql("          where ");
        sql.addSql("          RKS_SID in ( ");
        sql.addSql("          select ");
        sql.addSql("           RKS_SID  ");
        sql.addSql("          from RNG_COPY_KEIRO_STEP  ");
        sql.addSql("          where ");
        sql.addSql("           RTK_SID >= 0 ");
        sql.addSql("           and not exists ( ");
        sql.addSql("            select ");
        sql.addSql("             RTK_SID  ");
        sql.addSql("            from RNG_TEMPLATE_KEIRO  ");
        sql.addSql("            where ");
        sql.addSql("             RTK_SID=RNG_COPY_KEIRO_STEP.RTK_SID) ");
        sql.addSql("           );");
        sqlList.add(sql);

        //稟議削除時に複写用稟議経路ステップ情報、複写用稟議経路ステップ選択情報が削除されていなかったため
        //一括削除
        sql = new SqlBuffer();
        sql.addSql(" delete    ");
        sql.addSql("  from RNG_COPY_KEIRO_STEP");
        sql.addSql("  where not exists (");
        sql.addSql("    select * from RNG_KEIRO_STEP");
        sql.addSql("    where RNG_KEIRO_STEP.RKS_SID =");
        sql.addSql("            RNG_COPY_KEIRO_STEP.RKS_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" delete ");
        sql.addSql("  from RNG_COPY_KEIROSTEP_SELECT");
        sql.addSql("  where not exists (");
        sql.addSql("    select * from RNG_KEIRO_STEP");
        sql.addSql("    where RNG_KEIRO_STEP.RKS_SID =");
        sql.addSql("            RNG_COPY_KEIROSTEP_SELECT.RKS_SID);");
        sqlList.add(sql);
        /*-------------------------------*/
        /*------------WebSocket対応（チャット）------*/
        //
        sql = new SqlBuffer();
        // ブラウザ使用情報
        sql.addSql(" create table CMN_BROWSER_INF (");
        sql.addSql("   USR_SID integer not null,");
        sql.addSql("   CBI_SESSION_ID varchar(150) not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" )");
        sql.addSql(" ;");
        sqlList.add(sql);
        /*-------------------------------*/

        /*--------------No741 TLD制限対応----*/
        //
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_TLD_LIMIT integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ADM_CONF add WAD_TLD_LIMIT_TEXT text;");
        sqlList.add(sql);
        /*-------------------------------*/

        return sqlList;
    }




}
