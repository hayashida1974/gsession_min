package jp.groupsession.v2.man.man210;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 モバイル使用一括設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man210ParamModel extends AbstractParamModel {
    /** 対象区分 0 = 全 1 = 指定 */
    private int man210ObjKbn__ = 0;
    /** モバイル使用区分 0 = 可 1 = 不可 */
    private int man210UseKbn__ = 0;
    /** 個体識別番号制御 */
    private String man210NumCont__ = String.valueOf(GSConstUser.UID_DOESNT_CONTROL);
    /** 個体識別番号自動登録 */
    private String man210NumAutAdd__ = String.valueOf(GSConstUser.UID_AUTO_REG_NO);

    /** グループ */
    private String man210groupSid__ = null;
    /** 対象 メンバー */
    private String[] man210userSid__ = null;
    /** 対象 メンバー UI */
    private UserGroupSelector man210userSidUI__ = null;

    /**
     * <p>man210NumAutAdd を取得します。
     * @return man210NumAutAdd
     */
    public String getMan210NumAutAdd() {
        return man210NumAutAdd__;
    }
    /**
     * <p>man210NumAutAdd をセットします。
     * @param man210NumAutAdd man210NumAutAdd
     */
    public void setMan210NumAutAdd(String man210NumAutAdd) {
        man210NumAutAdd__ = man210NumAutAdd;
    }
    /**
     * <p>man210NumCont を取得します。
     * @return man210NumCont
     */
    public String getMan210NumCont() {
        return man210NumCont__;
    }
    /**
     * <p>man210NumCont をセットします。
     * @param man210NumCont man210NumCont
     */
    public void setMan210NumCont(String man210NumCont) {
        man210NumCont__ = man210NumCont;
    }
    /**
     * <p>man210ObjKbn を取得します。
     * @return man210ObjKbn
     */
    public int getMan210ObjKbn() {
        return man210ObjKbn__;
    }
    /**
     * <p>man210ObjKbn をセットします。
     * @param man210ObjKbn man210ObjKbn
     */
    public void setMan210ObjKbn(int man210ObjKbn) {
        man210ObjKbn__ = man210ObjKbn;
    }
    /**
     * <p>man210UseKbn を取得します。
     * @return man210UseKbn
     */
    public int getMan210UseKbn() {
        return man210UseKbn__;
    }
    /**
     * <p>man210UseKbn をセットします。
     * @param man210UseKbn man210UseKbn
     */
    public void setMan210UseKbn(int man210UseKbn) {
        man210UseKbn__ = man210UseKbn;
    }
    /**
     * <p>man210groupSid を取得します。
     * @return man210groupSid
     */
    public String getMan210groupSid() {
        return man210groupSid__;
    }
    /**
     * <p>man210groupSid をセットします。
     * @param man210groupSid man210groupSid
     */
    public void setMan210groupSid(String man210groupSid) {
        man210groupSid__ = man210groupSid;
    }
    /**
     * <p>man210userSid を取得します。
     * @return man210userSid
     */
    public String[] getMan210userSid() {
        return man210userSid__;
    }
    /**
     * <p>man210userSid をセットします。
     * @param man210userSid man210userSid
     */
    public void setMan210userSid(String[] man210userSid) {
        man210userSid__ = man210userSid;
    }
    /**
     * <p>man210userSidUI を取得します。
     * @return man210userSidUI
     */
    public UserGroupSelector getMan210userSidUI() {
        return man210userSidUI__;
    }
    /**
     * <p>man210userSidUI をセットします。
     * @param man210userSidUI man210userSidUI
     */
    public void setMan210userSidUI(UserGroupSelector man210userSidUI) {
        man210userSidUI__ = man210userSidUI;
    }
}