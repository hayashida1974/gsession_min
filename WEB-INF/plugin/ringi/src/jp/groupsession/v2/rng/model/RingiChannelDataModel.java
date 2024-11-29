package jp.groupsession.v2.rng.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;


/**
 * <br>[機  能] 稟議経路情報を格納するのModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiChannelDataModel extends RngChannelModel {

    /** ユーザ氏名 */
    private String userName__ = null;
    /** ユーザ氏名カナ */
    private String userNameKn__ = null;
    /** 役職 */
    private String yakusyoku__ = null;
    /** 確認日(文字列) */
    private String strRncChkDate__ = null;

    /** 添付ファイル情報 */
    private List<CmnBinfModel> tmpFileList__ = null;

    /** 回覧板プラグイン使用可/不可 */
    private boolean ringiUse__ = true;
    /** 削除ユーザか */
    private boolean delUser__ = false;
    /** ログイン停止ユーザか */
    private int usrUkoFlg__ = 0;

    /**
     * <p>strRncChkDate を取得します。
     * @return strRncChkDate
     */
    public String getStrRncChkDate() {
        return strRncChkDate__;
    }

    /**
     * <p>strRncChkDate をセットします。
     * @param strRncChkDate strRncChkDate
     */
    public void setStrRncChkDate(String strRncChkDate) {
        strRncChkDate__ = strRncChkDate;
    }

    /**
     * <p>tmpFileList を取得します。
     * @return tmpFileList
     */
    public List<CmnBinfModel> getTmpFileList() {
        return tmpFileList__;
    }

    /**
     * <p>tmpFileList をセットします。
     * @param tmpFileList tmpFileList
     */
    public void setTmpFileList(List<CmnBinfModel> tmpFileList) {
        tmpFileList__ = tmpFileList;
    }

    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }

    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }

    /**
     * <p>userNameKn を取得します。
     * @return userNameKn
     */
    public String getUserNameKn() {
        return userNameKn__;
    }

    /**
     * <p>userNameKn をセットします。
     * @param userNameKn userNameKn
     */
    public void setUserNameKn(String userNameKn) {
        userNameKn__ = userNameKn;
    }

    /**
     * <p>yakusyoku を取得します。
     * @return yakusyoku
     */
    public String getYakusyoku() {
        return yakusyoku__;
    }

    /**
     * <p>yakusyoku をセットします。
     * @param yakusyoku yakusyoku
     */
    public void setYakusyoku(String yakusyoku) {
        yakusyoku__ = yakusyoku;
    }

    /**
     * <p>ringiUse を取得します。
     * @return ringiUse
     */
    public boolean isRingiUse() {
        return ringiUse__;
    }

    /**
     * <p>ringiUse をセットします。
     * @param ringiUse ringiUse
     */
    public void setRingiUse(boolean ringiUse) {
        ringiUse__ = ringiUse;
    }

    /**
     * <p>delUser を取得します。
     * @return delUser
     */
    public boolean isDelUser() {
        return delUser__;
    }

    /**
     * <p>delUser をセットします。
     * @param delUser delUser
     */
    public void setDelUser(boolean delUser) {
        delUser__ = delUser;
    }

    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }

    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }

}
