package jp.groupsession.v2.sml.sml160;

import java.util.ArrayList;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml100.Sml100ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 手動削除画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml160ParamModel extends Sml100ParamModel {

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
}