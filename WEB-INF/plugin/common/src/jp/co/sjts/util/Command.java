package jp.co.sjts.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;

/**
 * <br>[機 能] OSコマンドを実行するためのクラスです
 * <br>[解 説]
 * <br>[備 考]
 * <br>
 * <br>
 * コンストラクタの引数に"javac hoge.java"等とコマンドを入力すると実行できます。<br><br>
 *
 *<pre>
 *ここでは"ls -al"コマンドを実行します。
 *コマンドの実行結果を表示するためにはexec()の戻り値(InputStream)を使用します。
 *
 *Command comp = new Command("ls -al");
 *InputStream in = comp.exec();
 *
 *BufferedReader bf = new BufferedReader(new InputStreamReader(in));
 *try{
 *    String line;
 *    while((line = bf.readLine()) != null){
 *        System.out.println(line);
 *    }
 *}catch(Exception e){
 *    e.printStackTrace();
 *}
 *
 *出力結果は通常の"ls -al"とまったく同じです。
 *
 *合計 20
 *drwxrwxr-x    3 kitade   kitade       4096 Feb 27 15:29 .
 *drwxrwxr-x    8 kitade   kitade       4096 Feb 14 10:55 ..
 *-rwxrwxr-x    1 kitade   kitade         75 Feb 27 15:29 command.sh
 *
 *</pre>
 * @author JTS
 */
public class Command {

    /** コマンドを格納します。 */
    private String command__;

    /**
     * [機 能] コンストラクタ。コマンドをセットします。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param command コマンド
     */
    public Command(String command) {
        command__ = command;
    }

    /**
     * [機 能] コマンドを実行します。<br>
     * [解 説] <br>
     * [備 考] <br>
     *  @return 入力,エラーストリームを返します。InputStream
     *  @throws IOException IOエラー
     */
    public InputStream exec() throws IOException {
        InputStream inErr = null;

        Runtime runt = __getRuntime(); //Runtime取得
        try {
            Process pr = runt.exec(command__); //コマンド実行
            inErr = __getInErr(pr); //入力、エラーストリームを取得
        } catch (IOException e) {
            throw e;
        }
        return inErr;
    }

    /**
     * [機 能] コマンドを実行します。(戻り値String)<br>
     * [解 説] <br>
     * [備 考] <br>
     *  @return 入力,エラーストリームをStringにしたものを返します。
     *  @throws IOException IOエラー
     */
    public String execRetStr() throws IOException {
        StringBuilder retBuf = new StringBuilder();
        InputStream inErr = null;
        BufferedReader bf = null;
        try {
            inErr = exec();
            bf = new BufferedReader(
                    new InputStreamReader(inErr));
            String line;
            while ((line = bf.readLine()) != null) {
                retBuf.append(line + "\r\n");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (inErr != null) {
                    inErr.close();
                }
            } catch (IOException e) {
            }
        }
        return retBuf.toString();
    }

    /**
     * [機 能] 入力ストリーム、エラーストリームを取得する。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param  pr Process
     * @return InputStream
     */
    private InputStream __getInErr(Process pr) {
        InputStream in = pr.getInputStream();
        InputStream err = pr.getErrorStream(); // get process ERROR stream
        return new SequenceInputStream(in, err); // in, err を繋ぐ
    }

    /**
     * [機 能] Runtime取得<br>
     * [解 説] <br>
     * [備 考] <br>
     * @return ランタイム(実行が終了するとSystem.exet()するので注意する)
     */
    private Runtime __getRuntime() {
        return Runtime.getRuntime();
    }
}
