package jp.groupsession.v2.man.man070kn;

import jp.groupsession.v2.man.man070.Man070Form;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070knForm extends Man070Form {

    /** プロキシサーバを使用しないアドレス(表示用) */
    private String[] man070knViewNoProxyAddress__ = null;

    /**
     * <p>man070knViewNoProxyAddress を取得します。
     * @return man070knViewNoProxyAddress
     */
    public String[] getMan070knViewNoProxyAddress() {
        return man070knViewNoProxyAddress__;
    }

    /**
     * <p>man070knViewNoProxyAddress をセットします。
     * @param man070knViewNoProxyAddress man070knViewNoProxyAddress
     */
    public void setMan070knViewNoProxyAddress(String[] man070knViewNoProxyAddress) {
        man070knViewNoProxyAddress__ = man070knViewNoProxyAddress;
    }

}