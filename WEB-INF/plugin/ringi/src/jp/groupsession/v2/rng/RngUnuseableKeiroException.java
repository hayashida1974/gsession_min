package jp.groupsession.v2.rng;

import java.util.Iterator;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

/**
 *
 * <br>[機  能] 利用不可能な経路設定によるエクセプション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngUnuseableKeiroException extends Exception {
    /** 理由表示文字列 */
    private String reason__;

    public RngUnuseableKeiroException(ActionErrors errors, MessageResources mres) {
        StringBuilder sb = new StringBuilder();
        @SuppressWarnings("rawtypes")
        Iterator it = errors.get();
        while (it.hasNext()) {
            ActionMessage error = (ActionMessage) it.next();
            sb.append(mres.getMessage(error.getKey(), error.getValues()));
            if (it.hasNext()) {
                sb.append("<br />");
            }
        }
        reason__ = sb.toString();
    }

    /**
     * <p>reason を取得します。
     * @return reason
     * @see jp.groupsession.v2.rng.RngUnuseableKeiroException#reason__
     */
    public String getReason() {
        return reason__;
    }

    /**
     * <p>reason をセットします。
     * @param reason reason
     * @see jp.groupsession.v2.rng.RngUnuseableKeiroException#reason__
     */
    public void setReason(String reason) {
        reason__ = reason;
    }
}
