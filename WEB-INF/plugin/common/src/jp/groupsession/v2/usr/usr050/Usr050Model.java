package jp.groupsession.v2.usr.usr050;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] パスワード情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr050Model extends AbstractModel {

    /** ユーザSID */
    private int usrSid__;
    /** パスワード(旧) */
    private String usrOldPassWord__;
    /** パスワード(新) */
    private String usrNewPassWord__;
    /** パスワード変更画面モード */
    private int usrMode__;

    /**
     * @return usrSid__ を戻します。
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid__。
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * @return usrNewPassWord__ を戻します。
     */
    public String getUsrNewPassWord() {
        return usrNewPassWord__;
    }
    /**
     * @param usrNewPassWord 設定する usrNewPassWord__。
     */
    public void setUsrNewPassWord(String usrNewPassWord) {
        usrNewPassWord__ = usrNewPassWord;
    }
    /**
     * @return usrOldPassWord__ を戻します。
     */
    public String getUsrOldPassWord() {
        return usrOldPassWord__;
    }
    /**
     * @param usrOldPassWord 設定する usrOldPassWord__。
     */
    public void setUsrOldPassWord(String usrOldPassWord) {
        usrOldPassWord__ = usrOldPassWord;
    }
    /**
     * <p>usrMode を取得します。
     * @return usrMode
     */
    public int getUsrMode() {
        return usrMode__;
    }
    /**
     * <p>usrMode をセットします。
     * @param usrMode usrMode
     */
    public void setUsrMode(int usrMode) {
        usrMode__ = usrMode;
    }
}