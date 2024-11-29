package jp.groupsession.v2.man.man380;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man380Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @throws Exception SQL実行時例外
     */
    public void setInitData(Man380ParamModel paramMdl) throws Exception {

        //初期表示フラグON
        if (paramMdl.isMan380InitDsp()) {

            //初期表示フラグOFF
            paramMdl.setMan380InitDsp(false);
            paramMdl.setMan380Year(0);
            paramMdl.setMan380Month(6);
        }
    }

    /**
     * <br>[機  能] 常に表示する値を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception SQL実行時例外
     */
    public void setDspData(Man380ParamModel paramMdl, RequestModel reqMdl) throws Exception {

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        //年リスト
        for (int i = 0; i <= 5; i++) {
            yearLabel.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                            Integer.toString(i)));
        }
        yearLabel.add(new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {"10"}), "10"));
        paramMdl.setMan380YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                            Integer.toString(i)));
        }
        paramMdl.setMan380MonthLabelList(monthLabel);
    }

}
