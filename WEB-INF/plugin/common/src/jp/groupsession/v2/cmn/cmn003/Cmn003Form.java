package jp.groupsession.v2.cmn.cmn003;

import java.util.List;

import jp.groupsession.v2.cmn.cmn002.Cmn002Form;

/**
 * <br>[機  能] メインメニューのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn003Form extends Cmn002Form {

    /** 最大ページ */
    private int cmn003maxPage__ = 1;
    /** WEB検索表示フラグ */
    private int cmn003searchFlg__ = 0;
    /** メニュー情報 */
    private List<MenuInfo> menuInfoList__ = null;
    /** ボタン表示フラグ */
    private int cmn003buttonFlg__ = 0;
    /** プラグインID */
    private String cmn003PluginId__ = null;
    /** バイナリSID */
    private Long cmn003BinSid__ = new Long(0);
    /** 管理者ユーザフラグ */
    private boolean cmn003SysAdminFlg__ = false;
    /** ロゴ バイナリSID */
    private Long cmn003LogoBinSid__ = new Long(0);
    /** メモプラグイン使用権限  */
    private int cmn003MemoUse__ = 0;
    
    /**
     * <p>cmn003BinSid を取得します。
     * @return cmn003BinSid
     */
    public Long getCmn003BinSid() {
        return cmn003BinSid__;
    }
    /**
     * <p>cmn003BinSid をセットします。
     * @param cmn003BinSid cmn003BinSid
     */
    public void setCmn003BinSid(Long cmn003BinSid) {
        cmn003BinSid__ = cmn003BinSid;
    }
    /**
     * <p>cmn003maxPage を取得します。
     * @return cmn003maxPage
     */
    public int getCmn003maxPage() {
        return cmn003maxPage__;
    }
    /**
     * <p>cmn003maxPage をセットします。
     * @param cmn003maxPage cmn003maxPage
     */
    public void setCmn003maxPage(int cmn003maxPage) {
        cmn003maxPage__ = cmn003maxPage;
    }
    /**
     * <p>cmn003searchFlg を取得します。
     * @return cmn003searchFlg
     */
    public int getCmn003searchFlg() {
        return cmn003searchFlg__;
    }
    /**
     * <p>cmn003searchFlg をセットします。
     * @param cmn003searchFlg cmn003searchFlg
     */
    public void setCmn003searchFlg(int cmn003searchFlg) {
        cmn003searchFlg__ = cmn003searchFlg;
    }
    /**
     * <p>menuInfoList を取得します。
     * @return menuInfoList
     */
    public List<MenuInfo> getMenuInfoList() {
        return menuInfoList__;
    }
    /**
     * <p>menuInfoList をセットします。
     * @param menuInfoList menuInfoList
     */
    public void setMenuInfoList(List<MenuInfo> menuInfoList) {
        menuInfoList__ = menuInfoList;
    }
    /**
     * <p>cmn003buttonFlg を取得します。
     * @return cmn003buttonFlg
     */
    public int getCmn003buttonFlg() {
        return cmn003buttonFlg__;
    }
    /**
     * <p>cmn003buttonFlg をセットします。
     * @param cmn003buttonFlg cmn003buttonFlg
     */
    public void setCmn003buttonFlg(int cmn003buttonFlg) {
        cmn003buttonFlg__ = cmn003buttonFlg;
    }
    /**
     * <p>cmn003SysAdminFlg を取得します。
     * @return cmn003SysAdminFlg
     */
    public boolean isCmn003SysAdminFlg() {
        return cmn003SysAdminFlg__;
    }
    /**
     * <p>cmn003SysAdminFlg をセットします。
     * @param cmn003SysAdminFlg cmn003SysAdminFlg
     */
    public void setCmn003SysAdminFlg(boolean cmn003SysAdminFlg) {
        cmn003SysAdminFlg__ = cmn003SysAdminFlg;
    }
    /**
     * <p>cmn003LogoBinSid を取得します。
     * @return cmn003LogoBinSid
     */
    public Long getCmn003LogoBinSid() {
        return cmn003LogoBinSid__;
    }
    /**
     * <p>cmn003LogoBinSid をセットします。
     * @param cmn003LogoBinSid cmn003LogoBinSid
     */
    public void setCmn003LogoBinSid(Long cmn003LogoBinSid) {
        cmn003LogoBinSid__ = cmn003LogoBinSid;
    }
    /**
     * <p>cmn003MemoUse を取得します。
     * @return cmn003MemoUse
     * @see jp.groupsession.v2.cmn.cmn003.Cmn003Form#cmn003MemoUse__
     */
    public int getCmn003MemoUse() {
        return cmn003MemoUse__;
    }
    /**
     * <p>cmn003MemoUse をセットします。
     * @param cmn003MemoUse cmn003MemoUse
     * @see jp.groupsession.v2.cmn.cmn003.Cmn003Form#cmn003MemoUse__
     */
    public void setCmn003MemoUse(int cmn003MemoUse) {
        cmn003MemoUse__ = cmn003MemoUse;
    }
    /**
     * <p>cmn003PluginId を取得します。
     * @return cmn003PluginId
     */
    public String getCmn003PluginId() {
        return cmn003PluginId__;
    }
    /**
     * <p>cmn003PluginId をセットします。
     * @param cmn003PluginId cmn003PluginId
     */
    public void setCmn003PluginId(String cmn003PluginId) {
        cmn003PluginId__ = cmn003PluginId;
    }
}
