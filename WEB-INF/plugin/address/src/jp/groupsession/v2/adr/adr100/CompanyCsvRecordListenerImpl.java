package jp.groupsession.v2.adr.adr100;

import java.io.PrintWriter;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.adr.adr120.CompanyCsvModel;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 会社情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CompanyCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public CompanyCsvRecordListenerImpl(PrintWriter pw) {
        pw__ = pw;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        CompanyCsvModel csvModel = (CompanyCsvModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //企業コード
        sb.append(CsvEncode.encString(csvModel.getCompanyCode()));
        sb.append(",");
        //会社名
        sb.append(CsvEncode.encString(csvModel.getCompanyName()));
        sb.append(",");
        //会社名(カナ)
        sb.append(CsvEncode.encString(csvModel.getCompanyNameKn()));
        sb.append(",");
        //会社郵便番号
        sb.append(CsvEncode.encString(csvModel.getCompanyPostNo()));
        sb.append(",");
        //会社都道府県
        sb.append(CsvEncode.encString(csvModel.getCompanyTdfk()));
        sb.append(",");
        //会社住所１
        sb.append(CsvEncode.encString(csvModel.getCompanyAddress1()));
        sb.append(",");
        //会社住所２
        sb.append(CsvEncode.encString(csvModel.getCompanyAddress2()));
        sb.append(",");
        //URL
        sb.append(CsvEncode.encString(csvModel.getCompanyUrl()));
        sb.append(",");
        //備考
        sb.append(CsvEncode.encString(csvModel.getCompanyBiko()));
        sb.append(",");

        if (StringUtil.isNullZeroString(csvModel.getCompanyBaseName())) {
            sb.append("" + "," + "" + "," + "" + "," + "" + "," + "" + "," + "" + "," + "");
        } else {
            //企業拠点種別
            sb.append(CsvEncode.encString(csvModel.getCompanyBaseType()));
            sb.append(",");
            //企業拠点名
            sb.append(CsvEncode.encString(csvModel.getCompanyBaseName()));
            sb.append(",");
            //郵便番号
            sb.append(CsvEncode.encString(csvModel.getCompanyBasePostNo()));
            sb.append(",");
            //都道府県
            sb.append(CsvEncode.encString(csvModel.getCompanyBaseTdfk()));
            sb.append(",");
            //住所1
            sb.append(CsvEncode.encString(csvModel.getCompanyBaseAddress1()));
            sb.append(",");
            //住所2
            sb.append(CsvEncode.encString(csvModel.getCompanyBaseAddress2()));
            sb.append(",");
            //企業拠点備考
            sb.append(CsvEncode.encString(csvModel.getCompanyBaseBiko()));
        }

        pw__.println(sb.toString());
    }
}