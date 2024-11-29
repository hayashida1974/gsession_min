package jp.groupsession.v2.bbs.bbs040;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.bbs010.Bbs010ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 詳細検索画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs040ParamModel extends Bbs010ParamModel {
    /** フォーラム */
    private int bbs040forumSid__ = 0;
    /** キーワード区分 */
    private int bbs040keyKbn__ = 0;
    /** 検索対象(スレッドタイトル) */
    private int bbs040taisyouThread__ = 0;
    /** 検索対象(内容) */
    private int bbs040taisyouNaiyou__ = 0;
    /** 投稿者名 */
    private String bbs040userName__ = null;
    /** 未読・既読 */
    private int bbs040readKbn__ = 0;
    /** 投稿日時指定無し */
    private int bbs040dateNoKbn__ = 0;
    /** 投稿日時From年 */
    private int bbs040fromYear__ = 0;
    /** 投稿日時From月 */
    private int bbs040fromMonth__ = 0;
    /** 投稿日時From日 */
    private int bbs040fromDay__ = 0;
    /** 投稿日時From年月日 */
    private String bbs040fromDate__ = null;
    /** 投稿日時To年 */
    private int bbs040toYear__ = 0;
    /** 投稿日時To月 */
    private int bbs040toMonth__ = 0;
    /** 投稿日時To日 */
    private int bbs040toDay__ = 0;
    /** 投稿日時To年月日 */
    private String bbs040toDate__ = null;
    /** 公開状態 掲示中 */
    private int bbs040publicStatusOngoing__ = 0;
    /** 公開状態 掲示予定 */
    private int bbs040publicStatusScheduled__ = 0;
    /** 公開状態 掲示終了 */
    private int bbs040publicStatusOver__ = 0;
    /** 検索画面ID */
    private String searchDspID__ = null;
    /** スレッドSID */
    private int threadSid__ = 0;

    /** ページコンボ上 */
    private int bbs060page1__ = 0;
    /** 掲示板 最古年 */
    private int bbs040oldYear__ = new UDate().getYear();

    /** フォーラムコンボ */
    private List < LabelValueBean > bbs040forumList__ = null;

    /**
     * @return searchDspID を戻します。
     */
    public String getSearchDspID() {
        return searchDspID__;
    }
    /**
     * @param searchDspID 設定する searchDspID。
     */
    public void setSearchDspID(String searchDspID) {
        searchDspID__ = searchDspID;
    }
    /**
     * @return bbs040dateNoKbn を戻します。
     */
    public int getBbs040dateNoKbn() {
        return bbs040dateNoKbn__;
    }
    /**
     * @param bbs040dateNoKbn 設定する bbs040dateNoKbn。
     */
    public void setBbs040dateNoKbn(int bbs040dateNoKbn) {
        bbs040dateNoKbn__ = bbs040dateNoKbn;
    }
    /**
     * @return bbs040forumSid を戻します。
     */
    public int getBbs040forumSid() {
        return bbs040forumSid__;
    }
    /**
     * @param bbs040forumSid 設定する bbs040forumSid。
     */
    public void setBbs040forumSid(int bbs040forumSid) {
        bbs040forumSid__ = bbs040forumSid;
    }
    /**
     * @return bbs040fromDay を戻します。
     */
    public int getBbs040fromDay() {
        return bbs040fromDay__;
    }
    /**
     * @param bbs040fromDay 設定する bbs040fromDay。
     */
    public void setBbs040fromDay(int bbs040fromDay) {
        bbs040fromDay__ = bbs040fromDay;
    }
    /**
     * @return bbs040fromMonth を戻します。
     */
    public int getBbs040fromMonth() {
        return bbs040fromMonth__;
    }
    /**
     * @param bbs040fromMonth 設定する bbs040fromMonth。
     */
    public void setBbs040fromMonth(int bbs040fromMonth) {
        bbs040fromMonth__ = bbs040fromMonth;
    }
    /**
     * @return bbs040fromYear を戻します。
     */
    public int getBbs040fromYear() {
        return bbs040fromYear__;
    }
    /**
     * @param bbs040fromYear 設定する bbs040fromYear。
     */
    public void setBbs040fromYear(int bbs040fromYear) {
        bbs040fromYear__ = bbs040fromYear;
    }
    /**
     * <p>bbs040fromDate を取得します。
     * @return bbs040fromDate
     * @see jp.groupsession.v2.bbs.bbs040.Bbs040Form#bbs040fromDate__
     */
    public String getBbs040fromDate() {
        return bbs040fromDate__;
    }
    /**
     * <p>bbs040fromDate をセットします。
     * @param bbs040fromDate bbs040fromDate
     * @see jp.groupsession.v2.bbs.bbs040.Bbs040Form#bbs040fromDate__
     */
    public void setBbs040fromDate(String bbs040fromDate) {
        bbs040fromDate__ = bbs040fromDate;
    }
    /**
     * @return bbs040keyKbn を戻します。
     */
    public int getBbs040keyKbn() {
        return bbs040keyKbn__;
    }
    /**
     * @param bbs040keyKbn 設定する bbs040keyKbn。
     */
    public void setBbs040keyKbn(int bbs040keyKbn) {
        bbs040keyKbn__ = bbs040keyKbn;
    }
    /**
     * @return bbs040readKbn を戻します。
     */
    public int getBbs040readKbn() {
        return bbs040readKbn__;
    }
    /**
     * @param bbs040readKbn 設定する bbs040readKbn。
     */
    public void setBbs040readKbn(int bbs040readKbn) {
        bbs040readKbn__ = bbs040readKbn;
    }
    /**
     * @return bbs040taisyouNaiyou を戻します。
     */
    public int getBbs040taisyouNaiyou() {
        return bbs040taisyouNaiyou__;
    }
    /**
     * @param bbs040taisyouNaiyou 設定する bbs040taisyouNaiyou。
     */
    public void setBbs040taisyouNaiyou(int bbs040taisyouNaiyou) {
        bbs040taisyouNaiyou__ = bbs040taisyouNaiyou;
    }
    /**
     * @return bbs040taisyouThread を戻します。
     */
    public int getBbs040taisyouThread() {
        return bbs040taisyouThread__;
    }
    /**
     * @param bbs040taisyouThread 設定する bbs040taisyouThread。
     */
    public void setBbs040taisyouThread(int bbs040taisyouThread) {
        bbs040taisyouThread__ = bbs040taisyouThread;
    }
    /**
     * @return bbs040toDay を戻します。
     */
    public int getBbs040toDay() {
        return bbs040toDay__;
    }
    /**
     * @param bbs040toDay 設定する bbs040toDay。
     */
    public void setBbs040toDay(int bbs040toDay) {
        bbs040toDay__ = bbs040toDay;
    }
    /**
     * @return bbs040toMonth を戻します。
     */
    public int getBbs040toMonth() {
        return bbs040toMonth__;
    }
    /**
     * @param bbs040toMonth 設定する bbs040toMonth。
     */
    public void setBbs040toMonth(int bbs040toMonth) {
        bbs040toMonth__ = bbs040toMonth;
    }
    /**
     * @return bbs040toYear を戻します。
     */
    public int getBbs040toYear() {
        return bbs040toYear__;
    }
    /**
     * @param bbs040toYear 設定する bbs040toYear。
     */
    public void setBbs040toYear(int bbs040toYear) {
        bbs040toYear__ = bbs040toYear;
    }
    /**
     * <p>bbs040toDate を取得します。
     * @return bbs040toDate
     * @see jp.groupsession.v2.bbs.bbs040.Bbs040Form#bbs040toDate__
     */
    public String getBbs040toDate() {
        return bbs040toDate__;
    }
    /**
     * <p>bbs040toDate をセットします。
     * @param bbs040toDate bbs040toDate
     * @see jp.groupsession.v2.bbs.bbs040.Bbs040Form#bbs040toDate__
     */
    public void setBbs040toDate(String bbs040toDate) {
        bbs040toDate__ = bbs040toDate;
    }
    /**
     * <p>bbs040userName を取得します。
     * @return bbs040userName
     */
    public String getBbs040userName() {
        return bbs040userName__;
    }
    /**
     * <p>bbs040userName をセットします。
     * @param bbs040userName bbs040userName
     */
    public void setBbs040userName(String bbs040userName) {
        bbs040userName__ = bbs040userName;
    }
    /**
     * @return bbs040forumList を戻します。
     */
    public List < LabelValueBean > getBbs040forumList() {
        return bbs040forumList__;
    }
    /**
     * @param bbs040forumList 設定する bbs040forumList。
     */
    public void setBbs040forumList(List < LabelValueBean > bbs040forumList) {
        bbs040forumList__ = bbs040forumList;
    }
    /**
     * @return bbs060page1 を戻します。
     */
    public int getBbs060page1() {
        return bbs060page1__;
    }
    /**
     * @param bbs060page1 設定する bbs060page1。
     */
    public void setBbs060page1(int bbs060page1) {
        bbs060page1__ = bbs060page1;
    }
    /**
     * <p>threadSid を取得します。
     * @return threadSid
     */
    public int getThreadSid() {
        return threadSid__;
    }
    /**
     * <p>threadSid をセットします。
     * @param threadSid threadSid
     */
    public void setThreadSid(int threadSid) {
        threadSid__ = threadSid;
    }

    /**
     * <p>bbs040publicStatusOngoing を取得します。
     * @return bbs040publicStatusOngoing
     */
    public int getBbs040publicStatusOngoing() {
        return bbs040publicStatusOngoing__;
    }

    /**
     * <p>bbs040publicStatusOngoing をセットします。
     * @param bbs040publicStatusOngoing bbs040publicStatusOngoing
     */
    public void setBbs040publicStatusOngoing(int bbs040publicStatusOngoing) {
        bbs040publicStatusOngoing__ = bbs040publicStatusOngoing;
    }

    /**
     * <p>bbs040publicStatusScheduled を取得します。
     * @return bbs040publicStatusScheduled
     */
    public int getBbs040publicStatusScheduled() {
        return bbs040publicStatusScheduled__;
    }

    /**
     * <p>bbs040publicStatusScheduled をセットします。
     * @param bbs040publicStatusScheduled bbs040publicStatusScheduled
     */
    public void setBbs040publicStatusScheduled(int bbs040publicStatusScheduled) {
        bbs040publicStatusScheduled__ = bbs040publicStatusScheduled;
    }

    /**
     * <p>bbs040publicStatusOver を取得します。
     * @return bbs040publicStatusOver
     */
    public int getBbs040publicStatusOver() {
        return bbs040publicStatusOver__;
    }

    /**
     * <p>bbs040publicStatusOver をセットします。
     * @param bbs040publicStatusOver bbs040publicStatusOver
     */
    public void setBbs040publicStatusOver(int bbs040publicStatusOver) {
        bbs040publicStatusOver__ = bbs040publicStatusOver;
    }
    
    /**
     * <p>bbs040oldYear を取得します。
     * @return bbs040oldYear
     * @see jp.groupsession.v2.bbs.bbs040.Bbs040Form#bbs040oldYear__
     */
    public int getBbs040oldYear() {
        return bbs040oldYear__;
    }

    /**
     * <p>bbs040oldYear をセットします。
     * @param bbs040oldYear bbs040oldYear
     * @see jp.groupsession.v2.bbs.bbs040.Bbs040Form#bbs040oldYear__
     */
    public void setBbs040oldYear(int bbs040oldYear) {
        bbs040oldYear__ = bbs040oldYear;
    }

}