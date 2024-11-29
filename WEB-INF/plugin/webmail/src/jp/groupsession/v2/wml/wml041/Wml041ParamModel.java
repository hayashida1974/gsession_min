package jp.groupsession.v2.wml.wml041;

import jp.groupsession.v2.cmn.model.AbstractParamModel;


/**
 * <br>[機  能] WEBメール アカウント 署名登録(ポップアップ)画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml041ParamModel extends AbstractParamModel {
    /** 編集モード */
    private int wml041mode__ = 0;
    /** No. */
    private int wml041No__ = 0;
    /** タイトル */
    private String wml041title__ = null;
    /** 署名 */
    private String wml041sign__ = null;
    /** 初期表示フラグ */
    private int wml041initFlg__ = 0;
    /** 呼出元画面 */
    private int wml041parentId__ = 0;
    /**
     * <p>wml041mode を取得します。
     * @return wml041mode
     */
    public int getWml041mode() {
        return wml041mode__;
    }
    /**
     * <p>wml041mode をセットします。
     * @param wml041mode wml041mode
     */
    public void setWml041mode(int wml041mode) {
        wml041mode__ = wml041mode;
    }
    /**
     * <p>wml041No を取得します。
     * @return wml041No
     */
    public int getWml041No() {
        return wml041No__;
    }
    /**
     * <p>wml041No をセットします。
     * @param wml041No wml041No
     */
    public void setWml041No(int wml041No) {
        wml041No__ = wml041No;
    }
    /**
     * <p>wml041title を取得します。
     * @return wml041title
     */
    public String getWml041title() {
        return wml041title__;
    }
    /**
     * <p>wml041title をセットします。
     * @param wml041title wml041title
     */
    public void setWml041title(String wml041title) {
        wml041title__ = wml041title;
    }
    /**
     * <p>wml041sign を取得します。
     * @return wml041sign
     */
    public String getWml041sign() {
        return wml041sign__;
    }
    /**
     * <p>wml041sign をセットします。
     * @param wml041sign wml041sign
     */
    public void setWml041sign(String wml041sign) {
        wml041sign__ = wml041sign;
    }
    /**
     * <p>wml041initFlg を取得します。
     * @return wml041initFlg
     */
    public int getWml041initFlg() {
        return wml041initFlg__;
    }
    /**
     * <p>wml041initFlg をセットします。
     * @param wml041initFlg wml041initFlg
     */
    public void setWml041initFlg(int wml041initFlg) {
        wml041initFlg__ = wml041initFlg;
    }
    /**
     * <p>wml041parentId を取得します。
     * @return wml041parentId
     */
    public int getWml041parentId() {
        return wml041parentId__;
    }
    /**
     * <p>wml041parentId をセットします。
     * @param wml041parentId wml041parentId
     */
    public void setWml041parentId(int wml041parentId) {
        wml041parentId__ = wml041parentId;
    }
}