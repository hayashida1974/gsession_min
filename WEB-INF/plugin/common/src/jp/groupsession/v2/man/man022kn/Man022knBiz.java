package jp.groupsession.v2.man.man022kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.man.man020.Man020HolidayModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 休日削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man022knBiz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man022knParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {

        List<UDate> dateList = getDateList(paramMdl);

        CmnHolidayDao holDao = new CmnHolidayDao(con);
        List<CmnHolidayModel> holList = holDao.getHoliDayList(dateList);
        List<Man020HolidayModel> man020List = new ArrayList<Man020HolidayModel>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (CmnHolidayModel holMdl : holList) {
            Man020HolidayModel man020Hol = new Man020HolidayModel();
            UDate holDate = holMdl.getHolDate();
            man020Hol.setHolName(holMdl.getHolName());
            man020Hol.setStrDate(holDate.getDateString());

            StringBuilder viewDate = new StringBuilder("");
            viewDate.append(String.valueOf(holDate.getMonth()));
            viewDate.append(gsMsg.getMessage("cmn.month"));
            viewDate.append(" ");
            viewDate.append(String.valueOf(holDate.getIntDay()));
            viewDate.append(gsMsg.getMessage("cmn.day"));
            man020Hol.setViewDate(viewDate.toString());
            man020List.add(man020Hol);
        }
        paramMdl.setMan020HolList(man020List);
    }

    /**
     * <br>[機  能] 休日情報の削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void delHoliday(Man022knParamModel paramMdl, Connection con)
    throws SQLException {

        List < UDate > dateList = getDateList(paramMdl);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        holDao.deleteHoliday(dateList);

    }

    /**
     * <br>[機  能] 削除対象日付を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return List&lt;UDate&gt; 削除対象日付
     */
    public List<UDate> getDateList(Man022knParamModel paramMdl) {

        List<UDate> dateList = new ArrayList<UDate>();

        String[] delDate = paramMdl.getHolDate();
        if (delDate == null || delDate.length == 0) {
            return dateList;
        }

        for (String date : delDate) {
            UDate holDate = new UDate();
            holDate.setTime(0);
            holDate.setDate(date);
            dateList.add(holDate);
        }

        return dateList;
    }
}
