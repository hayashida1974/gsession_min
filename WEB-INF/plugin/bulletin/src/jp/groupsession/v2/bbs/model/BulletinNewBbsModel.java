package jp.groupsession.v2.bbs.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 新着掲示板の情報を格納するModelクラス
 * <br>[解  説] フォーラム一覧にて新着スレッドを表示するために使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinNewBbsModel extends AbstractModel {

    /** 未読/既読フラグ 未読 */
    public static final int READFLG_NOREAD = 0;
    /** 未読/既読フラグ 既読 */
    public static final int READFLG_READ = 1;

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
    /** スレッドタイトル */
    private String btiTitle__  = null;
    /** ユーザSID */
    private int userSid__ = 0;
    /** 投稿ユーザ名 */
    private String userName__ = "";
    /** 投稿ユーザ状態区分 */
    private int userJkbn__ = 0;
    /** ユーザ有効区分 */
    private int userYukoKbn__ = 0;
    /** 投稿グループSID */
    private int grpSid__ = 0;
    /** 投稿グループ名 */
    private String grpName__ = "";
    /** 投稿グループ状態区分 */
    private int grpJkbn__ = 0;


    /** 最新書き込み日時 */
    private UDate writeDate__ = null;
    /** 最新書き込み日時(文字列) */
    private String strWriteDate__ = null;

    /** フォーラム未読フラグ */
    private int f_ReadFlg__ = 0;
    /** スレッド未読フラグ */
    private int t_ReadFlg__ = 0;

    /** バイナリSID */
    private Long imgBinSid__ = new Long(0);

    /** 重要度 */
    private int threImportance__ = 0;

    /** スレッド 添付ファイルフラグ */
    private int btsTempflg__ = 0;

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
     * @return f_ReadFlg
     */
    public int getF_ReadFlg() {
        return f_ReadFlg__;
    }
    /**
     * @param fReadFlg セットする f_ReadFlg
     */
    public void setF_ReadFlg(int fReadFlg) {
        f_ReadFlg__ = fReadFlg;
    }
    /**
     * @return t_ReadFlg
     */
    public int getT_ReadFlg() {
        return t_ReadFlg__;
    }
    /**
     * @param tReadFlg セットする t_ReadFlg
     */
    public void setT_ReadFlg(int tReadFlg) {
        t_ReadFlg__ = tReadFlg;
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
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }
    /**
     * @param userName セットする userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
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
     * <p>userJkbn を取得します。
     * @return userJkbn
     */
    public int getUserJkbn() {
        return userJkbn__;
    }
    /**
     * <p>userJkbn をセットします。
     * @param userJkbn userJkbn
     */
    public void setUserJkbn(int userJkbn) {
        userJkbn__ = userJkbn;
    }
    /**
     * userYukoKbn__ を取得します。
     * @return ユーザ有効区分
     */
    public int getUserYukoKbn() {
        return userYukoKbn__;
    }
    /**
     *  userYukoKbn__ をセットします。
     *  @param userYukoKbn ユーザ有効区分
     */
    public void setUserYukoKbn(int userYukoKbn) {
        userYukoKbn__ = userYukoKbn;
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
     * <p>threImportance を取得します。
     * @return threImportance
     */
    public int getThreImportance() {
        return threImportance__;
    }
    /**
     * <p>threImportance をセットします。
     * @param threImportance threImportance
     */
    public void setThreImportance(int threImportance) {
        threImportance__ = threImportance;
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
}
