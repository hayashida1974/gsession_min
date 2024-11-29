package jp.groupsession.v2.sml.sml030;

import java.util.HashMap;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] Hashステータスを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考] 全データ中の位置を把握するために使用
 *
 * @author JTS
 */
public class HashControlModel extends AbstractModel {

    /** 該当行番号 */
    private int rowNum__;
    /** 全データハッシュ */
    private HashMap<Integer, Sml030Model> map__;

    /**
     * @return map を戻します。
     */
    public HashMap<Integer, Sml030Model> getMap() {
        return map__;
    }
    /**
     * @param map 設定する map。
     */
    public void setMap(HashMap<Integer, Sml030Model> map) {
        map__ = map;
    }
    /**
     * @return rowNum を戻します。
     */
    public int getRowNum() {
        return rowNum__;
    }
    /**
     * @param rowNum 設定する rowNum。
     */
    public void setRowNum(int rowNum) {
        rowNum__ = rowNum;
    }
}