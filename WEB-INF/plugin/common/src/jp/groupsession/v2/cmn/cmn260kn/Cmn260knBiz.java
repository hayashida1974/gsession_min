package jp.groupsession.v2.cmn.cmn260kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;

/**
 * <br>[機  能] OAuth認証情報登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn260knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn260knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Cmn260knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel パラメータ格納モデル
     */
    public void setInitData(Cmn260knParamModel paramModel) {

        //備考をHTML表示用に変換する。
        paramModel.setCmn260knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(paramModel.getCmn260biko()));

    }
    /**
     * <br>[機  能] 新規登録画面処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    protected void _doInsert(
            Cmn260knParamModel paramMdl,
            int userSid,
            MlCountMtController cntCon) throws SQLException {

        boolean commitFlg = false;

        try {
            //認証情報SID採番
            int authSaiSid = (int) cntCon.getSaibanNumber(
                    GSConstCommon.SBNSID_COMMON,
                    GSConstCommon.SBNSID_SUB_OAUTH,
                    userSid);

            //認証情報登録Model
            CmnOauthModel couMdl  = __getOauthMdl(paramMdl, userSid, authSaiSid);
            //認証情報を登録する
            CmnOauthDao couDao = new CmnOauthDao(con__);
            couDao.insert(couMdl);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("新規登録失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 編集画面登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    protected void _doUpdate(
            Cmn260knParamModel paramMdl,
            int userSid) throws SQLException {

        boolean commitFlg = false;

        try {
            //認証情報更新Model
            CmnOauthModel couMdl  = __getOauthMdl(paramMdl, userSid, paramMdl.getCmnAuthSid());
            //認証情報を更新する
            CmnOauthDao couDao = new CmnOauthDao(con__);
            couDao.updateOAuth(couMdl);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("編集失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 新規登録または更新用のCmnOauthModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @param authSid 認証SID
     * @return CmnOauthModel
     * @throws SQLException SQL実行例外
     */
    private CmnOauthModel __getOauthMdl(
            Cmn260knParamModel paramMdl,
            int userSid,
            int authSid) throws SQLException {

        CmnOauthModel couMdl = new CmnOauthModel();
        UDate now = new UDate();

        if (paramMdl.getCmn250CmdMode() == GSConstCommon.MODE_ADD) {
            couMdl.setCouAuid(userSid);
            couMdl.setCouAdate(now);
        }
        couMdl.setCouSid(authSid);
        couMdl.setCouProvider(paramMdl.getCmn260provider());
        couMdl.setCouAuthId(paramMdl.getCmn260cliendId());
        couMdl.setCouAuthSecret(paramMdl.getCmn260secret());
        couMdl.setCouBiko(paramMdl.getCmn260biko());
        couMdl.setCouEuid(userSid);
        couMdl.setCouEdate(now);

        return couMdl;
    }
}
