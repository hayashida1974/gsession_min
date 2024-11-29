package jp.groupsession.v2.wml.wml270;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール 送信先リスト 送信先情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml270DestListAddressModel extends AbstractModel {

    /** 種別 */
    private int type__ = 0;
    /** 名称 */
    private String name__ = null;
    /**
     * <p>type を取得します。
     * @return type
     */
    public int getType() {
        return type__;
    }
    /**
     * <p>type をセットします。
     * @param type type
     */
    public void setType(int type) {
        type__ = type;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
}
