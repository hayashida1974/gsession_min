package jp.groupsession.v2.man.model.base;

import java.io.Serializable;

/**
 * <p>PTL_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlAdmConfModel implements Serializable {

    /** PAC_PTL_EDITKBN mapping */
    private int pacPtlEditkbn__;
    /** PAC_DEF_KBN mapping */
    private int pacDefKbn__;
    /** PAC_DEF_TYPE mapping */
    private int pacDefType__;

    /**
     * <p>Default Constructor
     */
    public PtlAdmConfModel() {
    }

    /**
     * <p>get PAC_PTL_EDITKBN value
     * @return PAC_PTL_EDITKBN value
     */
    public int getPacPtlEditkbn() {
        return pacPtlEditkbn__;
    }

    /**
     * <p>set PAC_PTL_EDITKBN value
     * @param pacPtlEditkbn PAC_PTL_EDITKBN value
     */
    public void setPacPtlEditkbn(int pacPtlEditkbn) {
        pacPtlEditkbn__ = pacPtlEditkbn;
    }

    /**
     * <p>get PAC_DEF_KBN value
     * @return PAC_DEF_KBN value
     */
    public int getPacDefKbn() {
        return pacDefKbn__;
    }

    /**
     * <p>set PAC_DEF_KBN value
     * @param pacDefKbn PAC_DEF_KBN value
     */
    public void setPacDefKbn(int pacDefKbn) {
        pacDefKbn__ = pacDefKbn;
    }

    /**
     * <p>get PAC_DEF_TYPE value
     * @return PAC_DEF_TYPE value
     */
    public int getPacDefType() {
        return pacDefType__;
    }

    /**
     * <p>set PAC_DEF_TYPE value
     * @param pacDefType PAC_DEF_TYPE value
     */
    public void setPacDefType(int pacDefType) {
        pacDefType__ = pacDefType;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(pacPtlEditkbn__);
        buf.append(",");
        buf.append(pacDefKbn__);
        buf.append(",");
        buf.append(pacDefType__);
        return buf.toString();
    }

}
