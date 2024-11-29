package jp.groupsession.v2.man.man190;

import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man190Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man190ParamModel paramMdl) throws SQLException {

        //初期表示フラグON
        if (paramMdl.isMan190InitDsp()) {

            //初期表示フラグOFF
            paramMdl.setMan190InitDsp(false);
            paramMdl.setMan190Year(3);
            paramMdl.setMan190Month(0);
        }
    }

    /**
     * <br>[機  能] 常に表示する値を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(Man190ParamModel paramMdl, RequestModel reqMdl) throws SQLException {

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
        paramMdl.setMan190YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                           Integer.toString(i)));
        }
        paramMdl.setMan190MonthLabelList(monthLabel);
    }
}