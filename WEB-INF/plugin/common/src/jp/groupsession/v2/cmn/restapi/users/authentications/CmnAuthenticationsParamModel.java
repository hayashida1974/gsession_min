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
public class CmnAuthenticationsParamModel {
    /** ユーザID */
    @NotBlank
    @MaxLength(256)
    private String userId__;
    /** パスワード*/
    @NotBlank
    @MaxLength(256)
    private String password__;

    /**
     * <p>password を取得します。
     * @return password
     * @see CmnAuthenticationsParamModel#password__
     */
    public String getPassword() {
        return password__;
    }

    /**
     * <p>password をセットします。
     * @param password password
     * @see CmnAuthenticationsParamModel#password__
     */
    public void setPassword(String password) {
        password__ = password;
    }

    /**
     * <p>userId を取得します。
     * @return userId
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersParamModel#userId__
     */
    public String getUserId() {
        return userId__;
    }

    /**
     * <p>userId をセットします。
     * @param userId userId
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersParamModel#userId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }

}
