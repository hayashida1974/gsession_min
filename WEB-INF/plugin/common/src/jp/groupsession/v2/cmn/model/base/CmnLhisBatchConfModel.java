package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ログイン履歴自動削除設定を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnLhisBatchConfModel extends AbstractModel {

    /** CBC_ADL_KBN mappin */
    private int cbcAdlKbn__;
    /** CBC_ADL_YEAR mapping */
    private int cbcAdlYear__;
    /** CBC_ADL_MONTH mapping */
    private int cbcAdlMonth__;
    /** CBC_AUID mapping */
    private int cbcAuid__;
    /** CBC_ADATE mapping */
    private UDate cbcAdate__;
    /** CBC_EUID mapping */
    private int cbcEuid__;
    /** CBC_EDATE mapping */
    private UDate cbcEdate__;

    /**
     * <p>cbcAdlKbn を取得します。
     * @return cbcAdlKbn
     */
    public int getCbcAdlKbn() {
        return cbcAdlKbn__;
    }
    /**
     * <p>cbcAdlKbn をセットします。
     * @param cbcAdlKbn cbcAdlKbn
     */
    public void setCbcAdlKbn(int cbcAdlKbn) {
        cbcAdlKbn__ = cbcAdlKbn;
    }
    /**
     * <p>cbcAdlYear を取得します。
     * @return cbcAdlYear
     */
    public int getCbcAdlYear() {
        return cbcAdlYear__;
    }
    /**
     * <p>cbcAdlYear をセットします。
     * @param cbcAdlYear cbcAdlYear
     */
    public void setCbcAdlYear(int cbcAdlYear) {
        cbcAdlYear__ = cbcAdlYear;
    }
    /**
     * <p>cbcAdlMonth を取得します。
     * @return cbcAdlMonth
     */
    public int getCbcAdlMonth() {
        return cbcAdlMonth__;
    }
    /**
     * <p>cbcAdlMonth をセットします。
     * @param cbcAdlMonth cbcAdlMonth
     */
    public void setCbcAdlMonth(int cbcAdlMonth) {
        cbcAdlMonth__ = cbcAdlMonth;
    }
    /**
     * <p>cbcAuid を取得します。
     * @return cbcAuid
     */
    public int getCbcAuid() {
        return cbcAuid__;
    }
    /**
     * <p>cbcAuid をセットします。
     * @param cbcAuid cbcAuid
     */
    public void setCbcAuid(int cbcAuid) {
        cbcAuid__ = cbcAuid;
    }
    /**
     * <p>cbcAdate を取得します。
     * @return cbcAdate
     */
    public UDate getCbcAdate() {
        return cbcAdate__;
    }
    /**
     * <p>cbcAdate をセットします。
     * @param cbcAdate cbcAdate
     */
    public void setCbcAdate(UDate cbcAdate) {
        cbcAdate__ = cbcAdate;
    }
    /**
     * <p>cbcEuid を取得します。
     * @return cbcEuid
     */
    public int getCbcEuid() {
        return cbcEuid__;
    }
    /**
     * <p>cbcEuid をセットします。
     * @param cbcEuid cbcEuid
     */
    public void setCbcEuid(int cbcEuid) {
        cbcEuid__ = cbcEuid;
    }
    /**
     * <p>cbcEdate を取得します。
     * @return cbcEdate
     */
    public UDate getCbcEdate() {
        return cbcEdate__;
    }
    /**
     * <p>cbcEdate をセットします。
     * @param cbcEdate cbcEdate
     */
    public void setCbcEdate(UDate cbcEdate) {
        cbcEdate__ = cbcEdate;
    }
}