package jp.groupsession.v2.sml.sml260;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml240.Sml240ParamModel;

/**
 * <br>[機  能] ショートメール アカウントインポート画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260ParamModel extends Sml240ParamModel {
    /** 初期表示フラグ */
    private int sml260initFlg__ = 0;
    /** プラグインID */
    private String sml260pluginId__ = GSConstSmail.PLUGIN_ID_SMAIL;

    /** 添付ファイル(コンボで選択中) */
    private String[] sml260selectFiles__ = null;
    /** ファイルコンボ */
    private  List<LabelValueBean> sml260FileLabelList__ = null;

    /**
     * <p>sml260initFlg を取得します。
     * @return sml260initFlg
     */
    public int getSml260initFlg() {
        return sml260initFlg__;
    }
    /**
     * <p>sml260initFlg をセットします。
     * @param sml260initFlg sml260initFlg
     */
    public void setSml260initFlg(int sml260initFlg) {
        sml260initFlg__ = sml260initFlg;
    }
    /**
     * <p>sml260FileLabelList を取得します。
     * @return sml260FileLabelList
     */
    public List<LabelValueBean> getSml260FileLabelList() {
        return sml260FileLabelList__;
    }
    /**
     * <p>sml260FileLabelList をセットします。
     * @param sml260FileLabelList sml260FileLabelList
     */
    public void setSml260FileLabelList(List<LabelValueBean> sml260FileLabelList) {
        sml260FileLabelList__ = sml260FileLabelList;
    }
    /**
     * <p>sml260pluginId を取得します。
     * @return sml260pluginId
     */
    public String getSml260pluginId() {
        return sml260pluginId__;
    }
    /**
     * <p>sml260pluginId をセットします。
     * @param sml260pluginId sml260pluginId
     */
    public void setSml260pluginId(String sml260pluginId) {
        sml260pluginId__ = sml260pluginId;
    }
    /**
     * <p>sml260selectFiles を取得します。
     * @return sml260selectFiles
     */
    public String[] getSml260selectFiles() {
        return sml260selectFiles__;
    }
    /**
     * <p>sml260selectFiles をセットします。
     * @param sml260selectFiles sml260selectFiles
     */
    public void setSml260selectFiles(String[] sml260selectFiles) {
        sml260selectFiles__ = sml260selectFiles;
    }
}