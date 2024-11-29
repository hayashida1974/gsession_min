package jp.groupsession.v2.usr.usr033kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.usr031kn.Usr031knBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] メイン 管理者設定 ユーザ一括削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr033knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr033knBiz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Usr033knBiz(
            Connection con,
            RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ユーザの削除処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param uinfList ユーザ情報一覧
     * @param lis ユーザリスナー
     * @return true:削除成功 false:削除失敗
     * @throws SQLException 実行例外
     */
    public boolean executeDel(
            List<CmnUsrmInfModel> uinfList,
            IUserGroupListener[] lis)
                    throws SQLException {

        boolean commitFlg = false;
        try {

            //削除処理
            Usr031knBiz usr031knBiz = new Usr031knBiz(con__, reqMdl__);
            usr031knBiz.delUser(uinfList, lis, reqMdl__);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("ユーザ削除に失敗", e);
            throw e;

        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }

        log__.debug("END");
        return commitFlg;
    }
}
