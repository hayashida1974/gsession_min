package jp.groupsession.v2.cmn.model;

import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
/**
 *
 * <br>[機  能] ユーザ情報
 * <br>[解  説] ユーザID、ログイン停止状態、削除フラグの取得を保障
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnUserModel implements IUserMiniimalInfo {
    /** ユーザ情報 */
    private final CmnUsrmInfModel baseUsrmInfo__;
    /** ユーザ情報 */
    private final CmnUsrmModel baseUsrm__;


    public CmnUserModel(CmnUsrmInfModel baseUsrmInfo, CmnUsrmModel baseUsrm) {
        super();
        baseUsrmInfo__ = baseUsrmInfo;
        baseUsrm__ = baseUsrm;
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsigpSid()
     */
    public String getUsigpSid() {
        return baseUsrmInfo__.getUsigpSid();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsigpNm()
     */
    public String getUsigpNm() {
        return baseUsrmInfo__.getUsigpNm();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMblUse()
     */
    public int getUsiMblUse() {
        return baseUsrmInfo__.getUsiMblUse();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsrSid()
     */
    @Override
    public int getUsrSid() {
        return baseUsrmInfo__.getUsrSid();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsiSei()
     */
    @Override
    public String getUsiSei() {
        return baseUsrmInfo__.getUsiSei();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsiMei()
     */
    @Override
    public String getUsiMei() {
        return baseUsrmInfo__.getUsiMei();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsiSeiKn()
     */
    @Override
    public String getUsiSeiKn() {
        return baseUsrmInfo__.getUsiSeiKn();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsiMeiKn()
     */
    @Override
    public String getUsiMeiKn() {
        return baseUsrmInfo__.getUsiMeiKn();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSini()
     */
    public String getUsiSini() {
        return baseUsrmInfo__.getUsiSini();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsiName()
     */
    @Override
    public String getUsiName() {
        return baseUsrmInfo__.getUsiName();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiBdate()
     */
    public UDate getUsiBdate() {
        return baseUsrmInfo__.getUsiBdate();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiZip1()
     */
    public String getUsiZip1() {
        return baseUsrmInfo__.getUsiZip1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiZip2()
     */
    public String getUsiZip2() {
        return baseUsrmInfo__.getUsiZip2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getTdfSid()
     */
    public int getTdfSid() {
        return baseUsrmInfo__.getTdfSid();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiAddr1()
     */
    public String getUsiAddr1() {
        return baseUsrmInfo__.getUsiAddr1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiAddr2()
     */
    public String getUsiAddr2() {
        return baseUsrmInfo__.getUsiAddr2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTel1()
     */
    public String getUsiTel1() {
        return baseUsrmInfo__.getUsiTel1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTel2()
     */
    public String getUsiTel2() {
        return baseUsrmInfo__.getUsiTel2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTel3()
     */
    public String getUsiTel3() {
        return baseUsrmInfo__.getUsiTel3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFax1()
     */
    public String getUsiFax1() {
        return baseUsrmInfo__.getUsiFax1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFax2()
     */
    public String getUsiFax2() {
        return baseUsrmInfo__.getUsiFax2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFax3()
     */
    public String getUsiFax3() {
        return baseUsrmInfo__.getUsiFax3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMail1()
     */
    public String getUsiMail1() {
        return baseUsrmInfo__.getUsiMail1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMail2()
     */
    public String getUsiMail2() {
        return baseUsrmInfo__.getUsiMail2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMail3()
     */
    public String getUsiMail3() {
        return baseUsrmInfo__.getUsiMail3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSyainNo()
     */
    public String getUsiSyainNo() {
        return baseUsrmInfo__.getUsiSyainNo();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSyozoku()
     */
    public String getUsiSyozoku() {
        return baseUsrmInfo__.getUsiSyozoku();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiYakusyoku()
     */
    public String getUsiYakusyoku() {
        return baseUsrmInfo__.getUsiYakusyoku();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiBiko()
     */
    public String getUsiBiko() {
        return baseUsrmInfo__.getUsiBiko();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiHenkou()
     */
    public String getUsiHenkou() {
        return baseUsrmInfo__.getUsiHenkou();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiPrvFlg()
     */
    public int getUsiPrvFlg() {
        return baseUsrmInfo__.getUsiPrvFlg();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiLtlgin()
     */
    public UDate getUsiLtlgin() {
        return baseUsrmInfo__.getUsiLtlgin();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiAuid()
     */
    public int getUsiAuid() {
        return baseUsrmInfo__.getUsiAuid();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiAdate()
     */
    public UDate getUsiAdate() {
        return baseUsrmInfo__.getUsiAdate();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiEuid()
     */
    public int getUsiEuid() {
        return baseUsrmInfo__.getUsiEuid();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiEdate()
     */
    public UDate getUsiEdate() {
        return baseUsrmInfo__.getUsiEdate();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getBinSid()
     */
    public Long getBinSid() {
        return baseUsrmInfo__.getBinSid();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiAddr1Kf()
     */
    public int getUsiAddr1Kf() {
        return baseUsrmInfo__.getUsiAddr1Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiAddr2Kf()
     */
    public int getUsiAddr2Kf() {
        return baseUsrmInfo__.getUsiAddr2Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiBdateKf()
     */
    public int getUsiBdateKf() {
        return baseUsrmInfo__.getUsiBdateKf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFax1Kf()
     */
    public int getUsiFax1Kf() {
        return baseUsrmInfo__.getUsiFax1Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFax2Kf()
     */
    public int getUsiFax2Kf() {
        return baseUsrmInfo__.getUsiFax2Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFax3Kf()
     */
    public int getUsiFax3Kf() {
        return baseUsrmInfo__.getUsiFax3Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMail1Kf()
     */
    public int getUsiMail1Kf() {
        return baseUsrmInfo__.getUsiMail1Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMail2Kf()
     */
    public int getUsiMail2Kf() {
        return baseUsrmInfo__.getUsiMail2Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMail3Kf()
     */
    public int getUsiMail3Kf() {
        return baseUsrmInfo__.getUsiMail3Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiPictKf()
     */
    public int getUsiPictKf() {
        return baseUsrmInfo__.getUsiPictKf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTdfKf()
     */
    public int getUsiTdfKf() {
        return baseUsrmInfo__.getUsiTdfKf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTel1Kf()
     */
    public int getUsiTel1Kf() {
        return baseUsrmInfo__.getUsiTel1Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTel2Kf()
     */
    public int getUsiTel2Kf() {
        return baseUsrmInfo__.getUsiTel2Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTel3Kf()
     */
    public int getUsiTel3Kf() {
        return baseUsrmInfo__.getUsiTel3Kf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiZipKf()
     */
    public int getUsiZipKf() {
        return baseUsrmInfo__.getUsiZipKf();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getPosSid()
     */
    public int getPosSid() {
        return baseUsrmInfo__.getPosSid();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFaxCmt1()
     */
    public String getUsiFaxCmt1() {
        return baseUsrmInfo__.getUsiFaxCmt1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFaxCmt2()
     */
    public String getUsiFaxCmt2() {
        return baseUsrmInfo__.getUsiFaxCmt2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiFaxCmt3()
     */
    public String getUsiFaxCmt3() {
        return baseUsrmInfo__.getUsiFaxCmt3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMailCmt1()
     */
    public String getUsiMailCmt1() {
        return baseUsrmInfo__.getUsiMailCmt1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMailCmt2()
     */
    public String getUsiMailCmt2() {
        return baseUsrmInfo__.getUsiMailCmt2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMailCmt3()
     */
    public String getUsiMailCmt3() {
        return baseUsrmInfo__.getUsiMailCmt3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTelCmt1()
     */
    public String getUsiTelCmt1() {
        return baseUsrmInfo__.getUsiTelCmt1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTelCmt2()
     */
    public String getUsiTelCmt2() {
        return baseUsrmInfo__.getUsiTelCmt2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTelCmt3()
     */
    public String getUsiTelCmt3() {
        return baseUsrmInfo__.getUsiTelCmt3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTelNai1()
     */
    public String getUsiTelNai1() {
        return baseUsrmInfo__.getUsiTelNai1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTelNai2()
     */
    public String getUsiTelNai2() {
        return baseUsrmInfo__.getUsiTelNai2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiTelNai3()
     */
    public String getUsiTelNai3() {
        return baseUsrmInfo__.getUsiTelNai3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiNumCont()
     */
    public int getUsiNumCont() {
        return baseUsrmInfo__.getUsiNumCont();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiNumAutadd()
     */
    public int getUsiNumAutadd() {
        return baseUsrmInfo__.getUsiNumAutadd();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiOtpsendAddress()
     */
    public String getUsiOtpsendAddress() {
        return baseUsrmInfo__.getUsiOtpsendAddress();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getLabelNameList()
     */
    public List<String> getLabelNameList() {
        return baseUsrmInfo__.getLabelNameList();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getViewLabelName()
     */
    public String getViewLabelName() {
        return baseUsrmInfo__.getViewLabelName();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getMailPersonal()
     */
    public String getMailPersonal() {
        return baseUsrmInfo__.getMailPersonal();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getMailAddress1()
     */
    public String getMailAddress1() {
        return baseUsrmInfo__.getMailAddress1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getMailAddress2()
     */
    public String getMailAddress2() {
        return baseUsrmInfo__.getMailAddress2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getMailAddress3()
     */
    public String getMailAddress3() {
        return baseUsrmInfo__.getMailAddress3();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSortkey1()
     */
    public String getUsiSortkey1() {
        return baseUsrmInfo__.getUsiSortkey1();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSortkey2()
     */
    public String getUsiSortkey2() {
        return baseUsrmInfo__.getUsiSortkey2();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSeibetu()
     */
    public int getUsiSeibetu() {
        return baseUsrmInfo__.getUsiSeibetu();
    }

    /**
     * @return 委譲先の実行結果
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiEntranceDate()
     */
    public UDate getUsiEntranceDate() {
        return baseUsrmInfo__.getUsiEntranceDate();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsrUkoFlg()
     */
    @Override
    public int getUsrUkoFlg() {
        return baseUsrm__.getUsrUkoFlg();
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsrJkbn()
     */
    @Override
    public int getUsrJkbn() {
        return baseUsrm__.getUsrJkbn();
    }


    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.model.IUserMiniimalInfo#getUsrLgid()
     */
    @Override
    public String getUsrLgid() {
        return baseUsrm__.getUsrLgid();
    }

    @Override
    public String getUsiNameKn() {
        return String.format("%s %s",
                NullDefault.getString(baseUsrmInfo__.getUsiSeiKn(), ""),
                NullDefault.getString(baseUsrmInfo__.getUsiMeiKn(), ""));
    }

}
