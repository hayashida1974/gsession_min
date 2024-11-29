package jp.groupsession.v2.wml.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
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
 * <br>[機  能] WEBメール内容PDF出力に関するユーティリティクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public WmlPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] WEBメール内容PDF出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfModel PDFモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream 勤務表データの出力先となるストリーム
     * @throws Exception 実行例外
     * @author JTS
     */
    public void createSmalPdf(
            WmlPdfModel pdfModel,
            String appRootPath,
            OutputStream oStream) throws Exception {

        Document doc = null;
        try {
            /* フォント設定部 */
            String fontPath = PdfUtil.getFontFilePath(appRootPath);
            //フォント(アカウント名)
            Font font_accountName = PdfUtil.getFont16b(fontPath);
            //フォント(ヘッダー)
            Font font_header = PdfUtil.getFont10(fontPath);
            //フォント(メイン)
            Font font_main = PdfUtil.getFont12(fontPath);
            //フォント(改行)
            Font font_empty = PdfUtil.getFont10(fontPath);

            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
            //アウトプットストリームをPDFWriterに設定します。
            doc = new Document(PageSize.A4); //(595F,842F)
            PdfWriter.getInstance(doc, byteout);

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String wmail = gsMsg.getMessage("wml.wml010.25");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(wmail);

            doc.open();


            //文字入力範囲（横）
            float totalWidth = 920;

            //アカウント名
            PdfPTable table_acc = new PdfPTable(1);
            table_acc.setWidthPercentage(100f);
            table_acc.setTotalWidth(totalWidth);
            PdfPCell cell_acc;

            //ヘッダー
            PdfPTable table_header = new PdfPTable(2);
            table_header.setWidthPercentage(100f);
            table_header.setTotalWidth(totalWidth);
            float [] width_header  = {0.13f, 0.87f};
            table_header.setWidths(width_header);
            PdfPCell cell_header;

            //メイン
            PdfPTable table_main = new PdfPTable(1);
            table_main.setWidthPercentage(100f);
            table_main.setTotalWidth(totalWidth);
            float [] width_main  = {1.0f};
            table_main.setWidths(width_main);
            PdfPCell cell_main;

            //タイトル
            cell_acc = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfModel.getTitle()), EMP__),
                    font_accountName));
            settingWidth(cell_acc, 0, 0, 0, 1);
            cell_acc.setLeading(1.2f, 1.2f); //行間の設定
            cell_acc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_acc.setPaddingBottom(6f);
            table_acc.addCell(cell_acc);

            //空白
            cell_acc = new PdfPCell(new Phrase(EMP__, font_empty));
            cell_acc.setBorder(0);
            table_acc.addCell(cell_acc);

            //ヘッダー
            //アカウント
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("wml.102") + "：", font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(pdfModel.getAccName()),
                    font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);
            //タイトル
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.kenmei"), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfModel.getTitle()), EMP__),
                    font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //差出人
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.sendfrom"), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(pdfModel.getSender()),
                    font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //日時
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.date"), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(pdfModel.getDate(), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //宛先
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.atesaki"), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfModel.getAtesaki()), EMP__),
                    font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //宛先CC
            if (!StringUtil.isNullZeroString(pdfModel.getAtesakiCC())) {
                cell_header = new PdfPCell(new Phrase(
                        gsMsg.getMessage("cmn.pdf.cc"), font_header));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                cell_header = new PdfPCell(new Phrase(
                        NullDefault.getString(
                                PdfUtil.replaseBackslashToYen(pdfModel.getAtesakiCC()), EMP__),
                        font_header));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);
            }


            //宛先BCC
            if (!StringUtil.isNullZeroString(pdfModel.getAtesakiBCC())) {
                cell_header = new PdfPCell(new Phrase(
                        "ＢＣＣ：", font_header));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                cell_header = new PdfPCell(new Phrase(
                        NullDefault.getString(
                                PdfUtil.replaseBackslashToYen(pdfModel.getAtesakiBCC()), EMP__),
                        font_header));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);
            }

            //ラベル
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.label"), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfModel.getLabel()), EMP__),
                    font_header));
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setBorder(0);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //添付
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.temp"), font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfModel.getTempFile()), EMP__),
                    font_header));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);
            //空白行
            cell_header = new PdfPCell(new Phrase(EMP__, font_empty));
            settingWidth(cell_header, 0, 0, 0, 1);
            cell_header.setColspan(2);
            table_header.addCell(cell_header);

            //空白行
            cell_header = new PdfPCell(new Phrase(EMP__, font_empty));
            cell_header.setBorder(0);
            cell_header.setColspan(2);
            table_header.addCell(cell_header);

            //本文
            cell_main = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfModel.getMain()), EMP__),
                    font_main));
            cell_main.setBorder(0);
            cell_main.setLeading(1.3f, 1.3f); //行間の設定
            cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_main.addCell(cell_main);

            table_acc.setSplitLate(false);
            table_header.setSplitLate(false);
            table_main.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_acc, 0);
            pdfUtil.addCalcPaging(doc, table_header, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);

            doc.close();


            PdfReader reader = null;
            PdfStamper stamper = null;
            try {
                //ページ番号の追記
                reader = new PdfReader(byteout.toByteArray());
                //生成されたPDFの総ページ数を取得する
                int total = reader.getNumberOfPages();
                PdfContentByte under_content;
                stamper = new PdfStamper(reader, oStream);

                UDate date = new UDate();
                for (int i = 1; i <= total; i++) {
                    //GroupSessioin
                    under_content = stamper.getUnderContent(i);
                    under_content.beginText();
                    under_content.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                    under_content.moveText(20, 820);
                    under_content.showText("GroupSession"
                            + "  " + gsMsg.getMessage("wml.wml010.25"));
                    under_content.endText();

                    //ページ番号を追加する
                    under_content = stamper.getUnderContent(i);
                    under_content.beginText();
                    under_content.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                    under_content.moveText(500, 820);
                    under_content.showText(
                            i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                    under_content.endText();

                    //日付
                    under_content = stamper.getUnderContent(i);
                    under_content.beginText();
                    under_content.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                    under_content.moveText(500, 10);
                    under_content.showText(
                            date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay());
                    under_content.endText();
                }
            } finally {
                if (stamper != null) {
                    stamper.close();
                }
                reader.close();
            }

        } catch (FileNotFoundException e) {
            log__.error(e);
        } catch (DocumentException e) {
            log__.error(e);
        } catch (Exception e) {
            log__.error(e);
        }

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
    private PdfPCell settingWidth(PdfPCell cell, float top, float left, float right, float bottom) {

        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }
}