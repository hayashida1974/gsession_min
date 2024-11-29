package jp.groupsession.v2.cmn.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;


/**
 * <br>[機  能] ユーザCSV情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserCsvModel extends CmnUsrmInfModel {


    /** ユーザログインID */
    private String usrLgid__;

    /**
     * @return usrLgid を戻します。
     */
    public String getUsrLgid() {
        return usrLgid__;
    }

    /**
     * @param usrLgid 設定する usrLgid。
     */
    public void setUsrLgid(String usrLgid) {
        usrLgid__ = usrLgid;
    }


}