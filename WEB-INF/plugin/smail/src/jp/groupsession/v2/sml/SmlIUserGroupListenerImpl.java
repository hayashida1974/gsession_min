package jp.groupsession.v2.sml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmlAccountAutoDestDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.dao.SmlAccountSortDao;
import jp.groupsession.v2.sml.dao.SmlAccountUserDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlPushUconfDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlPushUconfModel;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] ショートメールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlIUserGroupListenerImpl implements IUserGroupListener {

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

        //ユーザ情報取得
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usrMdl = null;
        usrMdl = udao.select(usid);

        if (usrMdl != null) {
            //アカウント登録
            __insertSmlAccount(cntCon, con, usid, usrMdl, reqMdl);
        }

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
        SmlAccountDao accountDao = new SmlAccountDao(con);
        SmlAccountModel accounMdl = accountDao.selectFromUsrSid(usid);

        if (accounMdl != null && accounMdl.getSacSid() > 0) {

            UDate sysUd = new UDate();

            //ショートメール明細(送信)を論理削除
            SmlSmeisDao sdao = new SmlSmeisDao(con);
            sdao.deleteMsgRonri(accounMdl.getSacSid(), eusid, sysUd);

            //ショートメール明細(受信)を論理削除
            SmlJmeisDao jdao = new SmlJmeisDao(con);
            jdao.deleteMsgRonri(accounMdl.getSacSid(), eusid, sysUd);

            SmlWmeisDao wdao = new SmlWmeisDao(con);

            //ショートメール下書きのデータ使用量を登録(削除対象のデータ使用量を減算)
            ArrayList<Integer> smwSidList = wdao.selectAllWSid(accounMdl.getSacSid());
            SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
            usedDataBiz.insertSoukouDataSize(smwSidList, false);

            //削除前に全メールSIDを保存
            ArrayList < Integer > allSidList =
                wdao.selectAllWSid(accounMdl.getSacSid());

            //ショートメール下書きを物理削除
            wdao.deleteMsgButuri(accounMdl.getSacSid());

            if (!allSidList.isEmpty()) {
                //ショートメール下書き宛先を物理削除
                SmlAsakDao adao = new SmlAsakDao(con);
                adao.deleteMsgButuri(allSidList.toArray(new Integer[allSidList.size()]));
            }

            //アカウントを論理削除
            accountDao.updateJkbn(accounMdl.getSacSid(), GSConstSmail.SAC_JKBN_DELETE);

            //プッシュ通知設定を削除
            SmlPushUconfDao spuDao = new SmlPushUconfDao(con);
            spuDao.deleteAccount(accounMdl.getSacSid());

            //アカウント使用者を削除する
            SmlAccountUserDao acctUserDao = new SmlAccountUserDao(con);
            acctUserDao.deleteOfUser(usid);

            //ショートメールひな形を物理削除
            SmlHinaDao hdao = new SmlHinaDao(con);
            hdao.deleteHinaButuri(accounMdl.getSacSid());

            //ショートメール自動送信先を物理削除
            SmlAccountAutoDestDao saaDao = new SmlAccountAutoDestDao(con);
            saaDao.delete(accounMdl.getSacSid());

        }
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




    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param cntCon MlCountMtController
     * @param usrMdl CmnUsrmInfModel
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void __insertSmlAccount(MlCountMtController cntCon,
                                   Connection con,
                                   int usid,
                                   CmnUsrmInfModel usrMdl,
                                   RequestModel reqMdl) throws SQLException {

        //アカウント情報の登録
        SmlAccountModel accountModel = new SmlAccountModel();

        accountModel.setUsrSid(usid);
        accountModel.setSacType(GSConstSmail.SAC_TYPE_USER);
        accountModel.setSacName(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
        accountModel.setSacBiko("");
        accountModel.setSacTheme(0);
        accountModel.setSacQuotes(0);
        accountModel.setSacSendMailtype(0);

        //アカウント採番取得
        int sacSaiSid = (int) cntCon.getSaibanNumber("smail", "account", -1);

        SmlAccountDao accountDao = new SmlAccountDao(con);
        SmlAccountUserDao accountUserDao = new SmlAccountUserDao(con);
        SmlAccountSortDao accountSortDao = new SmlAccountSortDao(con);
        SmlAccountDiskDao wadDao = new SmlAccountDiskDao(con);

        accountModel.setSacSid(sacSaiSid);
        accountDao.insertAccountDef(accountModel);

        //アカウントの並び順を登録する
        accountSortDao.insertAccountSort(sacSaiSid, usid);
        accountUserDao.insert(sacSaiSid, accountModel.getSacType(),
                   new String[] {String.valueOf(usid)});

        //Push通知設定
        if (PushServiceOperator.getInstance(con, reqMdl.getDomain()).isUseable()) {
            SmlPushUconfDao spuDao = new SmlPushUconfDao(con);
            SmlPushUconfModel mdl = new SmlPushUconfModel();
            mdl.setSacSid(sacSaiSid);
            mdl.setUsrSid(usid);
            mdl.setSpuPushuse(GSConstSmail.ACCOUNT_PUSH_OFF);
            spuDao.insert(mdl);
        }

        //ディスク使用量の新規登録
        SmlAccountDiskModel useDiskMdl = new SmlAccountDiskModel();
        useDiskMdl.setSacSid(sacSaiSid);
        //新規登録時は使用サイズを0にセット
        useDiskMdl.setSdsSize(0);
        wadDao.insert(useDiskMdl);
    }
}