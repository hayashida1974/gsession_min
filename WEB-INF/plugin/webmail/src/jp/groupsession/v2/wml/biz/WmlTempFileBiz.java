package jp.groupsession.v2.wml.biz;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.MailTempFileModel;

/**
 * <br>[機  能] WEBメールプラグインの添付ファイルを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlTempFileBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlTempFileBiz.class);

    /**
     * <br>[機  能] 送信メールの添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリルートディレクトリ
     * @return 添付ファイル情報一覧
     * @throws IOToolsException 
     * @throws Exception 添付ファイル情報取得時に例外発生
     */
    public List<WmlMailFileModel> getTempFileList(String tempDir) throws IOToolsException {

        List<WmlMailFileModel> tempFileList = new ArrayList<WmlMailFileModel>();

        List<String> fileList = null;
        if (tempDir != null && !tempDir.isEmpty()) {
            fileList = IOTools.getFileNames(tempDir);
        }
        if (fileList != null && !fileList.isEmpty()) {
            for (String fileName : fileList) {
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

                WmlMailFileModel fileMdl = new WmlMailFileModel();
                fileMdl.setFileName(fMdl.getFileName());
                fileMdl.setFilePath(IOTools.replaceFileSep(tempDir + fMdl.getSaveFileName()));
                tempFileList.add(fileMdl);
            }
        }

        return tempFileList;
    }

    /**
     * <br>[機  能] 添付ファイルのアップロードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdlFile 添付ファイル
     * @param tempDir テンポラリディレクトリ
     * @return 保存ファイル名
     * @throws Exception 添付ファイルのアップロードに失敗
     */
    public String deproyFormFileData(FormFile paramMdlFile, String tempDir)
            throws Exception {

        String saveFileName = null;
        try {

            //現在日付の文字列(YYYYMMDD)を取得
            String dateStr = (new UDate()).getDateString();

            //ファイルの連番を取得する
            int fileNum = Cmn110Biz.getFileNumber(tempDir, dateStr);
            fileNum++;

            //添付ファイル名
            String fileName = (new File(paramMdlFile.getFileName())).getName();
            long fileSize = paramMdlFile.getFileSize();
            //添付ファイル(本体)のパスを取得
            File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);

            //添付ファイルアップロード
            TempFileUtil.upload(paramMdlFile, tempDir, saveFilePath.getName());

            //オブジェクトファイルを設定
            File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
            Cmn110FileModel fileMdl = new Cmn110FileModel();
            fileMdl.setFileName(fileName);
            fileMdl.setSaveFileName(saveFilePath.getName());
            fileMdl.setAtattiSize(fileSize);

            ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
            objFile.save(fileMdl);

            saveFileName = saveFilePath.getName();

        } catch (Exception e) {
            log__.error("添付ファイルのアップロードに失敗", e);
            throw new IOException();
        }

        return saveFileName;
    }

    /** 
     * 参照メールの添付ファイルをテンポラリディレクトリに展開する
     * @param con
     * @param reqMdl
     * @param templateSid
     * @param binSidArray
     * @param appRootPath
     * @param tempDir
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     * @throws SQLException 
    */
    public void deproyAttachimentTemplateFileData(
        Connection con,
        RequestModel reqMdl,
        int templateSid,
        long[] binSidArray,
        String appRootPath,
        String tempDir) throws
    TempFileException, IOException, IOToolsException, SQLException {

        if (binSidArray == null || binSidArray.length == 0) {
            return;
        }

        WmlMailTemplateFileDao wmtfDao = new WmlMailTemplateFileDao(con);

        Set<Long> binSids1 =
            Stream.of(wmtfDao.getBinSid(templateSid))
            .map(sidStr -> Long.valueOf(sidStr))
            .collect(Collectors.toSet());
    
        Set<Long> binSids2 =
            Arrays.stream(binSidArray)
            .mapToObj(sid -> Long.valueOf(sid))
            .collect(Collectors.toSet());
        
        String[] retainSidArray = binSids2.stream()
                                    .filter(sid -> binSids1.contains(sid))
                                    .map(sid -> sid.toString())
                                    .toArray(String[]::new);
        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        String domain = reqMdl.getDomain();

        //テンポラリディレクトリに既に登録されているファイルが存在する場合、ファイル番号が連番になるようにする
        int num = 1;
        List < String > fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            num += fileList.size();
        }

        for (CmnBinfModel binMdl : cmnBiz.getBinInfo(con, retainSidArray, domain)) {
            if (binMdl != null) {
                //添付ファイルをテンポラリディレクトリにコピーする。
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, num);
                num++;
            }
        }
    
    }
    /** 
     * 参照メールの添付ファイルをテンポラリディレクトリに展開する
     * @param con
     * @param reqMdl
     * @param mailSid
     * @param binSidArray
     * @param appRootPath
     * @param tempDir
     * @throws TempFileException
     * @throws IOException
     * @throws IOToolsException
     * @throws SQLException 
    */
    public void deproyAttachimentRefarenceFileData(
        Connection con,
        RequestModel reqMdl,
        long mailSid,
        long[] binSidArray,
        String appRootPath,
        String tempDir) throws
    TempFileException, IOException, IOToolsException, SQLException {

        if (binSidArray == null || binSidArray.length == 0) {
            return;
        }

        //参照メールと紐づいているかを確認
        WebmailDao dao = new WebmailDao(con);
        List<MailTempFileModel> tempList = dao.getTempFileList(mailSid);
        Set<Long> binSids1 = tempList.stream()
            .map(mdl -> mdl.getBinSid())
            .collect(Collectors.toSet());
   
        Set<Long> binSids2 =
            Arrays.stream(binSidArray)
            .mapToObj(sid -> Long.valueOf(sid))
            .collect(Collectors.toSet());
        
        long[] retainSidArray = binSids2.stream()
                                    .filter(sid -> binSids1.contains(sid))
                                    .mapToLong(sid -> sid.longValue())
                                    .toArray();
        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();

        int num = 1;
        List < String > fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            num += fileList.size();
        }


        for (long wtfSid : retainSidArray) {
            WmlTempfileModel wtfModel = cmnBiz.getBinInfoForWebmail(
                con,
                mailSid,
                wtfSid, reqMdl.getDomain());

            cmnBiz.saveTempFileForWebmail(
                dateStr, wtfModel, appRootPath, tempDir, num);
            num++;

        }
    }    

}
