package jp.groupsession.v2.man.man170;

import jp.groupsession.v2.cmn.model.base.CmnLoginHistoryModel;

/**
 * <br>[機  能] ログイン履歴情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man170Model extends CmnLoginHistoryModel {

    /** ログイン時間 */
    private String loginTime__;
    /** 端末名 */
    private String terminalName__;
    /** キャリア名 */
    private String carName__;

    /**
     * <p>loginTime を取得します。
     * @return loginTime
     */
    public String getLoginTime() {
        return loginTime__;
    }
    /**
     * <p>loginTime をセットします。
     * @param loginTime loginTime
     */
    public void setLoginTime(String loginTime) {
        loginTime__ = loginTime;
    }
    /**
     * <p>terminalName を取得します。
     * @return terminalName
     */
    public String getTerminalName() {
        return terminalName__;
    }
    /**
     * <p>terminalName をセットします。
     * @param terminalName terminalName
     */
    public void setTerminalName(String terminalName) {
        terminalName__ = terminalName;
    }
    /**
     * <p>carName を取得します。
     * @return carName
     */
    public String getCarName() {
        return carName__;
    }
    /**
     * <p>carName をセットします。
     * @param carName carName
     */
    public void setCarName(String carName) {
        carName__ = carName;
    }
}