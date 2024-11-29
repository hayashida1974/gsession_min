package jp.groupsession.v2.wml.wml170;

import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.wml020.Wml020Form;

/**
 * <br>[機  能] WEBメール 送受信ログ自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml170Form extends Wml020Form {

    /** 初期表示区分 */
    private String wml170initFlg__ = String.valueOf(GSConstWebmail.DSP_FIRST);
    /** 自動削除区分 */
    private String wml170delKbn__ = String.valueOf(GSConstWebmail.WAL_KBN_NOSET);
    /** 自動削除 年 */
    private int wml170delYear__ = GSConstWebmail.YEAR_ZERO;
    /** 自動削除 月 */
    private int wml170delMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** 自動削除 日 */
    private int wml170delDay__ = GSConstWebmail.DEL_DAY_START;

    /** 年コンボ */
    private List<LabelValueBean> yearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabelList__ = null;
    /** 日コンボ */
    private List<LabelValueBean> dayLabelList__ = null;
    
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            ActionErrors errors) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean bYearError = false;
        boolean bMonthError = false;
        boolean bDayError = false;
 
        if (NullDefault.getInt(wml170delKbn__, 3) == GSConst.AUTO_DEL_LIMIT) {
            int targetYear = wml170delYear__;
            int targetMonth = wml170delMonth__;
            int targetDay = wml170delDay__;
            for (int y: GSConst.DEL_YEAR_DATE) {
                if (y == targetYear) {
                    bYearError = true;
                    break;
                }
            }
            for (int m: GSConst.DEL_MONTH_DATE) {
                if (m == targetMonth) {
                    bMonthError = true;
                    break;
                }
            }
            for (int d: GSConst.DEL_DAY_DATE) {
                if (d == targetDay) {
                    bDayError = true;
                    break;
                }
            }
            if (!bYearError || !bMonthError || !bDayError) {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " 
                      + gsMsg.getMessage("wml.wml170kn.02"));
                String eprefix = "wmlautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            } else if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " 
                      + gsMsg.getMessage("wml.wml170kn.02"),
                        gsMsg.getMessage("cmn.oneday"));
                String eprefix = "wmlautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(wml170delKbn__, 3) == GSConst.AUTO_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.autodel.between",
                    gsMsg.getMessage("wml.wml010.25"),
                    gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("wml.wml170kn.02"));
            String eprefix = "wmlautoDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
            
        return errors;
    }

    /**
     * <p>dayLabelList を取得します。
     * @return dayLabelList
     */
    public List<LabelValueBean> getDayLabelList() {
        return dayLabelList__;
    }
    /**
     * <p>dayLabelList をセットします。
     * @param dayLabelList dayLabelList
     */
    public void setDayLabelList(List<LabelValueBean> dayLabelList) {
        dayLabelList__ = dayLabelList;
    }
    /**
     * <p>monthLabelList を取得します。
     * @return monthLabelList
     */
    public List<LabelValueBean> getMonthLabelList() {
        return monthLabelList__;
    }
    /**
     * <p>monthLabelList をセットします。
     * @param monthLabelList monthLabelList
     */
    public void setMonthLabelList(List<LabelValueBean> monthLabelList) {
        monthLabelList__ = monthLabelList;
    }
    /**
     * <p>yearLabelList を取得します。
     * @return yearLabelList
     */
    public List<LabelValueBean> getYearLabelList() {
        return yearLabelList__;
    }
    /**
     * <p>yearLabelList をセットします。
     * @param yearLabelList yearLabelList
     */
    public void setYearLabelList(List<LabelValueBean> yearLabelList) {
        yearLabelList__ = yearLabelList;
    }
    /**
     * <p>wml170delKbn を取得します。
     * @return wml170delKbn
     */
    public String getWml170delKbn() {
        return wml170delKbn__;
    }
    /**
     * <p>wml170delKbn をセットします。
     * @param wml170delKbn wml170delKbn
     */
    public void setWml170delKbn(String wml170delKbn) {
        wml170delKbn__ = wml170delKbn;
    }
    /**
     * <p>wml170delMonth を取得します。
     * @return wml170delMonth
     */
    public int getWml170delMonth() {
        return wml170delMonth__;
    }
    /**
     * <p>wml170delMonth をセットします。
     * @param wml170delMonth wml170delMonth
     */
    public void setWml170delMonth(int wml170delMonth) {
        wml170delMonth__ = wml170delMonth;
    }
    /**
     * <p>wml170delYear を取得します。
     * @return wml170delYear
     */
    public int getWml170delYear() {
        return wml170delYear__;
    }
    /**
     * <p>wml170delYear をセットします。
     * @param wml170delYear wml170delYear
     */
    public void setWml170delYear(int wml170delYear) {
        wml170delYear__ = wml170delYear;
    }
    /**
     * <p>wml170delDay を取得します。
     * @return wml170delDay
     */
    public int getWml170delDay() {
        return wml170delDay__;
    }
    /**
     * <p>wml170delDay をセットします。
     * @param wml170delDay wml170delDay
     */
    public void setWml170delDay(int wml170delDay) {
        wml170delDay__ = wml170delDay;
    }
    /**
     * <p>wml170initFlg を取得します。
     * @return wml170initFlg
     */
    public String getWml170initFlg() {
        return wml170initFlg__;
    }
    /**
     * <p>wml170initFlg をセットします。
     * @param wml170initFlg wml170initFlg
     */
    public void setWml170initFlg(String wml170initFlg) {
        wml170initFlg__ = wml170initFlg;
    }
}
