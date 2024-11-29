package jp.groupsession.v2.convert.convert460.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.6.0へコンバートする際に使用する
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
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

        //WEBメール メール情報  登録日時, 編集元メール, 編集種別
        sql = new SqlBuffer();
        sql.addSql("alter table WML_MAILDATA add WMD_ADATE timestamp;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_MAILDATA add WMD_ORIGIN bigint;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table WML_MAILDATA add WMD_EDIT_TYPE integer;");
        sqlList.add(sql);

        //WEBメール 送信先 メールアドレス(検索用)
        sql = new SqlBuffer();
        sql.addSql("alter table WML_SENDADDRESS add WSA_ADDRESS_SRH varchar(768);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update WML_SENDADDRESS set WSA_ADDRESS_SRH = (");
        sql.addSql("   select lower(WSA_ADDRESS) from WML_SENDADDRESS SENDADDRESS");
        sql.addSql("   where WML_SENDADDRESS.WMD_MAILNUM = SENDADDRESS.WMD_MAILNUM");
        sql.addSql("   and WML_SENDADDRESS.WSA_NUM = SENDADDRESS.WSA_NUM");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール アカウント 署名 自動挿入
        sql = new SqlBuffer();
        sql.addSql("alter table WML_ACCOUNT add WAC_SIGN_AUTO integer not null default 0;");
        sqlList.add(sql);

        //ユーザ情報管理者設定 QRコード表示区分
        sql = new SqlBuffer();
        sql.addSql("alter table USR_ACONF add UAD_QR_KBN integer default 1 not null;");
        sqlList.add(sql);

        //掲示板 スレッド情報に「重要度」追加
        sql = new SqlBuffer();
        sql.addSql("alter table BBS_THRE_INF add BTI_IMPORTANCE integer default 0 not null;");
        sqlList.add(sql);

        //BBSスレッド情報テーブル インデクス漏れ対応
        if (h2db) {
            sql = new SqlBuffer();
            sql.addSql("create index if not exists ");
            sql.addSql("   BBS_THRE_INF_INDEX1 on BBS_THRE_INF(BTI_LIMIT_DATE);");
            sqlList.add(sql);
        }

        //掲示板 フォーラム情報_親フォーラムSID
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_PARENT_SID integer not null default 0;");
        sqlList.add(sql);

        //掲示板 フォーラム情報_階層レベル
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_LEVEL integer not null default 1;");
        sqlList.add(sql);

        //掲示板 投稿本文添付情報
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_BODY_BIN");
        sql.addSql(" (");
        sql.addSql("  BWI_SID          integer      not null,");
        sql.addSql("  BBB_FILE_SID          integer      not null,");
        sql.addSql("  BIN_SID          bigint      not null,");
        sql.addSql("  primary key (BWI_SID, BBB_FILE_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板 投稿情報_内容 型をtext型に変更
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_WRITE_INF alter column BWI_VALUE type text;");
        sqlList.add(sql);

        //掲示板 投稿情報_内容(プレーンテキスト)
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_WRITE_INF add BWI_VALUE_PLAIN text;");
        sqlList.add(sql);

        //掲示板 投稿情報_投稿タイプ
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_WRITE_INF add BWI_TYPE integer not null default 0;");
        sqlList.add(sql);

        //掲示板 フォーラム情報_スレッドテンプレート内容 型をtext型に変更
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF alter column BFI_TEMPLATE type text;");
        sqlList.add(sql);

        //掲示板 フォーラム情報_スレッドテンプレートタイプ
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_TEMPLATE_TYPE integer not null default 0;");
        sqlList.add(sql);

        //掲示板 親フォーラムのメンバー設定準拠
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF"
                + " add BFI_FOLLOW_PARENT_MEM integer not null default 0;");
        sqlList.add(sql);

        //掲示板 掲示板管理者設定_投稿タイプ初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_ADM_CONF add BAC_INI_POST_TYPE integer not null default 0;");
        sqlList.add(sql);

        //掲示板 掲示板管理者設定_投稿タイプ初期値区分
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_ADM_CONF"
                + " add BAC_INI_POST_TYPE_KBN integer not null default 0;");
        sqlList.add(sql);

        //掲示板 掲示板個人設定_投稿タイプ初期値
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_USER add BUR_INI_POST_TYPE integer not null default 0;");
        sqlList.add(sql);

        //掲示板 スレッド集計情報 添付ファイル
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_SUM add BTS_TEMPFLG integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update BBS_THRE_SUM set BTS_TEMPFLG = 1");
        sql.addSql(" where BTI_SID in (");
        sql.addSql("   select BTI_SID from BBS_WRITE_INF, BBS_BIN");
        sql.addSql("   where BBS_WRITE_INF.BWI_SID = BBS_BIN.BWI_SID");
        sql.addSql(" );");
        sqlList.add(sql);

        //スケジュール 時間マスタ追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_AM_FR_H integer not null default 9;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_AM_FR_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_AM_TO_H integer not null default 12;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_AM_TO_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_PM_FR_H integer not null default 13;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_PM_FR_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_PM_TO_H integer not null default 18;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_PM_TO_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_ALL_FR_H integer not null default 9;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_ALL_FR_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_ALL_TO_H integer not null default 18;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_ALL_TO_M integer not null default 0;");
        sqlList.add(sql);

        //施設予約 時間マスタ追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_AM_FR_H integer not null default 9;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_AM_FR_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_AM_TO_H integer not null default 12;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_AM_TO_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_PM_FR_H integer not null default 13;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_PM_FR_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_PM_TO_H integer not null default 18;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_PM_TO_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_ALL_FR_H integer not null default 9;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_ALL_FR_M integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_ALL_TO_H integer not null default 18;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_ALL_TO_M integer not null default 0;");
        sqlList.add(sql);

        //アドレス帳 編集権限制限設定追加
        sql = new SqlBuffer();
        sql.addSql(" create table ADR_AREST");
        sql.addSql(" (");
        sql.addSql("   GRP_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   primary key (GRP_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ACONF add AAC_AREST_KBN integer not null default 0;");
        sqlList.add(sql);

        //回覧板 登録権限制限設定追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_AREST");
        sql.addSql(" (");
        sql.addSql("   GRP_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   primary key (GRP_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACONF add CAF_AREST_KBN integer not null default 0;");
        sqlList.add(sql);
        //掲示板 モバイル不整合データの改修（掲示開始日時）
        //H2の場合
        if (h2db) {
            sql = new SqlBuffer();
            sql.addSql(" update BBS_THRE_INF set BTI_LIMIT_FR_DATE = ");
            sql.addSql(" formatdatetime(BTI_ADATE, 'yyyy-MM-dd') || ' 00:00:00.000' ");
            sql.addSql(" where BTI_LIMIT = 1 AND BTI_LIMIT_FR_DATE is NULL;");
            sqlList.add(sql);

        //Postgresqlの場合
        } else {
            sql = new SqlBuffer();
            sql.addSql(" update BBS_THRE_INF set BTI_LIMIT_FR_DATE = ");
            sql.addSql(" cast (to_char(BTI_ADATE, 'yyyy-MM-dd') || ' 00:00:00.000' as timestamp)");
            sql.addSql(" where BTI_LIMIT = 1 AND BTI_LIMIT_FR_DATE is NULL;");
            sqlList.add(sql);
        }

        return sqlList;
    }
}