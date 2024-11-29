package jp.groupsession.v2.convert.convert430.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.SaibanDao;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableAfterDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableAfterDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableAfterDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableAfterDao(Connection con) {
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
            for (int i = 0; i < sqlList.size(); i++) {
                SqlBuffer sql = sqlList.get(i);
                log__.info(sql.toLogString());
                log__.info("ショートメール、回覧板　テーブル定義変更中--------" + (i + 1) + "/" + sqlList.size());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

            boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);
            //Postgresの場合
            if (!h2db) {
                long maxOid = __getMaxOid(con);
                //採番テーブルにoid追加
                SaibanDao saibanDao = new SaibanDao();
                saibanDao.setCon(con);
                SaibanModel saibanModel =  new SaibanModel();
                saibanModel.setSbnSid(GSConst.SBNSID_OID);
                saibanModel.setSbnSidSub(GSConst.SBNSID_SUB_OID);
                saibanModel.setSbnString(GSConst.SBNSID_SUB_OID);
                saibanModel.setSbnEid(0);
                saibanModel.setSbnEdate(new UDate());
                saibanModel.setSbnNumber(maxOid);
                saibanModel.setSbnAid(0);
                saibanModel.setSbnAdate(new UDate());
                saibanDao.insert(saibanModel);
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

        //ショートメール 自動削除 プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table SML_ADEL drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table SML_ADEL DROP CONSTRAINT sml_adel_pkey;");
        }
        sqlList.add(sql);

        //ショートメール自動削除 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADEL drop USR_SID;");
        sqlList.add(sql);
        //ショートメール自動削除 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADEL add PRIMARY KEY(SAC_SID);");
        sqlList.add(sql);

        //草稿宛先 プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table SML_ASAK drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table SML_ASAK drop CONSTRAINT sml_asak_pkey;");
        }
        sqlList.add(sql);
        //草稿宛先 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ASAK drop USR_SID;");
        sqlList.add(sql);
        //草稿宛先 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ASAK add PRIMARY KEY(SAC_SID, SMS_SID, SMJ_SENDKBN);");
        sqlList.add(sql);

        //ショートメール 転送設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_MAILFW;");
        sqlList.add(sql);
        //ショートメール 開封設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_SMAIL_OP;");
        sqlList.add(sql);
        //ショートメール 転送デフォルトアドレス
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_MAIL_DF;");
        sqlList.add(sql);
        //ショートメール 転送振り分け設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_HURIWAKE;");
        sqlList.add(sql);
        //ショートメール 転送アドレス1
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_ZMAIL1;");
        sqlList.add(sql);
        //ショートメール 転送アドレス2
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_ZMAIL2;");
        sqlList.add(sql);
        //ショートメール 転送アドレス3
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER drop SML_ZMAIL3;");
        sqlList.add(sql);


        //ひな形 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_HINA drop USR_SID;");
        sqlList.add(sql);

        //草稿 プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table SML_WMEIS drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table SML_WMEIS drop CONSTRAINT sml_wmeis_pkey;");
        }
        sqlList.add(sql);
        //草稿 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS drop USR_SID;");
        sqlList.add(sql);
        //草稿 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS add PRIMARY KEY(SMW_SID);");
        sqlList.add(sql);

        //受信 プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table SML_JMEIS drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table SML_JMEIS drop CONSTRAINT sml_jmeis_pkey;");
        }
        sqlList.add(sql);
        //受信 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS drop USR_SID;");
        sqlList.add(sql);
        //受信 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_JMEIS add PRIMARY KEY(SAC_SID, SMJ_SID);");
        sqlList.add(sql);

        //送信 インデックス削除
        sql = new SqlBuffer();
        sql.addSql(" drop index SML_SMEIS_INDEX1;");
        sqlList.add(sql);
        //送信 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS drop USR_SID;");
        sqlList.add(sql);
        //インデックス作成
        sql = new SqlBuffer();
        sql.addSql(" create index SML_SMEIS_INDEX1 on SML_SMEIS(SAC_SID,SMS_JKBN);");
        sqlList.add(sql);

        //送信メール区分
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS add SMS_TYPE integer not null default 0;");
        sqlList.add(sql);

        //草稿メール区分
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS add SMW_TYPE integer not null default 0;");
        sqlList.add(sql);

        //ショートメール ラベル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_LABEL");
        sql.addSql(" (");
        sql.addSql("   SLB_SID   integer      not null,");
        sql.addSql("   USR_SID   integer      not null,");
        sql.addSql("   SLB_NAME  varchar(100) not null,");
        sql.addSql("   SLB_TYPE  integer      not null,");
        sql.addSql("   SAC_SID   integer      not null,");
        sql.addSql("   SLB_ORDER integer      not null,");
        sql.addSql("   primary key(SLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール ラベル 並び順
        sql = new SqlBuffer();
        sql.addSql(" create table SML_LABEL_SORT");
        sql.addSql(" (");
        sql.addSql("   SAC_SID         integer      not null,");
        sql.addSql("   USR_SID         integer      not null,");
        sql.addSql("   SLB_SID         integer      not null,");
        sql.addSql("   SLS_SORTKEY     integer      not null,");
        sql.addSql("   SLS_ORDER       integer      not null,");
        sql.addSql("   primary key (SAC_SID, USR_SID, SLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 受信メールラベル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_JMEIS_LABEL");
        sql.addSql(" (");
        sql.addSql("   SMJ_SID      integer      not null,");
        sql.addSql("   SLB_SID      integer      not null,");
        sql.addSql("   SAC_SID      integer      not null,");
        sql.addSql("   primary key(SMJ_SID, SLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 送信メールラベル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_SMEIS_LABEL");
        sql.addSql(" (");
        sql.addSql("   SMS_SID      integer      not null,");
        sql.addSql("   SLB_SID      integer      not null,");
        sql.addSql("   SAC_SID      integer      not null,");
        sql.addSql("   primary key(SMS_SID, SLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール 草稿メールラベル
        sql = new SqlBuffer();
        sql.addSql(" create table SML_WMEIS_LABEL");
        sql.addSql(" (");
        sql.addSql("   SMW_SID      integer      not null,");
        sql.addSql("   SLB_SID      integer      not null,");
        sql.addSql("   SAC_SID      integer      not null,");
        sql.addSql("   primary key(SMW_SID, SLB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール フィルタ
        sql = new SqlBuffer();
        sql.addSql(" create table SML_FILTER");
        sql.addSql(" (");
        sql.addSql("   SFT_SID               integer not null,");
        sql.addSql("   USR_SID               integer not null,");
        sql.addSql("   SFT_NAME              varchar(100) not null,");
        sql.addSql("   SFT_TYPE              integer not null,");
        sql.addSql("   SAC_SID               integer,");
        sql.addSql("   SFT_TEMPFILE          integer not null,");
        sql.addSql("   SFT_ACTION_LABEL      integer not null,");
        sql.addSql("   SLB_SID               integer,");
        sql.addSql("   SFT_ACTION_READ       integer not null,");
        sql.addSql("   SFT_ACTION_DUST       integer not null,");
        sql.addSql("   SFT_CONDITION         integer not null,");
        sql.addSql("   primary key(SFT_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール フィルタ 並び順
        sql = new SqlBuffer();
        sql.addSql(" create table SML_FILTER_SORT");
        sql.addSql(" (");
        sql.addSql("   SAC_SID         integer      not null,");
        sql.addSql("   SFT_SID         integer      not null,");
        sql.addSql("   SFS_SORT        bigint       not null,");
        sql.addSql("   primary key (SAC_SID, SFT_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //ショートメール フィルタ コンディション
        sql = new SqlBuffer();
        sql.addSql(" create table SML_FILTER_CONDITION");
        sql.addSql(" (");
        sql.addSql("   SFT_SID         integer      not null,");
        sql.addSql("   SFC_NUM         integer      not null,");
        sql.addSql("   SFC_TYPE        integer      not null,");
        sql.addSql("   SFC_EXPRESSION  integer      not null,");
        sql.addSql("   SFC_TEXT        varchar(256) not null,");
        sql.addSql("   primary key(SFT_SID, SFC_NUM)");
        sql.addSql(" );");
        sqlList.add(sql);

        //送信メール プレーンテキスト
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS add SMS_BODY_PLAIN text;");
        //sql.addSql(" alter table SML_SMEIS ALTER COLUMN SMS_BODY_PLAIN TYPE text;");
        sqlList.add(sql);

        //送信メール 本文
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS alter column SMS_BODY type text;");
      //sql.addSql(" alter table SML_SMEIS ALTER COLUMN SMS_BODY TYPE text;");
        sqlList.add(sql);

        //草稿メール プレーンテキスト
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS add SMW_BODY_PLAIN text");
        //sql.addSql(" alter table SML_WMEIS ALTER COLUMN SMW_BODY_PLAIN TYPE text;");
        sqlList.add(sql);

        //草稿メール 本文
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS alter column  SMW_BODY type text;");
      //sql.addSql(" alter table SML_WMEIS ALTER COLUMN SMW_BODY TYPE text;");
        sqlList.add(sql);

        //回覧先情報プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table CIR_VIEW drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table CIR_VIEW drop CONSTRAINT cir_view_pkey;");
        }
        sqlList.add(sql);
        //回覧先情報 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_VIEW drop USR_SID;");
        sqlList.add(sql);
        //回覧先情報 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_VIEW add PRIMARY KEY(CIF_SID, CAC_SID);");
        sqlList.add(sql);

        //回覧板自動削除プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table CIR_ADEL drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table CIR_ADEL drop CONSTRAINT cir_adel_pkey;");
        }
        sqlList.add(sql);
        //回覧板自動削除 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ADEL drop USR_SID;");
        sqlList.add(sql);
        //回覧板自動削除 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ADEL add PRIMARY KEY(CAC_SID);");
        sqlList.add(sql);

        //回覧先ユーザ添付情報プライマリーキー削除
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table CIR_USER_BIN drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table CIR_USER_BIN drop CONSTRAINT cir_user_bin_pkey;");
        }
        sqlList.add(sql);
        //回覧先ユーザ添付情報 ユーザSID削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER_BIN drop USR_SID;");
        sqlList.add(sql);
        //回覧先ユーザ添付情報 プライマリーキー設定
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER_BIN add PRIMARY KEY(CIF_SID, CAC_SID, CUB_BIN_SID);");
        sqlList.add(sql);
        //回覧板 ショートメール転送設定削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER drop CUR_SML_NTF;");
        sqlList.add(sql);
        //回覧板 メモ区分削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER drop CUR_MEMO_KBN;");
        sqlList.add(sql);
        //回覧板 メモ修正期間削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER drop CUR_MEMO_DAY;");
        sqlList.add(sql);
        //回覧板 公開区分削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER drop CUR_KOU_KBN;");
        sqlList.add(sql);
        //回覧板 初期値設定削除
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER drop CUR_INIT_KBN;");
        sqlList.add(sql);

        //回覧板 編集区分
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INF add CIF_EKBN integer not null default 0;");
        sqlList.add(sql);
        //回覧板 編集日付
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INF add CIF_EDIT_DATE timestamp;");
        sqlList.add(sql);

        //ファイル管理 ディレクトリ更新グループ
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_DIRECTORY add FDR_EGID integer default 0;");
        sqlList.add(sql);

        //ファイル管理 ファイル履歴 更新グループ
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_FILE_REKI add FFR_EGID integer default 0;");
        sqlList.add(sql);

        //ショートメール ショートメールタイトル
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_SMEIS alter column SMS_TITLE type VARCHAR(100);");
        sqlList.add(sql);
        //ショートメール 草稿タイトル
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_WMEIS alter column SMW_TITLE type VARCHAR(100);");
        sqlList.add(sql);
        //回覧板 回覧板タイトル
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INF alter column CIF_TITLE type VARCHAR(100);");
        sqlList.add(sql);
        //プロジェクト プロジェクト名
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_PRJDATA alter column PRJ_NAME type VARCHAR(100);");
        sqlList.add(sql);
        //プロジェクト プロジェクト名
        sql = new SqlBuffer();
        sql.addSql(" alter table PRJ_PRJDATA_TMP alter column PRT_NAME type VARCHAR(100);");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] pg_largeobjectに登録されているoidの最大値を取得
     * <br>[解  説]
     * <br>[備  考] レプリケーション時はマスタの値を取得する
     * @param con コネクション
     * @return OID
     * @throws SQLException 実行例外
     */
    private static long __getMaxOid(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long oid = 0;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" /*NO LOAD BALANCE*/ ");
            sql.addSql(" select max(loid) as maxOid from pg_largeobject;");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                oid = rs.getLong("maxOid");
            }

            oid++;

        } catch (SQLException e) {
            log__.error("oidの最大値の取得に失敗しました。");
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return oid;
    }
}