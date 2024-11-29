package jp.groupsession.v2.convert.convert480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 *
 * <br>[機  能] 稟議関連 コンバートDAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvRingiDao {
    /**
    *
    * <br>[機  能] 稟議 添付情報コンバートクラス
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   private static class UpdateRnb extends AbstractInsertKeiroTable {

       @Override
       public void createStatement(Connection con) throws SQLException {
           PreparedStatement pstmt = null;

           try {
               //SQL文
               SqlBuffer sql = new SqlBuffer();
               sql.addSql(" update");
               sql.addSql("   RNG_BIN");
               sql.addSql(" set ");
               sql.addSql("   RKS_SID=? ");
               sql.addSql(" where ");
               sql.addSql("   RNG_SID=?");
               sql.addSql("   and USR_SID=?");
               pstmt = con.prepareStatement(sql.toSqlString());
               pstmt__ = pstmt;
               sql__ = sql;
           } catch (SQLException e) {
               throw e;
           }
       }

       @Override
       public void execute(int rksSid, ResultSet rs) throws SQLException {
           pstmt__.clearParameters();
           sql__.clearValue();
           //RKS_SID
           sql__.addIntValue(rksSid);
           //where
           //RNG_SID
           sql__.addIntValue(rs.getInt("RNG_SID"));
           //USR_SID
           sql__.addIntValue(rs.getInt("USR_SID"));
           log__.info(sql__.toLogString());
           sql__.setParameter(pstmt__);
           pstmt__.executeUpdate();
       }

   }

    /**
     *
     * <br>[機  能] テンプレートデータコンバートクラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class UpdateRtp extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {
            PreparedStatement pstmt = null;

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   RNG_TEMPLATE");
                sql.addSql(" set ");
                sql.addSql("   RTP_FORM=? ");
                sql.addSql(" where ");
                sql.addSql("   RTP_SID=?");
                sql.addSql("   and RTP_VER=?");
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt__ = pstmt;
                sql__ = sql;
            } catch (SQLException e) {
                throw e;
            }
        }
        /**
        *
        * <br>[機  能] 汎用稟議 の内容要素となるFormCellの生成
        * <br>[解  説]
        * <br>[備  考]
        * @param defValue 初期値
        * @return FormCell
        */
       private String __createHanyouRingiNaiyo(String defValue) {
           JSONObject cell = new JSONObject();
           cell.element("formID", "汎用稟議＿内容");
           cell.element("require", 1);
           cell.element("sid", 0);
           cell.element("title", "内容");
           cell.element("type", "textarea");
           JSONObject body = new JSONObject();
           //"body":{
           body.element("defaultValue", defValue);
           body.element("maxlength", 1000);
           //}
           cell.element("body", body);

           JSONArray col = new JSONArray();
           col.add(cell);

           JSONArray row = new JSONArray();
           row.add(col);
           return row.toString();
       }

        @Override
        public void execute(int nouse, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
            //RTP_FORM
            String content = rs.getString("RTP_CONTENT");
            sql__.addStrValue(__createHanyouRingiNaiyo(content));
            //where
            //RTP_SID
            sql__.addIntValue(rs.getInt("RTP_SID"));
            //RTP_VER
            sql__.addIntValue(0);
            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();
        }

    }
    /**
     *
     * <br>[機  能] 稟議テンプレートフォーム情報 作成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class InsertRtf extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {
            PreparedStatement pstmt = null;

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" RNG_TEMPLATE_FORM(");
                sql.addSql("   RTP_SID,");
                sql.addSql("   RTP_VER,");
                sql.addSql("   RTF_SID,");
                sql.addSql("   RTF_ID,");
                sql.addSql("   RTF_TITLE,");
                sql.addSql("   RTF_REQUIRE,");
                sql.addSql("   RTF_TYPE,");
                sql.addSql("   RTF_BODY");
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
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt__ = pstmt;
                sql__ = sql;

            } catch (SQLException e) {
                throw e;
            }

        }

        @Override
        public void execute(int rksSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
//            sql.addSql("   RTP_SID,");
            sql__.addIntValue(rs.getInt("RTP_SID"));
//            sql.addSql("   RTP_VER,");
            sql__.addIntValue(0);
//            sql.addSql("   RTF_SID,");
            sql__.addIntValue(0);
//            sql.addSql("   RTF_ID,");
            sql__.addStrValue("汎用稟議＿内容");
//            sql.addSql("   RTF_TITLE,");
            sql__.addStrValue("内容");
//            sql.addSql("   RTF_REQUIRE,");
            sql__.addIntValue(1);
//            sql.addSql("   RTF_TYPE,");
            sql__.addIntValue(3);
//            sql.addSql("   RTF_BODY");
            sql__.addStrValue(null);
            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();

        }
    }
    /**
     *
     * <br>[機  能] テンプレート経路ユーザ コンバートクラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class InsertRkuForRTP extends InsertRkuForRCT {

        @Override
        public void execute(int rtkSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
    //          RTK_SID,
           sql__.addIntValue(rtkSid);
    //          USR_SID,
           sql__.addIntValue(rs.getInt("USR_SID"));
    //          GRP_SID,
           sql__.addIntValue(-1);
    //          POS_SID,
           sql__.addIntValue(-1);
    //          RKU_TYPE,
           sql__.addIntValue(rs.getInt("RTU_TYPE"));
    //          RKU_AUID,
           sql__.addIntValue(rs.getInt("RTU_AUID"));
    //          RKU_ADATE,
           sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RTU_ADATE")));
    //          RKU_EUID,
           sql__.addIntValue(rs.getInt("RTU_EUID"));
    //          RKU_EDATE
           sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RTU_EDATE")));

           log__.info(sql__.toLogString());
           sql__.setParameter(pstmt__);
           pstmt__.executeUpdate();
        }
    }
    /**
     *
     * <br>[機  能] テンプレート経路ステップ コンバートクラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class InsertRtkForRTP extends InsertRtkForRCT {

        @Override
        public void execute(int rtkSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
//             RTK_SID,
            sql__.addIntValue(rtkSid);
//             RCT_SID,
            sql__.addIntValue(0);
//             USR_SID,
            sql__.addIntValue(0);
//             RTP_SID,
            sql__.addIntValue(rs.getInt("RTP_SID"));
//             RTP_VER,
            sql__.addIntValue(0);
//             RTK_SORT,
            sql__.addIntValue(rs.getInt("RTU_SORT"));
//             RTK_LEVEL,
            sql__.addIntValue(0);
//             RTK_NAME,
            sql__.addStrValue("");
//             RTK_TYPE,
            sql__.addIntValue(0);
//             RTK_ROLL_TYPE,
            sql__.addIntValue(rs.getInt("RTU_TYPE"));
//             RTK_OUTCONDITION,
            sql__.addIntValue(1);
//             RTK_OUTCOND_BORDER,
            sql__.addIntValue(0);
//             RTK_NOUSER,
            sql__.addIntValue(0);
//             RTK_ADDSTEP,
            sql__.addIntValue(0);
//             RTK_KEIRO_SKIP,
            sql__.addIntValue(0);
//             RTK_KEIRO_KOETU,
            sql__.addIntValue(1);
//             RTK_MULTISEL_FLG,
            sql__.addIntValue(0);
//             RTK_BOSSSTEP_CNT,
            sql__.addIntValue(0);
//             RTK_BOSSSTEP_MASTCNT,
            sql__.addIntValue(0);
//             RTK_AUID,
            sql__.addIntValue(rs.getInt("RTU_AUID"));
//             RTK_ADATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RTU_ADATE")));
//             RTK_EUID,
            sql__.addIntValue(rs.getInt("RTU_EUID"));
//             RTK_EDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RTU_EDATE")));
//             RTK_JKBN,
            sql__.addIntValue(0);
//             RTK_KOETU_SIZI
            sql__.addIntValue(1);
//          RTK_OWNSINGI
            sql__.addIntValue(0);
            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();
        }
    }
    /**
     *
     * <br>[機  能] 稟議経路テンプレートユーザ情報登録クラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class InsertRkuForRCT extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {
            PreparedStatement pstmt = null;
            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" RNG_TEMPLATE_KEIRO_USER(");
                sql.addSql("   RTK_SID,");
                sql.addSql("   USR_SID,");
                sql.addSql("   GRP_SID,");
                sql.addSql("   POS_SID,");
                sql.addSql("   RKU_TYPE,");
                sql.addSql("   RKU_AUID,");
                sql.addSql("   RKU_ADATE,");
                sql.addSql("   RKU_EUID,");
                sql.addSql("   RKU_EDATE");
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
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt__ = pstmt;
                sql__ = sql;
            } catch (SQLException e) {
                throw e;
            }

        }

        @Override
        public void execute(int rtkSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
//               RTK_SID,
            sql__.addIntValue(rtkSid);
//               USR_SID,
            sql__.addIntValue(rs.getInt("USR_SID"));
//               GRP_SID,
            sql__.addIntValue(-1);
//               POS_SID,
            sql__.addIntValue(-1);
//               RKU_TYPE,
            sql__.addIntValue(rs.getInt("RCU_TYPE"));
//               RKU_AUID,
            sql__.addIntValue(rs.getInt("RCU_AUID"));
//               RKU_ADATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RCU_ADATE")));
//               RKU_EUID,
            sql__.addIntValue(rs.getInt("RCU_EUID"));
//               RKU_EDATE
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RCU_EDATE")));

            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();

        }

    }
    /**
     *
     * <br>[機  能] テンプレート経路ステップコンバート クラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class InsertRtkForRCT extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {
            PreparedStatement pstmt = null;

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" RNG_TEMPLATE_KEIRO(");
                sql.addSql("   RTK_SID,");
                sql.addSql("   RCT_SID,");
                sql.addSql("   USR_SID,");
                sql.addSql("   RTP_SID,");
                sql.addSql("   RTP_VER,");
                sql.addSql("   RTK_SORT,");
                sql.addSql("   RTK_LEVEL,");
                sql.addSql("   RTK_NAME,");
                sql.addSql("   RTK_TYPE,");
                sql.addSql("   RTK_ROLL_TYPE,");
                sql.addSql("   RTK_OUTCONDITION,");
                sql.addSql("   RTK_OUTCOND_BORDER,");
                sql.addSql("   RTK_NOUSER,");
                sql.addSql("   RTK_ADDSTEP,");
                sql.addSql("   RTK_KEIRO_SKIP,");
                sql.addSql("   RTK_KEIRO_KOETU,");
                sql.addSql("   RTK_MULTISEL_FLG,");
                sql.addSql("   RTK_BOSSSTEP_CNT,");
                sql.addSql("   RTK_BOSSSTEP_MASTCNT,");
                sql.addSql("   RTK_AUID,");
                sql.addSql("   RTK_ADATE,");
                sql.addSql("   RTK_EUID,");
                sql.addSql("   RTK_EDATE,");
                sql.addSql("   RTK_JKBN,");
                sql.addSql("   RTK_KOETU_SIZI,");
                sql.addSql("   RTK_OWNSINGI");
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
                pstmt__ = pstmt;
                sql__ = sql;
            } catch (SQLException e) {
                throw e;
            }
        }

        @Override
        public void execute(int rtkSid, ResultSet rs) throws SQLException {

            pstmt__.clearParameters();
            sql__.clearValue();
//             RTK_SID,
            sql__.addIntValue(rtkSid);
//             RCT_SID,
            sql__.addIntValue(rs.getInt("RCT_SID"));
//             USR_SID,
            sql__.addIntValue(rs.getInt("RCT_USR_SID"));
//             RTP_SID,
            sql__.addIntValue(0);
//             RTP_VER,
            sql__.addIntValue(0);
//             RTK_SORT,
            sql__.addIntValue(rs.getInt("RCU_SORT"));
//             RTK_LEVEL,
            sql__.addIntValue(0);
//             RTK_NAME,
            sql__.addStrValue("");
//             RTK_TYPE,
            sql__.addIntValue(0);
//             RTK_ROLL_TYPE,
            sql__.addIntValue(rs.getInt("RCU_TYPE"));
//             RTK_OUTCONDITION,
            sql__.addIntValue(1);
//             RTK_OUTCOND_BORDER,
            sql__.addIntValue(0);
//             RTK_NOUSER,
            sql__.addIntValue(0);
//             RTK_ADDSTEP,
            sql__.addIntValue(0);
//             RTK_KEIRO_SKIP,
            sql__.addIntValue(0);
//             RTK_KEIRO_KOETU,
            sql__.addIntValue(1);
//             RTK_MULTISEL_FLG,
            sql__.addIntValue(0);
//             RTK_BOSSSTEP_CNT,
            sql__.addIntValue(0);
//             RTK_BOSSSTEP_MASTCNT,
            sql__.addIntValue(0);
//             RTK_AUID,
            sql__.addIntValue(rs.getInt("RCU_AUID"));
//             RTK_ADATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RCU_ADATE")));
//             RTK_EUID,
            sql__.addIntValue(rs.getInt("RCU_EUID"));
//             RTK_EDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RCU_EDATE")));
//             RTK_JKBN,
            sql__.addIntValue(0);
//             RTK_KOETU_SIZI
            sql__.addIntValue(1);
//             RTK_OWNSINGI
            sql__.addIntValue(0);

            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();

        }

    }

    /**
    *
    * <br>[機  能] 管理者設定コンバート クラス
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   private static class UpdateRar extends AbstractInsertKeiroTable {

       @Override
       public void createStatement(Connection con) throws SQLException {
           PreparedStatement pstmt = null;

           try {
               //SQL文
               SqlBuffer sql = new SqlBuffer();
               sql.addSql(" update ");
               sql.addSql("   RNG_ACONF ");
               sql.addSql(" set");
               sql.addSql("   RAR_SML_JUSIN_KBN = ?,");
               sql.addSql("   RAR_SML_KOETU_KBN = ?");
               pstmt = con.prepareStatement(sql.toSqlString());
               pstmt__ = pstmt;
               sql__ = sql;
           } catch (SQLException e) {
               throw e;
           }
       }

       @Override
       public void execute(int smlKbn, ResultSet rs) throws SQLException {

           pstmt__.clearParameters();
           sql__.addIntValue(smlKbn);
           sql__.addIntValue(smlKbn);

           log__.info(sql__.toLogString());
           sql__.setParameter(pstmt__);
           pstmt__.executeUpdate();
       }

   }

   /**
   *
   * <br>[機  能] 個人設定コンバート クラス
   * <br>[解  説]
   * <br>[備  考]
   *
   * @author JTS
   */
  private static class UpdateRur extends AbstractInsertKeiroTable {

      @Override
      public void createStatement(Connection con) throws SQLException {
          PreparedStatement pstmt = null;

          try {
              //SQL文
              SqlBuffer sql = new SqlBuffer();
              sql.addSql(" update ");
              sql.addSql("   RNG_UCONF ");
              sql.addSql(" set");
              sql.addSql("   RUR_SML_JUSIN = ?,");
              sql.addSql("   RUR_SML_KOETU = ?");
              sql.addSql(" where");
              sql.addSql("   USR_SID = ?");
              pstmt = con.prepareStatement(sql.toSqlString());
              pstmt__ = pstmt;
              sql__ = sql;
          } catch (SQLException e) {
              throw e;
          }
      }

      @Override
      public void execute(int usrSid, ResultSet rs) throws SQLException {

          pstmt__.clearParameters();
          sql__.addIntValue(rs.getInt("RUR_SML_NTF"));
          sql__.addIntValue(rs.getInt("RUR_SML_NTF"));
          sql__.addIntValue(usrSid);

          log__.info(sql__.toLogString());
          sql__.setParameter(pstmt__);
          pstmt__.executeUpdate();
      }

  }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvRingiDao.class);

    /**
     *
     * <br>[機  能] 稟議関連テーブルのコンバート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    public void convert(Connection con, MlCountMtController saiban) throws SQLException {
        //テーブル定義追加
        List<SqlBuffer> sqlList = __updateTableTeigi(saiban);
        __executeQuery(con, sqlList);

        //既存データのコンバート
        __dataConvert(con, saiban);

        //旧テーブル定義削除
        sqlList = __dropOldTableTeigi();
        __executeQuery(con, sqlList);
    }
    /**
     *
     * <br>[機  能] データコンバート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __dataConvert(Connection con, MlCountMtController saiban) throws SQLException {
        __convertRngRndata(con);

        __convertRngChannel(con, saiban);

        __convertRngChannelTemplate(con, saiban);

        __convertRngTemplate(con, saiban);

        __convertRngTemplateUser(con, saiban);

        __convertRngAconf(con);

        __convertRngUconf(con);

    }
    /**
    *
    * <br>[機  能] 管理者設定 コンバート
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
   private void __convertRngUconf(Connection con) throws SQLException {
       //稟議テンプレート経路ステップ
       UpdateRur updateRur = new ConvRingiDao.UpdateRur();
       try {
           PreparedStatement pstmt = null;
           ResultSet rs = null;
           try {
               updateRur.createStatement(con);
               //SQL文
               SqlBuffer sql = new SqlBuffer();
               sql.addSql(" select ");
               sql.addSql("   USR_SID,");
               sql.addSql("   RUR_SML_NTF,");
               sql.addSql("   RUR_VIEW_CNT,");
               sql.addSql("   RUR_SML_JUSIN,");
               sql.addSql("   RUR_SML_KOETU");
               sql.addSql(" from ");
               sql.addSql("   RNG_UCONF");

               pstmt = con.prepareStatement(sql.toSqlString());
               log__.info(sql.toLogString());
               rs = pstmt.executeQuery();
               int usrSid = -1;
               if (rs.next()) {
                   usrSid = rs.getInt("USR_SID");
                   updateRur.execute(usrSid, rs);
               }
           } catch (SQLException e) {
               throw e;
           } finally {
               JDBCUtil.closeResultSet(rs);
               JDBCUtil.closeStatement(pstmt);
           }

       } catch (SQLException e) {
           throw e;
       } finally {
           updateRur.close();
       }

   }
    /**
    *
    * <br>[機  能] 管理者設定 コンバート
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
   private void __convertRngAconf(Connection con) throws SQLException {
       //稟議テンプレート経路ステップ
       UpdateRar updateRar = new ConvRingiDao.UpdateRar();
       try {
           PreparedStatement pstmt = null;
           ResultSet rs = null;
           try {
               updateRar.createStatement(con);
               //SQL文
               SqlBuffer sql = new SqlBuffer();
               sql.addSql(" select ");
               sql.addSql("   RAR_DEL_AUTH,");
               sql.addSql("   RAR_AUID,");
               sql.addSql("   RAR_ADATE,");
               sql.addSql("   RAR_EUID,");
               sql.addSql("   RAR_EDATE,");
               sql.addSql("   RAR_SML_NTF,");
               sql.addSql("   RAR_SML_NTF_KBN,");
               sql.addSql("   RAR_SML_JUSIN_KBN,");
               sql.addSql("   RAR_RNGID,");
               sql.addSql("   RAR_RNGID_DEF_SID,");
               sql.addSql("   RAR_SML_KOETU_KBN,");
               sql.addSql("   RAR_OVERLAP");
               sql.addSql(" from ");
               sql.addSql("   RNG_ACONF");

               pstmt = con.prepareStatement(sql.toSqlString());
               log__.info(sql.toLogString());
               rs = pstmt.executeQuery();
               int smlKbn = 1;
               if (rs.next()) {
                   smlKbn = rs.getInt("RAR_SML_NTF_KBN");
                   updateRar.execute(smlKbn, rs);
               }
           } catch (SQLException e) {
               throw e;
           } finally {
               JDBCUtil.closeResultSet(rs);
               JDBCUtil.closeStatement(pstmt);
           }

       } catch (SQLException e) {
           throw e;
       } finally {
           updateRar.close();
       }
   }
    /**
     *
     * <br>[機  能] テンプレート経路ユーザ コンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convertRngTemplateUser(Connection con,
            MlCountMtController saiban) throws SQLException {
        //稟議テンプレート経路ステップ
        InsertRtkForRTP insertRtk = new ConvRingiDao.InsertRtkForRTP();
        //稟議テンプレート経路ユーザ
        AbstractInsertKeiroTable insertRku = new ConvRingiDao.InsertRkuForRTP();
        try {
            insertRtk.createStatement(con);
            insertRku.createStatement(con);
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql(" RNG_TEMPLATE_USER.RTP_SID,");
                sql.addSql(" RNG_TEMPLATE_USER.USR_SID,");
                sql.addSql(" RNG_TEMPLATE_USER.RTU_SORT,");
                sql.addSql(" RNG_TEMPLATE_USER.RTU_TYPE,");
                sql.addSql(" RNG_TEMPLATE_USER.RTU_AUID,");
                sql.addSql(" RNG_TEMPLATE_USER.RTU_ADATE,");
                sql.addSql(" RNG_TEMPLATE_USER.RTU_EUID,");
                sql.addSql(" RNG_TEMPLATE_USER.RTU_EDATE");
                sql.addSql(" from ");
                sql.addSql(" RNG_TEMPLATE_USER");
                sql.addSql(" ,CMN_USRM");
                sql.addSql(" where ");
                sql.addSql(" RNG_TEMPLATE_USER.USR_SID = CMN_USRM.USR_SID");
                sql.addSql(" and CMN_USRM.USR_JKBN=0");
                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int rtkSid = (int) saiban.getSaibanNumber(
                            "ringi", "rtkSid",
                            -1);
                    insertRtk.execute(rtkSid, rs);
                    insertRku.execute(rtkSid, rs);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            insertRtk.close();
            insertRku.close();
        }

    }
    /**
    *
    * <br>[機  能] 既存稟議テンプレートデータのコンバート
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param saiban 採番コントローラ
    * @throws SQLException SQL実行時例外
    */
    private void __convertRngTemplate(
            Connection con,
            MlCountMtController saiban) throws SQLException {
        //稟議テンプレートフォームデータ
        AbstractInsertKeiroTable insertRtf = new ConvRingiDao.InsertRtf();
        //稟議テンプレートデータ
        AbstractInsertKeiroTable updateRtf = new ConvRingiDao.UpdateRtp();

        try {
            insertRtf.createStatement(con);
            updateRtf.createStatement(con);
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   RTP_SID,");
                sql.addSql("   RTP_TYPE,");
                sql.addSql("   USR_SID,");
                sql.addSql("   RTP_TITLE,");
                sql.addSql("   RTP_RNG_TITLE,");
                sql.addSql("   RTP_CONTENT,");
                sql.addSql("   RTP_SORT,");
                sql.addSql("   RTP_AUID,");
                sql.addSql("   RTP_ADATE,");
                sql.addSql("   RTP_EUID,");
                sql.addSql("   RTP_EDATE,");
                sql.addSql("   RTC_SID,");
                sql.addSql("   RTP_VER, ");
                sql.addSql("   RTP_MAXVER_KBN, ");
                sql.addSql("   RTP_JKBN, ");
                sql.addSql("   RTP_IDFORMAT_SID, ");
                sql.addSql("   RTP_FORM, ");
                sql.addSql("   RCT_SID, ");
                sql.addSql("   RCT_USR_SID, ");
                sql.addSql("   RTP_BIKO, ");
                sql.addSql("   RTP_IDMANUAL ");
                sql.addSql(" from ");
                sql.addSql("   RNG_TEMPLATE");

                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    insertRtf.execute(0, rs);
                    updateRtf.execute(0, rs);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            insertRtf.close();
            updateRtf.close();
        }

    }
    /**
     *
     * <br>[機  能] 既存稟議経路テンプレートデータのコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convertRngChannelTemplate(Connection con,
            MlCountMtController saiban) throws SQLException {
        InsertRtkForRCT insertRtk = new ConvRingiDao.InsertRtkForRCT();
        AbstractInsertKeiroTable insertRku = new ConvRingiDao.InsertRkuForRCT();
        try {
            insertRtk.createStatement(con);
            insertRku.createStatement(con);

            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCT_SID,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.USR_SID,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCU_SORT,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCU_TYPE,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCU_AUID,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCU_ADATE,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCU_EUID,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCU_EDATE,");
                sql.addSql("   RNG_CHANNEL_TEMPLATE.USR_SID as RCT_USR_SID");
                sql.addSql(" from ");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER");
                sql.addSql("   ,RNG_CHANNEL_TEMPLATE");
                sql.addSql("   ,CMN_USRM");
                sql.addSql(" where ");
                sql.addSql("   RNG_CHANNEL_TEMPLATE_USER.RCT_SID = RNG_CHANNEL_TEMPLATE.RCT_SID");
                sql.addSql("   and RNG_CHANNEL_TEMPLATE_USER.USR_SID = CMN_USRM.USR_SID");
                sql.addSql("   and CMN_USRM.USR_JKBN=0");


                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int rtkSid = (int) saiban.getSaibanNumber(
                            "ringi", "rtkSid",
                            -1);
                    insertRtk.execute(rtkSid, rs);
                    insertRku.execute(rtkSid, rs);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            insertRtk.close();
            insertRku.close();
        }

    }
    /**
     *
     * <br>[機  能] 経路変換インサートクラス共通実装
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private abstract static class AbstractInsertKeiroTable  {
        /** プリペアドステートメント*/
        PreparedStatement pstmt__ = null;
        /** sqlバッファー*/
        SqlBuffer sql__ = new SqlBuffer();

        /**
         *
         * <br>[機  能] プリペアドステートメントの生成
         * <br>[解  説]
         * <br>[備  考]
         * @param con コネクション
         * @throws SQLException SQL実行時例外
         */
        public abstract void createStatement(Connection con) throws SQLException;
        /**
         *
         * <br>[機  能] ステートメントクエリの実行
         * <br>[解  説]
         * <br>[備  考]
         * @param sid SID
         * @param rs リザルトセット
         * @throws SQLException SQL実行時例外
         */
        public abstract void execute(int sid, ResultSet rs) throws SQLException;
        /**
         *
         * <br>[機  能] プリペアドステートメントの解放
         * <br>[解  説]
         * <br>[備  考]
         */
        public void close() {
            JDBCUtil.closeStatement(pstmt__);
        }
    }
    /**
     *
     * <br>[機  能] 経路変換インサートクラス
     * <br>[解  説] 経路ステップ
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class InsertRks extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" RNG_KEIRO_STEP(");
                sql.addSql("   RKS_SID,");
                sql.addSql("   RNG_SID,");
                sql.addSql("   RKS_SORT,");
                sql.addSql("   RKS_STATUS,");
                sql.addSql("   RTK_SID,");
                sql.addSql("   RKS_ROLL_TYPE,");
                sql.addSql("   RKS_RCVDATE,");
                sql.addSql("   RKS_CHKDATE,");
                sql.addSql("   RKS_AUID,");
                sql.addSql("   RKS_ADATE,");
                sql.addSql("   RKS_EUID,");
                sql.addSql("   RKS_EDATE,");
                sql.addSql("   RKS_BELONG_SID, ");
                sql.addSql("   RKS_KEIRO_KOETU, ");
                sql.addSql("   RKS_KOETU_SIZI,");
                sql.addSql("   RKS_ADDSTEP");
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
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt__ = con.prepareStatement(sql.toSqlString());
                sql__ = sql;
            } catch (SQLException e) {
                throw e;
            }
        }

        @Override
        public void execute(int rksSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();

            // 稟議状態: 決裁(完了済み) ＋ 経路状態: 未設定(最終確認者) → [経路状態: 確認中] へ変更する
            int rncStatus = rs.getInt("RNC_STATUS");
            if (rs.getInt("RNG_STATUS")  == 2   // 稟議 状態 決裁
             && rs.getInt("RNG_COMPFLG") == 1   // 完了フラグ 完了
             && rs.getInt("RNC_TYPE")    == 1   // 承認者種別 最終確認者
             && rncStatus == 0) {               // 稟議経路情報 状態 未設定
                rncStatus = 1; // 稟議経路情報 状態 確認中
            }
            if (rs.getInt("RNC_SKIPED") == 7) {
                rncStatus = rs.getInt("RNC_SKIPED");
            }

//               RKS_SID,
            sql__.addIntValue(rksSid);
//             RNG_SID,
            sql__.addIntValue(rs.getInt("RNG_SID"));
//             RKS_SORT,
            sql__.addIntValue(rs.getInt("RNC_SORT"));
//             RKS_STATUS,
            sql__.addIntValue(rncStatus);
//             RTK_SID,
            sql__.addIntValue(-1);
//             RKS_ROLL_TYPE,
            sql__.addIntValue(rs.getInt("RNC_TYPE"));
//             RKS_RCVDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_RCVDATE")));
//             RKS_CHKDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_CHKDATE")));
//             RKS_AUID,
            sql__.addIntValue(rs.getInt("RNC_AUID"));
//             RKS_ADATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_ADATE")));
//             RKS_EUID,
            sql__.addIntValue(rs.getInt("RNC_EUID"));
//             RKS_EDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_EDATE")));
//             RKS_BELONG_SID,
            sql__.addIntValue(-1);
//             RKS_KEIRO_KOETU,
            sql__.addIntValue(0);
//             RKS_KOETU_SIZI
            sql__.addIntValue(0);
//             RKS_ADDSTEP
         sql__.addIntValue(-1);

            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();

        }

    }
    /**
    *
    * <br>[機  能] 経路変換インサートクラス
    * <br>[解  説] 経路審議情報
    * <br>[備  考]
    *
    * @author JTS
    */
    private static class InsertRns extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" RNG_SINGI(");
                sql.addSql("   RKS_SID,");
                sql.addSql("   USR_SID,");
                sql.addSql("   RNG_SID,");
                sql.addSql("   USR_SID_KOETU,");
                sql.addSql("   USR_SID_DAIRI,");
                sql.addSql("   RSS_STATUS,");
                sql.addSql("   RSS_COMMENT,");
                sql.addSql("   RSS_CHKDATE,");
                sql.addSql("   RSS_AUID,");
                sql.addSql("   RSS_ADATE,");
                sql.addSql("   RSS_EUID,");
                sql.addSql("   RSS_EDATE");
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
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt__ = con.prepareStatement(sql.toSqlString());
                sql__ = sql;
            } catch (SQLException e) {
                throw e;
            }

        }

        @Override
        public void execute(int rksSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
            int rncStatus = rs.getInt("RNC_STATUS");
            if (rs.getInt("RNC_SKIPED") == 7) {
                rncStatus = rs.getInt("RNC_SKIPED");
            }

//               RKS_SID,
            sql__.addIntValue(rksSid);
//               USR_SID,
            sql__.addIntValue(rs.getInt("USR_SID"));
//               RNG_SID,
            sql__.addIntValue(rs.getInt("RNG_SID"));
//               USR_SID_KOETU,
            sql__.addIntValue(-1);
//               USR_SID_DAIRI,
            sql__.addIntValue(-1);
//               RSS_STATUS,
            sql__.addIntValue(rncStatus);
//               RSS_COMMENT,
            sql__.addStrValue(rs.getString("RNC_COMMENT"));
//               RSS_CHKDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_CHKDATE")));
//             RSS_AUID,
            sql__.addIntValue(rs.getInt("RNC_AUID"));
//             RKS_ADATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_ADATE")));
//             RKS_EUID,
            sql__.addIntValue(rs.getInt("RNC_EUID"));
//             RKS_EDATE,
            sql__.addDateValue(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_EDATE")));

            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();

        }

    }
    /**
    *
    * <br>[機  能] 経路変換インサートクラス
    * <br>[解  説] 経路ステップ選択情報
    * <br>[備  考]
    *
    * @author JTS
    */
    private static class InsertRss extends AbstractInsertKeiroTable {

        @Override
        public void createStatement(Connection con) throws SQLException {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" RNG_KEIROSTEP_SELECT(");
                sql.addSql("   RKS_SID,");
                sql.addSql("   USR_SID,");
                sql.addSql("   GRP_SID,");
                sql.addSql("   POS_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt__ = con.prepareStatement(sql.toSqlString());
                sql__ = sql;
            } catch (SQLException e) {
                throw e;
            }

        }

        @Override
        public void execute(int rksSid, ResultSet rs) throws SQLException {
            pstmt__.clearParameters();
            sql__.clearValue();
//               RKS_SID,
            sql__.addIntValue(rksSid);
//               USR_SID,
            sql__.addIntValue(rs.getInt("USR_SID"));
//               GRP_SID,
            sql__.addIntValue(-1);
//               POS_SID
            sql__.addIntValue(-1);

            log__.info(sql__.toLogString());
            sql__.setParameter(pstmt__);
            pstmt__.executeUpdate();
        }

    }
    /**
     *
     * <br>[機  能] 既存稟議審議データ、経路データのコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convertRngChannel(Connection con,
            MlCountMtController saiban) throws SQLException {
        AbstractInsertKeiroTable insertRks = new InsertRks();
        AbstractInsertKeiroTable insertRns = new InsertRns();
        AbstractInsertKeiroTable insertRss = new InsertRss();
        AbstractInsertKeiroTable updateRnb = new UpdateRnb();
        try {
            insertRks.createStatement(con);
            insertRns.createStatement(con);
            insertRss.createStatement(con);
            updateRnb.createStatement(con);
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {

                int max = __channelCount(con);

                for (int num = 0; num < max; num += 10000) {
                    log__.info("チャンネル進行度：" + num + "/" + max);
                    //SQL文
                    SqlBuffer sql = new SqlBuffer();
                    sql.addSql(" select ");
                    sql.addSql("   RNG_CHANNEL.RNG_SID as RNG_SID,");
                    sql.addSql("   RNG_CHANNEL.USR_SID as USR_SID,");
                    sql.addSql("   RNG_CHANNEL.RNC_SORT as RNC_SORT,");
                    sql.addSql("   RNG_CHANNEL.RNC_STATUS as RNC_STATUS,");
                    sql.addSql("   RNG_CHANNEL.RNC_COMMENT as RNC_COMMENT,");
                    sql.addSql("   RNG_CHANNEL.RNC_RCVDATE as RNC_RCVDATE,");
                    sql.addSql("   RNG_CHANNEL.RNC_CHKDATE as RNC_CHKDATE,");
                    sql.addSql("   RNG_CHANNEL.RNC_TYPE as RNC_TYPE,");
                    sql.addSql("   RNG_CHANNEL.RNC_AUID as RNC_AUID,");
                    sql.addSql("   RNG_CHANNEL.RNC_ADATE as RNC_ADATE,");
                    sql.addSql("   RNG_CHANNEL.RNC_EUID as RNC_EUID,");
                    sql.addSql("   RNG_CHANNEL.RNC_EDATE as RNC_EDATE,");
                    sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
                    sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
                    sql.addSql("   case  when exists(");
                    sql.addSql("       select ");
                    sql.addSql("         RNG_SID");
                    sql.addSql("       from RNG_CHANNEL AFTER ");
                    sql.addSql("       where RNG_CHANNEL.RNC_TYPE = 0 ");
                    sql.addSql("             and RNG_CHANNEL.RNC_STATUS = 0  ");
                    sql.addSql("             and AFTER.RNG_SID = RNG_CHANNEL.RNG_SID ");
                    sql.addSql("             and AFTER.RNC_SORT > RNG_CHANNEL.RNC_SORT ");
                    sql.addSql("             and AFTER.RNC_TYPE = 0");
                    sql.addSql("             and AFTER.RNC_STATUS <> 0");
                    sql.addSql("   ) then 7 else 0 end as RNC_SKIPED ");
                    sql.addSql(" from ");
                    sql.addSql("   RNG_CHANNEL");
                    sql.addSql(" left join ");
                    sql.addSql("   RNG_RNDATA ");
                    sql.addSql(" on RNG_CHANNEL.RNG_SID = RNG_RNDATA.RNG_SID ");
                    sql.addSql(" order by ");
                    sql.addSql("   RNG_CHANNEL.RNG_SID asc,");
                    sql.addSql("   RNG_CHANNEL.USR_SID asc");
                    sql.setPagingValue(num, 10000);

                    pstmt = con.prepareStatement(sql.toSqlString());
                    log__.info(sql.toLogString());
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        int rksSid = (int) saiban.getSaibanNumber(
                                "ringi", "rngkeirostep", -1);
                        insertRks.execute(rksSid, rs);
                        insertRns.execute(rksSid, rs);
                        insertRss.execute(rksSid, rs);
                        updateRnb.execute(rksSid, rs);
                    }

                    rs = null;

                    if (num + 10000 >= max) {
                        log__.info("チャンネル進行度：" + max + "/" + max);
                    }
                }

            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            insertRks.close();
            insertRns.close();
            insertRss.close();
            updateRnb.close();
        }
    }

    /**
     * <p>経路データの件数を取得する
     * @param con コネクション
     * @return ArrayList in CMN_LOGModel
     * @throws SQLException SQL実行例外
     */
    private int __channelCount(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 0;

        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select ");
        sql.addSql("   count(*) as CNT");
        sql.addSql(" from ");
        sql.addSql("   RNG_CHANNEL");

        pstmt = con.prepareStatement(sql.toSqlString());
        log__.info(sql.toLogString());
        rs = pstmt.executeQuery();
        if  (rs.next()) {
            ret = rs.getInt("CNT");
        }

        return ret;
    }


    /**
     *
     * <br>[機  能] 既存稟議データのコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convertRngRndata(Connection con) throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;
        sql = new SqlBuffer();
        sql.addSql(" insert ");
        sql.addSql(" into ");
        sql.addSql(" RNG_FORMDATA(");
        sql.addSql("   RNG_SID,");
        sql.addSql("   RFD_SID,");
        sql.addSql("   RFD_ROWNO,");
        sql.addSql("   RFD_ID,");
        sql.addSql("   RFD_VALUE,");
        sql.addSql("   RFD_AUID,");
        sql.addSql("   RFD_ADATE,");
        sql.addSql("   RFD_EUID,");
        sql.addSql("   RFD_EDATE");
        sql.addSql(" )");
        sql.addSql(" select ");
        sql.addSql("   RNG_SID,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   '汎用稟議＿内容',");
        sql.addSql("   RNG_CONTENT,");
        sql.addSql("   RNG_AUID,");
        sql.addSql("   RNG_ADATE,");
        sql.addSql("   RNG_EUID,");
        sql.addSql("   RNG_EDATE");
        sql.addSql(" from RNG_RNDATA ");
        sqlList.add(sql);
        __executeQuery(con, sqlList);

    }
    /**
     * <br>[機  能] クエリの実行
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sqlList 実行クエリリスト
     * @throws SQLException SQL実行例外
     */
    private void __executeQuery(Connection con,
            List<SqlBuffer> sqlList) throws SQLException {

        PreparedStatement pstmt = null;

        try {
            //SQL生成
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
     *
     * <br>[機  能] テーブル定義の変更を行う
     * <br>[解  説] フィールド追加などの追加系の処理を行う
     * <br>[備  考]
     * @param saiban 採番コントローラ
     * @return 実行クエリリスト
     */
    private ArrayList<SqlBuffer> __updateTableTeigi(MlCountMtController saiban) {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

        // 稟議 管理者設定稟議申請IDフィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF ");
        sql.addSql(" add");
        sql.addSql("   RAR_RNGID integer not null default 2;");
        sqlList.add(sql);

        // 稟議 管理者設定デフォルトSIDフィールド
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF");
        sql.addSql(" add");
        sql.addSql("   RAR_RNGID_DEF_SID integer;");
        sqlList.add(sql);

        // 稟議 ショートメール通知設定区分（後閲）
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF");
        sql.addSql(" add");
        sql.addSql("   RAR_SML_KOETU_KBN integer;");
        sqlList.add(sql);
        // 稟議 ショートメール通知設定区分（受信）
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF");
        sql.addSql(" add");
        sql.addSql("   RAR_SML_JUSIN_KBN integer;");
        sqlList.add(sql);
        // 稟議 ID重複区分
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF");
        sql.addSql(" add");
        sql.addSql("   RAR_OVERLAP integer not null default 0;");
        sqlList.add(sql);

        // 稟議 ショートメール通知設定区分（後閲）
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_UCONF");
        sql.addSql(" add");
        sql.addSql("   RUR_SML_KOETU integer not null default 0;");
        sqlList.add(sql);
        // 稟議 ショートメール通知設定区分（受信）
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_UCONF");
        sql.addSql(" add");
        sql.addSql("   RUR_SML_JUSIN integer not null default 0;");
        sqlList.add(sql);

        // 稟議 申請ID
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_RNDATA ");
        sql.addSql(" add");
        sql.addSql("   RNG_ID varchar(120);");
        sqlList.add(sql);

        // 稟議 テンプレートSID
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_RNDATA ");
        sql.addSql(" add");
        sql.addSql("   RTP_SID integer  not null default 0;");
        sqlList.add(sql);

        // 稟議 テンプレートバージョン
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_RNDATA ");
        sql.addSql(" add");
        sql.addSql("   RTP_VER integer  not null default 0;");
        sqlList.add(sql);
        //稟議 代理人ユーザテーブル
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_DAIRI_USER ");
        sql.addSql(" ( ");
          sql.addSql(" USR_SID integer not null, ");
          sql.addSql(" USR_SID_DAIRI integer not null, ");
          sql.addSql(" RDU_START timestamp not null, ");
          sql.addSql(" RDU_END timestamp , ");
          sql.addSql(" primary key (USR_SID, USR_SID_DAIRI) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 経路ステップ
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_KEIRO_STEP ");
        sql.addSql(" ( ");
        sql.addSql("   RKS_SID       integer     not null, ");
        sql.addSql("   RNG_SID       integer     not null, ");
        sql.addSql("   RKS_SORT      integer     not null, ");
        sql.addSql("   RKS_STATUS    integer     not null, ");
        sql.addSql("   RTK_SID       integer     not null, ");
        sql.addSql("   RKS_ROLL_TYPE integer     not null, ");
        sql.addSql("   RKS_RCVDATE   timestamp , ");
        sql.addSql("   RKS_CHKDATE   timestamp , ");
        sql.addSql("   RKS_AUID      integer     not null, ");
        sql.addSql("   RKS_ADATE     timestamp   not null, ");
        sql.addSql("   RKS_EUID      integer     not null, ");
        sql.addSql("   RKS_EDATE     timestamp   not null, ");
        sql.addSql("   RKS_BELONG_SID integer    not null, ");
        sql.addSql("   RKS_KEIRO_KOETU integer   not null, ");
        sql.addSql("   RKS_KOETU_SIZI integer    not null, ");
        sql.addSql("   RKS_ADDSTEP integer    not null, ");
        sql.addSql("   primary key   (RKS_SID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 審議情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_SINGI ");
        sql.addSql(" ( ");
        sql.addSql("   RKS_SID        integer        not null, ");
        sql.addSql("   USR_SID        integer        not null, ");
        sql.addSql("   RNG_SID        integer        not null, ");
        sql.addSql("   USR_SID_KOETU  integer , ");
        sql.addSql("   USR_SID_DAIRI  integer , ");
        sql.addSql("   RSS_STATUS     integer        not null, ");
        sql.addSql("   RSS_COMMENT    varchar(300) , ");
        sql.addSql("   RSS_CHKDATE    timestamp , ");
        sql.addSql("   RSS_AUID       integer        not null, ");
        sql.addSql("   RSS_ADATE      timestamp      not null, ");
        sql.addSql("   RSS_EUID       integer        not null, ");
        sql.addSql("   RSS_EDATE      timestamp      not null, ");
        sql.addSql("   primary key    (RKS_SID, USR_SID) ");
        sql.addSql(" );         ");
        sqlList.add(sql);

        //稟議 フォーム入力値情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_FORMDATA ");
        sql.addSql(" ( ");
        sql.addSql("   RNG_SID integer not null, ");
        sql.addSql("   RFD_SID integer not null, ");
        sql.addSql("   RFD_ROWNO integer not null, ");
        sql.addSql("   RFD_ID varchar(100), ");
        sql.addSql("   RFD_VALUE text , ");
        sql.addSql("   RFD_AUID integer not null, ");
        sql.addSql("   RFD_ADATE timestamp not null, ");
        sql.addSql("   RFD_EUID integer not null, ");
        sql.addSql("   RFD_EDATE timestamp not null ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 経路テンプレートステップ情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATE_KEIRO ");
        sql.addSql(" ( ");
        sql.addSql("   RTK_SID           integer     not null, ");
        sql.addSql("   RCT_SID           integer     not null, ");
        sql.addSql("   USR_SID           integer     not null, ");
        sql.addSql("   RTP_SID           integer     not null, ");
        sql.addSql("   RTP_VER           integer     not null, ");
        sql.addSql("   RTK_SORT          integer     not null, ");
        sql.addSql("   RTK_LEVEL         integer     not null, ");
        sql.addSql("   RTK_NAME          varchar(10) not null, ");
        sql.addSql("   RTK_TYPE          integer     not null, ");
        sql.addSql("   RTK_ROLL_TYPE     integer     not null, ");
        sql.addSql("   RTK_OUTCONDITION  integer     not null, ");
        sql.addSql("   RTK_OUTCOND_BORDER integer    not null, ");
        sql.addSql("   RTK_NOUSER        integer     not null, ");
        sql.addSql("   RTK_ADDSTEP       integer     not null, ");
        sql.addSql("   RTK_KEIRO_SKIP    integer     not null, ");
        sql.addSql("   RTK_KEIRO_KOETU   integer     not null, ");
        sql.addSql("   RTK_MULTISEL_FLG  integer     not null, ");
        sql.addSql("   RTK_BOSSSTEP_CNT  integer     not null, ");
        sql.addSql("   RTK_BOSSSTEP_MASTCNT integer  not null, ");
        sql.addSql("   RTK_AUID          integer     not null, ");
        sql.addSql("   RTK_ADATE         timestamp   not null, ");
        sql.addSql("   RTK_EUID          integer     not null, ");
        sql.addSql("   RTK_EDATE         timestamp   not null, ");
        sql.addSql("   RTK_JKBN          integer     not null, ");
        sql.addSql("   RTK_KOETU_SIZI    integer     not null, ");
        sql.addSql("   RTK_OWNSINGI      integer     not null, ");
        sql.addSql("       primary key    (RTK_SID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 経路テンプレートステップユーザ情報
       sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATE_KEIRO_USER ");
        sql.addSql(" ( ");
        sql.addSql("   RTK_SID integer not null, ");
        sql.addSql("   USR_SID integer not null, ");
        sql.addSql("   GRP_SID integer not null, ");
        sql.addSql("   POS_SID integer not null, ");
        sql.addSql("   RKU_TYPE integer not null, ");
        sql.addSql("   RKU_AUID integer not null, ");
        sql.addSql("   RKU_ADATE timestamp not null, ");
        sql.addSql("   RKU_EUID integer not null, ");
        sql.addSql("   RKU_EDATE timestamp not null, ");
        sql.addSql("   primary key   (RTK_SID, USR_SID, GRP_SID, POS_SID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 経路テンプレート進行条件情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATE_KEIRO_CONDITION ");
        sql.addSql(" ( ");
        sql.addSql("   RTK_SID integer not null, ");
        sql.addSql("   RKC_IFTEMPLATE integer not null, ");
        sql.addSql("   RKC_IFGROUP integer not null, ");
        sql.addSql("   RKC_IFPOS integer not null, ");
        sql.addSql("   RKC_IFFORM varchar(20) , ");
        sql.addSql("   RKC_IFFORM_OPR varchar(10) , ");
        sql.addSql("   RKC_IFFORM_VALUE varchar(20), ");
        sql.addSql("   RKC_ORID integer not null ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 経路テンプレート 論理削除フラグ 追加
        sql = new SqlBuffer();
        sql.addSql("alter table RNG_CHANNEL_TEMPLATE add RCT_JKBN integer not null default 0;");
        sqlList.add(sql);

        //稟議 テンプレートカテゴリアクセス権限情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATECATEGORY_ACCESS ");
        sql.addSql(" ( ");
        sql.addSql("   RTC_SID integer not null, ");
        sql.addSql("   USR_SID integer not null, ");
        sql.addSql("   GRP_SID integer not null, ");
        sql.addSql("   RTA_KBN integer not null, ");
        sql.addSql("   primary key (RTC_SID, USR_SID, GRP_SID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 経路ステップ選択ユーザ情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_KEIROSTEP_SELECT ");
        sql.addSql(" ( ");
        sql.addSql("   RKS_SID integer not null, ");
        sql.addSql("   USR_SID integer not null, ");
        sql.addSql("   GRP_SID integer not null, ");
        sql.addSql("   POS_SID integer not null, ");
        sql.addSql("   primary key   (RKS_SID, USR_SID, GRP_SID, POS_SID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議 申請ID情報
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_ID ");
        sql.addSql(" ( ");
        sql.addSql("   RID_SID integer not null, ");
        sql.addSql("   RID_FORMAT varchar(120) not null, ");
        sql.addSql("   RID_INIT integer not null, ");
        sql.addSql("   RID_MANUAL integer not null, ");
        sql.addSql("   RID_RESET integer not null, ");
        sql.addSql("   RID_ZERO integer not null, ");
        sql.addSql("   RID_TITLE varchar(50) not null, ");
        sql.addSql("   primary key   (RID_SID) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into RNG_ID ");
        sql.addSql(" ( ");
        sql.addSql("   RID_SID,");
        sql.addSql("   RID_FORMAT,");
        sql.addSql("   RID_INIT,");
        sql.addSql("   RID_MANUAL,");
        sql.addSql("   RID_RESET,");
        sql.addSql("   RID_ZERO,");
        sql.addSql("   RID_TITLE");
        sql.addSql(" ) values (");
        sql.addSql("   0,");
        sql.addSql("   '${NO}',");
        sql.addSql("   0,");
        sql.addSql("   2,");
        sql.addSql("   0,");
        sql.addSql("   0,");
        sql.addSql("   '汎用申請ID'");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //稟議テンプレート情報 フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_VER integer  not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_MAXVER_KBN integer  not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_JKBN integer  not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_IDFORMAT_SID integer  not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_FORM text;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RCT_SID integer  not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RCT_USR_SID integer  not null default -1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_BIKO varchar(1000);");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_IDMANUAL integer  not null default 1;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" add");
        sql.addSql("   RTP_SPEC_VER integer  not null default 0;");
        sqlList.add(sql);

        //稟議テンプレート情報 プライマリーキー修正
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table RNG_TEMPLATE drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table RNG_TEMPLATE drop CONSTRAINT rng_template_pkey;");
        }
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE add PRIMARY KEY(RTP_SID, RTP_VER);");
        sqlList.add(sql);

        //稟議テンプレート添付ファイル情報 フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE_BIN ");
        sql.addSql(" add");
        sql.addSql("   RTP_VER integer  not null default 0;");
        sqlList.add(sql);
        //稟議テンプレート情報 プライマリーキー修正
        sql = new SqlBuffer();
        if (h2db) {
            sql.addSql(" alter table RNG_TEMPLATE_BIN drop PRIMARY KEY;");
        } else {
            //Postgresqlの場合
            sql.addSql(" alter table RNG_TEMPLATE_BIN drop CONSTRAINT rng_template_bin_pkey;");
        }
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE_BIN add PRIMARY KEY(RTP_SID, BIN_SID, RTP_VER);");
        sqlList.add(sql);
        //テンプレートフォーム情報テーブル
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATE_FORM");
        sql.addSql(" (");
        sql.addSql("   RTP_SID     integer        not null,");
        sql.addSql("   RTP_VER     integer        not null,");
        sql.addSql("   RTF_SID     integer        not null,");
        sql.addSql("   RTF_ID      varchar(100),");
        sql.addSql("   RTF_TITLE   varchar(100),");
        sql.addSql("   RTF_REQUIRE integer        not null,");
        sql.addSql("   RTF_TYPE    integer        not null,");
        sql.addSql("   RTF_BODY    text,");
        sql.addSql("   primary key (RTP_SID, RTP_VER, RTF_SID)");
        sql.addSql(" ) ;        ");
        sqlList.add(sql);

         //稟議テンプレートカテゴリー 使用制限と使用タイプを作成
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE_CATEGORY");
        sql.addSql("  add RTC_USE_LIMIT integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE_CATEGORY");
        sql.addSql("  add RTC_LIMIT_TYPE integer not null default 0;");
        sqlList.add(sql);

        //稟議テンプレートカテゴリ使用制限テーブルの追加
        sql = new SqlBuffer();
        sql.addSql(" CREATE TABLE RNG_TEMPLATECATEGORY_USE");
        sql.addSql("(    RTC_SID                     INTEGER NOT NULL,");
        sql.addSql("     USR_SID                     INTEGER NOT NULL,");
        sql.addSql("     GRP_SID                     INTEGER NOT NULL,");
        sql.addSql("     PRIMARY KEY (RTC_SID, USR_SID, GRP_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //稟議テンプレートカテゴリ管理者テーブルの追加
        sql = new SqlBuffer();
        sql.addSql(" CREATE TABLE RNG_TEMPLATECATEGORY_ADM");
        sql.addSql("(    RTC_SID                     INTEGER NOT NULL,");
        sql.addSql("     USR_SID                     INTEGER NOT NULL,");
        sql.addSql("     GRP_SID                     INTEGER NOT NULL,");
        sql.addSql("     PRIMARY KEY (RTC_SID, USR_SID, GRP_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //稟議添付ファイル情報 経路ステップSIDの追加
       sql = new SqlBuffer();
       sql.addSql(" alter table RNG_BIN");
       sql.addSql("  add RKS_SID integer not null default 0;");
       sqlList.add(sql);

       //強制完了稟議のステータスを変更（強制完了ステータスが定義されたため）
       sql = new SqlBuffer();
       sql.addSql(" update RNG_RNDATA set ");
       sql.addSql("  RNG_STATUS=4");
       sql.addSql(" where  RNG_STATUS=1 and  RNG_COMPFLG=1");
       sqlList.add(sql);

       //インデックスを設定
       sql = new SqlBuffer();
       sql.addSql(" create index RNG_TEMPLATE_KEIRO1 on RNG_TEMPLATE_KEIRO(RCT_SID); ");
       sql.addSql(" create index RNG_TEMPLATE_KEIRO2 on RNG_TEMPLATE_KEIRO(RTP_SID, RTP_VER); ");
       sql.addSql(" create index RNG_FORMDATA1 on RNG_FORMDATA(RNG_SID); ");
       sql.addSql(" create index RNG_KEIRO_STEP1 on RNG_KEIRO_STEP(RNG_SID); ");
       sql.addSql(" create index RNG_SINGI1 on RNG_SINGI(RNG_SID); ");
       sqlList.add(sql);

       return sqlList;
    }
    /**
     *
     * <br>[機  能] 既存テーブル定義から使用しなくなったテーブル定義を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行クエリリスト
     */
    private ArrayList<SqlBuffer> __dropOldTableTeigi() {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;
        //稟議テンプレート情報 内容を削除
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE ");
        sql.addSql(" drop");
        sql.addSql("   RTP_CONTENT;");
        sqlList.add(sql);
        //稟議情報 内容を削除
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_RNDATA ");
        sql.addSql(" drop");
        sql.addSql("   RNG_CONTENT;");
        sqlList.add(sql);

        //稟議 旧テンプレートユーザ情報を削除
        sql = new SqlBuffer();
        sql.addSql("drop table RNG_TEMPLATE_USER;");
        sqlList.add(sql);

        //稟議 旧経路テンプレートユーザ情報を削除
        sql = new SqlBuffer();
        sql.addSql("drop table RNG_CHANNEL_TEMPLATE_USER;");
        sqlList.add(sql);

        //稟議 旧経路ユーザ情報を削除
        sql = new SqlBuffer();
        sql.addSql("drop table RNG_CHANNEL;");
        sqlList.add(sql);




        return sqlList;
    }



}
