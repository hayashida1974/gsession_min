package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] バッチ処理に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BatchInfo {
    /** Gs2Resources.properties BatchStatus=2:制限して実行 の場合、バッチ処理の実行を行うか */
    private boolean limitation__ = false;

    /**
     * <p>limitation を取得します。
     * @return limitation
     */
    public boolean isLimitation() {
        return limitation__;
    }

    /**
     * <p>limitation をセットします。
     * @param limitation limitation
     */
    public void setLimitation(boolean limitation) {
        limitation__ = limitation;
    }
}
