package jp.groupsession.v2.cmn.cmn100;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn100Form extends AbstractGsForm {

    /** プラグインID */
    private String cmn100pluginId__ = GSConstCommon.SBNSID_COMMON;
    /** ユーザSID */
    private String cmn100usid__ = null;
    /** 性 */
    private String cmn100usiSei__ = null;
    /** 名 */
    private String cmn100usiMei__ = null;
    /** 性カナ */
    private String cmn100usiSeiKn__ = null;
    /** 名カナ */
    private String cmn100usiMeiKn__ = null;
    /** 社員/職員番号 */
    private String cmn100usiSyainNo__ = null;
    /** 役職 */
    private String cmn100usiYakusyoku__ = null;
    /** 備考 */
    private String cmn100usiBiko__ = null;
    /** 所属グループ */
    private ArrayList<Cmn100GroupModel> cmn100grpNmlist__ = null;

    /** 性別 */
    private String cmn100usiSeibetu__ = null;
    /** 入社年月日 */
    private String cmn100entranceDate__ = null;

    /** 所属 */
    private String cmn100usiSyozoku__ = null;
    /** 生年月日 */
    private String cmn100usiBdate__ = null;
    /** 生年月日公開フラグ */
    private int cmn100usiBdateKf__ = 0;
    /** 年齢 */
    private int cmn100usiAge__ = 0;
    /** メールアドレス１ */
    private String cmn100usiMail1__ = null;
    /** メールアドレスコメント１ */
    private String cmn100usiMailCmt1__ = null;
    /** メールアドレス１公開フラグ */
    private int cmn100usiMail1Kf__ = 0;
    /** メールアドレス２ */
    private String cmn100usiMail2__ = null;
    /** メールアドレスコメント２ */
    private String cmn100usiMailCmt2__ = null;
    /** メールアドレス２公開フラグ */
    private int cmn100usiMail2Kf__ = 0;
    /** メールアドレス３ */
    private String cmn100usiMail3__ = null;
    /** メールアドレスコメント３ */
    private String cmn100usiMailCmt3__ = null;
    /** メールアドレス３公開フラグ */
    private int cmn100usiMail3Kf__ = 0;
    /** 郵便番号 */
    private String cmn100usiZip__ = null;
    /** 郵便番号公開フラグ */
    private int cmn100usiZipKf__ = 0;
    /** 都道府県名 */
    private String cmn100TdfkName__ = null;
    /** 都道府県公開フラグ */
    private int cmn100usiTdfkKf__ = 0;
    /** 住所１ */
    private String cmn100usiAddr1__ = null;
    /** 住所１公開フラグ */
    private int cmn100usiAddr1Kf__ = 0;
    /** 住所２ */
    private String cmn100usiAddr2__ = null;
    /** 住所２公開フラグ */
    private int cmn100usiAddr2Kf__ = 0;
    /** 電話番号１ */
    private String cmn100usiTel1__ = null;
    /** 電話番号内線１ */
    private String cmn100usiTelNai1__ = null;
    /** 電話番号コメント１ */
    private String cmn100usiTelCmt1__ = null;
    /** 電話番号１公開フラグ */
    private int cmn100usiTel1Kf__ = 0;
    /** 電話番号２ */
    private String cmn100usiTel2__ = null;
    /** 電話番号内線２ */
    private String cmn100usiTelNai2__ = null;
    /** 電話番号コメント２ */
    private String cmn100usiTelCmt2__ = null;
    /** 電話番号２公開フラグ */
    private int cmn100usiTel2Kf__ = 0;
    /** 電話番号３ */
    private String cmn100usiTel3__ = null;
    /** 電話番号内線３ */
    private String cmn100usiTelNai3__ = null;
    /** 電話番号コメント３ */
    private String cmn100usiTelCmt3__ = null;
    /** 電話番号３公開フラグ */
    private int cmn100usiTel3Kf__ = 0;
    /** ＦＡＸ１ */
    private String cmn100usiFax1__ = null;
    /** ＦＡＸコメント１ */
    private String cmn100usiFaxCmt1__ = null;
    /** ＦＡＸ１公開フラグ */
    private int cmn100usiFax1Kf__ = 0;
    /** ＦＡＸ２ */
    private String cmn100usiFax2__ = null;
    /** ＦＡＸコメント２ */
    private String cmn100usiFaxCmt2__ = null;
    /** ＦＡＸ２公開フラグ */
    private int cmn100usiFax2Kf__ = 0;
    /** ＦＡＸ３ */
    private String cmn100usiFax3__ = null;
    /** ＦＡＸコメント３ */
    private String cmn100usiFaxCmt3__ = null;
    /** ＦＡＸ３公開フラグ */
    private int cmn100usiFax3Kf__ = 0;
    /** 写真ファイルSID */
    private Long cmn100binSid__ = new Long(0);

    /** 写真公開フラグ */
    private int cmn100usiPictKf__ = 0;
    /** フォームアンロードフラグ */
    private boolean formUnload__ = false;

    /** 状態区分 */
    private String cmn100usrJkbn__ = null;

    /** 追加情報 */
    private ArrayList <Cmn100AppendInfoModel> appendInfoList__ = null;

    /** モバイルプラグイン使用可否 **/
    private int cmn100UsiMblUse__;
    /** モバイルプラグイン使用可否 区分 **/
    private int cmn100UsiMblUseKbn__;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int cmn100searchUse__ = GSConst.PLUGIN_USE;

    /** ユーザラベル名の配列 */
    private ArrayList<CmnLabelUsrModel> labelList__;

    /** WEB検索ワード（地図） */
    private String cmn100WebSearchWord__ = "";

    /** QRコード表示区分*/
    private int cmn100QrDispKbn = GSConstUser.QR_CODE_DISP;

    /** 有効フラグ 0=有効 1=無効 */
    private int cmn100usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;

    /**
     * <p>cmn100UsiMblUse__ を取得します。
     * @return cmn100UsiMblUse
     */
    public int getCmn100UsiMblUse() {
        return cmn100UsiMblUse__;
    }

    /**
     * <p>cmn100UsiMblUse__ をセットします。
     * @param cmn100UsiMblUse cmn100UsiMblUse__
     */
    public void setCmn100UsiMblUse(int cmn100UsiMblUse) {
        cmn100UsiMblUse__ = cmn100UsiMblUse;
    }

    /**
     * <p>cmn100UsiMblUseKbn__ を取得します。
     * @return cmn100UsiMblUseKbn
     */
    public int getCmn100UsiMblUseKbn() {
        return cmn100UsiMblUseKbn__;
    }

    /**
     * <p>cmn100UsiMblUseKbn__ をセットします。
     * @param cmn100UsiMblUseKbn cmn100UsiMblUseKbn__
     */
    public void setCmn100UsiMblUseKbn(int cmn100UsiMblUseKbn) {
        cmn100UsiMblUseKbn__ = cmn100UsiMblUseKbn;
    }

    /**
     * <p>appendInfoList を取得します。
     * @return appendInfoList
     */
    public ArrayList<Cmn100AppendInfoModel> getAppendInfoList() {
        return appendInfoList__;
    }

    /**
     * <p>appendInfoList をセットします。
     * @param appendInfoList appendInfoList
     */
    public void setAppendInfoList(ArrayList<Cmn100AppendInfoModel> appendInfoList) {
        appendInfoList__ = appendInfoList;
    }

    /**
     * <p>cmn100usrJkbn を取得します。
     * @return cmn100usrJkbn
     */
    public String getCmn100usrJkbn() {
        return cmn100usrJkbn__;
    }

    /**
     * <p>cmn100usrJkbn をセットします。
     * @param cmn100usrJkbn cmn100usrJkbn
     */
    public void setCmn100usrJkbn(String cmn100usrJkbn) {
        cmn100usrJkbn__ = cmn100usrJkbn;
    }

    /**
     * @return formUnload__ を戻します。
     */
    public boolean isFormUnload() {
        return formUnload__;
    }

    /**
     * @param formUnload 設定する formUnload__。
     */
    public void setFormUnload(boolean formUnload) {
        formUnload__ = formUnload;
    }

    /**
     * @return cmn100TdfkName__ を戻します。
     */
    public String getCmn100TdfkName() {
        return cmn100TdfkName__;
    }

    /**
     * @param cmn100TdfkName 設定する cmn100TdfkName__。
     */
    public void setCmn100TdfkName(String cmn100TdfkName) {
        cmn100TdfkName__ = cmn100TdfkName;
    }

    /**
     * @return cmn100usiAddr1__ を戻します。
     */
    public String getCmn100usiAddr1() {
        return cmn100usiAddr1__;
    }

    /**
     * @param cmn100usiAddr1 設定する cmn100usiAddr1__。
     */
    public void setCmn100usiAddr1(String cmn100usiAddr1) {
        cmn100usiAddr1__ = cmn100usiAddr1;
    }

    /**
     * @return cmn100usiAddr1Kf__ を戻します。
     */
    public int getCmn100usiAddr1Kf() {
        return cmn100usiAddr1Kf__;
    }

    /**
     * @param cmn100usiAddr1Kf 設定する cmn100usiAddr1Kf__。
     */
    public void setCmn100usiAddr1Kf(int cmn100usiAddr1Kf) {
        cmn100usiAddr1Kf__ = cmn100usiAddr1Kf;
    }

    /**
     * @return cmn100usiAddr2__ を戻します。
     */
    public String getCmn100usiAddr2() {
        return cmn100usiAddr2__;
    }

    /**
     * @param cmn100usiAddr2 設定する cmn100usiAddr2__。
     */
    public void setCmn100usiAddr2(String cmn100usiAddr2) {
        cmn100usiAddr2__ = cmn100usiAddr2;
    }

    /**
     * @return cmn100usiAddr2Kf__ を戻します。
     */
    public int getCmn100usiAddr2Kf() {
        return cmn100usiAddr2Kf__;
    }

    /**
     * @param cmn100usiAddr2Kf 設定する cmn100usiAddr2Kf__。
     */
    public void setCmn100usiAddr2Kf(int cmn100usiAddr2Kf) {
        cmn100usiAddr2Kf__ = cmn100usiAddr2Kf;
    }

    /**
     * @return cmn100usiBdate__ を戻します。
     */
    public String getCmn100usiBdate() {
        return cmn100usiBdate__;
    }

    /**
     * @param cmn100usiBdate 設定する cmn100usiBdate__。
     */
    public void setCmn100usiBdate(String cmn100usiBdate) {
        cmn100usiBdate__ = cmn100usiBdate;
    }

    /**
     * @return cmn100usiBdateKf__ を戻します。
     */
    public int getCmn100usiBdateKf() {
        return cmn100usiBdateKf__;
    }

    /**
     * @param cmn100usiBdateKf 設定する cmn100usiBdateKf__。
     */
    public void setCmn100usiBdateKf(int cmn100usiBdateKf) {
        cmn100usiBdateKf__ = cmn100usiBdateKf;
    }

    /**
     * @return cmn100usiFax1__ を戻します。
     */
    public String getCmn100usiFax1() {
        return cmn100usiFax1__;
    }

    /**
     * @param cmn100usiFax1 設定する cmn100usiFax1__。
     */
    public void setCmn100usiFax1(String cmn100usiFax1) {
        cmn100usiFax1__ = cmn100usiFax1;
    }

    /**
     * @return cmn100usiFax1Kf__ を戻します。
     */
    public int getCmn100usiFax1Kf() {
        return cmn100usiFax1Kf__;
    }

    /**
     * @param cmn100usiFax1Kf 設定する cmn100usiFax1Kf__。
     */
    public void setCmn100usiFax1Kf(int cmn100usiFax1Kf) {
        cmn100usiFax1Kf__ = cmn100usiFax1Kf;
    }

    /**
     * @return cmn100usiFax2__ を戻します。
     */
    public String getCmn100usiFax2() {
        return cmn100usiFax2__;
    }

    /**
     * @param cmn100usiFax2 設定する cmn100usiFax2__。
     */
    public void setCmn100usiFax2(String cmn100usiFax2) {
        cmn100usiFax2__ = cmn100usiFax2;
    }

    /**
     * @return cmn100usiFax2Kf__ を戻します。
     */
    public int getCmn100usiFax2Kf() {
        return cmn100usiFax2Kf__;
    }

    /**
     * @param cmn100usiFax2Kf 設定する cmn100usiFax2Kf__。
     */
    public void setCmn100usiFax2Kf(int cmn100usiFax2Kf) {
        cmn100usiFax2Kf__ = cmn100usiFax2Kf;
    }

    /**
     * @return cmn100usiFax3__ を戻します。
     */
    public String getCmn100usiFax3() {
        return cmn100usiFax3__;
    }

    /**
     * @param cmn100usiFax3 設定する cmn100usiFax3__。
     */
    public void setCmn100usiFax3(String cmn100usiFax3) {
        cmn100usiFax3__ = cmn100usiFax3;
    }

    /**
     * @return cmn100usiFax3Kf__ を戻します。
     */
    public int getCmn100usiFax3Kf() {
        return cmn100usiFax3Kf__;
    }

    /**
     * @param cmn100usiFax3Kf 設定する cmn100usiFax3Kf__。
     */
    public void setCmn100usiFax3Kf(int cmn100usiFax3Kf) {
        cmn100usiFax3Kf__ = cmn100usiFax3Kf;
    }

    /**
     * @return cmn100usiMail1__ を戻します。
     */
    public String getCmn100usiMail1() {
        return cmn100usiMail1__;
    }

    /**
     * @param cmn100usiMail1 設定する cmn100usiMail1__。
     */
    public void setCmn100usiMail1(String cmn100usiMail1) {
        cmn100usiMail1__ = cmn100usiMail1;
    }

    /**
     * @return cmn100usiMail1Kf__ を戻します。
     */
    public int getCmn100usiMail1Kf() {
        return cmn100usiMail1Kf__;
    }

    /**
     * @param cmn100usiMail1Kf 設定する cmn100usiMail1Kf__。
     */
    public void setCmn100usiMail1Kf(int cmn100usiMail1Kf) {
        cmn100usiMail1Kf__ = cmn100usiMail1Kf;
    }

    /**
     * @return cmn100usiMail2__ を戻します。
     */
    public String getCmn100usiMail2() {
        return cmn100usiMail2__;
    }

    /**
     * @param cmn100usiMail2 設定する cmn100usiMail2__。
     */
    public void setCmn100usiMail2(String cmn100usiMail2) {
        cmn100usiMail2__ = cmn100usiMail2;
    }

    /**
     * @return cmn100usiMail2Kf__ を戻します。
     */
    public int getCmn100usiMail2Kf() {
        return cmn100usiMail2Kf__;
    }

    /**
     * @param cmn100usiMail2Kf 設定する cmn100usiMail2Kf__。
     */
    public void setCmn100usiMail2Kf(int cmn100usiMail2Kf) {
        cmn100usiMail2Kf__ = cmn100usiMail2Kf;
    }

    /**
     * @return cmn100usiMail3__ を戻します。
     */
    public String getCmn100usiMail3() {
        return cmn100usiMail3__;
    }

    /**
     * @param cmn100usiMail3 設定する cmn100usiMail3__。
     */
    public void setCmn100usiMail3(String cmn100usiMail3) {
        cmn100usiMail3__ = cmn100usiMail3;
    }

    /**
     * @return cmn100usiMail3Kf__ を戻します。
     */
    public int getCmn100usiMail3Kf() {
        return cmn100usiMail3Kf__;
    }

    /**
     * @param cmn100usiMail3Kf 設定する cmn100usiMail3Kf__。
     */
    public void setCmn100usiMail3Kf(int cmn100usiMail3Kf) {
        cmn100usiMail3Kf__ = cmn100usiMail3Kf;
    }

    /**
     * @return cmn100usiPictKf__ を戻します。
     */
    public int getCmn100usiPictKf() {
        return cmn100usiPictKf__;
    }

    /**
     * @param cmn100usiPictKf 設定する cmn100usiPictKf__。
     */
    public void setCmn100usiPictKf(int cmn100usiPictKf) {
        cmn100usiPictKf__ = cmn100usiPictKf;
    }

    /**
     * @return cmn100usiSyozoku__ を戻します。
     */
    public String getCmn100usiSyozoku() {
        return cmn100usiSyozoku__;
    }

    /**
     * @param cmn100usiSyozoku 設定する cmn100usiSyozoku__。
     */
    public void setCmn100usiSyozoku(String cmn100usiSyozoku) {
        cmn100usiSyozoku__ = cmn100usiSyozoku;
    }

    /**
     * @return cmn100usiTdfkKf__ を戻します。
     */
    public int getCmn100usiTdfkKf() {
        return cmn100usiTdfkKf__;
    }

    /**
     * @param cmn100usiTdfkKf 設定する cmn100usiTdfkKf__。
     */
    public void setCmn100usiTdfkKf(int cmn100usiTdfkKf) {
        cmn100usiTdfkKf__ = cmn100usiTdfkKf;
    }

    /**
     * @return cmn100usiTel1__ を戻します。
     */
    public String getCmn100usiTel1() {
        return cmn100usiTel1__;
    }

    /**
     * @param cmn100usiTel1 設定する cmn100usiTel1__。
     */
    public void setCmn100usiTel1(String cmn100usiTel1) {
        cmn100usiTel1__ = cmn100usiTel1;
    }

    /**
     * @return cmn100usiTel1Kf__ を戻します。
     */
    public int getCmn100usiTel1Kf() {
        return cmn100usiTel1Kf__;
    }

    /**
     * @param cmn100usiTel1Kf 設定する cmn100usiTel1Kf__。
     */
    public void setCmn100usiTel1Kf(int cmn100usiTel1Kf) {
        cmn100usiTel1Kf__ = cmn100usiTel1Kf;
    }

    /**
     * @return cmn100usiTel2__ を戻します。
     */
    public String getCmn100usiTel2() {
        return cmn100usiTel2__;
    }

    /**
     * @param cmn100usiTel2 設定する cmn100usiTel2__。
     */
    public void setCmn100usiTel2(String cmn100usiTel2) {
        cmn100usiTel2__ = cmn100usiTel2;
    }

    /**
     * @return cmn100usiTel2Kf__ を戻します。
     */
    public int getCmn100usiTel2Kf() {
        return cmn100usiTel2Kf__;
    }

    /**
     * @param cmn100usiTel2Kf 設定する cmn100usiTel2Kf__。
     */
    public void setCmn100usiTel2Kf(int cmn100usiTel2Kf) {
        cmn100usiTel2Kf__ = cmn100usiTel2Kf;
    }

    /**
     * @return cmn100usiTel3__ を戻します。
     */
    public String getCmn100usiTel3() {
        return cmn100usiTel3__;
    }

    /**
     * @param cmn100usiTel3 設定する cmn100usiTel3__。
     */
    public void setCmn100usiTel3(String cmn100usiTel3) {
        cmn100usiTel3__ = cmn100usiTel3;
    }

    /**
     * @return cmn100usiTel3Kf__ を戻します。
     */
    public int getCmn100usiTel3Kf() {
        return cmn100usiTel3Kf__;
    }

    /**
     * @param cmn100usiTel3Kf 設定する cmn100usiTel3Kf__。
     */
    public void setCmn100usiTel3Kf(int cmn100usiTel3Kf) {
        cmn100usiTel3Kf__ = cmn100usiTel3Kf;
    }

    /**
     * @return cmn100usiZip__ を戻します。
     */
    public String getCmn100usiZip() {
        return cmn100usiZip__;
    }

    /**
     * @param cmn100usiZip 設定する cmn100usiZip__。
     */
    public void setCmn100usiZip(String cmn100usiZip) {
        cmn100usiZip__ = cmn100usiZip;
    }

    /**
     * @return cmn100usiZipKf__ を戻します。
     */
    public int getCmn100usiZipKf() {
        return cmn100usiZipKf__;
    }

    /**
     * @param cmn100usiZipKf 設定する cmn100usiZipKf__。
     */
    public void setCmn100usiZipKf(int cmn100usiZipKf) {
        cmn100usiZipKf__ = cmn100usiZipKf;
    }

    /**
     * @return cmn100grpNmlist を戻します。
     */
    public ArrayList<Cmn100GroupModel> getCmn100grpNmlist() {
        return cmn100grpNmlist__;
    }

    /**
     * @param cmn100grpNmlist 設定する cmn100grpNmlist。
     */
    public void setCmn100grpNmlist(ArrayList<Cmn100GroupModel> cmn100grpNmlist) {
        cmn100grpNmlist__ = cmn100grpNmlist;
    }

    /**
     * @return cmn100usiBiko を戻します。
     */
    public String getCmn100usiBiko() {
        return cmn100usiBiko__;
    }

    /**
     * @param cmn100usiBiko 設定する cmn100usiBiko。
     */
    public void setCmn100usiBiko(String cmn100usiBiko) {
        cmn100usiBiko__ = cmn100usiBiko;
    }

    /**
     * @return cmn100usiMei を戻します。
     */
    public String getCmn100usiMei() {
        return cmn100usiMei__;
    }

    /**
     * @param cmn100usiMei 設定する cmn100usiMei。
     */
    public void setCmn100usiMei(String cmn100usiMei) {
        cmn100usiMei__ = cmn100usiMei;
    }

    /**
     * @return cmn100usiMeiKn を戻します。
     */
    public String getCmn100usiMeiKn() {
        return cmn100usiMeiKn__;
    }

    /**
     * @param cmn100usiMeiKn 設定する cmn100usiMeiKn。
     */
    public void setCmn100usiMeiKn(String cmn100usiMeiKn) {
        cmn100usiMeiKn__ = cmn100usiMeiKn;
    }

    /**
     * @return cmn100usiSei を戻します。
     */
    public String getCmn100usiSei() {
        return cmn100usiSei__;
    }

    /**
     * @param cmn100usiSei 設定する cmn100usiSei。
     */
    public void setCmn100usiSei(String cmn100usiSei) {
        cmn100usiSei__ = cmn100usiSei;
    }

    /**
     * @return cmn100usiSeiKn を戻します。
     */
    public String getCmn100usiSeiKn() {
        return cmn100usiSeiKn__;
    }

    /**
     * @param cmn100usiSeiKn 設定する cmn100usiSeiKn。
     */
    public void setCmn100usiSeiKn(String cmn100usiSeiKn) {
        cmn100usiSeiKn__ = cmn100usiSeiKn;
    }

    /**
     * @return cmn100usiSyainNo を戻します。
     */
    public String getCmn100usiSyainNo() {
        return cmn100usiSyainNo__;
    }

    /**
     * @param cmn100usiSyainNo 設定する cmn100usiSyainNo。
     */
    public void setCmn100usiSyainNo(String cmn100usiSyainNo) {
        cmn100usiSyainNo__ = cmn100usiSyainNo;
    }

    /**
     * @return cmn100usiYakusyoku を戻します。
     */
    public String getCmn100usiYakusyoku() {
        return cmn100usiYakusyoku__;
    }

    /**
     * @param cmn100usiYakusyoku 設定する cmn100usiYakusyoku。
     */
    public void setCmn100usiYakusyoku(String cmn100usiYakusyoku) {
        cmn100usiYakusyoku__ = cmn100usiYakusyoku;
    }

    /**
     * @return cmn100usid を戻します。
     */
    public String getCmn100usid() {
        return cmn100usid__;
    }

    /**
     * @param cmn100usid 設定する cmn100usid。
     */
    public void setCmn100usid(String cmn100usid) {
        cmn100usid__ = cmn100usid;
    }

    /**
     * @return cmn100pluginId__ を戻します。
     */
    public String getCmn100pluginId() {
        return cmn100pluginId__;
    }

    /**
     * <p>cmn100usiFaxCmt1 を取得します。
     * @return cmn100usiFaxCmt1
     */
    public String getCmn100usiFaxCmt1() {
        return cmn100usiFaxCmt1__;
    }

    /**
     * <p>cmn100usiFaxCmt1 をセットします。
     * @param cmn100usiFaxCmt1 cmn100usiFaxCmt1
     */
    public void setCmn100usiFaxCmt1(String cmn100usiFaxCmt1) {
        cmn100usiFaxCmt1__ = cmn100usiFaxCmt1;
    }

    /**
     * <p>cmn100usiFaxCmt2 を取得します。
     * @return cmn100usiFaxCmt2
     */
    public String getCmn100usiFaxCmt2() {
        return cmn100usiFaxCmt2__;
    }

    /**
     * <p>cmn100usiFaxCmt2 をセットします。
     * @param cmn100usiFaxCmt2 cmn100usiFaxCmt2
     */
    public void setCmn100usiFaxCmt2(String cmn100usiFaxCmt2) {
        cmn100usiFaxCmt2__ = cmn100usiFaxCmt2;
    }

    /**
     * <p>cmn100usiFaxCmt3 を取得します。
     * @return cmn100usiFaxCmt3
     */
    public String getCmn100usiFaxCmt3() {
        return cmn100usiFaxCmt3__;
    }

    /**
     * <p>cmn100usiFaxCmt3 をセットします。
     * @param cmn100usiFaxCmt3 cmn100usiFaxCmt3
     */
    public void setCmn100usiFaxCmt3(String cmn100usiFaxCmt3) {
        cmn100usiFaxCmt3__ = cmn100usiFaxCmt3;
    }

    /**
     * <p>cmn100usiMailCmt1 を取得します。
     * @return cmn100usiMailCmt1
     */
    public String getCmn100usiMailCmt1() {
        return cmn100usiMailCmt1__;
    }

    /**
     * <p>cmn100usiMailCmt1 をセットします。
     * @param cmn100usiMailCmt1 cmn100usiMailCmt1
     */
    public void setCmn100usiMailCmt1(String cmn100usiMailCmt1) {
        cmn100usiMailCmt1__ = cmn100usiMailCmt1;
    }

    /**
     * <p>cmn100usiMailCmt2 を取得します。
     * @return cmn100usiMailCmt2
     */
    public String getCmn100usiMailCmt2() {
        return cmn100usiMailCmt2__;
    }

    /**
     * <p>cmn100usiMailCmt2 をセットします。
     * @param cmn100usiMailCmt2 cmn100usiMailCmt2
     */
    public void setCmn100usiMailCmt2(String cmn100usiMailCmt2) {
        cmn100usiMailCmt2__ = cmn100usiMailCmt2;
    }

    /**
     * <p>cmn100usiMailCmt3 を取得します。
     * @return cmn100usiMailCmt3
     */
    public String getCmn100usiMailCmt3() {
        return cmn100usiMailCmt3__;
    }

    /**
     * <p>cmn100usiMailCmt3 をセットします。
     * @param cmn100usiMailCmt3 cmn100usiMailCmt3
     */
    public void setCmn100usiMailCmt3(String cmn100usiMailCmt3) {
        cmn100usiMailCmt3__ = cmn100usiMailCmt3;
    }

    /**
     * <p>cmn100usiTelCmt1 を取得します。
     * @return cmn100usiTelCmt1
     */
    public String getCmn100usiTelCmt1() {
        return cmn100usiTelCmt1__;
    }

    /**
     * <p>cmn100usiTelCmt1 をセットします。
     * @param cmn100usiTelCmt1 cmn100usiTelCmt1
     */
    public void setCmn100usiTelCmt1(String cmn100usiTelCmt1) {
        cmn100usiTelCmt1__ = cmn100usiTelCmt1;
    }

    /**
     * <p>cmn100usiTelCmt2 を取得します。
     * @return cmn100usiTelCmt2
     */
    public String getCmn100usiTelCmt2() {
        return cmn100usiTelCmt2__;
    }

    /**
     * <p>cmn100usiTelCmt2 をセットします。
     * @param cmn100usiTelCmt2 cmn100usiTelCmt2
     */
    public void setCmn100usiTelCmt2(String cmn100usiTelCmt2) {
        cmn100usiTelCmt2__ = cmn100usiTelCmt2;
    }

    /**
     * <p>cmn100usiTelCmt3 を取得します。
     * @return cmn100usiTelCmt3
     */
    public String getCmn100usiTelCmt3() {
        return cmn100usiTelCmt3__;
    }

    /**
     * <p>cmn100usiTelCmt3 をセットします。
     * @param cmn100usiTelCmt3 cmn100usiTelCmt3
     */
    public void setCmn100usiTelCmt3(String cmn100usiTelCmt3) {
        cmn100usiTelCmt3__ = cmn100usiTelCmt3;
    }

    /**
     * <p>cmn100usiTelNai1 を取得します。
     * @return cmn100usiTelNai1
     */
    public String getCmn100usiTelNai1() {
        return cmn100usiTelNai1__;
    }

    /**
     * <p>cmn100usiTelNai1 をセットします。
     * @param cmn100usiTelNai1 cmn100usiTelNai1
     */
    public void setCmn100usiTelNai1(String cmn100usiTelNai1) {
        cmn100usiTelNai1__ = cmn100usiTelNai1;
    }

    /**
     * <p>cmn100usiTelNai2 を取得します。
     * @return cmn100usiTelNai2
     */
    public String getCmn100usiTelNai2() {
        return cmn100usiTelNai2__;
    }

    /**
     * <p>cmn100usiTelNai2 をセットします。
     * @param cmn100usiTelNai2 cmn100usiTelNai2
     */
    public void setCmn100usiTelNai2(String cmn100usiTelNai2) {
        cmn100usiTelNai2__ = cmn100usiTelNai2;
    }

    /**
     * <p>cmn100usiTelNai3 を取得します。
     * @return cmn100usiTelNai3
     */
    public String getCmn100usiTelNai3() {
        return cmn100usiTelNai3__;
    }

    /**
     * <p>cmn100usiTelNai3 をセットします。
     * @param cmn100usiTelNai3 cmn100usiTelNai3
     */
    public void setCmn100usiTelNai3(String cmn100usiTelNai3) {
        cmn100usiTelNai3__ = cmn100usiTelNai3;
    }

    /**
     * <p>cmn100usiAge を取得します。
     * @return cmn100usiAge
     */
    public int getCmn100usiAge() {
        return cmn100usiAge__;
    }

    /**
     * <p>cmn100usiAge をセットします。
     * @param cmn100usiAge cmn100usiAge
     */
    public void setCmn100usiAge(int cmn100usiAge) {
        cmn100usiAge__ = cmn100usiAge;
    }

    /**
     * <p>cmn100searchUse を取得します。
     * @return cmn100searchUse
     */
    public int getCmn100searchUse() {
        return cmn100searchUse__;
    }

    /**
     * <p>cmn100searchUse をセットします。
     * @param cmn100searchUse cmn100searchUse
     */
    public void setCmn100searchUse(int cmn100searchUse) {
        cmn100searchUse__ = cmn100searchUse;
    }

    /**
     * <p>cmn100binSid を取得します。
     * @return cmn100binSid
     */
    public Long getCmn100binSid() {
        return cmn100binSid__;
    }

    /**
     * <p>cmn100binSid をセットします。
     * @param cmn100binSid cmn100binSid
     */
    public void setCmn100binSid(Long cmn100binSid) {
        cmn100binSid__ = cmn100binSid;
    }

    /**
     * <p>labelList を取得します。
     * @return labelList
     */
    public ArrayList<CmnLabelUsrModel> getLabelList() {
        return labelList__;
    }

    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(ArrayList<CmnLabelUsrModel> labelList) {
        labelList__ = labelList;
    }

    /**
     * <p>cmn100WebSearchWord を取得します。
     * @return cmn100WebSearchWord
     */
    public String getCmn100WebSearchWord() {
        return cmn100WebSearchWord__;
    }

    /**
     * <p>cmn100WebSearchWord をセットします。
     * @param cmn100WebSearchWord cmn100WebSearchWord
     */
    public void setCmn100WebSearchWord(String cmn100WebSearchWord) {
        cmn100WebSearchWord__ = cmn100WebSearchWord;
    }

    /**
     * <p>cmn100usiSeibetu を取得します。
     * @return cmn100usiSeibetu
     */
    public String getCmn100usiSeibetu() {
        return cmn100usiSeibetu__;
    }

    /**
     * <p>cmn100usiSeibetu をセットします。
     * @param cmn100usiSeibetu cmn100usiSeibetu
     */
    public void setCmn100usiSeibetu(String cmn100usiSeibetu) {
        cmn100usiSeibetu__ = cmn100usiSeibetu;
    }

    /**
     * <p>cmn100entranceDate を取得します。
     * @return cmn100entranceDate
     */
    public String getCmn100entranceDate() {
        return cmn100entranceDate__;
    }

    /**
     * <p>cmn100entranceDate をセットします。
     * @param cmn100entranceDate cmn100entranceDate
     */
    public void setCmn100entranceDate(String cmn100entranceDate) {
        cmn100entranceDate__ = cmn100entranceDate;
    }

    /**
     * <p>cmn100QrDispKbn を取得します。
     * @return cmn100QrDispKbn
     */
    public int getCmn100QrDispKbn() {
        return cmn100QrDispKbn;
    }

    /**
     * <p>cmn100QrDispKbn をセットします。
     * @param cmn100QrDispKbn cmn100QrDispKbn
     */
    public void setCmn100QrDispKbn(int cmn100QrDispKbn) {
        this.cmn100QrDispKbn = cmn100QrDispKbn;
    }

    /**
     * <p>cmn100usrUkoFlg を取得します。
     * @return cmn100usrUkoFlg
     */
    public int getCmn100usrUkoFlg() {
        return cmn100usrUkoFlg__;
    }

    /**
     * <p>cmn100usrUkoFlg をセットします。
     * @param cmn100usrUkoFlg cmn100usrUkoFlg
     */
    public void setCmn100usrUkoFlg(int cmn100usrUkoFlg) {
        cmn100usrUkoFlg__ = cmn100usrUkoFlg;
    }

}