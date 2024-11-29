package jp.groupsession.v2.cmn.formmodel.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.EnumModeType;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumChildType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class InnerUserSelector extends AbstractSelector {

    /** 含有クラス ユーザ選択機能実装*/
    private UserGroupSelector baseUser__;
    /** 追加パラメータ*/
    private ParamForm param__;
    /** 選択先設定*/
    private Map<String, Select> select__;
    /** グループ選択フラグ*/
    private boolean withGroup__ = false;

    /** 選択ターゲットキー*/
    private String[] selectKeys__;

    protected InnerUserSelector(ParamForm param) {
        super(param);
        param__ = param;
        baseUser__ = UserGroupSelector.builder()
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainType(EnumSelectType.USERGROUP)
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
    public static class ParamForm extends SelectorFactory<InnerUserSelector, ParamForm> {
        /** 参照元ユーザ選択フォーム */
        private UserGroupSelectModel parent__;
        public ParamForm(Class<InnerUserSelector> targetClz) {
            super(targetClz);
        }
        /**
         * <p>parent を取得します。
         * @return parent
         * @see jp.groupsession.v2.cmn.formmodel.ui.ParamForm#parent__
         */
        public UserGroupSelectModel getParent() {
            return parent__;
        }
        /**
         * <p>parent をセットします。
         * @param parent parent
         * @see jp.groupsession.v2.cmn.formmodel.ui.ParamForm#parent__
         */
        public void setParent(UserGroupSelectModel parent) {
            parent__ = parent;
        }
        /**
         * <p>parent をセットします。
         * @param userGroupSelectModel parent
         * @return this
         * @see jp.groupsession.v2.cmn.formmodel.ui.ParamForm#parent__
         */
        public ParamForm chainParent(UserGroupSelectModel userGroupSelectModel) {
            setParent(userGroupSelectModel);
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
        ParamForm ret = new ParamForm(InnerUserSelector.class);
        return ret;
    }
    @Override
    public boolean isUseDetailSearch() {
        return baseUser__.isUseDetailSearch();
    }
    @Override
    public void setMode(EnumModeType mode) {
        super.setMode(mode);
        baseUser__.setMode(mode);
    }
    @Override
    public void setSelectGroupSid(String selectGroupSid) {
        super.setSelectGroupSid(selectGroupSid);
        baseUser__.setSelectGroupSid(selectGroupSid);
    }
    @Override
    public void setSelectTargetKey(String selectTargetKey) {
        super.setSelectTargetKey(selectTargetKey);
        baseUser__.setSelectTargetKey(selectTargetKey);
    }
    @Override
    public void setModeText(String mode) {
        super.setModeText(mode);
        baseUser__.setModeText(mode);
    }
    @Override
    public void setMultiselectorFilter(String[] multiselectorFilter) {
        super.setMultiselectorFilter(multiselectorFilter);
        baseUser__.setMultiselectorFilter(multiselectorFilter);
    }

    @Override
    public final List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
                    throws SQLException {
        return baseUser__.answerSelectedList(
                reqMdl,
                con,
                paramModel,
                selectedSidList);
    }
    @Override
    public String answerDefaultGroup(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> groupSidList)
            throws SQLException {
        return baseUser__.answerDefaultGroup(reqMdl, con, paramModel, groupSidList);
    }
    @Override
    public final List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        UserGroupSelectModel parent = param__.getParent();
        List<IChild> ret = baseUser__.answerSelectionList(
                reqMdl,
                con,
                paramModel,
                selectedGrpSid,
                selectedSidList);
        if (withGroup__ == false) {
            ret = ret.stream()
                    .filter(child -> child.getType() == EnumChildType.USER)
                    .collect(Collectors.toList());
        }

        //選択制限(選択不可)の反映
        if (parent.getBanUsrSid() != null && parent.getBanUsrSid().length > 0) {
            List<String> banSid = List.of(parent.getBanUsrSid());
            ret = ret.stream()
                    .filter(child -> banSid
                                     .contains(
                                        child.getValue()
                                     ) == false
                            )
                    .collect(Collectors.toList());
        }
        if (parent.getUseSeigen() == UserGroupSelectModel.FLG_SEIGEN_OFF) {
            return ret;
        }


        //選択制限(選択許可)の反映
        if (parent.getSelectable() != null && parent.getSelectable().length > 0) {
            List<String> selectable = List.of(parent.getSelectable())
                                            .stream()
                                            .filter(sid -> sid.startsWith("G") == false
                                                      && ValidateUtil.isNumberHaifun(sid))
                                            .collect(Collectors.toList());
            if (selectable.size() == 0) {
                return ret;
            }
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
    public final List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {
        UserGroupSelectModel parent = param__.getParent();
        List<IChild> ret = baseUser__.answerGroupList(reqMdl, con, paramModel);
        //選択制限(選択不可)の反映
        if (parent.getBanUsrSid() != null && parent.getBanUsrSid().length > 0) {
            List<String> banSid = List.of(parent.getBanUsrSid());
            ret = ret.stream()
                    .filter(child -> banSid
                                     .contains(
                                        child.getValue()
                                     ) == false
                            )
                    .collect(Collectors.toList());
        }

        if (parent.getUseSeigen() == UserGroupSelectModel.FLG_SEIGEN_OFF) {
            return ret;
        }
        Set<String> usrSelectableSid = new HashSet<String>();
        Set<String> grpSelectableSid = new HashSet<String>();
        if (parent.getSelectable() != null && parent.getSelectable().length > 0) {
            List<String> selectable = List.of(parent.getSelectable());
            for (String sid : selectable) {
                if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                    grpSelectableSid.add(
                            sid.substring(
                                    UserGroupSelectBiz.GROUP_PREFIX.length()));
                } else {
                    usrSelectableSid.add(String.valueOf(
                            NullDefault.getInt(sid, -1)
                            ));
                }
            }
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            ArrayList<Integer> grpSids = belongDao.selectUserBelongGroupSid(
                    new ArrayList<>(usrSelectableSid));

            grpSelectableSid.addAll(
                    grpSids.stream()
                        .map(sid -> String.valueOf(sid))
                        .collect(Collectors.toSet())
                    );
            int check = ret.size();
            ret = ret.stream()
                    .filter(child -> grpSelectableSid.contains(
                                        child.getValue()
                                    ) == true
                            )
                    .collect(Collectors.toList());

            //除外後にグループ一覧が全グループと数が異なる場合にインデントを除去
            if (check != ret.size()) {
                ret = ret.stream()
                        .map(child -> {
                            if (child.getType() == EnumChildType.GROUP) {
                                ((GroupChild) child).getGroup().setClassLevel(0);
                            }
                            return child;
                        })
                        .collect(Collectors.toList());
            }

        }
        return ret;
    }
    @Override
    public Map<String, Select> getSelectMap() {
        if (param__.parent__ == null) {
            return new LinkedHashMap<String, Select>();
        }
        //parentのdspInit内でパラメータ名が初期化されるまで選択マップを生成しない
        if (param__.parent__.getParamName() == null) {
            return new LinkedHashMap<String, Select>();
        }
        if (select__ == null) {
            select__ = new LinkedHashMap<String, Select>();
            for (Entry<String, String[]> entry : param__.parent__.getSelected().entrySet()) {
                String key = entry.getKey();
                select__.put(
                        ISelector.SELECT_NAME_HEAD + select__.size(),
                        Select.builder()
                            .chainParameterName(
                                String.format(
                                        "%s.selected(%s)",
                                        param__.parent__.getParamName(),
                                        key)

                            )
                            .build()
                );
            }
            if (param__.parent__.getSelected().size() == 0) {
                select__.put(
                        ISelector.SELECT_NAME_HEAD + select__.size(),
                        Select.builder()
                            .chainParameterName(
                                String.format(
                                        "%s.selected(%s)",
                                        param__.parent__.getParamName(),
                                        UserGroupSelectModel.KEY_DEFAULT)

                            )
                            .build()
                );

            }
        }
        return select__;
    }
    @Override
    public Select getSelect(String key) {
        return getSelectMap().get(key);
    }
    @Override
    public String getGroupSelectionParamName() {
        if (param__.parent__ == null) {
            return null;
        }
        //parentのdspInit内でパラメータ名が初期化されるまでグループ選択パラメータ名を設定しない
        if (param__.parent__.getParamName() == null) {
            return null;
        }
        return String.format(
                "%s.group.selectedSingle",
                param__.parent__.getParamName()
                );
    }

    /**
     * <p>withGroup を取得します。
     * @return withGroup
     * @see jp.groupsession.v2.cmn.formmodel.ui.InnerUserSelector#withGroup__
     */
    public boolean isWithGroup() {
        return withGroup__;
    }
    /**
     * <p>withGroup をセットします。
     * @param withGroup withGroup
     * @see jp.groupsession.v2.cmn.formmodel.ui.InnerUserSelector#withGroup__
     */
    public void setWithGroup(boolean withGroup) {
        withGroup__ = withGroup;
    }
    /**
     * <p>selectKeys を取得します。
     * @return selectKeys
     * @see jp.groupsession.v2.cmn.formmodel.ui.InnerUserSelector#selectKeys__
     */
    public String[] getSelectKeys() {
        return selectKeys__;
    }
    /**
     * <p>selectKeys をセットします。
     * @param selectKeys selectKeys
     * @see jp.groupsession.v2.cmn.formmodel.ui.InnerUserSelector#selectKeys__
     */
    public void setSelectKeys(String[] selectKeys) {
        selectKeys__ = selectKeys;
    }



}
