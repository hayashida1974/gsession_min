package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_OTP_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOtpConfModel implements Serializable {

    /** COC_USE_OTP mapping */
    private int cocUseOtp__;
    /** COC_OTP_USER mapping */
    private int cocOtpUser__;
    /** COC_OTP_USER_TYPE mapping */
    private int cocOtpUserType__;
    /** COC_OTP_IPCOND mapping */
    private int cocOtpIpcond__;
    /** COC_OTP_IP mapping */
    private String cocOtpIp__;
    /** COC_SMTP_URL mapping */
    private String cocSmtpUrl__;
    /** COC_SMTP_PORT mapping */
    private String cocSmtpPort__;
    /** COC_SMTP_SSLUSE mapping */
    private int cocSmtpSsluse__;
    /** COC_SMTP_FROM mapping */
    private String cocSmtpFrom__;
    /** COC_SMTP_USER mapping */
    private String cocSmtpUser__;
    /** COC_SMTP_PASS mapping */
    private String cocSmtpPass__;
    /** COC_SMTP_AUTH_TYPE mapping */
    private int cocSmtpAuthType__;
    /** COT_SID mapping */
    private int cotSid__;

    /**
     * <p>Default Constructor
     */
    public CmnOtpConfModel() {
    }

    /**
     * <p>get COC_USE_OTP value
     * @return COC_USE_OTP value
     */
    public int getCocUseOtp() {
        return cocUseOtp__;
    }

    /**
     * <p>set COC_USE_OTP value
     * @param cocUseOtp COC_USE_OTP value
     */
    public void setCocUseOtp(int cocUseOtp) {
        cocUseOtp__ = cocUseOtp;
    }

    /**
     * <p>get COC_OTP_USER value
     * @return COC_OTP_USER value
     */
    public int getCocOtpUser() {
        return cocOtpUser__;
    }

    /**
     * <p>set COC_OTP_USER value
     * @param cocOtpUser COC_OTP_USER value
     */
    public void setCocOtpUser(int cocOtpUser) {
        cocOtpUser__ = cocOtpUser;
    }

    /**
     * <p>get COC_OTP_USER_TYPE value
     * @return COC_OTP_USER_TYPE value
     */
    public int getCocOtpUserType() {
        return cocOtpUserType__;
    }

    /**
     * <p>set COC_OTP_USER_TYPE value
     * @param cocOtpUserType COC_OTP_USER_TYPE value
     */
    public void setCocOtpUserType(int cocOtpUserType) {
        cocOtpUserType__ = cocOtpUserType;
    }

    /**
     * <p>get COC_OTP_IPCOND value
     * @return COC_OTP_IPCOND value
     */
    public int getCocOtpIpcond() {
        return cocOtpIpcond__;
    }

    /**
     * <p>set COC_OTP_IPCOND value
     * @param cocOtpIpcond COC_OTP_IPCOND value
     */
    public void setCocOtpIpcond(int cocOtpIpcond) {
        cocOtpIpcond__ = cocOtpIpcond;
    }

    /**
     * <p>get COC_OTP_IP value
     * @return COC_OTP_IP value
     */
    public String getCocOtpIp() {
        return cocOtpIp__;
    }

    /**
     * <p>set COC_OTP_IP value
     * @param cocOtpIp COC_OTP_IP value
     */
    public void setCocOtpIp(String cocOtpIp) {
        cocOtpIp__ = cocOtpIp;
    }

    /**
     * <p>get COC_SMTP_URL value
     * @return COC_SMTP_URL value
     */
    public String getCocSmtpUrl() {
        return cocSmtpUrl__;
    }

    /**
     * <p>set COC_SMTP_URL value
     * @param cocSmtpUrl COC_SMTP_URL value
     */
    public void setCocSmtpUrl(String cocSmtpUrl) {
        cocSmtpUrl__ = cocSmtpUrl;
    }

    /**
     * <p>get COC_SMTP_PORT value
     * @return COC_SMTP_PORT value
     */
    public String getCocSmtpPort() {
        return cocSmtpPort__;
    }

    /**
     * <p>set COC_SMTP_PORT value
     * @param cocSmtpPort COC_SMTP_PORT value
     */
    public void setCocSmtpPort(String cocSmtpPort) {
        cocSmtpPort__ = cocSmtpPort;
    }

    /**
     * <p>get COC_SMTP_SSLUSE value
     * @return COC_SMTP_SSLUSE value
     */
    public int getCocSmtpSsluse() {
        return cocSmtpSsluse__;
    }

    /**
     * <p>set COC_SMTP_SSLUSE value
     * @param cocSmtpSsluse COC_SMTP_SSLUSE value
     */
    public void setCocSmtpSsluse(int cocSmtpSsluse) {
        cocSmtpSsluse__ = cocSmtpSsluse;
    }

    /**
     * <p>get COC_SMTP_FROM value
     * @return COC_SMTP_FROM value
     */
    public String getCocSmtpFrom() {
        return cocSmtpFrom__;
    }

    /**
     * <p>set COC_SMTP_FROM value
     * @param cocSmtpFrom COC_SMTP_FROM value
     */
    public void setCocSmtpFrom(String cocSmtpFrom) {
        cocSmtpFrom__ = cocSmtpFrom;
    }

    /**
     * <p>get COC_SMTP_USER value
     * @return COC_SMTP_USER value
     */
    public String getCocSmtpUser() {
        return cocSmtpUser__;
    }

    /**
     * <p>set COC_SMTP_USER value
     * @param cocSmtpUser COC_SMTP_USER value
     */
    public void setCocSmtpUser(String cocSmtpUser) {
        cocSmtpUser__ = cocSmtpUser;
    }

    /**
     * <p>get COC_SMTP_PASS value
     * @return COC_SMTP_PASS value
     */
    public String getCocSmtpPass() {
        return cocSmtpPass__;
    }

    /**
     * <p>set COC_SMTP_PASS value
     * @param cocSmtpPass COC_SMTP_PASS value
     */
    public void setCocSmtpPass(String cocSmtpPass) {
        cocSmtpPass__ = cocSmtpPass;
    }

    /**
     * <p>get COC_SMTP_AUTH_TYPE value
     * @return COC_SMTP_AUTH_TYPE value
     */
    public int getCocSmtpAuthType() {
        return cocSmtpAuthType__;
    }

    /**
     * <p>set COC_SMTP_AUTH_TYPE value
     * @param cocSmtpAuthType COC_SMTP_AUTH_TYPE value
     */
    public void setCocSmtpAuthType(int cocSmtpAuthType) {
        cocSmtpAuthType__ = cocSmtpAuthType;
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
        buf.append(cocUseOtp__);
        buf.append(",");
        buf.append(cocOtpUser__);
        buf.append(",");
        buf.append(cocOtpUserType__);
        buf.append(",");
        buf.append(cocOtpIpcond__);
        buf.append(",");
        buf.append(NullDefault.getString(cocOtpIp__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cocSmtpUrl__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cocSmtpPort__, ""));
        buf.append(",");
        buf.append(cocSmtpSsluse__);
        buf.append(",");
        buf.append(NullDefault.getString(cocSmtpFrom__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cocSmtpUser__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cocSmtpPass__, ""));
        buf.append(",");
        buf.append(cocSmtpAuthType__);
        buf.append(",");
        buf.append(cotSid__);
        return buf.toString();
    }

}
