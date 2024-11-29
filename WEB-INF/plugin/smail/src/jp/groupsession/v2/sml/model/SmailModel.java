package jp.groupsession.v2.sml.model;

import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメールの各種処理に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmailModel extends AbstractModel {

    /** アカウントSID */
    private int accountSid__;
    /** アカウント名 */
    private String accountName__;
    /** アカウント 状態区分 */
    private int accountJkbn__;

    /** メールSID */
    private int smlSid__;
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
    /** 有効フラグ */
    private int usrUkoFlg__;
    /** ユーザSID */
    private int usrSid__;
    /** 姓 */
    private String usiSei__;
    /** 名 */
    private String usiMei__;
    /** 宛先リスト */
    private ArrayList < AtesakiModel > atesakiList__;
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

    /** メール形式 */
    private int smsType__;

    /** メールサイズ  */
    private Long smsSize__ = new Long(0);

    /** メールサイズ 表示用  */
    private String smlSizeStr__ = "";

    /** 写真 ファイルSid  */
    private Long binFileSid__ = new Long(0);
    /** 写真 ファイル有無 */
    private int photoFileDsp__;

    /** 返信フラグ 0=未返信 1=返信 */
    private int returnKbn__;
    /** 転送フラグ 0=未転送信 1=転送 */
    private int fwKbn__;

    /** アカウント情報  */
    private AccountDataModel smlAccountData__ = null;

    /** ラベルリスト */
    private List <SmlLabelModel> labelList__;

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
     * <p>smlSizeStr を取得します。
     * @return smlSizeStr
     */
    public String getSmlSizeStr() {
        return smlSizeStr__;
    }
    /**
     * <p>smlSizeStr をセットします。
     * @param smlSizeStr smlSizeStr
     */
    public void setSmlSizeStr(String smlSizeStr) {
        smlSizeStr__ = smlSizeStr;
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
     * <p>smlAccountData を取得します。
     * @return smlAccountData
     */
    public AccountDataModel getSmlAccountData() {
        return smlAccountData__;
    }
    /**
     * <p>smlAccountData をセットします。
     * @param smlAccountData smlAccountData
     */
    public void setSmlAccountData(AccountDataModel smlAccountData) {
        smlAccountData__ = smlAccountData;
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
     * <p>labelList を取得します。
     * @return labelList
     */
    public List<SmlLabelModel> getLabelList() {
        return labelList__;
    }
    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(List<SmlLabelModel> labelList) {
        labelList__ = labelList;
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