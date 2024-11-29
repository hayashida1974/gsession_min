package jp.groupsession.v2.adr.adr120kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 会社情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr120knCompanyData extends AbstractModel {

    /** 会社名 */
    private String companyName__ = null;
    /** 支店・営業所名一覧 */
    private List<String> companyBaseNameList__ = null;

    /**
     * <p>companyBaseNameList を取得します。
     * @return companyBaseNameList
     */
    public List<String> getCompanyBaseNameList() {
        return companyBaseNameList__;
    }
    /**
     * <p>companyBaseNameList をセットします。
     * @param companyBaseNameList companyBaseNameList
     */
    public void setCompanyBaseNameList(List<String> companyBaseNameList) {
        companyBaseNameList__ = companyBaseNameList;
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

}
