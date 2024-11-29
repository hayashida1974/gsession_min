package jp.groupsession.v2.convert.convert540.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.Blowfish;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.lic.GSConstLicese;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v5.4.0へコンバートする際に使用する
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
        alterTable();

        __deleteWmlLabelRelation();

        __updateBbsWrtDate();

        __deleteNotUsedPortletImage();

        __deleteSchNoData();

        __updateSchExdata();

        __updateInfoSendDate();

        __updateCabinetSort(GSConstFile.CABINET_KBN_PRIVATE);

        __updateCabinetSort(GSConstFile.CABINET_KBN_PUBLIC);

        __deletePrivateCallConf();

        __deletePrivateCallData();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] テーブルの更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void alterTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL();

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
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
        SqlBuffer sql = new SqlBuffer();
        /*--- No.1323 テーブル定義の変更 start ---*/
        //WEBメール 自動宛先設定(WAC_AUTOTO)の変更
        __addSql(sqlList,
                " alter table WML_ACCOUNT alter column WAC_AUTOTO type varchar(256);");
        //WEBメール 自動CC設定(WAC_AUTOCC)の変更
        __addSql(sqlList,
                " alter table WML_ACCOUNT alter column WAC_AUTOCC type varchar(256);");
        //WEBメール 自動BCC設定(WAC_AUTOBCC)の変更
        __addSql(sqlList,
                " alter table WML_ACCOUNT alter column WAC_AUTOBCC type varchar(256);");
        /*--- No.1323 テーブル定義の変更 end ---*/

        /*--- No.1283 テーブル定義の変更 start ---*/
        //アンケート 回答数値(ENQ_ANS_SUB)の変更
        __addSql(sqlList,
                " alter table ENQ_ANS_SUB alter column EAS_ANS_NUM type decimal(15, 5);");
        //アンケート 初期値_数値(EQS_DEF_NUM)の変更
        __addSql(sqlList,
                " alter table ENQ_QUE_SUB alter column EQS_DEF_NUM type decimal(15, 5);");
        //アンケート 開始_数値(EQS_RNG_STR_NUM)の変更
        __addSql(sqlList,
                " alter table ENQ_QUE_SUB alter column EQS_RNG_STR_NUM type decimal(15, 5);");
        //アンケート 終了_数値(EQS_RNG_END_NUM)の変更
        __addSql(sqlList,
                " alter table ENQ_QUE_SUB alter column EQS_RNG_END_NUM type decimal(15, 5);");
        /*--- No.1283 テーブル定義の変更 end ---*/

        /*--- No.16828 テーブル定義の変更 start ---*/
        //スケジュール拡張情報 通知対象 アプリ(SCE_TARGET_APP)の変更
        __addSql(sqlList,
                " alter table SCH_EXDATA add column SCE_TARGET_APP integer not null default 0;");
        //スケジュール拡張情報 通知対象 ブラウザ(SCE_TARGET_PC)の変更
        __addSql(sqlList,
                " alter table SCH_EXDATA add column SCE_TARGET_PC integer not null default 0;");
        //スケジュール拡張情報 通知対象 グループ(SCE_TARGET_GRP)の変更
        __addSql(sqlList,
                " alter table SCH_EXDATA add column SCE_TARGET_GRP integer not null default 0;");
        //スケジュール拡張情報 通知時間(SCE_REMINDER)の変更
        __addSql(sqlList,
                " alter table SCH_EXDATA add column SCE_REMINDER integer not null default 0;");
        /*--- No.16828 テーブル定義の変更 end ---*/

        /*--- No.17482 テーブル定義の変更 start ---*/
        //ショートメール 下書き_編集元メールSID(SML_WMEIS)の追加
        __addSql(sqlList,
                " alter table SML_WMEIS add SMW_ORIGIN integer default 0;");
        //ショートメール 下書き_編集区分(SMW_EDIT_KBN)の追加
        __addSql(sqlList,
                " alter table SML_WMEIS add SMW_EDIT_KBN integer default 0;");
        /*--- No.17482 テーブル定義の変更 end ---*/

        /*--- No.19556 アドレス帳 会社情報(ADR_COMPANY)に住所情報を追加 ---*/
        __addSql(sqlList, "alter table ADR_COMPANY add ACO_POSTNO1 varchar(3)");
        __addSql(sqlList, "alter table ADR_COMPANY add ACO_POSTNO2 varchar(4)");
        __addSql(sqlList, "alter table ADR_COMPANY add TDF_SID integer");
        __addSql(sqlList, "alter table ADR_COMPANY add ACO_ADDR1 varchar(100)");
        __addSql(sqlList, "alter table ADR_COMPANY add ACO_ADDR2 varchar(100)");
        /*--- No.17482 テーブル定義の変更 end ---*/

        /*--- No.17443 テーブル定義の変更 start ---*/
        //ファイル管理 キャビネット情報(FILE_CABINET)の変更
        __addSql(sqlList,
                " alter table FILE_CABINET add FCB_ERRL integer default 0;");
        __addSql(sqlList,
                " alter table FILE_CABINET add FCB_SORT_FOLDER integer default 0;");
        __addSql(sqlList,
                " alter table FILE_CABINET add FCB_SORT_FOLDER1 integer;");
        __addSql(sqlList,
                " alter table FILE_CABINET add FCB_SORT_FOLDER2 integer;");
        __addSql(sqlList,
                " alter table FILE_CABINET add FCB_JKBN integer default 0;");
        //ファイル管理 ディレクトリ情報(FILE_DIRECTORY)の変更
        __addSql(sqlList,
                " alter table FILE_DIRECTORY add FDR_TRADE_DATE timestamp;");
        __addSql(sqlList,
                " alter table FILE_DIRECTORY add FDR_TRADE_TARGET varchar(50);");
        __addSql(sqlList,
                " alter table FILE_DIRECTORY add FDR_TRADE_MONEYKBN integer;");
        __addSql(sqlList,
                " alter table FILE_DIRECTORY add FDR_TRADE_MONEY decimal(16, 4);");
        __addSql(sqlList,
                " alter table FILE_DIRECTORY add EMT_SID integer;");
        //ファイル管理 更新履歴情報(FILE_FILE_REKI)の変更
        __addSql(sqlList,
                " alter table FILE_FILE_REKI add FDR_TRADE_DATE timestamp;");
        __addSql(sqlList,
                " alter table FILE_FILE_REKI add FDR_TRADE_TARGET varchar(50);");
        __addSql(sqlList,
                " alter table FILE_FILE_REKI add FDR_TRADE_MONEYKBN integer;");
        __addSql(sqlList,
                " alter table FILE_FILE_REKI add FDR_TRADE_MONEY decimal(16, 4);");
        __addSql(sqlList,
                " alter table FILE_FILE_REKI add EMT_SID integer;");

        //ファイル管理 電帳法ファイル登録情報(FILE_ERRL_ADDDATA)の追加
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_ERRL_ADDDATA(");
        sql.addSql("    FEA_SID          integer      not null,");
        sql.addSql("    BIN_SID          bigint       not null,");
        sql.addSql("    FFL_EXT          varchar(50)          ,");
        sql.addSql("    FFL_FILE_SIZE    integer      not null,");
        sql.addSql("    FCB_SID          integer      not null,");
        sql.addSql("    FDR_PARENT_SID   integer      not null,");
        sql.addSql("    FDR_NAME         varchar(255) not null,");
        sql.addSql("    FDR_BIKO         varchar(1000)        ,");
        sql.addSql("    FFR_UP_CMT       varchar(1000)        ,");
        sql.addSql("    FDR_AUID         integer      not null,");
        sql.addSql("    FDR_ADATE        timestamp    not null,");
        sql.addSql("    primary key (FEA_SID)");
        sql.addSql(" );");
        sqlList.add(sql);
        sql = new SqlBuffer();

        //ファイル管理 外貨マスタ情報の(FILE_MONEY_MASTER)追加
        sql.addSql(" create table FILE_MONEY_MASTER(");
        sql.addSql("    FMM_SID          integer      not null,");
        sql.addSql("    FMM_NAME         varchar(15)  not null,");
        sql.addSql("    FMM_SORT         integer      not null,");
        sql.addSql("    primary key (FMM_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into FILE_MONEY_MASTER (");
        sql.addSql("   FMM_SID,");
        sql.addSql("   FMM_NAME,");
        sql.addSql("   FMM_SORT");
        sql.addSql(" ) values (");
        sql.addSql("  1,");
        sql.addSql("  '円',");
        sql.addSql("  1");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into FILE_MONEY_MASTER (");
        sql.addSql("   FMM_SID,");
        sql.addSql("   FMM_NAME,");
        sql.addSql("   FMM_SORT");
        sql.addSql(" ) values (");
        sql.addSql("  2,");
        sql.addSql("  'USD',");
        sql.addSql("  2");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into FILE_MONEY_MASTER (");
        sql.addSql("   FMM_SID,");
        sql.addSql("   FMM_NAME,");
        sql.addSql("   FMM_SORT");
        sql.addSql(" ) values (");
        sql.addSql("  3,");
        sql.addSql("  'EUR',");
        sql.addSql("  3");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into FILE_MONEY_MASTER (");
        sql.addSql("   FMM_SID,");
        sql.addSql("   FMM_NAME,");
        sql.addSql("   FMM_SORT");
        sql.addSql(" ) values (");
        sql.addSql("  4,");
        sql.addSql("  'GBP',");
        sql.addSql("  4");
        sql.addSql(" );");
        sqlList.add(sql);

        //共通 採番値の追加
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_SAIBAN (");
        sql.addSql("   SBN_SID,");
        sql.addSql("   SBN_SID_SUB,");
        sql.addSql("   SBN_NUMBER,");
        sql.addSql("   SBN_STRING,");
        sql.addSql("   SBN_AID,");
        sql.addSql("   SBN_ADATE,");
        sql.addSql("   SBN_EID,");
        sql.addSql("   SBN_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("   'file',");
        sql.addSql("   'money',");
        sql.addSql("   4,");
        sql.addSql("   'money',");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp,");
        sql.addSql("   0,");
        sql.addSql("   current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);
        /*--- No.17443 テーブル定義の変更 end ---*/

        //HTMLエディタ コンテンツ挿入関連テーブルの追加
        sql = new SqlBuffer();
        sql.addSql(" create table ENQ_DESC_BIN(");
        sql.addSql("    EMN_SID            bigint         not null,");
        sql.addSql("    EDB_SID            integer        not null,");
        sql.addSql("    BIN_SID            bigint         not null,");
        sql.addSql("    primary key (EMN_SID, EDB_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //GSモバイル スマートフォン版 テーマ関連のテーブルを削除
        __addSql(sqlList, "drop table MBL_THEME;");
        __addSql(sqlList, "drop table MBL_USR_THEME;");

        /*--- No.19025 テーブル定義の変更 start ---*/
        //利用状況次回送信日(CMN_INFO_SEND_DATE)の追加
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_INFO_SEND_DATE(");
        sql.addSql("     CSD_DATE       timestamp        not null");
        sql.addSql(" );");
        sqlList.add(sql);
        /*--- No.19025 テーブル定義の変更 end ---*/

        /*--- No.20498 GSファイアウォール ---*/
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_FIREWALL_CONF( ");
        sql.addSql("     CFC_ALLOW_IP       varchar(1000) not null default '', ");
        sql.addSql("     CFC_ALLOW_MBL      integer       not null default 0, ");
        sql.addSql("     CFC_ALLOW_ANP      integer       not null default 0 ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_FIREWALL_CONF ( ");
        sql.addSql("   CFC_ALLOW_IP, ");
        sql.addSql("   CFC_ALLOW_MBL, ");
        sql.addSql("   CFC_ALLOW_ANP ");
        sql.addSql(" ) values ( ");
        sql.addSql("   '', ");
        sql.addSql("   0, ");
        sql.addSql("   0 ");
        sql.addSql(" ); ");
        sqlList.add(sql);
        /*--- No.20498 GSファイアウォール end ---*/

        return sqlList;
    }

    /**
     * <br>[機  能] 指定されたSQL文をSqlBufferに設定し、実行SQLリストへ追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList 実行SQLリスト
     * @param sql SQL文
     */
    private void __addSql(ArrayList<SqlBuffer> sqlList, String sql) {
        SqlBuffer sqlBuffer = new SqlBuffer();
        sqlBuffer.addSql(sql);
        sqlList.add(sqlBuffer);
    }

    /**
     * <br>[機  能] 掲示板の最新書き込み日時を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __updateBbsWrtDate() throws SQLException {

        List<Integer> bfiSidList = new ArrayList<Integer>();
        bfiSidList = __getCnvSbjList();

        for (int bfiSid : bfiSidList) {
            __updateBfsWrtDate(bfiSid);
        }
    }

    /**
     * <br>[機  能] v540へのバージョンアップコンバート時の修正対象を取得する
     * <br>[解  説] フォーラムSID、スレッド数SID、最新書き込み日時用パラメータの取得
     * <br>[備  考]
     * @param
     * @return フォーラムSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getCnvSbjList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate now = new UDate();
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_THRE_INF.BFI_SID, ");
            sql.addSql("   BBS_THRE_INF.BTI_SID, ");
            sql.addSql("   case ");
            sql.addSql("     when ");
            sql.addSql("       MAX(BBS_THRE_INF.BTI_LIMIT_FR_DATE) != null ");
            sql.addSql("       and MAX(BBS_THRE_INF.BTI_LIMIT_FR_DATE) > ? ");
            sql.addSql("         then ");
            sql.addSql("           MAX(BBS_THRE_INF.BTI_LIMIT_FR_DATE) ");
            sql.addSql("     else ");
            sql.addSql("       MAX(BBS_WRITE_INF.BWI_EDATE) ");
            sql.addSql("   end TARGET ");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_INF, ");
            sql.addSql("   BBS_WRITE_INF ");
            sql.addSql(" where ");
            sql.addSql("   BBS_THRE_INF.BTI_SID = BBS_WRITE_INF.BTI_SID ");
            sql.addSql(" group by ");
            sql.addSql("   BBS_THRE_INF.BTI_SID ");
            sql.addSql(" order by ");
            sql.addSql("   BBS_THRE_INF.BFI_SID, ");
            sql.addSql("   BBS_THRE_INF.BTI_SID ");

            sql.addDateValue(now);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                __updateWrtDate(rs.getInt("BTI_SID"),
                        UDate.getInstanceTimestamp(rs.getTimestamp("TARGET")));
                if (!ret.contains(rs.getInt("BFI_SID"))) {
                    ret.add(rs.getInt("BFI_SID"));
                }
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
     * <br>[機  能] 既存のスレッド最新書き込み日時を修正する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @param date 最新書き込み日時更新用日付
     * @return update count
     * @throws SQLException SQL実行例外
     */
    private int __updateWrtDate(int btiSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" set ");
            sql.addSql("   BTS_WRT_DATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(date);
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 最新書き込み日時を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return update count
     * @throws SQLException SQL実行例外
     */
    private int __updateBfsWrtDate(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_SUM");
            sql.addSql(" set ");
            sql.addSql("   BFS_WRT_DATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(__getForMaxEdate(bfiSid));
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] フォーラム内の最新更新日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return BBS_FOR_SUMModel
     * @throws SQLException SQL実行例外
     */
    private UDate __getForMaxEdate(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate maxEdate = null;
        UDate now = new UDate();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   Max(BWI_EDATE) as BWI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   BWI_ADATE < ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);
            sql.addDateValue(now);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxEdate = UDate.getInstanceTimestamp(rs.getTimestamp("BWI_EDATE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxEdate;
    }

    /**
     * <br>[機  能] 使用されていないポートレット 画像情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteNotUsedPortletImage() throws SQLException {

        //内容 種別 = 文章を入力 のポートレット一覧を取得する
        List<PortletDataModel> pltDataList = __getInputPorgletList();
        for (PortletDataModel pltData : pltDataList) {

            //内容に指定されている画像のポートレット画像SIDを取得する
            int pltSid = pltData.getPltSid();
            List<Long> pliSidList = new ArrayList<Long>();
            String linkStr =
                    "src=\"../pltimage/ptl990.do"
                    + "?ptlPortletSid=" + pltSid
                    + "&amp;imgId=";
            String content = pltData.getPltContent();
            int linkIdx = -1;
            while ((linkIdx = content.indexOf(linkStr)) >= 0) {
                String imgId = content.substring(linkIdx + linkStr.length());
                if (imgId.indexOf("\"") > 0) {
                    imgId = imgId.substring(0, imgId.indexOf("\""));
                    if (ValidateUtil.isNumber(imgId)) {
                        pliSidList.add(Long.parseLong(imgId));
                    }
                }

                content = content.replaceFirst(Pattern.quote(linkStr), "");
            }

            //内容に指定されていないポートレット画像SIDを削除する
            __deletePortletImageBinData(pltSid, pliSidList);
            __deletePortletImage(pltSid, pliSidList);
        }

    }

    /**
     * <br>[機  能] 内容 種別 = 文章を入力、 かつポートレット画像が登録されているポートレット一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ポートレットSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private List<PortletDataModel> __getInputPorgletList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PortletDataModel> ret = new ArrayList<PortletDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLT_CONTENT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET");
            sql.addSql(" where ");
            sql.addSql("   PLT_CONTENT_TYPE = 0");
            sql.addSql(" and ");
            sql.addSql("   exists (");
            sql.addSql("     select 1 from PTL_PORTLET_IMAGE");
            sql.addSql("     where PTL_PORTLET_IMAGE.PLT_SID = PTL_PORTLET.PLT_SID");
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PortletDataModel model = new PortletDataModel();
                model.setPltSid(rs.getInt("PLT_SID"));
                model.setPltContent(rs.getString("PLT_CONTENT"));
                ret.add(model);
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
     * <br>[機  能] 指定したポートレット 画像情報に関連するバイナリ情報を論理削除する
     * <br>[解  説] ポートレット 内容に挿入されていない画像のみを対象とする
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param pliSidList ポートレット画像SID
     * @return update count
     * @throws SQLException SQL実行例外
     */
    private int __deletePortletImageBinData(int pltSid, List<Long> pliSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_JKBN = ?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(0);
            sql.addDateValue(now);

            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from PTL_PORTLET_IMAGE ");
            sql.addSql("     where PLT_SID = ?");
            sql.addIntValue(pltSid);

            //内容に挿入されている画像は削除対象から除外する
            if (!pliSidList.isEmpty()) {
                sql.addSql("     and");
                sql.addSql("       PLI_SID not in (");
                for (int idx = 0; idx < pliSidList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("         ,?");
                    } else {
                        sql.addSql("         ?");
                    }
                    sql.addLongValue(pliSidList.get(idx));
                }
                sql.addSql("       )");
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したポートレット 画像情報を物理削除する
     * <br>[解  説] ポートレット 内容に挿入されていない画像のみを対象とする
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param pliSidList ポートレット画像SID
     * @return update count
     * @throws SQLException SQL実行例外
     */
    private int __deletePortletImage(int pltSid, List<Long> pliSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from PTL_PORTLET_IMAGE ");
            sql.addSql(" where PLT_SID = ?");
            sql.addIntValue(pltSid);

            //内容に挿入されている画像は削除対象から除外する
            if (!pliSidList.isEmpty()) {
                sql.addSql(" and");
                sql.addSql("   PLI_SID not in (");
                for (int idx = 0; idx < pliSidList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("     ,?");
                    } else {
                        sql.addSql("     ?");
                    }
                    sql.addLongValue(pliSidList.get(idx));
                }
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] メールに紐づくラベル情報の削除を行う
     * <br>[解  説] アカウント毎に不要なラベルの削除を行う
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteWmlLabelRelation() throws SQLException {
        List<Integer> accountList = new ArrayList<Integer>();
        accountList = __getWmlAccount();

        if (accountList != null && accountList.size() > 0) {
            for (int wacSid : accountList) {
                __deleteWmlLabelNotRelation(wacSid);
            }
        }
    }
    /**
     * <br>[機  能] WEBメールに紐づいているアカウントに存在しないラベルを削除する
     * <br>[解  説] アカウント毎に不要なラベルの削除を行う
     * <br>[備  考]
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getWmlAccount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("WAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] WEBメール情報に紐づいているアカウントに存在しないラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WebメールアカウントSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    private int __deleteWmlLabelNotRelation(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID in (");
            sql.addSql("     select");
            sql.addSql("       WLB_SID from WML_LABEL");
            sql.addSql("     where");
            sql.addSql("       WAC_SID <> ?");
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wacSid);
            sql.addLongValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <br>[機  能] スケジュールと紐づいていない拡張スケジュール情報を削除する
     * <br>[解  説]
     * <br>[備  考] 拡張スケジュールに紐づく公開情報，添付ファイルも削除される
     * @throws SQLException SQL実行例外
     */
    private void __deleteSchNoData() throws SQLException {

        List<Integer> delSceSidList = __getDeleteSchExdataSid();
        if (delSceSidList.isEmpty()) {
            return;
        }

        List<Integer> delBinSidList = __getDeleteBinSid(delSceSidList);
        __deleteSchExdata(delSceSidList);
        __deleteSchExdataPub(delSceSidList);
        __deleteSchExdataBin(delSceSidList);

        if (!delBinSidList.isEmpty()) {
            __deleteCmnBinf(delBinSidList);
        }
    }

    /**
     * <br>[機  能] スケジュールと紐づいていない拡張スケジュールを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return 削除対象の拡張スケジュールSID
     * @throws SQLException SQL実行例外
     */
    private List<Integer> __getDeleteSchExdataSid() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID ");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXDATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID not in (");
            sql.addSql("     select ");
            sql.addSql("       SCE_SID");
            sql.addSql("     from ");
            sql.addSql("       SCH_DATA");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("SCE_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] 削除対象の添付ファイルSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSidList 削除対象スケジュール拡張SID
     * @return 削除対象の添付ファイルSID
     * @throws SQLException SQL実行例外
     */
    private List<Integer> __getDeleteBinSid(List<Integer> sceSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID ");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXDATA_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in ( ");
            for (int idx = 0; idx < sceSidList.size(); idx++) {
                sql.addSql(" ?");
                if (idx != sceSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(sceSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info("SQL:" + sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("BIN_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] スケジュールと紐づいていない拡張スケジュールを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSidList 拡張スケジュールSIDリスト
     * @throws SQLException SQL実行例外
     */
    private void __deleteSchExdata(List<Integer> sceSidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   SCH_EXDATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in ( ");
            for (int idx = 0; idx < sceSidList.size(); idx++) {
                sql.addSql(" ?");
                if (idx != sceSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(sceSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] スケジュールと紐づいていない拡張スケジュール公開情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSidList 拡張スケジュールSIDリスト
     * @throws SQLException SQL実行例外
     */
    private void __deleteSchExdataPub(List<Integer> sceSidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in ( ");
            for (int idx = 0; idx < sceSidList.size(); idx++) {
                sql.addSql(" ?");
                if (idx != sceSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(sceSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] スケジュールと紐づいていない拡張スケジュール公開情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSidList 拡張スケジュールSIDリスト
     * @throws SQLException SQL実行例外
     */
    private void __deleteSchExdataBin(List<Integer> sceSidList) throws SQLException {


        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   SCH_EXDATA_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in ( ");
            for (int idx = 0; idx < sceSidList.size(); idx++) {
                sql.addSql(" ?");
                if (idx != sceSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(sceSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] スケジュールと紐づいていない拡張スケジュール公開情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSidList 添付ファイルSIDリスト
     * @throws SQLException SQL実行例外
     */
    private void __deleteCmnBinf(List<Integer> binSidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(GSConst.SYSTEM_USER_ADMIN);
            sql.addDateValue(new UDate());

            sql.addSql(" where ");
            sql.addSql("   BIN_SID in ( ");
            for (int idx = 0; idx < binSidList.size(); idx++) {
                sql.addSql(" ?");
                if (idx != binSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(binSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 既存のスケジュール拡張情報にリマインダー通知を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __updateSchExdata() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   SCH_EXDATA");
            sql.addSql(" set ");
            sql.addSql("   SCE_TARGET_APP = ?,");
            sql.addSql("   SCE_TARGET_PC = ?,");
            sql.addSql("   SCE_TARGET_GRP = ?,");
            sql.addSql("   SCE_REMINDER = ?");

            sql.addIntValue(GSConstSchedule.REMINDER_USE_YES);
            sql.addIntValue(GSConstSchedule.REMINDER_USE_YES);
            sql.addIntValue(GSConstSchedule.REMINDER_USE_YES);
            sql.addIntValue(GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES);

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 利用状況の次回送信日を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __updateInfoSendDate() throws SQLException {

        Connection con = getCon();
        CmnContmDao ccmDao = new CmnContmDao(con);
        //String gsUid = ccmDao.getGsUid();
        int addMonth = 0;
        int addDay = 0;
        int addHour = 0;
        int addMinute = 0;

        try {
            String gsUid = __getDecryString(ccmDao.getGsUid());
            String[] uidAry = gsUid.split(":");
            String uidFirst = uidAry[0].replace("SN", "");
            String uidSecond = uidAry[1];

            long uidFirstDec = Long.parseLong(uidFirst, 16);
            long uidSecondDec = Long.parseLong(uidSecond, 16);

            addMonth = (int) (uidFirstDec % 5);
            addDay = (int) (uidSecondDec % 30);
            addHour = (int) (uidFirstDec % 24);
            addMinute = (int) (uidSecondDec % 60);
        } catch (Exception e) {
            Random random = new Random();
            addMonth = random.nextInt(6);
            addDay = random.nextInt(31);
            addHour = random.nextInt(24);
            addMinute = random.nextInt(60);
        }

        UDate date = new UDate();
        date.addMonth(addMonth);
        date.addDay(addDay);
        date.addHour(addHour);
        date.addMinute(addMinute);

        __insertSendDate(date);
    }

    /**
     * <br>[機  能] 利用状況の次回送信日時テーブルを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 次回送信日時
     * @throws SQLException SQL実行例外
     */
    private void __insertSendDate(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CMN_INFO_SEND_DATE ( ");
            sql.addSql("   CSD_DATE ");
            sql.addSql(" ) ");
            sql.addSql(" values ( ");
            sql.addSql("   ? ");
            sql.addSql(" ) ");

            sql.addDateValue(date);
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 暗号化された文字を復号する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 暗号化された文字
     * @return 復号化した文字
     * @throws EncryptionException 文字の復号に失敗
     */
    private String __getDecryString(String target) throws EncryptionException {

        String decStr = null;

        if (target != null) {
            try {
                byte[] decBytes = Base64.decodeBase64(target.getBytes(Encoding.UTF_8));
                decStr = Blowfish.decrypt(GSConstLicese.LICENSE_PHRASE, decBytes);
            } catch (Exception e) {
                throw new EncryptionException("復号化に失敗", e);
            }
        }

        return decStr;
    }

    /**
     * <br>[機  能] キャビネットのソート変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param personalKbn キャビネット区分 0:共有, 1:個人
     * @throws SQLException SQL実行例外
     */
    private void __updateCabinetSort(int personalKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_PERSONAL_FLG = ?");
            sql.addSql(" order by ");
            sql.addSql("   FCB_SORT ");

            sql.addIntValue(personalKbn);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<Integer> cabSidList = new ArrayList<Integer>();
            while (rs.next()) {
                cabSidList.add(rs.getInt("FCB_SID"));
            }

            if (cabSidList.isEmpty()) {
                return;
            }

            //対象のソート順を修正
            __updateSort(cabSidList);
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
    }

    /**
     * <br>[機  能] ソート変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSidList キャビネットSID
     * @throws SQLException SQL実行例外
     */
    private void __updateSort(List<Integer> fcbSidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            int sort = 1;
            for (int fcbSid : fcbSidList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   FILE_CABINET");
                sql.addSql(" set ");
                sql.addSql("   FCB_SORT=?");
                sql.addSql(" where ");
                sql.addSql("   FCB_SID=?");
                sql.addIntValue(sort);
                sql.addIntValue(fcbSid);
                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sort++;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <br>[機  能] 個人キャビネットの更新通知設定を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __deletePrivateCallConf() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   FILE_CALL_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID in (");
            sql.addSql("     select ");
            sql.addSql("       FDR_SID ");
            sql.addSql("     from ");
            sql.addSql("       FILE_DIRECTORY,");
            sql.addSql("       FILE_CABINET");
            sql.addSql("     where ");
            sql.addSql("       FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("     and ");
            sql.addSql("       FILE_CABINET.FCB_PERSONAL_FLG = ?");
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <br>[機  能] 個人キャビネットの更新通知データを削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __deletePrivateCallData() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   FILE_CALL_DATA");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID in (");
            sql.addSql("     select ");
            sql.addSql("       FDR_SID ");
            sql.addSql("     from ");
            sql.addSql("       FILE_DIRECTORY,");
            sql.addSql("       FILE_CABINET");
            sql.addSql("     where ");
            sql.addSql("       FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("     and ");
            sql.addSql("       FILE_CABINET.FCB_PERSONAL_FLG = ?");
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }
}
