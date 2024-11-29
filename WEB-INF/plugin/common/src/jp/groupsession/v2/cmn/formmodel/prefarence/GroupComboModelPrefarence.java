package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.formmodel.MultiSelectModel;
/**
   *
   * <br>[機  能] SimpleGroupSelect設定用
   * <br>[解  説]
   * <br>[備  考]
   *
   * @author JTS
   */
  public class GroupComboModelPrefarence extends jp.groupsession.v2.cmn.formmodel.GroupComboModel {
      /**設定用 グループ選択*/
      private MultiSelectModel select__ = new MultiSelectModel();
      /**設定用 デフォルトユーザ選択*/
      private MultiSelectModel defSelect__ = new MultiSelectModel();

      /**
       * <p>select を取得します。
       * @return select
       */
      public MultiSelectModel getSelect() {
          return select__;
      }

      /**
       * <p>select をセットします。
       * @param select select
       */
      public void setSelect(MultiSelectModel select) {
          select__ = select;
      }
      @Override
      public List<String> getSelectable() {
          if (select__.getSelected() == null) {
              return null;
          }
          return Arrays.asList(select__.getSelected());
      }
      @Override
      public void setSelectable(List<String> selectable) {
          select__.setSelected(selectable.toArray(new String[selectable.size()]));
          super.setSelectable(selectable);
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
          JSONObject obj = new JSONObject();
          obj.put("defaultValue", getDefaultValue());
          obj.put("multiFlg", getMultiFlg());
          obj.put("useSeigen", getUseSeigen());
          obj.put("selectable", getSelectable());
          return obj.toString();
      }


  }