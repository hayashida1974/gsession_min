package jp.groupsession.v2.adr.adr111;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.adr110.Adr110Action;
import jp.groupsession.v2.adr.adr110.Adr110BaseForm;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] アドレス帳 拠点登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr111Action extends Adr110Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr111Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START");

        ActionForward forward = null;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        Adr111Form thisForm = (Adr111Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        if (cmd.equals("confirmCompanyBase")) {
            //OKボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else if (cmd.equals("backCompanyInput")) {
            //戻るボタンクリック
            forward = map.findForward("inputCompany");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr111Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr111Biz biz = new Adr111Biz(getRequestModel(req));

        Adr111ParamModel paramMdl = new Adr111ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        saveToken(req);
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doConfirm(ActionMapping map,
                                    Adr111Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェックを行う
        ActionErrors errors = null;
        errors = form.validateCheck(con, req);
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        int editIndex = form.getAdr111editCompanyBaseIndex();
        List<Adr110BaseForm> abaList = form.getAbaListToList();
        for (Adr110BaseForm baseForm : abaList) {
            if (baseForm.getAdr110abaIndex() == editIndex) {

                baseForm.setAdr110abaTypeDetail(form.getAdr111abaType());
                baseForm.setAdr110abaName(form.getAdr111abaName());
                baseForm.setAdr110abaPostno1(form.getAdr111abaPostno1());
                baseForm.setAdr110abaPostno2(form.getAdr111abaPostno2());
                baseForm.setAdr110abaTdfk(form.getAdr111abaTdfk());
                baseForm.setAdr110abaAddress1(form.getAdr111abaAddress1());
                baseForm.setAdr110abaAddress2(form.getAdr111abaAddress2());
                baseForm.setAdr110abaBiko(form.getAdr111abaBiko());

            }
        }
        form.setAbaListForm(abaList);

        return __setCompPageParam(map, req, form);
    }
    
    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Adr111Form form) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("inputCompany");
        cmn999Form.setUrlOK(urlForward.getPath() + "?" + GSConst.P_CMD + "=adr111commit");

        //メッセージセット
        String msgState = null;
        msgState = "hensyu.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, gsMsg.getMessage(req, "address.10")));

        //パラメータ設定
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        
        return map.findForward("gf_msg");
    }

}
