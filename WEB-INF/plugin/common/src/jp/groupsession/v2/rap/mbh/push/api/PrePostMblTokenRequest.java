package jp.groupsession.v2.rap.mbh.push.api;

/**
 * <br>[機  能] 管理端末追加申請APIリクエスト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrePostMblTokenRequest implements IPushApiRequest {
    /**
     *
     * デフォルトコンストラクタ
     */
    public PrePostMblTokenRequest() {
    }

    /** 端末データ 端末トークン,アプリID */
    private String deviceData__;

    /**
     * <p>deviceData を取得します。
     * @return token
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#deviceData__
     */
    public String getDeviceData() {
        return deviceData__;
    }

    /**
     * <p>deviceData をセットします。
     * @param deviceData
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#deviceData__
     */
    public void setDeviceData(String deviceData) {
        deviceData__ = deviceData;
    }



}
