package jp.groupsession.v2.usr.usr050;

import java.sql.Connection;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.model.ValidatePasswordModel;

/**
 * <br>[機  能] メイン 個人設定 パスワード変更画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr050Form extends AbstractGsForm {

    /** ユーザSID */
    private int usr050UserSid__;
    /** パスワード(旧) */
    private String usr050OldPassWord__;
    /** パスワード(新) */
    private String usr050NewPassWord__;
    /** パスワード(新 確認) */
    private String usr050NewPassWordKn__;
    /** 英数字混在区分 */
    private int usr050CoeKbn__ = GSConstMain.PWC_COEKBN_OFF;
    /** 大文字小文字混在区分 */
    private int usr050UppercaseKbn__ = GSConstMain.PWC_UPPERCASE_OFF;
    /** パスワード桁数 */
    private int usr050Digit__ = GSConstMain.DEFAULT_DIGIT;
    /** ユーザID同一パスワード設定区分 */
    private int usr050UidPswdKbn__ = GSConstMain.PWC_UIDPSWDKBN_OFF;
    /** 旧パスワード設定区分 */
    private int usr050OldPswdKbn__ = GSConstMain.PWC_OLDPSWDKBN_OFF;
    /** パスワード変更画面モード */
    private int usr050Mode__ = GSConstUser.PSWD_MODE_NOMAL;

    /** 遷移元 メイン管理者メニュー:1 その他:0*/
    private int backScreen__ = 0;

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
     * <p>usr050UppercaseKbn を取得します。
     * @return usr050UppercaseKbn
     */
    public int getUsr050UppercaseKbn() {
        return usr050UppercaseKbn__;
    }
    /**
     * <p>usr050UppercaseKbn をセットします。
     * @param usr050UppercaseKbn usr050UppercaseKbn
     */
    public void setUsr050UppercaseKbn(int usr050UppercaseKbn) {
        usr050UppercaseKbn__ = usr050UppercaseKbn;
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
    /**
     * <p>backScreen を取得します。
     * @return backScreen
     * @see jp.groupsession.v2.usr.usr050.Usr050Form#backScreen__
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     * @see jp.groupsession.v2.usr.usr050.Usr050Form#backScreen__
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return errors エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validate(int userSid, Connection con, RequestModel reqMdl)
        throws Exception {

        ActionErrors errors = new ActionErrors();
        GSValidateUser gsValidate = new GSValidateUser(reqMdl);

        int checkUserSid = userSid;
        if (backScreen__ == GSConstMain.BACK_MAIN_ADM_SETTING) {
            checkUserSid = 0;
        }

        //旧パスワード
        gsValidate.validateOldPasswordMach(
                errors, con, checkUserSid, usr050OldPassWord__);

        // パスワードルール設定取得
        int coe = usr050CoeKbn__;
        int uppercase = usr050UppercaseKbn__;
        int digit = usr050Digit__;
        int uidPswdKbn = GSConstMain.PWC_UIDPSWDKBN_OFF;
        int oldPswdKbn = GSConstMain.PWC_OLDPSWDKBN_OFF;

        CmnPswdConfDao dao = new CmnPswdConfDao(con);
        CmnPswdConfModel model = dao.select();

        if (model != null) {
            uidPswdKbn = model.getPwcUidPswd();
            oldPswdKbn = model.getPwcOldPswd();
        }

        //新パスワード
        ValidatePasswordModel passModel = new ValidatePasswordModel();
        passModel.setCoe(coe);
        passModel.setUppercase(uppercase);
        passModel.setDigit(digit);
        passModel.setUidPswdKbn(uidPswdKbn);
        passModel.setOldPswdKbn(oldPswdKbn);
        passModel.setUserSid(checkUserSid);
        passModel.setOldPassword(usr050OldPassWord__);
        passModel.setPassword(usr050NewPassWord__);
        passModel.setPassword2(usr050NewPassWordKn__);

        gsValidate.validateNewPassword(con, errors, passModel);

        return errors;
    }
}