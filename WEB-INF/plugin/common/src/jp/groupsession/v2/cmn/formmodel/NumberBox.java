package jp.groupsession.v2.cmn.formmodel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Objects;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 数値入力要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NumberBox extends TextInput {
    /** 単位 */
    private String tanni__;
    /** デフォルトコンストラクタ*/
    public NumberBox() {
        setMaxlength("9");
    }
    /**
     * <p>tanni を取得します。
     * @return tanni
     */
    public String getTanni() {
        return tanni__;
    }

    /**
     * <p>tanni をセットします。
     * @param tanni tanni
     */
    public void setTanni(String tanni) {
        tanni__ = tanni;
    }
    @Override
    public void setValue(String value) {
        super.setValue(value);
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
            setTanni(jsonObj.getString("tanni"));
        } catch (JSONException e) {

        }
    }


    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        String tanni = getTanni();
        result = prime * result + ((tanni == null) ? 0 : tanni.hashCode());
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
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof NumberBox)) {
            return false;
        }
        NumberBox other = (NumberBox) obj;
        if (!Objects.equals(getTanni(), other.getTanni())) {
            return false;
        }
        return true;
    }
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.number;
    }
    @Override
    public void merge(AbstractFormModel obj) {
        if (this == obj) {
            return;
        }
        if (!(obj instanceof NumberBox)) {
            return;
        }
        super.merge(obj);
        NumberBox other = (NumberBox) obj;
        if (StringUtil.isNullZeroString(getTanni())) {
            setTanni(other.getTanni());
        }

    }
    @Override
    public void merge(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return;
        }
        setValue(values[0]);
    }
    /**
    *
    * <br>[機  能] 表示用にフォーマットされた値を返します
    * <br>[解  説] 3桁カンマ区切りされて返します
    * <br>[備  考]
    * @return カンマフォーマットされた値
    */
    public String dspValueHTML() {
       String value = getValue();
       if (StringUtil.isNullZeroString(value)) {
           return "";
       }
       try {
           BigDecimal bd = new BigDecimal(value);
           NumberFormat nf =  NumberFormat.getNumberInstance();
           nf.setMaximumFractionDigits(bd.scale());
           return nf.format(bd);
       } catch (NumberFormatException e__) {
           return "";
       }
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCommon.validateNumberDecimal(errors, getValue(),
                info.outputName(gsMsg),
                Integer.parseInt(getMaxlength()),
                info.chkRequire());
    }
    @Override
    public String getCalced() {
        return getValue();
    }


}
