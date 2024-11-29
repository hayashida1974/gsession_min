package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_POSITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPositionModel implements Serializable {

    /** POS_SID mapping */
    private int posSid__;
    /** POS_CODE mapping */
    private String posCode__;
    /** POS_NAME mapping */
    private String posName__;
    /** POS_BIKO mapping */
    private String posBiko__;
    /** POS_SORT mapping */
    private int posSort__;
    /** POS_AUID mapping */
    private int posAuid__;
    /** POS_ADATE mapping */
    private UDate posAdate__;
    /** POS_EUID mapping */
    private int posEuid__;
    /** POS_EDATE mapping */
    private UDate posEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnPositionModel() {
    }

    /**
     * <p>get POS_SID value
     * @return POS_SID value
     */
    public int getPosSid() {
        return posSid__;
    }

    /**
     * <p>set POS_SID value
     * @param posSid POS_SID value
     */
    public void setPosSid(int posSid) {
        posSid__ = posSid;
    }

    /**
     * <p>posCode を取得します。
     * @return posCode
     */
    public String getPosCode() {
        return posCode__;
    }

    /**
     * <p>posCode をセットします。
     * @param posCode posCode
     */
    public void setPosCode(String posCode) {
        posCode__ = posCode;
    }

    /**
     * <p>get POS_NAME value
     * @return POS_NAME value
     */
    public String getPosName() {
        return posName__;
    }

    /**
     * <p>set POS_NAME value
     * @param posName POS_NAME value
     */
    public void setPosName(String posName) {
        posName__ = posName;
    }

    /**
     * <p>get POS_BIKO value
     * @return POS_BIKO value
     */
    public String getPosBiko() {
        return posBiko__;
    }

    /**
     * <p>set POS_BIKO value
     * @param posBiko POS_BIKO value
     */
    public void setPosBiko(String posBiko) {
        posBiko__ = posBiko;
    }

    /**
     * <p>get POS_SORT value
     * @return POS_SORT value
     */
    public int getPosSort() {
        return posSort__;
    }

    /**
     * <p>set POS_SORT value
     * @param posSort POS_SORT value
     */
    public void setPosSort(int posSort) {
        posSort__ = posSort;
    }

    /**
     * <p>get POS_AUID value
     * @return POS_AUID value
     */
    public int getPosAuid() {
        return posAuid__;
    }

    /**
     * <p>set POS_AUID value
     * @param posAuid POS_AUID value
     */
    public void setPosAuid(int posAuid) {
        posAuid__ = posAuid;
    }

    /**
     * <p>get POS_ADATE value
     * @return POS_ADATE value
     */
    public UDate getPosAdate() {
        return posAdate__;
    }

    /**
     * <p>set POS_ADATE value
     * @param posAdate POS_ADATE value
     */
    public void setPosAdate(UDate posAdate) {
        posAdate__ = posAdate;
    }

    /**
     * <p>get POS_EUID value
     * @return POS_EUID value
     */
    public int getPosEuid() {
        return posEuid__;
    }

    /**
     * <p>set POS_EUID value
     * @param posEuid POS_EUID value
     */
    public void setPosEuid(int posEuid) {
        posEuid__ = posEuid;
    }

    /**
     * <p>get POS_EDATE value
     * @return POS_EDATE value
     */
    public UDate getPosEdate() {
        return posEdate__;
    }

    /**
     * <p>set POS_EDATE value
     * @param posEdate POS_EDATE value
     */
    public void setPosEdate(UDate posEdate) {
        posEdate__ = posEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(posSid__);
        buf.append(",");
        buf.append(posCode__);
        buf.append(",");
        buf.append(NullDefault.getString(posName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(posBiko__, ""));
        buf.append(",");
        buf.append(posSort__);
        buf.append(",");
        buf.append(posAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(posAdate__, ""));
        buf.append(",");
        buf.append(posEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(posEdate__, ""));
        return buf.toString();
    }

}
