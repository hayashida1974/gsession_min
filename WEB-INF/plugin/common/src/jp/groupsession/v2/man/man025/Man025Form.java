package jp.groupsession.v2.man.man025;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man023.Man023Form;
import jp.groupsession.v2.man.man023.Man023HolidayTemplateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/通常画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man025Form extends Man023Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man025Form.class);


    /** 処理モード */
    private String processMode__ = null;

    /** 休日テンプレートSID */
    private int editHltSid__ = -1;
    /** 年度 */
    private String man020DspYear__ = null;

    /** 日付　月 */
    private int man025HltMonth__ = -1;
    /** 日付　日 */
    private int man025HltDay__ = -1;
    /** 入力休日名 */
    private String man025HltName__ = null;

    /** 休日テンプレート全選択 */
    private int man023CheckAll__ = 0;

    /** 自動振替フラグ */
    private int man025FuriFlg__ = 1;

    /** 月リスト */
    private ArrayList < LabelValueBean > man025MonthLabel__ = null;
    /** 日リスト */
    private ArrayList < LabelValueBean > man025DayLabel__ = null;

    /** 削除対象休日 */
    private String[] holDate__ = null;
    /** 修正対象休日 */
    private String editHolDate__ = null;

    /** 休日テンプレート一覧リストデータ */
    private Man023HolidayTemplateModel[] man023TemplateList__ = null;


    /**
     * @return processMode__ を戻します。
     */
    public String getProcessMode() {
        return processMode__;
    }

    /**
     * @param processMode 設定する processMode__。
     */
    public void setProcessMode(String processMode) {
        processMode__ = processMode;
    }

    /**
     * @return editHolDate を戻します。
     */
    public String getEditHolDate() {
        return editHolDate__;
    }

    /**
     * @param editHolDate 設定する editHolDate。
     */
    public void setEditHolDate(String editHolDate) {
        editHolDate__ = editHolDate;
    }

    /**
     * @return holDate を戻します。
     */
    public String[] getHolDate() {
        return holDate__;
    }

    /**
     * @param holDate 設定する holDate。
     */
    public void setHolDate(String[] holDate) {
        holDate__ = holDate;
    }

    /**
     * @return man020DspYear を戻します。
     */
    public String getMan020DspYear() {
        return man020DspYear__;
    }

    /**
     * @param man020DspYear 設定する man020DspYear。
     */
    public void setMan020DspYear(String man020DspYear) {
        man020DspYear__ = man020DspYear;
    }

    /**
     * @return man023TemplateList__ を戻します。
     */
    public Man023HolidayTemplateModel[] getMan023TemplateList() {
        return man023TemplateList__;
    }

    /**
     * @param man023TemplateList 設定する man023TemplateList__。
     */
    public void setMan023TemplateList(Man023HolidayTemplateModel[] man023TemplateList) {
        this.man023TemplateList__ = man023TemplateList;
    }

    /**
     * @return editHltSid__ を戻します。
     */
    public int getEditHltSid() {
        return editHltSid__;
    }

    /**
     * @param editHltSid 設定する editHltSid__。
     */
    public void setEditHltSid(int editHltSid) {
        this.editHltSid__ = editHltSid;
    }


    /**
     * @return man025HltName__ を戻します。
     */
    public String getMan025HltName() {
        return man025HltName__;
    }

    /**
     * @param man025HltName 設定する man025HltName__。
     */
    public void setMan025HltName(String man025HltName) {
        this.man025HltName__ = man025HltName;
    }

    /**
     * @return man025FuriFlg__ を戻します。
     */
    public int getMan025FuriFlg() {
        return man025FuriFlg__;
    }

    /**
     * @param man025FuriFlg 設定する man025FuriFlg__。
     */
    public void setMan025FuriFlg(int man025FuriFlg) {
        this.man025FuriFlg__ = man025FuriFlg;
    }

    /**
     * @return man025HltDay__ を戻します。
     */
    public int getMan025HltDay() {
        return man025HltDay__;
    }

    /**
     * @param man025HltDay 設定する man025HltDay__。
     */
    public void setMan025HltDay(int man025HltDay) {
        this.man025HltDay__ = man025HltDay;
    }

    /**
     * @return man025HltMonth__ を戻します。
     */
    public int getMan025HltMonth() {
        return man025HltMonth__;
    }

    /**
     * @param man025HltMonth 設定する man025HltMonth__。
     */
    public void setMan025HltMonth(int man025HltMonth) {
        this.man025HltMonth__ = man025HltMonth;
    }

    /**
     * @return man025DayLabel を戻します。
     */
    public ArrayList < LabelValueBean > getMan025DayLabel() {
        return man025DayLabel__;
    }
    /**
     * @param man025DayLabel 設定する man025DayLabel。
     */
    public void setMan025DayLabel(ArrayList < LabelValueBean > man025DayLabel) {
        man025DayLabel__ = man025DayLabel;
    }
    /**
     * @return man025MonthLabel を戻します。
     */
    public ArrayList < LabelValueBean > getMan025MonthLabel() {
        return man025MonthLabel__;
    }
    /**
     * @param man025MonthLabel 設定する man025MonthLabel。
     */
    public void setMan025MonthLabel(ArrayList < LabelValueBean > man025MonthLabel) {
        man025MonthLabel__ = man025MonthLabel;
    }

    /**
     * @return man023CheckAll__ を戻します。
     */
    public int getMan023CheckAll() {
        return man023CheckAll__;
    }

    /**
     * @param man023CheckAll 設定する man023CheckAll__。
     */
    public void setMan023CheckAll(int man023CheckAll) {
        this.man023CheckAll__ = man023CheckAll;
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
        log__.debug("man025HltMonth__ :" + man025HltMonth__);
        log__.debug("man025HltDay__ :" + man025HltDay__);
        log__.debug("man025HltName__ :" + man025HltName__);

        //日付 月のチェック
        if (man025HltMonth__ < 1) {
            msg = new ActionMessage("error.select.required.text",
                                getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE_MONTH));
            errors.add("man025HltMonth.error.select.required.text", msg);
            errorDate = true;
        }

        //日付 日のチェック
        if (man025HltDay__ < 1) {
            msg = new ActionMessage("error.select.required.text",
                                    getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE_DAY));
            errors.add("man025HltDay.error.select.required.text", msg);
            errorDate = true;
        }

        //日付のチェック
        if (!errorDate) {
            Man025ParamModel paramMdl = new Man025ParamModel();
            paramMdl.setParam(this);
            Man025Biz biz = new Man025Biz();

            if (!Man025Biz.isDateInput(man025HltMonth__, man025HltDay__)) {
                msg = new ActionMessage("error.input.notfound.date",
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE));
                StrutsUtil.addMessage(errors, msg, "error.input.notfound.date");
            } else if (biz.existHoliday(paramMdl, con)) {
                msg = new ActionMessage("error.input.exist.data",
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE),
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_DATE));
                StrutsUtil.addMessage(errors, msg, "error.input.exist.data");
            }
        }

        //休日名のチェック
        if (StringUtil.isNullZeroString(man025HltName__)) {
            msg = new ActionMessage("error.input.required.text",
                               getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, "man025HltName.error.input.required.text");
        } else if (ValidateUtil.isSpace(man025HltName__)) {
            msg = new ActionMessage("error.input.spase.only",
                               getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, "man025HltName.error.input.spase.only");

        } else {
            if (ValidateUtil.isSpaceStart(man025HltName__)) {
                //先頭スペースチェック
                msg = new ActionMessage("error.input.spase.start",
                           getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME));
                StrutsUtil.addMessage(errors, msg, "man025HltName.error.input.spase.start");
            }
            //先頭スペースチェック
            if (man025HltName__.length() > GSConstMain.MAX_LENGTH_HOL_NAME) {
                msg = new ActionMessage("error.input.length.text",
                        getInterMessage(req, GSConstMain.TEXT_HOLIDAY_NAME),
                                        GSConstMain.MAX_LENGTH_HOL_NAME);
                StrutsUtil.addMessage(errors, msg, "man025HltName.error.input.length.text");
            }
            if (!GSValidateUtil.isGsJapaneaseString(man025HltName__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(man025HltName__);
                msg = new ActionMessage("error.input.njapan.text",
                                        GSConstMain.TEXT_HOLIDAY_NAME, nstr);
                StrutsUtil.addMessage(errors, msg, "man025HltName.error.input.njapan.text");
            }
        }
        return errors;
    }

}
