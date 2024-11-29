package jp.co.sjts.util.lucene;


/**
 * <br>[機  能] 検索エンジンLuceneに関する定数クラス郡です。
 * <br>[解  説] IndexWriterのコンストラクタ指定の際の注意、<br>
 *              INDEX_CREATE_TRUE （一度インデックスを消去して再作成）<br>
 *              INDEX_CREATE_FALSE（optimize()や、単なる追加の場合）
 * <br>[備  考]
 *
 * @author JTS
 */
public class LuceneConst {

    /** インデックス名称 */
    public static final String INDEX_NAME = "help_index";
    /** インデックス作成オプション 作成する（初回のみ） */
    public static final boolean INDEX_CREATE_TRUE = true;
    /** インデックス作成オプション 作成しない */
    public static final boolean INDEX_CREATE_FALSE = false;

    /** インデックス作成チューニング用定数（１インデックスファイルのサイズ）初期値10 */
    public static final int INDEX_MAX_BUFFERED_DOC = 10;
    /** インデックス作成チューニング用定数（インデックスを束ねる数）初期値10 */
    public static final int INDEX_MERGE_FACTOR = 10;

    /** フィールド格納種別 "content" */
    public static final String FLD_CONTENT = "content";
    /** フィールドタイトル */
    public static final String FLD_TITLE = "title";
    /** フィールド格納種別 "path" */
    public static final String FLD_PATH = "path";
}
