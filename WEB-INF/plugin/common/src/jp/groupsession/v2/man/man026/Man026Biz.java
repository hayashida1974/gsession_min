package jp.groupsession.v2.man.man026;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.man.man025.Man025Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/拡張画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man026Biz extends Man025Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man026Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man026Biz() { }


    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man026ParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayTemplateDao holiDao = new CmnHolidayTemplateDao(con);
        CmnHolidayTemplateModel parmModel = new CmnHolidayTemplateModel();

        parmModel.setHltSid(paramMdl.getEditHltSid());
        CmnHolidayTemplateModel dbHoliModel = holiDao.select(parmModel);
        paramMdl.setMan026FuriFlg(dbHoliModel.getHltExFurikae());
        paramMdl.setMan026HltName(dbHoliModel.getHltName());
        paramMdl.setMan026HltMonth(dbHoliModel.getHltExMonth());
        paramMdl.setMan026HltWeek(dbHoliModel.getHltExWeekMonth());
        paramMdl.setMan026HltDayOfWeek(dbHoliModel.getHltExDayWeek());

    }

    /**
     * <br>[機  能] CMN_HOLIDAY_TEMLATEテーブルのデータセット・登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsid セッションユーザSID
     * @param sid テンプレートSID
     * @return boolean DB更新結果
     * @throws SQLException SQL実行時例外
     */
    public boolean execDataTran(Man026ParamModel paramMdl, Connection con, int sessionUsid, int sid)
    throws SQLException {

        CmnHolidayTemplateModel holiModel = new CmnHolidayTemplateModel();
        UDate udate = new UDate();

        holiModel.setHltSid(sid);

        holiModel.setHltDateMonth(0);
        holiModel.setHltDateDay(0);

        holiModel.setHltExMonth(paramMdl.getMan026HltMonth());
        holiModel.setHltExWeekMonth(paramMdl.getMan026HltWeek());
        holiModel.setHltExDayWeek(paramMdl.getMan026HltDayOfWeek());
        log__.debug("form.getMan026HltDayOfWeek() :" + paramMdl.getMan026HltDayOfWeek());
        holiModel.setHltName(paramMdl.getMan026HltName());
        holiModel.setHltUpuser(sessionUsid);
        holiModel.setHltUpdate(udate);

        holiModel.setHltExFurikae(paramMdl.getMan026FuriFlg());
        holiModel.setHltExflg(1);

        holiModel.setHltAduser(sessionUsid);
        holiModel.setHltAddate(udate);

        boolean commitFlg = _processCommit(con, holiModel, paramMdl.getProcessMode());

        return commitFlg;
    }

    /**
     * <br>[機  能] 指定された休日が存在するかを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return boolean true :存在 false :非存在
     * @throws SQLException SQL実行時例外
     */
    public boolean existHoliday(Man026ParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayTemplateDao hltDao = new CmnHolidayTemplateDao(con);
        CmnHolidayTemplateModel hltMdl = new CmnHolidayTemplateModel();
        hltMdl.setHltExMonth(paramMdl.getMan026HltMonth());
        hltMdl.setHltExWeekMonth(paramMdl.getMan026HltWeek());
        hltMdl.setHltExDayWeek(paramMdl.getMan026HltDayOfWeek());
        boolean exist = false;
        if (paramMdl.getProcessMode().equals(PROCESS_ADD)) {
            exist = hltDao.isExistSelectDateEx(hltMdl);
        } else {
            hltMdl.setHltSid(paramMdl.getEditHltSid());
            exist = hltDao.isExistSelectDateExSid(hltMdl);
        }
        log__.debug("拡張日付判定結果" + exist);
        return exist;
    }

    /**
     * <br>[機  能] 第何週コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  週コンボ
     */
    public static ArrayList<LabelValueBean> getWeekLabel(RequestModel reqMdl) {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int week = 1; week <= 5; week++) {
            labelList.add(
                    new LabelValueBean(
                            MaintenanceUtil.getWeek(week, reqMdl), String.valueOf(week)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 曜日コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public static ArrayList<LabelValueBean> getDayOfWeekLabel(RequestModel reqMdl) {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 7; i++) {
            labelList.add(
                    new LabelValueBean(MaintenanceUtil.getYoubi(i, reqMdl), String.valueOf(i)));
        }
        return labelList;
    }

}
