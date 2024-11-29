package jp.groupsession.v2.bbs.model;


import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsUserModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** BUR_FOR_CNT mapping */
    private int burForCnt__;
    /** BUR_THRE_CNT mapping */
    private int burThreCnt__;
    /** BUR_WRT_CNT mapping */
    private int burWrtCnt__;
    /** BUR_NEW_CNT mapping */
    private int burNewCnt__;
    /** BUR_SML_NTF mapping */
    private int burSmlNtf__;
    /** BUR_THRE_MAIN_CNT mapping */
    private int burThreMainCnt__;
    /** BUR_WRTLIST_ORDER mapping */
    private int burWrtlistOrder__;
    /** BUR_AUID mapping */
    private int burAuid__;
    /** BUR_ADATE mapping */
    private UDate burAdate__;
    /** BUR_EUID mapping */
    private int burEuid__;
    /** BUR_EDATE mapping */
    private UDate burEdate__;

    /** BUR_THRE_IMAGE mapping */
    private int burThreImage__;

    /** BUR_MAIN_CHKED_DSP mapping */
    private int burMainChkedDsp__;

    /** BUR_SUB_NEW_THRE mapping */
    private int burSubNewThre__;
    /** BUR_SUB_FORUM mapping */
    private int burSubForum__;
    /** BUR_SUB_UNCHK_THRE mapping */
    private int burSubUnchkThre__;
    /** BUR_TEMP_IMAGE mapping */
    private int burTempImage__;

    /** BUR_INI_POST_TYPE mapping */
    private int burIniPostType__;


    /**
     * <p>Default Constructor
     */
    public BbsUserModel() {
    }

    /**
     * @return burMainChkedDsp
     */
    public int getBurMainChkedDsp() {
        return burMainChkedDsp__;
    }

    /**
     * @param burMainChkedDsp セットする burMainChkedDsp
     */
    public void setBurMainChkedDsp(int burMainChkedDsp) {
        burMainChkedDsp__ = burMainChkedDsp;
    }

    /**
     * <p>burThreImage を取得します。
     * @return burThreImage
     */
    public int getBurThreImage() {
        return burThreImage__;
    }

    /**
     * <p>burThreImage をセットします。
     * @param burThreImage burThreImage
     */
    public void setBurThreImage(int burThreImage) {
        burThreImage__ = burThreImage;
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
     * <p>get BUR_FOR_CNT value
     * @return BUR_FOR_CNT value
     */
    public int getBurForCnt() {
        return burForCnt__;
    }

    /**
     * <p>set BUR_FOR_CNT value
     * @param burForCnt BUR_FOR_CNT value
     */
    public void setBurForCnt(int burForCnt) {
        burForCnt__ = burForCnt;
    }

    /**
     * <p>get BUR_THRE_CNT value
     * @return BUR_THRE_CNT value
     */
    public int getBurThreCnt() {
        return burThreCnt__;
    }

    /**
     * <p>set BUR_THRE_CNT value
     * @param burThreCnt BUR_THRE_CNT value
     */
    public void setBurThreCnt(int burThreCnt) {
        burThreCnt__ = burThreCnt;
    }

    /**
     * <p>get BUR_WRT_CNT value
     * @return BUR_WRT_CNT value
     */
    public int getBurWrtCnt() {
        return burWrtCnt__;
    }

    /**
     * <p>set BUR_WRT_CNT value
     * @param burWrtCnt BUR_WRT_CNT value
     */
    public void setBurWrtCnt(int burWrtCnt) {
        burWrtCnt__ = burWrtCnt;
    }

    /**
     * <p>get BUR_NEW_CNT value
     * @return BUR_NEW_CNT value
     */
    public int getBurNewCnt() {
        return burNewCnt__;
    }

    /**
     * <p>set BUR_NEW_CNT value
     * @param burNewCnt BUR_NEW_CNT value
     */
    public void setBurNewCnt(int burNewCnt) {
        burNewCnt__ = burNewCnt;
    }

    /**
     * <p>get BUR_SML_CNT value
     * @return BUR_SML_CNT value
     */
    public int getBurSmlNtf() {
        return burSmlNtf__;
    }

    /**
     * <p>set BUR_SML_CNT value
     * @param burSmlNtf BUR_SML_CNT value
     */
    public void setBurSmlNtf(int burSmlNtf) {
        burSmlNtf__ = burSmlNtf;
    }

    /**
     * <p>get BUR_NEW_CNT value
     * @return BUR_NEW_CNT value
     */
    public int getBurThreMainCnt() {
        return burThreMainCnt__;
    }

    /**
     * <p>set BUR_THRE_MAIN_CNT value
     * @param burThreMainCnt BUR_THRE_MAIN_CNT value
     */
    public void setBurThreMainCnt(int burThreMainCnt) {
        burThreMainCnt__ = burThreMainCnt;
    }

    /**
     * <p>get BUR_WRTLIST_ORDER value
     * @return BUR_WRTLIST_ORDER value
     */
    public int getBurWrtlistOrder() {
        return burWrtlistOrder__;
    }

    /**
     * <p>set BUR_WRTLIST_ORDER value
     * @param burWrtlistOrder BUR_WRTLIST_ORDER value
     */
    public void setBurWrtlistOrder(int burWrtlistOrder) {
        burWrtlistOrder__ = burWrtlistOrder;
    }

    /**
     * <p>get BUR_AUID value
     * @return BUR_AUID value
     */
    public int getBurAuid() {
        return burAuid__;
    }

    /**
     * <p>set BUR_AUID value
     * @param burAuid BUR_AUID value
     */
    public void setBurAuid(int burAuid) {
        burAuid__ = burAuid;
    }

    /**
     * <p>get BUR_ADATE value
     * @return BUR_ADATE value
     */
    public UDate getBurAdate() {
        return burAdate__;
    }

    /**
     * <p>set BUR_ADATE value
     * @param burAdate BUR_ADATE value
     */
    public void setBurAdate(UDate burAdate) {
        burAdate__ = burAdate;
    }

    /**
     * <p>get BUR_EUID value
     * @return BUR_EUID value
     */
    public int getBurEuid() {
        return burEuid__;
    }

    /**
     * <p>set BUR_EUID value
     * @param burEuid BUR_EUID value
     */
    public void setBurEuid(int burEuid) {
        burEuid__ = burEuid;
    }

    /**
     * <p>get BUR_EDATE value
     * @return BUR_EDATE value
     */
    public UDate getBurEdate() {
        return burEdate__;
    }

    /**
     * <p>set BUR_EDATE value
     * @param burEdate BUR_EDATE value
     */
    public void setBurEdate(UDate burEdate) {
        burEdate__ = burEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(burForCnt__);
        buf.append(",");
        buf.append(burThreCnt__);
        buf.append(",");
        buf.append(burWrtCnt__);
        buf.append(",");
        buf.append(burNewCnt__);
        buf.append(",");
        buf.append(burSmlNtf__);
        buf.append(",");
        buf.append(burThreMainCnt__);
        buf.append(",");
        buf.append(burWrtlistOrder__);
        buf.append(",");
        buf.append(burAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(burAdate__, ""));
        buf.append(",");
        buf.append(burEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(burEdate__, ""));
        return buf.toString();
    }

    /**
     * @return burSubNewThre
     */
    public int getBurSubNewThre() {
        return burSubNewThre__;
    }

    /**
     * @param burSubNewThre セットする burSubNewThre
     */
    public void setBurSubNewThre(int burSubNewThre) {
        burSubNewThre__ = burSubNewThre;
    }

    /**
     * @return burSubForum
     */
    public int getBurSubForum() {
        return burSubForum__;
    }

    /**
     * @param burSubForum セットする burSubForum
     */
    public void setBurSubForum(int burSubForum) {
        burSubForum__ = burSubForum;
    }

    /**
     * @return burSubUnchkThre
     */
    public int getBurSubUnchkThre() {
        return burSubUnchkThre__;
    }

    /**
     * @param burSubUnchkThre セットする burSubUnchkThre
     */
    public void setBurSubUnchkThre(int burSubUnchkThre) {
        burSubUnchkThre__ = burSubUnchkThre;
    }

    /**
     * <p>burTempImage を取得します。
     * @return burTempImage
     */
    public int getBurTempImage() {
        return burTempImage__;
    }

    /**
     * <p>burTempImage をセットします。
     * @param burTempImage burTempImage
     */
    public void setBurTempImage(int burTempImage) {
        burTempImage__ = burTempImage;
    }

    /**
     * <p>burIniPostType を取得します。
     * @return burIniPostType
     */
    public int getBurIniPostType() {
        return burIniPostType__;
    }

    /**
     * <p>burIniPostType をセットします。
     * @param burIniPostType burIniPostType
     */
    public void setBurIniPostType(int burIniPostType) {
        burIniPostType__ = burIniPostType;
    }

}
