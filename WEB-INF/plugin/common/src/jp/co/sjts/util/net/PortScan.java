package jp.co.sjts.util.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * <br>[機  能] 簡易ポートスキャンクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PortScan {

    /**
     * <br>[機  能] 1～1024番の開いているポート番号を返す
     * <br>[解  説]
     * <br>[備  考] 動作が遅いので、使用はお勧めできません。
     * @param host ホスト名
     * @return ポート番号の配列
     */
    public static ArrayList<Integer> scanAllPort(String host) {
        ArrayList<Integer> ports = new ArrayList<Integer>();

        for (int port = 1; port < 1024; port++) {
            if (isUsedPort(host, port)) {
                ports.add(new Integer(port));
            }
        }
        return ports;
    }

    /**
     * <br>[機  能] ホスト名とポート番号を指定して、ポートが使用されていないか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param host ホスト名
     * @param port ポート番号
     * @return true:使用中, false:未使用
     */
    public static boolean isUsedPort(String host, int port) {
        Socket sock = null;
        String hostName = host;
        boolean usedFlg = false;

        try {
            //ソケットが作成できたら(サーバと接続できたら)使用中
            sock = new Socket(hostName, port);
            usedFlg = true;
        } catch (Exception e) {
            //接続エラーの場合はポートが使用されていない
            usedFlg = false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
        return usedFlg;
    }
}
