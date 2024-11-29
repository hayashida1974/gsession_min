package jp.groupsession.v2.rng.rng170;

import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040Form;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 稟議 手動データ削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng170Form extends Rng040Form {

    /** 申請中 手動削除区分 */
    private int rng170pendingKbn__ = RngConst.MANU_DEL_NO;
    /** 申請中 手動削除 年 */
    private int rng170pendingYear__ = RngConst.YEAR_THREE;
    /** 申請中 手動削除 月 */
    private int rng170pendingMonth__ = RngConst.DEL_MONTH_START;
    /** 申請中 手動削除 日 */
    private int rng170pendingDay__ = RngConst.DEL_MONTH_START;
    /** 完了 手動削除区分 */
    private int rng170completeKbn__ = RngConst.RAD_KBN_NO;
    /** 完了 手動削除 年 */
    private int rng170completeYear__ = RngConst.YEAR_THREE;
    /** 完了 手動削除 月 */
    private int rng170completeMonth__ = RngConst.DEL_MONTH_START;
    /** 完了 手動削除 日 */
    private int rng170completeDay__ = RngConst.DEL_MONTH_START;
    /** 草稿 手動削除区分 */
    private int rng170draftKbn__ = RngConst.RAD_KBN_NO;
    /** 草稿 手動削除 年 */
    private int rng170draftYear__ = RngConst.YEAR_THREE;
    /** 草稿 手動削除 月 */
    private int rng170draftMonth__ = RngConst.DEL_MONTH_START;
    /** 草稿 手動削除 日 */
    private int rng170draftDay__ = RngConst.DEL_MONTH_START;

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
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //削除区分が全て削除しないの時
        if (rng170pendingKbn__ == GSConst.MANUAL_DEL_NO
                && rng170completeKbn__ == GSConst.MANUAL_DEL_NO
                && rng170draftKbn__ == GSConst.MANUAL_DEL_NO) {

            ActionMessage msg =  new ActionMessage("error.input.selectoen.check",
                    gsMsg.getMessage("cmn.cmn310.06"));
            String eprefix = "allNotDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        boolean bYearError = false;
        boolean bMonthError = false;
        boolean bDayError = false;

        //申請中
        if (rng170pendingKbn__ == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = rng170pendingYear__;
            int targetMonth = rng170pendingMonth__;
            int targetDay = rng170pendingDay__;
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("rng.48"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "rngPenYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("rng.48"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "rngPenMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bDayError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("rng.48"),
                        gsMsg.getMessage("cmn.passage.day"));
                String eprefix = "rngPenDay";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("rng.48"),
                        gsMsg.getMessage("cmn.oneday"));
                String eprefix = "rngPenLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (rng170pendingKbn__ == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("rng.62"),
                    gsMsg.getMessage("rng.48"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "rngPenDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //完了
        if (rng170completeKbn__ == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = rng170completeYear__;
            int targetMonth = rng170completeMonth__;
            int targetDay = rng170completeDay__;
            bYearError = false;
            bMonthError = false;
            bDayError = false;
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.complete"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "rngCompYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.complete"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "rngCompMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bDayError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.complete"),
                        gsMsg.getMessage("cmn.passage.day"));
                String eprefix = "rngCompDay";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.complete"),
                        gsMsg.getMessage("cmn.oneday"));
                String eprefix = "rngCompLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (rng170completeKbn__ == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("rng.62"),
                    gsMsg.getMessage("cmn.complete"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "rngCompDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //草稿
        if (rng170draftKbn__ == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = rng170draftYear__;
            int targetMonth = rng170draftMonth__;
            int targetDay = rng170draftDay__;
            bYearError = false;
            bMonthError = false;
            bDayError = false;
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.draft"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "rngDraftYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.draft"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "rngDraftMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bDayError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.draft"),
                        gsMsg.getMessage("cmn.passage.day"));
                String eprefix = "rngDraftDay";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("rng.62"),
                        gsMsg.getMessage("cmn.draft"),
                        gsMsg.getMessage("cmn.oneday"));
                String eprefix = "rngDraftLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (rng170draftKbn__ == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("rng.62"),
                    gsMsg.getMessage("cmn.draft"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "rngDraftDelKbn";
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
     * <p>rng170pendingKbn を取得します。
     * @return rng170pendingKbn
     */
    public int getRng170pendingKbn() {
        return rng170pendingKbn__;
    }
    /**
     * <p>rng170pendingKbn をセットします。
     * @param rng170pendingKbn rng170pendingKbn
     */
    public void setRng170pendingKbn(int rng170pendingKbn) {
        rng170pendingKbn__ = rng170pendingKbn;
    }
    /**
     * <p>rng170pendingYear を取得します。
     * @return rng170pendingYear
     */
    public int getRng170pendingYear() {
        return rng170pendingYear__;
    }
    /**
     * <p>rng170pendingYear をセットします。
     * @param rng170pendingYear rng170pendingYear
     */
    public void setRng170pendingYear(int rng170pendingYear) {
        rng170pendingYear__ = rng170pendingYear;
    }
    /**
     * <p>rng170pendingMonth を取得します。
     * @return rng170pendingMonth
     */
    public int getRng170pendingMonth() {
        return rng170pendingMonth__;
    }
    /**
     * <p>rng170pendingMonth をセットします。
     * @param rng170pendingMonth rng170pendingMonth
     */
    public void setRng170pendingMonth(int rng170pendingMonth) {
        rng170pendingMonth__ = rng170pendingMonth;
    }
    /**
     * <p>rng170pendingDay を取得します。
     * @return rng170pendingDay
     */
    public int getRng170pendingDay() {
        return rng170pendingDay__;
    }
    /**
     * <p>rng170pendingDay をセットします。
     * @param rng170pendingDay rng170pendingDay
     */
    public void setRng170pendingDay(int rng170pendingDay) {
        rng170pendingDay__ = rng170pendingDay;
    }
    /**
     * <p>rng170completeKbn を取得します。
     * @return rng170completeKbn
     */
    public int getRng170completeKbn() {
        return rng170completeKbn__;
    }
    /**
     * <p>rng170completeKbn をセットします。
     * @param rng170completeKbn rng170completeKbn
     */
    public void setRng170completeKbn(int rng170completeKbn) {
        rng170completeKbn__ = rng170completeKbn;
    }
    /**
     * <p>rng170completeYear を取得します。
     * @return rng170completeYear
     */
    public int getRng170completeYear() {
        return rng170completeYear__;
    }
    /**
     * <p>rng170completeYear をセットします。
     * @param rng170completeYear rng170completeYear
     */
    public void setRng170completeYear(int rng170completeYear) {
        rng170completeYear__ = rng170completeYear;
    }
    /**
     * <p>rng170completeMonth を取得します。
     * @return rng170completeMonth
     */
    public int getRng170completeMonth() {
        return rng170completeMonth__;
    }
    /**
     * <p>rng170completeMonth をセットします。
     * @param rng170completeMonth rng170completeMonth
     */
    public void setRng170completeMonth(int rng170completeMonth) {
        rng170completeMonth__ = rng170completeMonth;
    }
    /**
     * <p>rng170completeDay を取得します。
     * @return rng170completeDay
     */
    public int getRng170completeDay() {
        return rng170completeDay__;
    }
    /**
     * <p>rng170completeDay をセットします。
     * @param rng170completeDay rng170completeDay
     */
    public void setRng170completeDay(int rng170completeDay) {
        rng170completeDay__ = rng170completeDay;
    }
    /**
     * <p>rng170draftKbn を取得します。
     * @return rng170draftKbn
     */
    public int getRng170draftKbn() {
        return rng170draftKbn__;
    }
    /**
     * <p>rng170draftKbn をセットします。
     * @param rng170draftKbn rng170draftKbn
     */
    public void setRng170draftKbn(int rng170draftKbn) {
        rng170draftKbn__ = rng170draftKbn;
    }
    /**
     * <p>rng170draftYear を取得します。
     * @return rng170draftYear
     */
    public int getRng170draftYear() {
        return rng170draftYear__;
    }
    /**
     * <p>rng170draftYear をセットします。
     * @param rng170draftYear rng170draftYear
     */
    public void setRng170draftYear(int rng170draftYear) {
        rng170draftYear__ = rng170draftYear;
    }
    /**
     * <p>rng170draftMonth を取得します。
     * @return rng170draftMonth
     */
    public int getRng170draftMonth() {
        return rng170draftMonth__;
    }
    /**
     * <p>rng170draftMonth をセットします。
     * @param rng170draftMonth rng170draftMonth
     */
    public void setRng170draftMonth(int rng170draftMonth) {
        rng170draftMonth__ = rng170draftMonth;
    }
    /**
     * <p>rng170draftDay を取得します。
     * @return rng170draftDay
     */
    public int getRng170draftDay() {
        return rng170draftDay__;
    }
    /**
     * <p>rng170draftDay をセットします。
     * @param rng170draftDay rng170draftDay
     */
    public void setRng170draftDay(int rng170draftDay) {
        rng170draftDay__ = rng170draftDay;
    }
}
