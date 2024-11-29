package jp.groupsession.v2.sml.sml390;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sml.sml380.Sml380ParamModel;
import jp.groupsession.v2.sml.ui.parts.account.SharedAccountSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] 送信先制限設定 追加編集画面　パラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml390ParamModel extends Sml380ParamModel {
    /** 初期表示 */
    private int sml390initFlg__ = 0;
    /** 送信先リスト名 */
    private String sml390sbcName__ = null;
    /** 備考 */
    private String sml390biko__ = null;
    /** 禁止送信先 ユーザ/グループ */
    private String[] sml390sbdTarget__ = null;
    /** 禁止送信先 選択グループ */
    private int sml390banGroup__  = -1;
    /** 禁止送信先 グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** 禁止送信先 ユーザ/グループ選択コンボ */
    private List<UsrLabelValueBean> sml390sbdTargetSelectCombo__  = null;
    /** 禁止送信先 選択UI */
    private UserGroupSelector sml390sbdTargetSelector__ = null;
    /** 禁止送信先 アカウント */
    private String[] sml390sbdTargetAcc__ = null;
    /** 禁止送信先 アカウント選択コンボ */
    private List<LabelValueBean> sml390sbdTargetAccSelectCombo__  = null;
    /** 禁止送信先 アカウント選択UI */
    private SharedAccountSelector sml390sbdTargetAccSelector__ = null;

    /** 許可ユーザ・グループ */
    private String[] sml390sbpTarget__ = null;
    /** 許可ユーザ 選択グループ */
    private int sml390ableGroup__  = -1;
    /** 許可ユーザ 選択コンボ */
    private List<UsrLabelValueBean> sml390sbpTargetSelectCombo__  = null;
    /** 許可ユーザ 選択UI */
    private UserGroupSelector sml390sbpTargetSelector__ = null;
    /** 役職 */
    private int sml390post__  = -1;
    /** 役職コンボ */
    private List<LabelValueBean> postCombo__ = null;

    /**
     * <p>sml390initFlg を取得します。
     * @return sml390initFlg
     */
    public int getSml390initFlg() {
        return sml390initFlg__;
    }
    /**
     * <p>sml390initFlg をセットします。
     * @param sml390initFlg sml390initFlg
     */
    public void setSml390initFlg(int sml390initFlg) {
        sml390initFlg__ = sml390initFlg;
    }
    /**
     * <p>sml390sbcName を取得します。
     * @return sml390sbcName
     */
    public String getSml390sbcName() {
        return sml390sbcName__;
    }
    /**
     * <p>sml390sbcName をセットします。
     * @param sml390sbcName sml390sbcName
     */
    public void setSml390sbcName(String sml390sbcName) {
        sml390sbcName__ = sml390sbcName;
    }
    /**
     * <p>sml390biko を取得します。
     * @return sml390biko
     */
    public String getSml390biko() {
        return sml390biko__;
    }
    /**
     * <p>sml390biko をセットします。
     * @param sml390biko sml390biko
     */
    public void setSml390biko(String sml390biko) {
        sml390biko__ = sml390biko;
    }
    /**
     * <p>sml390sbdTarget を取得します。
     * @return sml390sbdTarget
     */
    public String[] getSml390sbdTarget() {
        return sml390sbdTarget__;
    }
    /**
     * <p>sml390sbdTarget をセットします。
     * @param sml390sbdTarget sml390sbdTarget
     */
    public void setSml390sbdTarget(String[] sml390sbdTarget) {
        sml390sbdTarget__ = sml390sbdTarget;
    }
    /**
     * <p>sml390sbpTarget を取得します。
     * @return sml390sbpTarget
     */
    public String[] getSml390sbpTarget() {
        return sml390sbpTarget__;
    }
    /**
     * <p>sml390sbpTarget をセットします。
     * @param sml390sbpTarget sml390sbpTarget
     */
    public void setSml390sbpTarget(String[] sml390sbpTarget) {
        sml390sbpTarget__ = sml390sbpTarget;
    }
    /**
     * <p>sml390ableGroup を取得します。
     * @return sml390ableGroup
     */
    public int getSml390ableGroup() {
        return sml390ableGroup__;
    }
    /**
     * <p>sml390ableGroup をセットします。
     * @param sml390ableGroup sml390ableGroup
     */
    public void setSml390ableGroup(int sml390ableGroup) {
        sml390ableGroup__ = sml390ableGroup;
    }
    /**
     * <p>sml390banGroup を取得します。
     * @return sml390banGroup
     */
    public int getSml390banGroup() {
        return sml390banGroup__;
    }
    /**
     * <p>sml390banGroup をセットします。
     * @param sml390banGroup sml390banGroup
     */
    public void setSml390banGroup(int sml390banGroup) {
        sml390banGroup__ = sml390banGroup;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>sml390sbpTargetSelectCombo を取得します。
     * @return sml390sbpTargetSelectCombo
     */
    public List<UsrLabelValueBean> getSml390sbpTargetSelectCombo() {
        return sml390sbpTargetSelectCombo__;
    }
    /**
     * <p>sml390sbpTargetSelectCombo をセットします。
     * @param sml390sbpTargetSelectCombo sml390sbpTargetSelectCombo
     */
    public void setSml390sbpTargetSelectCombo(
            List<UsrLabelValueBean> sml390sbpTargetSelectCombo) {
        sml390sbpTargetSelectCombo__ = sml390sbpTargetSelectCombo;
    }
    /**
     * <p>sml390sbdTargetSelector__ を取得します。
     * @return sml390sbdTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390ParamModel#sml390sbdTargetSelector__
     */
    public UserGroupSelector getSml390sbdTargetSelector() {
        return sml390sbdTargetSelector__;
    }
    /**
     * <p>sml390sbdTargetSelector__ をセットします。
     * @param sml390sbdTargetSelector sml390sbdTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390ParamModel#sml390sbdTargetSelector__
     */
    public void setSml390sbdTargetSelector(UserGroupSelector sml390sbdTargetSelector) {
        this.sml390sbdTargetSelector__ = sml390sbdTargetSelector;
    }
    /**
     * <p>sml390post を取得します。
     * @return sml390post
     */
    public int getSml390post() {
        return sml390post__;
    }
    /**
     * <p>sml390post をセットします。
     * @param sml390post sml390post
     */
    public void setSml390post(int sml390post) {
        sml390post__ = sml390post;
    }
    /**
     * <p>postCombo を取得します。
     * @return postCombo
     */
    public List<LabelValueBean> getPostCombo() {
        return postCombo__;
    }
    /**
     * <p>postCombo をセットします。
     * @param postCombo postCombo
     */
    public void setPostCombo(List<LabelValueBean> postCombo) {
        postCombo__ = postCombo;
    }
    /**
     * <p>sml390sbdTargetSelectCombo を取得します。
     * @return sml390sbdTargetSelectCombo
     */
    public List<UsrLabelValueBean> getSml390sbdTargetSelectCombo() {
        return sml390sbdTargetSelectCombo__;
    }
    /**
     * <p>sml390sbdTargetSelectCombo をセットします。
     * @param sml390sbdTargetSelectCombo sml390sbdTargetSelectCombo
     */
    public void setSml390sbdTargetSelectCombo(
            List<UsrLabelValueBean> sml390sbdTargetSelectCombo) {
        sml390sbdTargetSelectCombo__ = sml390sbdTargetSelectCombo;
    }
    /**
     * <p>sml390sbdTargetAcc を取得します。
     * @return sml390sbdTargetAcc
     */
    public String[] getSml390sbdTargetAcc() {
        return sml390sbdTargetAcc__;
    }
    /**
     * <p>sml390sbdTargetAcc をセットします。
     * @param sml390sbdTargetAcc sml390sbdTargetAcc
     */
    public void setSml390sbdTargetAcc(String[] sml390sbdTargetAcc) {
        sml390sbdTargetAcc__ = sml390sbdTargetAcc;
    }
    /**
     * <p>sml390sbdTargetAccSelectCombo を取得します。
     * @return sml390sbdTargetAccSelectCombo
     */
    public List<LabelValueBean> getSml390sbdTargetAccSelectCombo() {
        return sml390sbdTargetAccSelectCombo__;
    }
    /**
     * <p>sml390sbdTargetAccSelectCombo をセットします。
     * @param sml390sbdTargetAccSelectCombo sml390sbdTargetAccSelectCombo
     */
    public void setSml390sbdTargetAccSelectCombo(
            List<LabelValueBean> sml390sbdTargetAccSelectCombo) {
        sml390sbdTargetAccSelectCombo__ = sml390sbdTargetAccSelectCombo;
    }
    /**
     * <p>sml390sbdTargetAccSelector__ を取得します。
     * @return sml390sbdTargetAccSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390ParamModel#sml390sbdTargetAccSelector__
     */
    public SharedAccountSelector getSml390sbdTargetAccSelector() {
        return sml390sbdTargetAccSelector__;
    }
    /**
     * <p>sml390sbdTargetAccSelector__ をセットします。
     * @param sml390sbdTargetAccSelector sml390sbdTargetAccSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390ParamModel#sml390sbdTargetAccSelector__
     */
    public void setSml390sbdTargetAccSelector(
            SharedAccountSelector sml390sbdTargetAccSelector) {
        this.sml390sbdTargetAccSelector__ = sml390sbdTargetAccSelector;
    }
    /**
     * <p>sml390sbpTargetSelector__ を取得します。
     * @return sml390sbpTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390ParamModel#sml390sbpTargetSelector__
     */
    public UserGroupSelector getSml390sbpTargetSelector() {
        return sml390sbpTargetSelector__;
    }
    /**
     * <p>sml390sbpTargetSelector__ をセットします。
     * @param sml390sbpTargetSelector sml390sbpTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390ParamModel#sml390sbpTargetSelector__
     */
    public void setSml390sbpTargetSelector(UserGroupSelector sml390sbpTargetSelector) {
        this.sml390sbpTargetSelector__ = sml390sbpTargetSelector;
    }

}
