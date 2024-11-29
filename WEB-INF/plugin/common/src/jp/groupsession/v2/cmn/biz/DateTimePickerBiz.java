package jp.groupsession.v2.cmn.biz;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
/**
 *
 * <br>[機  能] DatePiker, TimePikerに関する共通機能を提供するビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DateTimePickerBiz {

    /**
     *
     * <br>[機  能] 年・月・日パラメータを日付パラメータ(yyyy/MM/dd)に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param param Form, またはParamModel
     * @param dateParamName 日付パラメータ名
     * @param yearParamName 年パラメータ名
     * @param monthParamName 月パラメータ名
     * @param dayParamName 日パラメータ名
     * @param dateParamNameJp 日付パラメータ名(表示名)
     * @throws IllegalAccessException 指定されたパラメータへのアクセスに失敗
     * @throws InvocationTargetException  指定されたパラメータへのアクセスに失敗
     * @throws NoSuchMethodException - 指定されたパラメータが存在しない
     * @return ActionErrors(年・月・日パラメータが正常な場合、空)
     */
    public ActionErrors setDateParam(Object param,
                                    String dateParamName,
                                    String yearParamName,
                                    String monthParamName,
                                    String dayParamName,
                                    String dateParamNameJp)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();

        //年・月・日パラメータのチェック
        String year = NullDefault.getString(BeanUtils.getProperty(param, yearParamName), "");
        String month = NullDefault.getString(BeanUtils.getProperty(param, monthParamName), "");
        String day = NullDefault.getString(BeanUtils.getProperty(param, dayParamName), "");

        String dateStr = year
                + "/" + __toDecFormat(month)
                + "/" + __toDecFormat(day);
        if (!ValidateUtil.isSlashDateFormat(dateStr)
        || !ValidateUtil.isExistDateYyyyMMdd(dateStr.replaceAll("/", ""))) {
            ActionMessage msg = new ActionMessage("error.input.notfound.date", dateParamNameJp);
            errors.add(dateParamName + ".error.input.notfound.date", msg);
            dateStr = null;
        }

        BeanUtils.setProperty(param, dateParamName, dateStr);

        return errors;
    }

    /**
     *
     * <br>[機  能] 日付パラメータに設定された日付を年・月・日パラメータに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param param Form, またはParamModel
     * @param dateParamName 日付パラメータ名
     * @param yearParamName 年パラメータ名
     * @param monthParamName 月パラメータ名
     * @param dayParamName 日パラメータ名
     * @param dateParamNameJp 日付パラメータ名(表示名)
     * @throws IllegalAccessException 指定されたパラメータへのアクセスに失敗
     * @throws InvocationTargetException  指定されたパラメータへのアクセスに失敗
     * @throws NoSuchMethodException - 指定されたパラメータが存在しない
     * @return ActionErrors(年・月・日パラメータが正常な場合、空)
     */
    public ActionErrors setYmdParam(Object param,
                                    String dateParamName,
                                    String yearParamName,
                                    String monthParamName,
                                    String dayParamName,
                                    String dateParamNameJp)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();

        String year = "";
        String month = "";
        String day = "";

        //日付パラメータのチェック
        String date = BeanUtils.getProperty(param, dateParamName);
        if (date == null
        || !ValidateUtil.isSlashDateFormat(date)) {
            ActionMessage msg = new ActionMessage("error.input.notfound.date", dateParamNameJp);
            errors.add(dateParamName + ".error.input.notfound.date", msg);
        } else {
            String[] dateValue = date.split("/");
            year = __getNumValue(dateValue[0]);
            month = __getNumValue(dateValue[1]);
            day = __getNumValue(dateValue[2]);

            //存在しない日付チェック
            date = year + __toDecFormat(month) + __toDecFormat(day);
            if (!ValidateUtil.isExistDateYyyyMMdd(date)) {
                ActionMessage msg = new ActionMessage("error.input.notfound.date", dateParamNameJp);
                errors.add(dateParamName + ".error.input.notfound.date", msg);
                year = "";
                month = "";
                day = "";
            }
        }

        //年・月・日パラメータの設定
        BeanUtils.setProperty(param, yearParamName, year);
        BeanUtils.setProperty(param, monthParamName, month);
        BeanUtils.setProperty(param, dayParamName, day);

        return errors;
    }

    /**
     *
     * <br>[機  能] 時・分パラメータを時刻パラメータ(hh:mm)に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param param Form, またはParamModel
     * @param timeParamName 時刻パラメータ名
     * @param hourParamName 時パラメータ名
     * @param minuteParamName 分パラメータ名
     * @param timeParamNameJp 時刻パラメータ名(表示名)
     * @throws IllegalAccessException 指定されたパラメータへのアクセスに失敗
     * @throws InvocationTargetException  指定されたパラメータへのアクセスに失敗
     * @throws NoSuchMethodException - 指定されたパラメータが存在しない
     * @return ActionErrors(年・月・日パラメータが正常な場合、空)
     */
    public ActionErrors setTimeParam(Object param,
                                    String timeParamName,
                                    String hourParamName,
                                    String minuteParamName,
                                    String timeParamNameJp)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();

        String hour = NullDefault.getString(BeanUtils.getProperty(param, hourParamName), "99");
        String minute = NullDefault.getString(BeanUtils.getProperty(param, minuteParamName), "99");

        //時・分パラメータのチェック
        String timeStr = __toDecFormat(hour) + ":" + __toDecFormat(minute);
        if (!ValidateUtil.isTimeFormat(timeStr)) {
            ActionMessage msg = new ActionMessage("error.input.required.text", timeParamNameJp);
            errors.add("timeParamName." + "error.input.required.text", msg);
            timeStr = null;
        }

        BeanUtils.setProperty(param, timeParamName, timeStr);

        return errors;
    }

    /**
     * <br>[機  能] 時刻パラメータに設定された時刻を時・分パラメータに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param param Form, またはParamModel
     * @param timeParamName 時刻パラメータ名
     * @param hourParamName 時パラメータ名
     * @param minuteParamName 分パラメータ名
     * @param timeParamNameJp 時刻パラメータ名(表示名)
     * @throws IllegalAccessException 指定されたパラメータへのアクセスに失敗
     * @throws InvocationTargetException  指定されたパラメータへのアクセスに失敗
     * @throws NoSuchMethodException - 指定されたパラメータが存在しない
     * @return ActionErrors(年・月・日パラメータが正常な場合、空)
     */
    public ActionErrors setHmParam(Object param,
                                    String timeParamName,
                                    String hourParamName,
                                    String minuteParamName,
                                    String timeParamNameJp)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();

        String hour = "";
        String minute = "";

        //時刻パラメータのチェック
        String timeStr = BeanUtils.getProperty(param, timeParamName);
        if (StringUtil.isNullZeroString(timeStr)
        || !ValidateUtil.isTimeFormat(timeStr)) {

            ActionMessage msg = new ActionMessage("error.input.required.text", timeParamNameJp);
            errors.add("timeParamName." + "error.input.required.text", msg);
        } else {
            String[] timeValue = timeStr.split(":");
            hour = __getNumValue(timeValue[0]);
            minute = __getNumValue(timeValue[1]);
        }

        BeanUtils.setProperty(param, hourParamName, hour);
        BeanUtils.setProperty(param, minuteParamName, minute);

        return errors;
    }

    /**
     * <br>[機  能] 指定された文字列の0埋めを行う
     * <br>[解  説]
     * <br>[備  考] 文字列が数値型以外の場合、0埋めを行わずにそのまま返す
     * @param value 文字列
     * @return 0埋めした文字列
     */
    private String __toDecFormat(String value) {
        String strValue = value;
        if (ValidateUtil.isNumber(strValue)) {
            strValue = StringUtil.toDecFormat(value, "00");
        }
        return strValue;
    }

    /**
     * <br>[機  能] 指定した文字列の部分文字列を取得する
     * <br>[解  説]
     * <br>[備  考] 部分文字列の0埋め解消を行う
     * @param value 文字列
     * @return 部分文字列
     */
    private String __getNumValue(String value) {
        //0埋め解消
        if (ValidateUtil.isNumber(value)) {
            value = String.valueOf(Integer.parseInt(value));
        }

        return value;
    }
}
