package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_OAUTH Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOauthModel implements Serializable {

    /** COU_SID mapping */
    private int couSid__;
    /** COU_PROVIDER mapping */
    private int couProvider__;
    /** COU_AUTH_ID mapping */
    private String couAuthId__;
    /** COU_AUTH_SECRET mapping */
    private String couAuthSecret__;
    /** COU_BIKO mapping */
    private String couBiko__;
    /** COU_AUID mapping */
    private int couAuid__;
    /** COU_ADATE mapping */
    private UDate couAdate__;
    /** COU_EUID mapping */
    private int couEuid__;
    /** COU_EDATE mapping */
    private UDate couEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnOauthModel() {
    }

    /**
     * <p>get COU_SID value
     * @return COU_SID value
     */
    public int getCouSid() {
        return couSid__;
    }

    /**
     * <p>set COU_SID value
     * @param couSid COU_SID value
     */
    public void setCouSid(int couSid) {
        couSid__ = couSid;
    }

    /**
     * <p>get COU_PROVIDER value
     * @return COU_PROVIDER value
     */
    public int getCouProvider() {
        return couProvider__;
    }

    /**
     * <p>set COU_PROVIDER value
     * @param couProvider COU_PROVIDER value
     */
    public void setCouProvider(int couProvider) {
        couProvider__ = couProvider;
    }

    /**
     * <p>get COU_AUTH_ID value
     * @return COU_AUTH_ID value
     */
    public String getCouAuthId() {
        return couAuthId__;
    }

    /**
     * <p>set COU_AUTH_ID value
     * @param couAuthId COU_AUTH_ID value
     */
    public void setCouAuthId(String couAuthId) {
        couAuthId__ = couAuthId;
    }

    /**
     * <p>get COU_AUTH_SECRET value
     * @return COU_AUTH_SECRET value
     */
    public String getCouAuthSecret() {
        return couAuthSecret__;
    }

    /**
     * <p>set COU_AUTH_SECRET value
     * @param couAuthSecret COU_AUTH_SECRET value
     */
    public void setCouAuthSecret(String couAuthSecret) {
        couAuthSecret__ = couAuthSecret;
    }

    /**
     * <p>get COU_BIKO value
     * @return COU_BIKO value
     */
    public String getCouBiko() {
        return couBiko__;
    }

    /**
     * <p>set COU_BIKO value
     * @param couBiko COU_BIKO value
     */
    public void setCouBiko(String couBiko) {
        couBiko__ = couBiko;
    }

    /**
     * <p>get COU_AUID value
     * @return COU_AUID value
     */
    public int getCouAuid() {
        return couAuid__;
    }

    /**
     * <p>set COU_AUID value
     * @param couAuid COU_AUID value
     */
    public void setCouAuid(int couAuid) {
        couAuid__ = couAuid;
    }

    /**
     * <p>get COU_ADATE value
     * @return COU_ADATE value
     */
    public UDate getCouAdate() {
        return couAdate__;
    }

    /**
     * <p>set COU_ADATE value
     * @param couAdate COU_ADATE value
     */
    public void setCouAdate(UDate couAdate) {
        couAdate__ = couAdate;
    }

    /**
     * <p>get COU_EUID value
     * @return COU_EUID value
     */
    public int getCouEuid() {
        return couEuid__;
    }

    /**
     * <p>set COU_EUID value
     * @param couEuid COU_EUID value
     */
    public void setCouEuid(int couEuid) {
        couEuid__ = couEuid;
    }

    /**
     * <p>get COU_EDATE value
     * @return COU_EDATE value
     */
    public UDate getCouEdate() {
        return couEdate__;
    }

    /**
     * <p>set COU_EDATE value
     * @param couEdate COU_EDATE value
     */
    public void setCouEdate(UDate couEdate) {
        couEdate__ = couEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(couSid__);
        buf.append(",");
        buf.append(couProvider__);
        buf.append(",");
        buf.append(NullDefault.getString(couAuthId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(couAuthSecret__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(couBiko__, ""));
        buf.append(",");
        buf.append(couAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(couAdate__, ""));
        buf.append(",");
        buf.append(couEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(couEdate__, ""));
        return buf.toString();
    }

}
