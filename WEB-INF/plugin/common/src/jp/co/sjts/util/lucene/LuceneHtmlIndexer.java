package jp.co.sjts.util.lucene;

import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;

/**
 * <br>[機  能] HTMLの文字列のみ取得します。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class LuceneHtmlIndexer extends HTMLEditorKit.ParserCallback {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(LuceneHtmlIndexer.class);

    /** LuceneUtil */
    private LuceneUtil lucene__ = null;
    /** ファイルのフルパス */
    private String fileFullPath__ = null;
    /** IndexWriter */
    private IndexWriter iw__ = null;


    /** TITLEフラグ */
    private boolean flgTagTitle__ = false;
    /** TITLE文字列 */
    private String pageTitle__ = "";
    /** Pタグフラグ */
    private boolean flgTagP__ = false;
    /** H2タグフラグ */
    private boolean flgTagH2__ = false;
    /** 文字列バッファ */
    private StringBuilder sb__ = new StringBuilder("");

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param lucene LuceneUtil
     * @param fileFullPath ファイルのフルパス
     * @param iw IndexWriter
     */
    public LuceneHtmlIndexer(LuceneUtil lucene, String fileFullPath, IndexWriter iw) {
        lucene__ = lucene;
        fileFullPath__ = fileFullPath;
        iw__ = iw;
    }

    /**
     * フラッシュ
     * @throws BadLocationException ロケーション例外
     */
    public void flush() throws BadLocationException {
        super.flush();
    }


    /**
     * <br>[機  能] HTMLの開始タグの取得・処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param tag HTML.Tag
     * @param attributes MutableAttributeSet
     * @param pos ポジション
     */
    public void handleStartTag(
            HTML.Tag tag,
            MutableAttributeSet attributes,
            int pos) {

        if (tag.equals(HTML.Tag.P)) {
            log__.debug("------------------------------");
            flgTagP__ = true;
            log__.debug("startタグ :" + tag.toString());

        } else if (tag.equals(HTML.Tag.H2)) {
            log__.debug("------------------------------");
            flgTagH2__ = true;
            log__.debug("startタグ :" + tag.toString());

        } else if (tag.equals(HTML.Tag.BR)) {
            sb__.append(" ");

        } else if (tag.equals(HTML.Tag.TITLE)) {
            flgTagTitle__ = true;

        }

        super.handleStartTag(tag, attributes, pos);
    }

    /**
     * <br>[機  能] HTMLの終了タグの取得・処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param tag HTML.Tag
     * @param pos ポジション
     */
    public void handleEndTag(
            HTML.Tag tag,
            int pos) {

        try {

            if (tag.equals(HTML.Tag.P)) {
                __execAddIndex(tag);
                flgTagP__ = false;

            } else if (tag.equals(HTML.Tag.H2)) {
                __execAddIndex(tag);
                flgTagH2__ = false;

            } else if (tag.equals(HTML.Tag.TITLE)) {
                flgTagTitle__ = false;

            }

        } catch (Exception e) {
            log__.warn("HTMLのIndex化に失敗しました。", e);
        }
        super.handleEndTag(tag, pos);
    }

    /**
     * <br>[機  能] 終了タグを検出した時にLuceneのインデックスを追加します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tag HTML.Tag
     * @throws IOException 入出力例外
     */
    private void __execAddIndex(HTML.Tag tag) throws IOException {
        log__.debug("ページタイトル  :" + pageTitle__);
        log__.debug("ファイルパス    :" + fileFullPath__);
        log__.debug("格納文字列      :" +  sb__.toString());
        lucene__.addIndex(iw__, fileFullPath__, pageTitle__, sb__.toString());
        sb__ = new StringBuilder("");

        log__.debug("endタグ :" + tag.toString());
        log__.debug("------------------------------");

    }

    /**
     * <br>[機  能] HTMLの単体タグの取得・処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param tag HTML.Tag
     * @param attributes MutableAttributeSet
     * @param pos ポジション
     */
    public void handleSimpleTag(
            HTML.Tag tag,
            MutableAttributeSet attributes,
            int pos) {
        String endtag = getAttributeValueString(attributes, "endtag");
        if ("true".equals(endtag)) {
            handleEndTag(tag, pos);
        } else {
            handleStartTag(tag, attributes, pos);
        }

        super.handleStartTag(tag, attributes, pos);
    }

    /**
     * <br>[機  能] HTMLの文字列の取得・処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param data char[]
     * @param pos ポジション
     */
    public void handleText(char[] data, int pos) {

        if (flgTagP__ || flgTagH2__) {
            sb__.append(new String(data));
        } else if (flgTagTitle__) {
            pageTitle__ = (new String(data));
        }
        super.handleText(data, pos);
    }

    /**
     * <br>[機  能] AttributeValueStringを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param attributes MutableAttributeSet
     * @param tag starttag or endtag
     * @return "true" or null
     */
    private String getAttributeValueString(MutableAttributeSet attributes, String tag) {
        return (String) attributes.getAttribute(tag);
    }
}
