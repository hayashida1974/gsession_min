package jp.groupsession.v2.cmn.cmn170;

import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] メイン 個人設定 テーマ設定画面の画面表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn170ParamModel extends AbstractParamModel {
    /** 画面項目表示有無 */
    private int cmn170Dsp1__ = GSConstCommon.NUM_INIT;

    /** クラシックテーマ画像パス保存リスト */
    private List<Cmn170Model> classicThemeList__;

    /** オリジナルテーマ画像パス保存リスト */
    private List<Cmn170Model> originalThemeList__;

    /**
     * <p>classicThemeList を取得します。
     * @return classicThemeList
     */
    public List<Cmn170Model> getClassicThemeList() {
        return classicThemeList__;
    }

    /**
     * <p>classicThemeList をセットします。
     * @param classicThemeList classicThemeList
     */
    public void setClassicThemeList(List<Cmn170Model> classicThemeList) {
        classicThemeList__ = classicThemeList;
    }

    /**
     * <p>originalThemeList を取得します。
     * @return originalThemeList
     */
    public List<Cmn170Model> getOriginalThemeList() {
        return originalThemeList__;
    }

    /**
     * <p>originalThemeList をセットします。
     * @param originalThemeList originalThemeList
     */
    public void setOriginalThemeList(List<Cmn170Model> originalThemeList) {
        originalThemeList__ = originalThemeList;
    }

    /**
     * <p>cmn170Dsp1 を取得します。
     * @return cmn170Dsp1
     */
    public int getCmn170Dsp1() {
        return cmn170Dsp1__;
    }

    /**
     * <p>cmn170Dsp1 をセットします。
     * @param cmn170Dsp1 cmn170Dsp1
     */
    public void setCmn170Dsp1(int cmn170Dsp1) {
        cmn170Dsp1__ = cmn170Dsp1;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return エラー
     */
    public ActionErrors validateCheck() {
        ActionErrors errors = new ActionErrors();
        return errors;
    }
}