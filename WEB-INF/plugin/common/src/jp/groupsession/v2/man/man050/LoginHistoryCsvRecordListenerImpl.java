package jp.groupsession.v2.man.man050;

import java.io.PrintWriter;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.LoginHistorySearchModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 商品情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoginHistoryCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** サーチモデル */
    private LoginHistorySearchModel mdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param mdl LoginHistorySearchModel
     */
    public LoginHistoryCsvRecordListenerImpl(PrintWriter pw,
                                             LoginHistorySearchModel mdl) {
        pw__ = pw;
        mdl__ = mdl;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        //1行分出力
        StringBuilder sb = new StringBuilder();

        if (String.valueOf(mdl__.getMode()).equals(GSConstMain.MODE_LIST)) {
            SltUserPerGroupModel csvModel = (SltUserPerGroupModel) model;

            //社員/職員番号
            sb.append(CsvEncode.encString(
                    NullDefault.getString(csvModel.getSyainno(), "")));
            sb.append(",");

            //氏名
            sb.append(CsvEncode.encString(csvModel.getFullName()));
            sb.append(",");

            //役職
            sb.append(CsvEncode.encString(
                    NullDefault.getString(String.valueOf(csvModel.getYakusyoku()), "")));
            sb.append(",");

            //最終ログイン時間
            sb.append(CsvEncode.encString(String.valueOf(csvModel.getLgintimeStr())));


        } else {

            Man050ViewModel csvModel = (Man050ViewModel) model;

            //社員/職員番号
            sb.append(CsvEncode.encString(
                    NullDefault.getString(csvModel.getSyainno(), "")));
            sb.append(",");

            //氏名
            sb.append(CsvEncode.encString(csvModel.getFullName()));
            sb.append(",");

            //役職
            sb.append(CsvEncode.encString(
                    NullDefault.getString(String.valueOf(csvModel.getYakusyoku()), "")));
            sb.append(",");

            //ログイン時間
            sb.append(CsvEncode.encString(csvModel.getLgintimeStr()));
            sb.append(",");

            //端末
            sb.append(CsvEncode.encString(
                    NullDefault.getString(String.valueOf(csvModel.getTerminalName()), "")));
            sb.append(",");

            //キャリア
            sb.append(CsvEncode.encString(
                    NullDefault.getString(String.valueOf(csvModel.getCarName()), "")));
            sb.append(",");

            //個体識別番号
            sb.append(CsvEncode.encString(
                    NullDefault.getString(String.valueOf(csvModel.getClhUid()), "")));

        }

        pw__.println(sb.toString());
    }
}