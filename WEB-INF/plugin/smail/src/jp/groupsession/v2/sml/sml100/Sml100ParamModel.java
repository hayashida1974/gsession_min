package jp.groupsession.v2.sml.sml100;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;

/**
 * <br>[機  能] ショートメール 管理者設定メニュー画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml100ParamModel extends Sml010ParamModel {
    /** 自動削除区分 */
    private int sml100autoDelKbn__ = GSConstSmail.AUTO_DEL_ADM;

    /**
     * <p>sml100autoDelKbn を取得します。
     * @return sml100autoDelKbn
     */
    public int getSml100autoDelKbn() {
        return sml100autoDelKbn__;
    }

    /**
     * <p>sml100autoDelKbn をセットします。
     * @param sml100autoDelKbn sml100autoDelKbn
     */
    public void setSml100autoDelKbn(int sml100autoDelKbn) {
        sml100autoDelKbn__ = sml100autoDelKbn;
    }
}