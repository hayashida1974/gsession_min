package jp.groupsession.v2.man.man026;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man025.Man025Form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/拡張画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man026Form extends Man025Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man026Form.class);

    /** 日付　月-拡張 */
    private int man026HltMonth__ = -1;
    /** 日付　週-拡張 */
    private int man026HltWeek__ = -1;
    /** 日付曜日-拡張 */
    private int man026HltDayOfWeek__ = -1;

    /** 休日名-拡張 */
    private String man026HltName__ = null;
    /** 自動振替フラグ */
    private int man026FuriFlg__ = 1;

    /** 月リスト-拡張 */
    private ArrayList < LabelValueBean > man026MonthLabel__ = null;
    /** 週リスト-拡張 */
    private ArrayList < LabelValueBean > man026WeekLabel__ = null;
    /** 曜日リスト-拡張 */
    private ArrayList < LabelValueBean > man026DayOfWeekLabel__ = null;


    /**
     * @return man026HltDayOfWeek__ を戻します。
     */
    public int getMan026HltDayOfWeek() {
        return man026HltDayOfWeek__;
    }

    /**
     * @param man026HltDayOfWeek 設定する man026HltDayOfWeek__。
     */
    public void setMan026HltDayOfWeek(int man026HltDayOfWeek) {
        this.man026HltDayOfWeek__ = man026HltDayOfWeek;
    }

    /**
     * @return man026HltMonth__ を戻します。
     */
    public int getMan026HltMonth() {
        return man026HltMonth__;
    }

    /**
     * @param man026HltMonth 設定する man026HltMonth__。
     */
    public void setMan026HltMonth(int man026HltMonth) {
        this.man026HltMonth__ = man026HltMonth;
    }

    /**
     * @return man026HltWeek__ を戻します。
     */
    public int getMan026HltWeek() {
        return man026HltWeek__;
    }

    /**
     * @param man026HltWeek 設定する man026HltWeek__。
     */
    public void setMan026HltWeek(int man026HltWeek) {
        this.man026HltWeek__ = man026HltWeek;
    }

    /**
     * @return man026MonthLabel__ を戻します。
     */
    public ArrayList < LabelValueBean > getMan026MonthLabel() {
        return man026MonthLabel__;
    }

    /**
     * @param man026MonthLabel 設定する man026MonthLabel__。
     */
    public void setMan026MonthLabel(ArrayList < LabelValueBean > man026MonthLabel) {
        this.man026MonthLabel__ = man026MonthLabel;
    }

    /**
     * @return man026WeekLabel__ を戻します。
     */
    public ArrayList < LabelValueBean > getMan026WeekLabel() {
        return man026WeekLabel__;
    }

    /**
     * @param man026WeekLabel 設定する man026WeekLabel__。
     */
    public void setMan026WeekLabel(ArrayList < LabelValueBean > man026WeekLabel) {
        this.man026WeekLabel__ = man026WeekLabel;
    }

    /**
     * @return man026DayOfWeekLabel__ を戻します。
     */
    public ArrayList < LabelValueBean > getMan026DayOfWeekLabel() {
        return man026DayOfWeekLabel__;
    }

    /**
     * @param man026DayOfWeekLabel 設定する man026DayOfWeekLabel__。
     */
    public void setMan026DayOfWeekLabel(
            ArrayList < LabelValueBean > man026DayOfWeekLabel) {
        this.man026DayOfWeekLabel__ = man026DayOfWeekLabel;
    }

    /**
     * @return man026HltName__ を戻します。
     */
    public String getMan026HltName() {
        return man026HltName__;
    }

    /**
     * @param man026HltName 設定する man026HltName__。
     */
    public void setMan026HltName(String man026HltName) {
        this.man026HltName__ = man026HltName;
    }


    /**
     * @return man026FuriFlg__ を戻します。
     */
    public int getMan026FuriFlg() {
        return man026FuriFlg__;
    }

    /**
     * @param man026FuriFlg 設定する man026FuriFlg__。
     */
    public void setMan026FuriFlg(int man026FuriFlg) {
        this.man026FuriFlg__ = man026FuriFlg;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL例外発生
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        boolean errorDate = false;

        log__.debug("検査パラメータ");
        log__.debug("man026HltMonth__ :" + man026HltMonth__);
        log__.debug("man026HltWeek__ :" + man026HltWeek__);
        log__.debug("man025HltDayOfWeek__ :" + man026HltDayOfWeek__);
        log__.debug("man026HltName__ :" + man026HltName__);


        //日付 月のチェック
        if (man026HltMonth__ < 1) {
            msg = new ActionMessage("error.select.required.text",
                                    getInterMessage(req,
                                            GSConstMain.TEXT_HOLIDAY_TEMPLATE_DATE_MONTH));
            errors.add("man026HltMonth.error.select.required.text", msg);
            errorDate = true;
        }
        //日付 週のチェック
        if (man026HltWeek__ < 1) {
            msg = new ActionMessage("error.select.required.text",
                                     getInterMessage(req,
                                             GSConstMain.TEXT_HOLIDAY_TEMPLATE_DATE_WEEK));
            errors.add("man026HltWeek.error.select.required.text", msg);
            errorDate = true;
        }
        //日付 曜日のチェック
        if (man026HltDayOfWeek__ < 0) {
            msg = new ActionMessage("error.select.required.text",
                                      getInterMessage(req,
                                              GSConstMain.TEXT_HOLIDAY_TEMPLATE_DATE_DAY_OF_WEEK));
            errors.add("man026HltDayOfWeek.error.select.required.text", msg);
            errorDate = true;
        }


        //日付のチェック
        if (!errorDate) {
            Man026ParamModel paramMdl = new Man026ParamModel();
            paramMdl.setParam(this);
            Man026Biz biz = new Man026Biz();
            if ((biz.existHoliday(paramMdl, con))) {
                msg = new ActionMessage("error.input.exist.data",
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE),
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE));
                StrutsUtil.addMessage(errors, msg, "error.input.exist.data");
             }
        }

        //休日名のチェック
        if (StringUtil.isNullZeroString(man026HltName__) || ValidateUtil.isSpace(man026HltName__)) {
            msg = new ActionMessage("error.input.required.text",
                    getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, "man026HltName.error.input.required.text");
        } else if (ValidateUtil.isSpace(man026HltName__)) {
            msg = new ActionMessage("error.input.spase.only",
                    getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, "man026HltName.error.input.spase.only");

        } else {
            if (ValidateUtil.isSpaceStart(man026HltName__)) {
                //先頭スペースチェック
                msg = new ActionMessage("error.input.spase.start",
                      getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME));
                StrutsUtil.addMessage(errors, msg, "man026HltName.error.input.required.text");
            }
            if (man026HltName__.length() > GSConstMain.MAX_LENGTH_HOL_NAME) {
                msg = new ActionMessage("error.input.length.text",
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME),
                                        GSConstMain.MAX_LENGTH_HOL_NAME);
                StrutsUtil.addMessage(errors, msg, "man026HltName.error.input.length.text");
            }
            if (!GSValidateUtil.isGsJapaneaseString(man026HltName__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(man026HltName__);
                msg = new ActionMessage("error.input.njapan.text",
                                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME),
                                        nstr);
                StrutsUtil.addMessage(errors, msg, "man026HltName.error.input.njapan.text");
            }
        }
        return errors;
    }
}
