package jp.groupsession.v2.rng.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;

import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.rng050.Rng050Biz;
import jp.groupsession.v2.rng.rng050.Rng050ParamModel;
import jp.groupsession.v2.rng.rng070.Rng070Biz;
import jp.groupsession.v2.rng.rng070.Rng070ParamModel;
import jp.groupsession.v2.rng.rng130.Rng130Biz;
import jp.groupsession.v2.rng.rng130.Rng130ParamModel;

/**
 * <br>[機  能] 稟議 PDF出力処理(別スレッド)用クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngMultiThread extends Thread  {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngMultiThread.class);

    /** 単体出力ディレクトリ名 */
    public static final String DIRNAME_TEMP = "ringi";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** パラメータ情報 */
    AbstractParamModel paramBase__;
    /** パラメータ情報 */
    private RngMultiThreadModel threadModel__ = new RngMultiThreadModel();
    /** 出力ディレクトリ用ハッシュ値 */
    private GSTemporaryPathModel tempPath__;
    /** セッションユーザSID */
    private int sessionUserSid__ = 0;
    /** 年月日 */
    private String nowDate__ = "";
    /** アプリケーションルートパス */
    private String appRootPath__ = "";




    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param tempPath 出力ディレクトリ用ハッシュ値
     * @param sessionUserSid セッションユーザSID
     * @param nowDate 年月日
     * @param appRootPath アプリケーションルートパス
     */
    public RngMultiThread(
            RequestModel reqMdl,
            Rng050ParamModel paramMdl,
            GSTemporaryPathModel tempPath,
            int sessionUserSid,
            String nowDate,
            String appRootPath
            ) {
        reqMdl__ = reqMdl;
        tempPath__ = tempPath;
        sessionUserSid__ = sessionUserSid;
        nowDate__ = nowDate;
        appRootPath__ = appRootPath;
        paramBase__ = paramMdl;
        threadModel__.setRngDataList(paramMdl.getRng050rngDataList());
        threadModel__.setRngProcMode(paramMdl.getRngProcMode());
        threadModel__.setRngApprMode(paramMdl.getRngApprMode());
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param tempPath 出力ディレクトリ用ハッシュ値
     * @param sessionUserSid セッションユーザSID
     * @param nowDate 年月日
     * @param appRootPath アプリケーションルートパス
     */
    public RngMultiThread(
            RequestModel reqMdl,
            Rng070ParamModel paramMdl,
            GSTemporaryPathModel tempPath,
            int sessionUserSid,
            String nowDate,
            String appRootPath
            ) {
        reqMdl__ = reqMdl;
        tempPath__ = tempPath;
        sessionUserSid__ = sessionUserSid;
        nowDate__ = nowDate;
        appRootPath__ = appRootPath;
        paramBase__ = paramMdl;
        threadModel__.setRngDataList(paramMdl.getRng070dataList());
        threadModel__.setRngProcMode(paramMdl.getRngProcMode());
        threadModel__.setRngApprMode(paramMdl.getRngApprMode());

    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param tempPath 出力ディレクトリ用ハッシュ値
     * @param sessionUserSid セッションユーザSID
     * @param nowDate 年月日
     * @param appRootPath アプリケーションルートパス
     */
    public RngMultiThread(
            RequestModel reqMdl,
            Rng130ParamModel paramMdl,
            GSTemporaryPathModel tempPath,
            int sessionUserSid,
            String nowDate,
            String appRootPath
            ) {
        reqMdl__ = reqMdl;
        tempPath__ = tempPath;
        sessionUserSid__ = sessionUserSid;
        nowDate__ = nowDate;
        appRootPath__ = appRootPath;
        paramBase__ = paramMdl;
        threadModel__.setRngDataList(paramMdl.getRng130rngDataList());
        threadModel__.setRngProcMode(paramMdl.getRngProcMode());
        threadModel__.setRngApprMode(paramMdl.getRngApprMode());
    }

    /**
     * <br>[機  能] PDF出力処理
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {

        Connection con = null;
        boolean succsess = false;

        //プラグイン固有のテンポラリパス取得
        String outTempDir = tempPath__.getTempPath();
        String outputDir = IOTools.replaceFileSep(
                outTempDir + DIRNAME_TEMP + File.separator);
        try {
            ThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, reqMdl__.getDomain());

            //スレッド名の設定
            Thread.currentThread().setName("RingiPDF"
                                            + "-" + System.currentTimeMillis()
                                            + "-" + reqMdl__.getDomain()
                                            + "-" + Thread.currentThread().getId());

            con = GroupSession.getConnection(
                    reqMdl__.getDomain(),
                    1000);
            con.setAutoCommit(false);
            if (paramBase__ instanceof Rng050ParamModel) {
                Rng050ParamModel paramMdl = (Rng050ParamModel) paramBase__;
                paramMdl.setRng050Type(paramMdl.getRngProcMode());
                paramMdl.setRngAdminPageTop(1); //全件取得の為

                Rng050Biz biz = new Rng050Biz(con, reqMdl__);
                List<RingiDataModel> rngDataList = biz.getSearchPdf(
                        paramMdl,
                        reqMdl__.getSmodel(),
                        paramMdl.getRng050pdfOutputMax());
                threadModel__.setRngDataList(rngDataList);
            } else if (paramBase__ instanceof Rng070ParamModel) {
                Rng070ParamModel paramMdl = (Rng070ParamModel) paramBase__;
                paramMdl.setRng070Type(paramMdl.getRngProcMode());
                paramMdl.setRngAdminPageTop(1); //全件取得の為
                Rng070Biz biz = new Rng070Biz(con, reqMdl__);

                List<RingiDataModel> rngDataList = biz.getSearchPdf(
                        paramMdl,
                        reqMdl__.getSmodel(),
                        paramMdl.getRng070pdfOutputMax());

                threadModel__.setRngDataList(rngDataList);

            } else if (paramBase__ instanceof Rng130ParamModel) {
                Rng130ParamModel paramMdl = (Rng130ParamModel) paramBase__;
                paramMdl.setRng130pageTop(1); //全件取得の為
                Rng130Biz biz = new Rng130Biz(con, reqMdl__);
                List<RingiDataModel> rngDataList = biz.getSearchPdf(
                        paramMdl,
                        reqMdl__.getSmodel(),
                        paramMdl.getRng130pdfOutputMax());
                threadModel__.setRngDataList(rngDataList);

            }
            //出力処理
            ArrayList<Integer> sidList = new ArrayList<Integer>();
            int sidCount = 0;
            int pdfNo = 1;
            int maxDigit = String.valueOf(threadModel__.getRngDataList().size()).length();
            boolean cancelFlg = false;
            if (threadModel__.getRngDataList().size() == 0) {
                //0件の場合は失敗として終了
                return;
            }
            for (RingiDataModel dataList : threadModel__.getRngDataList()) {
                sidList.add(dataList.getRngSid());
                sidCount++;
                if (sidCount == 100) {
                    __doOutputPdf(con, sidList,
                            outputDir, maxDigit, pdfNo);
                    sidList = new ArrayList<Integer>();
                    pdfNo = pdfNo + sidCount;
                    sidCount = 0;
                }
                if (__doCheckDir()) {
                    cancelFlg = true;
                    break;
                }
            }
            if (!cancelFlg && sidCount != 0) {
                    __doOutputPdf(con, sidList,
                            outputDir, maxDigit, pdfNo);
            }
            if (!cancelFlg) {
                if (__doCheckDir()) {
                    //TEMPディレクトリ削除
                    GSTemporaryPathUtil.getInstance().deleteTempPath(tempPath__);
                } else {
                    PrintWriter pw = new PrintWriter(outTempDir + "zipStart.flg");
                    pw.close();
                    //出力した勤務表をZIP圧縮する
                    String downloadTmpName = nowDate__
                            + "_ringi.zip";
                    String downloadPath = outTempDir + downloadTmpName;
                    ZipUtil.zipDir(outputDir, downloadPath);

                    pw = new PrintWriter(outTempDir + "pdfCompleat.flg");
                    pw.close();
                }
            } else {
              //TEMPディレクトリ削除
                GSTemporaryPathUtil.getInstance().deleteTempPath(tempPath__);
            }
            con.commit();
            succsess = true;
        } catch (Exception e1) {
            log__.error(e1);
            e1.printStackTrace();
        } finally {
            try {
                //スレッド名に"END-"を設定する
                Thread.currentThread().setName(
                        "END-" + Thread.currentThread().getName());
            } catch (Throwable e) {
            }

            if (con != null) {
                try {
                    if (!succsess) {
                        try {
                            IOTools.isDirCheck(outTempDir, true);
                            PrintWriter pw = new PrintWriter(outTempDir + "error.flg");
                            pw.close();
                            pw = null;
                        } catch (IOToolsException e) {
                            log__.error(e);
                        } catch (FileNotFoundException e) {
                            log__.error(e);
                        } finally {
                            JDBCUtil.rollback(con);
                        }
                    }
                } catch (Exception e) {
                    log__.error(e);
                    throw e;
                } finally {
                    JDBCUtil.closeConnection(con);
                }
            }
            con = null;
        }
    }


    /**
     * <br>[機  能] PDFファイル出力処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param sidList 稟議SIDリスト
     * @param outputDir 出力パス
     * @param maxDigit 出力PDFナンバー最大桁数
     * @param startPdfNo 開始出力PDFナンバー
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doOutputPdf(
            Connection con,
            ArrayList<Integer> sidList,
            String outputDir,
            int maxDigit,
            int startPdfNo)
                    throws SQLException, Exception {

        OutputStream oStream = null;

        //スケジュール(単票)PDF出力用モデル
        RngPdfWriter pdfWriter = new RngPdfWriter(con, reqMdl__);
        ArrayList<RngTanPdfModel> pdfModelList = pdfWriter.getRngPdfDataList(
                                sidList,
                                threadModel__.getRngProcMode(),
                                threadModel__.getRngApprMode(),
                                sessionUserSid__);

        int pdfNo = startPdfNo;
        for (RngTanPdfModel pdfModel : pdfModelList) {
            String outputPdfNo = String.format("%0" + maxDigit + "d", pdfNo);
            //最大100文字になるようにタイトルをカット
            if (pdfModel.getPdfTitle().length() > (100 - 12 - maxDigit)) {
                pdfModel.setPdfTitle(
                        pdfModel.getPdfTitle().substring(0, (100 - 12 - maxDigit))
                );
            }
            String saveFileName = outputPdfNo + "_"
                + pdfModel.getPdfMakeDate().substring(0, 4)
                + pdfModel.getPdfMakeDate().substring(5, 7)
                + pdfModel.getPdfMakeDate().substring(8, 10)
                + "_" +  pdfModel.getFileName().substring(3);
            pdfModel.setSaveFileName(saveFileName);
            try {
                IOTools.isDirCheck(outputDir, true);
                oStream = new FileOutputStream(outputDir + saveFileName);
                RngTanPdfUtil pdfUtil = new RngTanPdfUtil(reqMdl__);
                pdfUtil.createRngTanPdf(pdfModel, appRootPath__, oStream);
            } catch (Exception e) {
                log__.error("稟議(単票)PDF出力に失敗しました。", e);
            } finally {
                if (oStream != null) {
                    oStream.flush();
                    oStream.close();
                }
            }
            pdfNo++;
            sidList = new ArrayList<Integer>();
        }
    }

    /**
     * <br>[機  能] キャンセルフラグ検索処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return boolean cancelフラグ
     */
    private boolean __doCheckDir()
                    throws SQLException, Exception {

        //プラグイン固有のテンポラリパス取得
        String outTempDir = tempPath__.getTempPath();

        File dir = new File(outTempDir + "cancel.flg");
        if (dir.exists()) {
            return true;
        }
        File[] list = dir.listFiles();
        boolean cancelFlg = false;
        if (list != null) {
            String cancelDir = outTempDir + "cancel.flg";

            for (int i = 0; i < list.length; i++) {
                //ファイルの場合
                if (list[i].isFile()) {
                    if (list[i].toString().equals(cancelDir)) {
                        cancelFlg = true;
                    }
                }
            }
        }
        return cancelFlg;
    }
}
