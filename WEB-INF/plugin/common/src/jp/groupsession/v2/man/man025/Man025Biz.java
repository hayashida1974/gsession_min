package jp.groupsession.v2.man.man025;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/通常画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man025Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man025Action.class);

    /** テンプレート追加ボタン押下時CMD */
    protected static final String PROCESS_ADD = "addTemp";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man025Biz() { }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(Man025ParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {

        CmnHolidayTemplateDao holiDao = new CmnHolidayTemplateDao(con);
        CmnHolidayTemplateModel parmModel = new CmnHolidayTemplateModel();

        parmModel.setHltSid(paramMdl.getEditHltSid());
        CmnHolidayTemplateModel dbHoliModel = holiDao.select(parmModel);
        paramMdl.setMan025FuriFlg(dbHoliModel.getHltExFurikae());
        paramMdl.setMan025HltName(dbHoliModel.getHltName());
        paramMdl.setMan025HltMonth(dbHoliModel.getHltDateMonth());
        paramMdl.setMan025HltDay(dbHoliModel.getHltDateDay());

    }

    /**
     * <br>[機  能] CMN_HOLIDAY_TEMLATEテーブルのデータセット・登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsid セッションユーザSID
     * @param sid テンプレートSID
     * @return boolean 更新結果
     * @throws SQLException SQL実行時例外
     */
    public boolean execDataTran(Man025ParamModel paramMdl, Connection con, int sessionUsid, int sid)
    throws SQLException {

        CmnHolidayTemplateModel holiModel = new CmnHolidayTemplateModel();
        UDate udate = new UDate();

        holiModel.setHltSid(sid);

        holiModel.setHltDateMonth(paramMdl.getMan025HltMonth());
        holiModel.setHltDateDay(paramMdl.getMan025HltDay());

        holiModel.setHltExMonth(0);
        holiModel.setHltExWeekMonth(0);
        holiModel.setHltExDayWeek(0);

        holiModel.setHltName(paramMdl.getMan025HltName());
        holiModel.setHltUpuser(sessionUsid);
        holiModel.setHltUpdate(udate);

        holiModel.setHltExFurikae(paramMdl.getMan025FuriFlg());
        holiModel.setHltExflg(0);

        holiModel.setHltAduser(sessionUsid);
        holiModel.setHltAddate(udate);

        //テーブル追加／更新処理
        boolean result = _processCommit(con, holiModel, paramMdl.getProcessMode());

        return result;
    }

    /**
     * <br>[機  能] トランザクションブロックの制御処理
     * <br>[解  説] ホリデーテンプレート更新時はこのexecute()をキックしてください
     * <br>[備  考]
     * @param con コネクション
     * @param model CmnHolidayTemplateModel
     * @param processMode 処理モード
     * @return boolean 更新判定
     * @throws SQLException SQL実行時例外
     */
    protected boolean _processCommit(Connection con,
            CmnHolidayTemplateModel model,
            String processMode) throws SQLException {
        boolean commitFlg = false;
        try {
            //テーブル追加／更新処理
            __execute(con, model, processMode);
            commitFlg = true;
        } catch (SQLException e) {
            if  (PROCESS_ADD.equals(processMode)) {
                log__.error("休日テンプレートの追加に失敗", e);
            } else {
                log__.error("休日テンプレートの更新に失敗", e);
            }
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
     * <br>[機  能] 休日テンプレートデータ登録／更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param model CmnHolidayTemplateModel
     * @param processMode 処理モード
     * @throws SQLException SQL実行時例外
     */
    private void __execute(Connection con,
            CmnHolidayTemplateModel model,
            String processMode) throws SQLException {

        CmnHolidayTemplateDao dao = new CmnHolidayTemplateDao(con);
        if (PROCESS_ADD.equals(processMode)) {
            log__.debug("■登録モード");
            dao.insert(model);
        } else {
            log__.debug("■変更モード");
            dao.updateCmnTemplate(model);
        }
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
    public boolean existHoliday(Man025ParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayTemplateDao hltDao = new CmnHolidayTemplateDao(con);
        CmnHolidayTemplateModel hltMdl = new CmnHolidayTemplateModel();
        hltMdl.setHltDateMonth(paramMdl.getMan025HltMonth());
        hltMdl.setHltDateDay(paramMdl.getMan025HltDay());
        boolean exist = false;
        if (paramMdl.getProcessMode().equals(PROCESS_ADD)) {
            exist = hltDao.isExistSelectDate(hltMdl);
        } else {
            hltMdl.setHltSid(paramMdl.getEditHltSid());
            exist = hltDao.isExistSelectDateSid(hltMdl);
        }
        log__.debug("ノーマル日付判定結果" + exist);
        return exist;
    }

    /**
     * <br>[機  能] 月日より日付の妥当性チェックを行います
     * <br>[解  説] 通年用のため、2/29は常にOKとします
     * <br>[備  考]
     * @param month 月
     * @param day 日
     * @return boolean 判定結果 true:OK, false:NG
     */
    public static boolean isDateInput(int month, int day) {

        boolean result = false;
        if (day <= 0) { return result; }

        switch (month) {
        case 2:
            if (day <= 29) { result = true; }
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            log__.debug("小の月");
            if (day <= 30) { result = true; }
            break;
        default:
            if (day <= 31) { result = true; }
        }

        return result;
    }

    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public static ArrayList<LabelValueBean> getMonthLabel(RequestModel reqMdl) {
        int month = 1;
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month
                            + gsMsg.getMessage("cmn.month"), String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 日コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public static ArrayList<LabelValueBean> getDayLabel(RequestModel reqMdl) {
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day
                            + gsMsg.getMessage("cmn.day"), String.valueOf(day)));
            day++;
        }
        return labelList;
    }

}
