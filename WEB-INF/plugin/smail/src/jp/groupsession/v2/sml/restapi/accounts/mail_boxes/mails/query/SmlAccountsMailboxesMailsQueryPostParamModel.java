package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.parameter.QueryParamModelBase;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] メール情報検索API パラメータモデル
 * <br>[解  説] POST
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsMailboxesMailsQueryPostParamModel extends QueryParamModelBase {
    /** アカウントSID */
    private int accountSid__ = -1;
    /** メールボックス名 */
    @Selectable({"inbox", "sent", "draft", "trash"})
    private String boxName__;
    /** 登録日時(開始) */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate fromDateTime__;
    /** 登録日時(終了) */
    @UDateFormat(Format.DATETIME_SLUSH)
    private UDate toDateTime__;
    /** ショートメールSID */
    private int smlSid__ = -1;
    /** メールマーク */
    private int markFlg__ = -1;
    /** キーワード */
    private String keywordText__;
    /** キーワード区分 */
    private int keywordTargetType__ = 3;
    /** 差出人アカウントSID */
    private int senderAccountSid__ = -1;
    /** 宛先/CC/BCC アカウントSID */
    private int[] toAccountSidArray__;
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>boxName を取得します。
     * @return boxName
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#boxName__
     */
    public String getBoxName() {
        return boxName__;
    }
    /**
     * <p>boxName をセットします。
     * @param boxName boxName
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#boxName__
     */
    public void setBoxName(String boxName) {
        boxName__ = boxName;
    }
    /**
     * <p>fromDateTime を取得します。
     * @return fromDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#fromDateTime__
     */
    public UDate getFromDateTime() {
        return fromDateTime__;
    }
    /**
     * <p>fromDateTime をセットします。
     * @param fromDateTime fromDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#fromDateTime__
     */
    public void setFromDateTime(UDate fromDateTime) {
        fromDateTime__ = fromDateTime;
    }
    /**
     * <p>toDateTime を取得します。
     * @return toDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#toDateTime__
     */
    public UDate getToDateTime() {
        return toDateTime__;
    }
    /**
     * <p>toDateTime をセットします。
     * @param toDateTime toDateTime
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#toDateTime__
     */
    public void setToDateTime(UDate toDateTime) {
        toDateTime__ = toDateTime;
    }
    /**
     * <p>smlSid を取得します。
     * @return smlSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#smlSid__
     */
    public int getSmlSid() {
        return smlSid__;
    }
    /**
     * <p>smlSid をセットします。
     * @param smlSid smlSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#smlSid__
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }
    /**
     * <p>markFlg を取得します。
     * @return markFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#markFlg__
     */
    public int getMarkFlg() {
        return markFlg__;
    }
    /**
     * <p>markFlg をセットします。
     * @param markFlg markFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#markFlg__
     */
    public void setMarkFlg(int markFlg) {
        markFlg__ = markFlg;
    }
    /**
     * <p>keywordText を取得します。
     * @return keywordText
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#keywordText__
     */
    public String getKeywordText() {
        return keywordText__;
    }
    /**
     * <p>keywordText をセットします。
     * @param keywordText keywordText
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#keywordText__
     */
    public void setKeywordText(String keywordText) {
        keywordText__ = keywordText;
    }
    /**
     * <p>keywordTargetType を取得します。
     * @return keywordTargetType
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#keywordTargetType__
     */
    public int getKeywordTargetType() {
        return keywordTargetType__;
    }
    /**
     * <p>keywordTargetType をセットします。
     * @param keywordTargetType keywordTargetType
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#keywordTargetType__
     */
    public void setKeywordTargetType(int keywordTargetType) {
        keywordTargetType__ = keywordTargetType;
    }
    /**
     * <p>senderAccountSid を取得します。
     * @return senderAccountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#senderAccountSid__
     */
    public int getSenderAccountSid() {
        return senderAccountSid__;
    }
    /**
     * <p>senderAccountSid をセットします。
     * @param senderAccountSid senderAccountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#senderAccountSid__
     */
    public void setSenderAccountSid(int senderAccountSid) {
        senderAccountSid__ = senderAccountSid;
    }
    /**
     * <p>toAccountSidArray を取得します。
     * @return toAccountSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#toAccountSidArray__
     */
    public int[] getToAccountSidArray() {
        return toAccountSidArray__;
    }
    /**
     * <p>toAccountSidArray をセットします。
     * @param toAccountSidArray toAccountSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query.
     *       SmlAccountsMailboxesMailsQueryPostParamModel#toAccountSidArray__
     */
    public void setToAccountSidArray(int[] toAccountSidArray) {
        toAccountSidArray__ = toAccountSidArray;
    }

    /**
     * <br>[機  能] アカウント使用可能判定
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
}
