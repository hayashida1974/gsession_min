package jp.groupsession.v2.mbh.interfaces.api.login;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

/**
 * <br>[機  能] モバイル端末個体識別保管処理 Bizクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IMbhUUIDSaveBiz {
    /**
     *
     * <br>[機  能] GSモバイルからアクセス時に個体識別番号の自動登録処理を行う
     * <br>[解  説]
     * <br>[備  考] GSモバイル以外からのアクセス時はなにもしない
     * @param req リクエスト
     * @param con コネクション
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void saveUuidIfAutoSaveFromMBA(
            HttpServletRequest req,
            Connection con,
            int usrSid) throws SQLException;
}
