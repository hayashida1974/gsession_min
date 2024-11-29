package jp.groupsession.v2.cmn.model;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] ユーザ情報、在席状況、バイナリー情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserSearchModel extends CmnUsrmInfModel {

    /** ユーザ在席ステータス */
    private int uioStatus__;
    /** ユーザ在席コメント */
    private String uioComment__;
    /** 写真 ファイルSid  */
    private int binFileSid__;
    /** 写真 ファイル名  */
    private String binFileName__;
    /** 写真 ファイル名  */
    private String binFilePath__;
    /** 写真 ファイル有無 */
    private int photoFileDsp__;
    /** ショートメール有効送信先フラグ 0:無効 1:有効 */
    private int smlAble__ = 0;
    /** スケジュールアクセス可能フラグ 0:不可 1:可能 */
    private int schAccessFlg__ = GSConstSchedule.SCH_ACCESS_NO;
    /** スケジュール登録可能フラグ */
    private boolean schRegistFlg__ = false;

    /**
     * <p>uioComment を取得します。
     * @return uioComment
     */
    public String getUioComment() {
        return uioComment__;
    }

    /**
     * <p>uioComment をセットします。
     * @param uioComment uioComment
     */
    public void setUioComment(String uioComment) {
        uioComment__ = uioComment;
    }

    /**
     * @return uioStatus を戻します。
     */
    public int getUioStatus() {
        return uioStatus__;
    }

    /**
     * @param uioStatus 設定する uioStatus。
     */
    public void setUioStatus(int uioStatus) {
        uioStatus__ = uioStatus;
    }

    /**
     * <p>binFileName を取得します。
     * @return binFileName
     */
    public String getBinFileName() {
        return binFileName__;
    }

    /**
     * <p>binFileName をセットします。
     * @param binFileName binFileName
     */
    public void setBinFileName(String binFileName) {
        binFileName__ = binFileName;
    }

    /**
     * <p>photoFileDsp を取得します。
     * @return photoFileDsp
     */
    public int getPhotoFileDsp() {
        return photoFileDsp__;
    }

    /**
     * <p>photoFileDsp をセットします。
     * @param photoFileDsp photoFileDsp
     */
    public void setPhotoFileDsp(int photoFileDsp) {
        photoFileDsp__ = photoFileDsp;
    }

    /**
     * <p>binFilePath を取得します。
     * @return binFilePath
     */
    public String getBinFilePath() {
        return binFilePath__;
    }

    /**
     * <p>binFilePath をセットします。
     * @param binFilePath binFilePath
     */
    public void setBinFilePath(String binFilePath) {
        binFilePath__ = binFilePath;
    }

    /**
     * <p>binFileSid を取得します。
     * @return binFileSid
     */
    public int getBinFileSid() {
        return binFileSid__;
    }

    /**
     * <p>binFileSid をセットします。
     * @param binFileSid binFileSid
     */
    public void setBinFileSid(int binFileSid) {
        binFileSid__ = binFileSid;
    }

    /**
     * <p>smlAble を取得します。
     * @return smlAble
     */
    public int getSmlAble() {
        return smlAble__;
    }

    /**
     * <p>smlAble をセットします。
     * @param smlAble smlAble
     */
    public void setSmlAble(int smlAble) {
        smlAble__ = smlAble;
    }

    /**
     * <p>schAccessFlg を取得します。
     * @return schAccessFlg
     */
    public int getSchAccessFlg() {
        return schAccessFlg__;
    }

    /**
     * <p>schAccessFlg をセットします。
     * @param schAccessFlg schAccessFlg
     */
    public void setSchAccessFlg(int schAccessFlg) {
        schAccessFlg__ = schAccessFlg;
    }

    /**
     * <p>schRegistFlg を取得します。
     * @return schRegistFlg
     */
    public boolean isSchRegistFlg() {
        return schRegistFlg__;
    }

    /**
     * <p>schRegistFlg をセットします。
     * @param schRegistFlg schRegistFlg
     */
    public void setSchRegistFlg(boolean schRegistFlg) {
        schRegistFlg__ = schRegistFlg;
    }
}
