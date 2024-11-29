package jp.groupsession.v2.adr.adr110kn.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス帳 会社登録確認画面のアドレス情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110knModel extends AbstractModel {

    /** アドレスSID */
    private int adrSid__ = 0;
    /** 氏名 */
    private String userName__ = null;
    /** 会社拠点名 */
    private String companyBaseName__ = null;
    /** 役職名 */
    private String positionName__ = null;

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
     * <p>positionName を取得します。
     * @return positionName
     */
    public String getPositionName() {
        return positionName__;
    }
    /**
     * <p>positionName をセットします。
     * @param positionName positionName
     */
    public void setPositionName(String positionName) {
        positionName__ = positionName;
    }
    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }
    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }
    /**
     * <p>adrSid を取得します。
     * @return adrSid
     */
    public int getAdrSid() {
        return adrSid__;
    }
    /**
     * <p>adrSid をセットします。
     * @param adrSid adrSid
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }
}