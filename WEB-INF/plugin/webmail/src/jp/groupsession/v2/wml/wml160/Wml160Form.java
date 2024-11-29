package jp.groupsession.v2.wml.wml160;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.wml030.Wml030Form;

/**
 * <br>[機  能] WEBメール アカウントインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160Form extends Wml030Form {

    /** プラグインID */
    private String wml160pluginId__ = GSConstWebmail.PLUGIN_ID_WEBMAIL;
    /** 初期表示 */
    private int wml160initFlg__ = 0;
    /** 上書き */
    private int wml160updateFlg__ = 0;

    /** 添付ファイル(コンボで選択中) */
    private String[] wml160selectFiles__ = null;
    /** ファイルコンボ */
    private  List<LabelValueBean> wml160FileLabelList__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリ
     * @return エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl, String tempDir)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        String eprefix = "inputFile.";

        WmlBiz wBiz = new WmlBiz();
        List<Cmn110FileModel> fileDataList = wBiz.getFileData(tempDir);

        //ファイルが存在しない場合
        if (fileDataList == null || fileDataList.isEmpty()) {
            msg = new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("cmn.capture.file"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {

            boolean csvError = false;
            String fileName = null;
            //複数選択エラー
            if (fileDataList.size() > 2) {
                msg = new ActionMessage("error.input.notfound.file",
                        gsMsg.getMessage("cmn.capture.file"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                fileName = fileDataList.get(0).getFileName();
                String strExt = StringUtil.getExtension(fileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    msg = new ActionMessage("error.select.required.text",
                            gsMsg.getMessage("cmn.csv.file.format"));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }
            String fullPath = tempDir + fileDataList.get(0).getSaveFileName();
            WebmailCsvReader csvReader = new WebmailCsvReader();
            if (!csvError) {
                if (csvReader.isOverRowCount(new File(fullPath),
                        Encoding.WINDOWS_31J,
                        AbstractCsvRecordReader.MAX_ROW_COUNT_X5)) {
                    msg =
                            new ActionMessage("error.over.row.csvdata",
                                    gsMsg.getMessage("cmn.capture.file"),
                                    String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT_X5));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.over.row.csvdata");

                    csvError = true;
                }
            }

            //入力チェック
            if (!csvError) {
                csvReader.readCsvFile(fullPath);
                List<WebmailCsvModel> accountList = csvReader.getWebmailList();
                Map<String, String> accountNameMap = new HashMap<String, String>();
                WmlAccountDao accountDao = new WmlAccountDao(con);
                CmnOauthDao oauthDao = new CmnOauthDao(con);

                WmlAdmConfModel wadMdl = wBiz.getAdminConf(con); // 管理者設定
                String[] tldList = wBiz.getTldList(wadMdl);

                int accountCount = 0;
                for (WebmailCsvModel accountData : accountList) {
                    int errorCnt = errors.size();
                    String rowNum = String.valueOf(accountData.getRowNum());
                    String rowStr = gsMsg.getMessageVal0("cmn.line2", rowNum);
                    //アカウントID入力チェック
                    String accountUid = accountData.getAccountId();
                    String accountMsg = gsMsg.getMessage(GSConstWebmail.TEXT_ACCOUNT_ID);

                    GSValidateWebmail.validateTextBoxInput(errors, accountUid,
                            "acntUid" + rowNum,
                            rowStr + accountMsg,
                            GSConstWebmail.MAXLEN_ACCOUNT_ID, true);
                    if (errorCnt == errors.size()) {
                        if (!GSValidateUtil.isOtheridFormat(accountUid)) {
                            //アカウントＩＤフォーマットチェック
                            msg = new ActionMessage("error.input.format.text",
                                    rowStr + accountMsg);
                            StrutsUtil.addMessage(errors, msg,
                                    "acntUid" + rowNum + "error.input.format.text");
                        } else if (accountNameMap.containsKey(accountUid)) {
                            //アカウントID重複チェック
                            msg = new ActionMessage("error.select.dup.list2",
                                    rowStr + accountMsg,
                                    accountNameMap.get(accountUid) + accountMsg);
                            StrutsUtil.addMessage(errors, msg,
                                    "acntUid" + rowNum + "error.select.dup.list2");
                        } else {
                            accountNameMap.put(accountUid, rowStr);
                            if (wml160updateFlg__ != 1) {
                                //同じアカウントIDが登録済みかをチェック
                                if (accountDao.existAccount(accountUid, 0)) {
                                    msg = new ActionMessage("error.input.timecard.exist",
                                            rowStr + accountMsg,
                                            accountNameMap.get(accountUid) + accountMsg);
                                    StrutsUtil.addMessage(errors, msg,
                                            "acntUid" + rowNum + "error.input.timecard.exist");
                                }
                            }
                        }
                    }

                    GSValidateWebmail.validateTextBoxInput(errors, accountData.getAccountName(),
                            "acntName" + rowNum,
                            rowStr + accountMsg,
                            GSConstWebmail.MAXLEN_ACCOUNT, true);

                    //認証形式
                    String authType = accountData.getAuthType();
                    String acntAuthType = "acntAuthType";
                    String msgAuthType = gsMsg.getMessage("wml.307");

                    if (StringUtil.isNullZeroString(authType)) {
                        //未入力チェック
                        msg = new ActionMessage("error.select.required.text", rowStr + msgAuthType);
                        errors.add(acntAuthType + "error.select.required.text", msg);

                    } else if (!GSValidateUtil.isNumber(authType)) {
                        //半角数字チェック
                        String msgKey = "error.input.number.hankaku";
                        msg = new ActionMessage(msgKey, rowStr + msgAuthType);
                        errors.add(acntAuthType + msgKey, msg);

                    } else if (!(Integer.parseInt(authType) == GSConstWebmail.WAC_AUTH_TYPE_NORMAL)
                            && !(Integer.parseInt(authType) == GSConstWebmail.WAC_AUTH_TYPE_OAUTH)) {

                        msg = new ActionMessage("error.input.notvalidate.data", rowStr + msgAuthType);
                        errors.add(acntAuthType + "error.input.notvalidate.data", msg);
                    }

                    //プロバイダ
                    String provider = accountData.getProvider();
                    String acntProvider = "acntProvider";
                    String msgProvider = gsMsg.getMessage("wml.308");
                    String[] providerList = new String[]
                            {String.valueOf(GSConstCommon.COU_PROVIDER_GOOGLE),
                                    String.valueOf(GSConstCommon.COU_PROVIDER_MICROSOFT)};

                    if (!(StringUtil.isNullZeroString(authType))
                            && GSValidateUtil.isNumber(authType)
                            && Integer.parseInt(authType) == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {

                        if (StringUtil.isNullZeroString(provider)) {
                            //未入力チェック
                            msg = new ActionMessage("error.input.required.text", rowStr + msgProvider);
                            errors.add(acntProvider + "error.input.required.text", msg);

                        } else if (!GSValidateUtil.isNumber(provider)) {
                            //半角数字チェック
                            String msgKey = "error.input.number.hankaku";
                            msg = new ActionMessage(msgKey, rowStr + msgProvider);
                            errors.add(acntProvider + msgKey, msg);

                        } else if (Arrays.binarySearch(providerList, provider) < 0) {
                            //正しい値か
                            msg = new ActionMessage("error.input.notvalidate.data",
                                    gsMsg.getMessage("wml.308"));
                            errors.add("provider" + "error.input.notvalidate.data", msg);
                        } else if (GSValidateUtil.isNumber(provider)) {
                            //存在チェック
                            int oauth = Integer.parseInt(provider);
                            boolean result =  oauthDao.existProvider(oauth);
                            if (!result) {
                                msg = new ActionMessage("error.nothing.selected", msgProvider);
                                errors.add(acntProvider + "error.nothing.selected", msg);
                            }
                        }
                    }

                    //メールアドレス入力チェック
                    GSValidateWebmail.validateMailWithNameTextBoxInput(
                            errors, accountData.getMail(),
                            "acntAddress" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_ADDRESS),
                            GSConstWebmail.MAXLEN_ACCOUNT_ADDRESS, true);

                    MailEncryptBiz protocolBiz = new MailEncryptBiz();

                    if (!(StringUtil.isNullZeroString(authType))
                            && GSValidateUtil.isNumber(authType)
                            && Integer.parseInt(authType) == GSConstWebmail.WAC_AUTH_TYPE_NORMAL) {
                        //メール受信サーバー名入力チェック
                        GSValidateWebmail.validateTextBoxInput(errors, accountData.getMailRsvServer(),
                                "acntReceiveServer" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SERVER_RECEIVE),
                                GSConstWebmail.MAXLEN_ACCOUNT_SERVER, true);

                        //メール受信サーバー名ポート番号入力チェック
                        GSValidateWebmail.validateNumber(errors, accountData.getMailRsvPort(),
                                "acntReceivePort" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_PORT_RECEIVE),
                                GSConstWebmail.MAXLEN_ACCOUNT_PORT);

                        //メール受信サーバ暗号化入力チェック
                        if (!StringUtil.isNullZeroString(accountData.getMailRsvSsl())) {
                            int rsvEcryptType = NullDefault.getInt(accountData.getMailRsvSsl(), -1);
                            if (!protocolBiz.isExistProtocol(rsvEcryptType)) {
                                msg =  new ActionMessage("error.input.format.file",
                                        rowStr
                                        + gsMsg.getMessage("wml.297")
                                        + " "
                                        + gsMsg.getMessage("wml.299"),
                                        gsMsg.getMessage("main.man340.10"));
                                eprefix = "notReceiveServerEncrypt" + rowNum;
                                StrutsUtil.addMessage(errors, msg, eprefix);
                            }
                        }

                        //メール受信サーバーユーザ名入力チェック
                        GSValidateWebmail.validateTextBoxInput(errors, accountData.getMailRsvUser(),
                                "acntReceiveServerUser" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_USER_RECEIVE),
                                GSConstWebmail.MAXLEN_ACCOUNT_SERVER_USER, true);

                        //メール受信サーバーパスワード入力チェック
                        GSValidateWebmail.validateTextBoxInput(errors, accountData.getMailRsvPass(),
                                "acntReceiveServerPassword" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_PASS_RECEIVE),
                                GSConstWebmail.MAXLEN_ACCOUNT_SERVER_PASS, true);
                    }

                    //メール自動受信入力チェック
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getMailAutoRsv(),
                            "acntAutoReceive" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_MAIL_AUTO_RSV),
                            GSConstWebmail.MAIL_AUTO_RSV_OFF, GSConstWebmail.MAIL_AUTO_RSV_ON);

                    //メール自動受信間隔入力チェック
                    int receiveTimeCnt = errors.size();
                    if (String.valueOf(GSConstWebmail.MAIL_AUTO_RSV_ON).equals(
                            accountData.getMailAutoRsv())) {
                        String autoRsvTime = accountData.getMailAutoRsvTime();
                        GSValidateWebmail.validateTextBoxInput(
                                errors, autoRsvTime,
                                "acntAutoReceiveTime" + rowNum,
                                rowStr + gsMsg.getMessage("wml.auto.receive.time"),
                                2, false);
                        if (!StringUtil.isNullZeroString(autoRsvTime)
                                && receiveTimeCnt == errors.size()) {
                            int intAutoRsvTime = Integer.parseInt(autoRsvTime);
                            if (intAutoRsvTime != WebmailCsvModel.AUTO_RSVTIME_5
                                    && intAutoRsvTime != WebmailCsvModel.AUTO_RSVTIME_10
                                    && intAutoRsvTime != WebmailCsvModel.AUTO_RSVTIME_30
                                    && intAutoRsvTime != WebmailCsvModel.AUTO_RSVTIME_60) {
                                msg  = new ActionMessage("error.input.notvalidate.data",
                                        rowStr + gsMsg.getMessage("wml.auto.receive.time"));
                                StrutsUtil.addMessage(errors, msg,
                                        "acntAutoReceiveTime" + rowNum
                                        + "error.input.notvalidate.data");
                            }
                        }
                    }

                    int errSize = errors.size();

                    if (!(StringUtil.isNullZeroString(authType))
                            && GSValidateUtil.isNumber(authType)
                            && Integer.parseInt(authType) == GSConstWebmail.WAC_AUTH_TYPE_NORMAL) {
                        //メール送信サーバ名入力チェック
                        GSValidateWebmail.validateTextBoxInput(errors, accountData.getMailSndServer(),
                                "acntSendServer" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SERVER_SEND),
                                GSConstWebmail.MAXLEN_ACCOUNT_SERVER, true);

                        //メール送信ポート番号入力チェック
                        GSValidateWebmail.validateNumber(errors, accountData.getMailSndPort(),
                                "acntSendPort" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_PORT_SEND),
                                GSConstWebmail.MAXLEN_ACCOUNT_PORT);

                        //メール送信サーバ暗号化入力チェック
                        if (!StringUtil.isNullZeroString(accountData.getMailSndSsl())) {
                            int sendEcryptType = NullDefault.getInt(accountData.getMailSndSsl(), -1);
                            if (!protocolBiz.isExistProtocol(sendEcryptType)) {
                                msg =  new ActionMessage("error.input.format.file",
                                        rowStr
                                        + gsMsg.getMessage("wml.298")
                                        + " "
                                        + gsMsg.getMessage("wml.299"),
                                        gsMsg.getMessage("main.man340.10"));
                                eprefix = "notSendServerEncrypt" + rowNum;
                                StrutsUtil.addMessage(errors, msg, eprefix);
                            }
                        }


                        //SMTP認証
                        GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                                accountData.getSmtpNinsyo(),
                                "acntSmtpNinsyo" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SMTP_NINSYO),
                                GSConstWebmail.WAC_SMTPAUTH_NO, GSConstWebmail.WAC_SMTPAUTH_YES);

                        if (errSize == errors.size()) {
                            //SMTP認証：認証する場合のエラーチェック
                            if (String.valueOf(GSConstWebmail.WAC_SMTPAUTH_YES).equals(
                                    accountData.getSmtpNinsyo())) {

                                //メール送信サーバユーザ名入力チェック
                                GSValidateWebmail.validateTextBoxInput(errors,
                                        accountData.getMailSndUser(),
                                        "acntSendServerUser" + rowNum,
                                        rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_USER_SEND),
                                        GSConstWebmail.MAXLEN_ACCOUNT_SERVER_USER, true);

                                //メール送信サーバパスワード入力チェック
                                GSValidateWebmail.validateTextBoxInput(errors,
                                        accountData.getMailSndPass(),
                                        "acntSendServerPassword" + rowNum,
                                        rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_PASS_SEND),
                                        GSConstWebmail.MAXLEN_ACCOUNT_SERVER_PASS, true);
                            }
                        }

                        errSize = errors.size();
                    }

                    //ディスク容量最大値入力チェック
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getDiskCapa(),
                            "acntDiskCapa" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_DISK),
                            GSConstWebmail.WAC_DISK_UNLIMITED, GSConstWebmail.WAC_DISK_LIMIT);

                    if (errSize == errors.size()
                            && String.valueOf(GSConstWebmail.WAC_DISK_LIMIT).equals(
                                    accountData.getDiskCapa())) {
                        GSValidateWebmail.validateNumber(errors, accountData.getDiskCapaSize(),
                                "acntDiskSize" + rowNum,
                                rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_DISK),
                                GSConstWebmail.MAXLEN_ACCOUNT_DISK);
                    }

                    //ディスク容量 特例設定入力チェック
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getDiskCapaSps(),
                            "acntDiskCapaSps" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_DISK)
                            + " " + gsMsg.getMessage("cmn.spsetting"),
                            GSConstWebmail.WAC_DISK_SPS_NORMAL,
                            GSConstWebmail.WAC_DISK_SPS_SPSETTINGS);

                    //備考入力チェック
                    GSValidateWebmail.validateTextarea(errors, accountData.getBiko(),
                            "acntBiko" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_BIKO),
                            GSConstWebmail.MAXLEN_ACCOUNT_BIKO,
                            false);

                    //組織名入力チェック
                    GSValidateWebmail.validateTextBoxInput(errors, accountData.getOrganization(),
                            "acntOrganization" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_ORGANIZE),
                            GSConstWebmail.MAXLEN_ACCOUNT_ORGANIZE, false);

                    //署名入力チェック
                    GSValidateWebmail.validateTextarea(errors, accountData.getSign(),
                            "acntSign" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SIGN),
                            GSConstWebmail.MAXLEN_ACCOUNT_SIGN,
                            false);

                    //返信時署名位置
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getSignAuto(),
                            "signAuto" + rowNum,
                            rowStr + gsMsg.getMessage("wml.sign.auto"),
                            GSConstWebmail.WAC_SIGN_AUTO_INSERT,
                            GSConstWebmail.WAC_SIGN_AUTO_NO);

                    //返信時署名位置
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getSignPoint(),
                            "signPoint" + rowNum,
                            rowStr + gsMsg.getMessage("wml.sign.point"),
                            GSConstWebmail.SIGN_POINT_TOP, GSConstWebmail.SIGN_POINT_BOTTOM);

                    //返信時署名表示
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getSignDsp(),
                            "signDsp" + rowNum,
                            rowStr + gsMsg.getMessage("wml.receive.sign"),
                            GSConstWebmail.SIGN_RECEIVE_NOT_DSP, GSConstWebmail.SIGN_RECEIVE_DSP);

                    //自動TO入力チェック
                    GSValidateWebmail.validateMailTextBoxInput(errors, accountData.getAutoTo(),
                            "acntAutoTo" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_AUTO_TO),
                            GSConstWebmail.MAXLEN_ACCOUNT_ADDRESS, false,
                            reqMdl, tldList);

                    //自動CC入力チェック
                    GSValidateWebmail.validateMailTextBoxInput(errors, accountData.getAutoCc(),
                            "acntAutoCc" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_AUTO_CC),
                            GSConstWebmail.MAXLEN_ACCOUNT_ADDRESS, false,
                            reqMdl, tldList);

                    //自動BCC入力チェック
                    GSValidateWebmail.validateMailTextBoxInput(errors, accountData.getAutoBcc(),
                            "acntAutoBcc" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_AUTO_BCC),
                            GSConstWebmail.MAXLEN_ACCOUNT_ADDRESS, false,
                            reqMdl, tldList);

                    //受信時削除
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getRsvDelete(),
                            "acntRsvDelete" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_RSV_DELETE),
                            GSConstWebmail.WAC_DELRECEIVE_NO, GSConstWebmail.WAC_DELRECEIVE_YES);

                    //受信済みでも受信
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getRsvOk(),
                            "acntRsvOk" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_RSV_OK),
                            GSConstWebmail.WAC_RERECEIVE_NO, GSConstWebmail.WAC_RERECEIVE_YES);

                    //APOP
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getApop(),
                            "acntApop" + rowNum,
                            rowStr + GSConstWebmail.TEXT_APOP,
                            GSConstWebmail.WAC_APOP_NOTUSE, GSConstWebmail.WAC_APOP_USE);

                    //TOPコマンド
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getTopCmd(),
                            "acntTopCmd" + rowNum,
                            rowStr + GSConstWebmail.TEXT_TOP_CMD,
                            GSConstWebmail.WAC_TOP_COMMAND_ENABLE,
                            GSConstWebmail.WAC_TOP_COMMAND_DISABLE);

                    //送信前POP認証
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getBeSndPopNinsyo(),
                            "acntBeSndPopNinsyo" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SEND_POP),
                            GSConstWebmail.WAC_APOP_NOTUSE, GSConstWebmail.WAC_APOP_USE);

                    //送信文字コード
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getSndWordCode(),
                            "acntSndWordCode" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SEND_WORDCODE),
                            GSConstWebmail.WAC_ENCODE_SEND_ISO2022JP,
                            GSConstWebmail.WAC_ENCODE_SEND_UTF8);

                    //送信メール形式
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getSndMailType(),
                            "acntSndMailPatn" + rowNum,
                            rowStr + gsMsg.getMessage(GSConstWebmail.TEXT_SEND_TYPE),
                            GSConstWebmail.WAC_SEND_MAILTYPE_NORMAL,
                            GSConstWebmail.WAC_SEND_MAILTYPE_HTML);

                    //宛先の確認
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getCheckAddress(),
                            "checkAddress" + rowNum,
                            rowStr + gsMsg.getMessage("wml.238"),
                            GSConstWebmail.WAC_CHECK_ADDRESS_NOCHECK,
                            GSConstWebmail.WAC_CHECK_ADDRESS_CHECK);

                    //添付ファイルの確認
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getCheckFile(),
                            "checkFile" + rowNum,
                            rowStr + gsMsg.getMessage("wml.239"),
                            GSConstWebmail.WAC_CHECK_FILE_NOCHECK,
                            GSConstWebmail.WAC_CHECK_FILE_CHECK);

                    //添付ファイル自動圧縮
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getCompressFile(),
                            "compressFile" + rowNum,
                            rowStr + gsMsg.getMessage("wml.240"),
                            new int[] {
                                    GSConstWebmail.WAC_COMPRESS_FILE_NOTCOMPRESS,
                                    GSConstWebmail.WAC_COMPRESS_FILE_COMPRESS,
                                    GSConstWebmail.WAC_COMPRESS_FILE_INPUT},
                            false);

                    //添付ファイル自動圧縮 初期値
                    if (NullDefault.getString(accountData.getCompressFile(), "0").equals("2")) {
                        GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                                NullDefault.getString(accountData.getCompressFileDef(), "未入力"),
                                "compressFileDef" + rowNum,
                                rowStr + gsMsg.getMessage("wml.240")
                                + " " + gsMsg.getMessage("ntp.10"),
                                0, 1, true);
                    }

                    //時間差送信
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            accountData.getTimeSent(),
                            "timeSent" + rowNum,
                            rowStr + gsMsg.getMessage("wml.241"),
                            new int[] {
                                    GSConstWebmail.WAC_TIMESENT_NOSET,
                                    GSConstWebmail.WAC_TIMESENT_LATER,
                                    GSConstWebmail.WAC_TIMESENT_INPUT},
                            false);

                    //時間差送信 初期値
                    if (NullDefault.getString(accountData.getTimeSent(), "0").equals("2")) {
                        GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                                NullDefault.getString(accountData.getTimeSentDef(), "未入力"),
                                "timeSentDef" + rowNum,
                                rowStr + gsMsg.getMessage("wml.241")
                                + " " + gsMsg.getMessage("ntp.10"),
                                0, 1, true);
                    }

                    //テーマ
                    GSValidateWebmail.validateCsvAccountKbn(reqMdl, errors,
                            accountData.getTheme(),
                            "theme" + rowNum,
                            rowStr + gsMsg.getMessage("cmn.theme"),
                            new int[] {GSConstWebmail.WAC_THEME_NOSET,
                                    GSConstWebmail.WAC_THEME_THEME1,
                                    GSConstWebmail.WAC_THEME_THEME2,
                                    GSConstWebmail.WAC_THEME_THEME3,
                                    GSConstWebmail.WAC_THEME_THEME4,
                                    GSConstWebmail.WAC_THEME_THEME5,
                                    GSConstWebmail.WAC_THEME_THEME6,
                                    GSConstWebmail.WAC_THEME_THEME7,
                                    GSConstWebmail.WAC_THEME_THEME8,
                                    GSConstWebmail.WAC_THEME_THEME9,
                                    GSConstWebmail.WAC_THEME_THEME10,
                                    GSConstWebmail.WAC_THEME_THEME11,
                                    GSConstWebmail.WAC_THEME_THEME12,
                                    GSConstWebmail.WAC_THEME_THEME13,
                                    GSConstWebmail.WAC_THEME_THEME14,
                                    GSConstWebmail.WAC_THEME_THEME15
                    },
                            false
                            );

                    //引用符
                    GSValidateWebmail.validateCsvAccountKbn(reqMdl, errors,
                            accountData.getQuotes(),
                            "quotes" + rowNum,
                            rowStr + gsMsg.getMessage("cmn.quotes"),
                            new int[] {GSConstWebmail.WAC_QUOTES_DEF,
                                    GSConstWebmail.WAC_QUOTES_NONE,
                                    GSConstWebmail.WAC_QUOTES_2,
                                    GSConstWebmail.WAC_QUOTES_3,
                                    GSConstWebmail.WAC_QUOTES_4,
                                    GSConstWebmail.WAC_QUOTES_5,
                    },
                            false
                            );

                    //自動保存
                    boolean minuteFlg = false;
                    String autoSave = accountData.getAutoSave();
                    if(StringUtil.isNullZeroString(autoSave)) {
                        minuteFlg = true;
                    }
                    if (!minuteFlg) {
                        List<String> minuteList = Arrays.asList(GSConstWebmail.CSV_MINUTE_VALUE);
                        if (minuteList.contains(autoSave)) {
                            minuteFlg = true;
                        }
                    }
                    if (!minuteFlg) {
                        msg = new ActionMessage("error.input.notvalidate.data",
                                rowStr + gsMsg.getMessage("wml.302"));
                        errors.add("error.input.notvalidate.data", msg);
                    }

                    //使用ユーザ/グループ区分
                    String userKbn = accountData.getUserKbn();
                    boolean errorUserKbn = false;
                    errSize = errors.size();
                    GSValidateWebmail.validateCsvAccountFlg(reqMdl, errors,
                            userKbn,
                            "userKbn" + rowNum,
                            rowStr
                            + gsMsg.getMessage(
                                    "cmn.use.user.group.kbn"),
                            WebmailCsvModel.USERKBN_USER,
                            WebmailCsvModel.USERKBN_GROUP,
                            true);
                    errorUserKbn = errSize != errors.size();

                    //使用ユーザ/グループ
                    boolean user1 = StringUtil.isNullZeroString(accountData.getUser1());
                    boolean user2 = StringUtil.isNullZeroString(accountData.getUser2());
                    boolean user3 = StringUtil.isNullZeroString(accountData.getUser3());
                    boolean user4 = StringUtil.isNullZeroString(accountData.getUser4());
                    boolean user5 = StringUtil.isNullZeroString(accountData.getUser5());

                    String text_useUser = gsMsg.getMessage("cmn.use.user.group");
                    if (user1 && user2 && user3 && user4 && user5) {
                        //未入力チェック
                        String chkFlgName = "acntUseUser" + rowNum;
                        eprefix = chkFlgName + ".";
                        String targetJp = rowStr + text_useUser;
                        msg = new ActionMessage("error.input.required.text",
                                targetJp);
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

                    } else {
                        if (!errorUserKbn) {
                            //ユーザ1
                            __validateCsvUserId(errors, con, rowStr, rowNum, accountData.getUser1(),
                                    "acntUser1", text_useUser, 1, userKbn, errSize);
                            errSize = errors.size();

                            //ユーザ2
                            __validateCsvUserId(errors, con, rowStr, rowNum, accountData.getUser2(),
                                    "acntUser2", text_useUser, 2, userKbn, errSize);
                            errSize = errors.size();

                            //ユーザ3
                            __validateCsvUserId(errors, con, rowStr, rowNum, accountData.getUser3(),
                                    "acntUser3", text_useUser, 3, userKbn, errSize);
                            errSize = errors.size();

                            //ユーザ4
                            __validateCsvUserId(errors, con, rowStr, rowNum, accountData.getUser4(),
                                    "acntUser4", text_useUser, 4, userKbn, errSize);
                            errSize = errors.size();

                            //ユーザ5
                            __validateCsvUserId(errors, con, rowStr, rowNum, accountData.getUser5(),
                                    "acntUser5", text_useUser, 5, userKbn, errSize);
                            errSize = errors.size();
                        }

                        String text_proxyUser = gsMsg.getMessage("cmn.proxyuser");
                        //代理人1
                        __validateCsvUserId(errors, con, rowStr, rowNum,
                                accountData.getProxyUser1(),
                                "proxyUser1", text_proxyUser, 1, "0",
                                errSize);
                        errSize = errors.size();
                        //代理人2
                        __validateCsvUserId(errors, con, rowStr, rowNum,
                                accountData.getProxyUser2(),
                                "proxyUser2", text_proxyUser, 2, "0",
                                errSize);
                        errSize = errors.size();
                        //代理人3
                        __validateCsvUserId(errors, con, rowStr, rowNum,
                                accountData.getProxyUser3(),
                                "proxyUser3", text_proxyUser, 3, "0",
                                errSize);
                        errSize = errors.size();
                        //代理人4
                        __validateCsvUserId(errors, con, rowStr, rowNum,
                                accountData.getProxyUser4(),
                                "proxyUser4", text_proxyUser, 4, "0",
                                errSize);
                        errSize = errors.size();
                        //代理人5
                        __validateCsvUserId(errors, con, rowStr, rowNum,
                                accountData.getProxyUser5(),
                                "proxyUser5", text_proxyUser, 5, "0",
                                errSize);
                        errSize = errors.size();
                    }
                    if (errorCnt == errors.size()) {
                        accountCount++;
                    }
                }

                if (accountCount == 0) {
                    String msgText = gsMsg.getMessage("cmn.capture.file");
                    msg = new ActionMessage("search.notfound.data", msgText);
                    StrutsUtil.addMessage(errors, msg, "inputFile.search.notfound.data");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] ユーザID/グループIDのチェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param con コネクション
     * @param rowStr 行番号(文字列)
     * @param rowNum 行番号
     * @param value チェック対象の値
     * @param name 項目名
     * @param nameJp 項目名(エラー表示用)
     * @param num 項目番号
     * @param userKbn ユーザ区分(グループ or ユーザ)
     * @param errSize 入力チェック前のエラー件数
     * @throws SQLException SQL実行時例外
     */
    private void __validateCsvUserId(ActionErrors errors, Connection con,
            String rowStr, String rowNum,
            String value, String name, String nameJp,
            int num, String userKbn,
            int errSize)
                    throws SQLException {
        GSValidateWebmail.validateCsvUserId(errors, value,
                name + rowNum,
                rowStr + nameJp,
                num, userKbn);
        if (errSize == errors.size()) {
            GSValidateWebmail.validateCsvUserIdExist(errors,
                    value,
                    name + rowNum,
                    rowStr + nameJp,
                    con, num,
                    userKbn);
        }
    }

    /**
     * <p>wml160FileLabelList を取得します。
     * @return wml160FileLabelList
     */
    public List<LabelValueBean> getWml160FileLabelList() {
        return wml160FileLabelList__;
    }
    /**
     * <p>wml160FileLabelList をセットします。
     * @param wml160FileLabelList wml160FileLabelList
     */
    public void setWml160FileLabelList(List<LabelValueBean> wml160FileLabelList) {
        wml160FileLabelList__ = wml160FileLabelList;
    }
    /**
     * <p>wml160pluginId を取得します。
     * @return wml160pluginId
     */
    public String getWml160pluginId() {
        return wml160pluginId__;
    }
    /**
     * <p>wml160pluginId をセットします。
     * @param wml160pluginId wml160pluginId
     */
    public void setWml160pluginId(String wml160pluginId) {
        wml160pluginId__ = wml160pluginId;
    }
    /**
     * <p>wml160selectFiles を取得します。
     * @return wml160selectFiles
     */
    public String[] getWml160selectFiles() {
        return wml160selectFiles__;
    }
    /**
     * <p>wml160selectFiles をセットします。
     * @param wml160selectFiles wml160selectFiles
     */
    public void setWml160selectFiles(String[] wml160selectFiles) {
        wml160selectFiles__ = wml160selectFiles;
    }
    /**
     * <p>wml160initFlg を取得します。
     * @return wml160initFlg
     */
    public int getWml160initFlg() {
        return wml160initFlg__;
    }
    /**
     * <p>wml160initFlg をセットします。
     * @param wml160initFlg wml160initFlg
     */
    public void setWml160initFlg(int wml160initFlg) {
        wml160initFlg__ = wml160initFlg;
    }
    /**
     * <p>wml160updateFlg を取得します。
     * @return wml160updateFlg
     */
    public int getWml160updateFlg() {
        return wml160updateFlg__;
    }
    /**
     * <p>wml160updateFlg をセットします。
     * @param wml160updateFlg wml160updateFlg
     */
    public void setWml160updateFlg(int wml160updateFlg) {
        wml160updateFlg__ = wml160updateFlg;
    }
}