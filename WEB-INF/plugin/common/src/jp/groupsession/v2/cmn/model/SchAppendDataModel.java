package jp.groupsession.v2.cmn.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 他プラグインの情報をスケジュールへ表示する際使用するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchAppendDataModel extends AbstractModel {

    /** 他プラグインID */
    private String schPlgId__;
    /** 遷移先URL */
    private String schPlgUrl__;
    /** スケジュール時間文字列 */
    private String time__;
    /** 内容 */
    private String valueStr__;
    /** スケジュールタイトル文字列 */
    private String title__;
    /** スケジュール公開区分 */
    private int public__;
    /** 背景色 */
    private int bgColor__;
    /** スケジュール開始日時*/
    private UDate fromDate__;
    /** スケジュール終了日時*/
    private UDate toDate__;
    /** スケジュール時間指定区分 */
    private int timeKbn__;
    /** スケジュール戻り先URL */
    private String returnUrl__;

    /** ユーザSID */
    private int usrSid__;


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
     * <p>returnUrl を取得します。
     * @return returnUrl
     */
    public String getReturnUrl() {
        return returnUrl__;
    }
    /**
     * <p>returnUrl をセットします。
     * @param returnUrl returnUrl
     */
    public void setReturnUrl(String returnUrl) {
        returnUrl__ = returnUrl;
    }
    /**
     * <p>valueStr を取得します。
     * @return valueStr
     */
    public String getValueStr() {
        return valueStr__;
    }
    /**
     * <p>valueStr をセットします。
     * @param valueStr valueStr
     */
    public void setValueStr(String valueStr) {
        valueStr__ = valueStr;
    }
    /**
     * @return timeKbn を戻します。
     */
    public int getTimeKbn() {
        return timeKbn__;
    }
    /**
     * @param timeKbn 設定する timeKbn。
     */
    public void setTimeKbn(int timeKbn) {
        timeKbn__ = timeKbn;
    }
    /**
     * @return bgColor を戻します。
     */
    public int getBgColor() {
        return bgColor__;
    }
    /**
     * @param bgColor 設定する bgColor。
     */
    public void setBgColor(int bgColor) {
        bgColor__ = bgColor;
    }
    /**
     * @return fromDate を戻します。
     */
    public UDate getFromDate() {
        return fromDate__;
    }
    /**
     * @param fromDate 設定する fromDate。
     */
    public void setFromDate(UDate fromDate) {
        fromDate__ = fromDate;
    }
    /**
     * @return toDate を戻します。
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * @param toDate 設定する toDate。
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }
    /**
     * @return public を戻します。
     */
    public int getPublic() {
        return public__;
    }
    /**
     * @param public1 設定する public。
     */
    public void setPublic(int public1) {
        public__ = public1;
    }
    /**
     * @return time を戻します。
     */
    public String getTime() {
        return time__;
    }
    /**
     * @param time 設定する time。
     */
    public void setTime(String time) {
        time__ = time;
    }
    /**
     * @return title を戻します。
     */
    public String getTitle() {
        return title__;
    }
    /**
     * @param title 設定する title。
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>schPlgUrl を取得します。
     * @return schPlgUrl
     */
    public String getSchPlgUrl() {
        return schPlgUrl__;
    }
    /**
     * <p>schPlgUrl をセットします。
     * @param schPlgUrl schPlgUrl
     */
    public void setSchPlgUrl(String schPlgUrl) {
        schPlgUrl__ = schPlgUrl;
    }
    /**
     * <p>schPlgId を取得します。
     * @return schPlgId
     */
    public String getSchPlgId() {
        return schPlgId__;
    }
    /**
     * <p>schPlgId をセットします。
     * @param schPlgId schPlgId
     */
    public void setSchPlgId(String schPlgId) {
        schPlgId__ = schPlgId;
    }
}
