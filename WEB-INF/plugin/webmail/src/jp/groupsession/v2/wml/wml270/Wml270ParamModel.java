package jp.groupsession.v2.wml.wml270;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.wml020.Wml020ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 送信先リスト管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml270ParamModel extends Wml020ParamModel {

    /** 検索キーワード */
    private String wml270keyword__ = null;
    /** ページ上段 */
    private int wml270pageTop__ = 0;
    /** ページ下段 */
    private int wml270pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean wml270pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String wml270svKeyword__ = null;

    /** ソートキー */
    private int wml270sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int wml270order__ = GSConstWebmail.ORDER_ASC;

    /** 送信先リスト(編集対象) */
    private int wmlEditDestList__ = 0;
    /** 検索実行フラグ */
    private int wml270searchFlg__ = 0;

    /** 検索結果一覧 */
    private List<Wml270DestListModel> destListList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** 選択された送信先リスト */
    private String[] wml270selectDestList__;
    /**
     * <p>destListList を取得します。
     * @return destListList
     */
    public List<Wml270DestListModel> getDestListList() {
        return destListList__;
    }
    /**
     * <p>destListList をセットします。
     * @param destListList destListList
     */
    public void setDestListList(List<Wml270DestListModel> destListList) {
        destListList__ = destListList;
    }
    /**
     * <p>pageCombo を取得します。
     * @return pageCombo
     */
    public List<LabelValueBean> getPageCombo() {
        return pageCombo__;
    }
    /**
     * <p>pageCombo をセットします。
     * @param pageCombo pageCombo
     */
    public void setPageCombo(List<LabelValueBean> pageCombo) {
        pageCombo__ = pageCombo;
    }
    /**
     * <p>wmlEditDestList を取得します。
     * @return wmlEditDestList
     */
    public int getWmlEditDestList() {
        return wmlEditDestList__;
    }
    /**
     * <p>wmlEditDestList をセットします。
     * @param wmlEditDestList wmlEditDestList
     */
    public void setWmlEditDestList(int wmlEditDestList) {
        wmlEditDestList__ = wmlEditDestList;
    }
    /**
     * <p>wml270keyword を取得します。
     * @return wml270keyword
     */
    public String getWml270keyword() {
        return wml270keyword__;
    }
    /**
     * <p>wml270keyword をセットします。
     * @param wml270keyword wml270keyword
     */
    public void setWml270keyword(String wml270keyword) {
        wml270keyword__ = wml270keyword;
    }
    /**
     * <p>wml270order を取得します。
     * @return wml270order
     */
    public int getWml270order() {
        return wml270order__;
    }
    /**
     * <p>wml270order をセットします。
     * @param wml270order wml270order
     */
    public void setWml270order(int wml270order) {
        wml270order__ = wml270order;
    }
    /**
     * <p>wml270pageBottom を取得します。
     * @return wml270pageBottom
     */
    public int getWml270pageBottom() {
        return wml270pageBottom__;
    }
    /**
     * <p>wml270pageBottom をセットします。
     * @param wml270pageBottom wml270pageBottom
     */
    public void setWml270pageBottom(int wml270pageBottom) {
        wml270pageBottom__ = wml270pageBottom;
    }
    /**
     * <p>wml270pageTop を取得します。
     * @return wml270pageTop
     */
    public int getWml270pageTop() {
        return wml270pageTop__;
    }
    /**
     * <p>wml270pageTop をセットします。
     * @param wml270pageTop wml270pageTop
     */
    public void setWml270pageTop(int wml270pageTop) {
        wml270pageTop__ = wml270pageTop;
    }
    /**
     * <p>wml270sortKey を取得します。
     * @return wml270sortKey
     */
    public int getWml270sortKey() {
        return wml270sortKey__;
    }
    /**
     * <p>wml270sortKey をセットします。
     * @param wml270sortKey wml270sortKey
     */
    public void setWml270sortKey(int wml270sortKey) {
        wml270sortKey__ = wml270sortKey;
    }
    /**
     * <p>wml270searchFlg を取得します。
     * @return wml270searchFlg
     */
    public int getWml270searchFlg() {
        return wml270searchFlg__;
    }
    /**
     * <p>wml270searchFlg をセットします。
     * @param wml270searchFlg wml270searchFlg
     */
    public void setWml270searchFlg(int wml270searchFlg) {
        wml270searchFlg__ = wml270searchFlg;
    }
    /**
     * <p>wml270svKeyword を取得します。
     * @return wml270svKeyword
     */
    public String getWml270svKeyword() {
        return wml270svKeyword__;
    }
    /**
     * <p>wml270svKeyword をセットします。
     * @param wml270svKeyword wml270svKeyword
     */
    public void setWml270svKeyword(String wml270svKeyword) {
        wml270svKeyword__ = wml270svKeyword;
    }

    /**
     * <p>wml270pageDspFlg を取得します。
     * @return wml270pageDspFlg
     */
    public boolean isWml270pageDspFlg() {
        return wml270pageDspFlg__;
    }

    /**
     * <p>wml270pageDspFlg をセットします。
     * @param wml270pageDspFlg wml270pageDspFlg
     */
    public void setWml270pageDspFlg(boolean wml270pageDspFlg) {
        wml270pageDspFlg__ = wml270pageDspFlg;
    }
    /**
     * <p>wml270selectDestList を取得します。
     * @return wml270selectDestList
     */
    public String[] getWml270selectDestList() {
        return wml270selectDestList__;
    }
    /**
     * <p>wml270selectDestList をセットします。
     * @param wml270selectDestList wml270selectDestList
     */
    public void setWml270selectDestList(String[] wml270selectDestList) {
        wml270selectDestList__ = wml270selectDestList;
    }
}