package jp.groupsession.v2.adr.adr160.csv;

import java.io.PrintWriter;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴一覧画面のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160CsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param reqMdl RequestModel
     */
    public Adr160CsvRecordListenerImpl(PrintWriter pw, RequestModel reqMdl) {
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
        Adr160CsvModel csvModel = (Adr160CsvModel) model;
        //1行分出力
        StringBuilder sb = new StringBuilder();
        //氏名
        sb.append(CsvEncode.encString(csvModel.getAddressName()));
        sb.append(",");
        //氏名カナ
        sb.append(CsvEncode.encString(csvModel.getAddressNameKana()));
        sb.append(",");
        //会社名
        sb.append(CsvEncode.encString(csvModel.getAddressKaisyaName()));
        sb.append(",");
        //会社名カナ
        sb.append(CsvEncode.encString(csvModel.getAddressKaisyaNameKana()));
        sb.append(",");
        //支店・営業所種別
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getAddressEigyosyoSyubetu())));
        sb.append(",");
        //支店・営業所名
        sb.append(CsvEncode.encString(csvModel.getAddressEigyosyoSyubetuName()));
        sb.append(",");
        //タイトル
        sb.append(CsvEncode.encString(csvModel.getAdcTitle()));
        sb.append(",");
        //コンタクト履歴種別名
        sb.append(CsvEncode.encString(csvModel.getTypeName()));
        sb.append(",");
        //コンタクト開始日付
        sb.append(CsvEncode.encString(csvModel.getAddressContactStartDate()));
        sb.append(",");
        //コンタクト開始時間
        sb.append(CsvEncode.encString(csvModel.getAddressContactStartTime()));
        sb.append(",");
        //コンタクト終了日付
        sb.append(CsvEncode.encString(csvModel.getAddressContactEndDate()));
        sb.append(",");
        //コンタクト終了時間
        sb.append(CsvEncode.encString(csvModel.getAddressContactEndTime()));
        sb.append(",");

//        //プロジェクト名
//        sb.append(CsvEncode.encString(csvModel.getProjectName()));
//        sb.append(",");
        //備考
        sb.append(CsvEncode.encString(csvModel.getAdcBiko()));
        pw__.println(sb.toString());
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説] アドレス帳コンタクト履歴一覧検索画面専用
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord010(AbstractModel model) throws CSVException {
        Adr010CsvDetailModel csvModel = (Adr010CsvDetailModel) model;
        //1行分出力
        StringBuilder sb = new StringBuilder();
        //氏名
        sb.append(CsvEncode.encString(csvModel.getUserName()));
        sb.append(",");
        //氏名カナ
        sb.append(CsvEncode.encString(csvModel.getUserNameKn()));
        sb.append(",");
        //会社名
        sb.append(CsvEncode.encString(csvModel.getCompanyName()));
        sb.append(",");
        //会社名カナ
        sb.append(CsvEncode.encString(csvModel.getCompanyNameKn()));
        sb.append(",");
        //支店・営業所種別
        sb.append(CsvEncode.encString(csvModel.getCompanyBaseType()));
        sb.append(",");
        //支店・営業所名
        sb.append(CsvEncode.encString(csvModel.getCompanyBaseName()));
        sb.append(",");
        //タイトル
        sb.append(CsvEncode.encString(csvModel.getContactTitle()));
        sb.append(",");
        //コンタクト履歴種別名
        sb.append(CsvEncode.encString(csvModel.getTypeName()));
        sb.append(",");
        //コンタクト開始日付
        sb.append(CsvEncode.encString(csvModel.getContactDate()));
        sb.append(",");
        //コンタクト開始時間
        sb.append(CsvEncode.encString(csvModel.getContactTime()));
        sb.append(",");
        //コンタクト終了日付
        sb.append(CsvEncode.encString(csvModel.getContactDateTo()));
        sb.append(",");
        //コンタクト終了時間
        sb.append(CsvEncode.encString(csvModel.getContactTimeTo()));
        sb.append(",");
        //備考
        sb.append(CsvEncode.encString(csvModel.getContactBiko()));
        pw__.println(sb.toString());
    }
}