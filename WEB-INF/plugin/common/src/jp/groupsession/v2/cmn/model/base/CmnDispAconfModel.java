package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>CMN_DISP_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDispAconfModel implements Serializable {

    /** CDA_ROKUYOU_KBN mapping */
    private int cdaRokuyouKbn__;
    /** CDA_ROKUYOU mapping */
    private int cdaRokuyou__;

    /**
     * <p>Default Constructor
     */
    public CmnDispAconfModel() {
    }

    /**
     * <p>cdaRokuyouKbn を取得します。
     * @return cdaRokuyouKbn
     * @see jp.groupsession.v2.cmn.model.base.CmnDispAconfModel#cdaRokuyouKbn__
     */
    public int getCdaRokuyouKbn() {
        return cdaRokuyouKbn__;
    }
    /**
     * <p>cdaRokuyouKbn をセットします。
     * @param cdaRokuyouKbn cdaRokuyouKbn
     * @see jp.groupsession.v2.cmn.model.base.CmnDispAconfModel#cdaRokuyouKbn__
     */
    public void setCdaRokuyouKbn(int cdaRokuyouKbn) {
        cdaRokuyouKbn__ = cdaRokuyouKbn;
    }
    /**
     * <p>get CDA_ROKUYOU value
     * @return CDA_ROKUYOU value
     */
    public int getCdaRokuyou() {
        return cdaRokuyou__;
    }
    /**
     * <p>set CDA_ROKUYOU value
     * @param cdaRokuyou CDA_ROKUYOU value
     */
    public void setCdaRokuyou(int cdaRokuyou) {
        cdaRokuyou__ = cdaRokuyou;
    }
}
