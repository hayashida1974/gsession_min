package jp.groupsession.v2.rng.rng120;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng080.Rng080Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 稟議個人設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng120Form extends Rng080Form {

    /** 稟議表示件数の選択値 */
    public static final String[] RINGICNTVALUE
        = new String[] {"10", "20", "30", "40", "50"};

    /** ショートメール通知設定（管理者設定） */
    private int rng120AdmSmlNtf__ = RngConst.RAR_SML_NTF_USER;
    /** ショートメール通知 */
    private int rng120smlNtf__ = RngConst.RNG_SMAIL_TSUUCHI;
    /** 稟議表示件数 */
    private int rng120ringiCnt__ = 0;

    /** 稟議表示件数 一覧 */
    private List < LabelValueBean > rng120ringiCntLabel__ = null;

    /** ショートメール使用可否*/
    private int canUseSml__;

    /**
     * コンストラクタ
     */
    public Rng120Form() {
        rng120ringiCntLabel__ = new ArrayList < LabelValueBean >();
        for (String label : RINGICNTVALUE) {
            rng120ringiCntLabel__.add(new LabelValueBean(label, label));
        }
    }

    /**
     * <p>rng120ringiCnt を取得します。
     * @return rng120ringiCnt
     */
    public int getRng120ringiCnt() {
        return rng120ringiCnt__;
    }

    /**
     * <p>rng120ringiCnt をセットします。
     * @param rng120ringiCnt rng120ringiCnt
     */
    public void setRng120ringiCnt(int rng120ringiCnt) {
        rng120ringiCnt__ = rng120ringiCnt;
    }

    /**
     * <p>rng120ringiCntLabel を取得します。
     * @return rng120ringiCntLabel
     */
    public List<LabelValueBean> getRng120ringiCntLabel() {
        return rng120ringiCntLabel__;
    }

    /**
     * <p>rng120ringiCntLabel をセットします。
     * @param rng120ringiCntLabel rng120ringiCntLabel
     */
    public void setRng120ringiCntLabel(List<LabelValueBean> rng120ringiCntLabel) {
        rng120ringiCntLabel__ = rng120ringiCntLabel;
    }

    /**
     * <p>rng120smlNtf を取得します。
     * @return rng120smlNtf
     */
    public int getRng120smlNtf() {
        return rng120smlNtf__;
    }

    /**
     * <p>rng120smlNtf をセットします。
     * @param rng120smlNtf rng120smlNtf
     */
    public void setRng120smlNtf(int rng120smlNtf) {
        rng120smlNtf__ = rng120smlNtf;
    }

    /**
     * <p>rng120AdmSmlNtf を取得します。
     * @return rng120AdmSmlNtf
     */
    public int getRng120AdmSmlNtf() {
        return rng120AdmSmlNtf__;
    }

    /**
     * <p>rng120AdmSmlNtf をセットします。
     * @param rng120AdmSmlNtf rng120AdmSmlNtf
     */
    public void setRng120AdmSmlNtf(int rng120AdmSmlNtf) {
        rng120AdmSmlNtf__ = rng120AdmSmlNtf;
    }

    /**
     * <p>canUseSml を取得します。
     * @return canUseSml
     */
    public int getCanUseSml() {
        return canUseSml__;
    }

    /**
     * <p>canUseSml をセットします。
     * @param canUseSml canUseSml
     */
    public void setCanUseSml(int canUseSml) {
        canUseSml__ = canUseSml;
    }

}
