package jp.groupsession.v2.usr.model;

import java.io.Serializable;

/**
 * <br>[機  能] パスワードチェックの際必要となる情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ValidatePasswordModel implements Serializable {

    /** パスワード英数混在区分 */
    private int coe__;
    /** パスワード大文字小文字混在区分 */
    private int uppercase__;
    /** パスワード桁数 */
    private int digit__;
    /** ユーザIDと同一パスワード設定区分 */
    private int uidPswdKbn__;
    /** 旧パスワードと同一パスワード設定区分 */
    private int oldPswdKbn__;
    /** ユーザSID */
    private int userSid__;
    /** 旧パスワード */
    private String oldPassword__;
    /** 新パスワード */
    private String password__;
    /** 新パスワード確認用 */
    private String password2__;
    /**
     * <p>coe を取得します。
     * @return coe
     */
    public int getCoe() {
        return coe__;
    }
    /**
     * <p>coe をセットします。
     * @param coe coe
     */
    public void setCoe(int coe) {
        coe__ = coe;
    }
    /**
     * <p>uppercase を取得します。
     * @return uppercase
     */
    public int getUppercase() {
        return uppercase__;
    }
    /**
     * <p>uppercase をセットします。
     * @param uppercase uppercase
     */
    public void setUppercase(int uppercase) {
        uppercase__ = uppercase;
    }
    /**
     * <p>digit を取得します。
     * @return digit
     */
    public int getDigit() {
        return digit__;
    }
    /**
     * <p>digit をセットします。
     * @param digit digit
     */
    public void setDigit(int digit) {
        digit__ = digit;
    }
    /**
     * <p>uidPswdKbn を取得します。
     * @return uidPswdKbn
     */
    public int getUidPswdKbn() {
        return uidPswdKbn__;
    }
    /**
     * <p>uidPswdKbn をセットします。
     * @param uidPswdKbn uidPswdKbn
     */
    public void setUidPswdKbn(int uidPswdKbn) {
        uidPswdKbn__ = uidPswdKbn;
    }
    /**
     * <p>oldPswdKbn を取得します。
     * @return oldPswdKbn
     */
    public int getOldPswdKbn() {
        return oldPswdKbn__;
    }
    /**
     * <p>oldPswdKbn をセットします。
     * @param oldPswdKbn oldPswdKbn
     */
    public void setOldPswdKbn(int oldPswdKbn) {
        oldPswdKbn__ = oldPswdKbn;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>oldPassword を取得します。
     * @return oldPassword
     */
    public String getOldPassword() {
        return oldPassword__;
    }
    /**
     * <p>oldPassword をセットします。
     * @param oldPassword oldPassword
     */
    public void setOldPassword(String oldPassword) {
        oldPassword__ = oldPassword;
    }
    /**
     * <p>password を取得します。
     * @return password
     */
    public String getPassword() {
        return password__;
    }
    /**
     * <p>password をセットします。
     * @param password password
     */
    public void setPassword(String password) {
        password__ = password;
    }
    /**
     * <p>password2 を取得します。
     * @return password2
     */
    public String getPassword2() {
        return password2__;
    }
    /**
     * <p>password2 をセットします。
     * @param password2 password2
     */
    public void setPassword2(String password2) {
        password2__ = password2;
    }


}
