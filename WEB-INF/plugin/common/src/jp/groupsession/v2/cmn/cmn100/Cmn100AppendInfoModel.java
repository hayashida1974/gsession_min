package jp.groupsession.v2.cmn.cmn100;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstCommon;


/**
 * <br>[機  能] ユーザ情報ポップアップに表示する追加インフォメーションのモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn100AppendInfoModel implements Comparable<Cmn100AppendInfoModel> {

    /** タイトル */
    private String title__ = null;
    /** タイトルROW */
    private String titleRow__ = null;
    /** メッセージ */
    private ArrayList<String> message__ = null;
    /** 表示順**/
    private int order__ = -1;
    /** フィルター有無**/
    private int filter__ = 0;

    /** リンクタイプ 0=無効 1=スケジュール */
    private int linkType__ = GSConstCommon.APPENDINFO_LINK_TYPE_NONE;

    /**
     * <p>linkType を取得します。
     * @return linkType
     */
    public int getLinkType() {
        return linkType__;
    }
    /**
     * <p>linkType をセットします。
     * @param linkType linkType
     */
    public void setLinkType(int linkType) {
        linkType__ = linkType;
    }
    /**
     * <p>filter を取得します。
     * @return filter
     */
    public int getFilter() {
        return filter__;
    }
    /**
     * <p>filter をセットします。
     * @param filter filter
     */
    public void setFilter(int filter) {
        filter__ = filter;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
    }
    /**
     * <p>titleRow を取得します。
     * @return titleRow
     */
    public String getTitleRow() {
        return titleRow__;
    }
    /**
     * <p>titleRow をセットします。
     * @param titleRow titleRow
     */
    public void setTitleRow(String titleRow) {
        titleRow__ = titleRow;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>message を取得します。
     * @return message
     */
    public ArrayList<String> getMessage() {
        return message__;
    }
    /**
     * <p>message をセットします。
     * @param message message
     */
    public void setMessage(ArrayList<String> message) {
        message__ = message;
    }

    /**
     * このオブジェクトと指定されたオブジェクトの順序を比較します。
     * <br>[機  能]表示順を元に比較を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param o 比較対象のオブジェクト
     * @return 比較結果
     */
    public int compareTo(Cmn100AppendInfoModel o) {
        return getOrder() - o.getOrder();
    }

}
