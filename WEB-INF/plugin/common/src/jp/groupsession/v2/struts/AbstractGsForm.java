package jp.groupsession.v2.struts;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionForm;

/**
 * <br>[機  能] 本システムで共通的に使用するフォームクラスです
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class AbstractGsForm extends ActionForm {
    /**
     * <p>メッセージを取得します
     * @param req リクエスト
     * @param msgKey メッセージキー
     * @return メッセージ
     */
    public String getInterMessage(HttpServletRequest req, String msgKey) {
        GsMessage msg = new GsMessage();
        return msg.getMessage(req, msgKey);
    }

    /**
     * <p>メッセージを取得します
     * @param req リクエスト
     * @param msgKey メッセージキー
     * @param param0 メッセージパラメータ
     * @return メッセージ
     */
    public String getInterMessage(HttpServletRequest req, String msgKey, Object param0) {
        GsMessage msg = new GsMessage();
        return msg.getMessage(req, msgKey, String.valueOf(param0));
    }

    /**
     * <p>メッセージを取得します
     * @param req リクエスト
     * @param msgKey メッセージキー
     * @param param0 メッセージパラメータ
     * @param param1 メッセージパラメータ
     * @return メッセージ
     */
    public String getInterMessage(HttpServletRequest req, String msgKey, Object param0,
            Object param1) {
        GsMessage msg = new GsMessage();
        return msg.getMessage(req, msgKey, String.valueOf(param0), String.valueOf(param1));
    }

    /**
     * <p>メッセージを取得します
     * @param req リクエスト
     * @param msgKey メッセージキー
     * @param param0 メッセージパラメータ
     * @param param1 メッセージパラメータ
     * @param param2 メッセージパラメータ
     * @return メッセージ
     */
    public String getInterMessage(HttpServletRequest req, String msgKey, Object param0,
            Object param1, Object param2) {
        GsMessage msg = new GsMessage();
        return msg.getMessage(
           req, msgKey, String.valueOf(param0), String.valueOf(param1), String.valueOf(param2));
    }

    /**
     * <p>メッセージを取得します
     * @param req リクエスト
     * @param msgKey メッセージキー
     * @param param0 メッセージパラメータ
     * @param param1 メッセージパラメータ
     * @param param2 メッセージパラメータ
     * @param param3 メッセージパラメータ
     * @return メッセージ
     */
    public String getInterMessage(HttpServletRequest req, String msgKey, Object param0,
            Object param1, Object param2, Object param3) {
        GsMessage msg = new GsMessage();
        return msg.getMessage(
                           req, msgKey, String.valueOf(param0), String.valueOf(param1),
                           String.valueOf(param2), String.valueOf(param3));
    }

}
