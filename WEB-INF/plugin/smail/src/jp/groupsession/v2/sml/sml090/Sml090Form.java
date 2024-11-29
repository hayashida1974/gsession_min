package jp.groupsession.v2.sml.sml090;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.sml010.Sml010Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール詳細検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml090Form extends Sml010Form {

    /** 検索実行 switch */
    private int searchFlg__ = GSConstSmail.SEARCH_EXECUTE_FALSE;
    /** 処理モード（sml010ProcMode）のセーブ値 */
    private String sml090ProcModeSave__ = GSConstSmail.TAB_DSP_MODE_JUSIN;

    //ページコンボ
    /** ページコンボ上 */
    private int sml090page1__ = 0;
    /** ページコンボ下 */
    private int sml090page2__ = 0;

    /** 宛先SIDリスト */
    private String[] cmn120userSid__;


    /** 宛先情報 */
    private SmailModel sml090AtesakiModel__ = null;


    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int sml090searchUse__ = GSConst.PLUGIN_USE;


    /** ページラベル */
    private ArrayList<LabelValueBean> smlPageLabel__;

    /** 削除チェック */
    private String[] sml090DelSid__;
    /** 削除チェック (選択済) */
    private ArrayList<String> sml090SelectedDelSid__;





    //検索結果
    /** 検索結果リスト */
    private List<SmailModel> sml090SearchResultList__ = null;



    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target エラーの際に表示するターゲット名
     * @param req リクエスト
     * @param con コネクション
     * @return errors エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateSelectCheckDel(
            String target, HttpServletRequest req, Connection con) throws Exception {

        ActionErrors errors = new ActionErrors();
        GSValidateSmail.validateCheckBoxMessage(
                errors, target, sml090DelSid__, con, getSmlViewAccount());
        return errors;
    }

    /**
     * <p>sml090SearchResultList を取得します。
     * @return sml090SearchResultList
     */
    public List<SmailModel> getSml090SearchResultList() {
        return sml090SearchResultList__;
    }

    /**
     * <p>sml090SearchResultList をセットします。
     * @param sml090SearchResultList sml090SearchResultList
     */
    public void setSml090SearchResultList(
            List<SmailModel> sml090SearchResultList) {
        sml090SearchResultList__ = sml090SearchResultList;
    }

    /**
     * <p>sml090page1 を取得します。
     * @return sml090page1
     */
    public int getSml090page1() {
        return sml090page1__;
    }

    /**
     * <p>sml090page1 をセットします。
     * @param sml090page1 sml090page1
     */
    public void setSml090page1(int sml090page1) {
        sml090page1__ = sml090page1;
    }

    /**
     * <p>sml090page2 を取得します。
     * @return sml090page2
     */
    public int getSml090page2() {
        return sml090page2__;
    }

    /**
     * <p>sml090page2 をセットします。
     * @param sml090page2 sml090page2
     */
    public void setSml090page2(int sml090page2) {
        sml090page2__ = sml090page2;
    }

    /**
     * <p>smlPageLabel を取得します。
     * @return smlPageLabel
     */
    public ArrayList<LabelValueBean> getSmlPageLabel() {
        return smlPageLabel__;
    }

    /**
     * <p>smlPageLabel をセットします。
     * @param smlPageLabel smlPageLabel
     */
    public void setSmlPageLabel(ArrayList<LabelValueBean> smlPageLabel) {
        smlPageLabel__ = smlPageLabel;
    }

    /**
     * <p>cmn120userSid を取得します。
     * @return cmn120userSid
     */
    public String[] getCmn120userSid() {
        return cmn120userSid__;
    }

    /**
     * <p>cmn120userSid をセットします。
     * @param cmn120userSid cmn120userSid
     */
    public void setCmn120userSid(String[] cmn120userSid) {
        cmn120userSid__ = cmn120userSid;
    }

    /**
     * <p>sml090AtesakiModel を取得します。
     * @return sml090AtesakiModel
     */
    public SmailModel getSml090AtesakiModel() {
        return sml090AtesakiModel__;
    }

    /**
     * <p>sml090AtesakiModel をセットします。
     * @param sml090AtesakiModel sml090AtesakiModel
     */
    public void setSml090AtesakiModel(SmailModel sml090AtesakiModel) {
        sml090AtesakiModel__ = sml090AtesakiModel;
    }

    /**
     * <p>searchFlg を取得します。
     * @return searchFlg
     */
    public int getSearchFlg() {
        return searchFlg__;
    }

    /**
     * <p>searchFlg をセットします。
     * @param searchFlg searchFlg
     */
    public void setSearchFlg(int searchFlg) {
        searchFlg__ = searchFlg;
    }

    /**
     * <p>sml090ProcModeSave を取得します。
     * @return sml090ProcModeSave
     */
    public String getSml090ProcModeSave() {
        return sml090ProcModeSave__;
    }

    /**
     * <p>sml090ProcModeSave をセットします。
     * @param sml090ProcModeSave sml090ProcModeSave
     */
    public void setSml090ProcModeSave(String sml090ProcModeSave) {
        sml090ProcModeSave__ = sml090ProcModeSave;
    }

    /**
     * <p>sml090searchUse を取得します。
     * @return sml090searchUse
     */
    public int getSml090searchUse() {
        return sml090searchUse__;
    }

    /**
     * <p>sml090searchUse をセットします。
     * @param sml090searchUse sml090searchUse
     */
    public void setSml090searchUse(int sml090searchUse) {
        sml090searchUse__ = sml090searchUse;
    }

    /**
     * <p>sml090DelSid を取得します。
     * @return sml090DelSid
     */
    public String[] getSml090DelSid() {
        return sml090DelSid__;
    }

    /**
     * <p>sml090DelSid をセットします。
     * @param sml090DelSid sml090DelSid
     */
    public void setSml090DelSid(String[] sml090DelSid) {
        sml090DelSid__ = sml090DelSid;
    }

    /**
     * <p>sml090SelectedDelSid を取得します。
     * @return sml090SelectedDelSid
     */
    public ArrayList<String> getSml090SelectedDelSid() {
        return sml090SelectedDelSid__;
    }

    /**
     * <p>sml090SelectedDelSid をセットします。
     * @param sml090SelectedDelSid sml090SelectedDelSid
     */
    public void setSml090SelectedDelSid(ArrayList<String> sml090SelectedDelSid) {
        sml090SelectedDelSid__ = sml090SelectedDelSid;
    }

}