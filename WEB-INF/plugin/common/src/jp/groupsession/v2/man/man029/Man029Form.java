package jp.groupsession.v2.man.man029;

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
import jp.groupsession.v2.man.man023.Man023Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 休日テンプレートインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man029Form extends Man023Form {
    /** 添付ファイル(コンボで選択中) */
    private String[] man029SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man029FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;
    /** 上書きフラグ */
    private int man029updateFlg__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
                                       String tempDir,
                                       Connection con)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (fileList == null || fileList.size() == 0) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        gsMsg.getMessage(GSConstMain.TEXT_SELECT_FILE));
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
                            gsMsg.getMessage(GSConstMain.TEXT_SELECT_FILE));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                gsMsg.getMessage(GSConstMain.TEXT_CSV_FILE));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }


            String fullPath = tempDir + saveFileName;
            Man029CsvCheck csvCheck = new Man029CsvCheck(errors, reqMdl, con, man029updateFlg__);

            //CSVチェック
            csvCheck.isCsvDataOk(fullPath);

            //有効データ数
            setImpDataCnt(csvCheck.getCount());

            if (!csvError && getImpDataCnt() <= 0) {
                ActionMessage msg =
                    new ActionMessage("error.format.impfile");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.format.impfile");
            }

        }

        return errors;
    }

    /**
     * @return man029SelectFiles
     */
    public String[] getMan029SelectFiles() {
        return man029SelectFiles__;
    }

    /**
     * @param man029SelectFiles セットする man029SelectFiles
     */
    public void setMan029SelectFiles(String[] man029SelectFiles) {
        man029SelectFiles__ = man029SelectFiles;
    }

    /**
     * @return man029FileLabelList
     */
    public ArrayList<LabelValueBean> getMan029FileLabelList() {
        return man029FileLabelList__;
    }

    /**
     * @param man029FileLabelList セットする man029FileLabelList
     */
    public void setMan029FileLabelList(ArrayList<LabelValueBean> man029FileLabelList) {
        man029FileLabelList__ = man029FileLabelList;
    }

    /**
     * @return impDataCnt を戻します。
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }

    /**
     * @param impDataCnt 設定する impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }

    /**
     * @return man029updateFlg
     */
    public int getMan029updateFlg() {
        return man029updateFlg__;
    }

    /**
     * @param man029updateFlg セットする man029updateFlg
     */
    public void setMan029updateFlg(int man029updateFlg) {
        man029updateFlg__ = man029updateFlg;
    }
}