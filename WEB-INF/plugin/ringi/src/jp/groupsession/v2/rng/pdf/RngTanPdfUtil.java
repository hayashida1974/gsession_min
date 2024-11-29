package jp.groupsession.v2.rng.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール単票Pdf出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTanPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RngTanPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RngTanPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 稟議単票Pdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream スケジュールデータの出力先となるストリーム
     * @throws Exception 実行例外
     * @throws FileNotFoundException 実行例外
     * @throws DocumentException 実行例外
     * @author JTS
     */
    public void createRngTanPdf(
            RngTanPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {
        log__.debug("スケジュール単票PDFの生成開始");

        Document doc = null;

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10(fontPath);
        //フォント タイトル
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント 項目名
        Font font_itemTitle = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒
        Font font_main = PdfUtil.getFont10(fontPath);
        //フォント(確認 赤)
        Font font_check_red = PdfUtil.getFont10b(fontPath);
        font_check_red.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(備考 赤)
        Font font_biko_red = PdfUtil.getFont10(fontPath);
        font_biko_red.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント 点線 6pt 黒
        Font font_dot = PdfUtil.getFont6(fontPath);

        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_LIGHTBLUE;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;
        PdfWriter pdfwriter = null;

        try {
            doc = new Document(PageSize.A4); //(A4 H:842F, W:595F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            pdfwriter = PdfWriter.getInstance(doc, byteout);

            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("rng.62"));
            doc.addAuthor("GroupSession");
            doc.addSubject("単票");
            doc.open();

            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();

            //ヘッダー
            float [] width_header  = {0.50f, 0.50f};
            PdfPTable table_header =
                    PdfUtil.createTable(2, 100, totalWidth, width_header, Element.ALIGN_CENTER);

            //タイトル
            float [] width_title  = {0.11f, 0.89f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //稟議内容
            float [] width_main  = {0.2f, 0.8f};
            PdfPTable table_main =
                    PdfUtil.createTable(2, 100, totalWidth, width_main, Element.ALIGN_CENTER);

            //経路情報
            float [] width_main_keiro  = {0.05f, 0.03f, 0.25f, 0.36f, 0.12f, 0.19f};
            PdfPTable table_main_keiro =
                    PdfUtil.createTable(6, 100, totalWidth, width_main_keiro, Element.ALIGN_CENTER);

            //ヘッダー
            String headerTitle = "GroupSession　稟議";
            PdfPCell cell_header = PdfUtil.setCellRet(
                    headerTitle, 0, Element.ALIGN_LEFT, font_header);
            __settingWidth(cell_header, 0, 0, 0, 0);
            cell_header.setLeading(1.2f, 1.2f); //行間の設定
            table_header.addCell(cell_header);

            UDate date = new UDate();
            cell_header = PdfUtil.setCellRet(
                    date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay(),
                    0, Element.ALIGN_RIGHT, font_header);
            __settingWidth(cell_header, 0, 0, 0, 0);
            table_header.addCell(cell_header);

            //空白
            cell_header = PdfUtil.setCellRet(
                    " ", 2, Element.ALIGN_RIGHT, font_header);
            __settingWidth(cell_header, 0, 0, 0, 0);
            cell_header.setFixedHeight(12);
            table_header.addCell(cell_header);

            //トップタイトル
            String topTitle = pdfMdl.getPdfTitle();
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 2, Element.ALIGN_LEFT, font_title);
            __settingWidth(cell_title, 0, 0, 0, 0);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            table_title.addCell(cell_title);

            PdfPCell cell_main;

            //空白
            PdfUtil.setCell(table_main, EMP__, 2, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));

            //状態
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.status"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfStatus()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //申請者
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("rng.47"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfApprUser()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //申請日時
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("rng.application.date"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfMakeDate()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //申請ID
            if (pdfMdl.getRngId() != null) {
                cell_main = PdfUtil.setCellRet(
                        gsMsg.getMessage("rng.rng180.04"),
                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                cell_main.setBackgroundColor(color_header);
                cell_main.setPaddingBottom(3);
                table_main.addCell(cell_main);

                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(pdfMdl.getRngId()),
                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                table_main.addCell(cell_main);
            }

            //入力フォーム
            String inputValue = null;
            String inputTitle = null;
            boolean commentFlg = false;
            int tableRow = -1;
            int parentSid = -1;
            int parentType = -1;
            for (RngTanPdfTemplateModel tempModel : pdfMdl.getPdfTmpList()) {
                if (tempModel.getCommentValign() != 0 && tempModel.getCommentValign() != 1) {
                    commentFlg = false;
                }
                //ブロック
                if (tempModel.getParentType() == 13) {
                    if (parentSid == tempModel.getParentSid()) {
                        if (tempModel.getTitle().length() != 0
                                || tempModel.getParam().length() != 0) {
                            String str = "";
                            if (tempModel.getTitle().length() != 0) {
                                str = tempModel.getTitle() + " ";
                            }
                            if (inputValue.length() != 0) {
                                inputValue += "\n" + str + tempModel.getParam();
                            } else {
                                inputValue = str + tempModel.getParam();
                            }
                        }

                    } else {
                        if (parentSid >= 0) {
                            if (parentType != 14) {
                                cell_main = PdfUtil.setCellRet(
                                        inputTitle,
                                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                                cell_main.setBackgroundColor(color_header);
                                cell_main.setPaddingBottom(3);
                                table_main.addCell(cell_main);
                            }

                            cell_main = PdfUtil.setCellRet(
                                    PdfUtil.replaseBackslashToYen(inputValue),
                                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                            __settingWidth(cell_main, 1, 0.5f, 1, 0);
                            table_main.addCell(cell_main);

                        }
                        parentSid = tempModel.getParentSid();
                        parentType = 13;
                        inputTitle = tempModel.getParentTitle();
                        String str = "";
                        if (tempModel.getTitle().length() > 0) {
                            str = tempModel.getTitle() + " ";
                        }
                        inputValue = str + tempModel.getParam();
                    }

                //表
                } else if (tempModel.getParentType() == 14) {
                    if (parentSid == tempModel.getParentSid()) {
                        if (tempModel.getTableRow() == tableRow) {
                            if (tempModel.getTitle().length() != 0
                                    || tempModel.getParam().length() != 0) {
                                String str = "";
                                if (tempModel.getTitle() != null) {
                                    if (tempModel.getTitle().length() != 0) {
                                        str = tempModel.getTitle() + " ";
                                    }
                                }
                                if (inputValue.length() != 0) {
                                    inputValue += "\n" + str + tempModel.getParam();
                                } else {
                                    inputValue = str + tempModel.getParam();
                                }
                            }
                        } else {
                            cell_main = PdfUtil.setCellRet(
                                    PdfUtil.replaseBackslashToYen(inputValue),
                                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                            __settingWidth(cell_main, 1, 0.5f, 1, 0);
                            table_main.addCell(cell_main);

                            tableRow = tempModel.getTableRow();
                            String str = "";
                            if (tempModel.getTitle() != null) {
                                if (tempModel.getTitle().length() != 0) {
                                    str = tempModel.getTitle();
                                }
                            }
                            if (tempModel.getParam() != null) {
                                if (str != "") {
                                    str += " ";
                                }
                                str += tempModel.getParam();
                            }
                            inputValue = str;
                        }
                    } else {
                        if (parentSid >= 0) {
                            if (parentType != 14) {
                                cell_main = PdfUtil.setCellRet(
                                        inputTitle,
                                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                                cell_main.setBackgroundColor(color_header);
                                cell_main.setPaddingBottom(3);
                                table_main.addCell(cell_main);
                            }
                            cell_main = PdfUtil.setCellRet(
                                    PdfUtil.replaseBackslashToYen(inputValue),
                                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                            __settingWidth(cell_main, 1, 0.5f, 1, 0);
                            table_main.addCell(cell_main);
                        }
                        parentSid = tempModel.getParentSid();
                        parentType = 14;
                        tableRow = 0;
                        String str = "";
                        if (tempModel.getTitle() != null) {
                            if (tempModel.getTitle().length() != 0) {
                                str = tempModel.getTitle();
                            }
                        }
                        if (tempModel.getParam() != null) {
                            if (str != "") {
                                str += " ";
                            }
                            str += tempModel.getParam();
                        }
                        inputValue = str;
                        cell_main = PdfUtil.setCellRet(
                                tempModel.getParentTitle(),
                                1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                        cell_main.setRowspan(tempModel.getListSize()); //header body footer
                        __settingWidth(cell_main, 1, 1, 0.5f, 0);
                        cell_main.setBackgroundColor(color_header);
                        cell_main.setPaddingBottom(3);
                        table_main.addCell(cell_main);
                    }
                //その他
                } else {
                    if (parentSid >= 0) {
                        parentSid = -1;

                        if (parentType != 14) {
                            cell_main = PdfUtil.setCellRet(
                                    inputTitle,
                                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                            __settingWidth(cell_main, 1, 1, 0.5f, 0);
                            cell_main.setBackgroundColor(color_header);
                            cell_main.setPaddingBottom(3);
                            table_main.addCell(cell_main);
                        }

                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(inputValue),
                                1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                        __settingWidth(cell_main, 1, 0.5f, 1, 0);
                        table_main.addCell(cell_main);

                    }
                    inputValue = tempModel.getParam();
                    inputTitle = tempModel.getTitle();

                    if (tempModel.getCommentValign() == 0) {
                        int topBorder = 0;
                        if (!commentFlg) {
                            topBorder = 1;
                        }
                        commentFlg = true;
                        cell_main = PdfUtil.setCellRet(
                                inputValue,
                                2, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                        __settingWidth(cell_main, topBorder, 0, 0, 0);
                        cell_main.setPaddingBottom(10);
                        table_main.addCell(cell_main);
                    } else if (tempModel.getCommentValign() == 1) {
                        int topBorder = 0;
                        if (!commentFlg) {
                            topBorder = 1;
                        }
                        commentFlg = true;
                        cell_main = PdfUtil.setCellRet(
                                inputValue,
                                2, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, font_main);
                        __settingWidth(cell_main, topBorder, 0, 0, 0);
                        cell_main.setPaddingTop(10);
                        table_main.addCell(cell_main);
                    } else {
                        cell_main = PdfUtil.setCellRet(
                                inputTitle,
                                1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                        __settingWidth(cell_main, 1, 1, 0.5f, 0);
                        cell_main.setBackgroundColor(color_header);
                        cell_main.setPaddingBottom(3);
                        table_main.addCell(cell_main);

                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(inputValue),
                                1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                        __settingWidth(cell_main, 1, 0.5f, 1, 0);
                        table_main.addCell(cell_main);
                    }
                }

            }
            if (parentSid >= 0) {
                if (parentType != 14) {
                    cell_main = PdfUtil.setCellRet(
                            inputTitle,
                            1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                    __settingWidth(cell_main, 1, 1, 0.5f, 0);
                    cell_main.setBackgroundColor(color_header);
                    cell_main.setPaddingBottom(3);
                    table_main.addCell(cell_main);
                }

                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(inputValue),
                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                table_main.addCell(cell_main);
            }

            //添付 (汎用稟議時)
            if (pdfMdl.getPdfRtpSid() == 0) {
                cell_main = PdfUtil.setCellRet(
                        gsMsg.getMessage("cmn.attached"),
                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                cell_main.setBackgroundColor(color_header);
                cell_main.setPaddingBottom(3);
                table_main.addCell(cell_main);

                StringBuilder tmpFileList = new StringBuilder();
                if (pdfMdl.getPdfTmpFileList() != null) {
                    for (CmnBinfModel tmpFileMdl : pdfMdl.getPdfTmpFileList()) {
                        if (tmpFileList.length() != 0) {
                            tmpFileList.append("\n");
                        }
                        tmpFileList.append(tmpFileMdl.getBinFileName());
                    }
                }
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(tmpFileList.toString()),
                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
            }

            //経路情報

            //スペース
            if (!commentFlg) {
                cell_main = PdfUtil.setCellRet(
                        gsMsg.getMessage(""),
                        6, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_itemTitle);
                __settingWidth(cell_main, 1, 0, 0, 0);
                cell_main.setPaddingBottom(7);
                table_main_keiro.addCell(cell_main);
            }

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.status"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 1, 1, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main_keiro.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.user.name"),
                    2, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main_keiro.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    "コメント/確認時添付",
                    2, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main_keiro.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("rng.rng030.04"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_itemTitle);
            __settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3);
            table_main_keiro.addCell(cell_main);

            //点線
            float [] width_dotLine  = {0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f, 0.005f, 0.0075f,
                    0.005f, 0.0075f};
            PdfPTable table_dotLine = PdfUtil.createTable(
                    160, 100, totalWidth, width_dotLine, Element.ALIGN_CENTER);

            for (int i = 0; i < 160; i++) {
                int border_top = i % 2;
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(""),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_dot);
                  __settingWidth(cell_main, border_top, 0, 0, 0);
                  cell_main.setFixedHeight(1);
                  table_dotLine.addCell(cell_main);
            }

              boolean checkFlg = true;
              for (RngTanPdfKeiroModel keiro : pdfMdl.getPdfKeiroList()) {
                if (keiro.getKeiroSingi() == 1 && checkFlg) {
                    checkFlg = false;
                    cell_main = PdfUtil.setCellRet(
                            gsMsg.getMessage("cmn.check"),
                            6, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_itemTitle);
                    __settingWidth(cell_main, 1, 1, 1, 0);
                    cell_main.setBackgroundColor(color_header);
                    cell_main.setPaddingBottom(3);
                    table_main_keiro.addCell(cell_main);
                }

                //初回フラグ
                boolean firstFlg = false;

                String kerioStatusComment = null;
                String conditionComment = null;
                boolean conditionFlg = true;
                boolean borderTopFlg = false;
                boolean conditionCountFlg = true;
                int rowCount = keiro.getKeiroRowCount(); //審議者の人数 + 代理決裁等の追加行

                if (keiro.getKeiroName() != null) { //グループ名あり
                    if (rowCount == 1) {
                        rowCount++;
                    }
                    if (keiro.getKeiroCount() > 1 && keiro.getKeiroSingi() != 1) {
                        conditionCountFlg = false;
                        rowCount = rowCount - 1;
                    }
                    rowCount++;
                }
                if (keiro.getKeiroStatus() == 6 || keiro.getKeiroStatus() == 7) { //後閲orスキップ
                    if (rowCount == 1 && keiro.getKeiroCount() != 1) {
                        rowCount++;
                    }
                    if (keiro.getKeiroCount() > 1 && keiro.getKeiroSingi() != 1
                            && conditionCountFlg) {
                        rowCount = rowCount - 1;
                    }
                    rowCount++;
                }

                if (keiro.getKeiroCount() > 1 && keiro.getKeiroSingi() == 1) {
                    rowCount = rowCount - 2;
                }

                if (keiro.getKeiroCount() == 1 && keiro.getKeiroMessage() == 0
                        && keiro.getKeiroSingi() != 1) { //全員の審議の時、一人でも表示
                    if (keiro.getKeiroName() == null
                            && (keiro.getKeiroStatus() != 6 && keiro.getKeiroStatus() != 7)) {
                        rowCount++;
                    }
                    conditionComment = "全員の審議";
                }

                String img_pass = "";
                if (keiro.getKeiroStatus() == 2 || keiro.getKeiroStatus() == 4) {
                    img_pass = "ringi/images/classic/icon_stamp_ok.png";
                } else if (keiro.getKeiroStatus() == 3) {
                    img_pass = "ringi/images/classic/icon_stamp_ng.png";
                }
                if (img_pass.length() > 0) {
                    String imagePath =
                            IOTools.replaceFileSep(appRootPath + img_pass);
                    File file = new File(imagePath);
                    //ファイルの有無チェック
                    if (file.exists()) {
                        Image img = Image.getInstance(imagePath);
                        img.scalePercent(80f);

                        cell_main = new PdfPCell(img, true);
                        cell_main.setBorder(Rectangle.NO_BORDER);
                        cell_main.setColspan(1);
                        cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        __settingWidth(cell_main, 1, 1, 1, 0);
                        cell_main.setRowspan(rowCount);
                        cell_main.setFixedHeight(30);
                        table_main_keiro.addCell(cell_main);
                    } else {
                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(""),
                                1, Element.ALIGN_CENTER, Element.ALIGN_TOP, font_check_red);
                        __settingWidth(cell_main, 1, 1, 1, 0);
                        cell_main.setRowspan(rowCount);
                        table_main_keiro.addCell(cell_main);
                    }
                } else {
                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(""),
                            1, Element.ALIGN_CENTER, Element.ALIGN_TOP, font_check_red);
                    __settingWidth(cell_main, 1, 1, 1, 0);
                    cell_main.setRowspan(rowCount);
                    table_main_keiro.addCell(cell_main);
                }

                //後閲指示
                if (keiro.getKeiroStatus() == 6) {
                    kerioStatusComment = "この経路は " + keiro.getKeiroKoetuMei()
                    + " により後閲指示されました。";
                } else if (keiro.getKeiroStatus() == 7) {
                    kerioStatusComment = "この経路は 管理者によりスキップされました。";
                }

                //審議条件
                if ((keiro.getKeiroCount() > 1 && keiro.getKeiroSingi() != 1)
                        || keiro.getKeiroName() != null) {
                    switch (keiro.getKeiroMessage()) {
                        case RngConst.RNG_OUT_CONDITION_APPROVAL:
                            conditionComment = "全員の承認";
                            break;
                        case RngConst.RNG_OUT_CONDITION_NUMBER:
                            conditionComment = keiro.getKeiroLimit() + "人の承認";
                            break;
                        case RngConst.RNG_OUT_CONDITION_RATE:
                            conditionComment = keiro.getKeiroLimit() + "%の承認";
                            break;
                        default:
                            conditionComment = "全員の審議";
                            break;
                    }
                }
                if (kerioStatusComment != null) {
                    borderTopFlg = true;
                    if (conditionComment != null) {
                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(kerioStatusComment),
                                3, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_biko_red);
                        __settingWidth(cell_main, 1, 0, 0, 0);
                        table_main_keiro.addCell(cell_main);

                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(gsMsg.getMessage("rng.rng030.22")
                                        + " : " + conditionComment),
                                2, Element.ALIGN_RIGHT, Element.ALIGN_MIDDLE, font_main);
                        __settingWidth(cell_main, 1, 0, 1, 0);
                        table_main_keiro.addCell(cell_main);
                        conditionFlg = false;
                        firstFlg = true;
                    } else {
                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(kerioStatusComment),
                                5, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_biko_red);
                        __settingWidth(cell_main, 1, 0, 1, 0);
                        table_main_keiro.addCell(cell_main);
                        firstFlg = true;
                    }
                }
                if (keiro.getKeiroName() != null) {
                    borderTopFlg = true;
                    if (conditionComment != null && conditionFlg) {

                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(keiro.getKeiroName()),
                                3, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_itemTitle);
                        __settingWidth(cell_main, 1, 0, 0, 0);
                        table_main_keiro.addCell(cell_main);

                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(gsMsg.getMessage("rng.rng030.22")
                                        + " : " + conditionComment),
                                2, Element.ALIGN_RIGHT, Element.ALIGN_MIDDLE, font_main);
                        __settingWidth(cell_main, 1, 0, 1, 0);
                        table_main_keiro.addCell(cell_main);
                        conditionFlg = false;
                        firstFlg = true;

                    } else {
                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(keiro.getKeiroName()),
                                5, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_itemTitle);
                        if (firstFlg) {
                            __settingWidth(cell_main, 0, 0, 1, 0);
                        } else {
                            firstFlg = true;
                            __settingWidth(cell_main, 1, 0, 1, 0);
                        }
                        table_main_keiro.addCell(cell_main);
                    }
                }

                if (conditionComment != null && conditionFlg) {
                    borderTopFlg = true;
                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(gsMsg.getMessage("rng.rng030.22")
                                    + " : " + conditionComment),
                            5, Element.ALIGN_RIGHT, Element.ALIGN_MIDDLE, font_main);
                    firstFlg = true;
                    __settingWidth(cell_main, 1, 0, 1, 0);
                    table_main_keiro.addCell(cell_main);
                }

                if (keiro.getKeiroCount() == 1 && keiro.getKeiroName() == null) {
                    firstFlg = false;
                }

                for (RngTanPdfSingiModel singi : keiro.getSingiList()) {
                    float borderTop = 1;
                    int keiroNameSize = 1;
                    if (firstFlg) {
                        borderTop = 0;
                        cell_main = new PdfPCell(table_dotLine);
                        cell_main.setBorder(Rectangle.NO_BORDER);
                        cell_main.setColspan(5);
                        __settingWidth(cell_main, 0, 0, 1, 0);
                        table_main_keiro.addCell(cell_main);
                    }

                    if (singi.getDairiSid() > 0) {
                        cell_main = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen("この経路は " + singi.getSingiDairi()
                                + " によって代理決裁されました。"),
                                5, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_biko_red);
                        __settingWidth(cell_main, borderTop, 0, 1, 0);
                        table_main_keiro.addCell(cell_main);
                        borderTop = 0;
                        firstFlg = true;
                    }

                    if (keiro.getKeiroCount() == 1) {
                        keiroNameSize = 2;
                        if (borderTopFlg) {
                            borderTop = 0;
                        }
                    } else {
                        img_pass = "";
                        if (singi.getSingiStatus() == 2 || singi.getSingiStatus() == 4) {
                            img_pass = "ringi/images/classic/icon_stamp_ok_20.png";
                        } else if (singi.getSingiStatus() == 3) {
                            img_pass = "ringi/images/classic/icon_stamp_ng_20.png";
                        }
                        if (img_pass.length() > 0) {
                            String imagePath =
                                    IOTools.replaceFileSep(appRootPath + img_pass);
                            File file = new File(imagePath);
                            //ファイルの有無チェック
                            if (file.exists()) {
                                Image img = Image.getInstance(imagePath);
                                img.scalePercent(55f);

                                cell_main = new PdfPCell(img, false);
                                cell_main.setBorder(Rectangle.NO_BORDER);
                                cell_main.setColspan(1);
                                cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                __settingWidth(cell_main, borderTop, 0, 0, 0);
                                table_main_keiro.addCell(cell_main);
                            } else {
                                cell_main = PdfUtil.setCellRet(
                                        "",
                                        1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE,
                                        font_check_red);
                                __settingWidth(cell_main, borderTop, 0, 0, 0);
                                table_main_keiro.addCell(cell_main);
                            }
                        } else {
                            cell_main = PdfUtil.setCellRet(
                                    "",
                                    1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_check_red);
                            __settingWidth(cell_main, borderTop, 0, 0, 0);
                            table_main_keiro.addCell(cell_main);
                        }
                    }

                    String keiro_name = singi.getSingiName();
                    if (singi.getSingiPosition().length() > 0) {
                        keiro_name += "\n" + singi.getSingiPosition();
                    }

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(keiro_name),
                            keiroNameSize, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, borderTop, 0, 0, 0);
                    table_main_keiro.addCell(cell_main);

                    String keiro_Comment_File = singi.getSingiComment();
                    for (CmnBinfModel file : singi.getTmpFileList()) {
                        keiro_Comment_File += "\n[添付]" + file.getBinFileName();
                    }

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(keiro_Comment_File),
                            2, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, borderTop, 0, 0, 0);
                    table_main_keiro.addCell(cell_main);

                    String keiro_date = "";
                    if (keiro.getKeiroStatus() != 6 && singi.getSingiDate() != null) {
                        keiro_date = singi.getSingiDate() + " " + singi.getSingiTime();
                    }

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(keiro_date),
                            1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, borderTop, 0, 1, 0);
                    table_main_keiro.addCell(cell_main);

                    firstFlg = true;
                }
            }
            cell_main = PdfUtil.setCellRet(
                    "",
                    6, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0, 0, 0);
            cell_main.setFixedHeight(1);
            table_main_keiro.addCell(cell_main);

            table_header.setSplitLate(false);
            table_title.setSplitLate(false);
            table_main.setSplitLate(false);
            table_main_keiro.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil(true);
            pdfUtil.addCalcPaging(doc, table_header, 0);
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);
            pdfUtil.addCalcPaging(doc, table_main_keiro, 0);

            doc.close();
            oStream.write(byteout.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pdfwriter != null) {
                pdfwriter.close();
            }
            if (doc != null && doc.isOpen()) {
                doc.close();
            }
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
    private PdfPCell __settingWidth(
            PdfPCell cell, float top, float left, float right, float bottom) {

        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }
}