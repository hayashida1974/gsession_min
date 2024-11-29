package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_MAIL_SENDPLAN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailSendplanModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WSP_SENDKBN mapping */
    private int wspSendkbn__;
    /** WSP_SENDDATE mapping */
    private UDate wspSenddate__;
    /** WSP_MAILTYPE mapping */
    private int wspMailtype__;
    /** WSP_COMPRESS_FILE mapping */
    private int wspCompressFile__;

    /**
     * <p>Default Constructor
     */
    public WmlMailSendplanModel() {
    }

    /**
     * <p>get WMD_MAILNUM value
     * @return WMD_MAILNUM value
     */
    public long getWmdMailnum() {
        return wmdMailnum__;
    }

    /**
     * <p>set WMD_MAILNUM value
     * @param wmdMailnum WMD_MAILNUM value
     */
    public void setWmdMailnum(long wmdMailnum) {
        wmdMailnum__ = wmdMailnum;
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
     * <p>get WSP_SENDKBN value
     * @return WSP_SENDKBN value
     */
    public int getWspSendkbn() {
        return wspSendkbn__;
    }

    /**
     * <p>set WSP_SENDKBN value
     * @param wspSendkbn WSP_SENDKBN value
     */
    public void setWspSendkbn(int wspSendkbn) {
        wspSendkbn__ = wspSendkbn;
    }

    /**
     * <p>get WSP_SENDDATE value
     * @return WSP_SENDDATE value
     */
    public UDate getWspSenddate() {
        return wspSenddate__;
    }

    /**
     * <p>set WSP_SENDDATE value
     * @param wspSenddate WSP_SENDDATE value
     */
    public void setWspSenddate(UDate wspSenddate) {
        wspSenddate__ = wspSenddate;
    }

    /**
     * <p>get WSP_MAILTYPE value
     * @return WSP_MAILTYPE value
     */
    public int getWspMailtype() {
        return wspMailtype__;
    }

    /**
     * <p>set WSP_MAILTYPE value
     * @param wspMailtype WSP_MAILTYPE value
     */
    public void setWspMailtype(int wspMailtype) {
        wspMailtype__ = wspMailtype;
    }

    /**
     * <p>get WSP_COMPRESS_FILE value
     * @return WSP_COMPRESS_FILE value
     */
    public int getWspCompressFile() {
        return wspCompressFile__;
    }

    /**
     * <p>set WSP_COMPRESS_FILE value
     * @param wspCompressFile WSP_COMPRESS_FILE value
     */
    public void setWspCompressFile(int wspCompressFile) {
        wspCompressFile__ = wspCompressFile;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wspSendkbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wspSenddate__, ""));
        buf.append(",");
        buf.append(wspMailtype__);
        buf.append(",");
        buf.append(wspCompressFile__);
        return buf.toString();
    }

}
