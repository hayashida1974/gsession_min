package jp.groupsession.v2.cmn.cmn100;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ユーザ情報ポップアップで表示する情報を取得する処理に関するインターフェイス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface Cmn100AppendInfo {

    /**
     * <br>[機  能] 追加情報(明細形式)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param sessionUid セッションユーザSID
     * @param con DBコネクション
     * @param gsMsg GSメッセージ
     * @return メッセージのリスト
     */
    public List<Cmn100AppendInfoModel> getAppendInfo(Map<String, Object> paramMap,
                                                int usid, int sessionUid, Connection con,
                                                GsMessage gsMsg);

}
