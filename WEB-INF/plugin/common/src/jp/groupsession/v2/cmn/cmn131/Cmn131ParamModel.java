package jp.groupsession.v2.cmn.cmn131;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] メイン 個人設定 マイグループ登録画面のパラメータ、出力値を格納Modelするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn131ParamModel extends AbstractParamModel {

    //入力項目
    /** マイグループ名 */
    private String cmn131name__ = null;
    /** メモ */
    private String cmn131memo__ = null;
    /** グループ */
    private String cmn131groupSid__ = null;
    /** 追加用メンバー(選択中) */
    private String[] cmn131addUserSid__ = null;
    /** 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] cmn131userSid__ = null;
    /** 現在選択中のメンバー(コンボで選択中) */
    private String[] cmn131selectUserSid__ = null;
    /** 追加参照権限 グループ(選択中) */
    private String cmn131refGroupSid__ = null;
    /** 追加参照権限 追加用メンバー(選択中) */
    private String[] cmn131refAddUserSid__ = null;
    /** 追加参照権限 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] cmn131refUserSid__ = null;
    /** 追加参照権限 現在選択中のメンバー(コンボで選択中) */
    private String[] cmn131refSelectUserSid__ = null;

    //表示項目
    /** 画面タイトル */
    private String cmn131dspTitle__ = null;
    /** 現在選択中のメンバーコンボ */
    private List<UsrLabelValueBean> cmn131MbLabelList__ = null;
    /** 追加参照権限 選択グループのメンバーコンボ */
    private List<UsrLabelValueBean> cmn131refMbLabelList__ = null;
    /** メンバー 選択UI*/
    private UserGroupSelector cmn131userSidUI__ = null;
    /** 共有 選択UI*/
    private UserGroupSelector cmn131refUserSidUI__ = null;

    //非表示項目
    /** 処理モード */
    private String cmn130cmdMode__ = null;
    /** マイグループSID */
    private String cmn130selectGroupSid__ = null;
    /** 初期表示フラグ */
    private int cmn131initFlg__ = 0;

    /**
     * <p>cmn131groupSid を取得します。
     * @return cmn131groupSid
     */
    public String getCmn131groupSid() {
        return cmn131groupSid__;
    }

    /**
     * <p>cmn131groupSid をセットします。
     * @param cmn131groupSid cmn131groupSid
     */
    public void setCmn131groupSid(String cmn131groupSid) {
        cmn131groupSid__ = cmn131groupSid;
    }

    /**
     * <p>cmn131userSid を取得します。
     * @return cmn131userSid
     */
    public String[] getCmn131userSid() {
        return cmn131userSid__;
    }

    /**
     * <p>cmn131userSid をセットします。
     * @param cmn131userSid cmn131userSid
     */
    public void setCmn131userSid(String[] cmn131userSid) {
        cmn131userSid__ = cmn131userSid;
    }

    /**
     * <p>cmn131MbLabelList を取得します。
     * @return cmn131MbLabelList
     */
    public List<UsrLabelValueBean> getCmn131MbLabelList() {
        return cmn131MbLabelList__;
    }

    /**
     * <p>cmn131MbLabelList をセットします。
     * @param cmn131MbLabelList cmn131MbLabelList
     */
    public void setCmn131MbLabelList(List<UsrLabelValueBean> cmn131MbLabelList) {
        cmn131MbLabelList__ = cmn131MbLabelList;
    }

    /**
     * <p>cmn131refMbLabelList を取得します。
     * @return cmn131refMbLabelList
     */
    public List<UsrLabelValueBean> getCmn131refMbLabelList() {
        return cmn131refMbLabelList__;
    }

    /**
     * <p>cmn131refMbLabelList をセットします。
     * @param cmn131refMbLabelList cmn131refMbLabelList
     */
    public void setCmn131refMbLabelList(
            List<UsrLabelValueBean> cmn131refMbLabelList) {
        cmn131refMbLabelList__ = cmn131refMbLabelList;
    }

    /**
     * <p>cmn131addUserSid を取得します。
     * @return cmn131addUserSid
     */
    public String[] getCmn131addUserSid() {
        return cmn131addUserSid__;
    }

    /**
     * <p>cmn131addUserSid をセットします。
     * @param cmn131addUserSid cmn131addUserSid
     */
    public void setCmn131addUserSid(String[] cmn131addUserSid) {
        cmn131addUserSid__ = cmn131addUserSid;
    }

    /**
     * <p>cmn131selectUserSid を取得します。
     * @return cmn131selectUserSid
     */
    public String[] getCmn131selectUserSid() {
        return cmn131selectUserSid__;
    }

    /**
     * <p>cmn131selectUserSid をセットします。
     * @param cmn131selectUserSid cmn131selectUserSid
     */
    public void setCmn131selectUserSid(String[] cmn131selectUserSid) {
        cmn131selectUserSid__ = cmn131selectUserSid;
    }

    /**
     * <p>cmn131refGroupSid を取得します。
     * @return cmn131refGroupSid
     */
    public String getCmn131refGroupSid() {
        return cmn131refGroupSid__;
    }

    /**
     * <p>cmn131refGroupSid をセットします。
     * @param cmn131refGroupSid cmn131refGroupSid
     */
    public void setCmn131refGroupSid(String cmn131refGroupSid) {
        cmn131refGroupSid__ = cmn131refGroupSid;
    }

    /**
     * <p>cmn131refAddUserSid を取得します。
     * @return cmn131refAddUserSid
     */
    public String[] getCmn131refAddUserSid() {
        return cmn131refAddUserSid__;
    }

    /**
     * <p>cmn131refAddUserSid をセットします。
     * @param cmn131refAddUserSid cmn131refAddUserSid
     */
    public void setCmn131refAddUserSid(String[] cmn131refAddUserSid) {
        cmn131refAddUserSid__ = cmn131refAddUserSid;
    }

    /**
     * <p>cmn131refUserSid を取得します。
     * @return cmn131refUserSid
     */
    public String[] getCmn131refUserSid() {
        return cmn131refUserSid__;
    }

    /**
     * <p>cmn131refUserSid をセットします。
     * @param cmn131refUserSid cmn131refUserSid
     */
    public void setCmn131refUserSid(String[] cmn131refUserSid) {
        cmn131refUserSid__ = cmn131refUserSid;
    }

    /**
     * <p>cmn131refSelectUserSid を取得します。
     * @return cmn131refSelectUserSid
     */
    public String[] getCmn131refSelectUserSid() {
        return cmn131refSelectUserSid__;
    }

    /**
     * <p>cmn131refSelectUserSid をセットします。
     * @param cmn131refSelectUserSid cmn131refSelectUserSid
     */
    public void setCmn131refSelectUserSid(String[] cmn131refSelectUserSid) {
        cmn131refSelectUserSid__ = cmn131refSelectUserSid;
    }

    /**
     * <p>cmn131memo を取得します。
     * @return cmn131memo
     */
    public String getCmn131memo() {
        return cmn131memo__;
    }

    /**
     * <p>cmn131memo をセットします。
     * @param cmn131memo cmn131memo
     */
    public void setCmn131memo(String cmn131memo) {
        cmn131memo__ = cmn131memo;
    }

    /**
     * <p>cmn131name を取得します。
     * @return cmn131name
     */
    public String getCmn131name() {
        return cmn131name__;
    }

    /**
     * <p>cmn131name をセットします。
     * @param cmn131name cmn131name
     */
    public void setCmn131name(String cmn131name) {
        cmn131name__ = cmn131name;
    }

    /**
     * <p>cmn131userSidUI を取得します。
     * @return cmn131userSidUI
     * @see jp.groupsession.v2.cmn.cmn131.Cmn131ParamModel#cmn131userSidUI__
     */
    public UserGroupSelector getCmn131userSidUI() {
        return cmn131userSidUI__;
    }

    /**
     * <p>cmn131userSidUI をセットします。
     * @param cmn131userSidUI cmn131userSidUI
     * @see jp.groupsession.v2.cmn.cmn131.Cmn131ParamModel#cmn131userSidUI__
     */
    public void setCmn131userSidUI(UserGroupSelector cmn131userSidUI) {
        cmn131userSidUI__ = cmn131userSidUI;
    }

    /**
     * <p>cmn131refUserSidUI を取得します。
     * @return cmn131refUserSidUI
     * @see jp.groupsession.v2.cmn.cmn131.Cmn131ParamModel#cmn131refUserSidUI__
     */
    public UserGroupSelector getCmn131refUserSidUI() {
        return cmn131refUserSidUI__;
    }

    /**
     * <p>cmn131refUserSidUI をセットします。
     * @param cmn131refUserSidUI cmn131refUserSidUI
     * @see jp.groupsession.v2.cmn.cmn131.Cmn131ParamModel#cmn131refUserSidUI__
     */
    public void setCmn131refUserSidUI(UserGroupSelector cmn131refUserSidUI) {
        cmn131refUserSidUI__ = cmn131refUserSidUI;
    }

    /**
     * <p>cmn130cmdMode を取得します。
     * @return cmn130cmdMode
     */
    public String getCmn130cmdMode() {
        return cmn130cmdMode__;
    }

    /**
     * <p>cmn130cmdMode をセットします。
     * @param cmn130cmdMode cmn130cmdMode
     */
    public void setCmn130cmdMode(String cmn130cmdMode) {
        cmn130cmdMode__ = cmn130cmdMode;
    }

    /**
     * <p>cmn130selectGroupSid を取得します。
     * @return cmn130selectGroupSid
     */
    public String getCmn130selectGroupSid() {
        return cmn130selectGroupSid__;
    }

    /**
     * <p>cmn130selectGroupSid をセットします。
     * @param cmn130selectGroupSid cmn130selectGroupSid
     */
    public void setCmn130selectGroupSid(String cmn130selectGroupSid) {
        cmn130selectGroupSid__ = cmn130selectGroupSid;
    }

    /**
     * <p>cmn131dspTitle を取得します。
     * @return cmn131dspTitle
     */
    public String getCmn131dspTitle() {
        return cmn131dspTitle__;
    }

    /**
     * <p>cmn131dspTitle をセットします。
     * @param cmn131dspTitle cmn131dspTitle
     */
    public void setCmn131dspTitle(String cmn131dspTitle) {
        cmn131dspTitle__ = cmn131dspTitle;
    }

    /**
     * <p>cmn131initFlg を取得します。
     * @return cmn131initFlg
     */
    public int getCmn131initFlg() {
        return cmn131initFlg__;
    }

    /**
     * <p>cmn131initFlg をセットします。
     * @param cmn131initFlg cmn131initFlg
     */
    public void setCmn131initFlg(int cmn131initFlg) {
        cmn131initFlg__ = cmn131initFlg;
    }

}