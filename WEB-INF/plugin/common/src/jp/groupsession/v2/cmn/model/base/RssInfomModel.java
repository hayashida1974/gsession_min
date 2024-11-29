package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSFeedList;

/**
 * <p>RSS_INFOM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssInfomModel implements Serializable {

    /** RSS_SID mapping */
    private int rssSid__;
    /** RSM_URL_FEED mapping */
    private String rsmUrlFeed__;
    /** RSM_FEEDDATA mapping */
    private GSFeedList rsmFeeddata__;
    /** RSM_UPDATE_TIME mapping */
    private UDate rsmUpdateTime__;
    /** RSS購読の有無 */
    private int koudokuCount__ = GSConstRss.RSS_KOUDOKU_FLG_NO;
    /** RSM_AUID mapping */
    private int rsmAuid__;
    /** RSM_ADATE mapping */
    private UDate rsmAdate__;
    /** RSM_EUID mapping */
    private int rsmEuid__;
    /** RSM_EDATE mapping */
    private UDate rsmEdate__;
    /** フィードURL */
    private String url__;
    /** フィードタイトル */
    private String title__;
    /** RSM_AUTH mapping */
    private int rsmAuth__;
    /** RSM_AUTH_ID mapping */
    private String rsmAuthId__;
    /** RSM_AUTH_PAWD mapping */
    private String rsmAuthPswd__;

    /**
     * <p>Default Constructor
     */
    public RssInfomModel() {
    }

    /**
     * <p>get RSS_SID value
     * @return RSS_SID value
     */
    public int getRssSid() {
        return rssSid__;
    }

    /**
     * <p>set RSS_SID value
     * @param rssSid RSS_SID value
     */
    public void setRssSid(int rssSid) {
        rssSid__ = rssSid;
    }

    /**
     * <p>get RSM_URL_FEED value
     * @return RSM_URL_FEED value
     */
    public String getRsmUrlFeed() {
        return rsmUrlFeed__;
    }

    /**
     * <p>set RSM_URL_FEED value
     * @param rsmUrlFeed RSM_URL_FEED value
     */
    public void setRsmUrlFeed(String rsmUrlFeed) {
        rsmUrlFeed__ = rsmUrlFeed;
    }

    /**
     * <p>get RSM_FEEDDATA value
     * @return RSM_FEEDDATA value
     */
    public GSFeedList getRsmFeeddata() {
        return rsmFeeddata__;
    }

    /**
     * <p>set RSM_FEEDDATA value
     * @param rsmFeeddata RSM_FEEDDATA value
     */
    public void setRsmFeeddata(GSFeedList rsmFeeddata) {
        rsmFeeddata__ = rsmFeeddata;
    }

    /**
     * <p>get RSM_UPDATE_TIME value
     * @return RSM_UPDATE_TIME value
     */
    public UDate getRsmUpdateTime() {
        return rsmUpdateTime__;
    }

    /**
     * <p>set RSM_UPDATE_TIME value
     * @param rsmUpdateTime RSM_UPDATE_TIME value
     */
    public void setRsmUpdateTime(UDate rsmUpdateTime) {
        rsmUpdateTime__ = rsmUpdateTime;
    }

    /**
     * <p>get RSM_AUID value
     * @return RSM_AUID value
     */
    public int getRsmAuid() {
        return rsmAuid__;
    }

    /**
     * <p>set RSM_AUID value
     * @param rsmAuid RSM_AUID value
     */
    public void setRsmAuid(int rsmAuid) {
        rsmAuid__ = rsmAuid;
    }

    /**
     * <p>get RSM_ADATE value
     * @return RSM_ADATE value
     */
    public UDate getRsmAdate() {
        return rsmAdate__;
    }

    /**
     * <p>set RSM_ADATE value
     * @param rsmAdate RSM_ADATE value
     */
    public void setRsmAdate(UDate rsmAdate) {
        rsmAdate__ = rsmAdate;
    }

    /**
     * <p>get RSM_EUID value
     * @return RSM_EUID value
     */
    public int getRsmEuid() {
        return rsmEuid__;
    }

    /**
     * <p>set RSM_EUID value
     * @param rsmEuid RSM_EUID value
     */
    public void setRsmEuid(int rsmEuid) {
        rsmEuid__ = rsmEuid;
    }

    /**
     * <p>get RSM_EDATE value
     * @return RSM_EDATE value
     */
    public UDate getRsmEdate() {
        return rsmEdate__;
    }

    /**
     * <p>set RSM_EDATE value
     * @param rsmEdate RSM_EDATE value
     */
    public void setRsmEdate(UDate rsmEdate) {
        rsmEdate__ = rsmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rssSid__);
        buf.append(",");
        buf.append(NullDefault.getString(rsmUrlFeed__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsmFeeddata__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsmUpdateTime__, ""));
        buf.append(",");
        buf.append(rsmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsmAdate__, ""));
        buf.append(",");
        buf.append(rsmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsmEdate__, ""));
        return buf.toString();
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
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }

    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }

    /**
     * <p>rsmAuth を取得します。
     * @return rsmAuth
     */
    public int getRsmAuth() {
        return rsmAuth__;
    }

    /**
     * <p>rsmAuth をセットします。
     * @param rsmAuth rsmAuth
     */
    public void setRsmAuth(int rsmAuth) {
        rsmAuth__ = rsmAuth;
    }

    /**
     * <p>rsmAuthId を取得します。
     * @return rsmAuthId
     */
    public String getRsmAuthId() {
        return rsmAuthId__;
    }

    /**
     * <p>rsmAuthId をセットします。
     * @param rsmAuthId rsmAuthId
     */
    public void setRsmAuthId(String rsmAuthId) {
        rsmAuthId__ = rsmAuthId;
    }

    /**
     * <p>rsmAuthPswd を取得します。
     * @return rsmAuthPswd
     */
    public String getRsmAuthPswd() {
        return rsmAuthPswd__;
    }

    /**
     * <p>rsmAuthPswd をセットします。
     * @param rsmAuthPswd rsmAuthPswd
     */
    public void setRsmAuthPswd(String rsmAuthPswd) {
        rsmAuthPswd__ = rsmAuthPswd;
    }


}
