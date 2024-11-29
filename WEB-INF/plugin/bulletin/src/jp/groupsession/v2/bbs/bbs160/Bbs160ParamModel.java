package jp.groupsession.v2.bbs.bbs160;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs110.Bbs110ParamModel;

/**
 * <br>[機  能] 掲示板 管理者設定 ショートメール通知設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs160ParamModel extends Bbs110ParamModel {
    /** ショートメール通知 */
    private int bbs160smlNtf__ = GSConstBulletin.BAC_SML_NTF_USER;
    /** ショートメール通知区分 */
    private int bbs160smlNtfKbn__ = GSConstBulletin.BAC_SML_NTF_KBN_YES;
    /**
     * <p>bbs160smlNtf を取得します。
     * @return bbs160smlNtf
     */
    public int getBbs160smlNtf() {
        return bbs160smlNtf__;
    }
    /**
     * <p>bbs160smlNtf をセットします。
     * @param bbs160smlNtf bbs160smlNtf
     */
    public void setBbs160smlNtf(int bbs160smlNtf) {
        bbs160smlNtf__ = bbs160smlNtf;
    }
    /**
     * <p>bbs160smlNtfKbn を取得します。
     * @return bbs160smlNtfKbn
     */
    public int getBbs160smlNtfKbn() {
        return bbs160smlNtfKbn__;
    }
    /**
     * <p>bbs160smlNtfKbn をセットします。
     * @param bbs160smlNtfKbn bbs160smlNtfKbn
     */
    public void setBbs160smlNtfKbn(int bbs160smlNtfKbn) {
        bbs160smlNtfKbn__ = bbs160smlNtfKbn;
    }
}