package jp.groupsession.v2.man.man150;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;

/**
 * <br>[機  能] メイン 管理者設定 ライセンスファイル登録・更新画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man150ParamModel extends AbstractParamModel {
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