package jp.groupsession.v2.bbs.bbs190;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.ForumModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 掲示板 フォーラム選択(ラジオボタン選択方式)のビジネスロジックです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs190Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs190Biz.class);

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Bbs190Biz(Connection con) {
        con__ = con;
    }

    /**
     * <p>フォーラムリスト表示(ラジオ選択ALL)
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward doListView(
            ActionMapping map,
            Bbs190Form form,
            HttpServletRequest req,
            HttpServletResponse res)
                    throws Exception {
        con__.setAutoCommit(true);

        ActionForward forward = null;

        Bbs190Biz bbs190Biz = new Bbs190Biz(con__);
        ArrayList<ForumModel> tree = bbs190Biz.__getForumTree();

        String level = NullDefault.getString(
                req.getParameter("selectLevel"),
                String.valueOf(GSConstBulletin.BBS_FORUM_MIN_LEVEL));
        String checked = NullDefault.getString(req.getParameter("checkForum"), "");

        form.setCheckForum(checked);
        form.setSelectLevel(level);
        form.setForumList(tree);

        con__.setAutoCommit(false);

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] フォーラムの階層構造Model一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return フォーラムの階層構造Model一覧
     */
    private ArrayList<ForumModel> __getForumTree() {
        ArrayList<ForumModel> ret = null;

        try {
            BbsForInfDao bfiDao = new BbsForInfDao(con__);
            List<BbsForInfModel> forInfList = bfiDao.selectWithHierarchy();

            if (forInfList == null || forInfList.isEmpty()) {
                return null;
            }

            ret = new ArrayList<ForumModel>();

            Iterator<BbsForInfModel> it = forInfList.iterator();

            while (it.hasNext()) {
                BbsForInfModel forInfModel = it.next();

                ForumModel mdl = new ForumModel();
                //フォーラムSIDをセット
                mdl.setForumSid(forInfModel.getBfiSid());
                //フォーラム名をセット
                mdl.setForumName(forInfModel.getBfiName());
                //フォーラム階層レベルをセット
                mdl.setClassLevel(forInfModel.getBfiLevel());
                //アイコン画像のバイナリSIDをセット
                mdl.setBinSid(forInfModel.getBinSid());
                ret.add(mdl);
            }
        } catch (SQLException e) {
            log__.error("SQLエラー", e);
        }
        return ret;
    }

}
