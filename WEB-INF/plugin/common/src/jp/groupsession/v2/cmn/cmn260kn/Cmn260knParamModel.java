package jp.groupsession.v2.cmn.cmn260kn;

import jp.groupsession.v2.cmn.cmn260.Cmn260ParamModel;

/**
 * <br>[機  能] OAuth認証情報登録確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn260knParamModel extends Cmn260ParamModel {
    /** 備考(表示用) */
    private String cmn260knBiko__ = null;

    /**
     * <p>cmn260knBiko を取得します。
     * @return cmn260knBiko
     * @see jp.groupsession.v2.cmn.cmn260kn.Cmn260knForm#cmn260knBiko__
     */
    public String getCmn260knBiko() {
        return cmn260knBiko__;
    }

    /**
     * <p>cmn260knBiko をセットします。
     * @param cmn260knBiko cmn260knBiko
     * @see jp.groupsession.v2.cmn.cmn260kn.Cmn260knForm#cmn260knBiko__
     */
    public void setCmn260knBiko(String cmn260knBiko) {
        cmn260knBiko__ = cmn260knBiko;
    }

}