package jp.groupsession.v2.adr.adr110kn;

import java.util.List;

import jp.groupsession.v2.adr.adr110.Adr110ParamModel;
import jp.groupsession.v2.adr.adr110kn.model.Adr110knModel;

/**
 * <br>[機  能] アドレス帳 会社登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110knParamModel extends Adr110ParamModel {

    /** 会社拠点 都道府県名称 */
    private String adr110knViewCoTdfkName__ = null;
    /** 所属業種 (表示用) */
    private String[] adr110knViewAtiList__ = null;
    /** 備考(表示用) */
    private String adr110knViewBiko__ = null;
    /** アドレス情報一覧 */
    private List<Adr110knModel> adr110knAdrInfList__ = null;
    /** アドレス登録件数 */
    private String adr110knAdrCount__ = null;

    /**
     * <p>adr110knViewCoTdfkName を取得します。
     * @return adr110knViewCoTdfkName
     * @see jp.groupsession.v2.adr.adr110kn.Adr110knParamModel#adr110knViewCoTdfkName__
     */
    public String getAdr110knViewCoTdfkName() {
        return adr110knViewCoTdfkName__;
    }
    /**
     * <p>adr110knViewCoTdfkName をセットします。
     * @param adr110knViewCoTdfkName adr110knViewCoTdfkName
     * @see jp.groupsession.v2.adr.adr110kn.Adr110knParamModel#adr110knViewCoTdfkName__
     */
    public void setAdr110knViewCoTdfkName(String adr110knViewCoTdfkName) {
        adr110knViewCoTdfkName__ = adr110knViewCoTdfkName;
    }
    /**
     * <p>adr110knAdrInfList を取得します。
     * @return adr110knAdrInfList
     */
    public List<Adr110knModel> getAdr110knAdrInfList() {
        return adr110knAdrInfList__;
    }
    /**
     * <p>adr110knAdrInfList をセットします。
     * @param adr110knAdrInfList adr110knAdrInfList
     */
    public void setAdr110knAdrInfList(List<Adr110knModel> adr110knAdrInfList) {
        adr110knAdrInfList__ = adr110knAdrInfList;
    }
    /**
     * <p>adr110knViewAtiList を取得します。
     * @return adr110knViewAtiList
     */
    public String[] getAdr110knViewAtiList() {
        return adr110knViewAtiList__;
    }
    /**
     * <p>adr110knViewAtiList をセットします。
     * @param adr110knViewAtiList adr110knViewAtiList
     */
    public void setAdr110knViewAtiList(String[] adr110knViewAtiList) {
        adr110knViewAtiList__ = adr110knViewAtiList;
    }
    /**
     * <p>adr110knViewBiko を取得します。
     * @return adr110knViewBiko
     */
    public String getAdr110knViewBiko() {
        return adr110knViewBiko__;
    }
    /**
     * <p>adr110knViewBiko をセットします。
     * @param adr110knViewBiko adr110knViewBiko
     */
    public void setAdr110knViewBiko(String adr110knViewBiko) {
        adr110knViewBiko__ = adr110knViewBiko;
    }
    /**
     * <p>adr110knAdrCount を取得します。
     * @return adr110knAdrCount
     */
    public String getAdr110knAdrCount() {
        return adr110knAdrCount__;
    }
    /**
     * <p>adr110knAdrCount をセットします。
     * @param adr110knAdrCount adr110knAdrCount
     */
    public void setAdr110knAdrCount(String adr110knAdrCount) {
        adr110knAdrCount__ = adr110knAdrCount;
    }

}
