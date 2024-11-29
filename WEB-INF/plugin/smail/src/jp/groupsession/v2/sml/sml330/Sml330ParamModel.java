package jp.groupsession.v2.sml.sml330;

import java.util.List;

import jp.groupsession.v2.sml.sml240.Sml240ParamModel;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml330ParamModel extends Sml240ParamModel {
    /** フィルター編集モード */
    private int smlFilterCmdMode__ = 0;
    /** 編集対象フィルターSID */
    private int smlEditFilterId__ = 0;
    /** アカウント名 */
    private String sml330accountName__ = null;
    /** フィルターリスト */
    private List<Sml330FilterDataModel> filList__ = null;
    /** チェックされている並び順 */
    private String sml330SortRadio__ = null;
    /** 並び替え対象のラベル */
    private String[] sml330sortLabel__ = null;
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
     * <p>sml330accountName を取得します。
     * @return sml330accountName
     */
    public String getSml330accountName() {
        return sml330accountName__;
    }
    /**
     * <p>sml330accountName をセットします。
     * @param sml330accountName sml330accountName
     */
    public void setSml330accountName(String sml330accountName) {
        sml330accountName__ = sml330accountName;
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
    /**
     * <p>sml330SortRadio を取得します。
     * @return sml330SortRadio
     */
    public String getSml330SortRadio() {
        return sml330SortRadio__;
    }
    /**
     * <p>sml330SortRadio をセットします。
     * @param sml330SortRadio sml330SortRadio
     */
    public void setSml330SortRadio(String sml330SortRadio) {
        sml330SortRadio__ = sml330SortRadio;
    }
    /**
     * <p>sml330sortLabel を取得します。
     * @return sml330sortLabel
     */
    public String[] getSml330sortLabel() {
        return sml330sortLabel__;
    }
    /**
     * <p>sml330sortLabel をセットします。
     * @param sml330sortLabel sml330sortLabel
     */
    public void setSml330sortLabel(String[] sml330sortLabel) {
        sml330sortLabel__ = sml330sortLabel;
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

}