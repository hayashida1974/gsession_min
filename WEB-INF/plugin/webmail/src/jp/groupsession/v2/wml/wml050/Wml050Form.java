package jp.groupsession.v2.wml.wml050;

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
 * <br>[機  能] WEBメール 自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml050Form extends Wml020Form {

    /** 初期表示区分 */
    private String wml050initFlg__ = String.valueOf(GSConstWebmail.DSP_FIRST);
    /** ゴミ箱 自動削除区分 */
    private String wml050dustKbn__ = String.valueOf(GSConstWebmail.WAD_DUST_NO);
    /** ゴミ箱 自動削除 年 */
    private int wml050dustYear__ = GSConstWebmail.YEAR_ZERO;
    /** ゴミ箱 自動削除 月 */
    private int wml050dustMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** ゴミ箱 自動削除 日 */
    private int wml050dustDay__ = GSConstWebmail.DEL_MONTH_START;
    /** 送信済み 自動削除区分 */
    private String wml050sendKbn__ = String.valueOf(GSConstWebmail.WAD_SEND_NO);
    /** 送信済み 自動削除 年 */
    private int wml050sendYear__ = GSConstWebmail.YEAR_ZERO;
    /** 送信済み 自動削除 月 */
    private int wml050sendMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** 送信済み 自動削除 日 */
    private int wml050sendDay__ = GSConstWebmail.DEL_DAY_START;
    /** 草稿 自動削除区分 */
    private String wml050draftKbn__ = String.valueOf(GSConstWebmail.WAD_DRAFT_NO);
    /** 草稿 自動削除 年 */
    private int wml050draftYear__ = GSConstWebmail.YEAR_ZERO;
    /** 草稿 自動削除 月 */
    private int wml050draftMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** 草稿 自動削除 日 */
    private int wml050draftDay__ = GSConstWebmail.DEL_DAY_START;
    /** 受信箱 自動削除区分 */
    private String wml050resvKbn__ = String.valueOf(GSConstWebmail.WAD_RESV_NO);
    /** 受信箱 自動削除 年 */
    private int wml050resvYear__ = GSConstWebmail.YEAR_ZERO;
    /** 受信箱 自動削除 月 */
    private int wml050resvMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** 受信箱 自動削除 日 */
    private int wml050resvDay__ = GSConstWebmail.DEL_DAY_START;
    /** 保管 自動削除区分 */
    private String wml050keepKbn__ = String.valueOf(GSConstWebmail.WAD_KEEP_NO);
    /** 保管 自動削除 年 */
    private int wml050keepYear__ = GSConstWebmail.YEAR_ZERO;
    /** 保管 自動削除 月 */
    private int wml050keepMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** 保管 自動削除 日 */
    private int wml050keepDay__ = GSConstWebmail.DEL_DAY_START;

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
        boolean bYearError = false;
        boolean bMonthError = false;
        boolean bDayError = false;
            //ゴミ箱
            if (NullDefault.getInt(wml050dustKbn__, 3) == 2) {
                int targetYear = wml050dustYear__;
                int targetMonth = wml050dustMonth__;
                int targetDay = wml050dustDay__;
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
                          + gsMsg.getMessage("cmn.trash"));
                    String eprefix = "wmlautoDelTrash";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                          + gsMsg.getMessage("cmn.trash"),
                            gsMsg.getMessage("cmn.oneday"));
                    String eprefix = "wmlautoDelTrash";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(wml050dustKbn__, 3) == GSConst.AUTO_DEL_NO
                    || NullDefault.getInt(wml050dustKbn__, 3) == 1) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cmn.trash"));
                String eprefix = "wmlautoDelTrash";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //送信済み
            if (NullDefault.getInt(wml050sendKbn__, 3) == 2) {
                int targetYear = wml050sendYear__;
                int targetMonth = wml050sendMonth__;
                int targetDay = wml050sendDay__;
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
                if (!bYearError || !bMonthError || !bDayError) {
                    ActionMessage msg =  new ActionMessage("error.autodel.between",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                            + gsMsg.getMessage("cmn.sent"));
                    String eprefix = "wmlautoDelSend";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                                    + gsMsg.getMessage("cmn.sent"),
                            gsMsg.getMessage("cmn.oneday"));
                    String eprefix = "wmlautoDelSend";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(wml050sendKbn__, 3) == GSConst.AUTO_DEL_NO
                    || NullDefault.getInt(wml050sendKbn__, 3) == 1) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " 
                        + gsMsg.getMessage("cmn.sent"));
                String eprefix = "wmlautoDelSend";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //草稿
            if (NullDefault.getInt(wml050draftKbn__, 3) == 2) {
                int targetYear = wml050draftYear__;
                int targetMonth = wml050draftMonth__;
                int targetDay = wml050draftDay__;
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
                if (!bYearError || !bMonthError || !bDayError) {
                    ActionMessage msg =  new ActionMessage("error.autodel.between",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                            + gsMsg.getMessage("cmn.draft"));
                    String eprefix = "wmlautoDelDraft";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                                    + gsMsg.getMessage("cmn.draft"),
                            gsMsg.getMessage("cmn.oneday"));
                    String eprefix = "wmlautoDelDraft";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(wml050draftKbn__, 3) == GSConst.AUTO_DEL_NO
                    || NullDefault.getInt(wml050draftKbn__, 3) == 1) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " 
                        + gsMsg.getMessage("cmn.draft"));
                String eprefix = "wmlautoDelDraft";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //受信
            if (NullDefault.getInt(wml050resvKbn__, 3) == 2) {
                int targetYear = wml050resvYear__;
                int targetMonth = wml050resvMonth__;
                int targetDay = wml050resvDay__;
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
                if (!bYearError || !bMonthError || !bDayError) {
                    ActionMessage msg =  new ActionMessage("error.autodel.between",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                            + gsMsg.getMessage("cmn.receive"));
                    String eprefix = "wmlautoDelRecive";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                                    + gsMsg.getMessage("cmn.receive"),
                            gsMsg.getMessage("cmn.oneday"));
                    String eprefix = "wmlautoDelRecive";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(wml050resvKbn__, 3) == GSConst.AUTO_DEL_NO
                    || NullDefault.getInt(wml050resvKbn__, 3) == 1) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " 
                        + gsMsg.getMessage("cmn.receive"));
                String eprefix = "wmlautoDelRecive";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //保管
            if (NullDefault.getInt(wml050keepKbn__, 3) == 2) {
                int targetYear = wml050keepYear__;
                int targetMonth = wml050keepMonth__;
                int targetDay = wml050keepDay__;
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
                if (!bYearError || !bMonthError || !bDayError) {
                    ActionMessage msg =  new ActionMessage("error.autodel.between",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                            + gsMsg.getMessage("cmn.strage"));
                    String eprefix = "wmlautoDelSave";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0 && targetDay == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("wml.wml010.25"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                                    + gsMsg.getMessage("cmn.strage"),
                            gsMsg.getMessage("cmn.oneday"));
                    String eprefix = "wmlautoDelSave";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(wml050keepKbn__, 3) == GSConst.AUTO_DEL_NO
                    || NullDefault.getInt(wml050keepKbn__, 3) == 1) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("wml.wml010.25"),
                        gsMsg.getMessage("cmn.autodelete") + " " 
                        + gsMsg.getMessage("cmn.strage"));
                String eprefix = "wmlautoDelSave";
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
     * <p>wml050draftKbn を取得します。
     * @return wml050draftKbn
     */
    public String getWml050draftKbn() {
        return wml050draftKbn__;
    }
    /**
     * <p>wml050draftKbn をセットします。
     * @param wml050draftKbn wml050draftKbn
     */
    public void setWml050draftKbn(String wml050draftKbn) {
        wml050draftKbn__ = wml050draftKbn;
    }
    /**
     * <p>wml050draftMonth を取得します。
     * @return wml050draftMonth
     */
    public int getWml050draftMonth() {
        return wml050draftMonth__;
    }
    /**
     * <p>wml050draftMonth をセットします。
     * @param wml050draftMonth wml050draftMonth
     */
    public void setWml050draftMonth(int wml050draftMonth) {
        wml050draftMonth__ = wml050draftMonth;
    }
    /**
     * <p>wml050draftYear を取得します。
     * @return wml050draftYear
     */
    public int getWml050draftYear() {
        return wml050draftYear__;
    }
    /**
     * <p>wml050draftYear をセットします。
     * @param wml050draftYear wml050draftYear
     */
    public void setWml050draftYear(int wml050draftYear) {
        wml050draftYear__ = wml050draftYear;
    }
    /**
     * <p>wml050resvKbn を取得します。
     * @return wml050resvKbn
     */
    public String getWml050resvKbn() {
        return wml050resvKbn__;
    }
    /**
     * <p>wml050resvKbn をセットします。
     * @param wml050resvKbn wml050resvKbn
     */
    public void setWml050resvKbn(String wml050resvKbn) {
        wml050resvKbn__ = wml050resvKbn;
    }
    /**
     * <p>wml050resvMonth を取得します。
     * @return wml050resvMonth
     */
    public int getWml050resvMonth() {
        return wml050resvMonth__;
    }
    /**
     * <p>wml050resvMonth をセットします。
     * @param wml050resvMonth wml050resvMonth
     */
    public void setWml050resvMonth(int wml050resvMonth) {
        wml050resvMonth__ = wml050resvMonth;
    }
    /**
     * <p>wml050resvYear を取得します。
     * @return wml050resvYear
     */
    public int getWml050resvYear() {
        return wml050resvYear__;
    }
    /**
     * <p>wml050resvYear をセットします。
     * @param wml050resvYear wml050resvYear
     */
    public void setWml050resvYear(int wml050resvYear) {
        wml050resvYear__ = wml050resvYear;
    }
    /**
     * <p>wml050dustKbn を取得します。
     * @return wml050dustKbn
     */
    public String getWml050dustKbn() {
        return wml050dustKbn__;
    }
    /**
     * <p>wml050dustKbn をセットします。
     * @param wml050dustKbn wml050dustKbn
     */
    public void setWml050dustKbn(String wml050dustKbn) {
        wml050dustKbn__ = wml050dustKbn;
    }
    /**
     * <p>wml050dustMonth を取得します。
     * @return wml050dustMonth
     */
    public int getWml050dustMonth() {
        return wml050dustMonth__;
    }
    /**
     * <p>wml050dustMonth をセットします。
     * @param wml050dustMonth wml050dustMonth
     */
    public void setWml050dustMonth(int wml050dustMonth) {
        wml050dustMonth__ = wml050dustMonth;
    }
    /**
     * <p>wml050dustYear を取得します。
     * @return wml050dustYear
     */
    public int getWml050dustYear() {
        return wml050dustYear__;
    }
    /**
     * <p>wml050dustYear をセットします。
     * @param wml050dustYear wml050dustYear
     */
    public void setWml050dustYear(int wml050dustYear) {
        wml050dustYear__ = wml050dustYear;
    }
    /**
     * <p>wml050initFlg を取得します。
     * @return wml050initFlg
     */
    public String getWml050initFlg() {
        return wml050initFlg__;
    }
    /**
     * <p>wml050initFlg をセットします。
     * @param wml050initFlg wml050initFlg
     */
    public void setWml050initFlg(String wml050initFlg) {
        wml050initFlg__ = wml050initFlg;
    }
    /**
     * <p>wml050sendDay を取得します。
     * @return wml050sendDay
     */
    public int getWml050sendDay() {
        return wml050sendDay__;
    }
    /**
     * <p>wml050sendDay をセットします。
     * @param wml050sendDay wml050sendDay
     */
    public void setWml050sendDay(int wml050sendDay) {
        wml050sendDay__ = wml050sendDay;
    }
    /**
     * <p>wml050sendKbn を取得します。
     * @return wml050sendKbn
     */
    public String getWml050sendKbn() {
        return wml050sendKbn__;
    }
    /**
     * <p>wml050sendKbn をセットします。
     * @param wml050sendKbn wml050sendKbn
     */
    public void setWml050sendKbn(String wml050sendKbn) {
        wml050sendKbn__ = wml050sendKbn;
    }
    /**
     * <p>wml050sendMonth を取得します。
     * @return wml050sendMonth
     */
    public int getWml050sendMonth() {
        return wml050sendMonth__;
    }
    /**
     * <p>wml050sendMonth をセットします。
     * @param wml050sendMonth wml050sendMonth
     */
    public void setWml050sendMonth(int wml050sendMonth) {
        wml050sendMonth__ = wml050sendMonth;
    }
    /**
     * <p>wml050sendYear を取得します。
     * @return wml050sendYear
     */
    public int getWml050sendYear() {
        return wml050sendYear__;
    }
    /**
     * <p>wml050sendYear をセットします。
     * @param wml050sendYear wml050sendYear
     */
    public void setWml050sendYear(int wml050sendYear) {
        wml050sendYear__ = wml050sendYear;
    }
    /**
     * <p>wml050keepKbn を取得します。
     * @return wml050keepKbn
     */
    public String getWml050keepKbn() {
        return wml050keepKbn__;
    }
    /**
     * <p>wml050keepKbn をセットします。
     * @param wml050keepKbn wml050keepKbn
     */
    public void setWml050keepKbn(String wml050keepKbn) {
        wml050keepKbn__ = wml050keepKbn;
    }
    /**
     * <p>wml050keepMonth を取得します。
     * @return wml050keepMonth
     */
    public int getWml050keepMonth() {
        return wml050keepMonth__;
    }
    /**
     * <p>wml050keepMonth をセットします。
     * @param wml050keepMonth wml050keepMonth
     */
    public void setWml050keepMonth(int wml050keepMonth) {
        wml050keepMonth__ = wml050keepMonth;
    }
    /**
     * <p>wml050keepYear を取得します。
     * @return wml050keepYear
     */
    public int getWml050keepYear() {
        return wml050keepYear__;
    }
    /**
     * <p>wml050keepYear をセットします。
     * @param wml050keepYear wml050keepYear
     */
    public void setWml050keepYear(int wml050keepYear) {
        wml050keepYear__ = wml050keepYear;
    }
    /**
     * <p>wml050draftDay を取得します。
     * @return wml050draftDay
     */
    public int getWml050draftDay() {
        return wml050draftDay__;
    }
    /**
     * <p>wml050draftDay をセットします。
     * @param wml050draftDay wml050draftDay
     */
    public void setWml050draftDay(int wml050draftDay) {
        wml050draftDay__ = wml050draftDay;
    }
    /**
     * <p>wml050dustDay を取得します。
     * @return wml050dustDay
     */
    public int getWml050dustDay() {
        return wml050dustDay__;
    }
    /**
     * <p>wml050dustDay をセットします。
     * @param wml050dustDay wml050dustDay
     */
    public void setWml050dustDay(int wml050dustDay) {
        wml050dustDay__ = wml050dustDay;
    }
    /**
     * <p>wml050keepDay を取得します。
     * @return wml050keepDay
     */
    public int getWml050keepDay() {
        return wml050keepDay__;
    }
    /**
     * <p>wml050keepDay をセットします。
     * @param wml050keepDay wml050keepDay
     */
    public void setWml050keepDay(int wml050keepDay) {
        wml050keepDay__ = wml050keepDay;
    }
    /**
     * <p>wml050resvDay を取得します。
     * @return wml050resvDay
     */
    public int getWml050resvDay() {
        return wml050resvDay__;
    }
    /**
     * <p>wml050resvDay をセットします。
     * @param wml050resvDay wml050resvDay
     */
    public void setWml050resvDay(int wml050resvDay) {
        wml050resvDay__ = wml050resvDay;
    }
}
