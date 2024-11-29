package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.cmn.GSConst;

import java.io.Serializable;

/**
 * <p>ADR_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrAconfModel implements Serializable {

    /** AAC_ATI_EDIT mapping */
    private int aacAtiEdit__;
    /** AAC_ACO_EDIT mapping */
    private int aacAcoEdit__;
    /** AAC_ALB_EDIT mapping */
    private int aacAlbEdit__;
    /** AAC_EXPORT mapping */
    private int aacExport__;
    /** AAC_AUID mapping */
    private int aacAuid__;
    /** AAC_ADATE mapping */
    private UDate aacAdate__;
    /** AAC_EUID mapping */
    private int aacEuid__;
    /** AAC_EDATE mapping */
    private UDate aacEdate__;
    /** AAC_YKS_EDIT mapping */
    private int aacYksEdit__;
    /** AAC_VRM_EDIT mapping */
    private int aacVrmEdit__;
    /** AAC_PVW_KBN mapping */
    private int aacPvwKbn__;
    /** AAC_PET_KBN mapping */
    private int aacPetKbn__;
    /** AAC_AREST_KBN mapping */
    private int aacArestKbn__;

    /**
     * <p>Default Constructor
     */
    public AdrAconfModel() {
    }

    /**
     * <p>aacYksEdit を取得します。
     * @return aacYksEdit
     */
    public int getAacYksEdit() {
        return aacYksEdit__;
    }

    /**
     * <p>aacYksEdit をセットします。
     * @param aacYksEdit aacYksEdit
     */
    public void setAacYksEdit(int aacYksEdit) {
        aacYksEdit__ = aacYksEdit;
    }

    /**
     * <p>get AAC_ATI_EDIT value
     * @return AAC_ATI_EDIT value
     */
    public int getAacAtiEdit() {
        return aacAtiEdit__;
    }

    /**
     * <p>set AAC_ATI_EDIT value
     * @param aacAtiEdit AAC_ATI_EDIT value
     */
    public void setAacAtiEdit(int aacAtiEdit) {
        aacAtiEdit__ = aacAtiEdit;
    }

    /**
     * <p>get AAC_ACO_EDIT value
     * @return AAC_ACO_EDIT value
     */
    public int getAacAcoEdit() {
        return aacAcoEdit__;
    }

    /**
     * <p>set AAC_ACO_EDIT value
     * @param aacAcoEdit AAC_ACO_EDIT value
     */
    public void setAacAcoEdit(int aacAcoEdit) {
        aacAcoEdit__ = aacAcoEdit;
    }

    /**
     * <p>get AAC_ALB_EDIT value
     * @return AAC_ALB_EDIT value
     */
    public int getAacAlbEdit() {
        return aacAlbEdit__;
    }

    /**
     * <p>set AAC_ALB_EDIT value
     * @param aacAlbEdit AAC_ALB_EDIT value
     */
    public void setAacAlbEdit(int aacAlbEdit) {
        aacAlbEdit__ = aacAlbEdit;
    }

    /**
     * <p>get AAC_EXPORT value
     * @return AAC_EXPORT value
     */
    public int getAacExport() {
        return aacExport__;
    }

    /**
     * <p>set AAC_EXPORT value
     * @param aacExport AAC_EXPORT value
     */
    public void setAacExport(int aacExport) {
        aacExport__ = aacExport;
    }

    /**
     * <p>get AAC_AUID value
     * @return AAC_AUID value
     */
    public int getAacAuid() {
        return aacAuid__;
    }

    /**
     * <p>set AAC_AUID value
     * @param aacAuid AAC_AUID value
     */
    public void setAacAuid(int aacAuid) {
        aacAuid__ = aacAuid;
    }

    /**
     * <p>get AAC_ADATE value
     * @return AAC_ADATE value
     */
    public UDate getAacAdate() {
        return aacAdate__;
    }

    /**
     * <p>set AAC_ADATE value
     * @param aacAdate AAC_ADATE value
     */
    public void setAacAdate(UDate aacAdate) {
        aacAdate__ = aacAdate;
    }

    /**
     * <p>get AAC_EUID value
     * @return AAC_EUID value
     */
    public int getAacEuid() {
        return aacEuid__;
    }

    /**
     * <p>set AAC_EUID value
     * @param aacEuid AAC_EUID value
     */
    public void setAacEuid(int aacEuid) {
        aacEuid__ = aacEuid;
    }

    /**
     * <p>get AAC_EDATE value
     * @return AAC_EDATE value
     */
    public UDate getAacEdate() {
        return aacEdate__;
    }

    /**
     * <p>set AAC_EDATE value
     * @param aacEdate AAC_EDATE value
     */
    public void setAacEdate(UDate aacEdate) {
        aacEdate__ = aacEdate;
    }
    /**
     * <p>aacPetKbn を取得します。
     * @return aacPetKbn
     */
    public int getAacPetKbn() {
        return aacPetKbn__;
    }

    /**
     * <p>aacPetKbn をセットします。
     * @param aacPetKbn aacPetKbn
     */
    public void setAacPetKbn(int aacPetKbn) {
        aacPetKbn__ = aacPetKbn;
    }

    /**
     * <p>aacPvwKbn を取得します。
     * @return aacPvwKbn
     */
    public int getAacPvwKbn() {
        return aacPvwKbn__;
    }

    /**
     * <p>aacPvwKbn をセットします。
     * @param aacPvwKbn aacPvwKbn
     */
    public void setAacPvwKbn(int aacPvwKbn) {
        aacPvwKbn__ = aacPvwKbn;
    }

    /**
     * <p>aacVrmEdit を取得します。
     * @return aacVrmEdit
     */
    public int getAacVrmEdit() {
        return aacVrmEdit__;
    }

    /**
     * <p>aacVrmEdit をセットします。
     * @param aacVrmEdit aacVrmEdit
     */
    public void setAacVrmEdit(int aacVrmEdit) {
        aacVrmEdit__ = aacVrmEdit;
    }

    /**
     * <p>aacArestKbn を取得します。
     * @return aacErestKbn
     */
    public int getAacArestKbn() {
        return aacArestKbn__;
    }

    /**
     * <p>aacArestKbn をセットします。
     * @param aacArestKbn aacArestKbn
     */
    public void setAacArestKbn(int aacArestKbn) {
        aacArestKbn__ = aacArestKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(aacAtiEdit__);
        buf.append(",");
        buf.append(aacAcoEdit__);
        buf.append(",");
        buf.append(aacAlbEdit__);
        buf.append(",");
        buf.append(aacExport__);
        buf.append(",");
        buf.append(aacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(aacAdate__, ""));
        buf.append(",");
        buf.append(aacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(aacEdate__, ""));
        return buf.toString();
    }
    /**
     *
     * <br>[機  能] デフォルト設定で初期化する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void init() {
        setAacAtiEdit(0);
        setAacAcoEdit(0);
        setAacAlbEdit(0);
        setAacExport(1);
        setAacYksEdit(0);
        setAacVrmEdit(GSConstAddress.MEM_DSP_ADM);
        setAacPvwKbn(GSConst.ADR_VIEWPERMIT_OWN);
        setAacPetKbn(GSConstAddress.EDITPERMIT_OWN);
    }
}
