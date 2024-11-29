package jp.groupsession.v2.adr.adr170;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr161.Adr161ParamModel;
import jp.groupsession.v2.adr.ui.parts.address.AdrAddressSelector;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr170ParamModel extends Adr161ParamModel {

    /** 初期表示*/
    private int adr170InitFlg__ = 0;
    /** タイトル */
    private String adr170title__ = null;
    /** マーク */
    private int adr170Mark__ = -1;
    /** コンタクト日時From 年 (選択値) */
    private String adr170enterContactYear__ = null;
    /** コンタクト日時To 年 (選択値) */
    private String adr170enterContactYearTo__ = null;
    /** コンタクト日時From 月 (選択値) */
    private String adr170enterContactMonth__ = null;
    /** コンタクト日時To 月 (選択値) */
    private String adr170enterContactMonthTo__ = null;
    /** コンタクト日時From 日 (選択値) */
    private String adr170enterContactDay__ = null;
    /** コンタクト日時To 日 (選択値) */
    private String adr170enterContactDayTo__ = null;
    /** コンタクト日時From 年月日 (選択値) */
    private String adr170enterContactDateFr__ = null;
    /** コンタクト日時To 年月日 (選択値) */
    private String adr170enterContactDateTo__ = null;
    /** コンタクト日時From 時 (選択値) */
    private String adr170enterContactHour__ = null;
    /** コンタクト日時To 時 (選択値) */
    private String adr170enterContactHourTo__ = null;
    /** コンタクト日時From 分 (選択値) */
    private String adr170enterContactMinute__ = null;
    /** コンタクト日時To 分 (選択値) */
    private String adr170enterContactMinuteTo__ = null;
    /** コンタクト日時Fr 時間 (選択値) */
    private String adr170enterContactTimeFr__ = null;
    /** コンタクト日時To 時間 (選択値) */
    private String adr170enterContactTimeTo__ = null;
    /** プロジェクト (選択値) */
    private String adr170enterContactProject__ = null;
    /** 備考 */
    private String adr170biko__ = null;

    /** プロジェクト コンボ */
    private List < LabelValueBean > adr170projectLabelList__ = null;

    /** 添付ファイル(コンボで選択中) */
    private String[] adr170selectFiles__ = null;
    /** ファイルコンボ */
    private List<LabelValueBean> adr170FileLabelList__ = null;
    /** プラグインID */
    private String adr170pluginId__ = GSConstAddress.PLUGIN_ID_ADDRESS;

    //同時登録用
    /** 会社コンボ選択値 */
    private int adr170SelectedComComboSid__ = -2;

    /** 同時登録ユーザセーブ */
    private String[] saveUser__ = null;

    /** 同時登録 UI */
    private AdrAddressSelector saveUserUI__ = null;

    /** コンタクト履歴 登録対象者情報 会社名 */
    private String adr170ContactUserComName__ = null;
    /** コンタクト履歴 登録対象者情報 氏名 */
    private String adr170ContactUserName__ = null;

    /** プロジェクトSID */
    private String[] adr170ProjectSid__ = null;
    /** プロジェクトリスト */
    List <LabelValueBean> adr170ProjectList__ = null;
    /** 削除対象のプロジェクトSID */
    private String adr170delProjectSid__ = null;

    /**
     * <p>adr170biko を取得します。
     * @return adr170biko
     */
    public String getAdr170biko() {
        return adr170biko__;
    }

    /**
     * <p>adr170biko をセットします。
     * @param adr170biko adr170biko
     */
    public void setAdr170biko(String adr170biko) {
        adr170biko__ = adr170biko;
    }

    /**
     * <p>adr170enterContactDay を取得します。
     * @return adr170enterContactDay
     */
    public String getAdr170enterContactDay() {
        return adr170enterContactDay__;
    }

    /**
     * <p>adr170enterContactDay をセットします。
     * @param adr170enterContactDay adr170enterContactDay
     */
    public void setAdr170enterContactDay(String adr170enterContactDay) {
        adr170enterContactDay__ = adr170enterContactDay;
    }

    /**
     * <p>adr170enterContactHour を取得します。
     * @return adr170enterContactHour
     */
    public String getAdr170enterContactHour() {
        return adr170enterContactHour__;
    }

    /**
     * <p>adr170enterContactHour をセットします。
     * @param adr170enterContactHour adr170enterContactHour
     */
    public void setAdr170enterContactHour(String adr170enterContactHour) {
        adr170enterContactHour__ = adr170enterContactHour;
    }

    /**
     * <p>adr170enterContactMinute を取得します。
     * @return adr170enterContactMinute
     */
    public String getAdr170enterContactMinute() {
        return adr170enterContactMinute__;
    }

    /**
     * <p>adr170enterContactMinute をセットします。
     * @param adr170enterContactMinute adr170enterContactMinute
     */
    public void setAdr170enterContactMinute(String adr170enterContactMinute) {
        adr170enterContactMinute__ = adr170enterContactMinute;
    }

    /**
     * <p>adr170enterContactMonth を取得します。
     * @return adr170enterContactMonth
     */
    public String getAdr170enterContactMonth() {
        return adr170enterContactMonth__;
    }

    /**
     * <p>adr170enterContactMonth をセットします。
     * @param adr170enterContactMonth adr170enterContactMonth
     */
    public void setAdr170enterContactMonth(String adr170enterContactMonth) {
        adr170enterContactMonth__ = adr170enterContactMonth;
    }

    /**
     * <p>adr170enterContactProject を取得します。
     * @return adr170enterContactProject
     */
    public String getAdr170enterContactProject() {
        return adr170enterContactProject__;
    }

    /**
     * <p>adr170enterContactProject をセットします。
     * @param adr170enterContactProject adr170enterContactProject
     */
    public void setAdr170enterContactProject(String adr170enterContactProject) {
        adr170enterContactProject__ = adr170enterContactProject;
    }

    /**
     * <p>adr170enterContactYear を取得します。
     * @return adr170enterContactYear
     */
    public String getAdr170enterContactYear() {
        return adr170enterContactYear__;
    }

    /**
     * <p>adr170enterContactYear をセットします。
     * @param adr170enterContactYear adr170enterContactYear
     */
    public void setAdr170enterContactYear(String adr170enterContactYear) {
        adr170enterContactYear__ = adr170enterContactYear;
    }

    /**
     * <p>adr170projectLabelList を取得します。
     * @return adr170projectLabelList
     */
    public List<LabelValueBean> getAdr170projectLabelList() {
        return adr170projectLabelList__;
    }

    /**
     * <p>adr170projectLabelList をセットします。
     * @param adr170projectLabelList adr170projectLabelList
     */
    public void setAdr170projectLabelList(
            List<LabelValueBean> adr170projectLabelList) {
        adr170projectLabelList__ = adr170projectLabelList;
    }

    /**
     * <p>adr170title を取得します。
     * @return adr170title
     */
    public String getAdr170title() {
        return adr170title__;
    }

    /**
     * <p>adr170title をセットします。
     * @param adr170title adr170title
     */
    public void setAdr170title(String adr170title) {
        adr170title__ = adr170title;
    }

    /**
     * <p>adr170FileLabelList を取得します。
     * @return adr170FileLabelList
     */
    public List<LabelValueBean> getAdr170FileLabelList() {
        return adr170FileLabelList__;
    }

    /**
     * <p>adr170FileLabelList をセットします。
     * @param adr170FileLabelList adr170FileLabelList
     */
    public void setAdr170FileLabelList(List<LabelValueBean> adr170FileLabelList) {
        adr170FileLabelList__ = adr170FileLabelList;
    }

    /**
     * <p>adr170Mark を取得します。
     * @return adr170Mark
     */
    public int getAdr170Mark() {
        return adr170Mark__;
    }

    /**
     * <p>adr170Mark をセットします。
     * @param adr170Mark adr170Mark
     */
    public void setAdr170Mark(int adr170Mark) {
        adr170Mark__ = adr170Mark;
    }

    /**
     * <p>adr170pluginId を取得します。
     * @return adr170pluginId
     */
    public String getAdr170pluginId() {
        return adr170pluginId__;
    }

    /**
     * <p>adr170pluginId をセットします。
     * @param adr170pluginId adr170pluginId
     */
    public void setAdr170pluginId(String adr170pluginId) {
        adr170pluginId__ = adr170pluginId;
    }

    /**
     * <p>adr170selectFiles を取得します。
     * @return adr170selectFiles
     */
    public String[] getAdr170selectFiles() {
        return adr170selectFiles__;
    }

    /**
     * <p>adr170selectFiles をセットします。
     * @param adr170selectFiles adr170selectFiles
     */
    public void setAdr170selectFiles(String[] adr170selectFiles) {
        adr170selectFiles__ = adr170selectFiles;
    }

    /**
     * <p>adr170enterContactDayTo を取得します。
     * @return adr170enterContactDayTo
     */
    public String getAdr170enterContactDayTo() {
        return adr170enterContactDayTo__;
    }

    /**
     * <p>adr170enterContactDayTo をセットします。
     * @param adr170enterContactDayTo adr170enterContactDayTo
     */
    public void setAdr170enterContactDayTo(String adr170enterContactDayTo) {
        adr170enterContactDayTo__ = adr170enterContactDayTo;
    }
    
    /**
     * <p>adr170enterContactDateFr を取得します。
     * @return adr170enterContactDateFr
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactDateFr__
     */
    public String getAdr170enterContactDateFr() {
        return adr170enterContactDateFr__;
    }

    /**
     * <p>adr170enterContactDateFr をセットします。
     * @param adr170enterContactDateFr adr170enterContactDateFr
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactDateFr__
     */
    public void setAdr170enterContactDateFr(String adr170enterContactDateFr) {
        adr170enterContactDateFr__ = adr170enterContactDateFr;
    }

    /**
     * <p>adr170enterContactDateTo を取得します。
     * @return adr170enterContactDateTo
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactDateTo__
     */
    public String getAdr170enterContactDateTo() {
        return adr170enterContactDateTo__;
    }

    /**
     * <p>adr170enterContactDateTo をセットします。
     * @param adr170enterContactDateTo adr170enterContactDateTo
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactDateTo__
     */
    public void setAdr170enterContactDateTo(String adr170enterContactDateTo) {
        adr170enterContactDateTo__ = adr170enterContactDateTo;
    }

    /**
     * <p>adr170enterContactHourTo を取得します。
     * @return adr170enterContactHourTo
     */
    public String getAdr170enterContactHourTo() {
        return adr170enterContactHourTo__;
    }

    /**
     * <p>adr170enterContactHourTo をセットします。
     * @param adr170enterContactHourTo adr170enterContactHourTo
     */
    public void setAdr170enterContactHourTo(String adr170enterContactHourTo) {
        adr170enterContactHourTo__ = adr170enterContactHourTo;
    }

    /**
     * <p>adr170enterContactMinuteTo を取得します。
     * @return adr170enterContactMinuteTo
     */
    public String getAdr170enterContactMinuteTo() {
        return adr170enterContactMinuteTo__;
    }

    /**
     * <p>adr170enterContactMinuteTo をセットします。
     * @param adr170enterContactMinuteTo adr170enterContactMinuteTo
     */
    public void setAdr170enterContactMinuteTo(String adr170enterContactMinuteTo) {
        adr170enterContactMinuteTo__ = adr170enterContactMinuteTo;
    }

    /**
     * <p>adr170enterContactMonthTo を取得します。
     * @return adr170enterContactMonthTo
     */
    public String getAdr170enterContactMonthTo() {
        return adr170enterContactMonthTo__;
    }

    /**
     * <p>adr170enterContactMonthTo をセットします。
     * @param adr170enterContactMonthTo adr170enterContactMonthTo
     */
    public void setAdr170enterContactMonthTo(String adr170enterContactMonthTo) {
        adr170enterContactMonthTo__ = adr170enterContactMonthTo;
    }

    /**
     * <p>adr170enterContactYearTo を取得します。
     * @return adr170enterContactYearTo
     */
    public String getAdr170enterContactYearTo() {
        return adr170enterContactYearTo__;
    }

    /**
     * <p>adr170enterContactYearTo をセットします。
     * @param adr170enterContactYearTo adr170enterContactYearTo
     */
    public void setAdr170enterContactYearTo(String adr170enterContactYearTo) {
        adr170enterContactYearTo__ = adr170enterContactYearTo;
    }
    
    /**
     * <p>adr170enterContactTimeFr を取得します。
     * @return adr170enterContactTimeFr
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactTimeFr__
     */
    public String getAdr170enterContactTimeFr() {
        return adr170enterContactTimeFr__;
    }

    /**
     * <p>adr170enterContactTimeFr をセットします。
     * @param adr170enterContactTimeFr adr170enterContactTimeFr
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactTimeFr__
     */
    public void setAdr170enterContactTimeFr(String adr170enterContactTimeFr) {
        adr170enterContactTimeFr__ = adr170enterContactTimeFr;
    }

    /**
     * <p>adr170enterContactTimeTo を取得します。
     * @return adr170enterContactTimeTo
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactTimeTo__
     */
    public String getAdr170enterContactTimeTo() {
        return adr170enterContactTimeTo__;
    }

    /**
     * <p>adr170enterContactTimeTo をセットします。
     * @param adr170enterContactTimeTo adr170enterContactTimeTo
     * @see jp.groupsession.v2.adr.adr170.Adr170Form#adr170enterContactTimeTo__
     */
    public void setAdr170enterContactTimeTo(String adr170enterContactTimeTo) {
        adr170enterContactTimeTo__ = adr170enterContactTimeTo;
    }

    /**
     * <p>adr170SelectedComComboSid を取得します。
     * @return adr170SelectedComComboSid
     */
    public int getAdr170SelectedComComboSid() {
        return adr170SelectedComComboSid__;
    }

    /**
     * <p>adr170SelectedComComboSid をセットします。
     * @param adr170SelectedComComboSid adr170SelectedComComboSid
     */
    public void setAdr170SelectedComComboSid(int adr170SelectedComComboSid) {
        adr170SelectedComComboSid__ = adr170SelectedComComboSid;
    }

    /**
     * <p>saveUser を取得します。
     * @return saveUser
     */
    public String[] getSaveUser() {
        return saveUser__;
    }

    /**
     * <p>saveUser をセットします。
     * @param saveUser saveUser
     */
    public void setSaveUser(String[] saveUser) {
        saveUser__ = saveUser;
    }

    /**
     * <p>saveUserUI を取得します。
     * @return saveUserUI
     */
    public AdrAddressSelector getSaveUserUI() {
        return saveUserUI__;
    }

    /**
     * <p>saveUserUI をセットします。
     * @param saveUserUI saveUserUI
     */
    public void setSaveUserUI(AdrAddressSelector saveUserUI) {
        saveUserUI__ = saveUserUI;
    }

    /**
     * <p>adr170ContactUserComName を取得します。
     * @return adr170ContactUserComName
     */
    public String getAdr170ContactUserComName() {
        return adr170ContactUserComName__;
    }

    /**
     * <p>adr170ContactUserComName をセットします。
     * @param adr170ContactUserComName adr170ContactUserComName
     */
    public void setAdr170ContactUserComName(String adr170ContactUserComName) {
        adr170ContactUserComName__ = adr170ContactUserComName;
    }

    /**
     * <p>adr170ContactUserName を取得します。
     * @return adr170ContactUserName
     */
    public String getAdr170ContactUserName() {
        return adr170ContactUserName__;
    }

    /**
     * <p>adr170ContactUserName をセットします。
     * @param adr170ContactUserName adr170ContactUserName
     */
    public void setAdr170ContactUserName(String adr170ContactUserName) {
        adr170ContactUserName__ = adr170ContactUserName;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParam170(Cmn999Form msgForm) {

        msgForm.addHiddenParam("adr170title", adr170title__);
        msgForm.addHiddenParam("adr170Mark", adr170Mark__);
        msgForm.addHiddenParam("adr170enterContactYear", adr170enterContactYear__);
        msgForm.addHiddenParam("adr170enterContactMonth", adr170enterContactMonth__);
        msgForm.addHiddenParam("adr170enterContactDay", adr170enterContactDay__);
        msgForm.addHiddenParam("adr170enterContactHour", adr170enterContactHour__);
        msgForm.addHiddenParam("adr170enterContactMinute", adr170enterContactMinute__);
        msgForm.addHiddenParam("adr170enterContactProject", adr170enterContactProject__);
        msgForm.addHiddenParam("adr170biko", adr170biko__);
//        msgForm.addHiddenParam("projectPluginKbn", projectPluginKbn__);
        msgForm.addHiddenParam("adr170ProjectSid", adr170ProjectSid__);
    }

    /**
     * @return adr170ProjectList
     */
    public List<LabelValueBean> getAdr170ProjectList() {
        return adr170ProjectList__;
    }

    /**
     * @param adr170ProjectList 設定する adr170ProjectList
     */
    public void setAdr170ProjectList(List<LabelValueBean> adr170ProjectList) {
        adr170ProjectList__ = adr170ProjectList;
    }

    /**
     * @return adr170ProjectSid
     */
    public String[] getAdr170ProjectSid() {
        return adr170ProjectSid__;
    }

    /**
     * @param adr170ProjectSid 設定する adr170ProjectSid
     */
    public void setAdr170ProjectSid(String[] adr170ProjectSid) {
        adr170ProjectSid__ = adr170ProjectSid;
    }

    /**
     * @return adr170delProjectSid
     */
    public String getAdr170delProjectSid() {
        return adr170delProjectSid__;
    }

    /**
     * @param adr170delProjectSid 設定する adr170delProjectSid
     */
    public void setAdr170delProjectSid(String adr170delProjectSid) {
        adr170delProjectSid__ = adr170delProjectSid;
    }

    /**
     * <p>adr170InitFlg を取得します。
     * @return adr170InitFlg
     * @see jp.groupsession.v2.adr.adr170.Adr170ParamModel#adr170InitFlg__
     */
    public int getAdr170InitFlg() {
        return adr170InitFlg__;
    }

    /**
     * <p>adr170InitFlg をセットします。
     * @param adr170InitFlg adr170InitFlg
     * @see jp.groupsession.v2.adr.adr170.Adr170ParamModel#adr170InitFlg__
     */
    public void setAdr170InitFlg(int adr170InitFlg) {
        adr170InitFlg__ = adr170InitFlg;
    }
}