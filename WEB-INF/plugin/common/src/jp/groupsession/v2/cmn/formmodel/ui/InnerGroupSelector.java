package jp.groupsession.v2.cmn.formmodel.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.EnumModeType;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class InnerGroupSelector extends AbstractSelector {


    /** 含有クラス ユーザ選択機能実装*/
    private UserGroupSelector base__;
    /** 追加パラメータ*/
    private ParamForm param__;
    /** 選択先設定*/
    private Map<String, Select> select__;


    protected InnerGroupSelector(ParamForm param) {
        super(param);
        param__ = param;
        base__ = UserGroupSelector.builder()
                .chainType(EnumSelectType.GROUP)
                .build();
    }
    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<InnerGroupSelector, ParamForm> {
        /** 参照元ユーザ選択フォーム */
        private GroupComboModel parent__;
        public ParamForm(Class<InnerGroupSelector> targetClz) {
            super(targetClz);
        }
        /**
         * <p>parent を取得します。
         * @return parent
         * @see jp.groupsession.v2.cmn.formmodel.ui.ParamForm#parent__
         */
        public GroupComboModel getParent() {
            return parent__;
        }
        /**
         * <p>parent をセットします。
         * @param parent parent
         * @see jp.groupsession.v2.cmn.formmodel.ui.ParamForm#parent__
         */
        public void setParent(GroupComboModel parent) {
            parent__ = parent;
        }
        /**
         * <p>parent をセットします。
         * @param groupSelectModel parent
         * @return this
         * @see jp.groupsession.v2.cmn.formmodel.ui.ParamForm#parent__
         */
        public ParamForm chainParent(GroupComboModel groupSelectModel) {
            setParent(groupSelectModel);
            return this;
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
        ParamForm ret = new ParamForm(InnerGroupSelector.class);
        return ret;
    }

    @Override
    public final List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
                    throws SQLException {
        return base__.answerSelectedList(
                reqMdl,
                con,
                paramModel,
                selectedSidList);
    }
    @Override
    public final List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        GroupComboModel parent = param__.getParent();
        List<IChild> ret = base__.answerSelectionList(
                reqMdl,
                con,
                paramModel,
                selectedGrpSid,
                selectedSidList);
        if (parent.getUseSeigen() == UserGroupSelectModel.FLG_SEIGEN_OFF) {
            return ret;
        }


        //選択制限(選択許可)の反映
        if (parent.getSelectable() != null && parent.getSelectable().size() > 0) {
            List<String> selectable = parent.getSelectable();
            ret = ret.stream()
                    .filter(child -> selectable
                                     .contains(
                                        child.getValue()
                                     ) == true
                            )
                    .collect(Collectors.toList());
        }
        return ret;
    }
    @Override
    public Map<String, Select> getSelectMap() {
        if (param__.parent__ == null) {
            return new LinkedHashMap<String, Select>();
        }
        //パラメータ名が初期化されるまで選択マップを生成しない
        if (StringUtil.isNullZeroString(param__.parent__.getParamName())) {
            return new LinkedHashMap<String, Select>();
        }
        if (select__ == null) {
            select__ = new LinkedHashMap<String, Select>();
            select__.put(
                    ISelector.SELECT_NAME_HEAD + select__.size(),
                    Select.builder()
                        .chainParameterName(
                            String.format(
                                    "%s.selected",
                                    param__.parent__.getParamName())

                        )
                        .build()
                    );
        }
        return select__;
    }
    @Override
    public Select getSelect(String key) {
        return getSelectMap().get(key);
    }

    @Override
    public boolean isUseDetailSearch() {
        return base__.isUseDetailSearch();
    }
    @Override
    public void setMode(EnumModeType mode) {
        super.setMode(mode);
        base__.setMode(mode);
    }
    @Override
    public void setModeText(String mode) {
        super.setModeText(mode);
        base__.setModeText(mode);
    }
    @Override
    public void setMultiselectorFilter(String[] multiselectorFilter) {
        super.setMultiselectorFilter(multiselectorFilter);
        base__.setMultiselectorFilter(multiselectorFilter);
    }
}
