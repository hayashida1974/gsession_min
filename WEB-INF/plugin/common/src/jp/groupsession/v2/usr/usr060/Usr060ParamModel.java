package jp.groupsession.v2.usr.usr060;

import jp.groupsession.v2.cmn.cmn001.Cmn001ParamModel;

/**
*
* <br>[機  能] ワンタイムパスワード通知アドレス変更画面 パラムモデル
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class Usr060ParamModel
extends Cmn001ParamModel implements IUsr060Param {
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
    private boolean usr060AddMode__;
    /** ワンタイムパスワード送信済み*/
    private boolean usr060otpSended__;
    @Override
    public String getUsr060SendOtp() {
        return usr060SendOtp__;
    }
    @Override
    public void setUsr060SendOtp(String usr060SendOtp) {
        usr060SendOtp__ = usr060SendOtp;
    }
    @Override
    public String getUsr060Reenter() {
        return usr060Reenter__;
    }
    @Override
    public void setUsr060Reenter(String usr060Reenter) {
        usr060Reenter__ = usr060Reenter;
    }
    @Override
    public boolean isUsr060AddMode() {
        return usr060AddMode__;
    }
    @Override
    public void setUsr060AddMode(boolean usr060AddMode) {
        usr060AddMode__ = usr060AddMode;
    }
    @Override
    public boolean isUsr060otpSended() {
        return usr060otpSended__;
    }
    @Override
    public void setUsr060otpSended(boolean usr060otpSended) {
        usr060otpSended__ = usr060otpSended;
    }
    @Override
    public String getUsr060Token() {
        return usr060Token__;
    }
    @Override
    public void setUsr060Token(String usr060Token) {
        usr060Token__ = usr060Token;
    }
    @Override
    public String getUsr060SendToAddress() {
        return usr060SendToAddress__;
    }
    @Override
    public void setUsr060SendToAddress(String usr060SendToAddress) {
        usr060SendToAddress__ = usr060SendToAddress;
    }
    @Override
    public String getUsr060KakuninPass() {
        return usr060KakuninPass__;
    }
    @Override
    public void setUsr060KakuninPass(String usr060KakuninPass) {
        usr060KakuninPass__ = usr060KakuninPass;
    }



}
