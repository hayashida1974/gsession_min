package jp.groupsession.v2.usr.usr011;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.IDefaultGroupSelectListner;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumChildType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

public class Usr011GroupAdminSelector
    extends AbstractSelector implements IDefaultGroupSelectListner {
    /** 含有クラス ユーザ選択機能実装*/
    private final UserGroupSelector base__;
    /** ユーザSID */
    private Set<String> userSidSet__;
    /** ユーザSID(直列化済み) */
    private Set<String> userSerializeSidSet__;

    protected Usr011GroupAdminSelector(ParamForm param) {
        super(param);
        base__ = UserGroupSelector.builder()
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainType(EnumSelectType.USER)
                .chainLabel(param.getLabel())
                .chainGroupSelectionParamName(param.getGroupSelectionParamName())
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
    public static class ParamForm
    extends SelectorFactory<Usr011GroupAdminSelector, ParamForm> {

        public ParamForm(Class<Usr011GroupAdminSelector> targetClz) {
            super(targetClz);
        }

    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm builder() {
        ParamForm ret = new ParamForm(Usr011GroupAdminSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        Usr011Form form = paramModel.getInstance(Usr011Form.class);
        Usr011ParamModel paramMdl = new Usr011ParamModel();
        paramMdl.setParam(form);

        Set<String> memberSidList = __getUsers(con, paramMdl);

        //グループメンバー
        Set<String> groupSidList = new HashSet<>();
            groupSidList.addAll(
                memberSidList
                .stream()
                .filter(sid -> sid.startsWith(
                        GSConstUser.MEMBER_GROUP_HEADSTR))
                .map(sid -> sid.substring(
                        GSConstUser.MEMBER_GROUP_HEADSTR.length()))
                .collect(Collectors.toSet())
                    );

        //ユーザーが所属するグループのSIDリストを取得
        CmnBelongmDao cbDao = new CmnBelongmDao(con);
        List<Integer> userBelongList =
                cbDao.selectUserBelongGroupSid(
                        memberSidList.stream()
                        .filter(sid -> sid.startsWith(
                                GSConstUser.MEMBER_GROUP_HEADSTR) == false)
                        .collect(Collectors.toList())
                      );
        groupSidList.addAll(
                    userBelongList.stream()
                            .map(sid -> String.valueOf(sid))
                            .collect(Collectors.toList())
                            );

        //メンバー以外を除く
        List<IChild> groupSelect =
                new ArrayList<>();
        String textUserSitei = gsMsg.getMessage("cmn.user.specified");
        groupSelect.add(
                new jp.groupsession.v2.cmn.ui.parts.select.Child(
                        textUserSitei,
                        GSConstUser.GROUP_COMBO_VALUE_USER));
        groupSelect.addAll(
            base__.answerGroupList(reqMdl, con, paramModel)
            .stream()
            .map(child -> (GroupChild) child)
            .filter(child -> groupSidList.contains(
                    child.getValue()
                        )
                    )
            .map(child -> {
                if (child.getType() == EnumChildType.GROUP) {
                    ((GroupChild) child).getGroup().setClassLevel(0);
                }
                return child;
            })
            .collect(Collectors.toList())
                );
        return groupSelect;
    }
    @Override
    public String answerDefaultGroup(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> groupSidList)
            throws SQLException {
        String ret = base__.answerDefaultGroup(reqMdl, con, paramModel, groupSidList);
        if (groupSidList.contains(ret)) {
            return ret;
        }
        return groupSidList.get(0);
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        Usr011Form form = paramModel.getInstance(Usr011Form.class);
        Usr011ParamModel paramMdl = new Usr011ParamModel();
        paramMdl.setParam(form);
        Set<String> memberSidList = __getUsers(con, paramMdl);

        if (Objects.equals(selectedGrpSid,
                GSConstUser.GROUP_COMBO_VALUE_USER
                )) {
            //メンバーユーザから管理者に選択しているユーザを除いてリストを作成
            Set<String> selectedSet = selectedSidList
                                               .stream()
                                               .collect(Collectors.toSet());
            List<Integer> userList =
                    memberSidList.stream()
                    .filter(sid -> selectedSet.contains(sid) == false)
                    .filter(sid -> sid.startsWith(
                            GSConstUser.MEMBER_GROUP_HEADSTR) == false)
                    .map(sid -> Integer.valueOf(sid))
                    .collect(Collectors.toList());

            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap = new LinkedHashMap<>();

            new CmnUsrmInfDao(con)
            .getUsersInfList(userList
                    .stream()
                    .map(sid -> Integer.toString(sid))
                    .toArray(String[]::new),
                    sortMdl)
            .stream()
            .forEach(u -> usrmInfMap.put(u.getUsrSid(), u));

            //ユーザラベル情報
            final Map<Integer, CmnUsrmModel> usrmMap = new CmnUsrmDao(con)
                    .select(usrmInfMap.keySet())
                    .stream()
                    .collect(Collectors.toMap(
                            u -> u.getUsrSid(),
                            u -> u
                            ));
            CmnLabelUsrCategoryDao labelCategoryDao = new CmnLabelUsrCategoryDao(con);
            final Map<Integer, CmnLabelUsrCategoryModel> labelCategoryMap =
                    labelCategoryDao.select()
                    .stream()
                    .collect(Collectors.toMap(
                        l -> l.getLucSid(),
                        l -> l
                            ));
            CmnLabelUsrDao labelDao = new CmnLabelUsrDao(con);
            final List<CmnLabelUsrModel> labelList = new ArrayList<>();
                labelDao.select()
                    .stream()
                    .sorted((l1, l2) -> {
                        CmnLabelUsrCategoryModel c1, c2;
                        c1 = labelCategoryMap.get(l1.getLucSid());
                        c2 = labelCategoryMap.get(l2.getLucSid());
                        if (c1 != c2) {
                           return c1.getLucSort() - c2.getLucSort();
                        }
                        return l1.getLabSort() - l2.getLabSort();
                    })
                    .forEach(l -> labelList.add(l));
            CmnUsrmLabelDao usrlabelDao = new CmnUsrmLabelDao(con);
            final Map<Integer, Set<Integer>> belongMap =
                    usrlabelDao.getLabListBelongUsrMap(new HashSet<>(userList));

            List<IChild> selectionList = new ArrayList<>();
            selectionList.addAll(
                usrmInfMap.values()
                    .stream()
                    .map(usrInf -> new CmnUserModel(usrInf, usrmMap.get(usrInf.getUsrSid())))
                    .map(usr -> new Child(usr))
                    //ラベル設定
                    .map(child -> {
                        if (!belongMap.containsKey(child.getUser().getUsrSid())) {
                            return child;
                        }
                        Set<Integer> lblSids = belongMap.get(child.getUser().getUsrSid());
                        Iterator<CmnLabelUsrModel> it = labelList.stream()
                            .filter(l -> lblSids.contains(l.getLabSid()))
                            .iterator();

                        while (it.hasNext()) {
                            child.getLabelList().add(it.next().getLabName());
                        }
                        return child;
                    })
                    .collect(Collectors.toList())
                    );
            return selectionList;
        } else  {
            //メンバー
            Set<String> memberSeriarizeSidList =
                    __getSerializeMembers(con, paramMdl);
            return
                    base__.answerSelectionList(
                            reqMdl, con, paramModel, selectedGrpSid, selectedSidList)
                        .stream()
                        .map(child -> (Child) child)
                        .filter(child -> {
                            if (memberSidList.contains(child.getValue())) {
                                return true;
                            }
                            if (memberSeriarizeSidList.contains(child.getValue())) {
                                return true;
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
        }
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        Usr011Form form = paramModel.getInstance(Usr011Form.class);
        Usr011ParamModel paramMdl = new Usr011ParamModel();
        paramMdl.setParam(form);

        //メンバー
        Set<String> memberSeriarizeSidList =
                __getSerializeMembers(con, paramMdl);


        return base__.answerSelectedList(reqMdl, con, paramModel, selectedSidList)
                .stream()
                .map(child -> (Child) child)
                .filter(child -> {
                    if (memberSeriarizeSidList.contains(child.getValue())) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
    /**
     * <br>[機  能] メンバーを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @return メンバーSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private Set<String> __getUsers(
            Connection con, Usr011ParamModel paramMdl)
                    throws SQLException {

        List<String> selectMemberList = new ArrayList<>();
        selectMemberList.addAll(List.of(paramMdl.getUsers_l()));
        userSidSet__ = selectMemberList.stream()
                            .collect(Collectors.toSet());

        return userSidSet__;
    }
    /**
     * <br>[機  能] メンバーを返します
     * <br>[解  説] グループ指定をユーザで直列化して返します
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @return メンバーSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private Set<String> __getSerializeMembers(
            Connection con, Usr011ParamModel paramMdl)
                    throws SQLException {

        if (userSerializeSidSet__ != null) {
            return userSerializeSidSet__;
        }
        Set<String> selectMemberSet = __getUsers(con, paramMdl);
        userSerializeSidSet__ = new HashSet<>();
        //ユーザ
        userSerializeSidSet__.addAll(
                selectMemberSet.stream()
                .filter(sid -> !sid.startsWith(GSConstUser.MEMBER_GROUP_HEADSTR))
                .collect(Collectors.toSet())
                );
        //グループ指定されたユーザ
        CmnBelongmDao cbDao = new CmnBelongmDao(con);
        String[] gsid = selectMemberSet.stream()
                            .filter(sid -> sid.startsWith(
                                    GSConstUser.MEMBER_GROUP_HEADSTR))
                            .map(sid -> sid.substring(
                                    GSConstUser.MEMBER_GROUP_HEADSTR.length()))
                            .toArray(String[]::new);
        if (gsid.length > 0) {

            userSerializeSidSet__.addAll(
                    cbDao.select(gsid)
                    );
        }
        return userSerializeSidSet__;
    }

}
