package jp.groupsession.v2.man.man150;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.lic.LicenseOperation;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 ライセンスファイル登録・更新画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man150Form extends AbstractGsForm {

    /** プラグインID */
    private String usr150pluginId__ = GSConstLicese.PLIGIN_ID;
    /** 添付ファイル(コンボで選択中) */
    private String[] man150SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man150FileLabelList__ = null;
    /** ライセンスID */
    public String man150LicenseId__;
    /** 会社名 */
    public String man150LicenseCom__;
    /** プラグイン情報 */
    public ArrayList<LicenseModel> man150PluginList__;
    /** ライセンス区分 */
    public int man150LicenseKbn__;

    /**
     * <p>usr150pluginId を取得します。
     * @return usr150pluginId
     */
    public String getUsr150pluginId() {
        return usr150pluginId__;
    }
    /**
     * <p>usr150pluginId をセットします。
     * @param usr150pluginId usr150pluginId
     */
    public void setUsr150pluginId(String usr150pluginId) {
        usr150pluginId__ = usr150pluginId;
    }
    /**
     * <p>man150SelectFiles を取得します。
     * @return man150SelectFiles
     */
    public String[] getMan150SelectFiles() {
        return man150SelectFiles__;
    }
    /**
     * <p>man150SelectFiles をセットします。
     * @param man150SelectFiles man150SelectFiles
     */
    public void setMan150SelectFiles(String[] man150SelectFiles) {
        man150SelectFiles__ = man150SelectFiles;
    }
    /**
     * <p>man150FileLabelList を取得します。
     * @return man150FileLabelList
     */
    public ArrayList<LabelValueBean> getMan150FileLabelList() {
        return man150FileLabelList__;
    }
    /**
     * <p>man150FileLabelList をセットします。
     * @param man150FileLabelList man150FileLabelList
     */
    public void setMan150FileLabelList(ArrayList<LabelValueBean> man150FileLabelList) {
        man150FileLabelList__ = man150FileLabelList;
    }
    /**
     * <p>man150LicenseId を取得します。
     * @return man150LicenseId
     */
    public String getMan150LicenseId() {
        return man150LicenseId__;
    }
    /**
     * <p>man150LicenseId をセットします。
     * @param man150LicenseId man150LicenseId
     */
    public void setMan150LicenseId(String man150LicenseId) {
        man150LicenseId__ = man150LicenseId;
    }
    /**
     * <p>man150LicenseCom を取得します。
     * @return man150LicenseCom
     */
    public String getMan150LicenseCom() {
        return man150LicenseCom__;
    }
    /**
     * <p>man150LicenseCom をセットします。
     * @param man150LicenseCom man150LicenseCom
     */
    public void setMan150LicenseCom(String man150LicenseCom) {
        man150LicenseCom__ = man150LicenseCom;
    }
    /**
     * <p>man150PluginList を取得します。
     * @return man150PluginList
     */
    public ArrayList<LicenseModel> getMan150PluginList() {
        return man150PluginList__;
    }
    /**
     * <p>man150PluginList をセットします。
     * @param man150PluginList man150PluginList
     */
    public void setMan150PluginList(ArrayList<LicenseModel> man150PluginList) {
        man150PluginList__ = man150PluginList;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param tempDir 添付DIR
     * @param gs GSコンテキスト
     * @param gsUid シリアル番号
     * @return エラー
     * @throws IOToolsException 入出力時例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
                                       String tempDir,
                                       GSContext gs,
                                       String gsUid)
        throws IOToolsException, Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String eprefix = "inputFile.";

        if (fileList == null || fileList.size() == 0) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        gsMsg.getMessage(GSConstMain.TEXT_LICENSE_FILE));
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
            }

            boolean fileError = false;

            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage(
                            "error.input.notfound.file",
                            gsMsg.getMessage(GSConstMain.TEXT_LICENSE_FILE));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                fileError = true;
            }

            if (!fileError) {
                String fullPath = tempDir + saveFileName;
                File impFile = new File(fullPath);
                LicenseOperation lop = new LicenseOperation();

                //ライセンスファイルチェック
                lop.validateImportLicense(errors, reqMdl, impFile, gsUid);
            }
        }

        return errors;
    }
    /**
     * <p>man150LicenseKbn を取得します。
     * @return man150LicenseKbn
     */
    public int getMan150LicenseKbn() {
        return man150LicenseKbn__;
    }
    /**
     * <p>man150LicenseKbn をセットします。
     * @param man150LicenseKbn man150LicenseKbn
     */
    public void setMan150LicenseKbn(int man150LicenseKbn) {
        man150LicenseKbn__ = man150LicenseKbn;
    }
}