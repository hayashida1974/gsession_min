package jp.groupsession.v2.cmn.cmn200;

import jp.co.sjts.util.json.JSONArray;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ポップアップカレンダー休日取得のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn200Form  extends AbstractGsForm {

    /** 表示開始日付(MMdd形式) */
    private String cmn200dateStr__ = null;
    /** 取得範囲(年指定) */
    private int cmn200dateRange__ = 0;
    /** 休日JsonObject */
    private JSONArray cmn200JsonArray__ = null;
    /**
     * <p>cmn200JsonArray を取得します。
     * @return cmn200JsonArray
     */
    public JSONArray getCmn200JsonArray() {
        return cmn200JsonArray__;
    }
    /**
     * <p>cmn200JsonArray をセットします。
     * @param cmn200JsonArray cmn200JsonArray
     */
    public void setCmn200JsonArray(JSONArray cmn200JsonArray) {
        cmn200JsonArray__ = cmn200JsonArray;
    }
    /**
     * <p>cmn200dateStr を取得します。
     * @return cmn200dateStr
     */
    public String getCmn200dateStr() {
        return cmn200dateStr__;
    }
    /**
     * <p>cmn200dateStr をセットします。
     * @param cmn200dateStr cmn200dateStr
     */
    public void setCmn200dateStr(String cmn200dateStr) {
        cmn200dateStr__ = cmn200dateStr;
    }
    /**
     * <p>cmn200dateRange を取得します。
     * @return cmn200dateRange
     * @see jp.groupsession.v2.cmn.cmn200.Cmn200Form#cmn200dateRange__
     */
    public int getCmn200dateRange() {
        return cmn200dateRange__;
    }
    /**
     * <p>cmn200dateRange をセットします。
     * @param cmn200dateRange cmn200dateRange
     * @see jp.groupsession.v2.cmn.cmn200.Cmn200Form#cmn200dateRange__
     */
    public void setCmn200dateRange(int cmn200dateRange) {
        cmn200dateRange__ = cmn200dateRange;
    }

}
