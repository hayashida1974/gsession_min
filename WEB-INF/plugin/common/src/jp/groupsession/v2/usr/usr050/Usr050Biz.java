package jp.groupsession.v2.usr.usr050;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;

/**
 * <br>[機  能] メイン 個人設定 パスワード変更画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr050Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     *
     */
    public Usr050Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return sessionUsrSid セッションユーザSID
     */
    public int getSessionUserSid() {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }

    /**
     * <br>[機  能] パスワードを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param param パスワード更新情報Model
     * @param backScreen 戻り先画面の区分値
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード暗号化時例外
     */
    public void updatePassword(Connection con, Usr050Model param, int backScreen)
        throws SQLException, EncryptionException {

        log__.debug("パスワード更新");

        CmnUsrmModel usrmMdl = new CmnUsrmModel();

        if (backScreen == GSConstMain.BACK_MAIN_ADM_SETTING) {
            usrmMdl.setUsrSid(0);
        } else {
            usrmMdl.setUsrSid(param.getUsrSid());
        }
        usrmMdl.setUsrPswd(GSPassword.getEncryPassword(param.getUsrNewPassWord()));
        usrmMdl.setUsrEuid(param.getUsrSid());
        usrmMdl.setUsrEdate(new UDate());
        usrmMdl.setUsrPswdKbn(GSConstUser.PSWD_UPDATE_OFF);
        usrmMdl.setUsrPswdEdate(new UDate());

        CmnUsrmDao usrmDao = new CmnUsrmDao(con);
        usrmDao.updatePassword(usrmMdl);
    }
}