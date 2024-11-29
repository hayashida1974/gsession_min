package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_BELONGM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBelongmHistoryModel extends CmnBelongmModel {
    /** BEG_DATE mapping */
    private UDate begDate__;

    /**
     * <p>begDate を取得します。
     * @return begDate
     */
    public UDate getBegDate() {
        return begDate__;
    }

    /**
     * <p>begDate をセットします。
     * @param begDate begDate
     */
    public void setBegDate(UDate begDate) {
        begDate__ = begDate;
    }
}
