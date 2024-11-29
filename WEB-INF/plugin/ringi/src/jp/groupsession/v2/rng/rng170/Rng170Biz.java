package jp.groupsession.v2.rng.rng170;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.biz.RngBiz;

/**
 * <br>[機  能] 稟議 手動データ削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng170Biz {

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     */
    public void setInitData(RequestModel reqMdl, Rng170ParamModel paramMdl) {
        //年ラベル
        paramMdl.setYearLabelList(RngBiz.createDelYearCombo(reqMdl));
        //月ラベル
        paramMdl.setMonthLabelList(RngBiz.createDelMonthCombo(reqMdl));
        //日ラベル
        paramMdl.setDayLabelList(RngBiz.createDelDayCombo(reqMdl));
    }
}
