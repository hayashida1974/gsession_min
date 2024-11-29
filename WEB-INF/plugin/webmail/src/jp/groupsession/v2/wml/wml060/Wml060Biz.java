package jp.groupsession.v2.wml.wml060;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 手動データ削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml060Biz {

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     */
    public void setInitData(RequestModel reqMdl, Wml060ParamModel paramMdl) {
        //年ラベル
        paramMdl.setYearLabelList(WmlBiz.createDelYearCombo(reqMdl));
        //月ラベル
        paramMdl.setMonthLabelList(WmlBiz.createDelMonthCombo(reqMdl));
        //日ラベル
        paramMdl.setDayLabelList(WmlBiz.createDelDayCombo(reqMdl));
    }
}
