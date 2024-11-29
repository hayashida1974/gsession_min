package jp.groupsession.v2.sml.main;

import jp.groupsession.v2.sml.sml010.Sml010ParamModel;

/**
 * <br>[機  能]  ショートメール(メイン画面表示用)のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlMainParamModel extends Sml010ParamModel {
    /** ショートメールトップ画面URL */
    private String  smlTopUrl__;
    /**
     * @return smlTopUrl__ を戻します。
     */
    public String getSmlTopUrl() {
        return smlTopUrl__;
    }
    /**
     * @param smlTopUrl 設定する smlTopUrl__。
     */
    public void setSmlTopUrl(String smlTopUrl) {
        smlTopUrl__ = smlTopUrl;
    }
}