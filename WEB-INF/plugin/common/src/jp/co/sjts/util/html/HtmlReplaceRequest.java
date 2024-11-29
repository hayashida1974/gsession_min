package jp.co.sjts.util.html;

/**
 * <br>[機  能] 文字列置換ロジック
 * <br>[解  説] 以下の処理について共通化する
 * <br> ・タグの置換を行う。
 * <br> ・属性の置換を行う。
 * <br> ・本文の置換を行う。
 * <br>[備  考] ビルダークラスを使用しインスタンス化する
 * <br> ビルダークラスの公開ゲッターに必要なラムダ式をすべてセットすること
 *
 * @author JTS
 * @param
 */
public class HtmlReplaceRequest {

    /** タグ置換関数 ラムダ関数 */
    private TagReplacer funcTagReplace__;
    /** 属性置換関数 ラムダ関数 */
    private TypeReplacer funcTypeReplace__;
    /** 本文置換関数 ラムダ関数 */
    private BodyReplacer funcBodyReplace__;

    /**
     * <p>funcTagReplace を取得します。
     * @return funcTagReplace
     * @see jp.co.sjts.util.html.HtmlReplaceRequest#funcTagReplace__
     */
    public TagReplacer getFuncTagReplace() {
        return funcTagReplace__;
    }

    /**
     * <p>funcTypeReplace を取得します。
     * @return funcTypeReplace
     * @see jp.co.sjts.util.html.HtmlReplaceRequest#funcTypeReplace__
     */
    public TypeReplacer getFuncTypeReplace() {
        return funcTypeReplace__;
    }

    /**
     * <p>funcBodyReplace を取得します。
     * @return funcBodyReplace
     * @see jp.co.sjts.util.html.HtmlReplaceRequest#funcBodyReplace__
     */
    public BodyReplacer getFuncBodyReplace() {
        return funcBodyReplace__;
    }

    /**
     *
     * <br>[機  能] 動作区分列挙型
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ビルダークラス
     */
    public enum MotionKbn {
        /** 何も行わない */
        NONE,
        /** 削除 */
        DELETE,
        /** 置換 */
        REPLACE,
        /** 開始終了部の削除 */
        STARTENDDELETE
    };

    /**
     * タグ置換関数インタフェース
     */
    @FunctionalInterface
    public interface TagReplacer {
        /**
         * 本文置換関数
         * @param tagName タグ名称
         * @return 置換結果
         */
        HtmlReplaceResult tagReplace(String tagName);
    }

    /**
     * 属性置換関数インタフェース
     */
    @FunctionalInterface
    public interface TypeReplacer {
        /**
         * 本文置換関数
         * @param tagName タグ名称
         * @param typeName 属性名
         * @param typeParam 属性値
         * @return 置換結果
         */
        HtmlReplaceResult typeReplace(String tagName, String typeName, String typeParam);
    }

    /**
     * 本文置換関数インタフェース
     */
    @FunctionalInterface
    public interface BodyReplacer {
        /**
         * 本文置換関数
         * @param tagName タグ名称
         * @param bodyParam エスケープされていない文字列
         * @return 置換結果
         */
        HtmlReplaceResult bodyReplace(String tagName, String bodyParam);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダークラス
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <br>[機  能] ビルダークラス
     * <br>[解  説] ビルダークラスとプライベートコンストラクタによって不用意な継承による悪用を防ぐ
     * <br>[備  考]
     */
    public static class Builder {
        /** タグ置換関数 ラムダ関数 */
        private TagReplacer funcTagReplace__;
        /** 属性置換関数 ラムダ関数 */
        private TypeReplacer funcTypeReplace__;
        /** 本文置換関数 ラムダ関数 */
        private BodyReplacer funcBodyReplace__;

        /**
         * <br>[機  能] タグ置換関数 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * @param funcTagReplace
         * @return builder
         */
        public Builder setFuncTagReplace(TagReplacer funcTagReplace) {
            funcTagReplace__ = funcTagReplace;
            return this;
        }

        /**
         * <br>[機  能] 属性置換関数 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * @param funcTypeReplace
         * @return builder
         */
        public Builder setFuncTypeReplace(TypeReplacer funcTypeReplace) {
            funcTypeReplace__ = funcTypeReplace;
            return this;
        }

        /**
         * <br>[機  能] 本文置換関数 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * @param funcBodyReplace
         * @return builder
         */
        public Builder setFuncBodyReplace(BodyReplacer funcBodyReplace) {
            funcBodyReplace__ = funcBodyReplace;
            return this;
        }

        /**
         *
         * <br>[機  能] 並び替え共通化ビジネスロジックインスタンスを生成
         * <br>[解  説]
         * <br>[備  考]
         * @return 並び替え共通化ビジネスロジックインスタンス
         */
        public HtmlReplaceRequest build() {
            HtmlReplaceRequest ret = new HtmlReplaceRequest();
            ret.funcTagReplace__ = funcTagReplace__;
            ret.funcTypeReplace__ = funcTypeReplace__;
            ret.funcBodyReplace__ = funcBodyReplace__;
            return ret;
        }
    }
}