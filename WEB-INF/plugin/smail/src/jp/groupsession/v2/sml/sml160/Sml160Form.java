package jp.groupsession.v2.sml.sml160;

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
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.sml100.Sml100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 管理者設定 手動削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml160Form extends Sml100Form {

    /** 設定アカウント区分 */
    private int sml160SelKbn__ = GSConstSmail.ACCOUNT_SEL;
    /** 手動削除設定のアカウントSID */
    private int sml160AccountSid__;
    /** メ手動削除設定のアカウント名 */
    private String sml160AccountName__;

    //手動削除区分
    /** 受信タブ 処理区分 */
    private String sml160JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String sml160SdelKbn__ = null;
    /** 草稿タブ 処理区分 */
    private String sml160WdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String sml160DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> sml160YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> sml160MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String sml160JYear__ = null;
    /** 受信タブ 月選択 */
    private String sml160JMonth__ = null;
    /** 送信タブ 年選択 */
    private String sml160SYear__ = null;
    /** 送信タブ 月選択 */
    private String sml160SMonth__ = null;
    /** 草稿タブ 年選択 */
    private String sml160WYear__ = null;
    /** 草稿タブ 月選択 */
    private String sml160WMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String sml160DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String sml160DMonth__ = null;

    /**
     * <p>sml160JdelKbn を取得します。
     * @return sml160JdelKbn
     */
    public String getSml160JdelKbn() {
        return sml160JdelKbn__;
    }
    /**
     * <p>sml160JdelKbn をセットします。
     * @param sml160JdelKbn sml160JdelKbn
     */
    public void setSml160JdelKbn(String sml160JdelKbn) {
        sml160JdelKbn__ = sml160JdelKbn;
    }
    /**
     * <p>sml160SdelKbn を取得します。
     * @return sml160SdelKbn
     */
    public String getSml160SdelKbn() {
        return sml160SdelKbn__;
    }
    /**
     * <p>sml160SdelKbn をセットします。
     * @param sml160SdelKbn sml160SdelKbn
     */
    public void setSml160SdelKbn(String sml160SdelKbn) {
        sml160SdelKbn__ = sml160SdelKbn;
    }
    /**
     * <p>sml160WdelKbn を取得します。
     * @return sml160WdelKbn
     */
    public String getSml160WdelKbn() {
        return sml160WdelKbn__;
    }
    /**
     * <p>sml160WdelKbn をセットします。
     * @param sml160WdelKbn sml160WdelKbn
     */
    public void setSml160WdelKbn(String sml160WdelKbn) {
        sml160WdelKbn__ = sml160WdelKbn;
    }
    /**
     * <p>sml160DdelKbn を取得します。
     * @return sml160DdelKbn
     */
    public String getSml160DdelKbn() {
        return sml160DdelKbn__;
    }
    /**
     * <p>sml160DdelKbn をセットします。
     * @param sml160DdelKbn sml160DdelKbn
     */
    public void setSml160DdelKbn(String sml160DdelKbn) {
        sml160DdelKbn__ = sml160DdelKbn;
    }
    /**
     * <p>sml160YearLabelList を取得します。
     * @return sml160YearLabelList
     */
    public ArrayList<LabelValueBean> getSml160YearLabelList() {
        return sml160YearLabelList__;
    }
    /**
     * <p>sml160YearLabelList をセットします。
     * @param sml160YearLabelList sml160YearLabelList
     */
    public void setSml160YearLabelList(ArrayList<LabelValueBean> sml160YearLabelList) {
        sml160YearLabelList__ = sml160YearLabelList;
    }
    /**
     * <p>sml160MonthLabelList を取得します。
     * @return sml160MonthLabelList
     */
    public ArrayList<LabelValueBean> getSml160MonthLabelList() {
        return sml160MonthLabelList__;
    }
    /**
     * <p>sml160MonthLabelList をセットします。
     * @param sml160MonthLabelList sml160MonthLabelList
     */
    public void setSml160MonthLabelList(
            ArrayList<LabelValueBean> sml160MonthLabelList) {
        sml160MonthLabelList__ = sml160MonthLabelList;
    }
    /**
     * <p>sml160JYear を取得します。
     * @return sml160JYear
     */
    public String getSml160JYear() {
        return sml160JYear__;
    }
    /**
     * <p>sml160JYear をセットします。
     * @param sml160JYear sml160JYear
     */
    public void setSml160JYear(String sml160JYear) {
        sml160JYear__ = sml160JYear;
    }
    /**
     * <p>sml160JMonth を取得します。
     * @return sml160JMonth
     */
    public String getSml160JMonth() {
        return sml160JMonth__;
    }
    /**
     * <p>sml160JMonth をセットします。
     * @param sml160JMonth sml160JMonth
     */
    public void setSml160JMonth(String sml160JMonth) {
        sml160JMonth__ = sml160JMonth;
    }
    /**
     * <p>sml160SYear を取得します。
     * @return sml160SYear
     */
    public String getSml160SYear() {
        return sml160SYear__;
    }
    /**
     * <p>sml160SYear をセットします。
     * @param sml160SYear sml160SYear
     */
    public void setSml160SYear(String sml160SYear) {
        sml160SYear__ = sml160SYear;
    }
    /**
     * <p>sml160SMonth を取得します。
     * @return sml160SMonth
     */
    public String getSml160SMonth() {
        return sml160SMonth__;
    }
    /**
     * <p>sml160SMonth をセットします。
     * @param sml160SMonth sml160SMonth
     */
    public void setSml160SMonth(String sml160SMonth) {
        sml160SMonth__ = sml160SMonth;
    }
    /**
     * <p>sml160WYear を取得します。
     * @return sml160WYear
     */
    public String getSml160WYear() {
        return sml160WYear__;
    }
    /**
     * <p>sml160WYear をセットします。
     * @param sml160WYear sml160WYear
     */
    public void setSml160WYear(String sml160WYear) {
        sml160WYear__ = sml160WYear;
    }
    /**
     * <p>sml160WMonth を取得します。
     * @return sml160WMonth
     */
    public String getSml160WMonth() {
        return sml160WMonth__;
    }
    /**
     * <p>sml160WMonth をセットします。
     * @param sml160WMonth sml160WMonth
     */
    public void setSml160WMonth(String sml160WMonth) {
        sml160WMonth__ = sml160WMonth;
    }
    /**
     * <p>sml160DYear を取得します。
     * @return sml160DYear
     */
    public String getSml160DYear() {
        return sml160DYear__;
    }
    /**
     * <p>sml160DYear をセットします。
     * @param sml160DYear sml160DYear
     */
    public void setSml160DYear(String sml160DYear) {
        sml160DYear__ = sml160DYear;
    }
    /**
     * <p>sml160DMonth を取得します。
     * @return sml160DMonth
     */
    public String getSml160DMonth() {
        return sml160DMonth__;
    }
    /**
     * <p>sml160DMonth をセットします。
     * @param sml160DMonth sml160DMonth
     */
    public void setSml160DMonth(String sml160DMonth) {
        sml160DMonth__ = sml160DMonth;
    }
    /**
     * <p>sml160SelKbn を取得します。
     * @return sml160SelKbn
     */
    public int getSml160SelKbn() {
        return sml160SelKbn__;
    }
    /**
     * <p>sml160SelKbn をセットします。
     * @param sml160SelKbn sml160SelKbn
     */
    public void setSml160SelKbn(int sml160SelKbn) {
        sml160SelKbn__ = sml160SelKbn;
    }
    /**
     * <p>sml160AccountSid を取得します。
     * @return sml160AccountSid
     */
    public int getSml160AccountSid() {
        return sml160AccountSid__;
    }
    /**
     * <p>sml160AccountSid をセットします。
     * @param sml160AccountSid sml160AccountSid
     */
    public void setSml160AccountSid(int sml160AccountSid) {
        sml160AccountSid__ = sml160AccountSid;
    }
    /**
     * <p>sml160AccountName を取得します。
     * @return sml160AccountName
     */
    public String getSml160AccountName() {
        return sml160AccountName__;
    }
    /**
     * <p>sml160AccountName をセットします。
     * @param sml160AccountName sml160AccountName
     */
    public void setSml160AccountName(String sml160AccountName) {
        sml160AccountName__ = sml160AccountName;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSml160(
            Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(req);

        //ユーザチェック
        errors = usrSelectValidata(con, req);

        //削除区分が全て削除しないの時
        if (NullDefault.getInt(sml160JdelKbn__, 3) == GSConst.MANUAL_DEL_NO
                && NullDefault.getInt(sml160SdelKbn__, 3) == GSConst.MANUAL_DEL_NO
                && NullDefault.getInt(sml160WdelKbn__, 3) == GSConst.MANUAL_DEL_NO
                && NullDefault.getInt(sml160DdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {

            ActionMessage msg =  new ActionMessage("error.input.selectoen.check",
                    gsMsg.getMessage("cmn.cmn310.06"));
            String eprefix = "allNotDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        boolean bYearError = false;
        boolean bMonthError = false;
        //受信
        if (NullDefault.getInt(sml160JdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml160JYear__, 13);
            int targetMonth = NullDefault.getInt(sml160JMonth__, 12);
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
        } else if (NullDefault.getInt(sml160JdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.57"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlJDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //送信
        if (NullDefault.getInt(sml160SdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml160SYear__, 13);
            int targetMonth = NullDefault.getInt(sml160SMonth__, 12);
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
        } else if (NullDefault.getInt(sml160SdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.59"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlSDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        //草稿
        if (NullDefault.getInt(sml160WdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml160WYear__, 13);
            int targetMonth = NullDefault.getInt(sml160WMonth__, 12);
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
        } else if (NullDefault.getInt(sml160WdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("sml.58"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "smlWDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //ゴミ箱
        if (NullDefault.getInt(sml160DdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(sml160DYear__, 13);
            int targetMonth = NullDefault.getInt(sml160DMonth__, 12);
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
        } else if (NullDefault.getInt(sml160DdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
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
     *
     * <br>[機  能]削除するユーザのチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return アクションエラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors usrSelectValidata(
            Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg;
        GsMessage gsmsg = new GsMessage(req);

        //アカウント指定の場合はアカウントチェック
        if (sml160SelKbn__ == GSConstSmail.ACCOUNT_SEL) {
            //アカウントが選択されていない場合
            if (sml160AccountSid__ <= 0) {
                msg = new ActionMessage("error.select.required.text", gsmsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "error.select.required.text");
            }

            //システムメールユーザが選択されている場合
            if (sml160AccountSid__ == GSConst.SYSTEM_USER_MAIL) {
                msg = new ActionMessage("error.can.not.select", gsmsg.getMessage("user.src.61"));
                StrutsUtil.addMessage(errors, msg, "error.input.format.file");
            }

            //選択されたアカウントが存在していない場合
            SmlAccountDao sacDao = new SmlAccountDao(con);
            SmlAccountModel sacMdl = new SmlAccountModel();
            sacMdl = sacDao.select(sml160AccountSid__);
            if (sacMdl == null) {
                msg = new ActionMessage("search.data.notfound", gsmsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "search.data.notfound");
            }
        }

        return errors;
    }

}