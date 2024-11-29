package jp.co.sjts.util.qrcode;
import java.io.IOException;
import java.io.OutputStream;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.BrowserUtil;
import jp.co.sjts.util.http.ContentType;

import com.d_project.qrcode.ErrorCorrectLevel;

/**
 * <br>[機  能] QRコード生成ライブラリ
 * <br>[解  説] http://www.d-project.com/を使用しています。
 * <br>[備  考] 訂正レベル
 * <br>訂正レベル:Docomo/Au LMQH, SoftBank MQH が使用可能。
 * <br>URLは携帯側で自動認識してくれるので気にせず使用してください。
 * <br>アドレス帳登録はQrAddressModel,QrAddressUtilを使用して各キャリア用の形式に変換してください。
 * <br>
 * <br>使い方
 * <br>QrCodeGenerator qt = new QrCodeGenerator();
 * <br>qt.create("あいうえお", "c:\\qr_docomo.gif", "gif");
 * <br>
 * @author JTS
 */
public class QrCodeGenerator {

    /** エラー訂正レベル */
    private char errorLevel__ = ErrorCorrectLevel.M;
    /** バージョン (1 ～ 10 0:自動) */
    private int qrversion__ = 7;
    /** 余白 */
    private int margin__ = 4;

    /**
     * <br>[機  能] QRコード画像ファイルの生成 (File)
     * <br>[解  説]
     * <br>[備  考] jpgでも生成可能だが読み取り精度は低い
     * @param str QRコード化する文字列
     * @param filePath QRコード画像保存先
     * @param ext 拡張子(形式:gif or png)
     * @throws IOException IO例外
     * @throws Exception QRコード画像ファイルの生成に失敗
     */
    public void create(String str, String filePath, String ext)
            throws IOException, Exception {

        QrCode qrCode = new QrCode();
        qrCode.setQrVersion(getQrversion());
        qrCode.setMargin(getMargin());
        qrCode.createQrCode(str, errorLevel__, __getOutputType(ext), filePath);
    }
    /**
     * <br>[機  能] QRコード画像ファイルの生成 (OutputStream)
     * <br>[解  説]
     * <br>[備  考] jpgでも生成可能だが読み取り精度は低い
     * @param str QRコード化する文字列
     * @param output 出力ストリーム
     * @param ext 拡張子(形式:gif or png)
     * @throws IOException IO例外
     * @throws Exception QRコード画像ファイルの生成に失敗
     */
    public void create(String str, OutputStream output, String ext)
            throws IOException, Exception {

        QrCode qrCode = new QrCode();
        qrCode.setQrVersion(getQrversion());
        qrCode.setMargin(getMargin());
        qrCode.createQrCode(str, errorLevel__, __getOutputType(ext), output);
    }

    /**
     * <br>[機  能] QRコード画像ファイルの生成 (OutputStream)
     * <br>[解  説]
     * <br>[備  考] jpgでも生成可能だが読み取り精度は低い
     * @param str QRコード化する文字列
     * @param req HttpServletRequest
     * @param res レスポンス
     * @param fileName ファイル名(レスポンスヘッダに入れるファイル名)
     * @param ext 拡張子(形式:gif or png)
     * @param encoding エンコード
     * @throws IOException IO例外
     * @throws Exception QRコード画像ファイルの生成に失敗
     */
    public void downloadInline(String str, HttpServletRequest req,
                HttpServletResponse res, String fileName, String ext,
                String encoding) throws IOException, Exception {

        //ブラウザ判定
        boolean ieFlg = BrowserUtil.isIe(req);
        String dispfile = null;

        if (ieFlg) {
            //IE
            dispfile = new String(fileName.getBytes(Encoding.WINDOWS_31J), Encoding.ISO8859_1);
        } else {
            //IE以外
            dispfile = MimeUtility.encodeWord(fileName, Encoding.UTF_8, "B");
        }

        // 拡張子からcontentTypeを獲得 (元のファイル名で判定)
        String contentType = ContentType.getContentType(fileName);
        // contentTypeを出力
        if (ieFlg) {
            //ブラウザが解釈できる文字コードの文字列をセット(WINDOWS_31JではなくSHIFT_JIS)
            res.setContentType(contentType + "; charset=" + Encoding.SHIFT_JIS);
        } else {
            //
            String charset = Encoding.getHtmlCharset(encoding);
            res.setContentType(contentType + "; charset=" + charset);
        }

        //inline表示
        res.setHeader(
            "Content-disposition",
            "inline; filename=\"" + dispfile + "\"");

        create(str, res.getOutputStream(), ext);
    }

    /**
     * <br>[機  能] 指定した拡張子に該当する出力形式
     * <br>[解  説]
     * <br>[備  考]
     * @param ext 拡張子
     * @return 出力形式
     */
    private int __getOutputType(String ext) {
        int outputType = -1;
        String imgType = NullDefault.getString(ext, "").toUpperCase();
        if (imgType.equals("JPG") || imgType.equals("JPEG")) {
            outputType = QrCode.OUTPUT_JPEG;
        } else if (imgType.equals("PNG")) {
            outputType = QrCode.OUTPUT_PNG;
        } else if (imgType.equals("GIF")) {
            outputType = QrCode.OUTPUT_GIF;
        }

        return outputType;
    }
    /**
     * <p>errorLevel を取得します。
     * @return errorLevel
     */
    public char getErrorLevel() {
        return errorLevel__;
    }
    /**
     * <p>errorLevel をセットします。
     * @param errorLevel errorLevel
     */
    public void setErrorLevel(char errorLevel) {
        errorLevel__ = errorLevel;
    }
    /**
     * <p>margin を取得します。
     * @return margin
     */
    public int getMargin() {
        return margin__;
    }
    /**
     * <p>margin をセットします。
     * @param margin margin
     */
    public void setMargin(int margin) {
        margin__ = margin;
    }
    /**
     * <p>qrversion を取得します。
     * @return qrversion
     */
    public int getQrversion() {
        return qrversion__;
    }
    /**
     * <p>qrversion をセットします。
     * @param qrversion qrversion
     */
    public void setQrversion(int qrversion) {
        qrversion__ = qrversion;
    }
}
