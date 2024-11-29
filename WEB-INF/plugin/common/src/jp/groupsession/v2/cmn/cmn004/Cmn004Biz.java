package jp.groupsession.v2.cmn.cmn004;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.cmn001.Cmn001Biz;
import jp.groupsession.v2.cmn.dao.base.CmnOtpTokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpTokenModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
*
* <br>[機  能] ワンタイムパスワード入力画面 ビジネスロジック
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class Cmn004Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn001Biz.class);

    /** コネクション */
    protected Connection con__ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Cmn004Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] 画面初期化機能
     * <br>[解  説]
     * <br>[備  考]
     * @param param 画面パラメータ
     * @throws SQLException SQL実行時例外
     */
    public void doInit(ICmn004Param param) throws SQLException {
        log__.info("-- ワンタイムパスワード入力画面 初期化処理 開始");
        String token = param.getCmn004Token();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        CmnOtpTokenDao tokenDao = new CmnOtpTokenDao(con__);
        CmnOtpTokenModel tokenMdl = tokenDao.select(token);
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        CmnOtpConfModel otpConf = otpBiz.getOtpConf(con__);

        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel usrInf = usrDao.select(tokenMdl.getUsrSid());
        //送信先メールアドレス
        String toMail = otpBiz.secretedToAddres(usrInf.getUsiOtpsendAddress());

        //送信元メールアドレス
        String fromMail = otpConf.getCocSmtpFrom();
        //件名
        String subject = gsMsg.getMessage("cmn.cmn004.mail.subject");


        OtpSendResult result = new OtpSendResult();
        result.setOtpSendMailTo(toMail);
        result.setOtpSendMailFrom(fromMail);
        result.setOtpSendMailSubject(subject);
        result.setOtpSendDate(tokenMdl.getCotDate());

        param.setCmn004OtpSendResult(result);
        log__.info("-- ワンタイムパスワード入力画面 初期化処理 終了");

    }
}
