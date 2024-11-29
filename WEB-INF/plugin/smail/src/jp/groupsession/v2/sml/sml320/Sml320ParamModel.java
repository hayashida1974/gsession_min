package jp.groupsession.v2.sml.sml320;

import jp.groupsession.v2.sml.sml310.Sml310ParamModel;

/**
 * <br>[機  能] ショートメール ラベル登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml320ParamModel extends Sml310ParamModel {
    /** ラベル名 */
    private String sml320LabelName__ = null;
    /** 初期表示区分 */
    private int sml320initKbn__ = 0;
    /**
     * <p>sml320LabelName を取得します。
     * @return sml320LabelName
     */
    public String getSml320LabelName() {
        return sml320LabelName__;
    }
    /**
     * <p>sml320LabelName をセットします。
     * @param sml320LabelName sml320LabelName
     */
    public void setSml320LabelName(String sml320LabelName) {
        sml320LabelName__ = sml320LabelName;
    }
    /**
     * <p>sml320initKbn を取得します。
     * @return sml320initKbn
     */
    public int getSml320initKbn() {
        return sml320initKbn__;
    }
    /**
     * <p>sml320initKbn をセットします。
     * @param sml320initKbn sml320initKbn
     */
    public void setSml320initKbn(int sml320initKbn) {
        sml320initKbn__ = sml320initKbn;
    }

}