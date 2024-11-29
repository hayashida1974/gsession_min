package jp.groupsession.v2.adr.adr090;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.adr080.Adr080Form;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] アドレス帳 業種登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr090Form extends Adr080Form {

    //入力項目
    /** 業種名 */
    private String adr090atiName__;
    /** 備考 */
    private String adr090bikou__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateAdr090(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //業種名
        AdrValidateUtil.validateTextField(errors,
                                          adr090atiName__,
                                         "adr090atiName",
                                          gsMsg.getMessage(req, "address.101"),
                                          20,
                                          true);
        //備考
        AdrValidateUtil.validateTextAreaField(errors,
                                              adr090bikou__,
                                             "adr090bikou",
                                              gsMsg.getMessage(req, "cmn.memo"),
                                              300,
                                              false);
        return errors;
    }

    /**
     * <br>[機  能] 削除チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL例外発生
     */
    public ActionErrors deleteCheck(
            Connection con, RequestModel reqMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String fieldfix;

        //所属会社件数取得
        Adr090Biz biz = new Adr090Biz(reqMdl);
        int count = biz.getBelongCount(con, getAdr080EditAtiSid());
        if (count > 0) {
            //業種名取得
            AdrTypeindustryDao atiDao = new AdrTypeindustryDao(con);
            AdrTypeindustryModel atiModel = atiDao.select(getAdr080EditAtiSid());
            //メッセージ作成
            fieldfix = "adr080EditAtiSid" + ".";
            String msgKey = "error.duplication.type";
            msg = new ActionMessage(msgKey,
                    StringUtilHtml.transToHTmlPlusAmparsant(atiModel.getAtiName()), count);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
        }
        return errors;
    }

    /**
     * <p>adr090atiName を取得します。
     * @return adr090atiName
     */
    public String getAdr090atiName() {
        return adr090atiName__;
    }

    /**
     * <p>adr090atiName をセットします。
     * @param adr090atiName adr090atiName
     */
    public void setAdr090atiName(String adr090atiName) {
        adr090atiName__ = adr090atiName;
    }

    /**
     * <p>adr090bikou を取得します。
     * @return adr090bikou
     */
    public String getAdr090bikou() {
        return adr090bikou__;
    }

    /**
     * <p>adr090bikou をセットします。
     * @param adr090bikou adr090bikou
     */
    public void setAdr090bikou(String adr090bikou) {
        adr090bikou__ = adr090bikou;
    }
}