package jp.groupsession.v2.bbs.bbs030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
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
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class Bbs030ForumMemberSelector extends AbstractSelector {
    /** 含有クラス ユーザ選択機能実装*/
    private final UserGroupSelector base__;


    protected Bbs030ForumMemberSelector(ParamForm param) {
        super(param);
        base__ = UserGroupSelector.builder()
                .chainType(EnumSelectType.USERGROUP)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
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
   public static class ParamForm extends SelectorFactory<Bbs030ForumMemberSelector, ParamForm> {

       public ParamForm(Class<Bbs030ForumMemberSelector> targetClz) {
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
       ParamForm ret = new ParamForm(Bbs030ForumMemberSelector.class);
       return ret;
   }
    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {
        return List.of();
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<IChild> selectionList =
                new ArrayList<>();
        Bbs030Form form = paramModel.getInstance(Bbs030Form.class);
        int parentForumSid = form.getBbs030ParentForumSid();
        if (parentForumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            return List.of();
        }

        //親フォーラムのメンバー
        List<String> parentMemberSidList =
                __getParentForumMembers(con, parentForumSid);


        //メンバーユーザから管理者に選択しているユーザを除いてリストを作成
        Set<String> selectedSet = selectedSidList
                                           .stream()
                                           .collect(Collectors.toSet());
        List<String> selectionSidList =
                parentMemberSidList.stream()
                .filter(sid -> selectedSet.contains(sid) == false)
                .collect(Collectors.toList());

        List<String> userList =
                selectionSidList.stream()
                    .filter(sid -> sid.startsWith(
                            GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR) == false)
                    .collect(Collectors.toList());

        //グループ一覧取得
        int[] grpSids =
                selectionSidList.stream()
                .filter(sid -> sid.startsWith(
                        GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR))
                .map(sid -> sid.substring(
                        GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR.length()
                        ))
               .mapToInt(sid -> Integer.valueOf(sid))
               .toArray();

        ArrayList<GroupModel> grpList = null;
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        grpList = gdao.selectGroupNmListOrderbyConf(
                Arrays.stream(grpSids)
                .mapToObj(sid -> sid)
                .collect(Collectors.toList())
                    );
        selectionList.addAll(
                grpList
                    .stream()
                    .map(grp -> new Child(grp))
                    .collect(Collectors.toList())
                );


        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap = new LinkedHashMap<>();

        new CmnUsrmInfDao(con)
        .getUsersInfList(userList
                .stream()
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
                usrlabelDao.getLabListBelongUsrMap(
                        userList.stream()
                            .map(sid -> Integer.valueOf(sid))
                            .collect(Collectors.toSet())
                                );

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
    }
    /**
     * <br>[機  能] 親フォーラムのメンバーを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @return メンバーSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private List<String> __getParentForumMembers(
            Connection con, int forumSid)
                    throws SQLException {



        //メンバーSIDリスト
        List<String> memberList = new ArrayList<String>();

        //フォーラムが未設定の場合、モードに合わせて全ユーザまたは全グループを返します
        if (forumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            return memberList;

        }

        //フォーラムのメンバーを取得
        BbsBiz bbsBiz = new BbsBiz();
        forumSid = bbsBiz.getBfiSidForMemberInfo(con, forumSid);
        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        String[] memberSid = bbsMemDao.getForumMemberId(forumSid);
        return  Arrays.stream(memberSid)
                .collect(Collectors.toList());
    }
    @Override
    public boolean isUseDetailSearch() {
        return false;
    }
    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        return base__.answerSelectedList(reqMdl, con, paramModel, selectedSidList);
    }

}
