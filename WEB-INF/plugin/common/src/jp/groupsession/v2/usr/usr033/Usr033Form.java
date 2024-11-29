package jp.groupsession.v2.usr.usr033;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr030.Usr030Form;

/**
 * <br>[機  能] メイン 管理者設定 ユーザ一括削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr033Form extends Usr030Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr033Form.class);

    //非表示項目
    /** 初期表示フラグ */
    private int usr033initFlg__ = 0;
    /** プラグインID */
    private String usr033pluginId__ = GSConstUser.PLUGIN_ID_USER;

    /** 添付ファイル(コンボで選択中) */
    private String[] usr033selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> usr033FileLabelList__ = null;

    /**
     * <p>usr033initFlg を取得します。
     * @return usr033initFlg
     */
    public int getUsr033initFlg() {
        return usr033initFlg__;
    }

    /**
     * <p>usr033initFlg をセットします。
     * @param usr033initFlg usr033initFlg
     */
    public void setUsr033initFlg(int usr033initFlg) {
        usr033initFlg__ = usr033initFlg;
    }

    /**
     * <p>usr033pluginId を取得します。
     * @return usr033pluginId
     */
    public String getUsr033pluginId() {
        return usr033pluginId__;
    }

    /**
     * <p>usr033pluginId をセットします。
     * @param usr033pluginId usr033pluginId
     */
    public void setUsr033pluginId(String usr033pluginId) {
        usr033pluginId__ = usr033pluginId;
    }

    /**
     * <p>usr033selectFiles を取得します。
     * @return usr033selectFiles
     */
    public String[] getUsr033selectFiles() {
        return usr033selectFiles__;
    }

    /**
     * <p>usr033selectFiles をセットします。
     * @param usr033selectFiles usr033selectFiles
     */
    public void setUsr033selectFiles(String[] usr033selectFiles) {
        usr033selectFiles__ = usr033selectFiles;
    }

    /**
     * <p>usr033FileLabelList を取得します。
     * @return usr033FileLabelList
     */
    public ArrayList<LabelValueBean> getUsr033FileLabelList() {
        return usr033FileLabelList__;
    }

    /**
     * <p>usr033FileLabelList をセットします。
     * @param usr033FileLabelList usr033FileLabelList
     */
    public void setUsr033FileLabelList(ArrayList<LabelValueBean> usr033FileLabelList) {
        usr033FileLabelList__ = usr033FileLabelList;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリパス
     * @param con コネクション
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl,
            String tempDir,
            Connection con)
                    throws Exception {

        ActionErrors errors = new ActionErrors();
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        if (fileList == null || fileList.size() <= 0) {
            ActionMessage msg = new ActionMessage("error.select.required.text", textCaptureFile);
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

            //CSV形式のファイル
            String textCsvFile = gsMsg.getMessage("cmn.csv.file.format");
            boolean csvError = false;
            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file", textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage("error.select.required.text", textCsvFile);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }
            if (!csvError) {
                String fullPath = tempDir + saveFileName;
                log__.debug("FULLPATH==" + fullPath);
                UserDelCsvCheck csvCheck =
                        new UserDelCsvCheck(errors, con, reqMdl);
                //CSVチェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }
        return errors;
    }
}
