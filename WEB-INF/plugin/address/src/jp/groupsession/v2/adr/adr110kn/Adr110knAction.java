package jp.groupsession.v2.adr.adr110kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.adr020kn.Adr020knForm;
import jp.groupsession.v2.adr.adr110.Adr110Action;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110knAction extends Adr110Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr110knAction.class);

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

        Adr110knForm thisForm = (Adr110knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("backInputCompany")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("inputCompany");

        } else if (cmd.equals("backAdrList")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("adrList");
            if (thisForm.getAdr330back() == 1) {
                forward = map.findForward("adrAdmList");
            }

        } else if (cmd.equals("editCompany")) {
            log__.debug("会社編集");
            forward = map.findForward("inputCompany");

        } else if (cmd.equals("editAdrData")) {
            log__.debug("アドレス氏名リンククリック");
            Adr020knForm adr020knForm = new Adr020knForm();
            BeanUtils.copyProperties(adr020knForm, thisForm);
            adr020knForm.setAdr020viewFlg(1);
            adr020knForm.setAdr020BackId("adr110kn");
            req.setAttribute("adr020knForm", adr020knForm);
            forward = map.findForward("viewAddress");

        } else {
            log__.debug("*** 初期表示を行います。");
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
        Adr110knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        BaseUserModel buModel = getSessionUserModel(req);
        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr110knBiz biz = new Adr110knBiz(getRequestModel(req));
        int sessionUsrSid = buModel.getUsrsid();

        Adr110knParamModel paramMdl = new Adr110knParamModel();
        paramMdl.setParam(form);
        if (form.getAdr100backFlg() == 2) {
            if (biz.canViewCompanyData(con, paramMdl) != 0) {
                con.setAutoCommit(false);
                return __doNoneDataError(map, form, req, res, con);
            }
        }

        biz.getInitData(con, paramMdl, sessionUsrSid);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>編集対象が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(ActionMapping map, Adr110knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //エラー画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("adrList");
        if (form.getAdr330back() == 1) {
            urlForward = map.findForward("adrAdmList");
        }

        form.setHiddenParam(cmn999Form);

        //アドレス情報
        String textSchedule = gsMsg.getMessage(req, "address.118");
        //変更
        String textChange = gsMsg.getMessage(req, "cmn.change");


        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textSchedule, textChange));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
