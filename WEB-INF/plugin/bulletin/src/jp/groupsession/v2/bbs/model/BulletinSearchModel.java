package jp.groupsession.v2.bbs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 掲示板の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinSearchModel extends AbstractModel {

    /** フォーラムSID */
    private int bfiSid__ = 0;
    /** スレッドSID */
    private int btiSid__ = 0;
    /** 投稿SID */
    private int bwiSid__ = 0;
    /** ユーザSID */
    private int userSid__ = 0;
    /** 現在日時 */
    private UDate now__ = null;

    /** 検索キーワード */
    private List < String > keywordList__ = null;
    /** キーワード区分 */
    private int keywordKbn__ = GSConstBulletin.KEYWORDKBN_AND;
    /** 検索対象(スレッドタイトル)フラグ */
    private boolean searchThreTitleFlg__ = false;
    /** 検索対象(投稿内容)フラグ */
    private boolean searchWriteValueFlg__ = false;
    /** 投稿者名 */
    private String contributorName__ = null;
    /** 未読・既読 */
    private int readKbn__ = 0;
    /** 公開状態 掲示中 */
    private int publicStatusOngoing__ = 0;
    /** 公開状態 掲示予定 */
    private int publicStatusScheduled__ = 0;
    /** 公開状態 掲示終了 */
    private int publicStatusOver__ = 0;
    /** 投稿日時指定フラグ */
    private boolean writeDateFlg__ = false;
    /** 投稿日時From */
    private UDate writeDateFrom__ = null;
    /** 投稿日時To日 */
    private UDate writeDateTo__ = null;
    /** ソート順(投稿日時) */
    private int orderWriteDate__ = GSConstBulletin.BBS_WRTLIST_ORDER_ASC;

    /** 検索結果取得開始位置 */
    private int start__ = 0;
    /** 検索結果取得終了位置 */
    private int end__ = 0;
    /** new表示日数 */
    private int newCnt__ = 0;
    /** 管理者か否か true:管理者 false:一般ユーザ */
    private boolean admin__ = true;
    /** フォーラム管理者か否か true:管理者 false:一般ユーザ */
    private boolean forumAdmin__ = true;

    /** アプリケーションのルートパス */
    private String appRoot__ = null;
    /** テンポラリディレクトリ */
    private String tempDir__ = null;

    /**
     * @return admin を戻します。
     */
    public boolean isAdmin() {
        return admin__;
    }
    /**
     * @param admin 設定する admin。
     */
    public void setAdmin(boolean admin) {
        admin__ = admin;
    }
    /**
     * @return userSid を戻します。
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * @param userSid 設定する userSid。
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>keywordKbn を取得します。
     * @return keywordKbn
     */
    public int getKeywordKbn() {
        return keywordKbn__;
    }
    /**
     * <p>keywordKbn をセットします。
     * @param keywordKbn keywordKbn
     */
    public void setKeywordKbn(int keywordKbn) {
        keywordKbn__ = keywordKbn;
    }
    /**
     * @return keywordList を戻します。
     */
    public List < String > getKeywordList() {
        return keywordList__;
    }
    /**
     * @param keywordList 設定する keywordList。
     */
    public void setKeywordList(List < String > keywordList) {
        keywordList__ = keywordList;
    }
    /**
     * @return appRoot を戻します。
     */
    public String getAppRoot() {
        return appRoot__;
    }
    /**
     * @param appRoot 設定する appRoot。
     */
    public void setAppRoot(String appRoot) {
        appRoot__ = appRoot;
    }
    /**
     * @return bfiSid を戻します。
     */
    public int getBfiSid() {
        return bfiSid__;
    }
    /**
     * @param bfiSid 設定する bfiSid。
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }
    /**
     * @return btiSid を戻します。
     */
    public int getBtiSid() {
        return btiSid__;
    }
    /**
     * @param btiSid 設定する btiSid。
     */
    public void setBtiSid(int btiSid) {
        btiSid__ = btiSid;
    }
    /**
     * @return bwiSid を戻します。
     */
    public int getBwiSid() {
        return bwiSid__;
    }
    /**
     * @param bwiSid 設定する bwiSid。
     */
    public void setBwiSid(int bwiSid) {
        bwiSid__ = bwiSid;
    }
    /**
     * @return end を戻します。
     */
    public int getEnd() {
        return end__;
    }
    /**
     * @param end 設定する end。
     */
    public void setEnd(int end) {
        end__ = end;
    }
    /**
     * @return newCnt を戻します。
     */
    public int getNewCnt() {
        return newCnt__;
    }
    /**
     * @param newCnt 設定する newCnt。
     */
    public void setNewCnt(int newCnt) {
        newCnt__ = newCnt;
    }
    /**
     * @return start を戻します。
     */
    public int getStart() {
        return start__;
    }
    /**
     * @param start 設定する start。
     */
    public void setStart(int start) {
        start__ = start;
    }
    /**
     * @return tempDir を戻します。
     */
    public String getTempDir() {
        return tempDir__;
    }
    /**
     * @param tempDir 設定する tempDir。
     */
    public void setTempDir(String tempDir) {
        tempDir__ = tempDir;
    }
    /**
     * <p>contributorName を取得します。
     * @return contributorName
     */
    public String getContributorName() {
        return contributorName__;
    }
    /**
     * <p>contributorName をセットします。
     * @param contributorName contributorName
     */
    public void setContributorName(String contributorName) {
        contributorName__ = contributorName;
    }
    /**
     * <p>readKbn を取得します。
     * @return readKbn
     */
    public int getReadKbn() {
        return readKbn__;
    }
    /**
     * <p>readKbn をセットします。
     * @param readKbn readKbn
     */
    public void setReadKbn(int readKbn) {
        readKbn__ = readKbn;
    }
    /**
     * <p>publicStatusOngoing を取得します。
     * @return publicStatusOngoing
     */
    public int getPublicStatusOngoing() {
        return publicStatusOngoing__;
    }
    /**
     * <p>publicStatusOngoing をセットします。
     * @param publicStatusOngoing publicStatusOngoing
     */
    public void setPublicStatusOngoing(int publicStatusOngoing) {
        publicStatusOngoing__ = publicStatusOngoing;
    }
    /**
     * <p>publicStatusScheduled を取得します。
     * @return publicStatusScheduled
     */
    public int getPublicStatusScheduled() {
        return publicStatusScheduled__;
    }
    /**
     * <p>publicStatusScheduled をセットします。
     * @param publicStatusScheduled publicStatusScheduled
     */
    public void setPublicStatusScheduled(int publicStatusScheduled) {
        publicStatusScheduled__ = publicStatusScheduled;
    }
    /**
     * <p>publicStatusOver を取得します。
     * @return publicStatusOver
     */
    public int getPublicStatusOver() {
        return publicStatusOver__;
    }
    /**
     * <p>publicStatusOver をセットします。
     * @param publicStatusOver publicStatusOver
     */
    public void setPublicStatusOver(int publicStatusOver) {
        publicStatusOver__ = publicStatusOver;
    }
    /**
     * <p>searchThreTitleFlg を取得します。
     * @return searchThreTitleFlg
     */
    public boolean isSearchThreTitleFlg() {
        return searchThreTitleFlg__;
    }
    /**
     * <p>searchThreTitleFlg をセットします。
     * @param searchThreTitleFlg searchThreTitleFlg
     */
    public void setSearchThreTitleFlg(boolean searchThreTitleFlg) {
        searchThreTitleFlg__ = searchThreTitleFlg;
    }
    /**
     * <p>searchWriteValueFlg を取得します。
     * @return searchWriteValueFlg
     */
    public boolean isSearchWriteValueFlg() {
        return searchWriteValueFlg__;
    }
    /**
     * <p>searchWriteValueFlg をセットします。
     * @param searchWriteValueFlg searchWriteValueFlg
     */
    public void setSearchWriteValueFlg(boolean searchWriteValueFlg) {
        searchWriteValueFlg__ = searchWriteValueFlg;
    }
    /**
     * <p>writeDateFlg を取得します。
     * @return writeDateFlg
     */
    public boolean isWriteDateFlg() {
        return writeDateFlg__;
    }
    /**
     * <p>writeDateFlg をセットします。
     * @param writeDateFlg writeDateFlg
     */
    public void setWriteDateFlg(boolean writeDateFlg) {
        writeDateFlg__ = writeDateFlg;
    }
    /**
     * <p>writeDateFrom を取得します。
     * @return writeDateFrom
     */
    public UDate getWriteDateFrom() {
        return writeDateFrom__;
    }
    /**
     * <p>writeDateFrom をセットします。
     * @param writeDateFrom writeDateFrom
     */
    public void setWriteDateFrom(UDate writeDateFrom) {
        writeDateFrom__ = writeDateFrom;
    }
    /**
     * <p>writeDateTo を取得します。
     * @return writeDateTo
     */
    public UDate getWriteDateTo() {
        return writeDateTo__;
    }
    /**
     * <p>writeDateTo をセットします。
     * @param writeDateTo writeDateTo
     */
    public void setWriteDateTo(UDate writeDateTo) {
        writeDateTo__ = writeDateTo;
    }
    /**
     * <p>orderWriteDate を取得します。
     * @return orderWriteDate
     */
    public int getOrderWriteDate() {
        return orderWriteDate__;
    }
    /**
     * <p>orderWriteDate をセットします。
     * @param orderWriteDate orderWriteDate
     */
    public void setOrderWriteDate(int orderWriteDate) {
        orderWriteDate__ = orderWriteDate;
    }
    /**
     * <p>now を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now をセットします。
     * @param now now
     */
    public void setNow(UDate now) {
        now__ = now;
    }

    /**
     * <br>[機  能] キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param keyword キーワード
     */
    public void setKeyword(String keyword) {
        List < String > keywordList = new ArrayList < String >();
        if (keyword == null) {
            keyword = "";
        }
        String searchKey = StringUtil.substitute(keyword, "　", " ");
        StringTokenizer st = new StringTokenizer(searchKey, " ");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (!StringUtil.isNullZeroString(str)) {
                keywordList.add(str);
            }
        }

        setKeywordList(keywordList);
    }
    /**
     * <p>forumAdmin を取得します。
     * @return forumAdmin
     */
    public boolean isForumAdmin() {
        return forumAdmin__;
    }
    /**
     * <p>forumAdmin をセットします。
     * @param forumAdmin forumAdmin
     */
    public void setForumAdmin(boolean forumAdmin) {
        forumAdmin__ = forumAdmin;
    }
}
