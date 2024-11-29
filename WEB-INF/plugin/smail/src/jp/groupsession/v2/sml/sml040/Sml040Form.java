package jp.groupsession.v2.sml.sml040;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml120.Sml120Form;

/**
 * <br>[機  能] ショートメール 個人設定 表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml040Form extends Sml120Form {

    /** 表示件数 */
    private int sml040ViewCnt__ = 0;
    /** 表示件数コンボ */
    private List < LabelValueBean > sml040DspCntList__ = null;

    /** 自動リロード時間コンボ */
    private List < LabelValueBean > sml040TimeLabelList__ = null;
    /** 自動リロード時間の選択値 */
    private String sml040ReloadTime__ = null;

    /** 社員画像表示設定 */
    private String sml040PhotoDsp__ = "0";

    /** 添付画像表示設定 */
    private int sml040ImageTempDsp__ = GSConstSmail.SML_IMAGE_TEMP_DSP;

    /** 設定種別 表示件数 */
    private int sml040MaxDspStype__ = GSConstSmail.DISP_CONF_USER;
    /** 設定種別 自動リロード時間 */
    private int sml040ReloadTimeStype__ = GSConstSmail.DISP_CONF_USER;
    /** 設定種別 写真表示設定 */
    private int sml040PhotoDspStype__ = GSConstSmail.DISP_CONF_USER;
    /** 設定種別 添付画像表示設定 */
    private int sml040AttachImgDspStype__ = GSConstSmail.DISP_CONF_USER;

    /** メイン表示件数 */
    private String sml040mainDsp__;
    /** 既読ショートメールメイン表示区分 */
    private String sml040kidokuKbn__;
    /** メイン表示ソート */
    private String sml040mainSort__;

    /** メイン表示件数コンボ */
    private ArrayList<LabelValueBean> sml040mainDspList__;

    /**
     * <p>sml040ReloadTime を取得します。
     * @return sml040ReloadTime
     */
    public String getSml040ReloadTime() {
        return sml040ReloadTime__;
    }
    /**
     * <p>sml040ReloadTime をセットします。
     * @param sml040ReloadTime sml040ReloadTime
     */
    public void setSml040ReloadTime(String sml040ReloadTime) {
        sml040ReloadTime__ = sml040ReloadTime;
    }
    /**
     * <p>sml040TimeLabelList を取得します。
     * @return sml040TimeLabelList
     */
    public List<LabelValueBean> getSml040TimeLabelList() {
        return sml040TimeLabelList__;
    }
    /**
     * <p>sml040TimeLabelList をセットします。
     * @param sml040TimeLabelList sml040TimeLabelList
     */
    public void setSml040TimeLabelList(List<LabelValueBean> sml040TimeLabelList) {
        sml040TimeLabelList__ = sml040TimeLabelList;
    }
    /**
     * @return sml040DspCntList__ を戻します。
     */
    public List<LabelValueBean> getSml040DspCntList() {
        return sml040DspCntList__;
    }
    /**
     * @param sml040DspCntList 設定する sml040DspCntList__。
     */
    public void setSml040DspCntList(List<LabelValueBean> sml040DspCntList) {
        sml040DspCntList__ = sml040DspCntList;
    }
    /**
     * @return sml040ViewCnt__ を戻します。
     */
    public int getSml040ViewCnt() {
        return sml040ViewCnt__;
    }
    /**
     * @param sml040ViewCnt 設定する sml040ViewCnt__。
     */
    public void setSml040ViewCnt(int sml040ViewCnt) {
        sml040ViewCnt__ = sml040ViewCnt;
    }
    /**
     * @return sml040PhotoDsp
     */
    public String getSml040PhotoDsp() {
        return sml040PhotoDsp__;
    }
    /**
     * @param sml040PhotoDsp セットする sml040PhotoDsp
     */
    public void setSml040PhotoDsp(String sml040PhotoDsp) {
        sml040PhotoDsp__ = sml040PhotoDsp;
    }
    /**
     * <p>sml040ImageTempDsp を取得します。
     * @return sml040ImageTempDsp
     */
    public int getSml040ImageTempDsp() {
        return sml040ImageTempDsp__;
    }
    /**
     * <p>sml040ImageTempDsp をセットします。
     * @param sml040ImageTempDsp sml040ImageTempDsp
     */
    public void setSml040ImageTempDsp(int sml040ImageTempDsp) {
        sml040ImageTempDsp__ = sml040ImageTempDsp;
    }
    /**
     * <p>sml040MaxDspStype を取得します。
     * @return sml040MaxDspStype
     */
    public int getSml040MaxDspStype() {
        return sml040MaxDspStype__;
    }
    /**
     * <p>sml040MaxDspStype をセットします。
     * @param sml040MaxDspStype sml040MaxDspStype
     */
    public void setSml040MaxDspStype(int sml040MaxDspStype) {
        sml040MaxDspStype__ = sml040MaxDspStype;
    }
    /**
     * <p>sml040ReloadTimeStype を取得します。
     * @return sml040ReloadTimeStype
     */
    public int getSml040ReloadTimeStype() {
        return sml040ReloadTimeStype__;
    }
    /**
     * <p>sml040ReloadTimeStype をセットします。
     * @param sml040ReloadTimeStype sml040ReloadTimeStype
     */
    public void setSml040ReloadTimeStype(int sml040ReloadTimeStype) {
        sml040ReloadTimeStype__ = sml040ReloadTimeStype;
    }
    /**
     * <p>sml040PhotoDspStype を取得します。
     * @return sml040PhotoDspStype
     */
    public int getSml040PhotoDspStype() {
        return sml040PhotoDspStype__;
    }
    /**
     * <p>sml040PhotoDspStype をセットします。
     * @param sml040PhotoDspStype sml040PhotoDspStype
     */
    public void setSml040PhotoDspStype(int sml040PhotoDspStype) {
        sml040PhotoDspStype__ = sml040PhotoDspStype;
    }
    /**
     * <p>sml040AttachImgDspStype を取得します。
     * @return sml040AttachImgDspStype
     */
    public int getSml040AttachImgDspStype() {
        return sml040AttachImgDspStype__;
    }
    /**
     * <p>sml040AttachImgDspStype をセットします。
     * @param sml040AttachImgDspStype sml040AttachImgDspStype
     */
    public void setSml040AttachImgDspStype(int sml040AttachImgDspStype) {
        sml040AttachImgDspStype__ = sml040AttachImgDspStype;
    }
    /**
     * <p>sml040mainDsp を取得します。
     * @return sml040mainDsp
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040mainDsp__
     */
    public String getSml040mainDsp() {
        return sml040mainDsp__;
    }

    /**
     * <p>sml040mainDsp をセットします。
     * @param sml040mainDsp sml040mainDsp
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040mainDsp__
     */
    public void setSml040mainDsp(String sml040mainDsp) {
        sml040mainDsp__ = sml040mainDsp;
    }

    /**
     * <p>sml040kidokuKbn を取得します。
     * @return sml040kidokuKbn
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040kidokuKbn__
     */
    public String getSml040kidokuKbn() {
        return sml040kidokuKbn__;
    }

    /**
     * <p>sml040kidokuKbn をセットします。
     * @param sml040kidokuKbn sml040kidokuKbn
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040kidokuKbn__
     */
    public void setSml040kidokuKbn(String sml040kidokuKbn) {
        sml040kidokuKbn__ = sml040kidokuKbn;
    }

    /**
     * <p>sml040mainSort を取得します。
     * @return sml040mainSort
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040mainSort__
     */
    public String getSml040mainSort() {
        return sml040mainSort__;
    }

    /**
     * <p>sml040mainSort をセットします。
     * @param sml040mainSort sml040mainSort
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040mainSort__
     */
    public void setSml040mainSort(String sml040mainSort) {
        sml040mainSort__ = sml040mainSort;
    }

    /**
     * <p>sml040mainDspList を取得します。
     * @return sml040mainDspList
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040mainDspList__
     */
    public ArrayList<LabelValueBean> getSml040mainDspList() {
        return sml040mainDspList__;
    }

    /**
     * <p>sml040mainDspList をセットします。
     * @param sml040mainDspList sml040mainDspList
     * @see jp.groupsession.v2.sml.sml040.Sml040ParamModel#sml040mainDspList__
     */
    public void setSml040mainDspList(ArrayList<LabelValueBean> sml040mainDspList) {
        sml040mainDspList__ = sml040mainDspList;
    }
}