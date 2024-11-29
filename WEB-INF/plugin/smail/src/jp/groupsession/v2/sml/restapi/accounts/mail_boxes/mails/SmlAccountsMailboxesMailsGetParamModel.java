package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.QueryParamModelBase;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] メール情報API パラメータモデル
 * <br>[解  説] GET
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsMailboxesMailsGetParamModel extends QueryParamModelBase {
    /** アカウントSID */
    private int accountSid__ = -1;
    /** メールボックス名 */
    private String boxName__;
    /** 登録日時(開始) */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate fromDateTime__;
    /** 登録日時(終了) */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate toDateTime__;

    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.templates.
     *       SmlAccountsTemplatesParamModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }

    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.templates.
     *       SmlAccountsTemplatesParamModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>boxName を取得します。
     * @return boxName
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.
     *       SmlAccountsMailboxesMailsGetParamModel#boxName__
     */
    public String getBoxName() {
        return boxName__;
    }

    /**
     * <p>boxName をセットします。
     * @param boxName boxName
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.
     *       SmlAccountsMailboxesMailsGetParamModel#boxName__
     */
    public void setBoxName(String boxName) {
        boxName__ = boxName;
    }
    /**
     * <p>fromDateTime を取得します。
     * @return fromDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.
     *       SmlAccountsMailboxesMailsGetParamModel#fromDateTime__
     */
    public UDate getFromDateTime() {
        return fromDateTime__;
    }
    /**
     * <p>fromDateTime をセットします。
     * @param fromDateTime fromDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.
     *       SmlAccountsMailboxesMailsGetParamModel#fromDateTime__
     */
    public void setFromDateTime(UDate fromDateTime) {
        fromDateTime__ = fromDateTime;
    }

    /**
     * <p>toDateTime を取得します。
     * @return toDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.
     *       SmlAccountsMailboxesMailsGetParamModel#toDateTime__
     */
    public UDate getToDateTime() {
        return toDateTime__;
    }

    /**
     * <p>toDateTime をセットします。
     * @param toDateTime toDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.
     *       SmlAccountsMailboxesMailsGetParamModel#toDateTime__
     */
    public void setToDateTime(UDate toDateTime) {
        toDateTime__ = toDateTime;
    }
    @Validator
    public void validator(RestApiContext ctx) {
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        // アカウントチェック
        try {
            validateCheckSmlAccount(ctx.getCon(), gsMsg,
                    ctx.getRequestUserModel().getUsrsid(), getAccountSid(),
                    ctx.getRequestModel());

            //inbox:受信, sent:送信,draft:草稿,trash:ゴミ箱,｛数値｝
            String boxId = Optional.ofNullable(getBoxName()).orElse("");
            switch (boxId) {
            case "inbox":
            case "sent":
            case "draft":
            case "trash":
                break;
            default:
                if (!ValidateUtil.isNumberHaifun(boxId)) {
                    throw new RestApiValidateException(
                        EnumError.PARAM_FORMAT,
                        "error.input.comp.text.either.withnum",
                        "boxName",
                        Stream.of("inbox", "sent", "draft", "trash")
                            .collect(Collectors.joining(","))
                        );
                }
                // ラベルチェック
                SmlLabelDao labelDao = new SmlLabelDao(ctx.getCon());
                SmlLabelModel labelMdl = labelDao.select(
                        getAccountSid(), NullDefault.getInt(boxId, -1));
                if (labelMdl == null) {
                    // ラベルが存在しない場合
                    throw new RestApiPermissionException(
                            ReasonCode.EnumError.IMPERMISSIBLE,
                            "search.data.notfound",
                            new GsMessage(ctx.getRequestModel()).getMessage("cmn.label")
                            );
                }
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }
    }


    /**
     * <br>[機  能] アカウントチェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  userSid ユーザSID
     * @param  sacSid  アカウントSID
     * @param  reqMdl  リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void validateCheckSmlAccount(Connection con, GsMessage gsMsg,
                                                int userSid, int sacSid,
                                                RequestModel reqMdl)
        throws SQLException {

        SmailDao smlDao = new SmailDao(con);

        // 選択されているアカウントが使用可能かを判定する
        if (userSid <= 0 || sacSid <= 0 || !smlDao.canUseCheckAccount(userSid, sacSid)) {
            // アカウントがない場合
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.account")
                    );
        }
    }

    /**
     * <br>[機  能] メール可否チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  sacSid  アカウントSID
     * @param  smlSid  メールSID
     * @param  reqMdl  リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void validateReadSmail(Connection con,
                                  GsMessage gsMsg, int sacSid, int smlSid,
                                  RequestModel reqMdl)
        throws SQLException {

        SmailDao smlDao = new SmailDao(con);
        if (smlSid < 0 || !smlDao.isViewSmail(sacSid, smlSid)) {
            // メール閲覧権限が無い為、アクセスエラー
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.shortmail")
                    );
        }
    }
}
