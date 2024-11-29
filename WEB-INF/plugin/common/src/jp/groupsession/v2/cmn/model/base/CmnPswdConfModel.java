package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_TDFK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPswdConfModel implements Serializable {

    /** PWC_DIGIT mapping */
    private int pwcDigit__;
    /** PWC_COE mapping */
    private int pwcCoe__;
    /** PWC_UPPERCASE mapping */
    private int pwcUppercase__;
    /** PWC_LIMIT_DAY mapping */
    private int pwcLimitDay__;
    /** PWC_UIDPSWD_KBN mapping */
    private int pwcUidPswd__;
    /** PWC_OLDPSWD_KBN mapping */
    private int pwcOldPswd__;
    /** PWC_AUID mapping */
    private int pwcAuid__;
    /** PWC_ADATE mapping */
    private UDate pwcAdate__;
    /** PWC_EUID mapping */
    private int pwcEuid__;
    /** PWC_EDATE mapping */
    private UDate pwcEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnPswdConfModel() {
    }

    /**
     * <p>pwcAdate を取得します。
     * @return pwcAdate
     */
    public UDate getPwcAdate() {
        return pwcAdate__;
    }

    /**
     * <p>pwcAdate をセットします。
     * @param pwcAdate pwcAdate
     */
    public void setPwcAdate(UDate pwcAdate) {
        pwcAdate__ = pwcAdate;
    }

    /**
     * <p>pwcAuid を取得します。
     * @return pwcAuid
     */
    public int getPwcAuid() {
        return pwcAuid__;
    }

    /**
     * <p>pwcAuid をセットします。
     * @param pwcAuid pwcAuid
     */
    public void setPwcAuid(int pwcAuid) {
        pwcAuid__ = pwcAuid;
    }

    /**
     * <p>pwcUppercase を取得します。
     * @return pwcUppercase
     */
    public int getPwcUppercase() {
        return pwcUppercase__;
    }

    /**
     * <p>pwcCoe をセットします。
     * @param pwcUppercase pwcUppercase
     */
    public void setPwcUppercase(int pwcUppercase) {
        pwcUppercase__ = pwcUppercase;
    }

    /**
     * <p>pwcCoe を取得します。
     * @return pwcCoe
     */
    public int getPwcCoe() {
        return pwcCoe__;
    }

    /**
     * <p>pwcCoe をセットします。
     * @param pwcCoe pwcCoe
     */
    public void setPwcCoe(int pwcCoe) {
        pwcCoe__ = pwcCoe;
    }

    /**
     * <p>pwcDigit を取得します。
     * @return pwcDigit
     */
    public int getPwcDigit() {
        return pwcDigit__;
    }

    /**
     * <p>pwcDigit をセットします。
     * @param pwcDigit pwcDigit
     */
    public void setPwcDigit(int pwcDigit) {
        pwcDigit__ = pwcDigit;
    }

    /**
     * <p>pwcEdate を取得します。
     * @return pwcEdate
     */
    public UDate getPwcEdate() {
        return pwcEdate__;
    }

    /**
     * <p>pwcEdate をセットします。
     * @param pwcEdate pwcEdate
     */
    public void setPwcEdate(UDate pwcEdate) {
        pwcEdate__ = pwcEdate;
    }

    /**
     * <p>pwcEuid を取得します。
     * @return pwcEuid
     */
    public int getPwcEuid() {
        return pwcEuid__;
    }

    /**
     * <p>pwcEuid をセットします。
     * @param pwcEuid pwcEuid
     */
    public void setPwcEuid(int pwcEuid) {
        pwcEuid__ = pwcEuid;
    }

    /**
     * <p>pwcLimitDay を取得します。
     * @return pwcLimitDay
     */
    public int getPwcLimitDay() {
        return pwcLimitDay__;
    }

    /**
     * <p>pwcLimitDay をセットします。
     * @param pwcLimitDay pwcLimitDay
     */
    public void setPwcLimitDay(int pwcLimitDay) {
        pwcLimitDay__ = pwcLimitDay;
    }

    /**
     * <p>pwcOldPswd を取得します。
     * @return pwcOldPswd
     */
    public int getPwcOldPswd() {
        return pwcOldPswd__;
    }

    /**
     * <p>pwcOldPswd をセットします。
     * @param pwcOldPswd pwcOldPswd
     */
    public void setPwcOldPswd(int pwcOldPswd) {
        pwcOldPswd__ = pwcOldPswd;
    }

    /**
     * <p>pwcUidPswd を取得します。
     * @return pwcUidPswd
     */
    public int getPwcUidPswd() {
        return pwcUidPswd__;
    }

    /**
     * <p>pwcUidPswd をセットします。
     * @param pwcUidPswd pwcUidPswd
     */
    public void setPwcUidPswd(int pwcUidPswd) {
        pwcUidPswd__ = pwcUidPswd;
    }
}
