package jp.groupsession.v2.wml.wml010.model;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] WEBメール メールアドレス情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010AddressModel extends AbstractModel {

    /** ユーザSID */
    private int userSid__ = 0;
    /** ユーザ名 */
    private String userName__ = null;
    /** ログイン停止フラグ */
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;

    /** メールアドレス1 */
    private String mail1__ = null;
    /** メールアドレス2 */
    private String mail2__ = null;
    /** メールアドレス3 */
    private String mail3__ = null;

    /**
     * <p>mail1 を取得します。
     * @return mail1
     */
    public String getMail1() {
        return mail1__;
    }
    /**
     * <p>mail1 をセットします。
     * @param mail1 mail1
     */
    public void setMail1(String mail1) {
        mail1__ = mail1;
    }
    /**
     * <p>mail2 を取得します。
     * @return mail2
     */
    public String getMail2() {
        return mail2__;
    }
    /**
     * <p>mail2 をセットします。
     * @param mail2 mail2
     */
    public void setMail2(String mail2) {
        mail2__ = mail2;
    }
    /**
     * <p>mail3 を取得します。
     * @return mail3
     */
    public String getMail3() {
        return mail3__;
    }
    /**
     * <p>mail3 をセットします。
     * @param mail3 mail3
     */
    public void setMail3(String mail3) {
        mail3__ = mail3;
    }
    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }
    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
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
     * <br>[機  能] メールアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メールアドレス
     */
    public String getMailAddress() {
        if (!StringUtil.isNullZeroString(getMail1())) {
            return getMail1();
        } else if (!StringUtil.isNullZeroString(getMail2())) {
            return getMail2();
        }

        return NullDefault.getString(getMail3(), "");
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
}
