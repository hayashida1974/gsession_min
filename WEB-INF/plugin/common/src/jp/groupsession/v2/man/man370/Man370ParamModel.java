package jp.groupsession.v2.man.man370;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.base.CmnLangModel;

/**
 * <br>[機  能] メイン 言語変更画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man370ParamModel extends AbstractParamModel {
    /** 選択言語 */
    private String man370SelectLang__ = "JP";
    /** 言語リスト */
    private List<CmnLangModel> man370LangList__;
    /**
     * <p>man370SelectLang を取得します。
     * @return man370SelectLang
     */
    public String getMan370SelectLang() {
        return man370SelectLang__;
    }
    /**
     * <p>man370SelectLang をセットします。
     * @param man370SelectLang man370SelectLang
     */
    public void setMan370SelectLang(String man370SelectLang) {
        man370SelectLang__ = man370SelectLang;
    }
    /**
     * <p>man370LangList を取得します。
     * @return man370LangList
     */
    public List<CmnLangModel> getMan370LangList() {
        return man370LangList__;
    }
    /**
     * <p>man370LangList をセットします。
     * @param man370LangList man370LangList
     */
    public void setMan370LangList(List<CmnLangModel> man370LangList) {
        man370LangList__ = man370LangList;
    }
}