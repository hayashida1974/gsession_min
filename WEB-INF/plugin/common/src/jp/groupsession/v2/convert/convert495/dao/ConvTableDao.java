package jp.groupsession.v2.convert.convert495.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.5へコンバートする際に使用する
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
        //今日は何の日の削除
        __removeWhatDayData();
        //祝日テンプレート2020対応
        __changeSyukujituTemplate();

        //稟議テンプレート汎用稟議テンプレートの不正な参照の修正
        __repareRtp();

        //掲示板 投稿情報 親投稿フラグの追加
        __alterTableBbsWrite();

        //スケジュール （施設予約不具合で作られた不正なスケジュールを削除）
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" delete from SCH_DATA where SCD_USR_KBN = 1 and SCD_USR_SID = -1 ");
        __exeQuery(sql);

        log__.debug("-- DBコンバート終了 --");
    }
    /**
     *
     * <br>[機  能] 掲示板データメースコンバート
     * <br>[解  説] 掲示板 投稿情報 親投稿フラグの追加
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __alterTableBbsWrite() throws SQLException {
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" alter table BBS_WRITE_INF add column ");
        sql.addSql(" BWI_PARENT_FLG integer not null default 0;");
        __exeQuery(sql);

        sql = new SqlBuffer();
        sql.addSql(" update ");
        sql.addSql("     BBS_WRITE_INF ");
        sql.addSql(" set ");
        sql.addSql("     BWI_PARENT_FLG=1 ");
        sql.addSql(" where ");
        sql.addSql(" BWI_SID in ( ");
        sql.addSql("     select MIN.MIN_BWI ");
        sql.addSql("         from ( ");
        sql.addSql("         select ");
        sql.addSql("             BTI_SID, ");
        sql.addSql("             min(BWI_SID) as MIN_BWI  ");
        sql.addSql("         from ");
        sql.addSql("             BBS_WRITE_INF ");
        sql.addSql("         group by BTI_SID ");
        sql.addSql("     ) MIN ");
        sql.addSql(" ) ");
        __exeQuery(sql);
    }


    /**
     *
     * <br>[機  能] 稟議テンプレート汎用稟議テンプレートの不正な参照の修正を行う
     * <br>[解  説] DB[稟議情報]の稟議テンプレートSID=0 テンプレートバージョンが0以外のデータのテンプレートバージョンを０にする。
     * <br>         DB[稟議情報]の稟議テンプレートSID=0 以下のデータのテンプレートSIDを０にする。
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __repareRtp() throws SQLException {
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" update RNG_RNDATA ");
        sql.addSql(" set RTP_SID = 0 ");
        sql.addSql(" where RTP_SID < 0 ");
        __exeQuery(sql);

        sql = new SqlBuffer();
        sql.addSql(" update RNG_RNDATA ");
        sql.addSql(" set RTP_VER = 0 ");
        sql.addSql(" where RTP_SID = 0 and RTP_VER <> 0");
        __exeQuery(sql);
    }

    /**
     *
     * <br>[機  能] 今日は何の日関連データ削除
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __removeWhatDayData() throws SQLException {
       //DB[メイン表示設定]から今日は何の日の表示設定データを削除
       //（DB[メイン表示設定.画面項目区分]=5 今日は何の日の表示設定）
       SqlBuffer sql = new SqlBuffer();
       sql.addSql(" delete from CMN_MDISP where MDP_PID = '5'");
       __exeQuery(sql);

       //  削除により発生するDB[メイン画面位置設定.表示順]の飛び番を埋める
       __updateMscOrder();

       //  DB[メイン画面位置設定]から今日は何の日の表示設定データを削除
       sql = new SqlBuffer();
       sql.addSql("delete from CMN_MAINSCREEN_CONF where MSC_ID = ?");
       sql.addStrValue("anni");
       __exeQuery(sql);

    }
    /**
     *
     * <br>[機  能] 今日は何の日分のDB[メイン画面位置設定.表示順]の飛び番を埋める
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __updateMscOrder() throws SQLException {
        // TODO 自動生成されたメソッド・スタブ
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select USR_SID, MSC_ORDER,MSC_POSITION  ");
            sql.addSql(" from CMN_MAINSCREEN_CONF");
            sql.addSql(" where MSC_ID=?");
            sql.addStrValue("anni");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sql = new SqlBuffer();
                sql.addSql(" update CMN_MAINSCREEN_CONF set MSC_ORDER = MSC_ORDER -1 ");
                sql.addSql(" where USR_SID=? ");
                sql.addSql(" and MSC_POSITION =?");
                sql.addSql(" and MSC_ORDER > ?");
                sql.addIntValue(rs.getInt("USR_SID"));
                sql.addIntValue(rs.getInt("MSC_POSITION"));
                sql.addIntValue(rs.getInt("MSC_ORDER"));
                __exeQuery(sql);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @throws SQLException SQL実行例外
     */
    private void __exeQuery(SqlBuffer sql) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {


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
     *
     * <br>[機  能] 祝日テンプレートを2020年の祝日に合わせる
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __changeSyukujituTemplate() throws SQLException {
        //以下を満たすの休日テンプレートデータの日付を2月23日に変更する
        //・12月23日に登録されている
        //・休日テンプレート名が「天皇誕生日」
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" update CMN_HOLIDAY_TEMPLATE set");
        sql.addSql(" HLT_DATE_MONTH = 2,");
        sql.addSql(" HLT_DATE_DAY = 23");
        sql.addSql(" where HLT_DATE_MONTH = 12");
        sql.addSql(" and HLT_DATE_DAY = 23");
        sql.addSql(" and HLT_EXFLG = 0");
        sql.addSql(" and (HLT_NAME = ?");
        sql.addSql("     or HLT_NAME = ?)");
        sql.addStrValue("天皇誕生日 ");
        sql.addStrValue("天皇誕生日");
        __exeQuery(sql);

        //以下を満たす休日テンプレートデータの休日テンプレート名を「スポーツの日」に変更する
        //・10月の第二月曜日に登録されている
        //・休日テンプレート名が「体育の日」
        sql = new SqlBuffer();
        sql.addSql(" update CMN_HOLIDAY_TEMPLATE set");
        sql.addSql(" HLT_NAME = ?");
        sql.addSql(" where");
        sql.addSql(" HLT_EX_MONTH = 10");
        sql.addSql(" and HLT_EX_WEEK_MONTH = 2");
        sql.addSql(" and HLT_EX_DAY_WEEK = 1");
        sql.addSql(" and HLT_EXFLG = 1");
        sql.addSql(" and (HLT_NAME = ?");
        sql.addSql("     or HLT_NAME = ?)");
        sql.addStrValue("スポーツの日");
        sql.addStrValue("体育の日 ");
        sql.addStrValue("体育の日");
        __exeQuery(sql);

    }



}
