package jp.groupsession.v2.rng.rng080;

import jp.groupsession.v2.rng.rng010.Rng010Form;

/**
 * <br>[機  能] 稟議 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng080Form extends Rng010Form {

    /** ショートメール使用フラグ*/
    private int rng080SmlFlg__ = 0;
    /** 個人テンプレート使用フラグ*/
    private int rng080TemplatePersonalFlg__ = 0;
    /** 個人経路テンプレート使用フラグ*/
    private int rng080KeiroPersonalFlg__ = 0;

    /**
     * <p>rng080SmlFlg を取得します。
     * @return rng080SmlFlg
     */
    public int getRng080SmlFlg() {
        return rng080SmlFlg__;
    }

    /**
     * <p>rng080SmlFlg をセットします。
     * @param rng080SmlFlg rng080SmlFlg
     */
    public void setRng080SmlFlg(int rng080SmlFlg) {
        rng080SmlFlg__ = rng080SmlFlg;
    }

    /**
     * <p>rng080TemplatePersonalFlg を取得します。
     * @return rng080TemplatePersonalFlg
     * @see jp.groupsession.v2.rng.rng080.Rng080Form#rng080TemplatePersonalFlg__
     */
    public int getRng080TemplatePersonalFlg() {
        return rng080TemplatePersonalFlg__;
    }

    /**
     * <p>rng080TemplatePersonalFlg をセットします。
     * @param rng080TemplatePersonalFlg rng080TemplatePersonalFlg
     * @see jp.groupsession.v2.rng.rng080.Rng080Form#rng080TemplatePersonalFlg__
     */
    public void setRng080TemplatePersonalFlg(int rng080TemplatePersonalFlg) {
        rng080TemplatePersonalFlg__ = rng080TemplatePersonalFlg;
    }

    /**
     * <p>rng080KeiroPersonalFlg を取得します。
     * @return rng080KeiroPersonalFlg
     * @see jp.groupsession.v2.rng.rng080.Rng080Form#rng080KeiroPersonalFlg__
     */
    public int getRng080KeiroPersonalFlg() {
        return rng080KeiroPersonalFlg__;
    }

    /**
     * <p>rng080KeiroPersonalFlg をセットします。
     * @param rng080KeiroPersonalFlg rng080KeiroPersonalFlg
     * @see jp.groupsession.v2.rng.rng080.Rng080Form#rng080KeiroPersonalFlg__
     */
    public void setRng080KeiroPersonalFlg(int rng080KeiroPersonalFlg) {
        rng080KeiroPersonalFlg__ = rng080KeiroPersonalFlg;
    }
}
