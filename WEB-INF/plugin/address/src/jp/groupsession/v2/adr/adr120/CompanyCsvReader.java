package jp.groupsession.v2.adr.adr120;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;

/**
 * <br>[機  能] アドレス帳 会社インポート画面 インポートファイルのチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class CompanyCsvReader extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CompanyCsvReader.class);

    /** コネクション */
    private Connection con__ = null;
    /** 会社情報一覧 */
    private List<CompanyCsvModel> companyList__ = null;
    /** 企業コードと会社情報(会社拠点情報)のMapping */
    private HashMap<String, List<CompanyCsvModel>> companyMap__;
    /** 企業コードと会社情報のMapping */
    private HashMap<String, CompanyCsvModel> companyDataMap__ = null;

    /**
     * @return con を戻します。
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * @param con 設定する con。
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>companyList を取得します。
     * @return companyList
     */
    public List<CompanyCsvModel> getCompanyList() {
        return companyList__;
    }
    /**
     * <p>companyList をセットします。
     * @param companyList companyList
     */
    public void setCompanyList(List<CompanyCsvModel> companyList) {
        companyList__ = companyList;
    }
    /**
     * <p>companyMap を取得します。
     * @return companyMap
     */
    public HashMap<String, List<CompanyCsvModel>> getCompanyMap() {
        return companyMap__;
    }
    /**
     * <p>companyMap をセットします。
     * @param companyMap companyMap
     */
    public void setCompanyMap(HashMap<String, List<CompanyCsvModel>> companyMap) {
        companyMap__ = companyMap;
    }
    /**
     * <p>companyDataMap を取得します。
     * @return companyDataMap
     */
    public HashMap<String, CompanyCsvModel> getCompanyDataMap() {
        return companyDataMap__;
    }
    /**
     * <p>companyDataMap をセットします。
     * @param companyDataMap companyDataMap
     */
    public void setCompanyDataMap(
            HashMap<String, CompanyCsvModel> companyDataMap) {
        companyDataMap__ = companyDataMap;
    }

    /**
     * コンストラクタ
     * @param con コネクション
     */
    public CompanyCsvReader(Connection con) {
        setCon(con);
        setCompanyList(new ArrayList<CompanyCsvModel>());
        setCompanyMap(new HashMap<String, List<CompanyCsvModel>>());
        setCompanyDataMap(new HashMap<String, CompanyCsvModel>());
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

            CompanyCsvModel model = new CompanyCsvModel();
            model.setRowNum(num);
            model.setElementCount(csvTokenizer.length());

            for (int index = 0; csvTokenizer.hasMoreElements(); index++) {
                String value = csvTokenizer.nextToken();

                switch (index) {
                    case 0 :
                        //企業コード
                        model.setCompanyCode(value);
                        break;
                    case 1 :
                        //会社名
                        model.setCompanyName(value);
                        break;
                    case 2 :
                        //会社名(カナ)
                        model.setCompanyNameKn(value);
                        break;
                    case 3 :
                        //会社郵便番号
                        model.setCompanyPostNo(value);
                        break;
                    case 4 :
                        //会社都道府県
                        model.setCompanyTdfk(value);
                        break;
                    case 5 :
                        //会社住所１
                        model.setCompanyAddress1(value);
                        break;
                    case 6 :
                        //会社住所２
                        model.setCompanyAddress2(value);
                        break;
                    case 7 :
                        //URL
                        model.setCompanyUrl(value);
                        break;
                    case 8 :
                        //備考
                        model.setCompanyBiko(value);
                        break;
                    case 9 :
                        //企業拠点種別
                        model.setCompanyBaseType(value);
                        break;
                    case 10 :
                        //企業拠点名
                        model.setCompanyBaseName(value);
                        break;
                    case 11 :
                        //郵便番号
                        model.setCompanyBasePostNo(value);
                        break;
                    case 12 :
                        //都道府県
                        model.setCompanyBaseTdfk(value);
                        break;
                    case 13 :
                        //住所１
                        model.setCompanyBaseAddress1(value);
                        break;
                    case 14 :
                        //住所２
                        model.setCompanyBaseAddress2(value);
                        break;
                    case 15 :
                        //企業拠点備考
                        model.setCompanyBaseBiko(value);
                        break;
                    default :
                        break;
                }
            }

            getCompanyList().add(model);

            String companyCode = model.getCompanyCode();
            if (!StringUtil.isNullZeroString(companyCode)) {
                if (getCompanyMap().containsKey(companyCode)) {
                    getCompanyMap().get(companyCode).add(model);
                } else {
                    List<CompanyCsvModel> newCompanyList = new ArrayList<CompanyCsvModel>();
                    newCompanyList.add(model);
                    getCompanyMap().put(companyCode, newCompanyList);
                }


                if (!getCompanyDataMap().containsKey(companyCode)) {
                    getCompanyDataMap().put(companyCode, new CompanyCsvModel());
                }
                if (!StringUtil.isNullZeroString(model.getCompanyName())) {
                    getCompanyDataMap().get(companyCode).setCompanyName(model.getCompanyName());
                }
                if (!StringUtil.isNullZeroString(model.getCompanyNameKn())) {
                    getCompanyDataMap().get(companyCode).setCompanyNameKn(model.getCompanyNameKn());
                }
            }
        } catch (Exception e) {
            log__.error("CSVファイル読込み時例外");
            throw e;
        }
    }
}