package jp.groupsession.v2.rng;

import jp.groupsession.v2.cmn.model.RequestModel;


/**
 * <br>[機  能] 稟議の申請、承認などが行われた場合に呼ばれるリスナー
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IRingiListener {

    /**
     * <br>[機  能] ショートメール通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param noticeKbn 通知区分
     * @param sort [0]：通知開始ソート番号 [1]：通知終了ソート番号
     * @throws Exception 実行例外
     */
    public void sendSmlMain(RingiListenerModel model,
            RequestModel reqMdl, int noticeKbn, int[] sort)
            throws Exception;

    /**
     * <br>[機  能] ショートメール通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param noticeKbn 通知区分
     * @throws Exception 実行例外
     */
    public void sendSmlMain(RingiListenerModel model,
            RequestModel reqMdl, int noticeKbn)
            throws Exception;
}