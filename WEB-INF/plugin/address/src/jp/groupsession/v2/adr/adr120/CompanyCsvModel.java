package jp.groupsession.v2.adr.adr120;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス帳 会社インポート画面 インポートファイルの情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
@SuppressWarnings("all")
public class CompanyCsvModel extends AbstractModel implements Comparator {

    /** 行番号 */
    private long rowNum__ = 0;
    /** 項目数 */
    private int elementCount__ = 0;
    /** 企業コード */
    private String companyCode__ = null;
    /** 会社名 */
    private String companyName__ = null;
    /** 会社名(カナ) */
    private String companyNameKn__ = null;
    /** URL */
    private String companyUrl__ = null;
    /** 会社 郵便番号 */
    private String companyPostNo__ = null;
    /** 会社 都道府県 */
    private String companyTdfk__ = null;
    /** 会社 住所１ */
    private String companyAddress1__ = null;
    /** 会社 住所２ */
    private String companyAddress2__ = null;
    /** 会社 備考 */
    private String companyBiko__ = null;

    /** 企業拠点種別 */
    private String companyBaseType__ = null;
    /** 企業拠点名 */
    private String companyBaseName__ = null;
    /** 企業拠点 郵便番号 */
    private String companyBasePostNo__ = null;
    /** 企業拠点 都道府県 */
    private String companyBaseTdfk__ = null;
    /** 企業拠点 住所１ */
    private String companyBaseAddress1__ = null;
    /** 企業拠点 住所２ */
    private String companyBaseAddress2__ = null;
    /** 企業拠点 備考 */
    private String companyBaseBiko__ = null;

    /**
     * <p>companyBaseAddress1 を取得します。
     * @return companyBaseAddress1
     */
    public String getCompanyBaseAddress1() {
        return companyBaseAddress1__;
    }
    /**
     * <p>companyBaseAddress1 をセットします。
     * @param companyBaseAddress1 companyBaseAddress1
     */
    public void setCompanyBaseAddress1(String companyBaseAddress1) {
        companyBaseAddress1__ = companyBaseAddress1;
    }
    /**
     * <p>companyBaseAddress2 を取得します。
     * @return companyBaseAddress2
     */
    public String getCompanyBaseAddress2() {
        return companyBaseAddress2__;
    }
    /**
     * <p>companyBaseAddress2 をセットします。
     * @param companyBaseAddress2 companyBaseAddress2
     */
    public void setCompanyBaseAddress2(String companyBaseAddress2) {
        companyBaseAddress2__ = companyBaseAddress2;
    }
    /**
     * <p>companyBaseBiko を取得します。
     * @return companyBaseBiko
     */
    public String getCompanyBaseBiko() {
        return companyBaseBiko__;
    }
    /**
     * <p>companyBaseBiko をセットします。
     * @param companyBaseBiko companyBaseBiko
     */
    public void setCompanyBaseBiko(String companyBaseBiko) {
        companyBaseBiko__ = companyBaseBiko;
    }
    /**
     * <p>companyBaseName を取得します。
     * @return companyBaseName
     */
    public String getCompanyBaseName() {
        return companyBaseName__;
    }
    /**
     * <p>companyBaseName をセットします。
     * @param companyBaseName companyBaseName
     */
    public void setCompanyBaseName(String companyBaseName) {
        companyBaseName__ = companyBaseName;
    }
    /**
     * <p>companyBasePostNo を取得します。
     * @return companyBasePostNo
     */
    public String getCompanyBasePostNo() {
        return companyBasePostNo__;
    }
    /**
     * <p>companyBasePostNo をセットします。
     * @param companyBasePostNo companyBasePostNo
     */
    public void setCompanyBasePostNo(String companyBasePostNo) {
        companyBasePostNo__ = companyBasePostNo;
    }
    /**
     * <p>companyBaseTdfk を取得します。
     * @return companyBaseTdfk
     */
    public String getCompanyBaseTdfk() {
        return companyBaseTdfk__;
    }
    /**
     * <p>companyBaseTdfk をセットします。
     * @param companyBaseTdfk companyBaseTdfk
     */
    public void setCompanyBaseTdfk(String companyBaseTdfk) {
        companyBaseTdfk__ = companyBaseTdfk;
    }
    /**
     * <p>companyBaseType を取得します。
     * @return companyBaseType
     */
    public String getCompanyBaseType() {
        return companyBaseType__;
    }
    /**
     * <p>companyBaseType をセットします。
     * @param companyBaseType companyBaseType
     */
    public void setCompanyBaseType(String companyBaseType) {
        companyBaseType__ = companyBaseType;
    }
    /**
     * <p>companyBiko を取得します。
     * @return companyBiko
     */
    public String getCompanyBiko() {
        return companyBiko__;
    }
    /**
     * <p>companyBiko をセットします。
     * @param companyBiko companyBiko
     */
    public void setCompanyBiko(String companyBiko) {
        companyBiko__ = companyBiko;
    }
    /**
     * <p>companyCode を取得します。
     * @return companyCode
     */
    public String getCompanyCode() {
        return companyCode__;
    }
    /**
     * <p>companyCode をセットします。
     * @param companyCode companyCode
     */
    public void setCompanyCode(String companyCode) {
        companyCode__ = companyCode;
    }
    /**
     * <p>companyName を取得します。
     * @return companyName
     */
    public String getCompanyName() {
        return companyName__;
    }
    /**
     * <p>companyName をセットします。
     * @param companyName companyName
     */
    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }
    /**
     * <p>companyNameKn を取得します。
     * @return companyNameKn
     */
    public String getCompanyNameKn() {
        return companyNameKn__;
    }
    /**
     * <p>companyNameKn をセットします。
     * @param companyNameKn companyNameKn
     */
    public void setCompanyNameKn(String companyNameKn) {
        companyNameKn__ = companyNameKn;
    }
    /**
     * <p>companyUrl を取得します。
     * @return companyUrl
     */
    public String getCompanyUrl() {
        return companyUrl__;
    }
    /**
     * <p>companyUrl をセットします。
     * @param companyUrl companyUrl
     */
    public void setCompanyUrl(String companyUrl) {
        companyUrl__ = companyUrl;
    }
    /**
     * <p>companyPostNo を取得します。
     * @return companyPostNo
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyPostNo__
     */
    public String getCompanyPostNo() {
        return companyPostNo__;
    }
    /**
     * <p>companyPostNo をセットします。
     * @param companyPostNo companyPostNo
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyPostNo__
     */
    public void setCompanyPostNo(String companyPostNo) {
        companyPostNo__ = companyPostNo;
    }
    /**
     * <p>companyTdfk を取得します。
     * @return companyTdfk
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyTdfk__
     */
    public String getCompanyTdfk() {
        return companyTdfk__;
    }
    /**
     * <p>companyTdfk をセットします。
     * @param companyTdfk companyTdfk
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyTdfk__
     */
    public void setCompanyTdfk(String companyTdfk) {
        companyTdfk__ = companyTdfk;
    }
    /**
     * <p>companyAddress1 を取得します。
     * @return companyAddress1
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyAddress1__
     */
    public String getCompanyAddress1() {
        return companyAddress1__;
    }
    /**
     * <p>companyAddress1 をセットします。
     * @param companyAddress1 companyAddress1
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyAddress1__
     */
    public void setCompanyAddress1(String companyAddress1) {
        companyAddress1__ = companyAddress1;
    }
    /**
     * <p>companyAddress2 を取得します。
     * @return companyAddress2
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyAddress2__
     */
    public String getCompanyAddress2() {
        return companyAddress2__;
    }
    /**
     * <p>companyAddress2 をセットします。
     * @param companyAddress2 companyAddress2
     * @see jp.groupsession.v2.adr.adr120.CompanyCsvModel#companyAddress2__
     */
    public void setCompanyAddress2(String companyAddress2) {
        companyAddress2__ = companyAddress2;
    }
    /**
     * <p>elementCount を取得します。
     * @return elementCount
     */
    public int getElementCount() {
        return elementCount__;
    }
    /**
     * <p>elementCount をセットします。
     * @param elementCount elementCount
     */
    public void setElementCount(int elementCount) {
        elementCount__ = elementCount;
    }
    /**
     * <p>rowNum を取得します。
     * @return rowNum
     */
    public long getRowNum() {
        return rowNum__;
    }
    /**
     * <p>rowNum をセットします。
     * @param rowNum rowNum
     */
    public void setRowNum(long rowNum) {
        rowNum__ = rowNum;
    }

    /**
     * <br>[機  能] 順序付けのために 2 つの引数を比較する。
     * <br>[解  説]
     * <br>[備  考]
     * @param obj1 比較対象の最初のオブジェクト
     * @param obj2 比較対象の2番目のオブジェクト
     * @return 最初の引数が 2 番目の引数より小さい場合は負の整数、両方が等しい場合は 0、
     *          最初の引数が 2 番目の引数より大きい場合は正の整数
     */
    public int compare(Object obj1, Object obj2) {
        return (int) (((CompanyCsvModel) obj1).getRowNum() - ((CompanyCsvModel) obj2).getRowNum());
    }
}