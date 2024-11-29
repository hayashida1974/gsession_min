package jp.groupsession.v2.wml.wml190;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountUserProxyDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.wml040.Wml040Biz;

/**
 * <br>[機  能] WEBメール 個人設定 アカウント編集画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml190Biz {
    /** 画面ID */
    public static final String SCR_ID = "wml190";

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 署名情報の読み込みに失敗
     */
    public void setInitData(Connection con, Wml190ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException, IOToolsException {

        Wml040Biz biz040 = new Wml040Biz();

        //テンポラリディレクトリパス
        String tempDir = getTempDir(reqMdl);

        //代理人 使用許可
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setWml190proxyUserFlg(wmlBiz.isProxyUserAllowed(con));

        //戻る　表示
        if (paramMdl.getWml190initFlg() == GSConstWebmail.DSP_FIRST
                && paramMdl.getWmlCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            paramMdl.setWml190initFlg(GSConstWebmail.DSP_ALREADY);

            //編集 初期表示
        } else if (paramMdl.getWml190initFlg() == GSConstWebmail.DSP_FIRST
                && paramMdl.getWmlCmdMode() == GSConstWebmail.CMDMODE_EDIT) {

            int wacSid = paramMdl.getWmlAccountSid();

            //アカウント情報を設定する
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountMdl = accountDao.select(wacSid);

            paramMdl.setWml190name(accountMdl.getWacName());
            paramMdl.setWml190address(accountMdl.getWacAddress());

            int authType = accountMdl.getWacAuthType();
            paramMdl.setWml190authMethod(authType);
            if (authType == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {

                //認証形式 = OAuthの場合、プロバイダ・認証アカウントIDを設定
                int cotSid = accountMdl.getCotSid();
                OAuthBiz authBiz = new OAuthBiz();
                OauthDataModel authData = authBiz.getAuthData(con, cotSid);
                if (authData != null) {
                    paramMdl.setWml190provider(authData.getCouSid());
                    paramMdl.setWml190authAccount(authData.getAccountId());
                    paramMdl.setWml190cotSid(cotSid);
                }

            } else {
                //認証形式 = 基本認証
                paramMdl.setWml190receiveServer(accountMdl.getWacReceiveHost());
                paramMdl.setWml190receiveServerPort(String.valueOf(accountMdl.getWacReceivePort()));
                paramMdl.setWml190receiveServerEncrypt(accountMdl.getWacReceiveSsl());

                paramMdl.setWml190receiveServerUser(accountMdl.getWacReceiveUser());
                paramMdl.setWml190receiveServerPassword(accountMdl.getWacReceivePass());
                paramMdl.setWml190sendServer(accountMdl.getWacSendHost());
                paramMdl.setWml190sendServerPort(String.valueOf(accountMdl.getWacSendPort()));
                paramMdl.setWml190sendServerEncrypt(accountMdl.getWacSendSsl());

                paramMdl.setWml190smtpAuth(GSConstWebmail.WAC_SMTPAUTH_NO);
                if (accountMdl.getWacSmtpAuth() == GSConstWebmail.WAC_SMTPAUTH_YES) {
                    paramMdl.setWml190smtpAuth(GSConstWebmail.WAC_SMTPAUTH_YES);
                    paramMdl.setWml190sendServerUser(accountMdl.getWacReceiveUser());
                    paramMdl.setWml190sendServerPassword(accountMdl.getWacReceivePass());
                }
            }

            paramMdl.setWml190autoTo(accountMdl.getWacAutoto());
            paramMdl.setWml190autoCc(accountMdl.getWacAutocc());
            paramMdl.setWml190autoBcc(accountMdl.getWacAutobcc());

            paramMdl.setWml190theme(accountMdl.getWacTheme());
            paramMdl.setWml190quotes(accountMdl.getWacQuotes());

            //自動保存
            paramMdl.setWml190autoSaveMin(String.valueOf(accountMdl.getWacAutoSaveMinute()));

            paramMdl.setWml190initFlg(GSConstWebmail.DSP_ALREADY);

            //署名
            WmlAccountSignDao signDao = new WmlAccountSignDao(con);
            List<WmlAccountSignModel> signList = signDao.getSignList(wacSid);
            for (WmlAccountSignModel signDetailData : signList) {
                biz040.addSignModel(reqMdl, tempDir, signDetailData);
                if (signDetailData.getWsiDef() == GSConstWebmail.WSI_DEF_DEFAULT) {
                    paramMdl.setWml190sign(signDetailData.getWsiNo());
                }
            }

            //署名 自動挿入
            paramMdl.setWml190signAuto(accountMdl.getWacSignAuto());

            //代理人
            if (paramMdl.isWml190proxyUserFlg()) {
                WmlAccountUserProxyDao proxyUserDao = new WmlAccountUserProxyDao(con);
                List<Integer> proxyUserList = proxyUserDao.getProxyUserList(wacSid);
                String[] id = new String[proxyUserList.size()];
                for (int index = 0; index < id.length; index++) {
                    id[index] = String.valueOf(proxyUserList.get(index));
                }
                paramMdl.setWml190proxyUser(id);
            }

        }

        //署名を設定
        paramMdl.setWml190signList(biz040.createSignCombo(reqMdl, tempDir));

        //サーバ情報設定許可を設定
        paramMdl.setWml190settingServer(getSettingServer(con));

        //テーマコンボを設定
        paramMdl.setWml190themeList(biz040.createThemeCombo(con, reqMdl));

        //引用符コンボを設定
        paramMdl.setWml190quotesList(biz040.createQuotesCombo(reqMdl));

        //自動保存コンボを設定
        paramMdl.setWml190autoSaveList(biz040.createAtSaveCombo(reqMdl));

        //暗号化プロトコルコンボを設定
        MailEncryptBiz protcolBiz = new MailEncryptBiz(reqMdl);
        paramMdl.setWml190AngoProtocolCombo(
                protcolBiz.setDspProtocolLabels());

        //認証情報(認証アカウント名)を設定
        OAuthBiz authBiz = new OAuthBiz();
        OauthDataModel authData = authBiz.getAuthData(con, paramMdl.getWml190cotSid());
        boolean oauthCompFlg = (authData != null && authData.getRefreshToken() != null);
        if (oauthCompFlg) {
            paramMdl.setWml190authAccount(authData.getAccountId());
        }
        paramMdl.setWml190oauthCompFlg(oauthCompFlg);

    }

    /**
     * <br>[機  能] アカウントの編集が可能なユーザかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param userSid セッションユーザSID
     * @return true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditAccount(Connection con, int wacSid, int userSid) throws SQLException {
        //使用者かを判定する
        WmlDao wmlDao = new WmlDao(con);
        return wmlDao.canUseAccount(wacSid, userSid);
    }

    /**
     * <br>[機  能] サーバ情報設定許可を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return サーバ情報設定許可
     * @throws SQLException SQL実行時例外
     */
    public int getSettingServer(Connection con) throws SQLException {
        //サーバ情報設定許可を設定
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        WmlAdmConfModel adminMdl = adminDao.selectAdmData();
        return adminMdl.getWadSettingServer();
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        return wmlBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.deleteTempDir(reqMdl, SCR_ID);
    }
}
