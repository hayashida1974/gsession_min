package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_FILE_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnFileConfModel implements Serializable {

    /** FIC_MAX_SIZE mapping */
    private int ficMaxSize__;
    /** FIC_AUID mapping */
    private int ficAuid__;
    /** FIC_ADATE mapping */
    private UDate ficAdate__;
    /** FIC_EUID mapping */
    private int ficEuid__;
    /** FIC_EDATE mapping */
    private UDate ficEdate__;
    /** FIC_PHOTO_SIZE mapping */
    private String ficPhotoSize__;

    /**
     * <p>Default Constructor
     */
    public CmnFileConfModel() {
    }

    /**
     * <p>get FIC_MAX_SIZE value
     * @return FIC_MAX_SIZE value
     */
    public int getFicMaxSize() {
        return ficMaxSize__;
    }

    /**
     * <p>set FIC_MAX_SIZE value
     * @param ficMaxSize FIC_MAX_SIZE value
     */
    public void setFicMaxSize(int ficMaxSize) {
        ficMaxSize__ = ficMaxSize;
    }

    /**
     * <p>get FIC_AUID value
     * @return FIC_AUID value
     */
    public int getFicAuid() {
        return ficAuid__;
    }

    /**
     * <p>set FIC_AUID value
     * @param ficAuid FIC_AUID value
     */
    public void setFicAuid(int ficAuid) {
        ficAuid__ = ficAuid;
    }

    /**
     * <p>get FIC_ADATE value
     * @return FIC_ADATE value
     */
    public UDate getFicAdate() {
        return ficAdate__;
    }

    /**
     * <p>set FIC_ADATE value
     * @param ficAdate FIC_ADATE value
     */
    public void setFicAdate(UDate ficAdate) {
        ficAdate__ = ficAdate;
    }

    /**
     * <p>get FIC_EUID value
     * @return FIC_EUID value
     */
    public int getFicEuid() {
        return ficEuid__;
    }

    /**
     * <p>set FIC_EUID value
     * @param ficEuid FIC_EUID value
     */
    public void setFicEuid(int ficEuid) {
        ficEuid__ = ficEuid;
    }

    /**
     * <p>get FIC_EDATE value
     * @return FIC_EDATE value
     */
    public UDate getFicEdate() {
        return ficEdate__;
    }

    /**
     * <p>set FIC_EDATE value
     * @param ficEdate FIC_EDATE value
     */
    public void setFicEdate(UDate ficEdate) {
        ficEdate__ = ficEdate;
    }

    /**
     * <p>ficPhotoSize を取得します。
     * @return ficPhotoSize
     */
    public String getFicPhotoSize() {
        return ficPhotoSize__;
    }

    /**
     * <p>ficPhotoSize をセットします。
     * @param ficPhotoSize ficPhotoSize
     */
    public void setFicPhotoSize(String ficPhotoSize) {
        ficPhotoSize__ = ficPhotoSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(ficMaxSize__);
        buf.append(",");
        buf.append(ficAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ficAdate__, ""));
        buf.append(",");
        buf.append(ficEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ficEdate__, ""));
        buf.append(",");
        buf.append(ficPhotoSize__);
        return buf.toString();
    }

}
