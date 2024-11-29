package jp.groupsession.v2.man;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 役職管理の入力チェックを行うクラス(CSV用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidatePositionCsv extends GSValidateMain {

    /**
     * <br>[機  能] 役職名の入力チェックを行う(CSV取込み時)
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param posCode 役職名
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvPosCode(
            ActionErrors errors,
            RequestModel reqMdl,
            String posCode, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "poscode.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String[] lineNo = new String[] {String.valueOf(num)};

        if (StringUtil.isNullZeroString(posCode) || ValidateUtil.isSpace(posCode)) {
            // 未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");

        } else if (ValidateUtil.isSpaceStart(posCode)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (posCode.length() > GSConstMain.MAX_LENGTH_POS_CODE) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE),
                    GSConstMain.MAX_LENGTH_POS_CODE);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(posCode)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(posCode);
            msg = new ActionMessage("error.input.njapan.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE), nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        } else if (!GSValidateUtil.isOtheridFormat(posCode)) {
            //ID(コード)フォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                    + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.format.text");
        }
        return errors;
    }

    /**
     * <br>[機  能] 役職名の入力チェックを行う(CSV取込み時)
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param posName 役職名
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvPosName(
            ActionErrors errors,
            RequestModel reqMdl,
            String posName, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "posname.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String[] lineNo = new String[] {String.valueOf(num)};

        if (StringUtil.isNullZeroString(posName) || ValidateUtil.isSpace(posName)) {
            // 未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");

        //タブ文字チェック
        } else if (ValidateUtil.isTab(posName)) {
            msg = new ActionMessage("error.input.tab.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME));

            errors.add("error.input.tab.text", msg);

        } else if (ValidateUtil.isSpaceStart(posName)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (posName.length() > GSConstMain.MAX_LENGTH_POS) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME),
                    GSConstMain.MAX_LENGTH_POS);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(posName)) {
            // 利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(posName);
            msg = new ActionMessage("error.input.njapan.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME), nstr);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.njapan.text");
        }
        return errors;
    }

    /**
     * <br>[機  能] 備考の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param biko 備考
     * @param checkObject 項目名
     * @param line 行数
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateBiko(
        ActionErrors errors,
        String biko,
        String checkObject,
        long line,
        RequestModel reqMdl) {
        ActionMessage msg = null;
        String eprefix = line + checkObject + "cmt.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String text = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(line)})
                        + checkObject;


        if (!StringUtil.isNullZeroString(biko)) {
            if (biko.length() > GSConstMain.MAX_LENGTH_POS_CMT) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                         text,
                                         GSConstMain.MAX_LENGTH_POS_CMT);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            //スペース(改行)のみチェック
            } else if (ValidateUtil.isSpaceOrKaigyou(biko)) {
                    msg = new ActionMessage("error.input.spase.cl.only",
                            text,
                            GSConstMain.MAX_LENGTH_POS_CMT);
                    StrutsUtil.addMessage(errors, msg, checkObject);

            //タブ文字チェック
            } else if (ValidateUtil.isTab(biko)) {
                msg = new ActionMessage("error.input.tab.text",
                            text,
                            GSConstMain.MAX_LENGTH_POS_CMT);
                errors.add("error.input.tab.text", msg);

            } else if (!GSValidateUtil.isGsJapaneaseString(biko)) {
                // 利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(biko);
                msg = new ActionMessage("error.input.njapan.text", text, nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 表示順の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param posSort 表示順
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validatePosSort(
            ActionErrors errors,
            RequestModel reqMdl,
            String posSort, long num) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "posname.";
        String[] lineNo = new String[] {String.valueOf(num)};

        if (StringUtil.isNullZeroString(posSort) || ValidateUtil.isSpace(posSort)) {
            // 未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_SORT));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");

        } else if (!ValidateUtil.isNumber(posSort)) {
            //数字チェック
            msg = new ActionMessage("error.input.number.hankaku",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_SORT));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.number.hankaku");

        } else if (posSort.length() > GSConstMain.MAX_LENGTH_POS_SORT) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_POS_SORT),
                    GSConstMain.MAX_LENGTH_POS_SORT);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
        }
        return errors;
    }

    /**
     * <br>[機  能] 役職コードの重複登録チェックを行う(CSV取込み時)
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param posCode 役職名
     * @param num 行数
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateCsvPosCodeDouble(ActionErrors errors,
                RequestModel reqMdl,
                String posCode, long num, Connection con) throws SQLException {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "poscode.";
        CmnPositionDao dao = new CmnPositionDao(con);
        boolean ret = dao.isExistPositionCode(posCode, 0);

        if (ret) {
            //重複エラー
            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = new ActionMessage(
                    "error.input.timecard.exist",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.timecard.exist.poscode");
        }
        return errors;
    }

    /**
     * <br>[機  能] 役職名の重複登録チェックを行う(CSV取込み時)
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param posName 役職名
     * @param num 行数
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateCsvPosDouble(ActionErrors errors,
                RequestModel reqMdl,
                String posName, long num, Connection con) throws SQLException {
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "posname.";
        CmnPositionDao dao = new CmnPositionDao(con);
        boolean ret = dao.isExistPositionName(posName, 0);
        if (ret) {
            //重複エラー
            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = new ActionMessage(
                    "error.input.timecard.exist",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.timecard.exist");
        }
        return errors;
    }

}