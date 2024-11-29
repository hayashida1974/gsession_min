package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_FILTER_CONDITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlFilterConditionModel implements Serializable {

    /** SFT_SID mapping */
    private int sftSid__;
    /** SFC_NUM mapping */
    private int sfcNum__;
    /** SFC_TYPE mapping */
    private int sfcType__;
    /** SFC_EXPRESSION mapping */
    private int sfcExpression__;
    /** SFC_TEXT mapping */
    private String sfcText__;
    /**
     * <p>sftSid を取得します。
     * @return sftSid
     */
    public int getSftSid() {
        return sftSid__;
    }
    /**
     * <p>sftSid をセットします。
     * @param sftSid sftSid
     */
    public void setSftSid(int sftSid) {
        sftSid__ = sftSid;
    }
    /**
     * <p>sfcNum を取得します。
     * @return sfcNum
     */
    public int getSfcNum() {
        return sfcNum__;
    }
    /**
     * <p>sfcNum をセットします。
     * @param sfcNum sfcNum
     */
    public void setSfcNum(int sfcNum) {
        sfcNum__ = sfcNum;
    }
    /**
     * <p>sfcType を取得します。
     * @return sfcType
     */
    public int getSfcType() {
        return sfcType__;
    }
    /**
     * <p>sfcType をセットします。
     * @param sfcType sfcType
     */
    public void setSfcType(int sfcType) {
        sfcType__ = sfcType;
    }
    /**
     * <p>sfcExpression を取得します。
     * @return sfcExpression
     */
    public int getSfcExpression() {
        return sfcExpression__;
    }
    /**
     * <p>sfcExpression をセットします。
     * @param sfcExpression sfcExpression
     */
    public void setSfcExpression(int sfcExpression) {
        sfcExpression__ = sfcExpression;
    }
    /**
     * <p>sfcText を取得します。
     * @return sfcText
     */
    public String getSfcText() {
        return sfcText__;
    }
    /**
     * <p>sfcText をセットします。
     * @param sfcText sfcText
     */
    public void setSfcText(String sfcText) {
        sfcText__ = sfcText;
    }


}
