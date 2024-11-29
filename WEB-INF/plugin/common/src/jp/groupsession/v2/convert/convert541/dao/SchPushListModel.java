package jp.groupsession.v2.convert.convert541.dao;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>バージョンアップコンバート時のスケジュール_通知予定リストを保持するModelクラス
 */
public class SchPushListModel implements Serializable {

    /** SCD_SID mapping */
    private int scdSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SPL_REMINDER mapping */
    private UDate splReminder__;
    /** SPL_REMINDER_KBN mapping */
    private int splReminderKbn__;

    /** SCH_DATA.SCD_FR_DATE mapping */
    private UDate scdFrDate__;

    /**
     * <p>Default Constructor
     */
    public SchPushListModel() {
    }

    /**
     * <p>get SCD_SID value
     * @return SCD_SID value
     */
    public int getScdSid() {
        return scdSid__;
    }

    /**
     * <p>set SCD_SID value
     * @param scdSid SCD_SID value
     */
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get SPL_REMINDER value
     * @return SPL_REMINDER value
     */
    public UDate getSplReminder() {
        return splReminder__;
    }

    /**
     * <p>set SPL_REMINDER value
     * @param splReminder SPL_REMINDER value
     */
    public void setSplReminder(UDate splReminder) {
        splReminder__ = splReminder;
    }

    /**
     * <p>get SPL_REMINDER_KBN value
     * @return SPL_REMINDER_KBN value
     */
    public int getSplReminderKbn() {
        return splReminderKbn__;
    }

    /**
     * <p>set SPL_REMINDER_KBN value
     * @param splReminder SPL_REMINDER_KBN value
     */
    public void setSplReminderKbn(int splReminderKbn) {
        splReminderKbn__ = splReminderKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(scdSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(splReminder__, ""));
        buf.append(",");
        buf.append(splReminderKbn__);
        return buf.toString();
    }

    /**
     * <p>get SCH_DATA.SCD_FR_DATE value
     * @return SCH_DATA.SCD_FR_DATE value
     */
    public UDate getScdFrDate() {
        return scdFrDate__;
    }

    /**
     * <p>set SCH_DATA.SCD_FR_DATE value
     * @param scdFrDate SCH_DATA.SCD_FR_DATE value
     */
    public void setScdFrDate(UDate scdFrDate) {
        scdFrDate__ = scdFrDate;
    }
}
