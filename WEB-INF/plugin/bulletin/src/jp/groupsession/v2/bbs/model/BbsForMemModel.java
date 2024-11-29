package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

/**
 * <p>BBS_FOR_MEM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsForMemModel implements Serializable {

    /** BFI_SID mapping */
    private int bfiSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** BFM_AUTH mapping */
    private int bfmAuth__;

    /**
     * <p>Default Constructor
     */
    public BbsForMemModel() {
    }

    /**
     * <p>get BFI_SID value
     * @return BFI_SID value
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>set BFI_SID value
     * @param bfiSid BFI_SID value
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
         return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

    /**
     * <p>bfmAuth を取得します。
     * @return bfmAuth
     */
    public int getBfmAuth() {
        return bfmAuth__;
    }

    /**
     * <p>bfmAuth をセットします。
     * @param bfmAuth bfmAuth
     */
    public void setBfmAuth(int bfmAuth) {
        bfmAuth__ = bfmAuth;
    }

}
