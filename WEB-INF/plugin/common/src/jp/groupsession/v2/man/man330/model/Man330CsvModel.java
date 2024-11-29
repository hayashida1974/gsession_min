package jp.groupsession.v2.man.man330.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 所属情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330CsvModel extends AbstractModel {

    /** ユーザID */
    private String userId__;
    /** 氏名 */
    private String usrName__;
    /** 氏名カナ */
    private String usrNameKana__;
    /** グループデータ */
    private List<Man330CsvGroupDataModel> grpDataList__;

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
     * <p>usrName を取得します。
     * @return usrName
     */
    public String getUsrName() {
        return usrName__;
    }
    /**
     * <p>usrName をセットします。
     * @param usrName usrName
     */
    public void setUsrName(String usrName) {
        usrName__ = usrName;
    }
    /**
     * <p>usrNameKana を取得します。
     * @return usrNameKana
     */
    public String getUsrNameKana() {
        return usrNameKana__;
    }
    /**
     * <p>usrNameKana をセットします。
     * @param usrNameKana usrNameKana
     */
    public void setUsrNameKana(String usrNameKana) {
        usrNameKana__ = usrNameKana;
    }
    /**
     * <p>grpDataList を取得します。
     * @return grpDataList
     */
    public List<Man330CsvGroupDataModel> getGrpDataList() {
        return grpDataList__;
    }
    /**
     * <p>grpDataList をセットします。
     * @param grpDataList grpDataList
     */
    public void setGrpDataList(List<Man330CsvGroupDataModel> grpDataList) {
        grpDataList__ = grpDataList;
    }
}