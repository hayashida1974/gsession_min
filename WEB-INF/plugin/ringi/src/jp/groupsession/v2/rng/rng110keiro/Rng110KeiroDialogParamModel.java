package jp.groupsession.v2.rng.rng110keiro;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
/**
*
* <br>[機  能] 稟議経路詳細設定ダイアログ用 フォーム
* <br>[解  説]
* <br>[備  考] 設定項目を増やした場合はequalsメソッドの修正を忘れないこと
*
* @author JTS
*/
public class Rng110KeiroDialogParamModel
extends AbstractParamModel implements IRng110KeiroDialogParam {
    /** テンプレート共有範囲 */
    private int keiroShareRange__ = RngConst.RNG_TEMPLATE_SHARE;
    /** 経路設定先 審議or最終確認*/
    private int keiroRootType__ = RngConst.RNG_RNCTYPE_APPR;
    /** 経路ステップSID*/
    private int rtkSid__ = 0;
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
    /** 対象ユーザ指定*/
    private UserGroupSelectModel usrgrouptarget__ = new UserGroupSelectModel();

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
            if (firstNum == -1 && nextNum == 0) {
                return returnNum;
            }
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

    /** 審議者が申請者の場合申請時にスキップ */
    private int skip__;

    /** 複数選択 */
    private int multisel__;

    /** 自己審議の許可 */
    private int own__;

    /** 該当ユーザが存在しない場合 */
    private int nonuser__;

    /** 任意経路の追加 */
    private int addkeiro__;

    /** 経路の後閲の許可*/
    private int kouetu__ = RngConst.RNG_KOETU_NO;

    /** 審議者への後閲指示の許可*/
    private int kouetuSiji__ = RngConst.RNG_KOETU_NO;


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

    /** 経路使用条件リスト*/
    private Map<String, KeiroInCondition> inCond__ = new TreeMap<>(new NameComparator());

    /** 選択範囲 グループ選択*/
    private GroupComboModel groupSel__ = new GroupComboModel();
    /** 上長階層数*/
    private int bossStepCnt__ = 1;
    /** 必須上長階層数*/
    private int bossStepCntMin__ = 1;

    /** ドラッグ要素区分*/
    private int dragContentKbn__ = DRAG_CONTENT_KBN_BASIC;


    @Override
    public int getKeiroKbn() {
        return keiroKbn__;
    }

    @Override
    public void setKeiroKbn(int keiroKbn) {
        keiroKbn__ = keiroKbn;
    }

    @Override
    public String getKeiroName() {
        return keiroName__;
    }

    @Override
    public void setKeiroName(String keiroName) {
        keiroName__ = keiroName;
    }

    @Override
    public int getOutcondition() {
        return outcondition__;
    }

    @Override
    public void setOutcondition(int outcondition) {
        outcondition__ = outcondition;
    }

    @Override
    public String getOutcond_threshold() {
        return outcond_threshold__;
    }

    @Override
    public void setOutcond_threshold(String threshold) {
        outcond_threshold__ = threshold;
    }

    @Override
    public UserGroupSelectModel getUsrgroupselect() {
        return usrgroupselect__;
    }

    @Override
    public void setUsrgroupselect(UserGroupSelectModel usrgroupselect) {
        usrgroupselect__ = usrgroupselect;
    }

    @Override
    public TreeMap<String, TargetPosSel> getTargetposMap() {
        return targetpos__;
    }

    @Override
    public void setTargetposMap(TreeMap<String, TargetPosSel> targetpos) {
        targetpos__ = targetpos;
    }
    @Override
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
    @Override
    public void setTargetpos(String key, TargetPosSel posInf) {
        targetpos__.put(key, posInf);
    }

    @Override
    public int getSkip() {
        return skip__;
    }

    @Override
    public void setSkip(int skip) {
        skip__ = skip;
    }

    @Override
    public int getMultisel() {
        return multisel__;
    }

    @Override
    public void setMultisel(int multisel) {
        multisel__ = multisel;
    }

    @Override
    public int getOwn() {
        return own__;
    }

    @Override
    public void setOwn(int own) {
        own__ = own;
    }

    @Override
    public int getNonuser() {
        return nonuser__;
    }

    @Override
    public void setNonuser(int nonuser) {
        nonuser__ = nonuser;
    }

    @Override
    public int getAddkeiro() {
        return addkeiro__;
    }

    @Override
    public void setAddkeiro(int addkeiro) {
        addkeiro__ = addkeiro;
    }

    @Override
    public int getKouetu() {
        return kouetu__;
    }

    @Override
    public void setKouetu(int kouetu) {
        kouetu__ = kouetu;
    }

    @Override
    public Map<String, KeiroInCondition> getInCondMap() {
        return inCond__;
    }
    @Override
    public KeiroInCondition getInCond(String key) {
        if (inCond__.containsKey(key)) {
            return inCond__.get(key);
        }
        KeiroInCondition ret = new KeiroInCondition();
        inCond__.put(key, ret);
        return ret;
    }

    @Override
    public void setInCond(String key, KeiroInCondition inCond) {
        inCond__.put(key, inCond);
    }
    @Override
    public void setInCondMap(Map<String, KeiroInCondition> inCond) {
        inCond__ = inCond;
    }

    @Override
    public GroupComboModel getGroupSel() {
        return groupSel__;
    }

    @Override
    public void setGroupSel(GroupComboModel groupSel) {
        groupSel__ = groupSel;
    }

    @Override
    public int getBossStepCnt() {
        return bossStepCnt__;
    }

    @Override
    public void setBossStepCnt(int bossStepCnt) {
        bossStepCnt__ = bossStepCnt;
    }

    @Override
    public int getBossStepCntMin() {
        return bossStepCntMin__;
    }

    @Override
    public void setBossStepCntMin(int bossStepCntMin) {
        bossStepCntMin__ = bossStepCntMin;
    }

    @Override
    public UserGroupSelectModel getUsrgrouptarget() {
        return usrgrouptarget__;
    }

    @Override
    public void setUsrgrouptarget(UserGroupSelectModel usrgrouptarget) {
        usrgrouptarget__ = usrgrouptarget;
    }

    @Override
    public int getDragContentKbn() {
        return dragContentKbn__;
    }

    @Override
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

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + addkeiro__;
        result = prime * result + bossStepCntMin__;
        result = prime * result + bossStepCnt__;
        result = prime * result + dragContentKbn__;
        result = prime * result
                + ((groupSel__ == null) ? 0 : groupSel__.hashCode());
        result = prime * result
                + ((inCond__ == null) ? 0 : inCond__.hashCode());
        result = prime * result + keiroKbn__;
        result = prime * result + ((keiroComment__ == null) ? 0
                : keiroComment__.hashCode());
        result = prime * result
                + ((keiroName__ == null) ? 0 : keiroName__.hashCode());
        result = prime * result + keiroRootType__;
        result = prime * result + kouetu__;
        result = prime * result + multisel__;
        result = prime * result + nonuser__;
        result = prime * result + ((outcond_threshold__ == null) ? 0
                : outcond_threshold__.hashCode());
        result = prime * result + outcondition__;
        result = prime * result + own__;
        result = prime * result + skip__;
        result = prime * result
                + ((targetpos__ == null) ? 0 : targetpos__.hashCode());
        result = prime * result + ((usrgroupselect__ == null) ? 0
                : usrgroupselect__.hashCode());
        result = prime * result + ((usrgrouptarget__ == null) ? 0
                : usrgrouptarget__.hashCode());
        return result;
    }
    /**
     *
     * <br>[機  能] 経路設定部分の比較 デフォルト選択設定部分
     * <br>[解  説] デフォルト選択設定以外の一致は考慮しない
     * <br>[備  考]
     * @param obj オブジェクト
     * @return デフォルト選択設定が一致するか
     */
    public boolean equalsKeiroPrefDefaultSelect(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Rng110KeiroDialogParamModel)) {
            return false;
        }
        Rng110KeiroDialogParamModel other = (Rng110KeiroDialogParamModel) obj;
        try {
            switch (EnumKeiroKbn.valueOf(keiroKbn__)) {
            case FREESET:
                if (usrgroupselect__ == null) {
                    if (other.usrgroupselect__ != null) {
                        return false;
                    }
                } else if (!usrgroupselect__.equals(other.usrgroupselect__)) {
                    return false;
                }
                break;
            case BOSSTARGET:
            default:
                break;
            }
        } catch (EnumOutRangeException e) {

        }

        return true;
    }
    /**
     *
     * <br>[機  能] 経路設定部分の比較 デフォルト選択設定以外
     * <br>[解  説] デフォルト選択設定の一致は考慮しない
     * <br>[備  考]
     * @param obj 比較対象
     * @return 経路設定部分が一致するか
     */
    public boolean equalsKeiroPrefLessDefaultSelect(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Rng110KeiroDialogParamModel)) {
            return false;
        }
        Rng110KeiroDialogParamModel other = (Rng110KeiroDialogParamModel) obj;
//      稟議経路名
        if (keiroName__ == null) {
            if (other.keiroName__ != null) {
                return false;
            }
        } else if (!keiroName__.equals(other.keiroName__)) {
            return false;
        }
//       経路種別
        if (keiroKbn__ != other.keiroKbn__) {
            return false;
        }
//      経路コメント
        if (keiroComment__ == null) {
            if (other.keiroComment__ != null) {
                return false;
            }
        } else if (!keiroComment__.equals(other.keiroComment__)) {
            return false;
        }
//     経路進行条件
        if (outcondition__ != other.outcondition__) {
            return false;
        }
//     経路進行条件閾値
        if (outcond_threshold__ == null) {
            if (other.outcond_threshold__ != null) {
                return false;
            }
        } else if (!outcond_threshold__.equals(other.outcond_threshold__)) {
            return false;
        }
//     審議無し進行許可フラグ
        if (nonuser__ != other.nonuser__) {
            return false;
        }
//    任意経路追加フラグ
        if (addkeiro__ != other.addkeiro__) {
            return false;
        }
//     後閲許可フラグ
        if (kouetu__ != other.kouetu__) {
            return false;
        }
//     後閲指示許可フラグ
        if (kouetuSiji__ != other.kouetuSiji__) {
            return false;
        }
//     自己審議許可フラグ
        if (own__ != other.own__) {
            return false;
        }
        try {
            switch (EnumKeiroKbn.valueOf(keiroKbn__)) {
            case FREESET:
                break;
            case USERTARGET:
//               経路スキップフラグ
                if (skip__ != other.skip__) {
                    return false;
                }
                break;
            case POSTARGET:
//              経路スキップフラグ
                if (skip__ != other.skip__) {
                    return false;
                }
                break;
            case USERSEL:
//              複数選択フラグ
                if (multisel__ != other.multisel__) {
                    return false;
                }

                break;
            case GROUPSEL:
//              複数選択フラグ
                if (multisel__ != other.multisel__) {
                    return false;
                }
                break;
            case BOSSTARGET:
//              上長階層数
                if (bossStepCntMin__ != other.bossStepCntMin__) {
                    return false;
                }
//              必須上長階層数
                if (bossStepCnt__ != other.bossStepCnt__) {
                    return false;
                }
                break;
            default:
            }
        } catch (EnumOutRangeException e) {

        }
        //経路使用条件
        if (inCond__ == null) {
            if (other.inCond__ != null) {
                return false;
            }
        } else {
            Set<String> thisKeySet =  new HashSet<>(inCond__.keySet());
            Set<String> otherKeySet =  new HashSet<>(other.inCond__.keySet());
            thisKeySet.remove("template");
            otherKeySet.remove("template");
            if (thisKeySet.size() != otherKeySet.size()) {
                return false;
            }
            for (String key : thisKeySet) {
                if (otherKeySet.contains(key)) {
                    otherKeySet.remove(key);
                } else {
                    return false;
                }
            }
            for (String key : otherKeySet) {
                if (!thisKeySet.contains(key)) {
                    return false;
                }
            }
            for (String key : thisKeySet) {
                KeiroInCondition thisInCond = inCond__.get(key);
                KeiroInCondition otherInCond = other.inCond__.get(key);
                if (!thisInCond.equals(otherInCond)) {
                    return false;
                }
            }
        }
        try {
            switch (EnumKeiroKbn.valueOf(keiroKbn__)) {
            case USERSEL:
                if (usrgroupselect__ == null) {
                    if (other.usrgroupselect__ != null) {
                        return false;
                    }
                } else if (!usrgroupselect__.equals(other.usrgroupselect__)) {
                    return false;
                }
                break;
            case USERTARGET:
                if (usrgrouptarget__ == null) {
                    if (other.usrgrouptarget__ != null) {
                        return false;
                    }
                } else if (!usrgrouptarget__.equals(other.usrgrouptarget__)) {
                    return false;
                }
                break;
            case POSTARGET:
                if (targetpos__ == null) {
                    if (other.targetpos__ != null) {
                        return false;
                    }
                } else {
                    Set<String> thisKeySet = new HashSet<>(targetpos__.keySet());
                    Set<String> otherKeySet =  new HashSet<>(other.targetpos__.keySet());
                    thisKeySet.remove("template");
                    otherKeySet.remove("template");
                    if (thisKeySet.size() != otherKeySet.size()) {
                        return false;
                    }
                    for (String key : thisKeySet) {
                        if (otherKeySet.contains(key)) {
                            otherKeySet.remove(key);
                        } else {
                            return false;
                        }
                    }
                    for (String key : otherKeySet) {
                        if (!thisKeySet.contains(key)) {
                            return false;
                        }
                    }
                    for (String key : thisKeySet) {
                        TargetPosSel thisInCond = targetpos__.get(key);
                        TargetPosSel otherInCond = other.targetpos__.get(key);
                        if (!thisInCond.equals(otherInCond)) {
                            return false;
                        }
                    }
                }
                break;
            case GROUPSEL:
                if (groupSel__ == null) {
                    if (other.groupSel__ != null) {
                        return false;
                    }
                } else if (!groupSel__.equals(other.groupSel__)) {
                    return false;
                }
                break;
            case FREESET:
            case BOSSTARGET:
            default:
                break;
            }
        } catch (EnumOutRangeException e) {

        }

        return true;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!equalsKeiroPrefLessDefaultSelect(obj)) {
            return false;
        }
        if (!equalsKeiroPrefDefaultSelect(obj)) {
            return false;
        }
        return true;
    }

    /**
     * <p>rtkSid を取得します。
     * @return rtkSid
     */
    public int getRtkSid() {
        return rtkSid__;
    }

    /**
     * <p>rtkSid をセットします。
     * @param rtkSid rtkSid
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
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
        keiroShareRange__ = keiroShareRange;
    }

    /**
     * <p>keiroComment を取得します。
     * @return keiroCommnet
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel#keiroComment__
     */
    public String getKeiroComment() {
        return keiroComment__;
    }

    /**
     * <p>keiroComment をセットします。
     * @param keiroCommnet keiroComment
     * @see jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel#keiroComment__
     */
    public void setKeiroComment(String keiroComment) {
        keiroComment__ = keiroComment;
    }

}