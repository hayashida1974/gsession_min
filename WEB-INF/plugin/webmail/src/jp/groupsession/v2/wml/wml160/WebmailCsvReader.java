package jp.groupsession.v2.wml.wml160;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;

/**
 * <br>[機  能] WEBメール アカウントインポート 取込みファイルのチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class WebmailCsvReader extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WebmailCsvReader.class);

    /** アカウント情報一覧 */
    private List<WebmailCsvModel> webmailList__ = null;

    /**
     * <p>webmailList を取得します。
     * @return webmailList
     */
    public List<WebmailCsvModel> getWebmailList() {
        return webmailList__;
    }

    /**
     * <p>webmailList をセットします。
     * @param webmailList webmailList
     */
    public void setWebmailList(List<WebmailCsvModel> webmailList) {
        webmailList__ = webmailList;
    }

    /**
     * コンストラクタ
     */
    public WebmailCsvReader() {
        setWebmailList(new ArrayList<WebmailCsvModel>());
    }

    /**
     * <br>[機　能] 指定したCSVファイルからアカウント情報を読み込む
     * <br>[解　説]
     * <br>[備  考]
     * @param csvFile 入力ファイル名
     * @throws Exception 実行時例外
     */
     public void readCsvFile(String csvFile)
     throws Exception {

         //ファイル読込み
         readFile(new File(csvFile), Encoding.WINDOWS_31J);
     }

   /**
    * <br>[機  能] csvファイル一行の処理
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param num 行番号
    * @param lineStr 行データ
    * @throws Exception csv読込時例外
    * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
    */
    protected void processedLine(long num, String lineStr) throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        try {

            CsvTokenizer csvTokenizer = new CsvTokenizer(lineStr, ",");

            WebmailCsvModel model = new WebmailCsvModel();
            model.setRowNum(num);
            model.setElementCount(csvTokenizer.length());

            for (int index = 0; csvTokenizer.hasMoreElements(); index++) {
                String value = csvTokenizer.nextToken();

                switch (index) {
                    case 0 :
                        //アカウント名
                        model.setAccountId(value);
                        break;
                    case 1 :
                        //アカウント名
                        model.setAccountName(value);
                        break;
                    case 2 :
                        //認証形式
                        model.setAuthType(value);
                        break;
                    case 3 :
                        //プロバイダ
                        model.setProvider(value);
                        break;
                    case 4 :
                        //メールアドレス
                        model.setMail(value);
                        break;
                    case 5 :
                        //メール受信サーバ名
                        model.setMailRsvServer(value);
                        break;
                    case 6 :
                        //受信サーバ ポート番号
                        model.setMailRsvPort(value);
                        break;
                    case 7 :
                        //受信サーバ SSL
                        model.setMailRsvSsl(value);
                        break;
                    case 8 :
                        //受信サーバ ユーザ名
                        model.setMailRsvUser(value);
                        break;
                    case 9 :
                        //受信サーバ パスワード
                        model.setMailRsvPass(value);
                        break;
                    case 10 :
                        //メール自動受信
                        model.setMailAutoRsv(value);
                        break;
                    case 11 :
                        //メール自動受信間隔
                        model.setMailAutoRsvTime(value);
                        break;
                    case 12 :
                        //メール送信サーバ
                        model.setMailSndServer(value);
                        break;
                    case 13 :
                        //メール送信サーバ ポート番号
                        model.setMailSndPort(value);
                        break;
                    case 14 :
                        //メール送信サーバ SSL
                        model.setMailSndSsl(value);
                        break;
                    case 15 :
                        //SMTP認証ON/OFF
                        model.setSmtpNinsyo(value);
                        break;
                    case 16 :
                        //メール送信サーバ ユーザ名
                        model.setMailSndUser(value);
                        break;
                    case 17 :
                        //メール送信サーバ パスワード
                        model.setMailSndPass(value);
                        break;
                    case 18 :
                        //ディスク容量
                        model.setDiskCapa(value);
                        break;
                    case 19 :
                        //ディスク容量 サイズ
                        model.setDiskCapaSize(value);
                        break;
                    case 20 :
                        //ディスク容量 特例修正
                        model.setDiskCapaSps(value);
                        break;
                    case 21 :
                        //備考
                        model.setBiko(value);
                        break;
                    case 22 :
                        //組織名
                        model.setOrganization(value);
                        break;
                    case 23 :
                        //署名
                        model.setSign(value);
                        break;
                    case 24 :
                        //署名自動挿入
                        model.setSignAuto(value);
                        break;
                    case 25 :
                        //返信時署名位置
                        model.setSignPoint(value);
                        break;
                    case 26 :
                        //返信時署名表示
                        model.setSignDsp(value);
                        break;
                    case 27 :
                        //自動TO
                        model.setAutoTo(value);
                        break;
                    case 28 :
                        //自動CC
                        model.setAutoCc(value);
                        break;
                    case 29 :
                        //自動BCC
                        model.setAutoBcc(value);
                        break;
                    case 30 :
                        //受信時削除
                        model.setRsvDelete(value);
                        break;
                    case 31 :
                        //受信済みでも受信
                        model.setRsvOk(value);
                        break;
                    case 32 :
                        //APOPのON/OFF
                        model.setApop(value);
                        break;
                    case 33 :
                        //TOPコマンドの使用
                        model.setTopCmd(value);
                        break;
                    case 34 :
                        //送信前POP認証
                        model.setBeSndPopNinsyo(value);
                        break;
                    case 35 :
                        //送信文字コード
                        model.setSndWordCode(value);
                        break;
                    case 36 :
                        //送信メール形式
                        model.setSndMailType(value);
                        break;
                    case 37 :
                        //宛先の確認
                        model.setCheckAddress(value);
                        break;
                    case 38 :
                        //添付ファイルの確認
                        model.setCheckFile(value);
                        break;
                    case 39 :
                        //添付ファイル自動圧縮
                        model.setCompressFile(value);
                        break;
                    case 40 :
                        //添付ファイル自動圧縮 初期値
                        model.setCompressFileDef(value);
                        break;
                    case 41 :
                        //時間差送信
                        model.setTimeSent(value);
                        break;
                    case 42 :
                        //時間差送信 初期値
                        model.setTimeSentDef(value);
                        break;
                    case 43 :
                        //テーマ
                        model.setTheme(value);
                        break;
                    case 44 :
                        //引用符
                        model.setQuotes(value);
                        break;
                    case 45 :
                        //自動保存
                        model.setAutoSave(value);
                        break;
                    case 46 :
                        //使用ユーザ/グループ区分
                        model.setUserKbn(value);
                        break;
                    case 47 :
                        //使用ユーザ/グループ1
                        model.setUser1(value);
                        break;
                    case 48 :
                        //使用ユーザ/グループ2
                        model.setUser2(value);
                        break;
                    case 49 :
                        //使用ユーザ/グループ3
                        model.setUser3(value);
                        break;
                    case 50 :
                        //使用ユーザ/グループ4
                        model.setUser4(value);
                        break;
                    case 51 :
                        //使用ユーザ/グループ5
                        model.setUser5(value);
                        break;
                    case 52 :
                        //代理人1
                        model.setProxyUser1(value);
                        break;
                    case 53 :
                        //代理人2
                        model.setProxyUser2(value);
                        break;
                    case 54 :
                        //代理人3
                        model.setProxyUser3(value);
                        break;
                    case 55 :
                        //代理人4
                        model.setProxyUser4(value);
                        break;
                    case 56 :
                        //代理人5
                        model.setProxyUser5(value);
                        break;
                    default :
                        break;
                }
            }

            getWebmailList().add(model);
       } catch (Exception e) {
            log__.error("CSVファイル読込み時例外");
            throw e;
        }
    }
}