package jp.groupsession.v2.cmn.cmn132;

import jp.groupsession.v2.cmn.cmn131kn.Cmn131knParamModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] メイン 個人設定 共有マイグループ確認画面のパラメータ、出力値を格納Modelするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn132ParamModel extends Cmn131knParamModel {
    /**マイグループ所有者*/
    private CmnUsrmInfModel cmn132owner__;
    /**マイグループ区分*/
    private String cmn132MyGroupKbn__;

    /**
     * <p>cmn132owner を取得します。
     * @return cmn132owner
     */
    public CmnUsrmInfModel getCmn132owner() {
        return cmn132owner__;
    }

    /**
     * <p>cmn132owner をセットします。
     * @param cmn132owner cmn132owner
     */
    public void setCmn132owner(CmnUsrmInfModel cmn132owner) {
        cmn132owner__ = cmn132owner;
    }

    /**
     * <p>cmn132MyGroupKbn を取得します。
     * @return cmn132MyGroupKbn
     */
    public String getCmn132MyGroupKbn() {
        return cmn132MyGroupKbn__;
    }

    /**
     * <p>cmn132MyGroupKbn をセットします。
     * @param cmn132MyGroupKbn cmn132MyGroupKbn
     */
    public void setCmn132MyGroupKbn(String cmn132MyGroupKbn) {
        cmn132MyGroupKbn__ = cmn132MyGroupKbn;
    }


}
