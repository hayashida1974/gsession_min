package jp.groupsession.v2.man.man050;

import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;

/**
 * <br>[機  能] 最終ログイン時間情報(画面表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考] SltUserPerGroupModelを継承します
 *
 * @author JTS
 */
public class Man050ViewModel extends SltUserPerGroupModel {

    /** 最終ログイン時間 */
    private String strLgintime__ = null;

    /** 最終ログイン時間背景色フラグ */
    private int lgintimeFg__ = 0;

    /** 端末名 */
    private String terminalName__;

    /** キャリア名 */
    private String carName__;

    /** 固体識別番号 */
    private String clhUid__;

    /**
     * @return lgintimeFg を戻します。
     */
    public int getLgintimeFg() {
        return lgintimeFg__;
    }

    /**
     * @param lgintimeFg 設定する lgintimeFg。
     */
    public void setLgintimeFg(int lgintimeFg) {
        lgintimeFg__ = lgintimeFg;
    }

    /**
     * @return strLgintime を戻します。
     */
    public String getStrLgintime() {
        return strLgintime__;
    }

    /**
     * @param strLgintime 設定する strLgintime。
     */
    public void setStrLgintime(String strLgintime) {
        strLgintime__ = strLgintime;
    }

    /**
     * <p>carName を取得します。
     * @return carName
     */
    public String getCarName() {
        return carName__;
    }

    /**
     * <p>carName をセットします。
     * @param carName carName
     */
    public void setCarName(String carName) {
        carName__ = carName;
    }

    /**
     * <p>terminalName を取得します。
     * @return terminalName
     */
    public String getTerminalName() {
        return terminalName__;
    }

    /**
     * <p>terminalName をセットします。
     * @param terminalName terminalName
     */
    public void setTerminalName(String terminalName) {
        terminalName__ = terminalName;
    }

    /**
     * <p>clhUid を取得します。
     * @return clhUid
     */
    public String getClhUid() {
        return clhUid__;
    }

    /**
     * <p>clhUid をセットします。
     * @param clhUid clhUid
     */
    public void setClhUid(String clhUid) {
        clhUid__ = clhUid;
    }

}
