package jp.groupsession.v2.cmn.formmodel.action;

import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;

/**
*
* <br>[機  能] グループ選択ダイアログ フォーム
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class GroupSelDialogForm extends AbstractForm {
    /**グループ選択*/
    private GroupComboModel grpsel__ = new GroupComboModel(UserGroupSelectModel.FLG_MULTI_ON);
    /** ピッカー用表示フラグ */
    private int pickerFlg__ = 0;
    /** 結果用表示フラグ */
    private String resultFlg__ = "";

    /**
     * <p>grpSel を取得します。
     * @return grpSel
     * @see jp.groupsession.v2.cmn.formmodel.action.GroupSelDialogForm#grpSel
     */
    public GroupComboModel getGrpsel() {
        return grpsel__;
    }

    /**
     * <p>grpSel をセットします。
     * @param grpsel grpSel
     * @see jp.groupsession.v2.cmn.formmodel.action.GroupSelDialogForm#grpSel
     */
    public void setGrpsel(GroupComboModel grpsel) {
        grpsel__ = grpsel;
    }

    /**
     * <p>pickerFlg を取得します。
     * @return pickerFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.GroupSelDialogForm#pickerFlg__
     */
    public int getPickerFlg() {
        return pickerFlg__;
    }

    /**
     * <p>pickerFlg をセットします。
     * @param pickerFlg pickerFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.GroupSelDialogForm#pickerFlg__
     */
    public void setPickerFlg(int pickerFlg) {
        pickerFlg__ = pickerFlg;
    }

    /**
     * <p>resultFlg を取得します。
     * @return resultFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.GroupSelDialogForm#resultFlg__
     */
    public String getResultFlg() {
        return resultFlg__;
    }

    /**
     * <p>resultFlg をセットします。
     * @param resultFlg resultFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.GroupSelDialogForm#resultFlg__
     */
    public void setResultFlg(String resultFlg) {
        resultFlg__ = resultFlg;
    }
}
