package jp.groupsession.v2.wml.wml150;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール アカウント基本設定画面 不正なフィルター(転送先アドレス)情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150FwCheckModel extends AbstractModel {
    /** アカウントSID */
    private int wacSid__ = 0;
    /** アカウント名 */
    private String wacName__ = null;

    /** フィルターSID */
    private int wftSid__ = 0;
    /** フィルター名 */
    private String filterName__ = null;
    /** フィルター種別 */
    private int wftType__ = 0;
    /** 転送先アドレス */
    private String fwAddress__ = null;

    /** ユーザSID */
    private int userSid__ = 0;
    /** ユーザ名 姓 */
    private String userNameSei__ = null;
    /** ユーザ名 名 */
    private String userNameMei__ = null;
    /** ユーザ 状態区分 */
    private int usrJkbn__ = 0;

    /**
     * <p>wftSid を取得します。
     * @return wftSid
     */
    public int getWftSid() {
        return wftSid__;
    }
    /**
     * <p>wftSid をセットします。
     * @param wftSid wftSid
     */
    public void setWftSid(int wftSid) {
        wftSid__ = wftSid;
    }
    /**
     * <p>wftType を取得します。
     * @return wftType
     */
    public int getWftType() {
        return wftType__;
    }
    /**
     * <p>wftType をセットします。
     * @param wftType wftType
     */
    public void setWftType(int wftType) {
        wftType__ = wftType;
    }
    /**
     * <p>filterName を取得します。
     * @return filterName
     */
    public String getFilterName() {
        return filterName__;
    }
    /**
     * <p>filterName をセットします。
     * @param filterName filterName
     */
    public void setFilterName(String filterName) {
        filterName__ = filterName;
    }
    /**
     * <p>fwAddress を取得します。
     * @return fwAddress
     */
    public String getFwAddress() {
        return fwAddress__;
    }
    /**
     * <p>fwAddress をセットします。
     * @param fwAddress fwAddress
     */
    public void setFwAddress(String fwAddress) {
        fwAddress__ = fwAddress;
    }
    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * <p>wacName を取得します。
     * @return wacName
     */
    public String getWacName() {
        return wacName__;
    }
    /**
     * <p>wacName をセットします。
     * @param wacName wacName
     */
    public void setWacName(String wacName) {
        wacName__ = wacName;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>userNameSei を取得します。
     * @return userNameSei
     */
    public String getUserNameSei() {
        return userNameSei__;
    }
    /**
     * <p>userNameSei をセットします。
     * @param userNameSei userNameSei
     */
    public void setUserNameSei(String userNameSei) {
        userNameSei__ = userNameSei;
    }
    /**
     * <p>userNameMei を取得します。
     * @return userNameMei
     */
    public String getUserNameMei() {
        return userNameMei__;
    }
    /**
     * <p>userNameMei をセットします。
     * @param userNameMei userNameMei
     */
    public void setUserNameMei(String userNameMei) {
        userNameMei__ = userNameMei;
    }
    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
}
