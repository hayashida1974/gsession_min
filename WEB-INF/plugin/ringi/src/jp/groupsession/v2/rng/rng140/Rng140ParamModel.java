package jp.groupsession.v2.rng.rng140;


import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rng.rng150.Rng150ParamModel;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140ParamModel extends Rng150ParamModel {
    //入力項目
    /** カテゴリ名 */
    private String rng140CatName__ = "";

    /** カテゴリSID */
    private int rng140CatSid__ = 0;
    /** 状態区分 0=登録  1=編集 */
    private int rng140ProcMode__ = 0;
    /** 遷移元フラグ */
    private int rng140SeniFlg__ = 0;


    //管理者権限
    /** カテゴリ管理者ユーザ */
    private UserGroupSelectModel rng140UserAdmList__ = new UserGroupSelectModel();
    /** カテゴリ管理者ユーザ 選択UI */
    private UserGroupSelector rng140UserAdmSelector__ = null;

    //使用制限
    /** カテゴリ使用制限 */
    private int rmg140UserLimit__ = 0;

    //使用制限ユーザ・グループ
    /** 制限するユーザを指定：0 許可するユーザを指定：1 */
    private int rng140UserLimitType__ = 0;
    /** 使用制限ユーザ */
    private UserGroupSelectModel rng140UserLimitList__ = new UserGroupSelectModel();
    /** 使用制限ユーザ 選択UI*/
    private UserGroupSelector rng140UserLimitSelector__ = null;

    /**
     * <p>rng140UserAdmSelector を取得します。
     * @return rng140UserAdmSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140ParamModel#rng140UserAdmSelector__
     */
    public UserGroupSelector getRng140UserAdmSelector() {
        return rng140UserAdmSelector__;
    }

    /**
     * <p>rng140UserAdmSelector をセットします。
     * @param rng140UserAdmSelector rng140UserAdmSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140ParamModel#rng140UserAdmSelector__
     */
    public void setRng140UserAdmSelector(UserGroupSelector rng140UserAdmSelector) {
        rng140UserAdmSelector__ = rng140UserAdmSelector;
    }

    /**
     * <p>rng140UserLimitSelector を取得します。
     * @return rng140UserLimitSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140ParamModel#rng140UserLimitSelector__
     */
    public UserGroupSelector getRng140UserLimitSelector() {
        return rng140UserLimitSelector__;
    }

    /**
     * <p>rng140UserLimitSelector をセットします。
     * @param rng140UserLimitSelector rng140UserLimitSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140ParamModel#rng140UserLimitSelector__
     */
    public void setRng140UserLimitSelector(
            UserGroupSelector rng140UserLimitSelector) {
        rng140UserLimitSelector__ = rng140UserLimitSelector;
    }

    /**
     * @return rng140CatName
     */
    public String getRng140CatName() {
        return rng140CatName__;
    }

    /**
     * @param rng140CatName セットする rng140CatName
     */
    public void setRng140CatName(String rng140CatName) {
        rng140CatName__ = rng140CatName;
    }

    /**
     * @return rng140CatSid
     */
    public int getRng140CatSid() {
        return rng140CatSid__;
    }

    /**
     * @param rng140CatSid セットする rng140CatSid
     */
    public void setRng140CatSid(int rng140CatSid) {
        rng140CatSid__ = rng140CatSid;
    }

    /**
     * @return rng140ProcMode
     */
    public int getRng140ProcMode() {
        return rng140ProcMode__;
    }

    /**
     * @param rng140ProcMode 設定する rng140ProcMode
     */
    public void setRng140ProcMode(int rng140ProcMode) {
        rng140ProcMode__ = rng140ProcMode;
    }

    /**
     * @return rng140SeniFlg
     */
    public int getRng140SeniFlg() {
        return rng140SeniFlg__;
    }

    /**
     * @param rng140SeniFlg 設定する rng140SeniFlg
     */
    public void setRng140SeniFlg(int rng140SeniFlg) {
        rng140SeniFlg__ = rng140SeniFlg;
    }


    /**
     * <p>rng140UserAdmList を取得します。
     * @return rng140UserAdmList
     */
    public UserGroupSelectModel getRng140UserAdmList() {
        return rng140UserAdmList__;
    }

    /**
     * <p>rng140UserAdmList をセットします。
     * @param rng140UserAdmList rng140UserAdmList
     */
    public void setRng140UserAdmList(UserGroupSelectModel rng140UserAdmList) {
        rng140UserAdmList__ = rng140UserAdmList;
    }

    /**
     * <p>rmg140UserLimit を取得します。
     * @return rmg140UserLimit
     */
    public int getRng140UserLimit() {
        return rmg140UserLimit__;
    }

    /**
     * <p>rmg140UserLimit をセットします。
     * @param rng140UserLimit rng140UserLimit
     */
    public void setRng140UserLimit(int rng140UserLimit) {
        this.rmg140UserLimit__ = rng140UserLimit;
    }

    /**
     * <p>rng140UserLimitType を取得します。
     * @return rng140UserLimitType
     */
    public int getRng140UserLimitType() {
        return rng140UserLimitType__;
    }

    /**
     * <p>rng140UserLimitType をセットします。
     * @param rng140UserLimitType rng140UserLimitType
     */
    public void setRng140UserLimitType(int rng140UserLimitType) {
        rng140UserLimitType__ = rng140UserLimitType;
    }

    /**
     * <p>rng140UserLimitList を取得します。
     * @return rng140UserLimitList
     */
    public UserGroupSelectModel getRng140UserLimitList() {
        return rng140UserLimitList__;
    }

    /**
     * <p>rng140UserLimitList をセットします。
     * @param rng140UserLimitList rng140UserLimitList
     */
    public void setRng140UserLimitList(UserGroupSelectModel rng140UserLimitList) {
        rng140UserLimitList__ = rng140UserLimitList;
    }
}