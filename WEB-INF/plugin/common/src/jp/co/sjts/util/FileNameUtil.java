package jp.co.sjts.util;

import java.io.UnsupportedEncodingException;

import jp.co.sjts.util.io.IOTools;

/**
 * <br>[機  能] ファイル・ディレクト名で使用可能な文字列を返すクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileNameUtil {

    /** windowsファイルシステム最大文字長 */
    public static final int WIN_MAX_LENGTH = 255;
    /** windows ZIP解凍時最大バイト長 */
    public static final int ZIP_MAX_BYTE = 259;

    /**
     * <br>[機  能] タブ文字が含まれる場合、除去する
     * <br>[解  説]
     * <br>[備  考]
     * @param str 元の文字列
     * @return エスケープ後の文字列
     */
    public static String removeTabString(String str) {

        if (str.indexOf("\t") >= 0) {
            str = str.replaceAll("\t", "");
        }

        return str;
    }

    /**
     * <br>[機  能] タブ文字が含まれる場合、スペースに変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param str 元の文字列
     * @return エスケープ後の文字列
     */
    public static String replaceTabString(String str) {

        if (str.indexOf("\t") >= 0) {
            str = str.replaceAll("\t", " ");
        }

        return str;
    }

    /**
     * <br>[機  能] ピリオド付きの拡張子を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param extension 拡張子
     * @return ピリオド付き拡張子
     */
    private static String __checkExtension(String extension) {

        if (!extension.substring(0, 1).equals(".")) {
            extension = "." + extension;
        }

        return extension;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字は削除する
     * <br>[備  考]
     * @param fileName ファイル名（拡張子を含めない）
     * @return エスケープ後ファイル名
     */
    public static String getTempFileNameTabRemoveNoExt(String fileName) {
        fileName = __getUseTempName(fileName);
        fileName = removeTabString(fileName);
        return fileName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字は削除する
     * <br>[備  考]
     * @param fileName ファイル名（拡張子を含めない）
     * @param extension 拡張子
     * @return エスケープ後ファイル名 + 拡張子
     */
    public static String getTempFileNameTabRemove(String fileName, String extension) {
        fileName = getTempFileNameTabRemoveNoExt(fileName);
        fileName = fileName + __checkExtension(extension);
        return fileName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字はスペースに置き換える
     * <br>[備  考]
     * @param fileName ファイル名（拡張子を含めない）
     * @return エスケープ後ファイル名
     */
    public static String getTempFileNameTabReplaceNoExt(String fileName) {
        fileName = __getUseTempName(fileName);
        fileName = replaceTabString(fileName);
        return fileName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字はスペースに置き換える
     * <br>[備  考]
     * @param fileName ファイル名（拡張子を含めない）
     * @param extension 拡張子
     * @return エスケープ後ファイル名 + 拡張子
     */
    public static String getTempFileNameTabReplace(String fileName, String extension) {

        fileName = getTempFileNameTabReplaceNoExt(fileName);
        fileName = fileName + __checkExtension(extension);
        return fileName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なフォルダ名にエスケープ処理を行う
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字は削除する
     * <br>[備  考]
     * @param dirName ディレクトリ名
     * @return エスケープ後ディレクトリ名
     */
    public static String getTempDirNameTabRemove(String dirName) {
        dirName = __getUseTempName(dirName);
        dirName = removeTabString(dirName);
        return dirName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なフォルダ名にエスケープ処理を行う
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字はスペースに置き換える
     * <br>[備  考]
     * @param dirName ディレクトリ名
     * @return エスケープ後ディレクトリ名
     */
    public static String getTempDirNameTabReplace(String dirName) {
        dirName = __getUseTempName(dirName);
        dirName = replaceTabString(dirName);
        return dirName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う(ZIP圧縮ディレクトリ用)
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字はスペースに置き換える
     *                     ・zip解凍時にエラーが発生しないよう解凍時のファイルパス＋ファイル名が
     *                        259バイト以内になるようにファイル名をカットする。
     *
     * <br>[備  考] filePathは {tempDir}/allFile/user001/test.txt があったとし、allFileをZIPする時
     *                    「/allFile/user001/」を指定すること。
     * @param filePath ZIP圧縮対象ディレクトリからファイルを保存するディレクトリまでのパス
     * @param fileName ファイル名(拡張子無しであること)
     * @param extension 拡張子
     * @return エスケープ後のファイル名 + 拡張子
     * @throws UnsupportedEncodingException URLのエンコード時エラー
     */
    public static String getZipTempFileNameTabRemove(
            String filePath, String fileName, String extension)
                    throws UnsupportedEncodingException {
        fileName = removeTabString(fileName);
        return __getZipTempFileName(filePath, fileName, extension);
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う(ZIP圧縮ディレクトリ用)
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字はスペースに置き換える
     *                     ・zip解凍時にエラーが発生しないよう解凍時のファイルパス＋ファイル名が
     *                        259バイト以内になるようにファイル名をカットする。
     *
     * <br>[備  考] filePathは {tempDir}/allFile/user001/test.txt があったとし、allFileをZIPする時
     *                    「/allFile/user001/」を指定すること。
     * @param filePath ZIP圧縮対象ディレクトリからファイルを保存するディレクトリまでのパス
     * @param fileName ファイル名(拡張子無しであること)
     * @param extension 拡張子
     * @return エスケープ後のファイル名 + 拡張子
     * @throws UnsupportedEncodingException URLのエンコード時エラー
     */
    public static String getZipTempFileNameTabRepalce(
            String filePath, String fileName, String extension)
                    throws UnsupportedEncodingException {
        fileName = replaceTabString(fileName);
        return __getZipTempFileName(filePath, fileName, extension);
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う(ZIP圧縮ディレクトリ用)
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・zip解凍時にエラーが発生しないよう解凍時のファイルパス＋ファイル名が
     *                        259バイト以内になるようにファイル名をカットする。
     *
     * <br>[備  考] filePathは {tempDir}/allFile/user001/test.txt があったとし、allFileをZIPする時
     *                    「/allFile/user001/」を指定すること。
     * @param filePath ZIP圧縮対象ディレクトリからファイルを保存するディレクトリまでのパス
     * @param fileName ファイル名(拡張子無しであること)
     * @param extension 拡張子
     * @return エスケープ後のファイル名 + 拡張子
     * @throws UnsupportedEncodingException URLのエンコード時エラー
     */
    private static String __getZipTempFileName(
            String filePath, String fileName, String extension)
                    throws UnsupportedEncodingException {

        //セパレータチェック
        filePath = IOTools.setEndPathChar(filePath);
        //使用可能ファイルかチェック
        fileName = __getUseTempName(fileName);
        //拡張子チェック
        extension = __checkExtension(extension);

        //使用済バイト数
        int zipPathByteNoExt = filePath.getBytes("Windows-31J").length;
        int maxByte = ZIP_MAX_BYTE;

        //使用可能バイト数 拡張子有り
        int zipPathByteExt =
                maxByte - (zipPathByteNoExt + extension.getBytes("Windows-31J").length);


        int fullPathLength = zipPathByteNoExt
                + fileName.getBytes("Windows-31J").length
                + extension.getBytes("Windows-31J").length;


        String escFileName = null;

        //ファイルパス＋
        if (fullPathLength > ZIP_MAX_BYTE) {

            String formatFileName = "";
            int cntByte = 0;

            while (cntByte < zipPathByteExt) {
                String value = fileName.substring(0, 1);
                cntByte += value.getBytes("Windows-31J").length;

                formatFileName += value;
                fileName = fileName.substring(1);

                if (zipPathByteExt - cntByte == 1) {
                    break;
                }
            }

            escFileName = formatFileName + extension;

        } else {
            escFileName = fileName + extension;
        }

        return escFileName;
    }


    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う(アタッチメント用)
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字は削除する
     *                     ・255文字以内
     * <br>[備  考]
     * @param fileName ファイル名
     * @param extension 拡張子
     * @return エスケープ後ファイル名 + 拡張子
     */
    public static String getAttachmentFileNameTabRemove(String fileName, String extension) {
        fileName = __getUseTempName(fileName);
        fileName = removeTabString(fileName);
        fileName = getUseFileNameLength(fileName, extension);
        fileName = fileName + __checkExtension(extension);
        return fileName;
    }

    /**
     * <br>[機  能] ファイルシステムで使用可能なファイル名にエスケープ処理を行う(アタッチメント用)
     * <br>[解  説] ・使用不可文字は全て削除
     *                     ・Tab文字はスペースに置き換える
     *                     ・255文字以内
     * <br>[備  考]
     * @param fileName ファイル名
     * @param extension 拡張子
     * @return エスケープ後ファイル名 + 拡張子
     */
    public static String getAttachmentFileNameTabReplace(String fileName, String extension) {
        fileName = __getUseTempName(fileName);
        fileName = replaceTabString(fileName);
        fileName = getUseFileNameLength(fileName, extension);
        fileName = fileName + __checkExtension(extension);
        return fileName;
    }


    /**
     * <br>[機  能] ファイル名として使用不可能な文字列を"_"に変換する
     * <br>[解  説] "/", "\", "?", "*", ":", "|", """, "<", ">", "\0" を変換対象とする
     * <br>[備  考]
     * @param fileName ファイル名
     * @return エスケープ後ファイル名
     */
    private static String __getUseTempName(String fileName) {

        fileName = removeSpaceHead(fileName);
        fileName = replaceString(fileName, "/", "_");
        fileName = replaceString(fileName, "\\", "_"); //\
        fileName = replaceString(fileName, "?", "_");
        fileName = replaceString(fileName, "*", "_");
        fileName = replaceString(fileName, ":", "_");
        fileName = replaceString(fileName, "|", "_");
        fileName = replaceString(fileName, "\"", "_"); //"
        fileName = replaceString(fileName, "<", "_");
        fileName = replaceString(fileName, ">", "_");
        fileName = replaceString(fileName, "\0", "_");

        return fileName;
    }

    /**
     * <br>[機  能] ファイル名の先頭がスペース(半角、全角)の場合、除去する
     *                     スペースが連続していた場合も全て除去する
     * <br>[解  説]
     * <br>[備  考]
     * @param value 判定対象の文字列
     * @return true:スペースのみ, false:スペースのみではない
     */
    public static String removeSpaceHead(String value) {

        char[] target = value.toCharArray();
        int cntSpace = 0;
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if ('　' == c || ' ' == c) {
                cntSpace++;
            } else {
                break;
            }
        }
        return value.substring(cntSpace);
    }

    /**
     * <br>[機  能] ファイル名を使用可能な文字数にカットする
     * <br>[解  説] ファイル名＋拡張子 <= 255文字
     * <br>[備  考]
     * @param fileName ファイル名
     * @param extension 拡張子
     * @return カット後の文字列
     */
    public static String getUseFileNameLength(String fileName, String extension) {
        extension = __checkExtension(extension);
        int extLength = extension.length();
        //ファイル名＋拡張子=255文字以内
        fileName = StringUtil.trimRengeString(fileName, WIN_MAX_LENGTH - extLength);
        return fileName;
    }

    /**
     * <br>[機  能] 指定した文字列を指定された文字列に置き換えます。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msg 元の文字列
     * @param oldStr 置換対象の文字列
     * @param newStr おきかえる文字列
     * @return 置換後の文字列
     */
    public static String replaceString(
        String msg,
        String oldStr,
        String newStr) {

        int start = 0;
        int end = 0;
        StringBuilder retSB = new StringBuilder();

        if ((msg == null) || (oldStr == null) || (newStr == null)) {
            return null;
        }

        int oldLength = oldStr.length();

        while (true) {
            end = msg.indexOf(oldStr, start);
            if (end == -1) { //置換対象の文字列がない場合はそのまま返す
                retSB.append(msg.substring(start));
                break;
            } else {
                retSB.append(msg.substring(start, end));
                retSB.append(newStr);
                start = end + oldLength;
            }
        }
        return retSB.toString();
    }
    /**
     * <br>[機  能] テンポラリディレクトリに保存できるファイル名を含むパス返す。
     * <br>[解  説] テンポラリ + ファイル名 + 拡張子 + (一括ダウンロード時のファイル名) <= 255 になるようにする。
     * <br>[備  考]
     * @param tempdir テンポラリディレクトリ
     * @param fileName ファイル名 拡張子含む
     * @return 字数カットされたファイル名を使用したpath
     */
    public static String createFilePath(String tempdir, String fileName) {

        //最大文字数
        int maxLength = FileNameUtil.WIN_MAX_LENGTH;

        String fullPath = IOTools.setEndPathChar(tempdir)
                + FileNameUtil.getTempFileNameTabReplaceNoExt(fileName);
        if (fullPath.length() <= maxLength) {
            return fullPath;
        }

        String ext = "";
        if (fileName.lastIndexOf('.') > 0) {
            ext = fileName.substring(
                    fileName.lastIndexOf('.') - 1,
                    fileName.length());

        }
        //可能文字数
        int okLength = maxLength - tempdir.length() - ext.length();
        if (okLength == 0) {
            //どうしようもない時は何もせず、エラー発生による処理失敗を容認
            return fullPath;
        }

        //カット
        String escName = StringUtil.trimRengeString(fileName, okLength);
        //拡張子を追加
        escName = escName + ext;
        fullPath = tempdir + FileNameUtil.getTempFileNameTabReplaceNoExt(escName);
        return fullPath;
    }
}
