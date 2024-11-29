package jp.groupsession.v2.cmn.cmn110;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;

/**
 * <br>[機  能] ファイル添付ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn110Biz {

    /** 拡張子(BMP) */
    private static final String EXTENSION_BMP = ".BMP";
    /** 拡張子(JPG) */
    private static final String EXTENSION_JPG = ".JPG";
    /** 拡張子(JPEG) */
    private static final String EXTENSION_JPEG = ".JPEG";
    /** 拡張子(GIF) */
    private static final String EXTENSION_GIF = ".GIF";
    /** 拡張子(PNG) */
    private static final String EXTENSION_PNG = ".PNG";

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cmn110Model パラメータ情報
     * @param con Connection
     * @param tempDir テンポラリディレクトリ
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル操作時例外
     */
    public void setInitData(Cmn110ParamModel cmn110Model, Connection con, String tempDir)
        throws SQLException, IOToolsException {

        //添付ファイル最大容量取得
        CmnFileConfDao cfcDao = new CmnFileConfDao(con);
        if ((Objects.equals(cmn110Model.getCmn110Mode(),
            String.valueOf(GSConstCommon.CMN110MODE_FILEKANRI))
                || Objects.equals(cmn110Model.getCmn110Mode(),
            String.valueOf(GSConstCommon.CMN110MODE_FILEKANRI_TANITU)))
                && Objects.equals(cmn110Model.getTempDirId(), "fil080")) {

            int size = cfcDao.getFileKanriFileSize();
            cmn110Model.setStrMaxSize(String.valueOf(size));
        } else if (Objects.equals(
                    cmn110Model.getCmn110Mode(),
                String.valueOf(GSConstCommon.CMN110MODE_TANITU_USR031))) {
            CmnFileConfModel cfcMdl = cfcDao.select();
            cmn110Model.setStrMaxSize(cfcMdl.getFicPhotoSize());
        } else {
            CmnFileConfModel cfcMdl = cfcDao.select();
            cmn110Model.setStrMaxSize(String.valueOf(cfcMdl.getFicMaxSize()));
        }

        ArrayList<String> updList = new ArrayList<String>();

        String[] updStr;
        if (updList.isEmpty()) {
            updStr = new String[0];
        } else {
            updStr = updList.toArray(new String[updList.size()]);
        }
        cmn110Model.setFileList(updStr);
    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリパスの存在チェック
     * <br>[解  説] 不正なディレクトリへのファイル登録を防ぐ
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramModel パラメータ情報
     * @return true:存在 false:不在
     */
    public static boolean isTempDirOK(RequestModel reqMdl,
            Cmn110ParamModel paramModel) {
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        String[] path = __createSubPath(paramModel);

        String[] subdir = null;
        if (path.length > 1) {
            subdir = (String[]) ArrayUtils.subarray(path, 1, path.length);
        }
        return pathUtil.checkTempPath(reqMdl,
                paramModel.getCmn110pluginId(),
                path[0], subdir);
    }
    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param cmn110Model パラメータ情報
     * @return テンポラリディレクトリパス
     */
    public static String getTempDir(RequestModel reqMdl, Cmn110ParamModel cmn110Model) {
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();

        String[] path = __createSubPath(cmn110Model);

        String[] subdir = null;
        if (path.length > 1) {
            subdir = (String[]) ArrayUtils.subarray(path, 1, path.length);
        }

        String tempDir = pathUtil.getTempPath(reqMdl,
                cmn110Model.getCmn110pluginId(),
                path[0], subdir);

        return tempDir;
    }
    /**
     * <br>[機  能] テンポラリディレクトリパスを空にする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param cmn110Model パラメータ情報
     */
    public static void clearTemp(RequestModel reqMdl, Cmn110ParamModel cmn110Model) {
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();

        String[] path = __createSubPath(cmn110Model);

        String[] subdir = null;
        if (path.length > 1) {
            subdir = (String[]) ArrayUtils.subarray(path, 1, path.length);
        }

        pathUtil.clearTempPath(reqMdl,
                cmn110Model.getCmn110pluginId(),
                path[0], subdir);

    }

    /**
     * <br>[機  能] テンポラリディレクトリのディレクトリID以下のパス配列を生成
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn110Model cmn110Model
     * @return ディレクトリID以下のパス生成
     */
    private static String[] __createSubPath(Cmn110ParamModel cmn110Model) {
        StringBuilder dilBld = new StringBuilder();
        if (!StringUtil.isNullZeroString(cmn110Model.getTempDirId())) {
            dilBld.append(cmn110Model.getTempDirId());
            dilBld.append(File.separator);
        }
        if (!StringUtil.isNullZeroString(cmn110Model.getCmn110TempDirPlus())) {
            dilBld.append(cmn110Model.getCmn110TempDirPlus());
            dilBld.append(File.separator);
        }

        String[] path = StringUtils.split(
                IOTools.replaceFileSep(dilBld.toString()),
                File.separator);
        return path;
    }
    /**
     * <br>[機  能] 添付ファイル(本体)のパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @return 添付ファイル(本体)のパス
     */
    public static File getSaveFilePath(String tempDir, String dateStr, int fileNum) {

        return __getFilePath(tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_SAVEFILE);
    }

    /**
     * <br>[機  能] オブジェクトファイルのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @return 添付ファイル(本体)のパス
     */
    public static File getObjFilePath(String tempDir, String dateStr, int fileNum) {

        return __getFilePath(tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_OBJFILE);
    }

    /**
     * <br>[機  能] 添付ファイルの連番を取得する
     * <br>[解  説] テンポラリディレクトリパス以下に存在する
     * <br>         名前が「日付文字列 + xxx + "file"」のファイルの数を返す
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列
     * @return テンポラリファイルの連番
     */
    public static int getFileNumber(String tempDir, String dateStr) {
        Cmn110FilenameFilter filter =
            new Cmn110FilenameFilter(dateStr, GSConstCommon.ENDSTR_SAVEFILE);

        File tempDirPath = new File(tempDir);
        File[] fileList = tempDirPath.listFiles(filter);
        if (fileList == null) {
            return 0;
        }

        int fileNum = 0;
        int tailLen = GSConstCommon.ENDSTR_SAVEFILE.length();
        for (File fileName : fileList) {
            String num = fileName.getName();
            num = num.substring(dateStr.length(), num.length() - tailLen);
            if (fileNum < Integer.parseInt(num)) {
                fileNum = Integer.parseInt(num);
            }
        }

        return fileNum;
    }

    /**
     * <br>[機  能] ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリファイル
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @param endStr 接尾文字列("file" or "obj")
     * @return ファイルパス
     */
    private static File __getFilePath(String tempDir, String dateStr, int fileNum, String endStr) {

        StringBuilder filePath = new StringBuilder("");
        filePath.append(tempDir);
        filePath.append(dateStr);
        filePath.append(StringUtil.toDecFormat(fileNum, "000"));
        filePath.append(endStr);

        return new File(filePath.toString());
    }
    /**
     * <br>[機  能] ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリファイル
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @param endStr 接尾文字列("file" or "obj")
     * @return ファイルパス
     */
    public static String getFilePath(String tempDir, String dateStr, int fileNum, String endStr) {

        StringBuilder filePath = new StringBuilder("");
        filePath.append(tempDir);
        filePath.append(dateStr);
        filePath.append(StringUtil.toDecFormat(fileNum, "000"));
        filePath.append(endStr);

        return filePath.toString();
    }
    /**
     * <br>[機  能] ファイル名から拡張子を取得し、
     *              特定の種類のファイルであるかを確認する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fileName ファイル名
     * @return ret true:拡張子が正常 false:拡張子が不正
     */
    public static boolean isExtensionOk(String fileName) {

        boolean ret = false;

        //ファイル名がNULLまたは空の場合は処理しない
        if (fileName != null && !fileName.equals("")) {
            String strExt = StringUtil.getExtension(fileName);

            //拡張子が取得できた場合
            if (strExt != null) {

                //拡張子がBMPか
                if (strExt.toUpperCase().equals(EXTENSION_BMP)) {
                    return true;
                }
                //拡張子がJPGか
                if (strExt.toUpperCase().equals(EXTENSION_JPG)) {
                    return true;
                }
                //拡張子がJPEGか
                if (strExt.toUpperCase().equals(EXTENSION_JPEG)) {
                    return true;
                }
                //拡張子がGIFか
                if (strExt.toUpperCase().equals(EXTENSION_GIF)) {
                    return true;
                }
                //拡張子がPNGか
                if (strExt.toUpperCase().equals(EXTENSION_PNG)) {
                    return true;
                }
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] ファイル名から拡張子を取得し、
     *              特定の種類のファイルであるかを確認する
     * <br>[解  説] 画像ファイルの拡張子であればtrueを返します。BMPを除く。
     * <br>[備  考] JPG, JPEG, GIF, PNGの場合にtrueを返します。
     *
     * @param fileName ファイル名
     * @return ret true:拡張子が正常 false:拡張子が不正
     */
    public static boolean isExtensionForPhotoOk(String fileName) {

        boolean ret = false;

        //ファイル名がNULLまたは空の場合は処理しない
        if (fileName != null && !fileName.equals("")) {
            String strExt = StringUtil.getExtension(fileName);

            //拡張子が取得できた場合
            if (strExt != null) {

                //拡張子がJPGか
                if (strExt.toUpperCase().equals(EXTENSION_JPG)) {
                    return true;
                }
                //拡張子がJPEGか
                if (strExt.toUpperCase().equals(EXTENSION_JPEG)) {
                    return true;
                }
                //拡張子がGIFか
                if (strExt.toUpperCase().equals(EXTENSION_GIF)) {
                    return true;
                }
                //拡張子がPNGか
                if (strExt.toUpperCase().equals(EXTENSION_PNG)) {
                    return true;
                }
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考] '\b','\t','\f','\r' についてはサーバ側のJavascriptにてエスケープを行うので
     * <br>         エスケープの対象とはしない。
     * @param text 文字列
     * @return 文字列
     * @throws UnsupportedEncodingException 文字コードの指定が不正
     */
    public static String escapeText(String text) throws UnsupportedEncodingException {
        String encodeText = NullDefault.getString(text, "");
        encodeText = StringUtilHtml.transToHTml(encodeText);
        encodeText = StringUtilHtml.replaceString(encodeText, "\\", "\\\\");
        encodeText = StringUtilHtml.replaceString(encodeText, "\r\n", "\n");
        encodeText = StringUtilHtml.replaceString(encodeText, "\"", "\\\"");
        encodeText = StringUtilHtml.replaceString(encodeText, "\n", "\\n");

        return encodeText;
    }


}