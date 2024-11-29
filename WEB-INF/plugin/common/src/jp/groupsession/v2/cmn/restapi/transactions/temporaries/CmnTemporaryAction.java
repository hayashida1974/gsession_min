package jp.groupsession.v2.cmn.restapi.transactions.temporaries;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Delete;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;

/**
 * <br>[機  能] トランザクショントークンに紐づく添付ファイルを扱う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnTemporaryAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param ctx
     * @param tempPath テンポラリディレクトリモデル
     */
    @Delete
    public void doDelete(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath
            ) {
        CmnTemporaryForm form = (CmnTemporaryForm) req.getAttribute("restApiCmnTempForm");
        form.setFileIndex(
                Optional.ofNullable(ctx.getMap().getProperty("fileIndex"))
                    .map(idx -> NullDefault.getInt(idx, -1))
                    .orElse(null)
                );

        __deleteTempfile(form, ctx, tempPath);

        __writeResponse(res, ctx, tempPath);

    }
    /**
     *
     * <br>[機  能] 添付ファイルの削除
     * <br>[解  説]
     * <br>[備  考]
     * @param form
     * @param ctx
     * @param tempPath テンポラリディレクトリモデル
     */
    private void __deleteTempfile(CmnTemporaryForm form,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath
            ) {
        try {
            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(tempPath.getTempPath());
            List<LabelValueBean> delList = new ArrayList<>();
            if (form.getFileIndex() == null || form.getFileIndex() == -1) {
                delList.addAll(fileList);
            } else if (fileList.size() > form.getFileIndex()) {
                delList.add(fileList.get(form.getFileIndex()));
            }
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteFile(
                    delList.stream()
                        .map(l -> l.getValue())
                        .collect(Collectors.toList())
                        .toArray(new String[delList.size()]),
                    ctx.getRequestModel(),
                    "api", "temporary");
        } catch (IOToolsException e) {
            throw new RuntimeException("ファイル一覧削除に失敗", e);
        }

    }
    /**
     *
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param ctx コンテキスト
     * @param tempPath テンポラリディレクトリモデル
     */
    @Post
    public void doPost(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath
            ) {
        CmnTemporaryForm form = (CmnTemporaryForm) req.getAttribute("restApiCmnTempForm");
        __saveTempfile(form, ctx, tempPath);

        __writeResponse(res, ctx, tempPath);

    }
    /**
     *
     * <br>[機  能] レスポンス書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param ctx コンテキスト
     * @param tempPath テンポラリディレクトリモデル
     */
    private void __writeResponse(
            HttpServletResponse res,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath) {
        CommonBiz cmnBiz = new CommonBiz();

        RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);

        try {
            List<TempFileModel> fileList = cmnBiz.getTempFiles(tempPath.getTempPath());
            for (int i = 0; i < fileList.size(); i++) {
                builder.addResult(new ResultElement("result")
                            .addContent(
                                    new ResultElement("indexNum")
                                    .addContent(Integer.toString(i)))
                            .addContent(
                                    new ResultElement("fileName")
                                    .addContent(fileList.get(i).getFileName()))
                            );
            }
        } catch (IOToolsException e) {
            throw new RuntimeException("ファイル一覧取得に失敗", e);
        }
        builder.build().execute();
    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param form
     * @param ctx
     * @param tempPath テンポラリディレクトリモデル
     * @return テンポラリディレクトリ
     */
    private String  __saveTempfile(
            CmnTemporaryForm form,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath) {
        String tempDir;
        try {

            tempDir = tempPath.getTempPath();
            List<String> tempNameList = new ArrayList<String>();
            List<String> saveTempNameList = new ArrayList<String>();

            for (FormFile formFile : form.getTmpFile()) {


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

                tempNameList.add(CommonBiz.addAtattiSizeForName(fileName, fileSize));
                saveTempNameList.add(saveFileName.substring(0, saveFileName.length() - 4));
            }
        } catch (Exception e) {
            throw new RuntimeException("ファイル保管に失敗", e);
        }
        return tempDir;
    }


}
