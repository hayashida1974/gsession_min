package jp.groupsession.v2.adr.adr070;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;

/**
 * <br>[機  能] アドレス帳 アドレスインポート インポートファイル(CSV)チェッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class AddressCsvReader extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AddressCsvReader.class);

    /** アドレス帳情報一覧 */
    private List<AddressCsvModel> addressList__ = null;

    /** 画面モード 0:通常, 1:会社同時登録 **/
    private int cmdMode__ = 0;

    /**
     * <p>addressList を取得します。
     * @return addressList
     */
    public List<AddressCsvModel> getAddressList() {
        return addressList__;
    }
    /**
     * <p>addressList をセットします。
     * @param addressList addressList
     */
    public void setAddressList(List<AddressCsvModel> addressList) {
        addressList__ = addressList;
    }

    /**
     * コンストラクタ
     * @param cmdMode 画面モード
     */
    public AddressCsvReader(int cmdMode) {
        setCmdMode(cmdMode);
        setAddressList(new ArrayList<AddressCsvModel>());
    }

    /**
     * <br>[機　能] 指定したCSVファイルから会社情報を読み込む
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

            AddressCsvModel model = new AddressCsvModel();
            model.setRowNum(num);
            model.setElementCount(csvTokenizer.length());

            for (int index = 0; csvTokenizer.hasMoreElements(); index++) {
                String value = csvTokenizer.nextToken();

                switch (index) {
                    case 0 :
                        //氏名 姓
                        model.setNameSei(value);
                        break;
                    case 1 :
                        //氏名 名
                        model.setNameMei(value);
                        break;
                    case 2 :
                        //氏名カナ 姓
                        model.setNameSeiKn(value);
                        break;
                    case 3 :
                        //氏名カナ 名
                        model.setNameMeiKn(value);
                        break;
                    case 4 :
                        //所属
                        model.setSyozoku(value);
                        break;
                    case 5 :
                        //役職
                        model.setYakusyoku(value);
                        break;
                    case 6 :
                        //メールアドレス１
                        model.setMail1(value);
                        break;
                    case 7 :
                        //メールアドレスコメント１
                        model.setMail1Comment(value);
                        break;
                    case 8 :
                        //メールアドレス２
                        model.setMail2(value);
                        break;
                    case 9 :
                        //メールアドレスコメント２
                        model.setMail2Comment(value);
                        break;
                    case 10 :
                        //メールアドレス３
                        model.setMail3(value);
                        break;
                    case 11 :
                        //メールアドレスコメント３
                        model.setMail3Comment(value);
                        break;
                    case 12 :
                        //郵便番号
                        model.setPostNo(value);
                        break;
                    case 13 :
                        //都道府県
                        model.setTdfk(value);
                        break;
                    case 14 :
                        //住所１
                        model.setAddress1(value);
                        break;
                    case 15 :
                        //住所２
                        model.setAddress2(value);
                        break;
                    case 16 :
                        //電話番号１
                        model.setTel1(value);
                        break;
                    case 17 :
                        //内線１
                        model.setNai1(value);
                        break;
                    case 18 :
                        //電話番号コメント１
                        model.setTel1Comment(value);
                        break;
                    case 19 :
                        //電話番号２
                        model.setTel2(value);
                        break;
                    case 20 :
                        //内線２
                        model.setNai2(value);
                        break;
                    case 21 :
                        //電話番号コメント２
                        model.setTel2Comment(value);
                        break;
                    case 22 :
                        //電話番号３
                        model.setTel3(value);
                        break;
                    case 23 :
                        //内線３
                        model.setNai3(value);
                        break;
                    case 24 :
                        //電話番号コメント３
                        model.setTel3Comment(value);
                        break;
                    case 25 :
                        //ＦＡＸ１
                        model.setFax1(value);
                        break;
                    case 26 :
                        //ＦＡＸコメント１
                        model.setFax1Comment(value);
                        break;
                    case 27 :
                        //ＦＡＸ２
                        model.setFax2(value);
                        break;
                    case 28 :
                        //ＦＡＸコメント２
                        model.setFax2Comment(value);
                        break;
                    case 29 :
                        //ＦＡＸ３
                        model.setFax3(value);
                        break;
                    case 30 :
                        //ＦＡＸコメント３
                        model.setFax3Comment(value);
                        break;
                    case 31 :
                        //備考
                        model.setBiko(value);
                        break;

                    case 32 :
                        //企業コード
                        model.setCompanyCode(value);
                        break;
                    case 33 :
                        //会社名
                        model.setCompanyName(value);
                        break;
                    case 34 :
                        //会社名(カナ)
                        model.setCompanyNameKn(value);
                        break;
                    case 35 :
                        //会社郵便番号
                        model.setCompanyPostNo(value);
                        break;
                    case 36 :
                        //会社都道府県
                        model.setCompanyTdfk(value);
                        break;
                    case 37 :
                        //会社住所１
                        model.setCompanyAddress1(value);
                        break;
                    case 38 :
                        //会社住所２
                        model.setCompanyAddress2(value);
                        break;
                    case 39 :
                        //URL
                        model.setCompanyUrl(value);
                        break;
                    case 40 :
                        //備考
                        model.setCompanyBiko(value);
                        break;
                    case 41 :
                        //企業拠点種別
                        model.setCompanyBaseType(value);
                        break;
                    case 42 :
                        //企業拠点名
                        model.setCompanyBaseName(value);
                        break;
                    case 43 :
                        //郵便番号
                        model.setCompanyBasePostNo(value);
                        break;
                    case 44 :
                        //都道府県
                        model.setCompanyBaseTdfk(value);
                        break;
                    case 45 :
                        //住所１
                        model.setCompanyBaseAddress1(value);
                        break;
                    case 46 :
                        //住所２
                        model.setCompanyBaseAddress2(value);
                        break;
                    case 47 :
                        //企業拠点備考
                        model.setCompanyBaseBiko(value);
                        break;
                    default :
                        break;
                }
            }

            getAddressList().add(model);
       } catch (Exception e) {
            log__.error("CSVファイル読込み時例外");
            throw e;
        }
    }
/**
 * <p>cmdMode を取得します。
 * @return cmdMode
 */
public int getCmdMode() {
    return cmdMode__;
}
/**
 * <p>cmdMode をセットします。
 * @param cmdMode cmdMode
 */
public void setCmdMode(int cmdMode) {
    cmdMode__ = cmdMode;
}
}