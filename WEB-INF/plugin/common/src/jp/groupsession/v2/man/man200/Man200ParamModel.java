package jp.groupsession.v2.man.man200;

import java.util.ArrayList;

import jp.groupsession.v2.man.AbstractMainParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 パスワードルール設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man200ParamModel extends AbstractMainParamModel {
    /** 英数混在区分 */
    private int man200CoeKbn__ = 0;
    /** 大文字小文字混在区分 */
    private int man200UppercaseKbn__ = 0;
    /** 同一パスワード期限区分 */
    private int man200LimitKbn__ = 0;
    /** ユーザID同一パスワード設定区分 */
    private int man200UidPswdKbn__ = 0;
    /** 旧パスワード設定区分 */
    private int man200OldPswdKbn__ = 0;
    /** 同一パスワード期限(日) */
    private String man200LimitDay__ = null;
    /** 年 */
    private int man200Digit__;
    /** 桁数リスト */
    private ArrayList<LabelValueBean> man200DigitLabelList__ = null;
    /**
     * <p>man200CoeKbn を取得します。
     * @return man200CoeKbn
     */
    public int getMan200CoeKbn() {
        return man200CoeKbn__;
    }
    /**
     * <p>man200CoeKbn をセットします。
     * @param man200CoeKbn man200CoeKbn
     */
    public void setMan200CoeKbn(int man200CoeKbn) {
        man200CoeKbn__ = man200CoeKbn;
    }
    /**
     * <p>man200UppercaseKbn を取得します。
     * @return man200UppercaseKbn
     */
    public int getMan200UppercaseKbn() {
        return man200UppercaseKbn__;
    }
    /**
     * <p>man200UppercaseKbn をセットします。
     * @param man200UppercaseKbn man200UppercaseKbn
     */
    public void setMan200UppercaseKbn(int man200UppercaseKbn) {
        man200UppercaseKbn__ = man200UppercaseKbn;
    }
    /**
     * <p>man200Digit を取得します。
     * @return man200Digit
     */
    public int getMan200Digit() {
        return man200Digit__;
    }
    /**
     * <p>man200Digit をセットします。
     * @param man200Digit man200Digit
     */
    public void setMan200Digit(int man200Digit) {
        man200Digit__ = man200Digit;
    }
    /**
     * <p>man200DigitLabelList を取得します。
     * @return man200DigitLabelList
     */
    public ArrayList<LabelValueBean> getMan200DigitLabelList() {
        return man200DigitLabelList__;
    }
    /**
     * <p>man200DigitLabelList をセットします。
     * @param man200DigitLabelList man200DigitLabelList
     */
    public void setMan200DigitLabelList(
            ArrayList<LabelValueBean> man200DigitLabelList) {
        man200DigitLabelList__ = man200DigitLabelList;
    }
    /**
     * <p>man200LimitKbn を取得します。
     * @return man200LimitKbn
     */
    public int getMan200LimitKbn() {
        return man200LimitKbn__;
    }
    /**
     * <p>man200LimitKbn をセットします。
     * @param man200LimitKbn man200LimitKbn
     */
    public void setMan200LimitKbn(int man200LimitKbn) {
        man200LimitKbn__ = man200LimitKbn;
    }
    /**
     * <p>man200OldPswdKbn を取得します。
     * @return man200OldPswdKbn
     */
    public int getMan200OldPswdKbn() {
        return man200OldPswdKbn__;
    }
    /**
     * <p>man200OldPswdKbn をセットします。
     * @param man200OldPswdKbn man200OldPswdKbn
     */
    public void setMan200OldPswdKbn(int man200OldPswdKbn) {
        man200OldPswdKbn__ = man200OldPswdKbn;
    }
    /**
     * <p>man200UidPswdKbn を取得します。
     * @return man200UidPswdKbn
     */
    public int getMan200UidPswdKbn() {
        return man200UidPswdKbn__;
    }
    /**
     * <p>man200UidPswdKbn をセットします。
     * @param man200UidPswdKbn man200UidPswdKbn
     */
    public void setMan200UidPswdKbn(int man200UidPswdKbn) {
        man200UidPswdKbn__ = man200UidPswdKbn;
    }
    /**
     * <p>man200LimitDay を取得します。
     * @return man200LimitDay
     */
    public String getMan200LimitDay() {
        return man200LimitDay__;
    }
    /**
     * <p>man200LimitDay をセットします。
     * @param man200LimitDay man200LimitDay
     */
    public void setMan200LimitDay(String man200LimitDay) {
        man200LimitDay__ = man200LimitDay;
    }
}