package jp.groupsession.v2.convert.convert430.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CIR_ACCOUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CvtCirAccountModel implements Serializable {

    /** CAC_SID mapping */
    private int cacSid__;
    /** CAC_TYPE mapping */
    private int cacType__;
    /** USR_SID mapping */
    private int usrSid__;
    /** CAC_NAME mapping */
    private String cacName__;
    /** CAC_BIKO mapping */
    private String cacBiko__;
    /** CAC_JKBN mapping */
    private int cacJkbn__;
    /** CAC_THEME mapping */
    private int cacTheme__;
    /** CAC_SML_NTF mapping */
    private int cacSmlNtf__;
    /** CAC_MEMO_KBN mapping */
    private int cacMemoKbn__;
    /** CAC_MEMO_DAY mapping */
    private int cacMemoDay__;
    /** CAC_KOU_KBN mapping */
    private int cacKouKbn__;
    /** CAC_INIT_KBN mapping */
    private int cacInitKbn__;
    /**
     * <p>Default Constructor
     */
    public CvtCirAccountModel() {
    }

    /**
     * <p>get CAC_SID value
     * @return CAC_SID value
     */
    public int getCacSid() {
        return cacSid__;
    }

    /**
     * <p>set CAC_SID value
     * @param cacSid CAC_SID value
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }

    /**
     * <p>get CAC_TYPE value
     * @return CAC_TYPE value
     */
    public int getCacType() {
        return cacType__;
    }

    /**
     * <p>set CAC_TYPE value
     * @param cacType CAC_TYPE value
     */
    public void setCacType(int cacType) {
        cacType__ = cacType;
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
     * <p>get CAC_NAME value
     * @return CAC_NAME value
     */
    public String getCacName() {
        return cacName__;
    }

    /**
     * <p>set CAC_NAME value
     * @param cacName CAC_NAME value
     */
    public void setCacName(String cacName) {
        cacName__ = cacName;
    }

    /**
     * <p>get CAC_BIKO value
     * @return CAC_BIKO value
     */
    public String getCacBiko() {
        return cacBiko__;
    }

    /**
     * <p>set CAC_BIKO value
     * @param cacBiko CAC_BIKO value
     */
    public void setCacBiko(String cacBiko) {
        cacBiko__ = cacBiko;
    }

    /**
     * <p>get CAC_JKBN value
     * @return CAC_JKBN value
     */
    public int getCacJkbn() {
        return cacJkbn__;
    }

    /**
     * <p>set CAC_JKBN value
     * @param cacJkbn CAC_JKBN value
     */
    public void setCacJkbn(int cacJkbn) {
        cacJkbn__ = cacJkbn;
    }

    /**
     * <p>get CAC_THEME value
     * @return CAC_THEME value
     */
    public int getCacTheme() {
        return cacTheme__;
    }

    /**
     * <p>set CAC_THEME value
     * @param cacTheme CAC_THEME value
     */
    public void setCacTheme(int cacTheme) {
        cacTheme__ = cacTheme;
    }


    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(cacSid__);
        buf.append(",");
        buf.append(cacType__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cacName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cacBiko__, ""));
        buf.append(",");
        buf.append(cacJkbn__);
        buf.append(",");
        buf.append(cacTheme__);
        return buf.toString();
    }

    /**
     * <p>cacSmlNtf を取得します。
     * @return cacSmlNtf
     */
    public int getCacSmlNtf() {
        return cacSmlNtf__;
    }

    /**
     * <p>cacSmlNtf をセットします。
     * @param cacSmlNtf cacSmlNtf
     */
    public void setCacSmlNtf(int cacSmlNtf) {
        cacSmlNtf__ = cacSmlNtf;
    }

    /**
     * <p>cacMemoKbn を取得します。
     * @return cacMemoKbn
     */
    public int getCacMemoKbn() {
        return cacMemoKbn__;
    }

    /**
     * <p>cacMemoKbn をセットします。
     * @param cacMemoKbn cacMemoKbn
     */
    public void setCacMemoKbn(int cacMemoKbn) {
        cacMemoKbn__ = cacMemoKbn;
    }

    /**
     * <p>cacMemoDay を取得します。
     * @return cacMemoDay
     */
    public int getCacMemoDay() {
        return cacMemoDay__;
    }

    /**
     * <p>cacMemoDay をセットします。
     * @param cacMemoDay cacMemoDay
     */
    public void setCacMemoDay(int cacMemoDay) {
        cacMemoDay__ = cacMemoDay;
    }

    /**
     * <p>cacKouKbn を取得します。
     * @return cacKouKbn
     */
    public int getCacKouKbn() {
        return cacKouKbn__;
    }

    /**
     * <p>cacKouKbn をセットします。
     * @param cacKouKbn cacKouKbn
     */
    public void setCacKouKbn(int cacKouKbn) {
        cacKouKbn__ = cacKouKbn;
    }

    /**
     * <p>cacInitKbn を取得します。
     * @return cacInitKbn
     */
    public int getCacInitKbn() {
        return cacInitKbn__;
    }

    /**
     * <p>cacInitKbn をセットします。
     * @param cacInitKbn cacInitKbn
     */
    public void setCacInitKbn(int cacInitKbn) {
        cacInitKbn__ = cacInitKbn;
    }

}
