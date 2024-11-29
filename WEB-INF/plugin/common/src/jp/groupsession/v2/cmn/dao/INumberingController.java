package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * <br>[機 能] 採番処理の実装部インターフェース
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public interface INumberingController {

    /**
     * <br>
     * [機 能] IDを取得し採番マスタをアップデート <br>
     * [解 説] 本メソッドは数値型の採番を行う <br>
     * [備 考] 本メソッドを実行するユーザのSIDが 不確定(新規登録処理等)の場合は 引数:ユーザSIDを-1で渡してください
     * @param con コネクション
     * @param sid 採番SID
     * @param sids 採番SIDサブ
     * @param uid ユーザSID
     * @exception SQLException DB実行例外の場合にスローする
     * @return 採番SID
     */
    public long getSaibanNumber(Connection con, String sid, String sids, int uid)
            throws SQLException;

    /**
     * <br>
     * [機 能] IDを取得し採番マスタをアップデート <br>
     * [解 説] 本メソッドは数値型の採番を行う <br>
     * [備 考] 本メソッドを実行するユーザのSIDが 不確定(新規登録処理等)の場合は 引数:ユーザSIDを-1で渡してください
     * @param con コネクション
     * @param sid 採番SID
     * @param sids 採番SIDサブ
     * @param uid ユーザSID
     * @param count 取得件数
     * @exception SQLException DB実行例外の場合にスローする
     * @return 採番SIDリスト
     */
    public List<Long> getSaibanNumbers(Connection con, String sid, String sids, int uid, int count)
            throws SQLException;


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
    public long getSaibanNumberNotCommit(Connection con, String sid,
                                        String sids, int uid)
    throws SQLException;
}