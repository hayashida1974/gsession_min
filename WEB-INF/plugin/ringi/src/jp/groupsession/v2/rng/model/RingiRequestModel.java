package jp.groupsession.v2.rng.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 稟議申請情報を格納するのModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiRequestModel implements Serializable {

    /** 稟議SID */
    private int rngSid__ = 0;
    /** 稟議タイトル */
    private String rngTitle__ = null;
    /** 稟議内容 */
    private String rngContent__ = null;
    /** 承認ユーザ一覧 */
    private String[] rngapprUser__ = null;
    /** 最終確認ユーザ一覧 */
    private String[] rngconfirmUser__ = null;
    /** アプリケーションのルートパス */
    private String appRootPath__ = null;
    /** 添付ファイル設置ディレクトリパス */
    private String tempDir__ = null;
    /** ユーザSID */
    private int userSid__ = 0;
    /** 現在日時 */
    private UDate date__ = null;
    /** 申請ID */
    private String rngId__ = null;
    /** テンプレートID*/
    private int rtpSid__ = 0;
    /** テンプレートバージョン*/
    private int rtpVer__ = 0;
    /** 経路テンプレートバージョン*/
    private int rctVer__ = 0;

    /**
     * <p>rngSid を取得します。
     * @return rngSid
     */
    public int getRngSid() {
        return rngSid__;
    }
    /**
     * <p>rngSid をセットします。
     * @param rngSid rngSid
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }
    /**
     * <p>appRootPath を取得します。
     * @return appRootPath
     */
    public String getAppRootPath() {
        return appRootPath__;
    }
    /**
     * <p>appRootPath をセットします。
     * @param appRootPath appRootPath
     */
    public void setAppRootPath(String appRootPath) {
        appRootPath__ = appRootPath;
    }
    /**
     * <p>date を取得します。
     * @return date
     */
    public UDate getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setDate(UDate date) {
        date__ = date;
    }
    /**
     * rngIdを取得します。
     * @return rngId
     * */
    public String getRngId() {
        return rngId__;
    }
    /**
     * rngIdをセットします。
     * @param rngId rngId
     * */
    public void setRngId(String rngId) {
        rngId__ = rngId;
    }

    /**
     * <p>rngapprUser を取得します。
     * @return rngapprUser
     */
    public String[] getRngapprUser() {
        return rngapprUser__;
    }
    /**
     * <p>rngapprUser をセットします。
     * @param rngapprUser rngapprUser
     */
    public void setRngapprUser(String[] rngapprUser) {
        rngapprUser__ = rngapprUser;
    }
    /**
     * <p>rngconfirmUser を取得します。
     * @return rngconfirmUser
     */
    public String[] getRngconfirmUser() {
        return rngconfirmUser__;
    }
    /**
     * <p>rngconfirmUser をセットします。
     * @param rngconfirmUser rngconfirmUser
     */
    public void setRngconfirmUser(String[] rngconfirmUser) {
        rngconfirmUser__ = rngconfirmUser;
    }
    /**
     * <p>rngContent を取得します。
     * @return rngContent
     */
    public String getRngContent() {
        return rngContent__;
    }
    /**
     * <p>rngContent をセットします。
     * @param rngContent rngContent
     */
    public void setRngContent(String rngContent) {
        rngContent__ = rngContent;
    }
    /**
     * <p>rngTitle を取得します。
     * @return rngTitle
     */
    public String getRngTitle() {
        return rngTitle__;
    }
    /**
     * <p>rngTitle をセットします。
     * @param rngTitle rngTitle
     */
    public void setRngTitle(String rngTitle) {
        rngTitle__ = rngTitle;
    }
    /**
     * <p>tempDir を取得します。
     * @return tempDir
     */
    public String getTempDir() {
        return tempDir__;
    }
    /**
     * <p>tempDir をセットします。
     * @param tempDir tempDir
     */
    public void setTempDir(String tempDir) {
        tempDir__ = tempDir;
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
     * <p>rtpSid を取得します。
     * @return rtpSid
     */
    public int getRtpSid() {
        return rtpSid__;
    }
    /**
     * <p>rtpSid をセットします。
     * @param rtpSid rtpSid
     */
    public void setRtpSid(int rtpSid) {
        rtpSid__ = rtpSid;
    }
    /**
     * <p>rtpVer を取得します。
     * @return rtpVer
     */
    public int getRtpVer() {
        return rtpVer__;
    }
    /**
     * <p>rtpVer をセットします。
     * @param rtpVer rtpVer
     */
    public void setRtpVer(int rtpVer) {
        rtpVer__ = rtpVer;
    }
    /**
     * <p>rctVer を取得します。
     * @return rctVer
     */
    public int getRctVer() {
        return rctVer__;
    }
    /**
     * <p>rctVer をセットします。
     * @param rctVer rctVer
     */
    public void setRctVer(int rctVer) {
        rctVer__ = rctVer;
    }
}
