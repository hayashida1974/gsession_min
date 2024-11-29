package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_FWLIMIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFwlimitModel implements Serializable {

    /** WFL_TEXT mapping */
    private String wflText__;
    /** WFL_NO mapping */
    private int wflNo__;

    /**
     * <p>Default Constructor
     */
    public WmlFwlimitModel() {
    }

    /**
     * <p>get WFL_TEXT value
     * @return WFL_TEXT value
     */
    public String getWflText() {
        return wflText__;
    }

    /**
     * <p>set WFL_TEXT value
     * @param wflText WFL_TEXT value
     */
    public void setWflText(String wflText) {
        wflText__ = wflText;
    }

    /**
     * <p>get WFL_NO value
     * @return WFL_NO value
     */
    public int getWflNo() {
        return wflNo__;
    }

    /**
     * <p>set WFL_NO value
     * @param wflNo WFL_NO value
     */
    public void setWflNo(int wflNo) {
        wflNo__ = wflNo;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(wflText__, ""));
        buf.append(",");
        buf.append(wflNo__);
        return buf.toString();
    }

}
