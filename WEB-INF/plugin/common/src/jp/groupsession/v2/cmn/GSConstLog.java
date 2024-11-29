package jp.groupsession.v2.cmn;

/**
 * <br>[機  能] GroupSession オペレーションログ用定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstLog {

    /** ログレベル エラー */
    public static final String LEVEL_ERROR = "error";
    /** ログレベル 警告 */
    public static final String LEVEL_WARN = "warn";
    /** ログレベル 重要情報 */
    public static final String LEVEL_INFO = "info";
    /** ログレベル 一般情報(トレース) */
    public static final String LEVEL_TRACE = "trace";

    /** ログ出力有無 出力しない */
    public static final int LOG_DISABLE = 0;
    /** ログ出力有無 出力する */
    public static final int LOG_ENABLE = 1;

    /** オペレーションログ検索結果 一覧表示件数 */
    public static final int OPERATION_LOG_LIST_CNT = 30;

}