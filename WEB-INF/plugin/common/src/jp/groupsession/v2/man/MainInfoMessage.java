package jp.groupsession.v2.man;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メインのインフォメーションにメッセージを表示するためのインターフェイス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface MainInfoMessage {

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param gsMsg Gsメッセージ
     * @param reqMdl リクエストモデル
     * @return メッセージのリスト
     */
    public List<MainInfoMessageModel> getMessage(Map<String, Object> paramMap,
                     int usid, Connection con, GsMessage gsMsg, RequestModel reqMdl);
}
