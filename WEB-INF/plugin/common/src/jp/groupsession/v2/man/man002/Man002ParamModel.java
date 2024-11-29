package jp.groupsession.v2.man.man002;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.model.ManAdmSettingInfoModel;

/**
 * <br>[機  能] メイン 管理者設定メニュー画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man002ParamModel extends AbstractParamModel {
    /** ライセンス購入画面URL */
    public String licensePageUrl__;
    /** 無料ライセンス発行画面URL*/
    public String licenseFreeUrl__;
    /** ライセンスID */
    public String licenseId__;
    /** GS UID */
    public String gsUid__;

    /** ライセンス ユーザ数 */
    public String licenseCount__;
    /** 会社名 */
    public String licenseCom__;
    /** ラインセンス登録*/
    public String licenseEntry__;
    /** プラグイン情報 */
    public ArrayList<LicenseModel> pluginList__;
    /** 各プラグイン管理者設定情報 */
    public ArrayList<ManAdmSettingInfoModel> pluginMenuList__;
    /** 現在登録済みユーザ数 */
    public String userCount__;
    /** 登録可能ユーザ数 */
    private String limitUserCount__;
    /** 添付ファイル保存先区分 */
    private String tempFileHozonKbn__;
    /** DB区分 */
    private String dbKbn__;
    /** システム区分 */
    private int sysKbn__ = 0;
    /** DB使用可能容量 */
    private String dbCanUse__;
    /** DB使用量 */
    private String dbUse__;
    /** ドメイン */
    private String domain__ = GSConst.GS_DOMAIN;
    /** パスワード変更の有効・無効 */
    public int changePassword__ = GSConst.CHANGEPASSWORD_PARMIT;
    /** ポータルプラグイン利用可:0・不可:1*/
    private int portalUseOk__ = GSConstMain.PLUGIN_USE;
    /** ショートメール利用可:0/不可:1 */
    public int smlUseOk__ = GSConstMain.PLUGIN_USE;
    /** ショートメール通知設定利用可:0・不可:1*/
    private int smlNoticeFlg__ = GSConstMain.PLUGIN_USE;
    /** 自動削除設定利用可:0/不可:1 */
    private int autoDelFlg__ = GSConstMain.PLUGIN_USE;
    /** 手動削除利用可:0・不可:1*/
    private int manualDelFlg__ = GSConstMain.PLUGIN_USE;

    /** ワンタイムパスワード設定の有効・無効*/
    public int useableOtp__ = GSConstMain.OTP_NOUSE;
    /** システム情報表示フラグ */
    private int man002SysInfDspFlg__ = GSConstMain.SYS_INF_DSP_YES;
    /** 管理者フラグ システム管理者:0/プラグイン管理者:1 */
    private int man002AdminFlg__ = GSConstMain.ADMKBN_NOT_ADM;

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
     * @return the gsUid
     */
    public String getGsUid() {
        return gsUid__;
    }
    /**
     * @param gsUid the gsUid to set
     */
    public void setGsUid(String gsUid) {
        gsUid__ = gsUid;
    }
    /**
     * @return tempFileHozonKbn
     */
    public String getTempFileHozonKbn() {
        return tempFileHozonKbn__;
    }
    /**
     * @param tempFileHozonKbn セットする tempFileHozonKbn
     */
    public void setTempFileHozonKbn(String tempFileHozonKbn) {
        tempFileHozonKbn__ = tempFileHozonKbn;
    }
    /**
     * @return limitUserCount
     */
    public String getLimitUserCount() {
        return limitUserCount__;
    }
    /**
     * @param limitUserCount セットする limitUserCount
     */
    public void setLimitUserCount(String limitUserCount) {
        limitUserCount__ = limitUserCount;
    }
    /**
     * <p>licenseCount を取得します。
     * @return licenseCount
     */
    public String getLicenseCount() {
        return licenseCount__;
    }
    /**
     * <p>licenseCount をセットします。
     * @param licenseCount licenseCount
     */
    public void setLicenseCount(String licenseCount) {
        licenseCount__ = licenseCount;
    }
    /**
     * <p>userCount を取得します。
     * @return userCount
     */
    public String getUserCount() {
        return userCount__;
    }
    /**
     * <p>userCount をセットします。
     * @param userCount userCount
     */
    public void setUserCount(String userCount) {
        userCount__ = userCount;
    }
    /**
     * <p>pluginList を取得します。
     * @return pluginList
     */
    public ArrayList<LicenseModel> getPluginList() {
        return pluginList__;
    }
    /**
     * <p>pluginList をセットします。
     * @param pluginList pluginList
     */
    public void setPluginList(ArrayList<LicenseModel> pluginList) {
        pluginList__ = pluginList;
    }
    /**
     * <p>licenseId を取得します。
     * @return licenseId
     */
    public String getLicenseId() {
        return licenseId__;
    }
    /**
     * <p>licenseId をセットします。
     * @param licenseId licenseId
     */
    public void setLicenseId(String licenseId) {
        licenseId__ = licenseId;
    }
    /**
     * <p>licenseCom を取得します。
     * @return licenseCom
     */
    public String getLicenseCom() {
        return licenseCom__;
    }
    /**
     * <p>licenseCom をセットします。
     * @param licenseCom licenseCom
     */
    public void setLicenseCom(String licenseCom) {
        licenseCom__ = licenseCom;
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
     * <p>licensePageUrl を取得します。
     * @return licensePageUrl
     */
    public String getLicensePageUrl() {
        return licensePageUrl__;
    }
    /**
     * <p>licensePageUrl をセットします。
     * @param licensePageUrl licensePageUrl
     */
    public void setLicensePageUrl(String licensePageUrl) {
        licensePageUrl__ = licensePageUrl;
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
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#smlNoticeFlg__
     */
    public int getSmlNoticeFlg() {
        return smlNoticeFlg__;
    }
    /**
     * <p>smlNoticeFlg をセットします。
     * @param smlNoticeFlg smlNoticeFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#smlNoticeFlg__
     */
    public void setSmlNoticeFlg(int smlNoticeFlg) {
        smlNoticeFlg__ = smlNoticeFlg;
    }
    /**
     * <p>autoDelFlg を取得します。
     * @return autoDelFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#autoDelFlg__
     */
    public int getAutoDelFlg() {
        return autoDelFlg__;
    }
    /**
     * <p>autoDelFlg をセットします。
     * @param autoDelFlg autoDelFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#autoDelFlg__
     */
    public void setAutoDelFlg(int autoDelFlg) {
        autoDelFlg__ = autoDelFlg;
    }
    /**
     * <p>manualDelFlg を取得します。
     * @return manualDelFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#manualDelFlg__
     */
    public int getManualDelFlg() {
        return manualDelFlg__;
    }
    /**
     * <p>manualDelFlg をセットします。
     * @param manualDelFlg manualDelFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#manualDelFlg__
     */
    public void setManualDelFlg(int manualDelFlg) {
        manualDelFlg__ = manualDelFlg;
    }
    /**
     * <p>dbKbn を取得します。
     * @return dbKbn
     */
    public String getDbKbn() {
        return dbKbn__;
    }
    /**
     * <p>dbKbn をセットします。
     * @param dbKbn dbKbn
     */
    public void setDbKbn(String dbKbn) {
        dbKbn__ = dbKbn;
    }
    /**
     * <p>dbCanUse を取得します。
     * @return dbCanUse
     */
    public String getDbCanUse() {
        return dbCanUse__;
    }
    /**
     * <p>dbCanUse をセットします。
     * @param dbCanUse dbCanUse
     */
    public void setDbCanUse(String dbCanUse) {
        dbCanUse__ = dbCanUse;
    }
    /**
     * <p>dbUse を取得します。
     * @return dbUse
     */
    public String getDbUse() {
        return dbUse__;
    }
    /**
     * <p>dbUse をセットします。
     * @param dbUse dbUse
     */
    public void setDbUse(String dbUse) {
        dbUse__ = dbUse;
    }
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
     * <p>licenseEntry を取得します。
     * @return licenseEntry
     */
    public String getLicenseEntry() {
        return licenseEntry__;
    }
    /**
     * <p>licenseEntry をセットします。
     * @param licenseEntry licenseEntry
     */
    public void setLicenseEntry(String licenseEntry) {
        licenseEntry__ = licenseEntry;
    }
    /**
     * <p>licenseFreeUrl を取得します。
     * @return licenseFreeUrl
     */
    public String getLicenseFreeUrl() {
        return licenseFreeUrl__;
    }
    /**
     * <p>licenseFreeUrl をセットします。
     * @param licenseFreeUrl licenseFreeUrl
     */
    public void setLicenseFreeUrl(String licenseFreeUrl) {
        licenseFreeUrl__ = licenseFreeUrl;
    }
    /**
     * <p>useableOtp を取得します。
     * @return useableOtp
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#useableOtp__
     */
    public int getUseableOtp() {
        return useableOtp__;
    }
    /**
     * <p>useableOtp をセットします。
     * @param useableOtp useableOtp
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#useableOtp__
     */
    public void setUseableOtp(int useableOtp) {
        useableOtp__ = useableOtp;
    }
    /**
     * <p>man002SysInfDspFlg を取得します。
     * @return man002SysInfDspFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#man002SysInfDspFlg__
     */
    public int getMan002SysInfDspFlg() {
        return man002SysInfDspFlg__;
    }
    /**
     * <p>man002SysInfDspFlg をセットします。
     * @param man002SysInfDspFlg man002SysInfDspFlg
     * @see jp.groupsession.v2.man.man002.Man002ParamModel#man002SysInfDspFlg__
     */
    public void setMan002SysInfDspFlg(int man002SysInfDspFlg) {
        man002SysInfDspFlg__ = man002SysInfDspFlg;
    }
    /**
     * <p>man002AdminFlg を取得します。
     * @return man002AdminFlg
     * @see jp.groupsession.v2.man.man002.Man002Form#man002AdminFlg__
     */
    public int getMan002AdminFlg() {
        return man002AdminFlg__;
    }
    /**
     * <p>man002AdminFlg をセットします。
     * @param man002AdminFlg man002AdminFlg
     * @see jp.groupsession.v2.man.man002.Man002Form#man002AdminFlg__
     */
    public void setMan002AdminFlg(int man002AdminFlg) {
        man002AdminFlg__ = man002AdminFlg;
    }
}