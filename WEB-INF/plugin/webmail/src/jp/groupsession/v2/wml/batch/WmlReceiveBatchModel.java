package jp.groupsession.v2.wml.batch;

import java.sql.Connection;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] メール受信に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlReceiveBatchModel extends AbstractModel {

    /** コネクション */
    private Connection con__ = null;
    /** アカウントSID */
    private int[] accountSid__ = null;
    /** 採番コントローラ */
    private MlCountMtController mtCon__ = null;
    /** ユーザSID */
    private int userSid__ = 0;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;
    /** テンポラリディレクトリパス */
    private String tempDir__ = null;
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     */
    public int[] getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     */
    public void setAccountSid(int[] accountSid) {
        accountSid__ = accountSid;
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

}
