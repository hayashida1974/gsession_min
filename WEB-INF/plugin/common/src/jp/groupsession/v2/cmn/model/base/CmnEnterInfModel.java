package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_ENTER_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnEnterInfModel implements Serializable {

    /** ENI_NAME mapping */
    private String eniName__;
    /** ENI_NAME_KN mapping */
    private String eniNameKn__;
    /** ENI_KISYU mapping */
    private int eniKisyu__;
    /** ENI_URL mapping */
    private String eniUrl__;
    /** ENI_BIKO mapping */
    private String eniBiko__;
    /** BIN_SID mapping */
    private Long binSid__;
    /** MENU_BIN_SID mapping */
    private Long menuBinSid__;
    /** ENI_IMG_KBN mapping */
    private int eniImgKbn__;
    /** ENI_MENU_IMG_KBN mapping */
    private int eniMenuImgKbn__;
    /** ENI_AUID mapping */
    private int eniAuid__;
    /** ENI_ADATE mapping */
    private UDate eniAdate__;
    /** ENI_EUID mapping */
    private int eniEuid__;
    /** ENI_EDATE mapping */
    private UDate eniEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnEnterInfModel() {
    }

    /**
     * <p>get ENI_NAME value
     * @return ENI_NAME value
     */
    public String getEniName() {
        return eniName__;
    }

    /**
     * <p>set ENI_NAME value
     * @param eniName ENI_NAME value
     */
    public void setEniName(String eniName) {
        eniName__ = eniName;
    }

    /**
     * <p>get ENI_NAME_KN value
     * @return ENI_NAME_KN value
     */
    public String getEniNameKn() {
        return eniNameKn__;
    }

    /**
     * <p>set ENI_NAME_KN value
     * @param eniNameKn ENI_NAME_KN value
     */
    public void setEniNameKn(String eniNameKn) {
        eniNameKn__ = eniNameKn;
    }

    /**
     * <p>get ENI_KISYU value
     * @return ENI_KISYU value
     */
    public int getEniKisyu() {
        return eniKisyu__;
    }

    /**
     * <p>set ENI_KISYU value
     * @param eniKisyu ENI_KISYU value
     */
    public void setEniKisyu(int eniKisyu) {
        eniKisyu__ = eniKisyu;
    }

    /**
     * <p>get ENI_URL value
     * @return ENI_URL value
     */
    public String getEniUrl() {
        return eniUrl__;
    }

    /**
     * <p>set ENI_URL value
     * @param eniUrl ENI_URL value
     */
    public void setEniUrl(String eniUrl) {
        eniUrl__ = eniUrl;
    }

    /**
     * <p>get ENI_BIKO value
     * @return ENI_BIKO value
     */
    public String getEniBiko() {
        return eniBiko__;
    }

    /**
     * <p>set ENI_BIKO value
     * @param eniBiko ENI_BIKO value
     */
    public void setEniBiko(String eniBiko) {
        eniBiko__ = eniBiko;
    }

    /**
     * <p>get ENI_AUID value
     * @return ENI_AUID value
     */
    public int getEniAuid() {
        return eniAuid__;
    }

    /**
     * <p>set ENI_AUID value
     * @param eniAuid ENI_AUID value
     */
    public void setEniAuid(int eniAuid) {
        eniAuid__ = eniAuid;
    }

    /**
     * <p>get ENI_ADATE value
     * @return ENI_ADATE value
     */
    public UDate getEniAdate() {
        return eniAdate__;
    }

    /**
     * <p>set ENI_ADATE value
     * @param eniAdate ENI_ADATE value
     */
    public void setEniAdate(UDate eniAdate) {
        eniAdate__ = eniAdate;
    }

    /**
     * <p>get ENI_EUID value
     * @return ENI_EUID value
     */
    public int getEniEuid() {
        return eniEuid__;
    }

    /**
     * <p>set ENI_EUID value
     * @param eniEuid ENI_EUID value
     */
    public void setEniEuid(int eniEuid) {
        eniEuid__ = eniEuid;
    }

    /**
     * <p>get ENI_EDATE value
     * @return ENI_EDATE value
     */
    public UDate getEniEdate() {
        return eniEdate__;
    }

    /**
     * <p>set ENI_EDATE value
     * @param eniEdate ENI_EDATE value
     */
    public void setEniEdate(UDate eniEdate) {
        eniEdate__ = eniEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getString(eniName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(eniNameKn__, ""));
        buf.append(",");
        buf.append(eniKisyu__);
        buf.append(",");
        buf.append(NullDefault.getString(eniUrl__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(eniBiko__, ""));
        buf.append(",");
        buf.append(eniAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eniAdate__, ""));
        buf.append(",");
        buf.append(eniEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eniEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>eniImgKbn を取得します。
     * @return eniImgKbn
     */
    public int getEniImgKbn() {
        return eniImgKbn__;
    }

    /**
     * <p>eniImgKbn をセットします。
     * @param eniImgKbn eniImgKbn
     */
    public void setEniImgKbn(int eniImgKbn) {
        eniImgKbn__ = eniImgKbn;
    }

    /**
     * <p>menuBinSid を取得します。
     * @return menuBinSid
     */
    public Long getMenuBinSid() {
        return menuBinSid__;
    }

    /**
     * <p>menuBinSid をセットします。
     * @param menuBinSid menuBinSid
     */
    public void setMenuBinSid(Long menuBinSid) {
        menuBinSid__ = menuBinSid;
    }

    /**
     * <p>eniMenuImgKbn を取得します。
     * @return eniMenuImgKbn
     */
    public int getEniMenuImgKbn() {
        return eniMenuImgKbn__;
    }

    /**
     * <p>eniMenuImgKbn をセットします。
     * @param eniMenuImgKbn eniMenuImgKbn
     */
    public void setEniMenuImgKbn(int eniMenuImgKbn) {
        eniMenuImgKbn__ = eniMenuImgKbn;
    }

}
