package jp.groupsession.v2.bbs.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 掲示板 閲覧状況を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinWachModel extends AbstractModel {

    /** ユーザSID */
    private int usrSid__;
    /** ユーザ名　姓 */
    private String usiSei__ = null;
    /** ユーザ名　名 */
    private String usiMei__ = null;
    /** ユーザ名　姓（カナ） */
    private String usiSeiKn__ = null;
    /** ユーザ名　名（カナ） */
    private String usiMeiKn__ = null;
    /** ユーザ状態区分 */
    private int userJkbn__ = 0;
    /** ユーザ有効区分 */
    private int usrYukoKbn__ = 0;

    /**
     * <p>usiMei を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei をセットします。
     * @param usiMei usiMei
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>usiMeiKn を取得します。
     * @return usiMeiKn
     */
    public String getUsiMeiKn() {
        return usiMeiKn__;
    }
    /**
     * <p>usiMeiKn をセットします。
     * @param usiMeiKn usiMeiKn
     */
    public void setUsiMeiKn(String usiMeiKn) {
        usiMeiKn__ = usiMeiKn;
    }
    /**
     * <p>usiSei を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei をセットします。
     * @param usiSei usiSei
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * <p>usiSeiKn を取得します。
     * @return usiSeiKn
     */
    public String getUsiSeiKn() {
        return usiSeiKn__;
    }
    /**
     * <p>usiSeiKn をセットします。
     * @param usiSeiKn usiSeiKn
     */
    public void setUsiSeiKn(String usiSeiKn) {
        usiSeiKn__ = usiSeiKn;
    }
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
     * <p>userYukoKbn を取得します。
     * @return usrYukoKbn
     */
    public int getUsrYukoKbn() {
        return usrYukoKbn__;
    }
    /**
     * <p>userYukoKbn をセットします。
     * @param usrYukoKbn usrYukoKbn
     */
    public void setUsrYukoKbn(int usrYukoKbn) {
        usrYukoKbn__ = usrYukoKbn;
    }
}
