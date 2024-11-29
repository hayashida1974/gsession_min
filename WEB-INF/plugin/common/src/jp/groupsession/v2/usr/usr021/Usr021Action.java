package jp.groupsession.v2.usr.usr021;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.usr.usr020.Usr020Form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] グループ選択(チェックボックス選択方式)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr021Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr021Action.class);

    /**
     * <p>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        Usr020Form usr020Form = (Usr020Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        forward = __doListView(map, usr020Form, req, res, con);
        log__.debug("END");
        return forward;
    }

    /**
     * <p>グループリスト表示(ラジオ選択ALL)
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doListView(ActionMapping map, Usr020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);

        //
        ActionForward forward = null;
//        GroupDao dao = new GroupDao(con);
//        ArrayList<GroupModel> tree = dao.getGroupTree(null);
        GroupBiz grpBiz = new GroupBiz();
        ArrayList<GroupModel> tree = grpBiz.getGroupTree(con);

//        String type = NullDefault.getString(req.getParameter("listType"), "");
        String type = "checkbox";
        String level = NullDefault.getString(req.getParameter("selectLevel"), "10");
        String root = NullDefault.getString(req.getParameter("dspRoot"), "0");
        String checked = NullDefault.getString(req.getParameter("checkGroup"), "");

        form.setListType(type);
        form.setSelectLevel(level);
        form.setDspRoot(root);
        form.setCheckGroup(checked);
        form.setGroupList(tree);

        con.setAutoCommit(false);

        //
        forward = map.getInputForward();
        return forward;
    }
}