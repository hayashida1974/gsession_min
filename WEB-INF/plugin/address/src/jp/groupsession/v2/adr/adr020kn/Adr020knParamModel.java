package jp.groupsession.v2.adr.adr020kn;

import java.util.List;

import jp.groupsession.v2.adr.adr020.Adr020ParamModel;

/**
 * <br>[機  能] アドレス帳登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr020knParamModel extends Adr020ParamModel {

    /** 役職名称 */
    private String adr020knPositionName__ = null;
    /** 都道府県名称 */
    private String adr020knTdfkName__ = null;
    /** 備考(表示用) */
    private String adr020knViewBiko__ = null;
    /** 担当者名一覧 */
    private List<String> adr020knTantoNameList__ = null;
    /** 閲覧権限一覧 */
    private List<String> adr020knPermitViewList__ = null;
    /** 編集権限一覧 */
    private List<String> adr020knPermitEditList__ = null;

    /**
     * <p>adr020knPermitEditList を取得します。
     * @return adr020knPermitEditList
     */
    public List<String> getAdr020knPermitEditList() {
        return adr020knPermitEditList__;
    }
    /**
     * <p>adr020knPermitEditList をセットします。
     * @param adr020knPermitEditList adr020knPermitEditList
     */
    public void setAdr020knPermitEditList(List<String> adr020knPermitEditList) {
        adr020knPermitEditList__ = adr020knPermitEditList;
    }
    /**
     * <p>adr020knPermitViewList を取得します。
     * @return adr020knPermitViewList
     */
    public List<String> getAdr020knPermitViewList() {
        return adr020knPermitViewList__;
    }
    /**
     * <p>adr020knPermitViewList をセットします。
     * @param adr020knPermitViewList adr020knPermitViewList
     */
    public void setAdr020knPermitViewList(List<String> adr020knPermitViewList) {
        adr020knPermitViewList__ = adr020knPermitViewList;
    }
    /**
     * <p>adr020knPositionName を取得します。
     * @return adr020knPositionName
     */
    public String getAdr020knPositionName() {
        return adr020knPositionName__;
    }
    /**
     * <p>adr020knPositionName をセットします。
     * @param adr020knPositionName adr020knPositionName
     */
    public void setAdr020knPositionName(String adr020knPositionName) {
        adr020knPositionName__ = adr020knPositionName;
    }
    /**
     * <p>adr020knTantoNameList を取得します。
     * @return adr020knTantoNameList
     */
    public List<String> getAdr020knTantoNameList() {
        return adr020knTantoNameList__;
    }
    /**
     * <p>adr020knTantoNameList をセットします。
     * @param adr020knTantoNameList adr020knTantoNameList
     */
    public void setAdr020knTantoNameList(List<String> adr020knTantoNameList) {
        adr020knTantoNameList__ = adr020knTantoNameList;
    }
    /**
     * <p>adr020knTdfkName を取得します。
     * @return adr020knTdfkName
     */
    public String getAdr020knTdfkName() {
        return adr020knTdfkName__;
    }
    /**
     * <p>adr020knTdfkName をセットします。
     * @param adr020knTdfkName adr020knTdfkName
     */
    public void setAdr020knTdfkName(String adr020knTdfkName) {
        adr020knTdfkName__ = adr020knTdfkName;
    }
    /**
     * <p>adr020knViewBiko を取得します。
     * @return adr020knViewBiko
     */
    public String getAdr020knViewBiko() {
        return adr020knViewBiko__;
    }
    /**
     * <p>adr020knViewBiko をセットします。
     * @param adr020knViewBiko adr020knViewBiko
     */
    public void setAdr020knViewBiko(String adr020knViewBiko) {
        adr020knViewBiko__ = adr020knViewBiko;
    }
}
