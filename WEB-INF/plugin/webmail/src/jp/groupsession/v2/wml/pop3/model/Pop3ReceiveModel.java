package jp.groupsession.v2.wml.pop3.model;

import java.io.Serializable;
import java.sql.Connection;

import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] メール情報登録時に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Pop3ReceiveModel implements Serializable {

    /** コネクション */
    private Connection con__ = null;
    /** 採番コントローラ */
    private MlCountMtController mtCon__ = null;
    /** アカウントSID */
    private int wacSid__ = 0;
    /** アカウント文字列 */
    private String accountString__ = null;
    /** アカウントメールアドレス */
    private String accountMailAddress__ = null;
    /** ディレクトリSID(保存先) */
    private long saveWdrSid__ = 0;
    /** ディレクトリSID(ゴミ箱) */
    private long dustWdrSid__ = 0;
    /** ディレクトリSID(保管) */
    private long strageWdrSid__ = 0;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;
    /** メール添付ファイル保存先ディレクトリ */
    private String fileSaveDir__ = null;
    /** ユーザSID */
    private int userSid__ = 0;
    /** 送受信ログの登録 */
    private Integer logRegist__ = null;


    /**
     * <p>accountString を取得します。
     * @return accountString
     */
    public String getAccountString() {
        return accountString__;
    }
    /**
     * <p>accountString をセットします。
     * @param accountString accountString
     */
    public void setAccountString(String accountString) {
        accountString__ = accountString;
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
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>fileSaveDir を取得します。
     * @return fileSaveDir
     */
    public String getFileSaveDir() {
        return fileSaveDir__;
    }
    /**
     * <p>fileSaveDir をセットします。
     * @param fileSaveDir fileSaveDir
     */
    public void setFileSaveDir(String fileSaveDir) {
        fileSaveDir__ = fileSaveDir;
    }
    /**
     * <p>mtCon を取得します。
     * @return mtCon
     */
    public MlCountMtController getMtCon() {
        return mtCon__;
    }
    /**
     * <p>mtCon をセットします。
     * @param mtCon mtCon
     */
    public void setMtCon(MlCountMtController mtCon) {
        mtCon__ = mtCon;
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
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>saveWdrSid を取得します。
     * @return saveWdrSid
     */
    public long getSaveWdrSid() {
        return saveWdrSid__;
    }
    /**
     * <p>saveWdrSid をセットします。
     * @param saveWdrSid saveWdrSid
     */
    public void setSaveWdrSid(long saveWdrSid) {
        saveWdrSid__ = saveWdrSid;
    }
    /**
     * <p>dustWdrSid を取得します。
     * @return dustWdrSid
     */
    public long getDustWdrSid() {
        return dustWdrSid__;
    }

    /**
     * <p>dustWdrSid をセットします。
     * @param dustWdrSid dustWdrSid
     */
    public void setDustWdrSid(long dustWdrSid) {
        dustWdrSid__ = dustWdrSid;
    }

    /**
     * <p>accountMailAddress を取得します。
     * @return accountMailAddress
     */
    public String getAccountMailAddress() {
        return accountMailAddress__;
    }
    /**
     * <p>logRegist を取得します。
     * @return logRegist
     */
    public Integer getLogRegist() {
        return logRegist__;
    }
    /**
     * <p>logRegist をセットします。
     * @param logRegist logRegist
     */
    public void setLogRegist(Integer logRegist) {
        logRegist__ = logRegist;
    }
    /**
     * <p>accountMailAddress をセットします。
     * @param accountMailAddress accountMailAddress
     */
    public void setAccountMailAddress(String accountMailAddress) {
        accountMailAddress__ = accountMailAddress;
    }
    /**
     * <p>strageWdrSid を取得します。
     * @return strageWdrSid
     * @see jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel#strageWdrSid__
     */
    public long getStrageWdrSid() {
        return strageWdrSid__;
    }
    /**
     * <p>strageWdrSid をセットします。
     * @param strageWdrSid strageWdrSid
     * @see jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel#strageWdrSid__
     */
    public void setStrageWdrSid(long strageWdrSid) {
        strageWdrSid__ = strageWdrSid;
    }
}
