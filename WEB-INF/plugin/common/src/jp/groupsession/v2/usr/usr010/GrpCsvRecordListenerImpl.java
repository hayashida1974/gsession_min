package jp.groupsession.v2.usr.usr010;

import java.io.PrintWriter;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] グループ情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GrpCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public GrpCsvRecordListenerImpl(PrintWriter pw) {
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
        GrpExportModel csvModel = (GrpExportModel) model;
        //1行分出力
        StringBuilder sb = new StringBuilder();
        //グループID
        sb.append(CsvEncode.encString(csvModel.getGrpId()));
        sb.append(",");
        //グループ名
        sb.append(CsvEncode.encString(csvModel.getGrpName()));
        sb.append(",");
        //グループ名カナ
        sb.append(CsvEncode.encString(csvModel.getGrpNameKn()));
        sb.append(",");
        //親グループID
        sb.append(CsvEncode.encString(csvModel.getParentGpId()));
        sb.append(",");
        //コメント
        sb.append(CsvEncode.encString(csvModel.getGrpComment()));
        pw__.println(sb.toString());
    }
}