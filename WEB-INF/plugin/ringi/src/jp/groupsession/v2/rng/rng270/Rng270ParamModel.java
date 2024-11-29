package jp.groupsession.v2.rng.rng270;

import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng080.Rng080ParamModel;

/**
 * <br>[機  能] 稟議個人設定画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng270ParamModel extends Rng080ParamModel {

    /** 代理人終了期間設定*/
    private int rng270DairiLast__ = RngConst.RNG_DAIRI_NOT_KIKAN;

    /** 代理人開始期間*/
    private String rng270DairiStart__ = null;
    /** 代理人終了期間*/
    private String rng270DairiFinish__ = null;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String rng270ScrollFlg__ = "0";

    /** 対象ユーザ選択*/
    private UserGroupSelectModel usrgroupselect__ = new UserGroupSelectModel();

    /**
     * <p>rng270DairiLast を取得します。
     * @return rng270DairiLast
     */
    public int getRng270DairiLast() {
        return rng270DairiLast__;
    }

    /**
     * <p>rng270DairiLast をセットします。
     * @param rng270DairiLast rng270DairiLast
     */
    public void setRng270DairiLast(int rng270DairiLast) {
        rng270DairiLast__ = rng270DairiLast;
    }

    /**
     * <p>rng270DairiStart を取得します。
     * @return rng270DairiStart
     */
    public String getRng270DairiStart() {
        return rng270DairiStart__;
    }

    /**
     * <p>rng270DairiStart をセットします。
     * @param rng270DairiStart rng270DairiStart
     */
    public void setRng270DairiStart(String rng270DairiStart) {
        rng270DairiStart__ = rng270DairiStart;
    }

    /**
     * <p>rng270DairiFinish を取得します。
     * @return rng270DairiFinish
     */
    public String getRng270DairiFinish() {
        return rng270DairiFinish__;
    }

    /**
     * <p>rng270DairiFinish をセットします。
     * @param rng270DairiFinish rng270DairiFinish
     */
    public void setRng270DairiFinish(String rng270DairiFinish) {
        rng270DairiFinish__ = rng270DairiFinish;
    }

    /**
     * <p>rng270ScrollFlg を取得します。
     * @return rng270ScrollFlg
     */
    public String getRng270ScrollFlg() {
        return rng270ScrollFlg__;
    }

    /**
     * <p>rng270ScrollFlg をセットします。
     * @param rng270ScrollFlg rng270ScrollFlg
     */
    public void setRng270ScrollFlg(String rng270ScrollFlg) {
        rng270ScrollFlg__ = rng270ScrollFlg;
    }

    /**
     * <p>usrgroupselect を取得します。
     * @return usrgroupselect
     */
    public UserGroupSelectModel getUsrgroupselect() {
        return usrgroupselect__;
    }

    /**
     * <p>usrgroupselect をセットします。
     * @param usrgroupselect usrgroupselect
     */
    public void setUsrgroupselect(UserGroupSelectModel usrgroupselect) {
        usrgroupselect__ = usrgroupselect;
    }

}