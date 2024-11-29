package jp.groupsession.v2.rng.rng030;

import java.util.HashMap;
import java.util.Map;

import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;
/**
 *
 * <br>[機  能] 経路再設定用 パラメータクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng030KeiroEdit {
    /** 経路設定 */
    private Map<Integer, Rng020KeiroBlock> keiro__
    = new HashMap<Integer, Rng020KeiroBlock>();
    /** 最終確認設定 */
    private Map<Integer, Rng020KeiroBlock> kakuninKeiro__
    = new HashMap<Integer, Rng020KeiroBlock>();


    /**
     * <p>rng020keiro を取得します。
     * @return rng020keiro
     */
    public Map<Integer, Rng020KeiroBlock> getKeiroMap() {
        return keiro__;
    }
    /**
     * <p>rng020keiro をセットします。
     * @param rng020keiro rng020keiro
     */
    public void setKeiroMap(Map<Integer, Rng020KeiroBlock> rng020keiro) {
        keiro__ = rng020keiro;
    }
    /**
     * <p>rng020kakuninKeiro を取得します。
     * @return rng020kakuninKeiro
     */
    public Map<Integer, Rng020KeiroBlock> getKakuninKeiroMap() {
        return kakuninKeiro__;
    }
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setKakuninKeiroMap(Map<Integer, Rng020KeiroBlock> rng020kakuninKeiro) {
        kakuninKeiro__ = rng020kakuninKeiro;
    }
    /**
     * <p>rng020keiro を取得します。
     * @param sortNo 表示順
     * @return rng020keiro
     */
    public Rng020KeiroBlock getKeiro(String sortNo) {
        Integer sort = Integer.valueOf(sortNo);
        if (keiro__.containsKey(sort)) {
            return keiro__.get(sort);
        }
        Rng020KeiroBlock ret = new Rng020KeiroBlock();
        keiro__.put(sort, ret);
        return ret;
    }
    /**
     * <p>rng020keiro をセットします。
     * @param sort 表示順
     * @param rng020keiro rng020keiro
     */
    public void setKeiro(Integer sort, Rng020KeiroBlock rng020keiro) {
        keiro__.put(sort, rng020keiro);
    }
    /**
     * <p>rng020kakuninKeiro を取得します。
     * @param sortNo 表示順
     * @return rng020kakuninKeiro
     */
    public Rng020KeiroBlock getKakuninKeiro(String sortNo) {
        Integer sort = Integer.valueOf(sortNo);
        if (kakuninKeiro__.containsKey(sort)) {
            return kakuninKeiro__.get(sort);
        }
        Rng020KeiroBlock ret = new Rng020KeiroBlock();
        kakuninKeiro__.put(sort, ret);
        return ret;
    }
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param sort 表示順
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setKakuninKeiro(Integer sort, Rng020KeiroBlock rng020kakuninKeiro) {
        kakuninKeiro__.put(sort, rng020kakuninKeiro);
    }


}
