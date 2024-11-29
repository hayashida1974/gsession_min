package jp.groupsession.v2.rng.rng090;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONArray;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
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
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng200.Rng200Biz;
import jp.groupsession.v2.rng.rng200.Rng200Form;
import jp.groupsession.v2.rng.rng200.Rng200ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレート登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng090Action extends AbstractRingiTemplateAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng090Action.class);
    /** ディレクトリID */
    public static final String DIRID = "rng090";


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
        return DIRID;
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
   protected void _prepareAction(ActionMapping map, Rng090Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
       //個人稟議テンプレートの場合、rng090Contentからテキスト（複数行）の要素のみを持つJSONを生成する
       if (form.getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_INIT) {
           RngFormBuildBiz buildBiz = new RngFormBuildBiz(getRequestModel(req));
           FormCell cell = buildBiz.createHanyouRingiNaiyo(form.getRng090content());

           FormBuilder formBuilder = new FormBuilder();
           formBuilder.setFormCell(0, 0, cell);
           JSONArray jsonArray = JSONArray.fromObject(formBuilder.getFormTable());
           String json = jsonArray.toString();
           form.setRng090templateJSON(json);
       }

       if (form.getRng090templateJSON() != null) {
           //JSONからフォームビルダーを生成する
           String jsonStr = form.getRng090templateJSON();
           if (jsonStr.indexOf(0xA0) >= 0) {
               // HTML用半角スペース(&nbsp; -> 0xA0)が含まれている場合、通常の半角スペース(0x20)へ変換
               jsonStr = jsonStr.replace((char) 0xA0, (char) 0x20);
               form.setRng090templateJSON(jsonStr); // パラメータへ再セット
           }

           form.getRng090template().setFormTable(form.getRng090templateJSON());
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
   protected ActionForward _immigration(ActionMapping map, Rng090Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {
       Rng090Form thisForm = (Rng090Form) form;
       RequestModel reqMdl = getRequestModel(req);
       GsMessage gsMsg = new GsMessage(reqMdl);
       CommonBiz cmnBiz = new CommonBiz();
       boolean adminUser = cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), getPluginId());
       Rng090Biz rng090Biz = new Rng090Biz(con, reqMdl);

       String cmd = NullDefault.getString(req.getParameter("CMD"), "");
       if (cmd.equals("loadIDList")) {
           return null;
       }

       if (form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_SHARE
               && form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_PRIVATE) {
           return getSubmitErrorPage(map, req);
       }

       int tFlg = thisForm.getRngTemplateMode();

       if (form.getRng090rtpSpecVer() < RngConst.RNG_RTP_SPEC_VER_INIT
               || form.getRng090rtpSpecVer() > RngConst.RNG_RTP_SPEC_VER_A480) {

           return getSubmitErrorPage(map, req);
       }
       if (form.getRng060TemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE
               && form.getRng090rtpSpecVer() != RngConst.RNG_RTP_SPEC_VER_INIT) {
           return getSubmitErrorPage(map, req);
       }

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
           // 使用可能なテンプレートカテゴリ数をチェック
           // ※管理者 or 個人テンプレートの場合、常に「カテゴリなし」が使用可能な為にチェックなし
           if (!adminUser && form.getRngTemplateMode() != RngConst.RNG_TEMPLATE_PRIVATE) {
               RngBiz biz = new RngBiz(con);
               //共有のカテゴリ一覧を取得する
               ArrayList<RngTemplateCategoryModel> catList =
                   biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                                               reqMdl.getSmodel().getUsrsid(), adminUser,
                                               RngConst.RTPLIST_MOKUTEKI_KANRI);
               if (catList == null || catList.isEmpty()) {
                   // 使用可能なテンプレートカテゴリがないのでアクセスエラーとする
                   return getSubmitErrorPage(map, req);
               }
           }
           return null;
       }
       ActionForward backForward =  map.findForward("rng060");
       RngTemplateBiz rtBiz = new RngTemplateBiz();
       RngTemplateModel rtModel = rtBiz.getRtpModel(form.getRngSelectTplSid(), con);
       Cmn999Form cmn999form = new Cmn999Form();
       cmn999form.addHiddenAll(thisForm, thisForm.getClass(), "");

       if (rtModel == null) {
           return getCatchExceptionPage(map, req,
                   new RtpNotfoundException(), backForward, cmn999form);
       }


       RngCategoriCantAccessException rcaException = new RngCategoriCantAccessException();
       rcaException.setSeigenKbn(RngCategoriCantAccessException.SEIGEN_KBN_AUTH);
       rcaException.setCantActionStr(gsMsg.getMessage("cmn.edit"));
       ActionForward authError =  getCatchExceptionPage(map, req,
               rcaException, backForward, cmn999form);
       //カテゴリアクセス権限
       if (!rng090Biz.categoriAuthChk(tFlg, rtModel.getRtcSid(), adminUser)) {
           return authError;
       }
       //個人テンプレートのユーザSID権限確認
       if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE
               && rtModel.getUsrSid() != reqMdl.getSmodel().getUsrsid()) {
              return getCatchExceptionPage(map, req,
                      new RtpNotfoundException(), backForward, cmn999form);
       }

       //データの不正書き換え対策
       if (!rng090Biz.isEditFirstAccess(form.getRngTplCmdMode(), cmd)
               && form.getRng090rtpSpecVer() != rtModel.getRtpSpecVer()) {
           return getSubmitErrorPage(map, req);
       }
       return null;
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
        Rng090Form thisForm = (Rng090Form) form;

        _prepareAction(map, thisForm, req, res, con);

        forward = _immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("ok090")) {
            log__.debug("*** OKボタン。");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("rng060")) {
            log__.debug("*** 内容テンプレート一覧。");
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));

            forward = map.findForward("rng060");

        } else if (cmd.equals("cmn999del")) {
            log__.debug("*** 削除ボタン。");
            forward = __setDeleteDsp(map, req, thisForm);

        } else if (cmd.equals("delexe")) {
            log__.debug("*** 削除確認OK。");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("delTempfile")) {
            log__.debug("*** 添付ファイル削除ボタン。");
            forward = __doTempDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("loadKeiro")) {
            log__.debug("*** 経路設定再読み込");
            forward = __doLoadKeiro(map, thisForm, req, res, con);
        } else if (cmd.equals("loadIDList")) {
            log__.debug("*** ID一覧再読み込");
            forward = __doIDList(map, thisForm, req, res, con);
        } else if (cmd.equals("copy")) {
            log__.debug("*** 複写して新規作成");
            thisForm.setRngTplCmdMode(RngConst.RNG_CMDMODE_ADD);
            if (thisForm.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE) {
                thisForm.setRng090rtpSpecVer(RngConst.RNG_RTP_SPEC_VER_A480);
            }
            forward = __doInit(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。処理モード＝" + thisForm.getRngTplCmdMode());
            if (cmd.equals("060title") || cmd.equals("rng090")) {
                log__.debug("テンポラリディレクトリ ファイルの削除を行います。");
                GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));
            } else {
                log__.debug("テンポラリディレクトリ ファイルの削除を行いませんでした。");
            }
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
    private ActionForward __doInit(ActionMapping map, Rng090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);

        BaseUserModel buMdl = getSessionUserModel(req);
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Rng090ParamModel paramMdl = new Rng090ParamModel();
        paramMdl.setParam(form);
        Rng090Biz biz = new Rng090Biz(con, getRequestModel(req));
        form.setRng090UserSid(buMdl.getUsrsid());

        biz.modeSet(paramMdl, cmd);

        biz.initDsp(paramMdl, getAppRootPath(), cmd, buMdl.getUsrsid(),
                GSTemporaryPathModel.getInstance(getRequestModel(req),
                        getPluginId(), _getTempDirId()), getResources(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
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
    private ActionForward __doLoadKeiro(ActionMapping map, Rng090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);

        BaseUserModel buMdl = getSessionUserModel(req);
        Rng090ParamModel paramMdl = new Rng090ParamModel();
        paramMdl.setParam(form);
        Rng090Biz biz = new Rng090Biz(con, getRequestModel(req));
        form.setRng090UserSid(buMdl.getUsrsid());
        biz.initLoadKeiro(paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.findForward("loadKeiro");
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
    private ActionForward __doIDList(ActionMapping map, Rng090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Rng200Biz biz = new Rng200Biz();
        Rng200Form idListForm = new Rng200Form();
        Rng200ParamModel paramMdl = new Rng200ParamModel();
        paramMdl.setParam(idListForm);
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(idListForm);

        req.setAttribute("rng200Form", idListForm);
        con.setAutoCommit(false);
        return map.findForward("loadIdList");
    }

    /**
     * <br>[機  能] OKボタンクリック時
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
    private ActionForward __doOk(ActionMapping map, Rng090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("rng090kn");
    }


    /**
     * <br>[機  能] 添付ファイル削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doTempDelete(
        ActionMapping map,
        Rng090Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = _getRingiDir(req);
        log__.debug("テンポラリディレクトリ = " + tempDir.getTempPath());

        //選択された添付ファイルを削除する
        GSTemporaryPathUtil.getInstance().deleteFile(form.getRng090files(), tempDir);

        Rng090ParamModel paramMdl = new Rng090ParamModel();
        paramMdl.setParam(form);
        Rng090Biz rbiz = new Rng090Biz(con, getRequestModel(req));
        rbiz.setFileLabelList(paramMdl, tempDir.getTempPath());
        //ファイル変更フラグをたてる
        paramMdl.setFlgFileChange(1);
        paramMdl.setFormData(form);
        return  __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    @SuppressWarnings("unchecked")
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Rng090Form form) throws Exception {

        saveToken(req);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("delexe");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("delback");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.92");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage(
                        "sakujo.kakunin.once", msg));

        //画面パラメータをセット

        cmn999Form.addHiddenAll(form, form.getClass(), "");
        form.setHiddenParam(cmn999Form, false);

        //経路設定系サブフォームをパラメータ化
        Enumeration<String> enumParamName = (Enumeration<String>) req.getParameterNames();
        while (enumParamName.hasMoreElements()) {
            String name = enumParamName.nextElement();
            if (name.startsWith("rng090keiro.")) {
                Object prop = null;
                try {
                    prop = PropertyUtils.getProperty(form, name);
                } catch (NoSuchMethodException e) {
                    continue;
                }
                if (prop == null) {
                    continue;
                }
                if (prop.getClass().isArray()) {
                    //配列型の場合
                    Object[] objs = (Object[]) prop;
                    if (ArrayUtils.isEmpty(objs)) {
                        continue;
                    }
                    String[] arr = new String[objs.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (objs[i] != null) {
                            arr[i] = objs[i].toString();
                        }
                    }
                    cmn999Form.addHiddenParam(name, arr);
                } else if (prop.getClass().isAssignableFrom(Collection.class)) {
                    //List型の場合
                    Collection<Object> objList
                      = (Collection<Object>) prop;
                    if (objList != null && objList.size() == 0) {
                        continue;
                    }
                    Object[] objs = objList.toArray();
                    String[] arr = new String[objs.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (objs[i] != null) {
                            arr[i] = objs[i].toString();
                        }
                    }
                    cmn999Form.addHiddenParam(name, arr);
                } else {
                    cmn999Form.addHiddenParam(name,
                       prop.toString());
                }
            }
        }
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Rng090Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

      //トランザクショントークンチェック
        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        boolean commit = false;
        try {
            RequestModel reqMdl = getRequestModel(req);
            Rng090ParamModel paramMdl = new Rng090ParamModel();
            paramMdl.setParam(form);
            Rng090Biz biz = new Rng090Biz(con, reqMdl);
            //テンプレートの削除
            biz.deleteTpl(paramMdl, getSessionUserSid(req), form.getRngSelectTplSid());
            paramMdl.setFormData(form);
            forward = __setCompDsp(map, req, form);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String msg = gsMsg.getMessage("cmn.delete");

            String opCode = "";
            if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE) {
                opCode += gsMsg.getMessage("cmn.shared.template");    //共有
            } else if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
                opCode += gsMsg.getMessage("cmn.personal.template");  //個人
            }
            opCode += msg;

            //ログ出力処理
            RngBiz rngBiz = new RngBiz(con);
            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_INFO,
                    "[" + gsMsg.getMessage("rng.10") + "]" + form.getRng090title(),
                    reqMdl);
            GSTemporaryPathUtil.getInstance().deleteTempPath(_getRingiDir(req));
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
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Rng090Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;

        forwardOk = map.findForward("rng060");

        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.92");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        cmn999Form.addHiddenParam("rngTplCmdMode", form.getRngTplCmdMode());
        cmn999Form.addHiddenParam("rng060SelectCat", form.getRng060SelectCat());
        cmn999Form.addHiddenParam("rng060SelectCatUsr", form.getRng060SelectCatUsr());

        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
