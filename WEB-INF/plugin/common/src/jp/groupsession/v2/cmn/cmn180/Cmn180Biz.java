package jp.groupsession.v2.cmn.cmn180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.ThemePathBiz;
import jp.groupsession.v2.cmn.dao.base.CmnMdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrThemeDao;
import jp.groupsession.v2.cmn.dao.base.CmnWeatherAreaDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;
import jp.groupsession.v2.cmn.model.base.CmnWeatherAreaModel;

/**
 * <br>[機  能] 天気予報(メイン)のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn180Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn180Model パラメータ格納モデル
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cmn180ParamModel cmn180Model,
            Connection con, int userSid, RequestModel reqMdl) throws SQLException {

        String serverUrl = "http://" + GSConst.BIZ_URL + "/weather/";
        String bizUrl = CommonBiz.getBizUrl(reqMdl);
        if (bizUrl != null) {
            serverUrl = bizUrl + "weather/";
        }

        cmn180Model.setCmn180Url(serverUrl + "gadget/");
        cmn180Model.setCmn180WeekUrl(serverUrl + "area/");
        cmn180Model.setCmn180Date((new UDate()).getDateString());

        CmnMdispModel dispMdl = new CmnMdispModel();
        dispMdl.setUsrSid(userSid);
        dispMdl.setMdpPid(GSConstCommon.MAIN_DSPVALUE_WEATHER);
        CmnMdispDao dispDao = new CmnMdispDao(con);
        dispMdl = dispDao.select(dispMdl);

        List<Cmn180AreaModel> areaList = null;
        if (dispMdl != null) {

            if (dispMdl.getMdpDsp() == GSConstCommon.MAIN_DSP) {
                Cmn180Dao dao180 = new Cmn180Dao(con);
                areaList = dao180.getAreaList(userSid);

                if (areaList.isEmpty()) {
                    areaList = __getInitAreaList(con);
                }
            }

        } else {
            areaList = __getInitAreaList(con);
        }

        cmn180Model.setAreaList(areaList);

        // テーマ反映用パラメータの取得
        int ctmSid = 0;
        CmnUsrThemeDao cmnDao = new CmnUsrThemeDao(con);
        ctmSid = cmnDao.getThemeSid(userSid);
        if (ctmSid == 0) {
            ctmSid = ThemePathBiz.DEFAULT_CTM_SID;
        }
        cmn180Model.setCmn180themeSid(ctmSid);
    }

    /**
     * <br>[機  能] 地域情報一覧の初期表示情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return  地域情報一覧の初期表示情報
     * @throws SQLException SQL実行時例外
     */
    private List<Cmn180AreaModel> __getInitAreaList(Connection con)
    throws SQLException {
        List<Cmn180AreaModel> areaList = new ArrayList<Cmn180AreaModel>();

        CmnWeatherAreaDao weatherDao = new CmnWeatherAreaDao(con);
        CmnWeatherAreaModel weatherModel
            = weatherDao.select(Integer.parseInt(GSConstCommon.MAIN_DSP_WEATHER_INIT_AREA));

        if (weatherModel != null) {
            Cmn180AreaModel areaMdl = new Cmn180AreaModel();
            areaMdl.setAreaId(weatherModel.getCwaSid());
            areaMdl.setAreaName(weatherModel.getCwaName());
            areaList.add(areaMdl);
        }

        return areaList;
    }
}