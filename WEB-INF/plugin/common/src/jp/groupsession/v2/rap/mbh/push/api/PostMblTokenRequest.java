package jp.groupsession.v2.rap.mbh.push.api;

/**
 * <br>[機  能] 管理端末登録確認APIリクエスト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PostMblTokenRequest implements IPushApiRequest {
    /**
     *
     * デフォルトコンストラクタ
     */
    public PostMblTokenRequest() {
    }

    /** 端末データ 端末トークン,アプリID */
    private String deviceData__;
    /** 確認キー */
    private String exeKey__;

    /**
     * <p>deviceData を取得します。
     * @return deviceData
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#deviceData__
     */
    public String getDeviceData() {
        return deviceData__;
    }

    /**
     * <p>token をセットします。
     * @param deviceData
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#deviceData__
     */
    public void setDeviceData(String deviceData) {
        deviceData__ = deviceData;
    }

    /**
     * <p>exeKey を取得します。
     * @return exeKey
     * @see jp.groupsession.v2.rap.mbh.push.api.PostMblTokenRequest#exeKey__
     */
    public String getExeKey() {
        return exeKey__;
    }

    /**
     * <p>exeKey をセットします。
     * @param exeKey exeKey
     * @see jp.groupsession.v2.rap.mbh.push.api.PostMblTokenRequest#exeKey__
     */
    public void setExeKey(String exeKey) {
        exeKey__ = exeKey;
    }


}
