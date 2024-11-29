package jp.groupsession.v2.wml.wml260;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] WEBメール 予約送信メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260MailDataModel implements Serializable {

    /** メッセージ番号 */
    private int wmdMailnum__ = 0;
    /** アカウント名名 */
    private String accountName__ = null;
    /** 件名 */
    private String wlgTitle__ = null;
    /** 送信予定日時 */
    private UDate wlgDate__ = null;
    /** 送信元 */
    private String wlgFrom__ = null;
    /** 添付ファイルフラグ */
    private int wlgTempFlg__ = 0;
    /** メールアドレス */
    private String wlsAddress__ = null;

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
     * <p>wlgDate を取得します。
     * @return wlgDate
     */
    public UDate getWlgDate() {
        return wlgDate__;
    }
    /**
     * <p>wlgDate をセットします。
     * @param wlgDate wlgDate
     */
    public void setWlgDate(UDate wlgDate) {
        wlgDate__ = wlgDate;
    }
    /**
     * <p>wlgFrom を取得します。
     * @return wlgFrom
     */
    public String getWlgFrom() {
        return wlgFrom__;
    }
    /**
     * <p>wlgFrom をセットします。
     * @param wlgFrom wlgFrom
     */
    public void setWlgFrom(String wlgFrom) {
        wlgFrom__ = wlgFrom;
    }
    /**
     * <p>wlgTempFlg を取得します。
     * @return wlgTempFlg
     */
    public int getWlgTempFlg() {
        return wlgTempFlg__;
    }
    /**
     * <p>wlgTempFlg をセットします。
     * @param wlgTempFlg wlgTempFlg
     */
    public void setWlgTempFlg(int wlgTempFlg) {
        wlgTempFlg__ = wlgTempFlg;
    }
    /**
     * <p>wlgTitle を取得します。
     * @return wlgTitle
     */
    public String getWlgTitle() {
        return wlgTitle__;
    }
    /**
     * <p>wlgTitle をセットします。
     * @param wlgTitle wlgTitle
     */
    public void setWlgTitle(String wlgTitle) {
        wlgTitle__ = wlgTitle;
    }
    /**
     * <p>wlsAddress を取得します。
     * @return wlsAddress
     */
    public String getWlsAddress() {
        return wlsAddress__;
    }
    /**
     * <p>wlsAddress をセットします。
     * @param wlsAddress wlsAddress
     */
    public void setWlsAddress(String wlsAddress) {
        wlsAddress__ = wlsAddress;
    }
    /**
     * <p>wmdMailnum を取得します。
     * @return wmdMailnum
     */
    public int getWmdMailnum() {
        return wmdMailnum__;
    }
    /**
     * <p>wmdMailnum をセットします。
     * @param wmdMailnum wmdMailnum
     */
    public void setWmdMailnum(int wmdMailnum) {
        wmdMailnum__ = wmdMailnum;
    }

}
