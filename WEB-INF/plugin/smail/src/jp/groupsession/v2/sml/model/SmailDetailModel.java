package jp.groupsession.v2.sml.model;

import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメールの詳細データを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmailDetailModel extends AbstractModel {

    /** 差出人 アカウントSid*/
    private int accountSid__;
    /** 差出人 アカウント名 */
    private String accountName__;
    /** 差出人 アカウント状態区分*/
    private int accountJkbn__;

    /** メール区分(受信、送信、草稿) */
    private String mailKbn__;
    /** メールSID */
    private int smlSid__;
    /** 開封区分 */
    private int smjOpkbn__;
    /** 開封日時 */
    private UDate smjOpDate__;
    /** 差出人 SID */
    private int usrSid__;
    /** 差出人 姓 */
    private String usiSei__;
    /** 差出人 名 */
    private String usiMei__;
    /** 状態区分 */
    private int usrJkbn__;
    /** 有効フラグ */
    private int usrUkoFlg__;
    /** 送信日時 */
    private UDate smsSdate__;
    /** 送信日時(yyyy/MM/dd hh:mm:ss)形式 */
    private String smsSdateStr__;
    /** 宛先リスト */
    private ArrayList < AtesakiModel > atesakiList__;
    /** 宛先リストサイズ */
    private int listSize__;
    /** 宛先リスト */
    private ArrayList < AtesakiModel > ccList__;
    /** CCリストサイズ */
    private int ccListSize__;
    /** 宛先リスト */
    private ArrayList < AtesakiModel > bccList__;
    /** 宛先リストサイズ */
    private int bccListSize__;
    /** 件名 */
    private String smsTitle__;
    /** マーク */
    private int smsMark__;
    /** メール形式 */
    private int smsType__;
    /** 本文 */
    private String smsBody__;
    /** 送信区分 */
    private int smjSendkbn__;
    /** 写真 ファイルSid  */
    private Long binFileSid__ = new Long(0);
    /** 写真 ファイル有無 */
    private int photoFileDsp__;

    /** 返信区分 0=未返信 1=返信 */
    private int returnKbn__;
    /** 転送区分 0=未転送 1=転送 */
    private int fwKbn__;

    /** 更新日 */
    private UDate smsEdate__ = null;

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
     * @return atesakiList を戻します。
     */
    public ArrayList < AtesakiModel > getAtesakiList() {
        return atesakiList__;
    }
    /**
     * @param atesakiList 設定する atesakiList。
     */
    public void setAtesakiList(ArrayList < AtesakiModel > atesakiList) {
        atesakiList__ = atesakiList;
    }
    /**
     * @return smsBody を戻します。
     */
    public String getSmsBody() {
        return smsBody__;
    }
    /**
     * @param smsBody 設定する smsBody。
     */
    public void setSmsBody(String smsBody) {
        smsBody__ = smsBody;
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
     * @return smsSdateStr を戻します。
     */
    public String getSmsSdateStr() {
        return smsSdateStr__;
    }
    /**
     * @param smsSdateStr 設定する smsSdateStr。
     */
    public void setSmsSdateStr(String smsSdateStr) {
        smsSdateStr__ = smsSdateStr;
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
     * <p>ccList を取得します。
     * @return ccList
     */
    public ArrayList<AtesakiModel> getCcList() {
        return ccList__;
    }
    /**
     * <p>ccList をセットします。
     * @param ccList ccList
     */
    public void setCcList(ArrayList<AtesakiModel> ccList) {
        ccList__ = ccList;
    }
    /**
     * <p>ccListSize を取得します。
     * @return ccListSize
     */
    public int getCcListSize() {
        return ccListSize__;
    }
    /**
     * <p>ccListSize をセットします。
     * @param ccListSize ccListSize
     */
    public void setCcListSize(int ccListSize) {
        ccListSize__ = ccListSize;
    }
    /**
     * <p>bccList を取得します。
     * @return bccList
     */
    public ArrayList<AtesakiModel> getBccList() {
        return bccList__;
    }
    /**
     * <p>bccList をセットします。
     * @param bccList bccList
     */
    public void setBccList(ArrayList<AtesakiModel> bccList) {
        bccList__ = bccList;
    }
    /**
     * <p>bccListSize を取得します。
     * @return bccListSize
     */
    public int getBccListSize() {
        return bccListSize__;
    }
    /**
     * <p>bccListSize をセットします。
     * @param bccListSize bccListSize
     */
    public void setBccListSize(int bccListSize) {
        bccListSize__ = bccListSize;
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
     * <p>smsType を取得します。
     * @return smsType
     */
    public int getSmsType() {
        return smsType__;
    }
    /**
     * <p>smsType をセットします。
     * @param smsType smsType
     */
    public void setSmsType(int smsType) {
        smsType__ = smsType;
    }
    /**
     * <p>smsEdate を取得します。
     * @return smsEdate
     */
    public UDate getSmsEdate() {
        return smsEdate__;
    }
    /**
     * <p>smsEdate をセットします。
     * @param smsEdate smsEdate
     */
    public void setSmsEdate(UDate smsEdate) {
        smsEdate__ = smsEdate;
    }
    /**
     * <p>smjOpDate を取得します。
     * @return smjOpDate
     */
    public UDate getSmjOpDate() {
        return smjOpDate__;
    }
    /**
     * <p>smjOpDate をセットします。
     * @param smjOpDate smjOpDate
     */
    public void setSmjOpDate(UDate smjOpDate) {
        smjOpDate__ = smjOpDate;
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