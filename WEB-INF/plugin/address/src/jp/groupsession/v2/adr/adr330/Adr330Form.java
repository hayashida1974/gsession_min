package jp.groupsession.v2.adr.adr330;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Form;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
/**
 *
 * <br>[機  能] 管理者設定アドレス管理画面フォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr330Form extends Adr010Form {
    /** 選択アドレスチェック */
    private String[] adr330selectSid__;
    /**
     * <p>adr330selectSid を取得します。
     * @return adr330selectSid
     */
    public String[] getAdr330selectSid() {
        return adr330selectSid__;
    }

    /**
     * <p>adr330selectSid をセットします。
     * @param adr330selectSid adr330selectSid
     */
    public void setAdr330selectSid(String[] adr330selectSid) {
        adr330selectSid__ = adr330selectSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //氏名 姓
        String nameSei = gsMsg.getMessage(req, "cmn.name") + " "
                         + gsMsg.getMessage(req, "cmn.lastname");
        AdrValidateUtil.validateTextField(errors, getAdr330searchBean().getUnameSei(), "unameSei",
                nameSei, GSConstAddress.MAX_LENGTH_NAME_SEI, false);
        //氏名 名
        String nameMei = gsMsg.getMessage(req, "cmn.name") + " "
                         + gsMsg.getMessage(req, "cmn.name3");
        AdrValidateUtil.validateTextField(errors, getAdr330searchBean().getUnameMei(), "unameMei",
                nameMei, GSConstAddress.MAX_LENGTH_NAME_MEI, false);
        //氏名カナ 姓
        String nameSeiKn = gsMsg.getMessage(req, "cmn.name.kana") + " "
                           + gsMsg.getMessage(req, "cmn.lastname");
        AdrValidateUtil.validateTextFieldKana(errors,
                getAdr330searchBean().getUnameSeiKn(), "unameSeiKn",
                                            nameSeiKn,
                                            GSConstAddress.MAX_LENGTH_NAME_SEI_KN, false);
        //氏名カナ 名
        String nameMeiKn = gsMsg.getMessage(req, "cmn.name.kana") + " "
                           + gsMsg.getMessage(req, "cmn.name3");
        AdrValidateUtil.validateTextFieldKana(errors,
                getAdr330searchBean().getUnameMeiKn(), "unameMeiKn",
                                            nameMeiKn,
                                            GSConstAddress.MAX_LENGTH_NAME_MEI_KN, false);
        //会社名
        AdrValidateUtil.validateTextField(errors,
                getAdr330searchBean().getCoName(), "coName",
                gsMsg.getMessage(req, "cmn.company.name"),
                GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);

      //所属
        AdrValidateUtil.validateTextField(errors,
                getAdr330searchBean().getSyozoku(), "syozoku",
                gsMsg.getMessage(req, "cmn.affiliation"),
                GSConstAddress.MAX_LENGTH_SYOZOKU, false);
        //E-MAIL
        AdrValidateUtil.validateMail(errors, getAdr330searchBean().getMail(), 0, req);

        //マイグループかつユーザ選択なし 担当者
        if (AdrCommonBiz.isMyGroupSid(getAdr010detailTantoGroup())
                && getAdr010detailTantoUser() == -1) {
            AdrValidateUtil.validateMyGroupUser(errors, gsMsg.getMessage("cmn.staff"));
        }


        return errors;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        //削除チェックボックス
        msgForm.addHiddenParam("adr330selectSid", adr330selectSid__);

    }
    /**
     * <br>[機  能] 削除ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateSelectCheck010(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        //削除するアドレス
        String msgDelAdr = gsMsg.getMessage(req, "address.src.3");

        //未選択チェック
        if (adr330selectSid__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text", msgDelAdr);
            StrutsUtil.addMessage(errors, msg, "adrSid");
        } else {
            if (adr330selectSid__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text", msgDelAdr);
                StrutsUtil.addMessage(errors, msg, "adrSid");
            }
        }
        return errors;
    }

}
