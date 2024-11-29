package jp.groupsession.v2.adr;

import java.sql.Connection;
import java.sql.SQLException;
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
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報の入力チェックを行うクラス(CSV用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateAdrCsv {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSValidateAdrCsv.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set RequestModel
     * @param reqMdl RequestModel
     */
    public GSValidateAdrCsv(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
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
                        GSConstUser.MAX_LENGTH_YAKUSHOKU);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");
            } else {

                //メールフォーマットチェック
                if (!GSValidateUtil.isMailFormat(mail)) {
                    msg = new ActionMessage("error.input.format.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.format.text");
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
        String textTkfk = plusName + gsMsg.getMessage("cmn.prefectures");
        if (!StringUtil.isNullZeroString(tdfkCd)) {
            if (tdfkCd.length() > 2) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text", title + textTkfk,
                        GSConstUser.MAX_LENGTH_TDFK);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else {
                //数字チェック
                if (!GSValidateUtil.isNumber(tdfkCd)) {
                    String textFormat = gsMsg.getMessage("user.src.58");
                    msg = new ActionMessage("error.input.comp.text",
                            title + textTkfk, textFormat);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.comp.text");
                } else {
                    //存在チェック
                    GSValidateAdr gsValidateUser = new GSValidateAdr(reqMdl__);
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
     * <p>都道府県の入力チェックを行う(CSV用、都道府県名用)
     * @param errors ActionErrors
     * @param tdfkName 都道府県名
     * @param line 行数
     * @param con コネクション
     * @param plusName 項目名の補足文字列
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCsvTdfkName(
            ActionErrors errors,
            String tdfkName,
            long line,
            Connection con,
            String plusName) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        ActionMessage msg = null;
        String eprefix = line + "tdfk.";
        String title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)});
        //都道府県
        plusName = NullDefault.getString(plusName, "");
        String textTkfk = plusName + gsMsg.getMessage("cmn.prefectures");

        int errorCount = 0;

        //テキスト入力チェック
        if (!AdrValidateUtil.validateTextField(errors,
                tdfkName,
                eprefix,
                title + textTkfk,
                5,
                false)) {
            errorCount++;
        }

        //存在チェック
        if (errorCount == 0 && !StringUtil.isNullZeroString(tdfkName)) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
            List<CmnTdfkModel> tdfkList = tdfkDao.select();
            boolean existFlg = false;

            for (CmnTdfkModel mdl : tdfkList) {
                String existTdfk = mdl.getTdfName();
                if (existTdfk.equals(tdfkName)) {
                    existFlg = true;
                    break;
                }
            }

            if (!existFlg) {
                msg = new ActionMessage("search.notfound.tdfkcode", title + textTkfk);
                StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
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
        if (StringUtil.isNullZeroString(koukaiFlg) || ValidateUtil.isSpace(koukaiFlg)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textLine + chkFlgName);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (!GSValidateUtil.isNumber(koukaiFlg)) {
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
     * @return ActionErrors
     */
    public ActionErrors validateCsvGroupId(ActionErrors errors,
                                             String groupid, long num) {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "groupid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //グループID
        String textGroupId = gsMsg.getMessage("cmn.group.id");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        if (StringUtil.isNullZeroString(groupid)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    textLine + textGroupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (groupid.length() > GSConstUser.MAX_LENGTH_GROUPID) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textLine + textGroupId, GSConstUser.MAX_LENGTH_GROUPID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
        } else if (!GSValidateUtil.isUseridFormat(groupid)) {
            //グループＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    textLine + textGroupId);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
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
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCsvGroupExist(ActionErrors errors,
                String groupid, long num, Connection con) throws SQLException {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "groupid.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        CmnGroupmDao dao = new CmnGroupmDao(con);
        boolean ret = dao.existGroupEdit(0, groupid);
        if (!ret) {
            if (!groupid.equals(GSConstUser.USER_KANRI_ID)) {
                 //未登録エラー
                 msg = new ActionMessage(
                     "error.edit.no.group2", textLine);
                 StrutsUtil.addMessage(errors, msg, eprefix + "error.edit.no.group2");
             } else {
                 //管理者グループエラー
                 msg = new ActionMessage(
                     "error.input.double.kanri", textLine);
                 StrutsUtil.addMessage(errors, msg, eprefix + "error.input.double.kanri");
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
}