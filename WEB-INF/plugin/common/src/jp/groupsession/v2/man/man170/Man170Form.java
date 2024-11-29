package jp.groupsession.v2.man.man170;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.man050.Man050Form;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン ログイン履歴詳細画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man170Form extends Man050Form {

    /** 1ページ表示件数 */
    public static final int PAGE_MAX_DATA_CMT = 20;

    /** 氏名 */
    private String man170UserName__;
    /** 状態区分 */
    private int man170UserJtkbn__;
    /** 端末 */
    private int man170Terminal__;
    /** キャリア */
    private int man170Car__;
    /** ソートキー */
    private int man170SortKey__ = GSConstUser.SORT_DATE;
    /** オーダーキー */
    private int man170OrderKey__ = GSConst.ORDER_KEY_DESC;
    /** ページ(上) */
    private int man170PageTop__ = 1;
    /** ページ(下) */
    private int man170PageBottom__ = 1;

    /** ページリスト */
    private List<LabelValueBean> man170PageList__;
    /** キーリスト */
    private List<LabelValueBean> man170KeyList__;
    /** ログイン履歴リスト */
    private ArrayList<Man170Model> man170LoginHistoryList__ = null;

    /**
     * <p>man170UserJtkbn を取得します。
     * @return man170UserJtkbn
     */
    public int getMan170UserJtkbn() {
        return man170UserJtkbn__;
    }
    /**
     * <p>man170UserJtkbn をセットします。
     * @param man170UserJtkbn man170UserJtkbn
     */
    public void setMan170UserJtkbn(int man170UserJtkbn) {
        man170UserJtkbn__ = man170UserJtkbn;
    }
    /**
     * <p>man170UserName を取得します。
     * @return man170UserName
     */
    public String getMan170UserName() {
        return man170UserName__;
    }
    /**
     * <p>man170UserName をセットします。
     * @param man170UserName man170UserName
     */
    public void setMan170UserName(String man170UserName) {
        man170UserName__ = man170UserName;
    }
    /**
     * <p>man170Terminal を取得します。
     * @return man170Terminal
     */
    public int getMan170Terminal() {
        return man170Terminal__;
    }
    /**
     * <p>man170Terminal をセットします。
     * @param man170Terminal man170Terminal
     */
    public void setMan170Terminal(int man170Terminal) {
        man170Terminal__ = man170Terminal;
    }
    /**
     * <p>man170Car を取得します。
     * @return man170Car
     */
    public int getMan170Car() {
        return man170Car__;
    }
    /**
     * <p>man170Car をセットします。
     * @param man170Car man170Car
     */
    public void setMan170Car(int man170Car) {
        man170Car__ = man170Car;
    }
    /**
     * <p>man170PageList を取得します。
     * @return man170PageList
     */
    public List<LabelValueBean> getMan170PageList() {
        return man170PageList__;
    }
    /**
     * <p>man170PageList をセットします。
     * @param man170PageList man170PageList
     */
    public void setMan170PageList(List<LabelValueBean> man170PageList) {
        man170PageList__ = man170PageList;
    }
    /**
     * <p>man170KeyList を取得します。
     * @return man170KeyList
     */
    public List<LabelValueBean> getMan170KeyList() {
        return man170KeyList__;
    }
    /**
     * <p>man170KeyList をセットします。
     * @param man170KeyList man170KeyList
     */
    public void setMan170KeyList(List<LabelValueBean> man170KeyList) {
        man170KeyList__ = man170KeyList;
    }
    /**
     * <p>man170LoginHistoryList を取得します。
     * @return man170LoginHistoryList
     */
    public ArrayList<Man170Model> getMan170LoginHistoryList() {
        return man170LoginHistoryList__;
    }
    /**
     * <p>man170LoginHistoryList をセットします。
     * @param man170LoginHistoryList man170LoginHistoryList
     */
    public void setMan170LoginHistoryList(
            ArrayList<Man170Model> man170LoginHistoryList) {
        man170LoginHistoryList__ = man170LoginHistoryList;
    }
    /**
     * <p>man170SortKey を取得します。
     * @return man170SortKey
     */
    public int getMan170SortKey() {
        return man170SortKey__;
    }
    /**
     * <p>man170SortKey をセットします。
     * @param man170SortKey man170SortKey
     */
    public void setMan170SortKey(int man170SortKey) {
        man170SortKey__ = man170SortKey;
    }
    /**
     * <p>man170OrderKey を取得します。
     * @return man170OrderKey
     */
    public int getMan170OrderKey() {
        return man170OrderKey__;
    }
    /**
     * <p>man170OrderKey をセットします。
     * @param man170OrderKey man170OrderKey
     */
    public void setMan170OrderKey(int man170OrderKey) {
        man170OrderKey__ = man170OrderKey;
    }
    /**
     * <p>man170PageTop を取得します。
     * @return man170PageTop
     */
    public int getMan170PageTop() {
        return man170PageTop__;
    }
    /**
     * <p>man170PageTop をセットします。
     * @param man170PageTop man170PageTop
     */
    public void setMan170PageTop(int man170PageTop) {
        man170PageTop__ = man170PageTop;
    }
    /**
     * <p>man170PageBottom を取得します。
     * @return man170PageBottom
     */
    public int getMan170PageBottom() {
        return man170PageBottom__;
    }
    /**
     * <p>man170PageBottom をセットします。
     * @param man170PageBottom man170PageBottom
     */
    public void setMan170PageBottom(int man170PageBottom) {
        man170PageBottom__ = man170PageBottom;
    }
}