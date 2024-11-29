package jp.groupsession.v2.bbs.bbs030;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs020.Bbs020ParamModel;
import jp.groupsession.v2.cmn.GSConstBBS;
import jp.groupsession.v2.cmn.ITempDirIdUseable;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラム登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs030ParamModel extends Bbs020ParamModel implements ITempDirIdUseable {
    /** プラグインID */
    private String bbs030pluginId__ = GSConstBulletin.PLUGIN_ID_BULLETIN;

    /** 処理モード */
    private int bbs030cmdMode__ = 0;

    /** フォーラム名 */
    private String bbs030forumName__ = null;
    /** コメント */
    private String bbs030comment__ = null;
    /** グループSID */
    private int bbs030groupSid__ = Integer.parseInt(GSConstBulletin.GROUP_COMBO_VALUE);
    /** フォーラム階層レベル */
    private int bbs030ForumLevel__ = GSConstBulletin.BBS_FORUM_MIN_LEVEL;
    /** 選択中の親フォーラムSID */
    private int bbs030ParentForumSid__ = GSConstBulletin.BBS_DEFAULT_PFORUM_SID;
    /** 選択不可の親フォーラムSID */
    private int[] bbs030DisabledForumSid__ = null;


    /** グループ一覧 */
    private List < LabelValueBean > bbs030GroupList__ = null;
    /** 追加済みユーザ一覧 */
    private List < UsrLabelValueBean > bbs030LeftUserList__ = null;
    /** 追加済みユーザ一覧 */
    private List < UsrLabelValueBean > bbs030LeftUserListRead__ = null;
    /** 追加用ユーザ一覧 */
    private List < UsrLabelValueBean > bbs030RightUserList__ = null;

    /** 追加済みメンバー(選択) 追加・編集・削除 */
    private String[] bbs030SelectLeftUser__ = new String[0];
    /** 追加済みメンバー(選択) 閲覧 */
    private String[] bbs030SelectLeftUserRead__ = new String[0];
    /** 追加用メンバー(選択) */
    private String[] bbs030SelectRightUser__ = new String[0];


    /** グループSID 管理者 */
    private int bbs030groupSidAdm__ = Integer.parseInt(GSConstBulletin.GROUP_COMBO_VALUE_USER);
    /** グループ一覧 管理者 */
    private List < LabelValueBean > bbs030GroupListAdm__ = null;
    /** 追加済みユーザ一覧 管理者 */
    private List < UsrLabelValueBean > bbs030LeftUserListAdm__ = null;
    /** 追加用ユーザ一覧 管理者 */
    private List < UsrLabelValueBean > bbs030RightUserListAdm__ = null;
    /** 追加済みメンバー(選択) 管理者 */
    private String[] bbs030SelectLeftUserAdm__ = new String[0];
    /** 追加用メンバー(選択) 管理者 */
    private String[] bbs030SelectRightUserAdm__ = new String[0];

    /** 投稿許可 */
    private String bbs030reply__ = String.valueOf(GSConstBulletin.BBS_THRE_REPLY_YES);
    /** 新規ユーザスレッド閲覧状態 */
    private String bbs030read__ = String.valueOf(GSConstBulletin.NEWUSER_THRE_VIEW_NO);
    /** 投稿許可 */
    private String bbs030mread__ = String.valueOf(GSConstBulletin.BBS_FORUM_MREAD_NO);
    /** スレッドテンプレート区分 */
    private int bbs030templateKbn__ = GSConstBulletin.BBS_THRE_TEMPLATE_NO;
    /** スレッドテンプレート */
    private String bbs030template__ = null;
    /** スレッドテンプレート(HTML時) */
    private String bbs030templateHtml__ = null;
    /** スレッドテンプレート 投稿時使用区分 */
    private int bbs030templateWriteKbn__ = GSConstBulletin.BBS_THRE_TEMPLATE_WRITE_NO;
    /** スレッドテンプレート コンテンツタイプ */
    private int bbs030templateType__ = GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN;
    /** ディスク容量 */
    private int bbs030diskSize__ = GSConstBulletin.BFI_DISK_NOLIMIT;
    /** ディスク容量 最大値 */
    private String bbs030diskSizeLimit__ = null;
    /** ディスク容量警告 閾値 */
    private int bbs030warnDisk__ = 0;
    /** ディスク容量警告 閾値 */
    private int bbs030warnDiskThreshold__ = 0;

    /** 掲示期間 有効初期値 */
    private int bbs030LimitDisable__ = GSConstBulletin.THREAD_ENABLE;
    /** 掲示期間 初期値 */
    private int bbs030Limit__ = GSConstBulletin.THREAD_LIMIT_NO;
    /** 掲示期間日数 初期値 */
    private String bbs030LimitDate__ = null;
    /** 時間単位 */
    private int bbs030TimeUnit__ = GSConstBBS.MINUTE_DIVISION5;
    /** スレッド保存期間設定 */
    private int bbs030Keep__ = GSConstBulletin.THREAD_KEEP_NO;
    /** スレッド保存期間 経過年 */
    private int bbs030KeepDateY__ = -1;
    /** スレッド保存期間 経過月 */
    private int bbs030KeepDateM__ = -1;
    /** スレッド保存期間 自動削除設定内容表示フラグ */
    private int bbs030DspAtdelFlg__ = GSConstBulletin.AUTO_DELETE_OFF;
    /** スレッド保存期間 表示用 自動削除設定内容 経過年 */
    private int bbs030DspAtdelYear__ = -1;
    /** スレッド保存期間 表示用 自動削除設定内容  経過月 */
    private int bbs030DspAtdelMonth__ = -1;

    /** 経過年ラベル */
    private List < LabelValueBean > bbs030KeepDateYLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > bbs030KeepDateMLabel__ = null;

    /** 写真ファイル名 */
    private String bbs030ImageName__;
    /** 写真ファイル保存名 */
    private String bbs030ImageSaveName__;
    /** バイナリSID */
    private Long bbs030BinSid__ = Long.valueOf(0);

    /** TempDirId*/
    private String tempDirId__ = null;

    /** ディスク容量警告 閾値 選択値 */
    private List<LabelValueBean> warnDiskThresholdList__ = null;

    /** スクロール位置 */
    private String bbs030ScrollPosition__ = null;
    /** 親フォーラム選択フレーム スクロール位置 */
    private String bbs190ScrollPosition__ = null;

    /** 編集ユーザ一覧 登録用 */
    private String[] bbs030UserListWriteReg__ = null;
    /** 閲覧ユーザ一覧 登録用 */
    private String[] bbs030UserListReadReg__ = null;
    /** 管理者ユーザ一覧 登録用 */
    private String[] bbs030UserListAdmReg__ = null;

    /** メンバー選択（第1階層フォーラム時） */
    private UserGroupSelector bbs030memberSelectForRoot__ = null;
    /** ユーザ選択*/
    private Bbs030ForumMemberSelector bbs030memberSelect__ = null;
    /** 管理者選択*/
    private Bbs030ForumAdminMemberSelector bbs030adminSelect__ = null;


    /** 選択禁止ユーザ */
    private List<Integer> bbs030BanUserSidList__ = null;
    /** 選択禁止グループ */
    private List<Integer> bbs030BanGroupSidList__ = null;
    /** ユーザ絞り込み選択禁止グループを設定 */
    private List<Integer> bbs030DisableGroupSidList__ = null;

    /** 下位フォーラムへのメンバー反映フラグ */
    private int bbs030AdaptChildMemFlg__ = 0;
    /** 子フォーラムの有無フラグ */
    private int bbs030HaveChildForumFlg__ = GSConstBulletin.FORUM_CHILD_NOTEXIST;
    /** メンバーの親フォーラム準拠フラグ */
    private int bbs030FollowParentMemFlg__ = GSConstBulletin.FOLLOW_PARENT_MEMBER_YES;

    /** 無制限許可フラグ*/
    private int bbs030UnlimitedFlg__ = GSConstBulletin.BFI_UNLIMITED_YES;

    /**
     * <p>bbs030Read を取得します。
     * @return bbs030Read
     */
    public String getBbs030read() {
        return bbs030read__;
    }

    /**
     * <p>bbs030Read をセットします。
     * @param bbs030read bbs030read
     */
    public void setBbs030read(String bbs030read) {
        bbs030read__ = bbs030read;
    }

    /**
     * <p>bbs030Reply を取得します。
     * @return bbs030Reply
     */
    public String getBbs030reply() {
        return bbs030reply__;
    }

    /**
     * <p>bbs030Reply をセットします。
     * @param bbs030reply bbs030reply
     */
    public void setBbs030reply(String bbs030reply) {
        bbs030reply__ = bbs030reply;
    }

    /**
     * @return bbs030cmdMode を戻します。
     */
    public int getBbs030cmdMode() {
        return bbs030cmdMode__;
    }

    /**
     * @param bbs030cmdMode 設定する bbs030cmdMode。
     */
    public void setBbs030cmdMode(int bbs030cmdMode) {
        bbs030cmdMode__ = bbs030cmdMode;
    }

    /**
     * @return bbs030comment を戻します。
     */
    public String getBbs030comment() {
        return bbs030comment__;
    }

    /**
     * @param bbs030comment 設定する bbs030comment。
     */
    public void setBbs030comment(String bbs030comment) {
        bbs030comment__ = bbs030comment;
    }

    /**
     * @return bbs030forumName を戻します。
     */
    public String getBbs030forumName() {
        return bbs030forumName__;
    }

    /**
     * @param bbs030forumName 設定する bbs030forumName。
     */
    public void setBbs030forumName(String bbs030forumName) {
        bbs030forumName__ = bbs030forumName;
    }

    /**
     * @return bbs030GroupList を戻します。
     */
    public List < LabelValueBean > getBbs030GroupList() {
        return bbs030GroupList__;
    }

    /**
     * @param bbs030GroupList 設定する bbs030GroupList。
     */
    public void setBbs030GroupList(List < LabelValueBean > bbs030GroupList) {
        bbs030GroupList__ = bbs030GroupList;
    }

    /**
     * @return bbs030groupSid を戻します。
     */
    public int getBbs030groupSid() {
        return bbs030groupSid__;
    }

    /**
     * @param bbs030groupSid 設定する bbs030groupSid。
     */
    public void setBbs030groupSid(int bbs030groupSid) {
        bbs030groupSid__ = bbs030groupSid;
    }

    /**
     * <p>bbs030ForumLevel を取得します。
     * @return bbs030ForumLevel
     */
    public int getBbs030ForumLevel() {
        return bbs030ForumLevel__;
    }

    /**
     * <p>bbs030ForumLevel をセットします。
     * @param bbs030ForumLevel bbs030ForumLevel
     */
    public void setBbs030ForumLevel(int bbs030ForumLevel) {
        bbs030ForumLevel__ = bbs030ForumLevel;
    }

    /**
     * <p>bbs030ParentForumSid を取得します。
     * @return bbs030ParentForumSid
     */
    public int getBbs030ParentForumSid() {
        return bbs030ParentForumSid__;
    }

    /**
     * <p>bbs030ParentForumSid をセットします。
     * @param bbs030ParentForumSid bbs030ParentForumSid
     */
    public void setBbs030ParentForumSid(int bbs030ParentForumSid) {
        bbs030ParentForumSid__ = bbs030ParentForumSid;
    }

    /**
     * <p>bbs030DisabledForumSid を取得します。
     * @return bbs030DisabledForumSid
     */
    public int[] getBbs030DisabledForumSid() {
        return bbs030DisabledForumSid__;
    }

    /**
     * <p>bbs030DisabledForumSid をセットします。
     * @param bbs030DisabledForumSid bbs030DisabledForumSid
     */
    public void setBbs030DisabledForumSid(int[] bbs030DisabledForumSid) {
        bbs030DisabledForumSid__ = bbs030DisabledForumSid;
    }

    /**
     * @return bbs030LeftUserList を戻します。
     */
    public List < UsrLabelValueBean > getBbs030LeftUserList() {
        return bbs030LeftUserList__;
    }

    /**
     * @param bbs030LeftUserList 設定する bbs030LeftUserList。
     */
    public void setBbs030LeftUserList(List < UsrLabelValueBean > bbs030LeftUserList) {
        bbs030LeftUserList__ = bbs030LeftUserList;
    }

    /**
     * @return bbs030RightUserList を戻します。
     */
    public List < UsrLabelValueBean > getBbs030RightUserList() {
        return bbs030RightUserList__;
    }

    /**
     * @param bbs030RightUserList 設定する bbs030RightUserList。
     */
    public void setBbs030RightUserList(List < UsrLabelValueBean > bbs030RightUserList) {
        bbs030RightUserList__ = bbs030RightUserList;
    }

    /**
     * @return bbs030SelectLeftUser を戻します。
     */
    public String[] getBbs030SelectLeftUser() {
        return bbs030SelectLeftUser__;
    }

    /**
     * @param bbs030SelectLeftUser 設定する bbs030SelectLeftUser。
     */
    public void setBbs030SelectLeftUser(String[] bbs030SelectLeftUser) {
        bbs030SelectLeftUser__ = bbs030SelectLeftUser;
    }

    /**
     * @return bbs030SelectRightUser を戻します。
     */
    public String[] getBbs030SelectRightUser() {
        return bbs030SelectRightUser__;
    }

    /**
     * @param bbs030SelectRightUser 設定する bbs030SelectRightUser。
     */
    public void setBbs030SelectRightUser(String[] bbs030SelectRightUser) {
        bbs030SelectRightUser__ = bbs030SelectRightUser;
    }

    /**
     * <p>bbs030pluginId を取得します。
     * @return bbs030pluginId
     */
    public String getBbs030pluginId() {
        return bbs030pluginId__;
    }

    /**
     * <p>bbs030pluginId をセットします。
     * @param bbs030pluginId bbs030pluginId
     */
    public void setBbs030pluginId(String bbs030pluginId) {
        bbs030pluginId__ = bbs030pluginId;
    }

    /**
     * <p>bbs030ImageName を取得します。
     * @return bbs030ImageName
     */
    public String getBbs030ImageName() {
        return bbs030ImageName__;
    }

    /**
     * <p>bbs030ImageName をセットします。
     * @param bbs030ImageName bbs030ImageName
     */
    public void setBbs030ImageName(String bbs030ImageName) {
        bbs030ImageName__ = bbs030ImageName;
    }
    /**
     * <p>bbs030ImageSaveName を取得します。
     * @return bbs030ImageSaveName
     */
    public String getBbs030ImageSaveName() {
        return bbs030ImageSaveName__;
    }

    /**
     * <p>bbs030ImageSaveName をセットします。
     * @param bbs030ImageSaveName bbs030ImageSaveName
     */
    public void setBbs030ImageSaveName(String bbs030ImageSaveName) {
        bbs030ImageSaveName__ = bbs030ImageSaveName;
    }

    /**
     * <p>bbs030BinSid を取得します。
     * @return bbs030BinSid
     */
    public Long getBbs030BinSid() {
        return bbs030BinSid__;
    }

    /**
     * <p>bbs030BinSid をセットします。
     * @param bbs030BinSid bbs030BinSid
     */
    public void setBbs030BinSid(Long bbs030BinSid) {
        bbs030BinSid__ = bbs030BinSid;
    }

    /**
     * <p>bbs030SelectLeftUserRead を取得します。
     * @return bbs030SelectLeftUserRead
     */
    public String[] getBbs030SelectLeftUserRead() {
        return bbs030SelectLeftUserRead__;
    }

    /**
     * <p>bbs030SelectLeftUserRead をセットします。
     * @param bbs030SelectLeftUserRead bbs030SelectLeftUserRead
     */
    public void setBbs030SelectLeftUserRead(String[] bbs030SelectLeftUserRead) {
        bbs030SelectLeftUserRead__ = bbs030SelectLeftUserRead;
    }

    /**
     * <p>bbs030groupSidAdm を取得します。
     * @return bbs030groupSidAdm
     */
    public int getBbs030groupSidAdm() {
        return bbs030groupSidAdm__;
    }

    /**
     * <p>bbs030groupSidAdm をセットします。
     * @param bbs030groupSidAdm bbs030groupSidAdm
     */
    public void setBbs030groupSidAdm(int bbs030groupSidAdm) {
        bbs030groupSidAdm__ = bbs030groupSidAdm;
    }

    /**
     * <p>bbs030GroupListAdm を取得します。
     * @return bbs030GroupListAdm
     */
    public List<LabelValueBean> getBbs030GroupListAdm() {
        return bbs030GroupListAdm__;
    }

    /**
     * <p>bbs030GroupListAdm をセットします。
     * @param bbs030GroupListAdm bbs030GroupListAdm
     */
    public void setBbs030GroupListAdm(List<LabelValueBean> bbs030GroupListAdm) {
        bbs030GroupListAdm__ = bbs030GroupListAdm;
    }

    /**
     * <p>bbs030LeftUserListAdm を取得します。
     * @return bbs030LeftUserListAdm
     */
    public List<UsrLabelValueBean> getBbs030LeftUserListAdm() {
        return bbs030LeftUserListAdm__;
    }

    /**
     * <p>bbs030LeftUserListAdm をセットします。
     * @param bbs030LeftUserListAdm bbs030LeftUserListAdm
     */
    public void setBbs030LeftUserListAdm(List<UsrLabelValueBean> bbs030LeftUserListAdm) {
        bbs030LeftUserListAdm__ = bbs030LeftUserListAdm;
    }

    /**
     * <p>bbs030RightUserListAdm を取得します。
     * @return bbs030RightUserListAdm
     */
    public List<UsrLabelValueBean> getBbs030RightUserListAdm() {
        return bbs030RightUserListAdm__;
    }

    /**
     * <p>bbs030RightUserListAdm をセットします。
     * @param bbs030RightUserListAdm bbs030RightUserListAdm
     */
    public void setBbs030RightUserListAdm(
            List<UsrLabelValueBean> bbs030RightUserListAdm) {
        bbs030RightUserListAdm__ = bbs030RightUserListAdm;
    }

    /**
     * <p>bbs030LeftUserListRead を取得します。
     * @return bbs030LeftUserListRead
     */
    public List<UsrLabelValueBean> getBbs030LeftUserListRead() {
        return bbs030LeftUserListRead__;
    }

    /**
     * <p>bbs030LeftUserListRead をセットします。
     * @param bbs030LeftUserListRead bbs030LeftUserListRead
     */
    public void setBbs030LeftUserListRead(
            List<UsrLabelValueBean> bbs030LeftUserListRead) {
        bbs030LeftUserListRead__ = bbs030LeftUserListRead;
    }

    /**
     * <p>bbs030SelectRightUserAdm を取得します。
     * @return bbs030SelectRightUserAdm
     */
    public String[] getBbs030SelectRightUserAdm() {
        return bbs030SelectRightUserAdm__;
    }

    /**
     * <p>bbs030SelectRightUserAdm をセットします。
     * @param bbs030SelectRightUserAdm bbs030SelectRightUserAdm
     */
    public void setBbs030SelectRightUserAdm(String[] bbs030SelectRightUserAdm) {
        bbs030SelectRightUserAdm__ = bbs030SelectRightUserAdm;
    }

    /**
     * <p>bbs030SelectLeftUserAdm を取得します。
     * @return bbs030SelectLeftUserAdm
     */
    public String[] getBbs030SelectLeftUserAdm() {
        return bbs030SelectLeftUserAdm__;
    }

    /**
     * <p>bbs030SelectLeftUserAdm をセットします。
     * @param bbs030SelectLeftUserAdm bbs030SelectLeftUserAdm
     */
    public void setBbs030SelectLeftUserAdm(String[] bbs030SelectLeftUserAdm) {
        bbs030SelectLeftUserAdm__ = bbs030SelectLeftUserAdm;
    }

    /**
     * <p>bbs030mread を取得します。
     * @return bbs030mread
     */
    public String getBbs030mread() {
        return bbs030mread__;
    }

    /**
     * <p>bbs030mread をセットします。
     * @param bbs030mread bbs030mread
     */
    public void setBbs030mread(String bbs030mread) {
        bbs030mread__ = bbs030mread;
    }

    /**
     * <p>bbs030template を取得します。
     * @return bbs030template
     */
    public String getBbs030template() {
        return bbs030template__;
    }

    /**
     * <p>bbs030template をセットします。
     * @param bbs030template bbs030template
     */
    public void setBbs030template(String bbs030template) {
        bbs030template__ = bbs030template;
    }

    /**
     * <p>bbs030templateHtml を取得します。
     * @return bbs030templateHtml
     */
    public String getBbs030templateHtml() {
        return bbs030templateHtml__;
    }

    /**
     * <p>bbs030templateHtml をセットします。
     * @param bbs030templateHtml bbs030templateHtml
     */
    public void setBbs030templateHtml(String bbs030templateHtml) {
        bbs030templateHtml__ = bbs030templateHtml;
    }

    /**
     * <p>bbs030templateWriteKbn を取得します。
     * @return bbs030templateWriteKbn
     */
    public int getBbs030templateWriteKbn() {
        return bbs030templateWriteKbn__;
    }

    /**
     * <p>bbs030templateWriteKbn をセットします。
     * @param bbs030templateWriteKbn bbs030templateWriteKbn
     */
    public void setBbs030templateWriteKbn(int bbs030templateWriteKbn) {
        bbs030templateWriteKbn__ = bbs030templateWriteKbn;
    }

    /**
     * <p>bbs030templateKbn を取得します。
     * @return bbs030templateKbn
     */
    public int getBbs030templateKbn() {
        return bbs030templateKbn__;
    }

    /**
     * <p>bbs030templateKbn をセットします。
     * @param bbs030templateKbn bbs030templateKbn
     */
    public void setBbs030templateKbn(int bbs030templateKbn) {
        bbs030templateKbn__ = bbs030templateKbn;
    }

    /**
     * <p>bbs030templateType を取得します。
     * @return bbs030templateType
     */
    public int getBbs030templateType() {
        return bbs030templateType__;
    }

    /**
     * <p>bbs030templateType をセットします。
     * @param bbs030templateType bbs030templateType
     */
    public void setBbs030templateType(int bbs030templateType) {
        bbs030templateType__ = bbs030templateType;
    }

    /**
     * <p>bbs030diskSize__ を取得します。
     * @return bbs030diskSize
     */
   public int getBbs030diskSize() {
        return bbs030diskSize__;
    }

   /**
    * <p>bbs030diskSize をセットします。
    * @param bbs030diskSize bbs030diskSize
    */
    public void setBbs030diskSize(int bbs030diskSize) {
        bbs030diskSize__ = bbs030diskSize;
    }

    /**
     * <p>bbs030diskSizeLimit を取得します。
     * @return bbs030diskSizeLimit
     */
    public String getBbs030diskSizeLimit() {
        return bbs030diskSizeLimit__;
    }

    /**
     * <p>bbs030diskSizeLimit をセットします。
     * @param bbs030diskSizeLimit bbs030diskSizeLimit
     */
    public void setBbs030diskSizeLimit(String bbs030diskSizeLimit) {
        bbs030diskSizeLimit__ = bbs030diskSizeLimit;
    }

    /**
     * <p>bbs030warnDisk を取得します。
     * @return bbs030warnDisk
     */
    public int getBbs030warnDisk() {
        return bbs030warnDisk__;
    }

    /**
     * <p>bbs030warnDisk をセットします。
     * @param bbs030warnDisk bbs030warnDisk
     */
    public void setBbs030warnDisk(int bbs030warnDisk) {
        bbs030warnDisk__ = bbs030warnDisk;
    }

    /**
     * <p>bbs030warnDiskThreshold を取得します。
     * @return bbs030warnDiskThreshold
     */
    public int getBbs030warnDiskThreshold() {
        return bbs030warnDiskThreshold__;
    }

    /**
     * <p>bbs030warnDiskThreshold をセットします。
     * @param bbs030warnDiskThreshold bbs030warnDiskThreshold
     */
    public void setBbs030warnDiskThreshold(int bbs030warnDiskThreshold) {
        bbs030warnDiskThreshold__ = bbs030warnDiskThreshold;
    }

    /**
     * <p>warnDiskThresholdList を取得します。
     * @return warnDiskThresholdList
     */
    public List<LabelValueBean> getWarnDiskThresholdList() {
        return warnDiskThresholdList__;
    }

    /**
     * <p>warnDiskThresholdList をセットします。
     * @param warnDiskThresholdList warnDiskThresholdList
     */
    public void setWarnDiskThresholdList(List<LabelValueBean> warnDiskThresholdList) {
        warnDiskThresholdList__ = warnDiskThresholdList;
    }

    /**
     * <p>bbs030LimitDisable を取得します。
     * @return bbs030LimitDisable
     */
    public int getBbs030LimitDisable() {
        return bbs030LimitDisable__;
    }

    /**
     * <p>bbs030LimitDisable をセットします。
     * @param bbs030LimitDisable bbs030LimitDisable
     */
    public void setBbs030LimitDisable(int bbs030LimitDisable) {
        bbs030LimitDisable__ = bbs030LimitDisable;
    }

    /**
     * <p>bbs030Limit を取得します。
     * @return bbs030Limit
     */
    public int getBbs030Limit() {
        return bbs030Limit__;
    }

    /**
     * <p>bbs030Limit をセットします。
     * @param bbs030Limit bbs030Limit
     */
    public void setBbs030Limit(int bbs030Limit) {
        bbs030Limit__ = bbs030Limit;
    }

    /**
     * <p>bbs030LimitDate を取得します。
     * @return bbs030LimitDate
     */
    public String getBbs030LimitDate() {
        return bbs030LimitDate__;
    }

    /**
     * <p>bbs030LimitDate をセットします。
     * @param bbs030LimitDate bbs030LimitDate
     */
    public void setBbs030LimitDate(String bbs030LimitDate) {
        bbs030LimitDate__ = bbs030LimitDate;
    }

    /**
     * <p>bbs030TimeUnitを取得します。
     * @return bbs030TimeUnit
     * */
    public int getBbs030TimeUnit() {
        return bbs030TimeUnit__;
    }

    /**
     * <p>bbs030TimeUnitをセットします。
     * @param bbs030TimeUnit bbs030TimeUnit
     * */
    public void setBbs030TimeUnit(int bbs030TimeUnit) {
        bbs030TimeUnit__ = bbs030TimeUnit;
    }

    /**
     * <p>bbs030Keep を取得します。
     * @return bbs030Keep
     */
    public int getBbs030Keep() {
        return bbs030Keep__;
    }

    /**
     * <p>bbs030Keep をセットします。
     * @param bbs030Keep bbs030Keep
     */
    public void setBbs030Keep(int bbs030Keep) {
        bbs030Keep__ = bbs030Keep;
    }

    /**
     * <p>bbs030KeepDateY を取得します。
     * @return bbs030KeepDateY
     */
    public int getBbs030KeepDateY() {
        return bbs030KeepDateY__;
    }

    /**
     * <p>bbs030KeepDateY をセットします。
     * @param bbs030KeepDateY bbs030KeepDateY
     */
    public void setBbs030KeepDateY(int bbs030KeepDateY) {
        bbs030KeepDateY__ = bbs030KeepDateY;
    }

    /**
     * <p>bbs030KeepDateM を取得します。
     * @return bbs030KeepDateM
     */
    public int getBbs030KeepDateM() {
        return bbs030KeepDateM__;
    }

    /**
     * <p>bbs030KeepDateM をセットします。
     * @param bbs030KeepDateM bbs030KeepDateM
     */
    public void setBbs030KeepDateM(int bbs030KeepDateM) {
        bbs030KeepDateM__ = bbs030KeepDateM;
    }

    /**
     * <p>bbs030KeepDateYLabel を取得します。
     * @return bbs030KeepDateYLabel
     */
    public List<LabelValueBean> getBbs030KeepDateYLabel() {
        return bbs030KeepDateYLabel__;
    }

    /**
     * <p>bbs030KeepDateYLabel をセットします。
     * @param bbs030KeepDateYLabel bbs030KeepDateYLabel
     */
    public void setBbs030KeepDateYLabel(List<LabelValueBean> bbs030KeepDateYLabel) {
        bbs030KeepDateYLabel__ = bbs030KeepDateYLabel;
    }

    /**
     * <p>bbs030KeepDateMLabel を取得します。
     * @return bbs030KeepDateMLabel
     */
    public List<LabelValueBean> getBbs030KeepDateMLabel() {
        return bbs030KeepDateMLabel__;
    }

    /**
     * <p>bbs030KeepDateMLabel をセットします。
     * @param bbs030KeepDateMLabel bbs030KeepDateMLabel
     */
    public void setBbs030KeepDateMLabel(List<LabelValueBean> bbs030KeepDateMLabel) {
        bbs030KeepDateMLabel__ = bbs030KeepDateMLabel;
    }

    /**
     * <p>bbs030DspAtdelFlg を取得します。
     * @return bbs030DspAtdelFlg
     */
    public int getBbs030DspAtdelFlg() {
        return bbs030DspAtdelFlg__;
    }

    /**
     * <p>bbs030DspAtdelFlg をセットします。
     * @param bbs030DspAtdelFlg bbs030DspAtdelFlg
     */
    public void setBbs030DspAtdelFlg(int bbs030DspAtdelFlg) {
        bbs030DspAtdelFlg__ = bbs030DspAtdelFlg;
    }

    /**
     * <p>bbs030DspAtdelYear を取得します。
     * @return bbs030DspAtdelYear
     */
    public int getBbs030DspAtdelYear() {
        return bbs030DspAtdelYear__;
    }

    /**
     * <p>bbs030DspAtdelYear をセットします。
     * @param bbs030DspAtdelYear bbs030DspAtdelYear
     */
    public void setBbs030DspAtdelYear(int bbs030DspAtdelYear) {
        bbs030DspAtdelYear__ = bbs030DspAtdelYear;
    }

    /**
     * <p>bbs030DspAtdelMonth を取得します。
     * @return bbs030DspAtdelMonth
     */
    public int getBbs030DspAtdelMonth() {
        return bbs030DspAtdelMonth__;
    }

    /**
     * <p>bbs030DspAtdelMonth をセットします。
     * @param bbs030DspAtdelMonth bbs030DspAtdelMonth
     */
    public void setBbs030DspAtdelMonth(int bbs030DspAtdelMonth) {
        bbs030DspAtdelMonth__ = bbs030DspAtdelMonth;
    }

    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     */
    public String getTempDirId() {
        return tempDirId__;
    }

    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     */
    public void setTempDirId(String tempDirId) {
        tempDirId__ = tempDirId;
    }
    /**
     * <p>bbs030ScrollPosition を取得します。
     * @return bbs030ScrollPosition
     */
    public String getBbs030ScrollPosition() {
        return bbs030ScrollPosition__;
    }

    /**
     * <p>bbs030ScrollPosition をセットします。
     * @param bbs030ScrollPosition bbs030ScrollPosition
     */
    public void setBbs030ScrollPosition(String bbs030ScrollPosition) {
        bbs030ScrollPosition__ = bbs030ScrollPosition;
    }

    /**
     * <p>bbs190ScrollPosition を取得します。
     * @return bbs190ScrollPosition
     */
    public String getBbs190ScrollPosition() {
        return bbs190ScrollPosition__;
    }

    /**
     * <p>bbs190ScrollPosition をセットします。
     * @param bbs190ScrollPosition bbs190ScrollPosition
     */
    public void setBbs190ScrollPosition(String bbs190ScrollPosition) {
        bbs190ScrollPosition__ = bbs190ScrollPosition;
    }

    /**
     * <p>bbs030UserListWriteReg を取得します。
     * @return bbs030UserListWriteReg
     */
    public String[] getBbs030UserListWriteReg() {
        return bbs030UserListWriteReg__;
    }

    /**
     * <p>bbs030UserListWriteReg をセットします。
     * @param bbs030UserListWriteReg bbs030UserListWriteReg
     */
    public void setBbs030UserListWriteReg(String[] bbs030UserListWriteReg) {
        bbs030UserListWriteReg__ = bbs030UserListWriteReg;
    }

    /**
     * <p>bbs030UserListReadReg を取得します。
     * @return bbs030UserListReadReg
     */
    public String[] getBbs030UserListReadReg() {
        return bbs030UserListReadReg__;
    }

    /**
     * <p>bbs030UserListReadReg をセットします。
     * @param bbs030UserListReadReg bbs030UserListReadReg
     */
    public void setBbs030UserListReadReg(String[] bbs030UserListReadReg) {
        bbs030UserListReadReg__ = bbs030UserListReadReg;
    }

    /**
     * <p>bbs030UserListAdmReg を取得します。
     * @return bbs030UserListAdmReg
     */
    public String[] getBbs030UserListAdmReg() {
        return bbs030UserListAdmReg__;
    }

    /**
     * <p>bbs030UserListAdmReg をセットします。
     * @param bbs030UserListAdmReg bbs030UserListAdmReg
     */
    public void setBbs030UserListAdmReg(String[] bbs030UserListAdmReg) {
        bbs030UserListAdmReg__ = bbs030UserListAdmReg;
    }

    /**
     * <p>bbs030BanUserSidList を取得します。
     * @return bbs030BanUserSidList
     */
    public List<Integer> getBbs030BanUserSidList() {
        return bbs030BanUserSidList__;
    }

    /**
     * <p>bbs030BanUserSidList をセットします。
     * @param bbs030BanUserSidList bbs030BanUserSidList
     */
    public void setBbs030BanUserSidList(List<Integer> bbs030BanUserSidList) {
        bbs030BanUserSidList__ = bbs030BanUserSidList;
    }

    /**
     * <p>bbs030BanGroupSidList を取得します。
     * @return bbs030BanGroupSidList
     */
    public List<Integer> getBbs030BanGroupSidList() {
        return bbs030BanGroupSidList__;
    }

    /**
     * <p>bbs030BanGroupSidList をセットします。
     * @param bbs030BanGroupSidList bbs030BanGroupSidList
     */
    public void setBbs030BanGroupSidList(List<Integer> bbs030BanGroupSidList) {
        bbs030BanGroupSidList__ = bbs030BanGroupSidList;
    }

    /**
     * <p>bbs030DisableGroupSidList を取得します。
     * @return bbs030DisableGroupSidList
     */
    public List<Integer> getBbs030DisableGroupSidList() {
        return bbs030DisableGroupSidList__;
    }

    /**
     * <p>bbs030DisableGroupSidList をセットします。
     * @param bbs030DisableGroupSidList bbs030DisableGroupSidList
     */
    public void setBbs030DisableGroupSidList(List<Integer> bbs030DisableGroupSidList) {
        bbs030DisableGroupSidList__ = bbs030DisableGroupSidList;
    }

    /**
     * <p>bbs030AdaptChildMemFlg を取得します。
     * @return bbs030AdaptChildMemFlg
     */
    public int getBbs030AdaptChildMemFlg() {
        return bbs030AdaptChildMemFlg__;
    }

    /**
     * <p>bbs030AdaptChildMemFlg をセットします。
     * @param bbs030AdaptChildMemFlg bbs030AdaptChildMemFlg
     */
    public void setBbs030AdaptChildMemFlg(int bbs030AdaptChildMemFlg) {
        bbs030AdaptChildMemFlg__ = bbs030AdaptChildMemFlg;
    }

    /**
     * <p>bbs030HaveChildForumFlg を取得します。
     * @return bbs030HaveChildForumFlg
     */
    public int getBbs030HaveChildForumFlg() {
        return bbs030HaveChildForumFlg__;
    }

    /**
     * <p>bbs030HaveChildForumFlg をセットします。
     * @param bbs030HaveChildForumFlg bbs030HaveChildForumFlg
     */
    public void setBbs030HaveChildForumFlg(int bbs030HaveChildForumFlg) {
        bbs030HaveChildForumFlg__ = bbs030HaveChildForumFlg;
    }

    /**
     * <p>bbs030FollowParentMemFlg を取得します。
     * @return bbs030FollowParentMemFlg
     */
    public int getBbs030FollowParentMemFlg() {
        return bbs030FollowParentMemFlg__;
    }

    /**
     * <p>bbs030FollowParentMemFlg をセットします。
     * @param bbs030FollowParentMemFlg bbs030FollowParentMemFlg
     */
    public void setBbs030FollowParentMemFlg(int bbs030FollowParentMemFlg) {
        bbs030FollowParentMemFlg__ = bbs030FollowParentMemFlg;
    }

    /**
     * <p>bbs030UnlimitedFlg を取得します。
     * @return bbs030UnlimitedFlg
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030UnlimitedFlg__
     */
    public int getBbs030UnlimitedFlg() {
        return bbs030UnlimitedFlg__;
    }

    /**
     * <p>bbs030UnlimitedFlg をセットします。
     * @param bbs030UnlimitedFlg bbs030UnlimitedFlg
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030UnlimitedFlg__
     */
    public void setBbs030UnlimitedFlg(int bbs030UnlimitedFlg) {
        bbs030UnlimitedFlg__ = bbs030UnlimitedFlg;
    }


    /**
     * <p>bbs030memberSelect を取得します。
     * @return bbs030memberSelect
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030memberSelect__
     */
    public Bbs030ForumMemberSelector getBbs030memberSelect() {
        return bbs030memberSelect__;
    }

    /**
     * <p>bbs030memberSelect をセットします。
     * @param bbs030memberSelect bbs030memberSelect
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030memberSelect__
     */
    public void setBbs030memberSelect(Bbs030ForumMemberSelector bbs030memberSelect) {
        bbs030memberSelect__ = bbs030memberSelect;
    }

    /**
     * <p>bbs030adminSelect を取得します。
     * @return bbs030adminSelect
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030adminSelect__
     */
    public Bbs030ForumAdminMemberSelector getBbs030adminSelect() {
        return bbs030adminSelect__;
    }

    /**
     * <p>bbs030adminSelect をセットします。
     * @param bbs030adminSelect bbs030adminSelect
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030adminSelect__
     */
    public void setBbs030adminSelect(Bbs030ForumAdminMemberSelector bbs030adminSelect) {
        bbs030adminSelect__ = bbs030adminSelect;
    }

    /**
     * <p>bbs030memberSelectForRoot を取得します。
     * @return bbs030memberSelectForRoot
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030memberSelectForRoot__
     */
    public UserGroupSelector getBbs030memberSelectForRoot() {
        return bbs030memberSelectForRoot__;
    }

    /**
     * <p>bbs030memberSelectForRoot をセットします。
     * @param bbs030memberSelectForRoot bbs030memberSelectForRoot
     * @see jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel#bbs030memberSelectForRoot__
     */
    public void setBbs030memberSelectForRoot(
            UserGroupSelector bbs030memberSelectForRoot) {
        bbs030memberSelectForRoot__ = bbs030memberSelectForRoot;
    }

}
