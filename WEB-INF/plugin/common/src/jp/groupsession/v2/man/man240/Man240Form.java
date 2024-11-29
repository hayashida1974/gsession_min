package jp.groupsession.v2.man.man240;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man240Form extends AbstractGsForm {

    /** オペレーションログ設定リスト */
    private List<Man240BaseForm> man240LogConf__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man240Form() {
        man240LogConf__ = new ArrayList < Man240BaseForm >();
    }

    /**
     * <p>man240LogConf を取得します。
     * @return man240LogConf
     */
    public List<Man240BaseForm> getMan240LogConfList() {
        return man240LogConf__;
    }

    /**
     * <p>man240LogConf をセットします。
     * @param logConf logConf
     */
    public void setMan240LogConfForm(List<Man240BaseForm> logConf) {
        man240LogConf__ = logConf;
    }

    /**
     * <p>Man240BaseForm を取得します。
     * @param iIndex インデックス番号
     * @return Man240BaseForm を戻す
     */
    public Man240BaseForm getMan240LogConf(int iIndex) {
        while (man240LogConf__.size() <= iIndex) {
            man240LogConf__.add(new Man240BaseForm());
        }
        return man240LogConf__.get(iIndex);
    }
    /**
     * <p>Man240BaseForm を取得します。
     * @return Man240BaseForm[]
     */
    public Man240BaseForm[] getMan240LogConf() {
        return (Man240BaseForm[]) man240LogConf__.toArray(new Man240BaseForm[0]);
    }
}
