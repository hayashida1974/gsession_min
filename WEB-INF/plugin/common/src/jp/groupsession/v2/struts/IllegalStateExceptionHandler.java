package jp.groupsession.v2.struts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * <br>[機  能] IllegalStateException発生時の処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IllegalStateExceptionHandler extends ExceptionHandler {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IllegalStateExceptionHandler.class);

    @Override
    public ActionForward execute(Exception e,
            ExceptionConfig config,
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException {

        ActionForward forward = null;

        //処理の継続が不可能な場合
        try {
            if (e instanceof java.lang.IllegalStateException) {
                log__.error("予期せぬエラー(java.lang.IllegalStateException)", e);
                return null;
            }
        } catch (Throwable e1) {
        }
        return forward;
    }
}
