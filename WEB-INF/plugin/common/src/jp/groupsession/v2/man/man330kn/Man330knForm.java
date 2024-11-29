package jp.groupsession.v2.man.man330kn;

import java.util.List;

import jp.groupsession.v2.man.man330.Man330Form;
import jp.groupsession.v2.man.man330kn.model.Man330knCsvModel;

/**
 * <br>[機  能] メイン 所属情報一括設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330knForm extends Man330Form {

    /** 取込みファイル名 */
    private String man330knFileName__ = null;
    /** 取込み予定ユーザ情報 */
    private List<Man330knCsvModel> man330knImpList__ = null;
    /**
     * <p>man330knImpList を取得します。
     * @return man330knImpList
     */
    public List<Man330knCsvModel> getMan330knImpList() {
        return man330knImpList__;
    }
    /**
     * <p>man330knImpList をセットします。
     * @param man330knImpList man330knImpList
     */
    public void setMan330knImpList(List<Man330knCsvModel> man330knImpList) {
        man330knImpList__ = man330knImpList;
    }
    /**
     * <p>man330knFileName を取得します。
     * @return man330knFileName
     */
    public String getMan330knFileName() {
        return man330knFileName__;
    }
    /**
     * <p>man330knFileName をセットします。
     * @param man330knFileName man330knFileName
     */
    public void setMan330knFileName(String man330knFileName) {
        man330knFileName__ = man330knFileName;
    }
}
