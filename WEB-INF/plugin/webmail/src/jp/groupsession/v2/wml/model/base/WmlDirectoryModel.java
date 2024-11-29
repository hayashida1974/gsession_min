package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_DIRECTORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDirectoryModel implements Serializable {

    /** WDR_SID mapping */
    private long wdrSid__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WDR_NAME mapping */
    private String wdrName__;
    /** WDR_TYPE mapping */
    private int wdrType__;
    /** WDR_VIEW mapping */
    private int wdrView__;
    /** WDR_DEFAULT mapping */
    private int wdrDefault__;

    /**
     * <p>Default Constructor
     */
    public WmlDirectoryModel() {
    }

    /**
     * <p>get WDR_SID value
     * @return WDR_SID value
     */
    public long getWdrSid() {
        return wdrSid__;
    }

    /**
     * <p>set WDR_SID value
     * @param wdrSid WDR_SID value
     */
    public void setWdrSid(long wdrSid) {
        wdrSid__ = wdrSid;
    }

    /**
     * <p>get WAC_SID value
     * @return WAC_SID value
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>set WAC_SID value
     * @param wacSid WAC_SID value
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>get WDR_NAME value
     * @return WDR_NAME value
     */
    public String getWdrName() {
        return wdrName__;
    }

    /**
     * <p>set WDR_NAME value
     * @param wdrName WDR_NAME value
     */
    public void setWdrName(String wdrName) {
        wdrName__ = wdrName;
    }

    /**
     * <p>get WDR_TYPE value
     * @return WDR_TYPE value
     */
    public int getWdrType() {
        return wdrType__;
    }

    /**
     * <p>set WDR_TYPE value
     * @param wdrType WDR_TYPE value
     */
    public void setWdrType(int wdrType) {
        wdrType__ = wdrType;
    }

    /**
     * <p>get WDR_VIEW value
     * @return WDR_VIEW value
     */
    public int getWdrView() {
        return wdrView__;
    }

    /**
     * <p>set WDR_VIEW value
     * @param wdrView WDR_VIEW value
     */
    public void setWdrView(int wdrView) {
        wdrView__ = wdrView;
    }

    /**
     * <p>get WDR_DEFAULT value
     * @return WDR_DEFAULT value
     */
    public int getWdrDefault() {
        return wdrDefault__;
    }

    /**
     * <p>set WDR_DEFAULT value
     * @param wdrDefault WDR_DEFAULT value
     */
    public void setWdrDefault(int wdrDefault) {
        wdrDefault__ = wdrDefault;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wdrSid__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wdrName__, ""));
        buf.append(",");
        buf.append(wdrType__);
        buf.append(",");
        buf.append(wdrView__);
        buf.append(",");
        buf.append(wdrDefault__);
        return buf.toString();
    }

}
