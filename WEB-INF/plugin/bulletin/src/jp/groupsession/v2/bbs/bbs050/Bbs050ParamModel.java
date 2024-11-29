package jp.groupsession.v2.bbs.bbs050;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs010.Bbs010ParamModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 表示設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs050ParamModel extends Bbs010ParamModel {
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
    /** 投稿者画像表示有無 */
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
    private int bbs050threadFlg__ = 0;
    /** [サブコンテンツ] フォーラム一覧 表示フラグ */
    private int bbs050forumFlg__ = 0;
    /** [サブコンテンツ] 未読スレッド一覧 表示フラグ */
    private int bbs050midokuTrdFlg__ = 0;

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

    /**
     * <br>[機  能] new表示日数コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void setNewCntLabel(RequestModel reqMdl) {
        bbs050newCntLabel__ = new ArrayList < LabelValueBean >();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (String label : Bbs050Form.NEWCNTVALUE) {
            bbs050newCntLabel__.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.less.than.day", new String[] {label}),
                                        label));
        }
    }
}