package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import java.io.Serializable;

/**
 * <p>CMN_USR_LANG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrLangModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** CUL_COUNTRY mapping */
    private String culCountry__;
    /** CUL_AUID mapping */
    private int culAuid__;
    /** CUL_ADATE mapping */
    private UDate culAdate__;
    /** CUL_EUID mapping */
    private int culEuid__;
    /** CUL_EDATE mapping */
    private UDate culEdate__;
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>culAuid を取得します。
     * @return culAuid
     */
    public int getCulAuid() {
        return culAuid__;
    }
    /**
     * <p>culAuid をセットします。
     * @param culAuid culAuid
     */
    public void setCulAuid(int culAuid) {
        culAuid__ = culAuid;
    }
    /**
     * <p>culAdate を取得します。
     * @return culAdate
     */
    public UDate getCulAdate() {
        return culAdate__;
    }
    /**
     * <p>culAdate をセットします。
     * @param culAdate culAdate
     */
    public void setCulAdate(UDate culAdate) {
        culAdate__ = culAdate;
    }
    /**
     * <p>culEuid を取得します。
     * @return culEuid
     */
    public int getCulEuid() {
        return culEuid__;
    }
    /**
     * <p>culEuid をセットします。
     * @param culEuid culEuid
     */
    public void setCulEuid(int culEuid) {
        culEuid__ = culEuid;
    }
    /**
     * <p>culEdate を取得します。
     * @return culEdate
     */
    public UDate getCulEdate() {
        return culEdate__;
    }
    /**
     * <p>culEdate をセットします。
     * @param culEdate culEdate
     */
    public void setCulEdate(UDate culEdate) {
        culEdate__ = culEdate;
    }
    /**
     * <p>culCountry を取得します。
     * @return culCountry
     */
    public String getCulCountry() {
        return culCountry__;
    }
    /**
     * <p>culCountry をセットします。
     * @param culCountry culCountry
     */
    public void setCulCountry(String culCountry) {
        culCountry__ = culCountry;
    }



}
