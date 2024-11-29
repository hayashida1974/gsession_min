package jp.groupsession.v2.wml.restapi.accounts.mails.query;

import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメール メール一覧 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsQueryPostParamModel {

    /** WEBメールアカウントID */
    @NotBlank
    @TextField
    private String accountId__ = null;

    /** 取得件数 */
    @MinValue(1)
    @MaxValue(100)
    private int limit__ = 50;
    /** 取得開始位置 */
    @MinValue(0)
    private int offset__ = 0;

    /** ディレクトリタイプ */
    @Selectable({"all", "inbox", "sent", "future", "draft", "trash", "keep", "label"})
    private String directoryType__ = null;
    /** ラベルSID */
    private int labelSid__ = 0;
    /** 日時検索開始 */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate fromDateTime__;
    /** 日時検索終了 */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate toDateTime__;

    /** キーワード */
    @TextField
    @MaxLength(100)
    private String keywordText__ = null;
    /** 送信者検索文字列 */
    @TextField
    @MaxLength(256)
    private String fromAddressText__ = null;
    /** 送信先検索文字列 */
    @TextField
    @MaxLength(256)
    private String sendAddressText__ = null;
    /** 送信先検索（宛先）フラグ */
    @Selectable({"0", "1"})
    private int sendToFlg__ = 0;
    /** 送信先検索（CC）フラグ */
    @Selectable({"0", "1"})
    private int sendCcFlg__ = 0;
    /** 送信先検索（BCC）フラグ */
    @Selectable({"0", "1"})
    private int sendBccFlg__ = 0;
    /** 添付ファイル検索 フラグ */
    @Selectable({"0", "1"})
    private int useTmpFileFlg__ = 0;
    /** 未読・既読状態検索タイプ */
    @Selectable({"0", "1", "2"})
    private int openStatusType__ = 0;	

    public String getAccountId() {
        return accountId__;
    }

    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }

    public int getLimit() {
        return limit__;
    }

    public void setLimit(int limit) {
        limit__ = limit;
    }

    public int getOffset() {
        return offset__;
    }

    public void setOffset(int offset) {
        offset__ = offset;
    }

    public String getDirectoryType() {
        return directoryType__;
    }

    public void setDirectoryType(String directoryType) {
        directoryType__ = directoryType;
    }

    public int getLabelSid() {
        return labelSid__;
    }

    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }

    public UDate getFromDateTime() {
        return fromDateTime__;
    }

    public void setFromDateTime(UDate fromDateTime) {
        fromDateTime__ = fromDateTime;
    }

    public UDate getToDateTime() {
        return toDateTime__;
    }

    public void setToDateTime(UDate toDateTime) {
        toDateTime__ = toDateTime;
    }

    public String getKeywordText() {
        return keywordText__;
    }

    public void setKeywordText(String keywordText) {
        keywordText__ = keywordText;
    }
    public String getFromAddressText() {
        return fromAddressText__;
    }

    public void setFromAddressText(String fromAddressText) {
        fromAddressText__ = fromAddressText;
    }

    public String getSendAddressText() {
        return sendAddressText__;
    }

    public void setSendAddressText(String sendAddressText) {
        sendAddressText__ = sendAddressText;
    }

    public int getSendToFlg() {
        return sendToFlg__;
    }

    public void setSendToFlg(int sendToFlg) {
        sendToFlg__ = sendToFlg;
    }

    public int getSendCcFlg() {
        return sendCcFlg__;
    }

    public void setSendCcFlg(int sendCcFlg) {
        sendCcFlg__ = sendCcFlg;
    }

    public int getSendBccFlg() {
        return sendBccFlg__;
    }

    public void setSendBccFlg(int sendBccFlg) {
        sendBccFlg__ = sendBccFlg;
    }

    public int getUseTmpFileFlg() {
        return useTmpFileFlg__;
    }

    public void setUseTmpFileFlg(int useTmpFileFlg) {
        useTmpFileFlg__ = useTmpFileFlg;
    }

    public int getOpenStatusType() {
        return openStatusType__;
    }

    public void setOpenStatusType(int openStatusType) {
        openStatusType__ = openStatusType;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        //アカウント利用可能チェック
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        wraBiz.validateAccount(ctx, accountId__);
        if (NullDefault.getString(directoryType__, "").equals("label")) {
            if (labelSid__ <= 0) {
                //ラベルSID未入力チェック
                throw new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.input.required.text",
                        "lebelSid"
                        )
                    .setParamName("labelSid");
            } else {
                //ラベル存在チェック
                int wacSid = wraBiz.getWmlAccountSid(ctx.getCon(), accountId__);
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

        //日時検索大小チェック
        if (fromDateTime__ != null && toDateTime__ != null) {
            if (fromDateTime__.compare(fromDateTime__, toDateTime__) == UDate.SMALL) {
                throw new RestApiValidateException(
                        EnumError.PARAM_COLLABORATION,
                        "error.input.fromto.input",
                        "toDateTime", "fromDateTime"
                        )
                    .setParamName("toDateTime");
            }

        }

        //送信先検索文字列 未入力チェック
        if ((sendToFlg__ == 1 || sendCcFlg__ == 1 || sendBccFlg__ == 1)
        && StringUtil.isNullZeroString(sendAddressText__)) {
            throw new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.input.required.text",
                        "sendAddressText"
                        )
                    .setParamName("sendAddressText");
        }
    }

}
