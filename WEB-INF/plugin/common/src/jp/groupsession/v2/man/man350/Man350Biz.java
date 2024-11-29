package jp.groupsession.v2.man.man350;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutDao;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutAdminModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MainScreenOfPluginModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 メイン画面レイアウト設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man350Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man350Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Man350ParamModel paramMdl)
    throws SQLException {
        log__.debug("START");
        if (paramMdl.getMan350init() == 0) {

            //管理者設定を取得する。
            CmnMainscreenLayoutAdminDao layoutAdmDao = new CmnMainscreenLayoutAdminDao(con);
            CmnMainscreenLayoutAdminModel layoutAdmModel = layoutAdmDao.select();
            if (layoutAdmModel != null) {
                //レイアウト設定区分
                paramMdl.setMan350kbn(layoutAdmModel.getMlcAdmLayoutKbn());
                //デフォルト
                paramMdl.setMan350layout(layoutAdmModel.getMlcDefaultKbn());
            }

            if (paramMdl.getMan350layout() == GSConstMain.MANSCREEN_LAYOUT_CUSTOM) {
                CmnMainscreenLayoutDao layoutDao = new CmnMainscreenLayoutDao(con);
                List<CmnMainscreenLayoutModel> layoutList = layoutDao.select(0);

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

                paramMdl.setMan350area1(layoutLeft);
                paramMdl.setMan350area2(layoutRight);
                paramMdl.setMan350area3(layoutTop);
                paramMdl.setMan350area4(layoutBottom);
                paramMdl.setMan350area5(layoutCenter);
            }

        }
        log__.debug("END");
    }

}
