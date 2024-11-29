package jp.groupsession.v2.bbs.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * <br>[機  能] 投稿一覧PDF出力に関するユーティリティクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsListPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsListPdfUtil.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** フォント設定部 */
    String fontStr = null;
    /** 空文字 */
    private static final String EMP__ = " ";
    /** 空白セル */
    private Font fontEmpty__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public BbsListPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @throws DocumentException セルの定義に失敗
     * @throws IOException フォントの読み込みに失敗
     */
    public BbsListPdfUtil(RequestModel reqMdl)
            throws DocumentException, IOException {
        reqMdl__ = reqMdl;
    }

    /**
     * 指定されたストリームに投稿一覧情報を出力する
     * @param appRootPath ディレクトリパス
     * @param oStream 出力先となるストリーム
     * @param pdfMdl 掲示板の表示情報を格納するモデル
     * @throws Exception 実行例外
     */
    public synchronized void createBbsReport(
            String appRootPath,
            OutputStream oStream,
            BbsListPdfModel pdfMdl) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        log__.debug("投稿一覧情報 出力開始");

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //出力用のStreamをインスタンス化します。
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        //ページサイズを設定します。
        Document doc = new Document(PageSize.A4);

        PdfWriter pdfwriter = null;
        try {
            //アウトプットストリームをPDFWriterに設定します。
            pdfwriter = PdfWriter.getInstance(doc, byteout);

            //フォント フォーラム名
            Font font_form = PdfUtil.getFont12b(fontPath);
            //フォント スレッド名
            Font font_thread = PdfUtil.getFont16b(fontPath);
            //フォント(メイン)
            Font font_main = PdfUtil.getFont12(fontPath);
            //フォント 投稿一覧
            Font font_base = PdfUtil.getFont10(fontPath);
            //フォント(改行)
            Font font_empty = PdfUtil.getFont10(fontPath);
            //フォント 投稿修正
            Font font_edit_red = PdfUtil.getFont10b(fontPath);
            font_edit_red.setColor(PdfUtil.FONT_COLOR_RED);

            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();

            //アカウント名
            PdfPTable table_acc = new PdfPTable(1);
            table_acc.setWidthPercentage(100f);
            table_acc.setTotalWidth(totalWidth);
            PdfPCell cell_acc;

            //ヘッダー
            PdfPTable table_header = null;
            float [] width_header  = {0.13f, 0.87f};
            PdfPCell cell_header;

            //メイン
            PdfPTable table_main = null;
            float [] width_main  = {1.0f};
            PdfPCell cell_main;

            String bbs = gsMsg.getMessage("cmn.bulletin");

            //出力するPDFに説明を付与します。
            doc.addTitle(bbs);
            doc.addAuthor("GroupSession");
            doc.addSubject(gsMsg.getMessage("bbs.32"));

            PdfUtil pdfUtil = new PdfUtil();
            //文章の出力を開始します。
            doc.open();

            PdfPCell cell = null;
            //フォーラム名
            PdfPTable titleTable = PdfUtil.createTable(1, 100, totalWidth, Element.ALIGN_LEFT);
            __addTitleCell(titleTable,
                    PdfUtil.replaseBackslashToYen(pdfMdl.getBfiName()), font_form);
            titleTable.setSplitLate(false);
            pdfUtil.addCalcPaging(doc, titleTable, 0);
            //スレッド名
            String threName = null;
            if (pdfMdl.getBfiThreImportance() == 1) {
                threName = "【" + gsMsg.getMessage("sml.61") + "】" + pdfMdl.getBtiTitle();
            } else {
                threName =  pdfMdl.getBtiTitle();
            }
            cell_acc = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(threName),
                            EMP__), font_thread));
            __settingWidth(cell_acc, 0, 0, 0, 1);
            cell_acc.setLeading(1.2f, 1.2f);
            cell_acc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_acc.setPaddingBottom(6f);
            table_acc.addCell(cell_acc);
            table_acc.setSplitLate(false);

            pdfUtil.addCalcPaging(doc, table_acc, 0);
            for (BulletinDspModel mdl: pdfMdl.getWriteList()) {

                //ヘッダー初期化
                table_header = new PdfPTable(2);
                table_header.setWidthPercentage(100f);
                table_header.setTotalWidth(totalWidth);
                table_header.setWidths(width_header);

                //メイン初期化
                table_main = new PdfPTable(1);
                table_main.setWidthPercentage(100f);
                table_main.setTotalWidth(totalWidth);
                table_main.setWidths(width_main);


                //空白行
                cell_header = new PdfPCell(new Phrase(EMP__, font_empty));
                cell_header.setBorder(0);
                cell_header.setColspan(2);
                table_header.addCell(cell_header);

                //投稿者：
                cell_header = new PdfPCell(
                        new Phrase(gsMsg.getMessage("cmn.pdf.contributor"), font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                //投稿者名
                cell_header = new PdfPCell(new Phrase(
                        NullDefault.getString(
                                PdfUtil.replaseBackslashToYen(mdl.getUserName()),
                                EMP__), font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                //投稿日時：
                cell_header = new PdfPCell(
                        new Phrase(gsMsg.getMessage("cmn.pdf.contributordate"),
                                font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                //投稿日時
                String upDate = mdl.getStrBwiAdate();
                int index1 = upDate.indexOf("(");
                int index2 = upDate.indexOf(")");
                String udate = upDate.substring(0, index1);
                String utime = upDate.substring(index2 + 1);
                cell_header = new PdfPCell(
                        new Phrase(udate + utime, font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                //編集日時：(修正されている場合)
                if (mdl.getWriteUpdateFlg() == 1) {
                    cell_header = new PdfPCell(new Phrase(
                            gsMsg.getMessage("cmn.pdf.editdate"), font_base));
                    cell_header.setBorder(0);
                    cell_header.setLeading(1.1f, 1.1f);
                    cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_header.addCell(cell_header);

                    //編集日時
                    String editDate = mdl.getStrBwiEdate();
                    int index3 = editDate.indexOf("(");
                    int index4 = editDate.indexOf(")");
                    String edate = editDate.substring(0, index3);
                    String etime = editDate.substring(index4 + 1);
                    cell_header = new PdfPCell(new Phrase(
                            NullDefault.getString(edate + etime, EMP__), font_base));
                    cell_header.setBorder(0);
                    cell_header.setLeading(1.1f, 1.1f);
                    cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_header.addCell(cell_header);
                }

                //添付ファイル：
                if (mdl.getTmpFileList() != null && mdl.getTmpFileList().size() > 0) {
                    int roopCount = 0;
                    StringBuilder sb = new StringBuilder();
                    for (CmnBinfModel cmnBinMdl: mdl.getTmpFileList()) {
                        sb.append(cmnBinMdl.getBinFileName() + cmnBinMdl.getBinFileSizeDsp());
                        if (roopCount != mdl.getTmpFileList().size()) {
                            sb.append("\r\n");
                        }
                        roopCount++;
                    }
                    String fileListStr = new String(sb);
                    fileListStr = PdfUtil.replaseBackslashToYen(fileListStr);
                    cell_header = new PdfPCell(new Phrase(
                            gsMsg.getMessage("cmn.pdf.bbstemp"), font_base));
                    cell_header.setBorder(0);
                    cell_header.setLeading(1.1f, 1.1f);
                    cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_header.addCell(cell_header);

                    //添付ファイル名
                    cell_header = new PdfPCell(new Phrase(fileListStr, font_base));
                    cell_header.setBorder(0);
                    cell_header.setLeading(1.1f, 1.1f);
                    cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_header.addCell(cell_header);
                }
                //内容：
                cell_header = new PdfPCell(
                        new Phrase(gsMsg.getMessage("cmn.pdf.content"), font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                //空白行
                cell = PdfUtil.setCellRet("", 3, Element.ALIGN_CENTER, fontEmpty__);
                cell.setFixedHeight(12);
                table_header.addCell(cell);

                //投稿内容
                String postBody = mdl.getBwiValue();
                if (mdl.getBwiType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
                    postBody = mdl.getBwiValuePlain();
                }
                cell_main = new PdfPCell(new Phrase(
                        PdfUtil.replaseBackslashToYen(postBody),
                        font_main));
                cell_main.setBorder(0);
                cell_main.setLeading(1.3f, 1.3f); //行間の設定
                cell_main.setPaddingLeft(15f);
                cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_main.addCell(cell_main);

                //空白
                cell_main = new PdfPCell(new Phrase(EMP__, font_main));
                __settingWidth(cell_main, 0, 0, 0, 1);
                cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_main.addCell(cell_main);


                table_header.setSplitLate(false);
                table_main.setSplitLate(false);

                pdfUtil.addCalcPaging(doc, table_header, 0);
                pdfUtil.addCalcPaging(doc, table_main, 0);

            }
            doc.close();
        } catch (Exception e) {
            log__.error("PDF出力に失敗しました。");
        } finally {
            if (pdfwriter != null) {
                pdfwriter.close();
            }
            if (doc != null && doc.isOpen()) {
                doc.close();
            }
        }

        log__.debug("end");

        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            //ページ番号の追記
            reader = new PdfReader(byteout.toByteArray());
            //生成されたPDFの総ページ数を取得する
            int total = reader.getNumberOfPages();
            PdfContentByte underContent;
            stamper = new PdfStamper(reader, oStream);

            UDate date = new UDate();
            for (int i = 1; i <= total; i++) {

                //GroupSessioin
                underContent = stamper.getUnderContent(i);
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(20, 820);
                underContent.showText("GroupSession"
                        + "  " + gsMsg.getMessage("cmn.bulletin"));
                underContent.endText();

                //アンダーコンテンツを取得する
                underContent = stamper.getUnderContent(i);
                //ページ番号を追加する、フォント設定、位置設定
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(500, 820);
                underContent.showText(
                        i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                underContent.endText();

                //アンダーコンテンツを取得する
                underContent = stamper.getUnderContent(i);
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(500, 10);
                underContent.showText(
                        date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay());
                underContent.endText();
            }

        } finally {
            if (stamper != null) {
                stamper.close();
            }
            reader.close();
        }
    }

    /**
     * <br>[機  能] タイトル行を追加します。
     * <br>[解  説]
     * <br>[備  考]
     * @param table PdfPTable
     * @param title タイトル
     * @param font フォント
     */
    private void __addTitleCell(PdfPTable table, String title, Font font) {
        PdfPCell titleCell = new PdfPCell(
                new Phrase(title, font));

        titleCell.setBorder(0);
        titleCell.setFixedHeight(20);

        table.addCell(titleCell);
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
    private PdfPCell __settingWidth(
            PdfPCell cell, float top, float left, float right, float bottom) {
        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }

}