package jp.groupsession.v2.man.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>FRE_SYSINFO Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class FreSysinfoModel implements Serializable {

    /** FLI_SID mapping */
    private int fliSid__;
    /** FLI_GSSN mapping */
    private String fliGssn__;
    /** FLI_LIID mapping */
    private String fliLiid__;
    /** FSI_KBN mapping */
    private int fsiKbn__;
    /** FSI_ADATE mapping */
    private UDate fsiAdate__;
    /** FSI_CAMPANY mapping */
    private String fsiCampany__;
    /** FSI_SPLIMIT mapping */
    private UDate fsiSplimit__;
    /** FSI_MBLIMIT mapping */
    private UDate fsiMblimit__;
    /** FSI_CRLIMIT mapping */
    private UDate fsiCrlimit__;
    /** FSI_USERS mapping */
    private int fsiUsers__;
    /** FSI_SDATE mapping */
    private UDate fsiSdate__;
    /** FSI_OLDVER mapping */
    private String fsiOldver__;
    /** FSI_GSVER mapping */
    private String fsiGsver__;
    /** FSI_OSNAME mapping */
    private String fsiOsname__;
    /** FSI_CPUCORE mapping */
    private String fsiCpucore__;
    /** FSI_JVMBIT mapping */
    private String fsiJvmbit__;
    /** FSI_JAVAVER mapping */
    private String fsiJavaver__;
    /** FSI_USEDMEM mapping */
    private String fsiUsedmem__;
    /** FSI_MAXMEM mapping */
    private String fsiMaxmem__;
    /** FSI_ADISC mapping */
    private String fsiAdisc__;
    /** FSI_CON mapping */
    private String fsiCon__;
    /** FSI_H2_LOCK_MODE mapping */
    private int fsiH2LockMode__;
    /** FSI_H2_LOCK_TIMEOUT mapping */
    private int fsiH2LockTimeout__;
    /** FSI_H2_DEFAULT_LOCK_TIMEOUT mapping */
    private int fsiH2DefaultLockTimeout__;
    /** FSI_H2_MULTI_THREADED mapping */
    private int fsiH2MultiThreaded__;
    /** FSI_H2_IFEXISTS mapping */
    private String fsiH2Ifexists__;
    /** FSI_H2_AUTOCOMMIT mapping */
    private String fsiH2Autocommit__;
    /** FSI_H2_DB_CLOSE_ON_EXIT mapping */
    private String fsiH2DbCloseOnExit__;
    /** FSI_H2_CACHE_SIZE mapping */
    private int fsiH2CacheSize__;
    /** FSI_H2_PAGE_SIZE mapping */
    private int fsiH2PageSize__;
    /** FSI_H2_MAX_LENGTH_INPLACE_LOB mapping */
    private int fsiH2MaxLengthInplaceLob__;
    /** FSI_H2_CACHE_TYPE mapping */
    private String fsiH2CacheType__;
    /** FSI_H2_MVCC mapping */
    private String fsiH2Mvcc__;
    /** FSI_H2_QUERY_TIMEOUT mapping */
    private int fsiH2QueryTimeout__;
    /** FSI_H2_QUERY_TIMEOUT_BATCH mapping */
    private int fsiH2QueryTimeoutBatch__;
    /** FSI_H2_MAX_LOG_SIZE mapping */
    private int fsiH2MaxLogSize__;
    /** FSI_GSDATA_DIR mapping */
    private String fsiGsdataDir__;
    /** FSI_BACKUP_DIR mapping */
    private String fsiBackupDir__;
    /** FSI_FILEKANRI_DIR mapping */
    private String fsiFilekanriDir__;
    /** FSI_WEBMAIL_DIR mapping */
    private String fsiWebmailDir__;
    /** FSI_BACKUP_INTERVAL mapping */
    private String fsiBackupInterval__;
    /** FSI_BACKUP_GENERATION mapping */
    private String fsiBackupGeneration__;
    /** FSI_BACKUP_ZIP mapping */
    private String fsiBackupZip__;
    /** FSI_BATCHJOB mapping */
    private String fsiBatchjob__;
    /** FSI_TEMPDIR mapping */
    private String fsiTempdir__;
    /** FSI_COMPLIANT_VERSION mapping */
    private String fsiCompliantVersion__;
    /** FSI_GS_MBA_IOS_VERSION mapping */
    private String fsiGsMbaIosVersion__;
    /** FSI_GS_CAL_IOS_VERSION mapping */
    private String fsiGsCalIosVersion__;
    /** FSI_GS_ADR_IOS_VERSION mapping */
    private String fsiGsAdrIosVersion__;
    /** FSI_GS_SML_IOS_VERSION mapping */
    private String fsiGsSmlIosVersion__;
    /** FSI_GS_WML_IOS_VERSION mapping */
    private String fsiGsWmlIosVersion__;
    /** FSI_FIL_ALL_SEARCH_USE mapping */
    private int fsiFilAllSearchUse__;
    /** FSI_MAIL_PORT_NUMBER mapping */
    private int fsiMailPortNumber__;
    /** FSI_PORTLET_MAXLENGTH mapping */
    private int fsiPortletMaxlength__;
    /** FSI_RSV_PRINT_USE mapping */
    private int fsiRsvPrintUse__;
    /** FSI_MAILRECEIVE_THREAD_MAXCOUNT mapping */
    private int fsiMailreceiveThreadMaxcount__;
    /** FSI_MAILRECEIVE_MAXCOUNT mapping */
    private int fsiMailreceiveMaxcount__;
    /** FSI_MAILRECEIVE_CONNECTTIMEOUT mapping */
    private int fsiMailreceiveConnecttimeout__;
    /** FSI_MAILRECEIVE_TIMEOUT mapping */
    private int fsiMailreceiveTimeout__;
    /** FSI_MAILRECEIVE_RCVSVR_CHECKTIME mapping */
    private int fsiMailreceiveRcvsvrChecktime__;
    /** FSI_MAILBODY_LIMIT mapping */
    private int fsiMailbodyLimit__;

    /**
     * <p>Default Constructor
     */
    public FreSysinfoModel() {
    }

    /**
     * <p>get FLI_SID value
     * @return FLI_SID value
     */
    public int getFliSid() {
        return fliSid__;
    }

    /**
     * <p>set FLI_SID value
     * @param fliSid FLI_SID value
     */
    public void setFliSid(int fliSid) {
        fliSid__ = fliSid;
    }

    /**
     * <p>get FLI_GSSN value
     * @return FLI_GSSN value
     */
    public String getFliGssn() {
        return fliGssn__;
    }

    /**
     * <p>set FLI_GSSN value
     * @param fliGssn FLI_GSSN value
     */
    public void setFliGssn(String fliGssn) {
        fliGssn__ = fliGssn;
    }

    /**
     * <p>get FLI_LIID value
     * @return FLI_LIID value
     */
    public String getFliLiid() {
        return fliLiid__;
    }

    /**
     * <p>set FLI_LIID value
     * @param fliLiid FLI_LIID value
     */
    public void setFliLiid(String fliLiid) {
        fliLiid__ = fliLiid;
    }

    /**
     * <p>get FSI_KBN value
     * @return FSI_KBN value
     */
    public int getFsiKbn() {
        return fsiKbn__;
    }

    /**
     * <p>set FSI_KBN value
     * @param fsiKbn FSI_KBN value
     */
    public void setFsiKbn(int fsiKbn) {
        fsiKbn__ = fsiKbn;
    }

    /**
     * <p>get FSI_ADATE value
     * @return FSI_ADATE value
     */
    public UDate getFsiAdate() {
        return fsiAdate__;
    }

    /**
     * <p>set FSI_ADATE value
     * @param fsiAdate FSI_ADATE value
     */
    public void setFsiAdate(UDate fsiAdate) {
        fsiAdate__ = fsiAdate;
    }

    /**
     * <p>get FSI_CAMPANY value
     * @return FSI_CAMPANY value
     */
    public String getFsiCampany() {
        return fsiCampany__;
    }

    /**
     * <p>set FSI_CAMPANY value
     * @param fsiCampany FSI_CAMPANY value
     */
    public void setFsiCampany(String fsiCampany) {
        fsiCampany__ = fsiCampany;
    }

    /**
     * <p>get FSI_SPLIMIT value
     * @return FSI_SPLIMIT value
     */
    public UDate getFsiSplimit() {
        return fsiSplimit__;
    }

    /**
     * <p>set FSI_SPLIMIT value
     * @param fsiSplimit FSI_SPLIMIT value
     */
    public void setFsiSplimit(UDate fsiSplimit) {
        fsiSplimit__ = fsiSplimit;
    }

    /**
     * <p>get FSI_MBLIMIT value
     * @return FSI_MBLIMIT value
     */
    public UDate getFsiMblimit() {
        return fsiMblimit__;
    }

    /**
     * <p>set FSI_MBLIMIT value
     * @param fsiMblimit FSI_MBLIMIT value
     */
    public void setFsiMblimit(UDate fsiMblimit) {
        fsiMblimit__ = fsiMblimit;
    }

    /**
     * <p>get FSI_CRLIMIT value
     * @return FSI_CRLIMIT value
     */
    public UDate getFsiCrlimit() {
        return fsiCrlimit__;
    }

    /**
     * <p>set FSI_CRLIMIT value
     * @param fsiCrlimit FSI_CRLIMIT value
     */
    public void setFsiCrlimit(UDate fsiCrlimit) {
        fsiCrlimit__ = fsiCrlimit;
    }

    /**
     * <p>get FSI_USERS value
     * @return FSI_USERS value
     */
    public int getFsiUsers() {
        return fsiUsers__;
    }

    /**
     * <p>set FSI_USERS value
     * @param fsiUsers FSI_USERS value
     */
    public void setFsiUsers(int fsiUsers) {
        fsiUsers__ = fsiUsers;
    }

    /**
     * <p>get FSI_SDATE value
     * @return FSI_SDATE value
     */
    public UDate getFsiSdate() {
        return fsiSdate__;
    }

    /**
     * <p>set FSI_SDATE value
     * @param fsiSdate FSI_SDATE value
     */
    public void setFsiSdate(UDate fsiSdate) {
        fsiSdate__ = fsiSdate;
    }

    /**
     * <p>get FSI_OLDVER value
     * @return FSI_OLDVER value
     */
    public String getFsiOldver() {
        return fsiOldver__;
    }

    /**
     * <p>set FSI_OLDVER value
     * @param fsiOldver FSI_OLDVER value
     */
    public void setFsiOldver(String fsiOldver) {
        fsiOldver__ = fsiOldver;
    }

    /**
     * <p>get FSI_GSVER value
     * @return FSI_GSVER value
     */
    public String getFsiGsver() {
        return fsiGsver__;
    }

    /**
     * <p>set FSI_GSVER value
     * @param fsiGsver FSI_GSVER value
     */
    public void setFsiGsver(String fsiGsver) {
        fsiGsver__ = fsiGsver;
    }

    /**
     * <p>get FSI_OSNAME value
     * @return FSI_OSNAME value
     */
    public String getFsiOsname() {
        return fsiOsname__;
    }

    /**
     * <p>set FSI_OSNAME value
     * @param fsiOsname FSI_OSNAME value
     */
    public void setFsiOsname(String fsiOsname) {
        fsiOsname__ = fsiOsname;
    }

    /**
     * <p>get FSI_CPUCORE value
     * @return FSI_CPUCORE value
     */
    public String getFsiCpucore() {
        return fsiCpucore__;
    }

    /**
     * <p>set FSI_CPUCORE value
     * @param fsiCpucore FSI_CPUCORE value
     */
    public void setFsiCpucore(String fsiCpucore) {
        fsiCpucore__ = fsiCpucore;
    }

    /**
     * <p>get FSI_JVMBIT value
     * @return FSI_JVMBIT value
     */
    public String getFsiJvmbit() {
        return fsiJvmbit__;
    }

    /**
     * <p>set FSI_JVMBIT value
     * @param fsiJvmbit FSI_JVMBIT value
     */
    public void setFsiJvmbit(String fsiJvmbit) {
        fsiJvmbit__ = fsiJvmbit;
    }

    /**
     * <p>get FSI_JAVAVER value
     * @return FSI_JAVAVER value
     */
    public String getFsiJavaver() {
        return fsiJavaver__;
    }

    /**
     * <p>set FSI_JAVAVER value
     * @param fsiJavaver FSI_JAVAVER value
     */
    public void setFsiJavaver(String fsiJavaver) {
        fsiJavaver__ = fsiJavaver;
    }

    /**
     * <p>get FSI_USEDMEM value
     * @return FSI_USEDMEM value
     */
    public String getFsiUsedmem() {
        return fsiUsedmem__;
    }

    /**
     * <p>set FSI_USEDMEM value
     * @param fsiUsedmem FSI_USEDMEM value
     */
    public void setFsiUsedmem(String fsiUsedmem) {
        fsiUsedmem__ = fsiUsedmem;
    }

    /**
     * <p>get FSI_MAXMEM value
     * @return FSI_MAXMEM value
     */
    public String getFsiMaxmem() {
        return fsiMaxmem__;
    }

    /**
     * <p>set FSI_MAXMEM value
     * @param fsiMaxmem FSI_MAXMEM value
     */
    public void setFsiMaxmem(String fsiMaxmem) {
        fsiMaxmem__ = fsiMaxmem;
    }

    /**
     * <p>get FSI_ADISC value
     * @return FSI_ADISC value
     */
    public String getFsiAdisc() {
        return fsiAdisc__;
    }

    /**
     * <p>set FSI_ADISC value
     * @param fsiAdisc FSI_ADISC value
     */
    public void setFsiAdisc(String fsiAdisc) {
        fsiAdisc__ = fsiAdisc;
    }

    /**
     * <p>get FSI_CON value
     * @return FSI_CON value
     */
    public String getFsiCon() {
        return fsiCon__;
    }

    /**
     * <p>set FSI_CON value
     * @param fsiCon FSI_CON value
     */
    public void setFsiCon(String fsiCon) {
        fsiCon__ = fsiCon;
    }

    /**
     * <p>get FSI_H2_LOCK_MODE value
     * @return FSI_H2_LOCK_MODE value
     */
    public int getFsiH2LockMode() {
        return fsiH2LockMode__;
    }

    /**
     * <p>set FSI_H2_LOCK_MODE value
     * @param fsiH2LockMode FSI_H2_LOCK_MODE value
     */
    public void setFsiH2LockMode(int fsiH2LockMode) {
        fsiH2LockMode__ = fsiH2LockMode;
    }

    /**
     * <p>get FSI_H2_LOCK_TIMEOUT value
     * @return FSI_H2_LOCK_TIMEOUT value
     */
    public int getFsiH2LockTimeout() {
        return fsiH2LockTimeout__;
    }

    /**
     * <p>set FSI_H2_LOCK_TIMEOUT value
     * @param fsiH2LockTimeout FSI_H2_LOCK_TIMEOUT value
     */
    public void setFsiH2LockTimeout(int fsiH2LockTimeout) {
        fsiH2LockTimeout__ = fsiH2LockTimeout;
    }

    /**
     * <p>get FSI_H2_DEFAULT_LOCK_TIMEOUT value
     * @return FSI_H2_DEFAULT_LOCK_TIMEOUT value
     */
    public int getFsiH2DefaultLockTimeout() {
        return fsiH2DefaultLockTimeout__;
    }

    /**
     * <p>set FSI_H2_DEFAULT_LOCK_TIMEOUT value
     * @param fsiH2DefaultLockTimeout FSI_H2_DEFAULT_LOCK_TIMEOUT value
     */
    public void setFsiH2DefaultLockTimeout(int fsiH2DefaultLockTimeout) {
        fsiH2DefaultLockTimeout__ = fsiH2DefaultLockTimeout;
    }

    /**
     * <p>get FSI_H2_MULTI_THREADED value
     * @return FSI_H2_MULTI_THREADED value
     */
    public int getFsiH2MultiThreaded() {
        return fsiH2MultiThreaded__;
    }

    /**
     * <p>set FSI_H2_MULTI_THREADED value
     * @param fsiH2MultiThreaded FSI_H2_MULTI_THREADED value
     */
    public void setFsiH2MultiThreaded(int fsiH2MultiThreaded) {
        fsiH2MultiThreaded__ = fsiH2MultiThreaded;
    }

    /**
     * <p>get FSI_H2_IFEXISTS value
     * @return FSI_H2_IFEXISTS value
     */
    public String getFsiH2Ifexists() {
        return fsiH2Ifexists__;
    }

    /**
     * <p>set FSI_H2_IFEXISTS value
     * @param fsiH2Ifexists FSI_H2_IFEXISTS value
     */
    public void setFsiH2Ifexists(String fsiH2Ifexists) {
        fsiH2Ifexists__ = fsiH2Ifexists;
    }

    /**
     * <p>get FSI_H2_AUTOCOMMIT value
     * @return FSI_H2_AUTOCOMMIT value
     */
    public String getFsiH2Autocommit() {
        return fsiH2Autocommit__;
    }

    /**
     * <p>set FSI_H2_AUTOCOMMIT value
     * @param fsiH2Autocommit FSI_H2_AUTOCOMMIT value
     */
    public void setFsiH2Autocommit(String fsiH2Autocommit) {
        fsiH2Autocommit__ = fsiH2Autocommit;
    }

    /**
     * <p>get FSI_H2_DB_CLOSE_ON_EXIT value
     * @return FSI_H2_DB_CLOSE_ON_EXIT value
     */
    public String getFsiH2DbCloseOnExit() {
        return fsiH2DbCloseOnExit__;
    }

    /**
     * <p>set FSI_H2_DB_CLOSE_ON_EXIT value
     * @param fsiH2DbCloseOnExit FSI_H2_DB_CLOSE_ON_EXIT value
     */
    public void setFsiH2DbCloseOnExit(String fsiH2DbCloseOnExit) {
        fsiH2DbCloseOnExit__ = fsiH2DbCloseOnExit;
    }

    /**
     * <p>get FSI_H2_CACHE_SIZE value
     * @return FSI_H2_CACHE_SIZE value
     */
    public int getFsiH2CacheSize() {
        return fsiH2CacheSize__;
    }

    /**
     * <p>set FSI_H2_CACHE_SIZE value
     * @param fsiH2CacheSize FSI_H2_CACHE_SIZE value
     */
    public void setFsiH2CacheSize(int fsiH2CacheSize) {
        fsiH2CacheSize__ = fsiH2CacheSize;
    }

    /**
     * <p>get FSI_H2_PAGE_SIZE value
     * @return FSI_H2_PAGE_SIZE value
     */
    public int getFsiH2PageSize() {
        return fsiH2PageSize__;
    }

    /**
     * <p>set FSI_H2_PAGE_SIZE value
     * @param fsiH2PageSize FSI_H2_PAGE_SIZE value
     */
    public void setFsiH2PageSize(int fsiH2PageSize) {
        fsiH2PageSize__ = fsiH2PageSize;
    }

    /**
     * <p>get FSI_H2_MAX_LENGTH_INPLACE_LOB value
     * @return FSI_H2_MAX_LENGTH_INPLACE_LOB value
     */
    public int getFsiH2MaxLengthInplaceLob() {
        return fsiH2MaxLengthInplaceLob__;
    }

    /**
     * <p>set FSI_H2_MAX_LENGTH_INPLACE_LOB value
     * @param fsiH2MaxLengthInplaceLob FSI_H2_MAX_LENGTH_INPLACE_LOB value
     */
    public void setFsiH2MaxLengthInplaceLob(int fsiH2MaxLengthInplaceLob) {
        fsiH2MaxLengthInplaceLob__ = fsiH2MaxLengthInplaceLob;
    }

    /**
     * <p>get FSI_H2_CACHE_TYPE value
     * @return FSI_H2_CACHE_TYPE value
     */
    public String getFsiH2CacheType() {
        return fsiH2CacheType__;
    }

    /**
     * <p>set FSI_H2_CACHE_TYPE value
     * @param fsiH2CacheType FSI_H2_CACHE_TYPE value
     */
    public void setFsiH2CacheType(String fsiH2CacheType) {
        fsiH2CacheType__ = fsiH2CacheType;
    }

    /**
     * <p>get FSI_H2_MVCC value
     * @return FSI_H2_MVCC value
     */
    public String getFsiH2Mvcc() {
        return fsiH2Mvcc__;
    }

    /**
     * <p>set FSI_H2_MVCC value
     * @param fsiH2Mvcc FSI_H2_MVCC value
     */
    public void setFsiH2Mvcc(String fsiH2Mvcc) {
        fsiH2Mvcc__ = fsiH2Mvcc;
    }

    /**
     * <p>get FSI_H2_QUERY_TIMEOUT value
     * @return FSI_H2_QUERY_TIMEOUT value
     */
    public int getFsiH2QueryTimeout() {
        return fsiH2QueryTimeout__;
    }

    /**
     * <p>set FSI_H2_QUERY_TIMEOUT value
     * @param fsiH2QueryTimeout FSI_H2_QUERY_TIMEOUT value
     */
    public void setFsiH2QueryTimeout(int fsiH2QueryTimeout) {
        fsiH2QueryTimeout__ = fsiH2QueryTimeout;
    }

    /**
     * <p>get FSI_H2_QUERY_TIMEOUT_BATCH value
     * @return FSI_H2_QUERY_TIMEOUT_BATCH value
     */
    public int getFsiH2QueryTimeoutBatch() {
        return fsiH2QueryTimeoutBatch__;
    }

    /**
     * <p>set FSI_H2_QUERY_TIMEOUT_BATCH value
     * @param fsiH2QueryTimeoutBatch FSI_H2_QUERY_TIMEOUT_BATCH value
     */
    public void setFsiH2QueryTimeoutBatch(int fsiH2QueryTimeoutBatch) {
        fsiH2QueryTimeoutBatch__ = fsiH2QueryTimeoutBatch;
    }

    /**
     * <p>get FSI_H2_MAX_LOG_SIZE value
     * @return FSI_H2_MAX_LOG_SIZE value
     */
    public int getFsiH2MaxLogSize() {
        return fsiH2MaxLogSize__;
    }

    /**
     * <p>set FSI_H2_MAX_LOG_SIZE value
     * @param fsiH2MaxLogSize FSI_H2_MAX_LOG_SIZE value
     */
    public void setFsiH2MaxLogSize(int fsiH2MaxLogSize) {
        fsiH2MaxLogSize__ = fsiH2MaxLogSize;
    }

    /**
     * <p>get FSI_GSDATA_DIR value
     * @return FSI_GSDATA_DIR value
     */
    public String getFsiGsdataDir() {
        return fsiGsdataDir__;
    }

    /**
     * <p>set FSI_GSDATA_DIR value
     * @param fsiGsdataDir FSI_GSDATA_DIR value
     */
    public void setFsiGsdataDir(String fsiGsdataDir) {
        fsiGsdataDir__ = fsiGsdataDir;
    }

    /**
     * <p>get FSI_BACKUP_DIR value
     * @return FSI_BACKUP_DIR value
     */
    public String getFsiBackupDir() {
        return fsiBackupDir__;
    }

    /**
     * <p>set FSI_BACKUP_DIR value
     * @param fsiBackupDir FSI_BACKUP_DIR value
     */
    public void setFsiBackupDir(String fsiBackupDir) {
        fsiBackupDir__ = fsiBackupDir;
    }

    /**
     * <p>get FSI_FILEKANRI_DIR value
     * @return FSI_FILEKANRI_DIR value
     */
    public String getFsiFilekanriDir() {
        return fsiFilekanriDir__;
    }

    /**
     * <p>set FSI_FILEKANRI_DIR value
     * @param fsiFilekanriDir FSI_FILEKANRI_DIR value
     */
    public void setFsiFilekanriDir(String fsiFilekanriDir) {
        fsiFilekanriDir__ = fsiFilekanriDir;
    }

    /**
     * <p>get FSI_WEBMAIL_DIR value
     * @return FSI_WEBMAIL_DIR value
     */
    public String getFsiWebmailDir() {
        return fsiWebmailDir__;
    }

    /**
     * <p>set FSI_WEBMAIL_DIR value
     * @param fsiWebmailDir FSI_WEBMAIL_DIR value
     */
    public void setFsiWebmailDir(String fsiWebmailDir) {
        fsiWebmailDir__ = fsiWebmailDir;
    }

    /**
     * <p>get FSI_BACKUP_INTERVAL value
     * @return FSI_BACKUP_INTERVAL value
     */
    public String getFsiBackupInterval() {
        return fsiBackupInterval__;
    }

    /**
     * <p>set FSI_BACKUP_INTERVAL value
     * @param fsiBackupInterval FSI_BACKUP_INTERVAL value
     */
    public void setFsiBackupInterval(String fsiBackupInterval) {
        fsiBackupInterval__ = fsiBackupInterval;
    }

    /**
     * <p>get FSI_BACKUP_GENERATION value
     * @return FSI_BACKUP_GENERATION value
     */
    public String getFsiBackupGeneration() {
        return fsiBackupGeneration__;
    }

    /**
     * <p>set FSI_BACKUP_GENERATION value
     * @param fsiBackupGeneration FSI_BACKUP_GENERATION value
     */
    public void setFsiBackupGeneration(String fsiBackupGeneration) {
        fsiBackupGeneration__ = fsiBackupGeneration;
    }

    /**
     * <p>get FSI_BACKUP_ZIP value
     * @return FSI_BACKUP_ZIP value
     */
    public String getFsiBackupZip() {
        return fsiBackupZip__;
    }

    /**
     * <p>set FSI_BACKUP_ZIP value
     * @param fsiBackupZip FSI_BACKUP_ZIP value
     */
    public void setFsiBackupZip(String fsiBackupZip) {
        fsiBackupZip__ = fsiBackupZip;
    }

    /**
     * <p>get FSI_BATCHJOB value
     * @return FSI_BATCHJOB value
     */
    public String getFsiBatchjob() {
        return fsiBatchjob__;
    }

    /**
     * <p>set FSI_BATCHJOB value
     * @param fsiBatchjob FSI_BATCHJOB value
     */
    public void setFsiBatchjob(String fsiBatchjob) {
        fsiBatchjob__ = fsiBatchjob;
    }

    /**
     * <p>get FSI_TEMPDIR value
     * @return FSI_TEMPDIR value
     */
    public String getFsiTempdir() {
        return fsiTempdir__;
    }

    /**
     * <p>set FSI_TEMPDIR value
     * @param fsiTempdir FSI_TEMPDIR value
     */
    public void setFsiTempdir(String fsiTempdir) {
        fsiTempdir__ = fsiTempdir;
    }

    /**
     * <p>get FSI_COMPLIANT_VERSION value
     * @return FSI_COMPLIANT_VERSION value
     */
    public String getFsiCompliantVersion() {
        return fsiCompliantVersion__;
    }

    /**
     * <p>set FSI_COMPLIANT_VERSION value
     * @param fsiCompliantVersion FSI_COMPLIANT_VERSION value
     */
    public void setFsiCompliantVersion(String fsiCompliantVersion) {
        fsiCompliantVersion__ = fsiCompliantVersion;
    }

    /**
     * <p>get FSI_GS_MBA_IOS_VERSION value
     * @return FSI_GS_MBA_IOS_VERSION value
     */
    public String getFsiGsMbaIosVersion() {
        return fsiGsMbaIosVersion__;
    }

    /**
     * <p>set FSI_GS_MBA_IOS_VERSION value
     * @param fsiGsMbaIosVersion FSI_GS_MBA_IOS_VERSION value
     */
    public void setFsiGsMbaIosVersion(String fsiGsMbaIosVersion) {
        fsiGsMbaIosVersion__ = fsiGsMbaIosVersion;
    }

    /**
     * <p>get FSI_GS_CAL_IOS_VERSION value
     * @return FSI_GS_CAL_IOS_VERSION value
     */
    public String getFsiGsCalIosVersion() {
        return fsiGsCalIosVersion__;
    }

    /**
     * <p>set FSI_GS_CAL_IOS_VERSION value
     * @param fsiGsCalIosVersion FSI_GS_CAL_IOS_VERSION value
     */
    public void setFsiGsCalIosVersion(String fsiGsCalIosVersion) {
        fsiGsCalIosVersion__ = fsiGsCalIosVersion;
    }

    /**
     * <p>get FSI_GS_ADR_IOS_VERSION value
     * @return FSI_GS_ADR_IOS_VERSION value
     */
    public String getFsiGsAdrIosVersion() {
        return fsiGsAdrIosVersion__;
    }

    /**
     * <p>set FSI_GS_ADR_IOS_VERSION value
     * @param fsiGsAdrIosVersion FSI_GS_ADR_IOS_VERSION value
     */
    public void setFsiGsAdrIosVersion(String fsiGsAdrIosVersion) {
        fsiGsAdrIosVersion__ = fsiGsAdrIosVersion;
    }

    /**
     * <p>get FSI_GS_SML_IOS_VERSION value
     * @return FSI_GS_SML_IOS_VERSION value
     */
    public String getFsiGsSmlIosVersion() {
        return fsiGsSmlIosVersion__;
    }

    /**
     * <p>set FSI_GS_SML_IOS_VERSION value
     * @param fsiGsSmlIosVersion FSI_GS_SML_IOS_VERSION value
     */
    public void setFsiGsSmlIosVersion(String fsiGsSmlIosVersion) {
        fsiGsSmlIosVersion__ = fsiGsSmlIosVersion;
    }

    /**
     * <p>get FSI_GS_WML_IOS_VERSION value
     * @return FSI_GS_WML_IOS_VERSION value
     */
    public String getFsiGsWmlIosVersion() {
        return fsiGsWmlIosVersion__;
    }

    /**
     * <p>set FSI_GS_WML_IOS_VERSION value
     * @param fsiGsWmlIosVersion FSI_GS_WML_IOS_VERSION value
     */
    public void setFsiGsWmlIosVersion(String fsiGsWmlIosVersion) {
        fsiGsWmlIosVersion__ = fsiGsWmlIosVersion;
    }

    /**
     * <p>get FSI_FIL_ALL_SEARCH_USE value
     * @return FSI_FIL_ALL_SEARCH_USE value
     */
    public int getFsiFilAllSearchUse() {
        return fsiFilAllSearchUse__;
    }

    /**
     * <p>set FSI_FIL_ALL_SEARCH_USE value
     * @param fsiFilAllSearchUse FSI_FIL_ALL_SEARCH_USE value
     */
    public void setFsiFilAllSearchUse(int fsiFilAllSearchUse) {
        fsiFilAllSearchUse__ = fsiFilAllSearchUse;
    }

    /**
     * <p>get FSI_MAIL_PORT_NUMBER value
     * @return FSI_MAIL_PORT_NUMBER value
     */
    public int getFsiMailPortNumber() {
        return fsiMailPortNumber__;
    }

    /**
     * <p>set FSI_MAIL_PORT_NUMBER value
     * @param fsiMailPortNumber FSI_MAIL_PORT_NUMBER value
     */
    public void setFsiMailPortNumber(int fsiMailPortNumber) {
        fsiMailPortNumber__ = fsiMailPortNumber;
    }

    /**
     * <p>get FSI_PORTLET_MAXLENGTH value
     * @return FSI_PORTLET_MAXLENGTH value
     */
    public int getFsiPortletMaxlength() {
        return fsiPortletMaxlength__;
    }

    /**
     * <p>set FSI_PORTLET_MAXLENGTH value
     * @param fsiPortletMaxlength FSI_PORTLET_MAXLENGTH value
     */
    public void setFsiPortletMaxlength(int fsiPortletMaxlength) {
        fsiPortletMaxlength__ = fsiPortletMaxlength;
    }

    /**
     * <p>get FSI_RSV_PRINT_USE value
     * @return FSI_RSV_PRINT_USE value
     */
    public int getFsiRsvPrintUse() {
        return fsiRsvPrintUse__;
    }

    /**
     * <p>set FSI_RSV_PRINT_USE value
     * @param fsiRsvPrintUse FSI_RSV_PRINT_USE value
     */
    public void setFsiRsvPrintUse(int fsiRsvPrintUse) {
        fsiRsvPrintUse__ = fsiRsvPrintUse;
    }

    /**
     * <p>get FSI_MAILRECEIVE_THREAD_MAXCOUNT value
     * @return FSI_MAILRECEIVE_THREAD_MAXCOUNT value
     */
    public int getFsiMailreceiveThreadMaxcount() {
        return fsiMailreceiveThreadMaxcount__;
    }

    /**
     * <p>set FSI_MAILRECEIVE_THREAD_MAXCOUNT value
     * @param fsiMailreceiveThreadMaxcount FSI_MAILRECEIVE_THREAD_MAXCOUNT value
     */
    public void setFsiMailreceiveThreadMaxcount(int fsiMailreceiveThreadMaxcount) {
        fsiMailreceiveThreadMaxcount__ = fsiMailreceiveThreadMaxcount;
    }

    /**
     * <p>get FSI_MAILRECEIVE_MAXCOUNT value
     * @return FSI_MAILRECEIVE_MAXCOUNT value
     */
    public int getFsiMailreceiveMaxcount() {
        return fsiMailreceiveMaxcount__;
    }

    /**
     * <p>set FSI_MAILRECEIVE_MAXCOUNT value
     * @param fsiMailreceiveMaxcount FSI_MAILRECEIVE_MAXCOUNT value
     */
    public void setFsiMailreceiveMaxcount(int fsiMailreceiveMaxcount) {
        fsiMailreceiveMaxcount__ = fsiMailreceiveMaxcount;
    }

    /**
     * <p>get FSI_MAILRECEIVE_CONNECTTIMEOUT value
     * @return FSI_MAILRECEIVE_CONNECTTIMEOUT value
     */
    public int getFsiMailreceiveConnecttimeout() {
        return fsiMailreceiveConnecttimeout__;
    }

    /**
     * <p>set FSI_MAILRECEIVE_CONNECTTIMEOUT value
     * @param fsiMailreceiveConnecttimeout FSI_MAILRECEIVE_CONNECTTIMEOUT value
     */
    public void setFsiMailreceiveConnecttimeout(int fsiMailreceiveConnecttimeout) {
        fsiMailreceiveConnecttimeout__ = fsiMailreceiveConnecttimeout;
    }

    /**
     * <p>get FSI_MAILRECEIVE_TIMEOUT value
     * @return FSI_MAILRECEIVE_TIMEOUT value
     */
    public int getFsiMailreceiveTimeout() {
        return fsiMailreceiveTimeout__;
    }

    /**
     * <p>set FSI_MAILRECEIVE_TIMEOUT value
     * @param fsiMailreceiveTimeout FSI_MAILRECEIVE_TIMEOUT value
     */
    public void setFsiMailreceiveTimeout(int fsiMailreceiveTimeout) {
        fsiMailreceiveTimeout__ = fsiMailreceiveTimeout;
    }

    /**
     * <p>get FSI_MAILRECEIVE_RCVSVR_CHECKTIME value
     * @return FSI_MAILRECEIVE_RCVSVR_CHECKTIME value
     */
    public int getFsiMailreceiveRcvsvrChecktime() {
        return fsiMailreceiveRcvsvrChecktime__;
    }

    /**
     * <p>set FSI_MAILRECEIVE_RCVSVR_CHECKTIME value
     * @param fsiMailreceiveRcvsvrChecktime FSI_MAILRECEIVE_RCVSVR_CHECKTIME value
     */
    public void setFsiMailreceiveRcvsvrChecktime(int fsiMailreceiveRcvsvrChecktime) {
        fsiMailreceiveRcvsvrChecktime__ = fsiMailreceiveRcvsvrChecktime;
    }

    /**
     * <p>get FSI_MAILBODY_LIMIT value
     * @return FSI_MAILBODY_LIMIT value
     */
    public int getFsiMailbodyLimit() {
        return fsiMailbodyLimit__;
    }

    /**
     * <p>set FSI_MAILBODY_LIMIT value
     * @param fsiMailbodyLimit FSI_MAILBODY_LIMIT value
     */
    public void setFsiMailbodyLimit(int fsiMailbodyLimit) {
        fsiMailbodyLimit__ = fsiMailbodyLimit;
    }


}
