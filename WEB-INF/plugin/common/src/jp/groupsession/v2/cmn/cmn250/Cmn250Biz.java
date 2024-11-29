package jp.groupsession.v2.cmn.cmn250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.dao.base.CmnOauthTokenDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] OAuth認証情報管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn250Biz {

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
            Cmn250ParamModel paramMdl,
            int userSid,
            RequestModel reqMdl) throws SQLException {

        List<Cmn250DisplayModel> dspList = new ArrayList<Cmn250DisplayModel>();
        CmnOauthDao dao = new CmnOauthDao(con);
        dspList = dao.getAuthNameList();

        GsMessage gsMsg = new GsMessage(reqMdl);
        for (Cmn250DisplayModel dspMdl : dspList) {
            if (dspMdl.getCouProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
                dspMdl.setAuthValue(gsMsg.getMessage(GSConstCommon.PROVIDER_NAME_GOOGLE));

            } else if (dspMdl.getCouProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
                dspMdl.setAuthValue(gsMsg.getMessage(GSConstCommon.PROVIDER_NAME_MICROSOFT));
            }
        }

        paramMdl.setAuthList(dspList);
    }

    /**
     * <br>[機  能] 認証情報名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param targetSid ターゲットSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    protected int _getAuthName(Connection con, int targetSid) throws SQLException {

        CmnOauthDao authDao = new CmnOauthDao(con);
        CmnOauthModel authMdl = authDao.select(targetSid);

        return authMdl.getCouProvider();
    }

    /**
     * <br>[機  能] 削除対象となる認証情報の存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    protected boolean _existOauthData(Connection con, Cmn250ParamModel paramMdl)
            throws SQLException {

        int authSid = paramMdl.getCmnAuthSid();
        CmnOauthDao wouDao = new CmnOauthDao(con);
        return wouDao.existOauthData(authSid);
    }

    /**
     * <br>[機  能] 削除対象となる認証情報がアカウント情報に指定されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    protected boolean _existAuthCheck(Connection con, Cmn250ParamModel paramMdl)
            throws SQLException {

        int authSid = paramMdl.getCmnAuthSid();
        CmnOauthTokenDao authTokenDao = new CmnOauthTokenDao(con);
        return authTokenDao.existAuth(authSid);
    }

    /**
     * <br>[機  能] OAuth認証情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param authSid OAuth認証情報SID
     * @throws SQLException SQL実行時例外
     */
    protected void _deleteOAuth(Connection con, int authSid) throws SQLException {

        //認証情報を削除する
        CmnOauthDao couDao = new CmnOauthDao(con);

        couDao.delete(authSid);
    }
}
