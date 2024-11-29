package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_GROUP_CLASS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnGroupClassModel implements Serializable, Cloneable {

    /**
     * クローン用。
     * @return Object クローン
     * @throws CloneNotSupportedException CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /** GCL_SID1 mapping */
    private int gclSid1__ = -1;
    /** GCL_SID2 mapping */
    private int gclSid2__ = -1;
    /** GCL_SID3 mapping */
    private int gclSid3__ = -1;
    /** GCL_SID4 mapping */
    private int gclSid4__ = -1;
    /** GCL_SID5 mapping */
    private int gclSid5__ = -1;
    /** GCL_SID6 mapping */
    private int gclSid6__ = -1;
    /** GCL_SID7 mapping */
    private int gclSid7__ = -1;
    /** GCL_SID8 mapping */
    private int gclSid8__ = -1;
    /** GCL_SID9 mapping */
    private int gclSid9__ = -1;
    /** GCL_SID10 mapping */
    private int gclSid10__ = -1;

    /** GCL_AUID mapping */
    private int gclAuid__;
    /** GCL_ADATE mapping */
    private UDate gclAdate__;
    /** GCL_EUID mapping */
    private int gclEuid__;
    /** GCL_EDATE mapping */
    private UDate gclEdate__;

    /** 第1階層のグループ名 */
    private String gclName1__ = null;
    /** 第2階層のグループ名 */
    private String gclName2__ = null;
    /** 第3階層のグループ名 */
    private String gclName3__ = null;
    /** 第4階層のグループ名 */
    private String gclName4__ = null;
    /** 第5階層のグループ名 */
    private String gclName5__ = null;
    /** 第6階層のグループ名 */
    private String gclName6__ = null;
    /** 第7階層のグループ名 */
    private String gclName7__ = null;
    /** 第8階層のグループ名 */
    private String gclName8__ = null;
    /** 第9階層のグループ名 */
    private String gclName9__ = null;
    /** 第10階層のグループ名 */
    private String gclName10__ = null;

    /** 第1階層のグループID */
    private String gclId1__ = null;
    /** 第2階層のグループID */
    private String gclId2__ = null;
    /** 第3階層のグループID */
    private String gclId3__ = null;
    /** 第4階層のグループID */
    private String gclId4__ = null;
    /** 第5階層のグループID */
    private String gclId5__ = null;
    /** 第6階層のグループID */
    private String gclId6__ = null;
    /** 第7階層のグループID */
    private String gclId7__ = null;
    /** 第8階層のグループID */
    private String gclId8__ = null;
    /** 第9階層のグループID */
    private String gclId9__ = null;
    /** 第10階層のグループID */
    private String gclId10__ = null;

    /** 第1階層のグループ名カナ */
    private String gclKanaName1__ = null;
    /** 第2階層のグループ名カナ */
    private String gclKanaName2__ = null;
    /** 第3階層のグループ名カナ */
    private String gclKanaName3__ = null;
    /** 第4階層のグループ名カナ */
    private String gclKanaName4__ = null;
    /** 第5階層のグループ名カナ */
    private String gclKanaName5__ = null;
    /** 第6階層のグループ名カナ */
    private String gclKanaName6__ = null;
    /** 第7階層のグループ名カナ */
    private String gclKanaName7__ = null;
    /** 第8階層のグループ名カナ */
    private String gclKanaName8__ = null;
    /** 第9階層のグループ名カナ */
    private String gclKanaName9__ = null;
    /** 第10階層のグループ名カナ */
    private String gclKanaName10__ = null;

    /** 第1階層の親グループID */
    private String gclPid1__ = null;
    /** 第2階層の親グループID */
    private String gclPid2__ = null;
    /** 第3階層の親グループID */
    private String gclPid3__ = null;
    /** 第4階層の親グループID */
    private String gclPid4__ = null;
    /** 第5階層の親グループID */
    private String gclPid5__ = null;
    /** 第6階層の親グループID */
    private String gclPid6__ = null;
    /** 第7階層の親グループID */
    private String gclPid7__ = null;
    /** 第8階層の親グループID */
    private String gclPid8__ = null;
    /** 第9階層の親グループID */
    private String gclPid9__ = null;
    /** 第10階層の親グループID */
    private String gclPid10__ = null;

    /** 第1階層のコメント */
    private String gclCmt1__ = null;
    /** 第2階層のコメント */
    private String gclCmt2__ = null;
    /** 第3階層のコメント */
    private String gclCmt3__ = null;
    /** 第4階層のコメント */
    private String gclCmt4__ = null;
    /** 第5階層のコメント */
    private String gclCmt5__ = null;
    /** 第6階層のコメント */
    private String gclCmt6__ = null;
    /** 第7階層のコメント */
    private String gclCmt7__ = null;
    /** 第8階層のコメント */
    private String gclCmt8__ = null;
    /** 第9階層のコメント */
    private String gclCmt9__ = null;
    /** 第10階層のコメント */
    private String gclCmt10__ = null;

    /**
     * <p>Default Constructor
     */
    public CmnGroupClassModel() {
    }

    /**
     * <p>get GCL_SID1 value
     * @return GCL_SID1 value
     */
    public int getGclSid1() {
        return gclSid1__;
    }

    /**
     * <p>set GCL_SID1 value
     * @param gclSid1 GCL_SID1 value
     */
    public void setGclSid1(int gclSid1) {
        gclSid1__ = gclSid1;
    }

    /**
     * <p>get GCL_SID2 value
     * @return GCL_SID2 value
     */
    public int getGclSid2() {
        return gclSid2__;
    }

    /**
     * <p>set GCL_SID2 value
     * @param gclSid2 GCL_SID2 value
     */
    public void setGclSid2(int gclSid2) {
        gclSid2__ = gclSid2;
    }

    /**
     * <p>get GCL_SID3 value
     * @return GCL_SID3 value
     */
    public int getGclSid3() {
        return gclSid3__;
    }

    /**
     * <p>set GCL_SID3 value
     * @param gclSid3 GCL_SID3 value
     */
    public void setGclSid3(int gclSid3) {
        gclSid3__ = gclSid3;
    }

    /**
     * <p>get GCL_SID4 value
     * @return GCL_SID4 value
     */
    public int getGclSid4() {
        return gclSid4__;
    }

    /**
     * <p>set GCL_SID4 value
     * @param gclSid4 GCL_SID4 value
     */
    public void setGclSid4(int gclSid4) {
        gclSid4__ = gclSid4;
    }

    /**
     * <p>get GCL_SID5 value
     * @return GCL_SID5 value
     */
    public int getGclSid5() {
        return gclSid5__;
    }

    /**
     * <p>set GCL_SID5 value
     * @param gclSid5 GCL_SID5 value
     */
    public void setGclSid5(int gclSid5) {
        gclSid5__ = gclSid5;
    }

    /**
     * <p>get GCL_AUID value
     * @return GCL_AUID value
     */
    public int getGclAuid() {
        return gclAuid__;
    }

    /**
     * <p>set GCL_AUID value
     * @param gclAuid GCL_AUID value
     */
    public void setGclAuid(int gclAuid) {
        gclAuid__ = gclAuid;
    }

    /**
     * <p>get GCL_ADATE value
     * @return GCL_ADATE value
     */
    public UDate getGclAdate() {
        return gclAdate__;
    }

    /**
     * <p>set GCL_ADATE value
     * @param gclAdate GCL_ADATE value
     */
    public void setGclAdate(UDate gclAdate) {
        gclAdate__ = gclAdate;
    }

    /**
     * <p>get GCL_EUID value
     * @return GCL_EUID value
     */
    public int getGclEuid() {
        return gclEuid__;
    }

    /**
     * <p>set GCL_EUID value
     * @param gclEuid GCL_EUID value
     */
    public void setGclEuid(int gclEuid) {
        gclEuid__ = gclEuid;
    }

    /**
     * <p>get GCL_EDATE value
     * @return GCL_EDATE value
     */
    public UDate getGclEdate() {
        return gclEdate__;
    }

    /**
     * <p>set GCL_EDATE value
     * @param gclEdate GCL_EDATE value
     */
    public void setGclEdate(UDate gclEdate) {
        gclEdate__ = gclEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(gclSid1__);
        buf.append(",");
        buf.append(gclSid2__);
        buf.append(",");
        buf.append(gclSid3__);
        buf.append(",");
        buf.append(gclSid4__);
        buf.append(",");
        buf.append(gclSid5__);
        buf.append(",");
        buf.append(gclSid6__);
        buf.append(",");
        buf.append(gclSid7__);
        buf.append(",");
        buf.append(gclSid8__);
        buf.append(",");
        buf.append(gclSid9__);
        buf.append(",");
        buf.append(gclSid10__);
        buf.append(",");
        buf.append(gclAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(gclAdate__, ""));
        buf.append(",");
        buf.append(gclEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(gclEdate__, ""));
        return buf.toString();
    }

    /**
     * @return gclName1 を戻します。
     */
    public String getGclName1() {
        return gclName1__;
    }

    /**
     * @param gclName1 設定する gclName1。
     */
    public void setGclName1(String gclName1) {
        gclName1__ = gclName1;
    }

    /**
     * @return gclName2 を戻します。
     */
    public String getGclName2() {
        return gclName2__;
    }

    /**
     * @param gclName2 設定する gclName2。
     */
    public void setGclName2(String gclName2) {
        gclName2__ = gclName2;
    }

    /**
     * @return gclName3 を戻します。
     */
    public String getGclName3() {
        return gclName3__;
    }

    /**
     * @param gclName3 設定する gclName3。
     */
    public void setGclName3(String gclName3) {
        gclName3__ = gclName3;
    }

    /**
     * @return gclName4 を戻します。
     */
    public String getGclName4() {
        return gclName4__;
    }

    /**
     * @param gclName4 設定する gclName4。
     */
    public void setGclName4(String gclName4) {
        gclName4__ = gclName4;
    }

    /**
     * @return gclName5 を戻します。
     */
    public String getGclName5() {
        return gclName5__;
    }

    /**
     * @param gclName5 設定する gclName5。
     */
    public void setGclName5(String gclName5) {
        gclName5__ = gclName5;
    }

    /**
     * <p>gclPid1 を取得します。
     * @return gclPid1
     */
    public String getGclPid1() {
        return gclPid1__;
    }

    /**
     * <p>gclPid1 をセットします。
     * @param gclPid1 gclPid1
     */
    public void setGclPid1(String gclPid1) {
        gclPid1__ = gclPid1;
    }

    /**
     * <p>gclPid2 を取得します。
     * @return gclPid2
     */
    public String getGclPid2() {
        return gclPid2__;
    }

    /**
     * <p>gclPid2 をセットします。
     * @param gclPid2 gclPid2
     */
    public void setGclPid2(String gclPid2) {
        gclPid2__ = gclPid2;
    }

    /**
     * <p>gclPid3 を取得します。
     * @return gclPid3
     */
    public String getGclPid3() {
        return gclPid3__;
    }

    /**
     * <p>gclPid3 をセットします。
     * @param gclPid3 gclPid3
     */
    public void setGclPid3(String gclPid3) {
        gclPid3__ = gclPid3;
    }

    /**
     * <p>gclPid4 を取得します。
     * @return gclPid4
     */
    public String getGclPid4() {
        return gclPid4__;
    }

    /**
     * <p>gclPid4 をセットします。
     * @param gclPid4 gclPid4
     */
    public void setGclPid4(String gclPid4) {
        gclPid4__ = gclPid4;
    }

    /**
     * <p>gclPid5 を取得します。
     * @return gclPid5
     */
    public String getGclPid5() {
        return gclPid5__;
    }

    /**
     * <p>gclPid5 をセットします。
     * @param gclPid5 gclPid5
     */
    public void setGclPid5(String gclPid5) {
        gclPid5__ = gclPid5;
    }

    /**
     * <p>gclPid6 を取得します。
     * @return gclPid6
     */
    public String getGclPid6() {
        return gclPid6__;
    }

    /**
     * <p>gclPid6 をセットします。
     * @param gclPid6 gclPid6
     */
    public void setGclPid6(String gclPid6) {
        gclPid6__ = gclPid6;
    }

    /**
     * <p>gclPid7 を取得します。
     * @return gclPid7
     */
    public String getGclPid7() {
        return gclPid7__;
    }

    /**
     * <p>gclPid7 をセットします。
     * @param gclPid7 gclPid7
     */
    public void setGclPid7(String gclPid7) {
        gclPid7__ = gclPid7;
    }

    /**
     * <p>gclPid8 を取得します。
     * @return gclPid8
     */
    public String getGclPid8() {
        return gclPid8__;
    }

    /**
     * <p>gclPid8 をセットします。
     * @param gclPid8 gclPid8
     */
    public void setGclPid8(String gclPid8) {
        gclPid8__ = gclPid8;
    }

    /**
     * <p>gclPid9 を取得します。
     * @return gclPid9
     */
    public String getGclPid9() {
        return gclPid9__;
    }

    /**
     * <p>gclPid9 をセットします。
     * @param gclPid9 gclPid9
     */
    public void setGclPid9(String gclPid9) {
        gclPid9__ = gclPid9;
    }

    /**
     * <p>gclPid10 を取得します。
     * @return gclPid10
     */
    public String getGclPid10() {
        return gclPid10__;
    }

    /**
     * <p>gclPid10 をセットします。
     * @param gclPid10 gclPid10
     */
    public void setGclPid10(String gclPid10) {
        gclPid10__ = gclPid10;
    }

    /**
     * グループSIDを指定し親グループSIDを取得します。
     * @param sid グループSID
     * @return int 親グループSID
     */
    public int getParentsSid(int sid) {
        int ret = -1;
        if (gclSid1__ == sid) {
            return ret;
        } else if (gclSid2__ == sid) {
            return gclSid1__;
        } else if (gclSid3__ == sid) {
            return gclSid2__;
        } else if (gclSid4__ == sid) {
            return gclSid3__;
        } else if (gclSid5__ == sid) {
            return gclSid4__;
        } else if (gclSid6__ == sid) {
            return gclSid5__;
        } else if (gclSid7__ == sid) {
            return gclSid6__;
        } else if (gclSid8__ == sid) {
            return gclSid7__;
        } else if (gclSid9__ == sid) {
            return gclSid8__;
        } else if (gclSid10__ == sid) {
            return gclSid9__;
        } else {
            return ret;
        }
    }

    /**
     * <p>最下層グループの階層のレベルを返す
     * @return 階層レベル
     */
    public int getLowGrpLevel() {
        if (gclSid10__ != -1) {
            return 10;
        } else if (gclSid9__ != -1) {
            return 9;
        } else if (gclSid8__ != -1) {
            return 8;
        } else if (gclSid7__ != -1) {
            return 7;
        } else if (gclSid6__ != -1) {
            return 6;
        } else if (gclSid5__ != -1) {
            return 5;
        } else if (gclSid4__ != -1) {
            return 4;
        } else if (gclSid3__ != -1) {
            return 3;
        } else if (gclSid2__ != -1) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * <p>最下層のグループ名を返す
     * @return 最下層のグループ名を返す
     */
    public String getLowGrpName() {
        int level = getLowGrpLevel();
        String gpName = null;
        switch (level) {
        case 1:
            gpName = this.getGclName1();
            break;
        case 2:
            gpName = this.getGclName2();
            break;
        case 3:
            gpName = this.getGclName3();
            break;
        case 4:
            gpName = this.getGclName4();
            break;
        case 5:
            gpName = this.getGclName5();
            break;
        case 6:
            gpName = this.getGclName6();
            break;
        case 7:
            gpName = this.getGclName7();
            break;
        case 8:
            gpName = this.getGclName8();
            break;
        case 9:
            gpName = this.getGclName9();
            break;
        case 10:
        default:
            gpName = this.getGclName10();
            break;
        }
        return gpName;
    }

    /**
     * <p>最下層のグループIDを返す
     * @return 最下層のグループ名を返す
     */
    public String getLowGrpId() {
        int level = getLowGrpLevel();
        String gpId = null;
        switch (level) {
        case 1:
            gpId = this.getGclId1();
            break;
        case 2:
            gpId = this.getGclId2();
            break;
        case 3:
            gpId = this.getGclId3();
            break;
        case 4:
            gpId = this.getGclId4();
            break;
        case 5:
            gpId = this.getGclId5();
            break;
        case 6:
            gpId = this.getGclId6();
            break;
        case 7:
            gpId = this.getGclId7();
            break;
        case 8:
            gpId = this.getGclId8();
            break;
        case 9:
            gpId = this.getGclId9();
            break;
        case 10:
        default:
            gpId = this.getGclId10();
            break;
        }
        return gpId;
    }

    /**
     * <p>最下層のグループSIDを返す
     * @return 最下層のグループSID
     */
    public int getLowGrpSid() {
        int level = getLowGrpLevel();
        int gpsid = -1;
        switch (level) {
        case 1:
            gpsid = this.getGclSid1();
            break;
        case 2:
            gpsid = this.getGclSid2();
            break;
        case 3:
            gpsid = this.getGclSid3();
            break;
        case 4:
            gpsid = this.getGclSid4();
            break;
        case 5:
            gpsid = this.getGclSid5();
            break;
        case 6:
            gpsid = this.getGclSid6();
            break;
        case 7:
            gpsid = this.getGclSid7();
            break;
        case 8:
            gpsid = this.getGclSid8();
            break;
        case 9:
            gpsid = this.getGclSid9();
            break;
        case 10:
        default:
            gpsid = this.getGclSid10();
            break;
        }
        return gpsid;
    }

    /**
     * グループSIDを指定し、階層レベルを取得します。
     * 指定グループSIDが、このModelに存在しない場合、return = -1
     * @param sid グループSID
     * @return i
     */
    public int getClassLevel(int sid) {
        if (sid == -1) {
            return -1;
        } else if (gclSid1__ == sid) {
            return 1;
        } else if (gclSid2__ == sid) {
            return 2;
        } else if (gclSid3__ == sid) {
            return 3;
        } else if (gclSid4__ == sid) {
            return 4;
        } else if (gclSid5__ == sid) {
            return 5;
        } else if (gclSid6__ == sid) {
            return 6;
        } else if (gclSid7__ == sid) {
            return 7;
        } else if (gclSid8__ == sid) {
            return 8;
        } else if (gclSid9__ == sid) {
            return 9;
        } else if (gclSid10__ == sid) {
            return 10;
        } else {
            return -1;
        }
    }
    /**
     * 最下位層の階層レベルを取得します。
     * @return 最下位層レベル
     */
    public int getUnderGroupLevel() {

        if (gclSid2__ == -1) {
            return 1;
        } else if (gclSid3__ == -1) {
            return 2;
        } else if (gclSid4__ == -1) {
            return 3;
        } else if (gclSid5__ == -1) {
            return 4;
        } else if (gclSid6__ == -1) {
            return 5;
        } else if (gclSid7__ == -1) {
            return 6;
        } else if (gclSid8__ == -1) {
            return 7;
        } else if (gclSid9__ == -1) {
            return 8;
        } else if (gclSid10__ == -1) {
            return 9;
        } else {
            return 10;
        }
    }

    /**
     * 最下位層のグループSIDを取得します。
     * @return グループSID
     */
    public int getUnderGroup() {

        if (gclSid2__ == -1) {
            return gclSid1__;
        } else if (gclSid3__ == -1) {
            return gclSid2__;
        } else if (gclSid4__ == -1) {
            return gclSid3__;
        } else if (gclSid5__ == -1) {
            return gclSid4__;
        } else if (gclSid6__ == -1) {
            return gclSid5__;
        } else if (gclSid7__ == -1) {
            return gclSid6__;
        } else if (gclSid8__ == -1) {
            return gclSid7__;
        } else if (gclSid9__ == -1) {
            return gclSid8__;
        } else if (gclSid10__ == -1) {
            return gclSid9__;
        } else {
            return gclSid10__;
        }
    }
    /**
     * 最下位層へグループを追加します。
     * @param sid グループSID
     */
    public void addUnder(int sid) {
        int level = getClassLevel(this.getUnderGroup());
        switch (level) {
            case 1 :
                setGclSid2(sid);
                break;
            case 2 :
                setGclSid3(sid);
                break;
            case 3 :
                setGclSid4(sid);
                break;
            case 4 :
                setGclSid5(sid);
                break;
            case 5 :
                setGclSid6(sid);
                break;
            case 6 :
                setGclSid7(sid);
                break;
            case 7 :
                setGclSid8(sid);
                break;
            case 8 :
                setGclSid9(sid);
                break;
            case 9 :
                setGclSid10(sid);
                break;
            default :
            //Rootグループ
                setGclSid1(sid);
                break;
        }
    }

    /**
     * グループSIDを指定し、指定グループ以下の階層のグループSIDリストを取得。
     * @param sid グループSID
     * @return ArrayList
     */
    public ArrayList<Integer> getGroupSids(int sid) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (gclSid1__ == sid) {
            ret.add(new Integer(gclSid1__));
            ret.add(new Integer(gclSid2__));
            ret.add(new Integer(gclSid3__));
            ret.add(new Integer(gclSid4__));
            ret.add(new Integer(gclSid5__));
            ret.add(new Integer(gclSid6__));
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid2__ == sid) {
            ret.add(new Integer(gclSid2__));
            ret.add(new Integer(gclSid3__));
            ret.add(new Integer(gclSid4__));
            ret.add(new Integer(gclSid5__));
            ret.add(new Integer(gclSid6__));
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid3__ == sid) {
            ret.add(new Integer(gclSid3__));
            ret.add(new Integer(gclSid4__));
            ret.add(new Integer(gclSid5__));
            ret.add(new Integer(gclSid6__));
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid4__ == sid) {
            ret.add(new Integer(gclSid4__));
            ret.add(new Integer(gclSid5__));
            ret.add(new Integer(gclSid6__));
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid5__ == sid) {
            ret.add(new Integer(gclSid5__));
            ret.add(new Integer(gclSid6__));
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid6__ == sid) {
            ret.add(new Integer(gclSid6__));
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid7__ == sid) {
            ret.add(new Integer(gclSid7__));
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid8__ == sid) {
            ret.add(new Integer(gclSid8__));
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid9__ == sid) {
            ret.add(new Integer(gclSid9__));
            ret.add(new Integer(gclSid10__));
        } else if (gclSid10__ == sid) {
            ret.add(new Integer(gclSid10__));
        }
        return ret;
    }

    /**
     * <p>gclSid6 を取得します。
     * @return gclSid6
     */
    public int getGclSid6() {
        return gclSid6__;
    }

    /**
     * <p>gclSid6 をセットします。
     * @param gclSid6 gclSid6
     */
    public void setGclSid6(int gclSid6) {
        gclSid6__ = gclSid6;
    }

    /**
     * <p>gclSid7 を取得します。
     * @return gclSid7
     */
    public int getGclSid7() {
        return gclSid7__;
    }

    /**
     * <p>gclSid7 をセットします。
     * @param gclSid7 gclSid7
     */
    public void setGclSid7(int gclSid7) {
        gclSid7__ = gclSid7;
    }

    /**
     * <p>gclSid8 を取得します。
     * @return gclSid8
     */
    public int getGclSid8() {
        return gclSid8__;
    }

    /**
     * <p>gclSid8 をセットします。
     * @param gclSid8 gclSid8
     */
    public void setGclSid8(int gclSid8) {
        gclSid8__ = gclSid8;
    }

    /**
     * <p>gclSid9 を取得します。
     * @return gclSid9
     */
    public int getGclSid9() {
        return gclSid9__;
    }

    /**
     * <p>gclSid9 をセットします。
     * @param gclSid9 gclSid9
     */
    public void setGclSid9(int gclSid9) {
        gclSid9__ = gclSid9;
    }

    /**
     * <p>gclSid10 を取得します。
     * @return gclSid10
     */
    public int getGclSid10() {
        return gclSid10__;
    }

    /**
     * <p>gclSid10 をセットします。
     * @param gclSid10 gclSid10
     */
    public void setGclSid10(int gclSid10) {
        gclSid10__ = gclSid10;
    }

    /**
     * <p>gclName6 を取得します。
     * @return gclName6
     */
    public String getGclName6() {
        return gclName6__;
    }

    /**
     * <p>gclName6 をセットします。
     * @param gclName6 gclName6
     */
    public void setGclName6(String gclName6) {
        gclName6__ = gclName6;
    }

    /**
     * <p>gclName7 を取得します。
     * @return gclName7
     */
    public String getGclName7() {
        return gclName7__;
    }

    /**
     * <p>gclName7 をセットします。
     * @param gclName7 gclName7
     */
    public void setGclName7(String gclName7) {
        gclName7__ = gclName7;
    }

    /**
     * <p>gclName8 を取得します。
     * @return gclName8
     */
    public String getGclName8() {
        return gclName8__;
    }

    /**
     * <p>gclName8 をセットします。
     * @param gclName8 gclName8
     */
    public void setGclName8(String gclName8) {
        gclName8__ = gclName8;
    }

    /**
     * <p>gclName9 を取得します。
     * @return gclName9
     */
    public String getGclName9() {
        return gclName9__;
    }

    /**
     * <p>gclName9 をセットします。
     * @param gclName9 gclName9
     */
    public void setGclName9(String gclName9) {
        gclName9__ = gclName9;
    }

    /**
     * <p>gclName10 を取得します。
     * @return gclName10
     */
    public String getGclName10() {
        return gclName10__;
    }

    /**
     * <p>gclName10 をセットします。
     * @param gclName10 gclName10
     */
    public void setGclName10(String gclName10) {
        gclName10__ = gclName10;
    }

    /**
     * <p>gclId6 を取得します。
     * @return gclId6
     */
    public String getGclId6() {
        return gclId6__;
    }

    /**
     * <p>gclId6 をセットします。
     * @param gclId6 gclId6
     */
    public void setGclId6(String gclId6) {
        gclId6__ = gclId6;
    }

    /**
     * <p>gclId7 を取得します。
     * @return gclId7
     */
    public String getGclId7() {
        return gclId7__;
    }

    /**
     * <p>gclId7 をセットします。
     * @param gclId7 gclId7
     */
    public void setGclId7(String gclId7) {
        gclId7__ = gclId7;
    }

    /**
     * <p>gclId8 を取得します。
     * @return gclId8
     */
    public String getGclId8() {
        return gclId8__;
    }

    /**
     * <p>gclId8 をセットします。
     * @param gclId8 gclId8
     */
    public void setGclId8(String gclId8) {
        gclId8__ = gclId8;
    }

    /**
     * <p>gclId9 を取得します。
     * @return gclId9
     */
    public String getGclId9() {
        return gclId9__;
    }

    /**
     * <p>gclId9 をセットします。
     * @param gclId9 gclId9
     */
    public void setGclId9(String gclId9) {
        gclId9__ = gclId9;
    }

    /**
     * <p>gclId10 を取得します。
     * @return gclId10
     */
    public String getGclId10() {
        return gclId10__;
    }

    /**
     * <p>gclId10 をセットします。
     * @param gclId10 gclId10
     */
    public void setGclId10(String gclId10) {
        gclId10__ = gclId10;
    }

    /**
     * <p>gclId1 を取得します。
     * @return gclId1
     */
    public String getGclId1() {
        return gclId1__;
    }

    /**
     * <p>gclId1 をセットします。
     * @param gclId1 gclId1
     */
    public void setGclId1(String gclId1) {
        gclId1__ = gclId1;
    }

    /**
     * <p>gclId2 を取得します。
     * @return gclId2
     */
    public String getGclId2() {
        return gclId2__;
    }

    /**
     * <p>gclId2 をセットします。
     * @param gclId2 gclId2
     */
    public void setGclId2(String gclId2) {
        gclId2__ = gclId2;
    }

    /**
     * <p>gclId3 を取得します。
     * @return gclId3
     */
    public String getGclId3() {
        return gclId3__;
    }

    /**
     * <p>gclId3 をセットします。
     * @param gclId3 gclId3
     */
    public void setGclId3(String gclId3) {
        gclId3__ = gclId3;
    }

    /**
     * <p>gclId4 を取得します。
     * @return gclId4
     */
    public String getGclId4() {
        return gclId4__;
    }

    /**
     * <p>gclId4 をセットします。
     * @param gclId4 gclId4
     */
    public void setGclId4(String gclId4) {
        gclId4__ = gclId4;
    }

    /**
     * <p>gclId5 を取得します。
     * @return gclId5
     */
    public String getGclId5() {
        return gclId5__;
    }

    /**
     * <p>gclId5 をセットします。
     * @param gclId5 gclId5
     */
    public void setGclId5(String gclId5) {
        gclId5__ = gclId5;
    }

    /**
     * <p>gclKanaName1 を取得します。
     * @return gclKanaName1
     */
    public String getGclKanaName1() {
        return gclKanaName1__;
    }
    /**
     * <p>gclKanaName1 をセットします。
     * @param gclKanaName1 gclKanaName1
     */
    public void setGclKanaName1(String gclKanaName1) {
        gclKanaName1__ = gclKanaName1;
    }
    /**
     * <p>gclKanaName2 を取得します。
     * @return gclKanaName2
     */
    public String getGclKanaName2() {
        return gclKanaName2__;
    }
    /**
     * <p>gclKanaName2 をセットします。
     * @param gclKanaName2 gclKanaName2
     */
    public void setGclKanaName2(String gclKanaName2) {
        gclKanaName2__ = gclKanaName2;
    }
    /**
     * <p>gclKanaName3 を取得します。
     * @return gclKanaName3
     */
    public String getGclKanaName3() {
        return gclKanaName3__;
    }
    /**
     * <p>gclKanaName3 をセットします。
     * @param gclKanaName3 gclKanaName3
     */
    public void setGclKanaName3(String gclKanaName3) {
        gclKanaName3__ = gclKanaName3;
    }
    /**
     * <p>gclKanaName4 を取得します。
     * @return gclKanaName4
     */
    public String getGclKanaName4() {
        return gclKanaName4__;
    }
    /**
     * <p>gclKanaName4 をセットします。
     * @param gclKanaName4 gclKanaName4
     */
    public void setGclKanaName4(String gclKanaName4) {
        gclKanaName4__ = gclKanaName4;
    }
    /**
     * <p>gclKanaName5 を取得します。
     * @return gclKanaName5
     */
    public String getGclKanaName5() {
        return gclKanaName5__;
    }
    /**
     * <p>gclKanaName5 をセットします。
     * @param gclKanaName5 gclKanaName5
     */
    public void setGclKanaName5(String gclKanaName5) {
        gclKanaName5__ = gclKanaName5;
    }
    /**
     * <p>gclKanaName6 を取得します。
     * @return gclKanaName6
     */
    public String getGclKanaName6() {
        return gclKanaName6__;
    }
    /**
     * <p>gclKanaName6 をセットします。
     * @param gclKanaName6 gclKanaName6
     */
    public void setGclKanaName6(String gclKanaName6) {
        gclKanaName6__ = gclKanaName6;
    }
    /**
     * <p>gclKanaName7 を取得します。
     * @return gclKanaName7
     */
    public String getGclKanaName7() {
        return gclKanaName7__;
    }
    /**
     * <p>gclKanaName7 をセットします。
     * @param gclKanaName7 gclKanaName7
     */
    public void setGclKanaName7(String gclKanaName7) {
        gclKanaName7__ = gclKanaName7;
    }
    /**
     * <p>gclKanaName8 を取得します。
     * @return gclKanaName8
     */
    public String getGclKanaName8() {
        return gclKanaName8__;
    }
    /**
     * <p>gclKanaName8 をセットします。
     * @param gclKanaName8 gclKanaName8
     */
    public void setGclKanaName8(String gclKanaName8) {
        gclKanaName8__ = gclKanaName8;
    }
    /**
     * <p>gclKanaName9 を取得します。
     * @return gclKanaName9
     */
    public String getGclKanaName9() {
        return gclKanaName9__;
    }
    /**
     * <p>gclKanaName9 をセットします。
     * @param gclKanaName9 gclKanaName9
     */
    public void setGclKanaName9(String gclKanaName9) {
        gclKanaName9__ = gclKanaName9;
    }
    /**
     * <p>gclKanaName10 を取得します。
     * @return gclKanaName10
     */
    public String getGclKanaName10() {
        return gclKanaName10__;
    }
    /**
     * <p>gclKanaName10 をセットします。
     * @param gclKanaName10 gclKanaName10
     */
    public void setGclKanaName10(String gclKanaName10) {
        gclKanaName10__ = gclKanaName10;
    }
    /**
     * <p>gclCmt1 を取得します。
     * @return gclCmt1
     */
    public String getGclCmt1() {
        return gclCmt1__;
    }
    /**
     * <p>gclCmt1 をセットします。
     * @param gclCmt1 gclCmt1
     */
    public void setGclCmt1(String gclCmt1) {
        gclCmt1__ = gclCmt1;
    }
    /**
     * <p>gclCmt2 を取得します。
     * @return gclCmt2
     */
    public String getGclCmt2() {
        return gclCmt2__;
    }
    /**
     * <p>gclCmt2 をセットします。
     * @param gclCmt2 gclCmt2
     */
    public void setGclCmt2(String gclCmt2) {
        gclCmt2__ = gclCmt2;
    }
    /**
     * <p>gclCmt3 を取得します。
     * @return gclCmt3
     */
    public String getGclCmt3() {
        return gclCmt3__;
    }
    /**
     * <p>gclCmt3 をセットします。
     * @param gclCmt3 gclCmt3
     */
    public void setGclCmt3(String gclCmt3) {
        gclCmt3__ = gclCmt3;
    }
    /**
     * <p>gclCmt4 を取得します。
     * @return gclCmt4
     */
    public String getGclCmt4() {
        return gclCmt4__;
    }
    /**
     * <p>gclCmt4 をセットします。
     * @param gclCmt4 gclCmt4
     */
    public void setGclCmt4(String gclCmt4) {
        gclCmt4__ = gclCmt4;
    }
    /**
     * <p>gclCmt5 を取得します。
     * @return gclCmt5
     */
    public String getGclCmt5() {
        return gclCmt5__;
    }
    /**
     * <p>gclCmt5 をセットします。
     * @param gclCmt5 gclCmt5
     */
    public void setGclCmt5(String gclCmt5) {
        gclCmt5__ = gclCmt5;
    }
    /**
     * <p>gclCmt6 を取得します。
     * @return gclCmt6
     */
    public String getGclCmt6() {
        return gclCmt6__;
    }
    /**
     * <p>gclCmt6 をセットします。
     * @param gclCmt6 gclCmt6
     */
    public void setGclCmt6(String gclCmt6) {
        gclCmt6__ = gclCmt6;
    }
    /**
     * <p>gclCmt7 を取得します。
     * @return gclCmt7
     */
    public String getGclCmt7() {
        return gclCmt7__;
    }
    /**
     * <p>gclCmt7 をセットします。
     * @param gclCmt7 gclCmt7
     */
    public void setGclCmt7(String gclCmt7) {
        gclCmt7__ = gclCmt7;
    }
    /**
     * <p>gclCmt8 を取得します。
     * @return gclCmt8
     */
    public String getGclCmt8() {
        return gclCmt8__;
    }
    /**
     * <p>gclCmt8 をセットします。
     * @param gclCmt8 gclCmt8
     */
    public void setGclCmt8(String gclCmt8) {
        gclCmt8__ = gclCmt8;
    }
    /**
     * <p>gclCmt9 を取得します。
     * @return gclCmt9
     */
    public String getGclCmt9() {
        return gclCmt9__;
    }
    /**
     * <p>gclCmt9 をセットします。
     * @param gclCmt9 gclCmt9
     */
    public void setGclCmt9(String gclCmt9) {
        gclCmt9__ = gclCmt9;
    }
    /**
     * <p>gclCmt10 を取得します。
     * @return gclCmt10
     */
    public String getGclCmt10() {
        return gclCmt10__;
    }
    /**
     * <p>gclCmt10 をセットします。
     * @param gclCmt10 gclCmt10
     */
    public void setGclCmt10(String gclCmt10) {
        gclCmt10__ = gclCmt10;
    }

    /**
     * <p>最下層のグループ名カナを返す
     * @return 最下層のグループ名を返す
     */
    public String getLowGrpKanaName() {
        int level = getLowGrpLevel();
        String gpKanaName = null;
        switch (level) {
        case 1:
            gpKanaName = this.getGclKanaName1();
            break;
        case 2:
            gpKanaName = this.getGclKanaName2();
            break;
        case 3:
            gpKanaName = this.getGclKanaName3();
            break;
        case 4:
            gpKanaName = this.getGclKanaName4();
            break;
        case 5:
            gpKanaName = this.getGclKanaName5();
            break;
        case 6:
            gpKanaName = this.getGclKanaName6();
            break;
        case 7:
            gpKanaName = this.getGclKanaName7();
            break;
        case 8:
            gpKanaName = this.getGclKanaName8();
            break;
        case 9:
            gpKanaName = this.getGclKanaName9();
            break;
        case 10:
        default:
            gpKanaName = this.getGclKanaName10();
        }
        return gpKanaName;
    }

    /**
     * <p>最下層の親グループIDを返す
     * @return 最下層のグループ名を返す
     */
    public String getLowParentGrpId() {
        int level = getLowGrpLevel();
        String parentGpId = null;
        switch (level) {
        case 1:
            break;
        case 2:
            parentGpId = this.getGclId1();
            break;
        case 3:
            parentGpId = this.getGclId2();
            break;
        case 4:
            parentGpId = this.getGclId3();
            break;
        case 5:
            parentGpId = this.getGclId4();
            break;
        case 6:
            parentGpId = this.getGclId5();
            break;
        case 7:
            parentGpId = this.getGclId6();
            break;
        case 8:
            parentGpId = this.getGclId7();
            break;
        case 9:
            parentGpId = this.getGclId8();
            break;
        case 10:
            parentGpId = this.getGclId9();
        default:
        }
        return parentGpId;
    }

    /**
     * <p>最下層のコメントを返す
     * @return 最下層のコメントを返す
     */
    public String getLowCmt() {
        int level = getLowGrpLevel();
        String cmt = null;
        switch (level) {
        case 1:
            cmt = this.getGclCmt1();
            break;
        case 2:
            cmt = this.getGclCmt2();
            break;
        case 3:
            cmt = this.getGclCmt3();
            break;
        case 4:
            cmt = this.getGclCmt4();
            break;
        case 5:
            cmt = this.getGclCmt5();
            break;
        case 6:
            cmt = this.getGclCmt6();
            break;
        case 7:
            cmt = this.getGclCmt7();
            break;
        case 8:
            cmt = this.getGclCmt8();
            break;
        case 9:
            cmt = this.getGclCmt9();
            break;
        case 10:
            cmt = this.getGclCmt10();
        default:
        }
        return cmt;
    }
}
