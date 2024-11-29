package jp.groupsession.v2.cmn.cmn110;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル添付ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn110Action extends AbstractGsAction {

    /** ファイルダウンロード attachment */
    private static final int DOWNLOAD_ATTACHMENT__ = 0;
    /** ファイルダウンロード inline */
    private static final int DOWNLOAD_INLINE__ = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn110Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
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

        if (cmd.equals("fileDownload") || cmd.equals("fileInlineDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }

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

        log__.debug("START_Cmn110");
        ActionForward forward = null;

        Cmn110Form thisForm = (Cmn110Form) form;

        //ファイルサイズがMAXサイズを超えた場合、共通メッセージ画面でエラーメッセージを表示
        Object obj = req.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
        if (obj != null) {
            Boolean maxlength = (Boolean) obj;
            if (maxlength.booleanValue()) {
                return getFileSizeErrorPage(map, req, res);
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        if (cmd.equals("fileUpload")) {
            log__.debug("確定ボタンクリック");
            Cmn110ValidateResultModel resultMdl = __doUpload(map, thisForm, req, res, con);
            if (thisForm.getCmn110uploadType() == Cmn110Form.UPLOADTYPE_DROP) {
                StringBuilder strBuild = new StringBuilder("{");
                List<String> errMsgList = resultMdl.getErrMessageList();

                if (errMsgList != null && !errMsgList.isEmpty()) {
                    strBuild.append("\"errors\" : \"1\",");
                    strBuild.append("\"errorMsg\" : [");
                    int errIdx = 0;
                    for (String errMsg : errMsgList) {
                        if (errIdx > 0) {
                            strBuild.append(",");
                        }

                        strBuild.append("\""
                                    + Cmn110Biz.escapeText(errMsg)
                                    + "\"");

                        errIdx++;
                    }
                    strBuild.append("]");
                } else {
                    strBuild.append("\"errors\" : \"0\"");
                }

                if (thisForm.getCmn110tempName() != null
                    && thisForm.getCmn110tempName().length > 0) {
                    strBuild.append(",\"tempName\" : [");
                    for (int idx = 0; idx < thisForm.getCmn110tempName().length; idx++) {
                        if (idx > 0) {
                            strBuild.append(",");
                        }

                        String tempName = thisForm.getCmn110tempName()[idx];
                        strBuild.append("\""
                                    + Cmn110Biz.escapeText(tempName)
                                    + "\"");
                    }
                    strBuild.append("],");
                    strBuild.append("\"tempSaveName\" : [");
                    for (int idx = 0; idx < thisForm.getCmn110tempSaveName().length; idx++) {
                        if (idx > 0) {
                            strBuild.append(",");
                        }

                        String saveTempName = thisForm.getCmn110tempSaveName()[idx];
                        strBuild.append("\""
                            + Cmn110Biz.escapeText(saveTempName)
                            + "\"");
                    }
                    strBuild.append("]");
                }
                strBuild.append("}");

                PrintWriter writer = null;
                try {
                    res.setContentType("text/json; charset=UTF-8");
                    writer = res.getWriter();
                    writer.write(strBuild.toString());
                    writer.flush();
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
                forward = null;
            } else {
                forward = __doInit(map, thisForm, req, res, con);
            }

        } else if (cmd.equals("fileDownload")) {
            //ファイルダウンロード
            forward = __doDownLoad(map, thisForm, req, res, con, DOWNLOAD_ATTACHMENT__);
        } else if (cmd.equals("fileInlineDownload")) {
            //ファイルダウンロード(inline)
            forward = __doDownLoad(map, thisForm, req, res, con, DOWNLOAD_INLINE__);
        } else if (cmd.equals("fileDelete")) {
            //ファイル削除
            forward = __doDeleteFile(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cmn110");
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
     * @throws IOToolsException 添付ファイル操作時例外
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cmn110Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        Cmn110ParamModel paramModel = new Cmn110ParamModel();
        paramModel.setParam(form);

        String tempDir = Cmn110Biz.getTempDir(getRequestModel(req), paramModel);

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Cmn110Biz biz = new Cmn110Biz();
        biz.setInitData(paramModel, con, tempDir);
        paramModel.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 指定された添付ファイルをテンポラリファイルに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return 入力チェックの結果
     * @throws SQLException SQL実行例外
     * @throws IOException ファイルアクセス時例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    private Cmn110ValidateResultModel __doUpload(ActionMapping map,
        Cmn110Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException, IOException, IOToolsException {
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        Cmn110ValidateResultModel resultMdl = null;
        try {

            //テンポラリディレクトリパス
            Cmn110ParamModel paramModel = new Cmn110ParamModel();
            paramModel.setParam(form);
            if (!Cmn110Biz.isTempDirOK(reqMdl, paramModel)) {
                resultMdl.addErrors(req, "error.tempfile.savedir",
                        "cmn.cmn110.error.input.notfound.directory", 1, null);
                addErrors(req, resultMdl.getErrors());
                return resultMdl;
            }
            String tempDir = Cmn110Biz.getTempDir(getRequestModel(req), paramModel);

            //入力チェック
            con.setAutoCommit(true);
            resultMdl = form.validate110(con, req, tempDir);
            con.setAutoCommit(false);
            if (!resultMdl.getErrors().isEmpty()) {
                addErrors(req, resultMdl.getErrors());

                if (resultMdl.getErrorType() == Cmn110ValidateResultModel.ERRORTYPE_STOP) {
                    return resultMdl;
                }
            }

            List<FormFile> formFileList = form.getCmn110fileForm();

            List<String> tempNameList = new ArrayList<String>();
            List<String> saveTempNameList = new ArrayList<String>();

            int fileNo = 0;
            for (FormFile formFile : formFileList) {
                fileNo++;

                //入力エラーが存在するファイルは除外する
                if (resultMdl.getErrFileNoList().contains(fileNo)) {
                    continue;
                }

                String cmn110Mode = NullDefault.getString(form.getCmn110Mode(), "");

                if (cmn110Mode.equals(String.valueOf(GSConstCommon.CMN110MODE_GAZOU))
                || cmn110Mode.equals(String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU))
                || cmn110Mode.equals(String.valueOf(GSConstCommon.CMN110MODE_TANITU_USR031))
                || cmn110Mode.equals(String.valueOf(GSConstCommon.CMN110MODE_TANITU_FIL030))
                || cmn110Mode.equals(String.valueOf(GSConstCommon.CMN110MODE_FILEKANRI_TANITU))
                ) {
                    //単一ファイルアップロードモード

                    //添付ファイル名
                    String fileName = (new File(formFile.getFileName())).getName();
                    long fileSize = formFile.getFileSize();

                    //テンポラリディレクトリのファイル削除を行う
                    Cmn110Biz.clearTemp(reqMdl, paramModel);

                    //現在日付の文字列(YYYYMMDD)を取得
                    String dateStr = (new UDate()).getDateString();

                    //添付ファイル(本体)のパスを取得
                    File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, 0);

                    //添付ファイルアップロード
                    TempFileUtil.upload(formFile, tempDir, saveFilePath.getName());

                    //オブジェクトファイルを設定
                    File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, 0);
                    Cmn110FileModel fileMdl = new Cmn110FileModel();
                    fileMdl.setFileName(fileName);
                    fileMdl.setSaveFileName(saveFilePath.getName());
                    fileMdl.setUpdateKbn(0);

                    ObjectFile objFile =
                            new ObjectFile(objFilePath.getParent(), objFilePath.getName());
                    objFile.save(fileMdl);

                    String saveFileName = saveFilePath.getName();

                    log__.debug(">>>サイズ :" + fileSize);
                    tempNameList.add(CommonBiz.addAtattiSizeForName(fileName, fileSize));
                    saveTempNameList.add(saveFileName.substring(0, saveFileName.length() - 4));
                    form.setCmn110Decision(1);


                } else if (cmn110Mode.equals(
                        String.valueOf(GSConstCommon.CMN110MODE_EDITOR))) {

                    if (!Cmn110Biz.isExtensionForPhotoOk(formFile.getFileName())) {
                        //JPG,JPEG,GIF,PNG以外のファイルならばエラー
                        String[] strPhoto = {gsMsg.getMessage("cmn.attach.file")};
                        resultMdl.addErrors(req, "error.select2.required.extent2",
                                            "cmn.cmn110.error.select2.required.extent2",
                                            fileNo, null, strPhoto);
                        addErrors(req, resultMdl.getErrors());
                        return resultMdl;
                    }

                    //エディターへのアップロードモード
                    uploadForEditor(form, tempDir, tempNameList,
                            saveTempNameList, formFile);

                } else {
                    //複数ファイルアップロードモード

                    //施設予約:施設画像設定に使用する
                    if (cmn110Mode.equals(
                            String.valueOf(GSConstCommon.CMN110MODE_RETO_RSV090))) {
                        if (!Cmn110Biz.isExtensionOk(formFile.getFileName())) {
                            //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
                            resultMdl.addErrors(req, "error.select2.required.extent",
                                                "cmn.cmn110.error.select2.required.extent",
                                                fileNo, formFile.getFileName());
                            addErrors(req, resultMdl.getErrors());
                            continue;
                        }
                    }

                    //現在日付の文字列(YYYYMMDD)を取得
                    String dateStr = (new UDate()).getDateString();

                    //ファイルの連番を取得する
                    int fileNum = 1;
                    //TEMPディレクトリ内のファイル数から
                    //連番を取得する
                    fileNum = Cmn110Biz.getFileNumber(tempDir, dateStr);
                    fileNum++;

                    //添付ファイル名
                    String fileName = (new File(formFile.getFileName())).getName();
                    long fileSize = formFile.getFileSize();
                    //添付ファイル(本体)のパスを取得
                    File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);
                    //添付ファイルアップロード
                    TempFileUtil.upload(formFile, tempDir, saveFilePath.getName());

                    //オブジェクトファイルを設定
                    File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
                    Cmn110FileModel fileMdl = new Cmn110FileModel();
                    fileMdl.setFileName(fileName);
                    fileMdl.setSaveFileName(saveFilePath.getName());
                    fileMdl.setUpdateKbn(0);

                    ObjectFile objFile
                        = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
                    objFile.save(fileMdl);

                    String saveFileName = saveFilePath.getName();

                    log__.debug(">>>サイズ :" + fileSize);
                    tempNameList.add(CommonBiz.addAtattiSizeForName(fileName, fileSize));
                    saveTempNameList.add(saveFileName.substring(0, saveFileName.length() - 4));
                    form.setCmn110Decision(1);
                }
            }
            form.setCmn110tempName(
                    (String[]) tempNameList.toArray(new String[tempNameList.size()]));
            form.setCmn110tempSaveName(
                    (String[]) saveTempNameList.toArray(new String[saveTempNameList.size()]));
        } catch (IOException e) {
            log__.error("IOException", e);
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            throw e;
        } catch (Exception e) {
            log__.error("Exception", e);
            throw new IOException();
        }

        return resultMdl;
    }

    /**
     * <br>[機  能] 複数ファイルアップロード
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param tempDir テンポラリディレクトリパス
     * @param tempNameList ファイルサイズ付ファイル名リスト
     * @param saveTempNameList 一時保存ファイル名リスト
     * @param formFile 添付ファイル
     * @throws Exception 実行時例外
     */
    private void uploadForEditor(
            Cmn110Form form, String tempDir, List<String> tempNameList,
            List<String> saveTempNameList, FormFile formFile)
                    throws Exception {

        //ファイルごとにディレクトリを分ける
        StringBuilder tempDirBuilder = new StringBuilder(tempDir);
        //連番フォルダの存在チェック
        int dirIdx = 1;
        while (new File(tempDirBuilder.toString() + String.valueOf(dirIdx)).exists()) {
            ++dirIdx;
        }
        //連番付与
        tempDirBuilder.append(String.valueOf(dirIdx));
        tempDirBuilder.append("/");
        tempDir = tempDirBuilder.toString();


        //現在日付の文字列(YYYYMMDD)を取得
        String dateStr = (new UDate()).getDateString();

        //ファイルの連番を取得する
        int fileNum = 1;
        //ファイル数 = 無制限の場合はTEMPディレクトリ内のファイル数から
        //連番を取得する
        fileNum = Cmn110Biz.getFileNumber(tempDir, dateStr);
        fileNum++;


        //添付ファイル名
        String fileName = (new File(formFile.getFileName())).getName();
        long fileSize = formFile.getFileSize();
        //添付ファイル(本体)のパスを取得
        File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイルアップロード
        TempFileUtil.upload(formFile, tempDir, saveFilePath.getName());

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(fileName);
        fileMdl.setSaveFileName(saveFilePath.getName());
        fileMdl.setUpdateKbn(0);

        ObjectFile objFile
            = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);

        log__.debug(">>>サイズ :" + fileSize);
        tempNameList.add(CommonBiz.addAtattiSizeForName(fileName, fileSize));
        saveTempNameList.add(String.valueOf(dirIdx));
        form.setCmn110Decision(1);
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
     * @param downloadType ダウンロード種別(attachment or inline)
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(ActionMapping map,
            Cmn110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int downloadType) throws Exception {

        String fileId = form.getCmn110DlFileId();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        Cmn110ParamModel paramMdl = new Cmn110ParamModel();
        paramMdl.setParam(form);

        String tempDir = Cmn110Biz.getTempDir(getRequestModel(req), paramMdl);
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);
        if (downloadType == DOWNLOAD_INLINE__) {
            TempFileUtil.downloadInline(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);
        } else {
            TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);
        }

        return null;
    }

    /**
     * <br>[機  能] 添付ファイル削除の処理
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
    private ActionForward __doDeleteFile(ActionMapping map,
            Cmn110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        String fileId = form.getCmn110DlFileId();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            __writeErrMessage(req, res,
                            gsMsg.getMessage("failed.deletefile"),
                            "ファイルの削除に失敗"
                    );
        }

        Cmn110ParamModel paramMdl = new Cmn110ParamModel();
        paramMdl.setParam(form);

        //指定されたファイルを削除する
        boolean result = false;
        try {
            String tempDir = Cmn110Biz.getTempDir(getRequestModel(req), paramMdl);
            ObjectFile objFile
                = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
            objFile.remove();
            String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
            filePath = IOTools.replaceFileSep(filePath);
            IOTools.deleteFile(filePath);
            result = true;
        } catch (Exception e) {
            log__.error("ファイル削除時に例外発生", e);
        }
        if (result) {
            StringBuilder strBuild = new StringBuilder("{");
            strBuild.append("\"errors\" : \"0\"");
            strBuild.append("}");
            PrintWriter writer = null;
            try {
                res.setContentType("text/json; charset=UTF-8");
                writer = res.getWriter();
                writer.write(strBuild.toString());
                writer.flush();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } else {
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            __writeErrMessage(req, res,
                            gsMsg.getMessage("failed.deletefile"),
                            "ファイルの削除に失敗"
                    );
        }

        return null;
    }

    /**
     * <p>ファイル容量エラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward フォワード
     */
    public ActionForward getFileSizeErrorPage(ActionMapping map,
                                                                HttpServletRequest req,
                                                                HttpServletResponse res) {

        String uploadType = NullDefault.getString(req.getParameter("cmn110uploadType"), "");
        if (!uploadType.equals(Integer.toString(Cmn110Form.UPLOADTYPE_DROP))) {
            return super.getFileSizeErrorPage(map, req, res);
        }

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        __writeErrMessage(req, res,
                        gsMsg.getMessage("cmn.upload.totalcapacity.over",
                                new String[]{GSConstCommon.TEXT_FILE_MAX_SIZE}),
                        "ファイル容量エラーの処理に失敗"
                        );

        return null;
    }

    /**
     * <p>エラーメッセージの出力を行う
     * @param req リクエスト
     * @param res レスポンス
     * @param errMessage エラーメッセージ
     * @param errLogMessage 例外発生時のログ出力メッセージ
     */
    private void __writeErrMessage(HttpServletRequest req,
                                    HttpServletResponse res,
                                    String errMessage,
                                    String errLogMessage) {

        StringBuilder strBuild = new StringBuilder("{");
        strBuild.append("\"errors\" : \"1\",");
        strBuild.append("\"errorMsg\" : [");
        strBuild.append("\"" + errMessage + "\"");
        strBuild.append("]");
        strBuild.append("}");

        PrintWriter writer = null;
        try {
            res.setContentType("text/json; charset=UTF-8");
            writer = res.getWriter();
            writer.write(strBuild.toString());
            writer.flush();
        } catch (IOException e) {
            log__.error(errLogMessage, e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}