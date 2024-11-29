package jp.groupsession.v2.wml.biz;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.mail.MailUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.MailBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.WmlTempfileDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.ErrorBinfModel;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDiskDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserProxyDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAccessConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlHeaderDataDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelRelationDao;
import jp.groupsession.v2.wml.dao.base.WmlLogCountDao;
import jp.groupsession.v2.wml.dao.base.WmlMailBodyDao;
import jp.groupsession.v2.wml.dao.base.WmlMailSendplanDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.dao.base.WmlSendaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlUidlDao;
import jp.groupsession.v2.wml.model.WmlMailDeleteModel;
import jp.groupsession.v2.wml.model.WmlMailModel;
import jp.groupsession.v2.wml.model.WmlReceiveServerModel;
import jp.groupsession.v2.wml.model.base.AccountDataModel;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountDiskModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlHeaderDataModel;
import jp.groupsession.v2.wml.model.base.WmlLabelRelationModel;
import jp.groupsession.v2.wml.model.base.WmlLogCountModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.pop3.Pop3Receive;
import jp.groupsession.v2.wml.pop3.Pop3Server;
import jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel;
import jp.groupsession.v2.wml.smtp.WmlSmtpSender;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;
import jp.groupsession.v2.wml.util.WmlConfigBundle;
import jp.groupsession.v2.wml.util.WmlUtil;

/**
 * <br>[機  能] WEBメールプラグインで使用される共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlBiz {

    /** エンコードテキスト */
    private static final String ENCODETEXT__ = "?=";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlBiz.class);

    /**
     * <br>[機  能] 新着メールの読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param mtCon 採番コントローラ
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param ds DataSource
     * @param msgResource MessageResources
     * @param domain ドメイン
     * @throws Exception メールの受信に失敗
     */
    public void readNewMail(int accountSid, MlCountMtController mtCon,
                            int userSid, String appRootPath, String tempDir,
                            DataSource ds,
                            MessageResources msgResource,
                            String domain)
    throws Exception {

        Connection con = null;
        Pop3Server popServer = null;
        Pop3Receive receive = null;

        try {
            con = GroupSession.getConnection(domain, 1000);
            //アカウント情報を取得する
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(accountSid);
            accountDao = null;

            WmlReceiveServerModel receiveModel
                = createReceiveServerData(con, appRootPath, accountData);

            JDBCUtil.closeConnection(con);
            con = null;

            //メールの受信を行う
            popServer = new Pop3Server();

            receive = new Pop3Receive();

            log__.info(domain + ":アカウント[" + accountData.getWacName() + "] 受信開始");

            Pop3ReceiveModel pop3Model = new Pop3ReceiveModel();
            pop3Model.setMtCon(mtCon);
            pop3Model.setWacSid(accountSid);
            pop3Model.setAccountString(receiveModel.getAccountString());
            pop3Model.setAppRootPath(appRootPath);
            pop3Model.setUserSid(userSid);

            receive.receive(popServer, receiveModel, pop3Model, tempDir, ds, msgResource, domain);

            log__.info(domain + ":アカウント[" + accountData.getWacName() + "] 受信終了");
        } finally {
            JDBCUtil.closeConnection(con);
            con = null;
            popServer = null;
            receive = null;
        }
    }

    /**
     * <br>[機  能] 指定したファイルをメール添付ファイルへ登録する。
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイル本体は保存用ディレクトリにコピー、
     *              ファイル情報はDBに登録する
     * <br>[備  考]
     * @param con コネクション
     * @param fileDataList ファイル情報
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param mailNum メッセージ番号
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<Long> insertBinInfo(
        Connection con,
        List<WmlMailFileModel> fileDataList,
        String appRootPath,
        MlCountMtController cntCon,
        long mailNum,
        int userSid,
        UDate now) throws TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        List<Long> binList = cmnBiz.insertBinInfoForWebmail(
                con, appRootPath, cntCon, userSid, now, fileDataList, mailNum);

        return binList;
    }
    /**
     * <br>[機  能] 添付ファイルを全て登録に失敗した場合のエラーメッセージファイルを登録する
     * <br>[解  説] ファイル本体の保存先を振り分ける
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param fileDataList ファイルデータリスト
     * @param mailNum メール番号
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long insertBinInfoError(
            Connection con,
            String appRootPath,
            MlCountMtController cntCon,
            int userSid,
            UDate now,
            List<WmlMailFileModel> fileDataList,
            long mailNum) throws TempFileException {

        try {

            //データのinsert、update前にバイナリーSIDを採番する(OIDレプリケーション対策)
            String fileExtension = null;

            ErrorBinfModel binfMdl = new ErrorBinfModel();

            WmlTempfileModel tempFileModel = new WmlTempfileModel();
            tempFileModel.setWmdMailnum(mailNum);
            tempFileModel.setWtfAuid(userSid);
            tempFileModel.setWtfAdate(now);
            tempFileModel.setWtfEuid(userSid);
            tempFileModel.setWtfEdate(now);

            //バイナリーSID採番

            long wtfSid = cntCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                                                GSConstWebmail.SBNSID_SUB_BINARY,
                                                userSid);

            tempFileModel.setWtfSid(wtfSid);
            tempFileModel.setWtfFileName(binfMdl.getBinFileName());
            tempFileModel.setWtfFilePath("");


            for (WmlMailFileModel fileData : fileDataList) {
                binfMdl.addErrorFileName(fileData.getFileName());
            }
            binfMdl.createErrorMessage();
            fileExtension = StringUtil.getExtension(tempFileModel.getWtfFileName());
            if (fileExtension != null
            && fileExtension.length() > GSConstWebmail.MAXLEN_WTF_FILE_EXTENSION) {
                log__.warn("ファイル拡張子の桁数調整: " + fileExtension);
                fileExtension
                    = fileExtension.substring(0, GSConstWebmail.MAXLEN_WTF_FILE_EXTENSION);
            }
            tempFileModel.setWtfFileExtension(fileExtension);

            tempFileModel.setWtfFileSize(binfMdl.getBinFileSize());
            tempFileModel.setWtfJkbn(GSConst.JTKBN_TOROKU);
            tempFileModel.setWtfHtmlmail(GSConstWebmail.TEMPFILE_HTMLMAIL_NORMAL);
            tempFileModel.setWtfCharset(null);

            con.setAutoCommit(false);

            ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

            tempFileUtil.insertBinInfoErrorForWebmail(con,
                    appRootPath,
                    cntCon,
                    tempFileModel,
                    binfMdl);


            return wtfSid;

        } catch (SQLException  e) {
            log__.error("添付ファイルの登録に失敗しました。", e);
            throw new TempFileException("添付ファイルの登録に失敗しました。", e);
        }
    }


    /**
     * <br>[機  能] BLOBフィールド(Objectを格納)の読み込みを行う
     * <br>[解  説]
     * <br>[備  考] 取得できない場合エラーログがwarnレベルで記録されます。
     * @param rs ResultSet
     * @param fieldName フィールド名
     * @return BLOBフィールドの情報
     * @throws Exception BLOBフィールドの読み込みに失敗。エラー時はNUllを返す。
     */
    public Object readBlobField(ResultSet rs, String fieldName)
    throws Exception {

        Object value = null;
        Blob feedData = rs.getBlob(fieldName);

        if (feedData != null) {
            ObjectInputStream ois = null;

            try {
                ois = new ObjectInputStream(feedData.getBinaryStream());
                value = ois.readObject();
            } catch (Exception e) {
                log__.warn("BLOBフィールドの読み込みに失敗", e);
                return value;
            } finally {
                if (ois != null) {
                    ois.close();
                }
            }
        }

        return value;
    }

    /**
     * <br>[機  能] アカウントコンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param adMdlList アカウントリスト
     * @return ArrayList (in LabelValueBean)  アカウントコンボ
     */
    public List<LabelValueBean> getAcntCombo(RequestModel reqMdl,
                                            List<AccountDataModel> adMdlList) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.select.plz"), "-1"));
        for (int i = 0; i < adMdlList.size(); i++) {
            labelList.add(
               new LabelValueBean(adMdlList.get(i).getAccountName(),
                       String.valueOf(adMdlList.get(i).getAccountSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] ラベルコンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param lbMdlList ラベルリスト
     * @return ArrayList (in LabelValueBean)  ラベルコンボ
     */
    public List<LabelValueBean> getLbCombo(RequestModel reqMdl, List<LabelDataModel> lbMdlList) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        for (int i = 0; i < lbMdlList.size(); i++) {
            labelList.add(
               new LabelValueBean(lbMdlList.get(i).getLabelName(),
                       String.valueOf(lbMdlList.get(i).getLabelSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 表示開始日から前3年のコンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param year 基準年
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    public List<LabelValueBean> getYearList(RequestModel reqMdl, int year) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("-　", "-1"));
        for (int i = year - 3; i <= year; i++) {
            labelList.add(
                new LabelValueBean(gsMsg.getMessage(
                        "cmn.year", new String[] {String.valueOf(i)}), String.valueOf(i)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public List<LabelValueBean> getMonthList(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        int month = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("-　", "-1"));
        for (int i = 0; i < 12; i++) {
            labelList.add(
                new LabelValueBean(month + gsMsg.getMessage("cmn.month"),
                                    String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 日コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public List<LabelValueBean> getDayList(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        int day = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("-　", "-1"));
        for (int i = 0; i < 31; i++) {
            labelList.add(
                new LabelValueBean(day + gsMsg.getMessage("cmn.day"), String.valueOf(day)));
            day++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 検索キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param keyword キーワード
     * @return List in String
     */
    public List<String> setKeyword(String keyword) {
        List < String > keywordList = new ArrayList < String >();
        String searchKey = StringUtil.substitute(keyword, "　", " ");
        StringTokenizer st = new StringTokenizer(searchKey, " ");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (!StringUtil.isNullZeroString(str)) {
                keywordList.add(str);
            }
        }
        return keywordList;
    }

    /**
     * <br>[機  能] メール自動受信スレッド最大数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return メール自動受信スレッド最大数
     */
    public static synchronized int getMaxReceiveThreadCount(String appRootPath) {
        return getConfValue(appRootPath,
                            GSConstWebmail.MAILCONF_RECEIVE_THREAD_MAXCOUNT,
                            GSConstWebmail.RECEIVE_THREAD_MAXCOUNT_DEFAULT);
    }

    /**
     * <br>[機  能] 一度に受信できるメールの最大件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return メール自動受信スレッド最大数
     */
    public static synchronized int getMaxReceiveMailCount(String appRootPath) {
        return getConfValue(appRootPath,
                            GSConstWebmail.MAILCONF_RECEIVE_MAXCOUNT,
                            GSConstWebmail.RECEIVE_MAXCOUNT_DEFAULT);
    }

    /**
     * <br>[機  能] メール情報一括削除スレッド最大数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return メール自動受信スレッド最大数
     */
    public static synchronized int getMaxDeleteThreadCount(String appRootPath) {
        return getConfValue(appRootPath,
                            GSConstWebmail.MAILCONF_DELETE_THREAD_MAXCOUNT,
                            GSConstWebmail.DELETE_THREAD_MAXCOUNT_DEFAULT);
    }

    /**
     * <br>[機  能] WEBメール設定ファイルの設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param confKey 設定値のキー
     * @param defValue デフォルト値
     * @return 設定値
     */
    public static synchronized int getConfValue(String appRootPath, String confKey, int defValue) {
        String confValue = WmlConfigBundle.getValue(confKey);

        if (StringUtil.isNullZeroString(confValue)) {
            try {
                WmlConfigBundle.readConfig(appRootPath);
                confValue = WmlConfigBundle.getValue(confKey);
            } catch (IOException e) {
                log__.error("WEBメール設定ファイルの読み込みに失敗", e);
            }
        }

        if (!StringUtil.isNullZeroString(confValue)) {
            return Integer.parseInt(confValue);
        }

        return defValue;
    }

    /**
     * <br>[機  能] ファイルコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return ファイルコンボ
     * @throws Exception 実行時例外
     */
    public List<LabelValueBean> getFileCombo(String tempDir) throws Exception {

        ArrayList<LabelValueBean> fileCombo = new ArrayList<LabelValueBean>();
        List<Cmn110FileModel> fileDataList = getFileData(tempDir);
        if (fileDataList != null && !fileDataList.isEmpty()) {
            for (Cmn110FileModel fileData : fileDataList) {
                fileCombo.add(new LabelValueBean(fileData.getFileName(),
                                                fileData.getSaveFileName()));
            }
        }

        return fileCombo;
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイル情報
     * @throws Exception 実行時例外
     */
    public List<Cmn110FileModel> getFileData(String tempDir) throws Exception {

        List<Cmn110FileModel> fileDataList = new ArrayList<Cmn110FileModel>();
        List<String> fileNameList = IOTools.getFileNames(tempDir);

        if (fileNameList != null) {
            for (String fileName : fileNameList) {
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                fileDataList.add((Cmn110FileModel) fObj);
            }
        }

        return fileDataList;
    }
    /**
     * <br>[機  能] メール添付ファイルの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param mailNum メッセージ番号
     * @throws Exception メール添付ファイルの削除に失敗
     */
    public synchronized void deleteMailTempFile(Connection con, String appRootPath, long[] mailNum)
    throws Exception {
        if (mailNum != null && mailNum.length > 0) {

            //添付ファイルの実体を削除
            WmlTempfileDao tempFileDao = new WmlTempfileDao(con);
            List<Long> wtfSidList = tempFileDao.getWtfSid(mailNum);
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.deleteFileForWebmail(con, appRootPath, wtfSidList);

            //添付情報レコードを削除
            tempFileDao.delete(mailNum);
        }
    }

    /**
     * <br>[機  能] 指定されたアドレスを InternetAddress オブジェクトに構文解析する
     * <br>[解  説]
     * <br>[備  考]
     * @param address アドレス
     * @return InternetAddress オブジェクト
     * @throws AddressException 構文解析に失敗
     */
    public static InternetAddress[] parseAddress(String address) throws AddressException {
        return MailBiz.parseAddress(address);
    }

    /**
     * <br>[機  能] 指定されたアドレスを InternetAddress オブジェクトに構文解析する
     * <br>[解  説] 個人名をエンコードする
     * <br>[備  考]
     * @param address アドレス
     * @param encode 文字コード
     * @return InternetAddress オブジェクト
     * @throws AddressException 構文解析に失敗
     * @throws UnsupportedEncodingException 個人名のエンコーディングに失敗
     */
    public static InternetAddress[] parseAddressPlus(String address, String encode)
    throws AddressException, UnsupportedEncodingException {
        return MailBiz.parseAddressPlus(address, encode);
    }

    /**
     * <br>[機  能] メールアドレスの解析を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @return メールアドレス一覧
     * @throws AddressException メールアドレスの解析に失敗
     */
    public static List<String> getAddressList(String address)
    throws AddressException {
        return getAddressList(parseAddress(address));
    }
    /**
     *
     * <br>[機  能] InternetAddress型を文字列化
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @return アドレス文字列
     */
    public static String convertIAdress2String(InternetAddress address) {
        StringBuilder formatedAddress = new StringBuilder("");
        if (address == null) {
            return "";
        }

        boolean personal = !StringUtil.isNullZeroString(address.getPersonal());
        if (personal) {
            formatedAddress.append(MailBiz.formatPersonal(address.getPersonal()));
            formatedAddress.append(" ");
        }
        if (!StringUtil.isNullZeroString(address.getAddress())) {
            if (personal) {
                formatedAddress.append("<");
            }
            formatedAddress.append(address.getAddress());
            if (personal) {
                formatedAddress.append(">");
            }
        }
        return formatedAddress.toString();

    }

    /**
     * <br>[機  能] メールアドレスの解析を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @return メールアドレス一覧
     */
    public static List<String> getAddressList(Address[] address) {
        List<String> addressList = new ArrayList<String>();
        if (address != null) {
            for (Address adr : address) {
                addressList.add(adr.toString());
            }
        }

        return addressList;
    }

    /**
     * <br>[機  能] アカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return アカウント名
     * @throws SQLException SQL実行時例外
     */
    public String getAccountName(Connection con, int wacSid)
    throws SQLException {
        WmlAccountDao accountDao = new WmlAccountDao(con);
        return accountDao.getAccountName(wacSid);
    }

    /**
     * <br>[機  能] アカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdrSid ディレクトリSID
     * @return アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public int getAccountSid(Connection con, long wdrSid)
    throws SQLException {
        WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
        return dirDao.getAccountSid(wdrSid);
    }

    /**
     * <br>[機  能] ディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param wdrType ディレクトリ種別
     * @return ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public long getDirectorySid(Connection con, int wacSid, int wdrType)
    throws SQLException {
        WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
        return dirDao.getDirSid(wacSid, wdrType);
    }

    /**
     * <br>[機  能] 指定したアカウントのディスク使用量を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return アカウントのディスク使用量
     * @throws SQLException SQL実行時例外
     */
    public long getUseDiskSize(Connection con, int wacSid) throws SQLException {
        WmlAccountDiskDao accountDiskDao = new WmlAccountDiskDao(con);
        long useDiskSize = accountDiskDao.getUseDiskSize(wacSid);
        accountDiskDao = null;
        return useDiskSize;
    }

    /**
     * <br>[機  能] 指定したアカウントのディスク容量上限を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return アカウントのディスク容量上限
     * @throws SQLException SQL実行時例外
     */
    public int getDiskLimitSize(Connection con, int wacSid) throws SQLException {
        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = admConfDao.selectAdmData();
        int diskLimitSize = getDiskLimitSize(con, wacSid, admConfMdl);
        admConfMdl = null;
        admConfDao = null;
        return diskLimitSize;
    }

    /**
     * <br>[機  能] 指定したアカウントのディスク容量上限を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param admConfMdl WEBメール管理者設定
     * @return アカウントのディスク容量上限
     * @throws SQLException SQL実行時例外
     */
    public int getDiskLimitSize(Connection con, int wacSid, WmlAdmConfModel admConfMdl)
    throws SQLException {
        int diskLimitSize = -1;

        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountModel accountData = accountDao.select(wacSid);
        if (admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN
        || admConfMdl.getWadDiskComp() == GSConstWebmail.WAC_DISK_ADM_COMP) {

            if (accountData.getWacDiskSps() == GSConstWebmail.WAC_DISK_SPS_SPSETTINGS) {
                //アカウント ディスク容量特例設定 = 特例設定 かつ ディスク容量制限 = 制限あり の場合
                //アカウントごとに設定されたディスク容量上限を設定
                if (accountData.getWacDisk() == GSConstWebmail.WAC_DISK_LIMIT) {
                    diskLimitSize = accountData.getWacDiskSize();
                }

            } else if (admConfMdl.getWadDisk() == GSConstWebmail.WAC_DISK_LIMIT) {
                //管理者設定 ディスク容量設定区分 = 制限あり の場合
                //管理者設定のディスク容量上限を設定
                diskLimitSize = admConfMdl.getWadDiskSize();
            }

        } else if (accountData.getWacDisk() == GSConstWebmail.WAC_DISK_LIMIT) {
            //アカウント ディスク容量制限 = 制限あり の場合
            diskLimitSize = accountData.getWacDiskSize();
        }

        accountData = null;
        accountDao = null;
        return diskLimitSize;
    }

    /**
     * <br>[機  能] メール添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param msgNum メッセージ番号
     * @param binSid バイナリーSID
     * @param reqMdl リクエスト情報
     * @return メール添付ファイル情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public WmlTempfileModel getTempFileData(Connection con,
                                            long msgNum,
                                            long binSid,
                                            RequestModel reqMdl)
    throws TempFileException {
        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.getBinInfoForWebmail(con, msgNum, binSid,
                                        reqMdl.getDomain());
    }

    /**
     * <br>[機  能] メール添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param msgNum メッセージ番号
     * @param binSid バイナリーSID
     * @param domain ドメイン
     * @return メール添付ファイル情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public WmlTempfileModel getTempFileData(Connection con,
                                            long msgNum,
                                            long binSid,
                                            String domain)
    throws TempFileException {
        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.getBinInfoForWebmail(con, msgNum, binSid, domain);
    }

    /**
     * <br>[機  能] アカウントディスク使用量を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void updateAccountDiskSize(Connection con, int wacSid)
    throws SQLException {
        WebmailDao webmailDao = null;
        WmlAccountDiskModel diskMdl = null;
        WmlAccountDiskDao diskDao = null;

        try {
            webmailDao = new WebmailDao(con);

            diskMdl = new WmlAccountDiskModel();
            diskMdl.setWacSid(wacSid);
            diskMdl.setWdsSize(webmailDao.getSumAccountDiskSize(wacSid));

            diskDao = new WmlAccountDiskDao(con);
            if (diskDao.update(diskMdl) == 0) {
                diskDao.insert(diskMdl);
            }
        } finally {
            webmailDao = null;
            diskMdl = null;
            diskDao = null;
        }
    }

    /**
     * <br>[機  能] 削除設定画面の年コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 年コンボ
     */
    public static List<LabelValueBean> createDelYearCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        //年ラベル
        ArrayList<LabelValueBean> yearCombo = new ArrayList<LabelValueBean>();
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            yearCombo.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }

        return yearCombo;
    }

    /**
     * <br>[機  能] 削除設定画面の月コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 月コンボ
     */
    public static List<LabelValueBean> createDelMonthCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> monthCombo = new ArrayList<LabelValueBean>();
        for (int month : GSConst.DEL_MONTH_DATE) {
            monthCombo.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                    Integer.toString(month)));
        }

        return monthCombo;
    }

    /**
     * <br>[機  能] 削除設定画面の日コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 日コンボ
     */
    public static List<LabelValueBean> createDelDayCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> dayCombo = new ArrayList<LabelValueBean>();
        for (int day = GSConstWebmail.DEL_DAY_START;
            day <= GSConstWebmail.DEL_DAY_END; day++) {

            dayCombo.add(new LabelValueBean(day + gsMsg.getMessage("cmn.day"),
                                            String.valueOf(day)));
        }

        return dayCombo;
    }

    /**
     * <br>[機  能] メール情報の削除を行う
     * <br>[解  説] 「削除条件」と一致するメール情報を対象とする
     * <br>[備  考]
     * @param con コネクション
     * @param delMdl 削除条件
     * @param userSid ユーザSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public long deleteMailData(Connection con, WmlMailDeleteModel delMdl, int userSid)
    throws SQLException {
        long delCount = 0;
        WebmailDao dao = new WebmailDao(con);

        //削除対象のメールが存在しない場合は処理を終了する
        List<Long> mailNumList = dao.getUpdateMailList(delMdl);
        if (mailNumList.isEmpty()) {
            return 0;
        }

        for (int idx = 0; idx < mailNumList.size(); idx += 100) {
            boolean commit = false;
            con.setAutoCommit(false);

            int maxIdx = idx + 100;
            List<Long> delMailNumList = new ArrayList<Long>();
            for (int numIdx = idx; numIdx < mailNumList.size() && numIdx < maxIdx; numIdx++) {
                delMailNumList.add(mailNumList.get(numIdx));
            }

            try {
                dao.manuSendplanDel(delMailNumList);
                dao.manuSendDel(delMailNumList);
                dao.manuLabelRelationDel(delMailNumList);
                dao.manuUpdateTempFile(userSid, delMailNumList);
                dao.manuDelHeader(delMailNumList);
                dao.manuDelBody(delMailNumList);
                delCount += dao.manuDelMailData(delMailNumList);

                con.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("メール情報削除時に例外発生", e);
            } finally {
                if (!commit) {
                    con.rollback();
//                    break;
                }
            }
        }

        if (delCount > 0) {
            //削除したメールが1件以上存在する場合、アカウントディスク容量の集計を行う
            List<Integer> wacSidList = new ArrayList<Integer>();
            if (delMdl.isDelAllAccount()) {
                WmlAccountDao accountDao = new WmlAccountDao(con);
                List<WmlAccountModel> accountList = accountDao.select();
                for (WmlAccountModel accountMdl : accountList) {
                    wacSidList.add(accountMdl.getWacSid());
                }

            } else {
                wacSidList.add(delMdl.getWacSid());
            }

            boolean commit = false;
            try {
                for (Integer wacSid : wacSidList) {
                    updateAccountDiskSize(con, wacSid.intValue());
                }
                con.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("アカウントディスク容量の集計に失敗", e);
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        }

        return delCount;
    }


    /**
     * <br>[機  能] 指定したメールの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param mailNumList 削除するメールのメッセージ番号
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteMailData(Connection con, String wacSid, long[] mailNumList, int userSid)
            throws SQLException {

        if (mailNumList == null || mailNumList.length == 0) {
            return;
        }

        WmlSendaddressDao sendDao = new WmlSendaddressDao(con);
        WmlTempfileDao tempFileDao = new WmlTempfileDao(con);
        WmlLabelRelationDao labelRelationDao = new WmlLabelRelationDao(con);
        WmlHeaderDataDao headerDao = new WmlHeaderDataDao(con);
        WmlMailBodyDao bodyDao = new WmlMailBodyDao(con);
        WmlMailSendplanDao sendplanDao = new WmlMailSendplanDao(con);
        WmlMaildataDao mailDataDao = new WmlMaildataDao(con);
        UDate now = new UDate();

        for (long deleteMailNum : mailNumList) {
            sendDao.delete(deleteMailNum);
            tempFileDao.deleteTempFile(deleteMailNum, userSid, now);
            labelRelationDao.deleteToMailNum(deleteMailNum);
            headerDao.delete(deleteMailNum);
            bodyDao.delete(deleteMailNum);
            sendplanDao.delete(deleteMailNum);
            mailDataDao.delete(deleteMailNum);
        }

        log__.debug("物理削除したメールの件数 : " + mailNumList.length);

        //アカウントディスク容量の集計を行う
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.updateAccountDiskSize(con, Integer.parseInt(wacSid));
    }

    /**
     * ＷＥＢメール全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @throws GSException GS用汎実行例外
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            String opCode,
            String level,
            String value) throws GSException {
        outPutLog(map, reqMdl, con, opCode, level, value, null, GSConstWebmail.WML_LOG_FLG_NONE);
    }
    /**
     * ＷＥＢメール全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     * @throws GSException GS用汎実行例外
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            String opCode,
            String level,
            String value,
            String fileId,
            int logFlg) throws GSException {

        BaseUserModel usModel = reqMdl.getSmodel();
        if (usModel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        int usrSid = usModel.getUsrsid(); //セッションユーザSID

        GsMessage gsMsg = new GsMessage(reqMdl);
        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL);
        logMdl.setLogPluginName(
                gsMsg.getMessage(GSConstWebmail.PLUGIN_NAME_WEBMAIL));
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(reqMdl, map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        switch (logFlg) {
            case GSConstWebmail.WML_LOG_FLG_DOWNLOAD:
                logMdl.setLogCode("binSid：" + fileId);
                break;

            case GSConstWebmail.WML_LOG_FLG_PDF:
                logMdl.setLogCode(" PDF出力 wmlSid：" + fileId);
                break;

            case GSConstWebmail.WML_LOG_FLG_EML:
                logMdl.setLogCode(" EML出力 wmlSid：" + fileId);
                break;

            default:
        }

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * ＷＥＢメールＡＰＩ全般のログ出力を行う
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            RequestModel reqMdl,
            Connection con,
            int usid,
            String pgId,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL);
        logMdl.setLogPluginName(
                gsMsg.getMessage(GSConstWebmail.PLUGIN_NAME_WEBMAIL));
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getPgName(reqMdl, pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * ＷＥＢメール バッチ処理全般のログ出力を行う
     * @param con コネクション
     * @param domain ドメイン
     * @param opCode 操作
     * @param pgId 画面・機能ID
     * @param pgName 画面・機能名
     * @param level ログレベル
     * @param value 内容
     * @param logCode 操作コード(内部)
     * @throws GSException GS用汎実行例外
     */
    public void outPutBatchLog(
            Connection con,
            String domain,
            String opCode,
            String pgId,
            String pgName,
            String level,
            String value,
            String logCode) throws GSException {

        int usrSid = -1;

        GsMessage gsMsg = new GsMessage();
        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL);
        logMdl.setLogPluginName(
                gsMsg.getMessage(GSConstWebmail.PLUGIN_NAME_WEBMAIL));
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(pgName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(null);
        logMdl.setVerVersion(GSConst.VERSION);
        logMdl.setLogCode(logCode);

        LoggingBiz logBiz = new LoggingBiz(con);
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * プログラムIDからプログラム名称を取得する
     * @param reqMdl リクエスト情報
     * @param id アクションID
     * @return String
     */
    public String getPgName(RequestModel reqMdl, String id) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        String ret = new String();
        if (id == null) {
            return ret;
        }
        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.wml.wml010.Wml010Action")) {
            return gsMsg.getMessage("wml.124"); //WEBメール メール一覧
        }
        if (id.equals("jp.groupsession.v2.wml.wml040.Wml040Action")) {
            return gsMsg.getMessage("wml.122"); //WEBメール アカウント登録
        }
        if (id.equals("jp.groupsession.v2.wml.wml040kn.Wml040knAction")) {
            return gsMsg.getMessage("wml.123"); //WEBメール アカウント登録確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml050kn.Wml050knAction")) {
            return gsMsg.getMessage("wml.127"); //WEBメール 自動削除設定確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml060kn.Wml060knAction")) {
            return gsMsg.getMessage("wml.128"); //WEBメール 手動データ削除確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml080.Wml080Action")) {
            return gsMsg.getMessage("wml.125"); //WEBメール メール詳細(ポップアップ)
        }

        if (id.equals("jp.groupsession.v2.wml.wml100.Wml100Action")) {
            return gsMsg.getMessage("wml.182"); //個人設定 アカウントの管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml110.Wml110Action")) {
            return gsMsg.getMessage("wml.185"); //個人設定 ラベルの管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml120kn.Wml120knAction")) {
            return gsMsg.getMessage("wml.186"); //個人設定 ラベルの登録確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml130.Wml130Action")) {
            return gsMsg.getMessage("wml.183"); //個人設定 フィルタ設定
        }
        if (id.equals("jp.groupsession.v2.wml.wml140kn.Wml140knAction")) {
            return gsMsg.getMessage("wml.184"); //個人設定 フィルタ登録確認
        }

        if (id.equals("jp.groupsession.v2.wml.wml030.Wml030Action")) {
            return gsMsg.getMessage("wml.172"); //管理者設定 アカウントマネージャ
        }
        if (id.equals("jp.groupsession.v2.wml.wml150kn.Wml150knAction")) {
            return gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("cmn.preferences.kn"); //管理者設定 アカウント設定確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml160.Wml160Action")) {
            return gsMsg.getMessage("wml.174"); //管理者設定 アカウントインポート登録
        }
        if (id.equals("jp.groupsession.v2.wml.wml160kn.Wml160knAction")) {
            return gsMsg.getMessage("wml.175"); //管理者設定 アカウントインポート登録確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml170kn.Wml170knAction")) {
            return gsMsg.getMessage("wml.177"); //管理者設定 送受信ログ 自動削除設定確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml180kn.Wml180knAction")) {
            return gsMsg.getMessage("wml.178"); //管理者設定 送受信ログ手動削除確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml190kn.Wml190knAction")) {
            return gsMsg.getMessage("wml.wml190kn.01"); //個人設定 アカウント編集
        }
        if (id.equals("jp.groupsession.v2.wml.wml200.Wml200Action")) {
            return gsMsg.getMessage("wml.179"); //管理者設定 ラベル管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml210kn.Wml210knAction")) {
            return gsMsg.getMessage("wml.180"); //管理者設定 ラベル登録確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml220.Wml220Action")) {
            return gsMsg.getMessage("wml.198"); //管理者設定 フィルター管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml230kn.Wml230knAction")) {
            return gsMsg.getMessage("wml.199"); //管理者設定 フィルター登録確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml240.Wml240Action")) {
            return gsMsg.getMessage("wml.290"); //管理者設定 テンプレート管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml250kn.Wml250knAction")) {
            return gsMsg.getMessage("wml.291"); //管理者設定 テンプレート登録確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml260kn.Wml260knAction")) {
            return gsMsg.getMessage("wml.292"); //管理者設定 送信予定メール管理確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml270.Wml270Action")) {
            return gsMsg.getMessage("wml.293"); //管理者設定 送信先リスト管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml280.Wml280Action")) {
            return gsMsg.getMessage("wml.294"); //管理者設定 送信先リスト登録
        }
        if (id.equals("jp.groupsession.v2.wml.wml280kn.Wml280knAction")) {
            return gsMsg.getMessage("wml.295"); //管理者設定 送信先リスト登録確認
        }
        if (id.equals("jp.groupsession.v2.api.webmail.countm.ApiCountMAction")) {
            return gsMsg.getMessage("wml.126"); //WEBメール 件数API
        }
        if (id.equals("jp.groupsession.v2.wml.wml320.Wml320Action")) {
            return gsMsg.getMessage("wml.300"); //個人設定 メール情報一括削除
        }
        if (id.equals("jp.groupsession.v2.wml.wml320kn.Wml320knAction")) {
            return gsMsg.getMessage("wml.301"); //個人設定 メール情報一括削除確認
        }
        if (id.equals("jp.groupsession.v2.wml.wml330.Wml330Action")) {
            return gsMsg.getMessage("wml.wml330.01"); //管理者設定 OAuth認証情報管理
        }
        if (id.equals("jp.groupsession.v2.wml.wml340.Wml340Action")) {
            return gsMsg.getMessage("wml.wml340.01"); //管理者設定 OAuth認証情報登録
        }
        if (id.equals("jp.groupsession.v2.wml.wml340kn.Wml340knAction")) {
            return gsMsg.getMessage("wml.wml340kn.01"); //管理者設定 OAuth認証情報登録確認
        }

        //REST_API
        if (id.equals("RESTAPI_MAIL_DELETE")) {
            return gsMsg.getMessage("wml.api.maildelete"); //API メールの削除
        }
        if (id.equals("RESTAPI_MAIL_LABEL")) {
            return gsMsg.getMessage("wml.api.maillabel"); //API メールにラベルを設定・解除する
        }
        if (id.equals("RESTAPI_MAIL_LABEL_INSERT")) {
            return gsMsg.getMessage("wml.api.maillabel.insert"); //API メールにラベルを設定・解除する
        }
        if (id.equals("RESTAPI_MAIL_MOVE")) {
            return gsMsg.getMessage("wml.api.mailmove"); //API メールを移動する
        }
        if (id.equals("RESTAPI_MAIL_ADD_SEND")) {
            return gsMsg.getMessage("wml.api.mailsend"); //API メールを送信する（新規）
        }
        if (id.equals("RESTAPI_MAIL_ADD_DRAFT")) {
            return gsMsg.getMessage("wml.api.maildraft"); //API メールを草稿に保管する（編集）
        }
        if (id.equals("RESTAPI_MAIL_EDIT_SEND")) {
            return gsMsg.getMessage("wml.api.mailsend"); //API メールを送信する（編集）
        }
        if (id.equals("RESTAPI_MAIL_EDIT_DRAFT")) {
            return gsMsg.getMessage("wml.api.maildraft"); //API メールを草稿に保管する（編集）
        }

        return ret;
    }

    /**
     * <br>[機  能] メールヘッダ情報Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param mailData メール情報
     * @param wacSid アカウントSID
     * @return メールヘッダ情報Model
     */
    public List<WmlHeaderDataModel> createHeaderDataList(long mailNum,
                                                        WmlMailModel mailData,
                                                        int wacSid) {
        int num = 1;
        List<WmlHeaderDataModel> headerDataList = new ArrayList<WmlHeaderDataModel>();
        for (String headerType : mailData.getHeaderKey()) {
            List<String> headerValueList = mailData.getHeaderMap().get(headerType);

            for (String headerValue : headerValueList) {
                WmlHeaderDataModel headerData = new WmlHeaderDataModel();
                headerData.setWmdMailnum(mailNum);
                headerData.setWmhNum(num++);
                headerData.setWmhType(__cloneString(headerType));
                headerData.setWacSid(wacSid);

                if (!headerType.equals("References") || StringUtil.isNullZeroString(headerValue)) {
                    if (headerValue != null
                    && headerValue.length() > GSConstWebmail.HEADER_MAXLEN) {
                        headerData.setWmhContent(
                            __cloneString(headerValue.substring(0, GSConstWebmail.HEADER_MAXLEN)));
                    } else {
                        headerData.setWmhContent(__cloneString(headerValue));
                    }
                    headerDataList.add(headerData);

                } else {
                    StringTokenizer st = new StringTokenizer(headerValue.trim(), ">");
                    num--;
                    while (st.hasMoreTokens()) {
                        WmlHeaderDataModel refHeaderData = new WmlHeaderDataModel();
                        refHeaderData.setWmdMailnum(headerData.getWmdMailnum());
                        refHeaderData.setWmhNum(num++);
                        refHeaderData.setWmhType(headerData.getWmhType());
                        refHeaderData.setWmhContent(st.nextToken().trim() + ">");
                        refHeaderData.setWacSid(wacSid);
                        headerDataList.add(refHeaderData);
                    }
                }

            }
        }

        //1000文字を超えるヘッダーは切り捨て
        for (int index = 0; index < headerDataList.size(); index++) {
            String headerContent
                = NullDefault.getString(headerDataList.get(index).getWmhContent(), "");
            if (headerContent.length() > GSConstWebmail.HEADER_MAXLEN) {
                headerDataList.get(index).setWmhContent(
                        headerContent.substring(0, GSConstWebmail.HEADER_MAXLEN));
            }
        }

        return headerDataList;
    }

    /**
     * <br>[機  能] ファイル名のdecodeを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名
     * @return ファイル名
     * @throws UnsupportedEncodingException decodeに失敗
     */
    public static String decodeFileName(String fileName)
    throws UnsupportedEncodingException {
        if (StringUtil.isNullZeroString(fileName)) {
            return fileName;
        } else if (fileName.indexOf(ENCODETEXT__) < 0) {
//            return MimeUtility.decodeText(fileName);
            return __mimeDecode(fileName);
        }

        int index = -1;
        String decodeString = "";

        String subFileName = "";
        while ((index = fileName.indexOf(ENCODETEXT__)) >= 0) {

            if (index == 0) {
                decodeString += __mimeDecode(fileName);
                fileName = "";
            } else {
                subFileName += fileName.substring(0, index + 2);
                fileName = fileName.substring(index + 2, fileName.length());
                if (!subFileName.endsWith("?B?=") && !subFileName.endsWith("?Q?=")) {
                    subFileName = subFileName.trim();
                    decodeString += __mimeDecode(subFileName);
                    subFileName = "";
                }
            }
        }

        decodeString += fileName;
        fileName = null;
        return decodeString;
    }

    /**
     * <br>[機  能] メール受信サーバ接続情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param accountData アカウント情報
     * @return メール受信サーバ接続情報
     * @throws Exception 接続情報の生成に失敗
     */
    public WmlReceiveServerModel createReceiveServerData(Connection con, String appRootPath,
                                                    WmlAccountModel accountData) throws Exception {

        WmlReceiveServerModel receiveModel = new WmlReceiveServerModel();
        receiveModel.setWacSid(accountData.getWacSid());
        receiveModel.setAccountName(accountData.getWacName());
        receiveModel.setAccountMailAddress(accountData.getWacAddress());
        receiveModel.setAuthType(accountData.getWacAuthType());

        if (accountData.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
            receiveModel.setReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_IMAP);

            int cotSid = accountData.getCotSid();
            receiveModel.setCotSid(cotSid);

            //メール受信サーバ情報の取得
            OAuthBiz authBiz = new OAuthBiz();
            OauthMailServerModel receiveData
                = authBiz.getReceiveAuthData(con, cotSid, appRootPath);

            receiveModel.setAccessToken(receiveData.getAccessToken());
            receiveModel.setRefreshToken(receiveData.getRefreshToken());
            receiveModel.setClientId(receiveData.getClientId());
            receiveModel.setSecret(receiveData.getSecret());
            receiveModel.setProvider(receiveData.getProvider());
            receiveModel.setHost(receiveData.getHost());
            receiveModel.setPort(receiveData.getPort());

            receiveModel.setUser(accountData.getWacReceiveUser());
            receiveModel.setReceiveEncrypt(0);
            receiveModel.setApop(false);
            receiveModel.setTopCmd(false);

            //メール送信サーバ情報の取得
            OauthMailServerModel smtpData
                = authBiz.getSendServerData(con, cotSid, appRootPath, false);

            receiveModel.setSendServer(smtpData.getHost());
            receiveModel.setSendServerPort(smtpData.getPort());
            receiveModel.setSendServerUser(accountData.getWacSendUser());
            receiveModel.setSendServerPassword(null);
            receiveModel.setSendServerEncrypt(0);
            receiveModel.setSendServerAuth(false);
            receiveModel.setPopToSmtp(false);

        } else {
            receiveModel.setReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_POP);

            receiveModel.setHost(accountData.getWacReceiveHost());
            receiveModel.setPort(accountData.getWacReceivePort());
            receiveModel.setUser(accountData.getWacReceiveUser());
            receiveModel.setPassword(accountData.getWacReceivePass());
            receiveModel.setReceiveEncrypt(accountData.getWacReceiveSsl());
            receiveModel.setApop(
                    accountData.getWacApop() == GSConstWebmail.WAC_APOP_USE);
            receiveModel.setTopCmd(
                    accountData.getWacTopCmd() == GSConstWebmail.WAC_TOP_COMMAND_DISABLE);

            receiveModel.setSendServer(accountData.getWacSendHost());
            receiveModel.setSendServerPort(accountData.getWacSendPort());
            receiveModel.setSendServerUser(accountData.getWacSendUser());
            receiveModel.setSendServerPassword(accountData.getWacSendPass());
            receiveModel.setSendServerEncrypt(accountData.getWacSendSsl());
            receiveModel.setSendServerAuth(
                        accountData.getWacSmtpAuth() == GSConstWebmail.WAC_SMTPAUTH_YES);
            receiveModel.setPopToSmtp(
                        accountData.getWacPopbsmtp() == GSConstWebmail.WAC_POPBSMTP_YES);
        }

        String accountString = WmlUtil.getAccountString(
                                            receiveModel.getHost(),
                                            receiveModel.getPort(),
                                            receiveModel.getUser());

        receiveModel.setAccountString(accountString);

        receiveModel.setDelReceive(
                accountData.getWacDelreceive() == GSConstWebmail.WAC_DELRECEIVE_YES);
        receiveModel.setReReceive(
                accountData.getWacRereceive() == GSConstWebmail.WAC_RERECEIVE_YES);

        receiveModel.setReceiveConnectTimeout(
                WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_RECEIVE_CONNECTTIMEOUT,
                        GSConstWebmail.RECEIVE_CONNECTTIMEOUT_DEFAULT));
        receiveModel.setReceiveTimeout(
                WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_RECEIVE_TIMEOUT,
                        GSConstWebmail.RECEIVE_TIMEOUT_DEFAULT));
        receiveModel.setReceiveRcvsvrCheckTime(
                WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_MAILRECEIVE_RCVSVR_CHECKTIME,
                        GSConstWebmail.RECEIVE_RCVSVR_CHECKTIME_DEFAULT));

        receiveModel.setMailBodyLimit(getBodyLimitLength(appRootPath));

        if (accountData.getWacEncodeSend() == GSConstWebmail.WAC_ENCODE_SEND_UTF8) {
            receiveModel.setSendEncode(Encoding.UTF_8);
        } else {
            receiveModel.setSendEncode(Encoding.ISO_2022_JP);
        }

        //管理者情報を取得
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        WmlAdmConfModel adminMdl = adminDao.selectAdmData();

        //ディスク制限
        receiveModel.setDiskLimitSize(getDiskLimitSize(con, receiveModel.getWacSid(), adminMdl));
        receiveModel.setDiskLimit(receiveModel.getDiskLimitSize() > 0);

        //管理者一括
        if (adminMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
            //受信時削除
            receiveModel.setDelReceive(
                    adminMdl.getWadDelreceive() == GSConstWebmail.WAC_DELRECEIVE_YES);

        }

        receiveModel.setMaxReceiveMailCount(getMaxReceiveMailCount(appRootPath));

        return receiveModel;
    }

    /**
     * <br>[機  能] メール本文の最大文字数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return メール本文の最大文字数
     */
    public static int getBodyLimitLength(String appRootPath) {
        int bodyLimit = getConfValue(appRootPath,
                                    GSConstWebmail.MAILCONF_MAILBODY_LIMIT,
                                    GSConstWebmail.MAILBODY_LIMIT_DEFAULT);

        if (bodyLimit < 0) {
            bodyLimit = GSConstWebmail.MAILBODY_LIMIT_DEFAULT;
        }

        return bodyLimit;
    }

    /**
     * <br>[機  能] 送受信ログが「登録する」に設定されているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:送受信ログを「登録する」 false:送受信ログを「登録しない」
     * @throws SQLException SQL実行時例外
     */
    public boolean isEntryMailLog(Connection con) throws SQLException {
        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        return admConfDao.selectAdmData().getWadAcctLogRegist()
                    == GSConstWebmail.ACNT_LOG_REGIST_REGIST;
    }

    /**
     * <br>[機  能] 指定したディレクトリ種別が送信メールに関するものかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirType ディレクトリ種別
     * @return true:送信メールに関するもの false:送信メールとは無関係
     */
    public static boolean isSendDirType(int dirType) {
        return (dirType == GSConstWebmail.DIR_TYPE_SENDED
                || dirType == GSConstWebmail.DIR_TYPE_NOSEND
                || dirType == GSConstWebmail.DIR_TYPE_DRAFT);
    }

    /**
     * <br>[機  能] MIMEデコードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param value 文字列
     * @return デコード後の文字列
     * @throws UnsupportedEncodingException デコードに失敗
     */
    private static String __mimeDecode(String value)
    throws UnsupportedEncodingException {
//        String decodeValue = "";
//        if (value.startsWith("=?")) {
//            decodeValue = MimeUtility.decodeText(value);
//        } else {
//            int startIndex = value.indexOf("=?");
//            if (startIndex > 0) {
//                decodeValue = value.substring(0, startIndex);
//                decodeValue += MimeUtility.decodeText(value.substring(startIndex));
//            } else {
//                decodeValue = value.toString();
//            }
//        }
//
//        return decodeValue;

        try {
            return MailUtil.decodeMimeText(value);
        } catch (Exception e) {
            log__.error("デコードに失敗: " + value, e);
            throw new UnsupportedEncodingException("デコードに失敗");
        }
    }

    /**
     * <br>[機  能] Stringのディープコピーを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param value String
     * @return String
     */
    private String __cloneString(String value) {
        if (value != null) {
            return new String(value);
        }

        return null;
    }

    /**
     * <br>[機  能] 指定したメールが受信済みかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param accountString アカウント文字列
     * @param uid UID
     * @return true:受信済み false:未受信
     * @throws SQLException SQL実行時例外
     */
    public boolean isReceived(Connection con, int wacSid, String accountString, String uid)
    throws SQLException {
        WmlUidlDao uidlDao = new WmlUidlDao(con);
        boolean result = uidlDao.existUID(wacSid, accountString, uid);
        uidlDao = null;
        return result;
    }

    /**
     * <br>[機  能] 指定したアカウントが受信対象とするメールのメッセージIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param folder Folder
     * @param messages メール情報
     * @param wacSid アカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param receiveType 受信形式(POP3 or IMAP)
     * @return メッセージID
     * @throws SQLException SQL実行時例外
     * @throws MessagingException メッセージIDの取得に失敗
     */
    public List<String> getReceiveMessageId(Connection con, Folder folder,
                                            Message[] messages, int wacSid,
                                            String appRootPath,
                                            int receiveType)
    throws SQLException, MessagingException {
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountModel accountData = accountDao.select(wacSid);

        String accountString = null;
        boolean reReceive = accountData.getWacRereceive() == GSConstWebmail.WAC_RERECEIVE_YES;
        int maxReceiveCnt = getMaxReceiveMailCount(appRootPath);

        return getReceiveMessageId(con, folder, messages, wacSid,
                                    accountString, reReceive, maxReceiveCnt,
                                    receiveType);
    }

    /**
     * <br>[機  能] 指定したアカウントが受信対象とするメールのメッセージIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param folder Folder
     * @param messages メール情報
     * @param wacSid アカウントSID
     * @param accountString アカウント文字列
     * @param reReceive true:受信済みのメッセージを受信対象に含める false:受信済みのメッセージを受信対象に含めない
     * @param maxReceiveCnt 一度に受信するメールの最大件数
     * @param receiveType 受信形式(POP3 or IMAP)
     * @return メッセージID
     * @throws SQLException SQL実行時例外
     * @throws MessagingException メッセージIDの取得に失敗
     */
    public List<String> getReceiveMessageId(Connection con, Folder folder,
                                            Message[] messages,
                                            int wacSid, String accountString,
                                            boolean reReceive, int maxReceiveCnt,
                                            int receiveType)
    throws SQLException, MessagingException {

        List<String> msgIdList = new ArrayList<String>();
        if (maxReceiveCnt <= 0) {
            return msgIdList;
        }

        String uid = null;
        int receiveCnt = 0;
        for (Message message : messages) {
            if (receiveType == GSConstWebmail.WAC_RECEIVE_TYPE_IMAP) {
                uid = Long.toString(((IMAPFolder) folder).getUID(message));
            } else {
                uid = new String(((POP3Folder) folder).getUID(message));
            }

            if (reReceive || !isReceived(con, wacSid, accountString, uid)) {
               msgIdList.add(uid);
               receiveCnt++;
               if (receiveCnt >= maxReceiveCnt) {
                   break;
               }
            }
        }

        return msgIdList;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考] OSチェック未実装
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    public String fileNameCheck(String fileName) {
            String escName = fileName;

            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }

    /**
     * <br>[機  能] 指定したアカウントが「後で送信する」に設定されているかを判定する
     * <br>[解  説]
     * <br>[備  考] 管理者設定の設定内容を優先する
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return true: 後で送信する false: 即時送信
     * @throws SQLException SQL実行時例外
     */
    public boolean isTimeSent(Connection con, int wacSid) throws SQLException {
        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = admConfDao.selectAdmData();

        boolean result = false;
        if (admConfMdl != null
        && admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
            result = admConfMdl.getWadTimesent() == GSConstWebmail.WAD_TIMESENT_LATER;
        } else {
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountMdl = accountDao.select(wacSid);
            result = accountMdl.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_LATER;
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したアカウントが「作成時に選択 」に設定されているかを判定する
     * <br>[解  説]
     * <br>[備  考] 管理者設定の設定内容を優先する
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return true: 後で送信する false: 即時送信
     * @throws SQLException SQL実行時例外
     */
    public boolean isTimeSentInput(Connection con, int wacSid) throws SQLException {
        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = admConfDao.selectAdmData();

        boolean result = false;
        if (admConfMdl != null
        && admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
            result = admConfMdl.getWadTimesent() == GSConstWebmail.WAD_TIMESENT_INPUT;
        } else {
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountMdl = accountDao.select(wacSid);
            result = accountMdl.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_INPUT;
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したアカウントが何分後に送信するかを取得する
     * <br>[解  説]
     * <br>[備  考] 管理者設定の設定内容を優先する
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return true: 後で送信する false: 即時送信
     * @throws SQLException SQL実行時例外
     */
    public int getTimeSentMinute(Connection con, int wacSid) throws SQLException {
        int timeSentMinute = 0;
        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(wacSid);
        if (wacMdl != null && wacMdl.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_LATER) {
            //5分後に送信を行う
            timeSentMinute = 5;
        }
        return timeSentMinute;
    }

    /**
     * <br>[機  能] WEBメールで使用する「引用符」を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacQuotes アカウント情報 引用符
     * @return 引用符
     */
    public static String getMailQuotes(int wacQuotes) {
        String quates = "";
        switch (wacQuotes) {
            case GSConstWebmail.WAC_QUOTES_DEF:
                quates = ">";
                break;
            case GSConstWebmail.WAC_QUOTES_2:
                quates = ">>";
                break;
            case GSConstWebmail.WAC_QUOTES_3:
                quates = "<";
                break;
            case GSConstWebmail.WAC_QUOTES_4:
                quates = "<<";
                break;
            case GSConstWebmail.WAC_QUOTES_5:
                quates = "|";
                break;
            default:
        }

        return quates;
    }


    /**
     * <br>[機  能] WEBメールで使用する「引用符」(画面表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacQuotes アカウント情報 引用符
     * @param reqMdl リクエスト情報
     * @return 引用符(画面表示用)
     */
    public static String getViewMailQuotes(int wacQuotes, RequestModel reqMdl) {
        String quates = ">";
        switch (wacQuotes) {
            case GSConstWebmail.WAC_QUOTES_DEF:
                quates = ">";
                break;
            case GSConstWebmail.WAC_QUOTES_NONE:
                GsMessage gsMsg = new GsMessage(reqMdl);
                quates = gsMsg.getMessage("cmn.no");
                break;
            case GSConstWebmail.WAC_QUOTES_2:
                quates = ">>";
                break;
            case GSConstWebmail.WAC_QUOTES_3:
                quates = "<";
                break;
            case GSConstWebmail.WAC_QUOTES_4:
                quates = "<<";
                break;
            case GSConstWebmail.WAC_QUOTES_5:
                quates = "|";
                break;
            default:
        }

        return quates;
    }

    /**
     * <br>[機  能] 指定したアカウントを使用可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccountUserList(Connection con, int wacSid) throws SQLException {
        return getAccountUserList(con, wacSid, isProxyUserAllowed(con));
    }

    /**
     * <br>[機  能] 指定したアカウントを使用可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param proxyUser true: 代理人を含む false: 代理人を含めない
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccountUserList(Connection con, int wacSid, boolean proxyUser)
    throws SQLException {
        //送信先(アカウント使用者)
        List<Integer> userList = null;

        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountModel accountMdl = accountDao.select(wacSid);

        WmlAccountUserDao accountUserDao = new WmlAccountUserDao(con);
        if (accountMdl.getWacType() == GSConstWebmail.WAC_TYPE_GROUP) {
            userList = accountUserDao.getBelongUserList(wacSid);
        } else {
            userList = accountUserDao.getUserList(wacSid);
        }

        if (proxyUser) {
            //代理人ユーザを使用可能なユーザの一覧に含める
            WmlAccountUserProxyDao accountProxyUserDao = new WmlAccountUserProxyDao(con);
            List<Integer> proxyUserList = accountProxyUserDao.getProxyUserList(wacSid);
            for (Integer proxyUserSid : proxyUserList) {
                if (!userList.contains(proxyUserSid)) {
                    userList.add(proxyUserSid);
                }
            }
        }

        return userList;
    }

    /**
     * <br>[機  能] WEBメールに関する通知をショートメールで送信する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番用コネクション
     * @param pluginConfig PluginConfig
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @param wacSid アカウントSID
     * @param title タイトル
     * @param message 通知メッセージ
     * @throws SQLException SQL実行時例外
     * @throws Exception ショートメールの送信に失敗
     */
    public void sendSmail(Connection con,
            MlCountMtController cntCon,
            PluginConfig pluginConfig,
            String appRootPath,
            RequestModel reqMdl,
            int wacSid, String title, String message)
    throws SQLException, Exception {

        List<Integer> userList = getAccountUserList(con, wacSid);
        sendSmail(con, cntCon, pluginConfig,
                    appRootPath, reqMdl,
                    userList, title, message);
    }

    /**
     * <br>[機  能] WEBメールに関する通知をショートメールで送信する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番用コネクション
     * @param pluginConfig PluginConfig
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @param userList 送信先ユーザ一覧
     * @param title タイトル
     * @param message 通知メッセージ
     * @throws SQLException SQL実行時例外
     * @throws Exception ショートメールの送信に失敗
     */
    public void sendSmail(Connection con,
            MlCountMtController cntCon,
            PluginConfig pluginConfig,
            String appRootPath,
            RequestModel reqMdl,
            List<Integer> userList, String title, String message)
    throws SQLException, Exception {

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();
        //差出人(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);

        //送信先
        smlModel.setSendToUsrSidArray(userList);

        //タイトル
        String smlTitle = NullDefault.getString(title, "").toString();
        smlTitle = StringUtil.trimRengeString(smlTitle,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(smlTitle);

        //本文
        smlModel.setSendBody(message);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl);
        sender.execute();
    }

    /**
     * <br>[機  能] 送信メールの上限サイズを取得する
     * <br>[解  説] サイズ制限なしの場合、-1 を返す
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return 送信メールの上限サイズ
     * @throws SQLException SQL実行時例外
     */
    public int getSendMailLimitSize(Connection con, int wacSid) throws SQLException {
        int maxSize = -1;

        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = admConfDao.selectAdmData();
        if (admConfMdl != null
        && admConfMdl.getWadSendLimit() == GSConstWebmail.WAD_SEND_LIMIT_LIMITED) {
            maxSize = admConfMdl.getWadSendLimitSize() * 1024 * 1024;
        }

        return maxSize;
    }

    /**
     * <br>[機  能] 送信メールのサイズが上限を超えていないかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param sendData 送信メール情報
     * @param sendEncode 送信メールのエンコード
     * @return true: 正常 false: 送信メールのサイズが上限を超えている
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public boolean checkSendmailSize(Connection con, int wacSid,
                                                    SmtpSendModel sendData,
                                                    String sendEncode)
    throws SQLException,  UnsupportedEncodingException {
        //送信メールの上限サイズを取得
        int limit = getSendMailLimitSize(con, wacSid);

        return checkSendmailSize(sendData, limit, sendEncode);
    }

    /**
     * <br>[機  能] 送信メールのサイズが上限を超えていないかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sendData 送信メール情報
     * @param limit 送信メールの上限サイズ
     * @param sendEncode 送信メールのエンコード
     * @return true: 正常 false: 送信メールのサイズが上限を超えている
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public boolean checkSendmailSize(SmtpSendModel sendData, int limit, String sendEncode)
    throws SQLException,  UnsupportedEncodingException {
        if (limit <= 0) {
            return true;
        }

        long mailSize = getSendMailSize(sendData, sendEncode);
        return limit >= mailSize;
    }

    /**
     * <br>[機  能] 指定したメールを送信した場合、アカウントのディスク使用量上限を超えるかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param sendData 送信メール情報
     * @param sendEncode 送信メールのエンコード
     * @param diskLimitSize アカウント ディスク使用量条件
     * @param fileSize サーバーに保存されていない添付ファイルサイズ
     * @return true: 正常 false: アカウントのディスク使用量上限を超える
     * @throws SQLException SQL実行時例外
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public boolean checkDiskSizeLimitOnSend(Connection con, int wacSid,
                                                    SmtpSendModel sendData,
                                                    String sendEncode,
                                                    int diskLimitSize,
                                                    long fileSize)
    throws SQLException,  UnsupportedEncodingException {

        if (diskLimitSize <= 0) {
            return true;
        }

        long diskLimitSizeForByte =  WmlBiz.getDiskLimitSizeForByte(diskLimitSize);
        long sendMailSize = getSendMailSize(sendData, sendEncode) + fileSize;
        return diskLimitSizeForByte >= sendMailSize + getUseDiskSize(con, wacSid);
    }

    /**
     * <br>[機  能] 送信メール情報のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sendData 送信メール情報
     * @param sendEncode 送信メールのエンコード
     * @return 送信メール情報のサイズ
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public long getSendMailSize(SmtpSendModel sendData, String sendEncode)
            throws  UnsupportedEncodingException {
        int encLen = sendEncode.length();

        long mailSize = 0;
        mailSize += 45; //Date
        mailSize += 20; //MIME-Version
        mailSize += 30; //X-Mailer

        //Content-Type
        boolean existFile = false;
        if (sendData.getTempFileList() != null
        && !sendData.getTempFileList().isEmpty()) {
            mailSize += 92;
            existFile = true;
        } else {
            mailSize += 29 + encLen;
        }

        //Content-Transfer-Encoding
        mailSize += 45;

        //To, CC, BCC
        MailBiz mailBiz = new MailBiz();
        mailSize += mailBiz.getAddressSize(sendData.getTo(), sendEncode);
        mailSize += mailBiz.getAddressSize(sendData.getCc(), sendEncode);
        mailSize += mailBiz.getAddressSize(sendData.getBcc(), sendEncode);

        //Subject
        mailSize += 10 + NullDefault.getString(
                            mailBiz.mimeEncode(sendData.getSubject(), sendEncode), "").length();
        //本文
        if (sendData.isHtmlMail()) {
            mailSize += mailBiz.getBase64Size(WmlSmtpSender.formatHtmlToText(sendData.getBody()),
                                                        sendEncode);
        }
        mailSize += mailBiz.getBase64Size(sendData.getBody(), sendEncode);

        //添付ファイル
        if (existFile) {
            for (WmlMailFileModel fileData : sendData.getTempFileList()) {
                mailSize += 120 + mailBiz.mimeEncode(fileData.getFileName(), sendEncode).length();
                mailSize += mailBiz.getBinaryBase64Size(
                                                    (new File(fileData.getFilePath()).length()));
            }
        }

        return mailSize;
    }

    /**
     * <br>[機  能] 日時(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @return 日時(表示用)
     */
    public static String getWmlViewDate(UDate date) {
        if (date == null) {
            return "";
        }

        return UDateUtil.getSlashYYMD(date)
                + " " + UDateUtil.getSeparateHMS(date);
    }

    /**
     * <br>[機  能] アカウント情報から送信メールの文字コードを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accountData アカウント情報
     * @return 送信メールの文字コード
     */
    public static String getSendEncode(WmlAccountModel accountData) {
        String encode = null;
        if (accountData.getWacEncodeSend() == GSConstWebmail.WAC_ENCODE_SEND_ISO2022JP) {
            encode = Encoding.ISO_2022_JP;
        } else if (accountData.getWacEncodeSend() == GSConstWebmail.WAC_ENCODE_SEND_UTF8) {
            encode = Encoding.UTF_8;
        }

        return encode;
    }

    /**
     * <br>[機  能] WEBメールの各種通知をショートメールで送付する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param title タイトル
     * @param mailData メール情報
     * @param bodyTemplateFile 本文のテンプレートファイル名
     * @param wacSid アカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws Exception ショートメールの送付に失敗
     */
    public void sendSmail(Connection con,
                                    String title, WmlMaildataModel mailData,
                                    String bodyTemplateFile,
                                    int wacSid, String appRootPath, String domain)
    throws SQLException, Exception {
        Map<String, String> bodyParam = new HashMap<String, String>();
        bodyParam.put("TITLE", mailData.getWmdTitle());
        bodyParam.put("DATE", WmlBiz.getWmlViewDate(mailData.getWmdSdate()));

        sendSmail(con, title, bodyParam, bodyTemplateFile,
                        wacSid, appRootPath, domain);
    }

    /**
     * <br>[機  能] WEBメールの各種通知をショートメールで送付する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param title タイトル
     * @param bodyParam テンプレートへ設定する各種パラメータ
     * @param bodyTemplateFile 本文のテンプレートファイル名
     * @param wacSid アカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws Exception ショートメールの送付に失敗
     */
    public void sendSmail(Connection con,
                                    String title,
                                    Map<String, String> bodyParam,
                                    String bodyTemplateFile,
                                    int wacSid, String appRootPath, String domain)
    throws SQLException, Exception {

        String sendMailTitle = title;
        MlCountMtController cntCon
            = GroupSession.getResourceManager().getCountController(domain);
        PluginConfig pconfig
            = GroupSession.getResourceManager().getPluginConfig(domain);
        RequestModel reqMdl = new RequestModel();
        reqMdl.setDomain(domain);
        reqMdl.setLocale(Locale.JAPANESE);

        String templatePath = IOTools.setEndPathChar(appRootPath);
        templatePath
            = IOTools.replaceSlashFileSep(templatePath
                                                    + "/WEB-INF/plugin/webmail/smail/");
        templatePath += bodyTemplateFile;
        String tmpBody = IOTools.readText(templatePath, Encoding.UTF_8);
        String mailBody = StringUtil.merge(tmpBody, bodyParam);

        sendSmail(con, cntCon, pconfig, appRootPath, reqMdl,
                        wacSid, sendMailTitle, mailBody);
    }

    /**
     * <br>[機  能] アカウントの署名を取得する
     * <br>[解  説]
     * <br>[備  考] デフォルトの署名を取得
     * @param con コネクション
     * @param wacSid アカウントSid
     * @return アカウントの署名
     * @throws SQLException SQL実行時例外
     */
    public String getAccountSign(Connection con, int wacSid) throws SQLException {
        WmlAccountSignDao signDao = new WmlAccountSignDao(con);
        WmlAccountSignModel signMdl = signDao.getDefaultSign(wacSid);
        if (signMdl == null) {
            return "";
        }
        return signMdl.getWsiSign();
    }

    /**
     * <br>[機  能] 添付ファイルのダウンロードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param tempDir テンポラリディレクトリ
     * @param fileId 添付ファイルID
     * @return fileName ファイル名
     * @throws Exception 添付ファイルのダウンロードに失敗
     */
    public String downloadTempFile(HttpServletRequest req, HttpServletResponse res,
                                                String tempDir, String fileId)
    throws Exception {

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel filedMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, filedMdl.getFileName(), Encoding.UTF_8);
        return filedMdl.getFileName();
    }

    /**
     * <br>[機  能] アカウントに代理人を設定することが許可されているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true: 許可、false: 許可しない
     * @throws SQLException SQL実行時例外
     */
    public boolean isProxyUserAllowed(Connection con) throws SQLException {
        //管理者情報を取得
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        WmlAdmConfModel adminMdl = adminDao.selectAdmData();

        //代理人が許可されているかを判定
        return adminMdl.getWadProxyUser() == GSConstWebmail.WAD_PROXY_USER_YES;
    }

    /**
     * <br>[機  能] 指定したアカウントを編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param userSid ユーザSID
     * @return true: 編集可能、false: 編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditAccount(Connection con, int wacSid, int userSid) throws SQLException {
        WmlDao wmlDao = new WmlDao(con);
        return wmlDao.canUseAccount(wacSid, userSid, false);
    }

    /**
     * <br>[機  能] 指定した送信先リストを使用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlSid 送信先リストSID
     * @param userSid ユーザSID
     * @return true: 使用可能、false: 使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseDestlist(Connection con, int wdlSid, int userSid) throws SQLException {
        WmlDestlistAccessConfDao destlistAccessDao = new WmlDestlistAccessConfDao(con);
        return destlistAccessDao.checkDestlistAuth(wdlSid, userSid, -1);
    }

    /**
     * <br>[機  能] 指定した送信先リストを編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlSid 送信先リストSID
     * @param userSid ユーザSID
     * @return true: 編集可能、false: 編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditDestlist(Connection con, int wdlSid, int userSid) throws SQLException {
        WmlDestlistAccessConfDao destlistAccessDao = new WmlDestlistAccessConfDao(con);
        return destlistAccessDao.checkDestlistAuth(wdlSid, userSid, GSConstWebmail.WLA_AUTH_ALL);
    }

    /**
     * ディスク容量制限 制限サイズをbyte換算して返す
     * @param diskLimitSize ディスク容量制限 制限サイズ
     * @return ディスク容量制限 制限サイズ(byte換算)
     */
    public static long getDiskLimitSizeForByte(int diskLimitSize) {
        if (diskLimitSize <= 0) {
            return 0;
        }

        BigInteger limitSize = new BigInteger(String.valueOf(diskLimitSize));
        limitSize = limitSize.multiply(new BigInteger("1024"));
        limitSize = limitSize.multiply(new BigInteger("1024"));
        return limitSize.longValue();
    }

    /**
     * <br>[機  能] 送信メールの集計データを登録します。
     * <br>[解  説]
     * <br>[備  考]
     *  @param con コネクション
     *  @param wacSid アカウントSID
     *  @param cntTo TO件数
     *  @param cntCc CC件数
     *  @param cntBcc BCC件数
     *  @param soushinTime 送信日時
     *  @throws SQLException SQL実行時例外
     */
    public void regSmailLogCnt(
            Connection con, int wacSid, int cntTo, int cntCc, int cntBcc, UDate soushinTime)
                    throws SQLException {

        __registLogCnt(con, wacSid, GSConstWebmail.LOG_COUNT_KBN_SMAIL,
                cntTo, cntCc, cntBcc, soushinTime);
    }

    /**
     * <br>[機  能] 受信メールの集計データを登録します。
     * <br>[解  説]
     * <br>[備  考]
     *  @param con コネクション
     *  @param wacSid アカウントSID
     *  @param mailKbn メール区分 (TO or CC or BCC)
     *  @param jushinTime 受信日時
     *  @throws SQLException SQL実行時例外
     */
    public void regJmailLogCnt(
            Connection con, int wacSid, int mailKbn, UDate jushinTime)
                    throws SQLException {

        int cntTo = 0;
        int cntCc = 0;
        int cntBcc = 0;
        if (mailKbn == GSConstWebmail.WSA_TYPE_TO) {
            cntTo = 1;
        } else if (mailKbn == GSConstWebmail.WSA_TYPE_CC) {
            cntCc = 1;
        } else if (mailKbn == GSConstWebmail.WSA_TYPE_BCC) {
            cntBcc = 1;
        }

        __registLogCnt(con, wacSid, GSConstWebmail.LOG_COUNT_KBN_JMAIL,
                cntTo, cntCc, cntBcc, jushinTime);
    }

    /**
     * <br>[機  能] WEBメール 集計データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param wlcKbn ログ区分
     * @param cntTo TO件数
     * @param cntCc CC件数
     * @param cntBcc BCC件数
     * @param wlcTime 日時
     * @throws SQLException SQL実行時例外
     */
    private void __registLogCnt(
            Connection con, int wacSid, int wlcKbn,
            int cntTo, int cntCc, int cntBcc, UDate wlcTime) throws SQLException {
        WmlLogCountModel wlcMdl = new WmlLogCountModel();
        wlcMdl.setWacSid(wacSid);
        wlcMdl.setWlcKbn(wlcKbn);
        wlcMdl.setWlcCntTo(cntTo);
        wlcMdl.setWlcCntCc(cntCc);
        wlcMdl.setWlcCntBcc(cntBcc);
        wlcMdl.setWlcDate(wlcTime);
        WmlLogCountDao dao = new WmlLogCountDao(con);
        dao.insert(wlcMdl);

//        //集計
//        __registLogCntSum(con, wlcKbn, wlcTime);
    }

//    /**
//     * <br>[機  能] WEBメール 集計データの集計情報を登録する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param con コネクション
//     * @param wlcKbn ログ区分
//     * @param wlcTime 日時
//     * @throws SQLException SQL実行時例外
//     */
//    private void __registLogCntSum(
//            Connection con, int wlcKbn, UDate wlcTime) throws SQLException {
//
//        WmlLogCountSumDao logSumDao = new WmlLogCountSumDao(con);
//        WmlLogCountSumModel logSumMdl = logSumDao.getSumLogCount(wlcKbn, wlcTime);
//        if (logSumMdl != null) {
//            if (logSumDao.update(logSumMdl) == 0) {
//                logSumDao.insert(logSumMdl);
//            }
//        }
//    }

    /**
     * <br>[機  能] 管理者設定のリンク制限を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true: 制限、false: 制限しない
     * @throws SQLException SQL実行時例外
     */
    public boolean isLinkLimit(Connection con) throws SQLException {
        //管理者情報を取得
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        WmlAdmConfModel adminMdl = adminDao.selectAdmData();

        //代理人が許可されているかを判定
        return adminMdl.getWadlinkLimit() == GSConstWebmail.WAD_LINK_LIMITED;
    }

    /**
     * <br>[機  能] 管理者設定情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 管理者設定情報
     * @throws SQLException SQL実行時例外
     */
    public WmlAdmConfModel getAdminConf(Connection con) throws SQLException {
        //管理者情報を取得
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        List<WmlAdmConfModel> adminList = adminDao.select();
        WmlAdmConfModel adminMdl = null;
        if (adminList.size() > 0) {
            // 管理者設定情報がある場合
            adminMdl = adminList.get(0);
        } else {
            // ※db_init で初期データが設定されているのでここに来ることは無い
        }
        return adminMdl;
    }

    /**
     * <br>[機  能] トップレベルドメインの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wadMdl 管理者設定情報モデル
     * @return トップレベルドメインの一覧
     */
    public String[] getTldList(WmlAdmConfModel wadMdl) {
        if (wadMdl != null && wadMdl.getWadTldLimit() == GSConstWebmail.WAD_TLD_LIMITED) {
            // TLD制限ありの場合、許可リストをセット
            if (wadMdl.getWadTldLimitText() != null) {
                return wadMdl.getWadTldLimitText().split("\r\n");
            }
        }
        // TLD制限なしの場合、全許可
        return null;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl, String dirId, String... subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                                GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                                dirId,
                                                subId);
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     */
    public void deleteTempDir(RequestModel reqMdl, String dirId, String... subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl,
                                    GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                    dirId,
                                    subId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void createTempDir(RequestModel reqMdl, String dirId, String... subId)
    throws IOToolsException {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl,
                                GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                dirId,
                                subId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリの初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl, String dirId, String... subId)
    throws IOToolsException {
        //テンポラリディレクトリの削除後、再作成
        deleteTempDir(reqMdl, dirId, subId);
        createTempDir(reqMdl, dirId, subId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @return チェック結果 (true: 正常、false: 不正なパス)
     * @throws IOToolsException テンポラリディレクトリパスが不正
     */
    public boolean checkTempDir(RequestModel reqMdl, String dirId)
    throws IOToolsException {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();

        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                dirId);

        boolean result = tempPathUtil.checkTempPath(tempDir,
                                                reqMdl,
                                                GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                                dirId);
        if (!result) {
            throw new IOToolsException("指定されたディレクトリIDが不正 : " + dirId);
        }
        return true;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @return チェック結果 (true: 正常、false: 不正なパス)
     */
    public boolean checkTempDir(String tempDir, RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        return tempPathUtil.checkTempPath(tempDir,
                                reqMdl,
                                GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                dirId);
    }

    /**
     * [機  能] WEBメール HTMLファイルのインラインダウンロードを行う<br>
     * [解  説]<br>
     * [備  考]<br>
     * @param req HttpServletRequest
     * @param res レスポンス
     * @param wtfMdl 添付ファイルモデル
     * @param appRootPath アプリケーションルートパス
     * @param encoding ファイル名の出力文字コード(IE以外のブラウザで適用。IEの場合は強制的にShift_JIS)
     * @param tempDir ダウンロード用に変換したHTMLファイルの一時保存先ディレクトリ
     * @param apiFlg true: WEB APIから使用 false: API以外から
     * @throws Exception システムエラー
     */
    public void downloadHtmlForWebmail(
            HttpServletRequest req,
            HttpServletResponse res, WmlTempfileModel wtfMdl,
            String appRootPath, String encoding,
            String tempDir,
            boolean apiFlg)
            throws Exception {

        //添付ファイルをテンポラリディレクトリにコピーする。
        CommonBiz cmnBiz = new CommonBiz();
        String dateStr = (new UDate()).getDateString();
        StringBuilder filePath = new StringBuilder("");
        filePath.append(dateStr);
        filePath.append("000");
        filePath.append(GSConstCommon.ENDSTR_SAVEFILE);
        File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, 0);

        cmnBiz.saveTempFileForWebmail(
                dateStr, wtfMdl, appRootPath, tempDir, 0);

        String charset = null;
        if (!StringUtil.isNullZeroString(wtfMdl.getWtfCharset())) {
            charset = wtfMdl.getWtfCharset();
        }

        String htmlBody = IOTools.readText(saveFilePath.getPath(), charset);

        //JavaScriptの無効化を行う
        //<script>タグの除去と<iframe>タグの除去 ※１
        htmlBody = StringUtilHtml.removeIllegalTag(htmlBody);
        //<img>のonerror属性の除去
        htmlBody = StringUtilHtml.removeAttribute(htmlBody, "img", "onerror");
        //<audio>のonloadstart属性の除去
        htmlBody = StringUtilHtml.removeAttribute(htmlBody, "audio", "onloadstart");
        //<video>のonloadstart属性の除去
        htmlBody = StringUtilHtml.removeAttribute(htmlBody, "video", "onloadstart");

        String parentPath = "";
        if (apiFlg) {
            parentPath = "../";
        }

        String script = "<script src='" + parentPath + "../common/js/jquery-1.7.2.custom.min.js?"
                + GSConst.VERSION_PARAM + "'></script>";

        int lastLenHtml = htmlBody.toLowerCase().lastIndexOf("</body></html>");
        int lastLenHead = htmlBody.toLowerCase().lastIndexOf("</head>");

        //各種イベントの無効化用スクリプトを追加する
        if (lastLenHead > 0) {
            htmlBody = htmlBody.substring(0, lastLenHead)
                + script + htmlBody.substring(lastLenHead);
        } else if (lastLenHtml > 0) {
            htmlBody = htmlBody.substring(0, lastLenHtml)
                + script + htmlBody.substring(lastLenHtml);
        }

        String favicon = "<link href=\"../common/images/favicon.ico\" rel=\"SHORTCUT ICON\" />";

        IOTools.writeText(saveFilePath.getPath(), charset, favicon + htmlBody);

        TempFileUtil.downloadInline(req, res,
                saveFilePath,
                wtfMdl.getWtfFileName(),
                charset);
    }

    /**
     * 指定されたメールの予約送信タイプを取得する
     * @param mailData メール情報
     * @return 予約送信タイプ
     */
    public int getSendPlanType(WmlMailResultModel mailData) {
        if (mailData != null) {
            if (mailData.getSendPlanKbn() == GSConstWebmail.WAC_TIMESENT_LATER) {
                return GSConstWebmail.TIMESENT_FUTURE;
            }
        }

        return GSConstWebmail.TIMESENT_NORMAL;
    }

    /**
     * 指定されたメールの添付ファイル圧縮フラグ
     * @param mailData メール情報
     * @return 添付ファイル圧縮フラグ
     */
    public int getCompressFileType(WmlMailResultModel mailData) {
        return mailData.getSendPlanCompressFile();
    }

    /**
     * メールに指定したラベルを設定する
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param mailNumList メッセージ番号
     * @param labelSid ラベルSID
     * @throws SQLException SQL実行時例外
     */
    public void addLabelToMail(Connection con, int wacSid, long[] mailNumList, int labelSid)
    throws SQLException {
        WmlLabelRelationDao wlbRelationDao = new WmlLabelRelationDao(con);
        List<WmlLabelRelationModel> wlbRelationList = new ArrayList<WmlLabelRelationModel>();
        for (long mailSid : mailNumList) {
            if (wlbRelationDao.select(mailSid, labelSid) == null) {
                WmlLabelRelationModel labelRelationMdl = new WmlLabelRelationModel();
                labelRelationMdl.setWacSid(wacSid);
                labelRelationMdl.setWlbSid(labelSid);
                labelRelationMdl.setWmdMailnum(mailSid);
                wlbRelationList.add(labelRelationMdl);
            }
        }
        wlbRelationDao.insert(wlbRelationList);
    }

    /**
     * メールから指定したラベルを削除する
     * @param con コネクション
     * @param mailNumList メッセージ番号
     * @param wlbSid ラベルSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteLabelToMail(Connection con, long[] mailNumList, int wlbSid)
    throws SQLException {
        WmlLabelRelationDao wlbRelationDao = new WmlLabelRelationDao(con);
        wlbRelationDao.delete(wlbSid, mailNumList);
    }

    /**
     * メールの未読/既読状態を変更する
     * @param con コネクション
     * @param mailNumList メッセージ番号
     * @param wmdReaded 未読/既読状態
     * @throws SQLException SQL実行時例外
     */
    public void changeMailReaded(Connection con, long[] mailNumList, int wmdReaded)
    throws SQLException {
        WmlMaildataDao mailDataDao = new WmlMaildataDao(con);
        mailDataDao.changeMailReaded(mailNumList, wmdReaded);
    }

    /**
     * メールを指定したディレクトリへ移動する
     * @param con コネクション
     * @param mailNumList メッセージ番号
     * @param wdrSid 移動先ディレクトリのディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public void moveMail(Connection con, long[] mailNumList, long wdrSid)
    throws SQLException {
        WebmailDao webmailDao = new WebmailDao(con);
        webmailDao.moveMail(mailNumList, wdrSid);
    }
}
