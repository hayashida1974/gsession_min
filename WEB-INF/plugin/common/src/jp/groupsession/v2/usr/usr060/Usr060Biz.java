package jp.groupsession.v2.usr.usr060;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnOtpAtokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpAtokenModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
/**
 *
 * <br>[機  能] ワンタイムパスワード通知アドレス設定 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr060Biz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /**
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Usr060Biz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 前提設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doPrepareSetting(IUsr060Param param) throws SQLException {

        CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con__);
        CmnOtpAtokenModel token;
        if (!StringUtil.isNullZeroString(param.getUsr060Token())
                && reqMdl__.getSmodel() == null) {
            //ログイン画面からの遷移時 トークンからログインユーザ情報の取得
            token = atokenDao.select(param.getUsr060Token());
            AuthDao aDao = new AuthDao(con__);
            BaseUserModel smodel = aDao.selectLogin(token.getUsrSid());
            reqMdl__.setSmodel(smodel);
        }

    }
    /**
     *
     * <br>[機  能] トークン有効性チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param usr060Token トークン
     * @param smodel セッションユーザ情報
     * @return 結果
     * @throws SQLException 実行時例外
     */
    public LoginResultModel checkTokenLive(String usr060Token,
            BaseUserModel smodel) throws SQLException {
        CmnOtpAtokenModel token = null;
        if (!StringUtil.isNullZeroString(usr060Token)) {
            CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con__);
            token = atokenDao.select(usr060Token);
        }
        LoginResultModel resultModel = __checkTokenLive(token);
        if (resultModel.getErrCode() != LoginResultModel.ECODE_NONE) {
            return resultModel;
        }

        AuthDao aDao = new AuthDao(con__);
        BaseUserModel usrModel = aDao.selectLogin(token.getUsrSid());
        //トークンのユーザが不正
        if (usrModel == null) {
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            return resultModel;
        }
        //ログインユーザとトークンのユーザが異なる

        if (smodel != null && usrModel.getUsrsid() != smodel.getUsrsid()) {
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            return resultModel;
        }
        return resultModel;
    }
    /**
    *
    * <br>[機  能] トークン有効性チェックを行う
    * <br>[解  説]
    * <br>[備  考]
    * @param token トークン
    * @return 結果
    * @throws SQLException 実行時例外
    */
    private LoginResultModel __checkTokenLive(CmnOtpAtokenModel token) throws SQLException {
        LoginResultModel resultModel = new LoginResultModel();
        if (token == null) {
            //トークンが不正
            resultModel.setErrCode(LoginResultModel.ECODE_TOKENNONE);
            resultModel.setErrors();

            return resultModel;
        }
        UDate now = new UDate();
        if (now.compare(now, token.getCoaLimitDate()) == UDate.SMALL) {
            //トークンが有効期限切れ
            resultModel.setErrCode(LoginResultModel.ECODE_TOKENTIMEOVER);
            resultModel.setErrors();

            return resultModel;
        }
        return resultModel;
    }
    /**
     *
     * <br>[機  能] 戻り先フォワード文字列取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param isSession セッションの有無
     * @return 戻り先フォワード文字列
     */
    public String getBackForwardStr(boolean isSession) {
        if (isSession) {
            return "backPconf";
        } else {
            return "backLogin";
        }
    }
    /**
     *
     * <br>[機  能] ワンタイムパスワード設定画面通知先アドレストークンとワンタイムパスワードで認証を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return 結果モデル
     * @throws SQLException SQL実行時例外
     */
    public LoginResultModel checkAuth(IUsr060Param param) throws SQLException {
        CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con__);
        CmnOtpAtokenModel token = atokenDao.select(param.getUsr060Token());
        LoginResultModel resultModel = __checkTokenLive(token);

        if (resultModel.getErrCode() != LoginResultModel.ECODE_NONE) {
            return resultModel;
        }
        //送信先アドレスが未設定
        if (StringUtil.isNullZeroString(token.getCoaAddress())) {
            resultModel.setErrCode(LoginResultModel.ECODE_TOKENNONE);
            resultModel.setErrors();
            return resultModel;
        }
        //パスワードが不正
        if (!token.getCoaPass().equals(param.getUsr060KakuninPass())) {
            resultModel.setErrCode(LoginResultModel.ECODE_MISS_OTPASS);
            resultModel.setErrors();
            return resultModel;
        }
        AuthDao aDao = new AuthDao(con__);
        BaseUserModel usrModel = aDao.selectLogin(token.getUsrSid());
        //トークンのユーザが不正
        if (usrModel == null) {
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            return resultModel;
        }
        //ログインユーザとトークンのユーザが異なる
        BaseUserModel smodel = reqMdl__.getSmodel();
        if (smodel != null && usrModel.getUsrsid() != smodel.getUsrsid()) {
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            return resultModel;
        }
        resultModel.setLoginUser(usrModel);
        return resultModel;
    }

    /**
     *
     * <br>[機  能] 初期化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param admFlg GS管理者フラグ
     * @throws SQLException 実行時例外
     */
    public void doInit(IUsr060Param param, boolean admFlg) throws SQLException {
        if (StringUtil.isNullZeroString(param.getUsr060Token())) {
            //トークン未発行（個人情報画面からの遷移）

            param.setUsr060AddMode(false);

            //既存情報 取得
            CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
            int targetUserSid = reqMdl__.getSmodel().getUsrsid();
            if (admFlg) {
                targetUserSid = 0;
            }
            CmnUsrmInfModel usrInf = usrDao.select(targetUserSid);
            param.setUsr060SendToAddress(usrInf.getUsiOtpsendAddress());
        } else {
            param.setUsr060AddMode(true);
        }
    }
    /**
     *
     * <br>[機  能] 描画設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doDsp(IUsr060Param param) throws SQLException {
        if (param.isUsr060otpSended()) {
            CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con__);
            CmnOtpAtokenModel token = null;
            token = atokenDao.select(param.getUsr060Token());
            param.setUsr060SendToAddress(token.getCoaAddress());
        }

    }
    /**
     *
     * <br>[機  能] 確認用パスワード送信処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return 送信結果
     * @throws SQLException 実行時例外
     */
    public OtpSendResult doSendPass(IUsr060Param param) throws SQLException {
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con__);
        CmnOtpAtokenModel token = null;

        String pass;
        //トークン未発行（個人情報画面からの遷移）
        if (StringUtil.isNullZeroString(param.getUsr060Token())) {
            token = otpBiz.saveOtpAtoken(reqMdl__.getSmodel(), reqMdl__);
            param.setUsr060Token(token.getCoaToken());
        } else {
            token = atokenDao.select(param.getUsr060Token());
        }
        pass = otpBiz.createOtp();
        UDate now = new UDate();
        UDate limit = now.cloneUDate();
        limit.addMinute(GSConstCommon.OTP_LIMIT_TIME);
        token.setCoaToken(param.getUsr060Token());
        token.setCoaAddress(param.getUsr060SendToAddress());
        token.setCoaPass(pass);
        token.setCoaDate(now);
        token.setCoaLimitDate(limit);

        atokenDao.update(token);

        OtpSendResult result = new OtpSendResult();
        result.setOtpSendDate(now);
        otpBiz.sendOtpMail(result, pass,
                param.getUsr060SendToAddress(),
                reqMdl__, con__);
        if (result.getEcode() == OtpSendResult.ECODE_NONE) {
            param.setUsr060otpSended(true);
        }
        return result;

    }
    /**
     *
     * <br>[機  能] 再入力ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doReenter(IUsr060Param param) throws SQLException {
        doDsp(param);
        param.setUsr060otpSended(false);

    }
    /**
     *
     * <br>[機  能] アドレス更新確定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param smodel ログインユーザ
     * @throws SQLException 実行時例外
     */
    public void doUpdateAddress(IUsr060Param param, BaseUserModel smodel) throws SQLException {
        CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con__);
        CmnOtpAtokenModel token = atokenDao.select(param.getUsr060Token());
        //更新するアドレスをパラメータに格納（ログ出力用）
        param.setUsr060SendToAddress(token.getCoaAddress());

        //既存情報 取得
        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel usrInf = usrDao.select(smodel.getUsrsid());
        //更新
        usrInf.setUsiOtpsendAddress(token.getCoaAddress());
        usrInf.setUsiEdate(new UDate());
        usrInf.setUsiEuid(smodel.getUsrsid());
        usrDao.updateCmnUserInf(usrInf);
        //トークンを削除
        atokenDao.delete(token.getCoaToken());

    }

}
