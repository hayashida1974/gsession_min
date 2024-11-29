package jp.co.sjts.util.log;

import org.apache.commons.logging.Log;

import jp.co.sjts.util.CloneableUtil;

/**
 * 
 * <br>[機  能] try with resourceで使用し開始終了時にログを出力する
 * <br>[解  説] ログレベルはINFO
 * <br>[備  考]
 *
 * @author JTS
 */
public class LogBlock implements AutoCloseable {
    /** パラメータ*/
    private final Builder param__; 
    /** 時間監視*/
    private final long timming__;
    /** ビルダークラス*/
    public static class Builder implements Cloneable {
        /** Logging インスタンス */
        private Log log__;
        /** 実行処理名*/
        private String blockName__;
        /** 開始時イベント*/
        private Runnable startEvt__;
        /** 終了時イベント*/
        private Runnable finishEvt__;
        /**
         * 
         * <br>[機  能] 開始時実行イベントをセット
         * <br>[解  説]
         * <br>[備  考]
         * @param startEvt 開始時実行イベント
         * @return this
         */
        public Builder setStartEvt(Runnable startEvt) {
            startEvt__ = startEvt;
            return this;
        }
        /**
         * 
         * <br>[機  能] 終了時実行イベントをセット
         * <br>[解  説]
         * <br>[備  考]
         * @param finishEvt 終了時実行イベント
         * @return this
         */
        public Builder setFinishEvt(Runnable finishEvt) {
            finishEvt__ = finishEvt;
            return this;
        }
        @Override
        public Builder clone() {
            Builder ret = new Builder();
            CloneableUtil.copyField(ret, this);
            return ret;
        }
        
        public LogBlock build() {
            return new LogBlock(this.clone());
        }
    }
    /**
     * 
     * <br>[機  能] ビルダ生成
     * <br>[解  説]
     * <br>[備  考]
     * @param blockName ブロック名
     * @param log ロガー
     * @return ビルダー
     */
    public static Builder builder(String blockName, Log log) {
        Builder ret = new Builder();
        ret.log__ = log;
        ret.blockName__ = blockName;
        
        return ret;
    }
    /**
     * 
     * コンストラクタ
     * @param clone パラメータモデル
     */
    private LogBlock(Builder clone) {
        param__ = clone;
        param__.log__.info(String.format("---- %s START", param__.blockName__));
        timming__ = System.currentTimeMillis();
        if (param__.startEvt__ != null) {
            param__.startEvt__.run();
        }
    }

    @Override
    public void close() {
        if (param__.finishEvt__ != null) {
            param__.finishEvt__.run();
        }
        param__.log__.info(String.format(
                "---- %s END %d ms",
                param__.blockName__,
                System.currentTimeMillis() - timming__
               ));
    }

}
