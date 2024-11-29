package jp.groupsession.v2.sml.sml150;

import java.util.ArrayList;

import jp.groupsession.v2.sml.sml100.Sml100ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 自動削除設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml150ParamModel extends Sml100ParamModel {
    /** 自動削除区分 */
    private String sml150DelKbn__ = null;
    /** 受信メール 処理区分 */
    private String sml150JdelKbn__ = null;
    /** 送信メール 処理区分 */
    private String sml150SdelKbn__ = null;
    /** 草稿メール 処理区分 */
    private String sml150WdelKbn__ = null;
    /** ゴミ箱メール 処理区分 */
    private String sml150DdelKbn__ = null;
    /** 年リスト */
    private ArrayList<LabelValueBean> sml150YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> sml150MonthLabelList__ = null;
    /** 受信メール 年選択 */
    private String sml150JYear__ = null;
    /** 受信メール 月選択 */
    private String sml150JMonth__ = null;
    /** 送信メール 年選択 */
    private String sml150SYear__ = null;
    /** 送信メール 月選択 */
    private String sml150SMonth__ = null;
    /** 草稿メール 年選択 */
    private String sml150WYear__ = null;
    /** 草稿メール 月選択 */
    private String sml150WMonth__ = null;
    /** ゴミ箱メール 年選択 */
    private String sml150DYear__ = null;
    /** ゴミ箱メール 月選択 */
    private String sml150DMonth__ = null;
    /** バッチ処理時間 */
    private String sml150BatchTime__ = null;

    /**
     * <p>sml150DelKbn を取得します。
     * @return sml150DelKbn
     */
    public String getSml150DelKbn() {
        return sml150DelKbn__;
    }
    /**
     * <p>sml150DelKbn をセットします。
     * @param sml150DelKbn sml150DelKbn
     */
    public void setSml150DelKbn(String sml150DelKbn) {
        sml150DelKbn__ = sml150DelKbn;
    }
    /**
     * <p>sml150JdelKbn を取得します。
     * @return sml150JdelKbn
     */
    public String getSml150JdelKbn() {
        return sml150JdelKbn__;
    }
    /**
     * <p>sml150JdelKbn をセットします。
     * @param sml150JdelKbn sml150JdelKbn
     */
    public void setSml150JdelKbn(String sml150JdelKbn) {
        sml150JdelKbn__ = sml150JdelKbn;
    }
    /**
     * <p>sml150SdelKbn を取得します。
     * @return sml150SdelKbn
     */
    public String getSml150SdelKbn() {
        return sml150SdelKbn__;
    }
    /**
     * <p>sml150SdelKbn をセットします。
     * @param sml150SdelKbn sml150SdelKbn
     */
    public void setSml150SdelKbn(String sml150SdelKbn) {
        sml150SdelKbn__ = sml150SdelKbn;
    }
    /**
     * <p>sml150WdelKbn を取得します。
     * @return sml150WdelKbn
     */
    public String getSml150WdelKbn() {
        return sml150WdelKbn__;
    }
    /**
     * <p>sml150WdelKbn をセットします。
     * @param sml150WdelKbn sml150WdelKbn
     */
    public void setSml150WdelKbn(String sml150WdelKbn) {
        sml150WdelKbn__ = sml150WdelKbn;
    }
    /**
     * <p>sml150DdelKbn を取得します。
     * @return sml150DdelKbn
     */
    public String getSml150DdelKbn() {
        return sml150DdelKbn__;
    }
    /**
     * <p>sml150DdelKbn をセットします。
     * @param sml150DdelKbn sml150DdelKbn
     */
    public void setSml150DdelKbn(String sml150DdelKbn) {
        sml150DdelKbn__ = sml150DdelKbn;
    }
    /**
     * <p>sml150YearLabelList を取得します。
     * @return sml150YearLabelList
     */
    public ArrayList<LabelValueBean> getSml150YearLabelList() {
        return sml150YearLabelList__;
    }
    /**
     * <p>sml150YearLabelList をセットします。
     * @param sml150YearLabelList sml150YearLabelList
     */
    public void setSml150YearLabelList(ArrayList<LabelValueBean> sml150YearLabelList) {
        sml150YearLabelList__ = sml150YearLabelList;
    }
    /**
     * <p>sml150MonthLabelList を取得します。
     * @return sml150MonthLabelList
     */
    public ArrayList<LabelValueBean> getSml150MonthLabelList() {
        return sml150MonthLabelList__;
    }
    /**
     * <p>sml150MonthLabelList をセットします。
     * @param sml150MonthLabelList sml150MonthLabelList
     */
    public void setSml150MonthLabelList(
            ArrayList<LabelValueBean> sml150MonthLabelList) {
        sml150MonthLabelList__ = sml150MonthLabelList;
    }
    /**
     * <p>sml150JYear を取得します。
     * @return sml150JYear
     */
    public String getSml150JYear() {
        return sml150JYear__;
    }
    /**
     * <p>sml150JYear をセットします。
     * @param sml150JYear sml150JYear
     */
    public void setSml150JYear(String sml150JYear) {
        sml150JYear__ = sml150JYear;
    }
    /**
     * <p>sml150JMonth を取得します。
     * @return sml150JMonth
     */
    public String getSml150JMonth() {
        return sml150JMonth__;
    }
    /**
     * <p>sml150JMonth をセットします。
     * @param sml150JMonth sml150JMonth
     */
    public void setSml150JMonth(String sml150JMonth) {
        sml150JMonth__ = sml150JMonth;
    }
    /**
     * <p>sml150SYear を取得します。
     * @return sml150SYear
     */
    public String getSml150SYear() {
        return sml150SYear__;
    }
    /**
     * <p>sml150SYear をセットします。
     * @param sml150SYear sml150SYear
     */
    public void setSml150SYear(String sml150SYear) {
        sml150SYear__ = sml150SYear;
    }
    /**
     * <p>sml150SMonth を取得します。
     * @return sml150SMonth
     */
    public String getSml150SMonth() {
        return sml150SMonth__;
    }
    /**
     * <p>sml150SMonth をセットします。
     * @param sml150SMonth sml150SMonth
     */
    public void setSml150SMonth(String sml150SMonth) {
        sml150SMonth__ = sml150SMonth;
    }
    /**
     * <p>sml150WYear を取得します。
     * @return sml150WYear
     */
    public String getSml150WYear() {
        return sml150WYear__;
    }
    /**
     * <p>sml150WYear をセットします。
     * @param sml150WYear sml150WYear
     */
    public void setSml150WYear(String sml150WYear) {
        sml150WYear__ = sml150WYear;
    }
    /**
     * <p>sml150WMonth を取得します。
     * @return sml150WMonth
     */
    public String getSml150WMonth() {
        return sml150WMonth__;
    }
    /**
     * <p>sml150WMonth をセットします。
     * @param sml150WMonth sml150WMonth
     */
    public void setSml150WMonth(String sml150WMonth) {
        sml150WMonth__ = sml150WMonth;
    }
    /**
     * <p>sml150DYear を取得します。
     * @return sml150DYear
     */
    public String getSml150DYear() {
        return sml150DYear__;
    }
    /**
     * <p>sml150DYear をセットします。
     * @param sml150DYear sml150DYear
     */
    public void setSml150DYear(String sml150DYear) {
        sml150DYear__ = sml150DYear;
    }
    /**
     * <p>sml150DMonth を取得します。
     * @return sml150DMonth
     */
    public String getSml150DMonth() {
        return sml150DMonth__;
    }
    /**
     * <p>sml150DMonth をセットします。
     * @param sml150DMonth sml150DMonth
     */
    public void setSml150DMonth(String sml150DMonth) {
        sml150DMonth__ = sml150DMonth;
    }
    /**
     * <p>sml150BatchTime を取得します。
     * @return sml150BatchTime
     */
    public String getSml150BatchTime() {
        return sml150BatchTime__;
    }
    /**
     * <p>sml150BatchTime をセットします。
     * @param sml150BatchTime sml150BatchTime
     */
    public void setSml150BatchTime(String sml150BatchTime) {
        sml150BatchTime__ = sml150BatchTime;
    }
}