package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.DateBox;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;


/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DateBoxPrefarence extends DateBox
implements JSONString {
    /** 初期値 インデント */
    String defaultIndentStr__ = "0";
    /** 表示用 区分リスト*/
    List<LabelValueBean> defaultKbnList__;
   /**
     * <p>defaultKbnList を取得します。
     * @return defaultKbnList
     */
    public List<LabelValueBean> getDefaultKbnList() {
        return defaultKbnList__;
    }
    /**
     * <p>defaultKbnList をセットします。
     * @param defaultKbnList defaultKbnList
     */
    public void setDefaultKbnList(List<LabelValueBean> defaultKbnList) {
        defaultKbnList__ = defaultKbnList;
    }
/**
     * <p>defaultIndentStr を取得します。
     * @return defaultIndentStr
     */
    public String getDefaultIndentStr() {
        return defaultIndentStr__;
    }
    /**
     * <p>defaultIndentStr をセットします。
     * @param defaultIndentStr defaultIndentStr
     */
    public void setDefaultIndentStr(String defaultIndentStr) {
        defaultIndentStr__ = defaultIndentStr;
    }
/**
   *
   * <br>[機  能] 入力値のバリデートチェックを行う
   * <br>[解  説]
   * <br>[備  考] dspInitを実行済みである必要がある
   * @param errors エラー格納用アクションエラー
   * @param reqMdl リクエストモデル
   * @param info バリデート情報
   */
  public void validateCheck(ActionErrors errors, RequestModel reqMdl, ValidateInfo info) {
      GsMessage gsMsg = new GsMessage(reqMdl);
      EnumDefaultDateKbn enumDateKbn = null;
      try {
          enumDateKbn = EnumDefaultDateKbn.valueOf(getDefaultDateKbn());
          if (enumDateKbn != EnumDefaultDateKbn.none) {
              GSValidateCommon.validateNumberInt(errors,
                      getDefaultIndentStr(),
                      gsMsg.getMessage("ntp.10"), 3);

          }
      } catch (EnumOutRangeException e) {
          ActionMessage acmsg = new ActionMessage("error.input.njapan.text",
                  gsMsg.getMessage("ntp.10"),
                  String.valueOf(getDefaultDateKbn()));
          StrutsUtil.addMessage(errors, acmsg, "error.input.njapan.text");
      }

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
      super.mergeJson(json, mergeKbn);
      try {
          setDefaultIndentStr(jsonObj.getString("defaultIndent"));
      } catch (JSONException e) {

      }

  }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        EnumUtil<EnumDefaultDateKbn> util = new EnumUtil<>(EnumDefaultDateKbn.class);
        setDefaultKbnList(util.getSelectList(gsMsg));
        try {
            setDefaultIndent(Integer.parseInt(getDefaultIndentStr()));
        } catch (NumberFormatException e) {
        }
    }
    @Override
    public String toJSONString() {
        // TODO 自動生成されたメソッド・スタブ
        JSONObject obj = new JSONObject();
        obj.put("defaultDateKbn", getDefaultDateKbn());
        obj.put("defaultIndent", getDefaultIndentStr());
        return obj.toString();
    }
}
