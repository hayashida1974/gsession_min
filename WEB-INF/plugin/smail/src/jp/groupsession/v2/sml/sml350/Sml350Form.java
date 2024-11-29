package jp.groupsession.v2.sml.sml350;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.sml270.Sml270Form;
import jp.groupsession.v2.sml.sml330.Sml330FilterDataModel;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] ショートメール 個人設定 フィルタ管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml350Form extends Sml270Form {

    /** フィルター編集モード */
    private int smlFilterCmdMode__ = 0;
    /** 編集対象フィルターSID */
    private int smlEditFilterId__ = 0;
    /** アカウント名 */
    private String sml350accountName__ = null;
    /** フィルターリスト */
    private List<Sml330FilterDataModel> filList__ = null;
    /** チェックされている並び順 */
    private String sml350SortRadio__ = null;
    /** 並び替え対象のラベル */
    private String[] sml350sortLabel__ = null;
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
     * <p>smlFilterCmdMode を取得します。
     * @return smlFilterCmdMode
     */
    public int getSmlFilterCmdMode() {
        return smlFilterCmdMode__;
    }


    /**
     * <p>smlFilterCmdMode をセットします。
     * @param smlFilterCmdMode smlFilterCmdMode
     */
    public void setSmlFilterCmdMode(int smlFilterCmdMode) {
        smlFilterCmdMode__ = smlFilterCmdMode;
    }


    /**
     * <p>smlEditFilterId を取得します。
     * @return smlEditFilterId
     */
    public int getSmlEditFilterId() {
        return smlEditFilterId__;
    }


    /**
     * <p>smlEditFilterId をセットします。
     * @param smlEditFilterId smlEditFilterId
     */
    public void setSmlEditFilterId(int smlEditFilterId) {
        smlEditFilterId__ = smlEditFilterId;
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
     * <p>sml350accountName を取得します。
     * @return sml350accountName
     */
    public String getSml350accountName() {
        return sml350accountName__;
    }

    /**
     * <p>sml350accountName をセットします。
     * @param sml350accountName sml350accountName
     */
    public void setSml350accountName(String sml350accountName) {
        sml350accountName__ = sml350accountName;
    }

    /**
     * <p>filList を取得します。
     * @return filList
     */
    public List<Sml330FilterDataModel> getFilList() {
        return filList__;
    }

    /**
     * <p>filList をセットします。
     * @param filList filList
     */
    public void setFilList(List<Sml330FilterDataModel> filList) {
        filList__ = filList;
    }

    /**
     * <p>sml350SortRadio を取得します。
     * @return sml350SortRadio
     */
    public String getSml350SortRadio() {
        return sml350SortRadio__;
    }

    /**
     * <p>sml350SortRadio をセットします。
     * @param sml350SortRadio sml350SortRadio
     */
    public void setSml350SortRadio(String sml350SortRadio) {
        sml350SortRadio__ = sml350SortRadio;
    }

    /**
     * <p>sml350sortLabel を取得します。
     * @return sml350sortLabel
     */
    public String[] getSml350sortLabel() {
        return sml350sortLabel__;
    }

    /**
     * <p>sml350sortLabel をセットします。
     * @param sml350sortLabel sml350sortLabel
     */
    public void setSml350sortLabel(String[] sml350sortLabel) {
        sml350sortLabel__ = sml350sortLabel;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("smlEditFilterId", getSmlEditFilterId());
    }

}
