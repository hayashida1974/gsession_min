package jp.groupsession.v2.sml.restapi.accounts.mails.multi;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] メール状態変更API パラメータモデル
 * <br>[解  説] PUT
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsMailsMultiPutParamModel {
    /** アカウントSID */
    private int accountSid__ = -1;
    /** 受信メールSID */
    private int[] inboxMailSidArray__;
    /** 送信メールSID */
    private int[] sentMailSidArray__;
    /** 草稿メールSID */
    private int[] draftMailSidArray__;
    /** ラベルSID */
    private int[] labelSidArray__;
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>inboxMailSidArray を取得します。
     * @return inboxMailSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#inboxMailSidArray__
     */
    public int[] getInboxMailSidArray() {
        return inboxMailSidArray__;
    }
    /**
     * <p>inboxMailSidArray をセットします。
     * @param inboxMailSidArray inboxMailSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#inboxMailSidArray__
     */
    public void setInboxMailSidArray(int[] inboxMailSidArray) {
        inboxMailSidArray__ = inboxMailSidArray;
    }
    /**
     * <p>sentMailSidArray を取得します。
     * @return sentMailSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#sentMailSidArray__
     */
    public int[] getSentMailSidArray() {
        return sentMailSidArray__;
    }
    /**
     * <p>sentMailSidArray をセットします。
     * @param sentMailSidArray sentMailSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#sentMailSidArray__
     */
    public void setSentMailSidArray(int[] sentMailSidArray) {
        sentMailSidArray__ = sentMailSidArray;
    }
    /**
     * <p>draftMailSidArray を取得します。
     * @return draftMailSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#draftMailSidArray__
     */
    public int[] getDraftMailSidArray() {
        return draftMailSidArray__;
    }
    /**
     * <p>draftMailSidArray をセットします。
     * @param draftMailSidArray draftMailSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#draftMailSidArray__
     */
    public void setDraftMailSidArray(int[] draftMailSidArray) {
        draftMailSidArray__ = draftMailSidArray;
    }
    /**
     * <p>labelSidArray を取得します。
     * @return labelSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#labelSidArray__
     */
    public int[] getLabelSidArray() {
        return labelSidArray__;
    }
    /**
     * <p>labelSidArray をセットします。
     * @param labelSidArray labelSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.multi.
     *       SmlAccountsMailsMultiPutParamModel#labelSidArray__
     */
    public void setLabelSidArray(int[] labelSidArray) {
        labelSidArray__ = labelSidArray;
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

    /**
     * <br>[機  能] ラベル使用判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  labelSidList ラベルSIDリスト
     * @param  reqMdl  リクエストモデル
     * @param  accountSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void validateCheckLabel(Connection con, GsMessage gsMsg, int[] labelSidList,
            RequestModel reqMdl, int accountSid)
        throws SQLException {

        //必須チェック
        if (labelSidList.length == 0) {
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "labelSidArray")
                .setParamName("labelSidArray");
        }

        //対象アカウントのラベルリスト取得
        SmlLabelDao lDao = new SmlLabelDao(con);
        List<SmlLabelModel> labelMdlList = lDao.selectList(accountSid);
        List<Integer> labelList = new ArrayList<Integer>();
        for (SmlLabelModel mdl : labelMdlList) {
            labelList.add(mdl.getSlbSid());
        }

        for (int labelSid : labelSidList) {
            if (!labelList.contains(labelSid)) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(reqMdl).getMessage("cmn.label")
                        );
            }
        }
    }

    /**
     * <br>[機  能] メール判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  inboxSidList ラベルSIDリスト
     * @param  sentSidList  リクエストモデル
     * @param  draftSidList アカウントSID
     * @param  reqMdl  リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void validateCheckMail(Connection con, GsMessage gsMsg, int[] inboxSidList,
            int[] sentSidList, int[] draftSidList, RequestModel reqMdl)
        throws SQLException {

        //必須チェック
        if (inboxSidList.length == 0
                && sentSidList.length == 0
                && draftSidList.length == 0) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.shortmail")
                    );
        }
    }
}
