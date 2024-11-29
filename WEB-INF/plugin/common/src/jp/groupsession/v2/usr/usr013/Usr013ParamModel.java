package jp.groupsession.v2.usr.usr013;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 グループインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr013ParamModel extends AbstractParamModel {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr013ParamModel.class);

    //非表示項目
    /** 初期表示フラグ */
    private int usr013initFlg__ = 0;
    /** プラグインID */
    private String usr013pluginId__ = GSConstUser.PLUGIN_ID_USER;

    /** 添付ファイル(コンボで選択中) */
    private String[] usr013selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> usr013FileLabelList__ = null;
    /** 既存のユーザ情報更新フラグ */
    private int usr013updateFlg__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map, RequestModel reqMdl,
            String tempDir, Connection con) throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();
        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        GsMessage gsMsg = new GsMessage(reqMdl);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text", textCaptureFile);
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
                GroupCsvCheck csvCheck
                         = new GroupCsvCheck(errors, con, usr013updateFlg__, reqMdl);
                //CSVチェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }

        return errors;
    }

    /**
     * <p>usr013initFlg を取得します。
     * @return usr013initFlg
     */
    public int getUsr013initFlg() {
        return usr013initFlg__;
    }

    /**
     * <p>usr013initFlg をセットします。
     * @param usr013initFlg usr013initFlg
     */
    public void setUsr013initFlg(int usr013initFlg) {
        usr013initFlg__ = usr013initFlg;
    }

    /**
     * <p>usr013pluginId を取得します。
     * @return usr013pluginId
     */
    public String getUsr013pluginId() {
        return usr013pluginId__;
    }

    /**
     * <p>usr013pluginId をセットします。
     * @param usr013pluginId usr013pluginId
     */
    public void setUsr013pluginId(String usr013pluginId) {
        usr013pluginId__ = usr013pluginId;
    }

    /**
     * <p>usr013FileLabelList を取得します。
     * @return usr013FileLabelList
     */
    public ArrayList<LabelValueBean> getUsr013FileLabelList() {
        return usr013FileLabelList__;
    }

    /**
     * <p>usr013FileLabelList をセットします。
     * @param usr013FileLabelList usr013FileLabelList
     */
    public void setUsr013FileLabelList(ArrayList<LabelValueBean> usr013FileLabelList) {
        usr013FileLabelList__ = usr013FileLabelList;
    }

    /**
     * <p>usr013selectFiles を取得します。
     * @return usr013selectFiles
     */
    public String[] getUsr013selectFiles() {
        return usr013selectFiles__;
    }

    /**
     * <p>usr013selectFiles をセットします。
     * @param usr013selectFiles usr013selectFiles
     */
    public void setUsr013selectFiles(String[] usr013selectFiles) {
        usr013selectFiles__ = usr013selectFiles;
    }

    /**
     * <p>usr013updateFlg を取得します。
     * @return usr013updateFlg
     */
    public int getUsr013updateFlg() {
        return usr013updateFlg__;
    }

    /**
     * <p>usr013updateFlg をセットします。
     * @param usr013updateFlg usr013updateFlg
     */
    public void setUsr013updateFlg(int usr013updateFlg) {
        usr013updateFlg__ = usr013updateFlg;
    }
}