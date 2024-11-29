package jp.groupsession.v2.convert.convert430.model;



import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメールの各種処理に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CvtSmailModel extends AbstractModel {

    /** メールSID */
    private int smlSid__;
    /** アカウントSID */
    private int sacSid__;
    /** 開封区分 */
    private int smjOpkbn__;
    /** マーク区分 */
    private int smsMark__;
    /** 件名 */
    private String smsTitle__;
    /** 送信日時 */
    private UDate smsSdate__;
    /** 送信日時 (yyyy/MM/dd hh:mm:ss形式) */
    private String strSdate__;
    /** 状態区分 */
    private int usrJkbn__;
    /** 姓 */
    private String usiSei__;
    /** 名 */
    private String usiMei__;
    /** 宛先リストサイズ */
    private int listSize__;
    /** メール区分(受信、送信、草稿) */
    private String mailKbn__ = "";
    /** メールキー (メール区分 + メールSID) */
    private String mailKey__ = "";
    /** 添付ファイル（カウント） */
    private int binCnt__ = 0;
    /** 本文 */
    private String smsBody__ = null;
    /** 送信区分 */
    private int smjSendkbn__;

    /** メールサイズ  */
    private Long smsSize__ = new Long(0);

    /** 写真 ファイルSid  */
    private Long binFileSid__ = new Long(0);
    /** 写真 ファイル有無 */
    private int photoFileDsp__;

    /** 返信フラグ 0=未返信 1=返信 */
    private int returnKbn__;
    /** 転送フラグ 0=未転送信 1=転送 */
    private int fwKbn__;

    /**
     * @return mailKey__ を戻します。
     */
    public String getMailKey() {
        return mailKey__;
    }
    /**
     * @param mailKey 設定する mailKey__。
     */
    public void setMailKey(String mailKey) {
        mailKey__ = mailKey;
    }
    /**
     * @return mailKbn__ を戻します。
     */
    public String getMailKbn() {
        return mailKbn__;
    }
    /**
     * @param mailKbn 設定する mailKbn__。
     */
    public void setMailKbn(String mailKbn) {
        mailKbn__ = mailKbn;
    }
    /**
     * @return listSize を戻します。
     */
    public int getListSize() {
        return listSize__;
    }
    /**
     * @param listSize 設定する listSize。
     */
    public void setListSize(int listSize) {
        listSize__ = listSize;
    }
    /**
     * @return smjOpkbn を戻します。
     */
    public int getSmjOpkbn() {
        return smjOpkbn__;
    }
    /**
     * @param smjOpkbn 設定する smjOpkbn。
     */
    public void setSmjOpkbn(int smjOpkbn) {
        smjOpkbn__ = smjOpkbn;
    }
    /**
     * @return smlSid を戻します。
     */
    public int getSmlSid() {
        return smlSid__;
    }
    /**
     * @param smlSid 設定する smlSid。
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }
    /**
     * @return smsMark を戻します。
     */
    public int getSmsMark() {
        return smsMark__;
    }
    /**
     * @param smsMark 設定する smsMark。
     */
    public void setSmsMark(int smsMark) {
        smsMark__ = smsMark;
    }
    /**
     * @return smsSdate を戻します。
     */
    public UDate getSmsSdate() {
        return smsSdate__;
    }
    /**
     * @param smsSdate 設定する smsSdate。
     */
    public void setSmsSdate(UDate smsSdate) {
        smsSdate__ = smsSdate;
    }
    /**
     * @return smsTitle を戻します。
     */
    public String getSmsTitle() {
        return smsTitle__;
    }
    /**
     * @param smsTitle 設定する smsTitle。
     */
    public void setSmsTitle(String smsTitle) {
        smsTitle__ = smsTitle;
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
     * @return strSdate を戻します。
     */
    public String getStrSdate() {
        return strSdate__;
    }
    /**
     * @param strSdate 設定する strSdate。
     */
    public void setStrSdate(String strSdate) {
        strSdate__ = strSdate;
    }
    /**
     * <p>binCnt を取得します。
     * @return binCnt
     */
    public int getBinCnt() {
        return binCnt__;
    }
    /**
     * <p>binCnt をセットします。
     * @param binCnt binCnt
     */
    public void setBinCnt(int binCnt) {
        binCnt__ = binCnt;
    }
    /**
     * @return smsBody
     */
    public String getSmsBody() {
        return smsBody__;
    }
    /**
     * @param smsBody 設定する smsBody
     */
    public void setSmsBody(String smsBody) {
        smsBody__ = smsBody;
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
     * @return returnKbn
     */
    public int getReturnKbn() {
        return returnKbn__;
    }
    /**
     * @param returnKbn セットする returnKbn
     */
    public void setReturnKbn(int returnKbn) {
        returnKbn__ = returnKbn;
    }
    /**
     * @return fwKbn
     */
    public int getFwKbn() {
        return fwKbn__;
    }
    /**
     * @param fwKbn セットする fwKbn
     */
    public void setFwKbn(int fwKbn) {
        fwKbn__ = fwKbn;
    }
    /**
     * <p>smsSize を取得します。
     * @return smsSize
     */
    public Long getSmsSize() {
        return smsSize__;
    }
    /**
     * <p>smsSize をセットします。
     * @param smsSize smsSize
     */
    public void setSmsSize(Long smsSize) {
        smsSize__ = smsSize;
    }
    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }
    /**
     * <p>sacSid をセットします。
     * @param sacSid sacSid
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }
}