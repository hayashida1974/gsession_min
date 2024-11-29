package jp.groupsession.v2.adr.adr330;

import jp.groupsession.v2.adr.adr010.Adr010ParamModel;
/**
 *
 * <br>[機  能] 管理者設定アドレス管理画面のパラムモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr330ParamModel extends Adr010ParamModel {
    /** 選択アドレスチェック */
    private String[] adr330selectSid__;

    /**
     * <p>adr330selectSid を取得します。
     * @return adr330selectSid
     */
    public String[] getAdr330selectSid() {
        return adr330selectSid__;
    }

    /**
     * <p>adr330selectSid をセットします。
     * @param adr330selectSid adr330selectSid
     */
    public void setAdr330selectSid(String[] adr330selectSid) {
        adr330selectSid__ = adr330selectSid;
    }

}
