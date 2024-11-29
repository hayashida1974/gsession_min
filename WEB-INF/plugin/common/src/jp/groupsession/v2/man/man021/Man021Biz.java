package jp.groupsession.v2.man.man021;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 休日設定/追加画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man021Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man021Biz() { }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man021Biz(RequestModel reqMdl) {
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
    public void setInitData(Man021ParamModel paramMdl, Connection con)
    throws SQLException {

        //リクエストパラメータを取得
        if (paramMdl.getMan021CmdMode() == GSConstMain.CMDMODE_HOLIDAY_EDIT) {
            //処理モード = 編集の場合は休日情報を取得する
            CmnHolidayDao holDao = new CmnHolidayDao(con);
            UDate holiday = new UDate();
            holiday.setDate(paramMdl.getEditHolDate());

            CmnHolidayModel holMdl = new CmnHolidayModel();
            holMdl.setHolDate(holiday);
            CmnHolidayModel result = holDao.select(holMdl);

            paramMdl.setMan021HolMonth(result.getHolDate().getMonth());
            paramMdl.setMan021HolDay(result.getHolDate().getIntDay());
            paramMdl.setMan021HolName(result.getHolName());

            //入力チェック用に変更前の日付を保持
            paramMdl.setMan021HolMonthOld(paramMdl.getMan021HolMonth());
            paramMdl.setMan021HolDayOld(paramMdl.getMan021HolDay());
        }
    }

    /**
     * <br>[機  能] 指定された休日が存在するかを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public boolean existHoliday(Man021ParamModel paramMdl, Connection con)
    throws SQLException {

        boolean exist = false;

        UDate holiday = new UDate();
        holiday.setMilliSecond(0);
        holiday.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));
        holiday.setMonth(paramMdl.getMan021HolMonth());
        holiday.setDay(paramMdl.getMan021HolDay());

        CmnHolidayDao holDao = new CmnHolidayDao(con);
        CmnHolidayModel holMdl = new CmnHolidayModel();
        holMdl.setHolDate(holiday);
        CmnHolidayModel result = holDao.select(holMdl);
        if (result != null && result.getHolDate() != null) {
            exist = true;
        }
        return exist;
    }

    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public List<LabelValueBean> getMonthLabel() {
        int month = 1;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
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
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public List<LabelValueBean> getDayLabel() {
        int day = 1;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day
                            + gsMsg.getMessage("cmn.day"), String.valueOf(day)));
            day++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 休日情報の新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertHoliday(Man021ParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayModel holMdl = createHolidayData(paramMdl);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        holDao.insertHoliday(holMdl);

    }

    /**
     * <br>[機  能] 休日情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateHoliday(Man021ParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayModel holMdl = createHolidayData(paramMdl);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        UDate targetHolDate = new UDate();
        targetHolDate.setDate(paramMdl.getEditHolDate());

        holDao.updateHoliday(holMdl, targetHolDate);

    }

    /**
     * <br>[機  能] パラメータを元に休日情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return CmnHolidayModel 休日情報
     */
    public CmnHolidayModel createHolidayData(Man021ParamModel paramMdl) {

        //休日日付を作成
        UDate holDate = new UDate();
        holDate.setMilliSecond(0);
        holDate.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));
        holDate.setMonth(paramMdl.getMan021HolMonth());
        holDate.setDay(paramMdl.getMan021HolDay());

        UDate nowDate = new UDate();

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        CmnHolidayModel holMdl = new CmnHolidayModel();
        holMdl.setHolDate(holDate);
        holMdl.setHolYear(holDate.getYear());
        holMdl.setHolName(paramMdl.getMan021HolName());
        holMdl.setHolAduser(sessionUsrSid);
        holMdl.setHolAddate(nowDate);
        holMdl.setHolUpuser(sessionUsrSid);
        holMdl.setHolUpdate(nowDate);

        return holMdl;
    }
}
