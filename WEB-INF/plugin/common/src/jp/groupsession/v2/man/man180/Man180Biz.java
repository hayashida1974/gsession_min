package jp.groupsession.v2.man.man180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.dao.base.CmnLhisBatchConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.model.base.CmnLhisBatchConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man180Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Man180ParamModel paramMdl) throws SQLException {

        //バッチ処理実行時間を取得
        CmnBatchJobDao batchDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batchMdl = batchDao.select();
        String batchTime = "";
        if (batchMdl != null) {
            batchTime = String.valueOf(batchMdl.getBatFrDate());
        }
        paramMdl.setMan180BatchTime(batchTime);

        //初期表示フラグON
        if (paramMdl.isMan180InitDsp()) {

            //初期表示フラグOFF
            paramMdl.setMan180InitDsp(false);

            CmnLhisBatchConfDao lhisbDao = new CmnLhisBatchConfDao(con);
            CmnLhisBatchConfModel lhisbMdl = lhisbDao.select();

            if (lhisbMdl != null) {
                paramMdl.setMan180Year(lhisbMdl.getCbcAdlYear());
                paramMdl.setMan180Month(lhisbMdl.getCbcAdlMonth());
                paramMdl.setMan180BatchKbn(lhisbMdl.getCbcAdlKbn());
            }
        }
    }

    /**
     * <br>[機  能] 常に表示する値を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(RequestModel reqMdl, Man180ParamModel paramMdl) throws SQLException {

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
        paramMdl.setMan180YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            String month = String.valueOf(i);
            monthLabel.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.months", new String[] {month}),
                                                        month));
        }
        paramMdl.setMan180MonthLabelList(monthLabel);
    }
}