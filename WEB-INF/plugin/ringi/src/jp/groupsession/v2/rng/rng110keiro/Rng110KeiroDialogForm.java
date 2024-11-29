package jp.groupsession.v2.rng.rng110keiro;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.SingleSelectModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rng.RngConst;

/**
*
* <br>[機  能] 稟議経路詳細設定ダイアログ用 フォーム
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class Rng110KeiroDialogForm extends AbstractForm implements IRng110KeiroDialogParam {
    /** テンプレート共有範囲 */
    private int keiroShareRange__ = RngConst.RNG_TEMPLATE_SHARE;
    /** 経路設定先 審議or最終確認*/
    private int keiroRootType__ = RngConst.RNG_RNCTYPE_APPR;
    /** 経路種別*/
    private int keiroKbn__ = 0;
    /** 経路名*/
    private String keiroName__ = "";
    /** 経路コメント*/
    private String keiroComment__ = "";
    /** 経路承認条件 */
    private int outcondition__ = 1;
    /** 経路承認条件 閾値*/
    private String outcond_threshold__ = "";

    /** 対象ユーザ選択*/
    private UserGroupSelectModel usrgroupselect__ = new UserGroupSelectModel();
    /** 対象ユーザ選択用 選択UI */
    private UserGroupSelector usrgroupselectSelector__ =
            UserGroupSelector.builder()
                .chainGroupSelectionParamName(
                        "usrgroupselect.group.selectedSingle"
                                )
                .chainType(EnumSelectType.USERGROUP)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "usrgroupselect.selected("
                                + "target"
                                + ")"
                                )
                        )
                .build();

    /** 対象ユーザ指定*/
    private UserGroupSelectModel usrgrouptarget__ = new UserGroupSelectModel();
    /** 対象ユーザ指定用 選択UI */
    private UserGroupSelector usrgrouptargetSelector__ =
            UserGroupSelector.builder()
                .chainGroupSelectionParamName(
                        "usrgrouptarget.group.selectedSingle"
                                )
                .chainType(EnumSelectType.USERGROUP)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "usrgrouptarget.selected("
                                + "target"
                                + ")"
                                )
                        )
                .build();

    /** 対象役職設定*/
    private TreeMap<String, TargetPosSel> targetpos__
    = new TreeMap<String, TargetPosSel>(new NameComparator());

    /**
    *
    * <br>[機  能]追加された経路利用条件を数字で正常に並び替えます
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   private static class NameComparator implements Comparator<String> {

       @Override
       public int compare(String first, String next) {

           int firstNum = NullDefault.getInt(first, Integer.MAX_VALUE);
           int nextNum = NullDefault.getInt(next, Integer.MAX_VALUE);
           int returnNum = 1;

           if (firstNum < nextNum) {
               returnNum = -1;
           } else if (firstNum == nextNum) {
               returnNum =  0;
           } else if (firstNum > nextNum) {
               returnNum =  1;
           }
           return returnNum;
       }

   }

    /** 審議者が申請者の場合 */
    private int skip__;

    /** 複数選択 */
    private int multisel__;

    /** 自己審議の許可 */
    private int own__;

    /** 該当ユーザが存在しない場合 */
    private int nonuser__;

    /** 任意経路の追加 */
    private int addkeiro__;

    /** 後閲の許可*/
    private int kouetu__;

    /** 経路使用条件リスト*/
    private Map<String, KeiroInCondition> inCond__ = new TreeMap<>(new NameComparator());

    /** 選択範囲 グループ選択*/
    private GroupComboModel groupSel__ = new GroupComboModel();
    /** 選択範囲 グループ選択 選択UI */
    private UserGroupSelector groupSelSelector__ =
            UserGroupSelector.builder()
                .chainType(EnumSelectType.GROUP)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "groupSel.selected"
                                )
                        )
                .build();

    /** 上長階層数*/
    private int bossStepCnt__;
    /** 必須上長階層数*/
    private int bossStepCntMin__;
    /** ドラッグ要素区分*/
    private int dragContentKbn__ = DRAG_CONTENT_KBN_BASIC;
    /** 審議者への後閲指示の許可*/
    private int kouetuSiji__;

    /**
     * <p>keiroKbn を取得します。
     * @return keiroKbn
     */
    public int getKeiroKbn() {
        return keiroKbn__;
    }

    /**
     * <p>keiroKbn をセットします。
     * @param keiroKbn keiroKbn
     */
    public void setKeiroKbn(int keiroKbn) {
        keiroKbn__ = keiroKbn;
    }

    /**
     * <p>keiroName を取得します。
     * @return keiroName
     */
    public String getKeiroName() {
        return keiroName__;
    }

    /**
     * <p>keiroName をセットします。
     * @param keiroName keiroName
     */
    public void setKeiroName(String keiroName) {
        keiroName__ = keiroName;
    }

    /**
     * <p>outcondition を取得します。
     * @return outcondition
     */
    public int getOutcondition() {
        return outcondition__;
    }

    /**
     * <p>outcondition をセットします。
     * @param outcondition outcondition
     */
    public void setOutcondition(int outcondition) {
        outcondition__ = outcondition;
    }

    /**
     * <p>outcond_threshold を取得します。
     * @return outcond_threshold
     */
    public String getOutcond_threshold() {
        return outcond_threshold__;
    }

    /**
     * <p>outcond_threshold をセットします。
     * @param threshold outcond_threshold
     */
    public void setOutcond_threshold(String threshold) {
        outcond_threshold__ = threshold;
    }

    /**
     * <p>usrgroupselect を取得します。
     * @return usrgroupselect
     */
    public UserGroupSelectModel getUsrgroupselect() {
        return usrgroupselect__;
    }

    /**
     * <p>usrgroupselect をセットします。
     * @param usrgroupselect usrgroupselect
     */
    public void setUsrgroupselect(UserGroupSelectModel usrgroupselect) {
        usrgroupselect__ = usrgroupselect;
    }

    /**
     * <p>targetpos を取得します。
     * @return targetpos
     */
    public TreeMap<String, TargetPosSel> getTargetposMap() {
        return targetpos__;
    }
    /**
     * <p>targetpos を取得します。
     * @param key インデックス
     * @return targetpos
     */
    public TargetPosSel getTargetpos(String key) {
        if (targetpos__.containsKey(key)) {
            TargetPosSel ret = targetpos__.get(key);
            if (ret == null) {
                ret =  new TargetPosSel();
                return ret;
            }
            return ret;
        }
        TargetPosSel ret =  new TargetPosSel();
        targetpos__.put(key, ret);
        return ret;
    }
    /**
     * <p>targetpos をセットします。
     * @param key インデックス
     * @param posInf 役職設定
     */
    public void setTargetpos(String key, TargetPosSel posInf) {
        targetpos__.put(key, posInf);
    }

    /**
     * <p>targetpos をセットします。
     * @param targetpos targetpos
     */
    public void setTargetposMap(TreeMap<String, TargetPosSel> targetpos) {
        targetpos__ = targetpos;
    }


    /**
     * <p>skip を取得します。
     * @return skip
     */
    public int getSkip() {
        return skip__;
    }

    /**
     * <p>skip をセットします。
     * @param skip skip
     */
    public void setSkip(int skip) {
        skip__ = skip;
    }

    /**
     * <p>multisel を取得します。
     * @return multisel
     */
    public int getMultisel() {
        return multisel__;
    }

    /**
     * <p>multisel をセットします。
     * @param multisel multisel
     */
    public void setMultisel(int multisel) {
        multisel__ = multisel;
    }

    /**
     * <p>own を取得します。
     * @return own
     */
    public int getOwn() {
        return own__;
    }

    /**
     * <p>own をセットします。
     * @param own own
     */
    public void setOwn(int own) {
        own__ = own;
    }

    /**
     * <p>nonuser を取得します。
     * @return nonuser
     */
    public int getNonuser() {
        return nonuser__;
    }

    /**
     * <p>nonuser をセットします。
     * @param nonuser nonuser
     */
    public void setNonuser(int nonuser) {
        nonuser__ = nonuser;
    }

    /**
     * <p>addkeiro を取得します。
     * @return addkeiro
     */
    public int getAddkeiro() {
        return addkeiro__;
    }

    /**
     * <p>addkeiro をセットします。
     * @param addkeiro addkeiro
     */
    public void setAddkeiro(int addkeiro) {
        addkeiro__ = addkeiro;
    }

    /**
     * <p>kouetu を取得します。
     * @return kouetu
     */
    public int getKouetu() {
        return kouetu__;
    }

    /**
     * <p>kouetu をセットします。
     * @param kouetu kouetu
     */
    public void setKouetu(int kouetu) {
        kouetu__ = kouetu;
    }

    /**
     * <p>inCond を取得します。
     * @return inCond
     */
    public Map<String, KeiroInCondition> getInCondMap() {
        return inCond__;
    }
    /**
     * <p>inCond を取得します。
     * @param key キー
     * @return inCond
     */
    public KeiroInCondition getInCond(String key) {
        if (inCond__.containsKey(key)) {
            return inCond__.get(key);
        }
        KeiroInCondition ret = new KeiroInCondition();
        inCond__.put(key, ret);
        return ret;
    }

    /**
     * <p>inCond をセットします。
     * @param key キー
     * @param inCond inCond
     */
    public void setInCond(String key, KeiroInCondition inCond) {
        inCond__.put(key, inCond);
    }
    /**
     * <p>inCond をセットします。
     * @param inCond inCond
     */
    public void setInCondMap(Map<String, KeiroInCondition> inCond) {
        inCond__ = inCond;
    }


    /**
     * <p>groupSel を取得します。
     * @return groupSel
     */
    public GroupComboModel getGroupSel() {
        return groupSel__;
    }

    /**
     * <p>groupSel をセットします。
     * @param groupSel groupSel
     */
    public void setGroupSel(GroupComboModel groupSel) {
        groupSel__ = groupSel;
    }


    /**
     * <p>bossStepCnt を取得します。
     * @return bossStepCnt
     */
    public int getBossStepCnt() {
        return bossStepCnt__;
    }

    /**
     * <p>bossStepCnt をセットします。
     * @param bossStepCnt bossStepCnt
     */
    public void setBossStepCnt(int bossStepCnt) {
        bossStepCnt__ = bossStepCnt;
    }


    /**
     * <p>bossStepCntMin を取得します。
     * @return bossStepCntMin
     */
    public int getBossStepCntMin() {
        return bossStepCntMin__;
    }

    /**
     * <p>bossStepCntMin をセットします。
     * @param bossStepCntMin bossStepCntMin
     */
    public void setBossStepCntMin(int bossStepCntMin) {
        bossStepCntMin__ = bossStepCntMin;
    }
    /**
     * <p>usrgrouptarget を取得します。
     * @return usrgrouptarget
     */
    public UserGroupSelectModel getUsrgrouptarget() {
        return usrgrouptarget__;
    }

    /**
     * <p>usrgrouptarget をセットします。
     * @param usrgrouptarget usrgrouptarget
     */
    public void setUsrgrouptarget(UserGroupSelectModel usrgrouptarget) {
        usrgrouptarget__ = usrgrouptarget;
    }

    /**
     * <p>dragContentKbn を取得します。
     * @return dragContentKbn
     */
    public int getDragContentKbn() {
        return dragContentKbn__;
    }

    /**
     * <p>dragContentKbn をセットします。
     * @param dragContentKbn dragContentKbn
     */
    public void setDragContentKbn(int dragContentKbn) {
        dragContentKbn__ = dragContentKbn;
    }


    /**
     * <p>keiroRootType を取得します。
     * @return keiroRootType
     */
    public int getKeiroRootType() {
        return keiroRootType__;
    }

    /**
     * <p>keiroRootType をセットします。
     * @param keiroRootType keiroRootType
     */
    public void setKeiroRootType(int keiroRootType) {
        keiroRootType__ = keiroRootType;
    }


    /**
     * <p>keiroShareRange を取得します。
     * @return keiroShareRange
     */
    public int getKeiroShareRange() {
        return keiroShareRange__;
    }

    /**
     * <p>keiroShareRange をセットします。
     * @param keiroShareRange keiroShareRange
     */
    public void setKeiroShareRange(int keiroShareRange) {
        this.keiroShareRange__ = keiroShareRange;
    }


    /**
     *
     * <br>[機  能] 経路選択用内部クラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class TargetPosSel {
        /**役職選択*/
        private SingleSelectModel posSel__ = new SingleSelectModel();
        /**グループ選択*/
        private SingleSelectModel grpSel__ = new SingleSelectModel();
        /**
         * <p>posSel を取得します。
         * @return posSel
         */
        public SingleSelectModel getPosSel() {
            return posSel__;
        }
        /**
         * <p>posSel をセットします。
         * @param posSel posSel
         */
        public void setPosSel(SingleSelectModel posSel) {
            posSel__ = posSel;
        }
        /**
         * <p>grpSel を取得します。
         * @return grpSel
         */
        public SingleSelectModel getGrpSel() {
            return grpSel__;
        }
        /**
         * <p>grpSel をセットします。
         * @param grpSel grpSel
         */
        public void setGrpSel(SingleSelectModel grpSel) {
            grpSel__ = grpSel;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TargetPosSel) {
                TargetPosSel target = (TargetPosSel) obj;
                String targetGrp = String.valueOf(target.grpSel__.getSelected());
                String thisGrp = String.valueOf(this.grpSel__.getSelected());
                String targetPos = String.valueOf(target.posSel__.getSelected());
                String thisPos = String.valueOf(this.posSel__.getSelected());
                if (targetGrp.equals(thisGrp)
                        && targetPos.equals(thisPos)) {
                    return true;
                }
            }
            return super.equals(obj);
        }
        @Override
        public int hashCode() {
            return Objects.hash(this.grpSel__.getSelected(), this.posSel__.getSelected());
        }
    }


    /**
     * <p>kouetuSiji を取得します。
     * @return kouetuSiji
     */
    public int getKouetuSiji() {
        return kouetuSiji__;
    }

    /**
     * <p>kouetuSiji をセットします。
     * @param kouetuSiji kouetuSiji
     */
    public void setKouetuSiji(int kouetuSiji) {
        kouetuSiji__ = kouetuSiji;
    }

    /**
     * <p>keiroComment を取得します。
     * @return keiroComment
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#keiroComment__
     */
    public String getKeiroComment() {
        return keiroComment__;
    }

    /**
     * <p>keiroComment をセットします。
     * @param keiroComment keiroComment
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#keiroComment__
     */
    public void setKeiroComment(String keiroComment) {
        keiroComment__ = keiroComment;
    }

    /**
     * <p>usrgroupselectSelector を取得します。
     * @return usrgroupselectSelector
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#usrgroupselectSelector__
     */
    public UserGroupSelector getUsrgroupselectSelector() {
        return usrgroupselectSelector__;
    }

    /**
     * <p>usrgroupselectSelector をセットします。
     * @param usrgroupselectSelector usrgroupselectSelector
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#usrgroupselectSelector__
     */
    public void setUsrgroupselectSelector(UserGroupSelector usrgroupselectSelector) {
        usrgroupselectSelector__ = usrgroupselectSelector;
    }

    /**
     * <p>usrgrouptargetSelector を取得します。
     * @return usrgrouptargetSelector
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#usrgrouptargetSelector__
     */
    public UserGroupSelector getUsrgrouptargetSelector() {
        return usrgrouptargetSelector__;
    }

    /**
     * <p>usrgrouptargetSelector をセットします。
     * @param usrgrouptargetSelector usrgrouptargetSelector
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#usrgrouptargetSelector__
     */
    public void setUsrgrouptargetSelector(UserGroupSelector usrgrouptargetSelector) {
        usrgrouptargetSelector__ = usrgrouptargetSelector;
    }

    /**
     * <p>groupSelSelector を取得します。
     * @return groupSelSelector
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#groupSelSelector__
     */
    public UserGroupSelector getGroupSelSelector() {
        return groupSelSelector__;
    }

    /**
     * <p>groupSelSelector をセットします。
     * @param groupSelSelector groupSelSelector
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm#groupSelSelector__
     */
    public void setGroupSelSelector(UserGroupSelector groupSelSelector) {
        groupSelSelector__ = groupSelSelector;
    }


}
