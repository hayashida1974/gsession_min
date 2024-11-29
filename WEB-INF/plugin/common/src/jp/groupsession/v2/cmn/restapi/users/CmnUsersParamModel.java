package jp.groupsession.v2.cmn.restapi.users;

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
public class CmnUsersParamModel  {
    /** ユーザID */
    @MaxLength(256)
    @NotBlank
    private String userId__;

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
