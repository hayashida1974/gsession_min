package jp.groupsession.v2.man;

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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.IBatchBackupListener;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.man.man080.Man080Biz;

/**
 * <br>[機  能] バックアップ処理時に実行されるリスナーを実装
 * <br>[解  説] 自動バックアップについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManBatchBackupListenerImpl implements IBatchBackupListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ManBatchBackupListenerImpl.class);
    /**バックアップステータス管理*/
    private int bkupStatus__ = 0;
    /**バックアップステータス 開始前*/
    private static final int STATUS_FLG_NONE = 0;
    /**バックアップステータス 実行中*/
    private static final int STATUS_FLG_START = 1;
    /**バックアップステータス 完了*/
    private static final int STATUS_FLG_FINISH = 2;

    /**
     * <p>バックアップ処理の前に実行されるJob
     * @param con DBコネクション
     * @param param バックアップ前処理で使用する情報
     * @param domain ドメイン
     * @throws Exception バックアップ前処理実行時例外
     */
    public void doBeforeBackup(Connection con, Object param, String domain) throws Exception {
        bkupStatus__ = STATUS_FLG_NONE;
    }

    /**
     * <p>バックアップ処理
     * @param con DBコネクション
     * @param param バックアップ処理時に使用する情報
     * @throws Exception バックアップ処理実行時例外
     */
    public void doBackup(Connection con, Object param) throws Exception {
        String appRootPath = "";
        GSContext gscontext = (GSContext) param;
        Object pathObj = gscontext.get(GSContext.APP_ROOT_PATH);

        //データベース停止フラグ
        boolean dbStop = false;

        String finallyDelTempDirName = null;
        try {

            //バックアップ設定を取得
            CmnBackupConfDao backConfDao = new CmnBackupConfDao(con);
            CmnBackupConfModel backConfMdl = backConfDao.select();

            if (backConfMdl == null) {
                log__.info("バックアップ設定が未登録の為、自動バックアップを行わない");
                return;
            }
            UDate now = new UDate();

            //Zipファイル保存フラグ
            int zipHznFlg = backConfMdl.getBucZipout();
            log__.debug("自動バックアップ開始");
            bkupStatus__ = STATUS_FLG_START;


            //アプリケーションのルートパスを取得
            if (pathObj != null) {
                appRootPath = (String) pathObj;
            }

            //ファイル一覧を設定
            String backupDir = CommonBiz.getBackupDirPath(appRootPath);

            //バックアップ先ディレクトリが存在しない場合は作成する
            IOTools.isDirCheck(backupDir, true);

            //バックアップテンポラリディレクトリ
            String backupTempDir = backupDir;
            backupTempDir = IOTools.replaceSlashFileSep(backupTempDir);
            if (!backupTempDir.endsWith("/")) {
                backupTempDir += "/";
            }
            backupTempDir += "temp/";
            backupTempDir = IOTools.replaceFileSep(backupTempDir);
            IOTools.isDirCheck(backupTempDir, true);
            finallyDelTempDirName = backupTempDir;
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

            //バックアップファイルパス
            String backupFileName = "";
            backupFileName += GSConstMain.BACKUPFILE_HEADSTR;
            backupFileName += now.getDateString();
            if (zipHznFlg == GSConstMain.ZIP_BACKUP_FLG_ON) {
                backupFileName += ".zip";
            }

            StringBuilder saveDir = new StringBuilder("");
            saveDir.append(backupTempDir);
            saveDir.append(backupFileName);
            String saveDirPath = IOTools.replaceFileSep(saveDir.toString());
            log__.debug("バックアップファイル = " + saveDirPath);

            log__.debug("バックアップのためデータベースを停止");
            IDbUtil dbUtil = DBUtilFactory.getInstance();
            dbUtil.shutdownDbServer(appRootPath, con);
            JDBCUtil.closeConnection(con);

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
                for (String bdir : targetDirs) {
                    log__.debug("バックアップ対象ディレクトリ==>" + bdir);
                }
            }
            if (zipHznFlg == GSConstMain.ZIP_BACKUP_FLG_ON) {

                //Zipの保存を行う
                ZipUtil.zipExclude(
                        targetDirs.toArray(new String[0]),
                        saveDirPath,
                        Arrays.asList(new String[] {GSConstMain.LUCENE_LOCKFILENAME}));
            } else {
                //圧縮せずに出力
                Man080Biz man080Biz = new Man080Biz();
                man080Biz.saveBackupFile(targetDirs.toArray(new String[0]), saveDirPath,
                        appRootPath);
            }

            //バックアップファイルの削除を行う
            Enumeration<File> files = IOTools.getFiles(backupDir);
            if (files != null) {
                List<Long> fileKey = new ArrayList<Long>();
                Map<Long, String> fileMap = new HashMap<Long, String>();
                while (files.hasMoreElements()) {
                    File file = (File) files.nextElement();

                    if (file.isDirectory()) {
                        String unzipfileName = file.getName();
                        if (unzipfileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR)) {
                            Long fileDate = Long.valueOf(file.lastModified());
                            fileKey.add(fileDate);
                            fileMap.put(fileDate, file.getPath() + GSConstMain.ZIP_BACKUP_FLG_OFF);
                        }
                        continue;
                    }

                    String zipfileName = file.getName();
                    if (zipfileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR)
                    && zipfileName.endsWith(".zip")) {
                        Long fileDate = Long.valueOf(file.lastModified());
                        fileKey.add(fileDate);
                        fileMap.put(fileDate, file.getPath() + GSConstMain.ZIP_BACKUP_FLG_ON);
                    }
                }

                //バックアップ先ディレクトリのファイル数が世代数を超えていた場合、
                //古い順にファイルの削除を行う
                if (fileKey.size() >= backConfMdl.getBucGeneration()) {

                    Collections.sort(fileKey);
                    int delCnt = fileKey.size() - backConfMdl.getBucGeneration();
                    delCnt++;
                    String path = "";
                    String dirKbn = "";
                    for (int i = 0; i < delCnt; i++) {
                        //削除パス
                        path = fileMap.get(fileKey.get(i)).substring(
                                           0, fileMap.get(fileKey.get(i)).length() - 1);
                        //ディレクトリ区分
                        dirKbn = fileMap.get(fileKey.get(i)).substring(
                                           fileMap.get(fileKey.get(i)).length() - 1,
                                           fileMap.get(fileKey.get(i)).length());

                        if (new File(path).exists()) {
                            if (dirKbn.equals(String.valueOf(GSConstMain.ZIP_BACKUP_FLG_ON))) {
                                //ファイル
                                IOTools.deleteFile(path);
                            } else {
                                //ディレクトリ
                                IOTools.deleteDir(path);
                            }
                        }
                    }
                }
            }

            //テンポラリディレクトリからバックアップディレクトリへファイルの移動を行う
            //IOTools.copyBinFile(saveDirPath,
            //                    IOTools.replaceFileSep(backupDir + backupFileName));
            if (zipHznFlg == GSConstMain.ZIP_BACKUP_FLG_OFF
                    && new File(backupDir + backupFileName).exists()) {
                //バックアップディレクトリに対象ディレクトリが既に存在する場合
                //ディレクトリ削除
                IOTools.deleteDir(backupDir + backupFileName);
            }

            IOTools.moveBinFile(saveDirPath,
                    IOTools.replaceFileSep(backupDir + backupFileName));
            bkupStatus__ = STATUS_FLG_FINISH;
            log__.debug("自動バックアップ完了");

        } catch (SQLException e) {
            log__.error("バックアップ設定の取得に失敗", e);
            throw e;

        } catch (IOToolsException e) {
            log__.error("自動バックアップに失敗", e);
            throw e;
        } catch (IOException e) {
            log__.error("自動バックアップに失敗", e);
            throw e;
        } catch (Exception e) {
            log__.error("予測不明なエラー", e);
            throw e;
        } finally {
            if (dbStop) {
                log__.debug("データベースを開始");
                IDbUtil dbUtil = DBUtilFactory.getInstance();
                dbUtil.init(appRootPath);
                dbUtil.startDbServer(appRootPath);
            }

            //テンポラリディレクトリの削除を行う
            if (finallyDelTempDirName != null) {
                IOTools.deleteDir(finallyDelTempDirName);
            }
        }
    }

    /**
     * <p>バックアップ処理終了後に実行されるJob
     * @param con DBコネクション
     * @param param バックアップ終了後処理で使用する情報
     * @param domain ドメイン
     * @throws Exception バックアップ終了後処理実行時例外
     */
    public void doAfterBackup(Connection con, Object param, String domain) throws Exception {
    }

    /**
     * <p>bkupStatus を取得します。
     * @return bkupStatus
     */
    public int getBkupStatus() {
        return bkupStatus__;
    }
}