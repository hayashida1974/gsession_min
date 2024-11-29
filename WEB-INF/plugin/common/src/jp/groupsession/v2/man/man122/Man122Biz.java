package jp.groupsession.v2.man.man122;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー(メニュー表示設定)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man122Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man122Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Man122ParamModel paramMdl, Connection con)
    throws SQLException  {

        //コントロールマスタを取得
        CmnContmDao contDao = new CmnContmDao(con);
        CmnContmModel contMdl = contDao.select();
        int menuEdit = GSConstMain.MENU_STATIC_NOT_USE;
        if (contMdl != null) {
            menuEdit = contMdl.getCntMenuStatic();
        }

        paramMdl.setMenuEditOk(String.valueOf(menuEdit));
    }

    /**
     * <br>[機  能] 管理者用トップ表示設定の登録を行う
     * <br>[解  説]
     * <br>[備  考] プラグインの追加/削除を考えdelete insertで登録を行う
     *
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void updateCtrmst(Connection con, Man122ParamModel paramMdl)
    throws SQLException {

        //コントロールマスタを更新
        CmnContmDao cntDao = new CmnContmDao(con);
        int kbn = GSConstMain.MENU_STATIC_NOT_USE;
        if (paramMdl.getMenuEditOk().equals(String.valueOf(GSConstMain.MENU_STATIC_NOT_USE))) {
            kbn = GSConstMain.MENU_STATIC_USE;
        }

        boolean commit = false;
        try {

            cntDao.updateMenuStatic(kbn);
            commit = true;
        } catch (SQLException e) {
            log__.error("メニュー表示設定の登録", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            } else {
                con.commit();
            }
        }
    }
}
