package jp.groupsession.v2.wml.wml270;

import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール 送信先リスト情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml270DestListModel extends AbstractModel {

    /** 送信先リストSID */
    private int destListSid__ = 0;
    /** 送信先リスト名 */
    private String destListName__ = null;
    /** 備考 */
    private String destListBiko__ = null;
    /** 使用ユーザ数 */
    private int userCount__ = 0;
    /** 編集可能フラグ */
    private boolean editFlg__ = false;

    /** 送信先情報一覧 */
    private List<Wml270DestListAddressModel> addressList__ = null;

    /**
     * <p>destListSid を取得します。
     * @return destListSid
     */
    public int getDestListSid() {
        return destListSid__;
    }

    /**
     * <p>destListSid をセットします。
     * @param destListSid destListSid
     */
    public void setDestListSid(int destListSid) {
        destListSid__ = destListSid;
    }

    /**
     * <p>destListName を取得します。
     * @return destListName
     */
    public String getDestListName() {
        return destListName__;
    }

    /**
     * <p>destListName をセットします。
     * @param destListName destListName
     */
    public void setDestListName(String destListName) {
        destListName__ = destListName;
    }

    /**
     * <p>destListBiko を取得します。
     * @return destListBiko
     */
    public String getDestListBiko() {
        return destListBiko__;
    }

    /**
     * <p>destListBiko をセットします。
     * @param destListBiko destListBiko
     */
    public void setDestListBiko(String destListBiko) {
        destListBiko__ = destListBiko;
    }

    /**
     * <p>editFlg を取得します。
     * @return editFlg
     */
    public boolean isEditFlg() {
        return editFlg__;
    }

    /**
     * <p>editFlg をセットします。
     * @param editFlg editFlg
     */
    public void setEditFlg(boolean editFlg) {
        editFlg__ = editFlg;
    }

    /**
     * <p>userCount を取得します。
     * @return userCount
     */
    public int getUserCount() {
        return userCount__;
    }

    /**
     * <p>userCount をセットします。
     * @param userCount userCount
     */
    public void setUserCount(int userCount) {
        userCount__ = userCount;
    }

    /**
     * <p>addressList を取得します。
     * @return addressList
     */
    public List<Wml270DestListAddressModel> getAddressList() {
        return addressList__;
    }

    /**
     * <p>addressList をセットします。
     * @param addressList addressList
     */
    public void setAddressList(List<Wml270DestListAddressModel> addressList) {
        addressList__ = addressList;
    }

    /**
     * <br>[機  能] 備考(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 備考(表示用)
     */
    public String getViewBiko() {
        return StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(destListBiko__, ""));
    }

}
