package jp.groupsession.v2.cmn.cmn380;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ファイルプレビュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn380Action extends AbstractGsAction {

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーム
     * @throws URISyntaxException
     * @throws IOException
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws URISyntaxException, IOException {

        ActionForward forward = null;
        Cmn380Form thisForm = (Cmn380Form) form;

        //プレビュー対象ファイルがプレビュー可能かチェックする
        if (!__checkPreviewFile(thisForm)) {
            __setErrorPage(map, req);
            return map.findForward("gf_msg");
        }

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("convertFile")) {
            //プレビュー対象ファイルを表示形式に合わせて変換
            __convertFile(thisForm, res);
        } else {
            //画面表示
            forward = __doDsp(map, thisForm, req);
        }
        return forward;
    }

    /**
     * <br>[機  能] プレビュー対象ファイルがプレビュー可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param form Cmn380Form
     * @return boolean 判定結果（true:プレビュー可能, false:プレビュー不可能）
     */
    private boolean __checkPreviewFile(Cmn380Form form) {

        boolean result = true;
        Cmn380Biz biz = new Cmn380Biz();
        Cmn380ParamModel paramMdl = new Cmn380ParamModel();
        paramMdl.setParam(form);

        //プレビュー対象ファイルがプレビュー可能かチェックする
        if (!biz.checkPreviewFile(paramMdl)) {
            result = false;
        }
        paramMdl.setFormData(form);
        return result;
    }

    /**
     * <br>[機  能] 画面表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form Cmn380Form
     * @param req レスポンス
     * @return ActionForward
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Cmn380Form form,
        HttpServletRequest req) throws UnsupportedEncodingException, URISyntaxException {

        Cmn380Biz biz = new Cmn380Biz();
        Cmn380ParamModel paramMdl = new Cmn380ParamModel();
        paramMdl.setParam(form);

        //表示用データをセット
        biz.setDspData(paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] プレビュー対象ファイルを表示形式に合わせて変換する
     * <br>[解  説] 変換後のファイルの内容をJSON形式で返す
     * <br>[備  考]
     * @param form Cmn380Form
     * @param res レスポンス
     * @throws IOException
     */
    private void __convertFile(
        Cmn380Form form,
        HttpServletResponse res) throws IOException {

        //プレビュー対象ファイルを表示形式に合わせて変換
        Cmn380Biz biz = new Cmn380Biz();
        Cmn380ParamModel paramMdl = new Cmn380ParamModel();
        paramMdl.setParam(form);
        biz.convertFile(paramMdl);
        paramMdl.setFormData(form);

        //レスポンスをJSON形式で返す
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("cmn380FileContent", form.getCmn380FileContent());
        __writeResp(res, jsonData);
    }

    /**
     * <br>[機  能] エラーメッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setErrorPage(
            ActionMapping map,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);

        //メッセージセット
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.access.window.colse"));

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     *
     * <br>[機  能] jsonレスポンスの書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param json jsonオブジェクト
     */
    private void __writeResp(HttpServletResponse res, JSONObject json) {
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}