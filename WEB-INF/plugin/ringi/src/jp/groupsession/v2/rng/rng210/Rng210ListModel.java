package jp.groupsession.v2.rng.rng210;
/**
 * <br>[機  能] 稟議 管理者設定 フォーマットリストModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */

public class Rng210ListModel {
    /** 初期表示フォーマット選択値 */
    private String rng210SelectFormat__ = null;
    /** 文字入力値*/
    private String rng210FormatWord__ = null;
    /**
     * <p>rng210SelectFormat を取得します。
     * @return rng210SelectFormat
     */
    public String getRng210SelectFormat() {
        return rng210SelectFormat__;
    }
    /**
     * <p>rng210SelectFormat をセットします。
     * @param rng210SelectFormat rng210SelectFormat
     */
    public void setRng210SelectFormat(String rng210SelectFormat) {
        rng210SelectFormat__ = rng210SelectFormat;
    }
    /**
     * <p>rng210FormatWord を取得します。
     * @return rng210FormatWord
     */
    public String getRng210FormatWord() {
        return rng210FormatWord__;
    }
    /**
     * <p>rng210FormatWord をセットします。
     * @param rng210FormatWord rng210FormatWord
     */
    public void setRng210FormatWord(String rng210FormatWord) {
        rng210FormatWord__ = rng210FormatWord;
    }
}
