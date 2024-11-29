package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_USR_PLUGIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrPluginModel implements Serializable {

    /** CUP_PID mapping */
    private String cupPid__;
    /** CUP_NAME mapping */
    private String cupName__;
    /** CUP_URL mapping */
    private String cupUrl__;
    /** CUP_VIEW mapping */
    private int cupView__;
    /** CUP_TARGET mapping */
    private int cupTarget__;
    /** BIN_SID mapping */
    private long binSid__;
    /** CUP_PARAM_KBN mapping */
    private int cupParamKbn__;
    /** CUP_SEND_KBN mapping */
    private int cupSendKbn__;

    /**
     * <p>Default Constructor
     */
    public CmnUsrPluginModel() {
    }

    /**
     * <p>get CUP_PID value
     * @return CUP_PID value
     */
    public String getCupPid() {
        return cupPid__;
    }

    /**
     * <p>set CUP_PID value
     * @param cupPid CUP_PID value
     */
    public void setCupPid(String cupPid) {
        cupPid__ = cupPid;
    }

    /**
     * <p>get CUP_NAME value
     * @return CUP_NAME value
     */
    public String getCupName() {
        return cupName__;
    }

    /**
     * <p>set CUP_NAME value
     * @param cupName CUP_NAME value
     */
    public void setCupName(String cupName) {
        cupName__ = cupName;
    }

    /**
     * <p>get CUP_VIEW value
     * @return CUP_VIEW value
     */
    public int getCupView() {
        return cupView__;
    }

    /**
     * <p>set CUP_VIEW value
     * @param cupView CUP_VIEW value
     */
    public void setCupView(int cupView) {
        cupView__ = cupView;
    }

    /**
     * <p>get CUP_TARGET value
     * @return CUP_TARGET value
     */
    public int getCupTarget() {
        return cupTarget__;
    }

    /**
     * <p>set CUP_TARGET value
     * @param cupTarget CUP_TARGET value
     */
    public void setCupTarget(int cupTarget) {
        cupTarget__ = cupTarget;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>cupUrl を取得します。
     * @return cupUrl
     */
    public String getCupUrl() {
        return cupUrl__;
    }

    /**
     * <p>cupUrl をセットします。
     * @param cupUrl cupUrl
     */
    public void setCupUrl(String cupUrl) {
        cupUrl__ = cupUrl;
    }
    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cupPid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cupName__, ""));
        buf.append(",");
        buf.append(cupView__);
        buf.append(",");
        buf.append(cupTarget__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

    /**
     * <p>cupParamKbn を取得します。
     * @return cupParamKbn
     */
    public int getCupParamKbn() {
        return cupParamKbn__;
    }

    /**
     * <p>cupParamKbn をセットします。
     * @param cupParamKbn cupParamKbn
     */
    public void setCupParamKbn(int cupParamKbn) {
        cupParamKbn__ = cupParamKbn;
    }

    /**
     * <p>cupSendKbn を取得します。
     * @return cupSendKbn
     */
    public int getCupSendKbn() {
        return cupSendKbn__;
    }

    /**
     * <p>cupSendKbn をセットします。
     * @param cupSendKbn cupSendKbn
     */
    public void setCupSendKbn(int cupSendKbn) {
        cupSendKbn__ = cupSendKbn;
    }
}
