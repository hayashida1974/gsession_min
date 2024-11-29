package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

public class TimeBox extends AbstractFormModel implements JSONString {
    /** 入力値*/
    private String value__;
    /** 初期値 */
    private String defaultValue__;
    /** 時間ラベル*/
    private final String[] hours__ =
            new String[] {
                "00",
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23"
                };

    /** 分ラベル*/
    private final String[] minutes__ =
            new String[] {
                "00",
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31",
                "32",
                "33",
                "34",
                "35",
                "36",
                "37",
                "38",
                "39",
                "40",
                "41",
                "42",
                "43",
                "44",
                "45",
                "46",
                "47",
                "48",
                "49",
                "50",
                "51",
                "52",
                "53",
                "54",
                "55",
                "56",
                "57",
                "58",
                "59"
                };
    /**モバイル用 時間*/
    private String hourMbh__ = null;
    /**モバイル用 分*/
    private String minuteMbh__ = null;

    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        if (value__ == null
                && hourMbh__ != null
                && minuteMbh__ != null) {
            return String.format(
                    "%s:%s",
                    hourMbh__,
                    minuteMbh__);
        }
        return value__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
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
        /** 初期値 */
        try {
            setDefaultValue(jsonObj.getString("defaultValue"));
        } catch (JSONException e) {

        }

        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
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
                + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
        result = prime * result + ((value__ == null) ? 0 : value__.hashCode());
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
        if (!(obj instanceof TimeBox)) {
            return false;
        }
        TimeBox other = (TimeBox) obj;
        if (getDefaultValue() == null) {
            if (other.getDefaultValue() != null) {
                return false;
            }
        } else if (!getDefaultValue().equals(other.getDefaultValue())) {
            return false;
        }
        if (value__ == null) {
            if (other.value__ != null) {
                return false;
            }
        } else if (!value__.equals(other.value__)) {
            return false;
        }
        return true;
    }

    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.time;
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
        if (!(obj instanceof TimeBox)) {
            return;
        }
        TimeBox other = (TimeBox) obj;
        if (StringUtil.isNullZeroString(getDefaultValue())) {
            setDefaultValue(other.getDefaultValue());
        }
        if (StringUtil.isNullZeroString(getValue())) {
            setValue(other.getValue());
        }
    }
    @Override
    public void defaultInit() {
        setValue(getDefaultValue());

        String[] times = value__.split(":");
        if (times.length == 2) {
            hourMbh__ = times[0];
            minuteMbh__ = times[1];
        }
    }
    @Override
    public void merge(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return;
        }
        setValue(values[0]);
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCommon.validateTimeFieldText(errors,
                getValue(),
                info.outputCode(),
                info.outputName(gsMsg),
                info.chkRequire());
    }

    /**
     * <p>hours を取得します。
     * @return hours
     * @see jp.groupsession.v2.cmn.formmodel.TimeBox#hours__
     */
    public String[] getHours() {
        return hours__;
    }

    /**
     * <p>minutes を取得します。
     * @return minutes
     * @see jp.groupsession.v2.cmn.formmodel.TimeBox#minutes__
     */
    public String[] getMinutes() {
        return minutes__;
    }

    /**
     * <p>hourMbh を取得します。
     * @return hourMbh
     * @see jp.groupsession.v2.cmn.formmodel.TimeBox#hourMbh__
     */
    public String getHourMbh() {
        return hourMbh__;
    }

    /**
     * <p>hourMbh をセットします。
     * @param hourMbh hourMbh
     * @see jp.groupsession.v2.cmn.formmodel.TimeBox#hourMbh__
     */
    public void setHourMbh(String hourMbh) {
        hourMbh__ = hourMbh;
    }

    /**
     * <p>minuteMbh を取得します。
     * @return minuteMbh
     * @see jp.groupsession.v2.cmn.formmodel.TimeBox#minuteMbh__
     */
    public String getMinuteMbh() {
        return minuteMbh__;
    }

    /**
     * <p>minuteMbh をセットします。
     * @param minuteMbh minuteMbh
     * @see jp.groupsession.v2.cmn.formmodel.TimeBox#minuteMbh__
     */
    public void setMinuteMbh(String minuteMbh) {
        minuteMbh__ = minuteMbh;
    }

    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("defaultValue", getDefaultValue());
        obj.put("value", getValue());
        return obj.toString();
    }

}
