package jp.groupsession.v2.adr;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateAdr {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSValidateAdr.class);
    /** リクエスト */
    protected RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public GSValidateAdr(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>郵便番号の入力チェックを行う
     * @param errors ActionErrors
     * @param post 上３桁-下４桁
     * @param line 行数
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 例外処理
     */
    public ActionErrors validateCsvPostNum(ActionErrors errors,
            String post, long line, RequestModel reqMdl) throws Exception {

        return validateCsvPostNum(errors, post, line, reqMdl, "");
    }

    /**
     * <p>郵便番号の入力チェックを行う
     * @param errors ActionErrors
     * @param post 上３桁-下４桁
     * @param line 行数
     * @param reqMdl RequestModel
     * @param plusName 項目名の補足文字列
     * @return ActionErrors
     * @throws Exception 例外処理
     */
    public ActionErrors validateCsvPostNum(ActionErrors errors,
            String post, long line, RequestModel reqMdl, String plusName) throws Exception {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        plusName = NullDefault.getString(plusName, "");
        String textFormat = gsMsg.getMessage("user.src.56");
        //半角数字
        String textNumbers3 = gsMsg.getMessage("user.src.54");
        //郵便番号
        String textPostCode = plusName + gsMsg.getMessage("cmn.postalcode");
        //郵便番号上３桁
        String textPostNum3 = plusName + gsMsg.getMessage("user.src.30");
        //郵便番号下４桁
        String textPostNum4 = plusName + gsMsg.getMessage("user.src.31");
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
     * <p>カテゴリーの存在チェックを行う
     * @param errors ActionErrors
     * @param con コネクション
     * @param alcSid カテゴリSID
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateCategoryExist(
            ActionErrors errors, Connection con, int alcSid)
        throws SQLException {

        AdrCommonBiz biz = new AdrCommonBiz();
        //カテゴリが存在しない場合
        if (!biz.isCheckExistAdrCategory(con, alcSid)) {
            ActionMessage msg = null;
            String eprefix = "select.category.";
            msg = new ActionMessage("error.none.category.data");
            StrutsUtil.addMessage(errors, msg, eprefix + "error.none.category.data");
        }

        return errors;
    }

}
