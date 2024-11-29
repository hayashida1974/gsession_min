package jp.groupsession.v2.rap.mbh.push.api;

import jp.co.sjts.util.json.JSONObject;

/**
 * <br>[機  能] WEB API モバイル共通テーマ取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IPushApiRequest {
    /**
     * <br>[機  能] リクエストパラメータ用JSONの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return レスポンス
     */
    default String getParam() {

        JSONObject ret = JSONObject.fromObject(this);

        return ret.toString();



    }

}
