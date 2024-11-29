package jp.groupsession.v2.man.man112kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.man.man112.Man112Form;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man112knForm extends Man112Form {

    /** 取込みファイル名 */
    private String man112knFileName__ = null;
    /** 取込み予定グループ情報 */
    private List<CmnPositionModel> man112knImpList__ = null;

    /**
     * <p>man112knFileName を取得します。
     * @return man112knFileName
     */
    public String getMan112knFileName() {
        return man112knFileName__;
    }
    /**
     * <p>man112knFileName をセットします。
     * @param man112knFileName man112knFileName
     */
    public void setMan112knFileName(String man112knFileName) {
        man112knFileName__ = man112knFileName;
    }
    /**
     * <p>man112knImpList を取得します。
     * @return man112knImpList
     */
    public List<CmnPositionModel> getMan112knImpList() {
        return man112knImpList__;
    }
    /**
     * <p>man112knImpList をセットします。
     * @param man112knImpList man112knImpList
     */
    public void setMan112knImpList(List<CmnPositionModel> man112knImpList) {
        man112knImpList__ = man112knImpList;
    }

}