package jp.groupsession.v2.wml.wml070;

import java.io.Serializable;

/**
 * <br>[機  能] WEBメール 送受信ログ一覧の表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml070MaildataDspModel implements Serializable {

    /** メッセージ番号 */
    private String wmdMailnum__ = null;
    /** 件名 */
    private String wlgTitle__ = null;
    /** 日付 */
    private String wlgDate__ = null;
    /** 時間 */
    private String wlgTime__ = null;
    /** 送信元 */
    private String wlgFrom__ = null;
    /** 添付ファイルフラグ */
    private String wlgTempFlg__ = null;
    /** メール種別 */
    private String wlgMailType__ = null;
    /** 種別 */
    private String wlsType__ = null;
    /** メールアドレス */
    private String wlsAddress__ = null;

    /**
     * <p>wlgDate を取得します。
     * @return wlgDate
     */
    public String getWlgDate() {
        return wlgDate__;
    }
    /**
     * <p>wlgDate をセットします。
     * @param wlgDate wlgDate
     */
    public void setWlgDate(String wlgDate) {
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
     * <p>wlgMailType を取得します。
     * @return wlgMailType
     */
    public String getWlgMailType() {
        return wlgMailType__;
    }
    /**
     * <p>wlgMailType をセットします。
     * @param wlgMailType wlgMailType
     */
    public void setWlgMailType(String wlgMailType) {
        wlgMailType__ = wlgMailType;
    }
    /**
     * <p>wlgTempFlg を取得します。
     * @return wlgTempFlg
     */
    public String getWlgTempFlg() {
        return wlgTempFlg__;
    }
    /**
     * <p>wlgTempFlg をセットします。
     * @param wlgTempFlg wlgTempFlg
     */
    public void setWlgTempFlg(String wlgTempFlg) {
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
     * <p>wlsType を取得します。
     * @return wlsType
     */
    public String getWlsType() {
        return wlsType__;
    }
    /**
     * <p>wlsType をセットします。
     * @param wlsType wlsType
     */
    public void setWlsType(String wlsType) {
        wlsType__ = wlsType;
    }
    /**
     * <p>wmdMailnum を取得します。
     * @return wmdMailnum
     */
    public String getWmdMailnum() {
        return wmdMailnum__;
    }
    /**
     * <p>wmdMailnum をセットします。
     * @param wmdMailnum wmdMailnum
     */
    public void setWmdMailnum(String wmdMailnum) {
        wmdMailnum__ = wmdMailnum;
    }
    /**
     * <p>wlgTime を取得します。
     * @return wlgTime
     */
    public String getWlgTime() {
        return wlgTime__;
    }
    /**
     * <p>wlgTime をセットします。
     * @param wlgTime wlgTime
     */
    public void setWlgTime(String wlgTime) {
        wlgTime__ = wlgTime;
    }

}
