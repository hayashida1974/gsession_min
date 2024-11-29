package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール ラベル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlLabelCountModel extends AbstractModel {

    /** ラベルSID */
    private int id__ = 0;
    /** ラベル名称 */
    private String name__ = null;
    /** 種別 */
    private int type__ = GSConstWebmail.LABELTYPE_ONES;
    /** 未読件数 */
    private long noReadCount__ = 0;

    /**
     * <p>id を取得します。
     * @return id
     */
    public int getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(int id) {
        id__ = id;
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
     * <p>noReadCount を取得します。
     * @return noReadCount
     */
    public long getNoReadCount() {
        return noReadCount__;
    }
    /**
     * <p>noReadCount をセットします。
     * @param noReadCount noReadCount
     */
    public void setNoReadCount(long noReadCount) {
        noReadCount__ = noReadCount;
    }
}
