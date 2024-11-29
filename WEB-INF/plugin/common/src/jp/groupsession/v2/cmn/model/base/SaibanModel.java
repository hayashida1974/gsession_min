package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>SAIBAN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SaibanModel implements Serializable {

    /** 採番区分 SID ユーザ */
    public static final String SBNSID_USER = "user";
    /** 採番区分 SID SUB ユーザ */
    public static final String SBNSID_SUB_USER = "user";
    /** 採番区分 SID SUB グループ */
    public static final String SBNSID_SUB_GROUP = "group";
    /** 採番区分 SID SUB 役職 */
    public static final String SBNSID_SUB_POS = "position";

    /** 採番区分 SID スケジュール */
    public static final String SBNSID_SCHEDULE = "schedule";
    /** 採番区分 SID SUB スケジュール */
    public static final String SBNSID_SUB_SCH = "sch";
    /** 採番区分 SID SUB スケジュールグループ */
    public static final String SBNSID_SUB_SCH_GP = "sch_gp";
    /** 採番区分 SID SUB スケジュール拡張 */
    public static final String SBNSID_SUB_SCH_EX = "sch_ex";
    /** 採番区分 SID SUB スケジュール施設予約 */
    public static final String SBNSID_SUB_SCH_RES = "sch_res";
    /** 採番区分 SID SUB スケジュール特例アクセス */
    public static final String SBNSID_SUB_SCH_SPACCESS = "spaccess";
    /** 採番区分 SID SUB スケジュール表示リスト */
    public static final String SBNSID_SUB_SCH_DISPLIST = "displsit";
    /** 採番区分 SID 施設予約 */
    public static final String SBNSID_RESERVE = "reserve";
    /** 採番区分 SID SUB 予約 */
    public static final String SBNSID_SUB_YOYAKU = "yoyaku";
    /** 採番SID(メール・ひな形SID) */
    public static final String SAIBAN_SML_SID = "smail";
    /** 採番SIDサブ(メールSID) */
    public static final String SAIBAN_SUB_MAIL_SID = "mail";
    /** 採番SIDサブ(雛形SID) */
    public static final String SAIBAN_SUB_HINA_SID = "hina";

    /** 採番区分 SID メイン */
    public static final String SBNSID_MAIN = "main";
    /** 採番区分 SID プラグイン */
    public static final String SBNSID_PLUGIN = "plugin";
    /** 採番区分 SID SUB 休日テンプレート */
    public static final String SBNSID_SUB_HLT = "hlt";
    /** 採番区分 SID SUB インフォメーション */
    public static final String SBNSID_SUB_INFO = "info";

    /** 採番区分 SID タイムカード */
    public static final String SBNSID_TIMECARD = "timecard";
    /** 採番区分 SID SUB 時間帯区分 */
    public static final String SBNSID_SUB_TCD = "tcdzone";

    /** 採番区分 SID WEBメール */
    public static final String SBNSID_WEBMAIL = "webmail";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";

    /** 採番区分 SID バイナリーSID */
    public static final String SBNSID_BIN = "binary";
    /** 採番区分 SID SUB バイナリーSID */
    public static final String SBNSID_SUB_BIN = "bin";

    /** 採番区分 SID ユーザ追加プラグイン */
    public static final String SBNSID_USER_PLUGIN = "userplugin";
    /** 採番区分 SID SUB ユーザ追加プラグイン*/
    public static final String SBNSID_SUB_USER_PLUGIN = "addplugin";

    /** 採番区分 SID WEBメール */
    public static final String SBNSID_CHAT = "chat";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_PAIR = "pairSid";

    /** sbn_sid mapping */
    private String sbnSid__;
    /** sbn_sid_sub mapping */
    private String sbnSidSub__;
    /** sbn_number mapping */
    private long sbnNumber__;
    /** sbn_string mapping */
    private String sbnString__;
    /** sbn_aid mapping */
    private int sbnAid__;
    /** sbn_adate mapping */
    private UDate sbnAdate__;
    /** sbn_eid mapping */
    private int sbnEid__;
    /** sbn_edate mapping */
    private UDate sbnEdate__;

    /**
     * <p>Default Constructor
     */
    public SaibanModel() {
    }

    /**
     * <p>get sbn_sid value
     * @return sbn_sid value
     */
    public String getSbnSid() {
        return sbnSid__;
    }

    /**
     * <p>set sbn_sid value
     * @param sbnSid sbn_sid value
     */
    public void setSbnSid(String sbnSid) {
        sbnSid__ = sbnSid;
    }

    /**
     * <p>get sbn_sid_sub value
     * @return sbn_sid_sub value
     */
    public String getSbnSidSub() {
        return sbnSidSub__;
    }

    /**
     * <p>set sbn_sid_sub value
     * @param sbnSidSub sbn_sid_sub value
     */
    public void setSbnSidSub(String sbnSidSub) {
        sbnSidSub__ = sbnSidSub;
    }

    /**
     * <p>get sbn_number value
     * @return sbn_number value
     */
    public long getSbnNumber() {
        return sbnNumber__;
    }

    /**
     * <p>set sbn_number value
     * @param sbnNumber sbn_number value
     */
    public void setSbnNumber(long sbnNumber) {
        sbnNumber__ = sbnNumber;
    }

    /**
     * <p>get sbn_string value
     * @return sbn_string value
     */
    public String getSbnString() {
        return sbnString__;
    }

    /**
     * <p>set sbn_string value
     * @param sbnString sbn_string value
     */
    public void setSbnString(String sbnString) {
        sbnString__ = sbnString;
    }

    /**
     * <p>get sbn_aid value
     * @return sbn_aid value
     */
    public int getSbnAid() {
        return sbnAid__;
    }

    /**
     * <p>set sbn_aid value
     * @param sbnAid sbn_aid value
     */
    public void setSbnAid(int sbnAid) {
        sbnAid__ = sbnAid;
    }

    /**
     * <p>get sbn_adate value
     * @return sbn_adate value
     */
    public UDate getSbnAdate() {
        return sbnAdate__;
    }

    /**
     * <p>set sbn_adate value
     * @param sbnAdate sbn_adate value
     */
    public void setSbnAdate(UDate sbnAdate) {
        sbnAdate__ = sbnAdate;
    }

    /**
     * <p>get sbn_eid value
     * @return sbn_eid value
     */
    public int getSbnEid() {
        return sbnEid__;
    }

    /**
     * <p>set sbn_eid value
     * @param sbnEid sbn_eid value
     */
    public void setSbnEid(int sbnEid) {
        sbnEid__ = sbnEid;
    }

    /**
     * <p>get sbn_edate value
     * @return sbn_edate value
     */
    public UDate getSbnEdate() {
        return sbnEdate__;
    }

    /**
     * <p>set sbn_edate value
     * @param sbnEdate sbn_edate value
     */
    public void setSbnEdate(UDate sbnEdate) {
        sbnEdate__ = sbnEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sbnSid__);
        buf.append(",");
        buf.append(NullDefault.getString(sbnSidSub__, ""));
        buf.append(",");
        buf.append(sbnNumber__);
        buf.append(",");
        buf.append(NullDefault.getString(sbnString__, ""));
        buf.append(",");
        buf.append(sbnAid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sbnAdate__, ""));
        buf.append(",");
        buf.append(sbnEid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sbnEdate__, ""));
        return buf.toString();
    }

}
