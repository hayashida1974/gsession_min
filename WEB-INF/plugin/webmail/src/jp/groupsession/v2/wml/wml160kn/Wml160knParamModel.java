package jp.groupsession.v2.wml.wml160kn;

import java.util.List;

import jp.groupsession.v2.wml.wml160.Wml160ParamModel;

/**
 * <br>[機  能] WEBメール アカウントインポート確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160knParamModel extends Wml160ParamModel {
    /** 取込ファイル名 */
    private String wml160knFileName__ = null;
    /** 使用者 代理人リスト */
    private List<Wml160knUseUsrModel> wml160knUseUserList__ = null;

    /**
     * <p>wml160knUseUserList を取得します。
     * @return wml160knUseUserList
     */
    public List<Wml160knUseUsrModel> getWml160knUseUserList() {
        return wml160knUseUserList__;
    }
    /**
     * <p>wml160knUseUserList をセットします。
     * @param wml160knUseUserList wml160knUseUserList
     */
    public void setWml160knUseUserList(List<Wml160knUseUsrModel> wml160knUseUserList) {
        wml160knUseUserList__ = wml160knUseUserList;
    }
    /**
     * <p>wml160knFileName を取得します。
     * @return wml160knFileName
     */
    public String getWml160knFileName() {
        return wml160knFileName__;
    }
    /**
     * <p>wml160knFileName をセットします。
     * @param wml160knFileName wml160knFileName
     */
    public void setWml160knFileName(String wml160knFileName) {
        wml160knFileName__ = wml160knFileName;
    }
}