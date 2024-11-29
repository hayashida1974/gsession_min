package jp.groupsession.v2.wml.wml200;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.wml030.Wml030ParamModel;

/**
 * <br>[機  能] WEBメール 管理者設定 ラベル管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml200ParamModel extends Wml030ParamModel {
    /** ラベル編集モード */
    private int wmlLabelCmdMode__ = GSConstWebmail.CMDMODE_ADD;
    /** 編集対象ラベルSID */
    private int wmlEditLabelId__ = 0;
    /** ラベルリスト */
    private List<LabelDataModel> lbnList__ = null;
    /** アカウント名 */
    private String wml200accountName__ = null;
    /** 編集対象ラベルSID */
    private String wml200editLabel__ = null;
    /** 並び替え対象のラベル */
    private String[] wml200sortLabel__ = null;
    /** チェックされている並び順 */
    private String wml200SortRadio__ = null;
    /** 表示回数 */
    private int dspCount__ = 0;
    /**
     * <p>lbnList を取得します。
     * @return lbnList
     */
    public List<LabelDataModel> getLbnList() {
        return lbnList__;
    }
    /**
     * <p>lbnList をセットします。
     * @param lbnList lbnList
     */
    public void setLbnList(List<LabelDataModel> lbnList) {
        lbnList__ = lbnList;
    }
    /**
     * <p>wmlEditLabelId を取得します。
     * @return wmlEditLabelId
     */
    public int getWmlEditLabelId() {
        return wmlEditLabelId__;
    }
    /**
     * <p>wmlEditLabelId をセットします。
     * @param wmlEditLabelId wmlEditLabelId
     */
    public void setWmlEditLabelId(int wmlEditLabelId) {
        wmlEditLabelId__ = wmlEditLabelId;
    }
    /**
     * <p>wmlLabelCmdMode を取得します。
     * @return wmlLabelCmdMode
     */
    public int getWmlLabelCmdMode() {
        return wmlLabelCmdMode__;
    }
    /**
     * <p>wmlLabelCmdMode をセットします。
     * @param wmlLabelCmdMode wmlLabelCmdMode
     */
    public void setWmlLabelCmdMode(int wmlLabelCmdMode) {
        wmlLabelCmdMode__ = wmlLabelCmdMode;
    }

    /**
     * <p>wml200editLabel を取得します。
     * @return wml200editLabel
     */
    public String getWml200editLabel() {
        return wml200editLabel__;
    }

    /**
     * <p>wml200editLabel をセットします。
     * @param wml200editLabel wml200editLabel
     */
    public void setWml200editLabel(String wml200editLabel) {
        wml200editLabel__ = wml200editLabel;
    }

    /**
     * <p>wml200sortLabel を取得します。
     * @return wml200sortLabel
     */
    public String[] getWml200sortLabel() {
        return wml200sortLabel__;
    }

    /**
     * <p>wml200sortLabel をセットします。
     * @param wml200sortLabel wml200sortLabel
     */
    public void setWml200sortLabel(String[] wml200sortLabel) {
        wml200sortLabel__ = wml200sortLabel;
    }

    /**
     * <p>wml200SortRadio を取得します。
     * @return wml200SortRadio
     */
    public String getWml200SortRadio() {
        return wml200SortRadio__;
    }

    /**
     * <p>wml200SortRadio をセットします。
     * @param wml200SortRadio wml200SortRadio
     */
    public void setWml200SortRadio(String wml200SortRadio) {
        wml200SortRadio__ = wml200SortRadio;
    }

    /**
     * <p>dspCount を取得します。
     * @return dspCount
     */
    public int getDspCount() {
        return dspCount__;
    }

    /**
     * <p>dspCount をセットします。
     * @param dspCount dspCount
     */
    public void setDspCount(int dspCount) {
        dspCount__ = dspCount;
    }
    /**
     * <p>wml200accountName を取得します。
     * @return wml200accountName
     */
    public String getWml200accountName() {
        return wml200accountName__;
    }
    /**
     * <p>wml200accountName をセットします。
     * @param wml200accountName wml200accountName
     */
    public void setWml200accountName(String wml200accountName) {
        wml200accountName__ = wml200accountName;
    }
}