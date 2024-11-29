package jp.groupsession.v2.man.man024kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 テンプレートから追加確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man024knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man024knBiz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man024knBiz() { }


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man024knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(Man024knParamModel paramMdl, Connection con)
            throws SQLException {
        log__.debug("START");

        CmnHolidayTemplateDao htDao = new CmnHolidayTemplateDao(con);

        List<CmnHolidayTemplateModel> holidayList
            = htDao.selectSortedHoliday(paramMdl.getMan023hltSid());

        //拡張を日付に直す処理
        List <Man024knHolidayModel> viewList = __changeKakutyo(paramMdl, holidayList);

        //通常と拡張で重複日付のフラグを立てる
        __flgDuplicateDate(viewList);

        //振替休日追加処理
        viewList = __addHurikaeDate(viewList);

        //休日リストをソート
        __sortHoliday(viewList);

        //既存データチェック「※」表示制御
        __setAsterisk(viewList, con);

        paramMdl.setMan024knTemplateList(viewList);
    }

    /**
     * <br>[機  能] 休日リストのソート
     * <br>[解  説] 日付・昇順ソート
     * <br>[備  考]
     * @param viewList 休日リスト
     */
    private void __sortHoliday(List<Man024knHolidayModel> viewList) {

        Man024knHolidayModel sortMdl = null;

        for (int i = 0; i < viewList.size(); i++) {
            for (int j = i + 1; j < viewList.size(); j++) {
                if (viewList.get(i).getDate() > viewList.get(j).getDate()) {
                    sortMdl = viewList.get(i);
                    viewList.set(i, viewList.get(j));
                    viewList.set(j, sortMdl);
                }
            }
        }
    }

    /**
     * <br>[機  能] 振替休日の追加
     * <br>[解  説]
     * <br>[備  考]
     * @param viewList 休日リスト
     * @return 休日・振替リスト
     */
    private List <Man024knHolidayModel> __addHurikaeDate(List<Man024knHolidayModel> viewList) {

        //休日・振替用リスト
        List<Man024knHolidayModel> hurikaeList = new ArrayList<Man024knHolidayModel>();

        Man024knHolidayModel hurikaeViewModel = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        for (Man024knHolidayModel model : viewList) {
            //打消し線の表示用
            boolean borderDspFlg = false;
            boolean huriExistFlg = true;

            String dspFurikaeString = "";

            UDate huriDate = new UDate();

            //「休日文字列」作成
            int hltExflg = model.getHltExflg();

            //振替休日の日付用
            String hurikaeDate = String.valueOf(model.getDate());

            //振替休日になる日付
            huriDate.setDate(hurikaeDate);

            //振替休日設定がされている日付が日曜日の時、振替休日処理を行う
            if ((huriDate.getWeek() == Calendar.SUNDAY) && model.getHltExFurikae() == 0
                    && model.isDspBorderFlg() == false) {

                huriDate.addDay(1);

                //振替休日の日付を判定する
                for (int i = 0; i < viewList.size(); i++) {
                    if (Integer.valueOf(huriDate.getDateString()) == viewList.get(i).getDate()) {
                        i = 0;
                        huriDate.addDay(1);
                    }
                }

                CmnHolidayTemplateModel holiHuriModel = new CmnHolidayTemplateModel();

                holiHuriModel.setHltDateMonth(huriDate.getMonth());
                holiHuriModel.setHltExMonth(huriDate.getMonth());
                holiHuriModel.setHltDateDay(huriDate.getIntDay());
                holiHuriModel.setHltExDayWeek(huriDate.getWeek());
                holiHuriModel.setHltExWeekMonth(huriDate.getWeekOfMonth());

                //通常登録時の振替休日の表示処理
                if (hltExflg == CmnHolidayTemplateModel.HLT_EXFLG_NORMAL) {
                    dspFurikaeString = holiHuriModel.getHltDateMonth()
                            + gsMsg.getMessage("cmn.month")
                            + "　"
                            + holiHuriModel.getHltDateDay()
                            + gsMsg.getMessage("cmn.day");
                    //振替時の画面セットモデル設定
                    hurikaeViewModel = __addHurikaeViewSet(hurikaeViewModel, dspFurikaeString,
                            huriDate, borderDspFlg, holiHuriModel);

                    hurikaeViewModel.setHltDateMonth(huriDate.getMonth());

                //拡張登録時の振替休日処理
                } else {

                    String strMon = Integer.toString(holiHuriModel.getHltDateMonth());
                    String strYoubi = MaintenanceUtil.getStrYoubiFromCalendar(
                            holiHuriModel.getHltExDayWeek(), reqMdl__);

                    dspFurikaeString = "（" + strMon
                            + gsMsg.getMessage("cmn.month")
                            + MaintenanceUtil.getWeek(
                                    holiHuriModel.getHltExWeekMonth(), reqMdl__)
                            + strYoubi + "）";

                    dspFurikaeString = huriDate.getMonth()
                            + gsMsg.getMessage("cmn.month")
                            + "　"
                            + huriDate.getIntDay()
                            + gsMsg.getMessage("cmn.day") + dspFurikaeString;

                    //振替時の画面セットモデル設定
                    hurikaeViewModel = __addHurikaeViewSet(hurikaeViewModel, dspFurikaeString,
                            huriDate, borderDspFlg, holiHuriModel);
                }

                //振替休日が重複したときの判定
                for (Man024knHolidayModel date: hurikaeList) {
                    if (date.isHurikaeFlg() == true
                            && date.getDate() == hurikaeViewModel.getDate()) {
                        huriExistFlg = false;
                    }
                }
                //振替フラグが立っていなければ振替休日を追加
                if (huriExistFlg) {
                    hurikaeList.add(hurikaeViewModel);
                }
            }
            hurikaeList.add(model);
        }
        return hurikaeList;
    }

    /**
     * <br>[機  能] 通常・拡張で日付の重複がないかを調べる
     * <br>[解  説] 重複があった場合打消し線のフラグを立てる
     * <br>[備  考]
     * @param viewList 休日リスト
     */
    private void __flgDuplicateDate(
            List<Man024knHolidayModel> viewList) {

        for (Man024knHolidayModel model : viewList) {

            //通常と拡張で重複日付の存在チェック
            if (model.getHltExflg() == 1) {
                for (int i = 0; i < viewList.size(); i++) {
                    if (model.getDate() == viewList.get(i).getDate()
                            && viewList.get(i).getHltExflg() == 0) {
                        model.setDspBorderFlg(true);
                    }
                }
            }
        }
    }

    /**
     * <br>[機  能] 拡張登録を日付に直し、休日リストを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラムモデル
     * @param holidayList 登録されている休日情報
     * @return 休日リスト
     */
    private List<Man024knHolidayModel> __changeKakutyo(
            Man024knParamModel paramMdl,
            List<CmnHolidayTemplateModel> holidayList) {

        List<Man024knHolidayModel> retHolidayList = new ArrayList<Man024knHolidayModel>();

        //休日選択が空だった場合
        if (holidayList == null) {
            return retHolidayList;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);

        for (CmnHolidayTemplateModel model : holidayList) {

            Man024knHolidayModel viewModel = new Man024knHolidayModel();
            Man024knHolidayModel kyujitsuMdl = new Man024knHolidayModel();

            boolean borderDspFlg = false;

            UDate date = new UDate();
            UDate huriDate = new UDate();
            //「休日文字列」作成
            int hltExflg = model.getHltExflg();
            String dspString = "";
            int intSetMon = -1;
            int intSetDay = -1;

            viewModel.setHltExflg(model.getHltExflg());

            if (hltExflg == CmnHolidayTemplateModel.HLT_EXFLG_NORMAL) {

                //選択した休日を表示する用
                dspString = model.getHltDateMonth()
                        + gsMsg.getMessage("cmn.month")
                        + "　"
                        + model.getHltDateDay() + gsMsg.getMessage("cmn.day");
                intSetMon = model.getHltDateMonth();
                intSetDay = model.getHltDateDay();
                date.setDate(Integer.parseInt(paramMdl.getMan020DspYear()),
                        model.getHltDateMonth(), intSetDay);

                viewModel.setHltDateMonth(model.getHltDateMonth());

                //振替用
                huriDate.setDate(Integer.parseInt(paramMdl.getMan020DspYear()),
                        model.getHltDateMonth(), intSetDay);

                int intCompMon = model.getHltDateMonth();

                //閏年判定
                if (date.getMonth() != intCompMon) {
                    borderDspFlg = true;
                }

            } else {
                String strMon = Integer.toString(model.getHltExMonth());
                String strSyu = Integer.toString(model.getHltExWeekMonth());
                String strYoubi =
                        MaintenanceUtil.getStrYoubiForCalendar(model.getHltExDayWeek(), reqMdl__);

                date.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));

                //31日が存在しない月対策用
                date.setDay(1);

                date.setMonth(model.getHltExMonth());
                boolean result = MaintenanceUtil.isAccurateWeekOfMonth(date,
                        MaintenanceUtil.getIntYoubiForCalendar(model.getHltExDayWeek()));
                if (result) {
                    date.setWeek(model.getHltExWeekMonth());
                } else {
                    date.setWeek(model.getHltExWeekMonth() + 1);
                }
                date.setDayOfWeek(MaintenanceUtil.getIntYoubiForCalendar(model.getHltExDayWeek()));

                viewModel.setHltExMonth(model.getHltExMonth());

                huriDate.setDate(date.getDateString());

                intSetMon = date.getMonth();
                intSetDay = date.getIntDay();

                dspString = "（" + strMon
                        + gsMsg.getMessage("cmn.month")
                        + MaintenanceUtil.getWeek(Integer.parseInt(strSyu), reqMdl__)
                        + strYoubi + "）";

                if (intSetMon == Integer.parseInt(strMon)) {
                    dspString = intSetMon
                            + gsMsg.getMessage("cmn.month")
                            + "　"
                            + intSetDay + gsMsg.getMessage("cmn.day") + dspString;
                } else {
                    borderDspFlg = true;
                }
            }

            viewModel.setHltExFurikae(model.getHltExFurikae());

            kyujitsuMdl = __setViewParts(dspString, viewModel, model, date, paramMdl, borderDspFlg);

            retHolidayList.add(kyujitsuMdl);
        }

        return retHolidayList;
    }

    /**
     * <br>[機  能] 休日テンプレートより休日の登録を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionSid セッションユーザ
     * @return true:登録成功 false:登録失敗
     * @throws SQLException SQL実行時例外
     */
    public boolean execDataTran(Man024knParamModel paramMdl,
            Connection con, int sessionSid)
                    throws SQLException {

        boolean commitFlg = false;
        CmnHolidayDao holiDao = new CmnHolidayDao(con);

        UDate now = new UDate();

        try {
            for (Man024knHolidayModel mdl : paramMdl.getMan024knTemplateList()) {
                if (mdl.isDspBorderFlg() == false) {
                    CmnHolidayModel holiModel = new CmnHolidayModel();
                    UDate hltDate = new UDate();
                    hltDate.setDate(mdl.getStrDate());
                    holiModel.setHolDate(hltDate);
                    hltDate.setDate(String.valueOf(mdl.getDate()));
                    holiModel.setHolYear(hltDate.getYear());
                    holiModel.setHolName(mdl.getHltName());
                    holiModel.setHolUpuser(sessionSid);
                    holiModel.setHolUpdate(now);

                    if (mdl.getAsterisk() == GSConstMain.HOLIDAY_TEMPLATE_ASTERISK_FLG_YES) {
                        holiDao.updateHoliday(holiModel, hltDate);
                    } else {
                        holiModel.setHolAduser(sessionSid);
                        holiModel.setHolAddate(now);
                        holiDao.insertHoliday(holiModel);
                    }
                }
            }
            commitFlg = true;
        } catch (SQLException e) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            log__.error(gsMsg.getMessage(GSConstMain.HOLIDAY_MSG) + "の登録に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        return commitFlg;
    }

    /**
     * <br>[機  能] 振替時の各種画面設定を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param hurikaeViewModel Man024knHolidayModel
     * @param dspFurikaeString 表示用文字列
     * @param huriDate 振替日用UDate
     * @param borderDspFlg 打消し表示フラグ
     * @param model CmnHolidayTemplateModel
     * @return Man023HolidayTemplateModel
     */
    private Man024knHolidayModel __addHurikaeViewSet(
            Man024knHolidayModel hurikaeViewModel,
            String dspFurikaeString,
            UDate huriDate,
            boolean borderDspFlg,
            CmnHolidayTemplateModel model
            ) {

        hurikaeViewModel = new Man024knHolidayModel();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        hurikaeViewModel.setHltSid(-1);
        hurikaeViewModel.setHltName(gsMsg.getMessage("main.src.man024kn.4"));
        hurikaeViewModel.setViewDate(dspFurikaeString);
        hurikaeViewModel.setDate(Integer.parseInt(huriDate.getDateString()));
        hurikaeViewModel.setStrDate(huriDate.getDateString()); //８桁日付保持
        hurikaeViewModel.setHurikaeFlg(true);
        hurikaeViewModel.setDspBorderFlg(borderDspFlg);
        hurikaeViewModel.setHltExflg(model.getHltExflg());

        return hurikaeViewModel;
    }

    /**
     * <br>[機  能] 既存データチェックを行い「※」表示制御する
     * <br>[解  説]
     * <br>[備  考]
     * @param viewModel Man023HolidayTemplateModel
     * @param con Connection
     * @throws SQLException SQL実行時例外
     */
    private void __setAsterisk(List<Man024knHolidayModel> viewModel,
            Connection con
            ) throws SQLException {

        List<CmnHolidayModel> holidayMdl = new ArrayList<CmnHolidayModel>();
        CmnHolidayDao holiDao = new CmnHolidayDao(con);
        List<UDate> kyujituDate = new ArrayList<UDate>();
        UDate date = null;

        for (Man024knHolidayModel model : viewModel) {
            date = new UDate();
            date.setDate(String.valueOf(model.getDate()));
            kyujituDate.add(date);
        }

        holidayMdl = holiDao.getHoliDayList(kyujituDate);

        for (Man024knHolidayModel model : viewModel) {
            for (CmnHolidayModel holiMdl : holidayMdl) {
                if (model.getDate()
                        == Integer.valueOf(holiMdl.getHolDate().getDateString())) {
                    model.setAsterisk(GSConstMain.HOLIDAY_TEMPLATE_ASTERISK_FLG_YES);
                }
            }
        }
    }

    /**
     * <br>[機  能] 画面表示に必要な項目をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param dspString 表示文字列
     * @param viewModel 表示モデル
     * @param model CmnHolidayTemplateModel
     * @param date UDate
     * @param paramMdl パラメータ情報
     * @param borderDspFlg 打消し線表示可否
     * @return 休日リスト
     */
    private Man024knHolidayModel __setViewParts(
            String dspString,
            Man024knHolidayModel viewModel,
            CmnHolidayTemplateModel model,
            UDate date,
            Man024knParamModel paramMdl,
            boolean borderDspFlg) {
        viewModel.setViewDate(dspString);

        log__.debug("START");

        //「休日名文字列」作成
        String holiday = "";
        holiday = model.getHltName();

        viewModel.setHltName(holiday);

        //リストへセット
        date = new UDate();

        date.setTime(0);
        if (model.getHltExflg() == 0) {
            date.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));
            date.setMonth(model.getHltDateMonth());
            date.setDay(model.getHltDateDay());
        } else {
            date.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));
            date.setMonth(model.getHltExMonth());
            boolean result = MaintenanceUtil.isAccurateWeekOfMonth(date,
                    MaintenanceUtil.getIntYoubiForCalendar(model.getHltExDayWeek()));
            if (result) {
                date.setWeek(model.getHltExWeekMonth());
            } else {
                date.setWeek(model.getHltExWeekMonth() + 1);
            }
            //date.setWeek(model.getHltExWeekMonth());
            date.setDayOfWeek(MaintenanceUtil
                    .getIntYoubiForCalendar(model.getHltExDayWeek()));
        }
        viewModel.setDate(Integer.parseInt(date.getDateString()));

        viewModel.setDspBorderFlg(borderDspFlg);
        viewModel.setStrDate(date.getDateString()); //８桁日付保持

        return viewModel;
    }
}

