package jp.groupsession.v2.man.man029kn;

import jp.groupsession.v2.man.man029.Man029ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 休日設定インポート確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man029knParamModel extends Man029ParamModel {
    /** 取込みファイル名称 */
    private String man029knFileName__ = null;

    /**
     * @return man029knFileName
     */
    public String getMan029knFileName() {
        return man029knFileName__;
    }

    /**
     * @param man029knFileName セットする man029knFileName
     */
    public void setMan029knFileName(String man029knFileName) {
        man029knFileName__ = man029knFileName;
    }
}