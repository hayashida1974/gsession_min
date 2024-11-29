package jp.groupsession.v2.convert.convert490.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;


/**
 *
 * <br>[機  能] チャットのコンバートを行う際に使用
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvChatDao {

    /** ログ */
    private static Log log__ = LogFactory.getLog(ConvChatDao.class);

    /**
    *
    * <br>[機  能] チャット関連テーブルのコンバート処理
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
   public void convert(Connection con) throws SQLException {
       // チャットテーブル追加
       __createChtAdmConfTable(con);
       __createChtGroupTargetTable(con);
       __createChtPriConfTable(con);
       __createChtGroupInfTable(con);
       __createChtGroupUserTable(con);
       __createChtCategoryTable(con);
       __createChtGroupDataTable(con);
       __createChtGroupViewTable(con);
       __createChtUserPairTable(con);
       __createChtUserDataTable(con);
       __createChtUserViewTable(con);
       __createChtSpaccessTable(con);
       __createChtSpaccessTargetTable(con);
       __createChtSpaccessPermitTable(con);
       __createChtLogCountTable(con);
       __createChtLogCountSumTable(con);
       __createChtFavoriteTable(con);
       // マスターデータ作成
       // カテゴリ
       List<ChtCategoryModel> categoryList = __createMasterCategoryData();
       // マスターデータinsert
       // カテゴリ
       for (ChtCategoryModel mdl: categoryList) {
           __insertMasterDataChtCategoryTable(mdl, con);
       }
   }

   /**
    * <p>管理者設定情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtAdmConfTable(Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();

       try {
           //SQL文
           sql.addSql(" create table CHT_ADM_CONF (");
           sql.addSql("   CAC_CHAT_FLG integer not null,");
           sql.addSql("   CAC_GROUP_FLG integer not null,");
           sql.addSql("   CAC_GROUP_KBN integer not null,");
           sql.addSql("   CAC_READ_FLG integer not null,");
           sql.addSql("   CAC_ATDEL_FLG integer not null,");
           sql.addSql("   CAC_ATDEL_Y integer,");
           sql.addSql("   CAC_ATDEL_M integer,");
           sql.addSql("   CAC_AUID integer not null,");
           sql.addSql("   CAC_ADATE timestamp not null,");
           sql.addSql("   CAC_EUID integer not null,");
           sql.addSql("   CAC_EDATE timestamp not null");
           sql.addSql(" )");

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットグループ対象テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtGroupTargetTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_GROUP_TARGET (");
           sql.addSql("   CGT_TYPE integer not null,");
           sql.addSql("   CGT_SSID integer not null");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットグループ情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtPriConfTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_PRI_CONF (");
           sql.addSql("   CPC_PRI_UID integer not null,");
           sql.addSql("   CPC_PUSH_FLG integer not null,");
           sql.addSql("   CPC_PUSH_TIME integer not null,");
           sql.addSql("   CPC_DEF_FLG integer not null,");
           sql.addSql("   CPC_CHAT_KBN integer not null,");
           sql.addSql("   CPC_SEL_SID integer not null,");
           sql.addSql("   CPC_ENTER_FLG integer not null,");
           sql.addSql("   CPC_AUID integer not null,");
           sql.addSql("   CPC_ADATE timestamp not null,");
           sql.addSql("   CPC_EUID integer not null,");
           sql.addSql("   CPC_EDATE timestamp not null,");
           sql.addSql("   primary key (CPC_PRI_UID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットグループ対象テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtGroupInfTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_GROUP_INF (");
           sql.addSql("   CGI_SID integer not null,");
           sql.addSql("   CGI_ID varchar(20) not null,");
           sql.addSql("   CGI_NAME varchar(100) not null,");
           sql.addSql("   CGI_CONTENT varchar(500),");
           sql.addSql("   CGI_COMP_FLG integer not null,");
           sql.addSql("   CGI_DEL_FLG integer not null,");
           sql.addSql("   CHC_SID integer,");
           sql.addSql("   CGI_AUID integer not null,");
           sql.addSql("   CGI_ADATE timestamp not null,");
           sql.addSql("   CGI_EUID integer not null,");
           sql.addSql("   CGI_EDATE timestamp not null,");
           sql.addSql("   primary key (CGI_SID)");
           sql.addSql(" )");

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットグループユーザ情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtGroupUserTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_GROUP_USER (");
           sql.addSql("   CGI_SID integer not null,");
           sql.addSql("   CGU_KBN integer not null,");
           sql.addSql("   CGU_SELECT_SID integer not null,");
           sql.addSql("   CGU_ADMIN_FLG integer not null,");
           sql.addSql("   CGU_AUID integer not null,");
           sql.addSql("   CGU_ADATE timestamp not null,");
           sql.addSql("   CGU_EUID integer not null,");
           sql.addSql("   CGU_EDATE timestamp not null,");
           sql.addSql("   primary key (CGI_SID,CGU_KBN,CGU_SELECT_SID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットカテゴリ情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtCategoryTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_CATEGORY (");
           sql.addSql("   CHC_SID integer not null,");
           sql.addSql("   CHC_NAME varchar(20) not null,");
           sql.addSql("   CHC_SORT integer not null,");
           sql.addSql("   CHC_AUID integer not null,");
           sql.addSql("   CHC_ADATE timestamp not null,");
           sql.addSql("   CHC_EUID integer not null,");
           sql.addSql("   CHC_EDATE timestamp not null,");
           sql.addSql("   primary key (CHC_SID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットグループ投稿情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtGroupDataTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_GROUP_DATA (");
           sql.addSql("   CGD_SID bigint not null,");
           sql.addSql("   CGI_SID integer not null,");
           sql.addSql("   CGD_TEXT varchar(3000),");
           sql.addSql("   BIN_SID integer,");
           sql.addSql("   CGD_SEND_UID integer not null,");
           sql.addSql("   CGD_STATE_KBN integer not null,");
           sql.addSql("   CGD_AUID integer not null,");
           sql.addSql("   CGD_ADATE timestamp not null,");
           sql.addSql("   CGD_EUID integer not null,");
           sql.addSql("   CGD_EDATE timestamp not null,");
           sql.addSql("   primary key (CGD_SID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットグループ閲覧情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtGroupViewTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_GROUP_VIEW (");
           sql.addSql("   CGI_SID integer not null,");
           sql.addSql("   CGV_UID integer not null,");
           sql.addSql("   CGD_SID bigint not null,");
           sql.addSql("   primary key (CGI_SID,CGV_UID)");
           sql.addSql(" )");

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットユーザペア情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtUserPairTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_USER_PAIR (");
           sql.addSql("   CUP_SID integer not null,");
           sql.addSql("   CUP_UID_F integer not null,");
           sql.addSql("   CUP_UID_S integer not null,");
           sql.addSql("   CUP_COMP_FLG integer not null,");
           sql.addSql("   primary key (CUP_SID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットユーザ投稿情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtUserDataTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_USER_DATA (");
           sql.addSql("   CUD_SID bigint not null,");
           sql.addSql("   CUP_SID integer not null,");
           sql.addSql("   CUD_TEXT varchar(3000),");
           sql.addSql("   BIN_SID integer,");
           sql.addSql("   CUD_SEND_UID integer not null,");
           sql.addSql("   CUD_STATE_KBN integer not null,");
           sql.addSql("   CUD_AUID integer not null,");
           sql.addSql("   CUD_ADATE timestamp not null,");
           sql.addSql("   CUD_EUID integer not null,");
           sql.addSql("   CUD_EDATE timestamp not null,");
           sql.addSql("   primary key (CUD_SID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットユーザ閲覧情報テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtUserViewTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_USER_VIEW (");
           sql.addSql("   CUP_SID integer not null,");
           sql.addSql("   CUV_UID integer not null,");
           sql.addSql("   CUD_SID bigint not null,");
           sql.addSql("   primary key (CUP_SID,CUV_UID)");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャット特例アクセステーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtSpaccessTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_SPACCESS (");
           sql.addSql("   CHS_SID integer not null,");
           sql.addSql("   CHS_NAME varchar(50) not null,");
           sql.addSql("   CHS_BIKO varchar(1000),");
           sql.addSql("   CHS_AUID integer not null,");
           sql.addSql("   CHS_ADATE timestamp not null,");
           sql.addSql("   CHS_EUID integer not null,");
           sql.addSql("   CHS_EDATE timestamp not null,");
           sql.addSql("   primary key (CHS_SID)");
           sql.addSql(" )");

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャット特例アクセス_制限対象テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtSpaccessTargetTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_SPACCESS_TARGET (");
           sql.addSql("   CHS_SID integer,");
           sql.addSql("   CST_TYPE integer not null,");
           sql.addSql("   CST_PSID integer not null");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャット特例アクセス_許可対象テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtSpaccessPermitTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_SPACCESS_PERMIT (");
           sql.addSql("   CHS_SID integer,");
           sql.addSql("   CSP_TYPE integer not null,");
           sql.addSql("   CSP_PSID integer not null");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャット集計データテーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtLogCountTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_LOG_COUNT (");
           sql.addSql("   CLC_USR_SID integer not null,");
           sql.addSql("   CLC_CHAT_KBN integer not null,");
           sql.addSql("   CLC_DATE timestamp not null");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャット集計データ_集計結果テーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtLogCountSumTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_LOG_COUNT_SUM (");
           sql.addSql("   CLS_SEND_CNT integer not null,");
           sql.addSql("   CLS_SEND_UCNT integer not null,");
           sql.addSql("   CLS_SEND_GCNT integer not null,");
           sql.addSql("   CLS_USER_CNT integer not null,");
           sql.addSql("   CLS_DATE Date not null,");
           sql.addSql("   CLS_YEAR_MONTH integer not null,");
           sql.addSql("   CLS_EDATE timestamp not null");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <p>チャットお気に入りテーブルを作成
    * @param con コネクション
    * @throws SQLException SQL実行例外
    */
   private void __createChtFavoriteTable(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();
       try {
           //SQL文
           sql.addSql(" create table CHT_FAVORITE (");
           sql.addSql("   CHF_UID integer not null,");
           sql.addSql("   CHF_CHAT_KBN integer not null,");
           sql.addSql("   CHF_SID integer not null");
           sql.addSql(" )");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <br>[機  能] カテゴリmodel作成を行います
    * <br>[解  説]
    * <br>[備  考]
    * @return カテゴリmodel一覧
    * @throws SQLException SQL例外
    */
   private List<ChtCategoryModel> __createMasterCategoryData()
                                                throws SQLException {
       UDate now = new UDate();
       List<ChtCategoryModel> ret = new ArrayList<ChtCategoryModel>();
       ret.add(__createCategoryMdl("カテゴリなし", -1, now));
       ret.add(__createCategoryMdl("一般ユーザ", 0, now));
       return ret;
   }


   /**
    * <br>[機  能] カテゴリmodel作成を行います
    * <br>[解  説]
    * <br>[備  考]
    * @param categoryName カテゴリ
    * @param chcSid カテゴリSID
    * @param now 日時
    * @return カテゴリmodel
    * @throws SQLException SQL例外
    */
   private ChtCategoryModel __createCategoryMdl(String categoryName,
                                               int chcSid,
                                               UDate now) throws SQLException {
       ChtCategoryModel model = new ChtCategoryModel();
       model.setChcSid(chcSid);
       model.setChcSort(chcSid);
       model.setChcName(categoryName);
       model.setChcAdate(now);
       model.setChcEdate(now);
       model.setChcAuid(-1);
       model.setChcEuid(-1);
       return model;
   }

   /**
    * <p>Insert CHT_CATEGORY Data Bindding JavaBean
    * @param bean CHT_CATEGORY Data Bindding JavaBean
    * @param con Connection
    * @throws SQLException SQL実行例外
    */
   public void __insertMasterDataChtCategoryTable(ChtCategoryModel bean,
           Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" insert ");
           sql.addSql(" into ");
           sql.addSql(" CHT_CATEGORY(");
           sql.addSql("   CHC_SID,");
           sql.addSql("   CHC_NAME,");
           sql.addSql("   CHC_SORT,");
           sql.addSql("   CHC_AUID,");
           sql.addSql("   CHC_ADATE,");
           sql.addSql("   CHC_EUID,");
           sql.addSql("   CHC_EDATE");
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
           sql.addIntValue(bean.getChcSid());
           sql.addStrValue(bean.getChcName());
           sql.addIntValue(bean.getChcSort());
           sql.addIntValue(bean.getChcAuid());
           sql.addDateValue(bean.getChcAdate());
           sql.addIntValue(bean.getChcEuid());
           sql.addDateValue(bean.getChcEdate());
           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }



}
