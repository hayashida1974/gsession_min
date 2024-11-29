package jp.groupsession.v2.man.man081;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchBackupListener;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.man080.Man080Biz;
import jp.groupsession.v2.man.man080.Man080FileModel;

/**
 * <br>[機  能] メイン 管理者設定 手動バックアップ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man081Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man081Biz.class);

    /**
     * <br>[機  能] 初期表示の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイル情報一覧の取得に失敗
     */
    public void setInitData(
                Connection con,
                Man081ParamModel paramMdl,
                String appRootPath) throws IOException, SQLException {

        log__.debug("setInitData start");

        //ファイル一覧を設定
        String backupDir = CommonBiz.getManualBackupDirPath(appRootPath);
        log__.info("バックアップファイルパス==>" + backupDir);
        Enumeration<File> fileList = IOTools.getFiles(backupDir);
        List<Man080FileModel> fileDataList = new ArrayList<Man080FileModel>();

        if (fileList != null) {
            fileDataList = getFileDataList(fileList);
        }

        paramMdl.setFileDataList(fileDataList);

        log__.debug("setInitData end");
    }

    /**
     * <br>[機  能] バックアップファイルの削除を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @return 削除ファイル名
     * @throws IOToolsException ファイル削除に失敗
     */
    public String deleteBackupFile(
                Man081ParamModel paramMdl,
                String appRootPath) throws IOToolsException {

        String backupFileName = null;
        try {

            //バックアップディレクトリの取得
            String backupDir = CommonBiz.getManualBackupDirPath(appRootPath);
            log__.debug("バックアップディレクトリ = " + backupDir);
            //ファイルの存在チェックを行う
            String backupFileHashName = paramMdl.getMan081backupFile();
            //ファイル一覧を設定
            Enumeration<File> fileList = IOTools.getFiles(backupDir);
            Man081Biz biz = new Man081Biz();
            List<Man080FileModel> dspFileList = biz.getFileDataList(fileList);
            boolean dlFlg = false;
            //パラメータのハッシュ値とバックアップディレクトリ内のファイル名のハッシュ値が一致したらダウンロード可能
            for (Man080FileModel fileMdl : dspFileList) {
                String hashFileName = fileMdl.getHashFileName();
                if (backupFileHashName.equals(hashFileName)) {
                    backupFileName = fileMdl.getFileName();
                    dlFlg = true;
                    break;
                }
            }

            //パラメータとバックアップディレクトリ内のファイル名のハッシュ値が一致していれば削除
            if (dlFlg) {
                //バックアップファイルの削除
                String backupFile = backupDir + backupFileName;
                if (IOTools.isFileCheck(backupDir, backupFileName, false)) {
                    IOTools.deleteFile(backupFile);
                }
            }
        } catch (IOToolsException e) {
            throw e;
        }

        return backupFileName;
    }

    /**
     * <br>[機  能] バックアップファイルを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param appRootPath アプリケーションルートパス
     * @param pluginConfig プラグイン情報
     * @param gscontext GroupSession共通情報
     * @param domain ドメイン
     * @throws Exception 実行例外
     */
    public void createBackupFile(Connection con, String appRootPath,
                         PluginConfig pluginConfig, GSContext gscontext, String domain)
    throws Exception {

        //データベース停止フラグ
        boolean dbStop = false;

        String backupTempDir = "";

        IBatchBackupListener[] backupBatchListeners = null;
        try {
            DayJob.setBackup(true);

            //バックアップ前処理を実行
            CommonBiz cmnBiz = new CommonBiz();
            backupBatchListeners = cmnBiz.getBackupBatchListeners(pluginConfig, con);
            if (backupBatchListeners != null && backupBatchListeners.length > 0) {
                for (IBatchBackupListener listener : backupBatchListeners) {
                    listener.doBeforeBackup(con, gscontext, domain);
                }
            }

            //バックアップファイルを保存するディレクトリパスを取得する
            String backupDir = CommonBiz.getManualBackupDirPath(appRootPath);

            //バックアップ先ディレクトリが存在しない場合は作成する
            IOTools.isDirCheck(backupDir, true);

            //バックアップテンポラリディレクトリ
            backupTempDir = backupDir + "/temp/";
            backupTempDir = IOTools.replaceFileSep(backupTempDir);
            IOTools.isDirCheck(backupTempDir, true);

            //データベース保存先ディレクトリを取得
            String dbDir = GsDataSourceFactory.getDbDir(appRootPath);
            log__.debug("データベース保存先ディレクトリ = " + dbDir);

            //バイナリーデータ保存先ディレクトリを取得
            CommonBiz commonBiz = new CommonBiz();
            String binDataDir = commonBiz.getFileRootPath(appRootPath);
            log__.debug("バイナリーデータ保存先ディレクトリ = " + binDataDir);

            //ファイル管理用バイナリーデータ保存先ディレクトリを取得
            String fileBinDataDir = commonBiz.getFileRootPathForFileKanri(appRootPath);
            log__.debug("ファイル管理用バイナリーデータ保存先ディレクトリ = " + fileBinDataDir);

            //WEBメール用バイナリーデータ保存先ディレクトリを取得
            String webmailBinDataDir = commonBiz.getFileRootPathForWebmail(appRootPath);
            log__.debug("WEBメール用バイナリーデータ保存先ディレクトリ = " + webmailBinDataDir);

            UDate now = new UDate();
            //バックアップファイルパス
            String backupFileName = "";
            backupFileName += GSConstMain.BACKUPFILE_HEADSTR;
            backupFileName += now.getDateString();
            String svBackupFileName = backupFileName;
            backupFileName += ".zip";

            //同じバックファイル名が存在する場合の対応
            if (IOTools.isFileCheck(backupDir, backupFileName, false)) {
                int i;
                String searchfileName = null;
                for (i = 1; i > -1; i++) {
                    searchfileName = svBackupFileName;
                    searchfileName += String.valueOf(i);
                    searchfileName += ".zip";
                    if (!IOTools.isFileCheck(backupDir, searchfileName, false)) {
                        break;
                    }
                }
                backupFileName = searchfileName;
            }

            StringBuilder saveDir = new StringBuilder("");
            saveDir.append(backupTempDir);
            saveDir.append(backupFileName);
            String saveDirPath = IOTools.replaceFileSep(saveDir.toString());
            log__.debug("バックアップファイル = " + saveDirPath);

            log__.debug("バックアップのためデータベースを停止");
            IDbUtil dbUtil = DBUtilFactory.getInstance();
            dbUtil.shutdownDbServer(appRootPath, con);
            dbStop = true;

            ArrayList<String> targetDirs = new ArrayList<String>();
            targetDirs.add(dbDir);
            if ((new File(binDataDir)).exists()) {
                targetDirs.add(binDataDir);
            }
            if ((new File(fileBinDataDir)).exists()) {
                targetDirs.add(fileBinDataDir);
            }
            if ((new File(webmailBinDataDir)).exists()) {
                targetDirs.add(webmailBinDataDir);
            }
            if (log__.isDebugEnabled()) {
                for (String target : targetDirs) {
                    log__.debug("バックアップ対象ディレクトリ==>" + target);
                }
            }
            //Zipの保存を行う
            ZipUtil.zipExclude(
                    targetDirs.toArray(new String[0]),
                    saveDirPath,
                    Arrays.asList(new String[] {GSConstMain.LUCENE_LOCKFILENAME}));

            //バックアップファイルの削除を行う
            Enumeration<File> files = IOTools.getFiles(backupDir);
            if (files != null) {
                List<Long> fileKey = new ArrayList<Long>();
                Map<Long, String> fileMap = new HashMap<Long, String>();
                while (files.hasMoreElements()) {
                    File file = (File) files.nextElement();

                    if (file.isDirectory()) {
                        continue;
                    }

                    String fileName = file.getName();
                    if (fileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR)
                    && fileName.endsWith(".zip")) {
                        Long fileDate = new Long(file.lastModified());
                        fileKey.add(fileDate);
                        fileMap.put(fileDate, file.getPath());
                    }
                }
            }

            //テンポラリディレクトリからバックアップディレクトリへファイルの移動を行う
            (new File(saveDirPath)).renameTo(
                    new File(IOTools.replaceFileSep(backupDir + backupFileName)));

        } catch (SQLException e) {
            log__.error("バックアップ設定の取得に失敗", e);
            throw e;

        } catch (IOToolsException e) {
            log__.error("手動バックアップに失敗", e);
            throw e;

        } catch (Exception e) {
            log__.error("DBの停止に失敗", e);
            throw e;

        } finally {
            DayJob.setBackup(false);

            if (dbStop) {
                log__.debug("データベースを開始");
                IDbUtil dbUtil = DBUtilFactory.getInstance();
                dbUtil.init(appRootPath);
                dbUtil.startDbServer(appRootPath);
            }
            JDBCUtil.closeConnection(con);
            con = __resetDBConnection(domain);
            //バックアップ後処理
            if (backupBatchListeners != null && backupBatchListeners.length > 0) {
                for (IBatchBackupListener listener : backupBatchListeners) {
                    listener.doAfterBackup(con, gscontext, domain);
                }
            }
            JDBCUtil.closeConnection(con);
            //テンポラリディレクトリからバックアップファイルの削除を行う
            IOTools.deleteDir(backupTempDir);
        }
    }
    /**
    *
    * <br>[機  能] データソース、コンテキストの再設定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param dsKey ドメイン
    * @return 再生成後のコネクション
    * @throws Exception 例外発生
    */
   private Connection __resetDBConnection(String dsKey) throws Exception {
       Connection con = null;
       try {
           Thread.sleep(3000);
           //データソース、コンテキストの再設定を行う
           //DS
           GroupSession.getResourceManager().resetDataSource(dsKey);
           log__.debug("Backup:DataSource 再作成");
           //採番
           GroupSession.getResourceManager()
               .resetCountController(dsKey);
           log__.debug("Backup:採番コントローラ 再作成(SAIBAN)");

           con = GroupSession.getConnection(dsKey);
       } catch (Exception e) {
           log__.error("Backup:コネクション再生成失敗", e);
           throw e;
       }
       return con;
   }
    /**
     * <br>[機  能] ファイルリストより表示用のファイルリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileList ファイルリスト
     * @return 表示用ファイルリスト
     */
    public List<Man080FileModel> getFileDataList(Enumeration<File> fileList) {
        List<Man080FileModel> fileDataList = new ArrayList<Man080FileModel>();

        while (fileList.hasMoreElements()) {
            File file = (File) fileList.nextElement();
            if (file.isDirectory()) {
                continue;
            }

            String fileName = file.getName();
            log__.debug("バックアップファイル名==>" + fileName);
            if (Man080Biz.isBackupFileName(fileName)) {
                Man080FileModel fileMdl = new Man080FileModel();
                fileMdl.setFileName(file.getName());
                fileMdl.setHashFileName(MainCommonBiz.getSelfBackUpHashName(file.getName()));
                fileMdl.setFileSize(CommonBiz.formatByteSizeString(file.length()));

                UDate lastUpdate = UDate.getInstance(file.lastModified());
                fileMdl.setStrMakeDate(UDateUtil.getSlashYYMD(lastUpdate)
                        + " "
                        + UDateUtil.getSeparateHMS(lastUpdate));
                fileMdl.setMakeDate(lastUpdate);
                fileDataList.add(fileMdl);
            }
        }

        Collections.sort(fileDataList);

        return fileDataList;
    }
}
