package jp.groupsession.v2.sml.restapi.accounts.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextArea;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.model.SmlBinModel;
import jp.groupsession.v2.sml.restapi.dao.SmlRestapiMailDao;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] メール作成API パラメータモデル
 * <br>[解  説] POST
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsMailsPostParamModel  {
    /** アカウントSID */
    private int accountSid__;
    /** 宛先アカウントSID */
    private int[] toArray__;
    /** CCアカウントSID */
    private int[] ccArray__;
    /** BCCアカウントSID */
    private int[] bccArray__;
    /** 件名 */
    @MaxLength(100)
    @TextField
    private String subjectText__;
    /** マーク */
    @Selectable({"0", "4", "8", "101", "102", "103", "104", "201", "202", "203"})
    private int markType__ = 0;
    /** 本文 */
    @TextArea
    private String bodyText__;
    /** メッセージ作成モード */
    @Selectable({"0", "1", "2", "3", "11"})
    private int procType__ = Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_NEW);
    /** 参照メールSID */
    private int refarenceSid__ = -1;
    /** バイナリSID */
    private long[] binSidArray__;
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#accountSid__
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#accountSid__
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>toArray を取得します。
     * @return toArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#toArray__
     */
    public int[] getToArray() {
        return toArray__;
    }
    /**
     * <p>toArray をセットします。
     * @param toArray toArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#toArray__
     */
    public void setToArray(int[] toArray) {
        toArray__ = toArray;
    }
    /**
     * <p>ccArray を取得します。
     * @return ccArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#ccArray__
     */
    public int[] getCcArray() {
        return ccArray__;
    }
    /**
     * <p>ccArray をセットします。
     * @param ccArray ccArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#ccArray__
     */
    public void setCcArray(int[] ccArray) {
        ccArray__ = ccArray;
    }
    /**
     * <p>bccArray を取得します。
     * @return bccArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#bccArray__
     */
    public int[] getBccArray() {
        return bccArray__;
    }
    /**
     * <p>bccArray をセットします。
     * @param bccArray bccArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#bccArray__
     */
    public void setBccArray(int[] bccArray) {
        bccArray__ = bccArray;
    }
    /**
     * <p>subjectText を取得します。
     * @return subjectText
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#subjectText__
     */
    public String getSubjectText() {
        return subjectText__;
    }
    /**
     * <p>subjectText をセットします。
     * @param subjectText subjectText
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#subjectText__
     */
    public void setSubjectText(String subjectText) {
        subjectText__ = subjectText;
    }
    /**
     * <p>markType を取得します。
     * @return markType
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#markType__
     */
    public int getMarkType() {
        return markType__;
    }
    /**
     * <p>markType をセットします。
     * @param markType markType
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#markType__
     */
    public void setMarkType(int markType) {
        markType__ = markType;
    }
    /**
     * <p>bodyText を取得します。
     * @return bodyText
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#bodyText__
     */
    public String getBodyText() {
        return bodyText__;
    }
    /**
     * <p>bodyText をセットします。
     * @param bodyText bodyText
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#bodyText__
     */
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }
    /**
     * <p>procType を取得します。
     * @return procType
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#procType__
     */
    public int getProcType() {
        return procType__;
    }
    /**
     * <p>procType をセットします。
     * @param procType procType
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPostParamModel#procType__
     */
    public void setProcType(int procType) {
        procType__ = procType;
    }
    /**
     * <p>refarenceSid を取得します。
     * @return refarenceSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#refarenceSid__
     */
    public int getRefarenceSid() {
        return refarenceSid__;
    }
    /**
     * <p>refarenceSid をセットします。
     * @param refarenceSid refarenceSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#refarenceSid__
     */
    public void setRefarenceSid(int refarenceSid) {
        refarenceSid__ = refarenceSid;
    }
    /**
     * <p>binSidArray を取得します。
     * @return binSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#binSidArray__
     */
    public long[] getBinSidArray() {
        return binSidArray__;
    }
    /**
     * <p>binSidArray をセットします。
     * @param binSidArray binSidArray
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.
     * 　　　　　　SmlAccountsMailsPostParamModel#binSidArray__
     */
    public void setBinSidArray(long[] binSidArray) {
        binSidArray__ = binSidArray;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説] アノテーションでチェックできなかった部分を対象とする
     * <br>[備  考]
     *
     * @param appRootPath アプリケーションルートパス
     * @param mode 処理モード
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException
     */
    public void validateCheck(String appRootPath, int mode,
            Connection con, RequestModel reqMdl) throws SQLException {
        //件名
        //未入力チェック
        if (StringUtil.isNullZeroString(subjectText__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "subjectText"
                    )
                .setParamName("subjectText");
        }
        //スペース(改行)のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(subjectText__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_ONLY_SPACE,
                    "error.input.spase.cl.only",
                    "subjectText"
                    )
                .setParamName("subjectText");
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(subjectText__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_START_SPACE,
                    "error.input.spase.start",
                    "subjectText"
                    )
                .setParamName("subjectText");
        }
        //タブスペースチェック
        if (ValidateUtil.isTab(subjectText__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_TAB,
                    "error.input.tab.text",
                    "subjectText"
                    )
                .setParamName("subjectText");
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(subjectText__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.njapan.text3",
                    "subjectText"
                    )
                .setParamName("subjectText");
        }

        //本文
        //未入力チェック
        if (StringUtil.isNullZeroString(bodyText__)
                && mode != Sml020Form.VALIDATE_MODE_SAVE) {
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "bodyText"
                    )
                .setParamName("bodyText");
        }
        //MAX桁チェック
        int maxlength = 0;
        maxlength = SmlCommonBiz.getBodyLimitLength(appRootPath);
        if (maxlength == -100) {
            throw new RestApiValidateException(
                    EnumError.SYS_RUNTIME_ERROR,
                    "error.can.not.conffile.open.error"
                    );
        } else if (maxlength == -101) {
            throw new RestApiValidateException(
                    EnumError.SYS_RUNTIME_ERROR,
                    "error.can.not.conffile.read.error"
                    );
        } else if (maxlength != 0) {
            if (!StringUtil.isNullZeroString(bodyText__)) {
                if (bodyText__.length() > maxlength) {
                    throw new RestApiValidateException(
                            EnumError.PARAM_MAXLENGTH,
                            "error.input.length.text",
                            "bodyText",
                            maxlength
                            )
                        .setParamName("bodyText");
                }
            }
        }

        //宛先/cc/bcc
        checkToCcBcc(con, reqMdl, accountSid__, toArray__, GSConstSmail.RESTAPI_PARAM_TO);
        checkToCcBcc(con, reqMdl, accountSid__, ccArray__, GSConstSmail.RESTAPI_PARAM_CC);
        checkToCcBcc(con, reqMdl, accountSid__, bccArray__, GSConstSmail.RESTAPI_PARAM_BCC);

        if (mode == Sml020Form.VALIDATE_MODE_SOUSIN
                && toArray__.length == 0) {
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "toArray"
                    )
                .setParamName("toArray");
        }
    }

    /**
     * <br>[機  能] 元メールの添付ファイル引継ぎ判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException
     */
    public void validateBinArrayCheckPost(Connection con, RequestModel reqMdl)
            throws SQLException {
        //メールSIDで添付ファイル情報取得
        SmlBinDao dao = new SmlBinDao(con);
        List<SmlBinModel> baseMailBinList = dao.getBinList(refarenceSid__);
        List<Long> baseBinList = new ArrayList<Long>();
        for (SmlBinModel mdl : baseMailBinList) {
            baseBinList.add(mdl.getBinSid());
        }
        for (long binSid : binSidArray__) {
            if (!baseBinList.contains(binSid)) {
                //元メールに存在しないSIDを指定
                throw new RestApiPermissionException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(reqMdl).getMessage("cmn.attach.file")
                        );
            }
        }
    }
    /**
    *
    * <br>[機  能] アカウント 使用可能判定
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param accountSid アカウントSID
    * @param sessionUserSid セッションユーザSID
    * @throws SQLException
    */
    public void validateAccount(Connection con, RequestModel reqMdl,
           int accountSid, int sessionUserSid) throws SQLException {

        // 選択されているアカウントが使用可能かを判定する
        SmailDao smlDao = new SmailDao(con);
        if (accountSid <= 0 || !smlDao.canUseCheckAccount(sessionUserSid, accountSid)) {
            // アカウントが存在しない場合
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.account")
                    );
        }
    }
    /**
     * <br>[機  能] 参照メール使用可能判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  con     コネクション
     * @param reqMdl リクエストモデル
     * @param  accountSid  アカウントSID
     * @param  smlSid  メールSID
     * @throws SQLException SQL実行時例外
     */
    public void validateRefarenceMail(Connection con, RequestModel reqMdl,
            int accountSid, int smlSid)
        throws SQLException {

        SmailDao smlDao = new SmailDao(con);
        if (smlSid < 0) {
            // メールSIDが未指定
            throw new RestApiPermissionException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "refarenceSid"
                    );
        } else if (!smlDao.isViewSmail(accountSid, smlSid)) {
            // メール閲覧権限が無い為、アクセスエラー
            throw new RestApiValidateException(
                    EnumError.PARAM_IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.mail")
                    );
        }
    }
    /**
     * <br>[機  能] 宛先/CC/BCC判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con     コネクション
     * @param reqMdl リクエストモデル
     * @param accountSid  アカウントSID
     * @param sidArray  宛先/cc/bccリスト
     * @param paramName パラメータ名
     * @throws SQLException SQL実行時例外
     */
    public void checkToCcBcc(Connection con, RequestModel reqMdl,
            int accountSid, int[] sidArray, String paramName)
        throws SQLException {


        if (sidArray.length > 0) {
            List<String> strSidList = new ArrayList<String>();
            List<Integer> intSidList = new ArrayList<Integer>();
            for (int sid : sidArray) {
                strSidList.add(String.valueOf(sid));
                intSidList.add(sid);
            }

            // 宛先のうち存在しないアカウント
            HashSet<Integer> checkSidList = new HashSet<Integer>();
            checkSidList.addAll(intSidList);
            HashMap<Integer, Integer> existSidMap = null;
            if (!checkSidList.isEmpty()) {
                SmlRestapiMailDao apiDao = new SmlRestapiMailDao(con);
                existSidMap = apiDao.getExistSacSidList(checkSidList);
            }
            if (existSidMap == null || existSidMap.isEmpty()
                    || existSidMap.size() != checkSidList.size()) {
                throw new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        paramName
                        );
            }

            //削除済アカウントのチェック
            SmlAccountDao sacDao = new SmlAccountDao(con);
            int sacCount = sacDao.getCountDeleteAccount(strSidList);

            if (sacCount > 0) {
                throw new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "error.select.has.deleteaccount.list",
                        paramName
                        );
            }

            //送信制限送信先のチェック
            SmlBanDestDao sbdDao = new SmlBanDestDao(con);
            //ユーザアカウント
            List<Integer> banSidList = sbdDao.getBanDestUsrSidList(reqMdl.getSmodel().getUsrsid());
            for (int sid : banSidList) {
                if (intSidList.contains(sacDao.selectFromUsrSid(sid).getSacSid())) {
                    throw new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            paramName
                            );
                }
            }
            //代表アカウント
            List<Integer> banedAccSid = new ArrayList<Integer>();
            if (strSidList != null && strSidList.size() > 0) {
                banedAccSid = sbdDao.getBanDestAccSidList(
                        reqMdl.getSmodel().getUsrsid(), intSidList);
            }
            if (banedAccSid.size() > 0) {
                throw new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        paramName
                        );
            }
        }
    }
}
