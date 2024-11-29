package jp.groupsession.v2.sml.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>SML_ACCOUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAccountModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SAC_TYPE mapping */
    private int sacType__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SAC_NAME mapping */
    private String sacName__;
    /** SAC_BIKO mapping */
    private String sacBiko__;
    /** SAC_SEND_MAILTYPE mapping */
    private int sacSendMailtype__;
    /** SAC_JKBN mapping */
    private int sacJkbn__;
    /** SAC_THEME mapping */
    private int sacTheme__;
    /** SAC_QUOTES mapping */
    private int sacQuotes__;
    /** ログイン停止フラグ 表示用*/
    private int usrUkoFlg__;
    /**
     * <p>Default Constructor
     */
    public SmlAccountModel() {
    }

    /**
     * <p>get SAC_SID value
     * @return SAC_SID value
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>set SAC_SID value
     * @param sacSid SAC_SID value
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>get SAC_TYPE value
     * @return SAC_TYPE value
     */
    public int getSacType() {
        return sacType__;
    }

    /**
     * <p>set SAC_TYPE value
     * @param sacType SAC_TYPE value
     */
    public void setSacType(int sacType) {
        sacType__ = sacType;
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
     * <p>get SAC_NAME value
     * @return SAC_NAME value
     */
    public String getSacName() {
        return sacName__;
    }

    /**
     * <p>set SAC_NAME value
     * @param sacName SAC_NAME value
     */
    public void setSacName(String sacName) {
        sacName__ = sacName;
    }

    /**
     * <p>get SAC_BIKO value
     * @return SAC_BIKO value
     */
    public String getSacBiko() {
        return sacBiko__;
    }

    /**
     * <p>set SAC_BIKO value
     * @param sacBiko SAC_BIKO value
     */
    public void setSacBiko(String sacBiko) {
        sacBiko__ = sacBiko;
    }

    /**
     * <p>get SAC_SEND_MAILTYPE value
     * @return SAC_SEND_MAILTYPE value
     */
    public int getSacSendMailtype() {
        return sacSendMailtype__;
    }

    /**
     * <p>set SAC_SEND_MAILTYPE value
     * @param sacSendMailtype SAC_SEND_MAILTYPE value
     */
    public void setSacSendMailtype(int sacSendMailtype) {
        sacSendMailtype__ = sacSendMailtype;
    }


    /**
     * <p>get SAC_JKBN value
     * @return SAC_JKBN value
     */
    public int getSacJkbn() {
        return sacJkbn__;
    }

    /**
     * <p>set SAC_JKBN value
     * @param sacJkbn SAC_JKBN value
     */
    public void setSacJkbn(int sacJkbn) {
        sacJkbn__ = sacJkbn;
    }

    /**
     * <p>get SAC_THEME value
     * @return SAC_THEME value
     */
    public int getSacTheme() {
        return sacTheme__;
    }

    /**
     * <p>set SAC_THEME value
     * @param sacTheme SAC_THEME value
     */
    public void setSacTheme(int sacTheme) {
        sacTheme__ = sacTheme;
    }


    /**
     * <p>get SAC_QUOTES value
     * @return SAC_QUOTES value
     */
    public int getSacQuotes() {
        return sacQuotes__;
    }

    /**
     * <p>set SAC_QUOTES value
     * @param sacQuotes SAC_QUOTES value
     */
    public void setSacQuotes(int sacQuotes) {
        sacQuotes__ = sacQuotes;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(sacType__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(sacName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(sacBiko__, ""));
        buf.append(",");
        buf.append(sacSendMailtype__);
        buf.append(",");
        buf.append(sacJkbn__);
        buf.append(",");
        buf.append(sacTheme__);
        buf.append(",");
        buf.append(sacQuotes__);
        return buf.toString();
    }

    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }

    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }

}
