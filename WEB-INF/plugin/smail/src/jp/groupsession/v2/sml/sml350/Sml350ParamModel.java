package jp.groupsession.v2.sml.sml350;

import java.util.List;

import jp.groupsession.v2.sml.sml270.Sml270ParamModel;
import jp.groupsession.v2.sml.sml330.Sml330FilterDataModel;

/**
 * <br>[機  能] ショートメール個人設定 フィルタ管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml350ParamModel extends Sml270ParamModel {
    /** フィルター編集モード */
    private int smlFilterCmdMode__ = 0;
    /** 編集対象フィルターSID */
    private int smlEditFilterId__ = 0;
    /** アカウント名 */
    private String sml350accountName__ = null;
    /** フィルターリスト */
    private List<Sml330FilterDataModel> filList__ = null;
    /** チェックされている並び順 */
    private String sml350SortRadio__ = null;
    /** 並び替え対象のラベル */
    private String[] sml350sortLabel__ = null;
    /** 表示回数 */
    private int dspCount__ = 0;
    /**
     * <p>smlFilterCmdMode を取得します。
     * @return smlFilterCmdMode
     */
    public int getSmlFilterCmdMode() {
        return smlFilterCmdMode__;
    }
    /**
     * <p>smlFilterCmdMode をセットします。
     * @param smlFilterCmdMode smlFilterCmdMode
     */
    public void setSmlFilterCmdMode(int smlFilterCmdMode) {
        smlFilterCmdMode__ = smlFilterCmdMode;
    }
    /**
     * <p>smlEditFilterId を取得します。
     * @return smlEditFilterId
     */
    public int getSmlEditFilterId() {
        return smlEditFilterId__;
    }
    /**
     * <p>smlEditFilterId をセットします。
     * @param smlEditFilterId smlEditFilterId
     */
    public void setSmlEditFilterId(int smlEditFilterId) {
        smlEditFilterId__ = smlEditFilterId;
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
     * <p>sml350accountName を取得します。
     * @return sml350accountName
     */
    public String getSml350accountName() {
        return sml350accountName__;
    }
    /**
     * <p>sml350accountName をセットします。
     * @param sml350accountName sml350accountName
     */
    public void setSml350accountName(String sml350accountName) {
        sml350accountName__ = sml350accountName;
    }
    /**
     * <p>sml350SortRadio を取得します。
     * @return sml350SortRadio
     */
    public String getSml350SortRadio() {
        return sml350SortRadio__;
    }
    /**
     * <p>sml350SortRadio をセットします。
     * @param sml350SortRadio sml350SortRadio
     */
    public void setSml350SortRadio(String sml350SortRadio) {
        sml350SortRadio__ = sml350SortRadio;
    }
    /**
     * <p>sml350sortLabel を取得します。
     * @return sml350sortLabel
     */
    public String[] getSml350sortLabel() {
        return sml350sortLabel__;
    }
    /**
     * <p>sml350sortLabel をセットします。
     * @param sml350sortLabel sml350sortLabel
     */
    public void setSml350sortLabel(String[] sml350sortLabel) {
        sml350sortLabel__ = sml350sortLabel;
    }
    /**
     * <p>filList を取得します。
     * @return filList
     */
    public List<Sml330FilterDataModel> getFilList() {
        return filList__;
    }
    /**
     * <p>filList をセットします。
     * @param filList filList
     */
    public void setFilList(List<Sml330FilterDataModel> filList) {
        filList__ = filList;
    }

}