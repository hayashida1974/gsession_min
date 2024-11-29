package jp.groupsession.v2.man.man110;

import jp.groupsession.v2.man.man100.Man100ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 役職登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man110ParamModel extends Man100ParamModel {
    //入力項目
    /** 役職コード */
    private String man110posCode__;
    /** 役職名 */
    private String man110posName__;
    /** 備考 */
    private String man110bikou__;

    /**
     * <p>man110bikou を取得します。
     * @return man110bikou
     */
    public String getMan110bikou() {
        return man110bikou__;
    }
    /**
     * <p>man110bikou をセットします。
     * @param man110bikou man110bikou
     */
    public void setMan110bikou(String man110bikou) {
        man110bikou__ = man110bikou;
    }
    /**
     * <p>man110posName を取得します。
     * @return man110posName
     */
    public String getMan110posName() {
        return man110posName__;
    }
    /**
     * <p>man110posName をセットします。
     * @param man110posName man110posName
     */
    public void setMan110posName(String man110posName) {
        man110posName__ = man110posName;
    }
    /**
     * <p>man110posCode を取得します。
     * @return man110posCode
     */
    public String getMan110posCode() {
        return man110posCode__;
    }
    /**
     * <p>man110posCode をセットします。
     * @param man110posCode man110posCode
     */
    public void setMan110posCode(String man110posCode) {
        man110posCode__ = man110posCode;
    }
}