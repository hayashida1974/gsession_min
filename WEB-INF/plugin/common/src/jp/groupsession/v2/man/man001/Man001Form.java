package jp.groupsession.v2.man.man001;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.cmn999.Cmn999Model;
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.man.MainScreenOfPluginModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] メイン画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man001Form extends AbstractGsForm {

    //共通項目
    /** 管理者権限有無 1:権限有り 2:権限無し*/
    private int adminKbn__;
    //表示設定
    /** 表示設定*/
    private ArrayList<CmnMdispModel> mainDspConfList__;
    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConstMain.PLUGIN_USE;
    /** プロジェクト管理プラグイン利用可:0・不可:1*/
    private int projectUseOk__ = GSConstMain.PLUGIN_USE;
    /** WEB検索プラグイン利用可:0・不可:1*/
    private int searchUseOk__ = GSConstMain.PLUGIN_USE;
    /** ポータル管理者フラグ */
    private boolean man001ptlAdminFlg__ = false;
    /** main status */
    private int man001mainStatus__ = 0;
    /** 最終ログイン時間 */
    private String man001lstLogintime__ = null;

    //週間スケジュール
    /** 週間スケジュール 表示日付 */
    private String schWeekDate__;

    /** 自動リロード時間 */
    private int man001Reload__ = 600000;

    /** 表示位置 左 */
    private String[] man001PositionLeft__ = null;
    /** 表示位置 右 */
    private String[] man001PositionRight__ = null;
    /** 表示位置 上 */
    private String[] man001PositionTop__ = null;
    /** 表示位置 下 */
    private String[] man001PositionBottom__ = null;
    /** 表示位置 中 */
    private String[] man001PositionCenter__ = null;

    /** hiddenリスト */
    private ArrayList < Cmn999Model > hiddenList__;

    /** インフォーメーションのリスト */
    private ArrayList<MainInfoMessageModel> infoMsgs__ = null;
    /** インフォーメーション設定　表示・非表示 */
    public boolean infoConf__ = false;

    /** インフォーメーションのリスト */
    private List<MainScreenOfPluginModel> screenInfoList__ = null;

    /** インフォーメーションのリスト */
    private List<MainScreenOfPluginModel> screenInfoListLeft__ = null;
    /** インフォーメーションのリスト */
    private List<MainScreenOfPluginModel> screenInfoListRight__ = null;
    /** インフォーメーションのリスト */
    private List<MainScreenOfPluginModel> screenInfoListTop__ = null;
    /** インフォーメーションのリスト */
    private List<MainScreenOfPluginModel> screenInfoListBottom__ = null;
    /** インフォーメーションのリスト */
    private List<MainScreenOfPluginModel> screenInfoListCenter__ = null;

    //スケジュール用
    /** 処理モード */
    private String cmd__ = null;
    /** ユーザSID */
    private String sch010SelectUsrSid__;
    /** ユーザ区分 */
    private String sch010SelectUsrKbn__;
    /** 表示日付 */
    private String sch010DspDate__;

    /** ポータル一覧取得 */
    private List<PtlPortalModel> portalList__ = null;
    /** ポータルSID */
    private int ptlMainSid__ = -1;

    /** レイアウトデフォルトフラグ */
    private boolean man001layoutDefFlg__ = true;
    /** レイアウト表示区分 左 */
    private int man001areaLeft__ = GSConstMain.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 右 */
    private int man001areaRight__ = GSConstMain.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 上 */
    private int man001areaTop__ = GSConstMain.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 下 */
    private int man001areaBottom__ = GSConstMain.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 中 */
    private int man001areaCenter__ = GSConstMain.LAYOUT_VIEW_OFF;

    /**
     * @return the infoConf
     */
    public boolean isInfoConf() {
        return infoConf__;
    }
    /**
     * @param infoConf the infoConf to set
     */
    public void setInfoConf(boolean infoConf) {
        infoConf__ = infoConf;
    }
    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }
    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }
    /**
     * <p>sch010DspDate を取得します。
     * @return sch010DspDate
     */
    public String getSch010DspDate() {
        return sch010DspDate__;
    }
    /**
     * <p>sch010DspDate をセットします。
     * @param sch010DspDate sch010DspDate
     */
    public void setSch010DspDate(String sch010DspDate) {
        sch010DspDate__ = sch010DspDate;
    }
    /**
     * <p>sch010SelectUsrKbn を取得します。
     * @return sch010SelectUsrKbn
     */
    public String getSch010SelectUsrKbn() {
        return sch010SelectUsrKbn__;
    }
    /**
     * <p>sch010SelectUsrKbn をセットします。
     * @param sch010SelectUsrKbn sch010SelectUsrKbn
     */
    public void setSch010SelectUsrKbn(String sch010SelectUsrKbn) {
        sch010SelectUsrKbn__ = sch010SelectUsrKbn;
    }
    /**
     * <p>sch010SelectUsrSid を取得します。
     * @return sch010SelectUsrSid
     */
    public String getSch010SelectUsrSid() {
        return sch010SelectUsrSid__;
    }
    /**
     * <p>sch010SelectUsrSid をセットします。
     * @param sch010SelectUsrSid sch010SelectUsrSid
     */
    public void setSch010SelectUsrSid(String sch010SelectUsrSid) {
        sch010SelectUsrSid__ = sch010SelectUsrSid;
    }
    /**
     * @return adminKbn を戻します。
     */
    public int getAdminKbn() {
        return adminKbn__;
    }
    /**
     * @param adminKbn 設定する adminKbn。
     */
    public void setAdminKbn(int adminKbn) {
        adminKbn__ = adminKbn;
    }
    /**
     * @return schWeekDate を戻します。
     */
    public String getSchWeekDate() {
        return schWeekDate__;
    }
    /**
     * @param schWeekDate 設定する schWeekDate。
     */
    public void setSchWeekDate(String schWeekDate) {
        schWeekDate__ = schWeekDate;
    }

    /**
     * <p>infoMsgs を取得します。
     * @return infoMsgs
     */
    public ArrayList<MainInfoMessageModel> getInfoMsgs() {
        return infoMsgs__;
    }
    /**
     * <p>infoMsgs をセットします。
     * @param infoMsgs infoMsgs
     */
    public void setInfoMsgs(ArrayList<MainInfoMessageModel> infoMsgs) {
        infoMsgs__ = infoMsgs;
    }

    /**
     * <p>man001mainStatus を取得します。
     * @return man001mainStatus
     */
    public int getMan001mainStatus() {
        return man001mainStatus__;
    }
    /**
     * <p>man001mainStatus をセットします。
     * @param man001mainStatus man001mainStatus
     */
    public void setMan001mainStatus(int man001mainStatus) {
        man001mainStatus__ = man001mainStatus;
    }
    /**
     * <p>screenInfoList を取得します。
     * @return screenInfoList
     */
    public List<MainScreenOfPluginModel> getScreenInfoList() {
        return screenInfoList__;
    }
    /**
     * <p>screenInfoList をセットします。
     * @param screenInfoList screenInfoList
     */
    public void setScreenInfoList(List<MainScreenOfPluginModel> screenInfoList) {
        screenInfoList__ = screenInfoList;
    }
    /**
     * <p>mainDspConfList を取得します。
     * @return mainDspConfList
     */
    public ArrayList<CmnMdispModel> getMainDspConfList() {
        return mainDspConfList__;
    }
    /**
     * <p>mainDspConfList をセットします。
     * @param mainDspConfList mainDspConfList
     */
    public void setMainDspConfList(ArrayList<CmnMdispModel> mainDspConfList) {
        mainDspConfList__ = mainDspConfList;
    }
    /**
     * <p>man001PositionLeft を取得します。
     * @return man001PositionLeft
     */
    public String[] getMan001PositionLeft() {
        return man001PositionLeft__;
    }
    /**
     * <p>man001PositionLeft をセットします。
     * @param man001PositionLeft man001PositionLeft
     */
    public void setMan001PositionLeft(String[] man001PositionLeft) {
        man001PositionLeft__ = man001PositionLeft;
    }
    /**
     * <p>man001PositionRight を取得します。
     * @return man001PositionRight
     */
    public String[] getMan001PositionRight() {
        return man001PositionRight__;
    }
    /**
     * <p>man001PositionRight をセットします。
     * @param man001PositionRight man001PositionRight
     */
    public void setMan001PositionRight(String[] man001PositionRight) {
        man001PositionRight__ = man001PositionRight;
    }
    /**
     * <p>man001Reload を取得します。
     * @return man001Reload
     */
    public int getMan001Reload() {
        return man001Reload__;
    }
    /**
     * <p>man001Reload をセットします。
     * @param man001Reload man001Reload
     */
    public void setMan001Reload(int man001Reload) {
        man001Reload__ = man001Reload;
    }
    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }
    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }
    /**
     * <p>projectUseOk を取得します。
     * @return projectUseOk
     */
    public int getProjectUseOk() {
        return projectUseOk__;
    }
    /**
     * <p>projectUseOk をセットします。
     * @param projectUseOk projectUseOk
     */
    public void setProjectUseOk(int projectUseOk) {
        projectUseOk__ = projectUseOk;
    }
    /**
     * <p>searchUseOk を取得します。
     * @return searchUseOk
     */
    public int getSearchUseOk() {
        return searchUseOk__;
    }
    /**
     * <p>searchUseOk をセットします。
     * @param searchUseOk searchUseOk
     */
    public void setSearchUseOk(int searchUseOk) {
        searchUseOk__ = searchUseOk;
    }
    /**
     * @return portalList
     */
    public List<PtlPortalModel> getPortalList() {
        return portalList__;
    }
    /**
     * @param portalList セットする portalList
     */
    public void setPortalList(List<PtlPortalModel> portalList) {
        portalList__ = portalList;
    }
    /**
     * @return ptlMainSid
     */
    public int getPtlMainSid() {
        return ptlMainSid__;
    }
    /**
     * @param ptlMainSid セットする ptlMainSid
     */
    public void setPtlMainSid(int ptlMainSid) {
        ptlMainSid__ = ptlMainSid;
    }
    /**
     * <p>man001ptlAdminFlg を取得します。
     * @return man001ptlAdminFlg
     */
    public boolean isMan001ptlAdminFlg() {
        return man001ptlAdminFlg__;
    }
    /**
     * <p>man001ptlAdminFlg をセットします。
     * @param man001ptlAdminFlg man001ptlAdminFlg
     */
    public void setMan001ptlAdminFlg(boolean man001ptlAdminFlg) {
        man001ptlAdminFlg__ = man001ptlAdminFlg;
    }
    /**
     * <p>man001areaLeft を取得します。
     * @return man001areaLeft
     */
    public int getMan001areaLeft() {
        return man001areaLeft__;
    }
    /**
     * <p>man001areaLeft をセットします。
     * @param man001areaLeft man001areaLeft
     */
    public void setMan001areaLeft(int man001areaLeft) {
        man001areaLeft__ = man001areaLeft;
    }
    /**
     * <p>man001areaRight を取得します。
     * @return man001areaRight
     */
    public int getMan001areaRight() {
        return man001areaRight__;
    }
    /**
     * <p>man001areaRight をセットします。
     * @param man001areaRight man001areaRight
     */
    public void setMan001areaRight(int man001areaRight) {
        man001areaRight__ = man001areaRight;
    }
    /**
     * <p>man001areaTop を取得します。
     * @return man001areaTop
     */
    public int getMan001areaTop() {
        return man001areaTop__;
    }
    /**
     * <p>man001areaTop をセットします。
     * @param man001areaTop man001areaTop
     */
    public void setMan001areaTop(int man001areaTop) {
        man001areaTop__ = man001areaTop;
    }
    /**
     * <p>man001areaBottom を取得します。
     * @return man001areaBottom
     */
    public int getMan001areaBottom() {
        return man001areaBottom__;
    }
    /**
     * <p>man001areaBottom をセットします。
     * @param man001areaBottom man001areaBottom
     */
    public void setMan001areaBottom(int man001areaBottom) {
        man001areaBottom__ = man001areaBottom;
    }
    /**
     * <p>man001areaCenter を取得します。
     * @return man001areaCenter
     */
    public int getMan001areaCenter() {
        return man001areaCenter__;
    }
    /**
     * <p>man001areaCenter をセットします。
     * @param man001areaCenter man001areaCenter
     */
    public void setMan001areaCenter(int man001areaCenter) {
        man001areaCenter__ = man001areaCenter;
    }
    /**
     * <p>man001layoutDefFlg を取得します。
     * @return man001layoutDefFlg
     */
    public boolean isMan001layoutDefFlg() {
        return man001layoutDefFlg__;
    }
    /**
     * <p>man001layoutDefFlg をセットします。
     * @param man001layoutDefFlg man001layoutDefFlg
     */
    public void setMan001layoutDefFlg(boolean man001layoutDefFlg) {
        man001layoutDefFlg__ = man001layoutDefFlg;
    }
    /**
     * <p>man001PositionTop を取得します。
     * @return man001PositionTop
     */
    public String[] getMan001PositionTop() {
        return man001PositionTop__;
    }
    /**
     * <p>man001PositionTop をセットします。
     * @param man001PositionTop man001PositionTop
     */
    public void setMan001PositionTop(String[] man001PositionTop) {
        man001PositionTop__ = man001PositionTop;
    }
    /**
     * <p>man001PositionBottom を取得します。
     * @return man001PositionBottom
     */
    public String[] getMan001PositionBottom() {
        return man001PositionBottom__;
    }
    /**
     * <p>man001PositionBottom をセットします。
     * @param man001PositionBottom man001PositionBottom
     */
    public void setMan001PositionBottom(String[] man001PositionBottom) {
        man001PositionBottom__ = man001PositionBottom;
    }
    /**
     * <p>man001PositionCenter を取得します。
     * @return man001PositionCenter
     */
    public String[] getMan001PositionCenter() {
        return man001PositionCenter__;
    }
    /**
     * <p>man001PositionCenter をセットします。
     * @param man001PositionCenter man001PositionCenter
     */
    public void setMan001PositionCenter(String[] man001PositionCenter) {
        man001PositionCenter__ = man001PositionCenter;
    }
    /**
     * <p>screenInfoListLeft を取得します。
     * @return screenInfoListLeft
     */
    public List<MainScreenOfPluginModel> getScreenInfoListLeft() {
        return screenInfoListLeft__;
    }
    /**
     * <p>screenInfoListLeft をセットします。
     * @param screenInfoListLeft screenInfoListLeft
     */
    public void setScreenInfoListLeft(
            List<MainScreenOfPluginModel> screenInfoListLeft) {
        screenInfoListLeft__ = screenInfoListLeft;
    }
    /**
     * <p>screenInfoListRight を取得します。
     * @return screenInfoListRight
     */
    public List<MainScreenOfPluginModel> getScreenInfoListRight() {
        return screenInfoListRight__;
    }
    /**
     * <p>screenInfoListRight をセットします。
     * @param screenInfoListRight screenInfoListRight
     */
    public void setScreenInfoListRight(
            List<MainScreenOfPluginModel> screenInfoListRight) {
        screenInfoListRight__ = screenInfoListRight;
    }
    /**
     * <p>screenInfoListTop を取得します。
     * @return screenInfoListTop
     */
    public List<MainScreenOfPluginModel> getScreenInfoListTop() {
        return screenInfoListTop__;
    }
    /**
     * <p>screenInfoListTop をセットします。
     * @param screenInfoListTop screenInfoListTop
     */
    public void setScreenInfoListTop(List<MainScreenOfPluginModel> screenInfoListTop) {
        screenInfoListTop__ = screenInfoListTop;
    }
    /**
     * <p>screenInfoListBottom を取得します。
     * @return screenInfoListBottom
     */
    public List<MainScreenOfPluginModel> getScreenInfoListBottom() {
        return screenInfoListBottom__;
    }
    /**
     * <p>screenInfoListBottom をセットします。
     * @param screenInfoListBottom screenInfoListBottom
     */
    public void setScreenInfoListBottom(
            List<MainScreenOfPluginModel> screenInfoListBottom) {
        screenInfoListBottom__ = screenInfoListBottom;
    }
    /**
     * <p>screenInfoListCenter を取得します。
     * @return screenInfoListCenter
     */
    public List<MainScreenOfPluginModel> getScreenInfoListCenter() {
        return screenInfoListCenter__;
    }
    /**
     * <p>screenInfoListCenter をセットします。
     * @param screenInfoListCenter screenInfoListCenter
     */
    public void setScreenInfoListCenter(
            List<MainScreenOfPluginModel> screenInfoListCenter) {
        screenInfoListCenter__ = screenInfoListCenter;
    }

    /**
     * <p>hiddenList を取得します。
     * @return hiddenList
     */
    public ArrayList<Cmn999Model> getHiddenList() {
        return hiddenList__;
    }
    /**
     * <p>hiddenList をセットします。
     * @param hiddenList hiddenList
     */
    public void setHiddenList(ArrayList<Cmn999Model> hiddenList) {
        hiddenList__ = hiddenList;
    }
    /**
     * <p>文字列パラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, String value) {
        if (hiddenList__ == null) {
            hiddenList__ = new ArrayList<Cmn999Model>();
        }
        Cmn999Model hdMdl = new Cmn999Model();
        hdMdl.setKey(key);
        hdMdl.setValue(value);
        hiddenList__.add(hdMdl);
    }
    /**
     * <p>man001lstLogintime を取得します。
     * @return man001lstLogintime
     */
    public String getMan001lstLogintime() {
        return man001lstLogintime__;
    }
    /**
     * <p>man001lstLogintime をセットします。
     * @param man001lstLogintime man001lstLogintime
     */
    public void setMan001lstLogintime(String man001lstLogintime) {
        man001lstLogintime__ = man001lstLogintime;
    }

}