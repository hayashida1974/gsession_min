package jp.groupsession.v2.wml.wml160kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.dao.base.CmnOauthTokenDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthTokenModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDiskDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvdataDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSortDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserProxyDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlManageNoticeDao;
import jp.groupsession.v2.wml.model.base.WmlAccountDiskModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvdataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;
import jp.groupsession.v2.wml.model.base.WmlManageNoticeModel;
import jp.groupsession.v2.wml.wml160.WebmailCsvModel;
import jp.groupsession.v2.wml.wml160.WebmailCsvReader;
import jp.groupsession.v2.wml.wml160.Wml160Biz;

/**
 * <br>[機  能] WEBメール アカウントインポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Wml160knBiz.class);

    /** ディレクトリ種別 */
    private static final int[] DIR_TYPE = {GSConstWebmail.DIR_TYPE_RECEIVE,
            GSConstWebmail.DIR_TYPE_SENDED,
            GSConstWebmail.DIR_TYPE_NOSEND,
            GSConstWebmail.DIR_TYPE_DRAFT,
            GSConstWebmail.DIR_TYPE_DUST,
            GSConstWebmail.DIR_TYPE_SPAM,
            GSConstWebmail.DIR_TYPE_STORAGE
    };

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Wml160knParamModel paramMdl, RequestModel reqMdl)
            throws Exception {

        log__.debug("START");

        WmlBiz wBiz = new WmlBiz();

        //テンポラリディレクトリパス取得
        Wml160Biz biz160 = new Wml160Biz();
        String tempDir = biz160.getTempDir(reqMdl);

        //取込ファイル名を設定する
        List<LabelValueBean> fileLabel = wBiz.getFileCombo(tempDir);
        paramMdl.setWml160knFileName(fileLabel.get(0).getLabel());

        //取込アカウント情報を設定する
        Wml160knDao dao = new Wml160knDao(con);
        List<WebmailCsvModel> accountList = getWebmailList(tempDir);
        List<Wml160knUseUsrModel> modelList = new ArrayList<Wml160knUseUsrModel>();
        Wml160knUseUsrModel model = null;
        List<String> useUsrIds = null;
        List<String> proxyUsrIds = null;

        for (int i = 0; i < accountList.size(); i++) {
            WebmailCsvModel csvData = accountList.get(i);
            model = new Wml160knUseUsrModel();
            model.setAccountName(csvData.getAccountName());
            int userKbn = Integer.parseInt(csvData.getUserKbn());
            model.setUserKbn(userKbn);

            //使用者
            useUsrIds = new ArrayList<String>();
            useUsrIds.add(csvData.getUser1());
            useUsrIds.add(csvData.getUser2());
            useUsrIds.add(csvData.getUser3());
            useUsrIds.add(csvData.getUser4());
            useUsrIds.add(csvData.getUser5());
            model.setUserNameList(
                    dao.getUseUserNameList(useUsrIds, userKbn));

            //代理人
            proxyUsrIds = new ArrayList<String>();
            proxyUsrIds.add(csvData.getProxyUser1());
            proxyUsrIds.add(csvData.getProxyUser2());
            proxyUsrIds.add(csvData.getProxyUser3());
            proxyUsrIds.add(csvData.getProxyUser4());
            proxyUsrIds.add(csvData.getProxyUser5());
            model.setProxyUserNameList(
                    dao.getUseUserNameList(proxyUsrIds, 0));

            modelList.add(model);
        }

        paramMdl.setWml160knUseUserList(modelList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] アカウント情報の登録を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリ
     * @param userSid ユーザSID
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行時例外
     * @return List
     */
    public List<WebmailCsvModel> addAccount(Connection con, RequestModel reqMdl,
            String tempDir, int userSid, MlCountMtController mtCon,
            int sessionUserSid) throws Exception {
        log__.debug("START");

        List<WebmailCsvModel> accountList = getWebmailList(tempDir);
        Wml160knDao dao = new Wml160knDao(con);
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountUserDao accountUserDao = new WmlAccountUserDao(con);
        WmlAccountUserProxyDao accountProxyUserDao = new WmlAccountUserProxyDao(con);
        WmlDirectoryModel directoryModel = new WmlDirectoryModel();
        WmlAccountSortDao accountSortDao = new WmlAccountSortDao(con);
        WmlAccountDiskDao accountDiskDao = new WmlAccountDiskDao(con);
        WmlAccountSignDao accountSignDao = new WmlAccountSignDao(con);
        WmlAccountRcvdataDao accountRcvDataDao = new WmlAccountRcvdataDao(con);
        CmnOauthDao oauthDao = new CmnOauthDao(con);
        CmnOauthTokenDao oauthTokenDao = new CmnOauthTokenDao(con);

        OAuthBiz authBiz = new OAuthBiz();

        MailEncryptBiz protocolBiz = new MailEncryptBiz();
        for (WebmailCsvModel accountData : accountList) {
            //アカウント情報の登録
            WmlAccountModel accountModel = new WmlAccountModel();
            CmnOauthModel oauthModel =  new CmnOauthModel();
            int beforeWacSid = accountDao.getAccountSid(accountData.getAccountId());

            int wacSaiSid = beforeWacSid;
            if (beforeWacSid <= 0) {
                wacSaiSid = (int) mtCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                        GSConstWebmail.SBNSID_SUB_ACCOUNT,
                        sessionUserSid);
            }

            accountModel.setWacSid(wacSaiSid);
            accountModel.setWacAccountId(accountData.getAccountId());
            accountModel.setWacName(accountData.getAccountName());
            accountModel.setWacAddress(accountData.getMail());

            accountModel.setWacAuthType(
                    NullDefault.getInt(accountData.getAuthType(),
                            GSConstWebmail.WAC_AUTH_TYPE_NORMAL));

            String address = null;
            int provider = 0;
            int oauthSid = 0;
            if (accountModel.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                provider = Integer.parseInt(accountData.getProvider());
                oauthSid = oauthDao.getOAuthSid(provider);
                oauthModel.setCouProvider(provider);

                MailBiz mailBiz = new MailBiz();
                address = mailBiz.extractAddress(accountData.getMail());
                accountModel.setWacSendHost(null);
                accountModel.setWacSendPort(0);
                accountModel.setWacSendUser(address);
                accountModel.setWacSendPass(null);

            } else {
                oauthModel.setCouProvider(0);
                accountModel.setWacSendHost(accountData.getMailSndServer());
                accountModel.setWacSendPort(Integer.parseInt(accountData.getMailSndPort()));
                accountModel.setWacSendUser(accountData.getMailSndUser());
                accountModel.setWacSendPass(accountData.getMailSndPass());
            }


            int sendEcryptType = NullDefault.getInt(accountData.getMailSndSsl(), -1);
            if (protocolBiz.isExistProtocol(sendEcryptType)) {
                accountModel.setWacSendSsl(sendEcryptType);
            } else {
                accountModel.setWacSendSsl(GSConstWebmail.WAC_SEND_SSL_NOTUSE);
            }

            accountModel.setWacReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_POP);

            int userKbn = Integer.parseInt(accountData.getUserKbn());
            if (userKbn == WebmailCsvModel.USERKBN_GROUP) {
                accountModel.setWacType(GSConstWebmail.WAC_TYPE_GROUP);
            } else {
                accountModel.setWacType(GSConstWebmail.WAC_TYPE_USER);
            }

            if (accountModel.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                accountModel.setWacReceiveHost(null);
                accountModel.setWacReceivePort(0);
                accountModel.setWacReceiveUser(address);
                accountModel.setWacReceivePass(null);
            } else {
                accountModel.setWacReceiveHost(accountData.getMailRsvServer());
                accountModel.setWacReceivePort(Integer.parseInt(accountData.getMailRsvPort()));
                accountModel.setWacReceiveUser(accountData.getMailRsvUser());
                accountModel.setWacReceivePass(accountData.getMailRsvPass());
            }

            int rsvEcryptType = NullDefault.getInt(accountData.getMailRsvSsl(), -1);
            if (protocolBiz.isExistProtocol(rsvEcryptType)) {
                accountModel.setWacReceiveSsl(rsvEcryptType);
            } else {
                accountModel.setWacReceiveSsl(GSConstWebmail.WAC_RECEIVE_SSL_NOTUSE);
            }

            int diskCapa = GSConstWebmail.WAC_DISK_UNLIMITED;
            if (__checkKbn(accountData.getDiskCapa(), GSConstWebmail.WAC_DISK_LIMIT)) {
                diskCapa = GSConstWebmail.WAC_DISK_LIMIT;
                accountModel.setWacDiskSize(Integer.parseInt(accountData.getDiskCapaSize()));
            }
            accountModel.setWacDisk(diskCapa);

            if (__checkKbn(accountData.getDiskCapaSps(), GSConstWebmail.WAC_DISK_SPS_SPSETTINGS)) {
                accountModel.setWacDiskSps(GSConstWebmail.WAC_DISK_SPS_SPSETTINGS);
            } else {
                accountModel.setWacDiskSps(GSConstWebmail.WAC_DISK_SPS_NORMAL);
            }

            accountModel.setWacBiko(accountData.getBiko());
            accountModel.setWacOrganization(accountData.getOrganization());
            accountModel.setWacSignAuto(
                    __formatKbn(accountData.getSignAuto(),
                            GSConstWebmail.WAC_SIGN_AUTO_INSERT));
            accountModel.setWacSignPointKbn(
                    __formatKbn(accountData.getSignPoint(),
                            GSConstWebmail.WAC_SIGN_POINT_KBN_BOTTOM));
            int signDspKbn = __formatKbn(accountData.getSignDsp(),
                    WebmailCsvModel.SIGN_DSP_KBN_DSP);
            if (signDspKbn == WebmailCsvModel.SIGN_DSP_KBN_DSP) {
                accountModel.setWacSignDspKbn(GSConstWebmail.WAC_SIGN_DSP_KBN_DSP);
            } else {
                accountModel.setWacSignDspKbn(GSConstWebmail.WAC_SIGN_DSP_KBN_HIDE);
            }
            accountModel.setWacAutoto(accountData.getAutoTo());
            accountModel.setWacAutocc(accountData.getAutoCc());
            accountModel.setWacAutobcc(accountData.getAutoBcc());
            accountModel.setWacDelreceive(
                    __formatKbn(accountData.getRsvDelete(),
                            GSConstWebmail.WAC_DELRECEIVE_NO));
            accountModel.setWacRereceive(
                    __formatKbn(accountData.getRsvOk(),
                            GSConstWebmail.WAC_RERECEIVE_NO));
            accountModel.setWacApop(
                    __formatKbn(accountData.getApop(),
                            GSConstWebmail.WAC_APOP_NOTUSE));
            accountModel.setWacTopCmd(
                    __formatKbn(accountData.getTopCmd(),
                            GSConstWebmail.WAC_TOP_COMMAND_ENABLE));
            accountModel.setWacSmtpAuth(
                    __formatKbn(accountData.getSmtpNinsyo(),
                            GSConstWebmail.WAC_SMTPAUTH_NO));
            accountModel.setWacPopbsmtp(
                    __formatKbn(accountData.getBeSndPopNinsyo(),
                            GSConstWebmail.WAC_POPBSMTP_NO));
            accountModel.setWacEncodeSend(
                    __formatKbn(accountData.getSndWordCode(),
                            GSConstWebmail.WAC_ENCODE_SEND_ISO2022JP));
            accountModel.setWacAutoreceive(
                    __formatKbn(accountData.getMailAutoRsv(),
                            GSConstWebmail.MAIL_AUTO_RSV_OFF));
            accountModel.setWacSendMailtype(
                    __formatKbn(accountData.getSndMailType(),
                            GSConstWebmail.WAC_SEND_MAILTYPE_NORMAL));
            accountModel.setWacReceiveDate(null);
            accountModel.setWacJkbn(GSConstWebmail.WAC_JKBN_NORMAL);

            //メール自動受信間隔
            int wacAutoRsvTime = GSConstWebmail.AUTO_RECEIVE_FIRST;
            if (accountModel.getWacAutoreceive() == GSConstWebmail.MAIL_AUTO_RSV_ON) {
                int autoRsvTime = __formatKbn(accountData.getMailAutoRsvTime(),
                        WebmailCsvModel.AUTO_RSVTIME_5);
                switch (autoRsvTime) {
                case WebmailCsvModel.AUTO_RSVTIME_5:
                    wacAutoRsvTime = GSConstWebmail.AUTO_RECEIVE_5;
                    break;
                case WebmailCsvModel.AUTO_RSVTIME_10:
                    wacAutoRsvTime = GSConstWebmail.AUTO_RECEIVE_10;
                    break;
                case WebmailCsvModel.AUTO_RSVTIME_30:
                    wacAutoRsvTime = GSConstWebmail.AUTO_RECEIVE_30;
                    break;
                case WebmailCsvModel.AUTO_RSVTIME_60:
                    wacAutoRsvTime = GSConstWebmail.AUTO_RECEIVE_60;
                    break;
                default:
                    wacAutoRsvTime = GSConstWebmail.AUTO_RECEIVE_FIRST;
                    break;
                }
            }
            accountModel.setWacAutoReceiveTime(wacAutoRsvTime);

            accountModel.setWacCheckAddress(
                    __formatKbn(accountData.getCheckAddress(),
                            GSConstWebmail.WAC_CHECK_ADDRESS_NOCHECK));
            accountModel.setWacCheckFile(
                    __formatKbn(accountData.getCheckFile(),
                            GSConstWebmail.WAC_CHECK_FILE_NOCHECK));
            accountModel.setWacCompressFile(
                    __formatKbn(accountData.getCompressFile(),
                            GSConstWebmail.WAC_COMPRESS_FILE_NOTCOMPRESS));
            if (accountModel.getWacCompressFile() == GSConstWebmail.WAC_COMPRESS_FILE_INPUT) {
                if (NullDefault.getString(accountData.getCompressFileDef(), "").equals("1")) {
                    accountModel.setWacCompressFileDef(
                            GSConstWebmail.WAC_COMPRESS_FILE_DEF_COMPRESS);
                } else {
                    accountModel.setWacCompressFileDef(
                            GSConstWebmail.WAC_COMPRESS_FILE_DEF_NOTCOMPRESS);
                }
            } else {
                accountModel.setWacCompressFileDef(GSConstWebmail.WAC_COMPRESS_FILE_DEF_NOSET);
            }
            accountModel.setWacTimesent(
                    __formatKbn(accountData.getTimeSent(),
                            GSConstWebmail.WAC_TIMESENT_NOSET));
            if (accountModel.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_INPUT) {
                if (NullDefault.getString(accountData.getTimeSentDef(), "").equals("1")) {
                    accountModel.setWacTimesentDef(GSConstWebmail.WAC_TIMESENT_DEF_LATER);
                } else {
                    accountModel.setWacTimesentDef(GSConstWebmail.WAC_TIMESENT_DEF_IMM);
                }
            } else {
                accountModel.setWacTimesentDef(GSConstWebmail.WAC_TIMESENT_DEF_NOSET);
            }
            accountModel.setWacTheme(
                    __formatKbn(accountData.getTheme(),
                            GSConstWebmail.WAC_THEME_NOSET));
            accountModel.setWacQuotes(
                    __formatKbn(accountData.getQuotes(),
                            GSConstWebmail.WAC_QUOTES_DEF));
            accountModel.setWacAutoSaveMinute(
                    __formatKbn(accountData.getAutoSave(),
                            GSConstWebmail.WAC_AUTO_SAVE_DEF));


            //アカウント使用者情報を取得する
            List<String> usrDataList = new ArrayList<String>();
            List<String> useUsrIdList = new ArrayList<String>();
            useUsrIdList.add(accountData.getUser1());
            useUsrIdList.add(accountData.getUser2());
            useUsrIdList.add(accountData.getUser3());
            useUsrIdList.add(accountData.getUser4());
            useUsrIdList.add(accountData.getUser5());
            usrDataList = dao.getUseUserSidList(useUsrIdList, userKbn);
            String[] usrSids =
                    (String[]) usrDataList.toArray(new String[usrDataList.size()]);


            //アカウント代理人情報を取得する
            List<String> proxyUsrDataList = new ArrayList<String>();
            List<String> proxyUsrIdList = new ArrayList<String>();
            proxyUsrIdList.add(accountData.getProxyUser1());
            proxyUsrIdList.add(accountData.getProxyUser2());
            proxyUsrIdList.add(accountData.getProxyUser3());
            proxyUsrIdList.add(accountData.getProxyUser4());
            proxyUsrIdList.add(accountData.getProxyUser5());
            proxyUsrDataList = dao.getUseUserSidList(proxyUsrIdList, 0);
            String[] proxyUsrSids =
                    (String[]) proxyUsrDataList.toArray(new String[proxyUsrDataList.size()]);

            GsMessage gsMsg = new GsMessage(reqMdl);
            WmlAccountSignModel signModel = null;
            if (!StringUtil.isNullZeroString(accountData.getSign())) {
                signModel = new WmlAccountSignModel();
                signModel.setWacSid(accountModel.getWacSid());
                signModel.setWsiNo(1);
                signModel.setWsiTitle(gsMsg.getMessage("wml.34"));
                signModel.setWsiSign(accountData.getSign());
                signModel.setWsiDef(GSConstWebmail.WSI_DEF_DEFAULT);
            }

            //OAuth認証トークン情報を登録する
            if (accountModel.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {

                int cotSid = 0;
                CmnOauthTokenModel tokenMdl
                    = oauthTokenDao.getOauthToken(oauthSid, address);
                if (tokenMdl == null) {
                    //対象アカウントのOAuthトークン情報が存在しない場合、新規登録を行う
                    OauthDataModel authData = new OauthDataModel();
                    authData.setCouSid(oauthSid);
                    authData.setAccountId(address);
                    cotSid = authBiz.entryOAuthToken(con, mtCon, authData, sessionUserSid);
                } else {
                    cotSid = tokenMdl.getCotSid();
                }

                accountModel.setCotSid(cotSid);
            }


            if (beforeWacSid > 0) {
                //アカウント情報の変更を行う
                accountDao.updateAccount(accountModel, GSConstWebmail.ACCOUNTMODE_COMMON);

                //アカウント使用者情報を削除する
                accountUserDao.delete(beforeWacSid);
                //アカウント代理人情報を削除する
                accountProxyUserDao.deleteProxyUser(beforeWacSid);
                //アカウント署名のデフォルトを更新する
                if (signModel != null) {
                    int updateCnt = accountSignDao.updateDefSign(accountModel.getWacSid(),
                            signModel.getWsiSign());
                    if (updateCnt == 0) {
                        accountSignDao.insert(signModel);
                    }
                }

            } else {
                //アカウント情報の新規登録を行う
                accountDao.insertAccount(accountModel, -1);

                //ディレクトリ名称
                long wdrSid = 0;
                WmlDirectoryDao directoryDao = new WmlDirectoryDao(con);
                for (int i = 0; i < DIR_TYPE.length; i++) {
                    wdrSid = mtCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                            GSConstWebmail.SBNSID_SUB_DIRECTORY,
                            sessionUserSid);

                    directoryModel.setWdrSid(wdrSid);
                    directoryModel.setWacSid(wacSaiSid);
                    directoryModel.setWdrType(DIR_TYPE[i]);

                    switch (directoryModel.getWdrType()) {
                    case GSConstWebmail.DIR_TYPE_RECEIVE:
                        directoryModel.setWdrName(gsMsg.getMessage("cmn.receive"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                        break;
                    case GSConstWebmail.DIR_TYPE_SENDED:
                        directoryModel.setWdrName(gsMsg.getMessage("wml.19"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                        break;
                    case GSConstWebmail.DIR_TYPE_NOSEND:
                        directoryModel.setWdrName(gsMsg.getMessage("wml.211"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                        break;
                    case GSConstWebmail.DIR_TYPE_DRAFT:
                        directoryModel.setWdrName(gsMsg.getMessage("cmn.draft"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                        break;
                    case GSConstWebmail.DIR_TYPE_DUST:
                        directoryModel.setWdrName(gsMsg.getMessage("cmn.trash"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                        break;
                    case GSConstWebmail.DIR_TYPE_SPAM:
                        directoryModel.setWdrName(gsMsg.getMessage("wml.212"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_NO);
                        break;
                    case GSConstWebmail.DIR_TYPE_STORAGE:
                        directoryModel.setWdrName(gsMsg.getMessage("cmn.strage"));
                        directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                        break;
                    default:
                    }

                    directoryModel.setWdrDefault(GSConstWebmail.DEF_DEFAULT);

                    directoryDao.insert(directoryModel);
                }

                //アカウントソートの並び順を登録する
                Arrays.sort(usrSids);
                for (int m = 0; m < usrSids.length; m++) {
                    accountSortDao.insertAccountSort(wacSaiSid, Integer.parseInt(usrSids[m]));
                }
                for (int idx = 0; idx < proxyUsrSids.length; idx++) {
                    if (Arrays.binarySearch(usrSids, proxyUsrSids[idx]) < 0) {
                        accountSortDao.insertAccountSort(
                                wacSaiSid, Integer.parseInt(proxyUsrSids[idx]));
                    }
                }

                //アカウントディスク使用量を登録する
                WmlAccountDiskModel acntDiskMdl = new WmlAccountDiskModel();
                acntDiskMdl.setWacSid(wacSaiSid);
                acntDiskMdl.setWdsSize(0);
                accountDiskDao.insert(acntDiskMdl);

                //アカウント受信情報を登録する
                WmlAccountRcvdataModel acntRcvdataMdl = new WmlAccountRcvdataModel();
                acntRcvdataMdl.setWacSid(wacSaiSid);
                acntRcvdataMdl.setWrdReceiveDate(null);
                accountRcvDataDao.insert(acntRcvdataMdl);

                //通知管理テーブルへデータを登録する
                WmlManageNoticeDao wmnDao = new WmlManageNoticeDao(con);
                WmlManageNoticeModel wmnMdl = new WmlManageNoticeModel();
                wmnMdl.setWacSid(wacSaiSid);
                wmnMdl.setWmnRcvfailedFlg(GSConstWebmail.WML_FAILEDNOTICE_NOTRECEIVED);
                wmnDao.insert(wmnMdl);

                //アカウント署名を登録する
                if (signModel != null) {
                    accountSignDao.insert(signModel);
                }
            }

            //アカウント使用者を登録する
            int wacType = GSConstWebmail.WAC_TYPE_USER;
            if (userKbn == WebmailCsvModel.USERKBN_GROUP) {
                wacType = GSConstWebmail.WAC_TYPE_GROUP;
            }
            accountUserDao.insert(wacSaiSid, wacType, usrSids);

            //アカウント代理人を登録する
            accountProxyUserDao.insertProxyUser(wacSaiSid, proxyUsrSids);
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
    public List<WebmailCsvModel> getWebmailList(String tempDir) throws Exception {

        WmlBiz wBiz = new WmlBiz();
        List<Cmn110FileModel> fileDataList = wBiz.getFileData(tempDir);
        String fullPath = tempDir + fileDataList.get(0).getSaveFileName();
        WebmailCsvReader csvReader = new WebmailCsvReader();
        csvReader.readCsvFile(fullPath);

        return csvReader.getWebmailList();
    }

    /**
     * <br>[機  能] アカウントの各種区分が比較対象と一致するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 区分
     * @param compareValue 比較対象
     * @return true: 比較対象と一致 false: 比較対象と一致しない
     */
    private boolean __checkKbn(String kbn, int compareValue) {
        kbn = NullDefault.getString(kbn, "");
        kbn = kbn.replaceAll("　", "");
        kbn = kbn.trim();

        return kbn.equals(String.valueOf(compareValue));
    }

    /**
     * <br>[機  能] アカウントの各種区分を数値へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 区分
     * @param defValue デフォルト値
     * @return 区分
     */
    private int __formatKbn(String kbn, int defValue) {
        if (!ValidateUtil.isNumber(NullDefault.getString(kbn, ""))) {
            return defValue;
        }

        return Integer.parseInt(kbn);
    }
}
