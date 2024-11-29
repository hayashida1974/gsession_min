package jp.groupsession.v2.adr.adr120kn;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.adr.adr120.Adr120Form;

/**
 * <br>[機  能] アドレス帳 会社インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr120knForm extends Adr120Form {

    /** 取込ファイル名 */
    private String adr120knFileName__ = null;
    /** 取込会社情報一覧 */
    private List<Adr120knCompanyData> adr120knCompanyList__ = null;
    /** 取込会社件数 */
    private int adr120knCompanyCnt__ = 0;
    /** 所属業種一覧 */
    private List<LabelValueBean> adr120knAtiList__ = null;

    /**
     * <p>adr120knCompanyList を取得します。
     * @return adr120knCompanyList
     */
    public List<Adr120knCompanyData> getAdr120knCompanyList() {
        return adr120knCompanyList__;
    }
    /**
     * <p>adr120knCompanyList をセットします。
     * @param adr120knCompanyList adr120knCompanyList
     */
    public void setAdr120knCompanyList(List<Adr120knCompanyData> adr120knCompanyList) {
        adr120knCompanyList__ = adr120knCompanyList;
    }
    /**
     * <p>adr120knFileName を取得します。
     * @return adr120knFileName
     */
    public String getAdr120knFileName() {
        return adr120knFileName__;
    }
    /**
     * <p>adr120knFileName をセットします。
     * @param adr120knFileName adr120knFileName
     */
    public void setAdr120knFileName(String adr120knFileName) {
        adr120knFileName__ = adr120knFileName;
    }
    /**
     * @return adr120knCompanyCnt
     */
    public int getAdr120knCompanyCnt() {
        return adr120knCompanyCnt__;
    }
    /**
     * @param adr120knCompanyCnt 設定する adr120knCompanyCnt
     */
    public void setAdr120knCompanyCnt(int adr120knCompanyCnt) {
        adr120knCompanyCnt__ = adr120knCompanyCnt;
    }
    /**
     * <p>adr120knAtiList を取得します。
     * @return adr120knAtiList
     */
    public List<LabelValueBean> getAdr120knAtiList() {
        return adr120knAtiList__;
    }
    /**
     * <p>adr120knAtiList をセットします。
     * @param adr120knAtiList adr120knAtiList
     */
    public void setAdr120knAtiList(List<LabelValueBean> adr120knAtiList) {
        adr120knAtiList__ = adr120knAtiList;
    }

}
