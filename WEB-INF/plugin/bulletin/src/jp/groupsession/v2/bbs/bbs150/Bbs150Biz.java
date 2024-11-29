package jp.groupsession.v2.bbs.bbs150;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 手動データ削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs150Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public Bbs150Biz() {
//    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Bbs150Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @throws Exception SQL実行時例外
     */
    public void setInitData(Bbs150Form form) throws SQLException {

        //初期表示フラグON
        if (form.isBBs150InitDsp()) {

            //初期表示フラグOFF
            form.setBbs150InitDsp(false);
            form.setBbs150Year(3);
            form.setBbs150Month(0);
        }
    }

    /**
     * <br>[機  能] 常に表示する値を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @throws Exception SQL実行時例外
     */
    public void setDspData(Bbs150Form form) {

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //年リスト
        for (int i = 0; i <= 10; i++) {
            yearLabel.add(new LabelValueBean(
                                gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                               Integer.toString(i)));
        }
        form.setBbs150YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                               gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                               Integer.toString(i)));
        }
        form.setBbs150MonthLabelList(monthLabel);
    }

}
