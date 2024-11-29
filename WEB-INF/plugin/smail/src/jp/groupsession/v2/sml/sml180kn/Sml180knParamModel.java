package jp.groupsession.v2.sml.sml180kn;

import java.util.ArrayList;

import jp.groupsession.v2.sml.sml180.Sml180ParamModel;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180knParamModel extends Sml180ParamModel {
    /** 設定が反映されるユーザリスト */
    private ArrayList<String> sml180knUsrOkLabelList__ = null;
    /** 設定が反映されないユーザ */
    private ArrayList<String> sml180knUsrNgLabelList__ = null;
    /** 対象ユーザ数 */
    private int sml180knUsrCnt__ = 0;
    /** 無効ユーザ数 */
    private int sml180knUsrCntNg__ = 0;

    /**
     * <p>sml180knUsrOkLabelList を取得します。
     * @return sml180knUsrOkLabelList
     */
    public ArrayList<String> getSml180knUsrOkLabelList() {
        return sml180knUsrOkLabelList__;
    }
    /**
     * <p>sml180knUsrOkLabelList をセットします。
     * @param sml180knUsrOkLabelList sml180knUsrOkLabelList
     */
    public void setSml180knUsrOkLabelList(ArrayList<String> sml180knUsrOkLabelList) {
        sml180knUsrOkLabelList__ = sml180knUsrOkLabelList;
    }
    /**
     * <p>sml180knUsrNgLabelList を取得します。
     * @return sml180knUsrNgLabelList
     */
    public ArrayList<String> getSml180knUsrNgLabelList() {
        return sml180knUsrNgLabelList__;
    }
    /**
     * <p>sml180knUsrNgLabelList をセットします。
     * @param sml180knUsrNgLabelList sml180knUsrNgLabelList
     */
    public void setSml180knUsrNgLabelList(ArrayList<String> sml180knUsrNgLabelList) {
        sml180knUsrNgLabelList__ = sml180knUsrNgLabelList;
    }
    /**
     * <p>sml180knUsrCnt を取得します。
     * @return sml180knUsrCnt
     */
    public int getSml180knUsrCnt() {
        return sml180knUsrCnt__;
    }
    /**
     * <p>sml180knUsrCnt をセットします。
     * @param sml180knUsrCnt sml180knUsrCnt
     */
    public void setSml180knUsrCnt(int sml180knUsrCnt) {
        sml180knUsrCnt__ = sml180knUsrCnt;
    }
    /**
     * <p>sml180knUsrCntNg を取得します。
     * @return sml180knUsrCntNg
     */
    public int getSml180knUsrCntNg() {
        return sml180knUsrCntNg__;
    }
    /**
     * <p>sml180knUsrCntNg をセットします。
     * @param sml180knUsrCntNg sml180knUsrCntNg
     */
    public void setSml180knUsrCntNg(int sml180knUsrCntNg) {
        sml180knUsrCntNg__ = sml180knUsrCntNg;
    }
}