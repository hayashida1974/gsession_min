package jp.groupsession.v2.cmn.formmodel;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;


/**
*
* <br>[機  能] 汎用選択項目 単体モデル
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class SingleSelectModel extends AbstractFormModel {
    /** 選択済みID*/
    private String selected__;
    /** 全リスト*/
    private List<? extends LabelValueBean> allList__;



    /**
     *
     */
    public SingleSelectModel() {
        super();
    }
    /**
     * @param json json
     */
    public SingleSelectModel(JSONObject json) {
        super(json);
    }
    /**
     * <p>allList を取得します。
     * @return allList
     */
    public List<? extends LabelValueBean> getAllList() {
        return allList__;
    }
    /**
     * <p>allList をセットします。
     * @param allList allList
     */
    public void setAllList(List<? extends LabelValueBean> allList) {
        allList__ = allList;
    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public String getSelected() {
        return selected__;
    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public String getSelectedLabel() {
        String selected = getSelected();
        String ret = null;
        for (ListIterator<? extends LabelValueBean> it
                = allList__.listIterator(allList__.size());
                it.hasPrevious();) {
            LabelValueBean labelValueBean = it.previous();
            ret = labelValueBean.getLabel();
            if (labelValueBean.getValue().equals(selected)) {
                break;
            }
        }
        return ret;
    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public LabelValueBean getSelectedLabelBean() {
        String selected = getSelected();
        LabelValueBean ret = null;
        for (ListIterator<? extends LabelValueBean> it
                = allList__.listIterator(allList__.size());
                it.hasPrevious();) {
            LabelValueBean labelValueBean = it.previous();
            if (labelValueBean.getValue().equals(selected)) {
                return labelValueBean;
            }
        }
        return ret;
    }

    /**
     * <p>selected をセットします。
     * @param selected selected
     */
    public void setSelected(String selected) {
        selected__ = selected;
    }
    /**
    *
    * <br>[機  能] シングル選択 表示設定
    * <br>[解  説]
    * <br>[備  考]
    * @param allList 全選択要素
    */
   public void setDspSelectData(List<? extends LabelValueBean> allList) {
       setAllList(allList);
       String selected = getSelected();
       String checked = null;
       for (LabelValueBean labelValueBean : allList) {
           if (labelValueBean.getValue().equals(selected)) {
               checked = labelValueBean.getValue();
               break;
           }
       }
       if (checked == null) {
           setSelected("");
       }
   }
    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        // TODO Cmn999hiddenパラメータへの書き込み処理を追加

    }
    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        // TODO JSONからオブジェクトに値をセットする処理を記述
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
        }
        if (jsonObj == null) {
            return;
        }

    }
    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getSelected() == null) ? 0 : getSelected().hashCode());
        return result;
    }
    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SingleSelectModel)) {
            return false;
        }
        SingleSelectModel other = (SingleSelectModel) obj;

        if (StringUtil.isNullZeroString(getSelected())) {
            if (!StringUtil.isNullZeroString(other.getSelected())) {
                return false;
            }
        } else if (!getSelected().equals(other.getSelected())) {
            return false;
        }
        return true;
    }
    @Override
    public EnumFormModelKbn formKbn() {
        return null;
    }
    @Override
    public void merge(AbstractFormModel obj) {
        super.merge(obj);

        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        if (!(obj instanceof SingleSelectModel)) {
            return;
        }
        SingleSelectModel other = (SingleSelectModel) obj;
        if (StringUtil.isNullZeroString(getSelected())) {
            setSelected(other.getSelected());
        }
        int scrollY, otherScY;
        scrollY = getScrollY();
        otherScY = other.getScrollY();
        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }
    }
    @Override
    public void defaultInit() {
    }
    @Override
    public void merge(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return;
        }
        setSelected(values[0]);
    }

}
