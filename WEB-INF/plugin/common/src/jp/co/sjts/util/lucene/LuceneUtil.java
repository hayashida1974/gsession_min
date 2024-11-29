package jp.co.sjts.util.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.io.IOTools;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 * <br>[機 能] Luceneユーティリィティクラス
 * <br>[解 説]
 * <br>[備 考]
 * @author JTS
 */
public class LuceneUtil {

    /** DirList */
    private List<File> dirs__ = null;

    /** 新規判定フラグ */
    private boolean newFlg = true;

    /**
     * コンストラクタ
     */
    public LuceneUtil() { }

    /**
     * コンストラクタ
     * @param dirList FileDirs
     */
    public LuceneUtil(List<File> dirList) {
        dirs__ = dirList;
    }

    /**
     * <br>[機 能] LuceneのIndexを作成します。
     * <br>[解 説]
     * <br>[備 考]
     * @param appPath アプリケーションディレクトリパス
     * @return IndexWriter
     * @throws IOException 入出力例外
     */
    public IndexWriter createIndex(String appPath) throws IOException {
        Directory directory = new SimpleFSDirectory(__getLuceneIndexPath(appPath));
        IndexWriterConfig indexConf =
            new IndexWriterConfig(Version.LUCENE_31, new CJKAnalyzer(Version.LUCENE_31));
        indexConf.setOpenMode(OpenMode.CREATE);
        return new IndexWriter(directory,
                new IndexWriterConfig(Version.LUCENE_31, new CJKAnalyzer(Version.LUCENE_31)));
    }

    /**
     * <br>[機 能] Luceneの既存Indexオブジェクトを取得します。
     * <br>[解 説]
     * <br>[備 考]
     * @param appPath アプリケーションディレクトリパス
     * @return IndexWriter
     * @throws IOException 入出力例外
     */
    public IndexWriter getIndex(String appPath) throws IOException {
        Directory directory = new SimpleFSDirectory(__getLuceneIndexPath(appPath));
        IndexWriterConfig indexConf =
            new IndexWriterConfig(Version.LUCENE_31, new CJKAnalyzer(Version.LUCENE_31));
        indexConf.setOpenMode(OpenMode.APPEND);
        return new IndexWriter(directory,
                new IndexWriterConfig(Version.LUCENE_31, new CJKAnalyzer(Version.LUCENE_31)));
    }

    /**
     * <br>[機  能] Luceneのインデックスパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appPath アプリケーションディレクトリパス
     * @return Lucene Indexパス
     */
    private File __getLuceneIndexPath(String appPath) {
        File file = new File(IOTools.replaceSlashFileSep(
                appPath + "/WEB-INF/plugin/help/" + LuceneConst.INDEX_NAME));
        return file;
    }

    /**
     * <br>[機 能] Indexを追加します。
     * <br>[解 説]
     * <br>[備 考]
     * @param iw IndexWriter
     * @param fileFullPath ファイル名称
     * @param pageTitle ページタイトル
     * @param addPhrase 追加フレーズ
     * @throws IOException 入出力例外
     */
    public void addIndex(
            IndexWriter iw,
            String fileFullPath,
            String pageTitle,
            String addPhrase) throws IOException {

        Document doc = new Document();
        doc.add(new Field(
                LuceneConst.FLD_TITLE, pageTitle, Field.Store.YES, Field.Index.NO));
        doc.add(new Field(
                LuceneConst.FLD_CONTENT, addPhrase, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field(
                LuceneConst.FLD_PATH, __getUnderPath(fileFullPath),
                Field.Store.YES, Field.Index.NO));
        iw.addDocument(doc);

    }

    /**
     * <br>[機  能] Lucene格納時のファイルパスを整形する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileFullPath ファイルのOS依存フルパス
     * @return アプリケーションパス
     */
    private static String __getUnderPath(String fileFullPath) {
        fileFullPath = IOTools.replaceSlashFileSep(fileFullPath);
        int warStartIndex = fileFullPath.indexOf("war") + 3;
        int length = fileFullPath.length();
        if (warStartIndex >= length) {
            return "";
        }
        return ".." + fileFullPath.substring(warStartIndex, length);
    }

    /**
     * <br>[機  能] 指定ディレクトリ以下にある「.html」ファイルを全てインデックス化する。
     * <br>[解  説]
     * <br>[備  考]
     * @param appPath アプリケーションディレクトリパス
     * @param dirPath File
     * @throws FileNotFoundException ファイル非存在時例外
     * @throws IOException 入出力例外
     */
    public void addIndexFromDir(
            String appPath, File dirPath) throws FileNotFoundException, IOException {
        File[] files = dirPath.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                addIndexFromDir(appPath, file);
            } else {

                if (IOTools.replaceSlashFileSep(file.getPath()).indexOf("ajax_") == -1
                        && file.getName().endsWith(".html")) {
                    __addIndexFromFile(appPath, file.getPath());
                }

            }
        }
    }

    /**
     * <br>[機  能] アプリパス以下特定のディレクトリの「.html」ファイルを全てインデックス化する。
     * <br>[解  説]
     * <br>[備  考]
     * @param appPath アプリケーションディレクトリパス
     * @throws FileNotFoundException ファイル非存在時例外
     * @throws IOException 入出力例外
     */
    public void addIndexFromDirs(
            String appPath) throws FileNotFoundException, IOException {
        for (File dirPath : dirs__) {
            if (dirPath.exists()) {
                addIndexFromDir(appPath, dirPath);
            }
        }
    }

    /**
     * <br>[機 能] ファイルを読込み１行ごとにインデックス保存します。
     * <br>[解 説]
     * <br>[備 考]
     * @param appPath アプリケーションディレクトリパス
     * @param fileFullPath ファイル名含むパス
     * @throws FileNotFoundException ファイル非存在例外
     * @throws IOException 入出力例外
     */
    private void __addIndexFromFile(String appPath, String fileFullPath)
            throws FileNotFoundException, IOException {

        IndexWriter iw = null;
        if (newFlg) {
            iw = createIndex(appPath);
            newFlg = false;
        } else {
            iw = getIndex(appPath);
        }

        //HTML解析
        Reader r = new InputStreamReader(
                new FileInputStream(fileFullPath), Encoding.UTF_8);
        ParserDelegator parser = new ParserDelegator();
        LuceneHtmlIndexer callback = new LuceneHtmlIndexer(this, fileFullPath, iw);

        callback = new LuceneHtmlIndexer(this, fileFullPath, iw);
        parser.parse(r, (HTMLEditorKit.ParserCallback) callback, true);

        iw.close();

    }

//    /**
//     * <br>[機 能] インデックスから検索を行います。
//     * <br>[解 説]
//     * <br>[備 考]
//     * @param searchPhrase 検索フレーズ
//     * @return true :マッチ false :アンマッチ
//     * @throws IOException 入出力例外
//     * @throws ParseException 解析例外
//     */
//    public boolean isSearchIndex(String searchPhrase) throws IOException,
//            ParseException {
//
//        boolean ret = false;
//        IndexSearcher is = new IndexSearcher("index");
//        QueryParser qp = new QueryParser(LuceneConst.FLD_CONTENT, new CJKAnalyzer());
//        Query q = qp.parse(searchPhrase);
//
//        Hits hs = is.search(q);
//        if (hs.length() > 0) {
//            ret = true;
//        }
//        is.close();
//        return ret;
//    }

//    /**
//     * <br>[機 能] トークン毎にリストに格納したものを返却します。
//     * <br>[解 説]
//     * <br>[備 考]
//     * @param phrase トークン分解対象フレーズ
//     * @return List in String
//     * @throws IOException 入出力例外
//     */
//    public List<String> getSpilitPhrases(String phrase) throws IOException {
//
//        Token t;
//        CJKAnalyzer a = new CJKAnalyzer();
//        TokenStream ts = a.tokenStream("default", new StringReader(phrase));
//        List<String> list = new ArrayList<String>();
//        while ((t = ts.next()) != null) {
//            list.add(t.toString());
//        }
//        return list;
//    }

    /**
     * <br>[機 能] 検索結果をリストに詰めて返却します。
     * <br>[解 説] 検索結果はList内にモデルとして返却します。
     * <br>[備 考]
     * @param appPath アプリケーションディレクトリパス
     * @param phrase 検索フレーズ
     * @return LuceneSearchResultModel
     * @throws IOException 入出力例外
     * @throws ParseException 解析例外
     */
    public LuceneSearchResultModel getSearchHits(
            String appPath,
            String phrase) throws IOException, ParseException {

        //検索結果モデル
        LuceneSearchResultModel ret = new LuceneSearchResultModel();
        Directory directory = new SimpleFSDirectory(__getLuceneIndexPath(appPath));
        IndexSearcher is = new IndexSearcher(directory);
        QueryParser qp = new QueryParser(
                Version.LUCENE_31, LuceneConst.FLD_CONTENT, new CJKAnalyzer(Version.LUCENE_31));
        Query q = qp.parse(phrase);

        //検索結果
        TopDocs topDocs = is.search(q, is.maxDoc());
        List<LuceneSearchResultRowModel> list = new ArrayList<LuceneSearchResultRowModel>();
        LuceneSearchResultRowModel retRow = null;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            retRow = new LuceneSearchResultRowModel();
            String title = doc.get(LuceneConst.FLD_TITLE);
            String content = doc.get(LuceneConst.FLD_CONTENT);
            String filePath = doc.get(LuceneConst.FLD_PATH);
            retRow.setTitle(title);
            retRow.setContent(content);
            retRow.setPath(filePath);
            list.add(retRow);
        }

//        Hits hs = is.search(q);
//        //ヒット件数
//        ret.setHitCount(hs.length());
//        //検索結果
//        List<LuceneSearchResultRowModel> list = new ArrayList<LuceneSearchResultRowModel>();
//        LuceneSearchResultRowModel retRow = null;
//        for (int i = 0; i < hs.length(); i++) {
//            retRow = new LuceneSearchResultRowModel();
//            String title = hs.doc(i).get(LuceneConst.FLD_TITLE);
//            String content = hs.doc(i).get(LuceneConst.FLD_CONTENT);
//            String filePath = hs.doc(i).get(LuceneConst.FLD_PATH);
//            retRow.setTitle(title);
//            retRow.setContent(content);
//            retRow.setPath(filePath);
//            list.add(retRow);
//        }
        ret.setResultList(list);
        is.close();
        return ret;

    }
}