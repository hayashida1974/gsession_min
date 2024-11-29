package jp.groupsession.v2.usr.usr060;
/**
 *
 * <br>[機  能] ワンタイムパスワード通知先メールアドレス設定 インタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IUsr060Param {

    /**
     * <p>usr060Token を取得します。
     * @return usr060Token
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060Token__
     */
    String getUsr060Token();

    /**
     * <p>usr060Token をセットします。
     * @param usr060Token usr060Token
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060Token__
     */
    void setUsr060Token(String usr060Token);

    /**
     * <p>usr060SendToAddress を取得します。
     * @return usr060SendToAddress
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060SendToAddress__
     */
    String getUsr060SendToAddress();

    /**
     * <p>usr060SendToAddress をセットします。
     * @param usr060SendToAddress usr060SendToAddress
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060SendToAddress__
     */
    void setUsr060SendToAddress(String usr060SendToAddress);

    /**
     * <p>usr060KakuninPass を取得します。
     * @return usr060KakuninPass
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060KakuninPass__
     */
    String getUsr060KakuninPass();

    /**
     * <p>usr060KakuninPass をセットします。
     * @param usr060KakuninPass usr060KakuninPass
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060KakuninPass__
     */
    void setUsr060KakuninPass(String usr060KakuninPass);

    /**
     * <p>usr060AddMode を取得します。
     * @return usr060AddMode
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060AddMode__
     */
    boolean isUsr060AddMode();

    /**
     * <p>usr060AddMode をセットします。
     * @param usr060AddMode usr060AddMode
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060AddMode__
     */
    void setUsr060AddMode(boolean usr060AddMode);

    /**
     * <p>usr060otpSended を取得します。
     * @return usr060otpSended
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060otpSended__
     */
    boolean isUsr060otpSended();

    /**
     * <p>usr060otpSended をセットします。
     * @param usr060otpSended usr060otpSended
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060otpSended__
     */
    void setUsr060otpSended(boolean usr060otpSended);

    /**
     * <p>usr060SendOtp を取得します。
     * @return usr060SendOtp
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060SendOtp__
     */
    String getUsr060SendOtp();

    /**
     * <p>usr060SendOtp をセットします。
     * @param usr060SendOtp usr060SendOtp
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060SendOtp__
     */
    void setUsr060SendOtp(String usr060SendOtp);

    /**
     * <p>usr060Reenter を取得します。
     * @return usr060Reenter
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060Reenter__
     */
    String getUsr060Reenter();

    /**
     * <p>usr060Reenter をセットします。
     * @param usr060Reenter usr060Reenter
     * @see jp.groupsession.v2.usr.usr060.Usr060Form#usr060Reenter__
     */
    void setUsr060Reenter(String usr060Reenter);

}