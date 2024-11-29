package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;

/**
 * <p>TCD_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdAdmConfModel implements Serializable {

    /** TAC_INTERVAL mapping */
    private int tacInterval__;
    /** TAC_KANSAN mapping */
    private int tacKansan__;
    /** TAC_SIMEBI mapping */
    private int tacSimebi__;
    /** TAC_HOL_SUN mapping */
    private int tacHolSun__;
    /** TAC_HOL_MON mapping */
    private int tacHolMon__;
    /** TAC_HOL_TUE mapping */
    private int tacHolTue__;
    /** TAC_HOL_WED mapping */
    private int tacHolWed__;
    /** TAC_HOL_THU mapping */
    private int tacHolThu__;
    /** TAC_HOL_FRI mapping */
    private int tacHolFri__;
    /** TAC_HOL_SAT mapping */
    private int tacHolSat__;
    /** TAC_LOCK_FLG mapping */
    private int tacLockFlg__;
    /** TAC_LOCK_STRIKE mapping */
    private int tacLockStrike__;
    /** TAC_LOCK_BIKO mapping */
    private int tacLockBiko__;
    /** TAC_LOCK_LATE mapping */
    private int tacLockLate__;
    /** TAC_LOCK_FLG mapping */
    private int tacLockHoliday__;
    /** TAC_LOCK_FLG mapping */
    private int tacLockTimezone__;
    /** TAC_DEF_TIMEZONE mapping */
    private int tacDefTimezone__;
    /** TAC_WORKREPORT_KBN mapping */
    private int tacWorkreportKbn__;
    /** TAC_WORKREPORT_SID mapping */
    private long tacWorkreportSid__;
    /** TAC_AUID mapping */
    private int tacAuid__;
    /** TAC_ADATE mapping */
    private UDate tacAdate__;
    /** TAC_EUID mapping */
    private int tacEuid__;
    /** TAC_EDATE mapping */
    private UDate tacEdate__;

    /**
     * <p>Default Constructor
     */
    public TcdAdmConfModel() {
    }
    /**
     * <p>定休日をClear
     */
    public void clearWeeks() {
        tacHolSun__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolMon__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolTue__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolWed__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolThu__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolFri__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolSat__ = GSConstTimecard.NOT_HOLIDAY_FLG;
    }

    /**
     * <p>初期値設定 Constructor
     * @param usrSid ユーザSID
     */
    public TcdAdmConfModel(int usrSid) {
        UDate sysDate = new UDate();
        tacInterval__ = GSConstTimecard.DF_INTERVAL;
        tacKansan__ = GSConstTimecard.DF_KANSAN;
        tacSimebi__ = GSConstTimecard.DF_SIMEBI;
        tacHolSun__ = GSConstTimecard.HOLIDAY_FLG;
        tacHolMon__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolTue__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolWed__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolThu__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolFri__ = GSConstTimecard.NOT_HOLIDAY_FLG;
        tacHolSat__ = GSConstTimecard.HOLIDAY_FLG;
        tacLockFlg__ =  GSConstTimecard.UNLOCK_FLG;
        tacLockStrike__ =  GSConstTimecard.UNLOCK_FLG;
        tacLockBiko__ =  GSConstTimecard.BIKO_UNNECESSARY_FLG;
        tacLockLate__ =  GSConstTimecard.UNLOCK_FLG;
        tacLockHoliday__ =  GSConstTimecard.UNLOCK_FLG;
        tacLockTimezone__ =  GSConstTimecard.UNLOCK_FLG;
        tacDefTimezone__ = GSConstTimecard.DEFAULT_TIMEZONE_SID;
        tacWorkreportKbn__ = 0;
        tacWorkreportSid__ = 0;
        tacAuid__ = usrSid;
        tacAdate__ = sysDate;
        tacEuid__ = usrSid;
        tacEdate__ = sysDate;
    }
    /**
     * <p>get TAC_INTERVAL value
     * @return TAC_INTERVAL value
     */
    public int getTacInterval() {
        return tacInterval__;
    }

    /**
     * <p>set TAC_INTERVAL value
     * @param tacInterval TAC_INTERVAL value
     */
    public void setTacInterval(int tacInterval) {
        tacInterval__ = tacInterval;
    }

    /**
     * <p>get TAC_KANSAN value
     * @return TAC_KANSAN value
     */
    public int getTacKansan() {
        return tacKansan__;
    }

    /**
     * <p>set TAC_KANSAN value
     * @param tacKansan TAC_KANSAN value
     */
    public void setTacKansan(int tacKansan) {
        tacKansan__ = tacKansan;
    }

    /**
     * <p>get TAC_SIMEBI value
     * @return TAC_SIMEBI value
     */
    public int getTacSimebi() {
        return tacSimebi__;
    }

    /**
     * <p>set TAC_SIMEBI value
     * @param tacSimebi TAC_SIMEBI value
     */
    public void setTacSimebi(int tacSimebi) {
        tacSimebi__ = tacSimebi;
    }

    /**
     * <p>get TAC_HOL_SUN value
     * @return TAC_HOL_SUN value
     */
    public int getTacHolSun() {
        return tacHolSun__;
    }

    /**
     * <p>set TAC_HOL_SUN value
     * @param tacHolSun TAC_HOL_SUN value
     */
    public void setTacHolSun(int tacHolSun) {
        tacHolSun__ = tacHolSun;
    }

    /**
     * <p>get TAC_HOL_MON value
     * @return TAC_HOL_MON value
     */
    public int getTacHolMon() {
        return tacHolMon__;
    }

    /**
     * <p>set TAC_HOL_MON value
     * @param tacHolMon TAC_HOL_MON value
     */
    public void setTacHolMon(int tacHolMon) {
        tacHolMon__ = tacHolMon;
    }

    /**
     * <p>tacLockBiko を取得します。
     * @return tacLockBiko
     */
    public int getTacLockBiko() {
        return tacLockBiko__;
    }
    /**
     * <p>tacLockBiko をセットします。
     * @param tacLockBiko tacLockBiko
     */
    public void setTacLockBiko(int tacLockBiko) {
        tacLockBiko__ = tacLockBiko;
    }
    /**
     * <p>tacLockHoliday を取得します。
     * @return tacLockHoliday
     */
    public int getTacLockHoliday() {
        return tacLockHoliday__;
    }
    /**
     * <p>tacLockHoliday をセットします。
     * @param tacLockHoliday tacLockHoliday
     */
    public void setTacLockHoliday(int tacLockHoliday) {
        tacLockHoliday__ = tacLockHoliday;
    }
    /**
     * <p>tacLockLate を取得します。
     * @return tacLockLate
     */
    public int getTacLockLate() {
        return tacLockLate__;
    }
    /**
     * <p>tacLockLate をセットします。
     * @param tacLockLate tacLockLate
     */
    public void setTacLockLate(int tacLockLate) {
        tacLockLate__ = tacLockLate;
    }
    /**
     * <p>tacLockStrike を取得します。
     * @return tacLockStrike
     */
    public int getTacLockStrike() {
        return tacLockStrike__;
    }
    /**
     * <p>tacLockStrike をセットします。
     * @param tacLockStrike tacLockStrike
     */
    public void setTacLockStrike(int tacLockStrike) {
        tacLockStrike__ = tacLockStrike;
    }
    /**
     * <p>get TAC_HOL_TUE value
     * @return TAC_HOL_TUE value
     */
    public int getTacHolTue() {
        return tacHolTue__;
    }

    /**
     * <p>set TAC_HOL_TUE value
     * @param tacHolTue TAC_HOL_TUE value
     */
    public void setTacHolTue(int tacHolTue) {
        tacHolTue__ = tacHolTue;
    }

    /**
     * <p>get TAC_HOL_WED value
     * @return TAC_HOL_WED value
     */
    public int getTacHolWed() {
        return tacHolWed__;
    }

    /**
     * <p>set TAC_HOL_WED value
     * @param tacHolWed TAC_HOL_WED value
     */
    public void setTacHolWed(int tacHolWed) {
        tacHolWed__ = tacHolWed;
    }

    /**
     * <p>get TAC_HOL_THU value
     * @return TAC_HOL_THU value
     */
    public int getTacHolThu() {
        return tacHolThu__;
    }

    /**
     * <p>set TAC_HOL_THU value
     * @param tacHolThu TAC_HOL_THU value
     */
    public void setTacHolThu(int tacHolThu) {
        tacHolThu__ = tacHolThu;
    }

    /**
     * <p>get TAC_HOL_FRI value
     * @return TAC_HOL_FRI value
     */
    public int getTacHolFri() {
        return tacHolFri__;
    }

    /**
     * <p>set TAC_HOL_FRI value
     * @param tacHolFri TAC_HOL_FRI value
     */
    public void setTacHolFri(int tacHolFri) {
        tacHolFri__ = tacHolFri;
    }

    /**
     * <p>get TAC_HOL_SAT value
     * @return TAC_HOL_SAT value
     */
    public int getTacHolSat() {
        return tacHolSat__;
    }

    /**
     * <p>set TAC_HOL_SAT value
     * @param tacHolSat TAC_HOL_SAT value
     */
    public void setTacHolSat(int tacHolSat) {
        tacHolSat__ = tacHolSat;
    }

    /**
     * <p>get TAC_LOCK_FLG value
     * @return TAC_LOCK_FLG value
     */
    public int getTacLockFlg() {
        return tacLockFlg__;
    }

    /**
     * <p>set TAC_LOCK_FLG value
     * @param tacLockFlg TAC_LOCK_FLG value
     */
    public void setTacLockFlg(int tacLockFlg) {
        tacLockFlg__ = tacLockFlg;
    }

    /**
     * <p>tacLockTimezone を取得します。
     * @return tacLockTimezone
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacLockTimezone__
     */
    public int getTacLockTimezone() {
        return tacLockTimezone__;
    }
    /**
     * <p>tacLockTimezone をセットします。
     * @param tacLockTimezone tacLockTimezone
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacLockTimezone__
     */
    public void setTacLockTimezone(int tacLockTimezone) {
        tacLockTimezone__ = tacLockTimezone;
    }
    /**
     * <p>tacDefTimezone を取得します。
     * @return tacDefTimezone
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacDefTimezone__
     */
    public int getTacDefTimezone() {
        return tacDefTimezone__;
    }
    /**
     * <p>tacDefTimezone をセットします。
     * @param tacDefTimezone tacDefTimezone
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacDefTimezone__
     */
    public void setTacDefTimezone(int tacDefTimezone) {
        tacDefTimezone__ = tacDefTimezone;
    }
    /**
     * <p>get TAC_AUID value
     * @return TAC_AUID value
     */
    public int getTacAuid() {
        return tacAuid__;
    }

    /**
     * <p>set TAC_AUID value
     * @param tacAuid TAC_AUID value
     */
    public void setTacAuid(int tacAuid) {
        tacAuid__ = tacAuid;
    }

    /**
     * <p>get TAC_ADATE value
     * @return TAC_ADATE value
     */
    public UDate getTacAdate() {
        return tacAdate__;
    }

    /**
     * <p>set TAC_ADATE value
     * @param tacAdate TAC_ADATE value
     */
    public void setTacAdate(UDate tacAdate) {
        tacAdate__ = tacAdate;
    }

    /**
     * <p>get TAC_EUID value
     * @return TAC_EUID value
     */
    public int getTacEuid() {
        return tacEuid__;
    }

    /**
     * <p>set TAC_EUID value
     * @param tacEuid TAC_EUID value
     */
    public void setTacEuid(int tacEuid) {
        tacEuid__ = tacEuid;
    }

    /**
     * <p>get TAC_EDATE value
     * @return TAC_EDATE value
     */
    public UDate getTacEdate() {
        return tacEdate__;
    }

    /**
     * <p>set TAC_EDATE value
     * @param tacEdate TAC_EDATE value
     */
    public void setTacEdate(UDate tacEdate) {
        tacEdate__ = tacEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(tacInterval__);
        buf.append(",");
        buf.append(tacKansan__);
        buf.append(",");
        buf.append(tacSimebi__);
        buf.append(",");
        buf.append(tacHolSun__);
        buf.append(",");
        buf.append(tacHolMon__);
        buf.append(",");
        buf.append(tacHolTue__);
        buf.append(",");
        buf.append(tacHolWed__);
        buf.append(",");
        buf.append(tacHolThu__);
        buf.append(",");
        buf.append(tacHolFri__);
        buf.append(",");
        buf.append(tacHolSat__);
        buf.append(",");
        buf.append(tacLockFlg__);
        buf.append(",");
        buf.append(tacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tacAdate__, ""));
        buf.append(",");
        buf.append(tacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tacEdate__, ""));
        return buf.toString();
    }
    /**
     * <p>tacWorkreportKbn を取得します。
     * @return tacWorkreportKbn
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacWorkreportKbn__
     */
    public int getTacWorkreportKbn() {
        return tacWorkreportKbn__;
    }
    /**
     * <p>tacWorkreportKbn をセットします。
     * @param tacWorkreportKbn tacWorkreportKbn
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacWorkreportKbn__
     */
    public void setTacWorkreportKbn(int tacWorkreportKbn) {
        tacWorkreportKbn__ = tacWorkreportKbn;
    }
    /**
     * <p>tacWorkreportSid を取得します。
     * @return tacWorkreportSid
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacWorkreportSid__
     */
    public long getTacWorkreportSid() {
        return tacWorkreportSid__;
    }
    /**
     * <p>tacWorkreportSid をセットします。
     * @param tacWorkreportSid tacWorkreportSid
     * @see jp.groupsession.v2.cmn.model.base.TcdAdmConfModel#tacWorkreportSid__
     */
    public void setTacWorkreportSid(long tacWorkreportSid) {
        tacWorkreportSid__ = tacWorkreportSid;
    }

}
