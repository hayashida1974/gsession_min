package jp.groupsession.v2.convert.convert240.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.convert.convert240.model.CvtFileDirectoryModel;
import jp.groupsession.v2.convert.convert240.model.CvtFileFileBinModel;
import jp.groupsession.v2.convert.convert240.model.CvtFileFileRekiModel;
import jp.groupsession.v2.convert.convert240.model.PrjMembersModel;
import jp.groupsession.v2.convert.convert240.model.PrjPrjdataModel;
import jp.groupsession.v2.convert.convert240.model.PrjSimpleDirectoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v2.4.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /** ファイル管理ID */
    private static final String PLUGIN_ID_FILE__ = "file";
    /** 採番区分 キャビネット */
    private static final String SBNSID_SUB_CABINET__ = "cabinet";
    /** 採番区分 ディレクトリ */
    private static final String SBNSID_SUB_DIRECTORY__ = "directory";

    /** ルートディレクトリSID */
    private static final int DIRECTORY_ROOT__ = 0;
    /** フォルダ　バージョン */
    private static final int FOLDER_VERSION__ = 0;
    /** ディレクトリ区分 0=フォルダ */
    private static final int DIRECTORY_FOLDER__ = 0;
    /** ディレクトリ区分 1=ファイル */
    private static final int DIRECTORY_FILE__ = 1;
    /** バージョン管理有無　無効 */
    private static final int VERSION_KBN_OFF__ = 0;
    /** ディレクトリ階層 0=ルート */
    private static final int DIRECTORY_LEVEL_0__ = 0;
    /** 履歴区分 新規 */
    private static final int REKI_KBN_NEW__ = 0;
    /** 管理者ユーザSID */
    private static final int ADMIN_USER_SID__ = 0;

    /** アプリケーションROOTパス*/
    private String appRootPath__ = null;
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
     * @param saiban 採番コントロール
     * @param appRootPath アプリケーションROOTパス
     * @throws SQLException 例外
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     */
    public void convert(MlCountMtController saiban, String appRootPath)
    throws SQLException, IOToolsException, IOException {

        log__.debug("-- DBコンバート開始 --");
        appRootPath__ = appRootPath;
        //新規テーブルのcreate、alter、insertを行う
        createTable();
        //プロジェクト管理→ファイル管理へファイル移行
        createFileData(saiban, appRootPath);

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
            List<SqlBuffer> sqlList = __createSQL(con);

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
     * @param con コネクション
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private List<SqlBuffer> __createSQL(Connection con) throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();
        SqlBuffer sql = new SqlBuffer();

        //共通
        sql.addSql(" alter table CMN_BINF add BIN_FILEKBN integer default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_CMBSORT_CONF");
        sql.addSql(" (");
        sql.addSql("   CSC_USER_SKEY1 integer not null,");
        sql.addSql("   CSC_USER_ORDER1 integer not null,");
        sql.addSql("   CSC_USER_SKEY2 integer not null,");
        sql.addSql("   CSC_USER_ORDER2 integer not null,");
        sql.addSql("   CSC_GROUP_SKBN integer not null,");
        sql.addSql("   CSC_GROUP_SKEY1 integer not null,");
        sql.addSql("   CSC_GROUP_ORDER1 integer not null,");
        sql.addSql("   CSC_GROUP_SKEY2 integer not null,");
        sql.addSql("   CSC_GROUP_ORDER2 integer not null,");
        sql.addSql("   CSC_AUID integer not null,");
        sql.addSql("   CSC_ADATE timestamp not null,");
        sql.addSql("   CSC_EUID integer not null,");
        sql.addSql("   CSC_EDATE timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into");
        sql.addSql(" CMN_CMBSORT_CONF (");
        sql.addSql("   CSC_USER_SKEY1,");
        sql.addSql("   CSC_USER_ORDER1,");
        sql.addSql("   CSC_USER_SKEY2,");
        sql.addSql("   CSC_USER_ORDER2,");
        sql.addSql("   CSC_GROUP_SKBN,");
        sql.addSql("   CSC_GROUP_SKEY1,");
        sql.addSql("   CSC_GROUP_ORDER1,");
        sql.addSql("   CSC_GROUP_SKEY2,");
        sql.addSql("   CSC_GROUP_ORDER2,");
        sql.addSql("   CSC_AUID,");
        sql.addSql("   CSC_ADATE,");
        sql.addSql("   CSC_EUID,");
        sql.addSql("   CSC_EDATE");
        sql.addSql(" )");
        sql.addSql(" values");
        sql.addSql(" (");
        sql.addSql("   3,");
        sql.addSql("   0,");
        sql.addSql("   1,");
        sql.addSql("   0,");
        sql.addSql("   1,");
        sql.addSql("   1,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //掲示板
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_INF add BTI_LIMIT integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_THRE_INF add BTI_LIMIT_DATE timestamp;");
        sqlList.add(sql);

        //ファイル管理
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_ACONF");
        sql.addSql("   (");
        sql.addSql("     FAC_CRT_KBN      integer    not null,");
        sql.addSql("     FAC_FILE_SIZE    integer    not null,");
        sql.addSql("     FAC_SAVE_DAYS    integer    not null,");
        sql.addSql("     FAC_LOCK_KBN     integer    not null,");
        sql.addSql("     FAC_VER_KBN      integer    not null,");
        sql.addSql("     FAC_AUID         integer    not null,");
        sql.addSql("     FAC_ADATE        timestamp  not null,");
        sql.addSql("     FAC_EUID         integer    not null,");
        sql.addSql("     FAC_EDATE        timestamp  not null");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_UCONF");
        sql.addSql("   (");
        sql.addSql("     USR_SID          integer    not null,");
        sql.addSql("     FUC_MAIN_OKINI   integer    not null,");
        sql.addSql("     FUC_MAIN_CALL    integer    not null,");
        sql.addSql("     FUC_RIREKI_CNT   integer    not null,");
        sql.addSql("     FUC_SMAIL_SEND   integer    not null,");
        sql.addSql("     primary key (USR_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_CABINET");
        sql.addSql("   (");
        sql.addSql("     FCB_SID          integer     not null,");
        sql.addSql("     FCB_NAME         varchar(150) not null,");
        sql.addSql("     FCB_ACCESS_KBN   integer     not null,");
        sql.addSql("     FCB_CAPA_KBN     integer     not null,");
        sql.addSql("     FCB_CAPA_SIZE    integer,");
        sql.addSql("     FCB_CAPA_WARN    integer,");
        sql.addSql("     FCB_VER_KBN      integer     not null,");
        sql.addSql("     FCB_VERALL_KBN   integer,");
        sql.addSql("     FCB_BIKO         varchar(3000),");
        sql.addSql("     FCB_SORT         integer     not null,");
        sql.addSql("     FCB_AUID         integer     not null,");
        sql.addSql("     FCB_ADATE        timestamp   not null,");
        sql.addSql("     FCB_EUID         integer     not null,");
        sql.addSql("     FCB_EDATE        timestamp   not null,");
        sql.addSql("     primary key (FCB_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_CABINET_BIN");
        sql.addSql("   (");
        sql.addSql("     FCB_SID          integer      not null,");
        sql.addSql("     BIN_SID          integer      not null,");
        sql.addSql("     primary key (FCB_SID, BIN_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_DIRECTORY");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     FDR_VERSION      integer      not null,");
        sql.addSql("     FCB_SID          integer      not null,");
        sql.addSql("     FDR_PARENT_SID   integer      not null,");
        sql.addSql("     FDR_KBN          integer      not null,");
        sql.addSql("     FDR_VER_KBN      integer      not null,");
        sql.addSql("     FDR_LEVEL        integer      not null,");
        sql.addSql("     FDR_NAME         varchar(150) not null,");
        sql.addSql("     FDR_BIKO         varchar(3000),");
        sql.addSql("     FDR_JTKBN        integer      not null,");
        sql.addSql("     FDR_AUID         integer      not null,");
        sql.addSql("     FDR_ADATE        timestamp    not null,");
        sql.addSql("     FDR_EUID         integer      not null,");
        sql.addSql("     FDR_EDATE        timestamp    not null,");
        sql.addSql("     primary key (FDR_SID, FDR_VERSION)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_DIRECTORY_BIN");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     FDR_VERSION      integer      not null,");
        sql.addSql("     BIN_SID          integer      not null,");
        sql.addSql("     primary key (FDR_SID, FDR_VERSION, BIN_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_FILE_BIN");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     FDR_VERSION      integer      not null,");
        sql.addSql("     BIN_SID          integer      not null,");
        sql.addSql("     FFL_EXT          varchar(150) ,");
        sql.addSql("     FFL_FILE_SIZE    bigint       ,");
        sql.addSql("     FFL_LOCK_KBN     integer      ,");
        sql.addSql("     FFL_LOCK_USER    integer      ,");
        sql.addSql("     primary key (FDR_SID, FDR_VERSION)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_CABINET_ADMIN");
        sql.addSql("   (");
        sql.addSql("     FCB_SID          integer      not null,");
        sql.addSql("     USR_SID          integer      not null,");
        sql.addSql("     primary key (FCB_SID, USR_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_CRT_CONF");
        sql.addSql("   (");
        sql.addSql("     USR_SID          integer      not null,");
        sql.addSql("     USR_KBN          integer      not null,");
        sql.addSql("     primary key (USR_SID, USR_KBN)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_SHORTCUT_CONF");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     USR_SID          integer      not null,");
        sql.addSql("     FSC_ADATE        timestamp    not null,");
        sql.addSql("     primary key (FDR_SID, USR_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_CALL_CONF");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     USR_SID          integer      not null,");
        sql.addSql("     primary key (FDR_SID, USR_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_CALL_DATA");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     USR_SID          integer      not null,");
        sql.addSql("     primary key (FDR_SID, USR_SID)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_ACCESS_CONF");
        sql.addSql("   (");
        sql.addSql("     FCB_SID          integer      not null,");
        sql.addSql("     USR_SID          integer      not null,");
        sql.addSql("     USR_KBN          integer      not null,");
        sql.addSql("     FAA_AUTH         integer      not null,");
        sql.addSql("     primary key (FCB_SID, USR_SID, USR_KBN)");
        sql.addSql("   )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table FILE_FILE_REKI");
        sql.addSql("   (");
        sql.addSql("     FDR_SID          integer      not null,");
        sql.addSql("     FDR_VERSION      integer      not null,");
        sql.addSql("     FFR_FNAME        varchar(150) not null,");
        sql.addSql("     FFR_KBN          integer      not null,");
        sql.addSql("     FFR_PARENT_SID   integer      not null,");
        sql.addSql("     FFR_EUID         integer      not null,");
        sql.addSql("     FFR_EDATE        timestamp    not null,");
        sql.addSql("     primary key (FDR_SID, FDR_VERSION)");
        sql.addSql("   )");
        sqlList.add(sql);

        return sqlList;
    }
    /**
     * <br>[機  能] プロジェクト管理→ファイル管理へファイル情報を移行する
     * <br>[解  説] マイプロジェクトは対象外とし、メンバーに対して書込み閲覧権限を与える
     * <br>[備  考]
     * @param saiban 採番コントロール
     * @param appRootPath アプリケーションROOTパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     */
    public void createFileData(MlCountMtController saiban, String appRootPath)
    throws SQLException, IOToolsException, IOException {

        Connection con = null;
        con = getCon();
        con.setAutoCommit(false);
        UDate now = new UDate();
        try {

            //ディレクトリ情報が登録されているプロジェクト一覧を取得
            ArrayList<PrjPrjdataModel> projList = __getProjectList();
            ArrayList<PrjMembersModel> pmemList = null;
            ArrayList<PrjSimpleDirectoryModel> pdirList = null;
            for (PrjPrjdataModel projMdl : projList) {

                //プロジェクト毎のメンバーを取得
                pmemList = __getPrjMembers(projMdl.getPrjSid());
                //キャビネット情報を登録
                int cabSid = (int) saiban.getSaibanNumber(
                        PLUGIN_ID_FILE__, SBNSID_SUB_CABINET__, ADMIN_USER_SID__);
                __createCabinet(cabSid, now, projMdl, pmemList);

                //Rootディレクトリ
                int rootDirSid = (int) saiban.getSaibanNumber(
                        PLUGIN_ID_FILE__, SBNSID_SUB_DIRECTORY__, ADMIN_USER_SID__);
                __createDirectory(__getRootDirectory(
                        cabSid, rootDirSid, projMdl.getPrjName(), now));

                //キャビネットメンバー
                __createMember(cabSid, now, projMdl, pmemList);

                //プロジェクト管理のROOTディレクトリ一覧を取得 親SID=0を持つLv1ディレクトリ
                pdirList = __getProjDirectoryList(projMdl.getPrjSid(), DIRECTORY_ROOT__);
                for (PrjSimpleDirectoryModel pdirMdl : pdirList) {

                    //ファイル管理用ディレクトリとして登録Lv1
                    int dirSid = __createFileKanriDirectory(
                            pdirMdl, saiban, cabSid, rootDirSid, new Integer(1), now);

                    //ファイル管理のディレクトリ情報を登録Lv2～Lv5
                    __createDirectory(
                            projMdl.getPrjSid(),
                            pdirMdl.getPdrSid(),
                            saiban,
                            cabSid,
                            dirSid,
                            new Integer(2),
                            now);
                }

            }


        } catch (SQLException e) {
            throw e;
        }

    }

    /**
     * プロジェクト情報からキャビネットを作成
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @param now 更新日付
     * @param projMdl プロジェクト情報
     * @param pmemList メンバー情報
     * @throws SQLException SQL実行時例外
     */
    private void __createCabinet(
            long cabSid,
            UDate now,
            PrjPrjdataModel projMdl,
            ArrayList<PrjMembersModel> pmemList)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(cabSid);
            sql.addStrValue(projMdl.getPrjName());
            //メンバーがいる場合はメンバーのみに制限
            if (pmemList.size() > 0) {
                sql.addIntValue(1);
            } else {
                sql.addIntValue(0);
            }
            sql.addIntValue(0);
            sql.addIntValue(0);
            sql.addIntValue(0);
            sql.addIntValue(0);
            sql.addIntValue(1);
            sql.addStrValue("");
            sql.addLongValue(cabSid);
            sql.addIntValue(ADMIN_USER_SID__);
            sql.addDateValue(now);
            sql.addIntValue(ADMIN_USER_SID__);
            sql.addDateValue(now);
;
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * ディレクトリ情報を作成
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ディレクトリ情報
     * @throws SQLException SQL実行時例外
     */
    private void __createDirectory(
            CvtFileDirectoryModel mdl)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_DIRECTORY(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mdl.getFdrSid());
            sql.addIntValue(mdl.getFdrVersion());
            sql.addIntValue(mdl.getFcbSid());
            sql.addIntValue(mdl.getFdrParentSid());
            sql.addIntValue(mdl.getFdrKbn());
            sql.addIntValue(mdl.getFdrVerKbn());
            sql.addIntValue(mdl.getFdrLevel());
            sql.addStrValue(mdl.getFdrName());
            sql.addStrValue(mdl.getFdrBiko());
            sql.addIntValue(mdl.getFdrJtkbn());
            sql.addIntValue(mdl.getFdrAuid());
            sql.addDateValue(mdl.getFdrAdate());
            sql.addIntValue(mdl.getFdrEuid());
            sql.addDateValue(mdl.getFdrEdate());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * キャビネットのRootディレクトリ情報を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param name ディレクトリ名
     * @param now 更新日付
     * @return CvtFileDirectoryModel
     */
    private CvtFileDirectoryModel __getRootDirectory(
            int cabSid, int dirSid, String name, UDate now) {

        CvtFileDirectoryModel bean = new CvtFileDirectoryModel();
        bean.setFdrSid(dirSid);
        bean.setFdrVersion(FOLDER_VERSION__);
        bean.setFcbSid(cabSid);
        bean.setFdrParentSid(DIRECTORY_ROOT__);
        bean.setFdrKbn(DIRECTORY_FOLDER__);
        bean.setFdrVerKbn(VERSION_KBN_OFF__);
        bean.setFdrLevel(DIRECTORY_LEVEL_0__);
        bean.setFdrName(name);
        bean.setFdrBiko("");
        bean.setFdrJtkbn(0);
        bean.setFdrAuid(ADMIN_USER_SID__);
        bean.setFdrAdate(now);
        bean.setFdrEuid(ADMIN_USER_SID__);
        bean.setFdrEdate(now);
        return bean;
    }
    /**
     * ディレクトリ情報を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param parDirSid 親ディレクトリSID
     * @param mdl ディレクトリ情報
     * @param level 階層レベル
     * @param now 更新日付
     * @return CvtFileDirectoryModel
     */
    private CvtFileDirectoryModel __getFileDirectory(
            int cabSid,
            int dirSid,
            int parDirSid,
            PrjSimpleDirectoryModel mdl,
            Integer level,
            UDate now) {

        CvtFileDirectoryModel bean = new CvtFileDirectoryModel();
        bean.setFdrSid(dirSid);
        bean.setFcbSid(cabSid);
        bean.setFdrParentSid(parDirSid);

        bean.setFdrVerKbn(VERSION_KBN_OFF__);
        bean.setFdrLevel(level.intValue());
        bean.setFdrName(mdl.getPdrName());
        bean.setFdrBiko(mdl.getPdrBiko());
        bean.setFdrJtkbn(0);
        bean.setFdrAuid(ADMIN_USER_SID__);
        bean.setFdrAdate(now);
        bean.setFdrEuid(ADMIN_USER_SID__);
        bean.setFdrEdate(now);
        if (mdl.getPdrKbn() == 0) {
            bean.setFdrVersion(FOLDER_VERSION__);
            bean.setFdrKbn(DIRECTORY_FOLDER__);
        } else {
            bean.setFdrVersion(1);
            bean.setFdrKbn(DIRECTORY_FILE__);
        }

        return bean;
    }
    /**
     * プロジェクトメンバー情報からキャビネットメンバーを作成
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @param now 更新日付
     * @param projMdl プロジェクト情報
     * @param pmemList メンバー情報
     * @throws SQLException SQL実行時例外
     */
    private void __createMember(
            long cabSid,
            UDate now,
            PrjPrjdataModel projMdl,
            ArrayList<PrjMembersModel> pmemList)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_ACCESS_CONF(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (pmemList != null) {
                for (PrjMembersModel mdl : pmemList) {
                    sql.addLongValue(cabSid);
                    sql.addIntValue(mdl.getUsrSid());
                    sql.addIntValue(0);
                    sql.addIntValue(1);
                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                    sql.clearValue();
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * 親ディレクトリを指定し、ディレクトリ一覧を取得し、ファイル管理用ディレクトリとして登録する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param pdirSid 親ディレクトリSID(プロジェクト管理)
     * @param saiban 採番コントロール
     * @param cabSid キャビネットSID
     * @param pDirSid 親ディレクトリSID(ファイル管理)
     * @param level 階層レベル
     * @param now 更新日時
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     */
    private void __createDirectory(
            int prjSid,
            int pdirSid,
            MlCountMtController saiban,
            int cabSid,
            int pDirSid,
            Integer level,
            UDate now)
    throws SQLException, IOToolsException, IOException {

        ArrayList<PrjSimpleDirectoryModel> pdirList = __getProjDirectoryList(prjSid, pdirSid);
        for (PrjSimpleDirectoryModel mdl : pdirList) {
            //ファイル管理用ディレクトリとして登録
            int dirSid = __createFileKanriDirectory(mdl, saiban, cabSid, pDirSid, level, now);
            //再帰的に下層へ処理を繰り返す
            __createDirectory(
                    prjSid,
                    mdl.getPdrSid(),
                    saiban,
                    cabSid,
                    dirSid,
                    new Integer(level.intValue() + 1),
                    now);
        }
    }

    /**
     * ファイル管理用ディレクトリ・ファイル情報を作成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl プロジェクト管理のディレクトリ情報
     * @param saiban 採番コントロール
     * @param cabSid キャビネットSID
     * @param parDirSid 親ディレクトリSID
     * @param now 更新日時
     * @param level 階層レベル
     * @return int 登録したディレクトリSID
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     */
    private int __createFileKanriDirectory(
            PrjSimpleDirectoryModel mdl,
            MlCountMtController saiban,
            int cabSid,
            int parDirSid,
            Integer level,
            UDate now
            ) throws SQLException, IOException, IOToolsException {

        int dirSid = (int) saiban.getSaibanNumber(
                PLUGIN_ID_FILE__, SBNSID_SUB_DIRECTORY__, ADMIN_USER_SID__);
        //ディレクトリ情報
        __createDirectory(__getFileDirectory(cabSid, dirSid, parDirSid, mdl, level, now));

        //ファイルの場合はファイル情報・バイナリ情報・履歴を登録
        if (mdl.getPdrKbn() == DIRECTORY_FILE__) {
            //元バイナリー情報を取得
            CmnBinfModel binMdl = __getBinInfo(mdl.getBinSid());

            //新たなバイナリーSIDを採番
            int binSid = (int) saiban.getSaibanNumber(
                    GSConst.SBNSID_BIN, GSConst.SBNSID_SUB_BIN, ADMIN_USER_SID__);
            //ファイル情報登録
            CvtFileFileBinModel filMdl = new CvtFileFileBinModel();
            filMdl.setFdrSid(dirSid);
            filMdl.setFdrVersion(1);
            filMdl.setFflExt(binMdl.getBinFileExtension());
            filMdl.setFflFileSize(binMdl.getBinFileSize());
            filMdl.setFflLockKbn(0);
            filMdl.setFflLockUser(ADMIN_USER_SID__);
            __createFileBin(filMdl, binSid);

            //履歴情報登録
            CvtFileFileRekiModel rekiMdl = new CvtFileFileRekiModel();
            rekiMdl.setFdrVersion(1);
            rekiMdl.setFfrKbn(REKI_KBN_NEW__);
            rekiMdl.setFfrEuid(0);
            rekiMdl.setFfrEdate(now);
            rekiMdl.setFfrParentSid(parDirSid);
            rekiMdl.setFdrSid(dirSid);
            rekiMdl.setFfrFname(binMdl.getBinFileName());
            __createFileReki(rekiMdl);

            //物理ファイルをコピー
            String savePath = __doCopyFile(binSid, binMdl, now);
            //バイナリー登録
            binMdl.setBinFilePath(savePath);
            __createCmnBin(binMdl, binSid);

        }
        return dirSid;
    }

    /**
     * 物理ファイルをファイル管理用にコピーを行い保存する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @param binMdl 元バイナリー情報
     * @param now 更新日
     * @return String DB用の保存先パス
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     */
    private String __doCopyFile(int binSid, CmnBinfModel binMdl, UDate now)
    throws IOException, IOToolsException {

        Long bsid = new Long(binSid);

        String savePath = "";
        CommonBiz biz = new CommonBiz();
        //元ファイルの保存先フルパス
        String fromSavePath = biz.getSaveFullPath(appRootPath__, binMdl.getBinFilePath());

        //添付ファイル保存用パス(フルパス)
        savePath = biz.getSavePathForDb(now, binSid);
        String toSavePath = biz.getSaveFullPathForFileKanri(now, bsid, appRootPath__);

        //ファイルの有効性チェック(ない場合に作成)
        IOTools.isFileCheck(
                biz.getSaveDirPathForFileKanri(now, appRootPath__), String.valueOf(bsid), true);

        //添付ファイルを保存
        IOTools.copyBinFile(fromSavePath, toSavePath);

        return savePath;
    }
    /**
     * ファイル情報を作成
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ファイル情報
     * @param binSid バイナリSID
     * @throws SQLException SQL実行時例外
     */
    private void __createFileBin(
            CvtFileFileBinModel bean, int binSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_BIN(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addIntValue(binSid);
            sql.addStrValue(bean.getFflExt());
            sql.addLongValue(bean.getFflFileSize());
            sql.addIntValue(bean.getFflLockKbn());
            sql.addIntValue(bean.getFflLockUser());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * <p>Insert FILE_FILE_REKI Data Bindding JavaBean
     * @param bean FILE_FILE_REKI Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    private void __createFileReki(CvtFileFileRekiModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_REKI(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FFR_FNAME,");
            sql.addSql("   FFR_KBN,");
            sql.addSql("   FFR_EUID,");
            sql.addSql("   FFR_EDATE,");
            sql.addSql("   FFR_PARENT_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addStrValue(bean.getFfrFname());
            sql.addIntValue(bean.getFfrKbn());
            sql.addIntValue(bean.getFfrEuid());
            sql.addDateValue(bean.getFfrEdate());
            sql.addIntValue(bean.getFfrParentSid());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * ファイル情報を作成
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ファイル情報
     * @param binSid バイナリSID
     * @throws SQLException SQL実行時例外
     */
    private void __createCmnBin(
            CmnBinfModel bean, int binSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BINF(");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(binSid);
            sql.addStrValue(bean.getBinFileName());
            sql.addStrValue(bean.getBinFilePath());
            sql.addStrValue(bean.getBinFileExtension());
            sql.addLongValue(bean.getBinFileSize());
            sql.addIntValue(bean.getBinAduser());
            sql.addDateValue(bean.getBinAddate());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());
            sql.addIntValue(bean.getBinFilekbn());
            sql.addIntValue(bean.getBinJkbn());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * <br>[機  能] ディレクトリ情報一覧
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @param pdrSid ディレクトリSID
     * @return ret ディレクトリ情報一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<PrjSimpleDirectoryModel> __getProjDirectoryList(int prjSid,
                                              int pdrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjSimpleDirectoryModel> ret = new ArrayList<PrjSimpleDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   DIR.PDR_SID as PDR_SID,");
            sql.addSql("   DIR.PDR_KBN as PDR_KBN,");
            sql.addSql("   DIR.PDR_NAME as PDR_NAME,");
            sql.addSql("   DIR.PDR_LEVEL as PDR_LEVEL,");
            sql.addSql("   DIR.PDR_BIKO as PDR_BIKO,");
            sql.addSql("   COALESCE(CMN_BINF.BIN_SID, -1) as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   PRJ_BIN.PFL_EXT as PFL_EXT,");
            sql.addSql("   COALESCE(PRJ_BIN.PFL_FILE_SIZE, -1) as PFL_FILE_SIZE,");
            sql.addSql("   DIR.PDR_EDATE as PDR_EDATE,");
            sql.addSql("   USRM.USI_SEI as USI_SEI,");
            sql.addSql("   USRM.USI_MEI as USI_MEI");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF USRM,");
            sql.addSql("   (");
            sql.addSql("     PRJ_DIRECTORY DIR left join PRJ_FILE_BIN PRJ_BIN");
            sql.addSql("     on DIR.BIN_SID = PRJ_BIN.BIN_SID");
            sql.addSql("   ) left join CMN_BINF CMN_BINF");
            sql.addSql("   on PRJ_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" where");
            sql.addSql("   DIR.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   DIR.PDR_PARENT_SID = ?");
            sql.addSql(" and");
            sql.addSql("   DIR.PDR_EUID = USRM.USR_SID");
            sql.addSql(" order by");
            sql.addSql("   DIR.PDR_KBN || '_' || ");
            sql.addSql("   DIR.PDR_NAME asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(pdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrjSimpleDirectoryModel mdl = new PrjSimpleDirectoryModel();
                mdl.setPdrSid(rs.getInt("PDR_SID"));
                mdl.setPdrKbn(rs.getInt("PDR_KBN"));
                mdl.setPdrName(rs.getString("PDR_NAME"));
                mdl.setPdrLevel(rs.getInt("PDR_LEVEL"));
                mdl.setPdrBiko(rs.getString("PDR_BIKO"));
                mdl.setBinSid(rs.getInt("BIN_SID"));
                mdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                mdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                mdl.setPflExt(rs.getString("PFL_EXT"));
                mdl.setPflFileSize(rs.getLong("PFL_FILE_SIZE"));

                //ファイルサイズ(表示用)
                String fileSizeStr = "";
                if (mdl.getPflFileSize() > 0) {

                    log__.debug("*** ファイルサイズ(byte) = " + mdl.getPflFileSize());

                    if (mdl.getPflFileSize() < 1024) {
                        //1024バイト以下は1KBにする
                        fileSizeStr = "1";
                    } else {
                        BigDecimal bdFileSize = new BigDecimal(mdl.getPflFileSize());
                        fileSizeStr = bdFileSize.divide(new BigDecimal(1024), 1).toString();
                    }

                    log__.debug("*** ファイルサイズ(KB) = " + fileSizeStr);
                    fileSizeStr = StringUtil.toCommaFormat(fileSizeStr);

                }
                mdl.setPflFileSizeStr(fileSizeStr);

                mdl.setPdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PDR_EDATE")));

                String edateStr = "";
                if (mdl.getPdrEdate() != null
                        && mdl.getPdrKbn() == DIRECTORY_FILE__) {
                    edateStr = UDateUtil.getSlashYYMD(mdl.getPdrEdate());
                    edateStr = edateStr + " " + UDateUtil.getSeparateHM(mdl.getPdrEdate());
                }
                mdl.setPdrEdateStr(edateStr);

                String sei = "";
                String mei = "";
                if (mdl.getPdrKbn() == DIRECTORY_FILE__) {
                    sei = rs.getString("USI_SEI");
                    mei = rs.getString("USI_MEI");
                }
                mdl.setUsiSei(sei);
                mdl.setUsiMei(mei);

                ret.add(mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Select PRJ_PRJDATA All Data
     * @return List in PRJ_PRJDATAModel
     * @throws SQLException SQL実行例外
     */
    private ArrayList<PrjPrjdataModel> __getProjectList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjPrjdataModel> ret = new ArrayList<PrjPrjdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID,");
            sql.addSql("   PRJ_MY_KBN,");
            sql.addSql("   PRJ_ID,");
            sql.addSql("   PRJ_NAME,");
            sql.addSql("   PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_YOSAN,");
            sql.addSql("   PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_DATE_START,");
            sql.addSql("   PRJ_DATE_END,");
            sql.addSql("   PRJ_STATUS_SID,");
            sql.addSql("   PRJ_TARGET,");
            sql.addSql("   PRJ_CONTENT,");
            sql.addSql("   PRJ_EDIT,");
            sql.addSql("   PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_AUID,");
            sql.addSql("   PRJ_ADATE,");
            sql.addSql("   PRJ_EUID,");
            sql.addSql("   PRJ_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA,");
            sql.addSql("   PRJ_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_PRJDATA.PRJ_MY_KBN = 0 ");
            sql.addSql(" and ");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID = PRJ_DIRECTORY.PRJ_SID ");
            sql.addSql(" group by PRJ_PRJDATA.PRJ_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjdataFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     * <p>Create PRJ_PRJDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    private PrjPrjdataModel __getPrjPrjdataFromRs(ResultSet rs) throws SQLException {
        PrjPrjdataModel bean = new PrjPrjdataModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPrjMyKbn(rs.getInt("PRJ_MY_KBN"));
        bean.setPrjId(rs.getString("PRJ_ID"));
        bean.setPrjName(rs.getString("PRJ_NAME"));
        bean.setPrjNameShort(rs.getString("PRJ_NAME_SHORT"));
        bean.setPrjYosan(rs.getInt("PRJ_YOSAN"));
        bean.setPrjKoukaiKbn(rs.getInt("PRJ_KOUKAI_KBN"));
        bean.setPrjDateStart(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_START")));
        bean.setPrjDateEnd(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_END")));
        bean.setPrjStatusSid(rs.getInt("PRJ_STATUS_SID"));
        bean.setPrjTarget(rs.getString("PRJ_TARGET"));
        bean.setPrjContent(rs.getString("PRJ_CONTENT"));
        bean.setPrjEdit(rs.getInt("PRJ_EDIT"));
        bean.setPrjMailKbn(rs.getInt("PRJ_MAIL_KBN"));
        bean.setPrjAuid(rs.getInt("PRJ_AUID"));
        bean.setPrjAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_ADATE")));
        bean.setPrjEuid(rs.getInt("PRJ_EUID"));
        bean.setPrjEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] プロジェクトメンバー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return PRJ_MEMBERSModel
     * @throws SQLException SQL実行例外
     */
    private ArrayList<PrjMembersModel> __getPrjMembers(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjMembersModel> ret = new ArrayList<PrjMembersModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjMembersFromRs(rs));

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     * <p>Create PRJ_MEMBERS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjMembersModel
     * @throws SQLException SQL実行例外
     */
    private PrjMembersModel __getPrjMembersFromRs(ResultSet rs) throws SQLException {
        PrjMembersModel bean = new PrjMembersModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setPrmEmployeeKbn(rs.getInt("PRM_EMPLOYEE_KBN"));
        bean.setPrmAdminKbn(rs.getInt("PRM_ADMIN_KBN"));
        bean.setPrmAuid(rs.getInt("PRM_AUID"));
        bean.setPrmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRM_ADATE")));
        bean.setPrmEuid(rs.getInt("PRM_EUID"));
        bean.setPrmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRM_EDATE")));
        bean.setPrmMemKey(rs.getString("PRM_MEM_KEY"));
        return bean;
    }
    /**
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリーSID
     * @return CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    private CmnBinfModel __getBinInfo(int binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBinfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addIntValue(binSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnBinfFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     * <p>Create CMN_BINF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    private CmnBinfModel __getCmnBinfFromRs(ResultSet rs) throws SQLException {
        CmnBinfModel bean = new CmnBinfModel();
        bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
        bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        bean.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
        bean.setBinFileSize(rs.getLong("BIN_FILE_SIZE"));
        bean.setBinAduser(rs.getInt("BIN_ADUSER"));
        bean.setBinAddate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_ADDATE")));
        bean.setBinUpuser(rs.getInt("BIN_UPUSER"));
        bean.setBinUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_UPDATE")));
        bean.setBinJkbn(rs.getInt("BIN_JKBN"));
        bean.setBinFilekbn(rs.getInt("BIN_FILEKBN"));
        return bean;
    }
}