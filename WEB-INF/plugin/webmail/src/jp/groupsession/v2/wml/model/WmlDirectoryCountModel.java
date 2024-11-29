package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;

/**
 * <br>[機  能] ディレクトリ情報とその未読件数を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlDirectoryCountModel extends AbstractModel {

    /** ディレクトリSID */
    private long id__ = 0;
    /** ディレクトリ名称 */
    private String name__ = null;
    /** ディレクトリ種別 */
    private int type__ = -1;
    /** 未読件数 */
    private long noReadCount__ = 0;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl WmlDirectoryModel
     */
    public WmlDirectoryCountModel (WmlDirectoryModel mdl) {
        id__ = mdl.getWdrSid();
        name__ = mdl.getWdrName();
        type__ = mdl.getWdrType();
    }

    /**
     * <p>id を取得します。
     * @return id
     */
    public long getId() {
        return id__;
    }

    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
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

}
