package jp.groupsession.v2.wml.model;

import java.util.List;

/**
 * <br>[機  能] 送信先リスト情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlDestListInfoModel {

    /** 送信先リスト(編集対象) */
    private int editDestList__ = 0;
    
    /** 送信先リスト名 */
    private String destListName__ = null;
    /** 備考 */
    private String destListBiko__ = null;
    
    /** 送信先 ユーザ情報 */
    private String[] destUsers__ = null;
    /** 送信先 アドレス帳情報 */
    private String[] destAddresses__ = null;

    /** アクセス権限 編集ユーザ */
    private String[] accessFullUsers__ = null;
    /** アクセス権限 閲覧ユーザ */
    private String[] accessReadUsers__ = null;
    
    /** 送信先 ユーザ情報一覧 */
    private List<WmlAddressParamModel> destUserList__  = null;

    public int getEditDestList() {
        return editDestList__;
    }

    public void setEditDestList(int editDestList) {
        editDestList__ = editDestList;
    }

    public String getDestListName() {
        return destListName__;
    }

    public void setDestListName(String destListName) {
        destListName__ = destListName;
    }

    public String getDestListBiko() {
        return destListBiko__;
    }

    public void setDestListBiko(String destListBiko) {
        destListBiko__ = destListBiko;
    }

    public String[] getDestUsers() {
        return destUsers__;
    }

    public void setDestUsers(String[] destUsers) {
        destUsers__ = destUsers;
    }

    public String[] getDestAddresses() {
        return destAddresses__;
    }

    public void setDestAddresses(String[] destAddresses) {
        destAddresses__ = destAddresses;
    }

    public String[] getAccessFullUsers() {
        return accessFullUsers__;
    }

    public void setAccessFullUsers(String[] accessFullUsers) {
        accessFullUsers__ = accessFullUsers;
    }

    public String[] getAccessReadUsers() {
        return accessReadUsers__;
    }

    public void setAccessReadUsers(String[] accessReadUsers) {
        accessReadUsers__ = accessReadUsers;
    }

    public List<WmlAddressParamModel> getDestUserList() {
        return destUserList__;
    }

    public void setDestUserList(List<WmlAddressParamModel> destUserList) {
        destUserList__ = destUserList;
    }
}
