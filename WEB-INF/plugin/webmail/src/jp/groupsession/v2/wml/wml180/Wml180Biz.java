package jp.groupsession.v2.wml.wml180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlMailLogDao;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール 送受信ログ手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml180Biz {

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, RequestModel reqMdl, Wml180ParamModel paramMdl)
    throws SQLException {
        //年ラベル
        paramMdl.setYearLabelList(WmlBiz.createDelYearCombo(reqMdl));
        //月ラベル
        paramMdl.setMonthLabelList(WmlBiz.createDelMonthCombo(reqMdl));
        //日ラベル
        paramMdl.setDayLabelList(WmlBiz.createDelDayCombo(reqMdl));

        //範囲指定 年、月、日コンボ
        WmlMailLogDao mailLogDao = new WmlMailLogDao(con);
        UDate[] limitDate = mailLogDao.getLimitDate();
        if (limitDate == null || limitDate[0] == null || limitDate[1] == null) {
            UDate now = new UDate();
            paramMdl.setDateAreaYearLabelList(
                    __createDateCombo(now.getYear() - 1, now.getYear()));
        } else {
            paramMdl.setDateAreaYearLabelList(
                    __createDateCombo(limitDate[0].getYear(), limitDate[1].getYear()));
        }
        paramMdl.setDateAreaMonthLabelList(__createDateCombo(1, 12));
        paramMdl.setDateAreaDayLabelList(__createDateCombo(1, 31));

        //送受信ログの登録 を設定
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setWmlEntryMailLogFlg(wmlBiz.isEntryMailLog(con));
    }

    /**
     * <br>[機  能] 日付(年 or 月 or 日)コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param start 開始
     * @param end 終了
     * @return 日付コンボ
     */
    private List<LabelValueBean> __createDateCombo(int start, int end) {
        List<LabelValueBean> combo = new ArrayList<LabelValueBean>();
        for (; start <= end; start++) {
            combo.add(new LabelValueBean(String.valueOf(start), String.valueOf(start)));
        }

        return combo;
    }
}
