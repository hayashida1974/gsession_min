package jp.groupsession.v2.cmn.cmn150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnWeatherAreaDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnWeatherAreaModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

public class Cmn150WeatherSelector extends AbstractSelector {


    protected Cmn150WeatherSelector(ParamForm param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<Cmn150WeatherSelector, ParamForm> {

        public ParamForm(Class<Cmn150WeatherSelector> targetClz) {
            super(targetClz);
        }

    }
    /**
     *
     * <br>[機  能] ビルダークラスの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダークラス
     */
    public static ParamForm builder() {
        ParamForm ret = new ParamForm(Cmn150WeatherSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        return __getWeatherList(selectedSidList, true, reqMdl, con);
    }
    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        return __getWeatherList(selectedSidList, false, reqMdl, con);
    }
    /**
     * <p>pluginIdList を取得します。
     * @param selectedSidList
     * @param selected
     * @param reqMdl
     * @param con
     * @return pluginIdList
     * @throws SQLException
     * @see jp.groupsession.v2.cmn.cmn140.MenuSelectorFilter#pluginIdList__
     */
    private List<IChild> __getWeatherList(
            List<String> selectedSidList,
            boolean selected,
            RequestModel reqMdl,
            Connection con) throws SQLException {

        List <IChild> selectAreaCombo = new ArrayList<IChild>();
        List <IChild> noSelectAreaCombo = new ArrayList<IChild>();

        CmnWeatherAreaDao weatherAreaDao = new CmnWeatherAreaDao(con);
        List<CmnWeatherAreaModel> areaList = weatherAreaDao.select();

        if (selectedSidList != null && selectedSidList.size() > 0) {
            for (String selectArea : selectedSidList) {
                int selectAreaIndex = 0;
                int selectAreaSid = Integer.parseInt(selectArea);

                for (CmnWeatherAreaModel areaData : areaList) {
                    if (areaData.getCwaSid() == selectAreaSid) {
                        break;
                    }
                    selectAreaIndex++;
                }

                if (selectAreaIndex < areaList.size()) {
                    selectAreaCombo.add(new Child(areaList.get(selectAreaIndex).getCwaName(),
                                        String.valueOf(areaList.get(selectAreaIndex).getCwaSid())));
                    areaList.remove(selectAreaIndex);
                }
            }
        }
        if (selected) {
            return selectAreaCombo;
        } else {
            for (CmnWeatherAreaModel areaData : areaList) {
                noSelectAreaCombo.add(new Child(areaData.getCwaName(),
                        String.valueOf(areaData.getCwaSid())));
            }
            return noSelectAreaCombo;
        }
    }
}
