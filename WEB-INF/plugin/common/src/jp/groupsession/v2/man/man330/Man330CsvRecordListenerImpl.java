package jp.groupsession.v2.man.man330;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.man.man330.model.Man330CsvModel;

/**
 * <br>[機  能] 所属情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330CsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** パラメータ情報 */
    private Man330ParamModel paramMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param paramMdl パラメータ情報
     */
    public Man330CsvRecordListenerImpl(PrintWriter pw, Man330ParamModel paramMdl) {
        pw__ = pw;
        paramMdl__ = paramMdl;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        if (paramMdl__.getMan330CsvOutField() == null) {
            return;
        }

        Man330CsvModel csvModel = (Man330CsvModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();
        List<String> csvOutGrpTypeList = new ArrayList<String>();
        boolean setFlg = false;

        for (int i = 0; i < paramMdl__.getMan330CsvOutField().length; i++) {

            switch(Integer.parseInt(paramMdl__.getMan330CsvOutField()[i])) {

            case 1:
                //ユーザID
                sb.append(CsvEncode.encString(csvModel.getUserId()));
                setFlg = true;
                break;
            case 2:
                if (setFlg) {
                    sb.append(",");
                }
                //氏名
                sb.append(CsvEncode.encString(csvModel.getUsrName()));
                setFlg = true;
                break;
            case 3:
                if (setFlg) {
                    sb.append(",");
                }
                //氏名カナ
                sb.append(CsvEncode.encString(csvModel.getUsrNameKana()));
                setFlg = true;
                break;

            default:

                if (Integer.parseInt(paramMdl__.getMan330CsvOutField()[i]) > 3) {
                    //グループデータで出力する項目
                    csvOutGrpTypeList.add(paramMdl__.getMan330CsvOutField()[i]);
                }

                break;
            }
        }

        if (csvOutGrpTypeList.size() > 0) {
            //グループデータ
            setGrpData(csvOutGrpTypeList, csvModel, sb, setFlg);
        }

        pw__.println(sb.toString());
    }

    /**
     * <br>[機  能] グループ情報をセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param csvOutTypeList 出力区分リスト
     * @param csvModel DBから取得したModel
     * @param sb 出力文字
     * @param setFlg 区切り付けフラグ
     * @throws CSVException CSV出力時例外
     */
    public void setGrpData(List<String>csvOutTypeList,
                            Man330CsvModel csvModel,
                            StringBuilder sb,
                            boolean setFlg)
    throws CSVException {

        for (int i = 0; i < csvModel.getGrpDataList().size(); i++) {

            if (csvOutTypeList.contains("4")) {
                if (setFlg) {
                    sb.append(",");
                }
                //グループID
                sb.append(CsvEncode.encString(
                        csvModel.getGrpDataList().get(i).getGroupId()));
                setFlg = true;
            }
            if (csvOutTypeList.contains("5")) {
                if (setFlg) {
                    sb.append(",");
                }
                //グループ名
                sb.append(CsvEncode.encString(
                        csvModel.getGrpDataList().get(i).getGroupName()));
                setFlg = true;
            }
            if (csvOutTypeList.contains("6")) {
                if (setFlg) {
                    sb.append(",");
                }
                //グループ名カナ
                sb.append(CsvEncode.encString(
                        csvModel.getGrpDataList().get(i).getGroupNameKana()));

                if (!setFlg && i < csvModel.getGrpDataList().size()) {
                    //グループ名カナのみ出力されたとき
                    sb.append(",");
                }
            }
        }
    }
}