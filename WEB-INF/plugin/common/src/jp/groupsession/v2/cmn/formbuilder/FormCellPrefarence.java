package jp.groupsession.v2.cmn.formbuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel.KBN_JSON_MERGE;
import jp.groupsession.v2.cmn.formmodel.Comment;
import jp.groupsession.v2.cmn.formmodel.SimpleMultiSelect;
import jp.groupsession.v2.cmn.formmodel.SimpleSelectBase;
import jp.groupsession.v2.cmn.formmodel.TextInput;
import jp.groupsession.v2.cmn.formmodel.prefarence.BlockListPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.BlockPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.CalcPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.CheckBoxPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.ComboBoxPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.CommentPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.DateBoxPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.GroupComboModelPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.NumberBoxPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.RadioButtonPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.SimpleUserSelectPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.SumPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.TempPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.TextareaPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.TextboxPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.TimeBoxPrefarence;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] FormCell設定用Object
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FormCellPrefarence extends FormCell {
    /** JSON展開後 他フォーム要素マップ*/
    private Map<Integer, FormCell> otherContents__;
    /** タイトル必須フラグ*/
    private int titleRequireFlg__ = 1;

    /**
     * コンストラクタ
     */
    public FormCellPrefarence() {
        super();
    }

    /**
     * コンストラクタ
     * @param json jsonObject
     */
    public FormCellPrefarence(JSONObject json) {
        super(json);
        __mergeJson(json);

    }
    /**
     * コンストラクタ
     * @param json json文字列
     */
    public FormCellPrefarence(String json) {
        super(json);
        try {
            __mergeJson(JSONObject.fromObject(json));
        } catch (JSONException e) {
        }
    }
    /**
    *
    * <br>[機  能] Jsonでクラスのマージ
    * <br>[解  説]
    * <br>[備  考]
    * @param json JSONObject
    */
   private void __mergeJson(JSONObject json) {
       try {
           Map<Integer, FormCell> map = new HashMap<Integer, FormCell>();
           JSONObject others = json.getJSONObject("otherContents");
           for (Object keyObj : others.keySet()) {
               String key = keyObj.toString();
               JSONObject formCell = others.getJSONObject(key);
               FormCell cell = new FormCell(formCell);
               map.put(Integer.valueOf(key),
                       cell);
           }
           otherContents__ = map;
       } catch (JSONException e) {
       }
       if (getType() == null) {
           return;
       }
   }
   public void prefarenceSubmit() {

   }

    @Override
    public void dspInit(RequestModel reqMdl,
           Connection con) throws SQLException, IOToolsException {
       if (getType() == null) {
           return;
       }
       GsMessage gsMsg = new GsMessage(reqMdl);
       GroupBiz grpBiz = new GroupBiz();
       UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
       switch (getType()) {
           case sum:
               ((SumPrefarence) getBody()).setSelect(
                       __createCalcSelect(otherContents__,
                               gsMsg, getType(), getFormID()));

               break;
           case calc:
               ((CalcPrefarence) getBody()).setSelect(
                       __createCalcSelect(otherContents__,
                               gsMsg, getType(), getFormID()));
               ((CalcPrefarence) getBody()).dspInit(reqMdl, con);
               break;
           case user:
               ((SimpleUserSelectPrefarence) getBody()).getSelect()
               .init(con, reqMdl, new String[] {"target"},
                       new String[] {""}, "-1",
                       selBiz.convertLabelValueList(
                               grpBiz.getGroupCombLabelList(
                                       con, false, gsMsg)),
                       new String[0]);

               break;
           case group:
               ((GroupComboModelPrefarence) getBody()).getSelect()
               .setDspSelectData(
                       selBiz.convertLabelValueList(
                               grpBiz.getGroupCombLabelList(
                                       con, false, gsMsg))
                       );
               break;
           case date:
               ((DateBoxPrefarence) getBody()).dspInit(reqMdl, con);
               break;
           case radio:
           case combo:
               __initDefaultSelList(((SimpleSelectBase) getBody()).getList());
               break;
           case check:
               __initDefaultSelList(((SimpleMultiSelect) getBody()).getList());
               break;
           case blocklist:
               ((BlockListPrefarence) getBody()).dspInit(reqMdl, con);
               break;

           default:
               break;
       }
    }
    /**
     *
     * <br>[機  能] チェックボックス、ラジオボックスの選択要素初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param list 選択要素
     */
    private void __initDefaultSelList(List<String> list) {
        if (list.size() <= 0) {
            list.add("");
        }
    }
    @Override
    public void validate(ActionErrors errors, RequestModel reqMdl) {
        validate(errors, reqMdl, new ValidateInfo());
    }
    @Override
    public void validate(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
       GsMessage gsMsg = new GsMessage(reqMdl);
       ActionMessage msg = null;

       //フォームタイトル
       switch (getType()) {
         case label:
             break;
         default:
             GSValidateCommon.validateTextField(errors, getTitle(),
                     "FormTitle",
                     gsMsg.getMessage("cmn.title"),
                     100,
                     (titleRequireFlg__ == 1));
             break;
       }

       //フォームID
       GSValidateCommon.validateTextField(errors, getFormID(),
               "FormId",
               gsMsg.getMessage("rng.rng110.31"),
               100,
               getType() != EnumFormModelKbn.block);
       String formId = getFormID();
       if (formId != null) {
           if (formId.indexOf("[") != -1 || formId.indexOf("]") != -1) {
               msg = new ActionMessage("error.input.formid.bracket",
                       gsMsg.getMessage("rng.rng110.31"));
               StrutsUtil.addMessage(errors, msg, "error.input.formid.bracket");
           }
       }

       for (Entry<Integer, FormCell> entry : otherContents__.entrySet()) {
           FormCell cell = entry.getValue();
           //フォームIDが重複する場合、エラー　error.select.dup.list=[{0}]が重複しています。
           if (!StringUtil.isNullZeroString(cell.getFormID())
                   && !StringUtil.isNullZeroString(this.getFormID())
                   && cell.getSid() != this.getSid()
                   && cell.getFormID().equals(this.getFormID())) {
               msg = new ActionMessage("error.select.dup.list", gsMsg.getMessage("rng.rng110.31"));
               StrutsUtil.addMessage(errors, msg, "error.select.dup.list");
           }
       }
       if (getType() == null) {
           return;
       }
       int chkErrSize = errors.size();
       switch (getType()) {
           case label:
               String value = ((Comment) getBody()).getValue();
               //json内の改行文字は「\n」に統一されるため
               //「\r\n」に変換
               value = value.replaceAll("\\r\\n|\\r|\\n", "\r\n");
               int length = value.length();
               //MAX桁チェック
               if (length > 10000) {
                   msg = new ActionMessage("error.input.length.text",
                           gsMsg.getMessage("cmn.comment"), 10000);
                   StrutsUtil.addMessage(errors, msg, "value");
               }

               int notitle = ((Comment) getBody()).getNotitle();
               if (notitle == 0) {
                   GSValidateCommon.validateTextField(errors, getTitle(),
                           "FormTitle",
                           gsMsg.getMessage("cmn.title"),
                           100,
                           (titleRequireFlg__ == 1));
               }
               break;
           case textbox:
                   GSValidateCommon.validateNumberInt(errors,
                           ((TextInput) getBody()).getMaxlength(),
                   gsMsg.getMessage("main.man200.1"), 5);
                   if (errors.size() == chkErrSize) {
                       int maxlength = Integer.parseInt(
                               ((TextInput) getBody()).getMaxlength());
                       if (maxlength < 1 || maxlength > 10000) {
                           msg = new ActionMessage("error.input.addhani.text",
                                   gsMsg.getMessage("main.man200.1"), 1, 10000);
                           StrutsUtil.addMessage(errors, msg, "cmn.threshold");
                       } else {

                           GSValidateCommon.validateTextField(errors,
                                   ((TextInput) getBody()).getDefaultValue(),
                                   "defaultValue",
                                   gsMsg.getMessage("ntp.10"),
                                   maxlength,
                                   false);
                       }
                   }
               break;
           case textarea:
                   GSValidateCommon.validateNumberInt(errors,
                           ((TextInput) getBody()).getMaxlength(),
                   gsMsg.getMessage("main.man200.1"), 5);
                   if (errors.size() == chkErrSize) {
                       int maxlength = Integer.parseInt(
                               ((TextInput) getBody()).getMaxlength());
                       if (maxlength < 1 || maxlength > 10000) {
                           msg = new ActionMessage("error.input.addhani.text",
                                   gsMsg.getMessage("main.man200.1"), 1, 10000);
                           StrutsUtil.addMessage(errors, msg, "cmn.threshold");
                       } else {
                           value = ((TextInput) getBody()).getDefaultValue();
                           //json内の改行文字は「\n」に統一されるため
                           //「\r\n」に変換
                           value = value.replaceAll("\\r\\n|\\r|\\n", "\r\n");
                           GSValidateCommon.validateTextAreaField(errors,
                                       value,
                                       "defaultValue",
                                       gsMsg.getMessage("ntp.10"),
                                       maxlength,
                                       false);
                       }

                   }
               break;
           case number:
                   GSValidateCommon.validateNumberInt(errors,
                           ((TextInput) getBody()).getMaxlength(),
                   gsMsg.getMessage("main.man200.1"), 2);
                   if (errors.size() == chkErrSize) {
                       int maxlength = NullDefault.getInt(
                               ((TextInput) getBody()).getMaxlength(),
                               0);
                       if (maxlength < 1 || maxlength > 20) {
                           msg = new ActionMessage("error.input.addhani.text",
                                   gsMsg.getMessage("main.man200.1"), 1, 20);
                       } else {
                           GSValidateCommon.validateNumberDecimal(errors,
                                   ((TextInput) getBody()).getDefaultValue(),
                                   gsMsg.getMessage("ntp.10"), maxlength, false);
                       }
                   }
                   GSValidateCommon.validateTextField(errors,
                           ((NumberBoxPrefarence) getBody()).getTanni(),
                           "tanni",
                           gsMsg.getMessage("ntp.102"),
                           20,
                           false);

               break;
           case date:
               ((DateBoxPrefarence) getBody()).validateCheck(errors, reqMdl);
               break;
           case time:
               ((TimeBoxPrefarence) getBody()).validateCheck(errors, reqMdl);
               break;
           case radio:
           case combo:
               __validateSelectList(errors, ((SimpleSelectBase) getBody()).getList(), gsMsg);
               break;
           case check:
               __validateSelectList(errors, ((SimpleMultiSelect) getBody()).getList(), gsMsg);
               break;
           case sum:
               ((SumPrefarence) getBody()).validateCheck(errors, reqMdl);
               break;
           case calc:
               ((CalcPrefarence) getBody()).validateCheck(errors, reqMdl);
               break;
           case blocklist:
               ((BlockListPrefarence) getBody()).validateCheck(errors, reqMdl);
               break;
           default:
               break;
       }

       return;
   }

   /**
    *
    * <br>[機  能] 自動計算 対象選択用コンボ作成
    * <br>[解  説]
    * <br>[備  考]
    * @param others 選択対象要素Map
    * @param gsMsg Gsメッセージ
    * @param type タイプ
    * @param formId フォームID
    * @return 選択リスト
    */
   private List<LabelValueBean> __createCalcSelect(Map<Integer, FormCell> others, GsMessage gsMsg,
           EnumFormModelKbn type, String formId) {
       ArrayList<LabelValueBean> ret = new ArrayList<>();
       if (EnumFormModelKbn.calc == type) {
           ret.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"),
                   "0"));
       } else {
           ret.add(new LabelValueBean(gsMsg.getMessage("cmn.teisu"),
                   "0"));
       }

       for (Entry<Integer, FormCell> entry : others.entrySet()) {
           FormCell cell = entry.getValue();
           if (cell.getFormID().equals(formId)) {
               continue;
           }
           if (!(cell.getType().equals(EnumFormModelKbn.number)
                   || cell.getType().equals(EnumFormModelKbn.sum)
                   || cell.getType().equals(EnumFormModelKbn.calc))) {
               continue;
           }
           if (!StringUtil.isNullZeroString(cell.getFormID())) {
               ret.add(new LabelValueBean(cell.getFormID(),
                       String.valueOf(cell.getSid())));
           }
       }
       return ret;
   }
    /**
    *
    * <br>[機  能] 役職選択の入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors アクションエラー
    * @param list 選択
    * @param gsMsg メッセージオブジェクト
    */
    private void __validateSelectList(ActionErrors errors,
          List<String> list,
          GsMessage gsMsg) {
      if (list.size() == 0) {
          ActionMessage msg = new ActionMessage("error.select.required.text",
                  gsMsg.getMessage("rng.rng110.26"));
          StrutsUtil.addMessage(errors, msg, "error.select.required.text");
          return;
      }
      HashSet<String> listJuuhukuChkSet = new HashSet<String>();
      for (String sel : list) {
          GSValidateCommon.validateTextField(errors,
                  sel,
                  "list",
                  gsMsg.getMessage("rng.rng110.26"),
                  100,
                  true);

          if (listJuuhukuChkSet.contains(sel)) {
              //選択の重複
              ActionMessage msg = new ActionMessage("error.select.dup.list",
                      gsMsg.getMessage("rng.rng110.26"));
              StrutsUtil.addMessage(errors, msg, "error.select.dup.list");
          }
          listJuuhukuChkSet.add(sel);
      }

    }
    @Override
    protected void _mergeBodyJson(EnumFormModelKbn type, JSONObject json) {
        try {
            if (type != null) {
                setBody(type, __createPrefarenceInstance(type, json));
            }
        } catch (JSONException e) {

        }
    }
    /**
    *
    * <br>[機  能] フォームタイプとjsonでインスタンスを生成する。
    * <br>[解  説]
    * <br>[備  考]
    * @param type フォーム種別
    * @param json jsonObject
    * @return インスタンス
    */
    private AbstractFormModel __createPrefarenceInstance(EnumFormModelKbn type, JSON json) {
        if (type == null) {
            return null;
        }
        AbstractFormModel ret = null;
        switch (type) {
        case label:
            ret = new CommentPrefarence();
            break;
        case textbox:
            ret = new TextboxPrefarence();
            break;
        case textarea:
            ret = new TextareaPrefarence();
            break;
        case date:
            ret = new DateBoxPrefarence();
            break;
        case time:
            ret = new TimeBoxPrefarence();
            break;
        case number:
            ret = new NumberBoxPrefarence();
            break;
        case radio:
            ret = new RadioButtonPrefarence();
            break;
        case combo:
            ret = new ComboBoxPrefarence();
            break;
        case check:
            ret = new CheckBoxPrefarence();
            break;
        case sum:
            ret = new SumPrefarence();
            break;
        case calc:
            ret = new CalcPrefarence();
            break;
        case user:
            ret = new SimpleUserSelectPrefarence();
            break;
        case group:
            ret = new GroupComboModelPrefarence();
            break;
        case block:
            ret = new BlockPrefarence();
            break;
        case blocklist:
            ret = new BlockListPrefarence();
            break;
        case file:
            ret = new TempPrefarence();
            break;

        default:
            ret = AbstractFormModel.getInstance(type, json);
            break;
        }
        if (ret != null && json != null) {
            ret.mergeJson(json, KBN_JSON_MERGE.all);
        }
        return ret;

    }

    /**
     * <p>otherContents を取得します。
     * @return otherContents
     */
    public Map<Integer, FormCell> getOtherContents() {
        return otherContents__;
    }

    /**
     * <p>otherContents をセットします。
     * @param otherContents otherContents
     */
    public void setOtherContents(Map<Integer, FormCell> otherContents) {
        otherContents__ = otherContents;
    }
    /**
     * <p>titleRequireFlg を取得します。
     * @return titleRequireFlg
     */
    public int getTitleRequireFlg() {
        return titleRequireFlg__;
    }

    /**
     * <p>titleRequireFlg をセットします。
     * @param titleRequireFlg titleRequireFlg
     */
    public void setTitleRequireFlg(int titleRequireFlg) {
        titleRequireFlg__ = titleRequireFlg;
    }

}
