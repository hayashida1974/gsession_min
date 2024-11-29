package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAdmConfModel implements Serializable {

    /** WAD_ACNT_MAKE mapping */
    private int wadAcntMake__;
    /** WAD_ACCT_SENDFORMAT mapping */
    private int wadAcctSendformat__;
    /** WAD_ACCT_LOG_REGIST mapping */
    private int wadAcctLogRegist__;
    /** WAD_PERMIT_KBN mapping */
    private int wadPermitKbn__;
    /** WAD_AUTO_RECEIVE_TIME mapping */
    private int wadAutoReceiveTime__;
    /** WAD_DISK mapping */
    private int wadDisk__;
    /** WAD_DISK_SIZE mapping */
    private int wadDiskSize__;
    /** WAD_DISK_COMP mapping */
    private int wadDiskComp__;
    /** WAD_DELRECEIVE mapping */
    private int wadDelreceive__;
    /** WAD_AUTORECEIVE mapping */
    private int wadAutoreceive__;
    /** WAD_SEND_HOST mapping */
    private String wadSendHost__;
    /** WAD_SEND_PORT mapping */
    private int wadSendPort__;
    /** WAD_SEND_SSL mapping */
    private int wadSendSsl__;
    /** WAD_RECEIVE_HOST mapping */
    private String wadReceiveHost__;
    /** WAD_RECEIVE_PORT mapping */
    private int wadReceivePort__;
    /** WAD_RECEIVE_SSL mapping */
    private int wadReceiveSsl__;
    /** WAD_CHECK_ADDRESS mapping */
    private int wadCheckAddress__;
    /** WAD_CHECK_FILE mapping */
    private int wadCheckFile__;
    /** WAD_COMPRESS_FILE mapping */
    private int wadCompressFile__;
    /** WAD_TIMESENT mapping */
    private int wadTimesent__;
    /** WAD_SEND_LIMIT mapping */
    private int wadSendLimit__;
    /** WAD_SEND_LIMIT_SIZE mapping */
    private int wadSendLimitSize__;
    /** WAD_FWLIMIT mapping */
    private int wadFwlimit__;
    /** WAD_BCC mapping */
    private int wadBcc__;
    /** WAD_BCC_TH mapping */
    private int wadBccTh__;
    /** WAD_WARN_DISK mapping */
    private int wadWarnDisk__;
    /** WAD_WARN_DISK_TH mapping */
    private int wadWarnDiskTh__;
    /** WAD_SETTING_SERVER mapping */
    private int wadSettingServer__;
    /** WAD_PROXY_USER mapping */
    private int wadProxyUser__;
    /** WAD_COMPRESS_FILE_DEF mapping */
    private int wadCompressFileDef__;
    /** WAD_TIMESENT_DEF mapping */
    private int wadTimesentDef__;
    /** WAD_LINKLIMIT mapping*/
    private int wadlinkLimit__;
    /** WAD_TLD_LIMIT mapping*/
    private int wadTldLimit__;
    /** WAD_TLD_LIMITLIST mapping */
    private String wadTldLimitText__;

    /**
     * <p>Default Constructor
     */
    public WmlAdmConfModel() {
    }

    /**
     * <p>get WAD_ACNT_MAKE value
     * @return WAD_ACNT_MAKE value
     */
    public int getWadAcntMake() {
        return wadAcntMake__;
    }

    /**
     * <p>set WAD_ACNT_MAKE value
     * @param wadAcntMake WAD_ACNT_MAKE value
     */
    public void setWadAcntMake(int wadAcntMake) {
        wadAcntMake__ = wadAcntMake;
    }

    /**
     * <p>get WAD_ACCT_SENDFORMAT value
     * @return WAD_ACCT_SENDFORMAT value
     */
    public int getWadAcctSendformat() {
        return wadAcctSendformat__;
    }

    /**
     * <p>set WAD_ACCT_SENDFORMAT value
     * @param wadAcctSendformat WAD_ACCT_SENDFORMAT value
     */
    public void setWadAcctSendformat(int wadAcctSendformat) {
        wadAcctSendformat__ = wadAcctSendformat;
    }

    /**
     * <p>get WAD_ACCT_LOG_REGIST value
     * @return WAD_ACCT_LOG_REGIST value
     */
    public int getWadAcctLogRegist() {
        return wadAcctLogRegist__;
    }

    /**
     * <p>set WAD_ACCT_LOG_REGIST value
     * @param wadAcctLogRegist WAD_ACCT_LOG_REGIST value
     */
    public void setWadAcctLogRegist(int wadAcctLogRegist) {
        wadAcctLogRegist__ = wadAcctLogRegist;
    }

    /**
     * <p>get WAD_PERMIT_KBN value
     * @return WAD_PERMIT_KBN value
     */
    public int getWadPermitKbn() {
        return wadPermitKbn__;
    }

    /**
     * <p>set WAD_PERMIT_KBN value
     * @param wadPermitKbn WAD_PERMIT_KBN value
     */
    public void setWadPermitKbn(int wadPermitKbn) {
        wadPermitKbn__ = wadPermitKbn;
    }

    /**
     * <p>get WAD_AUTO_RECEIVE_TIME value
     * @return WAD_AUTO_RECEIVE_TIME value
     */
    public int getWadAutoReceiveTime() {
        return wadAutoReceiveTime__;
    }

    /**
     * <p>set WAD_AUTO_RECEIVE_TIME value
     * @param wadAutoReceiveTime WAD_AUTO_RECEIVE_TIME value
     */
    public void setWadAutoReceiveTime(int wadAutoReceiveTime) {
        wadAutoReceiveTime__ = wadAutoReceiveTime;
    }

    /**
     * <p>get WAD_DISK value
     * @return WAD_DISK value
     */
    public int getWadDisk() {
        return wadDisk__;
    }

    /**
     * <p>set WAD_DISK value
     * @param wadDisk WAD_DISK value
     */
    public void setWadDisk(int wadDisk) {
        wadDisk__ = wadDisk;
    }

    /**
     * <p>get WAD_DISK_SIZE value
     * @return WAD_DISK_SIZE value
     */
    public int getWadDiskSize() {
        return wadDiskSize__;
    }

    /**
     * <p>set WAD_DISK_SIZE value
     * @param wadDiskSize WAD_DISK_SIZE value
     */
    public void setWadDiskSize(int wadDiskSize) {
        wadDiskSize__ = wadDiskSize;
    }

    /**
     * <p>get WAD_DISK_COMP value
     * @return WAD_DISK_COMP value
     */
    public int getWadDiskComp() {
        return wadDiskComp__;
    }

    /**
     * <p>set WAD_DISK_COMP value
     * @param wadDiskComp WAD_DISK_COMP value
     */
    public void setWadDiskComp(int wadDiskComp) {
        wadDiskComp__ = wadDiskComp;
    }

    /**
     * <p>get WAD_DELRECEIVE value
     * @return WAD_DELRECEIVE value
     */
    public int getWadDelreceive() {
        return wadDelreceive__;
    }

    /**
     * <p>set WAD_DELRECEIVE value
     * @param wadDelreceive WAD_DELRECEIVE value
     */
    public void setWadDelreceive(int wadDelreceive) {
        wadDelreceive__ = wadDelreceive;
    }

    /**
     * <p>get WAD_AUTORECEIVE value
     * @return WAD_AUTORECEIVE value
     */
    public int getWadAutoreceive() {
        return wadAutoreceive__;
    }

    /**
     * <p>set WAD_AUTORECEIVE value
     * @param wadAutoreceive WAD_AUTORECEIVE value
     */
    public void setWadAutoreceive(int wadAutoreceive) {
        wadAutoreceive__ = wadAutoreceive;
    }

    /**
     * <p>get WAD_SEND_HOST value
     * @return WAD_SEND_HOST value
     */
    public String getWadSendHost() {
        return wadSendHost__;
    }

    /**
     * <p>set WAD_SEND_HOST value
     * @param wadSendHost WAD_SEND_HOST value
     */
    public void setWadSendHost(String wadSendHost) {
        wadSendHost__ = wadSendHost;
    }

    /**
     * <p>get WAD_SEND_PORT value
     * @return WAD_SEND_PORT value
     */
    public int getWadSendPort() {
        return wadSendPort__;
    }

    /**
     * <p>set WAD_SEND_PORT value
     * @param wadSendPort WAD_SEND_PORT value
     */
    public void setWadSendPort(int wadSendPort) {
        wadSendPort__ = wadSendPort;
    }

    /**
     * <p>get WAD_SEND_SSL value
     * @return WAD_SEND_SSL value
     */
    public int getWadSendSsl() {
        return wadSendSsl__;
    }

    /**
     * <p>set WAD_SEND_SSL value
     * @param wadSendSsl WAD_SEND_SSL value
     */
    public void setWadSendSsl(int wadSendSsl) {
        wadSendSsl__ = wadSendSsl;
    }

    /**
     * <p>get WAD_RECEIVE_HOST value
     * @return WAD_RECEIVE_HOST value
     */
    public String getWadReceiveHost() {
        return wadReceiveHost__;
    }

    /**
     * <p>set WAD_RECEIVE_HOST value
     * @param wadReceiveHost WAD_RECEIVE_HOST value
     */
    public void setWadReceiveHost(String wadReceiveHost) {
        wadReceiveHost__ = wadReceiveHost;
    }

    /**
     * <p>get WAD_RECEIVE_PORT value
     * @return WAD_RECEIVE_PORT value
     */
    public int getWadReceivePort() {
        return wadReceivePort__;
    }

    /**
     * <p>set WAD_RECEIVE_PORT value
     * @param wadReceivePort WAD_RECEIVE_PORT value
     */
    public void setWadReceivePort(int wadReceivePort) {
        wadReceivePort__ = wadReceivePort;
    }

    /**
     * <p>get WAD_RECEIVE_SSL value
     * @return WAD_RECEIVE_SSL value
     */
    public int getWadReceiveSsl() {
        return wadReceiveSsl__;
    }

    /**
     * <p>set WAD_RECEIVE_SSL value
     * @param wadReceiveSsl WAD_RECEIVE_SSL value
     */
    public void setWadReceiveSsl(int wadReceiveSsl) {
        wadReceiveSsl__ = wadReceiveSsl;
    }

    /**
     * <p>get WAD_CHECK_ADDRESS value
     * @return WAD_CHECK_ADDRESS value
     */
    public int getWadCheckAddress() {
        return wadCheckAddress__;
    }

    /**
     * <p>set WAD_CHECK_ADDRESS value
     * @param wadCheckAddress WAD_CHECK_ADDRESS value
     */
    public void setWadCheckAddress(int wadCheckAddress) {
        wadCheckAddress__ = wadCheckAddress;
    }

    /**
     * <p>get WAD_CHECK_FILE value
     * @return WAD_CHECK_FILE value
     */
    public int getWadCheckFile() {
        return wadCheckFile__;
    }

    /**
     * <p>set WAD_CHECK_FILE value
     * @param wadCheckFile WAD_CHECK_FILE value
     */
    public void setWadCheckFile(int wadCheckFile) {
        wadCheckFile__ = wadCheckFile;
    }

    /**
     * <p>get WAD_COMPRESS_FILE value
     * @return WAD_COMPRESS_FILE value
     */
    public int getWadCompressFile() {
        return wadCompressFile__;
    }

    /**
     * <p>set WAD_COMPRESS_FILE value
     * @param wadCompressFile WAD_COMPRESS_FILE value
     */
    public void setWadCompressFile(int wadCompressFile) {
        wadCompressFile__ = wadCompressFile;
    }

    /**
     * <p>get WAD_TIMESENT value
     * @return WAD_TIMESENT value
     */
    public int getWadTimesent() {
        return wadTimesent__;
    }

    /**
     * <p>set WAD_TIMESENT value
     * @param wadTimesent WAD_TIMESENT value
     */
    public void setWadTimesent(int wadTimesent) {
        wadTimesent__ = wadTimesent;
    }

    /**
     * <p>get WAD_SEND_LIMIT value
     * @return WAD_SEND_LIMIT value
     */
    public int getWadSendLimit() {
        return wadSendLimit__;
    }

    /**
     * <p>set WAD_SEND_LIMIT value
     * @param wadSendLimit WAD_SEND_LIMIT value
     */
    public void setWadSendLimit(int wadSendLimit) {
        wadSendLimit__ = wadSendLimit;
    }

    /**
     * <p>get WAD_SEND_LIMIT_SIZE value
     * @return WAD_SEND_LIMIT_SIZE value
     */
    public int getWadSendLimitSize() {
        return wadSendLimitSize__;
    }

    /**
     * <p>set WAD_SEND_LIMIT_SIZE value
     * @param wadSendLimitSize WAD_SEND_LIMIT_SIZE value
     */
    public void setWadSendLimitSize(int wadSendLimitSize) {
        wadSendLimitSize__ = wadSendLimitSize;
    }

    /**
     * <p>get WAD_FWLIMIT value
     * @return WAD_FWLIMIT value
     */
    public int getWadFwlimit() {
        return wadFwlimit__;
    }

    /**
     * <p>set WAD_FWLIMIT value
     * @param wadFwlimit WAD_FWLIMIT value
     */
    public void setWadFwlimit(int wadFwlimit) {
        wadFwlimit__ = wadFwlimit;
    }

    /**
     * <p>get WAD_BCC value
     * @return WAD_BCC value
     */
    public int getWadBcc() {
        return wadBcc__;
    }

    /**
     * <p>set WAD_BCC value
     * @param wadBcc WAD_BCC value
     */
    public void setWadBcc(int wadBcc) {
        wadBcc__ = wadBcc;
    }

    /**
     * <p>get WAD_BCC_TH value
     * @return WAD_BCC_TH value
     */
    public int getWadBccTh() {
        return wadBccTh__;
    }

    /**
     * <p>set WAD_BCC_TH value
     * @param wadBccTh WAD_BCC_TH value
     */
    public void setWadBccTh(int wadBccTh) {
        wadBccTh__ = wadBccTh;
    }

    /**
     * <p>get WAD_WARN_DISK value
     * @return WAD_WARN_DISK value
     */
    public int getWadWarnDisk() {
        return wadWarnDisk__;
    }

    /**
     * <p>set WAD_WARN_DISK value
     * @param wadWarnDisk WAD_WARN_DISK value
     */
    public void setWadWarnDisk(int wadWarnDisk) {
        wadWarnDisk__ = wadWarnDisk;
    }

    /**
     * <p>get WAD_WARN_DISK_TH value
     * @return WAD_WARN_DISK_TH value
     */
    public int getWadWarnDiskTh() {
        return wadWarnDiskTh__;
    }

    /**
     * <p>set WAD_WARN_DISK_TH value
     * @param wadWarnDiskTh WAD_WARN_DISK_TH value
     */
    public void setWadWarnDiskTh(int wadWarnDiskTh) {
        wadWarnDiskTh__ = wadWarnDiskTh;
    }

    /**
     * <p>get WAD_SETTING_SERVER value
     * @return WAD_SETTING_SERVER value
     */
    public int getWadSettingServer() {
        return wadSettingServer__;
    }

    /**
     * <p>set WAD_SETTING_SERVER value
     * @param wadSettingServer WAD_SETTING_SERVER value
     */
    public void setWadSettingServer(int wadSettingServer) {
        wadSettingServer__ = wadSettingServer;
    }

    /**
     * <p>get WAD_PROXY_USER value
     * @return WAD_PROXY_USER value
     */
    public int getWadProxyUser() {
        return wadProxyUser__;
    }

    /**
     * <p>set WAD_PROXY_USER value
     * @param wadProxyUser WAD_PROXY_USER value
     */
    public void setWadProxyUser(int wadProxyUser) {
        wadProxyUser__ = wadProxyUser;
    }

    /**
     * <p>get WAD_COMPRESS_FILE_DEF value
     * @return WAD_COMPRESS_FILE_DEF value
     */
    public int getWadCompressFileDef() {
        return wadCompressFileDef__;
    }

    /**
     * <p>set WAD_COMPRESS_FILE_DEF value
     * @param wadCompressFileDef WAD_COMPRESS_FILE_DEF value
     */
    public void setWadCompressFileDef(int wadCompressFileDef) {
        wadCompressFileDef__ = wadCompressFileDef;
    }

    /**
     * <p>get WAD_TIMESENT_DEF value
     * @return WAD_TIMESENT_DEF value
     */
    public int getWadTimesentDef() {
        return wadTimesentDef__;
    }

    /**
     * <p>set WAD_TIMESENT_DEF value
     * @param wadTimesentDef WAD_TIMESENT_DEF value
     */
    public void setWadTimesentDef(int wadTimesentDef) {
        wadTimesentDef__ = wadTimesentDef;
    }

    /**
     * <p>get WAD_LINK_LIMIT value
     * @return WAD_LINK_LIMIT value
     */
    public int getWadlinkLimit() {
        return wadlinkLimit__;
    }

    /**
     * <p>set WAD_LINK_LIMIT value
     * @param wadlinkLimit WAD_LINK_LIMIT value
     */
    public void setWadlinkLimit(int wadlinkLimit) {
        wadlinkLimit__ = wadlinkLimit;
    }

    /**
     * <p>get WAD_TLD_LIMIT value
     * @return WAD_TLD_LIMIT value
     */
    public int getWadTldLimit() {
        return wadTldLimit__;
    }

    /**
     * <p>set WAD_TLD_LIMIT value
     * @param wadTldLimit WAD_TLD_LIMIT value
     */
    public void setWadTldLimit(int wadTldLimit) {
        wadTldLimit__ = wadTldLimit;
    }

    /**
     * <p>get WAD_TLD_LIMIT_TEXT value
     * @return WAD_TLD_LIMIT_TEXT value
     */
    public String getWadTldLimitText() {
        return wadTldLimitText__;
    }

    /**
     * <p>set WAD_TLD_LIMIT_TEXT value
     * @param wadTldLimitText WAD_TLD_LIMIT value
     */
    public void setWadTldLimitText(String wadTldLimitText) {
        wadTldLimitText__ = wadTldLimitText;
    }

   /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wadAcntMake__);
        buf.append(",");
        buf.append(wadAcctSendformat__);
        buf.append(",");
        buf.append(wadAcctLogRegist__);
        buf.append(",");
        buf.append(wadPermitKbn__);
        buf.append(",");
        buf.append(wadAutoReceiveTime__);
        buf.append(",");
        buf.append(wadDisk__);
        buf.append(",");
        buf.append(wadDiskSize__);
        buf.append(",");
        buf.append(wadDiskComp__);
        buf.append(",");
        buf.append(wadDelreceive__);
        buf.append(",");
        buf.append(wadAutoreceive__);
        buf.append(",");
        buf.append(NullDefault.getString(wadSendHost__, ""));
        buf.append(",");
        buf.append(wadSendPort__);
        buf.append(",");
        buf.append(wadSendSsl__);
        buf.append(",");
        buf.append(NullDefault.getString(wadReceiveHost__, ""));
        buf.append(",");
        buf.append(wadReceivePort__);
        buf.append(",");
        buf.append(wadReceiveSsl__);
        buf.append(",");
        buf.append(wadCheckAddress__);
        buf.append(",");
        buf.append(wadCheckFile__);
        buf.append(",");
        buf.append(wadCompressFile__);
        buf.append(",");
        buf.append(wadTimesent__);
        buf.append(",");
        buf.append(wadSendLimit__);
        buf.append(",");
        buf.append(wadSendLimitSize__);
        buf.append(",");
        buf.append(wadFwlimit__);
        buf.append(",");
        buf.append(wadBcc__);
        buf.append(",");
        buf.append(wadBccTh__);
        buf.append(",");
        buf.append(wadWarnDisk__);
        buf.append(",");
        buf.append(wadWarnDiskTh__);
        buf.append(",");
        buf.append(wadSettingServer__);
        buf.append(",");
        buf.append(wadProxyUser__);
        buf.append(",");
        buf.append(wadCompressFileDef__);
        buf.append(",");
        buf.append(wadTimesentDef__);
        return buf.toString();
    }

}
