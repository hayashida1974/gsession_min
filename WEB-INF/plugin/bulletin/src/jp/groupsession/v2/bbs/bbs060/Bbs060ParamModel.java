package jp.groupsession.v2.bbs.bbs060;

import java.util.List;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs041.Bbs041ParamModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;

/**
 * <br>[機  能] 掲示板 スレッド一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs060ParamModel extends Bbs041ParamModel {

    /** ページコンボ下 */
    private int bbs060page2__ = 0;
    /** スレッドSID */
    private int threadSid__ = GSConstBulletin.THREAD_SID_NONE;

    /** メインページ遷移フラグ */
    private int bbsmainFlg__ = 0;

    /** フォーラム名 */
    private String bbs060forumName__ = null;
    /** スレッド一覧 */
    private List < BulletinDspModel > threadList__ = null;
    /** フォーラム一覧 */
    private List < BulletinDspModel > forumList__ = null;
    /** 未読スレッド一覧 */
    private List<BulletinDspModel> notReadThreadList__ = null;
    /** フォーラムメンバー数 */
    private String forumMemberCount__ = null;
    /** バイナリSID */
    private Long bbs060BinSid__ = new Long(0);
    /** オーダーキー */
    private String bbs060orderKey__ = "0";
    /** ソートキー */
    private String bbs060sortKey__ = "5";

    /** 新規スレッドボタン表示フラグ */
    private boolean bbs060createDspFlg__ = false;

    /** フォーラム一覧表示フラグ */
    private int bbs060forumDspFlg__ = 0;

    /** 「全て既読にする」機能の使用可/不可 */
    private boolean bbs060mreadFlg__ = false;
    /** 表示しているフォーラム内の未読スレッド一覧 */
    private int bbs060unreadCnt__ = 0;

    /** フォーラム ディスク容量警告 */
    private int bbs060forumWarnDisk__ = 0;

    /** 重要度 */
    private int bbs060ThreImportance__;


    /** スレッドタイトル */
    private String bbs060threadTitle__ = null;
    /** 投稿一覧 ページコンボ上 */
    private int bbs060postPage1__ = 0;
    /** 投稿一覧 ページコンボ下 */
    private int bbs060postPage2__ = 0;
    /** スレッド削除ボタン表示フラグ */
    private int bbs060showThreBtn__ = 0;
    /** 投稿一覧 */
    private List <BulletinDspModel> postList__ = null;
    /** スレッドURL */
    private String threadUrl__ = null;
    /** 並び順 */
    private int bbs060postOrderKey__ = -1;
    /** 返信可能区分 */
    private String bbs060reply__ = null;
    /** 掲示期限 */
    private String bbs060limitDate__ = null;
    /** 既読件数 */
    private int readedCnt__ = 0;
    /** ボタン項目表示フラグ true:表示 false:非表示 */
    private boolean bbs060btnDspFlg__ = BulletinDspModel.SHOWALLBTNFLG_YES;
    /** 写真表示有無 */
    private int photoFileDsp__ = GSConstBulletin.BBS_IMAGE_DSP;
    /** 添付ファイル画像表示有無 */
    private int tempImageFileDsp__ = GSConstBulletin.BBS_IMAGE_TEMP_DSP;
    /** 投稿SID */
    private int bbs060postSid__ = 0;
    /** 投稿バイナリSID */
    private Long bbs060postBinSid__ = new Long(0);

    /** 削除スレッドタイトル */
    private String bbs060delTitle__ = null;

    /** 予約投稿数 */
    private int bbs060scheduledPostNum__ = 0;

    /** 投稿本文ファイルSID */
    private int bodyFileId__ = 0;

    /** 草稿SID */
    private int soukouSid__ = -1;
    /** 草稿モード */
    private boolean soukouFlg__ = false;
    /** 戻り先画面 */
    private String bbs220BackDsp__ = null;
    /** 戻り先スレッドSID */
    private int bbs220BackThreadSid__ = -1;
    /** 戻り先フォーラムSID */
    private int bbs220BackForumSid__ = -1;
    /** ソートキー */
    private int bbs220SortKey__ = GSConstBulletin.SOUKOU_SORT_KEY_ADATE;
    /** オーダーキー */
    private int bbs220OrderKey__ = GSConstBulletin.ORDER_KEY_DESC;
    /** チェック対象SID */
    private String[] bbs060ChkInfSid__ = null;

    /**
     * @return bbs060forumName を戻します。
     */
    public String getBbs060forumName() {
        return bbs060forumName__;
    }
    /**
     * @param bbs060forumName 設定する bbs060forumName。
     */
    public void setBbs060forumName(String bbs060forumName) {
        bbs060forumName__ = bbs060forumName;
    }
    /**
     * @return bbs060page2 を戻します。
     */
    public int getBbs060page2() {
        return bbs060page2__;
    }
    /**
     * @param bbs060page2 設定する bbs060page2。
     */
    public void setBbs060page2(int bbs060page2) {
        bbs060page2__ = bbs060page2;
    }

    /**
     * @return threadList を戻します。
     */
    public List < BulletinDspModel > getThreadList() {
        return threadList__;
    }
    /**
     * @param threadList 設定する threadList。
     */
    public void setThreadList(List < BulletinDspModel > threadList) {
        threadList__ = threadList;
    }
    /**
     * <p>bbsmainFlg を取得します。
     * @return bbsmainFlg
     */
    public int getBbsmainFlg() {
        return bbsmainFlg__;
    }
    /**
     * <p>bbsmainFlg をセットします。
     * @param bbsmainFlg bbsmainFlg
     */
    public void setBbsmainFlg(int bbsmainFlg) {
        bbsmainFlg__ = bbsmainFlg;
    }
    /**
     * <p>forumList を取得します。
     * @return forumList
     */
    public List<BulletinDspModel> getForumList() {
        return forumList__;
    }
    /**
     * <p>forumList をセットします。
     * @param forumList forumList
     */
    public void setForumList(List<BulletinDspModel> forumList) {
        forumList__ = forumList;
    }
    /**
     * <p>forumMemberCount を取得します。
     * @return forumMemberCount
     */
    public String getForumMemberCount() {
        return forumMemberCount__;
    }
    /**
     * <p>forumMemberCount をセットします。
     * @param forumMemberCount forumMemberCount
     */
    public void setForumMemberCount(String forumMemberCount) {
        forumMemberCount__ = forumMemberCount;
    }
    /**
     * <p>bbs060BinSid を取得します。
     * @return bbs060BinSid
     */
    public Long getBbs060BinSid() {
        return bbs060BinSid__;
    }
    /**
     * <p>bbs060BinSid をセットします。
     * @param bbs060BinSid bbs060BinSid
     */
    public void setBbs060BinSid(Long bbs060BinSid) {
        bbs060BinSid__ = bbs060BinSid;
    }
    /**
     * @return bbs060orderKey
     */
    public String getBbs060orderKey() {
        return bbs060orderKey__;
    }
    /**
     * @param bbs060orderKey セットする bbs060orderKey
     */
    public void setBbs060orderKey(String bbs060orderKey) {
        bbs060orderKey__ = bbs060orderKey;
    }
    /**
     * @return bbs060sortKey
     */
    public String getBbs060sortKey() {
        return bbs060sortKey__;
    }
    /**
     * @param bbs060sortKey セットする bbs060sortKey
     */
    public void setBbs060sortKey(String bbs060sortKey) {
        bbs060sortKey__ = bbs060sortKey;
    }
    /**
     * <p>bbs060createDspFlg を取得します。
     * @return bbs060createDspFlg
     */
    public boolean isBbs060createDspFlg() {
        return bbs060createDspFlg__;
    }
    /**
     * <p>bbs060createDspFlg をセットします。
     * @param bbs060createDspFlg bbs060createDspFlg
     */
    public void setBbs060createDspFlg(boolean bbs060createDspFlg) {
        bbs060createDspFlg__ = bbs060createDspFlg;
    }
    /**
     * @return bbs060forumDspFlg
     */
    public int getBbs060forumDspFlg() {
        return bbs060forumDspFlg__;
    }
    /**
     * @param bbs060forumDspFlg セットする bbs060forumDspFlg
     */
    public void setBbs060forumDspFlg(int bbs060forumDspFlg) {
        bbs060forumDspFlg__ = bbs060forumDspFlg;
    }
    /**
     * <p>bbs060mreadFlg を取得します。
     * @return bbs060mreadFlg
     */
    public boolean isBbs060mreadFlg() {
        return bbs060mreadFlg__;
    }
    /**
     * <p>bbs060mreadFlg をセットします。
     * @param bbs060mreadFlg bbs060mreadFlg
     */
    public void setBbs060mreadFlg(boolean bbs060mreadFlg) {
        bbs060mreadFlg__ = bbs060mreadFlg;
    }
    /**
     * <p>bbs060unreadCnt を取得します。
     * @return bbs060unreadCnt
     */
    public int getBbs060unreadCnt() {
        return bbs060unreadCnt__;
    }
    /**
     * <p>bbs060unreadCnt をセットします。
     * @param bbs060unreadCnt bbs060unreadCnt
     */
    public void setBbs060unreadCnt(int bbs060unreadCnt) {
        bbs060unreadCnt__ = bbs060unreadCnt;
    }
    /**
     * <p>bbs060forumWarnDisk を取得します。
     * @return bbs060forumWarnDisk
     */
    public int getBbs060forumWarnDisk() {
        return bbs060forumWarnDisk__;
    }
    /**
     * <p>bbs060forumWarnDisk をセットします。
     * @param bbs060forumWarnDisk bbs060forumWarnDisk
     */
    public void setBbs060forumWarnDisk(int bbs060forumWarnDisk) {
        bbs060forumWarnDisk__ = bbs060forumWarnDisk;
    }
    /**
     * <p>bbs060ThreImportance を取得します。
     * @return bbs060ThreImportance
     */
    public int getBbs060ThreImportance() {
        return bbs060ThreImportance__;
    }
    /**
     * <p>bbs060ThreImportance をセットします。
     * @param bbs060ThreImportance bbs060ThreImportance
     */
    public void setBbs060ThreImportance(int bbs060ThreImportance) {
        bbs060ThreImportance__ = bbs060ThreImportance;
    }
    /**
     * @return notReadThreadList
     */
    public List<BulletinDspModel> getNotReadThreadList() {
        return notReadThreadList__;
    }
    /**
     * @param notReadThreadList セットする notReadThreadList
     */
    public void setNotReadThreadList(List<BulletinDspModel> notReadThreadList) {
        notReadThreadList__ = notReadThreadList;
    }
    /**
     * <p>bbs060threadTitle を取得します。
     * @return bbs060threadTitle
     */
    public String getBbs060threadTitle() {
        return bbs060threadTitle__;
    }
    /**
     * <p>bbs060threadTitle をセットします。
     * @param bbs060threadTitle bbs060threadTitle
     */
    public void setBbs060threadTitle(String bbs060threadTitle) {
        bbs060threadTitle__ = bbs060threadTitle;
    }
    /**
     * <p>bbs060postPage1 を取得します。
     * @return bbs060postPage1
     */
    public int getBbs060postPage1() {
        return bbs060postPage1__;
    }
    /**
     * <p>bbs060postPage1 をセットします。
     * @param bbs060postPage1 bbs060postPage1
     */
    public void setBbs060postPage1(int bbs060postPage1) {
        bbs060postPage1__ = bbs060postPage1;
    }
    /**
     * <p>bbs060postPage2 を取得します。
     * @return bbs060postPage2
     */
    public int getBbs060postPage2() {
        return bbs060postPage2__;
    }
    /**
     * <p>bbs060postPage2 をセットします。
     * @param bbs060postPage2 bbs060postPage2
     */
    public void setBbs060postPage2(int bbs060postPage2) {
        bbs060postPage2__ = bbs060postPage2;
    }
    /**
     * <p>bbs060showThreBtn を取得します。
     * @return bbs060showThreBtn
     */
    public int getBbs060showThreBtn() {
        return bbs060showThreBtn__;
    }
    /**
     * <p>bbs060showThreBtn をセットします。
     * @param bbs060showThreBtn bbs060showThreBtn
     */
    public void setBbs060showThreBtn(int bbs060showThreBtn) {
        bbs060showThreBtn__ = bbs060showThreBtn;
    }
    /**
     * <p>postList を取得します。
     * @return postList
     */
    public List <BulletinDspModel> getPostList() {
        return postList__;
    }
    /**
     * <p>postList をセットします。
     * @param postList postList
     */
    public void setPostList(List <BulletinDspModel> postList) {
        postList__ = postList;
    }
    /**
     * <p>threadUrl を取得します。
     * @return threadUrl
     */
    public String getThreadUrl() {
        return threadUrl__;
    }
    /**
     * <p>threadUrl をセットします。
     * @param threadUrl threadUrl
     */
    public void setThreadUrl(String threadUrl) {
        threadUrl__ = threadUrl;
    }
    /**
     * <p>bbs060postOrderKey を取得します。
     * @return bbs060postOrderKey
     */
    public int getBbs060postOrderKey() {
        return bbs060postOrderKey__;
    }
    /**
     * <p>bbs060postOrderKey をセットします。
     * @param bbs060postOrderKey bbs060postOrderKey
     */
    public void setBbs060postOrderKey(int bbs060postOrderKey) {
        bbs060postOrderKey__ = bbs060postOrderKey;
    }
    /**
     * <p>bbs060reply を取得します。
     * @return bbs060reply
     */
    public String getBbs060reply() {
        return bbs060reply__;
    }
    /**
     * <p>bbs060reply をセットします。
     * @param bbs060reply bbs060reply
     */
    public void setBbs060reply(String bbs060reply) {
        bbs060reply__ = bbs060reply;
    }
    /**
     * <p>bbs060limitDate を取得します。
     * @return bbs060limitDate
     */
    public String getBbs060limitDate() {
        return bbs060limitDate__;
    }
    /**
     * <p>bbs060limitDate をセットします。
     * @param bbs060limitDate bbs060limitDate
     */
    public void setBbs060limitDate(String bbs060limitDate) {
        bbs060limitDate__ = bbs060limitDate;
    }
    /**
     * <p>readedCnt を取得します。
     * @return readedCnt
     */
    public int getReadedCnt() {
        return readedCnt__;
    }
    /**
     * <p>readedCnt をセットします。
     * @param readedCnt readedCnt
     */
    public void setReadedCnt(int readedCnt) {
        readedCnt__ = readedCnt;
    }
    /**
     * <p>bbs060btnDspFlg を取得します。
     * @return bbs060btnDspFlg
     */
    public boolean isBbs060btnDspFlg() {
        return bbs060btnDspFlg__;
    }
    /**
     * <p>bbs060btnDspFlg をセットします。
     * @param bbs060btnDspFlg bbs060btnDspFlg
     */
    public void setBbs060btnDspFlg(boolean bbs060btnDspFlg) {
        bbs060btnDspFlg__ = bbs060btnDspFlg;
    }
    /**
     * <p>photoFileDsp を取得します。
     * @return photoFileDsp
     */
    public int getPhotoFileDsp() {
        return photoFileDsp__;
    }
    /**
     * <p>photoFileDsp をセットします。
     * @param photoFileDsp photoFileDsp
     */
    public void setPhotoFileDsp(int photoFileDsp) {
        photoFileDsp__ = photoFileDsp;
    }
    /**
     * <p>tempImageFileDsp を取得します。
     * @return tempImageFileDsp
     */
    public int getTempImageFileDsp() {
        return tempImageFileDsp__;
    }
    /**
     * <p>tempImageFileDsp をセットします。
     * @param tempImageFileDsp tempImageFileDsp
     */
    public void setTempImageFileDsp(int tempImageFileDsp) {
        tempImageFileDsp__ = tempImageFileDsp;
    }
    /**
     * <p>bbs060postSid を取得します。
     * @return bbs060postSid
     */
    public int getBbs060postSid() {
        return bbs060postSid__;
    }
    /**
     * <p>bbs060postSid をセットします。
     * @param bbs060postSid bbs060postSid
     */
    public void setBbs060postSid(int bbs060postSid) {
        bbs060postSid__ = bbs060postSid;
    }
    /**
     * <p>bbs060postBinSid を取得します。
     * @return bbs060postBinSid
     */
    public Long getBbs060postBinSid() {
        return bbs060postBinSid__;
    }
    /**
     * <p>bbs060postBinSid をセットします。
     * @param bbs060postBinSid bbs060postBinSid
     */
    public void setBbs060postBinSid(Long bbs060postBinSid) {
        bbs060postBinSid__ = bbs060postBinSid;
    }
    /**
     * <p>bbs060delTitle を取得します。
     * @return bbs060delTitle
     */
    public String getBbs060delTitle() {
        return bbs060delTitle__;
    }
    /**
     * <p>bbs060delTitle をセットします。
     * @param bbs060delTitle bbs060delTitle
     */
    public void setBbs060delTitle(String bbs060delTitle) {
        bbs060delTitle__ = bbs060delTitle;
    }
    /**
     * <p>bbs060scheduledPostNum を取得します。
     * @return bbs060scheduledPostNum
     */
    public int getBbs060scheduledPostNum() {
        return bbs060scheduledPostNum__;
    }
    /**
     * <p>bbs060scheduledPostNum をセットします。
     * @param bbs060scheduledPostNum bbs060scheduledPostNum
     */
    public void setBbs060scheduledPostNum(int bbs060scheduledPostNum) {
        bbs060scheduledPostNum__ = bbs060scheduledPostNum;
    }
    /**
     * <p>bodyFileId を取得します。
     * @return bodyFileId
     */
    public int getBodyFileId() {
        return bodyFileId__;
    }
    /**
     * <p>bodyFileId をセットします。
     * @param bodyFileId bodyFileId
     */
    public void setBodyFileId(int bodyFileId) {
        bodyFileId__ = bodyFileId;
    }
    /**
     * <p>soukouSid を取得します。
     * @return soukouSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#soukouSid__
     */
    public int getSoukouSid() {
        return soukouSid__;
    }
    /**
     * <p>soukouSid をセットします。
     * @param soukouSid soukouSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#soukouSid__
     */
    public void setSoukouSid(int soukouSid) {
        soukouSid__ = soukouSid;
    }
    /**
     * <p>soukouFlg を取得します。
     * @return soukouFlg
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#soukouFlg__
     */
    public boolean isSoukouFlg() {
        return soukouFlg__;
    }
    /**
     * <p>soukouFlg をセットします。
     * @param soukouFlg soukouFlg
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#soukouFlg__
     */
    public void setSoukouFlg(boolean soukouFlg) {
        soukouFlg__ = soukouFlg;
    }
    /**
     * <p>bbs220SortKey を取得します。
     * @return bbs220SortKey
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220SortKey__
     */
    public int getBbs220SortKey() {
        return bbs220SortKey__;
    }
    /**
     * <p>bbs220SortKey をセットします。
     * @param bbs220SortKey bbs220SortKey
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220SortKey__
     */
    public void setBbs220SortKey(int bbs220SortKey) {
        bbs220SortKey__ = bbs220SortKey;
    }
    /**
     * <p>bbs220OrderKey を取得します。
     * @return bbs220OrderKey
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220OrderKey__
     */
    public int getBbs220OrderKey() {
        return bbs220OrderKey__;
    }
    /**
     * <p>bbs220OrderKey をセットします。
     * @param bbs220OrderKey bbs220OrderKey
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220OrderKey__
     */
    public void setBbs220OrderKey(int bbs220OrderKey) {
        bbs220OrderKey__ = bbs220OrderKey;
    }
    /**
     * <p>bbs060ChkInfSid を取得します。
     * @return bbs060ChkInfSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs060ChkInfSid__
     */
    public String[] getBbs060ChkInfSid() {
        return bbs060ChkInfSid__;
    }
    /**
     * <p>bbs060ChkInfSid をセットします。
     * @param bbs060ChkInfSid bbs060ChkInfSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs060ChkInfSid__
     */
    public void setBbs060ChkInfSid(String[] bbs060ChkInfSid) {
        bbs060ChkInfSid__ = bbs060ChkInfSid;
    }
    /**
     * <p>bbs220BackDsp を取得します。
     * @return bbs220BackDsp
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220BackDsp__
     */
    public String getBbs220BackDsp() {
        return bbs220BackDsp__;
    }
    /**
     * <p>bbs220BackDsp をセットします。
     * @param bbs220BackDsp bbs220BackDsp
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220BackDsp__
     */
    public void setBbs220BackDsp(String bbs220BackDsp) {
        bbs220BackDsp__ = bbs220BackDsp;
    }
    /**
     * <p>bbs220BackThreadSid を取得します。
     * @return bbs220BackThreadSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220BackThreadSid__
     */
    public int getBbs220BackThreadSid() {
        return bbs220BackThreadSid__;
    }
    /**
     * <p>bbs220BackThreadSid をセットします。
     * @param bbs220BackThreadSid bbs220BackThreadSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220BackThreadSid__
     */
    public void setBbs220BackThreadSid(int bbs220BackThreadSid) {
        bbs220BackThreadSid__ = bbs220BackThreadSid;
    }
    /**
     * <p>bbs220BackForumSid を取得します。
     * @return bbs220BackForumSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220BackForumSid__
     */
    public int getBbs220BackForumSid() {
        return bbs220BackForumSid__;
    }
    /**
     * <p>bbs220BackForumSid をセットします。
     * @param bbs220BackForumSid bbs220BackForumSid
     * @see jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel#bbs220BackForumSid__
     */
    public void setBbs220BackForumSid(int bbs220BackForumSid) {
        bbs220BackForumSid__ = bbs220BackForumSid;
    }


}