package jp.groupsession.v2.sml.sml380;

/**
 *
 * <br>[機  能] 送信先制限設定表示モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml380DataModel {
    /**送信先制限設定SID*/
    private int sbcSid__;
    /** 表示用送信先制限設定名*/
    private String dspSbcName__;
    /** 表示用送信先制限設定名*/
    private String dspSbcBiko__;

    /**
     * <p>sbcSid を取得します。
     * @return sbcSid
     */
    public int getSbcSid() {
        return sbcSid__;
    }
    /**
     * <p>sbcSid をセットします。
     * @param sbcSid sbcSid
     */
    public void setSbcSid(int sbcSid) {
        sbcSid__ = sbcSid;
    }
    /**
     * <p>dspSbcName を取得します。
     * @return dspSbcName
     */
    public String getDspSbcName() {
        return dspSbcName__;
    }
    /**
     * <p>dspSbcName をセットします。
     * @param dspSbcName dspSbcName
     */
    public void setDspSbcName(String dspSbcName) {
        dspSbcName__ = dspSbcName;
    }
    /**
     * <p>dspSbcBiko を取得します。
     * @return dspSbcBiko
     */
    public String getDspSbcBiko() {
        return dspSbcBiko__;
    }
    /**
     * <p>dspSbcBiko をセットします。
     * @param dspSbcBiko dspSbcBiko
     */
    public void setDspSbcBiko(String dspSbcBiko) {
        dspSbcBiko__ = dspSbcBiko;
    }


}
