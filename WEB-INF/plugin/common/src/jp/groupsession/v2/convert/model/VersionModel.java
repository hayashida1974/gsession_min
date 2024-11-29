package jp.groupsession.v2.convert.model;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_VER_INFO Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class VersionModel implements Serializable {

    /** VER_VERSION mapping */
    private String verVersion__;

    /**
     * <p>Default Constructor
     */
    public VersionModel() {
    }

    /**
     * <p>get VER_VERSION value
     * @return VER_VERSION value
     */
    public String getVerVersion() {
        return verVersion__;
    }

    /**
     * <p>set VER_VERSION value
     * @param verVersion VER_VERSION value
     */
    public void setVerVersion(String verVersion) {
        verVersion__ = verVersion;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getString(verVersion__, ""));
        return buf.toString();
    }

}
