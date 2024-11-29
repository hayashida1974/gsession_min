package jp.groupsession.v2.wml.syspro.wml1010;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlLabelBiz;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.AccountDataModel;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.wml110.Wml110Dao;

/**
 * <br>[機  能] WEBメール メールインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml1010Action extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml1010Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        Wml1010Form thisForm = (Wml1010Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("importEml")) {
            //インポートボタンクリック
            forward = __doKakunin(map, thisForm, req, res, con);

        } else if (cmd.equals("psnTool")) {
            //戻るボタンクリック
            forward = map.findForward("psnTool");

            //テンポラリディレクトリを削除
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.deleteTempDir(getRequestModel(req), "wml1010");
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Wml1010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        WmlBiz wmlBiz = new WmlBiz();
        WmlLabelBiz labelBiz = new WmlLabelBiz();

        int initFlg = form.getWml1010initFlg();
        int accountSid = -1;

        //セッション情報を取得
        HttpSession session = req.getSession();

        //ユーザSIDを取得
        BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int userSid = usModel.getUsrsid();

        //アカウントリストを取得
        Wml110Dao wml110dao = new Wml110Dao(con);
        List<AccountDataModel> adMdlList = wml110dao.getAccountList(userSid);
        if (initFlg == 0 && adMdlList.size() != 0) {
        	accountSid = adMdlList.get(0).getAccountSid();
        } else {
        	accountSid = form.getWml1010accountSid();
        }

        //ラベルリストを取得
        List<LabelDataModel> lbList = new ArrayList<LabelDataModel>();
        if (accountSid != -1) {
            //ラベルリストを取得
            WmlLabelDao wlbDao = new WmlLabelDao(con);
            List<WmlLabelModel> ldMdlList = wlbDao.getLabelList(accountSid);
            //ラベルを画面表示用に加工する
            lbList = labelBiz.getLabelDataList(ldMdlList);
        }

        //リクエストモデル取得
        RequestModel reqModel = getRequestModel(req);

        //テンポラリディレクトリパスの初期化
        if (initFlg != 1) {
        	wmlBiz.clearTempDir(reqModel, "wml1010");
        }

        //初期表示情報を画面にセットする
        form.setWml1010initFlg(1);
        form.setAcntList(wmlBiz.getAcntCombo(reqModel, adMdlList));
        form.setLbnList(wmlBiz.getLbCombo(reqModel, lbList));
        if (initFlg != 1 && adMdlList.size() != 0) {
        	form.setWml1010accountSid(adMdlList.get(0).getAccountSid());
        }

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確認画面へ遷移Action
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakunin(ActionMapping map, Wml1010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = map.findForward("kakunin");

        //リクエストモデル取得
        RequestModel reqModel = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqModel, "wml1010");

        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(req, reqModel, tempDir);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return forward;
    }
}
