package jp.groupsession.v2.man.man020;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] メイン 管理者設定 休日設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man020ParamModel extends AbstractParamModel {
    /** 年度 */
    private String man020DspYear__ = null;
    /** 削除対象休日 */
    private String[] holDate__ = null;
    /** 修正対象休日 */
    private String editHolDate__ = null;

    /** 休日情報リスト */
    private List < Man020HolidayModel > man020HolList__ = null;

    /**
     * @return editHolDate を戻します。
     */
    public String getEditHolDate() {
        return editHolDate__;
    }

    /**
     * @param editHolDate 設定する editHolDate。
     */
    public void setEditHolDate(String editHolDate) {
        editHolDate__ = editHolDate;
    }

    /**
     * @return holDate を戻します。
     */
    public String[] getHolDate() {
        return holDate__;
    }

    /**
     * @param holDate 設定する holDate。
     */
    public void setHolDate(String[] holDate) {
        holDate__ = holDate;
    }

    /**
     * @return man020DspYear を戻します。
     */
    public String getMan020DspYear() {
        return man020DspYear__;
    }

    /**
     * @param man020DspYear 設定する man020DspYear。
     */
    public void setMan020DspYear(String man020DspYear) {
        man020DspYear__ = man020DspYear;
    }

    /**
     * @return man020HolList を戻します。
     */
    public List < Man020HolidayModel > getMan020HolList() {
        return man020HolList__;
    }

    /**
     * @param man020HolList 設定する man020HolList。
     */
    public void setMan020HolList(List < Man020HolidayModel > man020HolList) {
        man020HolList__ = man020HolList;
    }
}