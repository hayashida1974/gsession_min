package jp.groupsession.v2.usr.usr031;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.model.ValidatePasswordModel;
import jp.groupsession.v2.usr.usr030.Usr030Form;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー（追加）画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr031Form extends Usr030Form {

    /** パスワードの編集の判定に使用します */
    public static final String CONST_PASS_STRING = "-z8po-3slr._efgebql9";
    /** 初期表示フラグ */
    private int usr031initFlg__ = 0;
    /** プラグインID */
    private String usr031pluginId__ = GSConstUser.PLUGIN_ID_USER;

    /** ユーザＩＤ(ログインＩＤ) */
    private String usr031userid__ = null;
    /** パスワード */
    private String usr031password__ = null;
    /** パスワード(確認用) */
    private String usr031passwordkn__ = null;
    /** ユーザ名 姓 */
    private String usr031sei__ = null;
    /** ユーザ名 名 */
    private String usr031mei__ = null;
    /** ユーザ名 姓カナ */
    private String usr031seikn__ = null;
    /** ユーザ名 名カナ */
    private String usr031meikn__ = null;
    /** 社員/職員番号 */
    private String usr031shainno__ = null;
    /** 役職 */
    private String usr031yakushoku__ = null;
    /** 備考 */
    private String usr031bikou__ = null;
    /** ソートキー1 */
    private String usr031sortkey1__ = null;
    /** ソートキー2 */
    private String usr031sortkey2__ = null;
    /** 生年月日 年*/
    private String usr031birthYear__ = null;
    /** 生年月日 月*/
    private String usr031birthMonth__ = null;
    /** 生年月日 日*/
    private String usr031birthDay__ = null;
    /** 入社年月日 年*/
    private String usr031entranceYear__ = null;
    /** 入社月日 月*/
    private String usr031entranceMonth__ = null;
    /** 入社月日 日*/
    private String usr031entranceDay__ = null;
    /** 性別 */
    private String usr031seibetu__ = String.valueOf(GSConstUser.SEIBETU_UNSET);
    /** メール１*/
    private String usr031mail1__ = null;
    /** メール１ コメント*/
    private String usr031mailCmt1__ = null;
    /** メール２*/
    private String usr031mail2__ = null;
    /** メール２ コメント*/
    private String usr031mailCmt2__ = null;
    /** メール３*/
    private String usr031mail3__ = null;
    /** メール３ コメント*/
    private String usr031mailCmt3__ = null;
    /** 電話番号１*/
    private String usr031tel1__ = null;
    /** 電話番号１ 内線*/
    private String usr031telNai1__ = null;
    /** 電話番号１ コメント*/
    private String usr031telCmt1__ = null;
    /** 電話番号２*/
    private String usr031tel2__ = null;
    /** 電話番号２ 内線*/
    private String usr031telNai2__ = null;
    /** 電話番号２ コメント*/
    private String usr031telCmt2__ = null;
    /** 電話番号３*/
    private String usr031tel3__ = null;
    /** 電話番号３ 内線*/
    private String usr031telNai3__ = null;
    /** 電話番号３ コメント*/
    private String usr031telCmt3__ = null;
    /** ＦＡＸ１*/
    private String usr031fax1__ = null;
    /** ＦＡＸ１ コメント*/
    private String usr031faxCmt1__ = null;
    /** ＦＡＸ２*/
    private String usr031fax2__ = null;
    /** ＦＡＸ２ コメント*/
    private String usr031faxCmt2__ = null;
    /** ＦＡＸ３*/
    private String usr031fax3__ = null;
    /** ＦＡＸ３ コメント*/
    private String usr031faxCmt3__ = null;
    /** 郵便番号上３桁*/
    private String usr031post1__ = null;
    /** 郵便番号下４桁*/
    private String usr031post2__ = null;
    /** 都道府県コード*/
    private String usr031tdfkCd__ = null;
    /** 住所１*/
    private String usr031address1__ = null;
    /** 住所２*/
    private String usr031address2__ = null;
    /** 所属*/
    private String usr031syozoku__ = null;
    /** 選択グループ(CSVが格納される) */
    private String selectgroup__ = null;
    /** デフォルトグループ */
    private int usr031defgroup__ = -1;
    /** パスワードダミー(******)表示用 */
    private String passworddamy__ = null;
    /** バイナリSID */
    private Long usr031BinSid__ = new Long(0);
    /** 写真公開フラグ */
    private int usr031UsiPicgKf__;
    /** 誕生日公開フラグ */
    private int usr031UsiBdateKf__;
    /** メールアドレス1公開フラグ */
    private int usr031UsiMail1Kf__;
    /** メールアドレス2公開フラグ */
    private int usr031UsiMail2Kf__;
    /** メールアドレス3公開フラグ */
    private int usr031UsiMail3Kf__;
    /** 郵便番号公開フラグ */
    private int usr031UsiZipKf__;
    /** 都道府県公開フラグ */
    private int usr031UsiTdfKf__;
    /** 住所1公開フラグ */
    private int usr031UsiAddr1Kf__;
    /** 住所2公開フラグ */
    private int usr031UsiAddr2Kf__;
    /** 電話番号1公開フラグ */
    private int usr031UsiTel1Kf__;
    /** 電話番号2公開フラグ */
    private int usr031UsiTel2Kf__;
    /** 電話番号3公開フラグ */
    private int usr031UsiTel3Kf__;
    /** FAX1公開フラグ */
    private int usr031UsiFax1Kf__;
    /** FAX2公開フラグ */
    private int usr031UsiFax2Kf__;
    /** FAX3公開フラグ */
    private int usr031UsiFax3Kf__;
    /** 写真リスト */
    private String usr031PictFileList__;
    /** 写真ファイル名 */
    private String usr031ImageName__;
    /** 写真ファイル保存名 */
    private String usr031ImageSaveName__;
    /** 管理者権限フラグ(true=管理者 false=一般ユーザ) */
    private boolean adminFlg__;
    /** モバイルプラグイン使用可否フラグ 可:0・不可:1*/
    private int usr031UsiMblUse__;
    /** モバイル使用 */
    private int usr031UsiMblUseKbn__;
    /**ワンタイムパスワード通知先メールアドレス*/
    private String usr031OtpToAddress__;


    //表示項目
    /** 都道府県ラベル */
    private ArrayList<LabelValueBean> tdfkLabelList__ = null;
    /** 役職ラベル */
    private ArrayList<LabelValueBean> posLabelList__ = null;
    /** 性別ラベル */
    private ArrayList<LabelValueBean> seibetuLabelList__ = null;
    /** グループツリー */
    ArrayList<GroupModel> groupList__ = null;

    /** 固体識別番号制御 */
    private String usr031NumCont__ = String.valueOf(GSConstUser.UID_DOESNT_CONTROL);
    /** 固体識別番号自動登録 */
    private String usr031NumAutAdd__ = String.valueOf(GSConstUser.UID_AUTO_REG_NO);
    /** 固体識別番号1*/
    private String usr031CmuUid1__ = "";
    /** 固体識別番号2*/
    private String usr031CmuUid2__ = "";
    /** 固体識別番号3*/
    private String usr031CmuUid3__ = "";
    /** 初回ログイン時、パスワード変更区分 */
    private String usr031PswdKbn__ = String.valueOf(GSConstUser.PSWD_UPDATE_OFF);
    /** 英数混在区分 */
    private int usr031CoeKbn__ = GSConstMain.PWC_COEKBN_OFF;
    /** 大文字小文字混在区分 */
    private int usr031UppercaseKbn__ = GSConstMain.PWC_UPPERCASE_OFF;
    /** パスワード桁数 */
    private int usr031Digit__ = GSConstMain.DEFAULT_DIGIT;
    /** ユーザID同一パスワード設定区分 */
    private int usr031UidPswdKbn__ = GSConstMain.PWC_UIDPSWDKBN_OFF;

    /** ラベル */
    private String[] usrLabel__ = null;
    /** 選択ラベル */
    private ArrayList<CmnLabelUsrModel> selectLabelList__ = null;
    /** 削除ラベル */
    private String delLabel__ = null;

    /** ラベル付与権限 */
    private String labelSetPow__ = null;


    /** パスワード変更の有効・無効 */
    public int changePassword__ = GSConst.CHANGEPASSWORD_PARMIT;

    /** ユーザの有効・無効 */
    private int usr031UsrUkoFlg__ = 0;

    /** パスワード変更の有効・無効 */
    public int changeOtpToAddress__ = GSConstMain.OTP_NOUSE;

    /** ユーザ数上限警告表示 */
    public int usr031warnUserLimit__ = 0;


    /**
     * <p>usr031NumCont を取得します。
     * @return usr031NumCont
     */
    public String getUsr031NumCont() {
        return usr031NumCont__;
    }
    /**
     * <p>usr031NumCont をセットします。
     * @param usr031NumCont usr031NumCont
     */
    public void setUsr031NumCont(String usr031NumCont) {
        usr031NumCont__ = usr031NumCont;
    }
    /**
     * <p>usr031NumAutAdd を取得します。
     * @return usr031NumAutAdd
     */
    public String getUsr031NumAutAdd() {
        return usr031NumAutAdd__;
    }
    /**
     * <p>usr031NumAutAdd をセットします。
     * @param usr031NumAutAdd usr031NumAutAdd
     */
    public void setUsr031NumAutAdd(String usr031NumAutAdd) {
        usr031NumAutAdd__ = usr031NumAutAdd;
    }
    /**
     * <p>usr031CmuUid1 を取得します。
     * @return usr031CmuUid1
     */
    public String getUsr031CmuUid1() {
        return usr031CmuUid1__;
    }
    /**
     * <p>usr031CmuUid1 をセットします。
     * @param usr031CmuUid1 usr031CmuUid1
     */
    public void setUsr031CmuUid1(String usr031CmuUid1) {
        usr031CmuUid1__ = usr031CmuUid1;
    }
    /**
     * <p>usr031CmuUid2 を取得します。
     * @return usr031CmuUid2
     */
    public String getUsr031CmuUid2() {
        return usr031CmuUid2__;
    }
    /**
     * <p>usr031CmuUid2 をセットします。
     * @param usr031CmuUid2 usr031CmuUid2
     */
    public void setUsr031CmuUid2(String usr031CmuUid2) {
        usr031CmuUid2__ = usr031CmuUid2;
    }
    /**
     * <p>usr031CmuUid3 を取得します。
     * @return usr031CmuUid3
     */
    public String getUsr031CmuUid3() {
        return usr031CmuUid3__;
    }
    /**
     * <p>usr031CmuUid3 をセットします。
     * @param usr031CmuUid3 usr031CmuUid3
     */
    public void setUsr031CmuUid3(String usr031CmuUid3) {
        usr031CmuUid3__ = usr031CmuUid3;
    }
    /**
     * @return usr031UsiMblUseKbn__ を戻します。
     */
    public int getUsr031UsiMblUseKbn() {
        return usr031UsiMblUseKbn__;
    }
    /**
     * @param usr031UsiMblUseKbn 設定する usr031UsiMblUseKbn__
     */
    public void setUsr031UsiMblUseKbn(int usr031UsiMblUseKbn) {
        usr031UsiMblUseKbn__ = usr031UsiMblUseKbn;
    }
    /**
     * @return usr031UsiMblUse__ を戻します。
     */
    public int getUsr031UsiMblUse() {
        return usr031UsiMblUse__;
    }
    /**
     * @param usr031UsiMblUse 設定する usr031UsiMblUse__
     */
    public void setUsr031UsiMblUse(int usr031UsiMblUse) {
        usr031UsiMblUse__ = usr031UsiMblUse;
    }

    /**
     * @return adminFlg__ を戻します。
     */
    public boolean isAdminFlg() {
        return adminFlg__;
    }

    /**
     * @param adminFlg 設定する adminFlg__。
     */
    public void setAdminFlg(boolean adminFlg) {
        adminFlg__ = adminFlg;
    }

    /**
     * @return usr031ImageName__ を戻します。
     */
    public String getUsr031ImageName() {
        return usr031ImageName__;
    }

    /**
     * @param usr031ImageName 設定する usr031ImageName__。
     */
    public void setUsr031ImageName(String usr031ImageName) {
        usr031ImageName__ = usr031ImageName;
    }

    /**
     * <p>usr031ImageSaveName を取得します。
     * @return usr031ImageSaveName
     */
    public String getUsr031ImageSaveName() {
        return usr031ImageSaveName__;
    }
    /**
     * <p>usr031ImageSaveName をセットします。
     * @param usr031ImageSaveName usr031ImageSaveName
     */
    public void setUsr031ImageSaveName(String usr031ImageSaveName) {
        usr031ImageSaveName__ = usr031ImageSaveName;
    }

    /**
     * @return usr031PictFileList__ を戻します。
     */
    public String getUsr031PictFileList() {
        return usr031PictFileList__;
    }

    /**
     * @param usr031PictFileList 設定する usr031PictFileList__。
     */
    public void setUsr031PictFileList(String usr031PictFileList) {
        usr031PictFileList__ = usr031PictFileList;
    }

    /**
     * @return usr031BinSid__ を戻します。
     */
    public Long getUsr031BinSid() {
        return usr031BinSid__;
    }

    /**
     * @param usr031BinSid 設定する usr031BinSid__
     */
    public void setUsr031BinSid(Long usr031BinSid) {
        usr031BinSid__ = usr031BinSid;
    }

    /**
     * @return usr031UsiAddr1Kf__ を戻します。
     */
    public int getUsr031UsiAddr1Kf() {
        return usr031UsiAddr1Kf__;
    }

    /**
     * @param usr031UsiAddr1Kf 設定する usr031UsiAddr1Kf__
     */
    public void setUsr031UsiAddr1Kf(int usr031UsiAddr1Kf) {
        usr031UsiAddr1Kf__ = usr031UsiAddr1Kf;
    }

    /**
     * @return usr031UsiAddr2Kf__ を戻します。
     */
    public int getUsr031UsiAddr2Kf() {
        return usr031UsiAddr2Kf__;
    }

    /**
     * @param usr031UsiAddr2Kf 設定する usr031UsiAddr2Kf__
     */
    public void setUsr031UsiAddr2Kf(int usr031UsiAddr2Kf) {
        usr031UsiAddr2Kf__ = usr031UsiAddr2Kf;
    }

    /**
     * @return usr031UsiBdateKf__ を戻します。
     */
    public int getUsr031UsiBdateKf() {
        return usr031UsiBdateKf__;
    }

    /**
     * @param usr031UsiBdateKf 設定する usr031UsiBdateKf__
     */
    public void setUsr031UsiBdateKf(int usr031UsiBdateKf) {
        usr031UsiBdateKf__ = usr031UsiBdateKf;
    }

    /**
     * @return usr031UsiFax1Kf_ を戻します。
     */
    public int getUsr031UsiFax1Kf() {
        return usr031UsiFax1Kf__;
    }

    /**
     * @param usr031UsiFax1Kf 設定する usr031UsiFax1Kf__
     */
    public void setUsr031UsiFax1Kf(int usr031UsiFax1Kf) {
        usr031UsiFax1Kf__ = usr031UsiFax1Kf;
    }

    /**
     * @return usr031UsiFax2Kf__ を戻します。
     */
    public int getUsr031UsiFax2Kf() {
        return usr031UsiFax2Kf__;
    }

    /**
     * @param usr031UsiFax2Kf 設定する usr031UsiFax2Kf__
     */
    public void setUsr031UsiFax2Kf(int usr031UsiFax2Kf) {
        usr031UsiFax2Kf__ = usr031UsiFax2Kf;
    }

    /**
     * @return usr031UsiFax3Kf__ を戻します。
     */
    public int getUsr031UsiFax3Kf() {
        return usr031UsiFax3Kf__;
    }

    /**
     * @param usr031UsiFax3Kf 設定する usr031UsiFax3Kf__
     */
    public void setUsr031UsiFax3Kf(int usr031UsiFax3Kf) {
        usr031UsiFax3Kf__ = usr031UsiFax3Kf;
    }

    /**
     * @return usr031UsiMail1Kf__ を戻します。
     */
    public int getUsr031UsiMail1Kf() {
        return usr031UsiMail1Kf__;
    }

    /**
     * @param usr031UsiMail1Kf 設定する usr031UsiMail1Kf__
     */
    public void setUsr031UsiMail1Kf(int usr031UsiMail1Kf) {
        usr031UsiMail1Kf__ = usr031UsiMail1Kf;
    }

    /**
     * @return usr031UsiMail2Kf__ を戻します。
     */
    public int getUsr031UsiMail2Kf() {
        return usr031UsiMail2Kf__;
    }

    /**
     * @param usr031UsiMail2Kf 設定する usr031UsiMail2Kf__
     */
    public void setUsr031UsiMail2Kf(int usr031UsiMail2Kf) {
        usr031UsiMail2Kf__ = usr031UsiMail2Kf;
    }

    /**
     * @return usr031UsiMail3Kf__ を戻します。
     */
    public int getUsr031UsiMail3Kf() {
        return usr031UsiMail3Kf__;
    }

    /**
     * @param usr031UsiMail3Kf 設定する usr031UsiMail3Kf__
     */
    public void setUsr031UsiMail3Kf(int usr031UsiMail3Kf) {
        usr031UsiMail3Kf__ = usr031UsiMail3Kf;
    }

    /**
     * @return usr031UsiPicgKf__ を戻します。
     */
    public int getUsr031UsiPicgKf() {
        return usr031UsiPicgKf__;
    }

    /**
     * @param usr031UsiPicgKf 設定する usr031UsiPicgKf__
     */
    public void setUsr031UsiPicgKf(int usr031UsiPicgKf) {
        usr031UsiPicgKf__ = usr031UsiPicgKf;
    }

    /**
     * @return usr031UsiTdfKf__ を戻します。
     */
    public int getUsr031UsiTdfKf() {
        return usr031UsiTdfKf__;
    }

    /**
     * @param usr031UsiTdfKf 設定する usr031UsiTdfKf__
     */
    public void setUsr031UsiTdfKf(int usr031UsiTdfKf) {
        usr031UsiTdfKf__ = usr031UsiTdfKf;
    }

    /**
     * @return usr031UsiTel1Kf__ を戻します。
     */
    public int getUsr031UsiTel1Kf() {
        return usr031UsiTel1Kf__;
    }

    /**
     * @param usr031UsiTel1Kf 設定する usr031UsiTel1Kf__
     */
    public void setUsr031UsiTel1Kf(int usr031UsiTel1Kf) {
        usr031UsiTel1Kf__ = usr031UsiTel1Kf;
    }

    /**
     * @return usr031UsiTel2Kf__ を戻します。
     */
    public int getUsr031UsiTel2Kf() {
        return usr031UsiTel2Kf__;
    }

    /**
     * @param usr031UsiTel2Kf 設定する usr031UsiTel2Kf__
     */
    public void setUsr031UsiTel2Kf(int usr031UsiTel2Kf) {
        usr031UsiTel2Kf__ = usr031UsiTel2Kf;
    }

    /**
     * @return usr031UsiTel3Kf__ を戻します。
     */
    public int getUsr031UsiTel3Kf() {
        return usr031UsiTel3Kf__;
    }

    /**
     * @param usr031UsiTel3Kf 設定する usr031UsiTel3Kf__
     */
    public void setUsr031UsiTel3Kf(int usr031UsiTel3Kf) {
        usr031UsiTel3Kf__ = usr031UsiTel3Kf;
    }

    /**
     * @return usr031UsiZipKf__ を戻します。
     */
    public int getUsr031UsiZipKf() {
        return usr031UsiZipKf__;
    }

    /**
     * @param usr031UsiZipKf 設定する usr031UsiZipKf__
     */
    public void setUsr031UsiZipKf(int usr031UsiZipKf) {
        usr031UsiZipKf__ = usr031UsiZipKf;
    }

    /**
     * @return tdfkLabelList を戻します。
     */
    public ArrayList<LabelValueBean> getTdfkLabelList() {
        return tdfkLabelList__;
    }

    /**
     * @param tdfkLabelList 設定する tdfkLabelList。
     */
    public void setTdfkLabelList(ArrayList<LabelValueBean> tdfkLabelList) {
        tdfkLabelList__ = tdfkLabelList;
    }

    /**
     * @return usr031address1 を戻します。
     */
    public String getUsr031address1() {
        return usr031address1__;
    }

    /**
     * @param usr031address1 設定する usr031address1。
     */
    public void setUsr031address1(String usr031address1) {
        usr031address1__ = usr031address1;
    }

    /**
     * @return usr031address2 を戻します。
     */
    public String getUsr031address2() {
        return usr031address2__;
    }

    /**
     * @param usr031address2 設定する usr031address2。
     */
    public void setUsr031address2(String usr031address2) {
        usr031address2__ = usr031address2;
    }

    /**
     * @return usr031birthDay を戻します。
     */
    public String getUsr031birthDay() {
        return usr031birthDay__;
    }

    /**
     * @param usr031birthDay 設定する usr031birthDay。
     */
    public void setUsr031birthDay(String usr031birthDay) {
        usr031birthDay__ = usr031birthDay;
    }

    /**
     * @return usr031birthMonth を戻します。
     */
    public String getUsr031birthMonth() {
        return usr031birthMonth__;
    }

    /**
     * @param usr031birthMonth 設定する usr031birthMonth。
     */
    public void setUsr031birthMonth(String usr031birthMonth) {
        usr031birthMonth__ = usr031birthMonth;
    }

    /**
     * @return usr031birthYear を戻します。
     */
    public String getUsr031birthYear() {
        return usr031birthYear__;
    }

    /**
     * @param usr031birthYear 設定する usr031birthYear。
     */
    public void setUsr031birthYear(String usr031birthYear) {
        usr031birthYear__ = usr031birthYear;
    }

    /**
     * @return usr031fax1 を戻します。
     */
    public String getUsr031fax1() {
        return usr031fax1__;
    }

    /**
     * @param usr031fax1 設定する usr031fax1。
     */
    public void setUsr031fax1(String usr031fax1) {
        usr031fax1__ = usr031fax1;
    }

    /**
     * @return usr031fax2 を戻します。
     */
    public String getUsr031fax2() {
        return usr031fax2__;
    }

    /**
     * @param usr031fax2 設定する usr031fax2。
     */
    public void setUsr031fax2(String usr031fax2) {
        usr031fax2__ = usr031fax2;
    }

    /**
     * @return usr031fax3 を戻します。
     */
    public String getUsr031fax3() {
        return usr031fax3__;
    }

    /**
     * @param usr031fax3 設定する usr031fax3。
     */
    public void setUsr031fax3(String usr031fax3) {
        usr031fax3__ = usr031fax3;
    }

    /**
     * @return usr031mail1 を戻します。
     */
    public String getUsr031mail1() {
        return usr031mail1__;
    }

    /**
     * @param usr031mail1 設定する usr031mail1。
     */
    public void setUsr031mail1(String usr031mail1) {
        usr031mail1__ = usr031mail1;
    }

    /**
     * @return usr031mail2 を戻します。
     */
    public String getUsr031mail2() {
        return usr031mail2__;
    }

    /**
     * @param usr031mail2 設定する usr031mail2。
     */
    public void setUsr031mail2(String usr031mail2) {
        usr031mail2__ = usr031mail2;
    }

    /**
     * @return usr031mail3 を戻します。
     */
    public String getUsr031mail3() {
        return usr031mail3__;
    }

    /**
     * @param usr031mail3 設定する usr031mail3。
     */
    public void setUsr031mail3(String usr031mail3) {
        usr031mail3__ = usr031mail3;
    }

    /**
     * @return usr031post1 を戻します。
     */
    public String getUsr031post1() {
        return usr031post1__;
    }

    /**
     * @param usr031post1 設定する usr031post1。
     */
    public void setUsr031post1(String usr031post1) {
        usr031post1__ = usr031post1;
    }

    /**
     * @return usr031post2 を戻します。
     */
    public String getUsr031post2() {
        return usr031post2__;
    }

    /**
     * @param usr031post2 設定する usr031post2。
     */
    public void setUsr031post2(String usr031post2) {
        usr031post2__ = usr031post2;
    }

    /**
     * @return usr031syozoku を戻します。
     */
    public String getUsr031syozoku() {
        return usr031syozoku__;
    }

    /**
     * @param usr031syozoku 設定する usr031syozoku。
     */
    public void setUsr031syozoku(String usr031syozoku) {
        usr031syozoku__ = usr031syozoku;
    }

    /**
     * @return usr031tdfkCd を戻します。
     */
    public String getUsr031tdfkCd() {
        return usr031tdfkCd__;
    }

    /**
     * @param usr031tdfkCd 設定する usr031tdfkCd。
     */
    public void setUsr031tdfkCd(String usr031tdfkCd) {
        usr031tdfkCd__ = usr031tdfkCd;
    }

    /**
     * @return usr031tel1 を戻します。
     */
    public String getUsr031tel1() {
        return usr031tel1__;
    }

    /**
     * @param usr031tel1 設定する usr031tel1。
     */
    public void setUsr031tel1(String usr031tel1) {
        usr031tel1__ = usr031tel1;
    }

    /**
     * @return usr031tel2 を戻します。
     */
    public String getUsr031tel2() {
        return usr031tel2__;
    }

    /**
     * @param usr031tel2 設定する usr031tel2。
     */
    public void setUsr031tel2(String usr031tel2) {
        usr031tel2__ = usr031tel2;
    }

    /**
     * @return usr031tel3 を戻します。
     */
    public String getUsr031tel3() {
        return usr031tel3__;
    }

    /**
     * @param usr031tel3 設定する usr031tel3。
     */
    public void setUsr031tel3(String usr031tel3) {
        usr031tel3__ = usr031tel3;
    }

    /**
     * @return usr031mei を戻します。
     */
    public String getUsr031mei() {
        return usr031mei__;
    }

    /**
     * @param usr031mei 設定する usr031mei。
     */
    public void setUsr031mei(String usr031mei) {
        usr031mei__ = usr031mei;
    }

    /**
     * @return usr031meikn を戻します。
     */
    public String getUsr031meikn() {
        return usr031meikn__;
    }

    /**
     * @param usr031meikn 設定する usr031meikn。
     */
    public void setUsr031meikn(String usr031meikn) {
        usr031meikn__ = usr031meikn;
    }

    /**
     * @return usr031password を戻します。
     */
    public String getUsr031password() {
        return usr031password__;
    }

    /**
     * @param usr031password 設定する usr031password。
     */
    public void setUsr031password(String usr031password) {
        usr031password__ = usr031password;
    }

    /**
     * @return usr031passwordkn を戻します。
     */
    public String getUsr031passwordkn() {
        return usr031passwordkn__;
    }

    /**
     * @param usr031passwordkn 設定する usr031passwordkn。
     */
    public void setUsr031passwordkn(String usr031passwordkn) {
        usr031passwordkn__ = usr031passwordkn;
    }

    /**
     * @return usr031sei を戻します。
     */
    public String getUsr031sei() {
        return usr031sei__;
    }

    /**
     * @param usr031sei 設定する usr031sei。
     */
    public void setUsr031sei(String usr031sei) {
        usr031sei__ = usr031sei;
    }

    /**
     * @return usr031seikn を戻します。
     */
    public String getUsr031seikn() {
        return usr031seikn__;
    }

    /**
     * @param usr031seikn 設定する usr031seikn。
     */
    public void setUsr031seikn(String usr031seikn) {
        usr031seikn__ = usr031seikn;
    }

    /**
     * @return usr031userid を戻します。
     */
    public String getUsr031userid() {
        return usr031userid__;
    }

    /**
     * @param usr031userid 設定する usr031userid。
     */
    public void setUsr031userid(String usr031userid) {
        usr031userid__ = usr031userid;
    }

    /**
     * @return usr031bikou を戻します。
     */
    public String getUsr031bikou() {
        return usr031bikou__;
    }

    /**
     * @param usr031bikou 設定する usr031bikou。
     */
    public void setUsr031bikou(String usr031bikou) {
        usr031bikou__ = usr031bikou;
    }

    /**
     * @return usr031shainno を戻します。
     */
    public String getUsr031shainno() {
        return usr031shainno__;
    }

    /**
     * @param usr031shainno 設定する usr031shainno。
     */
    public void setUsr031shainno(String usr031shainno) {
        usr031shainno__ = usr031shainno;
    }

    /**
     * @return usr031yakushoku を戻します。
     */
    public String getUsr031yakushoku() {
        return usr031yakushoku__;
    }

    /**
     * @param usr031yakushoku 設定する usr031yakushoku。
     */
    public void setUsr031yakushoku(String usr031yakushoku) {
        usr031yakushoku__ = usr031yakushoku;
    }

    /**
     * @return passworddamy を戻します。
     */
    public String getPassworddamy() {
        return passworddamy__;
    }

    /**
     * @param passworddamy 設定する passworddamy。
     */
    public void setPassworddamy(String passworddamy) {
        passworddamy__ = passworddamy;
    }

    /**
     * @return groupList を戻します。
     */
    public ArrayList<GroupModel> getGroupList() {
        return groupList__;
    }

    /**
     * @param groupList 設定する groupList。
     */
    public void setGroupList(ArrayList<GroupModel> groupList) {
        groupList__ = groupList;
    }

    /**
     * @return usr031defgroup を戻します。
     */
    public int getUsr031defgroup() {
        return usr031defgroup__;
    }

    /**
     * @param usr031defgroup 設定する usr031defgroup。
     */
    public void setUsr031defgroup(int usr031defgroup) {
        usr031defgroup__ = usr031defgroup;
    }

    /**
     * @return selectgroup を戻します。
     */
    public String getSelectgroup() {
        return selectgroup__;
    }

    /**
     * @param selectgroup 設定する selectgroup。
     */
    public void setSelectgroup(String selectgroup) {
        selectgroup__ = selectgroup;
    }

    /**
     * <p>usr031initFlg を取得します。
     * @return usr031initFlg
     */
    public int getUsr031initFlg() {
        return usr031initFlg__;
    }
    /**
     * <p>usr031initFlg をセットします。
     * @param usr031initFlg usr031initFlg
     */
    public void setUsr031initFlg(int usr031initFlg) {
        usr031initFlg__ = usr031initFlg;
    }
    /**
     * @return usr031pluginId__ を戻します。
     */
    public String getUsr031pluginId() {
        return usr031pluginId__;
    }

    /**
     * <p>usr031faxCmt1 を取得します。
     * @return usr031faxCmt1
     */
    public String getUsr031faxCmt1() {
        return usr031faxCmt1__;
    }

    /**
     * <p>usr031faxCmt1 をセットします。
     * @param usr031faxCmt1 usr031faxCmt1
     */
    public void setUsr031faxCmt1(String usr031faxCmt1) {
        usr031faxCmt1__ = usr031faxCmt1;
    }

    /**
     * <p>usr031faxCmt2 を取得します。
     * @return usr031faxCmt2
     */
    public String getUsr031faxCmt2() {
        return usr031faxCmt2__;
    }

    /**
     * <p>usr031faxCmt2 をセットします。
     * @param usr031faxCmt2 usr031faxCmt2
     */
    public void setUsr031faxCmt2(String usr031faxCmt2) {
        usr031faxCmt2__ = usr031faxCmt2;
    }

    /**
     * <p>usr031faxCmt3 を取得します。
     * @return usr031faxCmt3
     */
    public String getUsr031faxCmt3() {
        return usr031faxCmt3__;
    }

    /**
     * <p>usr031faxCmt3 をセットします。
     * @param usr031faxCmt3 usr031faxCmt3
     */
    public void setUsr031faxCmt3(String usr031faxCmt3) {
        usr031faxCmt3__ = usr031faxCmt3;
    }

    /**
     * <p>usr031mailCmt1 を取得します。
     * @return usr031mailCmt1
     */
    public String getUsr031mailCmt1() {
        return usr031mailCmt1__;
    }

    /**
     * <p>usr031mailCmt1 をセットします。
     * @param usr031mailCmt1 usr031mailCmt1
     */
    public void setUsr031mailCmt1(String usr031mailCmt1) {
        usr031mailCmt1__ = usr031mailCmt1;
    }

    /**
     * <p>usr031mailCmt2 を取得します。
     * @return usr031mailCmt2
     */
    public String getUsr031mailCmt2() {
        return usr031mailCmt2__;
    }

    /**
     * <p>usr031mailCmt2 をセットします。
     * @param usr031mailCmt2 usr031mailCmt2
     */
    public void setUsr031mailCmt2(String usr031mailCmt2) {
        usr031mailCmt2__ = usr031mailCmt2;
    }

    /**
     * <p>usr031mailCmt3 を取得します。
     * @return usr031mailCmt3
     */
    public String getUsr031mailCmt3() {
        return usr031mailCmt3__;
    }

    /**
     * <p>usr031mailCmt3 をセットします。
     * @param usr031mailCmt3 usr031mailCmt3
     */
    public void setUsr031mailCmt3(String usr031mailCmt3) {
        usr031mailCmt3__ = usr031mailCmt3;
    }

    /**
     * <p>usr031telCmt1 を取得します。
     * @return usr031telCmt1
     */
    public String getUsr031telCmt1() {
        return usr031telCmt1__;
    }

    /**
     * <p>usr031telCmt1 をセットします。
     * @param usr031telCmt1 usr031telCmt1
     */
    public void setUsr031telCmt1(String usr031telCmt1) {
        usr031telCmt1__ = usr031telCmt1;
    }

    /**
     * <p>usr031telCmt2 を取得します。
     * @return usr031telCmt2
     */
    public String getUsr031telCmt2() {
        return usr031telCmt2__;
    }

    /**
     * <p>usr031telCmt2 をセットします。
     * @param usr031telCmt2 usr031telCmt2
     */
    public void setUsr031telCmt2(String usr031telCmt2) {
        usr031telCmt2__ = usr031telCmt2;
    }

    /**
     * <p>usr031telCmt3 を取得します。
     * @return usr031telCmt3
     */
    public String getUsr031telCmt3() {
        return usr031telCmt3__;
    }

    /**
     * <p>usr031telCmt3 をセットします。
     * @param usr031telCmt3 usr031telCmt3
     */
    public void setUsr031telCmt3(String usr031telCmt3) {
        usr031telCmt3__ = usr031telCmt3;
    }

    /**
     * <p>usr031telNai1 を取得します。
     * @return usr031telNai1
     */
    public String getUsr031telNai1() {
        return usr031telNai1__;
    }

    /**
     * <p>usr031telNai1 をセットします。
     * @param usr031telNai1 usr031telNai1
     */
    public void setUsr031telNai1(String usr031telNai1) {
        usr031telNai1__ = usr031telNai1;
    }

    /**
     * <p>usr031telNai2 を取得します。
     * @return usr031telNai2
     */
    public String getUsr031telNai2() {
        return usr031telNai2__;
    }

    /**
     * <p>usr031telNai2 をセットします。
     * @param usr031telNai2 usr031telNai2
     */
    public void setUsr031telNai2(String usr031telNai2) {
        usr031telNai2__ = usr031telNai2;
    }

    /**
     * <p>usr031telNai3 を取得します。
     * @return usr031telNai3
     */
    public String getUsr031telNai3() {
        return usr031telNai3__;
    }

    /**
     * <p>usr031telNai3 をセットします。
     * @param usr031telNai3 usr031telNai3
     */
    public void setUsr031telNai3(String usr031telNai3) {
        usr031telNai3__ = usr031telNai3;
    }

    /**
     * <p>usr031pluginId をセットします。
     * @param usr031pluginId usr031pluginId
     */
    public void setUsr031pluginId(String usr031pluginId) {
        usr031pluginId__ = usr031pluginId;
    }

    /**
     * <p>posLabelList を取得します。
     * @return posLabelList
     */
    public ArrayList<LabelValueBean> getPosLabelList() {
        return posLabelList__;
    }

    /**
     * <p>posLabelList をセットします。
     * @param posLabelList posLabelList
     */
    public void setPosLabelList(ArrayList<LabelValueBean> posLabelList) {
        posLabelList__ = posLabelList;
    }
    /**
     * <p>usr031PswdKbn を取得します。
     * @return usr031PswdKbn
     */
    public String getUsr031PswdKbn() {
        return usr031PswdKbn__;
    }
    /**
     * <p>usr031PswdKbn をセットします。
     * @param usr031PswdKbn usr031PswdKbn
     */
    public void setUsr031PswdKbn(String usr031PswdKbn) {
        usr031PswdKbn__ = usr031PswdKbn;
    }
    /**
     * <p>usr031CoeKbn を取得します。
     * @return usr031CoeKbn
     */
    public int getUsr031CoeKbn() {
        return usr031CoeKbn__;
    }
    /**
     * <p>usr031CoeKbn をセットします。
     * @param usr031CoeKbn usr031CoeKbn
     */
    public void setUsr031CoeKbn(int usr031CoeKbn) {
        usr031CoeKbn__ = usr031CoeKbn;
    }
    /**
     * <p>usr031UppercaseKbn を取得します。
     * @return usr031UppercaseKbn
     */
    public int getUsr031UppercaseKbn() {
        return usr031UppercaseKbn__;
    }
    /**
     * <p>usr031UppercaseKbn をセットします。
     * @param usr031UppercaseKbn usr031UppercaseKbn
     */
    public void setUsr031UppercaseKbn(int usr031UppercaseKbn) {
        usr031UppercaseKbn__ = usr031UppercaseKbn;
    }
    /**
     * <p>usr031Digit を取得します。
     * @return usr031Digit
     */
    public int getUsr031Digit() {
        return usr031Digit__;
    }
    /**
     * <p>usr031Digit をセットします。
     * @param usr031Digit usr031Digit
     */
    public void setUsr031Digit(int usr031Digit) {
        usr031Digit__ = usr031Digit;
    }
    /**
     * <p>usr031UidPswdKbn を取得します。
     * @return usr031UidPswdKbn
     */
    public int getUsr031UidPswdKbn() {
        return usr031UidPswdKbn__;
    }
    /**
     * <p>usr031UidPswdKbn をセットします。
     * @param usr031UidPswdKbn usr031UidPswdKbn
     */
    public void setUsr031UidPswdKbn(int usr031UidPswdKbn) {
        usr031UidPswdKbn__ = usr031UidPswdKbn;
    }
    /**
     * <p>changePassword を取得します。
     * @return changePassword
     */
    public int getChangePassword() {
        return changePassword__;
    }
    /**
     * <p>changePassword をセットします。
     * @param changePassword changePassword
     */
    public void setChangePassword(int changePassword) {
        changePassword__ = changePassword;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param con DBコネクション
     * @param canChangePassword パスワード変更の有効・無効
     * @param tempDir テンポラリディレクトリパス
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateAdd(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            boolean canChangePassword,
            String tempDir)
                    throws Exception {

        ActionErrors errors = new ActionErrors();
        GSValidateUser gsValidateUser = new GSValidateUser(reqMdl);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //一般利用者が個人情報を修正する場合、変更できない箇所はチェックしない
        if (isAdminFlg()) {
            //ユーザＩＤ
            gsValidateUser.validateUserId(errors, usr031userid__);
            GSValidateUser.validateUserIdDouble(errors, getUsr030selectuser(), usr031userid__, con);

            if (canChangePassword) {
                //パスワードルール設定取得
                CmnPswdConfDao dao = new CmnPswdConfDao(con);
                CmnPswdConfModel model = dao.select();
                ValidatePasswordModel pswdMdl = new ValidatePasswordModel();
                pswdMdl.setCoe(usr031CoeKbn__);
                pswdMdl.setUppercase(usr031UppercaseKbn__);
                pswdMdl.setDigit(usr031Digit__);
                pswdMdl.setUidPswdKbn(GSConstMain.PWC_UIDPSWDKBN_OFF);
                if (model != null) {
                    pswdMdl.setUidPswdKbn(model.getPwcUidPswd());
                }
                pswdMdl.setUserSid(reqMdl.getSmodel().getUsrsid());
                pswdMdl.setPassword(usr031password__);
                pswdMdl.setPassword2(usr031passwordkn__);
                //パスワード入力チェック
                gsValidateUser.validatePassword(con, errors, pswdMdl);
            }
        }
        //ワンタイムパスワード通知先アドレス
        gsValidateUser.validateMail(errors, usr031OtpToAddress__,
                gsMsg.getMessage("user.157"), "usr031OtpToAddress.");
        //社員/職員番号
        gsValidateUser.validateShainNo(errors, usr031shainno__);
        //写真
        __validatePhoto(errors, usr031ImageName__,
                usr031ImageSaveName__, reqMdl, con, tempDir);
        //姓
        GSValidateUser.validateUserNameSei(errors, usr031sei__, reqMdl);
        //名
        GSValidateUser.validateUserNameMei(errors, usr031mei__, reqMdl);
        //姓カナ
        GSValidateUser.validateUserNameSeiKana(errors, usr031seikn__, reqMdl);
        //名カナ
        GSValidateUser.validateUserNameMeiKana(errors, usr031meikn__, reqMdl);
        //所属
        gsValidateUser.validateSyozoku(errors, usr031syozoku__);
//        //役職
//        GSValidateUser.validateYakushoku(errors, usr031yakushoku__);
        //ソートキー1
        gsValidateUser.validateSortkey1(errors, usr031sortkey1__);
        //ソートキー2
        gsValidateUser.validateSortkey2(errors, usr031sortkey2__);
        //生年月日
        gsValidateUser.validateBirthDate(
                errors, usr031birthYear__, usr031birthMonth__, usr031birthDay__);
        //入社年月日
        gsValidateUser.validateEntranceDate(
                errors, usr031entranceYear__, usr031entranceMonth__, usr031entranceDay__);
        //メールアドレス１
        gsValidateUser.validateMail(errors, usr031mail1__, 1);
        //メールアドレス１コメント
        String textMailAdCom1 = gsMsg.getMessage("user.src.69");
        GSValidateUser.validateCmt(errors, usr031mailCmt1__, textMailAdCom1);
        //メールアドレス２
        gsValidateUser.validateMail(errors, usr031mail2__, 2);
        //メールアドレス２コメント
        String textMailAdCom2 = gsMsg.getMessage("user.src.70");
        GSValidateUser.validateCmt(errors, usr031mailCmt2__, textMailAdCom2);
        //メールアドレス３
        gsValidateUser.validateMail(errors, usr031mail3__, 3);
        //メールアドレス３コメント
        String textMailAdCom3 = gsMsg.getMessage("user.src.71");
        GSValidateUser.validateCmt(errors, usr031mailCmt3__, textMailAdCom3);
        //郵便番号
        gsValidateUser.validatePostNum(errors, usr031post1__, usr031post2__);
        //住所１
        gsValidateUser.validateAddress(errors, usr031address1__, 1);
        //住所２
        gsValidateUser.validateAddress(errors, usr031address2__, 2);
        //電話１
        gsValidateUser.validateTel(errors, usr031tel1__, 1);
        //電話１内線
        String textExtension1 = gsMsg.getMessage("user.src.72");
        GSValidateUser.validateNaisen(errors, usr031telNai1__, textExtension1);
        //電話１コメント
        String textTelCom1 = gsMsg.getMessage("user.src.73");
        GSValidateUser.validateCmt(errors, usr031telCmt1__, textTelCom1);
        //電話２
        gsValidateUser.validateTel(errors, usr031tel2__, 2);
        //電話２内線
        String textExtensionCom2 = gsMsg.getMessage("user.src.74");
        GSValidateUser.validateNaisen(errors, usr031telNai2__, textExtensionCom2);
        //電話２コメント
        String textTelCom2 = gsMsg.getMessage("user.src.63");
        GSValidateUser.validateCmt(errors, usr031telCmt2__, textTelCom2);
        //電話３
        gsValidateUser.validateTel(errors, usr031tel3__, 3);
        //電話３内線
        String textExtensionCom3 = gsMsg.getMessage("user.src.64");
        GSValidateUser.validateNaisen(errors, usr031telNai3__, textExtensionCom3);
        //電話３コメント
        String textTelCom3 = gsMsg.getMessage("user.src.65");
        GSValidateUser.validateCmt(errors, usr031telCmt3__, textTelCom3);
        //ＦＡＸ１
        gsValidateUser.validateTel(errors, usr031fax1__, 4);
        //ＦＡＸ１コメント
        String textFaxCom1 = gsMsg.getMessage("user.src.66");
        GSValidateUser.validateCmt(errors, usr031faxCmt1__, textFaxCom1);
        //ＦＡＸ２
        gsValidateUser.validateTel(errors, usr031fax2__, 5);
        //ＦＡＸ２コメント
        String textFaxCom2 = gsMsg.getMessage("user.src.67");
        GSValidateUser.validateCmt(errors, usr031faxCmt2__, textFaxCom2);
        //ＦＡＸ３
        gsValidateUser.validateTel(errors, usr031fax3__, 6);
        //ＦＡＸ３コメント
        String textFaxCom3 = gsMsg.getMessage("user.src.68");
        GSValidateUser.validateCmt(errors, usr031faxCmt3__, textFaxCom3);

        //管理者 && モバイルプラグイン使用
        if (isAdminFlg() && usr031UsiMblUse__ == GSConstMain.PLUGIN_USE) {
            //固体識別番号1
            String textIdentificationNumber1 = gsMsg.getMessage("user.105");
            //固体識別番号2
            String textIdentificationNumber2 = gsMsg.getMessage("user.106");
            //固体識別番号3
            String textIdentificationNumber3 = gsMsg.getMessage("user.107");
            //固体識別番号1
            GSValidateUser.validateUid(errors, usr031CmuUid1__, textIdentificationNumber1);
            //固体識別番号2
            GSValidateUser.validateUid(errors, usr031CmuUid2__, textIdentificationNumber2);
            //固体識別番号3
            GSValidateUser.validateUid(errors, usr031CmuUid3__, textIdentificationNumber3);
        }

        //備考
        gsValidateUser.validateUserComment(errors, usr031bikou__);

        if (isAdminFlg()) {
            //一般利用者が個人情報を修正する場合、変更できない箇所はチェックしない
            gsValidateUser.validateSelectGroup(errors, selectgroup__);

            //グループSIDが存在するかチェックを行う
            if (selectgroup__ != null) {
                String[] tmpgps = selectgroup__.trim().split(",");
                for (String grid : tmpgps) {
                    boolean grExist = GSValidateUser.existGroup(NullDefault.getInt(grid, -1), con);
                    if (!grExist) {
                        String eprefix = "selectgroup.";
                        String textAffiliationGroup = gsMsg.getMessage("cmn.affiliation.group");
                        ActionMessage msg = new ActionMessage(
                                "error.select.required.text", textAffiliationGroup);
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    }
                }
            }
            //予約済みユーザはデフォルトグループを変更しないためチェックしない
            String processMode = NullDefault.getString(getProcessMode(), "");
            if (processMode.equals("add")
            || (!processMode.equals("add")
                && getUsr030selectuser() > GSConstUser.USER_RESERV_SID)) {
                //デフォルトグループ
                gsValidateUser.validateDefaultGroup(errors, usr031defgroup__);
            }
        }
        return errors;
    }

    /**
     * <p>写真の入力チェックを行う
     * @param errors ActionErrors
     * @param imageName 写真ファイル名
     * @param imageSaveName 写真ファイル保存名
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __validatePhoto(
            ActionErrors errors,
            String imageName,
            String imageSaveName,
            RequestModel reqMdl,
            Connection con,
            String tempDir)
                    throws SQLException {
        //写真ファイルパス
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgPhoto = gsMsg.getMessage("cmn.photo");
        String eprefix = "photo.";

        //写真の存在判定
        if (StringUtil.isNullZeroString(imageName)
                || StringUtil.isNullZeroString(imageSaveName)) {
            return errors;
        }
        File photoFile = new File(tempDir + imageSaveName + GSConstCommon.ENDSTR_SAVEFILE);
        if (photoFile == null || photoFile.length() <= 0) {
            return errors;
        }

        //ファイルの拡張子チェック
        if (!Cmn110Biz.isExtensionForPhotoOk(imageName)) {
            //JPG,JPEG,GIF,PNG以外のファイルならばエラー
            msg = new ActionMessage("error.select2.required.extent2", msgPhoto);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select2.required.extent2");
            return errors;
        }

        //ファイルサイズチェック
        CmnFileConfDao cfcDao = new CmnFileConfDao(con);
        CmnFileConfModel cfcModel = cfcDao.select();
        BigDecimal maxSize = new BigDecimal(cfcModel.getFicPhotoSize());
        BigDecimal maxSizeMb =
                maxSize.multiply(new BigDecimal(GSConstCommon.FILE_SIZE_1MB));
        BigDecimal fileSize = new BigDecimal(photoFile.length());

        if (maxSizeMb.compareTo(fileSize) == -1) {
            //「写真ファイル制限の最大値 < 指定されたファイルの容量」の場合はエラーメッセージを表示
            msg = new ActionMessage(
                    "error.input.capacity.over2",
                    msgPhoto,
                    String.valueOf(cfcModel.getFicPhotoSize()) + "MB");
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.capacity.over2");
        }

        return errors;
    }

    /**
     * <p>usrLabel を取得します。
     * @return usrLabel
     */
    public String[] getUsrLabel() {
        return usrLabel__;
    }
    /**
     * <p>userLabel をセットします。
     * @param usrLabel usrLabel
     */
    public void setUsrLabel(String[] usrLabel) {
        usrLabel__ = usrLabel;
    }
    /**
     * <p>selectLabelList を取得します。
     * @return selectLabelList
     */
    public ArrayList<CmnLabelUsrModel> getSelectLabelList() {
        return selectLabelList__;
    }
    /**
     * <p>selectLabelList をセットします。
     * @param selectLabelList selectLabelList
     */
    public void setSelectLabelList(ArrayList<CmnLabelUsrModel> selectLabelList) {
        selectLabelList__ = selectLabelList;
    }
    /**
     * <p>delLabel を取得します。
     * @return delLabel
     */
    public String getDelLabel() {
        return delLabel__;
    }
    /**
     * <p>delLabel をセットします。
     * @param delLabel delLabel
     */
    public void setDelLabel(String delLabel) {
        delLabel__ = delLabel;
    }
    /**
     * <p>labelSetPow を取得します。
     * @return labelSetPow
     */
    public String getLabelSetPow() {
        return labelSetPow__;
    }
    /**
     * <p>labelSetPow をセットします。
     * @param labelSetPow labelSetPow
     */
    public void setLabelSetPow(String labelSetPow) {
        labelSetPow__ = labelSetPow;
    }
    /**
     * <p>usr031sortkey1 を取得します。
     * @return usr031sortkey1
     */
    public String getUsr031sortkey1() {
        return usr031sortkey1__;
    }
    /**
     * <p>usr031sortkey1 をセットします。
     * @param usr031sortkey1 usr031sortkey1
     */
    public void setUsr031sortkey1(String usr031sortkey1) {
        usr031sortkey1__ = usr031sortkey1;
    }
    /**
     * <p>usr031sortkey2 を取得します。
     * @return usr031sortkey2
     */
    public String getUsr031sortkey2() {
        return usr031sortkey2__;
    }
    /**
     * <p>usr031sortkey2 をセットします。
     * @param usr031sortkey2 usr031sortkey2
     */
    public void setUsr031sortkey2(String usr031sortkey2) {
        usr031sortkey2__ = usr031sortkey2;
    }
    /**
     * <p>usr031entranceYear を取得します。
     * @return usr031entranceYear
     */
    public String getUsr031entranceYear() {
        return usr031entranceYear__;
    }
    /**
     * <p>usr031entranceYear をセットします。
     * @param usr031entranceYear usr031entranceYear
     */
    public void setUsr031entranceYear(String usr031entranceYear) {
        usr031entranceYear__ = usr031entranceYear;
    }
    /**
     * <p>usr031entranceMonth を取得します。
     * @return usr031entranceMonth
     */
    public String getUsr031entranceMonth() {
        return usr031entranceMonth__;
    }
    /**
     * <p>usr031entranceMonth をセットします。
     * @param usr031entranceMonth usr031entranceMonth
     */
    public void setUsr031entranceMonth(String usr031entranceMonth) {
        usr031entranceMonth__ = usr031entranceMonth;
    }
    /**
     * <p>usr031entranceDay を取得します。
     * @return usr031entranceDay
     */
    public String getUsr031entranceDay() {
        return usr031entranceDay__;
    }
    /**
     * <p>usr031entranceDay をセットします。
     * @param usr031entranceDay usr031entranceDay
     */
    public void setUsr031entranceDay(String usr031entranceDay) {
        usr031entranceDay__ = usr031entranceDay;
    }
    /**
     * <p>usr031seibetu を取得します。
     * @return usr031seibetu
     */
    public String getUsr031seibetu() {
        return usr031seibetu__;
    }
    /**
     * <p>usr031seibetu をセットします。
     * @param usr031seibetu usr031seibetu
     */
    public void setUsr031seibetu(String usr031seibetu) {
        usr031seibetu__ = usr031seibetu;
    }
    /**
     * <p>seibetuLabelList を取得します。
     * @return seibetuLabelList
     */
    public ArrayList<LabelValueBean> getSeibetuLabelList() {
        return seibetuLabelList__;
    }
    /**
     * <p>seibetuLabelList をセットします。
     * @param seibetuLabelList seibetuLabelList
     */
    public void setSeibetuLabelList(ArrayList<LabelValueBean> seibetuLabelList) {
        seibetuLabelList__ = seibetuLabelList;
    }
    /**
     * <p>usr031UsrUkoFlg を取得します。
     * @return usr031UsrUkoFlg
     */
    public int getUsr031UsrUkoFlg() {
        return usr031UsrUkoFlg__;
    }
    /**
     * <p>usr031UsrUkoFlg をセットします。
     * @param usr031UsrUkoFlg usr031UsrUkoFlg
     */
    public void setUsr031UsrUkoFlg(int usr031UsrUkoFlg) {
        usr031UsrUkoFlg__ = usr031UsrUkoFlg;
    }
    /**
     * <p>usr031OtpToAddress を取得します。
     * @return usr031OtpToAddress
     * @see jp.groupsession.v2.usr.usr031.Usr031Form#usr031OtpToAddress__
     */
    public String getUsr031OtpToAddress() {
        return usr031OtpToAddress__;
    }
    /**
     * <p>usr031OtpToAddress をセットします。
     * @param usr031OtpToAddress usr031OtpToAddress
     * @see jp.groupsession.v2.usr.usr031.Usr031Form#usr031OtpToAddress__
     */
    public void setUsr031OtpToAddress(String usr031OtpToAddress) {
        usr031OtpToAddress__ = usr031OtpToAddress;
    }
    /**
     * <p>changeOtpToAddress を取得します。
     * @return changeOtpToAddress
     * @see jp.groupsession.v2.usr.usr031.Usr031Form#changeOtpToAddress__
     */
    public int getChangeOtpToAddress() {
        return changeOtpToAddress__;
    }
    /**
     * <p>changeOtpToAddress をセットします。
     * @param changeOtpToAddress changeOtpToAddress
     * @see jp.groupsession.v2.usr.usr031.Usr031Form#changeOtpToAddress__
     */
    public void setChangeOtpToAddress(int changeOtpToAddress) {
        changeOtpToAddress__ = changeOtpToAddress;
    }
    /**
     * <p>usr031warnUserLimit を取得します。
     * @return usr031warnUserLimit
     * @see jp.groupsession.v2.usr.usr031.Usr031Form#usr031warnUserLimit__
     */
    public int getUsr031warnUserLimit() {
        return usr031warnUserLimit__;
    }
    /**
     * <p>usr031warnUserLimit をセットします。
     * @param usr031warnUserLimit usr031warnUserLimit
     * @see jp.groupsession.v2.usr.usr031.Usr031Form#usr031warnUserLimit__
     */
    public void setUsr031warnUserLimit(int usr031warnUserLimit) {
        usr031warnUserLimit__ = usr031warnUserLimit;
    }
}