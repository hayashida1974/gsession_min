package jp.co.sjts.util.html;

import jp.co.sjts.util.html.HtmlReplaceRequest.MotionKbn;

/**
 * <br>[機  能] ソート順変更情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class HtmlReplaceResult {
    /** 動作区分 */
    MotionKbn motionKbn__;
    /** 置き換え後文字列 */
    String afterString__;

    /**
     * <p>mdl を取得します。
     * @return motionKbn
     * @see jp.co.sjts.util.html.HtmlReplaceResult#getMotionKbn__
     */
    public MotionKbn getMotionKbn() {
        return motionKbn__;
    }
    /**
     * <p>beforeSort を取得します。
     * @return afterString
     * @see jp.co.sjts.util.html.HtmlReplaceResult#afterString
     */
    public String getAfterString() {
        return afterString__;
    }

    /**
     *
     * <br>[機  能] リザルト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param mKbn 動作区分
     * @param afterString 置換後文字列
     *
     * @return ビルダークラス
     */
    public static HtmlReplaceResult builder(
            MotionKbn mKbn, String afterString) {
        HtmlReplaceResult ret = new HtmlReplaceResult();
        ret.motionKbn__ = mKbn;
        ret.afterString__ = afterString;
        return ret;
    }

}