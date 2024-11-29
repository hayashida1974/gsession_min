package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_FILTER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterModel implements Serializable {

    /** WFT_SID mapping */
    private int wftSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** WFT_NAME mapping */
    private String wftName__;
    /** WFT_TYPE mapping */
    private int wftType__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WFT_TEMPFILE mapping */
    private int wftTempfile__;
    /** WFT_ACTION_LABEL mapping */
    private int wftActionLabel__;
    /** WLB_SID mapping */
    private int wlbSid__;
    /** WFT_ACTION_READ mapping */
    private int wftActionRead__;
    /** WFT_ACTION_FORWARD mapping */
    private int wftActionForward__;
    /** WFT_ACTION_FWADDRESS mapping */
    private String wftActionFwaddress__;
    /** WFT_CONDITION mapping */
    private int wftCondition__;
    /** WFT_ACTION_FOLDER mapping */
    private int wftActionFolder__;
    /**
     * <p>Default Constructor
     */
    public WmlFilterModel() {
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
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get WFT_NAME value
     * @return WFT_NAME value
     */
    public String getWftName() {
        return wftName__;
    }

    /**
     * <p>set WFT_NAME value
     * @param wftName WFT_NAME value
     */
    public void setWftName(String wftName) {
        wftName__ = wftName;
    }

    /**
     * <p>get WFT_TYPE value
     * @return WFT_TYPE value
     */
    public int getWftType() {
        return wftType__;
    }

    /**
     * <p>set WFT_TYPE value
     * @param wftType WFT_TYPE value
     */
    public void setWftType(int wftType) {
        wftType__ = wftType;
    }

    /**
     * <p>get WAC_SID value
     * @return WAC_SID value
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>set WAC_SID value
     * @param wacSid WAC_SID value
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>get WFT_TEMPFILE value
     * @return WFT_TEMPFILE value
     */
    public int getWftTempfile() {
        return wftTempfile__;
    }

    /**
     * <p>set WFT_TEMPFILE value
     * @param wftTempfile WFT_TEMPFILE value
     */
    public void setWftTempfile(int wftTempfile) {
        wftTempfile__ = wftTempfile;
    }

    /**
     * <p>get WFT_ACTION_LABEL value
     * @return WFT_ACTION_LABEL value
     */
    public int getWftActionLabel() {
        return wftActionLabel__;
    }

    /**
     * <p>set WFT_ACTION_LABEL value
     * @param wftActionLabel WFT_ACTION_LABEL value
     */
    public void setWftActionLabel(int wftActionLabel) {
        wftActionLabel__ = wftActionLabel;
    }

    /**
     * <p>get WLB_SID value
     * @return WLB_SID value
     */
    public int getWlbSid() {
        return wlbSid__;
    }

    /**
     * <p>set WLB_SID value
     * @param wlbSid WLB_SID value
     */
    public void setWlbSid(int wlbSid) {
        wlbSid__ = wlbSid;
    }

    /**
     * <p>get WFT_ACTION_READ value
     * @return WFT_ACTION_READ value
     */
    public int getWftActionRead() {
        return wftActionRead__;
    }

    /**
     * <p>set WFT_ACTION_READ value
     * @param wftActionRead WFT_ACTION_READ value
     */
    public void setWftActionRead(int wftActionRead) {
        wftActionRead__ = wftActionRead;
    }

    /**
     * <p>get WFT_ACTION_FORWARD value
     * @return WFT_ACTION_FORWARD value
     */
    public int getWftActionForward() {
        return wftActionForward__;
    }

    /**
     * <p>set WFT_ACTION_FORWARD value
     * @param wftActionForward WFT_ACTION_FORWARD value
     */
    public void setWftActionForward(int wftActionForward) {
        wftActionForward__ = wftActionForward;
    }

    /**
     * <p>get WFT_ACTION_FWADDRESS value
     * @return WFT_ACTION_FWADDRESS value
     */
    public String getWftActionFwaddress() {
        return wftActionFwaddress__;
    }

    /**
     * <p>set WFT_ACTION_FWADDRESS value
     * @param wftActionFwaddress WFT_ACTION_FWADDRESS value
     */
    public void setWftActionFwaddress(String wftActionFwaddress) {
        wftActionFwaddress__ = wftActionFwaddress;
    }

    /**
     * <p>get WFT_CONDITION value
     * @return WFT_CONDITION value
     */
    public int getWftCondition() {
        return wftCondition__;
    }

    /**
     * <p>set WFT_CONDITION value
     * @param wftCondition WFT_CONDITION value
     */
    public void setWftCondition(int wftCondition) {
        wftCondition__ = wftCondition;
    }
    /**
     * <p>wftActionFolder を取得します。
     * @return wftActionFolder
     * @see jp.groupsession.v2.wml.model.base.WmlFilterModel#wftActionFolder__
     */
    public int getWftActionFolder() {
        return wftActionFolder__;
    }

    /**
     * <p>wftActionFolder をセットします。
     * @param wftActionFolder wftActionFolder
     * @see jp.groupsession.v2.wml.model.base.WmlFilterModel#wftActionFolder__
     */
    public void setWftActionFolder(int wftActionFolder) {
        wftActionFolder__ = wftActionFolder;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wftSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(wftName__);
        buf.append(",");
        buf.append(wftType__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wftTempfile__);
        buf.append(",");
        buf.append(wftActionLabel__);
        buf.append(",");
        buf.append(wlbSid__);
        buf.append(",");
        buf.append(wftActionRead__);
        buf.append(",");
        buf.append(wftActionForward__);
        buf.append(",");
        buf.append(NullDefault.getString(wftActionFwaddress__, ""));
        buf.append(",");
        buf.append(wftCondition__);
        buf.append(",");
        buf.append(wftActionFolder__);
        return buf.toString();
    }

}
