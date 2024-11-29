package jp.groupsession.v2.adr.restapi.entities;

import java.sql.SQLException;

import jp.groupsession.v2.adr.restapi.biz.AdrRestAddressBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;

/**
 * <br>[機  能] アドレス帳 アドレス情報取得API用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class AdrEntitiesGetParamModel {

    /** アドレス情報SID */
    @NotBlank
    private Integer addressSid__ = 0;

    public Integer getAddressSid() {
        return addressSid__;
    }

    public void setAddressSid(Integer addressSid) {
        addressSid__ = addressSid;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        AdrRestAddressBiz biz = new AdrRestAddressBiz();
        biz.validateAddress(ctx, addressSid__);
    }
}
