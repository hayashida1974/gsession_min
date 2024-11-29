package jp.groupsession.v2.sml.sml260;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウントインポート 取込みファイルのチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class SmailCsvReader extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmailCsvReader.class);

    /** アカウント情報一覧 */
    private List<SmailCsvModel> smailList__ = null;

    /**
     * <p>smailList を取得します。
     * @return smailList
     */
    public List<SmailCsvModel> getSmailList() {
        return smailList__;
    }

    /**
     * <p>smailList をセットします。
     * @param smailList smailList
     */
    public void setSmailList(List<SmailCsvModel> smailList) {
        smailList__ = smailList;
    }

    /**
     * コンストラクタ
     */
    public SmailCsvReader() {
        setSmailList(new ArrayList<SmailCsvModel>());
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

            SmailCsvModel model = new SmailCsvModel();
            model.setRowNum(num);
            model.setElementCount(csvTokenizer.length());

            for (int index = 0; csvTokenizer.hasMoreElements(); index++) {
                String value = csvTokenizer.nextToken();

                switch (index) {
                    case 0 :
                        //アカウント名
                        model.setAccountName(value);
                        break;
                    case 1 :
                        //備考
                        model.setBiko(value);
                        break;
                    case 2 :
                        //送信メール形式
                        model.setSndMailType(value);
                        break;
                    case 3 :
                        //使用ユーザ1
                        model.setUser1(value);
                        break;
                    case 4 :
                        //使用ユーザ2
                        model.setUser2(value);
                        break;
                    case 5 :
                        //使用ユーザ3
                        model.setUser3(value);
                        break;
                    case 6 :
                        //使用ユーザ4
                        model.setUser4(value);
                        break;
                    case 7 :
                        //使用ユーザ5
                        model.setUser5(value);
                        break;
                    case 8 :
                        //使用グループ1
                        model.setGroup1(value);
                        break;
                    case 9 :
                        //使用グループ2
                        model.setGroup2(value);
                        break;
                    case 10 :
                        //使用グループ3
                        model.setGroup3(value);
                        break;
                    case 11 :
                        //使用グループ4
                        model.setGroup4(value);
                        break;
                    case 12 :
                        //使用グループ5
                        model.setGroup5(value);
                        break;
                    default :
                        break;
                }
            }

            getSmailList().add(model);
       } catch (Exception e) {
            log__.error("CSVファイル読込み時例外");
            throw e;
        }
    }
}