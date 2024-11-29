package jp.co.sjts.util.struts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.upload.MultipartRequestWrapper;

import jp.co.sjts.util.Encoding;

/**
 * <br>[機  能] 日本語に対応したMultipartリクエストを処理するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MultipartRequestHandlerJa implements MultipartRequestHandler {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MultipartRequestHandlerJa.class);

    /** デフォルトのMAXサイズ 約250MB */
    public static final long DEFAULT_SIZE_MAX = 250 * 1024 * 1024;

    /** デフォルトthresholdサイズ */
    public static final int DEFAULT_SIZE_THRESHOLD = 256 * 1024;

    /** リクエストパラメータ */
    private Hashtable<String , Object> elementsAll__;

    /** ファイルリクエストパラメータ */
    private Hashtable < String, Object > elementsFile__;

    /** テキストリクエストパラメータ */
    private Hashtable < String, Object > elementsText__;

    /** ActionMapping */
    private ActionMapping mapping__;

    /** ActionServlet */
    private ActionServlet servlet__;

    /**
     * ActionServletを返します。
     * @return ActionServlet
     */
    public ActionServlet getServlet() {
        return servlet__;
    }

    /**
     * ActionServletをセットします。
     * @param acservlet ActionServlet
     */
    public void setServlet(ActionServlet acservlet) {
        servlet__ = acservlet;
    }

    /**
     * ActionMappingを返します。
     * @return ActionMapping
     */
    public ActionMapping getMapping() {
        return mapping__;
    }


    /**
     * ActionMappingをセットします。
     * @param map The associated action mapping.
     */
    public void setMapping(ActionMapping map) {
        this.mapping__ = map;
    }


    /**
     * <br>[機 能] リクエストの解析を行います。
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param request multipartリクエスト
     * @throws ServletException DiskFileUploadのparseRequestが失敗した場合にスロー
     */
    @SuppressWarnings({"deprecation", "all" })
    public synchronized void handleRequest(HttpServletRequest request)
            throws ServletException {

        log__.debug("-- handleRequest Start --");
        ModuleConfig ac = (ModuleConfig) request.getAttribute(
                Globals.MODULE_KEY);

        org.apache.commons.fileupload.DiskFileUpload upload
            = new org.apache.commons.fileupload.DiskFileUpload();
        //FileUploadExceptionを発生させるMAXサイズをセット
        upload.setSizeMax((int) getSizeMax(ac));
        //メモリ内で処理するサイズをセット
        upload.setSizeThreshold((int) getSizeThreshold(ac));
        //テンポラリの保存先のパスをセット
        upload.setRepositoryPath(getRepositoryPath(ac));

        /** UTF-8固定になっている */
//        String encoding = request.getCharacterEncoding(); //これで問題ないか後日確認
        String encoding = Encoding.UTF_8;
        upload.setHeaderEncoding(encoding);

        //リクエスト格納インスタンスの生成
        elementsText__ = new Hashtable <String, Object>();
        elementsFile__ = new Hashtable <String, Object>();
        elementsAll__ = new Hashtable <String, Object>();

        List items = null;
        try {
            log__.debug("リクエスト解析前");
            items = upload.parseRequest(request);
            log__.debug("リクエスト解析後");

        } catch (org.apache.commons.fileupload.DiskFileUpload.SizeLimitExceededException e) {
            //MAXサイズを超えたリクエストの場合の処理
            request.setAttribute(
                    MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED,
                    Boolean.TRUE);
            log__.error("ファイルのアップロード中にエラーが発生しました。(サイズ)", e);
            return;
        } catch (FileUploadException e) {
            log__.error("ファイルのアップロード中にエラーが発生しました。", e);
//            throw new ServletException(e);
            return;
        }

        //解析後、インスタンスに解析結果を格納する。
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();

            if (item.isFormField()) {
                addTextParameter(request, item);
            } else {
                addFileParameter(item);
            }
        }
        upload = null;
        log__.debug("-- handleRequest END --");
    }


    /**
     * テキストリクエストパラメータを返します。
     * @return テキストリクエストパラメータ
     */
    public Hashtable< String, Object > getTextElements() {
        return this.elementsText__;
    }

    /**
     * ファイルリクエストパラメータを返します。
     * @return ファイルリクエストパラメータ
     */
    public Hashtable < String, Object > getFileElements() {
        return this.elementsFile__;
    }

    /**
     * 全てのリクエストパラメータを返します。
     * @return 全てのリクエストパラメータ
     */
    public Hashtable<String , Object> getAllElements() {
        return this.elementsAll__;
    }


    /**
     * <br>[機 能] ファイルリクエストパラメータに存在するファイルオブジェクトを削除します。
     * <br>[解 説]
     * <br>[備 考]
     */
    @SuppressWarnings("all")
    public void rollback() {
        Iterator iter = elementsFile__.values().iterator();

        while (iter.hasNext()) {
            FormFile formFile = (FormFile) iter.next();
            formFile.destroy();
        }
    }


    /**
     * <br>[機 能] 終処理を行います。
     * <br>[解 説]
     * <br>[備 考] rollback()を呼び出すのみ
     */
    public void finish() {
        rollback();
    }


    /**
     * <br>[機 能] MAXサイズを取得します。
     * <br>[解 説] struts_configに指定がない場合はデフォルト値を取得します。
     * <br>[備 考] デフォルト値はメンバ定数のDEFAULT_SIZE_MAXです。
     * @param mc struts_configから読み取った値
     * @return MAXサイズ(bytes)
     */
    protected long getSizeMax(ModuleConfig mc) {
        return convertSizeToBytes(
                mc.getControllerConfig().getMaxFileSize(),
                DEFAULT_SIZE_MAX);
    }


    /**
     * <br>[機 能] メモリ内で処理するサイズをを取得します。
     * <br>[解 説] Struts_Configに指定がない場合はデフォルト値を取得します。
     * <br>[備 考] デフォルト値はメンバ定数のDEFAULT_SIZE_THRESHOLDです。
     * @param mc struts_configから読み取った値
     * @return サイズ(bytes)
     */
    protected long getSizeThreshold(ModuleConfig mc) {
        return convertSizeToBytes(
                mc.getControllerConfig().getMemFileSize(),
                DEFAULT_SIZE_THRESHOLD);
    }

    /**
     * <br>[機 能] KB,MB,GBをB(バイト)に変換します。
     * <br>[解 説] 変換できない場合第二引数のデフォルトサイズを返します。
     * <br>[備 考]
     * @param sizeString 変換対象の文字列
     * @param defaultSize 変換に失敗した場合のデフォルトサイズ
     * @return 変換後のサイズ(bytes)
     */
    protected long convertSizeToBytes(String sizeString, long defaultSize) {
        int multiplier = 1;

        if (sizeString.endsWith("K")) {
            multiplier = 1024;
        } else if (sizeString.endsWith("M")) {
            multiplier = 1024 * 1024;
        } else if (sizeString.endsWith("G")) {
            multiplier = 1024 * 1024 * 1024;
        }
        if (multiplier != 1) {
            sizeString = sizeString.substring(0, sizeString.length() - 1);
        }

        long size = 0;
        try {
            size = Long.parseLong(sizeString);
        } catch (NumberFormatException nfe) {
            log__.warn("指定したファイルサイズは不正なフォーマットです。 ('" + sizeString
             + "') そのためデフォルト値が使用されます。 デフォルト値(" + defaultSize + " B)");
            size = defaultSize;
            multiplier = 1;
        }
        return (size * multiplier);
    }


    /**
     * <br>[機 能] テンポラリのディレクトリのパスを取得します。
     * <br>[解 説] <br>1) struts_configにテンポラリのパスを指定した場合、そのパスを返します。ない場合2へ。
     *             <br>2) ServletContextからテンポラリディレクトリを取得します(javax.servlet.context.tempdir)。ない場合3へ。
     *             <br>3) OSの環境変数よりテンポラリディレクトリを取得します(java.io.tmpdir)。
     * <br>[備 考]
     * @param mc struts_configから読み取った値
     * @return テンポラリファイルのパス
     */
    protected String getRepositoryPath(ModuleConfig mc) {

        String tempDir = mc.getControllerConfig().getTempDir();

        if (tempDir == null || tempDir.length() == 0) {
            if (servlet__ != null) {
                ServletContext context = servlet__.getServletContext();
                File tempDirFile = (File) context.getAttribute(
                        "javax.servlet.context.tempdir");
                tempDir = tempDirFile.getAbsolutePath();
            }

            if (tempDir == null || tempDir.length() == 0) {
                tempDir = System.getProperty("java.io.tmpdir");
            }
        }

        if (log__.isTraceEnabled()) {
            log__.trace("File upload temp dir: " + tempDir);
        }

        return tempDir;
    }


    /**
     * <br>[機 能] テキストリクエストパラメータを追加する。
     * <br>[解 説]
     * <br>[備 考]
     * @param request リクエスト
     * @param item 追加するアイテム
     */
    protected void addTextParameter(HttpServletRequest request, FileItem item) {
        String name = item.getFieldName();
        String value = null;

        try {
            value = item.getString(request.getCharacterEncoding());
        } catch (Exception e) {
            value = item.getString();
        }

        if (request instanceof MultipartRequestWrapper) {
            MultipartRequestWrapper wrapper = (MultipartRequestWrapper) request;
            wrapper.setParameter(name, value);
        }

        String[] oldArray = (String[]) elementsText__.get(name);
        String[] newArray;

        if (oldArray != null) {
            newArray = new String[oldArray.length + 1];
            System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
            newArray[oldArray.length] = value;
        } else {
            newArray = new String[] {value};
        }

        elementsText__.put(name, newArray);
        elementsAll__.put(name, newArray);
    }


    /**
     * <br>[機 能] ファイルリクエストパラメータを追加する
     * <br>[解 説]
     * <br>[備 考]
     * @param item 追加するアイテム
     */
    protected void addFileParameter(FileItem item) {
        FormFile formFile = new CommonsFormFile(item);

        String name = item.getFieldName();


        if (!elementsFile__.containsKey(name)) {
            elementsFile__.put(item.getFieldName(), formFile);
            elementsAll__.put(item.getFieldName(), formFile);
            return;
        }
        Object tmp = elementsFile__.get(name);
        if (tmp == null) {
            elementsFile__.put(item.getFieldName(), formFile);
            elementsAll__.put(item.getFieldName(), formFile);
            return;
        }
        if (tmp.getClass().isArray()) {
            FormFile[] oldArray = (FormFile[]) elementsFile__.get(name);
            FormFile[] newArray;
            newArray = new FormFile[oldArray.length + 1];
            System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
            newArray[oldArray.length] = formFile;
            elementsFile__.put(name, newArray);
            elementsAll__.put(name, newArray);
            return;
        }
        FormFile[] newArray = new FormFile[2];
        newArray[0] = (FormFile) tmp;
        newArray[1] = formFile;
        elementsFile__.put(name, newArray);
        elementsAll__.put(name, newArray);
        return;



    }



    /**
     * <br>[機 能] インターフェイスFormFileの実装クラス
     * <br>[解 説] 内部的にはCommons FileUploadのFileItemをラップしただけです。
     * <br>[備 考]
     */
    static class CommonsFormFile implements FormFile {

        /** ファイルアイテム　Commons FileUploadのFileItem */
        private FileItem fileItem__;

        /**
         * <br>[機 能] コンストラクタ
         * <br>[解 説]
         * <br>[備 考]
         * @param item Commons file item
         */
        public CommonsFormFile(FileItem item) {
            this.fileItem__ = item;
        }


        /**
         * <br>[機 能] コンテントタイプを返します。
         * <br>[解 説]
         * <br>[備 考]
         * @return コンテントタイプ
         */
        public String getContentType() {
            return fileItem__.getContentType();
        }


        /**
         * <br>[機 能] コンテントタイプをセットします。(使用しないでください。)
         * <br>[解 説] 使用するとUnsupportedOperationExceptionがスローされます。
         * <br>[備 考]
         * @param contentType コンテントタイプ
         */
        public void setContentType(String contentType) {
            throw new UnsupportedOperationException(
                    "The setContentType() method is not supported.");
        }


        /**
         * <br>[機 能] ファイルサイズを返します。
         * <br>[解 説]
         * <br>[備 考]
         * @return ファイルサイズ(bytes)
         */
        public int getFileSize() {
            return (int) fileItem__.getSize();
        }


        /**
         * <br>[機 能] ファイルサイズをセットします。(使用しないでください。)
         * <br>[解 説] 使用するとUnsupportedOperationExceptionがスローされます。
         * <br>[備 考]
         * @param filesize ファイルサイズ
         */
        public void setFileSize(int filesize) {
            throw new UnsupportedOperationException(
                    "The setFileSize() method is not supported.");
        }


        /**
         * <br>[機 能] ファイル名を取得します。
         * <br>[解 説]
         * <br>[備 考]
         * @return ファイル名
         */
        public String getFileName() {
            return getBaseFileName(fileItem__.getName());
        }


        /**
         * <br>[機 能] ファイル名をセットします。(使用しないでください。)
         * <br>[解 説] 使用するとUnsupportedOperationExceptionがスローされます。
         * <br>[備 考]
         * @param fileName セットするファイル名
         */
        public void setFileName(String fileName) {
            throw new UnsupportedOperationException(
                    "The setFileName() method is not supported.");
        }

        /**
         * <br>[機 能] ファイルデータ(byte[])を返します。
         * <br>[解 説]
         * <br>[備 考]
         * @return ファイルデータ(byte[])
         * @exception FileNotFoundException ファイルが見つからない場合にスロー
         * @exception IOException IOエラー時にスロー
         */
        public byte[] getFileData() throws FileNotFoundException, IOException {
            return fileItem__.get();
        }


        /**
         * <br>[機 能] ファイルよりInputStreamを返します。
         * <br>[解 説]
         * <br>[備 考]
         * @exception FileNotFoundException ファイルが見つからない場合にスロー
         * @exception IOException IOエラー発生時にスロー
         * @return InputStream
         */
        public InputStream getInputStream() throws FileNotFoundException, IOException {
            return fileItem__.getInputStream();
        }


        /**
         * <br>[機 能] 終処理
         * <br>[解 説] 内部的にはファイルを削除します。
         * <br>[備 考]
         */
        public void destroy() {
            fileItem__.delete();
        }


        /**
         * <br>[機 能] ファイルパスより、ファイル名を返します。
         * <br>[解 説]
         * <br>[備 考]
         * @param filePath ファイルパス
         * @return ファイル名
         */
        protected String getBaseFileName(String filePath) {

            // First, ask the JDK for the base file name.
            String fileName = new File(filePath).getName();

            // Now check for a Windows file name parsed incorrectly.
            int colonIndex = fileName.indexOf(":");
            if (colonIndex == -1) {
                // Check for a Windows SMB file path.
                colonIndex = fileName.indexOf("\\\\");
            }
            int backslashIndex = fileName.lastIndexOf("\\");

            if (colonIndex > -1 && backslashIndex > -1) {
                // Consider this filename to be a full Windows path, and parse it
                // accordingly to retrieve just the base file name.
                fileName = fileName.substring(backslashIndex + 1);
            }

            return fileName;
        }

        /**
         * <br>[機 能] 本オブジェクトの文字列形式(ここではファイル名)を返します。
         * <br>[解 説]
         * <br>[備 考]
         * @return 本オブジェクトの文字列形式(ここではファイル名)
         */
        public String toString() {
            return getFileName();
        }
    }
}
