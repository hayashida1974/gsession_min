package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_FOR_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsForInfModel implements Serializable {

    /** BFI_SID mapping */
    private int bfiSid__;
    /** BFI_NAME mapping */
    private String bfiName__;
    /** BFI_CMT mapping */
    private String bfiCmt__;
    /** BFI_SORT mapping */
    private int bfiSort__;
    /** BFI_REPLY mapping */
    private int bfiReply__;
    /** BFI_READ mapping */
    private int bfiRead__;
    /** BIN_SID mapping */
    private long binSid__;
    /** BFI_MREAD mapping */
    private int bfiMread__;
    /** BFI_TEMPLATE_KBN mapping */
    private int bfiTemplateKbn__;
    /** BFI_TEMPLATE mapping */
    private String bfiTemplate__;
    /** BFI_TEMPLATE_TYPE mapping */
    private int bfiTemplateType__;
    /** BFI_TEMPLATE_WRITE mapping */
    private int bfiTemplateWrite__;
    /** BFI_AUID mapping */
    private int bfiAuid__;
    /** BFI_ADATE mapping */
    private UDate bfiAdate__;
    /** BFI_EUID mapping */
    private int bfiEuid__;
    /** BFI_EDATE mapping */
    private UDate bfiEdate__;
    /** BFI_DISK mapping */
    private int bfiDisk__;
    /** BFI_DISK_SIZE mapping */
    private int bfiDiskSize__;
    /** BFI_WARN_DISK mapping */
    private int bfiWarnDisk__;
    /** BFI_WARN_DISK_TH mapping */
    private int bfiWarnDiskTh__;
    /** BFI_LIMIT mapping */
    private int bfiLimit__;
    /** BFI_LIMIT_DATE mapping */
    private int bfiLimitDate__;
    /** BFI_KEEP mapping */
    private int bfiKeep__;
    /** BFI_KEEP_DATE_Y mapping */
    private int bfiKeepDateY__;
    /** BFI_KEEP_DATE_M mapping */
    private int bfiKeepDateM__;
    /** BFI_LIMIT_ON mapping */
    private int bfiLimitOn__;
    /** BFI_PARENT_SID mapping */
    private int bfiParentSid__;
    /** BFI_LEVEL mapping */
    private int bfiLevel__;
    /** BFI_FOLLOW_PARENT_MEM mapping */
    private int bfiFollowParentMem__;
    /** BFI_MIN_DIV mapping */
    private int bfiMinDiv__;
    /** BFI_UNLIMITED mapping */
    private int bfiUnlimited__;

    /**
     * <p>Default Constructor
     */
    public BbsForInfModel() {
    }

    /**
     * <p>get BFI_SID value
     * @return BFI_SID value
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>set BFI_SID value
     * @param bfiSid BFI_SID value
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }

    /**
     * <p>get BFI_NAME value
     * @return BFI_NAME value
     */
    public String getBfiName() {
        return bfiName__;
    }

    /**
     * <p>set BFI_NAME value
     * @param bfiName BFI_NAME value
     */
    public void setBfiName(String bfiName) {
        bfiName__ = bfiName;
    }

    /**
     * <p>get BFI_CMT value
     * @return BFI_CMT value
     */
    public String getBfiCmt() {
        return bfiCmt__;
    }

    /**
     * <p>set BFI_CMT value
     * @param bfiCmt BFI_CMT value
     */
    public void setBfiCmt(String bfiCmt) {
        bfiCmt__ = bfiCmt;
    }

    /**
     * <p>get BFI_SORT value
     * @return BFI_SORT value
     */
    public int getBfiSort() {
        return bfiSort__;
    }

    /**
     * <p>set BFI_SORT value
     * @param bfiSort BFI_SORT value
     */
    public void setBfiSort(int bfiSort) {
        bfiSort__ = bfiSort;
    }

    /**
     * <p>get BFI_REPLY value
     * @return BFI_REPLY value
     */
    public int getBfiReply() {
        return bfiReply__;
    }

    /**
     * <p>set BFI_REPLY value
     * @param bfiReply BFI_REPLY value
     */
    public void setBfiReply(int bfiReply) {
        bfiReply__ = bfiReply;
    }

    /**
     * <p>get BFI_READ value
     * @return BFI_READ value
     */
    public int getBfiRead() {
        return bfiRead__;
    }

    /**
     * <p>set BFI_READ value
     * @param bfiRead BFI_READ value
     */
    public void setBfiRead(int bfiRead) {
        bfiRead__ = bfiRead;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get BFI_MREAD value
     * @return BFI_MREAD value
     */
    public int getBfiMread() {
        return bfiMread__;
    }

    /**
     * <p>set BFI_MREAD value
     * @param bfiMread BFI_MREAD value
     */
    public void setBfiMread(int bfiMread) {
        bfiMread__ = bfiMread;
    }

    /**
     * <p>get BFI_TEMPLATE_KBN value
     * @return BFI_TEMPLATE_KBN value
     */
    public int getBfiTemplateKbn() {
        return bfiTemplateKbn__;
    }

    /**
     * <p>set BFI_TEMPLATE_KBN value
     * @param bfiTemplateKbn BFI_TEMPLATE_KBN value
     */
    public void setBfiTemplateKbn(int bfiTemplateKbn) {
        bfiTemplateKbn__ = bfiTemplateKbn;
    }

    /**
     * <p>get BFI_TEMPLATE value
     * @return BFI_TEMPLATE value
     */
    public String getBfiTemplate() {
        return bfiTemplate__;
    }

    /**
     * <p>set BFI_TEMPLATE value
     * @param bfiTemplate BFI_TEMPLATE value
     */
    public void setBfiTemplate(String bfiTemplate) {
        bfiTemplate__ = bfiTemplate;
    }

    /**
     * <p>bfiTemplateType を取得します。
     * @return bfiTemplateType
     */
    public int getBfiTemplateType() {
        return bfiTemplateType__;
    }

    /**
     * <p>bfiTemplateType をセットします。
     * @param bfiTemplateType bfiTemplateType
     */
    public void setBfiTemplateType(int bfiTemplateType) {
        bfiTemplateType__ = bfiTemplateType;
    }

    /**
     * <p>get BFI_TEMPLATE_WRITE value
     * @return BFI_TEMPLATE_WRITE value
     */
    public int getBfiTemplateWrite() {
        return bfiTemplateWrite__;
    }

    /**
     * <p>set BFI_TEMPLATE_WRITE value
     * @param bfiTemplateWrite BFI_TEMPLATE_WRITE value
     */
    public void setBfiTemplateWrite(int bfiTemplateWrite) {
        bfiTemplateWrite__ = bfiTemplateWrite;
    }

    /**
     * <p>get BFI_AUID value
     * @return BFI_AUID value
     */
    public int getBfiAuid() {
        return bfiAuid__;
    }

    /**
     * <p>set BFI_AUID value
     * @param bfiAuid BFI_AUID value
     */
    public void setBfiAuid(int bfiAuid) {
        bfiAuid__ = bfiAuid;
    }

    /**
     * <p>get BFI_ADATE value
     * @return BFI_ADATE value
     */
    public UDate getBfiAdate() {
        return bfiAdate__;
    }

    /**
     * <p>set BFI_ADATE value
     * @param bfiAdate BFI_ADATE value
     */
    public void setBfiAdate(UDate bfiAdate) {
        bfiAdate__ = bfiAdate;
    }

    /**
     * <p>get BFI_EUID value
     * @return BFI_EUID value
     */
    public int getBfiEuid() {
        return bfiEuid__;
    }

    /**
     * <p>set BFI_EUID value
     * @param bfiEuid BFI_EUID value
     */
    public void setBfiEuid(int bfiEuid) {
        bfiEuid__ = bfiEuid;
    }

    /**
     * <p>get BFI_EDATE value
     * @return BFI_EDATE value
     */
    public UDate getBfiEdate() {
        return bfiEdate__;
    }

    /**
     * <p>set BFI_EDATE value
     * @param bfiEdate BFI_EDATE value
     */
    public void setBfiEdate(UDate bfiEdate) {
        bfiEdate__ = bfiEdate;
    }

    /**
     * <p>get BFI_DISK value
     * @return BFI_DISK value
     */
    public int getBfiDisk() {
        return bfiDisk__;
    }

    /**
     * <p>set BFI_DISK value
     * @param bfiDisk BFI_DISK value
     */
    public void setBfiDisk(int bfiDisk) {
        bfiDisk__ = bfiDisk;
    }

    /**
     * <p>get BFI_DISK_SIZE value
     * @return BFI_DISK_SIZE value
     */
    public int getBfiDiskSize() {
        return bfiDiskSize__;
    }

    /**
     * <p>set BFI_DISK_SIZE value
     * @param bfiDiskSize BFI_DISK_SIZE value
     */
    public void setBfiDiskSize(int bfiDiskSize) {
        bfiDiskSize__ = bfiDiskSize;
    }

    /**
     * <p>get BFI_WARN_DISK value
     * @return BFI_WARN_DISK value
     */
    public int getBfiWarnDisk() {
        return bfiWarnDisk__;
    }

    /**
     * <p>set BFI_WARN_DISK value
     * @param bfiWarnDisk BFI_WARN_DISK value
     */
    public void setBfiWarnDisk(int bfiWarnDisk) {
        bfiWarnDisk__ = bfiWarnDisk;
    }

    /**
     * <p>get BFI_WARN_DISK_TH value
     * @return BFI_WARN_DISK_TH value
     */
    public int getBfiWarnDiskTh() {
        return bfiWarnDiskTh__;
    }

    /**
     * <p>set BFI_WARN_DISK_TH value
     * @param bfiWarnDiskTh BFI_WARN_DISK_TH value
     */
    public void setBfiWarnDiskTh(int bfiWarnDiskTh) {
        bfiWarnDiskTh__ = bfiWarnDiskTh;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(bfiName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(bfiCmt__, ""));
        buf.append(",");
        buf.append(bfiSort__);
        buf.append(",");
        buf.append(bfiReply__);
        buf.append(",");
        buf.append(bfiRead__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(bfiMread__);
        buf.append(",");
        buf.append(bfiTemplateKbn__);
        buf.append(",");
        buf.append(NullDefault.getString(bfiTemplate__, ""));
        buf.append(",");
        buf.append(bfiTemplateWrite__);
        buf.append(",");
        buf.append(bfiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bfiAdate__, ""));
        buf.append(",");
        buf.append(bfiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bfiEdate__, ""));
        buf.append(",");
        buf.append(bfiDisk__);
        buf.append(",");
        buf.append(bfiDiskSize__);
        buf.append(",");
        buf.append(bfiWarnDisk__);
        buf.append(",");
        buf.append(bfiWarnDiskTh__);
        return buf.toString();
    }

    /**
     * <p>bfiLimit を取得します。
     * @return bfiLimit
     */
    public int getBfiLimit() {
        return bfiLimit__;
    }

    /**
     * <p>bfiLimit をセットします。
     * @param bfiLimit bfiLimit
     */
    public void setBfiLimit(int bfiLimit) {
        bfiLimit__ = bfiLimit;
    }

    /**
     * <p>bfiLimitDate を取得します。
     * @return bfiLimitDate
     */
    public int getBfiLimitDate() {
        return bfiLimitDate__;
    }

    /**
     * <p>bfiLimitDate をセットします。
     * @param bfiLimitDate bfiLimitDate
     */
    public void setBfiLimitDate(int bfiLimitDate) {
        bfiLimitDate__ = bfiLimitDate;
    }

    /**
     * <p>bfiKeep を取得します。
     * @return bfiKeep
     */
    public int getBfiKeep() {
        return bfiKeep__;
    }

    /**
     * <p>bfiKeep をセットします。
     * @param bfiKeep bfiKeep
     */
    public void setBfiKeep(int bfiKeep) {
        bfiKeep__ = bfiKeep;
    }

    /**
     * <p>bfiKeepDateY を取得します。
     * @return bfiKeepDateY
     */
    public int getBfiKeepDateY() {
        return bfiKeepDateY__;
    }

    /**
     * <p>bfiKeepDateY をセットします。
     * @param bfiKeepDateY bfiKeepDateY
     */
    public void setBfiKeepDateY(int bfiKeepDateY) {
        bfiKeepDateY__ = bfiKeepDateY;
    }

    /**
     * <p>bfiKeepDateM を取得します。
     * @return bfiKeepDateM
     */
    public int getBfiKeepDateM() {
        return bfiKeepDateM__;
    }

    /**
     * <p>bfiKeepDateM をセットします。
     * @param bfiKeepDateM bfiKeepDateM
     */
    public void setBfiKeepDateM(int bfiKeepDateM) {
        bfiKeepDateM__ = bfiKeepDateM;
    }

    /**
     * <p>bfiLimitOn を取得します。
     * @return bfiLimitOn
     */
    public int getBfiLimitOn() {
        return bfiLimitOn__;
    }

    /**
     * <p>bfiLimitOn をセットします。
     * @param bfiLimitOn bfiLimitOn
     */
    public void setBfiLimitOn(int bfiLimitOn) {
        bfiLimitOn__ = bfiLimitOn;
    }

    /**
     * <p>bfiParentSid を取得します。
     * @return bfiParentSid
     */
    public int getBfiParentSid() {
        return bfiParentSid__;
    }

    /**
     * <p>bfiParentSid をセットします。
     * @param bfiParentSid bfiParentSid
     */
    public void setBfiParentSid(int bfiParentSid) {
        bfiParentSid__ = bfiParentSid;
    }

    /**
     * <p>bfiLevel を取得します。
     * @return bfiLevel
     */
    public int getBfiLevel() {
        return bfiLevel__;
    }

    /**
     * <p>bfiLevel をセットします。
     * @param bfiLevel bfiLevel
     */
    public void setBfiLevel(int bfiLevel) {
        bfiLevel__ = bfiLevel;
    }

    /**
     * <p>bfiFollowParentMem を取得します。
     * @return bfiFollowParentMem
     */
    public int getBfiFollowParentMem() {
        return bfiFollowParentMem__;
    }

    /**
     * <p>bfiFollowParentMem をセットします。
     * @param bfiFollowParentMem bfiFollowParentMem
     */
    public void setBfiFollowParentMem(int bfiFollowParentMem) {
        bfiFollowParentMem__ = bfiFollowParentMem;
    }

    /**
     * <p>bfiMinDivを取得します。
     * @return bfiMinDiv
     * */
    public int getBfiMinDiv() {
        return bfiMinDiv__;
    }

    /**
     * <p>bfiMinDivをセットします。
     * @param bfiMinDiv bfiMinDiv
     * */
    public void setBfiMinDiv(int bfiMinDiv) {
        bfiMinDiv__ = bfiMinDiv;
    }

    /**
     * <p>bfiUnlimited を取得します。
     * @return bfiUnlimited
     * @see jp.groupsession.v2.bbs.model.BbsForInfModel#bfiUnlimited__
     */
    public int getBfiUnlimited() {
        return bfiUnlimited__;
    }

    /**
     * <p>bfiUnlimited をセットします。
     * @param bfiUnlimited bfiUnlimited
     * @see jp.groupsession.v2.bbs.model.BbsForInfModel#bfiUnlimited__
     */
    public void setBfiUnlimited(int bfiUnlimited) {
        bfiUnlimited__ = bfiUnlimited;
    }
}
