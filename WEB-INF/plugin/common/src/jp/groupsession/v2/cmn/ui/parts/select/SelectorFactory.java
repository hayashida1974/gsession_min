package jp.groupsession.v2.cmn.ui.parts.select;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import jp.co.sjts.util.CloneableUtil;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 * @param <T> buildで生成するクラス
 * @param <S> 自身のクラス
 * @author JTS
 */
public class SelectorFactory<T extends AbstractSelector, S> extends SelectorParamForm {
    /** 生成対象クラス*/
    private final Class<T> targetClz__;

    public SelectorFactory(Class<T> targetClz) {
        targetClz__ = targetClz;
    }

    /**
     * <p>groupSelectionParamName をセットします。
     * @param groupSelectionParamName groupSelectionParamName
     * @return this
     * @see #groupSelectionParamName__
     */
    @SuppressWarnings("unchecked")
    public S chainGroupSelectionParamName(
            String groupSelectionParamName) {
        setGroupSelectionParamName(groupSelectionParamName);
        return (S) this;
    }
    /**
     * <p>hissuFlg をセットします。
     * <p>必須パラメータです
     * @param hissuFlg hissuFlg
     * @return this
     * @see hissuFlg__
     */
    @SuppressWarnings("unchecked")
    public S chainHissuFlg(boolean hissuFlg) {
        setHissuFlg(hissuFlg);
        return  (S) this;
    }
    /**
     * <p>label をセットします。
     * @param label label
     * @see #label__
     * @return this
     */
    @SuppressWarnings("unchecked")
    public S chainLabel(GsMessageReq label) {
        setLabel(label);
        return  (S) this;
    }
    /**
     *
     * <br>[機  能] パラメータ情報のセットを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param key パラメータキー
     * @param paramName 対象パラメータ名
     * @return this
     */
    @SuppressWarnings("unchecked")
    public S chainOptionParameter(String key, String paramName) {
        setOptionParameter(key, paramName);
        return  (S) this;
    }
    /**
     *
     * <br>[機  能] 選択先要素設定
     * <br>[解  説]
     * <br>[備  考]
     * @param select 選択先要素設定
     * @return this
     */
    @SuppressWarnings("unchecked")
    public S chainSelect(Select.ParamForm select) {
        appendSelect(select);
        return (S) this;
    }
    /**
     *
     * <br>[機  能]  selectionDspInitFilterListをセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param filter
     * @return this
     * @see #selectionDspInitFilterList__
     */
    @SuppressWarnings("unchecked")
    public S chainSelectionDspInitFilter(
            IGroupSelectionDspInitFilter filter) {
        appendSelectionDspInitFilter(filter);
        return (S) this;

    }

    /**
     *
     * <br>[機  能] validateFilterList をセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param filter
     * @return this
     * @see #selectionDspInitFilterList__
     */
    public SelectorParamForm chainValidateFilter(IValidateFilter filter) {
        appendValidateFilter(filter);
        return this;
    }


    public T build() {
        try {
            Object param = getClass().getDeclaredConstructor(Class.class)
                    .newInstance(
                            targetClz__
                            );

            CloneableUtil.copyField(param, this);
            Constructor<T> constructor = targetClz__.getDeclaredConstructor(param.getClass());
            constructor.setAccessible(true);
            return constructor
                    .newInstance(
                            param
                            );


        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
