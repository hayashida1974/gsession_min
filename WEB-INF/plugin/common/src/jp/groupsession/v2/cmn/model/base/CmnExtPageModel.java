package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_EXT_PAGE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnExtPageModel implements Serializable {

    /** CEP_LIMIT_DSP mapping */
    private int cepLimitDsp__;
    /** CEP_AUID mapping */
    private int cepAuid__;
    /** CEP_ADATE mapping */
    private UDate cepAdate__;
    /** CEP_EUID mapping */
    private int cepEuid__;
    /** CEP_EDATE mapping */
    private UDate cepEdate__;


    /**
     * <p> get CEP_LIMIT_DSP value
     * @return CEP_LIMIT_DSP
     * */
    public int getCepLimitDsp() {
        return cepLimitDsp__;
    }
    /**
     * <p> set CEP_LIMIT_DSP value
     * @param cepLimitDsp CEP_LIMIT_DSP
     * */
    public void setCepLimitDsp(int cepLimitDsp) {
        cepLimitDsp__ = cepLimitDsp;
    }

    /**
     * <p> get CEP_AUID value
     * @return CEP_AUID
     * */
    public int getCepAuid() {
        return cepAuid__;
    }
    /**
     * <p> set CEP_AUID value
     * @param cepAuid CEP_AUID
     * */
    public void setCepAuid(int cepAuid) {
        cepAuid__ = cepAuid;
    }

    /**
     * <p> get CEP_ADATE value
     * @return CEP_ADATE
     * */
    public UDate getCepAdate() {
        return cepAdate__;
    }
    /**
     * <p> set CEP_ADATE value
     * @param cepAuid CEP_ADATE
     * */
    public void setCepAdate(UDate cepAdate) {
        cepAdate__ = cepAdate;
    }

    /**
     * <p> get CEP_EUID value
     * @return CEP_EUID
     * */
    public int getCepEuid() {
        return cepEuid__;
    }
    /**
     * <p> set CEP_EUID value
     * @param cepEuid CEP_EUID
     * */
    public void setCepEuid(int cepEuid) {
        cepEuid__ = cepEuid;
    }

    /**
     * <p> get CEP_EDATE value
     * @return CEP_EDATE
     * */
    public UDate getCepEdate() {
        return cepEdate__;
    }
    /**
     * <p> set CEP_EDATE value
     * @param cepEdate CEP_EDATE
     * */
    public void setCepEdate(UDate cepEdate) {
        cepEdate__ = cepEdate;
    }

}
