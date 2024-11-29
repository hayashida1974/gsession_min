package jp.groupsession.v2.convert.convert202.model;

import java.io.Serializable;

/**
 * <p>CMN_CONTM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ConvContmModel implements Serializable {

    /** CNT_PXY_USE mapping */
    private int cntPxyUse__;
    /** CNT_PXY_URL mapping */
    private String cntPxyUrl__;
    /** CNT_PXY_PORT mapping */
    private int cntPxyPort__;
    /** CNT_MENU_STATIC mapping */
    private int cntMenuStatic__;

    /**
     * <p>Default Constructor
     */
    public ConvContmModel() {
    }

    /**
     * <p>cntMenuStatic を取得します。
     * @return cntMenuStatic
     */
    public int getCntMenuStatic() {
        return cntMenuStatic__;
    }

    /**
     * <p>cntMenuStatic をセットします。
     * @param cntMenuStatic cntMenuStatic
     */
    public void setCntMenuStatic(int cntMenuStatic) {
        cntMenuStatic__ = cntMenuStatic;
    }

    /**
     * <p>get CNT_PXY_USE value
     * @return CNT_PXY_USE value
     */
    public int getCntPxyUse() {
        return cntPxyUse__;
    }

    /**
     * <p>set CNT_PXY_USE value
     * @param cntPxyUse CNT_PXY_USE value
     */
    public void setCntPxyUse(int cntPxyUse) {
        cntPxyUse__ = cntPxyUse;
    }

    /**
     * <p>get CNT_PXY_URL value
     * @return CNT_PXY_URL value
     */
    public String getCntPxyUrl() {
        return cntPxyUrl__;
    }

    /**
     * <p>set CNT_PXY_URL value
     * @param cntPxyUrl CNT_PXY_URL value
     */
    public void setCntPxyUrl(String cntPxyUrl) {
        cntPxyUrl__ = cntPxyUrl;
    }

    /**
     * <p>get CNT_PXY_PORT value
     * @return CNT_PXY_PORT value
     */
    public int getCntPxyPort() {
        return cntPxyPort__;
    }

    /**
     * <p>set CNT_PXY_PORT value
     * @param cntPxyPort CNT_PXY_PORT value
     */
    public void setCntPxyPort(int cntPxyPort) {
        cntPxyPort__ = cntPxyPort;
    }
}