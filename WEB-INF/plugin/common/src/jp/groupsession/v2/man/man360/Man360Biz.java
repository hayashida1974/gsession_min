package jp.groupsession.v2.man.man360;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutUserDao;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutAdminModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutUserModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MainScreenOfPluginModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 個人設定 メイン画面レイアウト設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man360Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man360Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Man360ParamModel paramMdl, int usrSid)
    throws SQLException {
        log__.debug("START");
        if (paramMdl.getMan360init() == 0) {

            //ユーザレイアウトのデフォルト設定を取得する。
            CmnMainscreenLayoutUserDao userLayoutDao = new CmnMainscreenLayoutUserDao(con);
            CmnMainscreenLayoutUserModel usrlayoutMdl = userLayoutDao.select(usrSid);
            if (usrlayoutMdl != null) {
                paramMdl.setMan360layout(usrlayoutMdl.getMsuDefaultKbn());
            }

            if (paramMdl.getMan360layout() == GSConstMain.MANSCREEN_LAYOUT_CUSTOM) {
                CmnMainscreenLayoutDao layoutDao = new CmnMainscreenLayoutDao(con);
                List<CmnMainscreenLayoutModel> layoutList = layoutDao.select(usrSid);

                String layoutOn = "1";
                String layoutOff = "0";

                String layoutLeft = layoutOff;
                String layoutRight = layoutOff;
                String layoutTop = layoutOff;
                String layoutBottom = layoutOff;
                String layoutCenter = layoutOff;

                if (layoutList != null && layoutList.size() > 0) {
                    for (CmnMainscreenLayoutModel layoutModel : layoutList) {

                        switch (layoutModel.getMscPosition()) {
                            case MainScreenOfPluginModel.POSITION_LEFT :
                                layoutLeft = layoutOn;
                                break;
                            case MainScreenOfPluginModel.POSITION_RIGHT :
                                layoutRight = layoutOn;
                                break;
                            case MainScreenOfPluginModel.POSITION_TOP :
                                layoutTop = layoutOn;
                                break;
                            case MainScreenOfPluginModel.POSITION_BOTTOM :
                                layoutBottom = layoutOn;
                                break;
                            case MainScreenOfPluginModel.POSITION_CENTER :
                                layoutCenter = layoutOn;
                                break;
                            default:
                                break;
                        }
                    }
                }

                paramMdl.setMan360area1(layoutLeft);
                paramMdl.setMan360area2(layoutRight);
                paramMdl.setMan360area3(layoutTop);
                paramMdl.setMan360area4(layoutBottom);
                paramMdl.setMan360area5(layoutCenter);
            }
        }
        log__.debug("END");
    }

    /**
     * <br>[機  能] アクセス権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 権限の有無 true:権限あり false:権限無し
     * @throws SQLException SQL実行例外
     */
    public boolean checkPow(Connection con) throws SQLException {

        CmnMainscreenLayoutAdminDao dao = new CmnMainscreenLayoutAdminDao(con);
        CmnMainscreenLayoutAdminModel model = dao.select();
        if (model == null) {
            return true;

        } else if (model.getMlcAdmLayoutKbn() == GSConstMain.MANSCREEN_LAYOUTKBN_ADMIN) {
            return false;
        }

        return true;
    }
}
