package jp.groupsession.v2.adr.adr010.csv;

import java.io.PrintWriter;
import java.util.HashMap;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;

/**
 * <br>[機  能] アドレス帳情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010CsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /** 都道府県情報のHashMap */
    private HashMap<Integer, CmnTdfkModel> tdfkMap__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public Adr010CsvRecordListenerImpl(PrintWriter pw) {
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

        Adr010CsvDetailModel csvModel = (Adr010CsvDetailModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //氏名(姓)
        __addCsvData(sb, csvModel.getUserSei());
        //氏名(名)
        __addCsvData(sb, csvModel.getUserMei());
        //氏名カナ(姓)
        __addCsvData(sb, csvModel.getUserSeiKn());
        //氏名カナ(名)
        __addCsvData(sb, csvModel.getUserMeiKn());

         //所属
        __addCsvData(sb, csvModel.getSyozoku());
         //役職名
        __addCsvData(sb, csvModel.getPositionName());

         //メールアドレス１
        __addCsvData(sb, csvModel.getMail1());
         //メールアドレスコメント１
        __addCsvData(sb, csvModel.getMail1Comment());
         //メールアドレス２
        __addCsvData(sb, csvModel.getMail2());
         //メールアドレスコメント３
        __addCsvData(sb, csvModel.getMail2Comment());
         //メールアドレス３
        __addCsvData(sb, csvModel.getMail3());
         //メールアドレスコメント３
        __addCsvData(sb, csvModel.getMail3Comment());

        //郵便番号
        String postNo = "";
        if (!StringUtil.isNullZeroString(csvModel.getPostNo1())
        && !StringUtil.isNullZeroString(csvModel.getPostNo2())) {
            postNo = csvModel.getPostNo1() + "-" + csvModel.getPostNo2();
        }
        __addCsvData(sb, postNo);
         //都道府県
        __addCsvData(sb, csvModel.getTdfk());
         //住所１
        __addCsvData(sb, csvModel.getAddress1());
         //住所２
        __addCsvData(sb, csvModel.getAddress2());

         //電話番号１
        __addCsvData(sb, csvModel.getTel1());
         //内線１
        __addCsvData(sb, csvModel.getNai1());
         //電話番号コメント１
        __addCsvData(sb, csvModel.getTel1Comment());
         //内線２
        __addCsvData(sb, csvModel.getNai2());
         //電話番号２
        __addCsvData(sb, csvModel.getTel2());
         //電話番号コメント２
        __addCsvData(sb, csvModel.getTel2Comment());
         //電話番号３
        __addCsvData(sb, csvModel.getTel3());
         //内線３
        __addCsvData(sb, csvModel.getNai3());
         //電話番号コメント３
        __addCsvData(sb, csvModel.getTel3Comment());

         //ＦＡＸ１
        __addCsvData(sb, csvModel.getFax1());
         //ＦＡＸコメント１
        __addCsvData(sb, csvModel.getFax1Comment());
         //ＦＡＸ２
        __addCsvData(sb, csvModel.getFax2());
         //ＦＡＸコメント２
        __addCsvData(sb, csvModel.getFax2Comment());
         //ＦＡＸ３
        __addCsvData(sb, csvModel.getFax3());
         //ＦＡＸコメント３
        __addCsvData(sb, csvModel.getFax3Comment());

         //備考
        __addCsvData(sb, csvModel.getBiko());

        //企業コード
        __addCsvData(sb, csvModel.getCompanyCode());
        //会社名
        __addCsvData(sb, csvModel.getCompanyName());
         //会社名カナ
        __addCsvData(sb, csvModel.getCompanyNameKn());

        //会社郵便番号
        String companyPostNo = "";
        if (!StringUtil.isNullZeroString(csvModel.getCompanyPostNo1())
        && !StringUtil.isNullZeroString(csvModel.getCompanyPostNo2())) {
            companyPostNo = csvModel.getCompanyPostNo1()
                                + "-" + csvModel.getCompanyPostNo2();
        }
        __addCsvData(sb, companyPostNo);
        //会社都道府県
        __addCsvData(sb, csvModel.getCompanyTdfk());
        //会社住所１
        __addCsvData(sb, csvModel.getCompanyAddress1());
        //会社住所２
        __addCsvData(sb, csvModel.getCompanyAddress2());

        //url
        __addCsvData(sb, csvModel.getCompanyUrl());
        //会社情報備考
        __addCsvData(sb, csvModel.getCompanyRemarks());

        //支店・営業所種別
       __addCsvData(sb, csvModel.getCompanyBaseType());
        //支店・営業所名
       __addCsvData(sb, csvModel.getCompanyBaseName());
        //支店・営業所郵便番号
       String companyBasePostNo = "";
       if (!StringUtil.isNullZeroString(csvModel.getCompanyBasePostNo1())
       && !StringUtil.isNullZeroString(csvModel.getCompanyBasePostNo2())) {
           companyBasePostNo = csvModel.getCompanyBasePostNo1()
                               + "-" + csvModel.getCompanyBasePostNo2();
       }
       __addCsvData(sb, companyBasePostNo);
       //支店・営業所都道府県
       __addCsvData(sb, csvModel.getCompanyBaseTdfk());
       //支店・営業所住所１
       __addCsvData(sb, csvModel.getCompanyBaseAddress1());
       //支店・営業所住所２
       __addCsvData(sb, csvModel.getCompanyBaseAddress2());
       //企業拠点備考
       sb.append(CsvEncode.encString(csvModel.getCompanyBaseRemarks()));

        pw__.println(sb.toString());
    }

    /**
     * <br>[機  能] CSVへ出力する項目を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sb StringBuffer
     * @param value 出力項目
     */
    private void __addCsvData(StringBuilder sb, String value) {

        sb.append(CsvEncode.encString(value));
        sb.append(",");

    }

    /**
     * <p>tdfkMap を取得します。
     * @return tdfkMap
     */
    public HashMap<Integer, CmnTdfkModel> getTdfkMap() {
        return tdfkMap__;
    }

    /**
     * <p>tdfkMap をセットします。
     * @param tdfkMap tdfkMap
     */
    public void setTdfkMap(HashMap<Integer, CmnTdfkModel> tdfkMap) {
        tdfkMap__ = tdfkMap;
    }

}