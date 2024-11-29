package jp.groupsession.v2.man.man330;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man330.model.Man330CsvModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 所属情報一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man330CsvWriter.class);


    /** 所属情報エクスポートCSVファイル名 */
    public static final String MAN330_FILE_NAME = "memberships.csv";

    /** ユーザ所属データ */
    private List<Man330CsvModel> usrDataMdlList__ = null;
    /** 最大グループ所属数 */
    private int maxGrpCnt__ = 0;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** パラメータ情報 */
    private Man330ParamModel paramMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     */
    public Man330CsvWriter(RequestModel reqMdl, Man330ParamModel paramMdl) {
        reqMdl__ = reqMdl;
        paramMdl__ = paramMdl;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath)
    throws CSVException {

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileName = MAN330_FILE_NAME;
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        //出力初期セット
        setCsvPath(fileFullPath);

        log__.debug("開始");
        write();
        log__.debug("終了");
    }

    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public void create(PrintWriter pw) throws CSVException {

        //ヘッダ
        __writeHeader(pw);

        //明細
        __writeItem(pw);
    }

    /**
     * <br>[機  能] ヘッダ部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {

        if (paramMdl__.getMan330CsvOutField() == null) {
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //1行分出力
        StringBuilder sb = new StringBuilder();
        int fieldCnt = paramMdl__.getMan330CsvOutField().length;
        log__.debug("***ヘッダ出力フィールド数:" + fieldCnt);
        List<String> csvOutGrpTypeList = new ArrayList<String>();
        boolean setFlg = false;

        for (int i = 0; i < paramMdl__.getMan330CsvOutField().length; i++) {

            switch(Integer.parseInt(paramMdl__.getMan330CsvOutField()[i])) {

            case 1:
                //ユーザID
                sb.append(gsMsg.getMessage("cmn.user.id"));
                setFlg = true;
                break;
            case 2:
                if (setFlg) {
                    sb.append(",");
                }
                //氏名
                sb.append(gsMsg.getMessage("cmn.name"));
                setFlg = true;
                break;
            case 3:
                if (setFlg) {
                    sb.append(",");
                }
                //氏名カナ
                sb.append(gsMsg.getMessage("cmn.name.kana"));
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
            setGrpData(csvOutGrpTypeList, sb, gsMsg, setFlg);
        }

        pw.println(sb.toString());
    }

    /**
     * <br>[機  能] グループ情報のヘッダ部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param csvOutTypeList 出力区分リスト
     * @param sb 出力文字
     * @param gsMsg メッセージ出力クラス
     * @param setFlg 区切り付けフラグ
     */
    public void setGrpData(List<String>csvOutTypeList,
                            StringBuilder sb,
                            GsMessage gsMsg,
                            boolean setFlg) {


        for (int i = 1; i <= getMaxGrpCnt(); i++) {
            if (csvOutTypeList.contains("4")) {
                if (setFlg) {
                    sb.append(",");
                }
                //グループID
                sb.append(gsMsg.getMessage("cmn.group.id") + i);
                setFlg = true;
            }
            if (csvOutTypeList.contains("5")) {
                if (setFlg) {
                    sb.append(",");
                }
                //グループ名
                sb.append(gsMsg.getMessage("cmn.group.name") + i);
                setFlg = true;
            }
            if (csvOutTypeList.contains("6")) {
                if (setFlg) {
                    sb.append(",");
                }
                //グループ名カナ
                sb.append(gsMsg.getMessage("user.14") + i);

                if (!setFlg && i < getMaxGrpCnt()) {
                    //グループ名カナのみ出力されたとき
                    sb.append(",");
                }
            }
        }
    }

    /**
     * <br>[機  能] 明細部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        Man330CsvRecordListenerImpl rl = new Man330CsvRecordListenerImpl(pw, paramMdl__);

        try {
            if (usrDataMdlList__ == null) {
                return;
            }

            for (Man330CsvModel model : usrDataMdlList__) {
                rl.setRecord(model);
            }
        } catch (Exception e) {
            throw new CSVException("CSVの書き込みに失敗", e);
        }
    }

    /**
     * <p>usrDataMdlList を取得します。
     * @return usrDataMdlList
     */
    public List<Man330CsvModel> getUsrDataMdlList() {
        return usrDataMdlList__;
    }
    /**
     * <p>usrDataMdlList をセットします。
     * @param usrDataMdlList usrDataMdlList
     */
    public void setUsrDataMdlList(List<Man330CsvModel> usrDataMdlList) {
        usrDataMdlList__ = usrDataMdlList;
    }
    /**
     * <p>maxGrpCnt を取得します。
     * @return maxGrpCnt
     */
    public int getMaxGrpCnt() {
        return maxGrpCnt__;
    }
    /**
     * <p>maxGrpCnt をセットします。
     * @param maxGrpCnt maxGrpCnt
     */
    public void setMaxGrpCnt(int maxGrpCnt) {
        maxGrpCnt__ = maxGrpCnt;
    }
}