package jp.groupsession.v2.rng.rng060;

import java.util.ArrayList;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngTemplateModel;

import org.apache.struts.util.LabelValueBean;
/**
 *
 * <br>[機  能] 稟議テンプレート画面 表示情報 モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng060PersonalParamImpl implements IRng060PersonalParam {
    /** チェックされている並び順(共有) */
    private String rng060SortRadio__ = null;
    /** チェックされている並び順(個人) */
    private String rng060SortRadioPrivate__ = null;
    /** テンプレート種別 */
    private int rng060TemplateMode__ = RngConst.RNG_TEMPLATE_SHARE;
    /** 選択カテゴリ */
    private int rng060SelectCat__ = -1;
    /** 選択カテゴリ(個人) */
    private int rng060SelectCatUsr__ = -1;


    /**
     * <p>rng060SortRadio を取得します。
     * @return rng060SortRadio
     */
    public String getRng060SortRadio() {
        return rng060SortRadio__;
    }
    /**
     * <p>rng060SortRadio をセットします。
     * @param rng060SortRadio rng060SortRadio
     */
    public void setRng060SortRadio(String rng060SortRadio) {
        rng060SortRadio__ = rng060SortRadio;
    }
    /**
     * <p>rng060SortRadioPrivate を取得します。
     * @return rng060SortRadioPrivate
     */
    public String getRng060SortRadioPrivate() {
        return rng060SortRadioPrivate__;
    }
    /**
     * <p>rng060SortRadioPrivate をセットします。
     * @param rng060SortRadioPrivate rng060SortRadioPrivate
     */
    public void setRng060SortRadioPrivate(String rng060SortRadioPrivate) {
        rng060SortRadioPrivate__ = rng060SortRadioPrivate;
    }
    /**
     * <p>rng060TemplateMode を取得します。
     * @return rng060TemplateMode
     */
    public int getRng060TemplateMode() {
        return rng060TemplateMode__;
    }
    /**
     * <p>rng060TemplateMode をセットします。
     * @param rng060TemplateMode rng060TemplateMode
     */
    public void setRng060TemplateMode(int rng060TemplateMode) {
        rng060TemplateMode__ = rng060TemplateMode;
    }
    /**
     * <p>rng060SelectCat を取得します。
     * @return rng060SelectCat
     */
    public int getRng060SelectCat() {
        return rng060SelectCat__;
    }
    /**
     * <p>rng060SelectCat をセットします。
     * @param rng060SelectCat rng060SelectCat
     */
    public void setRng060SelectCat(int rng060SelectCat) {
        rng060SelectCat__ = rng060SelectCat;
    }
    /**
     * <p>rng060SelectCatUsr を取得します。
     * @return rng060SelectCatUsr
     */
    public int getRng060SelectCatUsr() {
        return rng060SelectCatUsr__;
    }
    /**
     * <p>rng060SelectCatUsr をセットします。
     * @param rng060SelectCatUsr rng060SelectCatUsr
     */
    public void setRng060SelectCatUsr(int rng060SelectCatUsr) {
        rng060SelectCatUsr__ = rng060SelectCatUsr;
    }
}
