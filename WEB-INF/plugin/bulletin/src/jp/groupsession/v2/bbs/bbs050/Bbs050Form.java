package jp.groupsession.v2.bbs.bbs050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs010.Bbs010Form;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 掲示板 表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs050Form extends Bbs010Form {

    /** フォーラム表示件数の選択値 */
    public static final String[] FORUMCNTVALUE
        = new String[] {"10", "20", "30", "40", "50"};
    /** スレッド表示件数の選択値 */
    public static final String[] THRECNTVALUE
        = new String[] {"10", "20", "30", "40", "50"};
    /** 投稿表示件数の選択値 */
    public static final String[] WRITECNTVALUE
        = new String[] {"10", "20", "30", "40", "50"};
    /** new表示日数の選択値 */
    public static final String[] NEWCNTVALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    /** フォーラム表示件数 */
    private int bbs050forumCnt__ = 0;
    /** スレッド表示件数 */
    private int bbs050threCnt__ = 0;
    /** 投稿表示件数 */
    private int bbs050writeCnt__ = 0;
    /** new表示日数 */
    private int bbs050newCnt__ = 0;
    /** 投稿一覧表示順 */
    private int bbs050wrtOrder__ = GSConstBulletin.BBS_WRTLIST_ORDER_ASC;
    /** 投稿者画像表示有無 */
    private int bbs050threImage__ = GSConstBulletin.BBS_IMAGE_DSP;
    /** 添付画像表示有無 */
    private int bbs050tempImage__ = GSConstBulletin.BBS_IMAGE_TEMP_DSP;

    /** フォーラム表示件数 一覧*/
    private List < LabelValueBean > bbs050forumCntLabel__ = null;
    /** スレッド表示件数 */
    private List < LabelValueBean > bbs050threCntLabel__ = null;
    /** 投稿表示件数 */
    private List < LabelValueBean > bbs050writeCntLabel__ = null;
    /** new表示日数 */
    private List < LabelValueBean > bbs050newCntLabel__ = null;

    /** [サブコンテンツ] 新着スレッド一覧 表示フラグ */
    private int bbs050threadFlg__ = GSConstBulletin.BBS_THRED_DSP;
    /** [サブコンテンツ] フォーラム一覧 表示フラグ */
    private int bbs050forumFlg__ = GSConstBulletin.BBS_FORUM_DSP;
    /** [サブコンテンツ] 未読スレッド一覧 表示フラグ */
    private int bbs050midokuTrdFlg__ = GSConstBulletin.BBS_MIDOKU_TRD_DSP;

    /** メイン画面スレッド表示件数の選択値 */
    public static final String[] THREMAINCNTVALUE
        = new String[] {"5", "10", "20", "30", "40", "50"};

    /** メイン画面スレッド表示件数 */
    private int bbs050threMainCnt__ = 0;
    /** メイ画面確認済み投稿表示有無 */
    private int bbs050mainChkedDsp__ = GSConstBulletin.BBS_CHECKED_NOT_DSP;

    /** メイン画面スレッド表示件数 */
    private List < LabelValueBean > bbs050threMainCntLabel__ = null;

    /**
     * コンストラクタ
     */
    public Bbs050Form() {
        bbs050forumCntLabel__ = new ArrayList < LabelValueBean >();
        for (String label : FORUMCNTVALUE) {
            bbs050forumCntLabel__.add(new LabelValueBean(label, label));
        }

        bbs050threCntLabel__ = new ArrayList < LabelValueBean >();
        for (String label : THRECNTVALUE) {
            bbs050threCntLabel__.add(new LabelValueBean(label, label));
        }

        bbs050writeCntLabel__ = new ArrayList < LabelValueBean >();
        for (String label : WRITECNTVALUE) {
            bbs050writeCntLabel__.add(new LabelValueBean(label, label));
        }

        bbs050threMainCntLabel__ = new ArrayList < LabelValueBean >();
        for (String label : THREMAINCNTVALUE) {
            bbs050threMainCntLabel__.add(new LabelValueBean(label, label));
        }
    }

    /**
     * <br>[機  能] 入力値のチェックを行う
     * <br>[解  説] エラーを返す
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return 値
     * @throws SQLException SQL実行時例外
     *
     */
    public ActionErrors validateInputCheck(HttpServletRequest req, Connection con)
            throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        boolean bForumDisp = false;
        //フォーラム表示件数
        for (int idx = 0; idx < FORUMCNTVALUE.length; idx++) {
            if (bbs050forumCnt__ == Integer.parseInt(FORUMCNTVALUE[idx])) {
                bForumDisp = true;
                break;
            }
        }

        if (!bForumDisp) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.2"));
            errors.add("error.select.forum.radio", msg);
        }

        boolean bTheradDisp = false;
        //スレッド表示件数
        for (int idx = 0; idx < THRECNTVALUE.length; idx++) {
            if (bbs050threCnt__ == Integer.parseInt(THRECNTVALUE[idx])) {
                bTheradDisp = true;
                break;
            }
        }
        if (!bTheradDisp) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.4"));
            errors.add("error.select.forum.radio", msg);
        }

        //投稿表示件数
        boolean bToukoDisp = false;
        for (int idx = 0; idx < WRITECNTVALUE.length; idx++) {
            if (bbs050writeCnt__ == Integer.parseInt(WRITECNTVALUE[idx])) {
                bToukoDisp = true;
                break;
            }
        }
        if (!bToukoDisp) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.6"));
            errors.add("error.select.forum.radio", msg);
        }

        //new表示日数
        boolean bNewDate = false;
        for (int idx = 0; idx < NEWCNTVALUE.length; idx++) {
            if (bbs050newCnt__ == Integer.parseInt(NEWCNTVALUE[idx])) {
                bNewDate = true;
                break;
            }
        }
        if (!bNewDate) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.8"));
            errors.add("error.select.forum.radio", msg);
        }

        //投稿一覧表示順
        if (!(bbs050wrtOrder__ == GSConstBulletin.BBS_WRTLIST_ORDER_ASC
                || bbs050wrtOrder__ == GSConstBulletin.BBS_WRTLIST_ORDER_DESC)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.17"));
            errors.add("error.select.forum.radio", msg);
        }

        //投稿者画像表示
        if (!(bbs050threImage__ == GSConstBulletin.BBS_IMAGE_DSP
                || bbs050threImage__ == GSConstBulletin.BBS_IMAGE_NOT_DSP)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.19"));
            errors.add("error.select.forum.radio", msg);
        }

        //添付画像表示
        if (!(bbs050tempImage__ == GSConstBulletin.BBS_IMAGE_TEMP_DSP
                || bbs050tempImage__ == GSConstBulletin.BBS_IMAGE_TEMP_NOT_DSP)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.26"));
            errors.add("error.select.forum.radio", msg);
        }

        //サブコンテンツ新着スレッド一覧
        if (!(bbs050threadFlg__ == GSConstBulletin.BBS_THRED_DSP
                || bbs050threadFlg__ == GSConstBulletin.BBS_THRED_NOT_DSP)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.25")
                    + gsMsg.getMessage(req, "bbs.bbs010.2"));
            errors.add("error.select.forum.radio", msg);
        }

        //サブコンテンツフォーラム一覧
        if (!(bbs050forumFlg__ == GSConstBulletin.BBS_FORUM_DSP
                || bbs050forumFlg__ == GSConstBulletin.BBS_FORUM_NOT_DSP)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.25")
                    + gsMsg.getMessage(req, "bbs.1"));
            errors.add("error.select.forum.radio", msg);
        }

        //サブコンテンツ未読スレッド一覧
        if (!(bbs050midokuTrdFlg__ == GSConstBulletin.BBS_MIDOKU_TRD_DSP
                || bbs050midokuTrdFlg__ == GSConstBulletin.BBS_MIDOKU_TRD_NOT_DSP)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.25")
                    + gsMsg.getMessage(req, "bbs.10"));
            errors.add("error.select.forum.radio", msg);
        }

        //メイン画面スレッド表示件数
        boolean bDspCnt = false;
        for (int idx = 0; idx < THREMAINCNTVALUE.length; idx++) {
            if (bbs050threMainCnt__ == Integer.parseInt(THREMAINCNTVALUE[idx])) {
                bDspCnt = true;
                break;
            }
        }
        if (!bDspCnt) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.13"));
            errors.add("error.select.forum.radio", msg);
        }

        //メイン画面の既読投稿表示
        if (!(bbs050mainChkedDsp__ == GSConstBulletin.BBS_CHECKED_NOT_DSP
                || bbs050mainChkedDsp__ == GSConstBulletin.BBS_CHECKED_DSP)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.bbs050.15"));
            errors.add("error.select.forum.radio", msg);
        }
        return errors;
    }

    /**
     * <p>bbs050threImage を取得します。
     * @return bbs050threImage
     */
    public int getBbs050threImage() {
        return bbs050threImage__;
    }

    /**
     * <p>bbs050threImage をセットします。
     * @param bbs050threImage bbs050threImage
     */
    public void setBbs050threImage(int bbs050threImage) {
        bbs050threImage__ = bbs050threImage;
    }

    /**
     * <p>bbs050tempImage を取得します。
     * @return bbs050tempImage
     */
    public int getBbs050tempImage() {
        return bbs050tempImage__;
    }

    /**
     * <p>bbs050tempImage をセットします。
     * @param bbs050tempImage bbs050tempImage
     */
    public void setBbs050tempImage(int bbs050tempImage) {
        bbs050tempImage__ = bbs050tempImage;
    }

    /**
     * @return bbs050forumCnt を戻します。
     */
    public int getBbs050forumCnt() {
        return bbs050forumCnt__;
    }
    /**
     * @param bbs050forumCnt 設定する bbs050forumCnt。
     */
    public void setBbs050forumCnt(int bbs050forumCnt) {
        bbs050forumCnt__ = bbs050forumCnt;
    }
    /**
     * @return bbs050forumCntLabel を戻します。
     */
    public List < LabelValueBean > getBbs050forumCntLabel() {
        return bbs050forumCntLabel__;
    }
    /**
     * @param bbs050forumCntLabel 設定する bbs050forumCntLabel。
     */
    public void setBbs050forumCntLabel(List < LabelValueBean > bbs050forumCntLabel) {
        bbs050forumCntLabel__ = bbs050forumCntLabel;
    }
    /**
     * @return bbs050newCnt を戻します。
     */
    public int getBbs050newCnt() {
        return bbs050newCnt__;
    }
    /**
     * @param bbs050newCnt 設定する bbs050newCnt。
     */
    public void setBbs050newCnt(int bbs050newCnt) {
        bbs050newCnt__ = bbs050newCnt;
    }
    /**
     * @return bbs050newCntLabel を戻します。
     */
    public List < LabelValueBean > getBbs050newCntLabel() {
        return bbs050newCntLabel__;
    }
    /**
     * @param bbs050newCntLabel 設定する bbs050newCntLabel。
     */
    public void setBbs050newCntLabel(List < LabelValueBean > bbs050newCntLabel) {
        bbs050newCntLabel__ = bbs050newCntLabel;
    }
    /**
     * @return bbs050threCnt を戻します。
     */
    public int getBbs050threCnt() {
        return bbs050threCnt__;
    }
    /**
     * @param bbs050threCnt 設定する bbs050threCnt。
     */
    public void setBbs050threCnt(int bbs050threCnt) {
        bbs050threCnt__ = bbs050threCnt;
    }
    /**
     * @return bbs050threCntLabel を戻します。
     */
    public List < LabelValueBean > getBbs050threCntLabel() {
        return bbs050threCntLabel__;
    }
    /**
     * @param bbs050threCntLabel 設定する bbs050threCntLabel。
     */
    public void setBbs050threCntLabel(List < LabelValueBean > bbs050threCntLabel) {
        bbs050threCntLabel__ = bbs050threCntLabel;
    }
    /**
     * @return bbs050writeCnt を戻します。
     */
    public int getBbs050writeCnt() {
        return bbs050writeCnt__;
    }
    /**
     * @param bbs050writeCnt 設定する bbs050writeCnt。
     */
    public void setBbs050writeCnt(int bbs050writeCnt) {
        bbs050writeCnt__ = bbs050writeCnt;
    }
    /**
     * @return bbs050writeCntLabel を戻します。
     */
    public List < LabelValueBean > getBbs050writeCntLabel() {
        return bbs050writeCntLabel__;
    }
    /**
     * @param bbs050writeCntLabel 設定する bbs050writeCntLabel。
     */
    public void setBbs050writeCntLabel(List < LabelValueBean > bbs050writeCntLabel) {
        bbs050writeCntLabel__ = bbs050writeCntLabel;
    }

    /**
     * <p>bbs050wrtOrder を取得します。
     * @return bbs050wrtOrder
     */
    public int getBbs050wrtOrder() {
        return bbs050wrtOrder__;
    }

    /**
     * <p>bbs050wrtOrder をセットします。
     * @param bbs050wrtOrder bbs050wrtOrder
     */
    public void setBbs050wrtOrder(int bbs050wrtOrder) {
        bbs050wrtOrder__ = bbs050wrtOrder;
    }

    /**
     * @return bbs050threadFlg
     */
    public int getBbs050threadFlg() {
        return bbs050threadFlg__;
    }

    /**
     * @param bbs050threadFlg セットする bbs050threadFlg
     */
    public void setBbs050threadFlg(int bbs050threadFlg) {
        bbs050threadFlg__ = bbs050threadFlg;
    }

    /**
     * @return bbs050forumFlg
     */
    public int getBbs050forumFlg() {
        return bbs050forumFlg__;
    }

    /**
     * @param bbs050forumFlg セットする bbs050forumFlg
     */
    public void setBbs050forumFlg(int bbs050forumFlg) {
        bbs050forumFlg__ = bbs050forumFlg;
    }

    /**
     * @return bbs050midokuTrdFlg
     */
    public int getBbs050midokuTrdFlg() {
        return bbs050midokuTrdFlg__;
    }

    /**
     * @param bbs050midokuTrdFlg セットする bbs050midokuTrdFlg
     */
    public void setBbs050midokuTrdFlg(int bbs050midokuTrdFlg) {
        bbs050midokuTrdFlg__ = bbs050midokuTrdFlg;
    }

    /**
     * <p>bbs050threMainCnt を取得します。
     * @return bbs050threMainCnt
     * @see jp.groupsession.v2.bbs.bbs050.Bbs050Form#bbs050threMainCnt__
     */
    public int getBbs050threMainCnt() {
        return bbs050threMainCnt__;
    }

    /**
     * <p>bbs050threMainCnt をセットします。
     * @param bbs050threMainCnt bbs050threMainCnt
     * @see jp.groupsession.v2.bbs.bbs050.Bbs050Form#bbs050threMainCnt__
     */
    public void setBbs050threMainCnt(int bbs050threMainCnt) {
        bbs050threMainCnt__ = bbs050threMainCnt;
    }

    /**
     * <p>bbs050mainChkedDsp を取得します。
     * @return bbs050mainChkedDsp
     * @see jp.groupsession.v2.bbs.bbs050.Bbs050Form#bbs050mainChkedDsp__
     */
    public int getBbs050mainChkedDsp() {
        return bbs050mainChkedDsp__;
    }

    /**
     * <p>bbs050mainChkedDsp をセットします。
     * @param bbs050mainChkedDsp bbs050mainChkedDsp
     * @see jp.groupsession.v2.bbs.bbs050.Bbs050Form#bbs050mainChkedDsp__
     */
    public void setBbs050mainChkedDsp(int bbs050mainChkedDsp) {
        bbs050mainChkedDsp__ = bbs050mainChkedDsp;
    }

    /**
     * <p>bbs050threMainCntLabel を取得します。
     * @return bbs050threMainCntLabel
     * @see jp.groupsession.v2.bbs.bbs050.Bbs050Form#bbs050threMainCntLabel__
     */
    public List<LabelValueBean> getBbs050threMainCntLabel() {
        return bbs050threMainCntLabel__;
    }

    /**
     * <p>bbs050threMainCntLabel をセットします。
     * @param bbs050threMainCntLabel bbs050threMainCntLabel
     * @see jp.groupsession.v2.bbs.bbs050.Bbs050Form#bbs050threMainCntLabel__
     */
    public void setBbs050threMainCntLabel(
            List<LabelValueBean> bbs050threMainCntLabel) {
        bbs050threMainCntLabel__ = bbs050threMainCntLabel;
    }
}
