package jp.groupsession.v2.cmn.cmn160kn;

import jp.groupsession.v2.cmn.cmn160.Cmn160ParamModel;

/**
 * <br>[機  能] 企業情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160knParamModel extends Cmn160ParamModel {
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