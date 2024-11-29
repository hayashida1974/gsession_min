package jp.groupsession.v2.man.man060kn;

import jp.groupsession.v2.man.man060.Man060ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 ディスク容量管理確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man060knParamModel extends Man060ParamModel {
    /** 画面表示用警告文字列 */
    private String man060knwarnStr__ = null;

    /**
     * <p>man060knwarnStr を取得します。
     * @return man060knwarnStr
     */
    public String getMan060knwarnStr() {
        return man060knwarnStr__;
    }
    /**
     * <p>man060knwarnStr をセットします。
     * @param man060knwarnStr man060knwarnStr
     */
    public void setMan060knwarnStr(String man060knwarnStr) {
        man060knwarnStr__ = man060knwarnStr;
    }
}