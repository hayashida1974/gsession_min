package jp.groupsession.v2.cmn.cmn320;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] 表示設定画面のパラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn320ParamModel extends AbstractParamModel {

    /** 六曜表示区分 */
    private int cmn320RokuyoDspKbn__ = -1;
    /** 六曜表示 */
    private int cmn320RokuyoDsp__ = -1;

    /**
     * <p>cmn320RokuyoDspKbn を取得します。
     * @return cmn320RokuyoDspKbn
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320ParamModel#cmn320RokuyoDspKbn__
     */
    public int getCmn320RokuyoDspKbn() {
        return cmn320RokuyoDspKbn__;
    }
    /**
     * <p>cmn320RokuyoDspKbn をセットします。
     * @param cmn320RokuyoDspKbn cmn320RokuyoDspKbn
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320ParamModel#cmn320RokuyoDspKbn__
     */
    public void setCmn320RokuyoDspKbn(int cmn320RokuyoDspKbn) {
        cmn320RokuyoDspKbn__ = cmn320RokuyoDspKbn;
    }
    /**
     * <p>cmn320RokuyoDsp を取得します。
     * @return cmn320RokuyoDsp
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320Form#cmn320RokuyoDsp__
     */
    public int getCmn320RokuyoDsp() {
        return cmn320RokuyoDsp__;
    }
    /**
     * <p>cmn320RokuyoDsp をセットします。
     * @param cmn320RokuyoDsp cmn320RokuyoDsp
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320Form#cmn320RokuyoDsp__
     */
    public void setCmn320RokuyoDsp(int cmn320RokuyoDsp) {
        cmn320RokuyoDsp__ = cmn320RokuyoDsp;
    }
}