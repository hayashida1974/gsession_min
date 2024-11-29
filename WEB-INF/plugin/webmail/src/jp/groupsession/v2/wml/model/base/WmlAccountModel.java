package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_ACCOUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WAC_ACCOUNT_ID mapping */
    private String wacAccountId__;
    /** WAC_TYPE mapping */
    private int wacType__;
    /** USR_SID mapping */
    private int usrSid__;
    /** WAC_NAME mapping */
    private String wacName__;
    /** WAC_ADDRESS mapping */
    private String wacAddress__;
    /** WAC_SEND_HOST mapping */
    private String wacSendHost__;
    /** WAC_SEND_PORT mapping */
    private int wacSendPort__;
    /** WAC_SEND_USER mapping */
    private String wacSendUser__;
    /** WAC_SEND_PASS mapping */
    private String wacSendPass__;
    /** WAC_SEND_SSL mapping */
    private int wacSendSsl__;
    /** WAC_RECEIVE_TYPE mapping */
    private int wacReceiveType__;
    /** WAC_RECEIVE_HOST mapping */
    private String wacReceiveHost__;
    /** WAC_RECEIVE_PORT mapping */
    private int wacReceivePort__;
    /** WAC_RECEIVE_USER mapping */
    private String wacReceiveUser__;
    /** WAC_RECEIVE_PASS mapping */
    private String wacReceivePass__;
    /** WAC_RECEIVE_SSL mapping */
    private int wacReceiveSsl__;
    /** WAC_DISK mapping */
    private int wacDisk__;
    /** WAC_DISK_SIZE mapping */
    private int wacDiskSize__;
    /** WAC_BIKO mapping */
    private String wacBiko__;
    /** WAC_ORGANIZATION mapping */
    private String wacOrganization__;
    /** WAC_SIGN mapping */
    private String wacSign__;
    /** WAC_SIGN_POINT_KBN mapping */
    private int wacSignPointKbn__;
    /** WAC_SIGN_DSP_KBN mapping */
    private int wacSignDspKbn__;
    /** WAC_AUTOTO mapping */
    private String wacAutoto__;
    /** WAC_AUTOCC mapping */
    private String wacAutocc__;
    /** WAC_AUTOBCC mapping */
    private String wacAutobcc__;
    /** WAC_DELRECEIVE mapping */
    private int wacDelreceive__;
    /** WAC_RERECEIVE mapping */
    private int wacRereceive__;
    /** WAC_APOP mapping */
    private int wacApop__;
    /** WAC_TOP_CMD mapping */
    private int wacTopCmd__;
    /** WAC_SMTP_AUTH mapping */
    private int wacSmtpAuth__;
    /** WAC_POPBSMTP mapping */
    private int wacPopbsmtp__;
    /** WAC_ENCODE_SEND mapping */
    private int wacEncodeSend__;
    /** WAC_AUTORECEIVE mapping */
    private int wacAutoreceive__;
    /** WAC_SEND_MAILTYPE mapping */
    private int wacSendMailtype__;
    /** WAC_RECEIVE_DATE mapping */
    private UDate wacReceiveDate__;
    /** WAC_JKBN mapping */
    private int wacJkbn__;
    /** WAC_AUTO_RECEIVE_TIME mapping */
    private int wacAutoReceiveTime__;
    /** WAC_THEME mapping */
    private int wacTheme__;
    /** WAC_CHECK_ADDRESS mapping */
    private int wacCheckAddress__;
    /** WAC_CHECK_FILE mapping */
    private int wacCheckFile__;
    /** WAC_COMPRESS_FILE mapping */
    private int wacCompressFile__;
    /** WAC_TIMESENT mapping */
    private int wacTimesent__;
    /** WAC_QUOTES mapping */
    private int wacQuotes__;
    /** WAC_DISK_SPS mapping */
    private int wacDiskSps__;
    /** WAC_AUTORECEIVE_AP mapping */
    private int wacAutoreceiveAp__;
    /** WAC_TIMESENT_DEF mapping */
    private int wacTimesentDef__;
    /** WAC_COMPRESS_FILE_DEF mapping */
    private int wacCompressFileDef__;
    /** WAC_SIGN_AUTO mapping */
    private int wacSignAuto__;
    /** WAC_AUTH_TYPE mapping */
    private int wacAuthType__;
    /** WAC_AUTO_SAVE_MINUTE mapping */
    private int wacAutoSaveMinute__;
    /** COT_SID mapping */
    private int cotSid__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountModel() {
    }

    /**
     * <p>get WAC_SID value
     * @return WAC_SID value
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>set WAC_SID value
     * @param wacSid WAC_SID value
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>get WAC_ACCOUNT_ID value
     * @return WAC_ACCOUNT_ID value
     */
    public String getWacAccountId() {
        return wacAccountId__;
    }

    /**
     * <p>set WAC_ACCOUNT_ID value
     * @param wacAccountId WAC_ACCOUNT_ID value
     */
    public void setWacAccountId(String wacAccountId) {
        wacAccountId__ = wacAccountId;
    }

    /**
     * <p>get WAC_TYPE value
     * @return WAC_TYPE value
     */
    public int getWacType() {
        return wacType__;
    }

    /**
     * <p>set WAC_TYPE value
     * @param wacType WAC_TYPE value
     */
    public void setWacType(int wacType) {
        wacType__ = wacType;
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
     * <p>get WAC_NAME value
     * @return WAC_NAME value
     */
    public String getWacName() {
        return wacName__;
    }

    /**
     * <p>set WAC_NAME value
     * @param wacName WAC_NAME value
     */
    public void setWacName(String wacName) {
        wacName__ = wacName;
    }

    /**
     * <p>get WAC_ADDRESS value
     * @return WAC_ADDRESS value
     */
    public String getWacAddress() {
        return wacAddress__;
    }

    /**
     * <p>set WAC_ADDRESS value
     * @param wacAddress WAC_ADDRESS value
     */
    public void setWacAddress(String wacAddress) {
        wacAddress__ = wacAddress;
    }

    /**
     * <p>get WAC_SEND_HOST value
     * @return WAC_SEND_HOST value
     */
    public String getWacSendHost() {
        return wacSendHost__;
    }

    /**
     * <p>set WAC_SEND_HOST value
     * @param wacSendHost WAC_SEND_HOST value
     */
    public void setWacSendHost(String wacSendHost) {
        wacSendHost__ = wacSendHost;
    }

    /**
     * <p>get WAC_SEND_PORT value
     * @return WAC_SEND_PORT value
     */
    public int getWacSendPort() {
        return wacSendPort__;
    }

    /**
     * <p>set WAC_SEND_PORT value
     * @param wacSendPort WAC_SEND_PORT value
     */
    public void setWacSendPort(int wacSendPort) {
        wacSendPort__ = wacSendPort;
    }

    /**
     * <p>get WAC_SEND_USER value
     * @return WAC_SEND_USER value
     */
    public String getWacSendUser() {
        return wacSendUser__;
    }

    /**
     * <p>set WAC_SEND_USER value
     * @param wacSendUser WAC_SEND_USER value
     */
    public void setWacSendUser(String wacSendUser) {
        wacSendUser__ = wacSendUser;
    }

    /**
     * <p>get WAC_SEND_PASS value
     * @return WAC_SEND_PASS value
     */
    public String getWacSendPass() {
        return wacSendPass__;
    }

    /**
     * <p>set WAC_SEND_PASS value
     * @param wacSendPass WAC_SEND_PASS value
     */
    public void setWacSendPass(String wacSendPass) {
        wacSendPass__ = wacSendPass;
    }

    /**
     * <p>get WAC_SEND_SSL value
     * @return WAC_SEND_SSL value
     */
    public int getWacSendSsl() {
        return wacSendSsl__;
    }

    /**
     * <p>set WAC_SEND_SSL value
     * @param wacSendSsl WAC_SEND_SSL value
     */
    public void setWacSendSsl(int wacSendSsl) {
        wacSendSsl__ = wacSendSsl;
    }

    /**
     * <p>get WAC_RECEIVE_TYPE value
     * @return WAC_RECEIVE_TYPE value
     */
    public int getWacReceiveType() {
        return wacReceiveType__;
    }

    /**
     * <p>set WAC_RECEIVE_TYPE value
     * @param wacReceiveType WAC_RECEIVE_TYPE value
     */
    public void setWacReceiveType(int wacReceiveType) {
        wacReceiveType__ = wacReceiveType;
    }

    /**
     * <p>get WAC_RECEIVE_HOST value
     * @return WAC_RECEIVE_HOST value
     */
    public String getWacReceiveHost() {
        return wacReceiveHost__;
    }

    /**
     * <p>set WAC_RECEIVE_HOST value
     * @param wacReceiveHost WAC_RECEIVE_HOST value
     */
    public void setWacReceiveHost(String wacReceiveHost) {
        wacReceiveHost__ = wacReceiveHost;
    }

    /**
     * <p>get WAC_RECEIVE_PORT value
     * @return WAC_RECEIVE_PORT value
     */
    public int getWacReceivePort() {
        return wacReceivePort__;
    }

    /**
     * <p>set WAC_RECEIVE_PORT value
     * @param wacReceivePort WAC_RECEIVE_PORT value
     */
    public void setWacReceivePort(int wacReceivePort) {
        wacReceivePort__ = wacReceivePort;
    }

    /**
     * <p>get WAC_RECEIVE_USER value
     * @return WAC_RECEIVE_USER value
     */
    public String getWacReceiveUser() {
        return wacReceiveUser__;
    }

    /**
     * <p>set WAC_RECEIVE_USER value
     * @param wacReceiveUser WAC_RECEIVE_USER value
     */
    public void setWacReceiveUser(String wacReceiveUser) {
        wacReceiveUser__ = wacReceiveUser;
    }

    /**
     * <p>get WAC_RECEIVE_PASS value
     * @return WAC_RECEIVE_PASS value
     */
    public String getWacReceivePass() {
        return wacReceivePass__;
    }

    /**
     * <p>set WAC_RECEIVE_PASS value
     * @param wacReceivePass WAC_RECEIVE_PASS value
     */
    public void setWacReceivePass(String wacReceivePass) {
        wacReceivePass__ = wacReceivePass;
    }

    /**
     * <p>get WAC_RECEIVE_SSL value
     * @return WAC_RECEIVE_SSL value
     */
    public int getWacReceiveSsl() {
        return wacReceiveSsl__;
    }

    /**
     * <p>set WAC_RECEIVE_SSL value
     * @param wacReceiveSsl WAC_RECEIVE_SSL value
     */
    public void setWacReceiveSsl(int wacReceiveSsl) {
        wacReceiveSsl__ = wacReceiveSsl;
    }

    /**
     * <p>get WAC_DISK value
     * @return WAC_DISK value
     */
    public int getWacDisk() {
        return wacDisk__;
    }

    /**
     * <p>set WAC_DISK value
     * @param wacDisk WAC_DISK value
     */
    public void setWacDisk(int wacDisk) {
        wacDisk__ = wacDisk;
    }

    /**
     * <p>get WAC_DISK_SIZE value
     * @return WAC_DISK_SIZE value
     */
    public int getWacDiskSize() {
        return wacDiskSize__;
    }

    /**
     * <p>set WAC_DISK_SIZE value
     * @param wacDiskSize WAC_DISK_SIZE value
     */
    public void setWacDiskSize(int wacDiskSize) {
        wacDiskSize__ = wacDiskSize;
    }

    /**
     * <p>get WAC_BIKO value
     * @return WAC_BIKO value
     */
    public String getWacBiko() {
        return wacBiko__;
    }

    /**
     * <p>set WAC_BIKO value
     * @param wacBiko WAC_BIKO value
     */
    public void setWacBiko(String wacBiko) {
        wacBiko__ = wacBiko;
    }

    /**
     * <p>get WAC_ORGANIZATION value
     * @return WAC_ORGANIZATION value
     */
    public String getWacOrganization() {
        return wacOrganization__;
    }

    /**
     * <p>set WAC_ORGANIZATION value
     * @param wacOrganization WAC_ORGANIZATION value
     */
    public void setWacOrganization(String wacOrganization) {
        wacOrganization__ = wacOrganization;
    }

    /**
     * <p>get WAC_SIGN value
     * @return WAC_SIGN value
     */
    public String getWacSign() {
        return wacSign__;
    }

    /**
     * <p>set WAC_SIGN value
     * @param wacSign WAC_SIGN value
     */
    public void setWacSign(String wacSign) {
        wacSign__ = wacSign;
    }

    /**
     * <p>get WAC_SIGN_POINT_KBN value
     * @return WAC_SIGN_POINT_KBN value
     */
    public int getWacSignPointKbn() {
        return wacSignPointKbn__;
    }

    /**
     * <p>set WAC_SIGN_POINT_KBN value
     * @param wacSignPointKbn WAC_SIGN_POINT_KBN value
     */
    public void setWacSignPointKbn(int wacSignPointKbn) {
        wacSignPointKbn__ = wacSignPointKbn;
    }

    /**
     * <p>get WAC_SIGN_DSP_KBN value
     * @return WAC_SIGN_DSP_KBN value
     */
    public int getWacSignDspKbn() {
        return wacSignDspKbn__;
    }

    /**
     * <p>set WAC_SIGN_DSP_KBN value
     * @param wacSignDspKbn WAC_SIGN_DSP_KBN value
     */
    public void setWacSignDspKbn(int wacSignDspKbn) {
        wacSignDspKbn__ = wacSignDspKbn;
    }

    /**
     * <p>get WAC_AUTOTO value
     * @return WAC_AUTOTO value
     */
    public String getWacAutoto() {
        return wacAutoto__;
    }

    /**
     * <p>set WAC_AUTOTO value
     * @param wacAutoto WAC_AUTOTO value
     */
    public void setWacAutoto(String wacAutoto) {
        wacAutoto__ = wacAutoto;
    }

    /**
     * <p>get WAC_AUTOCC value
     * @return WAC_AUTOCC value
     */
    public String getWacAutocc() {
        return wacAutocc__;
    }

    /**
     * <p>set WAC_AUTOCC value
     * @param wacAutocc WAC_AUTOCC value
     */
    public void setWacAutocc(String wacAutocc) {
        wacAutocc__ = wacAutocc;
    }

    /**
     * <p>get WAC_AUTOBCC value
     * @return WAC_AUTOBCC value
     */
    public String getWacAutobcc() {
        return wacAutobcc__;
    }

    /**
     * <p>set WAC_AUTOBCC value
     * @param wacAutobcc WAC_AUTOBCC value
     */
    public void setWacAutobcc(String wacAutobcc) {
        wacAutobcc__ = wacAutobcc;
    }

    /**
     * <p>get WAC_DELRECEIVE value
     * @return WAC_DELRECEIVE value
     */
    public int getWacDelreceive() {
        return wacDelreceive__;
    }

    /**
     * <p>set WAC_DELRECEIVE value
     * @param wacDelreceive WAC_DELRECEIVE value
     */
    public void setWacDelreceive(int wacDelreceive) {
        wacDelreceive__ = wacDelreceive;
    }

    /**
     * <p>get WAC_RERECEIVE value
     * @return WAC_RERECEIVE value
     */
    public int getWacRereceive() {
        return wacRereceive__;
    }

    /**
     * <p>set WAC_RERECEIVE value
     * @param wacRereceive WAC_RERECEIVE value
     */
    public void setWacRereceive(int wacRereceive) {
        wacRereceive__ = wacRereceive;
    }

    /**
     * <p>get WAC_APOP value
     * @return WAC_APOP value
     */
    public int getWacApop() {
        return wacApop__;
    }

    /**
     * <p>set WAC_APOP value
     * @param wacApop WAC_APOP value
     */
    public void setWacApop(int wacApop) {
        wacApop__ = wacApop;
    }

    /**
     * <p>get WAC_TOP_CMD value
     * @return WAC_TOP_CMD value
     */
    public int getWacTopCmd() {
        return wacTopCmd__;
    }

    /**
     * <p>set WAC_TOP_CMD value
     * @param wacTopCmd WAC_TOP_CMD value
     */
    public void setWacTopCmd(int wacTopCmd) {
        wacTopCmd__ = wacTopCmd;
    }

    /**
     * <p>get WAC_SMTP_AUTH value
     * @return WAC_SMTP_AUTH value
     */
    public int getWacSmtpAuth() {
        return wacSmtpAuth__;
    }

    /**
     * <p>set WAC_SMTP_AUTH value
     * @param wacSmtpAuth WAC_SMTP_AUTH value
     */
    public void setWacSmtpAuth(int wacSmtpAuth) {
        wacSmtpAuth__ = wacSmtpAuth;
    }

    /**
     * <p>get WAC_POPBSMTP value
     * @return WAC_POPBSMTP value
     */
    public int getWacPopbsmtp() {
        return wacPopbsmtp__;
    }

    /**
     * <p>set WAC_POPBSMTP value
     * @param wacPopbsmtp WAC_POPBSMTP value
     */
    public void setWacPopbsmtp(int wacPopbsmtp) {
        wacPopbsmtp__ = wacPopbsmtp;
    }

    /**
     * <p>get WAC_ENCODE_SEND value
     * @return WAC_ENCODE_SEND value
     */
    public int getWacEncodeSend() {
        return wacEncodeSend__;
    }

    /**
     * <p>set WAC_ENCODE_SEND value
     * @param wacEncodeSend WAC_ENCODE_SEND value
     */
    public void setWacEncodeSend(int wacEncodeSend) {
        wacEncodeSend__ = wacEncodeSend;
    }

    /**
     * <p>get WAC_AUTORECEIVE value
     * @return WAC_AUTORECEIVE value
     */
    public int getWacAutoreceive() {
        return wacAutoreceive__;
    }

    /**
     * <p>set WAC_AUTORECEIVE value
     * @param wacAutoreceive WAC_AUTORECEIVE value
     */
    public void setWacAutoreceive(int wacAutoreceive) {
        wacAutoreceive__ = wacAutoreceive;
    }

    /**
     * <p>get WAC_SEND_MAILTYPE value
     * @return WAC_SEND_MAILTYPE value
     */
    public int getWacSendMailtype() {
        return wacSendMailtype__;
    }

    /**
     * <p>set WAC_SEND_MAILTYPE value
     * @param wacSendMailtype WAC_SEND_MAILTYPE value
     */
    public void setWacSendMailtype(int wacSendMailtype) {
        wacSendMailtype__ = wacSendMailtype;
    }

    /**
     * <p>get WAC_RECEIVE_DATE value
     * @return WAC_RECEIVE_DATE value
     */
    public UDate getWacReceiveDate() {
        return wacReceiveDate__;
    }

    /**
     * <p>set WAC_RECEIVE_DATE value
     * @param wacReceiveDate WAC_RECEIVE_DATE value
     */
    public void setWacReceiveDate(UDate wacReceiveDate) {
        wacReceiveDate__ = wacReceiveDate;
    }

    /**
     * <p>get WAC_JKBN value
     * @return WAC_JKBN value
     */
    public int getWacJkbn() {
        return wacJkbn__;
    }

    /**
     * <p>set WAC_JKBN value
     * @param wacJkbn WAC_JKBN value
     */
    public void setWacJkbn(int wacJkbn) {
        wacJkbn__ = wacJkbn;
    }

    /**
     * <p>get WAC_AUTO_RECEIVE_TIME value
     * @return WAC_AUTO_RECEIVE_TIME value
     */
    public int getWacAutoReceiveTime() {
        return wacAutoReceiveTime__;
    }

    /**
     * <p>set WAC_AUTO_RECEIVE_TIME value
     * @param wacAutoReceiveTime WAC_AUTO_RECEIVE_TIME value
     */
    public void setWacAutoReceiveTime(int wacAutoReceiveTime) {
        wacAutoReceiveTime__ = wacAutoReceiveTime;
    }

    /**
     * <p>get WAC_THEME value
     * @return WAC_THEME value
     */
    public int getWacTheme() {
        return wacTheme__;
    }

    /**
     * <p>set WAC_THEME value
     * @param wacTheme WAC_THEME value
     */
    public void setWacTheme(int wacTheme) {
        wacTheme__ = wacTheme;
    }

    /**
     * <p>get WAC_CHECK_ADDRESS value
     * @return WAC_CHECK_ADDRESS value
     */
    public int getWacCheckAddress() {
        return wacCheckAddress__;
    }

    /**
     * <p>set WAC_CHECK_ADDRESS value
     * @param wacCheckAddress WAC_CHECK_ADDRESS value
     */
    public void setWacCheckAddress(int wacCheckAddress) {
        wacCheckAddress__ = wacCheckAddress;
    }

    /**
     * <p>get WAC_CHECK_FILE value
     * @return WAC_CHECK_FILE value
     */
    public int getWacCheckFile() {
        return wacCheckFile__;
    }

    /**
     * <p>set WAC_CHECK_FILE value
     * @param wacCheckFile WAC_CHECK_FILE value
     */
    public void setWacCheckFile(int wacCheckFile) {
        wacCheckFile__ = wacCheckFile;
    }

    /**
     * <p>get WAC_COMPRESS_FILE value
     * @return WAC_COMPRESS_FILE value
     */
    public int getWacCompressFile() {
        return wacCompressFile__;
    }

    /**
     * <p>set WAC_COMPRESS_FILE value
     * @param wacCompressFile WAC_COMPRESS_FILE value
     */
    public void setWacCompressFile(int wacCompressFile) {
        wacCompressFile__ = wacCompressFile;
    }

    /**
     * <p>get WAC_TIMESENT value
     * @return WAC_TIMESENT value
     */
    public int getWacTimesent() {
        return wacTimesent__;
    }

    /**
     * <p>set WAC_TIMESENT value
     * @param wacTimesent WAC_TIMESENT value
     */
    public void setWacTimesent(int wacTimesent) {
        wacTimesent__ = wacTimesent;
    }

    /**
     * <p>get WAC_QUOTES value
     * @return WAC_QUOTES value
     */
    public int getWacQuotes() {
        return wacQuotes__;
    }

    /**
     * <p>set WAC_QUOTES value
     * @param wacQuotes WAC_QUOTES value
     */
    public void setWacQuotes(int wacQuotes) {
        wacQuotes__ = wacQuotes;
    }

    /**
     * <p>get WAC_DISK_SPS value
     * @return WAC_DISK_SPS value
     */
    public int getWacDiskSps() {
        return wacDiskSps__;
    }

    /**
     * <p>set WAC_DISK_SPS value
     * @param wacDiskSps WAC_DISK_SPS value
     */
    public void setWacDiskSps(int wacDiskSps) {
        wacDiskSps__ = wacDiskSps;
    }

    /**
     * <p>get WAC_AUTORECEIVE_AP value
     * @return WAC_AUTORECEIVE_AP value
     */
    public int getWacAutoreceiveAp() {
        return wacAutoreceiveAp__;
    }

    /**
     * <p>set WAC_AUTORECEIVE_AP value
     * @param wacAutoreceiveAp WAC_AUTORECEIVE_AP value
     */
    public void setWacAutoreceiveAp(int wacAutoreceiveAp) {
        wacAutoreceiveAp__ = wacAutoreceiveAp;
    }

    /**
     * <p>get WAC_TIMESENT_DEF value
     * @return WAC_TIMESENT_DEF value
     */
    public int getWacTimesentDef() {
        return wacTimesentDef__;
    }

    /**
     * <p>set WAC_TIMESENT_DEF value
     * @param wacTimesentDef WAC_TIMESENT_DEF value
     */
    public void setWacTimesentDef(int wacTimesentDef) {
        wacTimesentDef__ = wacTimesentDef;
    }

    /**
     * <p>get WAC_COMPRESS_FILE_DEF value
     * @return WAC_COMPRESS_FILE_DEF value
     */
    public int getWacCompressFileDef() {
        return wacCompressFileDef__;
    }

    /**
     * <p>set WAC_COMPRESS_FILE_DEF value
     * @param wacCompressFileDef WAC_COMPRESS_FILE_DEF value
     */
    public void setWacCompressFileDef(int wacCompressFileDef) {
        wacCompressFileDef__ = wacCompressFileDef;
    }

    /**
     * <p>get WAC_SIGN_AUTO value
     * @return WAC_SIGN_AUTO value
     */
    public int getWacSignAuto() {
        return wacSignAuto__;
    }

    /**
     * <p>set WAC_SIGN_AUTO value
     * @param wacSignAuto WAC_SIGN_AUTO value
     */
    public void setWacSignAuto(int wacSignAuto) {
        wacSignAuto__ = wacSignAuto;
    }

    /**
     * <p>get WAC_AUTO_SAVE_MINUTE value
     * @return WAC_AUTO_SAVE_MINUTE value
     */
    public int getWacAutoSaveMinute() {
        return wacAutoSaveMinute__;
    }

    /**
     * <p>set WAC_AUTO_SAVE_MINUTE value
     * @param wacAutoSaveMinute WAC_AUTO_SAVE_MINUTE value
     */
    public void setWacAutoSaveMinute(int wacAutoSaveMinute) {
        wacAutoSaveMinute__ = wacAutoSaveMinute;
    }

    /**
     * <p>get WAC_AUTH_TYPE value
     * @return WAC_AUTH_TYPE value
     */
    public int getWacAuthType() {
        return wacAuthType__;
    }

    /**
     * <p>set WAC_AUTH_TYPE value
     * @param wacAuthType WAC_AUTH_TYPE value
     */
    public void setWacAuthType(int wacAuthType) {
        wacAuthType__ = wacAuthType;
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
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wacType__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wacName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacAddress__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacSendHost__, ""));
        buf.append(",");
        buf.append(wacSendPort__);
        buf.append(",");
        buf.append(NullDefault.getString(wacSendUser__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacSendPass__, ""));
        buf.append(",");
        buf.append(wacSendSsl__);
        buf.append(",");
        buf.append(wacReceiveType__);
        buf.append(",");
        buf.append(NullDefault.getString(wacReceiveHost__, ""));
        buf.append(",");
        buf.append(wacReceivePort__);
        buf.append(",");
        buf.append(NullDefault.getString(wacReceiveUser__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacReceivePass__, ""));
        buf.append(",");
        buf.append(wacReceiveSsl__);
        buf.append(",");
        buf.append(wacDisk__);
        buf.append(",");
        buf.append(wacDiskSize__);
        buf.append(",");
        buf.append(NullDefault.getString(wacBiko__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacOrganization__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacSign__, ""));
        buf.append(",");
        buf.append(wacSignPointKbn__);
        buf.append(",");
        buf.append(wacSignDspKbn__);
        buf.append(",");
        buf.append(NullDefault.getString(wacAutoto__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacAutocc__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wacAutobcc__, ""));
        buf.append(",");
        buf.append(wacDelreceive__);
        buf.append(",");
        buf.append(wacRereceive__);
        buf.append(",");
        buf.append(wacApop__);
        buf.append(",");
        buf.append(wacTopCmd__);
        buf.append(",");
        buf.append(wacSmtpAuth__);
        buf.append(",");
        buf.append(wacPopbsmtp__);
        buf.append(",");
        buf.append(wacEncodeSend__);
        buf.append(",");
        buf.append(wacAutoreceive__);
        buf.append(",");
        buf.append(wacSendMailtype__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wacReceiveDate__, ""));
        buf.append(",");
        buf.append(wacJkbn__);
        buf.append(",");
        buf.append(wacAutoReceiveTime__);
        buf.append(",");
        buf.append(wacTheme__);
        buf.append(",");
        buf.append(wacCheckAddress__);
        buf.append(",");
        buf.append(wacCheckFile__);
        buf.append(",");
        buf.append(wacCompressFile__);
        buf.append(",");
        buf.append(wacTimesent__);
        buf.append(",");
        buf.append(wacQuotes__);
        buf.append(",");
        buf.append(wacDiskSps__);
        buf.append(",");
        buf.append(wacAutoreceiveAp__);
        buf.append(",");
        buf.append(wacTimesentDef__);
        buf.append(",");
        buf.append(wacCompressFileDef__);
        buf.append(",");
        buf.append(wacSignAuto__);
        buf.append(",");
        buf.append(wacAuthType__);
        buf.append(",");
        buf.append(wacAutoSaveMinute__);
        buf.append(",");
        buf.append(cotSid__);

        return buf.toString();
    }

}
