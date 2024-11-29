package jp.groupsession.v2.adr.adr150.model;

import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アドレス帳 会社選択の検索結果情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr150DetailModel extends AbstractModel {

    /** 会社SID */
    private int acoSid__ = 0;
    /** 会社拠点SID */
    private int abaSid__ = 0;

    /** 会社名 */
    private String companyName__ = null;
    /** 会社区分 */
    private int companyBaseType__ = 0;
    /** 支店・営業所名 */
    private String companyBaseName__ = null;
    /** 都道府県 */
    private int tdfSid__ = 0;
    /** 都道府県名 */
    private String tdfkName__ = null;
    /** 住所１ */
    private String companyBaseAddress1__ = null;
    /** 住所２ */
    private String companyBaseAddress2__ = null;
    /** 備考 */
    private String companyBiko__ = null;

    /**
     * <p>abaSid を取得します。
     * @return abaSid
     */
    public int getAbaSid() {
        return abaSid__;
    }
    /**
     * <p>abaSid をセットします。
     * @param abaSid abaSid
     */
    public void setAbaSid(int abaSid) {
        abaSid__ = abaSid;
    }
    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public int getAcoSid() {
        return acoSid__;
    }
    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }
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
     * <p>companyBaseType を取得します。
     * @return companyBaseType
     */
    public int getCompanyBaseType() {
        return companyBaseType__;
    }
    /**
     * <p>companyBaseType をセットします。
     * @param companyBaseType companyBaseType
     */
    public void setCompanyBaseType(int companyBaseType) {
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
     * <p>tdfkName を取得します。
     * @return tdfkName
     */
    public String getTdfkName() {
        return tdfkName__;
    }
    /**
     * <p>tdfkName をセットします。
     * @param tdfkName tdfkName
     */
    public void setTdfkName(String tdfkName) {
        tdfkName__ = tdfkName;
    }
    /**
     * <p>tdfSid を取得します。
     * @return tdfSid
     */
    public int getTdfSid() {
        return tdfSid__;
    }
    /**
     * <p>tdfSid をセットします。
     * @param tdfSid tdfSid
     */
    public void setTdfSid(int tdfSid) {
        tdfSid__ = tdfSid;
    }

    /**
     * <br>[機  能] 会社区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @return 会社区分
     */
    public String getCompanyBaseTypeName(RequestModel reqMdl) {
        return AddressBiz.getCompanyBaseTypeName(companyBaseType__, reqMdl);
    }

}