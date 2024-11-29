package jp.groupsession.v2.adr.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>ADR_UCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrUconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** AUC_ADRCOUNT mapping */
    private int aucAdrcount__;
    /** AUC_AUID mapping */
    private int aucAuid__;
    /** AUC_ADATE mapping */
    private UDate aucAdate__;
    /** AUC_EUID mapping */
    private int aucEuid__;
    /** AUC_EDATE mapping */
    private UDate aucEdate__;
    /** AUC_COMCOUNT mapping */
    private int aucComcount__;
    /** AUC_PERMIT_VIEW mapping */
    private int aucPermitView__;
    /** AUC_PERMIT_EDIT mapping */
    private int aucPermitEdit__;


    /**
     * <p>Default Constructor
     */
    public AdrUconfModel() {
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
     * <p>get AUC_ADRCOUNT value
     * @return AUC_ADRCOUNT value
     */
    public int getAucAdrcount() {
        return aucAdrcount__;
    }

    /**
     * <p>set AUC_ADRCOUNT value
     * @param aucAdrcount AUC_ADRCOUNT value
     */
    public void setAucAdrcount(int aucAdrcount) {
        aucAdrcount__ = aucAdrcount;
    }

    /**
     * <p>get AUC_AUID value
     * @return AUC_AUID value
     */
    public int getAucAuid() {
        return aucAuid__;
    }

    /**
     * <p>set AUC_AUID value
     * @param aucAuid AUC_AUID value
     */
    public void setAucAuid(int aucAuid) {
        aucAuid__ = aucAuid;
    }

    /**
     * <p>get AUC_ADATE value
     * @return AUC_ADATE value
     */
    public UDate getAucAdate() {
        return aucAdate__;
    }

    /**
     * <p>set AUC_ADATE value
     * @param aucAdate AUC_ADATE value
     */
    public void setAucAdate(UDate aucAdate) {
        aucAdate__ = aucAdate;
    }

    /**
     * <p>get AUC_EUID value
     * @return AUC_EUID value
     */
    public int getAucEuid() {
        return aucEuid__;
    }

    /**
     * <p>set AUC_EUID value
     * @param aucEuid AUC_EUID value
     */
    public void setAucEuid(int aucEuid) {
        aucEuid__ = aucEuid;
    }

    /**
     * <p>get AUC_EDATE value
     * @return AUC_EDATE value
     */
    public UDate getAucEdate() {
        return aucEdate__;
    }

    /**
     * <p>set AUC_EDATE value
     * @param aucEdate AUC_EDATE value
     */
    public void setAucEdate(UDate aucEdate) {
        aucEdate__ = aucEdate;
    }

    /**
     * <p>aucComcount を取得します。
     * @return aucComcount
     */
    public int getAucComcount() {
        return aucComcount__;
    }

    /**
     * <p>aucComcount をセットします。
     * @param aucComcount aucComcount
     */
    public void setAucComcount(int aucComcount) {
        aucComcount__ = aucComcount;
    }

    /**
     * <p>aucPermitEdit を取得します。
     * @return aucPermitEdit
     */
    public int getAucPermitEdit() {
        return aucPermitEdit__;
    }

    /**
     * <p>aucPermitEdit をセットします。
     * @param aucPermitEdit aucPermitEdit
     */
    public void setAucPermitEdit(int aucPermitEdit) {
        aucPermitEdit__ = aucPermitEdit;
    }

    /**
     * <p>aucPermitView を取得します。
     * @return aucPermitView
     */
    public int getAucPermitView() {
        return aucPermitView__;
    }

    /**
     * <p>aucPermitView をセットします。
     * @param aucPermitView aucPermitView
     */
    public void setAucPermitView(int aucPermitView) {
        aucPermitView__ = aucPermitView;
    }
}