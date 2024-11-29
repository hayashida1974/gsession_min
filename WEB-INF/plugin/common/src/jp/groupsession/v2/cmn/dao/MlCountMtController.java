package jp.groupsession.v2.cmn.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GsResourceBundle;

/**
 * <br>[機 能] 採番処理を行う
 * <br>[解 説] 指定した番号の取得、および採番マスタのアップデートを行う
 * <br>[備 考]
 *
 * @author JTS
 */
public class MlCountMtController {

    /** データソース */
    private DataSource ds__ = null;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MlCountMtController.class);

    /**
     * <br>
     * [機 能] 唯一のインスタンス <br>
     * [解 説] <br>
     * [備 考]
     */

    /** 採番処理実装クラスのインスタンス */
    private final INumberingController controller__ = __getController();

    /**
     * @return ds__ を戻す。
     */
    public DataSource getDs() {
        return ds__;
    }

    /**
     * @param ds
     *            ds__ をセット。
     */
    public void setDs(DataSource ds) {
        ds__ = ds;
    }


    /**
     * <br>
     * [機 能] IDを取得し採番マスタをアップデート <br>
     * [解 説] 本メソッドは数値型の採番を行う <br>
     * [備 考] 本メソッドを実行するユーザのSIDが 不確定(新規登録処理等)の場合は 引数:ユーザSIDを-1で渡してください
     *
     * @param sid
     *            採番SID
     * @param sids
     *            採番SIDサブ
     * @param uid
     *            ユーザSID
     * @exception SQLException
     *                DB実行例外の場合にスローする
     * @return 採番SID
     */
    public synchronized long getSaibanNumber(String sid, String sids, int uid)
            throws SQLException {

        Connection con = null;
        long bgSibanNum = 0;
        try {
            con = __getConnection();
            bgSibanNum = controller__.getSaibanNumber(con, sid, sids, uid);
        } catch (SQLException e) {
            log__.error(e);
            throw e;
        } finally {
            JDBCUtil.closeConnection(con);
        }

        return bgSibanNum;
    }
    /**
     * <br>
     * [機 能] IDを取得し採番マスタをアップデート <br>
     * [解 説] 本メソッドは数値型の採番を行う <br>
     * [備 考] 本メソッドを実行するユーザのSIDが 不確定(新規登録処理等)の場合は 引数:ユーザSIDを-1で渡してください
     *
     * @param sid
     *            採番SID
     * @param sids
     *            採番SIDサブ
     * @param uid
     *            ユーザSID
     * @param count
     *            発行するSID数
     * @exception SQLException
     *                DB実行例外の場合にスローする
     * @return 採番SID LIST
     */
    public synchronized List<Long> getSaibanNumbers(String sid, String sids, int uid, int count)
            throws SQLException {

        Connection con = null;
        try {
            con = __getConnection();
            con.setAutoCommit(false);

            return controller__.getSaibanNumbers(con, sid, sids, uid, count);

        } catch (SQLException e) {
            log__.error(e);
            throw e;
        } finally {
            JDBCUtil.closeConnection(con);
        }
    }

    /**
     * <br>
     * [機 能] IDを取得し採番マスタをアップデート(コンソールアプリケーション用) <br>
     * [解 説] 本メソッドは数値型の採番を行い、コミットしません。 そのため呼び出し元でコミットを実行してください。
     * コンソールアプリケーション等の排他制御不要の場合に使用してください。 <br>
     * [備 考] 本メソッドを実行するユーザのSIDが 不確定(新規登録処理等)の場合は 引数:ユーザSIDを-1で渡してください
     *
     * @param con
     *            コネクション
     * @param sid
     *            採番SID
     * @param sids
     *            採番SIDサブ
     * @param uid
     *            ユーザSID
     * @exception SQLException
     *                DB実行例外の場合にスローする
     * @return 採番SID
     */
    public synchronized long getSaibanNumberNotCommit(Connection con, String sid,
            String sids, int uid) throws SQLException {

        return controller__.getSaibanNumberNotCommit(con, sid, sids, uid);
    }

    /**
     * <br>[機  能] 採番処理実装クラスのインスタンスを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 採番処理実装クラスのインスタンス
     */
    private static INumberingController __getController() {
        String strController = GsResourceBundle.getString("INumberingController");
        log__.info("INumberingController is " + strController);
        INumberingController controller = null;

        if (strController != null) {
            try {
                @SuppressWarnings("unchecked")
                Class<INumberingController> cls
                    = (Class<INumberingController>) Class.forName(strController);
                controller = (INumberingController) cls.getDeclaredConstructor().newInstance();

            } catch (InstantiationException e) {
                log__.error(e);
            } catch (IllegalAccessException e) {
                log__.error(e);
            } catch (ClassNotFoundException e) {
                log__.error(e);
            } catch (IllegalArgumentException e) {
                log__.error(e);
            } catch (InvocationTargetException e) {
                log__.error(e);
            } catch (NoSuchMethodException e) {
                log__.error(e);
            } catch (SecurityException e) {
                log__.error(e);
            }
        } else {
            controller = new NumberingControllerForH2();
        }
        return controller;
    }

    /**
     * <p>
     * コネクションを取得する
     *
     * @return DB Connection
     * @throws SQLException
     *             コネクションの取得に失敗時にスロー
     */
    private Connection __getConnection() throws SQLException {

        Connection con = __getConnection2();
        con.setAutoCommit(false);
        return con;
    }

    /**
     * <p>プールからコネクションを取得する
     * <p>3回リトライし、失敗した場合はSQLExceptionをスローする
     * <p>またリトライ時は3秒の待ち時間の後にリトライする
     * <p>AutoCommitはTRUEに設定
     * @return Connection
     * @exception SQLException コネクションの取得に失敗した場合にスロー
     */
    private Connection __getConnection2() throws SQLException {
        Connection con = null;
        if (ds__ == null) {
            throw new NullPointerException("DataSource is Null");
        }
        try {
            //1回目
            con = ds__.getConnection();
            return con;
        } catch (SQLException e) {
            log__.error("コネクションの取得に失敗(1回目)", e);
            //throw e;
        }
        sleep();
        try {
            //2回目
            con = ds__.getConnection();
            return con;
        } catch (SQLException e) {
            log__.error("コネクションの取得に失敗(2回目)", e);
        }
        sleep();
        try {
            //3回目
            con = ds__.getConnection();
            return con;
        } catch (SQLException e) {
            log__.fatal("コネクションの取得に失敗(3回目)", e);
            throw e;
        }
    }

    /**
     * <p>処理を3秒またせるメソッド
     */
    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log__.error("3秒の待ち処理に失敗", e);
        }
    }
}