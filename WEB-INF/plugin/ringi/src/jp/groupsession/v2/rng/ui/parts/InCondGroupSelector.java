package jp.groupsession.v2.rng.ui.parts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class InCondGroupSelector extends AbstractSelector {
    /** 選択済みID */
    private String[] selected__ = new String[0];
    /** 選択先設定*/
    private Map<String, Select> select__;
    /** 含有クラス ユーザ選択機能実装*/
    private UserGroupSelector base__;

    protected InCondGroupSelector(ParamForm param) {
        super(param);
        base__ = UserGroupSelector.builder()
                .chainType(EnumSelectType.GROUP)
                .build();
    }

    /** パラメータ文字列  */
    private String paramName__;

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<InCondGroupSelector, ParamForm> {

        public ParamForm(Class<InCondGroupSelector> targetClz) {
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
        ParamForm ret = new ParamForm(InCondGroupSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
                    throws SQLException {
        return base__.answerSelectedList(
                reqMdl,
                con,
                paramModel,
                selectedSidList);
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<IChild> ret = base__.answerSelectionList(
                reqMdl,
                con,
                paramModel,
                selectedGrpSid,
                selectedSidList);
        return ret;
    }


    @Override
    public Map<String, Select> getSelectMap() {
        return select__;
    }
    @Override
    public Select getSelect(String key) {
        return getSelectMap().get(key);
    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public String[] getSelected() {
        return selected__;
    }
    /**
     * <p>selected をセットします。
     * @param selected selected
     */
    public void setSelected(String[] selected) {
        selected__ = selected;
    }

    /**
     * <p>paramName を取得します。
     * @return paramName
     * @see jp.groupsession.v2.rng.ui.parts.InCondGroupSelector#paramName__
     */
    public String getParamName() {
        return paramName__;
    }

    /**
     * <p>paramName をセットします。
     * @param paramName paramName
     * @see jp.groupsession.v2.rng.ui.parts.InCondGroupSelector#paramName__
     */
    public void setParamName(String paramName) {
        paramName__ = paramName;
        select__ = new LinkedHashMap<String, Select>();
        select__.put(
                ISelector.SELECT_NAME_HEAD + select__.size(),
                Select.builder()
                .chainParameterName(
                        String.format(
                                "%s.selected",
                                paramName__)

                        )
                .build()
                );

    }

    /**
     * <p>selected を取得します。
     * @return selected
     */
    public List<LabelValueBean> getSelectedList() {
        return getSelectMap().values()
            .stream()
            .findAny()
            .stream()
            .flatMap(sel -> sel.getList().stream())
            .map(child -> new LabelValueBean(
                    child.getName(),
                    child.getValue()))
            .collect(Collectors.toList());
    }
    @Override
    public boolean isUseDetailSearch() {
        return base__.isUseDetailSearch();
    }


}
