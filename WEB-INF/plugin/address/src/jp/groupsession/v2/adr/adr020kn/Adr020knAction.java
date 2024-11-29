package jp.groupsession.v2.adr.adr020kn;


import java.io.IOException;
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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.AbstractAddressSubAction;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr020.Adr020Form;
import jp.groupsession.v2.adr.adr110kn.Adr110knForm;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr020knAction extends AbstractAddressSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr020knAction.class);

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
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Adr020knForm thisForm = (Adr020knForm) form;
        if (cmd.equals("backRegist")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("backRegist");

        } else if (cmd.equals("backAddressList")) {
            log__.debug("*** 戻るボタンクリック");
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng020knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public ActionForward __doInit(ActionMapping map, Adr020knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException {
        ActionForward forward = map.getInputForward();
        try {
            con.setAutoCommit(true);
            RequestModel reqMdl = getRequestModel(req);
            Adr020knBiz biz = new Adr020knBiz(reqMdl);
            Adr020knParamModel paramMdl = new Adr020knParamModel();
            AddressBiz adrBiz = new AddressBiz(reqMdl);


            if (form.getAdr020viewFlg() == 1) {
                if (!adrBiz.isViewAddressData(con,
                        form.getAdr010EditAdrSid(),
                        reqMdl.getSmodel().getUsrsid())) {
                    forward = __doNoneDataError(map, form, req, res, con);
                    return forward;
                }
            } else {
                if (form.getAdr020ProcMode() != GSConstAddress.PROCMODE_ADD) {
                    if (!adrBiz.isEditAddressData(con,
                            form.getAdr010EditAdrSid(),
                            reqMdl.getSmodel().getUsrsid())) {
                        forward = __doNoneDataError(map, form, req, res, con);
                        return forward;
                    }
                } else {
                    if (!adrBiz.isAbleAdd(con, reqMdl.getSmodel().getUsrsid())) {
                        forward = getSubmitErrorPage(map, req);
                        return forward;
                    }
                }
            }




            paramMdl.setParam(form);
            biz.setInitData(con, paramMdl, getSessionUserModel(req));
            paramMdl.setFormData(form);

        } catch (SQLException se) {
            throw se;
        } catch (IOException ioe) {
            throw ioe;
        } catch (IOToolsException iote) {
            throw iote;
        } finally {
            con.setAutoCommit(false);
        }
        // トランザクショントークン設定
        saveToken(req);

        return forward;
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doBack(
        ActionMapping map,
        Adr020knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        String backId = NullDefault.getString(form.getAdr020BackId(), "");

        if (StringUtil.isNullZeroStringSpace(backId)) {
            forward = map.findForward("adrList");
            if (form.getAdr330back() == 1) {
                forward = map.findForward("adrAdmList");
            }
        } else if (backId.equals("adr160")) {
            forward = map.findForward("backContactHistory");
        } else if (backId.equals("adr110kn")) {
            Adr110knForm adr110knForm = new Adr110knForm();
            BeanUtils.copyProperties(adr110knForm, form);
            adr110knForm.setAdr110editAcoSid(form.getAdr110editAcoSid());
            adr110knForm.setAdr100backFlg(2);
            req.setAttribute("adr110knForm", adr110knForm);
            forward = map.findForward("inputCompanyConfirm");
        }

        return forward;
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
    private ActionForward __doNoneDataError(ActionMapping map, Adr020Form form,
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
        String textSchedule = gsMsg.getMessage(req, "address.src.2");
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
