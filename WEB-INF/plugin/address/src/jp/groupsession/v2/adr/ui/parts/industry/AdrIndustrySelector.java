package jp.groupsession.v2.adr.ui.parts.industry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

public class AdrIndustrySelector extends AbstractSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected AdrIndustrySelector(ParamForm param) {
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
    public static class ParamForm extends SelectorFactory<AdrIndustrySelector, ParamForm> {
        public ParamForm(Class<AdrIndustrySelector> targetClz) {
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
        ParamForm ret = new ParamForm(AdrIndustrySelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
           ParameterObject paramModel, List<String> selectedSidList) throws SQLException {

        //業種一覧を取得
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        List<AdrTypeindustryModel> industryList = industryDao.select();

        List<IChild> ret = new ArrayList<>();
        ret.addAll(industryList
                    .stream()
                    .filter(mdl -> selectedSidList.indexOf(String.valueOf(mdl.getAtiSid())) >= 0)
                    .map(mdl -> __createIndustryChild(mdl))
                    .collect(Collectors.toList())
               );
        return ret;
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {

        //業種一覧を取得
        AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
        List<AdrTypeindustryModel> industryList = industryDao.select();

        List<IChild> ret = new ArrayList<>();
        ret.addAll(industryList
                    .stream()
                    .filter(mdl -> selectedSidList.indexOf(String.valueOf(mdl.getAtiSid())) < 0)
                    .map(mdl -> __createIndustryChild(mdl))
                    .collect(Collectors.toList())
                );
        return ret;
    }

    /**
     * <br>[機  能] 業種情報から選択UIの子要素を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl 業種情報
     * @return 選択UIの子要素
     */
    private Child __createIndustryChild(AdrTypeindustryModel mdl) {
        return new Child(
                mdl.getAtiName(),
                String.valueOf(mdl.getAtiSid())
                );
    }
}
