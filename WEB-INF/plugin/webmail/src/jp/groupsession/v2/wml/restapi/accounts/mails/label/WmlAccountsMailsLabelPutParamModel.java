package jp.groupsession.v2.wml.restapi.accounts.mails.label;

import java.sql.SQLException;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsMultiParamModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメール メールにラベルを設定または解除する 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsLabelPutParamModel extends WmlAccountsMailsMultiParamModel {

    /** 操作タイプ ラベル設定 */
    public static final String PROCTYPE_ADD = "add";
    /** 操作タイプ ラベル解除 */
    public static final String PROCTYPE_REMOVE = "remove";

    /** 操作タイプ */
    @NotBlank
    @Selectable({PROCTYPE_ADD, PROCTYPE_REMOVE})
    private String procType__ = "add";
    /** ラベルSID */
    @NotBlank
    private int labelSid__ = 0;

    public String getProcType() {
        return procType__;
    }

    public void setProcType(String procType) {
        procType__ = procType;
    }

    public int getLabelSid() {
        return labelSid__;
    }

    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        //アカウント・メールチェック
        super.validate(ctx);

        if (labelSid__ <= 0) {
            //ラベルSID未入力チェック
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.label")
                    )
                .setParamName("lebelSid");
        } else {
            //ラベル存在チェック
            WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
            int wacSid = wraBiz.getWmlAccountSid(ctx.getCon(), getAccountId());
            WmlLabelDao labelDao = new WmlLabelDao(ctx.getCon());
            if (labelDao.checkLabel(wacSid, labelSid__) <= 0) {
                throw new RestApiValidateException(
                    WmlEnumReasonCode.PARAM_CANT_ACCESS_LABEL,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.label")
                    )
                .setParamName("labelSid");
            }
        }
    }
}
