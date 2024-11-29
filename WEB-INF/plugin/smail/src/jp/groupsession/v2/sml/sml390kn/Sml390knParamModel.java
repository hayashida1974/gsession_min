package jp.groupsession.v2.sml.sml390kn;

import jp.groupsession.v2.sml.sml390.Sml390ParamModel;

/**
 *
 * <br>[機  能] 送信先制限設定 追加編集画面　パラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml390knParamModel extends Sml390ParamModel {
    /**役職表示*/
    private String sml390knDspPosition__;
    /**備考表示*/
    private String sml390knDspBiko__;
    /**
     * <p>sml390knDspPosition を取得します。
     * @return sml390knDspPosition
     */
    public String getSml390knDspPosition() {
        return sml390knDspPosition__;
    }
    /**
     * <p>sml390knDspPosition をセットします。
     * @param sml390knDspPosition sml390knDspPosition
     */
    public void setSml390knDspPosition(String sml390knDspPosition) {
        sml390knDspPosition__ = sml390knDspPosition;
    }
    /**
     * <p>sml390knDspBiko を取得します。
     * @return sml390knDspBiko
     */
    public String getSml390knDspBiko() {
        return sml390knDspBiko__;
    }
    /**
     * <p>sml390knDspBiko をセットします。
     * @param sml390knDspBiko sml390knDspBiko
     */
    public void setSml390knDspBiko(String sml390knDspBiko) {
        sml390knDspBiko__ = sml390knDspBiko;
    }

}
