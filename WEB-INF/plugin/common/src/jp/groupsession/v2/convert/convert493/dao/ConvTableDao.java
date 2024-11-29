package jp.groupsession.v2.convert.convert493.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.3へコンバートする際に使用する
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

        //ひらがなで取り込まれてしまったUSI_MEI_KN をカタカナに変換
        __updateMeiKn();

        //wmlフィールド削除
        __deleteWmlFilter();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
    *
    * <br>[機  能] 取り残されたwebmailのフィルタフィールドを
    * <br>[解  説]
    * <br>[備  考]
    * @throws SQLException SQL実行時例外
    */
   private void __deleteWmlFilter() throws SQLException {
       Connection con = null;
       con = getCon();

       boolean hit = __selectWMlColumn(con);
       if (hit) {
           PreparedStatement pstmt = null;
           try {
               //SQL生成
               SqlBuffer sql = new SqlBuffer();
               //コンバート時にWmlFilterが0件の状態の場合に不正なフィールドが残ってしまう
               sql.addSql("ALTER TABLE WML_FILTER DROP COLUMN WFT_ACTION_DUST;");
               pstmt = con.prepareStatement(sql.toSqlString());
               pstmt.executeUpdate();
               log__.info(sql.toLogString());
           } catch (SQLException e) {
               throw e;
           } finally {
               JDBCUtil.closeStatement(pstmt);
           }
       }
   }

   /**
    * <br>[機  能] レコード件数を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @return 件数
    * @throws SQLException SQL実行例外
    */
   private boolean __selectWMlColumn(Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       boolean hit = false;
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql("   *");
           sql.addSql(" from ");
           sql.addSql("   WML_FILTER");

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           ResultSetMetaData meta = rs.getMetaData();

           for (int idx = 1; idx <= meta.getColumnCount(); idx++) {
               String name = meta.getColumnName(idx);
               if (name.equals("WFT_ACTION_DUST") || name.equals("wft_action_dust")) {
                   hit = true;
                   break;
               }
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       return hit;
   }

    /**
     *
     * <br>[機  能] DBのユーザ情報名カナに保管されていたひらがなをカタカナに変換
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __updateMeiKn() throws SQLException {
        Connection con = null;
        con = getCon();

        HashMap<Integer, String> userMeiKnMap = __selectUserMeiKn(con);
        HashMap<Integer, String> updateMeiKnMap = new HashMap<Integer, String>();

        for (Entry<Integer, String> entry : userMeiKnMap.entrySet()) {
            String str = entry.getValue();
            String str2 = __zenkakuHiraganaToZenkakuKatakana(str);
            if (str.equals(str2)) {
                continue;
            }
            updateMeiKnMap.put(entry.getKey(), str2);
        }
        PreparedStatement pstmt = null;

        try {

            //SQL生成
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   USI_MEI_KN=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            for (Entry<Integer, String> entry : updateMeiKnMap.entrySet()) {
                sql.clearValue();
                pstmt.clearParameters();
                sql.addStrValue(entry.getValue());
                sql.addIntValue(entry.getKey());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
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
     * <br>[機  能] 全角ひらがなをカタカナに変換
     * <br>[解  説]
     * <br>[備  考]
     * @param s 変換元文字列
     * @return カタカナに変換した文字列
     */
    public static String __zenkakuHiraganaToZenkakuKatakana(String s) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < sb.length(); i++) {
          char c = sb.charAt(i);
          if (c >= 'ぁ' && c <= 'ん') {
            sb.setCharAt(i, (char) (c - 'ぁ' + 'ァ'));
          }
        }
        return sb.toString();
    }
    /**
     * <br>[機  能] レコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    private HashMap<Integer, String> __selectUserMeiKn(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashMap<Integer, String> ret = new HashMap<Integer, String>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   USI_MEI_KN");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), rs.getString("USI_MEI_KN"));
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

        // チューニング
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {
        }

        //DB > SML_HINA.SHN_BODYの文字数を2000 → 5000に変更する
        sql = new SqlBuffer();
        sql.addSql("ALTER TABLE SML_HINA ALTER COLUMN SHN_BODY TYPE varchar(5000);");
        sqlList.add(sql);

        //DB > チャット一覧タブ選択情報追加
        sql = new SqlBuffer();
        sql.addSql("alter table CHT_PRI_CONF add CPC_SEL_TAB integer not null default 0;");
        sqlList.add(sql);

        //DB > チャット添付SID データ型変更
        sql = new SqlBuffer();
        sql.addSql("alter table CHT_USER_DATA alter column BIN_SID type bigint;");
        sqlList.add(sql);

        //DB > チャット添付SID データ型変更
        sql = new SqlBuffer();
        sql.addSql("alter table CHT_GROUP_DATA alter column BIN_SID type bigint;");
        sqlList.add(sql);

        return sqlList;
    }
}
