package jp.groupsession.v2.man.man112;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.man100.Man100ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man112ParamModel extends Man100ParamModel {
    //非表示項目
    /** プラグインID */
    private String man112pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man112selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man112FileLabelList__ = null;
    /** 既存の役職情報更新フラグ */
    private int man112updateFlg__ = 0;

    /**
     * <p>man112FileLabelList を取得します。
     * @return man112FileLabelList
     */
    public ArrayList<LabelValueBean> getMan112FileLabelList() {
        return man112FileLabelList__;
    }

    /**
     * <p>man112FileLabelList をセットします。
     * @param man112FileLabelList man112FileLabelList
     */
    public void setMan112FileLabelList(ArrayList<LabelValueBean> man112FileLabelList) {
        man112FileLabelList__ = man112FileLabelList;
    }

    /**
     * <p>man112pluginId を取得します。
     * @return man112pluginId
     */
    public String getMan112pluginId() {
        return man112pluginId__;
    }

    /**
     * <p>man112pluginId をセットします。
     * @param man112pluginId man112pluginId
     */
    public void setMan112pluginId(String man112pluginId) {
        man112pluginId__ = man112pluginId;
    }

    /**
     * <p>man112selectFiles を取得します。
     * @return man112selectFiles
     */
    public String[] getMan112selectFiles() {
        return man112selectFiles__;
    }

    /**
     * <p>man112selectFiles をセットします。
     * @param man112selectFiles man112selectFiles
     */
    public void setMan112selectFiles(String[] man112selectFiles) {
        man112selectFiles__ = man112selectFiles;
    }

    /**
     * <p>man112updateFlg を取得します。
     * @return man112updateFlg
     */
    public int getMan112updateFlg() {
        return man112updateFlg__;
    }

    /**
     * <p>man112updateFlg をセットします。
     * @param man112updateFlg man112updateFlg
     */
    public void setMan112updateFlg(int man112updateFlg) {
        man112updateFlg__ = man112updateFlg;
    }
}