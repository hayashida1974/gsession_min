package jp.groupsession.v2.usr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupClassDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.ValidatePasswordModel;
import jp.groupsession.v2.usr.usr011.Usr011Biz;

/**
 * <br>[機  能] ユーザ情報の入力チェックを行うクラス(CSV用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateUserCsv {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSValidateUserCsv.class);
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set HttpServletRequest
     * @param reqMdl RequestModel
     */
    public GSValidateUserCsv(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>ユーザＩＤの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvUserId(ActionErrors errors, String userid, long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (StringUtil.isNullZeroString(userid)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textLine + textUserId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (userid.length() < GSConstUser.MIN_LENGTH_USERID
                || userid.length() > GSConstUser.MAX_LENGTH_USERID) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text",
                    textLine + textUserId,
                    GSConstUser.MIN_LENGTH_USERID, GSConstUser.MAX_LENGTH_USERID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
        } else if (!GSValidateUtil.isUseridFormat(userid)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    textLine + textUserId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの重複登録チェックを行う(CSV取込み時)
     * <p>自分のユーザIDは除く
     * @param errors ActionErrors
     * @param usid 除外するユーザSID
     * @param userid ユーザＩＤ
     * @param num 行数
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCsvUserIdDouble(ActionErrors errors,
            int usid, String userid, long num, Connection con) throws SQLException {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //
        CmnUsrmDao dao = new CmnUsrmDao(con);
        boolean ret = dao.existLoginidEdit(usid, userid);
        if (ret) {
            //重複エラー
            msg = new ActionMessage("error.input.csv.double.userid", textLine);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.csv.double.userid");
        }
        return errors;
    }

    /**
     * <p>パスワードの入力チェックを行う
     * @param errors ActionErrors
     * @param password パスワード
     * @param usrLgid ユーザログインID
     * @param num 行数
     * @param coe 英数混在区分
     * @param uppercase 大文字小文字混在区分
     * @param digit パスワード桁数
     * @param uidPswdKbn ユーザID同一パスワード許可区分
     * @return ActionErrors
     */
    public ActionErrors validatePassword(
        ActionErrors errors,
        String password,
        String usrLgid,
        long num,
        int coe,
        int uppercase,
        int digit,
        int uidPswdKbn) {

        ValidatePasswordModel model = new ValidatePasswordModel();
        model.setPassword(password);
        model.setCoe(coe);
        model.setUppercase(uppercase);
        model.setDigit(digit);
        model.setUidPswdKbn(uidPswdKbn);

        return __validatePassword(errors, model, usrLgid, num, true);
    }

    /**
     * <br>[機  能] パスワードの入力チェックを行う
     * <br>[解  説] 未入力チェックを行わない、入力された場合はフォーマット等のチェックを行う
     * <br>[備  考] LDAP使用時に使用
     * @param errors ActionErrors
     * @param password パスワード
     * @param usrLgid ユーザログインID
     * @param num 行数
     * @param coe 英数混在区分
     * @param uppercase 大文字小文字混在区分
     * @param digit パスワード桁数
     * @param uidPswdKbn ユーザID同一パスワード許可区分
     * @return ActionErrors
     */
    public ActionErrors validatePasswordNoInputCheck(
        ActionErrors errors,
        String password,
        String usrLgid,
        long num,
        int coe,
        int uppercase,
        int digit,
        int uidPswdKbn) {

        ValidatePasswordModel model = new ValidatePasswordModel();
        model.setPassword(password);
        model.setCoe(coe);
        model.setUppercase(uppercase);
        model.setDigit(digit);
        model.setUidPswdKbn(uidPswdKbn);

        return __validatePassword(errors, model, usrLgid, num, false);
    }

    /**
     * <p>パスワードの入力チェックを行う
     * @param errors ActionErrors
     * @param model ValidatePasswordModel
     * @param usrLgid ユーザログインID
     * @param num 行数
     * @param inputChkFlg 未入力チェック使用フラグ true:未入力チェック行う
     * @return ActionErrors
     */
    private ActionErrors __validatePassword(
        ActionErrors errors,
        ValidatePasswordModel model,
        String usrLgid,
        long num,
        boolean inputChkFlg) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "password.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //パスワード
        String textPassWord = gsMsg.getMessage("user.117");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        if (StringUtil.isNullZeroString(model.getPassword())) {

            if (inputChkFlg) {
                //未入力チェック
                msg = new ActionMessage(
                        "error.input.required.text",
                        textLine + textPassWord);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            }

        } else if (model.getPassword().length() > GSConstUser.MAX_LENGTH_PASSWORD
                || model.getPassword().length() < model.getDigit()) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text",
                    textLine + textPassWord,
                    model.getDigit(),
                    GSConstUser.MAX_LENGTH_PASSWORD);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");

        } else if (!GSValidateUtil.isPasswordFormat(model.getPassword())) {
            //パスワード使用文字チェック
            msg = new ActionMessage(
                    "error.input.format.newpassword",
                    textLine + textPassWord);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + "error.input.format.newpassword" + textLine + textPassWord);

        } else if (!GSValidateUtil.isPasswordCombinationFormat(
                    model.getCoe(), model.getPassword())) {
            //パスワード英数字記号混在フォーマットチェック
            if (model.getCoe() == GSConstMain.PWC_COEKBN_ON_EN) {
                msg = new ActionMessage(
                        "error.input.format.newpassword2",
                        textLine + textPassWord);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword2" + textLine + textPassWord);
            } else if (model.getCoe() == GSConstMain.PWC_COEKBN_ON_ENS) {
                msg = new ActionMessage(
                        "error.input.format.newpassword3",
                        textLine + textPassWord);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword3" + textLine + textPassWord);
            }

        } else if (!GSValidateUtil.isPasswordUppercaseFormat(
                    model.getUppercase(), model.getPassword())) {
            //パスワード大文字小文字混在フォーマットチェック
            if (model.getUppercase() == GSConstMain.PWC_UPPERCASE_ON) {
                msg = new ActionMessage("error.input.format.newpassword4", textLine + textPassWord);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword4" + textLine + textPassWord);
            }

        } else if (model.getUidPswdKbn() == GSConstMain.PWC_UIDPSWDKBN_ON
                && model.getPassword().equals(usrLgid)) {
            // ユーザIDと同じパスワードは許可しない
            msg = new ActionMessage("error.input.icchi.useridpassword2",
                    textLine + textPassWord);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.icchi.useridpassword2");
        }

        return errors;
    }

    /**
     * <p>ユーザ名(姓)の入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param sei ユーザ名(姓)
     * @param num 行数
     * @param inputCheck 未入力チェックフラグ true:行う false:行わない
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validateCsvUserNameSei(ActionErrors errors,
            String sei, long num, boolean inputCheck) throws Exception {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "username.sei.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textUserNameSei = gsMsg.getMessage("user.src.47");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        if (StringUtil.isNullZeroString(sei) || ValidateUtil.isSpace(sei)) {
            // 未入力チェック
            if (inputCheck) {
                msg = new ActionMessage("error.input.required.text",
                        textLine + textUserNameSei);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.required.text");
            }
        } else if (ValidateUtil.isSpaceStart(sei)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textLine + textUserNameSei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(sei)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textLine + textUserNameSei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");


        } else if (sei.length() > GSConstUser.MAX_LENGTH_USER_NAME_SEI) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textUserNameSei,
                    GSConstUser.MAX_LENGTH_USER_NAME_SEI);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(sei)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(sei);
            msg = new ActionMessage("error.input.njapan.text",
                    textLine + textUserNameSei, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>
     * ユーザ名(名)の入力チェックを行う(CSV取込み時)
     *
     * @param errors ActionErrors
     * @param mei  ユーザ名(名)
     * @param num 行数
     * @param inputCheck 未入力チェックフラグ true:行う false:行わない
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validateCsvUserNameMei(ActionErrors errors,
            String mei, long num, boolean inputCheck) throws Exception {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "username.mei.";

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textUserNameMei = gsMsg.getMessage("user.src.45");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        if (StringUtil.isNullZeroString(mei) || ValidateUtil.isSpace(mei)) {
            if (inputCheck) {
                // 未入力チェック
                msg = new ActionMessage("error.input.required.text",
                        textLine + textUserNameMei);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.required.text");
                return errors;
            }
        } else if (ValidateUtil.isSpaceStart(mei)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textLine + textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(mei)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textLine + textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");


        } else if (mei.length() > GSConstUser.MAX_LENGTH_USER_NAME_MEI) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textUserNameMei,
                    GSConstUser.MAX_LENGTH_USER_NAME_MEI);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(mei)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(mei);
            msg = new ActionMessage("error.input.njapan.text",
                    textLine + textUserNameMei, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>ユーザ名(姓)カナの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param seikana ユーザ名(姓)カナ
     * @param num 行数
     * @param inputCheck 未入力チェックフラグ true:行う false:行わない
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validateCsvUserNameSeiKana(
            ActionErrors errors,
            String seikana,
            long num,
            boolean inputCheck) throws Exception {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "username.seikana.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textUserNameSeiKn = gsMsg.getMessage("user.src.48");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (StringUtil.isNullZeroString(seikana)) {
            if (inputCheck) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text",
                        textLine + textUserNameSeiKn);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
                return errors;
            }
        } else if (ValidateUtil.isSpaceStart(seikana)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textLine + textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        //タブスペースチェック
        } else if (ValidateUtil.isTab(seikana)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textLine + textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

        } else if (seikana.length() > GSConstUser.MAX_LENGTH_USER_NAME_SEI_KN) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textUserNameSeiKn,
                    GSConstUser.MAX_LENGTH_USER_NAME_SEI_KN);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isGsWideKana(seikana)) {
            //全角カナチェック
            msg = new ActionMessage("error.input.kana.text",
                    textLine + textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.kana.text");
        }
        return errors;
    }

    /**
     * <p>ユーザ名(名)カナの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param meikana ユーザ名(名)カナ
     * @param num 行数
     * @param inputCheck 未入力チェックフラグ true:行う false:行わない
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validateCsvUserNameMeiKana(
            ActionErrors errors,
            String meikana,
            long num,
            boolean inputCheck) throws Exception {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "username.meikana.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textUserNameMeiKn = gsMsg.getMessage("user.src.46");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        if (StringUtil.isNullZeroString(meikana)) {
            if (inputCheck) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text",
                        textLine + textUserNameMeiKn);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
                return errors;
            }
        } else if (ValidateUtil.isSpaceStart(meikana)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textLine + textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(meikana)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textLine + textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

        } else if (meikana.length() > GSConstUser.MAX_LENGTH_USER_NAME_MEI_KN) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textUserNameMeiKn,
                    GSConstUser.MAX_LENGTH_USER_NAME_MEI_KN);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isGsWideKana(meikana)) {
            //全角カナチェック
            msg = new ActionMessage("error.input.kana.text",
                    textLine + textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.kana.text");
        }
        return errors;
    }

    /**
     * <p>社員/職員番号の入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param shainno ユーザ名(姓)
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvShainNo(ActionErrors errors,
            String shainno,
            long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "shainno.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //社員/職員番号
        String textShainNo = gsMsg.getMessage("cmn.employee.staff.number");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (!StringUtil.isNullZeroString(shainno)) {
            if (shainno.length() > GSConstUser.MAX_LENGTH_SHAINNO) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textLine + textShainNo,
                        GSConstUser.MAX_LENGTH_SHAINNO);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseString(shainno)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(shainno);
                msg = new ActionMessage("error.input.njapan.text",
                        textLine + textShainNo, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>役職の入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param yakushoku 役職
     * @param line 行数
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCsvYakushoku(
        ActionErrors errors,
        String yakushoku,
        long line,
        Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //役職
        String textPost = gsMsg.getMessage("cmn.post");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        ActionMessage msg = null;
        String eprefix = line + "yakushoku.";
        String title = textLine;

        if (!StringUtil.isNullZeroString(yakushoku)) {
            if (yakushoku.length() > GSConstUser.MAX_LENGTH_YAKUSHOKU) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        title + textPost,
                        GSConstUser.MAX_LENGTH_YAKUSHOKU);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseString(yakushoku)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(yakushoku);
                msg = new ActionMessage("error.input.njapan.text",
                        title + textPost, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.njapan.text");
            }
        }

        return errors;
    }


    /**
     * <p>ソートキー1の入力チェックを行う
     * @param errors ActionErrors
     * @param sortkey ソートキー
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvSortKey1(ActionErrors errors,
            String sortkey, long line) {
        ActionMessage msg = null;
        String eprefix = "sortkey.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ソートキー1
        String textAffiliation = gsMsg.getMessage("cmn.sortkey") + "1";
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        textAffiliation = textLine + textAffiliation;

        if (!StringUtil.isNullZeroString(sortkey)) {
            if (sortkey.length() > GSConstUser.MAX_LENGTH_SORTKEY) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textAffiliation,
                        GSConstUser.MAX_LENGTH_SORTKEY);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isAlphaNum(sortkey)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(sortkey);
                msg = new ActionMessage("error.format.isalphanum",
                        textAffiliation, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.format.isalphanum");
            }
        }
        return errors;
    }

    /**
     * <p>ソートキー1の入力チェックを行う
     * @param errors ActionErrors
     * @param sortkey ソートキー
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvSortKey2(ActionErrors errors,
            String sortkey, long line) {
        ActionMessage msg = null;
        String eprefix = "sortkey.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ソートキー1
        String textAffiliation = gsMsg.getMessage("cmn.sortkey") + "2";
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        textAffiliation = textLine + textAffiliation;

        if (!StringUtil.isNullZeroString(sortkey)) {
            if (sortkey.length() > GSConstUser.MAX_LENGTH_SORTKEY) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textAffiliation,
                        GSConstUser.MAX_LENGTH_SORTKEY);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isAlphaNum(sortkey)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(sortkey);
                msg = new ActionMessage("error.format.isalphanum",
                        textAffiliation, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.format.isalphanum");
            }
        }
        return errors;
    }

    /**
     * <p>性別の入力チェックを行う
     * @param errors ActionErrors
     * @param seibetu 性別
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvSeibetu(ActionErrors errors,
            String seibetu, long line) {
        ActionMessage msg = null;
        String eprefix = "sortkey.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ソートキー1
        String textSeibetu = gsMsg.getMessage("user.123");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        textSeibetu = "[" + textLine + textSeibetu + "]";

        if (!StringUtil.isNullZeroString(seibetu)) {
            if (!seibetu.equals(String.valueOf(GSConstUser.SEIBETU_UNSET))
                    && !seibetu.equals(String.valueOf(GSConstUser.SEIBETU_MAN))
                    && !seibetu.equals(String.valueOf(GSConstUser.SEIBETU_WOMAN))) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(textSeibetu);
                msg = new ActionMessage("error.input.number.under",
                        textSeibetu, "2", nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.number.under");
            }
        }
        return errors;
    }

    /**
     * <p>所属の入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param syozoku 所属
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvSyozoku(
            ActionErrors errors,
            String syozoku,
            long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "syozoku.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //所属
        String textAffiliation = gsMsg.getMessage("cmn.affiliation");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (!StringUtil.isNullZeroString(syozoku)) {
            if (syozoku.length() > GSConstUser.MAX_LENGTH_SYOZOKU) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textLine + textAffiliation,
                        GSConstUser.MAX_LENGTH_SYOZOKU);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseString(syozoku)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(syozoku);
                msg = new ActionMessage("error.input.njapan.text",
                        textLine + textAffiliation, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>コメントの入力チェックを行う
     * @param errors ActionErrors
     * @param cmt コメント
     * @param checkObject 項目名
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCmt(
        ActionErrors errors,
        String cmt,
        String checkObject,
        long line) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        ActionMessage msg = null;
        String eprefix = line + checkObject + "cmt.";
        String text = textLine + checkObject;

        if (!StringUtil.isNullZeroString(cmt)) {
            if (cmt.length() > GSConstUser.MAX_LENGTH_CMT) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                        text,
                                        GSConstUser.MAX_LENGTH_CMT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else if (!GSValidateUtil.isGsJapaneaseString(cmt)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(cmt);
                msg = new ActionMessage("error.input.njapan.text", text, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>内線の入力チェックを行う
     * @param errors ActionErrors
     * @param naisen 内線
     * @param checkObject 項目名
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateNaisen(
        ActionErrors errors,
        String naisen,
        String checkObject,
        long line) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        ActionMessage msg = null;
        String eprefix = line + checkObject + "naisen.";
        String text = textLine + checkObject;

        if (!StringUtil.isNullZeroString(naisen)) {
            if (naisen.length() > GSConstUser.MAX_LENGTH_NAISEN) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                        text,
                                        GSConstUser.MAX_LENGTH_NAISEN);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else if (!GSValidateUtil.isGsJapaneaseString(naisen)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(naisen);
                msg = new ActionMessage("error.input.njapan.text", text, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>メールアドレスの入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param num メールの１～３
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvMail(ActionErrors errors,
            String mail, int num, long line) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //メールアドレス1
        String textMailAddress1 = gsMsg.getMessage("cmn.mailaddress1");
        //メールアドレス2
        String textMailAddress2 = gsMsg.getMessage("cmn.mailaddress2");
        //メールアドレス3
        String textMailAddress3 = gsMsg.getMessage("cmn.mailaddress3");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        String eprefix = String.valueOf(num) + line + "mail.";
        String text = "";
        if (num == 1) {
            text = textLine + textMailAddress1;
        } else if (num == 2) {
            text = textLine + textMailAddress2;
        } else if (num == 3) {
            text = textLine + textMailAddress3;
        }

        if (!StringUtil.isNullZeroString(mail)) {
            if (mail.length() > GSConstUser.MAX_LENGTH_MAIL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        text,
                        GSConstUser.MAX_LENGTH_MAIL);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else {
                //スペースのみチェック
                if (ValidateUtil.isSpace(mail)) {
                    msg = new ActionMessage("error.input.spase.only", text);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
                    return errors;
                }
                if (ValidateUtil.isSpaceStart(mail)) {
                    //先頭スペースチェック
                    msg = new ActionMessage("error.input.spase.start", text);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
                    return errors;
                }
                // タブ文字チェック
                if (ValidateUtil.isTab(mail)) {
                    msg = new ActionMessage("error.input.tab.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.tab.text");
                    return errors;
                }
                //@マークチェック
                if (mail.indexOf("@") <= 0) {
                    msg = new ActionMessage("error.input.format.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.format.text");
                    return errors;
                }

                if (!GSValidateUtil.isGsJapaneaseString(mail)) {
                    // 利用不可能な文字を入力した場合
                    String nstr = GSValidateUtil.getNotGsJapaneaseString(mail);
                    msg = new ActionMessage("error.input.njapan.text", text, nstr);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.njapan.text");
                }
            }

        }
        return errors;
    }

    /**
     * <p>電話番号の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param tel 電話番号
     * @param num 電話番号:1～3　ＦＡＸ:4～6
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvTel(ActionErrors errors,
            String tel, int num, long line) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //電話番号１
        String textTel1 = gsMsg.getMessage("cmn.tel1");
        //電話番号２
        String textTel2 = gsMsg.getMessage("cmn.tel2");
        //電話番号３
        String textTel3 = gsMsg.getMessage("cmn.tel3");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        String eprefix = String.valueOf(num) + line + "tel.";
        String text = "";
        if (num == 1) {
            text = textLine + textTel1;
        } else if (num == 2) {
            text = textLine + textTel2;
        } else if (num == 3) {
            text = textLine + textTel3;
        } else if (num == 4) {
            text = textLine + GSConstUser.TEXT_FAX1;
        } else if (num == 5) {
            text = textLine + GSConstUser.TEXT_FAX2;
        } else if (num == 6) {
            text = textLine + GSConstUser.TEXT_FAX3;
        }

        if (!StringUtil.isNullZeroString(tel)) {
            if (tel.length() > GSConstUser.MAX_LENGTH_TEL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        text,
                        GSConstUser.MAX_LENGTH_TEL);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else {

                //電話番号フォーマットチェック
                if (!GSValidateUtil.isTel(tel)) {
                    msg = new ActionMessage("error.input.format.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.format.text");
                }
            }

        }
        return errors;
    }

    /**
     * <p>住所の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param add 住所
     * @param num 1:住所１ or 2:住所２
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvAddress(ActionErrors errors,
            String add, int num, long line) {
        ActionMessage msg = null;
        String text = "";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //住所１
        String textAddress1 = gsMsg.getMessage("cmn.address1");
        //住所２
        String textAddress2 = gsMsg.getMessage("cmn.address2");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        if (num == 1) {
            text = textAddress1;
        } else if (num == 2) {
            text = textAddress2;
        }
        text = textLine + text;

        String eprefix = line + num + "address.";

        if (!StringUtil.isNullZeroString(add)) {
            if (add.length() > GSConstUser.MAX_LENGTH_ADD) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        text,
                        GSConstUser.MAX_LENGTH_ADD);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseString(add)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(add);
                msg = new ActionMessage("error.input.njapan.text",
                        text, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>入社年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymd 年月日
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvEntranceDate(ActionErrors errors,
            String ymd, long num) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //半角数字8桁(yyyymmdd形式)
        String textNumbers8 = gsMsg.getMessage("cmn.format.date");
        //入社年月日(西暦)
        String textEntranceDday = gsMsg.getMessage("user.122");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "entranceday.";
        String title = textLine + textEntranceDday;
//        boolean errorFlg = false;
//        boolean yearFlg = false;
        if (!StringUtil.isNullZeroString(ymd)) {
            //8桁入力
            if (ymd.length() != 8) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        textNumbers8);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
            } else {

                int iBYear = 0;
                int iBMonth = 0;
                int iBDay = 0;
                try {
                    String year = ymd.substring(0, 4);
                    String month = ymd.substring(4, 6);
                    String day = ymd.substring(6, 8);
                    log__.debug("year=" + year);
                    log__.debug("month=" + month);
                    log__.debug("day=" + day);
                    iBYear = Integer.parseInt(year);
                    iBMonth = Integer.parseInt(month);
                    iBDay = Integer.parseInt(day);
                } catch (NumberFormatException e) {
                    log__.debug("入社年月日CSV入力エラー");
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }

                //論理チェック
                UDate date = new UDate();
                date.setDate(iBYear, iBMonth, iBDay);
                if (date.getYear() != iBYear
                || date.getMonth() != iBMonth
                || date.getIntDay() != iBDay) {

                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }
            }


        }
        return errors;
    }

    /**
     * <p>生年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymd 年月日
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvBirthDate(ActionErrors errors,
            String ymd, long num) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //半角数字8桁(yyyymmdd形式)
        String textNumbers8 = gsMsg.getMessage("cmn.format.date");
        //生年月日(西暦)
        String textBirthDday = gsMsg.getMessage("user.120");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "birthday.";
        String title = textLine + textBirthDday;
//        boolean errorFlg = false;
//        boolean yearFlg = false;
        if (!StringUtil.isNullZeroString(ymd)) {
            //8桁入力
            if (ymd.length() != 8) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        textNumbers8);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
            } else {

                int iBYear = 0;
                int iBMonth = 0;
                int iBDay = 0;
                try {
                    String year = ymd.substring(0, 4);
                    String month = ymd.substring(4, 6);
                    String day = ymd.substring(6, 8);
                    log__.debug("year=" + year);
                    log__.debug("month=" + month);
                    log__.debug("day=" + day);
                    iBYear = Integer.parseInt(year);
                    iBMonth = Integer.parseInt(month);
                    iBDay = Integer.parseInt(day);
                } catch (NumberFormatException e) {
                    log__.debug("生年月日CSV入力エラー");
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }

                //論理チェック
                UDate date = new UDate();
                date.setDate(iBYear, iBMonth, iBDay);
                if (date.getYear() != iBYear
                || date.getMonth() != iBMonth
                || date.getIntDay() != iBDay) {

                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }
            }


        }
        return errors;
    }

    /**
     * <p>ユーザコメント(備考)の入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param comment コメント
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvUserComment(
            ActionErrors errors, String comment, long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "usercomment.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //備考
        String textMemo = gsMsg.getMessage("cmn.memo");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (!StringUtil.isNullZeroString(comment)) {
            if (comment.length() > GSConstUser.MAX_LENGTH_USERCOMMENT) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textLine + textMemo,
                        GSConstUser.MAX_LENGTH_USERCOMMENT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(comment)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(comment);
                msg = new ActionMessage("error.input.njapan.text",
                        textLine + textMemo, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>都道府県の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param tdfkCd コメント
     * @param line 行数
     * @param con コネクション
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCsvTdfk(
            ActionErrors errors,
            String tdfkCd,
            long line,
            Connection con) throws Exception {

        return validateCsvTdfk(errors, tdfkCd, line, con, "");
    }

    /**
     * <p>都道府県の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param tdfkCd コメント
     * @param line 行数
     * @param con コネクション
     * @param plusName 項目名の補足文字列
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCsvTdfk(
            ActionErrors errors,
            String tdfkCd,
            long line,
            Connection con,
            String plusName) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        ActionMessage msg = null;
        String eprefix = line + "tdfk.";
        String title = textLine;
        //都道府県
        plusName = NullDefault.getString(plusName, "");
        String textTkfk = gsMsg.getMessage("cmn.prefectures") + plusName;
        if (!StringUtil.isNullZeroString(tdfkCd)) {
            if (tdfkCd.length() > 2) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text", title + textTkfk,
                        GSConstUser.MAX_LENGTH_TDFK);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else {
                //数字チェック
                if (!GSValidateUtil.isNumberHaifun(tdfkCd)) {
                    String textFormat = gsMsg.getMessage("user.src.58");
                    msg = new ActionMessage("error.input.comp.text",
                            title + textTkfk, textFormat);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.comp.text");
                } else {
                    //存在チェック
                    GSValidateUser gsValidateUser = new GSValidateUser(reqMdl__);
                    if (Integer.parseInt(tdfkCd) != 0 && !gsValidateUser.existTdfk(tdfkCd, con)) {
                        msg = new ActionMessage("search.notfound.tdfkcode",
                                title + textTkfk);
                        StrutsUtil.addMessage(errors, msg, eprefix
                                + "search.notfound.tdfkcode");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * <p>公開フラグのチェックを行う(CSV用)
     * @param errors ActionErrors
     * @param koukaiFlg 公開フラグ
     * @param chkFlgName チェックするフラグの名称
     * @param line 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvKoukaiFlg(
            ActionErrors errors,
            String koukaiFlg,
            String chkFlgName,
            long line) {

        ActionMessage msg = null;
        String eprefix = chkFlgName + ".";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        //半角数字
        String textNumbers = gsMsg.getMessage("cmn.numbers");
        //0か1
        String text0Or1 = gsMsg.getMessage("reserve.src.47");
        if (koukaiFlg.equals("")) {
            koukaiFlg = "0";
        }
        if (!GSValidateUtil.isNumber(koukaiFlg)) {
            //数値チェック
            msg = new ActionMessage("error.input.comp.text",
                    textLine + chkFlgName, textNumbers);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
        } else if (!koukaiFlg.equals(String.valueOf(GSConstUser.INDIVIDUAL_INFO_OPEN))
                && !koukaiFlg.equals(String.valueOf(GSConstUser.INDIVIDUAL_INFO_CLOSE))) {
            //桁数および数値の論理チェック
            msg = new ActionMessage("error.input.comp.text",
                    textLine + chkFlgName, text0Or1);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
        }

        return errors;
    }

    /**
     * <p>グループＩＤの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param num 行数
     * @param groupNullCheck 未入力チェックを行うか(true:行う)
     * @param groupIdNum 現在確認しているグループID
     * @param inputCheck 未入力チェックフラグ true:行う false:行わない
     * @return ActionErrors
     */
    public ActionErrors validateCsvGroupId(ActionErrors errors,
                                            String groupid, long num, boolean groupNullCheck,
                                            String groupIdNum, boolean inputCheck) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //グループIDx
        String textGroupId = gsMsg.getMessage("cmn.group.id") + groupIdNum;
        String eprefix = String.valueOf(num) + "groupid" + textGroupId + ".";

        //Nullチェックがfalse(グループID2～10)でIDが未入力の時はチェックを行わない
        if (groupid.length() == 0 && !groupNullCheck) {
            return errors;
        }

        if (StringUtil.isNullZeroString(groupid)) {
            //未入力チェック
            if (inputCheck) {
                msg = new ActionMessage("error.input.required.text",
                        textLine + textGroupId);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            }
        } else if (groupid.length() > GSConstUser.MAX_LENGTH_GROUPID) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textGroupId, GSConstUser.MAX_LENGTH_GROUPID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isOtheridFormat(groupid)) {
            //グループＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    textLine + textGroupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        }
        return errors;
    }

    /**
     * <p>グループＩＤのフォーマットチェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvGroupIdFormat(ActionErrors errors,
                                            String groupid, long num) {

        if (StringUtil.isNullZeroStringSpace(groupid)) {
            ActionMessage msg = null;
            String eprefix = String.valueOf(num) + "groupid.";
            GsMessage gsMsg = new GsMessage(reqMdl__);
            //グループID
            String textGroupId = "親" + gsMsg.getMessage("cmn.group.id");
            //行目の
            String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
            if (!GSValidateUtil.isOtheridFormat(groupid)) {
                //グループＩＤフォーマットチェック
                msg = new ActionMessage("error.input.format.text",
                        textLine + textGroupId);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            }
        }

        return errors;
    }

    /**
     * <p>グループＩＤの重複登録チェックを行う(CSV取込み時)
     * <p>取り込み行グループSIDは除く
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param num 行数
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCsvGroupIdDouble(ActionErrors errors,
                String groupid, long num, Connection con) throws SQLException {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "groupid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        /** メッセージ グループID **/
        String groupId = gsMsg.getMessage("cmn.group.id");
        CmnGroupmDao dao = new CmnGroupmDao(con);
        boolean ret = dao.existGroupidEdit(0, groupid);
        if (ret) {
            //重複エラー
            msg = new ActionMessage(
                    "error.input.timecard.exist", textLine + groupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.timecard.exist");
        }
        return errors;
    }

    /**
     * <p>グループの存在チェックを行う(CSV取込み時)
     * <p>取り込み行グループSIDは除く
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param num 行数
     * @param con DBコネクション
     * @param groupIdNum 何番目のグループIDか
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCsvGroupExist(ActionErrors errors,
                String groupid, long num, Connection con, String  groupIdNum) throws SQLException {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //グループIDx
        String textGroupId = gsMsg.getMessage("cmn.group.id") + groupIdNum;
        String eprefix = String.valueOf(num) + "groupid" + textGroupId + ".";

        CmnGroupmDao dao = new CmnGroupmDao(con);
        boolean ret = dao.existGroupEdit(0, groupid);

        if (!ret) {
            if (!groupid.equals(GSConstUser.USER_KANRI_ID)) {
                //未登録エラー
                msg = new ActionMessage(
                    "error.edit.no.group2", textLine + "(" + textGroupId + ")");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.edit.no.group2");
            } else {
                //管理者グループエラー
                msg = new ActionMessage(
                    "error.input.double.kanri", textLine + "(" + textGroupId + ")");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.double.kanri");
            }
        }

        return errors;
    }

    /**
     * <p>グループの存在チェックを行う(CSV取込み時)
     * <p>取り込み行グループSIDは除く
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param num 行数
     * @param con DBコネクション
     * @param gpIdList 登録予定グループID
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCsvParentGroupExist(ActionErrors errors,
                String groupid, long num, Connection con,
                List<String> gpIdList) throws SQLException {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "groupid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        CmnGroupmDao dao = new CmnGroupmDao(con);
        boolean ret = dao.existGroupEdit(0, groupid);
        if (!ret) {
            if (!groupid.equals(GSConstUser.USER_KANRI_ID)) {

                if (gpIdList.indexOf(groupid) < 0) {
                    //未登録エラー
                    msg = new ActionMessage(
                        "error.edit.no.group2", textLine + "親");
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.edit.no.group2");
                }


            } else {
                //管理者グループエラー
                msg = new ActionMessage(
                    "error.input.double.kanri", textLine + "親");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.double.kanri");
            }
        }
        return errors;
    }

    /**
     * <p>親グループに自分より下の階層のグループが指定されていないか
     * <p>取り込み行グループSIDは除く
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param parentgroupid 親グループID
     * @param num 行数
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCsvExsitChild(ActionErrors errors,
                                            String groupid,
                                            String parentgroupid,
                                            long num,
                                            Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});

        CmnGroupmDao dao = new CmnGroupmDao(con);
        CmnGroupmModel grpmdl = new CmnGroupmModel();
        CmnGroupmModel parentgrpmdl = new CmnGroupmModel();
        grpmdl = dao.getGroupInf(groupid);
        parentgrpmdl = dao.getGroupInf(parentgroupid);

        if (grpmdl != null && parentgrpmdl != null) {
            Usr011Biz biz = new Usr011Biz(reqMdl__);
            ArrayList<Integer> childGrpSidList = new ArrayList<Integer>();
            childGrpSidList = biz.getDisabledGroups(grpmdl.getGrpSid(), con);

            ActionMessage msg = null;

            for (int childSid : childGrpSidList) {
                if (childSid == parentgrpmdl.getGrpSid() && childSid != 0) {

                    msg = new ActionMessage("error.select.parent.group",
                            textLine);
                    StrutsUtil.addMessage(errors, msg, "error.select.parent.group");

                }
            }
        }

        return errors;
    }

    /**
     * <p>グループ名の入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param gpname グループ名
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvGroupName(ActionErrors errors,
            String gpname, long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "gpname.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループ名
        String textGroupName = gsMsg.getMessage("cmn.group.name");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (StringUtil.isNullZeroString(gpname) || ValidateUtil.isSpace(gpname)) {
            // 未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textLine + textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");

        } else if (ValidateUtil.isSpaceStart(gpname)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textLine + textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(gpname)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textLine + textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

        } else if (gpname.length() > GSConstUser.MAX_LENGTH_GROUPNAME) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textGroupName,
                    GSConstUser.MAX_LENGTH_GROUPNAME);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(gpname)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(gpname);
            msg = new ActionMessage("error.input.njapan.text",
                    textLine + textGroupName, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>グループ名カナの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param grpNameKana グループ名カナ
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvGroupNameKana(
            ActionErrors errors,
            String grpNameKana,
            long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "username.seikana.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループ名カナ
        String textGroupName = gsMsg.getMessage("user.14");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (StringUtil.isNullZeroString(grpNameKana)) {
            return errors;

        } else if (ValidateUtil.isSpaceStart(grpNameKana)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textLine + textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(textGroupName)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textLine + textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
        } else if (grpNameKana.length() > GSConstUser.MAX_LENGTH_GROUPNAMEKANA) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textGroupName,
                    GSConstUser.MAX_LENGTH_GROUPNAMEKANA);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isGsWideKana(grpNameKana)) {
            //全角カナチェック
            msg = new ActionMessage("error.input.kana.text",
                    textLine + textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.kana.text");
        }
        return errors;
    }

    /**
     * <p>グループコメントの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param comment コメント
     * @param num 行数
     * @return ActionErrors
     */
    public ActionErrors validateCsvGroupComment(
            ActionErrors errors, String comment, long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "groupcomment.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //コメント
        String textComment = gsMsg.getMessage("cmn.comment");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (!StringUtil.isNullZeroString(comment)) {
            if (comment.length() > GSConstUser.MAX_LENGTH_GROUPCOMMENT) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textLine + textComment,
                        GSConstUser.MAX_LENGTH_GROUPCOMMENT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(comment)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(comment);
                msg = new ActionMessage("error.input.njapan.text",
                        textLine + textComment, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }


    /**
     * <p>グループの存在チェックを行う
     * @param errors ActionErrors
     * @param grpid グループID
     * @param reqMdl RequestModel
     * @param con コネクショ
     * @param num 行数
     * @param textId グループIDの文字列
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    public ActionErrors validateExsistGrp(ActionErrors errors, String grpid,
                                RequestModel reqMdl, Connection con,
                                long num, String textId) throws Exception {
        ActionMessage msg = null;

        //行目の
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});

        CmnGroupmDao dao = new CmnGroupmDao(con);
        CmnGroupmModel grpmdl = new CmnGroupmModel();
        grpmdl = dao.getGroupInf(grpid);

        log__.debug("存在チェック :" + grpid);
        if (grpmdl == null) {
            msg = new ActionMessage("search.data.notfound", textId + "(" +  textLine + ")");
            StrutsUtil.addMessage(errors, msg, "search.data.notfound");
            return errors;
        }
        return errors;
    }

    /**
     * <p>管理者グループのチェックを行う
     * @param errors ActionErrors
     * @param grpid グループID
     * @param reqMdl RequestModel
     * @param con コネクション
     * @param num 行数
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    public ActionErrors validateDeleteAdmin(ActionErrors errors, String grpid,
            RequestModel reqMdl, Connection con, long num) throws Exception {

        //行目の
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        ActionMessage msg = null;
        String eprefix = "admingroup.";

        CmnGroupmDao dao = new CmnGroupmDao(con);
        CmnGroupmModel grpmdl = new CmnGroupmModel();
        grpmdl = dao.getGroupInf(grpid);
        /** メッセージ グループ **/
        String group = gsMsg.getMessage("cmn.group");
        log__.debug("管理者削除チェック :" + grpid);
        //管理者の時エラー
        if (grpmdl != null && GSConstUser.SID_ADMIN == grpmdl.getGrpSid()) {
            msg = new ActionMessage("error.input.power.group", textLine + group);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.power.group");
            return errors;
        }
        return errors;
    }

    /**
     * <p>削除しようとするグループが階層の末端かチェック
     * @param errors ActionErrors
     * @param con コネクション
     * @param grpid 対象グループのID
     * @param num 行数
     * @param delGpid 削除予定のグループID
     * @return ActionErrors
     * @throws Exception SQL実行時例外
     */
    public ActionErrors validateIsLastGroup(
                    ActionErrors errors, Connection con, String grpid,
                    long num, List<String> delGpid)
        throws Exception {

        //行目の
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        CmnGroupmDao dao = new CmnGroupmDao(con);
        CmnGroupmModel grpmdl = new CmnGroupmModel();
        grpmdl = dao.getGroupInf(grpid);

        //削除予定のグループSID取得
        List<Integer> delGpSidList = new ArrayList<Integer>();
        CmnGroupmModel delGrpMdl = null;
        for (String delGpId : delGpid) {
            delGrpMdl = dao.getGroupInf(delGpId);
            if (delGrpMdl != null) {
                delGpSidList.add(delGrpMdl.getGrpSid());
            }
        }

        ActionMessage msg = null;
        if (grpmdl != null) {
            CmnGroupClassDao clDao = new CmnGroupClassDao(con);
            boolean ret = clDao.isEndGroup(grpmdl.getGrpSid(), delGpSidList);

            if (!ret) {
                //末端ではない
                msg = new ActionMessage("cant.delete.not.last.group2", textLine);
                StrutsUtil.addMessage(errors, msg, textLine + "cant.delete.not.last.group2");
                return errors;
            }
        }

        return errors;
    }

    /**
     * <p>ユーザ／グループの妥当性チェックを行う
     * @param errors ActionErrors
     * @param con Connection
     * @param grpid グループID
     * @param num 行数
     * @return ActionErrors
     * @throws Exception SQL実行時例外
     */
    public ActionErrors validateBelongUser(
            ActionErrors errors, Connection con, String grpid, long num) throws Exception {


        //行目の
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        ActionMessage msg = null;
        String eprefix = "syozokuuser.";

        CmnGroupmDao dao = new CmnGroupmDao(con);
        CmnGroupmModel grpmdl = new CmnGroupmModel();
        grpmdl = dao.getGroupInf(grpid);

        if (grpmdl != null) {
            //所属ユーザーリストを作成します。
            UserBiz userBiz = new UserBiz();
            List<SltUserPerGroupModel> alist
                = userBiz.getUserPerGroupList(con, grpmdl.getGrpSid(), null, true);


            String[] sids = new String[alist.size()];
            for (int i = 0; i < alist.size(); i++) {
                SltUserPerGroupModel sltModel = (SltUserPerGroupModel) alist.get(i);
                sids[i] = Integer.toString(sltModel.getUsrsid());
            }


            if (sids.length == 0) {
                return errors;
            }
            //所属ユーザ
            String textSelectBelongUser = gsMsg.getMessage("user.75");
            //サイズがゼロ以上はエラー
            if (sids.length > 0) {
                //未入力チェック
                msg = new ActionMessage("error.input.exist.user", textLine + textSelectBelongUser);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.exist.user");
                return errors;
            }
        }

        return errors;
    }
}