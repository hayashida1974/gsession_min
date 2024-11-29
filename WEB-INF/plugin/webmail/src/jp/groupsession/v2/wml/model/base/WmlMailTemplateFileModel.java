package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_MAIL_TEMPLATE_FILE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailTemplateFileModel implements Serializable {

    /** WTP_SID mapping */
    private int wtpSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public WmlMailTemplateFileModel() {
    }

    /**
     * <p>get WTP_SID value
     * @return WTP_SID value
     */
    public int getWtpSid() {
        return wtpSid__;
    }

    /**
     * <p>set WTP_SID value
     * @param wtpSid WTP_SID value
     */
    public void setWtpSid(int wtpSid) {
        wtpSid__ = wtpSid;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wtpSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
