package jp.groupsession.v2.sml.sml400;

import java.util.List;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml100.Sml100ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 表示設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml400ParamModel extends Sml100ParamModel {

    /** 表示件数コンボ */
    private List < LabelValueBean > sml400MaxDspList__ = null;
    /** 表示件数 設定種別 */
    private int sml400MaxDspStype__ = GSConstSmail.DISP_CONF_USER;
    /** 表示件数 選択値 */
    private int sml400MaxDsp__ = GSConstSmail.MAX_RECORD_COUNT;

    /** 自動リロード時間コンボ */
    private List < LabelValueBean > sml400ReloadTimeList__ = null;
    /** 自動リロード時間 設定種別 */
    private int sml400ReloadTimeStype__ = GSConstSmail.DISP_CONF_USER;
    /** 自動リロード時間 選択値 */
    private String sml400ReloadTime__ = null;

    /** 写真表示設定 設定種別 */
    private int sml400PhotoDspStype__ = GSConstSmail.DISP_CONF_USER;
    /** 写真表示設定 */
    private int sml400PhotoDsp__ = GSConstSmail.SML_PHOTO_DSP_DSP;

    /** 添付画像表示設定 設定種別 */
    private int sml400AttachImgDspStype__ = GSConstSmail.DISP_CONF_USER;
    /** 添付画像表示設定 */
    private int sml400AttachImgDsp__ = GSConstSmail.SML_IMAGE_TEMP_DSP;


    /**
     * <p>sml400MaxDspList を取得します。
     * @return sml400MaxDspList
     */
    public List<LabelValueBean> getSml400MaxDspList() {
        return sml400MaxDspList__;
    }
    /**
     * <p>sml400MaxDspList をセットします。
     * @param sml400MaxDspList sml400MaxDspList
     */
    public void setSml400MaxDspList(List<LabelValueBean> sml400MaxDspList) {
        sml400MaxDspList__ = sml400MaxDspList;
    }
    /**
     * <p>sml400MaxDspStype を取得します。
     * @return sml400MaxDspStype
     */
    public int getSml400MaxDspStype() {
        return sml400MaxDspStype__;
    }
    /**
     * <p>sml400MaxDspStype をセットします。
     * @param sml400MaxDspStype sml400MaxDspStype
     */
    public void setSml400MaxDspStype(int sml400MaxDspStype) {
        sml400MaxDspStype__ = sml400MaxDspStype;
    }
    /**
     * <p>sml400MaxDsp を取得します。
     * @return sml400MaxDsp
     */
    public int getSml400MaxDsp() {
        return sml400MaxDsp__;
    }
    /**
     * <p>sml400MaxDsp をセットします。
     * @param sml400MaxDsp sml400MaxDsp
     */
    public void setSml400MaxDsp(int sml400MaxDsp) {
        sml400MaxDsp__ = sml400MaxDsp;
    }
    /**
     * <p>sml400ReloadTimeList を取得します。
     * @return sml400ReloadTimeList
     */
    public List<LabelValueBean> getSml400ReloadTimeList() {
        return sml400ReloadTimeList__;
    }
    /**
     * <p>sml400ReloadTimeList をセットします。
     * @param sml400ReloadTimeList sml400ReloadTimeList
     */
    public void setSml400ReloadTimeList(List<LabelValueBean> sml400ReloadTimeList) {
        sml400ReloadTimeList__ = sml400ReloadTimeList;
    }
    /**
     * <p>sml400ReloadTimeStype を取得します。
     * @return sml400ReloadTimeStype
     */
    public int getSml400ReloadTimeStype() {
        return sml400ReloadTimeStype__;
    }
    /**
     * <p>sml400ReloadTimeStype をセットします。
     * @param sml400ReloadTimeStype sml400ReloadTimeStype
     */
    public void setSml400ReloadTimeStype(int sml400ReloadTimeStype) {
        sml400ReloadTimeStype__ = sml400ReloadTimeStype;
    }
    /**
     * <p>sml400ReloadTime を取得します。
     * @return sml400ReloadTime
     */
    public String getSml400ReloadTime() {
        return sml400ReloadTime__;
    }
    /**
     * <p>sml400ReloadTime をセットします。
     * @param sml400ReloadTime sml400ReloadTime
     */
    public void setSml400ReloadTime(String sml400ReloadTime) {
        sml400ReloadTime__ = sml400ReloadTime;
    }
    /**
     * <p>sml400PhotoDspStype を取得します。
     * @return sml400PhotoDspStype
     */
    public int getSml400PhotoDspStype() {
        return sml400PhotoDspStype__;
    }
    /**
     * <p>sml400PhotoDspStype をセットします。
     * @param sml400PhotoDspStype sml400PhotoDspStype
     */
    public void setSml400PhotoDspStype(int sml400PhotoDspStype) {
        sml400PhotoDspStype__ = sml400PhotoDspStype;
    }
    /**
     * <p>sml400PhotoDsp を取得します。
     * @return sml400PhotoDsp
     */
    public int getSml400PhotoDsp() {
        return sml400PhotoDsp__;
    }
    /**
     * <p>sml400PhotoDsp をセットします。
     * @param sml400PhotoDsp sml400PhotoDsp
     */
    public void setSml400PhotoDsp(int sml400PhotoDsp) {
        sml400PhotoDsp__ = sml400PhotoDsp;
    }
    /**
     * <p>sml400AttachImgDspStype を取得します。
     * @return sml400AttachImgDspStype
     */
    public int getSml400AttachImgDspStype() {
        return sml400AttachImgDspStype__;
    }
    /**
     * <p>sml400AttachImgDspStype をセットします。
     * @param sml400AttachImgDspStype sml400AttachImgDspStype
     */
    public void setSml400AttachImgDspStype(int sml400AttachImgDspStype) {
        sml400AttachImgDspStype__ = sml400AttachImgDspStype;
    }
    /**
     * <p>sml400AttachImgDsp を取得します。
     * @return sml400AttachImgDsp
     */
    public int getSml400AttachImgDsp() {
        return sml400AttachImgDsp__;
    }
    /**
     * <p>sml400AttachImgDsp をセットします。
     * @param sml400AttachImgDsp sml400AttachImgDsp
     */
    public void setSml400AttachImgDsp(int sml400AttachImgDsp) {
        sml400AttachImgDsp__ = sml400AttachImgDsp;
    }

}