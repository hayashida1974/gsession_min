package jp.groupsession.v2.rng.rng090kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONArray;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.FormBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiTemplateAction;
import jp.groupsession.v2.rng.RngCategoriCantAccessException;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngFormBuildBiz;
import jp.groupsession.v2.rng.biz.RngTemplateBiz;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng090.Rng090Action;
import jp.groupsession.v2.rng.rng090.Rng090Biz;
import jp.groupsession.v2.rng.rng090.Rng090Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレート登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng090knAction extends AbstractRingiTemplateAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng090knAction.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }
    @Override
    protected String _getTempDirId() {
        return Rng090Action.DIRID;
    }
    /**
    *
    * <br>[機  能] アクション実行前の事前処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @throws Exception 実行時例外
    */
   protected void _prepareAction(ActionMapping map, Rng090knForm form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
       Rng090knForm thisForm = form;
       //個人稟議テンプレートの場合、rng090Contentからテキスト（複数行）の要素のみを持つJSONを生成する
       if (form.getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_INIT) {
           RngFormBuildBiz buildBiz = new RngFormBuildBiz(getRequestModel(req));
           FormCell cell = buildBiz.createHanyouRingiNaiyo(thisForm.getRng090content());

           FormBuilder formBuilder = new FormBuilder();
           formBuilder.setFormCell(0, 0, cell);
           cell = buildBiz.createHanyouRingiTemp(null);
           formBuilder.setFormCell(1, 0, cell);
           JSONArray jsonArray = JSONArray.fromObject(formBuilder.getFormTable());
           String json = jsonArray.toString();
           thisForm.setRng090templateJSON(json);
       }

       if (form.getRng090templateJSON() != null) {
           String jsonStr = form.getRng090templateJSON();
           if (jsonStr.indexOf(0xA0) >= 0) {
               // HTML用半角スペース(&nbsp; -> 0xA0)が含まれている場合、通常の半角スペース(0x20)へ変換
               jsonStr = jsonStr.replace((char) 0xA0, (char) 0x20);
               form.setRng090templateJSON(jsonStr); // パラメータへ再セット
           }

           //JSONからフォームビルダーを生成する
           thisForm.getRng090template().setFormTable(form.getRng090templateJSON());
       }

   }
   /**
    *
    * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
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
   protected ActionForward _immigration(ActionMapping map, Rng090knForm form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
       Rng090Form thisForm = (Rng090Form) form;
       RequestModel reqMdl = getRequestModel(req);
       GsMessage gsMsg = new GsMessage(reqMdl);
       CommonBiz cmnBiz = new CommonBiz();
       boolean adminUser = cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), getPluginId());

       int tFlg = thisForm.getRngTemplateMode();

       // 個人テンプレート制限確認
       if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
           RngBiz rngBiz = new RngBiz(con);
           RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
           // 汎用稟議の使用が不可ならエラー
           if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
               return getSubmitErrorPage(map, req);
           }
           // 個人テンプレートの使用が不可ならエラー
           if (aconfMdl.getRarTemplatePersonalFlg()
                   == RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO) {
               return getSubmitErrorPage(map, req);
           }
       }

       //稟議編集の場合 カテゴリの編集権限がなければアクセスエラー
       //新規作成の場合 カテゴリは登録確認時に入力チェックするのでここではアクセスエラーにならない
       if (form.getRngTplCmdMode() == RngConst.RNG_CMDMODE_ADD) {
           return null;
       }
       ActionForward backForward =  map.findForward("rng060");
       RngTemplateBiz rtBiz = new RngTemplateBiz();
       //エラー画面用hidden設定
       RngTemplateModel rtModel = rtBiz.getRtpModel(form.getRngSelectTplSid(), con);
       Cmn999Form cmn999form = new Cmn999Form();
       cmn999form.addHiddenAll(thisForm, thisForm.getClass(), "");
       if (rtModel == null) {
           return getCatchExceptionPage(map, req,
                   new RtpNotfoundException(), backForward, cmn999form);
       }

       //カテゴリアクセス権限例外用のエラー場面設定を用意
       RngCategoriCantAccessException rcaException = new RngCategoriCantAccessException();
       rcaException.setSeigenKbn(RngCategoriCantAccessException.SEIGEN_KBN_AUTH);
       rcaException.setCantActionStr(gsMsg.getMessage("cmn.edit"));
       ActionForward authError =  getCatchExceptionPage(map, req,
               rcaException, backForward, cmn999form);
       //カテゴリアクセス権限
       Rng090Biz rng090Biz = new Rng090Biz(con, reqMdl);
       if (!rng090Biz.categoriAuthChk(tFlg, rtModel.getRtcSid(), adminUser)) {
           return authError;
       }
       //データの不正書き換え対策
       if (form.getRng090rtpSpecVer() != rtModel.getRtpSpecVer()) {
           return getSubmitErrorPage(map, req);
       }
       return null;
    }

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }

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


        Rng090knForm thisForm = (Rng090knForm) form;
        _prepareAction(map, thisForm, req, res, con);

        forward = _immigration(map, thisForm, req, res, con);
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (forward != null) {
            return forward;
        }

        if (cmd.equals("rng090back")) {
            log__.debug("*** 内容テンプレート登録。");
            forward = map.findForward("rng090back");

        } else if (cmd.equals("rng060")) {
            log__.debug("*** 内容テンプレート一覧。");
            forward = map.findForward("rng060");

        } else if (cmd.equals("cmn999kakutei")) {
            log__.debug("*** 確定。");
            forward = __doKakutei(map, thisForm, req, res, con);

        } else if (cmd.equals("fileDownload")) {
            log__.debug("*** 添付ファイルダウンロード");
            forward = __doDownLoad(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng090knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //rng090Form画面以外からの遷移をエラーとする
        if (req.getAttribute("rng090Form") == null) {
            return getSubmitErrorPage(map, req);
        }
        con.setAutoCommit(true);
        Rng090knParamModel paramMdl = new Rng090knParamModel();
        paramMdl.setParam(form);
        RequestModel reqMdl = getRequestModel(req);
        Rng090knBiz biz = new Rng090knBiz(con, reqMdl);
        biz.initDsp(paramMdl, _getRingiDir(req), con, reqMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
     }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng090knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Rng090knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

      //トランザクショントークンチェック
        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("rng090back");
        }

        ActionForward forward = null;
        boolean commit = false;
        try {
            Rng090knParamModel paramMdl = new Rng090knParamModel();
            paramMdl.setParam(form);
            Rng090knBiz biz = new Rng090knBiz(con, reqMdl);
            GSTemporaryPathModel tempDir = _getRingiDir(req);
            //テンプレート情報の登録
            biz.registRngTpl(
                    paramMdl,
                    getCountMtController(req),
                    getSessionUserSid(req),
                    getAppRootPath(),
                    tempDir,
                    getResources(req));
            paramMdl.setFormData(form);

            forward = __setCompPageParam(map, req, form);

            // ---------------------------------------------------
            //ログ出力処理
            GsMessage gsMsg = new GsMessage(reqMdl);
            String entry = gsMsg.getMessage("cmn.entry");
            String edit = gsMsg.getMessage("cmn.edit");

            RngBiz rngBiz = new RngBiz(con);
            String opCode = "";
            String msg = "";

            if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE) {
                opCode += gsMsg.getMessage("cmn.shared.template");    //共有
            } else if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
                opCode += gsMsg.getMessage("cmn.personal.template");  //個人
            }
            if (form.getRngTplCmdMode() == RngConst.RNG_CMDMODE_ADD) {
                opCode += entry;
            } else if (form.getRngTplCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
                opCode += edit;
            }

            String rtcName = null;
            if (form.getRng090CatSid() != RngConst.RNG_RTC_SID_NONE) {
                RngTemplateCategoryDao rtcDao = new RngTemplateCategoryDao(con);
                RngTemplateCategoryModel rtcMdl = rtcDao.select(form.getRng090CatSid());
                if (rtcMdl != null) {
                    rtcName = rtcMdl.getRtcName();
                }
            }
            msg += "[" + gsMsg.getMessage("cmn.category") + "] ";
            if (rtcName == null) {
                msg += gsMsg.getMessage("cmn.category.no"); // カテゴリなし
            } else {
                msg += rtcName;
            }

            msg += "\r\n[" + gsMsg.getMessage("rng.10") + "] ";
            msg += form.getRng090title();

            // 稟議申請ID
            if (form.getIdSelectable() == RngConst.RAR_SINSEI_TOUITU
             || form.getIdSelectable() == RngConst.RAR_SINSEI_TEMP) {
                msg += "\r\n[" + gsMsg.getMessage("rng.rng180.04") + "] ";
                msg += form.getRng090idTitle();

                if (form.getIdPrefManualEditable() == RngConst.RAR_SINSEI_MANUAL_TEMPLATE) {
                    // 手動入力許可する
                    msg += "(" + gsMsg.getMessage("rng.rng210.04") + ": ";
                    if (form.getRng090idPrefManual() == RngConst.RAR_SINSEI_MANUAL_KYOKA) {
                        msg += gsMsg.getMessage("cmn.permit");      // 許可する
                    } else {
                        msg += gsMsg.getMessage("cmn.not.permit");  // 許可しない
                    }
                    msg += ")";
                }
            }

            msg += "\r\n[" + gsMsg.getMessage("cmn.title") + "] ";
            msg += form.getRng090rngTitle();

            msg += "\r\n[" + gsMsg.getMessage("cmn.memo") + "] ";
            msg += form.getRng090biko();

            String[] fileNames = form.getRng090files();
            if (fileNames != null && fileNames.length > 0) {
                msg += "\r\n[" + gsMsg.getMessage("cmn.attach.file") + "] ";
                for (int i = 0; i < fileNames.length; i++) {
                    if (i > 0) {
                        msg += ",";
                    }
                    msg += fileNames[i];
                }
            }

            // 入力フォーム
            List<List<FormCell>> formTable = form.getRng090template().getFormTable();
            if (formTable != null && formTable.size() > 0) {
                for (List<FormCell> formList : formTable) {
                    for (FormCell cell : formList) {
                        LabelValueBean bean = rngBiz.outputFormCell(cell, true);
                        msg += "\r\n[" + bean.getLabel() + "] " + bean.getValue();
                    }
                }
            }

            GSTemporaryPathUtil.getInstance().deleteTempPath(tempDir);

            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_INFO,
                    msg,
                    reqMdl);
            // ---------------------------------------------------

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("稟議テンプレート情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
        ActionMapping map,
        Rng090knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {
        GSTemporaryPathModel tempDirMdl = _getRingiDir(req);
        String tempDir = tempDirMdl.getTempPath();
        String fileId = form.getRng090knTmpFileId();

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String download = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        RngBiz rngBiz = new RngBiz(con);
        rngBiz.outPutLog(
                map,
                download, GSConstLog.LEVEL_INFO, fMdl.getFileName(),
                reqMdl, fileId);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        rngBiz.downloadTempFile(req, res, tempDir, fileId);

        return null;
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
        Rng090knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("rng060");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String rng = gsMsg.getMessage(req, "rng.92");

        //メッセージセット
        String msgState = null;
        if (form.getRngTplCmdMode() == RngConst.RNG_CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getRngTplCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, rng));

        cmn999Form.addHiddenParam("rng010TransitionFlg", form.getRng010TransitionFlg());

        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        cmn999Form.addHiddenParam("rngTplCmdMode", form.getRngTplCmdMode());
        cmn999Form.addHiddenParam("rng060SelectCat", form.getRng090CatSid());
        cmn999Form.addHiddenParam("rng060SelectCatUsr", form.getRng090CatSid());

        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
