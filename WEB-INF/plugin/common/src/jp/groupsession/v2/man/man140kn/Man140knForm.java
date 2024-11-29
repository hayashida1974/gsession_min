package jp.groupsession.v2.man.man140kn;

import jp.groupsession.v2.man.man140.Man140Form;

/**
 * <br>[機  能] メイン 管理者設定 セッション保持時間設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man140knForm extends Man140Form {

    //表示項目
    /** セッション保持時間 */
    private String man140knSessionTime__;

    /**
     * <p>man140knSessionTime を取得します。
     * @return man140knSessionTime
     */
    public String getMan140knSessionTime() {
        return man140knSessionTime__;
    }

    /**
     * <p>man140knSessionTime をセットします。
     * @param man140knSessionTime man140knSessionTime
     */
    public void setMan140knSessionTime(String man140knSessionTime) {
        man140knSessionTime__ = man140knSessionTime;
    }

}
