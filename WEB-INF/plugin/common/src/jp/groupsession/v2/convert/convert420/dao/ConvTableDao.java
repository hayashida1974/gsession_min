package jp.groupsession.v2.convert.convert420.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.2.0へコンバートする際に使用する
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

        //安否確認管理者設定
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_ADM_CONF");
        sql.addSql("   (");
        sql.addSql("           USR_SID           integer         not null,");
        sql.addSql("           GRP_SID           integer         not null,");
        sql.addSql("           APA_AUID          integer         not null,");
        sql.addSql("           APA_ADATE         timestamp       not null,");
        sql.addSql("           APA_EUID          integer         not null,");
        sql.addSql("           APA_EDATE         timestamp       not null,");
        sql.addSql("           primary key (USR_SID, GRP_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認共通設定
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_CMN_CONF");
        sql.addSql("   (");
        sql.addSql("           APC_URL_BASE      varchar(600)    not null,");
        sql.addSql("           APC_URL_KBN     integer         not null,");
        sql.addSql("           APC_ADDRESS       varchar(768)    not null,");
        sql.addSql("           APC_SEND_HOST     varchar(300)    not null,");
        sql.addSql("           APC_SEND_PORT     integer         not null,");
        sql.addSql("           APC_SEND_USER     varchar(300)            ,");
        sql.addSql("           APC_SEND_PASS     varchar(300)            ,");
        sql.addSql("           APC_SEND_SSL      integer         not null,");
        sql.addSql("           APC_SMTP_AUTH     integer         not null,");
        sql.addSql("           APC_AUID          integer         not null,");
        sql.addSql("           APC_ADATE         timestamp       not null,");
        sql.addSql("           APC_EUID          integer         not null,");
        sql.addSql("           APC_EDATE         timestamp       not null");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認配信データ
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_HDATA");
        sql.addSql("   (");
        sql.addSql("           APH_SID         integer         not null,");
        sql.addSql("           APH_SUBJECT     varchar(60)             ,");
        sql.addSql("           APH_TEXT1       varchar(3000)            ,");
        sql.addSql("           APH_TEXT2       varchar(3000)            ,");
        sql.addSql("           APH_HUID        integer         not null,");
        sql.addSql("           APH_HDATE       timestamp       not null,");
        sql.addSql("           APH_SUID        integer                 ,");
        sql.addSql("           APH_SDATE       timestamp               ,");
        sql.addSql("           APH_SCOUNT      integer         not null,");
        sql.addSql("           APH_END_FLG     integer         not null,");
        sql.addSql("           APH_EUID        integer                 ,");
        sql.addSql("           APH_EDATE       timestamp               ,");
        sql.addSql("           APH_KNREN_FLG   integer         not null,");
        sql.addSql("           APH_VIEW_MAIN   integer         not null,");
        sql.addSql("           primary key (APH_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否状況データ
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_JDATA");
        sql.addSql("   (");
        sql.addSql("           APH_SID           integer         not null,");
        sql.addSql("           USR_SID           integer         not null,");
        sql.addSql("           APD_MAILADR       varchar(768)            ,");
        sql.addSql("           APD_JOKYO_FLG     integer         not null,");
        sql.addSql("           APD_PLACE_FLG     integer         not null,");
        sql.addSql("           APD_SYUSYA_FLG    integer         not null,");
        sql.addSql("           APD_COMMENT       varchar(3000)           ,");
        sql.addSql("           APD_HDATE         timestamp,");
        sql.addSql("           APD_SCOUNT        integer         not null,");
        sql.addSql("           APD_CDATE         timestamp               ,");
        sql.addSql("           APD_RDATE         timestamp               ,");
        sql.addSql("           APD_HAISIN_FLG    integer         not null,");
        sql.addSql("           APD_AUID          integer         not null,");
        sql.addSql("           APD_ADATE         timestamp       not null,");
        sql.addSql("           APD_EUID          integer         not null,");
        sql.addSql("           APD_EDATE         timestamp       not null,");
        sql.addSql("           primary key (APH_SID, USR_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //送信先データ
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_SDATA");
        sql.addSql("   (");
        sql.addSql("           APH_SID         integer         not null,");
        sql.addSql("           APS_TYPE         integer         not null,");
        sql.addSql("           USR_SID         integer         not null,");
        sql.addSql("           GRP_SID         integer         not null,");
        sql.addSql("           primary key (APH_SID, APS_TYPE, USR_SID, GRP_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //メールテンプレート
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_MTEMP");
        sql.addSql("   (");
        sql.addSql("           APM_SID           integer         not null,");
        sql.addSql("           APM_TITLE         varchar(60)     not null,");
        sql.addSql("           APM_SUBJECT       varchar(60)             ,");
        sql.addSql("           APM_TEXT1         varchar(3000)            ,");
        sql.addSql("           APM_TEXT2         varchar(3000)            ,");
        sql.addSql("           APM_AUID          integer         not null,");
        sql.addSql("           APM_ADATE         timestamp       not null,");
        sql.addSql("           APM_EUID          integer         not null,");
        sql.addSql("           APM_EDATE         timestamp       not null,");
        sql.addSql("           primary key (APM_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認個人設定
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_PRI_CONF");
        sql.addSql("   (");
        sql.addSql("           USR_SID           integer         not null,");
        sql.addSql("           APP_MAIN_KBN      integer         not null,");
        sql.addSql("           APP_LIST_COUNT    integer         not null,");
        sql.addSql("           APP_DSP_GROUP     integer         not null,");
        sql.addSql("           APP_DSP_MYGROUP   integer                 ,");
        sql.addSql("           APP_ALLGROUP_FLG     integer         not null,");
        sql.addSql("           APP_MAILADR       varchar(768)            ,");
        sql.addSql("           APP_TELNO         varchar(60)             ,");
        sql.addSql("           APP_AUID          integer         not null,");
        sql.addSql("           APP_ADATE         timestamp       not null,");
        sql.addSql("           APP_EUID          integer         not null,");
        sql.addSql("           APP_EDATE         timestamp       not null,");
        sql.addSql("           primary key (USR_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認 初期テンプレートの登録
        sql = new SqlBuffer();
        sql.addSql("   insert into ANP_MTEMP");
        sql.addSql("   (");
        sql.addSql("           APM_SID,");
        sql.addSql("           APM_TITLE,");
        sql.addSql("           APM_SUBJECT,");
        sql.addSql("           APM_TEXT1,");
        sql.addSql("           APM_TEXT2,");
        sql.addSql("           APM_AUID,");
        sql.addSql("           APM_ADATE,");
        sql.addSql("           APM_EUID,");
        sql.addSql("           APM_EDATE");
        sql.addSql("   ) values (");
        sql.addSql("           1,");
        sql.addSql("           '災害に伴う安否状況の確認',");
        sql.addSql("           '災害に伴う安否状況の確認',");
        sql.addSql("           '社員各位\n\n先ほど発生した災害に伴い安否状況を確認しております。\n"
                + "本メールが届いた方は、下記URLより速やかに安否状況の登録をお願いします。',");
        sql.addSql("           '',");
        sql.addSql("           0,");
        sql.addSql("           current_timestamp,");
        sql.addSql("           0,");
        sql.addSql("           current_timestamp");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認 初期テンプレートの採番
        sql = new SqlBuffer();
        sql.addSql("   insert into CMN_SAIBAN");
        sql.addSql("   (");
        sql.addSql("           SBN_SID,");
        sql.addSql("           SBN_SID_SUB,");
        sql.addSql("           SBN_NUMBER,");
        sql.addSql("           SBN_STRING,");
        sql.addSql("           SBN_AID,");
        sql.addSql("           SBN_ADATE,");
        sql.addSql("           SBN_EID,");
        sql.addSql("           SBN_EDATE");
        sql.addSql("   ) values (");
        sql.addSql("           'anpi',");
        sql.addSql("           'mailtemplate',");
        sql.addSql("           1,");
        sql.addSql("           'mailtemplate',");
        sql.addSql("           0,");
        sql.addSql("           current_timestamp,");
        sql.addSql("           0,");
        sql.addSql("           current_timestamp");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認 テンプレートの並び順
        sql = new SqlBuffer();
        sql.addSql("   create table ANP_MTEMP_SORT");
        sql.addSql("   (");
        sql.addSql("           APM_SID           integer         not null,");
        sql.addSql("           AMS_SORT      integer         not null,");
        sql.addSql("           primary key (APM_SID, AMS_SORT)");
        sql.addSql("   );");
        sqlList.add(sql);

        //安否確認 初期テンプレートの並び順
        sql = new SqlBuffer();
        sql.addSql("   insert into ANP_MTEMP_SORT");
        sql.addSql("   (");
        sql.addSql("           APM_SID,");
        sql.addSql("           AMS_SORT");
        sql.addSql("   ) values (");
        sql.addSql("           1,");
        sql.addSql("           0");
        sql.addSql("   );");
        sqlList.add(sql);

        //日報案件履歴
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_AN_HISTORY");
        sql.addSql(" (");
        sql.addSql("   NAH_SID               integer           not null,");
        sql.addSql("   NAN_SID               integer           not null,");
        sql.addSql("   NAN_CODE              varchar(8)        not null,");
        sql.addSql("   NAN_NAME              varchar(100)      not null,");
        sql.addSql("   NAN_DETIAL            varchar(1000)     not null,");
        sql.addSql("   NAN_MONTH             timestamp         not null,");
        sql.addSql("   NAN_DATE              timestamp         not null,");
        sql.addSql("   ACO_SID               integer           not null,");
        sql.addSql("   ABA_SID               integer           not null,");
        sql.addSql("   NGP_SID               integer                   ,");
        sql.addSql("   NAN_MIKOMI            integer                   ,");
        sql.addSql("   NAN_KIN_MITUMORI      integer                   ,");
        sql.addSql("   NAN_KIN_JUTYU         integer                   ,");
        sql.addSql("   NAN_SYODAN            integer                   ,");
        sql.addSql("   NAN_STATE             integer                   ,");
        sql.addSql("   NCN_SID               integer                   ,");
        sql.addSql("   NAN_AUID              integer                   ,");
        sql.addSql("   NAN_ADATE             timestamp         not null,");
        sql.addSql("   NAN_EUID              integer                   ,");
        sql.addSql("   NAN_EDATE             timestamp         not null,");
        sql.addSql("   primary key (NAH_SID)");
        sql.addSql(" );");
        sqlList.add(sql);


        //日報案件商品履歴
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_AN_SHOHIN_HISTORY");
        sql.addSql(" (");
        sql.addSql("   NAH_SID          integer           not null,");
        sql.addSql("   NAN_SID          integer           not null,");
        sql.addSql("   NHN_SID          integer           not null,");
        sql.addSql("   NAS_AUID         integer                   ,");
        sql.addSql("   NAS_ADATE        timestamp         not null,");
        sql.addSql("   NAS_EUID         integer                   ,");
        sql.addSql("   NAS_EDATE        timestamp         not null,");
        sql.addSql("   primary key (NAH_SID, NAN_SID, NHN_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //日報案件メンバー
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_AN_MEMBER");
        sql.addSql(" (");
        sql.addSql("   NAN_SID          integer           not null,");
        sql.addSql("   USR_SID          integer           not null,");
        sql.addSql("   NAM_AUID         integer                   ,");
        sql.addSql("   NAM_ADATE        timestamp         not null,");
        sql.addSql("   NAM_EUID         integer                   ,");
        sql.addSql("   NAM_EDATE        timestamp         not null,");
        sql.addSql("   primary key (NAN_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //日報案件メンバー履歴
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_AN_MEMBER_HISTORY");
        sql.addSql(" (");
        sql.addSql("   NAH_SID          integer           not null,");
        sql.addSql("   NAN_SID          integer           not null,");
        sql.addSql("   USR_SID          integer           not null,");
        sql.addSql("   NAM_AUID         integer                   ,");
        sql.addSql("   NAM_ADATE        timestamp         not null,");
        sql.addSql("   NAM_EUID         integer                   ,");
        sql.addSql("   NAM_EDATE        timestamp         not null,");
        sql.addSql("   primary key (NAH_SID, NAN_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //日報案件見積もり金額、受注金額日付
        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE NTP_ANKEN add NAN_MITUMORI_DATE "
                  + "timestamp not null default CURRENT_DATE;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE NTP_ANKEN add NAN_JUTYU_DATE "
                  + "timestamp not null default CURRENT_DATE;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" UPDATE NTP_ANKEN set NAN_MITUMORI_DATE = NAN_EDATE;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" UPDATE NTP_ANKEN set NAN_JUTYU_DATE = NAN_EDATE;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" UPDATE NTP_AN_HISTORY set NAN_KIN_MITUMORI = 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" UPDATE NTP_AN_HISTORY set NAN_KIN_JUTYU = 0;");
        sqlList.add(sql);

        //日報商品カテゴリ
        sql = new SqlBuffer();
        sql.addSql(" create table NTP_SHOHIN_CATEGORY");
        sql.addSql(" (");
        sql.addSql(" NSC_SID     integer       not null,");
        sql.addSql(" NSC_NAME    varchar(20)   not null,");
        sql.addSql(" NSC_BIKO    varchar(300),");
        sql.addSql(" NSC_SORT    integer       not null,");
        sql.addSql(" NSC_AUID    integer       not null,");
        sql.addSql(" NSC_ADATE   timestamp     not null,");
        sql.addSql(" NSC_EUID    integer       not null,");
        sql.addSql(" NSC_EDATE   timestamp     not null,");
        sql.addSql(" primary key (NSC_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into NTP_SHOHIN_CATEGORY");
        sql.addSql("  (");
        sql.addSql("   NSC_SID,");
        sql.addSql("   NSC_NAME,");
        sql.addSql("   NSC_BIKO,");
        sql.addSql("   NSC_SORT,");
        sql.addSql("   NSC_AUID,");
        sql.addSql("   NSC_ADATE,");
        sql.addSql("   NSC_EUID,");
        sql.addSql("   NSC_EDATE");
        sql.addSql(" )");
        sql.addSql("  values (1, '未設定', null, 1, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert  into");
        sql.addSql("   CMN_SAIBAN(");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql("   )");
        sql.addSql("    values");
        sql.addSql("   (");
        sql.addSql("     'nippou',");
        sql.addSql("     'shohincategory',");
        sql.addSql("     1,");
        sql.addSql("     'shohincategory',");
        sql.addSql("     0,");
        sql.addSql("     current_timestamp,");
        sql.addSql("     0,");
        sql.addSql("     current_timestamp");
        sql.addSql("  );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table NTP_SHOHIN add NSC_SID integer not null default 1;");
        sqlList.add(sql);

        //所属グループ履歴
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_BELONGM_HISTORY");
        sql.addSql(" (");
        sql.addSql(" GRP_SID       integer      not null,");
        sql.addSql(" USR_SID       integer      not null,");
        sql.addSql(" BEG_AUID      integer      not null,");
        sql.addSql(" BEG_ADATE     timestamp    not null,");
        sql.addSql(" BEG_EUID      integer      not null,");
        sql.addSql(" BEG_EDATE     timestamp    not null,");
        sql.addSql(" BEG_DEFGRP    integer      not null,");
        sql.addSql(" BEG_GRPKBN    integer);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" INSERT INTO CMN_BELONGM_HISTORY SELECT * FROM CMN_BELONGM;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_BELONGM_HISTORY");
        sql.addSql(" add BEG_DATE timestamp not null default CURRENT_DATE;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" UPDATE CMN_BELONGM_HISTORY set BEG_DATE = BEG_ADATE;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" ALTER TABLE CMN_BELONGM_HISTORY ADD PRIMARY KEY(GRP_SID, USR_SID, BEG_DATE);");
        sqlList.add(sql);

        //日報インデックス
        sql = new SqlBuffer();
        sql.addSql(" create index NTP_DATA_INDEX1"
                   + " on NTP_DATA(NIP_DATE, NIP_FR_TIME, NIP_TO_TIME);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index NTP_ANKEN_INDEX1 on NTP_ANKEN(NAN_DATE);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index NTP_AN_HISTORY_INDEX1 on NTP_AN_HISTORY(NAN_DATE);");
        sqlList.add(sql);

        //稟議管理者設定
        sql.addSql(" create table RNG_ACONF");
        sql.addSql(" (");
        sql.addSql("   RAR_DEL_AUTH     integer not null,");
        sql.addSql("   RAR_AUID         integer not null,");
        sql.addSql("   RAR_ADATE        timestamp not null,");
        sql.addSql("   RAR_EUID         integer not null,");
        sql.addSql("   RAR_EDATE        timestamp not null");
        sql.addSql(" );");

        //稟議自動削除設定
        sql.addSql(" create table RNG_AUTODELETE");
        sql.addSql(" (");
        sql.addSql("   RAD_PENDING_KBN    integer not null,");
        sql.addSql("   RAD_PENDING_YEAR   integer,");
        sql.addSql("   RAD_PENDING_MONTH  integer,");
        sql.addSql("   RAD_PENDING_DAY    integer,");
        sql.addSql("   RAD_COMPLETE_KBN   integer not null,");
        sql.addSql("   RAD_COMPLETE_YEAR  integer,");
        sql.addSql("   RAD_COMPLETE_MONTH integer,");
        sql.addSql("   RAD_COMPLETE_DAY   integer,");
        sql.addSql("   RAD_DRAFT_KBN      integer not null,");
        sql.addSql("   RAD_DRAFT_YEAR     integer,");
        sql.addSql("   RAD_DRAFT_MONTH    integer,");
        sql.addSql("   RAD_DRAFT_DAY      integer,");
        sql.addSql("   RAD_AUID           integer not null,");
        sql.addSql("   RAD_ADATE          timestamp not null,");
        sql.addSql("   RAD_EUID           integer not null,");
        sql.addSql("   RAD_EDATE          timestamp not null");
        sql.addSql(" );");

        //削除済みユーザのユーザ所属情報を削除する
        sql = new SqlBuffer();
        sql.addSql(" delete from CMN_BELONGM");
        sql.addSql(" where USR_SID in (");
        sql.addSql("  select USR_SID from CMN_USRM where USR_JKBN = "
                + GSConstUser.USER_JTKBN_DELETE);
        sql.addSql(" );");
        sqlList.add(sql);

        return sqlList;
    }
}