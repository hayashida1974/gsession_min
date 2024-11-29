package jp.groupsession.v2.usr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.CmnImpTimeModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr032.UserCsvCheck;
import jp.groupsession.v2.usr.usr032kn.UserCsvImport;
import jp.groupsession.v2.usr.usr032kn.UserCsvImportModel;

/**
 *
 * <br>[機  能] ユーザや所属情報、グループなどを自動インポートする
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AutoImportListnerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(AutoImportListnerImpl.class);

    /** ユーザ自動連係処理実行状況 */
    private static boolean isImportActive = false;

    /**
     * <p>isImportActive を取得します。
     * @return isImportActive
     * @see jp.groupsession.v2.usr.AutoImportListnerImpl#isImportActive
     */
    public static synchronized boolean isImportActive() {
        return isImportActive;
    }
    /**
     *
     * <br>[機  能] isImportActive にtrueをsetする
     * <br>[解  説] isImportActive がすでにtrueの場合falseを返す
     * <br>[備  考] インポート処理終了後にdoFinishImportActiveを呼ぶこと
     * @return isImportActive
     */
    private static synchronized boolean __doImportActive() {
        if (isImportActive) {
            return false;
        }
        AutoImportListnerImpl.isImportActive = true;
        return true;
    }
    /**
    *
    * <br>[機  能] isImportActive にfalseをsetする
    * <br>[解  説]
    * <br>[備  考]
    */
    private static synchronized void __doFinishImportActive() {
        AutoImportListnerImpl.isImportActive = false;
    }

    /**
     * <br>[機  能] 1日間隔で実行されるJob
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

    }

    /**
     * <br>[機  能] 1時間間隔で実行されるJob
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param)
            throws Exception {
        MainCommonBiz mcBiz = new MainCommonBiz();
        CmnImpTimeModel citMdl = mcBiz.getCmnImpTimeModel(con);
        if (citMdl == null) {
            return;
        }

        boolean impFlg = false;
        if (citMdl.getCitUsrTimeFlg() == GSConstUser.IMPORT_TIMING_HOUR) {
            impFlg = true;
        } else if (citMdl.getCitUsrTimeFlg() == GSConstUser.IMPORT_TIMING_SELECT) {
            UDate date = new UDate();
            if (date.getIntHour() == citMdl.getCitUsrTime().getIntHour()) {
                impFlg = true;
            }
        }

        if (impFlg) {
            doBatch(con, param, citMdl);
        }
    }

    /**
     * <br>[機  能] 5分間隔で実行されるJob
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {
        MainCommonBiz mcBiz = new MainCommonBiz();
        CmnImpTimeModel citMdl = mcBiz.getCmnImpTimeModel(con);
        if (citMdl == null) {
            return;
        }

        if (citMdl.getCitUsrTimeFlg() == GSConstUser.IMPORT_TIMING_MIN) {
            doBatch(con, param, citMdl);
        }
    }

    /**
     * <br>[機  能] バッチ実行
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @param citMdl CMN_IMP_TIMEモデル
     * @throws Exception バッチ処理実行時例外
     */
    private void doBatch(Connection con,
            IBatchModel param, CmnImpTimeModel citMdl) throws Exception {

        String domain = param.getDomain();
        String appRootPath = (String) param.getGsContext().get(GSContext.APP_ROOT_PATH);
        String impFilePath = __getImpPath(appRootPath);

        //インポートフォルダからCSVファイルを取得
        File tempDirPath = new File(impFilePath);
        File[] importFileList = tempDirPath.listFiles();
        //自動インポート「しない」の場合、取り込みファイルがない場合は終了
        if (citMdl.getCitUsrFlg() == GSConstUser.AUTO_IMPORT_NO
                || importFileList == null || importFileList.length < 1) {
            return;
        }
        if (isImportActive()) {
            //ユーザ自動連係処理実行中
            return;
        }
        UDate startTime = new UDate();
        long start = startTime.getTimeMillis();
        //0:失敗 1:完了 2:インポートファイルなし
        int success = -1;

        __outPutBatchLog(con,
                param.getDomain(),
                "",
                getClass().getName(),
                "ユーザ自動連携 バッチ処理",
                null,
                "batch user coop is end",
                success);

        con.commit();
        success = autoUsrImport(con, appRootPath, domain, true);

        CommonBiz cmnBiz = new CommonBiz();
        String receiveTime = cmnBiz.getExecBatchTimeString(start);

        __outPutBatchLog(con,
                param.getDomain(),
                "",
                getClass().getName(),
                "ユーザ自動連携 バッチ処理",
                "実行時間: " + receiveTime,
                "batch user coop is end",
                success);



    }

    /**
     * 自動連携 バッチ処理全般のログ出力を行う
     * @param con コネクション
     * @param domain ドメイン
     * @param opCode 操作
     * @param pgId 画面・機能ID
     * @param pgName 画面・機能名
     * @param value 内容
     * @param logCode 操作コード(内部)
     * @param success 0:失敗 1:完了 2:インポートファイルなし -1:開始
     * @throws GSException GS用汎実行例外
     */
    private void __outPutBatchLog(
            Connection con,
            String domain,
            String opCode,
            String pgId,
            String pgName,
            String value,
            String logCode,
            int success) throws GSException {

        int usrSid = -1;
        GsMessage gsMsg = new GsMessage();

        if (success == 0) {
            opCode = gsMsg.getMessage("main.man420.16");
        } else if (success == 1 || success == 2) {
            opCode = gsMsg.getMessage("main.man420.15");
        } else if (success == -1) {
            opCode = gsMsg.getMessage("main.man420.14");
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(GSConstLog.LEVEL_TRACE);
        logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
        logMdl.setLogPluginName(gsMsg.getMessage("cmn.sys.public"));
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(pgName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(null);
        logMdl.setVerVersion(GSConst.VERSION);
        logMdl.setLogCode(logCode);

        LoggingBiz logBiz = new LoggingBiz(con);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     *
     * <br>[機  能]ユーザ情報の自動インポートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param domain ドメイン
     * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
     * @return 0:失敗 1:完了 2:インポートファイルなし
     * @throws Exception 実行時例外
     */
    public int autoUsrImport(Connection con,
            String appRootPath, String domain, boolean tempDirFlg) throws Exception {


        boolean successFlg = false;
        MlCountMtController cntCon = null;
        //メッセージ取得のためリクエストモデルを作成
        RequestModel reqMdl = new RequestModel();
        reqMdl.setDomain(domain);
        reqMdl.setLocale(Locale.JAPANESE);
        //プラグインコンフィグ取得
        PluginConfig pconfig = GroupSession.getResourceManager().getPluginConfig(domain);
        
        //インポートファイル、失敗ファイル、成功ファイルのパスを取得
        //(いずれかのパスが表記されていない場合はインポートを行わない)
        String impFilePath = __getImpPath(appRootPath);
        String successPath = __getSuccessPath(appRootPath);
        String failPath = __getFailPath(appRootPath);
        
        String tempFileDir = GroupSession.getResourceManager().getTempPath(domain);
        
        //CSVを一時保管するテンポラリディレクトリパスを取得
        String csvTempDir = __getCsvTempPath(tempFileDir, tempDirFlg);
        
        ArrayList<File> fileList = new ArrayList<File>();
        if (!__doImportActive()) {
            return 3;
        }
        try {
            //採番クラス取得
            cntCon = GroupSession.getResourceManager().getCountController(domain);

            //メール本文用メッセージ
            StringBuilder mailLetter = new StringBuilder();

            //インポートファイルを置くディレクトリがない場合は作成
            IOTools.isDirCheck(impFilePath, true);

            //インポートユーザの登録者としてadminユーザを設定
            BaseUserModel bModel = new BaseUserModel();
            bModel.setUsrsid(GSConst.SYSTEM_USER_ADMIN);
            reqMdl.setSmodel(bModel);
            
            //インポートフォルダからコピーした一時保管CSVファイルを取得
            fileList = __getImportFile(impFilePath, csvTempDir);

            //インポートファイルがない場合は終了
            if (fileList == null || fileList.size() < 1) {
                successFlg = true;
                return 2;
            }


            //インポートを行うファイルのチェックおよびエラーメッセージの取得
            int impMode = GSConstUser.MODE_AUTO_IMPORT;
            ArrayList<File> failFileList = new ArrayList<>();

            //インポートファイルのチェック、エラーがなければそれをインポート
            for (File impFile:fileList) {
                boolean checkCsv = __checkOneCsv(con, reqMdl, impFile,
                        impMode, tempFileDir, tempDirFlg);
                if (!checkCsv) {
                    failFileList.add(impFile);
                    //エラーがあったファイルを指定フォルダ(失敗フォルダ)へ保存
                    __csvFileSave(failPath, impFile);
                    //失敗したファイルをメール添付用フォルダにコピー
                    String objTempDir = __getObjTempPath(tempFileDir, tempDirFlg);
                    __moveSmailTempFile(failPath, objTempDir, impFile);
                    //エラー発生時のメッセージ作成
                    __makeLetter(reqMdl, mailLetter, impFile, false);
                    continue;
                } else {

                    UserCsvImportModel csvImportModel = new UserCsvImportModel();
                    csvImportModel.setGlist(null);
                    csvImportModel.setDfGroup(null);
                    csvImportModel.setUserSid(GSConst.SYSTEM_USER_ADMIN);

                    //実行モード(0:インポート)
                    csvImportModel.setMode(0);
                    // 既存のユーザ情報更新フラグ
                    csvImportModel.setUpdateFlg(0);
                    //初回ログイン時パスワード変更フラグ
                    csvImportModel.setPswdKbn(0);
                    //グループ作成フラグ
                    csvImportModel.setInsertFlg(0);
                    //パスワード更新フラグ(csvImport内でユーザログインIDを元に再設定)
                    csvImportModel.setUpdatePassFlg(GSConstUser.PASS_CHANGE_OK);
                    UserCsvImport csvImport =
                            new UserCsvImport(reqMdl, cntCon, con, csvImportModel, impMode);
                    //ユーザリスナー取得
                    IUserGroupListener[] lis = UserUtil.getUserListeners(pconfig);
                    csvImport.setLis(lis);

                    //csvファイルの読み込み開始
                    csvImport.readFile(impFile, Encoding.WINDOWS_31J);

                    //正常終了メッセージの作成を行う
                    __makeLetter(reqMdl, mailLetter, impFile, true);

                }
            }

            //インポート失敗ファイルがある場合はメールへ添付
            if (!failFileList.isEmpty()) {
                //エラーが存在する場合はエラー文を添付する
                __errorLogTemp(tempFileDir, failPath, tempDirFlg);
            }

            //成功したファイルを保存する(置き場所がない場合は作成する)
            for (File removeFile:failFileList) {
                fileList.remove(removeFile);
            }
            if (!fileList.isEmpty()) {
                __csvFileSave(successPath, fileList);
            }
            con.commit();
            successFlg = true;
            
            //インポートフォルダにあるファイルを削除する
            IOTools.deleteInDir(impFilePath);

            //メールの送信
            __sendSmail(con, cntCon, appRootPath, mailLetter.toString(),
                        reqMdl, pconfig, tempDirFlg);
            con.commit();
            
            //テンポラリディレクトリ（一時保管）内のデータを削除する
            IOTools.deleteDir(__getTempPath(tempFileDir, tempDirFlg));
        } catch (Exception e) {
            JDBCUtil.rollback(con);
            //テンポラリディレクトリ（一時保管）内のデータを削除する
            IOTools.deleteDir(__getTempPath(tempFileDir, tempDirFlg));
            fileList = new ArrayList<File>();
            log__.error("ユーザデータ送信失敗(ユーザプラグインHrefデータ取得)", e);
        } finally {
            __doFinishImportActive();
            
            if (!successFlg) {
                //ファイルを指定フォルダ(失敗フォルダ)へ保存
                try {
                    //インポートフォルダからコピーした一時保管CSVファイルを取得
                    Enumeration<File> impList = IOTools.getFiles(impFilePath);
                    while (impList.hasMoreElements()) {
                        __csvFileSave(failPath, impList.nextElement());
                    }
                    IOTools.deleteInDir(impFilePath );
                } catch (Exception e) {
                    log__.error("自動連係ファイルの失敗フォルダへの移動に失敗", e);
                }
                StringBuilder sb = new StringBuilder();
                GsMessage gsMsg = new GsMessage(reqMdl);
                String failedImport = gsMsg.getMessage("main.man420.19");
                sb.append(failedImport);
                sb.append("\n");
                sb.append(gsMsg.getMessage("main.man420.18"));
                //メールの送信
                __sendSmail(con, cntCon, appRootPath, sb.toString(), reqMdl, pconfig, tempDirFlg);
                con.commit();
            }
        }
        if (fileList.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     *
     * <br>[機  能]取り込みファイルのインポート結果を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param mailLetter メール本文
     * @param impFile 取り込みファイル
     * @param succsess 成功フラグ(true:取り込み成功)
     */
    private void __makeLetter(RequestModel reqMdl,
            StringBuilder mailLetter,
            File impFile, boolean succsess) {
        GsMessage gsmsg = new GsMessage(reqMdl);
        mailLetter.append(impFile.getName());
        mailLetter.append("[");
        mailLetter.append(gsmsg.getMessage("main.src.19"));
        mailLetter.append("]");
        mailLetter.append(gsmsg.getMessage("user.usr032.3"));
        if (succsess) {
            mailLetter.append(gsmsg.getMessage("cmn.complete"));
        } else {
            mailLetter.append(gsmsg.getMessage("man.error"));
            mailLetter.append("\r");
            mailLetter.append(gsmsg.getMessage("main.man420.13"));
        }
        mailLetter.append("\r");
        mailLetter.append("-------------------------------------------------------");
        mailLetter.append("\r");
        mailLetter.append("\r");
    }

    /**
    *
    * <br>[機  能]インポートcsvファイルの値をチェックする
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param checkFile チェックファイル
    * @param impMode インポートモード
    * @param tempFileDir テンポラリディレクトリ
     * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
    * @return データに不正があるかどうか(false:不正あり)
    * @throws Exception 実行時例外
    */
   private  boolean __checkOneCsv(Connection con, RequestModel reqMdl,
           File checkFile, int impMode, String tempFileDir,
           boolean tempDirFlg) throws Exception {

           ActionErrors errors = new ActionErrors();
           String fileName = checkFile.getName();
           String userImportFilePath = checkFile.getPath();
           //ファイルが存在しない場合
           if (!checkFile.exists()) {
               log__.warn("取り込みCSVファイル 「" + fileName + "」が存在しません。");
               return false;
           }

           boolean csvAble = true;
           //拡張子チェック
           String strExt = StringUtil.getExtension(fileName);
           if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
               ActionMessage msg =
                   new ActionMessage("error.select.required.text", fileName);
               StrutsUtil.addMessage(errors, msg, fileName + "error.select.required.text");
               csvAble = false;
           }

           if (csvAble) {
               //パスワード変更区分はcsvCheck内でユーザログインIDを元に再設定
               UserCsvCheck csvCheck = new UserCsvCheck(errors,
                                                       impMode,
                                                        con,
                                                        0,
                                                        0,
                                                        reqMdl,
                                                        true);

               // CSVデータ読込み
               boolean isErr = csvCheck.isCsvDataOk(userImportFilePath);
               if (isErr) {
                   // CSVデータ データ整合性チェック
                   csvAble = false;
               } else {
                   // CSVデータ 件数チェック
                   int dataCnt = csvCheck.getCount(); // 有効データ件数

                   //登録対象にログイン停止、削除ユーザ以外が含まれる場合、ユーザ数上限チェックを行う
                   if (dataCnt > (csvCheck.getDeleteCount() + csvCheck.getLoginMukoCount())) {
                       UsrCommonBiz ucBiz = new UsrCommonBiz(con, reqMdl);
                       dataCnt -= csvCheck.getOverlabCount(); // 編集するユーザ数を有効データ件数から除外
                       dataCnt -= csvCheck.getDeleteCount();  // 削除するユーザ数を有効データ件数から除外

                       //ログイン無効から有効に変更するユーザ数を加算する
                       CmnUsrmDao usrDao = new CmnUsrmDao(con);
                       dataCnt += usrDao.getMukoUserCount(csvCheck.getLoginUkoUserList());

                       //ログイン有効から無効に変更するユーザを除外する
                       dataCnt -= usrDao.getYukoUserCount(csvCheck.getLoginMukoUserList());

                       //新規登録されるログイン停止ユーザを除外する
                       dataCnt -= csvCheck.getNewLoginMukoCount();

                       if (ucBiz.isUserCountOver(reqMdl, con, dataCnt, csvCheck.getDeleteCount())) {
                           //ユーザ数制限エラー
                           IGsResourceManager resourceManager = GroupSession.getResourceManager();
                           ActionMessage msg =
                           new ActionMessage("error.usercount.limit.over",
                                   resourceManager.getUserCountLimit(reqMdl.getDomain()));
                           StrutsUtil.addMessage(errors, msg,
                                                   fileName + "error.usercount.limit.over");
                           csvAble = false;
                       }
                   }
               }
           }

           //エラーメッセージを作成
           if (!csvAble) {
               //エラーログ用のメッセージ
               StringBuilder errorMsg = new StringBuilder();
               String msgString = __createErrMsg(errors);
               errorMsg.append(checkFile.getName());
               errorMsg.append("\r\n");
               errorMsg.append("-------------------------------------------------------");
               errorMsg.append("\r\n");
               errorMsg.append(msgString);
               errorMsg.append("\r\n");
               errorMsg.append("\r\n");

               //エラーログファイルの書き出し
               appendWrite(errorMsg, tempFileDir, GSConstUser.USER_IMPORT_FAILED_LOG,
                           tempDirFlg);
               return false;
           }

       return true;
   }

   /**
    *
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param addMessage 追記する文
    * @param tempFileDir テンポラリディレクトリ
    * @param fileName 保存するファイルの名前
     * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
    * @throws IOToolsException IOTools例外
    * @throws IOException 入出力例外
    */
    private void appendWrite(StringBuilder addMessage, String tempFileDir, String fileName,
                               boolean tempDirFlg)
            throws IOToolsException, IOException {
            //ファイルに出力
           BufferedWriter out = null;
           StringUtil.replaceReturnCode(addMessage.toString(), "\r\n");
           try {
               String errorLog = getTempErrorLogPath(tempFileDir, tempDirFlg);
               IOTools.isFileCheck(__getObjTempPath(tempFileDir, tempDirFlg), fileName, true);
               out = new BufferedWriter(
                       new OutputStreamWriter(new FileOutputStream(errorLog, true), "UTF-8"));
               out.write(addMessage.toString());
           } catch (IOException e) {
               throw e;
           } finally {
               out.close();
           }
    }

   /**
     * <br>[機  能] ユーザ情報CSVファイルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param impFilePath ユーザ情報CSVファイル設置ディレクトリ
     * @param csvTempPath cvs一時保管ディレクトリ
     * @return 取得したユーザ情報CSVファイルのファイルパス
     * @throws IOException ファイルの取得に失敗
     * @throws IOToolsException ファイルの取得に失敗
     */
    private ArrayList<File> __getImportFile(String impFilePath, String csvTempPath)
            throws IOException, IOToolsException {

        //ファイルをコピー
        __retrieveFileAll(impFilePath, csvTempPath);

        //ファイルを読み込む
        File tempDirPath = new File(csvTempPath);
        File[] importFileList = tempDirPath.listFiles();
        ArrayList<File> fileList = new ArrayList<File>();
        Map<String, File> fileMap = new HashMap<String, File>();
        ArrayList<String> fileNameList = new ArrayList<String>();

        for (File file : importFileList) {
            fileNameList.add(file.getName());
            fileMap.put(file.getName(), file);
        }

        //ファイル名でソートする
        Collections.sort(fileNameList);
        for (String fileName : fileNameList) {
            fileList.add(fileMap.get(fileName));
        }

        return fileList;
    }


    /**
     *
     * <br>[機  能]エラーログをテキストファイルで保存・メールに添付する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param failPath インポート失敗ファイル保存箇所
     * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
     * @throws IOException 入出力例外
     * @throws IOToolsException IOTools例外
     * @throws Exception 実行時例外
     */
    private void __errorLogTemp(String appRootPath, String failPath,
            boolean tempDirFlg)
            throws IOException, IOToolsException, Exception {

        String errorLog = getTempErrorLogPath(appRootPath, tempDirFlg);
        File errorFile = new File(errorLog);
        ArrayList<File> errorFileList = new ArrayList<>();
        errorFileList.add(errorFile);
        //エラーログは失敗インポートフォルダに保存しておく
        __csvFileSave(failPath, errorFileList);
        //エラーログをショートメールに添付する
        __moveSmailTempFile(failPath, __getObjTempPath(appRootPath, tempDirFlg), errorFile);
    }

    /**
     *
     * <br>[機  能]特定のファイルを指定パスに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param savePath 保存先パス
     * @param saveFile 保存ファイル
     * @throws IOException 入出力例外
     * @throws IOToolsException 入出力時例外
     */
    private void __csvFileSave(String savePath, File saveFile)
            throws IOException, IOToolsException {

        //保存先フォルダが存在しない場合は作成する
        IOTools.isDirCheck(savePath, true);

        //指定されたパスにファイルをコピーする
        String saveFileName = saveFile.getName();
        //既にファイルが存在している場合はファイル名先頭に連番を付ける

        //ファイルの拡張子を取得し、拡張子を除いたファイル名を取得
        String extension = StringUtil.getExtension(saveFileName);
        String pfixFileName = saveFileName;
        if (!StringUtil.isNullZeroString(extension)) {
            pfixFileName = saveFileName.substring(0, saveFileName.indexOf(extension));
        }

        int fileCount = 1;
        while (IOTools.isFileCheck(savePath, saveFileName, false)) {
            fileCount++;
            saveFileName = pfixFileName + "_" + fileCount + extension;
        }
        //
        saveFileName = savePath + saveFileName;
        IOTools.copyBinFile(saveFile.getPath(), saveFileName);

    }

    /**
     *
     * <br>[機  能]特定のファイルを指定パスに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param savePath 保存先パス
     * @param saveFileList 保存ファイルリスト
     * @throws IOException 入出力例外
     * @throws IOToolsException 入出力時例外
     */
    private void __csvFileSave(String savePath, ArrayList<File> saveFileList)
            throws IOException, IOToolsException {

        //保存先フォルダが存在しない場合は作成する
        IOTools.isDirCheck(savePath, true);

        //指定されたパスにファイルをコピーする
        for (File saveFile:saveFileList) {
            String saveFileName = saveFile.getName();
            //既にファイルが存在している場合はファイル名先頭に連番を付ける

            //ファイルの拡張子を取得し、拡張子を除いたファイル名を取得
            String extension = StringUtil.getExtension(saveFileName);
            String pfixFileName = saveFileName;
            if (!StringUtil.isNullZeroString(extension)) {
                pfixFileName = saveFileName.substring(0, saveFileName.indexOf(extension));
            }

            int fileCount = 1;
            while (IOTools.isFileCheck(savePath, saveFileName, false)) {
                fileCount++;
                saveFileName = pfixFileName + "_" + fileCount + extension;
            }
            //
            saveFileName = savePath + saveFileName;
            IOTools.copyBinFile(saveFile.getPath(), saveFileName);
        }

    }

    /**
     * <br>[機  能] 指定したディレクトリ内のファイルを全てコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param remoteDir コピー対象ディレクトリパス
     * @param toCopy コピー先
     * @throws IOException ファイルのコピーに失敗
     * @throws IOToolsException コピー先ディレクトリが不正
     */
    public static void __retrieveFileAll(String remoteDir, String toCopy)
            throws IOException, IOToolsException {
        IOTools.isDirCheck(remoteDir, true);
        IOTools.isDirCheck(toCopy, true);
        File remoteFile = new File(remoteDir);
        File saveFile = new File(toCopy);

        IOTools.copyDir(remoteFile, saveFile);
    }

    /**
     * <br>[機  能] CSVファイルインポート失敗通知をショートメールで送信する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番コントロール
     * @param appRootPath アプリケーションルートパス
     * @param errorMsg エラーメッセージ
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
     * @throws Exception 実行例外
     */
    private void __sendSmail(Connection con, MlCountMtController cntCon,
            String appRootPath, String errorMsg,
            RequestModel reqMdl, PluginConfig pconfig, boolean tempDirFlg) throws Exception {

        SmlSenderModel senderMdl = new SmlSenderModel();

        senderMdl = __setSmlSenderModell(con, appRootPath, errorMsg, reqMdl, tempDirFlg);

        //メール送信処理開始
        try {
            SmlSender sender = new SmlSender(con, cntCon, senderMdl, pconfig, appRootPath, reqMdl);
            sender.execute();
        } catch (TempFileException e) {
            Throwable cause = GSAttachFileNotExistException.searchCause(e);
            if (cause != null) {
                log__.error("", cause);
                //エラーログの添付に失敗した場合は添付なしで再実行
                senderMdl.setTempFile(false);
                SmlSender sender = new SmlSender(con, cntCon, senderMdl, pconfig, appRootPath, reqMdl);
                sender.execute();
            } else {
                throw e;
            }
        }

    }

    /**
     * <br>[機  能] ショートメール送信モデルに値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param msgString エラー、CSVファイルエラー発生行数
     * @param reqMdl リクエストモデル
     * @return smlModel ショートメール送信用モデル
     * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
     * @throws Exception 実行例外
     */
    private SmlSenderModel __setSmlSenderModell(
        Connection con,
        String appRootPath,
        String msgString,
        RequestModel reqMdl,
        boolean tempDirFlg) throws Exception {

        //管理者ユーザSID取得
        CmnBelongmDao cbDao = new CmnBelongmDao(con);
        List<Integer> adminUsrSidList
        = cbDao.selectBelongUserSid(Integer.valueOf(GSConstUser.USER_KANRI_ID));

        //ショートメール本文文字数チェック
        if (msgString.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            msgString = msgString.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();

        //差出人(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(adminUsrSidList);
        //タイトル
        GsMessage gsmsg = new GsMessage(reqMdl);
        StringBuilder titleSb = new StringBuilder();
        titleSb.append("[");
        titleSb.append(gsmsg.getMessage("main.src.19"));
        titleSb.append("]");
        titleSb.append(gsmsg.getMessage("user.usr032.3"));
        String title =  titleSb.toString();
        title = StringUtil.trimRengeString(title,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(title);
        //本文
        smlModel.setSendBody(msgString);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //添付ファイル
        //アプリケーションルートパス取得ディレクトリ取得
        String rootDir = GroupSession.getResourceManager().getTempPath(reqMdl.getDomain());
        smlModel.setSaveFulPath(__getObjTempPath(rootDir, tempDirFlg));
        //添付ファイルフラグ
        smlModel.setTempFile(true);

        return smlModel;

    }

    /**
     * <br>[機  能] エラーメッセージを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @return msgString エラーメッセージ
     */
    @SuppressWarnings("unchecked")
    private String __createErrMsg(ActionErrors errors) {
        //エラー行が存在する場合は行数、エラー内容をセットする
        ResourceBundle resources = ResourceBundle.getBundle("Messages");
        Iterator<ActionMessage> messages = errors.get();
        String msgString = "";
        String msgKeyString = "";
        Object[] msgVals = null;

        while (messages.hasNext()) {
            ActionMessage msg = messages.next();
            msgVals = new Object[msg.getValues().length];
            msgKeyString = resources.getString(msg.getKey());

            for (int i = 0; i < msg.getValues().length; i++) {
                msgVals [i] = msg.getValues()[i];
            }
            msgString += MessageFormat.format(msgKeyString, msgVals) + "\r\n";
            msgString  = StringUtil.substitute(msgString, "<br>", "\r\n");
        }

        return msgString;
    }

    /**
     * <br>[機  能] インポート失敗ファイルをショートメール送信用のディレクトリへ移動する
     * <br>[解  説] fil,obj形式
     * <br>[備  考]
     * @param tempDir 添付ファイルディレクトリ
     * @param tempDirCopyFail インポート失敗CSVファイル保存用ディレクトリ
     * @param file 対象ファイル
     * @throws IOToolsException ファイル入出力時実行時例外
     * @throws Exception ショートメールの送付に失敗
     */
    private void __moveSmailTempFile(String tempDir, String tempDirCopyFail, File file)
            throws IOToolsException, Exception {

        //現在日付の文字列(YYYYMMDD)を取得
        String dateStr = (new UDate()).getDateString();

        //TEMPディレクトリ内のファイル数から連番を取得する
        int fileNum = Cmn110Biz.getFileNumber(tempDirCopyFail, dateStr) + 1;
        File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイルのアップロード
        __upload(file, tempDirCopyFail, saveFilePath.getName());
        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(file.getName());
        fileMdl.setSaveFileName(saveFilePath.getName());
        fileMdl.setUpdateKbn(0);

        ObjectFile objFile = new ObjectFile(tempDirCopyFail, objFilePath.getName());
        objFile.save(fileMdl);
    }

    /**
     * [機  能] アップロードされたファイルを引数で指定した出力先にコピーを行う<br>
     * [解  説]<br>
     * [備  考]<br>
     * @param formFile アップロードされたファイル
     * @param outDir 出力先ディレクトリ
     * @param outFileName 出力先ファイル名
     * @throws Exception システムエラー
     */
    private void __upload(File formFile, String outDir, String outFileName)
        throws Exception {

        try {
            File makeUserDir = new File(outDir);
            if (!makeUserDir.exists()) {
                makeUserDir.mkdirs();
            }

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(formFile));

            File fileup = new File(outDir + outFileName);

            BufferedOutputStream outStream = null;
            try {
                outStream =
                    new BufferedOutputStream(new FileOutputStream(fileup));

                long mapLen = formFile.length();
                int bufSize = 1024;
                if (bufSize > mapLen) {
                    bufSize = new Long(mapLen).intValue();
                }

                byte[] bytes = new byte[bufSize];
                int s = 0;
                long readSize = 0;
                while ((s = inStream.read(bytes)) != -1) {
                    outStream.write(bytes, 0, s);
                    //正確なバイトサイズで書き込むための処理
                    readSize = readSize + bytes.length;
                    int zanSize = new Long(mapLen - readSize).intValue();
                    if (zanSize < bufSize) {
                        bytes = new byte[zanSize];
                    }
                    if (zanSize <= 0) {
                        break;
                    }
                }
            } finally {
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (outStream != null) {
                        outStream.close();
                    }
                } catch (IOException e) {
                }
            }

        } finally {
            //ファイルオブジェクト削除
            formFile.delete();
        }
    }

   /**
    * <br>[機  能] テンポラリディレクトリのパスを返す
    * <br>[解  説] /${tmpルート}/userImport/${自動or強制実行} を返す
    * <br>[備  考]
    * @param rootDir アプリケーションルートパス
    * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
    * @return パス
    */
   private String __getTempPath(String rootDir, boolean tempDirFlg) {
       StringBuffer tempDir = new StringBuffer();
       tempDir.append(rootDir);
       if (tempDirFlg) {
         tempDir.append("/userImport/" + GSConstUser.IMPORT_AUTO_DIR + "/");
       } else {
         tempDir.append("/userImport/" + GSConstUser.IMPORT_MANUAL_DIR + "/");
       }
       String savePath = IOTools.setEndPathChar(tempDir.toString());
       savePath = IOTools.replaceFileSep(savePath);
       return savePath;
   }

   /**
    * <br>[機  能] CSV保存用のテンポラリディレクトリのパスを返す
    * <br>[解  説] /${tmpルート}/userImport/csv を返す
    * <br>[備  考]
    * @param rootDir アプリケーションルートパス
    * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
    * @return パス
    */
   private String __getCsvTempPath(String rootDir, boolean tempDirFlg) {

       StringBuffer tempDir = new StringBuffer();
       tempDir.append(rootDir);
       if (tempDirFlg) {
         tempDir.append("/userImport/" + GSConstUser.IMPORT_AUTO_DIR + "/csv/");
       } else {
         tempDir.append("/userImport/" + GSConstUser.IMPORT_MANUAL_DIR + "/csv/");
       }
       String savePath = IOTools.setEndPathChar(tempDir.toString());
       savePath = IOTools.replaceFileSep(savePath);

       return savePath;
   }

   /**
    * <br>[機  能] sml添付オブジェクトファイル保存用のテンポラリディレクトリのパスを返す
    * <br>[解  説] /${tmpルート}/userImport/${自動or強制実行}/tmp を返す
    * <br>[備  考]
    * @param rootDir アプリケーションルートパス
    * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
    * @return パス
    */
   private String __getObjTempPath(String rootDir, boolean tempDirFlg) {

       StringBuffer tempDir = new StringBuffer();
       tempDir.append(rootDir);
       if (tempDirFlg) {
         tempDir.append("/userImport/" + GSConstUser.IMPORT_AUTO_DIR + "/tmp/");
       } else {
         tempDir.append("/userImport/" + GSConstUser.IMPORT_MANUAL_DIR + "/tmp/");
       }
       String savePath = IOTools.setEndPathChar(tempDir.toString());
       savePath = IOTools.replaceFileSep(savePath);

       return savePath;
   }

   /**
   *
   * <br>[機  能]引数で指定された種類のパスを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param dirPath 取得したいパスの種類
   * @param appRootPath アプリケーションルートパス
   * @return 取得したいパス
   */
  private String __getEtcPath(String dirPath, String appRootPath) {
      String path = NullDefault.getString(ConfigBundle.getValue(dirPath), "");

      //取得した設定ファイルの値が空の場合はデフォルトパスを渡す
      if (path.equals("")) {
          StringBuilder defPath = new StringBuilder();
          defPath.append(appRootPath);
          defPath.append(GSConstUser.AUTO_IMPORT_DEF_PATH);
          switch (dirPath) {
              case GSConstUser.USER_IMPORT_FILE_DIR:
                  defPath.append(GSConstUser.USER_IMPORT);
                  break;
              case GSConstUser.USER_IMPORT_SUCCESS_DIR:
                  defPath.append(GSConstUser.USER_IMPORT_SUCCESS);
                  break;
              case GSConstUser.USER_IMPORT_FAIL_DIR:
                  defPath.append(GSConstUser.USER_IMPORT_FAILED);
                  break;
              default:
                  break;
          }
          path = defPath.toString();
      }

      path = IOTools.setEndPathChar(path);
      path = IOTools.replaceFileSep(path);
      return path;
  }

  /**
   * <br>[機  能] 読み込みcsvファイルのあるパスを返す
   * <br>[解  説] 設定ファイルが空の場合はデフォルトのパスを返す
   * <br>[備  考]
   * @param appRootPath アプリケーションルートパス
   * @return パス
   */
  private String __getImpPath(String appRootPath) {
      String importPath =  __getEtcPath(GSConstUser.USER_IMPORT_FILE_DIR, appRootPath);
      return importPath;
  }

  /**
   * <br>[機  能] 読み込み可能csvファイルを保存するパスを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param appRootPath アプリケーションルートパス
   * @return パス
   */
  private String __getSuccessPath(String appRootPath) {
      return __getEtcPath(GSConstUser.USER_IMPORT_SUCCESS_DIR, appRootPath);
  }

  /**
   * <br>[機  能] 読み込み不可能csvファイルを保存するパスを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param appRootPath アプリケーションルートパス
   * @return パス
   */
  private String __getFailPath(String appRootPath) {
      return __getEtcPath(GSConstUser.USER_IMPORT_FAIL_DIR, appRootPath);
  }


  /**
  *
  * <br>[機  能]インポートエラーログのファイルパスを取得
  * <br>[解  説]
  * <br>[備  考]
  * @param appRootPath アプリケーションルートパス
  * @param tempDirFlg テンポラリディレクトリパスフラグ(true:自動 false:強制実行)
  * @return インポートエラーログのファイルパス
  */
 private String getTempErrorLogPath(String appRootPath, boolean tempDirFlg) {
     return __getObjTempPath(appRootPath, tempDirFlg) + "TempErrorLog.txt";
 }

}
