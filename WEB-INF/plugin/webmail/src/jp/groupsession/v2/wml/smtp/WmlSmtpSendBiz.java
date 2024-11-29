package jp.groupsession.v2.wml.smtp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.dao.base.WmlTempfileDao;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlUsedDataBiz;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlHeaderDataDao;
import jp.groupsession.v2.wml.dao.base.WmlMailBodyDao;
import jp.groupsession.v2.wml.dao.base.WmlMailLogDao;
import jp.groupsession.v2.wml.dao.base.WmlMailLogSendDao;
import jp.groupsession.v2.wml.dao.base.WmlMailSendplanDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.dao.base.WmlSendaddressDao;
import jp.groupsession.v2.wml.model.WmlMailModel;
import jp.groupsession.v2.wml.model.WmlSendMailModel;
import jp.groupsession.v2.wml.model.base.WmlHeaderDataModel;
import jp.groupsession.v2.wml.model.base.WmlMailBodyModel;
import jp.groupsession.v2.wml.model.base.WmlMailLogModel;
import jp.groupsession.v2.wml.model.base.WmlMailLogSendModel;
import jp.groupsession.v2.wml.model.base.WmlMailSendplanModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.smtp.model.SmtpModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] メール送信に関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlSmtpSendBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlSmtpSendBiz.class);

    /** 登録種別 新規登録 */
    public static final int ENTRY_TYPE_INSERT = 0;
    /** 登録種別 更新 */
    public static final int ENTRY_TYPE_UPDATE = 1;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlSmtpSendBiz() {
    }

    /**
     * <br>[機  能] メールの送信を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wacName アカウント名
     * @param smtpData 送信サーバ情報
     * @param sendData 送信メール情報
     * @param domain ドメイン
     * @return メッセージ番号
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException メールの文字コードが不正
     * @throws MessagingException 送信先アドレスの解析に失敗
     * @throws IOException 添付ファイルの登録に失敗
     * @throws IOToolsException 添付ファイルの登録に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public long sendMail(int wacSid, String wacName,
                        SmtpModel smtpData, SmtpSendModel sendData, String domain)
    throws SQLException, UnsupportedEncodingException, MessagingException,
            IOException, IOToolsException, TempFileException {
        WmlSendMailModel mailData = new WmlSendMailModel();
        mailData.setSubject(sendData.getSubject());
        mailData.addFrom(sendData.getFrom());
        mailData.setTo(getAddressList(sendData.getTo()));
        mailData.setCc(getAddressList(sendData.getCc()));
        mailData.setBcc(getAddressList(sendData.getBcc()));

        mailData.setContent(sendData.getBody());
        mailData.setHtmlMail(sendData.isHtmlMail());
        mailData.setHeaderMap(sendData.getHeaderData());

        try {
            return sendMail(wacSid, wacName, sendData, smtpData,
                                mailData, sendData.getTempFileList(), domain);
        } finally {
            mailData = null;
        }
    }

    /**
     * <br>[機  能] メールの送信を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wacName アカウント名
     * @param smtpSendData 送信メール情報
     * @param smtpData 送信サーバ情報
     * @param mailData 送信メール情報
     * @param tempFileList 添付ファイルのリスト
     * @param domain ドメイン
     * @return メッセージ番号
     * @throws SQLException SQL実行時例外
     * @throws MessagingException 送信先アドレスの解析に失敗
     * @throws IOException 添付ファイルの登録に失敗
     * @throws IOToolsException 添付ファイルの登録に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public long sendMail(int wacSid,
                        String wacName,
                        SmtpSendModel smtpSendData,
                        SmtpModel smtpData,
                        WmlSendMailModel mailData,
                        List<WmlMailFileModel> tempFileList,
                        String domain)
        throws SQLException, MessagingException,
            IOException, IOToolsException, TempFileException {
        WmlMailModel sendData = new WmlMailModel();
        WmlSmtpSender sender = null;
        try {

            try {
                sender = new WmlSmtpSender();
                sender.connect(smtpData);
            } catch (Exception e) {
                log__.warn("送信メールサーバの接続に失敗 アカウント[" + wacName + "]", e);
                sender.disConnect();
                return -1;
            }

            SmtpSendModel sendMailData = createSmtpSendModel(mailData, tempFileList);
            MimeMessage sendMessage = sender.send(sendMailData);

            @SuppressWarnings("all")
            Enumeration headers = sendMessage.getAllHeaders();
            while (headers.hasMoreElements()) {
                Header header = (Header) headers.nextElement();
                sendData.addHeader(header.getName(), header.getValue());
                header = null;
            }
            sendData.setSendDate(UDate.getInstanceDate(sendMessage.getSentDate()));
            sendData.setSubject(mailData.getSubject());
            sendData.setContent(mailData.getContent());

            sendData.addFrom(mailData.getFrom().get(0));
            sendData.setTo(getAddress(sendMessage, "to"));
            sendData.setCc(getAddress(sendMessage, "cc"));
            sendData.setBcc(getAddress(sendMessage, "bcc"));
            sendData.setTempFileList(tempFileList);

            sendMessage = null;
        } finally {
            if (sender != null) {
                sender.disConnect();
            }
        }

        SmtpSendModel sendModel = new SmtpSendModel();
        sendModel.setCon(smtpSendData.getCon());
        sendModel.setWdrSid(smtpSendData.getWdrSid());
        sendModel.setUserSid(smtpSendData.getUserSid());
        sendModel.setGsContext(smtpSendData.getGsContext());
        sendModel.setHtmlMail(mailData.isHtmlMail());
        long messageNum
            = entrySendMailData(wacSid, sendModel, sendData,
                                        ENTRY_TYPE_INSERT, false,
                                        smtpData.getEncode(), domain);
        sendData = null;

        //統計機能 送信メール集計用データを登録する
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.regSmailLogCnt(
                smtpSendData.getCon(),
                wacSid,
                mailData.getTo().size(),
                mailData.getCc().size(),
                mailData.getBcc().size(),
                new UDate());


        return messageNum;
    }

    /**
     * <br>[機  能] 送信メール情報の登録を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param sendModel 送信情報
     * @param mailData メール情報
     * @param entryType 登録種別
     * @param draft true:草稿 false:送信
     * @param encode 文字コード
     * @param domain ドメイン
     * @return メッセージ番号
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイルの登録に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public long entrySendMailData(int wacSid,
                                SmtpSendModel sendModel,
                                WmlMailModel mailData,
                                int entryType,
                                boolean draft,
                                String encode,
                                String domain)
    throws SQLException, IOToolsException, TempFileException {

        long mailNum = mailData.getMailNum();
        try {
            if (entryType == ENTRY_TYPE_INSERT) {
                mailNum = (GroupSession.getResourceManager().getCountController(domain))
                                    .getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                                                    GSConstWebmail.SBNSID_SUB_MESSAGE,
                                                    sendModel.getUserSid());
                mailData.setMailNum(mailNum);
            }
    
            WmlMaildataModel sendData = new WmlMaildataModel();
            sendData.setWmdMailnum(mailNum);
    
            //件名
            sendData.setWmdTitle(NullDefault.getString(mailData.getSubject(), ""));
            //件名が1000文字以上の場合は切捨て
            if (sendData.getWmdTitle().length() > 1000) {
                sendData.setWmdTitle(sendData.getWmdTitle().substring(0, 1000));
            }
            //送信日時
            sendData.setWmdSdate(new UDate());
            //送信元
            sendData.setWmdFrom(mailData.getFrom().get(0));
            //添付ファイルフラグ
            if ((mailData.getTempFileList() != null && !mailData.getTempFileList().isEmpty())
            || (sendModel.isHtmlMail() && !draft)) {
                sendData.setWmdTempflg(GSConstWebmail.TEMPFLG_EXIST);
            } else {
                sendData.setWmdTempflg(GSConstWebmail.TEMPFLG_NOTHING);
            }
            //状態,未読/既読フラグ
            sendData.setWmdStatus(GSConstWebmail.WMD_STATUS_NORMAL);
            sendData.setWmdReaded(GSConstWebmail.WMD_READED_YES);
    
            //転送フラグ,返信フラグ
            sendData.setWmdReply(GSConstWebmail.WMD_REPLY_NORMAL);
            sendData.setWmdForward(GSConstWebmail.WMD_FORWARD_NORMAL);
    
            //保存先アカウントSID
            sendData.setWacSid(wacSid);
    
            //保存先ディレクトリSID
            sendData.setWdrSid(sendModel.getWdrSid());
    
            //登録日時、編集元メール、編集種別
            sendData.setWmdAdate(new UDate());
            sendData.setWmdOrigin(sendModel.getOriginMailNum());
            sendData.setWmdEditType(sendModel.getEditType());
    
            WmlBiz wmlBiz = new WmlBiz();
            try {
                //メールサイズ
                sendModel.setBody(mailData.getContent());
                sendModel.setTempFileList(mailData.getTempFileList());
                sendData.setWmdSize(wmlBiz.getSendMailSize(sendModel, encode));
    
                WmlMaildataDao mailDataDao = new WmlMaildataDao(sendModel.getCon());
                if (entryType == ENTRY_TYPE_UPDATE) {
                    mailDataDao.update(sendData);
                } else {
                    mailDataDao.insert(sendData);
                }
                sendData = null;
                mailDataDao = null;
    
                //メール本文を登録する
                WmlMailBodyModel mailBodyMdl = new WmlMailBodyModel();
                mailBodyMdl.setWmdMailnum(mailNum);
                mailBodyMdl.setWmbCharset(encode);
                mailBodyMdl.setWacSid(wacSid);
                if (sendModel.isHtmlMail()) {
                    mailBodyMdl.setWmbBody(WmlSmtpSender.formatHtmlToText(mailData.getContent()));
                    if (!draft) {
                        WmlMailFileModel fileMdl = new WmlMailFileModel();
                        fileMdl.setFileName(GSConstWebmail.HTMLMAIL_FILENAME);
                        fileMdl.setContentType("Content-Type: text/html; charset=" + encode);
                        fileMdl.setFilePath(
                                __getHtmlMailFilePath(sendModel.getGsContext(), mailNum, domain));
                        fileMdl.setHtmlMail(true);
                        mailData.getTempFileList().add(fileMdl);
    
                        PrintWriter pw = null;
                        try {
                            IOTools.isDirCheck(
                                    __getHtmlMailDirPath(sendModel.getGsContext(),
                                            mailNum, domain), true);
                            pw = new PrintWriter(new BufferedWriter(new
                                    OutputStreamWriter(new FileOutputStream(fileMdl.getFilePath()),
                                            encode)));
                            pw.print(mailData.getContent());
                            pw.flush();
                        } catch (Exception e) {
                            log__.error("HTMLメールの保存に失敗", e);
                            throw new IOToolsException("HTMLメールの保存に失敗", e);
                        } finally {
                            if (pw != null) {
                                pw.close();
                            }
                        }
                    }
                } else {
                    mailBodyMdl.setWmbBody(mailData.getContent());
                }
                WmlMailBodyDao mailBodyDao = new WmlMailBodyDao(sendModel.getCon());
                if (entryType == ENTRY_TYPE_UPDATE) {
                    mailBodyDao.update(mailBodyMdl);
                } else {
                    mailBodyDao.insert(mailBodyMdl);
                }
                mailBodyMdl = null;
                mailBodyDao = null;
    
                //送信先を登録する
                WmlSendaddressDao sendAddressDao = new WmlSendaddressDao(sendModel.getCon());
                if (entryType == ENTRY_TYPE_UPDATE) {
                    sendAddressDao.delete(mailNum);
                }
                sendAddressDao.insertSendAddress(mailNum, wacSid,
                                                mailData.getTo(), mailData.getCc(), mailData.getBcc(),
                                                mailData.getRenban());
                sendAddressDao = null;
    
                //添付ファイルを保存する
                if (entryType == ENTRY_TYPE_UPDATE) {
                    WmlTempfileDao tempfileDao = new WmlTempfileDao(sendModel.getCon());
                    tempfileDao.deleteMail(mailNum);
                }
    
                if (mailData.getTempFileList() != null && !mailData.getTempFileList().isEmpty()) {
                    try {
                        wmlBiz.insertBinInfo(sendModel.getCon(), mailData.getTempFileList(),
                                            (String) sendModel.getGsContext().get(
                                                                GSContext.APP_ROOT_PATH),
                                            GroupSession.getResourceManager()
                                            .getCountController(domain),
                                            mailNum, sendModel.getUserSid(), new UDate());
                    } catch (TempFileException e) {
                        Throwable cause = GSAttachFileNotExistException.searchCause(e);
                        if (cause != null
                                && !draft && !sendModel.isTimeSent()) {
                            log__.error("", cause);
    //                      送信時ファイル添付に失敗した場合
    //                  　　テキストデータ「${ファイル名}を保管できませんでした」を代わりに登録する
    //                  　　ファイル名：添付ファイル保管エラー.txt
                            wmlBiz.insertBinInfoError(sendModel.getCon(),
                                    (String) sendModel.getGsContext().get(
                                            GSContext.APP_ROOT_PATH),
                                    GroupSession.getResourceManager()
                                    .getCountController(domain),
                                    sendModel.getUserSid(),
                                    new UDate(),
                                    mailData.getTempFileList(),
                                    mailNum);
                        } else {
                            throw e;
                        }
                    }
    
                    if (sendModel.isHtmlMail()) {
                        String htmlFilePath
                                = __getHtmlMailFilePath(sendModel.getGsContext(), mailNum, domain);
                        if ((new File(htmlFilePath)).exists()) {
                            IOTools.deleteFile(htmlFilePath);
                        }
                    }
                }
    
                if (sendModel.isTimeSent()) {
                    //メール情報_送信予約を登録
                    WmlMailSendplanModel sendPlanMdl = new WmlMailSendplanModel();
                    sendPlanMdl.setWmdMailnum(mailNum);
                    sendPlanMdl.setWacSid(wacSid);
    
                    if (draft) {
                        sendPlanMdl.setWspSendkbn(GSConstWebmail.WSP_SENDKBN_NOSET);
                    } else {
                        sendPlanMdl.setWspSendkbn(GSConstWebmail.WSP_SENDKBN_SENDTARGET);
                    }
    
                    sendPlanMdl.setWspSenddate(sendModel.getSendPlanDate());
                    if (sendModel.isHtmlMail()) {
                        sendPlanMdl.setWspMailtype(GSConstWebmail.WSP_MAILTYPE_HTML);
                    } else {
                        sendPlanMdl.setWspMailtype(GSConstWebmail.WSP_MAILTYPE_NORMAL);
                    }
    
                    sendPlanMdl.setWspCompressFile(sendModel.getSendPlanCompressFileType());
    
                    WmlMailSendplanDao sendPlanDao = new WmlMailSendplanDao(sendModel.getCon());
                    if (sendPlanDao.update(sendPlanMdl) <= 0) {
                        sendPlanDao.insert(sendPlanMdl);
                    }
                }
    
                if (!draft && !sendModel.isTimeSent()) {
                    //メールヘッダー情報を登録
                    if (!mailData.getHeaderKey().isEmpty()) {
                        List<WmlHeaderDataModel> headerDataList
                            = wmlBiz.createHeaderDataList(mailNum, mailData, wacSid);
                        WmlHeaderDataDao headerDao = new WmlHeaderDataDao(sendModel.getCon());
                        headerDao.insert(headerDataList);
    
                        headerDataList = null;
                        headerDao = null;
                    }
    
                    //---- メール送信ログ start ----
                    if (sendModel.getLogRegist() == null) {
                        WmlAdmConfDao admConfDao = new WmlAdmConfDao(sendModel.getCon());
                        sendModel.setLogRegist(admConfDao.selectAdmData().getWadAcctLogRegist());
                        admConfDao = null;
                    }
    
                    if (sendModel.getLogRegist().intValue() == GSConstWebmail.ACNT_LOG_REGIST_REGIST) {
                        //メール履歴を登録する
                        WmlMailLogModel mailLogModel = new WmlMailLogModel();
                        mailLogModel.setWmdMailnum(mailNum);
                        mailLogModel.setWlgTitle(NullDefault.getString(mailData.getSubject(), ""));
                        mailLogModel.setWlgDate(mailData.getSendDate());
                        if (mailData.getFrom() != null && !mailData.getFrom().isEmpty()) {
                            mailLogModel.setWlgFrom(mailData.getFrom().get(0));
                        }
                        if (mailData.getTempFileList() != null
                        && !mailData.getTempFileList().isEmpty()) {
                            mailLogModel.setWlgTempflg(GSConstWebmail.TEMPFLG_EXIST);
                        } else {
                            mailLogModel.setWlgTempflg(GSConstWebmail.TEMPFLG_NOTHING);
                        }
                        mailLogModel.setWlgMailtype(GSConstWebmail.MAILTYPE_SEND);
                        mailLogModel.setWacSid(wacSid);
    
                        WmlMailLogDao mailLogDao = new WmlMailLogDao(sendModel.getCon());
                        mailLogDao.insert(mailLogModel);
                        mailLogDao = null;
                        mailLogModel = null;
    
                        //メール履歴_送信先を登録する
                        WmlMailLogSendDao mailLogSendDao = new WmlMailLogSendDao(sendModel.getCon());
                        WmlMailLogSendModel mailLogSendModel = new WmlMailLogSendModel();
                        mailLogSendModel.setWmdMailnum(mailNum);
                        mailLogSendModel.setWacSid(wacSid);
    
                        int wlsNum = 1;
                        wlsNum = __insertMailLogSend(mailLogSendDao, mailLogSendModel,
                                                    mailData.getTo(),
                                                    wlsNum, GSConstWebmail.WSA_TYPE_TO);
                        wlsNum = __insertMailLogSend(mailLogSendDao, mailLogSendModel,
                                                    mailData.getCc(),
                                                    wlsNum, GSConstWebmail.WSA_TYPE_CC);
                        wlsNum = __insertMailLogSend(mailLogSendDao, mailLogSendModel,
                                                    mailData.getBcc(),
                                                    wlsNum, GSConstWebmail.WSA_TYPE_BCC);
    
                        mailLogSendDao = null;
                        mailLogSendModel = null;
    
    
                        //メール送受信ログのデータ使用量を登録
                        WmlUsedDataBiz usedDataBiz = new WmlUsedDataBiz(sendModel.getCon());
                        usedDataBiz.insertMaillogSize(mailNum, true);
                        usedDataBiz = null;
    
                    }
                    //---- メール送信ログ end ----
                }
    
                //アカウントディスク容量の再計算
                wmlBiz.updateAccountDiskSize(sendModel.getCon(), wacSid);
    
            } finally {
                wmlBiz = null;
    
            }
    
            return mailNum;
        } catch (SQLException e) {
            throw e;
        } catch (IOToolsException e) {
            throw e;
        } catch (TempFileException e) {
            throw e;
        } catch (Exception e) {
            //予期しない例外についてはRuntimeExceptionとして返す
            throw new RuntimeException(e);
        }

    }

    /**
     * <br>[機  能] メール履歴_送信先の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mlSendDao メール履歴_送信先Dao
     * @param mlSendData メール履歴_送信先Model
     * @param addressList 送信先アドレス
     * @param wlsNum 送信先No
     * @param wlsType 送信先種別
     * @return 送信先No
     * @throws SQLException SQL時刻事例外
     */
    private int __insertMailLogSend(WmlMailLogSendDao mlSendDao, WmlMailLogSendModel mlSendData,
                                    List<String> addressList, int wlsNum, int wlsType)
    throws SQLException {

        if (addressList != null) {

            for (String address : addressList) {
                mlSendData.setWlsNum(wlsNum);
                mlSendData.setWlsType(wlsType);
                mlSendData.setWlsAddress(address);
                mlSendDao.insert(mlSendData);

                wlsNum++;
            }
        }

        return wlsNum;
    }

    /**
     * <br>[機  能] To, Cc, Bccアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param msg 送信メール情報
     * @param type アドレス種別("to" or "cc" or "bcc")
     * @return アドレス
     * @throws MessagingException アドレスの取得に失敗
     * @throws UnsupportedEncodingException アドレスのデコードに失敗
     */
    public List<String> getAddress(MimeMessage msg, String type)
    throws MessagingException, UnsupportedEncodingException {
        List<String> addressList = null;

        if (type.equals("to")) {
            addressList = WmlBiz.getAddressList(msg.getRecipients(MimeMessage.RecipientType.TO));
        } else if (type.equals("cc")) {
            addressList = WmlBiz.getAddressList(msg.getRecipients(MimeMessage.RecipientType.CC));
        } else if (type.equals("bcc")) {
            addressList = WmlBiz.getAddressList(msg.getRecipients(MimeMessage.RecipientType.BCC));
        }

        if (addressList == null) {
            return null;
        }

        List<String> address = new ArrayList<String>(addressList.size());
        InternetAddress[] iAddress = null;
        for (int i = 0; i < addressList.size(); i++) {
            iAddress = InternetAddress.parse(addressList.get(i), true);
            if (!StringUtil.isNullZeroString(iAddress[0].getPersonal())) {
                address.add(
                        MimeUtility.decodeText(iAddress[0].getPersonal())
                        + " <" + iAddress[0].getAddress() + ">");
            } else {
                address.add(iAddress[0].getAddress());
            }
        }

        return address;
    }

    /**
     * <br>[機  能] 送信メール情報を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailData 送信メール情報
     * @param tempFileList 添付ファイル情報
     * @return 送信メール情報
     */
    public SmtpSendModel createSmtpSendModel(WmlSendMailModel mailData,
                                                            List<WmlMailFileModel> tempFileList) {
        SmtpSendModel sendMailData = new SmtpSendModel();
        sendMailData.setSubject(mailData.getSubject());
        sendMailData.setFrom(mailData.getFrom().get(0));
        sendMailData.setTo(__createAddress(mailData.getTo()));
        sendMailData.setCc(__createAddress(mailData.getCc()));
        sendMailData.setBcc(__createAddress(mailData.getBcc()));
        sendMailData.setBody(mailData.getContent());

        if (!StringUtil.isNullZeroString(sendMailData.getBody())
        && !sendMailData.getBody().endsWith("\n")) {
            sendMailData.setBody(sendMailData.getBody() + "\n");
        }

        sendMailData.setTempFileList(tempFileList);
        sendMailData.setHtmlMail(mailData.isHtmlMail());
        sendMailData.setHeaderData(mailData.getHeaderMap());

        return sendMailData;
    }

    /**
     * <br>[機  能] アドレス一覧からメール送信用アドレスを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param addressList アドレス一覧
     * @return メール送信用アドレス
     */
    private String __createAddress(List<String> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            return null;
        }

        String sendAddress = addressList.get(0).toString();
        for (int i = 1; i < addressList.size(); i++) {
            sendAddress += "," + addressList.get(i);
        }

        return sendAddress;
    }

    /**
     * <br>[機  能] HTMLメール添付ファイルの保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GSContext
     * @param mailNum メッセージ番号
     * @param domain ドメイン
     * @return HTMLメール添付ファイルのパス
     */
    private String __getHtmlMailDirPath(GSContext gsContext, long mailNum, String domain) {
        return GroupSession.getResourceManager().getTempPath(domain)
                + "/webmail/" + mailNum + "/";
    }

    /**
     * <br>[機  能] HTMLメール添付ファイルのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GSContext
     * @param mailNum メッセージ番号
     * @param domain ドメイン
     * @return HTMLメール添付ファイルのパス
     */
    private String __getHtmlMailFilePath(GSContext gsContext, long mailNum, String domain) {
        return __getHtmlMailDirPath(gsContext, mailNum, domain) + GSConstWebmail.HTMLMAIL_FILENAME;
    }

    /**
     * <br>[機  能] メールアドレスの解析を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailAddress メールアドレス
     * @return 解析後のメールアドレス
     * @throws AddressException メールアドレスの解析に失敗
     */
    public List<String> getAddressList(String mailAddress) throws AddressException {
        return WmlBiz.getAddressList(__formatSendAddress(mailAddress));
    }

    /**
     * <br>[機  能] 送信先メールアドレスのフォーマットを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailAddress メールアドレス
     * @return フォーマット後のメールアドレス
     * @throws AddressException フォーマットに失敗
     */
    private String __formatSendAddress(String mailAddress) throws AddressException {
        if (StringUtil.isNullZeroString(mailAddress)) {
            return mailAddress;
        }
        InternetAddress[] parsedList = InternetAddress.parse(mailAddress, true);
        StringBuilder formatedAddress = new StringBuilder("");
        for (InternetAddress internetAddress__ : parsedList) {
            if (formatedAddress.length() > 0) {
                formatedAddress.append(",");
            }
            formatedAddress.append(WmlBiz.convertIAdress2String(internetAddress__));
        }
        return formatedAddress.toString();
    }
}
