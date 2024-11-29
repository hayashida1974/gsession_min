package jp.groupsession.v2.sml.sml260kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] ショートメール アカウントの使用者に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260knUseUsrModel {

    /** アカウント名 */
    private String accountName__ = null;
    /** ユーザ名 */
    private List<CmnUsrmInfModel> userNameList__ = null;
    /** グループ名 */
    private List<CmnGroupmModel> groupNameList__ = null;

    /**
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }

    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }

    /**
     * <p>userNameList を取得します。
     * @return userNameList
     */
    public List<CmnUsrmInfModel> getUserNameList() {
        return userNameList__;
    }

    /**
     * <p>userNameList をセットします。
     * @param userNameList userNameList
     */
    public void setUserNameList(List<CmnUsrmInfModel> userNameList) {
        userNameList__ = userNameList;
    }

    /**
     * <p>groupNameList を取得します。
     * @return groupNameList
     */
    public List<CmnGroupmModel> getGroupNameList() {
        return groupNameList__;
    }

    /**
     * <p>groupNameList をセットします。
     * @param groupNameList groupNameList
     */
    public void setGroupNameList(List<CmnGroupmModel> groupNameList) {
        groupNameList__ = groupNameList;
    }
}
