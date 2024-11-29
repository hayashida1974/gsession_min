package jp.groupsession.v2.sml.sml140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml120.Sml120Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 個人設定 手動削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml140Form extends Sml120Form {

    /** 設定アカウント区分 */
    private int sml140SelKbn__ = GSConstSmail.ACCOUNT_SEL;
    /** 手動削除設定のアカウントSID */
    private int sml140AccountSid__;
    /** メ手動削除設定のアカウント名 */
    private String sml140AccountName__;

    //手動削除区分
    /** 受信タブ 処理区分 */
    private String sml140JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String sml140SdelKbn__ = null;
    /** 草稿タブ 処理区分 */
    private String sml140WdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String sml140DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> sml140YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> sml140MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String sml140JYear__ = null;
    /** 受信タブ 月選択 */
    private String sml140JMonth__ = null;
    /** 送信タブ 年選択 */
    private String sml140SYear__ = null;
    /** 送信タブ 月選択 */
    private String sml140SMonth__ = null;
    /** 草稿タブ 年選択 */
    private String sml140WYear__ = null;
    /** 草稿タブ 月選択 */
    private String sml140WMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String sml140DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String sml140DMonth__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSml140(
            Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(req);

        boolean bYearError = false;
        boolean bMonthError = false;
        //受信
        if (NullDefault.getInt(sml140JdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml140JYear__, 13);
            int targetMonth = NullDefault.getInt(sml140JMonth__, 12);
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.57"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "smlJYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.57"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "smlJMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.57"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "smlJLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(sml140JdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.57"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlJDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //送信
        if (NullDefault.getInt(sml140SdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml140SYear__, 13);
            int targetMonth = NullDefault.getInt(sml140SMonth__, 12);
            bYearError = false;
            bMonthError = false;
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.59"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "smlSYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.59"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "smlSMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.59"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "smlSLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(sml140SdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.59"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlSDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        //草稿
        if (NullDefault.getInt(sml140WdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml140WYear__, 13);
            int targetMonth = NullDefault.getInt(sml140WMonth__, 12);
            bYearError = false;
            bMonthError = false;
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.58"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "smlWyear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.58"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "smlWMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.58"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "smlWLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(sml140WdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.58"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlWDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //ゴミ箱
        if (NullDefault.getInt(sml140DdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml140DYear__, 13);
            int targetMonth = NullDefault.getInt(sml140DMonth__, 12);
            bYearError = false;
            bMonthError = false;
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.56"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "smlDYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.56"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "smlDMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cmn.shortmail"),
                        gsMsg.getMessage("sml.56"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "smlDLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(sml140DdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.56"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlDDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
     * <p>sml140JdelKbn を取得します。
     * @return sml140JdelKbn
     */
    public String getSml140JdelKbn() {
        return sml140JdelKbn__;
    }
    /**
     * <p>sml140JdelKbn をセットします。
     * @param sml140JdelKbn sml140JdelKbn
     */
    public void setSml140JdelKbn(String sml140JdelKbn) {
        sml140JdelKbn__ = sml140JdelKbn;
    }
    /**
     * <p>sml140SdelKbn を取得します。
     * @return sml140SdelKbn
     */
    public String getSml140SdelKbn() {
        return sml140SdelKbn__;
    }
    /**
     * <p>sml140SdelKbn をセットします。
     * @param sml140SdelKbn sml140SdelKbn
     */
    public void setSml140SdelKbn(String sml140SdelKbn) {
        sml140SdelKbn__ = sml140SdelKbn;
    }
    /**
     * <p>sml140WdelKbn を取得します。
     * @return sml140WdelKbn
     */
    public String getSml140WdelKbn() {
        return sml140WdelKbn__;
    }
    /**
     * <p>sml140WdelKbn をセットします。
     * @param sml140WdelKbn sml140WdelKbn
     */
    public void setSml140WdelKbn(String sml140WdelKbn) {
        sml140WdelKbn__ = sml140WdelKbn;
    }
    /**
     * <p>sml140DdelKbn を取得します。
     * @return sml140DdelKbn
     */
    public String getSml140DdelKbn() {
        return sml140DdelKbn__;
    }
    /**
     * <p>sml140DdelKbn をセットします。
     * @param sml140DdelKbn sml140DdelKbn
     */
    public void setSml140DdelKbn(String sml140DdelKbn) {
        sml140DdelKbn__ = sml140DdelKbn;
    }
    /**
     * <p>sml140YearLabelList を取得します。
     * @return sml140YearLabelList
     */
    public ArrayList<LabelValueBean> getSml140YearLabelList() {
        return sml140YearLabelList__;
    }
    /**
     * <p>sml140YearLabelList をセットします。
     * @param sml140YearLabelList sml140YearLabelList
     */
    public void setSml140YearLabelList(ArrayList<LabelValueBean> sml140YearLabelList) {
        sml140YearLabelList__ = sml140YearLabelList;
    }
    /**
     * <p>sml140MonthLabelList を取得します。
     * @return sml140MonthLabelList
     */
    public ArrayList<LabelValueBean> getSml140MonthLabelList() {
        return sml140MonthLabelList__;
    }
    /**
     * <p>sml140MonthLabelList をセットします。
     * @param sml140MonthLabelList sml140MonthLabelList
     */
    public void setSml140MonthLabelList(
            ArrayList<LabelValueBean> sml140MonthLabelList) {
        sml140MonthLabelList__ = sml140MonthLabelList;
    }
    /**
     * <p>sml140JYear を取得します。
     * @return sml140JYear
     */
    public String getSml140JYear() {
        return sml140JYear__;
    }
    /**
     * <p>sml140JYear をセットします。
     * @param sml140JYear sml140JYear
     */
    public void setSml140JYear(String sml140JYear) {
        sml140JYear__ = sml140JYear;
    }
    /**
     * <p>sml140JMonth を取得します。
     * @return sml140JMonth
     */
    public String getSml140JMonth() {
        return sml140JMonth__;
    }
    /**
     * <p>sml140JMonth をセットします。
     * @param sml140JMonth sml140JMonth
     */
    public void setSml140JMonth(String sml140JMonth) {
        sml140JMonth__ = sml140JMonth;
    }
    /**
     * <p>sml140SYear を取得します。
     * @return sml140SYear
     */
    public String getSml140SYear() {
        return sml140SYear__;
    }
    /**
     * <p>sml140SYear をセットします。
     * @param sml140SYear sml140SYear
     */
    public void setSml140SYear(String sml140SYear) {
        sml140SYear__ = sml140SYear;
    }
    /**
     * <p>sml140SMonth を取得します。
     * @return sml140SMonth
     */
    public String getSml140SMonth() {
        return sml140SMonth__;
    }
    /**
     * <p>sml140SMonth をセットします。
     * @param sml140SMonth sml140SMonth
     */
    public void setSml140SMonth(String sml140SMonth) {
        sml140SMonth__ = sml140SMonth;
    }
    /**
     * <p>sml140WYear を取得します。
     * @return sml140WYear
     */
    public String getSml140WYear() {
        return sml140WYear__;
    }
    /**
     * <p>sml140WYear をセットします。
     * @param sml140WYear sml140WYear
     */
    public void setSml140WYear(String sml140WYear) {
        sml140WYear__ = sml140WYear;
    }
    /**
     * <p>sml140WMonth を取得します。
     * @return sml140WMonth
     */
    public String getSml140WMonth() {
        return sml140WMonth__;
    }
    /**
     * <p>sml140WMonth をセットします。
     * @param sml140WMonth sml140WMonth
     */
    public void setSml140WMonth(String sml140WMonth) {
        sml140WMonth__ = sml140WMonth;
    }
    /**
     * <p>sml140DYear を取得します。
     * @return sml140DYear
     */
    public String getSml140DYear() {
        return sml140DYear__;
    }
    /**
     * <p>sml140DYear をセットします。
     * @param sml140DYear sml140DYear
     */
    public void setSml140DYear(String sml140DYear) {
        sml140DYear__ = sml140DYear;
    }
    /**
     * <p>sml140DMonth を取得します。
     * @return sml140DMonth
     */
    public String getSml140DMonth() {
        return sml140DMonth__;
    }
    /**
     * <p>sml140DMonth をセットします。
     * @param sml140DMonth sml140DMonth
     */
    public void setSml140DMonth(String sml140DMonth) {
        sml140DMonth__ = sml140DMonth;
    }
    /**
     * <p>sml140SelKbn を取得します。
     * @return sml140SelKbn
     */
    public int getSml140SelKbn() {
        return sml140SelKbn__;
    }
    /**
     * <p>sml140SelKbn をセットします。
     * @param sml140SelKbn sml140SelKbn
     */
    public void setSml140SelKbn(int sml140SelKbn) {
        sml140SelKbn__ = sml140SelKbn;
    }
    /**
     * <p>sml140AccountSid を取得します。
     * @return sml140AccountSid
     */
    public int getSml140AccountSid() {
        return sml140AccountSid__;
    }
    /**
     * <p>sml140AccountSid をセットします。
     * @param sml140AccountSid sml140AccountSid
     */
    public void setSml140AccountSid(int sml140AccountSid) {
        sml140AccountSid__ = sml140AccountSid;
    }
    /**
     * <p>sml140AccountName を取得します。
     * @return sml140AccountName
     */
    public String getSml140AccountName() {
        return sml140AccountName__;
    }
    /**
     * <p>sml140AccountName をセットします。
     * @param sml140AccountName sml140AccountName
     */
    public void setSml140AccountName(String sml140AccountName) {
        sml140AccountName__ = sml140AccountName;
    }

}