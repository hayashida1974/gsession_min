package jp.groupsession.v2.usr.usr033;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 ユーザ一括削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr033ParamModel extends  AbstractParamModel {

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
}
