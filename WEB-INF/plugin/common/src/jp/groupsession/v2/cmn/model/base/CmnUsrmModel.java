package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_USRM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrmModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** USR_LGID mapping */
    private String usrLgid__;
    /** USR_PSWD mapping */
    private String usrPswd__;
    /** USR_JKBN mapping */
    private int usrJkbn__;
    /** USR_AUID mapping */
    private int usrAuid__;
    /** USR_ADATE mapping */
    private UDate usrAdate__;
    /** USR_EUID mapping */
    private int usrEuid__;
    /** USR_EDATE mapping */
    private UDate usrEdate__;
    /** USR_PSWD_KBN mapping */
    private int usrPswdKbn__;
    /** USR_PSWD_EDATE mapping */
    private UDate usrPswdEdate__;
    /** USR_UKO_FLG mapping */
    private int usrUkoFlg__;

    /**
     * <p>Default Constructor
     */
    public CmnUsrmModel() {
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
     * <p>get USR_LGID value
     * @return USR_LGID value
     */
    public String getUsrLgid() {
        return usrLgid__;
    }

    /**
     * <p>set USR_LGID value
     * @param usrLgid USR_LGID value
     */
    public void setUsrLgid(String usrLgid) {
        usrLgid__ = usrLgid;
    }

    /**
     * <p>get USR_PSWD value
     * @return USR_PSWD value
     */
    public String getUsrPswd() {
        return usrPswd__;
    }

    /**
     * <p>set USR_PSWD value
     * @param usrPswd USR_PSWD value
     */
    public void setUsrPswd(String usrPswd) {
        usrPswd__ = usrPswd;
    }

    /**
     * <p>get USR_JKBN value
     * @return USR_JKBN value
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }

    /**
     * <p>set USR_JKBN value
     * @param usrJkbn USR_JKBN value
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }

    /**
     * <p>get USR_AUID value
     * @return USR_AUID value
     */
    public int getUsrAuid() {
        return usrAuid__;
    }

    /**
     * <p>set USR_AUID value
     * @param usrAuid USR_AUID value
     */
    public void setUsrAuid(int usrAuid) {
        usrAuid__ = usrAuid;
    }

    /**
     * <p>get USR_ADATE value
     * @return USR_ADATE value
     */
    public UDate getUsrAdate() {
        return usrAdate__;
    }

    /**
     * <p>set USR_ADATE value
     * @param usrAdate USR_ADATE value
     */
    public void setUsrAdate(UDate usrAdate) {
        usrAdate__ = usrAdate;
    }

    /**
     * <p>get USR_EUID value
     * @return USR_EUID value
     */
    public int getUsrEuid() {
        return usrEuid__;
    }

    /**
     * <p>set USR_EUID value
     * @param usrEuid USR_EUID value
     */
    public void setUsrEuid(int usrEuid) {
        usrEuid__ = usrEuid;
    }

    /**
     * <p>get USR_EDATE value
     * @return USR_EDATE value
     */
    public UDate getUsrEdate() {
        return usrEdate__;
    }

    /**
     * <p>set USR_EDATE value
     * @param usrEdate USR_EDATE value
     */
    public void setUsrEdate(UDate usrEdate) {
        usrEdate__ = usrEdate;
    }

    /**
     * <p>usrPswdEdate を取得します。
     * @return usrPswdEdate
     */
    public UDate getUsrPswdEdate() {
        return usrPswdEdate__;
    }

    /**
     * <p>usrPswdEdate をセットします。
     * @param usrPswdEdate usrPswdEdate
     */
    public void setUsrPswdEdate(UDate usrPswdEdate) {
        usrPswdEdate__ = usrPswdEdate;
    }

    /**
     * <p>usrPswdKbn を取得します。
     * @return usrPswdKbn
     */
    public int getUsrPswdKbn() {
        return usrPswdKbn__;
    }

    /**
     * <p>usrPswdKbn をセットします。
     * @param usrPswdKbn usrPswdKbn
     */
    public void setUsrPswdKbn(int usrPswdKbn) {
        usrPswdKbn__ = usrPswdKbn;
    }

    /**
     * <p>usrVarFlg を取得します。
     * @return usrVarFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }

    /**
     * <p>usrVarFlg をセットします。
     * @param usrVarFlg usrVarFlg
     */
    public void setUsrUkoFlg(int usrVarFlg) {
        usrUkoFlg__ = usrVarFlg;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(usrLgid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(usrPswd__, ""));
        buf.append(",");
        buf.append(usrJkbn__);
        buf.append(",");
        buf.append(usrAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(usrAdate__, ""));
        buf.append(",");
        buf.append(usrEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(usrEdate__, ""));
        buf.append(",");
        buf.append(usrPswdKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(usrPswdEdate__, ""));
        return buf.toString();
    }
}
