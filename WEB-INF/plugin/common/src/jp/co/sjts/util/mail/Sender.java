package jp.co.sjts.util.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;

import jp.co.sjts.util.Encoding;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.TempFileModel;

/**
 * <br>[機  能] メール送信オブジェクト
 * <br>[解  説]
 * <br>[備  考] connect → send → disconnectの順に使用してください。
 */
public class Sender {

    /** デフォルトポート番号 */
    public static final int DEFAULT_PORT = 25;
    /** X-Mailer */
    private String mailerName__ = "GroupSession" + GSConst.VERSION;
    /** エンコード */
    public static final String ENCODING = Encoding.UTF_8;

    /** メール送信オブジェクト */
    private Transport transport__;
    /** mailサーバとのセッション */
    private Session session__;

    /**
     * <br>[機  能] <p>デフォルト(stmp)でsession及びtransportオブジェクトを引数で指定し、
     *              <br>指定されたプロトコルのプロバイダで初期化します。</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws NoSuchProviderException NoSuchProviderException
     */
    public Sender() throws NoSuchProviderException {
        this("smtp");
    }

    /**
     * <br>[機  能] <p>session及びtransportオブジェクトを引数で指定し、
     *              <br>指定されたプロトコルのプロバイダで初期化します。</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param protocol プロトコル名
     * @throws NoSuchProviderException プロバイダが見つからなかった場合にスローされます。
     */
    public Sender(String protocol) throws NoSuchProviderException {
        this(protocol, false);
    }

    /**
     * <br>[機  能] <p>session及びtransportオブジェクトを引数で指定し、
     *              <br>指定されたプロトコルのプロバイダで初期化します。</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param useSmtpAuth true:SMTP認証を使用する, false:使用しない
     * @throws NoSuchProviderException プロバイダが見つからなかった場合にスローされます。
     */
    public Sender(boolean useSmtpAuth) throws NoSuchProviderException {
        this("smtp", useSmtpAuth);
    }

    /**
     * <br>[機  能] <p>session及びtransportオブジェクトを引数で指定し、
     *              <br>指定されたプロトコルのプロバイダで初期化します。</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param protocol プロトコル名
     * @param useSmtpAuth true:SMTP認証を使用する, false:使用しない
     * @throws NoSuchProviderException プロバイダが見つからなかった場合にスローされます。
     */
    public Sender(String protocol, boolean useSmtpAuth) throws NoSuchProviderException {
        Properties prop = new Properties();
        if (useSmtpAuth) {
            prop.put("mail.smtp.auth", "true");
        }
        session__ = Session.getInstance(prop, null);
        transport__ = session__.getTransport(protocol);
    }

    /**
     * <br>[機  能] <p>SSL専用コンストラクタ</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prop プロパティ
     * @param useSmtpAuth true:SMTP認証を使用する, false:使用しない
     * @throws MessagingException メッセージ例外
     */
    public Sender(Properties prop, boolean useSmtpAuth) throws MessagingException {
        if (useSmtpAuth) {
            prop.put("mail.smtp.auth", "true");
        }
        session__ = Session.getInstance(prop, null);
        transport__ = session__.getTransport("smtp");
    }

    /**
     * <br>[機  能] メールサーバに接続します。
     * <br>[解  説]
     * <br>[備  考]
     *
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
     *
     * @param host メールサーバ
     * @param port ポート
     * @param userName 認証ユーザ名
     * @param password パスワード
     * @throws MessagingException MessagingException
     */
    public synchronized void connect(String host, int port, String userName,
            String password) throws MessagingException {
        transport__.connect(host, port, userName, password);
    }

    /**
     * <br>[機  能] メールサーバに接続します。
     * <br>[解  説]
     * <br>[備  考] ユーザ認証が必要な場合に使用します。
     *
     * @param host メールサーバ
     * @param port ポート
     * @param accountId 認証アカウントID
     * @param accessToken
     * @throws MessagingException MessagingException
     */
    public synchronized void connectOAuth(String host, int port,
                                            String accountId,
                                            String accessToken) throws MessagingException {

        transport__ = session__.getTransport("smtp");
        transport__.connect(host, port, accountId, null);

        byte[] response = String.format("user=%s\1auth=Bearer %s\1\1",
                                        accountId,
                                        accessToken).getBytes();
        response = BASE64EncoderStream.encode(response);

        ((SMTPTransport) transport__).issueCommand(
                "AUTH XOAUTH2 " + new String(response),
                235);
    }

    /**
     * <br>[機  能] メールサーバとのコネクションを切断します。
     * <br>[解  説]
     * <br>[備  考]
     */
    public synchronized void disConnect() {
        try {
            transport__.close();
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
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] メール送信では基本的にはこのsendメソッドを使用します。
     *
     * @param message メッセージ
     * @throws MessagingException MessagingException
     */
    public void send(MimeMessage message) throws javax.mail.MessagingException {
        //getAllRecipients()でto,cc,bcc等のenvelopeToを取得する
        send(message, message.getAllRecipients());
    }

    /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] 送信先を指定します。
     *
     * @param message メッセージ
     * @param envelopeTo 送信先
     * @throws MessagingException MessagingException
     */
    public void send(MimeMessage message, Address[] envelopeTo)
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
     * @throws MessagingException MessagingException
     */
    public void send(String subject, String from, String to, String body)
            throws MessagingException {
        MimeMessage message = createMimeMessage();
        message.addFrom(InternetAddress.parse(from, true));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress
                .parse(to, true));
        message.setSubject(subject, ENCODING);
        message.setText(body, ENCODING);
        send(message);
    }

   /**
     * <br>[機  能] メールの送信を行います。
     * <br>[解  説]
     * <br>[備  考] <p>メッセージのfromではなく指定したアドレスをReply-Toに設定します。
     *              <br>またメールの送信がエラーになった場合のReturn-Pathもこのアドレスにします。</p>
     *
     * @param message メッセージ
     * @param replyAddress Reply用アドレス
     * @throws MessagingException MessagingException
     */
    public void sendReplyOption(MimeMessage message, String replyAddress)
        throws MessagingException {
        Properties prop = session__.getProperties();
        prop.setProperty("mail.smtp.from", replyAddress);
        message.setReplyTo(InternetAddress.parse(replyAddress, true));
        send(message);
        prop.remove("mail.smtp.from");
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
     *
     * @param subject サブジェクト
     * @param from fromアドレス
     * @param to toアドレス
     * @param body 本文
     * @param fileList 添付ファイルのリスト(java.io.File)
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException 添付ファイル名のエンコードが正しくない
     */
    public void sendFile(
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

        message.setSubject(subject, ENCODING);

        // 複数のボディを格納するマルチパートオブジェクトを生成
        Multipart mp = new MimeMultipart();
        // ボディパートを作成
        MimeBodyPart mbpMain = new MimeBodyPart();
        // メールの内容を指定
        mbpMain.setText(body, ENCODING);
        //ボディパートの追加
        mp.addBodyPart(mbpMain);

        //JavaMail1.3 + Activation.jarが必要 1.4だと別な実装？
        // 添付ファイルを作成
        TempFileModel tempFileMdl = null;
        for (int i = 0; i < fileList.size(); i++) {

            tempFileMdl = (TempFileModel) fileList.get(i);
            File file = tempFileMdl.getFile();

            MailUtil.addTempFileForSend(mp, file, tempFileMdl.getFileName(), ENCODING);

        }
        // マルチパートオブジェクトをメッセージに設定
        message.setContent(mp);

        send(message);
    }
}
