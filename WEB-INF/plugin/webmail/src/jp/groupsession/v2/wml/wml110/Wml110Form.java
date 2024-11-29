package jp.groupsession.v2.wml.wml110;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.wml090.Wml090Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール ラベルの管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml110Form extends Wml090Form {

    /** ラベル編集モード */
    private int wmlLabelCmdMode__ = GSConstWebmail.CMDMODE_ADD;
    /** 編集対象ラベルSID */
    private int wmlEditLabelId__ = 0;
    /** アカウントリスト */
    private List<LabelValueBean> acntList__ = null;
    /** ラベルリスト */
    private List<LabelDataModel> lbnList__ = null;
    /** アカウント */
    private String wml110account__ = null;
    /** 選択 アカウントSID */
    private int wml110accountSid__ = -1;
    /** 編集対象ラベルSID */
    private String wml110editLabel__ = null;
    /** 並び替え対象のラベル */
    private String[] wml110sortLabel__ = null;
    /** チェックされている並び順 */
    private String wml110SortRadio__ = null;
    /** 表示回数 */
    private int dspCount__ = 0;

    /**
     * <br>[機  能] アカウント選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) throws Exception {
        ActionErrors errors = new ActionErrors();

        //アカウント選択チェック
        if (wml110accountSid__ == -1) {
            String fieldfix = "wml110accountSid.";
            String msgKey = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgKey, getInterMessage(req, "wml.28"));
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
        }
        return errors;
    }

    /**
     * <p>wml110account を取得します。
     * @return wml110account
     */
    public String getWml110account() {
        return wml110account__;
    }

    /**
     * <p>wml110account をセットします。
     * @param wml110account wml110account
     */
    public void setWml110account(String wml110account) {
        wml110account__ = wml110account;
    }
    /**
     * <p>acntList を取得します。
     * @return acntList
     */
    public List<LabelValueBean> getAcntList() {
        return acntList__;
    }
    /**
     * <p>acntList をセットします。
     * @param acntList acntList
     */
    public void setAcntList(List<LabelValueBean> acntList) {
        acntList__ = acntList;
    }
    /**
     * <p>lbnList を取得します。
     * @return lbnList
     */
    public List<LabelDataModel> getLbnList() {
        return lbnList__;
    }
    /**
     * <p>lbnList をセットします。
     * @param lbnList lbnList
     */
    public void setLbnList(List<LabelDataModel> lbnList) {
        lbnList__ = lbnList;
    }
    /**
     * <p>wmlEditLabelId を取得します。
     * @return wmlEditLabelId
     */
    public int getWmlEditLabelId() {
        return wmlEditLabelId__;
    }
    /**
     * <p>wmlEditLabelId をセットします。
     * @param wmlEditLabelId wmlEditLabelId
     */
    public void setWmlEditLabelId(int wmlEditLabelId) {
        wmlEditLabelId__ = wmlEditLabelId;
    }
    /**
     * <p>wmlLabelCmdMode を取得します。
     * @return wmlLabelCmdMode
     */
    public int getWmlLabelCmdMode() {
        return wmlLabelCmdMode__;
    }
    /**
     * <p>wmlLabelCmdMode をセットします。
     * @param wmlLabelCmdMode wmlLabelCmdMode
     */
    public void setWmlLabelCmdMode(int wmlLabelCmdMode) {
        wmlLabelCmdMode__ = wmlLabelCmdMode;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("wmlEditLabelId", getWmlEditLabelId());
    }

    /**
     * <p>wml110editLabel を取得します。
     * @return wml110editLabel
     */
    public String getWml110editLabel() {
        return wml110editLabel__;
    }

    /**
     * <p>wml110editLabel をセットします。
     * @param wml110editLabel wml110editLabel
     */
    public void setWml110editLabel(String wml110editLabel) {
        wml110editLabel__ = wml110editLabel;
    }

    /**
     * <p>wml110sortLabel を取得します。
     * @return wml110sortLabel
     */
    public String[] getWml110sortLabel() {
        return wml110sortLabel__;
    }

    /**
     * <p>wml110sortLabel をセットします。
     * @param wml110sortLabel wml110sortLabel
     */
    public void setWml110sortLabel(String[] wml110sortLabel) {
        wml110sortLabel__ = wml110sortLabel;
    }

    /**
     * <p>wml110SortRadio を取得します。
     * @return wml110SortRadio
     */
    public String getWml110SortRadio() {
        return wml110SortRadio__;
    }

    /**
     * <p>wml110SortRadio をセットします。
     * @param wml110SortRadio wml110SortRadio
     */
    public void setWml110SortRadio(String wml110SortRadio) {
        wml110SortRadio__ = wml110SortRadio;
    }

    /**
     * <p>dspCount を取得します。
     * @return dspCount
     */
    public int getDspCount() {
        return dspCount__;
    }

    /**
     * <p>dspCount をセットします。
     * @param dspCount dspCount
     */
    public void setDspCount(int dspCount) {
        dspCount__ = dspCount;
    }

    /**
     * <p>wml110accountSid を取得します。
     * @return wml110accountSid
     */
    public int getWml110accountSid() {
        return wml110accountSid__;
    }

    /**
     * <p>wml110accountSid をセットします。
     * @param wml110accountSid wml110accountSid
     */
    public void setWml110accountSid(int wml110accountSid) {
        wml110accountSid__ = wml110accountSid;
    }

}
