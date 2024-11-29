package jp.groupsession.v2.rap.mbh.push.api;

import java.util.List;
import java.util.Map;

/**
 * <br>[機  能] プッシュ通知APIリクエスト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SendMessageRequest implements IPushApiRequest {
    /**
     *
     * デフォルトコンストラクタ
     */
    public SendMessageRequest() {
    }

    /** 端末リスト 端末トークン,アプリID */
    private List<String> deviceDatas__;
    /** 通知タイトル */
    private String title__;
    /** 通知メッセージ */
    private String message__;
    /** ペイロードデータ */
    private Map<String, String> data__;

    /**
     * <p>deviceDatas を取得します。
     * @return deviceDatas
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#deviceDatas__
     */
    public List<String> getDeviceDatas() {
        return deviceDatas__;
    }



    /**
     * <p>deviceDatas をセットします。
     * @param deviceDatas
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#deviceDatas__
     */
    public void setDeviceDatas(List<String> deviceDatas) {
        deviceDatas__ = deviceDatas;
    }

    /**
     * <p>title を取得します。
     * @return title
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#title__
     */
    public String getTitle() {
        return title__;
    }



    /**
     * <p>title をセットします。
     * @param title title
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#title__
     */
    public void setTitle(String title) {
        title__ = title;
    }



    /**
     * <p>message を取得します。
     * @return message
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#message__
     */
    public String getMessage() {
        return message__;
    }



    /**
     * <p>message をセットします。
     * @param message message
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#message__
     */
    public void setMessage(String message) {
        message__ = message;
    }



    /**
     * <p>data を取得します。
     * @return data
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#data__
     */
    public Map<String, String> getData() {
        return data__;
    }



    /**
     * <p>data をセットします。
     * @param data data
     * @see jp.groupsession.v2.rap.mbh.push.api.SendMessageRequest#data__
     */
    public void setData(Map<String, String> data) {
        data__ = data;
    }



}
