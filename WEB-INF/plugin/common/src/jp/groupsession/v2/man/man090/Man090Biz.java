package jp.groupsession.v2.man.man090;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.man.biz.MainCommonBiz;

/**
 * <br>[機  能] メイン 管理者設定 アプリケーションログ一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man090Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param prefix アプリケーションのルートパス
     * @throws Exception 実行例外
     */
    public void setInitData(Man090ParamModel paramMdl, String prefix)
    throws Exception {

        //ログディレクトリの取得
        String logDir = getLogFileDir(prefix);
        log__.debug("logディレクトリ = " + logDir);
        File srhDir = new File(logDir);

        String[] fileAry = srhDir.list();
        //処理しやすいようにリストに変換
        ArrayList<DspAppLogModel> addList = null;
        if (fileAry != null) {
            ArrayList<String> fileList = new ArrayList<String>();
            for (int i = 0; i < fileAry.length; i++) {
                fileList.add(fileAry[i]);
            }

            //抽出するログファイル名の取得
            String logFile = GSConst.LOGFILE_NAME + ".log";
            log__.debug("ログファイル名 = " + logFile);
            addList = __getFileData(logDir, fileList, logFile);
            log__.debug("addList = " + addList.size());

            //ソート処理更新日付の降順
            Collections.sort(addList);
            Collections.reverse(addList);

            //エラーログが存在する場合はファイル一覧に追加
            String errorLogName = GSConst.LOGFILE_NAME_ERROR + ".log";
            if (IOTools.isFileCheck(logDir, errorLogName, false)) {
                ArrayList<DspAppLogModel> errorList = new ArrayList<DspAppLogModel>();
                errorList = __getFileData(logDir, fileList, errorLogName);
                Collections.sort(errorList);
                Collections.reverse(errorList);
                addList.addAll(errorList);
            }
        }

        //画面にセット
        paramMdl.setLogList(addList);
    }

    /**
     * <br>[機  能] リスト内のファイル名の一部とファイル名が一致するものを返す
     * <br>[解  説] 一致したファイル名はリストから削除される
     * <br>[備  考]
     * @param logDir ログディレクトリパス
     * @param fileList 検索するファイル名配列
     * @param logFileName 対象のファイル名
     * @return 一致したデータのリスト
     */
    private ArrayList<DspAppLogModel> __getFileData(
        String logDir,
        ArrayList<String> fileList,
        String logFileName) {

        //ファイル名が一致したデータをaddListに追加する
        ArrayList<DspAppLogModel> targetList = new ArrayList<DspAppLogModel>();
        for (int i = 0; i < fileList.size(); i++) {
            String fileName = fileList.get(i);
            log__.debug("fileName  = " + fileName);
            if (fileName.indexOf(logFileName) != -1) {
                //取得したファイル名等のパラメータを追加
                targetList.add(__createLogModel(logDir, fileName));
                //追加したファイルは削除
                fileList.remove(i);
                i--;
            }
        }
        return  targetList;
    }

    /**
     * <br>[機  能] アプリケーションルートからログファイル出力先ディレクトリを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションルート
     * @return String
     */
    public static String getLogFileDir(String appRoot) {
        if (appRoot == null) {
            return "";
        }
        appRoot = IOTools.setEndPathChar(appRoot);
        String logDir = IOTools.replaceSlashFileSep(appRoot + "WEB-INF/log/");
        return logDir;
    }
    
    /**
     * <br>[機  能] ファイルリストより表示用のファイルリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileList ファイルリスト
     * @return 表示用ファイルリスト
     */
    public List<DspAppLogModel> getFileDataList(Enumeration<File> fileList) {
        
        List<DspAppLogModel> ret = new ArrayList<DspAppLogModel>();
        
        while (fileList.hasMoreElements()) {
            File file = (File) fileList.nextElement();
            DspAppLogModel model = new DspAppLogModel();
            model.setHashHttpLogName(MainCommonBiz.getAutoBackUpHashName(file.getName()));
            model.setHttpLogName(file.getName());
            ret.add(model);
        }
        
        return ret;
    }

    /**
     * <br>[機  能] ログ一覧表示用モデルを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dir ディレクトリパス
     * @param fileName ファイル名
     * @return ログ一覧表示用モデル
     */
    private DspAppLogModel __createLogModel(String dir, String fileName) {
        File file = new File(dir + fileName);
        DspAppLogModel appModel = new DspAppLogModel();
        CommonBiz cmnBiz = new CommonBiz();
        appModel.setHttpLogName(fileName);
        UDate edate = new UDate();
        edate.setTime(file.lastModified());
        appModel.setLogLastUpDate(
                UDateUtil.getSlashYYMD(edate)
                + " "
                + UDateUtil.getSeparateHMS(edate)
                );
        String escFileName = StringUtilHtml.transToHTml(fileName);
        appModel.setEscHttpLogName(escFileName);
        appModel.setHashHttpLogName(MainCommonBiz.getAutoBackUpHashName(escFileName));
        long size = file.length();
        String strSize = cmnBiz.getByteSizeString(size);
        appModel.setHttpLogSize(strSize);

        return appModel;
    }
}
