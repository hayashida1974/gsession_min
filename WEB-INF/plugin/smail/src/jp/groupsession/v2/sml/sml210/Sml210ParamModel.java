package jp.groupsession.v2.sml.sml210;

import java.util.ArrayList;

import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.sml020.Sml020ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール ひな形一覧画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml210ParamModel extends Sml020ParamModel {
    /** 選択SID(ボタンクリックで) */
    private int selectedHinaSid__;
    /** ページ数 */
    private int sml050PageNum__;
    /** ページ1 */
    private int sml050Slt_page1__;
    /** ページ2 */
    private int sml050Slt_page2__;
    /** ページラベル */
    ArrayList<LabelValueBean> sml050PageLabel__;
    /** オーダーキー */
    private int sml050Order_key__;
    /** ソートキー */
    private int sml050Sort_key__;
    /** ひな形一覧リスト */
    private ArrayList<SmlHinaModel> sml050HinaList__;
    /**
     * <p>selectedHinaSid を取得します。
     * @return selectedHinaSid
     */
    public int getSelectedHinaSid() {
        return selectedHinaSid__;
    }
    /**
     * <p>selectedHinaSid をセットします。
     * @param selectedHinaSid selectedHinaSid
     */
    public void setSelectedHinaSid(int selectedHinaSid) {
        selectedHinaSid__ = selectedHinaSid;
    }
    /**
     * <p>sml050PageNum を取得します。
     * @return sml050PageNum
     */
    public int getSml050PageNum() {
        return sml050PageNum__;
    }
    /**
     * <p>sml050PageNum をセットします。
     * @param sml050PageNum sml050PageNum
     */
    public void setSml050PageNum(int sml050PageNum) {
        sml050PageNum__ = sml050PageNum;
    }
    /**
     * <p>sml050Slt_page1 を取得します。
     * @return sml050Slt_page1
     */
    public int getSml050Slt_page1() {
        return sml050Slt_page1__;
    }
    /**
     * <p>sml050Slt_page1 をセットします。
     * @param sml050Slt_page1 sml050Slt_page1
     */
    public void setSml050Slt_page1(int sml050Slt_page1) {
        sml050Slt_page1__ = sml050Slt_page1;
    }
    /**
     * <p>sml050Slt_page2 を取得します。
     * @return sml050Slt_page2
     */
    public int getSml050Slt_page2() {
        return sml050Slt_page2__;
    }
    /**
     * <p>sml050Slt_page2 をセットします。
     * @param sml050Slt_page2 sml050Slt_page2
     */
    public void setSml050Slt_page2(int sml050Slt_page2) {
        sml050Slt_page2__ = sml050Slt_page2;
    }
    /**
     * <p>sml050PageLabel を取得します。
     * @return sml050PageLabel
     */
    public ArrayList<LabelValueBean> getSml050PageLabel() {
        return sml050PageLabel__;
    }
    /**
     * <p>sml050PageLabel をセットします。
     * @param sml050PageLabel sml050PageLabel
     */
    public void setSml050PageLabel(ArrayList<LabelValueBean> sml050PageLabel) {
        sml050PageLabel__ = sml050PageLabel;
    }
    /**
     * <p>sml050Order_key を取得します。
     * @return sml050Order_key
     */
    public int getSml050Order_key() {
        return sml050Order_key__;
    }
    /**
     * <p>sml050Order_key をセットします。
     * @param sml050Order_key sml050Order_key
     */
    public void setSml050Order_key(int sml050Order_key) {
        sml050Order_key__ = sml050Order_key;
    }
    /**
     * <p>sml050Sort_key を取得します。
     * @return sml050Sort_key
     */
    public int getSml050Sort_key() {
        return sml050Sort_key__;
    }
    /**
     * <p>sml050Sort_key をセットします。
     * @param sml050Sort_key sml050Sort_key
     */
    public void setSml050Sort_key(int sml050Sort_key) {
        sml050Sort_key__ = sml050Sort_key;
    }
    /**
     * <p>sml050HinaList を取得します。
     * @return sml050HinaList
     */
    public ArrayList<SmlHinaModel> getSml050HinaList() {
        return sml050HinaList__;
    }
    /**
     * <p>sml050HinaList をセットします。
     * @param sml050HinaList sml050HinaList
     */
    public void setSml050HinaList(ArrayList<SmlHinaModel> sml050HinaList) {
        sml050HinaList__ = sml050HinaList;
    }
}