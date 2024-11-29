package jp.groupsession.v2.man.man250;

import java.io.PrintWriter;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.man.man250.model.Man250DspModel;

/**
 * <br>[機  能] オペレーションログ情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LogCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public LogCsvRecordListenerImpl(PrintWriter pw) {
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

        Man250DspModel csvModel = (Man250DspModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //ログレベル
        sb.append(CsvEncode.encString(csvModel.getLogLevel()));
        sb.append(",");

        //実行日時
        sb.append(CsvEncode.encString(csvModel.getLogDate()));
        sb.append(",");

        //プラグイン
        sb.append(CsvEncode.encString(csvModel.getPluginName()));
        sb.append(",");

        //実行ユーザ
        if (csvModel.getUsrNameSei() == null || csvModel.getUsrNameSei().equals("")) {
            sb.append("");
        } else {
            sb.append(CsvEncode.encString(String.valueOf(
                    csvModel.getUsrNameSei() + " " + csvModel.getUsrNameMei())));
        }
        sb.append(",");

        //画面ID
        sb.append(CsvEncode.encString(csvModel.getPgId()));
        sb.append(",");

        //画面・機能名
        sb.append(CsvEncode.encString(csvModel.getPgName()));
        sb.append(",");

        //操作コード
        sb.append(CsvEncode.encString(csvModel.getOpCode()));
        sb.append(",");

        //内容
        sb.append(CsvEncode.encString(csvModel.getValue()));
        sb.append(",");

        //IPアドレス
        sb.append(CsvEncode.encString(csvModel.getLogIp()));
        sb.append(",");

        //バージョン
        sb.append(CsvEncode.encString(csvModel.getVersion()));

        pw__.println(sb.toString());
        sb = null;
    }
}