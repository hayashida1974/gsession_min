package jp.groupsession.v2.sml.sml210;

import java.util.ArrayList;

import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール ひな形一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml210Form extends Sml020Form {

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
     * @return selectedHinaSid を戻します。
     */
    public int getSelectedHinaSid() {
        return selectedHinaSid__;
    }
    /**
     * @param selectedHinaSid 設定する selectedHinaSid。
     */
    public void setSelectedHinaSid(int selectedHinaSid) {
        selectedHinaSid__ = selectedHinaSid;
    }
    /**
     * @return sml050HinaList を戻します。
     */
    public ArrayList<SmlHinaModel> getSml050HinaList() {
        return sml050HinaList__;
    }
    /**
     * @param sml050HinaList 設定する sml050HinaList。
     */
    public void setSml050HinaList(ArrayList<SmlHinaModel> sml050HinaList) {
        sml050HinaList__ = sml050HinaList;
    }
    /**
     * @return sml050Order_key を戻します。
     */
    public int getSml050Order_key() {
        return sml050Order_key__;
    }
    /**
     * @param sml050OrderKey 設定する sml050Order_key。
     */
    public void setSml050Order_key(int sml050OrderKey) {
        sml050Order_key__ = sml050OrderKey;
    }
    /**
     * @return sml050PageLabel を戻します。
     */
    public ArrayList<LabelValueBean> getSml050PageLabel() {
        return sml050PageLabel__;
    }
    /**
     * @param sml050PageLabel 設定する sml050PageLabel。
     */
    public void setSml050PageLabel(ArrayList<LabelValueBean> sml050PageLabel) {
        sml050PageLabel__ = sml050PageLabel;
    }
    /**
     * @return sml050PageNum を戻します。
     */
    public int getSml050PageNum() {
        return sml050PageNum__;
    }
    /**
     * @param sml050PageNum 設定する sml050PageNum。
     */
    public void setSml050PageNum(int sml050PageNum) {
        sml050PageNum__ = sml050PageNum;
    }
    /**
     * @return sml050Slt_page1 を戻します。
     */
    public int getSml050Slt_page1() {
        return sml050Slt_page1__;
    }
    /**
     * @param sml050SltPage1 設定する sml050Slt_page1。
     */
    public void setSml050Slt_page1(int sml050SltPage1) {
        sml050Slt_page1__ = sml050SltPage1;
    }
    /**
     * @return sml050Slt_page2 を戻します。
     */
    public int getSml050Slt_page2() {
        return sml050Slt_page2__;
    }
    /**
     * @param sml050SltPage2 設定する sml050Slt_page2。
     */
    public void setSml050Slt_page2(int sml050SltPage2) {
        sml050Slt_page2__ = sml050SltPage2;
    }
    /**
     * @return sml050Sort_key を戻します。
     */
    public int getSml050Sort_key() {
        return sml050Sort_key__;
    }
    /**
     * @param sml050SortKey 設定する sml050Sort_key。
     */
    public void setSml050Sort_key(int sml050SortKey) {
        sml050Sort_key__ = sml050SortKey;
    }
}