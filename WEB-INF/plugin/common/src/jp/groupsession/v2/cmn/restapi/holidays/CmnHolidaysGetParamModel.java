package jp.groupsession.v2.cmn.restapi.holidays;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
/**
 *
 * <br>[機  能] 休日情報API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class CmnHolidaysGetParamModel  {
    /** 取得開始日 */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate fromDate__;
    /** 取得終了日*/
    @UDateFormat(Format.DATE_SLUSH)
    private UDate toDate__;
    /**
     * <p>fromDate を取得します。
     * @return fromDate
     * @see jp.groupsession.v2.cmn.restapi.holidays.CmnHolidaysGetParamModel#fromDate__
     */
    public UDate getFromDate() {
        return fromDate__;
    }
    /**
     * <p>fromDate をセットします。
     * @param fromDate fromDate
     * @see jp.groupsession.v2.cmn.restapi.holidays.CmnHolidaysGetParamModel#fromDate__
     */
    public void setFromDate(UDate fromDate) {
        fromDate__ = fromDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     * @see jp.groupsession.v2.cmn.restapi.holidays.CmnHolidaysGetParamModel#toDate__
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     * @see jp.groupsession.v2.cmn.restapi.holidays.CmnHolidaysGetParamModel#toDate__
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }
}
