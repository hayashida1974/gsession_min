package jp.groupsession.v2.man.man021;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man020.Man020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 休日設定/追加画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man021Form extends Man020Form {

    /** 日付 月 */
    private int man021HolMonth__ = -1;
    /** 日付 日 */
    private int man021HolDay__ = -1;
    /** 日付 月 変更前*/
    private int man021HolMonthOld__ = -1;
    /** 日付 日 変更前 */
    private int man021HolDayOld__ = -1;
    /** 休日名 */
    private String man021HolName__ = null;
    /** 処理モード */
    private int man021CmdMode__ = 0;
    /** 月リスト */
    private List < LabelValueBean > man021MonthLabel__ = null;
    /** 日リスト */
    private List < LabelValueBean > man021DayLabel__ = null;

    /**
     * @return man021HolDay を戻します。
     */
    public int getMan021HolDay() {
        return man021HolDay__;
    }
    /**
     * @param man021HolDay 設定する man021HolDay。
     */
    public void setMan021HolDay(int man021HolDay) {
        man021HolDay__ = man021HolDay;
    }
    /**
     * @return man021HolMonth を戻します。
     */
    public int getMan021HolMonth() {
        return man021HolMonth__;
    }
    /**
     * @param man021HolMonth 設定する man021HolMonth。
     */
    public void setMan021HolMonth(int man021HolMonth) {
        man021HolMonth__ = man021HolMonth;
    }
    /**
     * @return man021HolDayOld を戻します。
     */
    public int getMan021HolDayOld() {
        return man021HolDayOld__;
    }
    /**
     * @param man021HolDayOld 設定する man021HolDayOld。
     */
    public void setMan021HolDayOld(int man021HolDayOld) {
        man021HolDayOld__ = man021HolDayOld;
    }
    /**
     * @return man021HolMonthOld を戻します。
     */
    public int getMan021HolMonthOld() {
        return man021HolMonthOld__;
    }
    /**
     * @param man021HolMonthOld 設定する man021HolMonthOld。
     */
    public void setMan021HolMonthOld(int man021HolMonthOld) {
        man021HolMonthOld__ = man021HolMonthOld;
    }
    /**
     * @return man021HolName を戻します。
     */
    public String getMan021HolName() {
        return man021HolName__;
    }
    /**
     * @param man021HolName 設定する man021HolName。
     */
    public void setMan021HolName(String man021HolName) {
        man021HolName__ = man021HolName;
    }
    /**
     * @return man021CmdMode を戻します。
     */
    public int getMan021CmdMode() {
        return man021CmdMode__;
    }
    /**
     * @param man021CmdMode 設定する man021CmdMode。
     */
    public void setMan021CmdMode(int man021CmdMode) {
        man021CmdMode__ = man021CmdMode;
    }
    /**
     * @return man021DayLabel を戻します。
     */
    public List < LabelValueBean > getMan021DayLabel() {
        return man021DayLabel__;
    }
    /**
     * @param man021DayLabel 設定する man021DayLabel。
     */
    public void setMan021DayLabel(List < LabelValueBean > man021DayLabel) {
        man021DayLabel__ = man021DayLabel;
    }
    /**
     * @return man021MonthLabel を戻します。
     */
    public List < LabelValueBean > getMan021MonthLabel() {
        return man021MonthLabel__;
    }
    /**
     * @param man021MonthLabel 設定する man021MonthLabel。
     */
    public void setMan021MonthLabel(List < LabelValueBean > man021MonthLabel) {
        man021MonthLabel__ = man021MonthLabel;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL例外発生
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        boolean errorDate = false;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //日付 月のチェック
        if (man021HolMonth__ < 1) {
            msg = new ActionMessage("error.select.required.text",
                                gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE_MONTH));
            errors.add("man021HolMonth.error.select.required.text", msg);
            errorDate = true;
        }
        //日付 日のチェック
        if (man021HolDay__ < 1) {
            msg = new ActionMessage("error.select.required.text",
                                    gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE_DAY));
            errors.add("man021HolDay.error.select.required.text", msg);
            errorDate = true;
        }

        //日付のチェック
        if (!errorDate) {
            Man021Biz biz = new Man021Biz(reqMdl);


            StringBuilder strHolDate = new StringBuilder("");
            strHolDate.append(getMan020DspYear());
            strHolDate.append(StringUtil.toDecFormat(man021HolMonth__, "00"));
            strHolDate.append(StringUtil.toDecFormat(man021HolDay__, "00"));
            if (!ValidateUtil.isExistDateYyyyMMdd(strHolDate.toString())) {
                msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE));
                StrutsUtil.addMessage(errors, msg, "error.input.notfound.date");
            } else {
                //処理モード=編集 かつ 日付が同じ場合、重複チェックを行わない
                boolean check = true;
                if (man021CmdMode__ == GSConstMain.CMDMODE_HOLIDAY_EDIT) {
                    if (man021HolMonth__ == man021HolMonthOld__
                    && man021HolDay__ == man021HolDayOld__) {
                        check = false;
                    }
                }

                Man021ParamModel paramMdl = new Man021ParamModel();
                paramMdl.setParam(this);
                if (check && biz.existHoliday(paramMdl, con)) {

                    msg = new ActionMessage("error.input.exist.data",
                                        gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE),
                                        gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE));
                    StrutsUtil.addMessage(errors, msg, "error.input.exist.data");

                }
            }
        }

        //休日名のチェック
        if (StringUtil.isNullZeroString(man021HolName__)) {
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, "man021HolName.error.input.required.text");
        } else if (ValidateUtil.isSpace(man021HolName__)) {
            msg = new ActionMessage("error.input.spase.only",
                             gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, "man021HolName.error.input.spase.only");
        } else {
            //先頭スペースチェック
            if (ValidateUtil.isSpaceStart(man021HolName__)) {
                msg = new ActionMessage("error.input.spase.start",
                                        gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME));
                StrutsUtil.addMessage(errors, msg, "man021HolName.error.input.spase.start");
            }
            //文字長チェック
            if (man021HolName__.length() > GSConstMain.MAX_LENGTH_HOL_NAME) {
                msg = new ActionMessage("error.input.length.text",
                                        gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME),
                                        GSConstMain.MAX_LENGTH_HOL_NAME);
                StrutsUtil.addMessage(errors, msg, "man021HolName.error.input.length.text");
            }
            //利用不可能な文字を入力した場合
            if (!GSValidateUtil.isGsJapaneaseString(man021HolName__)) {
                String nstr = GSValidateUtil.getNotGsJapaneaseString(man021HolName__);
                msg = new ActionMessage("error.input.njapan.text",
                                        gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME),
                                        nstr);
                StrutsUtil.addMessage(errors, msg, "man021HolName.error.input.njapan.text");
            }
        }

        return errors;
    }
}
