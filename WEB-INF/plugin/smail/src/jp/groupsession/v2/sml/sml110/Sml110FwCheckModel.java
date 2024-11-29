package jp.groupsession.v2.sml.sml110;

/**
 * <br>[機  能] ショートメール 管理者設定 転送設定画面 不正転送先情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml110FwCheckModel {

    /** 転送区分 */
    private int fwKbn__;
    /** ユーザSID */
    private int usrSid__;
    /** ユーザ名 姓 */
    private String usrNameSei__;
    /** ユーザ名 名 */
    private String usrNameMei__;
    /** 転送先アドレス */
    private String fwAddDf__;
    /** 転送先アドレス */
    private String fwAdd1__;
    /** 転送先アドレス */
    private String fwAdd2__;
    /** 転送先アドレス */
    private String fwAdd3__;


    /**
     * <p>fwAdd1 を取得します。
     * @return fwAdd1
     */
    public String getFwAdd1() {
        return fwAdd1__;
    }
    /**
     * <p>fwAdd1 をセットします。
     * @param fwAdd1 fwAdd1
     */
    public void setFwAdd1(String fwAdd1) {
        fwAdd1__ = fwAdd1;
    }
    /**
     * <p>fwAdd2 を取得します。
     * @return fwAdd2
     */
    public String getFwAdd2() {
        return fwAdd2__;
    }
    /**
     * <p>fwAdd2 をセットします。
     * @param fwAdd2 fwAdd2
     */
    public void setFwAdd2(String fwAdd2) {
        fwAdd2__ = fwAdd2;
    }
    /**
     * <p>fwAdd3 を取得します。
     * @return fwAdd3
     */
    public String getFwAdd3() {
        return fwAdd3__;
    }
    /**
     * <p>fwAdd3 をセットします。
     * @param fwAdd3 fwAdd3
     */
    public void setFwAdd3(String fwAdd3) {
        fwAdd3__ = fwAdd3;
    }
    /**
     * <p>fwAddDf を取得します。
     * @return fwAddDf
     */
    public String getFwAddDf() {
        return fwAddDf__;
    }
    /**
     * <p>fwAddDf をセットします。
     * @param fwAddDf fwAddDf
     */
    public void setFwAddDf(String fwAddDf) {
        fwAddDf__ = fwAddDf;
    }
    /**
     * <p>usrNameMei を取得します。
     * @return usrNameMei
     */
    public String getUsrNameMei() {
        return usrNameMei__;
    }
    /**
     * <p>usrNameMei をセットします。
     * @param usrNameMei usrNameMei
     */
    public void setUsrNameMei(String usrNameMei) {
        usrNameMei__ = usrNameMei;
    }
    /**
     * <p>usrNameSei を取得します。
     * @return usrNameSei
     */
    public String getUsrNameSei() {
        return usrNameSei__;
    }
    /**
     * <p>usrNameSei をセットします。
     * @param usrNameSei usrNameSei
     */
    public void setUsrNameSei(String usrNameSei) {
        usrNameSei__ = usrNameSei;
    }
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
     * <p>fwKbn を取得します。
     * @return fwKbn
     */
    public int getFwKbn() {
        return fwKbn__;
    }
    /**
     * <p>fwKbn をセットします。
     * @param fwKbn fwKbn
     */
    public void setFwKbn(int fwKbn) {
        fwKbn__ = fwKbn;
    }
}
