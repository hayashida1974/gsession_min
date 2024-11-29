package jp.groupsession.v2.cmn.formmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.util.LabelValueBean;
/**
*
* <br>[機  能] 汎用選択項目 モデル
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class MultiSelectModel extends AbstractFormModel {
    /** 選択済み選択リストchecked ID*/
    private String[] selectedChecked__;
    /** 未選択リスト checked ID*/
    private String[] noSelectedChecked__;
    /** 選択済み表示リスト*/
    private List<? extends LabelValueBean> selectedList__;
    /** 未選択表示リスト*/
    private List<? extends LabelValueBean> noSelectedList__;
    /** 選択済みID */
    private String[] selected__ = new String[0];
    /** 選択解除ID リスト ガラケーの選択解除ボタン用*/
    private HashMap<String, String[]> rmSelectedMbh__ = new HashMap<String, String[]>();

    /** 全リスト*/
    private List<? extends LabelValueBean> allList__;

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(getSelected());
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
        if (!(obj instanceof MultiSelectModel)) {
            return false;
        }
        MultiSelectModel other = (MultiSelectModel) obj;
        if (!Arrays.equals(getSelected(), other.getSelected())) {
            return false;
        }
        return true;
    }
    /**
     *
     */
    public MultiSelectModel() {
        super();
    }
    /**
     * @param json json
     */
    public MultiSelectModel(JSONObject json) {
        super(json);
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
    public String[] getSelected() {
        return selected__;
    }
    /**
     * <p>selected をセットします。
     * @param selected selected
     */
    public void setSelected(String[] selected) {
        selected__ = selected;
    }
    /**
     * <p>allList を取得します。
     * @return allList
     */
    public List<? extends LabelValueBean> getAllList() {
        if (allList__ == null) {
            List<LabelValueBean> list = new ArrayList<LabelValueBean>();
            if (selectedList__ != null) {
                list.addAll(selectedList__);
            }
            if (noSelectedList__ != null) {
                list.addAll(noSelectedList__);
            }
            allList__ = list;
        }
        return allList__;
    }
    /**
     * <p>noSelectedChecked を取得します。
     * @return noSelectedChecked
     */
    public String[] getNoSelectedChecked() {
        return noSelectedChecked__;
    }
    /**
     * <p>noSelectedChecked をセットします。
     * @param noSelectedChecked noSelectedChecked
     */
    public void setNoSelectedChecked(String[] noSelectedChecked) {
        noSelectedChecked__ = noSelectedChecked;
    }
    /**
     * <p>selectedChecked を取得します。
     * @return selectedChecked
     */
    public String[] getSelectedChecked() {
        return selectedChecked__;
    }
    /**
     * <p>selectedChecked をセットします。
     * @param selectedChecked selectedChecked
     */
    public void setSelectedChecked(String[] selectedChecked) {
        selectedChecked__ = selectedChecked;
    }
    /**
     * <p>selectedList を取得します。
     * @return selectedList
     */
    public List<? extends LabelValueBean> getSelectedList() {
        return selectedList__;
    }
    /**
     * <p>selectedList をセットします。
     * @param selectedList selectedList
     */
    public void setSelectedList(List<? extends LabelValueBean> selectedList) {
        selectedList__ = selectedList;
    }
    /**
     * <p>noSelectedList を取得します。
     * @return noSelectedList
     */
    public List<? extends LabelValueBean> getNoSelectedList() {
        return noSelectedList__;
    }
    /**
     * <p>noSelectedList をセットします。
     * @param noSelectedList noSelectedList
     */
    public void setNoSelectedList(List<? extends LabelValueBean> noSelectedList) {
        noSelectedList__ = noSelectedList;
    }
    /**
    *
    * <br>[機  能] マルチ選択要素 表示設定
    * <br>[解  説]
    * <br>[備  考]
    * @param allList 全選択要素
    */
    @SuppressWarnings("unchecked")
   public void setDspSelectData(List<? extends LabelValueBean> allList) {
       setAllList(allList);
       ArrayList<String> selected = new ArrayList<String>();

       if (selected__ != null && selected__.length > 0) {
           //ガラケー版削除ボタンによる選択解除
           Set<String> selectedSet =  new HashSet<String>(
                   CollectionUtils.subtract(
                           Arrays.asList(selected__), rmSelectedMbh__.keySet()));
           selected__ = selectedSet.toArray(new String[selectedSet.size()]);
           selected.addAll(selectedSet);

       }

       List<LabelValueBean> selectedLabel = new ArrayList<LabelValueBean>();
       List<LabelValueBean> noSelectedLabel = new ArrayList<LabelValueBean>();
       for (LabelValueBean label: allList__) {
           int idx = selected.indexOf(label.getValue());
           if (idx >= 0) {
               //選択済み
               selectedLabel.add(label);
               selected.remove(idx);
           } else {
               noSelectedLabel.add(label);
           }
       }
       setSelectedList(selectedLabel);
       setNoSelectedList(noSelectedLabel);
   }
   @Override
   public void setHiddenParam(Cmn999Form msgForm, String paramName) {
       // TODO Cmn999hiddenパラメータへの書き込み処理を追加

   }
   @Override
   public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
       // TODO JSONからオブジェクトに値をセットする処理を記述

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
       if (!(obj instanceof MultiSelectModel)) {
          return;
       }
       MultiSelectModel other = (MultiSelectModel) obj;
       if (ArrayUtils.isEmpty(getSelected())) {
           setSelected(other.getSelected());
       }
       Map<String, String[]> unselMbh = getRmSelectedMbh();
       Map<String, String[]> unselMbhOth = other.getRmSelectedMbh();
       if (!unselMbhOth.isEmpty()) {
           for (Entry<String, String[]> entry:unselMbhOth.entrySet()) {
               if (!unselMbh.containsKey(entry.getKey())) {
                   getRmSelectedMbh().put(entry.getKey(), entry.getValue());
               }
           }
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
       setSelected(values);
   }
/**
 * <p>unSelectedMbh を取得します。
 * @return unSelectedMbh
 * @see jp.groupsession.v2.cmn.formmodel.MultiSelectModel#unSelectedMbh__
 */
public HashMap<String, String[]> getRmSelectedMbh() {
    return rmSelectedMbh__;
}
/**
 * <p>unSelectedMbh をセットします。
 * @param unSelectedMbh unSelectedMbh
 * @see jp.groupsession.v2.cmn.formmodel.MultiSelectModel#unSelectedMbh__
 */
public void setRmSelectedMbh(HashMap<String, String[]> unSelectedMbh) {
    rmSelectedMbh__ = unSelectedMbh;
}
}
