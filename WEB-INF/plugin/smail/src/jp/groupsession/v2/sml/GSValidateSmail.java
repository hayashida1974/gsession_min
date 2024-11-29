package jp.groupsession.v2.sml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlUserSearchDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 入力チェックに関係する機能を実装したクラス
 * <br>[解  説] ショートメールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateSmail {

    /**
     * <br>[機  能] チェックボックスが選択されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target エラーの場合に表示するターゲット名
     * @param mailSid 削除するショートメッセージSID
     * @param con コネクション
     * @param sacSid アカウントSID
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateCheckBoxMessage(ActionErrors errors,
                                                         String target,
                                                         String[] mailSid,
                                                         Connection con,
                                                         int sacSid) throws SQLException {
        ActionMessage msg = null;

        //未選択チェック
        if (mailSid == null) {
            msg = new ActionMessage("error.select.required.text", target);
            StrutsUtil.addMessage(errors, msg, "mailSid");
        } else if (mailSid.length < 1) {
            msg = new ActionMessage("error.select.required.text", target);
            StrutsUtil.addMessage(errors, msg, "mailSid");
        }
        return errors;
    }

    /**
     * <br>[機  能] ショートメッセージの差出人アカウントチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param con コネクション
     * @param sendAccount 差出人アカウントSID
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateSendAccount(ActionErrors errors,
                                                     int sendAccount,
                                                     Connection con,
                                                     RequestModel reqMdl)
        throws SQLException {

        ActionMessage msg = null;
        SmlAccountDao saDao = new SmlAccountDao(con);
        int userSid = reqMdl.getSmodel().getUsrsid();
        if (!saDao.getAccountPossession(sendAccount, userSid)) {
            msg = new ActionMessage("error.can.not.send.account");
            StrutsUtil.addMessage(errors, msg, "hname");
        }

        return errors;
    }

    /**
     * <br>[機  能] ショートメッセージの宛先入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mode チェックモード
     * @param kbn 宛先区分（0:TO 1:CC 2:BCC）
     * @param userSid 宛先ユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateAtesakiUser(ActionErrors errors,
                                                     int mode,
                                                     int kbn,
                                                     String[] userSid,
                                                     Connection con,
                                                     RequestModel reqMdl)
        throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        if (userSid == null) {
            ArrayList<String> nullObj = null;
            return validateAtesakiUser(errors, mode, kbn, nullObj, con, reqMdl);
        } else {
            for (String i : userSid) {
                list.add(i);
            }
            return validateAtesakiUser(errors, mode, kbn, list, con, reqMdl);
        }
    }
    /**
     * <br>[機  能] ショートメッセージの宛先入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mode チェックモード
     * @param kbn 宛先区分（0:TO 1:CC 2:BCC）
     * @param userSid 宛先ユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateAtesakiUser(ActionErrors errors,
                                                     int mode,
                                                     int kbn,
                                                     List<String> userSid,
                                                     Connection con,
                                                     RequestModel reqMdl)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String targetName = "";
        String targetJp = "";
        switch (kbn) {
            case GSConstSmail.SML_SEND_KBN_ATESAKI:
                targetName = "userSid";
                targetJp = gsMsg.getMessage("cmn.from");;
                break;
            case GSConstSmail.SML_SEND_KBN_CC:
                targetName = "userSidCc";
                targetJp = "CC";
                break;
            case GSConstSmail.SML_SEND_KBN_BCC:
                targetName = "userSidBcc";
                targetJp = "BCC";
                break;
            default:
        }

        String fieldFix = targetName + ".";

        //未選択チェック
        if ((userSid == null || userSid.size() < 1)) {
            if (mode == Sml020Form.VALIDATE_MODE_SAVE || kbn != GSConstSmail.SML_SEND_KBN_ATESAKI) {
                return errors;
            }

            msg =
                new ActionMessage(
                    "error.select.required.text",
                    gsMsg.getMessage("cmn.from"));
            StrutsUtil.addMessage(errors, msg, "userSid");
        } else {
            //削除済ユーザのチェック
            List<String> newUserSid = new ArrayList<String>();
            List<String> accountUserSid = new ArrayList<String>();

            for (String usid : userSid) {
                String atesaki  = gsMsg.getMessage("sml.sml020.09");
                if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                    //数字チェック
                    if (!ValidateUtil.isNumber(
                            usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()))) {
                        msg = new ActionMessage("error.resetting", atesaki);
                        StrutsUtil.addMessage(errors, msg, "search.data.notfound");
                        return errors;
                    }
                    //作成アカウント
                    accountUserSid.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
                } else {
                    //数字チェック
                    if (!ValidateUtil.isNumber(usid)) {
                        msg = new ActionMessage("error.resetting", atesaki);
                        StrutsUtil.addMessage(errors, msg, "search.data.notfound");
                        return errors;
                    }
                    //GSユーザ
                    newUserSid.add(usid);
                }
            }
            int count = 0;
            int usrCount = 0;
            int sacCount = 0;

            if (!newUserSid.isEmpty()) {
                CmnUsrmDao udao = new CmnUsrmDao(con);
                usrCount = udao.getCountDeleteUser(newUserSid);
            }

            if (!accountUserSid.isEmpty()) {
                SmlAccountDao sacDao = new SmlAccountDao(con);
                sacCount = sacDao.getCountDeleteAccount(accountUserSid);
            }

            count = usrCount + sacCount;

            if (count > 0) {
                msg =
                    new ActionMessage("error.select.has.deleteuser.list", targetJp);
                StrutsUtil.addMessage(errors, msg,
                        fieldFix + "error.select.has.deleteuser.list");
            }
            //送信制限送信先のチェック
            if (mode != Sml020Form.VALIDATE_MODE_SAVE) {
                SmlBanDestDao sbdDao = new SmlBanDestDao(con);
                List<Integer> banedAccSid = new ArrayList<Integer>();
                List<Integer> banedUsrSid = new ArrayList<Integer>();
                if (accountUserSid != null && accountUserSid.size() > 0) {
                    List<Integer> intAccountUserSid = new ArrayList<Integer>();
                    for (String id : accountUserSid) {
                        intAccountUserSid.add(Integer.parseInt(id));
                    }
                    banedAccSid = sbdDao.getBanDestAccSidList(
                            reqMdl.getSmodel().getUsrsid(), intAccountUserSid);
                }
                if (newUserSid != null && newUserSid.size() > 0) {
                    List<Integer> intNewUserSid = new ArrayList<Integer>();
                    for (String id : newUserSid) {
                        intNewUserSid.add(Integer.parseInt(id));
                    }
                    banedUsrSid = sbdDao.getBanDestUsrSidList(
                            reqMdl.getSmodel().getUsrsid(), intNewUserSid);
                }
                if (banedAccSid.size() > 0 || banedUsrSid.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    SmlUserSearchDao udao = new SmlUserSearchDao(con);
                    if (banedUsrSid.size() > 0) {
                        String[] sids = new String[banedUsrSid.size()];
                        for (int i = 0; i < banedUsrSid.size(); i++) {
                            sids[i] = String.valueOf(banedUsrSid.get(i));
                        }
                        List<AtesakiModel> atkList = udao.getUserDataFromSidList(sids);
                        for (AtesakiModel atk :atkList) {
                            sb.append("<br />・");
                            sb.append(atk.getUsiSei());
                            sb.append(" ");
                            sb.append(atk.getUsiMei());
                        }
                    }
                    if (banedAccSid.size() > 0) {
                        String[] sids = new String[banedAccSid.size()];
                        for (int i = 0; i < banedAccSid.size(); i++) {
                            sids[i] = String.valueOf(banedAccSid.get(i));
                        }
                        List<AtesakiModel> atkList = udao.getAccountDataFromSidList(sids);
                        for (AtesakiModel atk :atkList) {
                            sb.append("<br />・");
                            sb.append(atk.getAccountName());
                        }
                    }
                    msg =
                            new ActionMessage("error.dest.banned", targetJp, sb.toString());
                        StrutsUtil.addMessage(errors, msg, fieldFix + "error.dest.banned");
                }
            }

        }

        return errors;
    }

    /**
     * <br>[機  能] ショートメールの宛先入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] システム通知メールを送信する際に使用します
     *
     * @param userSidArray 宛先ユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return errMsg エラーメッセージ
     * @throws SQLException SQL実行時例外
     */
    public static String validateAtesakiUser(List<Integer> userSidArray,
                                               Connection con, RequestModel reqMdl)
        throws SQLException {

        String errMsg = "";
        GsMessage gsMsg = new GsMessage(reqMdl);
        /** メッセージ  送信先が1件も指定されていません **/
        String errorMsg1 = gsMsg.getMessage("cmn.no.destination.specified");
        /** メッセージ  送信先に削除済みのユーザが含まれています **/
        String errorMsg2 = gsMsg.getMessage("cmn.contains.deleteduser.destination");

        //未選択チェック
        if (userSidArray.isEmpty()) {
            errMsg = errorMsg1;
        } else {
            //削除済ユーザのチェック
            CmnUsrmDao udao = new CmnUsrmDao(con);
            ArrayList<String> susids = new ArrayList<String>();
            for (Integer itg : userSidArray) {
                susids.add(itg.toString());
            }
            int count = udao.getCountDeleteUser(susids);
            if (count > 0) {
                errMsg = errorMsg2;
            }
        }

        return errMsg;
    }

    /**
     * <br>[機  能] 雛形名称の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param hname 雛形名称
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateHinaName(ActionErrors errors, String hname,
            RequestModel reqMdl) {
        ActionMessage msg = null;
        boolean error = false;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String hinaName = gsMsg.getMessage("sml.template.name");

        //未入力チェック
        if (StringUtil.isNullZeroString(hname)) {
            msg = new ActionMessage("error.input.required.text", hinaName);
            StrutsUtil.addMessage(errors, msg, "hname");
            error = true;
        }

        //MAX桁チェック
        if (!error && hname.length() > GSConstCommon.MAX_LENGTH_HINANAME) {
            msg = new ActionMessage("error.input.length.text", hinaName,
                                    GSConstCommon.MAX_LENGTH_HINANAME);
            StrutsUtil.addMessage(errors, msg, "hname");
            error = true;
        }
        //スペースのみチェック
        if (!error && ValidateUtil.isSpace(hname)) {
            msg = new ActionMessage("error.input.spase.only", hinaName);
            StrutsUtil.addMessage(errors, msg, "hname");
            error = true;
        }
        //先頭スペースチェック
        if (!error && ValidateUtil.isSpaceStart(hname)) {
            msg = new ActionMessage("error.input.spase.start", hinaName);
            StrutsUtil.addMessage(errors, msg, "hname");
            error = true;
        }
        //タブ文字が含まれている
        if (ValidateUtil.isTab(hname)) {
            msg = new ActionMessage("error.input.tab.text", hinaName);
            StrutsUtil.addMessage(errors, msg, "hname");
            error = true;
        }
        //JIS第2水準チェック
        if (!error && !GSValidateUtil.isGsJapaneaseString(hname)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(hname);
            msg = new ActionMessage("error.input.njapan.text", hinaName,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "hname");
            error = true;
        }
        return errors;
    }

    /**
     * <br>[機  能] 件名の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param title 件名
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateSmlTitle(ActionErrors errors, String title,
            RequestModel reqMdl) {
        ActionMessage msg = null;
        boolean error = false;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgSubject = gsMsg.getMessage("cmn.subject");

        //未入力チェック
        if (StringUtil.isNullZeroString(title)) {
            msg = new ActionMessage("error.input.required.text", msgSubject);
            StrutsUtil.addMessage(errors, msg, "title");
            error = true;
        }
        //MAX桁チェック
        if (!error && title.length() > GSConstCommon.MAX_LENGTH_SMLTITLE) {
            msg = new ActionMessage("error.input.length.text", msgSubject,
                                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            StrutsUtil.addMessage(errors, msg, "title");
            error = true;
        }
        //スペースのみチェック
        if (!error && ValidateUtil.isSpace(title)) {
            msg = new ActionMessage("error.input.spase.only", msgSubject);
            StrutsUtil.addMessage(errors, msg, "title");
            error = true;
        }
        //先頭スペースチェック
        if (!error && ValidateUtil.isSpaceStart(title)) {
            msg = new ActionMessage("error.input.spase.start", msgSubject);
            StrutsUtil.addMessage(errors, msg, "title");
            error = true;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(title)) {
            msg = new ActionMessage("error.input.tab.text", msgSubject);
            StrutsUtil.addMessage(errors, msg, "title");
            error = true;
        }

        //JIS第2水準チェック
        if (!error && !GSValidateUtil.isGsJapaneaseString(title)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(title);
            msg = new ActionMessage("error.input.njapan.text", msgSubject,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "title");
            error = true;
        }
        return errors;
    }

    /**
     * <br>[機  能] 件名の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] システム通知メールを送信する際に使用します
     *
     * @param title 件名
     * @param reqMdl リクエスト情報
     * @return errMsg エラーメッセージ
     */
    public static String validateSmlTitle(String title,
            RequestModel reqMdl) {

        String errMsg = "";
        GsMessage gsMsg = new GsMessage(reqMdl);

        //未入力チェック
        if (StringUtil.isNullZeroString(title)) {
            errMsg = gsMsg.getMessage("sml.143");
            return errMsg;
        }
        //MAX桁チェック
        if (title.length() > GSConstCommon.MAX_LENGTH_SMLTITLE) {
            errMsg = gsMsg.getMessage("sml.142");
            return errMsg;
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(title)) {
            errMsg = gsMsg.getMessage("sml.141");
            return errMsg;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(title)) {
            errMsg = gsMsg.getMessage("sml.145");
            return errMsg;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(title)) {
            errMsg = gsMsg.getMessage("sml.187");
            return errMsg;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(title)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(title);
            errMsg = gsMsg.getMessage("sml.144") + nstr + "]";
            return errMsg;
        }
        return errMsg;
    }

    /**
     * <br>[機  能] マークの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] システム通知メールを送信する際に使用します
     *
     * @param mark マーク区分
     * @param reqMdl リクエスト情報
     * @return errMsg エラーメッセージ
     */
    public static String validateSmlMark(int mark,
            RequestModel reqMdl) {

        String errMsg = "";

        //正当性評価
        switch (mark) {
            case GSConstSmail.MARK_KBN_NONE:
            case GSConstSmail.MARK_KBN_TEL:
            case GSConstSmail.MARK_KBN_INP:
            case GSConstSmail.MARK_KBN_SMAILY:
            case GSConstSmail.MARK_KBN_WORRY:
            case GSConstSmail.MARK_KBN_ANGRY:
            case GSConstSmail.MARK_KBN_SADRY:
            case GSConstSmail.MARK_KBN_BEER:
            case GSConstSmail.MARK_KBN_HART:
            case GSConstSmail.MARK_KBN_ZASETSU:
                break;
            default:
                GsMessage gsMsg = new GsMessage(reqMdl);
                errMsg = gsMsg.getMessage("sml.126");
        }

        return errMsg;
    }

    /**
     * <br>[機  能] 本文の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mode チェックモード
     * @param body 本文
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @return ActionErrors
     */
    public static ActionErrors validateSmlBody(ActionErrors errors, int mode, String body,
            RequestModel reqMdl, String appRootPath) {

        ActionMessage msg = null;
        boolean error = false;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgBody = gsMsg.getMessage("cmn.body");

        //未入力チェック
        if (StringUtil.isNullZeroString(body)) {
            if (mode == Sml020Form.VALIDATE_MODE_SAVE) {
                return errors;
            }
            msg = new ActionMessage("error.input.required.text", msgBody);
            StrutsUtil.addMessage(errors, msg, "body");
            error = true;
        }

        //MAX桁チェック
        int maxlength = 0;
        maxlength = SmlCommonBiz.getBodyLimitLength(appRootPath);
        if (maxlength == -100) {
            msg = new ActionMessage("error.can.not.conffile.open.error");
            StrutsUtil.addMessage(errors, msg, "body");
            error = true;
        } else if (maxlength == -101) {
            msg = new ActionMessage("error.can.not.conffile.read.error");
            StrutsUtil.addMessage(errors, msg, "body");
            error = true;
        } else if (maxlength != 0) {
            if (!error && body.length() > maxlength) {
                msg = new ActionMessage("error.input.length.textarea", msgBody, maxlength);
                StrutsUtil.addMessage(errors, msg, "body");
                error = true;
            }
        }

        //スペース、改行のみチェック
        if (!error && ValidateUtil.isSpaceOrKaigyou(body)) {
            msg = new ActionMessage("error.input.spase.cl.only", msgBody);
            StrutsUtil.addMessage(errors, msg, "body");
            error = true;
        }

        //JIS第2水準チェック
        if (!error && !GSValidateUtil.isGsJapaneaseStringTextArea(body)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(body);
            msg = new ActionMessage("error.input.njapan.text", msgBody, nstr);
            StrutsUtil.addMessage(errors, msg, "body");
            error = true;
        }

        return errors;
    }

    /**
     * <br>[機  能] 本文の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] システム通知メールを送信する場合に使用します
     *
     * @param body 本文
     * @param reqMdl リクエスト情報
     * @return errMsg エラーメッセージ
     */
    public static String validateSmlBody(String body,
            RequestModel reqMdl) {

        String errMsg = "";

        GsMessage gsMsg = new GsMessage(reqMdl);
        //未入力チェック
        if (StringUtil.isNullZeroString(body)) {
            errMsg = gsMsg.getMessage("sml.162");
            return errMsg;
        }
        //MAX桁チェック
        if (body.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            errMsg = gsMsg.getMessage("sml.161");
            return errMsg;
        }

        //スペース、改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(body)) {
            errMsg = gsMsg.getMessage("sml.160");
            return errMsg;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(body)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(body);
            errMsg = gsMsg.getMessage("sml.163", new String[] {nstr});
            return errMsg;
        }

        return errMsg;
    }

    /**
     * <br>[機  能] 検索キーワードの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 検査値
     * @param reqMdl リクエスト情報
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchKeyword(
            ActionErrors errors,
            String value,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        int maxLength = 100;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String searchkey = gsMsg.getMessage("cmn.search.keyword");
        //未入力はOK
        if (StringUtil.isNullZeroString(value)) {
            return false;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(value)) {
            msg = new ActionMessage("error.input.spase.only", searchkey);
            StrutsUtil.addMessage(errors, msg, "sml090KeyWord__");
            return true;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(value)) {
            msg = new ActionMessage("error.input.spase.start", searchkey);
            StrutsUtil.addMessage(errors, msg, "sml090KeyWord__");
            return true;
        }

        if (value.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage(
                    "error.input.length.text", searchkey);
            StrutsUtil.addMessage(errors, msg, "sml090KeyWord__");
            return true;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(value);
            msg = new ActionMessage("error.input.njapan.text", searchkey,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "sml090KeyWord__");
            return true;
        }


        return false;
    }


    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 検査値
     * @param targets [0]:title [1]:body
     * @param reqMdl リクエスト情報
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchTarget(
            ActionErrors errors,
            String value,
            String[] targets,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String searchkey = gsMsg.getMessage("cmn.search2");
        if ((targets == null || targets.length <= 0)
                && !StringUtil.isNullZeroString(value)) {

            //キーワード、ターゲット論理チェック
            msg = new ActionMessage("error.input.required.text", searchkey);
            StrutsUtil.addMessage(errors, msg, "target");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 検索ソート順の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param sortKey1 検査ソートKey1
     * @param sortKey2 検査ソートKey2
     * @param reqMdl リクエスト情報
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchSortOrder(
            ActionErrors errors,
            String sortKey1,
            String sortKey2,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String sortkey = gsMsg.getMessage("cmn.sortkey");
        if (sortKey1.equals(sortKey2)) {
            //同一キー指定チェック
            msg = new ActionMessage("error.select.dup.list", sortkey);
            StrutsUtil.addMessage(errors, msg, "sml090SvSearchOrderKey__");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] テキストボックスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text",
                    targetJp, nstr);
            StrutsUtil.addMessage(errors, msg,
                                     fieldFix + "error.input.njapan.text");
            return true;
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (ValidateUtil.isSpace(target)) {
            //スペースのみ
            String msgKey = "error.input.spase.only";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペース
            String msgKey = "error.input.spase.start";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(target)) {
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + msgKey);
            return true;
        }

        //入力エラー無し
        return false;
    }


    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetJp, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <p>アカウント登録のチェックを行う(CSV用)
     * @param req リクエスト
     * @param errors ActionErrors
     * @param target 対象
     * @param chkFlgName チェックするフラグの名称
     * @param targetJp チェック対象名(日本語)
     * @param chkFlg1 チェックフラグ1
     * @param chkFlg2 チェックフラグ2
     * @return ActionErrors
     */
    public static ActionErrors validateCsvAccountFlg(
            HttpServletRequest req,
            ActionErrors errors,
            String target,
            String chkFlgName,
            String targetJp,
            int chkFlg1,
            int chkFlg2) {

        ActionMessage msg = null;
        String eprefix = chkFlgName + ".";

        GsMessage gsMsg = new GsMessage();
        if (StringUtil.isNullZeroString(target) || ValidateUtil.isSpace(target)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    targetJp);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (!GSValidateUtil.isNumber(target)) {
            //数値チェック
            msg = new ActionMessage("error.input.comp.text",
                    targetJp,
                    gsMsg.getMessage(req, "cmn.numbers"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
        } else if (!target.equals(String.valueOf(chkFlg1))
                && !target.equals(String.valueOf(chkFlg2))) {

            //指定された値かをチェック
            String[] params = {String.valueOf(chkFlg1), String.valueOf(chkFlg2)};
            msg = new ActionMessage("error.input.comp.text",
                    targetJp, gsMsg.getMessage(req, "wml.134", params));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
        }

        return errors;
    }


    /**
     * <p>ユーザＩＤの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param index 番号
     * @return ActionErrors
     */
    public static ActionErrors validateCsvUserId(
            ActionErrors errors,
            String userid,
            String targetName,
            String targetJp,
            int index) {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";

        if (!(StringUtil.isNullZeroString(userid))) {
            if (userid.length() < GSConstSmail.MINLEN_USERID
                    || userid.length() > GSConstSmail.MAXLEN_USERID) {
                //MIN,MAX桁チェック
                msg = new ActionMessage("error.input.length2.text",
                        targetJp + index,
                        GSConstSmail.MINLEN_USERID, GSConstSmail.MAXLEN_USERID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
            } else if (!GSValidateUtil.isUseridFormat(userid)) {
                //ユーザＩＤフォーマットチェック
                msg = new ActionMessage("error.input.format.text",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            }
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの存在チェックを行う(CSV取込み時)
     * <p>自分のユーザIDは除く
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param con DBコネクション
     * @param index 番号
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateCsvUserIdExist(
            ActionErrors errors,
            String userid,
            String targetName,
            String targetJp,
            Connection con,
            int index) throws SQLException {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";
        if (!(StringUtil.isNullZeroString(userid))) {
            //存在しない、またはシステムユーザである場合のエラーチェックする
            CmnUsrmDao dao = new CmnUsrmDao(con);
            CmnUsrmModel mdl = dao.getUserSid(userid);
            if (mdl == null || mdl.getUsrSid() <= GSConstUser.USER_RESERV_SID) {
                msg = new ActionMessage("error.not.exist.userid",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.not.exist.userid");
                return errors;
            }
        }
        return errors;
    }

    /**
     * <p>グループＩＤの入力チェックを行う
     * @param errors ActionErrors
     * @param grpId グループＩＤの値
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param index 番号
     * @return ActionErrors
     */
    public static ActionErrors validateGroupId(
            ActionErrors errors, String grpId,
            String targetName, String targetJp, int index) {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";
        if (!(StringUtil.isNullZeroString(grpId))) {
            if (grpId.length() > GSConstUser.MAX_LENGTH_GROUPID) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        targetJp + index, GSConstUser.MAX_LENGTH_GROUPID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            } else if (!GSValidateUtil.isOtheridFormat(grpId)) {
                //ユーザＩＤフォーマットチェック
                msg = new ActionMessage("error.input.format.text", targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            }
        }
        return errors;
    }

    /**
     *
     * <br>[機  能]グループIDの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param groupid グループID
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param index 番号
     * @param con コネクション
     * @return アクションエラー
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateGroupExist(
            ActionErrors errors,
            String groupid, String targetName,
            String targetJp, int index, Connection con)
            throws SQLException {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";
        if (!(StringUtil.isNullZeroString(groupid))) {
            CmnGroupmDao dao = new CmnGroupmDao(con);
            boolean ret = dao.existGroupidEdit(-1, groupid);
            if (!ret) {
                //存在しない場合のエラー
                msg = new ActionMessage("error.not.exist.userid",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.not.exist.userid");
            }
        }
        return errors;
    }

}