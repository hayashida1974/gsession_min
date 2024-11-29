package jp.groupsession.v2.wml.restapi.accounts.mails.send;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.WmlValidateMailAddressResultModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsPostParamModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestTemplateBinBiz;

/**
 * <br>[機  能] WEBメール 新規送信, 新規草稿作成用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsSendPostParamModel extends WmlAccountsMailsPostParamModel {

    @Override
    public String getSubjectText() {
        return super.getSubjectText();
    }

    @Override
    public String getBodyText() {
        return super.getBodyText();
    }

    @Override
    public int getSendPlanType() {
        return super.getSendPlanType();
    }

    @Override
    public int getCompressFileFlg() {
        return super.getCompressFileFlg();
    }

    @Override
    public int getProcType() {
        return super.getProcType();
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        List<RestApiValidateException> valErr = new ArrayList<>();
        try {
            //アカウント利用可能チェック
            super.validate(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(0, e);
        }

        boolean addressInputFlg = false;
        try {
            //送信先未入力チェック
            __validateSousinsakiInput(ctx);
            addressInputFlg = true;
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        if (addressInputFlg) {
            try {
                //宛先アドレスフォーマットチェック
                __validateAddress(ctx, getToArray(),
                    gsMsg.getMessage("cmn.from"), "toArray");
            } catch (RestApiValidateException e) {
                valErr.add(e);
            }
    
            try {
                //Ccアドレスフォーマットチェック
                __validateAddress(ctx, getCcArray(),
                    gsMsg.getMessage("cmn.cc"), "ccArray");
            } catch (RestApiValidateException e) {
                valErr.add(e);
            }
    
            try {
                //Bccアドレスフォーマットチェック
                __validateAddress(ctx, getBccArray(),
                    gsMsg.getMessage("cmn.bcc"), "bccArray");
            } catch (RestApiValidateException e) {
                valErr.add(e);
            }
        }        

        try {
            //予約送信日時のチェック
            __validateSendPlanDateTime(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        try {
            //内容の最大文字数チェック
            __validateBodyTextLength(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        try {
            //参照メールSIDのチェック
            __validateRefarenceSid(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        try {
            //参照メールのバイナリSIDのチェック
            __validateRefarenceBinSid(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        try {
            //テンプレートSIDのチェック
            __validateTemplateSid(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        try {
            //テンプレートのバイナリSIDのチェック
            __validateTemplateBinSid(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }

        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
    }

    /**
     * <br>[機  能] 送信先が入力されているかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    private void __validateSousinsakiInput(RestApiContext ctx) {

        List<String> allAddress = new ArrayList<String>();
        allAddress.addAll(Arrays.asList(getToArray()));
        allAddress.addAll(Arrays.asList(getCcArray()));
        allAddress.addAll(Arrays.asList(getBccArray()));        

        //送信先自体が入力されていない もしくは全て空文字の場合はエラー
        if (allAddress.isEmpty() || String.join("", allAddress).length() == 0) {
            throw new RestApiValidateException(
                EnumError.PARAM_REQUIRED,
                "error.input.required.text",
                new GsMessage(ctx.getRequestModel()).getMessage("wml.send.dest"));
        }
    }

    /**
     * <br>[機  能] 対象の文字列のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @param target 対象文字列
     * @param dispName エラーメッセージで使用するパラメータ名(日本語)
     * @param paramName エラー時に画面に出力するパラメータ名(英語)
     * @param noInputCheckFlg true:未入力チェックする, false:未入力チェックしない
     */
    private void __validateText(
        RestApiContext ctx, String target,
        String dispName, String paramName, boolean noInputCheckFlg) {

        //未入力
        if (noInputCheckFlg && StringUtil.isNullZeroString(target)) {
            throw new RestApiValidateException(
                EnumError.PARAM_FORMAT,
                "errors.free.msg",
                new GsMessage(ctx.getRequestModel()).getMessage(
                    "wml.274", new String[] {dispName, target})
            ).setParamName(paramName);
        }

        //MAX桁チェック
        if (target.length() > GSConstWebmail.RESTAPI_MAXLEN_ACCOUNT_ADDRESS) {
            throw new RestApiValidateException(
                EnumError.PARAM_MAXLENGTH,
                "error.input.length.text",
                dispName, GSConstWebmail.RESTAPI_MAXLEN_ACCOUNT_ADDRESS
            ).setParamName(paramName);
        }

        //スペースのみ
        if (ValidateUtil.isSpace(target)) {
            throw new RestApiValidateException(
                EnumError.PARAM_FORMAT,
                "error.input.spase.only",
                dispName
            ).setParamName(paramName);
        }

        //先頭スペース
        if (ValidateUtil.isSpaceStart(target)) {
            throw new RestApiValidateException(
                EnumError.PARAM_FORMAT,
                "error.input.spase.start",
                dispName
            ).setParamName(paramName);
        }

        //改行チェック
        if (target.lines().count() > 1) {
            throw new RestApiValidateException(
                EnumError.PARAM_LETER,
                "error.input.njapan.text2",
                dispName,
                new GsMessage(ctx.getRequestModel()).getMessage("cmn.linefeed")
            ).setParamName(paramName);
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            throw new RestApiValidateException(
                EnumError.PARAM_LETER,
                "error.input.njapan.text3",
                dispName, nstr
            ).setParamName(paramName);
        }
    }

    /**
     * <br>[機  能] 送信先がメールアドレスフォーマットに則っているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @param addressArray 送信先配列
     * @param dispName エラーメッセージで使用するパラメータ名(日本語)
     * @param paramName エラー時に画面に出力するパラメータ名(英語)
     * @throws SQLException 
     */
    private void __validateAddress(RestApiContext ctx,
        String[] addressArray, String dispName, String paramName) throws SQLException {

        if (addressArray.length == 0) {
            return;
        }

        WmlBiz wBiz = new WmlBiz();
        WmlAdmConfModel wadMdl = wBiz.getAdminConf(ctx.getCon());
        String[] tldList = wBiz.getTldList(wadMdl);

        String paramNameIndex;
        int index = 0;
        for (String address : addressArray) {
            paramNameIndex = paramName + "[" + index + "]";
            __validateText(ctx, address, dispName, paramNameIndex, true);
            index++;
        }

        //メールアドレスフォーマットチェック
        String target = WmlMailSendBiz.joinAddress(addressArray);
        Connection con = ctx.getCon();
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlRestAccountBiz accountBiz = new WmlRestAccountBiz();
        int wacSid = accountBiz.getWmlAccountSid(con, getAccountId());
        WmlAccountModel accountMdl = accountDao.select(wacSid);
        String encode = WmlBiz.getSendEncode(accountMdl);

        WmlValidateMailAddressResultModel resultMdl
            = GSValidateWebmail.validateMailAddress(
                    target, dispName,
                    ctx.getRequestModel(), encode, tldList);

        List<ActionMessage> result = resultMdl.getErrMessageList();
        for (ActionMessage msg : result) {
            throw new RestApiValidateException(
                EnumError.PARAM_FORMAT,
                msg.getKey(),
                msg.getValues()
            ).setParamName(paramName);
        }

    }

    /**
     * <br>[機  能] 内容がWEBメール本文の最大文字数内に収まっているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    private void __validateBodyTextLength(RestApiContext ctx) {

        if (StringUtil.isNullZeroString(getBodyText())) {
            throw new RestApiValidateException(
                EnumError.PARAM_REQUIRED,
                "error.input.required.text",
                new GsMessage(ctx.getRequestModel()).getMessage("cmn.body")
            ).setParamName("bodyText");
        }

        String appRootPath = ctx.getAppRootPath();
        int bodyLimit = WmlBiz.getBodyLimitLength(appRootPath);
        String bodyText = WmlMailSendBiz.getFormattedBody(
            getBodyText(), bodyLimit, GSConstWebmail.SEND_HTMLMAIL_TEXT);

        if (bodyLimit > 0 && bodyText.length() > bodyLimit) {
            throw new RestApiValidateException(
                EnumError.PARAM_MAXLENGTH,
                "error.input.length.text",
                new GsMessage(ctx.getRequestModel()).getMessage("cmn.body")
            ).setParamName("bodyText");
        }
    }

    /**
     * <br>[機  能] 予約送信日時のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    private void __validateSendPlanDateTime(RestApiContext ctx) {

        if (getSendPlanType() != GSConstWebmail.TIMESENT_FUTURE) {
            return;
        }

        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        if (getSendPlanDateTime() == null) {
            throw new RestApiValidateException(
                EnumError.PARAM_REQUIRED,
                "error.input.required.text",
                gsMsg.getMessage("wml.211") + gsMsg.getMessage("cmn.date")
            ).setParamName("sendPlanDate");
        }

        UDate now = new UDate();
        UDate planDate = getSendPlanDateTime();
        if (now.compareDateYMDHM(planDate) != UDate.LARGE) {
            throw new RestApiValidateException(
                EnumError.PARAM_OTHER_INVALID,
                "errors.free.msg",
                gsMsg.getMessage("wml.wml010.33")
            ).setParamName("sendPlanDate");
        }
    }

    /**
     * <br>[機  能] 参照メールSIDをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     */
    private void __validateRefarenceSid(RestApiContext ctx) throws SQLException {

        //新規作成の場合は使用しないため処理を終了する
        if (getProcType() == GSConstWebmail.SEND_TYPE_NORMAL) {
            return;
        }

        long refarenceSid = getRefarenceSid();
        //未入力
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        if (refarenceSid < 0) {
            throw new RestApiValidateException(
                EnumError.PARAM_REQUIRED,
                "error.input.required.text",
                gsMsg.getMessage("wml.324")
            ).setParamName("refarenceSid");
        }

        //実行アカウントが参照メールSIDにアクセスできない場合
        Connection con = ctx.getCon();
        WmlRestAccountBiz biz = new WmlRestAccountBiz();
        int wacSid = biz.getWmlAccountSid(con, getAccountId());

        WmlRestMailDataBiz mailBiz = new WmlRestMailDataBiz();
        boolean canReadFlg = mailBiz.canAccess(con, wacSid, refarenceSid);

        if (!canReadFlg) {
            throw new RestApiValidateException(
                WmlEnumReasonCode.PARAM_CANT_ACCESS_MAIL,
                "error.input.notvalidate.data",
                gsMsg.getMessage("wml.324")
            ).setParamName("refarenceSid");
        }
    }

    /**
     * <br>[機  能] 参照メールのバイナリSIDをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     */
    private void __validateRefarenceBinSid(RestApiContext ctx) throws SQLException {

        //送信タイプが転送の時以外は使用しない
        if (getProcType() != GSConstWebmail.SEND_TYPE_FORWARD
            && getProcType() != GSConstWebmail.SEND_TYPE_EDIT) {
            return;
        }

        //参照メールSIDが入力されていない時は使用しない
        long refarenceSid = getRefarenceSid();
        if (refarenceSid < 0) {
            return;
        }
        
        //参照メールと紐づいているかを確認
        WebmailDao dao = new WebmailDao(ctx.getCon());
        List<MailTempFileModel> tempList = dao.getTempFileList(refarenceSid);
        List<Long> tempSidList = tempList.stream()
            .map(mdl -> mdl.getBinSid())
            .collect(Collectors.toList());
            for (long tempBinSid : getRefarenceBinSidArray()) {
                if (!tempSidList.contains(tempBinSid)) {
                    throw new RestApiValidateException(
                        WmlEnumReasonCode.PARAM_CANT_ACCESS_MAIL_TEMPFILE,
                        "error.input.notvalidate.data",
                        new GsMessage(ctx.getRequestModel()).getMessage("wml.325")
                    ).setParamName("refarenceBinSidArray");
                }
            }
    }

    /**
     * <br>[機  能] テンプレートSIDをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     */
    private void __validateTemplateSid(RestApiContext ctx) throws SQLException {

        int templateSid = getTemplateSid();
        if (templateSid < 0) {
            return;
        }

        //テンプレートSIDをチェックする
        WmlRestTemplateBinBiz wtbBiz = new WmlRestTemplateBinBiz();
        wtbBiz.validateBinReqTemplate(ctx, getAccountId(), templateSid, "templateSid");
    }

    /**
     * <br>[機  能] テンプレートのバイナリSIDをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @throws SQLException 
     */
    private void __validateTemplateBinSid(RestApiContext ctx) throws SQLException {

        //テンプレートSIDが未入力の時はチェックを行わない
        int templateSid = getTemplateSid();
        if (templateSid < 0) {
            return;
        }

        //テンプレートのバイナリSIDが未入力の時はチェックを行わない
        if (getTemplateBinSidArray().length == 0) {
            return;
        }

        //テンプレートSIDをチェックする
        WmlRestTemplateBinBiz wtbBiz = new WmlRestTemplateBinBiz();
        wtbBiz.validateBinReqTempBin(
            ctx, templateSid, getTemplateBinSidArray(), "templateBinSidArray");
    }

}
