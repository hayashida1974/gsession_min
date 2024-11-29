package jp.groupsession.v2.adr.adr160.csv;

import jp.groupsession.v2.adr.model.AdrContactModel;

/**
 * <br>アドレス帳 コンタクト履歴画面のエクスポートデータを保持するModelクラス
 * @author JTS
 */
public class Adr160CsvModel extends AdrContactModel {

    /** 氏名 */
    private String addressName__;
    /** 氏名カナ */
    private String addressNameKana__;
    /** 会社名 */
    private String addressKaisyaName__;
    /** 会社名カナ */
    private String addressKaisyaNameKana__;
    /** 支店・営業所種別 */
    private int addressEigyosyoSyubetu__;
    /** 支店・営業所名 */
    private String addressEigyosyoSyubetuName__;
    /** プロジェクト名 */
    private String projectName__;
    /** コンタクト履歴種別名 */
    private String typeName__;
    /** コンタクト開始日付 */
    private String addressContactStartDate__;
    /** コンタクト終了日付 */
    private String addressContactEndDate__;
    /** コンタクト開始時間 */
    private String addressContactStartTime__;
    /** コンタクト終了時間 */
    private String addressContactEndTime__;

    /**
     * <p>addressName を取得します。
     * @return addressName
     */
    public String getAddressName() {
        return addressName__;
    }

    /**
     * <p>addressName をセットします。
     * @param addressName addressName
     */
    public void setAddressName(String addressName) {
        addressName__ = addressName;
    }

    /**
     * <p>projectName を取得します。
     * @return projectName
     */
    public String getProjectName() {
        return projectName__;
    }

    /**
     * <p>projectName をセットします。
     * @param projectName projectName
     */
    public void setProjectName(String projectName) {
        projectName__ = projectName;
    }

    /**
     * <p>typeName を取得します。
     * @return typeName
     */
    public String getTypeName() {
        return typeName__;
    }

    /**
     * <p>typeName をセットします。
     * @param typeName typeName
     */
    public void setTypeName(String typeName) {
        typeName__ = typeName;
    }

    /**
     * <p>addressEigyosyoSyubetu を取得します。
     * @return addressEigyosyoSyubetu
     */
    public int getAddressEigyosyoSyubetu() {
        return addressEigyosyoSyubetu__;
    }

    /**
     * <p>addressEigyosyoSyubetu をセットします。
     * @param addressEigyosyoSyubetu addressEigyosyoSyubetu
     */
    public void setAddressEigyosyoSyubetu(int addressEigyosyoSyubetu) {
        addressEigyosyoSyubetu__ = addressEigyosyoSyubetu;
    }

    /**
     * <p>addressEigyosyoSyubetuName を取得します。
     * @return addressEigyosyoSyubetuName
     */
    public String getAddressEigyosyoSyubetuName() {
        return addressEigyosyoSyubetuName__;
    }

    /**
     * <p>addressEigyosyoSyubetuName をセットします。
     * @param addressEigyosyoSyubetuName addressEigyosyoSyubetuName
     */
    public void setAddressEigyosyoSyubetuName(String addressEigyosyoSyubetuName) {
        addressEigyosyoSyubetuName__ = addressEigyosyoSyubetuName;
    }

    /**
     * <p>addressKaisyaName を取得します。
     * @return addressKaisyaName
     */
    public String getAddressKaisyaName() {
        return addressKaisyaName__;
    }

    /**
     * <p>addressKaisyaName をセットします。
     * @param addressKaisyaName addressKaisyaName
     */
    public void setAddressKaisyaName(String addressKaisyaName) {
        addressKaisyaName__ = addressKaisyaName;
    }

    /**
     * <p>addressKaisyaNameKana を取得します。
     * @return addressKaisyaNameKana
     */
    public String getAddressKaisyaNameKana() {
        return addressKaisyaNameKana__;
    }

    /**
     * <p>addressKaisyaNameKana をセットします。
     * @param addressKaisyaNameKana addressKaisyaNameKana
     */
    public void setAddressKaisyaNameKana(String addressKaisyaNameKana) {
        addressKaisyaNameKana__ = addressKaisyaNameKana;
    }

    /**
     * <p>addressNameKana を取得します。
     * @return addressNameKana
     */
    public String getAddressNameKana() {
        return addressNameKana__;
    }

    /**
     * <p>addressNameKana をセットします。
     * @param addressNameKana addressNameKana
     */
    public void setAddressNameKana(String addressNameKana) {
        addressNameKana__ = addressNameKana;
    }

    /**
     * <p>addressContactEndDate を取得します。
     * @return addressContactEndDate
     */
    public String getAddressContactEndDate() {
        return addressContactEndDate__;
    }

    /**
     * <p>addressContactEndDate をセットします。
     * @param addressContactEndDate addressContactEndDate
     */
    public void setAddressContactEndDate(String addressContactEndDate) {
        addressContactEndDate__ = addressContactEndDate;
    }

    /**
     * <p>addressContactEndTime を取得します。
     * @return addressContactEndTime
     */
    public String getAddressContactEndTime() {
        return addressContactEndTime__;
    }

    /**
     * <p>addressContactEndTime をセットします。
     * @param addressContactEndTime addressContactEndTime
     */
    public void setAddressContactEndTime(String addressContactEndTime) {
        addressContactEndTime__ = addressContactEndTime;
    }

    /**
     * <p>addressContactStartDate を取得します。
     * @return addressContactStartDate
     */
    public String getAddressContactStartDate() {
        return addressContactStartDate__;
    }

    /**
     * <p>addressContactStartDate をセットします。
     * @param addressContactStartDate addressContactStartDate
     */
    public void setAddressContactStartDate(String addressContactStartDate) {
        addressContactStartDate__ = addressContactStartDate;
    }

    /**
     * <p>addressContactStartTime を取得します。
     * @return addressContactStartTime
     */
    public String getAddressContactStartTime() {
        return addressContactStartTime__;
    }

    /**
     * <p>addressContactStartTime をセットします。
     * @param addressContactStartTime addressContactStartTime
     */
    public void setAddressContactStartTime(String addressContactStartTime) {
        addressContactStartTime__ = addressContactStartTime;
    }
}
