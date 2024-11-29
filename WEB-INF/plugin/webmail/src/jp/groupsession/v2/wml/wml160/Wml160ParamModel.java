package jp.groupsession.v2.wml.wml160;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.wml030.Wml030ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール アカウントインポート画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160ParamModel extends Wml030ParamModel {
    /** プラグインID */
    private String wml160pluginId__ = GSConstWebmail.PLUGIN_ID_WEBMAIL;
    /** 初期表示 */
    private int wml160initFlg__ = 0;
    /** 上書き */
    private int wml160updateFlg__ = 0;

    /** 添付ファイル(コンボで選択中) */
    private String[] wml160selectFiles__ = null;
    /** ファイルコンボ */
    private  List<LabelValueBean> wml160FileLabelList__ = null;

    /**
     * <p>wml160FileLabelList を取得します。
     * @return wml160FileLabelList
     */
    public List<LabelValueBean> getWml160FileLabelList() {
        return wml160FileLabelList__;
    }
    /**
     * <p>wml160FileLabelList をセットします。
     * @param wml160FileLabelList wml160FileLabelList
     */
    public void setWml160FileLabelList(List<LabelValueBean> wml160FileLabelList) {
        wml160FileLabelList__ = wml160FileLabelList;
    }
    /**
     * <p>wml160pluginId を取得します。
     * @return wml160pluginId
     */
    public String getWml160pluginId() {
        return wml160pluginId__;
    }
    /**
     * <p>wml160pluginId をセットします。
     * @param wml160pluginId wml160pluginId
     */
    public void setWml160pluginId(String wml160pluginId) {
        wml160pluginId__ = wml160pluginId;
    }
    /**
     * <p>wml160selectFiles を取得します。
     * @return wml160selectFiles
     */
    public String[] getWml160selectFiles() {
        return wml160selectFiles__;
    }
    /**
     * <p>wml160selectFiles をセットします。
     * @param wml160selectFiles wml160selectFiles
     */
    public void setWml160selectFiles(String[] wml160selectFiles) {
        wml160selectFiles__ = wml160selectFiles;
    }
    /**
     * <p>wml160initFlg を取得します。
     * @return wml160initFlg
     */
    public int getWml160initFlg() {
        return wml160initFlg__;
    }
    /**
     * <p>wml160initFlg をセットします。
     * @param wml160initFlg wml160initFlg
     */
    public void setWml160initFlg(int wml160initFlg) {
        wml160initFlg__ = wml160initFlg;
    }
    /**
     * <p>wml160updateFlg を取得します。
     * @return wml160updateFlg
     */
    public int getWml160updateFlg() {
        return wml160updateFlg__;
    }
    /**
     * <p>wml160updateFlg をセットします。
     * @param wml160updateFlg wml160updateFlg
     */
    public void setWml160updateFlg(int wml160updateFlg) {
        wml160updateFlg__ = wml160updateFlg;
    }
}