package jp.co.sjts.util.pdf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jp.co.sjts.util.io.IOTools;

/**
 * <br>[機  能] PDF出力に関するユーティリティークラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PdfUtil {

    /**
     * <br>[機  能]コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public PdfUtil() {
    }

    /**
     * <br>[機  能]コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param verticalFlg 画像分割時Vertical設定フラグ
     */
    public PdfUtil(boolean verticalFlg) {
        verticalFlg__ = verticalFlg;
    }

    /** 画像分割時Vertical設定フラグ */
    private boolean verticalFlg__ = false;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PdfUtil.class);

    /** フォントファイル */
    private static final String FONTFILE = "GenShinGothic-Monospace-Normal.ttf";

    /** フォントカラー 青 */
    public static final Color FONT_COLOR_BLUE = Color.BLUE;
    /** フォントカラー 赤 */
    public static final Color FONT_COLOR_RED = Color.RED;
    /** フォントカラー 緑 */
    public static final Color FONT_COLOR_GREEN = new Color(0, 153, 0);
    /** フォントカラー オレンジ */
    public static final Color FONT_COLOR_ORANGE = new Color(191, 114, 0);
    /** フォントカラー 黒 */
    public static final Color FONT_COLOR_BLACK = new Color(51, 51, 51);
    /** フォントカラー 紺 */
    public static final Color FONT_COLOR_NAVY = new Color(0, 0, 128);
    /** フォントカラー 茶 */
    public static final Color FONT_COLOR_MAROON = new Color(128, 0, 0);
    /** フォントカラー シアン */
    public static final Color FONT_COLOR_CYAN = new Color(0, 128, 128);
    /** フォントカラー グレー */
    public static final Color FONT_COLOR_GRAY = new Color(128, 128, 128);
    /** フォントカラー 水色 */
    public static final Color FONT_COLOR_AQUA = new Color(0, 141, 203);

    /** 背景色 薄黄色(当日) */
    public static final Color BG_COLOR_LIGHTYELLOW = new Color(255, 255, 204);
    /** 背景色 薄青色(土曜) */
    public static final Color BG_COLOR_LIGHTBLUE = new Color(229, 237, 254);
    /** 背景色 薄赤色(日曜) */
    public static final Color BG_COLOR_LIGHTRED = new Color(255, 193, 193);
    /** 背景色 薄緑色(期間) */
    public static final Color BG_COLOR_LIGHTGREEN = new Color(212, 254, 223);
    /** 背景色 薄灰色(日にち＆曜日) */
    public static final Color BG_COLOR_LIGHTGRAY = new Color(238, 238, 238);
    /** 背景色 濃青色(ヘッダ) */
    public static final Color BG_COLOR_DARKBLUE = new Color(125, 145, 189);

    /** PDF出力ページ上限 */
    private static final int MAX_PAGE__ = 1000;

    /**
     * <br>[機  能] アプリケーションのルートパスからフォントファイルのパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return フォント
     */
    public static String getFontFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceFileSep(appRootPath
                + "WEB-INF/font/" + FONTFILE);
        return ret;
    }

    /**
     * <br>[機  能] ベースフォントを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return BaseFont ベースフォント
     * @throws Exception 実行例外
     */
    public static BaseFont getBaseFont(String fontPath) throws Exception {
        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);
        return baseFont;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック16pt(太字)
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont16b(String fontPath) throws Exception {
        //ゴシック16pt(太字)
        Font font_16b = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 16, Font.BOLD);
        return font_16b;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック12pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont12b(String fontPath) throws Exception {
        Font font_12b = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 12, Font.BOLD);
        return font_12b;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック10pt(太字)
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont10b(String fontPath) throws Exception {
        //ゴシック10pt(太字)
        Font font_10b = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 10, Font.BOLD);
        return font_10b;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック9pt(太字)
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont9b(String fontPath) throws Exception {
        Font font_9b = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 9, Font.BOLD);
        return font_9b;
    }


    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック8pt(太字)
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont8b(String fontPath) throws Exception {
        Font font_9b = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 8, Font.BOLD);
        return font_9b;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック17pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont17(String fontPath) throws Exception {
        Font font_17 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 17);
        return font_17;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック12pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont12(String fontPath) throws Exception {
        Font font_12 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 12);
        return font_12;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック10pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont10(String fontPath) throws Exception {
        Font font_10 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 10);
        return font_10;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック9pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont9(String fontPath) throws Exception {
        Font font_9 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 9);
        return font_9;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック8pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont8(String fontPath) throws Exception {
        Font font_8 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 8);
        return font_8;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック7pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont7(String fontPath) throws Exception {
        Font font_7 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 7);
        return font_7;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] ゴシック6pt
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFont6(String fontPath) throws Exception {
        Font font_6 = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 6);
        return font_6;
    }

    /**
     * <br>[機  能] フォントを取得する。
     * <br>[解  説] 空白セル用フォント(非表示)
     * <br>[備  考]
     * @param fontPath フォントファイルのパス
     * @return フォント
     * @throws Exception 実行例外
     */
    public static Font getFontEmpty(String fontPath) throws Exception {
        Font fontEmpty = new Font(
                BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED), 6);
        fontEmpty.setColor(new Color(255, 255, 255));
        return fontEmpty;
    }

    /**
     * <br>[機  能] テーブルを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cellCnt セルの数
     * @param widthPercentage テーブルの幅パーセンテージ(通常100)
     * @param totalWidth テーブルの幅
     * @param widths セルの幅
     * @param align 表示位置
     * @return table テーブル
     * @throws Exception 実行例外
     */
    public static PdfPTable createTable(
            int cellCnt,
            int widthPercentage,
            float totalWidth,
            float[] widths,
            int align
            ) throws Exception {

        PdfPTable table = new PdfPTable(cellCnt);
        table.setWidthPercentage(widthPercentage);
        table.setTotalWidth(totalWidth);
        table.setWidths(widths);
        //テーブルのデフォルトの表示位置（横）を設定します。
        table.setHorizontalAlignment(align);
        return table;
    }

    /**
     * <br>[機  能] テーブルを作成する。(幅指定無し)
     * <br>[解  説]
     * <br>[備  考]
     * @param cellCnt セルの数
     * @param widthPercentage テーブルの幅パーセンテージ(通常100)
     * @param totalWidth テーブルの幅
     * @param align 表示位置
     * @return table テーブル
     * @throws Exception 実行例外
     */
    public static PdfPTable createTable(
            int cellCnt,
            int widthPercentage,
            float totalWidth,
            int align
            ) throws Exception {

        PdfPTable table = new PdfPTable(cellCnt);
        table.setWidthPercentage(widthPercentage);
        table.setTotalWidth(totalWidth);
        //テーブルのデフォルトの表示位置（横）を設定します。
        table.setHorizontalAlignment(align);
        return table;
    }

    /**
     * <br>[機  能] セルを指定したテーブルに追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param table セルを追加するテーブル
     * @param text 表示するテキスト
     * @param colspan セルの幅
     * @param align 文字位置
     * @param font フォント
     * @throws Exception 実行例外
     */
    public static void setCell(PdfPTable table,
                                 String text,
                                 int colspan,
                                 int align,
                                 Font font) throws Exception {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(align);
        table.addCell(cell);
    }

    /**
     * <br>[機  能] セルを指定したテーブルに追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param table セルを追加するテーブル
     * @param text 表示するテキスト
     * @param colspan セルの幅
     * @param horizontalAlign 文字位置(横)
     * @param verticalAlign 文字位置(縦)
     * @param font フォント
     * @throws Exception 実行例外
     */
    public static void setCell(
                                 PdfPTable table,
                                 String text,
                                 int colspan,
                                 int horizontalAlign,
                                 int verticalAlign,
                                 Font font) throws Exception {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(horizontalAlign);
        cell.setVerticalAlignment(verticalAlign);
        table.addCell(cell);
    }

    /**
     * <br>[機  能] セルを設定してセルを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param text 表示するテキスト
     * @param colspan セルの幅
     * @param horizontalAlign 文字位置(横)
     * @param font フォント
     * @throws Exception 実行例外
     * @return cell セル
     */
    public static PdfPCell setCellRet(
                                 String text,
                                 int colspan,
                                 int horizontalAlign,
                                 Font font) throws Exception {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(horizontalAlign);
        return cell;
    }

    /**
     * <br>[機  能] セルを設定してセルを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param text 表示するテキスト
     * @param colspan セルの幅
     * @param horizontalAlign 文字位置(横)
     * @param verticalAlign 文字位置(縦)
     * @param font フォント
     * @throws Exception 実行例外
     * @return cell セル
     */
    public static PdfPCell setCellRet(
                                 String text,
                                 int colspan,
                                 int horizontalAlign,
                                 int verticalAlign,
                                 Font font) throws Exception {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(horizontalAlign);
        cell.setVerticalAlignment(verticalAlign);
        return cell;
    }

    /**
     * <br>[機  能] ページ番号を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfwriter PdfWriter
     * @param fontPath フォント
     * @param doc Document
     * @param allPage 総ページ数
     * @throws Exception 実行例外
     */
    public static void setPageNumber(PdfWriter pdfwriter,
                                       String fontPath,
                                       Document doc,
                                       int allPage) throws Exception {

         final PdfContentByte cb = pdfwriter.getDirectContent();
         cb.saveState();

         cb.beginText();
         cb.setFontAndSize(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
                 BaseFont.NOT_EMBEDDED), 9);
         // ページ番号の表示内容
         final String page_text = pdfwriter.getPageNumber() + "/" + allPage;

         // 中央に表示
         cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
                            page_text,
                            (doc.right() + doc.left()) / 2,
                            10, 0);

         cb.endText();

         cb.restoreState();

    }

    /**
     * <br>[機  能] ヘッダーを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param headerTable ヘッダーに表示するテーブル
     * @return HeaderFooter ヘッダー
     * @throws Exception 実行例外
     */
    public static HeaderFooter getHeader(PdfPTable headerTable) throws Exception {

        Paragraph paragraph = new Paragraph();
        paragraph.add(headerTable);

        HeaderFooter header = new HeaderFooter(paragraph, false);
        header.setBorder(Rectangle.NO_BORDER);
        return header;
    }
    /**
    *
    * <br>[機  能] Phrase幅をもとに改行したtableへ変換する
    * <br>[解  説]
    * <br>[備  考]
    * @param cell 基準セル
    * @param jpAutoLf 日本語改行禁則に基づく
    * @return 返還後のテーブル
    * @throws Exception 実行時例外
    *
    */
    private PdfPTable __writeAutoLfText(PdfPCell cell, boolean jpAutoLf) throws Exception {
        float width = cell.getWidth() - cell.getPaddingLeft()
                - cell.getPaddingRight() - cell.getIndent();
        Phrase phrase = cell.getPhrase();
        PdfPTable multiRowTable =
                createTable(1, 100, width, Element.ALIGN_LEFT);
        if (phrase == null) {
           return multiRowTable;
        }
        multiRowTable.setSplitLate(false);
        String naiyo = phrase.getContent();
        Font font = phrase.getFont();
        if (font.getSize() < 0) {
            font.setSize(font.getSize() * (float) -1.0);
        }

        float fixLead = cell.getLeading();
        float multiLead = cell.getMultipliedLeading();
        float leading = fixLead + multiLead * font.getSize();
        if (naiyo == null) {
            PdfPCell writeListCell =
            new PdfPCell(new Phrase(naiyo, font));
            settingBorderWidth(writeListCell, 0, 0, 0, 0);
            writeListCell.setVerticalAlignment(Element.ALIGN_TOP);
            multiRowTable.addCell(writeListCell);
            return multiRowTable;
        }

        naiyo = naiyo.replaceAll("\r\n", "\n");
        int start = 0;
        int end = 0;
        int count = (int) (width / font.getSize());
        //特定終端文字
        Pattern kutouPatern
        = Pattern.compile("[,.\\]］)）}｝」』】〕〉》?!>\"'。、・？！ぁぃぅぇぉっゃゅょァィゥェォッャュョ゜゛]");

        do {
            String tmp;
            String tmp2;
            end = naiyo.indexOf("\n", start);
            if (end < 0) {
                end = naiyo.length();
            }
            int spend = 0;
            spend = start + count;
            if (spend >= end) {
                spend = end;
            }
            tmp = naiyo.substring(start, spend);
            String nextHead;
            //文字幅が規定幅になるまで文字を読み込む
            while (spend < end) {
                tmp2 = naiyo.substring(start, spend + 1);
                if (width < font.getBaseFont().getWidthPoint(tmp2, font.getSize())) {
                    break;
                }
                spend++;
            }
            if (spend == start && spend < end) {
                spend++;
            }
            tmp = naiyo.substring(start, spend);
            if (spend < end) {
                //次の文字が句読点などの特定終端文字の場合にその１文字前の時点で改行する
                if (jpAutoLf) {
                    nextHead = naiyo.substring(spend, spend + 1);
                    Matcher macher = kutouPatern.matcher(nextHead);
                    while (macher.find() && spend - 1 > start) {
                        nextHead = naiyo.substring(spend - 1, spend + 1);
                        if (width < font.getBaseFont().getWidthPoint(nextHead, font.getSize())) {
                            break;
                        }
                        spend--;
                        nextHead = naiyo.substring(spend, spend + 1);
                        macher = kutouPatern.matcher(nextHead);
                    }
                }
                tmp = naiyo.substring(start, spend);
            }
            //改行文字か文字列終端まで読み込んでいる場合は次の読み込み開始位置の改行文字を飛ばす
            if (spend < end) {
                end = spend;
            } else {
                end++;
            }

            PdfPCell writeListCell =
            new PdfPCell(new Phrase(tmp, font));
            settingBorderWidth(writeListCell, 0, 0, 0, 0);
            writeListCell.setFixedHeight(leading);
            writeListCell.setVerticalAlignment(cell.getVerticalAlignment());
            writeListCell.setHorizontalAlignment(cell.getHorizontalAlignment());
            switch (cell.getVerticalAlignment()) {
            case Element.ALIGN_BOTTOM:
                writeListCell.setPaddingTop(0);
                writeListCell.setPaddingBottom(leading - font.getSize());
                break;
            case Element.ALIGN_CENTER:
                writeListCell.setPaddingTop((leading - font.getSize()) / 2);
                writeListCell.setPaddingBottom((leading - font.getSize()) / 2);
                break;
            default:
                writeListCell.setPaddingTop(leading - font.getSize());
                writeListCell.setPaddingBottom(0);
                break;
            }
//            writeListCell.setPaddingTop(0);
//            writeListCell.setPaddingBottom(0);
            writeListCell.setPaddingLeft(0);
            writeListCell.setPaddingRight(0);
            start = end;
//            if (end >= naiyo.length()) {
//                writeListCell.setPaddingBottom(cell.getPaddingBottom());
//            }
            multiRowTable.addCell(writeListCell);
        } while (end < naiyo.length());

        return multiRowTable;
    }
    /**
     * 線の太さを設定する。
     *
     * @param cell セル情報
     * @param top セル上部の線の太さ
     * @param left セル左側の線の太さ
     * @param right セル右側の線の太さ
     * @param bottom セル下部の線の太さ
     * @return cell セル情報
     * */
    public static PdfPCell settingBorderWidth(
            PdfPCell cell, float top, float left, float right, float bottom) {
        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }
    /** ドキュメント上の仮想カーソル位置*/
    private float cursorY__ = 0f;

    /**
     *
     * <br>[機  能] 改ページ位置を計算しテーブルを配置する
     * <br>[解  説] cursorY + offset位置に描画する
     *             描画後cursorYの位置は更新される
     * 　　　　　　　 cursorYはdocと独立し当クラスのフィールドとして保存している。
     *             セル内の文字列中のフォント、サイズ、属性の変更には対応していない。
     * 　　         本メソッド以外で直接docに対してaddを行っていた場合は正常に動作しない。
     * <br>[備  考] iText2.1.7の自動改ページ処理に不具合があったため実装
     * @param doc 挿入先ドキュメント
     * @param table 挿入テーブル
     * @param offset テーブル左上の描画開始位置(左上 = 0) ０以下は0として扱う
     * @throws Exception 実行時例外
     */
    public void addCalcPaging(Document doc, PdfPTable table, float offset)
            throws Exception {
        try {
            if (offset < 0) {
                offset = 0;
            }
            if (offset > 0) {
                PdfPTable space = PdfUtil.createTable(1, 100,
                        doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin(),
                        Element.ALIGN_LEFT);
                PdfPCell cell = new PdfPCell();
                cell.setFixedHeight(offset);
                PdfUtil.settingBorderWidth(cell, 0, 0, 0, 0);
                space.addCell(cell);
                addCalcPaging(doc, space, 0);
            }
            //iTextの自動改ページよりも少ない高さで自動改ページさせるため
            //ドキュメント高さを1減らしておく
            float docHeight = doc.getPageSize().getHeight()
                    - doc.topMargin() - doc.bottomMargin() - 1;
            float nowFreeHeight = docHeight - cursorY__;
            log__.debug("docHeight=" + String.valueOf(docHeight));

            PdfPTable[] splitTable;
            float docWidth = doc.getPageSize().getWidth()
                    - doc.rightMargin() - doc.leftMargin();
            if (table.getTotalWidth() == 0) {
                __resetTableWidth(table, docWidth);
            }
            if (table.getTotalWidth() > docWidth) {
                __resetTableWidth(table, docWidth);
            }

            float beforeHeight = 0;
            int page = 1;
            do {
                log__.debug("------ページ分割------");
                log__.debug("nowFreeHeight=" + String.valueOf(nowFreeHeight));
                log__.debug("tableHeight=" + String.valueOf(table.getTotalHeight()));
                boolean notTop = (cursorY__ != 0);
                splitTable = __splitTable(table, nowFreeHeight, notTop);

                //分割前Tableと分割後Tableの高さが同じ場合、そのまま設定する
                if (splitTable[1] != null
                && beforeHeight == splitTable[1].getTotalHeight()) {
                    doc.add(splitTable[1]);
                    break;
                }

                doc.add(splitTable[0]);

                nowFreeHeight -= splitTable[0].getTotalHeight();
                cursorY__ += splitTable[0].getTotalHeight();
                log__.debug("------ページ分割完了------");
                if (splitTable[1] != null) {
                    doc.newPage();
                    nowFreeHeight = docHeight;
                    cursorY__ = 0;
                    table = splitTable[1];
                    beforeHeight = table.getTotalHeight();
                    log__.debug("次のページ分割へ");
                }

                page++;
                if (page > MAX_PAGE__) {
                    throw new Exception("PDFファイルの最大ページ数("
                                                + MAX_PAGE__
                                                + ")を超えたため、処理を中断します。");
                }

            } while (splitTable[1] != null);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append(element.toString());
                sb.append("\n");
            }
            log__.error(sb.toString());
            throw e;
        }
    }

    /**
     *
     * <br>[機  能] 再起的にテーブルの幅設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param table 設定対象
     * @param width 設定幅
     * @throws DocumentException 実行時例外
     */
    private void __resetTableWidth(PdfPTable table, float width) throws DocumentException {
        float[] childWidthes = table.getAbsoluteWidths();
        table.setTotalWidth(width);

        for (int i = 0; i < table.getRows().size(); i++) {
            PdfPRow pRow = table.getRow(i);
            for (int colNo = 0; colNo < childWidthes.length; colNo++) {
                PdfPCell cell = pRow.getCells()[colNo];
                if (cell == null) {
                    continue;
                }

                PdfPTable cTable = cell.getTable();
                if (cTable != null) {
                    float maxWidth = 0;

                    for (int j = colNo; j < colNo + cell.getColspan(); j++) {
                        maxWidth += childWidthes[j];
                    }
                    float tableWidth = cTable.getTotalWidth();
                    if (maxWidth - cell.getPaddingLeft() - cell.getPaddingRight()
                            < tableWidth || tableWidth == 0) {
                        __resetTableWidth(cTable, maxWidth);
                    }
                }
            }
        }

    }
    /**
     *
     * <br>[機  能] テーブルを指定高さで分割する
     * <br>[解  説]
     * <br>[備  考]
     * @param table 分割対象table
     * @param splitHeight 分割する高さ
     * @param ableNoHeightSplit 高さ０の位置での分割を許可しない
     * @return [0]処理後指定の高さになったテーブルが格納される [1]指定位置で分割された残り
     * @throws Exception 実行時例外
     */
    private PdfPTable[] __splitTable(PdfPTable table, float splitHeight, boolean ableNoHeightSplit)
            throws Exception {
        //空きスペース
        float nowFreeHeight = splitHeight;
        //ヘッダー以外の高さ
        float contentHeight = table.getTotalHeight()
                - table.getHeaderHeight() - table.getFooterHeight();
        //描画に必要高さ
        float drawHeaderHeight = 0;
        float drawFooterHeight = 0;
        if (!table.isSkipFirstHeader()) {
            //1ページ目からヘッダーを描画する場合
            drawHeaderHeight = table.getHeaderHeight();
        }
        if (!table.isSkipLastFooter()) {
            //最終ページでもフッターを描画する場合
            drawFooterHeight = table.getFooterHeight();
        }
        float drawHeight = contentHeight + drawHeaderHeight + drawFooterHeight;
        PdfPTable[] ret = new PdfPTable[2];

        //現在のページ内に収まる場合
        log__.debug("------テーブル分割------");
        log__.debug("スペース判定");
        log__.debug("spaceHeight=" + String.valueOf(nowFreeHeight));
        log__.debug("contentHeight=" + String.valueOf(contentHeight));
        log__.debug("headerHeight=" + String.valueOf(table.getHeaderHeight()));
        log__.debug("footerHeight=" + String.valueOf(table.getFooterHeight()));
        log__.debug("isSkipFirstHeader=" + String.valueOf(table.isSkipFirstHeader()));
        log__.debug("isSkipLastFooter=" + String.valueOf(table.isSkipLastFooter()));
        log__.debug("drawHeight=" + String.valueOf(drawHeight));
        if (nowFreeHeight >= drawHeight) {
            ret[0] = new PdfPTable(table);
            log__.debug("スペースに収まる");
            log__.debug("------テーブル分割完了------");

            return ret;
        }

        int footerStart = table.getRows().size() - table.getFooterRows();
        List<PdfPRow> header = new ArrayList<PdfPRow>();
        if (table.getHeaderRows() > 0) {
            for (int i = 0; i < table.getHeaderRows(); i++) {
                header.add(table.getRow(i));
            }
        }
        List<PdfPRow> footer = new ArrayList<PdfPRow>();
        if (footerStart < table.getRows().size()) {
            for (int i = footerStart; i < table.getRows().size(); i++) {
                footer.add(table.getRow(i));
            }
        }
        PdfPTable tmp = new PdfPTable(table);

        for (int i = table.getRows().size() - 1; i >= 0; i--) {
            table.deleteRow(i);
        }
        //先頭ヘッダー,先頭フッターが表示できない場合
        if (drawHeaderHeight + drawFooterHeight
                > nowFreeHeight) {
            if (ableNoHeightSplit) {

                log__.debug("ヘッダーフッダーが表示する高さが無いのでテーブルを次のページへ");
                ret[0] = new PdfPTable(table);
                ret[1] = tmp;
                log__.debug("ret[0] Height=" + String.valueOf(ret[0].getTotalHeight()));
                log__.debug("ret[1] Height=" + String.valueOf(ret[1].getTotalHeight()));
            } else {
                log__.warn("ヘッダーフッダーが描画領域を超えている");
                ret[0] = new PdfPTable(table);
            }
            log__.debug("-----テーブル分割完了-----");
            return ret;

        }
        table = new PdfPTable(table);

        PdfPTable nokori = new PdfPTable(table);

        for (PdfPRow row : header) {
            PdfPRow pRow = (PdfPRow) row;
            __addRow(table, pRow);
            __addRow(nokori, pRow);
        }
        table.setHeaderRows(tmp.getHeaderRows());
        nokori.setHeaderRows(tmp.getHeaderRows());
        nokori.setSkipFirstHeader(false);
        table.setSkipLastFooter(false);
        nowFreeHeight -= drawHeaderHeight;
        nowFreeHeight -= table.getFooterHeight();

        int border = tmp.getHeaderRows();
        float borderHeight = 0;
        //1行を分割する必要があるか
        for (int rowNo = tmp.getHeaderRows(); rowNo < footerStart; rowNo++) {
            float rowHeight = tmp.getRowHeight(rowNo);
            border = rowNo;
            if (borderHeight + rowHeight > nowFreeHeight) {
                //16ポイント分遊びを設ける（無理にテーブルの一部を描画しないため）
                if (borderHeight + 16 < nowFreeHeight) {
                    //分割可能かを判定
                    if (__isAbleSplitRow(tmp.getRow(rowNo),
                            borderHeight + rowHeight - nowFreeHeight)) {
                        borderHeight = nowFreeHeight;
                    }
                }
                break;
            }
            borderHeight += rowHeight;
        }
        //
        if (!ableNoHeightSplit && borderHeight == 0) {
            log__.debug("無限ループ防止！！強制分割");
            borderHeight = nowFreeHeight;
        }
        log__.debug("splitHeight="
        + String.valueOf(borderHeight) + "/" + String.valueOf(contentHeight));

        float rowY = 0;
        //次ページの先頭行用Cell配列
        PdfPCell[] splitRowTop = new PdfPCell[tmp.getNumberOfColumns()];

        for (int rowNo = tmp.getHeaderRows(); rowNo < footerStart; rowNo++) {
            PdfPRow pRow = tmp.getRow(rowNo);
            if (rowY < borderHeight) {
                //分割点前
                for (int i = 0; i < tmp.getNumberOfColumns(); i++) {
                    float rowHeight = tmp.getRowspanHeight(rowNo, i);
                    PdfPCell cell =  pRow.getCells()[i];
                    if (cell == null) {
                        continue;
                    }
                    if (rowY + rowHeight > borderHeight) {
                        //分割点を超えるセルを分割する
                        int baseSpan = cell.getRowspan();
                        PdfPCell[] cells = __splitCell(cell, borderHeight - rowY, rowHeight);
                        int span = border - rowNo + 1;
                        cells[0].setRowspan(span);

                        int span2 = baseSpan - border + rowNo;
                        cells[1].setRowspan(span2);

                        splitRowTop[i] = cells[1];
                        cell = cells[0];

                    }
                    if (cell != null) {
                        table.addCell(cell);
                    }
                }
                rowY += tmp.getRowHeight(rowNo);
            } else {
                //分割点以降
                PdfPCell[] cells = pRow.getCells();
                for (int i = 0; i < tmp.getNumberOfColumns(); i++) {
                    if (splitRowTop[i] == null) {
                        splitRowTop[i] = cells[i];
                        cells[i] = null;
                    }
                    if (splitRowTop[i] != null) {
                        nokori.addCell(splitRowTop[i]);
                        int colspan = splitRowTop[i].getColspan();
                        splitRowTop[i] = null;
                        i += (colspan - 1);
                    }
                }
                for (int i = 0; i < tmp.getNumberOfColumns(); i++) {
                    if (cells[i] != null) {
                        nokori.addCell(cells[i]);
                    }
                }
            }
        }
        for (int i = 0; i < tmp.getNumberOfColumns(); i++) {
            PdfPCell cell =  splitRowTop[i];
            if (cell == null) {
                continue;
            }
            nokori.addCell(cell);
        }

        for (PdfPRow row : footer) {
            PdfPRow pRow = (PdfPRow) row;
            __addRow(table, pRow);
            __addRow(nokori, pRow);
        }
        table.setFooterRows(footer.size());
        nokori.setFooterRows(footer.size());

        ret[0] = table;
        ret[1] = nokori;
        log__.debug("ret[0] Height=" + String.valueOf(ret[0].getTotalHeight()));
        log__.debug("ret[1] Height=" + String.valueOf(ret[1].getTotalHeight()));
        log__.debug("------テーブル分割完了------");

        return ret;
    }
    /**
     *
     * <br>[機  能] テーブルが分割可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param table テーブル
     * @param splitHeight 分割高さ
     * @return 判定結果
     * @throws Exception 実行時例外
     *
     */
     private boolean __isAbleSplitTable(PdfPTable table, float splitHeight) throws Exception {
        if (table.getRows().size() >= 1 && table.getRowHeight(0) > splitHeight) {
            return __isAbleSplitRow(table.getRow(0), splitHeight);
        }
        return true;
    }
    /**
     *
     * <br>[機  能] 行が分割可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param pRow 行
     * @param splitHeight 分割高さ
     * @return 判定結果
     * @throws Exception 実行時例外
     */
    private boolean __isAbleSplitRow(PdfPRow pRow, float splitHeight) throws Exception {
        boolean ret = true;
        PdfPCell[] cells = pRow.getCells();

        for (PdfPCell cell : cells) {
            if (cell == null) {
                continue;
            }
            PdfPTable table = cell.getTable();
            if (table != null) {
                if (ret && table.getTotalHeight() > splitHeight) {
                    ret = __isAbleSplitTable(cell.getTable(), splitHeight);
                }
            }
            if (cell.getMinimumHeight() == pRow.getMaxHeights()) {
                log__.debug("行分割不可 最小高さ（" + String.valueOf(cell.getMinimumHeight()) + "）");
                return false;
            }
            if (cell.getFixedHeight() > 0 && pRow.getMaxHeights() == cell.getFixedHeight()) {
                log__.debug("行分割不可 高さ固定（" + String.valueOf(cell.getFixedHeight()) + "）");
                return false;
            }
            if (cell.getImage() != null) {
                log__.debug("行分割不可 画像あり");
                return false;
            }
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] Cellを指定の高さで分割する
     * <br>[解  説]
     * <br>[備  考]
     * @param cell 分割対象セル
     * @param splitHeight 分割する高さ
     * @param cellHeight 分割対象セルの高さ
     * @return [0]処理後指定の高さになったセルが格納される [1]指定の高さで切りとられた残りのセル
     * @throws Exception 実行時例外
     */
    private PdfPCell[] __splitCell(PdfPCell cell, float splitHeight, float cellHeight)
            throws Exception {
        log__.debug("------セル分割------");

        PdfPTable table = cell.getTable();
        PdfPCell[] ret =  new PdfPCell[2];
        if (table != null) {
            ret[0] = new PdfPCell(cell);
            float alin_space;
            switch (cell.getVerticalAlignment()) {
            case Element.ALIGN_BOTTOM:

                alin_space = cellHeight - table.getTotalHeight()
                        - cell.getPaddingTop() - cell.getPaddingBottom();
                splitHeight -= (alin_space + cell.getPaddingTop());

                break;

            case Element.ALIGN_CENTER:

                alin_space = cellHeight - table.getTotalHeight()
                        - cell.getPaddingTop() - cell.getPaddingBottom();
                alin_space /= 2;
                splitHeight -= (alin_space + cell.getPaddingTop());
                break;


            default:

                splitHeight -= cell.getPaddingTop();
                if (cellHeight - splitHeight < cell.getPaddingBottom()) {
                    splitHeight -= cell.getPaddingBottom();
                }
                break;

            }

            log__.debug("セル内テーブル分割");
            PdfPTable[] splitTable = __splitTable(table, splitHeight, true);
            if (splitTable[1] == null) {
                splitTable[1] = createTable(1, 100, table.getTotalWidth(), Element.ALIGN_LEFT);
            }

            ret[0] = new PdfPCell(splitTable[0]);
            ret[0].setPaddingLeft(cell.getPaddingLeft());
            ret[0].setPaddingTop(cell.getPaddingTop());
            ret[0].setPaddingRight(cell.getPaddingRight());
            ret[0].setPaddingBottom(cell.getPaddingBottom());
            ret[0].setIndent(cell.getIndent());
            ret[0].setColspan(cell.getColspan());
            ret[0].setBorderWidthLeft(cell.getBorderWidthLeft());
            ret[0].setBorderWidthTop(cell.getBorderWidthTop());
            ret[0].setBorderWidthRight(cell.getBorderWidthRight());
            ret[0].setBorderWidthBottom(cell.getBorderWidthBottom());
            ret[0].setBackgroundColor(cell.getBackgroundColor());

            ret[1] = new PdfPCell(splitTable[1]);
            ret[1].setPaddingLeft(cell.getPaddingLeft());
            ret[1].setPaddingTop(cell.getPaddingTop());
            ret[1].setPaddingRight(cell.getPaddingRight());
            ret[1].setPaddingBottom(cell.getPaddingBottom());
            ret[1].setIndent(cell.getIndent());
            ret[1].setColspan(cell.getColspan());
            ret[1].setBorderWidthLeft(cell.getBorderWidthLeft());
            ret[1].setBorderWidthTop(cell.getBorderWidthTop());
            ret[1].setBorderWidthRight(cell.getBorderWidthRight());
            ret[1].setBorderWidthBottom(cell.getBorderWidthBottom());
            ret[1].setBackgroundColor(cell.getBackgroundColor());

            ret[0].setBorderWidthBottom(0);
            ret[1].setBorderWidthTop(0);
            ret[0].setPaddingBottom(0);
            ret[1].setPaddingTop(0);
            log__.debug("------セル分割完了------");
            return ret;
        }
        Image img = cell.getImage();
        if (img != null) {
            log__.debug("セル内イメージ");
            ret[0] = new PdfPCell();
            ret[0].setPaddingLeft(cell.getPaddingLeft());
            ret[0].setPaddingTop(cell.getPaddingTop());
            ret[0].setPaddingRight(cell.getPaddingRight());
            ret[0].setPaddingBottom(cell.getPaddingBottom());
            ret[0].setIndent(cell.getIndent());
            ret[0].setColspan(cell.getColspan());
            ret[0].setBorderWidthLeft(cell.getBorderWidthLeft());
            ret[0].setBorderWidthTop(cell.getBorderWidthTop());
            ret[0].setBorderWidthRight(cell.getBorderWidthRight());
            ret[0].setBorderWidthBottom(cell.getBorderWidthBottom());
            ret[0].setBackgroundColor(cell.getBackgroundColor());

            ret[1] = new PdfPCell();
            ret[1].setPaddingLeft(cell.getPaddingLeft());
            ret[1].setPaddingTop(0);
            ret[1].setPaddingRight(cell.getPaddingRight());
            ret[1].setPaddingBottom(cell.getPaddingBottom());
            ret[1].setIndent(cell.getIndent());
            ret[1].setColspan(cell.getColspan());
            ret[1].setBorderWidthLeft(cell.getBorderWidthLeft());
            ret[1].setBorderWidthTop(0);
            ret[1].setBorderWidthRight(cell.getBorderWidthRight());
            ret[1].setBorderWidthBottom(cell.getBorderWidthBottom());
            ret[1].setBackgroundColor(cell.getBackgroundColor());

            if (img.getHeight() > splitHeight - cell.getPaddingTop() - cell.getPaddingBottom()) {
                ret[0].setPaddingBottom(2);
                ret[0].setBorderWidthBottom(0);
                ret[1].setImage(img);
                if (verticalFlg__) {
                    ret[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
                }
            } else {
                ret[0].setImage(img);
                if (verticalFlg__) {
                    ret[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
                }
            }

            ret[0].setBorderWidthBottom(0);
            ret[1].setBorderWidthTop(0);
            log__.debug("------セル分割完了------");
            return ret;
        }
        Phrase ph = cell.getPhrase();

        if (ph != null) {
            log__.debug("セル内文字列分割");
            ret = __splitPhrase(cell, splitHeight, cellHeight);
            ret[0].setPaddingLeft(cell.getPaddingLeft());
            ret[0].setPaddingTop(cell.getPaddingTop());
            ret[0].setPaddingRight(cell.getPaddingRight());
            ret[0].setPaddingBottom(cell.getPaddingBottom());
            ret[0].setIndent(cell.getIndent());
            ret[0].setColspan(cell.getColspan());
            ret[0].setBorderWidthLeft(cell.getBorderWidthLeft());
            ret[0].setBorderWidthTop(cell.getBorderWidthTop());
            ret[0].setBorderWidthRight(cell.getBorderWidthRight());
            ret[0].setBorderWidthBottom(cell.getBorderWidthBottom());
            ret[0].setBackgroundColor(cell.getBackgroundColor());

            ret[1].setPaddingLeft(cell.getPaddingLeft());
            ret[1].setPaddingTop(cell.getPaddingTop());
            ret[1].setPaddingRight(cell.getPaddingRight());
            ret[1].setPaddingBottom(cell.getPaddingBottom());
            ret[1].setIndent(cell.getIndent());
            ret[1].setColspan(cell.getColspan());
            ret[1].setBorderWidthLeft(cell.getBorderWidthLeft());
            ret[1].setBorderWidthTop(cell.getBorderWidthTop());
            ret[1].setBorderWidthRight(cell.getBorderWidthRight());
            ret[1].setBorderWidthBottom(cell.getBorderWidthBottom());
            ret[1].setBackgroundColor(cell.getBackgroundColor());

            ret[0].setBorderWidthBottom(0);
            ret[1].setBorderWidthTop(0);
            ret[0].setPaddingBottom(2);
            ret[1].setPaddingTop(0);
            log__.debug("------セル分割完了------");
            return ret;
        }
        ret[0] = new PdfPCell(cell);
        ret[0].setPaddingLeft(cell.getPaddingLeft());
        ret[0].setPaddingTop(cell.getPaddingTop());
        ret[0].setPaddingRight(cell.getPaddingRight());
        ret[0].setPaddingBottom(cell.getPaddingBottom());
        ret[0].setIndent(cell.getIndent());
        ret[0].setColspan(cell.getColspan());
        ret[0].setBorderWidthLeft(cell.getBorderWidthLeft());
        ret[0].setBorderWidthTop(cell.getBorderWidthTop());
        ret[0].setBorderWidthRight(cell.getBorderWidthRight());
        ret[0].setBorderWidthBottom(cell.getBorderWidthBottom());
        ret[0].setBackgroundColor(cell.getBackgroundColor());

        ret[1] = new PdfPCell();
        ret[1].setPaddingLeft(cell.getPaddingLeft());
        ret[1].setPaddingTop(cell.getPaddingTop());
        ret[1].setPaddingRight(cell.getPaddingRight());
        ret[1].setPaddingBottom(cell.getPaddingBottom());
        ret[1].setIndent(cell.getIndent());
        ret[1].setColspan(cell.getColspan());
        ret[1].setBorderWidthLeft(cell.getBorderWidthLeft());
        ret[1].setBorderWidthTop(cell.getBorderWidthTop());
        ret[1].setBorderWidthRight(cell.getBorderWidthRight());
        ret[1].setBorderWidthBottom(cell.getBorderWidthBottom());
        ret[1].setBackgroundColor(cell.getBackgroundColor());

        ret[0].setBorderWidthBottom(0);
        ret[1].setBorderWidthTop(0);
        ret[0].setPaddingBottom(0);
        ret[1].setPaddingTop(0);
        log__.debug("空白セル分割");

        log__.debug("------セル分割完了------");

        return ret;
    }

    /**
     *
     * <br>[機  能] PDFPtableへ行の挿入
     * <br>[解  説]
     * <br>[備  考]
     * @param table 挿入先テーブル
     * @param pRow 挿入行
     */
    private  void __addRow(PdfPTable table, PdfPRow pRow) {
        for (PdfPCell cell : pRow.getCells()) {
            if (cell != null) {
                table.addCell(cell);
            }
        }

    }
    /**
     *
     * <br>[機  能] 文字列セルを高さをもとに分割
     * <br>[解  説]
     * <br>[備  考]
     * @param cell 分割対象セル
     * @param splitHeight 分割する高さ
     * @param cellHeight 分割対象セル高さ
     * @return [0] 高さで分割されたテーブル :[1]残り
     * @throws Exception 実行時例外
     *
     */
    private PdfPCell[] __splitPhrase(PdfPCell cell, float splitHeight, float cellHeight)
            throws Exception {
        log__.debug("文字列テーブル化");
        PdfPTable table = __writeAutoLfText(cell, false);
        float alin_space;

        switch (cell.getVerticalAlignment()) {
        case Element.ALIGN_BOTTOM:
            alin_space = cellHeight - table.getTotalHeight()
            - cell.getPaddingTop() - cell.getPaddingBottom();
            splitHeight -= (alin_space + cell.getPaddingTop());
            break;
        case Element.ALIGN_CENTER:
            alin_space = cellHeight - table.getTotalHeight()
            - cell.getPaddingTop() - cell.getPaddingBottom();
            alin_space /= 2;
            splitHeight -= (alin_space + cell.getPaddingTop());
            break;

        default:
            splitHeight -= cell.getPaddingTop();
            if (cellHeight - splitHeight < cell.getPaddingBottom()) {
                splitHeight -= cell.getPaddingBottom();
            }
            break;
        }

        //源信フォント用の下部空きスペース
        splitHeight -= 2;

        PdfPCell[] ret = new PdfPCell[2];

        if (table.getRows().size() > 1) {
            log__.debug("文字列テーブル分割");
            PdfPTable[] splitTable = __splitTable(table, splitHeight, true);
            ret[0] = new PdfPCell(splitTable[0]);
            if (splitTable[1] != null) {
                ret[1] = new PdfPCell(splitTable[1]);
            } else {
                ret[1] = new PdfPCell();
            }
            return ret;
        } else {
            if (table.getTotalHeight() < splitHeight) {
                log__.debug("上部セルに文字列描画");
                ret[0] = new PdfPCell(table);
                ret[1] = new PdfPCell();
            } else {
                log__.debug("下部セルに文字列描画");
                ret[0] = new PdfPCell();
                ret[1] = new PdfPCell(table);
            }
            return ret;
        }
    }
    /**
     *
     * <br>[機  能] 文字列中の¥(U+005C)を¥(U+00A5)に置き換える
     * <br>[解  説] Winで入力される「¥」がフォント上でバックスラッシュで表示されるため
     * <br>[備  考]
     * @param str 入力文字列
     * @return 置き換え後文字列
     */
    public static String replaseBackslashToYen(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\\\", "¥");
    }



}
