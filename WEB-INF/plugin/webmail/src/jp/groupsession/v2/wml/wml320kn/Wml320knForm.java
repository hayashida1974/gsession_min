package jp.groupsession.v2.wml.wml320kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.wml320.Wml320Form;

/**
 * <br>[機  能] 個人設定 メール情報一括削除確認画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml320knForm extends Wml320Form {

    /** アカウント名 */
    private String wml320knAccount__ = null;
    /** ラベル名 */
    private String wml320knLabel__ = null;
    /** 日付From */
    private String wml320knFrom__ = null;
    /** 日付To */
    private String wml320knTo__ = null;

    /**
     * <p>wml320knAccount を取得します。
     * @return wml320knAccount
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knForm#wml320knAccount__
     */
    public String getWml320knAccount() {
        return wml320knAccount__;
    }
    /**
     * <p>wml320knAccount をセットします。
     * @param wml320knAccount wml320knAccount
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knForm#wml320knAccount__
     */
    public void setWml320knAccount(String wml320knAccount) {
        wml320knAccount__ = wml320knAccount;
    }
    /**
     * <p>wml320knLabel を取得します。
     * @return wml320knLabel
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knForm#wml320knLabel__
     */
    public String getWml320knLabel() {
        return wml320knLabel__;
    }
    /**
     * <p>wml320knLabel をセットします。
     * @param wml320knLabel wml320knLabel
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knForm#wml320knLabel__
     */
    public void setWml320knLabel(String wml320knLabel) {
        wml320knLabel__ = wml320knLabel;
    }
    /**
     * <p>wml320knFrom を取得します。
     * @return wml320knFrom
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knParamModel#wml320knFrom__
     */
    public String getWml320knFrom() {
        return wml320knFrom__;
    }
    /**
     * <p>wml320knFrom をセットします。
     * @param wml320knFrom wml320knFrom
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knParamModel#wml320knFrom__
     */
    public void setWml320knFrom(String wml320knFrom) {
        wml320knFrom__ = wml320knFrom;
    }
    /**
     * <p>wml320knTo を取得します。
     * @return wml320knTo
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knParamModel#wml320knTo__
     */
    public String getWml320knTo() {
        return wml320knTo__;
    }
    /**
     * <p>wml320knTo をセットします。
     * @param wml320knTo wml320knTo
     * @see jp.groupsession.v2.wml.wml320kn.Wml320knParamModel#wml320knTo__
     */
    public void setWml320knTo(String wml320knTo) {
        wml320knTo__ = wml320knTo;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("wml320account", getWml320svAccount());
    }

    /**
     * <br>[機  能] 入力チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @param con Connection
     * @param reqMdl RequestModel
     * @param buMdl BaseUserModel
     * @return ActionErrors
     * @throws SQLException SQLException
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con,
            RequestModel reqMdl, BaseUserModel buMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage();
        ActionErrors errors = new ActionErrors();

        //アカウント
        checkAccount(errors,
                    getWml320svAccount(),
                    buMdl.getUsrsid(),
                    gsMsg.getMessage("cmn.account"),
                    con);

        //ラベル
        if (getWml320svLabel() > 0) {
            checkLabel(errors,
                    getWml320svLabel(),
                    getWml320svAccount(),
                    gsMsg.getMessage("cmn.label"), con);
        }

        return errors;
    }
}
