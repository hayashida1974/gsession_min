package jp.groupsession.v2.man.man370;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 言語情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man370Model extends AbstractModel {

    /** 言語コード */
    private String man370lngCode__;
    /** 言語名 */
    private String man370lngName__;
    /** 国コード */
    private String man370cntCode__;
    /**
     * <p>man370lngCode を取得します。
     * @return man370lngCode
     */
    public String getMan370lngCode() {
        return man370lngCode__;
    }
    /**
     * <p>man370lngCode をセットします。
     * @param man370lngCode man370lngCode
     */
    public void setMan370lngCode(String man370lngCode) {
        man370lngCode__ = man370lngCode;
    }
    /**
     * <p>man370lngName を取得します。
     * @return man370lngName
     */
    public String getMan370lngName() {
        return man370lngName__;
    }
    /**
     * <p>man370lngName をセットします。
     * @param man370lngName man370lngName
     */
    public void setMan370lngName(String man370lngName) {
        man370lngName__ = man370lngName;
    }
    /**
     * <p>man370cntCode を取得します。
     * @return man370cntCode
     */
    public String getMan370cntCode() {
        return man370cntCode__;
    }
    /**
     * <p>man370cntCode をセットします。
     * @param man370cntCode man370cntCode
     */
    public void setMan370cntCode(String man370cntCode) {
        man370cntCode__ = man370cntCode;
    }
}