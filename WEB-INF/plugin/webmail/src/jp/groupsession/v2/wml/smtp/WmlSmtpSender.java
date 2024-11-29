package jp.groupsession.v2.wml.smtp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.mail.MailUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.pop3.Pop3Server;
import jp.groupsession.v2.wml.smtp.model.SmtpModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] メールの送信を行う
 * <br>[解  説]
 * <br>[備  考] connect → send → disconnectの順に使用してください。
 *
 * @author JTS
 */
public class WmlSmtpSender {

    /** Logging インスタンス */
    static Log log__ = LogFactory.getLog(WmlSmtpSender.class);

    /** デフォルトポート番号 */
    public static final int DEFAULT_PORT = 25;
    /** X-Mailer */
    private String mailerName__ = "GroupSession" + GSConst.VERSION;

    /** メール送信オブジェクト */
    private Transport transport__;
    /** SMTPサーバとのセッション */
    private Session session__;
    /** POPサーバとのセッション */
    private Store store__;
    /** エンコード */
    private String encode__ = Encoding.ISO_2022_JP;

    /** OAuth認証時のリターンコード */
    private static final int RESPONSE_CODE_OAUTH_SUCCESS__ = 235;


    /**
     * <br>[機  能] メールサーバに接続します。
     * <br>[解  説]
     * <br>[備  考]
     * @param host メールサーバ
     * @throws MessagingException MessagingException
     */
    public synchronized void connect(String host) throws MessagingException {
        connect(host, DEFAULT_PORT, null, null);
    }

    /**
     * <br>[機  能] メールサーバに接続します。
     * <br>[解  説]
     * <br>[備  考] ポート番号がデフォルトでない場合に使用します。
     *
     * @param host メールサーバ
     * @param port ポート番号
     * @throws MessagingException MessagingException
     */
    public synchronized void connect(String host, int port)
            throws MessagingException {
        connect(host, port, null, null);
    }

    /**
     * <br>[機  能] メールサーバに接続します。
     * <br>[解  説]
     * <br>[備  考] ユーザ認証が必要な場合に使用します。
     * @param host メールサーバ
     * @param port ポート
     * @param userName 認証ユーザ名
     * @param password パスワード
     * @throws MessagingException MessagingException
     */
    public synchronized void connect(String host, int port,
                                        String userName, String password)
    throws MessagingException {
        SmtpModel smtpData = new SmtpModel();
        smtpData.setSendServer(host);
        smtpData.setSendPort(port);
        smtpData.setSendUser(userName);
        smtpData.setSendPassword(password);
        smtpData.setSmtpAuth(true);
        connect(smtpData);
    }

    /**
     * <br>[機  能] メールサーバに接続します。
     * <br>[解  説]
     * <br>[備  考]
     * @param smtpData SMTPサーバ接続情報
     * @throws MessagingException MessagingException
     */
    public synchronized void connect(SmtpModel smtpData)
    throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.connectiontimeout", "60000");
        prop.setProperty("mail.smtp.timeout", "60000");

        if (smtpData.getAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.starttls.required", "true");
            prop.put("mail.smtp.sasl.enable", "false");

            session__ = Session.getInstance(prop, null);

            transport__ = session__.getTransport("smtp");
            transport__.connect(smtpData.getSendServer(),
                                smtpData.getSendPort(),
                                smtpData.getSendUser(), null);

            byte[] response = String.format("user=%s\1auth=Bearer %s\1\1",
                                            smtpData.getSendUser(),
                                            smtpData.getAccessToken()).getBytes();
            response = BASE64EncoderStream.encode(response);

            ((SMTPTransport) transport__).issueCommand(
                    "AUTH XOAUTH2 " + new String(response),
                    RESPONSE_CODE_OAUTH_SUCCESS__);

        } else {

            if (smtpData.isSmtpAuth() || smtpData.isPopBeforeSmtp()) {
                prop.put("mail.smtp.auth", "true");
            }

            if (smtpData.getSendEncrypt()
                    == MailEncryptBiz.ANGO_SSL_TLS) {
                prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                prop.setProperty("mail.smtp.socketFactory.fallback", "false");
                prop.setProperty("mail.smtp.socketFactory.port",
                                String.valueOf(smtpData.getSendPort()));
            } else if (smtpData.getSendEncrypt()
                    == MailEncryptBiz.ANGO_STARTTLS) {
                prop.setProperty("mail.smtp.starttls.enable", "true");
            }
            if (smtpData.isPopBeforeSmtp()) {
                if (smtpData.getPopServerEncrypt()
                        == MailEncryptBiz.ANGO_SSL_TLS) {
                    prop.setProperty("mail.pop3.socketFactory.class", Pop3Server.SSL_FACTORY);
                    prop.setProperty("mail.pop3.socketFactory.fallback", "false");
                    prop.setProperty("mail.pop3.port", String.valueOf(smtpData.getPopServerPort()));
                    prop.setProperty("mail.pop3.socketFactory.port",
                                    String.valueOf(smtpData.getPopServerPort()));
                } else if (smtpData.getPopServerEncrypt()
                        == MailEncryptBiz.ANGO_STARTTLS) {
                    prop.setProperty("mail.pop3.starttls.enable", "true");
                }
            }

            session__ = Session.getInstance(prop, null);
            transport__ = session__.getTransport("smtp");
            if (smtpData.isPopBeforeSmtp()) {
                store__ = session__.getStore("pop3");
                store__.connect(smtpData.getPopServer(), smtpData.getPopServerPort(),
                                smtpData.getPopServerUser(), smtpData.getPopServerPassword());

                transport__.connect(smtpData.getSendServer(), smtpData.getSendPort(),
                                    smtpData.getPopServerUser(),
                                    smtpData.getPopServerPassword());
            } else if (smtpData.isSmtpAuth()) {
                transport__.connect(smtpData.getSendServer(), smtpData.getSendPort(),
                                    smtpData.getSendUser(), smtpData.getSendPassword());
            } else {
                transport__.connect(smtpData.getSendServer(), smtpData.getSendPort(),
                                    null, null);
            }
        }


        if (!StringUtil.isNullZeroString(smtpData.getEncode())) {
            encode__ = smtpData.getEncode();
        }
    }

    /**
     * <br>[機  能] メールサーバとのコネクションを切断します。
     * <br>[解  説]
     * <br>[備  考]
     */
    public synchronized void disConnect() {
        try {
            if (transport__ != null) {
                transport__.close();
            }

            if (store__ != null) {
                store__.close();
            }
        } catch (javax.mail.MessagingException e) {
        }
    }

    /**
     * <br>[機  能] ガーベッジコレクションにかかる時にコネクションをクローズします。
     * <br>[解  説]
     * <br>[備  考]
     * @throws Throwable Throwable
     */
    protected void finalize() throws Throwable {
        disConnect();
    }

    /**
     * <br>[機  能] MimeMessageを作成します。
     * <br>[解  説]
     * <br>[備  考]　<p>セッションオブジェクトの外部からのアクセスを禁止したため
     *               <br>このメソッドは必須です。</p>
     *
     * @return MimeMessage
     */
    public MimeMessage createMimeMessage() {
        return new MimeMessage(session__);
    }

    /**
     * <br>[機  能] MimeMessageを作成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sendData 送信メール情報
     * @return MemeMessage
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public MimeMessage createMimeMessage(SmtpSendModel sendData)
        throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = createMimeMessage();
        message.addFrom(WmlBiz.parseAddressPlus(sendData.getFrom(), encode__));

        if (!StringUtil.isNullZeroString(sendData.getTo())) {
            message.setRecipients(MimeMessage.RecipientType.TO,
                                WmlBiz.parseAddressPlus(sendData.getTo(), encode__));
        }
        if (!StringUtil.isNullZeroString(sendData.getCc())) {
            message.setRecipients(MimeMessage.RecipientType.CC,
                                WmlBiz.parseAddressPlus(sendData.getCc(), encode__));
        }
        if (!StringUtil.isNullZeroString(sendData.getBcc())) {
            message.setRecipients(MimeMessage.RecipientType.BCC,
                                WmlBiz.parseAddressPlus(sendData.getBcc(), encode__));
        }
        message.setSubject(sendData.getSubject(), encode__);

        if (sendData.getHeaderData() != null && !sendData.getHeaderData().isEmpty()) {
            Iterator<String> keyIter = sendData.getHeaderData().keySet().iterator();
            while (keyIter.hasNext()) {
                String name = keyIter.next();
                List<String> valueList = sendData.getHeaderData().get(name);
                for (String headerValue : valueList) {
                    message.addHeader(name, headerValue);
                }
            }
        }

        if (sendData.getTempFileList() == null || sendData.getTempFileList().isEmpty()) {
            if (sendData.isHtmlMail()) {
                Multipart mp = new MimeMultipart();

                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(formatHtmlToText(sendData.getBody()), encode__);
                mp.addBodyPart(textPart);

                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(sendData.getBody(), "text/html; charset=" + encode__);
                mp.addBodyPart(htmlPart);

                message.setContent(mp);
            } else {
                message.setText(sendData.getBody(), encode__);
            }
        } else {

            // 複数のボディを格納するマルチパートオブジェクトを生成
            Multipart mp = new MimeMultipart();
            // ボディパートを作成
            MimeBodyPart mbpMain = new MimeBodyPart();
            // メールの内容を指定
            if (sendData.isHtmlMail()) {
                mbpMain.setContent(sendData.getBody(), "text/html; charset=" + encode__);
            } else {
                mbpMain.setText(sendData.getBody(), encode__);
            }
            //ボディパートの追加
            mp.addBodyPart(mbpMain);

            // 添付ファイルを作成
            for (WmlMailFileModel fileData : sendData.getTempFileList()) {
                __addTempFile(mp, new File(fileData.getFilePath()), fileData.getFileName());
            }
            // マルチパートオブジェクトをメッセージに設定
            message.setContent(mp);
        }
        return message;
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] メール送信では基本的にはこのsendメソッドを使用します。
     * @param message メッセージ
     * @return 送信したメール情報
     * @throws MessagingException MessagingException
     */
    public MimeMessage send(MimeMessage message) throws javax.mail.MessagingException {
        //getAllRecipients()でto,cc,bcc等のenvelopeToを取得する
        return send(message, message.getAllRecipients());
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] 送信先を指定します。
     * @param message メッセージ
     * @param envelopeTo 送信先
     * @return 送信したメール情報
     * @throws MessagingException MessagingException
     */
    public MimeMessage send(MimeMessage message, Address[] envelopeTo)
            throws MessagingException {
        message.setSentDate(new Date()); //送信日付をセット
        //fromアドレスからMessage-IDをセットする。
        String fromAddress = ((InternetAddress) message.getFrom()[0])
                .getAddress();
        Properties prop = session__.getProperties();
        prop.setProperty("mail.from", fromAddress);
        message.saveChanges();
        message.setHeader("X-Mailer", mailerName__);
        transport__.sendMessage(message, envelopeTo);
        return message;
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] 全てStringで実装したインスタントメソッドです。
     *
     * @param subject サブジェクト
     * @param from fromアドレス
     * @param to toアドレス
     * @param body 本文
     * @return 送信したメール情報
     * @throws MessagingException MessagingException
     */
    public MimeMessage send(String subject, String from, String to, String body)
            throws MessagingException {

        return send(subject, from, to, null, null, body);
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] 全てStringで実装したインスタントメソッドです。
     *
     * @param subject サブジェクト
     * @param from fromアドレス
     * @param to toアドレス
     * @param cc ccアドレス
     * @param bcc bccアドレス
     * @param body 本文
     * @return 送信したメール情報
     * @throws MessagingException MessagingException
     */
    public MimeMessage send(String subject, String from, String to, String cc, String bcc,
                            String body)
    throws MessagingException {

        MimeMessage sendMessage = null;
        try {
            SmtpSendModel sendData = new SmtpSendModel();
            sendData.setSubject(subject);
            sendData.setFrom(from);
            sendData.setTo(to);
            sendData.setCc(cc);
            sendData.setBcc(bcc);
            sendData.setBody(body);
            sendData.setTempFileList(null);
            sendData.setHtmlMail(false);
            sendMessage = send(sendData);
        } catch (UnsupportedEncodingException e) {
        }

        return sendMessage;
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] <p>メッセージのfromではなく指定したアドレスをReply-Toに設定します。
     *              <br>またメールの送信がエラーになった場合のReturn-Pathもこのアドレスにします。</p>
     *
     * @param message メッセージ
     * @return 送信したメール情報
     * @param replyAddress Reply用アドレス
     * @throws MessagingException MessagingException
     */
    public MimeMessage sendReplyOption(MimeMessage message, String replyAddress)
        throws MessagingException {
        Properties prop = session__.getProperties();
        prop.setProperty("mail.smtp.from", replyAddress);
        message.setReplyTo(InternetAddress.parse(replyAddress, true));
        MimeMessage sendMessage = send(message);
        prop.remove("mail.smtp.from");
        return sendMessage;
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] 全てStringで実装したインスタントメソッドです。
     * @param sendData 送信メール情報
     * @return 送信したメール情報
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public MimeMessage send(SmtpSendModel sendData)
        throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = createMimeMessage(sendData);
        return send(message);
    }

    /**
     * <p>mailerName を取得します。
     * @return mailerName
     */
    public String getMailerName() {
        return mailerName__;
    }

    /**
     * <p>mailerName をセットします。
     * @param mailerName mailerName
     */
    public void setMailerName(String mailerName) {
        mailerName__ = mailerName;
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] 全てStringで実装したインスタントメソッドです。
     * @param subject サブジェクト
     * @param from fromアドレス
     * @param to toアドレス
     * @param body 本文
     * @param fileList 添付ファイルのリスト(java.io.File)
     * @return 送信したメール情報
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public MimeMessage sendFile(
        String subject,
        String from,
        String to,
        String body,
        List<TempFileModel> fileList)
        throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = createMimeMessage();
        message.addFrom(InternetAddress.parse(from, true));

        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress
                .parse(to, true));

        message.setSubject(subject, encode__);

        // 複数のボディを格納するマルチパートオブジェクトを生成
        Multipart mp = new MimeMultipart();
        // ボディパートを作成
        MimeBodyPart mbpMain = new MimeBodyPart();
        // メールの内容を指定
        mbpMain.setText(body, encode__);
        //ボディパートの追加
        mp.addBodyPart(mbpMain);

        // 添付ファイルを作成
        for (TempFileModel tempFileMdl : fileList) {
            __addTempFile(mp, tempFileMdl.getFile(), tempFileMdl.getFileName());
        }
        // マルチパートオブジェクトをメッセージに設定
        message.setContent(mp);

        return send(message);
    }

    /**
     * <br>[機  能] HTMLをテキスト形式に変換する
     * <br>[解  説]
     * <br>[備  考] HTMLメール送信時に使用する
     * @param value HTML
     * @return valueをテキスト形式に変換したもの
     */
    public static String formatHtmlToText(String value) {
        if (StringUtil.isNullZeroString(value)) {
            return value;
        }

        String formatValue = "";
        String[] spaceArray = null;
        value = StringUtilHtml.replaceString(value, "<br>", "<BR>");
        value = StringUtilHtml.replaceString(value, "<br />", "<BR>");
        value = StringUtilHtml.replaceString(value, "<br/>", "<BR>");
        String[] lineArray = value.split("<BR>");
        for (String lineValue : lineArray) {
            if (lineValue.length() > 0) {
                spaceArray = lineValue.split("&nbsp;");
                for (int spaceIdx = 0; spaceIdx < spaceArray.length; spaceIdx++) {
                    if (spaceIdx > 0) {
                        formatValue += " ";
                    }
                    formatValue += transToText(
                                        StringUtilHtml.deleteHtmlTag(spaceArray[spaceIdx]));
                }
                spaceArray = null;
            }
            formatValue += "\r\n";
        }
        lineArray = null;

        return formatValue;
    }

    /**
     * <br>[機  能] HTMLへエスケープされた文字を元のテキストへ変換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msg 元に戻す文字列
     * @return 変換後の文字列
     */
    public static String transToText(String msg) {
        msg = StringUtilHtml.replaceString(msg, "&lt;", "<");
        msg = StringUtilHtml.replaceString(msg, "&gt;", ">");
        msg = StringUtilHtml.replaceString(msg, "&quot;", "\"");
        msg = StringUtilHtml.replaceString(msg, "&amp;", "&");
        return msg;
    }

    /**
     * <br>[機  能] 送信メール添付ファイルの追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mp Multipart
     * @param file 添付ファイルのパス
     * @param fileName 添付ファイル名称
     * @throws MessagingException 添付ファイルの追加に失敗
     * @throws UnsupportedEncodingException 添付ファイル名変換時の文字コードが不正
     */
    private void __addTempFile(Multipart mp, File file, String fileName)
    throws MessagingException, UnsupportedEncodingException {
        MailUtil.addTempFileForSend(mp, file, fileName, encode__);
    }
}
