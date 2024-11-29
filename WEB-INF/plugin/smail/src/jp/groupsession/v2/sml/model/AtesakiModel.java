package jp.groupsession.v2.sml.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] 宛先情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AtesakiModel extends AbstractModel {

    /** アカウントSid*/
    private int accountSid__;
    /** アカウント名 */
    private String accountName__;
    /** アカウント状態区分*/
    private int accountJkbn__;

    /** ユーザSID */
    private int usrSid__;
    /** 状態区分 */
    private int usrJkbn__;
    /** 有効フラグ */
    private int usrUkoFlg__;

    /** 姓 */
    private String usiSei__;
    /** 名 */
    private String usiMei__;
    /** 開封日時 */
    private UDate smjOpdate__;
    /** 開封日時(yyyy/MM/dd) 形式 */
    private String smlOpdateStr__;
    /** 転送区分 */
    private String smjFwkbn__;
    /** 送信区分 */
    private int smjSendkbn__;

    /** 写真 ファイルSid  */
    private Long binFileSid__ = new Long(0);
    /** 写真 ファイル有無 */
    private int photoFileDsp__;

    /**
     * <p>smjFwkbn を取得します。
     * @return smjFwkbn
     */
    public String getSmjFwkbn() {
        return smjFwkbn__;
    }
    /**
     * <p>smjFwkbn をセットします。
     * @param smjFwkbn smjFwkbn
     */
    public void setSmjFwkbn(String smjFwkbn) {
        smjFwkbn__ = smjFwkbn;
    }
    /**
     * @return usrSid を戻します。
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid。
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * @return smjOpdate を戻します。
     */
    public UDate getSmjOpdate() {
        return smjOpdate__;
    }
    /**
     * @param smjOpdate 設定する smjOpdate。
     */
    public void setSmjOpdate(UDate smjOpdate) {
        smjOpdate__ = smjOpdate;
    }
    /**
     * @return smlOpdateStr を戻します。
     */
    public String getSmlOpdateStr() {
        return smlOpdateStr__;
    }
    /**
     * @param smlOpdateStr 設定する smlOpdateStr。
     */
    public void setSmlOpdateStr(String smlOpdateStr) {
        smlOpdateStr__ = smlOpdateStr;
    }
    /**
     * @return usiMei を戻します。
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * @param usiMei 設定する usiMei。
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * @return usiSei を戻します。
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * @param usiSei 設定する usiSei。
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * @return usrJkbn を戻します。
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * @param usrJkbn 設定する usrJkbn。
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
    /**
     * <p>smjSendkbn を取得します。
     * @return smjSendkbn
     */
    public int getSmjSendkbn() {
        return smjSendkbn__;
    }
    /**
     * <p>smjSendkbn をセットします。
     * @param smjSendkbn smjSendkbn
     */
    public void setSmjSendkbn(int smjSendkbn) {
        smjSendkbn__ = smjSendkbn;
    }
    /**
     * <p>binFileSid を取得します。
     * @return binFileSid
     */
    public Long getBinFileSid() {
        return binFileSid__;
    }
    /**
     * <p>binFileSid をセットします。
     * @param binFileSid binFileSid
     */
    public void setBinFileSid(Long binFileSid) {
        binFileSid__ = binFileSid;
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
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }
    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>accountJkbn を取得します。
     * @return accountJkbn
     */
    public int getAccountJkbn() {
        return accountJkbn__;
    }
    /**
     * <p>accountJkbn をセットします。
     * @param accountJkbn accountJkbn
     */
    public void setAccountJkbn(int accountJkbn) {
        accountJkbn__ = accountJkbn;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
}