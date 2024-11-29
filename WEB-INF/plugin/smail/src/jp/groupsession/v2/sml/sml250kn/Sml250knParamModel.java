package jp.groupsession.v2.sml.sml250kn;

import java.util.ArrayList;

import jp.groupsession.v2.sml.sml250.Sml250ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] ショートメール アカウント登録確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml250knParamModel extends Sml250ParamModel {
    /** 備考 表示用 */
    private String sml250knBiko__ = null;
    /** テーマ 表示用 */
    private String sml250knTheme__ = null;
    /** 引用符 表示用 */
    private String sml250knQuotes__ = null;

    /** 設定が反映されるユーザリスト */
    private ArrayList<UsrLabelValueBean> sml250knUsrOkLabelList__ = null;
    /** 設定が反映されないユーザ */
    private ArrayList<UsrLabelValueBean> sml250knUsrNgLabelList__ = null;
    /** 対象ユーザ数 */
    private int sml250knUsrCnt__ = 0;
    /** 無効ユーザ数 */
    private int sml250knUsrCntNg__ = 0;

    /**
     * <p>sml250knBiko を取得します。
     * @return sml250knBiko
     */
    public String getSml250knBiko() {
        return sml250knBiko__;
    }
    /**
     * <p>sml250knBiko をセットします。
     * @param sml250knBiko sml250knBiko
     */
    public void setSml250knBiko(String sml250knBiko) {
        sml250knBiko__ = sml250knBiko;
    }

    /**
     * <p>sml250knTheme を取得します。
     * @return sml250knTheme
     */
    public String getSml250knTheme() {
        return sml250knTheme__;
    }
    /**
     * <p>sml250knTheme をセットします。
     * @param sml250knTheme sml250knTheme
     */
    public void setSml250knTheme(String sml250knTheme) {
        sml250knTheme__ = sml250knTheme;
    }
    /**
     * <p>sml250knQuotes を取得します。
     * @return sml250knQuotes
     */
    public String getSml250knQuotes() {
        return sml250knQuotes__;
    }
    /**
     * <p>sml250knQuotes をセットします。
     * @param sml250knQuotes sml250knQuotes
     */
    public void setSml250knQuotes(String sml250knQuotes) {
        sml250knQuotes__ = sml250knQuotes;
    }
    /**
     * <p>sml250knUsrOkLabelList を取得します。
     * @return sml250knUsrOkLabelList
     */
    public ArrayList<UsrLabelValueBean> getSml250knUsrOkLabelList() {
        return sml250knUsrOkLabelList__;
    }
    /**
     * <p>sml250knUsrOkLabelList をセットします。
     * @param sml250knUsrOkLabelList sml250knUsrOkLabelList
     */
    public void setSml250knUsrOkLabelList(ArrayList<UsrLabelValueBean> sml250knUsrOkLabelList) {
        sml250knUsrOkLabelList__ = sml250knUsrOkLabelList;
    }
    /**
     * <p>sml250knUsrNgLabelList を取得します。
     * @return sml250knUsrNgLabelList
     */
    public ArrayList<UsrLabelValueBean> getSml250knUsrNgLabelList() {
        return sml250knUsrNgLabelList__;
    }
    /**
     * <p>sml250knUsrNgLabelList をセットします。
     * @param sml250knUsrNgLabelList sml250knUsrNgLabelList
     */
    public void setSml250knUsrNgLabelList(ArrayList<UsrLabelValueBean> sml250knUsrNgLabelList) {
        sml250knUsrNgLabelList__ = sml250knUsrNgLabelList;
    }
    /**
     * <p>sml250knUsrCnt を取得します。
     * @return sml250knUsrCnt
     */
    public int getSml250knUsrCnt() {
        return sml250knUsrCnt__;
    }
    /**
     * <p>sml250knUsrCnt をセットします。
     * @param sml250knUsrCnt sml250knUsrCnt
     */
    public void setSml250knUsrCnt(int sml250knUsrCnt) {
        sml250knUsrCnt__ = sml250knUsrCnt;
    }
    /**
     * <p>sml250knUsrCntNg を取得します。
     * @return sml250knUsrCntNg
     */
    public int getSml250knUsrCntNg() {
        return sml250knUsrCntNg__;
    }
    /**
     * <p>sml250knUsrCntNg をセットします。
     * @param sml250knUsrCntNg sml250knUsrCntNg
     */
    public void setSml250knUsrCntNg(int sml250knUsrCntNg) {
        sml250knUsrCntNg__ = sml250knUsrCntNg;
    }
}