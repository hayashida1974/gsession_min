package jp.groupsession.v2.wml.wml160kn;


/**
 * <br>[機  能] WEBメール アカウントの使用者(ユーザ or グループ)、代理人に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160knUserDataModel {

    /** ユーザSID */
    private int usrSid__ = 0;
    /** ユーザ名 姓 */
    private String usiSei__ = null;
    /** ユーザ名 名 */
    private String usiMei__ = null;

    /** グループSID */
    private int grpSid__ = 0;
    /** グループ名 */
    private String grpName__ = null;
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>usiSei を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei をセットします。
     * @param usiSei usiSei
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * <p>usiMei を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei をセットします。
     * @param usiMei usiMei
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }
    /**
     * <p>grpName を取得します。
     * @return grpName
     */
    public String getGrpName() {
        return grpName__;
    }
    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }
}
