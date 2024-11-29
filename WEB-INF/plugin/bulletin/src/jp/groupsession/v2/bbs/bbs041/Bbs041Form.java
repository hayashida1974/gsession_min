package jp.groupsession.v2.bbs.bbs041;

import java.util.List;

import jp.groupsession.v2.bbs.bbs040.Bbs040Form;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] 掲示板検索結果一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs041Form extends Bbs040Form {
    /** ページコンボ上 */
    private int bbs041page1__ = 0;
    /** ページコンボ下 */
    private int bbs041page2__ = 0;
    /** フォーラムSID */
    private String bbs041forumSid__ = null;

    /** 検索結果一覧 */
    private List < BulletinDspModel > resultList__ = null;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int bbs041searchUse__ = GSConst.PLUGIN_USE;

    /** WEB検索ワード */
    private String bbs041WebSearchWord__ = "";

    /**
     * @return bbs041forumSid を戻します。
     */
    public String getBbs041forumSid() {
        return bbs041forumSid__;
    }
    /**
     * @param bbs041forumSid 設定する bbs041forumSid。
     */
    public void setBbs041forumSid(String bbs041forumSid) {
        bbs041forumSid__ = bbs041forumSid;
    }
    /**
     * @return bbs041page1 を戻します。
     */
    public int getBbs041page1() {
        return bbs041page1__;
    }
    /**
     * @param bbs041page1 設定する bbs041page1。
     */
    public void setBbs041page1(int bbs041page1) {
        bbs041page1__ = bbs041page1;
    }
    /**
     * @return bbs041page2 を戻します。
     */
    public int getBbs041page2() {
        return bbs041page2__;
    }
    /**
     * @param bbs041page2 設定する bbs041page2。
     */
    public void setBbs041page2(int bbs041page2) {
        bbs041page2__ = bbs041page2;
    }
    /**
     * <p>resultList を取得します。
     * @return resultList
     */
    public List<BulletinDspModel> getResultList() {
        return resultList__;
    }
    /**
     * <p>resultList をセットします。
     * @param resultList resultList
     */
    public void setResultList(List<BulletinDspModel> resultList) {
        resultList__ = resultList;
    }
    /**
     * <p>bbs041searchUse を取得します。
     * @return bbs041searchUse
     */
    public int getBbs041searchUse() {
        return bbs041searchUse__;
    }
    /**
     * <p>bbs041searchUse をセットします。
     * @param bbs041searchUse bbs041searchUse
     */
    public void setBbs041searchUse(int bbs041searchUse) {
        bbs041searchUse__ = bbs041searchUse;
    }
    /**
     * <p>bbs041WebSearchWord を取得します。
     * @return bbs041WebSearchWord
     */
    public String getBbs041WebSearchWord() {
        return bbs041WebSearchWord__;
    }
    /**
     * <p>bbs041WebSearchWord をセットします。
     * @param bbs041WebSearchWord bbs041WebSearchWord
     */
    public void setBbs041WebSearchWord(String bbs041WebSearchWord) {
        bbs041WebSearchWord__ = bbs041WebSearchWord;
    }

}
