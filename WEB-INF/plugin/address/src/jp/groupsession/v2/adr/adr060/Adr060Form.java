package jp.groupsession.v2.adr.adr060;

import java.util.List;

import jp.groupsession.v2.adr.adr050.Adr050Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 表示件数設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr060Form extends Adr050Form {

    /** 表示件数コンボ */
    private List<LabelValueBean> adr060DspCountList__ = null;
    /** 表示件数の選択値 */
    private String adr060DspCount__ = null;
    /** 表示件数の選択値 */
    private String adr060ComCount__ = null;

    /**
     * <p>adr060DspCount を取得します。
     * @return adr060DspCount
     */
    public String getAdr060DspCount() {
        return adr060DspCount__;
    }
    /**
     * <p>adr060DspCount をセットします。
     * @param adr060DspCount adr060DspCount
     */
    public void setAdr060DspCount(String adr060DspCount) {
        adr060DspCount__ = adr060DspCount;
    }
    /**
     * <p>adr060DspCountList を取得します。
     * @return adr060DspCountList
     */
    public List<LabelValueBean> getAdr060DspCountList() {
        return adr060DspCountList__;
    }
    /**
     * <p>adr060DspCountList をセットします。
     * @param adr060DspCountList adr060DspCountList
     */
    public void setAdr060DspCountList(List<LabelValueBean> adr060DspCountList) {
        adr060DspCountList__ = adr060DspCountList;
    }
    /**
     * <p>adr060ComCount を取得します。
     * @return adr060ComCount
     */
    public String getAdr060ComCount() {
        return adr060ComCount__;
    }
    /**
     * <p>adr060ComCount をセットします。
     * @param adr060ComCount adr060ComCount
     */
    public void setAdr060ComCount(String adr060ComCount) {
        adr060ComCount__ = adr060ComCount;
    }
}