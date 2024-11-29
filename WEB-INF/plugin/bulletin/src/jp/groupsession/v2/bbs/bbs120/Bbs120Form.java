package jp.groupsession.v2.bbs.bbs120;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs110.Bbs110Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 自動データ削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs120Form extends Bbs110Form {

    /** 経過年ラベルの選択値 */
    public static final String[] YEAR_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "10"};
    /** 経過月ラベルの選択値 */
    public static final String[] MONTH_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

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
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Bbs120Form() {
    }

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

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        //フラグチェック
        if (bbs120AtdelFlg__ != GSConstBulletin.AUTO_DELETE_OFF
                && bbs120AtdelFlg__ != GSConstBulletin.AUTO_DELETE_ON) {
            msg =  new ActionMessage("error.autodel.between",
                    gsMsg.getMessage("cmn.bulletin"),
                    gsMsg.getMessage("cmn.autodelete"));
            String eprefix = "bbsautoDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        if (bbs120AtdelFlg__ == GSConstBulletin.AUTO_DELETE_ON) {
            //経過年
            boolean yFlg = false;
            for (int iy : GSConst.DEL_YEAR_DATE) {
                if (bbs120AtdelYear__ == iy) {
                    yFlg = true;
                    break;
                }
            }
            //経過月
            boolean mFlg = false;
            for (int im : GSConst.DEL_MONTH_DATE) {
                if (bbs120AtdelMonth__ == im) {
                    mFlg = true;
                    break;
                }
            }
            if (yFlg == false || mFlg == false) {
                msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("cmn.bulletin"),
                        gsMsg.getMessage("cmn.autodelete"));
                String eprefix = "bbsautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //経過年、月
            if (yFlg && mFlg) {
                //
                if (bbs120AtdelYear__ == 0 && bbs120AtdelMonth__ == 0) {
                    msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("cmn.bulletin"),
                            gsMsg.getMessage("cmn.autodelete"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "bbsautoDel";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            }
        }
        return errors;
    }
}
