package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.ArrayUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
/**
 *
 * <br>[機  能] テキスト入力 共通親クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class TextInput extends AbstractFormModel {

    /** 初期値 */
    private String defaultValue__;
    /** 文字数制限 */
    private String maxlength__ = "100";
    /** 入力値*/
    private String value__;
    /**
     * デフォルトコンストラクタ
     */
    public TextInput() {
        super();
    }


    /**
     * @param json json
     */
    public TextInput(JSONObject json) {
        super(json);
    }


    /**
     * <p>defaultValue を取得します。
     * @return defaultValue
     */
    public String getDefaultValue() {
        return defaultValue__;
    }

    /**
     * <p>defaultValue をセットします。
     * @param defaultValue defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        defaultValue__ = defaultValue;
    }

    /**
     * <p>maxlength を取得します。
     * @return maxlength
     */
    public String getMaxlength() {
        return maxlength__;
    }

    /**
     * <p>maxlength をセットします。
     * @param maxlength maxlength
     */
    public void setMaxlength(String maxlength) {
        maxlength__ = maxlength;
    }
    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".value", getValue());
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
        try {
            setValue(jsonObj.getString("value"));
        } catch (JSONException e) {

        }
        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
            return;
        }
        /** 初期値 */
        try {
            setDefaultValue(jsonObj.getString("defaultValue"));
        } catch (JSONException e) {

        }

        /** 文字数制限 */
        try {
            setMaxlength(jsonObj.getString("maxlength"));
        } catch (JSONException e) {

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
                + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
        result = prime * result
                + ((getMaxlength() == null) ? 0 : getMaxlength().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
        if (!(obj instanceof TextInput)) {
            return false;
        }
        TextInput other = (TextInput) obj;
        if (getDefaultValue() == null) {
            if (other.getDefaultValue() != null) {
                return false;
            }
        } else if (!getDefaultValue().equals(other.getDefaultValue())) {
            return false;
        }
        if (getMaxlength() == null) {
            if (other.getMaxlength() != null) {
                return false;
            }
        } else if (!getMaxlength().equals(other.getMaxlength())) {
            return false;
        }
        if (getValue() == null) {
            if (other.getValue() != null) {
                return false;
            }
        } else if (!getValue().equals(other.getValue())) {
            return false;
        }
        return true;
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {
        super.dspInit(reqMdl, con);
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
        if (!(obj instanceof TextInput)) {
            return;
        }
        TextInput other = (TextInput) obj;
        if (StringUtil.isNullZeroString(getDefaultValue())) {
            setDefaultValue(other.getDefaultValue());
        }
        if (StringUtil.isNullZeroString(getValue())) {
            setValue(other.getValue());
        }
        if (StringUtil.isNullZeroString(getMaxlength())) {
            setMaxlength(other.getMaxlength());
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
        setValue(getDefaultValue());
    }
    @Override
    public String getCalced() {
        return getValue();
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
    * <br>[機  能] value を画面表示用 HTMLとして出力する
    * <br>[解  説]
    * <br>[備  考]
    * @return 表示文字列
    */
    public String dspValueHTML() {
       return StringUtilHtml.transToHTml(
               NullDefault.getString(getValue(), ""));
    }

}