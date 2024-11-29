package jp.groupsession.v2.adr.adr250;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.adr.adr010.Adr010ParamModel;

/**
 * <br>[機  能] アドレス帳 会社情報ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr250ParamModel extends Adr010ParamModel {

    /** 会社SID */
    private int adr250AcoSid__ = 0;

    /** 会社拠点情報一覧 */
    private List<Adr250BaseForm> abaList__ = null;

    /** 企業コード */
    private String adr250coCode__ = null;
    /** 会社名 */
    private String adr250coName__ = null;
    /** 会社名(カナ) */
    private String adr250coNameKn__ = null;
    /** 所属業種 */
    private String[] adr250atiList__ = null;
    /** 会社情報 郵便番号(上3桁) */
    private String adr250coPostno1__ = null;
    /** 会社情報 郵便番号(下4桁) */
    private String adr250coPostno2__ = null;
    /** 会社情報 都道府県 */
    private int adr250coTdfk__ = -1;
    /** 会社情報 都道府県名 */
    private String adr250coTdfkName__ = null;
    /** 会社情報 住所1 */
    private String adr250coAddress1__ = null;
    /** 会社情報 住所2 */
    private String adr250coAddress2__ = null;
    /** URL */
    private String adr250url__ = null;
    /** 備考 */
    private String adr250biko__ = null;

    /** 会社拠点 種別 */
    private int adr250abaType__ = 0;
    /** 会社拠点 支店・営業所名 */
    private String adr250abaName__ = null;
    /** 会社拠点 郵便番号上3桁 */
    private String adr250abaPostno1__ = null;
    /** 会社拠点 郵便番号下4桁 */
    private String adr250abaPostno2__ = null;
    /** 会社拠点 都道府県 */
    private int adr250abaTdfk__ = 0;
    /** 会社拠点 住所１ */
    private String adr250abaAddress1__ = null;
    /** 会社拠点 住所２ */
    private String adr250abaAddress2__ = null;
    /** 会社拠点 備考 */
    private String adr250abaBiko__ = null;

    /** 所属業種(選択用) */
    private String[] adr250selectAtiList__ = null;
    /** 未所属業種 */
    private String[] adr250NoSelectAtiList__ = null;
    /** 所属業種 */
    private List<LabelValueBean> selectAtiCombo__ = null;
    /** 未所属業種 */
    private List<LabelValueBean> noSelectAtiCombo__ = null;
    /** 拠点種別 */
    private List<LabelValueBean> abaTypeList__ = null;

    /** 所属業種 (表示用) */
    private String[] adr250ViewAtiList__ = null;
    /** 備考(表示用) */
    private String adr250ViewBiko__ = null;

    /**
     * @return adr250ViewAtiList
     */
    public String[] getAdr250ViewAtiList() {
        return adr250ViewAtiList__;
    }

    /**
     * @param adr250ViewAtiList 設定する adr250ViewAtiList
     */
    public void setAdr250ViewAtiList(String[] adr250ViewAtiList) {
        this.adr250ViewAtiList__ = adr250ViewAtiList;
    }

    /**
     * @return adr250ViewBiko
     */
    public String getAdr250ViewBiko() {
        return adr250ViewBiko__;
    }

    /**
     * @param adr250ViewBiko 設定する adr250ViewBiko
     */
    public void setAdr250ViewBiko(String adr250ViewBiko) {
        this.adr250ViewBiko__ = adr250ViewBiko;
    }

    /**
     * @return abaList
     */
    public List<Adr250BaseForm> getAbaList() {
        return abaList__;
    }

    /**
     * @param abaList 設定する abaList
     */
    public void setAbaList(List<Adr250BaseForm> abaList) {
        this.abaList__ = abaList;
    }

    /**
     * @return abaTypeList
     */
    public List<LabelValueBean> getAbaTypeList() {
        return abaTypeList__;
    }

    /**
     * @param abaTypeList 設定する abaTypeList
     */
    public void setAbaTypeList(List<LabelValueBean> abaTypeList) {
        this.abaTypeList__ = abaTypeList;
    }

    /**
     * @return adr250abaAddress1
     */
    public String getAdr250abaAddress1() {
        return adr250abaAddress1__;
    }

    /**
     * @param adr250abaAddress1 設定する adr250abaAddress1
     */
    public void setAdr250abaAddress1(String adr250abaAddress1) {
        this.adr250abaAddress1__ = adr250abaAddress1;
    }

    /**
     * @return adr250abaAddress2
     */
    public String getAdr250abaAddress2() {
        return adr250abaAddress2__;
    }

    /**
     * @param adr250abaAddress2 設定する adr250abaAddress2
     */
    public void setAdr250abaAddress2(String adr250abaAddress2) {
        this.adr250abaAddress2__ = adr250abaAddress2;
    }

    /**
     * @return adr250abaBiko
     */
    public String getAdr250abaBiko() {
        return adr250abaBiko__;
    }

    /**
     * @param adr250abaBiko 設定する adr250abaBiko
     */
    public void setAdr250abaBiko(String adr250abaBiko) {
        this.adr250abaBiko__ = adr250abaBiko;
    }

    /**
     * @return adr250abaName
     */
    public String getAdr250abaName() {
        return adr250abaName__;
    }

    /**
     * @param adr250abaName 設定する adr250abaName
     */
    public void setAdr250abaName(String adr250abaName) {
        this.adr250abaName__ = adr250abaName;
    }

    /**
     * @return adr250abaPostno1
     */
    public String getAdr250abaPostno1() {
        return adr250abaPostno1__;
    }

    /**
     * @param adr250abaPostno1 設定する adr250abaPostno1
     */
    public void setAdr250abaPostno1(String adr250abaPostno1) {
        this.adr250abaPostno1__ = adr250abaPostno1;
    }

    /**
     * @return adr250abaPostno2
     */
    public String getAdr250abaPostno2() {
        return adr250abaPostno2__;
    }

    /**
     * @param adr250abaPostno2 設定する adr250abaPostno2
     */
    public void setAdr250abaPostno2(String adr250abaPostno2) {
        this.adr250abaPostno2__ = adr250abaPostno2;
    }

    /**
     * @return adr250abaTdfk
     */
    public int getAdr250abaTdfk() {
        return adr250abaTdfk__;
    }

    /**
     * @param adr250abaTdfk 設定する adr250abaTdfk
     */
    public void setAdr250abaTdfk(int adr250abaTdfk) {
        this.adr250abaTdfk__ = adr250abaTdfk;
    }

    /**
     * @return adr250abaType
     */
    public int getAdr250abaType() {
        return adr250abaType__;
    }

    /**
     * @param adr250abaType 設定する adr250abaType
     */
    public void setAdr250abaType(int adr250abaType) {
        this.adr250abaType__ = adr250abaType;
    }

    /**
     * @return adr250atiList
     */
    public String[] getAdr250atiList() {
        return adr250atiList__;
    }

    /**
     * @param adr250atiList 設定する adr250atiList
     */
    public void setAdr250atiList(String[] adr250atiList) {
        this.adr250atiList__ = adr250atiList;
    }

    /**
     * @return adr250biko
     */
    public String getAdr250biko() {
        return adr250biko__;
    }

    /**
     * @param adr250biko 設定する adr250biko
     */
    public void setAdr250biko(String adr250biko) {
        this.adr250biko__ = adr250biko;
    }

    /**
     * @return adr250coCode
     */
    public String getAdr250coCode() {
        return adr250coCode__;
    }

    /**
     * @param adr250coCode 設定する adr250coCode
     */
    public void setAdr250coCode(String adr250coCode) {
        this.adr250coCode__ = adr250coCode;
    }

    /**
     * @return adr250coName
     */
    public String getAdr250coName() {
        return adr250coName__;
    }

    /**
     * @param adr250coName 設定する adr250coName
     */
    public void setAdr250coName(String adr250coName) {
        this.adr250coName__ = adr250coName;
    }

    /**
     * @return adr250coNameKn
     */
    public String getAdr250coNameKn() {
        return adr250coNameKn__;
    }

    /**
     * @param adr250coNameKn 設定する adr250coNameKn
     */
    public void setAdr250coNameKn(String adr250coNameKn) {
        this.adr250coNameKn__ = adr250coNameKn;
    }

    /**
     * <p>adr250coPostno1 を取得します。
     * @return adr250coPostno1
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coPostno1__
     */
    public String getAdr250coPostno1() {
        return adr250coPostno1__;
    }

    /**
     * <p>adr250coPostno1 をセットします。
     * @param adr250coPostno1 adr250coPostno1
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coPostno1__
     */
    public void setAdr250coPostno1(String adr250coPostno1) {
        adr250coPostno1__ = adr250coPostno1;
    }

    /**
     * <p>adr250coPostno2 を取得します。
     * @return adr250coPostno2
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coPostno2__
     */
    public String getAdr250coPostno2() {
        return adr250coPostno2__;
    }

    /**
     * <p>adr250coPostno2 をセットします。
     * @param adr250coPostno2 adr250coPostno2
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coPostno2__
     */
    public void setAdr250coPostno2(String adr250coPostno2) {
        adr250coPostno2__ = adr250coPostno2;
    }

    /**
     * <p>adr250coTdfk を取得します。
     * @return adr250coTdfk
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coTdfk__
     */
    public int getAdr250coTdfk() {
        return adr250coTdfk__;
    }

    /**
     * <p>adr250coTdfk をセットします。
     * @param adr250coTdfk adr250coTdfk
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coTdfk__
     */
    public void setAdr250coTdfk(int adr250coTdfk) {
        adr250coTdfk__ = adr250coTdfk;
    }

    /**
     * <p>adr250coTdfkName を取得します。
     * @return adr250coTdfkName
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coTdfkName__
     */
    public String getAdr250coTdfkName() {
        return adr250coTdfkName__;
    }

    /**
     * <p>adr250coTdfkName をセットします。
     * @param adr250coTdfkName adr250coTdfkName
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coTdfkName__
     */
    public void setAdr250coTdfkName(String adr250coTdfkName) {
        adr250coTdfkName__ = adr250coTdfkName;
    }

    /**
     * <p>adr250coAddress1 を取得します。
     * @return adr250coAddress1
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coAddress1__
     */
    public String getAdr250coAddress1() {
        return adr250coAddress1__;
    }

    /**
     * <p>adr250coAddress1 をセットします。
     * @param adr250coAddress1 adr250coAddress1
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coAddress1__
     */
    public void setAdr250coAddress1(String adr250coAddress1) {
        adr250coAddress1__ = adr250coAddress1;
    }

    /**
     * <p>adr250coAddress2 を取得します。
     * @return adr250coAddress2
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coAddress2__
     */
    public String getAdr250coAddress2() {
        return adr250coAddress2__;
    }

    /**
     * <p>adr250coAddress2 をセットします。
     * @param adr250coAddress2 adr250coAddress2
     * @see jp.groupsession.v2.adr.adr250.Adr250ParamModel#adr250coAddress2__
     */
    public void setAdr250coAddress2(String adr250coAddress2) {
        adr250coAddress2__ = adr250coAddress2;
    }

    /**
     * @return adr250NoSelectAtiList
     */
    public String[] getAdr250NoSelectAtiList() {
        return adr250NoSelectAtiList__;
    }

    /**
     * @param adr250NoSelectAtiList 設定する adr250NoSelectAtiList
     */
    public void setAdr250NoSelectAtiList(String[] adr250NoSelectAtiList) {
        this.adr250NoSelectAtiList__ = adr250NoSelectAtiList;
    }

    /**
     * @return adr250selectAtiList
     */
    public String[] getAdr250selectAtiList() {
        return adr250selectAtiList__;
    }

    /**
     * @param adr250selectAtiList 設定する adr250selectAtiList
     */
    public void setAdr250selectAtiList(String[] adr250selectAtiList) {
        this.adr250selectAtiList__ = adr250selectAtiList;
    }

    /**
     * @return adr250url
     */
    public String getAdr250url() {
        return adr250url__;
    }

    /**
     * @param adr250url 設定する adr250url
     */
    public void setAdr250url(String adr250url) {
        this.adr250url__ = adr250url;
    }

    /**
     * @return noSelectAtiCombo
     */
    public List<LabelValueBean> getNoSelectAtiCombo() {
        return noSelectAtiCombo__;
    }

    /**
     * @param noSelectAtiCombo 設定する noSelectAtiCombo
     */
    public void setNoSelectAtiCombo(List<LabelValueBean> noSelectAtiCombo) {
        this.noSelectAtiCombo__ = noSelectAtiCombo;
    }

    /**
     * @return selectAtiCombo
     */
    public List<LabelValueBean> getSelectAtiCombo() {
        return selectAtiCombo__;
    }

    /**
     * @param selectAtiCombo 設定する selectAtiCombo
     */
    public void setSelectAtiCombo(List<LabelValueBean> selectAtiCombo) {
        this.selectAtiCombo__ = selectAtiCombo;
    }

    /**
     * @return adr250AcoSid
     */
    public int getAdr250AcoSid() {
        return adr250AcoSid__;
    }

    /**
     * @param adr250AcoSid 設定する adr250AcoSid
     */
    public void setAdr250AcoSid(int adr250AcoSid) {
        this.adr250AcoSid__ = adr250AcoSid;
    }

}