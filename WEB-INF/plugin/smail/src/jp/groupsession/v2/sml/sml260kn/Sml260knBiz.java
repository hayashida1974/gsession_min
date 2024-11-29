package jp.groupsession.v2.sml.sml260kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.dao.SmlAccountSortDao;
import jp.groupsession.v2.sml.dao.SmlAccountUserDao;
import jp.groupsession.v2.sml.dao.SmlPushUconfDao;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlPushUconfModel;
import jp.groupsession.v2.sml.sml260.SmailCsvModel;
import jp.groupsession.v2.sml.sml260.SmailCsvReader;
import jp.groupsession.v2.sml.sml260.Sml260Biz;

/**
 * <br>[機  能] ショートメール アカウントインポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Sml260knBiz.class);


    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, RequestModel reqMdl, Sml260knParamModel paramMdl)
    throws Exception {

        log__.debug("START");

        SmlCommonBiz sBiz = new SmlCommonBiz();

        //テンポラリディレクトリパスを取得
        Sml260Biz biz260 = new Sml260Biz();
        String tempDir = biz260.getTempDir(reqMdl);

        //取込ファイル名を設定する
        List<LabelValueBean> fileLabel = sBiz.getFileCombo(tempDir);

        paramMdl.setSml260knFileName(fileLabel.get(0).getLabel());

        //取込アカウント情報を設定する
        Sml260knDao dao = new Sml260knDao(con);
        List<SmailCsvModel> accountList = getSmailList(tempDir);
        List<Sml260knUseUsrModel> modelList = new ArrayList<Sml260knUseUsrModel>();
        Sml260knUseUsrModel model = null;
        List<String> useUsrIds = null;

        for (int i = 0; i < accountList.size(); i++) {
            useUsrIds = new ArrayList<String>();
            model = new Sml260knUseUsrModel();
            model.setAccountName(accountList.get(i).getAccountName());
            useUsrIds.add(accountList.get(i).getUser1());
            useUsrIds.add(accountList.get(i).getUser2());
            useUsrIds.add(accountList.get(i).getUser3());
            useUsrIds.add(accountList.get(i).getUser4());
            useUsrIds.add(accountList.get(i).getUser5());
            model.setUserNameList(dao.getUseUserNameList(useUsrIds));

            //アカウント使用者情報(グループ指定)を取得する
            List<String> useGroupIdList = new ArrayList<String>();
            useGroupIdList.add(accountList.get(i).getGroup1());
            useGroupIdList.add(accountList.get(i).getGroup2());
            useGroupIdList.add(accountList.get(i).getGroup3());
            useGroupIdList.add(accountList.get(i).getGroup4());
            useGroupIdList.add(accountList.get(i).getGroup5());
            CmnGroupmDao cmnGrpDao = new CmnGroupmDao(con);
            String[] grpIds = (String[]) useGroupIdList.toArray(new String[useGroupIdList.size()]);
            //グループIDをもとにグループSIDを取得する
            model.setGroupNameList(cmnGrpDao.selectGrpData(grpIds, 0));

            modelList.add(model);
        }

        paramMdl.setSml260knUseUserList(modelList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] アカウント情報の登録を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行時例外
     * @return List
     */
    public List<SmailCsvModel> addAccount(Connection con, RequestModel reqMdl,
                            MlCountMtController mtCon, int sessionUserSid) throws Exception {
        log__.debug("START");

        //テンポラリディレクトリパスを取得
        Sml260Biz biz260 = new Sml260Biz();
        String tempDir = biz260.getTempDir(reqMdl);

        List<SmailCsvModel> accountList = getSmailList(tempDir);
        Sml260knDao dao = new Sml260knDao(con);
        SmlAccountDao accountDao = new SmlAccountDao(con);
        SmlAccountUserDao accountUserDao = new SmlAccountUserDao(con);
        SmlAccountSortDao accountSortDao = new SmlAccountSortDao(con);
        SmlAccountDiskDao accountDiskDao = new SmlAccountDiskDao(con);

        //Push通知用
        boolean pushUse = PushServiceOperator.getInstance(con, reqMdl.getDomain()).isUseable();
        SmlPushUconfDao spuDao = new SmlPushUconfDao(con);

        for (SmailCsvModel accountData : accountList) {
            //アカウント情報の登録
            SmlAccountModel accountModel = new SmlAccountModel();

            int wacSaiSid = (int) mtCon.getSaibanNumber(GSConstSmail.SAIBAN_SML_SID,
                    GSConstSmail.SBNSID_SUB_ACCOUNT,
                    sessionUserSid);

            accountModel.setSacSid(wacSaiSid);
            accountModel.setSacName(accountData.getAccountName());
            accountModel.setSacType(GSConstSmail.SAC_TYPE_USER);
            accountModel.setSacBiko(accountData.getBiko());
            accountModel.setSacSendMailtype(Integer.parseInt(accountData.getSndMailType()));
            accountModel.setSacJkbn(GSConstSmail.SAC_JKBN_NORMAL);


            accountDao.insertAccount(accountModel, -1);


            //アカウント使用者情報(ユーザ指定)を取得する
            ArrayList<String> usrDataList = new ArrayList<String>();
            List<String> useUsrIdList = new ArrayList<String>();
            useUsrIdList.add(accountData.getUser1());
            useUsrIdList.add(accountData.getUser2());
            useUsrIdList.add(accountData.getUser3());
            useUsrIdList.add(accountData.getUser4());
            useUsrIdList.add(accountData.getUser5());
            usrDataList = dao.getUseUserSidList(useUsrIdList);

            //アカウント使用者を登録する
            SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
            //デッドロックが発生しないようSIDを降順に設定
            usrDataList = smlCmnBiz.setOrderBySidDescStr(usrDataList);
            String[] usrSids = (String[]) usrDataList.toArray(new String[usrDataList.size()]);
            accountUserDao.insert(wacSaiSid, GSConstSmail.SAC_TYPE_USER, usrSids);

            //Push通知設定
            if (PushServiceOperator.getInstance(con, reqMdl.getDomain()).isUseable()) {
                for (String usrSid : usrSids) {
                    SmlPushUconfModel mdl = new SmlPushUconfModel();
                    mdl.setSacSid(wacSaiSid);
                    mdl.setUsrSid(NullDefault.getInt(usrSid, -1));
                    mdl.setSpuPushuse(GSConstSmail.ACCOUNT_PUSH_OFF);
                    spuDao.insert(mdl);
                }
            }

            //アカウント使用者情報(グループ指定)を取得する
            List<String> useGroupIdList = new ArrayList<String>();
            useGroupIdList.add(accountData.getGroup1());
            useGroupIdList.add(accountData.getGroup2());
            useGroupIdList.add(accountData.getGroup3());
            useGroupIdList.add(accountData.getGroup4());
            useGroupIdList.add(accountData.getGroup5());
            CmnGroupmDao cmnGrpDao = new CmnGroupmDao(con);
            String[] grpIds = (String[]) useGroupIdList.toArray(new String[useGroupIdList.size()]);
            //グループIDをもとにグループSIDを取得する
            List<CmnGroupmModel> groupDataList = cmnGrpDao.selectGrpData(grpIds, 0);
            ArrayList<String> groupSids = new ArrayList<String>();
            for (CmnGroupmModel cmnGrp:groupDataList) {
                groupSids.add(String.valueOf(cmnGrp.getGrpSid()));
            }
            //デッドロックが発生しないようSIDを降順に設定
            groupSids = smlCmnBiz.setOrderBySidDescStr(groupSids);
            accountUserDao.insert(
                    wacSaiSid,
                    GSConstSmail.SAC_TYPE_GROUP,
                    (String[]) groupSids.toArray(new String[groupSids.size()]));

            //アカウントソートの並び順を登録する
            for (int m = 0; m < usrSids.length; m++) {
                accountSortDao.insertAccountSort(wacSaiSid, Integer.parseInt(usrSids[m]));
            }

            //アカウントディスク使用量を登録する
            SmlAccountDiskModel acntDiskMdl = new SmlAccountDiskModel();
            acntDiskMdl.setSacSid(wacSaiSid);
            acntDiskMdl.setSdsSize(0);
            accountDiskDao.insert(acntDiskMdl);
        }
        return accountList;
    }
    /**
     * <br>[機  能] CSVファイルからアカウント情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return アカウント情報一覧
     * @throws Exception 実行時例外
     */
    public List<SmailCsvModel> getSmailList(String tempDir) throws Exception {

        SmlCommonBiz sBiz = new SmlCommonBiz();
        List<Cmn110FileModel> fileDataList = sBiz.getFileData(tempDir);
        String fullPath = tempDir + fileDataList.get(0).getSaveFileName();
        SmailCsvReader csvReader = new SmailCsvReader();
        csvReader.readCsvFile(fullPath);

        return csvReader.getSmailList();
    }
}
