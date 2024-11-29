package jp.groupsession.v2.cmn.login;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 認証処理の結果に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoginResultModel extends AbstractModel {

    /** エラーなし*/
    public static final int ECODE_NONE = 0;
    /** 該当ユーザなし*/
    public static final int ECODE_USERNONE = 1;
    /** システムメールユーザはログイン不可*/
    public static final int ECODE_SYSMAIL_LOGIN = 2;
    /** ログイン停止ユーザはログイン不可*/
    public static final int ECODE_LOGINTEISI_USER = 3;
    /** ワンタイムパスワードが必要*/
    public static final int ECODE_NEED_OTP = 4;
    /** システム予約ユーザはモバイルでのログインは不可*/
    public static final int ECODE_MBLLOGIN_SYSUSR = 5;
    /** 不正なトークン*/
    public static final int ECODE_TOKENNONE = 6;
    /** トークンの有効期限切れ*/
    public static final int ECODE_TOKENTIMEOVER = 7;
    /** トークンのパスワードが不正*/
    public static final int ECODE_MISS_OTPASS = 8;
    /** 別途シングルサインオンが必要*/
    public static final int ECODE_NEED_SSO = 9;
    /** 所属グループ未設定 */
    public static final int ECODE_BELONGGRP_NONE = 10;
    /** デフォルトグループ未設定 */
    public static final int ECODE_DEFGRP_NONE = 11;
    /** その他*/
    public static final int ECODE_OTHER = -1;

    /** ログインタイプ定数 通常ログイン */
    public static final String LOGINTYPE_NORMAL = "normal";

    /** 認証処理の結果 true:成功 false:失敗 */
    private boolean result__ = false;
    /** エラーコード*/
    private int errCode__;
    /** エラー */
    private ActionErrors errors__ = null;
    /** アクションフォワード */
    private ActionForward forward__ = null;
    /** ログイン対象ユーザ情報 認証成功後セッションに設定して用いる
     * <p> errCode__==ECODE_NEED_OTP || errCode__==ECODE_NEED_SSO 時にも値が設定される
     * <p> 上記の場合はセッションに設定してはいけない */
    private BaseUserModel loginUser__ = null;
    /** ログインタイプ*/
    private String loginType__ = LOGINTYPE_NORMAL;


    /**
     * <p>errors を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }
    /**
     * <p>errors をセットします。
     * @param errors errors
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
    }
    /**
     * <p>result を取得します。
     * @return result
     */
    public boolean isResult() {
        return result__;
    }
    /**
     * <p>result をセットします。
     * @param result result
     */
    public void setResult(boolean result) {
        result__ = result;
    }
    /**
     * <p>forward を取得します。
     * @return forward
     */
    public ActionForward getForward() {
        return forward__;
    }
    /**
     * <p>forward をセットします。
     * @param forward forward
     */
    public void setForward(ActionForward forward) {
        forward__ = forward;
    }
    /**
     * <p>loginUser を取得します。
     * @return loginUser
     * @see jp.groupsession.v2.cmn.login.LoginResultModel#loginUser__
     */
    public BaseUserModel getLoginUser() {
        return loginUser__;
    }
    /**
     * <p>loginUser をセットします。
     * @param loginUser loginUser
     * @see jp.groupsession.v2.cmn.login.LoginResultModel#loginUser__
     */
    public void setLoginUser(BaseUserModel loginUser) {
        loginUser__ = loginUser;
    }
    /**
     * <p>errCode を取得します。
     * @return errCode
     * @see jp.groupsession.v2.cmn.login.LoginResultModel#errCode__
     */
    public int getErrCode() {
        return errCode__;
    }
    /**
     * <p>errCode をセットします。
     * @param errCode errCode
     * @see jp.groupsession.v2.cmn.login.LoginResultModel#errCode__
     */
    public void setErrCode(int errCode) {
        errCode__ = errCode;
    }
    /**
     * <p>errCodeからerrors をセットします。
     *
     */
    public void setErrors() {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg;
        switch (errCode__) {
           case ECODE_NONE:
               setErrors(null);
               break;
           case ECODE_USERNONE:
               msg = new ActionMessage("error.auth.notfound.idpass");
               StrutsUtil.addMessage(errors, msg, "error.auth.notfound.idpass");
               setErrors(errors);
               break;
           case ECODE_SYSMAIL_LOGIN:
               msg = new ActionMessage("error.auth.notfound.idpass");
               StrutsUtil.addMessage(errors, msg, "error.auth.notfound.idpass");
               setErrors(errors);
               break;
           case ECODE_LOGINTEISI_USER:
               msg = new ActionMessage("error.auth.stop.login");
               StrutsUtil.addMessage(errors, msg, "error.auth.stop.login");
               setErrors(errors);
               break;
           case ECODE_NEED_OTP:
               msg = new ActionMessage("need.input.onetimepassword");
               StrutsUtil.addMessage(errors, msg, "need.input.onetimepassword");
               setErrors(errors);
               break;
           case ECODE_NEED_SSO:
               msg = new ActionMessage("need.auth.sso");
               StrutsUtil.addMessage(errors, msg, "need.auth.sso");
               setErrors(errors);
           case ECODE_MBLLOGIN_SYSUSR:
               msg = new ActionMessage("error.auth.notfound.idpass");
               StrutsUtil.addMessage(errors, msg, "error.auth.notfound.idpass");
               setErrors(errors);
              break;
           case ECODE_TOKENNONE:
               msg = new ActionMessage("error.notfound.otptoken");
               StrutsUtil.addMessage(errors, msg, "error.notfound.otptoken");
               setErrors(errors);
              break;
           case ECODE_TOKENTIMEOVER:
               msg = new ActionMessage("error.timeover.otptoken");
               StrutsUtil.addMessage(errors, msg, "error.timeover.otptoken");
               setErrors(errors);
              break;
           case ECODE_MISS_OTPASS:
               msg = new ActionMessage("error.miss.otpass");
               StrutsUtil.addMessage(errors, msg, "error.miss.otpass");
               setErrors(errors);
              break;
           default:
        }

    }
    /**
     * <p>loginType を取得します。
     * @return loginType
     * @see jp.groupsession.v2.cmn.login.LoginResultModel#loginType__
     */
    public String getLoginType() {
        return loginType__;
    }
    /**
     * <p>loginType をセットします。
     * @param loginType loginType
     * @see jp.groupsession.v2.cmn.login.LoginResultModel#loginType__
     */
    public void setLoginType(String loginType) {
        loginType__ = loginType;
    }

}
