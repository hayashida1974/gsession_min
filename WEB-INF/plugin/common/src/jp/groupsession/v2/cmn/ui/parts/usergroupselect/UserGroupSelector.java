package jp.groupsession.v2.cmn.ui.parts.usergroupselect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupMsModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.exception.UIValidateException;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.EnumModeType;
import jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitFilterChain;
import jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitModel;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.cmn.ui.parts.select.IValidateFilter;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.select.ValidateFilterChain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能] ユーザ選択UIクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserGroupSelector extends AbstractSelector implements IValidateFilter {

    public static class ParamForm<T extends UserGroupSelector>
        extends SelectorFactory<T, ParamForm<T>> {

        public ParamForm(Class<T> targetClz) {
            super(targetClz);
        }

        /** 選択タイプ*/
        private EnumSelectType type__ = EnumSelectType.USER;
        /** グループ選択タイプ*/
        private EnumGroupSelectType grpType__ = EnumGroupSelectType.GROUPONLY;

        /** プラグイン利用者制限設定 */
        private String onlyPluginUser__;
        /**
         * <p>type を取得します。
         * @return type
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector.ParamForm#type__
         */
        public EnumSelectType getType() {
            return type__;
        }
        /**
         * <p>type をセットします。
         * @param type type
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector.ParamForm#type__
         */
        public void setType(EnumSelectType type) {
            type__ = type;
        }
        /**
         * <p>type をセットします。
         * @param type type
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector.ParamForm#type__
         */
        public void setTypeText(String type) {
            setType(EnumSelectType.valueOf(type));
        }
        /**
         * <p>type をセットします。
         * <p>必須パラメータです
         * @param type type
         * @return this
         * @see type__
         */
        public ParamForm<T> chainType(EnumSelectType type) {
            type__ = type;
            return this;
        }
        /**
         * <p>onlyPluginUser を取得します。
         * @return onlyPluginUser
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.SelectorParamForm#onlyPluginUser__
         */
        public String getOnlyPluginUser() {
            return onlyPluginUser__;
        }
        /**
         * <p>onlyPluginUser をセットします。
         * @param onlyPluginUser onlyPluginUser
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.SelectorParamForm#onlyPluginUser__
         */
        public void setOnlyPluginUser(String onlyPluginUser) {
            onlyPluginUser__ = onlyPluginUser;
        }
        /**
         * <p>onlyPluginUser をセットします。
         * @param onlyPluginUser onlyPluginUser
         * @return this
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.SelectorParamForm#onlyPluginUser__
         */
        public ParamForm<T> chainOnlyPluginUser(String onlyPluginUser) {
            setOnlyPluginUser(onlyPluginUser);
            return this;
        }
        /**
         * <p>grpType を取得します。
         * @return grpType
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.ParamForm#grpType__
         */
        public EnumGroupSelectType getGrpType() {
            return grpType__;
        }
        /**
         * <p>grpType をセットします。
         * @param grpType grpType
         * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.ParamForm#grpType__
         */
        public void setGrpType(EnumGroupSelectType grpType) {
            grpType__ = grpType;
        }
        /**
         * <p>type をセットします。
         * <p>必須パラメータです
         * @param grpType grpType
         * @return this
         * @see type__
         */
        public ParamForm<T> chainGrpType(EnumGroupSelectType grpType) {
            setGrpType(grpType);
            return this;
        }

    }
    /** パラメータ*/
    private final ParamForm<? extends UserGroupSelector> param__;

    /** グループ一覧キャッシュ*/
    List<IChild> dspGroupListCash__;


    /**
     *
     * コンストラクタ
     * @param param
     */
    protected UserGroupSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
        if (param.getLabel() == null) {
            switch (param.getType()) {
            case GROUP:
                param.setLabel(new GsMessageReq("cmn.form.group", null));
                break;
            case USER:
            case USERGROUP:
            default:
                param.setLabel(new GsMessageReq("cmn.form.user", null));
                break;
            }
        }
        param.chainValidateFilter(this);
        param__ = param;

    }

    /**
     * <p>type を取得します。
     * <p>必須パラメータです
     * @return type
     * @see ParamForm#type__
     */
    public EnumSelectType getType() {
        return param__.getType();
    }
    /**
     * <p>grpType を取得します。
     * @return grpType
     * @see ParamForm#grpType__
     */
    public EnumGroupSelectType getGrpType() {
        return param__.grpType__;
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<? extends UserGroupSelector> builder() {
        ParamForm<UserGroupSelector> ret = new ParamForm<>(UserGroupSelector.class);
        return ret;
    }
   /**
    *
    * <br>[機  能] ユーザ選択部 生成処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param paramModel
    * @param reqMdl
    * @param con
    * @param mode
    * @param selectGrp 選択済みグループ
    * @param selected 選択済み
    * @return ユーザ選択元リスト
    * @throws SQLException
    */
   private List<IChild> __createUserSelection(ParameterObject paramModel,
           RequestModel reqMdl, Connection con, EnumModeType mode,
           String selectGrp,
           List<String> selected) throws SQLException {
       List<IChild> selection = new ArrayList<>();
       List<IChild> groupList = answerGroupList(reqMdl, con, paramModel);

       final Set<String> selectedUserSet = new HashSet<>();
       final Set<String> selectedGroupSet = new HashSet<>();
       for (String sid : selected) {
           if (ValidateUtil.isNumberHaifun(sid)) {
               selectedUserSet.add(sid);
           } else if (sid.startsWith("G")) {
               selectedGroupSet.add(sid);
           }
       }
       boolean userFilterFlg = false;
       String[] filterList = getMultiselectorFilter();
       if (getMode() == EnumModeType.EDIT_POPUP && filterList != null) {
           for (String filter : filterList) {
               String checkFilter = Optional.ofNullable(filter)
                                       .filter(fil -> fil.length() >= 5)
                                       .map(fil -> fil.substring(0, 5))
                                       .orElse("");

               if (checkFilter.contains("user")) {
                   userFilterFlg = true;
                   break;
               }
           }
       }

       try {

           List<Integer> usrSidList = new ArrayList<>();
           List<Child> selectionList = new ArrayList<>();

           if (getType() == EnumSelectType.USERGROUP
                   && selectGrp != null
                   && selectGrp.startsWith("M") == false
                   && ValidateUtil.isNumberHaifun(selectGrp)
                   && !userFilterFlg
                   ) {
                   GroupChild selectGrpChild =
                           groupList.stream()
                       .filter(child -> child instanceof GroupChild)
                       .filter(child -> Objects.equals(child.getValue(), selectGrp))
                       .map(child -> (GroupChild) child)
                       .findFirst()
                       .orElse(null);
                   selectionList.add(new Child(selectGrpChild.getGroup()));
           }


           if (selectGrp != null && ValidateUtil.isNumberHaifun(selectGrp)) {
               usrSidList.addAll(
                   new CmnBelongmDao(con)
                       .selectBelongLiveUserSid(
                               Integer.valueOf(selectGrp))
                       );
           } else if (selectGrp != null && selectGrp.startsWith("M")) {
               CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
               List<CmnMyGroupMsModel> cmgmList;
               cmgmList = cmgmDao.getMyGroupMsInfo(
                       reqMdl.getSmodel().getUsrsid(),
                       Integer.valueOf(selectGrp.substring(1)),
                       null,
                       false);

               usrSidList.addAll(
                       cmgmList.stream()
                           .map(cmgm -> cmgm.getMgmSid())
                           .collect(Collectors.toList())
                       );


           }
           //プラグイン利用制限されたユーザの除外
           if (getOnlyPluginUser() != null) {
               CommonBiz cmnBiz = new CommonBiz();
               usrSidList = cmnBiz.getCanUsePluginUser(con, getOnlyPluginUser(), usrSidList);
           }


           CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
           CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
           LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap = new LinkedHashMap<>();

           new CmnUsrmInfDao(con)
           .getUsersInfList(usrSidList
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
                   usrlabelDao.getLabListBelongUsrMap(new HashSet<>(usrSidList));

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


           selection.addAll(
                   selectionList.stream()
                       .filter(child -> {
                       if (child.getType() == EnumChildType.GROUP
                           && !selectedGroupSet.contains(child.getValue())) {
                           return true;
                       }
                       if (child.getType() == EnumChildType.USER
                           && !selectedUserSet.contains(child.getValue())) {
                           return true;
                       }
                       return false;
                       })
                   .collect(Collectors.toList())
                   );


       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return selection;


   }




    /**
     * @return onlyPluginUser
     * @see #getOnlyPluginUser()
     */
    public String getOnlyPluginUser() {
        return param__.getOnlyPluginUser();
    }

    @Override
    public final List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList) {
        List<IChild> ret = new ArrayList<>();
        try {
            if (param__.getType() == EnumSelectType.GROUP) {
                int[] grpSids =
                        selectedSidList.stream()
                           .filter(sid -> ValidateUtil.isNumberHaifun(sid))
                           .mapToInt(sid -> Integer.valueOf(sid))
                           .toArray();

                ArrayList<GroupModel> grpList = null;
                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                grpList = gdao.selectGroupNmListOrderbyConf(
                        Arrays.stream(grpSids)
                        .mapToObj(sid -> sid)
                        .collect(Collectors.toList())
                            );
                ret.addAll(
                        grpList.stream()
                        .map(grp -> new GroupChild(grp))
                        .collect(Collectors.toList())
                        );

            } else {
                //選択グループ
                int[] grpSids =
                        selectedSidList.stream()
                           .filter(sid -> sid.startsWith("G"))
                           .mapToInt(sid -> Integer.valueOf(sid.substring(1)))
                           .toArray();

                ArrayList<GroupModel> grpList = null;
                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                grpList = gdao.selectGroupNmListOrderbyConf(
                        Arrays.stream(grpSids)
                        .mapToObj(sid -> sid)
                        .collect(Collectors.toList())
                            );
                ret.addAll(
                        grpList.stream()
                        .map(grp -> new Child(grp))
                        .collect(Collectors.toList())
                        );

                //選択ユーザ
                List<Integer> usrSidList =
                        selectedSidList.stream()
                        .filter(sid -> ValidateUtil.isNumberHaifun(sid))
                        .map(sid -> Integer.valueOf(sid))
                        .collect(Collectors.toList());
                //プラグイン利用制限されたユーザの除外
                if (getOnlyPluginUser() != null) {
                    CommonBiz cmnBiz = new CommonBiz();
                    usrSidList = cmnBiz.getCanUsePluginUser(con, getOnlyPluginUser(), usrSidList);
                }

                CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
                CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
                LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap = new LinkedHashMap<>();



                new CmnUsrmInfDao(con)
                .getUsersInfList(usrSidList
                        .stream()
                        .map(sid -> String.valueOf(sid))
                        .toArray(String[]::new),
                        sortMdl)
                .stream()
                .forEach(u -> usrmInfMap.put(u.getUsrSid(), u));

                Map<Integer, CmnUsrmModel> usrmMap = new CmnUsrmDao(con)
                        .select(usrmInfMap.keySet())
                        .stream()
                        .collect(Collectors.toMap(
                                u -> u.getUsrSid(),
                                u -> u
                                ));
                //詳細検索時、追加情報を含むChildに置き換える
                if (getMode() == EnumModeType.EDIT_POPUP) {
                    GroupDao grpDao = new GroupDao(con);

                    //デフォルトグループマップ取得
                    Map<Integer, String> defGroupMap = grpDao.getDefaultGroups(
                            usrmInfMap.values()
                                .stream()
                                .map(usrInf -> String.valueOf(usrInf.getUsrSid()))
                                .mapToInt(Integer::parseInt)
                                .toArray());

                    ret.addAll(
                        usrmInfMap.values()
                            .stream()
                            .map(usrInf -> new CmnUserModel(
                                    usrInf, usrmMap.get(usrInf.getUsrSid())))
                            .map(usr -> new ChildSearch(usr, usr.getBinSid(), usr.getUsiPictKf(),
                                    defGroupMap.get(usr.getUsrSid())))
                            .collect(Collectors.toList())
                            );

                } else {
                    ret.addAll(
                            usrmInfMap.values()
                                .stream()
                                .map(usrInf -> new CmnUserModel(
                                        usrInf, usrmMap.get(usrInf.getUsrSid())))
                                .map(usr -> new Child(usr))
                                .collect(Collectors.toList())
                                );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }
    @Override
    public final List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {
        if (dspGroupListCash__ != null) {
            return dspGroupListCash__;
        }
        if (getType() == EnumSelectType.GROUP) {
            dspGroupListCash__ = List.of();
            return dspGroupListCash__;
        }
        List<IChild> groupSelection = new ArrayList<>();

        //マイグループ一覧取得
        if (getGrpType() == EnumGroupSelectType.WITHMYGROUP) {
            List<CmnMyGroupModel> cmgList = null;
            CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
            cmgList = cmgDao.getMyGroupList(reqMdl.getSmodel().getUsrsid());
            groupSelection.addAll(
                    cmgList.stream()
                    .map(cmg -> new GroupChild(cmg))
                    .collect(Collectors.toList())
                    );
        }
        //グループ一覧取得
        ArrayList<GroupModel> grpList = null;
        GroupBiz grpBiz = new GroupBiz();
        grpList = grpBiz.getGroupList(con);
        groupSelection.addAll(
                grpList.stream()
                .map(grp -> new GroupChild(grp))
                .collect(Collectors.toList())
                );
        dspGroupListCash__ = groupSelection;
        //詳細検索フィルタ
        if (getMode() == EnumModeType.EDIT_POPUP) {
            if (getMultiselectorFilter() != null) {
                List<String> filterUser = new ArrayList<String>();
                List<Integer> filterPost = new ArrayList<Integer>();
                List<Integer> filterLabel = new ArrayList<Integer>();
                for (String filter : getMultiselectorFilter()) {
                    String checkFilter = Optional.ofNullable(filter)
                                            .filter(fil -> fil.length() >= 5)
                                            .map(fil -> fil.substring(0, 5))
                                            .orElse("");
                    if (Objects.equals(checkFilter, "group")) {
                        dspGroupListCash__ = dspGroupListCash__
                                .stream()
                                .filter(child -> child instanceof GroupChild)
                                .map(child -> (GroupChild) child)
                                //グループのみに絞り込み
                                .filter(child -> child.getType() == EnumChildType.GROUP)
                                //フィルタ条件で絞り込み
                                .filter(child -> (
                                        Optional.ofNullable(child.getGroup())
                                            .map(grp -> grp.getGroupId())
                                            .orElse("")
                                            .contains(filter.substring(6))
                                        || Optional.ofNullable(child.getGroup())
                                        .map(grp -> grp.getGroupName())
                                        .orElse("")
                                        .contains(filter.substring(6))
                                        ))
                                .collect(Collectors.toList());

                    } else if (checkFilter.contains("user")) {
                        filterUser.add(filter.substring(5));
                    } else if (checkFilter.contains("post")) {
                        filterPost.add(NullDefault.getInt(filter.substring(5), -1));
                    } else if (checkFilter.contains("label")) {
                        filterLabel.add(NullDefault.getInt(filter.substring(6), -1));
                    }
                }
                if (getType() == EnumSelectType.USER
                        || getType() == EnumSelectType.USERGROUP) {
                    //表示ユーザの存在しないグループを非表示にする
                    UserSearchDao dao = new UserSearchDao(con);
                    List<Integer> sidList = dao.getMultiselectorTargetUser(
                            filterUser, filterPost, filterLabel);
                    GroupDao gDao = new GroupDao(con);
                    List<Integer> groupSidList = gDao.getUserGroup(sidList);
                    List<IChild> dspGroupList = dspGroupListCash__.stream()
                            .filter(child -> groupSidList.contains(
                                    NullDefault.getInt(child.getValue(), -1)))
                            .collect(Collectors.toList());

                    CmnMyGroupMsDao msDao = new CmnMyGroupMsDao(con);
                    List<Integer> myGroupSidList = msDao.getMyGroupSid(
                            reqMdl.getSmodel().getUsrsid(), sidList);
                    List<String> myGroupSidSearchList = new ArrayList<String>();
                    for (int sid : myGroupSidList) {
                        myGroupSidSearchList.add(String.valueOf("M" + sid));
                    }
                    List<IChild> dspMyGroupList = dspGroupListCash__.stream()
                            .filter(child -> myGroupSidSearchList.contains(child.getValue()))
                            .collect(Collectors.toList());

                    dspGroupListCash__ = new ArrayList<IChild>();
                    dspGroupListCash__.addAll(dspMyGroupList);
                    dspGroupListCash__.addAll(dspGroupList);
                }
            }
        }


        int defGrpSize = groupSelection.size();

        //カスタムユーザ選択UI用グループ除外処理
        if (ICustomUserGroupSelector.class.isAssignableFrom(getClass())) {

            ExclusionConf grpConf =
                    ((ICustomUserGroupSelector) this).answerGroupExclusion(
                            reqMdl,
                            con,
                            paramModel);
            dspGroupListCash__ =
                    dspGroupListCash__
                        .stream()
                        .filter(child -> grpConf.contains(
                                    child.getValue()
                                    ) == grpConf.isKyokaListFlg()
                                )
                        .collect(Collectors.toList());
            //除外後にグループ一覧が全グループと数が異なる場合にインデントを除去
            if (defGrpSize != dspGroupListCash__.size()) {
                dspGroupListCash__ =
                    dspGroupListCash__.stream()
                    .map(child -> {
                        if (child.getType() == EnumChildType.GROUP) {
                            ((GroupChild) child).getGroup().setClassLevel(0);
                        }
                        return child;
                    })
                    .collect(Collectors.toList());

            }
        }

        return dspGroupListCash__;
    }
    @Override
    public String answerDefaultGroup(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> groupSidList) {
        //デフォルトグループ取得
        final String defGroupSid;
        GroupBiz gbiz = new GroupBiz();
        try {
            defGroupSid = String.format("%d", gbiz.getDefaultGroupSid(
                    reqMdl.getSmodel().getUsrsid(), con));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return defGroupSid;
    }
    @Override
    public final List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<IChild> ret = new ArrayList<>();

        if (getType() != EnumSelectType.GROUP) {
            ret.addAll(__createUserSelection(paramModel, reqMdl, con, getMode(),
                    selectedGrpSid,
                    selectedSidList));
            //詳細検索フィルタ実行
            if (getMode() == EnumModeType.EDIT_POPUP
                    && getMultiselectorFilter() != null) {
                ret = __doDetailPopupFilter(
                        reqMdl, con, ret);
            }

        } else {

            //マイグループ一覧取得
            if (getGrpType() == EnumGroupSelectType.WITHMYGROUP) {
                List<CmnMyGroupModel> cmgList = null;
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                cmgList = cmgDao.getMyGroupList(reqMdl.getSmodel().getUsrsid());
                ret.addAll(
                        cmgList.stream()
                        .map(cmg -> new GroupChild(cmg))
                        .filter(child -> selectedSidList.contains(child.getValue()) == false)
                        .collect(Collectors.toList())
                        );
            }
            //グループ一覧取得
            ArrayList<GroupModel> grpList = null;
            GroupBiz grpBiz = new GroupBiz();
            grpList = grpBiz.getGroupList(con);
            ret.addAll(
                    grpList.stream()
                    .map(grp -> new GroupChild(grp))
                    .filter(child -> selectedSidList.contains(child.getValue()) == false)
                    .collect(Collectors.toList())
                    );

            if (getMode() == EnumModeType.EDIT_POPUP
                    && getType() == EnumSelectType.GROUP) {
                if (getMultiselectorFilter() != null) {
                    for (String filter : getMultiselectorFilter()) {
                        String checkFilter = Optional.ofNullable(filter)
                                                .filter(fil -> fil.length() >= 5)
                                                .map(fil -> fil.substring(0, 5))
                                                .orElse("");
                        if (Objects.equals(checkFilter, "group")) {
                            ret = ret
                                    .stream()
                                    .filter(child -> child instanceof GroupChild)
                                    .map(child -> (GroupChild) child)
                                    //グループのみに絞り込み
                                    .filter(child -> child.getType() == EnumChildType.GROUP)
                                    //フィルタ条件で絞り込み
                                    .filter(child -> (
                                            Optional.ofNullable(child.getGroup())
                                                .map(grp -> grp.getGroupId())
                                                .orElse("")
                                                .contains(filter.substring(6))
                                            || Optional.ofNullable(child.getGroup())
                                            .map(grp -> grp.getGroupName())
                                            .orElse("")
                                            .contains(filter.substring(6))
                                            ))
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }
        }

        //カスタムユーザ選択UI用除外処理
        if (ICustomUserGroupSelector.class.isAssignableFrom(getClass())) {

            ExclusionConf usrConf =
                    ((ICustomUserGroupSelector) this).answerUserExclusion(
                            reqMdl,
                            con,
                            paramModel);

            ret = ret.stream()
                    .filter(child -> usrConf.contains(
                                child.getValue()
                                ) == usrConf.isKyokaListFlg()
                            )
                    .collect(Collectors.toList());

        }
        //詳細検索時、追加情報を含むChildに置き換える
        if (getMode() == EnumModeType.EDIT_POPUP
                && getType() != EnumSelectType.GROUP) {
            //グループは置き換えなし
            List<IChild> searchSelectionList = new ArrayList<>();
            searchSelectionList.addAll(
                    ret.stream()
                        .filter(child -> child.getValue().contains("G"))
                        .collect(Collectors.toList())
                    );



            //デフォルトグループマップ取得
            GroupDao grpDao = new GroupDao(con);
            Map<Integer, String> defGroupMap = grpDao.getDefaultGroups(
                    ret.stream()
                        .map(child -> child.getValue())
                        .filter(sid -> !sid.contains("G"))
                        .filter(sid -> !sid.contains("M"))
                        .mapToInt(Integer::parseInt)
                        .toArray());

            //ユーザに役職、デフォルトグループ、ユーザ画像情報を追加
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap = new LinkedHashMap<>();
            new CmnUsrmInfDao(con)
            .getUsersInfList(ret.stream()
                    .map(child -> child.getValue())
                    .filter(sid -> !sid.contains("G"))
                    .filter(sid -> !sid.contains("M"))
                    .toArray(String[]::new),
                    sortMdl)
            .stream()
            .forEach(u -> usrmInfMap.put(u.getUsrSid(), u));

            Map<Integer, CmnUsrmModel> usrmMap = new CmnUsrmDao(con)
                    .select(usrmInfMap.keySet())
                    .stream()
                    .collect(Collectors.toMap(
                            u -> u.getUsrSid(),
                            u -> u
                            ));

            searchSelectionList.addAll(
                usrmInfMap.values()
                    .stream()
                    .map(usrInf -> new CmnUserModel(
                            usrInf, usrmMap.get(usrInf.getUsrSid())))
                    .map(usr -> new ChildSearch(
                            usr, usr.getBinSid(), usr.getUsiPictKf(),
                            defGroupMap.get(usr.getUsrSid())))
                    .collect(Collectors.toList())
                    );

            ret = searchSelectionList;
        }

        return ret;

    }

    @Override
    public final void doFilter(GroupSelectionDspInitFilterChain chain,
            RequestModel reqMdl, Connection con, ParameterObject paramModel,
            ISelector selector, GroupSelectionDspInitModel initModel) throws SQLException {
        super.doFilter(chain, reqMdl, con, paramModel, selector, initModel);
    }

    /**
    *
    * <br>[機  能] 詳細検索時選択元 描画フィルタ
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl
    * @param con
    * @param userList
    * @throws SQLException
    * @return フィルタによる除外後の選択元リスト
    */
   public List<IChild> __doDetailPopupFilter(
           RequestModel reqMdl, Connection con,
           List<IChild> userList) throws SQLException {
       if (getMultiselectorFilter() == null) {
           return userList;
       }
       List<IChild> ret = userList;
       for (String filter : getMultiselectorFilter()) {
           String checkFilter = Optional.ofNullable(filter)
                                   .filter(fil -> fil.length() >= 5)
                                   .map(fil -> fil.substring(0, 5))
                                   .orElse("");

           if (checkFilter.contains("user")) {
               ret =
                   ret.stream()
                   .filter(child -> child instanceof Child)
                   .map(child -> (Child) child)
                   //ユーザのみに絞り込み
                   .filter(child -> child.getType() == EnumChildType.USER)
                   //フィルタ条件で絞り込み
                   .filter(child -> (
                           Optional.ofNullable(child.getUser())
                               .map(user -> user.getUsiSei())
                               .orElse("")
                               .contains(filter.substring(5))
                           || Optional.ofNullable(child.getUser())
                               .map(user -> user.getUsiMei())
                               .orElse("")
                               .contains(filter.substring(5))
                           || Optional.ofNullable(child.getUser())
                               .map(user -> user.getUsiSeiKn())
                               .orElse("")
                               .contains(filter.substring(5))
                           || Optional.ofNullable(child.getUser())
                               .map(user -> user.getUsiMeiKn())
                               .orElse("")
                               .contains(filter.substring(5))
                           || Optional.ofNullable(child.getUser())
                               .map(user -> user.getUsiSyainNo())
                               .orElse("")
                               .contains(filter.substring(5))
                           ))
                   .collect(Collectors.toList());
               if (userList.size() > 0 && userList.get(0).getType() != EnumChildType.USER) {
                   ret.add(0, userList.get(0));
               }
               continue;
           }
           if (checkFilter.contains("post")) {
               int sid = NullDefault.getInt(filter.substring(5), -1);
               ret =
                       ret.stream()
                       .filter(child -> child instanceof Child)
                       .map(child -> (Child) child)
                       //ユーザのみに絞り込み
                       .filter(child -> child.getType() == EnumChildType.USER)
                       //フィルタ条件で絞り込み
                       .filter(child -> (
                               Objects.equals(sid,
                                       Optional.ofNullable(child.getUser())
                                           .map(user -> user.getPosSid())
                                           .orElse(0))
                               ))
                       .collect(Collectors.toList());
               if (userList.size() > 0 && userList.get(0).getType() != EnumChildType.USER) {
                   ret.add(0, userList.get(0));
               }
               continue;
           }
           if (checkFilter.contains("label")) {
               CmnUsrmLabelDao dao = new CmnUsrmLabelDao(con);
               List<Integer> sidList = dao.getLabelUser(
                       NullDefault.getInt(filter.substring(6), -1));
               ret =
                       ret.stream()
                       .filter(child -> child instanceof Child)
                       .map(child -> (Child) child)
                       //ユーザのみに絞り込み
                       .filter(child -> child.getType() == EnumChildType.USER)
                       //フィルタ条件で絞り込み
                       .filter(child -> sidList.contains(
                                           Optional.ofNullable(child.getUser())
                                               .map(user -> user.getUsrSid())
                                               .orElse(0)
                                           )
                               )
                       .collect(Collectors.toList());
               if (userList.size() > 0 && userList.get(0).getType() != EnumChildType.USER) {
                   ret.add(0, userList.get(0));
               }
               continue;

           }

       }
       return ret;
   }

    @Override
    public  final void doFilter(ValidateFilterChain chain, RequestModel reqMdl,
            Connection con, ParameterObject paramModel, ISelector selector,
            Select select) throws SQLException {
        boolean isErr = false;

        String[] sids = paramModel.get(select.getParameterName(), String[].class);

        //ユーザ
        UserBiz ubiz = new UserBiz();
        ArrayList<CmnUsrmInfModel> uList;
        String[] usrSids = Arrays.stream(sids)
                .filter(sid -> ValidateUtil.isNumberHaifun(sid))
                .toArray(String[]::new);
        try {
            uList = ubiz.getUserList(con,
                    usrSids, GSConstUser.USER_JTKBN_ACTIVE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (uList.size() != usrSids.length) {
            chain.addException(
                    new UIValidateException("error.select.forum.unknown.user",
                    new ActionMessage("error.select.forum.unknown.user",
                            selector.getLabel().getMessage(new GsMessage(reqMdl)))
                    )
                );
            isErr = true;
        }

        if (isErr == false) {
            //グループ
            int[] grpSids = Arrays.stream(sids)
                    .filter(sid -> sid.startsWith("G"))
                    .mapToInt(sid -> Integer.valueOf(sid.substring(1)))
                    .toArray();
            if (grpSids.length > 0) {
                GroupDao gDao = new GroupDao(con);
                List<CmnGroupmModel> gList;
                try {
                    gList = gDao.getGroups(grpSids);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (grpSids.length != gList.size()) {
                    chain.addException(
                            new UIValidateException("error.select.forum.unknown.user",
                            new ActionMessage("error.select.forum.unknown.user",
                                    getLabel().getMessage(new GsMessage(reqMdl)))
                            )
                        );
                    isErr = true;
                }
            }
        }
        chain.doFilterChain(reqMdl, con, paramModel, selector, select);


    }
    @Override
    public boolean isUseDetailSearch() {
        return true;
    }
}
