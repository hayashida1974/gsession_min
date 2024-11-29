package jp.groupsession.v2.sml.popserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.server.IServer;

/**
 * <br>[機  能] ショートメールのメッセージをメーラーで受信するための機能を提供します。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PopServer extends Thread implements IServer {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(PopServer.class);

    /** 終了フラグ true:終了, false:起動 */
    private boolean endFlg__ = false;

    /** クライアントサービスオブジェクト配列 */
    private PopService[] threadPool = new PopService[MAX_CONNECTION];
    /** POPサーバの唯一のインスタンス */
    private static PopServer server__ = null;

    /** 最大接続数 */
    public static final int MAX_CONNECTION = 100;
    /** ACCEPTの待ち時間(ミリ秒)  */
    public static final int TIME_OUT_ACCEPT = 1000;

    /** サーバソケット */
    private ServerSocket serversocket__ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public PopServer() {
    }

    /**
     * <br>
     * [機 能] インスタンスを取得 <br>
     * [解 説] <br>
     * [備 考]
     *
     * @return インスタンス
     */
    public static synchronized PopServer getInstance() {
        if (server__ == null) {
            server__ = new PopServer();
        }
        return server__;
    }

    /**
     * <p>サーバ起動
     */
    public void run() {
        try {

            //スレッド名の設定
            Thread.currentThread().setName("SmlPopServer"
                                            + "-" + System.currentTimeMillis()
                                            + "-" + Thread.currentThread().getId());

            String mailPortNumber = ConfigBundle.getValue("MAIL_PORT_NUMBER");
            log__.info("************* mailPortNumber = " + mailPortNumber);

            // サーバソケット作成
            serversocket__ = new ServerSocket(Integer.parseInt(mailPortNumber));
            serversocket__.setSoTimeout(TIME_OUT_ACCEPT);

            log__.info("ServerSocket = " + serversocket__);

            // クライアントからの接続要求の処理を行う
            while (true) {
                int p;
                // 接続チェック
                for (p = 0; p < MAX_CONNECTION; p++) {
                    // 空いている場合
                    if (threadPool[p] == null) {
                        break;
                    }
                }

                // 空いていない場合
                if (p == MAX_CONNECTION) {
                    // 以下の処理をしない
                    continue;
                }

                Socket socket = null;
                // クライアントからの接続許可
                if (serversocket__.isClosed()) {
                    return;
                } else {
                    try {
                        socket = serversocket__.accept();
                    } catch (SocketTimeoutException e) {
                        if (endFlg__) {
                            return;
                        }
                        //これはOK(TIME_OUT_ACCEPT秒毎にループする)
                        continue;
                    } catch (IOException e) {
                        //SOCKET CLOSE時にスローされるがこれは仕様のためログ等には出力しない
                        return;
                    }
                }
                log__.info("Thread ID " + p);
                if (endFlg__) {
                    return;
                }

                // クライアントに応対するスレッドを作成し、処理を任せる
                log__.debug("PopService インスタンス作成");

                threadPool[p] = new PopService(socket, this, p, null);

                // スレッドスタート
                threadPool[p].start();
            }
        } catch (Exception e) {
            log__.error("ソケット作成失敗 : " + e, e);
        } finally {
            //スレッド名に"END-"を設定する
            Thread.currentThread().setName(
                    "END-" + Thread.currentThread().getName());
        }

    }

    /**
     * <p>切断後処理(クライアントでQUIT処理の時に呼び出すこと)
     * @param clientnumber クライアント識別番号
     */
    public void quit(int clientnumber) {
        if (threadPool[clientnumber] != null) {
            //終了
            threadPool[clientnumber].setEndFlg(true);
            //空きにする
            threadPool[clientnumber] = null;
        }
    }

    /**
     * <p>終了処理
     */
    public void shutdown() {

        String[] domains = null;
        try {
            domains = GroupSession.getResourceManager().getDomain();
        } catch (Exception e) {
            log__.error("ドメインの取得に失敗", e);
        }

        for (String domain : domains) {
            //
            endFlg__ = true;

            boolean commitFlg = false;
            Connection con = null;
            try {
                //DB Connectionの取得
                if (server__ == null) {
                    __closeServerSocket();
                    return;
                }
                con = GroupSession.getConnection(domain);
                con.setAutoCommit(false);

                //各クライアントを終了させる
                log__.info("各クライアントのコネクション切断 開始");
                for (int i = 0; i < MAX_CONNECTION; i++) {
                    if (threadPool[i] != null) {
                        threadPool[i].setEndFlg(true);
                        threadPool[i].doQuit(con);
                        threadPool[i] = null;
                    }
                }

                commitFlg = true;
            } catch (SQLException e) {
                log__.error("クライアントの終了処理に失敗", e);
            } catch (Exception e) {
                log__.error("クライアントの終了処理に失敗", e);
            } finally {
                if (commitFlg) {
                    try {
                        con.commit();
                    } catch (SQLException e) {
                    }
                } else {
                    JDBCUtil.rollback(con);
                }
                JDBCUtil.closeConnection(con);
            }

            log__.info("各クライアントのコネクション切断 完了");


            __closeServerSocket();

            log__.info("サーバソケット破棄");
        }
    }

    /**
     * <br>[機  能] サーバソケットの終了処理を行う
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __closeServerSocket() {
        if (serversocket__ != null
                && serversocket__.isClosed() == false) {
            log__.info("サーバソケット終了処理 開始");
            try {
                serversocket__.close();
                serversocket__ = null;
            } catch (IOException e) {
                log__.error("Popサーバの終了に失敗", e);
            } catch (Throwable e) {
                log__.error("Popサーバの終了に失敗", e);
            }
            log__.info("サーバソケット終了処理 終了");
        }

    }

    /**
     * <br>[機  能]サーバ終了処理が完了したかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:完了 false:終了処理実行中
     */
    public boolean isShutdownEnd() {
        return endFlg__ && serversocket__ == null;
    }

    /**
     * @return server を戻します。
     */
    public static PopServer getServer() {
        return server__;
    }

    /**
     * @param server 設定する server。
     */
    public static void setServer(PopServer server) {
        server__ = server;
    }



//
//    /**
//     * <p>テスト用メイン
//     * @param arts
//     */
//    public static void main(String arts[]) {
//        PopServer server = PopServer.getInstance();
//        server.run();
//    }
}