package jp.groupsession.v2.bbs.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 掲示板の表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinDspModel extends AbstractModel {

    /** Newアイコン表示フラグ 非表示 */
    public static final int NEWFLG_NOTVIEW = 0;
    /** Newアイコン表示フラグ 表示 */
    public static final int NEWFLG_VIEW = 1;
    /** 未読/既読フラグ 未読 */
    public static final int READFLG_NOREAD = 0;
    /** 未読/既読フラグ 既読 */
    public static final int READFLG_READ = 1;
    /** 投稿更新フラグ 新規 */
    public static final int WRITEUPDATEFLG_NEW = 0;
    /** 投稿更新フラグ 更新 */
    public static final int WRITEUPDATEFLG_UPD = 1;
    /** ボタン表示フラグ 非表示 */
    public static final int SHOWBTNFLG_NO = 0;
    /** ボタン表示フラグ 表示 */
    public static final int SHOWBTNFLG_YES = 1;
    /** ボタン欄表示フラグ 非表示 */
    public static final boolean SHOWALLBTNFLG_NO = false;
    /** ボタン欄表示フラグ 表示 */
    public static final boolean SHOWALLBTNFLG_YES = true;

    /** フォーラムSID */
    private int bfiSid__ = 0;
    /** スレッドSID */
    private int btiSid__ = 0;
    /** 投稿SID */
    private int bwiSid__ = 0;
    /** バイナリSID */
    private Long binSid__ = new Long(0);

    /** フォーラム名称 */
    private String bfiName__ = null;
    /** フォーラム ディスク使用量 */
    private long bfsSize__ = 0;
    /** スレッドタイトル */
    private String btiTitle__  = null;
    /** スレッド掲示期限の有無 */
    private int bfiLimitOn__ = GSConstBulletin.THREAD_ENABLE;
    /** スレッド掲示期限 例外 */
    private int btiLimitException__ = GSConstBulletin.THREAD_NOEXCEPTION;
    /** スレッド掲示期限 */
    private int btiLimit__ = GSConstBulletin.THREAD_LIMIT_NO;
    /** スレッド掲示期間 開始 */
    private UDate btiLimitFrDate__ = null;
    /** スレッド掲示期間 開始(表示用) */
    private String strBtiLimitFrDate__ = null;
    /** スレッド掲示期間 終了 */
    private UDate btiLimitDate__ = null;
    /** スレッド掲示期間 終了(表示用) */
    private String strBtiLimitDate__ = null;
    /** スレッド掲示終了フラグ 0:終了していない  1:終了している */
    private int threadFinFlg__ = 0;
    /** 時間単位 */
    private int bfiMinDiv__ = 0;
    /** スレッド ディスク使用量 */
    private long btsSize__ = 0;
    /** スレッド 添付ファイルフラグ */
    private int btsTempflg__ = 0;
    /** ユーザSID */
    private int userSid__ = 0;
    /** ユーザ名称 */
    private String userName__ = null;
    /** 写真公開フラグ */
    private int userPictKf__ = 0;
    /** フォーラム表示順 */
    private int bfiSort__ = 0;

    /** フォーラムコメント */
    private String bfiCmt__ = null;
    /** フォーラムコメント(表示用) */
    private String bfiCmtView__ = null;
    /** 投稿タイプ */
    private int bwiType__ = GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN;
    /** 投稿内容 */
    private String bwiValue__ = null;
    /** 投稿内容(表示用) */
    private String bwiValueView__ = null;
    /** 投稿内容 プレーンテキスト形式 */
    private String bwiValuePlain__ = null;
    /** 投稿内容 プレーンテキスト形式(表示用) */
    private String bwiValuePlainView__ = null;
    /** 投稿・親子判別フラグ 1:親 0:子 */
    private int bwiParentFlg__ = 0;

    /** スレッド数 */
    private int bfsThreCnt__ = 0;
    /** 投稿数 */
    private int writeCnt__ = 0;
    /** ユーザ状態区分 */
    private int userJkbn__ = 0;
    /** ユーザ有効区分 */
    private int userYukoKbn__ = 0;

    /** 最新書き込み日時 */
    private UDate writeDate__ = null;
    /** 最新書き込み日時(文字列) */
    private String strWriteDate__ = null;

    /** 登録者SID */
    private int addUserSid__ = 0;

    /** 投稿者グループSID */
    private int grpSid__ = 0;
    /** 投稿者グループ名称 */
    private String grpName__ = null;
    /** 投稿者グループ 状態区分 */
    private int grpJkbn__ = 0;

    /** 投稿 登録日時 */
    private UDate bwiAdate__ = null;
    /** 投稿 登録日時(文字列) */
    private String strBwiAdate__ = null;
    /** 投稿 更新日時 */
    private UDate bwiEdate__ = null;
    /** 投稿 更新日時(文字列) */
    private String strBwiEdate__ = null;

    /** 投稿者写真ファイル名 */
    private String photoFileName__ = null;
    /** 投稿者写真ファイルパス */
    private String photoFilePath__ = null;
    /** 投稿者写真ファイルSID */
    private Long photoFileSid__ = new Long(0);
    /** 添付ファイル情報 */
    private List < CmnBinfModel > tmpFileList__ = null;

    /** 既読件数 */
    private int readedCnt__ = 0;
    /** Newアイコン表示フラグ 0:非表示 1:表示 */
    private int newFlg__ = NEWFLG_NOTVIEW;
    /** 未読/既読フラグ 0:未読 1:既読 */
    private int readFlg__ = READFLG_NOREAD;
    /** 投稿更新フラグ 0:新規 1:更新 */
    private int writeUpdateFlg__ = WRITEUPDATEFLG_NEW;
    /** ボタン表示フラグ 0:非表示 1:表示 */
    private int showBtnFlg__ = SHOWBTNFLG_NO;
    /** スレッド作成時投稿フラグ 0:通常の投稿 1:スレッド作成時の投稿 */
    private int thdWriteFlg__ = 0;

    /** スレッド返信 0:可能 1:不可能 */
    private int bfiReply__ = GSConstBulletin.BBS_THRE_REPLY_YES;
    /** 新規ユーザスレッド閲覧状態 0:未読 1:既読 */
    private int bfiRead__ = GSConstBulletin.NEWUSER_THRE_VIEW_NO;
    /** 「全て既読にする」 0:許可する 1:許可しない */
    private int bfiMread__ = GSConstBulletin.BBS_FORUM_MREAD_NO;
    /** スレッドテンプレート区分 0:使用しない 1:使用する */
    private int bfiTemplateKbn__ = GSConstBulletin.BBS_THRE_TEMPLATE_NO;
    /** スレッドテンプレート */
    private String bfiTemplate__ = null;
    /** スレッドテンプレートタイプ */
    private int bfiTemplateType__ = GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN;
    /** スレッドテンプレート投稿使用区分 0:使用しない 1:使用する */
    private int bfiTemplateWrite__ = GSConstBulletin.BBS_THRE_TEMPLATE_WRITE_NO;
    /** フォーラム  ディスク容量制限 */
    private int bfiDisk__;
    /** フォーラム ディスク容量上限 */
    private int bfiDiskSize__;
    /** フォーラム ディスク容量警告 */
    private int bfiWarnDisk__;
    /** フォーラム ディスク容量警告 閾値 */
    private int bfiWarnDiskTh__;
    /** 掲示期間 無制限*/
    private int bfiUnlimited__ = GSConstBulletin.BFI_UNLIMITED_YES;
    /** 掲示期間 初期値 */
    private int bfiLimit__ = GSConstBulletin.THREAD_LIMIT_NO;
    /** 掲示期間日数 初期値 */
    private int bfiLimitDate__;
    /** スレッド保存期間設定 */
    private int bfiKeep__ = GSConstBulletin.THREAD_KEEP_NO;
    /** スレッド保存期間 経過年 */
    private int bfiKeepDateY__;
    /** スレッド保存期間 経過月 */
    private int bfiKeepDateM__;

    /** バイナリSID */
    private Long imgBinSid__;

    /** 掲示予定リンク表示フラグ */
    private int rsvThreCnt__;

    /** 重要度 */
    private int bfiThreImportance__ = 0;

    /** 親フォーラムSID */
    private int parentForumSid__;
    /** 階層レベル */
    private int forumLevel__;

    /** 子フォーラム数 */
    private int numberOfChild__ = 0;

    /** メンバー親フォーラム準拠フラグ */
    private int followParentMemFlg__ = GSConstBulletin.FOLLOW_PARENT_MEMBER_YES;


    /**
     * @return userPictKf__ を戻します。
     */
    public int getUserPictKf() {
        return userPictKf__;
    }
    /**
     * <p>bfiRead を取得します。
     * @return bfiRead
     */
    public int getBfiRead() {
        return bfiRead__;
    }
    /**
     * <p>bfiRead をセットします。
     * @param bfiRead bfiRead
     */
    public void setBfiRead(int bfiRead) {
        bfiRead__ = bfiRead;
    }
    /**
     * <p>bfiReply を取得します。
     * @return bfiReply
     */
    public int getBfiReply() {
        return bfiReply__;
    }
    /**
     * <p>bfiReply をセットします。
     * @param bfiReply bfiReply
     */
    public void setBfiReply(int bfiReply) {
        bfiReply__ = bfiReply;
    }
    /**
     * @param userPictKf 設定する userPictKf__。
     */
    public void setUserPictKf(int userPictKf) {
        userPictKf__ = userPictKf;
    }
    /**
     * @return userSid__ を戻します。
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * @param userSid 設定する userSid__。
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * @return bfiCmt を戻します。
     */
    public String getBfiCmt() {
        return bfiCmt__;
    }
    /**
     * @param bfiCmt 設定する bfiCmt。
     */
    public void setBfiCmt(String bfiCmt) {
        bfiCmt__ = bfiCmt;
    }
    /**
     * @return bfiCmtView を戻します。
     */
    public String getBfiCmtView() {
        return bfiCmtView__;
    }
    /**
     * @param bfiCmtView 設定する bfiCmtView。
     */
    public void setBfiCmtView(String bfiCmtView) {
        bfiCmtView__ = bfiCmtView;
    }
    /**
     * @return bfiName を戻します。
     */
    public String getBfiName() {
        return bfiName__;
    }
    /**
     * @param bfiName 設定する bfiName。
     */
    public void setBfiName(String bfiName) {
        bfiName__ = bfiName;
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
     * @return bfsThreCnt を戻します。
     */
    public int getBfsThreCnt() {
        return bfsThreCnt__;
    }
    /**
     * @param bfsThreCnt 設定する bfsThreCnt。
     */
    public void setBfsThreCnt(int bfsThreCnt) {
        bfsThreCnt__ = bfsThreCnt;
    }
    /**
     * <p>writeCnt を取得します。
     * @return writeCnt
     */
    public int getWriteCnt() {
        return writeCnt__;
    }
    /**
     * <p>writeCnt をセットします。
     * @param writeCnt writeCnt
     */
    public void setWriteCnt(int writeCnt) {
        writeCnt__ = writeCnt;
    }
    /**
     * <p>strWriteDate を取得します。
     * @return strWriteDate
     */
    public String getStrWriteDate() {
        return strWriteDate__;
    }
    /**
     * <p>strWriteDate をセットします。
     * @param strWriteDate strWriteDate
     */
    public void setStrWriteDate(String strWriteDate) {
        strWriteDate__ = strWriteDate;
    }
    /**
     * <p>writeDate を取得します。
     * @return writeDate
     */
    public UDate getWriteDate() {
        return writeDate__;
    }
    /**
     * <p>writeDate をセットします。
     * @param writeDate writeDate
     */
    public void setWriteDate(UDate writeDate) {
        writeDate__ = writeDate;
    }
    /**
     * @return readedCnt を戻します。
     */
    public int getReadedCnt() {
        return readedCnt__;
    }
    /**
     * @param readedCnt 設定する readedCnt。
     */
    public void setReadedCnt(int readedCnt) {
        readedCnt__ = readedCnt;
    }
    /**
     * @return newFlg を戻します。
     */
    public int getNewFlg() {
        return newFlg__;
    }
    /**
     * @param newFlg 設定する newFlg。
     */
    public void setNewFlg(int newFlg) {
        newFlg__ = newFlg;
    }
    /**
     * @return readFlg を戻します。
     */
    public int getReadFlg() {
        return readFlg__;
    }
    /**
     * @param readFlg 設定する readFlg。
     */
    public void setReadFlg(int readFlg) {
        readFlg__ = readFlg;
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
     * <p>btiLimit を取得します。
     * @return btiLimit
     */
    public int getBtiLimit() {
        return btiLimit__;
    }
    /**
     * <p>btiLimit をセットします。
     * @param btiLimit btiLimit
     */
    public void setBtiLimit(int btiLimit) {
        btiLimit__ = btiLimit;
    }
    /**
     * <p>btiLimitDate を取得します。
     * @return btiLimitDate
     */
    public UDate getBtiLimitDate() {
        return btiLimitDate__;
    }
    /**
     * <p>btiLimitDate をセットします。
     * @param btiLimitDate btiLimitDate
     */
    public void setBtiLimitDate(UDate btiLimitDate) {
        btiLimitDate__ = btiLimitDate;
    }
    /**
     * @return btiTitle を戻します。
     */
    public String getBtiTitle() {
        return btiTitle__;
    }
    /**
     * @param btiTitle 設定する btiTitle。
     */
    public void setBtiTitle(String btiTitle) {
        btiTitle__ = btiTitle;
    }
    /**
     * <p>bfiLimitOn を取得します。
     * @return bfiLimitOn
     */
    public int getBfiLimitOn() {
        return bfiLimitOn__;
    }
    /**
     * <p>bfiLimitOn をセットします。
     * @param bfiLimitOn bfiLimitOn
     */
    public void setBfiLimitOn(int bfiLimitOn) {
        bfiLimitOn__ = bfiLimitOn;
    }
    /**
     * @return userJkbn を戻します。
     */
    public int getUserJkbn() {
        return userJkbn__;
    }
    /**
     * @param userJkbn 設定する userJkbn。
     */
    public void setUserJkbn(int userJkbn) {
        userJkbn__ = userJkbn;
    }
    /**
     *  @return userYukoKbn__
     */
    public int getUserYukoKbn() {
        return userYukoKbn__;
    }
    /**
     *  @param userYukoKbn ユーザ有効区分
     */
    public void setUserYukoKbn(int userYukoKbn) {
        userYukoKbn__ = userYukoKbn;
    }
    /**
     * @return userName を戻します。
     */
    public String getUserName() {
        return userName__;
    }
    /**
     * @param userName 設定する userName。
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }
    /**
     * @return bwiAdate を戻します。
     */
    public UDate getBwiAdate() {
        return bwiAdate__;
    }
    /**
     * @param bwiAdate 設定する bwiAdate。
     */
    public void setBwiAdate(UDate bwiAdate) {
        bwiAdate__ = bwiAdate;
    }
    /**
     * <p>addUserSid を取得します。
     * @return addUserSid
     */
    public int getAddUserSid() {
        return addUserSid__;
    }
    /**
     * <p>addUserSid をセットします。
     * @param addUserSid addUserSid
     */
    public void setAddUserSid(int addUserSid) {
        addUserSid__ = addUserSid;
    }
    /**
     * @return bwiEdate を戻します。
     */
    public UDate getBwiEdate() {
        return bwiEdate__;
    }
    /**
     * @param bwiEdate 設定する bwiEdate。
     */
    public void setBwiEdate(UDate bwiEdate) {
        bwiEdate__ = bwiEdate;
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
     * <p>bwiType を取得します。
     * @return bwiType
     */
    public int getBwiType() {
        return bwiType__;
    }
    /**
     * <p>bwiType をセットします。
     * @param bwiType bwiType
     */
    public void setBwiType(int bwiType) {
        bwiType__ = bwiType;
    }
    /**
     * @return bwiValue を戻します。
     */
    public String getBwiValue() {
        return bwiValue__;
    }
    /**
     * @param bwiValue 設定する bwiValue。
     */
    public void setBwiValue(String bwiValue) {
        bwiValue__ = bwiValue;
    }
    /**
     * @return bwiValueView を戻します。
     */
    public String getBwiValueView() {
        return bwiValueView__;
    }
    /**
     * @param bwiValueView 設定する bwiValueView。
     */
    public void setBwiValueView(String bwiValueView) {
        bwiValueView__ = bwiValueView;
    }
    /**
     * <p>bwiValuePlain を取得します。
     * @return bwiValuePlain
     */
    public String getBwiValuePlain() {
        return bwiValuePlain__;
    }
    /**
     * <p>bwiValuePlain をセットします。
     * @param bwiValuePlain bwiValuePlain
     */
    public void setBwiValuePlain(String bwiValuePlain) {
        bwiValuePlain__ = bwiValuePlain;
    }
    /**
     * <p>bwiValuePlainView を取得します。
     * @return bwiValuePlainView
     */
    public String getBwiValuePlainView() {
        return bwiValuePlainView__;
    }
    /**
     * <p>bwiValuePlainView をセットします。
     * @param bwiValuePlainView bwiValuePlainView
     */
    public void setBwiValuePlainView(String bwiValuePlainView) {
        bwiValuePlainView__ = bwiValuePlainView;
    }
    /**
     * <p>bwiParentFlg を取得します。
     * @return bwiParentFlg
     * @see jp.groupsession.v2.bbs.model.BulletinDspModel#bwiParentFlg__
     */
    public int getBwiParentFlg() {
        return bwiParentFlg__;
    }
    /**
     * <p>bwiParentFlg をセットします。
     * @param bwiParentFlg bwiParentFlg
     * @see jp.groupsession.v2.bbs.model.BulletinDspModel#bwiParentFlg__
     */
    public void setBwiParentFlg(int bwiParentFlg) {
        bwiParentFlg__ = bwiParentFlg;
    }
    /**
     * @return photoFileName を戻します。
     */
    public String getPhotoFileName() {
        return photoFileName__;
    }
    /**
     * @param photoFileName 設定する photoFileName。
     */
    public void setPhotoFileName(String photoFileName) {
        photoFileName__ = photoFileName;
    }
    /**
     * @return strBwiAdate を戻します。
     */
    public String getStrBwiAdate() {
        return strBwiAdate__;
    }
    /**
     * @param strBwiAdate 設定する strBwiAdate。
     */
    public void setStrBwiAdate(String strBwiAdate) {
        strBwiAdate__ = strBwiAdate;
    }
    /**
     * @return strBwiEdate を戻します。
     */
    public String getStrBwiEdate() {
        return strBwiEdate__;
    }
    /**
     * @param strBwiEdate 設定する strBwiEdate。
     */
    public void setStrBwiEdate(String strBwiEdate) {
        strBwiEdate__ = strBwiEdate;
    }
    /**
     * @return tmpFileList を戻します。
     */
    public List < CmnBinfModel > getTmpFileList() {
        return tmpFileList__;
    }
    /**
     * @param tmpFileList 設定する tmpFileList。
     */
    public void setTmpFileList(List < CmnBinfModel > tmpFileList) {
        tmpFileList__ = tmpFileList;
    }
    /**
     * @return writeUpdateFlg を戻します。
     */
    public int getWriteUpdateFlg() {
        return writeUpdateFlg__;
    }
    /**
     * @param writeUpdateFlg 設定する writeUpdateFlg。
     */
    public void setWriteUpdateFlg(int writeUpdateFlg) {
        writeUpdateFlg__ = writeUpdateFlg;
    }
    /**
     * @return showBtnFlg を戻します。
     */
    public int getShowBtnFlg() {
        return showBtnFlg__;
    }
    /**
     * @param showBtnFlg 設定する showBtnFlg。
     */
    public void setShowBtnFlg(int showBtnFlg) {
        showBtnFlg__ = showBtnFlg;
    }
    /**
     * <p>thdWriteFlg を取得します。
     * @return thdWriteFlg
     */
    public int getThdWriteFlg() {
        return thdWriteFlg__;
    }
    /**
     * <p>thdWriteFlg をセットします。
     * @param thdWriteFlg thdWriteFlg
     */
    public void setThdWriteFlg(int thdWriteFlg) {
        thdWriteFlg__ = thdWriteFlg;
    }
    /**
     * <p>bfiSort を取得します。
     * @return bfiSort
     */
    public int getBfiSort() {
        return bfiSort__;
    }
    /**
     * <p>bfiSort をセットします。
     * @param bfiSort bfiSort
     */
    public void setBfiSort(int bfiSort) {
        bfiSort__ = bfiSort;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>photoFileSid を取得します。
     * @return photoFileSid
     */
    public Long getPhotoFileSid() {
        return photoFileSid__;
    }
    /**
     * <p>photoFileSid をセットします。
     * @param photoFileSid photoFileSid
     */
    public void setPhotoFileSid(Long photoFileSid) {
        photoFileSid__ = photoFileSid;
    }
    /**
     * <p>imgBinSid を取得します。
     * @return imgBinSid
     */
    public Long getImgBinSid() {
        return imgBinSid__;
    }
    /**
     * <p>imgBinSid をセットします。
     * @param imgBinSid imgBinSid
     */
    public void setImgBinSid(Long imgBinSid) {
        imgBinSid__ = imgBinSid;
    }
    /**
     * <p>bfiMread を取得します。
     * @return bfiMread
     */
    public int getBfiMread() {
        return bfiMread__;
    }
    /**
     * <p>bfiMread をセットします。
     * @param bfiMread bfiMread
     */
    public void setBfiMread(int bfiMread) {
        bfiMread__ = bfiMread;
    }
    /**
     * <p>bfiTemplate を取得します。
     * @return bfiTemplate
     */
    public String getBfiTemplate() {
        return bfiTemplate__;
    }
    /**
     * <p>bfiTemplate をセットします。
     * @param bfiTemplate bfiTemplate
     */
    public void setBfiTemplate(String bfiTemplate) {
        bfiTemplate__ = bfiTemplate;
    }
    /**
     * <p>bfiTemplateType を取得します。
     * @return bfiTemplateType
     */
    public int getBfiTemplateType() {
        return bfiTemplateType__;
    }
    /**
     * <p>bfiTemplateType をセットします。
     * @param bfiTemplateType bfiTemplateType
     */
    public void setBfiTemplateType(int bfiTemplateType) {
        bfiTemplateType__ = bfiTemplateType;
    }
    /**
     * <p>bfiTemplateWrite を取得します。
     * @return bfiTemplateWrite
     */
    public int getBfiTemplateWrite() {
        return bfiTemplateWrite__;
    }
    /**
     * <p>bfiTemplateWrite をセットします。
     * @param bfiTemplateWrite bfiTemplateWrite
     */
    public void setBfiTemplateWrite(int bfiTemplateWrite) {
        bfiTemplateWrite__ = bfiTemplateWrite;
    }
    /**
     * <p>bfiTemplateKbn を取得します。
     * @return bfiTemplateKbn
     */
    public int getBfiTemplateKbn() {
        return bfiTemplateKbn__;
    }
    /**
     * <p>bfiTemplateKbn をセットします。
     * @param bfiTemplateKbn bfiTemplateKbn
     */
    public void setBfiTemplateKbn(int bfiTemplateKbn) {
        bfiTemplateKbn__ = bfiTemplateKbn;
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
     * <p>grpName を取得します。
     * @return grpName
     */
    public String getGrpName() {
        return grpName__;
    }
    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }
    /**
     * <p>grpJkbn を取得します。
     * @return grpJkbn
     */
    public int getGrpJkbn() {
        return grpJkbn__;
    }
    /**
     * <p>grpJkbn をセットします。
     * @param grpJkbn grpJkbn
     */
    public void setGrpJkbn(int grpJkbn) {
        grpJkbn__ = grpJkbn;
    }
    /**
     * @return photoFilePath
     */
    public String getPhotoFilePath() {
        return photoFilePath__;
    }
    /**
     * @param photoFilePath セットする photoFilePath
     */
    public void setPhotoFilePath(String photoFilePath) {
        photoFilePath__ = photoFilePath;
    }

    /**
     * <p>bfiDisk を取得します。
     * @return bfiDisk
     */
    public int getBfiDisk() {
        return bfiDisk__;
    }

    /**
     * <p>bfiDisk をセットします。
     * @param bfiDisk bfiDisk
     */
    public void setBfiDisk(int bfiDisk) {
        bfiDisk__ = bfiDisk;
    }

    /**
     * <p>bfiDiskSize を取得します。
     * @return bfiDiskSize
     */
    public int getBfiDiskSize() {
        return bfiDiskSize__;
    }

    /**
     * <p>bfiDiskSize をセットします。
     * @param bfiDiskSize bfiDiskSize
     */
    public void setBfiDiskSize(int bfiDiskSize) {
        bfiDiskSize__ = bfiDiskSize;
    }

    /**
     * <p>bfiWarnDisk を取得します。
     * @return bfiWarnDisk
     */
    public int getBfiWarnDisk() {
        return bfiWarnDisk__;
    }

    /**
     * <p>bfiWarnDisk をセットします。
     * @param bfiWarnDisk bfiWarnDisk
     */
    public void setBfiWarnDisk(int bfiWarnDisk) {
        bfiWarnDisk__ = bfiWarnDisk;
    }

    /**
     * <p>bfiWarnDiskTh を取得します。
     * @return bfiWarnDiskTh
     */
    public int getBfiWarnDiskTh() {
        return bfiWarnDiskTh__;
    }

    /**
     * <p>bfiWarnDiskTh をセットします。
     * @param bfiWarnDiskTh bfiWarnDiskTh
     */
    public void setBfiWarnDiskTh(int bfiWarnDiskTh) {
        bfiWarnDiskTh__ = bfiWarnDiskTh;
    }
    /**
     * <p>bfsSize を取得します。
     * @return bfsSize
     */
    public long getBfsSize() {
        return bfsSize__;
    }
    /**
     * <p>bfsSize をセットします。
     * @param bfsSize bfsSize
     */
    public void setBfsSize(long bfsSize) {
        bfsSize__ = bfsSize;
    }
    /**
     * <p>btsSize を取得します。
     * @return btsSize
     */
    public long getBtsSize() {
        return btsSize__;
    }
    /**
     * <p>btsSize をセットします。
     * @param btsSize btsSize
     */
    public void setBtsSize(long btsSize) {
        btsSize__ = btsSize;
    }

    /**
     * <br>[機  能] フォーラム ディスク使用量(表示用)を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return フォーラム ディスク使用量(表示用)
     */
    public String getViewBfsSize() {
        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.convertDiskSize(getBfsSize());
    }

    /**
     * <br>[機  能] スレッド ディスク使用量(表示用)を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return スレッド ディスク使用量(表示用)
     */
    public String getViewBtsSize() {
        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.convertDiskSize(getBtsSize());
    }
    /**
     * <p>btsTempflg を取得します。
     * @return btsTempflg
     */
    public int getBtsTempflg() {
        return btsTempflg__;
    }
    /**
     * <p>btsTempflg をセットします。
     * @param btsTempflg btsTempflg
     */
    public void setBtsTempflg(int btsTempflg) {
        btsTempflg__ = btsTempflg;
    }
    /**
     * <p>btiLimitException を取得します。
     * @return btiLimitException
     */
    public int getBtiLimitException() {
        return btiLimitException__;
    }
    /**
     * <p>btiLimitException をセットします。
     * @param btiLimitException btiLimitException
     */
    public void setBtiLimitException(int btiLimitException) {
        btiLimitException__ = btiLimitException;
    }
    /**
     * <p>btiLimitFrDate を取得します。
     * @return btiLimitFrDate
     */
    public UDate getBtiLimitFrDate() {
        return btiLimitFrDate__;
    }
    /**
     * <p>btiLimitFrDate をセットします。
     * @param btiLimitFrDate btiLimitFrDate
     */
    public void setBtiLimitFrDate(UDate btiLimitFrDate) {
        btiLimitFrDate__ = btiLimitFrDate;
    }
    /**
     * <p>bfiLimit を取得します。
     * @return bfiLimit
     */
    public int getBfiLimit() {
        return bfiLimit__;
    }
    /**
     * <p>bfiLimit をセットします。
     * @param bfiLimit bfiLimit
     */
    public void setBfiLimit(int bfiLimit) {
        bfiLimit__ = bfiLimit;
    }
    /**
     * <p>bfiLimitDate を取得します。
     * @return bfiLimitDate
     */
    public int getBfiLimitDate() {
        return bfiLimitDate__;
    }
    /**
     * <p>bfiLimitDate をセットします。
     * @param bfiLimitDate bfiLimitDate
     */
    public void setBfiLimitDate(int bfiLimitDate) {
        bfiLimitDate__ = bfiLimitDate;
    }
    /**
     * <p>bfiKeep を取得します。
     * @return bfiKeep
     */
    public int getBfiKeep() {
        return bfiKeep__;
    }
    /**
     * <p>bfiKeep をセットします。
     * @param bfiKeep bfiKeep
     */
    public void setBfiKeep(int bfiKeep) {
        bfiKeep__ = bfiKeep;
    }
    /**
     * <p>bfiKeepDateY を取得します。
     * @return bfiKeepDateY
     */
    public int getBfiKeepDateY() {
        return bfiKeepDateY__;
    }
    /**
     * <p>bfiKeepDateY をセットします。
     * @param bfiKeepDateY bfiKeepDateY
     */
    public void setBfiKeepDateY(int bfiKeepDateY) {
        bfiKeepDateY__ = bfiKeepDateY;
    }
    /**
     * <p>bfiKeepDateM を取得します。
     * @return bfiKeepDateM
     */
    public int getBfiKeepDateM() {
        return bfiKeepDateM__;
    }
    /**
     * <p>bfiKeepDateM をセットします。
     * @param bfiKeepDateM bfiKeepDateM
     */
    public void setBfiKeepDateM(int bfiKeepDateM) {
        bfiKeepDateM__ = bfiKeepDateM;
    }
    /**
     * <p>rsvThreCnt を取得します。
     * @return rsvThreCnt
     */
    public int getRsvThreCnt() {
        return rsvThreCnt__;
    }
    /**
     * <p>rsvThreCnt をセットします。
     * @param rsvThreCnt rsvThreCnt
     */
    public void setRsvThreCnt(int rsvThreCnt) {
        rsvThreCnt__ = rsvThreCnt;
    }
    /**
     * <p>threadFinFlg を取得します。
     * @return threadFinFlg
     */
    public int getThreadFinFlg() {
        return threadFinFlg__;
    }
    /**
     * <p>threadFinFlg をセットします。
     * @param threadFinFlg threadFinFlg
     */
    public void setThreadFinFlg(int threadFinFlg) {
        threadFinFlg__ = threadFinFlg;
    }
    /**
     * <p>bfiMinDivを取得します。
     * @return bfiMinDiv
     * */
    public int getMinDiv() {
        return bfiMinDiv__;
    }
    /**
     * <p>bfiMinDivをセットします。
     * @param bfiMinDiv bfiMinDiv
     * */
    public void setMinDiv(int bfiMinDiv) {
        bfiMinDiv__ = bfiMinDiv;
    }

    /**
     * <p>strBtiLimitDate を取得します。
     * @return strBtiLimitDate
     */
    public String getStrBtiLimitDate() {
        return strBtiLimitDate__;
    }
    /**
     * <p>strBtiLimitDate をセットします。
     * @param strBtiLimitDate strBtiLimitDate
     */
    public void setStrBtiLimitDate(String strBtiLimitDate) {
        strBtiLimitDate__ = strBtiLimitDate;
    }
    /**
     * <p>strBtiLimitFrDate を取得します。
     * @return strBtiLimitFrDate
     */
    public String getStrBtiLimitFrDate() {
        return strBtiLimitFrDate__;
    }
    /**
     * <p>strBtiLimitFrDate をセットします。
     * @param strBtiLimitFrDate strBtiLimitFrDate
     */
    public void setStrBtiLimitFrDate(String strBtiLimitFrDate) {
        strBtiLimitFrDate__ = strBtiLimitFrDate;
    }
    /**
     * <p>bfiThreImportance を取得します。
     * @return bfiThreImportance
     */
    public int getBfiThreImportance() {
        return bfiThreImportance__;
    }
    /**
     * <p>bfiThreImportance をセットします。
     * @param bfiThreImportance bfiThreImportance
     */
    public void setBfiThreImportance(int bfiThreImportance) {
        bfiThreImportance__ = bfiThreImportance;
    }
    /**
     * <p>parentForumSid を取得します。
     * @return parentForumSid
     */
    public int getParentForumSid() {
        return parentForumSid__;
    }
    /**
     * <p>parentForumSid をセットします。
     * @param parentForumSid parentForumSid
     */
    public void setParentForumSid(int parentForumSid) {
        parentForumSid__ = parentForumSid;
    }
    /**
     * <p>forumLevel を取得します。
     * @return forumLevel
     */
    public int getForumLevel() {
        return forumLevel__;
    }
    /**
     * <p>forumLevel をセットします。
     * @param forumLevel forumLevel
     */
    public void setForumLevel(int forumLevel) {
        forumLevel__ = forumLevel;
    }
    /**
     * <p>numberOfChild を取得します。
     * @return numberOfChild
     */
    public int getNumberOfChild() {
        return numberOfChild__;
    }
    /**
     * <p>numberOfChild をセットします。
     * @param numberOfChild numberOfChild
     */
    public void setNumberOfChild(int numberOfChild) {
        numberOfChild__ = numberOfChild;
    }
    /**
     * <p>followParentMemFlg を取得します。
     * @return followParentMemFlg
     */
    public int getFollowParentMemFlg() {
        return followParentMemFlg__;
    }
    /**
     * <p>followParentMemFlg をセットします。
     * @param followParentMemFlg followParentMemFlg
     */
    public void setFollowParentMemFlg(int followParentMemFlg) {
        followParentMemFlg__ = followParentMemFlg;
    }
    /**
     * <p>bfiUnlimited を取得します。
     * @return bfiUnlimited
     * @see jp.groupsession.v2.bbs.model.BulletinDspModel#bfiUnlimited__
     */
    public int getBfiUnlimited() {
        return bfiUnlimited__;
    }
    /**
     * <p>bfiUnlimited をセットします。
     * @param bfiUnlimited bfiUnlimited
     * @see jp.groupsession.v2.bbs.model.BulletinDspModel#bfiUnlimited__
     */
    public void setBfiUnlimited(int bfiUnlimited) {
        bfiUnlimited__ = bfiUnlimited;
    }
}
