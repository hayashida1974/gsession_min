package jp.groupsession.v2.wml.wml060;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.wml020.Wml020ParamModel;

/**
 * <br>[機  能] WEBメール 手動データ削除画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml060ParamModel extends Wml020ParamModel {
    /** ゴミ箱 手動削除区分 */
    private String wml060delKbn1__ = String.valueOf(GSConstWebmail.MANU_DEL_NO);
    /** ゴミ箱 手動削除 年 */
    private int wml060delYear1__ = GSConstWebmail.YEAR_THREE;
    /** ゴミ箱 手動削除 月 */
    private int wml060delMonth1__ = GSConstWebmail.DEL_MONTH_START;
    /** ゴミ箱 手動削除 日 */
    private int wml060delDay1__ = GSConstWebmail.DEL_DAY_START;
    /** 送信済み 手動削除区分 */
    private String wml060delKbn2__ = String.valueOf(GSConstWebmail.MANU_DEL_NO);
    /** 送信済み 手動削除 年 */
    private int wml060delYear2__ = GSConstWebmail.YEAR_THREE;
    /** 送信済み 手動削除 月 */
    private int wml060delMonth2__ = GSConstWebmail.DEL_MONTH_START;
    /** 送信済み 手動削除 日 */
    private int wml060delDay2__ = GSConstWebmail.DEL_DAY_START;
    /** 草稿 手動削除区分 */
    private String wml060delKbn3__ = String.valueOf(GSConstWebmail.MANU_DEL_NO);
    /** 草稿 手動削除 年 */
    private int wml060delYear3__ = GSConstWebmail.YEAR_THREE;
    /** 草稿 手動削除 月 */
    private int wml060delMonth3__ = GSConstWebmail.DEL_MONTH_START;
    /** 草稿 手動削除 日 */
    private int wml060delDay3__ = GSConstWebmail.DEL_DAY_START;
    /** 受信箱 手動削除区分 */
    private String wml060delKbn4__ = String.valueOf(GSConstWebmail.MANU_DEL_NO);
    /** 受信箱 手動削除 年 */
    private int wml060delYear4__ = GSConstWebmail.YEAR_THREE;
    /** 受信箱 手動削除 月 */
    private int wml060delMonth4__ = GSConstWebmail.DEL_MONTH_START;
    /** 受信箱 手動削除 日 */
    private int wml060delDay4__ = GSConstWebmail.DEL_DAY_START;
    /** 保管 手動削除区分 */
    private String wml060delKbn5__ = String.valueOf(GSConstWebmail.MANU_DEL_NO);
    /** 保管 手動削除 年 */
    private int wml060delYear5__ = GSConstWebmail.YEAR_THREE;
    /** 保管 手動削除 月 */
    private int wml060delMonth5__ = GSConstWebmail.DEL_MONTH_START;
    /** 保管 手動削除 日 */
    private int wml060delDay5__ = GSConstWebmail.DEL_DAY_START;
    /** 年コンボ */
    private List<LabelValueBean> yearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabelList__ = null;
    /** 日コンボ */
    private List<LabelValueBean> dayLabelList__ = null;
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
     * <p>wml060delKbn1 を取得します。
     * @return wml060delKbn1
     */
    public String getWml060delKbn1() {
        return wml060delKbn1__;
    }
    /**
     * <p>wml060delKbn1 をセットします。
     * @param wml060delKbn1 wml060delKbn1
     */
    public void setWml060delKbn1(String wml060delKbn1) {
        wml060delKbn1__ = wml060delKbn1;
    }
    /**
     * <p>wml060delKbn2 を取得します。
     * @return wml060delKbn2
     */
    public String getWml060delKbn2() {
        return wml060delKbn2__;
    }
    /**
     * <p>wml060delKbn2 をセットします。
     * @param wml060delKbn2 wml060delKbn2
     */
    public void setWml060delKbn2(String wml060delKbn2) {
        wml060delKbn2__ = wml060delKbn2;
    }
    /**
     * <p>wml060delKbn3 を取得します。
     * @return wml060delKbn3
     */
    public String getWml060delKbn3() {
        return wml060delKbn3__;
    }
    /**
     * <p>wml060delKbn3 をセットします。
     * @param wml060delKbn3 wml060delKbn3
     */
    public void setWml060delKbn3(String wml060delKbn3) {
        wml060delKbn3__ = wml060delKbn3;
    }
    /**
     * <p>wml060delKbn4 を取得します。
     * @return wml060delKbn4
     */
    public String getWml060delKbn4() {
        return wml060delKbn4__;
    }
    /**
     * <p>wml060delKbn4 をセットします。
     * @param wml060delKbn4 wml060delKbn4
     */
    public void setWml060delKbn4(String wml060delKbn4) {
        wml060delKbn4__ = wml060delKbn4;
    }
    /**
     * <p>wml060delMonth1 を取得します。
     * @return wml060delMonth1
     */
    public int getWml060delMonth1() {
        return wml060delMonth1__;
    }
    /**
     * <p>wml060delMonth1 をセットします。
     * @param wml060delMonth1 wml060delMonth1
     */
    public void setWml060delMonth1(int wml060delMonth1) {
        wml060delMonth1__ = wml060delMonth1;
    }
    /**
     * <p>wml060delMonth2 を取得します。
     * @return wml060delMonth2
     */
    public int getWml060delMonth2() {
        return wml060delMonth2__;
    }
    /**
     * <p>wml060delMonth2 をセットします。
     * @param wml060delMonth2 wml060delMonth2
     */
    public void setWml060delMonth2(int wml060delMonth2) {
        wml060delMonth2__ = wml060delMonth2;
    }
    /**
     * <p>wml060delMonth3 を取得します。
     * @return wml060delMonth3
     */
    public int getWml060delMonth3() {
        return wml060delMonth3__;
    }
    /**
     * <p>wml060delMonth3 をセットします。
     * @param wml060delMonth3 wml060delMonth3
     */
    public void setWml060delMonth3(int wml060delMonth3) {
        wml060delMonth3__ = wml060delMonth3;
    }
    /**
     * <p>wml060delMonth4 を取得します。
     * @return wml060delMonth4
     */
    public int getWml060delMonth4() {
        return wml060delMonth4__;
    }
    /**
     * <p>wml060delMonth4 をセットします。
     * @param wml060delMonth4 wml060delMonth4
     */
    public void setWml060delMonth4(int wml060delMonth4) {
        wml060delMonth4__ = wml060delMonth4;
    }
    /**
     * <p>wml060delYear1 を取得します。
     * @return wml060delYear1
     */
    public int getWml060delYear1() {
        return wml060delYear1__;
    }
    /**
     * <p>wml060delYear1 をセットします。
     * @param wml060delYear1 wml060delYear1
     */
    public void setWml060delYear1(int wml060delYear1) {
        wml060delYear1__ = wml060delYear1;
    }
    /**
     * <p>wml060delYear2 を取得します。
     * @return wml060delYear2
     */
    public int getWml060delYear2() {
        return wml060delYear2__;
    }
    /**
     * <p>wml060delYear2 をセットします。
     * @param wml060delYear2 wml060delYear2
     */
    public void setWml060delYear2(int wml060delYear2) {
        wml060delYear2__ = wml060delYear2;
    }
    /**
     * <p>wml060delYear3 を取得します。
     * @return wml060delYear3
     */
    public int getWml060delYear3() {
        return wml060delYear3__;
    }
    /**
     * <p>wml060delYear3 をセットします。
     * @param wml060delYear3 wml060delYear3
     */
    public void setWml060delYear3(int wml060delYear3) {
        wml060delYear3__ = wml060delYear3;
    }
    /**
     * <p>wml060delYear4 を取得します。
     * @return wml060delYear4
     */
    public int getWml060delYear4() {
        return wml060delYear4__;
    }
    /**
     * <p>wml060delKbn5 を取得します。
     * @return wml060delKbn5
     */
    public String getWml060delKbn5() {
        return wml060delKbn5__;
    }
    /**
     * <p>wml060delKbn5 をセットします。
     * @param wml060delKbn5 wml060delKbn5
     */
    public void setWml060delKbn5(String wml060delKbn5) {
        wml060delKbn5__ = wml060delKbn5;
    }
    /**
     * <p>wml060delMonth5 を取得します。
     * @return wml060delMonth5
     */
    public int getWml060delMonth5() {
        return wml060delMonth5__;
    }
    /**
     * <p>wml060delMonth5 をセットします。
     * @param wml060delMonth5 wml060delMonth5
     */
    public void setWml060delMonth5(int wml060delMonth5) {
        wml060delMonth5__ = wml060delMonth5;
    }
    /**
     * <p>wml060delYear5 を取得します。
     * @return wml060delYear5
     */
    public int getWml060delYear5() {
        return wml060delYear5__;
    }
    /**
     * <p>wml060delYear5 をセットします。
     * @param wml060delYear5 wml060delYear5
     */
    public void setWml060delYear5(int wml060delYear5) {
        wml060delYear5__ = wml060delYear5;
    }
    /**
     * <p>wml060delYear4 をセットします。
     * @param wml060delYear4 wml060delYear4
     */
    public void setWml060delYear4(int wml060delYear4) {
        wml060delYear4__ = wml060delYear4;
    }
    /**
     * <p>wml060delDay1 を取得します。
     * @return wml060delDay1
     */
    public int getWml060delDay1() {
        return wml060delDay1__;
    }
    /**
     * <p>wml060delDay1 をセットします。
     * @param wml060delDay1 wml060delDay1
     */
    public void setWml060delDay1(int wml060delDay1) {
        wml060delDay1__ = wml060delDay1;
    }
    /**
     * <p>wml060delDay2 を取得します。
     * @return wml060delDay2
     */
    public int getWml060delDay2() {
        return wml060delDay2__;
    }
    /**
     * <p>wml060delDay2 をセットします。
     * @param wml060delDay2 wml060delDay2
     */
    public void setWml060delDay2(int wml060delDay2) {
        wml060delDay2__ = wml060delDay2;
    }
    /**
     * <p>wml060delDay3 を取得します。
     * @return wml060delDay3
     */
    public int getWml060delDay3() {
        return wml060delDay3__;
    }
    /**
     * <p>wml060delDay3 をセットします。
     * @param wml060delDay3 wml060delDay3
     */
    public void setWml060delDay3(int wml060delDay3) {
        wml060delDay3__ = wml060delDay3;
    }
    /**
     * <p>wml060delDay4 を取得します。
     * @return wml060delDay4
     */
    public int getWml060delDay4() {
        return wml060delDay4__;
    }
    /**
     * <p>wml060delDay4 をセットします。
     * @param wml060delDay4 wml060delDay4
     */
    public void setWml060delDay4(int wml060delDay4) {
        wml060delDay4__ = wml060delDay4;
    }
    /**
     * <p>wml060delDay5 を取得します。
     * @return wml060delDay5
     */
    public int getWml060delDay5() {
        return wml060delDay5__;
    }
    /**
     * <p>wml060delDay5 をセットします。
     * @param wml060delDay5 wml060delDay5
     */
    public void setWml060delDay5(int wml060delDay5) {
        wml060delDay5__ = wml060delDay5;
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
}