package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.formmodel.MultiSelectModel;
import jp.groupsession.v2.cmn.formmodel.SimpleUserSelect;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;

/**
    *
    * <br>[機  能] SimpleUserSelect設定用
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   public class SimpleUserSelectPrefarence
   extends SimpleUserSelect {
       /**設定用 ユーザ選択*/
       private UserGroupSelectModel select__ = new UserGroupSelectModel();
       /**設定用 デフォルトユーザ選択*/
       private MultiSelectModel defSelect__ = new MultiSelectModel();

       /**
        * <p>select を取得します。
        * @return select
        */
       public UserGroupSelectModel getSelect() {
           return select__;
       }

       /**
        * <p>select をセットします。
        * @param select select
        */
       public void setSelect(UserGroupSelectModel select) {
           select__ = select;
       }
       @Override
       public List<String> getSelectable() {
           if (select__.getSelected("target") == null
                   || select__.getSelected("target").length == 0) {
               return Arrays.asList(new String[] {""});
           }
           return Arrays.asList(select__.getSelected("target"));
       }
       @Override
       public void setSelectable(List<String> selectable) {
           select__.setSelected("target",
                   selectable.toArray(new String[selectable.size()]));
       }
       @Override
       public List<String> getDefaultValue() {
           if (defSelect__.getSelected() == null || defSelect__.getSelected().length == 0) {
               return Arrays.asList(new String[] {""});
           }
           return Arrays.asList(defSelect__.getSelected());
       };
       @Override
       public void setDefaultValue(List<String> defaultValue) {
           defSelect__.setSelected(
                   defaultValue.toArray(new String[defaultValue.size()]));
       }

       @Override
       public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
           JSONObject jsonObj = null;
           if (json instanceof JSONObject) {
               jsonObj = (JSONObject) json;
           }
           if (jsonObj == null) {
               return;
           }
           super.mergeJson(jsonObj, mergeKbn);
           if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
               return;
           }
           try {
               select__.mergeJson(jsonObj.getJSONObject("select"), KBN_JSON_MERGE.value);
           } catch (JSONException e) {

           }
           try {
               List<String> value = new ArrayList<>();
               for (Object obj : jsonObj.getJSONArray("selectable")) {
                   value.add(obj.toString());
               }
               setSelectable(value);
           } catch (JSONException e) {

           }
       }

       /**
        * <p>defSelect を取得します。
        * @return defSelect
        */
       public MultiSelectModel getDefSelect() {
           return defSelect__;
       }

       /**
        * <p>defSelect をセットします。
        * @param defSelect defSelect
        */
       public void setDefSelect(MultiSelectModel defSelect) {
           defSelect__ = defSelect;
       }
       @Override
       public String toJSONString() {
           return super.toJSONString();
       }

   }