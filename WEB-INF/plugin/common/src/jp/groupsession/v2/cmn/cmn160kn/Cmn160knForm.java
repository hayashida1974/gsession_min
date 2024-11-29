package jp.groupsession.v2.cmn.cmn160kn;

import jp.groupsession.v2.cmn.cmn160.Cmn160Form;

/**
 * <br>[機  能] メイン 管理者設定 企業情報確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160knForm extends Cmn160Form {
    /** 備考 */
    private String cmn160knBiko__ = null;

    /**
     * @return cmn160knBiko
     */
    public String getCmn160knBiko() {
        return cmn160knBiko__;
    }

    /**
     * @param cmn160knBiko 設定する cmn160knBiko
     */
    public void setCmn160knBiko(String cmn160knBiko) {
        cmn160knBiko__ = cmn160knBiko;
    }
}
