package jp.groupsession.v2.rng.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>RNG_CHANNEL_TEMPLATE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngChannelTemplateModel implements Serializable {

    /** RCT_SID mapping */
    private int rctSid__;
    /** RCT_VER mapping */
    private int rctVer__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RCT_NAME mapping */
    private String rctName__;
    /** RCT_AUID mapping */
    private int rctAuid__;
    /** RCT_ADATE mapping */
    private UDate rctAdate__;
    /** RCT_EUID mapping */
    private int rctEuid__;
    /** RCT_EDATE mapping */
    private UDate rctEdate__;
    /** RCT_JKBN mapping  */
    private int rctJkbn__;

    /**
     * <p>Default Constructor
     */
    public RngChannelTemplateModel() {
    }

    /**
     * <p>get RCT_SID value
     * @return RCT_SID value
     */
    public int getRctSid() {
        return rctSid__;
    }

    /**
     * <p>set RCT_SID value
     * @param rctSid RCT_SID value
     */
    public void setRctSid(int rctSid) {
        rctSid__ = rctSid;
    }

    /**
     * <p>get RCT_VER value
     * @return RCT_VER value
     */
    public int getRctVer() {
        return rctVer__;
    }

    /**
     * <p>set RCT_VER value
     * @param rctVer RCT_VER value
     */
    public void setRctVer(int rctVer) {
        rctVer__ = rctVer;
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
     * <p>get RCT_NAME value
     * @return RCT_NAME value
     */
    public String getRctName() {
        return rctName__;
    }

    /**
     * <p>set RCT_NAME value
     * @param rctName RCT_NAME value
     */
    public void setRctName(String rctName) {
        rctName__ = rctName;
    }

    /**
     * <p>get RCT_AUID value
     * @return RCT_AUID value
     */
    public int getRctAuid() {
        return rctAuid__;
    }

    /**
     * <p>set RCT_AUID value
     * @param rctAuid RCT_AUID value
     */
    public void setRctAuid(int rctAuid) {
        rctAuid__ = rctAuid;
    }

    /**
     * <p>get RCT_ADATE value
     * @return RCT_ADATE value
     */
    public UDate getRctAdate() {
        return rctAdate__;
    }

    /**
     * <p>set RCT_ADATE value
     * @param rctAdate RCT_ADATE value
     */
    public void setRctAdate(UDate rctAdate) {
        rctAdate__ = rctAdate;
    }

    /**
     * <p>get RCT_EUID value
     * @return RCT_EUID value
     */
    public int getRctEuid() {
        return rctEuid__;
    }

    /**
     * <p>set RCT_EUID value
     * @param rctEuid RCT_EUID value
     */
    public void setRctEuid(int rctEuid) {
        rctEuid__ = rctEuid;
    }

    /**
     * <p>get RCT_EDATE value
     * @return RCT_EDATE value
     */
    public UDate getRctEdate() {
        return rctEdate__;
    }

    /**
     * <p>set RCT_EDATE value
     * @param rctEdate RCT_EDATE value
     */
    public void setRctEdate(UDate rctEdate) {
        rctEdate__ = rctEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rctSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(rctName__, ""));
        buf.append(",");
        buf.append(rctAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rctAdate__, ""));
        buf.append(",");
        buf.append(rctEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rctEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>rctJkbn を取得します。
     * @return rctJkbn
     */
    public int getRctJkbn() {
        return rctJkbn__;
    }

    /**
     * <p>rctJkbn をセットします。
     * @param rctJkbn rctJkbn
     */
    public void setRctJkbn(int rctJkbn) {
        rctJkbn__ = rctJkbn;
    }

}
