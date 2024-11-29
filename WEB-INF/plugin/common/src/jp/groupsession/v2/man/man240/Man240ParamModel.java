package jp.groupsession.v2.man.man240;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man240ParamModel extends AbstractParamModel {
    /** オペレーションログ設定リスト */
    private List<Man240BaseForm> man240LogConf__;

    /**
     * <br>[機  能] フォームパラメータをコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setParam(Object form) {
        super.setParam(form);

        setMan240LogConfList(((Man240Form) form).getMan240LogConfList());
    }
    /**
     * <br>[機  能] Modelの出力値をフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setFormData(Object form) {
        super.setFormData(form);

        ((Man240Form) form).setMan240LogConfForm(getMan240LogConfList());
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
    public void setMan240LogConfList(List<Man240BaseForm> logConf) {
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