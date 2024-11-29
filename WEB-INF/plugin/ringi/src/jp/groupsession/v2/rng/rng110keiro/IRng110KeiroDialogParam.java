package jp.groupsession.v2.rng.rng110keiro;

import java.util.Map;
import java.util.TreeMap;

import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
/**
 *
 * <br>[機  能] パラメータのインタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IRng110KeiroDialogParam {

    /**ドラッグ要素 区分 汎用要素*/
    int DRAG_CONTENT_KBN_BASIC = 0;
    /**ドラッグ要素 区分 ユーザ指定要素*/
    int DRAG_CONTENT_KBN_USER = 1;
    /**ドラッグ要素 区分 グループ指定要素*/
    int DRAG_CONTENT_KBN_GROUP = 2;
    /**ドラッグ要素 区分 役職指定要素*/
    int DRAG_CONTENT_KBN_POS = 3;

    /**
     * <p>keiroKbn を取得します。
     * @return keiroKbn
     */
    public abstract int getKeiroKbn();

    /**
     * <p>keiroKbn をセットします。
     * @param keiroKbn keiroKbn
     */
    public abstract void setKeiroKbn(int keiroKbn);

    /**
     * <p>keiroName を取得します。
     * @return keiroName
     */
    public abstract String getKeiroName();

    /**
     * <p>keiroName をセットします。
     * @param keiroName keiroName
     */
    public abstract void setKeiroName(String keiroName);

    /**
     * <p>outcondition を取得します。
     * @return outcondition
     */
    public abstract int getOutcondition();

    /**
     * <p>outcondition をセットします。
     * @param outcondition outcondition
     */
    public abstract void setOutcondition(int outcondition);

    /**
     * <p>outcond_threshold を取得します。
     * @return outcond_threshold
     */
    public abstract String getOutcond_threshold();

    /**
     * <p>outcond_threshold をセットします。
     * @param threshold outcond_threshold
     */
    public abstract void setOutcond_threshold(String threshold);

    /**
     * <p>usrgroupselect を取得します。
     * @return usrgroupselect
     */
    public abstract UserGroupSelectModel getUsrgroupselect();

    /**
     * <p>usrgroupselect をセットします。
     * @param usrgroupselect usrgroupselect
     */
    public abstract void setUsrgroupselect(UserGroupSelectModel usrgroupselect);

    /**
     * <p>targetpos を取得します。
     * @return targetpos
     */
    public abstract TreeMap<String, TargetPosSel> getTargetposMap();

    /**
     * <p>targetpos をセットします。
     * @param targetpos targetpos
     */
    public abstract void setTargetposMap(TreeMap<String, TargetPosSel> targetpos);

    /**
     * <p>targetpos を取得します。
     * @param key インデックス
     * @return targetpos
     */
    public abstract TargetPosSel getTargetpos(String key);

    /**
     * <p>targetpos をセットします。
     * @param key インデックス
     * @param posInf 役職設定
     */
    public abstract void setTargetpos(String key, TargetPosSel posInf);

    /**
     * <p>skip を取得します。
     * @return skip
     */
    public abstract int getSkip();

    /**
     * <p>skip をセットします。
     * @param skip skip
     */
    public abstract void setSkip(int skip);

    /**
     * <p>multisel を取得します。
     * @return multisel
     */
    public abstract int getMultisel();

    /**
     * <p>multisel をセットします。
     * @param multisel multisel
     */
    public abstract void setMultisel(int multisel);

    /**
     * <p>own を取得します。
     * @return own
     */
    public abstract int getOwn();

    /**
     * <p>own をセットします。
     * @param own own
     */
    public abstract void setOwn(int own);

    /**
     * <p>nonuser を取得します。
     * @return nonuser
     */
    public abstract int getNonuser();

    /**
     * <p>nonuser をセットします。
     * @param nonuser nonuser
     */
    public abstract void setNonuser(int nonuser);

    /**
     * <p>addkeiro を取得します。
     * @return addkeiro
     */
    public abstract int getAddkeiro();

    /**
     * <p>addkeiro をセットします。
     * @param addkeiro addkeiro
     */
    public abstract void setAddkeiro(int addkeiro);

    /**
     * <p>kouetu を取得します。
     * @return kouetu
     */
    public abstract int getKouetu();

    /**
     * <p>kouetu をセットします。
     * @param kouetu kouetu
     */
    public abstract void setKouetu(int kouetu);

    /**
     * <p>inCond を取得します。
     * @return inCond
     */
    public abstract Map<String, KeiroInCondition> getInCondMap();

    /**
     * <p>inCond を取得します。
     * @param key キー
     * @return inCond
     */
    public abstract KeiroInCondition getInCond(String key);

    /**
     * <p>inCond をセットします。
     * @param key キー
     * @param inCond inCond
     */
    public abstract void setInCond(String key, KeiroInCondition inCond);

    /**
     * <p>inCond をセットします。
     * @param inCond inCond
     */
    public abstract void setInCondMap(Map<String, KeiroInCondition> inCond);

    /**
     * <p>groupSel を取得します。
     * @return groupSel
     */
    public abstract GroupComboModel getGroupSel();

    /**
     * <p>groupSel をセットします。
     * @param groupSel groupSel
     */
    public abstract void setGroupSel(GroupComboModel groupSel);
    /**
     * <p>bossStepCnt を取得します。
     * @return bossStepCnt
     */
    public abstract int getBossStepCnt();

    /**
     * <p>bossStepCnt をセットします。
     * @param bossStepCnt bossStepCnt
     */
    public abstract void setBossStepCnt(int bossStepCnt);


    /**
     * <p>bossStepCntMin を取得します。
     * @return bossStepCntMin
     */
    public abstract  int getBossStepCntMin();

    /**
     * <p>bossStepCntMin をセットします。
     * @param bossStepCntMin bossStepCntMin
     */
    public abstract void setBossStepCntMin(int bossStepCntMin);
    /**
     * <p>usrgrouptarget を取得します。
     * @return usrgrouptarget
     */
    public abstract UserGroupSelectModel getUsrgrouptarget();
    /**
     * <p>usrgrouptarget をセットします。
     * @param usrgrouptarget usrgrouptarget
     */
    public abstract void setUsrgrouptarget(UserGroupSelectModel usrgrouptarget);
    /**
     * <p>dragContentKbn を取得します。
     * @return usrgrouptarget
     */
    public abstract int getDragContentKbn();
    /**
     * <p>DragContentKbn をセットします。
     * @param dragContentKbn dragContentKbn
     */
    public abstract void setDragContentKbn(int dragContentKbn);
    /**
     * <p>keiroRootType を取得します。
     * @return keiroRootType
     */
    public abstract int getKeiroRootType();

    /**
     * <p>keiroRootType をセットします。
     * @param keiroRootType keiroRootType
     */
    public abstract void setKeiroRootType(int keiroRootType);
    /**
     * <p>kouetuSiji を取得します。
     * @return kouetuSiji
     */
    public abstract int getKouetuSiji();

    /**
     * <p>kouetuSiji をセットします。
     * @param kouetuSiji kouetuSiji
     */
    public abstract void setKouetuSiji(int kouetuSiji);

    /**
     * <p>keiroComment を取得します。
     * @return kouetuSiji
     */
    public abstract String getKeiroComment();

    /**
     * <p>keiroComment をセットします。
     * @param keiroComment keiroComment
     */
    public abstract void setKeiroComment(String keiroComment);

}