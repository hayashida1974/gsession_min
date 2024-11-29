package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.base.SaibanDao;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

/**
 * <br>[機 能] 採番を行いマスタをアップデート
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class NumberingControllerForH2 implements INumberingController {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NumberingControllerForH2.class);

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
    public synchronized long getSaibanNumber(Connection con, String sid, String sids,
                                                int uid)
            throws SQLException {
        boolean commitFlg = false;
        long bgSibanNum = 0;

        try {
            bgSibanNum = getSaibanNumberNotCommit(con, sid, sids, uid);
            // DB操作正常終了
            commitFlg = true;
        } catch (SQLException e) {
            log__.error(e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
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
     * @param con コネクション
     * @param sid
     *            採番SID
     * @param sids
     *            採番SIDサブ
     * @param uid
     *            ユーザSID
     * @param maxid
     *            更新する値
     * @exception SQLException
     *                DB実行例外の場合にスローする
     * @return 採番SID
     */
    public synchronized long updateSaibanId(Connection con, String sid, String sids, int uid,
            long maxid) throws SQLException {
        boolean commitFlg = false;
        long bgSibanNum = 0;

        try {

            // 採番マスタから既存の情報取得
            SaibanDao dao = new SaibanDao(con);
            SaibanModel param = new SaibanModel();
            param.setSbnSid(sid);
            param.setSbnSidSub(sids);
            param.setSbnAid(uid);
            SaibanModel model = dao.getSaibanData(param);
            if (model == null) {
                model = new SaibanModel();
            }

            // 更新対象の番号をセット
            bgSibanNum = maxid;

            // 更新項目をbeanにセット
            model.setSbnSid(sid); // 採番SID
            model.setSbnSidSub(sids); // 採番SIDサブ
            model.setSbnNumber(bgSibanNum); // 採番No(数値)
            model.setSbnString(sids); // 採番No(文字)
            model.setSbnEid(uid); // 更新者
            UDate date = new UDate();
            model.setSbnEdate(date); // 更新日時

            // 採番マスタ更新
            int ret = dao.updateSaibanNo(model);

            // 採番マスタ未更新(既存データ無し)の場合
            if (ret < 1) {
                model.setSbnAdate(date); // 登録日時
                model.setSbnAid(uid); // 登録者
                dao.insert(model);
            }
            // DB操作正常終了
            commitFlg = true;
        } catch (SQLException e) {
            log__.error(e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
            JDBCUtil.closeConnection(con);
        }

        return bgSibanNum;
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

        long bgSibanNum = 0;

        try {
            SaibanDao dao = new SaibanDao();
            dao.setCon(con);

            // 採番マスタから既存の情報取得
            SaibanModel param = new SaibanModel();
            param.setSbnSid(sid);
            param.setSbnSidSub(sids);
            param.setSbnAid(uid);
            SaibanModel model = dao.getSaibanData(param);
            if (model == null) {
                model = new SaibanModel();
            }
            bgSibanNum = model.getSbnNumber();
            param = null;

            bgSibanNum += 1;
            // 更新項目をbeanにセット
            model.setSbnSid(sid); // 採番SID
            model.setSbnSidSub(sids); // 採番SIDサブ
            model.setSbnNumber(bgSibanNum); // 採番No(数値)
            model.setSbnString(sids); // 採番No(文字)
            model.setSbnEid(uid); // 更新者
            UDate date = new UDate();
            model.setSbnEdate(date); // 更新日時

            // 採番マスタ更新
            int ret = dao.updateSaibanNo(model);

            // 採番マスタ未更新(既存データ無し)の場合
            if (ret < 1) {
                model.setSbnAdate(date); // 登録日時
                model.setSbnAid(uid); // 登録者
                dao.insert(model);
            }

            model = null;
            dao = null;
        } catch (SQLException e) {
            log__.error(e);
            throw e;
        }

        return bgSibanNum;
    }

    @Override
    public synchronized List<Long> getSaibanNumbers(Connection con, String sid, String sids,
            int uid, int count) throws SQLException {
        boolean commitFlg = false;
        long bgSibanNum = 0;

        try {
            SaibanDao dao = new SaibanDao();
            dao.setCon(con);

            // 採番マスタから既存の情報取得
            SaibanModel param = new SaibanModel();
            param.setSbnSid(sid);
            param.setSbnSidSub(sids);
            param.setSbnAid(uid);
            SaibanModel model = dao.getSaibanData(param);
            if (model == null) {
                model = new SaibanModel();
            }
            bgSibanNum = model.getSbnNumber();
            param = null;


            // 更新項目をbeanにセット
            model.setSbnSid(sid); // 採番SID
            model.setSbnSidSub(sids); // 採番SIDサブ
            model.setSbnNumber(bgSibanNum + count); // 採番No(数値)
            model.setSbnString(sids); // 採番No(文字)
            model.setSbnEid(uid); // 更新者
            UDate date = new UDate();
            model.setSbnEdate(date); // 更新日時

            // 採番マスタ更新
            int ret = dao.updateSaibanNo(model);

            // 採番マスタ未更新(既存データ無し)の場合
            if (ret < 1) {
                model.setSbnAdate(date); // 登録日時
                model.setSbnAid(uid); // 登録者
                dao.insert(model);
            }

            model = null;
            dao = null;

            // DB操作正常終了
            con.commit();

            commitFlg = true;
        } catch (SQLException e) {
            log__.error(e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        return LongStream.range(bgSibanNum + 1, bgSibanNum + count + 1)
                    .mapToObj(index -> index)
                    .collect(Collectors.toList());
    }
}