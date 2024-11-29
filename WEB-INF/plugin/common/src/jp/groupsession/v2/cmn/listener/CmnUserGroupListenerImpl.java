package jp.groupsession.v2.cmn.listener;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnMdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnMdispWeatherDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupShareDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlMemberDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPriSortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrThemeDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.dao.UsrPconfDao;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnUserGroupListenerImpl implements IUserGroupListener {

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param con DBコネクション
     * @param cntCon MlCountMtController
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon,
            Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {
    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

        UDate now = new UDate();

        //ユーザ情報個人設定を削除
        UsrPconfDao pconfDao = new UsrPconfDao(con);
        pconfDao.delete(usid);

        //メイン表示設定を削除
        CmnMdispDao mdispDao = new CmnMdispDao(con);
        mdispDao.delete(usid);

        //トップ表示設定を削除
        CmnTdispDao tdispDao = new CmnTdispDao(con);
        tdispDao.delete(usid);

        //ユーザ情報ソート個人設定を削除
        CmnUsrPriSortConfDao usrPriSortDao = new CmnUsrPriSortConfDao(con);
        usrPriSortDao.delete(usid);

        //ユーザテーマを削除
        CmnUsrThemeDao usrThemeDao = new CmnUsrThemeDao(con);
        usrThemeDao.delete(usid);

        //メイン表示設定_天気予報を削除
        CmnMdispWeatherDao mdispWeatherDao = new CmnMdispWeatherDao(con);
        mdispWeatherDao.delete(usid);

        //プラグイン使用制限_メンバーを削除
        CmnPluginControlMemberDao pluginCtrMemDao = new CmnPluginControlMemberDao(con);
        pluginCtrMemDao.deleteForUser(usid);

        //ユーザ設定ラベルを削除
        CmnUsrmLabelDao usrLabelDao = new CmnUsrmLabelDao(con);
        usrLabelDao.delete(usid);

        //マイグループ情報を削除する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        cmgDao.deleteGroup(usid);

        //マイグループ情報明細を削除する
        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        cmgmDao.deleteGroup(usid);

        //マイグループ共有設定を削除する
        CmnMyGroupShareDao cmgsDao = new CmnMyGroupShareDao(con);
        cmgsDao.deleteGroupShare(usid);
    }

    /**
     * <br>[機  能] グループ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid, int eusid) throws SQLException {
    }

    /**
     * <br>[機  能] グループ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(
            Connection con, int gsid, int eusid, RequestModel reqMdl) throws SQLException {
        //プラグイン使用制限_メンバーを削除
        CmnPluginControlMemberDao pluginCtrMemDao = new CmnPluginControlMemberDao(con);
        pluginCtrMemDao.deleteForGroup(gsid);
    }

    /**
     * <br>[機  能] ユーザの所属グループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids, int eusid)
    throws SQLException {
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid, int eusid)
    throws SQLException {
    }
}
