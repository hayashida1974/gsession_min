package jp.groupsession.v2.adr.adr240.model;

import java.io.Serializable;

/**
 * <br>[機  能] アドレス帳 会社選択画面の検索結果を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr240Model implements Serializable {

    /** ACO_SID mapping */
    private int acoSid__;
    /** ACO_NAME mapping */
    private String acoName__;
    /** ACO_CODE mapping */
    private String acoCode__;
    /** ABA_NAME mapping */
    private int abaSid__;
    /** ABA_NAME mapping */
    private String abaName__;

    /**
     * @return abaName__
     */
    public String getAbaName() {
        return abaName__;
    }
    /**
     * @param abaName 設定する abaName
     */
    public void setAbaName(String abaName) {
        this.abaName__ = abaName;
    }
    /**
     * @return acoName
     */
    public String getAcoName() {
        return acoName__;
    }
    /**
     * @param acoName 設定する acoName
     */
    public void setAcoName(String acoName) {
        this.acoName__ = acoName;
    }
    /**
     * @return acoSid
     */
    public int getAcoSid() {
        return acoSid__;
    }
    /**
     * @param acoSid 設定する acoSid
     */
    public void setAcoSid(int acoSid) {
        this.acoSid__ = acoSid;
    }
    /**
     * @return abaSid
     */
    public int getAbaSid() {
        return abaSid__;
    }
    /**
     * @param abaSid 設定する abaSid
     */
    public void setAbaSid(int abaSid) {
        this.abaSid__ = abaSid;
    }
    /**
     * <p>acoCode を取得します。
     * @return acoCode
     */
    public String getAcoCode() {
        return acoCode__;
    }
    /**
     * <p>acoCode をセットします。
     * @param acoCode acoCode
     */
    public void setAcoCode(String acoCode) {
        acoCode__ = acoCode;
    }

}
