package jp.groupsession.v2.cmn;

import java.io.Serializable;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;

/**
 * <br>[機  能] RSSリーダー フィードリストを格納するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSFeedList implements Serializable {

    /** Feed SID */
    private int feedSid__ = -1;
    /** 位置(左 or 右) */
    private int feedPosition__ = GSConstRss.RSS_POSITIONFLG_LEFT;
    /** タイトル */
    private String ftitle__ = null;
    /** URL */
    private String furl__ = null;
    /** フィードURL */
    private String feedUrl__ = null;
    /** 説明 */
    private String description__ = null;
    /** フィードリスト */
    private List<SyndEntry> feedList__ = null;
    /** RSS購読の有無 */
    private int koudokuCount__ = GSConstRss.RSS_KOUDOKU_FLG_NO;
    /** 読み込みエラー */
    private boolean readError__ = false;

    /**
     * <p>feedList を取得します。
     * @return feedList
     */
    public List<SyndEntry> getFeedList() {
        return feedList__;
    }
    /**
     * <p>feedList をセットします。
     * @param feedList feedList
     */
    public void setFeedList(List<SyndEntry> feedList) {
        this.feedList__ = feedList;
    }
    /**
     * <p>feedSid を取得します。
     * @return feedSid
     */
    public int getFeedSid() {
        return feedSid__;
    }
    /**
     * <p>feedSid をセットします。
     * @param feedSid feedSid
     */
    public void setFeedSid(int feedSid) {
        feedSid__ = feedSid;
    }
    /**
     * <p>ftitle を取得します。
     * @return ftitle
     */
    public String getFtitle() {
        return ftitle__;
    }
    /**
     * <p>ftitle をセットします。
     * @param ftitle ftitle
     */
    public void setFtitle(String ftitle) {
        ftitle__ = ftitle;
    }
    /**
     * <p>furl を取得します。
     * @return furl
     */
    public String getFurl() {
        return furl__;
    }
    /**
     * <p>furl をセットします。
     * @param furl furl
     */
    public void setFurl(String furl) {
        furl__ = furl;
    }
    /**
     * <p>feedUrl を取得します。
     * @return feedUrl
     */
    public String getFeedUrl() {
        return feedUrl__;
    }
    /**
     * <p>feedUrl をセットします。
     * @param feedUrl feedUrl
     */
    public void setFeedUrl(String feedUrl) {
        feedUrl__ = feedUrl;
    }
    /**
     * <p>feedPosition を取得します。
     * @return feedPosition
     */
    public int getFeedPosition() {
        return feedPosition__;
    }
    /**
     * <p>feedPosition をセットします。
     * @param feedPosition feedPosition
     */
    public void setFeedPosition(int feedPosition) {
        feedPosition__ = feedPosition;
    }
    /**
     * <p>koudokuCount を取得します。
     * @return koudokuCount
     */
    public int getKoudokuCount() {
        return koudokuCount__;
    }
    /**
     * <p>koudokuCount をセットします。
     * @param koudokuCount koudokuCount
     */
    public void setKoudokuCount(int koudokuCount) {
        koudokuCount__ = koudokuCount;
    }
    /**
     * <p>description を取得します。
     * @return description
     */
    public String getDescription() {
        return description__;
    }
    /**
     * <p>description をセットします。
     * @param description description
     */
    public void setDescription(String description) {
        description__ = description;
    }
    /**
     * <p>readError を取得します。
     * @return readError
     */
    public boolean isReadError() {
        return readError__;
    }
    /**
     * <p>readError をセットします。
     * @param readError readError
     */
    public void setReadError(boolean readError) {
        readError__ = readError;
    }
}
