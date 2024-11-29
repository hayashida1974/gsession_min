package jp.groupsession.v2.cmn.cmn180;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 天気予報 地域情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn180AreaModel extends AbstractModel {

    /** 地域SID */
    private int areaId__ = 0;
    /** 地域名 */
    private String areaName__ = null;

    /**
     * <p>areaId を取得します。
     * @return areaId
     */
    public int getAreaId() {
        return areaId__;
    }
    /**
     * <p>areaId をセットします。
     * @param areaId areaId
     */
    public void setAreaId(int areaId) {
        areaId__ = areaId;
    }
    /**
     * <p>areaName を取得します。
     * @return areaName
     */
    public String getAreaName() {
        return areaName__;
    }
    /**
     * <p>areaName をセットします。
     * @param areaName areaName
     */
    public void setAreaName(String areaName) {
        areaName__ = areaName;
    }
}
