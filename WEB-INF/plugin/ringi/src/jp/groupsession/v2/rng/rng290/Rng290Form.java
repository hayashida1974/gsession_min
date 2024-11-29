package jp.groupsession.v2.rng.rng290;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng020.Rng020Form;
import jp.groupsession.v2.rng.rng110keiro.Rng110Keiro;

/**
 * <br>[機  能] 稟議テンプレート プレビュー(ポップアップ)画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng290Form extends Rng020Form {

    /** 稟議タイトル */
    private String rng290rngTitle__ = null;

    /** 申請IDSID*/
    private int rng290idSid__ = 0;

    /** 申請ID 手動入力設定*/
    private int rng290idPrefManual__ = RngConst.RAR_SINSEI_NOT_KYOKA;

    /** 添付ファイル */
    private String rng290files__ = null;

    /** 入力フォームJSON文字列(共有テンプレート＋新式テンプレートのみ) */
    private String rng290templateJSON__ = null;

    /** 内容(個人テンプレートor旧式テンプレートのみ) */
    private String rng290content__ = null;

    /** 経路設定*/
    private Rng110Keiro rng290keiro__ = new Rng110Keiro();

    /**
     * <p>rng290rngTitle を取得します。
     * @return rng290rngTitle
     */
    public String getRng290rngTitle() {
        return rng290rngTitle__;
    }
    /**
     * <p>rng290rngTitle をセットします。
     * @param rng290rngTitle rng290rngTitle
     */
    public void setRng290rngTitle(String rng290rngTitle) {
        rng290rngTitle__ = rng290rngTitle;
    }

    /**
     * <p>rng290idSid を取得します。
     * @return rng290idSid
     */
    public int getRng290idSid() {
        return rng290idSid__;
    }
    /**
     * <p>rng290idSid をセットします。
     * @param rng290idSid rng290idSid
     */
    public void setRng290idSid(int rng290idSid) {
        rng290idSid__ = rng290idSid;
    }

    /**
     * <p>rng290idPrefManual を取得します。
     * @return rng290idPrefManual
     */
    public int getRng290idPrefManual() {
        return rng290idPrefManual__;
    }
    /**
     * <p>rng290idPrefManual をセットします。
     * @param rng290idPrefManual rng290idPrefManual
     */
    public void setRng290idPrefManual(int rng290idPrefManual) {
        rng290idPrefManual__ = rng290idPrefManual;
    }

    /**
     * <p>rng290files を取得します。
     * @return rng290files
     */
    public String getRng290files() {
        return rng290files__;
    }

    /**
     * <p>rng290files をセットします。
     * @param rng290files rng290files
     */
    public void setRng290files(String rng290files) {
        rng290files__ = rng290files;
    }

    /**
     * <p>rng290templateJSON を取得します。
     * @return rng290templateJSON
     */
    public String getRng290templateJSON() {
        return rng290templateJSON__;
    }
    /**
     * <p>rng290templateJSON をセットします。
     * @param rng290templateJSON rng290templateJSON
     */
    public void setRng290templateJSON(String rng290templateJSON) {
        rng290templateJSON__ = rng290templateJSON;
    }

    /**
     * <p>rng290content を取得します。
     * @return rng290content
     */
    public String getRng290content() {
        return rng290content__;
    }
    /**
     * <p>rng290content をセットします。
     * @param rng290content rng290content
     */
    public void setRng290content(String rng290content) {
        rng290content__ = rng290content;
    }

    /**
     * <p>rng290keiro を取得します。
     * @return rng290keiro
     */
    public Rng110Keiro getRng290keiro() {
        return rng290keiro__;
    }
    /**
     * <p>rng290keiro をセットします。
     * @param rng290keiro rng290keiro
     */
    public void setRng290keiro(Rng110Keiro rng290keiro) {
        rng290keiro__ = rng290keiro;
    }
}
