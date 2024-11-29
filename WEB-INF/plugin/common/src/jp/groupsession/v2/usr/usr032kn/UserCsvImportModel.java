package jp.groupsession.v2.usr.usr032kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.usr.usr032.Usr032Form;

/**
 * <br>[機  能] ユーザインポート インポートに必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserCsvImportModel extends Usr032Form {

    /** 所属グループ */
    private List<CmnGroupmModel> glist__ = null;
    /** デフォルトグループ */
    private CmnGroupmModel dfGroup__ = null;
    /** セッションユーザSID */
    private int userSid__;
    /** 実行モード 0=インポート 1=インポート情報を取得 */
    private int mode__;
    /** 既存のユーザ情報更新フラグ */
    private int updateFlg__;
    /** パスワード更新フラグ */
    private int updatePassFlg__;
    /** 初回ログイン時、パスワード変更区分 */
    private int pswdKbn__;
    /** グループ作成フラグ */
    private int insertFlg__;

    /**
     * <p>glist を取得します。
     * @return glist
     */
    public List<CmnGroupmModel> getGlist() {
        return glist__;
    }
    /**
     * <p>glist をセットします。
     * @param glist glist
     */
    public void setGlist(List<CmnGroupmModel> glist) {
        glist__ = glist;
    }
    /**
     * <p>dfGroup を取得します。
     * @return dfGroup
     */
    public CmnGroupmModel getDfGroup() {
        return dfGroup__;
    }
    /**
     * <p>dfGroup をセットします。
     * @param dfGroup dfGroup
     */
    public void setDfGroup(CmnGroupmModel dfGroup) {
        dfGroup__ = dfGroup;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>mode を取得します。
     * @return mode
     */
    public int getMode() {
        return mode__;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(int mode) {
        mode__ = mode;
    }
    /**
     * <p>updateFlg を取得します。
     * @return updateFlg
     */
    public int getUpdateFlg() {
        return updateFlg__;
    }
    /**
     * <p>updateFlg をセットします。
     * @param updateFlg updateFlg
     */
    public void setUpdateFlg(int updateFlg) {
        updateFlg__ = updateFlg;
    }
    /**
     * <p>pswdKbn を取得します。
     * @return pswdKbn
     */
    public int getPswdKbn() {
        return pswdKbn__;
    }
    /**
     * <p>pswdKbn をセットします。
     * @param pswdKbn pswdKbn
     */
    public void setPswdKbn(int pswdKbn) {
        pswdKbn__ = pswdKbn;
    }
    /**
     * <p>insertFlg を取得します。
     * @return insertFlg
     */
    public int getInsertFlg() {
        return insertFlg__;
    }
    /**
     * <p>insertFlg をセットします。
     * @param insertFlg insertFlg
     */
    public void setInsertFlg(int insertFlg) {
        insertFlg__ = insertFlg;
    }
    /**
     * <p>updatePassFlg を取得します。
     * @return updatePassFlg
     */
    public int getUpdatePassFlg() {
        return updatePassFlg__;
    }
    /**
     * <p>updatePassFlg をセットします。
     * @param updatePassFlg updatePassFlg
     */
    public void setUpdatePassFlg(int updatePassFlg) {
        updatePassFlg__ = updatePassFlg;
    }

}