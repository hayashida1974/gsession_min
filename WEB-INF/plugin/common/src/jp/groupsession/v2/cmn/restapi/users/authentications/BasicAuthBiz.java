package jp.groupsession.v2.cmn.restapi.users.authentications;

import java.sql.Connection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.CloneableUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiAuthException;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
/**
 *
 * <br>[機  能] 通常認証ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BasicAuthBiz {
    public static class Builder {
        /**パスワード*/
        private Connection con__;
        /**ユーザID*/
        private String userId__;
        /**パスワード*/
        private String password__;
        /**アクションマップ*/
        private ActionMapping map__;
        /**プラグインコンフィグ*/
        private PluginConfig pconfig__;
        /**
         * <p>userId をセットします。
         * @param userId userId
         * @see jp.groupsession.v2.cmn.restapi.users.authentications.Builder#userId__
         * @return this
         */
        public Builder setUserId(String userId) {
            userId__ = userId;
            return this;
        }
        /**
         * <p>password をセットします。
         * @param password password
         * @see jp.groupsession.v2.cmn.restapi.users.authentications.BasicAuthBiz.Builder#password__
         * @return this
         */
        public Builder setPassword(String password) {
            password__ = password;
            return this;
        }
        /**
         *
         * <br>[機  能] インスタンス生成
         * <br>[解  説]
         * <br>[備  考]
         * @param ctx restApiコンテキスト
         * @return BasicAuthBiz
         */
        public BasicAuthBiz build(RestApiContext ctx) {
            Builder param = new Builder();
            CloneableUtil.copyField(param, this);
            param.con__ = ctx.getCon();
            param.map__ = ctx.getMap();
            param.pconfig__ = ctx.getPluginConfig();
            return new BasicAuthBiz(param);
        }

    }
    /** パラメータモデル */
    private final Builder param__;
    public static Builder builder() {
        Builder ret = new Builder();
        return ret;
    }
    /**
     *
     * コンストラクタ
     * @param param パラメータモデル
     */
    private BasicAuthBiz(Builder param) {
        param__ = param;
    }
    public LoginResultModel execute(HttpServletRequest req) {
        CommonBiz cmnBiz = new CommonBiz();
        ILogin loginBiz = cmnBiz.getLoginInstance();


        //ログイン処理に必要な情報を設定する
        LoginModel loginModel = new LoginModel();
        loginModel.setLoginId(param__.userId__);
        loginModel.setPassword(param__.password__);
        loginModel.setCon(param__.con__);
        loginModel.setReq(req);
        loginModel.setMap(param__.map__);
        loginModel.setPluginConfig(param__.pconfig__);

        LoginResultModel resultModel;
        try {
            resultModel = loginBiz.idpassAuthApi(loginModel);
        } catch (Exception e) {
            throw new RuntimeException("ログイン処理例外");
        }

        //ログインエラー （システムユーザ）
        if (resultModel.getErrCode() == LoginResultModel.ECODE_SYSMAIL_LOGIN) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.inpermissive.resource.path");
        }
        //ログインエラー （ログイン停止）
        if (resultModel.getErrCode() == LoginResultModel.ECODE_LOGINTEISI_USER) {
            throw new RestApiPermissionException(
                    EnumResourceReasonCode.RESOURCE_USER_LOGIN_STOP,
                    "error.auth.stop.login.api");
        }
        //ログインエラー （所属グループ未設定）
        if (resultModel.getErrCode() == LoginResultModel.ECODE_BELONGGRP_NONE) {
            throw new RestApiPermissionException(
                    EnumResourceReasonCode.RESOURCE_AUTH_NOTSET_BELONGGRP,
                    "error.auth.notset.belonggroup.api");
        }
        //ログインエラー （デフォルトグループ未設定）
        if (resultModel.getErrCode() == LoginResultModel.ECODE_DEFGRP_NONE) {
            throw new RestApiPermissionException(
                    EnumResourceReasonCode.RESOURCE_AUTH_NOTSET_DEFGRP,
                    "error.auth.notset.defgroup.api");
        }
        
        
        
        //別システムによるSSO連携が求められた場合
        if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_SSO) {

            return resultModel;
        }

        //ワンタイムパスワード入力が求められた場合
        if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_OTP) {

            return resultModel;
        }

        //ログイン処理に失敗 かつ ActionErrorsが設定されている場合
        if (!resultModel.isResult() && resultModel.getErrors() != null) {
            RestApiAuthException e = new RestApiAuthException();
            @SuppressWarnings("rawtypes")
            Iterator it = null;
            e.setReasonCode(EnumError.AUTH_IDPASSWORD);
            it = resultModel.getErrors().get();
            if (it.hasNext()) {
                ActionMessage error = (ActionMessage) it.next();
                e.setMessageResource(error.getKey(), error.getValues());
            }
            throw e;
        }
        if (resultModel.getLoginUser() == null) {
            throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_IDPASSWORD)
                .setMessageResource("error.auth.notfound.idpass.api");
        }
        return resultModel;

    }

}
