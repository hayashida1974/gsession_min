package jp.groupsession.v2.bbs.bbs120;

import java.util.List;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs110.Bbs110ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 自動データ削除設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs120ParamModel extends Bbs110ParamModel {
    /** 自動削除フラグ */
    private int bbs120AtdelFlg__ = GSConstBulletin.AUTO_DELETE_OFF;
    /** 経過年 */
    private int bbs120AtdelYear__ = -1;
    /** 経過月 */
    private int bbs120AtdelMonth__ = -1;
    /** 経過年ラベル */
    private List < LabelValueBean > bbs120AtdelYearLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > bbs120AtdelMonthLabel__ = null;

    /** バッチ処理実行時間 */
    private String batchTime__ = "";
    /**
     * <p>batchTime を取得します。
     * @return batchTime
     */
    public String getBatchTime() {
        return batchTime__;
    }

    /**
     * <p>batchTime をセットします。
     * @param batchTime batchTime
     */
    public void setBatchTime(String batchTime) {
        batchTime__ = batchTime;
    }

    /**
     * <p>bbs120AtdelFlg を取得します。
     * @return bbs120AtdelFlg
     */
    public int getBbs120AtdelFlg() {
        return bbs120AtdelFlg__;
    }

    /**
     * <p>bbs120AtdelFlg をセットします。
     * @param bbs120AtdelFlg bbs120AtdelFlg
     */
    public void setBbs120AtdelFlg(int bbs120AtdelFlg) {
        bbs120AtdelFlg__ = bbs120AtdelFlg;
    }

    /**
     * <p>bbs120AtdelMonth を取得します。
     * @return bbs120AtdelMonth
     */
    public int getBbs120AtdelMonth() {
        return bbs120AtdelMonth__;
    }

    /**
     * <p>bbs120AtdelMonth をセットします。
     * @param bbs120AtdelMonth bbs120AtdelMonth
     */
    public void setBbs120AtdelMonth(int bbs120AtdelMonth) {
        bbs120AtdelMonth__ = bbs120AtdelMonth;
    }

    /**
     * <p>bbs120AtdelMonthLabel を取得します。
     * @return bbs120AtdelMonthLabel
     */
    public List<LabelValueBean> getBbs120AtdelMonthLabel() {
        return bbs120AtdelMonthLabel__;
    }

    /**
     * <p>bbs120AtdelMonthLabel をセットします。
     * @param bbs120AtdelMonthLabel bbs120AtdelMonthLabel
     */
    public void setBbs120AtdelMonthLabel(List<LabelValueBean> bbs120AtdelMonthLabel) {
        bbs120AtdelMonthLabel__ = bbs120AtdelMonthLabel;
    }

    /**
     * <p>bbs120AtdelYear を取得します。
     * @return bbs120AtdelYear
     */
    public int getBbs120AtdelYear() {
        return bbs120AtdelYear__;
    }

    /**
     * <p>bbs120AtdelYear をセットします。
     * @param bbs120AtdelYear bbs120AtdelYear
     */
    public void setBbs120AtdelYear(int bbs120AtdelYear) {
        bbs120AtdelYear__ = bbs120AtdelYear;
    }

    /**
     * <p>bbs120AtdelYearLabel を取得します。
     * @return bbs120AtdelYearLabel
     */
    public List<LabelValueBean> getBbs120AtdelYearLabel() {
        return bbs120AtdelYearLabel__;
    }

    /**
     * <p>bbs120AtdelYearLabel をセットします。
     * @param bbs120AtdelYearLabel bbs120AtdelYearLabel
     */
    public void setBbs120AtdelYearLabel(List<LabelValueBean> bbs120AtdelYearLabel) {
        bbs120AtdelYearLabel__ = bbs120AtdelYearLabel;
    }
}