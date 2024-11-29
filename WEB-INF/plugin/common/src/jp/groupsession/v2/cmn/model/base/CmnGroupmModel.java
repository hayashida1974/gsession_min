package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>CMN_GROUPM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnGroupmModel extends AbstractModel {

    /** GRP_SID mapping */
    private int grpSid__;
    /** GRP_ID mapping */
    private String grpId__;
    /** GRP_NAME mapping */
    private String grpName__;
    /** GRP_NAME_KN mapping */
    private String grpNameKn__;
    /** GRP_COMMENT mapping */
    private String grpComment__;
    /** GRP_AUID mapping */
    private int grpAuid__;
    /** GRP_ADATE mapping */
    private UDate grpAdate__;
    /** GRP_EUID mapping */
    private int grpEuid__;
    /** GRP_EDATE mapping */
    private UDate grpEdate__;
    /** GRP_SORT mapping */
    private int grpSort__;
    /** GRP_JKBN mapping */
    private int grpJkbn__;

    /**
     * <p>Default Constructor
     */
    public CmnGroupmModel() {
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get GRP_NAME value
     * @return GRP_NAME value
     */
    public String getGrpName() {
        return grpName__;
    }

    /**
     * <p>set GRP_NAME value
     * @param grpName GRP_NAME value
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }

    /**
     * <p>get GRP_NAME_KN value
     * @return GRP_NAME_KN value
     */
    public String getGrpNameKn() {
        return grpNameKn__;
    }

    /**
     * <p>set GRP_NAME_KN value
     * @param grpNameKn GRP_NAME_KN value
     */
    public void setGrpNameKn(String grpNameKn) {
        grpNameKn__ = grpNameKn;
    }

    /**
     * <p>get GRP_COMMENT value
     * @return GRP_COMMENT value
     */
    public String getGrpComment() {
        return grpComment__;
    }

    /**
     * <p>set GRP_COMMENT value
     * @param grpComment GRP_COMMENT value
     */
    public void setGrpComment(String grpComment) {
        grpComment__ = grpComment;
    }

    /**
     * <p>get GRP_AUID value
     * @return GRP_AUID value
     */
    public int getGrpAuid() {
        return grpAuid__;
    }

    /**
     * <p>set GRP_AUID value
     * @param grpAuid GRP_AUID value
     */
    public void setGrpAuid(int grpAuid) {
        grpAuid__ = grpAuid;
    }

    /**
     * <p>get GRP_ADATE value
     * @return GRP_ADATE value
     */
    public UDate getGrpAdate() {
        return grpAdate__;
    }

    /**
     * <p>set GRP_ADATE value
     * @param grpAdate GRP_ADATE value
     */
    public void setGrpAdate(UDate grpAdate) {
        grpAdate__ = grpAdate;
    }

    /**
     * <p>get GRP_EUID value
     * @return GRP_EUID value
     */
    public int getGrpEuid() {
        return grpEuid__;
    }

    /**
     * <p>set GRP_EUID value
     * @param grpEuid GRP_EUID value
     */
    public void setGrpEuid(int grpEuid) {
        grpEuid__ = grpEuid;
    }

    /**
     * <p>get GRP_EDATE value
     * @return GRP_EDATE value
     */
    public UDate getGrpEdate() {
        return grpEdate__;
    }

    /**
     * <p>set GRP_EDATE value
     * @param grpEdate GRP_EDATE value
     */
    public void setGrpEdate(UDate grpEdate) {
        grpEdate__ = grpEdate;
    }

    /**
     * <p>get GRP_SORT value
     * @return GRP_SORT value
     */
    public int getGrpSort() {
        return grpSort__;
    }

    /**
     * <p>set GRP_SORT value
     * @param grpSort GRP_SORT value
     */
    public void setGrpSort(int grpSort) {
        grpSort__ = grpSort;
    }


    /**
     * <p>get GRP_JKBN value
     * @return GRP_JKBN value
     */
    public int getGrpJkbn() {
        return grpJkbn__;
    }

    /**
     * <p>set GRP_JKBN value
     * @param grpJkbn GRP_JKBN value
     */
    public void setGrpJkbn(int grpJkbn) {
        grpJkbn__ = grpJkbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(grpSid__);
        buf.append(",");
        buf.append(NullDefault.getString(grpName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(grpNameKn__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(grpComment__, ""));
        buf.append(",");
        buf.append(grpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(grpAdate__, ""));
        buf.append(",");
        buf.append(grpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(grpEdate__, ""));
        buf.append(",");
        buf.append(grpSort__);
        buf.append(",");
        buf.append(grpJkbn__);
        return buf.toString();
    }

    /**
     * <p>grpId を取得します。
     * @return grpId
     */
    public String getGrpId() {
        return grpId__;
    }

    /**
     * <p>grpId をセットします。
     * @param grpId grpId
     */
    public void setGrpId(String grpId) {
        grpId__ = grpId;
    }

}
