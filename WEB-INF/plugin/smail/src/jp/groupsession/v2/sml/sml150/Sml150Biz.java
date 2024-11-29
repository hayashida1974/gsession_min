package jp.groupsession.v2.sml.sml150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 管理者設定 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml150Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml150ParamModel paramMdl, Connection con)
        throws SQLException {

       /*********************************************************
        *
        * ショートメール自動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年リスト
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            yearLabel.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        paramMdl.setSml150YearLabelList(yearLabel);

        //月リスト
        for (int month : GSConst.DEL_MONTH_DATE) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                    Integer.toString(month)));
        }
        paramMdl.setSml150MonthLabelList(monthLabel);

        SmlAdelDao delDao = new SmlAdelDao(con);
        SmlAdelModel delMdl = delDao.select(0);
        if (delMdl == null) {
            delMdl = new SmlAdelModel();
            delMdl.setSadDelKbn(GSConstSmail.SML_DEL_KBN_ADM_SETTING);
            delMdl.setSadJdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadJdelYear(0);
            delMdl.setSadJdelMonth(0);
            delMdl.setSadSdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadSdelYear(0);
            delMdl.setSadSdelMonth(0);
            delMdl.setSadWdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadWdelYear(0);
            delMdl.setSadWdelMonth(0);
            delMdl.setSadDdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadDdelYear(0);
            delMdl.setSadDdelMonth(0);
        }

        //削除区分
        paramMdl.setSml150DelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml150DelKbn(),
                        String.valueOf(delMdl.getSadDelKbn())));

        //受信メール処理 選択値
        paramMdl.setSml150JdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml150JdelKbn(),
                        String.valueOf(delMdl.getSadJdelKbn())));

        //受信メール 年
        paramMdl.setSml150JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150JYear()),
                        String.valueOf(delMdl.getSadJdelYear())));

        //受信メール 月
        paramMdl.setSml150JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150JMonth()),
                        String.valueOf(delMdl.getSadJdelMonth())));

        //送信メール処理 選択値
        paramMdl.setSml150SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml150SdelKbn(),
                        String.valueOf(delMdl.getSadSdelKbn())));

        //送信メール 年
        paramMdl.setSml150SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150SYear()),
                        String.valueOf(delMdl.getSadSdelYear())));

        //送信メール 月
        paramMdl.setSml150SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150SMonth()),
                        String.valueOf(delMdl.getSadSdelMonth())));

        //草稿メール処理 選択値
        paramMdl.setSml150WdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml150WdelKbn(),
                        String.valueOf(delMdl.getSadWdelKbn())));

        //草稿メール 年
        paramMdl.setSml150WYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150WYear()),
                        String.valueOf(delMdl.getSadWdelYear())));

        //草稿メール 月
        paramMdl.setSml150WMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150WMonth()),
                        String.valueOf(delMdl.getSadWdelMonth())));

        //ゴミ箱メール処理 選択値
        paramMdl.setSml150DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml150DdelKbn(),
                        String.valueOf(delMdl.getSadDdelKbn())));

        //ゴミ箱メール 年
        paramMdl.setSml150DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150DYear()),
                        String.valueOf(delMdl.getSadDdelYear())));

        //ゴミ箱メール 月
        paramMdl.setSml150DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml150DMonth()),
                        String.valueOf(delMdl.getSadDdelMonth())));

        //バッチ処理実行時間を取得
        CmnBatchJobDao batchDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batchMdl = batchDao.select();
        String batchTime = "";
        if (batchMdl != null) {
            batchTime = String.valueOf(batchMdl.getBatFrDate());
        }
        paramMdl.setSml150BatchTime(batchTime);
    }
}