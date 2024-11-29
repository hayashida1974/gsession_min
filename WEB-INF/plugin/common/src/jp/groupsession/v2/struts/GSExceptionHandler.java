package jp.groupsession.v2.struts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.chain.commands.InvalidPathException;
import org.apache.struts.config.ExceptionConfig;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.biz.CloseableThreadName;
import jp.groupsession.v2.cmn.exception.GSInvalidPathException;

/**
 * <br>[機  能] AbstractGsActionでキャッチできないエラー発生時の処理
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class GSExceptionHandler extends ExceptionHandler {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSExceptionHandler.class);

    @Override
    public ActionForward execute(Exception e,
            ExceptionConfig config,
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException {

        ActionForward forward = null;
        IGsResourceManager rsm = GroupSession.getResourceManager();
        String domain = rsm.getDomain(req);
        try (CloseableThreadContext.Instance ctc
                = CloseableThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, domain);
                CloseableThreadName tnc = CloseableThreadName.setName("GSExceptionHandler"
                        + "-" + System.currentTimeMillis()
                        + "-" + domain
                        + "-" + Thread.currentThread().getId());
                ) {
            //処理の継続が不可能な場合
            try {
                if (e instanceof java.lang.IllegalStateException) {
                    //「レスポンスをコミットした後でセッションを作成できません」のエラー対応（処理の継続不可）
                    log__.error("予期せぬエラー(java.lang.IllegalStateException)", e);
                    return null;
                }
                if (e instanceof InvalidPathException) {
                    e = new GSInvalidPathException(
                            (InvalidPathException) e, req.getRequestURL().toString());
                }
            } catch (Throwable e1) {
            }

            try {
                forward = super.execute(e, config, map,
                        form, req, res);
                log__.error("予期せぬエラー", e);
                req.setAttribute(GSConst.ERROR_KEY, e);
            } catch (Throwable e2) {
            }
        }
        return forward;
    }
}
