package jp.groupsession.v2.wml.wml210;

import jp.groupsession.v2.wml.wml200.Wml200ParamModel;

/**
 * <br>[機  能] WEBメール 管理者設定 ラベル登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml210ParamModel extends Wml200ParamModel {
    /** ラベル名 */
    private String wml210LabelName__ = null;
    /** 初期表示区分 */
    private int wml210initKbn__ = 0;

    /**
     * <p>wml210LabelName を取得します。
     * @return wml210LabelName
     */
    public String getWml210LabelName() {
        return wml210LabelName__;
    }
    /**
     * <p>wml210LabelName をセットします。
     * @param wml210LabelName wml210LabelName
     */
    public void setWml210LabelName(String wml210LabelName) {
        wml210LabelName__ = wml210LabelName;
    }
    /**
     * <p>wml210initKbn を取得します。
     * @return wml210initKbn
     */
    public int getWml210initKbn() {
        return wml210initKbn__;
    }
    /**
     * <p>wml210initKbn をセットします。
     * @param wml210initKbn wml210initKbn
     */
    public void setWml210initKbn(int wml210initKbn) {
        wml210initKbn__ = wml210initKbn;
    }
}