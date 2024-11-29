package jp.groupsession.v2.cmn.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 通知時間選択ラベルリストモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum SchEnumReminderTime implements IEnumMsgkeyValue {
    /** リマインダー通知 通知時間 通知しない */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_NO,
        msgKey = "schedule.reminder.timesel.0")
    REMINDER_TIME_NO,
    /** リマインダー通知 通知時間 0分前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_ZERO,
        msgKey = "schedule.reminder.timesel.1")
    REMINDER_TIME_ZERO,
    /** リマインダー通知 通知時間 5分前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_FIVE_MINUTES,
        msgKey = "schedule.reminder.timesel.2")
    REMINDER_TIME_FIVE_MINUTES,
    /** リマインダー通知 通知時間 10分前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_TEN_MINUTES,
        msgKey = "schedule.reminder.timesel.3")
    REMINDER_TIME_TEN_MINUTES,
    /** リマインダー通知 通知時間 15分前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES,
        msgKey = "schedule.reminder.timesel.4")
    REMINDER_TIME_FIFTEEN_MINUTES,
    /** リマインダー通知 通知時間 30分前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_THIRTY_MINUTES,
        msgKey = "schedule.reminder.timesel.5")
    REMINDER_TIME_THIRTY_MINUTES,
    /** リマインダー通知 通知時間 1時間前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_ONE_HOUR,
        msgKey = "schedule.reminder.timesel.6")
    REMINDER_TIME_ONE_HOUR,
    /** リマインダー通知 通知時間 2時間前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_TWO_HOUR,
        msgKey = "schedule.reminder.timesel.7")
    REMINDER_TIME_TWO_HOUR,
    /** リマインダー通知 通知時間 3時間前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_THREE_HOUR,
        msgKey = "schedule.reminder.timesel.8")
    REMINDER_TIME_THREE_HOUR,
    /** リマインダー通知 通知時間 6時間前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_SIX_HOUR,
        msgKey = "schedule.reminder.timesel.9")
    REMINDER_TIME_SIX_HOUR,
    /** リマインダー通知 通知時間 12時間前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_TWELVE_HOUR,
        msgKey = "schedule.reminder.timesel.10")
    REMINDER_TIME_TWELVE_HOUR,
    /** リマインダー通知 通知時間 1日前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_ONE_DAY,
        msgKey = "schedule.reminder.timesel.11")
    REMINDER_TIME_ONE_DAY,
    /** リマインダー通知 通知時間 2日前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_TWO_DAY,
        msgKey = "schedule.reminder.timesel.12")
    REMINDER_TIME_TWO_DAY,
    /** リマインダー通知 通知時間 1週間前 */
    @Constructor(value = GSConstSchedule.REMINDER_TIME_ONE_WEEK,
        msgKey = "schedule.reminder.timesel.13")
    REMINDER_TIME_ONE_WEEK;

    /** バリュー検索用 MAP*/
    private static final HashMap<Integer, SchEnumReminderTime > MAP__ = new HashMap<>();
    /** 数キー*/
    private final int value__;
    /** メッセージキー*/
    private final String msgKey__;


    static {
        for (SchEnumReminderTime val :  SchEnumReminderTime.values()) {
            MAP__.put(val.getValue(), val);
        }
    }

    SchEnumReminderTime() {
        Constructor bindAnt = null;
        try {
            bindAnt = this.getClass().getDeclaredField(this.toString())
                    .getAnnotation(Constructor.class);
        } catch (NoSuchFieldException | SecurityException e) {
            value__ = -1;
            msgKey__ = "";
            return;
        }

        value__ = bindAnt.value();
        msgKey__ = bindAnt.msgKey();

    }

    /**
     * <p>msgKey を取得します。
     * @return msgKey
     * @see jp.groupsession.v2.cmn.model.SchEnumReminderTime#msgKey__
     */
    public String getMsgKey() {
        return msgKey__;
    }

    /**
     * <p>value を取得します。
     * @return value
     * @see jp.groupsession.v2.cmn.model.SchEnumReminderTime#value__
     */
    public int getValue() {
        return value__;
    }

    /**
     *
     * <br>[機  能] バリューから列挙子を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param value
     * @return 列挙子
     */
    public static SchEnumReminderTime valueOf(int value) {
        return MAP__.get(value);
    }

    /**
     *
     * <br>[機  能] ラベルリストを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg メッセージ管理
     * @return ラベルリスト
     */
    public static List<LabelValueBean> labelList(GsMessage gsMsg) {
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        for (SchEnumReminderTime val :  SchEnumReminderTime.values()) {
            ret.add(val.labelBean(gsMsg));
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] ラベルビーンを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg メッセージ管理
     * @return ラベルビーン
     */
    public LabelValueBean labelBean(GsMessage gsMsg) {
        return new LabelValueBean(
                gsMsg.getMessage(this.getMsgKey()),
                String.valueOf(this.getValue()));
    }
}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Constructor {
    int value();
    String msgKey();
}
