package jp.groupsession.v2.man.man030;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.AbstractMainParamModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.model.ManAdmSettingInfoModel;

/**
 * <br>[機  能] メイン 個人設定メニュー画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man030ParamModel extends AbstractMainParamModel {
    /** メニュー項目の設定メニューの有効・無効*/
    private int pluginSetting__ = GSConstMain.MENU_STATIC_NOT_USE;
    /** 各プラグイン管理者設定情報 */
    private ArrayList<ManAdmSettingInfoModel> pluginMenuList__;
    /** パスワード変更の有効・無効 */
    public int changePassword__ = GSConst.CHANGEPASSWORD_PARMIT;
    /** ワンタイムパスワード設定の有効・無効*/
    private int useableOtp__ = GSConstMain.OTP_NOUSE;
    /** ポータルプラグイン利用可:0・不可:1*/
    private int portalUseOk__ = GSConstMain.PLUGIN_USE;
    /** ショートメールプラグイン利用可:0/不可:1 */
    public int smlUseOk__ = GSConstMain.PLUGIN_USE;
    /** ショートメール通知設定利用可:0・不可:1*/
    private int smlNoticeFlg__ = GSConstMain.PLUGIN_USE;
    /** ポータルデフォルト表示 0=管理者のみ設定可 1=制限なし */
    private int ptlDefPow__ = 0;
    /** ポータルソート設定 0=管理者のみ設定可 1=制限なし */
    private int ptlSortPow__ = 0;
    /** メイン画面レイアウト設定区分 利用可:0・不可:1*/
    private int mainLayoutKbn__ = GSConstMain.MANSCREEN_LAYOUTKBN_USER;
    /** システム区分*/
    private int sysKbn__ = 0;
    /** 個人情報編集区分 */
    private int mainPconfEditKbn__ = GSConstMain.PCONF_EDIT_USER;
    /** 表示設定区分 */
    private int dspSettingKbn__ = GSConst.SETTING_ADM;

    /**
     * <p>pluginSetting を取得します。
     * @return pluginSetting
     */
    public int getPluginSetting() {
        return pluginSetting__;
    }

    /**
     * <p>pluginSetting をセットします。
     * @param pluginSetting pluginSetting
     */
    public void setPluginSetting(int pluginSetting) {
        pluginSetting__ = pluginSetting;
    }

    /**
     * <p>pluginMenuList を取得します。
     * @return pluginMenuList
     */
    public ArrayList<ManAdmSettingInfoModel> getPluginMenuList() {
        return pluginMenuList__;
    }

    /**
     * <p>pluginMenuList をセットします。
     * @param pluginMenuList pluginMenuList
     */
    public void setPluginMenuList(ArrayList<ManAdmSettingInfoModel> pluginMenuList) {
        pluginMenuList__ = pluginMenuList;
    }

    /**
     * <p>changePassword を取得します。
     * @return changePassword
     */
    public int getChangePassword() {
        return changePassword__;
    }

    /**
     * <p>changePassword をセットします。
     * @param changePassword changePassword
     */
    public void setChangePassword(int changePassword) {
        changePassword__ = changePassword;
    }

    /**
     * <p>portalUseOk を取得します。
     * @return portalUseOk
     */
    public int getPortalUseOk() {
        return portalUseOk__;
    }

    /**
     * <p>portalUseOk をセットします。
     * @param portalUseOk portalUseOk
     */
    public void setPortalUseOk(int portalUseOk) {
        portalUseOk__ = portalUseOk;
    }

    /**
     * <p>smlUseOk を取得します。
     * @return smlUseOk
     * @see jp.groupsession.v2.man.man002.Man002Form#smlUseOk__
     */
    public int getSmlUseOk() {
        return smlUseOk__;
    }

    /**
     * <p>smlUseOk をセットします。
     * @param smlUseOk smlUseOk
     * @see jp.groupsession.v2.man.man002.Man002Form#smlUseOk__
     */
    public void setSmlUseOk(int smlUseOk) {
        smlUseOk__ = smlUseOk;
    }

    /**
     * <p>smlNoticeFlg を取得します。
     * @return smlNoticeFlg
     * @see jp.groupsession.v2.man.man030.Man030Form#smlNoticeFlg__
     */
    public int getSmlNoticeFlg() {
        return smlNoticeFlg__;
    }

    /**
     * <p>smlNoticeFlg をセットします。
     * @param smlNoticeFlg smlNoticeFlg
     * @see jp.groupsession.v2.man.man030.Man030Form#smlNoticeFlg__
     */
    public void setSmlNoticeFlg(int smlNoticeFlg) {
        smlNoticeFlg__ = smlNoticeFlg;
    }

    /**
     * <p>mainLayoutKbn を取得します。
     * @return mainLayoutKbn
     */
    public int getMainLayoutKbn() {
        return mainLayoutKbn__;
    }

    /**
     * <p>mainLayoutKbn をセットします。
     * @param mainLayoutKbn mainLayoutKbn
     */
    public void setMainLayoutKbn(int mainLayoutKbn) {
        mainLayoutKbn__ = mainLayoutKbn;
    }

    /**
     * <p>sysKbn を取得します。
     * @return sysKbn
     */
    public int getSysKbn() {
        return sysKbn__;
    }

    /**
     * <p>sysKbn をセットします。
     * @param sysKbn sysKbn
     */
    public void setSysKbn(int sysKbn) {
        sysKbn__ = sysKbn;
    }

    /**
     * <p>ptlDefPow を取得します。
     * @return ptlDefPow
     */
    public int getPtlDefPow() {
        return ptlDefPow__;
    }

    /**
     * <p>ptlDefPow をセットします。
     * @param ptlDefPow ptlDefPow
     */
    public void setPtlDefPow(int ptlDefPow) {
        ptlDefPow__ = ptlDefPow;
    }

    /**
     * <p>ptlSortPow を取得します。
     * @return ptlSortPow
     */
    public int getPtlSortPow() {
        return ptlSortPow__;
    }

    /**
     * <p>ptlSortPow をセットします。
     * @param ptlSortPow ptlSortPow
     */
    public void setPtlSortPow(int ptlSortPow) {
        ptlSortPow__ = ptlSortPow;
    }

    /**
     * <p>mainPconfEditKbn を取得します。
     * @return mainPconfEditKbn
     */
    public int getMainPconfEditKbn() {
        return mainPconfEditKbn__;
    }

    /**
     * <p>mainPconfEditKbn をセットします。
     * @param mainPconfEditKbn mainPconfEditKbn
     */
    public void setMainPconfEditKbn(int mainPconfEditKbn) {
        mainPconfEditKbn__ = mainPconfEditKbn;
    }

    /**
     * <p>useableOtp を取得します。
     * @return useableOtp
     * @see jp.groupsession.v2.man.man030.Man030ParamModel#useableOtp__
     */
    public int getUseableOtp() {
        return useableOtp__;
    }

    /**
     * <p>useableOtp をセットします。
     * @param useableOtp useableOtp
     * @see jp.groupsession.v2.man.man030.Man030ParamModel#useableOtp__
     */
    public void setUseableOtp(int useableOtp) {
        useableOtp__ = useableOtp;
    }
    /**
     * <p>dspSettingKbn を取得します。
     * @return dspSettingKbn
     * @see jp.groupsession.v2.man.man030.Man030Form#dspSettingKbn__
     */
    public int getDspSettingKbn() {
        return dspSettingKbn__;
    }

    /**
     * <p>dspSettingKbn をセットします。
     * @param dspSettingKbn dspSettingKbn
     * @see jp.groupsession.v2.man.man030.Man030Form#dspSettingKbn__
     */
    public void setDspSettingKbn(int dspSettingKbn) {
        dspSettingKbn__ = dspSettingKbn;
    }
}