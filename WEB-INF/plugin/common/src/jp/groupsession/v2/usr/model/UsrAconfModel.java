package jp.groupsession.v2.usr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>USR_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class UsrAconfModel implements Serializable {

    /** UAD_EXPORT mapping */
    private int uadExport__;
    /** UAD_AUID mapping */
    private int uadAuid__;
    /** UAD_ADATE mapping */
    private UDate uadAdate__;
    /** UAD_EUID mapping */
    private int uadEuid__;
    /** UAD_EDATE mapping */
    private UDate uadEdate__;
    /** UAD_QR_KBN mapping*/
    private int uadQrKbn__;

    /**
     * <p>Default Constructor
     */
    public UsrAconfModel() {
    }

    /**
     * <p>get UAD_EXPORT value
     * @return UAD_EXPORT value
     */
    public int getUadExport() {
        return uadExport__;
    }

    /**
     * <p>set UAD_EXPORT value
     * @param uadExport UAD_EXPORT value
     */
    public void setUadExport(int uadExport) {
        uadExport__ = uadExport;
    }

    /**
     * <p>get UAD_AUID value
     * @return UAD_AUID value
     */
    public int getUadAuid() {
        return uadAuid__;
    }

    /**
     * <p>set UAD_AUID value
     * @param uadAuid UAD_AUID value
     */
    public void setUadAuid(int uadAuid) {
        uadAuid__ = uadAuid;
    }

    /**
     * <p>get UAD_ADATE value
     * @return UAD_ADATE value
     */
    public UDate getUadAdate() {
        return uadAdate__;
    }

    /**
     * <p>set UAD_ADATE value
     * @param uadAdate UAD_ADATE value
     */
    public void setUadAdate(UDate uadAdate) {
        uadAdate__ = uadAdate;
    }

    /**
     * <p>get UAD_EUID value
     * @return UAD_EUID value
     */
    public int getUadEuid() {
        return uadEuid__;
    }

    /**
     * <p>set UAD_EUID value
     * @param uadEuid UAD_EUID value
     */
    public void setUadEuid(int uadEuid) {
        uadEuid__ = uadEuid;
    }

    /**
     * <p>get UAD_EDATE value
     * @return UAD_EDATE value
     */
    public UDate getUadEdate() {
        return uadEdate__;
    }

    /**
     * <p>set UAD_EDATE value
     * @param uadEdate UAD_EDATE value
     */
    public void setUadEdate(UDate uadEdate) {
        uadEdate__ = uadEdate;
    }

    /**
     * <p>uadQrKbn を取得します。
     * @return uadQrKbn
     */
    public int getUadQrKbn() {
        return uadQrKbn__;
    }

    /**
     * <p>uadQrKbn をセットします。
     * @param uadQrKbn uadQrKbn
     */
    public void setUadQrKbn(int uadQrKbn) {
        uadQrKbn__ = uadQrKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(uadExport__);
        buf.append(",");
        buf.append(uadAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(uadAdate__, ""));
        buf.append(",");
        buf.append(uadEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(uadEdate__, ""));
        return buf.toString();
    }

}
