package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ログイン履歴を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnLoginHistoryModel extends AbstractModel {

    /** USR_SID mapping */
    private int usrSid__;
    /** CLH_TERMINAL mapping */
    private int clhTerminal__;
    /** CLH_IP mapping */
    private String clhIp__;
    /** CLH_CAR mapping */
    private int clhCar__;
    /** CLH_UID mapping */
    private String clhUid__;
    /** CLH_AUID mapping */
    private int clhAuid__;
    /** CLH_ADATE mapping */
    private UDate clhAdate__;
    /** CLH_EUID mapping */
    private int clhEuid__;
    /** CLH_EDATE mapping */
    private UDate clhEdate__;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>clhTerminal を取得します。
     * @return clhTerminal
     */
    public int getClhTerminal() {
        return clhTerminal__;
    }
    /**
     * <p>clhTerminal をセットします。
     * @param clhTerminal clhTerminal
     */
    public void setClhTerminal(int clhTerminal) {
        clhTerminal__ = clhTerminal;
    }
    /**
     * <p>clhIp を取得します。
     * @return clhIp
     */
    public String getClhIp() {
        return clhIp__;
    }
    /**
     * <p>clhIp をセットします。
     * @param clhIp clhIp
     */
    public void setClhIp(String clhIp) {
        clhIp__ = clhIp;
    }
    /**
     * <p>clhCar を取得します。
     * @return clhCar
     */
    public int getClhCar() {
        return clhCar__;
    }
    /**
     * <p>clhCar をセットします。
     * @param clhCar clhCar
     */
    public void setClhCar(int clhCar) {
        clhCar__ = clhCar;
    }
    /**
     * <p>clhUid を取得します。
     * @return clhUid
     */
    public String getClhUid() {
        return clhUid__;
    }
    /**
     * <p>clhUid をセットします。
     * @param clhUid clhUid
     */
    public void setClhUid(String clhUid) {
        clhUid__ = clhUid;
    }
    /**
     * <p>clhAuid を取得します。
     * @return clhAuid
     */
    public int getClhAuid() {
        return clhAuid__;
    }
    /**
     * <p>clhAuid をセットします。
     * @param clhAuid clhAuid
     */
    public void setClhAuid(int clhAuid) {
        clhAuid__ = clhAuid;
    }
    /**
     * <p>clhAdate を取得します。
     * @return clhAdate
     */
    public UDate getClhAdate() {
        return clhAdate__;
    }
    /**
     * <p>clhAdate をセットします。
     * @param clhAdate clhAdate
     */
    public void setClhAdate(UDate clhAdate) {
        clhAdate__ = clhAdate;
    }
    /**
     * <p>clhEuid を取得します。
     * @return clhEuid
     */
    public int getClhEuid() {
        return clhEuid__;
    }
    /**
     * <p>clhEuid をセットします。
     * @param clhEuid clhEuid
     */
    public void setClhEuid(int clhEuid) {
        clhEuid__ = clhEuid;
    }
    /**
     * <p>clhEdate を取得します。
     * @return clhEdate
     */
    public UDate getClhEdate() {
        return clhEdate__;
    }
    /**
     * <p>clhEdate をセットします。
     * @param clhEdate clhEdate
     */
    public void setClhEdate(UDate clhEdate) {
        clhEdate__ = clhEdate;
    }
}