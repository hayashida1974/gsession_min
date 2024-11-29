package jp.groupsession.v2.sml.popserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.CrlfTerminatedReader;
import jp.co.sjts.util.io.CrlfTerminatedWriter;
import jp.co.sjts.util.io.CrlfTerminationException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.mail.MailUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn001.Cmn001Form;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.usr.GSPassword;

/**
 * <br>[機  能] ショートメールPOPサーバ管理クラス
 * <br>[解  説]
 * <br>1) USER,PASSコマンドによる認証
 * <br>2) 認証が確立した段階で、ショートメールをMIME形式にしたものをファイルに書き込む
 * <br>3) RETRコマンド受信時に作成したファイルから書き出す。
 * <br>[備  考]
 * <br>メーラーの設定で受信時削除にした場合、ショートメール機能としては削除しないが、
 * <br>既読に変更する。
 *
 * @author JTS
 */
public class PopService extends Thread {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(PopService.class);

    /** ネットワーク入力 */
    private BufferedReader in__;

    /** ネットワーク出力 */
    private PrintWriter out__;

    /** クライアントとの通信用ソケット */
    private Socket socket__;

    /** Pop3Serverを記録 */
    private PopServer server__;

    /** ドメイン */
    private String domain__;

    /** クライアント番号(管理用) */
    int clientnumber__;
    /** ストリームから読取時の文字コード */
    public static final String ENCODE = "ASCII";

    /** 正常処理のレスポンスにつけるヘッダ */
    public static final String OK_RESPONSE = "+OK";

    /** エラー時のレスポンスにつけるヘッダ */
    public static final String ERR_RESPONSE = "-ERR";

    /** 終了フラグ true:終了, false:起動 */
    private boolean endFlg__ = false;

    /** ログインユーザＩＤ */
    private String user__ = null;
    /** ユーザ情報MODEL */
    private BaseUserModel cinfo__ = null;
    /** アカウント情報 */
    private SmlAccountModel sacMdl__ = null;
    /** メール */
    ArrayList<MimeMailModel> mimeMailList__ = new ArrayList<MimeMailModel>();

    /**
     * <p>コンストラクタ
     * @param socket ソケット
     * @param server サーバオブジェクトの参照
     * @param clientnumber クライアント番号
     * @param domain ドメイン
     */
    public PopService(Socket socket, PopServer server,
            int clientnumber, String domain) {
        // 引数の値をこのクラスの変数にセット
        socket__ = socket;
        server__ = server;
        clientnumber__ = clientnumber;
        domain__ = domain;

        log__.debug("GS Short Mail Service START");
        try {
            //入力ストリームからバッファドリーダを作成
            in__ = new CrlfTerminatedReader(new BufferedInputStream(socket
                    .getInputStream(), 512), ENCODE);
            String remoteIP = socket.getInetAddress().getHostAddress();
            String remoteHost = socket.getInetAddress().getHostName();
            // 出力ストリームからデータ出力ストリーム作成
            out__ = new CrlfTerminatedWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), ENCODE), 1024),
                    false);
            log__.debug("remoteIP " + remoteIP);
            log__.debug("remoteHost " + remoteHost);
            log__.debug("clientnumber " + clientnumber__);
        } catch (Exception e) { // 例外処理
            log__.error("->" + e.getMessage(), e);
        }
    }

    /**
     * コマンドライン読取クラス
     * @return the trimmed input line
     * @throws IOException
     *             if an exception is generated reading in the input characters
     */
    private String readCommandLine() throws IOException {

        while (!endFlg__) {
            try {
                String commandLine = in__.readLine();
                return commandLine;
            } catch (CrlfTerminationException e) {
                log__.error("要求文が不適切", e);
            } catch (SocketException e) {
                //クライアント側でソケットを閉じた場合にスローされる。
                //クライアント側の強制終了、断線等の可能性がある。
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * <p>スレッド実行 クライアントとの情報交換をする
     */
    public void run() {
        try {
            if (GroupSession.getResourceManager().getDataSource(domain__) == null) {
                return;
            }
        } catch (Exception e) {
            return;
        }

        //スレッド名の設定
        Thread.currentThread().setName("SmlPopService"
                                        + "-" + System.currentTimeMillis()
                                        + "-" + Thread.currentThread().getId());

        log__.debug("RUN START");
        String commandline = null; // 送信メッセージ記録用

        // 接続時の応答
        UDate now = new UDate();
        sendOkMessage("Connect at " + UDateUtil.getSlashYMD(now) + " "
                + UDateUtil.getSeparateHMS(now));

        boolean crlfFlg = true;
        try {
            while (!endFlg__) {
                // クライアントからのメッセージ受信
                commandline = readCommandLine();
                if (commandline == null || commandline.length() == 0) {
                    if (crlfFlg) {
                        crlfFlg = false;
                        sendMessage("");
                        continue;
                    } else {
                        __doClose();
                    }
                } else {
                    crlfFlg = true;
                }
                log__.debug("commandline = " + commandline);
                String command = null;
                StringTokenizer cmdToken = new StringTokenizer(commandline, " ");
                int arguments = cmdToken.countTokens();
                if (arguments == 0) {
                    return;
                } else if (arguments > 0) {
                    command = cmdToken.nextToken().toUpperCase();
                }

                String argument1 = null;
                if (arguments > 1) {
                    argument1 = cmdToken.nextToken();
                }
                String argument2 = null;
                if (arguments > 2) {
                    argument2 = cmdToken.nextToken();
                }
                log__.debug("CLIENT: " + command + " " + argument1 + " " + argument2);
                __doProc(command, argument1, argument2);
            }
        } catch (Exception e) {
            __doClose();
        } finally {
            //スレッド名に"END-"を設定する
            Thread.currentThread().setName(
                    "END-" + Thread.currentThread().getName());
        }
        log__.debug("RUN END");
    }

    /**
     * <p>
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    private synchronized void __doProc(String command,
            String argument1, String argument2) {

        Connection con = null;
        boolean commitFlg = false;
        try {
            //DB Connectionの取得
            con = GroupSession.getConnection(domain__);
            con.setAutoCommit(false);
            //コマンド実行
            if (command.equals("USER")) {
                //ユーザ
                doUser(con, command, argument1, argument2);
            } else if (command.equals("PASS")) {
                //パスワード
                doPass(con, command, argument1, argument2);
            } else if (command.equals("STAT")) {
                doStat(con, command, argument1, argument2);
            } else if (command.equals("LIST")) {
                doList(con, command, argument1, argument2);
            } else if (command.equals("UIDL")) {
                doUidl(con, command, argument1, argument2);
            } else if (command.equals("RSET")) {
                doRset(con, command, argument1, argument2);
            } else if (command.equals("DELE")) {
                doDele(con, command, argument1, argument2);
            } else if (command.equals("NOOP")) {
                doNoop(con, command, argument1, argument2);
            } else if (command.equals("RETR")) {
                doRetr(con, command, argument1, argument2);
            } else if (command.equals("TOP")) {
                doTop(con, command, argument1, argument2);
            } else if (command.equals("CAPA")) {
                doCapa(con, command, argument1, argument2);
            } else if (command.equals("AUTH")) {
                doCapa(con, command, argument1, argument2);
            } else if (command.equals("XTND")) {
                doXtnd(con, command, argument1, argument2);
            } else if (command.equals("PIPELINING")) {
                doPipelining(con, command, argument1, argument2);
            } else if (command.equals("QUIT")) {
                //終了処理
                doQuit(con);
                commitFlg = true;
                return;
            } else {
                // 不正 or 未対応のコマンド
                try {
                    sendErrMessage("command '" + command + "' is unknown. Close connection.");
                } catch (RuntimeException e) {
                    log__.error("エラーメッセージ送信に失敗", e);
                    return;
                }
                __doClose();
            }
            commitFlg = true;
        } catch (Exception e) {
            sendErrMessage("Unknown Error. Connection Close");
            log__.error("Unknown Error.", e);
            __doClose();
        } finally {
            if (commitFlg == true) {
                try {
                    con.commit();
                } catch (SQLException e) {
                }
            } else {
                JDBCUtil.rollback(con);
            }
            JDBCUtil.closeConnection(con);
        }
    }
    /**
     * <p>メッセージ送信
     * @param message メッセージ
     */
    public synchronized void sendMessage(String message) {
        if (out__ == null) {
            return;
        }
        log__.debug("SV:    " + message);
        // スレッドが応対するクライアントに送信
        out__.println(message);
        // バッファ内のデータを強制的に送信
        out__.flush();
    }

    /**
     * <p>メッセージ送信(正常処理時)
     * @param message メッセージ
     */
    public synchronized void sendOkMessage(String message) {
        sendMessage(OK_RESPONSE + " " + message);
    }

    /**
     * <p>メッセージ送信(エラー時)
     * @param message メッセージ
     */
    public synchronized void sendErrMessage(String message) {
        sendMessage(ERR_RESPONSE + " " + message);
    }

    /**
     * <p>CAPAコマンド実行
     * <br>なにもしない。(-ERR unimplementedを返す)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doCapa(Connection con, String command, String argument1, String argument2) {
        sendErrMessage("unimplemented");
    }
    /**
     * <p>AUTHコマンド実行
     * <br>なにもしない。(-ERR unimplementedを返す)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doAuth(Connection con, String command, String argument1, String argument2) {
        sendErrMessage("unimplemented");
    }
    /**
     * <p>XTNDコマンド実行
     * <br>なにもしない。(-ERR unimplementedを返す)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doXtnd(Connection con, String command, String argument1, String argument2) {
        sendErrMessage("unimplemented");
    }
    /**
     * <p>PIPELININGコマンド実行
     * <br>なにもしない。(-ERR unimplementedを返す)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doPipelining(Connection con, String command, String argument1, String argument2) {
        sendErrMessage("unimplemented");
    }

    /**
     * <p>USERコマンド実行
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doUser(Connection con, String command, String argument1, String argument2) {
        //
        if (argument1 == null || argument1.length() <= 0) {
            user__ = null;
            sendErrMessage("USER NAME IS NULL");
            return;
        }
        user__ = argument1;
        sendOkMessage("USER NAME IS " + user__);
    }

    /**
     * <p>PASSコマンド実行
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception 入力エラー,認証エラー時にスロー
     */
    public void doPass(Connection con, String command, String argument1,
            String argument2) throws Exception {
        if (argument1 == null || argument1.length() <= 0) {
            user__ = null;
            sendErrMessage("PASS IS NULL. Connection reset");
            __doClose();
            return;
        }
        //入力チェックを行う
        ActionErrors errors = Cmn001Form.validateLogin2(user__, argument1, null, null);
        if (!errors.isEmpty()) {
            //入力エラー
            sendErrMessage("Input Error(PASS).");
            __doClose();
            return;
        }

        //ユーザ認証
        //ユーザ情報取得
        AuthDao adao = new AuthDao(con);
        BaseUserModel bmodel = null;
        try {
            String loginId = user__;
            String epassword = GSPassword.getEncryPassword(argument1);
            bmodel = adao.selectLogin(loginId, epassword);
        } catch (SQLException e) {
            log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
            throw e;
        }

        //該当ユーザなし
        if (bmodel == null) {
            //
            sendErrMessage("Attestation error.");
            //接続を切断する
            __doClose();
            return;
        }

        //
        cinfo__ = null;

        //GSユーザ基本情報の取得
        cinfo__ = new BaseUserModel();
        cinfo__.setUsrsid(bmodel.getUsrsid());
        cinfo__.setUsisei(bmodel.getUsisei());
        cinfo__.setUsimei(bmodel.getUsimei());


        //アカウント情報の取得
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl__ = sacDao.selectFromUsrSid(cinfo__.getUsrsid());
        //該当アカウントなし（通常ありえない）
        if (sacMdl__ == null) {
            sendErrMessage("Attestation error.");
            //接続を切断する
            __doClose();
            return;
        }

        //認証が成功した時点で、現在DBに格納されたリストを取得し、
        //テンポラリに保存する。(とりあえずメモリ上)

        SmailDao sDao = new SmailDao(con);
        //件数取得
        //long mlcount = sDao.getJmeisCount(cinfo__.getUsrsid());
        SmlCommonBiz cbiz = new SmlCommonBiz();
        long mlcount = cbiz.getUnopenedMsgCnt(sacMdl__.getSacSid(), con);
        //データ取得
        ArrayList<SmailModel> smailList =
            sDao.selectJmeisList(
                sacMdl__.getSacSid(),
                0,
                (int) mlcount,
                GSConstSmail.MSG_SORT_KEY_DATE,
                GSConstSmail.ORDER_KEY_ASC,
                GSConstSmail.OPKBN_UNOPENED);

        //データリセット
        mimeMailList__ = new ArrayList<MimeMailModel>();
        //メール用文字列生成
        for (SmailModel mmodel : smailList) {
            //メール詳細データの取得
            SmailDetailModel dmodel = sDao.selectJmeisDetail2(
                    sacMdl__.getSacSid(), mmodel.getSmlSid());
            if (dmodel == null) {
                continue;
            }
            //メッセージID生成
            String msgid = createMessageId(dmodel);
            //メール用文字列生成
            String mstr = toMailString(msgid, dmodel, con);
            //バイト数計算
            int size = mstr.getBytes().length;
            MimeMailModel mmm = new MimeMailModel();
            mmm.setMsgid(msgid);
            mmm.setMstr(mstr);
            mmm.setSize(size);
            mmm.setSmlSid(dmodel.getSmlSid());
            mimeMailList__.add(mmm);
        }

        //OKメッセージ
        sendOkMessage(user__ + " sign on");
    }

    /**
     * メッセージIDを作成する。
     * メッセージID作成ルール: 送信日(yyyymmddhhMMss) + "." + ユーザSID + "." + ショートメールSID + "." + サーバ名
     * @param dmodel ショートメール詳細モデル
     * @return 作成したメッセージID
     */
    public String createMessageId(SmailDetailModel dmodel) {
        StringBuilder ret = new StringBuilder();
        //日付
        UDate sdate = dmodel.getSmsSdate();
        ret.append(sdate.toString());
        //.
        ret.append(".");
        //ユーザSID
        ret.append(dmodel.getUsrSid());
        //.
        ret.append(".");
        //ショートメールSID
        ret.append(dmodel.getSmlSid());
        //.
        ret.append(".");
        //サーバ名
        String serverName = "localhost";
        try {
            serverName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        ret.append(serverName);
        return ret.toString();
    }
    /**
     * <p>ショートメールモデルから、メール用Stringを作成する。
     * @param msgid メッセージID
     * @param dmodel ショートメールモデル
     * @return メール用String
     * @param con DBコネクション
     * @throws Exception 例外
     */
    public String toMailString(String msgid, SmailDetailModel dmodel,
            Connection con) throws Exception {
        //
        if (dmodel == null) {
            return null;
        }

        //メールString生成開始
        StringBuilder ret = new StringBuilder();
        //メールオブジェクトの作成
        String encoding = Encoding.ISO_2022_JP;
        Properties prop = new Properties();
        Session session = Session.getInstance(prop, null);
        MimeMessage message = new MimeMessage(session);

        //FROM
        String from = MimeUtility.encodeText(GSConst.PROJECT_NAME, encoding, "B");
        message.setHeader("From", from);
        String tojp = NullDefault.getString(dmodel.getUsiSei(), "")
        + " " + NullDefault.getString(dmodel.getUsiMei(), "");

        //TO
        String to = MimeUtility.encodeText(tojp, encoding, "B");
        message.setHeader("To", to);

        //日付
        UDate sdate = dmodel.getSmsSdate();
        message.setSentDate(sdate.toJavaUtilDate());

        //X-Mailer
        message.setHeader("X-Mailer", GSConst.PROJECT_NAME + " " + GSConst.VERSION
                + " Short Mail Server");

        //Subject
        String subject = NullDefault.getString(dmodel.getSmsTitle(), "");
        message.setSubject(subject, encoding);

        //BODY
        String body = null;
        if (dmodel.getSmsType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            //TEXT形式
            body = NullDefault.getString(dmodel.getSmsBody(), "") + "\r\n";
        } else {
            //HTML形式
            String htmlBody = NullDefault.getString(dmodel.getSmsBody(), "");
            body = StringUtilHtml.transToTextAndDeleteTag(htmlBody);
        }

        //BODY中の「.CRFL」 を 「..CRFL」に置き換える
        body = StringUtil.substitute(body, ".\r\n", "..\r\n");
        //body = MimeUtility.encodeText(body, encoding, "B");

        //添付ファイル設定
        try {
            SmlBinDao bindao = new SmlBinDao(con);
            ArrayList<CmnBinfModel> fileDataList
                = bindao.getFileList(dmodel.getSmlSid());
            if (fileDataList == null || fileDataList.isEmpty()) {
                //添付ファイルなし
                message.setText(body, encoding);
            } else {

                // 複数のボディを格納するマルチパートオブジェクトを生成
                Multipart mp = new MimeMultipart();
                // ボディパートを作成
                MimeBodyPart mbpMain = new MimeBodyPart();
                // メールの内容を指定
                mbpMain.setText(body, encoding);
                //ボディパートの追加
                mp.addBodyPart(mbpMain);

                // 添付ファイルを設定
                for (CmnBinfModel fileData : fileDataList) {
                    CommonBiz cmnBiz = new CommonBiz();
                    CmnBinfModel binMdl =
                            cmnBiz.getBinInfo(con, fileData.getBinSid(),
                                    GSConst.GS_DOMAIN);
                    __addTempFile(con, dmodel.getAccountSid(), mp, binMdl, encoding);
                }
                // マルチパートオブジェクトをメッセージに設定
                message.setContent(mp);
            }

            //その他ヘッダ情報の自動付加
            message.saveChanges();
            //メッセージIDは自動付加されたくないので、設定
            message.setHeader("Message-ID", msgid);
            //Content-Transfer-Encoding 7bit
            message.setHeader("Content-Transfer-Encoding", "7bit");

            InputStreamReader reader = null;
            reader = new InputStreamReader(message.getInputStream(), ENCODE);
            char[] charBuffer = new char[1024]; // 一時読み込み
            int byteRead = 0;

            //ヘッダ文字列をセット
            @SuppressWarnings("all")
            Enumeration em = message.getAllHeaderLines();
            while (em.hasMoreElements()) {
                ret.append(em.nextElement().toString());
                ret.append("\r\n");
            }
            ret.append("\r\n");
            ret.append("\r\n");

            //Mimeフォーマットの文字列作成
            while ((byteRead = reader.read(charBuffer, 0, 1024)) != -1) {
                // 読込データ格納
                ret.append(charBuffer, 0, byteRead);
            }
            reader.close();

            ret.append("\r\n");
            ret.append("\r\n");
            ret.append(".");
        } finally {
            //テンポラリディレクトリの削除
            __deletePopTempDir();
        }

        log__.debug("toString() " + message.toString());
        log__.debug("Message = \r\n " + ret);
        return ret.toString();
    }

    /**
     * <p>STATコマンド実行
     * <br>メールボックスにある全メールの件数とバイト数を問い合せる(DELされているものは除く)。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doStat(Connection con, String command, String argument1, String argument2) {
        //件数
        int count = mimeMailList__.size();
        //データサイズ(全メールの合計)
        long allsize = __getAllDataSize();
        sendOkMessage(count + " " + allsize);
    }

    /**
     * <p>全メールのサイズ合計を算出します。
     * @return メールサイズの合計
     */
    private long __getAllDataSize() {
        long ret = 0;
        for (MimeMailModel mmm : mimeMailList__) {
            //
            ret += mmm.getSize();
        }
        return ret;
    }

    /**
     * <p>LISTコマンド実行
     * 受信メールのリスト(ID,サイズ)を得る。数字を与えると、その番号のメールのサイズだけ返します。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception SQLの実行に失敗した場合にスロー
     */
    public void doList(Connection con, String command, String argument1,
            String argument2) throws Exception {

        if (argument1 != null && argument1.length() != 0) {
            //引数あり
            __doList2(con, command, argument1, argument2);
        } else {
            //引数なし
            __doList1(con, command, argument1, argument2);
        }
    }

    /**
     * <p>LISTコマンド実行(引数なし)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception SQLの実行に失敗した場合にスロー
     */
    private void __doList1(Connection con, String command, String argument1,
            String argument2) throws Exception {

        sendOkMessage("");
        int i = 0;
        for (MimeMailModel mmm : mimeMailList__) {
            i++;
            //INDEX + SIZE
            sendMessage(i + " " + mmm.getSize());
        }
        sendMessage(".");
    }

    /**
     * <p>LISTコマンド実行(引数あり)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception SQLの実行に失敗した場合にスロー
     */
    private void __doList2(Connection con, String command, String argument1,
            String argument2) throws Exception {

        int listSize = mimeMailList__.size();
        int index = NullDefault.getInt(argument1, -1);
        if (index <= 0 || index > listSize) {
            sendErrMessage(argument1 + " message is not found");
            return;
        }

        //データサイズ取得
        MimeMailModel mmm = mimeMailList__.get(index - 1);
        long size = mmm.getSize();
        //INDEX + SIZE
        sendOkMessage(index + " " + size);
    }

    /**
     * <p>UIDLコマンド実行
     * <br>UIDLを得る。UIDLはメールに対して固有のIDです。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception 予測できないエラー
     */
    public void doUidl(Connection con, String command, String argument1,
            String argument2) throws Exception {
        if (argument1 != null && argument1.length() != 0) {
            //引数あり
            __doUidl2(con, command, argument1, argument2);
        } else {
            //引数なし
            __doUidl1(con, command, argument1, argument2);
        }
    }
    /**
     * <p>UIDLコマンド実行(引数なし)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception SQLの実行に失敗した場合にスロー
     */
    private void __doUidl1(Connection con, String command, String argument1,
            String argument2) throws Exception {

        sendOkMessage("");
        int i = 0;
        for (MimeMailModel mmm : mimeMailList__) {
            i++;
            //INDEX + SIZE
            sendMessage(i + " " + mmm.getMsgid());
        }
        sendMessage(".");
    }

    /**
     * <p>UIDLコマンド実行(引数あり)
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception SQLの実行に失敗した場合にスロー
     */
    private void __doUidl2(Connection con, String command, String argument1,
            String argument2) throws Exception {

        int listSize = mimeMailList__.size();
        int index = NullDefault.getInt(argument1, -1);
        if (index <= 0 || index > listSize) {
            sendErrMessage(argument1 + " message is not found");
            return;
        }

        //データサイズ取得
        MimeMailModel mmm = mimeMailList__.get(index - 1);
        String msgid = mmm.getMsgid();
        //INDEX + MessageID
        sendOkMessage(index + " " + msgid);
    }

    /**
     * <p>RSETコマンド実行
     * <br>DELEで削除されたメールの取り消し。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doRset(Connection con, String command, String argument1, String argument2) {
        for (MimeMailModel mmm : mimeMailList__) {
            mmm.setDelFlg(false);
        }
        sendOkMessage("");
    }

    /**
     * <p>DELEコマンド実行
     * <br>指定したメール番号のメールにDELEマークを付けます。実際削除されるのはQUITした時で、RSETによって取り消すことが出来ます。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doDele(Connection con, String command, String argument1, String argument2) {
        int listSize = mimeMailList__.size();
        int index = NullDefault.getInt(argument1, -1);
        if (index <= 0 || index > listSize) {
            sendErrMessage(argument1 + " message is not found");
            return;
        }

        //削除フラグを立てる
        MimeMailModel mmm = mimeMailList__.get(index - 1);
        mmm.setDelFlg(true);
        sendOkMessage("");
    }

    /**
     * <p>Noopコマンド実行
     * <br>なにもしない。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     */
    public void doNoop(Connection con, String command, String argument1, String argument2) {
        sendOkMessage("");
    }

    /**
     * <p>Retrコマンド実行
     * <br>指定したメール番号のメールの内容を得ます。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 引数1
     * @param argument2 引数2
     * @throws Exception 例外
     */
    public void doRetr(Connection con, String command, String argument1,
            String argument2) throws Exception {
        int listSize = mimeMailList__.size();
        int index = NullDefault.getInt(argument1, -1);
        if (index <= 0 || index > listSize) {
            sendErrMessage(argument1 + " message is not found");
            return;
        }

        sendOkMessage("");
        MimeMailModel mmm = mimeMailList__.get(index - 1);
        sendMessage(mmm.getMstr());
    }

    /**
     * <p>TOPコマンド実行
     * <br>指定したメール番号のメールを指定した(ヘッダーを除く本文の)行数を得ます。
     * @param con DBコネクション
     * @param command コマンド
     * @param argument1 対象のメールインデックス
     * @param argument2 行数
     * @throws Exception 例外
     */
    public void doTop(Connection con, String command, String argument1,
            String argument2) throws Exception {
        //メールインデックスチェック
        int listSize = mimeMailList__.size();
        int index = NullDefault.getInt(argument1, -1);
        if (index <= 0 || index > listSize) {
            sendErrMessage(argument1 + " message is not found");
            return;
        }

        //行数
        int row = NullDefault.getInt(argument2, -1);
        if (row < 0) {
            sendErrMessage(argument2 + " RowNo is validate error");
            return;
        }

        //OK
        sendOkMessage("");

        MimeMailModel mmm = mimeMailList__.get(index - 1);
        String body = mmm.getMstr();
        String[] abody = body.split("\r\n");

        boolean bodyFlg = false;
        int bodyindex = 0;
        for (int i = 0; i < abody.length; i++) {
            //ヘッダ文字列終了判定
            if (bodyFlg == false && abody[i].length() == 0) {
                bodyFlg = true;
            }
            //BODY内のインデックスをカウントする
            if (bodyFlg) {
                bodyindex++;
            }
            //指定行まで出力する
            if (row < bodyindex) {
                sendMessage("."); //終了文字列を付加
                break;
            }
            //
            sendMessage(abody[i]);
        }
    }

    /**
     * <p>クライアントの要求による切断処理
     * <br>pop接続の終了。
     * <br>削除フラグの立っているメッセージは既読にする。
     * @param con DBコネクション
     * @throws Exception 例外
     */
    public void doQuit(Connection con) throws Exception {
        String responseString = " signing off.";

        //削除フラグの立っているメッセージを既読にする
        if (mimeMailList__ != null) {
            Sml030Biz sbiz = new Sml030Biz();
            for (MimeMailModel mmm : mimeMailList__) {
                if (mmm.isDelFlg()) {
                    //削除フラグ TRUE
                    sbiz.updateMidokuMsg(con, sacMdl__.getSacSid(),
                            cinfo__.getUsrsid(), mmm.getSmlSid());
                }
            }
        }

        sendOkMessage("");
        sendOkMessage(responseString);
        log__.debug("signing off完了");
        __doClose();
    }

    /**
     * <p>切断処理
     * <p>強制切断で使用する場合はエラーの応答は各呼び出し元で行うこと
     */
    private void __doClose() {
        try {
            //切断処理
            log__.debug("ソケット関係切断開始");
            if (socket__ != null && !socket__.isClosed()) {
                socket__.shutdownOutput();
                socket__.shutdownInput();
                socket__.close();
            }
            socket__ = null;
            server__.quit(clientnumber__);
        } catch (IOException e) {
            log__.error("ソケット関係の切断処理に失敗", e);
        }
    }

    /**
     * @return endFlg を戻します。
     */
    public boolean isEndFlg() {
        return endFlg__;
    }

    /**
     * @param endFlg 設定する endFlg。
     */
    public void setEndFlg(boolean endFlg) {
        endFlg__ = endFlg;
    }

    /**
     * <br>[機  能] メール添付ファイルの追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid ショートメールアカウントSID
     * @param mp Multipart
     * @param fileData 添付ファイル情報
     * @param encode ファイル名エンコード
     * @throws MessagingException 添付ファイルの追加に失敗
     * @throws UnsupportedEncodingException 添付ファイル名変換時の文字コードが不正
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __addTempFile(Connection con, int sacSid,
                                Multipart mp, CmnBinfModel fileData,
                                String encode)
    throws MessagingException, UnsupportedEncodingException,
            IOException, IOToolsException, TempFileException {

        String appRootPath
            = (String) GroupSession.getContext().get(GSContext.APP_ROOT_PATH);
        String tmpFilePath = __readTempFile(con, sacSid,
                                            fileData,
                                            appRootPath);
        MailUtil.addTempFileForSend(mp, new File(tmpFilePath),
                fileData.getBinFileName(), encode);
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSid ショートメールアカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param fileData 添付ファイル情報
     * @return 添付ファイル保存先ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private String __readTempFile(Connection con, int sacSid,
                                CmnBinfModel fileData, String appRootPath)
    throws IOException, IOToolsException, TempFileException {

        //テンポラリディレクトリパス
        String tempRootDir = __getPopTempDir(sacSid);
        UDate date = new UDate();
        String dateStr = date.getDateString();

        String tempPath = tempRootDir + fileData.getBinSid() + "/";
        IOTools.isDirCheck(tempPath, true);
        //ファイルの連番を取得する
        int fileNum = Cmn110Biz.getFileNumber(tempPath, dateStr);
        fileNum++;

        StringBuilder filePath = new StringBuilder(tempPath);
        filePath.append(dateStr);
        filePath.append(StringUtil.toDecFormat(fileNum, "000"));
        filePath.append(GSConstCommon.ENDSTR_SAVEFILE);
        String saveFilePath = filePath.toString();
        //添付ファイルをテンポラリディレクトリにコピーする。
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.saveTempFile(
                dateStr, fileData, appRootPath, tempPath, fileNum);
        return saveFilePath;
    }


    /**
     * [機  能] テンポラリディレクトリパスを返す
     * [解  説]
     * [備  考]
     * @param sacSid ショートメールアカウントSID
     * @return パス
     */
    private String __getPopTempDir(int sacSid) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        return tempPathUtil.getBatchTempPath(domain__,
                                            GSConstSmail.PLUGIN_ID_SMAIL,
                                            "popservice",
                                            String.valueOf(sacSid));
    }

    /**
     * [機  能] テンポラリディレクトリのパスを削除する
     * [解  説]
     * [備  考]
     */
    private void __deletePopTempDir() {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteBatchTempPath(domain__,
                                        GSConstSmail.PLUGIN_ID_SMAIL,
                                        "popservice");
    }

}