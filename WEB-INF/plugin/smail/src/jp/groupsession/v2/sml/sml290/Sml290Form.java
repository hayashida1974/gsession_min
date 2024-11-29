package jp.groupsession.v2.sml.sml290;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.LabelDataModel;
import jp.groupsession.v2.sml.sml240.Sml240Form;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml290Form extends Sml240Form {

    /** ラベル編集モード */
    private int smlLabelCmdMode__ = GSConstSmail.CMDMODE_ADD;
    /** 編集対象ラベルSID */
    private int smlEditLabelId__ = 0;
    /** ラベルリスト */
    private List<LabelDataModel> lbnList__ = null;
    /** アカウント名 */
    private String sml290accountName__ = null;
    /** 編集対象ラベルSID */
    private String sml290editLabel__ = null;
    /** 並び替え対象のラベル */
    private String[] sml290sortLabel__ = null;
    /** チェックされている並び順 */
    private String sml290SortRadio__ = null;
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
        return errors;
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
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("smlEditLabelId", getSmlEditLabelId());
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
     * <p>smlLabelCmdMode を取得します。
     * @return smlLabelCmdMode
     */
    public int getSmlLabelCmdMode() {
        return smlLabelCmdMode__;
    }

    /**
     * <p>smlLabelCmdMode をセットします。
     * @param smlLabelCmdMode smlLabelCmdMode
     */
    public void setSmlLabelCmdMode(int smlLabelCmdMode) {
        smlLabelCmdMode__ = smlLabelCmdMode;
    }

    /**
     * <p>smlEditLabelId を取得します。
     * @return smlEditLabelId
     */
    public int getSmlEditLabelId() {
        return smlEditLabelId__;
    }

    /**
     * <p>smlEditLabelId をセットします。
     * @param smlEditLabelId smlEditLabelId
     */
    public void setSmlEditLabelId(int smlEditLabelId) {
        smlEditLabelId__ = smlEditLabelId;
    }

    /**
     * <p>sml290accountName を取得します。
     * @return sml290accountName
     */
    public String getSml290accountName() {
        return sml290accountName__;
    }

    /**
     * <p>sml290accountName をセットします。
     * @param sml290accountName sml290accountName
     */
    public void setSml290accountName(String sml290accountName) {
        sml290accountName__ = sml290accountName;
    }

    /**
     * <p>sml290editLabel を取得します。
     * @return sml290editLabel
     */
    public String getSml290editLabel() {
        return sml290editLabel__;
    }

    /**
     * <p>sml290editLabel をセットします。
     * @param sml290editLabel sml290editLabel
     */
    public void setSml290editLabel(String sml290editLabel) {
        sml290editLabel__ = sml290editLabel;
    }

    /**
     * <p>sml290sortLabel を取得します。
     * @return sml290sortLabel
     */
    public String[] getSml290sortLabel() {
        return sml290sortLabel__;
    }

    /**
     * <p>sml290sortLabel をセットします。
     * @param sml290sortLabel sml290sortLabel
     */
    public void setSml290sortLabel(String[] sml290sortLabel) {
        sml290sortLabel__ = sml290sortLabel;
    }

    /**
     * <p>sml290SortRadio を取得します。
     * @return sml290SortRadio
     */
    public String getSml290SortRadio() {
        return sml290SortRadio__;
    }

    /**
     * <p>sml290SortRadio をセットします。
     * @param sml290SortRadio sml290SortRadio
     */
    public void setSml290SortRadio(String sml290SortRadio) {
        sml290SortRadio__ = sml290SortRadio;
    }

}
