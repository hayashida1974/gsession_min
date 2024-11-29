package jp.groupsession.v2.cmn.model;

import java.util.HashMap;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 施設予約プラグインからスケジュールクラスを操作する際必要となる情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class RsvScdOperationModel extends AbstractModel {

    /** スケジュールSID */
    private int scdSid__;
    /** ユーザ・グループSID */
    private int scdUsrSid__;
    /** スケジュールグループSID */
    private int scdGrpSid__;
    /** ユーザ区分 */
    private int scdUsrKbn__;
    /** スケジュール開始 */
    private UDate scdFrDate__;
    /** スケジュール終了 */
    private UDate scdToDate__;
    /** 時間指定区分 */
    private int scdDaily__;
    /** 背景色設定 */
    private int scdBgcolor__;
    /** タイトル */
    private String scdTitle__;
    /** 内容 */
    private String scdValue__;
    /** 備考 */
    private String scdBiko__;
    /** 公開区分 */
    private int scdPublic__;
    /** 登録者SID */
    private int scdAuid__;
    /** 登録日時 */
    private UDate scdAdate__;
    /** 更新者SID */
    private int scdEuid__;
    /** 更新日時 */
    private UDate scdEdate__;
    /** スケジュール拡張SID */
    private int sceSid__;
    /** 編集権限設定 */
    private int scdEdit__;
    /** スケジュール予約SID */
    private int scdRsSid__;

    /** 施設予約 施設グループSID */
    private int rsgSid__;
    /** 施設予約 登録者 */
    private int rsyAuid__;
    /** 施設予約 編集権限 */
    private int rsyEdit__;

    /** スケジュール リレーションSIDマッピング */
    private HashMap<Integer, Integer> rssidMap__;
    /** スケジュール 使用者SIDマッピング */
    private HashMap<String, String> usrMap__;
    /** RSY_SID mapping */
    private int rsySid__;
    /** RSR_RSID mapping */
    private int rsrRsid__;

    /** SCE_KBN mapping */
    private int sceKbn__;
    /** SCE_DWEEK1 mapping */
    private int sceDweek1__;
    /** SCE_DWEEK2 mapping */
    private int sceDweek2__;
    /** SCE_DWEEK3 mapping */
    private int sceDweek3__;
    /** SCE_DWEEK4 mapping */
    private int sceDweek4__;
    /** SCE_DWEEK5 mapping */
    private int sceDweek5__;
    /** SCE_DWEEK6 mapping */
    private int sceDweek6__;
    /** SCE_DWEEK7 mapping */
    private int sceDweek7__;
    /** SCE_DAY mapping */
    private int sceDay__;
    /** SCE_WEEK mapping */
    private int sceWeek__;
    /** SCE_MONTH_YEARLY mapping */
    private int sceMonthOfYearly__;
    /** SCE_DAY_YEARLY mapping */
    private int sceDayOfYearly__;

    /** SCE_TIME_FR mapping */
    private UDate sceTimeFr__;
    /** SCE_TIME_TO mapping */
    private UDate sceTimeTo__;
    /** SCE_TRAN_KBN mapping */
    private int sceTranKbn__;
    /** SCE_DATE_FR mapping */
    private UDate sceDateFr__;
    /** SCE_DATE_TO mapping */
    private UDate sceDateTo__;
    /** SCE_BGCOLOR mapping */
    private int sceBgcolor__;
    /** SCE_TITLE mapping */
    private String sceTitle__;
    /** SCE_VALUE mapping */
    private String sceValue__;
    /** SCE_BIKO mapping */
    private String sceBiko__;
    /** SCE_PUBLIC mapping */
    private int scePublic__;
    /** SCE_EDIT mapping */
    private int sceEdit__;
    /** SCE_AUID mapping */
    private int sceAuid__;
    /** SCE_ADATE mapping */
    private UDate sceAdate__;
    /** SCE_EUID mapping */
    private int sceEuid__;
    /** SCE_EDATE mapping */
    private UDate sceEdate__;

    /**
     * <p>rsySid を取得します。
     * @return rsySid
     */
    public int getRsySid() {
        return rsySid__;
    }

    /**
     * <p>rsySid をセットします。
     * @param rsySid rsySid
     */
    public void setRsySid(int rsySid) {
        rsySid__ = rsySid;
    }

    /**
     * <p>rsrRsid を取得します。
     * @return rsrRsid
     */
    public int getRsrRsid() {
        return rsrRsid__;
    }

    /**
     * <p>rsrRsid をセットします。
     * @param rsrRsid rsrRsid
     */
    public void setRsrRsid(int rsrRsid) {
        rsrRsid__ = rsrRsid;
    }

    /**
     * <p>get SCE_KBN value
     * @return SCE_KBN value
     */
    public int getSceKbn() {
        return sceKbn__;
    }

    /**
     * <p>set SCE_KBN value
     * @param sceKbn SCE_KBN value
     */
    public void setSceKbn(int sceKbn) {
        sceKbn__ = sceKbn;
    }

    /**
     * <p>get SCE_DWEEK1 value
     * @return SCE_DWEEK1 value
     */
    public int getSceDweek1() {
        return sceDweek1__;
    }

    /**
     * <p>set SCE_DWEEK1 value
     * @param sceDweek1 SCE_DWEEK1 value
     */
    public void setSceDweek1(int sceDweek1) {
        sceDweek1__ = sceDweek1;
    }

    /**
     * <p>get SCE_DWEEK2 value
     * @return SCE_DWEEK2 value
     */
    public int getSceDweek2() {
        return sceDweek2__;
    }

    /**
     * <p>set SCE_DWEEK2 value
     * @param sceDweek2 SCE_DWEEK2 value
     */
    public void setSceDweek2(int sceDweek2) {
        sceDweek2__ = sceDweek2;
    }

    /**
     * <p>get SCE_DWEEK3 value
     * @return SCE_DWEEK3 value
     */
    public int getSceDweek3() {
        return sceDweek3__;
    }

    /**
     * <p>set SCE_DWEEK3 value
     * @param sceDweek3 SCE_DWEEK3 value
     */
    public void setSceDweek3(int sceDweek3) {
        sceDweek3__ = sceDweek3;
    }

    /**
     * <p>get SCE_DWEEK4 value
     * @return SCE_DWEEK4 value
     */
    public int getSceDweek4() {
        return sceDweek4__;
    }

    /**
     * <p>set SCE_DWEEK4 value
     * @param sceDweek4 SCE_DWEEK4 value
     */
    public void setSceDweek4(int sceDweek4) {
        sceDweek4__ = sceDweek4;
    }

    /**
     * <p>get SCE_DWEEK5 value
     * @return SCE_DWEEK5 value
     */
    public int getSceDweek5() {
        return sceDweek5__;
    }

    /**
     * <p>set SCE_DWEEK5 value
     * @param sceDweek5 SCE_DWEEK5 value
     */
    public void setSceDweek5(int sceDweek5) {
        sceDweek5__ = sceDweek5;
    }

    /**
     * <p>get SCE_DWEEK6 value
     * @return SCE_DWEEK6 value
     */
    public int getSceDweek6() {
        return sceDweek6__;
    }

    /**
     * <p>set SCE_DWEEK6 value
     * @param sceDweek6 SCE_DWEEK6 value
     */
    public void setSceDweek6(int sceDweek6) {
        sceDweek6__ = sceDweek6;
    }

    /**
     * <p>get SCE_DWEEK7 value
     * @return SCE_DWEEK7 value
     */
    public int getSceDweek7() {
        return sceDweek7__;
    }

    /**
     * <p>set SCE_DWEEK7 value
     * @param sceDweek7 SCE_DWEEK7 value
     */
    public void setSceDweek7(int sceDweek7) {
        sceDweek7__ = sceDweek7;
    }

    /**
     * <p>get SCE_DAY value
     * @return SCE_DAY value
     */
    public int getSceDay() {
        return sceDay__;
    }

    /**
     * <p>set SCE_DAY value
     * @param sceDay SCE_DAY value
     */
    public void setSceDay(int sceDay) {
        sceDay__ = sceDay;
    }

    /**
     * <p>get SCE_WEEK value
     * @return SCE_WEEK value
     */
    public int getSceWeek() {
        return sceWeek__;
    }

    /**
     * <p>set SCE_WEEK value
     * @param sceWeek SCE_WEEK value
     */
    public void setSceWeek(int sceWeek) {
        sceWeek__ = sceWeek;
    }

    /**
     * <p>get SCE_TIME_FR value
     * @return SCE_TIME_FR value
     */
    public UDate getSceTimeFr() {
        return sceTimeFr__;
    }

    /**
     * <p>set SCE_TIME_FR value
     * @param sceTimeFr SCE_TIME_FR value
     */
    public void setSceTimeFr(UDate sceTimeFr) {
        sceTimeFr__ = sceTimeFr;
    }

    /**
     * <p>get SCE_TIME_TO value
     * @return SCE_TIME_TO value
     */
    public UDate getSceTimeTo() {
        return sceTimeTo__;
    }

    /**
     * <p>set SCE_TIME_TO value
     * @param sceTimeTo SCE_TIME_TO value
     */
    public void setSceTimeTo(UDate sceTimeTo) {
        sceTimeTo__ = sceTimeTo;
    }

    /**
     * <p>get SCE_TRAN_KBN value
     * @return SCE_TRAN_KBN value
     */
    public int getSceTranKbn() {
        return sceTranKbn__;
    }

    /**
     * <p>set SCE_TRAN_KBN value
     * @param sceTranKbn SCE_TRAN_KBN value
     */
    public void setSceTranKbn(int sceTranKbn) {
        sceTranKbn__ = sceTranKbn;
    }

    /**
     * <p>get SCE_DATE_FR value
     * @return SCE_DATE_FR value
     */
    public UDate getSceDateFr() {
        return sceDateFr__;
    }

    /**
     * <p>set SCE_DATE_FR value
     * @param sceDateFr SCE_DATE_FR value
     */
    public void setSceDateFr(UDate sceDateFr) {
        sceDateFr__ = sceDateFr;
    }

    /**
     * <p>get SCE_DATE_TO value
     * @return SCE_DATE_TO value
     */
    public UDate getSceDateTo() {
        return sceDateTo__;
    }

    /**
     * <p>set SCE_DATE_TO value
     * @param sceDateTo SCE_DATE_TO value
     */
    public void setSceDateTo(UDate sceDateTo) {
        sceDateTo__ = sceDateTo;
    }

    /**
     * <p>get SCE_BGCOLOR value
     * @return SCE_BGCOLOR value
     */
    public int getSceBgcolor() {
        return sceBgcolor__;
    }

    /**
     * <p>set SCE_BGCOLOR value
     * @param sceBgcolor SCE_BGCOLOR value
     */
    public void setSceBgcolor(int sceBgcolor) {
        sceBgcolor__ = sceBgcolor;
    }

    /**
     * <p>get SCE_TITLE value
     * @return SCE_TITLE value
     */
    public String getSceTitle() {
        return sceTitle__;
    }

    /**
     * <p>set SCE_TITLE value
     * @param sceTitle SCE_TITLE value
     */
    public void setSceTitle(String sceTitle) {
        sceTitle__ = sceTitle;
    }

    /**
     * <p>get SCE_VALUE value
     * @return SCE_VALUE value
     */
    public String getSceValue() {
        return sceValue__;
    }

    /**
     * <p>set SCE_VALUE value
     * @param sceValue SCE_VALUE value
     */
    public void setSceValue(String sceValue) {
        sceValue__ = sceValue;
    }

    /**
     * <p>get SCE_BIKO value
     * @return SCE_BIKO value
     */
    public String getSceBiko() {
        return sceBiko__;
    }

    /**
     * <p>set SCE_BIKO value
     * @param sceBiko SCE_BIKO value
     */
    public void setSceBiko(String sceBiko) {
        sceBiko__ = sceBiko;
    }

    /**
     * <p>get SCE_PUBLIC value
     * @return SCE_PUBLIC value
     */
    public int getScePublic() {
        return scePublic__;
    }

    /**
     * <p>set SCE_PUBLIC value
     * @param scePublic SCE_PUBLIC value
     */
    public void setScePublic(int scePublic) {
        scePublic__ = scePublic;
    }

    /**
     * <p>get SCE_EDIT value
     * @return SCE_EDIT value
     */
    public int getSceEdit() {
        return sceEdit__;
    }

    /**
     * <p>set SCE_EDIT value
     * @param sceEdit SCE_EDIT value
     */
    public void setSceEdit(int sceEdit) {
        sceEdit__ = sceEdit;
    }

    /**
     * <p>get SCE_AUID value
     * @return SCE_AUID value
     */
    public int getSceAuid() {
        return sceAuid__;
    }

    /**
     * <p>set SCE_AUID value
     * @param sceAuid SCE_AUID value
     */
    public void setSceAuid(int sceAuid) {
        sceAuid__ = sceAuid;
    }

    /**
     * <p>get SCE_ADATE value
     * @return SCE_ADATE value
     */
    public UDate getSceAdate() {
        return sceAdate__;
    }

    /**
     * <p>set SCE_ADATE value
     * @param sceAdate SCE_ADATE value
     */
    public void setSceAdate(UDate sceAdate) {
        sceAdate__ = sceAdate;
    }

    /**
     * <p>get SCE_EUID value
     * @return SCE_EUID value
     */
    public int getSceEuid() {
        return sceEuid__;
    }

    /**
     * <p>set SCE_EUID value
     * @param sceEuid SCE_EUID value
     */
    public void setSceEuid(int sceEuid) {
        sceEuid__ = sceEuid;
    }

    /**
     * <p>get SCE_EDATE value
     * @return SCE_EDATE value
     */
    public UDate getSceEdate() {
        return sceEdate__;
    }

    /**
     * <p>set SCE_EDATE value
     * @param sceEdate SCE_EDATE value
     */
    public void setSceEdate(UDate sceEdate) {
        sceEdate__ = sceEdate;
    }
    /**
     * <p>rsgSid__ を取得します。
     * @return rsgSid
     */
    public int getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid__ をセットします。
     * @param rsgSid rsgSid__
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }
    /**
     * <p>rssidMap__ を取得します。
     * @return rssidMap
     */
    public HashMap<Integer, Integer> getRssidMap() {
        return rssidMap__;
    }
    /**
     * <p>rssidMap__ をセットします。
     * @param rssidMap rssidMap__
     */
    public void setRssidMap(HashMap<Integer, Integer> rssidMap) {
        rssidMap__ = rssidMap;
    }
    /**
     * <p>usrMap__ を取得します。
     * @return usrMap
     */
    public HashMap<String, String> getUsrMap() {
        return usrMap__;
    }
    /**
     * <p>usrMap__ をセットします。
     * @param usrMap usrMap__
     */
    public void setUsrMap(HashMap<String, String> usrMap) {
        usrMap__ = usrMap;
    }
    /**
     * <p>rsyAuid__ を取得します。
     * @return rsyAuid
     */
    public int getRsyAuid() {
        return rsyAuid__;
    }
    /**
     * <p>rsyAuid__ をセットします。
     * @param rsyAuid rsyAuid__
     */
    public void setRsyAuid(int rsyAuid) {
        rsyAuid__ = rsyAuid;
    }
    /**
     * <p>rsyEdit__ を取得します。
     * @return rsyEdit
     */
    public int getRsyEdit() {
        return rsyEdit__;
    }
    /**
     * <p>rsyEdit__ をセットします。
     * @param rsyEdit rsyEdit__
     */
    public void setRsyEdit(int rsyEdit) {
        rsyEdit__ = rsyEdit;
    }
    /**
     * <p>scdAdate__ を取得します。
     * @return scdAdate
     */
    public UDate getScdAdate() {
        return scdAdate__;
    }
    /**
     * <p>scdAdate__ をセットします。
     * @param scdAdate scdAdate__
     */
    public void setScdAdate(UDate scdAdate) {
        scdAdate__ = scdAdate;
    }
    /**
     * <p>scdAuid__ を取得します。
     * @return scdAuid
     */
    public int getScdAuid() {
        return scdAuid__;
    }
    /**
     * <p>scdAuid__ をセットします。
     * @param scdAuid scdAuid__
     */
    public void setScdAuid(int scdAuid) {
        scdAuid__ = scdAuid;
    }
    /**
     * <p>scdBgcolor__ を取得します。
     * @return scdBgcolor
     */
    public int getScdBgcolor() {
        return scdBgcolor__;
    }
    /**
     * <p>scdBgcolor__ をセットします。
     * @param scdBgcolor scdBgcolor__
     */
    public void setScdBgcolor(int scdBgcolor) {
        scdBgcolor__ = scdBgcolor;
    }
    /**
     * <p>scdBiko__ を取得します。
     * @return scdBiko
     */
    public String getScdBiko() {
        return scdBiko__;
    }
    /**
     * <p>scdBiko__ をセットします。
     * @param scdBiko scdBiko__
     */
    public void setScdBiko(String scdBiko) {
        scdBiko__ = scdBiko;
    }
    /**
     * <p>scdDaily__ を取得します。
     * @return scdDaily
     */
    public int getScdDaily() {
        return scdDaily__;
    }
    /**
     * <p>scdDaily__ をセットします。
     * @param scdDaily scdDaily__
     */
    public void setScdDaily(int scdDaily) {
        scdDaily__ = scdDaily;
    }
    /**
     * <p>scdEdate__ を取得します。
     * @return scdEdate
     */
    public UDate getScdEdate() {
        return scdEdate__;
    }
    /**
     * <p>scdEdate__ をセットします。
     * @param scdEdate scdEdate__
     */
    public void setScdEdate(UDate scdEdate) {
        scdEdate__ = scdEdate;
    }
    /**
     * <p>scdEdit__ を取得します。
     * @return scdEdit
     */
    public int getScdEdit() {
        return scdEdit__;
    }
    /**
     * <p>scdEdit__ をセットします。
     * @param scdEdit scdEdit__
     */
    public void setScdEdit(int scdEdit) {
        scdEdit__ = scdEdit;
    }
    /**
     * <p>scdEuid__ を取得します。
     * @return scdEuid
     */
    public int getScdEuid() {
        return scdEuid__;
    }
    /**
     * <p>scdEuid__ をセットします。
     * @param scdEuid scdEuid__
     */
    public void setScdEuid(int scdEuid) {
        scdEuid__ = scdEuid;
    }
    /**
     * <p>scdFrDate__ を取得します。
     * @return scdFrDate
     */
    public UDate getScdFrDate() {
        return scdFrDate__;
    }
    /**
     * <p>scdFrDate__ をセットします。
     * @param scdFrDate scdFrDate__
     */
    public void setScdFrDate(UDate scdFrDate) {
        scdFrDate__ = scdFrDate;
    }
    /**
     * <p>scdGrpSid__ を取得します。
     * @return scdGrpSid
     */
    public int getScdGrpSid() {
        return scdGrpSid__;
    }
    /**
     * <p>scdGrpSid__ をセットします。
     * @param scdGrpSid scdGrpSid__
     */
    public void setScdGrpSid(int scdGrpSid) {
        scdGrpSid__ = scdGrpSid;
    }
    /**
     * <p>scdPublic__ を取得します。
     * @return scdPublic
     */
    public int getScdPublic() {
        return scdPublic__;
    }
    /**
     * <p>scdPublic__ をセットします。
     * @param scdPublic scdPublic__
     */
    public void setScdPublic(int scdPublic) {
        scdPublic__ = scdPublic;
    }
    /**
     * <p>scdRsSid__ を取得します。
     * @return scdRsSid
     */
    public int getScdRsSid() {
        return scdRsSid__;
    }
    /**
     * <p>scdRsSid__ をセットします。
     * @param scdRsSid scdRsSid__
     */
    public void setScdRsSid(int scdRsSid) {
        scdRsSid__ = scdRsSid;
    }
    /**
     * <p>scdSid__ を取得します。
     * @return scdSid
     */
    public int getScdSid() {
        return scdSid__;
    }
    /**
     * <p>scdSid__ をセットします。
     * @param scdSid scdSid__
     */
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
    }
    /**
     * <p>scdTitle__ を取得します。
     * @return scdTitle
     */
    public String getScdTitle() {
        return scdTitle__;
    }
    /**
     * <p>scdTitle__ をセットします。
     * @param scdTitle scdTitle__
     */
    public void setScdTitle(String scdTitle) {
        scdTitle__ = scdTitle;
    }
    /**
     * <p>scdToDate__ を取得します。
     * @return scdToDate
     */
    public UDate getScdToDate() {
        return scdToDate__;
    }
    /**
     * <p>scdToDate__ をセットします。
     * @param scdToDate scdToDate__
     */
    public void setScdToDate(UDate scdToDate) {
        scdToDate__ = scdToDate;
    }
    /**
     * <p>scdUsrKbn__ を取得します。
     * @return scdUsrKbn
     */
    public int getScdUsrKbn() {
        return scdUsrKbn__;
    }
    /**
     * <p>scdUsrKbn__ をセットします。
     * @param scdUsrKbn scdUsrKbn__
     */
    public void setScdUsrKbn(int scdUsrKbn) {
        scdUsrKbn__ = scdUsrKbn;
    }
    /**
     * <p>scdUsrSid__ を取得します。
     * @return scdUsrSid
     */
    public int getScdUsrSid() {
        return scdUsrSid__;
    }
    /**
     * <p>scdUsrSid__ をセットします。
     * @param scdUsrSid scdUsrSid__
     */
    public void setScdUsrSid(int scdUsrSid) {
        scdUsrSid__ = scdUsrSid;
    }
    /**
     * <p>scdValue__ を取得します。
     * @return scdValue
     */
    public String getScdValue() {
        return scdValue__;
    }
    /**
     * <p>scdValue__ をセットします。
     * @param scdValue scdValue__
     */
    public void setScdValue(String scdValue) {
        scdValue__ = scdValue;
    }
    /**
     * <p>sceSid__ を取得します。
     * @return sceSid
     */
    public int getSceSid() {
        return sceSid__;
    }
    /**
     * <p>sceSid__ をセットします。
     * @param sceSid sceSid__
     */
    public void setSceSid(int sceSid) {
        sceSid__ = sceSid;
    }

    /**
     * <p>sceMonthOfYearly を取得します。
     * @return sceMonthOfYearly
     */
    public int getSceMonthOfYearly() {
        return sceMonthOfYearly__;
    }

    /**
     * <p>sceMonthOfYearly をセットします。
     * @param sceMonthOfYearly sceMonthOfYearly
     */
    public void setSceMonthOfYearly(int sceMonthOfYearly) {
        sceMonthOfYearly__ = sceMonthOfYearly;
    }

    /**
     * <p>sceDayOfYearly を取得します。
     * @return sceDayOfYearly
     */
    public int getSceDayOfYearly() {
        return sceDayOfYearly__;
    }

    /**
     * <p>sceDayOfYearly をセットします。
     * @param sceDayOfYearly sceDayOfYearly
     */
    public void setSceDayOfYearly(int sceDayOfYearly) {
        sceDayOfYearly__ = sceDayOfYearly;
    }
}