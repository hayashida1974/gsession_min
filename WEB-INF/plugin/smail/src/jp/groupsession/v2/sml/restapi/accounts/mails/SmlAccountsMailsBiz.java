package jp.groupsession.v2.sml.restapi.accounts.mails;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.SmlMailFileModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlPushSender;
import jp.groupsession.v2.sml.biz.SmlReceiveFilter;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailSendModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlAsakModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.restapi.dao.SmlRestapiMailDao;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiDraftMailModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.sml.sml020.Sml020ParamModel;
import jp.groupsession.v2.sml.sml020kn.Sml020knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール作成API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsMailsBiz extends AbstractRestApiAction {

    /**
     * <br>[機  能] 空文字列の除外
     * <br>[解  説]
     * <br>[備  考]
     * @param sendList    チェックするアカウントSID配列
     * @param existSidMap 存在するアカウントSIDマップ
     * @return 空文字列を除外した配列
     */
    public String[] convertSacSidList(int[] sendList,
                                      HashMap<Integer, Integer> existSidMap) {
        if (sendList != null && sendList.length > 0) {
            List<String> convertList = new ArrayList<String>();
            for (Integer key : sendList) {
                Integer val = null;
                if (existSidMap != null && existSidMap.containsKey(key)) {
                    val = existSidMap.get(key);
                }
                if (val != null && val >= 0) {
                    convertList.add(val.toString());
                } else {
                    convertList.add(GSConstSmail.SML_ACCOUNT_STR + key);
                }
            }
            return convertList.toArray(new String[convertList.size()]);
        }
        return null;
    }
    /**
    *
    * <br>[機  能] 草稿メールから情報取得
    * <br>[解  説]
    * <br>[備  考]
    * @param param パラメータモデル
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @return メール情報
    * @throws SQLException
    */
    public SmlAccountsMailsPutParamModel getDraftMail(SmlAccountsMailsPutParamModel param,
            Connection con, RequestModel reqMdl)
            throws SQLException {
        SmlRestapiMailDao dao = new SmlRestapiMailDao(con);
        ArrayList<SmailDetailModel> mailDetail = dao.selectWmeisDetail(
                param.getAccountSid(), param.getMailSid(), GSConst.JTKBN_TOROKU);
        if (mailDetail.size() == 0) {
            // メールが存在しない場合
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.mail")
                    );
        }
        SmlAccountsMailsPutParamModel setParam = new SmlAccountsMailsPutParamModel();
        setParam.setAccountSid(param.getAccountSid());
        for (SmailDetailModel mdl : mailDetail) {
            if (param.getToArray().length == 0) {
                int[] atesakiList = new int[mdl.getAtesakiList().size()];
                int i = 0;
                for (AtesakiModel atesakiMdl : mdl.getAtesakiList()) {
                    atesakiList[i] = atesakiMdl.getAccountSid();
                    i++;
                }
                setParam.setToArray(atesakiList);
            } else {
                setParam.setToArray(param.getToArray());
            }
            if (param.getCcArray().length == 0) {
                int[] ccList = new int[mdl.getCcList().size()];
                int i = 0;
                for (AtesakiModel ccMdl : mdl.getCcList()) {
                    ccList[i] = ccMdl.getAccountSid();
                    i++;
                }
                setParam.setCcArray(ccList);
            } else {
                setParam.setCcArray(param.getCcArray());
            }
            if (param.getBccArray().length == 0) {
                int[] bccList = new int[mdl.getBccList().size()];
                int i = 0;
                for (AtesakiModel bccMdl : mdl.getBccList()) {
                    bccList[i] = bccMdl.getAccountSid();
                    i++;
                }
                setParam.setBccArray(bccList);
            } else {
                setParam.setBccArray(param.getBccArray());
            }
            if (param.getSubjectText() == null) {
                setParam.setSubjectText(mdl.getSmsTitle());
            } else {
                setParam.setSubjectText(param.getSubjectText());
            }
            if (param.getMarkType() == 0) {
                setParam.setMarkType(mdl.getSmsMark());
            } else {
                setParam.setMarkType(param.getMarkType());
            }
            if (param.getBodyText() == null) {
                setParam.setBodyText(mdl.getSmsBody());
            } else {
                setParam.setBodyText(param.getBodyText());
            }
        }
        return setParam;
    }

    /**
     * <br>[機  能] メール送信処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param param パラメータモデル
     * @param ctx コンテキスト
     * @param editMailSid 編集元となるショートメールSID
     * @param tempPath テンポラリパス
     * @return メール情報
     * @throws SQLException
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     */
    public List<SmlRestapiMailModel> sendMail(
            HttpServletRequest req,
            SmlAccountsMailsPostParamModel param,
            RestApiContext ctx,
            int editMailSid,
            String tempPath)
                    throws Exception, SQLException,
                    IOToolsException, IOException, TempFileException {

        // フォームへ情報セット(PCブラウザと共通処理を使用)
        Sml020Form sml020knForm = new Sml020Form();
        sml020knForm.setSml020ProcMode(String.valueOf(param.getProcType()));
        sml020knForm.setSml020MailType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL); // メールタイプは固定
        sml020knForm.setSml010EditSid(editMailSid); //元となるメールSID
        sml020knForm.setSml010SelectedSid(param.getRefarenceSid());
        sml020knForm.setSml020Title(NullDefault.getString(param.getSubjectText(), ""));
        sml020knForm.setSmlViewAccount(param.getAccountSid());
        sml020knForm.setSml020SendAccount(param.getAccountSid());
        sml020knForm.setSml020Mark(param.getMarkType());
        sml020knForm.setSml020Body(param.getBodyText());
        sml020knForm.setSml020userSid(convertSacSidList(param.getToArray(), null));
        sml020knForm.setSml020userSidCc(convertSacSidList(param.getCcArray(), null));
        sml020knForm.setSml020userSidBcc(convertSacSidList(param.getBccArray(), null));

        SmlAccountDao sacDao = new SmlAccountDao(ctx.getCon());
        SmlAccountModel sacMdl = sacDao.select(param.getAccountSid());
        String sacName = "";
        if (sacMdl != null) {
            sacName = sacMdl.getSacName();
        }

        // メール情報をDBに登録
        MlCountMtController cntCon = getCountMtController(req);
        PluginConfig pluginConfig = getPluginConfigForMain(getPluginConfig(req), ctx.getCon(), getRequestModel(req));

        Sml020knParamModel paramMdl = new Sml020knParamModel();
        paramMdl.setParam(sml020knForm);
        SmailSendModel smlMdl = insertMailData(
                paramMdl, ctx, cntCon, req,
                pluginConfig, param.getBinSidArray(), tempPath);

        // 結果判定
        if (paramMdl.getErrorsList() == null || paramMdl.getErrorsList().size() == 0) {
            // エラーなし → データ更新OK
            ctx.getCon().commit();
        } else {
            // 登録処理でエラー
            throw new RestApiValidateException(
                    EnumError.SYS_RUNTIME_ERROR,
                    "error.fail",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.entry")
                    );
        }

        //受信フィルタ実行処理
        new SmlReceiveFilter(ctx.getCon(), smlMdl)
            .doFilterJmail();

        //GSショートメールアプリ使用者にPush通知
        SmlPushSender pushSender = new SmlPushSender(ctx.getRequestModel(), ctx.getCon(),
                smlMdl.getAccountSidList(),
                paramMdl.getSml020Title(),
                smlMdl.getSmjSid(),
                sacMdl);
        pushSender.sendPush();

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(ctx.getCon(), ctx.getRequestModel());
        smlBiz.outPutApiLog(req, ctx.getCon(), ctx.getRequestUserSid(),
                this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE,
                                                  "アカウント:" + sacName
                                                  + "\n[title]" + param.getSubjectText());

        // リザルト
        SmlRestapiMailDao dao = new SmlRestapiMailDao(ctx.getCon());
        List<SmlRestapiMailModel> resList = new ArrayList<SmlRestapiMailModel>();
        ArrayList<SmailDetailModel> mailDetail = dao.selectSmeisDetailFromSid(smlMdl.getSmjSid());
        for (SmailDetailModel mdl : mailDetail) {
            SmlRestapiMailModel resMdl = new SmlRestapiMailModel();
            resMdl.setSid(mdl.getSmlSid());
            resMdl.setMailType(Integer.parseInt(GSConstSmail.TAB_DSP_MODE_SOSIN));
            resMdl.setBodyFormatFlg(mdl.getSmsType());
            resMdl.setOpenFlg(mdl.getSmjOpkbn());
            resMdl.setSubjectText(mdl.getSmsTitle());
            resMdl.setMarkType(mdl.getSmsMark());
            resMdl.setStatusType(mdl.getUsrJkbn());
            if (mdl.getSmsBody() != null) {
                resMdl.setBodyText(mdl.getSmsBody());
            }

            // 差出人アカウント情報
            resMdl.setSenderAccountSid(mdl.getAccountSid());
            resMdl.setSenderName(mdl.getAccountName());
            resMdl.setSenderUserDeleteFlg(mdl.getAccountJkbn());
            resMdl.setSenderLoginStopFlg(mdl.getUsrUkoFlg());

            // 送信先情報
            List<SmlRestapiMailAtesakiInfoModel> atsakiInfoList
                    = new ArrayList<SmlRestapiMailAtesakiInfoModel>();
            List<AtesakiModel>  retAts = mdl.getAtesakiList();
            if (retAts != null) {
                for (AtesakiModel atesakiMdl : retAts) {
                    SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                            = new SmlRestapiMailAtesakiInfoModel();
                    atesakiInfoMdl.setType(GSConstSmail.SML_SEND_KBN_ATESAKI);
                    atesakiInfoMdl.setAccountSid(atesakiMdl.getAccountSid());
                    atesakiInfoMdl.setName(atesakiMdl.getAccountName());
                    atesakiInfoMdl.setUserDeleteFlg(atesakiMdl.getAccountJkbn());
                    atesakiInfoMdl.setLoginStopFlg(atesakiMdl.getUsrUkoFlg());
                    atsakiInfoList.add(atesakiInfoMdl);
                }
            }
            retAts = mdl.getCcList();
            if (retAts != null) {
                for (AtesakiModel atesakiMdl : retAts) {
                    SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                            = new SmlRestapiMailAtesakiInfoModel();
                    atesakiInfoMdl.setType(GSConstSmail.SML_SEND_KBN_CC);
                    atesakiInfoMdl.setAccountSid(atesakiMdl.getAccountSid());
                    atesakiInfoMdl.setName(atesakiMdl.getAccountName());
                    atesakiInfoMdl.setUserDeleteFlg(atesakiMdl.getAccountJkbn());
                    atesakiInfoMdl.setLoginStopFlg(atesakiMdl.getUsrUkoFlg());
                    atsakiInfoList.add(atesakiInfoMdl);
                }
            }
            retAts = mdl.getBccList();
            if (retAts != null) {
                for (AtesakiModel atesakiMdl : retAts) {
                    SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                            = new SmlRestapiMailAtesakiInfoModel();
                    atesakiInfoMdl.setType(GSConstSmail.SML_SEND_KBN_BCC);
                    atesakiInfoMdl.setAccountSid(atesakiMdl.getAccountSid());
                    atesakiInfoMdl.setName(atesakiMdl.getAccountName());
                    atesakiInfoMdl.setUserDeleteFlg(atesakiMdl.getAccountJkbn());
                    atesakiInfoMdl.setLoginStopFlg(atesakiMdl.getUsrUkoFlg());
                    atsakiInfoList.add(atesakiInfoMdl);
                }
            }
            resMdl.setAtesakiArray(atsakiInfoList);

            // 送信日時
            String strSdate = null;
            if (mdl.getSmsSdate() != null) {
                strSdate =
                    UDateUtil.getSlashYYMD(mdl.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(mdl.getSmsSdate());
            }
            resMdl.setSendDateTime(strSdate);

            // 添付ファイル情報
            List<SmlRestapiMailTmpFileInfoModel> tmpFileInfoList
                    = new ArrayList<SmlRestapiMailTmpFileInfoModel>();

            SmlBinDao binDao = new SmlBinDao(ctx.getCon());
            ArrayList<CmnBinfModel> tmpFileList = binDao.getFileList(smlMdl.getSmjSid());

            for (CmnBinfModel binMdl : tmpFileList) {
                SmlRestapiMailTmpFileInfoModel tmpFileInfoMdl
                        = new SmlRestapiMailTmpFileInfoModel();
                tmpFileInfoMdl.setBinSid(binMdl.getBinSid());
                tmpFileInfoMdl.setFileName(binMdl.getBinFileName());
                tmpFileInfoMdl.setFileSizeByteNum((int) binMdl.getBinFileSize());
                tmpFileInfoList.add(tmpFileInfoMdl);
            }
            resMdl.setTmpFileArray(tmpFileInfoList);

            resList.add(resMdl);
        }

        return resList;
    }

    /**
     * <br>[機  能] 草稿保存処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param param パラメータモデル
     * @param ctx コンテキスト
     * @param draftSid 元となる草稿メールSID
     * @param tempPath テンポラリパス
     * @return メール情報
     * @throws SQLException
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     */
    public List<SmlRestapiDraftMailModel> saveMail(
            HttpServletRequest req,
            SmlAccountsMailsPostParamModel param,
            RestApiContext ctx,
            int draftSid,
            String tempPath)
                    throws Exception, SQLException,
                    IOToolsException, IOException, TempFileException {

        // フォームへ情報セット(PCブラウザと共通処理を使用)
        Sml020Form sml020Form = new Sml020Form();
        sml020Form.setSml020ProcMode(String.valueOf(param.getProcType()));
        sml020Form.setSml020MailType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL); // メールタイプは固定
        sml020Form.setSml010EditSid(draftSid); //草稿メールSID
        sml020Form.setSml010SelectedSid(param.getRefarenceSid());
        sml020Form.setSml020Title(NullDefault.getString(param.getSubjectText(), ""));
        sml020Form.setSmlViewAccount(param.getAccountSid());
        sml020Form.setSml020SendAccount(param.getAccountSid());
        sml020Form.setSml020Mark(param.getMarkType());
        sml020Form.setSml020Body(param.getBodyText());
        sml020Form.setSml020userSid(convertSacSidList(param.getToArray(), null));
        sml020Form.setSml020userSidCc(convertSacSidList(param.getCcArray(), null));
        sml020Form.setSml020userSidBcc(convertSacSidList(param.getBccArray(), null));


        // メール情報をDBに登録
        MlCountMtController cntCon = new MlCountMtController();
        try {
            cntCon = getCountMtController(ctx.getRequestModel());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Sml020ParamModel paramMdl = new Sml020ParamModel();
        paramMdl.setParam(sml020Form);
        int mailSid = insertSitagakiData(paramMdl, ctx, cntCon, req,
                                  param.getBinSidArray(), tempPath);
        // 結果判定
        if (paramMdl.getErrorsList() == null || paramMdl.getErrorsList().size() == 0) {
            // エラーなし → データ更新OK
            ctx.getCon().commit();
        } else {
            // 登録処理でエラー
            throw new RestApiValidateException(
                    EnumError.SYS_RUNTIME_ERROR,
                    "error.fail",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.entry")
                    );
        }

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(ctx.getCon(), ctx.getRequestModel());
        smlBiz.outPutApiLog(req, ctx.getCon(), ctx.getRequestUserModel().getUsrsid(),
                this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE, "");

        // リザルト
        List<SmlRestapiDraftMailModel> resList = new ArrayList<SmlRestapiDraftMailModel>();
        SmlRestapiMailDao dao = new SmlRestapiMailDao(ctx.getCon());
        ArrayList<SmailDetailModel> mailDetail = dao.selectWmeisDetail(
                param.getAccountSid(), mailSid, GSConst.JTKBN_TOROKU);
        for (SmailDetailModel mdl : mailDetail) {
            SmlRestapiDraftMailModel resMdl = new SmlRestapiDraftMailModel();
            resMdl.setSid(mdl.getSmlSid());
            resMdl.setMailType(NullDefault.getInt(mdl.getMailKbn(), 0));
            resMdl.setBodyFormatFlg(mdl.getSmsType());
            resMdl.setOpenFlg(mdl.getSmjOpkbn());
            resMdl.setSubjectText(mdl.getSmsTitle());
            resMdl.setMarkType(mdl.getSmsMark());
            resMdl.setStatusType(mdl.getUsrJkbn());
            if (mdl.getSmsBody() != null) {
                resMdl.setBodyText(mdl.getSmsBody());
            }

            // 送信先情報
            List<SmlRestapiMailAtesakiInfoModel> atsakiInfoList
                    = new ArrayList<SmlRestapiMailAtesakiInfoModel>();
            List<AtesakiModel>  retAts = mdl.getAtesakiList();
            if (retAts != null) {
                for (AtesakiModel atesakiMdl : retAts) {
                    SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                            = new SmlRestapiMailAtesakiInfoModel();
                    atesakiInfoMdl.setType(GSConstSmail.SML_SEND_KBN_ATESAKI);
                    atesakiInfoMdl.setAccountSid(atesakiMdl.getAccountSid());
                    atesakiInfoMdl.setName(atesakiMdl.getAccountName());
                    atesakiInfoMdl.setUserDeleteFlg(atesakiMdl.getAccountJkbn());
                    atesakiInfoMdl.setLoginStopFlg(atesakiMdl.getUsrUkoFlg());
                    atsakiInfoList.add(atesakiInfoMdl);
                }
            }
            retAts = mdl.getCcList();
            if (retAts != null) {
                for (AtesakiModel atesakiMdl : retAts) {
                    SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                            = new SmlRestapiMailAtesakiInfoModel();
                    atesakiInfoMdl.setType(GSConstSmail.SML_SEND_KBN_CC);
                    atesakiInfoMdl.setAccountSid(atesakiMdl.getAccountSid());
                    atesakiInfoMdl.setName(atesakiMdl.getAccountName());
                    atesakiInfoMdl.setUserDeleteFlg(atesakiMdl.getAccountJkbn());
                    atesakiInfoMdl.setLoginStopFlg(atesakiMdl.getUsrUkoFlg());
                    atsakiInfoList.add(atesakiInfoMdl);
                }
            }
            retAts = mdl.getBccList();
            if (retAts != null) {
                for (AtesakiModel atesakiMdl : retAts) {
                    SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                            = new SmlRestapiMailAtesakiInfoModel();
                    atesakiInfoMdl.setType(GSConstSmail.SML_SEND_KBN_BCC);
                    atesakiInfoMdl.setAccountSid(atesakiMdl.getAccountSid());
                    atesakiInfoMdl.setName(atesakiMdl.getAccountName());
                    atesakiInfoMdl.setUserDeleteFlg(atesakiMdl.getAccountJkbn());
                    atesakiInfoMdl.setLoginStopFlg(atesakiMdl.getUsrUkoFlg());
                    atsakiInfoList.add(atesakiInfoMdl);
                }
            }
            resMdl.setAtesakiArray(atsakiInfoList);

            // 添付ファイル情報
            List<SmlRestapiMailTmpFileInfoModel> tmpFileInfoList
                    = new ArrayList<SmlRestapiMailTmpFileInfoModel>();

            SmlBinDao binDao = new SmlBinDao(ctx.getCon());
            ArrayList<CmnBinfModel> tmpFileList = binDao.getFileList(mailSid);

            for (CmnBinfModel binMdl : tmpFileList) {
                SmlRestapiMailTmpFileInfoModel tmpFileInfoMdl
                        = new SmlRestapiMailTmpFileInfoModel();
                tmpFileInfoMdl.setBinSid(binMdl.getBinSid());
                tmpFileInfoMdl.setFileName(binMdl.getBinFileName());
                tmpFileInfoMdl.setFileSizeByteNum((int) binMdl.getBinFileSize());
                tmpFileInfoList.add(tmpFileInfoMdl);
            }
            resMdl.setTmpFileArray(tmpFileInfoList);

            resList.add(resMdl);
        }
        return resList;
    }

    /**
     * <br>[機  能] 作成されたメールデータを下書きとして登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param ctx コンテキスト
     * @param ctrl 採番用コネクション
     * @param req リクエスト
     * @param binSidArray 元メールの添付ファイル バイナリSID
     * @param tempPath テンポラリパス
     * @return メールSID
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public int insertSitagakiData(Sml020ParamModel paramMdl,
                                    RestApiContext ctx,
                                    MlCountMtController ctrl,
                                    HttpServletRequest req,
                                    long[] binSidArray,
                                    String tempPath)
        throws SQLException, IOToolsException, IOException, TempFileException {

        int mailSid = -1;
        UDate now = new UDate();
        CommonBiz biz = new CommonBiz();

        //転送, 草稿保存, 複写の時、元メールに存在する添付ファイルから指定したものを引き継ぐ
        if (binSidArray != null) {
            CommonBiz cmnBiz = new CommonBiz();
            String dateStr = now.getDateString();
            String domain = GroupSession.getResourceManager().getDomain(req);

            //既に登録されているファイルが存在する場合、ファイル番号を最新からにする
            int i = 1;
            List < String > fileList = IOTools.getFileNames(tempPath);
            if (fileList != null) {
                i += fileList.size();
            }
            for (long binSid : binSidArray) {
                CmnBinfModel binMdl = cmnBiz.getBinInfo(ctx.getCon(), binSid, domain);
                if (binMdl != null) {
                    //添付ファイルをテンポラリディレクトリにコピーする。
                    cmnBiz.saveTempFile(dateStr, binMdl, ctx.getAppRootPath(), tempPath, i);
                    i++;
                }
            }
        }
        //添付ファイルを登録
        List<String> binList = new ArrayList<String>();
        try {
            List<Long> binSidLongList = biz.insertBinInfo(
                    ctx.getCon(), tempPath,
                    ctx.getAppRootPath(),
                    ctrl,
                    ctx.getRequestUserSid(),
                    new UDate())
                    .stream()
                    .map(str -> Long.parseLong(str))
                    .collect(Collectors.toList());
            for (Long sid : binSidLongList) {
                binList.add(String.valueOf(sid));
            }
        } catch (TempFileException e) {
            throw new RuntimeException("添付ファイル登録に失敗", e);
        }

        //下書きから作成 → 再度下書き保存 の場合(つまり下書きデータの更新)
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(ctx.getCon());
        long delSize = 0;
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {

            mailSid = paramMdl.getSml010EditSid();

            //ショートメール情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertSoukouDataSize(Arrays.asList(mailSid), false);

            //下書きを削除
            SmlWmeisModel wparam = new SmlWmeisModel();
            wparam.setSmwSid(mailSid);
            SmlWmeisDao wdao = new SmlWmeisDao(ctx.getCon());

            //削除前の容量を取得
            Map<SmlWmeisModel, Long> wMap = wdao.getDeleteMail(Arrays.asList(mailSid), 1, 1, 0);
            for (Map.Entry<SmlWmeisModel, Long> map : wMap.entrySet()) {
                delSize = map.getValue();
            }

            wdao.delete(wparam);

            //下書き宛先を削除
            SmlAsakModel aparam = new SmlAsakModel();
            aparam.setSmsSid(mailSid);
            SmlAsakDao adao = new SmlAsakDao(ctx.getCon());
            adao.deleteFromMailSid(aparam);

            //ショートメールに送付されているバイナリSID一覧取得
            SmlBinDao sbinDao = new SmlBinDao(ctx.getCon());
            String[] mailSidList = new String[1];
            mailSidList[0] = String.valueOf(mailSid);
            List<Long> binSidList =
                sbinDao.selectBinSidList(mailSidList);

            //バイナリ情報を論理削除
            CmnBinfDao binDao = new CmnBinfDao(ctx.getCon());
            CmnBinfModel cbMdl = new CmnBinfModel();
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(ctx.getRequestUserSid());
            cbMdl.setBinUpdate(new UDate());
            binDao.updateJKbn(cbMdl, binSidList);

            //添付情報を削除
            sbinDao.deleteSmlBin(mailSid);
        }

        //SID採番
        mailSid =
            (int) ctrl.getSaibanNumber(
                    GSConstSmail.SAIBAN_SML_SID,
                    GSConstSmail.SAIBAN_SUB_MAIL_SID,
                    ctx.getRequestUserSid());

        //メールサイズ取得
        Long titile_byte = (long) 0;
        Long body_byte = (long) 0;
        Long file_byte = (long) 0;

        try {
            if (NullDefault.getString(
                    paramMdl.getSml020Title(), "").getBytes("UTF-8").length != 0) {
                titile_byte = Long.valueOf(
                        NullDefault.getString(
                                paramMdl.getSml020Title(), "").getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            titile_byte = Long.valueOf(
                    NullDefault.getString(
                            paramMdl.getSml020Title(), "").getBytes().length);
        }

        try {
            if (NullDefault.getString(
                    paramMdl.getSml020Body(), "").getBytes("UTF-8").length != 0) {
                body_byte = Long.valueOf(
                        NullDefault.getString(
                                paramMdl.getSml020Body(), "").getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            body_byte = Long.valueOf(
                    NullDefault.getString(
                            paramMdl.getSml020Body(), "").getBytes().length);
        }

        file_byte = biz.getTempFileSize(tempPath);


        //下書きテーブルにデータ作成
        SmlWmeisModel wparam = new SmlWmeisModel();
        wparam.setSacSid(paramMdl.getSml020SendAccount());
        wparam.setSmwSid(mailSid);
        wparam.setSmwTitle(NullDefault.getString(paramMdl.getSml020Title(), ""));
        wparam.setSmwMark(paramMdl.getSml020Mark());
        if (paramMdl.getSml020MailType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            wparam.setSmwBody(NullDefault.getString(paramMdl.getSml020Body(), ""));
            wparam.setSmwBodyPlain("");
        } else {
            wparam.setSmwBody(NullDefault.getString(paramMdl.getSml020BodyHtml(), ""));
            wparam.setSmwBodyPlain(StringUtilHtml.deleteHtmlTag(
                    NullDefault.getString(paramMdl.getSml020BodyHtml(), "")));
        }
        int editKbn = 0;
        int editSid = 0;
        /** 返信・全返信・転送時 **/
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)
                || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
            editKbn = Integer.parseInt(paramMdl.getSml020ProcMode());
            editSid = paramMdl.getSml010SelectedSid();
        }
        wparam.setSmwType(paramMdl.getSml020MailType());
        wparam.setSmwSize(titile_byte + body_byte + file_byte);
        wparam.setSmwJkbn(GSConst.JTKBN_TOROKU);
        wparam.setSmwOrigin(editSid);
        wparam.setSmwEditKbn(editKbn);
        wparam.setSmwAuid(ctx.getRequestUserSid());
        wparam.setSmwAdate(now);
        wparam.setSmwEuid(ctx.getRequestUserSid());
        wparam.setSmwEdate(now);
        SmlWmeisDao wdao = new SmlWmeisDao(ctx.getCon());
        wdao.insert(wparam);

        //ショートメール送付情報を登録
        SmlBinDao sbinDao = new SmlBinDao(ctx.getCon());
        SmlSmeisModel sparam = new SmlSmeisModel();
        sparam.setSmsSid(mailSid);
        sbinDao.insertSmlBin(sparam, binList);

        SmlCommonBiz smlBiz = new SmlCommonBiz();

        //宛先テーブルにデータ作成
        String[] userSids = paramMdl.getSml020userSid();
        //ユーザSIDからアカウントのSIDを取得
        String[] accountSids = null;
        accountSids = smlBiz.getAccountSidFromUsr(ctx.getCon(), userSids);

        SmlAsakDao adao = new SmlAsakDao(ctx.getCon());
        SmlAsakModel aparam = null;
        if (accountSids != null && accountSids.length > 0) {
            for (int i = 0; i < accountSids.length; i++) {
                aparam = new SmlAsakModel();
                aparam.setSacSid(Integer.parseInt(accountSids[i]));
                aparam.setSmsSid(mailSid);
                aparam.setSmjSendkbn(GSConstSmail.SML_SEND_KBN_ATESAKI);
                aparam.setSmsAuid(ctx.getRequestUserSid());
                aparam.setSmsAdate(now);
                aparam.setSmsEuid(ctx.getRequestUserSid());
                aparam.setSmsEdate(now);
                adao.insert(aparam);
            }
        }

        //CC
        String[] userSidsCc = paramMdl.getSml020userSidCc();
        //ユーザSIDからアカウントのSIDを取得
        String[] accountSidsCc = null;
        accountSidsCc = smlBiz.getAccountSidFromUsr(ctx.getCon(), userSidsCc);

        if (accountSidsCc != null && accountSidsCc.length > 0) {
            for (int i = 0; i < accountSidsCc.length; i++) {
                aparam = new SmlAsakModel();
                aparam.setSacSid(Integer.parseInt(accountSidsCc[i]));
                aparam.setSmsSid(mailSid);
                aparam.setSmjSendkbn(GSConstSmail.SML_SEND_KBN_CC);
                aparam.setSmsAuid(ctx.getRequestUserSid());
                aparam.setSmsAdate(now);
                aparam.setSmsEuid(ctx.getRequestUserSid());
                aparam.setSmsEdate(now);
                adao.insert(aparam);
            }
        }

        //BCC
        String[] userSidBcc = paramMdl.getSml020userSidBcc();
        //ユーザSIDからアカウントのSIDを取得
        String[] accountSidsBcc = null;
        accountSidsBcc = smlBiz.getAccountSidFromUsr(ctx.getCon(), userSidBcc);

        if (accountSidsBcc != null && accountSidsBcc.length > 0) {
            for (int i = 0; i < accountSidsBcc.length; i++) {
                aparam = new SmlAsakModel();
                aparam.setSacSid(Integer.parseInt(accountSidsBcc[i]));
                aparam.setSmsSid(mailSid);
                aparam.setSmjSendkbn(GSConstSmail.SML_SEND_KBN_BCC);
                aparam.setSmsAuid(ctx.getRequestUserSid());
                aparam.setSmsAdate(now);
                aparam.setSmsEuid(ctx.getRequestUserSid());
                aparam.setSmsEdate(now);
                adao.insert(aparam);
            }
        }

        //ディスク容量を更新
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        long sum = titile_byte + body_byte + file_byte - delSize;
        smlCmnBiz.updateAccountDiskSize(ctx.getCon(), paramMdl.getSmlViewAccount(), sum);

        //ショートメール情報のデータ使用量を登録
        usedDataBiz.insertSoukouDataSize(Arrays.asList(mailSid), true);

        return mailSid;
    }

    /**
     * <br>[機  能] 作成されたメールデータを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param ctx コンテキスト
     * @param ctrl 採番用コネクション
     * @param req リクエスト
     * @param pluginConfig PluginConfig
     * @param binSidArray バイナリSIDリスト
     * @param tempPath テンポラリパス
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return sendMdl 送信情報
     */
    public SmailSendModel insertMailData(
            Sml020knParamModel paramMdl,
            RestApiContext ctx,
            MlCountMtController ctrl,
            HttpServletRequest req,
            PluginConfig pluginConfig,
            long[] binSidArray,
            String tempPath)
                    throws
                    ClassNotFoundException,
                    IllegalAccessException,
                    InstantiationException,
                    SQLException,
                    IOToolsException,
                    IOException,
                    TempFileException {

        SmailSendModel sendMdl = new SmailSendModel();

        CommonBiz biz = new CommonBiz();
        UDate now = new UDate();
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(ctx.getRequestModel());
        SmlAdminModel adminConf = smlCmnBiz.getSmailAdminConf(
                ctx.getRequestUserSid(), ctx.getCon());

        SmlAccountDao sacDao = new SmlAccountDao(ctx.getCon());
        sendMdl.setSacMdl(
            sacDao.select(
                paramMdl.getSml020SendAccount()
                )
            );
    

        //転送, 草稿保存, 複写の時、元メールに存在する添付ファイルから指定したものを引き継ぐ
        if (binSidArray != null) {
            CommonBiz cmnBiz = new CommonBiz();
            String dateStr = now.getDateString();
            String domain = GroupSession.getResourceManager().getDomain(req);

            //既に登録されているファイルが存在する場合、ファイル番号を最新からにする
            int i = 1;
            List < String > fileList = IOTools.getFileNames(tempPath);
            if (fileList != null) {
                i += fileList.size();
            }
            for (long binSid : binSidArray) {
                CmnBinfModel binMdl = cmnBiz.getBinInfo(ctx.getCon(), binSid, domain);
                if (binMdl != null) {
                    //添付ファイルをテンポラリディレクトリにコピーする。
                    cmnBiz.saveTempFile(dateStr, binMdl, ctx.getAppRootPath(), tempPath, i);
                    i++;
                }
            }
        }
        //添付ファイルを登録
        List<String> binList = new ArrayList<String>();
        try {
            List<Long> binSidLongList = biz.insertBinInfo(
                    ctx.getCon(), tempPath,
                    ctx.getAppRootPath(),
                    ctrl,
                    ctx.getRequestUserSid(),
                    new UDate())
                    .stream()
                    .map(str -> Long.parseLong(str))
                    .collect(Collectors.toList());
            for (Long sid : binSidLongList) {
                binList.add(String.valueOf(sid));
            }
        } catch (TempFileException e) {
            throw new RuntimeException("添付ファイル登録に失敗", e);
        }

        //SID採番
        int mailSid =
                (int) ctrl.getSaibanNumber(
                        GSConstSmail.SAIBAN_SML_SID,
                        GSConstSmail.SAIBAN_SUB_MAIL_SID,
                        ctx.getRequestUserSid());

        //メールサイズ取得
        Long titile_byte = (long) 0;
        Long body_byte = (long) 0;
        Long file_byte = (long) 0;

        String bodyStr = "";
        String bodyPlainStr = "";

        if (paramMdl.getSml020MailType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            bodyStr = paramMdl.getSml020Body();
        } else {
            bodyStr = paramMdl.getSml020BodyHtml();
            bodyPlainStr = StringUtilHtml.deleteHtmlTag(bodyStr);
        }

        try {
            if (paramMdl.getSml020Title().getBytes("UTF-8").length != 0) {
                titile_byte = Long.valueOf(
                        paramMdl.getSml020Title().getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            titile_byte = Long.valueOf(
                    paramMdl.getSml020Title().getBytes().length);
        }

        try {
            if (bodyStr.getBytes("UTF-8").length != 0) {
                body_byte = Long.valueOf(
                        bodyStr.getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            body_byte = Long.valueOf(
                    bodyStr.getBytes().length);
        }

        file_byte = biz.getTempFileSize(tempPath);


        //送信テーブルにデータ作成
        SmlSmeisModel sparam = new SmlSmeisModel();
        sparam.setSacSid(paramMdl.getSml020SendAccount());
        sparam.setSmsSid(mailSid);
        sparam.setSmsSdate(now);
        sparam.setSmsTitle(paramMdl.getSml020Title());
        sparam.setSmsMark(paramMdl.getSml020Mark());
        sparam.setSmsBody(bodyStr);
        sparam.setSmsBodyPlain(bodyPlainStr);
        sparam.setSmsType(paramMdl.getSml020MailType());
        long addSize = titile_byte + body_byte + file_byte;
        sparam.setSmsSize(addSize);
        sparam.setSmsType(paramMdl.getSml020MailType());
        sparam.setSmsJkbn(GSConst.JTKBN_TOROKU);
        sparam.setSmsAuid(ctx.getRequestUserSid());
        sparam.setSmsAdate(now);
        sparam.setSmsEuid(ctx.getRequestUserSid());
        sparam.setSmsEdate(now);
        SmlSmeisDao sdao = new SmlSmeisDao(ctx.getCon());
        sdao.insert(sparam);

        SmlJmeisDao jdao = new SmlJmeisDao(ctx.getCon());

        /** 返信・全返信時には受信メールにわかるようにフィールドデータ変更  **/
        int editMailSid = paramMdl.getSml010EditSid();
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            int kbn = Integer.parseInt(paramMdl.getSml020ProcMode());
            jdao.updateHenshin(kbn, paramMdl.getSmlViewAccount(), editMailSid);
        }

        /** 転送時には受信メールにわかるようにフィールドデータ変更  **/
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
            int kbn = GSConstSmail.SML_FW;
            jdao.updateFw(kbn, paramMdl.getSmlViewAccount(), editMailSid);
        }

        /** 草稿メールからの編集且つ返信・転送メールの場合も受信メール情報を変更 */
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
            SmlWmeisDao wDao = new SmlWmeisDao(ctx.getCon());
            SmlWmeisModel wMdl = wDao.select(editMailSid);

            if (wMdl != null && wMdl.getSmwOrigin() > 0) {
                String editKbn = String.valueOf(wMdl.getSmwEditKbn());

                if (editKbn.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                        || editKbn.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
                    jdao.updateHenshin(
                            wMdl.getSmwEditKbn(),
                            paramMdl.getSmlViewAccount(),
                            wMdl.getSmwOrigin());
                }
                if (editKbn.equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
                    jdao.updateFw(
                            GSConstSmail.SML_FW,
                            paramMdl.getSmlViewAccount(),
                            wMdl.getSmwOrigin());
                }
            }
        }

        //受信テーブルにデータ作成
        String[] accountSidAtesaki = smlCmnBiz.getAccountSidFromUsr(
                ctx.getCon(), paramMdl.getSml020userSid());

        String[] accountSidCc = smlCmnBiz.getAccountSidFromUsr(
                ctx.getCon(), paramMdl.getSml020userSidCc());
        String[] accountSidBcc = smlCmnBiz.getAccountSidFromUsr(
                ctx.getCon(), paramMdl.getSml020userSidBcc());

        ArrayList<String[]> accountSidList = new ArrayList<String[]>();
        ArrayList<Integer> sendKbnList = new ArrayList<Integer>();
        accountSidList.add(accountSidAtesaki);
        sendKbnList.add(GSConstSmail.SML_SEND_KBN_ATESAKI);

        if (accountSidCc != null && accountSidCc.length > 0) {
            accountSidList.add(accountSidCc);
            sendKbnList.add(GSConstSmail.SML_SEND_KBN_CC);
        }
        if (accountSidBcc != null && accountSidBcc.length > 0) {
            accountSidList.add(accountSidBcc);
            sendKbnList.add(GSConstSmail.SML_SEND_KBN_BCC);
        }

        //受信メール登録前に送信メールの集計データを登録する
        String[] cntAccountSid = null;
        ArrayList<String> cntAllAccountSidList = new ArrayList<String>();
        int cntAtesaki = 0;
        int cntCc = 0;
        int cntBcc = 0;
        for (int n = 0; n < accountSidList.size(); n++) {
            cntAccountSid = accountSidList.get(n);
            for (int i = 0; i < cntAccountSid.length; i++) {
                if (cntAllAccountSidList.contains(cntAccountSid[i])) {
                    continue;
                }
                cntAllAccountSidList.add(cntAccountSid[i]);
                if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_ATESAKI) {
                    cntAtesaki++;
                } else if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_CC) {
                    cntCc++;
                } else if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_BCC) {
                    cntBcc++;
                }
            }
        }

        SmlCommonBiz smlBiz = new SmlCommonBiz();
        smlBiz.regSmeisLogCnt(ctx.getCon(), paramMdl.getSmlViewAccount(),
                cntAtesaki, cntCc, cntBcc, now);

        String[] accountSid = null;
        ArrayList<String> allAccountSidList = new ArrayList<String>();
        List<Integer> sendAccountList = new ArrayList<Integer>();

        ArrayList<SmlJmeisModel> jparamList = new ArrayList<SmlJmeisModel>();
        for (int n = 0; n < accountSidList.size(); n++) {

            accountSid = accountSidList.get(n);
            for (int i = 0; i < accountSid.length; i++) {
                if (allAccountSidList.contains(accountSid[i])) {
                    //一度送信したユーザを除く
                    continue;
                }
                allAccountSidList.add(accountSid[i]);

                SmlJmeisModel jparam = new SmlJmeisModel();
                jparam.setSacSid(Integer.parseInt(accountSid[i]));
                jparam.setSmjSid(mailSid);
                jparam.setSmjOpkbn(GSConstSmail.OPKBN_UNOPENED);
                jparam.setSmjFwkbn(GSConstSmail.FWKBN_NO);
                jparam.setSmjOpdate(null);
                jparam.setSmjJkbn(GSConst.JTKBN_TOROKU);
                jparam.setSmjSendkbn(sendKbnList.get(n));
                jparam.setSmjAuid(ctx.getRequestUserSid());
                jparam.setSmjAdate(now);
                jparam.setSmjEuid(ctx.getRequestUserSid());
                jparam.setSmjEdate(now);
                jparamList.add(jparam);
            }
        }

        //メール送信、それに伴う処理でデッドロックが発生しないよう
        //SIDの降順に並び替え実行
        jparamList = smlCmnBiz.setOrderBySidDescMdl(jparamList);
        for (SmlJmeisModel jparam:jparamList) {
            //受信メール登録
            jdao.insert(jparam);
            sendAccountList.add(jparam.getSacSid());
            //アカウントディスク使用量の再計算を行う
            smlBiz.updateAccountDiskSize(ctx.getCon(), jparam.getSacSid(), addSize);
            //受信メールの集計データを登録する
            smlBiz.regJmeisLogCnt(ctx.getCon(), jparam.getSacSid(), jparam.getSmjSendkbn(), now);
        }

        sendMdl.setSmjSid(mailSid);
        sendMdl.setAccountSidList(sendAccountList);

        //転送設定を取得し必要に応じてE-mailにて転送
        List<TempFileModel> fileList = biz.getTempFiles(tempPath);
        SmailDao smaildao = new SmailDao(ctx.getCon());
        ArrayList<SmailDetailModel> sdList =
                smaildao.selectSmeisDetailFromSid(sparam.getSmsSid());
        int fwkbn = 0;
        SmlJmeisModel jparam = null;

        if (sparam.getSmsType() == GSConstSmail.SAC_SEND_MAILTYPE_HTML) {
            //HTML形式の場合はattach.htmlを作成
            SmlMailFileModel fileMdl = new SmlMailFileModel();
            fileMdl.setFileName(GSConstSmail.HTMLMAIL_FILENAME);
            fileMdl.setContentType(
                    "Content-Type: text/html; charset=" + GSConstSmail.ATTACH_ENCODE);
            fileMdl.setFilePath(tempPath + "/" + GSConstSmail.HTMLMAIL_FILENAME);
            fileMdl.setHtmlMail(true);

            PrintWriter pw = null;
            try {
                IOTools.isDirCheck(tempPath, true);
                pw = new PrintWriter(new BufferedOutputStream(
                        new FileOutputStream(fileMdl.getFilePath())));
                pw.print(sparam.getSmsBody());
                pw.flush();
            } catch (Exception e) {
                throw new IOToolsException("HTMLメールの保存に失敗", e);
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }

            if (fileList == null || fileList.isEmpty()) {
                fileList = new ArrayList<TempFileModel>();
            }

            TempFileModel tempFileMdl = new TempFileModel();
            File file = new File(tempPath, GSConstSmail.HTMLMAIL_FILENAME);
            tempFileMdl.setFile(file);
            tempFileMdl.setFileName(GSConstSmail.HTMLMAIL_FILENAME);
            fileList.add(tempFileMdl);

            //メール送信用のテキストに変換
            bodyStr = StringUtilHtml.transToText(
                    StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                            StringUtilHtml.transBRtoCRLF(bodyStr)));
            if (sdList != null && !sdList.isEmpty()) {
                sdList.get(0).setSmsBody(bodyStr);
            }
        }

        //デッドロックが発生しないようアカウントSIDを降順に統一
        allAccountSidList = smlCmnBiz.setOrderBySidDescStr(allAccountSidList);
        for (int i = 0; i < allAccountSidList.size(); i++) {

            fwkbn = smlCmnBiz.sendSmailForward(
                    sparam,
                    sdList,
                    Integer.parseInt(allAccountSidList.get(i)),
                    fileList,
                    adminConf,
                    pluginConfig,
                    ctx.getCon());

            if (fwkbn == GSConstSmail.ERROR_KBN) {
                continue;
            }

            if (fwkbn > 0) {
                jparam = new SmlJmeisModel();
                jparam.setSacSid(Integer.parseInt(allAccountSidList.get(i)));
                jparam.setSmjSid(mailSid);
                jparam.setSmjFwkbn(GSConstSmail.FWKBN_OK);
                jdao.updateFwkbn(jparam);
            }

        }

        SmlBinDao sbinDao = new SmlBinDao(ctx.getCon());

        //草稿モードの場合、草稿データと添付情報を削除(物理)
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(ctx.getCon());
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)
                && editMailSid > 0) {

            //ショートメール情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertSoukouDataSize(Arrays.asList(editMailSid), false);

            //下書きを削除
            SmlWmeisModel wparam = new SmlWmeisModel();
            wparam.setSmwSid(editMailSid);
            SmlWmeisDao wdao = new SmlWmeisDao(ctx.getCon());
            //削除前に容量を取得し、集計
            Map<SmlWmeisModel, Long> wMap = wdao.getDeleteMail(
                    Arrays.asList(editMailSid), 1, 1, 0);
            long delSize = 0;
            for (Map.Entry<SmlWmeisModel, Long> map : wMap.entrySet()) {
                delSize = map.getValue();
            }
            smlCmnBiz.updateAccountDiskSize(ctx.getCon(), paramMdl.getSmlViewAccount(), -delSize);

            wdao.delete(wparam);

            //下書き宛先を削除
            SmlAsakModel aparam = new SmlAsakModel();
            aparam.setSmsSid(editMailSid);
            SmlAsakDao adao = new SmlAsakDao(ctx.getCon());
            adao.deleteFromMailSid(aparam);

            //ショートメールに送付されているバイナリSID一覧取得
            String[] mailSidList = new String[1];
            mailSidList[0] = String.valueOf(editMailSid);
            List<Long> binSidList =
                    sbinDao.selectBinSidList(mailSidList);

            //バイナリ情報を論理削除
            CmnBinfDao binDao = new CmnBinfDao(ctx.getCon());
            CmnBinfModel cbMdl = new CmnBinfModel();
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(ctx.getRequestUserSid());
            cbMdl.setBinUpdate(new UDate());
            binDao.updateJKbn(cbMdl, binSidList);

            //添付情報を削除
            sbinDao.deleteSmlBin(editMailSid);
        }

        //ショートメール送付情報を登録
        sbinDao.insertSmlBin(sparam, binList);

        //ショートメール情報のデータ使用量を登録
        usedDataBiz.insertSendDataSize(mailSid, true);

        //ディスク容量を更新
        smlCmnBiz.updateAccountDiskSize(ctx.getCon(), paramMdl.getSmlViewAccount(), addSize);

        return sendMdl;
    }
}
