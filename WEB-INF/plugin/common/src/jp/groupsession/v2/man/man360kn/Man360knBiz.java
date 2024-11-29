package jp.groupsession.v2.man.man360kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutUserDao;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutUserModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MainScreenOfPluginModel;

/**
 * <br>[機  能] メイン 個人設定 メイン画面レイアウト設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man360knBiz {

    /**
     * <br>[機  能] レイアウト情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateData(Man360knParamModel paramMdl,
                            Connection con,
                            int userSid)
    throws SQLException {

        UDate now = new UDate();
        int defaultKbn = paramMdl.getMan360layout();

        //レイアウト個人設定を登録する。
        CmnMainscreenLayoutUserDao layoutUsrDao = new CmnMainscreenLayoutUserDao(con);
        CmnMainscreenLayoutDao layoutDao = new CmnMainscreenLayoutDao(con);

        CmnMainscreenLayoutUserModel layoutUsrModel = new CmnMainscreenLayoutUserModel();

        layoutUsrModel.setUsrSid(userSid);
        layoutUsrModel.setMsuDefaultKbn(defaultKbn);
        layoutUsrModel.setMsuAdate(now);
        layoutUsrModel.setMsuAuid(userSid);
        layoutUsrModel.setMsuEdate(now);
        layoutUsrModel.setMsuEuid(userSid);

        int count = layoutUsrDao.update(layoutUsrModel);
        if (count < 1) {
            layoutUsrDao.insert(layoutUsrModel);
        }

        //レイアウト情報を削除する。
        layoutDao.delete(userSid);

        if (defaultKbn == GSConstMain.MANSCREEN_LAYOUT_CUSTOM) {

            CmnMainscreenLayoutModel layoutModel = new CmnMainscreenLayoutModel();
            layoutModel.setUsrSid(userSid);
            layoutModel.setMslAuid(userSid);
            layoutModel.setMslAdate(now);
            layoutModel.setMslEuid(userSid);
            layoutModel.setMslEdate(now);

            String layoutOn = "1";

            if (paramMdl.getMan360area1().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_LEFT);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan360area2().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_RIGHT);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan360area3().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_TOP);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan360area4().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_BOTTOM);
                layoutDao.insert(layoutModel);
            }

            if (paramMdl.getMan360area5().equals(layoutOn)) {
                layoutModel.setMscPosition(MainScreenOfPluginModel.POSITION_CENTER);
                layoutDao.insert(layoutModel);
            }

        }
    }

}
