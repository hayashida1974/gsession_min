package jp.groupsession.v2.convert.convert550.dao;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>バージョンアップコンバート時の施設グループ情報を保持するModelクラス
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisGrpModel implements Serializable {

    /** RSG_SID mapping */
    private int rsgSid__;
    /** RSK_SID mapping */
    private int rskSid__;
    /** RSG_ID mapping */
    private String rsgId__;
    /** RSG_NAME mapping */
    private String rsgName__;
    /** RSG_ADM_KBN mapping */
    private int rsgAdmKbn__;
    /** RSG_SORT mapping */
    private int rsgSort__;
    /** RSG_ACS_LIMIT_KBN mapping */
    private int rsgAcsLimitKbn__;
    /** RSG_APPR_KBN mapping */
    private int rsgApprKbn__;
    /** RSG_AUID mapping */
    private int rsgAuid__;
    /** RSG_ADATE mapping */
    private UDate rsgAdate__;
    /** RSG_EUID mapping */
    private int rsgEuid__;
    /** RSG_EDATE mapping */
    private UDate rsgEdate__;

    /**
     * <p>Default Constructor
     */
    public RsvSisGrpModel() {
    }

    /**
     * <p>get RSG_SID value
     * @return RSG_SID value
     */
    public int getRsgSid() {
        return rsgSid__;
    }

    /**
     * <p>set RSG_SID value
     * @param rsgSid RSG_SID value
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }

    /**
     * <p>get RSK_SID value
     * @return RSK_SID value
     */
    public int getRskSid() {
        return rskSid__;
    }

    /**
     * <p>set RSK_SID value
     * @param rskSid RSK_SID value
     */
    public void setRskSid(int rskSid) {
        rskSid__ = rskSid;
    }

    /**
     * <p>get RSG_NAME value
     * @return RSG_NAME value
     */
    public String getRsgName() {
        return rsgName__;
    }

    /**
     * <p>set RSG_NAME value
     * @param rsgName RSG_NAME value
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }

    /**
     * <p>get RSG_ADM_KBN value
     * @return RSG_ADM_KBN value
     */
    public int getRsgAdmKbn() {
        return rsgAdmKbn__;
    }

    /**
     * <p>set RSG_ADM_KBN value
     * @param rsgAdmKbn RSG_ADM_KBN value
     */
    public void setRsgAdmKbn(int rsgAdmKbn) {
        rsgAdmKbn__ = rsgAdmKbn;
    }

    /**
     * <p>get RSG_SORT value
     * @return RSG_SORT value
     */
    public int getRsgSort() {
        return rsgSort__;
    }

    /**
     * <p>set RSG_SORT value
     * @param rsgSort RSG_SORT value
     */
    public void setRsgSort(int rsgSort) {
        rsgSort__ = rsgSort;
    }

    /**
     * <p>get RSG_APPR_KBN value
     * @return RSG_APPR_KBN value
     */
    public int getRsgApprKbn() {
        return rsgApprKbn__;
    }

    /**
     * <p>set RSG_APPR_KBN value
     * @param rsgApprKbn RSG_APPR_KBN value
     */
    public void setRsgApprKbn(int rsgApprKbn) {
        rsgApprKbn__ = rsgApprKbn;
    }

    /**
     * <p>get RSG_AUID value
     * @return RSG_AUID value
     */
    public int getRsgAuid() {
        return rsgAuid__;
    }

    /**
     * <p>set RSG_AUID value
     * @param rsgAuid RSG_AUID value
     */
    public void setRsgAuid(int rsgAuid) {
        rsgAuid__ = rsgAuid;
    }

    /**
     * <p>get RSG_ADATE value
     * @return RSG_ADATE value
     */
    public UDate getRsgAdate() {
        return rsgAdate__;
    }

    /**
     * <p>set RSG_ADATE value
     * @param rsgAdate RSG_ADATE value
     */
    public void setRsgAdate(UDate rsgAdate) {
        rsgAdate__ = rsgAdate;
    }

    /**
     * <p>get RSG_EUID value
     * @return RSG_EUID value
     */
    public int getRsgEuid() {
        return rsgEuid__;
    }

    /**
     * <p>set RSG_EUID value
     * @param rsgEuid RSG_EUID value
     */
    public void setRsgEuid(int rsgEuid) {
        rsgEuid__ = rsgEuid;
    }

    /**
     * <p>get RSG_EDATE value
     * @return RSG_EDATE value
     */
    public UDate getRsgEdate() {
        return rsgEdate__;
    }

    /**
     * <p>set RSG_EDATE value
     * @param rsgEdate RSG_EDATE value
     */
    public void setRsgEdate(UDate rsgEdate) {
        rsgEdate__ = rsgEdate;
    }

    /**
     * <p>get RSG_ID value
     * @return RSG_ID value
     */
    public String getRsgId() {
        return rsgId__;
    }

    /**
     * <p>set RSG_ID value
     * @param rsgId RSG_ID value
     */
    public void setRsgId(String rsgId) {
        rsgId__ = rsgId;
    }

    /**
     * <p>rsgAcsLimitKbn を取得します。
     * @return rsgAcsLimitKbn
     */
    public int getRsgAcsLimitKbn() {
        return rsgAcsLimitKbn__;
    }

    /**
     * <p>rsgAcsLimitKbn をセットします。
     * @param rsgAcsLimitKbn rsgAcsLimitKbn
     */
    public void setRsgAcsLimitKbn(int rsgAcsLimitKbn) {
        rsgAcsLimitKbn__ = rsgAcsLimitKbn;
    }
}