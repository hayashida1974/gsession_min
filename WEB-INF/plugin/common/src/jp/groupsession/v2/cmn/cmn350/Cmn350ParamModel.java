package jp.groupsession.v2.cmn.cmn350;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] 表示設定画面のパラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn350ParamModel extends AbstractParamModel {

    /** 六曜表示 */
    private int cmn350RokuyoDsp__ = -1;

    /**
     * <p>Cmn350RokuyoDsp を取得します。
     * @return Cmn350RokuyoDsp
     * @see jp.groupsession.v2.cmn.Cmn350.Cmn350Form#Cmn350RokuyoDsp__
     */
    public int getCmn350RokuyoDsp() {
        return cmn350RokuyoDsp__;
    }
    /**
     * <p>Cmn350RokuyoDsp をセットします。
     * @param cmn350RokuyoDsp cmn350RokuyoDsp
     * @see jp.groupsession.v2.cmn.Cmn350.Cmn350Form#Cmn350RokuyoDsp__
     */
    public void setCmn350RokuyoDsp(int cmn350RokuyoDsp) {
        cmn350RokuyoDsp__ = cmn350RokuyoDsp;
    }
}