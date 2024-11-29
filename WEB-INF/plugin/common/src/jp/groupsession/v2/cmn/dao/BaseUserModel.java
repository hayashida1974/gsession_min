package jp.groupsession.v2.cmn.dao;
import java.util.Locale;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機 能] セッションユーザ情報を格納するModelクラス
 * <br>[解 説]
 * <br>[備 考] セッションに保持されるユーザ情報オブジェクト
 *
 * @author JTS
 */
public class BaseUserModel {
    /** ユーザSID */
    private int usrsid__ = -1;
    /** 姓 */
    private String usisei__ = null;
    /** 名 */
    private String usimei__ = null;
    /** ユーザ区分 true:管理者, false:一般 */
    private boolean adminFlg__ = false;
    /** 前回ログイン時間 */
    private String lstLogintime__ = null;
    /** ログインID */
    private String lgid__ = null;
    /** モバイル使用可否 */
    private int mblUse__ = GSConstUser.MBL_USE_OK;
    /** CTM_PATH mapping */
    private String ctmPath__;
    /** 言語 */
    private String country__ = Locale.JAPAN.getCountry();
    /** 会社ドメイン */
    private String domain__;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /** 遷移元プラグインID */
    private String backPluginId__ = GSConst.PLUGINID_MAIN;
    
    /**
     * <p>domain を取得します。
     * @return domain
     */
    public String getDomain() {
        return domain__;
    }
    /**
     * <p>domain をセットします。
     * @param domain domain
     */
    public void setDomain(String domain) {
        domain__ = domain;
    }
    /**
     * @return mblUse__ を戻します。
     */
    public int getMblUse() {
        return mblUse__;
    }
    /**
     * @param mblUse 設定する mblUse__。
     */
    public void setMblUse(int mblUse) {
        mblUse__ = mblUse;
    }
    /**
     * @return usimei を戻します。
     */
    public String getUsimei() {
        return usimei__;
    }
    /**
     * @param usimei 設定する usimei。
     */
    public void setUsimei(String usimei) {
        usimei__ = usimei;
    }
    /**
     * @return usisei を戻します。
     */
    public String getUsisei() {
        return usisei__;
    }
    /**
     * @param usisei 設定する usisei。
     */
    public void setUsisei(String usisei) {
        usisei__ = usisei;
    }

    /**
     * @return usrsid を戻します。
     */
    public int getUsrsid() {
        return usrsid__;
    }

    /**
     * @param usrsid 設定する usrsid。
     */
    public void setUsrsid(int usrsid) {
        usrsid__ = usrsid;
    }

    /**
     * @return adminFlg を戻します。
     */
    public boolean getAdminFlg() {
        return adminFlg__;
    }

    /**
     * @param adminFlg 設定する adminFlg。
     */
    public void setAdminFlg(boolean adminFlg) {
        adminFlg__ = adminFlg;
    }

    /**
     * <p>管理者かどうか判定を行う
     * @return true:管理者, false:一般
     */
    public boolean isAdmin() {
        return getAdminFlg();
    }

    /**
     * @return ユーザ名(姓 + 名) を戻します。
     */
    public String getUsiseimei() {
        return NullDefault.getString(usisei__, "") + "　" + NullDefault.getString(usimei__, "");
    }
    /**
     * <p>lstLogintime を取得します。
     * @return lstLogintime
     */
    public String getLstLogintime() {
        return lstLogintime__;
    }
    /**
     * <p>lstLogintime をセットします。
     * @param lstLogintime lstLogintime
     */
    public void setLstLogintime(String lstLogintime) {
        lstLogintime__ = lstLogintime;
    }
    /**
     * <p>lgid を取得します。
     * @return lgid
     */
    public String getLgid() {
        return lgid__;
    }
    /**
     * <p>lgid をセットします。
     * @param lgid lgid
     */
    public void setLgid(String lgid) {
        lgid__ = lgid;
    }

    /**
     * <p>get CTM_PATH value
     * @return CTM_PATH value
     */
    public String getCtmPath() {
        return ctmPath__;
    }

    /**
     * <p>set CTM_PATH value
     * @param ctmPath CTM_PATH value
     */
    public void setCtmPath(String ctmPath) {
        ctmPath__ = ctmPath;
    }
    /**
     * @return country
     */
    public String getCountry() {
        return country__;
    }
    /**
     * @param country 設定する country
     */
    public void setCountry(String country) {
        country__ = country;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
    
    /**
     * <p>backPluginId を取得します。
     * @return backPluginId
     * @see jp.groupsession.v2.cmn.dao.BaseUserModel#backPluginId__
     */
    public String getBackPluginId() {
        return backPluginId__;
    }
    /**
     * <p>backPluginId をセットします。
     * @param backPluginId backPluginId
     * @see jp.groupsession.v2.cmn.dao.BaseUserModel#backPluginId__
     */
    public void setBackPluginId(String backPluginId) {
        backPluginId__ = backPluginId;
    }
}
