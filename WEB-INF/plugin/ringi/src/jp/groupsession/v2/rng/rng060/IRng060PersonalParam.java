package jp.groupsession.v2.rng.rng060;

import java.util.ArrayList;

import jp.groupsession.v2.rng.model.RngTemplateModel;

import org.apache.struts.util.LabelValueBean;
/**
 *
 * <br>[機  能] 稟議テンプレート選択画面 表示情報インタフェース
 * <br>[解  説] 稟議テンプレート選択画面から遷移する画面へ実装する
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IRng060PersonalParam {

    /**
     * <p>rng060SortRadio を取得します。
     * @return rng060SortRadio
     */
    public abstract String getRng060SortRadio();

    /**
     * <p>rng060SortRadio をセットします。
     * @param rng060SortRadio rng060SortRadio
     */
    public abstract void setRng060SortRadio(String rng060SortRadio);

    /**
     * <p>rng060TemplateMode を取得します。
     * @return rng060TemplateMode
     */
    public abstract int getRng060TemplateMode();

    /**
     * <p>rng060TemplateMode をセットします。
     * @param rng060TemplateMode rng060TemplateMode
     */
    public abstract void setRng060TemplateMode(int rng060TemplateMode);

    /**
     * <p>rng060SortRadioPrivate を取得します。
     * @return rng060SortRadioPrivate
     */
    public abstract String getRng060SortRadioPrivate();

    /**
     * <p>rng060SortRadioPrivate をセットします。
     * @param rng060SortRadioPrivate rng060SortRadioPrivate
     */
    public abstract void setRng060SortRadioPrivate(String rng060SortRadioPrivate);


    /**
     * @return rng060SelectCat
     */
    public abstract int getRng060SelectCat();

    /**
     * @param rng060SelectCat 設定する rng060SelectCat
     */
    public abstract void setRng060SelectCat(int rng060SelectCat);

    /**
     * @return rng060SelectCatUsr
     */
    public abstract int getRng060SelectCatUsr();

    /**
     * @param rng060SelectCatUsr 設定する rng060SelectCatUsr
     */
    public abstract void setRng060SelectCatUsr(int rng060SelectCatUsr);

}