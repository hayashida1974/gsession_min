package jp.groupsession.v2.man.man210kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 モバイル使用一括設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man210knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man210knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Man210knParamModel paramMdl, Connection con) throws SQLException {
        //メンバー取得
        UserBiz userBiz = new UserBiz();
        paramMdl.setMan210knMemberList(
                    userBiz.getUserList(con, paramMdl.getMan210userSid()));
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(Man210knParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        boolean commit = false;
        try {
            con.setAutoCommit(false);

            //システム日付
            UDate now = new UDate();
            //登録用Model作成
            CmnUsrmInfModel mdl = new CmnUsrmInfModel();

            mdl.setUsiEuid(userSid);
            mdl.setUsiEdate(now);
            mdl.setUsiMblUse(paramMdl.getMan210UseKbn());
            mdl.setUsiNumAutadd(Integer.parseInt(paramMdl.getMan210NumAutAdd()));
            mdl.setUsiNumCont(Integer.parseInt(paramMdl.getMan210NumCont()));

            CmnUsrmInfDao dao = new CmnUsrmInfDao(con);

            if (paramMdl.getMan210ObjKbn() == 0) {
                // 対象 全ユーザ
                dao.updateMblKbn(null, mdl);
            } else {
                // 対象 指定ユーザ
                dao.updateMblKbn(paramMdl.getMan210userSid(), mdl);
            }
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.warn("マイグループ更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }
    }

}
