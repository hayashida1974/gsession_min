package jp.groupsession.v2.wml.wml240;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;
import jp.groupsession.v2.wml.wml030.Wml030ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール メールテンプレート管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml240ParamModel extends Wml030ParamModel {

    /** テンプレート編集モード */
    private int wmlTemplateCmdMode__ = GSConstWebmail.CMDMODE_ADD;
    /** 編集対象テンプレートSID */
    private int wmlEditTemplateId__ = 0;
    /** アカウント名 */
    private String wml240accountName__ = null;
    /** チェックされている並び順 */
    private int wml240SortRadio__ = 0;
    /** 表示回数 */
    private int dspCount__ = 0;

    /** アカウントリスト */
    private List<LabelValueBean> accountCombo__ = null;
    /** テンプレートリスト */
    private List<WmlMailTemplateModel> templateList__ = null;

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
        return errors;
    }

    /**
     * <p>wml240accountName を取得します。
     * @return wml240accountName
     */
    public String getWml240accountName() {
        return wml240accountName__;
    }

    /**
     * <p>wml240accountName をセットします。
     * @param wml240accountName wml240accountName
     */
    public void setWml240accountName(String wml240accountName) {
        wml240accountName__ = wml240accountName;
    }

    /**
     * <p>templateList を取得します。
     * @return templateList
     */
    public List<WmlMailTemplateModel> getTemplateList() {
        return templateList__;
    }
    /**
     * <p>templateList をセットします。
     * @param templateList templateList
     */
    public void setTemplateList(List<WmlMailTemplateModel> templateList) {
        templateList__ = templateList;
    }
    /**
     * <p>wmlEditTemplateId を取得します。
     * @return wmlEditTemplateId
     */
    public int getWmlEditTemplateId() {
        return wmlEditTemplateId__;
    }
    /**
     * <p>wmlEditTemplateId をセットします。
     * @param wmlEditTemplateId wmlEditTemplateId
     */
    public void setWmlEditTemplateId(int wmlEditTemplateId) {
        wmlEditTemplateId__ = wmlEditTemplateId;
    }
    /**
     * <p>wmlTemplateCmdMode を取得します。
     * @return wmlTemplateCmdMode
     */
    public int getWmlTemplateCmdMode() {
        return wmlTemplateCmdMode__;
    }
    /**
     * <p>wmlTemplateCmdMode をセットします。
     * @param wmlTemplateCmdMode wmlTemplateCmdMode
     */
    public void setWmlTemplateCmdMode(int wmlTemplateCmdMode) {
        wmlTemplateCmdMode__ = wmlTemplateCmdMode;
    }

    /**
     * <p>wml240SortRadio を取得します。
     * @return wml240SortRadio
     */
    public int getWml240SortRadio() {
        return wml240SortRadio__;
    }

    /**
     * <p>wml240SortRadio をセットします。
     * @param wml240SortRadio wml240SortRadio
     */
    public void setWml240SortRadio(int wml240SortRadio) {
        wml240SortRadio__ = wml240SortRadio;
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
     * <p>accountCombo を取得します。
     * @return accountCombo
     */
    public List<LabelValueBean> getAccountCombo() {
        return accountCombo__;
    }

    /**
     * <p>accountCombo をセットします。
     * @param accountCombo accountCombo
     */
    public void setAccountCombo(List<LabelValueBean> accountCombo) {
        accountCombo__ = accountCombo;
    }
}