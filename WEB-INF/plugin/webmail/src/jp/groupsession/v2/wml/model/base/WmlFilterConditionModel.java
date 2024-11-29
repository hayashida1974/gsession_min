package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_FILTER_CONDITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterConditionModel implements Serializable {

    /** WFT_SID mapping */
    private int wftSid__;
    /** WFC_NUM mapping */
    private int wfcNum__;
    /** WFC_TYPE mapping */
    private int wfcType__;
    /** WFC_EXPRESSION mapping */
    private int wfcExpression__;
    /** WFC_TEXT mapping */
    private String wfcText__;

    /**
     * <p>Default Constructor
     */
    public WmlFilterConditionModel() {
    }

    /**
     * <p>get WFT_SID value
     * @return WFT_SID value
     */
    public int getWftSid() {
        return wftSid__;
    }

    /**
     * <p>set WFT_SID value
     * @param wftSid WFT_SID value
     */
    public void setWftSid(int wftSid) {
        wftSid__ = wftSid;
    }

    /**
     * <p>get WFC_NUM value
     * @return WFC_NUM value
     */
    public int getWfcNum() {
        return wfcNum__;
    }

    /**
     * <p>set WFC_NUM value
     * @param wfcNum WFC_NUM value
     */
    public void setWfcNum(int wfcNum) {
        wfcNum__ = wfcNum;
    }

    /**
     * <p>get WFC_TYPE value
     * @return WFC_TYPE value
     */
    public int getWfcType() {
        return wfcType__;
    }

    /**
     * <p>set WFC_TYPE value
     * @param wfcType WFC_TYPE value
     */
    public void setWfcType(int wfcType) {
        wfcType__ = wfcType;
    }

    /**
     * <p>get WFC_EXPRESSION value
     * @return WFC_EXPRESSION value
     */
    public int getWfcExpression() {
        return wfcExpression__;
    }

    /**
     * <p>set WFC_EXPRESSION value
     * @param wfcExpression WFC_EXPRESSION value
     */
    public void setWfcExpression(int wfcExpression) {
        wfcExpression__ = wfcExpression;
    }

    /**
     * <p>get WFC_TEXT value
     * @return WFC_TEXT value
     */
    public String getWfcText() {
        return wfcText__;
    }

    /**
     * <p>set WFC_TEXT value
     * @param wfcText WFC_TEXT value
     */
    public void setWfcText(String wfcText) {
        wfcText__ = wfcText;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wftSid__);
        buf.append(",");
        buf.append(wfcNum__);
        buf.append(",");
        buf.append(wfcType__);
        buf.append(",");
        buf.append(wfcExpression__);
        buf.append(",");
        buf.append(NullDefault.getString(wfcText__, ""));
        return buf.toString();
    }

}
