package jp.groupsession.v2.adr.adr250;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.adr.GSConstAddress;

/**
 * <br>[機  能] アドレス帳 会社情報ポップアップの拠点情報を保持するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr250BaseForm extends AbstractForm {

    /** 会社拠点情報 index */
    int adr250abaIndex__ = 0;

    /** 会社拠点 会社拠点SID(一覧) */
    int adr250abaSidDetail__ = 0;
    /** 会社拠点 種別(一覧) */
    int adr250abaTypeDetail__ = 0;
    /** 会社拠点 支店・営業所名(一覧) */
    String adr250abaName__ = null;
    /** 会社拠点 郵便番号上3桁(一覧) */
    String adr250abaPostno1__ = null;
    /** 会社拠点 郵便番号下4桁(一覧) */
    String adr250abaPostno2__ = null;
    /** 会社拠点 都道府県(一覧) */
    int adr250abaTdfk__ = 0;
    /** 会社拠点 都道府県名称(一覧) */
    String adr250abaTdfkName__ = null;
    /** 会社拠点 住所１(一覧) */
    String adr250abaAddress1__ = null;
    /** 会社拠点 住所２(一覧) */
    String adr250abaAddress2__ = null;
    /** 会社拠点 備考(一覧) */
    String adr250abaBiko__ = null;

    /**
     * <p>adr250abaIndex を取得します。
     * @return adr250abaIndex
     */
    public int getAdr250abaIndex() {
        return adr250abaIndex__;
    }

    /**
     * <p>adr250abaIndex をセットします。
     * @param adr250abaIndex adr250abaIndex
     */
    public void setAdr250abaIndex(int adr250abaIndex) {
        adr250abaIndex__ = adr250abaIndex;
    }

    /**
     * <p>adr250abaAddress1 を取得します。
     * @return adr250abaAddress1
     */
    public String getAdr250abaAddress1() {
        return adr250abaAddress1__;
    }

    /**
     * <p>adr250abaAddress1 をセットします。
     * @param adr250abaAddress1 adr250abaAddress1
     */
    public void setAdr250abaAddress1(String adr250abaAddress1) {
        adr250abaAddress1__ = adr250abaAddress1;
    }

    /**
     * <p>adr250abaAddress2 を取得します。
     * @return adr250abaAddress2
     */
    public String getAdr250abaAddress2() {
        return adr250abaAddress2__;
    }

    /**
     * <p>adr250abaAddress2 をセットします。
     * @param adr250abaAddress2 adr250abaAddress2
     */
    public void setAdr250abaAddress2(String adr250abaAddress2) {
        adr250abaAddress2__ = adr250abaAddress2;
    }

    /**
     * <p>adr250abaBiko を取得します。
     * @return adr250abaBiko
     */
    public String getAdr250abaBiko() {
        return adr250abaBiko__;
    }

    /**
     * <p>adr250abaBiko をセットします。
     * @param adr250abaBiko adr250abaBiko
     */
    public void setAdr250abaBiko(String adr250abaBiko) {
        adr250abaBiko__ = adr250abaBiko;
    }

    /**
     * <p>adr250abaName を取得します。
     * @return adr250abaName
     */
    public String getAdr250abaName() {
        return adr250abaName__;
    }

    /**
     * <p>adr250abaName をセットします。
     * @param adr250abaName adr250abaName
     */
    public void setAdr250abaName(String adr250abaName) {
        adr250abaName__ = adr250abaName;
    }

    /**
     * <p>adr250abaPostno1 を取得します。
     * @return adr250abaPostno1
     */
    public String getAdr250abaPostno1() {
        return adr250abaPostno1__;
    }

    /**
     * <p>adr250abaPostno1 をセットします。
     * @param adr250abaPostno1 adr250abaPostno1
     */
    public void setAdr250abaPostno1(String adr250abaPostno1) {
        adr250abaPostno1__ = adr250abaPostno1;
    }

    /**
     * <p>adr250abaPostno2 を取得します。
     * @return adr250abaPostno2
     */
    public String getAdr250abaPostno2() {
        return adr250abaPostno2__;
    }

    /**
     * <p>adr250abaPostno2 をセットします。
     * @param adr250abaPostno2 adr250abaPostno2
     */
    public void setAdr250abaPostno2(String adr250abaPostno2) {
        adr250abaPostno2__ = adr250abaPostno2;
    }

    /**
     * <p>adr250abaSidDetail を取得します。
     * @return adr250abaSidDetail
     */
    public int getAdr250abaSidDetail() {
        return adr250abaSidDetail__;
    }

    /**
     * <p>adr250abaSidDetail をセットします。
     * @param adr250abaSidDetail adr250abaSidDetail
     */
    public void setAdr250abaSidDetail(int adr250abaSidDetail) {
        adr250abaSidDetail__ = adr250abaSidDetail;
    }

    /**
     * <p>adr250abaTdfk を取得します。
     * @return adr250abaTdfk
     */
    public int getAdr250abaTdfk() {
        return adr250abaTdfk__;
    }

    /**
     * <p>adr250abaTdfk をセットします。
     * @param adr250abaTdfk adr250abaTdfk
     */
    public void setAdr250abaTdfk(int adr250abaTdfk) {
        adr250abaTdfk__ = adr250abaTdfk;
    }

    /**
     * <p>adr250abaTypeDetail を取得します。
     * @return adr250abaTypeDetail
     */
    public int getAdr250abaTypeDetail() {
        return adr250abaTypeDetail__;
    }

    /**
     * <p>adr250abaTypeDetail をセットします。
     * @param adr250abaTypeDetail adr250abaTypeDetail
     */
    public void setAdr250abaTypeDetail(int adr250abaTypeDetail) {
        adr250abaTypeDetail__ = adr250abaTypeDetail;
    }

    /**
     * <p>adr250abaTdfkName を取得します。
     * @return adr250abaTdfkName
     */
    public String getAdr250abaTdfkName() {
        return adr250abaTdfkName__;
    }

    /**
     * <p>adr250abaTdfkName をセットします。
     * @param adr250abaTdfkName adr250abaTdfkName
     */
    public void setAdr250abaTdfkName(String adr250abaTdfkName) {
        adr250abaTdfkName__ = adr250abaTdfkName;
    }

    /**
     * <br>[機  能] 会社拠点 種別の名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 会社拠点種別名称
     */
    public String getAdr250abaTypeNameDetail() {
        String abaTypeName = null;
        switch (adr250abaTypeDetail__) {
            case GSConstAddress.ABATYPE_HEADOFFICE :
                //本社
                abaTypeName = "address.122";
                break;
            case GSConstAddress.ABATYPE_BRANCH :
                //支店
                abaTypeName = "address.123";
                break;
            case GSConstAddress.ABATYPE_BUSINESSOFFICE :
                //営業所
                abaTypeName = "address.124";
                break;
            default :
        }

        return abaTypeName;
    }

    /**
     * <br>[機  能] 会社拠点 備考(一覧 画面表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 備考(一覧 画面表示用)
     */
    public String getAdr250abaViewBiko() {
        return StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(adr250abaBiko__, ""));
    }
}
