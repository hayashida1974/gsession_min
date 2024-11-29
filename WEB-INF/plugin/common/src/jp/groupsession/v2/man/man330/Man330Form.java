package jp.groupsession.v2.man.man330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 所属情報一括設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330Form extends AbstractGsForm {
    //入力項目
    /** タブ切り替えモード エクスポート:0 インポート:1 */
    private String man330cmdMode__ = GSConstMain.MODE_EXPORT;
    /** CSV出力項目 */
    private String[] man330CsvOutField__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] man330SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man330FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;

    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }
    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }
    /**
     * <p>man330cmdMode を取得します。
     * @return man330cmdMode
     */
    public String getMan330cmdMode() {
        return man330cmdMode__;
    }
    /**
     * <p>man330cmdMode をセットします。
     * @param man330cmdMode man330cmdMode
     */
    public void setMan330cmdMode(String man330cmdMode) {
        man330cmdMode__ = man330cmdMode;
    }
    /**
     * <p>man330CsvOutField を取得します。
     * @return man330CsvOutField
     */
    public String[] getMan330CsvOutField() {
        return man330CsvOutField__;
    }
    /**
     * <p>man330CsvOutField をセットします。
     * @param man330CsvOutField man330CsvOutField
     */
    public void setMan330CsvOutField(String[] man330CsvOutField) {
        man330CsvOutField__ = man330CsvOutField;
    }
    /**
     * <p>man330SelectFiles を取得します。
     * @return man330SelectFiles
     */
    public String[] getMan330SelectFiles() {
        return man330SelectFiles__;
    }
    /**
     * <p>man330SelectFiles をセットします。
     * @param man330SelectFiles man330SelectFiles
     */
    public void setMan330SelectFiles(String[] man330SelectFiles) {
        man330SelectFiles__ = man330SelectFiles;
    }
    /**
     * <p>man330FileLabelList を取得します。
     * @return man330FileLabelList
     */
    public ArrayList<LabelValueBean> getMan330FileLabelList() {
        return man330FileLabelList__;
    }
    /**
     * <p>man330FileLabelList をセットします。
     * @param man330FileLabelList man330FileLabelList
     */
    public void setMan330FileLabelList(ArrayList<LabelValueBean> man330FileLabelList) {
        man330FileLabelList__ = man330FileLabelList;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir 添付DIR
     * @param con コネクション
     * @param allGpIdList 全グループIDリスト
     * @param allUsrIdList 全ユーザIDリスト
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
                                       String tempDir,
                                       Connection con,
                                       List<String> allGpIdList,
                                       List<String> allUsrIdList)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //CSV形式のファイル
        String textCsvFileFormat = gsMsg.getMessage("cmn.csv.file.format");

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        textCaptureFile);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                saveFileName = fMdl.getSaveFileName();
                baseFileName = fMdl.getFileName();
            }

            boolean csvError = false;
            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage(
                            "error.input.notfound.file",
                            textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                textCsvFileFormat);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }

            if (!csvError) {
                String fullPath = tempDir + saveFileName;
                Man330ImportCheck csvCheck = new Man330ImportCheck(errors,
                                                                reqMdl,
                                                                con,
                                                                allGpIdList,
                                                                allUsrIdList);

                //CSVチェック
                if (errors.isEmpty() && csvCheck.isCsvDataOk(fullPath)) {
                    ActionMessage msg =
                        new ActionMessage("error.format.impfile");
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.format.impfile");
                    csvError = true;

                }

                //有効データ数
                setImpDataCnt(csvCheck.getCount());
                if (!csvError && getImpDataCnt() <= 0) {
                    ActionMessage msg =
                        new ActionMessage("error.nodata.impfile");
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.nodata.impfile");
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] CSV出力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return errors
     */
    public ActionErrors validateCsvOutCheck(RequestModel reqMdl) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = "schedule";

        //CSV出力項目にチェック１つもないとき
        if (getMan330CsvOutField() == null) {
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("reserve.output.item"));
            StrutsUtil.addMessage(errors, msg, eprefix + "export");
        }

        return errors;
    }
}
