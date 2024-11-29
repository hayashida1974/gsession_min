package jp.groupsession.v2.rng.csv;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngCsvRecordListenerIppanImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public RngCsvRecordListenerIppanImpl(PrintWriter pw) {
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

        RingiCsvModel csvModel = (RingiCsvModel) model;
        GsMessage gsMsg = new GsMessage();

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //申請ID
        sb.append(CsvEncode.encString(csvModel.getRngId()));

        //タイトル
        sb.append(",");
        sb.append(CsvEncode.encString(csvModel.getRngTitle()));

        //申請ユーザ名
        sb.append(",");
        sb.append(CsvEncode.encString(csvModel.getApprUser()));

        //報告日
        sb.append(",");
        sb.append(CsvEncode.encString(csvModel.getStrRngAppldate()));

        //内容
        //sb.append(",");
        //sb.append(CsvEncode.encString(csvModel.getRngContent()));

        //状態
        String statusStr = "";
        if (csvModel.getRngStatus() == RngConst.RNG_STATUS_DRAFT) {
            // 草稿
            statusStr = gsMsg.getMessage("cmn.draft");
        } else if (csvModel.getRngStatus() == RngConst.RNG_STATUS_REQUEST) {
            // 申請中
            statusStr = gsMsg.getMessage("rng.48");
        } else if (csvModel.getRngStatus() == RngConst.RNG_STATUS_SETTLED) {
            // 決裁
            statusStr = gsMsg.getMessage("rng.29");
        } else if (csvModel.getRngStatus() == RngConst.RNG_STATUS_REJECT) {
            // 却下
            statusStr = gsMsg.getMessage("rng.22");
        } else if (csvModel.getRngStatus() == RngConst.RNG_STATUS_DONE) {
            // 強制完了
            statusStr = gsMsg.getMessage("rng.rng030.06");
        } else if (csvModel.getRngStatus() == RngConst.RNG_STATUS_TORISAGE) {
            // 取り下げ
            statusStr = gsMsg.getMessage("rng.66");
        } else {
            // 不明
            statusStr = gsMsg.getMessage("rng.60");
        }
        sb.append(",");
        sb.append(CsvEncode.encString(statusStr));

        // 最終更新日時
        sb.append(",");
        sb.append(CsvEncode.encString(csvModel.getStrLastManageDate()));

        // 入力フォームデータ
        ArrayList<LabelValueBean> formDataList = csvModel.getFormDataList();

        // 経路データ
        ArrayList<LabelValueBean> keiroList = csvModel.getKeiroList();

        int maxSize  = csvModel.getMaxSize();
        int formSize = (formDataList != null ? formDataList.size() : 0);
        for (int i = 0; i < maxSize; i++) {
            LabelValueBean val = null;
            int idx = (i - formSize);
            if (idx < 0) {
                if (formDataList != null) {
                    val = formDataList.get(i);
                }
            } else {
                if (keiroList != null && keiroList.size() > idx) {
                    val = keiroList.get(idx);
                }
            }

            if (val != null) {
                sb.append(",");
                sb.append(CsvEncode.encString(val.getLabel()));
                sb.append(",");
                sb.append(CsvEncode.encString(val.getValue()));
            } else {
                sb.append(",,");
            }
        }

        pw__.println(sb.toString());
    }
}