package jp.groupsession.v2.wml.wml010.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール アカウントのディレクトリ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010DirectoryModel extends AbstractModel {

    /** ディレクトリSID */
    private long id__ = 0;
    /** ディレクトリ名称 */
    private String name__ = null;
    /** ディレクトリ種別 */
    private int type__ = -1;
    /** 未読件数 */
    private long noReadCount__ = 0;

    /**
     * <p>id を取得します。
     * @return id
     */
    public long getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(long id) {
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

}
