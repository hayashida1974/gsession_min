package jp.groupsession.v2.cmn.biz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] jspで使用するデータを取得するビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class JspBiz {

    /**
     * <p>テーマ情報を返す
     * @param req リクエスト
     * @return RequestModel
     */
    public static boolean getTheme(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        String path = "";
        if (session != null) {
            Object tmp = session.getAttribute(GSConst.SESSION_KEY);
            if (tmp != null) {
                BaseUserModel smodel = (BaseUserModel) tmp;
                path = smodel.getCtmPath();
            }
        }
        if (path == null || path.length() <= 0) {
            return true;
        }

        if (path.startsWith("common/css/theme_classic")) {
            return false;
        }

        return true;
    }
}
