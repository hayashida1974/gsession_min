package jp.groupsession.v2.cmn.ui.parts.sortingselect;

import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorParamForm;

public abstract class AbstractSortingSelector extends AbstractSelector {
    protected AbstractSortingSelector(SelectorParamForm param) {
        super(param);
    }
    /** 並び替え中フラグ*/
    int sortingFlg__ = 0;

    /**
     * <p>sortingFlg を取得します。
     * @return sortingFlg
     * @see jp.groupsession.v2.cmn.ui.parts.sortingselect.AbstractSortingSelector#sortingFlg__
     */
    public int getSortingFlg() {
        return sortingFlg__;
    }

    /**
     * <p>sortingFlg をセットします。
     * @param sortingFlg sortingFlg
     * @see jp.groupsession.v2.cmn.ui.parts.sortingselect.AbstractSortingSelector#sortingFlg__
     */
    public void setSortingFlg(int sortingFlg) {
        sortingFlg__ = sortingFlg;
    }
}
