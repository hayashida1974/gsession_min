package jp.groupsession.v2.bbs.bbs190;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 掲示板 フォーラム選択(ラジオボタン選択方式)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs190Action extends AbstractBulletinAdminAction {

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        ActionForward forward = null;

        Bbs190Form bbs190Form = (Bbs190Form) form;
         //コマンドパラメータ取得
         Bbs190Biz bbs190Biz = new Bbs190Biz(con);
         forward = bbs190Biz.doListView(map, bbs190Form, req, res);
         return forward;
     }

}