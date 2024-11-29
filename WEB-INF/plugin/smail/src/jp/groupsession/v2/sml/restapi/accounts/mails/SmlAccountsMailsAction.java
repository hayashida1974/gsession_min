package jp.groupsession.v2.sml.restapi.accounts.mails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiDraftMailModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール作成API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsMailsAction extends AbstractRestApiAction {
    /**
    * <br>[機  能] メール作成API
    * <br>[解  説] POST
    * <br>[備  考] メールを送信する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @param tempPathModel テンポラリパスパラメータ
    *
    * @throws Exception
    * @throws SQLException
    * @throws IOToolsException
    */
    @Post
    @Parameter(name = "action", value = "send")
    public void doPostSend(
            HttpServletRequest req,
            HttpServletResponse res,
            SmlAccountsMailsPostParamModel param,
            RestApiContext ctx,
            GSTemporaryPathModel tempPathModel) throws Exception, SQLException, IOToolsException {

        //テンポラリディレクトリ作成
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPathModel);

        //ショートメールプラグインアクセス権限確認
        List<String> pluginList = new ArrayList<String>();
        pluginList.add(GSConst.PLUGINID_SML);
        if (!canAccessPlugin(pluginList)) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.use.plugin",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                    );
        }
        //アカウント 使用可能判定
        SmlAccountsMailsBiz biz = new SmlAccountsMailsBiz();
        param.validateAccount(ctx.getCon(), ctx.getRequestModel(),
                param.getAccountSid(), ctx.getRequestUserSid());

        // 参照メール使用チェック(新規作成以外の場合)
        if (param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_HENSIN)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_TENSO)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_SOKO)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_COPY)) {
            param.validateRefarenceMail(ctx.getCon(), ctx.getRequestModel(),
                    param.getAccountSid(), param.getRefarenceSid());
        } else {
            param.setRefarenceSid(-1);
        }

        // 元メールの添付ファイル引継ぎ判定
        if ((param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_TENSO)
                || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_SOKO)
                || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_COPY))
                && param.getBinSidArray().length > 0
                && param.getRefarenceSid() > 0) {
            param.validateBinArrayCheckPost(ctx.getCon(), ctx.getRequestModel());
        } else {
            param.setBinSidArray(null);
        }

        // 入力チェック
        param.validateCheck(getAppRootPath(), Sml020Form.VALIDATE_MODE_SOUSIN,
                ctx.getCon(), ctx.getRequestModel());
        //送信処理
        List<SmlRestapiMailModel> resList = biz.sendMail(
                req, param, ctx, param.getRefarenceSid(), tempPathModel.getTempPath());

        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPathModel);

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(resList)
        .build().execute();
    }

    /**
    * <br>[機  能] メール作成API
    * <br>[解  説] POST
    * <br>[備  考] メールを草稿保存する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @param tempPathModel テンポラリパスパラメータ
    * @throws Exception
    * @throws SQLException
    * @throws IOToolsException
    */
    @Post
    @Parameter(name = "action", value = "keep")
    public void doPostKeep(
            HttpServletRequest req,
            HttpServletResponse res,
            SmlAccountsMailsPostParamModel param,
            RestApiContext ctx,
            GSTemporaryPathModel tempPathModel) throws Exception, SQLException, IOToolsException {

        //テンポラリディレクトリ作成
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPathModel);

        //ショートメールプラグインアクセス権限確認
        List<String> pluginList = new ArrayList<String>();
        pluginList.add(GSConst.PLUGINID_SML);
        if (!canAccessPlugin(pluginList)) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.use.plugin",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                    );
        }
        //アカウント 使用可能判定
        SmlAccountsMailsBiz biz = new SmlAccountsMailsBiz();
        param.validateAccount(ctx.getCon(), ctx.getRequestModel(),
                param.getAccountSid(), ctx.getRequestUserSid());

        // 参照メール使用チェック(新規作成以外の場合)
        if (param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_HENSIN)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_TENSO)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_SOKO)
         || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_COPY)) {
            param.validateRefarenceMail(ctx.getCon(), ctx.getRequestModel(),
                    param.getAccountSid(), param.getRefarenceSid());
        }

        // 元メールの添付ファイル引継ぎ判定
        if ((param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_TENSO)
                || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_SOKO)
                || param.getProcType() == Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_COPY))
                && param.getBinSidArray().length > 0
                && param.getRefarenceSid() > 0) {
            param.validateBinArrayCheckPost(ctx.getCon(), ctx.getRequestModel());
        } else {
            param.setBinSidArray(null);
        }

        // 入力チェック
        param.validateCheck(getAppRootPath(), Sml020Form.VALIDATE_MODE_SAVE,
                ctx.getCon(), ctx.getRequestModel());

        // 草稿保存処理
        List<SmlRestapiDraftMailModel> resList = biz.saveMail(
                req, param, ctx, -1, tempPathModel.getTempPath());

        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPathModel);

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(resList)
        .build().execute();
    }
    /**
    * <br>[機  能] メール作成API
    * <br>[解  説] POST
    * <br>[備  考] 草稿保存されたメールを編集し、送信する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @param tempPathModel テンポラリパスパラメータ
    * @throws Exception
    * @throws SQLException
    * @throws IOToolsException
    */
    @Put
    @Parameter(name = "action", value = "send")
    public void doPutSend(
            HttpServletRequest req,
            HttpServletResponse res,
            SmlAccountsMailsPutParamModel param,
            RestApiContext ctx,
            GSTemporaryPathModel tempPathModel) throws Exception, SQLException, IOToolsException {

        //テンポラリディレクトリ作成
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPathModel);

        //ショートメールプラグインアクセス権限確認
        List<String> pluginList = new ArrayList<String>();
        pluginList.add(GSConst.PLUGINID_SML);
        if (!canAccessPlugin(pluginList)) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.use.plugin",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                    );
        }
        //アカウント 使用可能判定
        SmlAccountsMailsBiz biz = new SmlAccountsMailsBiz();
        param.validateAccount(ctx.getCon(), ctx.getRequestModel(),
                param.getAccountSid(), ctx.getRequestUserSid());

        // 元メール情報取得
        SmlAccountsMailsPutParamModel setParam = biz.getDraftMail(param, ctx.getCon(),
                ctx.getRequestModel());
        setParam.setProcType(Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_SOKO));

        // 入力チェック
        setParam.validateCheck(getAppRootPath(), Sml020Form.VALIDATE_MODE_SOUSIN,
                ctx.getCon(), ctx.getRequestModel());

        // 元メールの添付ファイル引継ぎ判定
        if (param.getBinSidArray().length > 0) {
            param.validateBinArrayCheckPut(ctx.getCon(), ctx.getRequestModel());
            setParam.setBinSidArray(param.getBinSidArray());
        }

        //送信処理
        List<SmlRestapiMailModel> resList = biz.sendMail(
                req, setParam, ctx, param.getMailSid(), tempPathModel.getTempPath());

        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPathModel);

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(resList)
        .build().execute();
    }
    /**
    * <br>[機  能] メール作成API
    * <br>[解  説] POST
    * <br>[備  考] 草稿保存されたメールを編集し、草稿保存する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @param tempPathModel テンポラリパスパラメータ
    * @throws Exception
    * @throws SQLException
    * @throws IOToolsException
    */
    @Put
    @Parameter(name = "action", value = "keep")
    public void doPutKeep(
            HttpServletRequest req,
            HttpServletResponse res,
            SmlAccountsMailsPutParamModel param,
            RestApiContext ctx,
            GSTemporaryPathModel tempPathModel) throws Exception, SQLException, IOToolsException {

        //テンポラリディレクトリ作成
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPathModel);

        //ショートメールプラグインアクセス権限確認
        List<String> pluginList = new ArrayList<String>();
        pluginList.add(GSConst.PLUGINID_SML);
        if (!canAccessPlugin(pluginList)) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.use.plugin",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                    );
        }
        //アカウント 使用可能判定
        SmlAccountsMailsBiz biz = new SmlAccountsMailsBiz();
        param.validateAccount(ctx.getCon(), ctx.getRequestModel(),
                param.getAccountSid(), ctx.getRequestUserSid());

        // 元メール情報取得
        SmlAccountsMailsPutParamModel setParam = biz.getDraftMail(param, ctx.getCon(),
                ctx.getRequestModel());
        setParam.setProcType(Integer.parseInt(GSConstSmail.MSG_CREATE_MODE_SOKO));

        // 入力チェック
        setParam.validateCheck(getAppRootPath(), Sml020Form.VALIDATE_MODE_SAVE,
                ctx.getCon(), ctx.getRequestModel());

        if (param.getBinSidArray().length > 0) {
            param.validateBinArrayCheckPut(ctx.getCon(), ctx.getRequestModel());
            setParam.setBinSidArray(param.getBinSidArray());
        }

        // 草稿保存処理
        List<SmlRestapiDraftMailModel> resList = biz.saveMail(
                req, setParam, ctx, param.getMailSid(), tempPathModel.getTempPath());

        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPathModel);

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(resList)
        .build().execute();
    }
}
