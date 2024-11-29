package jp.groupsession.v2.usr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupClassDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.model.ValidatePasswordModel;

/**
 * <br>[機  能] ユーザ情報の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateUser {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSValidateUser.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public GSValidateUser(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>グループＩＤの入力チェックを行う
     * @param errors ActionErrors
     * @param grpId グループＩＤ
     * @return ActionErrors
     */
    public ActionErrors validateGroupId(ActionErrors errors, String grpId) {
        ActionMessage msg = null;
        String eprefix = "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループID
        String textGroupId = gsMsg.getMessage("cmn.group.id");
        if (StringUtil.isNullZeroString(grpId)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textGroupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (grpId.length() > GSConstUser.MAX_LENGTH_GROUPID) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textGroupId, GSConstUser.MAX_LENGTH_GROUPID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isOtheridFormat(grpId)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text", textGroupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        }
        return errors;
    }

    /**
     * <p>グループ名の入力チェックを行う
     * @param errors ActionErrors
     * @param gpname グループ名
     * @return ActionErrors
     */
    public ActionErrors validateGroupName(ActionErrors errors, String gpname) {
        ActionMessage msg = null;
        String eprefix = "gpname.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループ名
        String textGroupName = gsMsg.getMessage("cmn.group.name");
        if (StringUtil.isNullZeroString(gpname) || ValidateUtil.isSpace(gpname)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (ValidateUtil.isSpaceStart(gpname)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(gpname)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

        } else if (gpname.length() > GSConstUser.MAX_LENGTH_GROUPNAME) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textGroupName,
                    GSConstUser.MAX_LENGTH_GROUPNAME);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(gpname)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(gpname);
            msg = new ActionMessage("error.input.njapan.text", textGroupName, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>グループ名カナの入力チェックを行う
     * @param errors ActionErrors
     * @param gpnamekn グループ名カナ
     * @return ActionErrors
     */
    public ActionErrors validateGroupNameKana(ActionErrors errors, String gpnamekn) {
        ActionMessage msg = null;
        String eprefix = "gpnamekana.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループ名カナ
        String textGroupName = gsMsg.getMessage("user.14");
        if (!StringUtil.isNullZeroString(gpnamekn)) {
            if (ValidateUtil.isSpace(gpnamekn)) {
                msg = new ActionMessage("error.input.spase.only",
                        textGroupName);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            } else if (ValidateUtil.isSpaceStart(gpnamekn)) {
                //先頭スペースチェック
                msg = new ActionMessage("error.input.spase.start",
                        textGroupName);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
                //タブスペースチェック
            } else if (ValidateUtil.isTab(gpnamekn)) {
                //タイトル
                msg = new ActionMessage("error.input.tab.text",
                        textGroupName);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

            } else if (gpnamekn.length() > GSConstUser.MAX_LENGTH_GROUPNAMEKANA) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text", textGroupName,
                        GSConstUser.MAX_LENGTH_GROUPNAMEKANA);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            } else if (!GSValidateUtil.isGsWideKana(gpnamekn)) {
                //全角カナチェック
                msg = new ActionMessage("error.input.kana.text", textGroupName);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.kana.text");
            }
        }
        return errors;
    }

    /**
     * <p>グループコメントの入力チェックを行う
     * @param errors ActionErrors
     * @param comment コメント
     * @return ActionErrors
     */
    public ActionErrors validateGroupComment(ActionErrors errors, String comment) {
        ActionMessage msg = null;
        String eprefix = "gpcomment.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //コメント
        String textComment = gsMsg.getMessage("cmn.comment");
        if (!StringUtil.isNullZeroString(comment)) {
            if (comment.length() > GSConstUser.MAX_LENGTH_GROUPCOMMENT) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.textarea",
                        textComment,
                                        GSConstUser.MAX_LENGTH_GROUPCOMMENT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.textarea");
            } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(comment)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(comment);
                msg = new ActionMessage("error.input.njapan.text",
                        textComment, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの入力チェックを行う(登録時)
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @return ActionErrors
     */
    public ActionErrors validateUserId(ActionErrors errors, String userid) {
        ActionMessage msg = null;
        String eprefix = "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        if (StringUtil.isNullZeroString(userid)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textUserId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (userid.length() < GSConstUser.MIN_LENGTH_USERID
                || userid.length() > GSConstUser.MAX_LENGTH_USERID) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text", textUserId,
                    GSConstUser.MIN_LENGTH_USERID, GSConstUser.MAX_LENGTH_USERID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
        } else if (!GSValidateUtil.isUseridFormat(userid)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text", textUserId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの入力チェックを行う(ログイン時)
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @return ActionErrors
     */
    public ActionErrors validateUserIdLogin(ActionErrors errors, String userid) {
        ActionMessage msg = null;
        String eprefix = "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        if (StringUtil.isNullZeroString(userid)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textUserId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (userid.length() > GSConstUser.MAX_LENGTH_USERID) {
            //MAX桁チェック
//            msg = new ActionMessage("error.input.length.text", GSConstUser.TEXT_USER_ID,
//                    GSConstUser.MAX_LENGTH_USERID);
//            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            msg = new ActionMessage("error.auth.notfound.idpass");
            StrutsUtil.addMessage(errors, msg, "error.auth.notfound.idpass");
        } else if (!GSValidateUtil.isUseridFormat(userid)) {
            //ユーザＩＤフォーマットチェック
//            msg = new ActionMessage("error.input.format.text", GSConstUser.TEXT_USER_ID);
//            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            msg = new ActionMessage("error.auth.notfound.idpass");
            StrutsUtil.addMessage(errors, msg, "error.auth.notfound.idpass");
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @return ActionErrors
     */
    public ActionErrors validateUserSearchId(ActionErrors errors, String userid) {
        ActionMessage msg = null;
        String eprefix = "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        if (!StringUtil.isNullZeroString(userid)) {

            if (userid.length() > GSConstUser.MAX_LENGTH_USERID) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.number", textUserId,
                        GSConstUser.MAX_LENGTH_USERID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.number");
            } else if (!GSValidateUtil.isUseridFormat(userid)) {
                //ユーザＩＤフォーマットチェック
                msg = new ActionMessage("error.input.format.text", textUserId);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            }

        }
        return errors;
    }

    /**
     * <p>グループＩＤの重複登録チェックを行う
     * <p>編集するグループSIDは除く
     * @param errors ActionErrors
     * @param gpSid 除外するユーザSID
     * @param groupid ユーザＩＤ
     * @param con DBコネクション
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateGroupIdDouble(ActionErrors errors,
            int gpSid, String groupid, Connection con, RequestModel reqMdl) throws SQLException {
        ActionMessage msg = null;
        String eprefix = "groupid.";

        CmnGroupmDao dao = new CmnGroupmDao(con);
        boolean ret = dao.existGroupidEdit(gpSid, groupid);
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (ret) {
            //重複エラー
            /** メッセージ グループID **/
            String groupId = gsMsg.getMessage("cmn.group.id");
            msg = new ActionMessage("error.input.timecard.exist", groupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.timecard.exist");
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの重複登録チェックを行う
     * <p>自分のユーザIDは除く
     * @param errors ActionErrors
     * @param usid 除外するユーザSID
     * @param userid ユーザＩＤ
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateUserIdDouble(ActionErrors errors,
            int usid, String userid, Connection con) throws SQLException {
        ActionMessage msg = null;
        String eprefix = "userid.";

        CmnUsrmDao dao = new CmnUsrmDao(con);
        boolean ret = dao.existLoginidEdit(usid, userid);
        if (ret) {
            //重複エラー
            msg = new ActionMessage("error.input.double.userid");
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.double.userid");
        }
        return errors;
    }

    /**
     * <p>パスワードの入力チェックを行う(ログイン時)
     * @param errors ActionErrors
     * @param password パスワード
     * @return ActionErrors
     */
    public ActionErrors validatePasswordLogin(ActionErrors errors, String password) {
        ActionMessage msg = null;
        String eprefix = "password.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //パスワード
        String textPassWord = gsMsg.getMessage("user.117");
        if (StringUtil.isNullZeroString(password)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textPassWord);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの入力チェックを行う
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @return ActionErrors
     */
    public ActionErrors validateUser(ActionErrors errors, String userid) {
        ActionMessage msg = null;
        String eprefix = "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        if (StringUtil.isNullZeroString(userid)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textUserId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (userid.length() > GSConstUser.MAX_LENGTH_USERID
                || userid.length() < GSConstUser.MIN_LENGTH_USERID) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text",
                    textUserId, GSConstUser.MIN_LENGTH_USERID,
                    GSConstUser.MAX_LENGTH_USERID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
        } else if (!GSValidateUtil.isUseridFormat(userid)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.userid");
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.userid");
        }
        return errors;
    }

    /**
     * <p>入力された旧パスワードをチェックする
     * @param errors ActionErrors
     * @param con コネクション
     * @param userSid チェック対象のユーザSID
     * @param oldPassWord 入力された旧パスワード
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード暗号化時例外
     */
    public ActionErrors validateOldPasswordMach(ActionErrors errors,
                                                Connection con,
                                                int userSid,
                                                String oldPassWord)
        throws SQLException, EncryptionException {

        ActionMessage msg = null;
        String eprefix = "oldPassWord.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //旧パスワード
        String textOldPass = gsMsg.getMessage("user.src.28");
        //未入力チェック
        if (StringUtil.isNullZeroString(oldPassWord)) {
            msg = new ActionMessage("error.input.required.text", textOldPass);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //MIN,MAX桁チェック
        } else if (oldPassWord.length() > GSConstUser.MAX_LENGTH_PASSWORD
                || oldPassWord.length() < GSConstUser.MIN_LENGTH_PASSWORD) {
            msg = new ActionMessage("error.input.length2.text",
                    textOldPass, GSConstUser.MIN_LENGTH_PASSWORD,
                    GSConstUser.MAX_LENGTH_PASSWORD);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
        //パスワードフォーマットチェック
        } else if (!GSValidateUtil.isPasswordFormat(oldPassWord)) {
            msg = new ActionMessage(
                    "error.input.format.newpassword", textOldPass);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.format.newpassword" + textOldPass);
        //旧パスワード一致チェック
        } else {
            CmnUsrmDao usrmDao = new CmnUsrmDao(con);
            CmnUsrmModel retMdl = usrmDao.select(userSid);
            if (retMdl != null) {
                String dbOldPassWord = retMdl.getUsrPswd();
                String inputOldPassWord = GSPassword.getEncryPassword(oldPassWord);
                if (!dbOldPassWord.equals(inputOldPassWord)) {
                    msg = new ActionMessage(
                            "error.input.icchi.oldpassword", textOldPass);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.icchi.oldpassword");
                }
            }
        }

        return errors;
    }

    /**
     * <p>入力された新パスワードの入力チェックを行う
     * @param con コネクション
     * @param errors ActionErrors
     * @param model ValidatePasswordModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validateNewPassword(
        Connection con,
        ActionErrors errors,
        ValidatePasswordModel model) throws Exception {
            return validatePassword(con, errors, model, true);
    }

    /**
     * <p>入力されたパスワードの入力チェックを行う
     * @param con コネクション
     * @param errors ActionErrors
     * @param model ValidatePasswordModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validatePassword(
        Connection con,
        ActionErrors errors,
        ValidatePasswordModel model) throws Exception {
            return validatePassword(con, errors, model, false);
    }

    /**
     * <p>入力されたパスワードの入力チェックを行う
     * @param con コネクション
     * @param errors ActionErrors
     * @param model ValidatePasswordModel
     * @param newPswdFlg 新パスワードフラグ（true：新パスワード, false：通常パスワード）
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public ActionErrors validatePassword(
        Connection con,
        ActionErrors errors,
        ValidatePasswordModel model,
        boolean newPswdFlg) throws Exception {

        ActionMessage msg = null;
        String eprefix = "passWord.";
        boolean pwFlg1 = false;
        boolean pwFlg2 = false;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        String textPass = gsMsg.getMessage("cmn.password");
        String textPassKn = gsMsg.getMessage("user.src.29");
        if (newPswdFlg) {
            eprefix = "newPassWord.";
            textPass = gsMsg.getMessage("user.src.26");
            textPassKn = gsMsg.getMessage("user.src.27");
        }

        if (StringUtil.isNullZeroString(model.getPassword())) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textPass);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text" + textPass);

        } else if (model.getPassword().length() > GSConstUser.MAX_LENGTH_PASSWORD
                || model.getPassword().length() < model.getDigit()) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text",
            textPass, model.getDigit(),
                    GSConstUser.MAX_LENGTH_PASSWORD);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text" + textPass);

        } else if (!GSValidateUtil.isPasswordFormat(model.getPassword())) {
            //パスワード使用文字チェック
            msg = new ActionMessage(
                    "error.input.format.newpassword", textPass);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.format.newpassword" + textPass);

        } else if (!GSValidateUtil.isPasswordCombinationFormat(
                model.getCoe(), model.getPassword())) {
            //パスワード英数字記号混在フォーマットチェック
            if (model.getCoe() == GSConstMain.PWC_COEKBN_ON_EN) {
                msg = new ActionMessage(
                        "error.input.format.newpassword2", textPass);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.format.newpassword2" + textPass);
            } else if (model.getCoe() == GSConstMain.PWC_COEKBN_ON_ENS) {
                msg = new ActionMessage(
                        "error.input.format.newpassword3", textPass);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.format.newpassword3" + textPass);
            }

        } else if (!GSValidateUtil.isPasswordUppercaseFormat(
            model.getUppercase(), model.getPassword())) {
            //パスワード大文字小文字混在フォーマットチェック
            if (model.getUppercase() == GSConstMain.PWC_UPPERCASE_ON) {
                msg = new ActionMessage("error.input.format.newpassword4", textPass);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword4" + textPass);
            }

        } else {
            pwFlg1 = true;
        }

        if (StringUtil.isNullZeroString(model.getPassword2())) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textPassKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text" + textPassKn);
        } else {
            pwFlg2 = true;
        }

        //一致チェック
        if ((pwFlg1) && (pwFlg2)) {
            CmnUsrmDao usrmDao = new CmnUsrmDao(con);
            CmnUsrmModel retMdl = usrmDao.select(model.getUserSid());

            if (!model.getPassword().equals(model.getPassword2())) {
                msg = new ActionMessage(
                        "error.input.icchi.newpassword",
                        textPass,
                        textPassKn);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.icchi.newpassword");

            // ユーザIDと同じパスワードは許可しない
            } else if (model.getUidPswdKbn() == GSConstMain.PWC_UIDPSWDKBN_ON
                    && model.getPassword().equals(retMdl.getUsrLgid())) {
                msg = new ActionMessage("error.input.icchi.useridpassword");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.icchi.useridpassword");

            //パスワード変更時
            if (newPswdFlg) {
                // 旧パスワードと同じパスワードは許可しない
                } else if (model.getOldPswdKbn() == GSConstMain.PWC_OLDPSWDKBN_ON
                            && model.getPassword().equals(model.getOldPassword())) {
                    msg = new ActionMessage("error.input.icchi.newoldpassword");
                    StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.icchi.newoldpassword");
                }
            }
        }
        return errors;
    }

    /**
     * <p>パスワードの入力チェックを行う
     * @param errors ActionErrors
     * @param coe 英数字記号混在区分
     * @param uppercase 大文字小文字混在区分
     * @param digit パスワード桁数
     * @param uidPswdKbn ユーザIDと同一パスワード設定区分
     * @param userSid ユーザSID
     * @param password パスワード
     * @param password2 パスワード確認用
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validatePassword(
        ActionErrors errors,
        int coe,
        int uppercase,
        int digit,
        int uidPswdKbn,
        String userSid,
        String password,
        String password2,
        RequestModel reqMdl) throws Exception {

        ActionMessage msg = null;
        String eprefix = "password.";
        boolean pwFlg1 = false;
        boolean pwFlg2 = false;
        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage("user.117");

        if (StringUtil.isNullZeroString(password)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textPassWord);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (password.length() > GSConstUser.MAX_LENGTH_PASSWORD
                || password.length() < digit) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text",
                    textPassWord, digit,
                    GSConstUser.MAX_LENGTH_PASSWORD);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");

        } else if (!GSValidateUtil.isPasswordFormat(password)) {
            //パスワード使用文字チェック
            msg = new ActionMessage("error.input.format.newpassword", textPassWord);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + "error.input.format.newpassword" + textPassWord);

        } else if (!GSValidateUtil.isPasswordCombinationFormat(coe, password)) {
            //パスワード英数字記号混在フォーマットチェック
            if (coe == GSConstMain.PWC_COEKBN_ON_EN) {
                msg = new ActionMessage("error.input.format.newpassword2", textPassWord);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword2" + textPassWord);
            } else if (coe == GSConstMain.PWC_COEKBN_ON_ENS) {
                msg = new ActionMessage("error.input.format.newpassword3", textPassWord);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword3" + textPassWord);
            }

        } else if (!GSValidateUtil.isPasswordUppercaseFormat(uppercase, password)) {
            //パスワード大文字小文字混在フォーマットチェック
            if (uppercase == GSConstMain.PWC_UPPERCASE_ON) {
                msg = new ActionMessage("error.input.format.newpassword4", textPassWord);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + "error.input.format.newpassword4" + textPassWord);
            }

        } else {
            pwFlg1 = true;
        }

        String textPasswordKn = gsMsg.getMessage("user.src.29");

        if (StringUtil.isNullZeroString(password2)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textPasswordKn);
            StrutsUtil.addMessage(
                errors, msg, eprefix + "error.input.required.text" + textPasswordKn);
        } else {
            pwFlg2 = true;
        }

        //一致チェック
        if ((pwFlg1) && (pwFlg2)) {
            if (!password.equals(password2)) {
                msg = new ActionMessage("error.input.icchi.pass");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.icchi.pass");
            // ユーザIDと同じパスワードは許可しない
            } else if (uidPswdKbn == GSConstMain.PWC_UIDPSWDKBN_ON && password.equals(userSid)) {
                msg = new ActionMessage("error.input.icchi.useridpassword");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.icchi.useridpassword");
            }
        }
        return errors;
    }

    /**
     * <p>ユーザ名(姓)の入力チェックを行う
     * @param errors ActionErrors
     * @param sei ユーザ名(姓)
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateUserNameSei(ActionErrors errors,
            String sei, RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.sei.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameSei = gsMsg.getMessage("user.src.47");

        if (StringUtil.isNullZeroString(sei)) {
            // 未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textUserNameSei);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");
            return errors;
        }
        errors = validateSearchUserNameSei(errors, sei, reqMdl);
        return errors;
    }

    /**
     * <p>ユーザ名(姓)の入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param sei ユーザ名(姓)
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateSearchUserNameSei(ActionErrors errors,
            String sei, RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.sei.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameSei = gsMsg.getMessage("user.src.47");

        if (StringUtil.isNullZeroString(sei)) {
            //未入力はエラーなし
            return errors;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(sei)) {
            msg = new ActionMessage("error.input.spase.only",
                    textUserNameSei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            return errors;
        }
        if (ValidateUtil.isSpaceStart(sei)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textUserNameSei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            return errors;
        }
        //タブスペースチェック
        if (ValidateUtil.isTab(sei)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textUserNameSei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
            return errors;
        }

        if (sei.length() > GSConstUser.MAX_LENGTH_USER_NAME_SEI) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textUserNameSei,
                    GSConstUser.MAX_LENGTH_USER_NAME_SEI);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
            return errors;
        }
        if (!GSValidateUtil.isGsJapaneaseString(sei)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(sei);
            msg = new ActionMessage("error.input.njapan.text",
                    textUserNameSei, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>
     * ユーザ名(名)の入力チェックを行う
     *
     * @param errors
     *            ActionErrors
     * @param mei
     *            ユーザ名(名)
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateUserNameMei(ActionErrors errors,
            String mei, RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.mei.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameMei = gsMsg.getMessage("user.src.45");
        if (StringUtil.isNullZeroString(mei)) {
            // 未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");
            return errors;
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(mei)) {
            msg = new ActionMessage("error.input.spase.only",
                    textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            return errors;
        }
        if (ValidateUtil.isSpaceStart(mei)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            return errors;
        }
        //タブスペースチェック
        if (ValidateUtil.isTab(mei)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
        }

        if (mei.length() > GSConstUser.MAX_LENGTH_USER_NAME_MEI) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textUserNameMei,
                    GSConstUser.MAX_LENGTH_USER_NAME_MEI);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
            return errors;
        }
        if (!GSValidateUtil.isGsJapaneaseString(mei)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(mei);
            msg = new ActionMessage("error.input.njapan.text",
                    textUserNameMei, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>
     * ユーザ名(名)の入力チェックを行う(検索時)
     *
     * @param errors
     *            ActionErrors
     * @param mei
     *            ユーザ名(名)
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateSearchUserNameMei(ActionErrors errors,
            String mei, RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.mei.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameMei = gsMsg.getMessage("user.src.45");
        if (StringUtil.isNullZeroString(mei)) {
            return errors;
        }

        if (ValidateUtil.isSpace(mei)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only",
                    textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            return errors;
        } else  if (ValidateUtil.isSpaceStart(mei)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textUserNameMei);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (mei.length() > GSConstUser.MAX_LENGTH_USER_NAME_MEI) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textUserNameMei,
                    GSConstUser.MAX_LENGTH_USER_NAME_MEI);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(mei)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(mei);
            msg = new ActionMessage("error.input.njapan.text",
                    textUserNameMei, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>ユーザ名(姓)カナの入力チェックを行う
     * @param errors ActionErrors
     * @param seikana ユーザ名(姓)カナ
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateUserNameSeiKana(
                                        ActionErrors errors,
                                        String seikana,
                                        RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.seikana.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameSeiKn = gsMsg.getMessage("user.src.48");

        if (StringUtil.isNullZeroString(seikana)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        }
        return validateSearchUserNameSeiKana(errors, seikana, reqMdl);
    }

    /**
     * <p>ユーザ名(姓)カナの入力チェックを行う
     * @param errors ActionErrors
     * @param seikana ユーザ名(姓)カナ
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateSearchUserNameSeiKana(
                                            ActionErrors errors,
                                            String seikana,
                                            RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.seikana.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameSeiKn = gsMsg.getMessage("user.src.48");

        if (StringUtil.isNullZeroString(seikana)) {
            return errors;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(seikana)) {
            msg = new ActionMessage("error.input.spase.only",
                    textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            return errors;
        }
        if (ValidateUtil.isSpaceStart(seikana)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            return errors;
        }
        //タブスペースチェック
        if (ValidateUtil.isTab(seikana)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
            return errors;
        }
        if (seikana.length() > GSConstUser.MAX_LENGTH_USER_NAME_SEI_KN) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textUserNameSeiKn,
                    GSConstUser.MAX_LENGTH_USER_NAME_SEI_KN);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            return errors;
        }
        if (!GSValidateUtil.isGsWideKana(seikana)) {
            //全角カナチェック
            msg = new ActionMessage("error.input.kana.text", textUserNameSeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.kana.text");
        }
        return errors;
    }

    /**
     * <p>ユーザ名(名)カナの入力チェックを行う
     * @param errors ActionErrors
     * @param meikana ユーザ名(名)カナ
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateUserNameMeiKana(
                                        ActionErrors errors,
                                        String meikana,
                                        RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.meikana.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameMeiKn = gsMsg.getMessage("user.src.46");

        if (StringUtil.isNullZeroString(meikana)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        }
        return validateSearchUserNameMeiKana(errors, meikana, reqMdl);
    }

    /**
     * <p>ユーザ名(名)カナの入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param meikana ユーザ名(名)カナ
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateSearchUserNameMeiKana(
                                                    ActionErrors errors,
                                                    String meikana,
                                                    RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "username.meikana.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textUserNameMeiKn = gsMsg.getMessage("user.src.46");

        if (StringUtil.isNullZeroString(meikana)) {
            return errors;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(meikana)) {
            msg = new ActionMessage("error.input.spase.only",
                    textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            return errors;
        }
        if (ValidateUtil.isSpaceStart(meikana)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            return errors;
        }
        //タブスペースチェック
        if (ValidateUtil.isTab(meikana)) {
            //タイトル
            msg = new ActionMessage("error.input.tab.text",
                    textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
            return errors;
        }
        if (meikana.length() > GSConstUser.MAX_LENGTH_USER_NAME_MEI_KN) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textUserNameMeiKn,
                    GSConstUser.MAX_LENGTH_USER_NAME_MEI_KN);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            return errors;
        }
        if (!GSValidateUtil.isGsWideKana(meikana)) {
            //全角カナチェック
            msg = new ActionMessage("error.input.kana.text", textUserNameMeiKn);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.kana.text");
        }
        return errors;
    }

    /**
     * <p>年齢(From)の入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param age 年齢
     * @return ActionErrors
     */
    public ActionErrors validateSearchAgeFrom(ActionErrors errors, String age) {
        String eprefix = "age.from";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //年齢
        String textAge = gsMsg.getMessage("user.3");
        return __validateSearchAge(errors, age, eprefix, textAge + "(FROM)");
    }

    /**
     * <p>年齢(TO)の入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param age 年齢
     * @return ActionErrors
     */
    public ActionErrors validateSearchAgeTo(ActionErrors errors, String age) {
        String eprefix = "age.from.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //年齢
        String textAge = gsMsg.getMessage("user.3");
        return __validateSearchAge(errors, age, eprefix, textAge + "(TO)");
    }

    /**
     * <p>年齢の入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param age 年齢
     * @param eprefix エラーキーのPrefix
     * @param etext 画面に表示するエラーメッセージの項目名
     * @return ActionErrors
     */
    private ActionErrors __validateSearchAge(ActionErrors errors,
            String age, String eprefix, String etext) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //半角数字
        String textNumbers = gsMsg.getMessage("cmn.numbers");
        if (StringUtil.isNullZeroString(age)) {
            //未入力はエラーなし
            return errors;
        }

        if (!ValidateUtil.isNumber(age)) {
            // 数字以外の文字を入力した場合
            msg = new ActionMessage("error.input.comp.text",
                    etext, textNumbers);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
            return errors;
        }

        int iage = NullDefault.getInt(age, 0);
        if (iage > 99) {
            //3桁チェック
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    etext,
                    GSConstUser.MAX_LENGTH_AGE);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        }
        return errors;
    }


    /**
     * <p>年齢(From、To)の大小チェックを行う(検索時)
     * @param errors ActionErrors
     * @param ageFr 年齢From
     * @param ageTo 年齢To
     * @return ActionErrors
     */
    public ActionErrors validateSearchAgeRange(
            ActionErrors errors, String ageFr, String ageTo) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //開始-終了
        String textStartEnd = gsMsg.getMessage("user.src.52");
        //開始 < 終了
        String textStartLessThanEnd = gsMsg.getMessage("cmn.start.lessthan.end");
        //年齢
        String textAge = gsMsg.getMessage("user.3");
        ActionMessage msg = null;
        String eprefix = "age.range";

        if (StringUtil.isNullZeroStringSpace(ageFr) || StringUtil.isNullZeroStringSpace(ageTo)) {
            return errors;
        }

        int dateFrom = Integer.parseInt(ageFr);
        int dateTo = Integer.parseInt(ageTo);

        if (dateFrom > dateTo) {
            log__.debug("大小エラー");

            msg = new ActionMessage("error.input.comp.text",
                    textAge + "：" + textStartEnd,
                    textStartLessThanEnd);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.comp.text");
            return errors;
        }
        return errors;
    }

    /**
     * <p>社員/職員番号の入力チェックを行う
     * @param errors ActionErrors
     * @param shainno 社員/職員番号
     * @return ActionErrors
     */
    public ActionErrors validateShainNo(ActionErrors errors,
            String shainno) {
        ActionMessage msg = null;
        String eprefix = "shainno.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //社員/職員番号
        String textShainNo = gsMsg.getMessage("cmn.employee.staff.number");
        if (!StringUtil.isNullZeroString(shainno)) {
            if (shainno.length() > GSConstUser.MAX_LENGTH_SHAINNO) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textShainNo,
                        GSConstUser.MAX_LENGTH_SHAINNO);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
                return errors;
            }
            //スペースのみチェック
            if (ValidateUtil.isSpace(shainno)) {
                msg = new ActionMessage("error.input.spase.only", textShainNo);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
                return errors;
            }
            //先頭スペースチェック
            if (ValidateUtil.isSpaceStart(shainno)) {
                msg = new ActionMessage("error.input.spase.start",
                        textShainNo);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");
                return errors;
            }
            if (!GSValidateUtil.isGsJapaneaseString(shainno)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(shainno);
                msg = new ActionMessage("error.input.njapan.text",
                        textShainNo, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>社員/職員番号の入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param shainno ユーザ名(姓)
     * @return ActionErrors
     */
    public ActionErrors validateSearchShainNo(ActionErrors errors,
            String shainno) {
        return validateShainNo(errors, shainno);
    }

    /**
     * <p>役職の入力チェックを行う
     * @param errors ActionErrors
     * @param yakushoku 役職
     * @return ActionErrors
     */
    public ActionErrors validateYakushoku(ActionErrors errors,
            String yakushoku) {
        ActionMessage msg = null;
        String eprefix = "yakushoku.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //役職
        String textPost = gsMsg.getMessage("cmn.post");
        if (!StringUtil.isNullZeroString(yakushoku)) {
            if (yakushoku.length() > GSConstUser.MAX_LENGTH_YAKUSHOKU) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textPost,
                        GSConstUser.MAX_LENGTH_YAKUSHOKU);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseString(yakushoku)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(yakushoku);
                msg = new ActionMessage("error.input.njapan.text",
                        textPost, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>役職の入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param yakushoku 役職
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateSearchYakushoku(
        ActionErrors errors,
        int yakushoku,
        Connection con) throws SQLException {

        ActionMessage msg = null;
        String eprefix = "yakusyoku.";
        PosBiz pBiz = new PosBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //役職
        String textPost = gsMsg.getMessage("cmn.post");
        //存在チェック
        if (yakushoku > GSConstCommon.NUM_INIT && !pBiz.existPos(con, yakushoku)) {
            //
            msg = new ActionMessage("search.data.notfound", textPost);
            StrutsUtil.addMessage(errors, msg, eprefix + "search.data.notfound");
        }
        return errors;
    }

    /**
     * <p>所属の入力チェックを行う
     * @param errors ActionErrors
     * @param syozoku 所属
     * @return ActionErrors
     */
    public ActionErrors validateSyozoku(ActionErrors errors,
            String syozoku) {
        ActionMessage msg = null;
        String eprefix = "yakushoku.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //所属
        String textAffiliation = gsMsg.getMessage("cmn.affiliation");

        if (!StringUtil.isNullZeroString(syozoku)) {
            if (syozoku.length() > GSConstUser.MAX_LENGTH_SYOZOKU) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textAffiliation,
                        GSConstUser.MAX_LENGTH_SYOZOKU);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else if (!GSValidateUtil.isGsJapaneaseString(syozoku)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(syozoku);
                msg = new ActionMessage("error.input.njapan.text",
                        textAffiliation, nstr);
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
     * @return ActionErrors
     */
    public ActionErrors validateSortkey1(ActionErrors errors,
            String sortkey) {
        ActionMessage msg = null;
        String eprefix = "sortkey.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //所属
        String textAffiliation = gsMsg.getMessage("cmn.sortkey") + "1";

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
     * <p>ソートキー2の入力チェックを行う
     * @param errors ActionErrors
     * @param sortkey ソートキー
     * @return ActionErrors
     */
    public ActionErrors validateSortkey2(ActionErrors errors,
            String sortkey) {
        ActionMessage msg = null;
        String eprefix = "sortkey.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //所属
        String textAffiliation = gsMsg.getMessage("cmn.sortkey") + "2";

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
     * <p>固体識別番号の入力チェックを行う
     * @param errors ActionErrors
     * @param cmt 固体識別番号
     * @param checkObject 項目名
     * @return ActionErrors
     */
    public static ActionErrors validateUid(
        ActionErrors errors,
        String cmt,
        String checkObject) {

        ActionMessage msg = null;
        String eprefix = checkObject + ".uid.";

        if (!StringUtil.isNullZeroString(cmt)) {
            if (cmt.length() > GSConstUser.MAX_LENGTH_UID) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                        checkObject,
                                        GSConstUser.MAX_LENGTH_UID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else if (!GSValidateUtil.isGsJapaneaseString(cmt)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(cmt);
                msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>コメントの入力チェックを行う
     * @param errors ActionErrors
     * @param cmt コメント
     * @param checkObject 項目名
     * @return ActionErrors
     */
    public static ActionErrors validateCmt(
        ActionErrors errors,
        String cmt,
        String checkObject) {

        ActionMessage msg = null;
        String eprefix = checkObject + ".cmt.";

        if (!StringUtil.isNullZeroString(cmt)) {
            if (cmt.length() > GSConstUser.MAX_LENGTH_CMT) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                        checkObject,
                                        GSConstUser.MAX_LENGTH_CMT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else if (!GSValidateUtil.isGsJapaneaseString(cmt)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(cmt);
                msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>メールアドレスの入力チェックを行う
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param num メールの１～３
     * @return ActionErrors
     */
    public ActionErrors validateMail(ActionErrors errors,
            String mail, int num) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //メールアドレス1
        String textMailAddress1 = gsMsg.getMessage("cmn.mailaddress1");
        //メールアドレス2
        String textMailAddress2 = gsMsg.getMessage("cmn.mailaddress2");
        //メールアドレス3
        String textMailAddress3 = gsMsg.getMessage("cmn.mailaddress3");
        //メールアドレス
        String textMailAddress = gsMsg.getMessage("cmn.mailaddress");

        String eprefix = num + "mail.";
        String text = "";
        if (num == 1) {
            text = textMailAddress1;
        } else if (num == 2) {
            text = textMailAddress2;
        } else if (num == 3) {
            text = textMailAddress3;
        } else if (num == 4) {
            text = GSConstUser.TEXT_EMAIL;
        } else {
            text = textMailAddress;
            return GSValidateCommon.validateTextField(errors,
                    mail, eprefix, text, GSConstUser.MAX_LENGTH_MAIL, false);
        }
        return validateMail(errors, mail, text, eprefix);
    }
    /**
     * <p>メールアドレスの入力チェックを行う
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param mailDspName メールの表示名
     * @param eprefix エラーkeyプレフィックス
     * @return ActionErrors
     */
    public ActionErrors validateMail(ActionErrors errors,
            String mail, String mailDspName, String eprefix) {
        return validateMail(errors, mail, mailDspName, eprefix, false);
    }
    /**
     * <p>メールアドレスの入力チェックを行う
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param mailDspName メールの表示名
     * @param eprefix エラーkeyプレフィックス
     * @param hissu 必須
     * @return ActionErrors
     */
    public ActionErrors validateMail(ActionErrors errors,
            String mail, String mailDspName, String eprefix, boolean hissu) {
        ActionMessage msg = null;

        if (!StringUtil.isNullZeroString(mail)) {
            if (mail.length() > GSConstUser.MAX_LENGTH_MAIL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        mailDspName,
                        GSConstUser.MAX_LENGTH_MAIL);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else {
                //スペースのみチェック
                if (ValidateUtil.isSpace(mail)) {
                    msg = new ActionMessage("error.input.spase.only", mailDspName);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
                    return errors;
                }
                if (ValidateUtil.isSpaceStart(mail)) {
                    //先頭スペースチェック
                    msg = new ActionMessage("error.input.spase.start", mailDspName);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
                    return errors;
                }
                // タブ文字チェック
                if (ValidateUtil.isTab(mail)) {
                    msg = new ActionMessage("error.input.tab.text", mailDspName);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.tab.text");
                    return errors;
                }
                //@マークチェック
                if (mail.indexOf("@") <= 0) {
                    msg = new ActionMessage("error.input.format.text", mailDspName);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.format.text");
                    return errors;
                }
                if (!GSValidateUtil.isGsJapaneaseString(mail)) {
                    // 利用不可能な文字を入力した場合
                    String nstr = GSValidateUtil.getNotGsJapaneaseString(mail);
                    msg = new ActionMessage("error.input.njapan.text", mailDspName, nstr);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.njapan.text");
                }
            }

        } else if (hissu) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", mailDspName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        }
        return errors;
    }

    /**
     * <p>メールアドレスの入力チェックを行う
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @return ActionErrors
     */
    public ActionErrors validateSearchMail(ActionErrors errors,
            String mail) {
        return validateMail(errors, mail, -1);
    }

    /**
     * <p>電話番号の入力チェックを行う
     * @param errors ActionErrors
     * @param tel 電話番号
     * @param num 電話番号:1～3　ＦＡＸ:4～6
     * @return ActionErrors
     */
    public ActionErrors validateTel(ActionErrors errors,
            String tel, int num) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //電話番号１
        String textTel1 = gsMsg.getMessage("cmn.tel1");
        //電話番号２
        String textTel2 = gsMsg.getMessage("cmn.tel2");
        //電話番号３
        String textTel3 = gsMsg.getMessage("cmn.tel3");

        String eprefix = num + "tel.";
        String text = "";
        if (num == 1) {
            text = textTel1;
        } else if (num == 2) {
            text = textTel2;
        } else if (num == 3) {
            text = textTel3;
        } else if (num == 4) {
            text = GSConstUser.TEXT_FAX1;
        } else if (num == 5) {
            text = GSConstUser.TEXT_FAX2;
        } else if (num == 6) {
            text = GSConstUser.TEXT_FAX3;
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
     * <p>内線の入力チェックを行う
     * @param errors ActionErrors
     * @param naisen 内線
     * @param checkObject 項目名
     * @return ActionErrors
     */
    public static ActionErrors validateNaisen(
        ActionErrors errors,
        String naisen,
        String checkObject) {

        ActionMessage msg = null;
        String eprefix = checkObject + ".naisen.";

        if (!StringUtil.isNullZeroString(naisen)) {
            if (naisen.length() > GSConstUser.MAX_LENGTH_NAISEN) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                        checkObject,
                                        GSConstUser.MAX_LENGTH_NAISEN);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else if (!GSValidateUtil.isGsJapaneaseString(naisen)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(naisen);
                msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>郵便番号の入力チェックを行う
     * @param errors ActionErrors
     * @param num1 上３桁
     * @param num2 下４桁
     * @return ActionErrors
     */
    public ActionErrors validatePostNum(ActionErrors errors,
            String num1, String num2) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //半角数字
        String textNumbers3 = gsMsg.getMessage("user.src.54");
        //半角数字４桁
        String textNumbers4 = gsMsg.getMessage("user.src.55");
        //郵便番号
        String textPostCode = gsMsg.getMessage("cmn.postalcode");
        //郵便番号上３桁
        String textPostNum3 = gsMsg.getMessage("user.src.30");
        //郵便番号下４桁
        String textPostNum4 = gsMsg.getMessage("user.src.31");

        String eprefix = "post.";
        String eprefix1 = "post1.";
        String eprefix2 = "post2.";

        boolean errorFlg = false;
        boolean input1 = false;
        boolean input2 = false;

        //上３桁
        if (!StringUtil.isNullZeroString(num1)) {
            input1 = true;
            if (num1.length() > GSConstUser.MAX_LENGTH_POST1) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textPostNum3,
                        GSConstUser.MAX_LENGTH_POST1);
                StrutsUtil.addMessage(errors, msg, eprefix1
                        + "error.input.length.text");
                errorFlg = true;
            } else {

                //郵便番号フォーマットチェック
                if (!GSValidateUtil.isNumber(num1)
                        || num1.length() != GSConstUser.MAX_LENGTH_POST1) {
                    msg = new ActionMessage("error.input.comp.text",
                            textPostNum3, textNumbers3);
                    StrutsUtil.addMessage(errors, msg, eprefix1
                            + "error.input.comp.text");
                    errorFlg = true;
                }
            }

        }
        //下４桁
        if (!StringUtil.isNullZeroString(num2)) {
            input2 = true;
            if (num1.length() > GSConstUser.MAX_LENGTH_POST2) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        textPostNum4,
                        GSConstUser.MAX_LENGTH_POST2);
                StrutsUtil.addMessage(errors, msg, eprefix2
                        + "error.input.length.text");
                errorFlg = true;
            } else {

                //郵便番号フォーマットチェック
                if (!GSValidateUtil.isNumber(num2)
                        || num2.length() != GSConstUser.MAX_LENGTH_POST2) {
                    msg = new ActionMessage("error.input.comp.text",
                            textPostNum4, textNumbers4);
                    StrutsUtil.addMessage(errors, msg, eprefix2
                            + "error.input.comp.text");
                    errorFlg = true;
                }
            }

        }

        //総合チェック
        if (!errorFlg) {
            if (input1 != input2) {
                msg = new ActionMessage("error.input.comp.text",
                        textPostCode);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
            }
        }
        return errors;
    }

    /**
     * <p>郵便番号の入力チェックを行う
     * @param errors ActionErrors
     * @param post 上３桁-下４桁
     * @param line 行数
     * @return ActionErrors
     * @throws Exception 例外処理
     */
    public ActionErrors validateCsvPostNum(ActionErrors errors,
            String post, long line) throws Exception {

        return validateCsvPostNum(errors, post, line, "");
    }

    /**
     * <p>郵便番号の入力チェックを行う
     * @param errors ActionErrors
     * @param post 上３桁-下４桁
     * @param line 行数
     * @param plusName 項目名の補足文字列
     * @return ActionErrors
     * @throws Exception 例外処理
     */
    public ActionErrors validateCsvPostNum(ActionErrors errors,
            String post, long line, String plusName) throws Exception {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        plusName = NullDefault.getString(plusName, "");
        String textFormat = gsMsg.getMessage("user.src.56");
        //半角数字
        String textNumbers3 = gsMsg.getMessage("user.src.54");
        //郵便番号
        String textPostCode = gsMsg.getMessage("cmn.postalcode") + plusName;
        //郵便番号上３桁
        String textPostNum3 = gsMsg.getMessage("user.src.30") + plusName;
        //郵便番号下４桁
        String textPostNum4 = gsMsg.getMessage("user.src.31") + plusName;
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        //半角数字４桁
        String textNum4 = gsMsg.getMessage("user.src.55");
        String eprefix = line + "post.";
        String title = textLine + textPostCode;
        if (!StringUtil.isNullZeroString(post)) {
            //8桁入力
            if (post.length() != 8) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        textFormat);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
            } else {
                String num1 = post.substring(0, 3);
                String num2 = post.substring(4, 8);
                log__.debug("post.substring(0, 3)==" + post.substring(0, 3));
                log__.debug("post.substring(4, 8)==" + post.substring(4, 8));
                //郵便番号フォーマットチェック
                if (!GSValidateUtil.isNumber(num1)
                        || num1.length() != GSConstUser.MAX_LENGTH_POST1) {
                    msg = new ActionMessage("error.input.comp.text",
                            textLine + textPostNum3, textNumbers3);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.comp.text");
                } else {
                    //郵便番号フォーマットチェック
                    if (!GSValidateUtil.isNumber(num2)
                            || num2.length() != GSConstUser.MAX_LENGTH_POST2) {
                        msg = new ActionMessage("error.input.comp.text",
                                textLine + textPostNum4, textNum4);
                        StrutsUtil.addMessage(errors, msg, eprefix
                                + "error.input.comp.text");
                    } else {
                        //郵便番号フォーマットチェック（全体）
                        if (!ValidateUtil.isZip(post)) {

                            msg = new ActionMessage("error.input.comp.text",
                                    title,
                                    textFormat);
                            StrutsUtil.addMessage(errors, msg, eprefix
                                    + "error.input.comp.text");
                        }
                    }
                }
            }
        }

        return errors;
    }

    /**
     * <p>住所の入力チェックを行う
     * @param errors ActionErrors
     * @param add 住所
     * @param num 1:住所１ or 2:住所２
     * @return ActionErrors
     */
    public ActionErrors validateAddress(ActionErrors errors,
            String add, int num) {
        ActionMessage msg = null;
        String text = "";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //住所１
        String textAddress1 = gsMsg.getMessage("cmn.address1");
        //住所２
        String textAddress2 = gsMsg.getMessage("cmn.address2");
        if (num == 1) {
            text = textAddress1;
        } else if (num == 2) {
            text = textAddress2;
        }
        String eprefix = num + "address.";

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
     * <p>生年月日の入力チェックを行う
     * @param errors ActionErrors
     * @param year 年
     * @param month 月
     * @param day 日
     * @return ActionErrors
     */
    public ActionErrors validateBirthDate(ActionErrors errors,
            String year, String month, String day) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String eprefix = "birthday.";
        String eprefix_year = "birthday.year.";
        String eprefix_month = "birthday.month.";
        String eprefix_day = "birthday.day.";
        boolean errorFlg = false;
        boolean yearFlg = false;

        //半角数字
        String textNumbers = gsMsg.getMessage("cmn.numbers");
        //年
        String textYear = gsMsg.getMessage("cmn.year2");
        //生年月日(西暦)
        String textBirthDday = gsMsg.getMessage("user.120");

        //生年月日　年
        if (!StringUtil.isNullZeroString(year)) {
            yearFlg = true;
            if (year.length() > GSConstUser.MAX_LENGTH_YEAR) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("user.121", new String[] {textYear}),
                        GSConstUser.MAX_LENGTH_YEAR);
                StrutsUtil.addMessage(errors, msg, eprefix_year
                        + "error.input.length.text");
                errorFlg = true;
            } else if (!GSValidateUtil.isNumber(year)) {
                // 数字以外の文字を入力した場合
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("user.121",
                                new String[] {textYear}), textNumbers);
                StrutsUtil.addMessage(errors, msg, eprefix_year
                        + "error.input.comp.text");
                errorFlg = true;
            }
        } else if (!StringUtil.isNullZeroString(month)
                || !StringUtil.isNullZeroString(day)) {
            yearFlg = true;
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textYear);
            StrutsUtil.addMessage(errors, msg, eprefix_year + "error.input.required.text");
            errorFlg = true;
        }
        boolean monthFlg = false;
        //月
        String textMonth = gsMsg.getMessage("cmn.month");
        //生年月日　月
        if (!StringUtil.isNullZeroString(month)) {
            monthFlg = true;
            if (month.length() > GSConstUser.MAX_LENGTH_MONTH) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("user.121",
                                new String[] {textMonth}),
                        GSConstUser.MAX_LENGTH_MONTH);
                StrutsUtil.addMessage(errors, msg, eprefix_month
                        + "error.input.length.text");
                errorFlg = true;
            } else if (!GSValidateUtil.isNumber(month)) {
                // 数字以外の文字を入力した場合
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("user.121",
                                new String[] {textMonth}), textNumbers);
                StrutsUtil.addMessage(errors, msg, eprefix_month
                        + "error.input.comp.text");
                errorFlg = true;
            }
        } else if (!StringUtil.isNullZeroString(year)
                    || !StringUtil.isNullZeroString(day)) {
            monthFlg = true;
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textMonth);
            StrutsUtil.addMessage(errors, msg, eprefix_month
                    + "error.input.required.text");
            errorFlg = true;
        }
        boolean dayFlg = false;
        //日
        String textDay = gsMsg.getMessage("cmn.day");
        //生年月日　日
        if (!StringUtil.isNullZeroString(day)) {
            dayFlg = true;
            if (day.length() > GSConstUser.MAX_LENGTH_DAY) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("user.121",
                                new String[] {textDay}),
                        GSConstUser.MAX_LENGTH_DAY);
                StrutsUtil.addMessage(errors, msg, eprefix_day
                        + "error.input.length.text");
                errorFlg = true;
            } else if (!GSValidateUtil.isNumber(day)) {
                // 数字以外の文字を入力した場合
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("user.121",
                                new String[] {textDay}), textNumbers);
                StrutsUtil.addMessage(errors, msg, eprefix_day
                        + "error.input.comp.text");
                errorFlg = true;
            }
        } else if (!StringUtil.isNullZeroString(year)
                || !StringUtil.isNullZeroString(month)) {
            dayFlg = true;
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textDay);
            StrutsUtil.addMessage(errors, msg, eprefix_day + "error.input.required.text");
            errorFlg = true;
        }
        //一部分のみの入力チェック
        if (yearFlg != monthFlg
            || yearFlg != dayFlg
            || monthFlg != dayFlg) {
            msg = new ActionMessage("error.input.notfound.date", textBirthDday);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.notfound.date");
            errorFlg = true;
        }
        //論理チェック
        if (!errorFlg && yearFlg && monthFlg && dayFlg) {
            int iBYear = Integer.parseInt(year);
            int iBMonth = Integer.parseInt(month);
            int iBDay = Integer.parseInt(day);

            UDate date = new UDate();
            date.setDate(iBYear, iBMonth, iBDay);
            if (date.getYear() != iBYear
            || date.getMonth() != iBMonth
            || date.getIntDay() != iBDay) {

                msg = new ActionMessage("error.input.notfound.date", textBirthDday);
                errors.add("error.input.notfound.date", msg);
            }
        }

        return errors;
    }

    /**
     * <p>入社年月日の入力チェックを行う
     * @param errors ActionErrors
     * @param year 年
     * @param month 月
     * @param day 日
     * @return ActionErrors
     */
    public ActionErrors validateEntranceDate(ActionErrors errors,
            String year, String month, String day) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String eprefix = "entrance.";
        String eprefix_year = "entrance.year.";
        String eprefix_month = "entrance.month.";
        String eprefix_day = "entrance.day.";
        boolean errorFlg = false;
        boolean yearFlg = false;

        //半角数字
        String textNumbers = gsMsg.getMessage("cmn.numbers");
        //年
        String textYear = gsMsg.getMessage("cmn.year2");
        //生年月日(西暦)
        String textEntranceDday = gsMsg.getMessage("user.122");

        //生年月日　年
        if (!StringUtil.isNullZeroString(year)) {
            yearFlg = true;
            if (year.length() > GSConstUser.MAX_LENGTH_YEAR) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("user.122", new String[] {textYear}),
                        GSConstUser.MAX_LENGTH_YEAR);
                StrutsUtil.addMessage(errors, msg, eprefix_year
                        + "error.input.length.text");
                errorFlg = true;
            } else if (!GSValidateUtil.isNumber(year)) {
                // 数字以外の文字を入力した場合
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("user.122",
                                new String[] {textYear}), textNumbers);
                StrutsUtil.addMessage(errors, msg, eprefix_year
                        + "error.input.comp.text");
                errorFlg = true;
            }
        } else if (!StringUtil.isNullZeroString(month)
                || !StringUtil.isNullZeroString(day)) {
            yearFlg = true;
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textYear);
            StrutsUtil.addMessage(errors, msg, eprefix_year + "error.input.required.text");
            errorFlg = true;
        }
        boolean monthFlg = false;
        //月
        String textMonth = gsMsg.getMessage("cmn.month");
        //生年月日　月
        if (!StringUtil.isNullZeroString(month)) {
            monthFlg = true;
            if (month.length() > GSConstUser.MAX_LENGTH_MONTH) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("user.122",
                                new String[] {textMonth}),
                        GSConstUser.MAX_LENGTH_MONTH);
                StrutsUtil.addMessage(errors, msg, eprefix_month
                        + "error.input.length.text");
                errorFlg = true;
            } else if (!GSValidateUtil.isNumber(month)) {
                // 数字以外の文字を入力した場合
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("user.122",
                                new String[] {textMonth}), textNumbers);
                StrutsUtil.addMessage(errors, msg, eprefix_month
                        + "error.input.comp.text");
                errorFlg = true;
            }
        } else if (!StringUtil.isNullZeroString(year)
                    || !StringUtil.isNullZeroString(day)) {
            monthFlg = true;
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textMonth);
            StrutsUtil.addMessage(errors, msg, eprefix_month
                    + "error.input.required.text");
            errorFlg = true;
        }
        boolean dayFlg = false;
        //日
        String textDay = gsMsg.getMessage("cmn.day");
        //生年月日　日
        if (!StringUtil.isNullZeroString(day)) {
            dayFlg = true;
            if (day.length() > GSConstUser.MAX_LENGTH_DAY) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("user.122",
                                new String[] {textDay}),
                        GSConstUser.MAX_LENGTH_DAY);
                StrutsUtil.addMessage(errors, msg, eprefix_day
                        + "error.input.length.text");
                errorFlg = true;
            } else if (!GSValidateUtil.isNumber(day)) {
                // 数字以外の文字を入力した場合
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("user.122",
                                new String[] {textDay}), textNumbers);
                StrutsUtil.addMessage(errors, msg, eprefix_day
                        + "error.input.comp.text");
                errorFlg = true;
            }
        } else if (!StringUtil.isNullZeroString(year)
                || !StringUtil.isNullZeroString(month)) {
            dayFlg = true;
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textDay);
            StrutsUtil.addMessage(errors, msg, eprefix_day + "error.input.required.text");
            errorFlg = true;
        }
        //一部分のみの入力チェック
        if (yearFlg != monthFlg
            || yearFlg != dayFlg
            || monthFlg != dayFlg) {
            msg = new ActionMessage("error.input.notfound.date", textEntranceDday);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.notfound.date");
            errorFlg = true;
        }
        //論理チェック
        if (!errorFlg && yearFlg && monthFlg && dayFlg) {
            int iBYear = Integer.parseInt(year);
            int iBMonth = Integer.parseInt(month);
            int iBDay = Integer.parseInt(day);

            UDate date = new UDate();
            date.setDate(iBYear, iBMonth, iBDay);
            if (date.getYear() != iBYear
            || date.getMonth() != iBMonth
            || date.getIntDay() != iBDay) {

                msg = new ActionMessage("error.input.notfound.date", textEntranceDday);
                errors.add("error.input.notfound.date", msg);
            }
        }

        return errors;
    }

    /**
     * <p>ユーザコメント(備考)の入力チェックを行う
     * @param errors ActionErrors
     * @param comment コメント
     * @return ActionErrors
     */
    public ActionErrors validateUserComment(ActionErrors errors, String comment) {
        ActionMessage msg = null;
        String eprefix = "usercomment.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //備考
        String textMemo = gsMsg.getMessage("cmn.memo");
        if (!StringUtil.isNullZeroString(comment)) {
            if (comment.length() > GSConstUser.MAX_LENGTH_USERCOMMENT) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.textarea",
                        textMemo,
                                        GSConstUser.MAX_LENGTH_USERCOMMENT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.textarea");
            } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(comment)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(comment);
                msg = new ActionMessage("error.input.njapan.text",
                        textMemo, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <p>グループ削除の妥当性チェックを行う
     * <p>デフォルトグループ指定のユーザが所属するグループの削除は不可
     * @param errors ActionErrors
     * @param con Connection
     * @param grpsid グループSID
     * @return ActionErrors
     * @throws SQLException
     */
    public ActionErrors validateDeleteGroupBelongUser(
        ActionErrors errors,
        Connection con, int grpsid) throws SQLException {

        ActionMessage msg = null;

        CmnBelongmDao dao = new CmnBelongmDao(con);
        List<Integer> defGrpUids = dao.selectBelongDefaultUserSid(grpsid);
        if (defGrpUids.size() > 0) {

            msg = new ActionMessage("error.delete.default.group");
            StrutsUtil.addMessage(errors, msg, "error.delete.default.group");
            return errors;
        }
        return errors;
    }

    /**
     * <p>管理者グループのチェックを行う
     * @param errors ActionErrors
     * @param gsid グループSID
     * @param reqMdl RequestModel
     * @return ActionErrors
     */
    public static ActionErrors validateDeleteAdmin(
        ActionErrors errors,
        int gsid,
        RequestModel reqMdl) {

        ActionMessage msg = null;
        String eprefix = "admingroup.";

        GsMessage gsMsg = new GsMessage(reqMdl);
        /** メッセージ グループ **/
        String group = gsMsg.getMessage("cmn.group");
        log__.debug("管理者削除チェック :" + gsid);
        //管理者の時エラー
        if (GSConstUser.SID_ADMIN == gsid) {
            msg = new ActionMessage("error.input.power.group", group);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.power.group");
            return errors;
        }
        return errors;
    }

    /**
     * <p>選択グループの入力チェックを行う
     * @param errors ActionErrors
     * @param csv グループSIDのCSV文字列
     * @return ActionErrors
     */
    public ActionErrors validateSelectGroup(ActionErrors errors, String csv) {
        ActionMessage msg = null;
        String eprefix = "selectgroup.";
        String[] tmpgps = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //所属グループ
        String textAffiliationGroup = gsMsg.getMessage("cmn.affiliation.group");
        if (csv != null) {
            csv = csv.trim();
            tmpgps = csv.split(",");
            log__.debug("tmpgps length = " + tmpgps.length);
        }
        if (StringUtil.isNullZeroString(csv)) {
            //未入力チェック
            msg = new ActionMessage("error.select.required.text", textAffiliationGroup);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
            return errors;
        }

        //通常ありえないが入力チェックは行う
        for (int i = 0; i < tmpgps.length; i++) {
            if (!ValidateUtil.isNumber(tmpgps[i])) {
                log__.debug(tmpgps[i]);
                //数値以外の文字列
                msg = new ActionMessage("error.input.number.text", textAffiliationGroup);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.number.text");
                return errors;
            }
        }
        return errors;
    }

    /**
     * <p>選択グループの入力チェックを行う
     * @param errors ActionErrors
     * @param grp グループSID
     * @return ActionErrors
     */
    public ActionErrors validateDefaultGroup(ActionErrors errors, int grp) {
        ActionMessage msg = null;
        String eprefix = "defaultgroup.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //デフォルトグループ
        String textDefaultGroup = gsMsg.getMessage("user.35");
        if (grp == -1) {
            //未入力チェック
            msg = new ActionMessage("error.select.required.text", textDefaultGroup);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            return errors;
        }
        return errors;
    }

    /**
     * <p>選択グループの入力チェックを行う(未選択は許可)
     * @param errors ActionErrors
     * @param grp グループSID
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateSelectGroup(ActionErrors errors,
            int grp, Connection con, RequestModel reqMdl) throws SQLException {
        ActionMessage msg = null;
        String eprefix = "group.";

        GsMessage gsMsg = new GsMessage(reqMdl);
        /** メッセージ 所属グループ **/
        String syozokuGroup = gsMsg.getMessage("cmn.affiliation.group");
        //存在チェック
        if (grp >= 0 && !existGroup(grp, con)) {
            //
            msg = new ActionMessage("search.data.notfound", syozokuGroup);
            StrutsUtil.addMessage(errors, msg, eprefix + "search.data.notfound");
        }
        return errors;
    }

    /**
     * <p>所属→未所属時の妥当性をチェックする
     * @param errors ActionErrors
     * @param con コネクション
     * @param usersleft 画面選択ユーザ
     * @param grpsid 編集グループ
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    public static ActionErrors validateShozokuDefaultUser(
            ActionErrors errors, Connection con, String[] usersleft, int grpsid,
            RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "defaultgroup.";

        if (usersleft == null || usersleft.length < 0) {
            return errors;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        /** メッセージ グループ **/
        String group = gsMsg.getMessage("cmn.group");

        //デフォルトグループ
        GroupBiz grpBz = new GroupBiz();
        log__.debug("デフォルトユーザチェック(for前) :" + usersleft);
        for (String user : usersleft) {
            log__.debug("デフォルトユーザチェック :" + user);

            int defGrp = grpBz.getDefaultGroupSid(Integer.parseInt(user), con);
            if (defGrp == grpsid) {
                msg = new ActionMessage("error.input.default.group", group);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.default.group");
                return errors;
            }

        }
        return errors;
    }

    /**
     * <p> デフォルトグループに設定している全ユーザが所属しているかをチェックする
     * @param errors ActionErrors
     * @param con コネクション
     * @param usersleft 画面選択ユーザ
     * @param grpsid 編集グループ
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws SQLException
     * @throws NumberFormatException
     * @throws Exception 実行時例外
     */
    public static ActionErrors validateShozokuUser(
        ActionErrors errors,
        Connection con,
        String[] usersleft,
        int grpsid,
        RequestModel reqMdl) throws SQLException{

        ActionMessage msg = null;
        String eprefix = "defaultgroup.";

        GsMessage gsMsg = new GsMessage(reqMdl);
        /** メッセージ グループ **/
        String group = gsMsg.getMessage("cmn.group");

        List<String> shozokuUids = new ArrayList<String>();
        if (usersleft != null && usersleft.length > 0) {
            shozokuUids = Arrays.asList(usersleft);
        }

        CmnBelongmDao dao = new CmnBelongmDao(con);
        List<Integer> defGrpUids = dao.selectBelongDefaultUserSid(grpsid);
        for (int defGrpUid : defGrpUids) {
            if (defGrpUid != GSConst.SYSTEM_USER_ADMIN && defGrpUid != GSConst.SYSTEM_USER_MAIL
                && !shozokuUids.contains(String.valueOf(defGrpUid))) {
                msg = new ActionMessage("error.input.default.group", group);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.default.group");
                return errors;
            }
        }
        return errors;
    }

    /**
     * <p>削除しようとするグループが階層の末端かチェック
     * @param errors ActionErrors
     * @param con コネクション
     * @param grpsid 対象グループのSID
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateIsLastGroup(ActionErrors errors, Connection con, int grpsid)
        throws SQLException {

        ActionMessage msg = null;
        String eprefix = "defaultgroup.";

        CmnGroupClassDao clDao = new CmnGroupClassDao(con);
        boolean ret = clDao.isEndGroup(grpsid);

        if (!ret) {
            //末端ではない
            msg = new ActionMessage("cant.delete.not.last.group");
            StrutsUtil.addMessage(errors, msg, eprefix + "cant.delete.not.last.group");
            return errors;
        }

        return errors;
    }

    /**
     * <p>都道府県の入力チェックを行う
     * @param errors ActionErrors
     * @param tdfkCd コメント
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    public ActionErrors validateTdfk(
            ActionErrors errors,
            String tdfkCd,
            Connection con) throws SQLException, Exception {

        ActionMessage msg = null;
        String eprefix = "tdfk.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //都道府県
        String textTkfk = gsMsg.getMessage("cmn.prefectures");
        if (!StringUtil.isNullZeroString(tdfkCd)) {
            if (tdfkCd.length() > 2) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text", textTkfk,
                        GSConstUser.MAX_LENGTH_TDFK);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else {
                //数字チェック
                if (!GSValidateUtil.isNumberHaifun(tdfkCd)) {
                    String textFormat = gsMsg.getMessage("user.src.58");
                    msg = new ActionMessage("error.input.comp.text",
                            textTkfk, textFormat);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.comp.text");
                } else {
                    //存在チェック
                    if (Integer.parseInt(tdfkCd) != 0 && !existTdfk(tdfkCd, con)) {
                        msg = new ActionMessage("search.notfound.tdfkcode",
                                textTkfk);
                        StrutsUtil.addMessage(errors, msg, eprefix
                                + "search.notfound.tdfkcode");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * 都道府県コードが存在するか判定します
     * @param tdfkCd 都道府県コード
     * @param con コネクション
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existTdfk(String tdfkCd, Connection con) throws SQLException {
        boolean ret = false;
        if (tdfkCd == null) {
            return ret;
        }
        CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
        CmnTdfkModel model = tdfkDao.select(Integer.parseInt(tdfkCd));
        if (model != null) {
            ret = true;
        }
        return ret;
    }

    /**
     * グループが存在するか判定します
     * @param gsid グループSID
     * @param con コネクション
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public static boolean existGroup(int gsid, Connection con) throws SQLException {
        boolean ret = false;
        if (gsid < 0) {
            return ret;
        }
        GroupDao dao = new GroupDao(con);
        CmnGroupmModel group = dao.getGroup(gsid);
        if (group == null) {
            ret = false;
        } else {
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] 検索ソート順の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param sortKey1 検査ソートKey1
     * @param sortKey2 検査ソートKey2
     * @return true: エラーあり false: エラーなし
     */
    public boolean validateSearchSortOrder(
            ActionErrors errors,
            String sortKey1,
            String sortKey2) {
        ActionMessage msg = null;
        String eprefix = "sort.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ソートキー
        String textSortKey = gsMsg.getMessage("cmn.sortkey");
        if (sortKey1.equals(sortKey2)) {
            //同一キー指定チェック
            msg = new ActionMessage("error.select.dup.list", textSortKey);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.dup.list");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 選択ラベルのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param labSid 選択ラベルSID
     * @return true: エラーあり false: エラーなし
     */
    public boolean validateSearchLabel(
            ActionErrors errors,
            String[] labSid) {
        ActionMessage msg = null;
        String eprefix = "search.label";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ラベル
        String textLabel = gsMsg.getMessage("cmn.label");
        if (labSid == null) {
            //ラベル選択チェック
            msg = new ActionMessage("error.select.label.search", textLabel);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.label.search");
            return true;
        }

        return false;
    }

    /**
     * <p>グループＩＤの入力チェックを行う(検索条件用)
     * @param errors ActionErrors
     * @param grpId グループＩＤ
     * @return ActionErrors
     */
    public ActionErrors validateSearchGroupId(ActionErrors errors, String grpId) {
        ActionMessage msg = null;
        String eprefix = "userid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループID
        String textGroupId = gsMsg.getMessage("cmn.group.id");
        if (grpId.length() > GSConstUser.MAX_LENGTH_GROUPID) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textGroupId, GSConstUser.MAX_LENGTH_GROUPID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isOtheridFormat(grpId)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text", textGroupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        }
        return errors;
    }

    /**
     * <p>グループ名の入力チェックを行う(検索条件用)
     * @param errors ActionErrors
     * @param gpname グループ名
     * @return ActionErrors
     */
    public ActionErrors validateSearchGroupName(ActionErrors errors, String gpname) {
        ActionMessage msg = null;
        String eprefix = "gpname.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループ名
        String textGroupName = gsMsg.getMessage("cmn.group.name");
        if (ValidateUtil.isSpaceStart(gpname)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    textGroupName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (gpname.length() > GSConstUser.MAX_LENGTH_GROUPNAME) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textGroupName,
                    GSConstUser.MAX_LENGTH_GROUPNAME);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(gpname)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(gpname);
            msg = new ActionMessage("error.input.njapan.text", textGroupName, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>キーワードの入力チェックを行う(検索時)
     * @param errors ActionErrors
     * @param keyword キーワード
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    public static ActionErrors validateSearchKeyword(ActionErrors errors,
            String keyword, RequestModel reqMdl) throws Exception {
        ActionMessage msg = null;
        String eprefix = "keyword.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textKeyword = gsMsg.getMessage("cmn.keyword");

        if (StringUtil.isNullZeroString(keyword)) {
            //未入力はエラーなし
            return errors;
        }
        if (keyword.length() > 60) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textKeyword,
                    60);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
            return errors;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(keyword)) {
            msg = new ActionMessage("error.input.spase.start",
                                    textKeyword);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.spase.start");
            return errors;
        }
        if (!GSValidateUtil.isGsJapaneaseString(keyword)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(keyword);
            msg = new ActionMessage("error.input.njapan.text",
                    textKeyword, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <p>カテゴリーの存在チェックを行う
     * @param errors ActionErrors
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param alcSid カテゴリSID
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateCategoryExist(
            ActionErrors errors, Connection con, RequestModel reqMdl, int alcSid)
        throws SQLException {

        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl);
        //カテゴリが存在しない場合
        if (!biz.isCheckExistUsrCategory(con, alcSid)) {
            ActionMessage msg = null;
            String eprefix = "select.category.";
            msg = new ActionMessage("error.none.category.data");
            StrutsUtil.addMessage(errors, msg, eprefix + "error.none.category.data");
        }
        return errors;
    }
}
