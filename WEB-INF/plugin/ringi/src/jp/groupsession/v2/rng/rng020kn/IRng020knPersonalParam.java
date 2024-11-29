package jp.groupsession.v2.rng.rng020kn;

import java.util.Map;

import jp.groupsession.v2.rng.rng020.IRng020PeronalParam;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;

/**
*
* <br>[機  能] Rng020登録画面機能で使用するパラメータ宣言
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public interface IRng020knPersonalParam extends IRng020PeronalParam {
    /**
     * <p>rng020kakuninKeiro を取得します。
     * @param sortNo 表示順
     * @return rng020kakuninKeiro
     */
    public Rng020KeiroBlock getRng020kakuninKeiroDsp(String sortNo);
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param sort 表示順
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setRng020kakuninKeiroDsp(Integer sort, Rng020KeiroBlock rng020kakuninKeiro);
    /**
     * <p>rng020kakuninKeiro を取得します。
     * @return rng020kakuninKeiro
     */
    public Map<Integer, Rng020KeiroBlock> getRng020kakuninKeiroDspMap();
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setRng020kakuninKeiroDspMap(Map<Integer, Rng020KeiroBlock> rng020kakuninKeiro);
}
