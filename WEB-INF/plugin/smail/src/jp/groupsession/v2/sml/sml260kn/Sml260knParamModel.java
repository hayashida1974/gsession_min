package jp.groupsession.v2.sml.sml260kn;

import java.util.List;

import jp.groupsession.v2.sml.sml260.Sml260ParamModel;

/**
 * <br>[機  能] ショートメール アカウントインポート確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260knParamModel extends Sml260ParamModel {
    /** 取込ファイル名 */
    private String sml260knFileName__ = null;
    /** 使用者リスト */
    private List<Sml260knUseUsrModel> sml260knUseUserList__ = null;

    /**
     * <p>sml260knUseUserList を取得します。
     * @return sml260knUseUserList
     */
    public List<Sml260knUseUsrModel> getSml260knUseUserList() {
        return sml260knUseUserList__;
    }
    /**
     * <p>sml260knUseUserList をセットします。
     * @param sml260knUseUserList sml260knUseUserList
     */
    public void setSml260knUseUserList(List<Sml260knUseUsrModel> sml260knUseUserList) {
        sml260knUseUserList__ = sml260knUseUserList;
    }
    /**
     * <p>sml260knFileName を取得します。
     * @return sml260knFileName
     */
    public String getSml260knFileName() {
        return sml260knFileName__;
    }
    /**
     * <p>sml260knFileName をセットします。
     * @param sml260knFileName sml260knFileName
     */
    public void setSml260knFileName(String sml260knFileName) {
        sml260knFileName__ = sml260knFileName;
    }
}