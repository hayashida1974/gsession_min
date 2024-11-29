package jp.groupsession.v2.cmn.formmodel.action;

import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
/**
 *
 * <br>[機  能] ユーザ選択ダイアログ フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsrGrpSelDialogForm extends AbstractForm {
    /**ユーザ選択*/
    private UserGroupSelectModel usrgrpsel__ = new UserGroupSelectModel();
    /** ピッカー用表示フラグ */
    private int pickerFlg__ = 0;
    /** 結果用表示フラグ */
    private String resultFlg__ = "";

    /**
     * <p>usrgrpsel を取得します。
     * @return usrgrpsel
     */
    public UserGroupSelectModel getUsrgrpsel() {
        return usrgrpsel__;
    }

    /**
     * <p>usrgrpsel をセットします。
     * @param usrgrpsel usrgrpsel
     */
    public void setUsrgrpsel(UserGroupSelectModel usrgrpsel) {
        usrgrpsel__ = usrgrpsel;
    }

    /**
     * <p>pickerFlg を取得します。
     * @return pickerFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.UsrGrpSelDialogForm#pickerFlg__
     */
    public int getPickerFlg() {
        return pickerFlg__;
    }

    /**
     * <p>pickerFlg をセットします。
     * @param pickerFlg pickerFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.UsrGrpSelDialogForm#pickerFlg__
     */
    public void setPickerFlg(int pickerFlg) {
        pickerFlg__ = pickerFlg;
    }

    /**
     * <p>resultFlg を取得します。
     * @return resultFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.UsrGrpSelDialogForm#resultFlg__
     */
    public String getResultFlg() {
        return resultFlg__;
    }

    /**
     * <p>resultFlg をセットします。
     * @param resultFlg resultFlg
     * @see jp.groupsession.v2.cmn.formmodel.action.UsrGrpSelDialogForm#resultFlg__
     */
    public void setResultFlg(String resultFlg) {
        resultFlg__ = resultFlg;
    }

}
