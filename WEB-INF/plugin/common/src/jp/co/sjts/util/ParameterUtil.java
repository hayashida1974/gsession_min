package jp.co.sjts.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.LabelValueBean;
/**
 *
 * <br>[機  能] パラメータ関連 Utilクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ParameterUtil {


    /**
     *
     * <br>[機  能] キーで指定のパラメータを文字列で返す
     * <br>[解  説] 配列型やリスト型のパラメータにも対応
     * <br>[備  考]
     * @param bean パラメータ取得対象オブジェクト
     * @param key パラメータ名
     * @return ｛パラメータ値｝, ...}
     */
    public static String[] getParameter(Object bean, String key) {

        String[] values;
        try {
            values = BeanUtils.getArrayProperty(bean, key);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            return null;
        }
        if (values == null) {
            return null;
        }
        return values;
    }
    /**
     *
     * <br>[機  能] キーで指定のパラメータをラベル化しリストで返す
     * <br>[解  説]
     * <br>[備  考]
     * @param bean パラメータ取得対象オブジェクト
     * @param keys パラメータ名
     * @return ラベルリスト｛label:パラメータ名, value:パラメータ値｝, ...
     */
    public static List<LabelValueBean> getParameterLabels(Object bean, String[] keys) {
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        if (keys == null || keys.length == 0) {
            return ret;
        }
        for (String key : keys) {
            String[] values = getParameter(bean, key);


            if (values == null || values.length == 0) {
                PropertyDescriptor pd = null;
                try {
                    pd = PropertyUtils.getPropertyDescriptor(bean, key);
                } catch (IllegalAccessException | InvocationTargetException
                        | NoSuchMethodException e) {
                    continue;
                }
                if (pd.getPropertyType().isArray()
                        || pd.getPropertyType().isAssignableFrom(Collection.class)) {
                    continue;
                }
                LabelValueBean label = new LabelValueBean();
                label.setLabel(key);
                label.setValue("");
                ret.add(label);
                continue;
            }
            for (String value : values) {
                LabelValueBean label = new LabelValueBean();
                label.setLabel(key);
                label.setValue(value);
                ret.add(label);
            }
        }
        return ret;
    }



}
