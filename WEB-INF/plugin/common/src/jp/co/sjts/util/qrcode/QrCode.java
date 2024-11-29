package jp.co.sjts.util.qrcode;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.d_project.qrcode.ErrorCorrectLevel;
import com.d_project.qrcode.QRCode;

/**
 * <br>[機  能] QRコード生成ライブラリ
 * <br>[解  説] http://www.d-project.com/を使用しています。
 * <br>[備  考]
 * @author JTS
 */
public class QrCode {

    /** 誤り訂正レベル レベルL 7% */
    public static final int ERR_LEVEL_L = ErrorCorrectLevel.L;
    /** 誤り訂正レベル レベルM 15% */
    public static final int ERR_LEVEL_M = ErrorCorrectLevel.M;
    /** 誤り訂正レベル レベルQ 25% */
    public static final int ERR_LEVEL_Q = ErrorCorrectLevel.Q;
    /** 誤り訂正レベル レベルH 30% */
    public static final int ERR_LEVEL_H = ErrorCorrectLevel.H;

    /** 出力形式 jpeg */
    public static final int OUTPUT_JPEG = 1;
    /** 出力形式 png */
    public static final int OUTPUT_PNG = 2;
    /** 出力形式 gif */
    public static final int OUTPUT_GIF = 3;

    /** バージョン (1 ～ 10 0:自動) */
    private int qrVersion__ = 0;
    /** 余白 */
    private int margin__ = 0;
    /** セルサイズ(1 ～ 4) */
    private int cellSize__ = 3;

    /**
     * <br>[機  能] QRコードの出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param text テキスト
     * @param errLevel 誤り訂正レベル
     * @param outputType 出力形式
     * @param filePath 出力先ファイルパス
     * @throws IOException 出力時例外
     */
    public void createQrCode(String text, int errLevel, int outputType, String filePath)
    throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            createQrCode(text, errLevel, outputType, getQrVersion(),
                        getMargin(), getCellSize(), fos);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * <br>[機  能] QRコードの出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param text テキスト
     * @param errLevel 誤り訂正レベル
     * @param outputType 出力形式
     * @param ostream 出力先ストリーム
     * @throws IOException 出力時例外
     */
    public void createQrCode(String text, int errLevel, int outputType, OutputStream ostream)
    throws IOException {
        createQrCode(text, errLevel, outputType, getQrVersion(),
                    getMargin(), getCellSize(), ostream);
    }

    /**
     * <br>[機  能] QRコードの出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param text テキスト
     * @param errLevel 誤り訂正レベル
     * @param outputType 出力形式
     * @param qrVersion バージョン (1 ～ 10 0:自動)
     * @param margin 余白
     * @param cellSize セルサイズ(1 ～ 4)
     * @param ostream 出力先ストリーム
     * @throws IOException 出力時例外
     */
    public void createQrCode(String text, int errLevel, int outputType,
                            int qrVersion, int margin, int cellSize, OutputStream ostream)
    throws IOException {

        QRCode qrcode = __createQRCode(text, errLevel, qrVersion);
        OutputStream out = null;
        try {
            switch (outputType) {
                case OUTPUT_JPEG:
                    BufferedImage jpegIimage = qrcode.createImage(cellSize, margin);
                    out = new BufferedOutputStream(ostream);
                    ImageIO.write(jpegIimage, "jpeg", out);
                    break;

                case OUTPUT_PNG:
                    BufferedImage pngImage = qrcode.createImage(cellSize, margin);
                    out = new BufferedOutputStream(ostream);
                    ImageIO.write(pngImage, "png", out);
                    break;

                case OUTPUT_GIF:
//                    GIFImage gifImage = __createGIFImage(qrcode, cellSize, margin);
//                    out = new BufferedOutputStream(ostream);
//                    gifImage.write(out);
                    BufferedImage gifImage = qrcode.createImage(cellSize, margin);
                    out = new BufferedOutputStream(ostream);
                    ImageIO.write(gifImage, "gif", out);
                    break;
                default:
                    throw new IllegalArgumentException("illegal output type : " + outputType);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] QRコードを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param text データ
     * @param errLevel 誤り訂正レベル
     * @param type 種別
     * @return QRコード
     */
    private static QRCode __createQRCode(String text, int errLevel, int type) {

        QRCode qr = null;
        if (type == 0) {
            qr = QRCode.getMinimumQRCode(text, errLevel);

        } else {
            qr = new QRCode();
            qr.setTypeNumber(type);
            qr.setErrorCorrectLevel(errLevel);
            qr.addData(text);
            qr.make();
        }

        return qr;
    }

    /**
     * <p>qrVersion を取得します。
     * @return qrVersion
     */
    public int getQrVersion() {
        return qrVersion__;
    }

    /**
     * <p>qrVersion をセットします。
     * @param qrVersion qrVersion
     */
    public void setQrVersion(int qrVersion) {
        qrVersion__ = qrVersion;
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
     * <p>cellSize を取得します。
     * @return cellSize
     */
    public int getCellSize() {
        return cellSize__;
    }

    /**
     * <p>cellSize をセットします。
     * @param cellSize cellSize
     */
    public void setCellSize(int cellSize) {
        cellSize__ = cellSize;
    }
}
