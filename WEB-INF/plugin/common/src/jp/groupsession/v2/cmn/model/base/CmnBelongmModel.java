package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;

import java.io.Serializable;

/**
 * <p>CMN_BELONGM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBelongmModel implements Serializable {
    /** デフォルトグループフラグ 通常 */
    public static final int DEFGRP_FLG_NORMAL = 0;
    /** デフォルトグループフラグ デフォルトグループ */
    public static final int DEFGRP_FLG_DEFAULT = 1;

    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** BEG_AUID mapping */
    private int begAuid__;
    /** BEG_ADATE mapping */
    private UDate begAdate__;
    /** BEG_EUID mapping */
    private int begEuid__;
    /** BEG_EDATE mapping */
    private UDate begEdate__;
    /** BEG_DEFGRP */
    private int begDefgrp__;
    /** BEG_GRPKBN */
    private int begGrpkbn__ = GSConst.USER_NOT_ADMIN;

    /**
     * <p>Default Constructor
     */
    public CmnBelongmModel() {
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
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
     * <p>get BEG_AUID value
     * @return BEG_AUID value
     */
    public int getBegAuid() {
        return begAuid__;
    }

    /**
     * <p>set BEG_AUID value
     * @param begAuid BEG_AUID value
     */
    public void setBegAuid(int begAuid) {
        begAuid__ = begAuid;
    }

    /**
     * <p>get BEG_ADATE value
     * @return BEG_ADATE value
     */
    public UDate getBegAdate() {
        return begAdate__;
    }

    /**
     * <p>set BEG_ADATE value
     * @param begAdate BEG_ADATE value
     */
    public void setBegAdate(UDate begAdate) {
        begAdate__ = begAdate;
    }

    /**
     * <p>get BEG_EUID value
     * @return BEG_EUID value
     */
    public int getBegEuid() {
        return begEuid__;
    }

    /**
     * <p>set BEG_EUID value
     * @param begEuid BEG_EUID value
     */
    public void setBegEuid(int begEuid) {
        begEuid__ = begEuid;
    }

    /**
     * <p>get BEG_EDATE value
     * @return BEG_EDATE value
     */
    public UDate getBegEdate() {
        return begEdate__;
    }

    /**
     * <p>set BEG_EDATE value
     * @param begEdate BEG_EDATE value
     */
    public void setBegEdate(UDate begEdate) {
        begEdate__ = begEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(begAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(begAdate__, ""));
        buf.append(",");
        buf.append(begEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(begEdate__, ""));
        return buf.toString();
    }

    /**
     * @return begDefgrp を戻します。
     */
    public int getBegDefgrp() {
        return begDefgrp__;
    }

    /**
     * @param begDefgrp 設定する begDefgrp。
     */
    public void setBegDefgrp(int begDefgrp) {
        begDefgrp__ = begDefgrp;
    }

    /**
     * @return begGrpkbn を戻します。
     */
    public int getBegGrpkbn() {
        return begGrpkbn__;
    }

    /**
     * @param begGrpkbn 設定する begDefgrp。
     */
    public void setBegGrpkbn(int begGrpkbn) {
        begGrpkbn__ = begGrpkbn;
    }

}
