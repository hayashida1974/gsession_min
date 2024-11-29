package jp.groupsession.v2.man.man028kn;

import jp.groupsession.v2.man.man028.Man028ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 休日設定インポート確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man028knParamModel extends Man028ParamModel {
    /** 取込みファイル名称 */
    private String man028knFileName__ = null;

    /**
     * @return man028knFileName
     */
    public String getMan028knFileName() {
        return man028knFileName__;
    }

    /**
     * @param man028knFileName セットする man028knFileName
     */
    public void setMan028knFileName(String man028knFileName) {
        man028knFileName__ = man028knFileName;
    }
}