package jp.groupsession.v2.adr.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.MailBiz;

/**
 * <p>ADR_ADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrAddressModel implements Serializable {

    /** ADR_SID mapping */
    private int adrSid__;
    /** ADR_SEI mapping */
    private String adrSei__;
    /** ADR_MEI mapping */
    private String adrMei__;
    /** ADR_SEI_KN mapping */
    private String adrSeiKn__;
    /** ADR_MEI_KN mapping */
    private String adrMeiKn__;
    /** ADR_SINI mapping */
    private String adrSini__;
    /** ACO_SID mapping */
    private int acoSid__;
    /** ABA_SID mapping */
    private int abaSid__;
    /** ADR_SYOZOKU mapping */
    private String adrSyozoku__;
    /** APS_SID mapping */
    private int apsSid__;
    /** ADR_MAIL1 mapping */
    private String adrMail1__;
    /** ADR_MAIL_CMT1 mapping */
    private String adrMailCmt1__;
    /** ADR_MAIL2 mapping */
    private String adrMail2__;
    /** ADR_MAIL_CMT2 mapping */
    private String adrMailCmt2__;
    /** ADR_MAIL3 mapping */
    private String adrMail3__;
    /** ADR_MAIL_CMT3 mapping */
    private String adrMailCmt3__;
    /** ADR_POSTNO1 mapping */
    private String adrPostno1__;
    /** ADR_POSTNO2 mapping */
    private String adrPostno2__;
    /** TDF_SID mapping */
    private int tdfSid__;
    /** ADR_ADDR1 mapping */
    private String adrAddr1__;
    /** ADR_ADDR2 mapping */
    private String adrAddr2__;
    /** ADR_TEL1 mapping */
    private String adrTel1__;
    /** ADR_TEL_NAI1 mapping */
    private String adrTelNai1__;
    /** ADR_TEL_CMT1 mapping */
    private String adrTelCmt1__;
    /** ADR_TEL2 mapping */
    private String adrTel2__;
    /** ADR_TEL_NAI2 mapping */
    private String adrTelNai2__;
    /** ADR_TEL_CMT2 mapping */
    private String adrTelCmt2__;
    /** ADR_TEL3 mapping */
    private String adrTel3__;
    /** ADR_TEL_NAI3 mapping */
    private String adrTelNai3__;
    /** ADR_TEL_CMT3 mapping */
    private String adrTelCmt3__;
    /** ADR_FAX1 mapping */
    private String adrFax1__;
    /** ADR_FAX_CMT1 mapping */
    private String adrFaxCmt1__;
    /** ADR_FAX2 mapping */
    private String adrFax2__;
    /** ADR_FAX_CMT2 mapping */
    private String adrFaxCmt2__;
    /** ADR_FAX3 mapping */
    private String adrFax3__;
    /** ADR_FAX_CMT3 mapping */
    private String adrFaxCmt3__;
    /** ADR_BIKO mapping */
    private String adrBiko__;
    /** ADR_PERMIT_VIEW mapping */
    private int adrPermitView__;
    /** ADR_PERMIT_EDIT mapping */
    private int adrPermitEdit__;
    /** ADR_AUID mapping */
    private int adrAuid__;
    /** ADR_ADATE mapping */
    private UDate adrAdate__;
    /** ADR_EUID mapping */
    private int adrEuid__;
    /** ADR_EDATE mapping */
    private UDate adrEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrAddressModel() {
    }

    /**
     * <p>get ADR_SID value
     * @return ADR_SID value
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>set ADR_SID value
     * @param adrSid ADR_SID value
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }

    /**
     * <p>get ADR_SEI value
     * @return ADR_SEI value
     */
    public String getAdrSei() {
        return adrSei__;
    }

    /**
     * <p>set ADR_SEI value
     * @param adrSei ADR_SEI value
     */
    public void setAdrSei(String adrSei) {
        adrSei__ = adrSei;
    }

    /**
     * <p>get ADR_MEI value
     * @return ADR_MEI value
     */
    public String getAdrMei() {
        return adrMei__;
    }

    /**
     * <p>set ADR_MEI value
     * @param adrMei ADR_MEI value
     */
    public void setAdrMei(String adrMei) {
        adrMei__ = adrMei;
    }

    /**
     * <p>get ADR_SEI_KN value
     * @return ADR_SEI_KN value
     */
    public String getAdrSeiKn() {
        return adrSeiKn__;
    }

    /**
     * <p>set ADR_SEI_KN value
     * @param adrSeiKn ADR_SEI_KN value
     */
    public void setAdrSeiKn(String adrSeiKn) {
        adrSeiKn__ = adrSeiKn;
    }

    /**
     * <p>get ADR_MEI_KN value
     * @return ADR_MEI_KN value
     */
    public String getAdrMeiKn() {
        return adrMeiKn__;
    }

    /**
     * <p>set ADR_MEI_KN value
     * @param adrMeiKn ADR_MEI_KN value
     */
    public void setAdrMeiKn(String adrMeiKn) {
        adrMeiKn__ = adrMeiKn;
    }

    /**
     * <p>get ADR_SINI value
     * @return ADR_SINI value
     */
    public String getAdrSini() {
        return adrSini__;
    }

    /**
     * <p>set ADR_SINI value
     * @param adrSini ADR_SINI value
     */
    public void setAdrSini(String adrSini) {
        adrSini__ = adrSini;
    }

    /**
     * <p>get ACO_SID value
     * @return ACO_SID value
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>set ACO_SID value
     * @param acoSid ACO_SID value
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>get ABA_SID value
     * @return ABA_SID value
     */
    public int getAbaSid() {
        return abaSid__;
    }

    /**
     * <p>set ABA_SID value
     * @param abaSid ABA_SID value
     */
    public void setAbaSid(int abaSid) {
        abaSid__ = abaSid;
    }

    /**
     * <p>get ADR_SYOZOKU value
     * @return ADR_SYOZOKU value
     */
    public String getAdrSyozoku() {
        return adrSyozoku__;
    }

    /**
     * <p>set ADR_SYOZOKU value
     * @param adrSyozoku ADR_SYOZOKU value
     */
    public void setAdrSyozoku(String adrSyozoku) {
        adrSyozoku__ = adrSyozoku;
    }

    /**
     * <p>get APS_SID value
     * @return APS_SID value
     */
    public int getApsSid() {
        return apsSid__;
    }

    /**
     * <p>set APS_SID value
     * @param apsSid APS_SID value
     */
    public void setApsSid(int apsSid) {
        apsSid__ = apsSid;
    }

    /**
     * <p>get ADR_MAIL1 value
     * @return ADR_MAIL1 value
     */
    public String getAdrMail1() {
        return adrMail1__;
    }

    /**
     * <p>set ADR_MAIL1 value
     * @param adrMail1 ADR_MAIL1 value
     */
    public void setAdrMail1(String adrMail1) {
        adrMail1__ = adrMail1;
    }

    /**
     * <p>get ADR_MAIL_CMT1 value
     * @return ADR_MAIL_CMT1 value
     */
    public String getAdrMailCmt1() {
        return adrMailCmt1__;
    }

    /**
     * <p>set ADR_MAIL_CMT1 value
     * @param adrMailCmt1 ADR_MAIL_CMT1 value
     */
    public void setAdrMailCmt1(String adrMailCmt1) {
        adrMailCmt1__ = adrMailCmt1;
    }

    /**
     * <p>get ADR_MAIL2 value
     * @return ADR_MAIL2 value
     */
    public String getAdrMail2() {
        return adrMail2__;
    }

    /**
     * <p>set ADR_MAIL2 value
     * @param adrMail2 ADR_MAIL2 value
     */
    public void setAdrMail2(String adrMail2) {
        adrMail2__ = adrMail2;
    }

    /**
     * <p>get ADR_MAIL_CMT2 value
     * @return ADR_MAIL_CMT2 value
     */
    public String getAdrMailCmt2() {
        return adrMailCmt2__;
    }

    /**
     * <p>set ADR_MAIL_CMT2 value
     * @param adrMailCmt2 ADR_MAIL_CMT2 value
     */
    public void setAdrMailCmt2(String adrMailCmt2) {
        adrMailCmt2__ = adrMailCmt2;
    }

    /**
     * <p>get ADR_MAIL3 value
     * @return ADR_MAIL3 value
     */
    public String getAdrMail3() {
        return adrMail3__;
    }

    /**
     * <p>set ADR_MAIL3 value
     * @param adrMail3 ADR_MAIL3 value
     */
    public void setAdrMail3(String adrMail3) {
        adrMail3__ = adrMail3;
    }

    /**
     * <p>get ADR_MAIL_CMT3 value
     * @return ADR_MAIL_CMT3 value
     */
    public String getAdrMailCmt3() {
        return adrMailCmt3__;
    }

    /**
     * <p>set ADR_MAIL_CMT3 value
     * @param adrMailCmt3 ADR_MAIL_CMT3 value
     */
    public void setAdrMailCmt3(String adrMailCmt3) {
        adrMailCmt3__ = adrMailCmt3;
    }

    /**
     * <p>get ADR_POSTNO1 value
     * @return ADR_POSTNO1 value
     */
    public String getAdrPostno1() {
        return adrPostno1__;
    }

    /**
     * <p>set ADR_POSTNO1 value
     * @param adrPostno1 ADR_POSTNO1 value
     */
    public void setAdrPostno1(String adrPostno1) {
        adrPostno1__ = adrPostno1;
    }

    /**
     * <p>get ADR_POSTNO2 value
     * @return ADR_POSTNO2 value
     */
    public String getAdrPostno2() {
        return adrPostno2__;
    }

    /**
     * <p>set ADR_POSTNO2 value
     * @param adrPostno2 ADR_POSTNO2 value
     */
    public void setAdrPostno2(String adrPostno2) {
        adrPostno2__ = adrPostno2;
    }

    /**
     * <p>get TDF_SID value
     * @return TDF_SID value
     */
    public int getTdfSid() {
        return tdfSid__;
    }

    /**
     * <p>set TDF_SID value
     * @param tdfSid TDF_SID value
     */
    public void setTdfSid(int tdfSid) {
        tdfSid__ = tdfSid;
    }

    /**
     * <p>get ADR_ADDR1 value
     * @return ADR_ADDR1 value
     */
    public String getAdrAddr1() {
        return adrAddr1__;
    }

    /**
     * <p>set ADR_ADDR1 value
     * @param adrAddr1 ADR_ADDR1 value
     */
    public void setAdrAddr1(String adrAddr1) {
        adrAddr1__ = adrAddr1;
    }

    /**
     * <p>get ADR_ADDR2 value
     * @return ADR_ADDR2 value
     */
    public String getAdrAddr2() {
        return adrAddr2__;
    }

    /**
     * <p>set ADR_ADDR2 value
     * @param adrAddr2 ADR_ADDR2 value
     */
    public void setAdrAddr2(String adrAddr2) {
        adrAddr2__ = adrAddr2;
    }

    /**
     * <p>get ADR_TEL1 value
     * @return ADR_TEL1 value
     */
    public String getAdrTel1() {
        return adrTel1__;
    }

    /**
     * <p>set ADR_TEL1 value
     * @param adrTel1 ADR_TEL1 value
     */
    public void setAdrTel1(String adrTel1) {
        adrTel1__ = adrTel1;
    }

    /**
     * <p>get ADR_TEL_NAI1 value
     * @return ADR_TEL_NAI1 value
     */
    public String getAdrTelNai1() {
        return adrTelNai1__;
    }

    /**
     * <p>set ADR_TEL_NAI1 value
     * @param adrTelNai1 ADR_TEL_NAI1 value
     */
    public void setAdrTelNai1(String adrTelNai1) {
        adrTelNai1__ = adrTelNai1;
    }

    /**
     * <p>get ADR_TEL_CMT1 value
     * @return ADR_TEL_CMT1 value
     */
    public String getAdrTelCmt1() {
        return adrTelCmt1__;
    }

    /**
     * <p>set ADR_TEL_CMT1 value
     * @param adrTelCmt1 ADR_TEL_CMT1 value
     */
    public void setAdrTelCmt1(String adrTelCmt1) {
        adrTelCmt1__ = adrTelCmt1;
    }

    /**
     * <p>get ADR_TEL2 value
     * @return ADR_TEL2 value
     */
    public String getAdrTel2() {
        return adrTel2__;
    }

    /**
     * <p>set ADR_TEL2 value
     * @param adrTel2 ADR_TEL2 value
     */
    public void setAdrTel2(String adrTel2) {
        adrTel2__ = adrTel2;
    }

    /**
     * <p>get ADR_TEL_NAI2 value
     * @return ADR_TEL_NAI2 value
     */
    public String getAdrTelNai2() {
        return adrTelNai2__;
    }

    /**
     * <p>set ADR_TEL_NAI2 value
     * @param adrTelNai2 ADR_TEL_NAI2 value
     */
    public void setAdrTelNai2(String adrTelNai2) {
        adrTelNai2__ = adrTelNai2;
    }

    /**
     * <p>get ADR_TEL_CMT2 value
     * @return ADR_TEL_CMT2 value
     */
    public String getAdrTelCmt2() {
        return adrTelCmt2__;
    }

    /**
     * <p>set ADR_TEL_CMT2 value
     * @param adrTelCmt2 ADR_TEL_CMT2 value
     */
    public void setAdrTelCmt2(String adrTelCmt2) {
        adrTelCmt2__ = adrTelCmt2;
    }

    /**
     * <p>get ADR_TEL3 value
     * @return ADR_TEL3 value
     */
    public String getAdrTel3() {
        return adrTel3__;
    }

    /**
     * <p>set ADR_TEL3 value
     * @param adrTel3 ADR_TEL3 value
     */
    public void setAdrTel3(String adrTel3) {
        adrTel3__ = adrTel3;
    }

    /**
     * <p>get ADR_TEL_NAI3 value
     * @return ADR_TEL_NAI3 value
     */
    public String getAdrTelNai3() {
        return adrTelNai3__;
    }

    /**
     * <p>set ADR_TEL_NAI3 value
     * @param adrTelNai3 ADR_TEL_NAI3 value
     */
    public void setAdrTelNai3(String adrTelNai3) {
        adrTelNai3__ = adrTelNai3;
    }

    /**
     * <p>get ADR_TEL_CMT3 value
     * @return ADR_TEL_CMT3 value
     */
    public String getAdrTelCmt3() {
        return adrTelCmt3__;
    }

    /**
     * <p>set ADR_TEL_CMT3 value
     * @param adrTelCmt3 ADR_TEL_CMT3 value
     */
    public void setAdrTelCmt3(String adrTelCmt3) {
        adrTelCmt3__ = adrTelCmt3;
    }

    /**
     * <p>get ADR_FAX1 value
     * @return ADR_FAX1 value
     */
    public String getAdrFax1() {
        return adrFax1__;
    }

    /**
     * <p>set ADR_FAX1 value
     * @param adrFax1 ADR_FAX1 value
     */
    public void setAdrFax1(String adrFax1) {
        adrFax1__ = adrFax1;
    }

    /**
     * <p>get ADR_FAX_CMT1 value
     * @return ADR_FAX_CMT1 value
     */
    public String getAdrFaxCmt1() {
        return adrFaxCmt1__;
    }

    /**
     * <p>set ADR_FAX_CMT1 value
     * @param adrFaxCmt1 ADR_FAX_CMT1 value
     */
    public void setAdrFaxCmt1(String adrFaxCmt1) {
        adrFaxCmt1__ = adrFaxCmt1;
    }

    /**
     * <p>get ADR_FAX2 value
     * @return ADR_FAX2 value
     */
    public String getAdrFax2() {
        return adrFax2__;
    }

    /**
     * <p>set ADR_FAX2 value
     * @param adrFax2 ADR_FAX2 value
     */
    public void setAdrFax2(String adrFax2) {
        adrFax2__ = adrFax2;
    }

    /**
     * <p>get ADR_FAX_CMT2 value
     * @return ADR_FAX_CMT2 value
     */
    public String getAdrFaxCmt2() {
        return adrFaxCmt2__;
    }

    /**
     * <p>set ADR_FAX_CMT2 value
     * @param adrFaxCmt2 ADR_FAX_CMT2 value
     */
    public void setAdrFaxCmt2(String adrFaxCmt2) {
        adrFaxCmt2__ = adrFaxCmt2;
    }

    /**
     * <p>get ADR_FAX3 value
     * @return ADR_FAX3 value
     */
    public String getAdrFax3() {
        return adrFax3__;
    }

    /**
     * <p>set ADR_FAX3 value
     * @param adrFax3 ADR_FAX3 value
     */
    public void setAdrFax3(String adrFax3) {
        adrFax3__ = adrFax3;
    }

    /**
     * <p>get ADR_FAX_CMT3 value
     * @return ADR_FAX_CMT3 value
     */
    public String getAdrFaxCmt3() {
        return adrFaxCmt3__;
    }

    /**
     * <p>set ADR_FAX_CMT3 value
     * @param adrFaxCmt3 ADR_FAX_CMT3 value
     */
    public void setAdrFaxCmt3(String adrFaxCmt3) {
        adrFaxCmt3__ = adrFaxCmt3;
    }

    /**
     * <p>get ADR_BIKO value
     * @return ADR_BIKO value
     */
    public String getAdrBiko() {
        return adrBiko__;
    }

    /**
     * <p>set ADR_BIKO value
     * @param adrBiko ADR_BIKO value
     */
    public void setAdrBiko(String adrBiko) {
        adrBiko__ = adrBiko;
    }

    /**
     * <p>get ADR_PERMIT_VIEW value
     * @return ADR_PERMIT_VIEW value
     */
    public int getAdrPermitView() {
        return adrPermitView__;
    }

    /**
     * <p>set ADR_PERMIT_VIEW value
     * @param adrPermitView ADR_PERMIT_VIEW value
     */
    public void setAdrPermitView(int adrPermitView) {
        adrPermitView__ = adrPermitView;
    }

    /**
     * <p>get ADR_PERMIT_EDIT value
     * @return ADR_PERMIT_EDIT value
     */
    public int getAdrPermitEdit() {
        return adrPermitEdit__;
    }

    /**
     * <p>set ADR_PERMIT_EDIT value
     * @param adrPermitEdit ADR_PERMIT_EDIT value
     */
    public void setAdrPermitEdit(int adrPermitEdit) {
        adrPermitEdit__ = adrPermitEdit;
    }

    /**
     * <p>get ADR_AUID value
     * @return ADR_AUID value
     */
    public int getAdrAuid() {
        return adrAuid__;
    }

    /**
     * <p>set ADR_AUID value
     * @param adrAuid ADR_AUID value
     */
    public void setAdrAuid(int adrAuid) {
        adrAuid__ = adrAuid;
    }

    /**
     * <p>get ADR_ADATE value
     * @return ADR_ADATE value
     */
    public UDate getAdrAdate() {
        return adrAdate__;
    }

    /**
     * <p>set ADR_ADATE value
     * @param adrAdate ADR_ADATE value
     */
    public void setAdrAdate(UDate adrAdate) {
        adrAdate__ = adrAdate;
    }

    /**
     * <p>get ADR_EUID value
     * @return ADR_EUID value
     */
    public int getAdrEuid() {
        return adrEuid__;
    }

    /**
     * <p>set ADR_EUID value
     * @param adrEuid ADR_EUID value
     */
    public void setAdrEuid(int adrEuid) {
        adrEuid__ = adrEuid;
    }

    /**
     * <p>get ADR_EDATE value
     * @return ADR_EDATE value
     */
    public UDate getAdrEdate() {
        return adrEdate__;
    }

    /**
     * <p>set ADR_EDATE value
     * @param adrEdate ADR_EDATE value
     */
    public void setAdrEdate(UDate adrEdate) {
        adrEdate__ = adrEdate;
    }

    /**
     * <br>[機  能] メールアドレスの「名前」部分を取得します。
     * <br>[解  説] 「氏名 姓」、「氏名 名」からメールアドレスの「名前」欄を作成します。
     * <br>[備  考]
     * @return メールアドレスの「名前」部分
     */
    public String getAdrMailPersonal() {
        String personal = NullDefault.getString(getAdrSei(), "");
        personal += " " + NullDefault.getString(getAdrMei(), "");
        return MailBiz.formatPersonal(personal);
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(adrSei__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMei__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrSeiKn__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMeiKn__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrSini__, ""));
        buf.append(",");
        buf.append(acoSid__);
        buf.append(",");
        buf.append(abaSid__);
        buf.append(",");
        buf.append(NullDefault.getString(adrSyozoku__, ""));
        buf.append(",");
        buf.append(apsSid__);
        buf.append(",");
        buf.append(NullDefault.getString(adrMail1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMailCmt1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMail2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMailCmt2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMail3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrMailCmt3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrPostno1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrPostno2__, ""));
        buf.append(",");
        buf.append(tdfSid__);
        buf.append(",");
        buf.append(NullDefault.getString(adrAddr1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrAddr2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTel1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTelNai1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTelCmt1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTel2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTelNai2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTelCmt2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTel3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTelNai3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrTelCmt3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrFax1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrFaxCmt1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrFax2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrFaxCmt2__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrFax3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrFaxCmt3__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(adrBiko__, ""));
        buf.append(",");
        buf.append(adrPermitView__);
        buf.append(",");
        buf.append(adrPermitEdit__);
        buf.append(",");
        buf.append(adrAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(adrAdate__, ""));
        buf.append(",");
        buf.append(adrEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(adrEdate__, ""));
        return buf.toString();
    }

}
