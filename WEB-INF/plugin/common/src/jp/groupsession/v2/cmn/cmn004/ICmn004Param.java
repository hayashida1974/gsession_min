package jp.groupsession.v2.cmn.cmn004;

import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
/**
 *
 * <br>[機  能] ワンタイムパスワード入力画面 インタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ICmn004Param {

    /**
     * <p>cmn004OtPass を取得します。
     * @return cmn004OtPass
     * @see jp.groupsession.v2.cmn.cmn004.Cmn004Form#cmn004OtPass__
     */
    String getCmn004OtPass();

    /**
     * <p>cmn004OtPass をセットします。
     * @param cmn004OtPass cmn004OtPass
     * @see jp.groupsession.v2.cmn.cmn004.Cmn004Form#cmn004OtPass__
     */
    void setCmn004OtPass(String cmn004OtPass);

    /**
     * <p>cmn004Token を取得します。
     * @return cmn004Token
     * @see jp.groupsession.v2.cmn.cmn004.Cmn004Form#cmn004Token__
     */
    String getCmn004Token();

    /**
     * <p>cmn004Token をセットします。
     * @param cmn004Token cmn004Token
     * @see jp.groupsession.v2.cmn.cmn004.Cmn004Form#cmn004Token__
     */
    void setCmn004Token(String cmn004Token);
    /**
     * <p>cmn004OtpSendResult を取得します。
     * @return cmn004OtpSendResult
     * @see jp.groupsession.v2.cmn.cmn004.Cmn004Form#cmn004OtpSendResult__
     */
    OtpSendResult getCmn004OtpSendResult();
    /**
     * <p>cmn004OtpSendResult をセットします。
     * @param cmn004OtpSendResult cmn004OtpSendResult
     * @see jp.groupsession.v2.cmn.cmn004.Cmn004Form#cmn004OtpSendResult__
     */
    void setCmn004OtpSendResult(OtpSendResult cmn004OtpSendResult);


}