package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.GroupDao;

/**
 * <br>[機  能] 掲示板の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateBulletin {

    /**
     * <br>[機  能] タイトルの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param title タイトル
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @return ActionErrors
     */
    public static ActionErrors validateTitle(
        ActionErrors errors,
        String title,
        String checkObject,
        int maxLength) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(title)) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        //MAX桁チェック
        if (title.length() > maxLength) {
            msg = new ActionMessage(
                    "error.input.length.text", checkObject, String.valueOf(maxLength));
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(title)) {
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(title)) {
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(title)) {
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(title)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(title);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        return errors;
    }

    /**
     * <br>[機  能] 内容の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 内容
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @param soukouFlg 草稿モード
     * @return ActionErrors
     */
    public static ActionErrors validateValue(
        ActionErrors errors,
        String value,
        String checkObject,
        int maxLength,
        boolean soukouFlg) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(value)) {
            // 草稿保存の場合
            if (soukouFlg) {
                return errors;
            }
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        //MAX桁チェック
        if (value.length() > maxLength) {
            msg = new ActionMessage("error.input.length.textarea", checkObject, maxLength);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペース(改行)のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(value)) {
            msg = new ActionMessage("error.input.spase.cl.only", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        return errors;
    }

    /**
     * <br>[機  能] グループ所属チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param groupSid グループSID
     * @param userSid ユーザSID
     * @param checkObject チェック対象
     * @param con Connection
     * @return ActionErrors
     * @throws SQLException SQL例外
     */
    public static ActionErrors validateIsBelongGroup(
        ActionErrors errors,
        Connection con,
        String checkObject,
        int groupSid,
        int userSid
        ) throws SQLException {
        ActionMessage msg = null;
        GroupDao grpDao = new GroupDao(con);
        if (!grpDao.isBelong(userSid, groupSid)) {
            msg = new ActionMessage("error.select3.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, "contributor");
        }
        return errors;
    }


    /**
     * <br>[機  能] 投稿者変更不可確認
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param groupSid グループSID
     * @param postSid 投稿SID
     * @param checkObject チェック対象
     * @param con Connection
     * @return ActionErrors
     * @throws SQLException SQL例外
     */
    public static ActionErrors validateNotChangeGroup(
        ActionErrors errors,
        Connection con,
        String checkObject,
        int groupSid,
        int postSid
        ) throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();

        BulletinDspModel mdl = bbsBiz.getWriteData(con, postSid);
        ActionMessage msg = null;
        if (groupSid != mdl.getGrpSid()) {
            msg = new ActionMessage("error.select3.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, "contributor");
        }
        return errors;
    }


}
