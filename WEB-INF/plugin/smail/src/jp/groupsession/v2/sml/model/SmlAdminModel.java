package jp.groupsession.v2.sml.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <p>SML_ADMIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlAdminModel implements Serializable {

    /** SMA_MAILFW mapping */
    private int smaMailfw__;
    /** SMA_AUTH_TYPE mapping */
    private int smaAuthType__;
    /** SMA_SMTPURL mapping */
    private String smaSmtpurl__;
    /** SMA_SMTP_PORT mapping */
    private String smaSmtpPort__;
    /** SMA_SMTP_USER mapping */
    private String smaSmtpUser__;
    /** SMA_SMTP_PASS mapping */
    private String smaSmtpPass__;
    /** SMA_FROM_ADD mapping */
    private String smaFromAdd__;
    /** SMA_FWLMT_KBN mapping */
    private int smaFwlmtKbn__;
    /** SMA_AUID mapping */
    private int smaAuid__;
    /** SMA_ADATE mapping */
    private UDate smaAdate__;
    /** SMA_EUID mapping */
    private int smaEuid__;
    /** SMA_EDATE mapping */
    private UDate smaEdate__;
    /** SMA_SSL mapping */
    private int smaSsl__;
    /** SMA_ACNT_MAKE mapping */
    private int smaAcntMake__;
    /** SMA_AUTO_DEL_KBN mapping */
    private int smaAutoDelKbn__;
    /** SMA_ACNT_USER mapping */
    private int smaAcntUser__;
    /** SMA_MAX_DSP_STYPE mapping */
    private int smaMaxDspStype__;
    /** SMA_MAX_DSP mapping */
    private int smaMaxDsp__;
    /** SMA_RELOAD_STYPE mapping */
    private int smaReloadDspStype__;
    /** SMA_RELOAD mapping */
    private int smaReloadDsp__;
    /** SMA_PHOTO_DSP_STYPE mapping */
    private int smaPhotoDspStype__;
    /** SMA_PHOTO_DSP mapping */
    private int smaPhotoDsp__;
    /** SMA_ATTACH_DSP_STYPE mapping */
    private int smaAttachDspStype__;
    /** SMA_ATTACH_DSP mapping */
    private int smaAttachDsp__;
    /** COT_SID mapping */
    private int cotSid__;

    /**
     * <p>Default Constructor
     */
    public SmlAdminModel() {
    }

    /**
     * <p>初期値を設定する Constructor
     * @param userSid ユーザSID
     */
    public SmlAdminModel(int userSid) {
        UDate now = new UDate();
        smaMailfw__ = GSConstSmail.MAIL_FORWARD_NG;
        smaSmtpurl__ = "";
        smaSmtpPort__ = "";
        smaSmtpUser__ = "";
        smaSmtpPass__ = "";
        smaFromAdd__ = "";
        smaFwlmtKbn__ = Integer.parseInt(GSConstSmail.MAIL_FORWARD_NO_LIMIT);
        smaAuid__ = userSid;
        smaAdate__ = now;
        smaEuid__ = userSid;
        smaEdate__ = now;
        smaSsl__ = GSConstSmail.SSL_NOTUSE;
        smaAcntMake__ = GSConstSmail.KANRI_USER_ONLY;
        smaAutoDelKbn__ = GSConstSmail.AUTO_DEL_ADM;
        smaAcntUser__ = GSConstSmail.KANRI_ACCOUNT_USER_NO;
        smaMaxDspStype__ = GSConstSmail.DISP_CONF_USER;
        smaMaxDsp__ = 0;
        smaReloadDspStype__ = GSConstSmail.DISP_CONF_USER;
        smaReloadDsp__ = 0;
        smaPhotoDspStype__ = GSConstSmail.DISP_CONF_USER;
        smaPhotoDsp__ = GSConstSmail.SML_PHOTO_DSP_DSP;
        smaAttachDspStype__ = GSConstSmail.DISP_CONF_USER;
        smaAttachDsp__ = GSConstSmail.SML_IMAGE_TEMP_DSP;
    }

    /**
     * <p>smaSmtpPort を取得します。
     * @return smaSmtpPort
     */
    public String getSmaSmtpPort() {
        return smaSmtpPort__;
    }
    /**
     * <p>smaSmtpPort をセットします。
     * @param smaSmtpPort smaSmtpPort
     */
    public void setSmaSmtpPort(String smaSmtpPort) {
        smaSmtpPort__ = smaSmtpPort;
    }

    /**
     * <p>smaFromAdd を取得します。
     * @return smaFromAdd
     */
    public String getSmaFromAdd() {
        return smaFromAdd__;
    }

    /**
     * <p>smaFromAdd をセットします。
     * @param smaFromAdd smaFromAdd
     */
    public void setSmaFromAdd(String smaFromAdd) {
        smaFromAdd__ = smaFromAdd;
    }

    /**
     * <p>get SMA_MAILFW value
     * @return SMA_MAILFW value
     */
    public int getSmaMailfw() {
        return smaMailfw__;
    }

    /**
     * <p>set SMA_MAILFW value
     * @param smaMailfw SMA_MAILFW value
     */
    public void setSmaMailfw(int smaMailfw) {
        smaMailfw__ = smaMailfw;
    }
    
    /**
     * <p>get SMA_AUTH_TYPE value
     * @return SMA_AUTH_TYPE value
     */
    public int getSmaAuthType() {
        return smaAuthType__;
    }

    /**
     * <p>set SMA_AUTH_TYPE value
     * @param smaAuthType SMA_AUTH_TYPE value
     */
    public void setSmaAuthType(int smaAuthType) {
        smaAuthType__ = smaAuthType;
    }

    /**
     * <p>get SMA_SMTPURL value
     * @return SMA_SMTPURL value
     */
    public String getSmaSmtpurl() {
        return smaSmtpurl__;
    }

    /**
     * <p>set SMA_SMTPURL value
     * @param smaSmtpurl SMA_SMTPURL value
     */
    public void setSmaSmtpurl(String smaSmtpurl) {
        smaSmtpurl__ = smaSmtpurl;
    }

    /**
     * <p>get SMA_SMTP_USER value
     * @return SMA_SMTP_USER value
     */
    public String getSmaSmtpUser() {
        return smaSmtpUser__;
    }

    /**
     * <p>set SMA_SMTP_USER value
     * @param smaSmtpUser SMA_SMTP_USER value
     */
    public void setSmaSmtpUser(String smaSmtpUser) {
        smaSmtpUser__ = smaSmtpUser;
    }

    /**
     * <p>get SMA_SMTP_PASS value
     * @return SMA_SMTP_PASS value
     */
    public String getSmaSmtpPass() {
        return smaSmtpPass__;
    }

    /**
     * <p>set SMA_SMTP_PASS value
     * @param smaSmtpPass SMA_SMTP_PASS value
     */
    public void setSmaSmtpPass(String smaSmtpPass) {
        smaSmtpPass__ = smaSmtpPass;
    }

    /**
     * <p>get SMA_AUID value
     * @return SMA_AUID value
     */
    public int getSmaAuid() {
        return smaAuid__;
    }

    /**
     * <p>set SMA_AUID value
     * @param smaAuid SMA_AUID value
     */
    public void setSmaAuid(int smaAuid) {
        smaAuid__ = smaAuid;
    }

    /**
     * <p>get SMA_ADATE value
     * @return SMA_ADATE value
     */
    public UDate getSmaAdate() {
        return smaAdate__;
    }

    /**
     * <p>set SMA_ADATE value
     * @param smaAdate SMA_ADATE value
     */
    public void setSmaAdate(UDate smaAdate) {
        smaAdate__ = smaAdate;
    }

    /**
     * <p>get SMA_EUID value
     * @return SMA_EUID value
     */
    public int getSmaEuid() {
        return smaEuid__;
    }

    /**
     * <p>set SMA_EUID value
     * @param smaEuid SMA_EUID value
     */
    public void setSmaEuid(int smaEuid) {
        smaEuid__ = smaEuid;
    }

    /**
     * <p>get SMA_EDATE value
     * @return SMA_EDATE value
     */
    public UDate getSmaEdate() {
        return smaEdate__;
    }

    /**
     * <p>set SMA_EDATE value
     * @param smaEdate SMA_EDATE value
     */
    public void setSmaEdate(UDate smaEdate) {
        smaEdate__ = smaEdate;
    }

    /**
     * <p>get SMA_ACNT_USER value
     * @return SMA_ACNT_USER value
     */
    public int getSmaAcntUser() {
        return smaAcntUser__;
    }

    /**
     * <p>set SMA_ACNT_USER value
     * @param smaAcntUser SMA_ACNT_USER value
     */
    public void setSmaAcntUser(int smaAcntUser) {
        smaAcntUser__ = smaAcntUser;
    }

    /**
     * <p>smaFwlmtKbn を取得します。
     * @return smaFwlmtKbn
     */
    public int getSmaFwlmtKbn() {
        return smaFwlmtKbn__;
    }

    /**
     * <p>smaFwlmtKbn をセットします。
     * @param smaFwlmtKbn smaFwlmtKbn
     */
    public void setSmaFwlmtKbn(int smaFwlmtKbn) {
        smaFwlmtKbn__ = smaFwlmtKbn;
    }

    /**
     * @return smaSsl
     */
    public int getSmaSsl() {
        return smaSsl__;
    }

    /**
     * @param smaSsl セットする smaSsl
     */
    public void setSmaSsl(int smaSsl) {
        smaSsl__ = smaSsl;
    }

    /**
     * <p>smaAcntMake を取得します。
     * @return smaAcntMake
     */
    public int getSmaAcntMake() {
        return smaAcntMake__;
    }

    /**
     * <p>smaAcntMake をセットします。
     * @param smaAcntMake smaAcntMake
     */
    public void setSmaAcntMake(int smaAcntMake) {
        smaAcntMake__ = smaAcntMake;
    }

    /**
     * <p>smaAutoDelKbn を取得します。
     * @return smaAutoDelKbn
     */
    public int getSmaAutoDelKbn() {
        return smaAutoDelKbn__;
    }

    /**
     * <p>smaAutoDelKbn をセットします。
     * @param smaAutoDelKbn smaAutoDelKbn
     */
    public void setSmaAutoDelKbn(int smaAutoDelKbn) {
        smaAutoDelKbn__ = smaAutoDelKbn;
    }

    /**
     * <p>smaMaxDspStype を取得します。
     * @return smaMaxDspStype
     */
    public int getSmaMaxDspStype() {
        return smaMaxDspStype__;
    }

    /**
     * <p>smaMaxDspStype をセットします。
     * @param smaMaxDspStype smaMaxDspStype
     */
    public void setSmaMaxDspStype(int smaMaxDspStype) {
        smaMaxDspStype__ = smaMaxDspStype;
    }

    /**
     * <p>smaMaxDsp を取得します。
     * @return smaMaxDsp
     */
    public int getSmaMaxDsp() {
        return smaMaxDsp__;
    }

    /**
     * <p>smaMaxDsp をセットします。
     * @param smaMaxDsp smaMaxDsp
     */
    public void setSmaMaxDsp(int smaMaxDsp) {
        smaMaxDsp__ = smaMaxDsp;
    }

    /**
     * <p>smaReloadDspStype を取得します。
     * @return smaReloadDspStype
     */
    public int getSmaReloadDspStype() {
        return smaReloadDspStype__;
    }

    /**
     * <p>smaReloadDspStype をセットします。
     * @param smaReloadDspStype smaReloadDspStype
     */
    public void setSmaReloadDspStype(int smaReloadDspStype) {
        smaReloadDspStype__ = smaReloadDspStype;
    }

    /**
     * <p>smaReloadDsp を取得します。
     * @return smaReloadDsp
     */
    public int getSmaReloadDsp() {
        return smaReloadDsp__;
    }

    /**
     * <p>smaReloadDsp をセットします。
     * @param smaReloadDsp smaReloadDsp
     */
    public void setSmaReloadDsp(int smaReloadDsp) {
        smaReloadDsp__ = smaReloadDsp;
    }

    /**
     * <p>smaPhotoDspStype を取得します。
     * @return smaPhotoDspStype
     */
    public int getSmaPhotoDspStype() {
        return smaPhotoDspStype__;
    }

    /**
     * <p>smaPhotoDspStype をセットします。
     * @param smaPhotoDspStype smaPhotoDspStype
     */
    public void setSmaPhotoDspStype(int smaPhotoDspStype) {
        smaPhotoDspStype__ = smaPhotoDspStype;
    }

    /**
     * <p>smaPhotoDsp を取得します。
     * @return smaPhotoDsp
     */
    public int getSmaPhotoDsp() {
        return smaPhotoDsp__;
    }

    /**
     * <p>smaPhotoDsp をセットします。
     * @param smaPhotoDsp smaPhotoDsp
     */
    public void setSmaPhotoDsp(int smaPhotoDsp) {
        smaPhotoDsp__ = smaPhotoDsp;
    }

    /**
     * <p>smaAttachDspStype を取得します。
     * @return smaAttachDspStype
     */
    public int getSmaAttachDspStype() {
        return smaAttachDspStype__;
    }

    /**
     * <p>smaAttachDspStype をセットします。
     * @param smaAttachDspStype smaAttachDspStype
     */
    public void setSmaAttachDspStype(int smaAttachDspStype) {
        smaAttachDspStype__ = smaAttachDspStype;
    }

    /**
     * <p>smaAttachDsp を取得します。
     * @return smaAttachDsp
     */
    public int getSmaAttachDsp() {
        return smaAttachDsp__;
    }

    /**
     * <p>smaAttachDsp をセットします。
     * @param smaAttachDsp smaAttachDsp
     */
    public void setSmaAttachDsp(int smaAttachDsp) {
        smaAttachDsp__ = smaAttachDsp;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(smaMailfw__);
        buf.append(",");
        buf.append(smaAuthType__);
        buf.append(",");
        buf.append(NullDefault.getString(smaSmtpurl__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(smaSmtpPort__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(smaSmtpUser__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(smaSmtpPass__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(smaFromAdd__, ""));
        buf.append(",");
        buf.append(smaFwlmtKbn__);
        buf.append(",");
        buf.append(smaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smaAdate__, ""));
        buf.append(",");
        buf.append(smaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smaEdate__, ""));
        buf.append(",");
        buf.append(smaSsl__);
        buf.append(",");
        buf.append(smaAcntMake__);
        buf.append(",");
        buf.append(smaAutoDelKbn__);
        buf.append(",");
        buf.append(smaAcntUser__);
        buf.append(",");
        buf.append(smaMaxDspStype__);
        buf.append(",");
        buf.append(smaMaxDsp__);
        buf.append(",");
        buf.append(smaPhotoDspStype__);
        buf.append(",");
        buf.append(smaPhotoDsp__);
        buf.append(",");
        buf.append(smaAttachDspStype__);
        buf.append(",");
        buf.append(smaAttachDsp__);
        buf.append(",");
        buf.append(cotSid__);
        return buf.toString();
    }

}
