package jp.groupsession.v2.sml.sml240;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] ショートメール アカウントマネージャー画面 検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml240SearchModel extends AbstractModel {

    /** キーワード */
    private String keyword__ = null;
    /** グループSID */
    private int grpSid__ = 0;
    /** ユーザSID */
    private int userSid__ = 0;

    /** ソートキー */
    private int sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int order__ = GSConstWebmail.ORDER_ASC;

    /** ページ数 */
    private int page__ = 0;
    /** 最大表示件数 */
    private int maxCount__ = 0;

    /**
     * <p>keyword を取得します。
     * @return keyword
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
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
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>maxCount を取得します。
     * @return maxCount
     */
    public int getMaxCount() {
        return maxCount__;
    }
    /**
     * <p>maxCount をセットします。
     * @param maxCount maxCount
     */
    public void setMaxCount(int maxCount) {
        maxCount__ = maxCount;
    }
    /**
     * <p>page を取得します。
     * @return page
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(int page) {
        page__ = page;
    }

    /**
     * <br>[機  能] 検索条件が存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:検索条件あり false:検索条件なし
     */
    public boolean existSearchCondition() {
        return !StringUtil.isNullZeroString(keyword__)
                || grpSid__ >= 0 || userSid__ > 0;
    }
}
