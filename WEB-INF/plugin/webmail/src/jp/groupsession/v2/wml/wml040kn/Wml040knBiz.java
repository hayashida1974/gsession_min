package jp.groupsession.v2.wml.wml040kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.MailBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDiskDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvdataDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvsvrDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSortDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserProxyDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterSortDao;
import jp.groupsession.v2.wml.dao.base.WmlManageNoticeDao;
import jp.groupsession.v2.wml.model.base.WmlAccountDiskModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvdataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvsvrModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;
import jp.groupsession.v2.wml.model.base.WmlManageNoticeModel;
import jp.groupsession.v2.wml.wml040.Wml040Biz;
import jp.groupsession.v2.wml.wml040.Wml040Form;
import jp.groupsession.v2.wml.wml040.Wml040ParamModel;
import jp.groupsession.v2.wml.wml040.Wml040SignModel;

/**
 * <br>[機  能] WEBメール アカウント登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml040knBiz extends Wml040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml040knBiz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 署名情報の取得に失敗
     */
    public void setInitData(Connection con, Wml040knParamModel paramMdl, RequestModel reqMdl)
    throws SQLException, IOToolsException {

        //管理者設定情報を取得
        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = admConfDao.selectAdmData();
        paramMdl.setWml040PermitKbn(admConfMdl.getWadPermitKbn());
        paramMdl.setWml040admDisk(admConfMdl.getWadDisk());
        paramMdl.setWml040admDiskSize(admConfMdl.getWadDiskSize());

        if (admConfMdl.getWadDiskComp() == GSConstWebmail.WAC_DISK_ADM_COMP) {
            //ディスク容量 強制制限あり
            paramMdl.setWml040diskSizeComp(GSConstWebmail.WAC_DISK_ADM_COMP);
        }

        paramMdl.setWml040knBiko(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getWml040biko()), ""));

        //使用者を設定
        if (paramMdl.getWml040userKbn() == Wml040knForm.USERKBN_GROUP) {
            __setGroupCombo(con, paramMdl);
        } else {
            __setUserCombo(con, paramMdl, reqMdl);
        }

        //代理人を設定
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setWml040proxyUserFlg(wmlBiz.isProxyUserAllowed(con));
        if (paramMdl.isWml040proxyUserFlg()) {
            __setProxyUserCombo(con, paramMdl, reqMdl);
        }

        //署名を設定
        String tempDir = getTempDir(reqMdl);
        paramMdl.setWml040signList(createSignCombo(reqMdl, tempDir));

        //テーマ(表示用)を設定
        paramMdl.setWml040knTheme(getThemeName(con, reqMdl, paramMdl.getWml040theme()));

        //引用符(表示用)を設定
        paramMdl.setWml040knQuotes(
                WmlBiz.getViewMailQuotes(paramMdl.getWml040quotes(), reqMdl));

        //プロバイダ(表示用)を設定
        CmnOauthDao oauthDao = new CmnOauthDao(con);
        CmnOauthModel oauthMdl = oauthDao.select(paramMdl.getWml040provider());
        if (oauthMdl != null) {
            paramMdl.setWml040knProvider(
                    OAuthBiz.getProviderName(oauthMdl.getCouProvider(), reqMdl));
        }

        //セッションユーザ情報を設定
        paramMdl.setWml040sessionUserData(reqMdl.getSmodel());

        //WEBメール管理者フラグを設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setWml040webmailAdmin(
                cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConstWebmail.PLUGIN_ID_WEBMAIL));
        //受信、送信暗号化表示用設定
        MailEncryptBiz proBiz = new MailEncryptBiz(reqMdl);
        paramMdl.setWml040knReciveEncrypt(
                proBiz.getProtocolName(paramMdl.getWml040receiveServerEncrypt()));
        paramMdl.setWml040knSendEncrypt(
                proBiz.getProtocolName(paramMdl.getWml040sendServerEncrypt()));

        //認証情報(認証アカウント名)を設定
        OAuthBiz authBiz = new OAuthBiz();
        int cotSid = authBiz.checkOAuthToken(con, 
                paramMdl.getWml040provider(), paramMdl.getWml040mailAddress(), false);
        OauthDataModel authData = authBiz.getAuthData(con, cotSid);
        boolean oauthCompFlg = (authData != null && authData.getRefreshToken() != null);
        if (oauthCompFlg) {
            paramMdl.setWml040authAccount(authData.getAccountId());
        }
        paramMdl.setWml040oauthCompFlg(oauthCompFlg);
    }

    /**
     * <br>[機  能] アカウント情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     * @return WmlAccountModel
     */
    public WmlAccountModel entryAccountData(Connection con, Wml040knParamModel paramMdl,
            MlCountMtController mtCon, int sessionUserSid, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        boolean newData = paramMdl.getWmlCmdMode() == GSConstWebmail.CMDMODE_ADD;
        int accountMode = paramMdl.getWmlAccountMode();
        int userKbn = paramMdl.getWml040userKbn();

        //アカウント情報の登録
        WmlAccountModel accountModel = new WmlAccountModel();

        accountModel.setUsrSid(sessionUserSid);
        accountModel.setWacType(GSConstWebmail.WAC_TYPE_NORMAL);
        if (accountMode == GSConstWebmail.ACCOUNTMODE_COMMON
        && userKbn == Wml040knForm.USERKBN_GROUP) {
            accountModel.setWacType(GSConstWebmail.WAC_TYPE_GROUP);
        } else {
            accountModel.setWacType(GSConstWebmail.WAC_TYPE_USER);
        }

        accountModel.setWacAccountId(paramMdl.getWml040accountId());
        accountModel.setWacName(paramMdl.getWml040name());
        accountModel.setWacAddress(paramMdl.getWml040mailAddress());

        int authType = paramMdl.getWml040authMethod();
        accountModel.setWacAuthType(authType);

        accountModel.setWacApop(GSConstWebmail.WAC_APOP_NOTUSE);
        accountModel.setWacPopbsmtp(GSConstWebmail.WAC_POPBSMTP_NO);
        if (authType == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
            //認証形式 = OAuthの場合
            accountModel.setWacReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_IMAP);

            MailBiz mailBiz = new MailBiz();
            String address = mailBiz.extractAddress(paramMdl.getWml040mailAddress());
            accountModel.setWacSendUser(address);
            accountModel.setWacReceiveUser(address);

            //OAuth認証情報を登録する
            OAuthBiz oatBiz = new OAuthBiz();
            int cotSid = oatBiz.checkOAuthToken(con, 
                    paramMdl.getWml040provider(), paramMdl.getWml040mailAddress(), false);
            OAuthBiz authBiz = new OAuthBiz();
            OauthDataModel authData = authBiz.getAuthData(con, cotSid);
            if (authData == null) {
                //存在しない場合はOAuth認証情報Modelを新規登録する
                authData = new OauthDataModel();
                authData.setCouSid(paramMdl.getWml040provider());
                authData.setAccountId(address);
                cotSid = authBiz.entryOAuthToken(con, mtCon, authData, sessionUserSid);
            }
            accountModel.setCotSid(cotSid);

        } else {
            //認証形式 = 基本認証の場合
            accountModel.setWacSendHost(paramMdl.getWml040sendServer());
            accountModel.setWacSendPort(Integer.parseInt(paramMdl.getWml040sendServerPort()));
            accountModel.setWacSendUser(paramMdl.getWml040sendServerUser());
            accountModel.setWacSendPass(paramMdl.getWml040sendServerPassword());
            accountModel.setWacSendSsl(paramMdl.getWml040sendServerEncrypt());
            accountModel.setWacReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_POP);
            accountModel.setWacReceiveHost(paramMdl.getWml040receiveServer());
            accountModel.setWacReceivePort(Integer.parseInt(paramMdl.getWml040receiveServerPort()));
            accountModel.setWacReceiveUser(paramMdl.getWml040receiveServerUser());
            accountModel.setWacReceivePass(paramMdl.getWml040receiveServerPassword());
            accountModel.setWacReceiveSsl(paramMdl.getWml040receiveServerEncrypt());

            if (paramMdl.getWml040apop() == Wml040knForm.APOP_ON) {
                accountModel.setWacApop(GSConstWebmail.WAC_APOP_USE);
            }

            if (paramMdl.getWml040popBSmtp() == Wml040knForm.POPBSMTP_ON) {
                accountModel.setWacPopbsmtp(GSConstWebmail.WAC_POPBSMTP_YES);
            }
        }

        //ディスク容量
        accountModel.setWacDisk(GSConstWebmail.WAC_DISK_UNLIMITED);
        //ディスク容量の制限が存在する場合
        if (paramMdl.getWml040diskSize() == Wml040knForm.DISKSIZE_LIMIT) {
            accountModel.setWacDisk(GSConstWebmail.WAC_DISK_LIMIT);
            accountModel.setWacDiskSize(Integer.parseInt(paramMdl.getWml040diskSizeLimit()));
        }

        //ディスク容量 特例設定
        accountModel.setWacDiskSps(GSConstWebmail.WAC_DISK_SPS_NORMAL);
        if (paramMdl.getWml040diskSps() == GSConstWebmail.WAC_DISK_SPS_SPSETTINGS) {
            accountModel.setWacDiskSps(GSConstWebmail.WAC_DISK_SPS_SPSETTINGS);
        }

        accountModel.setWacBiko(paramMdl.getWml040biko());
        accountModel.setWacOrganization(paramMdl.getWml040organization());
//        accountModel.setWacSign(paramMdl.getWml040sign());

        //署名 自動挿入
        accountModel.setWacSignAuto(paramMdl.getWml040signAuto());
        //返信時署名位置区分
        accountModel.setWacSignPointKbn(paramMdl.getWml040signPoint());
        //返信時署名表示区分
        accountModel.setWacSignDspKbn(paramMdl.getWml040receiveDsp());

        accountModel.setWacAutoto(paramMdl.getWml040autoTo());
        accountModel.setWacAutocc(paramMdl.getWml040autoCc());
        accountModel.setWacAutobcc(paramMdl.getWml040autoBcc());
        accountModel.setWacDelreceive(GSConstWebmail.WAC_DELRECEIVE_NO);
        if (paramMdl.getWml040delReceive() == Wml040knForm.DELRECEIVE_YES) {
            accountModel.setWacDelreceive(GSConstWebmail.WAC_DELRECEIVE_YES);
        }
        accountModel.setWacRereceive(GSConstWebmail.WAC_RERECEIVE_NO);
        if (paramMdl.getWml040reReceive() == Wml040knForm.RERECEIVE_YES) {
            accountModel.setWacRereceive(GSConstWebmail.WAC_RERECEIVE_YES);
        }
        accountModel.setWacTopCmd(GSConstWebmail.WAC_TOP_COMMAND_ENABLE);
        if (paramMdl.getWml040topCmd() == Wml040knForm.TOP_COMMAND_DISABLE) {
            accountModel.setWacTopCmd(GSConstWebmail.WAC_TOP_COMMAND_DISABLE);
        }
        accountModel.setWacSmtpAuth(GSConstWebmail.WAC_SMTPAUTH_NO);
        if (paramMdl.getWml040smtpAuth() == Wml040knForm.SMTPAUTH_ON) {
            accountModel.setWacSmtpAuth(GSConstWebmail.WAC_SMTPAUTH_YES);
        }
        accountModel.setWacEncodeSend(GSConstWebmail.WAC_ENCODE_SEND_ISO2022JP);
        if (paramMdl.getWml040encodeSend() == Wml040knForm.ENCODE_SEND_UNICODE) {
            accountModel.setWacEncodeSend(GSConstWebmail.WAC_ENCODE_SEND_UTF8);
        }

        accountModel.setWacAutoreceive(GSConstWebmail.MAIL_AUTO_RSV_OFF);
        if (paramMdl.getWml040autoResv() == Wml040knForm.AUTORESV_ON) {
            accountModel.setWacAutoreceive(GSConstWebmail.MAIL_AUTO_RSV_ON);
        }

        accountModel.setWacSendMailtype(GSConstWebmail.WAC_SEND_MAILTYPE_NORMAL);
        if (paramMdl.getWml040sendType() == Wml040knForm.SEND_MAIL_HTML) {
            accountModel.setWacSendMailtype(GSConstWebmail.WAC_SEND_MAILTYPE_HTML);
        }

        accountModel.setWacAutoReceiveTime(paramMdl.getWml040AutoReceiveTime());

        accountModel.setWacTheme(paramMdl.getWml040theme());
        accountModel.setWacCheckAddress(paramMdl.getWml040checkAddress());
        accountModel.setWacCheckFile(paramMdl.getWml040checkFile());
        accountModel.setWacCompressFile(paramMdl.getWml040compressFile());
        accountModel.setWacTimesent(paramMdl.getWml040timeSent());
        accountModel.setWacQuotes(paramMdl.getWml040quotes());
        accountModel.setWacAutoSaveMinute(Integer.parseInt(paramMdl.getWml040autoSaveMin()));

        //添付ファイル自動圧縮 初期値
        if (paramMdl.getWml040compressFile() == GSConstWebmail.WAC_COMPRESS_FILE_INPUT) {
            if (paramMdl.getWml040compressFileDef() == Wml040Form.COMPRESS_FILE_DEF_YES) {
                accountModel.setWacCompressFileDef(GSConstWebmail.WAC_COMPRESS_FILE_DEF_COMPRESS);
            } else {
                accountModel.setWacCompressFileDef(
                        GSConstWebmail.WAC_COMPRESS_FILE_DEF_NOTCOMPRESS);
            }
        } else {
            accountModel.setWacCompressFileDef(GSConstWebmail.WAC_COMPRESS_FILE_DEF_NOSET);
        }
        //時間差送信 初期値
        if (paramMdl.getWml040timeSent() == GSConstWebmail.WAC_TIMESENT_INPUT) {
            if (paramMdl.getWml040timeSentDef() == Wml040Form.TIMESENT_DEF_AFTER) {
                accountModel.setWacTimesentDef(GSConstWebmail.WAC_TIMESENT_DEF_LATER);
            } else {
                accountModel.setWacTimesentDef(GSConstWebmail.WAC_TIMESENT_DEF_IMM);
            }
        } else {
            accountModel.setWacTimesentDef(GSConstWebmail.WAC_TIMESENT_DEF_NOSET);
        }

        //アカウント採番取得
        int wacSaiSid = (int) mtCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                                                  GSConstWebmail.SBNSID_SUB_ACCOUNT,
                                                  sessionUserSid);
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountUserDao accountUserDao = new WmlAccountUserDao(con);
        WmlAccountSignDao signDao = new WmlAccountSignDao(con);
        WmlDirectoryModel directoryModel = new WmlDirectoryModel();
        WmlAccountSortDao accountSortDao = new WmlAccountSortDao(con);
        WmlAccountDiskDao wadDao = new WmlAccountDiskDao(con);

        int wdrSid = 0;

        //代理人が許可されているかを取得
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserAllowed = wmlBiz.isProxyUserAllowed(con);

        //新規登録
        if (newData) {

            accountModel.setWacSid(wacSaiSid);
            //アカウント単位で設定
            if (paramMdl.getWml040PermitKbn() == GSConstWebmail.PERMIT_ACCOUNT) {
                //管理者設定情報を取得
                WmlAdmConfDao wacDao = new WmlAdmConfDao(con);
                WmlAdmConfModel wacMdl = wacDao.selectAdmData();

                //『自動受信しない』
                if (paramMdl.getWml040autoResv() == GSConstWebmail.MAIL_AUTO_RSV_OFF) {
                    accountModel.setWacAutoReceiveTime(wacMdl.getWadAutoReceiveTime());
                }
            }

            accountDao.insertAccount(accountModel, accountMode);

            GsMessage gsMsg = new GsMessage(reqMdl);
            WmlDirectoryDao directoryDao = new WmlDirectoryDao(con);
            for (int i = 1; i <= 7; i++) {
                wdrSid = (int) mtCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                        GSConstWebmail.SBNSID_SUB_DIRECTORY,
                        sessionUserSid);

                directoryModel.setWacSid(wacSaiSid);
                directoryModel.setWdrDefault(GSConstWebmail.DEF_DEFAULT);
                if (i == GSConstWebmail.DIR_TYPE_RECEIVE) {
                    directoryModel.setWdrName(gsMsg.getMessage("cmn.receive"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                } else if (i == GSConstWebmail.DIR_TYPE_SENDED) {
                    directoryModel.setWdrName(gsMsg.getMessage("wml.19"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                } else if (i == GSConstWebmail.DIR_TYPE_NOSEND) {
                    directoryModel.setWdrName(gsMsg.getMessage("wml.211"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                } else if (i == GSConstWebmail.DIR_TYPE_DRAFT) {
                    directoryModel.setWdrName(gsMsg.getMessage("cmn.draft"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                } else if (i == GSConstWebmail.DIR_TYPE_DUST) {
                    directoryModel.setWdrName(gsMsg.getMessage("cmn.trash"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                } else if (i == GSConstWebmail.DIR_TYPE_SPAM) {
                    directoryModel.setWdrName(gsMsg.getMessage("wml.212"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_NO);
                } else if (i == GSConstWebmail.DIR_TYPE_STORAGE) {
                    directoryModel.setWdrName(gsMsg.getMessage("cmn.strage"));
                    directoryModel.setWdrView(GSConstWebmail.DSP_VIEW_OK);
                }
                directoryModel.setWdrSid(wdrSid);
                directoryModel.setWdrType(i);
                directoryDao.insert(directoryModel);
            }

            //フィルター適用順を登録する
            WmlFilterSortDao wfsDao = new WmlFilterSortDao(con);
            wfsDao.setAcuntInsFilterSort(wacSaiSid);

            //アカウントの並び順を登録する
            if (accountMode == GSConstWebmail.ACCOUNTMODE_NORMAL
                    || accountMode == GSConstWebmail.ACCOUNTMODE_PSNLSETTING) {
                accountSortDao.insertAccountSort(wacSaiSid, sessionUserSid);
                accountUserDao.insert(wacSaiSid, accountModel.getWacType(),
                                        new String[] {String.valueOf(sessionUserSid)});

            } else {
                //アカウントユーザ情報の登録
                if (accountModel.getWacType() == GSConstWebmail.WAC_TYPE_GROUP) {
                    accountUserDao.insert(wacSaiSid, accountModel.getWacType(),
                            paramMdl.getWml040userKbnGroup());
                } else if (accountModel.getWacType() == GSConstWebmail.WAC_TYPE_USER) {
                    accountUserDao.insert(wacSaiSid, accountModel.getWacType(),
                            paramMdl.getWml040userKbnUser());
                }

                if (userKbn == Wml040knForm.USERKBN_GROUP) {
                    accountSortDao.insertAccountSortGp(wacSaiSid, paramMdl.getWml040userKbnGroup());

                } else if (userKbn == Wml040knForm.USERKBN_USER) {
                    accountSortDao.insertAccountSortUsr(wacSaiSid, paramMdl.getWml040userKbnUser());
                }
            }

            //代理人のアカウント並び順
            accountSortDao.insertAccountSortUsr(wacSaiSid, paramMdl.getWml040proxyUser());

            //ディスク使用量の新規登録
            WmlAccountDiskModel useDiskMdl = new WmlAccountDiskModel();
            useDiskMdl.setWacSid(wacSaiSid);
            //新規登録時は使用サイズを0にセット
            useDiskMdl.setWdsSize(0);
            wadDao.insert(useDiskMdl);

            //アカウント受信情報を登録する
            WmlAccountRcvdataDao accountRcvDataDao = new WmlAccountRcvdataDao(con);
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

            //アカウント_受信サーバ情報の新規登録
            __insertAccountRcvSvr(con, wacSaiSid, false);

        //編集登録
        } else {

            int wacSid = paramMdl.getWmlAccountSid();

            //前回登録時のアカウント情報を取得
            WmlAccountModel beforeAcctData = accountDao.select(wacSid);

            accountModel.setWacSid(wacSid);

            if (paramMdl.getWml040autoResv() == GSConstWebmail.MAIL_AUTO_RSV_OFF
                    && paramMdl.getWml040PermitKbn() == GSConstWebmail.PERMIT_ACCOUNT) {

                //『自動受信しない』かつアカウント単位で設定
                accountModel.setWacAutoReceiveTime(beforeAcctData.getWacAutoReceiveTime());
            }

            accountDao.updateAccount(accountModel, accountMode);

            //受信サーバ情報が変更された場合はアカウント_受信サーバ情報をクリアする
            if (authType != GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                if (!accountModel.getWacReceiveHost().equals(beforeAcctData.getWacReceiveHost())
                || accountModel.getWacReceivePort() != beforeAcctData.getWacReceivePort()
                || !accountModel.getWacReceiveUser().equals(beforeAcctData.getWacReceiveUser())
                || !accountModel.getWacReceivePass().equals(beforeAcctData.getWacReceivePass())) {
                    __insertAccountRcvSvr(con, wacSaiSid, true);
                }
            }

            if (accountMode == GSConstWebmail.ACCOUNTMODE_COMMON) {
                //アカウント使用者の削除
                accountUserDao.delete(wacSid);

                //アカウントユーザ情報の登録
                if (accountModel.getWacType() == GSConstWebmail.WAC_TYPE_GROUP) {
                    accountUserDao.insert(wacSid, accountModel.getWacType(),
                            paramMdl.getWml040userKbnGroup());

                } else if (accountModel.getWacType() == GSConstWebmail.WAC_TYPE_USER) {
                    accountUserDao.insert(wacSid, accountModel.getWacType(),
                            paramMdl.getWml040userKbnUser());
                } else {
                    accountUserDao.insert(wacSid, accountModel.getWacType(),
                                        new String[]{String.valueOf(sessionUserSid)});
                }

                if (userKbn == Wml040knForm.USERKBN_GROUP) {
                    accountSortDao.delAccountSortGp(wacSid, paramMdl.getWml040userKbnGroup());
                    accountSortDao.updateAccountSort(wacSid, paramMdl.getWml040userKbnGroup());

                } else if (userKbn == Wml040knForm.USERKBN_USER) {
                    accountSortDao.delAccountSortUsr(wacSid, paramMdl.getWml040userKbnUser());
                    accountSortDao.updateAccountSortUsr(wacSid, paramMdl.getWml040userKbnUser(),
                            wacSid);
                }
            }

            //既存の代理人を削除する
            if (proxyUserAllowed) {
                WmlAccountUserProxyDao proxyUserDao = new WmlAccountUserProxyDao(con);
                proxyUserDao.deleteProxyUser(wacSid);

                String[] proxyUser = paramMdl.getWml040proxyUser();
                accountSortDao.delAccountSortProxyUser(wacSid, proxyUser);
                if (proxyUser != null && proxyUser.length > 0) {
                    accountSortDao.updateAccountSortUsr(wacSid, proxyUser, wacSid);
                }
            }

            //既存の署名情報を削除する
            signDao.delete(accountModel.getWacSid());

        }

        //代理人を設定
        if (proxyUserAllowed) {
            WmlAccountUserProxyDao proxyUserDao = new WmlAccountUserProxyDao(con);
            proxyUserDao.insertProxyUser(accountModel.getWacSid(), paramMdl.getWml040proxyUser());
        }

        //署名情報を登録する
        String tempDir = getTempDir(reqMdl);
        Wml040SignModel signData = loadSignModel(reqMdl, tempDir);
        for (WmlAccountSignModel signDetail : signData.getSignList()) {
            signDetail.setWacSid(accountModel.getWacSid());
            if (signDetail.getWsiNo() == 1) {
                signDetail.setWsiDef(GSConstWebmail.WSI_DEF_DEFAULT);
            } else {
                signDetail.setWsiDef(GSConstWebmail.WSI_DEF_NORMAL);
            }
            signDao.insert(signDetail);
        }

        return accountModel;
    }

    /**
     * <br>[機  能] テーマ名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param theme テーマ
     * @return テーマ名称
     * @throws SQLException SQL実行時例外
     */
    public String getThemeName(Connection con, RequestModel reqMdl, int theme) throws SQLException {
        CmnThemeDao themeDao = new CmnThemeDao(con);
        CmnThemeModel themeData = themeDao.select(theme);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String themeName = gsMsg.getMessage("cmn.notset");
        if (themeData != null) {
            themeName = themeData.getCtmName();
        }
        return themeName;
    }


    /**
     * <br>[機  能] アカウント_受信サーバ情報の新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param clear true:新規登録前に削除を行う false:削除を行わない
     * @throws SQLException SQL実行時例外
     */
    private void __insertAccountRcvSvr(Connection con, int wacSid, boolean clear)
    throws SQLException {

        //アカウント_受信サーバ情報の新規登録
        WmlAccountRcvsvrModel rcvSvrData = new WmlAccountRcvsvrModel();
        rcvSvrData.setWacSid(wacSid);
        //新規登録時は件数、サイズを0にセット
        rcvSvrData.setWrsReceiveCount(0);
        rcvSvrData.setWrsReceiveSize(0);
        WmlAccountRcvsvrDao rcvSvrDao = new WmlAccountRcvsvrDao(con);
        rcvSvrDao.insert(rcvSvrData);
    }

    /**
     * <br>[機  能] グループコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    private void __setGroupCombo(Connection con, Wml040ParamModel paramMdl)
            throws SQLException {
        List<LabelValueBean> selectGroupCombo = new ArrayList<LabelValueBean>();

        String[] selectGrpSid = paramMdl.getWml040userKbnGroup();
        if (selectGrpSid == null) {
            selectGrpSid = new String[0];
        }
        Arrays.sort(selectGrpSid);

        GroupBiz grpBiz = new GroupBiz();
        ArrayList<GroupModel> gpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : gpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                    String.valueOf(grpMdl.getGroupSid()));
            if (Arrays.binarySearch(selectGrpSid, String.valueOf(grpMdl.getGroupSid())) >= 0) {
                selectGroupCombo.add(label);
            }
        }

        paramMdl.setUserKbnGroupSelectCombo(selectGroupCombo);
    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setUserCombo(Connection con, Wml040ParamModel paramMdl, RequestModel reqMdl)
            throws SQLException {

        String[] selectUserSid = paramMdl.getWml040userKbnUser();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }
        Arrays.sort(selectUserSid);

        GsMessage gsMsg = new GsMessage(reqMdl);
        paramMdl.setUserKbnUserSelectCombo(createUserCombo(con, selectUserSid, gsMsg));
    }

    /**
     * <br>[機  能] 代理人 ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setProxyUserCombo(Connection con, Wml040ParamModel paramMdl,
            RequestModel reqMdl)
                    throws SQLException {

        String[] selectUserSid = paramMdl.getWml040proxyUser();

        paramMdl.setProxyUserSelectCombo(createProxySelectUserCombo(con, selectUserSid, reqMdl));
    }

}
