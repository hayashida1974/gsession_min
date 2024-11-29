package jp.groupsession.v2.adr.adr070kn;

import java.util.List;

import jp.groupsession.v2.adr.adr070.Adr070Form;

/**
 * <br>[機  能] アドレス帳 アドレスインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr070knForm extends Adr070Form {

    /** 取込ファイル名 */
    private String adr070knFileName__ = null;
    /** 取込みアドレス氏名 */
    private List<String> adr070knNameList__ = null;
    /** 会社ファイル名 */
    private String adr070knCompanyName__ = null;
    /** 支店・営業所名 */
    private String adr070knCompanyBaseName__ = null;
    /** 取込み会社名 */
    private List<String> adr070knComList__ = null;
    /** 追加役職 */
    private List<String> adr070knPositionList__ = null;

    /**
     * <p>adr070knCompanyBaseName を取得します。
     * @return adr070knCompanyBaseName
     */
    public String getAdr070knCompanyBaseName() {
        return adr070knCompanyBaseName__;
    }
    /**
     * <p>adr070knCompanyBaseName をセットします。
     * @param adr070knCompanyBaseName adr070knCompanyBaseName
     */
    public void setAdr070knCompanyBaseName(String adr070knCompanyBaseName) {
        adr070knCompanyBaseName__ = adr070knCompanyBaseName;
    }
    /**
     * <p>adr070knCompanyName を取得します。
     * @return adr070knCompanyName
     */
    public String getAdr070knCompanyName() {
        return adr070knCompanyName__;
    }
    /**
     * <p>adr070knCompanyName をセットします。
     * @param adr070knCompanyName adr070knCompanyName
     */
    public void setAdr070knCompanyName(String adr070knCompanyName) {
        adr070knCompanyName__ = adr070knCompanyName;
    }
    /**
     * <p>adr070knFileName を取得します。
     * @return adr070knFileName
     */
    public String getAdr070knFileName() {
        return adr070knFileName__;
    }
    /**
     * <p>adr070knFileName をセットします。
     * @param adr070knFileName adr070knFileName
     */
    public void setAdr070knFileName(String adr070knFileName) {
        adr070knFileName__ = adr070knFileName;
    }
    /**
     * <p>adr070knNameList を取得します。
     * @return adr070knNameList
     */
    public List<String> getAdr070knNameList() {
        return adr070knNameList__;
    }
    /**
     * <p>adr070knNameList をセットします。
     * @param adr070knNameList adr070knNameList
     */
    public void setAdr070knNameList(List<String> adr070knNameList) {
        adr070knNameList__ = adr070knNameList;
    }
    /**
     * <p>adr070knComList を取得します。
     * @return adr070knComList
     */
    public List<String> getAdr070knComList() {
        return adr070knComList__;
    }
    /**
     * <p>adr070knComList をセットします。
     * @param adr070knComList adr070knComList
     */
    public void setAdr070knComList(List<String> adr070knComList) {
        adr070knComList__ = adr070knComList;
    }
    /**
     * <p>adr070knPositionList を取得します。
     * @return adr070knPositionList
     */
    public List<String> getAdr070knPositionList() {
        return adr070knPositionList__;
    }
    /**
     * <p>adr070knPositionList をセットします。
     * @param adr070knPositionList adr070knPositionList
     */
    public void setAdr070knPositionList(List<String> adr070knPositionList) {
        adr070knPositionList__ = adr070knPositionList;
    }
}