package jp.groupsession.v2.wml.wml280kn;

import jp.groupsession.v2.wml.wml280.Wml280ParamModel;

/**
 * <br>[機  能] WEBメール 送信先リスト登録確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml280knParamModel extends Wml280ParamModel {
    /** 画面表示モード */
    private int wml280knMode__ = 0;
    /** 備考 表示用 */
    private String wml280knBiko__ = null;
    /**
     * <p>wml280knBiko を取得します。
     * @return wml280knBiko
     */
    public String getWml280knBiko() {
        return wml280knBiko__;
    }
    /**
     * <p>wml280knBiko をセットします。
     * @param wml280knBiko wml280knBiko
     */
    public void setWml280knBiko(String wml280knBiko) {
        wml280knBiko__ = wml280knBiko;
    }
    /**
     * <p>wml280knMode を取得します。
     * @return wml280knMode
     */
    public int getWml280knMode() {
        return wml280knMode__;
    }
    /**
     * <p>wml280knMode をセットします。
     * @param wml280knMode wml280knMode
     */
    public void setWml280knMode(int wml280knMode) {
        wml280knMode__ = wml280knMode;
    }
}