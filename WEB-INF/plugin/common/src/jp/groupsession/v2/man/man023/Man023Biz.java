package jp.groupsession.v2.man.man023;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 休日テンプレート一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man023Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man023Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man023Biz() { }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(Man023ParamModel paramMdl,
                                RequestModel reqMdl, Connection con)
    throws SQLException {
        log__.debug("START");

        CmnHolidayTemplateDao htDao = new CmnHolidayTemplateDao(con);
        List<Man023HolidayTemplateModel>viewList
            = new ArrayList<Man023HolidayTemplateModel>();

        List<CmnHolidayTemplateModel> holidayList = htDao.selectSortedHoliday();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (Object obj : holidayList) {
            CmnHolidayTemplateModel model = (CmnHolidayTemplateModel) obj;
            Man023HolidayTemplateModel viewModel = new Man023HolidayTemplateModel();

            //「テンプレートSID」作成
            int tmpSid = model.getHltSid();
            viewModel.setHltSid(tmpSid);

            //「休日文字列」作成
            int hltExflg = model.getHltExflg();
            String dspString = "";
            if (hltExflg == CmnHolidayTemplateModel.HLT_EXFLG_NORMAL) {
                dspString = model.getHltDateMonth()
                + gsMsg.getMessage("cmn.month")
                + model.getHltDateDay()
                + gsMsg.getMessage("cmn.day");
            } else {
                dspString = model.getHltExMonth()
                            + gsMsg.getMessage("cmn.month")
                            + MaintenanceUtil.getWeek(model.getHltExWeekMonth(), reqMdl)
                            + MaintenanceUtil.getYoubi(model.getHltExDayWeek(), reqMdl);
            }
            viewModel.setViewDate(dspString);

            //「休日名文字列」作成
            String holiday = "";
            holiday = model.getHltName();
            viewModel.setHltName(holiday);

            viewModel.setHltExflg(model.getHltExflg());
            //リストへセット
            UDate date = new UDate();
            date.setTime(0);
            if (model.getHltExflg() == 0) {
                date.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));
                date.setMonth(model.getHltDateMonth());
                date.setDay(model.getHltDateDay());
                viewModel.setHltDateMonth(model.getHltDateMonth());

            } else {
                date.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));

                boolean result = MaintenanceUtil.isAccurateWeekOfMonth(date,
                        MaintenanceUtil.getIntYoubiForCalendar(model.getHltExDayWeek()));
                if (result) {
                    date.setWeek(model.getHltExWeekMonth());
                } else {
                    date.setWeek(model.getHltExWeekMonth() + 1);
                }

                date.setDayOfWeek(MaintenanceUtil.getIntYoubiForCalendar(model.getHltExDayWeek()));
                date.setMonth(model.getHltExMonth());
                viewModel.setHltExMonth(model.getHltExMonth());
            }

            log__.debug("==>生成UDate" + date.getDateString());
            viewModel.setDate(Integer.parseInt(date.getDateString()));
            viewList.add(viewModel);
        }
        Man023HolidayTemplateModel[] viewArray = (Man023HolidayTemplateModel[])
        viewList.toArray(new Man023HolidayTemplateModel[viewList.size()]);

        Arrays.sort(viewArray);
        paramMdl.setMan023TemplateList(viewArray);

        log__.debug("END");
    }

}
