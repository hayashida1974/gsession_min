package jp.groupsession.v2.cmn.restapi.users.authentications;

import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] 認証情報取得API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class CmnAuthenticationsOtpParamModel extends CmnAuthenticationsParamModel {
    /** ワンタイムパスワードトークン*/
    @NotBlank
    @MaxLength(256)
    private String onetimePasswordTokenText__;

    /**
     * <p>onetimePasswordTokenText を取得します。
     * @return onetimePasswordTokenText
     * @see CmnAuthenticationsOtpParamModel#onetimePasswordTokenText__
     */
    public String getOnetimePasswordTokenText() {
        return onetimePasswordTokenText__;
    }

    /**
     * <p>onetimePasswordTokenText をセットします。
     * @param onetimePasswordTokenText onetimePasswordTokenText
     * @see CmnAuthenticationsOtpParamModel#onetimePasswordTokenText__
     */
    public void setOnetimePasswordTokenText(String onetimePasswordTokenText) {
        onetimePasswordTokenText__ = onetimePasswordTokenText;
    }

}
