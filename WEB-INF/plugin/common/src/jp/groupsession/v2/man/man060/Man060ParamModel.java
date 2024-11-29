package jp.groupsession.v2.man.man060;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ディスク容量管理画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man060ParamModel extends AbstractParamModel {
    /** 容量 */
    private int man060capacity__ = -1;

    //表示項目
    /** 容量一覧 */
    private List<LabelValueBean> capacityList__ = null;
    /** 空き容量 */
    private String freeSpace__ = null;

    /**
     * <p>capacityList を取得します。
     * @return capacityList
     */
    public List<LabelValueBean> getCapacityList() {
        return capacityList__;
    }
    /**
     * <p>capacityList をセットします。
     * @param capacityList capacityList
     */
    public void setCapacityList(List<LabelValueBean> capacityList) {
        capacityList__ = capacityList;
    }
    /**
     * <p>man060capacity を取得します。
     * @return man060capacity
     */
    public int getMan060capacity() {
        return man060capacity__;
    }
    /**
     * <p>man060capacity をセットします。
     * @param man060capacity man060capacity
     */
    public void setMan060capacity(int man060capacity) {
        man060capacity__ = man060capacity;
    }
    /**
     * <p>freeSpace を取得します。
     * @return freeSpace
     */
    public String getFreeSpace() {
        return freeSpace__;
    }
    /**
     * <p>freeSpace をセットします。
     * @param freeSpace freeSpace
     */
    public void setFreeSpace(String freeSpace) {
        freeSpace__ = freeSpace;
    }
}