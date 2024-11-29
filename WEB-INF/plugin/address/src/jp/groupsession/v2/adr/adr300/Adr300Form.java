package jp.groupsession.v2.adr.adr300;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr030.Adr030Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 管理者設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr300Form extends Adr030Form {
    /** 表示設定区分 */
    private int adr300MemDspKbn__ = GSConstAddress.MEM_DSP_ADM;
    /** 閲覧区分 */
    private int adr300PermitKbn__ = GSConst.ADR_VIEWPERMIT_OWN;
    /** 編集区分 */
    private int adr300EditKbn__ = GSConstAddress.EDITPERMIT_OWN;
    /** 閲覧 ラベル */
    private List<LabelValueBean> adr300PermitKbnLabel__ = null;
    /** 編集 ラベル */
    private List<LabelValueBean> adr300EditKbnLabel__ = null;

    /**
     * <p>adr300MemDspKbn を取得します。
     * @return adr300MemDspKbn
     */
    public int getAdr300MemDspKbn() {
        return adr300MemDspKbn__;
    }

    /**
     * <p>adr300MemDspKbn をセットします。
     * @param adr300MemDspKbn adr300MemDspKbn
     */
    public void setAdr300MemDspKbn(int adr300MemDspKbn) {
        adr300MemDspKbn__ = adr300MemDspKbn;
    }

    /**
     * <p>adr300EditKbn を取得します。
     * @return adr300EditKbn
     */
    public int getAdr300EditKbn() {
        return adr300EditKbn__;
    }

    /**
     * <p>adr300EditKbn をセットします。
     * @param adr300EditKbn adr300EditKbn
     */
    public void setAdr300EditKbn(int adr300EditKbn) {
        adr300EditKbn__ = adr300EditKbn;
    }

    /**
     * <p>adr300EditKbnLabel を取得します。
     * @return adr300EditKbnLabel
     */
    public List<LabelValueBean> getAdr300EditKbnLabel() {
        return adr300EditKbnLabel__;
    }

    /**
     * <p>adr300EditKbnLabel をセットします。
     * @param adr300EditKbnLabel adr300EditKbnLabel
     */
    public void setAdr300EditKbnLabel(List<LabelValueBean> adr300EditKbnLabel) {
        adr300EditKbnLabel__ = adr300EditKbnLabel;
    }

    /**
     * <p>adr300PermitKbn を取得します。
     * @return adr300PermitKbn
     */
    public int getAdr300PermitKbn() {
        return adr300PermitKbn__;
    }

    /**
     * <p>adr300PermitKbn をセットします。
     * @param adr300PermitKbn adr300PermitKbn
     */
    public void setAdr300PermitKbn(int adr300PermitKbn) {
        adr300PermitKbn__ = adr300PermitKbn;
    }

    /**
     * <p>adr300PermitKbnLabel を取得します。
     * @return adr300PermitKbnLabel
     */
    public List<LabelValueBean> getAdr300PermitKbnLabel() {
        return adr300PermitKbnLabel__;
    }

    /**
     * <p>adr300PermitKbnLabel をセットします。
     * @param adr300PermitKbnLabel adr300PermitKbnLabel
     */
    public void setAdr300PermitKbnLabel(List<LabelValueBean> adr300PermitKbnLabel) {
        adr300PermitKbnLabel__ = adr300PermitKbnLabel;
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
        if (adr300MemDspKbn__ != GSConstAddress.MEM_DSP_ADM
        && adr300MemDspKbn__ != GSConstAddress.MEM_DSP_USR) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage(req, "address.src.40"));
            errors.add("adr300MemDspKbn" + "error.select3.required.text", msg);
        }

        //閲覧権限
        boolean pmkFlg = false;
        for (int key : GSConstAddress.VIEWPERMIT_ALL) {
            if (adr300PermitKbn__ == key) {
                pmkFlg = true;
                break;
            }
        }
        if (!pmkFlg) {
            msg = new ActionMessage("error.select3.required.text",
                                     gsMsg.getMessage(req, "address.137"));
            errors.add("adr300PermitKbn" + "error.select3.required.text", msg);
        }

        //編集権限
        boolean editFlg = false;
        for (int key : GSConstAddress.VIEWPERMIT_ALL) {
            if (adr300EditKbn__ == key) {
                editFlg = true;
                break;
            }
        }
        if (!editFlg) {
            msg = new ActionMessage("error.select3.required.text",
                                     gsMsg.getMessage(req, "address.138"));
            errors.add("adr300EditKbn" + "error.select3.required.text", msg);
        }

        return errors;
    }
}