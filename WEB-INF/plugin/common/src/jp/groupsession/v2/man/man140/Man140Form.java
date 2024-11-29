package jp.groupsession.v2.man.man140;

import java.util.List;

import jp.groupsession.v2.struts.AbstractGsForm;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 セッション保持時間設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man140Form extends AbstractGsForm {

    //入力項目
    /** セッション保持時間 選択値 */
    private int man140SessionTime__;

    //表示項目
    /** セッション保持時間ラベル */
    private List<LabelValueBean> man140SessionTimeLabel__;

    /**
     * <p>man140SessionTimeLabel を取得します。
     * @return man140SessionTimeLabel
     */
    public List<LabelValueBean> getMan140SessionTimeLabel() {
        return man140SessionTimeLabel__;
    }
    /**
     * <p>man140SessionTimeLabel をセットします。
     * @param man140SessionTimeLabel man140SessionTimeLabel
     */
    public void setMan140SessionTimeLabel(
            List<LabelValueBean> man140SessionTimeLabel) {
        man140SessionTimeLabel__ = man140SessionTimeLabel;
    }
    /**
     * <p>man140SessionTime を取得します。
     * @return man140SessionTime
     */
    public int getMan140SessionTime() {
        return man140SessionTime__;
    }
    /**
     * <p>man140SessionTime をセットします。
     * @param man140SessionTime man140SessionTime
     */
    public void setMan140SessionTime(int man140SessionTime) {
        man140SessionTime__ = man140SessionTime;
    }
}
