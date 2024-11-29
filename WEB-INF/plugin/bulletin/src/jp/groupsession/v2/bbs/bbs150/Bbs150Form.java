package jp.groupsession.v2.bbs.bbs150;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.bbs110.Bbs110Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 手動データ削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs150Form extends Bbs110Form {

    /** 年 */
    private int bbs150Year__;
    /** 月 */
    private int bbs150Month__;
    /** 年リスト */
    private ArrayList<LabelValueBean> bbs150YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> bbs150MonthLabelList__ = null;
    /** 初期表示フラグ */
    private boolean bbs150InitDsp__ = true;

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

        //経過年
        boolean yFlg = false;
        for (int iy : GSConst.DEL_YEAR_DATE) {
            if (bbs150Year__ == iy) {
                yFlg = true;
                break;
            }
        }
        //経過月
        boolean mFlg = false;
        for (int im : GSConst.DEL_MONTH_DATE) {
            if (bbs150Month__ == im) {
                mFlg = true;
                break;
            }
        }
        if (!yFlg) {
            msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.bulletin"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.year"));
            String eprefix = "bbsYear";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        if (!mFlg) {
            msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.bulletin"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.month"));
            String eprefix = "bbsMonth";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //経過年、月
        if (yFlg && mFlg) {
            if (bbs150Year__ == 0 && bbs150Month__ == 0) {
                msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cmn.bulletin"),
                        gsMsg.getMessage("cmn.manual.delete2"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "bbsLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        }
        return errors;
    }

    /**
     * <p>bbs150Year を取得します。
     * @return bbs150Year
     */
    public int getBbs150Year() {
        return bbs150Year__;
    }
    /**
     * <p>bbs150Year をセットします。
     * @param bbs150Year bbs150Year
     */
    public void setBbs150Year(int bbs150Year) {
        bbs150Year__ = bbs150Year;
    }
    /**
     * <p>bbs150Month を取得します。
     * @return bbs150Month
     */
    public int getBbs150Month() {
        return bbs150Month__;
    }
    /**
     * <p>bbs150Month をセットします。
     * @param bbs150Month bbs150Month
     */
    public void setBbs150Month(int bbs150Month) {
        bbs150Month__ = bbs150Month;
    }
    /**
     * <p>bbs150YearLabelList を取得します。
     * @return bbs150YearLabelList
     */
    public ArrayList<LabelValueBean> getBbs150YearLabelList() {
        return bbs150YearLabelList__;
    }
    /**
     * <p>bbs150YearLabelList をセットします。
     * @param bbs150YearLabelList bbs150YearLabelList
     */
    public void setBbs150YearLabelList(ArrayList<LabelValueBean> bbs150YearLabelList) {
        bbs150YearLabelList__ = bbs150YearLabelList;
    }
    /**
     * <p>bbs150MonthLabelList を取得します。
     * @return bbs150MonthLabelList
     */
    public ArrayList<LabelValueBean> getBbs150MonthLabelList() {
        return bbs150MonthLabelList__;
    }
    /**
     * <p>bbs150MonthLabelList をセットします。
     * @param bbs150MonthLabelList bbs150MonthLabelList
     */
    public void setBbs150MonthLabelList(
            ArrayList<LabelValueBean> bbs150MonthLabelList) {
        bbs150MonthLabelList__ = bbs150MonthLabelList;
    }
    /**
     * <p>bbs150InitDsp を取得します。
     * @return bbs150InitDsp
     */
    public boolean isBBs150InitDsp() {
        return bbs150InitDsp__;
    }
    /**
     * <p>bbs150InitDsp をセットします。
     * @param bbs150InitDsp bbs150InitDsp
     */
    public void setBbs150InitDsp(boolean bbs150InitDsp) {
        bbs150InitDsp__ = bbs150InitDsp;
    }
}
