package jp.groupsession.v2.cmn;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] GroupSessionにおけるテンポラリディレクトリ利用におけるユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSTemporaryPathUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSTemporaryPathUtil.class);

    /**
     * コンストラクタ
     */
    private GSTemporaryPathUtil() {
    }

    /**
     *
     * <br>[機  能] GSTemporaryPathUtilを生成し返す
     * <br>[解  説]
     * <br>[備  考]
     * @return GSTemporaryPathUtil
     */
    public static GSTemporaryPathUtil getInstance() {
        return new GSTemporaryPathUtil();

    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param model GSTemporaryPathModel
     * @throws IOToolsException IOToolsException
     */
    public void createTempDir(GSTemporaryPathModel model) throws IOToolsException {
        IOTools.isDirCheck(model.getTempPath(), true);
    }

    /**
     *
     * <br>[機  能] テンポラリディレクトリを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリパス
     * @throws IOToolsException 実行時例外
     */
    public void createTempDir(RequestModel reqMdl, String pluginId, String dirId, String... subDir)
            throws IOToolsException {
        createTempDir(GSTemporaryPathModel.getInstance(reqMdl, pluginId, dirId, subDir));
    }

    /**
     *
     * <br>[機  能] テンポラリディレクトリパスを生成し返す
     * <br>[解  説] 画面処理のテンポラリディレクトリパス生成用
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     * @return テンポラリディレクトリパス
     */
    public String getTempPath(RequestModel reqMdl, String pluginId, String dirId,
            String... subDir) {

        if (reqMdl == null) {
            return null;
        }

        if (reqMdl.getSession() == null) {
            return null;
        }

        String tmp = GroupSession.getResourceManager().getTempPath(reqMdl.getDomain());

        if (StringUtil.isNullZeroString(tmp)) {
            return null;
        }

        return GSTemporaryPathModel.getInstance(reqMdl, pluginId, dirId, subDir).getTempPath();
    }

    /**
     *
     * <br>[機  能] テンポラリディレクトリパスを生成し返す
     * <br>[解  説] バッチ処理のテンポラリディレクトリパス生成用
     * <br>[備  考]
     * @param domain domain
     * @param batchId バッチID
     * @param pluginId プラグインID
     * @param subDir サブディレクトリ
     * @return テンポラリディレクトリパス
     */
    public String getBatchTempPath(String domain, String batchId, String pluginId,
            String... subDir) {

        String tmp = GroupSession.getResourceManager().getTempPath(domain);

        if (StringUtil.isNullZeroString(tmp)) {
            return null;
        }
        return GSTemporaryPathModel.getBatchInstance(domain,
                batchId, pluginId, subDir).getTempPath();

    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリ整合性チェック
     * <br>[解  説] srcパスがその他のパラメータで作成されるパス内にある かつ 実際に存在しなければエラー
     * <br>[備  考]
     * @param src チェック対象パス
     * @param tempPathModel テンポラリディレクトリオブジェクト
     * @return 整合性チェック
     */
    public boolean checkTempPath(String src, GSTemporaryPathModel tempPathModel) {
        File srcFile = new File(src);
        File accessableDir = new File(
                tempPathModel.getTempPath()
                );

        try {
            if (!srcFile.getCanonicalPath().startsWith(accessableDir.getCanonicalPath())) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

        if (!srcFile.exists()) {
            return false;
        }
        return true;

    }
    /**
    *
    * <br>[機  能] テンポラリディレクトリ整合性チェック
    * <br>[解  説] srcパスがその他のパラメータで作成されるパス内にある かつ 実際に存在しなければエラー
    * <br>[備  考]
    * @param src チェック対象パス
    * @param reqMdl リクエストモデル
    * @param pluginId プラグインID
    * @param dirId ディレクトリID
    * @param subDir サブディレクトリ
    * @return 整合性チェック
    */
   public boolean checkTempPath(String src, RequestModel reqMdl, String pluginId, String dirId,
           String... subDir) {
       return checkTempPath(src, GSTemporaryPathModel.getInstance(reqMdl, pluginId, dirId, subDir));
   }

    /**
     *
     * <br>[機  能] テンポラリディレクトリ整合性チェック
     * <br>[解  説] パラメータで作成されるパスがセッションIDディレクトリの下ではない または実際に存在しなければエラー
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     * @return 整合性チェック
     */
    public boolean checkTempPath(RequestModel reqMdl, String pluginId, String dirId,
            String... subDir) {
        String check = getTempPath(reqMdl, pluginId, dirId, subDir);
        return checkTempPath(check, reqMdl, pluginId, "");
    }

    /**
    *
    * <br>[機  能] 指定したテンポラリディレクトリ内を削除する
    * <br>[解  説] GSTEMPDIR/セッションID/プラグインID/ より上の階層が指定された場合削除を行わない
    * <br>[備  考]
    * @param tempPathModel テンポラリディレクトリオブジェクト
    * @return
    */
    public void clearTempPath(GSTemporaryPathModel tempPathModel) {
       File srcFile = new File(tempPathModel.getTempPath());
       for (File child : srcFile.listFiles()) {
           if (child.isFile()) {
               try {
                   IOTools.deleteFile(child);
               } catch (IOToolsException e) {
                   log__.error("ディレクトリ内の削除に失敗 path=" + srcFile.getPath(), e);
               }
           }
       }
    }
    /**
     *
     * <br>[機  能] 指定したテンポラリディレクトリ内を削除する
     * <br>[解  説] GSTEMPDIR/セッションID/プラグインID/ より上の階層が指定された場合削除を行わない
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     * @return
     */
    public void clearTempPath(RequestModel reqMdl, String pluginId, String dirId,
            String... subDir) {

        if (!checkTempPath(reqMdl, pluginId, dirId, subDir)) {
            return;
        }
        clearTempPath(GSTemporaryPathModel.getInstance(reqMdl, pluginId, dirId, subDir));

    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempPathModel テンポラリディレクトリオブジェクト
     */
    public void deleteTempPath(GSTemporaryPathModel tempPathModel) {
        File srcFile = new File(tempPathModel.getTempPath());
        try {
            IOTools.deleteDirRepeat(srcFile);
        } catch (IOToolsException e) {
            log__.error("ディレクトリの削除に失敗 path=" + srcFile.getPath(), e);
        }

    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     */
    public void deleteTempPath(RequestModel reqMdl, String pluginId, String dirId,
            String... subDir) {

        if (!checkTempPath(reqMdl, pluginId, dirId, subDir)) {
            return;
        }
        deleteTempPath(GSTemporaryPathModel.getInstance(reqMdl, pluginId, dirId, subDir));

    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain domain
     * @param batchId バッチID
     * @param pluginId プラグインID
     * @param subDir サブディレクトリ
     */
    public void deleteBatchTempPath(String domain, String batchId, String pluginId,
            String... subDir)  {

        deleteTempPath(GSTemporaryPathModel.getBatchInstance(domain, batchId, pluginId, subDir));

    }
    /**
     *
     * <br>[機  能] 指定されたファイル(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param delFile ファイル名
     * @param tempPathModel テンポラリディレクトリオブジェクト
     * @throws IOToolsException 実行時例外
     */
    public void deleteFile(String[] delFile, GSTemporaryPathModel tempPathModel)
            throws IOToolsException {
        if (delFile == null) {
            log__.debug("削除するファイルのフルパス(オブジェクト) = 戻る1");
            return;
        }
        if (delFile.length < 1) {
            log__.debug("削除するファイルのフルパス(オブジェクト) = 戻る2");
            return;
        }

        String tempDir = tempPathModel.getTempPath();

        for (int i = 0; i < delFile.length; i++) {

            //テンポラリファイルのフルパス(オブジェクト)
            String delPathObj =
                    tempDir + File.separator + delFile[i] + GSConstCommon.ENDSTR_OBJFILE;

            if (!checkTempPath(delPathObj, tempPathModel)) {
                continue;
            }

            delPathObj = IOTools.replaceFileSep(delPathObj);
            log__.debug("削除するファイルのフルパス(オブジェクト) = " + delPathObj);

            //テンポラリファイルのフルパス(本体)
            String delPathFile =
                    tempDir + File.separator  + delFile[i] + GSConstCommon.ENDSTR_SAVEFILE;
            delPathFile = IOTools.replaceFileSep(delPathFile);
            log__.debug("削除するファイルのフルパス(本体) = " + delPathFile);

            //ファイルを削除
            IOTools.deleteFile(delPathObj);
            IOTools.deleteFile(delPathFile);
        }
    }
    /**
     * <br>[機  能] 指定されたファイル(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param delFile ファイル名
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void deleteFile(String[] delFile, RequestModel reqMdl,
            String pluginId, String dirId, String... subDir) throws IOToolsException {

        if (!checkTempPath(reqMdl, pluginId, dirId, subDir)) {
            return;
        }
        deleteFile(delFile, GSTemporaryPathModel.getInstance(reqMdl, pluginId, dirId, subDir));

    }


    /**
    *
    * <br>[機  能] TEMPディレクトリIDを発行する。
    * <br>[解  説] テンポラリディレクトリの保存場所について重複がないことを保証する
    * <br>[備  考]
    * @param reqMdl リクエストモデル
    * @param pluginId プラグインID
    * @return TEMPディレクトリID
    * @throws IOToolsException IOエラー
    */
   public String getTempDirID(RequestModel reqMdl, String pluginId)
           throws IOToolsException {
       String tmpPath = null;
       String uuidStr = null;
       Random generator = new Random();
       int max = 33554432; //32^5
       boolean exists = true;
       for (int i = 0; i < 3; i++) {
           int rnd = generator.nextInt(max);
           uuidStr = Integer.toString(rnd, 32).toUpperCase();
           if (uuidStr.length() < 5) {
               StringBuilder sb = new StringBuilder();

               sb.append("%0");
               sb.append(String.valueOf(5 - uuidStr.length()));
               sb.append("d");
               sb.append("%s");
               uuidStr = String.format(sb.toString(), 0, uuidStr);
           }
           tmpPath = getTempPath(reqMdl, pluginId, uuidStr);
           if (!IOTools.isDirCheck(tmpPath, false)) {
               exists = false;
               break;
           }
       }
       if (exists) {
           throw new IOToolsException("TEMPディレクトリID発行に失敗");
       }
       return uuidStr;

   }


}