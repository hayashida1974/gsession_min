package jp.groupsession.v2.cmn.cmn004;

import jp.groupsession.v2.cmn.cmn001.Cmn001ParamModel;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
/**
 *
 * <br>[機  能] ワンタイムパスワード入力画面 パラメータ宣言 パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn004ParamModel extends Cmn001ParamModel implements ICmn004Param {
    /**ワンタイムパスワード */
    private String cmn004OtPass__;
    /**一次トークン*/
    private String cmn004Token__;
    /**送信結果モデル*/
    private OtpSendResult cmn004OtpSendResult__;

    @Override
    public String getCmn004OtPass() {
        return cmn004OtPass__;
    }
    @Override
    public void setCmn004OtPass(String cmn004OtPass) {
        cmn004OtPass__ = cmn004OtPass;
    }
    @Override
    public String getCmn004Token() {
        return cmn004Token__;
    }
    @Override
    public void setCmn004Token(String cmn004Token) {
        cmn004Token__ = cmn004Token;
    }
    @Override
    public OtpSendResult getCmn004OtpSendResult() {
        return cmn004OtpSendResult__;
    }
    @Override
    public void setCmn004OtpSendResult(OtpSendResult cmn004OtpSendResult) {
        cmn004OtpSendResult__ = cmn004OtpSendResult;
    }

}
