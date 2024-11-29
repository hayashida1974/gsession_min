package jp.groupsession.v2.rap.mbh.push.api;

import java.util.List;

/**
 * <br>[機  能] 管理端末削除APIリクエスト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DelMblTokenRequest implements IPushApiRequest {
    /**
     *
     * デフォルトコンストラクタ
     */
    public DelMblTokenRequest() {
    }

    /** 端末トークン */
    private List<String> deviceDatas__;

    /**
     * <p>deviceDatas を取得します。
     * @return token
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#deviceDatas__
     */
    public List<String> getDeviceDatas() {
        return deviceDatas__;
    }

    /**
     * <p>deviceDatas をセットします。
     * @param deviceDatas
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#deviceDatas__
     */
    public void setDeviceDatas(List<String> deviceDatas) {
        deviceDatas__ = deviceDatas;
    }

}
