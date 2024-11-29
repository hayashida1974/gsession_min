package jp.groupsession.v2.wml.restapi.accounts.mails.send;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.biz.WmlTempFileBiz;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailServerConnectException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.exception.WmlTempDirNoneException;
import jp.groupsession.v2.wml.exception.WmlTempFileNameException;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestTempFileBiz;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] WEBメール 新規メール送信用ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsSendPostBiz {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsSendPostBiz.class);

    /** RestApiコンテキスト */
    private final RestApiContext ctx__;
    /** パラメータ */
    private WmlAccountsMailsSendPostParamModel param__;
    /** GSコンテキスト */
    private GSContext context__;
    /** テンポラリディレクトリ */
    private String tempDir__;

    /** メール送信ビジネスロジック */
    private WmlMailSendBiz sender__;

    /** テンポラリディレクトリに配置されている添付ファイル名 */
    private List<String> tempFileNameList__ = new ArrayList<String>();

    /** 実行結果 */
    private WmlAccountsMailsResultModel result__;

    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsSendPostBiz#result__
     */
    public WmlAccountsMailsResultModel getResult() {
        return result__;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param param WEBメールラベル一覧 取得用モデル
     * @param ctx RestApiコンテキスト
     * @throws TempFileException 添付ファイル操作例外
     */
    public WmlAccountsMailsSendPostBiz(WmlAccountsMailsSendPostParamModel param,
        RestApiContext ctx, GSContext context, String tempDir) throws TempFileException {

        ctx__ = ctx;
        param__ = param;
        context__ = context;
        tempDir__ = tempDir;
        sender__ = __getMailBiz();
    }

    /**
     * <br>[機  能] 送信処理の実行
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     * @throws IllegalAccessException 実行結果作成時例外
     * @throws InvocationTargetException 実行結果作成時例外
     * @throws NoSuchMethodException 実行結果作成時例外
     * @throws IOException 添付ファイル登録時例外
     * @throws IOToolsException 添付ファイル登録時例外
     */
    protected void _execute() throws SQLException, IllegalAccessException,
        InvocationTargetException, NoSuchMethodException, IOException, IOToolsException {

        //オートコミットの設定
        boolean defAutoCommit = ctx__.getCon().getAutoCommit();
        ctx__.getCon().setAutoCommit(false);

        //メール送信に必要な情報の取得
        Connection con = ctx__.getCon();
        int userSid = ctx__.getRequestUserSid();
        String appRootPath = ctx__.getAppRootPath();
        String domain = ctx__.getRequestModel().getDomain();
        Locale locale = ctx__.getRequestModel().getLocale();

        //メール送信の実行
        WmlSendResultModel result = null;
        GsMessage gsMsg = new GsMessage(locale);
        log__.info("メール送信処理の開始");
        
        try {
            result = sender__.executeMail(
                con, context__, userSid, appRootPath, tempDir__, domain, locale);
        } catch (WmlTempDirNoneException e) {
            //テンポラリディレクトリが存在しない場合
            throw new RuntimeException(
                ctx__.getMessageResources().getMessage(
                    "error.input.njapan.text3", gsMsg.getMessage("cmn.main.temp.path")), e);
        } catch (WmlMailSizeOverException e) {
            //メールのサイズが上限を超えていた場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(WmlEnumReasonCode.FAILED_SEND_MAIL_SIZE);
            apiException.setMessageResource(
                "error.sizeover.sendmail", e.getMaxSizeText());
            throw apiException;
        } catch (WmlMailServerConnectException e) {
            //メールサーバへアクセスできなかった場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(WmlEnumReasonCode.FAILED_SEND_SERVER_ACCESS);
            apiException.setMessageResource(
                "error.connect.failed.mailserver",
                gsMsg.getMessage("wml.80"),
                gsMsg.getMessage("wml.80"));
            throw apiException;
        } catch(WmlDiskSizeOverException e) {
            //ディスクの容量制限を超えていた場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(WmlEnumReasonCode.FAILED_SEND_DISK_CAPACITY);
            apiException.setMessageResource(
                "errors.free.msg", e.getErrorMsg());
            throw apiException;
        } catch (WmlTempFileNameException e) {
            //添付ファイル名が不正の場合
            RestApiException apiException = new RestApiException(e);
            apiException.setReasonCode(EnumError.PARAM_OTHER_INVALID);
            apiException.setMessageResource(
                "errors.free.msg", e.getMessage());
            throw apiException;
        } finally {
            if (result == null) {
                //参照メールとテンプレートに紐づいているファイルをディレクトリから削除
                WmlRestTempFileBiz.deleteTempFile(tempDir__, tempFileNameList__);
            }
        }

        log__.info("メール送信処理の終了");

        //結果の取得
        SmtpSendModel sendData = null;
        if (result != null) {
            sendData = result.getSendMailData();
        }

        //API実行結果の作成
        __setApiResult(result);

        //ログ出力
        __outputResultLog(sendData);

        //オートコミットの設定
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);
    }

    /**
     * <br>[機  能] APIの実行結果の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws Exception 
     */
    private void __outputResultLog(SmtpSendModel sendData)  {

        if (sendData == null) {
            return;
        }
        //ログ出力
        GsMessage gsMsg = new GsMessage(ctx__.getRequestModel());
        String value = null;
        RequestModel reqMdl = ctx__.getRequestModel();
        if (sendData.getSendPlanDate() != null) {
            value = gsMsg.getMessage("wml.211") + "：";
            UDate sendPlanDate = sendData.getSendPlanDate();
            value += " [" + gsMsg.getMessage("wml.260") + "]"
                    + UDateUtil.getSlashYYMD(sendPlanDate)
                    + " " + UDateUtil.getSeparateHM(sendPlanDate)
                    + " ";
        } else {
            value = gsMsg.getMessage("cmn.mail.send") + "：";
        }
        value += "[" + gsMsg.getMessage("cmn.subject") + "]" + sendData.getSubject();
        value += " [From]" + NullDefault.getString(sendData.getFrom(), "");
        value += " [To]" + NullDefault.getString(sendData.getTo(), "");
        value += " [Cc]" + NullDefault.getString(sendData.getCc(), "");
        value += " [Bcc]" + NullDefault.getString(sendData.getBcc(), "");
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.outPutApiLog(reqMdl, ctx__.getCon(), ctx__.getRequestUserSid(),
                "RESTAPI_MAIL_ADD_SEND", gsMsg.getMessage("cmn.sent"),
                GSConstLog.LEVEL_TRACE, StringUtil.trimRengeString(value, 3000));
    }

    /**
     * <br>[機  能] APIの実行結果の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws Exception 
     */
    private void __setApiResult(WmlSendResultModel resultModel) throws SQLException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (resultModel == null || resultModel.getSendMailData() == null) {
            return;
        }

        int wacSid = (int) resultModel.getSendMailData().getWacSid();
        long mailNum = resultModel.getMailNum();
        
        WmlRestMailDataBiz biz = new WmlRestMailDataBiz();
        Connection con = ctx__.getCon();
        long[] mailSidArray = new long[] {mailNum};
        String appRootPath = ctx__.getAppRootPath();

        List<WmlAccountsMailsResultModel> resultList = biz.getMailsResult(con, wacSid, mailSidArray, appRootPath);
        for (WmlAccountsMailsResultModel mdl : resultList) {
            //送信したメール1件の情報を取得
            result__ = mdl;
            break;
        }

    }

    /**
     * <br>[機  能] 送信メールのテンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     */
    public void deleteSendTempDir(String tempDir) {
        if (tempDir != null && !tempDir.isEmpty()) {
            File dir = new File(tempDir);
            if (dir.getName().equals("sendmail")) {
                String parentDir = dir.getParent();
                if (parentDir != null && !parentDir.isEmpty()) {
                    IOTools.deleteDir(parentDir);
                }
            }
        }
    }

    /**
     * <br>[機  能] 指定されたメールアドレス一覧の結合を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param addressList メールアドレス一覧
     * @return 結合したメールアドレス
     */
    public String joinAddress(List<String> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            return null;
        }

        String joinAddress = addressList.get(0);
        for (int idx = 1; idx < addressList.size(); idx++) {
            joinAddress += ", " + addressList.get(idx);
        }

        return joinAddress;
    }

    /**
     * <br>[機  能] 送信メール情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param param WEBメールラベル一覧 取得用モデル
     * @param ctx RestApiコンテキスト
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル操作時例外
     * @throws IOException 添付ファイル操作時例外
     * @throws TempFileException 添付ファイル操作時例外
     */
    private WmlMailSendBiz __getMailBiz() throws TempFileException {
        
        try {
            //参照メールの添付ファイルをテンポラリディレクトリに配置
            if (param__.getProcType() == GSConstWebmail.SEND_TYPE_FORWARD
                || param__.getProcType() == GSConstWebmail.SEND_TYPE_EDIT) {
                __setRefarenceFileData();
            }
            
            //テンプレートの添付ファイルをテンポラリディレクトリに配置
            __setTemplateFileData();
            
            String appRootPath = (String) context__.get(GSContext.APP_ROOT_PATH);
            Connection con = ctx__.getCon();
            WmlRestAccountBiz accountBiz = new WmlRestAccountBiz();
            int wacSid = accountBiz.getWmlAccountSid(con, param__.getAccountId());
            int userSid = ctx__.getRequestUserSid();

            int compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_NOTCOMPRESS;
            if (param__.getCompressFileFlg() == GSConstWebmail.RESTAPI_COMPRESS_YES) {
                compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_COMPRESS;
            }
            
            WmlMailSendBiz mailBiz = WmlMailSendBiz.builder()
                .setTo(WmlMailSendBiz.joinAddress(param__.getToArray()))
                .setCc(WmlMailSendBiz.joinAddress(param__.getCcArray()))
                .setBcc(WmlMailSendBiz.joinAddress(param__.getBccArray()))
                .setSubject(param__.getSubjectText())
                .setContent(param__.getBodyText())
                .setWacSid(wacSid)
                .setSendMessageNum(param__.getRefarenceSid())
                .setSendMailType(param__.getProcType())
                .setHtmlMail(false)
                .setTimeSentType(param__.getSendPlanType())
                .setSendPlanDate(param__.getSendPlanDateTime())
                .setCompressFileType(compressFileFlg)
                .build(con, context__, appRootPath, tempDir__, userSid);

            return mailBiz;
        } catch (SQLException e) {
            //ハンドリングしない例外のため、RuntimeExceptionとしてthrowする
            throw new RuntimeException(e);
        } catch (IOToolsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** 
     * 参照メールの添付ファイルをテンポラリディレクトリに展開する
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     * @throws SQLException 
    */
    private void __setRefarenceFileData() throws
    TempFileException, IOException, IOToolsException, SQLException {
        WmlTempFileBiz tempBinBiz = new WmlTempFileBiz();
        tempBinBiz.deproyAttachimentRefarenceFileData(
            ctx__.getCon(),
            ctx__.getRequestModel(),
            param__.getRefarenceSid(),
            param__.getRefarenceBinSidArray(),
            ctx__.getAppRootPath(),
            tempDir__);

        }

    /**
     * テンプレートの添付ファイルをテンポラリディレクトリに展開する
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     * @throws SQLException
     */
    private void __setTemplateFileData() throws
        TempFileException, IOException, IOToolsException, SQLException {
        WmlTempFileBiz tempBinBiz = new WmlTempFileBiz();
        tempBinBiz.deproyAttachimentTemplateFileData(
            ctx__.getCon(),
            ctx__.getRequestModel(),
            param__.getTemplateSid(),
            param__.getTemplateBinSidArray(),
            ctx__.getAppRootPath(),
            tempDir__);
    }

     /**
     * <br>[機  能] yyyy/MM/dd hh:mm形式の文字列を、yyyyMMddhhmm00に変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param addressList 宛先アドレス一覧
     * @return 複数のアドレスを結合した文字列
     */
    protected static String getFormatedDate(String date) {

        return date.replaceAll("/", "")
            .replace(":", "")
            .replace(" ", "") + "00";

    }

}
