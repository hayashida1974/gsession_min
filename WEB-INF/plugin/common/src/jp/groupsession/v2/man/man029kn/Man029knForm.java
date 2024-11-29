package jp.groupsession.v2.man.man029kn;

import jp.groupsession.v2.man.man029.Man029Form;

/**
 * <br>[機  能] メイン 管理者設定 休日設定インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man029knForm extends Man029Form {

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