package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] ログ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LogInfo {

    /** 出力/出力しない */
    private boolean out__ = false;

    /**
     * @return output
     */
    public boolean isOut() {
        return out__;
    }

    /**
     * @param out セットする out
     */
    public void setOut(boolean out) {
        out__ = out;
    }
    /**
     * @param string セットする string
     */
    public void setOutByString(String string) {
        setOut(new Boolean(string).booleanValue());
    }

}
