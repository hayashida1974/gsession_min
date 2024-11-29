package jp.groupsession.v2.adr.adr310;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr030.Adr030ParamModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 個人設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr310ParamModel extends Adr030ParamModel {
    /** 表示設定区分 */
    private int adr310MemDspKbn__ = GSConstAddress.MEM_DSP_ADM;
    /** 閲覧区分 */
    private int adr310PermitKbn__ = GSConst.ADR_VIEWPERMIT_OWN;
    /** 編集区分 */
    private int adr310EditKbn__ = GSConst.ADR_VIEWPERMIT_NORESTRICTION;
    /** 閲覧 ラベル */
    private List<LabelValueBean> adr310PermitKbnLabel__ = null;
    /** 編集 ラベル */
    private List<LabelValueBean> adr310EditKbnLabel__ = null;

    /**
     * <p>adr310MemDspKbn を取得します。
     * @return adr310MemDspKbn
     */
    public int getAdr310MemDspKbn() {
        return adr310MemDspKbn__;
    }

    /**
     * <p>adr310MemDspKbn をセットします。
     * @param adr310MemDspKbn adr310MemDspKbn
     */
    public void setAdr310MemDspKbn(int adr310MemDspKbn) {
        adr310MemDspKbn__ = adr310MemDspKbn;
    }

    /**
     * <p>adr310EditKbn を取得します。
     * @return adr310EditKbn
     */
    public int getAdr310EditKbn() {
        return adr310EditKbn__;
    }

    /**
     * <p>adr310EditKbn をセットします。
     * @param adr310EditKbn adr310EditKbn
     */
    public void setAdr310EditKbn(int adr310EditKbn) {
        adr310EditKbn__ = adr310EditKbn;
    }

    /**
     * <p>adr310EditKbnLabel を取得します。
     * @return adr310EditKbnLabel
     */
    public List<LabelValueBean> getAdr310EditKbnLabel() {
        return adr310EditKbnLabel__;
    }

    /**
     * <p>adr310EditKbnLabel をセットします。
     * @param adr310EditKbnLabel adr310EditKbnLabel
     */
    public void setAdr310EditKbnLabel(List<LabelValueBean> adr310EditKbnLabel) {
        adr310EditKbnLabel__ = adr310EditKbnLabel;
    }

    /**
     * <p>adr310PermitKbn を取得します。
     * @return adr310PermitKbn
     */
    public int getAdr310PermitKbn() {
        return adr310PermitKbn__;
    }

    /**
     * <p>adr310PermitKbn をセットします。
     * @param adr310PermitKbn adr310PermitKbn
     */
    public void setAdr310PermitKbn(int adr310PermitKbn) {
        adr310PermitKbn__ = adr310PermitKbn;
    }

    /**
     * <p>adr310PermitKbnLabel を取得します。
     * @return adr310PermitKbnLabel
     */
    public List<LabelValueBean> getAdr310PermitKbnLabel() {
        return adr310PermitKbnLabel__;
    }

    /**
     * <p>adr310PermitKbnLabel をセットします。
     * @param adr310PermitKbnLabel adr310PermitKbnLabel
     */
    public void setAdr310PermitKbnLabel(List<LabelValueBean> adr310PermitKbnLabel) {
        adr310PermitKbnLabel__ = adr310PermitKbnLabel;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCheck(HttpServletRequest req) throws Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;

        //編集権限区分
        if (adr310MemDspKbn__ != GSConstAddress.MEM_DSP_ADM
        && adr310MemDspKbn__ != GSConstAddress.MEM_DSP_USR) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage(req, "address.src.40"));
            errors.add("adr310MemDspKbn" + "error.select3.required.text", msg);
        }

        //閲覧権限
        boolean pmkFlg = false;
        for (int key : GSConstAddress.VIEWPERMIT_ALL) {
            if (adr310PermitKbn__ == key) {
                pmkFlg = true;
                break;
            }
        }
        if (!pmkFlg) {
            msg = new ActionMessage("error.select3.required.text",
                                     gsMsg.getMessage(req, "address.137"));
            errors.add("adr310PermitKbn" + "error.select3.required.text", msg);
        }

        //編集権限
        boolean editFlg = false;
        for (int key : GSConstAddress.VIEWPERMIT_ALL) {
            if (adr310EditKbn__ == key) {
                editFlg = true;
                break;
            }
        }
        if (!editFlg) {
            msg = new ActionMessage("error.select3.required.text",
                                     gsMsg.getMessage(req, "address.138"));
            errors.add("adr310EditKbn" + "error.select3.required.text", msg);
        }

        return errors;
    }
}