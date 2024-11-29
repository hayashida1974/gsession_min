package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_MANAGE_NOTICE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlManageNoticeModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WMN_RCVFAILED_FLG mapping */
    private int wmnRcvfailedFlg__;

    /**
     * <p>Default Constructor
     */
    public WmlManageNoticeModel() {
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
     * <p>get WMN_RCVFAILED_FLG value
     * @return WMN_RCVFAILED_FLG value
     */
    public int getWmnRcvfailedFlg() {
        return wmnRcvfailedFlg__;
    }

    /**
     * <p>set WMN_RCVFAILED_FLG value
     * @param wmnRcvfailedFlg WMN_RCVFAILED_FLG value
     */
    public void setWmnRcvfailedFlg(int wmnRcvfailedFlg) {
        wmnRcvfailedFlg__ = wmnRcvfailedFlg;
    }

}
