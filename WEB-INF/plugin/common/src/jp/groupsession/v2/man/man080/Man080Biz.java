package jp.groupsession.v2.man.man080;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 自動バックアップ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man080Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man080Biz.class);

    /**
     * <br>[機  能] 初期表示の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイル情報一覧の取得に失敗
     */
    public void setInitData(
                Connection con,
                RequestModel reqMdl,
                Man080ParamModel paramMdl,
                String appRootPath) throws IOException, SQLException {

        log__.debug("setInitData start");

        //曜日一覧を設定
        List<LabelValueBean> dowList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.sunday3"), String.valueOf(UDate.SUNDAY)));
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.monday3"), String.valueOf(UDate.MONDAY)));
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.tuesday3"), String.valueOf(UDate.TUESDAY)));
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.wednesday3"), String.valueOf(UDate.WEDNESDAY)));
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.thursday3"), String.valueOf(UDate.THURSDAY)));
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("main.src.man080.7"), String.valueOf(UDate.FRIDAY)));
        dowList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.saturday3"), String.valueOf(UDate.SATURDAY)));
        paramMdl.setDowList(dowList);

        //週一覧を設定
        List<LabelValueBean> weekmonthList = new ArrayList<LabelValueBean>();
        weekmonthList.add(new LabelValueBean(gsMsg.getMessage("cmn.no.1"), "1"));
        weekmonthList.add(new LabelValueBean(gsMsg.getMessage("cmn.no.2"), "2"));
        weekmonthList.add(new LabelValueBean(gsMsg.getMessage("cmn.no.3"), "3"));
        weekmonthList.add(new LabelValueBean(gsMsg.getMessage("cmn.no.4"), "4"));
        weekmonthList.add(new LabelValueBean(gsMsg.getMessage("cmn.no.5"), "5"));
        paramMdl.setWeekmonthList(weekmonthList);

        //世代一覧を設定
        List<LabelValueBean> generationList = new ArrayList<LabelValueBean>();
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.generation.1"), "1"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.2generations"), "2"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.3generations"), "3"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.4generations"), "4"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.5generations"), "5"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.6generations"), "6"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.7generations"), "7"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.8generations"), "8"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.9generations"), "9"));
        generationList.add(new LabelValueBean(gsMsg.getMessage("cmn.10generations"), "10"));
        paramMdl.setGenerationList(generationList);

        //バッチ起動時間を設定
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        if (batMdl != null) {
            paramMdl.setMan080batchHour(Integer.toString(batMdl.getBatFrDate()));
        } else {
            paramMdl.setMan080batchHour("0");
        }

        //バックアップ設定情報を設定
        if (paramMdl.getMan080Interval() == -1) {
            CmnBackupConfDao backConfDao = new CmnBackupConfDao(con);
            CmnBackupConfModel backConfMdl = backConfDao.select();
            if (backConfMdl != null) {
                paramMdl.setMan080generation(backConfMdl.getBucGeneration());
                paramMdl.setMan080zipOutputKbn(backConfMdl.getBucZipout());
                int interval = backConfMdl.getBucInterval();
                paramMdl.setMan080Interval(interval);

                if (interval == GSConstMain.BUCCONF_INTERVAL_WEEKLY) {
                    paramMdl.setMan080dow(backConfMdl.getBucDow());
                } else if (interval == GSConstMain.BUCCONF_INTERVAL_MONTHLY) {
                    paramMdl.setMan080monthdow(backConfMdl.getBucDow());
                    paramMdl.setMan080weekmonth(backConfMdl.getBucWeekMonth());
                }
            } else {
                paramMdl.setMan080Interval(GSConstMain.BUCCONF_INTERVAL_NOSET);
            }
        }

        //ファイル一覧を設定
        String backupDir = CommonBiz.getBackupDirPath(appRootPath);
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
     * <br>[機  能] 指定されたファイルがバックアップファイルの名称かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名
     * @return 判定結果
     */
    public static boolean isBackupFileName(String fileName) {
        return fileName.endsWith(".zip")
                && (fileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR)
                    || fileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR_V3)
                    || fileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR_V2));
    }

    /**
     * <br>[機  能] バックアップファイルを保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param srcDirPathList バックアップファイルパス
     * @param saveDir 保存先パス
     * @param appRootPath アプリケーションルートパス
     * @throws IOException ファイル情報一覧の取得に失敗
     * @throws IOToolsException ファイル削除に失敗
     */
    public void saveBackupFile(
                String[] srcDirPathList,
                String saveDir,
                String appRootPath) throws IOException, IOToolsException {

        String saveDirectory = "";
        String dir = saveDir + File.separator;

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


        //DBディレクトリ
        if ((new File(dbDir)).exists()) {
            IOTools.isDirCheck(dir + "db", true);
            saveDirectory = dir + "db";
            IOTools.copyDir(new File(dbDir), new File(saveDirectory));

        }
        //ファイルディレクトリ
        if ((new File(binDataDir)).exists()) {
            IOTools.isDirCheck(dir + "file", true);
            saveDirectory = dir + "file";
            IOTools.copyDir(new File(binDataDir), new File(saveDirectory));

        }
        //ファイル管理ディレクトリ
        if ((new File(fileBinDataDir)).exists()) {
            IOTools.isDirCheck(dir + "filekanri", true);
            saveDirectory = dir + "filekanri";
            IOTools.copyDir(new File(fileBinDataDir), new File(saveDirectory));
        }
        //WEBメールディレクトリ
        if ((new File(webmailBinDataDir)).exists()) {
            IOTools.isDirCheck(dir + "webmail", true);
            saveDirectory = dir + "webmail";
            IOTools.copyDir(new File(webmailBinDataDir), new File(saveDirectory));
        }
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

        Man080FileModel fileMdl = null;
        while (fileList.hasMoreElements()) {
            File file = (File) fileList.nextElement();
            if (file.isDirectory()) {
                String upzipFileName = file.getName();
                log__.debug("バックアップファイル名[UNZIP]==>" + upzipFileName);

                if (upzipFileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR)
                        || upzipFileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR_V3)
                        || upzipFileName.startsWith(GSConstMain.BACKUPFILE_HEADSTR_V2)) {
                    //バックアップ(UNZIP)
                    fileMdl = new Man080FileModel();
                    fileMdl.setFileName(file.getName());
                    fileMdl.setHashFileName(MainCommonBiz.getAutoBackUpHashName(file.getName()));

                    UDate lastUpdate = UDate.getInstance(file.lastModified());
                    fileMdl.setStrMakeDate(UDateUtil.getSlashYYMD(lastUpdate)
                            + " "
                            + UDateUtil.getSeparateHMS(lastUpdate));
                    fileMdl.setMakeDate(lastUpdate);
                    fileMdl.setZipOutput(String.valueOf(GSConstMain.ZIP_BACKUP_FLG_OFF));
                    fileDataList.add(fileMdl);
                }
                continue;
            }
            String zipFileName = file.getName();
            log__.debug("バックアップファイル名[ZIP]==>" + zipFileName);
            if (isBackupFileName(zipFileName)) {
                //ZIP形式バックアップ
                fileMdl = new Man080FileModel();
                fileMdl.setFileName(file.getName());
                fileMdl.setHashFileName(MainCommonBiz.getAutoBackUpHashName(file.getName()));
                fileMdl.setFileSize(CommonBiz.formatByteSizeString(file.length()));

                UDate lastUpdate = UDate.getInstance(file.lastModified());
                fileMdl.setStrMakeDate(UDateUtil.getSlashYYMD(lastUpdate)
                        + " "
                        + UDateUtil.getSeparateHMS(lastUpdate));
                fileMdl.setMakeDate(lastUpdate);
                fileMdl.setZipOutput(String.valueOf(GSConstMain.ZIP_BACKUP_FLG_ON));
                fileDataList.add(fileMdl);
            }
        }
        Collections.sort(fileDataList);
        return fileDataList;
    }
}
