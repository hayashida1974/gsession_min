package jp.groupsession.v2.usr.usr013kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.usr.usr013.Usr013ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 グループインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr013knParamModel extends Usr013ParamModel {

    /** 取込みファイル名 */
    private String usr013knFileName__ = null;
    /** 取込み予定グループ情報 */
    private List<CmnGroupmModel> usr013knImpList__ = null;

    /**
     * <p>usr013knImpList を取得します。
     * @return usr013knImpList
     */
    public List<CmnGroupmModel> getUsr013knImpList() {
        return usr013knImpList__;
    }
    /**
     * <p>usr013knImpList をセットします。
     * @param usr013knImpList usr013knImpList
     */
    public void setUsr013knImpList(List<CmnGroupmModel> usr013knImpList) {
        usr013knImpList__ = usr013knImpList;
    }
    /**
     * <p>usr013knFileName を取得します。
     * @return usr013knFileName
     */
    public String getUsr013knFileName() {
        return usr013knFileName__;
    }
    /**
     * <p>usr013knFileName をセットします。
     * @param usr013knFileName usr013knFileName
     */
    public void setUsr013knFileName(String usr013knFileName) {
        usr013knFileName__ = usr013knFileName;
    }
}