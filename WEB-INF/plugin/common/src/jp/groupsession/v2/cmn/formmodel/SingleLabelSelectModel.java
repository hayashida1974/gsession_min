package jp.groupsession.v2.cmn.formmodel;

import java.util.List;

import org.apache.struts.util.LabelValueBean;
/**
 *
 * <br>[機  能] 汎用選択項目 単体モデル
 * <br>[解  説] ジェネリクスで型指定に対応
 * <br>[備  考]
 * @param <T> ラベルクラスの型引数
 * @author JTS
 */
public class SingleLabelSelectModel<T extends LabelValueBean> extends SingleSelectModel {

    @SuppressWarnings("unchecked")
    @Override
    public T getSelectedLabelBean() {
        return (T) super.getSelectedLabelBean();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formmodel.SingleSelectModel#getAllList()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAllList() {
        return (List<T>) super.getAllList();
    }



}
