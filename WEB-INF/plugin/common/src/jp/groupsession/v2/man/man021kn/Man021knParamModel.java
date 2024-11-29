package jp.groupsession.v2.man.man021kn;

import jp.groupsession.v2.man.man021.Man021ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 休日設定/追加確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man021knParamModel extends Man021ParamModel {
    /** 日付(表示用) */
    private String man021knHolDate__ = null;

    /**
     * @return man021knHolDate を戻します。
     */
    public String getMan021knHolDate() {
        return man021knHolDate__;
    }

    /**
     * @param man021knHolDate 設定する man021knHolDate。
     */
    public void setMan021knHolDate(String man021knHolDate) {
        man021knHolDate__ = man021knHolDate;
    }
}