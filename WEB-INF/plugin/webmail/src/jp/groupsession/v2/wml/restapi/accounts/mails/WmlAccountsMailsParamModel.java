package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextArea;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;

/**
 * <br>[機  能] WEBメール 新規送信, 新規草稿作成用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsParamModel {

    /** アカウントID */
    private String accountId__;

    /** 送信先 To */
    @TextField
    @MaxLength(768)
    private String[] toArray__ = new String[] {};
    /** 送信先 Cc */
    @TextField
    @MaxLength(768)
    private String[] ccArray__ = new String[] {};
    /** 送信先 Bcc */
    @TextField
    @MaxLength(768)
    private String[] bccArray__ = new String[] {};
    /** 件名 */
    @MaxLength(GSConstWebmail.MAXLEN_MAIL_SUBJECT)
    @TextField
    private String subjectText__ = "";
    /** 内容 */
    @TextArea
    private String bodyText__ = "";
    /** 予約送信タイプ */
    @Selectable({"0", "1", "2"})
    private int sendPlanType__ = GSConstWebmail.TIMESENT_NORMAL;
    /** 予約送信日時 */
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    private UDate sendPlanDateTime__;
    /** 添付ファイル 自動圧縮フラグ */
    @Selectable({"0", "1"})
    private int compressFileFlg__ = GSConstWebmail.RESTAPI_COMPRESS_NO;
    /** メッセージ作成モード */
    @Selectable({"0", "1", "2", "3", "4"})
    private int procType__ = GSConstWebmail.SEND_TYPE_NORMAL;
    /** 参照メールSID */
    private long refarenceSid__ = -1;
    /** 参照メールのバイナリSID */
    private long[] refarenceBinSidArray__ = new long[] {};
    /** テンプレートSID */
    private int templateSid__ = -1;
    /** テンプレートのバイナリSID */
    private long[] templateBinSidArray__ = new long[] {};

    /**
     * <p>accountId を取得します。
     * @return accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }

    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }

    /**
     * <p>toArray を取得します。
     * @return toArray toArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#toArray__
     */
    public String[] getToArray() {
        return toArray__;
    }

    /**
     * <p>toArray をセットします。
     * @param toArray toArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#toArray__
     */
    public void setToArray(String[] toArray) {
        toArray__ = toArray;
    }


    /**
     * <p>ccArray を取得します。
     * @return ccArray ccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#ccArray__
     */
    public String[] getCcArray() {
        return ccArray__;
    }

    /**
     * <p>ccArray をセットします。
     * @param ccArray ccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#ccArray__
     */
    public void setCcArray(String[] ccArray) {
        ccArray__ = ccArray;
    }

    /**
     * <p>bccArray を取得します。
     * @return bccArray bccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#bccArray__
     */
    public String[] getBccArray() {
        return bccArray__;
    }

    /**
     * <p>bccArray をセットします。
     * @param bccArray bccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#bccArray__
     */
    public void setBccArray(String[] bccArray) {
        bccArray__ = bccArray;
    }

    /**
     * <p>subjectText を取得します。
     * @return subjectText subjectText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#subjectText__
     */
    public String getSubjectText() {
        return subjectText__;
    }

    /**
     * <p>subjectText をセットします。
     * @param subjectText subjectText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#subjectText__
     */
    public void setSubjectText(String subjectText) {
        this.subjectText__ = subjectText;
    }

    /**
     * <p>bodyText を取得します。
     * @return bodyText bodyText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#bodyText__
     */
    public String getBodyText() {
        return bodyText__;
    }

    /**
     * <p>bodyText をセットします。
     * @param bodyText bodyText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#bodyText__
     */
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }

    /**
     * <p>bodyText を取得します。
     * @return bodyText bodyText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#bodyText__
     */
    public int getSendPlanType() {
        return sendPlanType__;
    }

    /**
     * <p>bodyText をセットします。
     * @param bodyText bodyText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#bodyText__
     */
    public void setSendPlanType(int sendPlanType) {
        this.sendPlanType__ = sendPlanType;
    }

    /**
     * <p>sendPlanDateTime を取得します。
     * @return sendPlanDateTime sendPlanDateTime
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#sendPlanDateTime__
     */
    public UDate getSendPlanDateTime() {
        return sendPlanDateTime__;
    }

    /**
     * <p>sendPlanDateTime をセットします。
     * @param sendPlanDateTime sendPlanDateTime
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#sendPlanDateTime__
     */
    public void setSendPlanDateTime(UDate sendPlanDateTime) {
        sendPlanDateTime__ = sendPlanDateTime;
    }

    /**
     * <p>compressFileFlg を取得します。
     * @return compressFileFlg compressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#compressFileFlg__
     */
    public int getCompressFileFlg() {
        return compressFileFlg__;
    }

    /**
     * <p>compressFileFlg をセットします。
     * @param compressFileFlg compressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#compressFileFlg__
     */
    public void setCompressFileFlg(int compressFileFlg) {
        compressFileFlg__ = compressFileFlg;
    }

    /**
     * <p>procType を取得します。
     * @return procType procType
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#procType__
     */
    public int getProcType() {
        return procType__;
    }

    /**
     * <p>procType をセットします。
     * @param procType procType
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#procType__
     */
    public void setProcType(int procType) {
        procType__ = procType;
    }

    /**
     * <p>refarenceSid を取得します。
     * @return refarenceSid refarenceSid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#refarenceSid__
     */
    public long getRefarenceSid() {
        return refarenceSid__;
    }

    /**
     * <p>refarenceSid をセットします。
     * @param refarenceSid refarenceSid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#refarenceSid__
     */
    public void setRefarenceSid(long refarenceSid) {
        refarenceSid__ = refarenceSid;
    }

    /**
     * <p>refarenceBinSidArray を取得します。
     * @return refarenceBinSidArray refarenceBinSidArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#refarenceBinSidArray__
     */
    public long[] getRefarenceBinSidArray() {
        return refarenceBinSidArray__;
    }

    /**
     * <p>refarenceBinSidArray をセットします。
     * @param refarenceBinSidArray refarenceBinSidArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#refarenceBinSidArray__
     */
    public void setRefarenceBinSidArray(long[] refarenceBinSidArray) {
        this.refarenceBinSidArray__ = refarenceBinSidArray;
    }

    /**
     * <p>templateSid を取得します。
     * @return templateSid templateSid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#templateSid__
     */
    public int getTemplateSid() {
        return templateSid__;
    }

    /**
     * <p>templateSid をセットします。
     * @param templateSid templateSid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#templateSid__
     */
    public void setTemplateSid(int templateSid) {
        templateSid__ = templateSid;
    }

    /**
     * <p>templateBinSidArray を取得します。
     * @return templateBinSidArray templateBinSidArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#templateBinSidArray__
     */
    public long[] getTemplateBinSidArray() {
        return templateBinSidArray__;
    }

    /**
     * <p>templateBinSidArray をセットします。
     * @param templateBinSidArray templateBinSidArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.send.WmlAccountsMailsParamModel#templateBinSidArray__
     */
    public void setTemplateBinSidArray(long[] templateBinSidArray) {
        this.templateBinSidArray__ = templateBinSidArray;
    }



    /**
     * <br>[機  能] アカウントに対しての入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        //アカウント利用可能チェック
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        wraBiz.validateAccount(ctx, accountId__);
    }
}
