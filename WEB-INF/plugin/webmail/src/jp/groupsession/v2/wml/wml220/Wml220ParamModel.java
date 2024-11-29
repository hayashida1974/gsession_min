package jp.groupsession.v2.wml.wml220;

import java.util.List;

import jp.groupsession.v2.wml.wml030.Wml030ParamModel;

/**
 * <br>[機  能] WEBメール 管理者設定 フィルタ管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml220ParamModel extends Wml030ParamModel {
    /** フィルター編集モード */
    private int wmlFilterCmdMode__ = 0;
    /** 編集対象フィルターSID */
    private int wmlEditFilterId__ = 0;
    /** アカウント名 */
    private String wml220accountName__ = null;
    /** フィルターリスト */
    private List<Wml220FilterDataModel> filList__ = null;
    /** チェックされている並び順 */
    private String wml220SortRadio__ = null;
    /** 並び替え対象のラベル */
    private String[] wml220sortLabel__ = null;
    /** 表示回数 */
    private int dspCount__ = 0;

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
     * <p>wmlEditFilterId を取得します。
     * @return wmlEditFilterId
     */
    public int getWmlEditFilterId() {
        return wmlEditFilterId__;
    }
    /**
     * <p>wmlEditFilterId をセットします。
     * @param wmlEditFilterId wmlEditFilterId
     */
    public void setWmlEditFilterId(int wmlEditFilterId) {
        wmlEditFilterId__ = wmlEditFilterId;
    }
    /**
     * <p>wmlFilterCmdMode を取得します。
     * @return wmlFilterCmdMode
     */
    public int getWmlFilterCmdMode() {
        return wmlFilterCmdMode__;
    }
    /**
     * <p>wmlFilterCmdMode をセットします。
     * @param wmlFilterCmdMode wmlFilterCmdMode
     */
    public void setWmlFilterCmdMode(int wmlFilterCmdMode) {
        wmlFilterCmdMode__ = wmlFilterCmdMode;
    }
    /**
     * <p>filList を取得します。
     * @return filList
     */
    public List<Wml220FilterDataModel> getFilList() {
        return filList__;
    }
    /**
     * <p>filList をセットします。
     * @param filList filList
     */
    public void setFilList(List<Wml220FilterDataModel> filList) {
        filList__ = filList;
    }
    /**
     * <p>wml220SortRadio を取得します。
     * @return wml220SortRadio
     */
    public String getWml220SortRadio() {
        return wml220SortRadio__;
    }
    /**
     * <p>wml220SortRadio をセットします。
     * @param wml220SortRadio wml220SortRadio
     */
    public void setWml220SortRadio(String wml220SortRadio) {
        wml220SortRadio__ = wml220SortRadio;
    }
    /**
     * <p>wml220sortLabel を取得します。
     * @return wml220sortLabel
     */
    public String[] getWml220sortLabel() {
        return wml220sortLabel__;
    }
    /**
     * <p>wml220sortLabel をセットします。
     * @param wml220sortLabel wml220sortLabel
     */
    public void setWml220sortLabel(String[] wml220sortLabel) {
        wml220sortLabel__ = wml220sortLabel;
    }
    /**
     * <p>wml220accountName を取得します。
     * @return wml220accountName
     */
    public String getWml220accountName() {
        return wml220accountName__;
    }
    /**
     * <p>wml220accountName をセットします。
     * @param wml220accountName wml220accountName
     */
    public void setWml220accountName(String wml220accountName) {
        wml220accountName__ = wml220accountName;
    }
}