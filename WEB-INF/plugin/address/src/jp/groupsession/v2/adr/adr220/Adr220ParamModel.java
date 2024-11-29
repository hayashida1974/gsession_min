package jp.groupsession.v2.adr.adr220;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.adr210.Adr210ParamModel;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能]アドレス帳 役職登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr220ParamModel extends Adr210ParamModel {

    /** 役職名 */
    private String adr220yksName__;
    /** 備考 */
    private String adr220bikou__;

    /**
     * <p>adr220yksName を取得します。
     * @return adr220yksName
     */
    public String getAdr220yksName() {
        return adr220yksName__;
    }
    /**
     * <p>adr220yksName をセットします。
     * @param adr220yksName adr220yksName
     */
    public void setAdr220yksName(String adr220yksName) {
        adr220yksName__ = adr220yksName;
    }
    /**
     * <p>adr220bikou を取得します。
     * @return adr220bikou
     */
    public String getAdr220bikou() {
        return adr220bikou__;
    }
    /**
     * <p>adr220bikou をセットします。
     * @param adr220bikou adr220bikou
     */
    public void setAdr220bikou(String adr220bikou) {
        adr220bikou__ = adr220bikou;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateAdr220(Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //役職名
        AdrValidateUtil.validateTextField(
                errors,
                adr220yksName__,
                "adr220yksName",
                gsMsg.getMessage(req, "cmn.job.title"),
                20,
                true);

        //備考
        AdrValidateUtil.validateTextAreaField(
                errors,
                adr220bikou__,
               "adr220bikou",
                gsMsg.getMessage(req, "cmn.memo"),
                300,
                false);

        int editPosSid = getAdr210EditPosSid();
        AdrPositionDao apDao = new AdrPositionDao(con);
        boolean existFlg = apDao.isExistPositionName(adr220yksName__, editPosSid);

        if (existFlg) {
            ActionMessage msg =
                new ActionMessage("error.input.exist.data",
                        gsMsg.getMessage(req, "cmn.job.title"));
            StrutsUtil.addMessage(errors, msg, "yakusyoku_name");
        }

        return errors;
    }
}