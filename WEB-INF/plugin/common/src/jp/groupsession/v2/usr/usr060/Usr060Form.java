package jp.groupsession.v2.usr.usr060;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn001.Cmn001Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;
/**
 *
 * <br>[機  能] ワンタイムパスワード通知アドレス変更画面 フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr060Form extends Cmn001Form implements IUsr060Param {
    /** ワンタイムパスワード通知アドレス変更画面 トークン*/
    private String usr060Token__;
    /** ワンタイムパスワード通知アドレス*/
    private String usr060SendToAddress__;
    /** 確認用ワンタイムパスワード入力*/
    private String usr060KakuninPass__;

    /** 確認用ワンタイムパスワード送信ボタン*/
    private String usr060SendOtp__;
    /** メールアドレス再入力ボタン*/
    private String usr060Reenter__;

    //表示用
    /** 新規作成かどうか*/
    private boolean usr060AddMode__ = false;
    /** ワンタイムパスワード送信済み*/
    private boolean usr060otpSended__ = false;

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#getUsr060Token()
     */
    @Override
    public String getUsr060Token() {
        return usr060Token__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060Token(java.lang.String)
     */
    @Override
    public void setUsr060Token(String usr060Token) {
        usr060Token__ = usr060Token;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#getUsr060SendToAddress()
     */
    @Override
    public String getUsr060SendToAddress() {
        return usr060SendToAddress__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060SendToAddress(java.lang.String)
     */
    @Override
    public void setUsr060SendToAddress(String usr060SendToAddress) {
        usr060SendToAddress__ = usr060SendToAddress;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#getUsr060KakuninPass()
     */
    @Override
    public String getUsr060KakuninPass() {
        return usr060KakuninPass__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060KakuninPass(java.lang.String)
     */
    @Override
    public void setUsr060KakuninPass(String usr060KakuninPass) {
        usr060KakuninPass__ = usr060KakuninPass;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#isUsr060AddMode()
     */
    @Override
    public boolean isUsr060AddMode() {
        return usr060AddMode__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060AddMode(boolean)
     */
    @Override
    public void setUsr060AddMode(boolean usr060AddMode) {
        usr060AddMode__ = usr060AddMode;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#isUsr060otpSended()
     */
    @Override
    public boolean isUsr060otpSended() {
        return usr060otpSended__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060otpSended(boolean)
     */
    @Override
    public void setUsr060otpSended(boolean usr060otpSended) {
        usr060otpSended__ = usr060otpSended;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#getUsr060SendOtp()
     */
    @Override
    public String getUsr060SendOtp() {
        return usr060SendOtp__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060SendOtp(java.lang.String)
     */
    @Override
    public void setUsr060SendOtp(String usr060SendOtp) {
        usr060SendOtp__ = usr060SendOtp;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#getUsr060Reenter()
     */
    @Override
    public String getUsr060Reenter() {
        return usr060Reenter__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.usr.usr060.IUsr060Param#setUsr060Reenter(java.lang.String)
     */
    @Override
    public void setUsr060Reenter(String usr060Reenter) {
        usr060Reenter__ = usr060Reenter;
    }
    /**
     *
     * <br>[機  能] パスワード入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validatePass(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // パスワード
        GSValidateCommon.validateNumberInt(errors,
                usr060KakuninPass__,
                gsMsg.getMessage("cmn.onetimepassword"),
                GSConstCommon.OTP_LENGTH);
        return errors;

    }
    /**
     *
     * <br>[機  能] アドレス欄入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateAddress(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GSValidateUser gsValidateUser = new GSValidateUser(reqMdl);
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        //ワンタイムパスワード通知先アドレス
        gsValidateUser.validateMail(errors, usr060SendToAddress__,
                gsMsg.getMessage("user.157"), "usr060SendToAddress.", true);

        return errors;
    }
}
