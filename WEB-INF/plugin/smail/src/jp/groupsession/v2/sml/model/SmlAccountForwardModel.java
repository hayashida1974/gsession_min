package jp.groupsession.v2.sml.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SAC_ACCOUNT_FORWARD Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlAccountForwardModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SAC_MAX_DSP mapping */
    /** SAC_AUID mapping */
    private int safAuid__;
    /** SAC_ADATE mapping */
    private UDate safAdate__;
    /** SAC_EUID mapping */
    private int safEuid__;
    /** SAC_EDATE mapping */
    private UDate safEdate__;
    /** SAC_MAILFW mapping */
    private int safMailfw__;
    /** SAC_SMAIL_OP mapping */
    private int safSmailOp__;
    /** SAC_MAIL_DF mapping */
    private String safMailDf__;
    /** SAC_HURIWAKE mapping */
    private int safHuriwake__;
    /** SAC_ZMAIL1 mapping */
    private String safZmail1__;
    /** SAC_ZMAIL2 mapping */
    private String safZmail2__;
    /** SAC_ZMAIL3 mapping */
    private String safZmail3__;
    /**

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
     * <p>get SAC_AUID value
     * @return SAC_AUID value
     */
    public int getSafAuid() {
        return safAuid__;
    }

    /**
     * <p>set SAC_AUID value
     * @param safAuid SAC_AUID value
     */
    public void setSafAuid(int safAuid) {
        safAuid__ = safAuid;
    }

    /**
     * <p>get SAC_ADATE value
     * @return SAC_ADATE value
     */
    public UDate getSafAdate() {
        return safAdate__;
    }

    /**
     * <p>set SAC_ADATE value
     * @param safAdate SAC_ADATE value
     */
    public void setSafAdate(UDate safAdate) {
        safAdate__ = safAdate;
    }

    /**
     * <p>get SAC_EUID value
     * @return SAC_EUID value
     */
    public int getSafEuid() {
        return safEuid__;
    }

    /**
     * <p>set SAC_EUID value
     * @param safEuid SAC_EUID value
     */
    public void setSafEuid(int safEuid) {
        safEuid__ = safEuid;
    }

    /**
     * <p>get SAC_EDATE value
     * @return SAC_EDATE value
     */
    public UDate getSafEdate() {
        return safEdate__;
    }

    /**
     * <p>set SAC_EDATE value
     * @param safEdate SAC_EDATE value
     */
    public void setSafEdate(UDate safEdate) {
        safEdate__ = safEdate;
    }

    /**
     * <p>get SAC_MAILFW value
     * @return SAC_MAILFW value
     */
    public int getSafMailfw() {
        return safMailfw__;
    }

    /**
     * <p>set SAC_MAILFW value
     * @param safMailfw SAC_MAILFW value
     */
    public void setSafMailfw(int safMailfw) {
        safMailfw__ = safMailfw;
    }

    /**
     * <p>get SAC_MAIL_DF value
     * @return SAC_MAIL_DF value
     */
    public String getSafMailDf() {
        return safMailDf__;
    }

    /**
     * <p>set SAC_MAIL_DF value
     * @param safMailDf SAC_MAIL_DF value
     */
    public void setSafMailDf(String safMailDf) {
        safMailDf__ = safMailDf;
    }

    /**
     * <p>get SAC_HURIWAKE value
     * @return SAC_HURIWAKE value
     */
    public int getSafHuriwake() {
        return safHuriwake__;
    }

    /**
     * <p>set SAC_HURIWAKE value
     * @param safHuriwake SAC_HURIWAKE value
     */
    public void setSafHuriwake(int safHuriwake) {
        safHuriwake__ = safHuriwake;
    }

    /**
     * <p>get SAC_ZMAIL1 value
     * @return SAC_ZMAIL1 value
     */
    public String getSafZmail1() {
        return safZmail1__;
    }

    /**
     * <p>set SAC_ZMAIL1 value
     * @param safZmail1 SAC_ZMAIL1 value
     */
    public void setSafZmail1(String safZmail1) {
        safZmail1__ = safZmail1;
    }

    /**
     * <p>get SAC_ZMAIL2 value
     * @return SAC_ZMAIL2 value
     */
    public String getSafZmail2() {
        return safZmail2__;
    }

    /**
     * <p>set SAC_ZMAIL2 value
     * @param safZmail2 SAC_ZMAIL2 value
     */
    public void setSafZmail2(String safZmail2) {
        safZmail2__ = safZmail2;
    }

    /**
     * <p>get SAC_ZMAIL3 value
     * @return SAC_ZMAIL3 value
     */
    public String getSafZmail3() {
        return safZmail3__;
    }

    /**
     * <p>set SAC_ZMAIL3 value
     * @param safZmail3 SAC_ZMAIL3 value
     */
    public void setSafZmail3(String safZmail3) {
        safZmail3__ = safZmail3;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(safAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(safAdate__, ""));
        buf.append(",");
        buf.append(safEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(safEdate__, ""));
        buf.append(",");
        buf.append(safMailfw__);
        buf.append(",");
        buf.append(NullDefault.getString(safMailDf__, ""));
        buf.append(",");
        buf.append(safHuriwake__);
        buf.append(",");
        buf.append(NullDefault.getString(safZmail1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(safZmail2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(safZmail3__, ""));
        return buf.toString();
    }

    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>sacSid をセットします。
     * @param sacSid sacSid
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>safSmailOp を取得します。
     * @return safSmailOp
     */
    public int getSafSmailOp() {
        return safSmailOp__;
    }

    /**
     * <p>safSmailOp をセットします。
     * @param safSmailOp safSmailOp
     */
    public void setSafSmailOp(int safSmailOp) {
        safSmailOp__ = safSmailOp;
    }


}
