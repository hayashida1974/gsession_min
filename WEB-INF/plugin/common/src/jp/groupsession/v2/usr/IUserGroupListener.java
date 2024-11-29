package jp.groupsession.v2.usr;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナー
 * <br>[解  説]
 * <br>[備  考]  DBのコミット、ロールバック処理は呼び出し元で行うので行わないこと。
 *
 * @author JTS
 */
public interface IUserGroupListener {

    /**
     * <p>ユーザ追加時に実行される
     * @param usid 追加されるユーザSID
     * @param eusid 更新者ユーザSID
     * @param cntCon MlCountMtController
     * @param con DBコネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon, Connection con, int usid,
            int eusid, RequestModel reqMdl) throws SQLException;

    /**
     * <p>ユーザ削除時に実行される
     * @param usid ユーザSID
     * @param eusid 更新者ユーザSID
     * @param con DBコネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid,
            int eusid, RequestModel reqMdl) throws SQLException;

    /**
     * <p>グループ追加時に実行される
     * @param gsid グループSID
     * @param eusid 更新者ユーザSID
     * @param con DBコネクション
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid,
            int eusid) throws SQLException;

    /**
     * <p>グループ削除時に実行される
     * @param gsid グループSID
     * @param eusid 更新者ユーザSID
     * @param con DBコネクション
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(Connection con, int gsid,
            int eusid, RequestModel reqMdl) throws SQLException;

    /**
     * <p>ユーザの所属グループが変更になった場合に実行される
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids,
            int eusid) throws SQLException;

    /**
     * <p>ユーザのデフォルトグループが変更になった場合に実行される
     * @param usid ユーザSID
     * @param eusid 更新者ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid,
            int eusid) throws SQLException;

}