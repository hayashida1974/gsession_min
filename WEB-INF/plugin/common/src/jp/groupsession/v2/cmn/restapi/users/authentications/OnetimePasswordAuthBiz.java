package jp.groupsession.v2.cmn.restapi.users.authentications;

import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnOtpTokenDao;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiAuthException;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OnetimePasswordAuthBiz {
    /** パラメータモデル*/
    private final CmnAuthenticationsOtpParamModel paramMdl__;
    /** RestApi コンテキスト*/
    private final RestApiContext ctx__;
    /**
     *
     * コンストラクタ
     * @param paramMdl パラメータモデル
     * @param ctx コンテキスト
     */
    public OnetimePasswordAuthBiz(CmnAuthenticationsOtpParamModel paramMdl,
            RestApiContext ctx) {
        super();
        paramMdl__ = paramMdl;
        ctx__ = ctx;
    }

    /**
     *
     * <br>[機  能] 認証の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param req Httpサーブレットリクエスト
     * @return 認証結果
     */
    public LoginResultModel execute(HttpServletRequest req) {
        LoginResultModel result;
        try {
            OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
            result = otpBiz.checkTokenLive(
                    paramMdl__.getOnetimePasswordTokenText(),
                    ctx__.getCon());
            @SuppressWarnings("rawtypes")
            Iterator it = null;
            if (result.getErrCode() != LoginResultModel.ECODE_NONE) {
                RestApiAuthException e = new RestApiAuthException();

                if (result.getErrCode() == LoginResultModel.ECODE_TOKENNONE) {
                    e.setReasonCode(EnumError.AUTH_TOKEN);
                }
                if (result.getErrCode() == LoginResultModel.ECODE_TOKENTIMEOVER) {
                    e.setReasonCode(EnumParamReasonCode.PARAM_TOKEN_DEAD);
                }

                it = result.getErrors().get();
                if (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    e.setMessageResource(error.getKey(), error.getValues());
                }
                throw e;
            }
            //ログイン処理を行う
            result = otpBiz.otpAuth(
                    paramMdl__.getOnetimePasswordTokenText(),
                    paramMdl__.getPassword(),
                    ctx__.getCon());
            //ログインエラー （システムユーザ）
            if (result.getErrCode() == LoginResultModel.ECODE_SYSMAIL_LOGIN) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.inpermissive.resource.path");
            }
            //ログインエラー （ログイン停止）
            if (result.getErrCode() == LoginResultModel.ECODE_LOGINTEISI_USER) {
                throw new RestApiPermissionException(
                        EnumResourceReasonCode.RESOURCE_USER_LOGIN_STOP,
                        "error.auth.stop.login.api");
            }
            //ログイン処理に失敗 かつ ActionErrorsが設定されている場合
            if (!result.isResult() && result.getErrors() != null) {
                RestApiAuthException e = new RestApiAuthException();
                e.setReasonCode(EnumError.AUTH_IDPASSWORD);
                it = result.getErrors().get();
                if (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    e.setMessageResource(error.getKey(), error.getValues());
                }
                throw e;
            }
            if (result.getLoginUser() == null) {
                throw new RestApiAuthException()
                    .setReasonCode(EnumError.AUTH_IDPASSWORD)
                    .setMessageResource("error.auth.notfound.idpass.api");
            }
            //管理者ユーザ
            if (result.getLoginUser().getUsrsid() == 0) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.inpermissive.resource.path");
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時 例外", e);
        }
        try {

            BaseUserModel smodel = result.getLoginUser();

            if (!smodel.getLgid().equals(paramMdl__.getUserId())) {
                RestApiAuthException e = new RestApiAuthException();
                e.setReasonCode(EnumError.AUTH_TOKEN);
                throw e;
            }

            //ワンタイムパスワードを破棄
            CmnOtpTokenDao tokenDao = new CmnOtpTokenDao(ctx__.getCon());
            tokenDao.delete(paramMdl__.getOnetimePasswordTokenText());

            return result;
        } catch (RestApiAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時 例外", e);
        }

    }


}
