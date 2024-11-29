package jp.groupsession.v2.usr.usr050;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 個人設定 パスワード変更画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr050ParamModel extends AbstractParamModel {

    /** ユーザSID */
    private int usr050UserSid__;
    /** パスワード(旧) */
    private String usr050OldPassWord__;
    /** パスワード(新) */
    private String usr050NewPassWord__;
    /** パスワード(新 確認) */
    private String usr050NewPassWordKn__;
    /** 英数混在区分 */
    private int usr050CoeKbn__ = GSConstMain.PWC_COEKBN_OFF;
    /** パスワード桁数 */
    private int usr050Digit__ = GSConstMain.DEFAULT_DIGIT;
    /** ユーザID同一パスワード設定区分 */
    private int usr050UidPswdKbn__ = GSConstMain.PWC_UIDPSWDKBN_OFF;
    /** 旧パスワード設定区分 */
    private int usr050OldPswdKbn__ = GSConstMain.PWC_OLDPSWDKBN_OFF;
    /** パスワード変更画面モード */
    private int usr050Mode__ = GSConstUser.PSWD_MODE_NOMAL;

    /**
     * @return usr050NewPassWord__ を戻します。
     */
    public String getUsr050NewPassWord() {
        return usr050NewPassWord__;
    }
    /**
     * @param usr050NewPassWord 設定する usr050NewPassWord__。
     */
    public void setUsr050NewPassWord(String usr050NewPassWord) {
        usr050NewPassWord__ = usr050NewPassWord;
    }
    /**
     * @return usr050NewPassWordKn__ を戻します。
     */
    public String getUsr050NewPassWordKn() {
        return usr050NewPassWordKn__;
    }
    /**
     * @param usr050NewPassWordKn 設定する usr050NewPassWordKn__。
     */
    public void setUsr050NewPassWordKn(String usr050NewPassWordKn) {
        usr050NewPassWordKn__ = usr050NewPassWordKn;
    }
    /**
     * @return usr050OldPassWord__ を戻します。
     */
    public String getUsr050OldPassWord() {
        return usr050OldPassWord__;
    }
    /**
     * @param usr050OldPassWord 設定する usr050OldPassWord__。
     */
    public void setUsr050OldPassWord(String usr050OldPassWord) {
        usr050OldPassWord__ = usr050OldPassWord;
    }
    /**
     * @return usr050UserSid__ を戻します。
     */
    public int getUsr050UserSid() {
        return usr050UserSid__;
    }
    /**
     * @param usr050UserSid 設定する usr050UserSid__。
     */
    public void setUsr050UserSid(int usr050UserSid) {
        usr050UserSid__ = usr050UserSid;
    }
    /**
     * <p>usr050CoeKbn を取得します。
     * @return usr050CoeKbn
     */
    public int getUsr050CoeKbn() {
        return usr050CoeKbn__;
    }
    /**
     * <p>usr050CoeKbn をセットします。
     * @param usr050CoeKbn usr050CoeKbn
     */
    public void setUsr050CoeKbn(int usr050CoeKbn) {
        usr050CoeKbn__ = usr050CoeKbn;
    }
    /**
     * <p>usr050Digit を取得します。
     * @return usr050Digit
     */
    public int getUsr050Digit() {
        return usr050Digit__;
    }
    /**
     * <p>usr050Digit をセットします。
     * @param usr050Digit usr050Digit
     */
    public void setUsr050Digit(int usr050Digit) {
        usr050Digit__ = usr050Digit;
    }
    /**
     * <p>usr050Mode を取得します。
     * @return usr050Mode
     */
    public int getUsr050Mode() {
        return usr050Mode__;
    }
    /**
     * <p>usr050Mode をセットします。
     * @param usr050Mode usr050Mode
     */
    public void setUsr050Mode(int usr050Mode) {
        usr050Mode__ = usr050Mode;
    }
    /**
     * <p>usr050OldPswdKbn を取得します。
     * @return usr050OldPswdKbn
     */
    public int getUsr050OldPswdKbn() {
        return usr050OldPswdKbn__;
    }
    /**
     * <p>usr050OldPswdKbn をセットします。
     * @param usr050OldPswdKbn usr050OldPswdKbn
     */
    public void setUsr050OldPswdKbn(int usr050OldPswdKbn) {
        usr050OldPswdKbn__ = usr050OldPswdKbn;
    }
    /**
     * <p>usr050UidPswdKbn を取得します。
     * @return usr050UidPswdKbn
     */
    public int getUsr050UidPswdKbn() {
        return usr050UidPswdKbn__;
    }
    /**
     * <p>usr050UidPswdKbn をセットします。
     * @param usr050UidPswdKbn usr050UidPswdKbn
     */
    public void setUsr050UidPswdKbn(int usr050UidPswdKbn) {
        usr050UidPswdKbn__ = usr050UidPswdKbn;
    }
}