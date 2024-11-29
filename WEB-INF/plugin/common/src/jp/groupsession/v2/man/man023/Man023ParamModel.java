package jp.groupsession.v2.man.man023;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] メイン 管理者設定 休日テンプレート一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man023ParamModel extends AbstractParamModel {
    /** 年度 */
    private String man020DspYear__ = null;

    /** 休日テンプレート一覧リストデータ */
    private Man023HolidayTemplateModel[] man023TemplateList__ = null;

    /** 休日テンプレートSID */
    private int editHltSid__ = -1;

    /** 休日テンプレート全選択 */
    private int man023CheckAll__ = 0;

    /** 休日テンプレート選択 */
    private int[] man023hltSid__ = null;

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
     * @return man023TemplateList__ を戻します。
     */
    public Man023HolidayTemplateModel[] getMan023TemplateList() {
        return man023TemplateList__;
    }

    /**
     * @param man023TemplateList 設定する man023TemplateList__。
     */
    public void setMan023TemplateList(Man023HolidayTemplateModel[] man023TemplateList) {
        this.man023TemplateList__ = man023TemplateList;
    }

    /**
     * @return editHltSid__ を戻します。
     */
    public int getEditHltSid() {
        return editHltSid__;
    }

    /**
     * @param editHltSid 設定する editHltSid__。
     */
    public void setEditHltSid(int editHltSid) {
        this.editHltSid__ = editHltSid;
    }

    /**
     * @return man023CheckAll__ を戻します。
     */
    public int getMan023CheckAll() {
        return man023CheckAll__;
    }

    /**
     * @param man023CheckAll 設定する man023CheckAll__。
     */
    public void setMan023CheckAll(int man023CheckAll) {
        this.man023CheckAll__ = man023CheckAll;
    }

    /**
     * @return man023hltSid__ を戻します。
     */
    public int[] getMan023hltSid() {
        return man023hltSid__;
    }

    /**
     * @param man023hltSid 設定する man023hltSid__。
     */
    public void setMan023hltSid(int[] man023hltSid) {
        this.man023hltSid__ = man023hltSid;
    }
}