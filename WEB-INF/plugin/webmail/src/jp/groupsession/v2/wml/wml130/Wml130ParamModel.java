package jp.groupsession.v2.wml.wml130;

import java.util.List;

import jp.groupsession.v2.wml.wml090.Wml090ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール フィルタ設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml130ParamModel extends Wml090ParamModel {
    /** フィルター編集モード */
    private int wmlFilterCmdMode__ = 0;
    /** 編集対象フィルターSID */
    private int wmlEditFilterId__ = 0;
    /** アカウントリスト */
    private List<LabelValueBean> acntList__ = null;
    /** アカウント */
    private String wml130account__ = null;
    /** 選択 アカウントSID */
    private int wml130accountSid__ = -1;
    /** フィルターリスト */
    private List<Wml130FilterDataModel> filList__ = null;
    /** チェックされている並び順 */
    private String wml130SortRadio__ = null;
    /** 並び替え対象のラベル */
    private String[] wml130sortLabel__ = null;
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
     * <p>acntList を取得します。
     * @return acntList
     */
    public List<LabelValueBean> getAcntList() {
        return acntList__;
    }
    /**
     * <p>acntList をセットします。
     * @param acntList acntList
     */
    public void setAcntList(List<LabelValueBean> acntList) {
        acntList__ = acntList;
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
    public List<Wml130FilterDataModel> getFilList() {
        return filList__;
    }
    /**
     * <p>filList をセットします。
     * @param filList filList
     */
    public void setFilList(List<Wml130FilterDataModel> filList) {
        filList__ = filList;
    }
    /**
     * <p>wml130SortRadio を取得します。
     * @return wml130SortRadio
     */
    public String getWml130SortRadio() {
        return wml130SortRadio__;
    }
    /**
     * <p>wml130SortRadio をセットします。
     * @param wml130SortRadio wml130SortRadio
     */
    public void setWml130SortRadio(String wml130SortRadio) {
        wml130SortRadio__ = wml130SortRadio;
    }
    /**
     * <p>wml130sortLabel を取得します。
     * @return wml130sortLabel
     */
    public String[] getWml130sortLabel() {
        return wml130sortLabel__;
    }
    /**
     * <p>wml130sortLabel をセットします。
     * @param wml130sortLabel wml130sortLabel
     */
    public void setWml130sortLabel(String[] wml130sortLabel) {
        wml130sortLabel__ = wml130sortLabel;
    }
    /**
     * <p>wml130account を取得します。
     * @return wml130account
     */
    public String getWml130account() {
        return wml130account__;
    }
    /**
     * <p>wml130account をセットします。
     * @param wml130account wml130account
     */
    public void setWml130account(String wml130account) {
        wml130account__ = wml130account;
    }
    /**
     * <p>wml130accountSid を取得します。
     * @return wml130accountSid
     */
    public int getWml130accountSid() {
        return wml130accountSid__;
    }
    /**
     * <p>wml130accountSid をセットします。
     * @param wml130accountSid wml130accountSid
     */
    public void setWml130accountSid(int wml130accountSid) {
        wml130accountSid__ = wml130accountSid;
    }
}