package jp.groupsession.v2.man.man330kn.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] CSVファイルから取得した所属情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330knCsvModel extends AbstractModel {

    /** ユーザID */
    private String userId__;
    /** グループデータ */
    private List<String> grpDataList__;

    /**
     * <p>userId を取得します。
     * @return userId
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>grpDataList を取得します。
     * @return grpDataList
     */
    public List<String> getGrpDataList() {
        return grpDataList__;
    }
    /**
     * <p>grpDataList をセットします。
     * @param grpDataList grpDataList
     */
    public void setGrpDataList(List<String> grpDataList) {
        grpDataList__ = grpDataList;
    }
}