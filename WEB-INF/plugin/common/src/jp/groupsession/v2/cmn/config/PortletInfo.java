package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] プラグインポートレットに関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PortletInfo implements Comparable<PortletInfo> {

    /** ポートレット画面ID */
    private String id__ = null;
    /** ポートレット選択画面 画面ID */
    private String listId__ = null;
    /** ポートレット選択画面 画面名(メッセージID) */
    private String listNameId__ = null;
    /** プラグインポートレット 表示順 */
    private int order__ = 0;
    /** スクリプト使用 */
    private String script__ = null;
    /** プラグインポートレットBiz */
    private String bizClass__ = null;

    /**
     * <p>id を取得します。
     * @return id
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>listId を取得します。
     * @return listId
     */
    public String getListId() {
        return listId__;
    }
    /**
     * <p>listId をセットします。
     * @param listId listId
     */
    public void setListId(String listId) {
        listId__ = listId;
    }
    /**
     * <p>listNameId を取得します。
     * @return listNameId
     */
    public String getListNameId() {
        return listNameId__;
    }
    /**
     * <p>listNameId をセットします。
     * @param listNameId listNameId
     */
    public void setListNameId(String listNameId) {
        listNameId__ = listNameId;
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
     * <br>[機  能] このオブジェクトと指定されたオブジェクトの順序を比較します。
     * <br>[解  説] 表示順を元に比較を行います。
     * <br>[備  考]
     *
     * @param o 比較対象のオブジェクト
     * @return 比較結果
     */
    public int compareTo(PortletInfo o) {
        return getOrder() - o.getOrder();
    }
    /**
     * <p>script を取得します。
     * @return script
     */
    public String getScript() {
        return script__;
    }
    /**
     * <p>script をセットします。
     * @param script script
     */
    public void setScript(String script) {
        script__ = script;
    }
    /**
     * <p>bizClass を取得します。
     * @return bizClass
     */
    public String getBizClass() {
        return bizClass__;
    }
    /**
     * <p>bizClass をセットします。
     * @param bizClass bizClass
     */
    public void setBizClass(String bizClass) {
        bizClass__ = bizClass;
    }

}
