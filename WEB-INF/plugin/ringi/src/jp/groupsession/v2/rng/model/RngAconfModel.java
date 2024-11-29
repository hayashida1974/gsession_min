package jp.groupsession.v2.rng.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.rng.RngConst;

/**
 * <p>RNG_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngAconfModel implements Serializable {

    /** RAR_DEL_AUTH mapping */
    private int rarDelAuth__;
    /** RAR_RNGID mapping */
    private int rarRngid__;
    /** RAR_RNGID_DEF_SID mapping */
    private int rarRngidDefSid__;
    /** RAR_AUID mapping */
    private int rarAuid__;
    /** RAR_ADATE mapping */
    private UDate rarAdate__;
    /** RAR_EUID mapping */
    private int rarEuid__;
    /** RAR_EDATE mapping */
    private UDate rarEdate__;
    /** RAR_SML_NTF mapping */
    private int rarSmlNtf__;
    /** RAR_SML_NTF_KBN mapping */
    private int rarSmlNtfKbn__;
    /** RAR_SML_JUSIN*/
    private int rarSmlJusinKbn__;
    /** RAR_SML_KOETU*/
    private int rarSmlKoetuKbn__;
    /** RAR_SML_DAIRI_KBN*/
    private int rarSmlDairiKbn__;
    /** RAR_OVERLAP*/
    private int rarOverlap__;
    /** RAR_HANYO_FLG*/
    private int rarHanyoFlg__;
    /** RAR_TEMPLATE_PERSONAL_FLG*/
    private int rarTemplatePersonalFlg__;
    /** RAR_TEMPLATE_KEIRO_FLG*/
    private int rarKeiroPersonalFlg__;

    /**
     * <p>Default Constructor
     */
    public RngAconfModel() {
    }

    /**
     * <p>get RAR_DEL_AUTH value
     * @return RAR_DEL_AUTH value
     */
    public int getRarDelAuth() {
        return rarDelAuth__;
    }

    /**
     * <p>set RAR_DEL_AUTH value
     * @param rarDelAuth RAR_DEL_AUTH value
     */
    public void setRarDelAuth(int rarDelAuth) {
        rarDelAuth__ = rarDelAuth;
    }

    /**
     * <p>get RAR_AUID value
     * @return RAR_AUID value
     */
    public int getRarAuid() {
        return rarAuid__;
    }

    /**
     * <p>set RAR_AUID value
     * @param rarAuid RAR_AUID value
     */
    public void setRarAuid(int rarAuid) {
        rarAuid__ = rarAuid;
    }

    /**
     * <p>get RAR_ADATE value
     * @return RAR_ADATE value
     */
    public UDate getRarAdate() {
        return rarAdate__;
    }

    /**
     * <p>set RAR_ADATE value
     * @param rarAdate RAR_ADATE value
     */
    public void setRarAdate(UDate rarAdate) {
        rarAdate__ = rarAdate;
    }

    /**
     * <p>get RAR_EUID value
     * @return RAR_EUID value
     */
    public int getRarEuid() {
        return rarEuid__;
    }

    /**
     * <p>set RAR_EUID value
     * @param rarEuid RAR_EUID value
     */
    public void setRarEuid(int rarEuid) {
        rarEuid__ = rarEuid;
    }

    /**
     * <p>get RAR_EDATE value
     * @return RAR_EDATE value
     */
    public UDate getRarEdate() {
        return rarEdate__;
    }

    /**
     * <p>set RAR_EDATE value
     * @param rarEdate RAR_EDATE value
     */
    public void setRarEdate(UDate rarEdate) {
        rarEdate__ = rarEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rarDelAuth__);
        buf.append(",");
        buf.append(rarAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rarAdate__, ""));
        buf.append(",");
        buf.append(rarEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rarEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>rarSmlNtf を取得します。
     * @return rarSmlNtf
     */
    public int getRarSmlNtf() {
        return rarSmlNtf__;
    }

    /**
     * <p>rarSmlNtf をセットします。
     * @param rarSmlNtf rarSmlNtf
     */
    public void setRarSmlNtf(int rarSmlNtf) {
        rarSmlNtf__ = rarSmlNtf;
    }

    /**
     * <p>rarSmlNtfKbn を取得します。
     * @return rarSmlNtfKbn
     */
    public int getRarSmlNtfKbn() {
        return rarSmlNtfKbn__;
    }

    /**
     * <p>rarSmlNtfKbn をセットします。
     * @param rarSmlNtfKbn rarSmlNtfKbn
     */
    public void setRarSmlNtfKbn(int rarSmlNtfKbn) {
        rarSmlNtfKbn__ = rarSmlNtfKbn;
    }

    /**
     * <p>rarRngid を取得します。
     * @return rarRngid
     */
    public int getRarRngid() {
        return rarRngid__;
    }

    /**
     * <p>rarRngid をセットします。
     * @param rarRngid rarRngid
     */
    public void setRarRngid(int rarRngid) {
        rarRngid__ = rarRngid;
    }

    /**
     * <p>rarRngidDefSid を取得します。
     * @return rarRngidDefSid
     */
    public int getRarRngidDefSid() {
        return rarRngidDefSid__;
    }

    /**
     * <p>rarRngidDefSid をセットします。
     * @param rarRngidDefSid rarRngidDefSid
     */
    public void setRarRngidDefSid(int rarRngidDefSid) {
        rarRngidDefSid__ = rarRngidDefSid;
    }

    /**
     * <p>rarSmlJusin を取得します。
     * @return rarSmlJusin
     */
    public int getRarSmlJusinKbn() {
        return rarSmlJusinKbn__;
    }

    /**
     * <p>rarSmlJusin をセットします。
     * @param rarSmlJusin rarSmlJusin
     */
    public void setRarSmlJusinKbn(int rarSmlJusin) {
        rarSmlJusinKbn__ = rarSmlJusin;
    }

    /**
     * <p>rarSmlKoetu を取得します。
     * @return rarSmlKoetu
     */
    public int getRarSmlKoetuKbn() {
        return rarSmlKoetuKbn__;
    }

    /**
     * <p>rarSmlKoetu をセットします。
     * @param rarSmlKoetu rarSmlKoetu
     */
    public void setRarSmlKoetuKbn(int rarSmlKoetu) {
        rarSmlKoetuKbn__ = rarSmlKoetu;
    }

    /**
     * <p>rarSmlDairiKbn を取得します。
     * @return rarSmlDairiKbn
     */
    public int getRarSmlDairiKbn() {
        return rarSmlDairiKbn__;
    }

    /**
     * <p>rarSmlDairiKbn をセットします。
     * @param rarSmlDairiKbn rarSmlDairiKbn
     */
    public void setRarSmlDairiKbn(int rarSmlDairiKbn) {
        rarSmlDairiKbn__ = rarSmlDairiKbn;
    }

    /**
     * <p>get RAR_OVERLAP value
     * @return RAR_OVERLAP value
     */
    public int getRarOverlap() {
        return rarOverlap__;
    }

    /**
     * <p>set RAR_Overlap value
     * @param rarOverlap RAR_Overlap value
     */
    public void setRarOverlap(int rarOverlap) {
        rarOverlap__ = rarOverlap;
    }

    /**
     * 初期設定値セット
     */
    public void initData() {
        this.setRarDelAuth(RngConst.RAR_DEL_AUTH_UNRESTRICTED);
        this.setRarRngid(RngConst.RAR_SINSEI_NONE);
        this.setRarRngidDefSid(0);
        this.setRarSmlNtf(RngConst.RAR_SML_NTF_USER);
        this.setRarHanyoFlg(RngConst.RAR_HANYO_FLG_YES);
        this.setRarTemplatePersonalFlg(RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES);
        this.setRarKeiroPersonalFlg(RngConst.RAR_KEIRO_PERSONAL_FLG_YES);
    }

    /**
     * <p>rarHanyoFlg を取得します。
     * @return rarHanyoFlg
     * @see jp.groupsession.v2.rng.model.RngAconfModel#rarHanyoFlg__
     */
    public int getRarHanyoFlg() {
        return rarHanyoFlg__;
    }

    /**
     * <p>rarHanyoFlg をセットします。
     * @param rarHanyoFlg rarHanyoFlg
     * @see jp.groupsession.v2.rng.model.RngAconfModel#rarHanyoFlg__
     */
    public void setRarHanyoFlg(int rarHanyoFlg) {
        rarHanyoFlg__ = rarHanyoFlg;
    }

    /**
     * <p>rarTemplatePersonalFlg を取得します。
     * @return rarTemplatePersonalFlg
     * @see jp.groupsession.v2.rng.model.RngAconfModel#rarTemplatePersonalFlg__
     */
    public int getRarTemplatePersonalFlg() {
        return rarTemplatePersonalFlg__;
    }

    /**
     * <p>rarTemplatePersonalFlg をセットします。
     * @param rarTemplatePersonalFlg rarTemplatePersonalFlg
     * @see jp.groupsession.v2.rng.model.RngAconfModel#rarTemplatePersonalFlg__
     */
    public void setRarTemplatePersonalFlg(int rarTemplatePersonalFlg) {
        rarTemplatePersonalFlg__ = rarTemplatePersonalFlg;
    }

    /**
     * <p>rarKeiroPersonalFlg を取得します。
     * @return rarKeiroPersonalFlg
     * @see jp.groupsession.v2.rng.model.RngAconfModel#rarKeiroPersonalFlg__
     */
    public int getRarKeiroPersonalFlg() {
        return rarKeiroPersonalFlg__;
    }

    /**
     * <p>rarKeiroPersonalFlg をセットします。
     * @param rarKeiroPersonalFlg rarKeiroPersonalFlg
     * @see jp.groupsession.v2.rng.model.RngAconfModel#rarKeiroPersonalFlg__
     */
    public void setRarKeiroPersonalFlg(int rarKeiroPersonalFlg) {
        rarKeiroPersonalFlg__ = rarKeiroPersonalFlg;
    }
}
