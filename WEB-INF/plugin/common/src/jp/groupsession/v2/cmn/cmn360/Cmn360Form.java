package jp.groupsession.v2.cmn.cmn360;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] 全グループから選択ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn360Form extends AbstractGsForm {
    /** セレクター参照名 */
    private String targetSelector__;

    /** 呼び出し元FormClass */
    private String formClsName__;


    /** セレクター表示コマンド */
    private String selectorDispCmd__;

    /** 表示コマンド*/
    private String selectorCmd__;
    /** ユーザ選択要素*/
    private ISelector selector__;

    /** 詳細検索用 フィルター項目 */
    private String[] multiselectorFilter__;

    /** 詳細検索用 フィルター 役職リスト */
    List<CmnPositionModel> filterPositionList__ = new ArrayList<CmnPositionModel>();
    /** 詳細検索用 フィルター ラベルリスト */
    List<CmnLabelUsrModel> filterLabelList__ = new ArrayList<CmnLabelUsrModel>();

    /**
     * <p>cmn360Cmd を取得します。
     * @return cmn360Cmd
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#selectorCmd__
     */
    public String getSelectorCmd() {
        return selectorCmd__;
    }

    /**
     * <p>cmn360Cmd をセットします。
     * @param cmn360Cmd cmn360Cmd
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#selectorCmd__
     */
    public void setSelectorCmd(String cmn360Cmd) {
        selectorCmd__ = cmn360Cmd;
    }

    /**
     * <p>selector を取得します。
     * @return selector
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#selector__
     */
    public ISelector getSelector() {
        return selector__;
    }



    /**
     * <p>selector をセットします。
     * @param selector selector
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#selector__
     */
    public void setSelector(ISelector selector) {
        selector__ = selector;
    }


    /**
     * <p>targetSelector を取得します。
     * @return targetSelector
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#targetSelector__
     */
    public String getTargetSelector() {
        return targetSelector__;
    }


    /**
     * <p>targetSelector をセットします。
     * @param targetSelector targetSelector
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#targetSelector__
     */
    public void setTargetSelector(String targetSelector) {
        targetSelector__ = targetSelector;
    }


    /**
     * <p>selectorDispCmd を取得します。
     * @return selectorDispCmd
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#selectorDispCmd__
     */
    public String getSelectorDispCmd() {
        return selectorDispCmd__;
    }


    /**
     * <p>selectorDispCmd をセットします。
     * @param selectorDispCmd selectorDispCmd
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#selectorDispCmd__
     */
    public void setSelectorDispCmd(String selectorDispCmd) {
        selectorDispCmd__ = selectorDispCmd;
    }


    /**
     * <p>formClsName を取得します。
     * @return formClsName
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#formClsName__
     */
    public String getFormClsName() {
        return formClsName__;
    }


    /**
     * <p>formClsName をセットします。
     * @param formClsName formClsName
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#formClsName__
     */
    public void setFormClsName(String formClsName) {
        formClsName__ = formClsName;
    }

    /**
     * <p>multiselectorFilter を取得します。
     * @return multiselectorFilter
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#multiselectorFilter__
     */
    public String[] getMultiselectorFilter() {
        return multiselectorFilter__;
    }



    /**
     * <p>multiselectorFilter をセットします。
     * @param multiselectorFilter multiselectorFilter
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#multiselectorFilter__
     */
    public void setMultiselectorFilter(String[] multiselectorFilter) {
        multiselectorFilter__ = multiselectorFilter;
    }
    /**
     * <p>filterPositionList を取得します。
     * @return filterPositionList
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#filterPositionList__
     */
    public List<CmnPositionModel> getFilterPositionList() {
        return filterPositionList__;
    }

    /**
     * <p>filterPositionList をセットします。
     * @param filterPositionList filterPositionList
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#filterPositionList__
     */
    public void setFilterPositionList(List<CmnPositionModel> filterPositionList) {
        filterPositionList__ = filterPositionList;
    }

    /**
     * <p>filterLabelList を取得します。
     * @return filterLabelList
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#filterLabelList__
     */
    public List<CmnLabelUsrModel> getFilterLabelList() {
        return filterLabelList__;
    }

    /**
     * <p>filterLabelList をセットします。
     * @param filterLabelList filterLabelList
     * @see jp.groupsession.v2.cmn.cmn360.Cmn360Form#filterLabelList__
     */
    public void setFilterLabelList(List<CmnLabelUsrModel> filterLabelList) {
        filterLabelList__ = filterLabelList;
    }

}