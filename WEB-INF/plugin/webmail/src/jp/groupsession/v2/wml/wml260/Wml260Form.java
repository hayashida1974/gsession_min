package jp.groupsession.v2.wml.wml260;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.wml020.Wml020Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール 送信予定メール管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260Form extends Wml020Form {

    /** 検索フラグ */
    private int wml260searchFlg__ = GSConstWebmail.SEARCH_EXECUTE_TRUE;
    /** アカウント名 */
    private String wml260AccountName__ = null;
    /** 件名 */
    private String wml260Title__ = null;
    /** メールアドレス */
    private String wml260Address__ = null;
    /** メールアドレス 差出人 */
    private int wml260AddressFrom__ = GSConstWebmail.ADDRESS_KBN_NO;
    /** メールアドレス 宛先 */
    private int wml260AddressTo__ = GSConstWebmail.ADDRESS_KBN_NO;
    /** 日付 年 */
    private int wml260SendDateYear__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付 月 */
    private int wml260SendDateMonth__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付 日 */
    private int wml260SendDateDay__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付 条件 */
    private int wml260SendDateCondition__ = GSConstWebmail.DATE_KBN_EQUAL;
    /** 日付TO 年 */
    private int wml260SendDateYearTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付TO 月 */
    private int wml260SendDateMonthTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付TO 日 */
    private int wml260SendDateDayTo__ = GSConstWebmail.SELECT_DATECOMBO;

    /** ページ1 */
    private int wml260pageTop__ = 1;
    /** ページ2 */
    private int wml260pageBottom__ = 1;
    /** ページ表示フラグ */
    private boolean wml260pageDspFlg__ = false;
    /** ページコンボ */
    private List<LabelValueBean> pageList__ = null;
    /** 年コンボ */
    private List<LabelValueBean> yearLabel__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabel__ = null;
    /** 日コンボ */
    private List<LabelValueBean> dayLabel__ = null;

    /** 予約送信メール一覧 */
    private List<Wml260MaildataDspModel> wml260SendResvList__ = null;

    /** ソートキー */
    private int wml260sortKey__ = GSConstWebmail.SKEY_DATE;
    /** 並び順 */
    private int wml260order__ = GSConstWebmail.ORDER_ASC;

    /** メッセージNo */
    private long wml080mailNum__ = 0;
    /** 選択されたメッセージ番号 */
    private long[] wml260selectMailNum__ = null;

    /*-- SVパラメータ start ----------------------------------------------------------*/
    /** アカウント名 */
    private String wml260svAccountName__ = null;
    /** 件名 */
    private String wml260svTitle__ = null;
    /** メールアドレス */
    private String wml260svAddress__ = null;
    /** メールアドレス 差出人 */
    private int wml260svAddressFrom__ = GSConstWebmail.ADDRESS_KBN_NO;
    /** メールアドレス 宛先 */
    private int wml260svAddressTo__ = GSConstWebmail.ADDRESS_KBN_NO;
    /** 日時 年 */
    private int wml260svSendDateYear__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 月 */
    private int wml260svSendDateMonth__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 日 */
    private int wml260svSendDateDay__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日時 条件 */
    private int wml260svSendDateCondition__ = GSConstWebmail.DATE_KBN_EQUAL;
    /** 日付TO 年 */
    private int wml260svSendDateYearTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付TO 月 */
    private int wml260svSendDateMonthTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /** 日付TO 日 */
    private int wml260svSendDateDayTo__ = GSConstWebmail.SELECT_DATECOMBO;
    /*-- SVパラメータ end ----------------------------------------------------------*/

    /**
     * 検索条件パラメータをSAVEフィールドへ移行します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {

        wml260svAccountName__ = wml260AccountName__;
        wml260svTitle__ = wml260Title__;
        wml260svAddress__ = wml260Address__;
        wml260svAddressFrom__ = wml260AddressFrom__;
        wml260svAddressTo__ = wml260AddressTo__;
        wml260svSendDateYear__ = wml260SendDateYear__;
        wml260svSendDateMonth__ = wml260SendDateMonth__;
        wml260svSendDateDay__ = wml260SendDateDay__;
        wml260svSendDateYearTo__ = wml260SendDateYearTo__;
        wml260svSendDateMonthTo__ = wml260SendDateMonthTo__;
        wml260svSendDateDayTo__ = wml260SendDateDayTo__;
        wml260svSendDateCondition__ = wml260SendDateCondition__;
    }

    /**
     * <br>[機  能] 詳細検索画面入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateWml260Check(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();

        //アカウント名入力チェック
        GSValidateWebmail.validateTextBoxInput(errors, wml260AccountName__,
                "wml260AccountName",
                getInterMessage(req, GSConstWebmail.TEXT_ACCOUNT),
                GSConstWebmail.MAXLEN_ACCOUNT, false);

        //件名入力チェック
        GSValidateWebmail.validateTextBoxInput(errors, wml260Title__,
                "wml260Title",
                getInterMessage(req, GSConstWebmail.TEXT_NAME),
                GSConstWebmail.MAXLEN_NAME, false);

        //メールアドレス入力チェック
        if (wml260AddressFrom__ == GSConstWebmail.ADDRESS_KBN_OK
                || wml260AddressTo__ == GSConstWebmail.ADDRESS_KBN_OK) {
            GSValidateWebmail.validateMailSearchInput(errors, wml260Address__,
                    "wml260Address",
                    getInterMessage(req, GSConstWebmail.TEXT_ADDRESS),
                    GSConstWebmail.MAXLEN_ACCOUNT_ADDRESS, true);

        } else if (wml260AddressFrom__ == GSConstWebmail.ADDRESS_KBN_NO
                && wml260AddressTo__ == GSConstWebmail.ADDRESS_KBN_NO
                && wml260Address__ != null
                && wml260Address__.length() != 0) {

            ActionMessage msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, GSConstWebmail.TEXT_SEARCH_ADDRESS));
            StrutsUtil.addMessage(
                    errors, msg, "noCheckAdKbn" + "error.select.required.text");
        }

        //日付
        boolean toInput = false;
        String dateNameJpn = getInterMessage(req, GSConstWebmail.TEXT_DATE);

        int errCount = errors.size();
        if (wml260SendDateCondition__ == GSConstWebmail.DATE_KBN_DATEAREA) {
            toInput = (wml260SendDateYearTo__ != GSConstWebmail.SELECT_DATECOMBO
                    && wml260SendDateMonthTo__ != GSConstWebmail.SELECT_DATECOMBO
                    && wml260SendDateDayTo__ != GSConstWebmail.SELECT_DATECOMBO);
            dateNameJpn += " From";
        }
        GSValidateWebmail.validateDate(req, errors, "wml260SendDate",
                                    dateNameJpn, wml260SendDateYear__,
                                    wml260SendDateMonth__, wml260SendDateDay__,
                                    toInput);

        if (wml260SendDateCondition__ == GSConstWebmail.DATE_KBN_DATEAREA) {
            boolean fromInput = (wml260SendDateYear__ != GSConstWebmail.SELECT_DATECOMBO
                    && wml260SendDateMonth__ != GSConstWebmail.SELECT_DATECOMBO
                    && wml260SendDateDay__ != GSConstWebmail.SELECT_DATECOMBO);

            GSValidateWebmail.validateDate(req, errors, "wml260SendDateTo",
                    getInterMessage(req, GSConstWebmail.TEXT_DATE) + " To", wml260SendDateYearTo__,
                                    wml260SendDateMonthTo__, wml260SendDateDayTo__, fromInput);

            if (errCount == errors.size() && fromInput && toInput) {

                UDate fromDate = new UDate();
                fromDate.setDate(wml260SendDateYear__,
                                wml260SendDateMonth__,
                                wml260SendDateDay__);
                UDate toDate = new UDate();
                toDate.setDate(wml260SendDateYearTo__,
                            wml260SendDateMonthTo__,
                            wml260SendDateDayTo__);
                if (fromDate.compareDateYMD(toDate) == UDate.SMALL) {
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                                                        getInterMessage(req, "wml.204"),
                                                        getInterMessage(req, "wml.203"));
                    StrutsUtil.addMessage(errors, msg, "wml260SendDateTo.error.input.comp.text");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 取消対象のメールが選択されているかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateSelectMessage(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        if (getWml260selectMailNum() == null || getWml260selectMailNum().length <= 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            ActionMessage msg
                = new ActionMessage("error.select.required.text", gsMsg.getMessage("cmn.mail"));
            StrutsUtil.addMessage(errors, msg, "wml260selectMailNum");
        }

        return errors;
    }

    /**
     * <p>wml260searchFlg を取得します。
     * @return wml260searchFlg
     */
    public int getWml260searchFlg() {
        return wml260searchFlg__;
    }

    /**
     * <p>wml260searchFlg をセットします。
     * @param wml260searchFlg wml260searchFlg
     */
    public void setWml260searchFlg(int wml260searchFlg) {
        wml260searchFlg__ = wml260searchFlg;
    }

    /**
     * <p>wml260SendDateDay を取得します。
     * @return wml260SendDateDay
     */
    public int getWml260SendDateDay() {
        return wml260SendDateDay__;
    }

    /**
     * <p>wml260SendDateDay をセットします。
     * @param wml260SendDateDay wml260SendDateDay
     */
    public void setWml260SendDateDay(int wml260SendDateDay) {
        wml260SendDateDay__ = wml260SendDateDay;
    }

    /**
     * <p>wml260SendDateMonth を取得します。
     * @return wml260SendDateMonth
     */
    public int getWml260SendDateMonth() {
        return wml260SendDateMonth__;
    }

    /**
     * <p>wml260SendDateMonth をセットします。
     * @param wml260SendDateMonth wml260SendDateMonth
     */
    public void setWml260SendDateMonth(int wml260SendDateMonth) {
        wml260SendDateMonth__ = wml260SendDateMonth;
    }

    /**
     * <p>wml260SendDateYear を取得します。
     * @return wml260SendDateYear
     */
    public int getWml260SendDateYear() {
        return wml260SendDateYear__;
    }

    /**
     * <p>wml260SendDateYear をセットします。
     * @param wml260SendDateYear wml260SendDateYear
     */
    public void setWml260SendDateYear(int wml260SendDateYear) {
        wml260SendDateYear__ = wml260SendDateYear;
    }

    /**
     * <p>wml260svSendDateCondition を取得します。
     * @return wml260svSendDateCondition
     */
    public int getWml260svSendDateCondition() {
        return wml260svSendDateCondition__;
    }

    /**
     * <p>wml260svSendDateCondition をセットします。
     * @param wml260svSendDateCondition wml260svSendDateCondition
     */
    public void setWml260svSendDateCondition(int wml260svSendDateCondition) {
        wml260svSendDateCondition__ = wml260svSendDateCondition;
    }

    /**
     * <p>wml260svSendDateDay を取得します。
     * @return wml260svSendDateDay
     */
    public int getWml260svSendDateDay() {
        return wml260svSendDateDay__;
    }

    /**
     * <p>wml260svSendDateDay をセットします。
     * @param wml260svSendDateDay wml260svSendDateDay
     */
    public void setWml260svSendDateDay(int wml260svSendDateDay) {
        wml260svSendDateDay__ = wml260svSendDateDay;
    }

    /**
     * <p>wml260svSendDateMonth を取得します。
     * @return wml260svSendDateMonth
     */
    public int getWml260svSendDateMonth() {
        return wml260svSendDateMonth__;
    }

    /**
     * <p>wml260svSendDateMonth をセットします。
     * @param wml260svSendDateMonth wml260svSendDateMonth
     */
    public void setWml260svSendDateMonth(int wml260svSendDateMonth) {
        wml260svSendDateMonth__ = wml260svSendDateMonth;
    }

    /**
     * <p>wml260svSendDateYear を取得します。
     * @return wml260svSendDateYear
     */
    public int getWml260svSendDateYear() {
        return wml260svSendDateYear__;
    }

    /**
     * <p>wml260svSendDateYear をセットします。
     * @param wml260svSendDateYear wml260svSendDateYear
     */
    public void setWml260svSendDateYear(int wml260svSendDateYear) {
        wml260svSendDateYear__ = wml260svSendDateYear;
    }

    /**
     * <p>wml260svTitle を取得します。
     * @return wml260svTitle
     */
    public String getWml260svTitle() {
        return wml260svTitle__;
    }

    /**
     * <p>wml260svTitle をセットします。
     * @param wml260svTitle wml260svTitle
     */
    public void setWml260svTitle(String wml260svTitle) {
        wml260svTitle__ = wml260svTitle;
    }

    /**
     * <p>wml260Title を取得します。
     * @return wml260Title
     */
    public String getWml260Title() {
        return wml260Title__;
    }

    /**
     * <p>wml260Title をセットします。
     * @param wml260Title wml260Title
     */
    public void setWml260Title(String wml260Title) {
        wml260Title__ = wml260Title;
    }

    /**
     * <p>wml260pageDspFlg を取得します。
     * @return wml260pageDspFlg
     */
    public boolean isWml260pageDspFlg() {
        return wml260pageDspFlg__;
    }

    /**
     * <p>wml260pageDspFlg をセットします。
     * @param wml260pageDspFlg wml260pageDspFlg
     */
    public void setWml260pageDspFlg(boolean wml260pageDspFlg) {
        wml260pageDspFlg__ = wml260pageDspFlg;
    }

    /**
     * <p>pageList を取得します。
     * @return pageList
     */
    public List<LabelValueBean> getPageList() {
        return pageList__;
    }

    /**
     * <p>pageList をセットします。
     * @param pageList pageList
     */
    public void setPageList(List<LabelValueBean> pageList) {
        pageList__ = pageList;
    }

    /**
     * <p>wml260SendResvList を取得します。
     * @return wml260SendResvList
     */
    public List<Wml260MaildataDspModel> getWml260SendResvList() {
        return wml260SendResvList__;
    }

    /**
     * <p>wml260SendResvList をセットします。
     * @param wml260SendResvList wml260SendResvList
     */
    public void setWml260SendResvList(List<Wml260MaildataDspModel> wml260SendResvList) {
        wml260SendResvList__ = wml260SendResvList;
    }

    /**
     * <p>dayLabel を取得します。
     * @return dayLabel
     */
    public List<LabelValueBean> getDayLabel() {
        return dayLabel__;
    }

    /**
     * <p>dayLabel をセットします。
     * @param dayLabel dayLabel
     */
    public void setDayLabel(List<LabelValueBean> dayLabel) {
        dayLabel__ = dayLabel;
    }

    /**
     * <p>monthLabel を取得します。
     * @return monthLabel
     */
    public List<LabelValueBean> getMonthLabel() {
        return monthLabel__;
    }

    /**
     * <p>monthLabel をセットします。
     * @param monthLabel monthLabel
     */
    public void setMonthLabel(List<LabelValueBean> monthLabel) {
        monthLabel__ = monthLabel;
    }

    /**
     * <p>yearLabel を取得します。
     * @return yearLabel
     */
    public List<LabelValueBean> getYearLabel() {
        return yearLabel__;
    }

    /**
     * <p>yearLabel をセットします。
     * @param yearLabel yearLabel
     */
    public void setYearLabel(List<LabelValueBean> yearLabel) {
        yearLabel__ = yearLabel;
    }

    /**
     * <p>wml260order を取得します。
     * @return wml260order
     */
    public int getWml260order() {
        return wml260order__;
    }

    /**
     * <p>wml260order をセットします。
     * @param wml260order wml260order
     */
    public void setWml260order(int wml260order) {
        wml260order__ = wml260order;
    }

    /**
     * <p>wml260sortKey を取得します。
     * @return wml260sortKey
     */
    public int getWml260sortKey() {
        return wml260sortKey__;
    }

    /**
     * <p>wml260sortKey をセットします。
     * @param wml260sortKey wml260sortKey
     */
    public void setWml260sortKey(int wml260sortKey) {
        wml260sortKey__ = wml260sortKey;
    }

    /**
     * <p>wml260selectMailNum を取得します。
     * @return wml260selectMailNum
     */
    public long[] getWml260selectMailNum() {
        return wml260selectMailNum__;
    }

    /**
     * <p>wml260selectMailNum をセットします。
     * @param wml260selectMailNum wml260selectMailNum
     */
    public void setWml260selectMailNum(long[] wml260selectMailNum) {
        wml260selectMailNum__ = wml260selectMailNum;
    }

    /**
     * <p>wml260AccountName を取得します。
     * @return wml260AccountName
     */
    public String getWml260AccountName() {
        return wml260AccountName__;
    }

    /**
     * <p>wml260AccountName をセットします。
     * @param wml260AccountName wml260AccountName
     */
    public void setWml260AccountName(String wml260AccountName) {
        wml260AccountName__ = wml260AccountName;
    }

    /**
     * <p>wml260Address を取得します。
     * @return wml260Address
     */
    public String getWml260Address() {
        return wml260Address__;
    }

    /**
     * <p>wml260Address をセットします。
     * @param wml260Address wml260Address
     */
    public void setWml260Address(String wml260Address) {
        wml260Address__ = wml260Address;
    }

    /**
     * <p>wml260AddressFrom を取得します。
     * @return wml260AddressFrom
     */
    public int getWml260AddressFrom() {
        return wml260AddressFrom__;
    }

    /**
     * <p>wml260AddressFrom をセットします。
     * @param wml260AddressFrom wml260AddressFrom
     */
    public void setWml260AddressFrom(int wml260AddressFrom) {
        wml260AddressFrom__ = wml260AddressFrom;
    }

    /**
     * <p>wml260AddressTo を取得します。
     * @return wml260AddressTo
     */
    public int getWml260AddressTo() {
        return wml260AddressTo__;
    }

    /**
     * <p>wml260AddressTo をセットします。
     * @param wml260AddressTo wml260AddressTo
     */
    public void setWml260AddressTo(int wml260AddressTo) {
        wml260AddressTo__ = wml260AddressTo;
    }

    /**
     * <p>wml260SendDateCondition を取得します。
     * @return wml260SendDateCondition
     */
    public int getWml260SendDateCondition() {
        return wml260SendDateCondition__;
    }

    /**
     * <p>wml260SendDateCondition をセットします。
     * @param wml260SendDateCondition wml260SendDateCondition
     */
    public void setWml260SendDateCondition(int wml260SendDateCondition) {
        wml260SendDateCondition__ = wml260SendDateCondition;
    }

    /**
     * <p>wml260svAccountName を取得します。
     * @return wml260svAccountName
     */
    public String getWml260svAccountName() {
        return wml260svAccountName__;
    }

    /**
     * <p>wml260svAccountName をセットします。
     * @param wml260svAccountName wml260svAccountName
     */
    public void setWml260svAccountName(String wml260svAccountName) {
        wml260svAccountName__ = wml260svAccountName;
    }

    /**
     * <p>wml260svAddress を取得します。
     * @return wml260svAddress
     */
    public String getWml260svAddress() {
        return wml260svAddress__;
    }

    /**
     * <p>wml260svAddress をセットします。
     * @param wml260svAddress wml260svAddress
     */
    public void setWml260svAddress(String wml260svAddress) {
        wml260svAddress__ = wml260svAddress;
    }

    /**
     * <p>wml260svAddressFrom を取得します。
     * @return wml260svAddressFrom
     */
    public int getWml260svAddressFrom() {
        return wml260svAddressFrom__;
    }

    /**
     * <p>wml260svAddressFrom をセットします。
     * @param wml260svAddressFrom wml260svAddressFrom
     */
    public void setWml260svAddressFrom(int wml260svAddressFrom) {
        wml260svAddressFrom__ = wml260svAddressFrom;
    }

    /**
     * <p>wml260svAddressTo を取得します。
     * @return wml260svAddressTo
     */
    public int getWml260svAddressTo() {
        return wml260svAddressTo__;
    }

    /**
     * <p>wml260svAddressTo をセットします。
     * @param wml260svAddressTo wml260svAddressTo
     */
    public void setWml260svAddressTo(int wml260svAddressTo) {
        wml260svAddressTo__ = wml260svAddressTo;
    }

    /**
     * <p>wml260pageBottom を取得します。
     * @return wml260pageBottom
     */
    public int getWml260pageBottom() {
        return wml260pageBottom__;
    }

    /**
     * <p>wml260pageBottom をセットします。
     * @param wml260pageBottom wml260pageBottom
     */
    public void setWml260pageBottom(int wml260pageBottom) {
        wml260pageBottom__ = wml260pageBottom;
    }

    /**
     * <p>wml260pageTop を取得します。
     * @return wml260pageTop
     */
    public int getWml260pageTop() {
        return wml260pageTop__;
    }

    /**
     * <p>wml260pageTop をセットします。
     * @param wml260pageTop wml260pageTop
     */
    public void setWml260pageTop(int wml260pageTop) {
        wml260pageTop__ = wml260pageTop;
    }

    /**
     * <p>wml080mailNum を取得します。
     * @return wml080mailNum
     */
    public long getWml080mailNum() {
        return wml080mailNum__;
    }

    /**
     * <p>wml080mailNum をセットします。
     * @param wml080mailNum wml080mailNum
     */
    public void setWml080mailNum(long wml080mailNum) {
        wml080mailNum__ = wml080mailNum;
    }

    /**
     * <p>wml260SendDateDayTo を取得します。
     * @return wml260SendDateDayTo
     */
    public int getWml260SendDateDayTo() {
        return wml260SendDateDayTo__;
    }

    /**
     * <p>wml260SendDateDayTo をセットします。
     * @param wml260SendDateDayTo wml260SendDateDayTo
     */
    public void setWml260SendDateDayTo(int wml260SendDateDayTo) {
        wml260SendDateDayTo__ = wml260SendDateDayTo;
    }

    /**
     * <p>wml260SendDateMonthTo を取得します。
     * @return wml260SendDateMonthTo
     */
    public int getWml260SendDateMonthTo() {
        return wml260SendDateMonthTo__;
    }

    /**
     * <p>wml260SendDateMonthTo をセットします。
     * @param wml260SendDateMonthTo wml260SendDateMonthTo
     */
    public void setWml260SendDateMonthTo(int wml260SendDateMonthTo) {
        wml260SendDateMonthTo__ = wml260SendDateMonthTo;
    }

    /**
     * <p>wml260SendDateYearTo を取得します。
     * @return wml260SendDateYearTo
     */
    public int getWml260SendDateYearTo() {
        return wml260SendDateYearTo__;
    }

    /**
     * <p>wml260SendDateYearTo をセットします。
     * @param wml260SendDateYearTo wml260SendDateYearTo
     */
    public void setWml260SendDateYearTo(int wml260SendDateYearTo) {
        wml260SendDateYearTo__ = wml260SendDateYearTo;
    }

    /**
     * <p>wml260svSendDateDayTo を取得します。
     * @return wml260svSendDateDayTo
     */
    public int getWml260svSendDateDayTo() {
        return wml260svSendDateDayTo__;
    }

    /**
     * <p>wml260svSendDateDayTo をセットします。
     * @param wml260svSendDateDayTo wml260svSendDateDayTo
     */
    public void setWml260svSendDateDayTo(int wml260svSendDateDayTo) {
        wml260svSendDateDayTo__ = wml260svSendDateDayTo;
    }

    /**
     * <p>wml260svSendDateMonthTo を取得します。
     * @return wml260svSendDateMonthTo
     */
    public int getWml260svSendDateMonthTo() {
        return wml260svSendDateMonthTo__;
    }

    /**
     * <p>wml260svSendDateMonthTo をセットします。
     * @param wml260svSendDateMonthTo wml260svSendDateMonthTo
     */
    public void setWml260svSendDateMonthTo(int wml260svSendDateMonthTo) {
        wml260svSendDateMonthTo__ = wml260svSendDateMonthTo;
    }

    /**
     * <p>wml260svSendDateYearTo を取得します。
     * @return wml260svSendDateYearTo
     */
    public int getWml260svSendDateYearTo() {
        return wml260svSendDateYearTo__;
    }

    /**
     * <p>wml260svSendDateYearTo をセットします。
     * @param wml260svSendDateYearTo wml260svSendDateYearTo
     */
    public void setWml260svSendDateYearTo(int wml260svSendDateYearTo) {
        wml260svSendDateYearTo__ = wml260svSendDateYearTo;
    }
}
