package jp.groupsession.v2.cmn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchAppendDataModel;
import jp.groupsession.v2.cmn.model.SchAppendDataParam;

/**
 * <br>[機  能] スケジュールに他プラグインのデータを表示する為のインターフェイス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface SchAppendSchData {

    /**
     * <br>[機  能] 追加情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @return メッセージのリスト
     * @throws SQLException SQL実行例外
     */
    public List<SchAppendDataModel> getAppendSchData(SchAppendDataParam paramMdl,
                                    RequestModel reqMdl, Connection con) throws SQLException;

}
