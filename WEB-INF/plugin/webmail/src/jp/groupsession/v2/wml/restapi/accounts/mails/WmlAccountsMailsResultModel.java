package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;
import jp.groupsession.v2.wml.restapi.model.LabelInfo;
import jp.groupsession.v2.wml.restapi.model.TmpFileInfo;

/**
 * <br>[機  能] WEBメール メール送信，作成，取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ResponceModel(targetField = {
    "sid",
    "directoryType",
    "directoryName",
    "fromText",
    "toArray",
    "ccArray",
    "bccArray",
    "subjectText",
    "bodyText",
    "tmpFileArray",
    "labelArray",
    "dateTime",
    "sendPlanType",
    "sendPlanDateTime",
    "openFlg",
    "replyFlg",
    "forwardFlg",
    "compressFileFlg"
})
public class WmlAccountsMailsResultModel {

    /** メールSID */
    private long sid__ = -1;
    /** ディレクトリタイプ */
    private String directoryType__ = "";
    /** ディレクトリ名 */
    private String directoryName__ = "";
    /** 送信元アドレス */
    private String fromText__ = "";
    /** TOアドレス */
    @ChildElementName("toInfo")
    private List<String> toArray__ = null;
    /** Ccアドレス */
    @ChildElementName("ccInfo")
    private List<String> ccArray__ = null;
    /** Bccアドレス */
    @ChildElementName("bccInfo")
    private List<String> bccArray__ = null;
    /** 件名 */
    private String subjectText__ = "";
    /** 本文 */
    private String bodyText__ = "";
    /** 添付ファイル配列 */
    @ChildElementName("tmpFileInfo")
    private List<TmpFileInfo> tmpFileArray__ = null;
    /** ラベル配列 */
    @ChildElementName("labelInfo")
    private List<LabelInfo> labelArray__ = null;
    /** メール日時 */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate dateTime__ = null;
    /** 予約送信タイプ */
    private int sendPlanType__ = GSConstWebmail.TIMESENT_NORMAL;
    /** 予約送信日時 */
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    private UDate sendPlanDateTime__ = null;
    /** 開封済みフラグ */
    private int openFlg__ = GSConstWebmail.READEDFLG_NOREAD;
    /** 返信済みフラグ */
    private int replyFlg__ = GSConstWebmail.REPLYFLG_NORMAL;
    /** 転送済みフラグ */
    private int forwardFlg__ = GSConstWebmail.FORWARDFLG_NORMAL;
    /** 自動圧縮フラグ */
    private int compressFileFlg__ = GSConstWebmail.RESTAPI_COMPRESS_NO;

    /**
     * <p>sid を取得します。
     * @return sid sid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#sid__
     */
    public long getSid() {
        return sid__;
    }
    /**
     * <p>sid を設定します。
     * @param sid sid
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#sid__
     */
    public void setSid(long sid) {
        sid__ = sid;
    }

    /**
     * <p>directoryType を取得します。
     * @return directoryType directoryType
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#directoryType__
     */
    public String getDirectoryType() {
        return directoryType__;
    }
    /**
     * <p>directoryType を設定します。
     * @param directoryType directoryType
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#directoryType__
     */
    public void setDirectoryType(String directoryType) {
        directoryType__ = directoryType;
    }

    /**
     * <p>directoryName を取得します。
     * @return directoryName directoryName
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#directoryName__
     */
    public String getDirectoryName() {
        return directoryName__;
    }
    /**
     * <p>directoryName を設定します。
     * @param directoryName directoryName
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#directoryName__
     */
    public void setDirectoryName(String directoryName) {
        directoryName__ = directoryName;
    }

    /**
     * <p>fromText を取得します。
     * @return fromText fromText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#fromText__
     */
    public String getFromText() {
        return fromText__;
    }
    /**
     * <p>fromText を設定します。
     * @param fromText fromText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#fromText__
     */
    public void setFromText(String fromText) {
        fromText__ = fromText;
    }

    /**
     * <p>toArray を取得します。
     * @return toArray toArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#toArray__
     */
    public List<String> getToArray() {
        return toArray__;
    }
    /**
     * <p>toArray を設定します。
     * @param toArray toArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#toArray__
     */
    public void setToArray(List<String> toArray) {
        toArray__ = toArray;
    }

    /**
     * <p>ccArray を取得します。
     * @return ccArray ccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#ccArray__
     */
    public List<String> getCcArray() {
        return ccArray__;
    }
    /**
     * <p>ccArray を設定します。
     * @param ccArray ccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#ccArray__
     */
    public void setCcArray(List<String> ccArray) {
        ccArray__ = ccArray;
    }
    
    /**
     * <p>bccArray を取得します。
     * @return bccArray bccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#bccArray__
     */
    public List<String> getBccArray() {
        return bccArray__;
    }
    /**
     * <p>bccArray を設定します。
     * @param bccArray bccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#bccArray__
     */
    public void setBccArray(List<String> bccArray) {
        bccArray__ = bccArray;
    }

    /**
     * <p>subjectText を取得します。
     * @return subjectText subjectText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#subjectText__
     */
    public String getSubjectText() {
        return subjectText__;
    }
    /**
     * <p>subjectText を設定します。
     * @param subjectText subjectText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#subjectText__
     */
    public void setSubjectText(String subjectText) {
        subjectText__ = subjectText;
    }

    /**
     * <p>bodyText を取得します。
     * @return bodyText bodyText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#bodyText__
     */
    public String getBodyText() {
        return bodyText__;
    }
    /**
     * <p>bodyText を設定します。
     * @param bodyText bodyText
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#bodyText__
     */
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }

    /**
     * <p>tmpFileArray を取得します。
     * @return tmpFileArray tmpFileArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#tmpFileArray__
     */
    public List<TmpFileInfo> getTmpFileArray() {
        return tmpFileArray__;
    }
    /**
     * <p>tmpFileArray を設定します。
     * @param tmpFileArray tmpFileArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#tmpFileArray__
     */
    public void setTmpFileArray(List<TmpFileInfo> tmpFileArray) {
        tmpFileArray__ = tmpFileArray;
    }

    /**
     * <p>labelArray を取得します。
     * @return labelArray labelArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#labelArray__
     */
    public List<LabelInfo> getLabelArray() {
        return labelArray__;
    }
    /**
     * <p>labelArray を設定します。
     * @param labelArray labelArray
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#labelArray__
     */
    public void setLabelArray(List<LabelInfo> labelArray) {
        labelArray__ = labelArray;
    }

    /**
     * <p>dateTime を取得します。
     * @return dateTime dateTime
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#dateTime__
     */
    public UDate getDateTime() {
        return dateTime__;
    }
    /**
     * <p>dateTime を設定します。
     * @param dateTime dateTime
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#dateTime__
     */
    public void setDateTime(UDate dateTime) {
        dateTime__ = dateTime;
    }

    /**
     * <p>sendPlanType を取得します。
     * @return sendPlanType sendPlanType
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#sendPlanType__
     */
    public int getSendPlanType() {
        return sendPlanType__;
    }
    /**
     * <p>sendPlanType を設定します。
     * @param sendPlanType sendPlanType
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#sendPlanType__
     */
    public void setSendPlanType(int sendPlanType) {
        sendPlanType__ = sendPlanType;
    }

    /**
     * <p>sendPlanDateTime を取得します。
     * @return sendPlanDateTime sendPlanDateTime
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#sendPlanDateTime__
     */
    public UDate getSendPlanDateTime() {
        return sendPlanDateTime__;
    }
    /**
     * <p>sendPlanDateTime を設定します。
     * @param sendPlanDateTime sendPlanDateTime
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#sendPlanDateTime__
     */
    public void setSendPlanDateTime(UDate sendPlanDateTime) {
        sendPlanDateTime__ = sendPlanDateTime;
    }

    /**
     * <p>openFlg を取得します。
     * @return openFlg openFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#openFlg__
     */
    public int getOpenFlg() {
        return openFlg__;
    }
    /**
     * <p>openFlg を設定します。
     * @param openFlg openFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#openFlg__
     */
    public void setOpenFlg(int openFlg) {
        openFlg__ = openFlg;
    }

    /**
     * <p>replyFlg を取得します。
     * @return replyFlg replyFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#replyFlg__
     */
    public int getReplyFlg() {
        return replyFlg__;
    }
    /**
     * <p>replyFlg を設定します。
     * @param replyFlg replyFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#replyFlg__
     */
    public void setReplyFlg(int replyFlg) {
        replyFlg__ = replyFlg;
    }

    /**
     * <p>forwardFlg を取得します。
     * @return forwardFlg forwardFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#forwardFlg__
     */
    public int getForwardFlg() {
        return forwardFlg__;
    }
    /**
     * <p>forwardFlg を設定します。
     * @param forwardFlg forwardFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#forwardFlg__
     */
    public void setForwardFlg(int forwardFlg) {
        forwardFlg__ = forwardFlg;
    }

    /**
     * <p>compressFileFlg を取得します。
     * @return compressFileFlg compressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#compressFileFlg__
     */
    public int getCompressFileFlg() {
        return compressFileFlg__;
    }
    /**
     * <p>compressFileFlg を設定します。
     * @param compressFileFlg compressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel#compressFileFlg__
     */
    public void setCompressFileFlg(int compressFileFlg) {
        compressFileFlg__ = compressFileFlg;
    }
    
    /**
     * <br>[機  能] Bccアドレスをレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が受信またはゴミ箱の場合は表示しない
     */
    public boolean canDisplayBccArray() {
        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_INBOX)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_TRASH)) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 予約送信タイプをレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が予約送信または草稿の場合のみ表示する
     */
    public boolean canDisplaySendPlanType() {

        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_FUTURE)
            || (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_DRAFT))) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 予約送信日付をレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が予約送信または草稿の場合のみ表示する
     */
    public boolean canDisplaySendPlanDateTime() {

        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_FUTURE)
            || (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_DRAFT))) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 開封済みフラグをレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が受信、ゴミ箱、保管の場合のみ表示する
     */
    public boolean canDisplayOpenFlg() {

        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_INBOX)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_TRASH)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_KEEP)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 返信済みフラグをレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が受信、ゴミ箱、保管の場合のみ表示する
     */
    public boolean canDisplayReplyFlg() {

        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_INBOX)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_TRASH)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_KEEP)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 転送済みフラグをレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が受信、ゴミ箱、保管の場合のみ表示する
     */
    public boolean canDisplayForwardFlg() {

        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_INBOX)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_TRASH)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_KEEP)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 自動圧縮フラグをレスポンスに出力するかを判定する
     * <br>[解  説]
     * <br>[備  考] 保存先が予約送信または草稿の場合のみ表示する
     */
    public boolean canDisplayCompressFileFlg() {

        if (Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_FUTURE)
            || Objects.equals(getDirectoryType(), GSConstWebmail.RESTAPI_MAILBOX_DRAFT)) {
            return true;
        }
        return false;
    }
}
