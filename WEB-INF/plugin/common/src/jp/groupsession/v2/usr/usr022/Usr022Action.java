package jp.groupsession.v2.usr.usr022;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.usr.usr020.Usr020Form;

/**
 * <br>[機  能] グループ選択(グループ名クリック方式)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr022Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr022Action.class);

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
    private ActionForward __doListView(ActionMapping map, Usr020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        GroupBiz grpBiz = new GroupBiz();

        String grpId = NullDefault.getString(form.getSearchGrpId(), "");
        String grpName = NullDefault.getString(form.getSearchGrpName(), "");

        ArrayList<GroupModel> tree = null;

        if (StringUtil.isNullZeroStringSpace(grpId)
                && StringUtil.isNullZeroStringSpace(grpName)) {
            //全件検索
            tree = grpBiz.getGroupTree(con);
        } else {
            //グループの絞込み表示
            tree = grpBiz.getSearchGroupNoTree(con, grpId, grpName);
        }

        String type = "link";
        String level = NullDefault.getString(req.getParameter("selectLevel"), "10");
        String root = NullDefault.getString(req.getParameter("dspRoot"), "0");
        String checked = NullDefault.getString(req.getParameter("checkGroup"), "");

        form.setListType(type);
        form.setSelectLevel(level);
        form.setDspRoot(root);
        form.setCheckGroup(checked);
        form.setGroupList(tree);

        con.setAutoCommit(false);
        return map.getInputForward();
    }
}