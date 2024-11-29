package jp.groupsession.v2.cmn.cmn004;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn001.Cmn001Form;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] ワンタイムパスワード入力画面 フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn004Form extends Cmn001Form implements ICmn004Param  {
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

    /**
     * <br>[機  能] ログイン時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @return エラー
     */
    public ActionErrors validateLogin(ActionMapping map, RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // パスワード
        GSValidateCommon.validateNumberInt(errors,
                cmn004OtPass__,
                gsMsg.getMessage("cmn.onetimepassword"),
                GSConstCommon.OTP_LENGTH);
        return errors;
    }

}
