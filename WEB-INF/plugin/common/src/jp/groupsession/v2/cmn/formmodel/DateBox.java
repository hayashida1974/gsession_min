package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 日付入力要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DateBox extends AbstractFormModel {
    /**
     *
     * <br>[機  能] 列挙型 日付選択
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    protected static enum EnumDefaultDateKbn implements IEnumMsgkeyValue {
        /** 未設定 */
        none(0, "cmn.notset"),
        /** 当日 */
        thatday(1, "cmn.thatday"),
        /** 週末 */
        weekend(2, "cmn.weekend"),
        /** 月初 */
        monthstart(3, "cmn.monthstart"),
        /** 月末 */
        monthend(4, "cmn.monthend");
        /** 選択用数値*/
        private int value__;
        /** 選択用メッセージキー*/
        private String msgKey__;

        /**
         * コンストラクタ
         * @param value 選択用数値
         * @param msgKey メッセージキー
         */
        private EnumDefaultDateKbn(int value, String msgKey) {
            value__ = value;
            msgKey__ = msgKey;
        }
        @Override
        public int getValue() {
            return value__;
        }
        @Override
        public String getMsgKey() {
            return msgKey__;
        }
        /**
         *
         * <br>[機  能] 値から列挙型を返す
         * <br>[解  説]
         * <br>[備  考]
         * @param val 判定値
         * @return 列挙型
         * @throws EnumOutRangeException 列挙型範囲外例外
         */
        public static EnumDefaultDateKbn valueOf(int val) throws EnumOutRangeException {
            switch (val) {
            case 0:
                return none;
            case 1:
                return thatday;
            case 2:
                return weekend;
            case 3:
                return monthstart;
            case 4:
                return monthend;
            default:
                throw new EnumOutRangeException();
            }
        }
    }
    /** 初期値 区分 */
    private int defaultDateKbn__;
    /** 初期値 インデント */
    private int defaultIndent__;
    /** 入力値*/
    private String value__;

    /** ガラケー用 年選択*/
    private String yearMbh__;
    /** ガラケー用 月選択*/
    private String monthMbh__;
    /** ガラケー用 日選択*/
    private String dayMbh__;
    /** ガラケー用 年リスト */
    private ArrayList <LabelValueBean> yearLavel__ = null;
    /** ガラケー用 月リスト */
    private ArrayList <LabelValueBean> monthLavel__ = null;
    /** ガラケー用 日リスト */
    private ArrayList <LabelValueBean> dayLavel__ = null;


    /**
     * <p>yearLavel を取得します。
     * @return yearLavel
     * @see jp.groupsession.v2.cmn.formmodel.DateBox#yearLavel__
     */
    public ArrayList<LabelValueBean> getYearLavel() {
        return yearLavel__;
    }

    /**
     * <p>monthLavel を取得します。
     * @return monthLavel
     * @see jp.groupsession.v2.cmn.formmodel.DateBox#monthLavel__
     */
    public ArrayList<LabelValueBean> getMonthLavel() {
        return monthLavel__;
    }

    /**
     * <p>dayLavel を取得します。
     * @return dayLavel
     * @see jp.groupsession.v2.cmn.formmodel.DateBox#dayLavel__
     */
    public ArrayList<LabelValueBean> getDayLavel() {
        return dayLavel__;
    }

    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }
    /**
     * <p>defaultDateKbn を取得します。
     * @return defaultDateKbn
     */
    public int getDefaultDateKbn() {
        return defaultDateKbn__;
    }

    /**
     * <p>defaultDateKbn をセットします。
     * @param defaultDateKbn defaultDateKbn
     */
    public void setDefaultDateKbn(int defaultDateKbn) {
        defaultDateKbn__ = defaultDateKbn;
    }

    /**
     * <p>defaultIndent を取得します。
     * @return defaultIndent
     */
    public int getDefaultIndent() {
        return defaultIndent__;
    }

    /**
     * <p>defaultIndent をセットします。
     * @param defaultIndent defaultIndent
     */
    public void setDefaultIndent(int defaultIndent) {
        defaultIndent__ = defaultIndent;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".value", getValue());
    }

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
        }
        if (jsonObj == null) {
            return;
        }
        try {
            setValue(jsonObj.getString("value"));
        } catch (JSONException e) {

        }
        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
            return;
        }
        /** 初期値 区分*/
        try {
            setDefaultDateKbn(jsonObj.getInt("defaultDateKbn"));
        } catch (JSONException e) {

        }
        /** 初期値 インデント*/
        try {
            setDefaultIndent(jsonObj.getInt("defaultIndent"));
        } catch (JSONException e) {

        }

    }
    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + defaultDateKbn__;
        result = prime * result + defaultIndent__;
        result = prime * result + ((value__ == null) ? 0 : value__.hashCode());
        return result;
    }
    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DateBox)) {
            return false;
        }
        DateBox other = (DateBox) obj;
        if (defaultDateKbn__ != other.defaultDateKbn__) {
            return false;
        }
        if (defaultIndent__ != other.defaultIndent__) {
            return false;
        }
        if (value__ == null) {
            if (other.value__ != null) {
                return false;
            }
        } else if (!value__.equals(other.value__)) {
            return false;
        }
        return true;
    }

    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.date;
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {
        yearLavel__ = __createYearLabel(reqMdl);
        monthLavel__ = __createMonthLavel(reqMdl);
        dayLavel__ = __createDayLavel(reqMdl);

        super.dspInit(reqMdl, con);
    }
    @Override
    public void merge(AbstractFormModel obj) {
        super.merge(obj);

        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        if (!(obj instanceof DateBox)) {
            return;
        }
        DateBox other = (DateBox) obj;
        if (StringUtil.isNullZeroString(getValue())) {
            setValue(other.getValue());
        }
    }
    @Override
    public void defaultInit() {
        setValue("");
        UDate defDate = new UDate();
        try {
            EnumDefaultDateKbn dayKbn = EnumDefaultDateKbn.valueOf(defaultDateKbn__);
            switch (dayKbn) {
            case none:
                return;
            case thatday:
                break;
            case weekend:
                defDate.setDayOfWeek(Calendar.FRIDAY);
                break;
            case monthstart:
                defDate.setDay(1);
                break;
            case monthend:
                defDate.setDay(defDate.getMaxDayOfMonth());
                break;
            default:
                return;
            }
        } catch (EnumOutRangeException e) {
            return;
        }
        defDate.addDay(defaultIndent__);
        setValue(UDateUtil.getSlashYYMD(defDate));
    }
    @Override
    public void merge(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return;
        }
        setValue(values[0]);
    }
    /**
     *
     * <br>[機  能] 入力した日付文字列をUDate型で返す
     * <br>[解  説]
     * <br>[備  考]
     * @return UDate
     */
    public UDate dspUDate() {
        return UDate.getInstanceStr(getValue());
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCommon.validateDateFieldText(errors,
                getValue(),
                info.outputName(gsMsg),
                info.outputCode(),
                info.chkRequire());
    }
    @Override
    public String getCalced() {
        return getValue();
    }
    /**
     *
     * <br>[機  能] ガラケー用 年選択設定
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     */
    public void setYearMbh(String year) {
        yearMbh__ = year;
        __uddateMbhValue();
    }
    /**
     *
     * <br>[機  能] ガラケー用 月選択設定
     * <br>[解  説]
     * <br>[備  考]
     * @param month 月
     */
    public void setMonthMbh(String month) {
        monthMbh__ = month;
        __uddateMbhValue();
    }
    /**
     *
     * <br>[機  能] ガラケー用 日選択設定
     * <br>[解  説]
     * <br>[備  考]
     * @param day 日
     */
    public void setDayMbh(String day) {
        dayMbh__ = day;
        __uddateMbhValue();
    }
    /**
     * <p>year を取得します。
     * @return year
     * @see jp.groupsession.v2.cmn.formmodel.DateBox#yearMbh__
     */
    public String getYearMbh() {
        String value = getValue();
        if (!StringUtil.isNullZeroString(value)
                && ValidateUtil.isSlashDateFormat(value)) {
            UDate date = UDate.getInstanceStr(getValue());
            return String.valueOf(date.getYear());
        }
        return "";
    }

    /**
     * <p>month を取得します。
     * @return month
     * @see jp.groupsession.v2.cmn.formmodel.DateBox#monthMbh__
     */
    public String getMonthMbh() {
        String value = getValue();
        if (!StringUtil.isNullZeroString(value)
                && ValidateUtil.isSlashDateFormat(value)) {
            UDate date = UDate.getInstanceStr(getValue());
            return String.valueOf(date.getMonth());
        }
        return "";
    }

    /**
     * <p>day を取得します。
     * @return day
     * @see jp.groupsession.v2.cmn.formmodel.DateBox#dayMbh__
     */
    public String getDayMbh() {
        String value = getValue();
        if (!StringUtil.isNullZeroString(value)
                && ValidateUtil.isSlashDateFormat(value)) {
            UDate date = UDate.getInstanceStr(getValue());
            return String.valueOf(date.getIntDay());
        }
        return "";
    }
    /**
     * <br>[機  能] 表示開始日から年コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    private ArrayList<LabelValueBean> __createYearLabel(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        UDate date;
        String value = getValue();
        if (!StringUtil.isNullZeroString(value)
                && ValidateUtil.isSlashDateFormat(value)) {
            date = UDate.getInstanceStr(value);
        } else {
            date = new UDate();
        }
        int year = date.getYear();
        year--;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //未選択を追加
        labelList.add(new LabelValueBean(gsMsg.getMessage("project.src.1"), ""));
        for (int i = 0; i < GSConstSchedule.YEAR_LIST_CNT; i++) {
            labelList.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.year", new String[] {String.valueOf(year)}),
                            String.valueOf(year)));
            year++;
        }
        return labelList;
    }
    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    private ArrayList<LabelValueBean> __createMonthLavel(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        int month = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //未選択を追加
        labelList.add(new LabelValueBean(gsMsg.getMessage("project.src.1"), ""));
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + gsMsg.getMessage("cmn.month"),
                            String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 日コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    private ArrayList<LabelValueBean> __createDayLavel(RequestModel reqMdl) {
        int day = 1;
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //未選択を追加
        labelList.add(new LabelValueBean(gsMsg.getMessage("project.src.1"), ""));
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + gsMsg.getMessage(
                            "cmn.day"), String.valueOf(day)));
            day++;
        }
        return labelList;
    }

    /**
     *
     * <br>[機  能] モバイル用の年月日入力欄がそろった場合、valueを更新
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __uddateMbhValue() {
        if (!StringUtil.isNullZeroString(dayMbh__)
                && !StringUtil.isNullZeroString(monthMbh__)
                && !StringUtil.isNullZeroString(yearMbh__)
                ) {
            UDate date = UDateUtil.getUDate(yearMbh__, monthMbh__, dayMbh__);
            setValue(UDateUtil.getSlashYYMD(date));
        }
    }
}
