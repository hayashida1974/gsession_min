package jp.groupsession.v2.sml.sml130;

import java.util.ArrayList;

import jp.groupsession.v2.sml.sml120.Sml120Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 個人設定 自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml130Form extends Sml120Form {

    //自動削除区分
    /** 受信タブ 処理区分 */
    private String sml130JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String sml130SdelKbn__ = null;
    /** 草稿タブ 処理区分 */
    private String sml130WdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String sml130DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> sml130YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> sml130MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String sml130JYear__ = null;
    /** 受信タブ 月選択 */
    private String sml130JMonth__ = null;
    /** 送信タブ 年選択 */
    private String sml130SYear__ = null;
    /** 送信タブ 月選択 */
    private String sml130SMonth__ = null;
    /** 草稿タブ 年選択 */
    private String sml130WYear__ = null;
    /** 草稿タブ 月選択 */
    private String sml130WMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String sml130DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String sml130DMonth__ = null;

    /** バッチ処理時間 */
    private String sml130BatchTime__ = null;

    /**
     * <p>sml130JdelKbn を取得します。
     * @return sml130JdelKbn
     */
    public String getSml130JdelKbn() {
        return sml130JdelKbn__;
    }
    /**
     * <p>sml130JdelKbn をセットします。
     * @param sml130JdelKbn sml130JdelKbn
     */
    public void setSml130JdelKbn(String sml130JdelKbn) {
        sml130JdelKbn__ = sml130JdelKbn;
    }
    /**
     * <p>sml130SdelKbn を取得します。
     * @return sml130SdelKbn
     */
    public String getSml130SdelKbn() {
        return sml130SdelKbn__;
    }
    /**
     * <p>sml130SdelKbn をセットします。
     * @param sml130SdelKbn sml130SdelKbn
     */
    public void setSml130SdelKbn(String sml130SdelKbn) {
        sml130SdelKbn__ = sml130SdelKbn;
    }
    /**
     * <p>sml130WdelKbn を取得します。
     * @return sml130WdelKbn
     */
    public String getSml130WdelKbn() {
        return sml130WdelKbn__;
    }
    /**
     * <p>sml130WdelKbn をセットします。
     * @param sml130WdelKbn sml130WdelKbn
     */
    public void setSml130WdelKbn(String sml130WdelKbn) {
        sml130WdelKbn__ = sml130WdelKbn;
    }
    /**
     * <p>sml130DdelKbn を取得します。
     * @return sml130DdelKbn
     */
    public String getSml130DdelKbn() {
        return sml130DdelKbn__;
    }
    /**
     * <p>sml130DdelKbn をセットします。
     * @param sml130DdelKbn sml130DdelKbn
     */
    public void setSml130DdelKbn(String sml130DdelKbn) {
        sml130DdelKbn__ = sml130DdelKbn;
    }
    /**
     * <p>sml130YearLabelList を取得します。
     * @return sml130YearLabelList
     */
    public ArrayList<LabelValueBean> getSml130YearLabelList() {
        return sml130YearLabelList__;
    }
    /**
     * <p>sml130YearLabelList をセットします。
     * @param sml130YearLabelList sml130YearLabelList
     */
    public void setSml130YearLabelList(ArrayList<LabelValueBean> sml130YearLabelList) {
        sml130YearLabelList__ = sml130YearLabelList;
    }
    /**
     * <p>sml130MonthLabelList を取得します。
     * @return sml130MonthLabelList
     */
    public ArrayList<LabelValueBean> getSml130MonthLabelList() {
        return sml130MonthLabelList__;
    }
    /**
     * <p>sml130MonthLabelList をセットします。
     * @param sml130MonthLabelList sml130MonthLabelList
     */
    public void setSml130MonthLabelList(
            ArrayList<LabelValueBean> sml130MonthLabelList) {
        sml130MonthLabelList__ = sml130MonthLabelList;
    }
    /**
     * <p>sml130JYear を取得します。
     * @return sml130JYear
     */
    public String getSml130JYear() {
        return sml130JYear__;
    }
    /**
     * <p>sml130JYear をセットします。
     * @param sml130JYear sml130JYear
     */
    public void setSml130JYear(String sml130JYear) {
        sml130JYear__ = sml130JYear;
    }
    /**
     * <p>sml130JMonth を取得します。
     * @return sml130JMonth
     */
    public String getSml130JMonth() {
        return sml130JMonth__;
    }
    /**
     * <p>sml130JMonth をセットします。
     * @param sml130JMonth sml130JMonth
     */
    public void setSml130JMonth(String sml130JMonth) {
        sml130JMonth__ = sml130JMonth;
    }
    /**
     * <p>sml130SYear を取得します。
     * @return sml130SYear
     */
    public String getSml130SYear() {
        return sml130SYear__;
    }
    /**
     * <p>sml130SYear をセットします。
     * @param sml130SYear sml130SYear
     */
    public void setSml130SYear(String sml130SYear) {
        sml130SYear__ = sml130SYear;
    }
    /**
     * <p>sml130SMonth を取得します。
     * @return sml130SMonth
     */
    public String getSml130SMonth() {
        return sml130SMonth__;
    }
    /**
     * <p>sml130SMonth をセットします。
     * @param sml130SMonth sml130SMonth
     */
    public void setSml130SMonth(String sml130SMonth) {
        sml130SMonth__ = sml130SMonth;
    }
    /**
     * <p>sml130WYear を取得します。
     * @return sml130WYear
     */
    public String getSml130WYear() {
        return sml130WYear__;
    }
    /**
     * <p>sml130WYear をセットします。
     * @param sml130WYear sml130WYear
     */
    public void setSml130WYear(String sml130WYear) {
        sml130WYear__ = sml130WYear;
    }
    /**
     * <p>sml130WMonth を取得します。
     * @return sml130WMonth
     */
    public String getSml130WMonth() {
        return sml130WMonth__;
    }
    /**
     * <p>sml130WMonth をセットします。
     * @param sml130WMonth sml130WMonth
     */
    public void setSml130WMonth(String sml130WMonth) {
        sml130WMonth__ = sml130WMonth;
    }
    /**
     * <p>sml130DYear を取得します。
     * @return sml130DYear
     */
    public String getSml130DYear() {
        return sml130DYear__;
    }
    /**
     * <p>sml130DYear をセットします。
     * @param sml130DYear sml130DYear
     */
    public void setSml130DYear(String sml130DYear) {
        sml130DYear__ = sml130DYear;
    }
    /**
     * <p>sml130DMonth を取得します。
     * @return sml130DMonth
     */
    public String getSml130DMonth() {
        return sml130DMonth__;
    }
    /**
     * <p>sml130DMonth をセットします。
     * @param sml130DMonth sml130DMonth
     */
    public void setSml130DMonth(String sml130DMonth) {
        sml130DMonth__ = sml130DMonth;
    }
    /**
     * <p>sml130BatchTime を取得します。
     * @return sml130BatchTime
     */
    public String getSml130BatchTime() {
        return sml130BatchTime__;
    }
    /**
     * <p>sml130BatchTime をセットします。
     * @param sml130BatchTime sml130BatchTime
     */
    public void setSml130BatchTime(String sml130BatchTime) {
        sml130BatchTime__ = sml130BatchTime;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return エラー
     */
    public ActionErrors validateCheck() {

        ActionErrors errors = new ActionErrors();

//        //受信タブ 自動削除設定の確認
//        int jdelKbn = NullDefault.getInt(sml130JdelKbn__, GSConstSmail.SML_AUTO_DEL_NO);
//        if (jdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//
//            int jyear = NullDefault.getInt(sml130JYear__, 0);
//            int jmonth = NullDefault.getInt(sml130JMonth__, 0);
//
//            if (jyear == 0 && jmonth == 0) {
//                msg =
//                    new ActionMessage(
//                            "error.input.range0over.data",
//                            "自動削除 受信タブ 経過年月",
//                            "1ヶ月");
//
//                StrutsUtil.addMessage(
//                        errors, msg, "jdel.error.input.range0over.data");
//            }
//        }
//
//        //送信タブ 自動削除設定の確認
//        int sdelKbn = NullDefault.getInt(sml130SdelKbn__, GSConstSmail.SML_AUTO_DEL_NO);
//        if (sdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//
//            int syear = NullDefault.getInt(sml130SYear__, 0);
//            int smonth = NullDefault.getInt(sml130SMonth__, 0);
//
//            if (syear == 0 && smonth == 0) {
//                msg =
//                    new ActionMessage(
//                            "error.input.range0over.data",
//                            "自動削除 送信タブ 経過年月",
//                            "1ヶ月");
//
//                StrutsUtil.addMessage(
//                        errors, msg, "sdel.error.input.range0over.data");
//            }
//        }
//
//        //草稿タブ 自動削除設定の確認
//        int wdelKbn = NullDefault.getInt(sml130WdelKbn__, GSConstSmail.SML_AUTO_DEL_NO);
//        if (wdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//
//            int wyear = NullDefault.getInt(sml130WYear__, 0);
//            int wmonth = NullDefault.getInt(sml130WMonth__, 0);
//
//            if (wyear == 0 && wmonth == 0) {
//                msg =
//                    new ActionMessage(
//                            "error.input.range0over.data",
//                            "自動削除 草稿タブ 経過年月",
//                            "1ヶ月");
//
//                StrutsUtil.addMessage(
//                        errors, msg, "wdel.error.input.range0over.data");
//            }
//        }
//
//        //ゴミ箱タブ 自動削除設定の確認
//        int ddelKbn = NullDefault.getInt(sml130DdelKbn__, GSConstSmail.SML_AUTO_DEL_NO);
//        if (ddelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//
//            int dyear = NullDefault.getInt(sml130DYear__, 0);
//            int dmonth = NullDefault.getInt(sml130DMonth__, 0);
//
//            if (dyear == 0 && dmonth == 0) {
//                msg =
//                    new ActionMessage(
//                            "error.input.range0over.data",
//                            "自動削除 ゴミ箱タブ 経過年月",
//                            "1ヶ月");
//
//                StrutsUtil.addMessage(
//                        errors, msg, "ddel.error.input.range0over.data");
//            }
//        }

        return errors;
    }
}