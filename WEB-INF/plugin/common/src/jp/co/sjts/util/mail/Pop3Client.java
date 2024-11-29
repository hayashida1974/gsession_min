package jp.co.sjts.util.mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * <br>[機  能] POPクライアントライブラリ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Pop3Client {
    /** Pop3サーバ */
    private String host__ = "xxx.xxx.xxx.xxx"; // ホストアドレス
    /** Pop3ポート */
    private int port__ = 110;
    /** アカウント */
    private String user__ = null; // アカウント
    /** パスワード */
    private String password__ = null; // パスワード

    /** Storeオブジェクト */
    private Store store__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Pop3Client() {
    }

    /**
     * <br>[機  能] アクセスするための基本情報をセットするコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param host メールサーバホスト名 or IPアドレス
     * @param user ユーザID
     * @param password パスワード
     */
    public Pop3Client(String host, String user, String password) {
        host__ = host;
        user__ = user;
        password__ = password;
    }

    /**
     * <br>[機  能] メールサーバに接続を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @throws AuthenticationFailedException 認証に失敗
     * @throws MessagingException 接続に失敗時にスロー
     */
    public void connect() throws AuthenticationFailedException, MessagingException {
        // 接続します
        Session session = Session.getDefaultInstance(System.getProperties(), null);
        try {
            store__ = session.getStore("pop3");
        } catch (NoSuchProviderException e) {
            //ありえないエラー
        }
        store__.connect(host__, port__, user__, password__);
    }
    /**
     * <br>[機  能] メールサーバとの接続を閉じる
     * <br>[解  説]
     * <br>[備  考]
     * @throws MessagingException 接続に失敗時にスロー
     */
    public void disConnect() throws MessagingException {
        if (store__ == null) {
            return;
        }
        store__.close();
    }

    /**
     * <br>[機  能] Folderオブジェクトを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param mode フォルダをオープンするモード
     * @throws MessagingException フォルダのオープンに失敗時にスロー
     * @return Folder
     */
    private Folder __getFolder(int mode) throws MessagingException {
        // フォルダーを開きます
        Folder folder = store__.getFolder("INBOX");
        folder.open(mode);
        return folder;
    }

    /**
     * <br>[機  能] INBOXのメッセージ数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return メッセージ数
     * @throws MessagingException INBOXフォルダの取得に失敗、メッセージカウントの取得に失敗時にスロー
     */
    public int getMessageCount() throws MessagingException {
        int ret = -1;

        Folder folder = null;
        try {
            // フォルダーを開きます
            folder = __getFolder(Folder.READ_ONLY);

            //カウントを取得
            ret = folder.getMessageCount();
        } finally {
            folder.close(false); //CLOSE時にメッセージを削除しない
        }

        return ret;
    }

    /**
     * <br>[機  能] INBOXのメッセージを全て取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return INBOX内の全てのメッセージ。 0件の場合はnullを返す。
     * @throws MessagingException INBOXフォルダの取得に失敗、メッセージカウントの取得に失敗時にスロー
     */
    public Message[]getMessages() throws MessagingException {
        Message[] messages = null;

        Folder folder = null;
        // フォルダーを開きます
        folder = __getFolder(Folder.READ_ONLY);

        //カウントを取得
        int count = folder.getMessageCount();
        //0件の場合
        if (count <= 0) {
            return null;
        }
        //メッセージを取得
        messages = folder.getMessages();
        return messages;
    }

    /**
     * <br>[機  能] INBOXの指定したメッセージ番号のメッセージを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param msgnums メッセージ番号(複数)
     * @return INBOX内の全てのメッセージ。 0件の場合はnullを返す。
     * @throws MessagingException INBOXフォルダの取得に失敗、メッセージカウントの取得に失敗時にスロー
     */
    public Message[]getMessages(int[] msgnums) throws MessagingException {
        Message[] messages = null;

        Folder folder = null;
        // フォルダーを開きます
        folder = __getFolder(Folder.READ_ONLY);

        //カウントを取得
        int count = folder.getMessageCount();
        //0件の場合
        if (count <= 0) {
            return null;
        }
        //メッセージを取得
        messages = folder.getMessages(msgnums);
        return messages;
    }

    /**
     * <br>[機  能] 指定したメッセージ番号のINBOXのメッセージを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param msgnum メッセージ番号
     * @return Message
     * @throws MessagingException INBOXフォルダの取得に失敗、メッセージカウントの取得に失敗時にスロー
     */
    public Message getMessage(int msgnum) throws MessagingException {
        Message message = null;

        Folder folder = null;
        // フォルダーを開きます
        folder = __getFolder(Folder.READ_ONLY);

        //メッセージを取得
        message = folder.getMessage(msgnum);
        return message;
    }

    /**
     * <p>host を取得します。
     * @return host
     */
    public String getHost() {
        return host__;
    }
    /**
     * <p>host をセットします。
     * @param host host
     */
    public void setHost(String host) {
        host__ = host;
    }
    /**
     * <p>password を取得します。
     * @return password
     */
    public String getPassword() {
        return password__;
    }
    /**
     * <p>password をセットします。
     * @param password password
     */
    public void setPassword(String password) {
        password__ = password;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public String getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(String user) {
        user__ = user;
    }
    /**
     * <p>port を取得します。
     * @return port
     */
    public int getPort() {
        return port__;
    }
    /**
     * <p>port をセットします。
     * @param port port
     */
    public void setPort(int port) {
        port__ = port;
    }
}
