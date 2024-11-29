package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_OAUTH_TOKEN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOauthTokenModel implements Serializable {

    /** COT_SID mapping */
    private int cotSid__;
    /** COU_SID mapping */
    private int couSid__;
    /** COT_ACCOUNTID mapping */
    private String cotAccountid__;
    /** COT_AUTH_RTOKEN mapping */
    private String cotAuthRtoken__;
    /** COT_AUTH_ATOKEN mapping */
    private String cotAuthAtoken__;
    /** COT_AUTH_ATOKEN_DATE mapping */
    private UDate cotAuthAtokenDate__;

    /**
     * <p>Default Constructor
     */
    public CmnOauthTokenModel() {
    }

    /**
     * <p>get COT_SID value
     * @return COT_SID value
     */
    public int getCotSid() {
        return cotSid__;
    }

    /**
     * <p>set COT_SID value
     * @param cotSid COT_SID value
     */
    public void setCotSid(int cotSid) {
        cotSid__ = cotSid;
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
     * <p>get COT_ACCOUNTID value
     * @return COT_ACCOUNTID value
     */
    public String getCotAccountid() {
        return cotAccountid__;
    }

    /**
     * <p>set COT_ACCOUNTID value
     * @param cotAccountid COT_ACCOUNTID value
     */
    public void setCotAccountid(String cotAccountid) {
        cotAccountid__ = cotAccountid;
    }

    /**
     * <p>get COT_AUTH_RTOKEN value
     * @return COT_AUTH_RTOKEN value
     */
    public String getCotAuthRtoken() {
        return cotAuthRtoken__;
    }

    /**
     * <p>set COT_AUTH_RTOKEN value
     * @param cotAuthRtoken COT_AUTH_RTOKEN value
     */
    public void setCotAuthRtoken(String cotAuthRtoken) {
        cotAuthRtoken__ = cotAuthRtoken;
    }

    /**
     * <p>get COT_AUTH_ATOKEN value
     * @return COT_AUTH_ATOKEN value
     */
    public String getCotAuthAtoken() {
        return cotAuthAtoken__;
    }

    /**
     * <p>set COT_AUTH_ATOKEN value
     * @param cotAuthAtoken COT_AUTH_ATOKEN value
     */
    public void setCotAuthAtoken(String cotAuthAtoken) {
        cotAuthAtoken__ = cotAuthAtoken;
    }

    /**
     * <p>get COT_AUTH_ATOKEN_DATE value
     * @return COT_AUTH_ATOKEN_DATE value
     */
    public UDate getCotAuthAtokenDate() {
        return cotAuthAtokenDate__;
    }

    /**
     * <p>set COT_AUTH_ATOKEN_DATE value
     * @param cotAuthAtokenDate COT_AUTH_ATOKEN_DATE value
     */
    public void setCotAuthAtokenDate(UDate cotAuthAtokenDate) {
        cotAuthAtokenDate__ = cotAuthAtokenDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cotSid__);
        buf.append(",");
        buf.append(couSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cotAccountid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cotAuthRtoken__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cotAuthAtoken__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cotAuthAtokenDate__, ""));
        return buf.toString();
    }

}
