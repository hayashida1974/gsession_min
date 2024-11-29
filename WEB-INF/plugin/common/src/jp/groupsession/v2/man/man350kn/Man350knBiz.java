package jp.groupsession.v2.man.man350kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutDao;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutAdminModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MainScreenOfPluginModel;

/**
 * <br>[機  能] メイン 管理者設定 メイン画面レイアウト設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man350knBiz {

    /**
     * <br>[機  能] レイアウト情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateData(Man350knParamModel paramMdl,
                            Connection con,
                            int userSid)
    throws SQLException {

        UDate now = new UDate();
        int layoutKbn = paramMdl.getMan350kbn();
        int defaultKbn = paramMdl.getMan350layout();

        //レイアウト管理者設定を登録する。
        CmnMainscreenLayoutAdminDao layoutAdmDao = new CmnMainscreenLayoutAdminDao(con);
        CmnMainscreenLayoutDao layoutDao = new CmnMainscreenLayoutDao(con);

        CmnMainscreenLayoutAdminModel layoutAdmModel = new CmnMainscreenLayoutAdminModel();

        layoutAdmModel.setMlcAdmLayoutKbn(layoutKbn);
        layoutAdmModel.setMlcDefaultKbn(defaultKbn);
        layoutAdmModel.setMlcAdate(now);
        layoutAdmModel.setMlcAuid(userSid);
        layoutAdmModel.setMlcEdate(now);
        layoutAdmModel.setMlcEuid(userSid);

        int count = layoutAdmDao.update(layoutAdmModel);
        if (count < 1) {
            layoutAdmDao.insert(layoutAdmModel);
        }

        //レイアウト情報を削除する。
        layoutDao.delete(0);

        if (layoutKbn == GSConstMain.MANSCREEN_LAYOUTKBN_ADMIN
                && defaultKbn == GSConstMain.MANSCREEN_LAYOUT_CUSTOM) {

            CmnMainscreenLayoutModel layoutModel = new CmnMainscreenLayoutModel();
            layoutModel.setUsrSid(0);
            layoutModel.setMslAuid(userSid);
            layoutModel.setMslAdate(now);
            layoutModel.setMslEuid(userSid);
            layoutModel.setMslEdate(now);

            String layoutOn = "1";

            if (paramMdl.getMan350area1().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_LEFT);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan350area2().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_RIGHT);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan350area3().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_TOP);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan350area4().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_BOTTOM);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan350area5().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_CENTER);
                layoutDao.insert(layoutModel);
            }

        }
    }

}
