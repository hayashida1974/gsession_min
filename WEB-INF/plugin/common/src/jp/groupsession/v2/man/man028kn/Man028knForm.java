package jp.groupsession.v2.man.man028kn;

import jp.groupsession.v2.man.man028.Man028Form;

/**
 * <br>[機  能] メイン 管理者設定 休日設定インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man028knForm extends Man028Form {

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