package jp.groupsession.v2.cmn.login.otp;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.AbstractModel;
/**
 *
 * <br>[機  能] ワンタイムパスワード送信結果格納モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OtpSendResult extends AbstractModel {
    /** エラーコード 正常*/
    public static final int ECODE_NONE = 0;
    /** エラーコード 送信失敗*/
    public static final int ECODE_SENDERROR = 1;
    /** エラーコード 送信先アドレス未設定 個人設定不可）*/
    public static final int ECODE_NOADDRES_CANTEDIT = 2;
    /** エラーコード 送信先アドレス未設定 個人設定可能）*/
    public static final int ECODE_NOADDRES_ABLEEDIT = 3;

    /**一次トークン or ワンタイムパスワード送信先登録画面トークン*/
    private String otpToken__;
    /**送信エラーコード*/
    private int ecode__;
    /** 送信先 アドレス*/
    private String otpSendMailTo__;
    /** 通知メールFrom*/
    private String otpSendMailFrom__;
    /** 通知メールタイトル*/
    private String otpSendMailSubject__;
    /** 通知メール送信日*/
    private UDate otpSendDate__;

    /**
     * <p>otpToken を取得します。
     * @return otpToken
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpToken__
     */
    public String getOtpToken() {
        return otpToken__;
    }
    /**
     * <p>otpToken をセットします。
     * @param otpToken otpToken
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpToken__
     */
    public void setOtpToken(String otpToken) {
        otpToken__ = otpToken;
    }
    /**
     * <p>ecode を取得します。
     * @return ecode
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#ecode__
     */
    public int getEcode() {
        return ecode__;
    }
    /**
     * <p>ecode をセットします。
     * @param ecode ecode
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#ecode__
     */
    public void setEcode(int ecode) {
        ecode__ = ecode;
    }
    /**
     * <p>otpSendDate を取得します。
     * @return otpSendDate
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendDate__
     */
    public UDate getOtpSendDate() {
        return otpSendDate__;
    }
    /**
     *
     * <br>[機  能] otpSendDateの文字列変換を返す
     * <br>[解  説] yyyy/mm/dd HH:MM:SS
     * <br>[備  考]
     * @return otpSendDateの文字列変換
     */
    public String getOtpSendDateStr() {
        return UDateUtil.getSlashYYMD(otpSendDate__)
                + " " + UDateUtil.getSeparateHM(otpSendDate__);
    }

    /**
     * <p>otpSendDate をセットします。
     * @param otpSendDate otpSendDate
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendDate__
     */
    public void setOtpSendDate(UDate otpSendDate) {
        otpSendDate__ = otpSendDate;
    }
    /**
     * <p>otpSendMailFrom を取得します。
     * @return otpSendMailFrom
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendMailFrom__
     */
    public String getOtpSendMailFrom() {
        return otpSendMailFrom__;
    }
    /**
     * <p>otpSendMailFrom をセットします。
     * @param otpSendMailFrom otpSendMailFrom
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendMailFrom__
     */
    public void setOtpSendMailFrom(String otpSendMailFrom) {
        otpSendMailFrom__ = otpSendMailFrom;
    }
    /**
     * <p>otpSendMailSubject を取得します。
     * @return otpSendMailSubject
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendMailSubject__
     */
    public String getOtpSendMailSubject() {
        return otpSendMailSubject__;
    }
    /**
     * <p>otpSendMailSubject をセットします。
     * @param otpSendMailSubject otpSendMailSubject
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendMailSubject__
     */
    public void setOtpSendMailSubject(String otpSendMailSubject) {
        otpSendMailSubject__ = otpSendMailSubject;
    }
    /**
     * <p>otpSendMailTo を取得します。
     * @return otpSendMailTo
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendMailTo__
     */
    public String getOtpSendMailTo() {
        return otpSendMailTo__;
    }
    /**
     * <p>otpSendMailTo をセットします。
     * @param otpSendMailTo otpSendMailTo
     * @see jp.groupsession.v2.cmn.login.otp.OtpSendResult#otpSendMailTo__
     */
    public void setOtpSendMailTo(String otpSendMailTo) {
        otpSendMailTo__ = otpSendMailTo;
    }


}
