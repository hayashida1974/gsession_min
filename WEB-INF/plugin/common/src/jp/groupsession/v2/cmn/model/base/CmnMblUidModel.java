package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 固体識別番号Model
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnMblUidModel extends AbstractModel {

    /** USR_SID mapping */
    private int usrSid__;
    /** CMU_UID_1 mapping */
    private String cmuUid1__;
    /** CMU_UID_2 mapping */
    private String cmuUid2__;
    /** CMU_UID_3 mapping */
    private String cmuUid3__;
    /** CMU_AUID mapping */
    private int cmuAuid__;
    /** CMU_ADATE mapping */
    private UDate cmuAdate__;
    /** CMU_EUID mapping */
    private int cmuEuid__;
    /** CMU_EDATE mapping */
    private UDate cmuEdate__;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>cmuUid1 を取得します。
     * @return cmuUid1
     */
    public String getCmuUid1() {
        return cmuUid1__;
    }
    /**
     * <p>cmuUid1 をセットします。
     * @param cmuUid1 cmuUid1
     */
    public void setCmuUid1(String cmuUid1) {
        cmuUid1__ = cmuUid1;
    }
    /**
     * <p>cmuUid2 を取得します。
     * @return cmuUid2
     */
    public String getCmuUid2() {
        return cmuUid2__;
    }
    /**
     * <p>cmuUid2 をセットします。
     * @param cmuUid2 cmuUid2
     */
    public void setCmuUid2(String cmuUid2) {
        cmuUid2__ = cmuUid2;
    }
    /**
     * <p>cmuUid3 を取得します。
     * @return cmuUid3
     */
    public String getCmuUid3() {
        return cmuUid3__;
    }
    /**
     * <p>cmuUid3 をセットします。
     * @param cmuUid3 cmuUid3
     */
    public void setCmuUid3(String cmuUid3) {
        cmuUid3__ = cmuUid3;
    }
    /**
     * <p>cmuAuid を取得します。
     * @return cmuAuid
     */
    public int getCmuAuid() {
        return cmuAuid__;
    }
    /**
     * <p>cmuAuid をセットします。
     * @param cmuAuid cmuAuid
     */
    public void setCmuAuid(int cmuAuid) {
        cmuAuid__ = cmuAuid;
    }
    /**
     * <p>cmuAdate を取得します。
     * @return cmuAdate
     */
    public UDate getCmuAdate() {
        return cmuAdate__;
    }
    /**
     * <p>cmuAdate をセットします。
     * @param cmuAdate cmuAdate
     */
    public void setCmuAdate(UDate cmuAdate) {
        cmuAdate__ = cmuAdate;
    }
    /**
     * <p>cmuEuid を取得します。
     * @return cmuEuid
     */
    public int getCmuEuid() {
        return cmuEuid__;
    }
    /**
     * <p>cmuEuid をセットします。
     * @param cmuEuid cmuEuid
     */
    public void setCmuEuid(int cmuEuid) {
        cmuEuid__ = cmuEuid;
    }
    /**
     * <p>cmuEdate を取得します。
     * @return cmuEdate
     */
    public UDate getCmuEdate() {
        return cmuEdate__;
    }
    /**
     * <p>cmuEdate をセットします。
     * @param cmuEdate cmuEdate
     */
    public void setCmuEdate(UDate cmuEdate) {
        cmuEdate__ = cmuEdate;
    }
}